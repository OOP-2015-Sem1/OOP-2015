package SgSwitchLibrary;

use strict;
use File::Temp qw(tempdir);
use File::Basename;
use Cwd;
use Carp qw(croak);
use vars qw($defaultFileList $errorPatterns);
use Sg;
use SgEnv;
use SgUtil;

$Carp::CarpLevel = 1;
$| = 1;

my $errorPatterns = {
  "xtclsh" => [
    "^ERROR",
  ],
};

my $defaultFileList = [
  '${{design}}.vhd',
  '${{design}}_cw.vhd',
  '${{design}}_mcw.vhd',
  '${{design}}_dcm_mcw.vhd',
  'isim_${{design}}.prj',
  'xst_${{design}}.prj',
  'synplify_${{design}}.prj',
  'vcom.do',
  'vsim.do',
  'pn_behavioral.do',
  'pn_posttranslate.do',
  'pn_postmap.do',
  'pn_postpar.do',
  '${{clkWrapper}}.xise',
];

sub isModuleDefined {
  my ($design, $module) = @_;

  if (lc($design) eq lc($module)) {
    return 1;
  }

  my $found = 0;
  my $fuse = &SgEnv::getISimFuseExePath();
  if (defined($fuse)) {
    # Run ISim Fuse to dump the modules of the design
    my $isimprj = "isim_${design}.prj";
    if (open(F, $isimprj)) {
      my $has_verilog = (scalar(grep(/^verilog/, <F>)) > 0);
      close(F);
      my @cmd = ($fuse, '-prj', $isimprj, '-dumpinterface');
      if ($has_verilog) {
        push(@cmd, 'glbl', '-L', 'unisims_ver', '-L', 'simprims_ver',
          '-L', 'xilinxcorelib_ver', '-L', 'unimarcos_ver');
      }
      push(@cmd, '2>&1', '>/dev/null');
      my $status = &Sg::runcmd(@cmd);
      if ($status == 0) {
        if (open(F, "isim/isimdump.txt")) {
          while (<F>) {
            if (/===/ .. /---/) {
              if (/^([^\t]+)\t/) {
                if (lc($1) eq lc($module)) {
                  $found = 1;
                  last;
                }
              }
            }
          }
          close(F);
        }
      }
    }
  }
  return $found;
}

sub processModelSimDo {
  my ($line, $fromLib, $toLib) = @_;
  if ($line =~ /^(\s*)vlib(\s+)(${fromLib})(\s.*)?$/) {
    $line = "$1vlib$2${toLib}$4";
  } elsif ($line =~ /^(\s*)vcom\s/) {
    if ($line =~ /^(\s*)vcom(.*\s)-work(\s+)(${fromLib})(\s.*)$/) {
      if ($toLib eq 'work') {
        $line = "$1vcom$2$5";
      }
      else {
        $line = "$1vcom$2-work$3${toLib}$5";
      }
    } elsif ($line !~ /^(\s*)vcom(.*\s)-work(\s+)/) {
      $line =~ s/^(\s*)vcom\s(.*)/$1vcom -work ${toLib} $2/;
    }
  } elsif ($line =~ /^(\s*)vsim\s/) {
    if ($line =~ /^(\s*)vsim(.*\s)-L(\s+)(${fromLib})(\s.*)$/) {
      $line = "$1vsim$2-L$3${toLib}$5";
    } elsif ($line !~ /^(\s*)vsim(.*\s)-L(\s+)/) {
      $line =~ s/^(\s*)vsim\s(.+)/$1vsim -L ${toLib} $2/;
    }

    if ($line =~ /^(\s*)vsim(.*\s)-lib(\s+)(${fromLib})(\s.*)$/) {
      if (${toLib} ne 'work') {
        $line = "$1vsim$2-lib$3${toLib}$5";
      } else {
        $line = "$1vsim$2$5";
      }
    } elsif ($line !~ /^(\s*)vsim(.*\s)-lib(\s+)/) {
      if (${toLib} ne 'work') {
        $line =~ s/^(\s*)vsim\s(.+)/$1vsim -lib ${toLib} $2/;
      }
    }
  }
  return $line;
}

sub processIsimProject {
  my ($line, $fromLib, $toLib) = @_;
  if ($line !~ /\$XILINX\/verilog\/src\/glbl\.v$/) {
      $line =~ s/^(\s*)(vhdl|verilog)(\s+)(${fromLib})(\s.*)$/$1$2$3${toLib}$5/;
  }
  return $line;
}

sub processXstProject {
  my ($line, $fromLib, $toLib) = @_;
  $line =~ s/^(\s*)(vhdl|verilog)(\s+)(${fromLib})(\s.*)$/$1$2$3${toLib}$5/;
  return $line;
}

sub processSynplifyProject {
  my ($line, $fromLib, $toLib) = @_;
  $line =~ s/^(\s*)add_file(\s.*\s)-lib(\s+)(${fromLib})(\s.*)$/$1add_file$2-lib$3${toLib}$5/;
  return $line;
}

sub processVhdl {
  my ($line, $fromLib, $toLib) = @_;
  $line =~ s/^(\s*)library(\s+)${fromLib}(\s*);//;
  $line =~ s/^(\s*)use(\s+)(${fromLib})\./$1use$2${toLib}./;
  $line =~ s/:(\s*)entity(\s+)(${fromLib})\./:$1entity$2${toLib}./;
  return $line;
}

sub processFile {
  my ($processor, $inFile, $fromLib, $outFile, $toLib) = @_;
  open(INFILE, "<$inFile") || croak "Failed to open '$inFile'";
  open(OUTFILE, ">$outFile") || croak "Failed to open '$outFile'";
  if (($inFile =~ /\.vhdl?$/) && ($toLib ne 'work')) {
    my $foundToLib = 0;
    while (my $line = <INFILE>) {
      $line = $processor->($line, $fromLib, $toLib);
      if ($line =~ /^\s*entity/) {
        $foundToLib = 0;
      }
      elsif ($line =~ /^\s*library\s+(${toLib})\s*;/) {
        $foundToLib = 1;
      }
      elsif (($foundToLib == 0) && ($line =~ /^(\s*)use(\s+)(${toLib})\./)) {
        $line = "library ${toLib};\n$line";
        $foundToLib = 1;
      }
      print OUTFILE "$line";
    }
  }
  else {
    while (my $line = <INFILE>) {
      chomp($line);
      $line = $processor->($line, $fromLib, $toLib);
      print OUTFILE "$line\n";
    }
  }
  close(INFILE) || croak "Failed to close '$inFile'";
  close(OUTFILE) || croak "Failed to close '$outFile'";
}

sub convertIseProject {
  my ($project, $fromLib, $toLib, $logFile) = @_;
  my $xtclsh = &SgEnv::getXTclshExePath();
  defined($xtclsh) || croak "xtclsh is not in the path\n";
  my $scripts_dir = &SgEnv::getSysgenScriptsPath();
  defined($scripts_dir) || croak "Could not find Sysgen scripts directory\n";
  my $script = "${scripts_dir}/SgIseSwitchLibrary.tcl";
  unlink($logFile);
  my $exitcode = &Sg::runcmd($xtclsh, $script, $project, $fromLib, $toLib, '2>&1', ">$logFile");
  ($exitcode == 0) || croak "An error occurred when switching library in ISE project '$project'.\n";
  &Sg::checkLog('xtclsh', $errorPatterns->{'xtclsh'}, $logFile);
}

sub expandVariable {
  my ($var) = @_;
  while ($var =~ /^(.*)\${{([^}]+)}}(.*)$/) {
    $var = $1 . &Sg::getAttribute($2) . $3;
  }
  return $var;
}

sub elaborateFileList {
  my @result = ();
  foreach my $file (@$defaultFileList) {
    my $f = &expandVariable($file);
    next unless (-f $f);
    push @result, $f;
  }
  return @result;
}

sub processDirectory {
  my ($targetDir, $fromLib, $toLib) = @_;

  $targetDir = &SgEnv::rel2abs($targetDir);

  my $cwd = getcwd();
  (-d $targetDir) || croak "Directory '$targetDir' not found.";
  chdir($targetDir) || croak "Cannot change to directory '$targetDir'.";

  $fromLib = &expandVariable($fromLib);
  $toLib = &expandVariable($toLib);
  my @fileList = &elaborateFileList();
  my $design = &Sg::getAttribute('design');
  my $clkWrapper = &Sg::getAttribute('clkWrapper');

  if (isModuleDefined($design, $toLib)) {
    croak "Could not switch to HDL library '$toLib' because a module named '$toLib' is found in the design.";
  }

  my $tmpDir = &SgEnv::canonpath(tempdir("switch_lib_backup.XXXX",
                                         DIR => $targetDir,
                                         TMPDIR => 1));
  print "INFO: Switching HDL library references in design '$clkWrapper' ...\n";
  print "INFO: A backup of the original files can be found at '$tmpDir'.\n";

  foreach my $file (@fileList) {
    next unless (-f $file);
    &Sg::copyordie($file, "$tmpDir/$file");
    print "INFO: Processing file '$file' ...\n";
    my $processor;
    if ($file =~ /\.vhdl?$/) {
      $processor = \&processVhdl;
    } elsif ($file =~ /\.do$/) {
      $processor = \&processModelSimDo;
    } elsif ($file =~ /^isim_.+\.prj$/) {
      $processor = \&processIsimProject;
    } elsif ($file =~ /^xst_.+\.prj$/) {
      $processor = \&processXstProject;
    } elsif ($file =~ /^synplify_.+\.prj$/) {
      $processor = \&processSynplifyProject;
    } elsif ($file =~ /\.xise$/) {
      my ($project, $dirPath, $fileExt) = fileparse($file, '.xise');
      my $logFile = "$tmpDir/${project}_ise_update.log";
      &convertIseProject($project, $fromLib, $toLib, $logFile);
      next; # skip the default processFile route
    }
    &processFile($processor, "$tmpDir/$file", $fromLib, $file, $toLib);
  }

  chdir($cwd);
}

if (@ARGV != 3) {
  print "Usage: $0 target_dir from_lib to_lib\n";
  exit 1;
}
&processDirectory($ARGV[0], $ARGV[1], $ARGV[2]);
