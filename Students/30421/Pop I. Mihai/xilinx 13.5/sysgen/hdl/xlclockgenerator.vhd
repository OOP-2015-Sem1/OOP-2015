
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
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on
entity xlclockgenerator is
    generic (ensure_clks_stable       : boolean := false;
             lcm_select               : integer := 0;
             frequency_mode           : string  := "low";
             clkdv_divide_generic     : real    := 2.0;
             clkdv_divide_attribute   : string  := "2.0";
             clkfx_multiply_generic   : integer := 4;
             clkfx_multiply_attribute : string  := "4";
             clkfx_divide_generic     : integer := 1;
             clkfx_divide_attribute   : string  := "1");
    port (clk   : in std_logic;
          rst   : in std_logic;
          clk0  : out std_logic;
          clk2x : out std_logic;
          clkfx : out std_logic;
          clkdv : out std_logic;
          lock  : out std_logic);
end xlclockgenerator;
architecture structural of xlclockgenerator is
   component bufg
      port(i: in std_logic;
           o: out std_logic);
   end component;
   component bufgmux
      port(i0 : in std_logic;
           i1 : in std_logic;
           s  : in std_logic;
           o  : out std_logic);
   end component;
   component dcm
      -- synopsys translate_off
      generic (clkout_phase_shift    : string := "fixed";
               dll_frequency_mode    : string := "low";
               duty_cycle_correction : boolean := true;
               clkdv_divide          : real := 2.0;
               clkfx_multiply        : integer := 4;
               clkfx_divide          : integer := 1);
      -- synopsys translate_on
      port (clkin    : in  std_logic;
            clkfb    : in  std_logic;
            dssen    : in  std_logic;
            psincdec : in  std_logic;
            psen     : in  std_logic;
            psclk    : in  std_logic;
            rst      : in  std_logic;
            clk0     : out std_logic;
            clk90    : out std_logic;
            clk180   : out std_logic;
            clk270   : out std_logic;
            clk2x    : out std_logic;
            clk2x180 : out std_logic;
            clkdv    : out std_logic;
            clkfx    : out std_logic;
            clkfx180 : out std_logic;
            locked   : out std_logic;
            psdone   : out std_logic;
            status   : out std_logic_vector(7 downto 0));
   end component;
   attribute dll_frequency_mode    : string;
   attribute duty_cycle_correction : string;
   attribute startup_wait          : string;
   attribute clkdv_divide          : string;
   attribute clkfx_multiply        : string;
   attribute clkfx_divide          : string;
   attribute duty_cycle_correction of dcm_comp1 : label is "true";
   attribute startup_wait of dcm_comp1          : label is "false";
   attribute dll_frequency_mode of dcm_comp1    : label is frequency_mode;
   attribute clkdv_divide of dcm_comp1   : label is clkdv_divide_attribute;
   attribute clkfx_multiply of dcm_comp1 : label is clkfx_multiply_attribute;
   attribute clkfx_divide of dcm_comp1   : label is clkfx_divide_attribute;
   component fdre
      port(q  : out   std_ulogic;
           d  : in    std_ulogic;
           c  : in    std_ulogic;
           r  : in    std_ulogic;
           ce : in    std_ulogic);
   end component;
   signal feedback    : std_logic;
   signal clklcm      : std_logic;
   signal clk0unbuf   : std_logic;
   signal clk0buf     : std_logic;
   signal clk2xunbuf  : std_logic;
   signal clkfxunbuf  : std_logic;
   signal clkdvunbuf  : std_logic;
   signal clkswitch   : std_logic;
   signal intlock     : std_logic;
begin
   dcm_comp1: dcm
      -- synopsys translate_off
      generic map (dll_frequency_mode => frequency_mode,
                   clkdv_divide       => clkdv_divide_generic,
                   clkfx_multiply     => clkfx_multiply_generic,
                   clkfx_divide       => clkfx_divide_generic)
      -- synopsys translate_on
      port map (clkin    => clk,
                clkfb    => feedback,
                dssen    => '0',
                psincdec => '0',
                psen     => '0',
                psclk    => '0',
                rst      => rst,
                clk0     => clk0unbuf,
                clk2x    => clk2xunbuf,
                clkfx    => clkfxunbuf,
                clkdv    => clkdvunbuf,
                locked   => intlock);
   bufgmux_clk0: bufgmux
      port map (i0 => '0',
                i1 => clk0unbuf,
                o  => clk0buf,
                s  => clkswitch);
   bufgmux_clk2x: bufgmux
      port map (i0 => '0',
                i1 => clk2xunbuf,
                o  => clk2x,
                s  => clkswitch);
   bufgmux_clkfx: bufgmux
      port map (i0 => '0',
                i1 => clkfxunbuf,
                o  => clkfx,
                s  => clkswitch);
   bufgmux_clkdv: bufgmux
      port map (i0 => '0',
                i1 => clkdvunbuf,
                o  => clkdv,
                s  => clkswitch);
   stability_req: if (ensure_clks_stable = true) generate
      mux_ctrl_reg: fdre
         port map (d  => intlock,
                   q  => clkswitch,
                   ce => '1',
                   c  => clklcm,
                   r  => rst);
      bufg_feedback: bufg
         port map(i => clk0unbuf,
                  o => feedback);
      lcm_is_clk0: if (lcm_select = 0) generate
         clklcm <= feedback;
      end generate;
      lcm_is_clkdv: if (lcm_select = 1) generate
         bufg_lcmclk: bufg
            port map(i => clkdvunbuf,
                     o => feedback);
      end generate;
   end generate;
   stability_not_req: if (ensure_clks_stable = false) generate
      feedback  <= clk0buf;
      clkswitch <= '1';
   end generate;
   lock <= intlock;
   clk0 <= clk0buf;
end architecture structural;
