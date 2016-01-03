
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
library work;
use work.timing_model_pkg.all;
package timing_pkg is
  function get_unload_delay(C_XDEVICEFAMILY                                  : string; C_ARCH, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT, C_HAS_NATURAL_OUTPUT, NFFT : integer) return integer;
  function get_extra_latency_r4(C_XDEVICEFAMILY                              : string; C_TWIDDLE_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_CMPY_TYPE, C_BFLY_TYPE, C_HAS_SCALING, C_HAS_ROUNDING, C_DATA_MEM_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_XN_DATA_OFFSET : integer) return integer;
  function get_extra_latency_r2(C_XDEVICEFAMILY                              : string; C_TWIDDLE_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_CMPY_TYPE, C_BFLY_TYPE, C_HAS_SCALING, C_HAS_ROUNDING, C_DATA_MEM_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer;
  function get_extra_latency_r22(C_XDEVICEFAMILY                             : string; NFFT, C_NFFT_MAX, C_BRAM_STAGES, C_HAS_SCALING, C_INPUT_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_TWIDDLE_WIDTH, C_HAS_ROUNDING, C_HAS_NATURAL_INPUT : integer) return integer;
  function get_output_order_latency_r22(C_XDEVICEFAMILY                      : string; C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_HAS_NFFT, C_NFFT_MAX, NFFT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_HAS_SCALING, C_HAS_BFP : integer) return integer;
  function get_fast_bfy_latency_r22(C_NFFT_MAX, C_HAS_NFFT, C_BFLY_TYPE, NFFT : integer) return integer;
  function get_extra_latency_so(C_XDEVICEFAMILY                              : string; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer;
  function get_output_order_latency_so(C_XDEVICEFAMILY                       : string; C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_XN_DATA_OFFSET : integer) return integer;
  function get_run_latency(C_XDEVICEFAMILY                                   : string; NFFT, C_ARCH, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_INPUT_WIDTH, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT, C_XN_DATA_OFFSET : integer) return integer;
  function get_fp_extra_latency(C_XDEVICEFAMILY                              : string; NFFT, C_ARCH, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_INPUT_WIDTH, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT : integer) return integer;
  function gui_get_transform_latency(C_XDEVICEFAMILY                         : string; nfft, C_NFFT_MAX, C_ARCH, C_HAS_NFFT, C_INPUT_WIDTH, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_HAS_SCALING, C_HAS_BFP, C_HAS_ROUNDING, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_DATA_MEM_TYPE, C_TWIDDLE_MEM_TYPE, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_CMPY_TYPE, C_BFLY_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT, C_THROTTLE_SCHEME : integer) return integer;
end timing_pkg;
package body timing_pkg is
  function get_unload_delay(C_XDEVICEFAMILY : string; C_ARCH, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT, C_HAS_NATURAL_OUTPUT, NFFT: integer) return integer is
    constant ARCH_E_FUDGE_FACTOR : integer := 3;
    variable result              : integer;
    constant c_fixed_width: integer:= get_fixed_width(C_ARCH,sp_float_mant_width+1);
    constant c_fixed_output_width: integer:=correct_output_width(c_fixed_width,when_else(C_ARCH=3,0,1),C_NFFT_MAX,0);
    constant c_fixed_output_frac_width: integer:=when_else(C_ARCH/=3,c_fixed_output_width-1,c_fixed_width-1);
  begin
    if C_USE_FLT_PT=1 and C_ARCH/=3 then
      result:=get_unload_delay(C_XDEVICEFAMILY, C_ARCH, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, 0,0, NFFT);
      result:=result+get_fp_convert_to_fp_delay(C_XDEVICEFAMILY,c_fixed_output_width,c_fixed_output_frac_width);
      if C_ARCH/=3 and C_HAS_NATURAL_OUTPUT=0 then
        result:=result+
                2**NFFT +
                4 +
                get_fp_convert_to_block_fp_delay(C_XDEVICEFAMILY,c_fixed_width);
      end if;
    else
      case C_ARCH is
        when 1 =>
          result := get_mem_delay(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX-2, get_twiddle_latency(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, C_TWIDDLE_WIDTH), 4, 4, 1, 1, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM) + 5;
        when 2 =>
          result := get_mem_delay(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX-1, get_twiddle_latency(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, C_TWIDDLE_WIDTH), 2, 2, 1, 1, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM) + 3;
        when 3 =>
          result := 0;
        when 4 =>
          result := get_min_mem_delay(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM) + ARCH_E_FUDGE_FACTOR;
        when others =>
          assert false report "timing_model : get_unload_delay : unknown value of C_ARCH" severity error;
      end case;
    end if;
    return result;
  end get_unload_delay;
  function get_extra_latency_r4(C_XDEVICEFAMILY : string; C_TWIDDLE_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_CMPY_TYPE, C_BFLY_TYPE, C_HAS_SCALING, C_HAS_ROUNDING, C_DATA_MEM_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_XN_DATA_OFFSET : integer) return integer is
    constant INPUT_MEMORY_DELAY : integer := 3;
    constant SWITCH_DELAY       : integer := 1;
    constant TWGEN_DELAY        : integer := get_twiddle_latency(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, C_TWIDDLE_WIDTH);
    constant DRFLY_WIDTH        : integer := C_OUTPUT_WIDTH + 4;
    constant MULT_OUT_WIDTH     : integer := DRFLY_WIDTH + 2;
    constant MULT_TYPE          : integer := 0;
    constant MULT_DELAY         : integer := mult_latency_bc(C_XDEVICEFAMILY, C_CMPY_TYPE, C_OUTPUT_WIDTH, C_TWIDDLE_WIDTH, MULT_OUT_WIDTH, 1, boolean'pos((MULT_TYPE /= 4) and (MULT_TYPE /= 5)), 1, 1, 0);
    constant PE_DELAY           : integer := pe_latency_b(C_XDEVICEFAMILY, C_BFLY_TYPE, DRFLY_WIDTH, MULT_DELAY, 1, C_HAS_SCALING, C_HAS_ROUNDING);
    constant MUX_DELAY          : integer := 1;
    constant RW_ADDR_GEN_DELAY  : integer := 4;
    constant TW_ADDR_GEN_DELAY  : integer := 4;
    constant INPUT_MEM_WR_DELAY : integer := INPUT_MEMORY_DELAY;
    constant MEM_DELAY          : integer := get_mem_delay(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX-2, TWGEN_DELAY, TW_ADDR_GEN_DELAY, RW_ADDR_GEN_DELAY, MUX_DELAY, SWITCH_DELAY, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
    constant PE_PAD_DELAY       : integer := (RW_ADDR_GEN_DELAY + MUX_DELAY + MEM_DELAY + SWITCH_DELAY + PE_DELAY + SWITCH_DELAY + MUX_DELAY) - (INPUT_MEM_WR_DELAY + MUX_DELAY);
  begin
    return PE_PAD_DELAY + 1;
  end get_extra_latency_r4;
  function get_extra_latency_r2(C_XDEVICEFAMILY : string; C_TWIDDLE_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_CMPY_TYPE, C_BFLY_TYPE, C_HAS_SCALING, C_HAS_ROUNDING, C_DATA_MEM_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer is
    constant SWITCH_DELAY      : integer := 1;
    constant TWGEN_DELAY       : integer := get_twiddle_latency(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX-1, C_TWIDDLE_WIDTH);
    constant BTRFLY_WIDTH      : integer := C_OUTPUT_WIDTH + 4;
    constant MULT_OUT_WIDTH    : integer := BTRFLY_WIDTH + 2;
    constant MULT_TYPE         : integer := 0;
    constant MULT_DELAY        : integer := mult_latency_bc(C_XDEVICEFAMILY, C_CMPY_TYPE, C_OUTPUT_WIDTH, C_TWIDDLE_WIDTH, MULT_OUT_WIDTH, 1, boolean'pos((MULT_TYPE /= 4) and (MULT_TYPE /= 5)), 1, 1, 0);
    constant PE_DELAY          : integer := r2_pe_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, BTRFLY_WIDTH, MULT_DELAY, C_HAS_SCALING, C_HAS_ROUNDING);
    constant MUX_DELAY         : integer := 1;
    constant RW_ADDR_GEN_DELAY : integer := 2;
    constant TW_ADDR_GEN_DELAY : integer := 2;
    constant MEM_DELAY         : integer := get_mem_delay(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_NFFT_MAX-1, TWGEN_DELAY, TW_ADDR_GEN_DELAY, RW_ADDR_GEN_DELAY, MUX_DELAY, SWITCH_DELAY, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
    constant PE_PAD_DELAY      : integer := (RW_ADDR_GEN_DELAY + MUX_DELAY + MEM_DELAY + SWITCH_DELAY + PE_DELAY + 0 + MUX_DELAY) - (RW_ADDR_GEN_DELAY + MUX_DELAY);
  begin
    return PE_PAD_DELAY + 1;
  end get_extra_latency_r2;
  function get_extra_latency_r22(C_XDEVICEFAMILY : string; NFFT, C_NFFT_MAX, C_BRAM_STAGES, C_HAS_SCALING, C_INPUT_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_TWIDDLE_WIDTH, C_HAS_ROUNDING, C_HAS_NATURAL_INPUT : integer) return integer is
    constant NUMBER_OF_PEs    : integer         := (C_NFFT_MAX+1)/2;
    constant LAST_PE_HAS_1_BF : integer         := C_NFFT_MAX mod 2;
    constant PEs_IN_USE       : integer         := (NFFT+1+LAST_PE_HAS_1_BF)/2;
    constant MEM_TYPE         : r22_const_array := r22_mem_type(C_NFFT_MAX, C_BRAM_STAGES,1);
    constant WIDTH_OF_PE      : r22_const_array := r22_pe_width(C_HAS_SCALING, C_NFFT_MAX, C_INPUT_WIDTH);
    constant PE_LATENCIES     : r22_const_array := r22_pe_latency(C_XDEVICEFAMILY, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_HAS_SCALING, C_HAS_ROUNDING, 1, C_HAS_NATURAL_INPUT, width_of_pe, mem_type);
    variable result           : integer         := 0;
  begin
    for i in NUMBER_OF_PEs-PEs_IN_USE to NUMBER_OF_PEs-1 loop
      result := result + PE_LATENCIES(i);
    end loop;
    return result;
  end get_extra_latency_r22;
  function get_output_order_latency_r22(C_XDEVICEFAMILY : string; C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_HAS_NFFT, C_NFFT_MAX, NFFT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_HAS_SCALING, C_HAS_BFP : integer) return integer is
    constant LOAD_UNLOAD_ORDER_SAME : boolean := (C_HAS_NATURAL_INPUT = 0 and C_HAS_NATURAL_OUTPUT = 0) or (C_HAS_NATURAL_INPUT = 1 and C_HAS_NATURAL_OUTPUT = 1);
    constant NEED_BUFFER : boolean := LOAD_UNLOAD_ORDER_SAME or (C_HAS_BFP = 1);
    constant RAM_OUTPUT_WIDTH      : integer := when_else(C_HAS_BFP = 1, 2*(C_OUTPUT_WIDTH+C_NFFT_MAX+1), 2*C_OUTPUT_WIDTH);
    constant HYBRID_REORDER_BUFFER : integer := boolean'pos(C_USE_HYBRID_RAM = 1 and r22_allow_reorder_hybrid_ram_use(C_XDEVICEFAMILY, C_NFFT_MAX, RAM_OUTPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_HAS_BFP));
    constant NEED_BFP_MUX : boolean := C_HAS_BFP = 1;
    variable result                : integer;
  begin
    if not(NEED_BUFFER) then
      if C_HAS_NFFT = 0 then
        result := -1;
      else
        if C_NFFT_MAX - NFFT > 1 then
          result := 0;
        else
          result := -1;
        end if;
        result := result + 1 + boolean'pos(C_HAS_NFFT = 1 and ((C_NFFT_MAX-2) > 8));
      end if;
    else
      result := integer(2**(NFFT));
      result := result + get_min_mem_delay(C_XDEVICEFAMILY, C_REORDER_MEM_TYPE, C_NFFT_MAX, C_OPTIMIZE_GOAL, HYBRID_REORDER_BUFFER);
      if C_HAS_NFFT = 1 then
        result := result + 2 + boolean'pos(C_HAS_NFFT = 1 and ((C_NFFT_MAX-2) > 8));
      end if;
      if C_HAS_NFFT = 1 and C_NFFT_MAX - NFFT > 1 then
        result := result + 1;
      end if;
    end if;
    if NEED_BFP_MUX then
      result := result + r22_right_shift_latency(C_NFFT_MAX+1);
    end if;
    if C_HAS_NATURAL_INPUT = 0 then
      case C_NFFT_MAX is
        when 3 => result := result + 5;
        when 4 => result := result + 8;
        when 5 => result := result + 23;
        when 6 to 16 => result := result + ((2**(C_NFFT_MAX-1)) - 2);
        when others => null;
      end case;
    end if;
    return result;
  end get_output_order_latency_r22;
  function get_fast_bfy_latency_r22(C_NFFT_MAX, C_HAS_NFFT, C_BFLY_TYPE, NFFT : integer) return integer is
    variable result : integer;
  begin
    if C_BFLY_TYPE = 1 and C_HAS_NFFT = 1 and (C_NFFT_MAX - NFFT) mod 2 = 0 then
      result := -1;
    else
      result := 0;
    end if;
    return result;
  end get_fast_bfy_latency_r22;
  function get_extra_latency_so(C_XDEVICEFAMILY : string; C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM : integer) return integer is
    constant PE_LATENCY_ACTUAL : integer := so_pe_latency(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
    constant PE_LATENCY        : integer := PE_LATENCY_ACTUAL + 1;
    constant NFFT_MIN          : integer := get_nfft_min(4, C_HAS_NFFT, C_NFFT_MAX);
    constant NFFT_MIN_REUSE    : integer := so_data_reuse(NFFT_MIN);
    constant BFP_SCALE_LATENCY : integer := so_bfp_scale_gen_latency(C_HAS_SCALING, C_HAS_ROUNDING) * C_HAS_BFP;
    constant MAX_REUSE_WAIT    : integer := max_i(PE_LATENCY-NFFT_MIN_REUSE, 0);
    constant MAX_WAIT          : integer := max_i(MAX_REUSE_WAIT, BFP_SCALE_LATENCY);
    constant MAX_WAIT_ADJUST   : integer := boolean'pos(PE_LATENCY_ACTUAL = NFFT_MIN_REUSE);
  begin
    return MAX_WAIT + MAX_WAIT_ADJUST;
  end get_extra_latency_so;
  function get_output_order_latency_so(C_XDEVICEFAMILY : string; C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_XN_DATA_OFFSET : integer) return integer is
    constant SO_RUN_ADDR_GEN_LATENCY : integer := 4;
    constant XN_RE_DELAY             : integer := 3;
    variable result                  : integer;
  begin
    result := SO_RUN_ADDR_GEN_LATENCY;
    if ((C_HAS_NATURAL_OUTPUT = 1 and C_HAS_NATURAL_INPUT = 1) or (C_HAS_NATURAL_OUTPUT = 0 and C_HAS_NATURAL_INPUT = 0)) then
      result := result + XN_RE_DELAY;
    else
      result := result + get_unload_delay(C_XDEVICEFAMILY, 4, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, 0,1,0);
    end if;
    return result;
  end get_output_order_latency_so;
  function get_run_latency(C_XDEVICEFAMILY : string; NFFT, C_ARCH, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_INPUT_WIDTH, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT, C_XN_DATA_OFFSET : integer) return integer is
    variable result : integer;

    constant c_fixed_width: integer:= get_fixed_width(C_ARCH,sp_float_mant_width+1);
    constant c_fixed_output_width: integer:=correct_output_width(c_fixed_width,when_else(C_ARCH=3,0,1),C_NFFT_MAX,0);
    constant c_fixed_output_frac_width: integer:=when_else(C_ARCH/=3,c_fixed_output_width-1,c_fixed_width-1);
  begin
    if C_USE_FLT_PT = 1 then
      if C_ARCH=3 then
        result := get_run_latency(C_XDEVICEFAMILY, NFFT, C_ARCH, C_DATA_MEM_TYPE, 0, 0, C_NFFT_MAX, c_fixed_output_width, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, 0, C_BRAM_STAGES, C_REORDER_MEM_TYPE, c_fixed_width, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, 0, C_XN_DATA_OFFSET);
        result:=result+get_fp_convert_to_fp_delay(C_XDEVICEFAMILY,c_fixed_output_width,c_fixed_output_frac_width);
      else
        result := get_run_latency(C_XDEVICEFAMILY, NFFT, C_ARCH, C_DATA_MEM_TYPE, 0, 1, C_NFFT_MAX, c_fixed_output_width, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, 1, C_BRAM_STAGES, C_REORDER_MEM_TYPE, c_fixed_width, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, 0, C_XN_DATA_OFFSET);
      end if;
      result := result +
                2**NFFT +
                4 +
                get_fp_convert_to_block_fp_delay(C_XDEVICEFAMILY,c_fixed_width);
    else
      case C_ARCH is
        when 1 =>
          result := (NFFT+1)/2 * integer(2**(NFFT-2));
          result := result + (NFFT+1)/2 * get_extra_latency_r4(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_CMPY_TYPE, C_BFLY_TYPE, C_HAS_SCALING, C_HAS_ROUNDING, C_DATA_MEM_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_XN_DATA_OFFSET);
        when 2 =>
          result := NFFT * integer(2**(NFFT-1));
          result := result + NFFT * get_extra_latency_r2(C_XDEVICEFAMILY, C_TWIDDLE_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_CMPY_TYPE, C_BFLY_TYPE, C_HAS_SCALING, C_HAS_ROUNDING, C_DATA_MEM_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);

        when 3 =>
          result := integer(2**(NFFT-1));
          result := result + get_extra_latency_r22(C_XDEVICEFAMILY, NFFT, C_NFFT_MAX, C_BRAM_STAGES, C_HAS_SCALING, C_INPUT_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_TWIDDLE_WIDTH, C_HAS_ROUNDING, C_HAS_NATURAL_INPUT);
          result := result + get_output_order_latency_r22(C_XDEVICEFAMILY, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_HAS_NFFT, C_NFFT_MAX, NFFT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_HAS_SCALING, C_HAS_BFP);
          result := result + get_fast_bfy_latency_r22(C_NFFT_MAX, C_HAS_NFFT, C_BFLY_TYPE, NFFT);

        when 4 =>
          result := NFFT * integer(2**NFFT);
          result := result + (NFFT-1) * get_extra_latency_so(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
          result := result + so_pe_latency(C_XDEVICEFAMILY, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM);
          result := result + get_output_order_latency_so(C_XDEVICEFAMILY, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_XN_DATA_OFFSET);
        when others =>
          assert false report "timing_model : get_run_latency : unknown value of C_ARCH" severity error;
      end case;
    end if;
    return result;
  end get_run_latency;
  function get_fp_extra_latency(C_XDEVICEFAMILY : string; NFFT, C_ARCH, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, C_TWIDDLE_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_INPUT_WIDTH, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT : integer) return integer is
    variable result : integer:=0;
    constant c_fixed_width: integer:= get_fixed_width(C_ARCH,sp_float_mant_width+1);
    constant c_fixed_output_width: integer:=correct_output_width(c_fixed_width,when_else(C_ARCH=3,0,1),C_NFFT_MAX,0);
    constant c_fixed_output_frac_width: integer:=when_else(C_ARCH/=3,c_fixed_output_width-1,c_fixed_width-1);
  begin
    if C_ARCH=3 then
     result:=get_fp_convert_to_fp_delay(C_XDEVICEFAMILY,c_fixed_output_width,c_fixed_output_frac_width);
    end if;
    result := result +
              2**NFFT +
              4 +
              get_fp_convert_to_block_fp_delay(C_XDEVICEFAMILY,c_fixed_width);

    return result;
  end get_fp_extra_latency;

  function gui_get_transform_latency(C_XDEVICEFAMILY : string; nfft, C_NFFT_MAX, C_ARCH, C_HAS_NFFT, C_INPUT_WIDTH, C_TWIDDLE_WIDTH, C_OUTPUT_WIDTH, C_HAS_SCALING, C_HAS_BFP, C_HAS_ROUNDING, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_DATA_MEM_TYPE, C_TWIDDLE_MEM_TYPE, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_CMPY_TYPE, C_BFLY_TYPE, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM, C_USE_FLT_PT, C_THROTTLE_SCHEME: integer) return integer is
    constant C_XN_DATA_OFFSET : integer := 0;
    constant NFFT_MIN         : integer := get_nfft_min(C_ARCH, C_HAS_NFFT, C_NFFT_MAX);
    constant TW_EXPAND_WIDTH  : integer := C_TWIDDLE_WIDTH + 1;
    constant R_USE_HYBRID_RAM : integer := boolean'pos(resolve_hybrid_ram_use(C_XDEVICEFAMILY, C_ARCH, C_NFFT_MAX, C_DATA_MEM_TYPE, C_OUTPUT_WIDTH, C_BRAM_STAGES, C_HAS_SCALING, C_HAS_BFP, C_INPUT_WIDTH, C_REORDER_MEM_TYPE, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, C_USE_HYBRID_RAM));
    variable cycles           : integer := 0;
  begin
    if nfft < NFFT_MIN or nfft > C_NFFT_MAX then
      return 0;
    end if;
    if C_ARCH = 3 then
      cycles := integer(2**nfft) / 2 + 3 + 1;
    else
      cycles := integer(2**nfft);
    end if;
    cycles := cycles + get_run_latency(C_XDEVICEFAMILY, nfft, C_ARCH, C_DATA_MEM_TYPE, C_HAS_ROUNDING, C_HAS_SCALING, C_NFFT_MAX, C_OUTPUT_WIDTH, C_TWIDDLE_MEM_TYPE, TW_EXPAND_WIDTH, C_BFLY_TYPE, C_CMPY_TYPE, C_HAS_NFFT, C_HAS_BFP, C_BRAM_STAGES, C_REORDER_MEM_TYPE, C_INPUT_WIDTH, C_HAS_NATURAL_INPUT, C_HAS_NATURAL_OUTPUT, C_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, C_USE_FLT_PT, C_XN_DATA_OFFSET);
    cycles := cycles + integer(2**nfft) + get_unload_delay(C_XDEVICEFAMILY, C_ARCH, C_DATA_MEM_TYPE, C_NFFT_MAX, C_TWIDDLE_MEM_TYPE, TW_EXPAND_WIDTH, C_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, C_USE_FLT_PT, C_HAS_NATURAL_OUTPUT, C_NFFT_MAX);
    if C_THROTTLE_SCHEME = 0 then
      cycles := cycles + 3;
    else
      cycles := cycles + 7;
    end if;

    return cycles;
  end gui_get_transform_latency;
end timing_pkg;
