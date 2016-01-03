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

source [FindFullPath "com/xilinx/ip/fir_compiler_v2_0/gui/fir_compiler_v2_0_utils.tcl"]

namespace eval fir_compiler_v2_0_latency {

  proc fn_calculate_mac6_latency { \
                                   filt_type rate p_val q_val rate_type \
                                   coef_struct symmetry \
                                   num_channels num_filts coef_reload \
                                   mem_option coef_mem_space coef_mem_type \
                                   data_buff_type dram_depth_thres data_mem_type \
                                   column_mode col1_ln coln_ln pipe_ln \
                                   reg_output \
                                   num_macs acum_req taps_per_mac_balanced \
                                   clk_per_channel halfband_single_mac use_b_casc \
                                   } {

    # Globals - trying to use as few as possible
    global has_ip_buff
    global ip_buff_depth
    global has_op_buff
    global op_buff_depth
    
    set mac6_latency 0

    ;# ********************* Calc Delay for filter structure ********************

    set input_stage_casc 0
    set input_stage_samp 0

    set addr_gen_casc 0
    set addr_gen_samp 0
  
    set has_ip_buff 0
    set ip_buff_depth 0

    if { $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" } {

      set addr_gen_casc [ expr { int(floor(double($taps_per_mac_balanced)/$p_val))+1 } ]
    
    } elseif { $filt_type == "Interpolation" && $coef_struct != "Non_Symmetric" && $coef_struct != "Half_Band" && $rate_type == "Integer" } {

      set addr_gen_casc [ expr { int(floor(double($taps_per_mac_balanced)/$rate))+1 } ]

    } elseif { ( $filt_type == "Interpolation" && $coef_struct == "Non_Symmetric" && $rate_type == "Integer" ) ||
               ( $filt_type == "Interpolation" && $rate_type == "Fixed_Fractional" ) } {

      set addr_gen_casc [ expr { int(floor(double($taps_per_mac_balanced)/$rate))+1 } ]
    
      if { $num_channels > 1 } {
    
        set addr_gen_samp $num_channels
    
        set has_ip_buff 1
        set ip_buff_depth [ expr { 2*$num_channels } ]

        if { $num_filts  == 1 } {

          if { [ expr { int(floor(double($taps_per_mac_balanced)/$rate)) } ] == 2 } {
            if { ([ expr { 2 * $num_channels } ] > $dram_depth_thres && $mem_option == "Automatic" ) ||
                 ( $mem_option == "Custom" && $data_buff_type == "Block" ) } {
              set addr_gen_casc [ expr { $addr_gen_casc +2 } ]

              if { $rate_type == "Fixed_Fractional" } {
                set addr_gen_casc [ expr { $addr_gen_casc -1 } ]
              }
            }
          }

          if { [ expr { int(floor(double($taps_per_mac_balanced)/$rate)) } ] == 1 } {

            if { ( [ expr { 2 * $num_channels } ] > $dram_depth_thres && $mem_option == "Automatic" ) ||
                 ( $mem_option == "Custom" && $data_buff_type == "Block" ) } {

              set addr_gen_casc [ expr { $addr_gen_casc +2 } ]
            } else {
              set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
            }
          }
        } else {
      
          set addr_gen_casc [ expr { $addr_gen_casc +2 } ]

          if { ( [ expr { 2 * $num_channels } ] > $dram_depth_thres && $mem_option == "Automatic" ) ||
               ( $mem_option == "Custom" && $data_buff_type == "Block" ) } {
            set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
          }
        }

      }
    } elseif { $coef_struct == "Half_Band" && $filt_type == "Decimation" } {

      set addr_gen_tpm [ expr { $taps_per_mac_balanced - $halfband_single_mac } ]

      set addr_gen_samp [ expr { 2* $num_channels } ]
    
      set has_ip_buff 1
      set ip_buff_depth [ expr { 4*$num_channels } ]

      set addr_gen_casc [ expr { $taps_per_mac_balanced +1 } ]

      if { $addr_gen_tpm == 2 || ($addr_gen_tpm == 4 && $num_filts>1) } {

        set addr_gen_casc [ expr { $taps_per_mac_balanced +3 } ]

        if { ( [ expr { 4 * $num_channels } ] > $dram_depth_thres && $mem_option == "Automatic" ) ||
             ( $mem_option == "Custom" && $data_buff_type == "Block" && $num_channels >1 ) } {
          set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
        }

        if { $num_filts > 1 } {
          set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
        }
    
      } elseif { $data_mem_type == 0 && (
                  [ expr { 4 * $num_channels } ] <= $dram_depth_thres ||
                  ( $mem_option == "Custom" && $data_buff_type == "Distributed" ) )  } {
        set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
      }

      #puts "addr_gen_casc: $addr_gen_casc"

    } else {
    
      set addr_gen_casc [ expr { $taps_per_mac_balanced +1 } ]
    
      if { $taps_per_mac_balanced == 1 && $num_filts > 1 && $num_channels == 1 &&
           $filt_type == "Single_Rate" && $coef_struct == "Non_Symmetric" && $rate_type == "Integer" } {
        set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
      }
    }
  
    set addr_gen_casc [ expr { $addr_gen_casc +1 } ]


    if { $filt_type == "Decimation" && $coef_struct != "Half_Band" && $coef_struct != "Non_Symmetric" && $clk_per_channel == 1 && $symmetry == 1 } {
    
      set addr_gen_casc [ expr { $addr_gen_casc +2 } ]

      set temp_val   [ expr { $num_channels * $rate } ]
      set buff_depth [ expr { 2 * pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]
    
      set has_ip_buff 1
      set ip_buff_depth $buff_depth
    
      if { ( $mem_option == "Automatic" && $buff_depth > $dram_depth_thres ) ||
           ( $mem_option == "Custom" && $data_buff_type == "Block" ) } {
        set addr_gen_casc [ expr { $addr_gen_casc +1 } ]
      }
    }
  
    if { $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" } {
    
      set addr_gen_samp [ expr { $q_val * $num_channels } ]

      if { [ expr { $q_val % 2 } ] == 2 } {
        set addr_gen_samp [ expr { ($q_val + 1 ) * $num_channels } ]
      }
    } elseif { $filt_type == "Decimation" && $coef_struct != "Half_Band" } {
      set addr_gen_samp [ expr { (($rate-1) * $num_channels) +1  } ]
    } else {
      set addr_gen_samp 0
    }

    ;# puts "input_stage_casc: $input_stage_casc"
    ;# puts "input_stage_samp: $input_stage_samp"
    ;# puts "addr_gen_casc: $addr_gen_casc"
    ;# puts "addr_gen_samp: $addr_gen_samp"

    ;# Filter structure

    if { $coef_struct != "Non_Symmetric" && $coef_struct != "Half_Band" && $filt_type == "Interpolation" && $rate_type == "Integer" } {
      set inter_sym 1
    } else {
      set inter_sym 0
    }
  
    ;# puts "inter sym : $inter_sym"

    ;#Block latency
    if { ( $coef_struct != "Half_Band" && $num_macs == 1 && $inter_sym == 0 ) ||
         ( $coef_struct == "Half_Band" && $num_macs == 1 && $halfband_single_mac ==1 ) } {

      ;#Single mac

      #check if fully parallel as well
      if { $taps_per_mac_balanced == 1 ||
         ( $coef_struct != "Half_Band" && $filt_type == "Interpolation"  && $rate_type == "Integer" && [ expr { int( $taps_per_mac_balanced / $rate) } ] == 1 )  ||
         ( $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" && [ expr { int( $taps_per_mac_balanced / $p_val) } ] == 1 ) } {
        set no_data_mem 1
      } else {
        set no_data_mem 0
      }

      if { $data_mem_type == 1 && $no_data_mem == 0 && $symmetry == 1 } {
        set block_latency 3
      } elseif { ( $data_mem_type == 1 && $no_data_mem == 0 ) || $coef_mem_type == 1 || ( $symmetry == 1 && $no_data_mem == 0 ) } {
        set block_latency 2
      } else {
        set block_latency 1
      }
      if { $coef_struct == "Half_Band" } {
        set block_latency [ expr { $block_latency + 2 } ]
      }
    } else {

      if { $taps_per_mac_balanced == 1 || 
         ( $coef_struct != "Half_Band" && $filt_type == "Interpolation"  && $rate_type == "Integer" && [ expr { int( $taps_per_mac_balanced / $rate) } ] == 1 )  ||
         ( $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" && [ expr { int( $taps_per_mac_balanced / $p_val) } ] == 1 ) } {
        ;# Fully parallel
        set coef_delay 0
        if { [ expr { $coef_mem_space * $num_filts } ] > 1 || $coef_reload == true } {
          set data_delay [ expr { 1 + $coef_mem_type } ]
          if { $use_b_casc == 1 } {
            set data_delay [ expr { $data_delay - 1 } ]
          }
        } else {
          set data_delay 0
        }

        set block_latency [ expr { $data_delay + 1 } ]

        if { ( $coef_struct == "Non_Symmetric" && $filt_type == "Interpolation" && $rate_type == "Integer" && $num_channels > 1 ) } {
          set block_latency 3
          if { $coef_mem_type == 1 } {
            set coef_delay 0
          } else {
            set coef_delay 1
          }
        }
        if { $use_b_casc == 1 } {
          set block_latency [ expr { $block_latency - 2 } ]
        }
      } else {
        
        ;# Semi-parallel

        set block_latency 2
        if { $data_mem_type==1 || $coef_mem_type==1 } {
          set block_latency [ expr { $block_latency + 1 } ]
        }
        if { $data_mem_type == 1 } {
          set data_delay 0
          if { $coef_mem_type == 1 } {
            set coef_delay 0
          } else {
            set coef_delay 1
          }
        } else {
          if { $coef_mem_type == 1 } {
            set coef_delay 0
            set data_delay 1
          } else {
            set coef_delay 0
            set data_delay 0
          }
        }
      }

      if { $symmetry == 1 } {
        if { $coef_delay < $data_delay } {
          set data_delay [ expr { $data_delay - 1 } ]
        } else {
          set block_latency [ expr { $block_latency + 1 } ]
          set coef_delay [ expr { $coef_delay + 1 } ]
        }
      }

    }

    ;# puts "block_latency: $block_latency"

    if { $column_mode != "Disabled" } {
      if { $num_macs > $col1_ln } {
        set num_pipe_stages [ expr { int((1+($num_macs-$col1_ln-1)/$coln_ln)*$pipe_ln) } ]
      } else {
        set num_pipe_stages 0
      }
    } else {
      set num_pipe_stages 0
    }

    ;# Half_Band interpolating delay
    if { $coef_struct == "Half_Band" && $filt_type == "Interpolation" } {
      if { $halfband_single_mac == 1 } {
        set halfband_inter_dly 2
      } else {
        set halfband_inter_dly 1
      }
    } else {
      set halfband_inter_dly 0
    }

    set filter_struct_casc [ expr { $block_latency + $num_macs + $num_pipe_stages - $halfband_inter_dly - 1 + 1 } ]

    ;# puts "num_pipe_stages: $num_pipe_stages"
    ;# puts "filter_struct_casc: $filter_struct_casc"

    ;# Accumulator
    set accum_casc [ expr { $acum_req + $use_b_casc } ]
    if { $coef_struct == "Half_Band" && $filt_type == "Interpolation" } {
      set accum_casc [ expr { $accum_casc + 1 } ]
    }

    ;# puts "accum_casc: $accum_casc"

    ;# Output stage
  
    set has_op_buff 0
    set op_buff_depth 0

    if { ($coef_struct == "Half_Band" && $filt_type == "Interpolation") || 
         ($coef_struct != "Half_Band" && $filt_type == "Decimation" && $rate_type == "Integer" && $num_channels > 1) ||
         ($inter_sym == 1) ||
         ($filt_type == "Decimation" && $rate_type == "Fixed_Fractional" ) } {

      if { $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" } {
        set output_stage_samp 0
        set output_stage_casc 2

        set temp_val [ expr { $p_val * $num_channels } ]
        set temp_val [ expr { 2 * pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]
        set buff_depth [ expr { pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]

        set has_op_buff 1
        set op_buff_depth $buff_depth

        if { ( $mem_option == "Automatic" &&
              $buff_depth > $dram_depth_thres ) ||
            ( $mem_option == "Custom" &&
              $data_buff_type == "Block" ) } {
          set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        }

      } elseif { $inter_sym == 1 } {

        if { $num_channels > 1 } {
          set output_stage_samp $num_channels
        } else {
          set output_stage_samp 0
        }

        set output_stage_casc [ expr { $taps_per_mac_balanced - ( int(floor(double($taps_per_mac_balanced)/$rate)) - 1) } ]
        set output_stage_casc [ expr { $output_stage_casc + 4 } ]

        set temp_val [ expr { 2 * $rate * $num_channels } ]
        set buff_depth [ expr { pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]

        set has_op_buff 1
        set op_buff_depth $buff_depth

        if { ( $mem_option == "Automatic" &&
              $buff_depth > $dram_depth_thres ) ||
            ( $mem_option == "Custom" &&
              $data_buff_type == "Block" ) } {
          set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        }

      } elseif { $filt_type == "Decimation" } {

        # set output_stage_casc [ expr { $clk_per_channel * $rate } ]
        #  set output_stage_samp 0
        #  ;# RAM delay
        #  set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        #  if { ($num_channels > 16 && $mem_option == "Automatic") || ( $mem_option == "Custom" && $data_buff_type == "Block") } {
        #    set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        #  }
        set output_stage_samp [ expr { $num_channels - 1 } ]
        set output_stage_casc 2

        set temp_val [ expr { $num_channels } ]
        set temp_val [ expr { 2 * pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]
        set buff_depth [ expr { pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]

        set has_op_buff 1
        set op_buff_depth $buff_depth

        if { ( $mem_option == "Automatic" &&
               $buff_depth > $dram_depth_thres ) ||
             ( $mem_option == "Custom" &&
               $data_buff_type == "Block" ) } {
          set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        }

      } else {

        set output_stage_casc 2

        if { $num_channels > 1 } {
          set output_stage_samp $num_channels
        } else {
          set output_stage_samp 0
        }
        set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        set temp_val [ expr { 2* $num_channels } ]
        set temp_val [ expr { 2 * pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]
        set buff_depth [ expr { pow(2, [ fir_compiler_v2_0_utils::log2ceil  $temp_val  ]) } ]

        set has_op_buff 1
        set op_buff_depth $buff_depth

        if { ( $mem_option == "Automatic" &&
               $buff_depth > $dram_depth_thres ) ||
             ( $mem_option == "Custom" &&
               $data_buff_type == "Block" ) } {
          set output_stage_casc [ expr { $output_stage_casc + 1 } ]
        }

      }
      if { $reg_output } {
        set output_stage_casc [ expr { $output_stage_casc + 1 } ]
      }
    } else {
     ;# Output latch
     if { $reg_output } {
       set output_stage_casc 1
     } else {
       set output_stage_casc 0
     }
     set output_stage_samp 0
     if { $use_b_casc == 1 } {
       set output_stage_casc 0
     }
    }

    ;# puts "output_stage_casc: $output_stage_casc"
    ;# puts "output_stage_samp: $output_stage_samp"

    ;# Tot up the final delay
    set mac6_latency [ expr { $input_stage_casc + $addr_gen_casc + $filter_struct_casc + $accum_casc + $output_stage_casc } ]

    if { $num_macs == 1 && $halfband_single_mac == 1 & $coef_struct == "Half_Band" } {
      set mac6_latency [ expr { $mac6_latency - 1 } ]
    }

    puts "**** mac6_latency: $mac6_latency ****"

    return $mac6_latency

  }
  
}