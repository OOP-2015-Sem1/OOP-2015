namespace eval xilinx {
    namespace eval Hwcosim {

        #
        # Get the path of hardware co-simulation compiler executable.
        #
        proc getHwcosimCompilerPath {} {
            switch $::xilinx::platform {
                "nt" {
                    set hwcosimCompilerPath [format "sysgen/bin/nt/hwcosim_compile.exe"]
                }
                "nt64" {
                    set hwcosimCompilerPath [format "sysgen/bin/nt64/hwcosim_compile.exe"]
                }
                "lin" {
                    set hwcosimCompilerPath [format "sysgen/bin/lin/hwcosim_compile"]
                }
                "lin64" {
                    set hwcosimCompilerPath [format "sysgen/bin/lin64/hwcosim_compile"]
                }
                default {
                    return {}
                }
            }
            if { [info exists ::env(XILINX_DSP)] } {
                set xilinxPath $::env(XILINX_DSP)
                set hwcosimCompilerFullPath [file join $xilinxPath $hwcosimCompilerPath]
                if { [file exist $hwcosimCompilerFullPath] } {
                    return $hwcosimCompilerFullPath
                }
            }
            if { [info exists ::env(XILINX)] } {
                set xilinxPath $::env(XILINX)
                set hwcosimCompilerFullPath [file join $xilinxPath $hwcosimCompilerPath]
                if { [file exist $hwcosimCompilerFullPath] } {
                    return $hwcosimCompilerFullPath
                }
            }
            return {}
        }

        #
        # Get a list of boards supported for ISim Hardware Co-Simulation.
        # A device/part name can be optinally specified to check for board compatibility.
        # The list contains elemnts in form of { BoardId BoardDescription IsCompatible }
        #
        proc getISimHwcosimBoardList { { device "" } } {
            set boardList {}

            # Construct the command line
            set hwcosimCompilerPath [getHwcosimCompilerPath]
            if {$hwcosimCompilerPath eq ""} {
                return $boardList
            }

            set cmdLine [list exec $hwcosimCompilerPath "--print-boards"]
            if {$device ne ""} {
                lappend cmdLine "--device"
                lappend cmdLine $device
            }

            # Parse the output of "hwcosim_compile --print-boards"
            if {[catch {eval $cmdLine} result]} {
                puts "ERROR: Failed to retrieve the list of boards for ISim Hardware Co-Simulation.\n$:::errorInfo\n"
                return $boardList
            }
            foreach line [split $result "\n"] {
                set token [split $line "\t"]
                set isCompatible [expr { [string compare [lindex $token 0] {!}] != 0 }]
                set board [list [lindex $token 1] [lindex $token 2] $isCompatible]
                lappend boardList $board
            }

            return $boardList
        }
    }
}
