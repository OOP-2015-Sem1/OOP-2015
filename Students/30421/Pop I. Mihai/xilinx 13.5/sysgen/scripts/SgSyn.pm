package SgSyn;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(makeSynplicityProjectFile);

use strict;
use warnings;

use FileHandle;
use Cwd;
use File::Basename;
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;
use SgEnv;

use vars qw();


#
# Returns the spelling Synplify wants for a Xilinx part family.
#
# @param $family Family whose Synplify spelling should be returned.
#
# @return Synplify spelling of $family.
#

sub synFamilySpelling {
  my $family = $_[0];
  return "virtex-e" if ($family =~ /^virtexe$/i);
  return "qprovirtex4" if ($family =~ /^qvirtex4$/i);
  return "qprorvirtex4" if ($family =~ /^qrvirtex4$/i);
  return "Virtex6-Lower-Power" if ($family =~ /^virtex6l$/i);
  return "QProVirtex5" if ($family =~ /^qvirtex5$/i);
  return "SPARTAN-3A-DSP" if ($family =~ /^spartan3adsp$/i);
  return "Spartan6-Lower-Power" if ($family =~ /^spartan6l$/i);
  return "ASpartan6" if ($family =~ /^aspartan6$/i);
  return $family;
}


#
# Makes a Synplify project file.
#
# @param %$args arguments to this function.
#
# @return array containing the names of the sdc file and the project
#         file that were generated.

sub makeSynplicityProjectFile() {
  my $args = $_[0];

  my $clkWrapper = &Sg::getAttribute("clkWrapper", $args);
  my $design = &Sg::getAttribute("design", $args);
  my $device = &Sg::getAttribute("device", $args);
  my $files = &Sg::getAttribute("files", $args);
  my $freq = &Sg::getAttribute("freq", $args, 1);
  my $omitIobs = &Sg::getAttribute("omitIobs", $args, 1);
  my $packRegInIobs = &Sg::getAttribute("packRegInIobs", $args, 1);
  my $writeConstraints = &Sg::getAttribute("writeConstraints", $args, 1);
  $writeConstraints = 1 unless defined($writeConstraints);
  my $prjFile = &Sg::getAttribute("prjFile", $args, 1);
  my $fileAttributes = &Sg::getAttribute("fileAttributes", $args, 1);
  my $sdcFile = &Sg::getAttribute("sdcFile", $args, 1); 
  my $synthesisTool = &Sg::getAttribute("synthesisTool", $args);
  #my $fpps = &findFamilyPartPackageSpeed($device);
  my $fpps = &findFamilyPartPackageSpeedFromAttributes($args);
  defined($fpps) || croak "part name is badly formed";
  my($family, $part, $pkg, $speed) =
    ($fpps->{'family'}, $fpps->{'part'}, $fpps->{'package'}, $fpps->{'speed'});
  $speed =~ s/^/-/ unless ($speed =~ /^-/);

  #If a custom top level file is provided then use that
  my $topLevel = $clkWrapper;
  if ( exists($args->{"customTopLevel"}) ) {
    $topLevel = $args->{"customTopLevel"};
  }

  # Build list of HDL files to compile.
  my @vhdFiles = grep(/\.vhd$/i, @$files);
  my @vFiles = grep(/\.v$/i, @$files);
  my @ednFiles = grep(/\.edn$/i, @$files);
  scalar(@vhdFiles) || scalar(@vFiles) || croak "list of HDL files is empty";

  # If necessary, deduce name to assign to generated .prj file.
  my $d = $design;
  $d =~ s/\W/_/g;
  $prjFile = "synplify_" . $d . ".prj" unless ($prjFile);

  # Open the file named $prjFile in the working directory.
  my @generatedFiles;
  my $handle = new FileHandle;
  open($handle, "> $prjFile") || croak "couldn't open $prjFile";
  binmode $handle;
  push(@generatedFiles, $prjFile);

  # Write the add_file line for the sdc file.
  #if custom top level is used then do not use the sdcfile
  if ( $topLevel eq $clkWrapper ){
    print $handle "add_file -constraint \"$sdcFile\"\n" if ($sdcFile);
  }

  # Write "add_file -vhdl -lib work <name of VHDL file>" and/or "add_file
  # -verilog -lib work <name of verilog file>" lines.  Note: it is important to
  # add Verilog files before VHDL because Verilog compilation order is
  # forgiving but VHDL is not.
  my $file;
  $fileAttributes = {} unless ($fileAttributes);
  foreach $file (@vFiles) {
    my $lib = $fileAttributes->{$file}->{"libName"};
    $lib = "work" unless ($lib);
    $file =~ s/\\/\//g;
    print $handle "add_file -verilog -lib $lib \"$file\"\n";
  }
  foreach $file (@vhdFiles) {
    my $lib = $fileAttributes->{$file}->{"libName"};
    $lib = "work" unless ($lib);
    $file =~ s/\\/\//g;
    print $handle "add_file -vhdl -lib $lib \"$file\"\n";
  }

  # Write "add_file -edif <name of edif file>" lines for plaintext edif files.
  # We omit binary files because they are encrypted, which means Synplify
  # can't handle them. Also we add the files only for Synplify pro
  if (defined($synthesisTool)) {
    if ($synthesisTool eq "Synplify Pro") {
      foreach $file (@ednFiles) {
        next unless -T $file;
        $file =~ s/\\/\//g;
        print $handle "add_file -edif \"$file\"\n";
      }
    }
  }

  # If there is a Verilog file, a <family>.v file must also be compiled.
  # This file is located in the synplicity directory.
  if (scalar(@vFiles)) {
    my $synPath = &SgEnv::getSynplifyRootPath();
    defined($synPath) || croak "Synplify is not in your path";
    my $familyV = $synPath;
    my $familyVName = $family;
    $familyVName =~ tr/A-Z/a-z/;
    #Change made for Synplify C2009.03
    #if ($familyVName eq "spartan2" || $familyVName eq "spartan2e") {
    $familyV .= "/lib/xilinx/unisim.v";
    #}
    #else {
    #  $familyV .= "/lib/xilinx/$familyVName" . ".v";
    #}
    if ( -e $familyV ){
      print $handle "add_file -verilog \"$familyV\"\n";
    }
  }

  # Write "project -result_file "<result file>.edf" line
  print $handle "\n";
  print $handle "project -result_file \"$clkWrapper.edf\"\n";

  # Find frequency if necessary.
  if (! $freq) {
    my $systemClockPeriod = &Sg::getAttribute("systemClockPeriod", $args);
    $freq = 1000.0 / $systemClockPeriod;
  }

  # Fanout limit is a function of frequency.
  my $fanoutLimit = 50000;
#  my $fanoutLimit;
#  if ($freq eq 'auto') {
#    $fanoutLimit = 50;
#  }
#  elsif ($freq < 150) {
#    $fanoutLimit = 1000;
#  }
#  elsif ($freq < 300) {
#    $fanoutLimit = 100;
#  }
#  else {
#    $fanoutLimit = 50;
#  }

  # Write "set option" lines
  print $handle "\n";
  print $handle "set_option -top_module $topLevel\n";
  print $handle "set_option -technology " . &synFamilySpelling($family) . "\n";
  print $handle "set_option -part $part\n";
  print $handle "set_option -package $pkg\n";
  print $handle "set_option -speed_grade $speed\n";
  print $handle "set_option -default_enum_encoding sequential\n";
  print $handle "set_option -symbolic_fsm_compiler false\n";
  print $handle "set_option -resource_sharing true\n";
  print $handle "set_option -frequency $freq\n";
  print $handle "set_option -auto_constrain_io 0\n";
  print $handle "set_option -fanout_limit $fanoutLimit\n";
  print $handle "set_option -maxfan_hard false\n";
  print $handle "set_option -pipe 1\n";
  print $handle "set_option -retiming 1\n";
  print $handle "set_option -disable_io_insertion " .
    ($omitIobs? "true" : "false") . "\n";
  print $handle "set_option -write_verilog false\n";
  print $handle "set_option -write_vhdl false\n";
  print $handle "set_option -write_apr_constraint " .
    (($sdcFile && $writeConstraints) ? "true" : "false") . "\n";
  print $handle "set_option -vlog_std v2001\n" if (scalar(@vFiles));
  # print $handle "set_option -mti_root \"\"\n";
  close($handle) || croak "trouble writing $prjFile";

  # Set the global syn_noclockbuf attribute when IOBs are
  # omitted.  This attribute is appended to the Synplify
  # SDC file, if there is one.
  if ($sdcFile && -e $sdcFile) {
      open(F, ">> $sdcFile") || croak "couldn't open $sdcFile";
      if ($omitIobs) {
          print F "define_global_attribute syn_noclockbuf 1\n";
      }
      if ($packRegInIobs) {
          print F "define_global_attribute syn_useioff 1\n";
      }
      close(F) || croak "trouble writing $sdcFile";
  }

  # Update synopsis to reflect file(s) just generated.
  if ($args->{"updateSynopsis"} && -e "synopsis") {
    my $synopsis = &Sg::evaluateSynopsis();
    &Sg::updateFilesList($synopsis, \@generatedFiles);
    &Sg::writeSynopsis($synopsis);
  }

  return $prjFile;
}


1;
