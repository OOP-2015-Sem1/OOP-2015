package provide iseutil 1.0

namespace eval ::xilinx::iseutil {
    namespace export GetISEVersionStr
}

proc ::xilinx::iseutil::GetISEVersionStr {{xilinx ""}} {
    global env
    if {$xilinx == ""} {
        set xilinx $env(XILINX)
    }
    
    set filesetPath [file join $xilinx "fileset.txt"]
    
    set sDefaultVersionStr "10.0i"
    
    if {$filesetPath == "" } {
        puts "filesetPath is empty"
        return $sDefaultVersionStr
    }
    
    if { [catch { open $filesetPath r } hFset] } {
        puts "ERROR: Could not open $filesetPath: $hFset"
        return $sDefaultVersion
    }
    
    # Scan for the version entry.
    # Since there can be multiple lines in this file for service packs,
    # we need to scan for the *last* version entry in the file.
    set versionStr ""
    while { ![eof $hFset] } {
        set line [gets $hFset]
        # get the LAST entry in the file.
        regexp {version=(.*)} $line match versionStr
    }
    
    catch { close $hFset }
    
    set versionStr [string trim $versionStr]
    
    if { $versionStr == "" } {
        set versionStr $sDefaultVersionStr
    }
    
    return $versionStr
}
