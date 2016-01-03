package SgCache;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(getHash copyOutFile addToCache deleteFromCache);

use strict;
use IPC::Open3;
use Digest::MD5;
use Carp qw(croak);
use Sg;
use SgEnv;

use vars qw($dcinit $dccli $sghash);

# Disk Cache initialization flag
$dcinit = 0;

sub init {
  if (!$dcinit) {
    my $bindir = &SgEnv::getSysgenBinPath();
    $sghash = "$bindir/dsphash";
    $dccli = "$bindir/dccli";
    $dcinit = 1;
  }
}

#
# Given a list of strings and filenames, generate a unique hash.
#
# @param    hashlist    Reference to an array of parameters to hash.
#                       Parameters that begin with "S:" will be hashed as
#                       strings.  Parameters that begin with "F:" will be
#                       hashed as files (i.e. their contents will be hashed).
# @return   Hash value.
#
sub getHash {
  my $hashlist = shift;
  my $x;
  my $ctx = Digest::MD5->new;
  my $f;

  foreach $x (@$hashlist) {
    if ($x =~ /^S:(.*)/) {
      $ctx->add($1);
    }
    elsif ($x =~ /^F:(.*)/) {
      open($f, "<$1") || croak "Failed to open '$1': $!\n";
      $ctx->addfile($f);
      close($f);
    }
    else {
      $ctx->add($x);
    }
  }
  return $ctx->hexdigest();
}

#
# Copy an entry out of the cache, if it exists.  The function returns 1 on
# failure, but it is up to the caller to verify whether the files were actually
# copied.
#
sub copyOutFile {
  my $hash = shift;
  my $destdir = shift;

  init();
  my ($result, @lines) = runcmd($dccli, '-c', $hash, $destdir);
  if ($result || @lines) {
    return 1;
  }
  return 0;
}

#
# Add the specified file/directory to the cache.  The function returns 1 on
# failure or 0 on success.
#
sub addToCache {
  my $hash = shift;
  my $fn = shift;

  init();
  my ($result, @lines) = runcmd($dccli, '-a', $hash, $fn);
  if ($result || @lines) {
    return 1;
  }
  return 0;
}

#
# Delete the specified entry from the cache.
#
sub deleteFromCache {
  my $hash = shift;

  init();
  my ($result, @lines) = runcmd($dccli, '-r', $hash);
  if ($result || @lines) {
    return 1;
  }
  return 0;
}

#
# Run a command and capture all output in an array
#
sub runcmd {
    my $cmd = shift;

    my $run;
    my @parameters;
    my @output;

    if (!@_) {
        ($run, @parameters) = split /\s+/, $cmd;
    } else {
        $run = $cmd;
        @parameters = @_;
    }

    open3(\*RUNNERINPUT, \*RUNNEROUTPUT, 0, $run, @parameters);
    # Handle the output.
    while (<RUNNEROUTPUT>) {
      chomp;
      s/\r//g;
      push @output, $_;
    }

    #
    # Wait for the child process to properly terminate.
    #
    wait;
    close(RUNNERINPUT);
    close(RUNNEROUTPUT);

    return ($?, @output);
}

1;
