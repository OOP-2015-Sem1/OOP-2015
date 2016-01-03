package SgLeon;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(makeLeonardoProjectFile);

use strict;
use warnings;

use FileHandle;
use Cwd;
use File::Basename;
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;

use vars qw();

#
# Returns the name of the part and package to use, but expressed in a way that
# Leonardo likes.
#
# @param $part   Part, expressed in Xilinx syntax (e.g., xc2v1500).
# @param $pkg    Package, expressed in Xilinx syntax (e.g., fg680).
#
# @return Part expressed in a way that Leonardo likes.
#

sub findLeonPart() {
  my($part, $pkg) = @_;
  $part =~ tr/A-Z/a-z/;
  $part =~ s/^xc//;
  $pkg =~ tr/A-Z/a-z/;
  return $part . $pkg;
}


#
# Returns the name of the wire table to use for a given Xilinx part family.
#
# @param $family Part family (Virtex, Virtex2, or Spartan2).
# @param $speed Speed of the part.
#
# @return Name of the wire table to use for $family.
#

sub findWireTable() {
  my($family, $part, $speed) = @_;
  my($wireTablePrefix);
  if ($family =~ /^Virtex4$/i) {
    $wireTablePrefix = "xcv4-";
  }
  elsif ($family =~ /^Virtex2$/i) {
    $wireTablePrefix = "xcv2-";
  }
  elsif ($family =~ /^Virtex2P$/i) {
    $wireTablePrefix = "xcv2p-";
  }
  elsif ($family =~ /^Virtex$/i) {
    $wireTablePrefix = "xcv";
  }
  elsif ($family =~ /Virtex-?E$/i) {
    $wireTablePrefix = "xcve";
  }
  elsif ($family =~ /Spartan2$/i) {
    $wireTablePrefix = "xis";
  }
  elsif ($family =~ /Spartan2E$/i) {
    $wireTablePrefix = "xis2e";
  }
  elsif ($family =~ /Spartan3$/i) {
    $wireTablePrefix = "xis3";
  }

  # print "prefix: $wireTablePrefix\n";

  my($wireTablePart);
  if ($part =~ /(.*)[a-z](\d+)e?$/i) {
    $wireTablePart = $2;
  }
  # print "part: $wireTablePart\n";

  croak "Xilinx part family $family is not supported by Leonardo Spectrum"
    if (! defined($wireTablePart) || ! defined($wireTablePrefix));
  return $wireTablePrefix . $wireTablePart . "-" . $speed . "_avg";
}


#
# Returns the "target" that Leonardo should use for
# a given Xilinx family.
#
# @param $family Part family (Virtex, Virtex2, or Spartan2).
#
# @return Leonardo target to use with $family.
#

sub findTarget() {
  my($family) = @_;
  return "xcv4" if ($family =~ /^Virtex4$/i);
  return "xcv2" if ($family =~ /^Virtex2$/i);
  return "xcv2p" if ($family =~ /^Virtex2P$/i);
  return "xcve" if ($family =~ /^Virtex-?E$/i);
  return "xcv"  if ($family =~ /^Virtex$/i);
  return "xis2" if ($family =~ /^Spartan2$/i);
  return "xis2e" if ($family =~ /^Spartan2E$/i);
  return "xis3" if ($family =~ /^Spartan3$/i);
  croak "don't know how to find target for family $family";
}


#
# Makes a Leonardo Spectrum project file.
#
# @param %$args arguments to this function.
#
# @return Name of the project file that was generated.

sub makeLeonardoProjectFile() {
  my $args = $_[0];

  my $clkWrapper = &Sg::getAttribute("clkWrapper", $args);
  my $design = &Sg::getAttribute("design", $args);
  my $device = &Sg::getAttribute("device", $args);
  my $files = &Sg::getAttribute("files", $args);
  my $freq = &Sg::getAttribute("freq", $args);
  my $omitIobs = &Sg::getAttribute("omitIobs", $args, 1);
  my $prjFile = &Sg::getAttribute("prjFile", $args, 1);
  my $readEdif = &Sg::getAttribute("readEdif", $args, 1);
  my $fpps = &findFamilyPartPackageSpeed($device);
  defined($fpps) || croak "part name is badly formed";
  my($family, $part, $pkg, $speed) =
    ($fpps->{'family'}, $fpps->{'part'}, $fpps->{'package'}, $fpps->{'speed'});
  $speed =~ s/^-//;

  # Build list of HDL files to compile.
  my @hdlFiles = grep(/\.v(hd)?$/i, @$files);
  scalar(@hdlFiles) || croak "list of HDL files is empty";

  # Produce the first part of the script for Leonardo.
  my $d = $design;
  $d =~ s/\W/_/g;
  my $script = "leonardo_" . $d . ".tcl";
  open(LEONSCRIPT, "> $script") || croak "Could not open $script";
  binmode(LEONSCRIPT);
  my $leonPart = &findLeonPart($part, $pkg);
  my $wireTable = &findWireTable($family, $part, $speed);
  my $target = &findTarget($family);
  my $library = $target;
  print LEONSCRIPT <<eof;
set part $leonPart
set process $speed
set delay TRUE
eof
  if ($omitIobs) {
    print LEONSCRIPT <<eof;
set macro TRUE
set chip FALSE
eof
  }
  else {
    print LEONSCRIPT <<eof;
set macro FALSE
set chip TRUE
eof
  }
  print LEONSCRIPT <<eof;
set register2register $freq
set register2output $freq
set input2output $freq
set input2register $freq
set wire_table $wireTable
set novendor_constraint_file TRUE
set target $target
load_library $library
eof

  # Write lines to read coregen edif if appropriate.
  if ($readEdif) {
    my @coregenEdifFiles = grep(/_[0-9a-f]{16}\.edn$/i, @$files);
    my $file;
    foreach $file (@coregenEdifFiles) {
      print LEONSCRIPT "read_coregen $file\n";
    }
  }

  # Write portion of the script that reads VHDL/Verilog files.
  print LEONSCRIPT "set comm_socket 16000\n";
  my @vFiles = grep(/\.v$/i, @hdlFiles);
  my @vhdFiles = grep(/\.vhd$/i, @hdlFiles);
  my $fileList;
  foreach $fileList (\@vFiles, \@vhdFiles) {
    if (scalar(@$fileList)) {
      print LEONSCRIPT "read {\n";
      my $file;
      foreach $file (@$fileList) {
        print LEONSCRIPT "\"$file\"\n";
      }
      print LEONSCRIPT "}\n";
    }
  }

  # Example of setting an attribute in Leonardo project:
  # print LEONSCRIPT "#set_attribute -port data -name PAD -value IBUF_AGP\n";

  # If design contains both Verilog and VHDL, then specify top module.
  if (scalar(@vFiles) > 0 && scalar(@vhdFiles) > 0) {
    print LEONSCRIPT "set entity $clkWrapper\n";
    print LEONSCRIPT "elaborate $clkWrapper\n";
  }


  print LEONSCRIPT "optimize -ta $target -hierarchy preserve";
  if ($omitIobs) {
    print LEONSCRIPT " -macro\n";
  }
  else {
    print LEONSCRIPT " -chip\n";
  }

  $prjFile = "$clkWrapper.edf" unless (defined($prjFile));

  print LEONSCRIPT "auto_write -format edif $prjFile\n";

  # If we need to write verilog file, uncomment the next line
  # print LEONSCRIPT "auto_write -format verilog $d.vm\n";
  close(LEONSCRIPT) || croak "trouble writing $script";

  # Update synopsis to reflect file just generated.
  if ($args->{"updateSynopsis"} && -e "synopsis") {
    my $synopsis = &Sg::evaluateSynopsis();
    &Sg::updateFilesList($synopsis, [$script]);
    &Sg::writeSynopsis($synopsis);
  }

  return $script;
}


1;
