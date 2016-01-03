package SgNgcVerilog;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(saveXcoSequence saveTemplateInstrs wrapup);

use strict;
use warnings;

use File::Basename;
use File::Temp qw(tempdir);
use Cwd;
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;
use SgEnv;
use SgGenerateCores qw(augmentXcoInstrs generateCores);
use SgSynth;
use SgXtable;
use Template;

use vars qw($xcoSequences $fileDeliveryInfo);

#
# Generates the cores needed by one block.
#
# @param @$xcoSeqs xco instructions (if any) for this block.  The elements of
#        this array are themselves arrays, each containing the xco instructions
#        for one core.
# @param $coregenTmpDir directory in which coregen should work.
# @param $ngcTmpDir directory to which cores should be delivered.
#

sub genCores() {
  my($xcoSeqs, $coregenTmpDir, $ngcTmpDir) = @_;

  # Return without doing anything if there are no xco instructions.
  return unless ($xcoSeqs && scalar(@$xcoSeqs));

  # Open a file to hold the xco instructions.
  my $xcoInstrs = "$coregenTmpDir/sysgen.sgxco";
  my $xcoInstrsFile = new FileHandle;
  open($xcoInstrsFile, "> $xcoInstrs") || croak "couldn't open $xcoInstrs";
  binmode $xcoInstrsFile;

  # Augment each instruction sequence with boilerplace at the front and a
  # GENERATE at the back, then write the augmented instructions to the sgxco
  # file.
  my $seq;
  foreach $seq (@$xcoSeqs) {
    my $augSeq = &SgGenerateCores::augmentXcoInstrs($seq);
    print $xcoInstrsFile "\n";
    my $line;
    foreach $line (@$augSeq) { print $xcoInstrsFile "$line\n"; }
  }
  close($xcoInstrsFile) || croak "trouble writing $xcoInstrs";

  # cd into the directory to which cores should be delivered.
  chdir $coregenTmpDir || croak "couldn't cd into $coregenTmpDir";

  # Generate cores.
  &SgGenerateCores::generateCores($xcoInstrs);

  # Copy relevant files to directory in which ngc file will be generated.
  opendir(DIR, ".") || croak "couldn't read working directory";
  my $tmpDir = &Sg::getTmpDir();
  my @files;
  my $file;
  foreach $file (readdir(DIR)) {
    if ($file =~ /\.vhd$/i || $file =~ /\.v$/i ||
        $file =~ /\.edn$/i || $file =~ /\.ngc$/i ||
        $file =~ /\.mif$/i) {
      my $err = copyordie($file, $ngcTmpDir);
    }
  }
  closedir(DIR) || croak "trouble reading working directory";
  return \@files;
}


#
# Runs the template processor on a given source file using a given hash
# table of keys/values, and writes the results to a given file handle.
#
# @param $sf name of the source file.
# @param %$templateKeyValues Keys/values that should be used by the template
#        processor.
# @param $resultsFile Handle for the file to which results should be written.
#

sub processTemplate() {
  my($sf, $templateKeyValues, $resultsFile) = @_;

  # Read source file into local array.
  open(SOURCE, "< $sf") || croak "couldn't open $sf";
  my @text = <SOURCE>;
  close(SOURCE) || croak "trouble reading $sf";
  chomp(@text);

  # Process source file text with Template::Toolkit text processor, using
  # template key/values as Template::Toolkit symbol table.
  my @newText;
  my $textString = join("\n", @text);
  my $tt = Template->new({OUTPUT => \@newText});
  $tt->process(\$textString, $templateKeyValues) || croak $tt->error;

  # Write text processor results to the appropriate file handle.
  my $line;
  foreach $line (@newText) {
    print $resultsFile "$line\n";
  }

  $resultsFile->flush;
}


#
# Processes the templates needed by one block.
#
# @param @$templateInfo sequence of instruction blocks.  Each block is a hash
#        table telling how to process one file.  The key/value pairs are
#        the following:
#          sourceFile - tells source file to operate upon.
#          templateKeyValues - hash table to be used by template processor.
# @param ngcTmpDir directory to which template processor results should be
#        delivered.
#

sub processTemplates() {
  my($templateInfo, $ngcTmpDir) = @_;

  # Return without doing anything if there are no templates to process.
  return unless ($templateInfo && scalar(@$templateInfo));

  # Open a file handle to receive the results.
  my $results = "$ngcTmpDir/template_results.vhd";
  my $resultsFile = new FileHandle;
  open($resultsFile, "> $results") || croak "couldn't open $results";
  binmode($resultsFile);

  # For each instruction block, run the template processor, and copy the
  # results to the file handle.
  my $instrBlock;
  foreach $instrBlock (@$templateInfo) {
    my $templateKeyValues = $instrBlock->{"templateKeyValues"};
    if ($templateKeyValues && scalar(keys %$templateKeyValues)) {
      &processTemplate($instrBlock->{"sourceFile"}, $templateKeyValues,
        $resultsFile);
    }
    else {
      my $err = copyordie($instrBlock->{"sourceFile"}, $resultsFile);
    }
  }
  close($resultsFile) || croak "trouble writing $results";
}


#
# Generates an ngc file.  This member assumes the following:
#   - When called, the directory $ngcTmpDir contains all files needed to produce
#     the ngc file.
#   - The top-level file is named template_results.vhd, and the last entity
#     defined by this file should be the top-level name assigned to the ngc.
#
# @param $ngcTmpDir directory in which ngc file should be generated.
# @param $verTmpDir directory in which we plan to generate Verilog.
#
# @return Name of the ngc file that was generated.
#

sub generateNgc() {
  my($ngcTmpDir, $verTmpDir) = @_;

  # Capture the entity lines from the file "template_results.vhd".
  open(TOPFILE, "< $ngcTmpDir/template_results.vhd") ||
    croak "couldn't open template_results.vhd";
  my @entityLines = grep(/^\s*entity\s+.*\s+is\s*$/i, <TOPFILE>);
  close(TOPFILE) || croak "trouble reading template_results.vhd";

  # Extract name of the top-level entity.
  my $lastLine = pop(@entityLines);
  defined $lastLine || croak "template_results.vhd is badly formed";
  $lastLine =~ s/^\s*entity\s+//i;
  $lastLine =~ s/\s+is\s*$//i;
  my $topLevel = $lastLine;

  # Construct instructions for running XST.
  my $files = [];
  opendir(DIR, $ngcTmpDir) || croak "couldn't open directory $ngcTmpDir";
  my $file;
  foreach $file (readdir(DIR)) {
    next unless ($file =~ /\.vhd$/i);
    push(@$files, $file) unless ($file eq "template_results.vhd");
  }
  closedir(DIR) || croak "trouble reading directory $ngcTmpDir";
  push(@$files, "template_results.vhd");
  my $instrs = {
    "synthesisTool" => "XST",
    "design" => $topLevel,
    "files" => $files,
    "clkWrapper" => $topLevel,
    "freq" => 1000.0 / &Sg::getAttribute("systemClockPeriod"),
    "hdlKind" => "vhdl",
    "omitIobs" => 1,
    "xcfFile" => undef,
    "ncfFile" => undef,
    "exposeStdStreams" => undef,
    "omitCores" => 1,
  };

  # Run xst and ngcbuild to make ngc file.
  chdir $ngcTmpDir || croak "couldn't cd into $ngcTmpDir";
  &SgSynth::makeNgc($instrs);

  # Copy ngc file to the directory in which we plan to generate Verilog, and to
  # the final directory.
  my $ngc = "$topLevel.ngc";
  my $err = copyordie($ngc, $verTmpDir);
  my $tmpDir = &Sg::getTmpDir();
  $err = copyordie($ngc, $tmpDir);

  # Copy ngo files (if any) to directory in which we plan to generate Verilog.
  opendir(DIR, ".") || croak "couldn't open directory $ngcTmpDir";
  foreach $file (readdir(DIR)) {
    next unless ($file =~ /\.ngo$/i);
    $err = copyordie($file, $verTmpDir);
  }
  closedir(DIR) || croak "trouble reading directory $ngcTmpDir";

  return $ngc;
}


#
# Generates Verilog from an ngc file.
#
# @param $ngc Tells the name of the ngc file.
# @param $verTmpDir directory in which Verilog file should be generated.
#
# @return Name of the Verilog file that was generated.
#

sub generateVerilog() {
  my($ngc, $verTmpDir) = @_;

  # Locate netgen and ngdbuild.
  my $ngdbuild = &SgEnv::getNgdBuildExePath();
  $ngdbuild || croak "couldn't find ngdbuild in your path";
  my $netgen = &SgEnv::getNetGenExePath();
  $netgen || croak "couldn't find netgen in your path";

  # Run ngdbuild to produce ngd file.
  chdir $verTmpDir || croak "couldn't cd into $verTmpDir";

  my $top = $ngc;
  $top =~ s/\.ngc$//i;
  my $ngd = "$top.ngd";
  my $status = &Sg::runcmd($ngdbuild, $top, $ngd, '2>&1', '>ngdbuild.log');
  $status && croak "trouble running ngdbuild";

  # Run netgen to produce Verilog file.
  $status = &Sg::runcmd($netgen, '-ofmt', 'verilog', '-sim', $ngd, '2>&1', '>netgen.log');
  $status && croak "trouble running netgen";

  # Augment Verilog file with synopsys translate_off/on
  my @text;
  my $ver = $ngc;
  $ver =~ s/\.ngc$/.v/i;
  open(VER, "< $ver") || croak "couldn't open $ver";
  my $line;
  my $state = "header";
  foreach $line (<VER>) {
    chomp($line);
    if ($state eq "header") {
      if ($line =~ /^\s*wire\b/) {
        push(@text, "// synopsys translate_off");
        $state = "body";
      }
      push(@text, $line);
    }
    elsif ($state eq "body") {
      if ($line =~ /^\s*endmodule\b/) {
        push(@text, "// synopsys translate_on");
        $state = "beforeGlobalModule";
      }
      push(@text, $line);
    }
    elsif ($state eq "beforeGlobalModule") {
      if ($line =~ /^\s*module\s+glbl\b/) {
        $state = "insideGlobalModule";
      }
      elsif (($line =~ /^\s*`timescale\b/)) {
        $state = "beforeGlobalModule";
      }
      else {
       push(@text, $line);
      }
    }
    elsif ($state eq "insideGlobalModule") {
      if ($line =~ /^\s*endmodule\b/) {
       $state = "pastGlobalModule";
      }
    }
    else {
      push(@text, $line);
    }
  }
  close(VER) || croak "trouble reading $ver";
  open(VER, "> $ver") || croak "couldn't open $ver";
  binmode(VER);
  foreach $line (@text) { print VER "$line\n"; }
  close(VER) || croak "trouble writing $ver";

  # Copy Verilog file to final destination.
  my $tmpDir = &Sg::getTmpDir();
  my $err = copyordie($ver, $tmpDir);

  return $ver;
}


#
# Generates the ngc and Verilog files for one block.
#
# @param $blockName Full Simulink name of the block for which we are generating
#        files.
# @param @$xcoSeq xco instructions (if any) for this block.  The elements of
#        this array are themselves arrays, each containing the xco instructions
#        for one core.
# @param @$templateInfo array of template processing instructions.  Each
#        element is a hash table containing the following key/value pairs
#          sourceFile - tells source file to operate upon.
#          templateKeyValues - hash table to be used by template processor.
#
# @return Names of generated cores, ngc, and Verlog files, expressed relative
#         to destination directory.
#

sub generateNgcVerilogFiles() {
  my($blockName, $xcoSeq, $templateInfo) = @_;

  # Make temporary directories in which to work.
  my $tmpDir = &SgEnv::canonpath(tempdir("ngc_verilog_XXXX",
          DIR => &Sg::getTmpDir(), CLEANUP => 0));
  my $coregenTmpDir = &SgEnv::canonpath(tempdir("coregen_XXXX",
          DIR => $tmpDir, CLEANUP => 0));
  my $ngcTmpDir = &SgEnv::canonpath(tempdir("ngc_XXXX",
          DIR => $tmpDir, CLEANUP => 0));
  my $verTmpDir = &SgEnv::canonpath(tempdir("ver_XXXX",
          DIR => $tmpDir, CLEANUP => 0));

  # Generate cores.
  my $files = &genCores($xcoSeq, $coregenTmpDir, $ngcTmpDir);

  # Process templates.
  &processTemplates($templateInfo, $ngcTmpDir);

  # Generate ngc file.
  my $ngc = &generateNgc($ngcTmpDir, $verTmpDir);

  # Generate Verilog file.
  my $ver = &generateVerilog($ngc, $verTmpDir);

  push(@$files, $ngc, $ver);
  return $files;
}


#
# Wraps up everything that is pending.  In detail, this member calls coregen
# and/or runs the template processor for each block that needs it, then
# produces a corresponding ngc file and Verilog file.
#
# @return Array telling names of generated files, expressed relative to
#         the target directory.
#

sub wrapup {
  my $h = {};
  my $blockName;
  foreach $blockName (keys %$xcoSequences) { $h->{$blockName} = 1; }
  foreach $blockName (keys %$fileDeliveryInfo) { $h->{$blockName} = 1; }

  my $files = [];
  foreach $blockName (keys %$h) {
    my $x = $xcoSequences->{$blockName};
    my $f = $fileDeliveryInfo->{$blockName};
    my $results = &generateNgcVerilogFiles($blockName, $x, $f);
    push(@$files, @$results);
  }

  return $files;
}


#
# Saves template processing instructions so they can be executed later.
#
# @param %$instrs Instructions to be saved.  Instructions contain the following
#        key/value fields:
#          blockName -
#            Name of the Sysgen block.
#          sourceFile -
#            Tells the file to operate upon.
#          templateKeyValues -
#            Hash table to be used by the template processor.
#
# @return undef.
#

sub saveTemplateInstrs {
  my $instrs = $_[0];
  $instrs = &SgXtable::copy($instrs);
  $instrs->{"sourceFile"} =
    &Sg::makeSourceFileCanonical($instrs->{"sourceFile"});

  # Save template instructions in hash table whose keys are block names
  # and values are sequences of template instructions.
  $fileDeliveryInfo = {} unless ($fileDeliveryInfo);
  my $blockName = $instrs->{"blockName"};
  my $b = $fileDeliveryInfo->{$blockName};
  if (! $b) {
    $b = [];
    $fileDeliveryInfo->{$blockName} = $b;
  }
  push(@$b, $instrs);

  return undef;
}


#
# Saves xco instructions so they can be passed to coregen later.
#
# @param %$instrs Instructions to be saved.  This a hash table containing two
# key/value pairs:
#
# blockName -
#   Tells the name of the Sysgen block to which these instructions
#   correspond.
# xcoSequence -
#   Tells the instructions to be passed to coregen.  These are
#   ordinary xco instructions except for three things:
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
# - The ordinary boilerplate at the front and back must not have already been
#   added.
#
# @return undef.
#

sub saveXcoSequence {
  my $instrs = $_[0];
  $instrs = &SgXtable::copy($instrs);

  # Save xco sequence in hash table whose keys are block names and values are
  # sequences of xco sequences.
  $xcoSequences = {} unless ($xcoSequences);
  my $blockName = $instrs->{"blockName"};
  my $b = $xcoSequences->{$blockName};
  if (! $b) {
    $b = [];
    $xcoSequences->{$blockName} = $b;
  }
  push(@$b, $instrs->{"xcoSequence"});

  return undef;
}


1;
