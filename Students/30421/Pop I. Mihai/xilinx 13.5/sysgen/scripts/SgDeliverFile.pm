package SgDeliverFile;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(deliverFile saveCollaborationInfo wrapup isUseCommonLib replaceConvPkgWithCommonLib);

use strict;

use File::Basename;
use File::Copy;
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;
use SgXtable;
use Template;

use vars qw($collaborations $fileDeliveryInfo $fileOrder);

#
# Calls the Template::Toolkit text processor to process and deliver a file.
#
# @param $sf Name of the source file.
# @param $df Name of the destination file (undef if we should use a default).
#        Relative names are interpreted with respect to the target directory.
# @param %$templateKeyValues Keys/values that should be used by the text
#        processor.
#
# @return Name of the file that was created if text processing results were
#         stored in an individual file; undef if results were adjoined to a
#         shared file.
#

sub callTextProcessor {
  my($sf, $df, $templateKeyValues) = @_;

  # Read source file into local array.
  open(SOURCE, "< $sf") || croak "Couldn't open $sf.\n";
  my @text = <SOURCE>;
  close(SOURCE) || croak "Trouble reading $sf.\n";
  chomp(@text);

  # Process source file text with Template::Toolkit text processor, using
  # template key/values as Template::Toolkit symbol table.
  my @newText;
  my $textString = join("\n", @text);
  my $tt = Template->new({OUTPUT => \@newText});
  $tt->process(\$textString, $templateKeyValues) || croak "$tt->error\n";

  # Write text processor results to the appropriate file handle.
  my($handle, $destFile) = &Sg::getFileHandle($sf, $df, ">>");
  my $line;
  my $useCommonLib = isUseCommonLib();
  my $common_lib_name = &Sg::getAttribute("common_lib_name", undef, 1);
  foreach $line (@newText) {
    if ($useCommonLib == 1){
        my @lines = split(/\n/, $line);
        my $line_of_lines;        
        foreach $line_of_lines (@lines){
          replaceConvPkgWithCommonLib($line_of_lines . "\n", $handle, $common_lib_name);
        }
    } 
    else {
        print $handle "$line\n";
    }
  }

  # Close file handle and report name of delivered file if appropriate.
  if ($destFile) {
    close($handle) || croak "Trouble delivering $destFile\n";
    return $destFile;
  }

  $handle->flush;
  return undef;
}


#
# Bundles an array of hashes to produce a single hash.  Each value in the
# result is an array formed by aggregating the corresponding values
# from the individual hashes.
#
# Example: Suppose this member is presented with the following:
#   [ { "a" => "b", "c" => "d" }, { "a" => "x" } ].
# Then the result returned is
#   { "a" => [ "b", "x" ] , "c" => [ "d", undef ] }.
#
# @param @$a array of hashes to bundle.
#
# @return result of bundling @$a.
#

sub bundleHashes {
  my $a = $_[0];

  # Make a first pass through the individual hashes in the array to make the
  # initial version of the bundle.  Every key that shows up in an individual
  # hash becomes a key in the bundle.
  my $bundle = {};
  my $h;
  foreach $h (@$a) {
    my $key;
    foreach $key (keys %$h) {
      $bundle->{$key} = [];
    }
  }

  # Make a second pass to build one big hash that bundles the values from the
  # individual hashes.
  foreach $h (@$a) {
    my $key;
    foreach $key (keys %$bundle) {
      push(@{$bundle->{$key}}, $h->{$key});
    }
  }
  return $bundle;
}

#
# Copies a file (which may be text or binary) from a source to a destination.
# When the destination is not specified explicitly, the file is copied
# to a default location.
#
# @param $sf Name of the source file.
# @param $df Name of the destination file, or undef if a default destination
#        should be used.
#
# @return Name of the file that was created, or undef if text was added to
#         the end of a shared destination file.  Name is expressed
#         relative to the target directory.
#
sub copyFile {
  my($sf, $df) = @_;
  my($fileEncrypted) = &isFileEncrypted($sf);
  my($mode) = ">>";
  if ( $fileEncrypted == 1 ) {
    $mode = ">";
  }
  # Append contents of a file to appropriate location
  my($handle, $destFile) = &Sg::getFileHandle($sf, $df, $mode);
  -e $sf || croak "Could not copy file $sf: File does not exist.\n";
  my $useCommonLib = isUseCommonLib();
  my $common_lib_name = &Sg::getAttribute("common_lib_name", undef, 1);
  if ( $useCommonLib == 1 && $fileEncrypted !=1 ){        
      open(SOURCE_FILE, "< $sf") || croak "Couldn't open $sf.\n";
      my @lines = <SOURCE_FILE>;
      my $line;
      foreach $line (@lines) {
        replaceConvPkgWithCommonLib($line, $handle, $common_lib_name);
      }
      close(SOURCE_FILE);      
  } 
  else {
      copy($sf, $handle) || croak "Trouble delivering $sf.";
  }
  if ($destFile) {
    close($handle) || croak "Trouble writing $destFile.\n";
    return &basename($destFile);
  }

  $handle->flush;
  return undef;
}

sub isFileEncrypted {
    my($sf) = @_;
    my($encryptionTag) = "";
    open(SOURCE_FILE, "< $sf");
    my($succ) = read SOURCE_FILE, $encryptionTag, 4;
    close(SOURCE_FILE);
    if ($succ != 0) {
        if ($encryptionTag eq "XlxV") {
            return 1;
        }
    }
    return 0;
}

#
# Checks if common library file creation has been done in a common_lib 
# package.
#

sub isUseCommonLib()
{
  my $use_common_lib = &Sg::getAttribute("use_common_lib", undef, 1);
  my $common_lib_name = &Sg::getAttribute("common_lib_name", undef, 1);
  return ( defined($use_common_lib) && defined($common_lib_name) );
}

#
# Checks if common library file creation has been done in a common_lib 
# package.
#
sub replaceConvPkgWithCommonLib()
{
  
  my($line, $handle, $common_lib_name) = @_;
  my($line, $handle) = @_;
  if ($line =~ m/use work.conv_pkg.all;/){
    print $handle "library $common_lib_name;\n";
    print $handle "use $common_lib_name.conv_pkg.all;\n";
    return;
  }
  if ($line =~ m/use work.clock_pkg.all;/){
    print $handle "library $common_lib_name;\n";
    print $handle "use $common_lib_name.clock_pkg.all;\n";
    return;
  }
  print $handle "$line";
  return;
}

#
# Test whether it is a mixed language mode
#
# @param hdlKind the connectivity netlist language
# @param sf source file
#
# @return true if it is mixed language
#
sub isMixedLanguageMode() {
  my($hdlKind, $file) = @_;
  my $mixedLanguageMode = 0;  
  if ($hdlKind =~ /^vhdl$/i) {
    if ($file =~ /\.v$/i) {
      $mixedLanguageMode = 1;
    } 
  } 
  if ($hdlKind =~ /^verilog$/i) {    
    if ($file =~ /\.vhd$/i) {      
      $mixedLanguageMode = 1;
    } 
  }
  return $mixedLanguageMode;
}
#
# Test if file needs to be forced to be netlisted separately
#
# @param sf source file name
# @return true if needs to be kept separate from rest of the netlist
#
sub forceSeparateFile() {  
  my ($sf) = shift;
  my($hdlKind) = &Sg::getAttribute("hdlKind", undef, 1);
  if (!defined($hdlKind)) {
    return 0; 
  }
  my($forceFlag) = &isMixedLanguageMode($hdlKind, $sf);
  return $forceFlag;
}
#
# Delivers files by calling text processor or by copying.
#
# @return Array of names of files that were written,
#
sub deliverFiles {
  # Deliver each file as specified by file delivery instructions.
  my $results = [];
  my $fi;
  my $destFiles = {};
  foreach $fi (@$fileDeliveryInfo) {
    my $sf = $fi->{"sourceFile"};
    my $df = $fi->{"destFile"};
    my $tkv = $fi->{"templateKeyValues"};
    my $consolidationFlag = $fi->{"consolidate"};
    my $hdlLibrary = $fi->{"hdl_library"};
    $tkv = &bundleHashes($tkv) if (ref($tkv) eq "ARRAY");
    my $result = scalar(keys %$tkv)?
   					&callTextProcessor($sf, $df, $tkv) : &copyFile($sf, $df);
    #Enter scripting results once per new destination file    
    if (defined($df)){
      next if $destFiles->{$df};
      $destFiles->{$df} = 1;
    }
    #Insert Library File attribute if required
    if ( defined($hdlLibrary) && ($hdlLibrary ne "work") && ($hdlLibrary ne "") ) {
      if (!defined($fi->{"fileAttributes"})){
        $fi->{"fileAttributes"} = {};
      }
      $fi->{"fileAttributes"}->{'libName'} = $hdlLibrary;
    }
    #Insert Consolidation file attribute if required
    if ($consolidationFlag eq "false"){  
      if (!defined($fi->{"fileAttributes"})){
        $fi->{"fileAttributes"} = {};
      }
      $fi->{"fileAttributes"}->{'consolidate'} = 'false';
    } 
    push(@$results, [$result, $fi->{"fileAttributes"}]) if ($result);
  }

  return $results;
}


#
# Wraps up everything that is pending.  In particular, this member wraps up
# collaborations and closes all open file handles.
#
# @return Array telling names of generated files, expressed relative to
#         the target directory.
#

sub wrapup {
  # Deliver files.
  my $results = &deliverFiles();

  # Close any file handles that are still open, and return the names
  # of the associated files.
  push(@$results, @{&Sg::closeFileHandles()});

  return $results;
}


#
# Saves template-processing instructions so they can be executed later.
#
# @param %$instrs Instructions to be saved.
# @param $fromCollab true (false) if instructions are (are not) part of a
#        collaboration.
#
# @return undef.
#

sub saveFileDeliveryInfo {
  my($instrs, $fromCollab) = @_;

  # Make source and destination file names canonical.
  my $sf = &Sg::makeSourceFileCanonical($instrs->{"sourceFile"});
  $instrs->{"sourceFile"} = $sf;
  if (&forceSeparateFile($sf)) {
    # Currently force separate file only checks for mixed language issues
    # if the destFile is defined in the instructions then we do not
    # really want to modify it 
    if (!defined($instrs->{"destFile"})) {
        $instrs->{"destFile"} = &basename($sf);  
        $instrs->{"consolidate"} = 'false';
    }
  }
  my $fa = $instrs->{"fileAttributes"};  
  my $df = &Sg::makeDestFileCanonical($instrs->{"destFile"},
             $instrs->{"sourceFile"}, $fa);
  $instrs->{"destFile"} = $df;

  # Find/make an array that saves information for file delivery.
  $fileDeliveryInfo = [] unless ($fileDeliveryInfo);

  # If these instructions are part of a collaboration, then save them
  # as part of the info for the first collaborator.
  if ($fromCollab) {
    $collaborations = {} unless ($collaborations);
    my $c = $collaborations->{$sf};
    if (! $c) {
      $c = &SgXtable::copy($instrs);
      $c->{"templateKeyValues"} = [];
      $collaborations->{$sf} = $c;
      push(@$fileDeliveryInfo, $c);
    }
    push(@{$c->{"templateKeyValues"}},
      &SgXtable::copy($instrs->{"templateKeyValues"}));
    if ($fa) {
      $c->{"fileAttributes"} = {} unless ($c->{"fileAttributes"});
      my $key;
      foreach $key (keys %$fa) {
        $c->{"fileAttributes"}->{$key} = &SgXtable::copy($fa->{$key});
      }
    }
  }
  else {
    push(@$fileDeliveryInfo, $instrs);
  }

  return undef;
}


#
# Saves collaboration template-processing instructions so they can be batched
# and executed later.
#
# @param %$instrs Instructions to be saved.
#
# @return undef.
#

sub saveCollaborationInfo {
  my $instrs = $_[0];
  return &saveFileDeliveryInfo($instrs, 1);
}


#
# Saves template-processing instructions so they can be executed later.
#
# @param %$instrs Instructions to be saved.
#
# @return undef.
#

sub deliverFile {
  my $instrs = $_[0];
  return &saveFileDeliveryInfo($instrs, 0);
}


1;
