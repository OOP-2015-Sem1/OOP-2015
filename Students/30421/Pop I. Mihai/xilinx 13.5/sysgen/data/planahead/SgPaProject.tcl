set exename [info nameofexecutable]
set currenttclshell [file tail [ file rootname $exename ] ]
if { ! [string match "planAhead" $currenttclshell] } {
    error "ERROR: Please run planAhead tcl."
    return
}
set enable_hwcosim_pa_flow 0

namespace eval ::xilinx::dsptool::planaheadproject {

    namespace eval planahead {}
    namespace export \
	VERBOSITY_QUIET VERBOSITY_ERROR VERBOSITY_WARNING \
	VERBOSITY_INFORMATION VERBOSITY_DEBUG

    set VERBOSITY_QUIET       0
    set VERBOSITY_ERROR       1
    set VERBOSITY_WARNING     2
    set VERBOSITY_INFORMATION 3
    set VERBOSITY_DEBUG       4

    set IS_DOING_SMOKETEST false
    if { [info exists ::xilinx::dsptool::planaheadprojecttest::is_doing_planAheadGenTest] } {
	set IS_DOING_SMOKETEST true
    } elseif { [info exists ::xilinx::dsptool::planaheadprojecttest::is_doing_planAheadGenPostSynthTest] } {
	set IS_DOING_SMOKETEST true
    }

    #-------------------------------------------------------------------------
    # Checks for a required parameter.
    #
    # @param  param          Parameter name.
    # @param  postproc       Post processor.
    # @return the parameter value.
    #-------------------------------------------------------------------------
    proc required_parameter {param {postproc ""}} {
	upvar $param p
	if {![info exists p]} {
	    error "ERROR: Required parameter \"[namespace tail $param]\" is not specified."
	}
	if {$postproc != ""} {
	    eval $postproc p
	}
	return $p
    }

    #-------------------------------------------------------------------------
    # Checks for an optional parameter.
    #
    # @param  param          Parameter name.
    # @param  defval         Default value of the parameter if unspecified.
    # @param  postproc       Post processor.
    # @return the parameter value.
    #-------------------------------------------------------------------------
    proc optional_parameter {param {defval ""} {postproc ""}} {
	upvar $param p
	if {![info exists p]} {
	    set p $defval
	}
	if {$postproc != ""} {
	    eval $postproc p
	}
	return $p
    }

    #-------------------------------------------------------------------------
    # Deletes an existing empty parameter.
    #
    # @param  param          Parameter name.
    #-------------------------------------------------------------------------
    proc clear_empty_parameter {param} {
	upvar $param p
	if {[info exists p] && [expr { [string length $p] == 0 }]} {
	    unset p
	}
    }

    #-------------------------------------------------------------------------
    # Checks a Boolean flag.
    #
    # @param  param          Parameter name.
    # @param  defval         Default value of the parameter if unspecified.
    # @return 1 if the flag is specified and is true, or 0 othewise.
    #-------------------------------------------------------------------------
    proc check_flag {param {defval ""}} {
	upvar $param p
	return [expr { [info exists p] && $p }]
    }

    #-------------------------------------------------------------------------
    # read input file, print to stdout, for smoke test purpose
    #-------------------------------------------------------------------------
    proc dumpfile { targetfile } {
	if { [ file exists $targetfile ] == 0 } {
	    return
	}

	set fp [open $targetfile r]
	set data [read $fp]
	close $fp

	puts $data
    }

    #-------------------------------------------------------------------------
    # checks whether isim run through without problem, for xt smoket test
    #
    # @param  isimlog          isim log file name.
    # @return error string based on parsing isim log file
    #  1. find mismatch points isim and simulink
    #  2. find error message in log
    #  3. log file does not exist
    #-------------------------------------------------------------------------
    proc parse_isimlog {isimlog} {
	if { [ file exists $isimlog ] == 0 } {
	    return "$isimlog file not exists"
	}

	set fp [open $isimlog]
	set data [read $fp]
	#if { [catch {close $fp} err]} {
	#    return 0
	#}
	close $fp
	set lines [split $data \n]
	# set lastline [lindex $lines end-1]
	foreach st $lines {
	    if { [string match {* mismatch*} $st] } {
		return "find mismatch"
	    }
	}

	return ""
    }

    #-------------------------------------------------------------------------
    # create and run post-synth sim, for xt smoket test
    #-------------------------------------------------------------------------
    proc run_post_synth_sim {} {
	planahead::open_netlist_design -name netlist_1

	::set_property is_enabled false [planahead::get_files -of_objects sources_1]

	switch -- $::xilinx::dsptool::iseproject::param::SynthesisTool {
	    "RDS" {
		::set_param simulation.netlist.rodin 1 
	    }
	}

	#set srcfiles [planahead::get_files -of_objects sources_1]
	#foreach srcfile $srcfiles {
	    #set_property is_enabled false [get_files -of_objects sources_1 $srcfile]
	#}

	set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]
	set currentdir [ pwd ]

	set projDir [file normalize $::xilinx::dsptool::iseproject::param::ProjectDir ]
	[cd $projDir]

	if { $is_vhdl } {
	    planahead::write_vhdl -force $projDir/post_synth.vhd
	    planahead::add_files -fileset sim_1 -norecurse -scan_for_includes post_synth.vhd
	    planahead::import_files -fileset sim_1 -norecurse -force post_synth.vhd
	} else {
	    planahead::write_verilog -force -mode sim $projDir/post_synth.v
	    planahead::add_files -fileset sim_1 -norecurse -scan_for_includes post_synth.v
	    planahead::import_files -fileset sim_1 -norecurse -force post_synth.v
	}

	[cd $currentdir]

	#set postsynthvhd ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/hdl_netlist/post_synth.vhd
	#set postsynthvhd ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/net2/post_synth.vhd
	set postsynthvhd $projDir/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/hdl_netlist/post_synth.v
	if { $is_vhdl } {
	    set postsynthvhd $projDir/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/hdl_netlist/post_synth.vhd
	}
	planahead::reorder_files -fileset sim_1 -front $postsynthvhd

	::launch_isim -batch -simset sim_1 -mode behavioral
    }

    #-------------------------------------------------------------------------
    # clean up post-synth sim, for xt smoket test
    #-------------------------------------------------------------------------
    proc clean_up_post_synth_sim {} {
	set projDir [file normalize $::xilinx::dsptool::iseproject::param::ProjectDir ]

	#set postsynthvhd ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/hdl_netlist/post_synth.vhd
	#set postsynthvhd ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/net2/post_synth.vhd

	set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]
	set postsynthvhd $projDir/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/hdl_netlist/post_synth.v
	if { $is_vhdl } {
	    set postsynthvhd $projDir/${::xilinx::dsptool::iseproject::param::Project}.srcs/sim_1/imports/hdl_netlist/post_synth.vhd
	}

	planahead::remove_files -fileset sim_1 $postsynthvhd
	::set_property is_enabled true [planahead::get_files -of_objects sources_1]

	#set srcfiles [planahead::get_files -of_objects sources_1]
	#foreach srcfile $srcfiles {
	    #set_property is_enabled true [get_files -of_objects sources_1 $srcfile]
	#}
    }

    #-------------------------------------------------------------------------
    # add files with certain extenstion in certain directory to project
    #
    # @param  filedir          directory to look at
    # @param  fileext          file extension which should add
    #-------------------------------------------------------------------------
    proc add_file_to_project {filedir fileext} {
	set fileSources [ glob -nocomplain $filedir/*.$fileext ]
	set extfiles " "
	foreach p $fileSources {
	    set extfiles "$extfiles $p"
	}

	if [not_empty_or_white_string $extfiles] {
	    planahead::import_files -fileset [ get_filesets sources_1 ] -force -norecurse $extfiles
	}
    }

    #-------------------------------------------------------------------------
    # wait until isim is finished, for xt smoket test
    #
    # @param  isimlog          isim log file name.
    # @param  simtype          behavior/post-synth/timing
    # @param  deleteisimlog    0/1, 1 is to delete
    # @return msgtext          empty string means no error
    #-------------------------------------------------------------------------
    proc wait_on_isim {isimlog simtype deleteisimlog} {
	set ncount 0
	while { [ expr {[ isim_done $isimlog ] == 0} ] && $ncount < 360} {
	    [ exec sleep 10 ]
	    incr ncount
	}

	set isimerrmsg0 ""

	if { $ncount >= 360 } {
	    set stmpmsg0 "error happened when run $simtype simulation.\n"
	    set isimerrmsg0 [concat $isimerrmsg0 $stmpmsg0]
	} else {
	    set isimmsg [ parse_isimlog $isimlog ]
	    if { [ not_empty_or_white_string $isimmsg ] } {
		set stmpmsg0 "$isimmsg in $simtype simulation.\n"
		set isimerrmsg0 [concat $isimerrmsg0 $stmpmsg0]
	    }
	}

	if { $deleteisimlog == 1 && [ file exists $isimlog ] == 1 } {
	    #file delete $isimlog
	    file rename -force $isimlog ${isimlog}_${simtype}
	}

	if { [string length $isimerrmsg0] > 0 } {
	    ::close_project
	    error "ERROR: $isimerrmsg0"
	}

	return $isimerrmsg0;
    }

    #-------------------------------------------------------------------------
    # checks whether a previous isim has finished, for xt smoket test
    #
    # @param  isimlog          isim log file name.
    # @return 1 if there is a previous isim has finished before.
    #-------------------------------------------------------------------------
    proc isim_done {isimlog} {
	if { [ file exists $isimlog ] == 0 } {
	    return 0
	}

	set fp [open $isimlog]
	set data [read $fp]
	#if { [catch {close $fp} err]} {
	#    return 0
	#}
	close $fp
	set lines [split $data \n]

	set lastline [lindex $lines end-1]
	#puts "lastline >>$lastline<<"
	if { [string match -nocase {*#*quit*-f*} $lastline] } {
	    return 1
	} else {
	    set lastline [lindex $lines end]
	    #puts "lastline >>$lastline<<"
	    return [string match -nocase {*#*quit*-f*} $lastline]
	}

    }

    #-------------------------------------------------------------------------
    # Tests if the current verbosity level is equal to or
    # greater than the target verbosity level.
    #
    # @param  level          Target verbosity level.
    # @return True if the current verbosity level is equal to or
    #         greater than the target verbosity level.
    #-------------------------------------------------------------------------
    proc meet_verbosity {level} {
	set curr_level [subst $[namespace current]::$level]
	return [expr { $::xilinx::dsptool::iseproject::param::_VERBOSITY >= $curr_level }]
    }

    #-------------------------------------------------------------------------
    # Post processor to turn the given parameter to lower case.
    #
    # @param  param          Parameter name.
    # @return the processed parameter value.
    #-------------------------------------------------------------------------
    proc lowercase_pp {param} {
	upvar $param p
	set p [string tolower $p]
	return $p
    }

    #-------------------------------------------------------------------------
    # Post processor for the SynthesisTool parameter.
    #
    # @param  param          Parameter name.
    # @return the processed parameter value.
    #-------------------------------------------------------------------------
    proc synthesis_tool_pp {param} {
	upvar $param p
	switch [string tolower $p] {
	    "xst" {
		set p "XST"
	    }
	    "rds" {
		set p "RDS"
	    }
	    "synplify" {
		set p "Synplify"
	    }
	    "synplify pro" {
		set p "Synplify Pro"
	    }
	    default {
		error "ERROR: Invalid value for parameter \"SynthesisTool\": $p"
	    }
	}
    }

    #-------------------------------------------------------------------------
    # Post processor for the HDLLanguage parameter.
    #
    # @param  param          Parameter name.
    # @return the processed parameter value.
    #-------------------------------------------------------------------------
    proc hdl_language_pp {param} {
	upvar $param p
	switch [string tolower $p] {
	    "vhdl" {
		set p "VHDL"
	    }
	    "verilog" {
		set p "Verilog"
	    }
	    default {
		error "ERROR: Invalid value for parameter \"HDLLanguage\": $p"
	    }
	}
    }

    #-------------------------------------------------------------------------
    # Dumps all variables of a given namespace. The current namespace is used
    # if no namespace is specified.
    #
    # @param  ns             Target namespace.
    #-------------------------------------------------------------------------
    proc dump_variables {{ns ""}} {
	if {$ns eq ""} {
	    set ns [namespace current]
	}
	foreach param [lsort [info vars $ns\::*]] {
	    upvar $param p
	    # TODO : print array, remove upvar
	    puts [namespace tail $param]\ =\ $p
	}
    }

    #-------------------------------------------------------------------------
    # Obtains a new unique command name for the given command.
    #
    # @param  cmd            Fully qualified command name.
    # @return fully qualified name of the new command.
    #-------------------------------------------------------------------------
    proc unique_command_name {cmd} {
	upvar _unique_command_id_ id
	if {![info exists id]} {
	    set id 0
	}

	set ns [namespace qualifiers $cmd]
	set old_name [namespace tail $cmd]
	set new_name "$old_name\_$id\_"
	set eval_ns [expr { $ns eq "" ? "::" : $ns }]
	while { [lsearch [namespace eval $eval_ns {info proc}] $new_name] >= 0 } {
	    incr id
	    set new_name "$old_name\_$id\_"
	}

	return "$ns\::$new_name"
    }

    #-------------------------------------------------------------------------
    # Decorates a command with the given decorator. Unless a new command name
    # is specified, the original command is renamed and then replaced by
    # the decorated command.
    #
    # @param  decorator      Fully qualified name of the decorator command.
    # @param  cmd            Fully qualified name of the command to be
    #                        decorated.
    # @param  new_cmd        Fully qualified name of the new command.
    #-------------------------------------------------------------------------
    proc decorate_command {decorator cmd {new_cmd ""}} {
	if {[expr {$new_cmd eq ""}] || [expr {$new_cmd eq $cmd}]} {
	    set new_cmd [unique_command_name $cmd]
	    set s "rename $cmd $new_cmd; \
		   proc $cmd {args} { \
		       return \[uplevel {$decorator} \[linsert \$args 0 {$cmd} {$new_cmd}\] \] \
		   };"
	} else {
	    set s "proc $new_cmd {args} { \
		       return \[uplevel {$decorator} \[linsert \$args 0 {$new_cmd} {$cmd}\] \] \
		   };"
	}
	eval $s
    }

    #-------------------------------------------------------------------------
    # Decorator that logs a given command without execution.
    #
    # @param  invoked_cmd    Invoked command.
    # @param  actual_cmd     Actual command.
    # @param  args           Additional argument list.
    #-------------------------------------------------------------------------
    proc log_command {invoked_cmd actual_cmd args} {
	if [meet_verbosity VERBOSITY_INFORMATION] {
	    set cmd "[namespace qualifiers $actual_cmd][namespace tail $actual_cmd]"
	    puts "$cmd $args"
	}
    }

    #-------------------------------------------------------------------------
    # Decorator that executes a given command.
    #
    # @param  invoked_cmd    Invoked command.
    # @param  actual_cmd     Actual command.
    # @param  args           Additional argument list.
    # @return the command result.
    #-------------------------------------------------------------------------
    proc run_command {invoked_cmd actual_cmd args} {
	set cmd "[namespace qualifiers $actual_cmd][namespace tail $actual_cmd]"
	if [meet_verbosity VERBOSITY_INFORMATION] {
	    puts "$cmd $args"
	}
	if [catch { uplevel $actual_cmd $args } result] {
	    error "ERROR: Failed to execute command \"$cmd $args\".\n$result"
	}
	return $result
    }

    #-------------------------------------------------------------------------
    # Decorates PlanAhead commands with appropriate decorators.
    #-------------------------------------------------------------------------
    proc decorate_planahead_commands {} {
	upvar _planahead_commands_already_decorated_ decorated
	if [check_flag decorated] {
	    return
	} else {
	    set decorated True
	}

	set planahead_cmd_list {
	   ::add_cells_to_pblock
	   ::add_files
	   ::add_reconfig_module
	   ::all_clocks
	   ::all_inputs
	   ::all_outputs
	   ::all_registers
	   ::close_design
	   ::close_project
	   ::compxlib
	   ::config_partition
	   ::config_run
	   ::config_timing_corners
	   ::config_timing_pessimism
	   ::connect_debug_port
	   ::create_clock
	   ::create_debug_core
	   ::create_debug_port
	   ::create_fileset
	   ::create_generated_clock
	   ::create_interface
	   ::create_ip
	   ::create_operating_conditions
	   ::create_pblock
	   ::create_port
	   ::create_project
	   ::create_property
	   ::create_run
	   ::create_slack_histogram
	   ::crossprobe_fed
	   ::current_design
	   ::current_fileset
	   ::current_instance
	   ::current_project
	   ::current_run
	   ::delete_debug_core
	   ::delete_debug_port
	   ::delete_fileset
	   ::delete_interface
	   ::delete_pblock
	   ::delete_port
	   ::delete_reconfig_module
	   ::delete_rpm
	   ::delete_run
	   ::delete_timing_results
	   ::demote_run
	   ::device_enable
	   ::disconnect_debug_port
	   ::endgroup
	   ::filter_collection
	   ::generate_ip
	   ::get_cells
	   ::get_clocks
	   ::get_debug_cores
	   ::get_debug_ports
	   ::get_designs
	   ::get_files
	   ::get_filesets
	   ::get_generated_clocks
	   ::get_interfaces
	   ::get_iobanks
	   ::get_lib_cells
	   ::get_lib_pins
	   ::get_libs
	   ::get_nets
	   ::get_param
	   ::get_parts
	   ::get_path_groups
	   ::get_pblocks
	   ::get_pins
	   ::get_ports
	   ::get_projects
	   ::get_property
	   ::get_reconfig_modules
	   ::get_runs
	   ::get_sites
	   ::group_path
	   ::help
	   ::highlight_objects
	   ::implement_debug_core
	   ::import_as_run
	   ::import_files
	   ::import_ip
	   ::launch_chipscope_analyzer
	   ::launch_fpga_editor
	   ::launch_impact
	   ::launch_isim
	   ::launch_runs
	   ::launch_xpa
	   ::list_param
	   ::list_property
	   ::load_reconfig_modules
	   ::make_diff_pair_ports
	   ::mark_objects
	   ::open_impl_design
	   ::open_io_design
	   ::open_netlist_design
	   ::open_project
	   ::open_rtl_design
	   ::place_design
	   ::place_pblocks
	   ::place_ports
	   ::promote_run
	   ::read_chipscope_cdc
	   ::read_csv
	   ::read_pxml
	   ::read_twx
	   ::read_ucf
	   ::read_verilog
	   ::read_xdc
	   ::read_xdl
	   ::redo
	   ::refresh_design
	   ::reimport_files
	   ::remove_cells_from_pblock
	   ::remove_disable_timing
	   ::remove_files
	   ::reorder_files
	   ::report_cell
	   ::report_clock_interaction
	   ::report_constraint
	   ::report_debug_core
	   ::report_delay_calculation
	   ::report_disable_timing
	   ::report_drc
	   ::report_min_pulse_width
	   ::report_param
	   ::report_power
	   ::report_property
	   ::report_resources
	   ::report_ssn
	   ::report_sso
	   ::report_stats
	   ::report_timing
	   ::report_transformed_primitives
	   ::report_ucf_timing
	   ::reset_drc
	   ::reset_ip
	   ::reset_path
	   ::reset_run
	   ::reset_ssn
	   ::reset_sso
	   ::reset_timing
	   ::reset_ucf
	   ::resize_pblock
	   ::save_design
	   ::save_design_as
	   ::save_project_as
	   ::select_objects
	   ::set_case_analysis
	   ::set_clock_gating_check
	   ::set_clock_latency
	   ::set_clock_transition
	   ::set_clock_uncertainty
	   ::set_data_check
	   ::set_delay_model
	   ::set_disable_timing
	   ::set_false_path
	   ::set_hierarchy_separator
	   ::set_ideal_latency
	   ::set_ideal_network
	   ::set_input_delay
	   ::set_input_jitter
	   ::set_load
	   ::set_max_delay
	   ::set_max_time_borrow
	   ::set_min_delay
	   ::set_multicycle_path
	   ::set_operating_conditions
	   ::set_output_delay
	   ::set_package_pin_val
	   ::set_param
	   ::set_propagated_clock
	   ::set_property
	   ::set_speed_grade
	   ::set_switching_activity
	   ::set_system_jitter
	   ::set_timing_derate
	   ::set_units
	   ::split_diff_pair_ports
	   ::start_gui
	   ::startgroup
	   ::stop_gui
	   ::undo
	   ::unhighlight_objects
	   ::unmark_objects
	   ::unselect_objects
	   ::update_file
	   ::update_reconfig_module
	   ::verify_config
	   ::version
	   ::wait_on_run
	   ::write_chipscope_cdc
	   ::write_csv
	   ::write_edf
	   ::write_ncd
	   ::write_pcf
	   ::write_sdc
	   ::write_sdf
	   ::write_timing
	   ::write_ucf
	   ::write_xdc
	   ::write_vhdl
	   ::write_verilog
	}
	if [check_flag ::xilinx::dsptool::iseproject::param::_DRY_RUN] {
	    set decorator [namespace current]::log_command
	} else {
	    set decorator [namespace current]::run_command
	}
	foreach cmd $planahead_cmd_list {
	    set new_cmd "[namespace current]::planahead::[namespace tail $cmd]"
	    decorate_command $decorator $cmd $new_cmd
	}
    }

    #-------------------------------------------------------------------------
    # Handles an exception when evaluating the given script and displays an
    # appropriate error message.
    #
    # @param  script         Script to evaluate.
    # @param  msg            Message to display upon an exception.
    # @param  append_msg     Specifies whether any returned error message is
    #                        also displayed.
    # @return 1 if the script is evaluated successfully, or 0 othewise.
    #-------------------------------------------------------------------------
    proc handle_exception {script {msg ""} {append_msg True}} {
	if [catch { uplevel $script } result] {
	    if {$msg eq ""} {
		set msg "ERROR: An internal error occurred."
	    }
	    puts stderr "$msg"
	    if {$append_msg} {
		puts stderr "\n$result"
	    }
	    return 0
	}
	return 1
    }

    #-------------------------------------------------------------------------
    # Processes all project parameters.
    #
    # REQUIRED PARAMETERS
    # ======================================================================
    #   Project
    #     PlanAhead project name.
    #
    #   Family
    #     Device family into which the design is implemented.
    #
    #   Device
    #     Device into which the design is implemented.
    #
    #   Package
    #     Package for the device being targeted.
    #
    #   Speed
    #     Speed grade of the device being targeted.
    #
    #   ProjectFiles
    #     Source files to be added in the project.
    #
    #
    # OPTIONAL PARAMETERS
    # ======================================================================
    # (*) Notes:
    #     "::=" denotes the list of supported values for each parameter.
    #
    # ----------------------------------------------------------------------
    #
    #   CompilationFlow
    #     Compilation flow.
    #
    #   TopLevelModule
    #     Top-level module of the design.
    #
    #   HDLLanguage
    #     Preferred language property controls the default setting for
    #     process properties that generate HDL output.
    #       ::= "VHDL" | "Verilog"
    #
    #   SynthesisTool
    #     Synthesis tool used for the design.
    #       ::= "XST" | "RDS" | "Synplify" | "Synplify Pro"
    #
    #   SynthesisConstraintsFile
    #     Synthesis constraints file. XCF for XST,
    #     SDC for Synplify/Synplify Pro.
    #
    #   SynthesisRegisterBalancing
    #     Register balancing option of the Synthesis process.
    #
    #   SynthesisRegisterDuplication
    #     Register duplication option of the Synthesis process.
    #
    #   SynthesisRetiming
    #     Retiming option of the Synthesis process. Synplify Pro Only.
    #       ::= True | False
    #
    #   WriteTimingConstraints
    #     Specifies whether or not to place timing constraints in the NGC
    #     file.
    #       ::= True | False
    #
    #   WriteVendorConstraints
    #     Specifies whether or not to generate vendor constraints file.
    #       ::= True | False
    #
    #   ReadCores
    #     Specifies whether or not black box cores are read for timing
    #     and area estimation in order to get better optimization of
    #     the rest of the design.
    #       ::= True | False
    #
    #   InsertIOBuffers
    #     Specifies whether or not to infer input/output buffers on all
    #     top-level I/O ports of the design.
    #       ::= True | False
    #
    #   BusDelimiter
    #     Specifies the delimiter type used to define the signal vectors in
    #     the resulting netlist.
    #       ::= "<>" | "[]" | "{}" | "()"
    #
    #   HierarchySeparator
    #     Hierarchy separator character which will be used in name
    #     generation when the design hierarchy is flattened.
    #       ::= "/" | "_"
    #
    #   KeepHierarchy
    #     Specifies whether or not the corresponding design unit should be
    #     preserved and not merged with the rest of the design.
    #       ::= "Yes" | "No" | "Soft"
    #
    #   Frequency
    #     Global clock frequency for timing-driven synthesis.
    #
    #   FanoutLimit
    #     Maximum limit of the fanout of nets.
    #
    #   MapRegisterDuplication
    #     Register duplication option of the Map process.
    #
    #   MapEffortLevel
    #     Effort level of the Map process.
    #
    #   PAREffortLevel
    #     Effort level of the Place & Route process.
    #
    #   BlockMemoryMapFile
    #     Block memory map (.bmm) file for the Data2MEM process.
    #
    #   BlockMemoryContentFile
    #     Block memory content file for the Data2MEM process.
    #
    #   Simulator
    #     Tool used for simulation.
    #
    #   DesignInstance
    #     Design instance name.
    #
    #   TestBenchModule
    #     Test-bench module.
    #
    #   SimulationTime
    #     Simulation time.
    #
    #   BehavioralSimulationCustomDoFile
    #     Custom Do file for the Behavioral Simulation process.
    #
    #   PostTranslateSimulationCustomDoFile
    #     Custom Do file for the Post-Translate Simulation process.
    #
    #   PostMapSimulationCustomDoFile
    #     Custom Do file for the Post-Map Simulation process.
    #
    #   PostPARSimulationCustomDoFile
    #     Custom Do file for the Post-Place & Route Simulation process.
    #
    #   ISimCustomProjectFile
    #     Custom project file for PlanAhead Simulator.
    #
    #   HasVerilogSource
    #     Indicate the project contains a Verilog source file.
    #
    #   ImplementationStopView
    #
    #   ProjectGenerator
    #
    #-------------------------------------------------------------------------
    proc process_parameters {} {


	optional_parameter ::xilinx::dsptool::iseproject::param::_DRY_RUN False
	optional_parameter ::xilinx::dsptool::iseproject::param::_VERBOSITY $[namespace current]::VERBOSITY_ERROR

	required_parameter ::xilinx::dsptool::iseproject::param::Project

	    if { !$::xilinx::dsptool::planaheadproject::IS_DOING_SMOKETEST } {
	     required_parameter ::xilinx::dsptool::iseproject::param::Family lowercase_pp
	     required_parameter ::xilinx::dsptool::iseproject::param::Device lowercase_pp
	     required_parameter ::xilinx::dsptool::iseproject::param::Package lowercase_pp
	     required_parameter ::xilinx::dsptool::iseproject::param::Speed
	    }

	required_parameter ::xilinx::dsptool::iseproject::param::ProjectFiles

	optional_parameter ::xilinx::dsptool::iseproject::param::Compilation {HDL Netlist}

	if { [string match "HDL Netlist" $::xilinx::dsptool::iseproject::param::Compilation] } {
	      optional_parameter ::xilinx::dsptool::iseproject::param::ProjectDir {hdl_netlist}
	   } elseif { [string match "Bitstream" $::xilinx::dsptool::iseproject::param::Compilation] } {
	      optional_parameter ::xilinx::dsptool::iseproject::param::ProjectDir {bitstream}
	   }  else {	     
		optional_parameter ::xilinx::dsptool::iseproject::param::ProjectDir {hwcosim}
		set ::enable_hwcosim_pa_flow 1
	   }


	optional_parameter ::xilinx::dsptool::iseproject::param::SynthStrategyName {}
	optional_parameter ::xilinx::dsptool::iseproject::param::ImplStrategyName {}

	optional_parameter ::xilinx::dsptool::iseproject::param::CompilationFlow {general}
	optional_parameter ::xilinx::dsptool::iseproject::param::HDLLanguage {VHDL} hdl_language_pp
	optional_parameter ::xilinx::dsptool::iseproject::param::SynthesisTool {XST} synthesis_tool_pp
	optional_parameter ::xilinx::dsptool::iseproject::param::SynthesisRegisterBalancing {No}
	optional_parameter ::xilinx::dsptool::iseproject::param::SynthesisRegisterDuplication True
	optional_parameter ::xilinx::dsptool::iseproject::param::SynthesisRetiming True
	optional_parameter ::xilinx::dsptool::iseproject::param::WriteTimingConstraints False
	optional_parameter ::xilinx::dsptool::iseproject::param::WriteVendorConstraints False
	optional_parameter ::xilinx::dsptool::iseproject::param::ReadCores True
	optional_parameter ::xilinx::dsptool::iseproject::param::InsertIOBuffers True
	set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]
	#optional_parameter ::xilinx::dsptool::iseproject::param::BusDelimiter [expr { $is_vhdl ? {()} : {[]} }]
	optional_parameter ::xilinx::dsptool::iseproject::param::BusDelimiter [expr { $is_vhdl ? {()} : {()} }]
	optional_parameter ::xilinx::dsptool::iseproject::param::HierarchySeparator {/}
	optional_parameter ::xilinx::dsptool::iseproject::param::KeepHierarchy {No}
	optional_parameter ::xilinx::dsptool::iseproject::param::HasVerilogSource False
	optional_parameter ::xilinx::dsptool::iseproject::param::MapRegisterDuplication True
	optional_parameter ::xilinx::dsptool::iseproject::param::MapEffortLevel {High}
	optional_parameter ::xilinx::dsptool::iseproject::param::PAREffortLevel {High}
	optional_parameter ::xilinx::dsptool::iseproject::param::DesignInstance {sysgen_dut}

	clear_empty_parameter ::xilinx::dsptool::iseproject::param::TopLevelModule
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::Frequency
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::FanoutLimit
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::BlockMemoryMapFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::BlockMemoryContentFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::Simulator
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::TestBenchModule
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::BehavioralSimulationCustomDoFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::PostTranslateSimulationCustomDoFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::PostMapSimulationCustomDoFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::PostPARSimulationCustomDoFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::ISimCustomProjectFile
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::ProjectGenerator
	clear_empty_parameter ::xilinx::dsptool::iseproject::param::ImplementationStopView
    }

    #---------------------------------------------------------------------------------------------------------
    # Adds the hwcosim related files to the list of project files and deletes unnecessary files.
    #---------------------------------------------------------------------------------------------------------
    proc hwcosim_modify_project_files {} { 
	   
	set projDir [file normalize $::xilinx::dsptool::iseproject::param::ProjectDir ] 	
	set projfiles $::xilinx::dsptool::iseproject::param::ProjectFiles 
	set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]
	set cosim_interface ""
	set ngc_name ""
	set compilation $::xilinx::dsptool::iseproject::param::Compilation
	set family $::xilinx::dsptool::iseproject::param::Family
	set family_low [string tolower $family]
	set synth_tool $::xilinx::dsptool::iseproject::param::SynthesisTool
	set extension ""
	
	if {"[string match -nocase *jtag* $compilation]"} {
		set cosim_interface jtag 
	} elseif {"[string match -nocase *ethernet* $compilation]"} {
		set cosim_interface pp_ethernet 
	} else {
		#for future interfaces
		error "Invalid Compilation target name $compilation"
	} 
	set filedir "$projDir/../hwcosim_tmp/$cosim_interface" 
	set filedir [file normalize $filedir]
	    
	if {$is_vhdl} {
	    set extension vhd
	    
	} else {
	    set extension v
	}  
	set sources "[ glob -nocomplain $filedir/*.$extension ]" 
	foreach srcfile $sources { 
		set srcfile [file normalize $srcfile]
		lappend  projfiles "{$srcfile} -view all" 	
	}
	    
	# delete the existing ucf file from the project files
	set index [lsearch $projfiles *.ucf*]
	if [expr $index ne -1] {
		set projfiles [lreplace $projfiles $index $index]	
	}
		
	set temp $filedir/hwcosim_top.ucf
	lappend  projfiles "$temp"  

	set ngcSources [ glob -nocomplain $projDir/../*.ngc ] 
	foreach ngcfile $ngcSources {
		set ngcfile [file normalize $ngcfile]
		file copy -force $ngcfile "$projDir/../hwcosim_tmp/$cosim_interface/" 	
		file delete -force $ngcfile
	}
	
	set ngcSources [ glob -nocomplain $filedir/*.ngc ] 
	foreach ngcfile $ngcSources {
		set ngcfile [file normalize $ngcfile]
		#lappend  projfiles "$ngcfile -view all"  
		#copy the ngc files to the sysgen directory, PA project gen looks in this directory for ngc files and add them to the project
		file copy -force $ngcfile $projDir/../sysgen
	}
		
	set ::xilinx::dsptool::iseproject::param::ProjectFiles $projfiles
	set ::xilinx::dsptool::iseproject::param::TopLevelModule hwcosim_top
	    
	if "![string match -nocase *RDS* $synth_tool]" {
		set synth_constraint_file_exists [info exists ::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile]
		if [expr $synth_constraint_file_exists eq 1] {
			unset ::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile
		}   
	} 
    }
    
    #-------------------------------------------------------------------------
    # Dumps all parameters.
    #-------------------------------------------------------------------------
    proc dump_parameters {} {
	if [meet_verbosity VERBOSITY_DEBUG] {
	    dump_variables param
	}
    }

    #-------------------------------------------------------------------------
    # return 0 if string is empty or only has white space
    # return 1 if not empty or not white
    # @param  str          Parameter name.
    #-------------------------------------------------------------------------
    proc not_empty_or_white_string {str} {
	set strtmp [string trim $str]
	set length [string length $strtmp]
	if {$length == 0} {
	    return 0
	} else {
	    return 1
	}
    }

    #-------------------------------------------------------------------------
    # Adds source files to the project.
    #-------------------------------------------------------------------------
    proc add_project_files {} {
	    set ucffiles " "
	    set tbfiles " "
	    set miffiles " "
	set has_testbench [info exists ::xilinx::dsptool::iseproject::param::TestBenchModule]

	if { $::enable_hwcosim_pa_flow == 1} { 
		hwcosim_modify_project_files
	}
	set_property design_mode RTL [ get_filesets sources_1]

    set projDir [file normalize $::xilinx::dsptool::iseproject::param::ProjectDir ]
    set filedir "$projDir/../sysgen"
    if { ![file exists $filedir ] } {
	set filedir "$projDir/.."
    }

	foreach p $::xilinx::dsptool::iseproject::param::ProjectFiles {
	    set filen " "
	    set origname [lindex $p 0]
	    set origrootname [ file rootname [lindex $p 0] ]
	    set filenameraw "$filedir/$origname"
	    if { [file exists $filenameraw] } {
		set filename [file normalize $filenameraw]
	    } else {
		set filename [file normalize $origname]
	    }
	    #set filename [file normalize [lindex $p 0]]
	    set opts [lrange $p 1 end]
	    set nopts [llength $opts]
	    if {$nopts % 2 != 0} {
		error "Parameter \"ProjectFiles\" contains an invalid value \"$p\"."
	    }
	    # Remember it if the project contains a Verilog source file.
	    if [string match -nocase "*.v" $filename] {
		set ::xilinx::dsptool::iseproject::param::HasVerilogSource True
	    }

	    if { ![string match -nocase "*.mdl" $filename] } {
		if { [string match -nocase "*.ucf" $filename] } {
		    set ucffiles "$ucffiles $filename"
		} elseif { [string match -nocase "*.mif" $filename] } {
		    set miffiles "$miffiles $filename"
		} elseif { [string match -nocase "*.v" $filename] || [string match -nocase "*.vhd" $filename]} {
		    if { $has_testbench && [ string match -nocase "$::xilinx::dsptool::iseproject::param::TestBenchModule" $origrootname ] } {
			set tbfiles "$tbfiles $filename"
		    } else {
			set filen "$filename"
		      }
		    }
	    }

	    if [not_empty_or_white_string $filen] {
		    planahead::import_files -fileset [ get_filesets sources_1 ] -force -norecurse $filen

	    for {set i 0} {$i < $nopts} {set i [expr {$i + 2}]} {
		set key [lindex $opts $i]
		set val [lindex $opts [expr {$i + 1}]]
		switch -- $key {
		    "-lib" {
			set_property library $val [get_files -of_object {sources_1} $origname]
		    }
		}
	    }
	}
    }

	#planahead::set_property strategy poweroptimization [ get_runs synth_1 ]
	#set verilogSources [ glob $srcDir/FifoBuffer.v $srcDir/async_fifo.v $srcDir/rtlRam.v $srcDir/$projName/*.v ]
	#import_files -fileset [ get_filesets sources_1 ] -force -norecurse $verilogSources

    if [not_empty_or_white_string $ucffiles] {
	planahead::import_files -fileset [ get_filesets constrs_1 ] -force -norecurse $ucffiles
    }

    if {[string equal -nocase $::xilinx::dsptool::iseproject::param::SynthesisTool "RDS"] } {

	set ipdir ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.srcs/sources_1/ip
	if { ![ file exists $ipdir ] } {
	    file mkdir $ipdir
	    }
	set coeSources [ glob -nocomplain $filedir/*.coe ]
	foreach coefile $coeSources {
	    file copy -force $coefile $ipdir
	}

	set xcoIpNames " "
	#set xcoSources [ glob $projDir/../sysgen/*.xco ]
	set xcoSources [ glob -nocomplain $filedir/*.xco ]
	foreach xcofile $xcoSources {
	    set xcofile [file normalize $xcofile]
	    set dirnames [split $xcofile /]
	    set xcofilename [lindex $dirnames end]
	    if [not_empty_or_white_string $xcofilename] {
		set ipnames [split $xcofilename .]
		set ipname [lindex $ipnames end-1]
		planahead::import_ip -file $xcofile -name  $ipname
		set xcoIpNames "$xcoIpNames $ipname"
	    }
	}

	set ngcSources [ glob -nocomplain $filedir/*.ngc ]
	set ngcfiles " "
	foreach ngcfile $ngcSources { 
	    set ngcfile [file normalize $ngcfile]
	    set dirnames [split $ngcfile /]
	    set ngcfilename [lindex $dirnames end]
	    if [not_empty_or_white_string $ngcfilename] {
		set ngcipnames [split $ngcfilename .]
		set ngcipname [lindex $ngcipnames end-1]
		if { [lsearch $xcoIpNames $ngcipname] < 0 } {
		    set ngcfiles "$ngcfiles $ngcfile"
		}
	    }
	} 

	if [not_empty_or_white_string $ngcfiles] {
	    planahead::import_files -fileset [ get_filesets sources_1 ] -force -norecurse $ngcfiles
	}

    } else {
	set fileext "ngc"
	add_file_to_project $filedir $fileext
    } 
	    
    set fileext "edn"
    add_file_to_project $filedir $fileext

    set fileext "edf"
    add_file_to_project $filedir $fileext

    set fileext "ndf"
    add_file_to_project $filedir $fileext

    if {$has_testbench} {

	if [not_empty_or_white_string $tbfiles] {
	    planahead::import_files -fileset [ get_filesets sim_1 ] -force -norecurse $tbfiles
	}

	if [not_empty_or_white_string $miffiles] {
	    planahead::import_files -fileset [ get_filesets sim_1 ] -force -norecurse $miffiles
	}

	set datpath "$projDir/../sysgen"
	if { ![file exists $filedir ] } {
	    set datpath "$projDir/.."
	}

	set datSources [ glob -nocomplain $datpath/*.dat ]
	if { [string length $datSources] < 1 } {
	    set datpath "$projDir/.."
	    set datSources [ glob -nocomplain $datpath/*.dat ]
	}
	set datfiles " "
	foreach p $datSources {
	    set stmp [file normalize $p]
	    set datfiles "$datfiles $stmp"
	}

	if [not_empty_or_white_string $datfiles] {
	    planahead::import_files -fileset [ get_filesets sim_1 ] -force -norecurse $datfiles
	}
    }

	#planahead::set_property top $::xilinx::dsptool::iseproject::param::TopLevelModule [ get_property srcset [ current_run ] ]
	set_property top $::xilinx::dsptool::iseproject::param::TopLevelModule [ get_property srcset [ current_run ] ]

	if [info exists ::xilinx::dsptool::iseproject::param::TopLevelModule] {
	    #planahead::set_property top $param::TopLevelModule [ get_property srcst [ current_run ] ]

	    #planahead::project set top "/$param::TopLevelModule"
	}
    }

    #-------------------------------------------------------------------------
    # Sets the general project settings.
    #-------------------------------------------------------------------------
    #proc set_project_settings {} {
	#planahead::project set family $param::Family
	#planahead::project set device $param::Device
	#planahead::project set package $param::Package
	#planahead::project set speed $param::Speed
    #}

    #-------------------------------------------------------------------------
    # Sets the synthesis settings for Rodin.
    #-------------------------------------------------------------------------
    proc set_rodin_synthesis_settings {} {
	set_property flow {RDS 13} [get_runs synth_1]
	if { [string length $::xilinx::dsptool::iseproject::param::SynthStrategyName] > 0 } {
	    set_property strategy "$::xilinx::dsptool::iseproject::param::SynthStrategyName" [get_runs synth_1]
	    return
	}
    }
   
    #-------------------------------------------------------------------------
    # Sets the synthesis settings for XST.
    #-------------------------------------------------------------------------
    proc set_xst_synthesis_settings {} {
	set_property flow {XST 13} [get_runs synth_1]
	#set_property strategy Sysgen_Defaults [get_runs synth_1]

	if { [string length $::xilinx::dsptool::iseproject::param::SynthStrategyName] > 0 } {
	    if {[string equal -nocase $::xilinx::dsptool::iseproject::param::SynthStrategyName "XST Defaults*"] } {
		set_property strategy "XST Defaults" [get_runs synth_1]
	    } else {
		set_property strategy "$::xilinx::dsptool::iseproject::param::SynthStrategyName" [get_runs synth_1]
		return
	    }
	}	    
	    
	#set moreoptions {-read_core yes}
	#set moreoptions {}
	# XST specific properties
	#planahead::project set {Synthesis Tool} {XST (VHDL/Verilog)}
	#planahead::project set {Optimization Goal} {Speed}
	planahead::config_run synth_1 -program xst -option -opt_mode -value speed
	#planahead::project set {Optimization Effort} {Normal} -process {Synthesize - XST}
	planahead::config_run synth_1 -program xst -option -opt_level -value 1
	#planahead::project set {Keep Hierarchy} $::xilinx::dsptool::iseproject::param::KeepHierarchy
	#set moreoptions "$moreoptions -keep_hierarchy $::xilinx::dsptool::iseproject::param::KeepHierarchy"
	set moreoptions "-keep_hierarchy $::xilinx::dsptool::iseproject::param::KeepHierarchy"
	#planahead::project set {Bus Delimiter} $::xilinx::dsptool::iseproject::param::BusDelimiter

	# tmp walk around for CR 618368, pa xml parser cannot read in [], the bus delimiter for verilog
	# should roll back after the cr is fixed
	#set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]
	#if {$is_vhdl} {
	    set moreoptions "$moreoptions -bus_delimiter $::xilinx::dsptool::iseproject::param::BusDelimiter"
	#}

	#planahead::project set {Hierarchy Separator} $::xilinx::dsptool::iseproject::param::HierarchySeparator
	set moreoptions "$moreoptions -hierarchy_separator $::xilinx::dsptool::iseproject::param::HierarchySeparator"
	#set read_cores [project get {Read Cores}]
	# TODO: Remove this check when PlanAhead settles with the read core property value
	if {[string equal -nocase $::xilinx::dsptool::iseproject::param::ReadCores "false"] 
	    || [string equal -nocase $::xilinx::dsptool::iseproject::param::ReadCores "no"]} {
		set moreoptions "$moreoptions -read_cores no"
	} else {
		set moreoptions "$moreoptions -read_cores yes"
	}
	#planahead::project set {Add I/O Buffers} $::xilinx::dsptool::iseproject::param::InsertIOBuffers
	if { [string equal -nocase $::xilinx::dsptool::iseproject::param::InsertIOBuffers "false"]
	    || [string equal -nocase $::xilinx::dsptool::iseproject::param::InsertIOBuffers "no"]} {
	    set moreoptions "$moreoptions -iobuf no"
	} else {
	    set moreoptions "$moreoptions -iobuf yes"
	}
	# planahead::project set {Optimize Instantiated Primitives} True
	#planahead::project set {Register Balancing} $::xilinx::dsptool::iseproject::param::SynthesisRegisterBalancing
	planahead::config_run synth_1 -program xst -option -register_balancing -value $::xilinx::dsptool::iseproject::param::SynthesisRegisterBalancing

	#planahead::project set {Register Duplication} $::xilinx::dsptool::iseproject::param::SynthesisRegisterDuplication -process {Synthesize - XST}
	if { [string equal -nocase $::xilinx::dsptool::iseproject::param::SynthesisRegisterDuplication "false"]
	    || [string equal -nocase $::xilinx::dsptool::iseproject::param::SynthesisRegisterDuplication "no"]} {
	    set moreoptions "$moreoptions -register_duplication no"
	} else {
	    set moreoptions "$moreoptions -register_duplication yes"
	}

	#planahead::project set {Write Timing Constraints} $::xilinx::dsptool::iseproject::param::WriteTimingConstraints
	if { [string equal -nocase $::xilinx::dsptool::iseproject::param::WriteTimingConstraints "true"]
	    || [string equal -nocase $::xilinx::dsptool::iseproject::param::WriteTimingConstraints "yes"]} {
	    set moreoptions "$moreoptions -write_timing_constraints yes"
	} else {
	    set moreoptions "$moreoptions -write_timing_constraints no"
	}


	if [info exists ::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile] {

	    set projDir [file normalize $::xilinx::dsptool::iseproject::param::ProjectDir ]
	    set filedir "$projDir/../sysgen"
	    if { ![file exists $filedir ] } {
		set filedir "$projDir/.."
	    }

	    set origname "$::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile"
	    set filenameraw "$filedir/$origname"
	    if { [file exists $filenameraw] } {
		set filename [file normalize $filenameraw]
	    } else {
		set filename [file normalize $origname]
	    }

	    #planahead::project set {Use Synthesis Constraints File} True
	    #set moreoptions "$moreoptions -iuc no"
	    #xst unable to handle multi-cycle path constraints
	    set moreoptions "$moreoptions -iuc yes"
	    #planahead::project set {Synthesis Constraints File} $::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile
	    #set moreoptions "$moreoptions -uc \"$::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile\""
	    #set moreoptions "$moreoptions -uc $::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile"
	    #set moreoptions "$moreoptions -uc \"[file normalize $::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile]\""
	    #set moreoptions "$moreoptions -uc [file normalize $::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile]"
	    #planahead::import_files -fileset [ get_filesets constrs_1 ] -force -norecurse [file normalize $::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile]
	    planahead::import_files -fileset [ get_filesets constrs_1 ] -force -norecurse $filename
	} else {
	    #planahead::project set {Use Synthesis Constraints File} False
	    set moreoptions "$moreoptions -iuc yes"
	}

	if [info exists ::xilinx::dsptool::iseproject::param::FanoutLimit] {
	    #planahead::project set {Max Fanout} $::xilinx::dsptool::iseproject::param::FanoutLimit
	    set moreoptions "$moreoptions -max_fanout $::xilinx::dsptool::iseproject::param::FanoutLimit"
	}

	planahead::config_run synth_1 -program xst -option {More Options} -value "$moreoptions"	 	
    }

    #-------------------------------------------------------------------------
    # Sets the synthesis settings for Synplify/Synplify Pro.
    #-------------------------------------------------------------------------
    proc set_synplify_synthesis_settings {} {
	set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]

	switch $::xilinx::dsptool::iseproject::param::SynthesisTool {
	    "Synplify" {
		if {$is_vhdl} {
		    planahead::project set {Synthesis Tool} {Synplify (VHDL)}
		} else {
		    planahead::project set {Synthesis Tool} {Synplify (Verilog)}
		}
	    }
	    "Synplify Pro" {
		planahead::project set {Synthesis Tool} {Synplify Pro (VHDL/Verilog)}
		planahead::project set {Retiming} $::xilinx::dsptool::iseproject::param::SynthesisRetiming -process {Synthesize - Synplify Pro}
	    }
	}

	# Synplify/Synplify Pro specific properties
	planahead::project set {Symbolic FSM Compiler} False
	planahead::project set {Pipelining} False
	planahead::project set {Resource Sharing} False
	planahead::project set {Disable I/O insertion} [ expr { $::xilinx::dsptool::iseproject::param::InsertIOBuffers ? False : True } ]
	planahead::project set {Auto Constrain} False
	if [info exists ::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile] {
	    planahead::project set {Constraint File Name} $::xilinx::dsptool::iseproject::param::SynthesisConstraintsFile
	}
	planahead::project set {Write Vendor Constraint File} $::xilinx::dsptool::iseproject::param::WriteVendorConstraints
	if [info exists ::xilinx::dsptool::iseproject::param::Frequency] {
	    planahead::project set {Frequency} $::xilinx::dsptool::iseproject::param::Frequency
	}
	if [info exists ::xilinx::dsptool::iseproject::param::FanoutLimit] {
	    planahead::project set {Fanout Guide} $::xilinx::dsptool::iseproject::param::FanoutLimit
	}
    }

    
    
    #-------------------------------------------------------------------------
    # Sets the hwcosim synthesis settings for XST.
    #-------------------------------------------------------------------------
    proc set_hwcosim_xst_synthesis_settings {} {
	    
	set_property flow {XST 13} [get_runs synth_1]
	    
	if { [string length $::xilinx::dsptool::iseproject::param::SynthStrategyName] > 0 } {
	    if {[string equal -nocase $::xilinx::dsptool::iseproject::param::SynthStrategyName "XST Defaults*"] } {
		set_property strategy "XST Defaults" [get_runs synth_1]
	    } else {
		set_property strategy "$::xilinx::dsptool::iseproject::param::SynthStrategyName" [get_runs synth_1]
		return
	    }
	}	
	#set ::xilinx::dsptool::iseproject::param::SynthStrategyName  "XST Defaults"
	#set_property strategy "XST Defaults" [get_runs synth_1]
	planahead::config_run synth_1 -program xst -option {More Options} -value {-bus_delimiter <> -case maintain -cross_clock_analysis YES -glob_opt AllClockNets -hierarchy_separator / -ifmt MIXED -iobuf NO -ofmt NGC -opt_level 2 -opt_mode SPEED  -write_timing_constraints YES -generics { } }	    
	
    }
    
    #-------------------------------------------------------------------------
    # Sets the synthesis settings.
    #-------------------------------------------------------------------------
    proc set_synthesis_settings {} {
	#planahead::project set {Preferred Language} $::xilinx::dsptool::iseproject::param::HDLLanguage

	switch -- $::xilinx::dsptool::iseproject::param::SynthesisTool {
	    "XST" {
		if { $::enable_hwcosim_pa_flow == 1} {
			set_hwcosim_xst_synthesis_settings
		} else {
			set_xst_synthesis_settings
		}
	    }
	    "RDS" {
		set_rodin_synthesis_settings
	    }
	    #"Synplify" - "Synplify Pro" {
		#set_synplify_synthesis_settings
	    #}
	}
    }
    
    #-------------------------------------------------------------------------
    # Sets the implementation settings.
    #-------------------------------------------------------------------------
    proc set_implementation_settings {} {

	switch -- $::xilinx::dsptool::iseproject::param::SynthesisTool {
	    "XST" {		    
		if { $::enable_hwcosim_pa_flow == 1} {
		    set_hwcosim_xst_implementation_settings 
		} else {
		    set_xst_implementation_settings
		}		
	    }
	    "RDS" {
		set_rodin_implementation_settings
	    }
	}
    }

    #-------------------------------------------------------------------------
    # Sets the xst implementation settings.
    #-------------------------------------------------------------------------
    proc set_rodin_implementation_settings {} {
	set_property flow {RDI 13} [get_runs impl_1]
	if { [string length $::xilinx::dsptool::iseproject::param::ImplStrategyName] > 0 } {
	    set_property strategy "$::xilinx::dsptool::iseproject::param::ImplStrategyName" [get_runs impl_1]
	    return
	}
    }

     
    #-------------------------------------------------------------------------
    # Sets the xst implementation settings.
    #-------------------------------------------------------------------------
    proc set_hwcosim_xst_implementation_settings {} {
	     
	set_property flow {ISE 13} [get_runs impl_1]
	    
	    
	if { [string length $::xilinx::dsptool::iseproject::param::ImplStrategyName] > 0 } {
	    if {[string equal -nocase $::xilinx::dsptool::iseproject::param::ImplStrategyName "ISE Defaults*"] } {
		set_property strategy "ISE Defaults" [get_runs impl_1]
	    } else {
		set_property strategy "$::xilinx::dsptool::iseproject::param::ImplStrategyName" [get_runs impl_1]
		return
	    }
	}
	    
	set compilation $::xilinx::dsptool::iseproject::param::Compilation   
	#set_property strategy "ISE Defaults" [get_runs impl_1]
	#set ::xilinx::dsptool::iseproject::param::ImplStrategyName "ISE Defaults" 
	    
	set moreoptions_ngdbuild {-nt timestamp}
	set projDir [file normalize $::xilinx::dsptool::iseproject::param::ProjectDir ]  
	set filedir "$projDir/.."   
	set filedir [ file normalize $filedir ] 
	set bmmsources [ glob -nocomplain $filedir/*.bmm ]   
	if {[llength bmmsources] == 1} {
		
	    if {![string match "" $bmmsources]} { 
		    
		    if {"[string match -nocase *ethernet* $compilation]"} { 
			    set moreoptions_ngdbuild "$moreoptions_ngdbuild -bm $bmmsources"   
		    } 
		    
	    } 
	}    
	    
	planahead::config_run impl_1 -program ngdbuild -option {More Options} -value "$moreoptions_ngdbuild"   
	      
	planahead::config_run impl_1 -program map -option -ol -value "high"
	planahead::config_run impl_1 -program map -option -timing -value "true"   
	    
	planahead::config_run impl_1 -program par -option -ol -value "high"   
    }
    
    #-------------------------------------------------------------------------
    # Sets the xst implementation settings.
    #-------------------------------------------------------------------------
    proc set_xst_implementation_settings {} {

	set_property flow {ISE 13} [get_runs impl_1]
	#set_property strategy Sysgen_Defaults [get_runs impl_1]

	if { [string length $::xilinx::dsptool::iseproject::param::ImplStrategyName] > 0 } {
	    if {[string equal -nocase $::xilinx::dsptool::iseproject::param::ImplStrategyName "ISE Defaults*"] } {
		set_property strategy "ISE Defaults" [get_runs impl_1]
	    } else {
		set_property strategy "$::xilinx::dsptool::iseproject::param::ImplStrategyName" [get_runs impl_1]
		return
	    }
	}

	# Translate properties
	#planahead::project set {Netlist Translation Type} {Timestamp}
	set moreoptions {-nt timestamp}
	#planahead::project set {Use LOC Constraints} True
	if [info exists ::xilinx::dsptool::iseproject::param::BlockMemoryMapFile] {
	    #planahead::project set {Other Ngdbuild Command Line Options} "-bm $::xilinx::dsptool::iseproject::param::BlockMemoryMapFile"
	    set moreoptions "$moreoptions -bm $::xilinx::dsptool::iseproject::param::BlockMemoryMapFile"
	}

	planahead::config_run impl_1 -program ngdbuild -option {More Options} -value "$moreoptions"

	# Determine the type of value the "Map Register Duplication" property accepts
	switch -- $::xilinx::dsptool::iseproject::param::Family {
	    "virtex" - "virtexe" - "spartan2" - "spartan2e" {
	    }
	    default {
		#set map_reg_dup [project get {Register Duplication} -process {Map}]
		#if {[string equal -nocase $map_reg_dup "true"] || [string equal -nocase $map_reg_dup "false"]} {
		    #set map_reg_dup $::xilinx::dsptool::iseproject::param::MapRegisterDuplication
		#} elseif {[string equal -nocase $map_reg_dup "on"] || [string equal -nocase $map_reg_dup "off"]} {
		    #set map_reg_dup [ expr { $::xilinx::dsptool::iseproject::param::MapRegisterDuplication ? "On" : "Off" } ]
		#} else {
		    #set map_reg_dup [ expr { $::xilinx::dsptool::iseproject::param::MapRegisterDuplication ? "Yes" : "No" } ]
		#}

		set map_reg_dup $::xilinx::dsptool::iseproject::param::MapRegisterDuplication
		if {[string equal -nocase $map_reg_dup "true"]
		    || [string equal -nocase $map_reg_dup "on"]
		    || [string equal -nocase $map_reg_dup "yes"]} {
		    set map_reg_dup {true}
		} else {
		    set map_reg_dup {false}
		}
	    }
	}



	# Map properties

	set mapol $::xilinx::dsptool::iseproject::param::MapEffortLevel
	if {[string equal -nocase $mapol "high"]} {
		set mapol {high}
	    } elseif {[string equal -nocase $mapol "standard"] 
	    || [string equal -nocase $mapol "std"]} {
		set mapol {std}
	    } else {
		set mapol {<none>}
	    }

	switch -glob -- $::xilinx::dsptool::iseproject::param::Family {
	    "*virtex4*" - "*spartan3*" {
		#planahead::project set {Map Effort Level} $::xilinx::dsptool::iseproject::param::MapEffortLevel
		planahead::config_run impl_1 -program map -option -ol -value $mapol

		#planahead::project set {Perform Timing-Driven Packing and Placement} True
		planahead::config_run impl_1 -program map -option -timing -value true

		#planahead::project set {Register Duplication} $map_reg_dup -process {Map}
		planahead::config_run impl_1 -program map -option -register_duplication -value $map_reg_dup
	    }
	    "virtex" - "virtexe" - "spartan2" - "spartan2e" {
		#planahead::project set {Perform Timing-Driven Packing} True
		planahead::config_run impl_1 -program map -option -timing -value true
	    }
	    default {
		#planahead::project set {Placer Effort Level} $::xilinx::dsptool::iseproject::param::MapEffortLevel
		planahead::config_run impl_1 -program map -option -ol -value $mapol

		#planahead::project set {Register Duplication} $map_reg_dup -process {Map}
		planahead::config_run impl_1 -program map -option -register_duplication -value $map_reg_dup
	    }
	}

	# Place & Route properties
	#planahead::project set {Place & Route Effort Level (Overall)} $::xilinx::dsptool::iseproject::param::PAREffortLevel

	set parol $::xilinx::dsptool::iseproject::param::PAREffortLevel
	if {[string equal -nocase $parol "high"]} {
		set parol {high}
	    } elseif {[string equal -nocase $parol "standard"] 
	    || [string equal -nocase $parol "std"]} {
		set parol {std}
	    } else {
		set parol {<none>}
	    }
	planahead::config_run impl_1 -program par -option -ol -value $parol
    }

    #-------------------------------------------------------------------------
    # Sets the configuration settings
    #-------------------------------------------------------------------------
    proc set_configuration_settings {} {
	switch -- $::xilinx::dsptool::iseproject::param::CompilationFlow {
	    "hwcosim" {
		planahead::project set {FPGA Start-Up Clock} {JTAG Clock}
		planahead::project set {Drive Done Pin High} True
		switch -- $::xilinx::dsptool::iseproject::param::Family {
		    "virtex2" - "virtex2p" - "virtex4" - "virtex5" {
			planahead::project set {Configuration Pin M0} {Pull Up}
			planahead::project set {Configuration Pin M1} {Pull Down}
			planahead::project set {Configuration Pin M2} {Pull Up}
		    }
		}
	    }
	}
	if [info exists ::xilinx::dsptool::iseproject::param::BlockMemoryContentFile] {
	    planahead::project set {Other Bitgen Command Line Options} "-bd $::xilinx::dsptool::iseproject::param::BlockMemoryContentFile"
	}
    }

    #-------------------------------------------------------------------------
    # Sets the simulation settings
    #-------------------------------------------------------------------------
    proc set_simulation_settings {} {
	set has_testbench [info exists ::xilinx::dsptool::iseproject::param::TestBenchModule]
	if {!$has_testbench} {
	    return
	} else {
	    create_fileset -simset sim_1
	    set_property SOURCE_SET sources_1 [ get_filesets sim_1 ]
	    set_property top $::xilinx::dsptool::iseproject::param::TestBenchModule [ get_filesets sim_1 ]
	}

	set has_simtime [info exists ::xilinx::dsptool::iseproject::param::SimulationTime]
	if {$has_simtime} {
	    set_property runtime $::xilinx::dsptool::iseproject::param::SimulationTime [ get_filesets sim_1 ]
	}

	#set_property NG.MORE_NETGEN_OPTIONS {-sdf_anno false} [ get_filesets sim_1 ]
	set_property nl.sdf_anno false [get_filesets sim_1]
	set_property ng.sdf_anno false [get_filesets sim_1]
	set_property source_mgmt_mode DisplayOnly [current_project]

	set is_vhdl [expr { $::xilinx::dsptool::iseproject::param::HDLLanguage eq "VHDL" }]
	if { !$is_vhdl } {
	    set_property -property_name FUSE.MORE_OPTIONS -property_value {-L unisims_ver -L simprims_ver -L xilinxcorelib_ver -L secureip} -object [get_filesets sim_1]
	    #set_property -name FUSE.MORE_OPTIONS -value "-L unisims_ver -L simprims_ver -L xilinxcorelib_ver -L secureip" -object [get_filesets sim_1]
	}
    }

    #-------------------------------------------------------------------------
    # Sets the specific settings related to DSP Tools
    #-------------------------------------------------------------------------
    proc set_dsptools_specific_settings {} {
	if [info exists ::xilinx::dsptool::iseproject::param::ImplementationStopView] {
	    planahead::project set {Implementation Stop View} $::xilinx::dsptool::iseproject::param::ImplementationStopView
	}
	if [info exists ::xilinx::dsptool::iseproject::param::ProjectGenerator] {
	    planahead::project set {Project Generator} $::xilinx::dsptool::iseproject::param::ProjectGenerator
	}
    }

    #-------------------------------------------------------------------------
    # Starts the project creation.
    #-------------------------------------------------------------------------
    proc start_project_creation {} {
	if { [file exists $::xilinx::dsptool::iseproject::param::ProjectDir] } {
	    file delete -force $::xilinx::dsptool::iseproject::param::ProjectDir
	}
	#file delete "$param::Project\.xise"
	#file delete "$param::Project\.gise"
	#file delete "$param::Project\.sgp"
	#planahead::project new $param::Project
	planahead::create_project $::xilinx::dsptool::iseproject::param::Project $::xilinx::dsptool::iseproject::param::ProjectDir \
	    -part ${::xilinx::dsptool::iseproject::param::Device}${::xilinx::dsptool::iseproject::param::Package}${::xilinx::dsptool::iseproject::param::Speed}
    }

    #-------------------------------------------------------------------------
    # Finishes the project creation.
    #-------------------------------------------------------------------------
    proc finish_project_creation {} {
	if { [catch current_project] } {
	    return
	}
	planahead::close_project
    }

    #-------------------------------------------------------------------------
    # delete everything except hdl_netlist dir in netlist dir
    # move everyting in hdl_netlist to netlist and rm hdl_netlist
    #-------------------------------------------------------------------------
    proc clean_up {} {
	set netlistfilelist [glob -nocomplain *]
	set nfiles [llength $netlistfilelist]
	for {set i 0} {$i<$nfiles} {incr i} {
	    set fn [lindex $netlistfilelist $i]
	    if {![string match "hdl_netlist" $fn]} {
		file delete -force $fn
	    }
	}

	[cd hdl_netlist]
	set netlistfilelist [glob -nocomplain *]
	[cd ..]
	set nfiles [llength $netlistfilelist]
	for {set i 0} {$i<$nfiles} {incr i} {
	    set fn [lindex $netlistfilelist $i]
	    file rename hdl_netlist/$fn $fn
	}

	file delete -force hdl_netlist
    }

    #-------------------------------------------------------------------------
    # Creates a new PlanAhead project.
    #-------------------------------------------------------------------------
    proc create_planahead_project {} {
	start_project_creation
	#set_project_settings
	#add_project_files
	#set_dsptools_specific_settings
	set_synthesis_settings
	set_implementation_settings
	#set_configuration_settings
	set_simulation_settings
	add_project_files
	finish_project_creation
	#clean_up
	
	if { [string match "Bitstream" $::xilinx::dsptool::iseproject::param::Compilation] } {
	    generate_planahead_bitstream
	} elseif { $::enable_hwcosim_pa_flow == 1} { 
	    generate_planahead_bitstream
	} 

	#compile_planahead_project
    }

    #-------------------------------------------------------------------------
    # check bitstream file.
    #-------------------------------------------------------------------------
    proc check_bitstream_file {} {
	if { $::enable_hwcosim_pa_flow == 1} {
		set bitfile ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.runs/impl_1/hwcosim_top.bit		    
		file copy -force $bitfile ${::xilinx::dsptool::iseproject::param::ProjectDir}/../${::xilinx::dsptool::iseproject::param::Project}.bit
	} else {
		 set bitfile ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.runs/impl_1/${::xilinx::dsptool::iseproject::param::Project}.bit 
	}
	if { ! [ file exists $bitfile ] } {
	    set bitgenerrmsg "failed to generate bitstream file $bitfile.\n"
	    #error "ERROR: $bitgenerrmsg"
	    puts "ERROR: $bitgenerrmsg"
	    exit 1
	}
    }

    #-------------------------------------------------------------------------
    # Compiles an PlanAhead project into a bitstream.
    #-------------------------------------------------------------------------
    proc generate_planahead_bitstream {} {
	::open_project ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.ppr

	::reset_run synth_1
	::reset_run impl_1

	#::set_param logicopt.enableMandatoryLopt no

	::launch_runs synth_1
	::wait_on_run synth_1

	set runmelog ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.runs/synth_1/runme.log
	dumpfile $runmelog

	::set_property add_step Bitgen [get_runs impl_1]
	    
	if { $::enable_hwcosim_pa_flow == 1} { 
	    config_run impl_1 -program bitgen -option {More Options} -value {-g StartUpClk:JtagClk}
	}   
	    
	::launch_runs impl_1
	::wait_on_run impl_1

	::close_project

	set runmelog ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.runs/impl_1/runme.log
	dumpfile $runmelog

	check_bitstream_file
    }

    #-------------------------------------------------------------------------
    # Do smoket test for an PlanAhead project
    #-------------------------------------------------------------------------
    proc compile_planahead_project {} {

	set status [handle_exception {
	    decorate_planahead_commands
	} "ERROR: An error occurred when loading PlanAhead Tcl commands." False]
	if {!$status} { return }

	set status [handle_exception {
	    process_parameters
	    dump_parameters
	} "ERROR: An error occurred when processing project parameters."]
	if {!$status} { return }

	::set_param logicopt.enableMandatoryLopt no
	#::set_param simulation.disableSDFAnnotation 1
	#planahead::open_project hdl_netlist/${::xilinx::dsptool::iseproject::param::Project}.ppr
	#::open_project hdl_netlist/${::xilinx::dsptool::iseproject::param::Project}.ppr
	::open_project ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.ppr

	if { [string match "Bitstream" $::xilinx::dsptool::iseproject::param::Compilation] } {
	    ::open_rtl_design -name rtl_1
	    ::open_impl_design
	    ::close_project

	    check_bitstream_file

	    return;
	}

	set has_simtime [info exists ::xilinx::dsptool::iseproject::param::SimulationTime]
	if {$has_simtime} {
	    set isimcmdfn [file normalize "xt_isim.cmd"]
	    set isimcmd [open $isimcmdfn w]
	    puts $isimcmd {onerror {resume}}
	    puts $isimcmd "run $::xilinx::dsptool::iseproject::param::SimulationTime"
	    puts $isimcmd "quit -f"
	    set_property isim.cmdfile $isimcmdfn [get_filesets sim_1]
	    close $isimcmd
	}

	::reset_run synth_1
	::reset_run impl_1

	#set isimlog hdl_netlist/${::xilinx::dsptool::iseproject::param::Project}.sim/sim_1/isim.log
	set isimlog ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.sim/sim_1/isim.log

	::open_rtl_design -name rtl_1

	set has_testbench [info exists ::xilinx::dsptool::iseproject::param::TestBenchModule]
	if {$has_testbench} {
	    ::launch_isim -batch -simset sim_1 -mode behavioral

	    set simtype "behavior"
	    set isimerrmsg [ wait_on_isim $isimlog $simtype 1 ]
	    if { [string length $isimerrmsg] > 0 } {
		return
	    }
	}

	::launch_runs synth_1
	::wait_on_run synth_1

	set runmelog ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.runs/synth_1/runme.log
	dumpfile $runmelog

	#run_post_synth_sim

	#set simtype "post-synth"
	#set isimerrmsg [ wait_on_isim $isimlog $simtype 1 ]
	#if { [string length $isimerrmsg] > 0 } {
	#    return
	#}

	#::close_project
	#error "post_synth is done"

	#clean_up_post_synth_sim

	if { [info exists ::xilinx::dsptool::planaheadprojecttest::is_doing_planAheadGenPostSynthTest] } {
	    ::close_project
	    return
	}

	#::set_property add_step Bitgen [get_runs impl_1]
	::launch_runs impl_1
	::wait_on_run impl_1

	set runmelog ${::xilinx::dsptool::iseproject::param::ProjectDir}/${::xilinx::dsptool::iseproject::param::Project}.runs/impl_1/runme.log
	dumpfile $runmelog

	::open_impl_design

	if {$has_testbench} {
	    ::launch_isim -batch -simset sim_1 -mode timing

	    set simtype "timing"
	    set isimerrmsg [ wait_on_isim $isimlog $simtype 0 ]
	    if { [string length $isimerrmsg] > 0 } {
		return
	    }
	}

	::close_project
    }

    #-------------------------------------------------------------------------
    # Entry point for creating a new PlanAhead project.
    #-------------------------------------------------------------------------
    proc create {} {
	set status [handle_exception {
	    decorate_planahead_commands
	} "ERROR: An error occurred when loading PlanAhead Tcl commands." False]
	if {!$status} { return }

	set status [handle_exception {
	    process_parameters
	    dump_parameters
	} "ERROR: An error occurred when processing project parameters."]
	if {!$status} { return }

	#planahead::project new -help
	#::help ::version
	#::xilinx::dsptool::planaheadproject::process_parameters
	#::version
	#::xilinx::dsptool::planaheadproject::process_parameters
	#::xilinx::dsptool::planaheadproject::planahead::version

	set status [handle_exception {
	    create_planahead_project
	} "ERROR: An error occurred when creating the PlanAhead project."]
	if {!$status} { return }
    }

}
# END namespace ::xilinx::dsptool::planaheadproject
