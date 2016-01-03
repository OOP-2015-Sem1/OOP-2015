
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
use std.textio.all;
entity xltbsink is
   generic (i_width    : integer := 4;
            i_bin_pt   : integer := 2;
            i_arith    : integer := xlSigned;
            latency    : integer := 1;
            inputFile  : string  := "test.dat";
            outputFile : string  := "";
            periodMultiplier : integer := 1);
   port (i : in std_logic_vector (i_width-1 downto 0);
         clk : in std_logic);
end xltbsink;
architecture behavior of xltbsink is
   constant entityName : string := inputFile(1 to (inputFile'length-4));
   file inputFilePtr   : text open read_mode is inputFile;
   procedure read_data_set(file inputFilePtr   : text;
                           constant width      : in integer;
                           signal valid_bit    : out std_logic;
                           signal data         : out std_logic_vector;
                           variable data_valid : out boolean)
   is
      constant inp_str_width : integer := width;
      variable inp_str       : string(1 to inp_str_width) := (others => ' ');
      variable data_str      : string(1 to width);
      variable new_line      : line;
      variable data_var      : std_logic_vector(width-1 downto 0);
      variable valid_bit_var : std_logic;
   begin
      if not endfile(inputFilePtr) then
         readline(inputFilePtr, new_line);
         read(new_line, inp_str);
      end if;
      valid_bit <= '1';
      data_str := inp_str(1 to inp_str_width);
      data_valid := not(is_binary_string_invalid(data_str) or
                        is_binary_string_undefined(data_str));
      data_var := bin_string_to_std_logic_vector(data_str);
      data <= data_var;
   end procedure read_data_set;
   procedure check_data(signal simulink_i              : in std_logic_vector;
                        signal i_minus_one             : in std_logic_vector;
                        signal real_simulink_i, real_i : in real;
                        constant data_valid            : in boolean;
                        variable found_error           : inout boolean;
                        variable sample_count          : inout integer;
                        variable valid_count           : inout integer;
                        variable dntcare_count         : inout integer;
                        variable error_count           : inout integer)
   is
      constant carriage_return : character := cr;
      variable log_line        : line;
   begin
      sample_count := sample_count + 1;
      if (sample_count = 1) then
         write(log_line, CR);
         writeline(output, log_line);
         write(log_line, "Beginning comparisons for instance " &
                              entityName);
         writeline(output, log_line);
      end if;
      valid_count := valid_count + 1;
      if (not(data_valid)) then
         dntcare_count := dntcare_count + 1;
      end if;
      if ((i_minus_one /= simulink_i) and data_valid) then
         write(log_line, CR);
         writeline(output, log_line);
         write(log_line, "** Data mismatch on instance " & entityName &
                              " at time ");
         write(log_line, now, justified => left, unit => ns);
         write(log_line, string'("."));
         writeline(output, log_line);
         write(log_line, "   Simulink result: " &
                         std_logic_vector_to_bin_string_w_point(simulink_i, i_bin_pt) &
                         " (" & real'image(real_simulink_i) & ")");
         writeline(output, log_line);
         write(log_line, "   VHDL result:     " &
                         std_logic_vector_to_bin_string_w_point(i, i_bin_pt) &
                         " (" & real'image(real_i) & ")");
         writeline(output, log_line);
         found_error := true;
         error_count := error_count + 1;
      end if;
   end procedure check_data;
   signal real_i, real_simulink_i : real;
   signal simulink_i              : std_logic_vector(i_width-1 downto 0);
   signal simulink_i_valid        : std_logic;
   signal error                   : std_logic := '0';
   signal i_minus_one             : std_logic_vector(i_width-1 downto 0);
begin
   real_i <= to_real(i, i_bin_pt, i_arith);
   real_simulink_i <= to_real(simulink_i, i_bin_pt, i_arith);
   i_minus_one <= i;
   xltbsink_Process : process(clk)
      variable found_error    : boolean := false;
      variable error_count    : integer := 0;
      variable sample_count   : integer := 0;
      variable valid_count    : integer := 0;
      variable dntcare_count  : integer := 0;
      variable error_reported : boolean := false;
      variable data_valid     : boolean := false;
      variable log_line       : line;
      variable triggered      : boolean := false;
      variable positive_edge  : boolean := true;
      variable period_count   : integer := 0;
      variable startup        : boolean := true;
   begin
      if (startup = true) then
         read_data_set(inputFilePtr, i_width, simulink_i_valid, simulink_i, data_valid);
         startup := false;
      end if;
      if (rising_edge(clk) or
          falling_edge(clk)) then
         if (triggered = false) then
            if (falling_edge(clk)) then
               positive_edge := false;
            end if;
            triggered := true;
         end if;
         if ((rising_edge(clk) and (positive_edge = true))
             or (falling_edge(clk) and
             (positive_edge = false))) then
            period_count := period_count + 1;
         end if;
      end if;
      if (period_count = periodMultiplier) then
         if not endfile(inputFilePtr) then
            check_data(simulink_i, i_minus_one, real_simulink_i, real_i,
                       data_valid, found_error, sample_count, valid_count,
                       dntcare_count, error_count);
            if (found_error) then
               error <= '1';
            else
               error <= '0';
            end if;
            read_data_set(inputFilePtr, i_width, simulink_i_valid, simulink_i, data_valid);
         elsif error_reported = false then
            check_data(simulink_i, i_minus_one, real_simulink_i, real_i,
                       data_valid, found_error, sample_count, valid_count,
                       dntcare_count, error_count);
            write(log_line, CR);
            writeline(output,log_line);
            write(log_line, "** Simulation summary for instance " & entityName & ":");
            writeline(output,log_line);
            write(log_line,      "   Samples Processed: " & integer'image(sample_count));
            writeline(output,log_line);
            write(log_line,    "           - Checked: " & integer'image(valid_count-dntcare_count) & " (");
            write(log_line, real(valid_count-dntcare_count)/real(sample_count)*100.0,
                                           justified=>left,digits=>1);
            write(log_line, string'("%)"));
            writeline(output,log_line);
            write(log_line,    "           - Ignored: " & integer'image(dntcare_count) & " (");
            write(log_line, real(dntcare_count)/real(sample_count)*100.0,
                                 justified=>left,digits=>1);
            write(log_line, string'("%) Don't Cares"));
            writeline(output,log_line);
            if ((sample_count-valid_count) /= 0) then
               write(log_line,    "                      " & integer'image(sample_count-valid_count) & " (");
               write(log_line, real(sample_count-valid_count)/real(sample_count)*100.0,
                                    justified=>left,digits=>1);
               write(log_line, string'("%) Invalid"));
               writeline(output,log_line);
            end if;
            if (found_error) then
               write(output, "   There were " & integer'image(error_count) & " mismatches in the test.");
            else
               write(output, "   Test completed with no errors.");
            end if;
            error_reported := true;
         end if;
         period_count := 0;
      end if;
   end process xltbsink_Process;
end architecture behavior;
-- synopsys translate_on
