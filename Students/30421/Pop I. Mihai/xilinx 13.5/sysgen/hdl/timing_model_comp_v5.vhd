
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

PACKAGE timing_model_comp IS

  COMPONENT timing_model
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
  END COMPONENT;

END timing_model_comp;
