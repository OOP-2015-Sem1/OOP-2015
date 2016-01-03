
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
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity mux2_LUT is
    Port (     D1 : in std_logic;
               D0 : in std_logic;
              sel : in std_logic;
                Y : out std_logic);
    end mux2_LUT;
architecture low_level_definition of mux2_LUT is
attribute INIT : string;
attribute INIT of the_mux_lut : label is "E4";
begin
  the_mux_lut: LUT3
  -- synopsys translate_off
    generic map (INIT => X"E4")
  -- synopsys translate_on
  port map( I0 => sel,
            I1 => D0,
            I2 => D1,
             O => Y );
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity mux4_LUTS_MUXF5 is
    Port (  D3 : in std_logic;
            D2 : in std_logic;
            D1 : in std_logic;
            D0 : in std_logic;
          sel1 : in std_logic;
          sel0 : in std_logic;
             Y : out std_logic);
    end mux4_LUTS_MUXF5;
architecture low_level_definition of mux4_LUTS_MUXF5 is
component mux2_LUT
    Port (  D1 : in std_logic;
            D0 : in std_logic;
           sel : in std_logic;
             Y : out std_logic);
    end component;
signal upper_selection : std_logic;
signal lower_selection : std_logic;
begin
  upper_mux: mux2_LUT
  port map(  D1 => D3,
             D0 => D2,
            sel => sel0,
              Y => upper_selection );
  lower_mux: mux2_LUT
  port map(  D1 => D1,
             D0 => D0,
            sel => sel0,
              Y => lower_selection );
  final_mux: MUXF5
  port map(  I1 => upper_selection,
             I0 => lower_selection,
              S => sel1,
              O => Y );
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity data_bus_mux2 is
    Port (  D1_bus : in std_logic_vector(7 downto 0);
            D0_bus : in std_logic_vector(7 downto 0);
               sel : in std_logic;
             Y_bus : out std_logic_vector(7 downto 0));
    end data_bus_mux2;
architecture macro_level_definition of data_bus_mux2 is
component mux2_LUT
    Port (  D1 : in std_logic;
            D0 : in std_logic;
           sel : in std_logic;
             Y : out std_logic);
    end component;
begin
  bus_width_loop: for i in 0 to 7 generate
  begin
     bit_mux2: mux2_LUT
     port map(  D1 => D1_bus(i),
                D0 => D0_bus(i),
               sel => sel,
                 Y => Y_bus(i));
  end generate bus_width_loop;
end macro_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity data_bus_mux4 is
    Port (  D3_bus : in std_logic_vector(7 downto 0);
            D2_bus : in std_logic_vector(7 downto 0);
            D1_bus : in std_logic_vector(7 downto 0);
            D0_bus : in std_logic_vector(7 downto 0);
              sel1 : in std_logic;
              sel0 : in std_logic;
             Y_bus : out std_logic_vector(7 downto 0));
    end data_bus_mux4;
architecture macro_level_definition of data_bus_mux4 is
component mux4_LUTS_MUXF5
    Port (  D3 : in std_logic;
            D2 : in std_logic;
            D1 : in std_logic;
            D0 : in std_logic;
          sel1 : in std_logic;
          sel0 : in std_logic;
             Y : out std_logic);
    end component;
begin
  bus_width_loop: for i in 0 to 7 generate
  begin
     bit_mux4: mux4_LUTS_MUXF5
     port map(  D3 => D3_bus(i),
                D2 => D2_bus(i),
                D1 => D1_bus(i),
                D0 => D0_bus(i),
              sel1 => sel1,
              sel0 => sel0,
                 Y => Y_bus(i));
  end generate bus_width_loop;
end macro_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity data_register_bank is
    Port (    Address_A : in std_logic_vector(4 downto 0);
              Din_A_bus : in std_logic_vector(7 downto 0);
            Write_A_bus : in std_logic;
             Dout_A_bus : out std_logic_vector(7 downto 0);
              Address_B : in std_logic_vector(4 downto 0);
             Dout_B_bus : out std_logic_vector(7 downto 0);
                    clk : in std_logic);
    end data_register_bank;
architecture low_level_definition of data_register_bank is
begin
  bus_width_loop: for i in 0 to 7 generate
  attribute INIT : string;
  attribute INIT of data_register_bit : label is "00000000";
  begin
     data_register_bit: RAM32X1D
     -- synopsys translate_off
     generic map(INIT => X"00000000")
     -- synopsys translate_on
     port map (       D => Din_A_bus(i),
                     WE => Write_A_bus,
                   WCLK => clk,
                     A0 => Address_A(0),
                     A1 => Address_A(1),
                     A2 => Address_A(2),
                     A3 => Address_A(3),
                     A4 => Address_A(4),
                  DPRA0 => Address_B(0),
                  DPRA1 => Address_B(1),
                  DPRA2 => Address_B(2),
                  DPRA3 => Address_B(3),
                  DPRA4 => Address_B(4),
                    SPO => Dout_A_bus(i),
                    DPO => Dout_B_bus(i));
  end generate bus_width_loop;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity logical_bus_processing is
    Port (  first_operand : in std_logic_vector(7 downto 0);
           second_operand : in std_logic_vector(7 downto 0);
                    code1 : in std_logic;
                    code0 : in std_logic;
                        Y : out std_logic_vector(7 downto 0);
                      clk : in std_logic);
    end logical_bus_processing;
architecture low_level_definition of logical_bus_processing is
signal combinatorial_logical_processing : std_logic_vector(7 downto 0);
begin
  bus_width_loop: for i in 0 to 7 generate
  attribute INIT : string;
  attribute INIT of logical_lut : label is "6E8A";
  begin
     logical_lut: LUT4
     -- synopsys translate_off
     generic map (INIT => X"6E8A")
     -- synopsys translate_on
     port map( I0 => second_operand(i),
               I1 => first_operand(i),
               I2 => code0,
               I3 => code1,
                O => combinatorial_logical_processing(i));
     pipeline_bit: FD
     port map ( D => combinatorial_logical_processing(i),
                Q => Y(i),
                C => clk);
  end generate bus_width_loop;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity addsub8 is
    Port (  first_operand : in std_logic_vector(7 downto 0);
           second_operand : in std_logic_vector(7 downto 0);
                 carry_in : in std_logic;
                 subtract : in std_logic;
                        Y : out std_logic_vector(7 downto 0);
                carry_out : out std_logic;
                      clk : in std_logic);
    end addsub8;
architecture low_level_definition of addsub8 is
signal half_addsub : std_logic_vector(7 downto 0);
signal full_addsub : std_logic_vector(7 downto 0);
signal carry_chain : std_logic_vector(6 downto 0);
begin
  bus_width_loop: for i in 0 to 7 generate
  attribute INIT : string;
  attribute INIT of arithmetic_lut : label is "96";
  begin
     lsb_carry: if i=0 generate
          begin
       arithmetic_carry: MUXCY
       port map( DI => first_operand(i),
                 CI => carry_in,
                  S => half_addsub(i),
                  O => carry_chain(i));
       arithmetic_xor: XORCY
       port map( LI => half_addsub(i),
                 CI => carry_in,
                  O => full_addsub(i));
          end generate lsb_carry;
     mid_carry: if i>0 and i<7 generate
          begin
       arithmetic_carry: MUXCY
       port map( DI => first_operand(i),
                 CI => carry_chain(i-1),
                  S => half_addsub(i),
                  O => carry_chain(i));
       arithmetic_xor: XORCY
       port map( LI => half_addsub(i),
                 CI => carry_chain(i-1),
                  O => full_addsub(i));
          end generate mid_carry;
     msb_carry: if i=7 generate
          begin
       arithmetic_carry: MUXCY
       port map( DI => first_operand(i),
                 CI => carry_chain(i-1),
                  S => half_addsub(i),
                  O => carry_out);
       arithmetic_xor: XORCY
       port map( LI => half_addsub(i),
                 CI => carry_chain(i-1),
                  O => full_addsub(i));
          end generate msb_carry;
     arithmetic_lut: LUT3
     -- synopsys translate_off
     generic map (INIT => X"96")
     -- synopsys translate_on
     port map( I0 => first_operand(i),
               I1 => second_operand(i),
               I2 => subtract,
                O => half_addsub(i));
     pipeline_bit: FD
     port map ( D => full_addsub(i),
                Q => Y(i),
                C => clk);
  end generate bus_width_loop;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity shift_rotate_process is
    Port    (    operand : in std_logic_vector(7 downto 0);
                carry_in : in std_logic;
              inject_bit : in std_logic;
             shift_right : in std_logic;
                   code1 : in std_logic;
                   code0 : in std_logic;
                       Y : out std_logic_vector(7 downto 0);
               carry_out : out std_logic;
                     clk : in std_logic);
    end shift_rotate_process;
architecture low_level_definition of shift_rotate_process is
component mux2_LUT
    Port (  D1 : in std_logic;
            D0 : in std_logic;
           sel : in std_logic;
             Y : out std_logic);
    end component;
component mux4_LUTS_MUXF5
    Port (  D3 : in std_logic;
            D2 : in std_logic;
            D1 : in std_logic;
            D0 : in std_logic;
          sel1 : in std_logic;
          sel0 : in std_logic;
             Y : out std_logic);
    end component;
signal mux_output   : std_logic_vector(7 downto 0);
signal shift_in_bit : std_logic;
signal carry_bit    : std_logic;
begin
  input_bit_mux4: mux4_LUTS_MUXF5
  port map(  D3 => inject_bit,
             D2 => operand(0),
             D1 => operand(7),
             D0 => carry_in,
           sel1 => code1,
           sel0 => code0,
              Y => shift_in_bit);
  bus_width_loop: for i in 0 to 7 generate
  begin
     lsb_shift: if i=0 generate
          begin
       bit_mux2: mux2_LUT
       port map(  D1 => operand(i+1),
                  D0 => shift_in_bit,
                 sel => shift_right,
                   Y => mux_output(i));
          end generate lsb_shift;
     mid_shift: if i>0 and i<7 generate
          begin
       bit_mux2: mux2_LUT
       port map(  D1 => operand(i+1),
                  D0 => operand(i-1),
                 sel => shift_right,
                   Y => mux_output(i));
          end generate mid_shift;
     msb_shift: if i=7 generate
          begin
       bit_mux2: mux2_LUT
       port map(  D1 => shift_in_bit,
                  D0 => operand(i-1),
                 sel => shift_right,
                   Y => mux_output(i));
          end generate msb_shift;
          pipeline_bit: FD
     port map ( D => mux_output(i),
                Q => Y(i),
                C => clk);
  end generate bus_width_loop;
  carry_out_mux2: mux2_LUT
  port map(  D1 => operand(0),
             D0 => operand(7),
            sel => shift_right,
              Y => carry_bit);
  pipeline_bit: FD
  port map ( D => carry_bit,
             Q => carry_out,
             C => clk);
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity arithmetic_process is
    Port (  first_operand : in std_logic_vector(7 downto 0);
           second_operand : in std_logic_vector(7 downto 0);
                 carry_in : in std_logic;
                    code1 : in std_logic;
                    code0 : in std_logic;
                        Y : out std_logic_vector(7 downto 0);
                carry_out : out std_logic;
                      clk : in std_logic);
    end arithmetic_process;
architecture low_level_definition of arithmetic_process is
component addsub8
    Port (  first_operand : in std_logic_vector(7 downto 0);
           second_operand : in std_logic_vector(7 downto 0);
                 carry_in : in std_logic;
                 subtract : in std_logic;
                        Y : out std_logic_vector(7 downto 0);
                carry_out : out std_logic;
                      clk : in std_logic);
    end component;
signal carry_in_bit       : std_logic;
signal carry_out_bit      : std_logic;
signal modified_carry_out : std_logic;
attribute INIT : string;
attribute INIT of carry_input_lut : label is "78";
attribute INIT of carry_output_lut : label is "6";
begin
  carry_input_lut: LUT3
     -- synopsys translate_off
    generic map (INIT => X"78")
     -- synopsys translate_on
  port map( I0 => carry_in,
            I1 => code0,
            I2 => code1,
             O => carry_in_bit );
  add_sub_module: addsub8
  port map (  first_operand => first_operand,
             second_operand => second_operand,
                   carry_in => carry_in_bit,
                   subtract => code1,
                          Y => Y,
                  carry_out => carry_out_bit,
                        clk => clk);
  carry_output_lut: LUT2
     -- synopsys translate_off
    generic map (INIT => X"6")
     -- synopsys translate_on
  port map( I0 => carry_out_bit,
            I1 => code1,
             O => modified_carry_out );
  pipeline_bit: FD
  port map ( D => modified_carry_out,
             Q => carry_out,
             C => clk);
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity register_and_flag_enable is
    Port (      instruction : in std_logic_vector(17 downto 13);
           active_interrupt : in std_logic;
                    T_state : in std_logic;
            register_enable : out std_logic;
                flag_enable : out std_logic;
                        clk : in std_logic);
    end register_and_flag_enable;
architecture low_level_definition of register_and_flag_enable is
signal reg_instruction_decode  : std_logic;
signal register_write_valid    : std_logic;
signal returni_or_shift_decode : std_logic;
signal returni_or_shift_valid  : std_logic;
signal arith_or_logical_decode : std_logic;
signal arith_or_logical_valid  : std_logic;
attribute INIT : string;
attribute INIT of reg_decode_lut        : label is "0155";
attribute INIT of reg_pulse_timing_lut  : label is "8";
attribute INIT of flag_decode1_lut      : label is "00FE";
attribute INIT of flag_decode2_lut      : label is "20";
attribute INIT of flag_pulse_timing_lut : label is "A8";
begin
  reg_decode_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"0155")
     -- synopsys translate_on
  port map( I0 => active_interrupt,
            I1 => instruction(13),
            I2 => instruction(14),
            I3 => instruction(17),
             O => reg_instruction_decode );
  reg_pipeline_bit: FD
  port map ( D => reg_instruction_decode,
             Q => register_write_valid,
             C => clk);
  reg_pulse_timing_lut: LUT2
     -- synopsys translate_off
    generic map (INIT => X"8")
     -- synopsys translate_on
  port map( I0 => T_state,
            I1 => register_write_valid,
             O => register_enable );
  flag_decode1_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"00FE")
     -- synopsys translate_on
  port map( I0 => instruction(13),
            I1 => instruction(14),
            I2 => instruction(15),
            I3 => instruction(17),
             O => arith_or_logical_decode );
  flag_pipeline1_bit: FD
  port map ( D => arith_or_logical_decode,
             Q => arith_or_logical_valid,
             C => clk);
  flag_decode2_lut: LUT3
     -- synopsys translate_off
    generic map (INIT => X"20")
     -- synopsys translate_on
  port map( I0 => instruction(15),
            I1 => instruction(16),
            I2 => instruction(17),
             O => returni_or_shift_decode );
  flag_pipeline2_bit: FD
  port map ( D => returni_or_shift_decode,
             Q => returni_or_shift_valid,
             C => clk);
  flag_pulse_timing_lut: LUT3
     -- synopsys translate_off
    generic map (INIT => X"A8")
     -- synopsys translate_on
  port map( I0 => T_state,
            I1 => arith_or_logical_valid,
            I2 => returni_or_shift_valid,
             O => flag_enable );
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity T_state_and_Reset is
    Port (    reset_input : in std_logic;
           internal_reset : out std_logic;
                  T_state : out std_logic;
                      clk : in std_logic);
    end T_state_and_Reset;
architecture low_level_definition of T_state_and_Reset is
signal reset_delay1     : std_logic;
signal reset_delay2     : std_logic;
signal not_T_state      : std_logic;
signal internal_T_state : std_logic;
attribute INIT : string;
attribute INIT of invert_lut : label is "1";
begin
  delay_flop1: FDS
  port map ( D => '0',
             Q => reset_delay1,
             S => reset_input,
             C => clk);
  delay_flop2: FDS
  port map ( D => reset_delay1,
             Q => reset_delay2,
             S => reset_input,
             C => clk);
  invert_lut: LUT1
     -- synopsys translate_off
    generic map (INIT => "01")
     -- synopsys translate_on
  port map( I0 => internal_T_state,
             O => not_T_state );
  toggle_flop: FDR
  port map ( D => not_T_state,
             Q => internal_T_state,
             R => reset_delay2,
             C => clk);
  T_state <= internal_T_state;
  internal_reset <= reset_delay2;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity zero_flag_logic is
    Port (          data : in std_logic_vector(7 downto 0);
           instruction17 : in std_logic;
           instruction14 : in std_logic;
             shadow_zero : in std_logic;
                   reset : in std_logic;
             flag_enable : in std_logic;
               zero_flag : out std_logic;
                     clk : in std_logic);
    end zero_flag_logic;
architecture low_level_definition of zero_flag_logic is
signal lower_zero       : std_logic;
signal upper_zero       : std_logic;
signal lower_zero_carry : std_logic;
signal data_zero        : std_logic;
signal next_zero_flag   : std_logic;
attribute INIT : string;
attribute INIT of lower_zero_lut : label is "0001";
attribute INIT of upper_zero_lut : label is "0001";
attribute INIT of select_lut     : label is "F870";
begin
  lower_zero_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"0001")
     -- synopsys translate_on
  port map( I0 => data(0),
            I1 => data(1),
            I2 => data(2),
            I3 => data(3),
             O => lower_zero );
  upper_zero_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"0001")
     -- synopsys translate_on
  port map( I0 => data(4),
            I1 => data(5),
            I2 => data(6),
            I3 => data(7),
             O => upper_zero );
   lower_carry: MUXCY
   port map( DI => '0',
             CI => '1',
              S => lower_zero,
              O => lower_zero_carry );
        upper_carry: MUXCY
   port map( DI => '0',
             CI => lower_zero_carry,
              S => upper_zero,
              O => data_zero );
  select_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"F870")
     -- synopsys translate_on
  port map( I0 => instruction14,
            I1 => instruction17,
            I2 => data_zero,
            I3 => shadow_zero,
             O => next_zero_flag );
  zero_flag_flop: FDRE
  port map ( D => next_zero_flag,
             Q => zero_flag,
            CE => flag_enable,
             R => reset,
             C => clk);
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity carry_flag_logic is
    Port ( instruction17 : in std_logic;
           instruction15 : in std_logic;
           instruction14 : in std_logic;
             shift_carry : in std_logic;
           add_sub_carry : in std_logic;
            shadow_carry : in std_logic;
                   reset : in std_logic;
             flag_enable : in std_logic;
              carry_flag : out std_logic;
                     clk : in std_logic);
    end carry_flag_logic;
architecture low_level_definition of carry_flag_logic is
signal carry_status    : std_logic;
signal next_carry_flag : std_logic;
attribute INIT : string;
attribute INIT of status_lut : label is "EC20";
attribute INIT of select_lut : label is "F870";
begin
  status_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"EC20")
     -- synopsys translate_on
  port map( I0 => instruction15,
            I1 => instruction17,
            I2 => add_sub_carry,
            I3 => shift_carry,
             O => carry_status );
  select_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"F870")
     -- synopsys translate_on
  port map( I0 => instruction14,
            I1 => instruction17,
            I2 => carry_status,
            I3 => shadow_carry,
             O => next_carry_flag );
  carry_flag_flop: FDRE
  port map ( D => next_carry_flag,
             Q => carry_flag,
            CE => flag_enable,
             R => reset,
             C => clk);
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity dual_loadable_counter is
    Port (         load_1_value : in std_logic_vector(9 downto 0);
                   load_0_value : in std_logic_vector(9 downto 0);
              select_load_value : in std_logic;
           increment_load_value : in std_logic;
                   normal_count : in std_logic;
                     enable_bar : in std_logic;
                          reset : in std_logic;
                      force_3FF : in std_logic;
                          count : out std_logic_vector(9 downto 0);
                            clk : in std_logic);
    end dual_loadable_counter;
architecture low_level_definition of dual_loadable_counter is
component mux2_LUT
    Port (  D1 : in std_logic;
            D0 : in std_logic;
           sel : in std_logic;
             Y : out std_logic);
    end component;
signal not_enable            : std_logic;
signal selected_load_value   : std_logic_vector(9 downto 0);
signal inc_load_value_carry  : std_logic_vector(8 downto 0);
signal inc_load_value        : std_logic_vector(9 downto 0);
signal selected_count_value  : std_logic_vector(9 downto 0);
signal inc_count_value_carry : std_logic_vector(8 downto 0);
signal inc_count_value       : std_logic_vector(9 downto 0);
signal count_value           : std_logic_vector(9 downto 0);
begin
  invert_enable: INV
  port map(  I => enable_bar,
             O => not_enable);
  count_width_loop: for i in 0 to 9 generate
  begin
     value_select_mux: mux2_LUT
     port map(  D1 => load_1_value(i),
                D0 => load_0_value(i),
               sel => select_load_value,
                 Y => selected_load_value(i));
     count_select_mux: mux2_LUT
     port map(  D1 => count_value(i),
                D0 => inc_load_value(i),
               sel => normal_count,
                 Y => selected_count_value(i));
     register_bit: FDRSE
     port map ( D => inc_count_value(i),
                Q => count_value(i),
                R => reset,
                S => force_3FF,
               CE => not_enable,
                C => clk);
          lsb_carry: if i=0 generate
          begin
       load_inc_carry: MUXCY
       port map( DI => '0',
                 CI => increment_load_value,
                  S => selected_load_value(i),
                  O => inc_load_value_carry(i));
       load_inc_xor: XORCY
       port map( LI => selected_load_value(i),
                 CI => increment_load_value,
                  O => inc_load_value(i));
       count_inc_carry: MUXCY
       port map( DI => '0',
                 CI => normal_count,
                  S => selected_count_value(i),
                  O => inc_count_value_carry(i));
       count_inc_xor: XORCY
       port map( LI => selected_count_value(i),
                 CI => normal_count,
                  O => inc_count_value(i));
          end generate lsb_carry;
     mid_carry: if i>0 and i<9 generate
          begin
       load_inc_carry: MUXCY
       port map( DI => '0',
                 CI => inc_load_value_carry(i-1),
                  S => selected_load_value(i),
                  O => inc_load_value_carry(i));
       load_inc_xor: XORCY
       port map( LI => selected_load_value(i),
                 CI => inc_load_value_carry(i-1),
                  O => inc_load_value(i));
       count_inc_carry: MUXCY
       port map( DI => '0',
                 CI => inc_count_value_carry(i-1),
                  S => selected_count_value(i),
                  O => inc_count_value_carry(i));
       count_inc_xor: XORCY
       port map( LI => selected_count_value(i),
                 CI => inc_count_value_carry(i-1),
                  O => inc_count_value(i));
          end generate mid_carry;
     msb_carry: if i=9 generate
          begin
       load_inc_xor: XORCY
       port map( LI => selected_load_value(i),
                 CI => inc_load_value_carry(i-1),
                  O => inc_load_value(i));
       count_inc_xor: XORCY
       port map( LI => selected_count_value(i),
                 CI => inc_count_value_carry(i-1),
                  O => inc_count_value(i));
          end generate msb_carry;
  end generate count_width_loop;
  count <= count_value;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity program_counter is
    Port (       instruction17 : in std_logic;
                 instruction16 : in std_logic;
                 instruction15 : in std_logic;
                 instruction14 : in std_logic;
                 instruction12 : in std_logic;
               low_instruction : in std_logic_vector(9 downto 0);
                   stack_value : in std_logic_vector(9 downto 0);
            flag_condition_met : in std_logic;
                       T_state : in std_logic;
                         reset : in std_logic;
                     force_3FF : in std_logic;
                 program_count : out std_logic_vector(9 downto 0);
                           clk : in std_logic);
    end program_counter;
architecture low_level_definition of program_counter is
component dual_loadable_counter
    Port (         load_1_value : in std_logic_vector(9 downto 0);
                   load_0_value : in std_logic_vector(9 downto 0);
              select_load_value : in std_logic;
           increment_load_value : in std_logic;
                   normal_count : in std_logic;
                     enable_bar : in std_logic;
                          reset : in std_logic;
                      force_3FF : in std_logic;
                          count : out std_logic_vector(9 downto 0);
                            clk : in std_logic);
    end component;
signal move_group       : std_logic;
signal normal_count     : std_logic;
signal increment_vector : std_logic;
attribute INIT : string;
attribute INIT of move_group_lut   : label is "2A00";
attribute INIT of inc_vector_lut   : label is "1";
attribute INIT of normal_count_lut : label is "2F";
begin
  move_group_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"2A00")
     -- synopsys translate_on
  port map( I0 => instruction14,
            I1 => instruction15,
            I2 => instruction16,
            I3 => instruction17,
             O => move_group );
  normal_count_lut: LUT3
     -- synopsys translate_off
    generic map (INIT => X"2F")
     -- synopsys translate_on
  port map( I0 => instruction12,
            I1 => flag_condition_met,
            I2 => move_group,
             O => normal_count );
  inc_vector_lut: LUT2
     -- synopsys translate_off
    generic map (INIT => X"1")
     -- synopsys translate_on
  port map( I0 => instruction15,
            I1 => instruction16,
             O => increment_vector );
  the_counter: dual_loadable_counter
  port map (         load_1_value => low_instruction,
                     load_0_value => stack_value,
                select_load_value => instruction16,
             increment_load_value => increment_vector,
                     normal_count => normal_count,
                       enable_bar => T_state,
                            reset => reset,
                        force_3FF => force_3FF,
                            count => program_count,
                              clk => clk );
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity flag_test is
    Port ( instruction11 : in std_logic;
           instruction10 : in std_logic;
               zero_flag : in std_logic;
              carry_flag : in std_logic;
           condition_met : out std_logic );
    end flag_test;
architecture low_level_definition of flag_test is
attribute INIT : string;
attribute INIT of decode_lut : label is "5A3C";
begin
  decode_lut: LUT4
     -- synopsys translate_off
    generic map (INIT => X"5A3C")
     -- synopsys translate_on
  port map( I0 => carry_flag,
            I1 => zero_flag,
            I2 => instruction10,
            I3 => instruction11,
             O => condition_met );
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity IO_strobe_logic is
    Port (    instruction17 : in std_logic;
              instruction15 : in std_logic;
              instruction14 : in std_logic;
              instruction13 : in std_logic;
           active_interrupt : in std_logic;
                    T_state : in std_logic;
                      reset : in std_logic;
               write_strobe : out std_logic;
                read_strobe : out std_logic;
                        clk : in std_logic);
    end IO_strobe_logic;
architecture low_level_definition of IO_strobe_logic is
signal IO_type     : std_logic;
signal write_event : std_logic;
signal read_event  : std_logic;
attribute INIT : string;
attribute INIT of IO_type_lut : label is "10";
attribute INIT of write_lut   : label is "1000";
attribute INIT of read_lut    : label is "0100";
begin
  IO_type_lut: LUT3
     -- synopsys translate_off
    generic map (INIT => X"10")
  -- synopsys translate_on
  port map( I0 => instruction14,
            I1 => instruction15,
            I2 => instruction17,
             O => IO_type );
  write_lut: LUT4
  -- synopsys translate_off
    generic map (INIT => X"1000")
  -- synopsys translate_on
  port map( I0 => active_interrupt,
            I1 => T_state,
            I2 => instruction13,
            I3 => IO_type,
             O => write_event );
  write_flop: FDR
  port map ( D => write_event,
             Q => write_strobe,
             R => reset,
             C => clk);
  read_lut: LUT4
  -- synopsys translate_off
    generic map (INIT => X"0100")
  -- synopsys translate_on
  port map( I0 => active_interrupt,
            I1 => T_state,
            I2 => instruction13,
            I3 => IO_type,
             O => read_event );
  read_flop: FDR
  port map ( D => read_event,
             Q => read_strobe,
             R => reset,
             C => clk);
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity stack_ram is
    Port (        Din : in std_logic_vector(9 downto 0);
                 Dout : out std_logic_vector(9 downto 0);
                 addr : in std_logic_vector(4 downto 0);
            write_bar : in std_logic;
                  clk : in std_logic);
    end stack_ram;
architecture low_level_definition of stack_ram is
signal ram_out      : std_logic_vector(9 downto 0);
signal write_enable : std_logic;
begin
  invert_enable: INV
  port map(  I => write_bar,
             O => write_enable);
  bus_width_loop: for i in 0 to 9 generate
  attribute INIT : string;
  attribute INIT of stack_ram_bit : label is "00000000";
  begin
     stack_ram_bit: RAM32X1S
     -- synopsys translate_off
     generic map(INIT => X"00000000")
     -- synopsys translate_on
     port map (    D => Din(i),
                  WE => write_enable,
                WCLK => clk,
                  A0 => addr(0),
                  A1 => addr(1),
                  A2 => addr(2),
                  A3 => addr(3),
                  A4 => addr(4),
                   O => ram_out(i));
     stack_ram_flop: FD
     port map ( D => ram_out(i),
                Q => Dout(i),
                C => clk);
  end generate bus_width_loop;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity stack_counter is
    Port (        instruction17 : in std_logic;
                  instruction16 : in std_logic;
                  instruction14 : in std_logic;
                  instruction13 : in std_logic;
                  instruction12 : in std_logic;
                        T_state : in std_logic;
             flag_condition_met : in std_logic;
               active_interrupt : in std_logic;
                          reset : in std_logic;
                    stack_count : out std_logic_vector(4 downto 0);
                            clk : in std_logic);
    end stack_counter;
architecture low_level_definition of stack_counter is
signal not_interrupt    : std_logic;
signal count_value      : std_logic_vector(4 downto 0);
signal next_count       : std_logic_vector(4 downto 0);
signal count_carry      : std_logic_vector(3 downto 0);
signal half_count       : std_logic_vector(4 downto 0);
signal call_type        : std_logic;
signal valid_to_move    : std_logic;
signal push_or_pop_type : std_logic;
attribute INIT : string;
attribute INIT of valid_move_lut : label is "D";
attribute INIT of call_lut       : label is "8000";
attribute INIT of push_pop_lut   : label is "8C00";
begin
  invert_interrupt: INV
  port map(  I => active_interrupt,
             O => not_interrupt);
  valid_move_lut: LUT2
  -- synopsys translate_off
    generic map (INIT => X"D")
  -- synopsys translate_on
  port map( I0 => instruction12,
            I1 => flag_condition_met,
             O => valid_to_move );
  call_lut: LUT4
  -- synopsys translate_off
    generic map (INIT => X"8000")
  -- synopsys translate_on
  port map( I0 => instruction13,
            I1 => instruction14,
            I2 => instruction16,
            I3 => instruction17,
             O => call_type );
  push_pop_lut: LUT4
  -- synopsys translate_off
    generic map (INIT => X"8C00")
  -- synopsys translate_on
  port map( I0 => instruction13,
            I1 => instruction14,
            I2 => instruction16,
            I3 => instruction17,
             O => push_or_pop_type );
  count_width_loop: for i in 0 to 4 generate
  begin
     register_bit: FDRE
     port map ( D => next_count(i),
                Q => count_value(i),
                R => reset,
               CE => not_interrupt,
                C => clk);
          lsb_count: if i=0 generate
     attribute INIT : string;
     attribute INIT of count_lut : label is "6555";
          begin
       count_lut: LUT4
       -- synopsys translate_off
       generic map (INIT => X"6555")
       -- synopsys translate_on
       port map( I0 => count_value(i),
                 I1 => T_state,
                 I2 => valid_to_move,
                 I3 => push_or_pop_type,
                  O => half_count(i) );
       count_muxcy: MUXCY
       port map( DI => count_value(i),
                 CI => '0',
                  S => half_count(i),
                  O => count_carry(i));
       count_xor: XORCY
       port map( LI => half_count(i),
                 CI => '0',
                  O => next_count(i));
          end generate lsb_count;
     mid_count: if i>0 and i<4 generate
     attribute INIT : string;
     attribute INIT of count_lut : label is "A999";
          begin
       count_lut: LUT4
       -- synopsys translate_off
       generic map (INIT => X"A999")
       -- synopsys translate_on
       port map( I0 => count_value(i),
                 I1 => T_state,
                 I2 => valid_to_move,
                 I3 => call_type,
                  O => half_count(i) );
       count_muxcy: MUXCY
       port map( DI => count_value(i),
                 CI => count_carry(i-1),
                  S => half_count(i),
                  O => count_carry(i));
       count_xor: XORCY
       port map( LI => half_count(i),
                 CI => count_carry(i-1),
                  O => next_count(i));
          end generate mid_count;
     msb_count: if i=4 generate
     attribute INIT : string;
     attribute INIT of count_lut : label is "A999";
          begin
       count_lut: LUT4
       -- synopsys translate_off
       generic map (INIT => X"A999")
       -- synopsys translate_on
       port map( I0 => count_value(i),
                 I1 => T_state,
                 I2 => valid_to_move,
                 I3 => call_type,
                  O => half_count(i) );
       count_xor: XORCY
       port map( LI => half_count(i),
                 CI => count_carry(i-1),
                  O => next_count(i));
          end generate msb_count;
  end generate count_width_loop;
  stack_count <= count_value;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity interrupt_capture is
    Port (        interrupt : in std_logic;
                    T_state : in std_logic;
                      reset : in std_logic;
           interrupt_enable : in std_logic;
           active_interrupt : out std_logic;
                        clk : in std_logic);
    end interrupt_capture;
architecture low_level_definition of interrupt_capture is
signal clean_interrupt        : std_logic;
signal interrupt_pulse        : std_logic;
signal active_interrupt_pulse : std_logic;
attribute INIT : string;
attribute INIT of interrupt_pulse_lut : label is "0080";
begin
  input_flop: FDR
  port map ( D => interrupt,
             Q => clean_interrupt,
             R => reset,
             C => clk);
  interrupt_pulse_lut: LUT4
  -- synopsys translate_off
    generic map (INIT => X"0080")
  -- synopsys translate_on
  port map( I0 => T_state,
            I1 => clean_interrupt,
            I2 => interrupt_enable,
            I3 => active_interrupt_pulse,
             O => interrupt_pulse );
  toggle_flop: FDR
  port map ( D => interrupt_pulse,
             Q => active_interrupt_pulse,
             R => reset,
             C => clk);
  active_interrupt <= active_interrupt_pulse;
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
library unisim;
use unisim.vcomponents.all;
entity interrupt_logic is
    Port (    instruction17 : in std_logic;
              instruction15 : in std_logic;
              instruction14 : in std_logic;
               instruction0 : in std_logic;
           active_interrupt : in std_logic;
                 carry_flag : in std_logic;
                  zero_flag : in std_logic;
                      reset : in std_logic;
           interrupt_enable : out std_logic;
              shaddow_carry : out std_logic;
               shaddow_zero : out std_logic;
                        clk : in std_logic);
    end interrupt_logic;
architecture low_level_definition of interrupt_logic is
signal update_enable    : std_logic;
signal new_enable_value : std_logic;
attribute INIT : string;
attribute INIT of decode_lut : label is "EAAA";
attribute INIT of value_lut  : label is "4";
begin
  decode_lut: LUT4
  -- synopsys translate_off
    generic map (INIT => X"EAAA")
  -- synopsys translate_on
  port map( I0 => active_interrupt,
            I1 => instruction14,
            I2 => instruction15,
            I3 => instruction17,
             O => update_enable );
  value_lut: LUT2
  -- synopsys translate_off
    generic map (INIT => X"4")
  -- synopsys translate_on
  port map( I0 => active_interrupt,
            I1 => instruction0,
             O => new_enable_value );
  int_enable_flop: FDRE
  port map ( D => new_enable_value,
             Q => interrupt_enable,
            CE => update_enable,
             R => reset,
             C => clk);
  preserve_carry_flop: FDE
  port map ( D => carry_flag,
             Q => shaddow_carry,
            CE => active_interrupt,
             C => clk);
  preserve_zero_flop: FDE
  port map ( D => zero_flag,
             Q => shaddow_zero,
            CE => active_interrupt,
             C => clk);
end low_level_definition;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity kcpsm2 is
    Port (      address : out std_logic_vector(9 downto 0);
            instruction : in std_logic_vector(17 downto 0);
                port_id : out std_logic_vector(7 downto 0);
           write_strobe : out std_logic;
               out_port : out std_logic_vector(7 downto 0);
            read_strobe : out std_logic;
                in_port : in std_logic_vector(7 downto 0);
              interrupt : in std_logic;
                  reset : in std_logic;
                    clk : in std_logic);
    end kcpsm2;
architecture macro_level_definition of kcpsm2 is
component data_bus_mux2
    Port (  D1_bus : in std_logic_vector(7 downto 0);
            D0_bus : in std_logic_vector(7 downto 0);
               sel : in std_logic;
             Y_bus : out std_logic_vector(7 downto 0));
    end component;
component data_bus_mux4
    Port (  D3_bus : in std_logic_vector(7 downto 0);
            D2_bus : in std_logic_vector(7 downto 0);
            D1_bus : in std_logic_vector(7 downto 0);
            D0_bus : in std_logic_vector(7 downto 0);
              sel1 : in std_logic;
              sel0 : in std_logic;
             Y_bus : out std_logic_vector(7 downto 0));
    end component;
component data_register_bank
    Port (    Address_A : in std_logic_vector(4 downto 0);
              Din_A_bus : in std_logic_vector(7 downto 0);
            Write_A_bus : in std_logic;
             Dout_A_bus : out std_logic_vector(7 downto 0);
              Address_B : in std_logic_vector(4 downto 0);
             Dout_B_bus : out std_logic_vector(7 downto 0);
                    clk : in std_logic);
    end component;
component logical_bus_processing
    Port (  first_operand : in std_logic_vector(7 downto 0);
           second_operand : in std_logic_vector(7 downto 0);
                    code1 : in std_logic;
                    code0 : in std_logic;
                        Y : out std_logic_vector(7 downto 0);
                      clk : in std_logic);
    end component;
component shift_rotate_process
    Port    (    operand : in std_logic_vector(7 downto 0);
                carry_in : in std_logic;
              inject_bit : in std_logic;
             shift_right : in std_logic;
                   code1 : in std_logic;
                   code0 : in std_logic;
                       Y : out std_logic_vector(7 downto 0);
               carry_out : out std_logic;
                     clk : in std_logic);
    end component;
component arithmetic_process
    Port (  first_operand : in std_logic_vector(7 downto 0);
           second_operand : in std_logic_vector(7 downto 0);
                 carry_in : in std_logic;
                    code1 : in std_logic;
                    code0 : in std_logic;
                        Y : out std_logic_vector(7 downto 0);
                carry_out : out std_logic;
                      clk : in std_logic);
    end component;
component register_and_flag_enable
    Port (      instruction : in std_logic_vector(17 downto 13);
           active_interrupt : in std_logic;
                    T_state : in std_logic;
            register_enable : out std_logic;
                flag_enable : out std_logic;
                        clk : in std_logic);
    end component;
component T_state_and_Reset
    Port (    reset_input : in std_logic;
           internal_reset : out std_logic;
                  T_state : out std_logic;
                      clk : in std_logic);
    end component;
component zero_flag_logic
    Port (          data : in std_logic_vector(7 downto 0);
           instruction17 : in std_logic;
           instruction14 : in std_logic;
             shadow_zero : in std_logic;
                   reset : in std_logic;
             flag_enable : in std_logic;
               zero_flag : out std_logic;
                     clk : in std_logic);
    end component;
component carry_flag_logic
    Port ( instruction17 : in std_logic;
           instruction15 : in std_logic;
           instruction14 : in std_logic;
             shift_carry : in std_logic;
           add_sub_carry : in std_logic;
            shadow_carry : in std_logic;
                   reset : in std_logic;
             flag_enable : in std_logic;
              carry_flag : out std_logic;
                     clk : in std_logic);
    end component;
         component program_counter
    Port (       instruction17 : in std_logic;
                 instruction16 : in std_logic;
                 instruction15 : in std_logic;
                 instruction14 : in std_logic;
                 instruction12 : in std_logic;
               low_instruction : in std_logic_vector(9 downto 0);
                   stack_value : in std_logic_vector(9 downto 0);
            flag_condition_met : in std_logic;
                       T_state : in std_logic;
                         reset : in std_logic;
                     force_3FF : in std_logic;
                 program_count : out std_logic_vector(9 downto 0);
                           clk : in std_logic);
    end component;
    component flag_test
    Port ( instruction11 : in std_logic;
           instruction10 : in std_logic;
               zero_flag : in std_logic;
              carry_flag : in std_logic;
           condition_met : out std_logic );
    end component;
  component IO_strobe_logic
  Port (    instruction17 : in std_logic;
            instruction15 : in std_logic;
            instruction14 : in std_logic;
            instruction13 : in std_logic;
         active_interrupt : in std_logic;
                  T_state : in std_logic;
                    reset : in std_logic;
             write_strobe : out std_logic;
              read_strobe : out std_logic;
                      clk : in std_logic);
  end component;
  component stack_ram
  Port (        Din : in std_logic_vector(9 downto 0);
               Dout : out std_logic_vector(9 downto 0);
               addr : in std_logic_vector(4 downto 0);
          write_bar : in std_logic;
                clk : in std_logic);
  end component;
  component stack_counter
  Port (        instruction17 : in std_logic;
                instruction16 : in std_logic;
                instruction14 : in std_logic;
                instruction13 : in std_logic;
                instruction12 : in std_logic;
                      T_state : in std_logic;
           flag_condition_met : in std_logic;
             active_interrupt : in std_logic;
                        reset : in std_logic;
                  stack_count : out std_logic_vector(4 downto 0);
                          clk : in std_logic);
  end component;
  component interrupt_capture
  Port (        interrupt : in std_logic;
                  T_state : in std_logic;
                    reset : in std_logic;
         interrupt_enable : in std_logic;
         active_interrupt : out std_logic;
                      clk : in std_logic);
  end component;
  component interrupt_logic
  Port (    instruction17 : in std_logic;
            instruction15 : in std_logic;
            instruction14 : in std_logic;
             instruction0 : in std_logic;
         active_interrupt : in std_logic;
               carry_flag : in std_logic;
                zero_flag : in std_logic;
                    reset : in std_logic;
         interrupt_enable : out std_logic;
            shaddow_carry : out std_logic;
             shaddow_zero : out std_logic;
                      clk : in std_logic);
  end component;
signal T_state        : std_logic;
signal internal_reset : std_logic;
signal sX_register           : std_logic_vector(7 downto 0);
signal sY_register           : std_logic_vector(7 downto 0);
signal register_write_enable : std_logic;
signal second_operand          : std_logic_vector(7 downto 0);
signal logical_result          : std_logic_vector(7 downto 0);
signal shift_and_rotate_result : std_logic_vector(7 downto 0);
signal shift_and_rotate_carry  : std_logic;
signal arithmetic_result       : std_logic_vector(7 downto 0);
signal arithmetic_carry        : std_logic;
signal ALU_result              : std_logic_vector(7 downto 0);
signal carry_flag         : std_logic;
signal zero_flag          : std_logic;
signal flag_clock_enable  : std_logic;
signal flag_condition_met : std_logic;
signal shaddow_carry_flag : std_logic;
signal shaddow_zero_flag  : std_logic;
signal interrupt_enable   : std_logic;
signal active_interrupt   : std_logic;
signal program_count  : std_logic_vector(9 downto 0);
signal stack_pop_data : std_logic_vector(9 downto 0);
signal stack_pointer  : std_logic_vector(4 downto 0);
begin
  out_port <= sX_register;
  port_id <= second_operand;
  basic_control: T_state_and_Reset
  port map (    reset_input => reset,
             internal_reset => internal_reset,
                    T_state => T_state,
                        clk => clk);
  data_registers: data_register_bank
  port map (   Address_A => instruction(12 downto 8),
               Din_A_bus => ALU_result,
             Write_A_bus => register_write_enable,
              Dout_A_bus => sX_register,
               Address_B => instruction(7 downto 3),
              Dout_B_bus => sY_register,
                     clk => clk);
  zero: zero_flag_logic
  port map (          data => ALU_result,
             instruction17 => instruction(17),
             instruction14 => instruction(14),
               shadow_zero => shaddow_zero_flag,
                     reset => internal_reset,
               flag_enable => flag_clock_enable,
                 zero_flag => zero_flag,
                       clk => clk);
  carry: carry_flag_logic
  port map ( instruction17 => instruction(17),
             instruction15 => instruction(15),
             instruction14 => instruction(14),
               shift_carry => shift_and_rotate_carry,
             add_sub_carry => arithmetic_carry,
              shadow_carry => shaddow_carry_flag,
                     reset => internal_reset,
               flag_enable => flag_clock_enable,
                carry_flag => carry_flag,
                       clk => clk);
  reg_and_flag_enables: register_and_flag_enable
  port map (     instruction => instruction(17 downto 13),
            active_interrupt => active_interrupt,
                     T_state => T_state,
             register_enable => register_write_enable,
                 flag_enable => flag_clock_enable,
                         clk => clk);
  test_flags: flag_test
  port map ( instruction11 => instruction(11),
             instruction10 => instruction(10),
                 zero_flag => zero_flag,
                carry_flag => carry_flag,
             condition_met => flag_condition_met );
  operand_select: data_bus_mux2
  port map ( D1_bus => sY_register,
             D0_bus => instruction(7 downto 0),
                sel => instruction(16),
              Y_bus => second_operand);
  logical_group: logical_bus_processing
  port map (  first_operand => sX_register,
             second_operand => second_operand,
                      code1 => instruction(14),
                      code0 => instruction(13),
                          Y => logical_result,
                        clk => clk);
  arithmetic_group: arithmetic_process
  port map (  first_operand => sX_register,
             second_operand => second_operand,
                   carry_in => carry_flag,
                      code1 => instruction(14),
                      code0 => instruction(13),
                          Y => arithmetic_result,
                  carry_out => arithmetic_carry,
                        clk => clk);
  shift_group: shift_rotate_process
  port map (    operand => sX_register,
               carry_in => carry_flag,
             inject_bit => instruction(0),
            shift_right => instruction(3),
                  code1 => instruction(2),
                  code0 => instruction(1),
                      Y => shift_and_rotate_result,
              carry_out => shift_and_rotate_carry,
                    clk => clk);
  ALU_mux: data_bus_mux4
  port map (  D3_bus => shift_and_rotate_result,
              D2_bus => in_port,
              D1_bus => arithmetic_result,
              D0_bus => logical_result,
                sel1 => instruction(17),
                sel0 => instruction(15),
               Y_bus => ALU_result);
  prog_count: program_counter
  port map (      instruction17 => instruction(17),
                  instruction16 => instruction(16),
                  instruction15 => instruction(15),
                  instruction14 => instruction(14),
                  instruction12 => instruction(12),
                low_instruction => instruction(9 downto 0),
                    stack_value => stack_pop_data,
             flag_condition_met => flag_condition_met,
                        T_state => T_state,
                          reset => internal_reset,
                      force_3FF => active_interrupt,
                  program_count => program_count,
                            clk => clk );
  address <= program_count;
  stack_memory: stack_ram
  port map (       Din => program_count,
                  Dout => stack_pop_data,
                  addr => stack_pointer,
             write_bar => T_state,
                   clk => clk );
  stack_control: stack_counter
  port map (      instruction17 => instruction(17),
                  instruction16 => instruction(16),
                  instruction14 => instruction(14),
                  instruction13 => instruction(13),
                  instruction12 => instruction(12),
                        T_state => T_state,
             flag_condition_met => flag_condition_met,
               active_interrupt => active_interrupt,
                          reset => internal_reset,
                    stack_count => stack_pointer,
                            clk => clk );
  IO_strobes: IO_strobe_logic
  port map (    instruction17 => instruction(17),
                instruction15 => instruction(15),
                instruction14 => instruction(14),
                instruction13 => instruction(13),
             active_interrupt => active_interrupt,
                     T_state  => T_state,
                        reset => internal_reset,
                 write_strobe => write_strobe,
                  read_strobe => read_strobe,
                          clk => clk );
  get_interrupt: interrupt_capture
  port map (        interrupt => interrupt,
                      T_state => T_state,
                        reset => internal_reset,
             interrupt_enable => interrupt_enable,
             active_interrupt => active_interrupt,
                          clk => clk );
  interrupt_control: interrupt_logic
  port map (    instruction17 => instruction(17),
                instruction15 => instruction(15),
                instruction14 => instruction(14),
                 instruction0 => instruction(0),
             active_interrupt => active_interrupt,
                   carry_flag => carry_flag,
                    zero_flag => zero_flag,
                        reset => internal_reset,
             interrupt_enable => interrupt_enable,
                shaddow_carry => shaddow_carry_flag,
                 shaddow_zero => shaddow_zero_flag,
                          clk => clk );
end macro_level_definition;
