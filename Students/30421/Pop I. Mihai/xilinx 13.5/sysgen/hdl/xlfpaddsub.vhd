
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
use work.conv_pkg.all;
entity xlfpaddsub is
  generic (
    core_name0: string := "";
    a_width: integer := 32;
    a_bin_pt: integer := 24;
    a_arith: integer := xlFloat;
    b_width: integer := 32;
    b_bin_pt: integer := 24;
    b_arith: integer := xlFloat;
    s_width: integer := 32;
    s_bin_pt: integer := 24;
    s_arith: integer := xlFloat;
    rst_width: integer := 1;
    rst_bin_pt: integer := 0;
    rst_arith: integer := xlUnsigned;
    en_width: integer := 1;
    en_bin_pt: integer := 0;
    en_arith: integer := xlUnsigned;
    a_tdata_width: integer := 32;
    s_tdata_width: integer := 32;
    extra_registers: integer := 0;
    latency: integer := 0;
    quantization: integer := xlTruncate;
    overflow: integer := xlWrap;
    c_latency: integer := 0
  );
  port (
    a   : in std_logic_vector(a_width - 1 downto 0);
    b   : in std_logic_vector(b_width - 1 downto 0);
    ce  : in std_logic;
    clr : in std_logic := '0';
    clk : in std_logic;
    rst : in std_logic_vector(rst_width - 1 downto 0) := "0";
    en  : in std_logic_vector(en_width - 1 downto 0) := "1";
    s   : out std_logic_vector(s_width - 1 downto 0)
  );
end xlfpaddsub;
architecture behavior of xlfpaddsub is
  component synth_reg
    generic (
      width: integer := 16;
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
  signal a_tvalid_net:std_logic := '1';
  signal a_tdata: std_logic_vector(a_tdata_width - 1 downto 0) := (others => '0');
  signal b_tvalid_net:std_logic := '1';
  signal b_tdata: std_logic_vector(a_tdata_width - 1 downto 0) := (others => '0');
  signal result_tdata:std_logic_vector(s_tdata_width - 1 downto 0);
  signal result_tvalid_net:std_logic;
  signal internal_clr: std_logic;
  signal internal_ce: std_logic;
  signal result: std_logic_vector(s_width - 1 downto 0);
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
begin
  internal_clr <= (clr or (rst(0))) and ce;
  internal_ce <= ce and en(0);
  addsub_process: process (a, b, result_tdata)
  begin
    a_tdata(a_width - 1 downto 0) <= a(a_width - 1 downto 0);
    b_tdata(b_width - 1 downto 0) <= b(b_width - 1 downto 0);
    result(s_width - 1 downto 0) <= result_tdata(s_width - 1 downto 0);
  end process addsub_process;

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
latency_gt_0: if (extra_registers > 0) generate
  reg: synth_reg
    generic map (
      width => s_width,
      latency => extra_registers
    )
    port map (
      i => result,
      ce => internal_ce,
      clr => internal_clr,
      clk => clk,
      o => s
    );
end generate;
latency_eq_0: if (extra_registers = 0) generate
  s <= result;
end generate;
end architecture behavior;
