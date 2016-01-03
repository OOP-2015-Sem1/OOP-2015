package SgXtable;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(toString hash deserialize serialize);

use strict;
use warnings;

use Digest::MD5;
use Carp qw(croak);

use vars qw($BOOL $INT $DOUBLE $STRING $SEQUENCE $MAP $NULL);

my( $BOOL,   $INT, $DOUBLE, $STRING, $SEQUENCE,   $MAP,  $NULL) =
  (chr(0), chr(1),  chr(2),  chr(3),    chr(4), chr(5), chr(6));


#
# Returns a string that realizes a given Perl object.  The string is formed
# so that a subsequent eval recreates the object.
#
# Note: To simplify things, this member assumes the object to be dumped is
# an XTable.  An XTable is one of the following:
#   Undef;
#   A Boolean, string, or number;
#   A reference to a hash whose keys are strings and values are XTables;
#   A reference to an array whose elements are XTables.
#
# @param $s   Object to be dumped.
# @param $pad String to use as padding when indenting.
#
# @return Text string that represents $s.
#

sub toString {
  my($s, $pad) = @_;
  return &toString($s, "") unless (defined($pad));
  return "undef" unless (defined($s));
  my $t;
  if (ref($s) eq "HASH") {
    $t = "{";
    my $key;
    foreach $key (sort(keys %$s)) {
      my $k = $key;
      $k =~ s/\\/\\\\/g;
      $k =~ s/'/\\'/g;
      $t .= "\n$pad  '$k' => " .
        &toString($s->{$key}, "$pad  ") . ",";
    }
    $t .= "\n" . $pad . "}";
  }
  elsif (ref($s) eq "ARRAY") {
    $t = "[";
    my $elt;
    foreach $elt (@$s) {
      $t .= "\n$pad  " . &toString($elt, "$pad  ") . ",";
    }
    $t .= "\n" . $pad . "]";
  }
  elsif ($s =~ /^[+-]?\d+(\.\d*)?(e\d+)?$/) {
    $t = $s;
  }
  else {
    $t = $s;
    $t =~ s/\\/\\\\/g;
    $t =~ s/'/\\'/g;
    $t = "'$t'";
  }

  return $t;
}


#
# Returns a deep copy of an XTable.
#
# @param $x XTable to be copied.
#
# @return Deep copy of $x.
#

sub copy {
  my $x = $_[0];
  return undef unless (defined($x));

  if (ref($x) eq "HASH") {
    my $x1 = {};
    my $key;
    foreach $key (sort(keys %$x)) {
      $x1->{$key} = &SgXtable::copy($x->{$key});
    }
    return $x1;
  }

  if (ref($x) eq "ARRAY") {
    my $x1 = [];
    my $elt;
    foreach $elt (@$x) {
      push(@$x1, &SgXtable::copy($elt));
    }
    return $x1;
  }

  return $x;
}


#
# Returns an MD5 hash of a given XTable.
#
# @param $x XTable whose MD5 hash should be returned.
#
# @return MD5 hash of $x.
#

sub hash {
  my $x = $_[0];
  my $md5 = Digest::MD5->new;
  if (ref($x) eq "HASH") {
    $md5->add("HASH");
    my $key;
    foreach $key (sort(keys %$x)) {
      $md5->add($key);
      $md5->add(&SgXtable::hash($x->{$key}));
    }
  }
  elsif (ref($x) eq "ARRAY") {
    $md5->add("ARRAY");
    my $value;
    foreach $value (@$x) {
      $md5->add(&SgXtable::hash($value));
    }
  }
  else {
    $md5->add("SCALAR");
    $md5->add($x);
  }
  return $md5->digest;
}


#
# Returns the Boolean that is encoded by the leading byte of a serialized
# XTable, and discards the byte from the stream.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return Boolean that is encoded by the leading byte of the serialized
#         XTable in $b.
#

sub deserializeBool() {
  my $b = $_[0];
  my $byte = substr($b, 0, 1);
  $b = substr($b, 1);
  return(ord($byte) == 0? 0 : 1, $b);
}


#
# Returns the int that is encoded by a the leading four bytes of a serialized
# XTable, and discards the bytes from the stream.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return int that is encoded by the leading four bytes of the serialized XTable
#         in $b.
#

sub deserializeInt() {
  my $b = $_[0];
  my $v = unpack("N", substr($b, 0, 4));
  $b = substr($b, 4);
  return($v, $b);
}


#
# Returns the double that is encoded by the leading 8 bytes of a serialized
# XTable, and discards the bytes from the stream.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return double that is encoded by the leading 8 bytes of the serialized
#         XTable in $b.
#

sub deserializeDouble() {
  my $b = $_[0];
  my $v = unpack("d*", substr($b, 0, 8));
  $b = substr($b, 8);
  return($v, $b);
}


#
# Returns the string that is encoded by the leading bytes of a serializied
# XTable, and discards the bytes from the stream.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return string that is encoded by the leading bytes of the serialized
#         XTable in $b.
#

sub deserializeString() {
  my $b = $_[0];
  my $len = unpack("N", substr($b, 0, 4));
  my $s = substr($b, 4, $len);
  $b = substr($b, 4 + $len);
  return($s, $b);
}


#
# Returns the sequence that is encoded by the leading bytes of a serializied
# XTable, and discards the bytes from the stream.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return sequence that is encoded by the leading bytes of the serialized
#         XTable in $b.
#

sub deserializeSequence() {
  my $b = $_[0];
  my $len = unpack("N", substr($b, 0, 4));
  $b = substr($b, 4);
  my $s = [];
  my $i;
  for ($i = 0; $i < $len; $i++) {
    (my $elt, $b) = &_deserialize($b);
    push(@$s, $elt);
  }
  return($s, $b);
}


#
# Returns the map that is encoded by the leading bytes of a serializied
# XTable, and discards the bytes from the stream.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return map that is encoded by the leading bytes of the serialized
#         XTable in $b.
#

sub deserializeMap() {
  my $b = $_[0];
  my $nrKeyValuePairs = unpack("N", substr($b, 0, 4));
  $b = substr($b, 4);
  my $m = {};
  my $i;
  for ($i = 0; $i < $nrKeyValuePairs; $i++) {
    (my $key, $b) = &_deserialize($b);
    (my $value, $b) = &_deserialize($b);
    $m->{$key} = $value;
  }
  return($m, $b);
}


#
# Returns the XTable that is encoded by a given byte sequence.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return XTable that is encoded by the serialized stream in $b.
#

sub _deserialize() {
  my $b = $_[0];
  my $byte = substr($b, 0, 1);
  $b = substr($b, 1);
  return &deserializeBool($b) if ($byte eq $BOOL);
  return &deserializeInt($b) if ($byte eq $INT);
  return &deserializeDouble($b) if ($byte eq $DOUBLE);
  return &deserializeString($b) if ($byte eq $STRING);
  return &deserializeSequence($b) if ($byte eq $SEQUENCE);
  return &deserializeMap($b) if ($byte eq $MAP);
  return (undef, $b) if ($byte eq $NULL);
  croak "can't deserialize byte stream into XTable";
}


#
# Returns the XTable that is encoded by a given byte sequence.
#
# @param $b byte sequence that contains a serialized XTable.
#
# @return XTable that is encoded by the serialized stream in $b.
#

sub deserialize() {
  my $b = $_[0];
  (my $x, $b) = &_deserialize($b);
  return $x;
}


#
# Serializes an XTable.
#
# @param $x XTable to serialize.
#
# @return Sequence of bytes that encodes the XTable $x.
#

sub serialize() {
  my $x = $_[0];

  # If $x is undef, then return $NULL.
  return $NULL unless (defined($x));

  # If $x is a hash, then adjoin the number of key/value pairs, followed by
  # the encoding of each pair.
  if (ref($x) eq "HASH") {
    my $s = $MAP . pack("N", scalar(keys %$x));
    my $key;
    foreach $key (sort(keys %$x)) {
      $s .= $STRING . pack("N", length($key)) . $key . &serialize($x->{$key});
    }
    return $s;
  }

  # If $x is an array, then adjoin the number of elements, followed by
  # the encoding of each element.
  if (ref($x) eq "ARRAY") {
    my $s = $SEQUENCE . pack("N", scalar(@$x));
    my $elt;
    foreach $elt (@$x) {
      $s .= &serialize($elt);
    }
    return $s;
  }

  # If $x is an int, adjoin a 4-byte encoding.
  if ($x =~ /^[+-]?\d+$/) {
    return $INT . pack("N", $x);
  }

  # If $x is a double, adjoin an 8-byte encoding.
  if ($x =~ /^[+-]?\d+(\.\d*)?(e\d+)?$/) {
    return $DOUBLE . pack("d*", $x);
  }

  # Otherwise, $x must be a string;
  return $STRING . pack("N", length($x)) . $x;
}


1;
