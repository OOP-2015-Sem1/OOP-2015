package SgPrecision;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(makePrecisionProjectFile);

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
# Returns a part family, expressed in the way that Precision would like to
# see it.
#
# @param $family Family to translate.
#
# @return Family, expressed in the way that Precision would like to see it.
#

sub precisionFamily() {
  my $family = $_[0];
  return "SPARTAN2"   if ($family eq "spartan2");
  return "SPARTAN2E"   if ($family eq "spartan2e");
  return "SPARTAN3"   if ($family eq "spartan3");
  return "VIRTEX"   if ($family eq "virtex");
  return "VIRTEX-E"   if ($family eq "virtexe");
  return "VIRTEX-II"   if ($family eq "virtex2");
  return "VIRTEX-IV"   if ($family eq "virtex4");
  return "{VIRTEX-II Pro}" if ($family eq "virtex2p");
  croak "don't know how Precision represents family $family";
}


#
# Returns a part, expressed in the way Precision would like to see it.
#
# @param $part Part, expressed as Systen does it.
# @param $pkg Package, expressed as Systen does it.
#
# @return Part expressed in the way Precision would like to see it.
#

sub precisionPart() {
  my($part, $pkg) = @_;
  $part =~ s/^xc//;
  return "$part$pkg";
}


#
# Calculates frequency from period (or vice-versa) and returns the results.
#
# @param $freq Frequency, or undef if not known.
# @param $period Period, or undef if not known.
#
# @return Frequency and period.
#

sub resolveFreqPeriod {
  my($freq, $period) = @_;

  return ($freq, $period) if ($freq && $period);
  return ($freq, 1000.0 / $freq) if ($freq);
  return (1000.0 / $period, $period) if ($period);
  croak "expected period or frequency to be specified"
}


#
# Makes a Precision Synthesis project file.
#
# @param %$args arguments to this function.
#
# @return Name of the project file that was generated.

sub makePrecisionProjectFile() {
  my $args = $_[0];

  my $clkWrapper = &Sg::getAttribute("clkWrapper", $args);
  my $design = &Sg::getAttribute("design", $args);
  my $device = &Sg::getAttribute("device", $args);
  my $files = &Sg::getAttribute("files", $args);
  my $freq = &Sg::getAttribute("freq", $args, 1);
  my $period = &Sg::getAttribute("systemClockPeriod", $args, 1);
  my $directory = &Sg::getAttribute("directory", $args);

  ($freq, $period) = &resolveFreqPeriod($freq, $period);
  my $fpps = &findFamilyPartPackageSpeed($device);
  defined($fpps) || croak "part name is badly formed";
  my $family = &precisionFamily($fpps->{'family'});
  my $part = &precisionPart($fpps->{'part'}, $fpps->{'package'});
  my $speed = $fpps->{'speed'};
  $speed =~ s/^-//;

  # Build lists of files to compile.
  my @vhdFiles = grep(/\.vhd$/i, @$files);
  my @vFiles = grep(/\.v$/i, @$files);
  scalar(@vhdFiles) || scalar(@vFiles) || croak "list of HDL files is empty";
  my @ednFiles = grep(/\.edn$/i, @$files);

  # Write script header.
  my $d = $design;
  $d =~ s/\W/_/g;
  my $script = "precision_" . $d . ".tcl";
  open(SCRIPT, "> $script") || croak "Could not open $script";
  binmode(SCRIPT);
  $directory =~ s/\\/\//g;
  print SCRIPT<<EOT;
set PROJECT_DIR $directory
set_input_dir \$PROJECT_DIR

if {[file exists "\$PROJECT_DIR/precision_proj.psp"]} {
  open_project "\$PROJECT_DIR/precision_proj.psp"
} else {
  new_project -name "precision_proj" -folder "\$PROJECT_DIR" -createimpl_name "precision_proj_impl_1"
}

EOT

  # Build list of files to compile.
  my $file;
  foreach $file (@vFiles, @vhdFiles, @ednFiles) {
    print SCRIPT "add_input_file $file\n";
  }

  print SCRIPT<<EOT;
setup_design -frequency=$freq
setup_design -input_delay=$period
setup_design -output_delay=$period

setup_design -retiming
setup_design -frontend_2004
setup_design -automap_work
setup_design -error_design_contention
setup_design -use_safe_fsm

setup_design -design $clkWrapper
setup_design -manufacturer Xilinx -family $family -part $part -speed $speed

EOT

  print SCRIPT<<EOT;
compile
synthesize
EOT

  close(SCRIPT) || croak "trouble writing $script";

  # Update synopsis to reflect file just generated.
  if ($args->{"updateSynopsis"} && -e "synopsis") {
    my $synopsis = &Sg::evaluateSynopsis();
    &Sg::updateFilesList($synopsis, [$script]);
    &Sg::writeSynopsis($synopsis);
  }

  return $script;
}


1;
