
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
use work.conv_pkg.all;
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on
entity xlusamp is
    generic (d_width      : integer := 5;
             d_bin_pt     : integer := 2;
             d_arith      : integer := xlUnsigned;
             q_width      : integer := 5;
             q_bin_pt     : integer := 2;
             q_arith      : integer := xlUnsigned;
             en_width     : integer := 1;
             normalized_clock_enable_period    : integer  := 2;
             log_2_normalized_clock_enable_period    : integer  := 1;                        latency      : integer := 0;
             copy_samples : integer := 0);
    port (d        : in std_logic_vector (d_width-1 downto 0);
          src_clk  : in std_logic;
          src_ce   : in std_logic;
          src_clr  : in std_logic;
          dest_clk : in std_logic;
          dest_ce  : in std_logic;
          dest_clr : in std_logic;
          en       : in std_logic_vector(en_width-1 downto 0);
          q        : out std_logic_vector (q_width-1 downto 0));
end xlusamp;
architecture struct of xlusamp is
    component FDSE
        port (q  : out   std_ulogic;
              d  : in    std_ulogic;
              c  : in    std_ulogic;
              s  : in    std_ulogic;
              ce : in    std_ulogic);
    end component;
    attribute syn_black_box of FDSE : component is true;
    attribute fpga_dont_touch of FDSE : component is "true";
    signal zero    : std_logic_vector (q_width-1 downto 0);
    signal internal_mux_sel_gen : std_logic;
    signal internal_mux_sel_gen_shift : std_logic;
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
    signal delay_adjusted_d : std_logic_vector(d_width - 1 downto 0);
    signal internal_ce : std_logic;
begin
  internal_ce <= en(0);
   latency_eq_0 : if (latency = 0) generate
       delay_adjusted_d <= d;
   end generate;
   latency_gt_0 : if (latency > 0) generate
       delay_input : synth_reg
           generic map(
               width => d_width,
               latency => latency
           )
           port map (
               i => d,
               ce => internal_ce,
               clr => '0',
               clk => src_clk,
               o => delay_adjusted_d
            );
   end generate;

   copy_samples_false : if (copy_samples = 0) generate
      zero <= (others => '0');
          generate_input_selector : entity work.xlclockenablegenerator
         generic map(
            period => normalized_clock_enable_period,
            pipeline_regs => 0,
            log_2_period => log_2_normalized_clock_enable_period
          )
          port map(
            clk => dest_clk,
            clr => '0',
            ce => internal_mux_sel_gen
          );

      phase_shift_input_selector : FDSE
         port map (q  => internal_mux_sel_gen_shift,
            d  => internal_mux_sel_gen,
            c  => dest_clk,
            ce => dest_ce,
            s  => src_clr
         );

      q_gen : process (internal_mux_sel_gen_shift, delay_adjusted_d, zero)
      begin
         if (internal_mux_sel_gen_shift = '1') then
            q <= delay_adjusted_d;
         else
            q <= zero;
         end if;
      end process;
   end generate;
   copy_samples_true : if (copy_samples = 1) generate
      q <= delay_adjusted_d;
   end generate;
end architecture struct;
