package SgSynth;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(runSynthesisTool makeNgc);

use strict;
use Cwd;
use File::Path;
use Carp qw(croak);
use Sg;
use SgEnv;
use SgXst;
use SgSyn;
use SgIse;
use SgCache;

use vars qw($errorPatterns);

# Patterns for errors in logs from various tools.
$errorPatterns = {
  "Synplify" => [
    '^@E:',
    "Error: Aborting",
  ],
  "Synplify Pro" => [
    '^@E:',
    "Error: Aborting",
  ],
  "XST" => [
    "INTERNAL ERROR",
    "FATAL",
    "--> ERROR",
    "^ERROR",
  ],
  "leon" => [
    "Syntax Error",
    "Error in file",
    "line \\d+: Error",
    "^-- Error found in",
    "^FLEXlm error:",
    "^Licensing checkout error",
    "^MGLS Licensing Error",
  ],
  "xflow" => [
    "^ERROR",
    ": Completed - [1-9]\\d* errors found",
  ],
  "ngcbuild" => [
    "^ERROR",
  ],
};

#
# Synthesizes HDL files associated with a design.  When this member is called,
# the working directory must be the one in which the synthesis tool should
# run.  It is the responsibility of the caller to copy results to the
# appropriate de3stination.
#
# @param instrs XTable of synthesis tool instructions.
#

sub runSynthesisTool {
  my $instrs = $_[0];

  # Break out individual keys/values from $instrs hash table.
  my $synthesisTool = &Sg::getAttribute("synthesisTool", $instrs);
  my $design = &Sg::getAttribute("design", $instrs);
  my $files = &Sg::getAttribute("files", $instrs);
  my $exposeStdStreams = &Sg::getAttribute("exposeStdStreams", $instrs);

  # Results from synthesis are stored in this file.
  my $logFile = "$design.results";

  # Cache variables
  my @toHash;
  my @outputFiles;
  my $hash = '';
  my $cwd = cwd();

  # Run synthesis tool.
  my $status;
  if ($synthesisTool eq "XST") {
    my $results = &SgXst::makeXstFiles($instrs);
    my $prjFile = $results->[0];
    my $scrFile = $results->[1];
    my $xst = &SgEnv::getXstExePath();
    defined($xst) || croak "user: XST is not in your path";

    # Build list of files that will be generated.
    my $clkWrapper = &Sg::getAttribute("clkWrapper", $instrs);
    push @outputFiles, "$clkWrapper.ngc";
    # Generate unique hash based on source information
    push @toHash, "S:xstsize " . getFileSize($xst);
    push @toHash, "F:$prjFile", "F:$scrFile";
    # Add HDL files to hash list
    my $files = &Sg::getAttribute("files", $instrs);
    foreach (@$files) {
        next unless (/\.v(hd)?$/i || /\.edn$/i || /\.ncf$/i || /\.xcf$/i || /\.ngc$/i || /\.edf$/i);
        push @toHash, "F:$_" if (-e "$_");
    }
    $hash = getHash(\@toHash);

    # Attempt to get file from cache, and return if successful
    if (!copyOutFile($hash, $cwd, $instrs) && (-e "$clkWrapper.ngc")) {
#      print "Retrieved prebuilt $clkWrapper.ngc from cache\n";
      return;
    }

    my @cmd = ($xst, '-ifn', $scrFile, '-ofn', "$clkWrapper.syr");

    if ($exposeStdStreams) {
      $status = &Sg::runcmd(@cmd) == 0;
    }
    else {
      $status = &Sg::runcmd(@cmd, '>/dev/null') == 0;
    }
    if (-e "$clkWrapper.syr") {
      my $err = copyordie("$clkWrapper.syr", $logFile);
      push @outputFiles, "$clkWrapper.syr";
    }
  }
  elsif (($synthesisTool eq "Synplify") or ($synthesisTool eq "Synplify Pro")) {
    my $prjFile = "synplify_$design.prj";
    $instrs->{'prjFile'} = $prjFile;
    &SgSyn::makeSynplicityProjectFile($instrs);
    my $synplifyProg;
    if ($synthesisTool eq "Synplify") {
      $synplifyProg = &SgEnv::getSynplifyExePath();
    }
    else {
      $synplifyProg = &SgEnv::getSynplifyProExePath();
    }
    defined($synplifyProg) || croak "user: $synthesisTool is not in your path";

    # XXX Add hashables here (any more?)
    push @toHash, "S:synplifyprosize " . getFileSize($synplifyProg);
    push @toHash, "F:$prjFile";
    # Add HDL files to hash list
    my $files = &Sg::getAttribute("files", $instrs);
    foreach (@$files) {
        next unless (/\.v(hd)?$/i || /\.edn$/i || /\.edf$/i || /\.ngc$/i || /\.sdc$/i);
        push @toHash, "F:$_" if (-e "$_");
    }
    $hash = getHash(\@toHash);
    my $clkWrapper = &Sg::getAttribute("clkWrapper", $instrs);
    push @outputFiles, "$clkWrapper.edf";
    push @outputFiles, "$clkWrapper.ncf" if (-e "$clkWrapper.ncf");

    # Attempt to get file from cache, and return if successful
    if (!copyOutFile($hash, $cwd, $instrs) && (-e "$clkWrapper.edf")) {
#      print "Retrieved prebuilt $clkWrapper.edf from cache\n";
      return;
    }

    my @cmd = ($synplifyProg, '-batch', $prjFile);
    if ($exposeStdStreams) {
      $status = &Sg::runcmd(@cmd) == 0;
    }
    else {
      $status = &Sg::runcmd(@cmd, '> /dev/null');
    }
    if (-e "$clkWrapper.srr") {
      my $err = copyordie("$clkWrapper.srr", $logFile);
      push @outputFiles, "$clkWrapper.srr";
    }
  }
  else {
    croak "don't know how to run synthesis tool $synthesisTool";
  }

  if (-e $logFile) {
    &Sg::checkLog($synthesisTool, $errorPatterns->{$synthesisTool}, $logFile);
  }

  unless ($status) {
    my $err = "user: An error was encountered while running ${synthesisTool}.";
    # For Synplify, we need to check if stdout.log reports a license issue.
    if (($synthesisTool eq "Synplify") or ($synthesisTool eq "Synplify Pro")) {
      if (open(LOGFILE, "<stdout.log")) {
        my @lines = <LOGFILE>;
        my $log = "@lines";
        if ($log =~ /invalid license/) {
          $err = "user: A license error was encountered while running ${synthesisTool}.";
        }
        close(LOGFILE);
      }
    }
    croak $err;
  }

  # If this point is reached, it means file(s) need(s) to be cached.
  if (@outputFiles == 1) {
    addToCache($hash, "$cwd/$outputFiles[0]", $instrs);
    # Ignore errors.  The cache may just be full.
  }
  else {
    # Copy @output files to their own directory and cache directory.
    mkdir("$cwd/tocache");
    foreach (@outputFiles) {
      my $err = copyordie($_, "$cwd/tocache");
    }
    addToCache($hash, "$cwd/tocache", $instrs);
    # Ignore errors.  The cache may just be full.
    rmtree("$cwd/tocache");
  }
}


#
# Invokes ngcbuild to merge all files associated with a Sysgen design into a
# single .ngc file.
#
# @param instrs XTable of NgcBuild instructions.
#
# @return Error message, if any.
#

sub runNgcBuild {
  my $instrs = $_[0];

  # Break out individual keys/values from %$instrs hash table.
  my $design  = &Sg::getAttribute("design", $instrs);
  my $device = &Sg::getAttribute("device", $instrs);
  my $exposeStdStreams = &Sg::getAttribute("exposeStdStreams", $instrs);
  my $logFile = "ngcbuild.results";
  my $ngcName = $design . "_complete";

  # Run ngcbuild, then examine log for errors.
  my $ngcbuild = &SgEnv::getNgcBuildExePath();
  defined($ngcbuild) || croak "user: NGCBuild is not in your path";
  my @cmd = ($ngcbuild, '-p', $device, $design, "$ngcName.ngc");
  if ($exposeStdStreams) {
    &Sg::runcmd(@cmd);
  }
  else {
    &Sg::runcmd(@cmd, '> /dev/null');
  }

  my $err = copyordie("$ngcName.ngc", "$design.ngc");
  -e "$ngcName.blc" || croak "ngcbuild failed";
  $err = copyordie("$ngcName.blc", $logFile);
  &Sg::checkLog("ngcbuild", $errorPatterns->{'ngcbuild'}, $logFile);
}


#
# Compiles a design into an ngc file.  Designs may be a mixture of VHDL,
# Verilog, or EDIF.  The design top-level must be a VHDL component or Verilog
# module.
#
# @param instrs XTable of synthesis tool instructions.
#

sub makeNgc {
  my $instrs = $_[0];

  &runSynthesisTool($instrs);
  &runNgcBuild($instrs);
}

1;
