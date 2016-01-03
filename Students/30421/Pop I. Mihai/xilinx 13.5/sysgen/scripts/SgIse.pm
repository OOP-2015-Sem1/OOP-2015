# SgIse.pm
#
# This module is responsible for generating the script that generates an ISE
# Project Navigator project file.

package SgIse;

use strict;

require Exporter;
use vars qw(@ISA @EXPORT);
@ISA = qw(Exporter);
@EXPORT = qw(makeIseFiles);

use POSIX qw(ceil);
use Cwd;
use File::Basename;
use File::Spec;
use Carp qw(croak);
use FindBin;
use lib "$FindBin::Bin";
use Sg;
use SgEnv;
use SgUtil;

use vars qw($errorPatterns);

# Patterns for errors in logs from various tools.
$errorPatterns = {
    "xtclsh" => [
        "^ERROR",
    ],
};

#
# Make ISE Project Navigator .ise project file.
#
# @param %$args Preloaded attributes hash reference.
#
# @return Array containing the name of the .ise file that was generated.
#
sub makeIseFiles {
    my $args = $_[0];

#    my $xilinx = $ENV{'XILINX'};
#    $xilinx =~ s/\\/\//g;
#    my $iseVersion = &findISEVer();
#    my $isISEVersion11 = ($iseVersion =~ /^11\./);
#    my $isISEVersionSupported = ($iseVersion =~ /^1\d\./);

    # Make sure the ISE version is supported.
    my @baseFiles;
#    if ($isISEVersionSupported) {
        push @baseFiles, 'SgIseProject.tcl';
        push @baseFiles, 'SgPaProject.tcl';
        push @baseFiles, 'DoPlanAheadGenTest.tcl';
#    }
#    else {
#        croak "Unsupported version of ISE: $iseVersion\n";
#    }
	 my $projType = Sg::getAttribute('proj_type', $args);
	 #my $compilation = Sg::getAttribute('compilation', $args);
	 my $compilation = 'HDL Netlist';
	 if(Sg::findAttribute('compilation', $args)) {
	     $compilation = Sg::getAttribute('compilation', $args);
	 }
	 my $synth_file = '';
	 if(Sg::findAttribute('synth_file', $args)) {
	     $synth_file = Sg::getAttribute('synth_file', $args);
	 }
	 $synth_file = getPAStrategyName($synth_file);
	 my $impl_file = '';
	 if(Sg::findAttribute('impl_file', $args)) {
	     $impl_file = Sg::getAttribute('impl_file', $args);
	 }
	 $impl_file = getPAStrategyName($impl_file);
    my $design = Sg::getAttribute("design", $args);
    my $mdlFile = Sg::getAttribute('design_full_path', $args);
    my $filesref = Sg::getAttribute('files', $args);
    my @hdlFiles = grep(/\.(v|vhd|vhdl)$/i, @$filesref);
    my @coreDataFiles = grep(/\.(mif|coe)$/i, @$filesref);
    my $ucfFile = Sg::getAttribute('ucfFile', $args, 1);
    my $clkWrapper = Sg::getAttribute('clkWrapper', $args);
    my $clkWrapperFile = Sg::getAttribute('clkWrapperFile', $args, 1);
    my $procBlockHDLName = Sg::getAttribute('proc_block_hdl_name', $args, 1);
    my $procBlockInfo = Sg::getAttribute('proc_block_info', $args, 1);
    my $readCores = 0;
    if (defined($procBlockInfo)) {
        my $procBlockNetlistingMode = $procBlockInfo->{'mode'} || '';
        my $procBlockDualClockMode = $procBlockInfo->{'dual_clock'} || 'off';  
        if (($procBlockNetlistingMode eq 'HDL netlisting') && ($procBlockDualClockMode eq 'on')) {
            ${readCores} = 1;
        }
    }
    my $readCoresStr = ${readCores} ? 'True' : 'False';
    my $bmmFile = "${clkWrapper}.bmm";
    if (defined($procBlockHDLName)) {
        open(BMMFILE, ">$bmmFile") || croak "Failed to create BMM file: $bmmFile\n";
        close(BMMFILE);
    }
    undef $bmmFile unless (-f $bmmFile);
    my $hdlkind = Sg::getAttribute('hdlKind', $args);
    my $fullPart = Sg::getAttribute('device', $args);
    my $synFlow = Sg::getAttribute('synthesisTool', $args);
    my $testbench = Sg::getAttribute('testbench', $args, 1);
    my $testbenchFile = Sg::getAttribute('testbenchFile', $args, 1);
    my $systemClockPeriod = Sg::getAttribute('systemClockPeriod', $args);
    my $sgdir = Sg::getAttribute('sysgen', $args);
    my $fileAttributes = Sg::getAttribute('fileAttributes', $args);

    # $systemClockPeriod is in ns
    my $freq = 1000.0 / $systemClockPeriod;

    # Define local variables for script manipulation based on simulation
    # language
    my $busDelimiter = ($hdlkind eq 'verilog') ? '[]' : '()';

    # Determine part information
    defined($fullPart) || croak "expected part to be defined\n";
    #my $pi = &findFamilyPartPackageSpeed($fullPart);
    my $pi = &findFamilyPartPackageSpeedFromAttributes($args);
    defined($pi) || croak "part name is badly formed\n";
    my $deviceFamily = $pi->{'family'};
    my $devicePart = $pi->{'part'};
    my $devicePackage = $pi->{'package'};
    my $deviceSpeed = $pi->{'speed'};
    my $useNewXSTParser = &Sg::isNewXSTParser($deviceFamily);
    defined($synFlow) ||
        croak "expected synopsis file to define 'synthesisTool'\n";

    # Extract constraints files name based upon synthesis choice
    my $constraintsFile;
    my $fanout;
    if ($synFlow =~ /Synplify/i) {
        $constraintsFile = Sg::getAttribute('sdcFile', $args);
        $fanout = 50000;
    }
	 elsif ($synFlow =~ /RDS/i) {
	 }
    else {
        $constraintsFile = Sg::getAttribute('xcfFile', $args);
    }

    my $sysgenVer = &findSysgenVer($sgdir);
    $sysgenVer = "v$sysgenVer" if ($sysgenVer ne '');

    # Open header Tcl file in the working directory.
    defined($clkWrapper) ||
        croak "expected synopsis file to define 'clkWrapper'\n";

		  #my $importFile = "${clkWrapper}_import.tcl";
    my $importFile = "ProjectGeneration.tcl";
    # Make sure the old import file is removed
    unlink $importFile;
    ! -e $importFile || croak "couldn't remove old $importFile: $!\n";

    my $iseProjectFile = "$clkWrapper.ise";
    my $xiseProjectFile = "$clkWrapper.xise";
    my $giseProjectFile = "$clkWrapper.gise";

    #
    # Remove stale copies of files
    #
    unlink($iseProjectFile);
    unlink("${iseProjectFile}_ISE_Backup");

    #
    # Copy in base or template file(s)
    #

	 if ($projType =~ /PlanAhead/i) {
		 my $err = copyordie(buildPath($sgdir, "data/planahead/$baseFiles[1]"), $baseFiles[1]);
		 chmod 0775, $baseFiles[1];
		 my $err = copyordie(buildPath($sgdir, "data/planahead/$baseFiles[2]"), $baseFiles[2]);
		 chmod 0775, $baseFiles[2];
	 }
	 else {
		 my $err = copyordie(buildPath($sgdir, "data/projnav/$baseFiles[0]"), $baseFiles[0]);
		 chmod 0775, $baseFiles[0];
	 }

    open(ISEIMPORT,">$importFile") || croak "couldn't write to $importFile: $!\n";

    my $dateTime = localtime;

    # Set the project properties
    #
    # Note that for the keys "PROP_DevDevice", "PROP_DevFamily" values should be lowercase
    # For CPLDs, the "Package" key's values whould be uppercase
    # For FPGAs, the "Package" key's values whould be lowercase
    #
    $deviceFamily = ucfirst($deviceFamily); # no need to capitalize it
    my $lcdevice = lc($deviceFamily);
    # Fix cases on special families
    my %fixcases = (
        'Spartan2e'  => 'Spartan2E',
        'Spartan3a'  => 'Spartan3A and Spartan3AN',
        'Spartan3adsp' => 'Spartan-3A DSP',
        'Spartan3e'  => 'Spartan3E',
        'Virtexe'    => 'VirtexE',
        'Virtex2p'   => 'Virtex2P',
    );
    if (exists $fixcases{$deviceFamily} ) {
        $deviceFamily = $fixcases{$deviceFamily};
    }

    print ISEIMPORT <<EOT;
#
# Created by System Generator $sysgenVer    $dateTime
#
# Note: This file is produced automatically, and will be overwritten the next
# time you press "Generate" in System Generator.
#

namespace eval ::xilinx::dsptool::iseproject::param {
EOT

	 if(defined($synth_file) && ($synth_file ne '')) {
    print ISEIMPORT <<EOT;
    set SynthStrategyName {$synth_file}
EOT
 }
	 if(defined($impl_file) && ($impl_file ne '')) {
    print ISEIMPORT <<EOT;
    set ImplStrategyName {$impl_file}
EOT
 }

    print ISEIMPORT <<EOT;
    set Compilation {$compilation}
    set Project {$clkWrapper}
    set Family {$deviceFamily}
    set Device {$devicePart}
    set Package {$devicePackage}
    set Speed {-$deviceSpeed}
    set HDLLanguage {$hdlkind}
    set SynthesisTool {$synFlow}
    set Simulator {Modelsim-SE}
    set ReadCores {$readCoresStr}
    set MapEffortLevel {High}
    set ParEffortLevel {High}
    set Frequency {$freq}
    set NewXSTParser {$useNewXSTParser}
    set ProjectFiles {
EOT
    if (defined($clkWrapperFile) && ($clkWrapperFile ne '')) {
      my $lib = $$fileAttributes{$clkWrapperFile}{'libName'};
      $lib = (defined($lib) && ($lib ne '')) ? " -lib {${lib}}" : '';
      print ISEIMPORT <<EOT
        {{${clkWrapperFile}}${lib} -view All}
EOT
    }
    foreach my $file (@hdlFiles) {
        next if ($file eq $clkWrapperFile);
        my $view = ' -view All';
        if (defined($testbenchFile) && ($file eq $testbenchFile)) {
          $view = ' -view Simulation';
        }
        my $lib = $$fileAttributes{$file}{'libName'};
        $lib = (defined($lib) && ($lib ne '')) ? " -lib {${lib}}" : '';
        print ISEIMPORT <<EOT
        {{${file}}${lib}${view}}
EOT
    }
    if (defined($ucfFile)) {
        print ISEIMPORT <<EOT
        {{${ucfFile}}}
EOT
    }
    if (defined($bmmFile)) {
        print ISEIMPORT <<EOT
        {{${bmmFile}}}
EOT
    }
    foreach my $file (@coreDataFiles) {
        print ISEIMPORT <<EOT
        {{${file}}}
EOT
    }
    # Add Sysgen MDL file
    if (defined($mdlFile) && (-f $mdlFile)) {
        print ISEIMPORT <<EOT;
        {{${mdlFile}}}
EOT
    }
    print ISEIMPORT <<EOT;
    }
    set TopLevelModule {${clkWrapper}}
EOT

    if (defined($fanout)) {
    print ISEIMPORT <<EOT;
    set FanoutLimit {$fanout}
EOT
    }

    if (defined($testbenchFile)) {
        my ($testbenchModule) = fileparse($testbenchFile, qr/\.(v|vhd|vhdl)$/);
        print ISEIMPORT <<EOT
    set TestBenchModule {$testbenchModule}
EOT
    }
    if (defined($constraintsFile) && ($constraintsFile ne '')) {
        print ISEIMPORT <<EOT;
    set SynthesisConstraintsFile {$constraintsFile}
EOT
    }

    # Set the test bench properties
    if (defined($testbench) && $testbench) {
        my $vsimTime = Sg::getAttribute('vsimtime', $args);
        $vsimTime .= " ns" unless ($vsimTime =~ / ns$/ || $vsimTime =~ / ps$/);
        print ISEIMPORT <<EOT;
    set ISimCustomProjectFile {isim_${design}.prj}
    set SimulationTime {$vsimTime}
    set BehavioralSimulationCustomDoFile {pn_behavioral.do}
    set PostTranslateSimulationCustomDoFile {pn_posttranslate.do}
    set PostMapSimulationCustomDoFile {pn_postmap.do}
EOT
    }

    # Set DSP Tools specific properties
    print ISEIMPORT <<EOT;
    set ImplementationStopView {Structural}
    set ProjectGenerator {SysgenDSP}
}
EOT

if ($projType =~ /PlanAhead/i) {
	print ISEIMPORT <<EOT;
    source SgPaProject.tcl
    if { [info exists ::xilinx::dsptool::planaheadprojecttest::is_doing_planAheadGenTest] } {
        ::xilinx::dsptool::planaheadproject::compile_planahead_project
    } elseif { [info exists ::xilinx::dsptool::planaheadprojecttest::is_doing_planAheadGenPostSynthTest] } {
        ::xilinx::dsptool::planaheadproject::compile_planahead_project
    } else {
        ::xilinx::dsptool::planaheadproject::create
    }
EOT
} else {
	print ISEIMPORT <<EOT;
    source SgIseProject.tcl
    ::xilinx::dsptool::iseproject::create
EOT
}

    close(ISEIMPORT);
    chmod 0555, "$importFile";

    close(STDERR);
    close(STDOUT);

    my $logFile = &SgEnv::rel2abs("${clkWrapper}_import.log");
    unlink($logFile);

    if ($projType =~ /PlanAhead/i) {
		 return [""];
	 }

    my $xtclsh = &SgEnv::getXTclshExePath();
    defined($xtclsh) || croak "Could not find the xtclsh executable.\n";

    my $exitcode = &Sg::runcmd($xtclsh,
		 #"${clkWrapper}_import.tcl",
		 "$importFile",
                               '2>&1', ">$logFile");
    if ($exitcode != 0) {
      croak "An error occurred when creating the ISE project.\n";
    }
    &Sg::checkLog('xtclsh', $errorPatterns->{'xtclsh'}, $logFile, 1);

	 #my $logFilePa = &SgEnv::rel2abs("${clkWrapper}_importPa.log");
	 #unlink($logFilePa);

	 #my $planahead = &SgEnv::getPlanAheadExePath();
	 #defined($planahead) || croak "Could not find the planAhead executable.\n";

	 #my $exitcodepa = &Sg::runcmd($planahead,
	 #"-mode batch -source ${clkWrapper}_import.tcl",
	 #'2>&1', ">$logFilePa");
	 #if ($exitcodepa != 0) {
	 #croak "An error occurred when creating the PlanAhead project.\n";
	 #}

    # Create the sgp file
    my $sgpFile = "${clkWrapper}.sgp";
    open(SGPFILE, "> $sgpFile") || croak "trouble writing $sgpFile";
    close(SGPFILE) || croak "trouble writing $sgpFile";

    # Add ise and sgp file to generated files list
    my @generatedFiles;
#    if ($isISEVersion11) {
#        @generatedFiles = ($iseProjectFile, $xiseProjectFile, $giseProjectFile, $sgpFile);
#    }
#    else {
        @generatedFiles = ($xiseProjectFile, $giseProjectFile, $sgpFile);
#    }
    my $synopsis = &Sg::evaluateSynopsis();
    &Sg::updateFilesList($synopsis, \@generatedFiles);
    &Sg::writeSynopsis($synopsis);
    return [$xiseProjectFile];
}

sub getPAStrategyName {
	my $strategyName = '';
	my $paStrategyFileName = $_[0];
	chomp($paStrategyFileName);
	if(length($paStrategyFileName) <= 0 )
	{
		return $strategyName;
	}
	
	my @a=split(/\//, $paStrategyFileName);
	#@a=split(/\\/, $paStrategyFileName);
	my $n = @a;
	if($n <= 0)
	{
		return $strategyName;
	}

	$strategyName = $a[$n-1];
	if($strategyName =~ /(.+)\..+\..*/)
	{
		$strategyName = $1;
	}
	return $strategyName;
}


# obReturnValue
1;
