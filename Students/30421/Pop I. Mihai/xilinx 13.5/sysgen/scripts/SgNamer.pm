package SgNamer;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(assignEntityNames makeToken isToken strictHdlify prefixHdlNames);

use strict;
use warnings;

use Digest::MD5 qw(md5_hex);
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;
use SgXtable;

use vars qw($vhdlKeywords $verilogKeywords);

$vhdlKeywords = {
  'abs' => 1,
  'access' => 1,
  'across' => 1,
  'after' => 1,
  'alias' => 1,
  'all' => 1,
  'and' => 1,
  'architecture' => 1,
  'array' => 1,
  'assert' => 1,
  'attribute' => 1,
  'begin' => 1,
  'block' => 1,
  'body' => 1,
  'break' => 1,
  'buffer' => 1,
  'bus' => 1,
  'case' => 1,
  'component' => 1,
  'configuration' => 1,
  'constant' => 1,
  'disconnect' => 1,
  'downto' => 1,
  'else' => 1,
  'elsif' => 1,
  'end' => 1,
  'entity' => 1,
  'exit' => 1,
  'false' => 1,
  'file' => 1,
  'for' => 1,
  'function' => 1,
  'generate' => 1,
  'generic' => 1,
  'group' => 1,
  'guarded' => 1,
  'if' => 1,
  'impure' => 1,
  'in' => 1,
  'inertial' => 1,
  'inout' => 1,
  'is' => 1,
  'label' => 1,
  'library' => 1,
  'linkage' => 1,
  'literal' => 1,
  'loop' => 1,
  'map' => 1,
  'mod' => 1,
  'nand' => 1,
  'nature' => 1,
  'new' => 1,
  'next' => 1,
  'noise' => 1,
  'nor' => 1,
  'not' => 1,
  'null' => 1,
  'of' => 1,
  'on' => 1,
  'open' => 1,
  'or' => 1,
  'others' => 1,
  'out' => 1,
  'package' => 1,
  'port' => 1,
  'postponed' => 1,
  'procedural' => 1,
  'procedure' => 1,
  'process' => 1,
  'pure' => 1,
  'quantity' => 1,
  'range' => 1,
  'record' => 1,
  'register' => 1,
  'reject' => 1,
  'rem' => 1,
  'report' => 1,
  'return' => 1,
  'rol' => 1,
  'ror' => 1,
  'select' => 1,
  'severity' => 1,
  'signal' => 1,
  'shared' => 1,
  'sla' => 1,
  'sll' => 1,
  'spectrum' => 1,
  'sra' => 1,
  'srl' => 1,
  'subnature' => 1,
  'subtype' => 1,
  'terminal' => 1,
  'then' => 1,
  'through' => 1,
  'to' => 1,
  'tolerance' => 1,
  'transport' => 1,
  'true' => 1,
  'type' => 1,
  'unaffected' => 1,
  'units' => 1,
  'until' => 1,
  'use' => 1,
  'variable' => 1,
  'wait' => 1,
  'when' => 1,
  'while' => 1,
  'with' => 1,
  'xnor' => 1,
  'xor' => 1,
};

$verilogKeywords = {
  'always' => 1,
  'and' => 1,
  'assign' => 1,
  'automatic' => 1,
  'begin' => 1,
  'buf' => 1,
  'bufif0' => 1,
  'bufif1' => 1,
  'case' => 1,
  'casex' => 1,
  'casez' => 1,
  'cell' => 1,
  'cmos' => 1,
  'config' => 1,
  'deassign' => 1,
  'default' => 1,
  'defparam' => 1,
  'design' => 1,
  'disable' => 1,
  'edge' => 1,
  'else' => 1,
  'end' => 1,
  'endcase' => 1,
  'endconfig' => 1,
  'endfunction' => 1,
  'endgenerate' => 1,
  'endmodule' => 1,
  'endprimitive' => 1,
  'endspecify' => 1,
  'endtable' => 1,
  'endtask' => 1,
  'event' => 1,
  'for' => 1,
  'force' => 1,
  'forever' => 1,
  'fork' => 1,
  'function' => 1,
  'generate' => 1,
  'genvar' => 1,
  'highz0' => 1,
  'highz1' => 1,
  'if' => 1,
  'ifnone' => 1,
  'incdir' => 1,
  'initial' => 1,
  'inout' => 1,
  'input' => 1,
  'instance' => 1,
  'integer' => 1,
  'join' => 1,
  'large' => 1,
  'liblist' => 1,
  'localparam' => 1,
  'localparameter' => 1,
  'macromodule' => 1,
  'medium' => 1,
  'module' => 1,
  'nand' => 1,
  'negedge' => 1,
  'nmos' => 1,
  'nor' => 1,
  'noshowcancelled' => 1,
  'notif0' => 1,
  'notif1' => 1,
  'not' => 1,
  'or' => 1,
  'output' => 1,
  'parameter' => 1,
  'pathpulse$' => 1,
  'pmos' => 1,
  'posedge' => 1,
  'primitive' => 1,
  'pull0' => 1,
  'pull1' => 1,
  'pulldown' => 1,
  'pullup' => 1,
  'pulsestyle_ondetect' => 1,
  'pulsestyle_onevent' => 1,
  'rcmos' => 1,
  'real' => 1,
  'realtime' => 1,
  'reg' => 1,
  'release' => 1,
  'repeat' => 1,
  'rnmos' => 1,
  'rpmos' => 1,
  'rtran' => 1,
  'rtranif0' => 1,
  'rtranif1' => 1,
  'scalared' => 1,
  'showcancelled' => 1,
  'signed' => 1,
  'small' => 1,
  'specify' => 1,
  'specparam' => 1,
  'strong0' => 1,
  'strong1' => 1,
  'supply0' => 1,
  'supply1' => 1,
  'table' => 1,
  'task' => 1,
  'time' => 1,
  'tran' => 1,
  'tranif0' => 1,
  'tranif1' => 1,
  'tri0' => 1,
  'tri1' => 1,
  'tri' => 1,
  'triand' => 1,
  'trior' => 1,
  'trireg' => 1,
  'unsigned' => 1,
  'use' => 1,
  'vectored' => 1,
  'wait' => 1,
  'wand' => 1,
  'weak0' => 1,
  'weak1' => 1,
  'while' => 1,
  'wire' => 1,
  'wor' => 1,
  'xnor' => 1,
  'xor' => 1,
};


#
# Returns the "hdl-ified" version of a given name.  The name is downcased
# and altered in such a way that it becomes acceptable as a VHDL/Verilog
# identifier.  The translation is strict in the sense that the end result
# needs no special quoting to be acceptable in VHDL and Verilog.
#
# @param $name Name to be hdl-ified.
#
# @return hdl-ified version of $name.
#

sub strictHdlify() {
  my $name = $_[0];
  $name =~ s/^\s+//;
  $name =~ s/\s+$//;
  $name =~ s/\W/_/g;
  $name =~ s/^_+//;
  $name =~ s/_+$//;
  $name =~ s/__+/_/g;
  $name = "x$name" unless ($name =~ /^[a-zA-Z]/);
  $name = 'x' if ($name eq '');
  $name .= '_x' if ($vhdlKeywords->{$name} || $verilogKeywords->{$name});
  return substr($name, 0, 225);
}


#
# Returns the spelling that should be used for a given name.  The spelling
# is adjusted so that it doesn't collide with any spellings already used.
#
# @param %$namesAlreadyUsed tells what spellings have already been used.
# @param $suffix suffix (if any) that should be adjoined when forming names.
# @param $suggestedSpelling suggested spelling.
#
# @return spelling that should be used for the given token.
#

sub makeSpellingUnique() {
  my($namesAlreadyUsed, $suffix, $suggestedSpelling) = @_;

  # Return a simple concatenation of suggested spelling and suffix,
  # provided this doesn't collide with any name already used.
  my $n = "$suggestedSpelling$suffix";
  return $n unless ($namesAlreadyUsed->{$n});

  # Return a simple concatenation of suggested spelling, "_x0",
  # and suffix, provided this doesn't collide with any name already used.
  $n = $suggestedSpelling . "_x0$suffix";
  return $n unless ($namesAlreadyUsed->{$n});

  # Otherwise, loop to test disambiguating suffixes until one that avoids
  # collisions is found.
  my $bad = 0;
  my $good = 50;
  while ($namesAlreadyUsed->{$suggestedSpelling . "_x$good$suffix"}) {
    $good += 50;
  }
  while ($good > $bad + 1) {
    my $mid = int(($good + $bad) / 2);
    if ($namesAlreadyUsed->{$suggestedSpelling . "_x$mid$suffix"}) {
      $bad = $mid;
    }
    else {
      $good = $mid;
    }
  }
  return $suggestedSpelling . "_x$good$suffix";
}


#
# Returns a hash table whose key/values tell how tokens should be
# resolved into names.
#
# @param %$tokens Keys tell the tokens that must be resolved.
# @param %$instrs Instructions telling what names to translate and how
#        they should be translated.  See the comment at the head of
#        "assignEntityNames" for details.  Note: This member updates
#        the instructions to show what names tokens are resolved to.
#
# @return Hash table whose keys are tokens; values tell the names to which
#         tokens should be resolved.
#

sub resolveTokens {
  my($tokens, $instrs) = @_;

  # Build hash table whose keys tell what names have already been used.
  my $namesAlreadyUsed = &SgXtable::copy($instrs->{"namesAlreadyUsed"});

  # Make two passes to resolve tokens to names.  1st pass deals with tokens
  # whose names are demanded or have already been resolved; 2nd deals with the
  # remainder.
  my $suffix = $instrs->{"suffix"};
  $suffix = "" unless ($suffix);
  my $lazy = $instrs->{"lazy"};
  my $nameMap = $instrs->{"nameMap"};
  my $resolutions = {};
  my $token;
  foreach $token (sort(keys %$tokens)) {
    my $v = $nameMap->{$token};
    my $demanded = $v->{"demanded"};
    my $resolvedName = $v->{"resolvedTo"};
    if ($resolvedName) {
      $resolutions->{$token} = $resolvedName;
      $namesAlreadyUsed->{$resolvedName} = 1;
    }
    elsif ($demanded && ! $lazy) {
      my $suggestedSpelling = $v->{"spelling"};
      $resolutions->{$token} = $suggestedSpelling;
      $namesAlreadyUsed->{$suggestedSpelling} = 1;
      $v->{"resolvedTo"} = $suggestedSpelling;
    }
  }

  if (! $lazy) {
    foreach $token (sort(keys %$tokens)) {
      my $v = $nameMap->{$token};
      my $demanded = $v->{"demanded"};
      my $resolvedName = $v->{"resolvedTo"};
      next if ($resolvedName || $demanded);
      my $suggestedSpelling = $v->{"spelling"};
      defined($suggestedSpelling) ||
        croak "missing suggested spelling for token $token";
      my $name = &makeSpellingUnique($namesAlreadyUsed,
                   $suffix, $suggestedSpelling);
      $resolutions->{$token} = $name;
      $v->{"resolvedTo"} = $suggestedSpelling;
      $namesAlreadyUsed->{$name} = 1;
    }
  }

  return $resolutions;
}


#
# Finds the tokens in a given file, and adds them as keys to a given hash table.
#
# @param $file name of the file to search for tokens.
# @param %$tokens Hash table to which tokens should be added as keys.
#

sub findTokens() {
  my($file, $tokens) = @_;

  # Read file into local array.
  my $handle = new FileHandle;
  open($handle, "< $file") || croak "couldn't open $file";
  my @text = <$handle>;
  close($handle) || croak "trouble reading $file";
  chomp(@text);

  # Find tokens on each line of the file.
  my @newText;
  my $line;
  foreach $line (@text) {
    while ($line =~ /^(.*)\b(x_[0-9a-f]{10})\b(.*)$/) {
      my($prefix, $token, $suffix) = ($1, $2, $3);
      $tokens->{$token} = 1;
      $line = $prefix . "token" . $suffix;
    }
  }
  return;
}


#
# Returns true (false) if a given name is (is not) a token.
#
# @param $name name to test.
#
# @return true (false) if a given name is (is not) a token.
#

sub isToken() {
  my $name = $_[0];
  return $name =~ /^x_[0-9a-f]{10}$/;
}


#
# Replaces tokens with names in a given file.  Note: In this member, if
# something looks like a token, it is assumed to be a token.
#
# @param $file name of the file in which names should be fixed.
# @param %$resolutions Keys tell tokens to be replaced; values tell
#        replacements.
#

sub assignNames() {
  my($file, $resolutions) = @_;

  # Read file into local array.
  my $handle = new FileHandle;
  open($handle, "< $file") || croak "couldn't open $file";
  my @text = <$handle>;
  close($handle) || croak "trouble reading $file";
  chomp(@text);

  # Fix names on each line of the file.  Note: The approach to finding names
  # is not sophisticated.  If a string looks like a name, it is a name.
  # A name is something like "x_a1b23c4d5f"; it consists of an "x_" followed
  # by a string of exactly 10 lower-case hex digits.
  my @newText;
  my $line;
  foreach $line (@text) {
    my($token, $suffix, @parts);
    my $prefix = $line;
    while ($prefix =~ /^(.*)\b(x_[0-9a-f]{10})\b(.*)$/) {
      ($prefix, $token, $suffix) = ($1, $2, $3);
      my $replacement = $resolutions->{$token};
      $replacement = $token unless ($replacement);
      unshift(@parts, $replacement, $suffix);
    }
    push(@newText, $prefix . join("", @parts));
  }

  # Write the new version of the file.
  $handle = new FileHandle;
  binmode $handle;
  chmod 0755, $file || croak "couldn't change permissions on $file";
  open($handle, "> $file") || croak "couldn't write $file";
  foreach $line (@newText) {
    print $handle "$line\n";
  }
  close($handle) || croak "trouble writing $file";
}


#
# Translates tokens to genuine entity names in a given list of files using a
# given set of instructions.  This member assumes that tokens start with "x_"
# and end with a standard number of lower-case hex digits.
#
# @param %$args XTable that tells the files that should be updated, and how
#        the updating should be done.  Example:
#
# {
#   "files" => [ "myfile1.vhd", "myfile2.vhd", ],
#   "entityNamingInstrs" => {
#     "suffix" => "_3c4d5s3d",
#     "lazy" => 0,
#     "namesAlreadyUsed" => {
#       "mydesign_xladdsub" => 1,
#       "mydesign_xladdsub1" => 1,
#     },
#     "nameMap" => {
#       "x_a1b23c4d5f" => {
#         "spelling" => "mydesign_xlmult_subsystem1",
#       },
#       "x_b23c4d5fa2" => {
#         "spelling" => "mydesign_xlmult_subsystem2",
#       },
#       "x_a22322144b" => {
#         "demanded" => 1,
#         "spelling" => "mydesign_xlmult_subsystem2",
#       },
#       "x_dd454d11a2" => {
#         "spelling" => "mydesign_xlmult",
#       },
#       "x_e333e4ecc2" => {
#         "demanded" => 1,
#         "spelling" => "mydesign",
#       },
#       "x_ffffee4455" => {
#         "spelling" => "hello",
#       },
#     },
#   }
# }
#
# Here the files in which names should be translated are "myfile1.vhd" and
# "myfile2.vhd".  The names that have already been assigned to entities (and
# that must, therefore be avoided) are "mydesign_xladdsub" and
# "mydesign_xladdsub1".  The "demanded" names are ones for which no adjusting
# can be done when translating.  For names that are not demanded, the suffix
# "_3c4d5s3d" is to be adjoined to the name.  The "lazy" key/value indicates
# whether name assignments should be lazy.  Lazy assignment only replaces
# a token with a name provided the token was previously resolved to a name
# in an earlier assigmnent.  The "resolvedTo" key/value (see example below)
# tells which tokens have been resolved.
#
# Note: if the "fromSynopsis" flag is supplied, then missing arguments (e.g.,
# "files" and "entityNamingInstrs") are obtained from the synopsis in the
# working directory.  If the "updateSynopsis" flag is set, the synopsis in
# the working directory is updated to reflect the results of naming.
#
# @return Argument that was passed in, updated to reflect the name assignments
#         that were made.  For example, if called with the instructions shown
#         above, this member might return the following:
# {
#   "files" => [ "myfile1.vhd", "myfile2.vhd", ],
#   "entityNamingInstrs" => {
#     "suffix" => "_3c4d5s3d",
#     "namesAlreadyUsed" => {
#       "mydesign_xladdsub" => 1,
#       "mydesign_xladdsub1" => 1,
#       "mydesign" => 1,
#       "mydesign_xlmult_3c4d5s3d" => 1,
#       "mydesign_xlmult_subsystem1_3c4d5s3d" => 1,
#       "mydesign_xlmult_subsystem2" => 1,
#       "mydesign_xlmult_subsystem2_3c4d5s3d" => 1,
#     },
#     "nameMap" => {
#       "x_a1b23c4d5f" => {
#         "spelling" => "mydesign_xlmult_subsystem1",
#         "resolvedTo" => "mydesign_xlmult_subsystem1_3c4d5s3d",
#       },
#       "x_b23c4d5fa2" => {
#         "spelling" => "mydesign_xlmult_subsystem2",
#         "resolvedTo" => "mydesign_xlmult_subsystem2_3c4d5s3d",
#       },
#       "x_a22322144b" => {
#         "demanded" => 1,
#         "spelling" => "mydesign_xlmult_subsystem2",
#         "resolvedTo" => "mydesign_xlmult_subsystem2",
#       },
#       "x_dd454d11a2" => {
#         "spelling" => "mydesign_xlmult",
#         "resolvedTo" => "mydesign_xlmult_3c4d5s3d",
#       },
#       "x_e333e4ecc2" => {
#         "demanded" => 1,
#         "spelling" => "mydesign",
#         "resolvedTo" => "mydesign",
#       },
#       "x_ffffee4455" => {
#         "spelling" => "hello",
#       },
#     },
#   }
# }
#

sub assignEntityNames() {
  my $args = $_[0];

  # Sanity check: verify every file to be fixed actually exists and is a
  # plain file.
  my $files = &Sg::getAttribute("files", $args);
  my $file;
  foreach $file (@$files) {
    -e $file || croak "there is no $file";
    -f $file || croak "$file is not a plain file";
  }

  # Read the files to determine which tokens need resolving.
  my $tokens = {};
  foreach $file (@$files) {
    next if (-B $file);
    &findTokens($file, $tokens);
  }

  # Build hash table whose key/values tell how to translate tokens to
  # names.
  my $instrs = &Sg::getAttribute("entityNamingInstrs", $args);
  my $resolutions = &resolveTokens($tokens, $instrs);

  # Translate names in each file.
  if (scalar(keys %$resolutions)) {
    foreach $file (@$files) {
      next if (-B $file);
      &assignNames($file, $resolutions);
    }
  }

  # If the "updateSynopsis" flag is set, then update synopsis in working
  # directory with results from above.
  if ($args->{"updateSynopsis"}) {
    my $synopsis = &Sg::evaluateSynopsis();
    $synopsis->{"attributes"}->{"entityNamingInstrs"} = $instrs;
    &Sg::writeSynopsis($synopsis);
  }

  return $args;
}


#
# Returns the "natural" token to use to represent a name.
#
# @param $name Name whose token should be returned.
#
# @return token to represent $name.
#

sub makeToken() {
  my $name = $_[0];
  return "x_" . substr(&md5_hex($name), 0, 10);
}


#
# Finds the entity/package names in a given VHDL file.
#
# @param $file Name of the file to search.
# @param %$names Hash table whose keys, on return, tell the names that were
#        found.
#

sub findVhdlNames {
  my($file, $names) = @_;

  open(FILE, "< $file") || croak "couldn't read $file";
  my $line;
  foreach $line (<FILE>) {
    chomp($line);
    if ($line =~ /^\s*entity\s+(\w+)\s+is\s*$/i) {
      $names->{$1} = 1;
    }
    elsif ($line =~ /^\s*package\s+(\w+)\s+is\s*$/i) {
      $names->{$1} = 1;
    }
  }
  close(FILE) || croak "trouble reading $file";
}


#
# Finds the module names in a given Verilog file.
#
# @param $file Name of the file to search.
# @param %$names Hash table whose keys, on return, tell the names that were
#        found.
#

sub findVerilogNames {
  my($file, $names) = @_;

  open(FILE, "< $file") || croak "couldn't read $file";
  my $line;
  foreach $line (<FILE>) {
    chomp($line);
    if ($line =~ /^\s*module\s+(\w+)\s*$/i) {
      $names->{$1} = 1;
    }
  }
  close(FILE) || croak "trouble reading $file";
}


#
# Fixes a line from a VHDL or Verilog file so package, entity, architecture, ...
# all start with a given prefix.
#
# @param $line      Line to be fixed.
# @param $prefix    Prefix to be added to entity, architecture, ... names.
# @param %$names    Hash table whose keys tell what names to fix.
# @param @$patterns Patterns to fix.
# @param $hdlKind   Tells the kind of HDL (vhdl or verilog) we are fixing.
#
# @return Fixed line.
#

sub fixHdlLine {
  my($line, $prefix, $names, $patterns, $hdlKind) = @_;

  my $p;
  foreach $p (@$patterns) {
    my $head = $p;
    $head =~ s/ .*//;
    my $tail = $p;
    $tail =~ s/.* //;
    my $pattern = "^($head)(\\w+)($tail)\$";
    if ($line =~ /$pattern/i && defined($names->{$2})) {
      return $1 . $prefix . "_" . $2 . $3
    }
    $pattern = $hdlKind eq "vhdl"? "^($head)(\\\\.*\\\\)($tail)\$" :
                 "^($head)(\\\\.*\\s+)($tail)\$";
    if ($line =~ /$pattern/i && defined($names->{$2})) {
      my $oldName = substr($2, 1);
      return $1 . "\\" . $prefix . "_" . $oldName . $3
    }
  }

  return $line;
}


#
# Fixes a line from a VHDL file so their package, entity, architecture, ...
# start with a given prefix.
#
# @param $line         Line to be fixed.
# @param $prefix       Prefix to be added to entity, architecture, ... names.
# @param %$names       Hash table whose keys tell what names to fix.
#
# @return Fixed line.
#

sub fixVhdLine {
  my($line, $prefix, $names) = @_;

  my $patterns = [
    "\\s*entity\\s+ \\s+is\\s*",
    "\\s*package\\s+ \\s+is\\s*",
    "\\s*package\\s+body\\s+ \\s+is\\s*",
    "\\s*end\\s+ \\s*;\\s*",
    "\\s*architecture\\s+\\w+\\s+of\\s+ \\s+is\\s*",
    "\\s*use\\s+work. .all\\s*;\\s*",
    "work\\. \\..*",
    ".*\\Wwork\\. \\..*",
    "\\s*component\\s+ .*",
    "\\s*attribute\\s+\\w+\\s+of\\s+ .*",
    "\\s*\\w+\\s*:\\s* \\s*",
    "\\s*\\\\.*\\\\\\s*:\\s* \\s*",
    "\\s*\\w+\\s*:\\s*entity\\s+work\\. \\s*",
    "\\s*\\\\.*\\\\\\s*:\\s*entity\\s+work\\. \\s*",
  ];

  my $p;
  return &fixHdlLine($line, $prefix, $names, $patterns, "vhdl");
}

#
# Fixes a line from a Verilog file so module names start with a given prefix.
#
# @param $line         Line to be fixed.
# @param $prefix       Prefix to be added to entity, architecture, ... names.
# @param %$names       Hash table whose keys tell what names to fix.
#
# @return Fixed line.
#

sub fixVLine {
  my($line, $prefix, $names) = @_;

  my $patterns = [
    "\\s* \\s*\\#.*",
    "\\s* \\s+\\w+.*",
  ];
  return &fixHdlLine($line, $prefix, $names, $patterns, "verilog");
}


#
# Fixes the entity, module, architecture, ... names in a given file.
#
# @param $file File to fix.
# @param $prefix Tells what prefix to use to fix names.
# @param %$names Keys tell what names to fix.
#

sub fixNames {
  my($file, $prefix, $names) = @_;

  # Read file into local array.
  open(FILE, "< $file") || &internal("couldn't open $file");
  my(@text) = <FILE>;
  close(FILE) || &internal("trouble reading $file");
  chomp(@text);

  # Fix names in file.
  my(@newText, $line);
  if ($file =~ /\.vhd$/i) {
    foreach $line (@text) {
      push(@newText, &fixVhdLine($line, $prefix, $names));
    }
  }
  elsif ($file =~ /\.v$/i) {
    foreach $line (@text) {
      push(@newText, &fixVLine($line, $prefix, $names));
    }
  }

  # Replace old file with updated version.
  unlink $file;
  ! -e $file || &internal("couldn't discard old $file");
  open(NEWFILE, "> $file") || croak "couldn't write $file";
  binmode(NEWFILE);
  foreach $line (@newText) {
    print NEWFILE "$line\n";
  }
  close(NEWFILE) || croak "trouble writing $file";
}


#
# Saves the file names following a "-f" command line argument.
#
# @param @$files Array in which file names should be saved.
#

sub saveFileNames {
  my($files) = @_;
  my($file) = shift(@ARGV);
  while (defined($file) && $file !~ /^-/) {
    push(@$files, $file);
    $file = shift(@ARGV);
  }
  unshift(@ARGV, $file) if (defined($file));
}


#
# Processes command line options, then returns the results.
#

sub processArgs {
  &usage() unless (scalar(@ARGV));

  my(@files, $prefix);
  while (scalar(@ARGV)) {
    my($arg) = shift(@ARGV);
       if ($arg eq "-h")   { &usage();                }
    elsif ($arg eq "-p")   { $prefix = shift(@ARGV);  }
    elsif ($arg eq "-f")   { &saveFileNames(\@files); }
    else                   { &usage();                }
  }

  # Verify prefix makes sense.
  defined($prefix) || &usage();
  $prefix =~ /^[a-zA-Z]\w*$/ || croak "prefix is badly formed";
  $prefix !~ /__/ || croak "prefix is badly formed";
  $prefix =~ s/_$//;

  # Verify file list makes sense.
  scalar(@files) || croak "list of files is empty";
  my(@newFiles, %alreadySeen);
  my $f;
  foreach $f (@files) {
    $f =~ /\.v(hd)?$/i || croak "expected file names to end with .vhd or .v";
    $f =~ tr/A-Z/a-z/;
    -e $f || croak "there is no $f";
    -f $f || croak "$f is not a plain file";
    -r $f || croak "$f is not readable";
    -w $f || croak "$f is not writable";
    push(@newFiles, $f) unless (exists($alreadySeen{$f}));
    $alreadySeen{$f} = 1;
  }

  return ($prefix, \@newFiles);
}


#
# Adds a prefix to entity/package/module names in a given collection of
# HDL files.
#
# @param %$args tells processing arguments.  Keys/values are the following:
#   prefix - Tells the prefix to add.  The prefix must be acceptable as part
#            of an ordinary VHDL/Verilog identifier, which means it must
#            consist of letters, digits, and underscores, must start with a
#            letter, and must not contain two consecutive underscores.
#   files -  Tells what files to adjust.  VHDL file names must end with .vhd
#            and Verilog file names must end with .v.
#

sub prefixHdlNames {
  my $args = $_[0];
  my $prefix = $args->{'prefix'};
  my $files = $args->{'files'};

  # Make a first pass to find all the entity/module/package names.
  my(%names, $file);
  foreach $file (@$files) {
    if ($file =~ /\.vhd$/i) {
      &findVhdlNames($file, \%names);
      next;
    }
    elsif ($file =~ /\.v$/i) {
      &findVerilogNames($file, \%names);
      next;
    }
    else {
      croak "expected file name to end in .vhd or .v";
    }
  }

  # Make a second pass to fix all the entity/module/package names.
  foreach $file (@$files) {
    &fixNames($file, $prefix, \%names);
#   print STDOUT "fixed names in $file\n";
  }
}


1;
