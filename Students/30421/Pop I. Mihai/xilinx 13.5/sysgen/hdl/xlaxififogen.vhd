
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
entity xlaxififogen is
  generic (
    core_name0: string := "";
    tdata_width: integer := -1;
    tdest_width: integer := -1;
    tstrb_width: integer := -1;
    tkeep_width: integer := -1;
    tid_width: integer := -1;
    tuser_width: integer := -1;
    has_aresetn: integer := -1
  );
  port (
      s_aclk: in std_logic;
      ce: in std_logic;
      aresetn: in std_logic;
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   port_def = port_component_def.$i                                      -%]
[% port_def %]
[% END                                                                     -%]
      s_axis_tvalid: in std_logic;
      s_axis_tready: out std_logic;
      m_axis_tready: in std_logic;
      m_axis_tvalid: out std_logic
  );
end xlaxififogen ;
architecture behavior of xlaxififogen is
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
  signal srst: std_logic:= '0';
  signal reset_gen1: std_logic  := '0';
  signal reset_gen_d1: std_logic        := '0';
  signal reset_gen_d2: std_logic := '0';
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
        srst <= reset_gen_d2 when (has_aresetn = 0)
                else not ( (not aresetn) and ce );
 process(s_aclk)
 begin
         if(s_aclk'event AND s_aclk = '1') then
                         reset_gen1 <= '1';
                         reset_gen_d1 <= reset_gen1;
                         reset_gen_d2 <= reset_gen_d1;
        end if;
 end process;

end  behavior;
