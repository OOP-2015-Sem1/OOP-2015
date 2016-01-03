
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
entity [% entity_name %] is
   generic (
[% num_inputs_minus_1 = num_inputs - 1 -%]
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
      din[% i %]_width   : integer := 3;
      din[% i %]_bin_pt  : integer := 1;
      din[% i %]_arith   : integer := xlUnsigned;
[% END -%]
      dout_width   : integer := 7;
      dout_bin_pt  : integer := 0;
      dout_arith   : integer := xlUnsigned);
   port (
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
      din[% i %]       : in std_logic_vector(din[% i %]_width-1 downto 0);
[% END -%]
      dout       : out std_logic_vector(dout_width-1 downto 0);
      ce         : in std_logic;
      clr        : in std_logic;
      clk        : in std_logic);
end [% entity_name %];
architecture structural of [% entity_name %] is
begin
[% port_def = "  dout <= din0" -%]
[% FOREACH i = [1 .. num_inputs_minus_1] -%]
[%   port_def = "$port_def & din$i" -%]
[% END -%]
[% port_def %];
end architecture structural;
