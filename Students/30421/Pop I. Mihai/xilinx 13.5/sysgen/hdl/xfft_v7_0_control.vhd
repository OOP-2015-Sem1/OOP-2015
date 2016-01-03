
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
entity xfft_v7_0_control is
    port (
[% T.PORTS %]
        );
end xfft_v7_0_control;
architecture behavior of xfft_v7_0_control is
  signal core_ce : std_logic;
begin
  core_ce <= ce and en;
  fft_sim : entity work.timing_model
    generic map(
          [% T.GENERICS_MAP %]
    )
    port map(
      CE             => CORE_CE,
      CLK            => CLK,
      START          => START,
      UNLOAD         => UNLOAD,
      NFFT           => NFFT,
      NFFT_WE        => NFFT_WE,
      SCLR           => RST,
      CP_LEN         => CP_LEN,
      CP_LEN_WE      => CP_LEN_WE,
      FWD_INV        => FWD_INV,
      FWD_INV_WE     => FWD_INV_WE,
      SCALE_SCH      => SCALE_SCH,
      SCALE_SCH_WE   => SCALE_SCH_WE,
      XN_INDEX       => XN_INDEX,
      XK_INDEX       => XK_INDEX,
      RFD            => RFD,
      BUSY           => BUSY,
      DV             => VOUT,
      EDONE          => EDONE,
      DONE           => DONE,
      CPV            => CPV,
      RFS            => RFS,
      FWD_INV_USED   => FWD_INV_USED,
      SCALE_SCH_USED => SCALE_SCH_USED
    );
end  behavior;
