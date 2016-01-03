package Sg;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(closeFileHandles evaluate evaluateSynopsis getAttribute findAttribute
            findFamilyPartPackageSpeed findFamilyPartPackageSpeedFromAttributes
            getFileHandle getTmpDir setAttributes
            updateFilesList writeLegacyFiles writeSynopsis
            findMatlabJava findSysgen scrubName findMatchingLines open_
            findIseVer remove removeDir removeFile getFileSize buildPath
            checkLog copywitherror copyordie compareSynopsis isNewXSTParser);

use strict;
use warnings;

use File::Temp qw(tempdir);
use File::Spec;
use File::Basename;
use File::Copy;
use FileHandle;
use Config;
use Carp qw(croak);

use SgEnv;
use SgXtable;

use vars qw($tmpDir $attributes);

$|++;

#
# Makes/finds the temporary directory in which the netlister is generating
# files.  Note: Ordinarily this directory is created elsewhere and specified
# in the attributes for the design.  When not created elsewhere, this member
# creates one.
#

sub getTmpDir {
  return $tmpDir if ($tmpDir);
  $tmpDir = $attributes->{"tmpDir"};
  return $tmpDir if ($tmpDir);
  $tmpDir = &SgEnv::canonpath(tempdir("sysgen_XXXX", TMPDIR => 1));
  return $tmpDir;
}

#
# Checks if the device uses the old XST parser
# This function was written because of change
# in the behavior of XST
#
#   @param family device family being targetted
#   @return 1 if new XST parser is used for the family else 0
sub isNewXSTParser() {
    my $family = $_[0];
    my @families = ("spartan3", "virtex4", "virtex5");
    my $fam;
    foreach $fam (@families) {
        if ($family =~ m/($fam)/i) {
            return 0;
        }
    }
    return 1;
}

#
# Compares a file name against a directory name, then returns one of two things:
#   1) If the file is in the tree rooted by the directory, then the relative
#      path from the directory to the file is returned;
#   2) Otherwise, the file name is returned unaltered.
#
# @param $dir Directory name to test.
# @param $fileName File name to test.
#
# @return File name altered as described above.
#

sub makeFilenameRelative {
  my($dir, $fileName) = @_;
  $dir = &SgEnv::canonpath($dir);
  $dir .= "\\" unless ($dir =~ /\\$/);
  my $fn = &SgEnv::rel2abs($fileName);
  return $fileName if (length($fn) < length($dir));
  my $prefix = substr($fn, 0, length($dir));
  return $fileName unless ($prefix eq $dir);
  return substr($fn, length($dir));
}


#
# Returns an absolute path to a source file.  The path is formed
# in one of two ways:
#   1) If the file name is absolute, it is returned unchanged;
#   2) Otherwise, the file name is prefixed by the path to the sysgen
#      directory, i.e., the directory in which Sysgen is installed.
#
# @param $fileName Source file name to be made canonical.
#
# @return Absolute file name formed as described above.
#

sub makeSourceFileCanonical {
  my $fileName = $_[0];
  return $fileName if (File::Spec->file_name_is_absolute($fileName));
  my $sysgen = &Sg::getAttribute("sysgen");
  return File::Spec->catfile($sysgen, $fileName);
}


#
# Returns an absolute path to a destination file.
#
# @param $destFile Destination file name to be made canonical.
# @param $sourceFile Name of the source file.
# @param $fileAttributes Attributes (if any) to be associated to the file.
#
# @return Absolute path to the destination file.
#

sub makeDestFileCanonical {
  my($destFile, $sourceFile, $fileAttributes) = @_;
  $destFile = &basename($sourceFile) if (! $destFile && $fileAttributes);
  return undef unless ($destFile);
  return $destFile if (File::Spec->file_name_is_absolute($destFile));
  return File::Spec->catfile(&getTmpDir(), $destFile);
}


#
# Returns the file handle to which results should be written.  Results are
# produced either by running a text processor on a source file, or
# by copying the source file without change.
#
# @param $sf Name of the source file.
# @param $df Name of the destination file, or undef if the results should be
#        written to a default location.
# @param $permisiion open destination file with the appropriate permisiions
#        '>' or '>>' etc.
#
# @return $handle File handle to which results should be written.
# @return $destName Undef if the results were written to a shared file;
#         otherwise, name of the destination file, expressed relative to the
#         target directory.  When this return value is not undef, it is
#         the caller's responsibility to close the file handle.
#

sub getFileHandle {
  my($sf, $df, $permission) = @_;

  # Get name of temp directory to which we are delivering files.
  my $tmpDir = &getTmpDir();

  # If name of the destination file is specified explicitly, then use it.
  if ($df) {
    if (!defined($permission)) {
      $permission = '>';
    }
    chmod 0777, $df || croak "couldn't change permissions on $df";
    my $h = new FileHandle;
    open($h, "$permission $df") || croak "couldn't open $df";
    binmode $h;
    return ($h, &makeFilenameRelative($tmpDir, $df));
  }

  # Otherwise, deliver file to default location.  First step is to search
  # default delivery table for key that matches source file name.  Example
  # table:
  #
  #   [
  #     ["(?i)\\.vhd$", { "fileName" => "xlmult_tb.vhd"}],
  #     ["(?i)\\.v$",   { "fileName" => "xlmult_tb.v"  }],
  #   ]
  #
  # Each entry in the table is a (pattern, destination info) pair.
  # File names are matched against the pattern (which may, as shown in the
  # example above, contain modifiers).  When a file name matches, the file
  # is delivered to the specified destination.  Thus, in the example, files
  # whose names end with ".vhd" or ".VHD" are adjoined to the end of a file
  # named "xlmult_tb.vhd" in the netlister temp directory.  Similarly, files
  # whose names end with ".v" or ".V" are adjoined to "xlmult_tb.v".
  my $t;
  my $fileDeliveryDefaults = &Sg::getAttribute("fileDeliveryDefaults");
  $fileDeliveryDefaults = [] unless ($fileDeliveryDefaults);
  foreach $t (@$fileDeliveryDefaults) {
    my $pattern = $t->[0];
    next unless ($sf =~ /$pattern/);
    my $fi = $t->[1];
    my $handle = $fi->{"handle"};
    if (! $handle) {
      my $file = &makeDestFileCanonical($fi->{"fileName"});
      chmod 0777, $file || croak "couldn't change permissions on $file";;
      $handle = new FileHandle;
      open($handle, "> $file") || croak "couldn't open $file";
      binmode $handle;
      $fi->{"handle"} = $handle;
    }
    return ($handle, undef);
  }

  # No pattern in the default delivery table matched the source file name,
  # so deliver file to temp directory.  Name for delivered file is the
  # base name of the source file.
  my $file = &makeDestFileCanonical(&basename($sf));
  chmod 0777, $file || croak "couldn't change permissions on $file";;
  my $h = new FileHandle;
  open($h, "> $file") || croak "couldn't open $file";
  binmode $h;
  return ($h, &makeFilenameRelative($tmpDir, $file));
}


#
# Closes all file handles for shared files, then returns a list of file names
# corresponding to the handles that were closed.
#
# @return List of names of shared files.
#

sub closeFileHandles {
  # Search default delivery table for open file handles.
  my $fileDeliveryDefaults = &Sg::getAttribute("fileDeliveryDefaults");
  return [] unless ($fileDeliveryDefaults);
  my $files = [];
  my $q;
  foreach $q (@$fileDeliveryDefaults) {
    my $handle = $q->[1]->{"handle"};
    my $fileName = $q->[1]->{"fileName"};
    if ($handle) {
      push(@$files, &makeFilenameRelative($tmpDir, $fileName));
      close($handle) || croak "trouble writing " . $fileName;
    }
  }
  return $files;
}


#
# Extracts the Xilinx family from a full part name.  For example, if the full
# part name is "xcv1000-7fg680", this member returns "Virtex".
#
# @param $fullPart Full Xilinx name for the part to use.  Example:
#        xcv1000-7fg680s.
#
# @return Family to be used, or undef if the part could not be deciphered.
#

sub extractFamily {
  my $partInfo = $_[0];
  return "virtex2"   if ($partInfo =~ /^xc2v\d+/i);
  return "virtex4"   if ($partInfo =~ /^xc4v.+/i);
  return "virtex5"   if ($partInfo =~ /^xc5v.+/i);
  return "virtex6l"   if ($partInfo =~ /^xc6v.+l/i);
  return "virtex6"   if ($partInfo =~ /^xc6v.+/i);
  return "virtex2p"  if ($partInfo =~ /^xc2vpx?\d+/i);
  return "virtexe"   if ($partInfo =~ /^xcv\d+e/i);
  return "virtex"    if ($partInfo =~ /^xcv\d+/i);
  return "spartan2e" if ($partInfo =~ /^xc2s\d+e/i);
  return "spartan2"  if ($partInfo =~ /^xc2s\d+/i);
  return "spartan3e"  if ($partInfo =~ /^xc3s\d+e/i);
  return "spartan3a"  if ($partInfo =~ /^xc3s\d+a/i);
  return "spartan3adsp"  if ($partInfo =~ /^xc3s[dp]\d+a/i);
  return "spartan3"  if ($partInfo =~ /^xc3s\d+/i);
  return "spartan6"  if ($partInfo =~ /^xc6s.+/i);
  return "qvirtex4"  if ($partInfo =~ /^xq4.+/i);
  return "qrvirtex4"  if ($partInfo =~ /^xqr.+/i);
  return "qvirtex5"  if ($partInfo =~ /^xq5.+/i);
  return undef;
}


#
# Returns a hash table telling the family, part, package, and speed encoded in
# a string.  For example, if the string is "xcv1000-7fg680", this member
# returns
#   { "family" => "virtex", "part" => "xcv1000",
#     "package" => "fg680", "speed" => 7 }
#
# @param partInfo Holds the string describing the device.
#
# @return Hash table describing the part, or undef if the string describing the
#         device is badly formed.
#

sub findFamilyPartPackageSpeed {
  my $partInfo = $_[0];
  $partInfo =~ tr/A-Z/a-z/;
  my $family = &extractFamily($partInfo);
  return undef unless (defined($family));
  $partInfo =~ /^(.*)-(\d+l?)(.*)$/ || return undef;
  my($part, $package, $speed) = ($1, $3, $2);
  my $h = {
    "family" => $family,
    "part" => $part,
    "package" => $package,
    "speed" => $speed,
  };
  return $h;
}


#
# Returns a hash table telling the family, part, package, and speed
# from attributes
#   { "family" => "virtex", "part" => "xcv1000",
#     "package" => "fg680", "speed" => 7 }
#
# @param attribute containing netlist attributes
#
# @return Hash table describing the part, or undefin not found
#

sub findFamilyPartPackageSpeedFromAttributes {
  my $attributes = $_[0];
  #Extract Information from attributes. These
  #keys have been selected to conform to union
  #of supported technologies
  my $family = &getAttribute("xilinxfamily",$attributes,1);
  $family =~ tr/A-Z/a-z/;
  my $part = &getAttribute("xilinx_part",$attributes,1);
  $part =~ tr/A-Z/a-z/;
  my $speed = &getAttribute("device_speed",$attributes,1);
  $speed = "$speed";
  $speed =~ tr/A-Z/a-z/;
  $speed =~ s/\-//;#Remove - to maintain backward compatibility
  my $package = &getAttribute("xilinx_package",$attributes,1);
  $package =~ tr/A-Z/a-z/;
  #Pak into a hash
  my $h = {
    "family" => $family,
    "part" => $part,
    "package" => $package,
    "speed" => $speed,
  };
  return $h;
}
# Saves an XTable that specifies attributes for the design.
#
# @param attrs XTable of attributes to save.
#

sub setAttributes {
  my $attrs = $_[0];
  $attributes = &SgXtable::copy($attrs);
  return undef;
}


#
# Returns an attribute value found as follows:
#
#   1) A given hash table is searched.
#   2) If the hash table does not define the attribute, the "attributes"
#      hash table is searched.
#   3) If the attributes hash table is undefined, the table is populated by
#      reading the synopsis in the working directory, and the table is
#      searched again.
#
# If the attribute cannot be found, this member does one of two things:
#
#   1) If $allowMissingValue is true, undef is returned;
#   2) Otherwise, this member dies.
#
# @param $attrName name of the attribute to find.
# @param %$h hash table to search for the attribute.  This parameter can be
#        omitted, in which case the search is done only within the ordinary
#        global attributes.
# @param $allowMissingValue tells whether a missing value is acceptable.
#
# @return value of the attribute named $attrName.  The attribute is located by
#         searching as described above.
#

sub getAttribute {
  my($attrName, $h, $allowMissingValue) = @_;
  return $h->{$attrName} if ($h && exists($h->{$attrName}));

  if (!$attributes) {
    if (-e "synopsis") {
      my $synopsis = &evaluateSynopsis();
    }
    elsif (!$allowMissingValue) {
      croak "expected to find synopsis in working directory " .
        "(looking for attribute '$attrName')";
    }
  }

  return $attributes->{$attrName} if exists($attributes->{$attrName});
  return undef if ($allowMissingValue);
  croak "found no definition for attribute '$attrName'";
}

sub findAttribute {
  my($attrName, $h) = @_;
  return 1 if ($h && exists($h->{$attrName}));

  if (!$attributes) {
    if (-e "synopsis") {
      my $synopsis = &evaluateSynopsis();
    }
    else {
      croak "expected to find synopsis in working directory " .
        "(looking for attribute '$attrName')";
    }
  }

  return 1 if exists($attributes->{$attrName});
  return 0;
}


#
# Returns a "cleaned-up" version of a string.  Leading and trailing white
# space is trimmed, and funny characters are turned into underscores.
#
# @param $s String to be scrubbed.
#
# @return Scrubbed version of $s.
#

sub scrubName {
  my($s) = @_;

  # Transform each non-word character to an underscore.
  $s =~ s/\W/_/g;

  # Trim leading and trailing underscores, and replace sequences of two or more
  # underscores with single underscores.
  $s =~ s/^_+//;
  $s =~ s/_+$//;
  $s =~ s/_+/_/g;

  # Downcase.
  $s =~ tr/A-Z/a-z/;

  # Make sure string is nonempty.
  $s = "x" if (length($s) == 0);

  return $s;
}


#
# Evaluates the Perl text in a given file, then returns the results.
#
# @param $file Name of the file to evaluate.
#
# @return Results of doing a Perl "eval" on the text in the file.
#

sub evaluate {
  my $file = $_[0];

  -e $file || croak "there is no $file";
  -f $file || croak "$file is not a plain file";
  -r $file || croak "$file is not readable";
  my $handle = new FileHandle;
  open($handle, "< $file") || croak "couldn't open $file";
  my @text = <$handle>;
  close($handle) || croak "trouble reading $file";
  chomp(@text);
  my $results = eval(join("\n", @text));
  croak $@ if ($@);
  return $results;
}


#
# Evaluates the file named "synopsis" in the working directory, then returns
# the results.  This member also sets the %$attributes variable to the
# global attributes defined by the synopsis.
#
# @return Results of doing a Perl "eval" on the file named "synopsis" in the
#         working directory.
#

sub evaluateSynopsis {
  my $synopsis = &evaluate("synopsis");
  $attributes = &SgXtable::copy($synopsis->{"attributes"});
  return $synopsis;
}

#
# Adjoins a given list of files to the "files" list in a given synopsis.
#
# @param $synopsis Synopsis to update.
# @param @$newFiles List of files to adjoin to the "files" list of the synopsis
#        in the working directory.
#

sub updateFilesList {
  my($synopsis, $newFiles) = @_;

  # Find files already listed in the synopsis.
  my $filesAlreadyListed = {};
  my $file;
  my $files = $synopsis->{"attributes"}->{"files"};
  foreach $file (@$files) {
    $filesAlreadyListed->{$file} = 1;
  }

  # Add new files to the synopsis.
  foreach $file (@$newFiles) {
    push(@$files, $file) unless ($filesAlreadyListed->{$file});
    $filesAlreadyListed->{$file} = 1;
  }
}


#
# Writes the synopsis to a file named "synopsis" in the working directory.
#
# @param $synopsis Synopsis to write.
#

sub writeSynopsis {
  my $synopsis = $_[0];

  my $handle = new FileHandle;
  open($handle, "> synopsis") || croak "couldn't write synopsis";
  binmode $handle;
  print $handle &SgXtable::toString($synopsis) . "\n";
  close($handle) || croak "trouble writing synopsis";
  return undef;
}


#
# Writes the "globals" file that older Sysgen scripts expect.  The file is
# written to the working directory.
#
# @param $attrs Attributes to use when constructing the "globals" file.
#

sub writeGlobalsFile {
  my $attrs = $_[0];

  # Write key/values that 1) must be defined, and 2) whose values are copied
  # without change.
  my $globals = {};
  my $perlSpelling = {
    "clkWrapper" => "clkWrapper",
    "clkWrapperFile" => "clkWrapperFile",
    "design" => "design",
    "device" => "device",
    "partFamily" => "family",
    "files" => "files",
    "hdlKind" => "hdlKind",
    "synthesisTool" => "synthesisTool",
    "sysgen" => "sysgen",
    "systemClockPeriod" => "systemClockPeriod",
    "xilinx" => "XILINX",
  };
  my $key;
  foreach $key (sort(keys %$perlSpelling)) {
    my $ps = $perlSpelling->{$key};
    my $value = $attrs->{$key};
    defined($value) || croak "attribute '$key' is not defined";
    $globals->{$ps} = $value;
  }

  # Write key/values that 1) might not be there, and/or 2) whose values must be
  # translated.
  $globals->{"using71Netlister"} = 1;
  $globals->{"isCombinatorial"} = $attrs->{"isCombinatorial"}? 1 : 0;
  $globals->{"createTestbench"} = $attrs->{"createTestbench"}? 1 : 0;
  $globals->{"vsimtime"} =
    $attrs->{"vsimtime"} if (defined($attrs->{"vsimtime"}));
  $globals->{"testbench"} =
    $attrs->{"testbench"} if (defined($attrs->{"testbench"}));
  $globals->{"testbenchFile"} =
    $attrs->{"testbenchFile"} if (defined($attrs->{"testbenchFile"}));
  my @dfl;
  my $file;
  foreach $file (@{$attrs->{"files"}}) {
    next unless ($file =~ /\.vhd$/i || $file =~ /\.v$/i);
    push(@dfl, $file);
  }
  $globals->{"designFileList"} = \@dfl;

  # Write globals to a file named "globals" in the working directory.
  unlink "globals";
  ! -e "globals" || croak "couldn't remove old globals file";
  my $handle = new FileHandle;
  open($handle, "> globals") || croak "couldn't write globals file";
  binmode $handle;
  print $handle &SgXtable::toString($globals) . "\n";
  close($handle) || croak "trouble writing globals file";
  return undef;
}


#
# Writes the "hdlFiles" file that older Sysgen scripts expect.  The file is
# written to the working directory.
#
# @param $@files List of all files generated so far.
#

sub writeHdlFilesFile {
  my $files = $_[0];

  unlink "hdlFiles";
  ! -e "hdlFiles" || croak "couldn't remove old hdlFiles";
  my $handle = new FileHandle;
  open($handle, "> hdlFiles") || croak "couldn't write hdlFiles file";
  binmode $handle;
  my $file;
  foreach $file (@$files) {
    next unless ($file =~ /\.vhd$/i || $file =~ /\.v$/i);
    print $handle "$file\n";
  }
  close($handle) || croak "trouble writing hdlFiles file";
  return undef;
}


#
# Writes the "globals" and "hdlFiles" files that older Sysgen applications
# expect.  The files are written to the working directory, and are formed
# from the global attributes defined by a given synopsis.

sub writeLegacyFiles {
  my $synopsis = $_[0];
  my $attrs = $synopsis->{"attributes"};

  # Write the "globals" file.
  &writeGlobalsFile($attrs);

  # Write the "hdlFiles" file.
  &writeHdlFilesFile($attrs->{"files"});

  # Update the synopsis file list to include "globals" and "hdlFiles", then
  # write the synopsis to a file named "synopsis" in teh working directory.
  &updateFilesList($synopsis, ["globals", "hdlFiles"]);

  return undef;
}


#
# Returns the path to java.exe in a given copy of Matlab.
#
# @param $matlab Path to matlab.exe, or to root directory of matlab (i.e.,
#        matlabroot).  When this parameter is omitted, Matlab is
#        located by searching the path.
#
# @return Path to java.exe in the Matlab tree that contains $matlab.
#

sub findMatlabJava {
  my $matlab = $_[0];

  # If the location of Matlab was not specified as a parameter, then locate
  # Matlab by searching the path.
  my $platform = &SgEnv::platform();
  my $is_64bit = &SgEnv::os_is_64bit();
  if (! $matlab) {
    $matlab = &SgEnv::getMatlabRootPath();
    $matlab || croak "couldn't find matlab in your path";
  }
  else {
    # Clean up path to Matlab.
    $matlab = &SgEnv::canonpath($matlab);
    $matlab = &dirname($matlab) unless (-d $matlab);
    if (($platform eq 'nt') || ($platform eq 'cygwin')) {
      if ($is_64bit) {
        $matlab =~ s/\/bin\/win64$//;
      }
      else {
        $matlab =~ s/\/bin\/win32$//;
      }
    }
    else {
      $matlab =~ s/\/bin$//;
    }
  }
  my $jrepath;
  if (($platform eq 'nt') || ($platform eq 'cygwin')) {
    if ($is_64bit) {
      $jrepath = "$matlab/sys/java/jre/win64";
    }
    else {
      $jrepath = "$matlab/sys/java/jre/win32";
    }
  }
  else {
    if ($is_64bit) {
      $jrepath = "$matlab/sys/java/jre/glnxa64";
    }
    else {
      $jrepath = "$matlab/sys/java/jre/glnx86";
    }
  }

  # Build path to jre.cfg file.
  my $jreCfg = &SgEnv::canonpath("$jrepath/jre.cfg");

  my $jreDirSuffix;
  if (-e $jreCfg) {
      open(JRECFG, "< $jreCfg") || croak "couldn't read $jreCfg";
      my @text = <JRECFG>;
      close(JRECFG) || croak "trouble reading $jreCfg";
      chomp(@text);
      scalar(@text) == 1 || croak "$jreCfg is badly formed";
      $jreDirSuffix = $text[0];
  }
  else {
      $jreDirSuffix = '';
  }
  my $javaDir = "$jrepath/jre$jreDirSuffix/bin";
  my $java = "$javaDir/java" . &SgEnv::exeSuffix();
  -e $java || croak "expected to find matlab java at $java";
  return &SgEnv::canonpath($java);
}


#
# Returns the path to a copy of the Sysgen software.  The copy is identified
# by searching the pathdef.m file in Matlab.  For example, if Matlab is
# stored in c:/matlabr14_1, and sysgen is installed in the default location,
# then this member returns c:/matlabr14_1/toolbox/xilinx/sysgen.
#
# @param $matlab Path to Matlab.  When this parameter is omitted, Matlab is
#        located by searching the path.
#
# @return Path to the root of the sysgen software.
#

sub findSysgen {
  my $matlab = $_[0];

  # If the location of Matlab was not specified as a parameter, then locate
  # Matlab by searching the path.
  if (! $matlab) {
    $matlab = &SgEnv::getMatlabExePath();
    $matlab || croak "couldn't find matlab in your path";
  }

  # Clean up path to Matlab.
  $matlab = &SgEnv::canonpath($matlab);
  $matlab = &dirname($matlab) unless (-d $matlab);
  my $platform = &SgEnv::platform();
  if (($platform eq 'nt') || ($platform eq 'cygwin')) {
    $matlab =~ s/\/bin\/win32$//;
  }
  else {
    $matlab =~ s/\/bin$//;
  }

  # Read pathdef.m file.
  my $pathdef = "$matlab/toolbox/local/pathdef.m";
  -e $pathdef || croak "there is no $pathdef";
  open(PATHDEF, "< $pathdef") || croak "couldn't read $pathdef";
  my @text = <PATHDEF>;
  close(PATHDEF) || croak "trouble reading $pathdef";
  chomp(@text);

  # Find a sysgen line in pathdef.m.
  @text = grep(/sysgen/i, @text);
  scalar(@text) || croak "found no sysgen lines in $pathdef";

  # Extract a sysgen line, and clean it up.
  my $sysgen = $text[0];
  $sysgen =~ tr/A-Z/a-z/;
  $sysgen =~ s/\\/\//g;
  $sysgen =~ s/^\s+//;
  $sysgen =~ s/,\s*\.\.\.\s*$//;
  $sysgen =~ s/\s+$//;
  $sysgen =~ s/matlabroot\s*,\s*//;
  $sysgen =~ s/'//g;
  $sysgen =~ s/;//;
  $sysgen =~ s/\/sysgen\/\w+$/\/sysgen/;
  $sysgen = "$matlab/$sysgen";
  -e $sysgen || croak "expected to find sysgen software at $sysgen";
  return &SgEnv::canonpath($sysgen);
}


#
# Returns an array of all lines from a given array that match one or more
# given patterns.
#
# @param @$patterns Tells the patterns to search for.
# @param @$lines    Contains the lines to be searched.
#

sub findMatchingLines {
  my($patterns, $lines) = @_;
  my @matchingLines;
  foreach my $line (@$lines) {
    foreach my $pattern (@$patterns) {
      if ($line =~ /$pattern/) {
        push(@matchingLines, $line);
        last;
      }
    }
  }
  return \@matchingLines;
}


#
# Appends a given command to a file in the working directory named
# "commandLines".
#
# @param $cmd Command to write.
#

sub saveCommand {
  my @args = @_;
  open(COMMANDLINES, ">> commandLines") ||
    croak "Error opening file 'commandLines': $!\n";
  binmode COMMANDLINES;
  if (@args == 1) {
      print COMMANDLINES "$args[0]\n";
  }
  else {
      my $space = '';   # no space on first arg
      foreach (@args) {
          if (/\s/) {
              print COMMANDLINES "$space\"$_\"";
          }
          else {
              print COMMANDLINES "$space$_";
          }
          $space = ' ';
      }
      print COMMANDLINES "\n";
  }
  close(COMMANDLINES) || croak "Error writing 'commandLines': $!\n";
}


#
# Executes a given command with a "system".  The command is saved in a file
# named "commandLines" in the working directory.
#
# @param $cmd Command to execute in "system".
#
# @return Exit status from the system call.
#

sub system_ {
  my @args = @_;

  &saveCommand(@args);
  return system(@args);
}


#
# Executes the given program with the specified parameters.  The command line
# is logged in "commandLines" in the working directory.  The results from
# STDOUT and STDERR are returned in an array.
#
# @param    $prog       Program to execute
# @param    @parameters Array of parameters to pass to program
# @return   Exit status from the system call and an array of the output lines.
#
sub runcmd ($ @) {
    my $prog = shift;
    my @args = @_;

    my $savecmd = ($prog =~ /\s/) ? "\"$prog\"" : $prog;
    my $err2out = 0;
    my $ignoreoutput = 0;
    my $errfile;
    my $outfile;
    # Handle stream redirection
    for (my $i = 0; $i < @args; $i++) {
        my $arg = $args[$i];
        if ($arg =~ /^2>&1/) {
            $err2out = 1;
            splice @args, $i, 1;
            $i--;
        }
        elsif ($arg =~ /^2>\s*(.+)/) {
            $errfile = $1;
            splice @args, $i, 1;
            $i--;
        }
        elsif ($arg =~ /^>\s*(.+)/) {
            $outfile = $1;
            splice @args, $i, 1;
            $i--;
        }
        elsif ($arg =~ /^>\s*\/dev\/null/) {
            $ignoreoutput = 1;
            splice @args, $i, 1;
            $i--;
        }
    }

    # Set up the saferun command line
    unshift @args, $prog;
    unshift @args, '--';
    if ($outfile) {
        if ($err2out) {
            unshift @args, $outfile;
            unshift @args, '-a';
        }
        else {
            unshift @args, $outfile;
            unshift @args, '-o';
        }
    }
    if ($errfile) {
        unshift @args, $errfile;
        unshift @args, '-e';
    }
    if ($ignoreoutput) {
        unshift @args, '-i';
    }

    my $bindir = &SgEnv::getSysgenBinPath();
    defined($bindir) || croak "SYSGEN environment variable not set by calling process\n";
    my $saferun = "${bindir}/saferun" . &SgEnv::exeSuffix();
    unshift @args, $saferun;
    &saveCommand(@args);

    my $exitcode = system(@args);
    $exitcode >>= 8;
    return $exitcode;
}


#
# Executes a given command with an "open".  The command is saved in a file
# named "commandLines" in the working directory.
#
# @param $handle File handle to which command stream(s) should be attached.
# @param $cmd Command to execute.
#
# @return Return value from the open.
#

sub open_ ($ $) {
  my ($handle, $cmd) = @_;

  &saveCommand($cmd);
  $cmd =~ s/\s+$//;
  $cmd .= " |" unless ($cmd =~ /|$/);
  my $status = open($handle, $cmd);
  return $status;
}


#
# Returns the version of ISE.
#
# @return ISE version, e.g., 6.3i, 7.1i
#

sub findIseVer {
  # Locate fileset.txt file in xilinx tree.
  my $xilinx = $ENV{'XILINX'};
  defined $xilinx || croak "\$XILINX is not defined";
  -e $xilinx || croak "there is no $xilinx";
  -d $xilinx || croak "$xilinx is not a directory";
  my $filesetTxt = "$xilinx/fileset.txt";
  -e $filesetTxt || croak "there is no $filesetTxt";
  -T $filesetTxt || croak "$filesetTxt is not a text file";

  open(FILE, "< $filesetTxt") || croak "couldn't open $filesetTxt";
  my @lines = <FILE>;
  close(FILE);
  chomp(@lines);
  close(FILE) || croak "trouble reading $filesetTxt";

  my $line;
  foreach $line (@lines) {
    if ($line =~ /version=(.*)$/) {
      my $iseVer  = $1;
      $iseVer =~ s/^\s+//;
      $iseVer =~ s/\s+$//;
      return $iseVer;
    }
  }
  croak "couldn't find ISE version";
}


#
# Recursively removes a directory and all of its subdirectories.  This
# subroutine works on NT even when the directory is not empty.
#
# @param $dir Directory to remove.
#

sub removeDir ($) {
  my $dir = shift;
  return unless (-e $dir);
  $dir = &SgEnv::canonpath($dir);
  my (@files) = File::DosGlob::doglob(1, "$dir/*");
  my $file;
  foreach $file (@files) {
    if (-f $file) {
      &removeFile($file);
    }
    elsif (-d $file) {
      &removeDir($file);
    }
  }

  chmod 0777, $dir;
  rmdir $dir;
  ! -e $dir || croak "couldn't remove directory $dir";
}


#
# Removes a dir and all subdirectories and files

sub removeDirNoError ($) {
  my $dir = shift;
  return unless (-e $dir);
  $dir = &SgEnv::canonpath($dir);
  my (@files) = File::DosGlob::doglob(1, "$dir/*");
  my $file;
  foreach $file (@files) {
    if (-f $file) {
      &removeFileNoError($file);
    }
    elsif (-d $file) {
      &removeDirNoError($file);
    }
  }

  chmod 0777, $dir;
  rmdir $dir;
}

#
# Removes a plain file.
#
# @param $file Plain file to remove.
#

sub removeFile ($) {
  my $file = $_[0];
  return unless (-e $file);
  chmod 0777, $file;
  unlink $file;
  ! -e $file || croak "couldn't remove plain file $file";
}


sub removeFileNoError ($) {
  my $file = $_[0];
  return unless (-e $file);
  chmod 0777, $file;
  unlink $file;
}

#
# Removes a file.  The file can be a plain file or a directory.
#
# @param $file File/directory to remove.
#

sub remove ($) {
  my $file = $_[0];
  return unless (-e $file);
  if (-d $file) {
    &removeDir($file);
  }
  else {
    &removeFile($file);
  }
}

#
# Just get the size of the requested file.
#
# @param    filename
#
sub getFileSize ($) {
  my $fn = shift;
  my @st = stat($fn);
  return $st[7];
}

#
# Given a directory and filename, join them together so there is only a single
# separator between them.
#
sub buildPath ($ $)  {
    my $path = shift;
    my $file = shift;
    $path =~ s/\/$//;
    $path =~ s/\\$//;
    $file =~ s/^\///;
    $file =~ s/^\\//;
    return "$path/$file";
}

#
# Searches a given log file for errors, and dies with an appropriate
# error if one is found.
#
# @param $tool          Tool that created the log.
# @param $errorPatterns Error patterns to look for in the log.
# @param $logFile       Log to search.
# @param $extractErrors Extract errors from the log file and
#                       report them in the error message.
#

sub checkLog {
  my ($tool, $errorPatterns, $logFile, $extractErrors) = @_;

  # Read the log into a local array.
  open(LOGFILE, "< $logFile") || croak "couldn't read $logFile";
  my @log = <LOGFILE>;
  close(LOGFILE) || croak "trouble reading $logFile";

  # Search log for errors.
  my $errors = &Sg::findMatchingLines($errorPatterns, \@log);
  if (scalar(@$errors)) {
    my $errmsg;
    if (defined($extractErrors) && $extractErrors) {
        $errmsg = "user: The following error was encountered while running ${tool}.\n" .
                  ('-' x 80) . "\n" .
                  join("", @$errors) .
                  ('-' x 80) . "\n" .
                  "Please refer to the log '$logFile' for further information.";
        croak $errmsg;
    }
    else {
        $errmsg = "user: An error was encountered while running ${tool}. " .
            "Please refer to the log '$logFile' for further information.";
        croak $errmsg;
    }
  }
  return undef;
}

sub copydir($ $) {
    my $source = shift;
    my $dest = shift;

    return if ( "$source" eq "$dest" );

    opendir(DIR, "$source");
    my @contents = grep !/\.\.?\z/, readdir(DIR);
    chomp(@contents);
    closedir(DIR);

    foreach my $file (@contents) {
       $file =~ s/\r//g;
       if ( -d "$file" ) {
           &copydir("$source/$file","$dest");
       } else {
           my $ignerr = &copywitherror("$source/$file", "$dest/$file");
       }
    }
}

#
# Copy a file, as normal, but return an error string if the copy failed.
#
sub copywitherror ($ $) {
    my $source = shift;
    my $dest = shift;

    if (!copy($source, $dest)) {
        return "Could not copy '$source' to '$dest': $!";
    }
    return '';
}

#
# Copy a file, as normal, but return an error string if the copy failed.
#
sub copyordie ($ $) {
    my $source = shift;
    my $dest = shift;

    if (!copy($source, $dest)) {
        croak "Could not copy '$source' to '$dest': $!";
    }
}

sub compareArray {
  my ($array1, $array2) = @_;

  my @common = ();
  my @added = ();
  my @removed = ();

  # Assume there is no duplicates in each array
  my %count = ();
  foreach my $item (@$array1) {
    $count{$item}++;
  }
  foreach my $item (@$array2) {
    $count{$item}--;
  }
  foreach my $item (keys %count) {
    my $n = $count{$item};
    push @{ ($n == 0) ? \@common : (($n < 0) ? \@added : \@removed) }, $item;
  }

  return {
          'common' => \@common,
          'added' => \@added,
          'removed' => \@removed,
         };
}

#
# Compare the differences between two synopsis files with respect to
# selected attributes.
#
sub compareSynopsis {
  my ($oldSynopsisFile, $newSynopsisFile) = @_;

  # Scalar attributes to check for equality
  my @attributesToCheck = ( 'synthesisTool',
                            'hdlKind',
                            'clkWrapper',
                            'design',
                            'partFamily',
                            'part',
                            'package',
                            'speed',
                            'design_full_path',
                            'testbench',
                            'ce_clr',
                          );

  # Check if each synopsis file can be read
  return undef unless (-e $oldSynopsisFile && -f $oldSynopsisFile && -r $oldSynopsisFile);
  return undef unless (-e $newSynopsisFile && -f $newSynopsisFile && -r $newSynopsisFile);

  # Evaluate synopsis files and extract attributes
  my $oldSynopsis = &Sg::evaluate($oldSynopsisFile);
  my $newSynopsis = &Sg::evaluate($newSynopsisFile);
  my $oldAttributes = $oldSynopsis->{'attributes'};
  my $newAttributes = $newSynopsis->{'attributes'};

  my @addedAttributes = ();
  my @removedAttributes = ();
  my @modifiedAttributes = ();

  foreach my $attr (@attributesToCheck) {
    my $oldAttributeExists = exists($oldAttributes->{$attr});
    my $newAttributeExists = exists($newAttributes->{$attr});

    if ((!$oldAttributeExists) && ($newAttributeExists)) {
      push @addedAttributes, $attr;
    }
    elsif ($oldAttributeExists) {
      if (!$newAttributeExists) {
        push @removedAttributes, $attr;
      }
      else {
        my $oldValue = $oldAttributes->{$attr};
        my $newValue = $newAttributes->{$attr};
        if ("$oldValue" ne "$newValue") {
          push @modifiedAttributes, $attr;
        }
       }
    }
  }

  # Check the existence of selected files.
  my $oldFiles = $oldAttributes->{'files'};
  my $newFiles = $newAttributes->{'files'};
  my $fileFilter = '\.(v|vhd|vhdl|mif|coe|ucf)$';
  my @oldFilesToCheck = grep(/$fileFilter/i, @$oldFiles);
  my @newFilesToCheck = grep(/$fileFilter/i, @$newFiles);

  my $fileDiff = &compareArray(\@oldFilesToCheck, \@newFilesToCheck);
  my $addedFiles = $fileDiff->{'added'};
  my $removedFiles = $fileDiff->{'removed'};
  if (@$addedFiles > 0 || @$removedFiles > 0) {
    push @modifiedAttributes, 'files';
  }

  my $changed = int((@addedAttributes > 0)
                    || (@removedAttributes > 0)
                    || (@modifiedAttributes > 0));

  return {
          'changed' => $changed,
          'attributes' => {
                           'added' => \@addedAttributes,
                           'removed' => \@removedAttributes,
                           'modified' => \@modifiedAttributes,
                          },
          'files' => $fileDiff,
         };
}

1;
