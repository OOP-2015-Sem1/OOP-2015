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

namespace eval fir_compiler_v2_0_resource {

  proc calc_syst_dsp48s { num_macs acum_req } {
    if { $num_macs == 1 && $acum_req == 1 } {
      return 1
    } else {
      return [ expr { $num_macs + $acum_req } ]
    }
  }
  
  proc calc_syst_brams { filt_type num_taps \
                         rate_type inter_rate rate \
                         data_width coef_width output_width \
                         mem_option data_buff_type coef_buff_type \
                         num_filts num_channels coef_reload \
                         num_macs \
                         data_mem_space coef_mem_space \
                         coef_struct symmetry \
                         dram_depth_thres srl16_depth_thres } {
    
    # Globals - trying to use as few as possible
    global has_ip_buff
    global ip_buff_depth
    global has_op_buff
    global op_buff_depth
    
    ;# calculate_resource $data_mem_type $data_mem_space $coef_mem_type $coef_mem_space
  
    # BRAM estimation

    ;# calculate_coefwidth_int_sym_modifier

    set cw_stuct_mod [ fir_compiler_v2_0_utils::get_coefwidth_struct_modifier $filt_type $rate_type $inter_rate $num_taps $coef_struct ]
    set coef_width_val [ expr { $coef_width + $cw_stuct_mod } ]

    # Input buffer
    if { $has_ip_buff == 1 } {

      if { $mem_option == "Automatic" } {
        if { $ip_buff_depth > $dram_depth_thres } {
          set ipbuff_num [ fir_compiler_v2_0_utils::num_BRAM $data_width $ip_buff_depth ]
        } else {
          set ipbuff_num 0
        }
      } elseif { $data_buff_type == "Distributed" } {
        set ipbuff_num 0
      } else {
        set ipbuff_num [ fir_compiler_v2_0_utils::num_BRAM $data_width $ip_buff_depth ]
      }

    } else {
      set ipbuff_num 0
    }
  
    # Data memory
    if { $mem_option == "Automatic" } {
       
       if { ( $coef_struct == "Non_Symmetric" && $filt_type == "Interpolation" && $rate_type == "Integer" && $num_channels > 1 ) ||
            ( $filt_type == "Interpolation" && $rate_type == "Fixed_Fractional" ) } {
          set max_depth $dram_depth_thres
       } else {
          set max_depth $srl16_depth_thres
       }

       if { [ expr { $data_mem_space * $num_channels } ] > $max_depth } {
          set dataram_num [ fir_compiler_v2_0_utils::num_BRAM $data_width $data_mem_space ]
       } else {
          set dataram_num 0
       }
    } elseif { $data_buff_type == "Distributed" } {
       set dataram_num 0
    } else {
       set dataram_num [ fir_compiler_v2_0_utils::num_BRAM $data_width $data_mem_space ]
    }
  
    # Data sym memory
    if { $symmetry == 1 } {
      if { $dataram_num > 0 } {

        set depth_comb [ expr { 2 * $data_mem_space } ]
        set num_to_comb [ fir_compiler_v2_0_utils::num_BRAM $data_width $depth_comb ]

        if { $num_to_comb > 1 } {
        # seperate rams
          set datasymram_num $dataram_num
        } else {
        #same ram
          set datasymram_num 0
        }

      } else {
        set datasymram_num 0
      }

    } else {
      set datasymram_num 0
    }

    set dataram_num [ expr { $dataram_num * $num_macs } ]
    set datasymram_num [ expr { $datasymram_num * $num_macs } ]
  
    set coef_mem_space [ expr { $coef_mem_space * $num_filts } ]
  
    # Coef memory
  
    if { $coef_reload == true } {

      set coef_mem_space [ expr { 2 * pow(2,[ fir_compiler_v2_0_utils::log2ceil  $coef_mem_space   ] ) } ]

      if { $mem_option == "Automatic" } {
        if { $coef_mem_space > $dram_depth_thres } {
          set coefram_num [ fir_compiler_v2_0_utils::num_BRAM $coef_width_val $coef_mem_space ]
        } else {
          set coefram_num 0
        }
      } elseif { $coef_buff_type == "Distributed" } {
        set coefram_num 0
      } else {
        set coefram_num [ fir_compiler_v2_0_utils::num_BRAM $coef_width_val $coef_mem_space ]
      }

    } else {
      if { $mem_option == "Automatic" } {

        set can_pack 0

        if { $symmetry == 0  } {
        #can try and pack

          set depth_comb [ expr { $data_mem_space + $coef_mem_space } ]
          if { $data_width > $coef_width_val } {
            set comb_width $data_width
          } else {
            set comb_width $coef_width_val
          }
          set num_to_comb [ fir_compiler_v2_0_utils::num_BRAM $comb_width $depth_comb ]

          if { $num_to_comb == 1 } {
          #can pack
            set coefram_num 0
            set can_pack 1
          }
        }

        if { $symmetry == 1 || $can_pack == 0 } {

          if { $coef_mem_space > $srl16_depth_thres } {
            set coefram_num [ fir_compiler_v2_0_utils::num_BRAM $coef_width_val $coef_mem_space ]
          } else {
            set coefram_num 0
          }

        }

      } elseif { $coef_buff_type == "Distributed" } {
        set coefram_num 0
      } else {

        if { $symmetry == 0 && $dataram_num > 0 } {
          #try and pack
          set depth_comb [ expr { $data_mem_space + $coef_mem_space } ]
          if { $data_width > $coef_width_val } {
            set comb_width $data_width
          } else {
            set comb_width $coef_width_val
          }
          set num_to_comb [ fir_compiler_v2_0_utils::num_BRAM $comb_width $depth_comb ]

          if { $num_to_comb == 1 } {
          #can pack
            set coefram_num 0
          } else {
            set coefram_num [ fir_compiler_v2_0_utils::num_BRAM $coef_width_val $coef_mem_space ]
          }
        } else {
          set coefram_num [ fir_compiler_v2_0_utils::num_BRAM $coef_width_val $coef_mem_space ]
        }
      }
     } ;# end not reload

     set coefram_num [ expr { $coefram_num * $num_macs } ]

     # Output buffer

     if { $has_op_buff == 1 } {
      if { $mem_option == "Automatic" } {
        if { $op_buff_depth > $dram_depth_thres } {
          set opbuff_num [ fir_compiler_v2_0_utils::num_BRAM $output_width $op_buff_depth ]
        } else {
          set opbuff_num 0
        }
      } elseif { $data_buff_type == "Distributed" } {
        set opbuff_num 0
      } else {
        set opbuff_num [ fir_compiler_v2_0_utils::num_BRAM $output_width $op_buff_depth ]
      }
     } else {
      set opbuff_num 0
     }

     # Deci sym wrap buffer

     set decisym_num 0

     if { ( $coef_struct == "Symmetric" || $coef_struct == "Negative_Symmetric" ) &&
          $filt_type == "Decimation" && $rate_type == "Integer" } {

        set temp_val [ expr { 2 * pow(2, [ fir_compiler_v2_0_utils::log2ceil  $rate  ]) } ]
        set decisym_depth [ expr { $num_channels * $temp_val } ]

        if { $mem_option == "Automatic" } {
          if { $decisym_depth > $dram_depth_thres } {
            set decisym_num [ fir_compiler_v2_0_utils::num_BRAM $data_width $decisym_depth ]
          } else {
            set decisym_num 0
          }
        } elseif { $data_buff_type == "Distributed" } {
          set decisym_num 0
        } else {
          set decisym_num [ fir_compiler_v2_0_utils::num_BRAM $data_width $decisym_depth ]
        }

     }
     

     set re_num_brams [ expr {  $ipbuff_num +
                                $dataram_num +
                                $datasymram_num +
                                $coefram_num +
                                $opbuff_num +
                                $decisym_num } ]
                              
    puts " ipbuff_num       : $ipbuff_num"
    puts " dataram_num      : $dataram_num"
    puts " datasymram_num   : $datasymram_num"
    puts " coefram_num      : $coefram_num"
    puts " opbuff_num       : $opbuff_num"
    puts " decisym_num      : $decisym_num"
    puts " TOTAL            : $re_num_brams"

    return $re_num_brams
  }



}
