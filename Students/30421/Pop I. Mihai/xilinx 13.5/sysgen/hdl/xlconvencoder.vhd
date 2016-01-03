
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
-- synopsys translate_off
library XilinxCoreLib;
-- synopsys translate_on
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
entity [% entity_name %] is
  port (
        din: in std_logic_vector(0 downto 0);
        dout0: out std_logic_vector(0 downto 0);
        dout1: out std_logic_vector(0 downto 0);
        dout2: out std_logic_vector(0 downto 0);
        dout3: out std_logic_vector(0 downto 0);
        dout4: out std_logic_vector(0 downto 0);
        dout5: out std_logic_vector(0 downto 0);
        dout6: out std_logic_vector(0 downto 0);
        vout: out std_logic;
        vin: in std_logic := '1';
        en: in std_logic := '1';
        rst: in std_logic := '0';
        clk: in std_logic;
        ce: in std_logic
  );
end [% entity_name %];
architecture behavior of [% entity_name %] is
  signal internal_clr: std_logic;
  signal internal_ce: std_logic;
  signal rdy_temp: std_logic;
  signal dout: std_logic_vector(6 downto 0);
  component [% core_name %]
    port (
                [% core_component_text %]
    );
  end component;
  attribute syn_black_box of [%  core_name %]: component is true;
  attribute fpga_dont_touch of [% core_name %]:component is "true";
  attribute box_type of [% core_name %]:component is "black_box";
begin
  internal_clr <= rst and ce;
  internal_ce <= (ce and en) or internal_clr;
  core_instance: [%  core_name %]
    port map(
      [% core_instance_text %]
    );
   [% dout_port_map %]
end  behavior;
