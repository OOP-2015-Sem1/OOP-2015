
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
use work.conv_pkg.all;
use std.textio.all ;
entity xltbsource is
    generic (o_width : integer := 4;
             o_bin_pt : integer := 2;
             o_arith : integer := xlSigned;
             latency : integer := 1;
             inputFile : string := "test.dat";
             periodMultiplier : integer := 1;
             reqHoldTime : time := 1500 ps);
    port (o       : out std_logic_vector (o_width-1 downto 0);
          clk     : in std_logic);
end xltbsource;
architecture behavior of xltbsource is
    file  inputFilePtr : text open read_mode is inputFile;
    procedure read_data_set(file inputFilePtr : text;
                            constant width : in integer;
                            signal data : out std_logic_vector) is
        variable new_line:line;
        variable data_str : string(1 to width) := (others => ' ');
    begin
        if not endfile(inputFilePtr) then
            readline(inputFilePtr, new_line);
            read(new_line, data_str);
        end if;
        data <= bin_string_to_std_logic_vector(data_str);
    end procedure read_data_set;
    signal internal_o : std_logic_vector(o_width-1 downto 0);
    signal real_o : real;
    signal sys_clk : std_logic;
begin
   sys_clk <= clk;
   o <= internal_o after reqHoldTime;
   real_o <= to_real(internal_o, o_bin_pt, o_arith);
   xltbsource_Process : process(sys_clk)
      variable period_count  : integer := 0;
      variable positive_edge : boolean := true;
      variable triggered     : boolean := false;
      variable startup       : boolean := true;
   begin
      if (startup = true) then
         read_data_set(inputFilePtr, o_width, internal_o);
         startup := false;
      end if;
      if (rising_edge(sys_clk) or falling_edge(sys_clk)) then
        if (triggered = false) then
           if (falling_edge(sys_clk)) then
             positive_edge := false;
           end if;
           triggered := true;
        end if;
        if ((rising_edge(sys_clk) and (positive_edge = true)) or
            (falling_edge(sys_clk) and (positive_edge = false))) then
          period_count := period_count + 1;
        end if;
      end if;
      if (period_count = periodMultiplier) then
         read_data_set(inputFilePtr, o_width, internal_o);
         period_count := 0;
      end if;
   end process xltbsource_Process;
end architecture behavior;
-- synopsys translate_on
