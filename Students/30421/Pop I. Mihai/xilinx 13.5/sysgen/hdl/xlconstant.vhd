
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
entity xlconstant is
   generic (const_val   : bit_vector := b"0000";
            cin_width   : integer    := 1;
            cin_bin_pt  : integer    := 0;
            cin_arith   : integer    := xlUnsigned;
            dout_width  : integer    := 5;
            dout_bin_pt : integer    := 2;
            use_cin     : integer    := 0;
            dout_arith  : integer    := xlUnsigned);
   port (dout : out std_logic_vector (dout_width-1 downto 0);
         ce   : in std_logic := '1';
         clr  : in std_logic := '0';
         clk  : in std_logic := '1';
         cin  : in std_logic_vector(cin_width - 1 downto 0) := (others => '0'));
end xlconstant;
architecture behavior of xlconstant is
   signal temp_dout  : std_logic_vector(dout_width -1 downto 0);
   signal temp_dout2 : std_logic_vector(dout_width -1 downto 0);
   signal temp_cin   : std_logic;
begin
   use_cin_port: if (use_cin = 1) generate
        temp_cin <= cin(0);
        temp_dout <= to_stdlogicvector(const_val);
        temp_dout2(6 downto 0) <= temp_dout(6 downto 0);
        temp_dout2(7) <= temp_cin;
        temp_dout2(dout_width -1 downto 8) <= temp_dout(dout_width-1 downto 8);
        dout <= temp_dout2;
   end generate;
   no_cin_port: if (use_cin = 0) generate
        dout <= to_stdlogicvector(const_val);
   end generate;
end architecture behavior;
