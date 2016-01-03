
-------------------------------------------------------------------
-- System Generator version 13.4 VHDL source file.
--
-- Copyright(C) 2011 by Xilinx, Inc.  All rights reserved.  This
-- text/file contains proprietary, confidential information of Xilinx,
-- Inc., is distributed under license from Xilinx, Inc., and may be used,
-- copied and/or disclosed only pursuant to the terms of a valid license
-- agreement with Xilinx, Inc.  Xilinx hereby grants you a license to use
-- this text/file solely for design, simulation, implementation and
-- creation of design files limited to Xilinx devices or technologies.
-- Use with non-Xilinx devices or technologies is expressly prohibited
-- and immediately terminates your license unless covered by a separate
-- agreement.
--
-- Xilinx is providing this design, code, or information "as is" solely
-- for use in developing programs and solutions for Xilinx devices.  By
-- providing this design, code, or information as one possible
-- implementation of this feature, application or standard, Xilinx is
-- making no representation that this implementation is free from any
-- claims of infringement.  You are responsible for obtaining any rights
-- you may require for your implementation.  Xilinx expressly disclaims
-- any warranty whatsoever with respect to the adequacy of the
-- implementation, including but not limited to warranties of
-- merchantability or fitness for a particular purpose.
--
-- Xilinx products are not intended for use in life support appliances,
-- devices, or systems.  Use in such applications is expressly prohibited.
--
-- Any modifications that are made to the source code are done at the user's
-- sole risk and will be unsupported.
--
-- This copyright and support notice must be retained as part of this
-- text at all times.  (c) Copyright 1995-2011 Xilinx, Inc.  All rights
-- reserved.
-------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;
use ieee.math_real.all;
library xilinxcorelib;
use xilinxcorelib.bip_utils_pkg_v2_0.all;
use xilinxcorelib.cmpy_pkg_v3_0.all;
use xilinxcorelib.floating_point_v5_0_consts.all;
use xilinxcorelib.floating_point_pkg_v5_0.all;
package timing_model_pkg is
  constant MAX_NFFT_MAX      : integer := 16;
  constant NFFT_MAX_WIDTH    : integer := 5;
  constant NFFT_MAX_M1_WIDTH : integer := 4;
  constant MAX_TWIDDLE_WIDTH : integer := 55;
  constant DIST_RAM  : integer := 0;
  constant BLOCK_RAM : integer := 1;
  function correct_output_width(C_INPUT_WIDTH, C_HAS_SCALING, C_NFFT_MAX, C_USE_FLT_PT : integer) return integer;
  type T_RESOLVABLE_GENERICS is (RC_CHANNELS,
                                 RC_HAS_BFP,
                                 RC_HAS_OVFLO,
                                 RC_DATA_MEM_TYPE,
                                 RC_TWIDDLE_MEM_TYPE,
                                 RC_BRAM_STAGES,
                                 RC_REORDER_MEM_TYPE,
                                 RC_CMPY_TYPE,
                                 RC_BFLY_TYPE,
                                 RC_USE_HYBRID_RAM
                                 );
  type T_RESOLVED_GENERICS is array (T_RESOLVABLE_GENERICS'low to T_RESOLVABLE_GENERICS'high) of integer;
  function resolve_hybrid_ram_use(C_XDEVICEFAMILY : string; C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return boolean;
  type T_N_COUNT is (N_COUNT_NONE,
                     N_COUNT_XN_INDEX,
                     N_COUNT_XK_INDEX,
                     N_COUNT_XN_AND_XK_BITREVERSE,
                     N_COUNT_ADDR_GEN
                     );
  type hybrid_ram_properties is record
    bram_width : integer;
    dram_width : integer;
  end record hybrid_ram_properties;
  constant SO_BFP_RANGER_LATENCY                              : integer := 1;
  constant SO_BFP_MAXHOLD_LATENCY                             : integer := 2;
  function so_butterfly_latency(C_XDEVICEFAMILY               : string; FAST_BFY, HAS_INPUT_REG, BUTTERFLY_WIDTH : integer) return integer;
  function so_scale_latency(HAS_SCALING                       : integer) return integer;
  function so_round_latency(HAS_ROUNDING                      : integer) return integer;
  function so_bfp_scale_gen_latency(HAS_SCALING, HAS_ROUNDING : integer) return integer;
  function so_pe_latency(C_XDEVICEFAMILY                      : string; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, EXPAND_TW_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer;
  function so_data_reuse(NFFT : integer) return integer;
  constant max_num_of_pe : integer := (MAX_NFFT_MAX+1)/2;
  type r22_const_array is array (0 to max_num_of_pe) of integer;
  function get_nfft_min(ARCH, HAS_NFFT, NFFT_MAX : integer) return integer;
  function max_i(a, b                               : integer) return integer;
  function when_else(condition                      : boolean; if_true, if_false : integer) return integer;
  function mult_latency_bc(C_XDEVICEFAMILY          : string; CMPY_TYPE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer) return integer;
  function PE_latency_b(C_XDEVICEFAMILY             : string; C_BFLY_TYPE, DFLY_WIDTH, CMULT_DELAY, C_HAS_MULTS, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer;
  function radix4_dragonfly_latency(C_XDEVICEFAMILY : string; C_BFLY_TYPE, DFLY_WIDTH : integer) return integer;
  function r2_pe_latency(C_XDEVICEFAMILY            : string; C_BFLY_TYPE, BTRFLY_WIDTH, CMULT_DELAY, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer;
  type ram_counts is record
    main_rams  : integer;
    extra_rams : integer;
  end record ram_counts;
  function should_use_9k_ram_prim (rams_w_9k, rams_18k_only : ram_counts; depth : integer) return boolean;
  function check_use_9k_bram (C_XDEVICEFAMILY : string; width, depth : integer) return boolean;
  function calc_rams (C_XDEVICEFAMILY              : string; width, depth : integer; use_9k_bram : boolean) return ram_counts;
  function check_dpm_aspect_ratio (c_xdevicefamily : string; width : integer; depth : integer) return boolean;
  function calc_dist_mem_addr_latency (C_XDEVICEFAMILY : string; data_mem_depth : integer) return integer;
  function calc_dist_mem_mux_latency (C_XDEVICEFAMILY  : string; data_mem_depth : integer) return integer;
  function get_min_mem_delay(c_xdevicefamily           : string; data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram : integer) return integer;
  function get_mem_delay(c_xdevicefamily               : string; data_mem_type, data_mem_depth, sin_cos_delay, tw_addr_gen_delay, rw_addr_gen_delay, mux_delay, switch_delay, optimize_goal, use_hybrid_ram : integer) return integer;
  function calc_hybrid_ram_delay (c_xdevicefamily      : string; ram_depth, opt_goal : integer) return integer;
  function calc_hybrid_ram_widths (c_xdevicefamily     : string; ram_width, ram_depth : integer) return hybrid_ram_properties;
  function r22_mem_type(nfft_max, bram_stage, natural_input : integer) return r22_const_array;
  function r22_pe_width(scaling, nfft_max, input_bits       : integer) return r22_const_array;
  function r22_bf1_delay(C_XDEVICEFAMILY                    : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer;
  function r22_bf2_delay(C_XDEVICEFAMILY                    : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer;
  function r22_scale_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : integer) return integer;
  function r22_round_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : integer) return integer;
  function r22_pe_latency(C_XDEVICEFAMILY                   : string; C_BFLY_TYPE, C_CMPY_TYPE, has_nfft, nfft_max, tw_bits, has_scaling, has_rounding, has_mux, has_natural_input : integer; width_of_pe, memory_type : r22_const_array) return r22_const_array;
  function r22_right_shift_latency (shift_distance          : integer) return integer;
  type T_TWGEN_ARCH is (TW_BRAM_HALF_SINCOS,
                        TW_BRAM_QUARTER_SIN,
                        TW_DISTMEM,
                        TW_DISTMEM_SO
                        );
  function get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return T_TWGEN_ARCH;
  type T_TWGEN_TABLE is array (0 to integer(2**MAX_NFFT_MAX)-1) of std_logic_vector(MAX_TWIDDLE_WIDTH-1 downto 0);
  function get_twiddle_latency(C_XDEVICEFAMILY : string; MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return integer;
  function get_butterfly_latency (c_xdevicefamily : string; fast_bfly, data_width : integer) return integer;
  constant bf_v4simd   : integer := 0;
  constant bf_v4dsp    : integer := 1;
  constant bf_v4hybrid : integer := 2;
  constant bf_s3simd   : integer := 3;
  constant bf_s3dsp    : integer := 4;
  constant bf_s3hybrid : integer := 5;
  constant bf_v5simd   : integer := 6;
  constant bf_v5dsp    : integer := 7;
  constant bf_v5hybrid : integer := 8;
  function cmpy_latency(C_XDEVICEFAMILY : string; CMPY_TYPE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer; SINGLE_OUTPUT : integer := 0) return integer;
  constant sp_float_width      : integer := 32;
  constant sp_float_exp_width  : integer := 8;
  constant sp_float_mant_width : integer := 23;
  type t_sp_float is record
    sign : std_logic;
    exp  : std_logic_vector(sp_float_exp_width-1 downto 0);
    mant : std_logic_vector(sp_float_mant_width-1 downto 0);
  end record t_sp_float;
  function get_fixed_width(C_ARCH, fp_word_len     : integer) return integer;
  function get_fp_convert_to_block_fp_delay(family : string; fixed_width : integer) return integer;
  function get_fp_convert_to_fp_delay(family       : string; data_width, data_frac_width : integer) return integer;
  function allow_hybrid_ram_use (c_xdevicefamily            : string; mem_type, output_width, depth : integer) return boolean;
  function r22_allow_reorder_hybrid_ram_use(c_xdevicefamily : string; nfft_max, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal, has_bfp : integer) return boolean;
  function r22_allow_hybrid_ram_use (c_xdevicefamily        : string; bram_stages, nfft_max, has_scaling, has_bfp, fft_input_width, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal : integer) return boolean;
end timing_model_pkg;
package body timing_model_pkg is
  function correct_output_width(C_INPUT_WIDTH, C_HAS_SCALING, C_NFFT_MAX, C_USE_FLT_PT : integer) return integer is
    variable result : integer;
  begin
    if C_HAS_SCALING = 1 or C_USE_FLT_PT = 1 then
      result := C_INPUT_WIDTH;
    else
      result := C_INPUT_WIDTH + C_NFFT_MAX + 1;
    end if;
    return result;
  end correct_output_width;
  function resolve_hybrid_ram_use(C_XDEVICEFAMILY : string; C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return boolean is
    variable MEMORY_DEPTH     : integer := 0;
    variable RAM_OUTPUT_WIDTH : integer := 2*C_OUTPUT_WIDTH;
  begin
    if C_USE_HYBRID_RAM = 0 then
      return false;
    else
      if C_ARCH = 1 then
        MEMORY_DEPTH := C_NFFT_MAX - 2;
      elsif C_ARCH = 2 then
        MEMORY_DEPTH := C_NFFT_MAX - 1;
      elsif C_ARCH = 3 then
        MEMORY_DEPTH := C_NFFT_MAX;
      elsif C_ARCH = 4 then
        if check_dpm_aspect_ratio(C_XDEVICEFAMILY, RAM_OUTPUT_WIDTH, C_NFFT_MAX) then
        else
          if C_NFFT_MAX < 13 then
            RAM_OUTPUT_WIDTH := RAM_OUTPUT_WIDTH/2;
          else
            RAM_OUTPUT_WIDTH := RAM_OUTPUT_WIDTH/8;
          end if;
        end if;
        MEMORY_DEPTH := C_NFFT_MAX;

      end if;
      if (C_ARCH /= 3 and allow_hybrid_ram_use(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, RAM_OUTPUT_WIDTH, MEMORY_DEPTH) = false) or
        (C_ARCH = 3 and (r22_allow_hybrid_ram_use(C_XDEVICEFAMILY, C_BRAM_STAGES, C_NFFT_MAX, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, RAM_OUTPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL) = false)) then
        return false;
      else
        return true;
      end if;
    end if;
  end function resolve_hybrid_ram_use;
  function so_butterfly_latency(C_XDEVICEFAMILY : string; FAST_BFY, HAS_INPUT_REG, BUTTERFLY_WIDTH : integer) return integer is
    constant FABRIC_OUTPUT_REGISTER : integer := 1;
    constant basic_bfly_latency     : integer := get_butterfly_latency(C_XDEVICEFAMILY, FAST_BFY, BUTTERFLY_WIDTH);
  begin
    assert HAS_INPUT_REG = 1 report "ERROR: so_butterfly_latency: HAS_INPUT_REG must be set to 1 for this function to work" severity error;
    return basic_bfly_latency + FABRIC_OUTPUT_REGISTER*boolean'pos(FAST_BFY = 1);
  end so_butterfly_latency;
  function so_scale_latency(HAS_SCALING : integer) return integer is
  begin
    if HAS_SCALING = 1 then
      return 1;
    else
      return 0;
    end if;
  end so_scale_latency;
  function so_round_latency(HAS_ROUNDING : integer) return integer is
  begin
    if HAS_ROUNDING = 1 then
      return 3;
    else
      return 0;
    end if;
  end so_round_latency;
  function so_bfp_scale_gen_latency(HAS_SCALING, HAS_ROUNDING : integer) return integer is
  begin
    return so_scale_latency(HAS_SCALING) + so_round_latency(HAS_ROUNDING) + SO_BFP_RANGER_LATENCY + SO_BFP_MAXHOLD_LATENCY;
  end so_bfp_scale_gen_latency;
  function so_pe_latency(C_XDEVICEFAMILY : string; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, EXPAND_TW_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer is
    constant POSTMULT_WIDTH         : integer := C_OUTPUT_WIDTH + 6;
    constant BUTTERFLY_WIDTH        : integer := POSTMULT_WIDTH - 2;
    constant TWIDDLE_LATENCY        : integer := get_twiddle_latency(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, EXPAND_TW_WIDTH, true);
    constant INPUT_MUX_LATENCY      : integer := 1;
    constant DATA_MEM_LATENCY       : integer := INPUT_MUX_LATENCY + get_min_mem_delay(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
    constant READ_DATA_HOLD_LATENCY : integer := 1;
    constant COMPLEX_MULT_LATENCY   : integer := cmpy_latency(C_XDEVICEFAMILY, C_CMPY_TYPE, C_OUTPUT_WIDTH, EXPAND_TW_WIDTH, POSTMULT_WIDTH, 1, 1, 1, 1, 0, 1);
    constant BUTTERFLY_LATENCY      : integer := so_butterfly_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, 1, BUTTERFLY_WIDTH);
    constant READ_DATA_DELAY        : integer := max_i(TWIDDLE_LATENCY - DATA_MEM_LATENCY - READ_DATA_HOLD_LATENCY, 0);
    constant SCALER_LATENCY         : integer := so_scale_latency(C_HAS_SCALING);
    constant ROUNDER_LATENCY        : integer := so_round_latency(C_HAS_ROUNDING);
  begin
    return
      DATA_MEM_LATENCY +
      READ_DATA_DELAY +
      READ_DATA_HOLD_LATENCY +
      COMPLEX_MULT_LATENCY +
      BUTTERFLY_LATENCY +
      SCALER_LATENCY +
      ROUNDER_LATENCY;
  end so_pe_latency;
  function so_data_reuse(NFFT : integer) return integer is
    variable result : integer;
  begin
    result := (integer(2**(NFFT-1)) - 1);
    return result;
  end so_data_reuse;
  function get_nfft_min(ARCH, HAS_NFFT, NFFT_MAX : integer) return integer is
    variable result : integer;
  begin
    if HAS_NFFT = 1 then
      case ARCH is
        when 0 =>
          assert false report "ERROR: xfft_v7_0 : deprecated architecture A specified in call to function get_nfft_min" severity error;
        when 1 =>
          result := 6;
        when 2 =>
          result := 3;
        when 3 =>
          result := 3;
        when 4 =>
          result := 3;
        when others =>
          assert false report "ERROR: xfft_v7_0 : unknown architecture specified in call to function get_nfft_min" severity error;
      end case;
    else
      result := NFFT_MAX;
    end if;
    return result;
  end get_nfft_min;
  function max_i(a, b : integer) return integer is
  begin
    if (a > b) then return a;
    else return b;
    end if;
  end;
  function when_else(condition : boolean; if_true, if_false : integer) return integer is
  begin
    if condition then
      return if_true;
    else
      return if_false;
    end if;
  end when_else;
  function mult_latency_bc(C_XDEVICEFAMILY : string; CMPY_TYPE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer) return integer is
    variable latency : integer;
  begin
    latency := cmpy_latency(C_XDEVICEFAMILY, CMPY_TYPE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR);
    if CMPY_TYPE /= 0 and (supports_dsp48(C_XDEVICEFAMILY) = 1 or supports_dsp48e(C_XDEVICEFAMILY) > 0 or supports_dsp48a(C_XDEVICEFAMILY) > 0) then
      latency := latency + 1;
    end if;
    return latency;
  end mult_latency_bc;
  function PE_latency_b(C_XDEVICEFAMILY : string; C_BFLY_TYPE, DFLY_WIDTH, CMULT_DELAY, C_HAS_MULTS, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer is
    constant SCALER_LATENCY  : integer := 1;
    constant ROUNDER_LATENCY : integer := 3;
    variable latency         : integer := 0;
  begin
    latency := radix4_dragonfly_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, DFLY_WIDTH);
    if C_HAS_MULTS = 1 then
      latency := latency + CMULT_DELAY;
    end if;
    if C_HAS_SCALER = 1 then
      latency := latency + SCALER_LATENCY;
    end if;
    if C_HAS_ROUNDER = 1 then
      latency := latency + ROUNDER_LATENCY;
    end if;
    return latency;
  end PE_latency_b;
  function radix4_dragonfly_latency(C_XDEVICEFAMILY : string; C_BFLY_TYPE, DFLY_WIDTH : integer) return integer is
    constant butterfly_1_latency    : integer := get_butterfly_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, DFLY_WIDTH);
    constant butterfly_2_latency    : integer := get_butterfly_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, DFLY_WIDTH+1);
    constant inter_dsp_reg          : integer := C_BFLY_TYPE;
    constant dfly_fabric_output_reg : integer := C_BFLY_TYPE;
  begin
    return butterfly_1_latency + inter_dsp_reg + butterfly_2_latency + dfly_fabric_output_reg;
  end function radix4_dragonfly_latency;
  function r2_pe_latency(C_XDEVICEFAMILY : string; C_BFLY_TYPE, BTRFLY_WIDTH, CMULT_DELAY, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer is
    variable latency                : integer;
    constant BFLY_FABRIC_OUTPUT_REG : integer := C_BFLY_TYPE;
    constant BFLY_LATENCY           : integer := get_butterfly_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, BTRFLY_WIDTH);
  begin
    latency := CMULT_DELAY + BFLY_LATENCY + BFLY_FABRIC_OUTPUT_REG + C_HAS_SCALER + 3*C_HAS_ROUNDER;
    return latency;
  end function r2_pe_latency;
  function should_use_9k_ram_prim (rams_w_9k, rams_18k_only : ram_counts; depth : integer) return boolean is
  begin
      case depth is
        when 2 to 10 => return ((rams_w_9k.main_rams/2)+(rams_w_9k.extra_rams/2) < rams_18k_only.main_rams+rams_18k_only.extra_rams);
        when 11      => return ((2*rams_w_9k.main_rams/2)+(rams_w_9k.extra_rams/2) < rams_18k_only.main_rams+rams_18k_only.extra_rams);
        when 12      => return ((4*rams_w_9k.main_rams/2)+(rams_w_9k.extra_rams/2) < (2*rams_18k_only.main_rams)+rams_18k_only.extra_rams);
        when 13      => return ((8*rams_w_9k.main_rams/2)+(rams_w_9k.extra_rams/2) < (4*rams_18k_only.main_rams)+rams_18k_only.extra_rams);
        when others  => assert false report "ERROR: xfft_v7_0: should_use_9k_ram_prim: caught illegal depth value for 9K implementation " & integer'image(depth) severity error;
      end case;
      return false;
  end function should_use_9k_ram_prim;
  function check_use_9k_bram (C_XDEVICEFAMILY : string; width, depth : integer) return boolean is
    variable rams_9k_18k   : ram_counts;
    variable rams_18k_only : ram_counts;
    variable use_9k        : boolean := false;
  begin
    if supports_RAMB8BWER(C_XDEVICEFAMILY) > 0 then
      if depth <= 13 then
        rams_9k_18k   := calc_rams(C_XDEVICEFAMILY, WIDTH, DEPTH, true);
        rams_18k_only := calc_rams(C_XDEVICEFAMILY, WIDTH, DEPTH, false);
        use_9k        := should_use_9k_ram_prim(rams_9k_18k, rams_18k_only, depth);
      else
        use_9k := false;
      end if;
    else
      use_9k := false;
    end if;
    return use_9k;
  end function check_use_9k_bram;
  function calc_rams (C_XDEVICEFAMILY : string; width, depth : integer; use_9k_bram : boolean) return ram_counts is
    variable num_rams : ram_counts;
  begin
    if use_9k_bram then
      case depth is
        when 2 to 8 =>
          num_rams.main_rams  := (width/36) + boolean'pos(width mod 36 /= 0);
          num_rams.extra_rams := 0;
        when 9 =>
          num_rams.main_rams  := (width/18) + boolean'pos(width mod 18 /= 0);
          num_rams.extra_rams := 0;
        when 10 =>
          num_rams.main_rams  := (width/9) + boolean'pos(width mod 9 /= 0);
          num_rams.extra_rams := 0;
        when 11 =>
          num_rams.main_rams  := width / 9;
          num_rams.extra_rams := boolean'pos((width-(9*num_rams.main_rams)) > 0)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 4);
        when 12 =>
          num_rams.main_rams  := width / 9;
          num_rams.extra_rams := boolean'pos((width-(9*num_rams.main_rams)) > 0)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 2)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 4)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 6);
        when 13 =>
          num_rams.main_rams  := width / 9;
          num_rams.extra_rams := (width-(9*num_rams.main_rams));
        when others => null;
      end case;
    else
      case depth is
        when 2 to 9 =>
          num_rams.main_rams  := (width/36) + boolean'pos(width mod 36 /= 0);
          num_rams.extra_rams := 0;
        when 10 =>
          num_rams.main_rams  := (width/18) + boolean'pos(width mod 18 /= 0);
          num_rams.extra_rams := 0;
        when 11 =>
          num_rams.main_rams  := (width/9) + boolean'pos(width mod 9 /= 0);
          num_rams.extra_rams := 0;
        when 12 =>
          num_rams.main_rams  := width / 9;
          num_rams.extra_rams := boolean'pos((width-(9*num_rams.main_rams)) > 0)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 4);
        when 13 =>
          num_rams.main_rams  := width / 9;
          num_rams.extra_rams := boolean'pos((width-(9*num_rams.main_rams)) > 0)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 2)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 4)
                                 + boolean'pos((width-(9*num_rams.main_rams)) > 6);
        when 14 | 15 | 16 =>
          num_rams.main_rams  := width / 9;
          num_rams.extra_rams := (width-(9*num_rams.main_rams));
        when others => null;
      end case;
    end if;
    return num_rams;
  end function calc_rams;
  function check_dpm_aspect_ratio (c_xdevicefamily : string; width : integer; depth : integer) return boolean is
    variable fits_in_one_bram : boolean := false;
    constant true_depth       : integer := integer(2**depth);
  begin
    if supports_dsp48e(C_XDEVICEFAMILY) > 0 then
      case true_depth is
        when 0 to 512 =>
          if (width <= 72) then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when 513 to 1024 =>
          if (width <= 36) then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when 1025 to 2048 =>
          if (width <= 18) then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when 2049 to 4096 =>
          if (width <= 9) then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when others =>
          fits_in_one_bram := false;
      end case;
    else
      case true_depth is
        when 0 to 512 =>
          if width <= 36 then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when 513 to 1024 =>
          if width <= 18 then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when 1025 to 2048 =>
          if width <= 9 then
            fits_in_one_bram := true;
          else
            fits_in_one_bram := false;
          end if;
        when others =>
          fits_in_one_bram := false;
      end case;
    end if;
    return fits_in_one_bram;
  end function check_dpm_aspect_ratio;
  function calc_dist_mem_addr_latency (c_xdevicefamily : string; data_mem_depth : integer) return integer is
    variable addr_decode_latency : integer := 99;
  begin
    if has_lut6(C_XDEVICEFAMILY) then
      case data_mem_depth is
        when 1 to 7  => addr_decode_latency := 0;
        when 8 to 10 => addr_decode_latency := 1;
        when others =>
          report "ERROR: xfft_v7_0: Invalid data_mem_depth caught in calc_dist_mem_addr_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    else
      case data_mem_depth is
        when 1 to 4  => addr_decode_latency := 0;
        when 5 to 10 => addr_decode_latency := 1;
        when others =>
          report "ERROR: xfft_v7_0: Invalid data_mem_depth caught in calc_dist_mem_addr_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    end if;
    return addr_decode_latency;
  end function calc_dist_mem_addr_latency;
  function calc_dist_mem_mux_latency (c_xdevicefamily : string; data_mem_depth : integer) return integer is
    variable output_mux_latency : integer := 99;
  begin
    if has_lut6(C_XDEVICEFAMILY) then
      case data_mem_depth is
        when 1 to 7 => output_mux_latency := 0;
        when 8 to 9 => output_mux_latency := 1;
        when 10     => output_mux_latency := 2;
        when others =>
          report "ERROR: xfft_v7_0: Invalid data_mem_depth caught in calc_dist_mem_mux_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    else
      case data_mem_depth is
        when 1 to 4  => output_mux_latency := 0;
        when 5 to 6  => output_mux_latency := 1;
        when 7 to 8  => output_mux_latency := 2;
        when 9 to 10 => output_mux_latency := 3;
        when others =>
          report "ERROR: xfft_v7_0: Invalid data_mem_depth caught in calc_dist_mem_mux_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    end if;
    return output_mux_latency;
  end function calc_dist_mem_mux_latency;
  function get_min_mem_delay(c_xdevicefamily : string; data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram : integer) return integer is
    variable mem_latency        : integer := 1;
    constant DISTRIBUTED_MEMORY : integer := 0;
    constant BLOCK_MEMORY       : integer := 1;
  begin
    if data_mem_type = DISTRIBUTED_MEMORY then
      mem_latency := 1;
      mem_latency := mem_latency + calc_dist_mem_addr_latency(c_xdevicefamily, data_mem_depth);
      mem_latency := mem_latency + calc_dist_mem_mux_latency(c_xdevicefamily, data_mem_depth);
    elsif data_mem_type = BLOCK_MEMORY then
      if use_hybrid_ram = 0 then
        if supports_dsp48(C_XDEVICEFAMILY) = 1 or supports_dsp48e(C_XDEVICEFAMILY) > 0 or supports_dsp48a(C_XDEVICEFAMILY) > 0 then
          mem_latency := 3;
        else
          mem_latency := 2;
        end if;
      else
        mem_latency := calc_hybrid_ram_delay(c_xdevicefamily, data_mem_depth, optimize_goal);
      end if;
    end if;
    return mem_latency;
  end get_min_mem_delay;
  function get_mem_delay(c_xdevicefamily : string; data_mem_type, data_mem_depth, sin_cos_delay, tw_addr_gen_delay, rw_addr_gen_delay, mux_delay, switch_delay, optimize_goal, use_hybrid_ram : integer) return integer is
    variable base_mem_delay : integer;
    variable result         : integer;
  begin
    base_mem_delay := get_min_mem_delay(c_xdevicefamily, data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram);
    if ((tw_addr_gen_delay + sin_cos_delay) > (rw_addr_gen_delay + mux_delay + base_mem_delay + switch_delay)) then
      result := (tw_addr_gen_delay + sin_cos_delay - rw_addr_gen_delay - mux_delay - switch_delay);
    else
      result := base_mem_delay;
    end if;
    return result;
  end get_mem_delay;
  function calc_hybrid_ram_delay (c_xdevicefamily : string; ram_depth, opt_goal : integer) return integer is
    variable delay              : integer := 0;
    constant DISTRIBUTED_MEMORY : integer := 0;
    constant BLOCK_MEMORY       : integer := 1;
    constant NOT_HYBRID_RAM     : integer := 0;
    constant dram_latency       : integer := get_min_mem_delay(c_xdevicefamily, DISTRIBUTED_MEMORY, ram_depth, opt_goal, NOT_HYBRID_RAM);
    constant bram_latency       : integer := get_min_mem_delay(c_xdevicefamily, BLOCK_MEMORY, ram_depth, opt_goal, NOT_HYBRID_RAM);
  begin
    if opt_goal = 0 then
      if dram_latency < bram_latency then
        delay := dram_latency;
      else
        delay := bram_latency;
      end if;
    else
      if dram_latency < bram_latency then
        delay := bram_latency;
      else
        delay := dram_latency;
      end if;
    end if;
    return delay;
  end function calc_hybrid_ram_delay;
  function calc_hybrid_ram_widths (c_xdevicefamily : string; ram_width, ram_depth : integer) return hybrid_ram_properties is
    type LUTS is array (0 to 16) of integer;
    constant LUT4s : LUTS := (0  => 0,
                              1  => 2,
                              2  => 2,
                              3  => 2,
                              4  => 2,
                              5  => 4,
                              6  => 8,
                              7  => 16,
                              8  => 32,
                              9  => 64,
                              10 => 128,
                              11 => 256,
                              12 => 512,
                              13 => 1024,
                              14 => 2048,
                              15 => 4096,
                              16 => 8192);
    constant LUT6s : LUTS := (0  => 0,
                              1  => 2,
                              2  => 2,
                              3  => 2,
                              4  => 2,
                              5  => 2,
                              6  => 2,
                              7  => 4,
                              8  => 8,
                              9  => 16,
                              10 => 32,
                              11 => 64,
                              12 => 128,
                              13 => 256,
                              14 => 512,
                              15 => 1024,
                              16 => 2048);
    variable ram_widths         : hybrid_ram_properties := (bram_width => 0, dram_width => 0);
    constant LUT4_LUTRAM_THRESH : integer               := 256;
    constant LUT6_LUTRAM_THRESH : integer               := 256;
    variable single_bram_width  : integer               := 0;
    variable bram_width         : integer               := 0;
    variable dram_width         : integer               := 0;
    constant has_LUT4           : boolean               := has_lut4(c_xdevicefamily);
    constant has_LUT6           : boolean               := has_lut6(c_xdevicefamily);
    constant can_use_9k_bram : boolean := check_use_9k_bram(C_XDEVICEFAMILY, ram_width, ram_depth);

  begin
    if ram_depth > 10 then
      ram_widths.bram_width := ram_width;
      ram_widths.dram_width := 0;
    else
      if can_use_9k_bram then
        if ram_depth <= 8 then
          single_bram_width := 36;
        elsif ram_depth = 9 then
          single_bram_width := 18;
        else
          single_bram_width := 9;
        end if;
      else
        if ram_depth <= 9 then
          single_bram_width := 36;
        else
          single_bram_width := 18;
        end if;
      end if;
      if ram_width <= single_bram_width then
        bram_width := ram_width;
      else
        bram_width := (ram_width / single_bram_width)*single_bram_width;
      end if;
      if ram_width <= single_bram_width then
        dram_width := 0;
      else
        dram_width := ram_width - bram_width;
      end if;
      if (has_LUT4 and (dram_width*LUT4s(ram_depth) > LUT4_LUTRAM_THRESH))
        or (has_LUT6 and (dram_width*LUT6s(ram_depth) > LUT6_LUTRAM_THRESH)) then
        bram_width := bram_width + dram_width;
        dram_width := 0;
      end if;
      ram_widths.bram_width := bram_width;
      ram_widths.dram_width := dram_width;
    end if;
    return ram_widths;
  end function calc_hybrid_ram_widths;
  function r22_pe_width(scaling, nfft_max, input_bits : integer)
    return r22_const_array is
    constant NUMBER_OF_PEs : integer := (nfft_max+1)/2;
    variable index         : integer := 0;
    variable bits          : r22_const_array;
  begin
    if scaling = 1 then
      while (index <= NUMBER_OF_PEs) loop
        bits(index) := input_bits;
        index       := index + 1;
      end loop;
    else
      bits(0) := input_bits;
      bits(1) := input_bits + 3;
      index   := 2;
      while (index < NUMBER_OF_PEs) loop
        bits(index) := bits(index-1) + 2;
        index       := index + 1;
      end loop;
      bits(NUMBER_OF_PEs) := bits(NUMBER_OF_PEs-1) + 1 + boolean'pos(nfft_max/2 = NUMBER_OF_PEs);
    end if;
    return bits;
  end r22_pe_width;
  function r22_bf1_delay(C_XDEVICEFAMILY : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer is
    constant BF_ID           : integer := when_else(has_natural_input = 1, 2*PE_ID+NFFT_MAX_EVEN, 2*PE_ID+NFFT_MAX_EVEN-1+(1-NFFT_MAX_EVEN));
    constant BF1_MEM_LATENCY : integer := 3;
    constant DSP_SPEEDUP_REG : integer := OPT_DSP48s;
    constant bfly_latency_fw : integer := get_butterfly_latency(C_XDEVICEFAMILY, OPT_DSP48s, bfly_width);
    constant bfly_latency_fb : integer := get_butterfly_latency(C_XDEVICEFAMILY, OPT_DSP48s, bfly_width+1);
    variable latency         : integer;
  begin
    if (PE_ID > 2) or ((PE_ID = 2) and (NFFT_MAX_EVEN = 1)) then
      return BF1_MEM_LATENCY+bfly_latency_fb+DSP_SPEEDUP_REG;
    else
      return 3+HAS_NFFT+(bfly_latency_fw-1)+integer(2**(BF_ID));
    end if;
  end r22_bf1_delay;
  function r22_bf2_delay(C_XDEVICEFAMILY : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer is
    constant BF_ID           : integer := when_else(has_natural_input = 1, max_i(2*PE_ID+NFFT_MAX_EVEN-1, 0), max_i(2*PE_ID+NFFT_MAX_EVEN+(1-NFFT_MAX_EVEN), 0));
    constant BF2_MEM_LATENCY : integer := 3;
    constant DSP_SPEEDUP_REG : integer := OPT_DSP48s;
    constant bfly_latency_fw : integer := get_butterfly_latency(C_XDEVICEFAMILY, OPT_DSP48s, bfly_width);
    constant bfly_latency_fb : integer := get_butterfly_latency(C_XDEVICEFAMILY, OPT_DSP48s, bfly_width+1);
    variable latency         : integer;
  begin
    if (PE_ID > 2) then
      return BF2_MEM_LATENCY+bfly_latency_fb+DSP_SPEEDUP_REG;
    else
      return 5+HAS_NFFT+integer(2**(BF_ID))+boolean'pos(bfly_latency_fw > 2);
    end if;
  end r22_bf2_delay;
  function r22_scale_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : integer) return integer is
    constant R22_SCALE_LATENCY : integer := 1;
    constant ROUND_OR_ROUNDREG : boolean := HAS_ROUNDING = 1 and (PE_ID > 0 or HAS_SCALING = 1);
    variable scale_delay       : integer := 0;
  begin
    if HAS_SCALING = 1 or ROUND_OR_ROUNDREG then
      scale_delay := R22_SCALE_LATENCY;
    end if;
    return scale_delay;
  end r22_scale_delay;
  function r22_round_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : integer) return integer is
    constant R22_ROUND_LATENCY : integer := 3;
    constant ROUND_OR_ROUNDREG : boolean := HAS_ROUNDING = 1 and (PE_ID > 0 or HAS_SCALING = 1);
    variable round_delay       : integer := 0;
  begin
    if ROUND_OR_ROUNDREG then
      round_delay := R22_ROUND_LATENCY;
    end if;
    return round_delay;
  end r22_round_delay;
  function r22_mem_type(nfft_max, bram_stage, natural_input : integer)
    return r22_const_array is
    constant NUMBER_OF_PEs : integer := (nfft_max+1)/2;
    variable num_of_stage  : integer := bram_stage;
    variable index         : integer := 0;
    variable mem_type      : r22_const_array;
  begin
    if natural_input = 1 then

      while (index < NUMBER_OF_PEs) loop
        if (num_of_stage > 1) then
          mem_type(index) := 2;
          num_of_stage    := num_of_stage - 2;
        elsif (num_of_stage > 0) then
          mem_type(index) := 1;
          num_of_stage    := num_of_stage - 1;
        else
          mem_type(index) := 0;
        end if;
        index := index + 1;
      end loop;

    else

      while (index < NUMBER_OF_PEs) loop
        if (num_of_stage > 1) then
          mem_type(NUMBER_OF_PEs-1-index) := 2;
          num_of_stage                    := num_of_stage - 2;
        elsif (num_of_stage > 0) then
          mem_type(NUMBER_OF_PEs-1-index) := 1;
          num_of_stage                    := num_of_stage - 1;
        else
          mem_type(NUMBER_OF_PEs-1-index) := 0;
        end if;
        index := index + 1;
      end loop;

    end if;
    return mem_type;
  end r22_mem_type;
  function r22_pe_latency(C_XDEVICEFAMILY          : string; C_BFLY_TYPE, C_CMPY_TYPE, has_nfft, nfft_max, tw_bits, has_scaling, has_rounding, has_mux, has_natural_input : integer;
                          width_of_pe, memory_type : r22_const_array) return r22_const_array is
    constant NUMBER_OF_PEs     : integer := (nfft_max+1)/2;
    variable pe_id             : integer;
    variable pe_index          : integer;
    constant power2            : integer := (nfft_max+2)/2-NUMBER_OF_PEs;
    variable index             : integer := 0;
    variable mult_type         : integer;
    variable mult_delay        : integer;
    variable twiddle_gen_delay : integer;
    variable tw_delay          : integer;
    variable bf1_delay         : integer;
    variable bf2_delay         : integer;
    variable data_tw_sync      : integer;
    variable has_rounder       : boolean;
    variable latency           : r22_const_array;
  begin
    while (index < NUMBER_OF_PEs) loop
      pe_id             := NUMBER_OF_PEs-1-index;
      pe_index          := NUMBER_OF_PEs-1-pe_id;
      twiddle_gen_delay := get_twiddle_latency(c_xdevicefamily, boolean'pos(memory_type(index) = 2), nfft_max-2*index-1, tw_bits);
      tw_delay          := 2 + twiddle_gen_delay;
      if has_natural_input = 1 then
        bf1_delay := r22_bf1_delay(C_XDEVICEFAMILY, C_BFLY_TYPE, pe_id, HAS_NFFT, power2, width_of_pe(index)+0, has_natural_input);
        bf2_delay := r22_bf2_delay(C_XDEVICEFAMILY, C_BFLY_TYPE, pe_id, HAS_NFFT, power2, width_of_pe(index)+1, has_natural_input);
      else
        bf1_delay := r22_bf1_delay(C_XDEVICEFAMILY, C_BFLY_TYPE, pe_index, HAS_NFFT, power2, width_of_pe(index)+0, has_natural_input);
        bf2_delay := r22_bf2_delay(C_XDEVICEFAMILY, C_BFLY_TYPE, pe_index, HAS_NFFT, power2, width_of_pe(index)+1, has_natural_input);
      end if;
      data_tw_sync   := tw_delay + has_nfft - bf1_delay - bf2_delay;
      mult_delay     := cmpy_latency(C_XDEVICEFAMILY, C_CMPY_TYPE, width_of_pe(index)+2, tw_bits, width_of_pe(index)+tw_bits+3, 1, 1, 1, 1, 0);
      latency(index) := bf1_delay;
      if ((power2 = 1) or (index < NUMBER_OF_PEs-1)) then
        latency(index) := latency(index) + bf2_delay;
      end if;
      if (HAS_NFFT = 1) and (index < NUMBER_OF_PEs-2) and (HAS_MUX = 1) then
        latency(index) := latency(index) + 1;
      end if;
      if (index < NUMBER_OF_PEs-1) then
        latency(index) := latency(index) + max_i(data_tw_sync, 0);
        latency(index) := latency(index) + mult_delay;
      end if;
      latency(index) := latency(index) + r22_scale_delay(pe_id, has_scaling, has_rounding);
      latency(index) := latency(index) + r22_round_delay(pe_id, has_scaling, has_rounding);
      index := index + 1;
    end loop;
    return latency;
  end r22_pe_latency;
  function r22_right_shift_latency (shift_distance : integer) return integer is
    variable latency : integer := 0;
  begin
    case shift_distance is
      when 4 to 7   => latency := 1;
      when 8 to 16  => latency := 2;
      when 17 to 18 => latency := 3;
      when others   => report "ERROR: r22_right_shift_latency: shift_distance " & integer'image(shift_distance) & " is out of range" severity error;
    end case;
    return latency;
  end function r22_right_shift_latency;
  function get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return T_TWGEN_ARCH is
  begin
    if MEM_TYPE = DIST_RAM then
      if SINGLE_OUTPUT then
        return TW_DISTMEM_SO;
      else
        return TW_DISTMEM;
      end if;
    else
      if ((THETA_WIDTH                        <= 8) or
          (THETA_WIDTH = 9 and TWIDDLE_WIDTH  <= 18) or
          (THETA_WIDTH = 10 and TWIDDLE_WIDTH <= 9) or
          (THETA_WIDTH = 11 and TWIDDLE_WIDTH <= 4)) then
        return TW_BRAM_HALF_SINCOS;
      else
        return TW_BRAM_QUARTER_SIN;
      end if;
    end if;
  end get_twiddle_arch;
  function get_twiddle_latency(C_XDEVICEFAMILY : string; MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return integer is
    constant ARCH    : T_TWGEN_ARCH := get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH, SINGLE_OUTPUT);
    variable latency : integer;
  begin
    case ARCH is
      when TW_BRAM_HALF_SINCOS =>
        if supports_dsp48(C_XDEVICEFAMILY) = 1 or supports_dsp48e(C_XDEVICEFAMILY) > 0 or supports_dsp48a(C_XDEVICEFAMILY) > 0 then
          latency := 3;
        else
          latency := 2;
        end if;
      when TW_BRAM_QUARTER_SIN =>
        if supports_dsp48(C_XDEVICEFAMILY) = 1 or supports_dsp48e(C_XDEVICEFAMILY) > 0 or supports_dsp48a(C_XDEVICEFAMILY) > 0 then
          latency := 4;
        else
          latency := 3;
        end if;
      when TW_DISTMEM =>
        latency := 2;
      when TW_DISTMEM_SO =>
        latency := 5;
      when others =>
        report "ERROR: Unknown twiddle generator architecture in function get_twiddle_latency" severity failure;
    end case;
    return latency;
  end get_twiddle_latency;
  function get_butterfly_latency (c_xdevicefamily : string; fast_bfly, data_width : integer) return integer is
    variable lat : integer := 0;
  begin
    if fast_bfly = 1 then
      if supports_dsp48(c_xdevicefamily) = 1 then
        if data_width <= 35 then
          lat := 2;
        else
          lat := 3;
        end if;
      elsif supports_dsp48e(C_XDEVICEFAMILY) > 0 or supports_dsp48a(C_XDEVICEFAMILY) > 0 then
        if data_width <= 47 then
          lat := 2;
        else
          lat := 3;
        end if;
      else
        report "ERROR: fast_bfly in get_butterfly_latency not valid for this device"
          severity error;
      end if;
    else
      lat := 1;
    end if;
    return lat;
  end function get_butterfly_latency;
  function cmpy_latency(C_XDEVICEFAMILY : string; CMPY_TYPE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer; SINGLE_OUTPUT : integer := 0) return integer is
    variable latency : integer := cmpy_v3_0_latency(XDEVICEFAMILY => C_XDEVICEFAMILY,
                                                    A_WIDTH       => A_WIDTH,
                                                    B_WIDTH       => B_WIDTH,
                                                    OUT_HIGH      => (A_WIDTH+B_WIDTH),
                                                    OUT_LOW       => (A_WIDTH+B_WIDTH+1-P_WIDTH),
                                                    LATENCY       => -1,
                                                    MULT_TYPE     => boolean'pos(CMPY_TYPE /= 0),
                                                    OPTIMIZE_GOAL => boolean'pos(CMPY_TYPE = 2),
                                                    SINGLE_OUTPUT => SINGLE_OUTPUT,
                                                    HAS_NEGATE    => 1,
                                                    ROUND         => ROUND);
    constant latency_adjust : integer := boolean'pos(CMPY_TYPE = 0 and has_lut6(C_XDEVICEFAMILY) and ROUND /= 0);
  begin
    return latency + latency_adjust;
  end cmpy_latency;
  function allow_hybrid_ram_use (c_xdevicefamily : string; mem_type, output_width, depth : integer) return boolean is
    variable ram_widths     : hybrid_ram_properties := (bram_width => 0, dram_width => 0);
    constant data_ram_width : integer               := output_width;
  begin
    if mem_type = BLOCK_RAM then
      ram_widths := calc_hybrid_ram_widths(c_xdevicefamily, data_ram_width, depth);
      if ram_widths.dram_width /= 0 then
        return true;
      else
        return false;
      end if;
    else
      return false;
    end if;
  end function allow_hybrid_ram_use;
  function r22_allow_reorder_hybrid_ram_use(c_xdevicefamily : string; nfft_max, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal, has_bfp : integer) return boolean is
    constant HAS_REORDER_BUFFER     : integer := 1;
    variable HYBRID_REORDER_LATENCY : integer := 0;
    variable OK_REORDER             : boolean := false;
    constant load_unload_order_same : boolean := (has_natural_input = 0 and has_natural_output = 0) or (has_natural_input = 1 and has_natural_output = 1);
    constant NEED_REORDER_BUFFER    : boolean := load_unload_order_same or (has_bfp = 1);
  begin
    if nfft_max < 11 then
      HYBRID_REORDER_LATENCY := get_min_mem_delay(c_xdevicefamily, reorder_mem_type, nfft_max, optimize_goal, HAS_REORDER_BUFFER);
    else
      HYBRID_REORDER_LATENCY := 0;
    end if;
    if NEED_REORDER_BUFFER and HYBRID_REORDER_LATENCY > 1 then
      OK_REORDER := allow_hybrid_ram_use(c_xdevicefamily, reorder_mem_type, fft_output_width, nfft_max);
    else
      OK_REORDER := false;
    end if;
    return OK_REORDER;
  end function r22_allow_reorder_hybrid_ram_use;
  function r22_allow_hybrid_ram_use (c_xdevicefamily : string; bram_stages, nfft_max, has_scaling, has_bfp, fft_input_width, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal : integer) return boolean is
    constant r22_output_width   : integer         := when_else(has_scaling = 1 and has_bfp = 1, fft_input_width+nfft_max+1, fft_output_width);
    constant r22_scaling        : integer         := when_else(has_scaling = 1 and has_bfp = 1, 0, has_scaling);
    constant R22_MEM_TYPES      : r22_const_array := r22_mem_type(nfft_max, bram_stages, has_natural_input);
    constant R22_MEM_WIDTHS     : r22_const_array := r22_pe_width(has_scaling, nfft_max, fft_input_width);
    constant BLOCK_RAM          : integer         := 1;
    variable OK_BRAM_STAGES     : boolean         := false;
    variable OK_REORDER         : boolean         := false;
    constant NUMBER_OF_PEs      : integer         := (NFFT_MAX+1)/2;
    variable MEM_DEPTH          : integer         := 0;
    constant streaming_mem_type : r22_const_array := r22_mem_type(NFFT_MAX, BRAM_STAGES, has_natural_input);
    variable PE_ID              : integer         := 0;
    variable PE_INDEX           : integer         := 0;
    variable TW_MEM_TYPE        : integer         := 0;
    constant width_of_pe        : r22_const_array := r22_pe_width(r22_scaling, NFFT_MAX, fft_input_width);
    variable bf1_mem_type       : integer         := 0;
    variable bf2_mem_type       : integer         := 0;
    variable real_mem_depth     : integer         := 0;
    variable BF1_ID             : integer         := 0;
    variable BF2_ID             : integer         := 0;
    variable has_bf2            : integer         := 0;
    constant NFFT_MAX_ODD       : integer         := NFFT_MAX - (NFFT_MAX/2)*2;
    constant NFFT_MAX_EVEN      : integer         := 1-NFFT_MAX_ODD;
  begin
    r22_allow_hybrid_ram_use_loop : for i in 0 to NUMBER_OF_PEs-1 loop
      MEM_DEPTH    := NFFT_MAX-1-2*i;
      PE_ID        := (MEM_DEPTH)/2;
      PE_INDEX     := (NFFT_MAX+1)/2-1 - PE_ID;
      BF1_MEM_TYPE := boolean'pos(streaming_mem_type(i) > 0);
      BF2_MEM_TYPE := boolean'pos(streaming_mem_type(i) > 1)*boolean'pos(PE_INDEX /= (nfft_max+1)/2-2);
      BF1_ID       := 2*PE_ID+NFFT_MAX_EVEN;
      BF2_ID       := BF1_ID-1;
      HAS_BF2      := 1-boolean'pos(PE_ID = 0)*(NFFT_MAX_ODD);
      if BF1_ID > 4 and BF1_MEM_TYPE = 1 then
        REAL_MEM_DEPTH := mem_depth;
        if allow_hybrid_ram_use(C_XDEVICEFAMILY, BF1_MEM_TYPE, 2*(width_of_pe(i)+1), REAL_MEM_DEPTH) then
          OK_BRAM_STAGES := true;
          exit r22_allow_hybrid_ram_use_loop;
        end if;
      else
      end if;
      if HAS_BF2 = 1 and BF2_MEM_TYPE = 1 then
        if BF2_ID > 4 then
          real_mem_depth := MEM_DEPTH - 1;
          if allow_hybrid_ram_use(C_XDEVICEFAMILY, BF2_MEM_TYPE, 2*(width_of_pe(i)+1), REAL_MEM_DEPTH) then
            OK_BRAM_STAGES := true;
            exit r22_allow_hybrid_ram_use_loop;
          end if;
        else
        end if;
      end if;

    end loop r22_allow_hybrid_ram_use_loop;
    OK_REORDER := r22_allow_reorder_hybrid_ram_use(c_xdevicefamily, nfft_max, r22_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal, has_bfp);
    return OK_BRAM_STAGES or OK_REORDER;
  end function r22_allow_hybrid_ram_use;
  function get_fixed_width(C_ARCH, fp_word_len : integer) return integer is
  begin
    if C_ARCH = 3 then
      return fp_word_len+3;
    elsif C_ARCH = 1 then
      return fp_word_len+5;
    else
      return fp_word_len+4;
    end if;
  end get_fixed_width;
  function get_fp_convert_to_block_fp_delay(family : string; fixed_width : integer) return integer is
    variable fpo_lat : integer := 0;
  begin
    fpo_lat := flt_pt_flt_to_fix_delay(sp_float_width, sp_float_mant_width+1, fixed_width, fixed_width-1);
    return fpo_lat+2;
  end get_fp_convert_to_block_fp_delay;
  function get_fp_convert_to_fp_delay(family : string; data_width, data_frac_width : integer) return integer is
    variable fpo_lat : integer := 0;
  begin
    fpo_lat := flt_pt_fix_to_flt_delay(data_width, 3);
    return fpo_lat+5;
  end get_fp_convert_to_fp_delay;
end timing_model_pkg;
