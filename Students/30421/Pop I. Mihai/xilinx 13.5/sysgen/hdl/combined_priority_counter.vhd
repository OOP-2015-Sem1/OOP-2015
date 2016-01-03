
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
library unisim;
use unisim.vcomponents.all;
entity combined_priority_counter is
  generic (
        C_HAS_C_IN   : integer := 0;
        C_WIDTH      : integer := 8;
        C_CONST_0_VAL    : integer := 1;
        C_CONST_1_VAL    : integer := 2;
        C_CONST_2_VAL    : integer := 3;
        C_INIT_SCLR_VAL  : integer :=0
          );
  port (
        A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
        ADD_C0     : in std_logic:='0';
        ADD_C1     : in std_logic:='0';
        ADD_C2     : in std_logic:='0';
        C_IN       : in  std_logic := '0';
        D          : out std_logic_vector(C_WIDTH-1 downto 0);
        CE        : in  std_logic := '1';
        SCLR      : in  std_logic := '0';
        CLK       : in  std_logic := '0'
       );
end combined_priority_counter;
architecture rtl of combined_priority_counter is
  function int_2_std_logic( a : integer) return std_logic is
    variable a_int : std_logic;
  begin
    if a = 1 then a_int := '1';
    else a_int := '0';
    end if;
    return a_int;
  end;

  function std_logic_to_bit( a : std_logic) return bit is
  begin
    if (a='1') then
      return '1';
    else
      return '0';
    end if;
  end;

  signal carry_int : std_logic_vector(C_WIDTH   downto 0);
  signal d_loc     : std_logic_vector(C_WIDTH-1 downto 0);
  signal d_int     : std_logic_vector(C_WIDTH-1 downto 0);

  constant C0_SLV: std_logic_vector(C_WIDTH-1 downto 0):=std_logic_vector(to_signed(C_CONST_0_VAL,C_WIDTH));
  constant C1_SLV: std_logic_vector(C_WIDTH-1 downto 0):=std_logic_vector(to_signed(C_CONST_1_VAL,C_WIDTH));
  constant C2_SLV: std_logic_vector(C_WIDTH-1 downto 0):=std_logic_vector(to_signed(C_CONST_2_VAL,C_WIDTH));
  constant C_INIT_SCLR_VAL_SLV: std_logic_vector(C_WIDTH-1 downto 0):=std_logic_vector(to_signed(C_INIT_SCLR_VAL,C_WIDTH));
  signal d_reg_int: std_logic_vector(C_WIDTH-1 downto 0):=C_INIT_SCLR_VAL_SLV;
begin
  gen_c_in1 : if C_HAS_C_IN = 1 generate
    carry_int(0) <= c_in;
  end generate;
  gen_c_in0 : if C_HAS_C_IN = 0 generate
    carry_int(0) <= '0';
  end generate;
  gen_adders: for i in 0 to C_WIDTH-1 generate
  constant init_bit:bit:=std_logic_to_bit(C_INIT_SCLR_VAL_SLV(i));
  signal set,reset:std_logic;
  begin


    d_loc(i)<=  a_in(i) xor C2_SLV(i) when (ADD_C2='1' and ADD_C1='1' and ADD_C0='1') else
                a_in(i) xor C1_SLV(i) when (ADD_C1='1' and ADD_C0='1') else
                a_in(i) xor C0_SLV(i) when (ADD_C0='1') else
                a_in(i);
    i_muxcy : muxcy_l
      port map (
      di => a_in(i),
      ci => carry_int(i),
      s  => d_loc(i),
      lo => carry_int(i+1)
      );
    i_xorcy : xorcy
      port map (
      li => d_loc(i),
      ci => carry_int(i),
      o  => d_int(i)
      );
    gen_high_reset: if (C_INIT_SCLR_VAL_SLV(i)='1') generate
      reset<='0';
      set<=sclr;
    end generate gen_high_reset;

    gen_low_reset: if (C_INIT_SCLR_VAL_SLV(i)/='1') generate
      reset<=sclr;
      set<='0';
    end generate gen_low_reset;
    i_reg: FDRSE
    generic map(
      INIT => init_bit
    )
    port map(
      C => clk,
      CE => ce,
      R => reset,
      S => set,
      D => d_int(i),
      Q => d_reg_int(i)
    );
  end generate;

 D<=d_reg_int;
end rtl;
