
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
library xilinxcorelib;
use xilinxcorelib.prims_utils_v9_1.derived;
library work;
use work.timing_model_pkg.all;
use work.timing_pkg.all;
entity timing_model is
  generic (
    C_FAMILY             : string  := "virtex4";
    C_XDEVICEFAMILY      : string  := "virtex4";
    C_CHANNELS           : integer := 1;
    C_NFFT_MAX           : integer := 10;
    C_ARCH               : integer := 1;
    C_HAS_NFFT           : integer := 0;
    C_USE_FLT_PT         : integer := 0;
    C_INPUT_WIDTH        : integer := 16;
    C_TWIDDLE_WIDTH      : integer := 16;
    C_OUTPUT_WIDTH       : integer := 16;
    C_HAS_CE             : integer := 0;
    C_HAS_SCLR           : integer := 0;
    C_HAS_OVFLO          : integer := 0;
    C_HAS_SCALING        : integer := 1;
    C_HAS_BFP            : integer := 0;
    C_HAS_ROUNDING       : integer := 0;
    C_DATA_MEM_TYPE      : integer := 1;
    C_TWIDDLE_MEM_TYPE   : integer := 1;
    C_BRAM_STAGES        : integer := 0;
    C_REORDER_MEM_TYPE   : integer := 1;
    C_HAS_NATURAL_OUTPUT : integer := 0;
    C_HAS_CYCLIC_PREFIX  : integer := 0;
    C_OPTIMIZE_GOAL      : integer := 0;
    C_USE_HYBRID_RAM     : integer := 0;
    C_FAST_CMPY          : integer := 0;
    C_OPTIMIZE           : integer := 0
    );
  port (
    START     : in std_logic                               := '0';
    UNLOAD    : in std_logic                               := '0';
    NFFT      : in std_logic_vector(4 downto 0)            := (others => '0');
    NFFT_WE   : in std_logic                               := '0';
    SCLR      : in std_logic                               := '0';
    CE        : in std_logic                               := '1';
    CLK       : in std_logic                               := '0';
    CP_LEN    : in std_logic_vector(C_NFFT_MAX-1 downto 0) := (others => '0');
    CP_LEN_WE : in std_logic                               := '0';
    FWD_INV      : in std_logic := '1';
    FWD_INV_WE   : in std_logic := '0';
    FWD_INV0     : in std_logic := '1';
    FWD_INV0_WE  : in std_logic := '0';
    FWD_INV1     : in std_logic := '1';
    FWD_INV1_WE  : in std_logic := '0';
    FWD_INV2     : in std_logic := '1';
    FWD_INV2_WE  : in std_logic := '0';
    FWD_INV3     : in std_logic := '1';
    FWD_INV3_WE  : in std_logic := '0';
    FWD_INV4     : in std_logic := '1';
    FWD_INV4_WE  : in std_logic := '0';
    FWD_INV5     : in std_logic := '1';
    FWD_INV5_WE  : in std_logic := '0';
    FWD_INV6     : in std_logic := '1';
    FWD_INV6_WE  : in std_logic := '0';
    FWD_INV7     : in std_logic := '1';
    FWD_INV7_WE  : in std_logic := '0';
    FWD_INV8     : in std_logic := '1';
    FWD_INV8_WE  : in std_logic := '0';
    FWD_INV9     : in std_logic := '1';
    FWD_INV9_WE  : in std_logic := '0';
    FWD_INV10    : in std_logic := '1';
    FWD_INV10_WE : in std_logic := '0';
    FWD_INV11    : in std_logic := '1';
    FWD_INV11_WE : in std_logic := '0';
    SCALE_SCH      : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH_WE   : in std_logic                                                                     := '0';
    SCALE_SCH0     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH0_WE  : in std_logic                                                                     := '0';
    SCALE_SCH1     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH1_WE  : in std_logic                                                                     := '0';
    SCALE_SCH2     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH2_WE  : in std_logic                                                                     := '0';
    SCALE_SCH3     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH3_WE  : in std_logic                                                                     := '0';
    SCALE_SCH4     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH4_WE  : in std_logic                                                                     := '0';
    SCALE_SCH5     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH5_WE  : in std_logic                                                                     := '0';
    SCALE_SCH6     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH6_WE  : in std_logic                                                                     := '0';
    SCALE_SCH7     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH7_WE  : in std_logic                                                                     := '0';
    SCALE_SCH8     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH8_WE  : in std_logic                                                                     := '0';
    SCALE_SCH9     : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH9_WE  : in std_logic                                                                     := '0';
    SCALE_SCH10    : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH10_WE : in std_logic                                                                     := '0';
    SCALE_SCH11    : in std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH11_WE : in std_logic                                                                     := '0';
    XN_INDEX : out std_logic_vector(C_NFFT_MAX-1 downto 0);
    XK_INDEX : out std_logic_vector(C_NFFT_MAX-1 downto 0);
    RFD      : out std_logic;
    BUSY     : out std_logic;
    DV       : out std_logic;
    EDONE    : out std_logic;
    DONE     : out std_logic;
    CPV      : out std_logic;
    RFS      : out std_logic;
    FWD_INV_USED   : out std_logic;
    FWD_INV0_USED  : out std_logic;
    FWD_INV1_USED  : out std_logic;
    FWD_INV2_USED  : out std_logic;
    FWD_INV3_USED  : out std_logic;
    FWD_INV4_USED  : out std_logic;
    FWD_INV5_USED  : out std_logic;
    FWD_INV6_USED  : out std_logic;
    FWD_INV7_USED  : out std_logic;
    FWD_INV8_USED  : out std_logic;
    FWD_INV9_USED  : out std_logic;
    FWD_INV10_USED : out std_logic;
    FWD_INV11_USED : out std_logic;
    SCALE_SCH_USED   : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH0_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH1_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH2_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH3_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH4_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH5_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH6_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH7_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH8_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH9_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH10_USED : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
    SCALE_SCH11_USED : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0)
    );
end timing_model;
architecture behavioral of timing_model is
  constant C_HAS_NATURAL_INPUT : integer := 1;

  function digit_reverse(data : std_logic_vector; arch : integer; reverse : boolean; nfft : std_logic_vector; nfft_max : integer; has_nfft : integer) return std_logic_vector is
    constant length              : integer                             := data'length;
    variable shifted_result      : std_logic_vector(length-1 downto 0) := (others => '0');
    variable shifted_result_wide : std_logic_vector(15 downto 0)       := (others => '0');
    variable result              : std_logic_vector(length-1 downto 0) := (others => '0');
    variable nfft_min            : std_logic_vector(4 downto 0)        := (others => '0');
    variable nfft_i              : std_logic_vector(4 downto 0)        := (others => '0');
  begin
    if has_nfft = 1 then
      if arch /= 1 then
        nfft_min := "00011";
      else
        nfft_min := "00110";
      end if;

      if nfft > conv_std_logic_vector(NFFT_MAX, 5) then
        nfft_i := conv_std_logic_vector(NFFT_MAX, 5);
      elsif nfft < nfft_min then
        nfft_i := nfft_min;
      else
        nfft_i := nfft;
      end if;
    end if;

    if reverse then
      if arch = 1 then
        result := data;

        if has_nfft = 1 then

          case nfft_i is
            when "00110" =>
              shifted_result_wide := '0' & '0' & '0' & '0' & '0' & '0' & '0' & '0' & '0' &'0' &data(1) &data(0) &data(3) &data(2) &data(5) &data(4);
            when "00111" =>
              shifted_result_wide := '0' & '0' & '0' & '0' & '0' & '0' & '0' & '0' & '0' &data(0) &data(2) &data(1) &data(4) &data(3) &data(6) &data(5);
            when "01000" =>
              shifted_result_wide := '0' & '0' & '0' & '0' & '0' & '0' & '0' & '0' &data(1) &data(0) &data(3) &data(2) &data(5) &data(4) &data(7) &data(6);
            when "01001" =>
              shifted_result_wide := '0' & '0' & '0' & '0' & '0' & '0' & '0' &data(0) &data(2) &data(1) &data(4) &data(3) &data(6) &data(5) &data(8) &data(7);
            when "01010" =>
              shifted_result_wide := '0' & '0' & '0' & '0' & '0' & '0' &data(1) &data(0) &data(3) &data(2) &data(5) &data(4) &data(7) &data(6) &data(9) &data(8);
            when "01011" =>
              shifted_result_wide := '0' & '0' & '0' & '0' & '0' &data(0) &data(2) &data(1) &data(4) &data(3) &data(6) &data(5) &data(8) &data(7) &data(10) &data(9);
            when "01100" =>
              shifted_result_wide := '0' & '0' & '0' & '0' &data(1) &data(0) &data(3) &data(2) &data(5) &data(4) &data(7) &data(6) &data(9) &data(8) &data(11) &data(10);
            when "01101" =>
              shifted_result_wide := '0' & '0' & '0' &data(0) &data(2) &data(1) &data(4) &data(3) &data(6) &data(5) &data(8) &data(7) &data(10) &data(9) &data(12) &data(11);
            when "01110" =>
              shifted_result_wide := '0' & '0' &data(1) &data(0) &data(3) &data(2) &data(5) &data(4) &data(7) &data(6) &data(9) &data(8) &data(11) &data(10) &data(13) &data(12);
            when "01111" =>
              shifted_result_wide := '0' &data(0) &data(2) &data(1) &data(4) &data(3) &data(6) &data(5) &data(8) &data(7) &data(10) &data(9) &data(12) &data(11) &data(14) &data(13);
            when "10000" =>
              shifted_result_wide := data(1) &data(0) &data(3) &data(2) &data(5) &data(4) &data(7) &data(6) &data(9) &data(8) &data(11) &data(10) &data(13) &data(12) &data(15) &data(14);
            when others => null;
          end case;
          result := shifted_result_wide(length-1 downto 0);

        else

          for i in 0 to (length/2)-1 loop
            result(2*i)   := data(length-(2*i)-2);
            result(2*i+1) := data(length-(2*i)-1);
          end loop;
          if length mod 2 = 1 then
            result(length-1) := data(0);
          end if;
        end if;

      else

        if has_nfft = 1 then
          shifted_result := shl(data, conv_std_logic_vector(nfft_max, 5)-nfft_i);
        else
          shifted_result := data;
        end if;

        for i in 0 to length-1 loop
          result(i) := shifted_result(length-i-1);
        end loop;

      end if;

    else
      result := data;

    end if;

    return result;

  end digit_reverse;
  procedure gen_fwd_inv_used(
    constant CHANNEL_EXISTS  : in    boolean;
    constant FWD_INV_DEFAULT : in    std_logic;
    signal CLK               : in    std_logic;
    signal ce                : in    std_logic;
    signal sclr              : in    std_logic;
    signal xn_index          : in    std_logic_vector;
    signal FWD_INV           : in    std_logic;
    signal FWD_INV_WE        : in    std_logic;
    signal fwd_inv_latched   : inout std_logic;
    signal FWD_INV_USED      : out   std_logic
    ) is
  begin
    if rising_edge(CLK) then
      if CHANNEL_EXISTS then
        if sclr = '1' then
          fwd_inv_latched <= FWD_INV_DEFAULT;
        elsif ce = '1' and FWD_INV_WE = '1' then
          fwd_inv_latched <= FWD_INV;
        end if;
        if sclr = '1' then
          FWD_INV_USED <= FWD_INV_DEFAULT;
        elsif ce = '1' and conv_integer(xn_index) = 3 then
          FWD_INV_USED <= fwd_inv_latched;
        end if;
      else
        FWD_INV_USED <= FWD_INV_DEFAULT;
      end if;
    end if;
  end gen_fwd_inv_used;
  procedure gen_scale_sch_used(
    constant CHANNEL_EXISTS    : in    boolean;
    constant SCALE_SCH_DEFAULT : in    std_logic_vector;
    signal CLK                 : in    std_logic;
    signal ce                  : in    std_logic;
    signal sclr                : in    std_logic;
    signal xn_index            : in    std_logic_vector;
    signal SCALE_SCH           : in    std_logic_vector;
    signal SCALE_SCH_WE        : in    std_logic;
    signal scale_sch_latched   : inout std_logic_vector;
    signal SCALE_SCH_USED      : out   std_logic_vector
    ) is
  begin
    if rising_edge(CLK) then
      if CHANNEL_EXISTS then
        if sclr = '1' then
          scale_sch_latched <= SCALE_SCH_DEFAULT;
        elsif ce = '1' and SCALE_SCH_WE = '1' then
          scale_sch_latched <= SCALE_SCH;
        end if;
        if sclr = '1' then
          SCALE_SCH_USED <= SCALE_SCH_DEFAULT;
        elsif ce = '1' and conv_integer(xn_index) = 3 then
          SCALE_SCH_USED <= scale_sch_latched;
        end if;
      else
        SCALE_SCH_USED <= SCALE_SCH_DEFAULT;
      end if;
    end if;
  end gen_scale_sch_used;
  function default_scale_sch(C_ARCH, C_NFFT_MAX : integer) return std_logic_vector is
    constant POW4   : boolean := ((C_NFFT_MAX mod 2) = 0);
    constant WIDTH  : integer := (C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2));
    variable result : std_logic_vector(WIDTH-1 downto 0);
  begin
    case C_ARCH is
      when 1 | 3 =>
        if POW4 then
          result(WIDTH-1 downto WIDTH-2) := "10";
        else
          result(WIDTH-1 downto WIDTH-2) := "01";
        end if;
        for i in WIDTH/2-2 downto 0 loop
          result(i*2+1 downto i*2) := "10";
        end loop;
      when 2 | 4 =>
        for i in WIDTH/2-1 downto 0 loop
          result(i*2+1 downto i*2) := "01";
        end loop;
      when others =>
        report "ERROR: timing_model.vhd: default_scale_sch: unknown architecture number" severity failure;
    end case;
    return result;
  end default_scale_sch;
  function calc_memory_depth (arch, point_size : integer) return integer is
    constant R4_MEMORY_DEPTH     : integer := C_NFFT_MAX - 2;
    constant R2_MEMORY_DEPTH     : integer := C_NFFT_MAX - 1;
    constant R2LITE_MEMORY_DEPTH : integer := C_NFFT_MAX;
  begin
    if arch = 1 then
      return R4_MEMORY_DEPTH;
    elsif arch = 2 then
      return R2_MEMORY_DEPTH;
    elsif arch = 4 then
      return R2LITE_MEMORY_DEPTH;
    else
      return 0;
    end if;
  end function calc_memory_depth;
  constant MEMORY_BANK_DEPTH : integer := calc_memory_depth(C_ARCH, C_NFFT_MAX);
  constant R_FAMILY             : string  := C_FAMILY;
  constant R_XDEVICEFAMILY      : string  := C_XDEVICEFAMILY;
  constant R_CHANNELS           : integer := when_else(C_ARCH = 4 or C_ARCH = 2 or C_ARCH = 1, C_CHANNELS, 1);
  constant R_NFFT_MAX           : integer := C_NFFT_MAX;
  constant R_ARCH               : integer := C_ARCH;
  constant R_HAS_NFFT           : integer := C_HAS_NFFT;
  constant R_INPUT_WIDTH        : integer := C_INPUT_WIDTH;
  constant R_TWIDDLE_WIDTH      : integer := C_TWIDDLE_WIDTH + 1;
  constant R_OUTPUT_WIDTH       : integer := C_OUTPUT_WIDTH;
  constant R_HAS_SCALING        : integer := when_else(C_ARCH = 3 and C_HAS_SCALING = 1 and C_HAS_BFP = 1, 0, C_HAS_SCALING);
  constant R_HAS_BFP            : integer := C_HAS_BFP;
  constant R_HAS_ROUNDING       : integer := C_HAS_ROUNDING;
  constant R_HAS_CE             : integer := C_HAS_CE;
  constant R_HAS_SCLR           : integer := C_HAS_SCLR;
  constant R_HAS_OVFLO          : integer := when_else(C_HAS_SCALING = 1 and C_HAS_BFP = 0, C_HAS_OVFLO, 0);
  constant R_HAS_NATURAL_INPUT  : integer := C_HAS_NATURAL_INPUT;
  constant R_HAS_NATURAL_OUTPUT : integer := C_HAS_NATURAL_OUTPUT;
  constant R_DATA_MEM_TYPE      : integer := when_else(C_ARCH = 3, 1, C_DATA_MEM_TYPE);
  constant R_TWIDDLE_MEM_TYPE   : integer := when_else(C_ARCH = 3, 1, C_TWIDDLE_MEM_TYPE);
  constant R_BRAM_STAGES        : integer := when_else(C_ARCH = 3, C_BRAM_STAGES, 0);
  constant R_REORDER_MEM_TYPE   : integer := when_else(C_ARCH = 3, when_else(C_HAS_NATURAL_OUTPUT = 1 or (C_HAS_SCALING = 1 and C_HAS_BFP = 1 and C_HAS_NATURAL_OUTPUT = 0), C_REORDER_MEM_TYPE, 1), 1);
  constant R_HAS_CYCLIC_PREFIX  : integer := when_else(R_HAS_NATURAL_OUTPUT = 1, C_HAS_CYCLIC_PREFIX, 0);
  constant R_OPTIMIZE_GOAL      : integer := 1;
  constant R_USE_HYBRID_RAM     : integer := when_else(C_USE_HYBRID_RAM = 0, 0, boolean'pos(resolve_hybrid_ram_use(R_FAMILY, R_XDEVICEFAMILY, R_ARCH, R_NFFT_MAX, R_DATA_MEM_TYPE, R_OUTPUT_WIDTH, R_BRAM_STAGES, C_HAS_SCALING, C_HAS_BFP, R_INPUT_WIDTH, R_REORDER_MEM_TYPE, R_HAS_NATURAL_INPUT, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, C_USE_HYBRID_RAM)));
  constant R_FAST_CMPY : integer := when_else(not (derived(C_FAMILY, "virtex4") or derived(C_FAMILY, "virtex5") or derived(C_XDEVICEFAMILY, "spartan3adsp")), 0,
                                                       C_FAST_CMPY);
  constant R_OPTIMIZE : integer := when_else(not (derived(C_FAMILY, "virtex4") or derived(C_FAMILY, "virtex5") or derived(C_XDEVICEFAMILY, "spartan3adsp")), 0,
                                                       C_OPTIMIZE);
  constant R_USE_FLT_PT         : integer := C_USE_FLT_PT;
  constant R_FAST_BFY : integer := R_OPTIMIZE;
  constant NFFT_MAX_SLV : std_logic_vector(4 downto 0) := conv_std_logic_vector(R_NFFT_MAX, 5);
  constant NFFT_MIN     : integer                      := get_nfft_min(R_ARCH, R_HAS_NFFT, R_NFFT_MAX);
  constant NFFT_MIN_SLV : std_logic_vector(4 downto 0) := conv_std_logic_vector(NFFT_MIN, 5);
  constant SCALE_SCH_DEFAULT : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := default_scale_sch(R_ARCH, R_NFFT_MAX);
  constant FWD_INV_DEFAULT : std_logic := '1';
  constant MAX_UNLOAD_DELAY : integer := get_unload_delay(R_FAMILY, R_XDEVICEFAMILY, R_ARCH, R_DATA_MEM_TYPE, R_NFFT_MAX, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT, R_HAS_NATURAL_OUTPUT, R_NFFT_MAX);
  signal unload_delay       : integer := get_unload_delay(R_FAMILY, R_XDEVICEFAMILY, R_ARCH, R_DATA_MEM_TYPE, R_NFFT_MAX, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT, R_HAS_NATURAL_OUTPUT, R_NFFT_MAX);
  signal ce_i    : std_logic;
  signal sclr_in : std_logic;
  signal sclr_i  : std_logic;
  signal nfft_i      : std_logic_vector(4 downto 0) := NFFT_MAX_SLV;
  signal n           : integer                      := 2**R_NFFT_MAX;
  signal run_latency : integer                      := get_run_latency(R_FAMILY, R_XDEVICEFAMILY, R_NFFT_MAX, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_INPUT, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT);
  signal fp_extra_latency : integer                 := get_fp_extra_latency(R_FAMILY, R_XDEVICEFAMILY, R_NFFT_MAX, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_INPUT, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT);
  signal cp_len_latched : std_logic_vector(C_NFFT_MAX-1 downto 0) := (others => '0');
  signal cp_len_used_i  : std_logic_vector(C_NFFT_MAX-1 downto 0) := (others => '0');
  signal cp_len_internal : std_logic_vector(C_NFFT_MAX-1 downto 0) := (others => '0');
  signal cp_len_unload : std_logic_vector(C_NFFT_MAX-1 downto 0) := (others => '0');
  signal fwd_inv_latched   : std_logic := '1';
  signal fwd_inv0_latched  : std_logic := '1';
  signal fwd_inv1_latched  : std_logic := '1';
  signal fwd_inv2_latched  : std_logic := '1';
  signal fwd_inv3_latched  : std_logic := '1';
  signal fwd_inv4_latched  : std_logic := '1';
  signal fwd_inv5_latched  : std_logic := '1';
  signal fwd_inv6_latched  : std_logic := '1';
  signal fwd_inv7_latched  : std_logic := '1';
  signal fwd_inv8_latched  : std_logic := '1';
  signal fwd_inv9_latched  : std_logic := '1';
  signal fwd_inv10_latched : std_logic := '1';
  signal fwd_inv11_latched : std_logic := '1';
  signal scale_sch_latched   : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch0_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch1_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch2_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch3_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch4_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch5_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch6_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch7_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch8_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch9_latched  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch10_latched : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch11_latched : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal fwd_inv_used_i   : std_logic := '1';
  signal fwd_inv0_used_i  : std_logic := '1';
  signal fwd_inv1_used_i  : std_logic := '1';
  signal fwd_inv2_used_i  : std_logic := '1';
  signal fwd_inv3_used_i  : std_logic := '1';
  signal fwd_inv4_used_i  : std_logic := '1';
  signal fwd_inv5_used_i  : std_logic := '1';
  signal fwd_inv6_used_i  : std_logic := '1';
  signal fwd_inv7_used_i  : std_logic := '1';
  signal fwd_inv8_used_i  : std_logic := '1';
  signal fwd_inv9_used_i  : std_logic := '1';
  signal fwd_inv10_used_i : std_logic := '1';
  signal fwd_inv11_used_i : std_logic := '1';
  signal scale_sch_used_i   : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch0_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch1_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch2_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch3_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch4_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch5_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch6_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch7_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch8_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch9_used_i  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch10_used_i : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal scale_sch11_used_i : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := SCALE_SCH_DEFAULT;
  signal xn_index_i : std_logic_vector(R_NFFT_MAX-1 downto 0) := (others => '0');
  signal xk_index_i : std_logic_vector(R_NFFT_MAX-1 downto 0) := (others => '0');
  signal rfd_i      : std_logic                               := '0';
  signal busy_i     : std_logic                               := '0';
  signal dv_i       : std_logic                               := '0';
  signal edone_i    : std_logic                               := '0';
  signal done_i     : std_logic                               := '0';
  signal cpv_i      : std_logic                               := '0';
  signal rfs_i      : std_logic                               := '1';
  signal dv_d       : std_logic                               := '0';
  signal xk_index_d : std_logic_vector(R_NFFT_MAX-1 downto 0) := (others => '0');
  signal cpv_d      : std_logic                               := '0';
  constant load_unload_order_same : boolean := ((R_HAS_NATURAL_OUTPUT = 1 and R_HAS_NATURAL_INPUT = 1) or (R_HAS_NATURAL_OUTPUT = 0 and R_HAS_NATURAL_INPUT = 0));
  constant load_unload_order_different : boolean := not((R_HAS_NATURAL_OUTPUT = 1 and R_HAS_NATURAL_INPUT = 1) or (R_HAS_NATURAL_OUTPUT = 0 and R_HAS_NATURAL_INPUT = 0));
begin
  has_ce : if R_HAS_CE = 1 generate
    ce_i <= CE;
  end generate;
  no_ce : if R_HAS_CE = 0 generate
    ce_i <= '1';
  end generate;
  has_sclr : if R_HAS_SCLR = 1 generate
    sclr_in <= SCLR;
  end generate;
  no_sclr : if R_HAS_SCLR = 0 generate
    sclr_in <= '0';
  end generate;
  sclr_has_nfft : if R_HAS_NFFT = 1 generate
    sclr_i <= sclr_in or (NFFT_WE and ce_i);
  end generate;
  sclr_no_nfft : if R_HAS_NFFT = 0 generate
    sclr_i <= sclr_in;
  end generate;
  get_var_nfft : if R_HAS_NFFT = 1 generate
    process(CLK)
      variable nfft_new : integer := R_NFFT_MAX;
      variable nfft_old : integer := R_NFFT_MAX;
    begin
      if rising_edge(CLK) then
        if sclr_in = '1' then
          nfft_new := R_NFFT_MAX;
        elsif ce_i = '1' and NFFT_WE = '1' then
          if NFFT < NFFT_MIN_SLV then
            nfft_new := NFFT_MIN;
          elsif NFFT > NFFT_MAX_SLV then
            nfft_new := R_NFFT_MAX;
          else
            nfft_new := conv_integer(NFFT);
          end if;
        end if;
        if nfft_new /= nfft_old then
          nfft_i      <= conv_std_logic_vector(nfft_new, 5);
          n           <= 2**nfft_new;
          run_latency <= get_run_latency(R_FAMILY, R_XDEVICEFAMILY, nfft_new, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_INPUT, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT);

          if R_USE_FLT_PT=1 then
            fp_extra_latency <= get_fp_extra_latency(R_FAMILY, R_XDEVICEFAMILY, nfft_new, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_INPUT, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT);

            unload_delay <= get_unload_delay(R_FAMILY, R_XDEVICEFAMILY, R_ARCH, R_DATA_MEM_TYPE, R_NFFT_MAX, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT, R_HAS_NATURAL_OUTPUT, nfft_new);
          end if;
          nfft_old    := nfft_new;
        end if;
      end if;
    end process;
  end generate;
  get_cp_len : if R_HAS_CYCLIC_PREFIX = 1 generate
    process(CLK)
    begin
      if rising_edge(CLK) then
        if sclr_in = '1' then
          cp_len_latched <= (others => '0');
        elsif ce_i = '1' and CP_LEN_WE = '1' then
          cp_len_latched <= CP_LEN;
        end if;
      end if;
    end process;
    process(CLK)
    begin
      if rising_edge(CLK) then
        if sclr_in = '1' then
          cp_len_used_i <= (others => '0');
        elsif ce_i = '1' and conv_integer(xn_index_i) = 3 then
          cp_len_used_i <= cp_len_latched;
        end if;
      end if;
    end process;
    process (cp_len_used_i, nfft_i)
    begin
      if C_NFFT_MAX-conv_integer(nfft_i) = 0 then
        cp_len_internal <= cp_len_used_i;
      else
        cp_len_internal <= conv_std_logic_vector((conv_integer(cp_len_used_i)/(2**((C_NFFT_MAX)-conv_integer(nfft_i)))), C_NFFT_MAX);
      end if;
    end process;
  end generate get_cp_len;
  gen_fwd_inv_used(R_CHANNELS = 1, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV, FWD_INV_WE, fwd_inv_latched, fwd_inv_used_i);
  gen_fwd_inv_used(R_CHANNELS > 1, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV0, FWD_INV0_WE, fwd_inv0_latched, fwd_inv0_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 2, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV1, FWD_INV1_WE, fwd_inv1_latched, fwd_inv1_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 3, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV2, FWD_INV2_WE, fwd_inv2_latched, fwd_inv2_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 4, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV3, FWD_INV3_WE, fwd_inv3_latched, fwd_inv3_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 5, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV4, FWD_INV4_WE, fwd_inv4_latched, fwd_inv4_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 6, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV5, FWD_INV5_WE, fwd_inv5_latched, fwd_inv5_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 7, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV6, FWD_INV6_WE, fwd_inv6_latched, fwd_inv6_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 8, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV7, FWD_INV7_WE, fwd_inv7_latched, fwd_inv7_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 9, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV8, FWD_INV8_WE, fwd_inv8_latched, fwd_inv8_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 10, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV9, FWD_INV9_WE, fwd_inv9_latched, fwd_inv9_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 11, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV10, FWD_INV10_WE, fwd_inv10_latched, fwd_inv10_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 12, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV11, FWD_INV11_WE, fwd_inv11_latched, fwd_inv11_used_i);
  gen_scale_sch_used(R_CHANNELS = 1, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH, SCALE_SCH_WE, scale_sch_latched, scale_sch_used_i);
  gen_scale_sch_used(R_CHANNELS > 1, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH0, SCALE_SCH0_WE, scale_sch0_latched, scale_sch0_used_i);
  gen_scale_sch_used(R_CHANNELS >= 2, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH1, SCALE_SCH1_WE, scale_sch1_latched, scale_sch1_used_i);
  gen_scale_sch_used(R_CHANNELS >= 3, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH2, SCALE_SCH2_WE, scale_sch2_latched, scale_sch2_used_i);
  gen_scale_sch_used(R_CHANNELS >= 4, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH3, SCALE_SCH3_WE, scale_sch3_latched, scale_sch3_used_i);
  gen_scale_sch_used(R_CHANNELS >= 5, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH4, SCALE_SCH4_WE, scale_sch4_latched, scale_sch4_used_i);
  gen_scale_sch_used(R_CHANNELS >= 6, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH5, SCALE_SCH5_WE, scale_sch5_latched, scale_sch5_used_i);
  gen_scale_sch_used(R_CHANNELS >= 7, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH6, SCALE_SCH6_WE, scale_sch6_latched, scale_sch6_used_i);
  gen_scale_sch_used(R_CHANNELS >= 8, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH7, SCALE_SCH7_WE, scale_sch7_latched, scale_sch7_used_i);
  gen_scale_sch_used(R_CHANNELS >= 9, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH8, SCALE_SCH8_WE, scale_sch8_latched, scale_sch8_used_i);
  gen_scale_sch_used(R_CHANNELS >= 10, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH9, SCALE_SCH9_WE, scale_sch9_latched, scale_sch9_used_i);
  gen_scale_sch_used(R_CHANNELS >= 11, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH10, SCALE_SCH10_WE, scale_sch10_latched, scale_sch10_used_i);
  gen_scale_sch_used(R_CHANNELS >= 12, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH11, SCALE_SCH11_WE, scale_sch11_latched, scale_sch11_used_i);
  FWD_INV_USED     <= fwd_inv_used_i;
  FWD_INV0_USED    <= fwd_inv0_used_i;
  FWD_INV1_USED    <= fwd_inv1_used_i;
  FWD_INV2_USED    <= fwd_inv2_used_i;
  FWD_INV3_USED    <= fwd_inv3_used_i;
  FWD_INV4_USED    <= fwd_inv4_used_i;
  FWD_INV5_USED    <= fwd_inv5_used_i;
  FWD_INV6_USED    <= fwd_inv6_used_i;
  FWD_INV7_USED    <= fwd_inv7_used_i;
  FWD_INV8_USED    <= fwd_inv8_used_i;
  FWD_INV9_USED    <= fwd_inv9_used_i;
  FWD_INV10_USED   <= fwd_inv10_used_i;
  FWD_INV11_USED   <= fwd_inv11_used_i;
  SCALE_SCH_USED   <= scale_sch_used_i;
  SCALE_SCH0_USED  <= scale_sch0_used_i;
  SCALE_SCH1_USED  <= scale_sch1_used_i;
  SCALE_SCH2_USED  <= scale_sch2_used_i;
  SCALE_SCH3_USED  <= scale_sch3_used_i;
  SCALE_SCH4_USED  <= scale_sch4_used_i;
  SCALE_SCH5_USED  <= scale_sch5_used_i;
  SCALE_SCH6_USED  <= scale_sch6_used_i;
  SCALE_SCH7_USED  <= scale_sch7_used_i;
  SCALE_SCH8_USED  <= scale_sch8_used_i;
  SCALE_SCH9_USED  <= scale_sch9_used_i;
  SCALE_SCH10_USED <= scale_sch10_used_i;
  SCALE_SCH11_USED <= scale_sch10_used_i;
  streaming_arch : if R_ARCH = 3 generate
    model_load : process
    begin
      xn_index_i <= (others => '0');
      rfd_i      <= '0';
      rfs_i      <= '1';
      wait on CLK until CLK = '1' and ce_i = '1' and sclr_i = '0';
      forever_load : loop
        if not (sclr_i = '0' and START = '1') then
          wait on CLK until CLK = '1' and ce_i = '1' and sclr_i = '0' and START = '1';
        end if;
        rfd_i <= '1';
        if R_HAS_CYCLIC_PREFIX = 1 then
          rfs_i <= '0';
        end if;
        xn_index_loop : for i in 0 to n-1 loop
          xn_index_i <= digit_reverse(data => conv_std_logic_vector(i, R_NFFT_MAX), arch => R_ARCH, reverse => (C_HAS_NATURAL_INPUT = 0), nfft => nfft_i, nfft_max => C_NFFT_MAX, has_nfft => 0);

          wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
          if sclr_i = '1' then
            exit xn_index_loop;
          end if;
          if R_HAS_CYCLIC_PREFIX = 1 and cp_len_internal = 0 and i = n-2 then
            rfs_i <= '1';
          end if;
        end loop xn_index_loop;
        rfd_i      <= '0';
        xn_index_i <= (others => '0');
        if sclr_i = '0' and R_HAS_CYCLIC_PREFIX = 1 and cp_len_internal > 0 then
          if cp_len_internal = 1 then
            rfs_i <= '1';
          end if;
          rfs_loop : for i in n-conv_integer(cp_len_internal) to n-1 loop
            wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
            if sclr_i = '1' then
              exit rfs_loop;
            end if;
            if i = n-2 then
              rfs_i <= '1';
            end if;
          end loop rfs_loop;
        end if;
        rfs_i <= '1';
      end loop forever_load;
    end process model_load;
    model_run : process(CLK)
      constant MAX_RUN_LATENCY    : integer          := get_run_latency(R_FAMILY, R_XDEVICEFAMILY, R_NFFT_MAX, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_INPUT, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM, R_USE_FLT_PT);
      type T_RUN_DELAY is array(1 to MAX_RUN_LATENCY) of std_logic;
      variable run_delay          : T_RUN_DELAY      := (others => '0');
      type T_CP_LENGTH_FIFO is array(1 to 8) of std_logic_vector(C_NFFT_MAX-1 downto 0);
      variable cp_length_fifo     : T_CP_LENGTH_FIFO := (others => (others => '0'));
      variable cp_length_fifo_end : integer          := 0;
      variable busy_v             : std_logic;
    begin
      if rising_edge(CLK) then
        if sclr_i = '1' then
          run_delay := (others => '0');
          if R_HAS_CYCLIC_PREFIX = 1 then
            cp_length_fifo     := (others => (others => '0'));
            cp_length_fifo_end := 0;
          end if;
        elsif ce_i = '1' then
          if C_HAS_NATURAL_INPUT = 1 then
            if xn_index_i = n/2+3 then
              run_delay := '1' & run_delay(1 to run_delay'length-1);
              if R_HAS_CYCLIC_PREFIX = 1 then
                cp_length_fifo     := cp_len_internal & cp_length_fifo(1 to cp_length_fifo'length-1);
                cp_length_fifo_end := cp_length_fifo_end + 1;
              end if;
            else
              run_delay := '0' & run_delay(1 to run_delay'length-1);
            end if;
          else
            if conv_integer(digit_reverse(data => xn_index_i, arch => 3, reverse => true, nfft => nfft_i, has_nfft => R_HAS_NFFT, nfft_max => R_NFFT_MAX)) = 3 then
              run_delay := '1' & run_delay(1 to run_delay'length-1);
              if R_HAS_CYCLIC_PREFIX = 1 then
                cp_length_fifo     := cp_len_internal & cp_length_fifo(1 to cp_length_fifo'length-1);
                cp_length_fifo_end := cp_length_fifo_end + 1;
              end if;
            else
              run_delay := '0' & run_delay(1 to run_delay'length-1);
            end if;
          end if;

          if R_HAS_CYCLIC_PREFIX = 1 then
            if dv_i = '1' and cpv_i = '0' and conv_integer(xk_index_i) = 0 then
              cp_length_fifo(cp_length_fifo_end) := (others => '0');
              cp_length_fifo_end                 := cp_length_fifo_end - 1;
            end if;
            if cp_length_fifo_end > 0 then
              cp_len_unload <= cp_length_fifo(cp_length_fifo_end);
            else
              cp_len_unload <= (others => '0');
            end if;
          end if;
        end if;
        busy_v := '0';
        if R_USE_FLT_PT=1 then
          for i in fp_extra_latency+1 to run_latency loop
            busy_v := busy_v or run_delay(i);
          end loop;
        else
          for i in 1 to run_latency loop
            busy_v := busy_v or run_delay(i);
          end loop;
        end if;
        busy_i  <= busy_v;
        edone_i <= run_delay(run_latency);
      end if;
    end process model_run;
    model_unload : process
      variable nfft_i_int : integer;
    begin
      xk_index_i <= (others => '0');
      dv_i       <= '0';
      wait on CLK until CLK = '1' and ce_i = '1' and sclr_i = '0';
      forever_unload : loop
        if not (sclr_i = '0' and edone_i = '1') then
          wait on CLK until CLK = '1' and ce_i = '1' and sclr_i = '0' and edone_i = '1';
        end if;
        dv_i       <= '1';
        nfft_i_int := conv_integer(nfft_i);
        if R_HAS_CYCLIC_PREFIX = 0 then
          cpv_i <= '0';
        else
          if conv_integer(cp_len_unload) /= 0 then
            cp_loop : for i in n-conv_integer(cp_len_unload) to n-1 loop
              cpv_i                             <= '1';
              xk_index_i(nfft_i_int-1 downto 0) <= conv_std_logic_vector(i, nfft_i_int);
              wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
              if sclr_i = '1' then
                exit cp_loop;
              end if;
            end loop cp_loop;
            cpv_i <= '0';
          else
            cpv_i <= '0';
          end if;
        end if;
        if sclr_i = '0' then
          xk_index_loop : for i in 0 to n-1 loop
            if R_HAS_NATURAL_OUTPUT = 1 then
              xk_index_i(nfft_i_int-1 downto 0) <= conv_std_logic_vector(i, nfft_i_int);
            else
              xk_index_i(nfft_i_int-1 downto 0) <= digit_reverse(conv_std_logic_vector(i, nfft_i_int), R_ARCH, true, nfft_i, R_NFFT_MAX, 0);
            end if;
            wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
            if sclr_i = '1' then
              exit xk_index_loop;
            end if;
          end loop xk_index_loop;
        end if;
        dv_i       <= '0';
        xk_index_i <= (others => '0');
      end loop forever_unload;
    end process model_unload;
  end generate streaming_arch;
  burst_archs : if R_ARCH /= 3 generate
    process
      variable first_load : boolean;
      variable nfft_i_int : integer;
    begin
      xn_index_i <= (others => '0');
      xk_index_i <= (others => '0');
      rfd_i      <= '0';
      busy_i     <= '0';
      dv_i       <= '0';
      edone_i    <= '0';
      first_load := true;
      wait on CLK until CLK = '1' and ce_i = '1' and sclr_i = '0';
      forever : loop
        if (R_ARCH = 4 or R_USE_FLT_PT=1) and load_unload_order_same then
          if dv_d /= '0' then
            wait on dv_d until dv_d = '0';
          end if;
        end if;
        if not (sclr_i = '0' and START = '1') then
          wait on CLK until (CLK = '1' and ce_i = '1' and START = '1') or sclr_i = '1';
          if sclr_i = '1' then
            first_load := true;
            next forever;
          end if;
        end if;
        rfd_i      <= '1';
        nfft_i_int := conv_integer(nfft_i);
        if load_unload_order_different and not first_load then
          dv_i <= '1';
        end if;
        xn_index_loop : for i in 0 to n-1 loop
          if R_HAS_NATURAL_INPUT = 1 then
            xn_index_i <= conv_std_logic_vector(i, R_NFFT_MAX);
          else
            xn_index_i <= digit_reverse(conv_std_logic_vector(i, R_NFFT_MAX), R_ARCH, true, nfft_i, R_NFFT_MAX, R_HAS_NFFT);
          end if;
          if load_unload_order_different and not first_load then
            if R_HAS_NATURAL_OUTPUT = 1 then
              xk_index_i(nfft_i_int-1 downto 0) <= (conv_std_logic_vector(i, nfft_i_int));
            else
              xk_index_i(nfft_i_int-1 downto 0) <= digit_reverse(conv_std_logic_vector(i, nfft_i_int), R_ARCH, true, nfft_i, R_NFFT_MAX, 0);
            end if;
          end if;
          wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
          if sclr_i = '1' then
            exit xn_index_loop;
          end if;
        end loop xn_index_loop;
        rfd_i      <= '0';
        xn_index_i <= (others => '0');
        if load_unload_order_different then
          dv_i       <= '0';
          xk_index_i <= (others => '0');
        end if;
        if sclr_i = '0' then
          busy_i <= '1';
          run_latency_loop : for i in 1 to run_latency - 1 loop
            wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
            if sclr_i = '1' then
              exit run_latency_loop;
            end if;
          end loop run_latency_loop;
        end if;
        if sclr_i = '0' then
          edone_i <= '1';
          wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
        end if;
        edone_i <= '0';
        busy_i  <= '0';
        if sclr_i = '0' and load_unload_order_same then
          if not (UNLOAD = '1') then
            wait on CLK until CLK = '1' and ((ce_i = '1' and UNLOAD = '1') or sclr_i = '1');
          end if;
          if sclr_i = '0' then
            dv_i <= '1';
            if R_HAS_CYCLIC_PREFIX = 0 then
              cpv_i <= '0';
            else
              if conv_integer(cp_len_internal) /= 0 then
                cp_loop : for i in n-conv_integer(cp_len_internal) to n-1 loop
                  cpv_i                             <= '1';
                  xk_index_i(nfft_i_int-1 downto 0) <= conv_std_logic_vector(i, nfft_i_int);
                  wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
                  if sclr_i = '1' then
                    exit cp_loop;
                  end if;
                end loop cp_loop;
                cpv_i <= '0';
              else
                cpv_i <= '0';
              end if;
            end if;
            if sclr_i = '0' then
              xk_index_loop : for i in 0 to n-1 loop
                if R_HAS_NATURAL_OUTPUT = 0 then
                  xk_index_i(nfft_i_int-1 downto 0) <= digit_reverse(conv_std_logic_vector(i, nfft_i_int), R_ARCH, true, nfft_i, R_NFFT_MAX, 0);
                else
                  xk_index_i(nfft_i_int-1 downto 0) <= conv_std_logic_vector(i, nfft_i_int);
                end if;
                wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
                if sclr_i = '1' then
                  exit xk_index_loop;
                end if;
              end loop xk_index_loop;
            end if;
            dv_i       <= '0';
            xk_index_i <= (others => '0');
            if (R_ARCH = 4 or R_USE_FLT_PT=1) and dv_d /= '1' and sclr_i = '0' then
              wait on CLK until CLK = '1' and ((ce_i = '1' and dv_d = '1') or sclr_i = '1');
            end if;
          end if;
        end if;
        if sclr_i = '1' then
          first_load := true;
        else
          first_load := false;
        end if;
      end loop forever;
    end process;
  end generate burst_archs;
  process
  begin
    done_i <= '0';
    wait on CLK until CLK = '1' and ((ce_i = '1' and edone_i = '1') or sclr_i = '1');
    if sclr_i = '0' then
      done_i <= '1';
      wait on CLK until CLK = '1' and (ce_i = '1' or sclr_i = '1');
    end if;
    done_i <= '0';
  end process;
  need_unload_delay : if MAX_UNLOAD_DELAY > 0 generate
    process(CLK)
      type T_DV_DELAY is array(1 to MAX_UNLOAD_DELAY) of std_logic;
      type T_XK_INDEX_DELAY is array (1 to MAX_UNLOAD_DELAY) of std_logic_vector(R_NFFT_MAX-1 downto 0);
      variable dv_delay       : T_DV_DELAY       := (others => '0');
      variable xk_index_delay : T_XK_INDEX_DELAY := (others => (others => '0'));
      variable cpv_delay      : T_DV_DELAY       := (others => '0');
    begin
      if CLK'event and CLK = '1' then
        if sclr_i = '1' then
          dv_delay       := (others => '0');
          xk_index_delay := (others => (others => '0'));
          cpv_delay      := (others => '0');
        elsif ce_i = '1' then
          dv_delay       := dv_i & dv_delay(1 to MAX_UNLOAD_DELAY-1);
          xk_index_delay := xk_index_i & xk_index_delay(1 to MAX_UNLOAD_DELAY-1);
          cpv_delay      := cpv_i & cpv_delay(1 to MAX_UNLOAD_DELAY-1);
        end if;
        dv_d       <= dv_delay(unload_delay);
        xk_index_d <= xk_index_delay(unload_delay);
        cpv_d      <= cpv_delay(unload_delay);
      end if;
    end process;
  end generate;
  no_unload_delay : if MAX_UNLOAD_DELAY < 1 generate
    dv_d       <= dv_i;
    xk_index_d <= xk_index_i;
    cpv_d      <= cpv_i;
  end generate;
  XN_INDEX <= xn_index_i;
  XK_INDEX <= xk_index_d;
  RFD      <= rfd_i;
  BUSY     <= busy_i;
  DV       <= dv_d;
  EDONE    <= edone_i;
  DONE     <= done_i;
  CPV      <= cpv_d;
  RFS      <= rfs_i;
end behavioral;
