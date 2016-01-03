package SgMap;
require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(runMap);

use strict;
use Cwd;
use File::DosGlob;
use File::Path;
use Sg;
use SgCache;
use SgUtil;
use SgXst;
use SgSyn;
use SgXFlow;
use SgSynth;


sub runMap {

  my $mapInstrs = $_[0];
  select(STDERR);
  $|=1;
  my $synopsis = &Sg::evaluateSynopsis();
  my $globals  = $synopsis->{"attributes"};
  my $clkWrapper = $globals->{"clkWrapper"};
  my $filesList = $globals->{"files"};
  my $cntr = 0;

  my $systemClkPeriod = $globals->{"systemClockPeriod"};
  my $fullPart = $globals->{"device"};
  my $hdlKind = $globals->{"hdlKind"};
  my $synthTool = $globals->{"synthesisTool"};
  my $sysgen = $globals->{"sysgen"};
  my $design = $globals->{"design"};
  my $topLevel = $globals->{"design"};
  my $implOptionsFile = "mapper_only.opt";
  my $iob = "False";
  if ( exists($mapInstrs->{"iob"}) ) {
    $iob = $mapInstrs->{"iob"};
  }

  my $instrs = {
    "synthesisTool" => $synthTool,
    "design" => $clkWrapper,
    "customTopLevel"=>$topLevel,
    "files" => $filesList,
    "clkWrapper" => $clkWrapper,
    "freq" => 1000.0 / $systemClkPeriod,
    "hdlKind" => $hdlKind,
    "omitIobs" => 0,
    "xcfFile" => undef,
    "ncfFile" => undef,
    "exposeStdStreams" => 1,
    "omitCores" => 1,
    "implOptionsFile" => $implOptionsFile,
    "configOptionsFile" => undef,
    "fullPart"=>$fullPart,
    "iob"=>$iob
  };
  open(XCF_FILE,">${clkWrapper}.xcf");
  close(XCF_FILE);
  open(NCF_FILE,">${clkWrapper}.ncf");
  close(NCF_FILE);
  open(UCF_FILE,">${clkWrapper}.ucf");
  close(UCF_FILE);
  print "Calling Synthesis tools\n";
  &SgSynth::runSynthesisTool($instrs);
  print "Calling XFlow tools\n";
  &SgXFlow::runXFlow($instrs);
  return;
}

1;
