
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
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use work.conv_pkg.all;
entity compute_relational_signed is
  generic (full_width        : integer := 25;
           operator_type  : integer := 1);
  port (a_in       : in std_logic_vector (full_width-1 downto 0);
        b_in       : in std_logic_vector (full_width-1 downto 0);
        dout    : out std_logic_vector (0 downto 0));
end compute_relational_signed;
architecture behavior of compute_relational_signed is
  signal a, b : signed (full_width-1 downto 0);
  signal result : std_logic;
begin
  a <= std_logic_vector_to_signed(a_in);
  b <= std_logic_vector_to_signed(b_in);
    equals : if (operator_type = 0) generate
     result <= '1' when a = b else '0';
  end generate;
  not_equals : if (operator_type = 1) generate
     result <= '1' when a /= b else '0';
  end generate;
  less_than : if (operator_type = 2) generate
     result <= '1' when a < b else '0';
  end generate;
  greater_than : if (operator_type = 3) generate
     result <= '1' when a > b else '0';
  end generate;
  less_than_or_equal_to : if (operator_type = 4) generate
     result <= '1' when a <= b else '0';
  end generate;
  greater_than_or_equal_to : if (operator_type = 5) generate
     result <= '1' when a >= b else '0';
  end generate;

  dout(0) <= result;
end architecture behavior;
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use work.conv_pkg.all;
entity compute_relational_unsigned is
  generic (full_width        : integer := 25;
           operator_type  : integer := 1);
  port (a_in       : in std_logic_vector (full_width-1 downto 0);
        b_in       : in std_logic_vector (full_width-1 downto 0);
        dout    : out std_logic_vector (0 downto 0));
end compute_relational_unsigned;
architecture behavior of compute_relational_unsigned is
  signal a, b : unsigned (full_width-1 downto 0);
  signal result : std_logic;
begin
  a <= std_logic_vector_to_unsigned(a_in);
  b <= std_logic_vector_to_unsigned(b_in);
  equals : if (operator_type = 0) generate
     result <= '1' when a = b else '0';
  end generate;
  not_equals : if (operator_type = 1) generate
     result <= '1' when a /= b else '0';
  end generate;
  less_than : if (operator_type = 2) generate
     result <= '1' when a < b else '0';
  end generate;
  greater_than : if (operator_type = 3) generate
     result <= '1' when a > b else '0';
  end generate;
  less_than_or_equal_to : if (operator_type = 4) generate
     result <= '1' when a <= b else '0';
  end generate;
  greater_than_or_equal_to : if (operator_type = 5) generate
     result <= '1' when a >= b else '0';
  end generate;

  dout(0) <= result;
end architecture behavior;
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use work.conv_pkg.all;
entity xlrelational is
   generic (a_width        : integer := 25;
            a_bin_pt       : integer := 19;
            a_arith        : integer := xlSigned;
            b_width        : integer := 2;
            b_bin_pt       : integer := 0;
            b_arith        : integer := xlSigned;
            dout_width     : integer := 1;
            dout_bin_pt    : integer := 0;
            dout_arith     : integer := xlUnsigned;
            en_width       : integer := 5;
            en_bin_pt      : integer := 2;
            en_arith       : integer := xlUnsigned;
            full_width     : integer := 12;
            full_arith     : integer := xlUnsigned;
            full_bin_pt    : integer := 0;
            latency        : integer := 1;
            operator_type  : integer := 1);
   port (a       : in std_logic_vector (a_width-1 downto 0);
         b       : in std_logic_vector (b_width-1 downto 0);
         dout    : out std_logic_vector (dout_width-1 downto 0);
         ce      : in std_logic;
         clr     : in std_logic;
         clk     : in std_logic;
         en      : in std_logic_vector (en_width-1 downto 0));
end xlrelational;
architecture behavior of xlrelational is
   component compute_relational_signed
      generic (full_width   : integer;
               operator_type : integer);
      port (a_in   : in std_logic_vector(full_width-1 downto 0);
            b_in   : in std_logic_vector(full_width-1 downto 0);
            dout   : out std_logic_vector(0 downto 0));
   end component;
   component compute_relational_unsigned
      generic (full_width   : integer;
               operator_type : integer);
      port (a_in   : in std_logic_vector(full_width-1 downto 0);
            b_in   : in std_logic_vector(full_width-1 downto 0);
            dout   : out std_logic_vector(0 downto 0));
   end component;
   component synth_reg
      generic (width   : integer;
               latency : integer);
      port (i   : in std_logic_vector(width-1 downto 0);
            ce  : in std_logic;
            clr : in std_logic;
            clk : in std_logic;
            o   : out std_logic_vector(width-1 downto 0));
   end component;
   signal full_a     : std_logic_vector (full_width-1 downto 0);
   signal full_b     : std_logic_vector (full_width-1 downto 0);
   signal dout_unreg : std_logic_vector (dout_width-1 downto 0);
   signal dout_reg   : std_logic_vector (dout_width-1 downto 0);
   signal block_ce   : std_logic;
begin
   block_ce  <= ce and en(0);
   full_a <= align_input(a, a_width, full_bin_pt - a_bin_pt, a_arith, full_width);
   full_b <= align_input(b, b_width, full_bin_pt - b_bin_pt, b_arith, full_width);
   signed_operator : if (full_arith = xlSigned) generate
     signed_inst : compute_relational_signed
       generic map (full_width => full_width,
                    operator_type => operator_type)
       port map (a_in => full_a,
                 b_in => full_b,
                 dout => dout_unreg);
   end generate;
   unsigned_operator : if (full_arith = xlUnsigned) generate
     unsigned_inst : compute_relational_unsigned
       generic map (full_width => full_width,
                    operator_type => operator_type)
       port map (a_in => full_a,
                 b_in => full_b,
                 dout => dout_unreg);
   end generate;

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
end architecture behavior;
