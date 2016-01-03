
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
entity xlmux is
  generic (
    core_name0: string  := "";
[% FOREACH i = [0 .. 31] -%]
    din[% i %]_width: integer := 3;
    din[% i %]_bin_pt: integer := 1;
    din[% i %]_arith: integer := xlUnsigned;
[% END -%]
    s_width: integer := 3;
    s_bin_pt: integer := 0;
    s_arith: integer := xlUnsigned;
    en_arith: integer := xlUnsigned;
    en_bin_pt: integer := 0;
    en_width: integer := 1;
    rst_arith: integer := xlUnsigned;
    rst_bin_pt: integer := 0;
    rst_width: integer := 1;
    dout_width: integer := 3;
    dout_bin_pt: integer := 1;
    dout_arith: integer := xlSigned;
    core_dout_arith: integer := xlSigned;
    core_dout_bin_pt: integer := 0;
    c_inputs: integer := 2;
    c_width: integer := 13;
    c_sel_width: integer := 3;
    c_mux_type: integer := 0;
    c_has_q: integer := 1;
    c_has_o: integer := 0;
    c_has_sclr: integer := 1;
    c_has_ce: integer := 1;
    c_latency: integer := 1;
    c_enable_rlocs: integer := 0;
    latency: integer := 1;
    quantization: integer := xlTruncate;
    overflow: integer := xlWrap;
    core: boolean := true
  );
  port (
[% FOREACH i = [0 .. 31] -%]
    din[% i %]: in std_logic_vector(din[% i %]_width - 1 downto 0) :=
      (others => '0');
[% END -%]
    s: in std_logic_vector(s_width - 1 downto 0);
    en: in std_logic_vector(en_width - 1 downto 0);
    rst: in std_logic_vector(rst_width - 1 downto 0);
    ce: in std_logic;
    clr: in std_logic;
    clk: in std_logic;
    dout: out std_logic_vector(dout_width - 1 downto 0)
  );
end xlmux ;
architecture behavior of xlmux is
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
[% FOREACH i = [0 .. 31] -%]
  signal conv_din[% i %]: std_logic_vector(c_width - 1 downto 0);
[% END -%]
  signal core_dout: std_logic_vector(c_width - 1 downto 0);
  signal conv_core_dout: std_logic_vector(dout_width - 1 downto 0);
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
  constant zeroVec: std_logic_vector(c_width - 1 downto 0) :=
    (others => '0');
  constant zeroStr: string(1 to c_width) :=
    std_logic_vector_to_bin_string(zeroVec);
-- synopsys translate_on
begin
  internal_clr <= (clr or (rst(0))) and ce;
  internal_ce <= ce and (en(0));
[% FOREACH i = [0 .. 31] -%]
  conv_din[% i %] <= align_input(din[% i %], din[% i %]_width,
                       core_dout_bin_pt - din[% i %]_bin_pt,
                       din[% i %]_arith, c_width);
[% END -%]
  conv_core_dout <= convert_type(core_dout, c_width, core_dout_bin_pt,
                      core_dout_arith, dout_width, dout_bin_pt, dout_arith,
                      quantization, overflow);
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
  latency_test : if (latency - c_latency > 0) generate
    del_reg : synth_reg
      generic map (
        width => dout_width,
        latency => latency - c_latency
      )
      port map (
        i => conv_core_dout,
        ce => internal_ce,
        clr => internal_clr,
        clk => clk,
        o => dout
      );
  end generate;
  latency_0_or_1_or_2 : if (latency - c_latency = 0) generate
    dout <= conv_core_dout;
  end generate;
end architecture behavior;
