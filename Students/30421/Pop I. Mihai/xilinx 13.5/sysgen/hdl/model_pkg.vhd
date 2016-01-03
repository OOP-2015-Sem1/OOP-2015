
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
library XilinxCoreLib;
use XilinxCoreLib.prims_utils_v9_0.all;
library std;
use std.textio.all;
library work;
use work.fir_compiler_v3_2_sim_pkg.all;
package model_pkg is
function bin_to_int (
         binstring : string;
         width     : integer;
         signed    : boolean )
         return integer;

FUNCTION get_gcd(x,y : INTEGER) RETURN INTEGER;
function calc_coe_width (
         c_family         : string;
         c_xdevicefamily  : string;
         filter_arch      : integer;
         filter_type      : integer;
         rate_change_type : integer;
         interp_rate      : integer;
         num_taps         : integer;
         symmetry         : integer;
         coef_type        : integer;
         coef_width       : integer  )
         return integer;
end model_pkg;
package body model_pkg is
function bin_to_int( binstring: string; width: integer; signed: boolean) return integer IS
variable str      : string (1 to width) := binstring;
variable ch       : character;
variable intval   : integer := 0;
begin
  intval := 0;
  for i in width downto 2 loop
    ch := str(i);
    case ch is
      when '0'    => null;
      when '1'    => intval := intval + 2**(width-i);
      when others => assert false
                     report "Invalid binary value." severity error;
                     intval := 0;
    end case;
  end loop;
  ch := str(1);
  if ch = '1' then
    if signed then
      intval := intval - 2**(width-1);
    else
      intval := intval + 2**(width-1);
    end if;
  end if;
  return intval;
end bin_to_int;
function get_gcd(x,y : integer) return integer is
  variable int_x : integer := x;
  variable int_y : integer := y;
  variable int_t : integer;
begin
  while int_x > 0 loop
    if (int_x < int_y) then
      int_t := int_x;
      int_x := int_y;
      int_y := int_t;
    end if;
    int_x := int_x - int_y;
  end loop;
  return int_y;
end get_gcd;
function calc_coe_width ( c_family         : string;
                          c_xdevicefamily  : string;
                          filter_arch      : integer;
                          filter_type      : integer;
                          rate_change_type : integer;
                          interp_rate      : integer;
                          num_taps         : integer;
                          symmetry         : integer;
                          coef_type        : integer;
                          coef_width       : integer  )
                          return integer is
begin

  if (derived(c_family,"virtex4") or derived(c_family,"virtex5") or c_xdevicefamily="spartan3adsp") and
     filter_arch = 1 and
     (filter_type=c_interpolating_symmetry or filter_type=c_polyphase_interpolating) and
     rate_change_type=0 and
     symmetry=1 then

    if coef_type = c_signed then
      report "Interpolating symmetric case with Signed coefficients, coef_width = " & int_to_string(coef_width-1);
      return coef_width-1;
    else
      report "Interpolating symmetric case with Unsigned coefficients, coef_width = " & int_to_string(coef_width-2);
      return coef_width-2;
    end if;

  else
    report "Normal case, coef_width = " & int_to_string(coef_width);
    return coef_width;

  end if;

end calc_coe_width;
end model_pkg;
