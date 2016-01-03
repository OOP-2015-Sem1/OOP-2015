
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
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use work.conv_pkg.all;
entity raminfr_[% entity_name %] is
  generic (width : integer := 8);
  port (clk  : in std_logic;
        we   : in std_logic;
        a    : in std_logic_vector(3 downto 0);
        dpra : in std_logic_vector(3 downto 0);
        di   : in std_logic_vector(width-1 downto 0);
        spo  : out std_logic_vector(width-1 downto 0);
        dpo  : out std_logic_vector(width-1 downto 0));
end raminfr_[% entity_name %];
architecture syn of raminfr_[% entity_name %] is
  type ram_type is array (15 downto 0)
    of std_logic_vector(width-1 downto 0);
  signal RAM : ram_type;
begin
  process(clk)
  begin
    if (clk'event and clk = '1') then
      if (we = '1') then
        RAM(conv_integer(a)) <= di;
      end if;
    end if;
  end process;
  spo <= RAM(conv_integer(a));
  dpo <= RAM(conv_integer(dpra));
end syn;
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use work.conv_pkg.all;
entity sync_fifo_[% entity_name %] is
  generic (din_width   : integer := 8;
           din_bin_pt  : integer := 2;
           din_arith   : integer := xlSigned;
           dout_width  : integer := 18;
           dout_bin_pt : integer := 8;
           dout_arith  : integer := xlSigned;
           depth       : integer := 3);
  port (din      : in std_logic_vector (din_width-1 downto 0);
        write_ce : in std_logic;
        read_ce  : in std_logic;
        clr      : in std_logic;
        clk      : in std_logic;
        dout     : out std_logic_vector (dout_width-1 downto 0));
end sync_fifo_[% entity_name %];
architecture behavior of sync_fifo_[% entity_name %] is
  constant depth_minus_1 : unsigned(2 downto 0) := std_logic_vector_to_unsigned(
    integer_to_std_logic_vector (depth-1,3,xlUnsigned));

  component raminfr_[% entity_name %] is
    generic (width : integer := 8);
    port (clk  : in std_logic;
          we   : in std_logic;
          a    : in std_logic_vector(3 downto 0);
          dpra : in std_logic_vector(3 downto 0);
          di   : in std_logic_vector(width-1 downto 0);
          spo  : out std_logic_vector(width-1 downto 0);
          dpo  : out std_logic_vector(width-1 downto 0));
  end component;

  signal not_empty : std_logic := '0';
  signal write_pos : unsigned(2 downto 0) := "000";
  signal read_pos  : unsigned(2 downto 0) := "000";
  signal ram_data  : std_logic_vector(dout_width-1 downto 0) := (others => '0');
  signal write_addr : std_logic_vector(3 downto 0) := (others => '0');
  signal read_addr  : std_logic_vector(3 downto 0) := (others => '0');
begin
  write_addr(2 downto 0) <= unsigned_to_std_logic_vector(write_pos);
  write_addr(3) <= '0';
  read_addr(2 downto 0) <= unsigned_to_std_logic_vector(read_pos);
  read_addr(3) <= '0';

  distributed_ram : raminfr_[% entity_name %]
    generic map (width => dout_width)
    port map (clk  => clk,
              we   => write_ce,
              a    => write_addr,
              dpra => read_addr,
              di   => din,
              spo  => open,
              dpo  => ram_data);
  process (clr, clk)
  begin
    if (rising_edge(clk)) then
      if (clr = '1') then
        write_pos <= (others => '0');
        read_pos <= (others => '0');
        not_empty <= '0';
        dout <= (others => '0');
      else
        if (write_ce = '1') then
          not_empty <= '1';
          if write_pos = depth_minus_1 then
            write_pos <= "000";
          else
            write_pos <= write_pos + 1;
          end if;
        end if;
        if read_ce = '1' then
          if not_empty = '1' then
            if read_pos = depth_minus_1 then
              read_pos <= "000";
            else
              read_pos <= read_pos + 1;
            end if;
          end if;
          dout <= ram_data;
        end if;
      end if;
    end if;
  end process;
end behavior;
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
    din0: in std_logic_vector(din0_width - 1 downto 0);
    vin: in std_logic_vector(0 downto 0) := "1";
    sample_in_ce: in std_logic;
    sample_in_clr: in std_logic;
    sample_in_clk: in std_logic;
    core_ce: in std_logic;
    core_clr: in std_logic;
    core_clk: in std_logic;
    sample_out_ce: in std_logic;
    sample_out_clr: in std_logic;
    sample_out_clk: in std_logic;
    dout0: out std_logic_vector(dout0_width - 1 downto 0);
    vout: out std_logic_vector(0 downto 0)
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
      d: in std_ulogic;
      c: in std_ulogic;
      s: in std_ulogic
    );
  end component;
  attribute syn_black_box of fds: component is true;
  attribute fpga_dont_touch of fds: component is "true";
  component sync_fifo_[% entity_name %]
    generic (
      din_width: integer := 8;
      din_bin_pt: integer := 2;
      din_arith: integer := xlSigned;
      dout_width: integer := 18;
      dout_bin_pt: integer := 8;
      dout_arith: integer := xlSigned;
      depth: integer := 3
    );
    port (
      din: in std_logic_vector(din_width - 1 downto 0);
      write_ce: in std_logic;
      read_ce: in std_logic;
      clr: in std_logic;
      clk: in std_logic;
      dout: out std_logic_vector(dout_width - 1 downto 0)
    );
  end component;
  signal nd: std_logic;
  signal rfd: std_logic;
  signal core_rdy: std_logic;
  signal registered_rdy: std_logic;
  signal sampled_dout0: std_logic_vector(c_result_width-1 downto 0) :=
    (others => '0');
  signal raw_dout0: std_logic_vector(c_result_width - 1 downto 0);
  signal core_dout0: std_logic_vector(c_result_width - 1 downto 0);
  signal registered_dout0: std_logic_vector(c_result_width - 1 downto 0) :=
    (others => '0');
  signal del_sample_in_ce: std_logic;
  signal clr_pulse, fifo_clr: std_logic;
  signal internal_clr:std_logic;

  -- synopsys translate_off
  signal real_din0, real_core_dout0, real_sampled_dout0,
    real_sampled_dout0_dly, real_dout0: real;
  -- synopsys translate_on
  component  [% core_name0 %]
    port (
      nd: in std_logic;
      rdy: out std_logic;
      clk: in std_logic;
      rst: in std_logic;
      rfd: out std_logic;
      din: in std_logic_vector(din0_width - 1 downto 0);
      dout: out std_logic_vector(c_result_width - 1 downto 0)
    );
  end component;
  attribute syn_black_box of [% core_name0 %] : component is true;
  attribute fpga_dont_touch of [% core_name0 %] : component is "true";
begin
  -- synopsys translate_off
  -- synopsys translate_on
  sample_in_ce_reg: fds
    port map (
      q => del_sample_in_ce,
      d => sample_in_ce,
      c => core_clk,
      s => core_clr
    );
  nd <= vin(0) and del_sample_in_ce;
    core_instance : [% core_name0 %]
      port map (
        nd => nd,
        rdy => core_rdy,
        clk => core_clk,
        rst => core_clr,
        rfd => rfd,
        din => din0,
        dout => core_dout0
      );

  reg_core_output: process (core_clk, core_clr)
  begin
    if core_clr = '1' then
      registered_dout0 <= (others => '0');
    elsif core_clk'event and core_clk = '1' then
      if core_rdy = '1' then
        registered_dout0 <= core_dout0;
      end if;
      registered_rdy <= core_rdy;
    end if;
  end process;
  valid_pipe: synth_reg_w_init
    generic map (
      width => 1,
      init_index => 0,
      latency => latency
    )
    port map (
      i => vin,
      ce => sample_out_ce,
      clr => sample_out_clr,
      clk => core_clk,
      o => vout
    );
  polyphase_eq_1: if not((interpolating = 1) and (c_polyphase_factor > 1))
  generate
    sample_output: process (core_clk)
    begin
      if core_clk'event and core_clk = '1' then
        if sample_out_ce = '1' then
          sampled_dout0 <= registered_dout0;
        end if;
      end if;
    end process;
  end generate;
  polyphase_gt_1: if (interpolating = 1) and (c_polyphase_factor > 1)
  generate
   fifo_clr_ff: fds
    port map (
      q => clr_pulse,
      d => '0',
      c => core_clk,
      s => core_clr
    );
    fifo_clr <= clr_pulse or sample_out_clr;

    sync_fifo_comp: sync_fifo_[% entity_name %]
      generic map (
        din_width => c_result_width,
        din_bin_pt => dout0_bin_pt,
        din_arith => dout0_arith,
        dout_width => c_result_width,
        dout_bin_pt => dout0_bin_pt,
        depth => c_polyphase_factor
      )
      port map (
        din => registered_dout0,
        write_ce => registered_rdy,
        read_ce => sample_out_ce,
        clr => fifo_clr,
        clk => core_clk,
        dout => sampled_dout0
      );
  end generate;
  latency_test: if (extra_registers > 0)
  generate
    reg: synth_reg
      generic map (
        width => c_result_width,
        latency => extra_registers
      )
      port map (
        i => sampled_dout0,
        ce => sample_out_ce,
        clr => sample_out_clr,
        clk => core_clk,
        o => raw_dout0
      );
  end generate;
  latency0: if ((latency = 0) or (extra_registers = 0))
  generate
    raw_dout0 <= sampled_dout0;
  end generate;
  sign_extend: process (raw_dout0)
  begin
    if dout0_arith = xlUnsigned then
      dout0 <= zero_ext(raw_dout0, dout0_width);
    else
      dout0 <= sign_ext(raw_dout0, dout0_width);
    end if;
  end process;
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
    sample_in_ce: in std_logic;
    sample_in_clk: in std_logic;
    core_ce: in std_logic;
    core_clk: in std_logic;
    sample_out_ce: in std_logic;
    sample_out_clk: in std_logic
  );
end [% entity_name %] ;
architecture behavior of [% entity_name %] is
begin
   core_instance: entity work.wrapped_[% entity_name %]
    port map (
      [% port_instance %]
      core_ce => core_ce,
      core_clk => core_clk,
      core_clr => rst,
      sample_in_ce => sample_in_ce,
      sample_in_clk => sample_in_clk,
      sample_in_clr => rst,
      sample_out_ce => sample_out_ce,
      sample_out_clk => sample_out_clk,
      sample_out_clr => rst
    );
end behavior;
