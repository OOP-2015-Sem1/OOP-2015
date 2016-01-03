
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
entity xldpram_dist is
  generic (
    core_name0: string := "";
    c_width: integer := 12;
    addr_width: integer := 12;
    c_address_width: integer := 4;
    latency: integer := 1
  );
  port (
    dina: in std_logic_vector(c_width - 1 downto 0);
    addra: in std_logic_vector(addr_width - 1 downto 0);
    wea: in std_logic_vector(0 downto 0);
    ena: in std_logic_vector(0 downto 0) := (others => '1');
    a_ce: in std_logic;
    a_clk: in std_logic;
    douta: out std_logic_vector(c_width - 1 downto 0);
    addrb: in std_logic_vector(addr_width - 1 downto 0);
    enb: in std_logic_vector(0 downto 0) := (others => '1');
    b_ce: in std_logic;
    b_clk: in std_logic;
    doutb: out std_logic_vector(c_width - 1 downto 0)
  );
end xldpram_dist ;
architecture behavior of xldpram_dist is
  component synth_reg is
    generic (
      width: integer := 8;
      latency: integer := 1
    );
    port (
      i: in std_logic_vector(width - 1 downto 0);
      ce: in std_logic;
      clr: in std_logic;
      clk: in std_logic;
      o: out std_logic_vector(width - 1 downto 0)
    );
  end component;
  constant num_extra_addr_bits: integer := (c_address_width - addr_width);
  signal core_addra, core_addrb: std_logic_vector(c_address_width - 1 downto 0);
  signal core_data_in, core_douta, core_doutb: std_logic_vector(c_width - 1 downto 0);
  signal reg_douta, reg_doutb: std_logic_vector(c_width - 1 downto 0);
  signal core_we: std_logic_vector(0 downto 0);
  signal core_cea, core_ceb: std_logic;
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   comp_def = core_component_def.$i                                      -%]
  component [% name %]
    port (
      a: in std_logic_vector(c_address_width - 1 downto 0);
      clk: in std_logic;
      d: in std_logic_vector(c_width - 1 downto 0);
      we: in std_logic;
      dpra: in std_logic_vector(c_address_width - 1 downto 0);
      spo: out std_logic_vector(c_width - 1 downto 0);
      dpo: out std_logic_vector(c_width - 1 downto 0)
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
  need_to_pad_addr : if num_extra_addr_bits > 0 generate
      core_addra(c_address_width - 1 downto addr_width) <= (others => '0');
      core_addra(addr_width - 1 downto 0) <= addra;
      core_addrb(c_address_width - 1 downto addr_width) <= (others => '0');
      core_addrb(addr_width - 1 downto 0) <= addrb;
  end generate;
  no_need_to_pad_addr: if num_extra_addr_bits = 0 generate
    core_addra <= addra;
    core_addrb <= addrb;
  end generate;
  douta <= reg_douta;
  doutb <= reg_doutb;
  core_cea <= a_ce and ena(0);
  core_ceb <= b_ce and enb(0);
  core_we(0) <= wea(0) and core_cea;
  registered_dpram : if latency > 0 generate
    output_rega: synth_reg
      generic map (
        width   => c_width,
        latency => latency
      )
      port map (
        i   => core_douta,
        ce  => core_cea,
        clr => '0',
        clk => a_clk,
        o   => reg_douta
      );
    output_regb: synth_reg
      generic map (
        width   => c_width,
        latency => latency
      )
      port map (
        i   => core_doutb,
        ce  => core_ceb,
        clr => '0',
        clk => b_clk,
        o   => reg_doutb
      );
  end generate;
  nonregistered_ram : if latency = 0 generate
    reg_douta <= core_douta;
    reg_doutb <= core_doutb;
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
        a => core_addra,
        clk => a_clk,
        d => dina,
        we => core_we(0),
        dpra => core_addrb,
        spo => core_douta,
        dpo => core_doutb
      );
  end generate;
[% END                                                                     -%]
end behavior;
