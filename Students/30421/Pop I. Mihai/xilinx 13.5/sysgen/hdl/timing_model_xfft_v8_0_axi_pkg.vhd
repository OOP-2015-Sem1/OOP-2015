
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
library xilinxcorelib;
use work.timing_model_pkg.all;
package timing_model_xfft_v8_0_axi_pkg is
  constant MAX_NUM_CHANNELS : integer := 12;
  constant MAX_OUTPUT_WIDTH : integer := 51;
  constant BLK_EXP_WIDTH    : integer := 5;
  type T_XK_RE_CHANNEL_ARRAY is array (MAX_NUM_CHANNELS-1 downto 0) of std_logic_vector(MAX_OUTPUT_WIDTH -1 downto 0);
  type T_XK_IM_CHANNEL_ARRAY is array (MAX_NUM_CHANNELS-1 downto 0) of std_logic_vector(MAX_OUTPUT_WIDTH -1 downto 0);
  type T_BLK_EXP_CHANNEL_ARRAY is array (MAX_NUM_CHANNELS-1 downto 0) of std_logic_vector(BLK_EXP_WIDTH -1 downto 0);
  type T_OVFLO_CHANNEL_ARRAY   is array (MAX_NUM_CHANNELS-1 downto 0) of std_logic;
  function has_nfft      ( constant C_HAS_NFFT : integer
                         ) return integer;
  function has_cp_len    ( constant C_HAS_CYCLIC_PREFIX  : in integer;
                           constant C_HAS_NATURAL_OUTPUT : in integer
                         ) return integer;
  function has_scale_sch ( constant C_HAS_SCALING  : in integer;
                           constant C_HAS_BFP      : in integer
                         ) return integer;
  function has_ovflo     ( constant C_HAS_OVFLO   : integer;
                           constant C_HAS_SCALING : integer;
                           constant C_HAS_BFP     : integer;
                           constant C_USE_FLT_PT  : integer
                         ) return integer;
  function has_blk_exp  ( constant C_HAS_SCALING : integer;
                          constant C_HAS_BFP     : integer
                        ) return integer;
  function is_streaming  ( constant C_ARCH : integer
                         ) return integer;
  function is_rdx2_lite  ( constant C_ARCH : integer
                         ) return integer;
  function how_much_padding(field_width : integer) return integer;
  function calculate_config_width_no_padding(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING, C_NFFT_MAX, C_HAS_BFP, C_ARCH : integer) return integer;
  procedure split_config_tdata (constant tdata                : in STD_LOGIC_VECTOR;
                                signal   nfft                 : out std_logic_vector;
                                signal   cp_len               : out std_logic_vector;
                                signal   fwd_inv0             : out  std_logic;
                                signal   fwd_inv1             : out  std_logic;
                                signal   fwd_inv2             : out  std_logic;
                                signal   fwd_inv3             : out  std_logic;
                                signal   fwd_inv4             : out  std_logic;
                                signal   fwd_inv5             : out  std_logic;
                                signal   fwd_inv6             : out  std_logic;
                                signal   fwd_inv7             : out  std_logic;
                                signal   fwd_inv8             : out  std_logic;
                                signal   fwd_inv9             : out  std_logic;
                                signal   fwd_inv10            : out  std_logic;
                                signal   fwd_inv11            : out  std_logic;
                                signal   scale_sch0           : out  std_logic_vector;
                                signal   scale_sch1           : out  std_logic_vector;
                                signal   scale_sch2           : out  std_logic_vector;
                                signal   scale_sch3           : out  std_logic_vector;
                                signal   scale_sch4           : out  std_logic_vector;
                                signal   scale_sch5           : out  std_logic_vector;
                                signal   scale_sch6           : out  std_logic_vector;
                                signal   scale_sch7           : out  std_logic_vector;
                                signal   scale_sch8           : out  std_logic_vector;
                                signal   scale_sch9           : out  std_logic_vector;
                                signal   scale_sch10          : out  std_logic_vector;
                                signal   scale_sch11          : out  std_logic_vector;
                                constant C_HAS_NFFT           : in  integer;
                                constant C_HAS_CYCLIC_PREFIX  : in  integer;
                                constant C_HAS_NATURAL_OUTPUT : in  integer;
                                constant C_CHANNELS           : in  integer;
                                constant C_HAS_SCALING        : in  integer;
                                constant C_NFFT_MAX           : in  integer;
                                constant C_HAS_BFP            : in  integer;
                                constant C_ARCH               : in  integer;
                                constant C_CONTAINS_PADDING   : in integer
                                );
  function remove_config_data_padding(padded_data: STD_LOGIC_VECTOR; C_HAS_NFFT,    C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS,
                                                                     C_HAS_SCALING, C_NFFT_MAX,          C_HAS_BFP,            C_ARCH : integer) return STD_LOGIC_VECTOR;
  function get_config_default_value(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING,
                                    C_NFFT_MAX, C_HAS_BFP, C_ARCH : integer) return std_logic_vector;
  function calculate_data_in_width_no_padding(C_INPUT_WIDTH, C_CHANNELS : integer) return integer;
  procedure convert_data_in_vector_to_fft_in (constant fifo_vector          : in  std_logic_vector;
                                              signal   tlast                : out std_logic;
                                              variable XN_RE                : out std_logic_vector;
                                              variable XN_IM                : out std_logic_vector;
                                              variable XN0_RE               : out std_logic_vector;
                                              variable XN0_IM               : out std_logic_vector;
                                              variable XN1_RE               : out std_logic_vector;
                                              variable XN1_IM               : out std_logic_vector;
                                              variable XN2_RE               : out std_logic_vector;
                                              variable XN2_IM               : out std_logic_vector;
                                              variable XN3_RE               : out std_logic_vector;
                                              variable XN3_IM               : out std_logic_vector;
                                              variable XN4_RE               : out std_logic_vector;
                                              variable XN4_IM               : out std_logic_vector;
                                              variable XN5_RE               : out std_logic_vector;
                                              variable XN5_IM               : out std_logic_vector;
                                              variable XN6_RE               : out std_logic_vector;
                                              variable XN6_IM               : out std_logic_vector;
                                              variable XN7_RE               : out std_logic_vector;
                                              variable XN7_IM               : out std_logic_vector;
                                              variable XN8_RE               : out std_logic_vector;
                                              variable XN8_IM               : out std_logic_vector;
                                              variable XN9_RE               : out std_logic_vector;
                                              variable XN9_IM               : out std_logic_vector;
                                              variable XN10_RE              : out std_logic_vector;
                                              variable XN10_IM              : out std_logic_vector;
                                              variable XN11_RE              : out std_logic_vector;
                                              variable XN11_IM              : out std_logic_vector;
                                              constant C_INPUT_WIDTH        : in  integer;
                                              constant C_CHANNELS           : in  integer;
                                              constant C_CONTAINS_PADDING   : in  integer
                                              );
  function convert_axi_to_data_in_vector(tdata         : STD_LOGIC_VECTOR;
                                         tlast         : STD_LOGIC;
                                         C_INPUT_WIDTH : integer;
                                         C_CHANNELS    : integer)  return STD_LOGIC_VECTOR;
  function calculate_data_out_width_no_padding(constant C_OUTPUT_WIDTH: in integer;
                                               constant C_CHANNELS    : in integer;
                                               constant C_NFFT_MAX    : in integer;
                                               constant C_HAS_OVFLO   : in integer;
                                               constant C_HAS_SCALING : in integer;
                                               constant C_HAS_BFP     : in integer;
                                               constant C_HAS_XK_INDEX: in integer;
                                               constant C_USE_FLT_PT  : in integer) return integer;
  function get_start_of_data_channel_ovflo(constant C_NFFT_MAX     : in integer;
                                           constant C_HAS_XK_INDEX : in integer
                                          ) return integer;
  function convert_fft_out_to_data_out_vector(constant tlast          : in std_logic;
                                              constant xk_re          : in T_XK_RE_CHANNEL_ARRAY;
                                              constant xk_im          : in T_XK_RE_CHANNEL_ARRAY;
                                              constant xk_index       : in std_logic_vector;
                                              constant blk_exp        : in T_BLK_EXP_CHANNEL_ARRAY;
                                              constant ovflo          : in T_OVFLO_CHANNEL_ARRAY;
                                              constant C_OUTPUT_WIDTH : in integer;
                                              constant C_CHANNELS     : in integer;
                                              constant C_NFFT_MAX     : in integer;
                                              constant C_HAS_OVFLO    : in integer;
                                              constant C_HAS_SCALING  : in integer;
                                              constant C_HAS_BFP      : in integer;
                                              constant C_HAS_XK_INDEX : in integer;
                                              constant C_USE_FLT_PT   : in integer
                                              ) return std_logic_vector;
  procedure convert_data_out_vector_to_axi (constant fifo_vector    : in std_logic_vector;
                                            signal tlast            : out std_logic;
                                            signal tdata            : out std_logic_vector;
                                            signal tuser            : out std_logic_vector;
                                            constant C_OUTPUT_WIDTH : in integer;
                                            constant C_CHANNELS     : in integer;
                                            constant C_NFFT_MAX     : in integer;
                                            constant C_HAS_OVFLO    : in integer;
                                            constant C_HAS_SCALING  : in integer;
                                            constant C_HAS_BFP      : in integer;
                                            constant C_HAS_XK_INDEX : in integer;
                                            constant C_USE_FLT_PT   : in integer
                                            );
  function calculate_status_channel_width_no_padding(constant C_CHANNELS   : in integer;
                                                     constant C_HAS_OVFLO  : in integer;
                                                     constant C_HAS_SCALING: in integer;
                                                     constant C_HAS_BFP    : in integer;
                                                     constant C_USE_FLT_PT : in integer) return integer;
  function convert_fft_out_to_status_vector        ( constant blk_exp        : in T_BLK_EXP_CHANNEL_ARRAY;
                                                     constant ovflo          : in T_OVFLO_CHANNEL_ARRAY;
                                                     constant C_CHANNELS     : in integer;
                                                     constant C_HAS_OVFLO    : in integer;
                                                     constant C_HAS_SCALING  : in integer;
                                                     constant C_HAS_BFP      : in integer;
                                                     constant C_USE_FLT_PT   : in integer
                                                     ) return std_logic_vector;
  procedure convert_status_vector_to_axi           ( constant fifo_vector    : in std_logic_vector;
                                                     signal tdata            : out std_logic_vector;
                                                     constant C_CHANNELS     : in integer;
                                                     constant C_HAS_OVFLO    : in integer;
                                                     constant C_HAS_SCALING  : in integer;
                                                     constant C_HAS_BFP      : in integer;
                                                     constant C_USE_FLT_PT   : in integer
                                                     );
end timing_model_xfft_v8_0_axi_pkg;
package body timing_model_xfft_v8_0_axi_pkg is
  function has_nfft ( constant C_HAS_NFFT : integer)  return integer is
  begin
    return C_HAS_NFFT;
  end has_nfft;
  function has_cp_len ( constant C_HAS_CYCLIC_PREFIX  : in integer;
                        constant C_HAS_NATURAL_OUTPUT : in integer
                        )  return integer is
  begin
    if (C_HAS_CYCLIC_PREFIX = 1 and C_HAS_NATURAL_OUTPUT = 1) then
      return 1;
    else
      return 0;
    end if;
  end has_cp_len;
  function has_scale_sch( constant C_HAS_SCALING  : in integer;
                          constant C_HAS_BFP      : in integer
                          )  return integer is
  begin
    if (C_HAS_SCALING = 1) and (C_HAS_BFP = 0) then
      return 1;
    else
      return 0;
    end if;
  end has_scale_sch;
  function has_ovflo     ( constant C_HAS_OVFLO   : integer;
                           constant C_HAS_SCALING : integer;
                           constant C_HAS_BFP     : integer;
                           constant C_USE_FLT_PT  : integer
                         ) return integer is
  begin
    if C_HAS_OVFLO = 1 and ((C_HAS_SCALING = 1 and C_HAS_BFP = 0) or C_USE_FLT_PT = 1) then
      return 1;
    else
      return 0;
    end if;
  end has_ovflo;
  function has_blk_exp  ( constant C_HAS_SCALING : integer;
                          constant  C_HAS_BFP     : integer
                        ) return integer is
  begin
    if C_HAS_SCALING = 1 and C_HAS_BFP = 1 then
      return 1;
    else
      return 0;
    end if;
  end has_blk_exp;
  function is_streaming  ( constant C_ARCH : integer
                         ) return integer is
  begin
    if C_ARCH = 3 then
      return 1;
    else
      return 0;
    end if;
  end is_streaming;
  function is_rdx2_lite  ( constant C_ARCH : integer
                         ) return integer is
  begin
    if C_ARCH = 4 then
      return 1;
    else
      return 0;
    end if;
  end is_rdx2_lite;
  function how_much_padding(field_width : integer) return integer is
    variable padding_bits : integer := 0;
  begin
    if field_width mod 8 = 0 then
      padding_bits := 0;
    else
      padding_bits :=  8 - (field_width mod 8);
    end if;
    return padding_bits;
  end how_much_padding;
  function calculate_config_width_no_padding(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING,
                                             C_NFFT_MAX, C_HAS_BFP, C_ARCH : integer) return integer is
    variable result : integer;
  begin
    result := C_CHANNELS;
    if C_HAS_NFFT = 1  then
      result := result + 5;
    end if;
    if (C_HAS_CYCLIC_PREFIX = 1 and C_HAS_NATURAL_OUTPUT = 1) then
      result := result + C_NFFT_MAX;
    end if;
    if (C_HAS_SCALING = 1) and (C_HAS_BFP = 0) then
      result := result + (get_scale_sch_width(C_NFFT_MAX, C_ARCH) * C_CHANNELS);
    end if;
    return result;
  end calculate_config_width_no_padding;
  procedure split_config_tdata (constant tdata                 : in STD_LOGIC_VECTOR;
                                signal   nfft                  : out std_logic_vector;
                                signal   cp_len                : out std_logic_vector;
                                signal   fwd_inv0              : out  std_logic;
                                signal   fwd_inv1              : out  std_logic;
                                signal   fwd_inv2              : out  std_logic;
                                signal   fwd_inv3              : out  std_logic;
                                signal   fwd_inv4              : out  std_logic;
                                signal   fwd_inv5              : out  std_logic;
                                signal   fwd_inv6              : out  std_logic;
                                signal   fwd_inv7              : out  std_logic;
                                signal   fwd_inv8              : out  std_logic;
                                signal   fwd_inv9              : out  std_logic;
                                signal   fwd_inv10             : out  std_logic;
                                signal   fwd_inv11             : out  std_logic;
                                signal   scale_sch0            : out  std_logic_vector;
                                signal   scale_sch1            : out  std_logic_vector;
                                signal   scale_sch2            : out  std_logic_vector;
                                signal   scale_sch3            : out  std_logic_vector;
                                signal   scale_sch4            : out  std_logic_vector;
                                signal   scale_sch5            : out  std_logic_vector;
                                signal   scale_sch6            : out  std_logic_vector;
                                signal   scale_sch7            : out  std_logic_vector;
                                signal   scale_sch8            : out  std_logic_vector;
                                signal   scale_sch9            : out  std_logic_vector;
                                signal   scale_sch10           : out  std_logic_vector;
                                signal   scale_sch11           : out  std_logic_vector;
                                constant C_HAS_NFFT           : in  integer;
                                constant C_HAS_CYCLIC_PREFIX  : in  integer;
                                constant C_HAS_NATURAL_OUTPUT : in  integer;
                                constant C_CHANNELS           : in  integer;
                                constant C_HAS_SCALING        : in  integer;
                                constant C_NFFT_MAX           : in  integer;
                                constant C_HAS_BFP            : in  integer;
                                constant C_ARCH               : in  integer;
                                constant C_CONTAINS_PADDING   : in integer
                                ) is
    variable in_ptr      : integer := 0;
    variable field_width : integer;
  begin
    if has_nfft(C_HAS_NFFT) = 1  then
      field_width := NFFT_MAX_WIDTH;
      nfft <= tdata(in_ptr + field_width - 1 downto in_ptr);
      in_ptr := in_ptr  + field_width + (how_much_padding(field_width) * C_CONTAINS_PADDING);
    end if;
    if (has_cp_len(C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT) = 1) then
      field_width := C_NFFT_MAX;
      cp_len <= tdata(in_ptr + field_width - 1 downto in_ptr);
      in_ptr  := in_ptr  + field_width + (how_much_padding(field_width) * C_CONTAINS_PADDING);
    end if;
    field_width := 1;
    fwd_inv0 <= tdata(in_ptr);
    in_ptr  := in_ptr  + field_width;
    if(C_CHANNELS > 1) then
      fwd_inv1 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 2) then
      fwd_inv2 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 3) then
      fwd_inv3 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 4) then
      fwd_inv4 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 5) then
      fwd_inv5 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 6) then
      fwd_inv6 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 7) then
      fwd_inv7 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 8) then
      fwd_inv8 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 9) then
      fwd_inv9 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 10) then
      fwd_inv10 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if(C_CHANNELS > 11) then
      fwd_inv11 <= tdata(in_ptr);
      in_ptr  := in_ptr  + field_width;
    end if;
    if has_scale_sch(C_HAS_SCALING, C_HAS_BFP) = 1 then
      field_width := get_scale_sch_width(C_NFFT_MAX, C_ARCH);
      scale_sch0 <= tdata(in_ptr + field_width-1 downto in_ptr);
      in_ptr  := in_ptr  + field_width;
      if(C_CHANNELS > 1) then
        scale_sch1 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 2) then
        scale_sch2 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 3) then
        scale_sch3 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 4) then
        scale_sch4 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 5) then
        scale_sch5 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 6) then
        scale_sch6 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 7) then
        scale_sch7 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 8) then
        scale_sch8 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 9) then
        scale_sch9 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 10) then
        scale_sch10 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
      if(C_CHANNELS > 11) then
        scale_sch11 <= tdata(in_ptr + field_width-1 downto in_ptr);
        in_ptr  := in_ptr  + field_width;
      end if;
    end if;
  end split_config_tdata;
  function remove_config_data_padding(padded_data: STD_LOGIC_VECTOR;   C_HAS_NFFT   , C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS,
                                                                       C_HAS_SCALING, C_NFFT_MAX         , C_HAS_BFP           , C_ARCH : integer)
    return STD_LOGIC_VECTOR is
    variable out_vector : STD_LOGIC_VECTOR(calculate_config_width_no_padding(
                                                   C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT,
                                                   C_CHANNELS, C_HAS_SCALING, C_NFFT_MAX, C_HAS_BFP, C_ARCH)-1
                                           downto 0);
    variable in_ptr      : integer := 0;
    variable out_ptr     : integer := 0;
    variable field_width : integer;
  begin
    if C_HAS_NFFT = 1 then
      field_width := NFFT_MAX_WIDTH;
      out_vector(out_ptr + field_width -1 downto out_ptr) := padded_data(in_ptr + field_width - 1 downto in_ptr);
      out_ptr := out_ptr + field_width;
      in_ptr  := in_ptr  + field_width + how_much_padding(field_width);
    else
    end if;
    if (C_HAS_CYCLIC_PREFIX = 1 and C_HAS_NATURAL_OUTPUT = 1) then
      field_width := C_NFFT_MAX;
      out_vector(out_ptr + field_width-1 downto out_ptr) := padded_data(in_ptr + field_width-1 downto in_ptr);
      out_ptr := out_ptr + field_width;
      in_ptr  := in_ptr  + field_width + how_much_padding(field_width);
    else
    end if;
    field_width := C_CHANNELS;
    out_vector(out_ptr + field_width-1 downto out_ptr) := padded_data(in_ptr + field_width-1 downto in_ptr);
    out_ptr := out_ptr + field_width;
    in_ptr  := in_ptr  + field_width;
    if (C_HAS_SCALING = 1) and (C_HAS_BFP = 0) then
      field_width := get_scale_sch_width(C_NFFT_MAX, C_ARCH) * C_CHANNELS;
      out_vector(out_ptr + field_width-1 downto out_ptr) := padded_data(in_ptr + field_width-1 downto in_ptr);
      out_ptr := out_ptr + field_width;
      in_ptr  := in_ptr  + field_width;
    end if;
    return out_vector;
  end remove_config_data_padding;
  function get_config_default_value(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING,
                                             C_NFFT_MAX, C_HAS_BFP, C_ARCH : integer) return std_logic_vector is
    variable out_vector : STD_LOGIC_VECTOR(calculate_config_width_no_padding(
                                                           C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT,
                                                           C_CHANNELS, C_HAS_SCALING, C_NFFT_MAX, C_HAS_BFP, C_ARCH)-1
                                           downto 0);
    variable out_ptr         : integer := 0;
    variable field_width     : integer;
    constant scale_sch_width : integer := get_scale_sch_width(C_NFFT_MAX, C_ARCH);
    variable max_scale_sch   : std_logic_vector(scale_sch_width -1 downto 0);
  begin
    if C_HAS_NFFT = 1  then
      out_vector(NFFT_MAX_WIDTH-1 downto 0) := std_logic_vector(to_unsigned(C_NFFT_MAX, NFFT_MAX_WIDTH));
      out_ptr := NFFT_MAX_WIDTH;
    end if;
    if (C_HAS_CYCLIC_PREFIX = 1 and C_HAS_NATURAL_OUTPUT = 1) then
      out_vector(out_ptr + C_NFFT_MAX-1 downto out_ptr) := (others => '0');
      out_ptr := out_ptr + C_NFFT_MAX;
    end if;
    out_vector(out_ptr + C_CHANNELS-1 downto out_ptr) := (others => '1');
    out_ptr := out_ptr + C_CHANNELS;
    if (C_HAS_SCALING = 1) and (C_HAS_BFP = 0) then
      field_width := scale_sch_width;
       for c in 1 to C_CHANNELS loop
         case C_ARCH is
           when 1 =>
             max_scale_sch := default_r4_scale_sch(scale_sch_width);
           when 3 =>
             max_scale_sch := r22_scale_sch_init(C_NFFT_MAX);
           when others =>
             max_scale_sch := default_r2_scale_sch(C_NFFT_MAX);
         end case;
         out_vector(out_ptr + field_width -1 downto out_ptr) := max_scale_sch;
         out_ptr := out_ptr + field_width;
       end loop;
    end if;
    return out_vector;
  end get_config_default_value;
  function calculate_data_in_width_no_padding(C_INPUT_WIDTH, C_CHANNELS: integer) return integer is
    variable result : integer;
  begin
    result := (2 * C_INPUT_WIDTH * C_CHANNELS)+1;
    return result;
  end calculate_data_in_width_no_padding;
  procedure convert_data_in_vector_to_fft_in (constant fifo_vector          : in  std_logic_vector;
                                              signal   tlast                : out std_logic;
                                              variable XN_RE                : out std_logic_vector;
                                              variable XN_IM                : out std_logic_vector;
                                              variable XN0_RE               : out std_logic_vector;
                                              variable XN0_IM               : out std_logic_vector;
                                              variable XN1_RE               : out std_logic_vector;
                                              variable XN1_IM               : out std_logic_vector;
                                              variable XN2_RE               : out std_logic_vector;
                                              variable XN2_IM               : out std_logic_vector;
                                              variable XN3_RE               : out std_logic_vector;
                                              variable XN3_IM               : out std_logic_vector;
                                              variable XN4_RE               : out std_logic_vector;
                                              variable XN4_IM               : out std_logic_vector;
                                              variable XN5_RE               : out std_logic_vector;
                                              variable XN5_IM               : out std_logic_vector;
                                              variable XN6_RE               : out std_logic_vector;
                                              variable XN6_IM               : out std_logic_vector;
                                              variable XN7_RE               : out std_logic_vector;
                                              variable XN7_IM               : out std_logic_vector;
                                              variable XN8_RE               : out std_logic_vector;
                                              variable XN8_IM               : out std_logic_vector;
                                              variable XN9_RE               : out std_logic_vector;
                                              variable XN9_IM               : out std_logic_vector;
                                              variable XN10_RE              : out std_logic_vector;
                                              variable XN10_IM              : out std_logic_vector;
                                              variable XN11_RE              : out std_logic_vector;
                                              variable XN11_IM              : out std_logic_vector;
                                              constant C_INPUT_WIDTH        : in  integer;
                                              constant C_CHANNELS           : in  integer;
                                              constant C_CONTAINS_PADDING   : in  integer
                                              ) is
    variable in_ptr        : integer := 0;
    variable field_width   : integer;
    variable padding_width : integer;
  begin
    tlast <= fifo_vector(0);
    in_ptr := in_ptr + 1;
    field_width   := C_INPUT_WIDTH;
    padding_width :=  how_much_padding(field_width) * C_CONTAINS_PADDING;
    XN_RE  := fifo_vector(in_ptr + field_width -1 downto in_ptr);
    XN0_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
    in_ptr := in_ptr  + field_width + padding_width;
    XN_IM  := fifo_vector(in_ptr + field_width -1 downto in_ptr);
    XN0_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
    in_ptr := in_ptr  + field_width + padding_width;
    if(C_CHANNELS > 1) then
      XN1_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN1_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 2) then
      XN2_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN2_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 3) then
      XN3_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN3_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 4) then
      XN4_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN4_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 5) then
      XN5_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN5_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 6) then
      XN6_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN6_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 7) then
      XN7_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN7_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 8) then
      XN8_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN8_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 9) then
      XN9_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
      XN9_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 10) then
      XN10_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr  := in_ptr  + field_width + padding_width;
      XN10_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr  := in_ptr  + field_width + padding_width;
    end if;
    if(C_CHANNELS > 11) then
      XN11_RE := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr  := in_ptr  + field_width + padding_width;
      XN11_IM := fifo_vector(in_ptr + field_width -1 downto in_ptr);
      in_ptr  := in_ptr  + field_width + padding_width;
    end if;
  end convert_data_in_vector_to_fft_in;
  function convert_axi_to_data_in_vector(tdata         : STD_LOGIC_VECTOR;
                                         tlast         : STD_LOGIC;
                                         C_INPUT_WIDTH : integer;
                                         C_CHANNELS    : integer)
    return STD_LOGIC_VECTOR is
    variable out_vector : STD_LOGIC_VECTOR(calculate_data_in_width_no_padding(C_INPUT_WIDTH,C_CHANNELS) - 1 downto 0);
    variable in_ptr      : integer := 0;
    variable out_ptr     : integer := 0;
    variable field_width : integer;
  begin
    out_vector(out_ptr) := tlast;
    out_ptr  := out_ptr  + 1;
    field_width := C_INPUT_WIDTH;
    for c in 1 to C_CHANNELS*2 loop
      out_vector(out_ptr + field_width -1 downto out_ptr) := tdata(in_ptr + field_width - 1 downto in_ptr);
      out_ptr := out_ptr + field_width;
      in_ptr  := in_ptr  + field_width + how_much_padding(field_width);
    end loop;
    return out_vector;
  end convert_axi_to_data_in_vector;
  function calculate_data_out_width_no_padding(constant C_OUTPUT_WIDTH: in integer;
                                               constant C_CHANNELS      : in integer;
                                               constant C_NFFT_MAX      : in integer;
                                               constant C_HAS_OVFLO     : in integer;
                                               constant C_HAS_SCALING   : in integer;
                                               constant C_HAS_BFP       : in integer;
                                               constant C_HAS_XK_INDEX  : in integer;
                                               constant C_USE_FLT_PT    : in integer) return integer is
    variable result : integer :=1;
  begin
    result := result + (C_OUTPUT_WIDTH * 2 * C_CHANNELS);
    if C_HAS_XK_INDEX = 1 then
       result := result + C_NFFT_MAX;
    end if;
    result := result + calculate_status_channel_width_no_padding(C_CHANNELS   => C_CHANNELS   ,
                                                                 C_HAS_OVFLO  => C_HAS_OVFLO  ,
                                                                 C_HAS_SCALING=> C_HAS_SCALING,
                                                                 C_HAS_BFP    => C_HAS_BFP    ,
                                                                 C_USE_FLT_PT => C_USE_FLT_PT );
    return result;
  end calculate_data_out_width_no_padding;
  function get_start_of_data_channel_ovflo(constant C_NFFT_MAX : in integer;
                                           constant C_HAS_XK_INDEX: in integer) return integer is
    variable field_width   : integer := 0;
    variable padding_width : integer := 0;
  begin
    if C_HAS_XK_INDEX = 1 then
      field_width   := C_NFFT_MAX;
      padding_width := how_much_padding(field_width);
    end if;
    return field_width + padding_width;
  end get_start_of_data_channel_ovflo;
  function convert_fft_out_to_data_out_vector(
                                 constant tlast          : in std_logic;
                                 constant xk_re          : in T_XK_RE_CHANNEL_ARRAY;
                                 constant xk_im          : in T_XK_RE_CHANNEL_ARRAY;
                                 constant xk_index       : in std_logic_vector;
                                 constant blk_exp        : in T_BLK_EXP_CHANNEL_ARRAY;
                                 constant ovflo          : in T_OVFLO_CHANNEL_ARRAY;
                                 constant C_OUTPUT_WIDTH : in integer;
                                 constant C_CHANNELS     : in integer;
                                 constant C_NFFT_MAX     : in integer;
                                 constant C_HAS_OVFLO    : in integer;
                                 constant C_HAS_SCALING  : in integer;
                                 constant C_HAS_BFP      : in integer;
                                 constant C_HAS_XK_INDEX : in integer;
                                 constant C_USE_FLT_PT   : in integer
                                 ) return std_logic_vector is
    constant vector_width : integer := calculate_data_out_width_no_padding(C_OUTPUT_WIDTH  => C_OUTPUT_WIDTH,
                                                                           C_CHANNELS      => C_CHANNELS   ,
                                                                           C_NFFT_MAX      => C_NFFT_MAX   ,
                                                                           C_HAS_OVFLO     => C_HAS_OVFLO  ,
                                                                           C_HAS_SCALING   => C_HAS_SCALING,
                                                                           C_HAS_BFP       => C_HAS_BFP    ,
                                                                           C_HAS_XK_INDEX  => C_HAS_XK_INDEX,
                                                                           C_USE_FLT_PT    => C_USE_FLT_PT
                                                                           );
    variable result      : std_logic_vector( vector_width-1 downto 0) := (others => '-');
    variable out_ptr     : integer := 0;
    variable field_width : integer;
  begin
    field_width     := 1;
    result(out_ptr) := tlast;
    out_ptr         := out_ptr + field_width;
    field_width := C_OUTPUT_WIDTH;
    for chan in 0 to C_CHANNELS-1 loop
      result(out_ptr + field_width -1 downto out_ptr) := xk_re(chan)(C_OUTPUT_WIDTH-1 downto 0);
      out_ptr                                         := out_ptr + field_width;
      result(out_ptr + field_width -1 downto out_ptr) := xk_im(chan)(C_OUTPUT_WIDTH-1 downto 0);
      out_ptr                                         := out_ptr + field_width;
    end loop;
    if C_HAS_XK_INDEX = 1 then
      field_width                                     := C_NFFT_MAX;
      result(out_ptr + field_width -1 downto out_ptr) := xk_index;
      out_ptr                                         := out_ptr + field_width;
    end if;
    if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT ) /= 0 then
      field_width := 1;
      for chan in 0 to C_CHANNELS-1 loop
        result(out_ptr) := ovflo(chan);
        out_ptr         := out_ptr + field_width;
      end loop;
    elsif has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0  then
      field_width := BLK_EXP_WIDTH;
      for chan in 0 to C_CHANNELS-1 loop
        result(out_ptr + field_width -1 downto out_ptr) := blk_exp(chan);
        out_ptr                                         := out_ptr + field_width;
      end loop;
    end if;
    return result;
  end convert_fft_out_to_data_out_vector;
  procedure convert_data_out_vector_to_axi (constant fifo_vector    : in std_logic_vector;
                                            signal tlast            : out std_logic;
                                            signal tdata            : out std_logic_vector;
                                            signal tuser            : out std_logic_vector;
                                            constant C_OUTPUT_WIDTH : in integer;
                                            constant C_CHANNELS     : in integer;
                                            constant C_NFFT_MAX     : in integer;
                                            constant C_HAS_OVFLO    : in integer;
                                            constant C_HAS_SCALING  : in integer;
                                            constant C_HAS_BFP      : in integer;
                                            constant C_HAS_XK_INDEX : in integer;
                                            constant C_USE_FLT_PT   : in integer
                                            ) is
    variable in_ptr        : integer := 0;
    variable out_ptr       : integer := 0;
    variable field_width   : integer;
    variable padding_width : integer;
    variable sign_bit      : std_logic;
  begin
    field_width := 1;
    tlast       <= fifo_vector(in_ptr);
    in_ptr      := in_ptr + field_width;
    field_width   := C_OUTPUT_WIDTH;
    padding_width := how_much_padding(field_width);
    out_ptr       := 0;
    for chan in 0 to (2*C_CHANNELS)-1 loop
      tdata(out_ptr + field_width -1 downto out_ptr) <= fifo_vector(in_ptr + field_width - 1 downto in_ptr);
      out_ptr                                        := out_ptr + field_width;
      sign_bit := fifo_vector(in_ptr + field_width - 1);
      for p in 0 to padding_width - 1 loop
        tdata(out_ptr) <= sign_bit;
        out_ptr := out_ptr + 1;
      end loop;
      in_ptr := in_ptr + field_width;
    end loop;
    out_ptr       := 0;
    if C_HAS_XK_INDEX /= 0 then
      field_width   := C_NFFT_MAX;
      padding_width := how_much_padding(field_width);
      sign_bit      := '0';
      tuser(out_ptr + field_width -1 downto out_ptr) <= fifo_vector(in_ptr + field_width - 1 downto in_ptr);
      out_ptr                                        := out_ptr + field_width;
      for p in 0 to padding_width - 1 loop
        tuser(out_ptr) <= sign_bit;
        out_ptr        := out_ptr + 1;
      end loop;
      in_ptr := in_ptr + field_width;
    end if;
    if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT ) /= 0 then
      field_width   := C_CHANNELS;
      padding_width := how_much_padding(field_width);
      sign_bit      := '0';
      tuser(out_ptr + field_width -1 downto out_ptr) <= fifo_vector(in_ptr + field_width - 1 downto in_ptr);
      out_ptr                                        := out_ptr + field_width;
      in_ptr                                         := in_ptr + field_width;
      for p in 0 to padding_width - 1 loop
        tuser(out_ptr) <= sign_bit;
        out_ptr        := out_ptr + 1;
      end loop;
    elsif has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0  then
      field_width   := BLK_EXP_WIDTH;
      padding_width := how_much_padding(field_width);
      sign_bit      := '0';
      for chan in 0 to C_CHANNELS-1 loop
        tuser(out_ptr + field_width -1 downto out_ptr) <= fifo_vector(in_ptr + field_width - 1 downto in_ptr);
        out_ptr                                        := out_ptr + field_width;
        in_ptr                                         := in_ptr + field_width;
        for p in 0 to padding_width - 1 loop
          tuser(out_ptr) <= sign_bit;
          out_ptr        := out_ptr + 1;
        end loop;
      end loop;
    end if;
  end convert_data_out_vector_to_axi;
  function calculate_status_channel_width_no_padding(constant C_CHANNELS   : in integer;
                                                     constant C_HAS_OVFLO  : in integer;
                                                     constant C_HAS_SCALING: in integer;
                                                     constant C_HAS_BFP    : in integer;
                                                     constant C_USE_FLT_PT : in integer) return integer is
    variable result : integer :=0;
  begin
    if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT ) /= 0 then
      result := C_CHANNELS;
    elsif has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0  then
      result := BLK_EXP_WIDTH * C_CHANNELS;
    end if;
    return result;
  end calculate_status_channel_width_no_padding;
  function convert_fft_out_to_status_vector(
                                 constant blk_exp        : in T_BLK_EXP_CHANNEL_ARRAY;
                                 constant ovflo          : in T_OVFLO_CHANNEL_ARRAY;
                                 constant C_CHANNELS     : in integer;
                                 constant C_HAS_OVFLO    : in integer;
                                 constant C_HAS_SCALING  : in integer;
                                 constant C_HAS_BFP      : in integer;
                                 constant C_USE_FLT_PT   : in integer
                                 ) return std_logic_vector is
    constant vector_width : integer := calculate_status_channel_width_no_padding( C_CHANNELS    => C_CHANNELS,
                                                                                  C_HAS_OVFLO   => C_HAS_OVFLO,
                                                                                  C_HAS_SCALING => C_HAS_SCALING,
                                                                                  C_HAS_BFP     => C_HAS_BFP,
                                                                                  C_USE_FLT_PT  => C_USE_FLT_PT
                                                                                  );
    variable result      : std_logic_vector( vector_width-1 downto 0) := (others => '-');
    variable out_ptr     : integer := 0;
    variable field_width : integer;
  begin
    if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT ) /= 0 then
      field_width := 1;
      for chan in 0 to C_CHANNELS-1 loop
        result(out_ptr) := ovflo(chan);
        out_ptr         := out_ptr + field_width;
      end loop;
    elsif has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0  then
      field_width := BLK_EXP_WIDTH;
      for chan in 0 to C_CHANNELS-1 loop
        result(out_ptr + field_width -1 downto out_ptr) := blk_exp(chan);
        out_ptr                                         := out_ptr + field_width;
      end loop;
    end if;
    return result;
  end convert_fft_out_to_status_vector;
  procedure convert_status_vector_to_axi (constant fifo_vector    : in std_logic_vector;
                                            signal tdata            : out std_logic_vector;
                                            constant C_CHANNELS     : in integer;
                                            constant C_HAS_OVFLO    : in integer;
                                            constant C_HAS_SCALING  : in integer;
                                            constant C_HAS_BFP      : in integer;
                                            constant C_USE_FLT_PT   : in integer
                                            ) is
    variable in_ptr        : integer := 0;
    variable out_ptr       : integer := 0;
    variable field_width   : integer;
    variable padding_width : integer;
    variable sign_bit      : std_logic;
  begin
    if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT ) /= 0 then
      field_width   := C_CHANNELS;
      padding_width := how_much_padding(field_width);
      sign_bit      := '0';
      tdata(out_ptr + field_width -1 downto out_ptr) <= fifo_vector(in_ptr + field_width - 1 downto in_ptr);
      out_ptr                                        := out_ptr + field_width;
      in_ptr                                         := in_ptr  + field_width;
      for p in 0 to padding_width - 1 loop
        tdata(out_ptr) <= sign_bit;
        out_ptr     := out_ptr + 1;
      end loop;
    elsif has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0  then
      field_width   := BLK_EXP_WIDTH;
      padding_width := how_much_padding(field_width);
      sign_bit      := '0';
      for chan in 0 to C_CHANNELS-1 loop
        tdata(out_ptr + field_width -1 downto out_ptr) <= fifo_vector(in_ptr + field_width - 1 downto in_ptr);
        out_ptr                                        := out_ptr + field_width;
        in_ptr                                         := in_ptr  + field_width;
        for p in 0 to padding_width - 1 loop
          tdata(out_ptr) <= sign_bit;
          out_ptr        := out_ptr + 1;
        end loop;
      end loop;
    end if;
  end convert_status_vector_to_axi;
end timing_model_xfft_v8_0_axi_pkg;
