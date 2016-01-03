package SgMakeDoFiles;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(makeDoFiles);

use strict;

use FileHandle;
use Cwd;
use Carp qw(croak);
use File::Basename;
use FindBin;
use lib "$FindBin::Bin";
use Sg;

use vars qw(@flavors);


#
# Returns the array of library names needed by this design.
#
# @param %$args Tells settings to use in lieu of ones obtained from synopsis.
#
# @return Array of library names needed by this design.
#

sub findLibraries() {
  my $args = $_[0];
  my $files = &Sg::getAttribute("files", $args);
  my @vFiles = grep(/\.v$/i, @$files);
  my @vhdFiles = grep(/\.vhd$/i, @$files);
  my $fileAttributes = &Sg::getAttribute("fileAttributes", $args, 1);
  $fileAttributes = {} unless ($fileAttributes);
  my $libs = {};
  my @libsArray;
  my $file;
  foreach $file (@vFiles, @vhdFiles) {
    my $lib = $fileAttributes->{$file}->{"libName"};
    next unless ($lib && ! exists($libs->{$lib}));
    $libs->{$lib} = 1;
    push(@libsArray, $lib);
  }
  push(@libsArray, "work");
  return \@libsArray;
}


#
# Writes script header boilerplate to a given file handle.
#
# @param $handle File handle to which header bolierplate should be written.
#

sub writeHeaderBoilerplate() {
  my $handle = $_[0];
  print $handle <<headerEnd;
-- If you see error messages concerning missing libraries for
-- XilinxCoreLib, unisims, or simprims, you may not have set
-- up your ModelSim environment correctly.  See the Xilinx
-- Support Website for instructions telling how to compile
-- these libraries.

headerEnd
}


#
# Returns the name of the script corresponding to a particular flavor.
#
# @param $flavor Tells the flavor of script.
#
# @return Name of the script corresponding to a particular flavor.
#

sub scriptName {
  my $flavor = $_[0];
  return "$flavor.do";
}

#
# Returns the file suffix to use when backannotated simulation file is
# created after each step of Xilinx implementation flow.
#
# @param $flavor Tells implementation flavor used to create the sim file
#
# @return suffix string to be used
#
sub getSimFileSuffix(){
  my($flavor) = @_;
  my $suffix = $flavor eq "pn_posttranslate"? "_translate" :
                  $flavor eq "pn_postmap"? "_map" :
                  $flavor eq "pn_postpar"? "_timesim" :
                  croak "couldn't handle script flavor $flavor";
  return $suffix;
}

#
# Returns the netgen sub directory containing backannotated sim file based
# on the flavor of Xilinx implementation flow
#
# @param $flavor Tells implementation flavor used to create the sim file
#
# @return netgen sub dir name containing the sim model
#
sub getNetgenSubdirName(){
  my($flavor) = @_;
  my $netgenDir = $flavor eq "pn_posttranslate"? "translate" :
                  $flavor eq "pn_postmap"? "map" :
                  $flavor eq "pn_postpar"? "par" :
                  croak "couldn't handle script flavor $flavor";
  return $netgenDir;
}

#
# Returns the name of the simulation file to be used for a particular
# flavor of Xilinx implementation flow
#
# @param $flavor Tells the flavor of script we are generating.
# @param $file Name of the original clock wrapper for the design.
# @param $args Arguments to use in lieu of setting from synopsis.
#
# @return simulation file name
#
sub getSimFileName(){
  my($flavor, $file, $args) = @_;
  return $file if ($flavor eq "pn_behavioral");
  my $iseVersion = &Sg::getAttribute('ise_version', $args);
  my $netgenDir = &getNetgenSubdirName($flavor);
  my $suffix = &getSimFileSuffix($flavor);
  $file =~ /^(.*)(\.v(hd)?)$/i;
  my($head, $tail) = ($1, $2);
  my $newFile = "./netgen/$netgenDir/" . "$head$suffix$tail";
  return $newFile;
}

sub updateSimFile(){
  my($handle,$flavor, $file, $args) = @_;
  my $newFile = &getSimFileName($flavor, $file, $args);
  return $newFile;
}

#
# Generates an ISim project file.
#
# @param %$args Tells settings to use in lieu of ones from synopsis.
#

sub generateISimProject {
  my $args = $_[0];
  my $script = $_[1];

  unlink $script;
  ! -e $script || croak "couldn't remove old $script";

  my $handle = new FileHandle;
  open($handle, "> $script") || croak "couldn't write $script";
  binmode $handle;

  my $files = &Sg::getAttribute('files', $args);
  my $fileAttributes = &Sg::getAttribute("fileAttributes", $args, 1);
  $fileAttributes = {} unless ($fileAttributes);

  # Write vhdl/verilog lines.
  my $file;
  my $hasVerilog = 0;
  foreach $file (@$files) {
    my $hdlKind;
    if ($file =~ /\.v$/) {
      $hdlKind = 'verilog';
      $hasVerilog = 1;
    }
    elsif ($file =~ /\.vhd$/) {
      $hdlKind = 'vhdl';
    }
    else {
      next;
    }
    my $lib = $fileAttributes->{$file}->{'libName'};
    $lib = 'work' unless defined($lib);
    print $handle "$hdlKind $lib $file\n";
  }
  if ($hasVerilog) {
    print $handle "verilog work \$XILINX/verilog/src/glbl.v\n";
  }

  close($handle) || croak "trouble writing $script";
}

#
# Generates an ISim simulation Tcl script.
#
# @param %$args Tells settings to use in lieu of ones obtained from synopsis.
#

sub generateISimScript {
  my $args = $_[0];
  my $script = $_[1];

  unlink $script;
  ! -e $script || croak "couldn't remove old $script";

  my $handle = new FileHandle;
  open($handle, "> $script") || croak "couldn't write $script";
  binmode $handle;

  # Write HDL-appropriate vsim line.
  my $testbench = &Sg::getAttribute('testbench', $args);
  my $clkWrapper = &Sg::getAttribute('clkWrapper', $args);
  my $vsimTime = &Sg::getAttribute('vsimtime', $args);
  print $handle "saif open -file ${clkWrapper}.saif\n";
  print $handle "run $vsimTime\n";
  print $handle "saif close\n";
  print $handle "quit -f\n";
  close($handle) || croak "trouble writing $script";
}

#
# Generates a Project Navigator .do script.
#
# @param $flavor Tells flavor of script to generate.
# @param %$args Tells settings to use in lieu of ones obtained from synopsis.
#

sub generatePnScript {
  my($flavor, $args) = @_;

  my $script = &scriptName($flavor);
  unlink $script;
  ! -e $script || croak "couldn't remove old $script";

  # Write boilerplate header comment.
  my $handle = new FileHandle;
  open($handle, "> $script") || croak "couldn't write $script";
  binmode $handle;
  &writeHeaderBoilerplate($handle);

  my $hdlKind = &Sg::getAttribute('hdlKind', $args);
  my $files = &Sg::getAttribute('files', $args);
  my $sysgen = &Sg::getAttribute('sysgen', $args);
  my @vFiles = grep(/\.v$/i, @$files);
  my @vhdFiles = grep(/\.vhd$/i, @$files);

  # Write vlib lines
  my $libs = &findLibraries($args);
  my $lib;
  if ($flavor eq "pn_behavioral") {
    foreach $lib (@$libs) {
      print $handle "vlib $lib\n";
    }
  }
  else {
    print $handle "vlib work\n";
  }
  print $handle "\n";

  # Write line to compile glbl.v if appropriate.
  my $xilinx = &Sg::getAttribute('xilinx', $args);
  my $clkWrapperFile = &Sg::getAttribute('clkWrapperFile', $args);
  # want this if there is any verilog files at all.
  if (($hdlKind eq "verilog") || (@vFiles > 0)) {
      print $handle "vlog $xilinx/verilog/src/glbl.v\n" ;
  }

  # Write vcom/vlog lines.
  my ($file, $newFile);
  if ($flavor eq "pn_behavioral") {
    my $fileAttributes = &Sg::getAttribute("fileAttributes", $args, 1);
    $fileAttributes = {} unless ($fileAttributes);
    foreach $file (@vFiles) {
      $lib = $fileAttributes->{$file}->{"libName"};
      my $work = $lib? "-work $lib" : "";
      # Do not insert clock probe if not using multiple clock wrapper
      if ($file ne $clkWrapperFile) {
        print $handle "vlog $work $file\n";
      }
      else {
        $newFile = &updateSimFile($handle, $flavor, $file, $args);
        print $handle "vlog $work $newFile\n";
      }
    }
    foreach $file (@vhdFiles) {
      $lib = $fileAttributes->{$file}->{"libName"};
      my $work = $lib? "-work $lib" : "";
      if ($file ne $clkWrapperFile) {
        print $handle "vcom -93 $work -nowarn 1 $file\n";
      }
      else {
        $newFile =&updateSimFile($handle, $flavor, $file, $args);
        print $handle "vcom $work -nowarn 1 $newFile\n";
      }
    }
  }
  else {
      if ($hdlKind eq 'vhdl') {
          print $handle "vcom -93 -nowarn 1 \"$sysgen/hdl/conv_pkg.vhd\"\n";
          $file = $vhdFiles[-2];
          $newFile = $file;
          $newFile = &updateSimFile($handle, $flavor, $file, $args);
          print $handle "vcom -nowarn 1 $newFile\n";
          print $handle "vcom -93 -nowarn 1 " . $vhdFiles[-1] . "\n";
      }
      else {
          $file = $vFiles[-2];
          $newFile = $file;
          $newFile = &updateSimFile($handle, $flavor, $file, $args);
          print $handle "vlog $newFile\n";
          print $handle "vlog " . $vFiles[-1] . "\n";
      }
  }

  # Write leading vsim line
  my $sdf = "";
  if ($flavor eq "pn_postpar") {
    $sdf = $newFile;
    $sdf =~ s/\.vhd$|\.v$//;
    $sdf = "-sdftyp /sysgen_dut=$sdf.sdf";
  }
  my $testbench = &Sg::getAttribute('testbench', $args);
  if ($hdlKind eq "verilog") {
      my $vLibs = " -L UNISIMS_VER -L SIMPRIMS_VER -L XILINXCORELIB_VER work.glbl";
      print $handle "vsim +nowarnTFMPC -L " . join(" -L ", @$libs) . $vLibs . " -t ps $sdf $testbench\n";
  }
  else {
    my $vLibs;
    # if use any verilog files add: -L UNISIMS_VER -L SIMPRIMS_VER -L XILINXCORELIB_VER work.glbl
    if (@vFiles > 0) {
        $vLibs = " -L UNISIMS_VER -L SIMPRIMS_VER -L XILINXCORELIB_VER work.glbl";
    }
    print $handle "vsim -L " . join(" -L ", @$libs) . $vLibs . " -t ps $sdf $testbench\n";
  }

  # Write trailing vsim lines
  my $quit = &Sg::getAttribute('quit', $args, 1);
  my $vsimTime = &Sg::getAttribute('vsimtime', $args);
  $vsimTime .= " ns" unless ($vsimTime =~ / ns$/ || $vsimTime =~ / ps$/);
  print $handle "view wave\n";
  print $handle "add wave *\n";
  print $handle "view structure\n";
  print $handle "view signals\n";

  # turn off the warnings because at the start of the simulation,
  # everything is X
  if ($flavor eq "pn_behavioral" && $hdlKind eq "vhdl") {
     print $handle "set NumericStdNoWarnings 1\n";
     print $handle "run 0\n";
     print $handle "set NumericStdNoWarnings 0\n";
  }
  #Add additional run time if DCM is used
  if (defined(&Sg::getAttribute('clock_wrapper',$args))){
    my $clock_wrapper = &Sg::getAttribute('clock_wrapper', $args);
    my $base_system_period_hardware = &Sg::getAttribute('base_system_period_hardware',$args);
    if ( $clock_wrapper eq "Clock Generator(DCM)" ){
      print $handle "#Add additional cycles for DCM initialization routine\n";
      my $additional_cycles = $base_system_period_hardware * 100.0;
      print $handle "run $additional_cycles ns\n";
    }
  }
  print $handle "run $vsimTime\n";
  print $handle "quit -f\n" if ($quit);

  close($handle) || croak "trouble writing $script";
}


#
# Generates an ordinary vcom.do script.
#
# @param %$args Tells settings to use in lieu of ones from synopsis.
#

sub generateVcomScript {
  my $args = $_[0];

  my $script = &scriptName("vcom");
  unlink $script;
  ! -e $script || croak "couldn't remove old $script";

  # Write boilerplate header comment.
  my $handle = new FileHandle;
  open($handle, "> $script") || croak "couldn't write $script";
  binmode $handle;
  &writeHeaderBoilerplate($handle);

  # Write vlib lines
  my $files = &Sg::getAttribute('files', $args);
  my $fileAttributes = &Sg::getAttribute("fileAttributes", $args, 1);
  $fileAttributes = {} unless ($fileAttributes);
  my @vFiles = grep(/\.v$/i, @$files);
  my @vhdFiles = grep(/\.vhd$/i, @$files);
  my $libs = &findLibraries($args);
  my $lib;
  foreach $lib (@$libs) {
    print $handle "vlib $lib\n";
  }
  print $handle "\n";

  # Write line to compile glbl.v if appropriate.
  my $hdlKind = &Sg::getAttribute('hdlKind', $args);
  my $xilinx = &Sg::getAttribute('xilinx', $args);
  # want this if any verilog is being used, not just the hdlKind of the output
  # Check the size of @vFiles
  if (($hdlKind eq "verilog") || (@vFiles > 0)) {
      print $handle "vlog $xilinx/verilog/src/glbl.v\n" ;
  }

  # Write vcom/vlog lines.
  my $file;
  foreach $file (@vFiles) {
    $lib = $fileAttributes->{$file}->{"libName"};
    my $work = $lib? "-work $lib" : "";
    print $handle "vlog $work $file\n";
  }
  foreach $file (@vhdFiles) {
    $lib = $fileAttributes->{$file}->{"libName"};
    my $work = $lib? "-work $lib" : "";
    print $handle "vcom $work -nowarn 1 $file\n";
  }

  close($handle) || croak "trouble writing $script";
}


#
# Generates an ordinary vsim.do script.
#
# @param %$args Tells settings to use in lieu of ones obtained from synopsis.
#

sub generateVsimScript {
  my $args = $_[0];

  my $script = &scriptName("vsim");
  unlink $script;
  ! -e $script || croak "couldn't remove old $script";

  # Write boilerplate header comment.
  my $handle = new FileHandle;
  open($handle, "> $script") || croak "couldn't write $script";
  binmode $handle;
  &writeHeaderBoilerplate($handle);

  # Write HDL-appropriate vsim line.
  my $hdlKind = &Sg::getAttribute('hdlKind', $args);
  my $testbench = &Sg::getAttribute('testbench', $args);
  my $vsimTime = &Sg::getAttribute('vsimtime', $args);
  my $quit = &Sg::getAttribute('quit', $args, 1);
  my $libs = &findLibraries($args);
  if ($hdlKind eq "verilog") {
      my $vLibs = " -L UNISIMS_VER -L SIMPRIMS_VER -L XILINXCORELIB_VER work.glbl";
      print $handle "vsim +nowarnTFMPC -L " . join(" -L ", @$libs) . $vLibs . " -t ps $testbench\n";
  }
  else {
      my $vLibs;
      my $files = &Sg::getAttribute('files', $args);
      # if use any verilog files add: -L UNISIMS_VER -L SIMPRIMS_VER -L XILINXCORELIB_VER work.glbl
      if (grep(/\.v$/i, @$files)) {
          $vLibs = " -L UNISIMS_VER -L SIMPRIMS_VER -L XILINXCORELIB_VER work.glbl";
      }
      print $handle "vsim -L " . join(" -L ", @$libs) . $vLibs . " -t ps $testbench\n";
  }

  # turn off the warnings because at the start of the simulation,
  # everything is X
  if ($hdlKind eq "vhdl") {
    print $handle "set NumericStdNoWarnings 1\n";
    print $handle "run 0\n";
    print $handle "set NumericStdNoWarnings 0\n";
  }
  #Add additional run time if DCM is used
  if (defined(&Sg::getAttribute('clock_wrapper',$args))){
    my $clock_wrapper = &Sg::getAttribute('clock_wrapper', $args);
    my $base_system_period_hardware = &Sg::getAttribute('base_system_period_hardware',$args);
    if ( $clock_wrapper eq "Clock Generator(DCM)" ){
      print $handle "#Add additional cycles for DCM initialization routine\n";
      my $additional_cycles = $base_system_period_hardware * 100.0;
      print $handle "run $additional_cycles ns\n";
    }
  }
  print $handle "run $vsimTime\n";
  print $handle "quit -f\n" if ($quit);
  close($handle) || croak "trouble writing $script";
}

#
# Checks if the multrate implementation is using clock enables
#
# @param $args Arguments from command line.
# @return true if uses clock enables
#
sub isClockEnabledWrapper{
  my $args = $_[0];
  if (defined(&Sg::getAttribute('clock_wrapper',$args))){
    my $clock_wrapper = &Sg::getAttribute('clock_wrapper', $args);
    if ( $clock_wrapper eq "Clock Enables" ){
      return  1;
    }
    return 0;
  }
  return 1;
}

#
# Writes the ModelTech .do files that Sysgen designs need when running under
# Project Navigator.  The files are the following:
#
# pn_behavioral.do    - Runs behavioral (i.e., pre-mapping) simulation.
# pn_posttranslate.do - Runs post-ngdbuild simulation without timing info.
# pn_postmap.do       - Runs post-mapping simulation with timing info.
# pn_postpar.do       - Runs post-placement/routing simulation.
#
# In addition, this member writes scripts for compiling and simulating outside
# Project Navigator.  The files are the following:
#
# vcom.do             - Compiles VHDL/Verilog without simulating.
# vsim.do             - Runs behavioral (i.e., pre-mapping) simulation.
#
# This member operates on the "synopsis" file that describes Sysgen results,
# and must be run from the directory that contains the file.
#
# @param $args Arguments from command line.  $args->{"quit"} tells whether
#        this member should append "quit -f" lines to the end of the vsim.do
#        script.
#

sub makeDoFiles() {
  my $args = $_[0];

  @flavors = ("pn_behavioral", "pn_posttranslate", "pn_postmap",
    "pn_postpar", "vcom", "vsim");

  # Determine which .do files to write.  If we did not make a testbench,
  # then the only file to write is vcom.do.
  my $testbench = &Sg::getAttribute('testbench', $args, 1);
  if ($testbench) {
    @flavors = ("pn_behavioral", "pn_posttranslate", "pn_postmap",
                "pn_postpar", "vcom", "vsim");
  }
  else {
    @flavors = ("vcom");
  }

  # Construct local array containing names of HDL files.
  my $files = &Sg::getAttribute('files', $args);
  my @hdlFiles = grep(/\.v(hd)?$/i, @$files);

  # Generate each flavor of script.
  my $flavor;
  foreach $flavor (@flavors) {
    if ($flavor =~ /^pn/) {
      &generatePnScript($flavor, $args);
    }
    elsif ($flavor eq "vcom") {
      &generateVcomScript();
    }
    elsif ($flavor eq "vsim") {
      &generateVsimScript($args);
    }
    else {
      croak "don't know how to make $flavor script";
    }
  }

  my $design = &Sg::getAttribute('design', $args);
  # Generate ISim project and simulation Tcl file
  my $isimprj = "isim_${design}.prj";
  &generateISimProject($args, $isimprj);
  # Create ISim simulation Tcl file only if testbench was created.
  my $isimtcl = "isim_${design}_run.tcl";
  if ($testbench) {
      &generateISimScript($args, $isimtcl);
  }

  # Update synopsis to reflect files just generated.
  if ($args->{"updateSynopsis"} && -e "synopsis") {
    my $generatedFiles = [];
    foreach $flavor (@flavors) { push(@$generatedFiles, &scriptName($flavor)); }
    push(@$generatedFiles, $isimprj);
    if ($testbench) {
        push(@$generatedFiles, $isimtcl);
    }
    my $synopsis = &Sg::evaluateSynopsis();
    &Sg::updateFilesList($synopsis, $generatedFiles);
    &Sg::writeSynopsis($synopsis);
  }

  return undef;
}


1;
