-------------------------------------------------------------------
-- System Generator version 6.2 VHDL source file.
-- Copyright (c) 2004, Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
-------------------------------------------------------------------

-- synopsys translate_off
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
use work.clock_pkg.all;
use std.textio.all ;
entity xltbsource is
    generic (o_width : integer := 4;
             o_bin_pt : integer := 2;
             o_arith : integer := xlSigned;
             latency : integer := 1;
             inputFile : string := "test.dat";
             sysClkPeriod : time := 100 ns;
             periodMultiplier : integer := 1;
             reqHoldTime : time := 1500 ps);
    port (o       : out std_logic_vector (o_width-1 downto 0));
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
   sys_clk <= work.clock_pkg.int_clk;
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


-------------------------------------------------------------------
-- System Generator version 6.2 VHDL source file.
-- Copyright (c) 2004, Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
-------------------------------------------------------------------

-- synopsys translate_off
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
use work.clock_pkg.all;
use std.textio.all ;
entity xltbsource1 is
  generic (
    latency: integer := 1;
    inputFile: string := "test.dat";
    sysClkPeriod: time := 100 ns;
    periodMultiplier: integer := 1;
    reqHoldTime: time := 1500 ps
  );
  port (
    o: out std_logic
  );
end xltbsource1;
architecture behavior of xltbsource1 is
  component xltbsource is
    generic (
      o_width: integer;
      o_bin_pt: integer;
      o_arith: integer;
      latency: integer;
      inputFile: string;
      sysClkPeriod: time;
      periodMultiplier: integer;
      reqHoldTime: time
    );
    port (
      o: out std_logic_vector(o_width - 1 downto 0)
    );
  end component;
begin
  tbsource: xltbsource
    generic map (
      o_width => 1,
      o_bin_pt => 0,
      o_arith => xlUnsigned,
      latency => latency,
      inputFile => inputFile,
      sysClkPeriod => sysClkPeriod,
      periodMultiplier => periodMultiplier,
      reqHoldTime => reqHoldTime
    )
    port map (
      o(0) => o
    );
end architecture behavior;
-- synopsys translate_on


-------------------------------------------------------------------
-- System Generator version 6.2 VHDL source file.
-- Copyright (c) 2004, Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
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
            sysClkPeriod : time := 100 ns;
            periodMultiplier : integer := 1);
   port (i : in std_logic_vector (i_width-1 downto 0));
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
      valid_count := valid_count + 1;
      if (not(data_valid)) then
         dntcare_count := dntcare_count + 1;
      end if;
      if ((i_minus_one /= simulink_i) and data_valid) then
         write(log_line, CR & "** Data mismatch on instance " & entityName &
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
   xltbsink_Process : process(work.clock_pkg.int_clk)
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
      if (rising_edge(work.clock_pkg.int_clk) or
          falling_edge(work.clock_pkg.int_clk)) then
         if (triggered = false) then
            if (falling_edge(work.clock_pkg.int_clk)) then
               positive_edge := false;
            end if;
            triggered := true;
         end if;
         if ((rising_edge(work.clock_pkg.int_clk) and (positive_edge = true))
             or (falling_edge(work.clock_pkg.int_clk) and
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
            write(output, CR & "** Simulation summary for instance " & entityName & ":" & CR);
            write(output,      "   Samples Processed: " & integer'image(sample_count) & CR);
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
               write(output, "   There were " & integer'image(error_count) & " mismatches in the test." & CR);
            else
               write(output, "   Test completed with no errors." & CR);
            end if;
            error_reported := true;
         end if;
         period_count := 0;
      end if;
   end process xltbsink_Process;
end architecture behavior;
-- synopsys translate_on


-------------------------------------------------------------------
-- System Generator version 6.2 VHDL source file.
-- Copyright (c) 2004, Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
-------------------------------------------------------------------

-- synopsys translate_off
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use work.conv_pkg.all;
use std.textio.all;
entity xltbsink1 is
  generic (
    latency: integer := 1;
    inputFile: string := "test.dat";
    outputFile: string := "";
    sysClkPeriod: time := 100 ns;
    periodMultiplier: integer := 1
  );
  port (
    i : in std_logic
  );
end xltbsink1;
architecture behavior of xltbsink1 is
  component xltbsink is
    generic (
      i_width: integer;
      i_bin_pt: integer;
      i_arith: integer;
      latency: integer;
      inputFile: string;
      outputFile: string;
      sysClkPeriod: time;
      periodMultiplier: integer
    );
    port (
      i: in std_logic_vector(i_width - 1 downto 0)
    );
  end component;
begin
  tbsink: xltbsink
    generic map (
      i_width => 1,
      i_bin_pt => 0,
      i_arith => xlUnsigned,
      latency => latency,
      inputFile => inputFile,
      outputFile => outputFile,
      sysClkPeriod => sysClkPeriod,
      periodMultiplier => periodMultiplier
    )
    port map (
      i(0) => i
    );
end architecture behavior;
-- synopsys translate_on


-------------------------------------------------------------------
-- System Generator version 6.2 VHDL source file.
-- Copyright (c) 2004, Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
-------------------------------------------------------------------

-- synopsys translate_off
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
entity xlclk is
  generic (clk_period  : time := 100 ns);
  port (clk   : out std_logic := '0';
        clr   : out std_logic := '0';
        ce    : out std_logic := '1');
end xlclk;
architecture behavior of xlclk is
    signal internal_clk : std_logic := '0';
begin
    clk_gen : process(internal_clk)
    begin
        internal_clk <= not(internal_clk) after clk_period/2;
        if clk_period > 400 ns then
            clk <= transport internal_clk after 200 ns;
        else
            clk <= transport internal_clk after (200 ns - (clk_period/2));
        end if;
    end process;
    clr <= '0';
    ce <= '1';
end behavior;
-- synopsys translate_on

-------------------------------------------------------------------
-- System Generator version 6.2 VHDL source file.
-- Copyright (c) 2004, Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
-------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
use work.clock_pkg.all;
entity xlclkprobe is
  port (clk             : in std_logic;
        clr             : in std_logic;
        ce              : in std_logic;
        fakeOutForXst   : out std_logic);
end xlclkprobe;
architecture behavior of xlclkprobe is
begin
   fakeOutForXst <= '0';
-- synopsys translate_off
   work.clock_pkg.int_clk <= clk;
-- synopsys translate_on
end behavior;

-- --------------------------------------------------------------
-- System Generator version 6.2vhdl source file.
-- Copyright (c) 2004 Xilinx, Inc.  All rights reserved.
-- Reproduction or reuse, in any form, without the explicit written
-- consent of Xilinx, Inc., is strictly prohibited.
-- --------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
use work.clock_pkg.all;

entity lowpass_filter_testbench is 
end lowpass_filter_testbench;

architecture structural of lowpass_filter_testbench is
  -- Declare the VHDL co-simulation component here:
  component foo_mti_hwcosim
    port (
      ce: in std_logic := '1';
      clk: in std_logic;
      sample_in: in std_logic_vector(7 downto 0);
      sample_out: out std_logic_vector(24 downto 0)
    );
  end component;
  component xlclk
    generic (
      clk_period: time := 100 ns
    );
    port (
      clk: out std_logic
    );
  end component;
  component xlclkprobe
    port (
      ce: in std_logic;
      clk: in std_logic;
      clr: in std_logic;
      fakeoutforxst: out std_logic
  );
  end component;
  component xltbsink
    generic (
-- synopsys translate_off
      inputFile: string := "filter_output.dat";
-- synopsys translate_on
      i_arith: integer := xlSigned;
      i_bin_pt: integer := 19;
      i_width: integer := 25;
      periodMultiplier: integer := 32;
      sysClkPeriod: time := 100 ns
    );
    port (
      i: in std_logic_vector(i_width - 1 downto 0)
    );
  end component;
  component xltbsource
    generic (
-- synopsys translate_off
      inputFile: string := "filter_input.dat";
-- synopsys translate_on
      o_arith: integer := xlSigned;
      o_bin_pt: integer := 6;
      o_width: integer := 8;
      periodMultiplier: integer := 32;
      sysClkPeriod: time := 100 ns
    );
    port (
      o: out std_logic_vector(o_width - 1 downto 0)
    );
  end component;

  signal clk_sysgen: std_logic;
  signal sample_in_x_0: std_logic_vector(7 downto 0);
  signal sample_out_x_0: std_logic_vector(24 downto 0);

begin

  -- Instantiate the VHDL co-simulation component here:
  filter_inst: foo_mti_hwcosim
    port map (
      clk => clk_sysgen,
      sample_in => sample_in_x_0,
      sample_out => sample_out_x_0
    );
  sample_in: xltbsource
    generic map (
-- synopsys translate_off
      inputFile => "filter_input.dat",
-- synopsys translate_on
      o_arith => xlSigned,
      o_bin_pt => 6,
      o_width => 8,
      periodMultiplier => 32,
      sysClkPeriod => 100 ns
    )
    port map (
      o => sample_in_x_0
    );
  sample_out: xltbsink
    generic map (
-- synopsys translate_off
      inputFile => "filter_output.dat",
-- synopsys translate_on
      i_arith => xlSigned,
      i_bin_pt => 19,
      i_width => 25,
      periodMultiplier => 32,
      sysClkPeriod => 100 ns
    )
    port map (
      i => sample_out_x_0
    );
  xlclk_x_0: xlclk
    generic map (
      clk_period => 100 ns
    )
    port map (
      clk => clk_sysgen
    );
  xlclkprobe_x_0: xlclkprobe
    port map (
      ce => '1',
      clk => clk_sysgen,
      clr => '0'
    );
  
end structural;

