#-------------------------------------------------------------------------------
# Copyright (c) 2005-2006 Xilinx, Inc.
# All rights reserved.
#-------------------------------------------------------------------------------
#    ____  ____
#   /   /\/   /
#  /___/  \  /   Vendor: Xilinx
#  \   \   \/    Version: 2.0
#   \   \        Filename: fir_compiler_v2_0.tcl
#   /   /        Date Last Modified: 30/11/05
#  /___/   /\    Date Created: 30/11/05
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

namespace eval fir_compiler_v2_0_reload {

  ###############################################################################
  # Function to generate the list of reload order indices
  ###############################################################################
  proc get_reload_indices { \
                             core_symmetry                \
                             core_oddsymmetry             \
                             core_num_macs                \
                             core_taps_calced             \
                             core_coefmemspace            \
                             core_midtap                  \
                             core_halfband_smac           \
                             num_taps                     \
                             inter_rate_ip                \
                             deci_rate_ip                 \
                             col_mode                     \
                             col1_ln                      \
                             coln_ln                      \
                             coef_struct                  \
                             filt_type                    \
                             rate_type                    \
                             mode                         \
                             debug_level                  \
                           } {
  
    #rates
    set deci_rate  [ expr { $deci_rate_ip / [ fir_compiler_v2_0_utils::get_gcd $inter_rate_ip $deci_rate_ip ] } ]
    set inter_rate [ expr { $inter_rate_ip / [ fir_compiler_v2_0_utils::get_gcd $inter_rate_ip $deci_rate_ip ] } ]
  
    #Filter type

    if { $rate_type == "Fixed_Fractional" } {
      set filter_type_value 9
    } else {
      if { $filt_type == "Single_Rate" } {
        if { $coef_struct == "Hilbert" } {
           set filter_type_value 3
        } elseif { $coef_struct == "Half_Band" } {
           set filter_type_value 5
        } else {
           set filter_type_value 0
        }
      } elseif {$filt_type == "Decimation"} {
        if {$coef_struct == "Half_Band"} {
           set filter_type_value 6
        } else {
           set filter_type_value 1
        }
      } elseif {$filt_type == "Interpolation"} {
        if {$coef_struct == "Half_Band"} {
           set filter_type_value 7
        } else {
           set filter_type_value 2
        }
      } elseif {$filt_type == "Interpolated"} {
        set filter_type_value 4
      }
    }

    if { $debug_level > 0 } { puts "filter type value: $filter_type_value" }
  
    #Generate Reordered Index file

    # Set core_taps_calced locally to be the required number of taps before zero removal
    set local_core_taps_calced [ expr { $core_taps_calced - 2*$core_halfband_smac } ]
    #set local_core_taps_calced [ expr { $core_taps_calced - 0 } ]
    if { $debug_level > 0 } { puts "local_core_taps_calced  : $local_core_taps_calced" }
    
    #Generate source index
    set src_coefficients_index ""
    
    if {$mode=="sysgen"} {
      set top_index $local_core_taps_calced
      set text_to_prepend ""
    } else {
      set top_index $num_taps
      set text_to_prepend "Index "
    }
    
    for {set index 0} { $index < $top_index } { incr index } {
      set write_i [ expr { $index } ]
      lappend src_coefficients_index "$text_to_prepend$write_i"
    }

    #set src_coefficients_index ""
    #for {set index 0} { $index < $num_taps} {incr index} {
    #  set write_i [ expr { $index } ]
    #  lappend src_coefficients_index "Index $write_i"
    #}

    # Determine if coefficients need to be calculated
    set tap_difference 0
  
    if { $core_symmetry == 1 } {

      set local_req_num_taps [ expr { $num_taps / 2 } ]

      if { [ expr { $num_taps % 2 } ] > 0 } {
        set local_req_num_taps [ expr { $local_req_num_taps + 1 } ]
      }
      #puts "local_req_num_taps      : $local_req_num_taps"
      set tap_difference [ expr { $local_core_taps_calced - $local_req_num_taps } ]

      if { $filter_type_value == 2 && $core_symmetry == 1 && $core_oddsymmetry == 1 } {

        set tap_difference [ expr { $tap_difference - ( $inter_rate /2 ) } ]
      }
    } else {
      set local_req_num_taps $num_taps
    }
    if { $debug_level > 0 } { puts "tap_difference          : $tap_difference" }
    
    if {$mode=="sysgen"} {
      set coefficients $src_coefficients_index
    } else {
      # Pad and transfer to coefficients list
      set coefficients ""
      for {set tap 0} { $tap < $local_core_taps_calced} {incr tap} {
        if { $core_symmetry == 1 } {
          if { $tap >= $tap_difference } {
            lappend coefficients [ lindex $src_coefficients_index [ expr { $tap - $tap_difference} ] ]
          } else {
            lappend coefficients 0
          }
        } else {
          if { $tap >= $num_taps } {
            lappend coefficients 0
          } else {
            lappend coefficients [ lindex $src_coefficients_index $tap ]
          }
        }
      }
    }
    
    #create mod_coef by setting to padded
    set mod_coefficients $coefficients
    set mac_coefficients $coefficients

    #Generate symmetric pairs for interpolating symmetry
    if { $filter_type_value == 2 && $core_symmetry == 1 } {

      set tap 0
      while { $tap <= [ expr {$local_core_taps_calced -1} ] } {

        set phase_offset 0
        if { [ expr { $inter_rate % 2 } ] != 0 } {
          #get centre phase
          lset mod_coefficients $tap [ lindex $coefficients [ expr { $tap+($inter_rate/2) } ] ]
          set phase_offset 1
        }

        set phase 0
        while { $phase < [ expr { $inter_rate / 2 } ] } {

          set write_i [ expr { $tap+(2*$phase)+1+$phase_offset } ]
          set read_val_1 [ lindex $coefficients [ expr { $tap+$phase } ] ]
          set read_val_2 [ lindex $coefficients [ expr { ($inter_rate+$tap-1)-$phase } ] ]

          lset mod_coefficients $write_i "$read_val_1 + $read_val_2"

          set write_i [ expr { $tap+(2*$phase)+$phase_offset } ]

          lset mod_coefficients $write_i "$read_val_1 - $read_val_2"

          incr phase
        }

        #put reodered back into src list
        set phase 0
        while { $phase <= [ expr { $inter_rate-1 } ] } {
          lset coefficients [ expr { $tap + $phase } ] [ lindex $mod_coefficients [ expr { $tap + $phase } ] ]
          incr phase
        }
      
        incr tap $inter_rate

      }
    }

    # for {set tap 0} { $tap < $local_core_taps_calced} {incr tap} {
    #   puts "[lindex $coefficients $tap]"
    # }
  
    # Re-order Coefficients to fit filter structure

    if { $filter_type_value == 3 || $filter_type_value == 5 || $filter_type_value == 6 || $filter_type_value == 7 } {
      #Halfband, including multi-rate, and halfband
      set rate 2
    } elseif { $filter_type_value == 4 } {
      #Interpolated
      set rate 1
    } else {
      if { $inter_rate > $deci_rate } {
        set rate $inter_rate
      } else {
        set rate $deci_rate
      }
    }
  
    for {set mac 0} {$mac < $core_num_macs} {incr mac} {
      set mac_mem_pos 0
      set phase 0
      set mod_phase 0
      set phase_loop_end [ expr { $rate-1} ]
      set phase_loop_dir 1
      set pq_deci_start_phase 0

      if { $filter_type_value == 1 ||
           ($filter_type_value == 9 && $inter_rate<$deci_rate) } {
      
        set phase [ expr { $rate-1} ]
        set mod_phase [ expr { $rate-1} ]
        set pq_deci_start_phase [ expr { $inter_rate-1} ]
        set phase_loop_end 0
        set phase_loop_dir -1
      }
    
      while { $phase != [ expr { $phase_loop_end+$phase_loop_dir } ] } {
      
        if { $filter_type_value == 9 && $inter_rate<$deci_rate } {
        
          #PQ Decimation reordering

          set pq_deci_phase $pq_deci_start_phase

          for {set pq_reorder 0} { $pq_reorder < $inter_rate } {incr pq_reorder} {
        
            if { $debug_level > 1 } {
              puts "mod_phase: $mod_phase"
              puts "pq_deci_phase: $pq_deci_phase"
            }

            set tap_pos [ expr { ($mac * $core_coefmemspace) +
                                 ($pq_deci_phase * $deci_rate) +
                                 $mod_phase } ]

            while { $tap_pos < [ expr { ($mac+1)*$core_coefmemspace  } ] } {
              if { $debug_level > 1 } { 
                puts "mac_mem_pos: $mac_mem_pos"
                puts "tap_pos: $tap_pos"
              }
              lset mac_coefficients $mac_mem_pos [ lindex $coefficients $tap_pos ]
              incr mac_mem_pos
              set tap_pos [ expr { $tap_pos + ( $inter_rate * $deci_rate) } ]
            }

            set pq_deci_phase [ expr { ($pq_deci_phase-1) % $inter_rate } ]
          }
        } elseif {  $filter_type_value != 3 &&
                    $filter_type_value != 4 &&
                    $filter_type_value != 5 &&
                    $filter_type_value != 6 &&
                    $filter_type_value != 7 } {
        
          #Standard reordering

          if { $filter_type_value == 9 } {
            set tap_pos [ expr { ($mac*$core_coefmemspace)+$mod_phase } ]
          } else {
            set tap_pos [ expr { ($mac*$core_coefmemspace)+$phase } ]
          }
        
          while { $tap_pos < [ expr { ($mac+1)*$core_coefmemspace  } ] } {
            lset mac_coefficients $mac_mem_pos [ lindex $coefficients $tap_pos ]
            incr mac_mem_pos
            set tap_pos [ expr { $tap_pos + $rate } ]
          }

        } else {

          #Hilbert,Halfband and interpolated reordering
          set tap_pos [ expr { ($mac*$core_coefmemspace*$rate)+$phase } ]
          while { $tap_pos < [ expr { ($mac+1)*$core_coefmemspace * $rate  } ] && $phase == 0 } {
            if { $debug_level > 1 } { 
              puts "tap_pos          : $tap_pos"
              puts "mac_coefficients : $mac_coefficients"
              puts "mac_mem_pos      : $mac_mem_pos"
            }
            lset mac_coefficients $mac_mem_pos [ lindex $coefficients $tap_pos ]
            incr mac_mem_pos
            set tap_pos [ expr { $tap_pos + $rate } ]
          }
          if { $debug_level > 1 } { 
            puts "END tap_pos          : $tap_pos"
            puts "END mac_coefficients : $mac_coefficients"
            puts "END mac_mem_pos      : $mac_mem_pos"
          }
        }

        set phase [ expr { $phase + $phase_loop_dir } ]

        if { $inter_rate > $deci_rate } {
          #interpolation
          set mod_phase [ expr { ($mod_phase+$deci_rate) % $inter_rate } ]
        } else {
          #decimation
          if { [ expr { $mod_phase-$inter_rate} ] < 0 } {
            set pq_deci_start_phase [ expr { $pq_deci_start_phase -1} ]
          }
          set mod_phase [ expr { ($mod_phase-$inter_rate) % $deci_rate } ]
        }
      }
      #end reorder loop


      #Multicolumn reordering
      if { $col_mode != "Disabled" } {
      
        if { $mac < $col1_ln } {
          set new_mac_pos [ expr { $core_num_macs -1-$mac } ]
        } else {

          set remaining_mac [ expr { $mac - $col1_ln } ]
          set mac_in_col [ expr { $remaining_mac % $coln_ln } ]
        
          if { [ expr { ((($core_num_macs-1)-$col1_ln) % $coln_ln) } ]
                == [ expr { $remaining_mac / $coln_ln } ] } {
            #last column
            if { [ expr { ((($core_num_macs-1)-$col1_ln) % $coln_ln) } ]
                 != [ expr { $coln_ln -1 } ] } {
              #col not ful so modifiy col lenght
              set cur_col_len [ expr { ((($core_num_macs-1)-$col1_ln) % $coln_ln)+1 } ]
            } else {
              set cur_col_len $coln_ln
            }
          } else {
            set cur_col_len $coln_ln
          }
        
          if { [ expr { ($remaining_mac /  $coln_ln) % 2 } ] == 0 } {
            #column dir reversed
            set new_mac_pos [ expr { (($coln_ln-1)-$mac_in_col)
                                     + $col1_ln
                                     + (($remaining_mac/$coln_ln)*$coln_ln) } ]
          } else {
            #column dir normal
            set new_mac_pos [ expr { $mac_in_col
                                     + $col1_ln
                                     + (($remaining_mac/$coln_ln)*$coln_ln) } ]
          }
        
          set new_mac_pos [ expr { ($core_num_macs-1)-$new_mac_pos } ]
        }
      
        for { set pos 0} {$pos < $core_coefmemspace} {incr pos} {
        
          set write_i [ expr { ($new_mac_pos * $core_coefmemspace) + $pos } ]
          lset mod_coefficients $write_i [ lindex $mac_coefficients $pos ]
        }
      } else {
        #no multi-column
        set new_mac_pos [ expr { ($core_num_macs-1) - $mac } ]

        for { set pos 0} {$pos < $core_coefmemspace} {incr pos} {
        
          set write_i [ expr { ($new_mac_pos * $core_coefmemspace) + $pos } ]
          lset mod_coefficients $write_i [ lindex $mac_coefficients $pos ]
        }
      }

    }
    #end mac loop
    if { $debug_level > 1 } { puts "mod_coefficients : $mod_coefficients" }
  
    #Store centre tap when halfband
    if {  $filter_type_value == 5 || $filter_type_value == 6 || $filter_type_value == 7 } {
    
      if { $core_num_macs == $core_midtap } {
        set halfband_centre_tap  [ lindex $coefficients [ expr { $tap_difference + $local_req_num_taps -1 } ] ]
      } else {
        #symmetry disabled
        set halfband_centre_tap  [ lindex $coefficients [ expr { $num_taps / 2 } ] ]
      }
      if { $debug_level > 1 } { puts "halfband_centre_tap : $halfband_centre_tap" }
      #set write_i [ expr { $core_num_macs * $core_coefmemspace } ]
      set write_i [ expr { $core_num_macs * $core_coefmemspace - $core_halfband_smac} ]
      if { $debug_level > 1 } { puts "write_i : $write_i" }
      lset mod_coefficients $write_i $halfband_centre_tap
      if { $debug_level > 1 } {
        puts "mod_coefficients :"
        puts $mod_coefficients
      }

      #if { $core_halfband_smac == 1 } {
      #  #if halfband single mac memory space needs shifted down by 1 as mid tap in main memory space
      #  if { $debug_level > 1 } { puts "core_coefmemspace $core_coefmemspace" }
      #  for { set tap 0} { $tap < $core_coefmemspace} {incr tap} {
      #    lset mod_coefficients $tap [ lindex $mod_coefficients [ expr { $tap+1} ] ]
      #  }
      #}
    }
  
    set rm_length $local_core_taps_calced
    #if { $mode == "sysgen" } { set rm_length $local_core_taps_calced } else { set rm_length $num_taps }
    if { $filter_type_value == 5 || $filter_type_value == 6 || $filter_type_value == 7 } {
      # Removal of upper half of index vector for halfband or hilbert cases
      #for { set index [ expr { $local_core_taps_calced/2 } ] } { $index < $local_core_taps_calced } {incr index} {
      #  lset mod_coefficients $tap [ lindex $mod_coefficients [ expr { $tap+1} ] ]
      #}
      set mod_coefficients [ lreplace $mod_coefficients [ expr { $rm_length/2 + 1 } ] end ]
    } elseif { $filter_type_value == 3 } {
      set mod_coefficients [ lreplace $mod_coefficients [ expr { $rm_length/2     } ] end ]
    }
    
    return $mod_coefficients

  } ;# end generate_Reload_Info

  #################################################################

}