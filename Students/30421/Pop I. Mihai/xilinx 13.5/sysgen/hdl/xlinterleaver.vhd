
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
library XilinxCoreLib;
-- synopsys translate_on
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;

entity [% entity_name %] is
  port (
[% toplevel_ports %]
    vout: out std_logic;
    vin: in std_logic := '1';
    en: in std_logic := '1';
    rst: in std_logic := '0';
    clk: in std_logic;
    ce: in std_logic
  );
end [% entity_name %] ;

architecture behavior of [% entity_name %] is
  component synth_reg_w_init
    generic (
      width: integer := 16;
      init_index: integer := 0;
      latency: integer := 5
    );
    port (
      i: in std_logic_vector(width - 1 downto 0);
      ce: in std_logic;
      clr: in std_logic;
      clk: in std_logic;
      o: out std_logic_vector(width - 1 downto 0)
    );
  end component;

  signal nd, ndo: std_logic;
  signal core_fd: std_logic_vector(0 downto 0);
  signal curr_seen_rst: std_logic_vector(0 downto 0);
  signal next_seen_rst: std_logic_vector(0 downto 0) := "0";
  signal internal_clr: std_logic;
  signal internal_ce: std_logic;

  component [% core_name %]
    port (
[% core_component_text %]
    );
  end component;
  attribute syn_black_box of [% core_name %]:
    component is true;
  attribute fpga_dont_touch of [% core_name %]:
    component is "true";
  attribute box_type of [% core_name %]:
    component  is "black_box";
begin
  fd_logic: process(curr_seen_rst, rst, vin)
  begin
    if curr_seen_rst(0) = '0' then
      if (rst = '1') then
        next_seen_rst(0) <= '1';
      else
        next_seen_rst <= curr_seen_rst;
      end if;
      core_fd(0) <= '0';
    else
      if vin = '1' then
        core_fd(0) <= '1';
        if (rst = '0') then
          next_seen_rst(0) <= '0';
        else
          next_seen_rst <= curr_seen_rst;
        end if;
      else
        core_fd(0) <= '0';
        next_seen_rst <= curr_seen_rst;
      end if;
    end if;
  end process;

  seen_rst_reg: synth_reg_w_init
    generic map (
      width => 1,
      init_index => 1,
      latency => 1
    )
    port map (
      i => next_seen_rst,
      ce => internal_ce,
      clr => internal_clr,
      clk => clk,
      o => curr_seen_rst
    );

  nd <= vin and ce;
  internal_clr <= rst and ce;
  internal_ce <= (ce and (en)) or internal_clr;

  core_instance: [% core_name %]
      port map (
       [% core_instance_text %]
      );

  vout <= ndo;
end behavior;
