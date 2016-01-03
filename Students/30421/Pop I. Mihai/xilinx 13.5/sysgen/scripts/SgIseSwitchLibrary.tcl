namespace eval ::xilinx::dsptool::iseproject {

    proc handle_exception {script {msg ""} {append_msg True}} {
        if [catch { uplevel $script } result] {
            if {$msg eq ""} {
                set msg "An internal error occurred."
            }
            puts stderr "$msg"
            if {$append_msg} {
                puts stderr "\n$result"
            }
            return 0
        }
        return 1
    }

    proc library_exists { lib } {
        set n [collection sizeof [search $lib -exactmatch -type lib_vhdl]]
        return [expr { $n > 0 }]
    }

    proc add_file_to_library { lib fileobj } {
        set fullpath [object get $fileobj name]
        set filename [lindex [file split $fullpath] end]
        if [regexp {\.(v|vhd|vhdl)$} $filename] {
            lib_vhdl add_file $lib $filename
        }
    }

    proc create_library { lib } {
        if { ![library_exists $lib] } {
            lib_vhdl new $lib
        }
    }

    proc delete_library { lib } {
        if { $lib ne "work" } {
            lib_vhdl delete $lib
        }
    }

    proc switch_library { from_lib to_lib } {
        if { ![library_exists $from_lib] } {
            return
        }

        create_library $to_lib

        set filelist [lib_vhdl get $from_lib files]
        set filecnt [collection sizeof $filelist]
        set changecnt [collection foreach f $filelist {
            add_file_to_library $to_lib $f
        }]

        if { $changecnt == $filecnt } {
            delete_library $from_lib
        }       
    }

    proc library_summary { } {
        collection foreach lib [search * -type lib_vhdl] {
            puts "============================================================"
            puts "Library: [object get $lib name]"
            puts "------------------------------------------------------------"
            collection foreach f [lib_vhdl get $lib files] {
                puts [object get $f name]
            }
        }
    }

    proc switch_library_in_project { ise_project from_lib to_lib } {
        return [handle_exception {
            project open $ise_project
            switch_library $from_lib $to_lib
            project close
        } "ERROR: An error occurred when switching library in ISE project." True]
    }

}

if {$argc != 3} {
    puts "Usage: $argv0 ise_project from_lib to_lib"
    exit 1
}

set status [::xilinx::dsptool::iseproject::switch_library_in_project [lindex $argv 0] [lindex $argv 1] [lindex $argv 2]]
exit [ expr {$status ? 0 : 1} ]
