
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
entity xllogical2 is
   generic (din0_width   : integer := 12;
            din0_bin_pt  : integer := 7;
            din0_arith   : integer := xlSigned;
            din1_width   : integer := 12;
            din1_bin_pt  : integer := 7;
            din1_arith   : integer := xlSigned;
            dout_width   : integer := 12;
            dout_bin_pt  : integer := 7;
            dout_arith   : integer := xlSigned;
            en_width     : integer := 1;
            en_bin_pt    : integer := 0;
            en_arith     : integer := xlUnsigned;
            full_width   : integer := 12;
            full_arith   : integer := xlUnsigned;
            full_bin_pt  : integer := 0;
            num_inputs   : integer := 2;
            latency      : integer := 2;
            gate_type    : integer := 0);
   port (din0       : in std_logic_vector (din0_width-1 downto 0);
         din1       : in std_logic_vector (din1_width-1 downto 0);
         dout       : out std_logic_vector (dout_width-1 downto 0);
         en         : in std_logic_vector(en_width-1 downto 0);
         ce         : in std_logic;
         clr        : in std_logic;
         clk        : in std_logic);
end xllogical2;
architecture behavior of xllogical2 is
   component synth_reg
      generic (width   : integer;
               latency : integer);
      port (i   : in std_logic_vector(width-1 downto 0);
            ce  : in std_logic;
            clr : in std_logic;
            clk : in std_logic;
            o   : out std_logic_vector(width-1 downto 0));
   end component;
   signal full_din0       : std_logic_vector (full_width-1 downto 0);
   signal full_din1       : std_logic_vector (full_width-1 downto 0);
   signal full_dout_unreg : std_logic_vector (full_width-1 downto 0);
   signal full_dout_reg   : std_logic_vector (full_width-1 downto 0);
   signal user_dout       : std_logic_vector (dout_width-1 downto 0);
   signal block_ce        : std_logic;
   constant quantization    : integer := xlTruncate;
   constant overflow        : integer := xlWrap;
begin
   block_ce  <= ce and en(0);
   full_din0 <= align_input(din0, din0_width, full_bin_pt - din0_bin_pt,
                            din0_arith, full_width);
   full_din1 <= align_input(din1, din1_width, full_bin_pt - din1_bin_pt,
                            din1_arith, full_width);
   AND_gate : if (gate_type = 0) generate
      full_dout_unreg <= full_din0 and full_din1;
   end generate;
   NAND_gate : if (gate_type = 1) generate
      full_dout_unreg <= not(full_din0 and full_din1);
   end generate;
   OR_gate : if (gate_type = 2) generate
      full_dout_unreg <= full_din0 or full_din1;
   end generate;
   NOR_gate : if (gate_type = 3) generate
      full_dout_unreg <= not(full_din0 or full_din1);
   end generate;
   XOR_gate : if (gate_type = 4) generate
      full_dout_unreg <= full_din0 xor full_din1;
   end generate;
   XNOR_gate : if (gate_type = 5) generate
      full_dout_unreg <= not(full_din0 xor full_din1);
   end generate;
   latency_gt_0 : if (latency > 0) generate
      reg_inst : synth_reg
         generic map (width   => full_width,
                      latency => 1)
         port map (i   => full_dout_unreg,
                   ce  => block_ce,
                   clr => clr,
                   clk => clk,
                   o   => full_dout_reg);
   end generate;
   latency_eq_0 : if (latency = 0) generate
      full_dout_reg <= full_dout_unreg;
   end generate;
   user_dout <= convert_type(full_dout_reg, full_width, full_bin_pt, full_arith,
                             dout_width, dout_bin_pt, dout_arith, quantization,
                             overflow);
   extra_latency : if (latency > 1) generate
      latency_pipe : synth_reg
         generic map (width   => dout_width,
                      latency => latency-1)
         port map (i   => user_dout,
                   ce  => block_ce,
                   clr => clr,
                   clk => clk,
                   o   => dout);
   end generate;
   no_extra_latency : if (latency = 0 or latency = 1) generate
      dout <= user_dout;
   end generate;
end architecture behavior;
