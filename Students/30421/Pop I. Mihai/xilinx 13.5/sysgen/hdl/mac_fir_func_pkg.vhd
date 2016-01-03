
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
use IEEE.numeric_std.all;
library work;
use work.mac_fir_utils_pkg.all;
use work.mac_fir_const_pkg.all;
library xilinxcorelib;
use xilinxcorelib.prims_utils_v9_0.all;
library std;
use std.textio.all;
package mac_fir_func_pkg is
  function mac_fir_generic_analysis (
           reqs : t_reqs;
           c_family :string )
           return t_details;

  function parallel_sym_check(
           reqs: t_reqs;
           details    : t_details;
           num_macs: integer;
           deci_rate: integer)
           return integer;
  function gen_mif_files (
           elab_dir   : string;
           mif_prefix : string;
           mif_file   : string;
           reqs       : t_reqs;
           details    : t_details )
           return integer;

  function convert_bin_mif_to_hex (
           elab_dir   : string;
           mif_file   : string;
           num_taps   : integer;
           coef_width : integer;
           num_filts  : integer )
           return integer;

  function read_coef_data (
           filename         : string;
           number_of_values : integer;
           coef_width       : integer;
           offset           : integer )
           return t_coefficients;

  function read_coef_data_bin (
           filename         : string;
           number_of_values : integer;
           coef_width       : integer;
           offset           : integer )
           return t_coefficients;

  function write_coef_data_hex (
           filename         : string;
           number_of_values : integer;
           coef_width       : integer;
           coef_data        : t_coefficients )
           return integer;
  function write_coef_data (
           filename         : string;
           number_of_values : integer;
           coef_width       : integer;
           coef_data        : t_coefficients )
           return integer;

  function get_number_of_inputs (
           filename: string )
           return integer;

  function hex_to_std_logic_vector (
           hexstring : string;
           width     : integer )
           return std_logic_vector;

  function std_logic_vector_to_hex (
           slv : std_logic_vector )
           return string;
  function read_mem_mif_file (
           filename         : string;
           number_of_values : integer;
           coef_width       : integer;
           offset           : integer )
           return t_coefficients;

  function v4_bram_prim_sel(
           width:integer;
           depth:integer)
           return  string;

  function bmg2v1_bram_prim_sel(
           width:integer;
           depth:integer)
           return  integer;

  function v4_bram_prim_width(prim:string) return integer;

  function bmg2v1_bram_prim_width(prim:integer) return integer;

  function map_da_type ( type_in  : integer ) return integer;
  function map_da_rate (
           type_in     : integer;
           interp_rate : integer;
           decim_rate  : integer )
           return integer;
  function get_da_baat (
           filt_type    : integer;
           databits     : integer;
           clk_per_samp : integer;
           symmetry     : integer)
           return integer;
end mac_fir_func_pkg;
package body mac_fir_func_pkg is
function mac_fir_generic_analysis ( reqs: t_reqs; c_family :string ) return t_details is
variable details : t_details;
variable data_depth,
         clk_per_chan_hlfbnd_calc,
         num_macs_hlfbnd_calc,
         temp_taps_calced,
         para_struct,
         p_val,
         q_val
         : integer;

constant srl16_mem_depth_thres: integer:=UTILS_PKG_select_integer(32,64,
                                                                  c_family="virtex5");
constant dram_mem_depth_thres: integer:=UTILS_PKG_select_integer(16,32,
                                                                  c_family="virtex5");
begin
  if (reqs.filter_type = c_single_rate
      or reqs.filter_type = c_interpolated_transform
      or (reqs.filter_type = c_polyphase_decimating) ) then
    details.symmetry     := reqs.symmetry;
    details.odd_symmetry := reqs.odd_symmetry;
    details.neg_symmetry := reqs.neg_symmetry;
    details.num_taps_calced := (reqs.num_taps/2**reqs.symmetry);
    if ( reqs.num_taps rem 2**reqs.symmetry >0) then
            details.num_taps_calced := details.num_taps_calced+1;
    end if;
  else
    details.symmetry        :=0;
    details.odd_symmetry    :=0;
    details.neg_symmetry    :=0;
    details.num_taps_calced :=reqs.num_taps;
  end if;

  details.deci_packed              := 0;
  details.use_data_cascade         := 0;
  details.acum_required            := 1;
  details.no_data_mem              := 0;
  details.parallel_datasym_mem     :=0;
  details.latch_last_phase_dly_mod :=0;
  details.multi_write_deci_rate    :=1;
  details.half_band_inter_dly      :=0;
  details.single_mac_modifier      :=0;
  para_struct:=0;
  case (reqs.filter_type) is
  when c_single_rate  =>
      if ( reqs.clk_per_samp/reqs.num_channels = 0 ) then
        report "not enough clock cycles per sample to process all channels, must have a minimum of 1 clk cycle per channel" severity error;
      elsif ( reqs.clk_per_samp rem reqs.num_channels > 0 ) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;
      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.num_taps_per_mac := details.clk_per_chan;
      details.num_macs := details.num_taps_calced/details.num_taps_per_mac;

      if ( details.num_taps_calced rem details.num_taps_per_mac > 0 ) then
        details.num_macs:=details.num_macs+1;
      end if;
      if (reqs.tap_balance=1) then
        details.clk_per_chan:=details.num_taps_calced/details.num_macs;
        if (details.num_taps_calced rem details.num_macs >0) then
          details.clk_per_chan:=details.clk_per_chan+1;
        end if;
        details.num_taps_per_mac := details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac;
       details.num_taps_calced:=details.num_macs*details.num_taps_per_mac;
      details.inter_rate := 1;
      details.deci_rate  := 1;
      if (details.clk_per_chan=1 and reqs.num_channels=1 and details.symmetry=0) then
        details.use_data_cascade:=1;
      end if;

      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        if (details.num_macs/=1) then
          details.acum_required := 0;
        end if;
        details.no_data_mem   := 1;
        if (details.symmetry = 1) then
          details.parallel_datasym_mem := 1;
        end if;
      end if;
      if (details.clk_per_chan=1 and details.symmetry=1
          and parallel_sym_check(reqs,details,details.num_macs,details.deci_rate)=0
          and reqs.c_has_nd=1) then
        details.symmetry     := 0;
        details.odd_symmetry := 0;
        details.neg_symmetry := 0;
        details.num_taps_calced := reqs.num_taps;

        details.num_macs := details.num_taps_calced/details.num_taps_per_mac;
        if ( details.num_taps_calced rem details.num_taps_per_mac > 0 ) then
          details.num_macs:=details.num_macs+1;
        end if;

        details.num_taps_calced:=details.num_macs*details.num_taps_per_mac;
      end if;
  when c_polyphase_decimating =>
      if (reqs.clk_per_samp rem reqs.num_channels > 0) then
              report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp/reqs.num_channels;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.num_taps_per_phase := details.num_taps_calced/reqs.deci_rate;
      if ( details.num_taps_calced rem reqs.deci_rate > 0) then
        details.num_taps_per_phase := details.num_taps_per_phase+1;
      end if;
      details.num_taps_per_mac := details.clk_per_chan;
      details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;
      if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
        details.num_macs := details.num_macs+1;
      end if;

      if ( details.clk_per_chan=1 and details.symmetry=1
          and parallel_sym_check(reqs,details,details.num_macs,reqs.deci_rate)=0 ) then
        details.symmetry        :=0;
        details.odd_symmetry    :=0;
        details.neg_symmetry    :=0;
        details.num_taps_calced :=reqs.num_taps;
        details.num_taps_per_phase := details.num_taps_calced/reqs.deci_rate;
        if ( details.num_taps_calced rem reqs.deci_rate > 0) then
          details.num_taps_per_phase := details.num_taps_per_phase+1;
        end if;

        details.num_taps_per_mac := details.clk_per_chan;

        details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;
        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs := details.num_macs+1;
        end if;
      end if;
      if (reqs.tap_balance=1) then
        details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;
        if (details.num_taps_per_phase rem details.num_macs >0) then
          details.clk_per_chan:=details.clk_per_chan+1;
        end if;
        details.num_taps_per_mac := details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac * reqs.deci_rate;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac * reqs.deci_rate;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac * reqs.deci_rate;

      details.inter_rate  := 1;
      details.deci_rate   := reqs.deci_rate;
      details.deci_packed := 1;

      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        details.no_data_mem := 1;
      end if;
  when c_decimating =>
      details.symmetry     := reqs.symmetry;
      details.odd_symmetry := reqs.odd_symmetry;
      details.neg_symmetry := reqs.neg_symmetry;

      details.num_taps_calced := (reqs.num_taps/2**reqs.symmetry);
      if ( reqs.num_taps rem 2**reqs.symmetry >0) then
              details.num_taps_calced := details.num_taps_calced+1;
      end if;
      if (reqs.clk_per_samp rem reqs.num_channels > 0) then
              report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp/reqs.num_channels;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.num_taps_per_mac := details.clk_per_chan*reqs.deci_rate;
      details.num_macs := details.num_taps_calced/details.num_taps_per_mac;
      if ( details.num_taps_calced rem details.num_taps_per_mac > 0) then
        details.num_macs := details.num_macs+1;
      end if;

      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac;

      details.inter_rate  := 1;
      details.deci_rate   := reqs.deci_rate;
      details.deci_packed := 1;

      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        details.no_data_mem := 1;
        details.num_taps_per_mac_dataspace :=0;
      end if;
  when c_polyphase_interpolating =>
      if ( reqs.clk_per_samp rem reqs.num_channels > 0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      if (details.clk_per_chan rem reqs.inter_rate > 0) then
        report "the available clock cycles is not evenly divided by the interpolation rate, this means there will be unused clock cycles" severity note;
      end if;

      report "reqs.inter_rate = " & UTILS_PKG_int_to_string(reqs.inter_rate) severity note;

      details.num_taps_per_mac := (details.clk_per_chan / reqs.inter_rate) * reqs.inter_rate;
      report "details.num_taps_per_mac = " & UTILS_PKG_int_to_string(details.num_taps_per_mac) severity note;

      details.num_macs:= details.num_taps_calced/details.num_taps_per_mac;
      report "details.num_macs = " & UTILS_PKG_int_to_string(details.num_macs) severity note;

      if ( details.num_taps_calced rem details.num_taps_per_mac>0) then
        details.num_macs:=details.num_macs+1;
      end if;


      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac/reqs.inter_rate;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac;
      details.inter_rate  := reqs.inter_rate;
      details.deci_rate   := 1;
      details.deci_packed := 1;
      details.mac_mid_tap:= details.num_macs;
      if (details.num_taps_per_mac / details.inter_rate = 1) then
        if (details.num_macs/=1) then
          details.acum_required := 0;
        end if;
        if (reqs.num_channels = 1) then
          details.no_data_mem := 1;
        end if;
      end if;
  when c_interpolating_symmetry =>
      details.symmetry     := reqs.symmetry;
      details.odd_symmetry := reqs.odd_symmetry;
      details.neg_symmetry := reqs.neg_symmetry;
      details.num_taps_calced := (reqs.num_taps/2**reqs.symmetry);
      if ( reqs.num_taps rem 2**reqs.symmetry >0) then
              details.num_taps_calced := details.num_taps_calced+(reqs.inter_rate/2)+1;
      end if;
      if ( reqs.clk_per_samp rem reqs.num_channels > 0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      if (details.clk_per_chan rem reqs.inter_rate > 0) then
        report "the available clock cycles is not evenly divided by the interpolation rate, this means there will be unused clock cycles" severity note;
      end if;

      report "reqs.inter_rate = " & UTILS_PKG_int_to_string(reqs.inter_rate) severity note;

      details.num_taps_per_mac := (details.clk_per_chan / reqs.inter_rate) * reqs.inter_rate;
      report "details.num_taps_per_mac = " & UTILS_PKG_int_to_string(details.num_taps_per_mac) severity note;

      details.num_macs:= details.num_taps_calced/details.num_taps_per_mac;
      report "details.num_macs = " & UTILS_PKG_int_to_string(details.num_macs) severity note;

      if ( details.num_taps_calced rem details.num_taps_per_mac>0) then
        details.num_macs:=details.num_macs+1;
      end if;
      if (reqs.tap_balance=1) then
        details.clk_per_chan:=details.num_taps_calced/details.num_macs;
        if (details.num_taps_calced rem details.num_macs >0) then
          details.clk_per_chan:=details.clk_per_chan+1;
        end if;
        if ( details.clk_per_chan rem reqs.inter_rate > 0 ) then
          details.clk_per_chan:= ((details.clk_per_chan/reqs.inter_rate)+1) * reqs.inter_rate;
        else
          details.clk_per_chan:= (details.clk_per_chan/reqs.inter_rate) * reqs.inter_rate;
        end if;
        details.num_taps_per_mac :=details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac/reqs.inter_rate;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac;

      details.inter_rate  := reqs.inter_rate;
      details.deci_rate   := 1;
      details.mac_mid_tap:= details.num_macs;

      if (details.num_macs=1) then
        details.single_mac_modifier:=1;
      end if;
      if (details.num_taps_per_mac / details.inter_rate = 1) then

        details.no_data_mem := 1;
      end if;
  when c_hilbert_transform =>

      details.symmetry     := 1;
      details.odd_symmetry := 0;
      details.neg_symmetry := 1;

      details.num_taps_calced := (reqs.num_taps/2)+1;
      if (reqs.clk_per_samp rem reqs.num_channels > 0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.num_taps_per_mac := details.clk_per_chan;
      details.num_taps_per_phase := details.num_taps_calced/2;

      details.num_macs := details.num_taps_per_phase / details.num_taps_per_mac;
      if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
        details.num_macs:=details.num_macs+1;
      end if;

      if (reqs.tap_balance=1) then
        details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;
        if (details.num_taps_per_phase rem details.num_macs >0) then
          details.clk_per_chan:=details.clk_per_chan+1;
        end if;
        details.num_taps_per_mac := details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac*2;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      details.inter_rate  := 1;
      details.deci_rate   := 2;
      details.deci_packed := 1;
      if (details.clk_per_chan = 1) then
        details.no_data_mem   := 1;
        details.acum_required := 0;
      end if;
      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan=1
          and parallel_sym_check(reqs,details,details.num_macs,details.deci_rate)=0
          and reqs.c_has_nd=1) then
        details.symmetry     := 0;
        details.odd_symmetry := 0;
        details.neg_symmetry := 0;

        details.num_taps_calced := reqs.num_taps;
        details.num_taps_per_phase := details.num_taps_calced/2;
        if ( details.num_taps_calced rem 2 > 0) then
          details.num_taps_per_phase := details.num_taps_per_phase +1;
        end if;

        details.num_macs := details.num_taps_per_phase / details.num_taps_per_mac;

        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs:=details.num_macs+1;
        end if;
        details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
        details.num_taps_per_mac_dataspace := details.num_taps_per_mac*2;

        details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      end if;
  when c_interpolated_transform =>
      if (reqs.clk_per_samp rem reqs.num_channels > 0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;
      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.num_taps_per_mac := details.clk_per_chan;

      details.num_taps_per_phase := details.num_taps_calced;

      details.num_macs := details.num_taps_per_phase / details.num_taps_per_mac;
      if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
        details.num_macs:=details.num_macs+1;
      end if;

      if (reqs.tap_balance=1) then
        details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;
        if (details.num_taps_per_phase rem details.num_macs >0) then
          details.clk_per_chan:=details.clk_per_chan+1;
        end if;
        details.num_taps_per_mac := details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac * reqs.zero_packing_factor;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac;
      details.inter_rate  := 1;
      details.deci_rate   := reqs.zero_packing_factor;
      details.deci_packed := 1;
      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        details.acum_required := 0;
        details.no_data_mem   := 1;
      end if;

      if (details.clk_per_chan=1
          and details.symmetry=1
          and parallel_sym_check(reqs,details,details.num_macs,reqs.zero_packing_factor)=0
          and reqs.c_has_nd=1) then

        details.symmetry     := 0;
        details.odd_symmetry := 0;
        details.neg_symmetry := 0;

        details.num_taps_calced := reqs.num_taps;
        details.num_taps_per_phase := details.num_taps_calced;

        details.num_macs := details.num_taps_per_phase / details.num_taps_per_mac;
        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs:=details.num_macs+1;
        end if;
        details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
        details.num_taps_per_mac_dataspace := details.num_taps_per_mac * reqs.zero_packing_factor;

        details.num_taps_calced := details.num_macs * details.num_taps_per_mac;
      end if;
  when c_halfband_transform =>
      details.symmetry     := 1;
      details.odd_symmetry := 0;
      details.neg_symmetry := 0;
      details.num_taps_calced := (reqs.num_taps/2)+1;
      if (reqs.clk_per_samp rem reqs.num_channels>0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;
      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.num_taps_per_mac := details.clk_per_chan;
      details.num_taps_per_phase := details.num_taps_calced/2;
      details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;

      if ( details.num_macs=0) then
        details.single_mac_modifier:=0;
      else
        details.single_mac_modifier:=1;
      end if;
      if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
        details.num_macs := details.num_macs+1;
      end if;

      if (reqs.tap_balance=1) then
        if (details.single_mac_modifier=0) then
          details.clk_per_chan:=(details.num_taps_per_phase+1);
        else
          details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;

          if (details.num_taps_per_phase rem details.num_macs >0) then
            details.clk_per_chan:=details.clk_per_chan+1;
          end if;
        end if;
        details.num_taps_per_mac := details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac * 2;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      details.inter_rate  := 1;
      details.deci_rate   := 2;
      details.deci_packed := 1;
      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        details.no_data_mem :=1;
      end if;

      if (details.clk_per_chan=1
          and parallel_sym_check(reqs,details,details.num_macs,details.deci_rate)=0
          and reqs.c_has_nd=1) then
        details.symmetry     := 0;
        details.odd_symmetry := 0;
        details.neg_symmetry := 0;

        details.num_taps_calced := reqs.num_taps;

        details.num_taps_per_phase := details.num_taps_calced/2;
        if ( details.num_taps_calced rem 2 > 0) then
          details.num_taps_per_phase := details.num_taps_per_phase +1;
        end if;

        details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;
        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs := details.num_macs+1;
        end if;

        details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      end if;
  when c_decimating_half_band =>
      details.symmetry     := 1;
      details.odd_symmetry := 0;
      details.neg_symmetry := 0;
      details.num_taps_calced := (reqs.num_taps/2)+1;
      if (reqs.clk_per_samp rem reqs.num_channels > 0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels * 2;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;

      clk_per_chan_hlfbnd_calc :=  reqs.clk_per_samp / reqs.num_channels * 2;
      details.num_taps_per_mac := details.clk_per_chan;
      details.num_taps_per_phase := details.num_taps_calced/2;

      details.num_macs:= details.num_taps_per_phase/details.num_taps_per_mac;
      num_macs_hlfbnd_calc:= details.num_taps_per_phase/clk_per_chan_hlfbnd_calc;
      if (num_macs_hlfbnd_calc=0) then
        details.single_mac_modifier:=0;
        details.num_macs:=1;
        details.num_taps_per_mac := clk_per_chan_hlfbnd_calc;
      else
        details.single_mac_modifier:=1;

        if ( details.num_taps_per_phase rem details.num_taps_per_mac>0) then
          details.num_macs:=details.num_macs+1;
        end if;
      end if;
      if (reqs.tap_balance=1 and reqs.num_channels=1) then
        if (details.single_mac_modifier=0) then
          details.clk_per_chan:=(details.num_taps_per_phase+1);
        else
          details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;
          if (details.num_taps_per_phase rem details.num_macs >0) then
            details.clk_per_chan:=details.clk_per_chan+1;
          end if;
        end if;
        details.num_taps_per_mac := details.clk_per_chan;
      end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac;

      details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      details.inter_rate  := 1;
      details.deci_rate   := 1;
      details.deci_packed := 0;
      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        details.no_data_mem := 1;
      end if;

      if (details.clk_per_chan=1
          and parallel_sym_check(reqs,details,details.num_macs,details.deci_rate)=0) then
        details.symmetry     := 0;
        details.odd_symmetry := 0;
        details.neg_symmetry := 0;
        details.num_taps_calced := reqs.num_taps;
        details.num_taps_per_phase := details.num_taps_calced/2;
        if ( details.num_taps_calced rem 2 > 0) then
          details.num_taps_per_phase := details.num_taps_per_phase +1;
        end if;

        details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;
        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs := details.num_macs+1;
        end if;
        details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      end if;
  when c_interpolating_half_band =>
      details.symmetry     := 1;
      details.odd_symmetry := 0;
      details.neg_symmetry := 0;
      details.num_taps_calced := (reqs.num_taps/2)+1;
      if (reqs.clk_per_samp rem reqs.num_channels > 0) then
        report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles can not support continuous operation" severity note;
      end if;

      details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;
      details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;
      clk_per_chan_hlfbnd_calc := reqs.clk_per_samp / reqs.num_channels;
        details.num_taps_per_mac := (details.clk_per_chan / 2) * 2;

        details.num_taps_per_phase := details.num_taps_calced/2;
        details.num_macs := details.num_taps_per_phase / details.num_taps_per_mac;
        num_macs_hlfbnd_calc:= details.num_taps_per_phase/clk_per_chan_hlfbnd_calc;

        if (num_macs_hlfbnd_calc=0) then
          details.single_mac_modifier:=0;

          details.num_taps_per_mac:=clk_per_chan_hlfbnd_calc;
          details.num_macs :=1;
          details.half_band_inter_dly := 2;
        else
          details.single_mac_modifier:=1;
          details.half_band_inter_dly := 1;
          if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
            details.num_macs:=details.num_macs+1;
          end if;
        end if;

        if (reqs.tap_balance=1) then
          if (details.single_mac_modifier=0) then
            details.clk_per_chan:=(details.num_taps_per_phase+1);
          else
            details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;
            if (details.num_taps_per_phase rem details.num_macs >0) then
              details.clk_per_chan:=details.clk_per_chan+1;
            end if;
          end if;

          if (details.single_mac_modifier/=0) then
            if ( details.clk_per_chan rem 2 >0) then
              details.clk_per_chan := ((details.clk_per_chan / 2)+1) * 2;
            else
              details.clk_per_chan := (details.clk_per_chan / 2) * 2;
            end if;
          end if;
          details.num_taps_per_mac := details.clk_per_chan;
        end if;
      details.num_taps_per_mac_coefspace := details.num_taps_per_mac;
      details.num_taps_per_mac_dataspace := details.num_taps_per_mac;
      details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;

      details.inter_rate  := 1;
      details.deci_rate   := 1;
      details.deci_packed := 0;
      details.mac_mid_tap:= details.num_macs;
      if (details.clk_per_chan = 1) then
        details.no_data_mem := 1;
      end if;

      if (details.clk_per_chan=1
          and parallel_sym_check(reqs,details,details.num_macs,details.deci_rate)=0) then
        details.symmetry     := 0;
        details.odd_symmetry := 0;
        details.neg_symmetry := 0;

        details.num_taps_calced := reqs.num_taps;

        details.num_taps_per_phase := details.num_taps_calced/2;
        if ( details.num_taps_calced rem 2 > 0) then
          details.num_taps_per_phase := details.num_taps_per_phase +1;
        end if;

        details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;
        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs := details.num_macs+1;
        end if;

        details.num_taps_calced := details.num_macs * details.num_taps_per_mac * 2;
      end if;

  when c_polyphase_pq =>
      p_val:=reqs.inter_rate/get_gcd(reqs.inter_rate,reqs.deci_rate);
      q_val:=reqs.deci_rate/get_gcd(reqs.inter_rate,reqs.deci_rate);

      details.symmetry     := 0;
      details.odd_symmetry := 0;
      details.neg_symmetry := 0;
      if (p_val > q_val) then
        if ( reqs.clk_per_samp rem reqs.num_channels > 0) then
          report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles" severity note;
        end if;

        details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;

        details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;

        if (details.clk_per_chan rem p_val > 0) then
          report "the available clock cycles is not evenly divided by the interpolation rate, this means there will be unused clock cycles" severity note;
        end if;

        details.num_taps_per_mac := (details.clk_per_chan / p_val) * q_val;
        details.num_taps_per_phase:=details.num_taps_calced/p_val;
        if (details.num_taps_calced rem p_val > 0) then
          details.num_taps_per_phase:=details.num_taps_per_phase+1;
        end if;
        details.num_macs:= details.num_taps_per_phase/details.num_taps_per_mac;

        if ( details.num_taps_per_phase rem details.num_taps_per_mac>0) then
          details.num_macs:=details.num_macs+1;
        end if;

        details.num_taps_per_mac_coefspace := ((details.num_taps_per_mac)*p_val);
        details.num_taps_per_mac_dataspace := details.num_taps_per_mac;

        details.num_taps_calced := details.num_macs * details.num_taps_per_mac * p_val;

        details.inter_rate  := p_val;
        details.deci_rate  := q_val;

        details.mac_mid_tap:= details.num_macs;

        if (details.num_taps_per_mac = 1) then
          if (details.num_macs/=1) then
            details.acum_required := 0;
          end if;

          if (reqs.num_channels = 1) then
            details.no_data_mem := 1;
          end if;
        end if;

      else

        if ( reqs.clk_per_samp rem reqs.num_channels > 0) then
          report "number of channels does not fit fully into clock cycles per sample, there will be unused clock cycles" severity note;
        end if;

        details.clk_per_chan := reqs.clk_per_samp / reqs.num_channels;

        details.addr_gen_ip_cyc:=reqs.clk_per_samp / reqs.num_channels;

        details.num_taps_per_phase := details.num_taps_calced/q_val;
        if ( details.num_taps_calced rem q_val >0) then
          details.num_taps_per_phase := details.num_taps_per_phase+1;
        end if;
        details.num_taps_per_mac := (details.clk_per_chan / p_val) * p_val;
        details.num_macs := details.num_taps_per_phase/details.num_taps_per_mac;
        if ( details.num_taps_per_phase rem details.num_taps_per_mac > 0) then
          details.num_macs := details.num_macs+1;
        end if;
        if (reqs.tap_balance=1) then
          details.clk_per_chan:=details.num_taps_per_phase/details.num_macs;
          if (details.num_taps_per_phase rem details.num_macs >0) then
            details.clk_per_chan:=details.clk_per_chan+1;
          end if;

          if ( details.clk_per_chan rem p_val > 0 ) then
            details.clk_per_chan:= ((details.clk_per_chan/p_val)+1) * p_val;
          else
            details.clk_per_chan:= (details.clk_per_chan/p_val) * p_val;
          end if;

          details.num_taps_per_mac :=details.clk_per_chan;
        end if;
        details.num_taps_per_mac_coefspace := details.num_taps_per_mac * q_val;
        details.num_taps_per_mac_dataspace := details.num_taps_per_mac * q_val;

        details.num_taps_calced := details.num_macs * details.num_taps_per_mac * q_val;
        details.inter_rate  := p_val;
        details.deci_rate  := q_val;

        details.mac_mid_tap:= details.num_macs;
        if (details.clk_per_chan/p_val = 1) then
          details.no_data_mem := 1;
        end if;
      end if;
  when others =>
      report "invalid filter type specified" severity error;
  end case;

  report "num_taps_per_mac_coefspace: "&int_to_str(details.num_taps_per_mac_coefspace);
  report "num_taps_per_mac_dataspace: "&int_to_str(details.num_taps_per_mac_dataspace);
  details.data_mem_depth := (details.num_taps_per_mac_dataspace * reqs.num_channels);
  if (reqs.data_mem_type=0) then
    if (details.data_mem_depth > srl16_mem_depth_thres) then

      details.data_mem_type:=1;
      if (reqs.memory_pack=0) then
        details.data_mem_depth := 2**log2_ext(2**log2_ext(details.num_taps_per_mac_dataspace) * reqs.num_channels);
      end if;
    else
      details.data_mem_type:=0;
      if (reqs.filter_type=c_polyphase_interpolating and reqs.num_channels>1) or (reqs.filter_type=c_decimating)
        or (reqs.filter_type=c_polyphase_pq and details.inter_rate>details.deci_rate  and reqs.num_channels>1) then
        if (details.data_mem_depth <= dram_mem_depth_thres) then
          details.data_mem_type:=2;

          if (reqs.memory_pack=0) then
            details.data_mem_depth := 2**log2_ext(2**log2_ext(details.num_taps_per_mac_dataspace) * reqs.num_channels);
          end if;
        else
          details.data_mem_type:=1;

          if (reqs.memory_pack=0) then
            details.data_mem_depth := 2**log2_ext(2**log2_ext(details.num_taps_per_mac_dataspace) * reqs.num_channels);
          end if;
        end if;
      end if;

      if (reqs.memory_pack=0) then
        details.data_mem_depth := 2**log2_ext(2**log2_ext(details.num_taps_per_mac_dataspace) * reqs.num_channels);
      end if;
    end if;
  elsif (reqs.data_mem_type=1) then
    details.data_mem_type:=0;

    if (reqs.memory_pack=0) then
      details.data_mem_depth := 2**log2_ext(2**log2_ext(details.num_taps_per_mac_dataspace) * reqs.num_channels);
    end if;
    if ( (reqs.filter_type=c_polyphase_interpolating and reqs.num_channels>1) or (reqs.filter_type=c_decimating)
      or (reqs.filter_type=c_polyphase_pq and details.inter_rate>details.deci_rate and reqs.num_channels>1)
      or (details.data_mem_depth > 1024) ) then
      details.data_mem_type:=2;
    end if;
  elsif (reqs.data_mem_type=2) then
    details.data_mem_type:=1;
    if (reqs.memory_pack=0) then
      details.data_mem_depth := 2**log2_ext(2**log2_ext(details.num_taps_per_mac_dataspace) * reqs.num_channels);
    end if;
  end if;
  details.datasym_mem_offset:=0;
  if (details.symmetry=1) then
    details.datasym_mem_depth:=details.data_mem_depth;
    if (details.data_mem_type=1) then
      details.datasym_mem_type:=1;
      if (details.datasym_mem_depth + details.data_mem_depth < v4_bram_depth(reqs.data_width)) then
        report "will fit both data and symmetrical data into 1 ram" severity note;
        details.data_combined := 1;
        details.datasym_mem_depth:=details.datasym_mem_depth+details.data_mem_depth;
        details.datasym_mem_offset:=details.data_mem_depth;
      else
        report "unable to fit data and symmetrical data into 1 ram" severity note;
        details.data_combined := 0;
      end if;
    else
      details.data_combined := 0;

      details.datasym_mem_type:=details.data_mem_type;
    end if;
  else
          details.datasym_mem_depth := 2;
          details.data_combined     := 0;
  end if;
  details.coef_mem_depth:=details.num_taps_per_mac_coefspace*reqs.num_filts;
  details.coef_mem_offset:=0;
  details.data_coef_combined:=0;
  if (reqs.coef_reload=1) then

    details.data_coef_combined:=0;
    details.coef_mem_depth:=2**log2_ext((2**log2_ext(details.num_taps_per_mac_coefspace))*2);
    if (reqs.coef_mem_type=0) then
      if (details.coef_mem_depth> dram_mem_depth_thres) then
        details.coef_mem_type:=1;
      else
        details.coef_mem_type:=2;
      end if;
    elsif (reqs.coef_mem_type=1) then
      details.coef_mem_type:=2;
    elsif (reqs.coef_mem_type=2) then
      details.coef_mem_type:=1;
    end if;
  else
    if (reqs.memory_pack=0) then
      details.coef_mem_depth:=2**log2_ext((2**log2_ext(details.num_taps_per_mac_coefspace))*reqs.num_filts);
    end if;
    if (reqs.coef_mem_type=0) then
      if (details.data_mem_type=1 and details.symmetry=0 and details.no_data_mem=0) then
        if (details.coef_mem_depth+details.data_mem_depth<= v4_bram_depth(
                                                              bmg2v1_bram_prim_width(
                                                                bmg2v1_bram_prim_sel(
                                                                  max(reqs.data_width,reqs.coef_width),
                                                                    details.data_mem_depth)))) then
          report "will fit data and coefficients in one ram" severity note;
          details.coef_mem_type:=1;
          details.data_coef_combined:=1;
          if (reqs.memory_pack=0) then
            details.coef_mem_offset:=max(details.data_mem_depth,details.coef_mem_depth);
            details.coef_mem_depth:=details.coef_mem_depth+max(details.data_mem_depth,details.coef_mem_depth);
          else
            details.coef_mem_offset:=details.data_mem_depth;
            details.coef_mem_depth:=details.coef_mem_depth+details.data_mem_depth;
          end if;
        else

          details.data_coef_combined:=0;

          if ( details.coef_mem_depth> srl16_mem_depth_thres) then
            details.coef_mem_type:=1;
            if (reqs.memory_pack=0) then
              details.coef_mem_depth:=(2**log2_ext(details.num_taps_per_mac_coefspace))*reqs.num_filts;
            end if;
          else
            details.coef_mem_type:=0;
          end if;
        end if;
      else
        details.data_coef_combined:=0;

        if ( details.coef_mem_depth> srl16_mem_depth_thres) then
          details.coef_mem_type:=1;

          if (details.symmetry=0 and details.no_data_mem=0) then

            if (details.coef_mem_depth+details.data_mem_depth<= v4_bram_depth(
                                                              bmg2v1_bram_prim_width(
                                                                bmg2v1_bram_prim_sel(
                                                                  max(reqs.data_width,reqs.coef_width),
                                                                    details.data_mem_depth)))) then
              report "will fit data and coefficients in one ram" severity note;

              details.data_coef_combined:=1;
              if (reqs.memory_pack=0) then
                details.coef_mem_offset:=max(details.data_mem_depth,details.coef_mem_depth);
                details.coef_mem_depth:=details.coef_mem_depth+max(details.data_mem_depth,details.coef_mem_depth);
              else
                details.coef_mem_offset:=details.data_mem_depth;
                details.coef_mem_depth:=details.coef_mem_depth+details.data_mem_depth;
              end if;
              details.data_mem_type:=1;
            end if;
          end if;
        else
          details.coef_mem_type:=0;
        end if;
      end if;
    elsif (reqs.coef_mem_type=1) then
      details.coef_mem_type:=0;
    elsif (reqs.coef_mem_type=2) then

      details.coef_mem_type:=1;

      if (details.data_mem_type=1 or (reqs.data_mem_type=0 and details.data_mem_type=0)) then
        if (details.symmetry=0  and details.no_data_mem=0) then
          if (details.coef_mem_depth+details.data_mem_depth<= v4_bram_depth(
                                                              bmg2v1_bram_prim_width(
                                                                bmg2v1_bram_prim_sel(
                                                                  max(reqs.data_width,reqs.coef_width),
                                                                    details.data_mem_depth)))) then
            report "will fit data and coefficients in one ram" severity note;
            details.data_coef_combined:=1;
            if (reqs.memory_pack=0) then
              details.coef_mem_offset:=max(details.data_mem_depth,details.coef_mem_depth);
              details.coef_mem_depth:=details.coef_mem_depth+max(details.data_mem_depth,details.coef_mem_depth);
            else
              details.coef_mem_offset:=details.data_mem_depth;
              details.coef_mem_depth:=details.coef_mem_depth+details.data_mem_depth;
            end if;

            details.data_mem_type:=1;
          end if;
        end if;
      end if;
    end if;
  end if;

  report "data_mem_depth: "&int_to_str(details.data_mem_depth);
  report "coef_mem_depth: "&int_to_str(details.coef_mem_depth);
  report "coef_mem_offset: "&int_to_str(details.coef_mem_offset);

  details.data_in_latch_req  := 1;
  details.data_out_latch_req := reqs.reg_output;
  if ((details.num_taps_per_mac / details.inter_rate > 1)
     and not(details.clk_per_chan=1 and reqs.filter_type=c_decimating))
     or (details.num_taps_per_mac > 1 and reqs.filter_type=c_polyphase_pq and details.inter_rate>details.deci_rate) then
    if (details.data_mem_type = 1 or details.data_mem_type = 2) then
      details.mac_block_latency:=3;
      details.data_delay := 0;
      if (details.coef_mem_type=1) then
        details.coef_delay := 0;
      else
        details.coef_delay := 1;
      end if;
    else
      if (details.coef_mem_type = 1) then
        details.mac_block_latency := 3;
        details.coef_delay        := 0;
        details.data_delay        := 1;
      else
        details.coef_delay        := 0;
        details.data_delay        := 0;
        details.mac_block_latency := 2;
      end if;
    end if;
  else
     details.coef_delay := 0;
     if (details.coef_mem_depth > 1) then
       if (details.coef_mem_type = 1) then
         details.data_delay := 2;
       else
         details.data_delay := 1;
       end if;
       if (details.use_data_cascade = 1) then
        details.data_delay :=0;
       end if;
     else
       details.data_delay := 0;
     end if;
     details.mac_block_latency := details.data_delay+1;
     if (reqs.filter_type = c_polyphase_interpolating and reqs.num_channels > 1) then
       details.data_delay := 0;
       if (details.coef_mem_type = 1) then
         details.coef_delay := 0;
       else
         details.coef_delay := 1;
       end if;
       details.mac_block_latency := 3;
     end if;
    if (details.use_data_cascade = 1) then
      details.mac_block_latency := details.mac_block_latency-1;
      details.mac_block_latency := details.mac_block_latency-1;
      details.data_in_latch_req  := 0;
      details.data_out_latch_req := 0;
    end if;
  end if;
  if (details.symmetry=1) then
    if (details.coef_delay<details.data_delay) then
      details.data_delay:=details.data_delay-1;
    else
      details.coef_delay        := details.coef_delay+1;
      details.mac_block_latency := details.mac_block_latency+1;
    end if;
  end if;
  if (details.num_macs=1 and details.single_mac_modifier=0) then
    if ( (details.data_mem_type>0 and details.no_data_mem=0) and details.symmetry=1) then
      details.mac_block_latency := 3;
    elsif ( (details.data_mem_type>0 and details.no_data_mem=0) or details.coef_mem_type=1 or (details.symmetry=1 and details.no_data_mem=0)) then
      details.mac_block_latency := 2;
    else
      details.mac_block_latency := 1;
    end if;
    if (reqs.filter_type=c_halfband_transform or
        reqs.filter_type=c_interpolating_half_band or
        reqs.filter_type=c_decimating_half_band) then
      details.mac_block_latency := details.mac_block_latency +2;
      details.coef_delay:=details.coef_delay+1;
    end if;
  end if;
  if (details.coef_delay=0) then
    details.filter_stage_struct.multi_coef_extra_delay:=0;
    details.filter_stage_struct.multi_coef_src:=ds_coef_store_out;
    details.filter_stage_struct.coef_bal_src:=ds_coef_store_out;
  elsif (details.coef_delay=1) then
    details.filter_stage_struct.multi_coef_extra_delay:=1;
    details.filter_stage_struct.multi_coef_src:=ds_coef_store_out;
    details.filter_stage_struct.coef_bal_src:=ds_coef_store_out;
  elsif (details.coef_delay>1) then
    details.filter_stage_struct.multi_coef_extra_delay:=1;
    details.filter_stage_struct.multi_coef_src:=ds_coef_dly;
    details.filter_stage_struct.coef_bal_src:=ds_coef_store_out;
    details.filter_stage_struct.coef_bal_depth:=details.coef_delay-1;
  end if;
  details.filter_stage_struct.data_store_in_src:=ds_data_in;
  details.filter_stage_struct.data_store_we_src:=cs_we_ip;
  details.filter_stage_struct.data_bal_src:=ds_store_out;
  if ( (reqs.filter_type=c_polyphase_interpolating and reqs.num_channels>1)
      and(details.num_taps_per_mac / details.inter_rate = 1)) then
    details.filter_stage_struct.data_buff_src:=ds_data_in;
    details.filter_stage_struct.data_buff_ce_src:=cs_we_ip;
  else
    details.filter_stage_struct.data_buff_src:=ds_store_out;
    details.filter_stage_struct.data_buff_ce_src:=cs_we_reg1;
    if (reqs.filter_type=c_decimating and details.no_data_mem=0) then
        details.filter_stage_struct.data_buff_ce_src:=cs_we_sym_reg2;
    end if;
  end if;
  if (details.use_data_cascade=1) then
    details.filter_stage_struct.data_out_src:=ds_data_casc;
  else
    details.filter_stage_struct.data_out_src:=ds_buffer_out;
  end if;
  details.filter_stage_struct.data_ce_out_src:=cs_we_reg1;
  details.filter_stage_struct.multi_casc_add_en:=cs_high;
  if (details.use_data_cascade=1) then
    details.filter_stage_struct.multi_data_extra_delay:=1;
    details.filter_stage_struct.multi_casc_add_en:=cs_we_reg2;
  else
    if (details.data_delay=0) then
      details.filter_stage_struct.multi_data_extra_delay:=0;
    elsif (details.data_delay>0) then
      details.filter_stage_struct.multi_data_extra_delay:=1;
    end if;
  end if;
  details.filter_stage_struct.multi_ip_ce_src:=cs_high;
  if (details.symmetry=1) then
    details.filter_stage_struct.multi_data_src:=ds_adder_out;
  elsif (details.use_data_cascade=1) then
    details.filter_stage_struct.multi_data_src:=ds_data_in;
    details.filter_stage_struct.multi_ip_ce_src:=cs_we_ip;
  elsif (details.data_delay>1) then
    details.filter_stage_struct.multi_data_src:=ds_data_dly;
    details.filter_stage_struct.data_bal_depth:=details.data_delay-1;
  else
    details.filter_stage_struct.multi_data_src:=ds_store_out;
  end if;
  if (details.symmetry=1 and details.odd_symmetry=1) then
    details.filter_stage_struct.multi_data_laststage_src:=ds_adder_out_blanking;
  else
    details.filter_stage_struct.multi_data_laststage_src:=details.filter_stage_struct.multi_data_src;
  end if;
  if (details.no_data_mem=0) then
    details.data_buffer_depth:=(reqs.num_channels*details.deci_rate)-1;
    if (reqs.filter_type=c_polyphase_pq and details.inter_rate>details.deci_rate) then
      details.data_buffer_depth:=(reqs.num_channels)-1;
    end if;
    if ( (reqs.filter_type=c_polyphase_interpolating and reqs.num_channels>1)
        and(details.num_taps_per_mac / details.inter_rate = 1)) then
      details.data_buffer_depth:=(reqs.num_channels*details.deci_rate)+1;
    end if;
    if (reqs.filter_type=c_decimating) then
      details.data_buffer_depth:=( (reqs.num_channels)*details.deci_rate) - details.deci_rate;
      if (details.deci_rate<=3) then
        if ( reqs.num_channels>1) then
          details.data_buffer_depth:=details.data_buffer_depth- details.deci_rate;
        else
          if (details.deci_rate=3) then
            details.filter_stage_struct.data_buff_ce_src:=cs_cntrl_extra1;
          else
            details.filter_stage_struct.data_buff_ce_src:=cs_we_sym_ip;
          end if;
          details.data_buffer_depth:=details.data_buffer_depth+((reqs.num_channels)*details.deci_rate);
        end if;
      end if;
    end if;
  else
    details.data_buffer_depth:=(reqs.num_channels*details.deci_rate)+1;
  end if;
  details.filter_stage_struct.dout_mid_src:=ds_store_out;
  details.filter_stage_struct.adder_ip_2_src:=ds_data_sym_store_out;
  details.filter_stage_struct.adder_ip_1_src:=ds_store_out;
  if (details.data_mem_type=0) then
    details.filter_stage_struct.adder_blank_src:=cs_we_sym_reg1;
  else
    details.filter_stage_struct.adder_blank_src:=cs_we_sym_reg2;
  end if;
  details.filter_stage_struct.data_sym_buff_depth_multi:=0;
  if (details.symmetry =1) then
    details.filter_stage_struct.data_sym_store_we_src:=cs_we_sym_ip;
    details.filter_stage_struct.data_sym_ce_out_src:=cs_we_sym_reg1;
    details.filter_stage_struct.input_data_sym_src:=1;
    para_struct:=1;
    if (details.datasym_mem_type=0
        or details.no_data_mem=1) then
      if (details.no_data_mem=1 and reqs.filter_type=c_interpolating_symmetry) then
        details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;
        details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
        details.data_sym_buffer_depth:=reqs.num_channels;
        details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;
        details.filter_stage_struct.data_sym_out_src:=ds_data_sym_buffer_out;
        details.last_stage_output_delay:=0;
        details.filter_stage_struct.data_buff_laststage_src:=ds_store_out;
        details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_ip;

        details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_in;
        details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_sym_ip;
      else
        if ((details.num_taps_per_mac=1 and reqs.filter_type/=c_decimating)
          or (details.clk_per_chan=1 and reqs.filter_type=c_decimating)) then



            details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_in;
            details.filter_stage_struct.data_sym_store_we_src:=cs_we_sym_ip;
            details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
            details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;
            details.filter_stage_struct.input_data_sym_src:=1;
            details.data_sym_buffer_depth:=0;
            details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_in;
            details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_sym_ip;
            details.filter_stage_struct.data_sym_out_src:=ds_data_sym_in;
            details.filter_stage_struct.data_sym_ce_out_src:=cs_we_sym_reg1;
            details.filter_stage_struct.data_buff_laststage_src:=ds_data_in;
            details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_ip;


            para_struct:=1;
            if (reqs.c_has_nd=0) then
              para_struct:=3;
            end if;

            if (reqs.filter_type=c_decimating)
              or (reqs.filter_type=c_polyphase_decimating) then
              para_struct:=2;
            end if;

            if (para_struct=1) then

              details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate);
              if (details.odd_symmetry=1) then
                details.data_sym_buffer_depth:=0;
              end if;

              details.filter_stage_struct.data_sym_buff_src:=ds_data_in;
              details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_ip;

              details.filter_stage_struct.multi_casc_add_en:=cs_high;
              details.filter_stage_struct.input_data_sym_src:=-1;

              details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;

              details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_in;
              details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_ip;

              details.filter_stage_struct.data_sym_buff_depth_multi:=1;

              if (details.odd_symmetry=1) then
                      details.filter_stage_struct.multi_data_laststage_src:=ds_data_dly;
                      details.filter_stage_struct.data_bal_depth:=1;
              end if;

              details.filter_stage_struct.data_sym_out_src:=ds_data_sym_latch;
            elsif (para_struct=2) then

              details.filter_stage_struct.input_data_sym_src:=-1;

              details.data_sym_buffer_depth:=0;
              details.filter_stage_struct.data_sym_buff_depth_multi:=2;

              details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
              details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_reg1;
              details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;

              details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_in;
              details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_reg1;
              details.filter_stage_struct.data_sym_out_src:=ds_data_sym_latch;


            elsif (para_struct=3) then

              details.filter_stage_struct.input_data_sym_src:=1;
              details.filter_stage_struct.data_sym_buff_depth_multi:=0;
              details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate)-1;
              details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
              details.filter_stage_struct.data_sym_out_src:=ds_data_sym_buffer_out;

              details.filter_stage_struct.data_sym_buff_ce_src:=cs_high;


              details.filter_stage_struct.data_out_src:=ds_buffer_out;
              details.filter_stage_struct.adder_ip_2_src:=ds_data_sym_in;
              details.filter_stage_struct.adder_ip_1_src:=ds_data_in;
              details.filter_stage_struct.data_buff_src:=ds_data_in;

              details.filter_stage_struct.data_buff_ce_src:=cs_high;

              if (details.odd_symmetry=0) then
                details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate);
              else
                details.last_stage_output_delay:=0;
              end if;

            end if;


        elsif (details.num_taps_per_mac=2) then

          details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_in;

          details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
          details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;

          details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate)+((reqs.num_channels*details.deci_rate)-1);

          details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_buffer_out;
          details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_sym_ip;

          details.filter_stage_struct.data_sym_out_src:=ds_data_sym_latch;

        elsif (details.num_taps_per_mac=3) then

          details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_in;
          details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
          details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;

          details.data_sym_buffer_depth:=details.num_taps_per_mac*(reqs.num_channels*details.deci_rate);

          details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_in;
          details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_sym_ip;
          details.filter_stage_struct.data_sym_out_src:=ds_data_sym_buffer_out;

        else
          details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;

          details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
          details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;

          details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate)-1;

          details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_store_out;
          details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_reg2;

          details.filter_stage_struct.data_sym_out_src:=ds_data_sym_latch;

        end if;

        details.filter_stage_struct.data_buff_laststage_src:=ds_store_out;
        details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_ip;

        if (para_struct/=3) then
          if (details.odd_symmetry=1) then

            details.last_stage_output_delay:=((reqs.num_channels*details.deci_rate))-1;

            if (details.num_taps_per_mac=1) then
              details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate)-1;

            end if;
          else
            details.last_stage_output_delay:=(2*(reqs.num_channels*details.deci_rate))-1;
            if (details.num_taps_per_mac=1) then
              details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate);

            end if;
          end if;
        end if;

      end if;
    else
      if (details.num_taps_per_mac/details.inter_rate>4) then
        details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;
        details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
        details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;
        details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate);
        details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_store_out;
        details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_reg2;
        details.filter_stage_struct.data_sym_out_src:=ds_data_sym_latch;
      elsif (details.num_taps_per_mac/details.inter_rate>2) then
        details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;
        details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
        details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;
        details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate)-1;
        details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_store_out;
        details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_reg2;
        details.filter_stage_struct.data_sym_out_src:=ds_data_sym_latch;
      elsif (details.num_taps_per_mac/details.inter_rate=2) then
        details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_in;
        details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
        details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;
        details.data_sym_buffer_depth:=(reqs.num_channels*details.deci_rate)+((reqs.num_channels*details.deci_rate));
        details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_buffer_out;
        details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_sym_ip;
        details.filter_stage_struct.data_sym_out_src:=ds_data_sym_buffer_out;
      end if;
      if (details.odd_symmetry=1) then
        details.filter_stage_struct.data_buff_laststage_src:=ds_store_out;
        details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_reg1;
        details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate);
        if (details.num_taps_per_mac=2) then
          details.filter_stage_struct.data_sym_latch_src:=ds_data_in;
          details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_ip;
          details.filter_stage_struct.data_buff_laststage_src:=ds_data_sym_latch;
          details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_ip;
          details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate)*(details.num_taps_per_mac-1);
        end if;
      else
        details.filter_stage_struct.data_buff_laststage_src:=ds_store_out;
        details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_reg2;
        details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate);
        if (details.num_taps_per_mac=3) then
          details.filter_stage_struct.data_buff_laststage_src:=ds_store_out;
          details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_reg1;
          details.last_stage_output_delay:=(2*(reqs.num_channels*details.deci_rate));
        elsif (details.num_taps_per_mac=2) then
          details.filter_stage_struct.data_sym_latch_src:=ds_data_in;
          details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_ip;
          details.filter_stage_struct.data_buff_laststage_src:=ds_data_sym_latch;
          details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_sym_ip;
          details.last_stage_output_delay:=(reqs.num_channels*details.deci_rate)*details.num_taps_per_mac;
        end if;
      end if;
    end if;

    if (reqs.filter_type=c_decimating and details.clk_per_chan>1) then

      details.filter_stage_struct.data_sym_store_we_src:=cs_we_sym_ip;
      details.filter_stage_struct.data_sym_ce_out_src:=cs_we_sym_reg1;
      details.filter_stage_struct.input_data_sym_src:=1;
      details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_buffer_out;
      details.filter_stage_struct.data_sym_out_src:=ds_data_sym_store_out;
      details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;

      details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_reg3;
      details.filter_stage_struct.data_sym_buff_re_src:=cs_cntrl_extra1;
      details.data_sym_buffer_depth:=((reqs.num_channels)*details.deci_rate);
      if (details.clk_per_chan=3 and details.deci_rate=2) then
        if (reqs.num_channels>1) then
          details.data_sym_buffer_depth:=((reqs.num_channels)*details.deci_rate)-details.deci_rate;
        else
          details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_reg1;
        end if;
      end if;
      if (details.clk_per_chan=2 and details.deci_rate<=5) then
        details.data_sym_buffer_depth:=((reqs.num_channels-1)*details.deci_rate);
      end if;
      if (details.odd_symmetry=1) then

        details.filter_stage_struct.data_sym_buff_re_src:=cs_cntrl_extra2;
      end if;

    end if;
    if (reqs.filter_type=c_polyphase_decimating and details.num_taps_per_mac>1) then
      details.last_stage_output_delay:=0;
        details.filter_stage_struct.data_buff_laststage_src:=ds_store_out;
    end if;
  else
    details.filter_stage_struct.data_sym_store_in_src:=ds_data_sym_in;
    details.filter_stage_struct.data_sym_store_we_src:=cs_we_sym_ip;
    details.filter_stage_struct.data_sym_buff_src:=ds_data_sym_in;
    details.filter_stage_struct.data_sym_buff_ce_src:=cs_we_sym_ip;
    details.filter_stage_struct.input_data_sym_src:=1;
    details.data_sym_buffer_depth:=0;
    details.filter_stage_struct.data_sym_latch_src:=ds_data_sym_in;
    details.filter_stage_struct.data_sym_latch_ce_src:=cs_we_sym_ip;
    details.filter_stage_struct.data_sym_out_src:=ds_data_sym_in;
    details.filter_stage_struct.data_sym_ce_out_src:=cs_we_sym_reg1;
    details.filter_stage_struct.data_buff_laststage_src:=ds_data_in;
    details.filter_stage_struct.data_buff_laststage_ce_src:=cs_we_ip;
  end if;
  if (  (reqs.filter_type=c_decimating
        or reqs.filter_type=c_polyphase_decimating)
      and details.symmetry=1
      and details.clk_per_chan=1 ) then

    details.flip_buff_para_extra_delay:=0;

    if (reqs.data_mem_type=0 and (2*(2**log2_ext(reqs.num_channels*details.deci_rate))>dram_mem_depth_thres))
      or (reqs.data_mem_type=2) then
        details.flip_buff_para_extra_delay:=1;
    end if;
  else
    details.flip_buff_para_extra_delay:=0;
  end if;


  return details;
end;
function parallel_sym_check(
           reqs: t_reqs;
           details: t_details;
           num_macs: integer;
           deci_rate: integer)
           return integer is
variable sym_buff_depth: integer;
constant buffer_max_size: integer:=1089;
begin

  if (reqs.filter_type=c_polyphase_decimating) then
    if ( ((num_macs-1)
          * reqs.num_channels
          * deci_rate) > buffer_max_size) then
      return 0;
    else
      return 1;
    end if;
  else
    if (details.odd_symmetry=1) then
      sym_buff_depth:=0;
    else
      sym_buff_depth:=reqs.num_channels*deci_rate;
    end if;
    if ( (sym_buff_depth
          +( (num_macs-1)
            * reqs.num_channels
            * deci_rate
            * 2 ) ) > buffer_max_size) then
      return 0;
    else
      return 1;
    end if;
  end if;
end;
function gen_mif_files (
         elab_dir   : string;
         mif_prefix : string;
         mif_file   : string;
         reqs       : t_reqs;
         details    : t_details )
         return integer is

variable coefficients : t_multi_coefficients(reqs.num_filts-1 downto 0);
type t_mac_coefficients is array (details.num_macs-1 downto 0)
     of t_coefficients(details.coef_mem_depth-1 downto 0);
variable mac_coefficients : t_mac_coefficients;
variable mod_coefficients : t_coefficients (max_num_taps_calced-1 downto 0);
variable half_band_centre_value : t_coefficients(
                                    UTILS_PKG_select_integer(reqs.num_filts,
                                                             2,
                                                             reqs.coef_reload=1)-1 downto 0);
variable local_req_num_taps,
         tap_difference,
         mac_mem_pos,
         tap_pos,
         string_range,
         write_res,
         local_deci_rate,
         phase,
         phase_loop_dir,
         phase_loop_end,
         tap,
         phase_offset,
         temp_diff,
         mod_phase,
         pq_deci_start_phase,
         pq_deci_phase
         : integer;
begin
  if (get_number_of_inputs(mif_file)<reqs.num_taps*reqs.num_filts) then

    report "*** gen_mif_files ***" severity note;
    report "input mif file has incorrect number of values" severity note;
    report UTILS_PKG_int_to_string(get_number_of_inputs(mif_file) ) severity note;
    report UTILS_PKG_int_to_string(reqs.num_taps*reqs.num_filts ) severity note;

  else

    report "*** gen_mif_files ***" severity note;
    report "MIF_FILE : " & mif_file severity note;
    report "TAPS     : " & UTILS_PKG_int_to_string(reqs.num_taps) severity note;
    report "FILTS    : " & UTILS_PKG_int_to_string(reqs.num_filts ) severity note;
    for filter in 0 to reqs.num_filts-1 loop
      coefficients(filter)(reqs.num_taps-1 downto 0) :=
                     read_coef_data( mif_file,
                                     reqs.num_taps,
                                     reqs.coef_width,
                                     filter*reqs.num_taps);
      report "Filter - " & UTILS_PKG_int_to_string(filter) severity note;
    end loop;
    tap_difference:=0;
    if (details.symmetry=1) then
      local_req_num_taps:=reqs.num_taps/2;
      if ( reqs.num_taps rem 2 > 0 ) then
        local_req_num_taps:=local_req_num_taps+1;
      end if;
      tap_difference:=details.num_taps_calced-local_req_num_taps;
      if (reqs.filter_type=c_interpolating_symmetry and details.odd_symmetry=1) then
        temp_diff:=details.inter_rate/2;

        tap_difference:=tap_difference-temp_diff;
      end if;
      if (details.num_macs=1 and details.single_mac_modifier=0 and
         (reqs.filter_type=c_halfband_transform  or
         reqs.filter_type=c_interpolating_half_band or
         reqs.filter_type=c_decimating_half_band)) then
         tap_difference:=tap_difference-2;
      end if;

      for filter in 0 to reqs.num_filts-1 loop
        for get_taps in 0 to details.num_taps_calced-1 loop
          if get_taps>=tap_difference then

            mod_coefficients(get_taps):=coefficients(filter)(get_taps-tap_difference);
            if ( (get_taps=details.num_taps_calced-2)
                 and details.num_macs=1 and details.single_mac_modifier=0 and
                 (reqs.filter_type=c_halfband_transform or
                 reqs.filter_type=c_interpolating_half_band or
                 reqs.filter_type=c_decimating_half_band)) then
               mod_coefficients(get_taps):=coefficients(filter)(get_taps-tap_difference-1);
            end if;
          else
            mod_coefficients(get_taps) := (others=>'0');
          end if;

        end loop;

        coefficients(filter) := mod_coefficients;
      end loop;
    else

      report "reqs.num_taps = " & UTILS_PKG_int_to_string(reqs.num_taps)
        severity note;

      local_req_num_taps:=reqs.num_taps;
      for filter in 0 to reqs.num_filts-1 loop
        report "local_req_num_taps = " & UTILS_PKG_int_to_string(local_req_num_taps)
          severity note;
        report "details.num_taps_calced = " & UTILS_PKG_int_to_string(details.num_taps_calced)
          severity note;
        if (details.num_taps_calced-1>local_req_num_taps) then
          for pad in local_req_num_taps to details.num_taps_calced-1 loop
            coefficients(filter)(pad):=(others=>'0');
          end loop;
        end if;
      end loop;
    end if;

    if (reqs.filter_type=c_interpolating_symmetry) then
      for filter in 0 to reqs.num_filts-1 loop
        tap:=0;
        while tap<=details.num_taps_calced-1 loop
          phase_offset:=0;
          if (details.inter_rate rem 2 /=0) then
            mod_coefficients(tap):=coefficients(filter)(tap+((details.inter_rate/2)));
            phase_offset:=1;
          end if;
          phase:=0;
          while phase<((details.inter_rate/2)) loop

            mod_coefficients(tap+(2*phase)+1+phase_offset)(reqs.coef_width-1 downto 0):= std_logic_vector(signed(coefficients(filter)(tap+phase)(reqs.coef_width-1 downto 0))
                                                                        +signed(coefficients(filter)( (details.inter_rate+tap-1)-(phase) )(reqs.coef_width-1 downto 0)));
            mod_coefficients(tap+(2*phase)+phase_offset)(reqs.coef_width-1 downto 0):= std_logic_vector(signed(coefficients(filter)(tap+phase)(reqs.coef_width-1 downto 0))
                                                                          -signed(coefficients(filter)( (details.inter_rate+tap-1)-(phase) )(reqs.coef_width-1 downto 0)));
            phase:=phase+1;
          end loop;
          phase:=0;
          while phase<=details.inter_rate-1 loop
            coefficients(filter)(tap+phase):=mod_coefficients(tap+phase);
            phase:=phase+1;
          end loop;

          tap:=tap+details.inter_rate;
        end loop;
      end loop;
    end if;
    for mac in 0 to  details.num_macs-1 loop
       for mac_mem_loc in 0 to details.coef_mem_depth-1 loop
         mac_coefficients(mac)(mac_mem_loc):=(others=>'0');
       end loop;
    end loop;
    if ( reqs.filter_type = c_decimating) then
            local_deci_rate:=1;
    elsif ( reqs.filter_type = c_decimating_half_band
      or reqs.filter_type = c_interpolating_half_band ) then
            local_deci_rate:=2;
    elsif (reqs.filter_type = c_interpolated_transform) then
            local_deci_rate:=1;
    else
            local_deci_rate:=details.deci_rate;
    end if;
    for mac in 0 to  details.num_macs-1 loop
      mac_mem_pos:=0;
      if (details.data_coef_combined=1) then
              mac_mem_pos:=details.coef_mem_offset;
      end if;
      for filters in 0 to reqs.num_filts-1 loop
        phase:=0;
        mod_phase:=0;
        phase_loop_end := max(local_deci_rate,details.inter_rate)-1;
        phase_loop_dir := 1;

        if (reqs.filter_type = c_polyphase_decimating)
          or (reqs.filter_type = c_polyphase_pq and details.inter_rate<details.deci_rate) then
          phase := max(local_deci_rate,details.inter_rate)-1;
          mod_phase:= max(local_deci_rate,details.inter_rate)-1;
          pq_deci_start_phase:=details.inter_rate-1;
          phase_loop_end := 0;
          phase_loop_dir := -1;
        end if;
        while  not(phase=phase_loop_end+phase_loop_dir) loop
          if not ( reqs.filter_type = c_hilbert_transform
                or reqs.filter_type = c_interpolated_transform
                or reqs.filter_type = c_halfband_transform
                or reqs.filter_type = c_decimating_half_band
                or reqs.filter_type = c_interpolating_half_band ) then
            if (reqs.filter_type = c_polyphase_pq and details.inter_rate<details.deci_rate) then

              pq_deci_phase:=pq_deci_start_phase;
              for pq_reorder in 0 to details.inter_rate-1 loop


                tap_pos := (mac*details.num_taps_per_mac_coefspace)+(pq_deci_phase*details.deci_rate)+mod_phase;

                while tap_pos<((mac+1)*details.num_taps_per_mac_coefspace) loop
                  mac_coefficients(mac)(mac_mem_pos) := coefficients(filters)(tap_pos);
                  mac_mem_pos := mac_mem_pos+1;

                  tap_pos := tap_pos+ (details.inter_rate*details.deci_rate);
                end loop;

                pq_deci_phase:=(pq_deci_phase-1) mod details.inter_rate;
              end loop;
            else
              if (reqs.filter_type = c_polyphase_pq) then
                tap_pos := (mac*details.num_taps_per_mac_coefspace)+mod_phase;
              else
                tap_pos := (mac*details.num_taps_per_mac_coefspace)+phase;
              end if;

              while tap_pos<((mac+1)*details.num_taps_per_mac_coefspace) loop

                mac_coefficients(mac)(mac_mem_pos) := coefficients(filters)(tap_pos);

                mac_mem_pos := mac_mem_pos+1;

                tap_pos := tap_pos+max(local_deci_rate,details.inter_rate);
              end loop;

            end if;
          else
            tap_pos:=(mac*details.num_taps_per_mac_coefspace*local_deci_rate)+phase;
            while ((tap_pos<((mac+1)*details.num_taps_per_mac_coefspace*local_deci_rate))and phase=0) loop

              mac_coefficients(mac)(mac_mem_pos):=coefficients(filters)(tap_pos);

              mac_mem_pos:=mac_mem_pos+1;
              tap_pos := tap_pos+local_deci_rate;
            end loop;
          end if;
          phase := phase+phase_loop_dir;

          if (details.inter_rate>details.deci_rate) then
            mod_phase :=(mod_phase+ details.deci_rate) mod details.inter_rate;
          else
            if (mod_phase-details.inter_rate<0) then
              pq_deci_start_phase:=pq_deci_start_phase-1;
            end if;
            mod_phase :=(mod_phase- details.inter_rate) mod details.deci_rate;
          end if;
        end loop;
        if ( reqs.filter_type = c_halfband_transform
          or reqs.filter_type = c_decimating_half_band
          or reqs.filter_type = c_interpolating_half_band ) then
          if ( details.num_macs = details.mac_mid_tap ) then
            half_band_centre_value(filters) :=
                    coefficients(filters)(tap_difference+local_req_num_taps-1);
          else
            half_band_centre_value(filters) :=
                    coefficients(filters)( (reqs.num_taps/2) );
          end if;
        end if;
        if (reqs.memory_pack=0) then
          mac_mem_pos:=((2**log2_ext(details.num_taps_per_mac_coefspace))*(filters+1))+details.coef_mem_offset;
        end if;
      end loop;
    end loop;
    for mac in 0 to  details.num_macs-1 loop

      if (mac<10) then
        string_range:=15;
      else
        string_range:=16;
      end if;

      if (  details.data_coef_combined=1
          and reqs.data_width > reqs.coef_width
          and reqs.coef_sign = 0 ) then
        for tap in 0 to details.num_taps_per_mac loop
          mac_coefficients(mac)(tap)(reqs.data_width-1 downto 0):=
                              ext_bus(mac_coefficients(mac)(tap)(reqs.coef_width-1 downto 0),reqs.data_width,reqs.coef_sign);
        end loop;
      end if;
      write_res := write_coef_data( elab_dir
                                  & mif_prefix
                                  & "COEFF_auto"
                                  & int_to_str(mac)
                                  & ".mif",
                                    details.coef_mem_depth,
                                    UTILS_PKG_select_integer( reqs.coef_width,
                                                              max(reqs.coef_width,reqs.data_width),
                                                              details.data_coef_combined=1),
                                    mac_coefficients(mac)                  );
    end loop;

    if ( reqs.filter_type = c_halfband_transform
      or reqs.filter_type = c_decimating_half_band
      or reqs.filter_type = c_interpolating_half_band) then
      write_res := write_coef_data( elab_dir
                                  & mif_prefix
                                  & "COEFF_auto_HALFBAND_CENTRE.mif",
                                    UTILS_PKG_select_integer(reqs.num_filts,
                                                             2,
                                                             reqs.coef_reload=1),
                                    reqs.coef_width,
                                    half_band_centre_value);
    end if;
  end if;
  return 1;
end;
function convert_bin_mif_to_hex (
         elab_dir   : string;
         mif_file   : string;
         num_taps   : integer;
         coef_width : integer;
         num_filts  : integer )
         return integer is

variable coefficients : t_coefficients(num_filts*num_taps-1 downto 0);
variable write_res : integer;
constant elab_mif_file : string := elab_dir & mif_file;
begin
  if (get_number_of_inputs(elab_mif_file) < num_taps*num_filts) then

    report "input binary mif file has incorrect number of values" severity note;

  else

    coefficients(num_filts*num_taps-1 downto 0) :=
                     read_coef_data_bin( elab_mif_file,
                                         num_taps*num_filts,
                                         coef_width,
                                         0);
    write_res := write_coef_data_hex( elab_dir
                                    & "hex_"
                                    & mif_file,
                                      num_taps*num_filts,
                                      coef_width,
                                      coefficients         );
  end if;
  return 1;
end;
function read_coef_data ( filename         : string;
                          number_of_values : integer;
                          coef_width       : integer;
                          offset           : integer )
                          return t_coefficients is
  variable data        : t_coefficients(number_of_values-1 downto 0);
  file     filepointer : text;
  variable dataline    : line;
  constant width       : integer := (coef_width + 3)/4;
  variable hexstring   : string(1 to width);
  variable lines       : integer := 0;
  variable mif_status  : file_open_status;
begin

  file_open(mif_status,filepointer,filename,read_mode);

  while (not(endfile(filepointer)) and (lines < number_of_values+offset)) loop
    readline(filepointer, dataline);
    read(dataline, hexstring);
    if (lines>(offset-1)) then
      data(lines-offset)(coef_width-1 downto 0) := hex_to_std_logic_vector(hexstring,coef_width);
    end if;
    lines := lines + 1;
  end loop;

  file_close(filepointer);

  return data;
end read_coef_data;
function read_coef_data_bin ( filename         : string;
                              number_of_values : integer;
                              coef_width       : integer;
                              offset           : integer )
                              return t_coefficients is
  variable data        : t_coefficients(number_of_values-1 downto 0);
  file     filepointer : text;
  variable dataline    : line;
  variable binstring   : string(1 to coef_width);
  variable bitval      : std_logic_vector((coef_width-1) downto 0);
  variable lines       : integer := 0;
  variable mif_status  : file_open_status;
begin
  file_open(mif_status,filepointer,filename,read_mode);

  while (not(endfile(filepointer)) and (lines < number_of_values+offset)) loop
    readline(filepointer, dataline);
    read(dataline, binstring);

    if (lines>(offset-1)) then
      data(lines-offset)(coef_width-1 downto 0) := UTILS_PKG_string_to_slv(binstring,coef_width);
    end if;
    lines := lines + 1;
  end loop;

  file_close(filepointer);

  return data;
end read_coef_data_bin;
function write_coef_data_hex ( filename         : string;
                               number_of_values : integer;
                               coef_width       : integer;
                               coef_data        : t_coefficients )
                               return integer is
  file     filepointer : text;
  variable mif_status  : file_open_status;
  constant hexlength   : integer := (coef_width+3)/4;
  variable hex_string  : string(1 to hexlength);
  variable write_line  : line;
  variable bitchar     : character;
begin
  file_open(mif_status,filepointer,filename,write_mode);
  for loc in 0 to number_of_values-1 loop
    hex_string := std_logic_vector_to_hex(coef_data(loc)(coef_width-1 downto 0));
    write(write_line,hex_string);
    writeline(filepointer,write_line);
  end loop;
  file_close(filepointer);
  return 0;
end write_coef_data_hex;
function write_coef_data ( filename         : string;
                           number_of_values : integer;
                           coef_width       : integer;
                           coef_data        : t_coefficients )
                           return integer is
  file     filepointer: text;
  variable mif_status  : file_open_status;
  variable write_line: line;
  variable write_line_slv: std_logic_vector((coef_width - 1) downto 0);
  variable bitchar: character;
begin
  file_open(mif_status,filepointer,filename,write_mode);
  for loc in 0 to number_of_values-1 loop
    write_line_slv:=std_logic_vector'(coef_data(loc)(coef_width-1 downto 0));
    for bitloop in 0 to coef_width-1 loop
      if (write_line_slv((coef_width-1)-bitloop)='1') then
        bitchar:='1';
      else
        bitchar:='0';
      end if;
      write(write_line,bitchar);
    end loop;
    writeline(filepointer,write_line);
  end loop;
  file_close(filepointer);
  return 0;
end write_coef_data;
function read_mem_mif_file(filename: string; number_of_values: integer; coef_width: integer; offset:integer) return t_coefficients is
variable data       : t_coefficients(number_of_values-1 downto 0);
file     filepointer: text;
variable dataline   : line;
variable width      : integer := (coef_width + 3)/4;
variable binstring  : string(1 to coef_width);
variable read_result:boolean;
variable bitval     : std_logic_vector((coef_width - 1) downto 0);
variable lines      : integer := 0;
variable mif_status  : file_open_status;
begin
        file_open(mif_status,filepointer,filename,read_mode);
        while (not(endfile(filepointer)) and (lines < number_of_values+offset)) loop

                readline(filepointer, dataline);
                read(dataline, binstring,read_result);
                ASSERT read_result
                REPORT "Error: problem reading memory initialization file"
                SEVERITY FAILURE;

                for bitloop in 1 to coef_width loop
                        if (binstring(bitloop)='1') then
                                bitval(coef_width-bitloop):='1';
                        else
                                bitval(coef_width-bitloop):='0';
                        end if;
                end loop;
                if (lines>(offset-1)) then
                        data(lines-offset)(coef_width-1 downto 0) := bitval;
                end if;
                lines := lines + 1;
        end loop;

        file_close(filepointer);

        return data;
end read_mem_mif_file;
function get_number_of_inputs(filename: string) return integer is
file     inpfile    : text;
variable oneline    : line;
variable decvalue   : integer;
variable count      : integer := 0;
variable mif_status  : file_open_status;
begin
        report "*** get_number_of_inputs ***" severity note;
        report "MIF_FILE : " & filename severity note;

        file_open(mif_status,inpfile,filename,read_mode);
        while (not(endfile(inpfile))) loop
                readline(inpfile, oneline);
                count := count + 1;
        end loop;

        file_close(inpfile);
        return count;
end get_number_of_inputs;
function hex_to_std_logic_vector( hexstring: string; width: integer) RETURN std_logic_vector IS
constant length   : integer := hexstring'length;
variable bitval   : std_logic_vector(((length*4) - 1) downto 0);
variable posn     : integer := ((length * 4) - 1);
variable ch       : character;
begin
  bitval := (others => '0');
  for i in 1 to length loop
    ch := hexstring(i);
    case ch is
      when '0'        => bitval(posn downto posn - 3) := "0000";
      when '1'        => bitval(posn downto posn - 3) := "0001";
      when '2'        => bitval(posn downto posn - 3) := "0010";
      when '3'        => bitval(posn downto posn - 3) := "0011";
      when '4'        => bitval(posn downto posn - 3) := "0100";
      when '5'        => bitval(posn downto posn - 3) := "0101";
      when '6'        => bitval(posn downto posn - 3) := "0110";
      when '7'        => bitval(posn downto posn - 3) := "0111";
      when '8'        => bitval(posn downto posn - 3) := "1000";
      when '9'        => bitval(posn downto posn - 3) := "1001";
      when 'A' | 'a'  => bitval(posn downto posn - 3) := "1010";
      when 'B' | 'b'  => bitval(posn downto posn - 3) := "1011";
      when 'C' | 'c'  => bitval(posn downto posn - 3) := "1100";
      when 'D' | 'd'  => bitval(posn downto posn - 3) := "1101";
      when 'E' | 'e'  => bitval(posn downto posn - 3) := "1110";
      when 'F' | 'f'  => bitval(posn downto posn - 3) := "1111";
      when others     => assert false
        report "invalid hex value." severity error;
        bitval(posn downto posn - 3) := "XXXX";
     end case;
     posn := posn - 4;
  end loop;
  return bitval(width-1 downto 0);
end hex_to_std_logic_vector;
function std_logic_vector_to_hex( slv: std_logic_vector ) RETURN string IS
constant hexlength : integer := (slv'length+3)/4;
variable hexval    : string(1 to hexlength);
variable bitval    : std_logic_vector((4*hexlength - 1) downto 0);
variable posn      : integer := 4*hexlength-1;
variable slv4      : std_logic_vector(3 downto 0);
variable nibble    : string(1 to 4);
begin
  bitval := (others => '0');
  bitval(slv'length-1 downto 0) := slv;

  for i in 1 to hexlength loop
    slv4   := bitval(posn downto posn-3);
    nibble := UTILS_PKG_slv_to_string(slv4,4);
    case nibble is
      when "0000" => hexval(i):= '0';
      when "0001" => hexval(i):= '1';
      when "0010" => hexval(i):= '2';
      when "0011" => hexval(i):= '3';
      when "0100" => hexval(i):= '4';
      when "0101" => hexval(i):= '5';
      when "0110" => hexval(i):= '6';
      when "0111" => hexval(i):= '7';
      when "1000" => hexval(i):= '8';
      when "1001" => hexval(i):= '9';
      when "1010" => hexval(i):= 'A';
      when "1011" => hexval(i):= 'B';
      when "1100" => hexval(i):= 'C';
      when "1101" => hexval(i):= 'D';
      when "1110" => hexval(i):= 'E';
      when "1111" => hexval(i):= 'F';
      when others => assert false
        report "invalid binary value." severity error;
        hexval(i) := 'X';
     end case;
     posn := posn-4;
  end loop;
  return hexval;
end std_logic_vector_to_hex;
function v4_bram_prim_sel(width:integer;depth:integer) return string is
variable widthOK: integer;
begin

  widthOK:=width;
  while widthOK>0 loop

    if (v4_bram_depth(widthOK)>=depth) then
      return v4_bram_prim(widthOK);
    else
      widthOK:=widthOK-1;
    end if;
  end loop;

  report "MEMORY PRIMITIVE WARNING: unable to find deep enough memory primative" severity WARNING;
  return v4_bram_prim(1);
end v4_bram_prim_sel;
function bmg2v1_bram_prim_sel(width:integer;depth:integer) return integer is
variable widthOK: integer;
begin

  widthOK:=width;
  while widthOK>0 loop

    if (v4_bram_depth(widthOK)>=depth) then
      return bmg2v1_bram_prim(widthOK);
    else
      widthOK:=widthOK-1;
    end if;
  end loop;

  report "MEMORY PRIMITIVE WARNING: unable to find deep enough memory primative" severity WARNING;
  return bmg2v1_bram_prim(1);
end bmg2v1_bram_prim_sel;
function v4_bram_prim_width(prim:string) return integer is
variable prim_check:string(1 to 6);
begin
  prim_check:=prim;
  case prim_check is
    when " 32kx1" => return 1;
    when "  8kx2" => return 2;
    when "  4kx4" => return 4;
    when "  2kx9" => return 9;
    when " 1kx18" => return 18;
    when "512x36" => return 36;
    when others => return -1;
  end case;
end v4_bram_prim_width;
function bmg2v1_bram_prim_width(prim:integer) return integer is
  variable prim_check:integer;
begin
  prim_check:=prim;
  case prim_check is
    when 0 => return 1;
    when 1 => return 2;
    when 2 => return 4;
    when 3 => return 9;
    when 4 => return 18;
    when 5 => return 36;
    when others => return -1;
  end case;
end bmg2v1_bram_prim_width;
function map_da_type ( type_in  : integer ) return integer is
  variable type_out : integer;
begin
  type_out := type_in;
  if type_in = c_polyphase_interpolating then
    type_out := c_polyphase_decimating;
  elsif type_in = c_polyphase_decimating then
    type_out := c_polyphase_interpolating;
  end if;

  return type_out;

end map_da_type;
function map_da_rate (
         type_in     : integer;
         interp_rate : integer;
         decim_rate  : integer )
         return integer is
  variable rate_out : integer;
begin
  if    ( type_in=c_polyphase_interpolating
       or type_in=c_interpolating_symmetry  ) then
    rate_out := interp_rate;
  elsif ( type_in=c_polyphase_decimating
       or type_in=c_decimating              ) then
    rate_out := decim_rate;
  elsif ( type_in=c_interpolating_half_band
       or type_in=c_decimating_half_band) then
    rate_out := 2;
  else
    rate_out := 1;
  end if;

  return rate_out;

end map_da_rate;
function get_da_baat (
         filt_type    : integer;
         databits     : integer;
         clk_per_samp : integer;
         symmetry     : integer)
         return integer is
  variable bits : integer := 0;
  variable baat : integer := 0;
begin

  if symmetry = 0
  or filt_type = c_polyphase_interpolating
  or filt_type = c_interpolating_half_band  then
    bits := databits;
  else
    bits := databits + 1;
  end if;

  if ( clk_per_samp = 1 ) then
    baat := databits;
  else
    baat := ( bits + (clk_per_samp - 1) ) / clk_per_samp;
  end if;

  return baat;

end get_da_baat;
end mac_fir_func_pkg;
