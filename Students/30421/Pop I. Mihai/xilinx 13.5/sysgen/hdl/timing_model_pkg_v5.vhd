
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
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
USE ieee.std_logic_unsigned.ALL;
USE ieee.std_logic_arith.ALL;
USE ieee.math_real.ALL;
library XilinxCoreLib;
USE XilinxCoreLib.prims_utils_v9_1.ALL;
USE XilinxCoreLib.pkg_baseblox_v9_1.ALL;

LIBRARY XilinxCoreLib;
USE XilinxCoreLib.pkg_mult_gen_v10_0.ALL;


PACKAGE timing_model_pkg IS

  CONSTANT MAX_NFFT_MAX      : INTEGER := 16;
  CONSTANT NFFT_MAX_WIDTH    : INTEGER := 5;
  CONSTANT NFFT_MAX_M1_WIDTH : INTEGER := 4;
  CONSTANT MAX_TWIDDLE_WIDTH : INTEGER := 25;
  CONSTANT DIST_RAM  : INTEGER := 0;
  CONSTANT BLOCK_RAM : INTEGER := 1;
  TYPE T_RESOLVABLE_GENERICS IS (RC_CHANNELS,
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
  TYPE T_RESOLVED_GENERICS IS ARRAY (T_RESOLVABLE_GENERICS'LOW TO T_RESOLVABLE_GENERICS'HIGH) OF INTEGER;
  FUNCTION resolve_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY : STRING; C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : INTEGER) RETURN BOOLEAN;
  TYPE T_N_COUNT IS (N_COUNT_NONE,
                     N_COUNT_XN_INDEX,
                     N_COUNT_XK_INDEX,
                     N_COUNT_XN_AND_XK_BITREVERSE,
                     N_COUNT_ADDR_GEN
                     );
  TYPE hybrid_ram_properties IS RECORD
    bram_width : INTEGER;
    dram_width : INTEGER;
  END RECORD hybrid_ram_properties;
  CONSTANT SO_BFP_RANGER_LATENCY  : INTEGER := 1;
  CONSTANT SO_BFP_MAXHOLD_LATENCY : INTEGER := 2;
  FUNCTION so_butterfly_latency(FAST_BFY, HAS_INPUT_REG       : INTEGER) RETURN INTEGER;
  FUNCTION so_scale_latency(HAS_SCALING                       : INTEGER) RETURN INTEGER;
  FUNCTION so_round_latency(HAS_ROUNDING                      : INTEGER) RETURN INTEGER;
  FUNCTION so_bfp_scale_gen_latency(HAS_SCALING, HAS_ROUNDING : INTEGER) RETURN INTEGER;
  FUNCTION so_pe_latency(C_FAMILY, C_XDEVICEFAMILY            : STRING; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, EXPAND_TW_WIDTH, C_FAST_BFY, C_FAST_CMPY, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : INTEGER) RETURN INTEGER;
  FUNCTION so_data_reuse(NFFT : INTEGER) RETURN INTEGER;
  CONSTANT max_num_of_pe : INTEGER := (MAX_NFFT_MAX+1)/2;
  TYPE     r22_const_array IS ARRAY (0 TO max_num_of_pe) OF INTEGER;
  FUNCTION get_nfft_min(ARCH, HAS_NFFT, NFFT_MAX : INTEGER) RETURN INTEGER;
  FUNCTION max_i(a, b                                                                     : INTEGER) RETURN INTEGER;
  FUNCTION min_i(a, b                                                                     : INTEGER) RETURN INTEGER;
  FUNCTION when_else(condition                                                            : BOOLEAN; if_true, if_false : INTEGER) RETURN INTEGER;
  FUNCTION mult_latency_bc(C_FAMILY, C_XDEVICEFAMILY                                      : STRING; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : INTEGER) RETURN INTEGER;
  FUNCTION PE_latency_b(C_FAST_BFY, cmult_delay, C_HAS_MULTS, C_HAS_SCALER, C_HAS_ROUNDER : INTEGER) RETURN INTEGER;
  FUNCTION radix4_dragonfly_latency(C_FAST_BFY : INTEGER) RETURN INTEGER;
  FUNCTION r2_pe_latency(C_FAST_BFY, CMULT_DELAY, C_HAS_SCALER, C_HAS_ROUNDER             : INTEGER) RETURN INTEGER;
  TYPE ram_counts IS RECORD
    main_rams  : INTEGER;
    extra_rams : INTEGER;
  END RECORD ram_counts;
  FUNCTION check_dpm_aspect_ratio (family : STRING; width : INTEGER; depth : INTEGER) RETURN BOOLEAN;
  FUNCTION calc_dist_mem_addr_latency (c_family  : STRING; data_mem_depth : INTEGER) RETURN INTEGER;
  FUNCTION calc_dist_mem_mux_latency (c_family   : STRING; data_mem_depth : INTEGER) RETURN INTEGER;
  FUNCTION get_min_mem_delay(c_family, c_xdevicefamily            : STRING; data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram : INTEGER) RETURN INTEGER;
  FUNCTION get_mem_delay(c_family, c_xdevicefamily                : STRING; data_mem_type, data_mem_depth, sin_cos_delay, tw_addr_gen_delay, rw_addr_gen_delay, mux_delay, switch_delay, optimize_goal, use_hybrid_ram : INTEGER) RETURN INTEGER;
  FUNCTION calc_hybrid_ram_delay (family, xdevicefamily : STRING; ram_depth, opt_goal : INTEGER) RETURN INTEGER;
  FUNCTION calc_hybrid_ram_widths (family, xdevicefamily : STRING; ram_width, ram_depth : INTEGER) RETURN hybrid_ram_properties;
  FUNCTION r22_mem_type(nfft_max, bram_stage          : INTEGER) RETURN r22_const_array;
  FUNCTION r22_pe_width(scaling, nfft_max, input_bits : INTEGER) RETURN r22_const_array;
  FUNCTION r22_bf1_delay(OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN : INTEGER) RETURN INTEGER;
  FUNCTION r22_bf2_delay(OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN : INTEGER) RETURN INTEGER;
  FUNCTION r22_scale_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : INTEGER) RETURN INTEGER;
  FUNCTION r22_round_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : INTEGER) RETURN INTEGER;
  FUNCTION r22_pe_latency(c_family, C_XDEVICEFAMILY   : STRING; C_FAST_BFY, C_FAST_CMPY, c_fast_sincos, has_nfft, nfft_max, tw_bits, has_scaling, has_rounding, has_mux : INTEGER; width_of_pe, memory_type : r22_const_array) RETURN r22_const_array;
  TYPE T_TWGEN_ARCH IS (TW_BRAM_HALF_SINCOS,
                        TW_BRAM_QUARTER_SIN,
                        TW_DISTMEM,
                        TW_DISTMEM_SO
                        );
  FUNCTION get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : INTEGER; SINGLE_OUTPUT : BOOLEAN := FALSE) RETURN T_TWGEN_ARCH;
  TYPE T_TWGEN_TABLE IS ARRAY (0 TO INTEGER(2**MAX_NFFT_MAX)-1) OF STD_LOGIC_VECTOR(MAX_TWIDDLE_WIDTH-1 DOWNTO 0);
  FUNCTION get_twiddle_latency(C_FAMILY, C_XDEVICEFAMILY : STRING; MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : INTEGER; SINGLE_OUTPUT : BOOLEAN := FALSE) RETURN INTEGER;
  CONSTANT ARCH_cmpy_18x18    : INTEGER := 0;
  CONSTANT ARCH_cmpy_35x18    : INTEGER := 1;
  CONSTANT ARCH_cmpy_52x18    : INTEGER := 6;
  CONSTANT ARCH_cmpy_35x35    : INTEGER := 2;
  CONSTANT ARCH_cmpy_3        : INTEGER := 3;
  CONSTANT ARCH_complex_mult3 : INTEGER := 4;
  CONSTANT ARCH_complex_mult4 : INTEGER := 5;
  CONSTANT cmpy_mult18x18_DSP48s : INTEGER := 1;
  CONSTANT cmpy_mult35x18_DSP48s : INTEGER := 2;
  CONSTANT cmpy_mult35x35_DSP48s : INTEGER := 4;
  CONSTANT cmpy18x18_DSP48s      : INTEGER := 4;
  CONSTANT cmpy35x18_DSP48s      : INTEGER := 8;
  CONSTANT cmpy52x18_DSP48s      : INTEGER := 12;
  CONSTANT cmpy35x35_DSP48s      : INTEGER := 16;
  FUNCTION mult_gen_mults(A_WIDTH, B_WIDTH       : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_nov4_3_mults(A_WIDTH, B_WIDTH    : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_nov4_4_mults(A_WIDTH, B_WIDTH    : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_mult_add_DSP48s(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_mult_add_DSP48Es(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_DSP48_DSP48s(A_WIDTH, B_WIDTH  : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_DSP48_DSP48Es(A_WIDTH, B_WIDTH  : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_arch(C_FAMILY, C_XDEVICEFAMILY : STRING; OPTIMIZE, LARGE_WIDTH, SMALL_WIDTH : INTEGER; SINGLE_OUTPUT : INTEGER := 0) RETURN INTEGER;
  FUNCTION cascade_mult35x35(MODE, A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS : INTEGER) RETURN BOOLEAN;
  FUNCTION cmpy_mult_add_latency(C_XDEVICEFAMILY : STRING; A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS, MODE, PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER;
  FUNCTION mult_latency(C_FAMILY, C_XDEVICEFAMILY                                                         : STRING; A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER;
  FUNCTION cmpy18x18_latency(PIPE_IN, PIPE_MID, PIPE_OUT                                                  : INTEGER) RETURN INTEGER;
  FUNCTION cmpy35x18_latency(C_XDEVICEFAMILY : STRING; PIPE_IN, PIPE_MID, PIPE_OUT                                                  : INTEGER) RETURN INTEGER;
  FUNCTION cmpy52x18_latency(C_XDEVICEFAMILY : STRING; PIPE_IN, PIPE_MID, PIPE_OUT                                                  : INTEGER) RETURN INTEGER;
  FUNCTION cmpy35x35_latency(C_XDEVICEFAMILY : STRING; PIPE_IN, PIPE_MID, PIPE_OUT                                                  : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_get_adder_delay_1_3 (pipe_in, pipe_mid, a_width, pipe_out : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_get_adder_delay_2 (pipe_in, pipe_mid, a_width, b_width, pipe_out : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_get_p2_width (a_width, b_width : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_get_round_bits (p2_width, p_width : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_get_post_mult2_delay (adder_delay_2 : INTEGER; C_XDEVICEFAMILY : STRING; a_width, b_width, round_bits, round, pipe_in, pipe_mid, pipe_out : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_get_mult13_pipe_in (pipe_in, post_mult2_delay, adder_delay_1_3 : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_3_DSP48_latency(C_XDEVICEFAMILY : STRING; A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT             : INTEGER) RETURN INTEGER;
  FUNCTION cmpy_latency(C_FAMILY, C_XDEVICEFAMILY : STRING; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : INTEGER; SINGLE_OUTPUT : INTEGER := 0) RETURN INTEGER;
  FUNCTION allow_hybrid_ram_use (family, xdevicefamily : STRING; mem_type, output_width, depth : INTEGER) RETURN BOOLEAN;
  FUNCTION r22_allow_reorder_hybrid_ram_use(family, xdevicefamily : STRING; nfft_max, fft_output_width, reorder_mem_type, has_natural_output, optimize_goal : INTEGER) RETURN BOOLEAN;
  FUNCTION r22_allow_hybrid_ram_use (family, xdevicefamily : STRING; bram_stages, nfft_max, has_scaling, fft_input_width, fft_output_width, reorder_mem_type, has_natural_output, optimize_goal  : INTEGER) RETURN BOOLEAN;

END timing_model_pkg;


PACKAGE BODY timing_model_pkg IS

  FUNCTION resolve_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY : STRING; C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : INTEGER) RETURN BOOLEAN IS
    VARIABLE MEMORY_DEPTH : INTEGER := 0;
    VARIABLE RAM_OUTPUT_WIDTH : INTEGER := 2*C_OUTPUT_WIDTH;
  BEGIN

    IF C_USE_HYBRID_RAM = 0 THEN
      RETURN FALSE;
    ELSE
      IF C_ARCH = 1 THEN
        MEMORY_DEPTH := C_NFFT_MAX - 2;
      ELSIF C_ARCH = 2 THEN
        MEMORY_DEPTH := C_NFFT_MAX - 1;
      ELSIF C_ARCH = 3 THEN
        MEMORY_DEPTH := C_NFFT_MAX;
      ELSIF C_ARCH = 4 THEN

        IF check_dpm_aspect_ratio(C_FAMILY, RAM_OUTPUT_WIDTH, C_NFFT_MAX) THEN
        ELSE
          IF C_NFFT_MAX < 13 THEN
            RAM_OUTPUT_WIDTH := RAM_OUTPUT_WIDTH/2;
          ELSE
            RAM_OUTPUT_WIDTH := RAM_OUTPUT_WIDTH/8;
          END IF;
        END IF;

        MEMORY_DEPTH := C_NFFT_MAX;

      END IF;

      IF (C_ARCH /= 3 AND allow_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY, C_DATA_MEM_TYPE, RAM_OUTPUT_WIDTH, MEMORY_DEPTH) = FALSE) OR
        (C_ARCH = 3 AND (r22_allow_hybrid_ram_use(C_FAMILY, C_XDEVICEFAMILY, C_BRAM_STAGES, C_NFFT_MAX, C_HAS_SCALING, C_INPUT_WIDTH, RAM_OUTPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL) = FALSE)) THEN
        RETURN FALSE;
      ELSE
        RETURN TRUE;
      END IF;
    END IF;

  END FUNCTION resolve_hybrid_ram_use;

  FUNCTION so_butterfly_latency(FAST_BFY, HAS_INPUT_REG : INTEGER) RETURN INTEGER IS
    VARIABLE result                 : INTEGER;
    CONSTANT FABRIC_OUTPUT_REGISTER : INTEGER := 1;
  BEGIN
    IF FAST_BFY = 1 THEN
      result := 1 + HAS_INPUT_REG + FABRIC_OUTPUT_REGISTER;
    ELSE
      result := 1;
    END IF;
    RETURN result;
  END so_butterfly_latency;

  FUNCTION so_scale_latency(HAS_SCALING : INTEGER) RETURN INTEGER IS
  BEGIN
    IF HAS_SCALING = 1 THEN
      RETURN 1;
    ELSE
      RETURN 0;
    END IF;
  END so_scale_latency;

  FUNCTION so_round_latency(HAS_ROUNDING : INTEGER) RETURN INTEGER IS
  BEGIN
    IF HAS_ROUNDING = 1 THEN
      RETURN 3;
    ELSE
      RETURN 0;
    END IF;
  END so_round_latency;

  FUNCTION so_bfp_scale_gen_latency(HAS_SCALING, HAS_ROUNDING : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN so_scale_latency(HAS_SCALING) + so_round_latency(HAS_ROUNDING) + SO_BFP_RANGER_LATENCY + SO_BFP_MAXHOLD_LATENCY;
  END so_bfp_scale_gen_latency;

  FUNCTION so_pe_latency(C_FAMILY, C_XDEVICEFAMILY : STRING; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, EXPAND_TW_WIDTH, C_FAST_BFY, C_FAST_CMPY, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : INTEGER) RETURN INTEGER IS
    CONSTANT POSTMULT_WIDTH         : INTEGER := C_OUTPUT_WIDTH + 6;
    CONSTANT TWIDDLE_LATENCY        : INTEGER := get_twiddle_latency(C_FAMILY, C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, EXPAND_TW_WIDTH, TRUE);
    CONSTANT INPUT_MUX_LATENCY      : INTEGER := 1;
    CONSTANT DATA_MEM_LATENCY       : INTEGER := INPUT_MUX_LATENCY + get_min_mem_delay(C_FAMILY, C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
    CONSTANT READ_DATA_HOLD_LATENCY : INTEGER := 1;
    CONSTANT COMPLEX_MULT_LATENCY   : INTEGER := cmpy_latency(C_FAMILY, C_XDEVICEFAMILY, 1-C_FAST_CMPY, C_OUTPUT_WIDTH, EXPAND_TW_WIDTH, POSTMULT_WIDTH, 1, 1, 1, 1, 0, 1);
    CONSTANT BUTTERFLY_LATENCY      : INTEGER := so_butterfly_latency(C_FAST_BFY, 1);
    CONSTANT READ_DATA_DELAY        : INTEGER := max_i(TWIDDLE_LATENCY - DATA_MEM_LATENCY - READ_DATA_HOLD_LATENCY, 0);
    CONSTANT SCALER_LATENCY         : INTEGER := so_scale_latency(C_HAS_SCALING);
    CONSTANT ROUNDER_LATENCY        : INTEGER := so_round_latency(C_HAS_ROUNDING);
  BEGIN
    RETURN
      DATA_MEM_LATENCY +
      READ_DATA_DELAY +
      READ_DATA_HOLD_LATENCY +
      COMPLEX_MULT_LATENCY +
      BUTTERFLY_LATENCY +
      SCALER_LATENCY +
      ROUNDER_LATENCY;
  END so_pe_latency;

  FUNCTION so_data_reuse(NFFT : INTEGER) RETURN INTEGER IS
    VARIABLE result : INTEGER;
  BEGIN
    result := (INTEGER(2**(NFFT-1)) - 1);
    RETURN result;
  END so_data_reuse;

  FUNCTION get_nfft_min(ARCH, HAS_NFFT, NFFT_MAX : INTEGER) RETURN INTEGER IS
    VARIABLE result : INTEGER;
  BEGIN
    IF HAS_NFFT = 1 THEN
      CASE ARCH IS
        WHEN 0 =>
          ASSERT FALSE REPORT "xfft_v5_0 : deprecated architecture A specified in call to function get_nfft_min" SEVERITY ERROR;
        WHEN 1 =>
          result := 6;
        WHEN 2 =>
          result := 3;
        WHEN 3 =>
          result := 3;
        WHEN 4 =>
          result := 3;
        WHEN OTHERS =>
          ASSERT FALSE REPORT "xfft_v5_0 : unknown architecture specified in call to function get_nfft_min" SEVERITY ERROR;
      END CASE;
    ELSE
      result := NFFT_MAX;
    END IF;
    RETURN result;
  END get_nfft_min;

  FUNCTION max_i(a, b : INTEGER) RETURN INTEGER IS
  BEGIN
    IF (a > b) THEN RETURN a;
    ELSE RETURN b;
    END IF;
  END;

  FUNCTION min_i(a, b : INTEGER) RETURN INTEGER IS
  BEGIN
    IF (a > b) THEN RETURN b;
    ELSE RETURN a;
    END IF;
  END;

  FUNCTION when_else(condition : BOOLEAN; if_true, if_false : INTEGER) RETURN INTEGER IS
  BEGIN
    IF condition THEN
      RETURN if_true;
    ELSE
      RETURN if_false;
    END IF;
  END when_else;

  FUNCTION mult_latency_bc(C_FAMILY, C_XDEVICEFAMILY : STRING; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : INTEGER) RETURN INTEGER IS
    VARIABLE latency : INTEGER;
  BEGIN
    latency := cmpy_latency(C_FAMILY, C_XDEVICEFAMILY, OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR);
    IF (derived(c_family, "virtex4") OR derived(c_family, "virtex5") OR derived(c_xdevicefamily, "spartan3adsp")) THEN
      latency := latency + 1;
    END IF;
    RETURN latency;
  END mult_latency_bc;

  FUNCTION PE_latency_b(C_FAST_BFY, CMULT_DELAY, C_HAS_MULTS, C_HAS_SCALER, C_HAS_ROUNDER : INTEGER) RETURN INTEGER IS
    CONSTANT SCALER_LATENCY  : INTEGER := 1;
    CONSTANT ROUNDER_LATENCY : INTEGER := 3;
    VARIABLE latency         : INTEGER := 0;
  BEGIN

    latency := radix4_dragonfly_latency(C_FAST_BFY);

    IF C_HAS_MULTS = 1 THEN
      latency := latency + CMULT_DELAY;
    END IF;

    IF C_HAS_SCALER = 1 THEN
      latency := latency + SCALER_LATENCY;
    END IF;

    IF C_HAS_ROUNDER = 1 THEN
      latency := latency + ROUNDER_LATENCY;
    END IF;

    RETURN latency;
  END PE_latency_b;

  FUNCTION radix4_dragonfly_latency(C_FAST_BFY : INTEGER) RETURN INTEGER IS
    CONSTANT DRAGONFLY_LATENCY             : INTEGER := 2;
    CONSTANT DSP48_DRAGONFLY_EXTRA_LATENCY : INTEGER := 3;
    CONSTANT DFLY_FABRIC_OUTPUT_REG        : INTEGER := 1;
  BEGIN
    RETURN DRAGONFLY_LATENCY + C_FAST_BFY*(DSP48_DRAGONFLY_EXTRA_LATENCY + DFLY_FABRIC_OUTPUT_REG);
  END FUNCTION radix4_dragonfly_latency;

  FUNCTION r2_pe_latency(C_FAST_BFY, CMULT_DELAY, C_HAS_SCALER, C_HAS_ROUNDER : INTEGER) RETURN INTEGER IS
    VARIABLE latency                : INTEGER;
    CONSTANT BFLY_FABRIC_OUTPUT_REG : INTEGER := C_FAST_BFY;
  BEGIN
    latency := CMULT_DELAY + 1 + C_FAST_BFY + BFLY_FABRIC_OUTPUT_REG + C_HAS_SCALER + 3*C_HAS_ROUNDER;
    RETURN latency;
  END r2_PE_latency;

  FUNCTION check_dpm_aspect_ratio (family : STRING; width : INTEGER; depth : INTEGER) RETURN BOOLEAN IS
    VARIABLE fits_in_one_bram : BOOLEAN := FALSE;
    CONSTANT true_depth       : INTEGER := INTEGER(2**depth);
  BEGIN
    IF derived(family, "virtex5") THEN
      CASE true_depth IS
        WHEN 0 TO 512 =>
          IF (width <= 72) THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN 513 TO 1024 =>
          IF (width <= 36) THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN 1025 TO 2048 =>
          IF (width <= 18) THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN 2049 TO 4096 =>
          IF (width <= 9) THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN OTHERS =>
          fits_in_one_bram := FALSE;
      END CASE;
    ELSE
      CASE true_depth IS
        WHEN 0 TO 512 =>
          IF width <= 36 THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN 513 TO 1024 =>
          IF width <= 18 THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN 1025 TO 2048 =>
          IF width <= 9 THEN
            fits_in_one_bram := TRUE;
          ELSE
            fits_in_one_bram := FALSE;
          END IF;
        WHEN OTHERS =>
          fits_in_one_bram := FALSE;
      END CASE;
    END IF;
    RETURN fits_in_one_bram;
  END FUNCTION check_dpm_aspect_ratio;

  FUNCTION calc_dist_mem_addr_latency (c_family : STRING; data_mem_depth : INTEGER) RETURN INTEGER IS
    VARIABLE addr_decode_latency : INTEGER := 99;
  BEGIN
    IF (derived(c_family, "virtex5")) THEN
      CASE data_mem_depth IS
        WHEN 1 TO 7  => addr_decode_latency := 0;
        WHEN 8 TO 10 => addr_decode_latency := 1;
        WHEN OTHERS  =>
          REPORT "ERROR: xfft_v5_0: Invalid data_mem_depth caught in calc_dist_mem_addr_latency " & INTEGER'IMAGE(data_mem_depth)
            SEVERITY FAILURE;
      END CASE;
    ELSE
      CASE data_mem_depth IS
        WHEN 1 TO 4  => addr_decode_latency := 0;
        WHEN 5 TO 10 => addr_decode_latency := 1;
        WHEN OTHERS  =>
          REPORT "ERROR: xfft_v5_0: Invalid data_mem_depth caught in calc_dist_mem_addr_latency " & INTEGER'IMAGE(data_mem_depth)
            SEVERITY FAILURE;
      END CASE;
    END IF;
    RETURN addr_decode_latency;
  END FUNCTION calc_dist_mem_addr_latency;

  FUNCTION calc_dist_mem_mux_latency (c_family : STRING; data_mem_depth : INTEGER) RETURN INTEGER IS
    VARIABLE output_mux_latency : INTEGER := 99;
  BEGIN
    IF (derived(c_family, "virtex5")) THEN
      CASE data_mem_depth IS
        WHEN 1 TO 7  => output_mux_latency := 0;
        WHEN 8 TO 9  => output_mux_latency := 1;
        WHEN 10      => output_mux_latency := 2;
        WHEN OTHERS  =>
          REPORT "ERROR: xfft_v5_0: Invalid data_mem_depth caught in calc_dist_mem_mux_latency " & INTEGER'IMAGE(data_mem_depth)
            SEVERITY FAILURE;
      END CASE;
    ELSE
      CASE data_mem_depth IS
        WHEN 1 TO 4  => output_mux_latency := 0;
        WHEN 5 TO 6  => output_mux_latency := 1;
        WHEN 7 TO 8  => output_mux_latency := 2;
        WHEN 9 TO 10 => output_mux_latency := 3;
        WHEN OTHERS  =>
          REPORT "ERROR: xfft_v5_0: Invalid data_mem_depth caught in calc_dist_mem_mux_latency " & INTEGER'IMAGE(data_mem_depth)
            SEVERITY FAILURE;
      END CASE;
    END IF;
    RETURN output_mux_latency;
  END FUNCTION calc_dist_mem_mux_latency;

  FUNCTION get_min_mem_delay(c_family, c_xdevicefamily : STRING; data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram : INTEGER) RETURN INTEGER IS
    VARIABLE mem_latency        : INTEGER := 1;
    CONSTANT DISTRIBUTED_MEMORY : INTEGER := 0;
    CONSTANT BLOCK_MEMORY       : INTEGER := 1;
  BEGIN
    IF data_mem_type = DISTRIBUTED_MEMORY THEN

      mem_latency := 1;

      mem_latency := mem_latency + calc_dist_mem_addr_latency(c_family, data_mem_depth);

      mem_latency := mem_latency + calc_dist_mem_mux_latency(c_family, data_mem_depth);

    ELSIF data_mem_type = BLOCK_MEMORY THEN
      IF use_hybrid_ram = 0 THEN
        IF (derived(c_family, "virtex4") OR derived(c_family, "virtex5") OR derived(c_xdevicefamily, "spartan3adsp")) THEN
          mem_latency := 3;
        ELSE
          mem_latency := 2;
        END IF;
      ELSE
        mem_latency := calc_hybrid_ram_delay(c_family, c_xdevicefamily, data_mem_depth, optimize_goal);
      END IF;

    END IF;

    RETURN mem_latency;

  END get_min_mem_delay;

  FUNCTION get_mem_delay(c_family, c_xdevicefamily : STRING; data_mem_type, data_mem_depth, sin_cos_delay, tw_addr_gen_delay, rw_addr_gen_delay, mux_delay, switch_delay, optimize_goal, use_hybrid_ram : INTEGER) RETURN INTEGER IS
    VARIABLE base_mem_delay : INTEGER;
    VARIABLE result         : INTEGER;
  BEGIN

    base_mem_delay := get_min_mem_delay(c_family, c_xdevicefamily, data_mem_type, data_mem_depth, optimize_goal, use_hybrid_ram);

    IF ((tw_addr_gen_delay + sin_cos_delay) > (rw_addr_gen_delay + mux_delay + base_mem_delay + switch_delay)) THEN
      result := (tw_addr_gen_delay + sin_cos_delay - rw_addr_gen_delay - mux_delay - switch_delay);
    ELSE
      result := base_mem_delay;
    END IF;

    RETURN result;

  END get_mem_delay;

  FUNCTION calc_hybrid_ram_delay (family, xdevicefamily : STRING; ram_depth, opt_goal : INTEGER) RETURN INTEGER IS
    VARIABLE delay              : INTEGER := 0;
    CONSTANT DISTRIBUTED_MEMORY : INTEGER := 0;
    CONSTANT BLOCK_MEMORY       : INTEGER := 1;
    CONSTANT NOT_HYBRID_RAM     : INTEGER := 0;
    CONSTANT dram_latency       : INTEGER := get_min_mem_delay(family, xdevicefamily, DISTRIBUTED_MEMORY, ram_depth, opt_goal, NOT_HYBRID_RAM);
    CONSTANT bram_latency       : INTEGER := get_min_mem_delay(family, xdevicefamily, BLOCK_MEMORY, ram_depth, opt_goal, NOT_HYBRID_RAM);
  BEGIN


    IF opt_goal = 0 THEN
      IF dram_latency < bram_latency THEN
        delay := dram_latency;
      ELSE
        delay := bram_latency;
      END IF;
    ELSE
      IF dram_latency < bram_latency THEN
        delay := bram_latency;
      ELSE
        delay := dram_latency;
      END IF;
    END IF;

    RETURN delay;

  END FUNCTION calc_hybrid_ram_delay;

  FUNCTION calc_hybrid_ram_widths (family, xdevicefamily : STRING; ram_width, ram_depth : INTEGER) RETURN hybrid_ram_properties IS

    TYPE LUTS IS ARRAY (0 TO 16) OF INTEGER;
    CONSTANT LUT4s : LUTS := (0  => 0,
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
    CONSTANT LUT6s : LUTS := (0  => 0,
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

    VARIABLE ram_widths         : hybrid_ram_properties := (bram_width => 0, dram_width => 0);
    CONSTANT LUT4_LUTRAM_THRESH : INTEGER := 256;
    CONSTANT LUT6_LUTRAM_THRESH : INTEGER := 256;
    VARIABLE single_bram_width  : INTEGER := 0;
    VARIABLE bram_width         : INTEGER := 0;
    VARIABLE dram_width         : INTEGER := 0;
    CONSTANT has_LUT4 : BOOLEAN := NOT(derived(family, "virtex5"));
    CONSTANT has_LUT6 : BOOLEAN := derived(family, "virtex5");

  BEGIN



    IF ram_depth > 10 THEN

      ram_widths.bram_width := ram_width;
      ram_widths.dram_width := 0;

    ELSE

      IF ram_depth <= 9 THEN
        single_bram_width := 36;
      ELSE
        single_bram_width := 18;
      END IF;

      IF ram_width <= single_bram_width THEN
        bram_width := ram_width;
      ELSE
        bram_width := (ram_width / single_bram_width)*single_bram_width;
      END IF;

      IF ram_width <= single_bram_width THEN
        dram_width := 0;
      ELSE
        dram_width := ram_width - bram_width;
      END IF;

      IF (has_LUT4 AND (dram_width*LUT4s(ram_depth) > LUT4_LUTRAM_THRESH))
        OR (has_LUT6 AND (dram_width*LUT6s(ram_depth) > LUT6_LUTRAM_THRESH)) THEN
        bram_width := bram_width + dram_width;
        dram_width := 0;
      END IF;

      ram_widths.bram_width := bram_width;
      ram_widths.dram_width := dram_width;

    END IF;

    RETURN ram_widths;

  END FUNCTION calc_hybrid_ram_widths;

  FUNCTION r22_pe_width(scaling, nfft_max, input_bits : INTEGER)
    RETURN r22_const_array IS

    CONSTANT NUMBER_OF_PEs : INTEGER := (nfft_max+1)/2;
    VARIABLE index         : INTEGER := 0;
    VARIABLE bits          : r22_const_array;

  BEGIN
    IF scaling = 1 THEN
      WHILE (index <= NUMBER_OF_PEs) LOOP
        bits(index) := input_bits;
        index       := index + 1;
      END LOOP;
    ELSE
      bits(0) := input_bits;
      bits(1) := input_bits + 3;
      index   := 2;
      WHILE (index < NUMBER_OF_PEs) LOOP
        bits(index) := bits(index-1) + 2;
        index       := index + 1;
      END LOOP;
      bits(NUMBER_OF_PEs) := bits(NUMBER_OF_PEs-1) + 1 + boolean'pos(nfft_max/2 = NUMBER_OF_PEs);
    END IF;
    RETURN bits;
  END r22_pe_width;

  FUNCTION r22_bf1_delay(OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN : INTEGER) RETURN INTEGER IS
    CONSTANT BF_ID           : INTEGER := 2*PE_ID+NFFT_MAX_EVEN;
    CONSTANT BF1_MEM_LATENCY : INTEGER := 3;
    CONSTANT DSP_SPEEDUP_REG : INTEGER := OPT_DSP48s;
    VARIABLE latency         : INTEGER;
  BEGIN
    IF (PE_ID > 2) OR ((PE_ID = 2) AND (NFFT_MAX_EVEN = 1)) THEN
      RETURN BF1_MEM_LATENCY+1+OPT_DSP48s+DSP_SPEEDUP_REG;
    ELSE
      RETURN 3+HAS_NFFT+OPT_DSP48s+INTEGER(2**(BF_ID));
    END IF;
  END r22_bf1_delay;

  FUNCTION r22_bf2_delay(OPT_DSP48s, pe_id, HAS_NFFT, NFFT_MAX_EVEN : INTEGER) RETURN INTEGER IS
    VARIABLE BF_ID           : INTEGER := 0;
    CONSTANT BF2_MEM_LATENCY : INTEGER := 3;
    CONSTANT DSP_SPEEDUP_REG : INTEGER := OPT_DSP48s;
    VARIABLE latency         : INTEGER;
  BEGIN
    BF_ID := max_i(2*PE_ID+NFFT_MAX_EVEN-1, 0);
    IF (PE_ID > 2) THEN
      RETURN BF2_MEM_LATENCY+1+OPT_DSP48s+DSP_SPEEDUP_REG;
    ELSE
      RETURN 5+HAS_NFFT+INTEGER(2**(BF_ID));
    END IF;
  END r22_bf2_delay;

  FUNCTION r22_scale_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : INTEGER) RETURN INTEGER IS
    CONSTANT R22_SCALE_LATENCY : INTEGER := 1;
    CONSTANT ROUND_OR_ROUNDREG : BOOLEAN := HAS_ROUNDING = 1 AND (PE_ID > 0 OR HAS_SCALING = 1);
    VARIABLE scale_delay : INTEGER := 0;
  BEGIN
    IF HAS_SCALING = 1 OR ROUND_OR_ROUNDREG THEN
      scale_delay := R22_SCALE_LATENCY;
    END IF;
    RETURN scale_delay;
  END r22_scale_delay;

  FUNCTION r22_round_delay(PE_ID, HAS_SCALING, HAS_ROUNDING : INTEGER) RETURN INTEGER IS
    CONSTANT R22_ROUND_LATENCY : INTEGER := 3;
    CONSTANT ROUND_OR_ROUNDREG : BOOLEAN := HAS_ROUNDING = 1 AND (PE_ID > 0 OR HAS_SCALING = 1);
    VARIABLE round_delay : INTEGER := 0;
  BEGIN
    IF ROUND_OR_ROUNDREG THEN
      round_delay := R22_ROUND_LATENCY;
    END IF;
    RETURN round_delay;
  END r22_round_delay;

  FUNCTION r22_mem_type(nfft_max, bram_stage : INTEGER)
    RETURN r22_const_array IS

    CONSTANT NUMBER_OF_PEs : INTEGER := (nfft_max+1)/2;
    VARIABLE num_of_stage  : INTEGER := bram_stage;
    VARIABLE index         : INTEGER := 0;
    VARIABLE mem_type      : r22_const_array;

  BEGIN
    WHILE (index < NUMBER_OF_PEs) LOOP
      IF (num_of_stage > 1) THEN
        mem_type(index) := 2;
        num_of_stage    := num_of_stage - 2;
      ELSIF (num_of_stage > 0) THEN
        mem_type(index) := 1;
        num_of_stage    := num_of_stage - 1;
      ELSE
        mem_type(index) := 0;
      END IF;
      index := index + 1;
    END LOOP;
    RETURN mem_type;
  END r22_mem_type;

  FUNCTION r22_pe_latency(c_family, C_XDEVICEFAMILY                                                                               : STRING;
                          C_FAST_BFY, C_FAST_CMPY, c_fast_sincos, has_nfft, nfft_max, tw_bits, has_scaling, has_rounding, has_mux : INTEGER;
                          width_of_pe, memory_type                                                                                : r22_const_array) RETURN r22_const_array IS

    CONSTANT NUMBER_OF_PEs     : INTEGER := (nfft_max+1)/2;
    VARIABLE pe_id             : INTEGER;
    CONSTANT power2            : INTEGER := (nfft_max+2)/2-NUMBER_OF_PEs;
    VARIABLE index             : INTEGER := 0;
    VARIABLE mult_type         : INTEGER;
    VARIABLE mult_delay        : INTEGER;
    VARIABLE twiddle_gen_delay : INTEGER;
    VARIABLE tw_delay          : INTEGER;
    VARIABLE bf1_delay         : INTEGER;
    VARIABLE bf2_delay         : INTEGER;
    VARIABLE data_tw_sync      : INTEGER;
    VARIABLE has_rounder       : BOOLEAN;
    VARIABLE latency           : r22_const_array;

  BEGIN
    WHILE (index < NUMBER_OF_PEs) LOOP
      pe_id             := NUMBER_OF_PEs-1-index;
      twiddle_gen_delay := get_twiddle_latency(c_family, c_xdevicefamily, boolean'pos(memory_type(index) = 2), nfft_max-2*index-1, tw_bits);
      tw_delay          := 2 + twiddle_gen_delay;
      bf1_delay         := r22_bf1_delay(C_FAST_BFY, pe_id, HAS_NFFT, power2);
      bf2_delay         := r22_bf2_delay(C_FAST_BFY, pe_id, HAS_NFFT, power2);
      data_tw_sync      := tw_delay + has_nfft - bf1_delay - bf2_delay;
      mult_delay        := cmpy_latency(C_FAMILY, C_XDEVICEFAMILY, 1-C_FAST_CMPY, width_of_pe(index)+2, tw_bits, width_of_pe(index)+tw_bits+3, 1, 1, 1, 1, 0);

      latency(index) := bf1_delay;

      IF ((power2 = 1) OR (index < NUMBER_OF_PEs-1)) THEN
        latency(index) := latency(index) + bf2_delay;
      END IF;

      IF (HAS_NFFT = 1) AND (index < NUMBER_OF_PEs-2) AND (HAS_MUX = 1) THEN
        latency(index) := latency(index) + 1;
      END IF;

      IF (index < NUMBER_OF_PEs-1) THEN
        latency(index) := latency(index) + max_i(data_tw_sync, 0);
        latency(index) := latency(index) + mult_delay;
      END IF;

      latency(index) := latency(index) + r22_scale_delay(pe_id, has_scaling, has_rounding);
      latency(index) := latency(index) + r22_round_delay(pe_id, has_scaling, has_rounding);

      index := index + 1;
    END LOOP;

    RETURN latency;
  END r22_pe_latency;

  FUNCTION get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : INTEGER; SINGLE_OUTPUT : BOOLEAN := FALSE) RETURN T_TWGEN_ARCH IS
  BEGIN
    IF MEM_TYPE = DIST_RAM THEN
      IF SINGLE_OUTPUT THEN
        RETURN TW_DISTMEM_SO;
      ELSE
        RETURN TW_DISTMEM;
      END IF;
    ELSE
      IF ((THETA_WIDTH                        <= 8) OR
          (THETA_WIDTH = 9 AND TWIDDLE_WIDTH  <= 18) OR
          (THETA_WIDTH = 10 AND TWIDDLE_WIDTH <= 9) OR
          (THETA_WIDTH = 11 AND TWIDDLE_WIDTH <= 4)) THEN
        RETURN TW_BRAM_HALF_SINCOS;
      ELSE
        RETURN TW_BRAM_QUARTER_SIN;
      END IF;
    END IF;
  END get_twiddle_arch;

  FUNCTION get_twiddle_latency(C_FAMILY, C_XDEVICEFAMILY : STRING; MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH : INTEGER; SINGLE_OUTPUT : BOOLEAN := FALSE) RETURN INTEGER IS
    CONSTANT ARCH    : T_TWGEN_ARCH := get_twiddle_arch(MEM_TYPE, THETA_WIDTH, TWIDDLE_WIDTH, SINGLE_OUTPUT);
    VARIABLE latency : INTEGER;
  BEGIN

    CASE ARCH IS
      WHEN TW_BRAM_HALF_SINCOS =>
        IF derived(C_FAMILY, "virtex4") OR derived(C_FAMILY, "virtex5") OR derived(C_XDEVICEFAMILY, "spartan3adsp") THEN
          latency := 3;
        ELSE
          latency := 2;
        END IF;
      WHEN TW_BRAM_QUARTER_SIN =>
        IF derived(C_FAMILY, "virtex4") OR derived(C_FAMILY, "virtex5") OR derived(C_XDEVICEFAMILY, "spartan3adsp") THEN
          latency := 4;
        ELSE
          latency := 3;
        END IF;
      WHEN TW_DISTMEM =>
        latency := 2;
      WHEN TW_DISTMEM_SO =>
        latency := 5;
      WHEN OTHERS =>
        ASSERT TRUE REPORT "Unknown twiddle generator architecture in function get_twiddle_latency" SEVERITY FAILURE;
    END CASE;
    RETURN latency;
  END get_twiddle_latency;

  FUNCTION cascade_mult35x35(MODE, A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS : INTEGER) RETURN BOOLEAN IS
    VARIABLE OK : BOOLEAN;
  BEGIN
    OK := (MODE = 0) OR
          ((MODE = 1) AND (ROUND_BITS < 46)) OR
          ((MODE = 2) AND (A_WIDTH+B_WIDTH < 49)) OR
          ((MODE = 3) AND (A_WIDTH+B_WIDTH < 49) AND (ROUND_BITS < 46)) OR
          ((MODE = 4) AND (A_WIDTH+B_WIDTH < 49) AND (C_WIDTH < 49)) OR
          ((MODE = 5) AND (A_WIDTH+B_WIDTH < 49) AND (C_WIDTH < 49));
    RETURN OK;
  END cascade_mult35x35;

  FUNCTION mult_gen_mults(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN (1+(A_WIDTH-2)/17)*(1+(B_WIDTH-2)/17);
  END mult_gen_mults;

  FUNCTION cmpy_nov4_3_mults(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    VARIABLE debug : INTEGER := 0;
  BEGIN
    debug := 2*mult_gen_mults(A_WIDTH+1, B_WIDTH)+mult_gen_mults(A_WIDTH, B_WIDTH+1);
    RETURN debug;
  END cmpy_nov4_3_mults;

  FUNCTION cmpy_nov4_4_mults(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    VARIABLE debug : INTEGER := 0;
  BEGIN
    debug := 4*mult_gen_mults(A_WIDTH, B_WIDTH);
    RETURN debug;
  END cmpy_nov4_4_mults;

  FUNCTION cmpy_mult_add_DSP48s(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    VARIABLE blocks  : INTEGER := 0;
    CONSTANT smaller : INTEGER := min_i(A_WIDTH, B_WIDTH);
    CONSTANT larger  : INTEGER := max_i(A_WIDTH, B_WIDTH);
  BEGIN
    IF (larger < 19) THEN
      blocks := cmpy_mult18x18_DSP48s;
    ELSE
      IF (smaller < 19) THEN
        blocks := cmpy_mult35x18_DSP48s;
      ELSE
        blocks := cmpy_mult35x35_DSP48s;
      END IF;
    END IF;
    RETURN blocks;
  END cmpy_mult_add_DSP48s;

  FUNCTION cmpy_mult_add_DSP48Es(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    VARIABLE blocks  : INTEGER := 0;
    CONSTANT smaller : INTEGER := min_i(A_WIDTH, B_WIDTH);
    CONSTANT larger  : INTEGER := max_i(A_WIDTH, B_WIDTH);
  BEGIN
    IF (larger < 26 AND smaller < 19) THEN
      blocks := cmpy_mult18x18_DSP48s;
    ELSE
      IF (larger < 43 AND smaller < 19) OR (larger < 36 AND smaller < 26) THEN
        blocks := cmpy_mult35x18_DSP48s;
      ELSE
        blocks := cmpy_mult35x35_DSP48s;
      END IF;
    END IF;
    RETURN blocks;
  END cmpy_mult_add_DSP48Es;

  FUNCTION cmpy_3_DSP48_DSP48s(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    VARIABLE blocks : INTEGER := 0;
  BEGIN
    blocks := cmpy_mult_add_DSP48s(B_WIDTH+1, A_WIDTH);

    IF (A_WIDTH+B_WIDTH+1 > 48) THEN
      blocks := blocks+8;
    ELSE
      blocks := blocks+2*cmpy_mult_add_DSP48s(A_WIDTH+1, B_WIDTH);
    END IF;

    RETURN blocks;
  END;

  FUNCTION cmpy_3_DSP48_DSP48Es(A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    VARIABLE blocks : INTEGER := 0;
  BEGIN
    blocks := cmpy_mult_add_DSP48Es(B_WIDTH+1, A_WIDTH);

    IF (A_WIDTH+B_WIDTH+1 > 48) THEN
      blocks := blocks+8;
    ELSE
      blocks := blocks+2*cmpy_mult_add_DSP48Es(A_WIDTH+1, B_WIDTH);
    END IF;

    RETURN blocks;
  END;

  FUNCTION cmpy_arch(C_FAMILY, C_XDEVICEFAMILY : STRING; OPTIMIZE, LARGE_WIDTH, SMALL_WIDTH : INTEGER; SINGLE_OUTPUT : INTEGER := 0) RETURN INTEGER IS
    VARIABLE result        : INTEGER;
    VARIABLE mult_3_DSP48s : INTEGER;
  BEGIN

    IF derived(c_family, "virtex4") OR derived(C_XDEVICEFAMILY, "spartan3adsp") THEN
      IF (OPTIMIZE = 0) THEN
        IF (LARGE_WIDTH < 19) THEN result                           := ARCH_cmpy_18x18;
        ELSIF (LARGE_WIDTH < 36) AND (SMALL_WIDTH < 19) THEN result := ARCH_cmpy_35x18;
        ELSIF (LARGE_WIDTH < 53) AND (SMALL_WIDTH < 19) THEN result := ARCH_cmpy_52x18;
        ELSIF (LARGE_WIDTH < 36) THEN result                        := ARCH_cmpy_35x35;
        ELSIF (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        THEN result                                                 := ARCH_complex_mult3;
        ELSE result                                                 := ARCH_complex_mult4;
        END IF;
      ELSE
        mult_3_DSP48s                                               := cmpy_3_DSP48_DSP48s(LARGE_WIDTH, SMALL_WIDTH);
        IF (LARGE_WIDTH < 19) THEN result                           := when_else((mult_3_DSP48s < cmpy18x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_18x18);
        ELSIF (SMALL_WIDTH < 19) AND (LARGE_WIDTH < 35) THEN result := when_else((mult_3_DSP48s < cmpy35x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x18);
        ELSIF (SMALL_WIDTH < 19) AND (LARGE_WIDTH = 35) THEN result := ARCH_cmpy_35x18;
        ELSIF (LARGE_WIDTH < 35) THEN result                        := when_else((mult_3_DSP48s < cmpy35x35_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x35);
        ELSIF (LARGE_WIDTH = 35) THEN result                        := ARCH_cmpy_35x35;
        ELSIF (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        THEN result                                                 := ARCH_complex_mult3;
        ELSE result                                                 := ARCH_complex_mult4;
        END IF;
      END IF;
    ELSIF derived(c_family, "virtex5") THEN
      IF (OPTIMIZE = 0) THEN
        IF (LARGE_WIDTH < 26) AND (SMALL_WIDTH < 19) THEN result    := ARCH_cmpy_18x18;
        ELSIF (LARGE_WIDTH < 36) AND (SMALL_WIDTH < 26) THEN result := ARCH_cmpy_35x18;
        ELSIF (LARGE_WIDTH < 43) AND (SMALL_WIDTH < 19) THEN result := ARCH_cmpy_35x18;
        ELSIF (LARGE_WIDTH < 53) AND (SMALL_WIDTH < 26) THEN result := ARCH_cmpy_52x18;
        ELSIF (LARGE_WIDTH < 43) AND (SMALL_WIDTH < 36) THEN result := ARCH_cmpy_35x35;
        ELSIF (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        THEN result                                                 := ARCH_complex_mult3;
        ELSE result                                                 := ARCH_complex_mult4;
        END IF;
      ELSE
        mult_3_DSP48s                                               := cmpy_3_DSP48_DSP48Es(LARGE_WIDTH, SMALL_WIDTH);
        IF (LARGE_WIDTH < 26) AND (SMALL_WIDTH < 19) THEN result    := when_else((mult_3_DSP48s < cmpy18x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_18x18);
        ELSIF (LARGE_WIDTH < 42) AND (SMALL_WIDTH < 19) THEN result := when_else((mult_3_DSP48s < cmpy35x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x18);
        ELSIF (LARGE_WIDTH = 42) AND (SMALL_WIDTH < 19) THEN result := ARCH_cmpy_35x18;
        ELSIF (LARGE_WIDTH < 35 AND SMALL_WIDTH < 26) THEN result := when_else((mult_3_DSP48s < cmpy35x18_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x18);
        ELSIF (LARGE_WIDTH = 35 AND SMALL_WIDTH < 26) THEN result := ARCH_cmpy_35x18;
        ELSIF (LARGE_WIDTH < 42 AND (LARGE_WIDTH+SMALL_WIDTH < 65)) THEN result := when_else((mult_3_DSP48s < cmpy35x35_DSP48s), ARCH_cmpy_3, ARCH_cmpy_35x35);
        ELSIF (LARGE_WIDTH = 42) THEN result                        := ARCH_cmpy_35x35;
        ELSIF (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
        THEN result                                                 := ARCH_complex_mult3;
        ELSE result                                                 := ARCH_complex_mult4;
        END IF;
      END IF;
    ELSE
      IF (cmpy_nov4_3_mults(LARGE_WIDTH, SMALL_WIDTH) < cmpy_nov4_4_mults(LARGE_WIDTH, SMALL_WIDTH))
      THEN result := ARCH_complex_mult3;
      ELSE result := ARCH_complex_mult4;
      END IF;
    END IF;

    IF SINGLE_OUTPUT = 1 THEN
      IF result = ARCH_cmpy_3 THEN
        IF (LARGE_WIDTH < 19) OR (derived(C_FAMILY, "virtex5") AND LARGE_WIDTH < 26 AND SMALL_WIDTH < 19) THEN result                             := ARCH_cmpy_18x18;
        ELSIF (derived(C_FAMILY, "virtex5") AND LARGE_WIDTH < 43 AND SMALL_WIDTH < 19) THEN result := ARCH_cmpy_35x18;
        ELSIF ((LARGE_WIDTH < 36) AND (SMALL_WIDTH < 19)) OR (derived(C_FAMILY, "virtex5") AND LARGE_WIDTH < 36 AND SMALL_WIDTH < 26) THEN result := ARCH_cmpy_35x18;
        ELSIF ((LARGE_WIDTH < 53) AND (SMALL_WIDTH < 19)) OR (derived(C_FAMILY, "virtex5") AND LARGE_WIDTH < 53 AND SMALL_WIDTH < 26) THEN result := ARCH_cmpy_52x18;
        ELSIF (LARGE_WIDTH < 36) OR (derived(C_FAMILY, "virtex5") AND LARGE_WIDTH < 43 AND SMALL_WIDTH < 36) THEN result                          := ARCH_cmpy_35x35;
        END IF;
      ELSIF result = ARCH_complex_mult3 THEN
        result := ARCH_complex_mult4;
      END IF;
    END IF;

    RETURN result;
  END cmpy_arch;

  FUNCTION mult_latency(C_FAMILY, C_XDEVICEFAMILY : STRING; A_WIDTH, B_WIDTH : INTEGER) RETURN INTEGER IS
    CONSTANT latency : INTEGER := calc_fully_pipelined_latency(C_XDEVICEFAMILY,
                                                               A_WIDTH,
                                                               0,
                                                               B_WIDTH,
                                                               0,
                                                               1,
                                                               1,
                                                               0,
                                                               "");
  BEGIN
    RETURN latency;
  END mult_latency;

  FUNCTION cmpy18x18_latency(PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER IS
    CONSTANT latency : INTEGER := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 2*min_i(max_i(0, PIPE_OUT), 1);
  BEGIN
    RETURN latency;
  END cmpy18x18_latency;

  FUNCTION cmpy35x18_latency(C_XDEVICEFAMILY : STRING; PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER IS
    CONSTANT DSP48A_EXTRA_DELAY : INTEGER := BOOLEAN'POS(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    CONSTANT latency : INTEGER := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 4*min_i(max_i(0, PIPE_OUT), 1) + DSP48A_EXTRA_DELAY;
  BEGIN
    RETURN latency;
  END cmpy35x18_latency;

  FUNCTION cmpy52x18_latency(C_XDEVICEFAMILY : STRING; PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER IS
    CONSTANT DSP48A_EXTRA_DELAY : INTEGER := BOOLEAN'POS(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    CONSTANT latency : INTEGER := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 6*min_i(max_i(0, PIPE_OUT), 1) + 2*DSP48A_EXTRA_DELAY;
  BEGIN
    RETURN latency;
  END cmpy52x18_latency;

  FUNCTION cmpy35x35_latency(C_XDEVICEFAMILY : STRING; PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER IS
    CONSTANT DSP48A_EXTRA_DELAY : INTEGER := BOOLEAN'POS(derived(C_XDEVICEFAMILY, "spartan3adsp"));
    CONSTANT latency : INTEGER := min_i(max_i(0, PIPE_IN), 1) + min_i(max_i(0, PIPE_MID), 1) + 8*min_i(max_i(0, PIPE_OUT), 1) + 2*DSP48A_EXTRA_DELAY;
  BEGIN
    RETURN latency;
  END cmpy35x35_latency;

  FUNCTION cmpy_mult_add_latency(C_XDEVICEFAMILY : STRING; A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS, MODE, PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER IS
    VARIABLE latency : INTEGER := 0;
    CONSTANT smaller : INTEGER := min_i(A_WIDTH, B_WIDTH);
    CONSTANT larger  : INTEGER := max_i(A_WIDTH, B_WIDTH);
    VARIABLE arch    : BOOLEAN;
    CONSTANT DSP48A_EXTRA_LATENCY : INTEGER := boolean'pos(derived(C_XDEVICEFAMILY, "spartan3adsp"));
  BEGIN

    IF (larger < 19) OR (derived(C_XDEVICEFAMILY, "virtex5") AND larger < 26 AND smaller < 19) THEN
      latency := max_i(0, PIPE_IN) + min_i(max_i(0, PIPE_MID), 1) + min_i(max_i(0, PIPE_OUT), 1);
    ELSIF (smaller < 19)  OR (derived(C_XDEVICEFAMILY, "virtex5") AND ((larger < 43 AND smaller < 19) OR (larger < 36 AND smaller < 26))) THEN
      latency := max_i(0, PIPE_IN) + min_i(max_i(0, PIPE_MID), 1) + 2*min_i(max_i(0, PIPE_OUT), 1) + DSP48A_EXTRA_LATENCY;
    ELSE
      arch    := cascade_mult35x35(MODE, A_WIDTH, B_WIDTH, C_WIDTH, ROUND_BITS);
      latency := max_i(0, PIPE_IN) + min_i(max_i(0, PIPE_MID), 1) + 4*min_i(max_i(0, PIPE_OUT), 1) + 2*DSP48A_EXTRA_LATENCY;
      IF (NOT arch) THEN
        latency := latency + 2*min_i(max_i(0, PIPE_OUT), 1);
      END IF;
    END IF;
    RETURN latency;
  END cmpy_mult_add_latency;

  FUNCTION cmpy_3_get_adder_delay_1_3 (pipe_in, pipe_mid, a_width, pipe_out : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN pipe_in+pipe_mid*(a_width/(18))+pipe_out;
  END FUNCTION cmpy_3_get_adder_delay_1_3;

  FUNCTION cmpy_3_get_adder_delay_2 (pipe_in, pipe_mid, a_width, b_width, pipe_out : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN pipe_in+pipe_mid*((b_width+boolean'pos(pipe_in = 0)*(a_width-b_width))/(18))+pipe_out;
  END FUNCTION cmpy_3_get_adder_delay_2;

  FUNCTION cmpy_3_get_p2_width (a_width, b_width : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN a_width+b_width+1;
  END FUNCTION cmpy_3_get_p2_width;

  FUNCTION cmpy_3_get_round_bits (p2_width, p_width : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN p2_width-p_width-1;
  END FUNCTION cmpy_3_get_round_bits;

  FUNCTION cmpy_3_get_post_mult2_delay (adder_delay_2 : INTEGER; C_XDEVICEFAMILY : STRING; a_width, b_width, round_bits, round, pipe_in, pipe_mid, pipe_out : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN adder_delay_2 + cmpy_mult_add_latency(c_xdevicefamily, a_width, b_width+1, 0, round_bits, round, pipe_in, pipe_mid, pipe_out);
  END FUNCTION cmpy_3_get_post_mult2_delay;

  FUNCTION cmpy_3_get_mult13_pipe_in (pipe_in, post_mult2_delay, adder_delay_1_3 : INTEGER) RETURN INTEGER IS
  BEGIN
    RETURN max_i(PIPE_IN, POST_MULT2_DELAY - ADDER_DELAY_1_3);
  END FUNCTION cmpy_3_get_mult13_pipe_in;

  FUNCTION cmpy_3_DSP48_latency(C_XDEVICEFAMILY : STRING; A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT : INTEGER) RETURN INTEGER IS
    CONSTANT NO_PREADD_PIPE_IN    : INTEGER := 0;
    CONSTANT ADDER_DELAY_1_3      : INTEGER := cmpy_3_get_adder_delay_1_3(NO_PREADD_PIPE_IN, PIPE_MID, A_WIDTH, PIPE_OUT);
    CONSTANT ADDER_DELAY_2        : INTEGER := cmpy_3_get_adder_delay_2(NO_PREADD_PIPE_IN, PIPE_MID, A_WIDTH, B_WIDTH, PIPE_OUT);
    VARIABLE P2_WIDTH             : INTEGER := 0;
    CONSTANT ROUND_BITS_2         : INTEGER := cmpy_3_get_round_bits(P2_WIDTH, P_WIDTH);
    CONSTANT POST_MULT2_DELAY     : INTEGER := cmpy_3_get_post_mult2_delay(ADDER_DELAY_2, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH, ROUND_BITS_2, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT);
    CONSTANT MULT_13_PIPE_IN      : INTEGER := cmpy_3_get_mult13_pipe_in(PIPE_IN, POST_MULT2_DELAY, ADDER_DELAY_1_3);
    VARIABLE cmpy_3_DSP48_LATENCY : INTEGER;
  BEGIN
    P2_WIDTH := cmpy_3_get_p2_width(A_WIDTH, B_WIDTH);
    cmpy_3_DSP48_LATENCY := ADDER_DELAY_1_3 + cmpy_mult_add_latency(C_XDEVICEFAMILY, A_WIDTH+1, B_WIDTH, P2_WIDTH, 0, 5, MULT_13_PIPE_IN, PIPE_MID, PIPE_OUT) + 1;
    RETURN cmpy_3_DSP48_LATENCY;
  END cmpy_3_DSP48_latency;

  FUNCTION cmpy_latency(C_FAMILY, C_XDEVICEFAMILY : STRING; OPTIMIZE, A_WIDTH, B_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT, C_HAS_SCLR : INTEGER; SINGLE_OUTPUT : INTEGER := 0) RETURN INTEGER IS
    CONSTANT LARGE_WIDTH          : INTEGER := max_i(A_WIDTH, B_WIDTH);
    CONSTANT SMALL_WIDTH          : INTEGER := min_i(A_WIDTH, B_WIDTH);
    CONSTANT arch                 : INTEGER := cmpy_arch(C_FAMILY, C_XDEVICEFAMILY, OPTIMIZE, LARGE_WIDTH, SMALL_WIDTH, SINGLE_OUTPUT);
    VARIABLE latency              : INTEGER;
    CONSTANT SO_CMPY_SYNC_LATENCY : INTEGER := 2;
    CONSTANT SO_INPUT_MUX_REG_LATENCY : INTEGER := 1;
  BEGIN
    CASE arch IS
      WHEN ARCH_cmpy_18x18    => latency := cmpy18x18_latency(PIPE_IN, PIPE_MID, PIPE_OUT);
      WHEN ARCH_cmpy_35x18    => latency := cmpy35x18_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      WHEN ARCH_cmpy_52x18    => latency := cmpy52x18_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      WHEN ARCH_cmpy_35x35    => latency := cmpy35x35_latency(C_XDEVICEFAMILY, PIPE_IN, PIPE_MID, PIPE_OUT);
      WHEN ARCH_complex_mult4 => latency := mult_latency(C_FAMILY, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH)+PIPE_OUT+(PIPE_OUT*ROUND)+1;
      WHEN ARCH_complex_mult3 => RETURN max_i(mult_latency(C_FAMILY, C_XDEVICEFAMILY, A_WIDTH+1, B_WIDTH),
                                              mult_latency(C_FAMILY, C_XDEVICEFAMILY, A_WIDTH, B_WIDTH+1))+2*PIPE_OUT+(PIPE_OUT*ROUND)+1;
      WHEN OTHERS => RETURN cmpy_3_DSP48_latency(C_XDEVICEFAMILY, LARGE_WIDTH, SMALL_WIDTH, P_WIDTH, ROUND, PIPE_IN, PIPE_MID, PIPE_OUT);
    END CASE;
    IF SINGLE_OUTPUT = 1 THEN
      RETURN latency + SO_CMPY_SYNC_LATENCY + SO_INPUT_MUX_REG_LATENCY;
    ELSE
      RETURN latency;
    END IF;
  END cmpy_latency;

  FUNCTION allow_hybrid_ram_use (family, xdevicefamily : STRING; mem_type, output_width, depth : INTEGER) RETURN BOOLEAN IS
    VARIABLE ram_widths     : hybrid_ram_properties := (bram_width => 0, dram_width => 0);
    CONSTANT data_ram_width : INTEGER               := output_width;
  BEGIN
    IF mem_type = BLOCK_RAM THEN
      ram_widths := calc_hybrid_ram_widths(family, xdevicefamily, data_ram_width, depth);
      IF ram_widths.dram_width /= 0 THEN
        RETURN TRUE;
      ELSE
        RETURN FALSE;
      END IF;
    ELSE
      RETURN FALSE;
    END IF;
  END FUNCTION allow_hybrid_ram_use;

  FUNCTION r22_allow_reorder_hybrid_ram_use(family, xdevicefamily : STRING; nfft_max, fft_output_width, reorder_mem_type, has_natural_output, optimize_goal : INTEGER) RETURN BOOLEAN IS
    CONSTANT HAS_REORDER_BUFFER     : INTEGER := 1;
    VARIABLE HYBRID_REORDER_LATENCY : INTEGER := 0;
    VARIABLE OK_REORDER             : BOOLEAN := FALSE;
  BEGIN

    IF nfft_max < 11 THEN
      HYBRID_REORDER_LATENCY := get_min_mem_delay(family, xdevicefamily, reorder_mem_type, nfft_max, optimize_goal, HAS_REORDER_BUFFER);
    ELSE
      HYBRID_REORDER_LATENCY := 0;
    END IF;

    IF has_natural_output = 1 AND HYBRID_REORDER_LATENCY > 1 THEN
      OK_REORDER := allow_hybrid_ram_use(family, xdevicefamily, reorder_mem_type, fft_output_width, nfft_max);
    ELSE
      OK_REORDER := FALSE;
    END IF;
    RETURN OK_REORDER;
  END FUNCTION r22_allow_reorder_hybrid_ram_use;

  FUNCTION r22_allow_hybrid_ram_use (family, xdevicefamily : STRING; bram_stages, nfft_max, has_scaling, fft_input_width, fft_output_width, reorder_mem_type, has_natural_output, optimize_goal : INTEGER) RETURN BOOLEAN IS
    CONSTANT R22_MEM_TYPES      : r22_const_array := r22_mem_type(nfft_max, bram_stages);
    CONSTANT R22_MEM_WIDTHS     : r22_const_array := r22_pe_width(has_scaling, nfft_max, fft_input_width);
    CONSTANT BLOCK_RAM          : INTEGER         := 1;
    VARIABLE OK_BRAM_STAGES     : BOOLEAN         := false;
    VARIABLE OK_REORDER         : BOOLEAN         := false;
    CONSTANT NUMBER_OF_PEs      : INTEGER         := (NFFT_MAX+1)/2;
    VARIABLE MEM_DEPTH          : INTEGER         := 0;
    CONSTANT streaming_mem_type : r22_const_array := r22_mem_type(NFFT_MAX, BRAM_STAGES);
    VARIABLE PE_ID              : INTEGER         := 0;
    VARIABLE PE_INDEX           : INTEGER         := 0;
    VARIABLE TW_MEM_TYPE        : INTEGER         := 0;
    CONSTANT width_of_pe        : r22_const_array := r22_pe_width(HAS_SCALING, NFFT_MAX, fft_INPUT_WIDTH);
    VARIABLE bf1_mem_type       : INTEGER         := 0;
    VARIABLE bf2_mem_type       : INTEGER         := 0;
    VARIABLE real_mem_depth     : INTEGER         := 0;
    VARIABLE BF1_ID             : INTEGER         := 0;
    VARIABLE BF2_ID             : INTEGER         := 0;
    VARIABLE has_bf2            : INTEGER         := 0;
    CONSTANT NFFT_MAX_ODD       : INTEGER         := NFFT_MAX - (NFFT_MAX/2)*2;
    CONSTANT NFFT_MAX_EVEN      : INTEGER         := 1-NFFT_MAX_ODD;
  BEGIN

    r22_allow_hybrid_ram_use_loop : FOR i IN 0 TO NUMBER_OF_PEs-1 LOOP
      MEM_DEPTH    := NFFT_MAX-1-2*i;
      PE_ID        := (MEM_DEPTH)/2;
      PE_INDEX     := (NFFT_MAX+1)/2-1 - PE_ID;
      BF1_MEM_TYPE := BOOLEAN'pos(streaming_mem_type(i) > 0);
      BF2_MEM_TYPE := BOOLEAN'pos(streaming_mem_type(i) > 1)*BOOLEAN'pos(PE_INDEX /= (nfft_max+1)/2-2);
      BF1_ID       := 2*PE_ID+NFFT_MAX_EVEN;
      BF2_ID       := BF1_ID-1;
      HAS_BF2      := 1-BOOLEAN'pos(PE_ID = 0)*(NFFT_MAX_ODD);
      IF BF1_ID > 4 AND BF1_MEM_TYPE = 1 THEN
        REAL_MEM_DEPTH := mem_depth;
        IF allow_hybrid_ram_use(FAMILY, XDEVICEFAMILY, BF1_MEM_TYPE, 2*(width_of_pe(i)+1), REAL_MEM_DEPTH) THEN
          OK_BRAM_STAGES := true;
          EXIT r22_allow_hybrid_ram_use_loop;
        END IF;
      ELSE
      END IF;

      IF HAS_BF2 = 1 AND BF2_MEM_TYPE = 1 THEN
        IF BF2_ID > 4 THEN
          real_mem_depth := MEM_DEPTH - 1;
          IF allow_hybrid_ram_use(FAMILY, XDEVICEFAMILY, BF2_MEM_TYPE, 2*(width_of_pe(i)+1), REAL_MEM_DEPTH) THEN
            OK_BRAM_STAGES := true;
            EXIT r22_allow_hybrid_ram_use_loop;
          END IF;
        ELSE
        END IF;
      END IF;

    END LOOP r22_allow_hybrid_ram_use_loop;

    OK_REORDER := r22_allow_reorder_hybrid_ram_use(family, xdevicefamily, nfft_max, fft_output_width, reorder_mem_type, has_natural_output, optimize_goal);

    RETURN OK_BRAM_STAGES OR OK_REORDER;
  END FUNCTION r22_allow_hybrid_ram_use;

END timing_model_pkg;
