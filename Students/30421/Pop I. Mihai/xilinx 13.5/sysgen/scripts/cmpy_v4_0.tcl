# $RCSfile: cmpy_v4_0.tcl,v $ $Date: 2010/06/28 13:54:37 $ $Revision: 1.1.2.13 $
#
#-- (c) Copyright 2009 Xilinx, Inc. All rights reserved.
#--
#-- This file contains confidential and proprietary information
#-- of Xilinx, Inc. and is protected under U.S. and
#-- international copyright and other intellectual property
#-- laws.
#--
#-- DISCLAIMER
#-- This disclaimer is not a license and does not grant any
#-- rights to the materials distributed herewith. Except as
#-- otherwise provided in a valid license issued to you by
#-- Xilinx, and to the maximum extent permitted by applicable
#-- law: (1) THESE MATERIALS ARE MADE AVAILABLE "AS IS" AND
#-- WITH ALL FAULTS, AND XILINX HEREBY DISCLAIMS ALL WARRANTIES
#-- AND CONDITIONS, EXPRESS, IMPLIED, OR STATUTORY, INCLUDING
#-- BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY, NON-
#-- INFRINGEMENT, OR FITNESS FOR ANY PARTICULAR PURPOSE; and
#-- (2) Xilinx shall not be liable (whether in contract or tort,
#-- including negligence, or under any other theory of
#-- liability) for any loss or damage of any kind or nature
#-- related to, arising under or in connection with these
#-- materials, including for any direct, or any indirect,
#-- special, incidental, or consequential loss or damage
#-- (including loss of data, profits, goodwill, or any type of
#-- loss or damage suffered as a result of any action brought
#-- by a third party) even if such damage or loss was
#-- reasonably foreseeable or Xilinx had been advised of the
#-- possibility of the same.
#--
#-- CRITICAL APPLICATIONS
#-- Xilinx products are not designed or intended to be fail-
#-- safe, or for use in any application requiring fail-safe
#-- performance, such as life-support or safety devices or
#-- systems, Class III medical devices, nuclear facilities,
#-- applications related to the deployment of airbags, or any
#-- other applications that could lead to death, personal
#-- injury, or severe property or environmental damage
#-- (individually and collectively, "Critical
#-- Applications"). Customer assumes the sole risk and
#-- liability of any use of Xilinx products in Critical
#-- Applications, subject only to applicable laws and
#-- regulations governing limitations on product liability.
#--
#-- THIS COPYRIGHT NOTICE AND DISCLAIMER MUST BE RETAINED AS
#-- PART OF THIS FILE AT ALL TIMES. 

source [FindFullPath "com/xilinx/ip/xbip_utils_v2_0/gui/xbip_utils_v2_0.tcl" ]
source [FindFullPath "com/xilinx/ip/mult_gen_v11_2/gui/mult_gen_v11_2_utils.tcl" ]
source [::IpGui::File "cmpy_v4_0_utils.tcl"]

#anindit - source AXI subfield utils tcl
source [::IpGui::File "AXISysgenSubfieldsUtil.tcl"]

set IpModel [Sim::IpModelInit]
$IpModel SetVersion 2
$IpModel SetTabToShow "Resource Estimates"

set c_xdevicefamily [string tolower [$IpModel GetProperty "DeviceFamily"] ]
set has_mult18x18 false
#if {[IsDerivedFrom $c_xdevicefamily "spartan3"] || [IsDerivedFrom $c_xdevicefamily "spartan3e"] || [IsDerivedFrom $c_xdevicefamily "spartan3a"]} {
#    set has_mult18x18 true
#}
if {[xbip_utils_v2_0::supports_mult18x18 $c_xdevicefamily] == 1 || [xbip_utils_v2_0::supports_mult18x18sio $c_xdevicefamily] == 1} {
    # Identify Spartan-3, Spartan-3A and Spartan-3E devices
    set has_mult18x18 true
}
set has_dsp48 {$has_mult18x18 == true ? false : true}

# Only write out resource estimates and latency values
# for internal users, based on an env var
set write_test_files false
if { [info exists ::env(XILINX_CMPY_TEST_FILES) ] } {
    set write_test_files true
} else {
    set write_test_files false
}
puts "-->> Set write_test_files to $write_test_files"

#################################
#Constants
#################################
set AXI_lat_overhead 9

#################################################################
# Utility procedures
#################################################################

proc optimize_goal_xco_to_sim { opt_goal } {
    puts "Entering [get_proc_name]"
    if {$opt_goal == "Resources"} {
	return 0
    } elseif {$opt_goal == "Performance"} {
	return 1
    } else {
	puts "ERROR: cmpy_v4_0: Unrecognised optimize_goal value caught in optimize_goal_xco_to_sim: $opt_goal"
	return 2
    }
}

proc mult_type_xco_to_sim { mult_type } {
    puts "Entering [get_proc_name]"
    if {$mult_type == "Use_LUTs"} {
	return 0
    } elseif {$mult_type == "Use_Mults"} {
	return 1
    } else {
	puts "ERROR: cmpy_v4_0: Unrecognised mult_type value caught in mult_type_xco_to_sim: $mult_type"
	return 2
    }
}

proc round_mode_xco_to_sim { round_mode } {
    puts "Entering [get_proc_name]"
    if {$round_mode == "Truncate"} {
	return 0
    } elseif {$round_mode == "Random_Rounding"} {
	return 1
    } else {
	puts "ERROR: cmpy_v4_0: Unrecognised round_mode value caught in round_mode_xco_to_sim: $round_mode"
	return 2
    }
}

proc calculate_cmpy_mults {} {
    puts "Entering [get_proc_name]"
    global c_xdevicefamily
    global APortWidth
    global BPortWidth
    global MultType
    global OptimizeGoal
    global MinimumLatency
    global RoundMode
    
    set lat -1;#[$Latency GetValue]
    set single_output 0
    set has_negate 0
    set round [round_mode_xco_to_sim [$RoundMode GetValue]]

    set mult_count [cmpy_v4_0_utils::cmpy_v4_0_mults $c_xdevicefamily [$APortWidth GetValue] [$BPortWidth GetValue] $lat [mult_type_xco_to_sim [$MultType GetValue]] [optimize_goal_xco_to_sim [$OptimizeGoal GetValue]] $single_output $has_negate $round]
    #puts "mult_count is $mult_count"
    return $mult_count
}

proc calculate_cmpy_latency_report {} {
    puts "Entering [get_proc_name]"
    global c_xdevicefamily
    global APortWidth
    global BPortWidth
    global OutputWidth
    global MultType
    global OptimizeGoal
    global RoundMode
    global AXI_lat_overhead
    
    set lat -1;#[$Latency GetValue]
    set single_output 0
    set has_negate 0
    set round [round_mode_xco_to_sim [$RoundMode GetValue]]
    set OutHigh [ expr { [ $OutputWidth GetValue ] - 1 } ]

    set lat [cmpy_v4_0_utils::cmpy_v4_0_latency $c_xdevicefamily [$APortWidth GetValue] [$BPortWidth GetValue] $OutHigh 0 $lat [mult_type_xco_to_sim [$MultType GetValue]] [optimize_goal_xco_to_sim [$OptimizeGoal GetValue]] $single_output $has_negate $round]

    return $lat
}

proc calc_max_latency {} {
    puts "Entering [get_proc_name]"
    global c_xdevicefamily
    global APortWidth
    global BPortWidth
    global OutputWidth
    global MultType
    global OptimizeGoal
    global RoundMode
    global AXI_lat_overhead
    global InputBehavior
   
    set lat [ calculate_cmpy_latency_report ]

    if { [ $InputBehavior GetValue ] == "Blocking" } {
	set AXI_lat_overhead 9
    } else {
	set AXI_lat_overhead 1
    }
    set maxlat [expr {$lat + $AXI_lat_overhead} ]

    return $maxlat

}


proc get_proc_name {} {
    return [lindex [info level [expr [info level]-1]] 0]
}

#################################################################
#
#   Initialisation Section
#
#   Initialise all XCO parameters and Symbol pins (optional).
#
#################################################################

set Component_Name  [$IpModel AddParam STRING "Component_Name" "complex_multiplier" ]
set APortWidth      [$IpModel AddParam INT    "APortWidth" 16 8 63 ]
set HasATLAST       [$IpModel AddParam BOOL   "HasATLAST" false ]
set HasATUSER       [$IpModel AddParam BOOL   "HasATUSER" false ]
set ATUSERWidth     [$IpModel AddParam INT    "ATUSERWidth" 1 1 256 ]

set BPortWidth      [$IpModel AddParam INT    "BPortWidth" 16 8 63 ]
set HasBTLAST       [$IpModel AddParam BOOL   "HasBTLAST" false ]
set HasBTUSER       [$IpModel AddParam BOOL   "HasBTUSER" false ]
set BTUSERWidth     [$IpModel AddParam INT    "BTUSERWidth" 1 1 256 ]

set MultType        [$IpModel AddParam LIST   "MultType" "Use_Mults" {"Use_LUTs" "Use_Mults"} ]
set OptimizeGoal    [$IpModel AddParam LIST   "OptimizeGoal" "Resources" {"Resources" "Performance"} ]
set InputBehavior   [$IpModel AddParam LIST   "InputBehavior" "Blocking" {"Blocking" "NonBlocking"} ]
set OutputWidth     [$IpModel AddParam INT    "OutputWidth" [ expr { [ $APortWidth GetValue ] + [ $BPortWidth GetValue ] + 1 } ] 2 [ expr { [ $APortWidth GetValue ] + [ $BPortWidth GetValue ] + 1 } ] ]
set RoundMode       [$IpModel AddParam LIST   "RoundMode" "Truncate" {"Truncate" "Random_Rounding"} ]

set HasCTRLTLAST    [$IpModel AddParam BOOL   "HasCTRLTLAST" false ]
set HasCTRLTUSER    [$IpModel AddParam BOOL   "HasCTRLTUSER" false ]
set CTRLTUSERWidth  [$IpModel AddParam INT    "CTRLTUSERWidth" 1 1 256 ]

set OutTLASTBehv    [$IpModel AddParam LIST   "OutTLASTBehv" "Null" {"Null" "Pass_A_TLAST" "Pass_B_TLAST" "Pass_CTRL_TLAST" "OR_all_TLASTs" "AND_all_TLASTs"} ]

set LatencyConfig   [$IpModel AddParam LIST   "LatencyConfig" "Automatic" {"Automatic" "Manual"} ]
set MinimumLatency  [$IpModel AddParam INT    "MinimumLatency" [calc_max_latency] 10 60 ]

set ACLKEN          [$IpModel AddParam BOOL   "ACLKEN"  false ]
set ARESETN         [$IpModel AddParam BOOL   "ARESETN" false ]



#################################################################
# GUI resource estimation
#################################################################
set Mults           [$IpModel AddMetaParam WIDGET "Mults" ]
set LabelMults      [$IpModel AddMetaParam WIDGET "LabelMults" ]
set LatencyReport   [$IpModel AddMetaParam WIDGET "LatencyReport" ]


set doNotCallToSetStubbed 0

# Only need to set the label once for each launch of the GUI
proc calculate_cmpy_label_mults {} {
    puts "Entering [get_proc_name]"
    global has_mult18x18
    global has_dsp48
    
    # Use "Mult18x18s" for Spartan-3, 3E, 3A devices (not 3A-DSP)
    # Use "XtremeDSP slices" for everything else    
    if {$has_mult18x18} {
	set mult_label "Mult18x18s:"
    } else {
	set mult_label "XtremeDSP slices:"
    }

    return $mult_label
}

$LabelMults    SetText [calculate_cmpy_label_mults];
$Mults         SetText -1
$LatencyReport SetText -1

#################################################################
# Set Tooltips
#################################################################
$Component_Name SetToolTip [I18n "Name of the generated complex multiplier core"]
$APortWidth SetToolTip [I18n "Specifies the width of the AR and AI operands"]
$BPortWidth SetToolTip [I18n "Specifies the width of the BR and BI operands"]
$OutputWidth SetToolTip [I18n "Specifies the width of the output product"]
$MinimumLatency SetToolTip [I18n "Selects the minimum latency of the complex multiplier"]
$MultType SetToolTip [I18n "Selects whether LUTs or embedded multipliers / XtremeDSP slices should be used"]
$OptimizeGoal SetToolTip [I18n "Selects if the complex multiplier should be optimized for resources or performance"]
$InputBehavior SetToolTip [I18n "Selects if the complex multiplier will wait for valid data on all input (blocking) or operate when any input is valid (non-blocking)"]
$RoundMode SetToolTip [I18n "Selects if the output should be rounded, using random rounding with ROUND_CY input"]
$ACLKEN SetToolTip [I18n "Selects if the complex multiplier has a clock enable pin"]
$ARESETN SetToolTip [I18n "Selects if the complex multiplier has a synchronous clear pin"]

# Initialise pins (optional)
set pin_s_axis_a_tvalid      [$IpModel AddPin PIN "s_axis_a_tvalid"    LEFT  IN  NET      "NONE" false true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_a_tready      [$IpModel AddPin PIN "s_axis_a_tready"    LEFT  OUT NET      "NONE" true true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_a_tlast       [$IpModel AddPin PIN "s_axis_a_tlast"     LEFT  IN  NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_a_tdata       [$IpModel AddPin PIN "s_axis_a_tdata"     LEFT  IN  BUS 31 0 "NONE" false true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_a_tuser       [$IpModel AddPin PIN "s_axis_a_tuser"     LEFT  IN  BUS 31 0 "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
                              $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_b_tvalid      [$IpModel AddPin PIN "s_axis_b_tvalid"    LEFT  IN  NET      "NONE" false true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_b_tready      [$IpModel AddPin PIN "s_axis_b_tready"    LEFT  OUT NET      "NONE" true  true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_b_tlast       [$IpModel AddPin PIN "s_axis_b_tlast"     LEFT  IN  NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_b_tdata       [$IpModel AddPin PIN "s_axis_b_tdata"     LEFT  IN  BUS 31 0 "NONE" false true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_b_tuser       [$IpModel AddPin PIN "s_axis_b_tuser"     LEFT  IN  BUS 31 0 "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
                              $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_ctrl_tvalid   [$IpModel AddPin PIN "s_axis_ctrl_tvalid" LEFT  IN  NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_ctrl_tready   [$IpModel AddPin PIN "s_axis_ctrl_tready" LEFT  OUT NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_ctrl_tlast    [$IpModel AddPin PIN "s_axis_ctrl_tlast"  LEFT  IN  NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_ctrl_tdata    [$IpModel AddPin PIN "s_axis_ctrl_tdata"  LEFT  IN  BUS  7 0 "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_s_axis_ctrl_tuser    [$IpModel AddPin PIN "s_axis_ctrl_tuser"  LEFT  IN  BUS 31 0 "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
                              $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pinACLK                  [$IpModel AddPin PIN "ACLK"               LEFT  IN  NET      "NONE" true  true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pinACLKEN                [$IpModel AddPin PIN "ACLKEN"             LEFT  IN  NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pinARESETN               [$IpModel AddPin PIN "ARESETN"            LEFT  IN  NET      "NONE" true  false]
                              $IpModel AddPin PIN "XILINX_PIN_SPACE"   LEFT  IN  NET      "NONE" true  true
set pin_m_axis_dout_tvalid   [$IpModel AddPin PIN "m_axis_dout_tvalid" RIGHT OUT NET      "NONE" false true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   RIGHT IN  NET      "NONE" true  true
set pin_m_axis_dout_tready   [$IpModel AddPin PIN "m_axis_dout_tready" RIGHT IN  NET      "NONE" true  true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   RIGHT IN  NET      "NONE" true  true
set pin_m_axis_dout_tlast    [$IpModel AddPin PIN "m_axis_dout_tlast"  RIGHT OUT NET      "NONE" true  false]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   RIGHT IN  NET      "NONE" true  true
set pin_m_axis_dout_tdata    [$IpModel AddPin PIN "m_axis_dout_tdata"  RIGHT OUT BUS 63 0 "NONE" false true]
#                             $IpModel AddPin PIN "XILINX_PIN_SPACE"   RIGHT IN  NET      "NONE" true  true
set pin_m_axis_dout_tuser    [$IpModel AddPin PIN "m_axis_dout_tuser"  RIGHT OUT BUS 31 0 "NONE" true  false]



#################################################################
#
#   Validation Section
#
#   These Tcl procedures are responsible for validating XCO
#   parameter values following an update in the front-end GUI.
#
#   validate_IpModel() is a special case which validates the GUI
#   state as a whole i.e. captures parameter inter-dependencies.
#
#################################################################

# validate parameter inter-dependencies.
proc validate_IpModel {} {
    puts "Entering [get_proc_name]"

   return true
} ;# end validate_IpModel

proc validate_Component_Name {} {
    puts "Entering [get_proc_name]"

   global Component_Name

   return true
} ;# end validate_Component_Name

proc validate_APortWidth {} {
    puts "Entering [get_proc_name]"

   global APortWidth

   set aw [$APortWidth GetValue]
   set aw_min [$APortWidth GetMinValue]
   set aw_max [$APortWidth GetMaxValue]

   if {$aw > $aw_max || $aw < $aw_min} {
       $APortWidth SetErrorMessage [I18n "APortWidth is out of range $aw_min to $aw_max: Value is $aw"]
       return false
   }

   return true
} ;# end validate_APortWidth

proc validate_ATUSERWidth {} {
    puts "Entering [get_proc_name]"
    global ATUSERWidth

    return true
} ;# end validate_ATUSERWidth

proc validate_BPortWidth {} {
    puts "Entering [get_proc_name]"

   global BPortWidth

   set bw [$BPortWidth GetValue]
   set bw_min [$BPortWidth GetMinValue]
   set bw_max [$BPortWidth GetMaxValue]

   if {$bw > $bw_max || $bw < $bw_min} {
       $BPortWidth SetErrorMessage [I18n "BPortWidth is out of range $bw_min to $bw_max: Value is $bw"]
       return false
   }

   return true
} ;# end validate_BPortWidth

proc validate_BTUSERWidth {} {
    puts "Entering [get_proc_name]"

    return true;
} ;# end validate_BTUSERWidth

proc validate_OutputWidth {} {
    puts "Entering [get_proc_name]"

    global APortWidth
    global BPortWidth
    global OutputWidth

    $OutputWidth SetMaxValue [ expr { [ $APortWidth GetValue ] + [ $BPortWidth GetValue ] + 1 } ]
    set owh [$OutputWidth GetValue]
    set owh_max [$OutputWidth GetMaxValue]

    if {$owh < 0 || $owh > $owh_max} {
	$OutputWidth SetErrorMessage [I18n "OutputWidth is out of range 0 to $owh_max: Value is $owh"]
	return false
    }

   return true
} ;# end validate_OutputWidth

proc validate_MinimumLatency {} {
    puts "Entering [get_proc_name]"

   global MinimumLatency

   return true
} ;# end validate_MinimumLatency

proc validate_MultType {} {
    puts "Entering [get_proc_name]"

   global MultType

   set mt [$MultType GetValue]

   if {$mt != "Use_LUTs" && $mt != "Use_Mults"} {
       $MultType SetErrorMessage [I18n "MultType is incorrect.  Allowed values are Use_LUTs and Use_Mults.  Value is [$MultType GetValue]"]
       return false
   } 

   return true
} ;# end validate_MultType

proc validate_OptimizeGoal {} {
    puts "Entering [get_proc_name]"

   global OptimizeGoal

   set og [$OptimizeGoal GetValue]

   if {$og != "Resources" && $og != "Performance"} {
       $OptimizeGoal SetErrorMessage [I18n "OptimizeGoal is incorrect.  Allowed values are Resources and Performance.  Value is [$OptimizeGoal GetValue]"]
       return false
   }

   return true
} ;# end validate_OptimizeGoal

proc validate_InputBehavior {} {
    puts "Entering [get_proc_name]"

   global InputBehavior

   set og [$InputBehavior GetValue]

#   if {$og != "Blocking" && $og != "NonBlocking"} {
#       $InputBehavior SetErrorMessage [I18n "InputBehavior is incorrect.  Allowed values are Blocking and NonBlocking.  Value is [$InputBehavior GetValue]"]
#       return false
#   }

   return true
} ;# end validate_InputBehavior

proc validate_OutTLASTBehv {} {
    global HasATLAST
    global HasBTLAST
    global HasCTRLTLAST
    global OutTLASTBehv

    set A [$HasATLAST GetValue]
    set B [$HasBTLAST GetValue]
    set C [$HasCTRLTLAST GetValue]
    set O [$OutTLASTBehv GetValue]

    if {$A == true} {
	if {$B == true} {
	    if {$C == true} {
		if {$O == "Null"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    } else {
		if {$O == "Null" || $O == "Pass_CTRL_TLAST"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    }
	} else {
	    if {$C == true} {
		if {$O == "Null" || $O == "Pass_B_TLAST"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    } else {
		if {$O != "Pass_A_TLAST"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    }
	}
    } else {
	if {$B == true} {
	    if {$C == true} {
		if {$O == "Null" || $O == "Pass_A_TLAST"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    } else {
		if {$O != "Pass_B_TLAST"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    }
	} else {
	    if {$C == true} {
		if {$O != "Pass_CTRL_TLAST"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    } else {
		if {$O != "Null"} {
		    $OutTLASTBehv SetErrorMessage [I18n "OutTLASTBehv is incorrect. Value is $O"]
		    return false
		}
	    }
	}
    }
    return true    
} ;#end validate_OutTLASTBehv

proc validate_RoundMode {} {
    puts "Entering [get_proc_name]"

   global RoundMode

   set rm [$RoundMode GetValue]

   if {$rm != "Truncate" && $rm != "Random_Rounding" } {
       $RoundMode SetErrorMessage [I18n "RoundMode is incorrect.  Allowed values are Truncate and Random_Rounding.  Value is [$RoundMode GetValue]"]
       return false
   }

   return true
} ;# end validate_RoundMode

proc validate_CTRLTUSERWidth {} {
    puts "Entering [get_proc_name]"

    return true;
} ;# end validate_CTRLTUSERWidth

proc validate_ACLKEN {} {
    puts "Entering [get_proc_name]"

   global ACLKEN

   set cen [$ACLKEN GetValue]

   if {$cen != true && $cen != false} {
       $ACLKEN SetErrorMessage [I18n "ACLKEN is incorrect.  Allowed values are true and false.  Value is [$ACLKEN GetValue]"]
       return false
   } 

   return true
} ;# end validate_ACLKEN

proc validate_ARESETN {} {
    puts "Entering [get_proc_name]"

   global ARESETN
 
   set sc [$ARESETN GetValue]

   if {$sc != true && $sc != false} {
       $ARESETN SetErrorMessage [I18n "ARESETN is incorrect.  Allowed values are true and false.  Value is [$ARESETN GetValue]"]
       return false
   } 

   return true
} ;# end validate_ARESETN



#################################################################
#
#   Update Section
#
#   These Tcl procedures are responsible for updating XCO
#   parameters and their associated state (e.g. enabled/disabled)
#   following an update in the front-end GUI.
#
#################################################################

proc update_Component_Name {} {
    puts "Entering [get_proc_name]"

   global Component_Name

   # Only update this parameter's state

} ;# end update_Component_Name

proc update_APortWidth {} {
    puts "Entering [get_proc_name]"

   global APortWidth

   # Only update this parameter's state

} ;# end update_APortWidth

proc update_ATUSERWidth {} {
    puts "Entering [get_proc_name]"

    global ATUSERWidth
    global HasATUSER

    if { [$HasATUSER GetValue] } {
	$ATUSERWidth SetEnabled true
    } else {
	$ATUSERWidth SetEnabled false
    }

   # Only update this parameter's state

} ;# end update_ATUSERWidth

proc update_BPortWidth {} {
    puts "Entering [get_proc_name]"

   global BPortWidth

   # Only update this parameter's state

} ;# end update_BPortWidth

proc update_BTUSERWidth {} {
    puts "Entering [get_proc_name]"

    global BTUSERWidth
    global HasBTUSER

    if { [$HasBTUSER GetValue] } {
	$BTUSERWidth SetEnabled true
    } else {
	$BTUSERWidth SetEnabled false
    }

   # Only update this parameter's state

} ;# end update_BTUSERWidth

proc update_OutputWidth {} {
    puts "Entering [get_proc_name]"

    global APortWidth
    global BPortWidth
    global OutputWidth

    $OutputWidth SetMaxValue [ expr { [ $APortWidth GetValue ] + [ $BPortWidth GetValue ] + 1 } ]
    # Only update this parameter's state
    $OutputWidth SetValue [ expr { [ $APortWidth GetValue ] + [ $BPortWidth GetValue ] + 1 } ] 

} ;# end update_OutputWidth

proc update_OutTLASTBehv {} {
    puts "Entering [get_proc_name]"
    global HasATLAST
    global HasBTLAST
    global HasCTRLTLAST
    global OutTLASTBehv

    if { [$HasATLAST GetValue] == true } {
	if { [$HasBTLAST GetValue] == true } {
	    if { [$HasCTRLTLAST GetValue] == true } {
		$OutTLASTBehv SetEnabled true
		$OutTLASTBehv SetEnabled false "Null"
		$OutTLASTBehv SetEnabled true "Pass_A_TLAST"
		$OutTLASTBehv SetEnabled true "Pass_B_TLAST"
		$OutTLASTBehv SetEnabled true "Pass_CTRL_TLAST"
		$OutTLASTBehv SetEnabled true "OR_all_TLASTs"
		$OutTLASTBehv SetEnabled true "AND_all_TLASTs"
                $OutTLASTBehv SetValue "Pass_A_TLAST"
	    } else {
		$OutTLASTBehv SetEnabled true
		$OutTLASTBehv SetEnabled false "Null"
		$OutTLASTBehv SetEnabled true "Pass_A_TLAST"
		$OutTLASTBehv SetEnabled true "Pass_B_TLAST"
		$OutTLASTBehv SetEnabled false "Pass_CTRL_TLAST"
		$OutTLASTBehv SetEnabled true "OR_all_TLASTs"
		$OutTLASTBehv SetEnabled true "AND_all_TLASTs"
                $OutTLASTBehv SetValue "Pass_A_TLAST"
	    }
	} else {
	    if { [$HasCTRLTLAST GetValue] == true } {
		$OutTLASTBehv SetEnabled true
		$OutTLASTBehv SetEnabled false "Null"
		$OutTLASTBehv SetEnabled true "Pass_A_TLAST"
		$OutTLASTBehv SetEnabled false "Pass_B_TLAST"
		$OutTLASTBehv SetEnabled true "Pass_CTRL_TLAST"
		$OutTLASTBehv SetEnabled true "OR_all_TLASTs"
		$OutTLASTBehv SetEnabled true "AND_all_TLASTs"
                $OutTLASTBehv SetValue "Pass_A_TLAST"
	    } else {
		$OutTLASTBehv SetEnabled false
                $OutTLASTBehv SetValue "Pass_A_TLAST"
	    }
	}
    } else {
	if { [$HasBTLAST GetValue] == true } {
	    if { [$HasCTRLTLAST GetValue] == true } {
		$OutTLASTBehv SetEnabled true
		$OutTLASTBehv SetEnabled false "Null"
		$OutTLASTBehv SetEnabled false "Pass_A_TLAST"
		$OutTLASTBehv SetEnabled true "Pass_B_TLAST"
		$OutTLASTBehv SetEnabled true "Pass_CTRL_TLAST"
		$OutTLASTBehv SetEnabled true "OR_all_TLASTs"
		$OutTLASTBehv SetEnabled true "AND_all_TLASTs"
                $OutTLASTBehv SetValue "Pass_B_TLAST"
	    } else {
		$OutTLASTBehv SetEnabled false
                $OutTLASTBehv SetValue "Pass_B_TLAST"
	    }
	} else {
	    if { [$HasCTRLTLAST GetValue] == true } {
		$OutTLASTBehv SetEnabled false 
                $OutTLASTBehv SetValue "Pass_CTRL_TLAST"
	    } else {
		$OutTLASTBehv SetEnabled false 
                $OutTLASTBehv SetValue "Null"
	    }
	}
    }
}

proc update_LatencyConfig {} {
    puts "Entering [get_proc_name]"

   global LatencyConfig
   # Only update this parameter's state

} ;# end update_LatencyConfig

proc update_MinimumLatency {} {
    puts "Entering [get_proc_name]"

    global LatencyConfig
    global MinimumLatency
    global InputBehavior
    global AXI_lat_overhead

    set maxcorelatency [calculate_cmpy_latency_report]
    if { [ $InputBehavior GetValue ] == "Blocking"} {
	set maxval [ expr { $maxcorelatency + $AXI_lat_overhead } ]
    } else {
	set maxval [ expr { $maxcorelatency + 1 } ]
    }

    # Only update this parameter's state
    if { [ $InputBehavior GetValue ] == "Blocking" } {
	$MinimumLatency SetMinValue $AXI_lat_overhead
    } else {
	$MinimumLatency SetMinValue 0
    }
#  $MinimumLatency SetMaxValue $maxval  #maxval is always 60 - cmpy core will pad if overallocated.
#  however, the auto latency should be set to the fully, but not over-pipelined.
    if { [$LatencyConfig GetValue] == "Automatic" } {
	$MinimumLatency SetEnabled false
	$MinimumLatency SetValue $maxval 
    } else {
	$MinimumLatency SetEnabled true
    }
    
} ;# end update_MinimumLatency


proc update_MultType {} {
    puts "Entering [get_proc_name]"

   global MultType

   # Only update this parameter's state

} ;# end update_MultType

# Update latency drop-down value when MultType is changed
proc finalize_MultType {} {


}

proc update_OptimizeGoal {} {
     puts "Entering [get_proc_name]"
   
    global MultType
    global OptimizeGoal
    
    # Only update this parameter's state
    
    # 3-mult structure is cheaper if we use LUTs
    #G.Old - below consideration now handled in translate.
#    if {[$MultType GetValue] == "Use_LUTs" } {
#  	$OptimizeGoal SetEnabled false
#  	$OptimizeGoal SetValue "Resources"
#    } else {
# 	$OptimizeGoal SetEnabled true
#    }
    
} ;# end update_OptimizeGoal

proc update_InputBehavior {} {
     puts "Entering [get_proc_name]"
   
    global InputBehavior

    
} ;# end update_InputBehavior

# Update latency drop-down value when OptimizeGoal is changed
proc finalize_OptimizeGoal {} {
    puts "Entering [get_proc_name]"

}

proc update_RoundMode {} {
    puts "Entering [get_proc_name]"

   global APortWidth
   global BPortWidth
   global OutputWidth
   global RoundMode

   # Only update this parameter's state
   if { [ $OutputWidth GetValue ] == [ expr { [ $APortWidth GetValue ] + [ $BPortWidth GetValue ] + 1 } ] } {
       $RoundMode SetEnabled false
       $RoundMode SetValue "Truncate"
   } else {
       $RoundMode SetEnabled true
   }

} ;# end update_RoundMode

# Update latency drop-down value when RoundMode is changed
proc finalize_RoundMode {} {
    puts "Entering [get_proc_name]"


}

proc update_HasCTRLTUSER {} {
    puts "Entering [get_proc_name]"

   global HasCTRLTUSER
   global RoundMode

   if {[$RoundMode GetValue] == "Random_Rounding"} {
       $HasCTRLTUSER SetEnabled true
   } else {
       $HasCTRLTUSER SetEnabled false
       $HasCTRLTUSER SetValue false       
   }
   # Only update this parameter's state

} ;# end update_HasCTRLTUSER

proc update_HasCTRLTLAST {} {
    puts "Entering [get_proc_name]"

   global HasCTRLTLAST
   global RoundMode

   if {[$RoundMode GetValue] == "Random_Rounding"} {
       $HasCTRLTLAST SetEnabled true
   } else {
       $HasCTRLTLAST SetEnabled false
       $HasCTRLTLAST SetValue false       
   }
   # Only update this parameter's state

} ;# end update_HasCTRLTLAST

proc update_CTRLTUSERWidth {} {
    puts "Entering [get_proc_name]"

    global CTRLTUSERWidth
    global HasCTRLTUSER

    if { [$HasCTRLTUSER GetValue] } {
	$CTRLTUSERWidth SetEnabled true
    } else {
	$CTRLTUSERWidth SetEnabled false
    }

  # Only update this parameter's state

} ;# end update_CTRLTUSERWidth


proc update_ACLKEN {} {
    puts "Entering [get_proc_name]"

    global ACLKEN
    global MinimumLatency

    if {[$MinimumLatency GetValue] == 0} {
	$ACLKEN SetEnabled false
	$ACLKEN SetValue false
    } else {
	$ACLKEN SetEnabled true
    }

} ;# end update_ACLKEN

proc update_ARESETN {} {
    puts "Entering [get_proc_name]"

    global ARESETN
    global MinimumLatency

    if {[$MinimumLatency GetValue] == 0} {
	$ARESETN SetEnabled false
	$ARESETN SetValue false
    } else {
	$ARESETN SetEnabled true
    }

} ;# end update_ARESETN

#anindit - test code for cmpy_v4_0 begins
proc update_pins {} {
   puts "Entering [get_proc_name]"

   # Declare global parameters used.  
   global APortWidth
   global BPortWidth
   global OutputWidth
   global ACLKEN
   global ARESETN
   global MinimumLatency
   global InputBehavior
   global RoundMode
   global ATUSERWidth
   global BTUSERWidth
   global CTRLTUSERWidth
   global HasATUSER
   global HasBTUSER
   global HasCTRLTUSER
   global HasATLAST
   global HasBTLAST
   global HasCTRLTLAST

   # Check globals before using them, otherwise we can get pin configuration errors 
   if {![GlobalsValid]} {
       #anindit
       #return TRUE
       return true
   }
   
   update_pins_struct

   update_pins_prop

   return true
}
#anindit - test code for cmpy_v4_0 ends

proc update_pins_prop {} {
    puts "Entering [get_proc_name]"

   # Declare global parameters used.  
    global APortWidth
    global BPortWidth
    global OutputWidth
    global ACLKEN
    global ARESETN
    global MinimumLatency
    global InputBehavior
    global RoundMode
    global ATUSERWidth
    global BTUSERWidth
    global CTRLTUSERWidth
    global HasATUSER
    global HasBTUSER
    global HasCTRLTUSER
    global HasATLAST
    global HasBTLAST
    global HasCTRLTLAST

   # Check globals before using them, otherwise we can get pin configuration errors 
   if {![GlobalsValid]} {
       #anindit
       #return TRUE
       return true
   }

  # Declare global pins used.
    global pin_s_axis_a_tvalid    
    global pin_s_axis_a_tready    
    global pin_s_axis_a_tlast     
    global pin_s_axis_a_tdata     
    global pin_s_axis_a_tuser     
    global pin_s_axis_b_tvalid    
    global pin_s_axis_b_tready    
    global pin_s_axis_b_tlast     
    global pin_s_axis_b_tdata     
    global pin_s_axis_b_tuser     
    global pin_s_axis_ctrl_tvalid 
    global pin_s_axis_ctrl_tready 
    global pin_s_axis_ctrl_tlast  
    global pin_s_axis_ctrl_tdata  
    global pin_s_axis_ctrl_tuser  
    global pinACLK                 
    global pinACLKEN              
    global pinARESETN             
    global pin_m_axis_dout_tvalid 
    global pin_m_axis_dout_tready 
    global pin_m_axis_dout_tlast  
    global pin_m_axis_dout_tdata  
    global pin_m_axis_dout_tuser  

    if { [$MinimumLatency GetValue] == 0 } {
	$pinACLK    SetEnabled false
    } else {
	$pinACLK    SetEnabled true
    }


    set atdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $APortWidth GetValue ] 8 ] * 2 } ]
    set btdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $BPortWidth GetValue ] 8 ] * 2 } ]
    set ctrltdatawidth 8
    set douttdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $OutputWidth GetValue ] 8 ] * 2 } ]

    if {[$InputBehavior GetValue] == "Blocking"} {
	$pin_s_axis_a_tready    SetEnabled true
	$pin_s_axis_b_tready    SetEnabled true
	$pin_m_axis_dout_tready SetEnabled true
    } else {
	$pin_s_axis_a_tready    SetEnabled false
	$pin_s_axis_b_tready    SetEnabled false
	$pin_m_axis_dout_tready SetEnabled false
    }

    if { [ $HasATUSER GetValue ] } {
	if { [ $HasBTUSER GetValue ] } {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] + [$BTUSERWidth GetValue] + [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] + [$BTUSERWidth GetValue] } ]
	    }
	} else {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] + [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] } ]
	    }
	}
    } else {
	if {[$HasBTUSER GetValue]} {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$BTUSERWidth GetValue] + [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth [ expr { [$BTUSERWidth GetValue] } ]
	    }
	} else {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth 1
	    }
	}
    }

    #Visibility of TLAST pins
    if { [ $HasATLAST GetValue ] } {
	$pin_s_axis_a_tlast SetEnabled true
    } else {
	$pin_s_axis_a_tlast SetEnabled false
    }
    
    if { [ $HasBTLAST GetValue ] } {
	$pin_s_axis_b_tlast SetEnabled true
    } else {
	$pin_s_axis_b_tlast SetEnabled false
    }
    
    if { [ $HasCTRLTLAST GetValue ] } {
	$pin_s_axis_ctrl_tlast SetEnabled true
    } else {
	$pin_s_axis_ctrl_tlast SetEnabled false
    }
    
    #Visibility of TUSER buses
    if { [ $HasATUSER GetValue ] } {
	$pin_s_axis_a_tuser SetEnabled true
    } else {
	$pin_s_axis_a_tuser SetEnabled false
    }
    
    if { [ $HasBTUSER GetValue ] } {
	$pin_s_axis_b_tuser SetEnabled true
    } else {
	$pin_s_axis_b_tuser SetEnabled false
    }
    
    if { [ $HasCTRLTUSER GetValue ] } {
	$pin_s_axis_ctrl_tuser SetEnabled true
    } else {
	$pin_s_axis_ctrl_tuser SetEnabled false
    }
    
    $pin_s_axis_a_tdata SetIndices [ expr { $atdatawidth -1 } ] 0
    $pin_s_axis_a_tuser SetIndices [ expr { [ $ATUSERWidth GetValue ] -1 } ] 0
    $pin_s_axis_b_tdata SetIndices [ expr { $btdatawidth -1 } ] 0
    $pin_s_axis_b_tuser SetIndices [ expr { [ $BTUSERWidth GetValue ] -1 } ] 0
    $pin_s_axis_ctrl_tuser SetIndices [ expr { [$CTRLTUSERWidth GetValue ] -1 } ] 0

    $pin_m_axis_dout_tdata SetIndices [ expr { $douttdatawidth - 1 } ] 0
    $pin_m_axis_dout_tuser SetIndices [ expr { $douttuserwidth - 1 } ] 0

    #anindit - update width of tdata-subfields through this method
    update_tdata_pin

    #pin_m_axis_dout_tlast
    if { [ $HasATLAST GetValue ] || [$HasBTLAST GetValue] || [$HasCTRLTLAST GetValue] } {
	$pin_m_axis_dout_tlast SetEnabled true
    } else {
	$pin_m_axis_dout_tlast SetEnabled false
    }
    
    #pin_m_axis_dout_tuser
    if { [ $HasATUSER GetValue ] || [$HasBTUSER GetValue] || [$HasCTRLTUSER GetValue] } {
	$pin_m_axis_dout_tuser SetEnabled true
    } else {
	$pin_m_axis_dout_tuser SetEnabled false
    }
   
    #CTRL channel pins
    if { [$RoundMode GetValue] == "Random_Rounding" } {
	$pin_s_axis_ctrl_tvalid SetEnabled true
        if {[$InputBehavior GetValue] == "Blocking"} {
	    $pin_s_axis_ctrl_tready SetEnabled true
	} else {
	    $pin_s_axis_ctrl_tready SetEnabled false
	}
	$pin_s_axis_ctrl_tdata SetEnabled true
	if { [ $HasCTRLTLAST GetValue ] } {
	    $pin_s_axis_ctrl_tlast SetEnabled true
	}
	if { [ $HasCTRLTUSER GetValue ] } {
	    $pin_s_axis_ctrl_tuser SetEnabled true
	}
    } else {
	$pin_s_axis_ctrl_tvalid SetEnabled false
	$pin_s_axis_ctrl_tready SetEnabled false
	$pin_s_axis_ctrl_tdata SetEnabled false
	$pin_s_axis_ctrl_tlast SetEnabled false
	$pin_s_axis_ctrl_tuser SetEnabled false
    }

    if { [$ACLKEN GetValue] == true } {
	$pinACLKEN SetEnabled true
    } else {
	$pinACLKEN SetEnabled false
    }

    if { [$ARESETN GetValue] == true } {
	$pinARESETN SetEnabled true
    } else {
	$pinARESETN SetEnabled false
    }

    #anindit
    #return TRUE
    return true
}

#anindit - create/update/compute methods for cmpy_v4_0 begin

proc createOneStubbedAXItdataInfo { real_name imag_name width } {
    upvar $width portwidth

    set axiClassName "Streaming" 
    set dataClassName "Complex"
    set arithtype "signed"
    set binpt 0
    set period -1
    set subFieldType "atomic"

    set atdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $portwidth GetValue ] 8 ] * 2 } ]
    set subfieldwidth [ $portwidth GetValue ]
    set subfieldactualwidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $portwidth GetValue ] 8 ] } ]
    set a_tdata_re [ AXISysgenSubfieldsUtil::buildOneAXItdataSubFieldInfo $real_name $subFieldType $subfieldwidth $subfieldactualwidth $arithtype $binpt $period]
    set a_tdata_imag [ AXISysgenSubfieldsUtil::buildOneAXItdataSubFieldInfo $imag_name $subFieldType $subfieldwidth $subfieldactualwidth $arithtype $binpt $period]
    set a_tdata_subfield_info [list $a_tdata_imag $a_tdata_re]
    set a_tdata [ AXISysgenSubfieldsUtil::buildOneAXItdataInfo $axiClassName $dataClassName $atdatawidth $a_tdata_subfield_info]

    return $a_tdata
}

proc setStubbedAXItdataInfo {} {
    global APortWidth
    global BPortWidth
    global OutputWidth
    global pin_s_axis_a_tdata
    global pin_s_axis_b_tdata
    global pin_m_axis_dout_tdata

    set re_name "re"
    set imag_name "imag"
    set a_tdata [createOneStubbedAXItdataInfo "s_axis_a_tdata_$re_name" "s_axis_a_tdata_$imag_name" APortWidth]
    $pin_s_axis_a_tdata SetProperty [ AXISysgenSubfieldsUtil::tdata_info] $a_tdata

    set b_tdata [createOneStubbedAXItdataInfo "s_axis_b_tdata_$re_name" "s_axis_b_tdata_$imag_name" BPortWidth]
    $pin_s_axis_b_tdata SetProperty [ AXISysgenSubfieldsUtil::tdata_info] $b_tdata

    set dout_tdata [createOneStubbedAXItdataInfo "m_axis_dout_tdata_$re_name" "m_axis_dout_tdata_$imag_name" OutputWidth]
    $pin_m_axis_dout_tdata SetProperty [ AXISysgenSubfieldsUtil::tdata_info] $dout_tdata

    return true
}

proc update_pins_struct {} {
    global doNotCallToSetStubbed 
    #tdata_info is not currently present at tdata_pins, hence creating stubbed data
    if { $doNotCallToSetStubbed != 1} {
        setStubbedAXItdataInfo
    }
    set doNotCallToSetStubbed 0
    return true
}

proc update_tdata_pin {} {
    global APortWidth
    global BPortWidth
    global OutputWidth
    global pin_s_axis_a_tdata
    global pin_s_axis_b_tdata
    global pin_m_axis_dout_tdata

    #for now updating only the subFieldWidth value - if anything else also
    #needs to be updated, then that should be done as well
    #IP-developer should check and correct this logic, if need be.
    #
    #for cmpy_v4_0, subfields are atomic, for IPs with composite tdata subfields,
    #the recursive algorithm may be like -
    #     foreach subfield
    #        if atomic subfield
    #           update width, based on proper rule
    #           update any-other-field ...
    #        else
    #            invoke itself with the subfieldlist(using getSubFieldInfoList) of the current subfield
    #
    set atdatasubfieldwidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $APortWidth GetValue ] 8 ] } ]
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_re" "subFieldActualWidth" $atdatasubfieldwidth 
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_imag" "subFieldActualWidth" $atdatasubfieldwidth
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_re" "subFieldWidth" [$APortWidth GetValue]
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_imag" "subFieldWidth" [$APortWidth GetValue]
    ######################################################################################################
    # Arvind's Comments
    # Also Interface Width Should Get Updated Here - Need a function to do that in SubfieldsUtil
    ######################################################################################################
    set atdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $APortWidth GetValue ] 8 ] * 2 } ]
    AXISysgenSubfieldsUtil::setInterfaceWidth pin_s_axis_a_tdata $atdatawidth
 
    set btdatasubfieldwidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $BPortWidth GetValue ] 8 ] } ]
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_re" "subFieldActualWidth" $btdatasubfieldwidth
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_imag" "subFieldActualWidth" $btdatasubfieldwidth
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_re" "subFieldWidth" [$BPortWidth GetValue]
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_imag" "subFieldWidth" [$BPortWidth GetValue]
    set btdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $BPortWidth GetValue ] 8 ] * 2 } ]
    AXISysgenSubfieldsUtil::setInterfaceWidth pin_s_axis_b_tdata $btdatawidth

    set douttdatasubfieldwidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $OutputWidth GetValue ] 8 ] } ]
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_re" "subFieldActualWidth" $douttdatasubfieldwidth
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_imag" "subFieldActualWidth" $douttdatasubfieldwidth
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_re" "subFieldWidth" [$OutputWidth GetValue]
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_imag" "subFieldWidth" [$OutputWidth GetValue]
    set douttdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $OutputWidth GetValue ] 8 ] * 2 } ]
    AXISysgenSubfieldsUtil::setInterfaceWidth pin_m_axis_dout_tdata $douttdatawidth
    return true
}

#anindit - create/update/compute methods end

proc finalize {} {
    puts "Entering [get_proc_name]"

    global LabelMults
    global Mults
    global LatencyReport
    global HasATLAST
    global HasBTLAST
    global HasCTRLTLAST
    global HasATUSER
    global HasBTUSER
    global HasCTRLTUSER
    global OutputWidth
    global APortWidth
    global BPortWidth
   
    set multsText       [calculate_cmpy_mults]
   #   set latencyText     [calculate_cmpy_latency_report]
    set latencyText [calc_max_latency]
   
    $Mults         SetText $multsText
    $LatencyReport SetText $latencyText

}

#################################################################
#
#   Translation Section
#
#   These Tcl procedures are responsible for XCO to SIM parameter
#   translation performed on core Generation only.
#
#################################################################

proc translate_Component_Name {xcoValue} {
    puts "Entering [get_proc_name]"

    global write_test_files

    if {$write_test_files == true} {
	# hijack this translate subroutine to write out the resource estimates to file core_resources.txt
	global IpModel

	set Mults "[calculate_cmpy_mults]"
	
	set mult_count [I18n "Mults: $Mults"]
	set resourcefileresult [$IpModel WriteTextFile "core_resources.txt" $mult_count]
	
	# now write out latency values
	set lat "[calculate_cmpy_latency_report]"
	set lat [I18n "latency: $lat"]
	
	set latencyfileresults [ $IpModel WriteTextFile "gui_latency.txt" $lat ]
    }

    lappend simList "Component_Name $xcoValue"
    
    return $simList
} ;# end translate_Component_Name

proc translate_APortWidth {xcoValue} {
    puts "Entering [get_proc_name]"

   lappend simList "c_a_width $xcoValue"

   return $simList
} ;# end translate_APortWidth

proc translate_BPortWidth {xcoValue} {
    puts "Entering [get_proc_name]"

   lappend simList "c_b_width $xcoValue"

   return $simList
} ;# end translate_BPortWidth

proc translate_OutputWidth {xcoValue} {
    global OutputWidth
    global HasATUSER
    global HasBTUSER
    global HasCTRLTUSER
    global ATUSERWidth
    global BTUSERWidth
    global CTRLTUSERWidth


    puts "Entering [get_proc_name]"

    lappend simList "c_out_width $xcoValue"

    if { [ $HasATUSER GetValue ] } {
	if { [ $HasBTUSER GetValue ] } {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] + [$BTUSERWidth GetValue] + [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] + [$BTUSERWidth GetValue] } ]
	    }
	} else {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] + [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth [ expr { [$ATUSERWidth GetValue] } ]
	    }
	}
    } else {
	if {[$HasBTUSER GetValue]} {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$BTUSERWidth GetValue] + [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth [ expr { [$BTUSERWidth GetValue] } ]
	    }
	} else {
	    if { [ $HasCTRLTUSER GetValue ] } {
		set douttuserwidth [ expr { [$CTRLTUSERWidth GetValue] } ]
	    } else {
		set douttuserwidth 1
	    }
	}
    }
    lappend simList "c_m_axis_dout_tuser_width $douttuserwidth"
    set douttdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $OutputWidth GetValue ] 8 ] * 2 } ]
    lappend simList "c_m_axis_dout_tdata_width $douttdatawidth"
    return $simList

   return $simList
} ;# end translate_OutputWidth

proc translate_MinimumLatency {xcoValue} {
    puts "Entering [get_proc_name]"

    lappend simList "c_latency $xcoValue"

    return $simList
} ;# end translate_MinimumLatency

proc translate_MultType {xcoValue} {
    puts "Entering [get_proc_name]"

    if {$xcoValue == "Use_Mults"} {
	lappend simList "c_mult_type 1"
    } else {
	lappend simList "c_mult_type 0"
    }

   return $simList
} ;# end translate_MultType

proc translate_OptimizeGoal {xcoValue} {
    puts "Entering [get_proc_name]"
    global MultType

    if {$xcoValue == "Performance"} {
	if {[$MultType GetValue] == "Use_LUTs"} {
	    lappend simList "c_optimize_goal 0"
	} else {
	    lappend simList "c_optimize_goal 1"
	}
    } else {
	lappend simList "c_optimize_goal 0"
    }

   return $simList
} ;# end translate_OptimizeGoal

proc translate_InputBehavior {xcoValue} {
    puts "Entering [get_proc_name]"

    global OptimizeGoal
    global ACLKEN

    set Opt [$OptimizeGoal GetValue]

    if {$xcoValue == "Blocking"} {
	if {($Opt == "Performance") && ([$ACLKEN GetValue] == true) } {
	    lappend simList "c_throttle_scheme 2"
	} else {
	    lappend simList "c_throttle_scheme 1"
	}
    } else {
	lappend simList "c_throttle_scheme 0"
    }

   return $simList
} ;# end translate_InputBehavior

proc translate_RoundMode {xcoValue} {
    puts "Entering [get_proc_name]"
    
    if {$xcoValue == "Random_Rounding"} {
	lappend simList "round 1"
    } else {
	lappend simList "round 0"
    }
    
    return $simList
} ;# end translate_RoundMode

proc translate_ACLKEN {xcoValue} {
    puts "Entering [get_proc_name]"

    if {$xcoValue == true} {
	lappend simList "c_has_aclken 1"
    } else {
	lappend simList "c_has_aclken 0"
    }

   return $simList
} ;# end translate_ACLKEN

proc translate_ARESETN {xcoValue} {
    puts "Entering [get_proc_name]"

    if {$xcoValue == true} {
	lappend simList "c_has_aresetn 1"
    } else {
	lappend simList "c_has_aresetn 0"
    }

   return $simList
} ;# end translate_ARESETN

proc translate_HasATUSER {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == true} {
	lappend simList "c_has_s_axis_a_tuser 1"
    } else {
	lappend simList "c_has_s_axis_a_tuser 0"
    }

   return $simList

} ;# end translate_HasATUSER

proc translate_HasATLAST {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == true} {
	lappend simList "c_has_s_axis_a_tlast 1"
    } else {
	lappend simList "c_has_s_axis_a_tlast 0"
    }

   return $simList
} ;# end translate_HasATLAST

proc translate_HasBTUSER {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == true} {
	lappend simList "c_has_s_axis_b_tuser 1"
    } else {
	lappend simList "c_has_s_axis_b_tuser 0"
    }

   return $simList
} ;# end translate_HasBTUSER

proc translate_HasBTLAST {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == true} {
	lappend simList "c_has_s_axis_b_tlast 1"
    } else {
	lappend simList "c_has_s_axis_b_tlast 0"
    }

   return $simList
} ;# end translate_HasBTLAST

proc translate_HasCTRLTUSER {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == true} {
	lappend simList "c_has_s_axis_ctrl_tuser 1"
    } else {
	lappend simList "c_has_s_axis_ctrl_tuser 0"
    }

   return $simList
} ;# end translate_HasCTRLTUSER

proc translate_HasCTRLTLAST {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == true} {
	lappend simList "c_has_s_axis_ctrl_tlast 1"
    } else {
	lappend simList "c_has_s_axis_ctrl_tlast 0"
    }

   return $simList
} ;# end translate_HasCTRLTLAST

proc translate_OutTLASTBehv {xcoValue} {
    puts "Entering [get_proc_name]"
    if {$xcoValue == "Pass_A_TLAST"} {
	lappend simList "c_tlast_resolution 1"
    } elseif {$xcoValue == "Pass_B_TLAST"} {
	lappend simList "c_tlast_resolution 2"
    } elseif {$xcoValue == "Pass_CTRL_TLAST"} {
	lappend simList "c_tlast_resolution 3"
    } elseif {$xcoValue == "OR_all_TLASTs"} {
	lappend simList "c_tlast_resolution 16"
    } elseif {$xcoValue == "AND_all_TLASTs"} {
	lappend simList "c_tlast_resolution 17"
    } else {
	lappend simList "c_tlast_resolution 0"
    }
    return $simList
} ;# end translate_OutTLASTBehv

proc translate_ATUSERWidth {xcoValue} {
    global APortWidth

    puts "Entering [get_proc_name]"
    lappend simList "c_s_axis_a_tuser_width $xcoValue"
    set atdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $APortWidth GetValue ] 8 ] * 2 } ]
    lappend simList "c_s_axis_a_tdata_width $atdatawidth"

    return $simList
} ;# end translate_ATUSERWidth

proc translate_BTUSERWidth {xcoValue} {
    global BPortWidth

    puts "Entering [get_proc_name]"
    lappend simList "c_s_axis_b_tuser_width $xcoValue"
    set btdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $BPortWidth GetValue ] 8 ] * 2 } ]
    lappend simList "c_s_axis_b_tdata_width $btdatawidth"

    return $simList
} ;# end translate_BTUSERWidth

proc translate_CTRLTUSERWidth {xcoValue} {
    puts "Entering [get_proc_name]"
    lappend simList "c_s_axis_ctrl_tdata_width 8"
    lappend simList "c_s_axis_ctrl_tuser_width $xcoValue"

    return $simList
} ;# end translate_CTRLTUSERWidth

proc translate_DOUTTUSERWidth {xcoValue} {
    global OutputWidth

    puts "Entering [get_proc_name]"
    lappend simList "c_m_axis_dout_tuser_width $xcoValue"
    set douttdatawidth [ expr { [ xbip_utils_v2_0::roundup_to_multiple [ $OutputWidth GetValue ] 8 ] * 2 } ]
    lappend simList "c_m_axis_dout_tdata_width $douttdatawidth"
    return $simList
} ;# end translate_DOUTTUSERWidth





#################################################################

proc updateUsingPortProperties {} {
    puts "Entering [get_proc_name]"

    global IpModel

    global APortWidth
    global pin_s_axis_a_tdata
    global pin_s_axis_b_tdata
    global pin_s_axis_ctrl_tdata
    global pin_m_axis_dout_tdata
    global pinAR
    global pinAI
    global BPortWidth
    global pinBR
    global pinBI
    global pinROUND_CY
    global pinPR
    global pinPI
    global pinACLKEN
    global pinACLK
    global pinARESETN
    global doNotCallToSetStubbed 
    global OutputWidth

    global MinimumLatency

    # Because the value on the list box automatically updates on the CoreGen GUI, when uUPP is called,
    # the value is likely to change from that which the customer set.  
    # So read back the value and re-apply after the properties are set.
    set MinimumLatency_value [$MinimumLatency GetValue]

    # We disabled this value for CoreGen users, so re-enabled
    $MinimumLatency SetEnabled "true" -1

    $pinACLKEN SetProperty "isClockEnable" "true"
    $pinACLK SetProperty "isClock" "true"
    $pinARESETN SetProperty "isReset" "true"

    #new to v4.0 code
    ##set A_left   [$pin_s_axis_a_tdata GetProperty "leftindex"]
    ##set A_right  [$pin_s_axis_a_tdata GetProperty "rightindex"]
    ##set A_arith  [$pin_s_axis_a_tdata GetProperty "arithmetictype"]
    ##set A_binpt  [$pin_s_axis_a_tdata GetProperty "binarypoint"]
    ##set A_width  [expr { abs($A_left - $A_right) + 1}]

    ##set B_left   [$pin_s_axis_b_tdata GetProperty "leftindex"]
    ##set B_right  [$pin_s_axis_b_tdata GetProperty "rightindex"]
    ##set B_arith  [$pin_s_axis_b_tdata GetProperty "arithmetictype"]
    ##set B_binpt  [$pin_s_axis_b_tdata GetProperty "binarypoint"]
    ##set B_width  [expr { abs($B_left - $B_right) + 1}]

    ####################################################################
    ## Arvind's Comments 
    ##
    ## Before Setting any XcoValue obtain the subfields Information 
    ## We need to mask the information that that binarypoint is being used
    ## to get tdata information
    ####################################################################
    set A_subfields_from_sysgen  [$pin_s_axis_a_tdata GetProperty [AXISysgenSubfieldsUtil::tdata_info]]
    set B_subfields_from_sysgen  [$pin_s_axis_b_tdata GetProperty [AXISysgenSubfieldsUtil::tdata_info]]
    ## Arvind's Comment subfieldWidth Must contain the information of what the real data widths as reported from SysGen is
    ## subFieldActualWidth is something the IP developer must fill as only they know how padding should be done. Please correct this so that
    ## subfieldWidth contains the width information packed from the sysgen world.
    set A_subfield_re_width [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_re" "subfieldWidth"]
    set A_subfield_re_binary_point [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_re" "binarypoint"]
    set A_subfield_re_arithmetic_type [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_re" "arithmetictype"]
    set A_subfield_imag_width [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_imag" "subfieldWidth"]
    set A_subfield_imag_binary_point [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_imag" "binarypoint"]
    set A_subfield_imag_arithmetic_type [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_a_tdata "s_axis_a_tdata_imag" "arithmetictype"]
    
    set B_subfield_re_width [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_re" "subfieldWidth"]
    set B_subfield_re_binary_point [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_re" "binarypoint"]
    set B_subfield_re_arithmetic_type [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_re" "arithmetictype"]
    set B_subfield_imag_width [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_imag" "subfieldWidth"]
    set B_subfield_imag_binary_point [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_imag" "binarypoint"]
    set B_subfield_imag_arithmetic_type [ AXISysgenSubfieldsUtil::getAXItdataSubfieldProperty pin_s_axis_b_tdata "s_axis_b_tdata_imag" "arithmetictype"]
    #end of new code

    # The complex mult is performing (ar + jai)(br + jbi) where j = sqrt(-1)
    # This yields (arbr - aibi) + j(arbi + aibr) = Pr + jPi
    #
    # There is only one width parameter for each operand (so Ar width == Ai width, etc.)
    #
    # In the same vein, we also permit only one binary point location per operand.  This makes calculation
    # much simpler, as the resultant binary point location will be the same for every partial product in the result above.
    #
    # Conceivably, it would be possible to have a different binary point for each individual component of each operand
    # but this would make the handling of the binary point calculation much more complex.  Consider the case of binary point
    # ar=1 ai=2 br=3 bi=4.  Additionally the core itself has no way to realign binary points internally.
    #
    # So the output binary point will be, for example, Out_binpt = a_binpt+b_binpt.  The add or sub operation leads to only
    # integer bit growth, so it doesn't move the binary point.  The multiplication part is just like a real multiplier (binary points add)
   
    if { ![string equal -nocase $A_subfield_re_arithmetic_type "signed"] } { return "ERROR: s_axis_a_tdata_re must be signed not $A_subfield_re_arithmetic_type"}
    if { ![string equal -nocase $A_subfield_imag_arithmetic_type "signed"] } { return "ERROR: s_axis_a_tdata_imag must be signed not $A_subfield_imag_arithmetic_type"}
    if { ![string equal -nocase $B_subfield_re_arithmetic_type "signed"] } { return "ERROR: s_axis_b_tdata_re must be signed not $B_subfield_re_arithmetic_type"}
    if { ![string equal -nocase $B_subfield_imag_arithmetic_type "signed"] } { return "ERROR: s_axis_b_tdata_imag must be signed not $B_subfield_imag_arithmetic_type"}

    if { $A_subfield_re_width != $A_subfield_imag_width } { return "ERROR: s_axis_a_tdata_re and s_axis_a_tdata_imag must have the same widths" }
    if { $A_subfield_re_binary_point != $A_subfield_imag_binary_point } { return "ERROR: s_axis_a_tdata_re and s_axis_a_tdata_imag must have the same binary points" }
    if { $B_subfield_re_width != $B_subfield_imag_width } { return "ERROR: s_axis_b_tdata_re and s_axis_b_tdata_imag must have the same widths" }
    if { $B_subfield_re_binary_point != $B_subfield_imag_binary_point } { return "ERROR: s_axis_b_tdata_re and s_axis_b_tdata_imag must have the same binary points" }
    #Setting the values on the A and B ports will cause an update on the OutputWidth. The update procs set default
    #values. This means any value set by the GUI will be overriden. So read back current values before setting new.
    set OutputWidth_value [$OutputWidth GetValue]
    
    # Arvind's Comments - APortWidth and BPortWidths are the real widths and not
    # the interface widths
    # Setting the appropriate input widths and invoking the entire coregen feedback chain
    set retval [$IpModel SetXcoValue "APortWidth" $A_subfield_re_width valid]
    if { $retval != "OK" } { return $retval }
    if { !$valid } { return "ERROR: Invalid value [$APortWidth GetErrorMessage]" }
    
    set retval [$IpModel SetXcoValue "BPortWidth" $B_subfield_re_width valid]
    if { $retval != "OK" } { return $retval }
    if { !$valid } { return "ERROR: Invalid value [$BPortWidth GetErrorMessage]" }
   
    # Arvind's Comments : Is this what you would like - A question for Gordon 
    #Set orignal Output values back on the XCO parameters
    set retval [$IpModel SetXcoValue "OutputWidth" $OutputWidth_value valid]
    if { $retval != "OK" } { return $retval }
    if { !$valid } { return "ERROR: Invalid value [$OutputWidth GetErrorMessage]" }
    
    # Write back latency value set by user in SysGen GUI
    set retval [$IpModel SetXcoValue "MinimumLatency" $MinimumLatency_value valid]
    if { $retval != "OK" } { return $retval }
    if { !$valid } { return "ERROR: Invalid value [$MinimumLatency GetErrorMessage]" }

    # Result binary point is sum of binary point locations for either of the operand elements
    set P_subfield_re_binary_point [ expr { $A_subfield_re_binary_point + $B_subfield_re_binary_point } ]    
    
    ##############################################################################################
    # Arvind's Comments
    # I am not sure what is happening Here. The IP developer must review this part
    ##############################################################################################

    #Need to reduce binary width as LSBs are removed
    set OutputWidthLow_value [ expr {$A_subfield_re_width + $B_subfield_re_width + 1 - $OutputWidth_value } ]
    if { $P_subfield_re_binary_point - $OutputWidthLow_value > 0 } {
      set P_subfield_re_binary_point [expr { $P_subfield_re_binary_point - $OutputWidthLow_value } ]
    } else {
      set P_subfield_re_binary_point 0
    }
    if { $P_subfield_re_binary_point > $OutputWidth_value } {
    ##  #Binary point of both outputs should be the same so only check one.
      return "ERROR: OutputWidth, $OutputWidth_value, is set to a value less than the output binary point"
    }
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_re" "binarypoint" $P_subfield_re_binary_point
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_imag" "binarypoint" $P_subfield_re_binary_point
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_re" "arithmetictype" "signed" 
    AXISysgenSubfieldsUtil::setAXItdataSubfieldProperty pin_m_axis_dout_tdata "m_axis_dout_tdata_imag" "arithmetictype" "signed" 
    ##################################################################################################################
    # Arvind's Comments
    # At thie point The Binary Point and Arithmetic Type of the Output has been Updated the width has not been Updated
    # Yet and that must be done And we invoke update_pins_prop for this which based on XCO parameters updated will
    # Now Proceed to update the Widths on the Pins and this also implies that the appropriate tdatainfo has to be 
    # Updated.
    ###################################################################################################################
    update_tdata_pin
    set doNotCallToSetStubbed 1
    return "OK"

    ##set P_arith "signed"
    
    ##$pin_m_axis_dout_tdata SetProperty "arithmetictype" $P_arith
    
    # Write back latency value set by user in SysGen GUI
    ##set retval [$IpModel SetXcoValue "MinimumLatency" $MinimumLatency_value valid]
    ##if { $retval != "OK" } { return $retval }
    ##if { !$valid } { return "ERROR: Invalid value [$MinimumLatency GetErrorMessage]" }

    ##update_pins
#    update_AccelDSP_latency ;# may be redundant now?

} ;# end updateUsingPortProperties()

#proc update_AccelDSP_latency {} {
#
#    global pinAR
#    global pinAI
#    global pinBR
#    global pinBI
#    global pinROUND_CY
#    global pinPR
#    global pinPI
#    
#    global Latency
#
#    $Latency SetEnabled "true" -1
#
#    $pinAR SetProperty "latency" 0
#    $pinAI SetProperty "latency" 0
#    $pinBR SetProperty "latency" 0
#    $pinBI SetProperty "latency" 0
#    $pinROUND_CY SetProperty "latency" 0
#
#    set paramLatencyValue [$Latency GetValue];
#
#    $pinPR SetProperty "latency" $paramLatencyValue
#    $pinPI SetProperty "latency" $paramLatencyValue
#
#} ;# end update_AccelDSP_latency

