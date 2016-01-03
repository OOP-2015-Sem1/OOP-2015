#!/usr/bin/perl

# use strict 'vars';

#
#  xlpbsmble.pl --
#
#  Runs the kcpsm2 assembler on a collection of files, then generates
#  Matlab vectors for loading program stores in System Generator xlpb blocks.
#

&main();
exit(0);


#
#  usage --
#
#  Tells how to use this tool, then exits
#

sub usage {
  my($b) = &basename($0);
  print STDERR <<usageText;
usage: $b [options]

$b assembles files containing PicoBlaze code for either PicoBlaze 2 or 
PicoBlaze 3 based upon the value of <version>.  The result is a collection 
of .m files to be used for populating xlpb program store ROMs.  Each 
file is named "fill_<file>_program_store.m", where <file> is the name of
the corresponding code file.

examples: $b -v version -p file1.psm file2.psm
          $b -v version -p file1 file2
          $b -v version -p *.psm
          $b -d

Arguments:
  -h           Print this message.
  -d           Use defaults for all arguments not specified explicitly.  
               Default version of assembler is KCPSM-3.
  -v <version> Either 2 (for PicoBlaze 2) or 3 (for PicoBlaze 3).
  -p <files>   Tells the .psm files to assemble.  The .psm suffix can be omitted
               from file names.  When there is no -p option, every .psm file in
               the working directory is assembled.  For each file, intermediate
               results are left in a directory named <file>_results, where 
	       <file> is the name of the file.
  -n <mfiles>  Specifies a list of Matlab files names into which the assembled
  	       instruction values are written.  When using this option, the 
	       mfile list must be the same length as the number of .psm files 
	       being assembled.
usageText
  exit 2;
}


#
# Digests the text from a .coe file so it can be used as the program store
# for the Sysgen xlpb block, then returns the results.
#
# @param @$coe Text to be digested.
#
# @return Array containing the digested text.
#

sub digestCoeText {
  my($coe) = @_;

  # Discard every line in the .coe file except for the hex memory initial
  # values at the end.
  my(@newCoe);
  foreach $line (reverse @$coe) {
    last if ($line =~ /^memory_initialization_vector\s*=/);
    $line =~ s/,//g;
    $line =~ s/;//;
    $line =~ tr/A-Z/a-z/;
    my(@a);
    foreach $h (split(/\s+/, $line)) { push(@a, hex($h)); }
    unshift(@newCoe, join(", ", @a) . ", ");
  }

  return \@newCoe;
}


#
# Assembles a given .psm file.
#
# @param $file .psm file to assemble.
#

sub assemble {
  my($version, $file, $mfile) = @_;

  # Make a directory in which file should be assembled.
  my($dir) = &dirname($file);
  my($b) = &basename($file);
  $b =~ tr/A-Z/a-z/;
  $b =~ s/\W/_/g;
  $b =~ s/\.psm$//;
  $b .= "_results";
  $dir .= "/$b";
  chmod 0755, $dir;
  &rmtree($dir);
  ! -e $dir || &internal("couldn't discard old copy of $dir.  " .
                 "Perhaps NT thinks it's in use?");
  &makeDir($dir);
  -e $dir || &internal("couldn't make directory $dir.");

  # Copy file into directory made above.
  $b = &basename($file);
  $b =~ tr/A-Z/a-z/;
  $b =~ s/\.psm$//;
  $b =~ s/\W/_/g;
  $b = "x$b" unless ($b =~ /^[a-z]/);
  $b = substr($b, 0, 8) unless (length($b) <= 8);
  $b .= ".psm";
  &copy($file, "$dir/$b");
  -e "$dir/$b" || &internal("trouble copying $file into $dir");

  # Copy ROM_form.coe and ROM_form.vhd into directory made above.
  my $sysgenDir = $FindBin::Bin . "/..";
  my($f) = "${sysgenDir}/data/ROM_form.coe";
  -e $f || &internal("couldn't find $f");
  &copy($f, $dir);
  -e "$dir/" . &basename($f) || &internal("couldn't copy $f to $dir");
  chmod 0644, "$dir/" . &basename($f);
  $f = "${sysgenDir}/hdl/ROM_form.vhd";
  -e $f || &internal("couldn't find $f");
  &copy($f, $dir);
  -e "$dir/" . &basename($f) || &internal("couldn't copy $f to $dir");
  chmod 0644, "$dir/" . &basename($f);
  # Copy Rom_form.v into directory made above
  $f = "${sysgenDir}/hdl/ROM_form.v";
  -e $f || &internal("couldn't find $f");
  &copy($f, $dir);
  -e "$dir/" . &basename($f) || &internal("couldn't copy $f to $dir");
  chmod 0644, "$dir/" . &basename($f);

  if($version eq "2") {
    # Make a wrapper .bat file for calling kcpsm2.exe.  This is necessary
    # so we can redirect the assembler's output to a file.
    chdir $dir;
    my($kcpsm2) = "${sysgenDir}/bin/" . SgEnv::getISEPlatformTag() . "/kcpsm2.exe";
    -e $kcpsm2 || &internal("couldn't find $kcpsm2");
    $kcpsm2 =~ s/\//\\/g;
    my($wrapper) = "wrapkcpsm2.bat";
    open(WRAPPER, "> $wrapper") || &internal("couldn't write $wrapper");
    print WRAPPER "$kcpsm2 $b\n";
    close(WRAPPER) || &internal("trouble writing $wrapper");

    # Run ksmble.exe on the file.
    my($cmd) = $wrapper;
    my($results) = "assembler.results";
    system("$cmd > $results 2>&1") && &internal("couldn't run $cmd");

    # Check results file for errors.
    -e $results || &internal("kcpsm2 assembler failed");
    open(RESULTS, "< $results") || &internal("couldn't read $results");
    my(@resultsText) = <RESULTS>;
    close(RESULTS) || &internal("trouble reading $results");
    if (scalar(@resultsText)) {
      chomp(@resultsText);
      my($succeeded) = scalar(grep(/^KCPSM2 successful/, @resultsText));
      $succeeded || &internal("assembly failed for " .
        &basename($file) . " - see $results");
    }
    else {
      &internal("kcpsm2 assembler failed");
    }
  }
  else { # KCPSM3

    # Make a wrapper .bat file for calling kcpsm3.exe.  This is necessary
    # so we can redirect the assembler's output to a file.
    chdir $dir;
    my($kcpsm3) = "${sysgenDir}/bin/" . SgEnv::getISEPlatformTag() . "/kcpsm3.exe";
    -e $kcpsm3 || &internal("couldn't find $kcpsm3");
    $kcpsm3 =~ s/\//\\/g;
    my($wrapper) = "wrapkcpsm3.bat";
    open(WRAPPER, "> $wrapper") || &internal("couldn't write $wrapper");
    print WRAPPER "$kcpsm3 $b\n";
    close(WRAPPER) || &internal("trouble writing $wrapper");

    # Run ksmble.exe on the file.
    my($cmd) = $wrapper;
    my($results) = "assembler.results";
    system("$cmd > $results 2>&1") && &internal("couldn't run $cmd");

    # Check results file for errors.
    -e $results || &internal("kcpsm3 assembler failed");
    open(RESULTS, "< $results") || &internal("couldn't read $results");
    my(@resultsText) = <RESULTS>;
    close(RESULTS) || &internal("trouble reading $results");
    if (scalar(@resultsText)) {
      chomp(@resultsText);
      my($succeeded) = scalar(grep(/^KCPSM3 successful/, @resultsText));
      $succeeded || &internal("assembly failed for " .
        &basename($file) . " - see $results");
    }
    else {
      &internal("kcpsm3 assembler failed");
    }

  }

  # Read .coe file into local array.
  my($coeFile) = $b;
  $coeFile =~ s/\.psm$/.coe/;
  $coeFile =~ tr/a-z/A-Z/;
  $coeFile = "$dir/$coeFile";
  -e $coeFile || &internal("expected to find $coeFile");
  open(COEFILE, "< $coeFile") || &internal("couldn't read $coeFile");
  my(@coe) = <COEFILE>;
  close(COEFILE) || &internal("trouble reading $coeFile");
  chomp(@coe);

  # Transform the .coe file into a form that can be used by the Sysgen xlpb
  # block.
  my($sgcoe) = &digestCoeText(\@coe);

  # Write the digested file.
  my($bn) = basename($file);
  $bn =~ tr/A-Z/a-z/;
  $bn =~ s/\.psm$//;
  $bn =~ s/\W/_/g;
  $bn = "x$bn" unless ($bn =~ /^[a-z]/);

  my($digestedFile) = dirname($coeFile) . "/../$mfile";
  unlink $digestedFile;
  ! -e $digestedFile || &internal("couldn't remove old $digestedFile");
  open(SGCOE, "> $digestedFile") || &internal("couldn't open $digestedFile");
  binmode(SGCOE);
  my($funname) = $mfile;
  $funname =~ s/\.m$//;
  print SGCOE "function bits = $funname" . "()\n";
  print SGCOE "  bits = [ ...\n";
  foreach $line (@$sgcoe) { print SGCOE "    $line...\n"; }
  print SGCOE "  ];\n\n";
  print SGCOE "  return;\n";
  close(SGCOE) || &internal("trouble writing $digestedFile");
}


#
# Processes command line options, then returns the results.
#
# @return Full path name of the .coe file to digest.

sub processArgs {
  &usage() unless (scalar(@ARGV));

  my(@psmFiles);
  my(@mFileNames);
  my($version);
  my($name);

  $version = "3";
  while (scalar(@ARGV)) {
    my($arg) = shift(@ARGV);
       if ($arg eq "-h")     { &usage();                         }
    elsif ($arg eq "-d")     { ;                                 }
    elsif ($arg eq "-v")     { $version = shift(@ARGV);          }
    elsif ($arg eq "-p")     { while (scalar(@ARGV) && $ARGV[0] !~ /^-/) {
                                 push(@psmFiles, shift(@ARGV));
                               }
                             }
    elsif ($arg eq "-n")     { while (scalar(@ARGV) && $ARGV[0] !~ /^-/) {
                                 push(@mFileNames, shift(@ARGV));
                               }
			     }
    else                     { &usage();                         }
  }

  # If command line contains no -p option, assume we should assemble every
  # .psm file in the working directory.
  if (! scalar(@psmFiles)) {
    opendir(WORKINGDIR, ".") || &internal("couldn't read working directory");
    foreach $file (readdir(WORKINGDIR)) {
      push(@psmFiles, $file) if ($file =~ /\.psm$/i);
    }
    closedir(WORKINGDIR) || &internal("trouble reading working directory");
    scalar(@psmFiles) > 0 || &usage();
  }

  # Expand wild cards in .psm file names.
  my(@psmFiles1);
  foreach $file (@psmFiles) {
    push(@psmFiles1, File::DosGlob::doglob(1, $file));
  }
  if(scalar(@psmFiles1)) {
    @psmFiles = @psmFiles1;
  }

  # Make sure file names make sense.
  foreach $file (@psmFiles) {
    -e $file || -e "$file.psm" || &internal("there is no $file or $file.psm");
    $file .= ".psm" unless (-e $file);
    -f $file || &internal("$file is not a plain file");
    chmod 0644, $file;
    -r $file || &internal("couldn't make $file readable");
    $file = &makePathnameAbsolute($file);
  }

  # Check that Mfile name list contains enough names for the psmFiles
  if(scalar(@mFileNames) > 0) {
     if(scalar(@mFileNames) ne scalar(@psmFiles)) {
     	&internal("Must specify the same number of .m file names as there are .psm files.");
     }
  }


  return ($version,\@psmFiles,\@mFileNames);
}


#
#  main --
#
#  Top level function.
#

sub main {
  # Process command line arguments to find .psm files to be assembled.
  my($version, $psmFiles, $mFileNames) = &processArgs();

  my($count);
  my($matfile);
  $count = 0;
  # Assemble each .psm file.
  foreach $file (@$psmFiles) {
    print STDERR "$file";
    if(scalar(@$mFileNames) ne 0) {
         my($tmp) = @$mFileNames->[$count];
         $matfile = $tmp =~ /\.m$/? $tmp : $tmp . ".m";
	 $count = $count + 1;
    }
    else {
       my($bn) = basename($file);
       $bn =~ tr/A-Z/a-z/;
       $bn =~ s/\.psm$//;
       $bn =~ s/\W/_/g;
       $bn = "x$bn" unless ($bn =~ /^[a-z]/);
       $matfile = "fill_$bn" . "_program_store.m";
       unlink $matfile;
    }
    # COmment out for now so I can use print statements to debug
    &assemble($version, $file, $matfile);
  }

  print STDERR "done\n";
}


BEGIN {
  # The next two lines make Perl look for libraries in the directory containing
  # this file.
  use FindBin;
  use lib "$FindBin::Bin";
  use File::DosGlob;
  use File::Basename;
  use File::Copy;
  use File::Path;
  use SgUtil;
  use SgEnv;
}
