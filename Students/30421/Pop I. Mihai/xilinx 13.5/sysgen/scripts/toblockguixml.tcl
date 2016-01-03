#!tclsh
set cwd [pwd]
set script_dir [file dirname $argv0]
cd $script_dir
source DSPAPI.tcl 
source ISEUtil.tcl 
cd $cwd

if {$argc == 0} {
    exit -1
}
if {$argc == 1} {
    set config_file [lindex $argv 0]
    if {![file exists $config_file]} {
        exit -1
    }
} 
if {$argc > 1} {
    set ip_name [lindex $argv 0]
    set ip_version [lindex $argv 1]
}
if {$argc > 2} {
    set lib_name [lindex $argv 2]
} 
if {$argc > 3} {
    set enablement_function [lindex $argv 3]
} 
if {$argc > 4} {
    exit -1
}

set isever [xilinx::iseutil::GetISEVersionStr]
set api_version 9
if { [regexp "^(\\d+)" $isever ise_major] } {
    if { $ise_major >= 10 } {
        set api_version 10
    }
}
xilinx::dsptool::init $api_version


if {$argc == 1} {
    xilinx::dsptool::toBlockXmlWrapper $config_file
} elseif {$argc == 2} {
    xilinx::dsptool::toBlockXmlWrapper $ip_name $ip_version
} elseif {$argc == 3} {
    xilinx::dsptool::toBlockXmlWrapper $ip_name $ip_version $lib_name
} elseif {$argc == 4} {
    xilinx::dsptool::toBlockXmlWrapper $ip_name $ip_version $lib_name $enablement_function
}


