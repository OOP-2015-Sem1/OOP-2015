#-------------------------------------------------------------------------------
# Copyright (c) 2005-2006 Xilinx, Inc.
# All rights reserved.
#-------------------------------------------------------------------------------
#    ____  ____
#   /   /\/   /
#  /___/  \  /   Vendor: Xilinx
#  \   \   \/    Version: 2.0
#   \   \        Filename: fir_compiler_v2_0_utils.tcl
#   /   /        Date Last Modified: 08/05/06
#  /___/   /\    Date Created: 03/05/06
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

namespace eval fir_compiler_v2_0_utils {

   proc Num_DSP_Col { c_xdevice } {

     ;# puts "**********NUM DSP COL RUN **********"
     
     #puts "$c_xdevice"

     switch $c_xdevice {
       "xc4vlx15"    { return 1 }
       "xc4vlx25"    { return 1 }
       "xc4vlx40"    { return 1 }
       "xc4vlx60"    { return 1 }
       "xc4vlx80"    { return 1 }
       "xc4vlx100"   { return 1 }
       "xc4vlx160"   { return 1 }
       "xc4vlx200"   { return 1 }
       "xc4vfx12"    { return 1 }
       "xc4vfx20"    { return 1 }
       "xc4vfx40"    { return 1 }
       "xc4vfx60"    { return 2 }
       "xc4vfx100"   { return 2 }
       "xc4vfx140"   { return 2 }
       "xc4vsx25"    { return 4 }
       "xc4vsx35"    { return 4 }
       "xc4vsx55"    { return 8 }
       "xc5vlx30"    { return 1 }
       "xc5vlx50"    { return 1 }
       "xc5vlx85"    { return 1 }
       "xc5vlx110"   { return 1 }
       "xc5vlx220"   { return 2 }
       "xc5vlx330"   { return 2 }
       "xc5vlx30t"   { return 1 }
       "xc5vlx50t"   { return 1 }
       "xc5vlx110t"  { return 1 }
       "xc5vlx330t"  { return 2 }
       "xc5vsx35t"   { return 6 }
       "xc5vsx50t"   { return 6 }
       "xc5vsx95t"   { return 10 }
       "xa4vfx12"    { return 1 }
       default       { puts "Unrecognised Virtex4 or Virtex5 part"
                       return 0 }
     }
   }

   proc Num_DSP_per_Col { c_xdevice } {

      ;# puts "**********NUM DSP RUN **********"

     switch $c_xdevice {
       "xc4vlx15"    { return 32 }
       "xc4vlx25"    { return 48 }
       "xc4vlx40"    { return 64 }
       "xc4vlx60"    { return 64 }
       "xc4vlx80"    { return 80 }
       "xc4vlx100"   { return 96 }
       "xc4vlx160"   { return 96 }
       "xc4vlx200"   { return 96 }
       "xc4vfx12"    { return 32 }
       "xc4vfx20"    { return 32 }
       "xc4vfx40"    { return 48 }
       "xc4vfx60"    { return 64 }
       "xc4vfx100"   { return 80 }
       "xc4vfx140"   { return 96 }
       "xc4vsx25"    { return 32 }
       "xc4vsx35"    { return 48 }
       "xc4vsx55"    { return 64 }
       "xc5vlx30"    { return 32 }
       "xc5vlx50"    { return 48 }
       "xc5vlx85"    { return 48 }
       "xc5vlx110"   { return 64 }
       "xc5vlx220"   { return 64 }
       "xc5vlx330"   { return 96 }
       "xc5vlx30t"   { return 32 }
       "xc5vlx50t"   { return 48 }
       "xc5vlx110t"  { return 64 }
       "xc5vlx330t"  { return 96 }
       "xc5vsx35t"   { return 32 }
       "xc5vsx50t"   { return 48 }
       "xc5vsx95t"   { return 64 }
       "xa4vfx12"    { return 32 }
       default       { puts "Unrecognised Virtex4 or Virtex5 part"
                       return 0 }
     }
   }

   proc log2 { value } {

     #puts "Executing log2 function on value $value"
     set abc1 [ expr { log($value) } ]
     set abc2 [ expr { log(2) } ]
     set abc3 [ expr { $abc1/$abc2 } ]
     #puts "Division result = $abc3"

     return $abc3
   }

   proc log2int { value } {

     #puts "Executing log2int function on value $value"
     set abc1 [ fir_compiler_v2_0_utils::log2 $value ]
     set abc2 [ expr { int($abc1) } ]
     #puts "Rounding = $abc2"

     return $abc2
   }

   proc log2ceil { value } {

     #puts "Executing log2ceil function on value $value"
     set abc1 [ fir_compiler_v2_0_utils::log2 $value ]
     set abc2 [ expr { ceil($abc1) } ]
     #puts "Ceiling = $abc2"

     return $abc2
   }

   proc get_IdealCoefficients { radix coefficients signed width } {

      ;# convert all radix to signed radix 10 coefficients
      ;# NEED TO ADD WIDTH PARAMETER!!!
      set vector ""
      foreach coeff $coefficients {
         switch  $radix {
            "2"  { lappend vector [ConvertBase  2 10 $coeff $signed $width ] }
            "10" { lappend vector $coeff }
            "16" { lappend vector [ConvertBase 16 10 $coeff $signed $width ] }
         }
      }

      return $vector

   } ;# end get_IdealCoefficients

   ###############################################################################
   # Routine to check for a null string instead of an integer
   ###############################################################################
   proc check_valid_integer {xcoValue} {
     
     if {$xcoValue == ""} {
       return "1"
     } elseif {[string is integer $xcoValue]} { 
       return "$xcoValue"
     } else {
       # just return a default to stop the function failing
       # a check elsewhere will ensure that non-numeric characters are flagged as an error
       return "1"
     }
   }

   ###############################################################################
   # Routine to check for a null string instead of a real
   ###############################################################################
   proc check_valid_real {xcoValue} {
     
     if {$xcoValue == ""} {
       return "1"
     } elseif {[string is double $xcoValue]} { 
       return "$xcoValue"
     } else {
       # just return a default to stop the function failing
       # a check elsewhere will ensure that non-numeric characters are flagged as an error
       return "1"
     }
   }

   ###############################################################################
   # Routine to check for a null string instead of a number
   ###############################################################################
   proc check_valid_number {xcoValue} {
     
     if {$xcoValue == ""} {
       return "1"
     } elseif {[string is integer $xcoValue]} { 
       return "$xcoValue"
     } elseif {[string is double $xcoValue]} { 
       return "$xcoValue"
     } else {
       # just return a default to stop the function failing
       # a check elsewhere will ensure that non-numeric characters are flagged as an error
       return "1"
     }
   }
   
   ###############################################################################
   # Find GCD for P/Q latency calc's
   ###############################################################################
   proc get_gcd { valx valy } {

    set int_x $valx
    set int_y $valy
    set int_t 0

    while { $int_x > 0 } {

      if { $int_x < $int_y } {

        set int_t $int_x

        set int_x $int_y

        set int_y $int_t
      }

      set int_x [ expr { $int_x - $int_y } ]
    }

    return $int_y
   }
   
   ###############################################################################
   # Work out number of RAM needed given depth and width
   ###############################################################################
   proc num_BRAM { width depth } {
   
    if { $depth <= 16384 && $depth > 8192 } {
      
      set num_rams [ expr { int(ceil(double($width)/1)) } ]

    } elseif { $depth <= 8192 && $depth > 4096 } {
      
      set num_rams [ expr { int(ceil(double($width)/2)) } ]

    } elseif { $depth <= 4096 && $depth > 2048 } {
    
      set num_rams [ expr { int(ceil(double($width)/4)) } ]

    } elseif { $depth <= 2048 && $depth > 1024 } {
      
      set num_rams [ expr { int(ceil(double($width)/9)) } ]

    } elseif { $depth <= 1024 && $depth > 512 } {
      
      set num_rams [ expr { int(ceil(double($width)/18)) } ]

    } elseif { $depth <= 512 } {
      
      set num_rams [ expr { int(ceil(double($width)/36)) } ]

    } else {
      ;# this case means multiple 1-bit rams per bit of width

      set num_per_bit [ expr { int(ceil(double($depth)/16384)) } ]
      set num_rams [ expr { $width * $num_per_bit } ]

    }
    
    return $num_rams
    
   }

   ############################################################################
   # Determine if symmetric structure is to be used
   ############################################################################
   proc calc_symmetry { sym_override coef_struct rate_type } {
     
     if { $sym_override == 0 } {

       if { $coef_struct == "Half_Band" || $coef_struct == "Hilbert" } {
         set symmetry 1
       } elseif { $rate_type == "Integer"  } {
         if { $coef_struct != "Non_Symmetric" } {
           set symmetry 1
         } else {
           set symmetry 0
         }
       } else {
         set symmetry 0
       }

     } else {
       set symmetry 0
     }
     return $symmetry
   }

   ############################################################################
   # Determine if odd symmetric structure is to be used
   ############################################################################
   proc calc_odd_symmetry { symmetry num_taps } {
     
     if { $symmetry == 1 && [ expr { $num_taps % 2 } ] > 0 } {
        return 1;
     } else {
        return 0;
     }
     
   }

   ############################################################################
   # Determine number of actual filter taps to calculate, after folding for symmetry
   ############################################################################
   proc get_taps_to_calc { symmetry odd_symmetry num_taps filt_type coef_struct inter_rate } {

     if { $symmetry == 1 } {
       set taps_to_calc [ expr { int(ceil( double($num_taps)/2)) } ]

       if { $filt_type == "Interpolation" && $odd_symmetry == 1 && $coef_struct != "Half_Band" } {
         set taps_to_calc [ expr { int(ceil( double($num_taps)/2)) + ( int(floor(double($inter_rate)/2))) } ]
       }

     } else {
       set taps_to_calc $num_taps
     }
     return $taps_to_calc
   }
   
   ############################################################################
   # Determine effective rate (for latency calculation purposes only)
   ############################################################################
   proc get_effective_rate { filt_type rate_type coef_struct inter_rate deci_rate } {

      if { $rate_type == "Fixed_Fractional" } {
        set rate 1
      } elseif { $coef_struct == "Half_Band" || $coef_struct == "Hilbert" } {
        set rate 2
      } elseif { $filt_type == "Interpolation" } {
        set rate $inter_rate
      } elseif { $filt_type == "Decimation" } {
        set rate $deci_rate
      } else {
        set rate 1
      }
      return $rate
   }
      
   ############################################################################
   # Determine the number of taps per phase
   ############################################################################
   proc get_taps_per_phase { filt_type rate_type p_val q_val taps_to_calc coef_struct rate } {

     if { $rate_type == "Fixed_Fractional" } {
       if { $p_val > $q_val } {
         ;# Interpolating
         set taps_per_phase [ expr { int(ceil(double($taps_to_calc) / $p_val)) } ]
       } else {
         ;# Decimating
         set taps_per_phase [ expr { int(ceil(double($taps_to_calc) / $q_val)) } ]
         set taps_per_phase [ expr { int(ceil(double($taps_per_phase) / $p_val)) * $p_val } ]
       }
     } elseif { $coef_struct == "Half_Band" || $coef_struct == "Hilbert" || $filt_type == "Decimation" } {
       set taps_per_phase [ expr { int(ceil(double($taps_to_calc) / $rate)) } ]
     } else {
       set taps_per_phase $taps_to_calc
     }
     return $taps_per_phase
   }
      
   ############################################################################
   # Determine the required memory space per tap for data
   ############################################################################
   proc get_data_mem_space { filt_type rate_type coef_struct taps_per_mac q_val rate zpf } {

     if { $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" } {
       set data_mem_space [ expr { int( $taps_per_mac * $q_val) } ]
     } elseif { $coef_struct != "Half_Band" && $filt_type == "Interpolation" && $rate_type == "Integer" } {
       set data_mem_space [ expr { int( $taps_per_mac / $rate) } ]
     } elseif { ( $coef_struct != "Half_Band" && $filt_type == "Decimation" ) || ( $filt_type == "Single_Rate" && ( $coef_struct == "Half_Band" || $coef_struct == "Hilbert" )) } {
       set data_mem_space [ expr { int( $taps_per_mac * $rate) } ]
     } elseif { $filt_type == "Interpolated" } {
       set data_mem_space [ expr { int( $taps_per_mac * $zpf) } ]
     } else {
       set data_mem_space $taps_per_mac
     }
     return $data_mem_space
   }
      
   ############################################################################
   # Determine the required memory space per tap for coefficients
   ############################################################################
   proc get_coef_mem_space { filt_type rate_type coef_struct taps_per_mac p_val q_val rate } {

     if { $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" } {
       set coef_mem_space [ expr { int( $taps_per_mac * $q_val) } ]
     } elseif { $filt_type == "Interpolation" && $rate_type == "Fixed_Fractional" } {
       set coef_mem_space [ expr { int( $taps_per_mac * $p_val) } ]
     } elseif { $coef_struct != "Half_Band" && $filt_type == "Decimation" } {
       set coef_mem_space [ expr { int( $taps_per_mac * $rate) } ]
     } else {
       set coef_mem_space $taps_per_mac
     }
     return $coef_mem_space
   }
      
   ############################################################################
   # Determine the type of data memory
   ############################################################################
   proc get_data_mem_type { mem_option data_mem_space num_channels srl16_depth_thres coef_struct filt_type rate_type data_buff_type } {

     if { $mem_option == "Automatic" } {
    
       if { [ expr { $data_mem_space * $num_channels } ] > $srl16_depth_thres ||
            ( $coef_struct == "Non_Symmetric" && $filt_type == "Interpolation" && $rate_type == "Integer" && $num_channels > 1 ) ||
            ( $filt_type == "Interpolation" && $rate_type == "Fixed_Fractional" && $num_channels > 1 ) } {
         set data_mem_type 1
       } else {
         set data_mem_type 0
       }
    
     } elseif { $data_buff_type == "Distributed" } {
     
       set data_mem_type 0

       #set temp_val [ expr { $num_channels * pow(2, [fir_compiler_v2_0_utils::log2ceil  $data_mem_space  ]) } ]

       if { ( $coef_struct == "Non_Symmetric" && $filt_type == "Interpolation" && $rate_type == "Integer" && $num_channels > 1 ) ||
            [ expr {$num_channels * $data_mem_space} ] > 1024 ||
            ( $filt_type == "Interpolation" && $rate_type == "Fixed_Fractional" ) } {
         set data_mem_type 1
       }

     } else {
       set data_mem_type 1
     }
     return $data_mem_type
   }
      
   ############################################################################
   # Determine the type of coefficient memory
   ############################################################################
   proc get_coef_mem_type { mem_option coef_mem_space num_filts coef_reload dram_depth_thres srl16_depth_thres coef_buff_type } {

     if { $mem_option == "Automatic" } {

       #set temp_val [ expr { $num_filts * pow(2, [ fir_compiler_v2_0_utils::log2ceil  $coef_mem_space  ]) } ]
       set temp_val [ expr { $num_filts * $coef_mem_space } ]
       set cmp_depth [ expr { pow(2,[ fir_compiler_v2_0_utils::log2ceil  $temp_val   ] ) } ]
       if { $coef_reload == true } {
         set cmp_depth [ expr { 2 * $cmp_depth } ]

         if { $cmp_depth > $dram_depth_thres } {
           set coef_mem_type 1
         } else {
           set coef_mem_type 0
         }
       } else {
         if { $cmp_depth > $srl16_depth_thres } {
           set coef_mem_type 1
         } else {
           set coef_mem_type 0
         }
       }
     } elseif { $coef_buff_type == "Distributed" } {
       set coef_mem_type 0
     } else {
       set coef_mem_type 1
     }
     return $coef_mem_type
   }
      
   ############################################################################
   # Determine if it is possible to balance taps across MACs
   ############################################################################
   proc tap_balancing_ok { filt_type coef_struct rate_type num_channels } {
   
     if { ( $filt_type == "Single_Rate" ) ||
          ( $filt_type == "Interpolation" && $coef_struct != "Non_Symmetric" && $rate_type == "Integer" ) ||
          ( $filt_type == "Decimation" && $coef_struct != "Half_Band" && $rate_type == "Integer" ) ||
          ( $filt_type == "Decimation" && $coef_struct == "Half_Band" && $num_channels == 1 ) ||
          ( $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" ) ||
          ( $filt_type == "Interpolated" ) } {

       return 1
       
     } else { return 0 }
   }
      
   ############################################################################
   # Alter clk_per_channel for the tap balancing scenario
   ############################################################################
   proc balance_clk_per_channel { filt_type rate_type coef_struct halfband_single_mac \
                                  taps_per_phase num_macs rate p_val } {
   
     if { $coef_struct == "Half_Band" && $halfband_single_mac == 1 } {
       set new_clk_per_channel [ expr { $taps_per_phase + 1 } ]
       #puts "UTILS : new_clk_per_channel : $new_clk_per_channel ( hb_smac )"
     } else {
       set new_clk_per_channel [ expr { int(ceil(double($taps_per_phase) / $num_macs)) } ]
       #puts "UTILS : new_clk_per_channel : $new_clk_per_channel ( not hb_smac )"
     }
     
     
     if { $filt_type == "Interpolation" && 
          $coef_struct != "Non_Symmetric" && 
          $rate_type == "Integer"  &&
          ( $halfband_single_mac != 1  ||
            $coef_struct != "Half_Band" ) } {
       set new_clk_per_channel [ expr { int(ceil(double($new_clk_per_channel) / $rate )) * $rate } ]
       #puts "UTILS : new_clk_per_channel : $new_clk_per_channel ( non hb_smac int symm )"
     }
     
     if { $filt_type == "Decimation" && $rate_type == "Fixed_Fractional" } {
       set new_clk_per_channel [ expr { int(ceil(double($new_clk_per_channel) / $p_val )) * $p_val } ]
       #puts "UTILS : new_clk_per_channel : $new_clk_per_channel ( frac dec )"
     }
   
     return $new_clk_per_channel
   
   }
      
   ############################################################################
   # Determine number of taps calculated per MAC, without tap balancing
   ############################################################################
   proc get_taps_per_mac { filt_type rate_type coef_struct p_val q_val clk_per_channel taps_per_phase rate } {
   
     if { $rate_type == "Fixed_Fractional" } {

       if { $p_val > $q_val } {
       ;# Interpolating
         set taps_per_mac [ expr { int( $clk_per_channel / $p_val ) * $q_val } ]
       } else {
         set taps_per_mac [ expr { int( $clk_per_channel / $p_val ) * $p_val } ]
       }

     } elseif { $filt_type == "Interpolation" } {
       if { $coef_struct == "Half_Band" && [ expr { int( $taps_per_phase / $clk_per_channel ) } ] } {
         set taps_per_mac $clk_per_channel        
       } else {
         set taps_per_mac [ expr { int( $clk_per_channel / $rate ) * $rate } ]
       }
     } elseif { $coef_struct == "Half_Band" && $filt_type == "Decimation" } {
       set taps_per_mac [ expr { $clk_per_channel * $rate } ]
       #set clk_per_channel $taps_per_mac
     } else {
       set taps_per_mac $clk_per_channel
     }
     if { $taps_per_mac < 1 } { set taps_per_mac 1 }
     
     return $taps_per_mac
   
   }
      
   ############################################################################
   # Determine whether or not this is a halfband single MAC structure
   ############################################################################
   proc get_hb_single_mac { coef_struct filt_type taps_per_phase clk_per_channel taps_per_mac } {
   
     if { $coef_struct == "Half_Band" && $filt_type == "Interpolation" } {
       if { [ expr { int( $taps_per_phase / $clk_per_channel ) } ] == 0 } {
         return 1
         #set taps_per_mac $clk_per_channel
       } else {
         return 0
       }
     } elseif { $coef_struct == "Half_Band" } {
       if { [ expr { int($taps_per_phase / $taps_per_mac) } ] == 0 } {
         return 1
       } else {
         return 0
       }
     } else {
       return 0
     }
   }
      
   ############################################################################
   # Determine if symmetry needs to be overridden, in which case we need to
   # recalculate the intermediate variables again.
   ############################################################################
   proc check_sym_override { filt_type coef_struct clk_per_channel symmetry has_nd \
                             num_taps num_macs num_channels deci_rate zpf rate } {
   
     #Parallel sym check
     if { ($clk_per_channel == 1 && $symmetry == 1 && $has_nd == true &&
          ( ( $filt_type != "Decimation" && $filt_type != "Interpolation" ) || $coef_struct == "Half_Band" )) ||
           ($clk_per_channel == 1 && $symmetry == 1 && $filt_type == "Decimation" && $coef_struct != "Half_Band" ) } {
       ;# do sum to work out if fully parallel sym structure is possible and baseblock shiftram will
       ;# not be called with to deep a memory requirement, qualify to only structure check is done for

       if { $coef_struct != "Half_Band" && $filt_type == "Decimation" } {
         #For decimation symmetry a different calculation must be done to work out ram depth
         if { [ expr { ( ($num_macs-1) * $num_channels * $deci_rate  ) } ] > 1089 } {
           set sym_override 1
           ;#not suitable for symmetry
         } else {
           set sym_override 0
         }
       } else {
         #Original calculation
         if { $filt_type == "Interpolated" } {
           set local_rate $zpf
         } elseif { ( $coef_struct != "Half_Band" && $filt_type == "Decimation"  ) ||
                    ( $coef_struct == "Half_Band" && $filt_type == "Single_Rate" ) ||
                      $coef_struct == "Hilbert" } {
           set local_rate $rate
         } else {
           set local_rate 1
         }
         if { [ expr { $num_taps % 2 } ] > 0 } {
           ;#odd sym
           set sym_buff_depth 0
         } else {
           set sym_buff_depth [ expr { $num_channels * $local_rate } ]
         }
         if { [ expr { $sym_buff_depth + ( ($num_macs-1) * $num_channels * $local_rate * 2 ) } ] > 1089 } {
           set sym_override 1
           ;#not suitable for symmetry
         } else {
           set sym_override 0
         }
       }
     } else {
       set sym_override 0
     }
     return $sym_override
   }
      
   ############################################################################
   # Shorter version of coef_struct_modifier for use by mac6_analysis only
   ############################################################################
   proc get_coefwidth_struct_modifier { filt_type rate_type inter_rate num_taps coef_struct } {
   
     # Only increase coefwidth by 1 if using Interpolating Symmetry for MAC in DSP48 capable devices
     if { $filt_type == "Interpolation" && $rate_type == "Integer" } {
       if { [ expr { $inter_rate % 2 } ] == 1 || [ expr { $num_taps % 2 } ] == 0 } {
         if { $coef_struct == "Symmetric" || $coef_struct == "Negative_Symmetric" } {
           return 1
         } else { return 0 }
       } else { return 0 }
     } else { return 0 }
   }
   
   ############################################################################
   # Calculate the output width of the filter
   ############################################################################
   proc get_output_width { filt_arch filt_type coef_struct num_taps coef_sign coef_width data_sign data_width inter_rate } {
   
     # Based on structure, adjust values used to calculate output width
     # Remove zeroes from Hilbert and Half_Band
     if { $coef_struct == "Hilbert" } {
       set local_num_taps [ expr { int( ( $num_taps + 1 ) / 2 ) } ]
     } elseif { $coef_struct == "Half_Band" } {
       set local_num_taps [ expr { int( ( $num_taps + 1 ) / 2 ) + 1 } ]     
     } else {
       set local_num_taps $num_taps
     }
     
     # If DA, increment effective datawidth if data unsigned but coeff signed
     if {  $filt_arch == "Distributed_Arithmetic" && $coef_sign == "Signed" && $data_sign == "Unsigned" } {
       set local_data_width [ expr { $data_width+1 } ]     
     } else {
       set local_data_width $data_width
     }
     ;# puts "Effective data width has been determined as $data_width"

     # Need to adjust coefficient width for DA single bit cases
     if { $filt_arch == "Distributed_Arithmetic" && $local_data_width == 1 && $data_sign == "Signed" && $coef_sign == "Unsigned" } {
       set local_coef_width [expr { $coef_width + 1 } ]
     } else {
       set local_coef_width $coef_width
     }
     ;# puts "Effective coeff width has been determined as $coef_width"
     
     # Need further adjustments for interpolating filters based on rate
     if {$filt_type == "Interpolation"} {

         # Special case for halfband, only remove centre tap
         if { $coef_struct == "Half_Band" } {
           set local_num_taps [expr { $local_num_taps - 1 } ]
         } else {     
           set local_num_taps [expr { int( ( $local_num_taps + $inter_rate - 1) / $inter_rate ) } ]
         }
     }
     ;# puts "Number of taps calculated has been determined as $num_taps"

     set bitgrowth 0
     while { [expr { pow(2, $bitgrowth) }] < $local_num_taps } {
       set bitgrowth [expr { $bitgrowth + 1 }]
     }
     ;# puts "Calculated log2 of $num_taps as $bitgrowth"
     
     if { $local_data_width == 1 && $local_coef_width == 1 } {
       if { $data_sign == "Signed" && $coef_sign == "Signed" } {
         set output_width [ expr { 2 + $bitgrowth } ]
       } else {
         set output_width $bitgrowth
       }
     } elseif { $local_data_width == 1 } {
       set output_width [ expr { $local_coef_width + $bitgrowth } ]
     } elseif { ( $local_coef_width == 1 ) } {
       set output_width [ expr { $local_data_width + $bitgrowth } ]
     } else {
       set output_width [ expr { $local_data_width + $local_coef_width + $bitgrowth } ]
     }
     return $output_width
   }
   
   ############################################################################
   # 
   ############################################################################
   #proc aaaa {  } {
   #
   #
   #}
   
   ############################################################################
   # Determine whether or not an accumulator is required
   ############################################################################
   proc calc_acum_req { num_macs coef_struct filt_type rate_type taps_per_mac rate } {

      if {  $num_macs != 1
            && (
                 (
                   (
                     ( $coef_struct != "Half_Band" &&
                         ( $filt_type == "Single_Rate" ||  ( $filt_type == "Interpolated" && $rate_type == "Integer" )
                         )
                     )
                    || $coef_struct == "Hilbert"
                   )
                  && $taps_per_mac == 1
                 )
                ||
                ( $coef_struct == "Non_Symmetric" && $filt_type == "Interpolation" && $rate_type == "Integer" && [ expr { int( $taps_per_mac / $rate) } ] == 1
                )
               ) } {
        return 0
      } else {
        return 1
      }

   }
   
   ############################################################################
   # Determine the number of taps actually calculated
   ############################################################################
   proc get_taps_calced { filt_type coef_struct rate_type inter_rate deci_rate rate num_macs taps_per_mac } {

     if { $rate_type == "Fixed_Fractional" && $filt_type == "Decimation" } {
       return [ expr { $num_macs * $taps_per_mac * $deci_rate } ]
     } elseif { $rate_type == "Fixed_Fractional" } {
       return [ expr { $num_macs * $taps_per_mac * $inter_rate } ]
     } elseif { $coef_struct == "Half_Band" || $coef_struct == "Hilbert" || $filt_type == "Decimation" } {
       return [ expr { $num_macs * $taps_per_mac * $rate } ]
     } else {
       return [ expr { $num_macs * $taps_per_mac } ]
     }

   }
   
   ############################################################################
   # Determine the number padded coefficients
   ############################################################################
   proc get_padded_taps { halfband_single_mac core_taps_calced taps_to_calc } {

     if { $halfband_single_mac == 1 } {
       return [ expr { $core_taps_calced - $taps_to_calc - 2 } ]
     } else {
       return [ expr { $core_taps_calced - $taps_to_calc } ]
     }

   }
   
   
   
}
