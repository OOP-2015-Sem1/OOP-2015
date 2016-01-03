package SgXPower;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(runXPower);

use strict;
use Cwd;
use Carp qw(croak);
use Sg;
use SgEnv;
use SgIse;

use vars qw($errorPatterns);

# Patterns for errors in logs from various tools.
$errorPatterns = {
  "xpwr" => [
    "^ERROR",
  ],
};

#
#  Invokes XPower to analysis power.
#
#  @param instrs XTable of XPower instructions.
#
#
sub runXPower
{
    my $instrs = $_[0];

    # Break out individual keys/values from %$instrs hash table.
    my $design = &Sg::getAttribute('design', $instrs);
    my $powerAnalysisMode = &Sg::getAttribute('powerAnalysisMode', $instrs, 1);

    my $saifFile;
    if ($powerAnalysisMode eq 'isim-simulation') {
        my $cwd = getcwd();

        # FIXME:
        chdir "..";
        my $dut = &Sg::getAttribute('design', 0);
        my $clkWrapper = &Sg::getAttribute('clkWrapper', 0);
        my $testbench = &Sg::getAttribute('testbench', 0, 1);

        my $isimtcl = "isim_${dut}_run.tcl";
        if (-e $isimtcl) {
            my $simexe = "isim_${dut}.exe";
            my $fuse = &SgEnv::getISimFuseExePath();
            defined($fuse) || croak "user: ISim Fuse is not in your path";

            # Run ISim Fuse to compile the design.
            my $isimprj = "isim_${dut}.prj";
            open(F, $isimprj) || croak "user: Failed to open ISim project '$isimprj'.";
            my $has_verilog = (scalar(grep(/^verilog/, <F>)) > 0);
            close(F);
            my @cmd = ($fuse, '-prj', $isimprj, '-o', $simexe, $testbench);
            if ($has_verilog) {
                push(@cmd, 'glbl', '-L', 'unisims_ver', '-L', 'simprims_ver',
                    '-L', 'xilinxcorelib_ver', '-L', 'unimarcos_ver');
            }
            my $status = &Sg::runcmd(@cmd) == 0;
            croak "user: Failed to compile the design for ISim simulation." unless ($status);

            # Run ISim-generated simulation executable.
            my $isimlog = "isim_${dut}_run.log";
            @cmd = ($simexe, '-tclbatch', $isimtcl, ">$isimlog", '2>&1');
            $status = &Sg::runcmd(@cmd) == 0;
            croak "user: Failed to simulate the design through ISim." unless ($status);
        }
        chdir($cwd);
        $saifFile = "../${clkWrapper}.saif";
    }

    my $xpwr = &SgEnv::getXPowerExePath();
    defined($xpwr) || croak "user: XPower is not in your path";
    my @cmd = ($xpwr, '-v', "${design}.ncd", "${design}.pcf");
    if (defined($saifFile) && -f $saifFile) {
        push @cmd, '-s', $saifFile;
    }

    # Run xpwr.
    my $status = &Sg::runcmd(@cmd) == 0;

    # Results from xpwr are stored in this file.
    my $logFile = "${design}.pwr";

    # Check the log file for other errors.
    &Sg::checkLog("xpwr", $errorPatterns->{'xpwr'}, $logFile);
    croak "XPower failed" unless ($status);
}


1;
