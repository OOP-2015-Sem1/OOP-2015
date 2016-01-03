
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
entity xlaccum is
  generic (
    core_name0: string := "";
    b_width: integer := 8;
    b_bin_pt: integer := 2;
    b_arith: integer := xlUnsigned;
    q_width: integer := 18;
    q_bin_pt: integer := 4;
    q_arith: integer := xlSigned
  );
  port (
    b: in std_logic_vector(b_width - 1 downto 0);
    ce: in std_logic;
    clr: in std_logic;
    clk: in std_logic;
    en: in std_logic_vector(0 downto 0);
    rst: in std_logic_vector(0 downto 0);
    q: out std_logic_vector(q_width - 1 downto 0)
  );
end xlaccum ;
architecture behavior of xlaccum is
  -- synopsys translate_off
  signal real_b, real_q : real;
   -- synopsys translate_on
  signal internal_ce : std_logic;
  signal sclr : std_logic;
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
  constant zeroVec: std_logic_vector(q_width - 1 downto 0) :=
    (others => '0');
  constant zeroStr : string(1 to q_width) :=
    std_logic_vector_to_bin_string(zeroVec);
-- synopsys translate_on
begin
  internal_ce <= ce and en(0);
  sclr <= (clr or rst(0)) and ce;
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
end  behavior;
