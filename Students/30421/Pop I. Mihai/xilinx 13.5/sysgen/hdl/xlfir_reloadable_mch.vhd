
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
entity wrapped_[% entity_name %] is
  generic (
    [% generic_map %]
  );
  port (
    din0: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din1: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din2: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din3: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din4: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din5: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din6: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    din7: in std_logic_vector(din0_width - 1 downto 0) := (others => '0');
    dout0: out std_logic_vector(dout0_width - 1 downto 0);
    dout1: out std_logic_vector(dout0_width - 1 downto 0);
    dout2: out std_logic_vector(dout0_width - 1 downto 0);
    dout3: out std_logic_vector(dout0_width - 1 downto 0);
    dout4: out std_logic_vector(dout0_width - 1 downto 0);
    dout5: out std_logic_vector(dout0_width - 1 downto 0);
    dout6: out std_logic_vector(dout0_width - 1 downto 0);
    dout7: out std_logic_vector(dout0_width - 1 downto 0);
    ld_din: in std_logic_vector(c_coeff_width - 1  downto 0);
    ld_we: in std_logic_vector(0 downto 0);
    coef_ld: in std_logic_vector(0 downto 0);
    vin: in std_logic_vector(0 downto 0) := (others => '1');
    rfd: out std_logic_vector(0 downto 0);
    vout: out std_logic_vector(0 downto 0);
    sample_in_ce: in std_logic;
    sample_in_clr: in std_logic;
    sample_in_clk: in std_logic;
    core_ce: in std_logic;
    core_clr: in std_logic;
    core_clk: in std_logic;
    sample_out_ce: in std_logic;
    sample_out_clr: in std_logic;
    sample_out_clk: in std_logic
  );
end wrapped_[% entity_name %] ;
architecture behavior of wrapped_[% entity_name %] is
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
  component fdre
-- synopsys translate_off
    generic (
      INIT: bit := '0'
    );
-- synopsys translate_on
    port (
      q: out std_ulogic;
      d: in std_ulogic;
      c: in std_ulogic;
      r: in std_ulogic;
      ce: in std_ulogic
    );
  end component;
  attribute syn_black_box of fdre: component is true;
  attribute fpga_dont_touch of fdre: component is "true";
  attribute INIT: string;
  attribute INIT of rfd_reg: label is "1";
  component [% core_name0 %]
    port (
      ND: in std_logic;
      RDY: out std_logic;
      CLK: in std_logic;
      RST: in std_logic;
      RFD: out std_logic;
      LD_WE: in std_logic;
      COEF_LD: in std_logic;
      LD_DIN: in std_logic_vector(c_coeff_width - 1 downto 0);
      DIN: in std_logic_vector(din0_width - 1 downto 0);
      SEL_I: out std_logic_vector([% sel_width %] - 1 downto 0);
      SEL_O: out std_logic_vector([% sel_width %] - 1 downto 0);
      DOUT: out std_logic_vector(c_result_width - 1 downto 0)
    );
  end component;
  attribute syn_black_box of [% core_name0 %]:
    component is true;
  attribute fpga_dont_touch of [% core_name0 %]:
    component is "true";
  signal nd: std_logic;
  signal we: std_logic;
  signal ld: std_logic;
  signal rfd_delayed: std_logic;
  signal core_rfd: std_logic;
  signal rfd_reg_ce: std_logic;
  signal int_rfd: std_logic;
  signal core_rdy: std_logic;
  signal registered_rdy: std_logic;
  signal pre_vout: std_logic_vector(0 downto 0);
  signal int_vin: std_logic_vector(0 downto 0);
  signal core_dout: std_logic_vector(c_result_width - 1 downto 0);
  signal
    reg_dout0, reg_dout1, reg_dout2, reg_dout3,
    reg_dout4, reg_dout5, reg_dout6, reg_dout7,
    aligned_dout0, aligned_dout1, aligned_dout2, aligned_dout3,
    aligned_dout4, aligned_dout5, aligned_dout6, aligned_dout7,
    sampled_dout0, sampled_dout1, sampled_dout2, sampled_dout3,
    sampled_dout4, sampled_dout5, sampled_dout6, sampled_dout7:
      std_logic_vector(c_result_width - 1 downto 0) := (others => '0');
  signal sel_i: std_logic_vector(2 downto 0) := (others => '0');
  signal sel_o: std_logic_vector(2 downto 0) := (others => '0');
  signal del_core_ce: std_logic;
  signal muxed_din: std_logic_vector(din0_width - 1 downto 0) :=
    (others => '0');
-- synopsys translate_off
  signal real_din0, real_din1 :real;
  signal real_core_dout, real_sampled_dout,
    real_sampled_dout_dly, real_dout: real;
-- synopsys translate_on
begin
  -- synopsys translate_off
  -- synopsys translate_on
  rfd(0) <= int_rfd;
  rfd_delayed <= core_rfd after 200 ps;
  core_ce_reg: fds
    port map (
      q => del_core_ce,
      d => core_ce,
      c => core_clk,
      s => core_clr
    );
  rfd_reg_ce <= sample_in_ce and rfd_delayed;
  rfd_reg: fdre
-- synopsys translate_off
    generic map (
      INIT => '1'
    )
-- synopsys translate_on
    port map (
      q => int_rfd,
      d => '1',
      c => core_clk,
      r => ld,
      ce => rfd_reg_ce
    );
  nd <= vin(0) and del_core_ce and int_rfd;
  we <= ld_we(0) and sample_in_ce;
  ld <= coef_ld(0) and sample_in_ce;
    core_instance: [% core_name0 %]
      port map (
        ND => nd,
        RDY => core_rdy,
        CLK => core_clk,
        RST => core_clr,
        RFD => core_rfd,
        LD_WE => we,
        COEF_LD => ld,
        LD_DIN => ld_din,
        DIN => muxed_din,
        SEL_I => sel_i([% sel_width %] - 1 downto 0),
        SEL_O => sel_o([% sel_width %] - 1 downto 0),
        DOUT => core_dout
      );
  din_mux: process(sel_i, din0, din1, din2, din3, din4, din5, din6, din7)
  begin
    case sel_i is
      when "000"  => muxed_din <= din0;
      when "001"  => muxed_din <= din1;
      when "010"  => muxed_din <= din2;
      when "011"  => muxed_din <= din3;
      when "100"  => muxed_din <= din4;
      when "101"  => muxed_din <= din5;
      when "110"  => muxed_din <= din6;
      when others => muxed_din <= din7;
    end case;
  end process;
  reg_core_output: process(core_clk, core_clr)
  begin
    if (core_clk'event and core_clk = '1') then
      if (core_clr = '1') then
        reg_dout0 <= (others => '0');
        reg_dout1 <= (others => '0');
        reg_dout2 <= (others => '0');
        reg_dout3 <= (others => '0');
        reg_dout4 <= (others => '0');
        reg_dout5 <= (others => '0');
        reg_dout6 <= (others => '0');
        reg_dout7 <= (others => '0');
        registered_rdy <= '0';
      else
        if (core_rdy = '1') then
          if (sel_o = "000") then reg_dout0 <= core_dout; end if;
          if (sel_o = "001") then reg_dout1 <= core_dout; end if;
          if (sel_o = "010") then reg_dout2 <= core_dout; end if;
          if (sel_o = "011") then reg_dout3 <= core_dout; end if;
          if (sel_o = "100") then reg_dout4 <= core_dout; end if;
          if (sel_o = "101") then reg_dout5 <= core_dout; end if;
          if (sel_o = "110") then reg_dout6 <= core_dout; end if;
          if (sel_o = "111") then reg_dout7 <= core_dout; end if;
        end if;
        registered_rdy <= core_rdy;
     end if;
    end if;
  end process;
  valid_pipe_w_clr: synth_reg_w_init
    generic map (
      width => 1,
      latency => latency-extra_registers,
      init_index => 0
    )
    port map (
      i => int_vin,
      ce => sample_out_ce,
      clr => ld,
      clk => core_clk,
      o => pre_vout
    );
  int_vin(0) <= vin(0) and int_rfd;
  polyphase_eq_1: if (interpolating /= 1) or (c_polyphase_factor <= 1) generate
    sample_output0: process(core_clk, core_clr)
    begin
      if (core_clk'event and core_clk = '1') then
        if (core_clr = '1') then
          aligned_dout0 <= (others => '0');
          aligned_dout1 <= (others => '0');
          aligned_dout2 <= (others => '0');
          aligned_dout3 <= (others => '0');
          aligned_dout4 <= (others => '0');
          aligned_dout5 <= (others => '0');
          aligned_dout6 <= (others => '0');
          aligned_dout7 <= (others => '0');
          sampled_dout0 <= (others => '0');
          sampled_dout1 <= (others => '0');
          sampled_dout2 <= (others => '0');
          sampled_dout3 <= (others => '0');
          sampled_dout4 <= (others => '0');
          sampled_dout5 <= (others => '0');
          sampled_dout6 <= (others => '0');
          sampled_dout7 <= (others => '0');
        else
          if (sel_o = "000") then
            aligned_dout0 <= reg_dout0;
            aligned_dout1 <= reg_dout1;
            aligned_dout2 <= reg_dout2;
            aligned_dout3 <= reg_dout3;
            aligned_dout4 <= reg_dout4;
            aligned_dout5 <= reg_dout5;
            aligned_dout6 <= reg_dout6;
            aligned_dout7 <= reg_dout7;
          end if;
          if (sample_out_ce = '1') then
            sampled_dout0 <= aligned_dout0;
            sampled_dout1 <= aligned_dout1;
            sampled_dout2 <= aligned_dout2;
            sampled_dout3 <= aligned_dout3;
            sampled_dout4 <= aligned_dout4;
            sampled_dout5 <= aligned_dout5;
            sampled_dout6 <= aligned_dout6;
            sampled_dout7 <= aligned_dout7;
          end if;
        end if;
      end if;
    end process;
  end generate;
  latency_gt_0: if (extra_registers > 0) generate
    reg0: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout0,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout0
      );
    reg1: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout1,
        ce  => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout1
      );
    reg2: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout2,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout2
      );
    reg3: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout3,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout3
      );
    reg4: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout4,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout4
      );
    reg5: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout5,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout5
      );
    reg6: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout6,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout6
      );
    reg7: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout7,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => dout7
      );
    valid_pipe: synth_reg_w_init
      generic map (
        width => 1,
        init_index => 0,
        latency => extra_registers
      )
      port map (i => pre_vout,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => vout
      );
  end generate;
  latency_eq_0: if ((latency = 0) or (extra_registers = 0)) generate
    dout0 <= sampled_dout0;
    dout1 <= sampled_dout1;
    dout2 <= sampled_dout2;
    dout3 <= sampled_dout3;
    dout4 <= sampled_dout4;
    dout5 <= sampled_dout5;
    dout6 <= sampled_dout6;
    dout7 <= sampled_dout7;
    vout <= pre_vout;
  end generate;
end architecture behavior;
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
entity [% entity_name %] is
  generic (
    [% generic_map %]
  );
  port (
    [% port_map %]
    rst: in std_logic := '0';
    ld_din: in std_logic_vector(c_coeff_width - 1 downto 0);
    ld_we: in std_logic_vector(0 downto 0);
    coef_ld: in std_logic_vector(0 downto 0);
    sample_in_ce: in std_logic;
    sample_in_clk: in std_logic;
    core_ce: in std_logic;
    core_clk: in std_logic;
    sample_out_ce: in std_logic;
    sample_out_clk: in std_logic;
    rfd: out std_logic_vector(0 downto 0)
  );
end [% entity_name %] ;
architecture behavior of [% entity_name %] is
begin

   core_instance: entity work.wrapped_[% entity_name %]
    port map (
      [% port_instance %]
      ld_din => ld_din,
      ld_we => ld_we,
      coef_ld => coef_ld,
      core_ce => core_ce,
      core_clk => core_clk,
      core_clr => rst,
      sample_in_ce => sample_in_ce,
      sample_in_clk => sample_in_clk,
      sample_in_clr => rst,
      sample_out_ce => sample_out_ce,
      sample_out_clk => sample_out_clk,
      sample_out_clr => rst,
      rfd => rfd
    );
end behavior;
