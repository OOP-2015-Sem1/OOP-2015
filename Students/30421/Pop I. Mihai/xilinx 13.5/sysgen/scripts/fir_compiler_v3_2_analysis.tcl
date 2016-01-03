#-------------------------------------------------------------------------------
# $Header: /devl/xcs/repo/env/Databases/ip/src2/J/fir_compiler_v3_2/gui/fir_compiler_v3_2_analysis.tcl,v 1.5 2008/09/09 19:57:08 akennedy Exp $
#-------------------------------------------------------------------------------
# Copyright (c) 2005-2006 Xilinx, Inc.
# All rights reserved.
#-------------------------------------------------------------------------------
#    ____  ____
#   /   /\/   /
#  /___/  \  /   Vendor: Xilinx
#  \   \   \/    Version: 2.0
#   \   \        Filename: fir_compiler_v3_2_analysis.tcl
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

namespace eval fir_compiler_v3_2_analysis {

  namespace import ::fir_compiler_v3_2_utils::l*

  set c_single_rate                 0
  set c_polyphase_decimating        1
  set c_polyphase_interpolating     2
  set c_hilbert_transform           3
  set c_interpolated_transform      4
  set c_halfband_transform          5
  set c_decimating_half_band        6
  set c_interpolating_half_band     7
  set c_polyphase_pq                9
  set c_interpolating_symmetry      10

  set c_srl16   0
  set c_bram    1
  set c_dram    2

  set c_speed   0
  set c_area    1

  set c_mem_auto        0
  set c_mem_forced_dist 1
  set c_mem_forced_bram 2

  set c_preadd_add          0
  set c_preadd_sub          1
  set c_preadd_addsub       2
  set c_preadd_subadd       3
  set c_preadd_add_swapped  4
  
  set v4          "virtex4"
  set v5          "virtex5"
  set s3ax        "spartan3adsp"
  set s3          "spartan3"
  set s3a         "spartan3a"
  set s3e         "spartan3e"
  set v2          "virtex2"
  set ns_family   "ns"
  
  set c_full_precision     0
  set c_truncate_lsbs      1
  set c_symmetric_zero     2
  set c_symmetric_inf      3
  set c_convergent_even    4
  set c_convergent_odd     5
  set c_non_symmetric_down 6
  set c_non_symmetric_up   7

  
  proc is_pow_of_2 { val } {

    set power 0

    while { [ expr { pow(2,$power) } ] <= $val } {
      if { [ expr { pow(2,$power) } ] == $val } {
        return 1
      } else {
        set power [ expr { $power + 1 } ]
      }
    }

    return 0
  }

  proc v4_bram_depth { width } {
    #puts "using bram_depth"
    if { $width <= 1 } {
      return [ expr { pow(2,14) } ]
    } elseif { $width <= 2 } {
      return [ expr { pow(2,13) } ]
    } elseif { $width <= 4 } {
      return [ expr { pow(2,12) } ]
    } elseif { $width <= 9 } {
      return [ expr { pow(2,11) } ]
    } elseif { $width <= 18 } {
      return [ expr { pow(2,10) } ]
    } elseif { $width <= 36 } {
      return [ expr { pow(2,9) } ]
    } else {
      return 0
    }
  }

  proc get_max { val1 val2 } {
    if { $val1 > $val2 } {
      return $val1
    } else {
      return $val2
    }
  }

  proc treat_as_s3ax { family } {
    
    if { $family == "spartan3adsp" ||
         $family == "spartan3" ||
         $family == "spartan3a" ||
         $family == "spartan3e" ||
         $family == "virtex2" } {
      return true
    } else {
      return false
    }
  }

  #-----------------------------------------------------------------
  # Toolbox functions
  #-----------------------------------------------------------------

  #-----------------------------------------------------------------
  namespace eval delay {
    
    proc use_bram { depth mem_type data_width family } {
    
      set bram_min_depth 7

      if { $mem_type == 1 } {
        #force srl16
        return 0
      } elseif { $mem_type == 2 } {
        #force bram
        if { $depth > $bram_min_depth } {
          return 1
        } else {
          return 0
        }
      } else {
        #auto
        set max_slice_to_bram 160
        set slice_per_bit [ expr { int( ceil( double( $max_slice_to_bram ) / $data_width ) ) } ]
        if { $family == "virtex5" } {
          set slice_to_bram_depth [ fir_compiler_v3_2_analysis::get_max $bram_min_depth [ expr { 64 * $slice_per_bit } ] ]
        } else {
          set slice_to_bram_depth [ fir_compiler_v3_2_analysis::get_max $bram_min_depth [ expr { 32 * $slice_per_bit } ] ]
        }
        if { $depth > $slice_to_bram_depth } {
          return 1
        } else {
          return 0
        }
      }
    }
    #end use bram
  }

  #-----------------------------------------------------------------
  namespace eval addsub {

    proc lat { has_pre_reg has_mid_reg num_mid_reg } {

      set latency 1

      if { $has_pre_reg == 1 } {
        set latency [ expr { $latency + 1 } ]
      }

      if { $has_mid_reg == 1 } {
        set latency [ expr { $latency + $num_mid_reg } ]
      }

      return $latency
    }
    
    proc get_adder_max { param } {
      if { [ fir_compiler_v3_2_analysis::treat_as_s3ax $param ] == false } {
        return 12
      } else {
        return 8;
      }
    }

  }
  
  #-----------------------------------------------------------------
  namespace eval ram {

    set param_default [ list 0 0 0 0 0 0 ]
    set family              0
    set implementation      1
    set mem_type            2
    set write_mode          3
    set has_ce              4
    set use_mif             5

    proc lat { param num_ports } {

      if { [lindex $param $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {
        return 1
      } else {
        if { $num_ports == 2 } {
          if { [lindex $param $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_dram } {
            return 1
          } else {
            if { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $param $fir_compiler_v3_2_analysis::ram::family ] ] == true &&
                 [lindex $param $fir_compiler_v3_2_analysis::ram::family ] != "spartan3adsp" } {
              return 1
            } else {
              return 2
            }
          }
        } else {
          if { [lindex $param $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
            if { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $param $fir_compiler_v3_2_analysis::ram::family ] ] == true &&
                 [lindex $param $fir_compiler_v3_2_analysis::ram::family ] != "spartan3adsp" } {
              return 1
            } else {
              return 2
            }
          } else {
            if { [lindex $param $fir_compiler_v3_2_analysis::ram::write_mode ] == 1 } {
              return 2
            } else {
              return 1
            }
          }
        }
      }
    }
  }

  #-----------------------------------------------------------------
  namespace eval emb_calc {

    set param_default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
    set family              0
    set implementation      1
    set pre_add             2
    set pre_add_func        3
    set pre_add_ipreg       4
    set pre_add_midreg      5
    set a_delay             6
    set b_delay             7
    set a_src               8
    set a_sign              9
    set b_sign              10
    set d_sign              11
    set reg_opcode          12
    set implement_extra_b_dly 13
    
    set dtls_default [ list 0 0 0 0 0 ]
    set dtls_pre_add_ipreg  0
    set dtls_pre_add_midreg 1
    set dtls_extra_b_dly    2
    set dtls_latency        3
    set dtls_extra_a_dly    4
    
    proc dtls { param } {

      set latency         0
      set extra_b_dly     0
      set extra_a_dly     0
      set pre_add_midreg  0
      set pre_add_ipreg   0

      if  { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::family ] == "virtex4" ||
            [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::family ] == "virtex5" } {
        
        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1 } {

          set pre_add_ipreg [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add_ipreg ]
          set pre_add_midreg [lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add_midreg ]
          set latency [ expr { $latency + [ fir_compiler_v3_2_analysis::addsub::lat $pre_add_ipreg $pre_add_midreg 1 ] } ]

        }

        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 0 } {

          if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] > [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } {
            set latency [ expr { $latency + [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] } ]
          } else {
            set latency [ expr { $latency + [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } ]
          }

        } else {
          set latency [ expr { $latency + [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } ]
        }

        set latency [ expr { $latency + 3 } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1 } {
          set a_dly [ expr { [ fir_compiler_v3_2_analysis::addsub::lat $pre_add_ipreg $pre_add_midreg 1 ] - [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] } ]
        } else {
          set a_dly 0
        }

        if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] + $a_dly - 1 } ] > 0 } {
          set extra_b_dly [ expr { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] + $a_dly - 1 } ]
        } else {
          set extra_b_dly 0
        }

      } elseif { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::family ] == "spartan3adsp" } {
      #Sandia
        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1 } {
          set latency [ expr { $latency + 1 } ]
        }

        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 0 } {

          if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] > [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } {
            set latency [ expr { $latency + [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] } ]
          } else {
            set latency [ expr { $latency + [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } ]
          }

        } else {
          set latency [ expr { $latency + [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } ]
        }

        set latency [ expr { $latency + 3 } ]
        
        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1  } {
          set extra_b_dly 1
        } else {
          set extra_b_dly 0
        }
      } else {
        #MULT18 families

        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1 } {

          set pre_add_ipreg [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add_ipreg ]
          set pre_add_midreg [lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add_midreg ]
          set latency [ expr { $latency + [ fir_compiler_v3_2_analysis::addsub::lat $pre_add_ipreg $pre_add_midreg 1 ] } ]

        }
        
        #Multiplier+post_adder
        set latency [ expr { $latency + 2 } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::family ] == "spartan3e" ||
             [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::family ] == "spartan3a"  } {
          #extra reg for mult18sio
          set latency [ expr { $latency + 1 } ]
        }
        
        set dly_on_a 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1 } {
          set dly_on_a [ expr { [ fir_compiler_v3_2_analysis::addsub::lat $pre_add_ipreg $pre_add_midreg 1 ] - [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] } ]
        }
        
        set extra_a_dly [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::pre_add ] == 1 } {
          set extra_a_dly [ fir_compiler_v3_2_analysis::get_max 0 [ expr { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::a_delay ] - [ fir_compiler_v3_2_analysis::addsub::lat $pre_add_ipreg $pre_add_midreg 1 ] } ] ]
        }

        set extra_b_dly [ expr { [ lindex $param $fir_compiler_v3_2_analysis::emb_calc::b_delay ] + $dly_on_a } ]
      }

      set details $fir_compiler_v3_2_analysis::emb_calc::dtls_default
      lset details $fir_compiler_v3_2_analysis::emb_calc::dtls_latency $latency
      lset details $fir_compiler_v3_2_analysis::emb_calc::dtls_extra_b_dly $extra_b_dly
      lset details $fir_compiler_v3_2_analysis::emb_calc::dtls_extra_a_dly $extra_a_dly
      lset details $fir_compiler_v3_2_analysis::emb_calc::dtls_pre_add_midreg $pre_add_midreg
      lset details $fir_compiler_v3_2_analysis::emb_calc::dtls_pre_add_ipreg $pre_add_ipreg

      return $details

    }
  }
  
  #-----------------------------------------------------------------
  namespace eval tap_memory_add_casc {
    
    set param_default [ list 0 0 0 0 0 0 0 0 0 0 0 0 ]
    set family              0
    set implementation      1
    set data_mem_type       2
    set coef_mem_type       3
    set data_comb           4
    set data_coef_comb      5
    set no_data_mem         6
    set coef_mem_depth      7
    set has_ce              8
    set coef_reload         9
    set coef_reload_depth   10
    set symmetric           11
    
    set lat_default [ list 0 0 ]
    set lat_data 0
    set lat_coef 1
    
    proc lat { param } {
      set lat_coef 0
      set lat_data 0
      
      set data_ram_param $fir_compiler_v3_2_analysis::ram::param_default
      set coef_ram_param $fir_compiler_v3_2_analysis::ram::param_default

      lset data_ram_param $fir_compiler_v3_2_analysis::ram::family          [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family ]
      lset data_ram_param $fir_compiler_v3_2_analysis::ram::implementation  [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation ]
      lset data_ram_param $fir_compiler_v3_2_analysis::ram::mem_type        [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ]
      lset data_ram_param $fir_compiler_v3_2_analysis::ram::write_mode      1
      lset data_ram_param $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce ]
      lset data_ram_param $fir_compiler_v3_2_analysis::ram::use_mif         0

      lset coef_ram_param $fir_compiler_v3_2_analysis::ram::family          [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family ]
      lset coef_ram_param $fir_compiler_v3_2_analysis::ram::implementation  [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation ]
      lset coef_ram_param $fir_compiler_v3_2_analysis::ram::mem_type        [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb ] == 1 } {
        lset coef_ram_param $fir_compiler_v3_2_analysis::ram::write_mode    1
      } else {
        lset coef_ram_param $fir_compiler_v3_2_analysis::ram::write_mode    0
      }
      lset coef_ram_param $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce ]
      lset coef_ram_param $fir_compiler_v3_2_analysis::ram::use_mif         1

      #Data
      if { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem ] == 1 } {
        set lat_data 0
      } elseif { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb ] == 1 } {
        set lat_data [ fir_compiler_v3_2_analysis::ram::lat $data_ram_param 2 ]
      } elseif { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb ] == 1 } {
        set lat_data [ fir_compiler_v3_2_analysis::ram::lat $data_ram_param 2 ]
      } else {
        set lat_data [ fir_compiler_v3_2_analysis::ram::lat $data_ram_param 1 ]
      }

      #Coef
      if { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload ] == 1 } {
        
        if { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_dram } {
          set lat_coef [ fir_compiler_v3_2_analysis::ram::lat $coef_ram_param 2 ]
        } else {
          set lat_coef [ fir_compiler_v3_2_analysis::ram::lat $coef_ram_param 2 ]
        }

      } elseif { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb ] == 1 } {
        set lat_coef [ fir_compiler_v3_2_analysis::ram::lat $coef_ram_param 2 ]
      } else {
        if { [ lindex $param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth ] > 1 } {
          set lat_coef [ fir_compiler_v3_2_analysis::ram::lat $coef_ram_param 1 ]
        } else {
          set lat_coef 0
        }
      }

      set latency $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_default
      lset latency $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data $lat_data
      lset latency $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef $lat_coef
      
      return $latency

    }

  }

  #-----------------------------------------------------------------
  namespace eval filt_arm_add_casc {

    namespace import ::fir_compiler_v3_2_utils::num_BRAM

    set param_default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]

    set family              0
    set implementation      1
    set num_taps            2
    set inter_we_cycles     3
    set inter_tap_delay     4
    set symmetric           5
    set pre_add_func        6
    set data_mem_type       7
    set coef_mem_type       8
    set data_comb           9
    set data_coef_comb      10
    set data_cascade        11
    set no_data_mem         12
    set coef_mem_depth      13
    set p_src               14
    set data_sign           15
    set coef_sign           16
    set has_ce              17
    set reload              18
    set reload_strms        19
    set reload_depth        20
    set output_index        21
    set output_src          22
    set num_independant_col 23
    set num_split_col       24
    set inter_split_col_dly 25
    #set col_len             26
    set dynamic_opcode      26
    set sym_para_struct     27
    set para_casc_we_src    28
    set resource_opt        29
    set data_width          30
    set datapath_mem_type   31
    set col_len             32
    set odd_symmetry        33
    # not done just now #add on another 7 locations in the default for the column lenghts

    #lset param_default $col_len [ list 0 0 0 0 0 0 0 0 ]

    set lat_default [ list 0 0 0 0 0 0 0 0 0 ]
    set lat_cascaded              0
    set lat_tap                   1
    set lat_sym_inter_buff_depth  2
    set lat_pre_add_ipreg         3
    set lat_pre_add_midreg        4
    set lat_coef_addr_extra_delay 5
    set lat_datapath_BRAM         6
    set lat_sym_struct_slice_est  7
    set lat_data_addr_extra_delay 8

    proc calc_num_split_col { num_taps col_len } {
      set num_cols 0
      set tap_count [ lindex $col_len 0 ]
      
      while { $tap_count < $num_taps } {
        if { $num_cols < 9 } { set num_cols [ expr { $num_cols  + 1 } ] }
        #puts "---> tap_count : $tap_count"
        #puts "---> col_len   : $col_len"
        #puts "---> num_cols  : $num_cols"
        set tap_count [ expr { $tap_count + [ lindex $col_len $num_cols ] } ]
      }

      return [ expr { $num_cols + 1 } ]
    }

    proc lat { param } {
      
      set lat_cascaded              0
      set lat_tap                   0
      set lat_sym_inter_buff_depth  0
      set lat_pre_add_ipreg         0
      set lat_pre_add_midreg        0
      set lat_coef_addr_extra_delay 0
      set lat_data_addr_extra_delay 0

      set mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth ]
      lset mem_x $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric ]

      set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_x ]

      set tap_x $fir_compiler_v3_2_analysis::emb_calc::param_default
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::family                  [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::implementation          [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::pre_add                 [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::pre_add_func            [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram &&
           [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem ] == 0 } {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::pre_add_ipreg         1
      } else {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::pre_add_ipreg         0
      }
      if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width ] > [ fir_compiler_v3_2_analysis::addsub::get_adder_max [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ] ] } {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::pre_add_midreg        1
      } else {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::pre_add_midreg        0
      }
      if { [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] } ] > 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade ] == 0 } {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::a_delay               1
      } else {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::a_delay               0
      }
      if { [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] } ] < 0  } {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::b_delay               1
      } else {
        lset tap_x $fir_compiler_v3_2_analysis::emb_calc::b_delay               0
      }
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::a_src                   [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::a_sign                  [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::b_sign                  [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::d_sign                  [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign ]
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::reg_opcode              0
      lset tap_x $fir_compiler_v3_2_analysis::emb_calc::implement_extra_b_dly   0

      #puts "tap_x: $tap_x"

      set tap_x_dtls [ fir_compiler_v3_2_analysis::emb_calc::dtls $tap_x ]

      set lat_cascaded [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps ] - 1
                                + ( ( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col ] - 1 ) * ( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly ] +1 ) ) } ]
      
      if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] < [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] } {
        set lat_tap [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + [ lindex $tap_x_dtls $fir_compiler_v3_2_analysis::emb_calc::dtls_latency ] } ]
      } else {
        set lat_tap [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] + [ lindex $tap_x_dtls $fir_compiler_v3_2_analysis::emb_calc::dtls_latency ] } ]
      }
      
      if { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ] ] == true &&
           [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ] != "spartan3adsp" } {
        set lat_tap [ expr { $lat_tap + [ lindex $tap_x $fir_compiler_v3_2_analysis::emb_calc::b_delay ] } ]
      }

      set num_BRAMs 0
      set sym_struct_slice_est 0

      if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem ] == 1 &&
           ( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 ||
             [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 3 ) &&
           [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric ] == 1 } {

        set lat_sym_inter_buff_depth [ expr {  ( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps ] - 1 )
                                              *( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 3 } {
          set lat_sym_inter_buff_depth [ expr { $lat_sym_inter_buff_depth * 2 } ]
          if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry ] == 1 } {
            set lat_sym_inter_buff_depth [ expr { $lat_sym_inter_buff_depth + [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 } ]
          }
        }

        #BRAMs used in nd para struct due to delays , also calc slice est for two structures
        set srl16base_depth 17
        if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ] == "virtex5" } {
          set srl16base_depth 33
        }
        for { set tap_num 1 } { $tap_num <= [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps ] } { incr tap_num } {
          set ram_depth [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps ] - $tap_num )* ( [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]

            if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 3 } {
              set ram_depth [ expr { $ram_depth * 2 } ]
              if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry ] == 0 } {
                set ram_depth [ expr { $ram_depth + [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 } ]
              }
            }

          if { [ fir_compiler_v3_2_analysis::delay::use_bram $ram_depth [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ] ] == 1 } {
            set num_BRAMs [ expr { $num_BRAMs + [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width ] $ram_depth [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::family ] ] } ]
          }
          
          set tap_slices [ expr { int(ceil( double( $ram_depth ) / $srl16base_depth)) } ]
          
          if { [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {
            set tap_slices [ expr { $tap_slices + 1 } ]
          }

          set tap_slices [ expr { $tap_slices * [ lindex $param $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width ] } ]
          
          set sym_struct_slice_est [ expr { $sym_struct_slice_est + $tap_slices } ]
        }

      }

      set lat_pre_add_ipreg         [ lindex $tap_x_dtls $fir_compiler_v3_2_analysis::emb_calc::dtls_pre_add_ipreg ]
      set lat_pre_add_midreg        [ lindex $tap_x_dtls $fir_compiler_v3_2_analysis::emb_calc::dtls_pre_add_midreg ]
      set lat_coef_addr_extra_delay [ lindex $tap_x_dtls $fir_compiler_v3_2_analysis::emb_calc::dtls_extra_b_dly ]
      set lat_data_addr_extra_delay [ lindex $tap_x_dtls $fir_compiler_v3_2_analysis::emb_calc::dtls_extra_a_dly ]


      set latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_default
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded               $lat_cascaded
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap                    $lat_tap
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth   $lat_sym_inter_buff_depth
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_pre_add_ipreg          $lat_pre_add_ipreg
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_pre_add_midreg         $lat_pre_add_midreg
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_coef_addr_extra_delay  $lat_coef_addr_extra_delay
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay  $lat_data_addr_extra_delay
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM          $num_BRAMs
      lset latency $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est   $sym_struct_slice_est

      return $latency

    }
  }

  #-----------------------------------------------------------------
  namespace eval data_address {

    set param_default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
    set family              0
    set implementation      1
    set mem_type            2
    set base_cnt            3
    set block_cnt           4
    set symmetric           5
    set addr_width          6
    set sym_addr_width      7
    set combined            8
    set addr_packed         9
    set srl16_sequence      10
    set use_sym_cntrl       11
    set resource_opt        12
    set en_dly              13
    set block_end_dly       14
    set last_block_dly      15
    set write_phase_dly     16
    set sub_block_end_dly   17
    
    proc lat { param } {
      
      if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::data_address::base_cnt ] * [ lindex $param $fir_compiler_v3_2_analysis::data_address::block_cnt ] } ]  == 1 } {
        set no_base 1
      } else {
        set no_base 0
      }

      set latency 0

      if { $no_base == 1 } {
        if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::block_cnt ] > 1 && [ lindex $param $fir_compiler_v3_2_analysis::data_address::srl16_sequence ] == 1 } {

          if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {
            set latency 2
          } else {
            set latency 1
          }
        } else {
          set latency 0
        }
      } elseif { [ lindex $param $fir_compiler_v3_2_analysis::data_address::symmetric ] == 1 } {

        if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::mem_type ] > $fir_compiler_v3_2_analysis::c_srl16 } {

          if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::block_cnt ] > 1 &&
               [ lindex $param $fir_compiler_v3_2_analysis::data_address::addr_packed ] == 1 &&
               ! ( ( [ fir_compiler_v3_2_analysis::is_pow_of_2 [ lindex $param $fir_compiler_v3_2_analysis::data_address::base_cnt ] ] == 1 && [ lindex $param $fir_compiler_v3_2_analysis::data_address::combined ] == 0 ) ||
                   ( [ fir_compiler_v3_2_analysis::is_pow_of_2 [ lindex $param $fir_compiler_v3_2_analysis::data_address::base_cnt ] ] == 1 &&
                     [ fir_compiler_v3_2_analysis::is_pow_of_2 [ expr { [ lindex $param $fir_compiler_v3_2_analysis::data_address::base_cnt ] * [ lindex $param $fir_compiler_v3_2_analysis::data_address::block_cnt ] } ] ] == 1 &&
                     [ lindex $param $fir_compiler_v3_2_analysis::data_address::combined ] == 1 ) ) } then {
            set latency 3
          } else {
            set latency 2
          }

        } else {
          set latency 1
        }
      } else {

        if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::block_cnt ] > 1 && 
             [ lindex $param $fir_compiler_v3_2_analysis::data_address::addr_packed ] == 1 &&
             [ fir_compiler_v3_2_analysis::is_pow_of_2 [ lindex $param $fir_compiler_v3_2_analysis::data_address::base_cnt ] ] != 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::data_address::mem_type ] > $fir_compiler_v3_2_analysis::c_srl16  } {
          set latency 2
        } else {
          if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 &&
               [ lindex $param $fir_compiler_v3_2_analysis::data_address::srl16_sequence ] == 1 } {
            set latency 2
          } else {
            set latency 1
          }
        }
      }

      if { [ lindex $param $fir_compiler_v3_2_analysis::data_address::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $param $fir_compiler_v3_2_analysis::data_address::family ] ] == true  } {
        set latency [ expr { $latency + 1 } ]
      }

      return $latency

    }

  }
  
  #-----------------------------------------------------------------
  namespace eval coef_address {

    set param_default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
    set family              0
    set implementation      1
    set base_cnt            2
    set block_cnt           3
    set addr_packed         4
    set addr_width          5
    set num_filters         6
    set offset              7
    set has_ce              8
    set use_count_src       9
    set en_dly              10
    set base_max_dly        11
    set skip_base_max_dly   12
    set count_max_dly       13
    set filt_sel_dly        14
    set resource_opt        15

    proc lat { param } {

      set latency 0

      if { [ lindex $param $fir_compiler_v3_2_analysis::coef_address::family ] == "virtex5" } {
        set max_depth 64
      } else {
        set max_depth 32
      }

      set decode_rom_param $fir_compiler_v3_2_analysis::ram::param_default
      lset decode_rom_param $fir_compiler_v3_2_analysis::ram::family          [ lindex $param $fir_compiler_v3_2_analysis::coef_address::family ]
      lset decode_rom_param $fir_compiler_v3_2_analysis::ram::implementation  [ lindex $param $fir_compiler_v3_2_analysis::coef_address::implementation ]
      # if { [ lindex $param $fir_compiler_v3_2_analysis::coef_address::num_filters ] > $max_depth } {
#         lset decode_rom_param $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
#       } else {
        lset decode_rom_param $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
#      }
      lset decode_rom_param $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset decode_rom_param $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $param $fir_compiler_v3_2_analysis::coef_address::has_ce ]
      lset decode_rom_param $fir_compiler_v3_2_analysis::ram::use_mif         1

      set decode_rom_lat [ fir_compiler_v3_2_analysis::ram::lat $decode_rom_param  1 ]

      if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::coef_address::base_cnt ] * [ lindex $param $fir_compiler_v3_2_analysis::coef_address::block_cnt ] } ]  == 1 } {
        set no_base 1
      } else {
        set no_base 0
      }

      if { [ lindex $param $fir_compiler_v3_2_analysis::coef_address::num_filters ] > 1 && [ lindex $param $fir_compiler_v3_2_analysis::coef_address::addr_packed ] == 1 } {
        if { $no_base == 0 } {
          set latency [ expr { $decode_rom_lat + 2 } ]
        } else {
          set latency [ expr { $decode_rom_lat + 1 } ]
        }
      } else {
        if { $no_base == 0 } {
          set latency 1
        } else {
          set latency 0
        }
      }

      if { [ lindex $param $fir_compiler_v3_2_analysis::coef_address::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $param $fir_compiler_v3_2_analysis::coef_address::family ] ] == true &&
           ! ( [ lindex $param $fir_compiler_v3_2_analysis::coef_address::num_filters ] > 1 &&
               [ lindex $param $fir_compiler_v3_2_analysis::coef_address::addr_packed ] == 1 ) } {
        set latency [ expr { $latency + 1 } ]
        
        if { [ lindex $param $fir_compiler_v3_2_analysis::coef_address::num_filters ] > 1 &&
             $no_base == 1 } {
          set latency [ expr { $latency + 1 } ]
        }
      }

      return $latency

    }
  }

  #-----------------------------------------------------------------
  namespace eval coef_reload_cntrl {

    namespace import ::fir_compiler_v3_2_utils::log2ceil

    set param_default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
    
    set family              0
    set implementation      1
    set reload_base_cnt     2
    set coef_addr_packed    3
    set num_filts           4
    set coef_mem_depth      5
    set num_macs            6
    set has_hb              7
    set has_ce              8
    set coef_width          9
    set filt_sel_width      10
    set filt_sel_width_out  11
    set reload_width        12
    set resource_opt        13

    set lat_default [ list 0 0 ]
    set lat_filt_sel    0
    set lat_reload  1

    proc lat { param } {

      set lat_filt_sel 0
      set lat_reload 0

      if { [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }
      
      if { [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      set coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_width         [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth ] ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts ] * 2 } ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             0
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce ]
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::use_count_src      1
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::en_dly             0
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::base_max_dly       0
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::skip_base_max_dly  0
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::count_max_dly      0
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::filt_sel_dly       0
      lset coef_reload_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt ]

      set coef_reload_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_reload_addr_param ]
      
      set coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::param_default
      lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::family          [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family ]
      lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::implementation  [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation ]
      # if { [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts ] > $dram_mem_depth_thres } {
#         lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
#       } else {
        lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
#       }
      lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce ]
      lset coef_reload_page_ram $fir_compiler_v3_2_analysis::ram::use_mif         0

      set coef_reload_page_ram_lat [ fir_compiler_v3_2_analysis::ram::lat $coef_reload_page_ram 2 ]

      # if { [ lindex $param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts ] == 1 } {
#         set lat_filt_sel 1
#         set lat_reload [ expr { $coef_reload_addr_lat + 2 } ]
#       } else {
#         set lat_filt_sel [ expr { $coef_reload_page_ram_lat + 1 } ]
#         set lat_reload [ expr { $coef_reload_page_ram_lat + $coef_reload_addr_lat + 2 } ]
#       }

      set latency $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_default
      #lset latency $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel $lat_filt_sel
      lset latency $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel 2
      #lset latency $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_reload   $lat_reload
      lset latency $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_reload   [ expr { 1 + $coef_reload_addr_lat } ]

      return $latency

    }

  }


  #-----------------------------------------------------------------
  # Mac element functions
  #-----------------------------------------------------------------

  #-----------------------------------------------------------------
  namespace eval mac {

    namespace import ::fir_compiler_v3_2_utils::*

    #-----------------------------------------------------------------
    namespace eval reqs {
      set default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
      set family                  0
      set filter_type             1
      set deci_rate               2
      set inter_rate              3
      set rate_type               4
      set num_taps                5
      set clk_per_samp            6
      set num_channels            7
      set num_filts               8
      set symmetry                9
      set neg_symmetry            10
      set zero_packing_factor     11
      set coef_reload             12
      set data_width              13
      set coef_width              14
      set filt_sel_width          15
      set chan_width              16
      set output_width            17
      set data_mem_type           18
      set coef_mem_type           19
      set ipbuff_mem_type         20
      set opbuff_mem_type         21
      set datapath_mem_type       22
      set data_sign               23
      set coef_sign               24
      set reg_output              25
      set has_nd                  26
      set has_ce                  27
      set has_sclr                28
      set col_mode                29
      set col_1st_len             30
      set col_wrap_len            31
      set col_pipe_len            32
      set resource_opt            33
      set accum_width             34
      set round_mode              35
      set allow_approx            36
    }

    #-----------------------------------------------------------------
    namespace eval param {
    
      set default [ list 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ]
      set family                0
      set filter_type           1
      set deci_rate             2
      set inter_rate            3
      set num_taps              4
      set clk_per_samp          5
      set num_channels          6
      set num_filts             7
      set symmetry              8
      set odd_symmetry          9
      set neg_symmetry          10
      set zero_packing_factor   11
      set coef_reload           12
      set data_sign             13
      set coef_sign             14
      set single_mac            15
      set centre_mac            16
      set num_taps_calced       17
      set clk_per_chan          18
      set num_macs              19
      set base_count            20
      set base_data_space       21
      set base_coef_space       22
      set data_width            23
      set coef_width            24
      set data_mem_depth        25
      set data_mem_type         26
      set datasym_mem_depth     27
      set datasym_mem_type      28
      set datasym_mem_offset    29
      set data_combined         30
      set data_packed           31
      set no_data_mem           32
      set full_parallel         33
      set coef_mem_depth        34
      set coef_mem_type         35
      set coef_mem_offset       36
      set coef_packed           37
      set data_coef_combined    38
      #these are not in VHDL original, they would be in the records specific to the filter type
      #there is no point in doing the same in the TCL. They must be common over all structures
      #Some fields to do with utilisation, maybe better just returning RAM and DSP counts
      set num_dsp48s            39
      set num_bram              40
      set latency               41
      set sample_latency        42
      set buff_type             43
      set buff_page_depth       44
    }

    #-----------------------------------------------------------------
    #
    #----------------------------------------------------
    proc memory_calcs { reqs ip_param data_depth_unpacked data_depth_packed coef_depth_unpacked coef_depth_packed } {

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      set param $ip_param

      #puts "mem calcs 1"

      #Data
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_auto } {
        if { $data_depth_packed <= $srl16_mem_depth_thres } {
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type   $fir_compiler_v3_2_analysis::c_srl16
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth  $data_depth_packed
          lset param $fir_compiler_v3_2_analysis::mac::param::data_packed     1
        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type   $fir_compiler_v3_2_analysis::c_bram
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth  $data_depth_unpacked
          lset param $fir_compiler_v3_2_analysis::mac::param::data_packed     0
        }
      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
        if { $data_depth_packed > 1024 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type   $fir_compiler_v3_2_analysis::c_dram
        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type   $fir_compiler_v3_2_analysis::c_srl16
        }
        lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth  $data_depth_packed
        lset param $fir_compiler_v3_2_analysis::mac::param::data_packed     1
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type   $fir_compiler_v3_2_analysis::c_bram
        if { $data_depth_unpacked > [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] &&
             $data_depth_packed <= [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] } {
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth  $data_depth_packed
          lset param $fir_compiler_v3_2_analysis::mac::param::data_packed     1
        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth  $data_depth_unpacked
          lset param $fir_compiler_v3_2_analysis::mac::param::data_packed     0
        }
      }

      #puts "mem calcs 2"

      #Symmetric Data
      lset param $fir_compiler_v3_2_analysis::mac::param::data_combined       0
      lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth   0
      lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset  0

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {

          #puts "trying to pack sym"

          if { [ expr { 2 * pow(2, [ log2ceil $data_depth_unpacked ] ) } ] <= [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::data_combined       1
            lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth   $data_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset  [ expr { pow(2, [ log2ceil $data_depth_unpacked ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::data_packed         0
          } elseif { [ expr { 2*$data_depth_packed } ] <= [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::data_combined       1
            lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth   $data_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset  $data_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::data_packed         1
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ]
            lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset  0
          }
        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ]
        }
      }

      #puts "mem calcs 3"

      #Coef
      lset param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined  0
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset     0

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {

        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_auto } {

          if { [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ] <= $dram_mem_depth_thres } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          } elseif { [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ] <= $dram_mem_depth_thres } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          } else { 
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_bram
            if { [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ] <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ]
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
            } elseif { [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ] <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ]
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
            } else {
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ]
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
            }
          }
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
          # lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ]
#           lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          if { [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ] < [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          }
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
          lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_bram
          if { [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ] <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          } elseif { [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ] <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_packed ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  [ expr { pow(2, [ log2ceil $coef_depth_unpacked ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          }
        }
      } else {
        #Normal ( not reload )

        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_auto } {

          #puts "coef mem auto"

          if { $coef_depth_unpacked <= $srl16_mem_depth_thres } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          } elseif { $coef_depth_packed <= $srl16_mem_depth_thres } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_bram
            if { $coef_depth_unpacked <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_unpacked
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
            } elseif { $coef_depth_packed <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_packed
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
            } else {
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_unpacked
              lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
            }
          }
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          if { $coef_depth_unpacked <= $srl16_mem_depth_thres } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_dram
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          }
        } else {
          #force bram
          lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type   $fir_compiler_v3_2_analysis::c_bram
          if { $coef_depth_unpacked <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          } elseif { $coef_depth_packed <=  [ fir_compiler_v3_2_analysis::v4_bram_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] ] } {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     1
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth  $coef_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed     0
          }
        }

      
      #puts "mem calcs 4"

      #puts "coef_depth_unpacked: $coef_depth_unpacked"

      #puts [ fir_compiler_v3_2_analysis::v4_bram_depth [ fir_compiler_v3_2_analysis::get_max [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] ]
      #puts [ expr { $coef_depth_unpacked +
      #                pow(2, [ log2ceil [ fir_compiler_v3_2_analysis::get_max $data_depth_unpacked $coef_depth_unpacked ] ] ) } ]

        #Try to fit into 1 ram
        if { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram || [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram ) &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 0 &&
             $coef_depth_packed > 1 } {
  
          if { [ expr { $coef_depth_unpacked +
                        pow(2, [ log2ceil [ fir_compiler_v3_2_analysis::get_max $data_depth_unpacked $coef_depth_unpacked ] ] ) } ] <=
               [ fir_compiler_v3_2_analysis::v4_bram_depth [ fir_compiler_v3_2_analysis::get_max [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] ] } {
  

            #puts "mem calcs 4.1"

            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset     [ expr { pow(2, [ log2ceil [ fir_compiler_v3_2_analysis::get_max $data_depth_unpacked $coef_depth_unpacked ] ] ) } ]
            lset param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined  1
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth      $coef_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth      $data_depth_unpacked
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed         0
            lset param $fir_compiler_v3_2_analysis::mac::param::data_packed         0
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type       $fir_compiler_v3_2_analysis::c_bram
            lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type       $fir_compiler_v3_2_analysis::c_bram
            
            #puts "mem calcs 4.2"
  
          } elseif { [ expr { $coef_depth_packed + $data_depth_packed } ] <=
                        [ fir_compiler_v3_2_analysis::v4_bram_depth [ fir_compiler_v3_2_analysis::get_max [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] ] ] } {

            #puts "mem calcs 4.3"
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset     $data_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined  1
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth      $coef_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth      $data_depth_packed
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_packed         1
            lset param $fir_compiler_v3_2_analysis::mac::param::data_packed         1
            lset param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type       $fir_compiler_v3_2_analysis::c_bram
            lset param $fir_compiler_v3_2_analysis::mac::param::data_mem_type       $fir_compiler_v3_2_analysis::c_bram
            #puts "mem calcs 4.4"
          }
        }
      }

      return $param

    }

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc mod_lat_non_dsp48 { family single_mac has_accum accum_width split_post_adder extra_embcalc } {
      
      set lat_mod 0
      
      if { [ fir_compiler_v3_2_analysis::treat_as_s3ax $family ] == true &&
           $family != "spartan3adsp" } {

        if { $split_post_adder == $fir_compiler_v3_2_analysis::c_speed } {

          #Tap
          #if { $family == "spartan3" || $family == "virtex2" } {
            #Extra reg is added post mult18s
            set lat_mod [ expr { $lat_mod + 1 } ]
          #}
  
          #Accum and pc recombine
          set num_adders [ expr { ceil( double( $accum_width ) / [ fir_compiler_v3_2_analysis::addsub::get_adder_max $family ] ) } ]
  
          # puts "$accum_width"
#           puts "$num_adders"

          #Add the recombine of accum output or filt arm recombine
          set lat_mod [ expr { $lat_mod + $num_adders } ]
  
          if { $has_accum == 1 && $single_mac == 0 } {
            #Add the second recombine need if have accum
            set lat_mod [ expr { $lat_mod + $num_adders } ]
          }

          if { $extra_embcalc > 0 } {
            set lat_mod [ expr { $lat_mod + ($extra_embcalc*$num_adders) } ]
          }
        }
      }
      
      return $lat_mod
    }

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_single_rate { reqs } {

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ]
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::neg_symmetry ]
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] % 2 } ]
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      0
      }
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ]

      #puts "1"

      set pass 0
      while { $pass < 2 } {

        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ) ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ]
        #puts "---> PARAMETERS : $param"
        #set temp [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        #puts "---> BASE_COUNT : $temp"
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        #puts "---> PARAMETERS : $param"
        #set temp [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        #puts "---> NUM_MACS   : $temp"
        set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe
        #puts "---> NUM_MACS_SAFE   : $num_macs_safe"
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / $num_macs_safe ) } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
          set shorter_px_time 1
        } else {
          set shorter_px_time 0
        }

        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  1

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }

        #Not sure this is necessary for these calculations
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 0 } {
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            lset param $fir_compiler_v3_2_analysis::mac::param::data_width  30
          } else {
            lset param $fir_compiler_v3_2_analysis::mac::param::data_width  18
          }
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]
        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        #puts "before mem calcs"

        #puts "reqs: $reqs"

        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        #puts "after mem calcs"

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]
        #set mem_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_default

        #puts "2"

        #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        #puts "3"

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }
        set num_cyc_read_earily 0
        if { [ expr { $cyc_to_write + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] - $cyc_to_data_out } ] < 0 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
          set change_src 1
        } else {
          set change_src 0
        }
        if { $change_src == 0 &&
             [ expr { $num_cyc_read_earily - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ] > 0 } {
          if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
            set extra_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
          } else {
            set extra_dly [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] - 1 } ]
          }
        } else {
          set extra_dly 0
        }


        #-----
        set first_tap_extra_dly 0

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

          if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] == 1 &&
               [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] == 1 &&
               [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 0 } {
            set first_tap_extra_dly   [ expr { $first_tap_extra_dly + [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] } ]
          } elseif { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
            set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
          }
        }

        #filter arm settings
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          # if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] > [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] } {
#             set num_col [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] - [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] )
#                                    / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] } ]
#           } else {
#             set num_col 1
#           }
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }

        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 0 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade      1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade      0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]
        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        #set filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_default

        set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

        #puts "5"
        
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {

          set pre_sym_delay [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                      ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 33 )) } ]
          } else {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 17 )) } ]
          }
          set pre_sym_delay [ expr { $pre_sym_delay * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] } ]

          set sym_param_est_orig [ expr { $pre_sym_delay + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] } ]

          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     3
          set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          #change to diff struct
          if { [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] >= $sym_param_est_orig } {
            #change back as more efficient
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
            set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          }
        }

        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]

        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

      }
      #end loop


      #Accum
      set accum_req 1
      if { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] > 1 ) ||
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] == 1 } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** SINGLE RATE FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"

      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }
      
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      #puts "coef param: $coef_addr_param"


      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]
      #set reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_default

      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set we_sym_dly [ expr { $addr_cntrl_lat + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] } ]

      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }
      
      set latency [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + $round_spare_cycle
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]

      # puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"

      #puts "latency: $latency"

      #puts "6"

      #Calc num dsp48s
      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 && 
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Sym buffer mem
      set num_sym_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 && 
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 && 
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {

        #front end delay
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }

        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

      }

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram )
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram } ]


      #puts "dsp: $num_dsp48"

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  0

      #puts "9"

      return $param

    }

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    namespace eval deci_sym_wrap {
      
      namespace import ::fir_compiler_v3_2_utils::log2ceil

      set default [ list 0 0 0 0 0 0 0 0 0 0 0 0 ]
      set family            0
      set implementation    1
      set base_count        2
      set num_phases        3
      set num_channels      4
      set sym_type          5
      set driving_mem_type  6
      set driving_mem_lat   7
      set force_srl16_mem   8
      set has_ce            9
      set has_nd            10
      set data_width        11
    }

    proc lat_polyphase_decimation_sym_wrap { param } {
      
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::base_count ] > 1 } {
        #not supported latency
        return 0
      } else {
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::family ] == "virtex5" } {
          set srl16_mem_depth_thres 64
        } else {
          set srl16_mem_depth_thres 32
        }

        set ram_param $fir_compiler_v3_2_analysis::ram::param_default
        lset ram_param $fir_compiler_v3_2_analysis::ram::family          [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::family ]
        lset ram_param $fir_compiler_v3_2_analysis::ram::implementation  [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::implementation ]
        
        set parallel_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_phases ] } ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::sym_type ] == 1 } {
          set parallel_depth [ expr { $parallel_depth + 1 } ]
        }

        set parallel_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_channels ] *
                                      pow(2,[ log2ceil $parallel_depth ] ) } ]

        if { ( $parallel_depth <= $srl16_mem_depth_thres ) ||
             [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::force_srl16_mem ] == 1 } {
          lset ram_param $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
        } else {
          lset ram_param $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
        }
        lset ram_param $fir_compiler_v3_2_analysis::ram::write_mode      0
        lset ram_param $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::has_ce ]
        lset ram_param $fir_compiler_v3_2_analysis::ram::use_mif         0

        set ram_lat [ fir_compiler_v3_2_analysis::ram::lat $ram_param  1 ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::sym_type ] == 0 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::has_nd ] == 0 } {
          return $ram_lat
        } else {
          return [ expr { $ram_lat +1 } ]
        }
      }
    }
    
    proc num_bram_polyphase_decimation_sym_wrap { param } {

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::base_count ] > 1 } {
        #approximation for depth
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::sym_type ] == 0 } {
          set depth [ expr { (  2 *
                                [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_channels ] * 
                                [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_phases ] )
                              - [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_channels ] } ]
        } else {
          set depth [ expr { (  2 *
                                [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_channels ] *
                                [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_phases ] ) } ]
        }
        
        if { ( $depth <= $srl16_mem_depth_thres ) ||
             [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::force_srl16_mem ] == 1 } {
          return 0
        } else {
          return [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::data_width ] $depth [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::family ] ]
        }

      } else {
        set parallel_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_phases ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::sym_type ] } ]
        set parallel_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_channels ] * pow(2, [ log2ceil $parallel_depth ] ) } ]

        if { ( $parallel_depth <= $srl16_mem_depth_thres ) ||
             [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::force_srl16_mem ] == 1 } {
          return 0
        } else {
          return [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_phases ] [ lindex $param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::family ] ]
        }
      }
    }

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_decimation { reqs } {

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ]
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::neg_symmetry ]
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] % 2 } ]
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      0
      }
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ]

      #puts "1"

      set pass 0
      while { $pass < 2 } {

        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ]
        set taps_per_phase                            [ expr { ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] > 1 } {
          set shorter_px_time 1
        } else {
          set shorter_px_time 0
        }

        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ]

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }
        
        set output_rate [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        #puts "2"

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]
        
        #puts "2.1"

        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]


        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        #puts "2.2"

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

        #puts "3"

        set wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::default
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::family            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::family ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::implementation    1
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::base_count        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_phases        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::deci_rate ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::num_channels      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::sym_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::driving_mem_type  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::driving_mem_lat   [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ]
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::force_srl16_mem 1
        } else {
          lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::force_srl16_mem 0
        }
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::has_ce            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::has_nd            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset wrap_over_param $fir_compiler_v3_2_analysis::mac::deci_sym_wrap::data_width        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]

        set wrap_over_lat [ fir_compiler_v3_2_analysis::mac::lat_polyphase_decimation_sym_wrap $wrap_over_param ]

        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 0 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
          set para_sym_struct 1

          if {$wrap_over_lat >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::deci_rate ] } {
           set para_sym_struct 1
          } else {
           set para_sym_struct 2
          }

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
                   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 &&
                   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
          set para_sym_struct 1
        } else {
          set para_sym_struct 0
        }

        #puts "4"
        #-----

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 } {
          set data_dly_modifier -1
        } else {
          set data_dly_modifier 0
        }
        
        set first_tap_extra_dly 0

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

          if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
            set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
            set data_dly_modifier     [ expr { $data_dly_modifier + [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
          }
        }


        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }
        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] ) + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade      0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        if { $para_sym_struct == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     0
        }
        if { $shorter_px_time == 1 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        
        set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

        #puts "5"

      }
      #end loop

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                        )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 1
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::param::full_parallel ] == 1
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] >  1                                                 )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** DECIMATION FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }
      
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #Output buffer
      set has_output_buffer 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 } {
        set has_output_buffer 1
      }

      set output_buffer_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 } ]

      set output_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset output_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $output_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset output_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset output_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      set extra_opb_reg 0
      if { $has_output_buffer == 1 &&
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ lindex $output_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_dram } {
        set extra_opb_reg 1
      }

      if { $has_output_buffer == 1 } {
        set output_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $output_buffer  2 ]
      } else {
        set output_buffer_lat 0
      }

      #puts "6"

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]

     #  puts $data_addr_lat
#       puts "addr params"
#       puts $data_addr_param

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]

      # puts $coef_addr_lat
#       puts "addr params"
#       puts $coef_addr_param

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]
      lset reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] + 1 } ]

      #puts "7"

      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] > 1 ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        set filt_sel_lat 1
      } else {
        set filt_sel_lat 0
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 + $filt_sel_lat } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 + $filt_sel_lat - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set para_sym_addr_delay 0
      
      #puts "wrap over lat: $wrap_over_lat"
      #puts "data_dly_modifier: $data_dly_modifier"

      if { $para_sym_struct == 2 } {

         #do nothing

      } elseif { $para_sym_struct == 1 } {

        if { [ expr { [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] + $addr_cntrl_lat
                      + $data_dly_modifier } ] > $wrap_over_lat } {
          #do nothing
        } else {
          set para_sym_addr_delay [ expr { $wrap_over_lat -
                                           ( [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                                             + $addr_cntrl_lat
                                             + $data_dly_modifier ) } ]
        }
      }

      #puts "8"

      set opb_latency 0
      if { $has_output_buffer == 1 } {
        set opb_latency [ expr { $output_buffer_lat +
                                 $output_rate +
                                 $extra_opb_reg } ]
      }

      set latency [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + $para_sym_addr_delay
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + $round_spare_cycle
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder  ]
                           + $opb_latency
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]


     #  puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly "
#       puts "$para_sym_addr_delay"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "$opb_latency"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"
#       set temp [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
#                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
#                                    $accum_req \
#                                    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
#                                    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
#                                    $has_rounder  ]
#       puts "$temp"
#       puts "$rounder_delay"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Sym buffer mem
      set num_sym_buff_ram 0
      if { $para_sym_struct == 1 } {
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
        
        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

      }

      #Sym Wrap buffer
      set num_sym_wrap_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        set num_sym_wrap_buff_ram [ fir_compiler_v3_2_analysis::mac::num_bram_polyphase_decimation_sym_wrap $wrap_over_param ]
      }

      #Output buffer
      set num_op_buff_ram 0
      if { $has_output_buffer == 1 } {

        if { [ lindex $output_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {

          set num_op_buff_ram [ num_BRAM  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::output_width ] $output_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
      }

      # puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       puts "$num_sym_buff_ram"
#       puts "$num_sym_wrap_buff_ram "
#       puts "$num_op_buff_ram"
#       puts "Num macs: [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]"

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram
                              + $num_sym_wrap_buff_ram
                              + $num_op_buff_ram } ]


      set sample_latency [ expr { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] - 1 )) +1 } ]
      set buffer_type 0
      if { $has_output_buffer == 1 } {
        set buffer_type 2
      }
      set buffer_page_size [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   $sample_latency
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        $buffer_type
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  $buffer_page_size

      #puts "9"

      return $param
    }
    #end define_decimation

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_halfband { reqs } {
      
      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            1
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + 1 } ]

      set pass 0
      while { $pass < 2 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ]
        set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / 2 ) ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( $taps_per_phase / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        # set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        # lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        if { [ expr { $taps_per_phase % [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } ] > 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::num_macs [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] + 1 } ]
        }

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::base_count [ expr { $taps_per_phase +1 } ]
        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::base_count [ expr { ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) } ]
        }

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
          set shorter_px_time 1
        } else {
          set shorter_px_time 0
        }

        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] *
                                                                ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                                                                 - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] ) *
                                                                2 } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  2

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         2 } ]
        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         2 } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        
        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

         #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }
        set num_cyc_read_earily 0
        if { [ expr { $cyc_to_write + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] - $cyc_to_data_out } ] < 0 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
          set change_src 1
        } else {
          set change_src 0
        }
        if { $change_src == 0 &&
             [ expr { $num_cyc_read_earily - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ] > 0 } {
          if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
            set extra_dly [ expr { 2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]
          } else {
            set extra_dly [ expr { (2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) - 1 } ]
          }
        } else {
          set extra_dly 0
        }

        #-----
        set first_tap_extra_dly 0

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

          if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
            set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
          }
        }

        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]

          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }
        
        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { ( 2 * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] ) + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add_swapped
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        
        set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

        #puts "Sym para struct: [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ]"

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {

          set pre_sym_delay [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                      ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 33 )) } ]
          } else {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 17 )) } ]
          }
          set pre_sym_delay [ expr { $pre_sym_delay * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] } ]

          set sym_param_est_orig [ expr { $pre_sym_delay + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] } ]
          
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     3
          set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          #change to diff struct
          if { [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] >= $sym_param_est_orig } {
            #change back as more efficient
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
            set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          }
        }

        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]

        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

      }
      #end loop

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac] == 1 } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** HALFBAND FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }

      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #centre tap coef, only need to know memory type
      set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_dram
      set centre_tap_coef_mem_depth 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1  &&
            [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] * 2  } ] > $dram_mem_depth_thres } {
        set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_bram
        set centre_tap_coef_mem_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] * 2 } ]
      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] > $srl16_mem_depth_thres } {
        set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_bram
        set centre_tap_coef_mem_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      }
      #Not used for latency but need for utilisation

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ expr { 2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
        lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      $fir_compiler_v3_2_analysis::c_area
      } else {
        lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
      }

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]
      
      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set we_sym_dly [ expr { $addr_cntrl_lat + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] - 1 } ]

      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }
      
      set addr_cntrl_lat [ expr { $addr_cntrl_lat + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]

      set latency [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + $round_spare_cycle
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder  ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
                           
      # puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"
                           
      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Sym buffer mem
      set num_sym_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 && 
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        
        #front end delay
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] != 3 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }

        # puts "[ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ]"
#         puts "$num_sym_buff_ram"
        
        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

#         puts "$num_sym_buff_ram"

      }
      
      #centre tap coef mem
      set num_centre_coef_mem_ram 0
      if { $centre_tap_coef_mem_type == $fir_compiler_v3_2_analysis::c_bram && $accum_req == 1 } {
        set num_centre_coef_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $centre_tap_coef_mem_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram
                              + $num_centre_coef_mem_ram } ]

      # puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       puts "$num_sym_buff_ram"
#       puts "$num_centre_coef_mem_ram "

      #puts "dsp: $num_dsp48"

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  0

      #puts "I've been run !!"

      #temp return value
      return $param
    }
    #end defin halfband

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_hilbert { reqs } {
      
      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            1
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        1
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + 1 } ]
      
      set pass 0
      while { $pass < 2 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ]
        set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / 2 ) ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( $taps_per_phase / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) ) } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
          set shorter_px_time 1
        } else {
          set shorter_px_time 0
        }

        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * 2 } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  2

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         2 } ]
        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         2 } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]
        
        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

        #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }
        set num_cyc_read_earily 0
        if { [ expr { $cyc_to_write + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] - $cyc_to_data_out } ] < 0 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
          set change_src 1
        } else {
          set change_src 0
        }
        if { $change_src == 0 &&
             [ expr { $num_cyc_read_earily - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ] > 0 } {
          if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
            set extra_dly [ expr { 2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]
          } else {
            set extra_dly [ expr { (2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) - 1 } ]
          }
        } else {
          set extra_dly 0
        }

        #-----
        set first_tap_extra_dly 0

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

          if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
            set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
          }
        }

        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }

        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { ( 2 * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] ) + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        
        set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {
          
          set pre_sym_delay [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                      ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 33 )) } ]
          } else {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 17 )) } ]
          }
          set pre_sym_delay [ expr { $pre_sym_delay * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] } ]

          set sym_param_est_orig [ expr { $pre_sym_delay + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] } ]
          
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     3
          set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          #change to diff struct
          if { [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] >= $sym_param_est_orig } {
            #change back as more efficient
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
            set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          }
        }

        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]
        
        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

      }
      #end loop

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] == 1  ||
           ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] > 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count] == 1 ) } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** HILBERT FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }
      
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ expr { 2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]

      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set we_sym_dly [ expr { $addr_cntrl_lat + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] } ]
      
      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }

      set latency [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + $round_spare_cycle
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder  ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]

      # puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"
                           
      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Sym buffer mem
      set num_sym_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 && 
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {

        #front end delay
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
        
        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

      }

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram } ]

      #puts "dsp: $num_dsp48"

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  0

      #puts "Run hilbert lat"

      return $param

    }
    #end hilbert

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_interpolated { reqs } {

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ]
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::neg_symmetry ]
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] % 2 } ]
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      0
      }
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ]

      set pass 0
      while { $pass < 2 } {

        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ) ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
          set shorter_px_time 1
        } else {
          set shorter_px_time 0
        }
        
        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] * 
                                         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ] } ]
        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ] } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        
        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]
        #set mem_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_default

        #puts "2"

        #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }
        set num_cyc_read_earily 0
        if { [ expr { $cyc_to_write + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] - $cyc_to_data_out } ] < 0 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
          set change_src 1
        } else {
          set change_src 0
        }
        if { $change_src == 0 &&
             [ expr { $num_cyc_read_earily - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ] > 0 } {
          if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
            set extra_dly [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]
          } else {
            set extra_dly [ expr { ([ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) - 1 } ]
          }
        } else {
          set extra_dly 0
        }

        #-----
        set first_tap_extra_dly 0

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

          if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
            set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
          }
        }

        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]

          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }
        
        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] ) + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        
        set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {
          
          set pre_sym_delay [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                      ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 33 )) } ]
          } else {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 17 )) } ]
          }
          set pre_sym_delay [ expr { $pre_sym_delay * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] } ]

          set sym_param_est_orig [ expr { $pre_sym_delay + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] } ]

          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     3
          set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          #change to diff struct
          if { [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] >= $sym_param_est_orig } {
            #change back as more efficient
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
            set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          }
        }

        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]

        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

      }
      #end loop

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] == 1  ||
           ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] > 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count] == 1 ) } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                         )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** INTERPOLATED FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"

      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }

      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]

      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set we_sym_dly [ expr { $addr_cntrl_lat + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] } ]

      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }
      
      set latency [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + $round_spare_cycle
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder  ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]

      # puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"

      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #puts "dsp: $num_dsp48"

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Sym buffer mem
      set num_sym_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 && 
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {

        #front end delay
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }

        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

      }

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  0

      #puts "Run interpolated lat"

      return $param

    }
    #end interpolated

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_interpolation { reqs } {

      #puts "0.1"

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            0
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]

      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]
      set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) ) } ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) ) } ]
      set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe

      set base_count_op_rate                        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) ) } ]

      #puts "1"

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < $base_count_op_rate } {
        set shorter_px_time 1
      } else {
        set shorter_px_time 0
      }

      set sing_chan_short_block 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] == 1 &&
           $shorter_px_time == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] > [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ] } {
        set sing_chan_short_block 1
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]

      lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
      lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  1

      lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
      lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        if {[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
        }
      }

      #Mem calcs
      set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]
      set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

      set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                       pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

      set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                       pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

      set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

      set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

      set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]
      #set mem_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_default

      #puts "1"

      #-----
      set first_tap_extra_dly 0

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

        if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
          set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
        }
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
        set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
        #no supported yet (independant columns)
        set num_col 1
      } else {
        set num_col 1
      }

      set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
      } else {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
      }
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
      } else {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
      }
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    1
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

      #puts "4"

      set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]

      set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] == 1  ||
           ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] > 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count] == 1 ) } {
        set accum_req 0
      }
      
      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** INTERPOLATION (NON-SYMMETRIC) FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"

      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"

      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }
      
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #INput buffer
      set has_input_buffer 0
      set input_buffer_cntrl_dly 0
      set input_buffer_lat 0
      set input_buffer_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 } ]

      if { $shorter_px_time == 1 } {
        set input_buffer_cntrl_dly 1
      }

      set input_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset input_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset input_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $input_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::ipbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::ipbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset input_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset input_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset input_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset input_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset input_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 } {
        set has_input_buffer 1
        set input_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $input_buffer  2 ]
        
        if { $shorter_px_time == 1 } {
         set input_buffer_lat [ expr { $input_buffer_lat + 1 } ]
        }
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]

      if { $sing_chan_short_block == 1 ||
           $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      #puts "5"

      if { $has_input_buffer == 1 } {
        if { $input_buffer_lat > $addr_cntrl_lat } {
          set addr_dly [ expr { $input_buffer_lat - $addr_cntrl_lat } ]
          set filt_dly 0
        } else {
          set addr_dly 0
          set filt_dly [ expr { $addr_cntrl_lat - $input_buffer_lat } ]
        }
      } else {
        set addr_dly 0
        set filt_dly $addr_cntrl_lat
      }
      
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { $filt_dly - [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] } ] <= 0 } {
          set addr_dly [ expr { $addr_dly + abs( $filt_dly - [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] ) } ]
        } else {
          #do nothing
        }
      }

      set latency [ expr { $has_input_buffer
                           + $base_count_op_rate
                           + $addr_cntrl_lat
                           + $addr_dly
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]  \
                                                 $has_rounder ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]

      # puts "latency components:"
#       puts "$has_input_buffer"
#       puts "$base_count_op_rate"
#       puts "$addr_cntrl_lat"
#       puts "$addr_dly"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"

      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #puts "dsp: $num_dsp48"

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Input buffer
      set num_ip_buff_ram 0
      if { $has_input_buffer == 1 } {
        if { [ lindex $input_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
          set num_ip_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $input_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
      }

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_ip_buff_ram } ]


      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      if { $has_input_buffer == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        1
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        0
      }
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]

      #puts "Run interpolation lat"

      return $param

    }
    #end interpolation
    
    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_halfband_decimation { reqs } {

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            1
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + 1 } ]

      set pass 0
      while { $pass < 2 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        #lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] * 2 } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] * 2 ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / 2 ) ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( $taps_per_phase / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        # set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        # lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        if { [ expr { $taps_per_phase % [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } ] > 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::num_macs [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] + 1 } ]
        }
        
        set base_count_op_rate [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::base_count $taps_per_phase

        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::base_count [ expr { ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) } ]
        }

        ######
        set shorter_px_time 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 0 } {
          if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < $base_count_op_rate } {
            set shorter_px_time 1
          }
        } else {
          if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + 1 } ] < $base_count_op_rate } {
            set shorter_px_time 1
          }
        }

        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] *
                                                               [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] *
                                                               2 } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  1

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }
        
        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]
        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

        #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }
        set num_cyc_read_earily 0
        if { [ expr { $cyc_to_write + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] - $cyc_to_data_out } ] < 0 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
          set change_src 1
        } else {
          set change_src 0
        }
        if { $change_src == 0 &&
             [ expr { $num_cyc_read_earily - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ] > 0 } {
          if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
            set extra_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
          } else {
            set extra_dly [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] - 1 } ]
          }
        } else {
          set extra_dly 0
        }
        
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }

        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func        $fir_compiler_v3_2_analysis::c_preadd_add
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]
        
        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        
        #set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {
          
          set pre_sym_delay [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                      ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 33 )) } ]
          } else {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 17 )) } ]
          }
          set pre_sym_delay [ expr { $pre_sym_delay * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] } ]

          set sym_param_est_orig [ expr { $pre_sym_delay + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] } ]
          
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     3
          set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          #change to diff struct
          if { [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] >= $sym_param_est_orig } {
            #change back as more efficient
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
            set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          }
        }

        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]

        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

      }
      #end loop

      #Accum
      set accum_req 1

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** DECIMATING HALFBAND FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"

      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }

      #Treat as estimate, noticed has_rounder condition is not a 100% replica of VHDL
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #INput buffer
      set input_buffer_lat 0
      set input_buffer_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 4 } ]
      
      set input_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset input_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset input_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $input_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::ipbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::ipbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset input_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset input_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset input_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset input_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset input_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      set input_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $input_buffer  2 ]

      #centre tap coef, only need to know memory type
      set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_dram
      set centre_tap_coef_mem_depth 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1  &&
            [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] * 2  } ] > $dram_mem_depth_thres } {
        set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_bram
        set centre_tap_coef_mem_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] * 2 } ]
      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] > $srl16_mem_depth_thres } {
        set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_bram
        set centre_tap_coef_mem_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      }
      #Not used for latency but need for utilisation

      
      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]

      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ expr { $input_buffer_lat + 1 } ] > $addr_cntrl_lat } {
        set addr_dly [ expr { ( $input_buffer_lat + 1 ) - $addr_cntrl_lat } ]
        set filt_dly 0
        set data_dly 0
      } else {
        set addr_dly 0
        set filt_dly [ expr { $addr_cntrl_lat - ($input_buffer_lat + 1 ) } ]
        set data_dly [ expr { $addr_cntrl_lat - ($input_buffer_lat + 1 ) } ]
      }

      if { $data_dly == 0 && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {
        set filt_dly [ expr { $filt_dly + 1 } ]
        set addr_dly [ expr { $addr_dly + 1 } ]
        set data_dly [ expr { $data_dly + 1 } ]
      }
      
      #if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] > 1 } {
        set addr_dly [ expr { $addr_dly + 2 } ]
        set data_dly [ expr { $data_dly + 2 } ]
      #}

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { $filt_dly - [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] } ] <= 0 } {
          set addr_dly [ expr { $addr_dly + abs( $filt_dly - [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] ) } ]
        } else {
          #do nothing
        }
      }

      set we_sym_dly [ expr { $addr_cntrl_lat + $addr_dly + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] } ]

      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_dly [ expr { $addr_dly + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }

      set latency [ expr { 1
                           + $base_count_op_rate
                           + $addr_cntrl_lat
                           + $addr_dly
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder  ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
      
      # puts "latency components:"
#       puts "1"
#       puts "$base_count_op_rate"
#       puts "$addr_cntrl_lat"
#       puts "$addr_dly"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#        puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"


      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1  &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] != 1 } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #puts "dsp: $num_dsp48"

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Sym buffer mem
      set num_sym_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 && 
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {

        #front end delay
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }

        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

      }

      #Input buffer
      set num_ip_buff_ram 0
      if { [ lindex $input_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_ip_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $input_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #centre tap coef mem
      set num_centre_coef_mem_ram 0
      if { $centre_tap_coef_mem_type == $fir_compiler_v3_2_analysis::c_bram && 
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] != 1 } {
        set num_centre_coef_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $centre_tap_coef_mem_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Second phase delay
      set num_phase2_ram 0
      set phase2_delay [ expr { ( int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] / 2 )
                                  * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] )
                                 - ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] - 1 ) } ]
      
      if { [ fir_compiler_v3_2_analysis::delay::use_bram $phase2_delay [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {

        set num_phase2_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $phase2_delay [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]

      }

      # puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       puts "$num_sym_buff_ram"
#       puts "$num_centre_coef_mem_ram "
#       puts "$num_ip_buff_ram"
#       puts "$num_phase2_ram"

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram
                              + $num_ip_buff_ram
                              + $num_centre_coef_mem_ram 
                              + $num_phase2_ram } ]


      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        1
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 } ]

      #puts "Run halfband deci lat"

      return $param

    }
    #end halfband decimtaion

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_halfband_interpolation { reqs } {

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            1
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + 1 } ]

      set pass 0
      while { $pass < 2 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ]
        set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / 2 ) ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( $taps_per_phase / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
        # set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        # lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
        }

        if { [ expr { $taps_per_phase % [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } ] > 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::num_macs [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] + 1 } ]
        }

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::base_count $taps_per_phase
        } else {
          lset param $fir_compiler_v3_2_analysis::mac::param::base_count [ expr { ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) } ]
        }

        set shorter_px_time 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 0 } {
          if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
            set shorter_px_time 1
          }
        } else {
          if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + 1 } ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
            set shorter_px_time 1
          }
        }
        
        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }
        
        set output_rate [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] / 2 ) } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] *
                                                               [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] *
                                                               2 } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate 1
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  1

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]
        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

         set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

        #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }
        set num_cyc_read_earily 0
        if { [ expr { $cyc_to_write + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] - $cyc_to_data_out } ] < 0 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
          set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
        }
        if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
          set change_src 1
        } else {
          set change_src 0
        }
        if { $change_src == 0 &&
             [ expr { $num_cyc_read_earily - [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ] > 0 } {
          if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
            set extra_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
          } else {
            set extra_dly [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] - 1 } ]
          }
        } else {
          set extra_dly 0
        }
        
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }

        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add_swapped
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]
        
        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
        
        #set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]
        
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct ] == 1 } {
          
          set pre_sym_delay [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                      ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
          if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 33 )) } ]
          } else {
            set pre_sym_delay [ expr { int(ceil( double( $pre_sym_delay ) / 17 )) } ]
          }
          set pre_sym_delay [ expr { $pre_sym_delay * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ] } ]

          set sym_param_est_orig [ expr { $pre_sym_delay + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] } ]
          
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     3
          set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          #change to diff struct
          if { [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_struct_slice_est ] >= $sym_param_est_orig } {
            #change back as more efficient
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
            set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
          }
        }

        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]
                                    
        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }

      }
      #end loop
    
      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1  } {
        set accum_req 0
      }
      
      set accum_extra_dly 0
      if { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] != 1 &&
           $accum_req == 1 } {
        set accum_extra_dly 1
      }

      # Rounder block
      set has_rounder 0
      if {     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                    )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                  )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf
            && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 2 && $shorter_px_time == 0                  )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** INTERPOLATING HALFBAND FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"

      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }

      #As with half deci, has_rounder is not 100% replica of VHDL. Treat as estimate
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0 } {
        set round_spare_cycle 1
      }

      #centre tap coef, only need to know memory type
      set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_dram
      set centre_tap_coef_mem_depth 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1  &&
            [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] * 2  } ] > $dram_mem_depth_thres } {
        set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_bram
        set centre_tap_coef_mem_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] * 2 } ]
      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] > $srl16_mem_depth_thres } {
        set centre_tap_coef_mem_type $fir_compiler_v3_2_analysis::c_bram
        set centre_tap_coef_mem_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      }
      #Not used for latency but need for utilisation

      #Output buffer
      set output_buffer_depth [ expr { 2 * pow(2, [ log2ceil [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 } ] ] ) } ]

      set output_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset output_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $output_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset output_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset output_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      set output_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $output_buffer  2 ]

      if { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] ) ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_hold_struct 1
      } else {
        set addr_hold_struct 0
      }
      
      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]
      
      if { $addr_hold_struct == 1 } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set we_sym_dly [ expr { $addr_cntrl_lat + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ] } ]

      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }

      set latency [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $accum_extra_dly
                           + $rounder_delay
                           + $round_spare_cycle
                           + 1
                           + $output_buffer_lat
                           + $output_rate
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 [ expr { $has_rounder + $accum_extra_dly} ] ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
                           
      # puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "$accum_extra_dly"
#       puts "1"
#       puts "$output_buffer_lat"
#       puts "$output_rate"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"
#       set temp [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
#                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
#                                                  $accum_req \
#                                                  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
#                                                  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
#                                                  [ expr { $has_rounder + $accum_extra_dly} ]  ]
#       puts "$temp"
#       puts "$rounder_delay"

      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $accum_extra_dly == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }

      #puts "dsp: $num_dsp48"

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Sym buffer mem
      set num_sym_buff_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 && 
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        
        #front end delay
        set delay_depth [  expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] -
                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] ) *
                                  ( [ lindex $filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay ] - 1 ) } ]
        if { [ fir_compiler_v3_2_analysis::delay::use_bram $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == 1 } {
          set num_sym_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $delay_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
        
        set num_sym_buff_ram [ expr { $num_sym_buff_ram + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_datapath_BRAM ] } ]

      }

      #centre tap coef mem
      set num_centre_coef_mem_ram 0
      if { $centre_tap_coef_mem_type == $fir_compiler_v3_2_analysis::c_bram && $accum_req == 1 } {
        set num_centre_coef_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $centre_tap_coef_mem_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Output buffer
      set num_op_buff_ram 0
      if { [ lindex $output_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {

        set num_op_buff_ram [ num_BRAM  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::output_width ] $output_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      # puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       puts "$num_sym_buff_ram"
#       puts "$num_centre_coef_mem_ram "
#       puts "$num_op_buff_ram"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]"

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_sym_buff_ram
                              + $num_centre_coef_mem_ram
                              + $num_op_buff_ram } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        2
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 } ]

      #puts "Run halfband inter lat"

      return $param

    }
    #end halfband interpolation

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_sympair_interpolation { reqs } {

      #puts "start sym pair"

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ]
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::neg_symmetry ]
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] % 2 } ]
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      0
      }
      
      set odd_and_even 0
      if { [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] % 2 } ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
        set odd_and_even 1
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) } ]
      if { $odd_and_even == 0 && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] + int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] / 2 ) + 1 } ]
      }
      if { $odd_and_even == 1 && [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] % int(pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] )) } ] > 0 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] + 1 } ]
      }

      #puts "0.5"

      set pass 0
      while { $pass < 2 } {

        lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]
        set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) ) } ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) ) } ]
        set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
        lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe

        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) ) } ]

        if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]  * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ] < [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] } {
          set shorter_px_time 1
        } else {
          set shorter_px_time 0
        }

        if { $pass == 0 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        }

        lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
        lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  1

        lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
          lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        }
        
        set output_rate [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]

        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
        
        #puts "1"

        #Mem calcs
        set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ] ) *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

        set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

        set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

        set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                         pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]


        set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

        set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

        set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

        set sym_calc_odd_sym  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

        #Symetric calcs - needed to work out is control has been delayed to line up sym data
        set cyc_to_data_out [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_data ] + 1 } ]
        set cyc_to_write 0

        #puts "3"

        if { [ lindex $mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
          set cyc_to_write  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        }

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 } {

          set num_cyc_read_earily 0
          if { [ expr { $cyc_to_write + $sym_calc_odd_sym - $cyc_to_data_out } ] < 0 } {
            set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
          }
          if { $sym_calc_odd_sym == 1 } {
            set num_cyc_read_earily [ expr { $num_cyc_read_earily + 1 } ]
          }
          if { $num_cyc_read_earily >= [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] } {
            set change_src 1
          } else {
            set change_src 0
          }
          if { $change_src == 0 &&
               [ expr { $num_cyc_read_earily - $sym_calc_odd_sym } ] > 0 } {
            if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
              set extra_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
            } else {
              set extra_dly [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] - 1 } ]
            }
          } else {
            set extra_dly 0
          }

          if { $odd_and_even == 1 && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {
            
            if { $change_src == 0 &&
                 [ expr { $num_cyc_read_earily - $sym_calc_odd_sym } ] > 0 } {
              if { [ expr { $cyc_to_data_out - $num_cyc_read_earily } ] <= $cyc_to_write } {
                set extra_dly [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] + 1 } ]
              } else {
                set extra_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
              }
            } else {
              set extra_dly 0
            }
          }
          
          if { $change_src == 1 } {
            
            set extra_dly [ expr { (( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] - $sym_calc_odd_sym ) *
                                       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] )
                                      - [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] } ]

            if { $odd_and_even == 1 && [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {
              set extra_dly [ expr { $extra_dly + 1 } ]
            }
          }

        } else {
          
          if { $odd_and_even == 1 &&  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]  == 1 } {
            set sym_calc_odd_sym 0
          }

          set change_src 0
          set extra_dly 0
          set num_cyc_read_earily $sym_calc_odd_sym

        }
        

        set first_tap_extra_dly 0

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

          if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
            set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
          }
        }
        
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
          set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
          #no supported yet (independant columns)
          set num_col 1
        } else {
          set num_col 1
        }
        
        #puts "3.5"

        set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
        #not correct value but doesn't need to be
        #lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] ) + 1 } ]
        if { $odd_and_even == 1 } {

          if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
          } else {
            lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay 2
          }
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
        }
        #this is set for symmetic path depth, fir_compiler_v3_2_analysis has two parameters but its sym param thats important to
        #see if symmetry should be diasable, although this is no longer necessary. It is not enalbled I don't think
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade      0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 &&
             $odd_and_even == 1 } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
        }
        if { $odd_and_even == 1 &&
             ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 || 
              ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 &&
                [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 ) ) } {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     1
        } else {
          lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     0
        }
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    1
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

        #puts "4"

        set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]

        set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]
        
        set p_WE_SYM_OUT  [ expr {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] +
                                    ( $cyc_to_data_out - 1 ) -
                                    $num_cyc_read_earily } ]

        #Check if disable symmetry
        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
             $pass == 0 &&
             [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_sym_inter_buff_depth ] > 1089 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_dist } {
          lset param $fir_compiler_v3_2_analysis::mac::param::symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry 0
          lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
          set pass 1
        } else {
          set pass 2
        }
      }
      # end loop

      #Accum
      set accum_lat 1
      set accum_req 1
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" &&
           [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] + 1 } ] > 35 &&
           !( $odd_and_even == 1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 ) } {
        set accum_lat 2
      }
      if { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
           ( [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] % 2 } ] > 0 ||
            ( $odd_and_even == 1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] != 2 ) ) } {
        set accum_lat 2
      }
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1  && $odd_and_even == 1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 } {
        set accum_lat 0
        set accum_req 0
      }

      set gen_extra_cycle 0
      if {  $shorter_px_time==1 && $odd_and_even==1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]==2
         && [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] - ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ] > 1 } {
        # Enable the generation of an extra cycle between the the 1st and 2nd phase
        # when a rate of 2 with odd number of taps
        set gen_extra_cycle 1
      }

      # Rounder block
      set has_rounder 0
      if {   ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] > 1 && !($odd_and_even == 1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]==2 ) )
          || ( ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] ==2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true )
          || ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] ==2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" )
          || ( ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] ==2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
               && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0
               && !($shorter_px_time == 0 &&  $gen_extra_cycle== true) ) } {

        set has_rounder 1
      }
      #puts "***ROUNDER*** INTERPOLATION (SYMMETRIC) FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { $odd_and_even == 1 &&  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 } {
          if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
                [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
            set rounder_delay 2
          } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
            set rounder_delay 2
          } else {
            set rounder_delay 1
          }
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 } {
          set rounder_delay 2
        } elseif { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] &&
                   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 &&
                   ( $odd_and_even == 1 || [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] % 2 } ] > 0 ) } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" &&
                   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] > 34 &&
                   ! ( $odd_and_even == 1 &&  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 ) } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }

      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0  && $gen_extra_cycle == true} {
        set round_spare_cycle 1
      }

      #Output buffer
      set output_buffer_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      set output_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset output_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $output_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset output_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset output_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      set output_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $output_buffer  2 ]

      set has_output_buffer 1
      if { $odd_and_even == 1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] == 1 && $shorter_px_time!=1 } {
        set has_output_buffer 0
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      
      if { $odd_and_even == 1 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 } {
        set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat + 1 } ] ]
      }

      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]


      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]
      #lset reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] + 1 } ]

      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 ) } {
        set addr_cntrl_lat 2
      } else {
        set addr_cntrl_lat 1
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set we_sym_dly [ expr { $addr_cntrl_lat +
                              $first_tap_extra_dly +
                              [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                              -1 } ]

      if { $p_WE_SYM_OUT <= 0 &&  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 } {
        if { [ expr { $we_sym_dly -1 + $p_WE_SYM_OUT } ] < 0 } {
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + abs( $we_sym_dly -1 + $p_WE_SYM_OUT ) } ]
        }
      }

      set opbuff_lat 0
      if { $has_output_buffer == 1 } {
        set opbuff_lat [ expr { 1 + $output_rate } ]
        if { $odd_and_even == 1 &&  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 } {
          set opbuff_lat [ expr { $opbuff_lat + $output_buffer_lat } ]
        } else {
          set opbuff_lat [ expr { $opbuff_lat + $output_buffer_lat + 1 } ]
        }
      }

      set base_count_multi 1
      if { $has_output_buffer == 1 } {
        set base_count_multi [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
      }

      set accum_extra_bit 0
      if { [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] != "spartan3adsp" } {
        set accum_extra_bit 1
      }

      if { $odd_and_even == 1 &&  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] == 2 } {
        set extra_lat [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                           $accum_req \
                                           [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] + $accum_extra_bit } ] \
                                           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                           $has_rounder  ]
      } else {
        set extra_lat [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                           $accum_req \
                                           [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] + $accum_extra_bit } ] \
                                           $fir_compiler_v3_2_analysis::c_area \
                                           $has_rounder  ]
      }

      set latency [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * $base_count_multi )
                           + $addr_cntrl_lat
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_lat
                           + $rounder_delay
                           + $round_spare_cycle
                           + $extra_lat
                           + $opbuff_lat
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
                           
      # puts "latency components:"
#       puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#       puts "$base_count_multi"
#       puts "$addr_cntrl_lat"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_lat"
#       puts "$extra_lat"
#       puts "$opbuff_lat"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"


      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_lat > 0 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Output buffer
      set num_op_buff_ram 0
      if { $has_output_buffer == 1 } {

        if { [ lindex $output_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {

          set num_op_buff_ram [ num_BRAM  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::output_width ] $output_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
      }

      # puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       puts "$num_sym_buff_ram"
#       puts "$num_centre_coef_mem_ram "
#       puts "$num_op_buff_ram"

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram )
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_op_buff_ram } ]


      set buffer_type 0
      if { $has_output_buffer == 1 } {
        set buffer_type 2
      }
      set buffer_page_size [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        $buffer_type
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  $buffer_page_size

      #puts "9"

      #puts "inter sym pair run"

      return $param

    }
    #end symmetric pair
    
    
    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_pq_decimation { reqs } {

      set param $fir_compiler_v3_2_analysis::mac::param::default

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            0
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry      0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ expr { ( int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ] / pow(2, [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] ) ) )
                                                                 + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { floor( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { floor( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]
      set taps_per_phase                                                  [ expr { ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] ) } ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { ceil( $taps_per_phase / ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) ) } ]
      set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe
      lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ceil( $taps_per_phase / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) ) } ]

      if { [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] % [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ] > 0 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) + 1 } ]
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]
      }

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] <
           [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ] } {
        set shorter_px_time 1
      } else {
        set shorter_px_time 0
      }
      
      lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]

      lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr {   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
                                                                                   * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
                                                                                   * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] *
                                                             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] *
                                                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] * 
                                                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
      lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ]

      lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
      lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
      
      set output_rate [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]
      
      lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
      }

      #Mem calcs
      set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ] ) *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

      set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] *
                                       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]

      set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                       pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

      set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                       pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]


      set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

       set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

      set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

      #-----
      set first_tap_extra_dly 0

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

        if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
          set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
        }
      }
      
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]

        set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
        #no supported yet (independant columns)
        set num_col 1
      } else {
        set num_col 1
      }
      set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] ) + 2 } ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
      } else {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
      }
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade      0
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
      } else {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
      }
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    2
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

      #puts "4"

      set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
      
      set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
         || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf  } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** P/Q DECIMATION FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"
      
      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"
      
      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }
      
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0  } {
        set round_spare_cycle 1
      }

      #Output buffer
      set has_output_buffer 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 ||
            $shorter_px_time == 1 ||
            ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] == 1 &&
              [ expr { int( int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] ) % [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ] > 0 ) } {
        set has_output_buffer 1
      }

      set output_buffer_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      set output_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset output_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $output_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::opbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset output_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset output_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset output_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset output_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      set extra_opb_reg 0
      if { $has_output_buffer == 1 &&
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ lindex $output_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_dram } {
        set extra_opb_reg 1
      }

      if { $has_output_buffer == 1 } {
        set output_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $output_buffer  2 ]
      } else {
        set output_buffer_lat 0
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {
        lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] } ]
      } else {
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] == 1 } {
          lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ]
        } else {
          lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt     1
        }
      }
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    0
      if { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ] == 1 &&
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 ) ||
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {

        lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      $fir_compiler_v3_2_analysis::c_area
      } else {
        lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
      }

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 || [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_srl16 } {

        if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 } {
          if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ] == 0 ||
               [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
            set data_addr_lat [ expr { $data_addr_lat + 1 } ]
          } else {
            set data_addr_lat [ expr { $data_addr_lat + 2 } ]
          }
        } else {
          set data_addr_lat [ expr { $data_addr_lat + 1 } ]
        }
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] == $fir_compiler_v3_2_analysis::c_speed &&
           [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 &&
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 } {

        set data_addr_lat [ expr { $data_addr_lat + 1 } ]
      }

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]


      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]
      lset reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] + 1 } ]

      set addr_cntrl_lat 2

      set filt_sel_lat 2

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 + $filt_sel_lat } ] < $addr_cntrl_lat } {
          #Do nothing
        } else {
          set addr_dly  [ expr { [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] - 1 + $filt_sel_lat - $addr_cntrl_lat } ]
          set addr_cntrl_lat [ expr { $addr_cntrl_lat + $addr_dly } ]
        }
      } else {
        #do nothing
      }

      set opb_latency 0
      if { $has_output_buffer == 1 } {
        set opb_latency [ expr { $output_rate +
                                 $extra_opb_reg } ]
      }

      set buffer_type 2
      if { $has_output_buffer == 1 } {
        set latency [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] )
                             + $addr_cntrl_lat
                             + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                             + $first_tap_extra_dly
                             + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                             + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                             + $accum_req
                             + $rounder_delay
                             + $round_spare_cycle
                             + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                   $accum_req \
                                                   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                   $has_rounder  ]
                             + $opb_latency
                             + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
                             
        # puts "latency components:"
#         puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#         puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]"
#         puts "$addr_cntrl_lat"
#         puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#         puts "$first_tap_extra_dly"
#         puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#         puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#         puts "$accum_req"
#         puts "$opb_latency"
#         puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"

        set sample_latency [ expr { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] - 1 )) +1 } ]
        set buffer_page_size [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      } else {
        set latency [ expr { ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] % [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) )
                             + $addr_cntrl_lat
                             + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                             + $first_tap_extra_dly
                             + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                             + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                             + $accum_req
                             + $rounder_delay
                             + $round_spare_cycle
                             + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                   $accum_req \
                                                   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                   $has_rounder  ]
                             + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
        
        # puts "latency components:"
#         puts "[ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]"
#         puts "[ expr { ( ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] % [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) ) } ]"
#         puts "$addr_cntrl_lat"
#         puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#         puts "$first_tap_extra_dly"
#         puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#         puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#         puts "$accum_req"
#         puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"


        if { [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] % [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ] > 0 } {
          set sample_latency [ expr { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) + 1 } ]
        } else {
          set sample_latency [ expr { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]
        }
        set buffer_page_size 0
      }

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      #Output buffer
      set num_op_buff_ram 0
      if { $has_output_buffer == 1 } {

        if { [ lindex $output_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
          
          set num_op_buff_ram [ num_BRAM  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::output_width ] $output_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
      }

     #  puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       #puts "$num_sym_buff_ram"
#       #puts "$num_centre_coef_mem_ram "
#       puts "$num_op_buff_ram"

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_op_buff_ram } ]



      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   $sample_latency
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        $buffer_type
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  $buffer_page_size

      #puts "pq deci run"

      return $param

    }
    #end pq decimation

    #-----------------------------------------------------------------
    #
    #-----------------------------------------------------------------
    proc define_pq_interpolation { reqs } {

       set param $fir_compiler_v3_2_analysis::mac::param::default
#      set param $param::default

      # puts "test read: "
#       puts [ lindex $reqs $reqs::family ]
#       puts [ lindex $param $param::family ]
#       puts "hi"
#       puts [ log2ceil 5 ]

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set srl16_mem_depth_thres 64
      } else {
        set srl16_mem_depth_thres 32
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex5" } {
        set dram_mem_depth_thres 32
      } else {
        set dram_mem_depth_thres 16
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset param $fir_compiler_v3_2_analysis::mac::param::filter_type         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_reload         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset param $fir_compiler_v3_2_analysis::mac::param::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::coef_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_width ]
      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_samp        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_channels        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_filts           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset param $fir_compiler_v3_2_analysis::mac::param::zero_packing_factor [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::zero_packing_factor ]
      lset param $fir_compiler_v3_2_analysis::mac::param::symmetry            0
      lset param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry        0
      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_taps ]

      lset param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan    [ expr { int( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::clk_per_samp ] / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] ) } ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::clk_per_chan ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) } ]
      set taps_per_phase                            [ expr { int( ceil( double( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ] ) / [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] ) ) } ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        [ expr { int( ceil( double( $taps_per_phase ) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) ) } ]
      set num_macs_safe [ fir_compiler_v3_2_analysis::get_max 1 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ]
      lset param $fir_compiler_v3_2_analysis::mac::param::num_macs        $num_macs_safe
      
      set base_count_op_rate                        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_count      [ expr { int( ceil( double($taps_per_phase) / [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] ) ) } ]

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] < $base_count_op_rate } {
        set shorter_px_time 1
      } else {
        set shorter_px_time 0
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::centre_mac  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]

      lset param $fir_compiler_v3_2_analysis::mac::param::base_data_space [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset param $fir_compiler_v3_2_analysis::mac::param::base_coef_space [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::inter_rate [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
      lset param $fir_compiler_v3_2_analysis::mac::param::deci_rate  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ]

      lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 0
      lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::full_parallel 1
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] == 1 } {
          lset param $fir_compiler_v3_2_analysis::mac::param::no_data_mem 1
        }
      }

      lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::single_mac 1
      }
      
      #Mem calcs
      set data_depth_unpacked [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] ] ) *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]
      set data_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_data_space ] *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] } ]

      set coef_depth_unpacked [ expr { pow(2, [ log2ceil [ expr { pow(2, [ log2ceil [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] ] ) *
                                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] } ] ] ) *
                                       pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

      set coef_depth_packed   [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ] *
                                       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_filts ] *
                                       pow(2, [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_reload ] ) } ]

      set param [ memory_calcs $reqs $param $data_depth_unpacked $data_depth_packed $coef_depth_unpacked $coef_depth_packed ]

      set mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::param_default
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::implementation     1
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_type      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_comb          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::data_coef_comb     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::no_data_mem        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::coef_reload_depth  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
      lset mem_param $fir_compiler_v3_2_analysis::tap_memory_add_casc::symmetric          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]

      set memory_path_lat [ fir_compiler_v3_2_analysis::tap_memory_add_casc::lat $mem_param ]

      #-----
      set first_tap_extra_dly 0

      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 1 } {

        if { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] > 1 } {
          set first_tap_extra_dly   [ expr { [ lindex $memory_path_lat $fir_compiler_v3_2_analysis::tap_memory_add_casc::lat_coef ] - 1 } ]
        }
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 1 } {
          ### Set a default array of column lengths - needs to be long enough to cope with 1024 taps
          ### and a minimum device column length of 22 (xc3sd1800a).  This means 1024 / 22 = 46.*, so
          ## use 48 columns maximum
          set col_len [ list [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_1st_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] \
                             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_wrap_len ] ]
                             
        set num_col [ fir_compiler_v3_2_analysis::filt_arm_add_casc::calc_num_split_col [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] $col_len ]

      } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_mode ] == 2 } {
        #no supported yet (independant columns)
        set num_col 1
      } else {
        set num_col 1
      }

      set filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::param_default
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::family              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::implementation      1
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_taps            [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_we_cycles     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_tap_delay     [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_channels ] + 1 } ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::symmetric           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::neg_symmetry ] == 1 } {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_sub
      } else {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::pre_add_func      $fir_compiler_v3_2_analysis::c_preadd_add
      }
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_type       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_comb           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_coef_comb      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_cascade        0
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::no_data_mem         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_mem_depth      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::p_src               0
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_sign ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::coef_sign           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_sign ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::has_ce              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload              [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::reload_depth        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_independant_col $num_col
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::num_split_col       $num_col
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::inter_split_col_dly [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::col_pipe_len ]
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] == 1 } {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    1
      } else {
        lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::dynamic_opcode    0
      }
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::sym_para_struct     [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::para_casc_we_src    1
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::resource_opt        [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::data_width          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::data_width ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::datapath_mem_type   [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::datapath_mem_type ]
      lset filt_arm $fir_compiler_v3_2_analysis::filt_arm_add_casc::odd_symmetry        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]

      #puts "4"

      set filt_arm_lat [ fir_compiler_v3_2_analysis::filt_arm_add_casc::lat $filt_arm ]
      
      set first_tap_extra_dly [ expr { $first_tap_extra_dly + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_data_addr_extra_delay ] } ]

      #Accum
      set accum_req 1
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] == 1  ||
           ( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs] > 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count] == 1 ) } {
        set accum_req 0
      }

      # Rounder block
      set has_rounder 0
      if {  (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true                                          )
         || (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4"                                               )
         || (  (  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_zero
               || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == $fir_compiler_v3_2_analysis::c_symmetric_inf )
            && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ] == 0 && $shorter_px_time == 0                         )
         } {
        set has_rounder 1
      }
      #puts "***ROUNDER*** P/Q INTERPOLATION FILTER TYPE"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ]
      #puts "***ROUNDER*** ROUND_MODE   = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      #puts "***ROUNDER*** FAMILY       = $temp"
      set temp [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::allow_approx ]
      #puts "***ROUNDER*** ALLOW_APPROX = $temp"
      set temp $shorter_px_time
      #puts "***ROUNDER*** SHORTER_PX_TIME = $temp"

      #puts "***ROUNDER*** HAS_ROUNDER  = $has_rounder"

      if { $has_rounder == 1 } {
        if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 ) &&
              [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == true } {
          set rounder_delay 2
        } elseif { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "virtex4" } {
          set rounder_delay 2
        } else {
          set rounder_delay 1
        }
      } else {
        set rounder_delay 0
      }
      
      set round_spare_cycle 0
      if { ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 2 || [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::round_mode ] == 3 )
              && $shorter_px_time == 1 && $has_rounder == 0  } {
        set round_spare_cycle 1
      }

      #INput buffer
      set has_input_buffer 0
      set input_buffer_cntrl_dly 0
      set input_buffer_lat 0
      set input_buffer_depth [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] * 2 } ]
      
      if { $shorter_px_time == 1 } {
        set input_buffer_cntrl_dly 1
      }

      set input_buffer $fir_compiler_v3_2_analysis::ram::param_default
      lset input_buffer $fir_compiler_v3_2_analysis::ram::family          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset input_buffer $fir_compiler_v3_2_analysis::ram::implementation  1
      if { ( $input_buffer_depth > $dram_mem_depth_thres && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::ipbuff_mem_type ] != $fir_compiler_v3_2_analysis::c_mem_forced_dist ) ||
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::ipbuff_mem_type ] == $fir_compiler_v3_2_analysis::c_mem_forced_bram } {
        lset input_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_bram
      } else {
        lset input_buffer $fir_compiler_v3_2_analysis::ram::mem_type $fir_compiler_v3_2_analysis::c_dram
      }
      lset input_buffer $fir_compiler_v3_2_analysis::ram::write_mode      0
      lset input_buffer $fir_compiler_v3_2_analysis::ram::has_ce          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset input_buffer $fir_compiler_v3_2_analysis::ram::use_mif         0

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 } {
        set has_input_buffer 1
        set input_buffer_lat [ fir_compiler_v3_2_analysis::ram::lat $input_buffer  2 ]

        if { $shorter_px_time == 1 } {
         set input_buffer_lat [ expr { $input_buffer_lat + 1 } ]
        }
      }
      
      #--
      set no_addr_latch 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type] == $fir_compiler_v3_2_analysis::c_srl16 && 
           [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ] > 1 } {
        set no_addr_latch 1
      }

      #Data address
      set data_addr_param $fir_compiler_v3_2_analysis::data_address::param_default
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::family            [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::implementation    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::mem_type          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::base_cnt          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::block_cnt         [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::symmetric         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::combined          [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::addr_packed       [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_packed ]
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::srl16_sequence    1
      lset data_addr_param $fir_compiler_v3_2_analysis::data_address::resource_opt      [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      # puts "Data address params:"
#       puts $data_addr_param

      set data_addr_lat [ fir_compiler_v3_2_analysis::data_address::lat $data_addr_param ]
      set data_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $data_addr_lat - 1 } ] ]
      #set data_addr_lat 0

      set coef_addr_param $fir_compiler_v3_2_analysis::coef_address::param_default
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::implementation     1
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::base_cnt           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::block_cnt          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::addr_packed        [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::num_filters        [ expr { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] *
                                                                            pow(2,[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] ) } ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::offset             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset coef_addr_param $fir_compiler_v3_2_analysis::coef_address::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set coef_addr_lat [ fir_compiler_v3_2_analysis::coef_address::lat $coef_addr_param ]
      set coef_addr_lat [ fir_compiler_v3_2_analysis::get_max 0 [ expr { $coef_addr_lat - 1 } ] ]
      #set coef_addr_lat 0

      set reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::param_default
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::family             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::implementation     1
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::reload_base_cnt    [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] * [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_addr_packed   [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_filts          [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::coef_mem_depth     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::num_macs           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::has_ce             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_ce ]
      lset reload_cntrl_param $fir_compiler_v3_2_analysis::coef_reload_cntrl::resource_opt       [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ]

      set reload_cntrl_lat [ fir_compiler_v3_2_analysis::coef_reload_cntrl::lat $reload_cntrl_param ]

      set pipeline_addr_en 0
      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           $no_addr_latch == 0 &&
           $shorter_px_time != 1 } {
        set pipeline_addr_en 1
      }
      
      if { $shorter_px_time == 1 ||
           ( [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::has_nd ] == 1 &&
             [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
             $no_addr_latch == 0 ) } {
        set addr_cntrl_lat [ expr { 2 + $pipeline_addr_en } ]
      } else {
        set addr_cntrl_lat 1
      }

      if { $has_input_buffer == 1 } {
        if { $input_buffer_lat > $addr_cntrl_lat } {
          set addr_dly [ expr { $input_buffer_lat - $addr_cntrl_lat } ]
          set filt_dly 0
        } else {
          set addr_dly 0
          set filt_dly [ expr { $addr_cntrl_lat - $input_buffer_lat } ]
        }
      } else {
        set addr_dly 0
        #set filt_dly $input_buffer_lat
        set filt_dly $addr_cntrl_lat
      }
      
      if { $has_input_buffer == 0 && [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_filts ] > 1 } {
        set filt_dly [ expr { $filt_dly - 1 } ]
      }

      if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::coef_reload ] == 1 } {
        if { [ expr { $filt_dly - [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] } ] <= 0 } {
          set addr_dly [ expr { $addr_dly + abs( $filt_dly - [ lindex $reload_cntrl_lat $fir_compiler_v3_2_analysis::coef_reload_cntrl::lat_filt_sel ] ) } ]
        } else {
          #do nothing
        }
      }

      set latency [ expr { $has_input_buffer
                           + $base_count_op_rate
                           + $addr_cntrl_lat
                           + $addr_dly
                           + [ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]
                           + $first_tap_extra_dly
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]
                           + [ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]
                           + $accum_req
                           + $rounder_delay
                           + $round_spare_cycle
                           + [ mod_lat_non_dsp48 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] \
                                                 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] \
                                                 $accum_req \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::accum_width ] \
                                                 [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::resource_opt ] \
                                                 $has_rounder  ]
                           + [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ] } ]
                           
      # puts "latency components:"
#       puts "$has_input_buffer"
#       puts "$base_count_op_rate"
#       puts "$addr_cntrl_lat"
#       puts "$addr_dly"
#       puts "[ fir_compiler_v3_2_analysis::get_max $data_addr_lat $coef_addr_lat ]"
#       puts "$first_tap_extra_dly"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_cascaded ]"
#       puts "[ lindex $filt_arm_lat $fir_compiler_v3_2_analysis::filt_arm_add_casc::lat_tap ]"
#       puts "$accum_req"
#       puts "[ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::reg_output ]"

      #puts "latency: $latency"

      #puts "6"

      set num_dsp48 [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
      if { $accum_req == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      if { $has_rounder == 1 &&
           ( [ fir_compiler_v3_2_analysis::treat_as_s3ax [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ] == false ||
             [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] == "spartan3adsp" ) } {
        set num_dsp48 [ expr { $num_dsp48 + 1 } ]
      }
      
      #puts "dsp: $num_dsp48"

      #Calc numBRAMs used

      #Data mem
      set num_data_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set num_data_mem_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_depth ] [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Data sym mem
      set num_data_sym_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ] == 1 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::no_data_mem ] == 0 &&
           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set sym_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_depth ] +
                                                      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::datasym_mem_offset ] } ]
        set num_data_sym_mem_ram  [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $sym_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }

      #Coef mem
      set num_coef_mem_ram 0
      if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
        set coef_depth [ expr { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_depth ] +
                                                  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
        set num_coef_mem_ram  [ num_BRAM  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_width ] $coef_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
      }
      
      set num_ip_buff_ram 0
      if { $has_input_buffer == 1 } {
        if { [ lindex $input_buffer $fir_compiler_v3_2_analysis::ram::mem_type ] == $fir_compiler_v3_2_analysis::c_bram } {
          set num_ip_buff_ram [ num_BRAM [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_width ] $input_buffer_depth [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] ]
        }
      }

      # puts "resource"
#       puts "$num_data_mem_ram"
#       puts "$num_data_sym_mem_ram"
#       puts "$num_coef_mem_ram"
#       puts "$num_sym_buff_ram"
#       puts "$num_centre_coef_mem_ram "
#       puts "$num_op_buff_ram"

      set num_BRAMs [ expr {  (( $num_data_mem_ram +
                              $num_data_sym_mem_ram +
                              $num_coef_mem_ram ) 
                              * [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ] )
                              + $num_ip_buff_ram } ]

      lset param $fir_compiler_v3_2_analysis::mac::param::latency          $latency
      lset param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s       $num_dsp48
      lset param $fir_compiler_v3_2_analysis::mac::param::num_bram         $num_BRAMs
      lset param $fir_compiler_v3_2_analysis::mac::param::sample_latency   0
      if { $has_input_buffer == 1 } {
        lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        1
      } else {
        lset param $fir_compiler_v3_2_analysis::mac::param::buff_type        0
      }
      lset param $fir_compiler_v3_2_analysis::mac::param::buff_page_depth  [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::num_channels ]

      #puts "Run pq interpolation "

      return $param
    }
    #end pq interpolation
  }
  #namespace mac
  
  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc gen_filt_type { gui_filt_type gui_coef_struct gui_rate_change } {
    
   #puts "gen_filt_type"

   set filter_type_value 0
   if { $gui_rate_change == "Fixed_Fractional" } {

     set filter_type_value $fir_compiler_v3_2_analysis::c_polyphase_pq

   } else {
     if { $gui_filt_type == "Single_Rate" } {
        if { $gui_coef_struct == "Hilbert" } {
           set filter_type_value $fir_compiler_v3_2_analysis::c_hilbert_transform
        } elseif { $gui_coef_struct == "Half_Band" } {
           set filter_type_value $fir_compiler_v3_2_analysis::c_halfband_transform
        } else {
           set filter_type_value $fir_compiler_v3_2_analysis::c_single_rate
        }
     } elseif {$gui_filt_type == "Decimation"} {
        if {$gui_coef_struct == "Half_Band"} {
           set filter_type_value $fir_compiler_v3_2_analysis::c_decimating_half_band
        } else {
           set filter_type_value $fir_compiler_v3_2_analysis::c_polyphase_decimating
        }
     } elseif {$gui_filt_type == "Interpolation"} {
        if {$gui_coef_struct == "Half_Band"} {
           set filter_type_value $fir_compiler_v3_2_analysis::c_interpolating_half_band
        } else {
           set filter_type_value $fir_compiler_v3_2_analysis::c_polyphase_interpolating
        }
     } elseif {$gui_filt_type == "Interpolated"} {
        set filter_type_value $fir_compiler_v3_2_analysis::c_interpolated_transform
     }
   }

   return $filter_type_value

  }

  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc gen_filt_param { reqs } {
    
    set param $fir_compiler_v3_2_analysis::mac::param::default
    set temp_reqs $fir_compiler_v3_2_analysis::mac::reqs::default

    if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::family ] != $fir_compiler_v3_2_analysis::ns_family } {
      set check_val [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type ]
  
      #couldnt get switch to work so use if
      if { $check_val == $fir_compiler_v3_2_analysis::c_single_rate } {
        set param [ fir_compiler_v3_2_analysis::mac::define_single_rate $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_polyphase_decimating } {
        set param [ fir_compiler_v3_2_analysis::mac::define_decimation $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_polyphase_interpolating } {
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::symmetry ] == 1 } {
          set temp_reqs $reqs
          lset temp_reqs $fir_compiler_v3_2_analysis::mac::reqs::filter_type $fir_compiler_v3_2_analysis::c_interpolating_symmetry
          set param [ fir_compiler_v3_2_analysis::mac::define_sympair_interpolation $temp_reqs ]
        } else {
          set param [ fir_compiler_v3_2_analysis::mac::define_interpolation $reqs ]
        }
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_hilbert_transform } {
        set param [ fir_compiler_v3_2_analysis::mac::define_hilbert $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_interpolated_transform } {
        set param [ fir_compiler_v3_2_analysis::mac::define_interpolated $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_halfband_transform } {
        set param [ fir_compiler_v3_2_analysis::mac::define_halfband $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_decimating_half_band } {
        set param [ fir_compiler_v3_2_analysis::mac::define_halfband_decimation $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_interpolating_half_band } {
        set param [ fir_compiler_v3_2_analysis::mac::define_halfband_interpolation $reqs ]
      } elseif { $check_val == $fir_compiler_v3_2_analysis::c_polyphase_pq } {
        if { [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::deci_rate ] > [ lindex $reqs $fir_compiler_v3_2_analysis::mac::reqs::inter_rate ] } {
          set param [ fir_compiler_v3_2_analysis::mac::define_pq_decimation $reqs ]
        } else {
          set param [ fir_compiler_v3_2_analysis::mac::define_pq_interpolation $reqs ]
        }
      } else {
         #puts "invalid filter_type"
      }

      #make sure everthing is an integer before returning
      set i 0
      foreach element $param {
        #puts "$element"
        if { $i > 0 } {
          lset param $i [ expr { int($element) } ]
        }
        incr i
      }
    } else {
      #set invalid latency
      lset param $fir_compiler_v3_2_analysis::mac::param::latency -1
    }

    return $param

  }
  #end gen_filt_param

  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc get_num_DSP48s { param } {
    return [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_dsp48s ]
  }
  
  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc get_num_BRAM { param } {
    return [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_bram ]
  }

  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc get_latency { param } {
    return [ lindex $param $fir_compiler_v3_2_analysis::mac::param::latency ]
  }
  
  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc get_num_taps_calced { param } {
    return [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ]
  }
  
  #-----------------------------------------------------------------
  #
  #-----------------------------------------------------------------
  proc get_reload_indices { param mode col_mode col1_ln coln_ln } {

    #Generate Reordered Index file
    #puts "get_reload_indices"

    set symmetry         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::symmetry ]
    set oddsymmetry      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::odd_symmetry ]
    set num_macs         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_macs ]
    set taps_calced      [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps_calced ]
    set coefmemspace     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_coef_space ]
    set midtap           [ lindex $param $fir_compiler_v3_2_analysis::mac::param::centre_mac ]
    set num_taps         [ lindex $param $fir_compiler_v3_2_analysis::mac::param::num_taps ]
    set inter_rate_ip    [ lindex $param $fir_compiler_v3_2_analysis::mac::param::inter_rate ]
    set deci_rate_ip     [ lindex $param $fir_compiler_v3_2_analysis::mac::param::deci_rate ]

    #rates
    set deci_rate  [ expr { $deci_rate_ip / [ fir_compiler_v3_2_utils::get_gcd $inter_rate_ip $deci_rate_ip ] } ]
    set inter_rate [ expr { $inter_rate_ip / [ fir_compiler_v3_2_utils::get_gcd $inter_rate_ip $deci_rate_ip ] } ]
  
    #Filter type
    set filter_type_value  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::filter_type ]

    #Halfband centre tap
    set halfband_smac 0
    if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::single_mac ] == 1 &&
         ($filter_type_value == $fir_compiler_v3_2_analysis::c_halfband_transform ||
         $filter_type_value == $fir_compiler_v3_2_analysis::c_decimating_half_band ||
         $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolating_half_band ) } {
      set halfband_smac 1
    }

    #Generate source index
    set src_coefficients_index ""

    if {$mode=="sysgen"} {
      set top_index $taps_calced
      set text_to_prepend ""
    } else {
      set top_index $num_taps
      set text_to_prepend "Index "
    }

    for {set index 0} { $index < $top_index } { incr index } {
      set write_i [ expr { $index } ]
      lappend src_coefficients_index "$text_to_prepend$write_i"
    }

    # puts "$src_coefficients_index"
#     puts "-------------"

    #set src_coefficients_index ""
    #for {set index 0} { $index < $num_taps} {incr index} {
    #  set write_i [ expr { $index } ]
    #  lappend src_coefficients_index "Index $write_i"
    #}

    # Determine if coefficients need to be calculated
    set tap_difference 0

    if { $symmetry == 1 } {

      set local_req_num_taps [ expr { $num_taps / 2 } ]

      if { [ expr { $num_taps % 2 } ] > 0 } {
        set local_req_num_taps [ expr { $local_req_num_taps + 1 } ]
      }

      set tap_difference [ expr { $taps_calced - $local_req_num_taps } ]

      if { $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolating_symmetry  &&
           $oddsymmetry == 1 &&
           [ expr { $inter_rate % 2 } ] > 0 } {
        set tap_difference [ expr { $tap_difference - int( $inter_rate /2 ) } ]
      }

    } else {
      set local_req_num_taps $num_taps
    }

    if {$mode=="sysgen"} {
      set coefficients $src_coefficients_index
    } else {
      # Pad and transfer to coefficients list
      set coefficients ""
      for {set tap 0} { $tap < $taps_calced} {incr tap} {
        if { $symmetry == 1 } {
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

    # puts "coefficients"
#     puts "$coefficients"
#     puts "-------------"


    #create mod_coef by setting to padded
    #set mod_coefficients $coefficients
    #set mac_coefficients $coefficients
    foreach coeff $coefficients {
      lappend mod_coefficients 0
      lappend mac_coefficients 0
    }
    
    # puts "mod_coefficients"
#     puts "$mod_coefficients"
#     puts "-------------"


    #Generate symmetric pairs for interpolating symmetry
    if { $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolating_symmetry } {

      set tap 0
      while { $tap <= [ expr { $taps_calced - 1 } ] } {

        set phase_last_mod 0
        set phase_offset 0
        if { [ expr { $inter_rate % 2 } ] != 0 } {
          #get centre phase
          lset mod_coefficients $tap [ lindex $coefficients [ expr { $tap+( $inter_rate / 2 ) } ] ]
          set phase_offset 1
        }

        set phase 0
        set inter_sym_loop_len [ expr { int( $inter_rate /2 ) } ]
        
        if { [ expr { $inter_rate % 2 } ] == 0 && $oddsymmetry == 1 } {
          lset mod_coefficients $tap [ lindex $coefficients [ expr { $tap + ( int( $inter_rate / 2 ) - 1 ) } ] ]
          lset mod_coefficients [ expr { $tap + $inter_rate - 1 } ] [ lindex $coefficients [ expr { $tap + ( $inter_rate - 1 ) } ] ]
          set phase_offset 1
          set phase_last_mod 1
          set inter_sym_loop_len [ expr { int( ( $inter_rate - 2 ) / 2 ) } ]
        }
        
        for { set phase_loop 0 } { $phase_loop < $inter_sym_loop_len } { incr phase_loop } {

          set write_i [ expr { $tap + ( 2 * $phase ) + 1 + $phase_offset } ]
          set read_val_1 [ lindex $coefficients [ expr { $tap+$phase } ] ]
          set read_val_2 [ lindex $coefficients [ expr { ($inter_rate+$tap-1-$phase_last_mod)-$phase } ] ]

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
    
    #Initial multicolumn settings
    # puts "num_macs: $num_macs"
#     puts "col1_ln: $col1_ln"
#     if { $col_mode != "Disabled" } {
#       set num_cols        [ expr { 1 + int( ($num_macs - $col1_ln) / $coln_ln ) } ]
#       if { [ expr { ($num_macs - $col1_ln) % $coln_ln } ] > 0 } {
#         set num_cols [ expr { $num_cols +1 } ]
#       }
#     } else {
      set num_cols 1
#     }
    #Multi-col reload changed so no re-ordering required.

    if { [ expr { ($num_macs - $col1_ln) % $coln_ln } ] > 0 } {
      set last_col        [ expr { ($num_macs - $col1_ln) % $coln_ln } ]
    } else {
      set last_col        $coln_ln
    }
    set curr_col        1
    #puts "num_cols: $num_cols"
    if { $num_cols > 1 } {
      set remaining_macs  $col1_ln
    } else {
      set remaining_macs $num_macs
    }
    #puts "remaining_macs: $remaining_macs"
    set new_mac_pos     [ expr { ($num_macs-1) } ]

    #puts "new_mac_pos: $new_mac_pos"

    # Re-order Coefficients to fit filter structure
    if { $filter_type_value == $fir_compiler_v3_2_analysis::c_decimating_half_band ||
         $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolating_half_band  } {
      #Deci or inter halfband
      set local_deci_rate 2
    } elseif { $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolated_transform } {
      #Interpolated
      set local_deci_rate 1
    } else {
      set local_deci_rate $deci_rate
    }

    for {set mac 0} {$mac < $num_macs} {incr mac} {
      
      #puts "mac: $mac"

      set mac_mem_pos 0

      # if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_coef_combined ] == 1 } {
#         set mac_mem_pos [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ]
#       }

      set pq_deci_first_phase 0
      for { set i 0 } { $i < $deci_rate } { incr i } {
        lappend pq_deci_first_phase 0
      }

      set phase 0
      set mod_phase 0
      set phase_loop_end [ expr { [ get_max $local_deci_rate $inter_rate ] - 1 } ]
      set phase_loop_dir 1
      set pq_deci_start_phase 0

      if { $filter_type_value == $fir_compiler_v3_2_analysis::c_polyphase_decimating ||
           ( $filter_type_value == $fir_compiler_v3_2_analysis::c_polyphase_pq && $inter_rate<$deci_rate ) } {
      
        set phase  $phase_loop_end
        #same start value
        set mod_phase $phase_loop_end
        set pq_deci_start_phase [ expr { $inter_rate-1} ]
        set phase_loop_end 0
        set phase_loop_dir -1
      }

      while { $phase != [ expr { $phase_loop_end+$phase_loop_dir } ] } {

        #puts "phase: $phase"
        #puts "mac_mem_pos: $mac_mem_pos"

        if { $filter_type_value == $fir_compiler_v3_2_analysis::c_polyphase_pq && $inter_rate<$deci_rate } {

          #PQ Decimation reordering
          #puts "pq deci"

          set pq_deci_phase 0
          set pq_deci_count_ips 0

          for {set pq_reorder 0} { $pq_reorder < $inter_rate } {incr pq_reorder} {

            #puts "mod_phase: $mod_phase"
            #puts "pq_deci_phase: $pq_deci_phase"


            if { $pq_deci_phase == 0 } {
              set tap_pos [ expr { ($mac * $coefmemspace) +
                                   (($mod_phase - $pq_reorder) % $deci_rate) } ]
            } else {
              set tap_pos [ expr { ($mac * $coefmemspace) +
                                   (($mod_phase - $pq_reorder) % $deci_rate) +
                                   (($inter_rate - int((($pq_deci_phase)*$inter_rate)/$deci_rate))*$deci_rate) } ]
            }
            
            lset pq_deci_first_phase $mod_phase 1

            while { $tap_pos < [ expr { ($mac+1)*$coefmemspace  } ] } {
              
              if {  [ lindex $param $fir_compiler_v3_2_analysis::mac::param::data_mem_type ] != $fir_compiler_v3_2_analysis::c_srl16 &&
                    [ lindex $pq_deci_first_phase [ expr { (($mod_phase - $pq_reorder) % $deci_rate ) } ] ] == 0 } {
                set base_count [ expr { int( [ lindex $param $fir_compiler_v3_2_analysis::mac::param::base_count ] ) } ]
                lset mac_coefficients $mac_mem_pos [ lindex $coefficients [ expr { int( ($mac*$base_count*$inter_rate*$deci_rate)
                                                                                   + ( ( $tap_pos-($base_count*$inter_rate*$deci_rate)
                                                                                          + (($base_count-1)*$inter_rate*$deci_rate))
                                                                                          % ($base_count*$inter_rate*$deci_rate)) ) } ] ]
              } else {
                lset mac_coefficients $mac_mem_pos [ lindex $coefficients $tap_pos ]
              }

              incr mac_mem_pos
              set tap_pos [ expr { $tap_pos + ( $inter_rate * $deci_rate) } ]
            }

            while { $pq_deci_count_ips != [ expr { $pq_reorder + 1 } ] } {
              set pq_deci_count_ips [ expr { ( $pq_deci_count_ips + $inter_rate ) % $deci_rate } ]
              set pq_deci_phase [ expr { ($pq_deci_phase+1) % $deci_rate } ]
            }
          }
        } elseif {  $filter_type_value != $fir_compiler_v3_2_analysis::c_hilbert_transform &&
                    $filter_type_value != $fir_compiler_v3_2_analysis::c_interpolated_transform &&
                    $filter_type_value != $fir_compiler_v3_2_analysis::c_halfband_transform &&
                    $filter_type_value != $fir_compiler_v3_2_analysis::c_decimating_half_band &&
                    $filter_type_value != $fir_compiler_v3_2_analysis::c_interpolating_half_band } {
        
          #Standard reordering

          if { $filter_type_value == $fir_compiler_v3_2_analysis::c_polyphase_pq } {
            set tap_pos [ expr { ($mac*$coefmemspace)+$mod_phase } ]
          } else {
            set tap_pos [ expr { ($mac*$coefmemspace)+$phase } ]
          }
        
          while { $tap_pos < [ expr { ($mac+1)*$coefmemspace  } ] } {
            lset mac_coefficients $mac_mem_pos [ lindex $coefficients $tap_pos ]
            incr mac_mem_pos
            set tap_pos [ expr { $tap_pos + [ get_max $local_deci_rate $inter_rate ] } ]
          }

        } else {

          #Hilbert,Halfband and interpolated reoderding
          set tap_pos [ expr { ($mac*$coefmemspace*$local_deci_rate)+$phase } ]

          while { $tap_pos < [ expr { ($mac+1)*$coefmemspace * $local_deci_rate  } ] && $phase == 0 } {
            lset mac_coefficients $mac_mem_pos [ lindex $coefficients $tap_pos ]
            incr mac_mem_pos
            set tap_pos [ expr { $tap_pos + $local_deci_rate } ]
          }
        }

        set phase [ expr { $phase + $phase_loop_dir } ]

        if { $inter_rate > $deci_rate } {
          #interpolation
          set mod_phase [ expr { ($mod_phase+$deci_rate) % $inter_rate } ]
        } else {
          #decimation
          set mod_phase [ expr { ($mod_phase-$inter_rate) % $deci_rate } ]
        }

       #  if { [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_packed ] == 0 } {
#           set mac_mem_pos [ expr { int(pow(2,int( [ log2ceil $coefmemspace] ) )) + [ lindex $param $fir_compiler_v3_2_analysis::mac::param::coef_mem_offset ] } ]
#         }
      }
      #end reorder loop

      # puts "mac content"
#       puts "$mac_coefficients"
#       puts "---------"

      #Multicolumn reordering
      #puts "new_mac_pos: $new_mac_pos"

      for { set pos 0} {$pos < $coefmemspace} {incr pos} {

        set write_i [ expr { ($new_mac_pos * $coefmemspace) + $pos } ]
        lset mod_coefficients $write_i [ lindex $mac_coefficients $pos ]
      }


      if { $remaining_macs == 1 } {
       # move into next column

        set curr_col [ expr { $curr_col + 1 } ]
        # puts "curr_col: $curr_col"
#         puts "num_cols: $num_cols"
        if { $curr_col == $num_cols } {
          set remaining_macs $last_col
        } else {
          set remaining_macs $coln_ln
        }
        set new_mac_pos [ expr { $new_mac_pos- $remaining_macs } ]


      } else {
        #stay in current col
        set remaining_macs [ expr { $remaining_macs -1 } ]
        if { [ expr { $curr_col % 2 } ] == 1 } {
          #up col
          set new_mac_pos [ expr { $new_mac_pos -1 } ]
        } else {
          #down col
          set new_mac_pos [ expr { $new_mac_pos +1 } ]
        }
      }

      # puts "load seq"
#       puts "$mod_coefficients"
#       puts "---------"


    }
    #end mac loop

    #Store centre tap when halfband
    if {  $filter_type_value == $fir_compiler_v3_2_analysis::c_halfband_transform ||
          $filter_type_value == $fir_compiler_v3_2_analysis::c_decimating_half_band ||
          $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolating_half_band } {
    
      if { $num_macs == $midtap } {
        set halfband_centre_tap  [ lindex $coefficients [ expr { $tap_difference + $local_req_num_taps -1 } ] ]
      } else {
        #symmetry disabled
        set halfband_centre_tap  [ lindex $coefficients [ expr { $num_taps / 2 } ] ]
      }

      #puts "halfband_centre_tap: $halfband_centre_tap"

      set write_i [ expr { $num_macs * $coefmemspace } ]
      if { $halfband_smac == 1 } {
        #coef mem space will reflect the the centre tap is in the main mac
        #memory so need to subtract one as not extra value on end
        set write_i [ expr { $coefmemspace - 1  } ]
      }

      lset mod_coefficients $write_i $halfband_centre_tap
      
      #puts "mod_coefficients: $mod_coefficients"

      # if { $halfband_smac == 1 } {
#         #if halfband single mac memory space needs shifted down by 1 as mid tap in main memory space
#         puts "halfband single mac"
#         for { set tap 0} { $tap < $coefmemspace} {incr tap} {
#           lset mod_coefficients $tap [ lindex $mod_coefficients [ expr { $tap+1} ] ]
#         }
#       }
    }

    if { $filter_type_value == $fir_compiler_v3_2_analysis::c_halfband_transform ||
         $filter_type_value == $fir_compiler_v3_2_analysis::c_decimating_half_band ||
         $filter_type_value == $fir_compiler_v3_2_analysis::c_interpolating_half_band } {
      # Removal of upper half of index vector for halfband or hilbert cases
      #for { set index [ expr { $taps_calced/2 } ] } { $index < $taps_calced } {incr index} {
      #  lset mod_coefficients $tap [ lindex $mod_coefficients [ expr { $tap+1} ] ]
      #}
      set mod_coefficients [ lreplace $mod_coefficients [ expr { $taps_calced/2 + 1 } ] end ]
    } elseif { $filter_type_value == $fir_compiler_v3_2_analysis::c_hilbert_transform } {
      set mod_coefficients [ lreplace $mod_coefficients [ expr { $taps_calced/2 } ] end ]
    }
    
    return $mod_coefficients

  }
  ;# end generate_Reload_Info

}
#namespace fir_compiler_v3_2_analysis

