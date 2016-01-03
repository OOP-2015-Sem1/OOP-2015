package SgConsolidate;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(consolidate);

use strict;
use warnings;

use FileHandle;
use Carp qw(croak);
use Cwd;
use Sg;
use SgNamer;
use SgXtable;
use SgDeliverFile;

#
# Returns true (false) if a given file was (was not) produced by coregen.
#
# @param $file Name fo the file.
# @param %$fileAtributes Hash table of file attributes.
#
# @return true (false) if a given file was (was not) produced by coregen.
#

sub isCoregenFile() {
  my($file, $fileAttributes) = @_;
  return undef unless defined($fileAttributes->{$file});
  return undef unless defined($fileAttributes->{$file}->{'producer'});
  return $fileAttributes->{$file}->{'producer'} eq 'coregen';
}

sub isDoNotConsolidateDirective() {
  my($file, $fileAttributes) = @_;
  return undef unless defined($fileAttributes->{$file});
  return undef unless defined($fileAttributes->{$file}->{'consolidate'});
  return $fileAttributes->{$file}->{'consolidate'} eq 'false';
}

sub isKeepBlockEntitiesSeparate() {
  my $encrypt_attribute = &Sg::getAttribute("encrypt_netlist", undef, 1);
  return $encrypt_attribute;
}

#
# Returns true (false) if a given file was (was not) produced by nonleaf
# netlisting.
#
# @param $file Name fo the file.
# @param %$fileAtributes Hash table of file attributes.
#
# @return true (false) if a given file was (was not) produced by nonleaf
#          netlisting.
#

sub isNonleafFile() {
  my($file, $fileAttributes) = @_;
  return undef unless defined($fileAttributes->{$file});
  return undef unless defined($fileAttributes->{$file}->{'producer'});
  return $fileAttributes->{$file}->{'producer'} eq 'nonleafNetlister';
}


#
# Opens a file handle for writing if it has not already been opened.
#
# @param $handle File handle to test.  If the handle is defined, it is
#        returned without doing anything more.
# @param $fileName Name of the file to write to.
#
# @return File handle for writing/appending to the file named $fileName.
#

sub openHandle() {
  my($handle, $fileName) = @_;
  return $handle if ($handle);
  $handle = new FileHandle;
  open($handle, ">> $fileName") || croak "couldn't open $fileName";
  binmode($handle);
  print CONSOLIDATE "opened file $fileName\n";
  return $handle;
}

#
# Consolidate all the files produced by coregen on each of the cores
# into the netlist based on various netlisting options.
# @param $origFiles Reference to an array of files to be delivered
# @param $origFileAttributes Reference to a Hash of delivered files and their properties
# @param $files Reference to an array of files to be delivered after consolidation
# @param $fileAttributes Reference to a hash of files and thier properties post-consolidation
# @param $hdlKind Reference to the VHDL used
# @param $vhdHandleRef File to which the consolidatable VHDL files are delivered to
# @param $verHandleRef File to which the consolidatable V files are delivered to
#
sub consolidateCoregenResults() 
{
  my($origFiles, $origFileAttributes, $files, $fileAttributes, $design, $hdlKind, $vhdHandleRef, $verHandleRef) = @_;
  my $err;
  my $file;
  foreach $file (@$origFiles) {
    next unless (&isCoregenFile($file, $origFileAttributes));
    if ($file =~ /\.vhd$/i) {
      next unless ($hdlKind =~ /^vhdl$/i);
      ${$vhdHandleRef} = &openHandle(${$vhdHandleRef}, &getLeafConsolidationFile($design, "vhdl"));
      $err = copyordie($file, ${$vhdHandleRef});
      print CONSOLIDATE "copied $file\n";
      next;
    }
    if ($file =~ /\.v$/i) {
      next unless ($hdlKind =~ /^verilog$/i);
      ${$verHandleRef} = &openHandle(${$verHandleRef}, &getLeafConsolidationFile($design, "verilog"));
      $err = copyordie($file, ${$verHandleRef});
      print CONSOLIDATE "copied $file\n";
      next;
    }
    if ($file =~ /\.edn$/i || $file =~ /\.ngc$/i || $file =~ /\.mif$/i) {
      push(@$files, $file);
      $fileAttributes->{$file}->{'producer'} = 'coregen';
      next;
    }
  }  
}

#
# Consolidate all the files produced by leav/blocks into the netlist based 
#on various netlisting options.
# @param $origFiles Reference to an array of files to be delivered
# @param $origFileAttributes Reference to a Hash of delivered files and their properties
# @param $files Reference to an array of files to be delivered after consolidation
# @param $fileAttributes Reference to a hash of files and thier properties post-consolidation
# @param $hdlKind Reference to the VHDL used
# @param $vhdHandleRef File to which the consolidatable VHDL files are delivered to
# @param $verHandleRef File to which the consolidatable Verilog files are delivered to
#
sub consolidateLeafResults() 
{
  my($origFiles, $origFileAttributes, $files, $fileAttributes, $design, $hdlKind, $vhdHandleRef, $verHandleRef) = @_;
  my $err;
  my $file;
  foreach $file (@$origFiles) {
    next if (&isCoregenFile($file, $origFileAttributes));
    next if (&isNonleafFile($file, $origFileAttributes));
    #Push only if doNotConsolidate is set for the file
    if (&isDoNotConsolidateDirective($file,$origFileAttributes)) {    
      push(@$files, $file);
      $fileAttributes->{$file} = &SgXtable::copy($origFileAttributes->{$file});
      next;
    }
    if ($file =~ /\.vhd$/i) {     
      ${$vhdHandleRef} = &openHandle(${$vhdHandleRef}, &getLeafConsolidationFile($design, "vhdl"));
      $err = copyordie($file, ${$vhdHandleRef});
	  print CONSOLIDATE "copied $file\n";
      next;
    }
    if ($file =~ /\.v$/i) {      
      ${$verHandleRef} = &openHandle(${$verHandleRef}, &getLeafConsolidationFile($design, "verilog"));
      $err = copyordie($file, ${$verHandleRef});
	  print CONSOLIDATE "copied $file\n";
      next;
    }
    push(@$files, $file);
  } 
}

sub getLeafConsolidationFile()
{
  my ($design, $hdlKind) = @_;
  my $leafConsolidateFile = "$design";
  if ( isKeepBlockEntitiesSeparate() ) {
    $leafConsolidateFile = "block_entities";
  }
  if ($hdlKind =~ /^vhdl$/i) {
    $leafConsolidateFile = $leafConsolidateFile . ".vhd";
  }
  else {
    $leafConsolidateFile = $leafConsolidateFile . ".v";
  }
  return $leafConsolidateFile;
}

#
# Consolidates the files in a Sysgen design, and adjusts the "files" list
# and the "fileAttributes" map in the synopsis accordingly.
#

sub consolidate() {
open(CONSOLIDATE, "> consolidate.log") || croak "couldn't open consolidate.log";
binmode CONSOLIDATE;
  # Read synopsis to obtain original "files" list and "fileAttributes" map.
  my $synopsis = &Sg::evaluateSynopsis();
  my $globals = $synopsis->{'attributes'};
  my $origFiles = $globals->{'files'};
  my $origFileAttributes = $globals->{'fileAttributes'};


  # Form names for consolidated HDL files.
  my $design = $globals->{'design'};
  if (&SgNamer::isToken($design)) {
    my $spelling =
      $globals->{'entityNamingInstrs'}->{'nameMap'}->{$design}->{'spelling'};
    $spelling || croak "expected to find spelling for token $design";
    $design = $spelling;
  }
  $design = &SgNamer::strictHdlify($design);
  my $hdlKind = $globals->{'hdlKind'};
  my $designVhd = "$design.vhd";
  my $designVer = "$design.v";

  # Add relevant coregen files to consolidated results.
  my($vhdHandle, $verHandle);
  $vhdHandle = undef;
  $verHandle = undef;

  my $files = [];
  my $fileAttributes = {};
  my $file;
  print CONSOLIDATE join(" ", @$origFiles) . "\n";
  my $err;
  #Override the destination files if leaves to be netlisted separately

  &consolidateCoregenResults($origFiles, $origFileAttributes, $files, $fileAttributes, $design, $hdlKind, \$vhdHandle, \$verHandle);
  
  &consolidateLeafResults($origFiles, $origFileAttributes, $files, $fileAttributes, $design, $hdlKind, \$vhdHandle, \$verHandle);
  
  ##Leaf consolidation Done cleanup stuff if necessary
  if ( isKeepBlockEntitiesSeparate() ){
    if (defined($vhdHandle)){
      close($vhdHandle);
      $vhdHandle = undef;
    }
    if (defined($verHandle)){
      close($verHandle);
      $verHandle = undef;
    }
  }

  #Push NonLeaf Results to the appropriate file
  # Add appropriate files from nonleaves to consolidated results.  
  foreach $file (@$origFiles) {
    next unless (&isNonleafFile($file, $origFileAttributes));
    
    if ($file =~ /\.vhd$/i) {
      $vhdHandle = &openHandle($vhdHandle, $designVhd);
      $err = copyordie($file, $vhdHandle);
	  print CONSOLIDATE "copied $file\n";
      next;
    }
    if ($file =~ /\.v$/i) {
      $verHandle = &openHandle($verHandle, $designVer);
      $err = copyordie($file, $verHandle);
	  print CONSOLIDATE "copied $file\n";
      next;
    }
    push(@$files, $file);
  }

  # Add consolidated files to file list.
  if (defined($verHandle)) {
    close($verHandle) || croak "trouble writing $designVer";
    if ( isKeepBlockEntitiesSeparate() ){
      push(@$files, &getLeafConsolidationFile($design, "verilog"));
    }
    push(@$files, $designVer);
    
  }
  if (defined($vhdHandle)) {
    close($vhdHandle) || croak "trouble writing $designVhd";
    #print CONSOLIDATE "char count in file just written: " . `wc xlmult_tb.vhd` . "\n";
    if ( isKeepBlockEntitiesSeparate() ){
      push(@$files, &getLeafConsolidationFile($design, "vhdl"));
    }
    push(@$files, $designVhd);
  }

  close(CONSOLIDATE) || croak "trouble writing consolidate.log";
  $globals->{'designFile'} = $hdlKind =~ /^vhdl$/i? $designVhd : $designVer;
  $globals->{'files'} = $files;
  $globals->{'fileAttributes'} = $fileAttributes
    if (scalar(keys(%$fileAttributes)));

  # Write updated synopsis.
  &Sg::writeSynopsis($synopsis);
  return undef;
}


1;
