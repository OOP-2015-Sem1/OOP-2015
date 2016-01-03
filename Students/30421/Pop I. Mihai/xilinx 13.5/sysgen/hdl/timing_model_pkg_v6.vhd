
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
library XilinxCoreLib;
USE XilinxCoreLib.prims_utils_v9_1.all;
USE XilinxCoreLib.pkg_baseblox_v9_1.all;
USE XilinxCoreLib.mult_gen_pkg_v10_1.all;
USE XilinxCoreLib.floating_point_v4_0_consts.all;
USE XilinxCoreLib.floating_point_pkg_v4_0.all;
PACKAGE timing_model_pkg IS
  constant MAX_NFFT_MAX      : integer := 16;
  constant NFFT_MAX_WIDTH    : integer := 5;
  constant NFFT_MAX_M1_WIDTH : integer := 4;
  constant MAX_TWIDDLE_WIDTH : integer := 35;
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
                                 RC_FAST_CMPY,
                                 RC_OPTIMIZE,
                                 RC_USE_HYBRID_RAM,
                                 RC_FAST_SINCOS,
                                 RC_HAS_BYPASS,
                                 RC_ENABLE_RLOCS
                                 );
  type T_RESOLVED_GENERICS is array (T_RESOLVABLE_GENERICS'low to T_RESOLVABLE_GENERICS'high) of integer;
  function resolve_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY : string; C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return boolean;
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
  function so_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY : string; FAST_BFY, HAS_INPUT_REG, BUTTERFLY_WIDTH : integer) return integer;
  function so_scale_latency(HAS_SCALING                       : integer) return integer;
  function so_round_latency(HAS_ROUNDING                      : integer) return integer;
  function so_bfp_scale_gen_latency(HAS_SCALING, HAS_ROUNDING : integer) return integer;
  function so_pe_latency(C_FAMILY, C_XDEVICEFAMILY            : string; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, EXPAND_TW_WIDTH, C_FAST_BFY, C_FAST_CMPY, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer;
  function so_data_reuse(NFFT : integer) return integer;
  constant max_num_of_pe : integer := (MAX_NFFT_MAX+1)/2;
  type r22_const_array is array (0 to max_num_of_pe) of integer;
  function get_nfft_min(ARCH, HAS_NFFT, NFFT_MAX : integer) return integer;
  function max_i(a, b                                                                     : integer) return integer;
  function min_i(a, b                                                                     : integer) return integer;
  function when_else(condition                                                            : boolean; if_true, if_false : integer) return integer;
  function mult_latency_bc(C_FAMILY, C_XDEVICEFAMILY                                      : string; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer) return integer;
  function PE_latency_b(C_FAMILY, C_XDEVICEFAMILY : string; C_FAST_BFY, DFLY_WIDTH, CMULT_DELAY, C_HAS_MULTS, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer;
  function radix4_dragonfly_latency(C_FAMILY, C_XDEVICEFAMILY : string; C_FAST_BFY, DFLY_WIDTH : integer) return integer;
  function r2_pe_latency(C_FAMILY, C_XDEVICEFAMILY : string; C_FAST_BFY, BTRFLY_WIDTH, CMULT_DELAY, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer;
  type ram_counts is record
    main_rams  : integer;
    extra_rams : integer;
  end record ram_counts;
  function check_dpm_aspect_ratio (family       : string; width : integer; depth : integer) return boolean;
  function calc_dist_mem_addr_latency (c_family          : string; data_mem_depth : integer) return integer;
  function calc_dist_mem_mux_latency (c_family           : string; data_mem_depth : integer) return integer;
  function get_min_mem_delay(c_family, c_xdevicefamily   : string; data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram : integer) return integer;
  function get_mem_delay(c_family, c_xdevicefamily       : string; data_mem_type, data_mem_depth, sin_cos_delay, tw_addr_gen_delay, rw_addr_gen_delay, mux_delay, switch_delay, optimize_goal, use_hybrid_ram : integer) return integer;
  function calc_hybrid_ram_delay (family, xdevicefamily  : string; ram_depth, opt_goal : integer) return integer;
  function calc_hybrid_ram_widths (family, xdevicefamily : string; ram_width, ram_depth : integer) return hybrid_ram_properties;
  function r22_mem_type(nfft_max, bram_stage, natural_input         : integer) return r22_const_array;
  function r22_pe_width(scaling, nfft_max, input_bits               : integer) return r22_const_array;
  function r22_bf1_delay(C_FAMILY, C_XDEVICEFAMILY : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer;
  function r22_bf2_delay(C_FAMILY, C_XDEVICEFAMILY : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer;
  function r22_scale_delay(PE_ID, HAS_SCALING, HAS_ROUNDING         : integer) return integer;
  function r22_round_delay(PE_ID, HAS_SCALING, HAS_ROUNDING         : integer) return integer;
  function r22_pe_latency(c_family, C_XDEVICEFAMILY                 : string; C_FAST_BFY, C_FAST_CMPY, c_fast_sincos, has_nfft, nfft_max, tw_bits, has_scaling, has_rounding, has_mux, has_natural_input : integer; width_of_pe, memory_type : r22_const_array) return r22_const_array;
  function r22_right_shift_latency (shift_distance : integer) return integer;
  type T_TWGEN_ARCH is (TW_BRAM_HALF_SINCOS,
                        TW_BRAM_QUARTER_SIN,
                        TW_DISTMEM,
                        TW_DISTMEM_SO
                        );
  function get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return T_TWGEN_ARCH;
  type T_TWGEN_TABLE is array (0 to integer(2**MAX_NFFT_MAX)-1) of std_logic_vector(MAX_TWIDDLE_WIDTH-1 downto 0);
  function get_twiddle_latency(C_FAMILY, C_XDEVICEFAMILY : string; MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return integer;
  function get_butterfly_latency (c_family, c_xdevicefamily : string; fast_bfly, data_width : integer) return integer;
  constant bf_v4simd   : integer := 0;
  constant bf_v4dsp    : integer := 1;
  constant bf_v4hybrid : integer := 2;
  constant bf_s3simd   : integer := 3;
  constant bf_s3dsp    : integer := 4;
  constant bf_s3hybrid : integer := 5;
  constant bf_v5simd   : integer := 6;
  constant bf_v5dsp    : integer := 7;
  constant bf_v5hybrid : integer := 8;
  constant ARCH_cmpy_18x18    : integer := 0;
  constant ARCH_cmpy_35x18    : integer := 1;
  constant ARCH_cmpy_52x18    : integer := 6;
  constant ARCH_cmpy_35x35    : integer := 2;
  constant ARCH_cmpy_52x35    : integer := 7;
  constant ARCH_cmpy_3        : integer := 3;
  constant ARCH_complex_mult3 : integer := 4;
  constant ARCH_complex_mult4 : integer := 5;
  constant cmpy_mult18x18_DSP48s : integer := 1;
  constant cmpy_mult35x18_DSP48s : integer := 2;
  constant cmpy_mult35x35_DSP48s : integer := 4;
  constant cmpy18x18_DSP48s      : integer := 4;
  constant cmpy35x18_DSP48s      : integer := 8;
  constant cmpy52x18_DSP48s      : integer := 12;
  constant cmpy35x35_DSP48s      : integer := 16;
  constant cmpy52x35_DSP48s      : integer := 24;
  constant cmpy_mult_add_18x18 : integer := 0;
  constant cmpy_mult_add_35x18 : integer := 1;
  constant cmpy_mult_add_35x25 : integer := 2;
  constant cmpy_mult_add_35x35 : integer := 3;
  constant cmpy_mult_add_52x52 : integer := 4;
  function mult_gen_mults(A_WIDTH, B_WIDTH        : integer) return integer;
  function cmpy_nov4_3_mults(A_WIDTH, B_WIDTH     : integer) return integer;
  function cmpy_nov4_4_mults(A_WIDTH, B_WIDTH     : integer) return integer;
  function cmpy_mult_add_DSP48s(A_WIDTH, B_WIDTH  : integer) return integer;
  function cmpy_mult_add_DSP48Es(A_WIDTH, B_WIDTH : integer) return integer;
  function cmpy_3_DSP48_DSP48s(A_WIDTH, B_WIDTH   : integer) return integer;
  function cmpy_3_DSP48_DSP48Es(A_WIDTH, B_WIDTH  : integer) return integer;
  function cmpy_arch(C_FAMILY, C_XDEVICEFAMILY    : string; OPTIMIZE, LARGE_WIDTH, SMALL_WIDTH : integer; SINGLE_OUTPUT : integer := 0) return integer;
  function cascade_mult35x35(MODE, A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS : integer) return boolean;
  function cmpy_mult_add_arch(C_XDEVICEFAMILY : string; large_width, small_width, mode : integer) return integer;
  function cmpy_mult_add_latency(C_XDEVICEFAMILY         : string; A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS, MODE, PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function mult_latency(C_FAMILY, C_XDEVICEFAMILY        : string; A_WIDTH, B_WIDTH : integer) return integer;
  function cmpy18x18_latency(PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function cmpy35x18_latency(C_XDEVICEFAMILY             : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function cmpy52x18_latency(C_XDEVICEFAMILY             : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function cmpy35x35_latency(C_XDEVICEFAMILY             : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function cmpy52x35_latency(C_XDEVICEFAMILY             : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function cmpy_3_get_adder_delay_1_3 (pipe_in, pipe_mid, a_width, pipe_out        : integer) return integer;
  function cmpy_3_get_adder_delay_2 (pipe_in, pipe_mid, a_width, b_width, pipe_out : integer) return integer;
  function cmpy_3_get_p2_width (a_width, b_width                                   : integer) return integer;
  function cmpy_3_get_round_bits (p2_width, p_width                                : integer) return integer;
  function cmpy_3_get_post_mult2_delay (adder_delay_2                              : integer; C_XDEVICEFAMILY : string; a_width, b_width, round_bits, round, pipe_in, pipe_mid, pipe_out : integer) return integer;
  function cmpy_3_get_mult13_pipe_in (pipe_in, post_mult2_delay, adder_delay_1_3   : integer) return integer;
  function cmpy_3_DSP48_latency(C_XDEVICEFAMILY : string; A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer;
  function cmpy_latency(C_FAMILY, C_XDEVICEFAMILY : string; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer; SINGLE_OUTPUT : integer := 0) return integer;
  constant sp_float_width: integer:=32;
  constant sp_float_exp_width: integer:=8;
  constant sp_float_mant_width: integer:=23;
  type t_sp_float is record
    sign  : std_logic;
    exp   : std_logic_vector(sp_float_exp_width-1 downto 0);
    mant  : std_logic_vector(sp_float_mant_width-1 downto 0);
  end record t_sp_float;
  function get_fixed_width(C_ARCH,fp_word_len:integer) return integer;
  function get_fp_convert_to_block_fp_delay(family:string;fixed_width:integer) return integer;
  function get_fp_convert_to_fp_delay(family:string;data_width,data_frac_width:integer) return integer;
  function allow_hybrid_ram_use (family, xdevicefamily            : string; mem_type, output_width, depth : integer) return boolean;
  function r22_allow_reorder_hybrid_ram_use(family, xdevicefamily : string; nfft_max, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal, has_bfp : integer) return boolean;
  function r22_allow_hybrid_ram_use (family, xdevicefamily        : string; bram_stages, nfft_max, has_scaling, has_bfp, fft_input_width, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal : integer) return boolean;
END timing_model_pkg;
PACKAGE BODY timing_model_pkg IS
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
  function resolve_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY : string; C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return boolean is
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
        if check_dpm_aspect_ratio(C_FAMILY, RAM_OUTPUT_WIDTH, C_NFFT_MAX) then
        else
          if C_NFFT_MAX < 13 then
            RAM_OUTPUT_WIDTH := RAM_OUTPUT_WIDTH/2;
          else
            RAM_OUTPUT_WIDTH := RAM_OUTPUT_WIDTH/8;
          end if;
        end if;
        MEMORY_DEPTH := C_NFFT_MAX;

      end if;
      if (C_ARCH /= 3 and allow_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY, C_DATA_MEM_TYPE, RAM_OUTPUT_WIDTH, MEMORY_DEPTH) = false) or
        (C_ARCH = 3 and (r22_allow_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY, C_BRAM_STAGES, C_NFFT_MAX, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, RAM_OUTPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL) = false)) then
        return false;
      else
        return true;
      end if;
    end if;
  end function resolve_hybrid_ram_use;
  function so_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY : string; FAST_BFY, HAS_INPUT_REG, BUTTERFLY_WIDTH : integer) return integer is
    constant FABRIC_OUTPUT_REGISTER : integer := 1;
    constant basic_bfly_latency : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, FAST_BFY, BUTTERFLY_WIDTH);
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
  function so_pe_latency(C_FAMILY, C_XDEVICEFAMILY : string; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, EXPAND_TW_WIDTH, C_FAST_BFY, C_FAST_CMPY, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer is
    constant POSTMULT_WIDTH         : integer := C_OUTPUT_WIDTH + 6;
    constant BUTTERFLY_WIDTH        : integer := POSTMULT_WIDTH - 2;
    constant TWIDDLE_LATENCY        : integer := get_twiddle_latency(C_FAMILY, C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, EXPAND_TW_WIDTH, true);
    constant INPUT_MUX_LATENCY      : integer := 1;
    constant DATA_MEM_LATENCY       : integer := INPUT_MUX_LATENCY + get_min_mem_delay(C_FAMILY, C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
    constant READ_DATA_HOLD_LATENCY : integer := 1;
    constant COMPLEX_MULT_LATENCY   : integer := cmpy_latency(C_FAMILY, C_XDEVICEFAMILY, 1-C_FAST_CMPY, C_OUTPUT_WIDTH, EXPAND_TW_WIDTH, POSTMULT_WIDTH, 1, 1, 1, 1, 0, 1);
    constant BUTTERFLY_LATENCY      : integer := so_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, 1, BUTTERFLY_WIDTH);
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
          assert false report "ERROR: xfft_v6_0 : deprecated architecture A specified in call to function get_nfft_min" severity error;
        when 1 =>
          result := 6;
        when 2 =>
          result := 3;
        when 3 =>
          result := 3;
        when 4 =>
          result := 3;
        when others =>
          assert false report "ERROR: xfft_v6_0 : unknown architecture specified in call to function get_nfft_min" severity error;
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
  function min_i(a, b : integer) return integer is
  begin
    if (a > b) then return b;
    else return a;
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
  function mult_latency_bc(C_FAMILY, C_XDEVICEFAMILY : string; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer) return integer is
    variable latency : integer;
  begin
    latency := cmpy_latency(C_FAMILY, C_XDEVICEFAMILY, OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR);
    if (derived(c_family, "virtex4") or derived(c_family, "virtex5") or derived(c_xdevicefamily, "spartan3adsp")) then
      latency := latency + 1;
    end if;
    return latency;
  end mult_latency_bc;
  function PE_latency_b(C_FAMILY, C_XDEVICEFAMILY : string; C_FAST_BFY, DFLY_WIDTH, CMULT_DELAY, C_HAS_MULTS, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer is
    constant SCALER_LATENCY  : integer := 1;
    constant ROUNDER_LATENCY : integer := 3;
    variable latency         : integer := 0;
  begin
    latency := radix4_dragonfly_latency(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, DFLY_WIDTH);
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
  function radix4_dragonfly_latency(C_FAMILY, C_XDEVICEFAMILY : string; C_FAST_BFY, DFLY_WIDTH : integer) return integer is
    constant butterfly_1_latency      : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, DFLY_WIDTH);
    constant butterfly_2_latency      : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, DFLY_WIDTH+1);
    constant inter_dsp_reg          : integer := C_FAST_BFY;
    constant dfly_fabric_output_reg : integer := C_FAST_BFY;
  begin
    return butterfly_1_latency + inter_dsp_reg + butterfly_2_latency + dfly_fabric_output_reg;
  end function radix4_dragonfly_latency;
  function r2_pe_latency(C_FAMILY, C_XDEVICEFAMILY : string; C_FAST_BFY, BTRFLY_WIDTH, CMULT_DELAY, C_HAS_SCALER, C_HAS_ROUNDER : integer) return integer is
    variable latency                : integer;
    constant BFLY_FABRIC_OUTPUT_REG : integer := C_FAST_BFY;
    constant BFLY_LATENCY           : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, BTRFLY_WIDTH);
  begin
    latency := CMULT_DELAY + BFLY_LATENCY + BFLY_FABRIC_OUTPUT_REG + C_HAS_SCALER + 3*C_HAS_ROUNDER;
    return latency;
  end function r2_pe_latency;
  function check_dpm_aspect_ratio (family : string; width : integer; depth : integer) return boolean is
    variable fits_in_one_bram : boolean := false;
    constant true_depth       : integer := integer(2**depth);
  begin
    if derived(family, "virtex5") then
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
  function calc_dist_mem_addr_latency (c_family : string; data_mem_depth : integer) return integer is
    variable addr_decode_latency : integer := 99;
  begin
    if (derived(c_family, "virtex5")) then
      case data_mem_depth is
        when 1 to 7  => addr_decode_latency := 0;
        when 8 to 10 => addr_decode_latency := 1;
        when others =>
          report "ERROR: xfft_v6_0: Invalid data_mem_depth caught in calc_dist_mem_addr_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    else
      case data_mem_depth is
        when 1 to 4  => addr_decode_latency := 0;
        when 5 to 10 => addr_decode_latency := 1;
        when others =>
          report "ERROR: xfft_v6_0: Invalid data_mem_depth caught in calc_dist_mem_addr_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    end if;
    return addr_decode_latency;
  end function calc_dist_mem_addr_latency;
  function calc_dist_mem_mux_latency (c_family : string; data_mem_depth : integer) return integer is
    variable output_mux_latency : integer := 99;
  begin
    if (derived(c_family, "virtex5")) then
      case data_mem_depth is
        when 1 to 7 => output_mux_latency := 0;
        when 8 to 9 => output_mux_latency := 1;
        when 10     => output_mux_latency := 2;
        when others =>
          report "ERROR: xfft_v6_0: Invalid data_mem_depth caught in calc_dist_mem_mux_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    else
      case data_mem_depth is
        when 1 to 4  => output_mux_latency := 0;
        when 5 to 6  => output_mux_latency := 1;
        when 7 to 8  => output_mux_latency := 2;
        when 9 to 10 => output_mux_latency := 3;
        when others =>
          report "ERROR: xfft_v6_0: Invalid data_mem_depth caught in calc_dist_mem_mux_latency " & integer'image(data_mem_depth)
            severity failure;
      end case;
    end if;
    return output_mux_latency;
  end function calc_dist_mem_mux_latency;
  function get_min_mem_delay(c_family, c_xdevicefamily : string; data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram : integer) return integer is
    variable mem_latency        : integer := 1;
    constant DISTRIBUTED_MEMORY : integer := 0;
    constant BLOCK_MEMORY       : integer := 1;
  begin
    if data_mem_type = DISTRIBUTED_MEMORY then
      mem_latency := 1;
      mem_latency := mem_latency + calc_dist_mem_addr_latency(c_family, data_mem_depth);
      mem_latency := mem_latency + calc_dist_mem_mux_latency(c_family, data_mem_depth);
    elsif data_mem_type = BLOCK_MEMORY then
      if use_hybrid_ram = 0 then
        if (derived(c_family, "virtex4") or derived(c_family, "virtex5") or derived(c_xdevicefamily, "spartan3adsp")) then
          mem_latency := 3;
        else
          mem_latency := 2;
        end if;
      else
        mem_latency := calc_hybrid_ram_delay(c_family, c_xdevicefamily, data_mem_depth, optimize_goal);
      end if;
    end if;
    return mem_latency;
  end get_min_mem_delay;
  function get_mem_delay(c_family, c_xdevicefamily : string; data_mem_type, data_mem_depth, sin_cos_delay, tw_addr_gen_delay, rw_addr_gen_delay, mux_delay, switch_delay, optimize_goal, use_hybrid_ram : integer) return integer is
    variable base_mem_delay : integer;
    variable result         : integer;
  begin
    base_mem_delay := get_min_mem_delay(c_family, c_xdevicefamily, data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram);
    if ((tw_addr_gen_delay + sin_cos_delay) > (rw_addr_gen_delay + mux_delay + base_mem_delay + switch_delay)) then
      result := (tw_addr_gen_delay + sin_cos_delay - rw_addr_gen_delay - mux_delay - switch_delay);
    else
      result := base_mem_delay;
    end if;
    return result;
  end get_mem_delay;
  function calc_hybrid_ram_delay (family, xdevicefamily : string; ram_depth, opt_goal : integer) return integer is
    variable delay              : integer := 0;
    constant DISTRIBUTED_MEMORY : integer := 0;
    constant BLOCK_MEMORY       : integer := 1;
    constant NOT_HYBRID_RAM     : integer := 0;
    constant dram_latency       : integer := get_min_mem_delay(family, xdevicefamily, DISTRIBUTED_MEMORY, ram_depth, opt_goal, NOT_HYBRID_RAM);
    constant bram_latency       : integer := get_min_mem_delay(family, xdevicefamily, BLOCK_MEMORY, ram_depth, opt_goal, NOT_HYBRID_RAM);
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
  function calc_hybrid_ram_widths (family, xdevicefamily : string; ram_width, ram_depth : integer) return hybrid_ram_properties is
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
    constant has_LUT4           : boolean               := not(derived(family, "virtex5"));
    constant has_LUT6           : boolean               := derived(family, "virtex5");
  begin
    if ram_depth > 10 then
      ram_widths.bram_width := ram_width;
      ram_widths.dram_width := 0;
    else
      if ram_depth <= 9 then
        single_bram_width := 36;
      else
        single_bram_width := 18;
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
  function r22_bf1_delay(C_FAMILY, C_XDEVICEFAMILY : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer is
    constant BF_ID           : integer := when_else(has_natural_input = 1, 2*PE_ID+NFFT_MAX_EVEN, 2*PE_ID+NFFT_MAX_EVEN-1+(1-NFFT_MAX_EVEN));
    constant BF1_MEM_LATENCY : integer := 3;
    constant DSP_SPEEDUP_REG : integer := OPT_DSP48s;
    constant bfly_latency_fw    : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, OPT_DSP48s, bfly_width);
    constant bfly_latency_fb    : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, OPT_DSP48s, bfly_width+1);
    variable latency         : integer;
  begin
    if (PE_ID > 2) or ((PE_ID = 2) and (NFFT_MAX_EVEN = 1)) then
      return BF1_MEM_LATENCY+bfly_latency_fb+DSP_SPEEDUP_REG;
    else
      return 3+HAS_NFFT+(bfly_latency_fw-1)+integer(2**(BF_ID));
    end if;
  end r22_bf1_delay;
  function r22_bf2_delay(C_FAMILY, C_XDEVICEFAMILY : string; OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN, bfly_width, has_natural_input : integer) return integer is
    constant BF_ID           : integer := when_else(has_natural_input = 1, max_i(2*PE_ID+NFFT_MAX_EVEN-1, 0), max_i(2*PE_ID+NFFT_MAX_EVEN+(1-NFFT_MAX_EVEN), 0));
    constant BF2_MEM_LATENCY : integer := 3;
    constant DSP_SPEEDUP_REG : integer := OPT_DSP48s;
    constant bfly_latency_fw    : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, OPT_DSP48s, bfly_width);
    constant bfly_latency_fb    : integer := get_butterfly_latency(C_FAMILY, C_XDEVICEFAMILY, OPT_DSP48s, bfly_width+1);
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
          num_of_stage           := num_of_stage - 2;
        elsif (num_of_stage > 0) then
          mem_type(index) := 1;
          num_of_stage           := num_of_stage - 1;
        else
          mem_type(index) := 0;
        end if;
        index := index + 1;
      end loop;

    else

      while (index < NUMBER_OF_PEs) loop
        if (num_of_stage > 1) then
          mem_type(NUMBER_OF_PEs-1-index) := 2;
          num_of_stage           := num_of_stage - 2;
        elsif (num_of_stage > 0) then
          mem_type(NUMBER_OF_PEs-1-index) := 1;
          num_of_stage           := num_of_stage - 1;
        else
          mem_type(NUMBER_OF_PEs-1-index) := 0;
        end if;
        index := index + 1;
      end loop;

    end if;
    return mem_type;
  end r22_mem_type;
  function r22_pe_latency(c_family, C_XDEVICEFAMILY                                                                                                  : string;
                          C_FAST_BFY, C_FAST_CMPY, c_fast_sincos, has_nfft, nfft_max, tw_bits, has_scaling, has_rounding, has_mux, has_natural_input : integer;
                          width_of_pe, memory_type                                                                                                   : r22_const_array) return r22_const_array is
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
      twiddle_gen_delay := get_twiddle_latency(c_family, c_xdevicefamily, boolean'pos(memory_type(index) = 2), nfft_max-2*index-1, tw_bits);
      tw_delay          := 2 + twiddle_gen_delay;
      if has_natural_input = 1 then
        bf1_delay := r22_bf1_delay(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, pe_id, HAS_NFFT, power2, width_of_pe(index)+0, has_natural_input);
        bf2_delay := r22_bf2_delay(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, pe_id, HAS_NFFT, power2, width_of_pe(index)+1, has_natural_input);
      else
        bf1_delay := r22_bf1_delay(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, pe_index, HAS_NFFT, power2, width_of_pe(index)+0, has_natural_input);
        bf2_delay := r22_bf2_delay(C_FAMILY, C_XDEVICEFAMILY, C_FAST_BFY, pe_index, HAS_NFFT, power2, width_of_pe(index)+1, has_natural_input);
      end if;
      data_tw_sync      := tw_delay + has_nfft - bf1_delay - bf2_delay;
      mult_delay        := cmpy_latency(C_FAMILY, C_XDEVICEFAMILY, 1-C_FAST_CMPY, width_of_pe(index)+2, tw_bits, width_of_pe(index)+tw_bits+3, 1, 1, 1, 1, 0);
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
      when 4 to 7 => latency := 1;
      when 8 to 16 => latency := 2;
      when 17 to 18 => latency := 3;
      when others => report "ERROR: r22_right_shift_latency: shift_distance " & integer'image(shift_distance) & " is out of range" severity error;
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
  function get_twiddle_latency(C_FAMILY, C_XDEVICEFAMILY : string; MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : integer; SINGLE_OUTPUT : boolean := false) return integer is
    constant ARCH    : T_TWGEN_ARCH := get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH, SINGLE_OUTPUT);
    variable latency : integer;
  begin
    case ARCH is
      when TW_BRAM_HALF_SINCOS =>
        if derived(C_FAMILY, "virtex4") or derived(C_FAMILY, "virtex5") or derived(C_XDEVICEFAMILY, "spartan3adsp") then
          latency := 3;
        else
          latency := 2;
        end if;
      when TW_BRAM_QUARTER_SIN =>
        if derived(C_FAMILY, "virtex4") or derived(C_FAMILY, "virtex5") or derived(C_XDEVICEFAMILY, "spartan3adsp") then
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
  function get_butterfly_latency (c_family, c_xdevicefamily : string; fast_bfly, data_width : integer) return integer is
    variable lat : integer := 0;
  begin
    if fast_bfly = 1 then
      if derived(c_family, "virtex4") then
        if data_width <= 35 then
          lat := 2;
        else
          lat := 3;
        end if;
      elsif derived(c_family, "virtex5") or derived(c_xdevicefamily, "spartan3adsp") then
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
  function cascade_mult35x35(MODE, A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS : integer) return boolean is
    variable OK : boolean;
  begin
    OK := (MODE = 0) or
          ((MODE = 1) and (ROUND_BITS < 46)) or
          ((MODE = 2) and (A_WIDTH+B_WIDTH < 49)) or
          ((MODE = 3) and (A_WIDTH+B_WIDTH < 49) and (ROUND_BITS < 46)) or
          ((MODE = 4) and (A_WIDTH+B_WIDTH < 49) and (C_WIDTH < 49)) or
          ((MODE = 5) and (A_WIDTH+B_WIDTH < 49) and (C_WIDTH < 49));
    return OK;
  end cascade_mult35x35;
  function mult_gen_mults(A_WIDTH, B_WIDTH : integer) return integer is
  begin
    return (1+(A_WIDTH-2)/17)*(1+(B_WIDTH-2)/17);
  end mult_gen_mults;
  function cmpy_nov4_3_mults(A_WIDTH, B_WIDTH : integer) return integer is
    constant debug : integer := 2*mult_gen_mults(A_WIDTH+1, B_WIDTH)+mult_gen_mults(A_WIDTH, B_WIDTH+1);
  begin
    return debug;
  end cmpy_nov4_3_mults;
  function cmpy_nov4_4_mults(A_WIDTH, B_WIDTH : integer) return integer is
    constant debug : integer := 4*mult_gen_mults(A_WIDTH, B_WIDTH);
  begin
    return debug;
  end cmpy_nov4_4_mults;
  function cmpy_mult_add_DSP48s(A_WIDTH, B_WIDTH : integer) return integer is
    variable blocks  : integer := 0;
    constant sm : integer := min_i(A_WIDTH, B_WIDTH);
    constant lg  : integer := max_i(A_WIDTH, B_WIDTH);
  begin
    if (lg <= 18 and sm <= 18) then
      blocks := 1;
    elsif (lg <= 35 and sm <= 18 and lg+sm<49) then
      blocks := 2;
    elsif (lg <= 52 and sm <= 18 and lg+sm<49) then
      blocks := 3;
    elsif (lg <= 35 and sm <= 35 and lg+sm<65) then
      blocks := 4;
    elsif (lg <= 52 and sm <= 35) then
      blocks := 6;
    else
      blocks := 9;
    end if;
    return blocks;
  end cmpy_mult_add_DSP48s;
  function cmpy_mult_add_DSP48Es(A_WIDTH, B_WIDTH : integer) return integer is
    variable blocks  : integer := 0;
    constant sm : integer := min_i(A_WIDTH, B_WIDTH);
    constant lg  : integer := max_i(A_WIDTH, B_WIDTH);
  begin
    if (lg <= 25 and sm <= 18) then
      blocks := 1;
    elsif (lg <= 35 and sm <= 25 and lg+sm<49) then
      blocks := 2;
    elsif (lg <= 42 and sm <= 18 and lg+sm<49) then
      blocks := 2;
    elsif (lg <= 42 and sm <= 35 and lg+sm<65) then
      blocks := 4;
    elsif (lg <= 42 and sm <= 42) then
      blocks := 5;
    elsif (lg <= 52 and sm <= 25) then
      blocks := 3;
    elsif (lg <= 59 and sm <= 18) then
      blocks := 3;
    elsif (lg <= 59 and sm <= 35) then
      blocks := 6;
    elsif (lg <= 59 and sm <= 42) then
      blocks := 7;
    else
      blocks := 9;
    end if;
    return blocks;
  end cmpy_mult_add_DSP48Es;
  function cmpy_3_DSP48_DSP48s(A_WIDTH, B_WIDTH : integer) return integer is
    variable blocks : integer := 0;
  begin
    blocks := cmpy_mult_add_DSP48s(B_WIDTH+1, A_WIDTH);
    blocks := blocks+2*cmpy_mult_add_DSP48s(A_WIDTH+1, B_WIDTH);
    return blocks;
  end;
  function cmpy_3_DSP48_DSP48Es(A_WIDTH, B_WIDTH : integer) return integer is
    variable blocks : integer := 0;
  begin
    blocks := cmpy_mult_add_DSP48Es(B_WIDTH+1, A_WIDTH);
    blocks := blocks+2*cmpy_mult_add_DSP48Es(A_WIDTH+1, B_WIDTH);
    return blocks;
  end;
  function cmpy_arch(C_FAMILY, C_XDEVICEFAMILY : string; OPTIMIZE, LARGE_WIDTH, SMALL_WIDTH : integer; SINGLE_OUTPUT : integer := 0) return integer is
    variable result        : integer;
    variable mult_3_DSP48s : integer;
  begin
    if derived(C_FAMILY, "virtex4") or derived(C_XDEVICEFAMILY, "spartan3adsp") then
      if (OPTIMIZE = 0) then
        if (LARGE_WIDTH < 19) then result                           := ARCH_cmpy_18x18;
        elsif (LARGE_WIDTH < 36) and (SMALL_WIDTH < 19) then result := ARCH_cmpy_35x18;
        elsif (LARGE_WIDTH < 53) and (SMALL_WIDTH < 19) then result := ARCH_cmpy_52x18;
        elsif (LARGE_WIDTH < 36) then result                        := ARCH_cmpy_35x35;
        elsif (LARGE_WIDTH < 53 and SMALL_WIDTH < 36) then result   := ARCH_cmpy_52x35;
        elsif (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        then result                                                 := ARCH_complex_mult3;
        else result                                                 := ARCH_complex_mult4;
        end if;
      else
        mult_3_DSP48s                                               := cmpy_3_DSP48_DSP48s(LARGE_WIDTH, SMALL_WIDTH);
        if (LARGE_WIDTH < 19) then result                           := when_else((mult_3_DSP48s < cmpy18x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_18x18);
        elsif (SMALL_WIDTH < 19) and (LARGE_WIDTH < 35) then result := when_else((mult_3_DSP48s < cmpy35x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x18);
        elsif (SMALL_WIDTH < 19) and (LARGE_WIDTH = 35) then result := ARCH_cmpy_35x18;
        elsif (LARGE_WIDTH < 35 and (LARGE_WIDTH+SMALL_WIDTH < 65)) then result := when_else((mult_3_DSP48s < cmpy35x35_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x35);
        elsif (LARGE_WIDTH = 35) then result                        := ARCH_cmpy_35x35;
        elsif (LARGE_WIDTH < 53 and SMALL_WIDTH < 36) then result   := ARCH_cmpy_3;
        elsif (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        then result                                                 := ARCH_complex_mult3;
        else result                                                 := ARCH_complex_mult4;
        end if;
      end if;
    elsif derived(C_FAMILY, "virtex5") then
      if (OPTIMIZE = 0) then
        if (LARGE_WIDTH < 26) and (SMALL_WIDTH < 19) then result    := ARCH_cmpy_18x18;
        elsif (LARGE_WIDTH < 36) and (SMALL_WIDTH < 26) then result := ARCH_cmpy_35x18;
        elsif (LARGE_WIDTH < 43) and (SMALL_WIDTH < 19) then result := ARCH_cmpy_35x18;
        elsif (LARGE_WIDTH < 53) and (SMALL_WIDTH < 26) then result := ARCH_cmpy_52x18;
        elsif (LARGE_WIDTH < 43) and (SMALL_WIDTH < 36) then result := ARCH_cmpy_35x35;
        elsif (LARGE_WIDTH < 53) and (SMALL_WIDTH < 36) then result := ARCH_cmpy_52x35;
        elsif (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        then result                                                 := ARCH_complex_mult3;
        else result                                                 := ARCH_complex_mult4;
        end if;
      else
        mult_3_DSP48s                                                           := cmpy_3_DSP48_DSP48Es(LARGE_WIDTH, SMALL_WIDTH);
        if (LARGE_WIDTH < 26) and (SMALL_WIDTH < 19) then result                := when_else((mult_3_DSP48s < cmpy18x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_18x18);
        elsif (LARGE_WIDTH < 42) and (SMALL_WIDTH < 19) then result             := when_else((mult_3_DSP48s < cmpy35x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x18);
        elsif (LARGE_WIDTH = 42) and (SMALL_WIDTH < 19) then result             := ARCH_cmpy_35x18;
        elsif (LARGE_WIDTH < 35 and SMALL_WIDTH < 26) then result               := when_else((mult_3_DSP48s < cmpy35x18_DSP48s and LARGE_WIDTH+SMALL_WIDTH < 49), ARCH_cmpy_3, ARCH_cmpy_35x18);
        elsif (LARGE_WIDTH = 35 and SMALL_WIDTH < 26) then result               := ARCH_cmpy_35x18;
        elsif (LARGE_WIDTH < 42 and (LARGE_WIDTH+SMALL_WIDTH < 65)) then result := when_else((mult_3_DSP48s < cmpy35x35_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x35);
        elsif (LARGE_WIDTH = 42) then result                                    := ARCH_cmpy_35x35;
        elsif LARGE_WIDTH < 53 and SMALL_WIDTH < 36 then result                 := ARCH_cmpy_3;
        elsif (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        then result                                                             := ARCH_complex_mult3;
        else result                                                             := ARCH_complex_mult4;
        end if;
      end if;
    else
      if (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
      then result := ARCH_complex_mult3;
      else result := ARCH_complex_mult4;
      end if;
    end if;

    if SINGLE_OUTPUT = 1 then
      if result = ARCH_cmpy_3 then
        if (LARGE_WIDTH < 19) or (derived(C_FAMILY, "virtex5") and LARGE_WIDTH < 26 and SMALL_WIDTH < 19)    then result                          := ARCH_cmpy_18x18;
        elsif (derived(C_FAMILY, "virtex5") and LARGE_WIDTH < 43 and SMALL_WIDTH < 19)                       then result                          := ARCH_cmpy_35x18;
        elsif ((LARGE_WIDTH < 36) and (SMALL_WIDTH < 19)) or (derived(C_FAMILY, "virtex5") and LARGE_WIDTH < 36 and SMALL_WIDTH < 26) then result := ARCH_cmpy_35x18;
        elsif ((LARGE_WIDTH < 53) and (SMALL_WIDTH < 19)) or (derived(C_FAMILY, "virtex5") and LARGE_WIDTH < 53 and SMALL_WIDTH < 26) then result := ARCH_cmpy_52x18;
        elsif (LARGE_WIDTH < 36) or (derived(C_FAMILY, "virtex5") and LARGE_WIDTH < 43 and SMALL_WIDTH < 36) then result                          := ARCH_cmpy_35x35;
        elsif (LARGE_WIDTH < 53 and SMALL_WIDTH < 36)                                                        then result                          := ARCH_cmpy_52x35;
        end if;
      elsif result = ARCH_complex_mult3 then
        result := ARCH_complex_mult4;
      end if;
    end if;

    return result;
  end cmpy_arch;
  function mult_latency(C_FAMILY, C_XDEVICEFAMILY : string; A_WIDTH, B_WIDTH : integer) return integer is
    constant latency : integer := calc_fully_pipelined_latency(C_XDEVICEFAMILY,
                                                               A_WIDTH,
                                                               0,
                                                               B_WIDTH,
                                                               0,
                                                               1,
                                                               1,
                                                               0,
                                                               "0");
  begin
    return latency;
  end mult_latency;
  function cmpy18x18_latency(PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    constant latency : integer := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 2*min_i(max_i(0, PIPE_OUT), 1);
  begin
    return latency;
  end cmpy18x18_latency;
  function cmpy35x18_latency(C_XDEVICEFAMILY : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    constant DSP48A_EXTRA_DELAY : integer := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    constant latency            : integer := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 4*min_i(max_i(0, PIPE_OUT), 1) + DSP48A_EXTRA_DELAY;
  begin
    return latency;
  end cmpy35x18_latency;
  function cmpy52x18_latency(C_XDEVICEFAMILY : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    constant DSP48A_EXTRA_DELAY : integer := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    constant latency            : integer := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 6*min_i(max_i(0, PIPE_OUT), 1) + 2*DSP48A_EXTRA_DELAY;
  begin
    return latency;
  end cmpy52x18_latency;
  function cmpy35x35_latency(C_XDEVICEFAMILY : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    constant DSP48A_EXTRA_DELAY : integer := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    constant latency            : integer := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 8*min_i(max_i(0, PIPE_OUT), 1) + 2*DSP48A_EXTRA_DELAY;
  begin
    return latency;
  end cmpy35x35_latency;
  function cmpy52x35_latency(C_XDEVICEFAMILY : string; PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    constant DSP48A_EXTRA_DELAY : integer := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    constant latency            : integer := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 12*min_i(max_i(0, PIPE_OUT), 1) + 3*DSP48A_EXTRA_DELAY;
  begin
    return latency;
  end cmpy52x35_latency;
  function cmpy_mult_add_arch(C_XDEVICEFAMILY : string; large_width, small_width, mode : integer) return integer is
    constant is_v4_s3adsp : boolean := derived(C_XDEVICEFAMILY, "virtex4") or derived(C_XDEVICEFAMILY, "spartan3adsp");
    constant is_v5        : boolean := derived(C_XDEVICEFAMILY, "virtex5");
    constant use_small_mult      : boolean := (is_v4_s3adsp and large_width < 19 and small_width < 19) or (is_v5 and large_width < 26 and small_width < 19);
    constant use_med_mult        : boolean := not(use_small_mult) and ((is_v4_s3adsp and large_width > 18 and large_width < 36 and small_width < 19) or (is_v5 and large_width > 25 and large_width < 43 and small_width < 19 and ((MODE = 5 and large_width+small_width < 49) or MODE = 1)));
    constant use_v5_med_mult     : boolean := not(use_small_mult) and not(use_med_mult) and (is_v5 and large_width < 36 and small_width < 26 and ((MODE = 5 and large_width+small_width < 49) or MODE = 1));
    constant use_large_mult      : boolean := not(use_small_mult) and not(use_med_mult) and not(use_v5_med_mult) and ((large_width < 36 and small_width < 36 and large_width+small_width < 65 and is_v4_s3adsp) or (large_width < 43 and small_width < 36 and large_width+small_width < 65 and is_v5));
    constant use_very_large_mult : boolean := not(use_small_mult) and not(use_med_mult) and not(use_v5_med_mult) and not(use_large_mult);
  begin
    if use_small_mult = true then
      return cmpy_mult_add_18x18;
    elsif use_med_mult = true then
      return cmpy_mult_add_35x18;
    elsif use_v5_med_mult = true then
      return cmpy_mult_add_35x25;
    elsif use_large_mult = true then
      return cmpy_mult_add_35x35;
    else
      return cmpy_mult_add_52x52;
    end if;
  end function cmpy_mult_add_arch;
  function cmpy_mult_add_latency(C_XDEVICEFAMILY : string; A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS, MODE, PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    variable latency              : integer := 0;
    constant smaller              : integer := min_i(A_WIDTH, B_WIDTH);
    constant larger               : integer := max_i(A_WIDTH, B_WIDTH);
    variable arch                 : boolean;
    constant is_v4_s3dsp : boolean := not(derived(C_XDEVICEFAMILY, "virtex5"));
    constant is_v5 : boolean := derived(C_XDEVICEFAMILY, "virtex5");
    constant DSP48A_EXTRA_LATENCY : integer := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    constant dsp_add_bits : integer := 46;
    constant mult_add_arch : integer := cmpy_mult_add_arch(C_XDEVICEFAMILY, larger, smaller, MODE);
  begin
    if mult_add_arch = cmpy_mult_add_18x18 then
      latency := max_i(0, PIPE_IN) + min_i(max_i(0, PIPE_MID), 1) + min_i(max_i(0, PIPE_OUT), 1);
      elsif mult_add_arch = cmpy_mult_add_35x18 or mult_add_arch = cmpy_mult_add_35x25 then
      latency := max_i(0, PIPE_IN) + min_i(max_i(0, PIPE_MID), 1) + 2*min_i(max_i(0, PIPE_OUT), 1) + DSP48A_EXTRA_LATENCY;
      elsif mult_add_arch = cmpy_mult_add_35x35 then
      arch    := cascade_mult35x35(MODE, A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS);
      latency := max_i(0, PIPE_IN) + min_i(max_i(0, PIPE_MID), 1) + 4*min_i(max_i(0, PIPE_OUT), 1) + 2*DSP48A_EXTRA_LATENCY;
      if (not arch) then
        latency := latency + 2*min_i(max_i(0, PIPE_OUT), 1);
      end if;
elsif mult_add_arch = cmpy_mult_add_52x52 then
      latency := mult_latency(C_XDEVICEFAMILY, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH) + boolean'pos(C_WIDTH > dsp_add_bits) + boolean'pos(MODE /= 1);
      else
        report "ERROR: cmpy_mult_add_latency: No valid mult_add_arch was found" severity error;
    end if;
    return latency;
  end cmpy_mult_add_latency;
  function cmpy_3_get_adder_delay_1_3 (pipe_in, pipe_mid, a_width, pipe_out : integer) return integer is
  begin
    return pipe_in+pipe_mid*(a_width/(18))+pipe_out;
  end function cmpy_3_get_adder_delay_1_3;
  function cmpy_3_get_adder_delay_2 (pipe_in, pipe_mid, a_width, b_width, pipe_out : integer) return integer is
  begin
    return pipe_in+pipe_mid*((b_width+boolean'pos(pipe_in = 0)*(a_width-b_width))/(18))+pipe_out;
  end function cmpy_3_get_adder_delay_2;
  function cmpy_3_get_p2_width (a_width, b_width : integer) return integer is
  begin
    return a_width+b_width+1;
  end function cmpy_3_get_p2_width;
  function cmpy_3_get_round_bits (p2_width, p_width : integer) return integer is
  begin
    return p2_width-p_width-1;
  end function cmpy_3_get_round_bits;
  function cmpy_3_get_post_mult2_delay (adder_delay_2 : integer; C_XDEVICEFAMILY : string; a_width, b_width, round_bits, round, pipe_in, pipe_mid, pipe_out : integer) return integer is
  begin
    return adder_delay_2 + cmpy_mult_add_latency(c_xdevicefamily, a_width, b_width+1, 0, round_bits, round, pipe_in, pipe_mid, pipe_out);
  end function cmpy_3_get_post_mult2_delay;
  function cmpy_3_get_mult13_pipe_in (pipe_in, post_mult2_delay, adder_delay_1_3 : integer) return integer is
  begin
    return max_i(PIPE_IN, POST_MULT2_DELAY - ADDER_DELAY_1_3);
  end function cmpy_3_get_mult13_pipe_in;
  function cmpy_3_DSP48_latency(C_XDEVICEFAMILY : string; A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT : integer) return integer is
    constant NO_PREADD_PIPE_IN    : integer := 0;
    constant ADDER_DELAY_1_3      : integer := cmpy_3_get_adder_delay_1_3(NO_PREADD_PIPE_IN, PIPE_MID, A_WIDTH, PIPE_OUT);
    constant ADDER_DELAY_2        : integer := cmpy_3_get_adder_delay_2(NO_PREADD_PIPE_IN, PIPE_MID, A_WIDTH, B_WIDTH, PIPE_OUT);
    constant P2_WIDTH             : integer := cmpy_3_get_p2_width(A_WIDTH, B_WIDTH);
    constant ROUND_BITS_2         : integer := cmpy_3_get_round_bits(P2_WIDTH, P_WIDTH);
    constant POST_MULT2_DELAY     : integer := cmpy_3_get_post_mult2_delay(ADDER_DELAY_2, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH, ROUND_BITS_2, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT);
    constant MULT_13_PIPE_IN      : integer := cmpy_3_get_mult13_pipe_in(PIPE_IN, POST_MULT2_DELAY, ADDER_DELAY_1_3);
    constant DSP48A_EXTRA_LATENCY : integer := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    constant large_width : integer := max_i(A_WIDTH, B_WIDTH);
    constant small_width : integer := min_i(A_WIDTH, B_WIDTH);
    constant is_v4_s3dsp : boolean := not(derived(C_XDEVICEFAMILY, "virtex5"));
    constant is_v5 : boolean := derived(C_XDEVICEFAMILY, "virtex5");
      variable mult_1_3_latency : integer := 0;
      variable mult_2_latency    : integer := 0;
    variable cmpy_3_DSP48_LATENCY : integer := 0;
  begin
    assert large_width > 8 and large_width < 52 report "ERROR: cmpy_3_dsp48_latency: Min large width is 9 bits and max is 51 bits" severity error;
    assert small_width > 7 and small_width < 36 report "ERROR: cmpy_3_dsp48_latency: Min small width is 8 bits and max is 35 bits" severity error;
    if derived(C_XDEVICEFAMILY, "virtex4") then
      if large_width+1 <= 18 and small_width <= 18 then
        mult_2_latency := 3;
      elsif large_width+1 > 18 and large_width+1 <= 35 and small_width <= 18 then
        mult_2_latency := 4;
      elsif large_width+1 > 35 and large_width+1 <= 52 and small_width <= 18 then
        mult_2_latency := 5 + boolean'pos(large_width+1+small_width > 46);
      elsif large_width+1 > 52 and small_width <= 18 then
        mult_2_latency := 6 + 1;
      elsif large_width+1 > 18 and large_width+1 <= 35 and small_width > 18 and small_width <= 35 and (large_width+1+small_width < 65) then
        mult_2_latency := 6 + 2*boolean'pos(not cascade_mult35x35(MODE => 1, A_WIDTH => large_width+1, B_WIDTH => small_width, C_WIDTH => 0, ROUND_BITS => 1));
      elsif large_width+1 > 18 and large_width+1 <= 35 and small_width > 18 and small_width <= 35 and (large_width+1+small_width >= 65) then
        mult_2_latency := 9;
      elsif large_width+1 > 35 and small_width > 18 and small_width <= 35 then
        mult_2_latency := 8 + 1;
      elsif large_width+1 > 35 and small_width > 35 then
        mult_2_latency := 11;
      else
        null;
      end if;
      if large_width <= 18 and small_width+1 <= 18 then
        mult_1_3_latency := 3;
      elsif large_width > 18 and large_width <= 35 and small_width+1 <= 18 then
        mult_1_3_latency := 4;
      elsif large_width > 35 and large_width <= 52 and small_width+1 <= 18 then
        mult_1_3_latency := 5 + 2;
      elsif large_width > 52 and small_width+1 <= 18 then
        mult_1_3_latency := 6 ;
      elsif large_width > 18 and large_width <= 35 and small_width+1 > 18 and small_width+1 <= 35 then
        mult_1_3_latency := 6 + 2*boolean'pos(not cascade_mult35x35(MODE => 5, A_WIDTH => large_width, B_WIDTH => small_width+1, C_WIDTH => large_width+1+small_width, ROUND_BITS => 0));
        if (large_width = 32 and small_width = 32)
          or (large_width = 33 and small_width = 31)
          or (large_width = 33 and small_width = 32)
          or (large_width = 33 and small_width = 33)
          or (large_width = 34 and small_width = 30)
          or (large_width = 34 and small_width = 31)
          or (large_width = 34 and small_width = 32)
          or (large_width = 34 and small_width = 33)
          or (large_width = 34 and small_width = 34)
        then
          mult_1_3_latency := mult_1_3_latency - 2;
        end if;
      elsif large_width > 35 and small_width+1 > 18 and small_width+1 <= 35 then
        mult_1_3_latency := 8 + 2;
      elsif large_width > 35 and small_width+1 > 35 then
        mult_1_3_latency := 11 + 2;
      else
        null;
      end if;

      cmpy_3_dsp48_latency := adder_delay_1_3 + mult_2_latency + mult_1_3_latency;


    elsif derived(C_XDEVICEFAMILY, "spartan3adsp") then

      if large_width+1 <= 18 and small_width <= 18 then
        mult_2_latency := 3;
      elsif large_width+1 > 18 and large_width+1 <= 35 and small_width <= 18 then
        mult_2_latency := 5;
      elsif large_width+1 > 35 and large_width+1 <= 52 and small_width <= 18 then
        mult_2_latency := 7 + boolean'pos(large_width+1+small_width > 46);
      elsif large_width+1 > 52 and small_width <= 18 then
        mult_2_latency := 6 + 1;
      elsif large_width+1 > 18 and large_width+1 <= 35 and small_width > 18 and small_width <= 35 and (large_width+1+small_width < 65) then
        mult_2_latency := 8 + 2*boolean'pos(not cascade_mult35x35(MODE => 1, A_WIDTH => large_width+1, B_WIDTH => small_width, C_WIDTH => 0, ROUND_BITS => 1));
      elsif large_width+1 > 18 and large_width+1 <= 35 and small_width > 18 and small_width <= 35 and (large_width+1+small_width >= 65) then
        mult_2_latency := 11;
      elsif large_width+1 > 35 and small_width > 18 and small_width <= 35 then
        mult_2_latency := 12 + 2*boolean'pos(small_width = 35);
      elsif large_width+1 > 35 and small_width > 35 then
        mult_2_latency := 13;
      else
        null;
      end if;
      if large_width <= 18 and small_width+1 <= 18 then
        mult_1_3_latency := 3;
      elsif large_width > 18 and large_width <= 35 and small_width+1 <= 18 then
        mult_1_3_latency := 5;
      elsif large_width > 35 and large_width <= 52 and small_width+1 <= 18 then
        mult_1_3_latency := 7 + 2;
      elsif large_width > 52 and small_width+1 <= 18 then
        mult_1_3_latency := 6 ;
      elsif large_width > 18 and large_width <= 35 and small_width+1 > 18 and small_width+1 <= 35 then
        mult_1_3_latency := 8 + 2*boolean'pos(not cascade_mult35x35(MODE => 5, A_WIDTH => large_width, B_WIDTH => small_width+1, C_WIDTH => large_width+1+small_width, ROUND_BITS => 0));
        if (large_width = 32 and small_width = 32)
          or (large_width = 33 and small_width = 31)
          or (large_width = 33 and small_width = 32)
          or (large_width = 33 and small_width = 33)
          or (large_width = 34 and small_width = 30)
          or (large_width = 34 and small_width = 31)
          or (large_width = 34 and small_width = 32)
          or (large_width = 34 and small_width = 33)
          or (large_width = 34 and small_width = 34)
        then
          mult_1_3_latency := mult_1_3_latency - 2;
        end if;
      elsif large_width > 35 and small_width+1 > 18 and small_width+1 <= 35 then
        mult_1_3_latency := 12 + 1;
      elsif large_width > 35 and small_width+1 > 35 then
        mult_1_3_latency := 13 + 2;
      else
        null;
      end if;

      cmpy_3_dsp48_latency := adder_delay_1_3 + mult_2_latency + mult_1_3_latency;

    elsif derived(C_XDEVICEFAMILY, "virtex5") then
      if (large_width = 35 and small_width = 29)
        or (large_width = 34 and small_width = 30)
        or (large_width = 33 and small_width = 31)
        or (large_width = 32 and small_width = 32)
        or (large_width = 39 and small_width = 25)
        or (large_width = 40 and small_width = 25)
        or (large_width = 41 and small_width = 25)
      then
        cmpy_3_DSP48_LATENCY := 17;
      elsif (large_width = 36 and small_width >= 28 and small_width <= 34)
        or (large_width = 37 and (small_width >= 27 and small_width <= 34))
        or (large_width = 38 and (small_width >= 26 and small_width <= 34))
        or (large_width = 39 and (small_width >= 25 and small_width <= 34))
        or (large_width = 40 and (small_width >= 25 and small_width <= 34))
        or (large_width = 41 and (small_width >= 26 and small_width <= 34))
      then
        cmpy_3_DSP48_LATENCY := 18;
      elsif (large_width = 40 and small_width = 24)
        or (large_width = 41 and small_width = 23)
        or (large_width = 41 and small_width = 24)
        or (large_width >= 43 and (small_width = 23 or small_width = 24))
        or (large_width >= 43 and small_width <= 22)
      then
        cmpy_3_DSP48_LATENCY := 16;
      elsif (large_width < 52 and small_width < 36 and (large_width+small_width < 65)) then
        cmpy_3_DSP48_LATENCY := ADDER_DELAY_1_3 + cmpy_mult_add_latency(C_XDEVICEFAMILY, A_WIDTH+1, B_WIDTH, P2_WIDTH, 0, 5, MULT_13_PIPE_IN, PIPE_MID, PIPE_OUT) + 1;
      elsif (large_width < 53 and small_width < 26)
        or (large_width >= 36 and large_width <= 41 and small_width = 35)
      then
        cmpy_3_DSP48_LATENCY := 19;
      elsif large_width < 42 and small_width < 42 then
        cmpy_3_DSP48_LATENCY := 17;
      else
        cmpy_3_DSP48_LATENCY := 22;
      end if;

    end if;

    return cmpy_3_DSP48_LATENCY;
  end cmpy_3_DSP48_latency;
  function cmpy_latency(C_FAMILY, C_XDEVICEFAMILY : string; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : integer; SINGLE_OUTPUT : integer := 0) return integer is
    constant LARGE_WIDTH              : integer := max_i(A_WIDTH, B_WIDTH);
    constant SMALL_WIDTH              : integer := min_i(A_WIDTH, B_WIDTH);
    constant arch                     : integer := cmpy_arch(C_FAMILY, C_XDEVICEFAMILY, OPTIMIZE, LARGE_WIDTH, SMALL_WIDTH, SINGLE_OUTPUT);
    variable latency                  : integer;
    constant SO_CMPY_SYNC_LATENCY     : integer := 2;
    constant SO_INPUT_MUX_REG_LATENCY : integer := 1;
  begin
    case arch is
      when ARCH_cmpy_18x18    => latency := cmpy18x18_latency(PIPE_IN, PIPE_MID, PIPE_OUT);
      when ARCH_cmpy_35x18    => latency := cmpy35x18_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      when ARCH_cmpy_52x18    => latency := cmpy52x18_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      when ARCH_cmpy_35x35    => latency := cmpy35x35_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      when ARCH_cmpy_52x35    => latency := cmpy52x35_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      when ARCH_complex_mult4 => latency := mult_latency(C_FAMILY, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH)+PIPE_OUT+(PIPE_OUT*ROUND)+1;
      when ARCH_complex_mult3 => return max_i(mult_latency(C_FAMILY, C_XDEVICEFAMILY, A_WIDTH+1, B_WIDTH),
                                              mult_latency(C_FAMILY, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH+1))+2*PIPE_OUT+(PIPE_OUT*ROUND)+1;
      when others => return cmpy_3_DSP48_latency(C_XDEVICEFAMILY, LARGE_WIDTH, SMALL_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT);
    end case;
    if SINGLE_OUTPUT = 1 then
      return latency + SO_CMPY_SYNC_LATENCY + SO_INPUT_MUX_REG_LATENCY;
    else
      return latency;
    end if;
  end cmpy_latency;
  function allow_hybrid_ram_use (family, xdevicefamily : string; mem_type, output_width, depth : integer) return boolean is
    variable ram_widths     : hybrid_ram_properties := (bram_width => 0, dram_width => 0);
    constant data_ram_width : integer               := output_width;
  begin
    if mem_type = BLOCK_RAM then
      ram_widths := calc_hybrid_ram_widths(family, xdevicefamily, data_ram_width, depth);
      if ram_widths.dram_width /= 0 then
        return true;
      else
        return false;
      end if;
    else
      return false;
    end if;
  end function allow_hybrid_ram_use;
  function r22_allow_reorder_hybrid_ram_use(family, xdevicefamily : string; nfft_max, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal, has_bfp : integer) return boolean is
    constant HAS_REORDER_BUFFER     : integer := 1;
    variable HYBRID_REORDER_LATENCY : integer := 0;
    variable OK_REORDER             : boolean := false;
    constant load_unload_order_same : boolean := (has_natural_input = 0 and has_natural_output = 0) or (has_natural_input = 1 and has_natural_output = 1);
    constant NEED_REORDER_BUFFER    : boolean := load_unload_order_same or (has_bfp = 1);
  begin
    if nfft_max < 11 then
      HYBRID_REORDER_LATENCY := get_min_mem_delay(family, xdevicefamily, reorder_mem_type, nfft_max, optimize_goal, HAS_REORDER_BUFFER);
    else
      HYBRID_REORDER_LATENCY := 0;
    end if;
    if NEED_REORDER_BUFFER and HYBRID_REORDER_LATENCY > 1 then
      OK_REORDER := allow_hybrid_ram_use(family, xdevicefamily, reorder_mem_type, fft_output_width, nfft_max);
    else
      OK_REORDER := false;
    end if;
    return OK_REORDER;
  end function r22_allow_reorder_hybrid_ram_use;
  function r22_allow_hybrid_ram_use (family, xdevicefamily : string; bram_stages, nfft_max, has_scaling, has_bfp, fft_input_width, fft_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal : integer) return boolean is
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
        if allow_hybrid_ram_use(FAMILY, XDEVICEFAMILY, BF1_MEM_TYPE, 2*(width_of_pe(i)+1), REAL_MEM_DEPTH) then
          OK_BRAM_STAGES := true;
          exit r22_allow_hybrid_ram_use_loop;
        end if;
      else
      end if;
      if HAS_BF2 = 1 and BF2_MEM_TYPE = 1 then
        if BF2_ID > 4 then
          real_mem_depth := MEM_DEPTH - 1;
          if allow_hybrid_ram_use(FAMILY, XDEVICEFAMILY, BF2_MEM_TYPE, 2*(width_of_pe(i)+1), REAL_MEM_DEPTH) then
            OK_BRAM_STAGES := true;
            exit r22_allow_hybrid_ram_use_loop;
          end if;
        else
        end if;
      end if;

    end loop r22_allow_hybrid_ram_use_loop;
    OK_REORDER := r22_allow_reorder_hybrid_ram_use(family, xdevicefamily, nfft_max, r22_output_width, reorder_mem_type, has_natural_input, has_natural_output, optimize_goal, has_bfp);
    return OK_BRAM_STAGES or OK_REORDER;
  end function r22_allow_hybrid_ram_use;
  function get_fixed_width(C_ARCH,fp_word_len:integer) return integer is
  begin
    if C_ARCH=3 then
      return fp_word_len+3;
    elsif C_ARCH=1 then
      return fp_word_len+5;
    else
      return fp_word_len+4;
    end if;
  end;
  function get_fp_convert_to_block_fp_delay(family:string;fixed_width:integer) return integer is
    variable fpo_lat:integer:=0;
  begin
     fpo_lat:=flt_pt_flt_to_fix_delay(sp_float_width,sp_float_mant_width+1,fixed_width,fixed_width-1);
     return fpo_lat+2;
  end;
  function get_fp_convert_to_fp_delay(family:string;data_width,data_frac_width:integer) return integer is
    variable fpo_lat:integer:=0;
  begin
     fpo_lat:=flt_pt_fix_to_flt_delay(data_width,3);
     return fpo_lat+5;
  end;
END timing_model_pkg;
