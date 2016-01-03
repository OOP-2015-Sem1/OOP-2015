
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
-- synopsys translate_off
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use work.conv_pkg.all;
use std.textio.all ;
entity clock_driver is
  generic (clk_period  : time := 100 ns;
           latency : integer := 1);
    port (clk   : out std_logic := '0';
          clr   : out std_logic := '1';
          ce    : out std_logic := '1';
          clk2  : out std_logic := '0';
          clr2  : out std_logic := '1';
          ce2   : out std_logic := '1';
          clk4  : out std_logic := '0';
          clr4  : out std_logic := '1';
          ce4   : out std_logic := '1';
          clk8  : out std_logic := '0';
          clr8  : out std_logic := '1';
          ce8   : out std_logic := '1';
          clk16 : out std_logic := '0';
          clr16 : out std_logic := '1';
          ce16  : out std_logic := '1';
          clk32 : out std_logic := '0';
          clr32 : out std_logic := '1';
          ce32  : out std_logic := '1';
          clk64 : out std_logic := '0';
          clr64 : out std_logic := '1';
          ce64  : out std_logic := '1'
          );
end clock_driver;
architecture behavior of clock_driver is
    signal internal_clk1 : std_logic := '0';
    signal internal_clk2 : std_logic := '0';
    signal internal_clk4 : std_logic := '0';
    signal internal_clk8 : std_logic := '0';
    signal internal_clk16 : std_logic := '0';
    signal internal_clk32 : std_logic := '0';
    signal internal_clk64 : std_logic := '0';
begin
    clk <= internal_clk1;
    clk_gen : process(internal_clk1)
    begin
        internal_clk1 <= not(internal_clk1) after clk_period/2;
    end process;
    clr_gen : process(internal_clk1)
    begin
        if internal_clk1'event and internal_clk1 = '1'  then
            clr <= '0' after clk_period/2;
        end if;
    end process;
    ce <= '1';
    clk2 <= internal_clk2;
    clk2_gen : process(internal_clk1)
    begin
        if (rising_edge(internal_clk1)) then
            internal_clk2 <= not(internal_clk2);
        end if;
    end process;
    clr2_gen : process(internal_clk2)
    begin
      if internal_clk2'event and internal_clk2 = '1'  then
          clr2 <= '0' after clk_period;
      end if;
    end process;
    ce2 <= '1';
    clk4 <= internal_clk4;
    clk4_gen : process(internal_clk2)
    begin
        if (rising_edge(internal_clk2)) then
            internal_clk4 <= not(internal_clk4);
        end if;
    end process;
    one_shot_gen4 : process(internal_clk4)
    begin
      if internal_clk4'event and internal_clk4 = '1'  then
          clr4 <= '0' after 2*clk_period;
      end if;
    end process;
    ce4 <= '1';
    clk8 <= internal_clk8;
    clk8_gen : process(internal_clk4)
    begin
        if (rising_edge(internal_clk4)) then
            internal_clk8 <= not(internal_clk8);
        end if;
    end process;
    one_shot_gen8 : process(internal_clk8)
    begin
      if internal_clk8'event and internal_clk8 = '1'  then
          clr8 <= '0' after 4*clk_period;
      end if;
    end process;
    ce8 <= '1';
    clk16 <= internal_clk16;
    clk16_gen : process(internal_clk8)
    begin
        if (rising_edge(internal_clk8)) then
            internal_clk16 <= not(internal_clk16);
        end if;
    end process;
    one_shot_gen16 : process(internal_clk16)
    begin
      if internal_clk16'event and internal_clk16 = '1'  then
          clr16 <= '0' after 8*clk_period;
      end if;
    end process;
    ce16 <= '1';
    clk32 <= internal_clk32;
    clk32_gen : process(internal_clk16)
    begin
        if (rising_edge(internal_clk16)) then
            internal_clk32 <= not(internal_clk32);
        end if;
    end process;
    one_shot_gen32 : process(internal_clk32)
    begin
      if internal_clk32'event and internal_clk32 = '1'  then
          clr32 <= '0' after 16*clk_period;
      end if;
    end process;
    ce32 <= '1';
    clk64 <= internal_clk64;
    clk64_gen : process(internal_clk32)
    begin
        if (rising_edge(internal_clk32)) then
            internal_clk64 <= not(internal_clk64);
        end if;
    end process;
    one_shot_gen64 : process(internal_clk64)
    begin
      if internal_clk64'event and internal_clk64 = '1'  then
          clr64 <= '0' after 32*clk_period;
      end if;
    end process;
    ce64 <= '1';
end architecture behavior;
-- synopsys translate_on
