
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
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
entity xlcounter is
  generic (
    q_width: integer := 1;
    q_bin_pt: integer := 0;
    q_arith: integer := xlSigned;
    en_arith: integer := xlUnsigned;
    en_bin_pt: integer := 0;
    en_width: integer := 1;
    rst_arith: integer := xlUnsigned;
    rst_bin_pt: integer := 0;
    rst_width: integer := 1;
    c_sinit_val: integer := 0
  );
  port (
    ce: in std_logic;
    clr: in std_logic;
    clk: in std_logic;
    q: out std_logic_vector(q_width - 1 downto 0);
    en: in std_logic_vector(en_width - 1 downto 0);
    rst: in std_logic_vector(rst_width - 1 downto 0)
  );
end xlcounter;
architecture behavior of xlcounter is
  component synth_reg_w_init
    generic (
      width: integer;
      init_index: integer;
      latency: integer
    );
    port (
      i: in std_logic_vector(width - 1 downto 0);
      ce: in std_logic;
      clr: in std_logic;
      clk: in std_logic;
      o: out std_logic_vector(width - 1 downto 0)
    );
  end component;
  signal sinit, core_ce: std_logic;
  signal d, tmp: std_logic_vector(0 downto 0);
begin
  sinit <= (clr or rst(0)) and ce;
  core_ce <= ce and en(0);
  d(0) <= not tmp(0);
  q(0) <= tmp(0);
  counter_inst: synth_reg_w_init
    generic map (
      width => q_width,
      init_index => c_sinit_val,
      latency => 1
    )
    port map (
      i => d,
      ce => core_ce,
      clr => sinit,
      clk => clk,
      o => tmp
    );
end  behavior;
