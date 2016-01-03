
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
use IEEE.std_logic_arith.all;
use ieee.std_logic_unsigned.all;
use work.conv_pkg.all;
entity xlfifogen is
  generic (
    core_name0: string := "";
    data_width: integer := -1;
    data_count_width: integer := -1;
    percent_full_width: integer := -1;
    has_ae : integer := 0;
    has_af : integer := 0
  );
  port (
    din: in std_logic_vector(data_width - 1 downto 0);
    we: in std_logic;
    we_ce: in std_logic;
    re: in std_logic;
    re_ce: in std_logic;
    rst: in std_logic;
    en: in std_logic;
    ce: in std_logic;
    clk: in std_logic;
    empty: out std_logic;
    full: out std_logic;
    percent_full: out std_logic_vector(percent_full_width - 1 downto 0);
    dcount: out std_logic_vector(data_count_width - 1 downto 0);
    ae: out std_logic;
    af: out std_logic;
    dout: out std_logic_vector(data_width - 1 downto 0)
  );
end xlfifogen ;
architecture behavior of xlfifogen is
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   comp_def = core_component_def.$i                                      -%]
  component [% name %]
    port (
[% comp_def %]
    );
  end component;
  attribute syn_black_box of [% name %]:
    component is true;
  attribute fpga_dont_touch of [% name %]:
    component is "true";
  attribute box_type of [% name %]:
    component  is "black_box";
[% END                                                                     -%]
  signal rd_en: std_logic;
  signal wr_en: std_logic;
  signal srst: std_logic;
  signal core_full: std_logic;
  signal core_dcount: std_logic_vector(data_count_width - 1 downto 0);
begin
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                      -%]
  comp[% i %]: if ((core_name0 = "[% name %]")) generate
    core_instance[% i %]: [% name %]
      port map (
[% inst_txt %]
      );
  end generate;
[% END                                                                     -%]

  modify_count: process(core_full, core_dcount) is
  begin
    if core_full = '1' then
      percent_full <= (others => '1');
    else
      percent_full <= core_dcount(data_count_width-1 downto data_count_width-percent_full_width);
    end if;
  end process modify_count;

  rd_en <= re and en and re_ce;
  wr_en <= we and en and we_ce;
  full <= core_full;
  srst <= rst and ce;
  dcount <= core_dcount;

  terminate_core_ae: if has_ae /= 1 generate
  begin
    ae <= '0';
  end generate terminate_core_ae;
  terminate_core_af: if has_af /= 1 generate
  begin
    af <= '0';
  end generate terminate_core_af;
end  behavior;
