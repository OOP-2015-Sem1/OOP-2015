package SgEnv;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(platform os_is_64bit username exeSuffix shlibSuffix pathSeparator scrubFileName
             getTempDir getUserTempDir canonpath rel2abs
             getISERootPath getISEPlatformTag
             getMatlabRootPath getMatlabExePath
             getSysgenRootPath getSysgenBinPath getSysgenScriptsPath
             getCoregenExePath getISimFuseExePath
             getXstExePath getXTclshExePath getPlanAheadExePath getXFlowExePath getXPowerExePath getXilPerlExePath
             getMapExePath getParExePath getBitgenExePath getPartgenExePath
             getNgcBuildExePath getNgdBuildExePath getNetGenExePath
             getSynplifyRootPath getSynplifyExePath getSynplifyProExePath);

use strict;
use File::Spec;
use File::Basename;
use Carp qw(croak);
use Config;
use vars qw($_platform $_os_is_64bit);

#
# Returns the name of the platform on which we are running.
#
# @return Name of the platform on which we are running.
#

sub platform {
    return $_platform if (defined($_platform));

    if ($Config{"osname"} eq "solaris") {
        $_platform = "sol";
    }
    elsif ($Config{"osname"} eq "sunos") {
        $_platform = "sun";
    }
    elsif ($Config{"osname"} eq "aix") {
        $_platform = "rs6000";
    }
    elsif ($Config{"osname"} eq "hpux") {
        $_platform = "hp";
    }
    elsif ($Config{"osname"} eq "Windows_NT" ||
        $Config{"osname"} eq "Windows_95" ||
        $Config{"osname"} eq "MSWin32") {
        $_platform = "nt";
    }
    elsif ($Config{"osname"} eq "cygwin") {
        $_platform = "cygwin";
    }
    elsif ($Config{"osname"} eq "linux") {
        $_platform = "linux";
    }
    elsif ($Config{"osname"} eq "darwin") {
        $_platform = "macos";
    }
    else {
        croak "couldn't determine platform type";
    }

    return $_platform;
}

#
# Returns the user name of the current process.
#
sub username {
    return getlogin || getpwuid($<);
}

#
# Returns the system temporary directory.
#
sub getTempDir {
    my $temp = $ENV{'TMPDIR'} || $ENV{'TMP'} || $ENV{'TEMP'} || $ENV{'Temp'};
    if (!$temp) {
        my $plat = &platform();
        $temp = ($plat eq 'nt' || $plat eq 'cygwin') ? 'c:/temp' : '/tmp';
    }
    return $temp;
}

#
# Checks if the machine is 64-bit or not.
#
# @return true if the machine is 64-bit.
#

sub os_is_64bit {
    return $_os_is_64bit if (defined($_os_is_64bit));

    my $xilperl = &getXilPerlExePath();

    if ($xilperl =~ /\/bin\/nt64\/xilperl/ || $xilperl =~ /\/bin\/lin64\/xilperl/) {
        $_os_is_64bit = 1;
    }
    else {
        $_os_is_64bit = 0;
    }

    return $_os_is_64bit;
}

#
# Returns the per-user temporary directory.
#
sub getUserTempDir {
    my $tempdir = &getTempDir();
    my $username = &SgEnv::username();
    if ($username) {
        $tempdir .= "/sysgentmp-${username}";
    }
    else {
        $tempdir .= "/sysgentmp";
    }
    return $tempdir;
}


sub canonpath {
  my $p = $_[0];
  $p = File::Spec->canonpath($p);
  $p =~ s/\\/\//g;
  return $p;
}

sub rel2abs {
  my $p = $_[0];
  $p = File::Spec->rel2abs($p);
  $p =~ s/\\/\//g;
  return $p;
}

#
# Returns the platform-specific file suffix of executables.
#
# @return File suffix of executables.
#
sub exeSuffix {
    return &platform() eq 'nt' ? '.exe' : '';
}

#
# Returns the platform-specific file suffix of shared libraries.
#
# @return File suffix of shared libraries.
#
sub shlibSuffix {
    return &platform() eq 'nt' ? '.dll' : '.so';
}

#
# Returns the platform-specific path separator.
#
# @return Path separator.
#
sub pathSeparator {
    return &platform() eq 'nt' ? ';' : ':';
}

#
# Returns a cleaned up version of a file name.
#
# @param $file File name to clean.
#
# @return Cleaned-up version of $file.
#

sub scrubFileName {
    my $file = $_[0];
    $file =~ s/\\/\//g;
    if (&platform eq 'nt' || &platform eq 'cygwin') {
        $file =~ tr/A-Z/a-z/;
    }
    $file =~ s/\/*$//;
    return $file;
}

#
# Returns a path to an executable, or undef if no such executable is found.
#
# @param $exe Executable name.
#
# @return Location of $exe, or undef if no $exe is found in the path.
#
sub which {
    my $exe = $_[0];
    my $sep = &pathSeparator();
    my @pathParts = split(/$sep/, $ENV{'PATH'});
    my $suffix = &exeSuffix();
    my $part;
    foreach $part (@pathParts) {
        $part = &scrubFileName($part);
        my $loc = "${part}/${exe}${suffix}";
        next unless (-e $loc);
        return &scrubFileName($loc);
    }
    return undef;
}

#
#  Returns the ISE platform-specific tag.
#
sub getISEPlatformTag
{
    my $is_64bit = &os_is_64bit();
    if (($^O eq 'MSWin32') || ($^O eq 'cygwin')) {
        return ($is_64bit) ? 'nt64' : 'nt';
    }
    elsif ($^O eq 'linux') {
        return ($is_64bit) ? 'lin64' : 'lin';
    }
}

#
#  Returns the installation path of ISE.
#
sub getISERootPath
{
    my $path = $ENV{'XILINX'};
    $path =~ s/\\/\//g if defined($path);
    return $path;
}

#
#  Returns the path to the MATLAB executable.
#
sub getMatlabExePath
{
    return &which('matlab');
}

#
#  Returns the installation path of MATLAB.
#
sub getMatlabRootPath
{
    my $path = &getMatlabExePath();
    if (defined($path)) {
      $path = &SgEnv::canonpath($path);
      $path = &dirname($path) unless (-d $path);
      $path =~ s/\\/\//g;
      if (($^O eq 'MSWin32') || ($^O eq 'cygwin')) {
          if (&os_is_64bit()) {
              $path =~ s/\/win64$//;
          }
          else {
              $path =~ s/\/win32$//;
          }
      }
      $path =~ s/\/bin$//;
    }
    return $path;
}

#
#  Returns the installation path of Sysgen.
#
sub getSysgenRootPath
{
    my $path = $ENV{'SYSGEN'};
    $path =~ s/\\/\//g if defined($path);
    return $path;
}

#
#  Returns the path of Sysgen bin directory.
#
sub getSysgenBinPath
{
    my $path = &getSysgenRootPath();
    $path .= '/bin' if defined($path);
    $path .= '/' . &getISEPlatformTag() if defined($path);
    return $path;
}

#
#  Returns the path of Sysgen scripts directory.
#
sub getSysgenScriptsPath
{
    my $path = &getSysgenRootPath();
    $path .= '/scripts' if defined($path);
    return $path;
}

#
#  Returns the path to the Coregen executable.
#
sub getCoregenExePath
{
    return &which('coregen');
}

#
#  Returns the path to the ISim Fuse executable.
#
sub getISimFuseExePath
{
    return &which('fuse');
}

#
#  Returns the path to the XST executable.
#
sub getXstExePath
{
  return &which('xst');
}

#
#  Returns the path to the XST executable.
#
sub getXTclshExePath
{
    return &which('xtclsh');
}

#
#  Returns the path to the XST executable.
#
sub getPlanAheadExePath
{
    return &which('planAhead');
}

#
#  Returns the path to the XFlow executable.
#
sub getXFlowExePath
{
    return &which('xflow');
}

#
#  Returns the path to the XPower executable.
#
sub getXPowerExePath
{
    return &which('xpwr');
}

#
#  Returns the path to the XilPerl executable.
#
sub getXilPerlExePath
{
    return &which('xilperl');
}

#
#  Returns the path to the Map executable.
#
sub getMapExePath
{
    return &which('map');
}

#
#  Returns the path to the Par executable.
#
sub getParExePath
{
    return &which('par');
}

#
#  Returns the path to the Bitgen executable.
#
sub getBitgenExePath
{
    return &which('bitgen');
}

#
#  Returns the path to the Partgen executable.
#
sub getPartgenExePath
{
    return &which('partgen');
}

#
#  Returns the path to the NGCBuild executable.
#
sub getNgcBuildExePath
{
    return &which('ngcbuild');
}

#
#  Returns the path to the NGDBuild executable.
#
sub getNgdBuildExePath
{
    return &which('ngdbuild');
}

#
#  Returns the path to the NetGen executable.
#
sub getNetGenExePath
{
    return &which('netgen');
}

#
#  Returns the path to the Synplify executable.
#
sub getSynplifyExePath
{
    return &which('synplify');
}

#
#  Returns the path to the Synplify Pro executable.
#
sub getSynplifyProExePath
{
    return &which('synplify_pro');
}

#
#  Returns the installation path of Synplify.
#
sub getSynplifyRootPath
{
    my $path = &getSynplifyExePath();
    $path = &getSynplifyProExePath() unless defined($path);
    if (defined($path)) {
      $path =~ s/\\/\//g;
      $path =~ s/\/bin\/synplify(_pro)?(\.exe)?//;
    }
    return $path;
}


1;
