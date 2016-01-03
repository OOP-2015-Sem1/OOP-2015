
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

LIBRARY xilinxcorelib;
USE xilinxcorelib.prims_utils_v9_1.derived;

LIBRARY work;
USE work.timing_model_pkg.ALL;
USE work.timing_pkg.ALL;


ENTITY timing_model IS
  GENERIC (
    C_FAMILY             : STRING  := "virtex4";
    C_XDEVICEFAMILY      : STRING  := "virtex4";
    C_CHANNELS           : INTEGER := 1;
    C_NFFT_MAX           : INTEGER := 10;
    C_ARCH               : INTEGER := 1;
    C_HAS_NFFT           : INTEGER := 0;
    C_INPUT_WIDTH        : INTEGER := 16;
    C_TWIDDLE_WIDTH      : INTEGER := 16;
    C_OUTPUT_WIDTH       : INTEGER := 16;
    C_HAS_CE             : INTEGER := 0;
    C_HAS_SCLR           : INTEGER := 0;
    C_HAS_OVFLO          : INTEGER := 0;
    C_HAS_SCALING        : INTEGER := 1;
    C_HAS_BFP            : INTEGER := 0;
    C_HAS_ROUNDING       : INTEGER := 0;
    C_DATA_MEM_TYPE      : INTEGER := 1;
    C_TWIDDLE_MEM_TYPE   : INTEGER := 1;
    C_BRAM_STAGES        : INTEGER := 0;
    C_REORDER_MEM_TYPE   : INTEGER := 1;
    C_HAS_NATURAL_OUTPUT : INTEGER := 0;
    C_HAS_CYCLIC_PREFIX  : INTEGER := 0;
    C_OPTIMIZE_GOAL      : INTEGER := 0;
    C_USE_HYBRID_RAM     : INTEGER := 0;
    C_FAST_CMPY          : INTEGER := 0;
    C_OPTIMIZE           : INTEGER := 0
    );
  PORT (
    START     : IN STD_LOGIC                               := '0';
    UNLOAD    : IN STD_LOGIC                               := '0';
    NFFT      : IN STD_LOGIC_VECTOR(4 DOWNTO 0)            := (OTHERS => '0');
    NFFT_WE   : IN STD_LOGIC                               := '0';
    SCLR      : IN STD_LOGIC                               := '0';
    CE        : IN STD_LOGIC                               := '1';
    CLK       : IN STD_LOGIC                               := '0';
    CP_LEN    : IN STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');
    CP_LEN_WE : IN STD_LOGIC                               := '0';

    FWD_INV        : IN STD_LOGIC := '1';
    FWD_INV_WE     : IN STD_LOGIC := '0';
    FWD_INV0       : IN STD_LOGIC := '1';
    FWD_INV0_WE    : IN STD_LOGIC := '0';
    FWD_INV1       : IN STD_LOGIC := '1';
    FWD_INV1_WE    : IN STD_LOGIC := '0';
    FWD_INV2       : IN STD_LOGIC := '1';
    FWD_INV2_WE    : IN STD_LOGIC := '0';
    FWD_INV3       : IN STD_LOGIC := '1';
    FWD_INV3_WE    : IN STD_LOGIC := '0';
    FWD_INV4       : IN STD_LOGIC := '1';
    FWD_INV4_WE    : IN STD_LOGIC := '0';
    FWD_INV5       : IN STD_LOGIC := '1';
    FWD_INV5_WE    : IN STD_LOGIC := '0';
    FWD_INV6       : IN STD_LOGIC := '1';
    FWD_INV6_WE    : IN STD_LOGIC := '0';
    FWD_INV7       : IN STD_LOGIC := '1';
    FWD_INV7_WE    : IN STD_LOGIC := '0';
    FWD_INV8       : IN STD_LOGIC := '1';
    FWD_INV8_WE    : IN STD_LOGIC := '0';
    FWD_INV9       : IN STD_LOGIC := '1';
    FWD_INV9_WE    : IN STD_LOGIC := '0';
    FWD_INV10      : IN STD_LOGIC := '1';
    FWD_INV10_WE   : IN STD_LOGIC := '0';
    FWD_INV11      : IN STD_LOGIC := '1';
    FWD_INV11_WE   : IN STD_LOGIC := '0';

    SCALE_SCH      : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH_WE   : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH0     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH0_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH1     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH1_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH2     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH2_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH3     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH3_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH4     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH4_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH5     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH5_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH6     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH6_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH7     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH7_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH8     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH8_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH9     : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH9_WE  : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH10    : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH10_WE : IN STD_LOGIC                                                                     := '0';
    SCALE_SCH11    : IN STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := (OTHERS => '0');
    SCALE_SCH11_WE : IN STD_LOGIC                                                                     := '0';

    XN_INDEX : OUT STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0);
    XK_INDEX : OUT STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0);
    RFD      : OUT STD_LOGIC;
    BUSY     : OUT STD_LOGIC;
    DV       : OUT STD_LOGIC;
    EDONE    : OUT STD_LOGIC;
    DONE     : OUT STD_LOGIC;
    CPV      : OUT STD_LOGIC;
    RFS      : OUT STD_LOGIC;

    FWD_INV_USED     : OUT STD_LOGIC;
    FWD_INV0_USED    : OUT STD_LOGIC;
    FWD_INV1_USED    : OUT STD_LOGIC;
    FWD_INV2_USED    : OUT STD_LOGIC;
    FWD_INV3_USED    : OUT STD_LOGIC;
    FWD_INV4_USED    : OUT STD_LOGIC;
    FWD_INV5_USED    : OUT STD_LOGIC;
    FWD_INV6_USED    : OUT STD_LOGIC;
    FWD_INV7_USED    : OUT STD_LOGIC;
    FWD_INV8_USED    : OUT STD_LOGIC;
    FWD_INV9_USED    : OUT STD_LOGIC;
    FWD_INV10_USED   : OUT STD_LOGIC;
    FWD_INV11_USED   : OUT STD_LOGIC;

    SCALE_SCH_USED   : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH0_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH1_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH2_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH3_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH4_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH5_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH6_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH7_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH8_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH9_USED  : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH10_USED : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0);
    SCALE_SCH11_USED : OUT STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0)
    );
END timing_model;


ARCHITECTURE behavioral OF timing_model IS

  FUNCTION digit_reverse(data : STD_LOGIC_VECTOR; C_ARCH : INTEGER) RETURN STD_LOGIC_VECTOR IS
    CONSTANT length : INTEGER := data'LENGTH;
    VARIABLE result : STD_LOGIC_VECTOR(length-1 DOWNTO 0);
  BEGIN
    IF C_ARCH = 1 THEN
      FOR i IN 0 TO (length/2)-1 LOOP
        result(2*i)      := data(length-(2*i)-2);
        result(2*i+1)    := data(length-(2*i)-1);
      END LOOP;
      IF length MOD 2 = 1 THEN
        result(length-1) := data(0);
      END IF;
    ELSE
      FOR i IN 0 TO length-1 LOOP
        result(i)        := data(length-i-1);
      END LOOP;
    END IF;
    RETURN result;
  END digit_reverse;

  PROCEDURE gen_fwd_inv_used(
    CONSTANT CHANNEL_EXISTS    : IN    BOOLEAN;
    CONSTANT FWD_INV_DEFAULT   : IN    STD_LOGIC;
    SIGNAL   CLK               : IN    STD_LOGIC;
    SIGNAL   ce                : IN    STD_LOGIC;
    SIGNAL   sclr              : IN    STD_LOGIC;
    SIGNAL   xn_index          : IN    STD_LOGIC_VECTOR;
    SIGNAL   FWD_INV           : IN    STD_LOGIC;
    SIGNAL   FWD_INV_WE        : IN    STD_LOGIC;
    SIGNAL   fwd_inv_latched   : INOUT STD_LOGIC;
    SIGNAL   FWD_INV_USED      : OUT   STD_LOGIC
    ) IS
  BEGIN
    IF rising_edge(CLK) THEN
      IF CHANNEL_EXISTS THEN

        IF sclr = '1' THEN
          fwd_inv_latched <= FWD_INV_DEFAULT;
        ELSIF ce = '1' AND FWD_INV_WE = '1' THEN
          fwd_inv_latched <= FWD_INV;
        END IF;

        IF sclr = '1' THEN
          FWD_INV_USED   <= FWD_INV_DEFAULT;
        ELSIF ce = '1' AND conv_integer(xn_index) = 3 THEN
          FWD_INV_USED <= fwd_inv_latched;
        END IF;

      ELSE
        FWD_INV_USED <= FWD_INV_DEFAULT;
      END IF;

    END IF;
  END gen_fwd_inv_used;

  PROCEDURE gen_scale_sch_used(
    CONSTANT CHANNEL_EXISTS    : IN    BOOLEAN;
    CONSTANT SCALE_SCH_DEFAULT : IN    STD_LOGIC_VECTOR;
    SIGNAL   CLK               : IN    STD_LOGIC;
    SIGNAL   ce                : IN    STD_LOGIC;
    SIGNAL   sclr              : IN    STD_LOGIC;
    SIGNAL   xn_index          : IN    STD_LOGIC_VECTOR;
    SIGNAL   SCALE_SCH         : IN    STD_LOGIC_VECTOR;
    SIGNAL   SCALE_SCH_WE      : IN    STD_LOGIC;
    SIGNAL   scale_sch_latched : INOUT STD_LOGIC_VECTOR;
    SIGNAL   SCALE_SCH_USED    : OUT   STD_LOGIC_VECTOR
    ) IS
  BEGIN
    IF rising_edge(CLK) THEN
      IF CHANNEL_EXISTS THEN

        IF sclr = '1' THEN
          scale_sch_latched <= SCALE_SCH_DEFAULT;
        ELSIF ce = '1' AND SCALE_SCH_WE = '1' THEN
          scale_sch_latched <= SCALE_SCH;
        END IF;

        IF sclr = '1' THEN
          SCALE_SCH_USED   <= SCALE_SCH_DEFAULT;
        ELSIF ce = '1' AND conv_integer(xn_index) = 3 THEN
          SCALE_SCH_USED <= scale_sch_latched;
        END IF;

      ELSE
        SCALE_SCH_USED <= SCALE_SCH_DEFAULT;
      END IF;

    END IF;
  END gen_scale_sch_used;

  FUNCTION default_scale_sch(C_ARCH, C_NFFT_MAX : INTEGER) RETURN STD_LOGIC_VECTOR IS
    CONSTANT POW4 : BOOLEAN := ((C_NFFT_MAX MOD 2) = 0);
    CONSTANT WIDTH : INTEGER := (C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2));
    VARIABLE result : STD_LOGIC_VECTOR(WIDTH-1 DOWNTO 0);

  BEGIN
    CASE C_ARCH IS
      WHEN 1 | 3 =>
        IF POW4 THEN
          result(WIDTH-1 DOWNTO WIDTH-2) := "10";
        ELSE
          result(WIDTH-1 DOWNTO WIDTH-2) := "01";
        END IF;
        FOR i IN WIDTH/2-2 DOWNTO 0 LOOP
          result(i*2+1 DOWNTO i*2) := "10";
        END LOOP;
      WHEN 2 | 4 =>
        FOR i IN WIDTH/2-1 DOWNTO 0 LOOP
          result(i*2+1 DOWNTO i*2) := "01";
        END LOOP;
      WHEN OTHERS =>
        REPORT "ERROR: timing_model.vhd: default_scale_sch: unknown architecture number" SEVERITY FAILURE;
    END CASE;
    RETURN result;
  END default_scale_sch;

  FUNCTION calc_memory_depth (arch, point_size : INTEGER) RETURN INTEGER IS
    CONSTANT R4_MEMORY_DEPTH     : INTEGER := C_NFFT_MAX - 2;
    CONSTANT R2_MEMORY_DEPTH     : INTEGER := C_NFFT_MAX - 1;
    CONSTANT R2LITE_MEMORY_DEPTH : INTEGER := C_NFFT_MAX;
  BEGIN
    IF arch = 1 THEN
      RETURN R4_MEMORY_DEPTH;
    ELSIF arch = 2 THEN
      RETURN R2_MEMORY_DEPTH;
    ELSIF arch = 4 THEN
      RETURN R2LITE_MEMORY_DEPTH;
    ELSE
      RETURN 0;
    END IF;
  END FUNCTION calc_memory_depth;

  CONSTANT MEMORY_BANK_DEPTH : INTEGER := calc_memory_depth(C_ARCH, C_NFFT_MAX);


  CONSTANT R_FAMILY             : STRING  := C_FAMILY;
  CONSTANT R_XDEVICEFAMILY      : STRING  := C_XDEVICEFAMILY;
  CONSTANT R_CHANNELS           : INTEGER := when_else(C_ARCH = 4 OR C_ARCH = 2 OR C_ARCH = 1, C_CHANNELS, 1);
  CONSTANT R_NFFT_MAX           : INTEGER := C_NFFT_MAX;
  CONSTANT R_ARCH               : INTEGER := C_ARCH;
  CONSTANT R_HAS_NFFT           : INTEGER := C_HAS_NFFT;
  CONSTANT R_INPUT_WIDTH        : INTEGER := C_INPUT_WIDTH;
  CONSTANT R_TWIDDLE_WIDTH      : INTEGER := C_TWIDDLE_WIDTH + 1;
  CONSTANT R_OUTPUT_WIDTH       : INTEGER := C_OUTPUT_WIDTH;
  CONSTANT R_HAS_SCALING        : INTEGER := C_HAS_SCALING;
  CONSTANT R_HAS_BFP            : INTEGER := when_else(C_ARCH = 3, 0, C_HAS_BFP);
  CONSTANT R_HAS_ROUNDING       : INTEGER := C_HAS_ROUNDING;
  CONSTANT R_HAS_CE             : INTEGER := C_HAS_CE;
  CONSTANT R_HAS_SCLR           : INTEGER := C_HAS_SCLR;
  CONSTANT R_HAS_OVFLO          : INTEGER := when_else(C_HAS_SCALING = 1 AND (C_HAS_BFP = 0 OR C_ARCH = 3), C_HAS_OVFLO, 0);
  CONSTANT R_HAS_NATURAL_OUTPUT : INTEGER := C_HAS_NATURAL_OUTPUT;
  CONSTANT R_DATA_MEM_TYPE      : INTEGER := when_else(C_ARCH = 3, 1, C_DATA_MEM_TYPE);
  CONSTANT R_TWIDDLE_MEM_TYPE   : INTEGER := when_else(C_ARCH = 3, 1, C_TWIDDLE_MEM_TYPE);
  CONSTANT R_BRAM_STAGES        : INTEGER := when_else(C_ARCH = 3, C_BRAM_STAGES, 0);
  CONSTANT R_REORDER_MEM_TYPE   : INTEGER := when_else(C_ARCH = 3, when_else(C_HAS_NATURAL_OUTPUT = 1, C_REORDER_MEM_TYPE, 1), 1);
  CONSTANT R_HAS_CYCLIC_PREFIX  : INTEGER := when_else(R_HAS_NATURAL_OUTPUT = 1, C_HAS_CYCLIC_PREFIX, 0);
  CONSTANT R_OPTIMIZE_GOAL      : INTEGER := 1;
  CONSTANT R_USE_HYBRID_RAM : INTEGER := when_else(C_USE_HYBRID_RAM = 0, 0, boolean'pos(resolve_hybrid_ram_use(R_FAMILY, R_XDEVICEFAMILY, R_ARCH, R_NFFT_MAX, R_DATA_MEM_TYPE, R_OUTPUT_WIDTH, R_BRAM_STAGES, R_HAS_SCALING, R_INPUT_WIDTH, R_REORDER_MEM_TYPE, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, C_USE_HYBRID_RAM)));
    CONSTANT R_FAST_CMPY          : INTEGER := when_else(NOT (derived(C_FAMILY, "virtex4") OR derived(C_FAMILY, "virtex5") OR derived(C_XDEVICEFAMILY, "spartan3adsp")), 0,
                                              C_FAST_CMPY);
  CONSTANT R_OPTIMIZE           : INTEGER := when_else(NOT (derived(C_FAMILY, "virtex4") OR derived(C_FAMILY, "virtex5") OR derived(C_XDEVICEFAMILY, "spartan3adsp")), 0,
                                             when_else(derived(C_FAMILY, "virtex4") AND C_OUTPUT_WIDTH > 30, 0, C_OPTIMIZE));

  CONSTANT R_FAST_BFY : INTEGER := R_OPTIMIZE;

  CONSTANT NFFT_MAX_SLV : STD_LOGIC_VECTOR(4 DOWNTO 0) := conv_std_logic_vector(R_NFFT_MAX, 5);
  CONSTANT NFFT_MIN     : INTEGER                      := get_nfft_min(R_ARCH, R_HAS_NFFT, R_NFFT_MAX);
  CONSTANT NFFT_MIN_SLV : STD_LOGIC_VECTOR(4 DOWNTO 0) := conv_std_logic_vector(NFFT_MIN, 5);

  CONSTANT SCALE_SCH_DEFAULT : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := default_scale_sch(R_ARCH, R_NFFT_MAX);

  CONSTANT FWD_INV_DEFAULT : STD_LOGIC := '1';

  CONSTANT UNLOAD_DELAY : INTEGER := get_unload_delay(R_FAMILY, R_XDEVICEFAMILY, R_ARCH, R_DATA_MEM_TYPE, R_NFFT_MAX, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM);

  SIGNAL ce_i    : STD_LOGIC;
  SIGNAL sclr_in : STD_LOGIC;
  SIGNAL sclr_i  : STD_LOGIC;

  SIGNAL nfft_i      : STD_LOGIC_VECTOR(4 DOWNTO 0) := NFFT_MAX_SLV;
  SIGNAL n           : INTEGER                      := 2**R_NFFT_MAX;
  SIGNAL run_latency : INTEGER                      := get_run_latency(R_FAMILY, R_XDEVICEFAMILY, R_NFFT_MAX, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM);

  SIGNAL cp_len_latched    : STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');
  SIGNAL cp_len_used_i : STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');

  SIGNAL cp_len_internal : STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');

  SIGNAL cp_len_unload : STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');

  SIGNAL fwd_inv_latched   : STD_LOGIC := '1';
  SIGNAL fwd_inv0_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv1_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv2_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv3_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv4_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv5_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv6_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv7_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv8_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv9_latched  : STD_LOGIC := '1';
  SIGNAL fwd_inv10_latched : STD_LOGIC := '1';
  SIGNAL fwd_inv11_latched : STD_LOGIC := '1';

  SIGNAL scale_sch_latched   : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch0_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch1_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch2_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch3_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch4_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch5_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch6_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch7_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch8_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch9_latched  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch10_latched : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch11_latched : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;

  SIGNAL fwd_inv_used_i   : STD_LOGIC := '1';
  SIGNAL fwd_inv0_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv1_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv2_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv3_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv4_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv5_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv6_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv7_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv8_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv9_used_i  : STD_LOGIC := '1';
  SIGNAL fwd_inv10_used_i : STD_LOGIC := '1';
  SIGNAL fwd_inv11_used_i : STD_LOGIC := '1';

  SIGNAL scale_sch_used_i   : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch0_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch1_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch2_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch3_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch4_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch5_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch6_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch7_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch8_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch9_used_i  : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch10_used_i : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;
  SIGNAL scale_sch11_used_i : STD_LOGIC_VECTOR((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH MOD 2))-1 DOWNTO 0) := SCALE_SCH_DEFAULT;

  SIGNAL xn_index_i : STD_LOGIC_VECTOR(R_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');
  SIGNAL xk_index_i : STD_LOGIC_VECTOR(R_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');
  SIGNAL rfd_i      : STD_LOGIC                               := '0';
  SIGNAL busy_i     : STD_LOGIC                               := '0';
  SIGNAL dv_i       : STD_LOGIC                               := '0';
  SIGNAL edone_i    : STD_LOGIC                               := '0';
  SIGNAL done_i     : STD_LOGIC                               := '0';
  SIGNAL cpv_i      : STD_LOGIC                               := '0';
  SIGNAL rfs_i      : STD_LOGIC                               := '1';

  SIGNAL dv_d       : STD_LOGIC                               := '0';
  SIGNAL xk_index_d : STD_LOGIC_VECTOR(R_NFFT_MAX-1 DOWNTO 0) := (OTHERS => '0');
  SIGNAL cpv_d      : STD_LOGIC                               := '0';


BEGIN

  has_ce : IF R_HAS_CE = 1 GENERATE
    ce_i <= CE;
  END GENERATE;
  no_ce  : IF R_HAS_CE = 0 GENERATE
    ce_i <= '1';
  END GENERATE;

  has_sclr : IF R_HAS_SCLR = 1 GENERATE
    sclr_in <= SCLR;
  END GENERATE;
  no_sclr  : IF R_HAS_SCLR = 0 GENERATE
    sclr_in <= '0';
  END GENERATE;

  sclr_has_nfft : IF R_HAS_NFFT = 1 GENERATE
    sclr_i <= sclr_in OR (NFFT_WE AND ce_i);
  END GENERATE;
  sclr_no_nfft  : IF R_HAS_NFFT = 0 GENERATE
    sclr_i <= sclr_in;
  END GENERATE;

  get_var_nfft : IF R_HAS_NFFT = 1 GENERATE
    PROCESS(CLK)
      VARIABLE nfft_new : INTEGER := R_NFFT_MAX;
      VARIABLE nfft_old : INTEGER := R_NFFT_MAX;
    BEGIN
      IF rising_edge(CLK) THEN
        IF sclr_in = '1' THEN
          nfft_new   := R_NFFT_MAX;
        ELSIF ce_i = '1' AND NFFT_WE = '1' THEN
          IF NFFT < NFFT_MIN_SLV THEN
            nfft_new := NFFT_MIN;
          ELSIF NFFT > NFFT_MAX_SLV THEN
            nfft_new := R_NFFT_MAX;
          ELSE
            nfft_new := conv_integer(NFFT);
          END IF;
        END IF;
        IF nfft_new /= nfft_old THEN
          nfft_i      <= conv_std_logic_vector(nfft_new, 5);
          n           <= 2**nfft_new;
          run_latency <= get_run_latency(R_FAMILY, R_XDEVICEFAMILY, nfft_new, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM);
          nfft_old   := nfft_new;
        END IF;
      END IF;
    END PROCESS;
  END GENERATE;

  get_cp_len : IF R_HAS_CYCLIC_PREFIX = 1 GENERATE

    PROCESS(CLK)
    BEGIN
      IF rising_edge(CLK) THEN
        IF sclr_in = '1' THEN
          cp_len_latched <= (OTHERS => '0');
        ELSIF ce_i = '1' AND CP_LEN_WE = '1' THEN
          cp_len_latched <= CP_LEN;
        END IF;
      END IF;
    END PROCESS;

    PROCESS(CLK)
    BEGIN
      IF rising_edge(CLK) THEN
        IF sclr_in = '1' THEN
          cp_len_used_i <= (OTHERS => '0');
        ELSIF ce_i = '1' AND conv_integer(xn_index_i) = 3 THEN
          cp_len_used_i <= cp_len_latched;
        END IF;
      END IF;
    END PROCESS;

    PROCESS (cp_len_used_i, nfft_i)
    BEGIN
      IF C_NFFT_MAX-conv_integer(nfft_i) = 0 THEN
        cp_len_internal <= cp_len_used_i;
      ELSE
        cp_len_internal <= conv_std_logic_vector((conv_integer(cp_len_used_i)/(2**((C_NFFT_MAX)-conv_integer(nfft_i)))),C_NFFT_MAX);
      END IF;
    END PROCESS;

  END GENERATE get_cp_len;





  gen_fwd_inv_used(R_CHANNELS =  1,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV,   FWD_INV_WE,   fwd_inv_latched,   fwd_inv_used_i  );
  gen_fwd_inv_used(R_CHANNELS >  1,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV0,  FWD_INV0_WE,  fwd_inv0_latched,  fwd_inv0_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 2,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV1,  FWD_INV1_WE,  fwd_inv1_latched,  fwd_inv1_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 3,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV2,  FWD_INV2_WE,  fwd_inv2_latched,  fwd_inv2_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 4,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV3,  FWD_INV3_WE,  fwd_inv3_latched,  fwd_inv3_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 5,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV4,  FWD_INV4_WE,  fwd_inv4_latched,  fwd_inv4_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 6,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV5,  FWD_INV5_WE,  fwd_inv5_latched,  fwd_inv5_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 7,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV6,  FWD_INV6_WE,  fwd_inv6_latched,  fwd_inv6_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 8,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV7,  FWD_INV7_WE,  fwd_inv7_latched,  fwd_inv7_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 9,  FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV8,  FWD_INV8_WE,  fwd_inv8_latched,  fwd_inv8_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 10, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV9,  FWD_INV9_WE,  fwd_inv9_latched,  fwd_inv9_used_i );
  gen_fwd_inv_used(R_CHANNELS >= 11, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV10, FWD_INV10_WE, fwd_inv10_latched, fwd_inv10_used_i);
  gen_fwd_inv_used(R_CHANNELS >= 12, FWD_INV_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, FWD_INV11, FWD_INV11_WE, fwd_inv11_latched, fwd_inv11_used_i);

  gen_scale_sch_used(R_CHANNELS =  1,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH,   SCALE_SCH_WE,   scale_sch_latched,   scale_sch_used_i  );
  gen_scale_sch_used(R_CHANNELS >  1,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH0,  SCALE_SCH0_WE,  scale_sch0_latched,  scale_sch0_used_i );
  gen_scale_sch_used(R_CHANNELS >= 2,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH1,  SCALE_SCH1_WE,  scale_sch1_latched,  scale_sch1_used_i );
  gen_scale_sch_used(R_CHANNELS >= 3,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH2,  SCALE_SCH2_WE,  scale_sch2_latched,  scale_sch2_used_i );
  gen_scale_sch_used(R_CHANNELS >= 4,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH3,  SCALE_SCH3_WE,  scale_sch3_latched,  scale_sch3_used_i );
  gen_scale_sch_used(R_CHANNELS >= 5,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH4,  SCALE_SCH4_WE,  scale_sch4_latched,  scale_sch4_used_i );
  gen_scale_sch_used(R_CHANNELS >= 6,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH5,  SCALE_SCH5_WE,  scale_sch5_latched,  scale_sch5_used_i );
  gen_scale_sch_used(R_CHANNELS >= 7,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH6,  SCALE_SCH6_WE,  scale_sch6_latched,  scale_sch6_used_i );
  gen_scale_sch_used(R_CHANNELS >= 8,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH7,  SCALE_SCH7_WE,  scale_sch7_latched,  scale_sch7_used_i );
  gen_scale_sch_used(R_CHANNELS >= 9,  SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH8,  SCALE_SCH8_WE,  scale_sch8_latched,  scale_sch8_used_i );
  gen_scale_sch_used(R_CHANNELS >= 10, SCALE_SCH_DEFAULT, CLK, ce_i, sclr_in, xn_index_i, SCALE_SCH9,  SCALE_SCH9_WE,  scale_sch9_latched,  scale_sch9_used_i );
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



  streaming_arch : IF R_ARCH = 3 GENERATE

    model_load : PROCESS
    BEGIN
      xn_index_i <= (OTHERS => '0');
      rfd_i      <= '0';
      rfs_i      <= '1';

      WAIT ON CLK UNTIL CLK = '1' AND ce_i = '1' AND sclr_i = '0';

      forever_load : LOOP

        IF NOT (sclr_i = '0' AND START = '1') THEN
          WAIT ON CLK UNTIL CLK = '1' AND ce_i = '1' AND sclr_i = '0' AND START = '1';
        END IF;

        rfd_i        <= '1';
        IF R_HAS_CYCLIC_PREFIX = 1 THEN
          rfs_i        <= '0';
        END IF;
        xn_index_loop : FOR i IN 0 TO n-1 LOOP
          xn_index_i <= conv_std_logic_vector(i, R_NFFT_MAX);
          WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
          IF sclr_i = '1' THEN
            EXIT xn_index_loop;
          END IF;
          IF R_HAS_CYCLIC_PREFIX = 1 AND cp_len_internal = 0 AND i = n-2 THEN
            rfs_i <= '1';
          END IF;
        END LOOP xn_index_loop;

        rfd_i      <= '0';
        xn_index_i <= (OTHERS => '0');

        IF sclr_i = '0' AND R_HAS_CYCLIC_PREFIX = 1 AND cp_len_internal > 0 THEN
          IF cp_len_internal = 1 THEN
            rfs_i <= '1';
          END IF;
          rfs_loop : FOR i IN n-conv_integer(cp_len_internal) TO n-1 LOOP
            WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
            IF sclr_i = '1' THEN
              EXIT rfs_loop;
            END IF;
            IF i = n-2 THEN
              rfs_i <= '1';
            END IF;
          END LOOP rfs_loop;
        END IF;

        rfs_i <= '1';

      END LOOP forever_load;
    END PROCESS model_load;

    model_run : PROCESS(CLK)
      CONSTANT MAX_RUN_LATENCY : INTEGER     := get_run_latency(R_FAMILY, R_XDEVICEFAMILY, R_NFFT_MAX, R_ARCH, R_DATA_MEM_TYPE, R_HAS_ROUNDING, R_HAS_SCALING, R_NFFT_MAX, R_OUTPUT_WIDTH, R_TWIDDLE_MEM_TYPE, R_TWIDDLE_WIDTH, R_FAST_BFY, R_FAST_CMPY, R_HAS_NFFT, R_HAS_BFP, R_BRAM_STAGES, R_REORDER_MEM_TYPE, R_INPUT_WIDTH, R_HAS_NATURAL_OUTPUT, R_OPTIMIZE_GOAL, R_USE_HYBRID_RAM);
      TYPE T_RUN_DELAY IS ARRAY(1 TO MAX_RUN_LATENCY) OF STD_LOGIC;
      VARIABLE run_delay       : T_RUN_DELAY := (OTHERS => '0');
      TYPE T_CP_LENGTH_FIFO IS ARRAY(1 TO 8) OF STD_LOGIC_VECTOR(C_NFFT_MAX-1 DOWNTO 0);
      VARIABLE cp_length_fifo  : T_CP_LENGTH_FIFO := (OTHERS => (OTHERS => '0'));
      VARIABLE cp_length_fifo_end : INTEGER := 0;
      VARIABLE busy_v          : STD_LOGIC;
    BEGIN
      IF rising_edge(CLK) THEN
        IF sclr_i = '1' THEN
          run_delay   := (OTHERS => '0');
          IF R_HAS_CYCLIC_PREFIX = 1 THEN
            cp_length_fifo := (OTHERS => (OTHERS => '0'));
            cp_length_fifo_end := 0;
          END IF;
        ELSIF ce_i = '1' THEN
          IF conv_integer(xn_index_i) = n/2+3 THEN
            run_delay := '1' & run_delay(1 TO run_delay'LENGTH-1);
            IF R_HAS_CYCLIC_PREFIX = 1 THEN
              cp_length_fifo := cp_len_internal & cp_length_fifo(1 TO cp_length_fifo'LENGTH-1);
              cp_length_fifo_end := cp_length_fifo_end + 1;
            END IF;
          ELSE
            run_delay := '0' & run_delay(1 TO run_delay'LENGTH-1);
          END IF;
          IF R_HAS_CYCLIC_PREFIX = 1 THEN
            IF dv_i = '1' AND cpv_i = '0' AND conv_integer(xk_index_i) = 0 THEN
              cp_length_fifo(cp_length_fifo_end) := (OTHERS => '0');
              cp_length_fifo_end := cp_length_fifo_end - 1;
            END IF;
            IF cp_length_fifo_end > 0 THEN
              cp_len_unload <= cp_length_fifo(cp_length_fifo_end);
            ELSE
              cp_len_unload <= (OTHERS => '0');
            END IF;
          END IF;
        END IF;
        busy_v        := '0';
        FOR i IN 1 TO run_latency LOOP
          busy_v      := busy_v OR run_delay(i);
        END LOOP;
        busy_i  <= busy_v;
        edone_i <= run_delay(run_latency);
      END IF;
    END PROCESS model_run;

    model_unload : PROCESS
      VARIABLE nfft_i_int : INTEGER;
    BEGIN
      xk_index_i <= (OTHERS => '0');
      dv_i       <= '0';

      WAIT ON CLK UNTIL CLK = '1' AND ce_i = '1' AND sclr_i = '0';

      forever_unload : LOOP

        IF NOT (sclr_i = '0' AND edone_i = '1') THEN
          WAIT ON CLK UNTIL CLK = '1' AND ce_i = '1' AND sclr_i = '0' AND edone_i = '1';
        END IF;

        dv_i                                  <= '1';
        nfft_i_int := conv_integer(nfft_i);

        IF R_HAS_CYCLIC_PREFIX = 0 THEN
          cpv_i <= '0';
        ELSE
          IF conv_integer(cp_len_unload) /= 0 THEN
            cp_loop : FOR i IN n-conv_integer(cp_len_unload) TO n-1 LOOP
              cpv_i <= '1';
              xk_index_i(nfft_i_int-1 DOWNTO 0) <= conv_std_logic_vector(i, nfft_i_int);
              WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
              IF sclr_i = '1' THEN
                EXIT cp_loop;
              END IF;
            END LOOP cp_loop;
            cpv_i <= '0';
          ELSE
            cpv_i <= '0';
          END IF;
        END IF;

        IF sclr_i = '0' THEN
          xk_index_loop : FOR i IN 0 TO n-1 LOOP
            IF R_HAS_NATURAL_OUTPUT = 1 THEN
              xk_index_i(nfft_i_int-1 DOWNTO 0) <= conv_std_logic_vector(i, nfft_i_int);
            ELSE
              xk_index_i(nfft_i_int-1 DOWNTO 0) <= digit_reverse(conv_std_logic_vector(i, nfft_i_int), R_ARCH);
            END IF;
            WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
            IF sclr_i = '1' THEN
              EXIT xk_index_loop;
            END IF;
          END LOOP xk_index_loop;
        END IF;

        dv_i       <= '0';
        xk_index_i <= (OTHERS => '0');
      END LOOP forever_unload;
    END PROCESS model_unload;

  END GENERATE streaming_arch;

  burst_archs : IF R_ARCH /= 3 GENERATE

    PROCESS
      VARIABLE first_load : BOOLEAN;
      VARIABLE nfft_i_int : INTEGER;
    BEGIN
      xn_index_i <= (OTHERS => '0');
      xk_index_i <= (OTHERS => '0');
      rfd_i      <= '0';
      busy_i     <= '0';
      dv_i       <= '0';
      edone_i    <= '0';
      first_load := TRUE;

      WAIT ON CLK UNTIL CLK = '1' AND ce_i = '1' AND sclr_i = '0';

      forever : LOOP

        IF R_ARCH = 4 AND R_HAS_NATURAL_OUTPUT = 1 THEN
          IF dv_d /= '0' THEN
            WAIT ON dv_d UNTIL dv_d = '0';
          END IF;
        END IF;

        IF NOT (sclr_i = '0' AND START = '1') THEN
          WAIT ON CLK UNTIL (CLK = '1' AND ce_i = '1' AND START = '1') OR sclr_i = '1';
          IF sclr_i = '1' THEN
            first_load := TRUE;
            NEXT forever;
          END IF;
        END IF;

        rfd_i  <= '1';
        nfft_i_int := conv_integer(nfft_i);
        IF R_HAS_NATURAL_OUTPUT = 0 AND NOT first_load THEN
          dv_i <= '1';
        END IF;

        xn_index_loop : FOR i IN 0 TO n-1 LOOP
          xn_index_i                          <= conv_std_logic_vector(i, R_NFFT_MAX);
          IF R_HAS_NATURAL_OUTPUT = 0 AND NOT first_load THEN
            xk_index_i(nfft_i_int-1 DOWNTO 0) <= digit_reverse(conv_std_logic_vector(i, nfft_i_int), R_ARCH);
          END IF;
          WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
          IF sclr_i = '1' THEN
            EXIT xn_index_loop;
          END IF;
        END LOOP xn_index_loop;

        rfd_i        <= '0';
        xn_index_i   <= (OTHERS => '0');
        IF R_HAS_NATURAL_OUTPUT = 0 THEN
          dv_i       <= '0';
          xk_index_i <= (OTHERS => '0');
        END IF;

        IF sclr_i = '0' THEN

          busy_i <= '1';
          run_latency_loop : FOR i IN 1 TO run_latency - 1 LOOP
            WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
            IF sclr_i = '1' THEN
              EXIT run_latency_loop;
            END IF;
          END LOOP run_latency_loop;
        END IF;

        IF sclr_i = '0' THEN
          edone_i <= '1';
          WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
        END IF;
        edone_i   <= '0';
        busy_i    <= '0';

        IF sclr_i = '0' AND R_HAS_NATURAL_OUTPUT = 1 THEN
          IF NOT (UNLOAD = '1') THEN
            WAIT ON CLK UNTIL CLK = '1' AND ((ce_i = '1' AND UNLOAD = '1') OR sclr_i = '1');
          END IF;

          IF sclr_i = '0' THEN

            dv_i <= '1';

            IF R_HAS_CYCLIC_PREFIX = 0 THEN
              cpv_i <= '0';
            ELSE
              IF conv_integer(cp_len_internal) /= 0 THEN
                cp_loop : FOR i IN n-conv_integer(cp_len_internal) TO n-1 LOOP
                  cpv_i <= '1';
                  xk_index_i(nfft_i_int-1 DOWNTO 0) <= conv_std_logic_vector(i, nfft_i_int);
                  WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
                  IF sclr_i = '1' THEN
                    EXIT cp_loop;
                  END IF;
                END LOOP cp_loop;
                cpv_i <= '0';
              ELSE
                cpv_i <= '0';
              END IF;
            END IF;

            IF sclr_i = '0' THEN
              xk_index_loop : FOR i IN 0 TO n-1 LOOP
                xk_index_i(nfft_i_int-1 DOWNTO 0) <= conv_std_logic_vector(i, nfft_i_int);
                WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
                IF sclr_i = '1' THEN
                  EXIT xk_index_loop;
                END IF;
              END LOOP xk_index_loop;
            END IF;

            dv_i                                <= '0';
            xk_index_i                          <= (OTHERS => '0');
            IF R_ARCH = 4 AND dv_d /= '1' AND sclr_i = '0' THEN
              WAIT ON CLK UNTIL CLK = '1' AND ((ce_i = '1' AND dv_d = '1') OR sclr_i = '1');
            END IF;

          END IF;
        END IF;

        IF sclr_i = '1' THEN
          first_load := TRUE;
        ELSE
          first_load := FALSE;
        END IF;

      END LOOP forever;
    END PROCESS;
  END GENERATE burst_archs;

  PROCESS
  BEGIN
    done_i   <= '0';
    WAIT ON CLK UNTIL CLK = '1' AND ((ce_i = '1' AND edone_i = '1') OR sclr_i = '1');
    IF sclr_i = '0' THEN
      done_i <= '1';
      WAIT ON CLK UNTIL CLK = '1' AND (ce_i = '1' OR sclr_i = '1');
    END IF;
    done_i   <= '0';
  END PROCESS;

  need_unload_delay : IF UNLOAD_DELAY > 0 GENERATE
    PROCESS(CLK)
      TYPE T_DV_DELAY IS ARRAY(1 TO UNLOAD_DELAY) OF STD_LOGIC;
      TYPE T_XK_INDEX_DELAY IS ARRAY (1 TO UNLOAD_DELAY) OF STD_LOGIC_VECTOR(R_NFFT_MAX-1 DOWNTO 0);
      VARIABLE dv_delay       : T_DV_DELAY       := (OTHERS => '0');
      VARIABLE xk_index_delay : T_XK_INDEX_DELAY := (OTHERS => (OTHERS => '0'));
      VARIABLE cpv_delay      : T_DV_DELAY       := (OTHERS => '0');
    BEGIN

      IF CLK'EVENT AND CLK = '1' THEN
        IF sclr_i = '1' THEN
          dv_delay       := (OTHERS => '0');
          xk_index_delay := (OTHERS => (OTHERS => '0'));
          cpv_delay      := (OTHERS => '0');
        ELSIF ce_i = '1' THEN
          dv_delay       := dv_i & dv_delay(1 TO UNLOAD_DELAY-1);
          xk_index_delay := xk_index_i & xk_index_delay(1 TO UNLOAD_DELAY-1);
          cpv_delay      := cpv_i & cpv_delay(1 TO UNLOAD_DELAY-1);
        END IF;
        dv_d       <= dv_delay(UNLOAD_DELAY);
        xk_index_d <= xk_index_delay(UNLOAD_DELAY);
        cpv_d      <= cpv_delay(UNLOAD_DELAY);
      END IF;
    END PROCESS;
  END GENERATE;
  no_unload_delay : IF UNLOAD_DELAY < 1 GENERATE
    dv_d           <= dv_i;
    xk_index_d     <= xk_index_i;
    cpv_d          <= cpv_i;
  END GENERATE;

  XN_INDEX <= xn_index_i;
  XK_INDEX <= xk_index_d;
  RFD      <= rfd_i;
  BUSY     <= busy_i;
  DV       <= dv_d;
  EDONE    <= edone_i;
  DONE     <= done_i;
  CPV      <= cpv_d;
  RFS      <= rfs_i;

END behavioral;
