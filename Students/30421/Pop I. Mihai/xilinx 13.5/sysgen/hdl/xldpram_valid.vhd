
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
entity xldpram_valid is
  generic (
    core_name0: string := "";
    c_width_a: integer := 13;
    c_depth_a: integer := 16;
    c_address_width_a: integer := 4;
    c_pipe_stages_a: integer := 0;
    c_write_modea: integer := 0;
    c_width_b: integer := 13;
    c_depth_b: integer := 16;
    c_address_width_b: integer := 4;
    c_write_modeb: integer := 0;
    c_pipe_stages_b: integer := 0;
    c_sinita_value: string := "0";
    c_sinitb_value: string := "0";
                -- synopsys translate_off
    c_mem_init_file: string := "null.mif";
                -- synopsys translate_on
    uid: integer := 0;
    latency: integer := 1
  );
  port (
    dina: in std_logic_vector(c_width_a - 1 downto 0);
    addra: in std_logic_vector(c_address_width_a - 1 downto 0);
    wea: in std_logic_vector(0 downto 0);
    a_ce: in std_logic;
    a_clr: in std_logic;
    a_clk: in std_logic;
    rst_a: in std_logic_vector(0 downto 0) := (others => '0');
    douta: out std_logic_vector(c_width_a - 1 downto 0);
    vouta: out std_logic_vector(0 downto 0);
    dinb: in std_logic_vector(c_width_b - 1 downto 0);
    addrb: in std_logic_vector(c_address_width_b - 1 downto 0);
    web: in std_logic_vector(0 downto 0);
    b_ce: in std_logic;
    b_clr: in std_logic;
    b_clk: in std_logic;
    rst_b: in std_logic_vector(0 downto 0) := (others => '0');
    doutb: out std_logic_vector(c_width_b - 1 downto 0);
    voutb: out std_logic_vector(0 downto 0)
  );
end xldpram_valid ;
architecture behavior of xldpram_valid is
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
  component synth_reg_w_init
    generic (
      width: integer;
      init_index: integer;
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
  signal addra_valid_vec_in, addra_valid_vec_out: std_logic_vector(0 downto 0);
  signal addrb_valid_vec_in, addrb_valid_vec_out: std_logic_vector(0 downto 0);
  signal core_addra: std_logic_vector(c_address_width_a - 1 downto 0);
  signal core_addrb: std_logic_vector(c_address_width_b - 1 downto 0);
  signal high_addra: std_logic_vector(c_address_width_b - 1 downto 0);
  signal core_dina, core_douta, dly_douta:
    std_logic_vector(c_width_a - 1 downto 0);
  signal core_dinb, core_doutb, dly_doutb:
    std_logic_vector(c_width_b - 1 downto 0);
  signal dly_addra_valid, dly_addrb_valid: std_logic;
  signal core_wea, core_web, mod_core_wea, mod_core_web: std_logic;
  signal core_a_ce, core_b_ce: std_logic;
  signal rw_error_a, rw_error_b: std_logic;
  signal rw_error_a_dly, rw_error_b_dly: std_logic;
  signal rw_error_a_vec_in, rw_error_a_vec_out: std_logic_vector(0 downto 0);
  signal rw_error_b_vec_in, rw_error_b_vec_out: std_logic_vector(0 downto 0);
  signal writeError: std_logic;
  signal sinita, sinitb, not_writeError: std_logic;
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   comp_def = core_component_def.$i                                      -%]
  component [% name %]
    port (
      addra: in std_logic_vector(c_address_width_a - 1 downto 0);
      addrb: in std_logic_vector(c_address_width_b - 1 downto 0);
      dina: in std_logic_vector(c_width_a - 1 downto 0);
      dinb: in std_logic_vector(c_width_b - 1 downto 0);
      clka: in std_logic;
      clkb: in std_logic;
      wea: in std_logic;
      web: in std_logic;
      ena: in std_logic;
      enb: in std_logic;
      sinita: in std_logic;
      sinitb: in std_logic;
      douta: out std_logic_vector(c_width_a - 1 downto 0);
      doutb: out std_logic_vector(c_width_b - 1 downto 0)
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
  rw_error_detection_a: process(a_ce, b_ce, high_addra, core_addrb,
                                core_wea, core_web)
  begin
    if ((a_ce = '1') and (b_ce = '1') and (high_addra = core_addrb)) then
      if ((core_wea = '1') and (core_web = '1')) then
          writeError <= '1';
      else
          writeError <= '0';
      end if;
      if ( ((core_wea = '0') and (core_web = '1') and not(c_write_modeb = 1 ))
         or ((core_wea = '1') and (core_web = '1')and not(c_write_modea = 2 ))) then
        rw_error_a <= '1';
      else
        if ( (core_wea = '1') and (core_web = '0') and
          ( c_write_modea = 2) ) then
          rw_error_a <= rw_error_a_dly;
        else
          rw_error_a <= '0';
        end if;
      end if;
    else
      writeError <= '0';
      if ((c_write_modea = 2) and (core_wea = '1')) then
        rw_error_a <= rw_error_a_dly;
      else
        rw_error_a <= '0';
      end if;
    end if;
  end process;
  dly_rw_error_a_case : if c_write_modea = 2 generate
    rw_error_a_vec_in(0) <= rw_error_a;
    rw_error_a_reg: synth_reg_w_init
        generic map (
          width => 1,
          init_index => 0,
          latency => 1
        )
      port map (
        i => rw_error_a_vec_in,
        ce => a_ce,
        clr => a_clr,
        clk => a_clk,
        o => rw_error_a_vec_out
      );
    rw_error_a_dly <= rw_error_a_vec_out(0);
  end generate;
  rw_error_detection_b: process (a_ce, b_ce, high_addra, core_addrb,
                                 core_wea, core_web)
  begin
    if ((a_ce = '1') and (b_ce = '1') and (high_addra = core_addrb)) then
      if ( ((core_web = '0') and (core_wea = '1') and not(c_write_modea = 1 ))
            or ((core_wea = '1') and (core_web = '1')and not(c_write_modeb = 2 )) ) then
        rw_error_b <= '1';
      else
        if ((core_web = '1') and (core_wea = '0') and
                 (c_write_modeb = 2)) then
          rw_error_b <= rw_error_b_dly;
        else
          rw_error_b <= '0';
        end if;
      end if;
    else
      if ((c_write_modeb = 2) and (core_web = '1')) then
        rw_error_b <= rw_error_b_dly;
      else
        rw_error_b <= '0';
      end if;
    end if;
  end process;
  dly_rw_error_b_case: if c_write_modeb = 2 generate
    rw_error_b_vec_in(0) <= rw_error_b;
    rw_error_b_reg: synth_reg_w_init
      generic map (
        width => 1,
        init_index => 0,
        latency => 1
      )
      port map (
        i => rw_error_b_vec_in,
        ce => b_ce,
        clr => b_clr,
        clk => b_clk,
        o => rw_error_b_vec_out
      );
    rw_error_b_dly <= rw_error_b_vec_out(0);
  end generate;
  core_addra <= addra;
  high_addra(c_address_width_b - 1 downto 0) <=
    core_addra(c_address_width_a - 1 downto
      (c_address_width_a-c_address_width_b));
  core_dina <= dina;
  douta <= dly_douta;
  vouta(0) <=  dly_addra_valid;
  core_wea <= wea(0);
  mod_core_wea <= wea(0) and not(writeError);
  core_a_ce <= a_ce;
  sinita <= (rst_a(0) or a_clr) and a_ce;
  addra_valid_vec_in(0) <= not(rw_error_a);
  synth_reg_instA: synth_reg_w_init
    generic map (
      width => 1,
      init_index => 1,
      latency => latency
    )
    port map (
      i => addra_valid_vec_in,
      ce => a_ce,
      clr => sinita,
      clk => a_clk,
      o => addra_valid_vec_out
    );
  dly_addra_valid <= addra_valid_vec_out(0);
  core_addrb <= addrb;
  core_dinb <= dinb;
  doutb <= dly_doutb;
  voutb(0) <=  dly_addrb_valid;
  core_web <= web(0);
  mod_core_web <= web(0) and not(writeError);
  core_b_ce <= b_ce;
  sinitb <= (rst_b(0) or b_clr) and b_ce;
  addrb_valid_vec_in(0) <= not(rw_error_b);
  synth_reg_instB: synth_reg_w_init
      generic map (
        width => 1,
        init_index => 1,
        latency => latency
      )
      port map (
        i => addrb_valid_vec_in,
        ce => b_ce,
        clr => sinitb,
        clk => b_clk,
        o => addrb_valid_vec_out
      );
  dly_addrb_valid <= addrb_valid_vec_out(0);
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                      -%]
  comp[% i %]: if ((core_name0 = "[% name %]")) generate
    core_instance[% i %]: [% name %]
      port map (
        addra => core_addra,
        clka => a_clk,
        addrb => core_addrb,
        clkb => b_clk,
        dina => core_dina,
        wea => mod_core_wea,
        dinb => core_dinb,
        web => mod_core_web,
        ena => core_a_ce,
        enb => core_b_ce,
        sinita => sinita,
        sinitb => sinitb,
        douta => core_douta,
        doutb => core_doutb
      );
  end generate;
[% END                                                                     -%]
  latency_test : if (latency > 1) generate
    regA: synth_reg
      generic map (
        width => c_width_a,
        latency => latency - 1 -  c_pipe_stages_a
      )
      port map (
        i => core_douta,
        ce => a_ce,
        clr => a_clr,
        clk => a_clk,
        o => dly_douta
      );
    regB: synth_reg
      generic map (
        width => c_width_b,
        latency => latency - 1 -  c_pipe_stages_b
      )
      port map (
        i => core_doutb,
        ce => b_ce,
        clr => b_clr,
        clk => b_clk,
        o => dly_doutb
      );
  end generate;
  latency1 : if (latency <= 1) generate
    dly_douta <= core_douta;
    dly_doutb <= core_doutb;
  end generate;
end behavior;
