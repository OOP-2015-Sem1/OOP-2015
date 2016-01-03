namespace eval sysgen {
    # IP instances that already have I/O pads on their ports
    # if their ports show up at the top-level ports, Sysgen should tag these
    # ports with 'none' 'IOB'
    # all IP instance names should be in lower cases
    variable ipinsts_noiopad [list "mpmc"]
    variable clk_name ""
    variable rst_name ""

    variable projpath
    variable projname
    variable synthesis_language
    variable arch 
    variable dev
    variable package
    variable speedgrade
    variable target_dir
    variable diff_clock_names

    proc xget_hw_ipinsts_handle_by_type {mhs_handle ip_name} {
    # return a list of handles of the ip instances of type 'ip_name'

        lappend matched_ipinst_handles

        set ipinst_handles [::xget_hw_ipinst_handle ${mhs_handle} *]
        foreach ipinst_handle ${ipinst_handles} {
            if [string match ${ip_name} [::xget_hw_value ${ipinst_handle}]] {
                lappend matched_ipinst_handles ${ipinst_handle}
            }
        }

        return ${matched_ipinst_handles}
    }

    proc unique_lappend {handle_list handle} {
    # do 'lappend' only when the item does not already in the list
        upvar ${handle_list} _handle_list
        if {[lsearch -exact _handle_list ${handle}] == -1} {
            lappend _handle_list ${handle}
        }
    }

    proc xget_hw_ipinst_connected_ports_handle {mhs_handle ipinst_handle port_name {type "all"}} {
    # return a list of port handles that connect to a port of 'port_name' on an IP instance
        lappend ports_handle
        set conn_list [::xget_hw_port_connectors_list ${ipinst_handle} ${port_name}]
        foreach conn ${conn_list} {
            set handle [::xget_hw_connected_ports_handle ${mhs_handle} ${conn} ${type}]
            for {set i 0} {$i < [llength ${handle}]} {incr i} {
                unique_lappend ports_handle [lindex ${handle} ${i}]
            }
        }
        return ${ports_handle}
    }

    proc xget_hw_ipinst_connected_ports_of_ip_handle {mhs_handle ipinst_handle port_name ip_name {type "all"}} {
        set ports_handle [xget_hw_ipinst_connected_ports_handle ${mhs_handle} ${ipinst_handle} ${port_name} ${type}]
        lappend ports_of_ip_handle
        foreach handle ${ports_handle} {
            set ipinst_handle [::xget_hw_parent_handle ${handle}]
            if {[string compare ${ip_name} [::xget_hw_value ${ipinst_handle}]] == 0} {
                unique_lappend ports_of_ip_handle ${handle}
            }
        }
        return ${ports_of_ip_handle}

    }

    proc xget_hw_ipinst_connected_ipinsts_handle {mhs_handle ipinst_handle port_name {type "all"}} {
        lappend ipinsts_handle
        set ports_handle [xget_hw_ipinst_connected_ports_handle ${mhs_handle} ${ipinst_handle} ${port_name} ${type}]
        foreach handle ${ports_handle} {
            unique_lappend ipinsts_handle [::xget_hw_parent_handle ${handle}]
        }
        return ${ipinsts_handle}
    }

    proc xget_hw_ipinsts_type_from_handle {ipinsts_handle} {
    # given a list of IP instance handles, return their names
        upvar ${ipinsts_handle} _ipinsts_handle
        lappend names
        foreach handle ${_ipinsts_handle} {
            # the hw_value of an ipinst is the IP name
            lappend names [::xget_hw_value ${handle}]
        }
        return ${names}
    }

    proc xget_hw_ipinst_connected_ipinsts_handle_by_type {mhs_handle ipinst_handle ipinst_type port_name {type "all"}} {
        set ipinsts_handle [xget_hw_ipinst_connected_ipinsts_handle ${mhs_handle} ${ipinst_handle} ${port_name} ${type}]
        set ipinsts_name [xget_hw_ipinsts_type_from_handle ipinsts_handle]
        set indices [lsearch -exact -all ${ipinsts_name} ${ipinst_type}]
        return [lindex ${ipinsts_handle} ${indices}]
    }

    proc xget_hw_ipinst_connected_clock_generator_handle {mhs_handle ipinst_handle port_name} {
        return [xget_hw_ipinst_connected_ipinsts_handle_by_type ${mhs_handle} ${ipinst_handle} "clock_generator" ${port_name} "source"]
    }

    proc xget_hw_plb_clock_generator_handle {mhs_handle ipinst_handle} {
        return [xget_hw_ipinst_connected_clock_generator_handle ${mhs_handle} ${ipinst_handle} "PLB_Clk"]
    }

    # find out the clock frequency of the PLB bus interface. The clock
    # frequency, if found, is used to generate cross-clock domain constraints. 
    # @return the clock frequency of the PLB bus interface if found; zero if not found.
    proc xget_hw_plb_clock_freq {mhs_handle ipinst_handle} {
        set clock_freq ""

        set port_handle [xget_hw_ipinst_connected_ports_handle ${mhs_handle} ${ipinst_handle} "PLB_Clk" "source"]
        if {[string length ${port_handle}] == 0} { return -1; }
        if {[llength ${port_handle}] > 1} {
            set port_handle [lindex ${port_handle} 0]
        }

        set ipinst_handle [::xget_hw_parent_handle ${port_handle}]

        if {[string length ${ipinst_handle}] == 0} {
            # in case that the IP instance corresponding to port_handle cannot
            # be found (usually due to a bug in the XPS connectivity API),
            # assume PLB clock frequency is 0.
            set clock_freq 0
        } else {
            if {[string compare ${ipinst_handle} ${mhs_handle}] == 0} {
                # get from the clk_freq of the top-level clock port
                set clock_freq [::xget_hw_subproperty_value ${port_handle} "CLK_FREQ"]
            } else {
                set ipinst_name [::xget_hw_value ${ipinst_handle}]
                # if {[string compare ${ipinst_name} "clock_generator"] == 0} {
                    # if clock generator is found, get the clock frequency from the clock generator
                set port_name [::xget_hw_name ${port_handle}]
                set clock_freq_param_handle [::xget_hw_parameter_handle ${ipinst_handle} "C_${port_name}_FREQ"]
                set clock_freq [::xget_hw_value ${clock_freq_param_handle}]
                # }
            }
        }

        # set plb clock freq to 0 if failed to find it
        if {[string length ${clock_freq}] == 0} {
            set clock_freq 0
        }

        return ${clock_freq}
    }

    proc get_toplevel_portnames {port_handle_list} {
    # return a list of top-level port names

        lappend port_names

        foreach handle ${port_handle_list} {
            lappend port_names [::xget_hw_name ${handle}]
        }

        return ${port_names}
    }

    proc get_processor_info {mhs_handle} {
    # return types and numbers of processors (MicroBlaze and PowerPC)

        set microblaze_info [get_processor_info_of_type ${mhs_handle} microblaze]
        set ppc405_info [get_processor_info_of_type ${mhs_handle} ppc405]
        
        return [list ${microblaze_info} ${ppc405_info}]
    }

    proc get_processor_info_of_type {mhs_handle type} {

        set handles [xget_hw_ipinsts_handle_by_type ${mhs_handle} ${type}]
        lappend hw_vers
        foreach handle ${handles} {
            lappend hw_vers [::xget_hw_parameter_value ${handle} HW_VER]
        }
        # ignore any processor without HW_VER parameter
        set num_microblaze [llength ${hw_vers}]

        return [concat ${type} ${num_microblaze} ${hw_vers}]
    }

    proc del_toplevel_ports {mhs_handle port_info_list} {
    # delete toplevel ports

        set toplevel_port_handles [::xget_hw_port_handle ${mhs_handle} *]
        set toplevel_port_names [get_toplevel_portnames ${toplevel_port_handles}]
        set toplevel_port_names_lower [string tolower ${toplevel_port_names}]

        foreach port_info ${port_info_list} {
            set name "sg_[string tolower [lindex ${port_info} 0]]"
            set port_index [lsearch -exact ${toplevel_port_names_lower} ${name}]
            if {${port_index} > -1} {
                puts "deleting toplevel port: ${name} ..."
                ::xdel_hw_toplevel_port ${mhs_handle} [lindex ${toplevel_port_names} ${port_index}]
            }
        }
        
        return ""
    }

    proc add_toplevel_ports {mhs_handle port_info_list} {
    # add toplevel ports

        set toplevel_port_handles [::xget_hw_port_handle ${mhs_handle} *]
        set toplevel_port_names [get_toplevel_portnames ${toplevel_port_handles}]
        # for case-insensitive compare
        set toplevel_port_names [string tolower ${toplevel_port_names}]

        foreach port_info ${port_info_list} {
            # port information
            set name [string tolower [lindex ${port_info} 0]]
            set dir [lindex ${port_info} 1]
            set width [expr [lindex ${port_info} 2] - 1]
            set port_index [lsearch -exact ${toplevel_port_names} "sg_${name}"]
            if {${port_index} == -1} {
                puts "adding toplevel port: ${name}"
                set port_handle [::xadd_hw_ipinst_port ${mhs_handle} "sg_${name}" "sg_${name}"]
            } else {
                set port_handle [lindex ${toplevel_port_handles} ${port_index}]
            }
            if {[string length ${port_handle}] == 0} {
                return "unable to add port: ${name} ..."
            }
            ::xadd_hw_subproperty ${port_handle} "DIR" "${dir}"
            if {${width} > 0} {
                ::xadd_hw_subproperty ${port_handle} "VEC" "\[${width}:0\]"
            }
        }

        return ""
    }

    # No longer required since 13.1. XPS does not deal with MSS files any more since then.
    proc modify_mss {system_name bus_type} {
        puts "modifying MSS ..."
        set peri_name "sg_${bus_type}iface_0"
        puts "peri_name: ${peri_name}"

        set mss_bak_name [file_backup "${system_name}" "mss"]

        set mss_file_name "${system_name}.mss"
        set mss_bak_fd [open ${mss_bak_name}]
        set mss_fd [open "${mss_file_name}" "w+"]
        fconfigure ${mss_fd} -translation binary

        set inside_block 0
        set buffer ""
        set found 0
        while {![eof ${mss_bak_fd}]} {
            gets ${mss_bak_fd} line
            if {[regexp -nocase {^\s*begin\s*driver\s*$} ${line}]} {
                set inside_block 1
            } elseif {[regexp -nocase "^\\\s*parameter\\\s+hw_instance\\\s+=\\\s+${peri_name}\\\s*$" ${line}]} {
                set found 1
            }
            if {${inside_block} == 0} {
                puts ${mss_fd} "${line}"
            } else {
                if {[string length ${buffer}] == 0} {
                    set buffer "${line}"
                } else {
                    set buffer "${buffer}\n${line}"
                }
            }
            if {${inside_block} == 1 && [regexp -nocase {^\s*end\s*$} ${line}]} {
                set inside_block 0
                if {${found} != 1} {
                    puts ${mss_fd} "${buffer}"
                    set found 0
                }
                set buffer ""
            }
        }

        close ${mss_bak_fd}

        # put in the sg_plbiface driver
        puts ${mss_fd} "BEGIN DRIVER
 PARAMETER DRIVER_NAME = sg_${bus_type}iface
 PARAMETER DRIVER_VER = 1.00.a
 PARAMETER HW_INSTANCE = sg_${bus_type}iface_0
END"
        close ${mss_fd}

        return ""
    }

    proc puts_mlist {fd list} {
    # nicely puts for lists
        
        puts -nonewline ${fd} "ret{end+1} = {"

        foreach item ${list} {
            set item [regsub "'" ${item} "''"]
            puts -nonewline ${fd} "'${item}', "
        }
        puts ${fd} "};"
    }

    proc dump_data_to_m {dir mfunc_name data} {
    # dump the EDK project information to a MATLAB m function

        set fd [open [file join ${dir} "${mfunc_name}.m"] "w+"]

        puts ${fd} "function ret = ${mfunc_name}()"
        puts ${fd} ""
        puts ${fd} "ret = {};"
        
        foreach item ${data} {
            puts_mlist ${fd} ${item}
        }

        close ${fd}
    }

    proc get_edkprj_settings {mhs_handle} {
        lappend settings [::xget_hw_name ${mhs_handle}]
        lappend settings [::xget dev]
        lappend settings [::xget package]
        lappend settings [::xget speedgrade]
        lappend settings [::xget ucf_file]

        return ${settings}
    }

    proc get_mhs_handle {xmp_file err_msg} {
        upvar ${err_msg} _err_msg 

        set _err_msg ""

        # exist immediately on loading error
        if {[xload xmp ${xmp_file}] != 0} { 
            set _err_msg "encountered error loading xmp file: ${xmp_file}."
            return -1
        }
        
        set mhs_handle [::xget_handle mhs]
        if {[string length ${mhs_handle}] == 0 || ${mhs_handle} == 0} { 
            set _err_msg "unable to obtain mhs_handle: ${mhs_handle}."
            return -1
        }

        return ${mhs_handle}
    }

    proc file_backup {system_name ext} {
        # back up MHS/MSS file after successful loading and before modification
        set file_name "${system_name}.${ext}"

        set bak_name "${file_name}.bak"
        set bak_count 1
        while {[file exists ${bak_name}] == 1} {
            set bak_name "${file_name}.bak${bak_count}"
            incr bak_count
        }
        puts "backing up ${file_name} to ${bak_name} ..."
        file copy -force "${file_name}" "${bak_name}"

        return ${bak_name}
    }

    proc get_procbusname {bus} {
        # return the name of the data bus on processor to be connected to SysGen
        switch [string tolower ${bus}] {
            "axi" { set bus_name "M_AXI_DP" }
            default { set bus_name "DPLB" }
        }
        return ${bus_name}
    }

    proc get_portinfo {bus} {
        # TODO: need to move to Java or C++ so that we can grab the portinfo
        # from a single source
        switch [string tolower ${bus}] {
            "axi" {
                set port_info { {AXI_ACLK O 0}
                                {AXI_ARESETN O 0}
                                {AWADDR   O 32}
                                {AWID     O 8}
                                {AWLEN    O 8}
                                {AWSIZE   O 3}
                                {AWBURST  O 2}
                                {AWLOCK   O 2}
                                {AWCACHE  O 4}
                                {AWPROT   O 3}
                                {AWVALID  O 0}
                                {WLAST    O 0}
                                {WDATA    O 32}
                                {WSTRB    O 4}
                                {WVALID   O 0}
                                {BREADY   O 0}
                                {ARADDR   O 32}
                                {ARID     O 8}
                                {ARLEN    O 8}
                                {ARSIZE   O 3}
                                {ARBURST  O 2}
                                {ARLOCK   O 2}
                                {ARCACHE  O 4}
                                {ARPROT   O 3}
                                {ARVALID  O 0}
                                {RREADY   O 0}
                                {AWREADY  I 0}
                                {WREADY   I 0}
                                {BRESP    I 2}
                                {BID      I 8}
                                {BVALID   I 0}
                                {ARREADY  I 0}
                                {RLAST    I 0}
                                {RID      I 8}
                                {RDATA    I 32}
                                {RRESP    I 2}
                                {RVALID   I 0} }
            }
            "plb" {
                set port_info { {SPLB_Clk O 0}
                                {SPLB_Rst O 0}
                                {PLB_ABus O 32}
                                {PLB_PAValid O 0}
                                {PLB_RNW O 0}
                                {PLB_wrDBus O 32}
                                {Sl_addrAck I 0}
                                {Sl_wait I 0}
                                {Sl_wrComp I 0}
                                {Sl_wrDAck I 0}
                                {Sl_rdComp I 0}
                                {Sl_rdDAck I 0}
                                {Sl_rdDBus I 32}}
            }
        }
        return ${port_info}
    }

    # search all the processor instances in the imported XPS project
    # do DRC checking that exact one MicroBlaze processor is supported
    # Return: a list of information about the MicroBlaze processor
    #         1. type of the processor (MicroBlaze or PowerPC 405)
    #         2. XPS handle of the processor
    #         3. interface connected to SysGen DUT
    #         4. bus interface (plb or axi, always converted to lower cases)
    proc collect_processor_info {mhs_handle} {
        puts "collecting processor information ..."

        # list of MicroBlaze instances in the imported XPS project
        set microblaze_inst_list [xget_hw_ipinsts_handle_by_type ${mhs_handle} "microblaze"]
        # list of PowerPC406 instances in the imported XPS project
        set ppc_inst_list [xget_hw_ipinsts_handle_by_type ${mhs_handle} "ppc405"]
        # number of MicroBlaze instances
        set num_microblaze_inst [llength ${microblaze_inst_list}]
        # number of Power405 instances
        set num_ppc_inst [llength ${ppc_inst_list}]
        # total number of processor instances
        set num_processor_inst [expr ${num_microblaze_inst} + ${num_ppc_inst}]

        # exact one MicroBlaze instance is supported
        if {${num_processor_inst} > 1} {
            return "more than one processor is found. Only one processor is supported."
        } elseif {${num_processor_inst} == 0} {
            return "no processor is found. One processor should be present."
        } elseif {${num_ppc_inst} == 1} {
            return "PowerPC 405 is not supported. Only MicroBlaze is supported"
        }

        # processor type (only MicroBlaze is supported)
        set processor_type "MicroBlaze"
        # XPS handle of the processor
        set processor_inst [lindex ${microblaze_inst_list} 0]

        # automatically determine the SysGen memory map bus type
        set proc_interconnect [::xget_hw_parameter_value ${processor_inst} "C_INTERCONNECT"]
        # for legacy MicroBlaze, C_INTERCONNECT is empty (not defined). Set to big-end PLB
        if {[string length ${proc_interconnect}] == 0} {
            set proc_interconnect 1
        }
        puts "proc_interconnect: ${proc_interconnect} (1 for big-end PLB; 2 for little-end AXI)\n"

        if {${proc_interconnect} == 1} {
            set bus_type "plb"
        } else {
            set bus_type "axi"
        }

        # interface connected to SysGen DUT
        set plb_interface [get_procbusname ${bus_type}]

        return [list ${processor_type} ${processor_inst} ${plb_interface} ${bus_type}]
    }

    proc save_proj_files {} {
        puts "saving MHS file ..."
        if {[save mhs] != 0} { exit 1 }
        # puts "saving MSS file ..."
        # if {[save mss] != 0} { exit 1 }

        # get mhs_handle again as the original handle is destroyed after saving
        set mhs_handle [::xget_handle mhs]
        return ${mhs_handle}
    }

    proc handle_diff_clocks {mhs_handle} {
        set toplevel_port_handle_list [::xget_hw_port_handle ${mhs_handle} *]
        set diff_clocks [list]
        
        variable diff_clock_names
        set diff_clock_names ""

        # iterate through the toplevel ports to identify LVDS clocks
        foreach handle ${toplevel_port_handle_list} {
            set name [::xget_hw_name ${handle}]
            set conn [::xget_hw_value ${handle}]
            set diff_polar [::xget_hw_subproperty_value ${handle} "DIFFERENTIAL_POLARITY"]
            set sigis [::xget_hw_subproperty_value ${handle} "SIGIS"]
            set dir [::xget_hw_subproperty_value ${handle} "DIR"]
            set vec [::xget_hw_subproperty_value ${handle} "VEC"]
            set clk_freq [::xget_hw_subproperty_value ${handle} "CLK_FREQ"]

            # delete the LVDS clock first, we will add back all the P ports later
            if {[string length ${diff_polar}] > 0 && 
                [string match -nocase "CLK" ${sigis}] &&
                [string match -nocase "i*" ${dir}] && 
                [string length ${vec}] == 0} \
            {
                if {[string match -nocase "p*" ${diff_polar}]} {
                    lappend diff_clocks [list ${name} ${conn} ${clk_freq}]
                } elseif {[string match -nocase "n*" ${diff_polar}]} {
                    lappend diff_clock_names ${name}
                }
                puts "deleting differential clock: ${name} (${diff_polar}, ${conn}) ..."
                ::xdel_hw_toplevel_port ${mhs_handle} ${name}
            }
        }
        foreach clk ${diff_clocks} {
            set name [lindex ${clk} 0]
            set conn [lindex ${clk} 1]
            puts "adding clk: ${name} ..."
            set port_handle [::xadd_hw_ipinst_port ${mhs_handle} \
                             ${name} ${conn}]
            ::xadd_hw_subproperty ${port_handle} "DIR" "IN"
            ::xadd_hw_subproperty ${port_handle} "SIGIS" "CLK"
            set clk_freq [lindex ${clk} 2]
            if {[string length ${clk_freq}] > 0} {
                ::xadd_hw_subproperty ${port_handle} "CLK_FREQ" [lindex ${clk} 2]
            }
        }
    }

    proc xps_import {xmp_file baseaddr highaddr baseaddr_lock} {
        set err_msg ""

        variable codebug

        puts "xps_import settings: xmp_file(${xmp_file}), codebug(${codebug})"

        # the processor memory map is empty if baseaddr and highaddr are equal
        # in this case, there is no need to insert dummy sg iface Pcore and expose bus pins
        set is_memmap_empty [string match -nocase ${baseaddr} ${highaddr}]

        # load the XPS project
        set mhs_handle [get_mhs_handle ${xmp_file} err_msg]
        if {[string length ${err_msg}] > 0} {
            return ${err_msg}
        }
        
        # identify system name
        set system_name [::xget_hw_name ${mhs_handle}]
        puts "system name: ${system_name}\n"

        # DRC on the top-level clock ports
        # only one clock port is supported
        set clock_port_names [find_toplevel_clock_ports ${mhs_handle}]
        set num_clock_ports [llength ${clock_port_names}]
        puts "number of top-level input clock ports: ${num_clock_ports}\n"
        if {${num_clock_ports} > 1} {
            set err_msg "Only one input clock port is supported. Remove SIGIS = CLK for clocks not driven by System Generator"
            return ${err_msg}
        }
       
        # collect processor information
        # the bus type of SysGen memory map is determined automatically by the processor settings
        if ${is_memmap_empty} {
            puts "skip processor check with empty memory map"
            set plb_clk_freq 0
        } else {
            set proc_info [collect_processor_info ${mhs_handle}]

            set processor_type [lindex ${proc_info} 0]
            set processor_inst [lindex ${proc_info} 1]
            set plb_interface  [lindex ${proc_info} 2]
            set bus_type       [lindex ${proc_info} 3]
            
            set proc_inst_name [::xget_hw_parameter_value ${processor_inst} "INSTANCE"]
            puts "processor_type: ${processor_type}"
            puts "proc_inst_name: ${proc_inst_name}"
            puts "bus_interface: ${plb_interface}\n"
            puts "===> bus_type: [string toupper ${bus_type}]\n"

            set dplb_handle [::xget_hw_busif_handle ${processor_inst} ${plb_interface}]

            if {[string length ${dplb_handle}] == 0} {
                return "The ${plb_interface} on ${processor_type} is unconnected."
            } 
            
            set dplb_name [::xget_hw_value ${dplb_handle}]

            set plb_inst_name "${dplb_name}"
            puts "plb_inst_name: ${plb_inst_name}"
            set plb_inst_handle [::xget_hw_ipinst_handle ${mhs_handle} ${plb_inst_name}]

            if {[string match ${bus_type} "plb"]} {
                # only works for PLB as the PLB bus exists as a IP instance
                # won't work for AXI as AXI is for p2p commuincation. The AXI bus does not exist as an IP
                # in case of AXI, need to search for the interconnect IP if existed
                # TODO: what if the interconnect IP is not there?
                set plb_clk_freq [xget_hw_plb_clock_freq ${mhs_handle} ${plb_inst_handle}]
                # aclk are not required for PLB bus
                set interconnect_aclk_name ""
            } else {
                # MicroBlaze CLK
                set interconnect_aclk_handle [::xget_hw_port_handle ${processor_inst} "CLK"]
                if {[string length ${interconnect_aclk_handle}] == 0} {
                    # AXI_ACLK
                    set interconnect_aclk_handle [::xget_hw_port_handle ${plb_inst_handle} "INTERCONNECT_ACLK"]
                }
                if {[string length ${interconnect_aclk_handle}] == 0} {
                    return "unable to find CLK port from MicroBlaze and INTERCONNECT_ACLK port from its data AXI interface"
                }

                set interconnect_aclk_name [::xget_hw_value ${interconnect_aclk_handle}]
                puts "memory map ACLK: ${interconnect_aclk_name}"

                set plb_clk_freq 0
            }
        }

        file_backup "${system_name}" "mhs"

        puts "setting the XPS project as a submodule ..."
        xset hier sub

        # interface port information
        set port_info_list [get_portinfo ${bus_type}]
        set plbiface_name "sg_${bus_type}iface_0"

        if ${is_memmap_empty} {
            puts "deletings import related toplevel ports ..."
            del_toplevel_ports ${mhs_handle} ${port_info_list}
        } else {
            puts "adding import related toplevel ports ..."
            add_toplevel_ports ${mhs_handle} ${port_info_list}
        }

        # co-debugging ports
        set debug_port_info_list {{CoDebug_Halted O 0}
                                  {CoDebug_Stop I 0}
                                 }
        if [string equal ${codebug} "off"] {
            puts "deletings codebug related toplevel ports ..."
            del_toplevel_ports ${mhs_handle} ${debug_port_info_list}
        } else {
            puts "adding codebug related toplevel ports ..."
            add_toplevel_ports ${mhs_handle} ${debug_port_info_list}
        }

        set inst_handle_list [xget_hw_ipinsts_handle_by_type ${mhs_handle} "sg_${bus_type}iface"]
        set plbiface_handle ""
        foreach handle ${inst_handle_list} {
            set inst_name [::xget_hw_name ${handle}]
            if {[string compare ${inst_name} ${plbiface_name}] == 0} {
                set plbiface_handle ${handle}
                continue
            } 
            puts "deleting instance: ${inst_name} ..."
            ::xdel_hw_ipinst ${mhs_handle} ${inst_name}
        }
        if {! ${is_memmap_empty}} {
            if {[string length ${plbiface_handle}] == 0} {
                puts "adding instance: ${plbiface_name} ..."
                set plbiface_handle [::xadd_hw_ipinst ${mhs_handle} ${plbiface_name} "sg_${bus_type}iface" "1.00.a"]
                if {[string length ${plbiface_handle}] == 0} {
                    return "unable to add sg_${bus_type}iface"
                }
            } else {
                ::xadd_hw_ipinst_parameter ${plbiface_handle} "HW_VER" "1.00.a"
            }

            if [string match ${bus_type} "plb"] {
                set bus_name "SPLB"
            } else {
                set bus_name "S_[string toupper ${bus_type}]"
            }

            ::xadd_hw_ipinst_busif ${plbiface_handle} ${bus_name} ${plb_inst_name}
            ::xadd_hw_ipinst_parameter ${plbiface_handle} "C_BASEADDR" ${baseaddr}
            ::xadd_hw_ipinst_parameter ${plbiface_handle} "C_HIGHADDR" ${highaddr}

            foreach port_info ${port_info_list} {
                set port_name [lindex ${port_info} 0]
                ::xadd_hw_ipinst_port ${plbiface_handle} "sg${port_name}" [string tolower "sg_${port_name}"]
            }

            # co-debug ports on processort
            if {[string equal ${codebug} "on"]} {
                puts "adding co-debugging ports ..."
                ::xadd_hw_ipinst_port ${processor_inst} "Dbg_Stop" "sg_codebug_stop"
                ::xadd_hw_ipinst_port ${processor_inst} "Mb_Halted" "sg_codebug_halted"
            } else {
                puts "deleting co-debugging ports ..."
                ::xdel_hw_ipinst_port ${processor_inst} "Dbg_Stop"
                ::xdel_hw_ipinst_port ${processor_inst} "Mb_Halted"
            }
            if {[string length ${interconnect_aclk_name}] > 0} {
                # wire up the AXI interconnect clock
                ::xadd_hw_ipinst_port ${plbiface_handle} "AXI_ACLK" ${interconnect_aclk_name}
            }
            if [string match ${bus_type} "axi"] {
                # specify AXI masters
                ::xadd_hw_ipinst_parameter ${plbiface_handle} "C_INTERCONNECT_S_AXI_MASTERS" "${proc_inst_name}.${plb_interface}"

            }
        }
        
        set mhs_handle [save_proj_files]

        handle_diff_clocks ${mhs_handle}

        set mhs_handle [save_proj_files]

        # if {! ${is_memmap_empty}} {
        #     modify_mss ${system_name} ${bus_type}
        # }

        # # exist immediately on loading error
        # puts "synching up mss file after modification: ${system_name}.mss ..."
        # if {[string length [xload mss "${system_name}.mss"]] != 0} {
        #     return "error synching up mss file: ${system_name}.mss"
        # }

        puts "getting a new mhs handle after reloading ..."
        set mhs_handle [::xget_handle mhs]
        if {[string length ${mhs_handle}] == 0 || ${mhs_handle} == 0} { 
            return "unable to obtain mhs_handle: ${mhs_handle}"
        }

        if ${is_memmap_empty} {
            puts "skip running addrgen on empty memory map" 
        } elseif {[string compare ${baseaddr_lock} "off"] == 0} {
            # run addrgen
            run_addrgen ${mhs_handle} ${bus_type}

            run resync

            set mhs_handle [::xget_handle mhs]
            set baseaddr [get_baseaddr ${mhs_handle} ${bus_type}]
        }

        # return "" upon success
        return [dump_projinfo_to_m ${mhs_handle} ${plb_clk_freq} ${baseaddr} ${baseaddr_lock} ${proc_inst_name} ${bus_type}]
    }

    proc get_value {data_list data_index args} {
        set value ""
        # assign default value
        if {[llength ${args}] > 0} {
            set value [lindex ${args} 0]
        }
        if {[llength ${data_list}] > ${data_index}} {
            set value [lindex ${data_list} ${data_index}]
        }
        return ${value}
    }

    proc check_toplevel_port_attr {handle attr_name expected_value} {
        # convert values to lower cases for case insensitive matching
        set attr_value [string tolower [::xget_hw_subproperty_value ${handle} ${attr_name}]]
        set expected_value [string tolower ${expected_value}]
        
        return [string match "${expected_value}" ${attr_value}]
    }

    proc find_toplevel_clock_ports {mhs_handle} {
    # count the number of input clock pins of the XPS project
    # LVDS clock pairs are considered as one input clock

        set clock_port_names [list]
        set num_clock_ports 0

        set toplevel_port_handle_list [::xget_hw_port_handle ${mhs_handle} *]
        foreach handle ${toplevel_port_handle_list} {
            # input port, clock port, one-bit vector ==> input clock port
            if {[check_toplevel_port_attr ${handle} "DIR" "I*"] &&
                [check_toplevel_port_attr ${handle} "SIGIS" "CLK"] &&
                [check_toplevel_port_attr ${handle} "VEC" ""]} {
                set port_name [::xget_hw_name ${handle}]
                # for LVDS clock, only count P but not N
                set diffpol [string tolower [::xget_hw_subproperty_value ${handle} "DIFFERENTIAL_POLARITY"]]
                if {[string length ${diffpol}] == 0 || [string match ${diffpol} "p"]} {
                    lappend clock_port_names ${port_name}
                    incr num_clock_ports
                    puts "====> top-level input clock port (${num_clock_ports}) : ${port_name}"
                }
            }
        }

        return ${clock_port_names}
    }

    proc get_toplevel_port_alias {sigis handle} {
        set alias ""
        
        set sigis [string tolower ${sigis}]

        # alias is always empty for non-clock ports
        if {[string match ${sigis} "clk"]}  {
            set alias [::xget_hw_value ${handle}]
            puts "internal_net_name: ${alias}"
            # variable diff_clock_names
            # puts "diff_clock_names: ${diff_clock_names}"
        }

        return ${alias}
    }

    # dump information of the XPS project into a M code file so that it can picked up by SysGen later
    proc dump_projinfo_to_m {mhs_handle args} {
        set plb_clk_freq   [get_value ${args} 0]
        set baseaddr       [get_value ${args} 1]
        set baseaddr_lock  [get_value ${args} 2]
        set proc_inst_name [get_value ${args} 3]
        set bus_type       [get_value ${args} 4]

        puts "Dumping project info into m code ..."
        # 1: system name
        # 2, 3, 4: device settings
        # 5: ucf
        set item_data [get_edkprj_settings ${mhs_handle}]
        # 6: clock frequency
        lappend item_data ${plb_clk_freq}
        # 7: processor instance name
        lappend item_data ${proc_inst_name}
        # 8: bus type
        lappend item_data ${bus_type}
        # 9: base address (opttional)
        if {[string compare ${baseaddr_lock} "off"] == 0} {
            lappend item_data ${baseaddr}
        }
        lappend data ${item_data}

        puts "Dumping top-level pin info into M code ..."
        set toplevel_port_handle_list [::xget_hw_port_handle ${mhs_handle} *]
        foreach handle ${toplevel_port_handle_list} {
            set port_name [::xget_hw_name ${handle}]
            set port_info ${port_name}
            # dir
            lappend port_info [::xget_hw_subproperty_value ${handle} "DIR"]
            # vec (none means std_vector)
            lappend port_info [::xget_hw_subproperty_value ${handle} "VEC"]
            # sigis (CLK, RST, INTERRUPT, or empty)
            set sigis [::xget_hw_subproperty_value ${handle} "SIGIS"]
            lappend port_info ${sigis}
            # clk_freq
            lappend port_info [::xget_hw_subproperty_value ${handle} "CLK_FREQ"]
            # whether to insert IO pads (e.g., MPMC instantiates IO pads itself)
            lappend port_info [check_port_iopad ${mhs_handle} ${port_name}]
            # alias (used for LVDS N pin or internal net name)
            set alias [get_toplevel_port_alias ${sigis} ${handle}]
            lappend port_info ${alias}

            lappend data ${port_info}
            puts "====> port_info: ${port_info}"
        }

        variable model_dir
        dump_data_to_m ${model_dir} "edk_info" ${data}

        run netlistclean
        
        # return "" upon success
        return ""
    }

    proc check_port_iopad {mhs_handle port_name} {
        variable ipinsts_noiopad

        set conn_list [::xget_hw_port_connectors_list ${mhs_handle} ${port_name}]

        foreach conn ${conn_list} {
            set ports_handle [::xget_hw_connected_ports_handle ${mhs_handle} ${conn} "all"]
            foreach handle ${ports_handle} {
                set ipinst_handle [::xget_hw_parent_handle ${handle}]
                if {[string length ${ipinst_handle}] != 0 && ${ipinst_handle} != ${mhs_handle}} {
                    set ipinst_name [string tolower [::xget_hw_value ${ipinst_handle}]]
                    if {[lsearch -exact ${ipinsts_noiopad} ${ipinst_name}] != -1} {
                        return "none";
                    }
                }
            }
        }

        return ""
    }

    proc run_addrgen {mhs_handle bus_type} {
        puts "running addrgen ..."

        set system_name [::xget_hw_name ${mhs_handle}]

        # path to addrgen executable
        set edk_dir $::env(XILINX_EDK)
        set addrgen "addrgen"
        
        set fd [open "cc.gen" "w+"]
        puts $fd "inputMhs= ${system_name}.mhs
outputMhs= ${system_name}.mhs
AddrUnFixInsName=sg_${bus_type}iface_0
AddrUnFixParamName=C_BASEADDR
"
        close ${fd}
        catch {exec ${addrgen} -opt cc.gen}
    }

    proc get_baseaddr {mhs_handle bus_type} {
        set sg_ip_inst_name "sg_${bus_type}iface_0"
        puts "getting baseaddr of ${sg_ip_inst_name} ..."

        set handle [::xget_hw_ipinst_handle ${mhs_handle} ${sg_ip_inst_name}]
        if {[string length ${handle}] == 0} { 
            puts "unable to get handle for ipinst ${sg_ip_inst_name}"
            exit 1 
        }

        set baseaddr [::xget_hw_parameter_value ${handle} "C_BASEADDR"]
        if {[string length ${baseaddr}] == 0} { 
            puts "unable to get C_BASEADDR"
            exit 1 
        }

        return ${baseaddr}
    }

    proc clean_software {xmp_file} {
        puts "getting mhs_handle ..."
        set mhs_handle [get_mhs_handle ${xmp_file} err_msg]
        if {[string length ${err_msg}] > 0} {
            return ${err_msg}
        }
        puts "clean software ..."
        run swclean

        return ""
    }

    proc set_address {xmp_file baseaddr highaddr} {
        set err_msg ""

        puts "getting mhs_handle ..."
        set mhs_handle [get_mhs_handle ${xmp_file} err_msg]
        if {[string length ${err_msg}] > 0} {
            return ${err_msg}
        }

        set handle [::xget_hw_ipinst_handle ${mhs_handle} "sg_plbiface_0"]
        if {[string length ${handle}] == 0} { 
            puts "unable to get handle for ipinst sg_plbiface_0"
            exit 1 
        }

        puts "setting sg_plbiface_0 BASEADDR to ${baseaddr}"
        ::xadd_hw_ipinst_parameter ${handle} "C_BASEADDR" ${baseaddr}

        puts "setting sg_plbiface_0 HIGHADDR to ${highaddr}"
        ::xadd_hw_ipinst_parameter ${handle} "C_HIGHADDR" ${highaddr}

        save mhs

        return ""
    }

    proc exporttosdk { target_dir} {
        set sdk_export_dir [file join ${target_dir} "SDK_Export"]
        
        xset sdk_export_bmm_bit 0
        xset sdk_export_dir ${sdk_export_dir}
        run exporttosdk

        set pcores [file join ${sysgen::projpath} "pcores"]
        set sysgen_repos [file join ${sdk_export_dir} "sysgen_repos"]
        set target_pcores [file join ${sysgen_repos} "drivers"]
        puts "copying \"${pcores}\" directory  to '${target_pcores}' ..."
        # remove the sysgen repository directory before copying
        if [file exists ${sysgen_repos}] {
            puts "removing existing SysGen repository: ${sysgen_repos}"
            if {[catch {file delete -force ${sysgen_repos} } msg]} {
                puts ${msg}
                puts "encountered error when deleting SysGen repository: $::errorInfo"
                exit 1
            }
        }
        if {[catch {file mkdir ${sysgen_repos}} msg]} {
            puts ${msg}
            puts "encountered error when creating ${sysgen_repos}: $::errorInfo"
            exit 1
        }
        if {[catch {file copy -force ${pcores} ${target_pcores}} msg]} {
            puts ${msg}
            puts "encountered error when copying software driver: $::errorInfo"
            exit 1
        }
    }
    
    proc xps_netlist {} {
        cd ${sysgen::projpath}
        xload xmp "${sysgen::projname}.xmp"
        xset hdl ${sysgen::synthesis_language}

        # netlist as submodule, so that I/O pads are not inserted in NGC files
        xset hier sub
        # TODO: outdated xset option, no longer required
        # xset topinst %top%

        set device_settings "[xget dev][xget package][xget speedgrade]"
        set sysgen_device_settings "${sysgen::dev}${sysgen::package}${sysgen::speedgrade}"
        if {[string compare ${device_settings} ${sysgen_device_settings}] != 0} {
            # clean up hardware design files upon change of device
            run hwclean
            # set to the same device as in SysGen token
            xset arch ${sysgen::arch}
            xset dev ${sysgen:dev}
            xset package ${sysgen::package}
            xset speedgrade ${sysgen::speedgrade}
        }

        # save XMP settings
        save xmp
        # run XPS netlist
        run netlist

        # export the result to netlist/SDK_Export
        # this is required for all flows including HDL netlist and HW co-sim, etc.
        exporttosdk ${sysgen::target_dir}
    }

    proc compile_and_update_bitstream { xmp_file bit bmm elf_file } {

        set xmp_dir [file dirname ${xmp_file}]
        set xmp [file tail ${xmp_file}]

        puts "change into directory: ${xmp_dir} ..."
        cd "${xmp_dir}"

        set executable ""
        set err_msg ""

        puts "opening XMP file: ${xmp} ..."
        set mhs_handle [get_mhs_handle ${xmp} err_msg]
        if {[string length ${err_msg}] > 0} {
            return ${err_msg}
        }

        if {[string length ${elf_file}] > 0 && [file isfile ${elf_file}]} {
            puts "using ELF file: ${elf_file} ..."
            set executable ${elf_file}

            set proc_info [collect_processor_info ${mhs_handle}]

            set proc_inst_handle [lindex ${proc_info} 1]

            set procinst [::xget_hw_parameter_value ${proc_inst_handle} "INSTANCE"]
        } else {
            puts "checking status of software programs ..."
            set apps [xget swapps] 

            foreach app $apps { 
                set init_bram [xget_swapp_prop_value $app "init_bram"]
                set active [xget_swapp_prop_value $app "active"]
                if {$init_bram && $active} {
                    set procinst [xget_swapp_prop_value $app "procinst"]
                    set executable [xget_swapp_prop_value $app "executable"]
                    break
                }
            }

            if {[string length ${executable}] == 0} {
                puts "unable to find an application group to compile."
                exit 1
            }

            puts "compiling software programs ..."
            if {[run program] != 0} {
                puts "encountered error when compiling software programs"
                exit 1
            }
        }

        puts "updating bitstream ..."

        set system [file rootname ${xmp}]
        set mhs "[::xget_hw_name ${mhs_handle}].mhs"
        puts "MHS file: ${mhs}"

        set tempbitname "[file rootname ${bit}]_[clock seconds].bit"
        set tempbit [file join [file dirname ${bit}] ${tempbitname}]

        set part_name "[xget dev][xget speedgrade][xget package]"

        puts "bitinit -p ${part_name} ${mhs} -pe ${procinst} ${executable} -bt ${bit} -bm ${bmm} -o ${tempbit}"
        if {[catch {exec bitinit -p ${part_name} ${mhs} -pe ${procinst} ${executable} -bt ${bit} -bm ${bmm} -o ${tempbit}} msg]} {
            puts ${msg}
            puts "encountered error when updating bitstream: $::errorInfo"
            exit 1
        }

        puts ${msg}

        puts "overwriting ${bit} ..."
        file rename -force -- ${tempbit} ${bit}

        puts "compiled and updated bitstream successfully."
    }

    
} ;# namespace sysgen

