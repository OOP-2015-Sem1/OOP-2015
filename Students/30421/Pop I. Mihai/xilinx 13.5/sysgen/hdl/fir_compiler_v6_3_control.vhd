
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
  entity fir_compiler_v6_3_control is

   port (
[% T.PORTS %]
        );
end fir_compiler_v6_3_control;
architecture behavioral of fir_compiler_v6_3_control is
  signal reload_packet_fsel: integer;
  signal data_send_amount: integer;
  signal core_ce : std_logic;
  [% T.SIGNALS %]

begin

  s_axis_reload_packet_fsel <= integer_to_std_logic_vector(reload_packet_fsel, 32, xlUnsigned);
  s_axis_data_send_amount <= integer_to_std_logic_vector(data_send_amount, 32, xlUnsigned);
  core_ce <= aclken and en;
  [% T.SYNC_LOGIC %]
  fir_sim : entity work.fir_compiler_v6_3_timing_model
  generic map (
      [% T.GENERICS_MAP %]
  )
  port map (
      [% T.PORT_MAP %]
  );
end;
