package SgXFlow;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(runXFlow);

use strict;
use Cwd;
use Carp qw(croak);
use Sg;
use SgEnv;
use SgIse;

use vars qw($errorPatterns);

# Patterns for errors in logs from various tools.
$errorPatterns = {
  "xflow" => [
    "^ERROR",
    ": Completed - [1-9]\\d* errors found",
  ],
};

#
#  Invokes XFlow to run ngdbuild, map, par, and bitgen.
#
#  @param instrs XTable of XFlow instructions.
#
#
sub runXFlow
{
    my $instrs = $_[0];

    # Results from xflow are stored in this file.
    my $logFile = "xflow.results";

    # Break out individual keys/values from %$instrs hash table.
    my $design = &Sg::getAttribute("design", $instrs);
    my $fullPart = &Sg::getAttribute("fullPart", $instrs);
    my $implOptionsFile = &Sg::getAttribute("implOptionsFile", $instrs);
    my $configOptionsFile = &Sg::getAttribute("configOptionsFile", $instrs, 1);

    # Allow impossible timing in map for Timing Analysis flow.
    my $allowImpossibleTiming = &Sg::getAttribute("allowImpossibleTiming", $instrs, 1);
    if (defined($allowImpossibleTiming) && $allowImpossibleTiming) {
        $ENV{'XIL_TIMING_ALLOW_IMPOSSIBLE'} = '1';  
    }

    my $xflow = &SgEnv::getXFlowExePath();
    defined($xflow) || croak "user: XFlow is not in your path";
    my @cmd = ($xflow, '-p', $fullPart, '-implement', $implOptionsFile);

    if (defined($configOptionsFile)) {
        push @cmd, '-config', $configOptionsFile;
    }
    push @cmd, $design;

    # Run xflow.
    my $status = &Sg::runcmd(@cmd) == 0;

    if (-e "xflow.log") {
        my $err = copyordie("xflow.log", $logFile);
    }

    # Check the log file for timing constraint violations and
    # give a clear error message.
    if (open(LOGFILE, "< $logFile")) {
        my @log = <LOGFILE>;
        foreach my $line (@log) {
            if ($line =~ /([1-9]\d* constraints?) not met/) {
                my $errmsg = "user: Failed to implement the design as it could not meet $1. " .
                  "Try to optimize the critical paths or lower the clock frequency of the design. " .
                  "Please refer to '$logFile' for further details.";
                croak $errmsg;
            }
        }
        close(LOGFILE);
    }

    # Check the log file for other errors.
    &Sg::checkLog("xflow", $errorPatterns->{'xflow'}, $logFile);
    croak "XFlow failed" unless ($status);
}


1;
