
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
use IEEE.numeric_std.all;
use work.conv_pkg.all;
entity xlinv is
   generic (din_width   : integer := 12;
            din_bin_pt  : integer := 7;
            din_arith   : integer := xlSigned;
            dout_width  : integer := 12;
            dout_bin_pt : integer := 7;
            dout_arith  : integer := xlSigned;
            en_width    : integer := 5;
            en_bin_pt   : integer := 2;
            en_arith    : integer := xlUnsigned;
            latency     : integer := 1);
   port (din  : in std_logic_vector (din_width-1 downto 0);
         dout : out std_logic_vector (dout_width-1 downto 0);
         ce   : in std_logic;
         clr  : in std_logic;
         clk  : in std_logic;
         en   : in std_logic_vector (en_width-1 downto 0));
end xlinv;
architecture dataflow of xlinv is
   component synth_reg
      generic (width   : integer;
               latency : integer);
      port (i   : in std_logic_vector(width-1 downto 0);
            ce  : in std_logic;
            clr : in std_logic;
            clk : in std_logic;
            o   : out std_logic_vector(width-1 downto 0));
   end component;
   signal dout_unreg : std_logic_vector (dout_width-1 downto 0);
   signal dout_reg   : std_logic_vector (dout_width-1 downto 0);
   signal block_ce   : std_logic;
begin
   block_ce <= ce and en(0);
   dout_unreg <= not(din);
   latency_gt_0 : if (latency > 0) generate
      reg_inst : synth_reg
         generic map (width   => dout_width,
                      latency => 1)
         port map (i   => dout_unreg,
                   ce  => block_ce,
                   clr => clr,
                   clk => clk,
                   o   => dout_reg);
   end generate;
   latency_eq_0 : if (latency = 0) generate
      dout_reg <= dout_unreg;
   end generate;
   extra_latency : if (latency > 1) generate
      latency_pipe : synth_reg
         generic map (width   => dout_width,
                      latency => latency-1)
         port map (i   => dout_reg,
                   ce  => block_ce,
                   clr => clr,
                   clk => clk,
                   o   => dout);
   end generate;
   no_extra_latency : if (latency = 0 or latency = 1) generate
      dout <= dout_reg;
   end generate;
end architecture dataflow;
