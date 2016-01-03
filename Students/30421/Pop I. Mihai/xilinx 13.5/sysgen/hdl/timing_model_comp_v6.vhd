
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
package timing_model_comp is
  component timing_model
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
  end component;
end timing_model_comp;
