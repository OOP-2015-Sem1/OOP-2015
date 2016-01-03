
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
-- synopsys translate_off
library IEEE;
use IEEE.std_logic_1164.all;
library UNISIM;
use UNISIM.Vcomponents.ALL;
entity xlclksource_gated is
  generic (t : time := 0 ns);
  port (clkin             : in std_logic;
        clk_gate        : in std_logic := '1';
        clr             : in std_logic;
        ce              : in std_logic;
        clk             : out std_logic;
        fakeOutForXst   : out std_logic);
end xlclksource_gated;
architecture behavior of xlclksource_gated is
  signal clk_gate_delayed : std_logic;
  component BUFGCE
    port (
      CE: in std_logic;
      I: in std_logic;
      O: out std_logic
    );
  end component;
begin
  clk_gate_delayed <= transport clk_gate after t;
  fakeOutForXst <= '0';
  gate_clk: BUFGCE
    port map (
      CE => clk_gate_delayed,
      I => clkin,
      O => clk
    );
end behavior;
-- synopsys translate_on
