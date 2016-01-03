
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
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
entity [% entity_name %] is
  generic (
    [% generic_map %]
  );
  port (
    din: in std_logic_vector(din0_width - 1 downto 0);
    dout: out std_logic_vector(dout0_width - 1 downto 0);
    vin: in std_logic_vector(0 downto 0);
    vout: out std_logic_vector(0 downto 0);
    rfd: out std_logic_vector(0 downto 0);
    ld_din: in std_logic_vector(c_coeff_width - 1 downto 0);
    ld_we: in std_logic_vector(0 downto 0);
    coef_ld: in std_logic_vector(0 downto 0);
    sel_in: out std_logic_vector(sel_width - 1 downto 0);
    sel_out: out std_logic_vector(sel_width - 1 downto 0);
    ce: in std_logic;
    rst: in std_logic := '0';
    clk: in std_logic);
  end [% entity_name %];
architecture behavior of [% entity_name %] is
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
  component fds
    port (
      q: out std_ulogic;
      d: in  std_ulogic;
      c: in  std_ulogic;
      s: in  std_ulogic
    );
  end component;
  attribute syn_black_box of fds: component is true;
  attribute fpga_dont_touch of fds: component is "true";
  component [% core_name0 %]
    port (
      ND: in  std_logic;
      RDY: out std_logic;
      CLK: in  std_logic;
      RST: in std_logic;
      RFD: out std_logic;
      LD_WE: in std_logic;
      COEF_LD: in std_logic;
      LD_DIN: in std_logic_vector(c_coeff_width - 1 downto 0);
      DIN: in  std_logic_vector(din0_width - 1 downto 0);
      SEL_I: out std_logic_vector([% sel_width %] - 1 downto 0);
      SEL_O: out std_logic_vector([% sel_width %] - 1 downto 0);
      DOUT: out std_logic_vector(c_result_width - 1 downto 0)
    );
  end component;
  attribute syn_black_box of [% core_name0 %]:
    component is true;
  attribute fpga_dont_touch of [% core_name0 %]:
    component is "true";
  signal nd, clr: std_logic;
  signal core_rdy: std_logic;
  signal registered_rdy: std_logic;
  signal core_dout: std_logic_vector(c_result_width - 1 downto 0);
  signal sampled_dout: std_logic_vector(c_result_width - 1 downto 0) :=
    (others => '0');
  signal raw_dout: std_logic_vector(c_result_width - 1 downto 0);
  signal del_core_ce: std_logic;
  signal temp_vout: std_logic_vector(0 downto 0);
  signal temp_rfd: std_logic_vector(0 downto 0);
  signal temp_sel_out: std_logic_vector(sel_width - 1 downto 0);
-- synopsys translate_off
  signal real_din0, real_din1: real;
  signal real_core_dout, real_sampled_dout, real_sampled_dout_dly,
    real_dout: real;
-- synopsys translate_on
begin
  -- synopsys translate_off
  -- synopsys translate_on
  clr <= rst;
  core_ce_reg: fds
    port map (
      q => del_core_ce,
      d => ce,
      c => clk,
      s => clr
    );
  nd <= vin(0) and ce;
    core_instance: [% core_name0 %]
      port map (
        ND => vin(0),
        RDY => temp_vout(0),
        CLK => clk,
        RST => clr,
        RFD => rfd(0),
        DIN => din,
        LD_WE => ld_we(0),
        COEF_LD => coef_ld(0),
        LD_DIN => ld_din,
        SEL_I => sel_in,
        SEL_O => temp_sel_out,
        DOUT => core_dout
      );
  latency_gt_0: if (extra_registers > 0) generate
    reg: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => core_dout,
        ce => ce,
        clr => clr,
        clk => clk,
        o => raw_dout
      );
    vout_reg: synth_reg
      generic map (
        width => 1,
        latency => extra_registers
      )
      port map (
        i => temp_vout,
        ce => ce,
        clr => clr,
        clk => clk,
        o => vout
      );
    sel_out_reg: synth_reg
      generic map (
        width => sel_width,
        latency => extra_registers
      )
      port map (
        i => temp_sel_out,
        ce => ce,
        clr => clr,
        clk => clk,
        o => sel_out
      );
  end generate;
  latency_eq_0: if ((latency = 0) or (extra_registers = 0)) generate
    raw_dout <= core_dout;
    vout <= temp_vout;
    sel_out <= temp_sel_out;
  end generate;
  sign_extend: process(raw_dout)
  begin
    if (dout0_arith = xlUnsigned) then
      dout <= zero_ext(raw_dout, dout0_width);
    else
      dout <= sign_ext(raw_dout, dout0_width);
    end if;
  end process;
end architecture behavior;
