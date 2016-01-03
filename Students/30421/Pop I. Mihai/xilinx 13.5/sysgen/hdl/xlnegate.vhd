
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
entity xlnegate is
  generic (
    core_name0: string := "";
    a_width: integer := 12;
    a_bin_pt: integer := 7;
    a_arith: integer := xlSigned;
    p_width: integer := 13;
    p_bin_pt: integer := 7;
    p_arith: integer := xlSigned;
    rst_width: integer := 1;
    rst_bin_pt: integer := 0;
    en_width: integer := 5;
    en_bin_pt: integer := 2;
    en_arith: integer := xlUnsigned;
    rst_arith: integer := xlUnsigned;
    latency: integer := 1;
    quantization: integer := xlRound;
    overflow: integer := xlSaturate;
    c_width: integer;
    c_has_s: integer;
    c_has_q: integer;
    c_enable_rlocs: integer := 1;
    core: boolean := false
  );
  port (
    a: in std_logic_vector(a_width - 1 downto 0);
    ce: in std_logic;
    clr: in std_logic;
    clk: in std_logic;
    rst: in std_logic_vector(rst_width - 1 downto 0);
    en: in std_logic_vector(en_width - 1 downto 0);
    p: out std_logic_vector(p_width - 1 downto 0)
  );
end xlnegate ;
architecture behavior of xlnegate is
  component synth_negate
    generic (
      c_a_width: integer;
      latency: integer
    );
  port (
    a: in std_logic_vector(c_a_width - 1 downto 0);
    ce: in std_logic;
    clr: in std_logic;
    clk: in std_logic;
    p: out std_logic_vector(c_a_width downto 0)
  );
  end component;
  component synth_reg
    generic (
      width: integer;
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
  constant full_p_width: integer := c_width + 1;
  constant full_p_bin_pt: integer := a_bin_pt;
  constant full_p_arith: integer := xlSigned;
  signal full_a: std_logic_vector(c_width - 1 downto 0);
  signal full_s, full_q: std_logic_vector(c_width downto 0);
  signal conv_p: std_logic_vector(p_width - 1 downto 0);
  signal core_p: std_logic_vector(c_width downto 0);
  signal internal_clr: std_logic;
  signal internal_ce: std_logic;
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
  -- synopsys translate_off
  constant zeroStr: string(1 to c_width) := (others => '0');
  -- synopsys translate_on
begin
  internal_clr <= (clr or (rst(0))) and ce;
  internal_ce <= ce and (en(0));
  full_a <= extend_MSB(a, c_width, a_arith);
  negate_process: process (a, core_p)
  begin
  conv_p <= convert_type(core_p, full_p_width, full_p_bin_pt, full_p_arith,
                         p_width, p_bin_pt, p_arith, quantization, overflow);
  end process negate_process;
  synth_behavioral_inst: if not core generate
    p <= conv_p;
    synth_negate_inst: synth_negate
      generic map (
        c_a_width => c_width,
        latency => latency
      )
      port map (
        a => full_a,
        ce => internal_ce,
        clr => internal_clr,
        clk => clk,
        p => core_p
      );
  end generate;
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
  latency_test: if (core) and (latency > 1) generate
    core_p <= full_q;
    reg: synth_reg
      generic map (
        width => p_width,
        latency => latency - 1
      )
      port map (
        i => conv_p,
        ce => internal_ce,
        clr => internal_clr,
        clk => clk,
        o => p
      );
  end generate;
  latency0: if (core) and (latency = 0) generate
    core_p <= full_s;
    p <= conv_p;
  end generate latency0;
  latency1: if (core) and (latency = 1) generate
    core_p <= full_q;
    p <= conv_p;
  end generate latency1;
end architecture behavior;
