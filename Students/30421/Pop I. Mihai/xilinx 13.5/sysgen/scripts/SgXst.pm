package SgXst;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(makeXstFiles);

use strict;
use warnings;

use FileHandle;
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;

use vars qw();

#
# Makes XST .prj and .scr files.
#
# @param %$args Arguments to this function.
#   $args->{"design"} tells name to use for design.
#   $args->{"clkWrapper"} tells name to use for clock wrapper.
#   $args->{"device"} tells full part name.
#   $args->{"fileList"} tells list of files to compile.
#   $args->{"omitIobs"} tells whether to add IOBs.
#   $args->{"xcfFile"} tells name of companion .xcf constraint file.
#   $args->{"hdlKind"} tells kind of HDL.
#
# @return Array containing the names of the .prj and .scr files that were
#         generated.

sub makeXstFiles() {
  my $args = $_[0];

  # Break out individual keys/values from %$args hash table.  Keys not
  # found in the hash table are read from the synopsis.
  my $clkWrapper = &Sg::getAttribute("clkWrapper", $args);
  my $design = &Sg::getAttribute("design", $args);
# my $fileList = &Sg::getAttribute("fileList", $args);
  my $files = &Sg::getAttribute("files", $args);
  my $device = &Sg::getAttribute("device", $args);
  my $fpps = &findFamilyPartPackageSpeedFromAttributes($args);
  defined($fpps) || croak "part name is badly formed";
  my($family, $part, $pkg, $speed) =
    ($fpps->{'family'}, $fpps->{'part'}, $fpps->{'package'}, $fpps->{'speed'});
  my $hdlKind = &Sg::getAttribute("hdlKind", $args);
  my $omitIobs = &Sg::getAttribute("omitIobs", $args);
  my $omitCores = &Sg::getAttribute("omitCores", $args, 1);
  my $writeConstraints = &Sg::getAttribute("writeConstraints", $args, 1);
  $writeConstraints = 1 unless defined($writeConstraints);
  my $xcfFile = &Sg::getAttribute("xcfFile", $args);
  my $fileAttributes = &Sg::getAttribute("fileAttributes", $args, 1);
  my $disableRetiming = &Sg::getAttribute("disableRetiming", $args, 1);
  #Added by arving for resource estimator
  #Consider moving this information to synopsis file later
  my $iob = "Auto";
  if ( exists($args->{"iob"}) ){
    $iob = $args->{"iob"};
  }
  my $topLevel = $clkWrapper;
  if ( exists($args->{"customTopLevel"}) ) {
    $topLevel = $args->{"customTopLevel"};
  }
  # Build lists of HDL files to compile.
  my @vhdlFiles = grep(/\.vhd$/i, @$files);
  my @verFiles = grep(/\.v$/i, @$files);

  # Set name of xcf file if it wasn't set explicitly, then verify xcf file is
  # a readable plain file whose name ends in .xcf.
  $xcfFile = "$clkWrapper.xcf" if (! defined($xcfFile) && -e "$clkWrapper.xcf");
  if (defined($xcfFile)) {
    $xcfFile =~ /\.xcf$/ || croak "name of xcf file must end with .xcf";
    -e $xcfFile || croak "there is no $xcfFile";
    -f $xcfFile || croak "$xcfFile is not a plain file";
    -r $xcfFile || croak "$xcfFile is not readable";
  }

  # If necessary, deduce name to assign to .prj file.
  my $d = $design;
  $d =~ s/\W/_/g;
  my $xstDesign = "xst_" . $d;

  # Determine what bus delimiter to use.
  my $busDelimiter = $hdlKind eq "verilog"? "[]" : "()";

  # Write xst project file in working directory.
  my $prjFile = $xstDesign . ".prj";
  my(@generatedFiles);
  push(@generatedFiles, $prjFile);
  my $prjHandle = new FileHandle;
  open($prjHandle, "> $prjFile") || croak "couldn't open $prjFile";
  binmode $prjHandle;
  my $file;
  $fileAttributes = {} unless ($fileAttributes);
  foreach $file (@verFiles) {
    my $lib = $fileAttributes->{$file}->{"libName"};
    $lib = "work" unless ($lib);
    $file =~ s/\\/\//g;
    print $prjHandle "verilog $lib $file\n";
  }
  foreach $file (@vhdlFiles) {
    my $lib = $fileAttributes->{$file}->{"libName"};
    $lib = "work" unless ($lib);
    $file =~ s/\\/\//g;
    print $prjHandle "vhdl $lib $file\n";
  }
  close($prjHandle) || croak "trouble writing $prjFile";

  # Write xst script in working directory.
  my $scrFile = $xstDesign . ".scr";
  push(@generatedFiles, $scrFile);
  my $scrHandle = new FileHandle;
  open($scrHandle, "> $scrFile") || croak "couldn't open $scrFile";
  binmode $scrHandle;
  my $iobuf = $omitIobs? "NO" : "YES";
  #The following line is commented off till a fix can be obtained from XST
  #my $registerBalancing = $disableRetiming? "no" : "yes";
  my $registerBalancing = "no";

  my $cmd = "run -ifn $prjFile -ifmt mixed -ofn $clkWrapper.ngc " .
    "-ofmt NGC -p $device -ent $topLevel -keep_hierarchy NO -iobuf " .
    "$iobuf -bus_delimiter $busDelimiter -top $topLevel " .
    "-hierarchy_separator / -report_timing_constraint_problems warning " .
    "-register_balancing $registerBalancing " .
    "-iob $iob";
  if ( &Sg::isNewXSTParser($family) ) {
  #  $cmd = $cmd . " -change_error_to_warning \"HDLCompiler:756\"";
  }
  #Temporary workaround due to L.24 issue
  $cmd .= " -uc $xcfFile -write_timing_constraints yes" if (defined($xcfFile) && $writeConstraints);
  $cmd .= " -read_cores no" if ($omitCores);
  print $scrHandle "$cmd\n";
  close($scrHandle) || croak "trouble writing $scrFile";

  # Update synopsis to reflect file(s) just generated.
  if ($args->{"updateSynopsis"} && -e "synopsis") {
    my $synopsis = &Sg::evaluateSynopsis();
    &Sg::updateFilesList($synopsis, \@generatedFiles);
    &Sg::writeSynopsis($synopsis);
  }
  return [$prjFile, $scrFile];
}


1;
