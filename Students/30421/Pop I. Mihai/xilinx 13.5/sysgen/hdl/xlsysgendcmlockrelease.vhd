
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
library unisim;
use unisim.vcomponents.all;

entity xlsysgendcmlockrelease is
  generic (
    extra_delay : integer := 0
  );
  port (
    clk : in std_logic;
    reset : in std_logic;
    locked : in std_logic;
    releasebufgce : out std_logic
  );
end xlsysgendcmlockrelease;
architecture structural of xlsysgendcmlockrelease is
  component FDRE
    port (
      C: in std_logic;
      CE: in std_logic;
      D: in std_logic;
      R: in std_logic;
      Q: out std_logic
    );
  end component;
  signal reset_net : std_logic;
  signal reset_neg_edge_pulse_net : std_logic;
  signal reset_neg_edge_capture_net : std_logic;

  signal locked_net : std_logic;
  signal locked_bar_net : std_logic;
  signal locked_pos_edge_pulse_net : std_logic;
  signal locked_pos_edge_capture_net : std_logic;

  signal release_net : std_logic;
  signal release_net_temp : std_logic;

  signal simulation_mux : std_logic := '0';
begin
  reset_net <= reset;
  locked_net <= locked;
  locked_bar_net <= not locked;

-- synopsys translate_off
  simulation_mux <= '1';
-- synopsys translate_on
  process (simulation_mux, locked_net, release_net)
  begin
    if simulation_mux = '1' then
      releasebufgce <= locked_net;
    else
      releasebufgce <= release_net;
    end if;
  end process;
  falling_edge_detect_on_reset :  entity work.xlnegedgedet
  port map(
    a => reset_net,
    clk => clk,
    dout => reset_neg_edge_pulse_net
  );
  capture_falling_edge_on_reset: FDRE
  port map(
    C => clk,
    CE => reset_neg_edge_pulse_net,
    D => '1',
    R => reset_net,
    Q => reset_neg_edge_capture_net
  );

  rising_edge_detect_on_locked : entity work.xlnegedgedet
  port map(
    a => locked_bar_net,
    clk => clk,
    dout => locked_pos_edge_pulse_net
  );
  capture_rising_edge_on_locked: FDRE
  port map(
    C => clk,
    CE => locked_pos_edge_capture_net,
    D => '1',
    R => reset_net,
    Q => release_net_temp
  );
  locked_pos_edge_capture_net <= reset_neg_edge_capture_net and locked_pos_edge_pulse_net;

  virtex4_mode : if (extra_delay = 0) generate
    release_net <= release_net_temp;
  end generate virtex4_mode;

  virtex5_spartan3adsp_mode : if (extra_delay /= 0) generate
     delay_v5_s3adsp: FDRE
     port map(
       C => clk,
      CE => '1',
       D => release_net_temp,
       R => reset_net,
       Q => release_net
     );
  end generate virtex5_spartan3adsp_mode;


end structural;
