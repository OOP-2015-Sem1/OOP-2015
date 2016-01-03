
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
entity xlaxi_rst_gen is
  generic (
    has_aresetn: integer := -1
  );
  port (
    aclk: in std_logic;
    i_aresetn : in std_logic;
    ce : in std_logic;
    o_aresetn : out std_logic
    );
end xlaxi_rst_gen;
architecture behavior of xlaxi_rst_gen is
  signal reset_gen1: std_logic  := '0';
  signal reset_gen_d1: std_logic        := '0';
  signal reset_gen_d2: std_logic := '0';
begin
        o_aresetn <= reset_gen_d2 when (has_aresetn = 0)
                else not ( (not i_aresetn) and ce );
 process(aclk)
 begin
         if(aclk'event AND aclk = '1') then
                         reset_gen1 <= '1';
                         reset_gen_d1 <= reset_gen1;
                         reset_gen_d2 <= reset_gen_d1;
        end if;
 end process;
end behavior;
