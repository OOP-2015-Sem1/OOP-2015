
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
entity xlfir_compiler is
  generic (
    core_name0: string := "";
    din_width: integer := 16;
    dout_width: integer := 16;
    dout_type : integer := 0;
    core_dout_width: integer := 16;
    chan_in_width: integer := 1;
    chan_out_width: integer := 1;
    coef_din_width: integer := 1;
    use_core_chan_in: integer := 0
  );
  port (
    din: in std_logic_vector(din_width - 1 downto 0);
    dout: out std_logic_vector(dout_width - 1 downto 0) := (others => '0');
    chan_in : out std_logic_vector(chan_in_width - 1 downto 0) := (others => '0');
    chan_out : out std_logic_vector(chan_out_width - 1 downto 0) := (others => '0');
    coef_din : in std_logic_vector(coef_din_width - 1 downto 0) := (others => '0');
    coef_ld : in std_logic := '0';
    coef_we : in std_logic := '0';
    en  : in std_logic := '1';
    rst  : in std_logic := '0';
    vin : in std_logic := '1';
    vout : out std_logic := '0';
    rfd  : out std_logic := '0';
    src_ce: in std_logic;
    src_clk: in std_logic;
    dest_ce: in std_logic;
    dest_clk: in std_logic;
    core_ce: in std_logic;
    core_clk: in std_logic
  );
end xlfir_compiler ;
architecture behavior of xlfir_compiler is
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
component synth_reg_w_init
    generic (
        width: integer;
        init_index: integer;
        init_value: bit_vector;
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
signal core_rfd : std_logic;
signal din_rfd : std_logic;
signal inter_rfd : std_logic;
signal internal_nd : std_logic;
attribute MAX_FANOUT: string;
attribute MAX_FANOUT of internal_nd : signal is "REDUCE";
signal core_dout : std_logic_vector(core_dout_width-1 downto 0) := (others => '0');
signal core_chan_in : std_logic_vector(chan_in_width-1 downto 0) := (others => '0');
signal core_chan_out : std_logic_vector(chan_out_width-1 downto 0) := (others => '0');
signal internal_core_ce : std_logic;
signal internal_src_ce : std_logic;
signal internal_dest_ce : std_logic;
signal latched_core_rdy_vec : std_logic_vector(0 downto 0) := (others => '0');
signal core_rdy : std_logic;
signal ored_latched_core_rdy_vec : std_logic_vector(0 downto 0) := (others => '0');
signal predicated_core_rdy : std_logic := '0';
signal predicated_core_rdy_vec : std_logic_vector(0 downto 0) := (others => '0');
signal vout_vec : std_logic_vector(0 downto 0) := (others => '0');
signal internal_rst_src : std_logic;
signal internal_rst_core : std_logic;
signal internal_rst_dest : std_logic;
signal internal_coef_ld : std_logic := '0';
signal internal_coef_we : std_logic := '0';
signal adjusted_rate_core_dout : std_logic_vector(core_dout_width-1 downto 0) := (others => '0');
begin
internal_nd      <= src_ce and vin;
internal_core_ce <= core_ce and en;
internal_src_ce  <= src_ce and en;
internal_dest_ce <= dest_ce and en;
internal_coef_ld <= coef_ld and en;
internal_coef_we <= coef_we and en;
internal_rst_src <= rst and src_ce;
internal_rst_dest <= rst and dest_ce;
internal_rst_core <= rst and core_ce;
rfd              <= core_rfd;

sign_extension: process(adjusted_rate_core_dout)
begin
  dout <= extend_MSB(adjusted_rate_core_dout, dout_width, dout_type);
end process sign_extension;

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
predicated_core_rdy <= not(internal_dest_ce) and (core_rdy or latched_core_rdy_vec(0));
predicated_core_rdy_vec(0) <= predicated_core_rdy;
LATCH_RDY_FOR_VOUT :  synth_reg_w_init
generic map (
      width => 1,
      init_index => 0,
      init_value => "0",
      latency => 1
)
port map (
      i => predicated_core_rdy_vec,
      ce => internal_core_ce,
      clr => internal_rst_core,
      clk => core_clk,
      o => latched_core_rdy_vec
);
ored_latched_core_rdy_vec(0) <= latched_core_rdy_vec(0) or core_rdy;
USE_LATCHED_CHAN_IN : if(use_core_chan_in /= 1) generate
  LATCH_CHAN_IN: synth_reg_w_init
    generic map(
      width => chan_in_width,
      init_index => 0,
      init_value => "0",
      latency => 1
    )
    port map (
      i => core_chan_in,
      ce => internal_src_ce,
      clr => internal_rst_src,
      clk => src_clk,
      o => chan_in
    );
end generate;
USE_DIRECT_CHAN_IN : if(use_core_chan_in = 1) generate
  chan_in <= core_chan_in;
end generate;
LATCH_DATA_OUT: synth_reg_w_init
generic map (
      width => core_dout_width,
      init_index => 0,
      init_value => "0",
      latency => 1
)
port map (
      i => core_dout,
      ce => internal_dest_ce,
      clr => internal_rst_dest,
      clk => core_clk,
      o => adjusted_rate_core_dout
);
LATCH_CHAN_OUT: synth_reg_w_init
generic map (
      width => chan_out_width,
      init_index => 0,
      init_value => "0",
      latency => 1
)
port map (
      i => core_chan_out,
      ce => internal_dest_ce,
      clr => internal_rst_dest,
      clk => core_clk,
      o => chan_out
);
LATCH_VOUT: synth_reg_w_init
generic map (
      width => 1,
      init_index => 0,
      init_value => "0",
      latency => 1
)
port map (
      i => ored_latched_core_rdy_vec,
      ce => internal_dest_ce,
      clr => internal_rst_dest,
      clk => core_clk,
      o => vout_vec
);
vout <= vout_vec(0);
end architecture behavior;
