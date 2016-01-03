
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
library work;
use work.mac_fir_utils_pkg.all;
use work.mac_fir_const_pkg.all;
use work.mac_fir_comp_pkg.all;
use work.mac_fir_func_pkg.all;
entity fir_compiler_v2_0_xst is
generic (
  C_FAMILY            : string  := "virtex4";
  C_XDEVICE           : string  := "xdevice";
  C_ELABORATION_DIR   : string  := "./";
  C_COMPONENT_NAME    : string  := "fir";
  C_MEM_INIT_FILE     : string  := "COEFF";
  FILTER_TYPE         : integer := 0;
  INTERP_RATE         : integer := 1;
  DECIM_RATE          : integer := 1;
  RATE_CHANGE_TYPE    : integer := 0;
  ZERO_PACKING_FACTOR : integer := 0;
  NUM_CHANNELS        : integer := 1;
  CHAN_SEL_WIDTH      : integer := 4;
  NUM_TAPS            : integer := 16;
  CLOCK_FREQ          : integer := 400000000;
  SAMPLE_FREQ         : integer := 100000000;
  FILTER_ARCH         : integer := 1;
  DATA_TYPE           : integer := 0;
  DATA_WIDTH          : integer := 16;
  COEF_TYPE           : integer := 0;
  COEF_WIDTH          : integer := 16;
  OUTPUT_WIDTH        : integer := 48;
  OUTPUT_REG          : integer := 1;
  SYMMETRY            : integer := 0;
  ODD_SYMMETRY        : integer := 0;
  NEG_SYMMETRY        : integer := 0;
  COEF_RELOAD         : integer := 0;
  NUM_FILTS           : integer := 1;
  FILTER_SEL_WIDTH    : integer := 1;
  C_HAS_SCLR          : integer := 1;
  C_HAS_CE            : integer := 1;
  C_HAS_ND            : integer := 1;
  MEMORY_MODE         : integer := 0;
  DATA_MEMTYPE        : integer := 0;
  COEF_MEMTYPE        : integer := 0;
  COL_MODE            : integer := 0;
  COL_1ST_LEN         : integer := 3;
  COL_WRAP_LEN        : integer := 4;
  COL_PIPE_LEN        : integer := 3;
  C_LATENCY           : integer := 0
);
port (
  SCLR          : in  std_logic;
  CLK           : in  std_logic;
  CE            : in  std_logic;
  ND            : in  std_logic;
  DIN           : in  std_logic_vector(DATA_WIDTH-1 downto 0) := (others => '0');
  FILTER_SEL    : in  std_logic_vector(FILTER_SEL_WIDTH-1 downto 0)  := (others => '0');
  COEF_LD       : in  std_logic := '0';
  COEF_WE       : in  std_logic := '0';
  COEF_DIN      : in  std_logic_vector(COEF_WIDTH-1 downto 0)   := (others => '0');
  WE_FILTER_BLOCK   : out STD_LOGIC;
  COEF_CURRENT_PAGE : out STD_LOGIC;

  RFD           : out std_logic;
  RDY           : out std_logic;
  DOUT          : out std_logic_vector(OUTPUT_WIDTH-1 downto 0);
  DOUT_I        : out std_logic_vector(DATA_WIDTH-1 downto 0);
  DOUT_Q        : out std_logic_vector(OUTPUT_WIDTH-1 downto 0);
  CHAN_IN       : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0);
  CHAN_OUT      : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0)
);
end fir_compiler_v2_0_xst;
architecture synth of fir_compiler_v2_0_xst is

  constant clk_per_samp          : integer := CLOCK_FREQ/SAMPLE_FREQ;
  constant clk_per_samp_per_chan : integer := clk_per_samp/NUM_CHANNELS;
  constant mac6_dmem_type1 : integer := UTILS_PKG_select_integer(
                                        2,1,DATA_MEMTYPE=1);
  constant mac6_dmem_type  : integer := UTILS_PKG_select_integer(
                                        mac6_dmem_type1,
                                        0,
                                        MEMORY_MODE=0);
  constant mac6_cmem_type1 : integer := UTILS_PKG_select_integer(
                                        2,1,COEF_MEMTYPE=1);
  constant mac6_cmem_type  : integer := UTILS_PKG_select_integer(
                                        mac6_cmem_type1,
                                        0,
                                        MEMORY_MODE=0);



  constant hex_mif_file : string := "hex_" & C_MEM_INIT_FILE;

begin

  g_mac6 : if FILTER_ARCH = 1 generate

    mac6: mac_fir_v6_0_xst
    generic map (
      C_FAMILY            => C_FAMILY,
      C_ELABORATION_DIR   => C_ELABORATION_DIR,
      C_COMPONENT_NAME    => C_COMPONENT_NAME,
      C_MEM_INIT_FILE     => hex_mif_file,
      FILTER_TYPE         => FILTER_TYPE,
      INTERP_RATE         => INTERP_RATE,
      DECIM_RATE          => DECIM_RATE,
      RATE_CHANGE_TYPE    => RATE_CHANGE_TYPE,
      ZERO_PACKING_FACTOR => ZERO_PACKING_FACTOR,
      NUM_CHANNELS        => NUM_CHANNELS,
      CHAN_SEL_WIDTH      => CHAN_SEL_WIDTH,
      NUM_TAPS            => NUM_TAPS,
      CLK_PER_SAMP        => clk_per_samp_per_chan * NUM_CHANNELS,
      DATA_WIDTH          => DATA_WIDTH,
      COEF_WIDTH          => COEF_WIDTH,
      OUTPUT_WIDTH        => OUTPUT_WIDTH,
      OUTPUT_REG          => OUTPUT_REG,
      SYMMETRY            => SYMMETRY,
      ODD_SYMMETRY        => ODD_SYMMETRY,
      NEG_SYMMETRY        => NEG_SYMMETRY,
      COEF_RELOAD         => COEF_RELOAD,
      NUM_FILTS           => NUM_FILTS,
      FILTER_SEL_WIDTH    => FILTER_SEL_WIDTH,
      C_HAS_SCLR          => C_HAS_SCLR,
      C_HAS_CE            => C_HAS_CE,
      C_HAS_ND            => C_HAS_ND,
      C_HAS_RFD           => 1,
      C_HAS_RDY           => 1,
      DATA_MEMTYPE        => mac6_dmem_type,
      COEF_MEMTYPE        => mac6_cmem_type,
      MEMORY_PACK         => 0,
      COL_MODE            => COL_MODE,
      COL_1ST_LEN         => COL_1ST_LEN,
      COL_WRAP_LEN        => COL_WRAP_LEN,
      COL_PIPE_LEN        => COL_PIPE_LEN,
      DATA_SIGN           => DATA_TYPE,
      COEF_SIGN           => COEF_TYPE,
      C_TAP_BALANCE       => 0
    )
    port map (
      RESET       => SCLR,
      CLK         => CLK,
      CE          => CE,
      VIN         => ND,
      DIN         => DIN,
      FILTER_SEL  => FILTER_SEL,

      WE_FILTER_BLOCK   => WE_FILTER_BLOCK,
      COEF_CURRENT_PAGE => COEF_CURRENT_PAGE,

      RFD         => RFD,
      VOUT        => RDY,
      DOUT        => DOUT,
      CHAN_OUT    => CHAN_OUT,
      CHAN_IN     => CHAN_IN,

      DOUT_I      => DOUT_I,
      DOUT_Q      => DOUT_Q,
      COEF_LD     => COEF_LD,
      COEF_WE     => COEF_WE,
      COEF_DIN    => COEF_DIN

    );

  end generate;





end synth;
