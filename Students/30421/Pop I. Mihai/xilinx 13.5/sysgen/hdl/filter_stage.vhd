
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
use ieee.numeric_std.all;
library unisim;
use unisim.VComponents.all;
library work;
use work.mac_fir_const_pkg.all;
use work.mac_fir_comp_pkg.all;
use work.mac_fir_utils_pkg.all;
library xilinxcorelib;
use xilinxcorelib.prims_utils_v9_0.all;
entity filter_stage is
generic (
  C_FAMILY          : string;
  FIR_REQS          : t_reqs;
  FIR_DETAILS       : t_details;
  COL_TOP           : integer;
  COL_BOTTOM        : integer;
  LAST_STAGE        : integer;
  STAGE_NUM         : integer;
  C_ELABORATION_DIR : string;
  MIF_FILE          : string;
  LAST_COEF_STAGE   : integer;
  C_HAS_CE          : integer
);
port (
  CLK               : in  std_logic;
  CE                : in  std_logic;
  RESET             : in  std_logic;
  WE                : in  std_logic;
  WE_SYM            : in  std_logic;
  DIN               : in  std_logic_vector(FIR_REQS.data_width-1 downto 0);
  DIN_SYM           : in  std_logic_vector(FIR_REQS.data_width-1 downto 0);
  DATA_ADDR_IN      : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                          (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
  DATA_SYM_ADDR_IN  : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                          (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
  COEF_ADDR_IN      : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                          (FIR_DETAILS.coef_mem_depth-1)-1 downto 0);
  PC_IN             : in  std_logic_vector(47 downto 0);
  CARRY_IN          : in  std_logic_vector(47 downto 0);
  DIN_CASC          : in  std_logic_vector(29 downto 0);
  WE_OUT            : out std_logic;
  WE_OUT_SYM        : out std_logic;
  DOUT              : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
  DOUT_SYM          : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
  DOUT_MID          : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
  P_OUT             : out std_logic_vector(47 downto 0);
  PC_OUT            : out std_logic_vector(47 downto 0);
  DATA_ADDR_OUT     : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                          (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
  DATA_SYM_ADDR_OUT : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                          (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
  COEF_ADDR_OUT     : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                          (FIR_DETAILS.coef_mem_depth-1)-1 downto 0);
  DOUT_CASC         : out std_logic_vector(29 downto 0);

  DOUT_SMAC         : out std_logic_vector(24 downto 0);
  COUT_SMAC         : out std_logic_vector(24 downto 0);

  COEF_LD           : in std_logic;
  COEF_WE_IN        : in std_logic;
  COEF_WE_OUT       : out std_logic;
  COEF_LD_EN_IN     : in std_logic;
  COEF_LD_EN_OUT    : out std_logic;
  COEF_DIN          : in std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  COEF_DOUT         : out std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  COEF_RELOAD_PAGE_IN  : in std_logic;
  COEF_RELOAD_PAGE_OUT : out std_logic;

  CNTRL_EXTRA1_IN   : in std_logic;
  CNTRL_EXTRA2_IN   : in std_logic;
  CNTRL_EXTRA3_IN   : in std_logic;
  CNTRL_EXTRA1_OUT  : out std_logic;
  CNTRL_EXTRA2_OUT  : out std_logic;
  CNTRL_EXTRA3_OUT  : out std_logic;
  FIRST_PHASE       : in std_logic
);
end filter_stage;
architecture synth of filter_stage is

  signal gnd     : std_logic;
  signal vcc     : std_logic;
  signal gnd_bus : std_logic_vector(47 downto 0);
  signal data_store_in,
         data_sym_store_in,
         data_store_out,
         data_sym_store_out,
         adder_input_1,
         adder_input_2,
         dout_buffer_in,
         dout_buffer_out,
         dout_buffer_laststage_in,
         dsym_buffer_in,
         dsym_buffer_out,
         data_sym_latch_in,
         data_delay_in,
         data_delay : std_logic_vector(FIR_REQS.data_width-1 downto 0);
  signal coef_delay_in,
         coef_delay,
         coef_store_out : std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  signal data_store_we,
         data_sym_store_we,
         adder_sym_blank,
         dout_buffer_ce,
         dsym_buffer_ce,
         data_sym_latch_ce,
         dout_buffer_laststage_ce,
         casc_add_en : std_logic;

signal int_ce                        : std_logic;
signal  cntrl_extra1_reg,
        cntrl_extra2_reg,
        cntrl_extra3_dly:std_logic:='0';
begin
  gnd     <= '0';
  vcc     <= '1';
  gnd_bus <= (others=>'0');
  gce1 : if C_HAS_CE=0 generate
  begin
    int_ce <= '1';
  end   generate;
  gce2 : if C_HAS_CE/=0 generate
  begin
    int_ce <= ce;
  end   generate;
  memory: memory_structure
  generic map(
    C_FAMILY          => C_FAMILY,
    FIR_REQS          => FIR_REQS,
    FIR_DETAILS       => FIR_DETAILS,
    c_elaboration_dir => C_ELABORATION_DIR,
    mif_file          => MIF_FILE,
    LAST_COEF_STAGE   => LAST_COEF_STAGE,
    C_HAS_CE          => C_HAS_CE
  )
  port map(
    clk         => clk,
    ce                                  => int_ce,
    reset       => reset,
    c_addr      => COEF_ADDR_IN,
    d_addr      => DATA_ADDR_IN,
    d_sym_addr  => DATA_SYM_ADDR_IN,
    coef_out    => coef_store_out,
    data_in     => data_store_in,
    data_out    => data_store_out,
    we          => data_store_we,
    datasym_in  => data_sym_store_in,
    datasym_out => data_sym_store_out,
    we_sym      => data_sym_store_we,

    coef_ld         =>COEF_LD,
    coef_we_in      =>COEF_WE_IN,
    coef_we_out     => COEF_WE_OUT,
    coef_ld_en_in   =>COEF_LD_EN_IN,
    coef_ld_en_out  =>COEF_LD_EN_OUT,
    coef_din        =>COEF_DIN,
    coef_dout       =>COEF_DOUT,
    coef_reload_page_in => COEF_RELOAD_PAGE_IN,
    coef_reload_page_out => COEF_RELOAD_PAGE_OUT
  );

  cntrlextra: process(clk)
  begin
    if (rising_edge(clk)) then
      if (reset='1') then
        cntrl_extra1_reg<='0';
      elsif (CE='1') then
        cntrl_extra1_reg<=CNTRL_EXTRA1_IN;
      end if;
    end if;
  end process;

  CNTRL_EXTRA1_OUT<=cntrl_extra1_reg;

end synth;
