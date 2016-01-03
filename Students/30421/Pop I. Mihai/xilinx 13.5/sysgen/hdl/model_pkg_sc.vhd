
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
use work.sim_pkg.all;
package model_pkg is
type t_syst_mac_fir_properties is
record
  cascade_dly         : integer;
  actual_taps         : integer;
  symmetry            : integer;
  odd_symmetry        : integer;
  full_taps           : integer;
  ipbuff_depth        : integer;
  ipbuff_rate         : integer;
  ipbuff_thresh       : integer;
  opbuff_depth        : integer;
  opbuff_rate         : integer;
  opbuff_thresh       : integer;
  zpf                 : integer;
  clk_per_chan        : integer;
end record;
function hex_to_int (
         hexstring : string;
         width     : integer;
         signed    : boolean )
         return integer;
function bin_to_int (
         binstring : string;
         width     : integer;
         signed    : boolean )
         return integer;
procedure read_coe_string (
         file filename : text;
         str_out       : out string;
         last_char     : out integer);


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
function fir_v3_prop ( reqs : t_reqs ) return t_syst_mac_fir_properties;

end model_pkg;
package body model_pkg is
function hex_to_int( hexstring: string; width: integer; signed: boolean) return integer IS
variable ch       : character;
constant length   : integer := hexstring'length;
variable intval   : integer := 0;
begin
  intval := 0;
  for i in length downto 1 loop
    ch := hexstring(i);
    case ch is
      when '0'        => null;
      when '1'        => intval := intval +  1 * 16**(length-i);
      when '2'        => intval := intval +  2 * 16**(length-i);
      when '3'        => intval := intval +  3 * 16**(length-i);
      when '4'        => intval := intval +  4 * 16**(length-i);
      when '5'        => intval := intval +  5 * 16**(length-i);
      when '6'        => intval := intval +  6 * 16**(length-i);
      when '7'        => intval := intval +  7 * 16**(length-i);
      when '8'        => intval := intval +  8 * 16**(length-i);
      when '9'        => intval := intval +  9 * 16**(length-i);
      when 'A' | 'a'  => intval := intval + 10 * 16**(length-i);
      when 'B' | 'b'  => intval := intval + 11 * 16**(length-i);
      when 'C' | 'c'  => intval := intval + 12 * 16**(length-i);
      when 'D' | 'd'  => intval := intval + 13 * 16**(length-i);
      when 'E' | 'e'  => intval := intval + 14 * 16**(length-i);
      when 'F' | 'f'  => intval := intval + 15 * 16**(length-i);
      when others     => assert false
        report "Invalid hexadecimal value." severity error;
        intval := 0;
    end case;
  end loop;
  if signed and intval >= 2**(width-1) then
    intval := intval - 2**width;
  end if;
  return intval;
end hex_to_int;
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
procedure read_coe_string (
                      file filename : text;
                      str_out       : out string;
                      last_char     : out integer
                      ) is

  variable ln       : line;
  variable char     : character;
  variable str_test : boolean;

begin

  readline(filename, ln);
  for i in str_out'range loop
    str_out(i):=' ';
  end loop;
  for i in str_out'range loop
    read(ln, char);
    str_out(i) := char;

    if char=';' then
      last_char := i;
      exit;
    end if;
  end loop;

end read_coe_string;
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
      report "Interpolating symmetric case with Unsigned coefficients, coef_width = " & int_to_string(coef_width-1);
      return coef_width-1;
    end if;

  else
    report "Normal case, coef_width = " & int_to_string(coef_width);
    return coef_width;

  end if;

end calc_coe_width;
function get_full_taps ( num_taps         : integer;
                         actual_taps      : integer;
                         symmetry         : integer
                         )
                         return integer is
begin
  return actual_taps*(2**symmetry)-(num_taps rem (2**symmetry));

end get_full_taps;
function fir_v3_prop ( reqs : t_reqs ) return t_syst_mac_fir_properties is

  variable single_rate            : t_define_single_rate;
  variable decimation             : t_define_decimation;
  variable halfband               : t_define_halfband;
  variable interpolated           : t_define_interpolated;
  variable hilbert                : t_define_hilbert;
  variable interpolation          : t_define_interpolation;
  variable halfband_decimation    : t_define_halfband_decimation;
  variable halfband_interpolation : t_define_halfband_interpolation;
  variable sympair_interpolation  : t_define_sympair_interpolation;
  variable pq_interpolation       : t_define_pq_interpolation;
  variable pq_decimation          : t_define_pq_decimation;
  variable properties             : t_syst_mac_fir_properties;
  variable reqs_temp              : t_reqs;
  variable zpf                    : integer := 1;
begin
  properties.zpf             := 1;
  properties.ipbuff_depth    := 1;
  properties.opbuff_depth    := 0;
  properties.opbuff_rate     := 0;
  properties.opbuff_thresh   := 0;


  case reqs.filter_type is
  when c_single_rate =>
    single_rate                   :=define_single_rate(reqs);
    properties.cascade_dly        :=single_rate.latency;
    properties.actual_taps        :=single_rate.param.num_taps_calced;
    properties.symmetry           :=single_rate.param.symmetry;
    properties.odd_symmetry       :=single_rate.param.odd_symmetry;
    properties.clk_per_chan       :=single_rate.param.clk_per_chan;
  when c_polyphase_decimating =>
    decimation                    :=define_decimation(reqs);
    properties.cascade_dly        :=decimation.latency;
    properties.actual_taps        :=decimation.param.num_taps_calced;
    properties.symmetry           :=decimation.param.symmetry;
    properties.odd_symmetry       :=decimation.param.odd_symmetry;
    if decimation.has_output_buffer then
      properties.opbuff_depth     := decimation.buffer_page_size;
    else
      properties.opbuff_depth     := 0;
    end if;
    properties.opbuff_rate        :=decimation.output_rate;
    properties.opbuff_thresh      :=properties.opbuff_depth-1;
    properties.clk_per_chan       :=decimation.param.clk_per_chan;
  when c_polyphase_interpolating =>
    if reqs.symmetry=1 then
      reqs_temp:=reqs;
      reqs_temp.filter_type       :=c_interpolating_symmetry;
      sympair_interpolation       :=define_sympair_interpolation(reqs_temp);
      properties.cascade_dly      :=sympair_interpolation.latency;
      properties.actual_taps      :=sympair_interpolation.param.num_taps_calced;
      properties.symmetry         :=sympair_interpolation.param.symmetry;
      properties.odd_symmetry     :=sympair_interpolation.param.odd_symmetry;
      if sympair_interpolation.has_output_buffer then
        properties.opbuff_depth   := sympair_interpolation.buffer_page_size;
      else
        properties.opbuff_depth   := 0;
      end if;
      properties.opbuff_rate      :=sympair_interpolation.output_rate;
      properties.opbuff_thresh    :=properties.opbuff_depth-reqs.inter_rate;
      properties.clk_per_chan     :=sympair_interpolation.param.clk_per_chan;
    else
      interpolation               :=define_interpolation(reqs);
      properties.cascade_dly      :=interpolation.latency;
      properties.actual_taps      :=interpolation.param.num_taps_calced;
      properties.symmetry         :=interpolation.param.symmetry;
      properties.odd_symmetry     :=interpolation.param.odd_symmetry;
      if interpolation.has_input_buffer then
        properties.ipbuff_depth   := interpolation.buffer_page_size;
      else
        properties.ipbuff_depth   := 1;
      end if;
      properties.clk_per_chan     :=interpolation.param.clk_per_chan;
    end if;
  when c_hilbert_transform =>
    hilbert                       :=define_hilbert(reqs);
    properties.cascade_dly        :=hilbert.latency;
    properties.actual_taps        :=hilbert.param.num_taps_calced;
    properties.symmetry           :=hilbert.param.symmetry;
    properties.odd_symmetry       :=hilbert.param.odd_symmetry;
    properties.clk_per_chan       :=hilbert.param.clk_per_chan;
  when c_interpolated_transform =>
    interpolated                  :=define_interpolated(reqs);
    properties.cascade_dly        :=interpolated.latency;
    properties.actual_taps        :=interpolated.param.num_taps_calced;
    properties.symmetry           :=interpolated.param.symmetry;
    properties.odd_symmetry       :=interpolated.param.odd_symmetry;
    properties.zpf                :=reqs.zero_packing_factor;
    properties.clk_per_chan       :=interpolated.param.clk_per_chan;
  when c_halfband_transform =>
    halfband                      :=define_halfband(reqs);
    properties.cascade_dly        :=halfband.latency;
    properties.actual_taps        :=halfband.param.num_taps_calced;
    properties.symmetry           :=halfband.param.symmetry;
    properties.odd_symmetry       :=halfband.param.odd_symmetry;
    properties.clk_per_chan       :=halfband.param.clk_per_chan;
  when c_decimating_half_band =>
    halfband_decimation           :=define_halfband_decimation(reqs);
    properties.cascade_dly        :=halfband_decimation.latency;
    properties.actual_taps        :=halfband_decimation.param.num_taps_calced;
    properties.symmetry           :=halfband_decimation.param.symmetry;
    properties.odd_symmetry       :=halfband_decimation.param.odd_symmetry;
    properties.ipbuff_depth       :=halfband_decimation.buffer_page_size;
    properties.clk_per_chan       :=halfband_decimation.param.clk_per_chan;
  when c_interpolating_half_band =>
    halfband_interpolation        :=define_halfband_interpolation(reqs);
    properties.cascade_dly        :=halfband_interpolation.latency;
    properties.actual_taps        :=halfband_interpolation.param.num_taps_calced;
    properties.symmetry           :=halfband_interpolation.param.symmetry;
    properties.odd_symmetry       :=halfband_interpolation.param.odd_symmetry;
    properties.opbuff_depth       :=halfband_interpolation.buffer_page_size;
    properties.opbuff_rate        :=halfband_interpolation.output_rate;
    properties.opbuff_thresh      :=properties.opbuff_depth-2;
    properties.clk_per_chan       :=halfband_interpolation.param.clk_per_chan;
  when c_polyphase_pq =>
    if reqs.inter_rate>reqs.deci_rate then
      pq_interpolation            :=define_pq_interpolation(reqs);
      properties.cascade_dly      :=pq_interpolation.latency;
      properties.actual_taps      :=pq_interpolation.param.num_taps_calced;
      properties.symmetry         :=pq_interpolation.param.symmetry;
      properties.odd_symmetry     :=pq_interpolation.param.odd_symmetry;
      if pq_interpolation.has_input_buffer then
        properties.ipbuff_depth   := pq_interpolation.buffer_page_size;
      else
        properties.ipbuff_depth   := 1;
      end if;
      properties.clk_per_chan     :=pq_interpolation.param.clk_per_chan;
    else
      pq_decimation               :=define_pq_decimation(reqs);
      properties.cascade_dly      :=pq_decimation.latency;
      properties.actual_taps      :=pq_decimation.param.num_taps_calced;
      properties.symmetry         :=pq_decimation.param.symmetry;
      properties.odd_symmetry     :=pq_decimation.param.odd_symmetry;
      if pq_decimation.has_output_buffer then
        properties.opbuff_depth   := pq_decimation.buffer_page_size;
      else
        properties.opbuff_depth   := 0;
      end if;
      properties.opbuff_rate      :=pq_decimation.output_rate;
      properties.opbuff_thresh    :=properties.opbuff_depth-1;
      properties.clk_per_chan     :=pq_decimation.param.clk_per_chan;
    end if;
  when others =>
    report "FIR MODEL: Invalid filter type." severity failure;
  end case;
  properties.full_taps := get_full_taps(reqs.num_taps,properties.actual_taps,properties.symmetry);

  properties.ipbuff_rate := properties.clk_per_chan/reqs.inter_rate;

  properties.ipbuff_thresh := properties.ipbuff_depth-1;



  return properties;
end;
  end model_pkg;
