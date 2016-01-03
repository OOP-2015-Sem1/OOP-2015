
package SgGenerateCores;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(saveXcoSequence buildCoreName wrapup augmentXcoInstrs
             generateCores);

use strict;

use Digest::MD5;
use File::Temp qw(tempdir);
use File::Basename;
use File::Path;
use File::DosGlob;
use Carp qw(croak);
use Cwd;
use FindBin;
use lib "$FindBin::Bin";
use Sg;
use SgEnv;

use vars qw($tmpDir $xcoInstrs $xcoInstrsFile $xcoInstrsCoreFile $coregenTmpDir $LOCK_SH
  $LOCK_EX $LOCK_NB $LOCK_UN $RESULTS);

use vars qw($validlog);

$LOCK_SH = 1; # Avoids Perl warning about variable used only once.
$LOCK_EX = 2;
$LOCK_NB = 4;
$LOCK_UN = 8;

$validlog = 0;

if ( exists($ENV{'XIL_SYSGEN_COREGEN_DEBUG'}) && $ENV{'XIL_SYSGEN_COREGEN_DEBUG'} > 0 ) {
    $ENV{'XIL_SYSGEN_SKIP_FROM_COREGEN_CACHE'} = 1;
    $ENV{'XIL_SYSGEN_LOG_COREGEN_CALLS'} = 1;
}

if ( exists($ENV{'XIL_SYSGEN_LOG_COREGEN_CALLS'}) && $ENV{'XIL_SYSGEN_LOG_COREGEN_CALLS'} > 0 ) {
   if ( open(GENCORESLOG,'> generate_cores.log') ) {
      binmode(GENCORESLOG);
      GENCORESLOG->flush();
      $validlog = 1;
   }
}

my(@BehaviourSuppDeviceList) = qw(cc_cmplr cmpy rs_encdr xbp_dsp48_mcr fr_cmplr dv_gn dds_cmplr crdc cnvltn);

sub __loginfo() {
    my $msg = $_[0];
    if ( $validlog ) { print GENCORESLOG "$msg\n"; }
}

#
# Returns a vector produced by inflating a
# "CSET coefficient_file = <coe_file>" line.
#
# @param coeFileName Name of the file to inflate.
#
# @return Vector of lines produced by inflating <code>coeFileLine</code>.

sub inflateCoeFileLine() {
  &__loginfo("Calling inflateCoeFileLine Perl function");
  my $coeFileName = $_[0];

  # Build a vector.  First line is "CSET coefficient_file = [",
  # subsequent lines are from the coe file, and last line is "]".
  my $coeLines = ["CSET coefficient_file = ["];
  -e $coeFileName || croak "there is no $coeFileName";
  open(COEFILE, "< $coeFileName") || croak "couldn't open $coeFileName";
  push(@$coeLines, <COEFILE>);
  my $line;
  foreach $line (<COEFILE>) { push(@$coeLines, "  $line"); }
  close(COEFILE) || croak "trouble reading $coeFileName";
  chomp(@$coeLines);

  push(@$coeLines, "]");
  return $coeLines;
}


#
# Returns a name to be used for a core.  If the name is already specified
# explicitly in a CSET component_name in the xco instructions, then it is
# returned.  Otherwise the name is constructed from a prefix derived from the
# SELECT line in the core's xco instructions, and a suffix that is a hash of
# the core's non-boilerplate xco instructions.
#
# @param @$instrs Full set of the core's xco instructions.
#
# @return Name to be used for a core whose SELECT line is given by
#         $selectLine, and whose xco instructions are given
#         by @$instrs.
#

sub buildCoreName() {
  &__loginfo("Calling buildCoreName Perl function");
  my $instrs = $_[0];

  # Is the name specified explicitly in a CSET component_name line?  If so,
  # use it.
  my($line, $selectLine, $selectIndex);
  my $j = -1;
  foreach $line (@$instrs) {
    $j++;
    return $1 if ($line =~ /^\s*CSET\s+component_name\s*=\s*(\S+)/i);
    if ($line =~ /^\s*SELECT\s/i) {
      $selectLine = $line;
      $selectIndex = $j;
    }
  }

  # Otherwise, build a prefix from the SELECT line and a suffix from
  # an MD5 hash of the xco instructions.
  $selectLine || croak "expected xco instructions to contain a SELECT line";
  my $prefix = $selectLine;
  $prefix =~ s/^\s*SELECT\s+//i;
  $prefix =~ s/\s*$//;
  $prefix =~ s/Xilinx,_Inc\.//;
  $prefix = &Sg::scrubName($prefix);

  my $md5 = Digest::MD5->new;
  for ($j = $selectIndex; $j < scalar(@$instrs); $j++) {
    $md5->add($instrs->[$j]);
  }
  my $suffix = substr($md5->hexdigest, 0, 16);
  return $prefix . "_$suffix";
}


#
# Augments a given sequence of sgxco instructions with the boilerplate
# needed at the front, and with the CSET component_name and GENERATE
# at the back.
#
# @param instrs Sequence of sgxco instructions to augment.
#
# @return Augmented list of instructions.
#

sub augmentXcoInstrs() {
  &__loginfo("Calling augmentXcoInstrs Perl function");
  my $instrs = $_[0];
  my $nrElts = scalar(@$instrs);
  $nrElts > 0 || croak "expected coregen instructions to be nonempty";
  my $lastLine = $instrs->[$nrElts - 1];
  pop(@$instrs) if ($lastLine =~ /^\s*GENERATE\s*$/i);

  #Verify if XilinxFamily is already set in the instructions
  my @familyLines = grep(/^\s*SET\s+XilinxFamily\s*=/, @$instrs);
  my @deviceLines = grep(/^\s*SET\s+devicefamily\s*=/, @$instrs); 
  
  my @simulationfiles = grep(/^\s*SET\s+simulationfiles\s*=/, @$instrs); 

  # Build name to use for core.

  # Write header boilerplate if needed.
  my $newInstrs = [];
  if ($instrs->[0] !~ /^\s*SETPROJECT\s/i) {
    push(@$newInstrs, "SETPROJECT ./");

# removed. Already captured with the same default in coregen.cgp file
#    push(@$newInstrs, "SET AddPads = false");

    # coregen.cgp file may use TRUE for ASYsymbol. So keep this instruction
    push(@$newInstrs, "SET ASYSymbol = false");

    # coregen.cgp file has BusFormatAngleBracketNotRipped value
    push(@$newInstrs, "SET BusFormat = BusFormatParenNotRipped");

    # Use coregen_part_family variable if s it is set in the synopsis file
    # otherwise use coregenPartFamily
    my $cpf = &Sg::getAttribute("coregen_part_family", undef ,1);
    $cpf = &Sg::getAttribute("coregenPartFamily") if (! defined($cpf));

    #Use the XilinxFamily Specified
    if (scalar(@familyLines)) {
        push(@$newInstrs, @familyLines);
    }
    else {
        #Extract the xilinx family from globals
        if ($cpf eq "Spartan3ADSP") {
            $cpf =~ tr/A-Z/a-z/;
        }
        push(@$newInstrs, "SET XilinxFamily = $cpf");
    }
	
	if(scalar(@simulationfiles)) {
	   push(@$newInstrs, @simulationfiles);
    }
    else {
        push(@$newInstrs, "SET simulationfiles = Behavioral");
    }
	&__loginfo("Simulaiton Files : @simulationfiles");
	
    $cpf =~ tr/A-Z/a-z/;

    #Added for DAFIR support. This was required because
    #DAFIR may target VIRTEX4 when VIRTEX5 support is
    #required. Hence additional code for device lines
    #has been introduced. This ensures that project
    #devicefamily and core device family are insync
    #with xilinx family. Need to clean this out further
    #So that all interactions are purely through XilinxFamily
    if ( scalar(@deviceLines) ) {
        push(@$newInstrs, @deviceLines);
    }
    else {
        push(@$newInstrs, "SET devicefamily = $cpf");
    }

  #Add changes to the make file to include the package and speed grade information 
  my($xilinxpart,$xilinxpackage,$xilinxspeed);
  $xilinxpart = &Sg::getAttribute("xilinxpart", undef ,1);
  $xilinxpackage = &Sg::getAttribute("xilinx_package", undef ,1);
  $xilinxspeed = &Sg::getAttribute("device_speed", undef ,1);
  push(@$newInstrs, "SET device = $xilinxpart") if (defined($xilinxpart));
  push(@$newInstrs, "SET package = $xilinxpackage") if (defined($xilinxpackage)); 
  push(@$newInstrs, "SET speedgrade = $xilinxspeed") if (defined($xilinxspeed));
  
  &__loginfo("Xilinx part :: << $xilinxpart >>");
  &__loginfo("Xilinx package :: << $xilinxpackage >>");
  &__loginfo("Xilinx speed :: << $xilinxspeed >>");
  
  #end of changes for adding part information to the cgXCO file

# removed. Already captured with the same default in coregen.cgp file
#    push(@$newInstrs, "SET FlowVendor = Other");
#    push(@$newInstrs, "SET FormalVerification = false");
#    push(@$newInstrs, "SET FoundationSym = false");
#    push(@$newInstrs, "SET ImplementationFileType = Ngc");

    my $hdl_attr = "hdlKind";
    my $hdlKind = Sg::getAttribute($hdl_attr, undef, 1);
    if (defined ($hdlKind) && ($hdlKind =~ /vhdl/)) {
        push(@$newInstrs, "SET VerilogSim = false");
        push(@$newInstrs, "SET VHDLSim = true");
    }
    else {
        push(@$newInstrs, "SET VerilogSim = true");
        push(@$newInstrs, "SET VHDLSim = false");
    }

# removed. Already captured with the same default in coregen.cgp file
#    push(@$newInstrs, "SET RemoveRPMs = false");
  }

  # Copy the old instructions
  my $componentNameLine;
  my $line;
  foreach $line (@$instrs) {
    $line =~ s/^\s+//;
    $line =~ s/\s+$//;
# Note: we no longer need to inflate the CSET coefficient_file lines
#  here, because the front-end netlister inflates it instead.
#   if ($line =~ /^CSET\s+coefficient_file\s+=\s+(.*)$/ && $1 ne "[") {
#     my $coeFileLines = &inflateCoeFileLine($1);
#     push(@$newInstrs, @$coeFileLines);
#   }
#   else {
      if ($line =~/^\s*SET\s+XilinxFamily\s*=/) {
        #skip if encountering XilinxFamily Specification
      }
      else {
        push(@$newInstrs, $line);
      }
#   }
    $componentNameLine = $line if ($line =~ /^CSET\s+component_name/);
  }

  # Add a CSET component_name line if there is not one already.
  my $coreName = "";
  if (! $componentNameLine) {
    $coreName = &buildCoreName($newInstrs);
    push(@$newInstrs, "CSET component_name = $coreName")
  } 
  else {
     if ( $componentNameLine =~ m/^CSET(\s+)component_name = (\w+)(\s*)$/ ) {
         $coreName = $2;
     }
  }
  push(@$newInstrs, "GENERATE");
  push(@$newInstrs, $coreName);

  return $newInstrs;
}

#
# Constructs to enable deferred synthesis in PA/Rodin flow
#
sub isDeferredSynthesis() {
  my $projType = &Sg::getAttribute("proj_type", undef ,1);
  my $synthesisTool = &Sg::getAttribute("synthesisTool", undef ,1);
  if ( defined($projType) && defined($synthesisTool) ) {
    if ( $projType eq "PlanAhead" ) {
		if ( $synthesisTool eq "RDS" ) {      		
			return 1;
		}
    }
  }    
  return 0;
}

#
# Writes a sequence of xco instructions to a file for later processing by
# coregen.  The sequence must be official except for two things:
#
# - The CSET coefficient_file = <coe_file> instruction must not
#   be used.  Instead, substitute
#     CSET coefficient_file = [
#       <line 1>
#       <line 2>
#          :
#       <line n>
#     ]
#
# - Each core must be named in a particular way.  A typical name is
#     viterbi_decoder_virtex_2_0_d60126e279f2ef00
#   Here the prefix " viterbi_decoder_virtex_2_0" is derived from
#   the xco SELECT line in the obvious way.  The suffix "d60126e279f2ef00"
#   is the MD5 hash of the XCO instructions (not including the
#   CSET component_name or GENERATE lines).  The FFT cores are an exception;
#   their names are the ones coregen insists on.
#
# @param @$seq Sequence of xco instructions to write to the file.
#

sub saveXcoSequence {
  &__loginfo("Calling saveXcoSequence Perl function");
  my $seq = $_[0];
  my $augSeq = &augmentXcoInstrs($seq);
  my $coreName = pop(@$augSeq);
  $tmpDir = &Sg::getTmpDir();
  # Is the file handle for the instructions open?  If not, create a directory
  # for coregen to run in, and open the file handle.
  if ( isDeferredSynthesis() ) {    
    $xcoInstrsCoreFile = new FileHandle;
    binmode $xcoInstrsCoreFile;
    open($xcoInstrsCoreFile, "> $tmpDir/$coreName.xco") || croak "couldn't open $coreName.xco";
	push(@$RESULTS, ["$tmpDir/$coreName", { 'producer' => 'coregen'}]);
  }
  if (! $xcoInstrsFile) {    
    $coregenTmpDir = &Sg::getAttribute("coregen_core_generation_tmpdir", undef ,1);
    # Revert to old behaviour if new behaviour fails
    if (! defined($coregenTmpDir)) {
      $coregenTmpDir = &SgEnv::canonpath(tempdir("coregen_XXXX",
                                                 DIR => $tmpDir));
    }

    &mkpath($coregenTmpDir, 0, 0777);

    $xcoInstrs = "$coregenTmpDir/sysgen.sgxco";
    $xcoInstrsFile = new FileHandle;
    binmode $xcoInstrsFile;
    open($xcoInstrsFile, "> $xcoInstrs") || croak "couldn't open $xcoInstrs";
    $xcoInstrsFile->autoflush();
  }
  
  print $xcoInstrsFile "\n";
  my $line;
  foreach $line (@$augSeq) { 
	  print $xcoInstrsFile "$line\n"; 
  }    
  if ( isDeferredSynthesis() ) {
    $augSeq = elaborateXcoChunk($augSeq, findCoreName($augSeq), $tmpDir, 1);
    foreach $line (@$augSeq) { 
	  print $xcoInstrsCoreFile "$line\n"; 
    }    
    close($xcoInstrsCoreFile);
  }
  return undef;
}


#
# Returns an array produced by dividing the xco instruction file into "chunks".
# Each chunk consists of the xco instructions needed to make one core, and
# is separated from its neighbors by blank lines.
#
# @param $xcoFile Name of the xco file to be parsed.
#
# @return Array whose elements are instruction chunks derived from the file
#         $xcoFile.
#

sub parseXcoFile() {
  &__loginfo("Calling parseXcoFile Perl function");
  my $xcoFile = $_[0];

  my $chunks = [];
  my $chunk = [];
  open(XCOFILE, "< $xcoFile") || &copyCoregenWork("couldn't read $xcoFile");
  my $line;
  foreach $line (<XCOFILE>) {
    chomp($line);
    if ($line =~ /^\s*$/) {
      if (scalar(@$chunk)) {
        push (@$chunks, $chunk);
        $chunk = [];
      }
    }
    else {
      push(@$chunk, $line);
    }
  }
  close(XCOFILE) || &copyCoregenWork("trouble reading $xcoFile");
  push(@$chunks, $chunk) if (scalar(@$chunk));
  return $chunks;
}


#
# Returns the core name that should be used for a given chunk of
# xco instructions.  Note: We assume this name is defined by the
# CSET component_name instruction.
#
# @param @$xcoChunk Sequence of xco instructions for which core name should
#        be found.
#
# @return Name that should be used for the core generated from the xco
#         instructions in @$xcoChunk.
#

sub findCoreName() {
  &__loginfo("Calling findCoreName Perl function");
  my $xcoChunk = $_[0];

  # Extract the core name from the CSET component_name <core_name> instruction.
  my @nameLines = grep(/^\s*CSET\s+component_name\s*=/, @$xcoChunk);
  scalar(@nameLines) ||
    &copyCoregenWork("expected to find CSET component_name line in xco instructions");
  scalar(@nameLines) == 1 ||
    &copyCoregenWork("too many CSET component_name lines in xco instructions");
  my $line = $nameLines[0];
  $line =~ /\s+(\S+)\s*$/;
  my $name = $1;
  defined($name) ||
    &copyCoregenWork("couldn't find component name in xco CSET component_name line");
  return $name;
}

sub findImplementationtype() {
   &__loginfo("Calling findImplementationtype Perl function");
   my $xcoChunk = $_[0];
   my $imp_type = "null";
   my @nameLines;
	
   @nameLines = grep(/^\s*SET\s+implementationFileType\s*=/,@$xcoChunk); 	
   &__loginfo("The name lines and XcoChunk : @nameLines \n  @$xcoChunk\n");
	  
    if(scalar(@nameLines)) {
		scalar(@nameLines) eq 1 || &copyCoregenWork("too many SET component_name lines in xco instructions");
	    my $line = $nameLines[0];
		$line =~ /\s+(\S+)\s*$/;
		$imp_type = $1;
		defined($imp_type) || &copyCoregenWork("couldn't find component name in xco SET implementation type line");
    	&__loginfo("Implementation type = $imp_type\n"); 
	}
    return $imp_type;
}

#
# Returns 1 (0) if the cache contains (does not contain) the files for a given
# core.
#
# @param $cache Tells the directory that contains the cache (or is undefined
#        if there is no cache).
# @param $coreName Name of the core to look for.
#
# @return 1 (0) if the cache contains (does not contain) the files for the core
#         named $coreName.
#

sub cacheContains() {
  &__loginfo("Calling cacheContains Perl function");
  my($cache, $coreName) = @_;
  return 0 unless (defined($cache) && ($cache !~ /^ /));
  return -e "$cache/$coreName";
}


#
# Copies files from the core cache to the coregen tmp directory.
#
# @param $cache Name of the core cache directory.
# @param $coreName Name of the core whose files should be copied.
# @param $coregenTmp Directory to which files should be copied.
#
# @return 1 (0) if this function succeeded (failed).
#

sub copyCacheFiles() {
  &__loginfo("Calling copyCacheFiles Perl function");
  my($cache, $coreName, $coregenTmp) = @_;

  my $dir = "$cache/$coreName";
  my @files = &File::DosGlob::doglob(1, "$dir/*");
  if (! scalar(@files)) {
    &removeDir($dir);
    &releaseLock($cache, $coreName);
    return 0;
  }
  my $file;
  foreach $file (@files) {
    my $err = copyordie($file, $coregenTmp);
  }
  return 1;
}


#
# Writes a .coe file containing the lines from a given array, then returns
# the name of the file.  The file name is chosen so it doesn't collide with
# any other file in the coregen tmp directory.
#
# @param @$lines Array containing the lines to write.
# @param $coreName Name of the core that will own the file.
# @param $coregenTmp Directory to which the file should be written.
#
# @return Name of the .coe file.
#

sub writeCoeFile() {
  &__loginfo("Calling writeCoeFile Perl function");
  my($lines, $coreName, $coregenTmp) = @_;

  my $fileName = "$coregenTmp/$coreName.coe";
  my $i = 0;
  while (-e $fileName) {
    $i++;
    $fileName = "$coregenTmp/$coreName" . "_$i.coe";
  }

  open(COEFILE, "> $fileName") || &copyCoregenWork("couldn't write $fileName");
  binmode(COEFILE);
  COEFILE->autoflush();
  my $line;
  foreach $line (@$lines) { print COEFILE "$line\n"; }
  close(COEFILE) || &copyCoregenWork("trouble writing $fileName");
  return &basename($fileName);
}


#
# Returns an "elaborated" version of a given sequence of xco instructions.  The
# given sequence is in the form Sysgen likes.  The elaborated sequence is
# transformed in whatever ways are needed so the instructions are ready for
# coregen.
#
# @param @$xcoChunk Sequence of instructions to be elaborated.
# @param $coreName Name to be assigned to the core.
# @param $coregenTmp Directory to which files will be delivered.
#
# @return Elaborated version of the instructions from @$xcoChunk.
#

sub elaborateXcoChunk() {
  &__loginfo("Calling elaborateXcoChunk Perl function");
  my($xcoChunk, $coreName, $coregenTmp, $isDeferredSynthesis) = @_;

  # Form new chunk from original.  New chunk matches original except that
  # coe file lines are written to file.
  my(@newChunk, @coeFileLines);
  my $formingCoeFile = 0;
  my $coefficient_file;
  my $line;
  foreach $line (@$xcoChunk) {
    chomp($line);
    if ($formingCoeFile) {
      if ($line ne "]") {
        $line =~ s/^\s+//;
        push(@coeFileLines, $line);
      }
      else {
        my $coeFileName =
          &writeCoeFile(\@coeFileLines, $coreName, $coregenTmp);
			 if ( defined ($isDeferredSynthesis) && ($isDeferredSynthesis == 1) ) {
				 push(@newChunk, "CSET $coefficient_file = ../$coeFileName");
			 }
			 else {
				 push(@newChunk, "CSET $coefficient_file = $coeFileName");
			 }
        $formingCoeFile = 0;
        @coeFileLines = ();
      }
    }
    elsif ($line =~ /^\s*CSET\s+(\w+)\s*=\s*\[\s*$/) {
      $coefficient_file = $1;
      $formingCoeFile = 1;
    }
    else {
      push(@newChunk, $line);
    }
  }
  return \@newChunk;
}


#
# Extracts the Xilinx part family from a given sequence of xco instructions,
# then returns the family.
#
# @param @$xcoChunk Xco instruction sequence from which part family should be
#        extracted.
#
# @return Xilinx part family extracted from @$xcoChunk.
#

sub findXilinxFamily() {
  my $xcoChunk = $_[0];

  my @familyLines = grep(/^\s*SET\s+XilinxFamily\s*=/, @$xcoChunk);
  scalar(@familyLines) ||
    &copyCoregenWork("expected to find SET XilinxFamily line in xco instructions");
  scalar(@familyLines) == 1 ||
    &copyCoregenWork("too many SET XilinxFamily lines in xco instructions");
  my $line = $familyLines[0];
  $line =~ /\s+(\S+)\s*$/;
  my $family = $1;
  defined($family) ||
    &copyCoregenWork("couldn't find family in xco SET XilinxFamily line");
  return $family;
}


#
# Tries to lock on a given core in the core cache, then returns 1 (0) if it
# succeeded (failed).
#
# @param $cache Tells the location of the core cache (or is undefined if there
#        is no core cache).
# @param $coreName Name of the core for which the lock should be obtained.
#
# @return 1 (0) if core could be (could not be) locked.
#

sub getLock() {
  my($cache, $coreName) = @_;
  defined($cache) || return 0;
  &mkpath($cache, 0, 0777);
  my $lockFile = "$cache/$coreName.lock";
  open(LOCK, "> $lockFile") || &copyCoregenWork("couldn't write $lockFile");
  if (flock(LOCK, $LOCK_EX | $LOCK_NB)) {
    my $timeString = localtime;
    print LOCK "$coreName locked at time $timeString\n";
    return 1;
  }
  close(LOCK) || &copyCoregenWork("couldn't close $lockFile");
  unlink $lockFile;
  return 0;
}


#
# Releases the lock on a given core in the core cache.
#
# @param $cache Tells the location of the core cache (or is undefined if there
#        is no core cache).
# @param $coreName Name of the core for which the lock should be released.
#

sub releaseLock() {
  my($cache, $coreName) = @_;
  defined($cache) || return;
  flock(LOCK, $LOCK_UN);
  close(LOCK);
  my $lockFile = "$cache/$coreName.lock";
  unlink $lockFile;
}

#
# Returns false if a file is in a sub-directory or doesn't match acceptable patterns
# <component_name>.vhd, <component_name>.v, <component_name>.coe
# <component_name>.ngc, <component_name>.xco, <component_name>*.mif
# <component_name>_flist.txt
#
sub isFileFilterOut() {
  my $file = $_[0];
  my $coreName = $_[1];

  if ($file =~ /\//) {
     return 1;
  }

  if ($file =~ /$coreName/) {
      my @AcceptablePatterns = ('.vhd$', '.v$', '.mif$', '.coe$', '.ngc$', '_flist.txt$', '.xco$');
      foreach my $acceptablePattern (@AcceptablePatterns) {
          if ($file =~ /$acceptablePattern/) {
              return 0;
          }
      }
  }

  return 1;

}

#
# Copies results generated by coregen from coregen tmp directory to cache.
#
# @param $%nameToInstrs Hash table whose keys are the names of the cores
#        that coregen generated.
# @param $resultsDir Name of the directory containing the coregen results.
# @param $cache Tells the location of the cores cache.
#

sub cacheCoregenResults() {
  &__loginfo("Calling cacheCoregenResults Perl function");
  my($nameToInstrs, $resultsDir, $cache) = @_;
  return unless (defined($cache) && ($cache !~ /^ /));

  # Create a directory for the cache if necessary.
  -e $cache || &mkpath($cache, 0, 0777);
  -e $cache || croak "couldn't make $cache";
  -d $cache || croak "$cache is not a directory";
  -w $cache || croak "$cache is not writable";

  # For each core generated by coregen, make a cache subdirectory having the
  # same name, and copy the coregen results to the subdirectory.
  my $coreName;
  foreach $coreName (keys %$nameToInstrs) {
    next unless (&getLock($cache, $coreName));
    my $dir = "$cache/$coreName";
	&__loginfo("CoreName : $coreName $cache $dir \n");
    if (-e $dir) {
      &releaseLock($cache, $coreName);
      next;
    }

    # make cache directory for $coreName
    &mkpath($dir, 0, 0777);
    if (! -e $dir) {
      &releaseLock($cache, $coreName);
      &copyCoregenWork("couldn't make $dir");
    }

    # check if <$corename>_flist.txt file exists
    my $flist = "$resultsDir/$coreName" . "_flist.txt";
    if (! -e $flist) {
      &removeDir($dir);
      &releaseLock($cache, $coreName);
      &copyCoregenWork("user: Cannot find $flist file.");
    }

    if (! open(FLIST, "< $flist")) {
      &removeDir($dir);
      &releaseLock($cache, $coreName);
      &copyCoregenWork("user: Cannot open $flist file to read.");
    }

    my $file;
    my $err;
    my $found_vhdl_netlist = 0;
    my $found_vlog_netlist = 0;
    my $vhdl_netlist = $coreName . ".vhd";
    my $vlog_netlist = $coreName . ".v";
    foreach $file (<FLIST>) {
      chomp($file);
      $file =~ s/#.*//;
      $file =~ s/^\s+//;
      $file =~ s/\s+$//;
      $file =~ s/\\/\//;
      next unless (length($file) > 0);
      # filters out any file that doesn't have extension
      # .vhd, .v, .ngc, _flist.txt, .mif, .coe, and .xco
      next if &isFileFilterOut($file, $coreName);
      
      my $srcFile = "$resultsDir/$file";

      # check if VHDL netlist exists
      if ($file eq $vhdl_netlist) {
          if (-e $srcFile) {
              $found_vhdl_netlist = 1;
              &__loginfo("Found VHDL netlist $vhdl_netlist.");
          }
      }

      # check if Verilog netlist exists
      if ($file eq $vlog_netlist) {
          if (-e $srcFile) {
              $found_vlog_netlist = 1;
              &__loginfo("Found Verilog netlist $vlog_netlist.");
          }
      }

      my $destFile = "$dir/$file";
	  &__loginfo("Directory : $srcFile $destFile \n");
      $err = copywitherror($srcFile, $destFile);
      if ($err ne '') {
        &removeDir($dir);
        &releaseLock($cache, $coreName);
        &copyCoregenWork("$err");
      }
    }
    close(FLIST) || croak "Trouble closing $flist file.";

    # neither of the HDL netlists exists
    if (($found_vhdl_netlist == 0) && ($found_vlog_netlist == 0)) {
        &removeDir($dir);
        &releaseLock($cache, $coreName);
        &copyCoregenWork("user: Error: Neither Verilog nor VHDL netlist is created for $coreName.");
    }
        
    my $srcFile = $flist;
    my $destFile = "$dir/" . &basename($flist);
    $err = copywitherror($srcFile, $destFile);
	&__loginfo("Directory(2) : $srcFile $destFile \n");
    if ($err ne '') {
      &removeDir($dir);
      &releaseLock($cache, $coreName);
      &copyCoregenWork("$err");
    }

    &releaseLock($cache, $coreName);
  }
}


#
# Returns a string (suitable for printing) that describes a number of cores.
#
# @param $n Tells the number of cores.
#
# @return String (suitable for printing) that describes $n.
#

sub coreCount() {
  my $n = $_[0];
  return "no cores" if ($n == 0);
  return "one core" if ($n == 1);
  return "$n cores";
}


#
# Returns without doing anything if a list of error messages is empty;
# otherwise, prints the first message from the list and dies.
#
# @$errors List of error messages to examine.
#

sub reportErrorsAndDie() {
  &__loginfo("Calling reportErrorsAndDie Perl function");
  if (scalar(@{$_[0]})) {
     my $idx = 1;
     foreach my $err (@{$_[0]}) {
        &__loginfo("ERROR $idx found :: $err");
        ++$idx;
     }
  }

  my $errors = $_[0];
  my $coregenLogFile = $_[1];
  my $tmpDirLogFile = $_[2];
  return unless (scalar(@$errors));
  my $firstError = $errors->[0];
  chomp($firstError);
  $firstError =~ s/^# ERROR: //;
  $firstError =~ s/^\s*ERROR: //;
  &copyCoregenWork("user: $firstError. More information can be found in $coregenLogFile or in $tmpDirLogFile.");
}




#
# Copies all .edn, .ngc, ... files from the coregen tmp directory to the target
# directory.
#
# @param $coregenTmp Coregen tmp directory from which files should
#        be copied.
# @param $targetDir Directory to which files should be copied.
#

sub copyCoregenResults() {
    &__loginfo("Calling copyCoregenResults Perl function");
    return;
  # No need for this function to be called. Since with refactor, the coregenTmp==targetdir.
  my($coregenTmp, $targetDir) = @_;

  my @files = &File::DosGlob::doglob(1, "$coregenTmp/*.edn");
  push(@files, &File::DosGlob::doglob(1, "$coregenTmp/*.ngc"));
  push(@files, &File::DosGlob::doglob(1, "$coregenTmp/*.mif"));
  push(@files, &File::DosGlob::doglob(1, "$coregenTmp/*.vhd"));
  push(@files, &File::DosGlob::doglob(1, "$coregenTmp/*.v"));
  push(@files, &File::DosGlob::doglob(1, "$coregenTmp/*.cdc"));
  my $file;
  foreach $file (@files) {
    my $err = copyordie($file, $targetDir);
  }
}


#
# Parses the XCO file and skips synthesis if needed
#
# @param xcoFile from which the core is generated
#

sub skipCoreSynthesisForSimulation() {
    &__loginfo("Calling skipCoreSynthesisForSimulation Perl function");
    my $xcoFile = $_[0];
    open (XCOFILE, "< $xcoFile") || &copyCoregenWork("couldn't read $xcoFile");
    my $line;
    $ENV{'XIL_CG_SKIP_SYNTHESIS'} = 'false';
    foreach $line (<XCOFILE>) {
        chomp($line);
        if ( $line =~ /Skip Synthesis/ ){
               $ENV{'XIL_CG_SKIP_SYNTHESIS'} = 'true';
               close(XCOFILE) || &copyCoregenWork("trouble closing $xcoFile");
               return undef;
        }
    }
    close(XCOFILE) || &copyCoregenWork("trouble closing $xcoFile");
    return undef;
}



#
# Generates cores in the working directory, using a given set of xco
# instructions.
#
# @param $xcoFile Path to the xco file.
#

sub generateCores() {

  &__loginfo("Calling generateCores Perl function");
  my $xcoFile = $_[0];

  # Verify xco file makes sense.
  -e $xcoFile || &copyCoregenWork("there is no $xcoFile");
  -f $xcoFile || &copyCoregenWork("$xcoFile is not a plain file");
  -r $xcoFile || &copyCoregenWork("$xcoFile is not readable");

  # Decide where cache should be.
  #my $cache = $ENV{'SGCORECACHE'};
  my $cacheroot = &Sg::getAttribute("dsp_cache_root_path", undef ,1);
  $cacheroot =~ s/\\/\//g;
  if (! defined($cacheroot)) {
      $cacheroot = &SgEnv::getUserTempDir();
  }
  &__loginfo("Cache root --> << ${cacheroot} >>");

  my $cache = "$cacheroot/sg_core_cache";

  #if (! defined($cache)) {
    my $temp = &SgEnv::getUserTempDir();
    &mkpath($temp, 0, 0777) unless (-e $temp);
    -e $temp || &copyCoregenWork("there is no $temp");
    -d $temp || &copyCoregenWork("$temp is not a directory");
  #  $cache = "$temp/sg_core_cache";
    &mkpath($cache, 0, 0777) unless (-e $cache);
    -e $cache || &copyCoregenWork("there is no $cache");
    -d $cache || &copyCoregenWork("$cache is not a directory");
    -r $cache || &copyCoregenWork("$cache is not readable");
    -w $cache || &copyCoregenWork("$cache is not writable");
    &__loginfo("User Temp directory    --> << ${temp} >>");
  #}

  &__loginfo("SG Core Cache location --> << ${cache} >>");

  $coregenTmpDir = &Sg::getAttribute("coregen_core_generation_tmpdir", undef ,1);
    # Revert to old behaviour if new behaviour fails
    if (! defined($coregenTmpDir)) {
      $coregenTmpDir = &SgEnv::canonpath(tempdir("coregen_XXXX",
                                                 DIR => $tmpDir));
    }

  # Make a fresh "coregen_tmp" subdirectory of the target directory.
  my $targetDir = &SgEnv::rel2abs('.');
  #my $coregenTmp = "$targetDir/coregen_tmp";
  # Make coregenTmp the same directory a targetDir. ie.
  # targetDir = C:\temp\sysgentmp-sps\cg_wk\cd3f3a4d5601eb4f4, make coregen
  # work in the same directory.
  my $coregenTmp = "$targetDir";
  my $logFile = "coregen.log";
  $coregenTmp =~ s/\\/\//g;

  # The targetDir was created in cxx, no need to delete and mkpath.
  #&Sg::removeDirNoError($coregenTmp);
  # Test for /tmp in $coregenTmp since we can't actually delete $coregenTmp, but if a previous coregen was called in
  # this dir, there would be a /tmp directory
  #! -e "$coregenTmp/tmp" || croak "couldn't remove old $coregenTmp/tmp";
  #&mkpath($coregenTmp, 0, 0777);

  # Parse the xco file to produce an array of "chunks".  Each chunk is a
  # sequence of xco instructions corresponding to one core.
  my $xcoChunks = &parseXcoFile($xcoFile);

  # Make a hash table to hold the instructions we actually plan to submit to
  # coregen.  Each key is the name of the core.  Corresponding value is
  # the list of instructions needed to realize the core.
  my $nameToInstrs = {};

  # For each chunk, decide whether the cache already contains an
  # implementation.  If so, copy the cache files to the target directory;
  # otherwise, add the instructions needed to the array.
  my $namesAlreadySeen = {};
  my $coresCopied = 0;
  my $coresToGenerate = 0;
  my $xilinxFamilies = {};
  my $xcoChunk;
  foreach $xcoChunk (@$xcoChunks) {
    my $coreName = &findCoreName($xcoChunk);
    next if ($namesAlreadySeen->{$coreName});
    $namesAlreadySeen->{$coreName} = 1;
    my $copiedFiles = 0;

    my $skip_core_cache = 0;
    my $hasenvvar = exists($ENV{'XIL_SYSGEN_SKIP_FROM_COREGEN_CACHE'}) || 0;
    if ( $hasenvvar != 0 or $ENV{'XIL_SYSGEN_SKIP_FROM_COREGEN_CACHE'} > 0 ) { $skip_core_cache = 1; }

    &__loginfo("Flag for skipping core cache :: << $skip_core_cache >>");

    if ( $skip_core_cache == 0 ) {
       if (&cacheContains($cache, $coreName) && &getLock($cache, $coreName)) {
          &__loginfo("Found core requested in core cache :: $coreName...");
          $copiedFiles = &copyCacheFiles($cache, $coreName, $coregenTmp);
          &releaseLock($cache, $coreName);
          $coresCopied++ if ($copiedFiles);
       }
    } else {
        &__loginfo("Skipping retrieval from coregen cache, rebuilding $coreName!");
    }
    if (! $copiedFiles) {      
      $nameToInstrs->{$coreName} =
        &elaborateXcoChunk($xcoChunk, $coreName, $coregenTmp);
      $coresToGenerate++;
      $xilinxFamilies->{&findXilinxFamily($xcoChunk)} = 1;
    }

    &__loginfo("Number of cores copied from cache :: $copiedFiles");
    &__loginfo("Number of cores to generate       :: $coresToGenerate");
  }
  # If there are no cores that need to be generated, quit now.
  if ($coresToGenerate == 0) {
    &copyCoregenResults($coregenTmp, $targetDir);
    &__loginfo("No cores to generate, all retrieved from cache or none needed!");
    return;
  }

  #Determine the Xilinx part family to use when making the coregen project.
  #Since we allow each block to specify the Xilinx family this error cannot
  #be thrown.
  #--scalar(keys %$xilinxFamilies) == 1 ||
  #--  croak "xco instructions called for more than one part family";
  my($xilinxFamily, $family);
  #--foreach $family (keys %$xilinxFamilies) { $xilinxFamily = $family; }
  $xilinxFamily = &Sg::getAttribute("coregen_part_family", undef ,1);
  $xilinxFamily = &Sg::getAttribute("coregenPartFamily",undef,1) if (! defined($xilinxFamily));
  #The following line has been added to allow calling the core from coregen side
  if (! defined($xilinxFamily) ) {
    foreach $family (keys %$xilinxFamilies) { $xilinxFamily = $family; }
  }
  &__loginfo("Xilinx family :: << $xilinxFamily >>");

  # Make a project file to set up coregen.
  chdir $coregenTmp || croak "couldn't cd into $coregenTmp";
  my $projectFile = "makeproj";
  unlink $projectFile;
  ! -e $projectFile || &copyCoregenWork("couldn't remove old $projectFile");
  open(PROJECTFILE, "> $projectFile") ||
    &copyCoregenWork("couldn't write $projectFile");
  binmode(PROJECTFILE);
  PROJECTFILE->autoflush();
  print PROJECTFILE "NEWPROJECT ./\n";
  print PROJECTFILE "SETPROJECT ./\n";
  if ($xilinxFamily eq "Spartan3ADSP") {
   $xilinxFamily =~ tr/A-Z/a-z/;
  }
  print PROJECTFILE "SET XilinxFamily = $xilinxFamily\n";
  print PROJECTFILE "SET overwritefiles = True\n";

  #Add changes to the make file to include the package and speed grade information 
  my($xilinxpart,$xilinxpackage,$xilinxspeed);
  $xilinxpart = &Sg::getAttribute("xilinxpart", undef ,1);
  $xilinxpackage = &Sg::getAttribute("xilinx_package", undef ,1);
  $xilinxspeed = &Sg::getAttribute("device_speed", undef ,1);
  print PROJECTFILE   "SET device = $xilinxpart\n" if (defined($xilinxpart));
  print PROJECTFILE   "SET package = $xilinxpackage\n" if (defined($xilinxpackage));
  print PROJECTFILE   "SET speedgrade = $xilinxspeed\n" if (defined($xilinxspeed));
  
  &__loginfo("Xilinx part :: << $xilinxpart >>");
  &__loginfo("Xilinx package :: << $xilinxpackage >>");
  &__loginfo("Xilinx speed :: << $xilinxspeed >>");
  
  #end of changes for adding part information to the make file 

# Commented out for IP1_L.14_L.28p.0 release as coregen failed in creating coregen.cgp file
##  print PROJECTFILE "END\n";

  close(PROJECTFILE) || &copyCoregenWork("trouble closing $projectFile");

# Added to skip ISE project (xise and gise) files generation by CoreGen
  $ENV{'XIL_CG_SKIP_ISE_GENERATOR'} = '1';

  my $coregen_debug_param = '';
  if ( exists($ENV{'XIL_SYSGEN_COREGEN_DEBUG'}) && $ENV{'XIL_SYSGEN_COREGEN_DEBUG'} > 0 ) { 
       my $coregen_verboseness = $ENV{'XIL_SYSGEN_COREGEN_DEBUG'};
       if ( $coregen_verboseness > 3 ) { $coregen_verboseness == 3; }
       $coregen_debug_param = '-'.'d' x $coregen_verboseness;
       &__loginfo("Handling debug parameters for COREGEN");
  }

  &__loginfo("Coregen debug parameters :: << $coregen_debug_param >>");

  # Call coregen on the project file to make a .prj file.
  my $coregen = &SgEnv::getCoregenExePath();
  &__loginfo("Coregen executable :: << $coregen >>");

  defined($coregen) || &copyCoregenWork("Could not find the \"coregen\" executable.");
  my @cmd = ($coregen);

  if ( $coregen_debug_param ne '' ) {
     push(@cmd, "$coregen_debug_param");
  }
  push (@cmd, "-b", "$projectFile", ">$coregenTmp/first_pass_coregen.txt", '2>&1');

  &__loginfo("Coregen command components :: ".join(" ",@cmd));

  my $stat = &Sg::runcmd(@cmd);

  &__loginfo("Status of first pass coregen command :: $stat");

  ! $stat || &copyCoregenWork("couldn't run (1st pass) $coregen: $stat");
  open(COREGENRESULTS, "< $coregenTmp/first_pass_coregen.txt") || croak "couldn't open first pass text file";
  my @results = <COREGENRESULTS>;
  close(COREGENRESULTS) || &copyCoregenWork("couldn't close first pass text file");
  my($error_records); 
  $error_records = &compressResults(\@results);  
  my $errorPatterns = [ "^ERROR:", "^# ERROR" ];
  my $errors = &Sg::findMatchingLines($errorPatterns, $error_records);
  &reportErrorsAndDie($errors, "$coregenTmp/$logFile", "$tmpDir/$logFile");

  # Write a file containing the instructions for coregen.
  my $cgxco = "coregen.cgxco";
  unlink $cgxco;
  ! -e $cgxco || &copyCoregenWork("couldn't remove old $cgxco");
  open(XCOFILE, "> $cgxco") || &copyCoregenWork("couldn't write $cgxco");
  binmode(XCOFILE);
  
  my $processcgxco1 = 0;
  my $processcgxco2 = 0;
  
  my $cgxco2 = "coregen2.cgxco";
  unlink $cgxco2;
  ! -e $cgxco2 || &copyCoregenWork("couldn't remove old $cgxco2");

  open(XCOFILE2, "> $cgxco2") || &copyCoregenWork("couldn't write $cgxco2");
  binmode(XCOFILE2);

  XCOFILE->autoflush();
  XCOFILE2->autoflush();
  my $coreName;
  foreach $coreName (sort(keys %$nameToInstrs)) {
    my $xcoChunk = $nameToInstrs->{$coreName};
    print XCOFILE "\n";
	print XCOFILE2 "\n";
    my $line;
	my $simOnly = 0;
	my $imp_type;
 
    my $imp_type = &findImplementationtype($xcoChunk);
	
   &__loginfo("Implementation type $imp_type");
   
   if ($imp_type eq "None"){    
       my($device);
       foreach $device (@BehaviourSuppDeviceList){
         if ($coreName =~ m/$device/) {
             $simOnly = 1;
             &__loginfo("Sim only = $simOnly\n"); 	
         }
       }
    }	
    foreach $line (@$xcoChunk) {
	  if ($simOnly eq 1){
        print XCOFILE2 "$line\n";
		&__loginfo("XCO2 = $line\n");
		$processcgxco2 = 1;
	  }
	  else{
        print XCOFILE "$line\n";
		&__loginfo("XCO1 = $line\n");
		$processcgxco1 = 1;
	  }
    }
  }
  close(XCOFILE) || &copyCoregenWork("trouble writing $cgxco");
  close(XCOFILE2) || &copyCoregenWork("trouble writing $cgxco2");

  if($processcgxco1 eq 1) {
	  # Call coregen a second time to make cores.
	  @cmd = ($coregen);
	  if ( $coregen_debug_param ne '' ) {
	      push(@cmd, $coregen_debug_param)
	  }
	  push (@cmd, '-p', '.', '-b', "$cgxco", ">$coregenTmp/second_pass_coregen_1.txt", '2>&1');

	  &__loginfo("Coregen command components :: ".join(" ",@cmd));
	  $stat = &Sg::runcmd(@cmd);
	  &__loginfo("Status of second pass coregen command - Structural :: $stat");
  }
  
  # Call Coregen for Behavioural - Simulaiton models
  if($processcgxco2 eq 1) {
      $ENV{'XIL_CG_SKIP_SYNTHESIS'} = 1;
   	  @cmd = ($coregen);
   	  if ( $coregen_debug_param ne '' ) {
   	  	 push(@cmd, $coregen_debug_param)
	  }
   	  push (@cmd, '-p', '.', '-b', "$cgxco2", ">$coregenTmp/second_pass_coregen_2.txt", '2>&1');

	  &__loginfo("Coregen command components :: ".join(" ",@cmd));
      $stat = &Sg::runcmd(@cmd);
      &__loginfo("Status of second pass coregen command - Behavioural :: $stat");
	  $ENV{'XIL_CG_SKIP_SYNTHESIS'} = 0;
  }

  #! $stat || croak "couldn't run (2nd pass) $coregen: $stat";

  # Check coregen log for errors and warnings.
  my $logFile = "coregen.log";
  #-e $logFile || croak "there is no $logFile";
  unless (-e $logFile) {
      unless ($stat == 0) {
          &copyCoregenWork("couldn't run (2nd pass) $coregen: $stat");
      } else {
          &copyCoregenWork("there is no $logFile");
      }
  }
  open(COREGENLOG, "< $logFile") || &copyCoregenWork("couldn't read $logFile");
  @results = <COREGENLOG>;
  close(COREGENLOG) || &copyCoregenWork("trouble reading $logFile");

  # compress the results (i.e., combine lines which begin with a space with the previous line)
  #my $error_records;
  $error_records = &compressResults(\@results);  
  
  $errors = &findMatchingLines($errorPatterns, $error_records);
  @$errors = grep(! /^# WARNING: The value "ViewlogicLibraryAlias"/, @$errors);
  my $pattern = "^# WARNING: This command file may be corrupt or from " .
    "an old version of Core Generator";
  @$errors = grep(! /$pattern/, @$errors);
  $pattern = "^# WARNING: Non reloadable constant coefficient";
  @$errors = grep(! /$pattern/, @$errors);
  &reportErrorsAndDie($errors, "$coregenTmp/$logFile", "$tmpDir/$logFile");

  # Copy coregen results from target directory back to cache.
  # Default behaviour is to cache.  If this ENV is set to non-zero 
  # then caching is DISABLED
  my $skip_caching = 0;
  my $hasenvvar = exists($ENV{'XIL_SYSGEN_SKIP_CACHE_COREGEN_RESULTS'}) || 0;
  if ( $hasenvvar != 0 or $ENV{'XIL_SYSGEN_SKIP_CACHE_COREGEN_RESULTS'} > 0 ) { $skip_caching = 1; }

  &__loginfo("Request check to skip caching results from coregen :: << $skip_caching >>");

  if ( $skip_caching == 0 ) {
     &cacheCoregenResults($nameToInstrs, $coregenTmp, $cache);
  }

  &copyCoregenResults($coregenTmp, $targetDir);

}



#
# Generates cores, then copies results to the appropriate directory.
#
# @return XTable telling what files coregen produced.  Only the "interesting"
#         files are reported.
#

sub wrapup {
  # Close the xco instruction file.
  close($xcoInstrsFile) || &copyCoregenWork("trouble writing $xcoInstrs");

  &__loginfo("Calling wrapup Perl function");

  # cd into the directory to which sgcoregen.pl should deliver files.
  chdir $coregenTmpDir || &copyCoregenWork("couldn't cd into $coregenTmpDir");

  # Generate cores.
  &generateCores($xcoInstrs);

  # Copy "interesting" coregen files.
  my $file;
  my $results = [];
  opendir(DIR, ".") || &copyCoregenWork("couldn't open directory");
  foreach $file (readdir(DIR)) {
    next unless  ($file =~ /\.vhd$/i || $file =~ /\.v$/i ||
                  $file =~ /\.edn$/i || $file =~ /\.ngc$/i ||
                  $file =~ /\.cdc$/i || $file =~ /\.mif$/i);
    push(@$results, [$file, { 'producer' => 'coregen'}]);
    unlink "$tmpDir/$file";
    my $err = copyordie($file, $tmpDir);
  }

  closedir(DIR);

  # Copy sysgen.sgxco and coregen.log file to $netlist/sysgen, if those
  # files exists. If not it means cores were pulled from cache
  my $ignoreerr;

  opendir (CG_TEMP_DIR, "$coregenTmpDir") || &copyCoregenWork("couldn't open directory");
  foreach $file (readdir (CG_TEMP_DIR)) {
    if ($file =~ /\.coe$/i) {
       $ignoreerr =  copywitherror("$coregenTmpDir/$file", "$tmpDir/$file");
    }
  }
  closedir (CG_TEMP_DIR);

  $ignoreerr = copywitherror("$coregenTmpDir/coregen.cgxco", "$tmpDir/coregen.cgxco");
  $ignoreerr = copywitherror("$coregenTmpDir/coregen.cgp", "$tmpDir/coregen.cgp");
  $ignoreerr = copywitherror("$coregenTmpDir/coregen.log","$tmpDir/coregen.log");

  &__loginfo("coregenTmpDir --> << $coregenTmpDir >>");
  &__loginfo("tmpDir        --> << $tmpDir >>");

  chdir $tmpDir;

  # If I got to this point, everything must have run correctly, otherwise
  # a croak would have run. So I can safely remove the temp dir that was
  # created.
  if ( exists($ENV{'XIL_SYSGEN_COREGEN_DEBUG'}) && $ENV{'XIL_SYSGEN_COREGEN_DEBUG'} > 0 ) {
     &mkpath("$tmpDir/coregen_work_contents",0,0777) if ( not -d "$tmpDir/coregen_work_contents");
     &Sg::copydir("$coregenTmpDir","$tmpDir/coregen_work_contents");
     &__loginfo("Copied coregen work directory contents");
  } else {
     &Sg::removeDirNoError("$coregenTmpDir");
  }
  close(GENCORESLOG);
  return $results;
}

sub copyCoregenWork() {
   my $msg = $_[0] || "Error, shutting down netlisting and/or simulation model generation.";
   if ( defined($coregenTmpDir) && -d "$coregenTmpDir" ) {
      my $ignoreerr = copywitherror("$coregenTmpDir/coregen.cgxco", "$tmpDir/coregen.cgxco");
      $ignoreerr = copywitherror("$coregenTmpDir/coregen.cgp", "$tmpDir/coregen.cgp");
      $ignoreerr = copywitherror("$coregenTmpDir/coregen.log","$tmpDir/coregen.log");
      &mkpath("$tmpDir/coregen_work_contents",0,0777) if ( not -d "$tmpDir/coregen_work_contents");
      &Sg::copydir("$coregenTmpDir","$tmpDir/coregen_work_contents");
      &__loginfo("Copied coregen work directory to $tmpDir/coregen_work_contents due to encountered error!");
   }
   close(GENCORESLOG);
   chomp ($msg);
   croak "$msg";
}


sub compressResults(){

  # compress the results (i.e., combine lines which begin with a space with the previous line)
  my($results) = @_;
  my @error_records;
  my $line;
  my($nexterrorfound) = 0;	  
  foreach $line (@$results) {
    chomp($line); 	  
    if ($line =~ /^[[:space:]]/) {
  	 push(@error_records, pop(@error_records).$line);
         $nexterrorfound = 1;
     } else {
         if ($nexterrorfound eq 1) { 
            $nexterrorfound = 0;
         }	      
         push(@error_records, $line);
     }		
  }
	return \@error_records;
}


1;
