#-------------------------------------------------------------------------------
# Copyright (c) 2005-2006 Xilinx, Inc.
# All rights reserved.
#-------------------------------------------------------------------------------
#    ____  ____
#   /   /\/   /
#  /___/  \  /   Vendor: Xilinx
#  \   \   \/    Version: 2.0
#   \   \        Filename: fir_compiler_v2_0_latency.tcl
#   /   /        Date Last Modified: 09/01/06
#  /___/   /\    Date Created: 09/01/06
#  \   \  /  \   $id:
#   \___\/\___\
#
# This text contains proprietary, confidential information of Xilinx,Inc.,
# is distributed under license from Xilinx, Inc., and may be used, copied
# and/or disclosed only pursuant to the terms of a valid license agreement
# with Xilinx, Inc.
#
# This copyright notice must be retained as part of this text at all times.
#
#-------------------------------------------------------------------------------

#source [FindFullPath "com/xilinx/ip/fir_compiler_v2_0/gui/fir_compiler_v2_0_utils.tcl"]

namespace eval fir_compiler_v2_0_analysis {

   ############################################################################
   # Analyse the filter parameters and generate intermediate parameters as
   # globals
   ############################################################################
   proc mac6_analysis { \
                      num_taps num_channels filt_type \
                      rate_type inter_rate deci_rate zpf \
                      coef_struct num_filts coef_reload \
                      mem_option data_buff_type coef_buff_type \
                      has_nd \
                      dram_depth_thres srl16_depth_thres \
                      clocks_per_sample_calculated \
                      debug_level \
                      } {
   
     ##########################################################################
     # GLOBAL DECLARATIONS
     global num_macs                    # Number of MAC units, not including Accumulator
     global acum_req                    # Accumulator required, high for non-parallel cases, including single MAC
     global clk_per_channel             # Number of clocks per channel
     global symmetry                    # Whether or not symmetry is exploited in the implementation
     global odd_symmetry                # whether or not odd symmetry is present and exploited
     global taps_to_calc                # number of non-zero taps to be calculated, after symmetric folding
     global rate                        # the effective rate for a multi-rate filter  
     global p_val                       # the P value for fractional rate P/Q filters 
     global q_val                       # the Q value for fractional rate P/Q filters
     #global taps_per_phase             # Number of taps calculated per phase (not needed as a global?)
     global halfband_single_mac         # {0,1} shows when filter is a single MAC implementation of a halfband filter
     global taps_per_mac_balanced       # Number of taps calculated per MAC unit after balancing
     global data_mem_space              # Data memory space, for resource and latency calculations
     global coef_mem_space              # Coeff memory type, for resource and latency calculations
     global data_mem_type               # Data memory space, for resource and latency calculations
     global coef_mem_type               # Coeff memory type, for resource and latency calculations
     global use_b_casc                  # Specifies if the CASCADEB ports are used, affects latency
     global mid_tap                     # Specifies where the middle tap is, only used for reload info textfile generation
   
     ##########################################################################
     # MAIN PROCESS
     ##########################################################################
     
     # Initialisation
     set num_macs 1
     set acum_req 0
     set clk_per_channel $clocks_per_sample_calculated
     
     #puts "Analyzing MAC v6 Structure"

     ;#*********************Generic Analysis******************************

     set recalc 1
     set sym_override 0

     ;#loop start
     while { $recalc != 0 } {

       set symmetry     [ fir_compiler_v2_0_utils::calc_symmetry $sym_override $coef_struct $rate_type ]
       if { $debug_level > 1 } { puts "ANALYSIS :  symmetry     : $symmetry" }
       set odd_symmetry [ fir_compiler_v2_0_utils::calc_odd_symmetry $symmetry $num_taps ]
       if { $debug_level > 1 } { puts "ANALYSIS :  odd_symmetry : $odd_symmetry" }

       set taps_to_calc [ fir_compiler_v2_0_utils::get_taps_to_calc $symmetry $odd_symmetry $num_taps $filt_type $coef_struct $inter_rate ]
       if { $debug_level > 0 } { puts "ANALYSIS :  taps_to_calc : $taps_to_calc" }

       set rate [ fir_compiler_v2_0_utils::get_effective_rate $filt_type $rate_type $coef_struct $inter_rate $deci_rate ]      
       if { $debug_level > 1 } { puts "ANALYSIS :  rate : $rate" }
       
       set gcd   [ fir_compiler_v2_0_utils::get_gcd  $inter_rate $deci_rate  ]
       set p_val [ expr { $inter_rate / $gcd } ]
       set q_val [ expr { $deci_rate / $gcd } ]
       if { $debug_level > 1 } { 
         puts "ANALYSIS :  p_val : $p_val"
         puts "ANALYSIS :  q_val : $q_val"
       }

       set taps_per_phase [ fir_compiler_v2_0_utils::get_taps_per_phase $filt_type $rate_type $p_val $q_val $taps_to_calc $coef_struct $rate ]
       if { $debug_level > 0 } { puts "ANALYSIS :  taps_per_phase : $taps_per_phase" }

       set taps_per_mac [ fir_compiler_v2_0_utils::get_taps_per_mac $filt_type $rate_type $coef_struct $p_val $q_val \
                                                                    $clk_per_channel $taps_per_phase $rate ]
       if { $debug_level > 0 } { puts "ANALYSIS :  taps_per_mac : $taps_per_mac" }
       
       if { $coef_struct == "Half_Band" && $filt_type == "Decimation" } {
         set clk_per_channel $taps_per_mac
       }
       if { $debug_level > 0 } { puts "ANALYSIS :  clk_per_channel : $clk_per_channel" }
       
       set halfband_single_mac [ fir_compiler_v2_0_utils::get_hb_single_mac $coef_struct $filt_type $taps_per_phase $clk_per_channel $taps_per_mac ]      
       if { $debug_level > 0 } { puts "ANALYSIS :  halfband_single_mac : $halfband_single_mac" }

       set num_macs [ expr { int(ceil( double( $taps_per_phase) / $taps_per_mac )) } ]
       if { $num_macs < 1 } { set num_macs 1 }

       if { $debug_level > 0 } { puts "ANALYSIS :  num_macs : $num_macs" }

       ;# Tap Balancing
       ;# This is alway present now
       set allow_balancing [ fir_compiler_v2_0_utils::tap_balancing_ok $filt_type $coef_struct $rate_type $num_channels ]
       
       if { $allow_balancing == 1 } {

         set clk_per_channel [ fir_compiler_v2_0_utils::balance_clk_per_channel $filt_type $rate_type $coef_struct $halfband_single_mac \
                                                                                $taps_per_phase $num_macs $rate $p_val ]
         set taps_per_mac_balanced $clk_per_channel
         
       } else {
         ;# Not able to balance taps across MACs
         set taps_per_mac_balanced $taps_per_mac
       }
       if { $clk_per_channel < 0 } { set clk_per_channel 1 }

       if { $debug_level > 0 } {
         puts "ANALYSIS :  clk_per_channel : $clk_per_channel"
         puts "ANALYSIS :  taps_per_mac_balanced : $taps_per_mac_balanced"
       }

       set data_mem_space [ fir_compiler_v2_0_utils::get_data_mem_space $filt_type $rate_type $coef_struct $taps_per_mac_balanced $q_val $rate $zpf ]
       if { $debug_level > 1 } { puts "ANALYSIS :  data_mem_space : $data_mem_space" }

       set coef_mem_space [ fir_compiler_v2_0_utils::get_coef_mem_space $filt_type $rate_type $coef_struct \
                                                                        $taps_per_mac_balanced $p_val $q_val $rate ]
       if { $debug_level > 1 } { puts "ANALYSIS :  coef_mem_space : $coef_mem_space" }

       set data_mem_type [ fir_compiler_v2_0_utils::get_data_mem_type $mem_option $data_mem_space $num_channels $srl16_depth_thres \
                                                                      $coef_struct $filt_type $rate_type $data_buff_type ]
       if { $debug_level > 1 } { puts "ANALYSIS :  data_mem_type : $data_mem_type" }
       
       if { $data_mem_type == 1 && $symmetry == 1 } { set data_combined 1 } else { set data_combined 0 }

       set coef_mem_type [ fir_compiler_v2_0_utils::get_coef_mem_type $mem_option $coef_mem_space $num_filts $coef_reload \
                                                                      $dram_depth_thres $srl16_depth_thres $coef_buff_type ]
       if { $debug_level > 1 } { puts "ANALYSIS :  coef_mem_type : $coef_mem_type" }

       if { $num_channels == 1 && $taps_per_mac_balanced == 1 && $symmetry == 0  && $filt_type == "Single_Rate" } {
         set use_b_casc 1
       } else {
         set use_b_casc 0
       }
       if { $debug_level > 0 } { puts "ANALYSIS :  use_b_casc : $use_b_casc" }

       set acum_req [ fir_compiler_v2_0_utils::calc_acum_req $num_macs $coef_struct $filt_type $rate_type $taps_per_mac_balanced $rate ]
       if { $debug_level > 0 } { puts "ANALYSIS :  acum_req : $acum_req" }
       
       if { $sym_override == 0 } { set mid_tap $num_macs }

       set sym_override [ fir_compiler_v2_0_utils::check_sym_override $filt_type $coef_struct $clk_per_channel $symmetry $has_nd \
                                                                      $num_taps $num_macs $num_channels $deci_rate $zpf $rate ]
       if { $debug_level > 1 } { puts "ANALYSIS :  sym_override : $sym_override" }

       set recalc $sym_override

     }  ;# loop stop
   
   }
   
   return 0
}
