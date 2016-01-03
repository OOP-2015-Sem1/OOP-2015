
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
library std;
use std.textio.all;
library XilinxCoreLib;
use XilinxCoreLib.prims_utils_v9_0.all;
library work;
use work.fir_compiler_v3_2_sim_pkg.all;
entity fir_compiler_v3_2_model is
generic (
  C_FAMILY            : string  := "virtex4";
  C_XDEVICEFAMILY     : string  := "virtex4";
  C_ELABORATION_DIR   : string  := "./";
  C_COMPONENT_NAME    : string  := "fir";
  C_MEM_INIT_FILE     : string  := "coeff.mif";
  FILTER_TYPE         : integer := 0;
  INTERP_RATE         : integer := 1;
  DECIM_RATE          : integer := 1;
  RATE_CHANGE_TYPE    : integer := 0;
  ZERO_PACKING_FACTOR : integer := 0;
  NUM_CHANNELS        : integer := 1;
  CHAN_SEL_WIDTH      : integer := 4;
  NUM_TAPS            : integer := 16;
  CLOCK_FREQ          : integer := 400000000;
  SAMPLE_FREQ         : integer := 100000000;
  FILTER_ARCH         : integer := 1;
  DATA_TYPE           : integer := 0;
  DATA_WIDTH          : integer := 16;
  COEF_TYPE           : integer := 0;
  COEF_WIDTH          : integer := 16;
  ROUND_MODE          : integer := 0;
  ACCUM_WIDTH         : integer := 48;
  OUTPUT_WIDTH        : integer := 48;
  ALLOW_APPROX        : integer := 0;
  OUTPUT_REG          : integer := 1;
  SYMMETRY            : integer := 0;
  ODD_SYMMETRY        : integer := 0;
  NEG_SYMMETRY        : integer := 0;
  COEF_RELOAD         : integer := 0;
  NUM_FILTS           : integer := 1;
  FILTER_SEL_WIDTH    : integer := 4;
  C_HAS_SCLR          : integer := 1;
  C_HAS_CE            : integer := 0;
  C_HAS_ND            : integer := 1;
  DATA_MEMTYPE        : integer := 0;
  COEF_MEMTYPE        : integer := 0;
  IPBUFF_MEMTYPE      : integer := 0;
  OPBUFF_MEMTYPE      : integer := 0;
  DATAPATH_MEMTYPE    : integer := 0;
  COL_MODE            : integer := 0;
  COL_1ST_LEN         : integer := 3;
  COL_WRAP_LEN        : integer := 4;
  COL_PIPE_LEN        : integer := 3;
  C_LATENCY           : integer := 0;
  C_OPTIMIZATION      : integer := 0
);
port (
  SCLR            : in  std_logic;
  CLK             : in  std_logic;
  CE              : in  std_logic;
  ND              : in  std_logic;
  DIN             : in  std_logic_vector(DATA_WIDTH-1 downto 0);
  FILTER_SEL      : in  std_logic_vector(FILTER_SEL_WIDTH-1 downto 0);
  COEF_LD         : in  std_logic;
  COEF_WE         : in  std_logic;
  COEF_DIN        : in  std_logic_vector(COEF_WIDTH-1 downto 0);
  COEF_FILTER_SEL : in  std_logic_vector(FILTER_SEL_WIDTH-1 downto 0);

  RFD             : out std_logic := '0';
  RDY             : out std_logic := '0';
  DOUT            : out std_logic_vector(OUTPUT_WIDTH-1 downto 0) := (others=>'0');
  DOUT_I          : out std_logic_vector(DATA_WIDTH-1 downto 0) := (others=>'0');
  DOUT_Q          : out std_logic_vector(OUTPUT_WIDTH-1 downto 0) := (others=>'0');
  CHAN_IN         : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0) := (others=>'0');
  CHAN_OUT        : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0) := (others=>'0')
);
end entity fir_compiler_v3_2_model;
architecture behavioural of fir_compiler_v3_2_model is
constant clocks_per_sample : integer := integer(clock_freq/sample_freq)/num_channels;
constant clks_per_samp_int : integer := integer(clock_freq/sample_freq);
constant family_lcase        : string := lcase(C_FAMILY);
constant xdevicefamily_lcase : string := lcase(C_XDEVICEFAMILY);
constant fir_model_reqs_v3 : t_reqs := (
        family              => family_val(select_string(family_lcase,
                                                        xdevicefamily_lcase,
                                                        family_lcase="spartan3")),
        filter_type         => select_integer(FILTER_TYPE,
                                              c_interpolating_symmetry,
                                              FILTER_TYPE=c_polyphase_interpolating and SYMMETRY=1),
        deci_rate           => DECIM_RATE,
        inter_rate          => INTERP_RATE,
        rate_type           => RATE_CHANGE_TYPE,
        num_taps            => NUM_TAPS,
        clk_per_samp        => clks_per_samp_int,
        num_channels        => NUM_CHANNELS,
        num_filts           => NUM_FILTS,
        symmetry            => SYMMETRY,
        neg_symmetry        => NEG_SYMMETRY,
        zero_packing_factor => ZERO_PACKING_FACTOR,
        coef_reload         => COEF_RELOAD,
        data_width          => DATA_WIDTH,
        coef_width          => COEF_WIDTH,
        filt_sel_width      => FILTER_SEL_WIDTH,
        chan_width          => CHAN_SEL_WIDTH,
        round_mode          => ROUND_MODE,
        accum_width         => ACCUM_WIDTH,
        output_width        => OUTPUT_WIDTH,
        allow_approx        => ALLOW_APPROX,
        data_mem_type       => DATA_MEMTYPE,
        coef_mem_type       => COEF_MEMTYPE,
        ipbuff_mem_type     => IPBUFF_MEMTYPE,
        opbuff_mem_type     => OPBUFF_MEMTYPE,
        datapath_mem_type   => DATAPATH_MEMTYPE,
        data_sign           => DATA_TYPE,
        coef_sign           => COEF_TYPE,
        reg_output          => OUTPUT_REG,
        has_nd              => C_HAS_ND,
        has_ce              => C_HAS_CE,
        has_sclr            => C_HAS_SCLR,
        col_mode            => COL_MODE,
        col_1st_len         => COL_1ST_LEN,
        col_wrap_len        => COL_WRAP_LEN,
        col_pipe_len        => COL_PIPE_LEN,
        resource_opt        => C_OPTIMIZATION
        );
type t_syst_mac_fir_properties is
record
  cascade_dly         : integer;
  actual_taps         : integer;
  reload_taps         : integer;
  symmetry            : integer;
  odd_symmetry        : integer;
  full_taps           : integer;
  ipbuff_depth        : integer;
  ipbuff_rate         : integer;
  ipbuff_thresh       : integer;
  opbuff_depth        : integer;
  opbuff_rate         : integer;
  opbuff_thresh       : integer;
  inter_rate          : integer;
  deci_rate           : integer;
  zpf                 : integer;
  clk_per_chan        : integer;
  num_macs            : integer;
  single_mac          : integer;
  centre_mac          : integer;
  base_count          : integer;
  base_data_space     : integer;
  base_coef_space     : integer;
  coef_mem_type       : integer;
  data_mem_type       : integer;
  reload_lat          : integer;
  use_approx          : boolean;
  full_parallel       : integer;
end record;
function get_full_taps ( filter_type      : integer;
                         inter_rate       : integer;
                         num_taps         : integer;
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

begin

  properties.zpf             := 1;
  properties.ipbuff_depth    := 0;
  properties.opbuff_depth    := 0;
  properties.opbuff_rate     := 0;
  properties.opbuff_thresh   := 0;
  properties.full_parallel   := 0;
  case reqs.filter_type is
  when c_single_rate =>
    single_rate                   :=define_single_rate(reqs);
    properties.cascade_dly        :=single_rate.latency;
    properties.actual_taps        :=single_rate.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps;
    properties.symmetry           :=single_rate.param.symmetry;
    properties.odd_symmetry       :=single_rate.param.odd_symmetry;
    properties.clk_per_chan       :=single_rate.param.clk_per_chan;
    properties.reload_lat         :=0;
    properties.num_macs           :=single_rate.param.num_macs;
    properties.single_mac         :=single_rate.param.single_mac;
    properties.base_count         :=single_rate.param.base_count;
    properties.inter_rate         :=single_rate.param.inter_rate;
    properties.deci_rate          :=single_rate.param.deci_rate;
    properties.centre_mac         :=single_rate.param.centre_mac;
    properties.coef_mem_type      :=single_rate.param.coef_mem_type;
    properties.data_mem_type      :=single_rate.param.data_mem_type;
    properties.base_coef_space    :=single_rate.param.base_coef_space;
    properties.base_data_space    :=single_rate.param.base_coef_space;
    properties.use_approx         :=single_rate.use_approx;
    properties.full_parallel      :=single_rate.param.full_parallel;
  when c_polyphase_decimating =>
    decimation                    :=define_decimation(reqs);
    properties.cascade_dly        :=decimation.latency;
    properties.actual_taps        :=decimation.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps;
    properties.symmetry           :=decimation.param.symmetry;
    properties.odd_symmetry       :=decimation.param.odd_symmetry;
    if decimation.has_output_buffer then
      properties.opbuff_depth     :=decimation.buffer_page_size;
    else
      properties.opbuff_depth     :=0;
    end if;
    properties.opbuff_rate        :=decimation.output_rate;
    properties.opbuff_thresh      :=properties.opbuff_depth-1;
    properties.clk_per_chan       :=decimation.param.clk_per_chan;
    properties.reload_lat         :=2;
    properties.num_macs           :=decimation.param.num_macs;
    properties.single_mac         :=decimation.param.single_mac;
    properties.base_count         :=decimation.param.base_count;
    properties.inter_rate         :=decimation.param.inter_rate;
    properties.deci_rate          :=decimation.param.deci_rate;
    properties.centre_mac         :=decimation.param.centre_mac;
    properties.coef_mem_type      :=decimation.param.coef_mem_type;
    properties.data_mem_type      :=decimation.param.data_mem_type;
    properties.base_coef_space    :=decimation.param.base_coef_space;
    properties.base_data_space    :=decimation.param.base_coef_space;
    properties.use_approx         :=decimation.use_approx;
    properties.full_parallel      :=decimation.param.full_parallel;
  when c_polyphase_interpolating =>
    interpolation                 :=define_interpolation(reqs);
    properties.cascade_dly        :=interpolation.latency;
    properties.actual_taps        :=interpolation.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps;
    properties.symmetry           :=interpolation.param.symmetry;
    properties.odd_symmetry       :=interpolation.param.odd_symmetry;
    if interpolation.has_input_buffer then
      properties.ipbuff_depth     := interpolation.buffer_page_size;
      properties.reload_lat       := 2+interpolation.input_buffer_lat;
    else
      properties.ipbuff_depth     := 0;
      properties.reload_lat       := 1;
    end if;
    properties.clk_per_chan       :=interpolation.param.clk_per_chan;
    properties.num_macs           :=interpolation.param.num_macs;
    properties.single_mac         :=interpolation.param.single_mac;
    properties.base_count         :=interpolation.param.base_count;
    properties.inter_rate         :=interpolation.param.inter_rate;
    properties.deci_rate          :=interpolation.param.deci_rate;
    properties.centre_mac         :=interpolation.param.centre_mac;
    properties.coef_mem_type      :=interpolation.param.coef_mem_type;
    properties.data_mem_type      :=interpolation.param.data_mem_type;
    properties.base_coef_space    :=interpolation.param.base_coef_space;
    properties.base_data_space    :=interpolation.param.base_coef_space;
    properties.use_approx         :=interpolation.use_approx;
    properties.full_parallel      :=interpolation.param.full_parallel;
  when c_interpolating_symmetry =>
    sympair_interpolation         :=define_sympair_interpolation(reqs);
    properties.cascade_dly        :=sympair_interpolation.latency;
    properties.actual_taps        :=sympair_interpolation.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps;
    properties.symmetry           :=sympair_interpolation.param.symmetry;
    properties.odd_symmetry       :=sympair_interpolation.param.odd_symmetry;
    if sympair_interpolation.has_output_buffer then
      properties.opbuff_depth     := sympair_interpolation.buffer_page_size;
    else
      properties.opbuff_depth     := 0;
    end if;
    properties.opbuff_rate        :=sympair_interpolation.output_rate;
    properties.opbuff_thresh      :=properties.opbuff_depth-reqs.inter_rate;
    properties.clk_per_chan       :=sympair_interpolation.param.clk_per_chan;
    properties.reload_lat         :=0;
    properties.num_macs           :=sympair_interpolation.param.num_macs;
    properties.single_mac         :=sympair_interpolation.param.single_mac;
    properties.base_count         :=sympair_interpolation.param.base_count;
    properties.inter_rate         :=sympair_interpolation.param.inter_rate;
    properties.deci_rate          :=sympair_interpolation.param.deci_rate;
    properties.centre_mac         :=sympair_interpolation.param.centre_mac;
    properties.coef_mem_type      :=sympair_interpolation.param.coef_mem_type;
    properties.data_mem_type      :=sympair_interpolation.param.data_mem_type;
    properties.base_coef_space    :=sympair_interpolation.param.base_coef_space;
    properties.base_data_space    :=sympair_interpolation.param.base_coef_space;
    properties.use_approx         :=sympair_interpolation.use_approx;
    properties.full_parallel      :=sympair_interpolation.param.full_parallel;
  when c_hilbert_transform =>
    hilbert                       :=define_hilbert(reqs);
    properties.cascade_dly        :=hilbert.latency;
    properties.actual_taps        :=hilbert.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps/2;
    properties.symmetry           :=hilbert.param.symmetry;
    properties.odd_symmetry       :=hilbert.param.odd_symmetry;
    properties.clk_per_chan       :=hilbert.param.clk_per_chan;
    properties.reload_lat         :=0;
    properties.num_macs           :=hilbert.param.num_macs;
    properties.single_mac         :=hilbert.param.single_mac;
    properties.base_count         :=hilbert.param.base_count;
    properties.inter_rate         :=hilbert.param.inter_rate;
    properties.deci_rate          :=hilbert.param.deci_rate;
    properties.centre_mac         :=hilbert.param.centre_mac;
    properties.coef_mem_type      :=hilbert.param.coef_mem_type;
    properties.data_mem_type      :=hilbert.param.data_mem_type;
    properties.base_coef_space    :=hilbert.param.base_coef_space;
    properties.base_data_space    :=hilbert.param.base_coef_space;
    properties.use_approx         :=hilbert.use_approx;
    properties.full_parallel      :=hilbert.param.full_parallel;
  when c_interpolated_transform =>
    interpolated                  :=define_interpolated(reqs);
    properties.cascade_dly        :=interpolated.latency;
    properties.actual_taps        :=interpolated.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps;
    properties.symmetry           :=interpolated.param.symmetry;
    properties.odd_symmetry       :=interpolated.param.odd_symmetry;
    properties.zpf                :=reqs.zero_packing_factor;
    properties.clk_per_chan       :=interpolated.param.clk_per_chan;
    properties.reload_lat         :=0;
    properties.num_macs           :=interpolated.param.num_macs;
    properties.single_mac         :=interpolated.param.single_mac;
    properties.base_count         :=interpolated.param.base_count;
    properties.inter_rate         :=interpolated.param.inter_rate;
    properties.deci_rate          :=interpolated.param.deci_rate;
    properties.centre_mac         :=interpolated.param.centre_mac;
    properties.coef_mem_type      :=interpolated.param.coef_mem_type;
    properties.data_mem_type      :=interpolated.param.data_mem_type;
    properties.base_coef_space    :=interpolated.param.base_coef_space;
    properties.base_data_space    :=interpolated.param.base_coef_space;
    properties.use_approx         :=interpolated.use_approx;
    properties.full_parallel      :=interpolated.param.full_parallel;
  when c_halfband_transform =>
    halfband                      :=define_halfband(reqs);
    properties.cascade_dly        :=halfband.latency;
    properties.actual_taps        :=halfband.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps/2 + 1;
    properties.symmetry           :=halfband.param.symmetry;
    properties.odd_symmetry       :=halfband.param.odd_symmetry;
    properties.clk_per_chan       :=halfband.param.clk_per_chan;
    properties.reload_lat         :=0;
    properties.num_macs           :=halfband.param.num_macs;
    properties.single_mac         :=halfband.param.single_mac;
    properties.base_count         :=halfband.param.base_count;
    properties.inter_rate         :=halfband.param.inter_rate;
    properties.deci_rate          :=halfband.param.deci_rate;
    properties.centre_mac         :=halfband.param.centre_mac;
    properties.coef_mem_type      :=halfband.param.coef_mem_type;
    properties.data_mem_type      :=halfband.param.data_mem_type;
    properties.base_coef_space    :=halfband.param.base_coef_space;
    properties.base_data_space    :=halfband.param.base_coef_space;
    properties.use_approx         :=halfband.use_approx;
    properties.full_parallel      :=halfband.param.full_parallel;
  when c_decimating_half_band =>
    halfband_decimation           :=define_halfband_decimation(reqs);
    properties.cascade_dly        :=halfband_decimation.latency;
    properties.actual_taps        :=halfband_decimation.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps/2 + 1;
    properties.symmetry           :=halfband_decimation.param.symmetry;
    properties.odd_symmetry       :=halfband_decimation.param.odd_symmetry;
    properties.ipbuff_depth       :=halfband_decimation.buffer_page_size;
    properties.clk_per_chan       :=halfband_decimation.param.clk_per_chan;
    properties.reload_lat         :=5+halfband_decimation.input_buffer_lat;
    properties.num_macs           :=halfband_decimation.param.num_macs;
    properties.single_mac         :=halfband_decimation.param.single_mac;
    properties.base_count         :=halfband_decimation.param.base_count;
    properties.inter_rate         :=halfband_decimation.param.inter_rate;
    properties.deci_rate          :=halfband_decimation.param.deci_rate;
    properties.centre_mac         :=halfband_decimation.param.centre_mac;
    properties.coef_mem_type      :=halfband_decimation.param.coef_mem_type;
    properties.data_mem_type      :=halfband_decimation.param.data_mem_type;
    properties.base_coef_space    :=halfband_decimation.param.base_coef_space;
    properties.base_data_space    :=halfband_decimation.param.base_coef_space;
    properties.use_approx         :=halfband_decimation.use_approx;
    properties.full_parallel      :=halfband_decimation.param.full_parallel;
  when c_interpolating_half_band =>
    halfband_interpolation        :=define_halfband_interpolation(reqs);
    properties.cascade_dly        :=halfband_interpolation.latency;
    properties.actual_taps        :=halfband_interpolation.param.num_taps_calced;
    properties.reload_taps        :=properties.actual_taps/2 + 1;
    properties.symmetry           :=halfband_interpolation.param.symmetry;
    properties.odd_symmetry       :=halfband_interpolation.param.odd_symmetry;
    properties.opbuff_depth       :=halfband_interpolation.buffer_page_size;
    properties.opbuff_rate        :=halfband_interpolation.output_rate;
    properties.opbuff_thresh      :=properties.opbuff_depth-2;
    properties.clk_per_chan       :=halfband_interpolation.param.clk_per_chan;
    properties.reload_lat         :=0;
    properties.num_macs           :=halfband_interpolation.param.num_macs;
    properties.single_mac         :=halfband_interpolation.param.single_mac;
    properties.base_count         :=halfband_interpolation.param.base_count;
    properties.inter_rate         :=halfband_interpolation.param.inter_rate;
    properties.deci_rate          :=halfband_interpolation.param.deci_rate;
    properties.centre_mac         :=halfband_interpolation.param.centre_mac;
    properties.coef_mem_type      :=halfband_interpolation.param.coef_mem_type;
    properties.data_mem_type      :=halfband_interpolation.param.data_mem_type;
    properties.base_coef_space    :=halfband_interpolation.param.base_coef_space;
    properties.base_data_space    :=halfband_interpolation.param.base_coef_space;
    properties.use_approx         :=halfband_interpolation.use_approx;
    properties.full_parallel      :=halfband_interpolation.param.full_parallel;
  when c_polyphase_pq =>
    if reqs.inter_rate>reqs.deci_rate then
      pq_interpolation            :=define_pq_interpolation(reqs);
      properties.cascade_dly      :=pq_interpolation.latency;
      properties.actual_taps      :=pq_interpolation.param.num_taps_calced;
      properties.reload_taps      :=properties.actual_taps;
      properties.symmetry         :=pq_interpolation.param.symmetry;
      properties.odd_symmetry     :=pq_interpolation.param.odd_symmetry;
      if pq_interpolation.has_input_buffer then
        properties.ipbuff_depth   := pq_interpolation.buffer_page_size;
        properties.reload_lat     := 2+pq_interpolation.input_buffer_lat;
      else
        properties.ipbuff_depth   := 1;
        if pq_interpolation.param.num_filts>1 then
          properties.reload_lat   := 2;
        else
          properties.reload_lat   := 1;
        end if;
      end if;
      properties.clk_per_chan     :=pq_interpolation.param.clk_per_chan;
      properties.num_macs         :=pq_interpolation.param.num_macs;
      properties.single_mac       :=pq_interpolation.param.single_mac;
      properties.base_count       :=pq_interpolation.param.base_count;
      properties.inter_rate       :=pq_interpolation.param.inter_rate;
      properties.deci_rate        :=pq_interpolation.param.deci_rate;
      properties.centre_mac       :=pq_interpolation.param.centre_mac;
      properties.coef_mem_type    :=pq_interpolation.param.coef_mem_type;
      properties.data_mem_type    :=pq_interpolation.param.data_mem_type;
      properties.base_coef_space  :=pq_interpolation.param.base_coef_space;
      properties.base_data_space  :=pq_interpolation.param.base_coef_space;
      properties.use_approx       :=pq_interpolation.use_approx;
      properties.full_parallel    :=pq_interpolation.param.full_parallel;
    else
      pq_decimation               :=define_pq_decimation(reqs);
      properties.actual_taps      :=pq_decimation.param.num_taps_calced;
      properties.reload_taps      :=properties.actual_taps;
      properties.symmetry         :=pq_decimation.param.symmetry;
      properties.odd_symmetry     :=pq_decimation.param.odd_symmetry;
      if pq_decimation.has_output_buffer then
        properties.opbuff_depth   := pq_decimation.buffer_page_size;
        properties.cascade_dly    :=pq_decimation.latency -(pq_decimation.param.base_count*(reqs.inter_rate-1));
      else
        properties.opbuff_depth   := 0;
        properties.cascade_dly    :=pq_decimation.latency-(pq_decimation.param.base_count*(reqs.deci_rate rem reqs.inter_rate))
                                    +pq_decimation.param.base_count;
      end if;
      properties.opbuff_rate      :=pq_decimation.output_rate;
      properties.opbuff_thresh    :=properties.opbuff_depth-1;
      properties.clk_per_chan     :=pq_decimation.param.clk_per_chan;
      properties.reload_lat       :=3;
      properties.num_macs         :=pq_decimation.param.num_macs;
      properties.single_mac       :=pq_decimation.param.single_mac;
      properties.base_count       :=pq_decimation.param.base_count;
      properties.inter_rate       :=pq_decimation.param.inter_rate;
      properties.deci_rate        :=pq_decimation.param.deci_rate;
      properties.centre_mac       :=pq_decimation.param.centre_mac;
      properties.coef_mem_type    :=pq_decimation.param.coef_mem_type;
      properties.data_mem_type    :=pq_decimation.param.data_mem_type;
      properties.base_coef_space  :=pq_decimation.param.base_coef_space;
      properties.base_data_space  :=pq_decimation.param.base_coef_space;
      properties.use_approx       :=pq_decimation.use_approx;
      properties.full_parallel    :=pq_decimation.param.full_parallel;
     end if;
  when others =>
    report "<FIR MODEL> Invalid filter type." severity failure;
  end case;
  properties.full_taps := get_full_taps( reqs.filter_type,
                                         reqs.inter_rate,
                                         reqs.num_taps,
                                         properties.actual_taps,
                                         properties.symmetry);
  if reqs.filter_type = c_decimating_half_band then
    properties.ipbuff_rate := halfband_decimation.base_count_op_rate;
  elsif reqs.filter_type = c_polyphase_pq then
    if reqs.inter_rate>reqs.deci_rate then
      properties.ipbuff_rate := properties.clk_per_chan*reqs.deci_rate/reqs.inter_rate;
    else
      properties.ipbuff_rate := properties.base_count;
    end if;
  else
    properties.ipbuff_rate := properties.clk_per_chan/reqs.inter_rate;
  end if;
  properties.ipbuff_thresh := properties.ipbuff_depth-1;
  report "<FIR MODEL> Required taps     = " & int_to_string(reqs.num_taps);
  report "<FIR MODEL> Actual taps       = " & int_to_string(properties.actual_taps);
  report "<FIR MODEL> Full taps         = " & int_to_string(properties.full_taps);
  report "<FIR MODEL> Reload taps       = " & int_to_string(properties.reload_taps);
  report "<FIR MODEL> Casade delay      = " & int_to_string(properties.cascade_dly);
  report "<FIR MODEL> I/P buffer depth  = " & int_to_string(properties.ipbuff_depth);
  report "<FIR MODEL> I/P buffer rate   = " & int_to_string(properties.ipbuff_rate);
  report "<FIR MODEL> I/P buffer thresh = " & int_to_string(properties.ipbuff_thresh);
  report "<FIR MODEL> O/P buffer depth  = " & int_to_string(properties.opbuff_depth);
  report "<FIR MODEL> O/P buffer rate   = " & int_to_string(properties.opbuff_rate);
  report "<FIR MODEL> O/P buffer thresh = " & int_to_string(properties.opbuff_thresh);
  report "<FIR MODEL> ZPF               = " & int_to_string(properties.zpf);
  report "<FIR MODEL> Clk/Sample/Chan   = " & int_to_string(properties.clk_per_chan);
  report "<FIR MODEL> Rld latency       = " & int_to_string(properties.reload_lat);
  report "<FIR MODEL> Number of MACs    = " & int_to_string(properties.num_macs);
  report "<FIR MODEL> Single MAC?       = " & int_to_string(properties.single_mac);
  report "<FIR MODEL> Base count        = " & int_to_string(properties.base_count);
  report "<FIR MODEL> Interp Rate       = " & int_to_string(properties.inter_rate);
  report "<FIR MODEL> Decimation Rate   = " & int_to_string(properties.deci_rate);
  report "<FIR MODEL> Centre MAC        = " & int_to_string(properties.centre_mac);
  report "<FIR MODEL> Coef Mem Type     = " & int_to_string(properties.coef_mem_type);
  report "<FIR MODEL> Data Mem Type     = " & int_to_string(properties.data_mem_type);
  report "<FIR MODEL> Base Coef Space   = " & int_to_string(properties.base_coef_space);
  report "<FIR MODEL> Base Data Space   = " & int_to_string(properties.base_data_space);
  return properties;
end;
constant properties : t_syst_mac_fir_properties := fir_v3_prop(fir_model_reqs_v3);
constant max_int_width : integer :=  31;
constant maxint        : integer :=    2**(max_int_width-1) - 1;
constant minint        : integer := -1*2**(max_int_width-1);
constant buff_in_depth  : integer := 2*properties.ipbuff_depth;
constant buff_out_depth           : integer := (properties.opbuff_depth);
constant buff_out_depth_pq_extra  : integer := select_integer(0,(properties.opbuff_depth-1)*DECIM_RATE,
                                                                fir_model_reqs_v3.filter_type = c_polyphase_pq and INTERP_RATE<DECIM_RATE and properties.opbuff_depth/=0);
constant sample_delay : integer := ((properties.cascade_dly+properties.clk_per_chan)/properties.clk_per_chan);
constant buffer_fudge : integer := ((properties.full_taps+3)/4);
constant main_buffer_depth : integer := buff_in_depth +
                                        INTERP_RATE*sample_delay  +
                                        properties.zpf*properties.full_taps +
                                        buff_out_depth +
                                        buff_out_depth_pq_extra +
                                        buffer_fudge;
constant sym_padding     : integer := ((properties.full_taps-fir_model_reqs_v3.num_taps)/2);
constant inter_sym_shift : integer:=select_integer(0,fir_model_reqs_v3.inter_rate/2,
                                                  (fir_model_reqs_v3.filter_type=c_interpolating_symmetry or
                                                  (fir_model_reqs_v3.filter_type=c_polyphase_interpolating and properties.symmetry=1))
                                                  and properties.odd_symmetry=1
                                                  and (fir_model_reqs_v3.inter_rate rem 2 >0));
constant pad_offset      : integer := select_integer(0,sym_padding-inter_sym_shift,properties.symmetry=1);
constant rdy_dly_len    : integer := select_integer(
                                     properties.cascade_dly-3,
                                     properties.cascade_dly-4,
                                     properties.opbuff_depth>0);

constant regressor_len  : integer := (properties.full_taps-1)*properties.zpf+1;
constant pointer_start : integer := regressor_len-1;
constant split_accum : boolean :=  fir_model_reqs_v3.accum_width > max_int_width
                               or (fir_model_reqs_v3.data_width+fir_model_reqs_v3.coef_width) > max_int_width;
constant split_cwidth : integer := get_min( (max_int_width-DATA_WIDTH) , COEF_WIDTH );
constant cpages : integer := (COEF_WIDTH+split_cwidth-1)/split_cwidth;
constant signed_output : boolean := ((COEF_TYPE=0) or (DATA_TYPE=0));
constant set_index : integer := pointer_start
                                - select_integer(DECIM_RATE-1,0,
                                                 fir_model_reqs_v3.filter_type = c_polyphase_pq and INTERP_RATE>DECIM_RATE);
constant reload_pointer_max : integer := 9*COEF_RELOAD;
constant reload_dly_len : integer := properties.reload_lat+3
                                     - select_integer(1,0,buff_in_depth=0);
constant alter_input_rate:boolean:=(fir_model_reqs_v3.filter_type=c_decimating_half_band) and
                                   (properties.base_count+properties.single_mac = properties.ipbuff_rate) and
                                   ((clks_per_samp_int*2)/fir_model_reqs_v3.num_channels > (clks_per_samp_int/fir_model_reqs_v3.num_channels)*2);
type t_coefficients     is array (0 to   properties.full_taps-1) of integer;
type t_sets             is array (0 to             DECIM_RATE-1) of integer;
type t_reload_coeffs    is array (0 to properties.reload_taps-1) of integer;
type t_split_coeff      is array (0 to                 cpages-1) of integer;
type t_coeff_pages      is array (0 to                 cpages-1) of t_coefficients;
type t_coeff_array      is array (0 to              NUM_FILTS-1) of t_coeff_pages;
type t_reload_array     is array (0 to       reload_pointer_max) of t_coeff_array;
type t_input_buffer     is array (0 to          buff_in_depth-1) of integer;
type t_ipbuff_array     is array (0 to                        2) of t_input_buffer;
type t_main_buffer      is array (0 to      main_buffer_depth-1) of integer;
type t_buffer_array     is array (0 to           NUM_CHANNELS-1) of t_main_buffer;
type t_regressor        is array (0 to          regressor_len-1) of integer;
type t_pointers         is array (0 to           NUM_CHANNELS-1) of integer;
type t_rdy_dly          is array (0 to            rdy_dly_len-1) of std_logic;
type t_reload_pointer   is array (0 to              NUM_FILTS-1) of integer;
type t_push_regressor_dly   is array (0 to     reload_dly_len-1) of boolean;
type t_filter_channel_dly   is array (0 to     reload_dly_len-1) of integer;
type t_filter_data_in_dly   is array (0 to     reload_dly_len-1) of integer;
type t_filter_fsel_dly      is array (0 to     reload_dly_len-1) of integer;
type t_ipbuff_channel1_dly  is array (0 to     reload_dly_len-1) of integer;
type t_ipbuff_data_out1_dly is array (0 to     reload_dly_len-1) of integer;
type t_ipbuff_fsel_out1_dly is array (0 to     reload_dly_len-1) of integer;
constant rld_order_max : integer := 2*properties.actual_taps;
type t_rld_index is array (              2 downto 0) of integer;
type t_rld_order is array (rld_order_max-1 downto 0) of t_rld_index;
function get_reload_indices (
                            symmetry            : integer;
                            oddsymmetry         : integer;
                            num_macs            : integer;
                            taps_calced         : integer;
                            coefmemspace        : integer;
                            midtap              : integer;
                            num_taps            : integer;
                            inter_rate_ip       : integer;
                            deci_rate_ip        : integer;
                            single_mac          : integer;
                            filter_type_value   : integer;
                            data_mem_type       : integer;
                            base_count          : integer;
                            col_mode            : integer;
                            col1_ln             : integer;
                            coln_ln             : integer;
                            sysgen_mode         : boolean
                            ) return t_rld_order is
  constant deci_rate            : integer := deci_rate_ip  / get_gcd( inter_rate_ip, deci_rate_ip );
  constant inter_rate           : integer := inter_rate_ip / get_gcd( inter_rate_ip, deci_rate_ip );

  variable coefficients         : t_rld_order := (others=>(others=>-1));
  variable mod_coefficients     : t_rld_order := (others=>(others=>-1));
  variable mac_coefficients     : t_rld_order := (others=>(others=>-1));
  variable halfband_smac        : integer     := 0;
  variable tap_difference       : integer     := 0;
  variable local_req_num_taps   : integer;
  variable tap                  : integer     := 0;
  variable phase                : integer     := 0;
  variable phase_last_mod       : integer     := 0;
  variable phase_offset         : integer     := 0;
  variable inter_sym_loop_len   : integer;
  variable num_cols             : integer;
  variable curr_col             : integer;
  variable last_col             : integer;
  variable remaining_macs       : integer;
  variable new_mac_pos          : integer;
  variable local_deci_rate      : integer;
  variable mac_mem_pos          : integer;
  variable mod_phase            : integer;
  variable phase_loop_end       : integer;
  variable phase_loop_dir       : integer;
  variable pq_deci_start_phase  : integer;
  variable pq_deci_phase        : integer;
  variable pq_deci_count_ips    : integer;
  variable tap_pos              : integer;
  variable write_i              : integer;
  variable read_val_1           : integer;
  variable read_val_2           : integer;
  variable halfband_centre_tap  : integer;


  type     t_pq_deci_first_phase  is array (0 to deci_rate_ip-1) of integer;
  variable pq_deci_first_phase  : t_pq_deci_first_phase := (others=>0);

begin
  if ( single_mac=1 and ( filter_type_value = c_halfband_transform or
                          filter_type_value = c_decimating_half_band or
                          filter_type_value = c_interpolating_half_band ) ) then
    halfband_smac := 1;
  end if;
  if symmetry = 1 then
    local_req_num_taps := num_taps/2;
    if ( num_taps mod 2 ) > 0 then
      local_req_num_taps := local_req_num_taps+1;
    end if;
    tap_difference := taps_calced - local_req_num_taps ;
    if ( filter_type_value = c_interpolating_symmetry and
         oddsymmetry = 1 and (inter_rate mod 2 ) > 0 ) then
      tap_difference := tap_difference - integer(inter_rate/2) ;
    end if;
  else
    local_req_num_taps := num_taps;
  end if;
  for i in 0 to rld_order_max-1 loop
    if sysgen_mode then
      coefficients(i)(0) := i;
    else
      if symmetry=1 then
        if i >= tap_difference then
          coefficients(i)(0) := i - tap_difference;
        else
          coefficients(i)(0) := -1;
        end if;
      else
        if i >= num_taps then
          coefficients(i)(0) := -1;
        else
          coefficients(i)(0) := i;
        end if;
      end if;
    end if;
  end loop;
  if filter_type_value = c_interpolating_symmetry then
    tap := 0;

    while tap <= taps_calced-1 loop
      phase_last_mod := 0;
      phase_offset   := 0;
      if (inter_rate mod 2)/=0 then
        mod_coefficients(tap)(0) := coefficients(tap+integer(inter_rate/2))(0);
        phase_offset := 1;
      end if;
      phase := 0;
      inter_sym_loop_len := inter_rate/2;

      if (inter_rate mod 2)=0 and oddsymmetry=1 then
        mod_coefficients(tap)             (0) := coefficients(tap+integer(inter_rate/2)-1)(0);
        mod_coefficients(tap+inter_rate-1)(0) := coefficients(tap+integer(inter_rate-1))  (0);
        phase_offset       := 1;
        phase_last_mod     := 1;
        inter_sym_loop_len := (inter_rate-2)/2;
      end if;

      for phase_loop in 0 to inter_sym_loop_len-1 loop
        write_i := tap + (2*phase) + 1 + phase_offset;

        read_val_1 := coefficients(tap+phase)             (0);
        read_val_2 := coefficients(inter_rate+tap-1-phase_last_mod-phase)(0);
        mod_coefficients(write_i)(0) := read_val_1;
        mod_coefficients(write_i)(1) := read_val_2;
        write_i := tap + (2*phase) + phase_offset;
        mod_coefficients(write_i)(0) := read_val_1;
        mod_coefficients(write_i)(2) := read_val_2;
        phase := phase+1;

      end loop;
      phase := 0;
      while phase <= (inter_rate-1) loop
        coefficients(tap+phase) := mod_coefficients(tap+phase);
        phase := phase+1;
      end loop;
      tap := tap + inter_rate;
    end loop;

  end if;

  if false then
    num_cols := 1 + integer((num_macs-col1_ln)/coln_ln);
    if ((num_macs-col1_ln) mod coln_ln) > 0 then
      num_cols := num_cols+1;
    end if;
  else
    num_cols := 1;
  end if;
  if ((num_macs - col1_ln) mod coln_ln) > 0 then
    last_col := (num_macs - col1_ln) mod coln_ln;
  else
    last_col := coln_ln;
  end if;
  curr_col := 1;

  if ( num_cols > 1 ) then
    remaining_macs := col1_ln;
  else
    remaining_macs := num_macs;
  end if;
  new_mac_pos := num_macs-1;
  if ( filter_type_value = c_decimating_half_band or
       filter_type_value = c_interpolating_half_band ) then
    local_deci_rate := 2;
  elsif filter_type_value = c_interpolated_transform then
    local_deci_rate := 1;
  else
    local_deci_rate := deci_rate;
  end if;
  for mac in 0 to num_macs-1 loop

    mac_mem_pos := 0;
    pq_deci_first_phase := (others=>0);
    phase               := 0;
    mod_phase           := 0;
    phase_loop_end      := get_max(local_deci_rate,inter_rate) - 1;
    phase_loop_dir      := 1;
    pq_deci_start_phase := 0;
    if ( filter_type_value = c_polyphase_decimating or
       ( filter_type_value = c_polyphase_pq and inter_rate<deci_rate ) ) then
      phase                 := phase_loop_end;
      mod_phase             := phase_loop_end;
      pq_deci_start_phase   := inter_rate-1;
      phase_loop_end        := 0;
      phase_loop_dir        := -1;
    end if;
    while ( phase /= phase_loop_end+phase_loop_dir ) loop
      if ( filter_type_value = c_polyphase_pq and inter_rate<deci_rate ) then
        pq_deci_phase     := 0;
        pq_deci_count_ips := 0;
        for pq_reorder in 0 to inter_rate-1 loop

          if pq_deci_phase = 0 then
            tap_pos := mac * coefmemspace
                     + ((mod_phase - pq_reorder) mod deci_rate);
          else
            tap_pos := mac * coefmemspace
                     + ((mod_phase - pq_reorder) mod deci_rate)
                     + (inter_rate - ((pq_deci_phase*inter_rate)/deci_rate))*deci_rate;
          end if;
          pq_deci_first_phase(mod_phase) := 1;
          while tap_pos < (mac+1)*coefmemspace loop
            if (  data_mem_type /= c_srl16 and
                  pq_deci_first_phase((mod_phase-pq_reorder) mod deci_rate) = 0 ) then
              mac_coefficients(mac_mem_pos) := coefficients ( mac*base_count*inter_rate*deci_rate
                                                              + ( ( tap_pos-(base_count*inter_rate*deci_rate)
                                                                + ((base_count-1)*inter_rate*deci_rate) )
                                                              mod (base_count*inter_rate*deci_rate) ) );
            else
              mac_coefficients(mac_mem_pos) := coefficients(tap_pos);
            end if;
            mac_mem_pos := mac_mem_pos + 1;
            tap_pos     := tap_pos + ( inter_rate * deci_rate );
          end loop;
          while pq_deci_count_ips /= pq_reorder+1 loop
            pq_deci_count_ips := (pq_deci_count_ips+inter_rate) mod deci_rate;
            pq_deci_phase     := (pq_deci_phase+1)              mod deci_rate;
          end loop;
        end loop;
      elsif ( filter_type_value /= c_hilbert_transform and
              filter_type_value /= c_interpolated_transform and
              filter_type_value /= c_halfband_transform and
              filter_type_value /= c_decimating_half_band and
              filter_type_value /= c_interpolating_half_band ) then

        if filter_type_value = c_polyphase_pq then
          tap_pos := (mac*coefmemspace)+mod_phase;
        else
          tap_pos := (mac*coefmemspace)+phase;
        end if;

        while tap_pos < (mac+1)*coefmemspace loop
          mac_coefficients(mac_mem_pos) := coefficients(tap_pos);
          mac_mem_pos := mac_mem_pos+1;
          tap_pos := tap_pos + get_max(local_deci_rate,inter_rate);
        end loop;
      else
        tap_pos := ( mac * coefmemspace * local_deci_rate ) + phase;
        while ( tap_pos < (mac+1) * coefmemspace * local_deci_rate ) and phase=0 loop
          mac_coefficients(mac_mem_pos) := coefficients(tap_pos);
          mac_mem_pos := mac_mem_pos+1;
          tap_pos := tap_pos + local_deci_rate;
        end loop;
      end if;
      phase := phase + phase_loop_dir;
      if inter_rate > deci_rate then
        mod_phase := (mod_phase+deci_rate) mod inter_rate;
      else
        mod_phase := (mod_phase-inter_rate) mod deci_rate;
      end if;
    end loop;
    for pos in 0 to coefmemspace-1 loop
      write_i := (new_mac_pos * coefmemspace) + pos;
      mod_coefficients(write_i) := mac_coefficients(pos);
    end loop;
    if remaining_macs = 1 then
      curr_col := curr_col+1;
      if curr_col = num_cols then
        remaining_macs := last_col;
      else
        remaining_macs := coln_ln;
      end if;
      new_mac_pos := new_mac_pos - remaining_macs;
    else
      remaining_macs := remaining_macs-1;
      if (curr_col mod 2) = 1 then
        new_mac_pos := new_mac_pos-1;
      else
        new_mac_pos := new_mac_pos+1;
      end if;
    end if;
  end loop;
  if (  filter_type_value = c_halfband_transform or
        filter_type_value = c_decimating_half_band or
        filter_type_value = c_interpolating_half_band ) then

    if num_macs = midtap then
      halfband_centre_tap := coefficients(tap_difference+local_req_num_taps-1)(0);
    else
      halfband_centre_tap := coefficients(num_taps/2)(0);
    end if;
    write_i := num_macs * coefmemspace;
    if halfband_smac=1 then
      write_i := coefmemspace-1;
    end if;
    mod_coefficients(write_i)(0) := halfband_centre_tap;

  end if;
  if ( filter_type_value = c_halfband_transform or
       filter_type_value = c_decimating_half_band or
       filter_type_value = c_interpolating_half_band ) then
    mod_coefficients(rld_order_max-1 downto (taps_calced/2 + 1)) := (others=>(others=>-1));
  elsif filter_type_value = c_hilbert_transform then
    mod_coefficients(rld_order_max-1 downto taps_calced/2) := (others=>(others=>-1));
  end if;

  return mod_coefficients;
end;
constant sysgen_mode  : boolean := true;
constant reload_order : t_rld_order := get_reload_indices (
                                       properties.symmetry,
                                       properties.odd_symmetry,
                                       properties.num_macs,
                                       properties.actual_taps,
                                       properties.base_coef_space,
                                       properties.centre_mac,
                                       fir_model_reqs_v3.num_taps,
                                       properties.inter_rate,
                                       properties.deci_rate,
                                       properties.single_mac,
                                       fir_model_reqs_v3.filter_type,
                                       properties.data_mem_type,
                                       properties.base_count,
                                       fir_model_reqs_v3.col_mode,
                                       fir_model_reqs_v3.col_1st_len,
                                       fir_model_reqs_v3.col_wrap_len,
                                       sysgen_mode
                                       );
function num_last_value_indexes ( reqs          : t_reqs;
                                  properties    : t_syst_mac_fir_properties ) return integer is
  variable num_indexes : integer;
begin
  case reqs.filter_type is
  when c_single_rate =>
    if properties.full_parallel=1 then
      if properties.symmetry=0 or properties.odd_symmetry=1 then
        return 1;
      else
        return 2;
      end if;
    else
      return (properties.num_macs*(2**properties.symmetry))
            -properties.odd_symmetry;
    end if;
  when c_polyphase_decimating =>
    num_indexes := (properties.num_macs*(2**properties.symmetry));

  when c_polyphase_interpolating =>

    if properties.full_parallel=1 then
      return 1;
    else
      return properties.num_macs;
    end if;
  when c_interpolating_symmetry =>
    if reqs.inter_rate=2 and odd_symmetry=1 then
      if properties.full_parallel=1 then
        return 2;
      else
        return properties.num_macs*(2**properties.symmetry);
      end if;
    else
      num_indexes := 1;
    end if;
  when c_hilbert_transform =>
    if properties.full_parallel=1 then
      return 2;
    else
      return properties.num_macs*(2**properties.symmetry);
    end if;

  when c_interpolated_transform =>
    if properties.full_parallel=1 then
      if  properties.symmetry=0 or properties.odd_symmetry=1 then
        return 1;
      else
        return 2;
      end if;
    else
      return (properties.num_macs*(2**properties.symmetry))-properties.odd_symmetry;
    end if;

  when c_halfband_transform|c_decimating_half_band =>
    if reqs.filter_type=c_halfband_transform and properties.full_parallel=1 then
      num_indexes := 1;
    elsif properties.single_mac=0 then
      num_indexes := (properties.num_macs*(2**properties.symmetry));
    else
      num_indexes := 1;
    end if;

  when c_interpolating_half_band =>
    num_indexes := (properties.num_macs*(2**properties.symmetry));

  when c_polyphase_pq =>
    if properties.full_parallel=1 and reqs.inter_rate>reqs.deci_rate then
      return 1;
    else
      return properties.num_macs;
    end if;

  when others =>
    report "FIR MODEL: unsupported filter type" severity failure;
    num_indexes := 1;
  end case;

  return num_indexes;

end;
type t_last_value_indexes is
record
  phase : integer;
  list  : t_int_array( num_last_value_indexes(fir_model_reqs_v3,properties)-1 downto 0);
end record;
function last_value_indexes_iterate ( reqs          : t_reqs;
                                      properties    : t_syst_mac_fir_properties;
                                      last_indexes  : t_last_value_indexes ) return t_last_value_indexes is
  variable indexes: t_last_value_indexes:=last_indexes;
  variable index_count: integer;
begin
  case reqs.filter_type is

  when c_single_rate | c_interpolated_transform =>
    if properties.full_parallel=1 then
      if properties.symmetry=0 or properties.odd_symmetry=1 then
        indexes.list(0):=properties.num_macs-1;
      else
        indexes.list(0):=properties.num_macs-1;
        indexes.list(1):=properties.num_macs;
      end if;
    else
      index_count:=properties.base_count-1;
      for i in 0 to indexes.list'HIGH loop
        indexes.list(i):=index_count;
        if properties.symmetry=1 and i=indexes.list'HIGH/2 then
          if properties.odd_symmetry=1 then
            index_count:=index_count+properties.base_count;
          else
            index_count:=index_count+1;
          end if;
        else
          index_count:=index_count+properties.base_count;
        end if;
      end loop;
    end if;

  when c_polyphase_decimating =>
    index_count:=(properties.base_count-1)*properties.deci_rate;
    for i in 0 to indexes.list'HIGH loop
      indexes.list(i):=index_count;
      if properties.symmetry=1 and i=indexes.list'HIGH/2 then
        if properties.odd_symmetry=1 then
          index_count:=index_count+((2*properties.deci_rate)-2);
        else
          index_count:=index_count+((2*properties.deci_rate)-1);
        end if;
      else
        index_count:=index_count+(properties.base_count*properties.deci_rate);
      end if;
    end loop;
  when c_polyphase_interpolating =>
    if indexes.phase/=properties.inter_rate-1 then
      indexes.phase:=indexes.phase+1;
    else
      indexes.phase:=0;
    end if;
    if properties.full_parallel=1 then
      indexes.list(0):=((properties.num_macs-1)*properties.inter_rate)+indexes.phase;
    else
      index_count:=((properties.base_count-1)*properties.inter_rate)+indexes.phase;
      for i in 0 to indexes.list'HIGH loop
        indexes.list(i):=index_count;
        index_count:=index_count+(properties.base_count*properties.inter_rate);
      end loop;
    end if;
  when c_interpolating_symmetry =>
    if (reqs.inter_rate=2 and odd_symmetry=1) then
      if indexes.phase/=properties.inter_rate-1 then
        indexes.phase:=indexes.phase+1;
      else
        indexes.phase:=0;
      end if;
      if properties.full_parallel=1 then
        if indexes.phase=0 then
          indexes.list(0):=(properties.num_macs-1)*2;
          indexes.list(1):=((properties.num_macs-1)*2)+2;
        else
          indexes.list(0):=((properties.num_macs-1)*2)+1;
          indexes.list(1):=properties.actual_taps*2;
        end if;
      else
        index_count:=((properties.base_count-1)*properties.inter_rate)+indexes.phase;
        for i in 0 to indexes.list'HIGH loop
          indexes.list(i):=index_count;

          if i=indexes.list'HIGH/2 then
            if indexes.phase=0 then
              index_count:=index_count+2;
            else
              index_count:=index_count+(properties.base_count*properties.inter_rate);
            end if;
          else
            index_count:=index_count+(properties.base_count*properties.inter_rate);
          end if;
        end loop;
      end if;
    end if;
  when c_hilbert_transform =>
    if properties.full_parallel=1 then
      indexes.list(0):=(properties.num_macs-1)*2;
      indexes.list(1):=((properties.num_macs-1)*2)+2;
    else
      index_count:=2*properties.base_count-2;
      for i in 0 to indexes.list'HIGH loop
        indexes.list(i):=index_count;
        if i=indexes.list'HIGH/2 then
          index_count:=index_count+2;
        else
          index_count:=index_count+(2*properties.base_count);
        end if;
      end loop;
    end if;

  when c_halfband_transform | c_decimating_half_band =>
    if reqs.filter_type=c_halfband_transform and properties.full_parallel=1 then
      indexes.list(0):=2*properties.num_macs-1;
    elsif properties.single_mac=0 then
      index_count:=2*properties.base_count-2;
      for i in 0 to indexes.list'HIGH loop
        indexes.list(i):=index_count;
        if i=indexes.list'HIGH/2 then
          index_count:=index_count+2;
        else
          index_count:=index_count+(2*properties.base_count);
        end if;
      end loop;
    else
      if reqs.filter_type=c_halfband_transform then
        indexes.list(0):=2*properties.base_count-3;
      else
        indexes.list(0):=2*properties.base_count-1;
      end if;
    end if;

  when c_interpolating_half_band =>
    if indexes.phase=0 then
      indexes.phase:=1;
      index_count:=2*properties.base_count-1;
    else
      indexes.phase:=0;
      index_count:=2*properties.base_count-2;
    end if;

    for i in 0 to indexes.list'HIGH loop
      if indexes.phase=1 and
         (i=indexes.list'HIGH/2 or i=indexes.list'HIGH) then
        indexes.list(i):=2*properties.base_count-1;
      else
        indexes.list(i):=index_count;
      end if;
      if i=indexes.list'HIGH/2 then
        if indexes.phase=0 then
          index_count:=index_count+2;
        else
          index_count:=2*properties.base_count-1;
        end if;
      else
        index_count:=index_count+(2*properties.base_count);
      end if;
    end loop;
  when c_polyphase_pq =>
    if reqs.inter_rate>reqs.deci_rate then
      indexes.phase:=(indexes.phase+reqs.deci_rate) mod reqs.inter_rate;
      if properties.full_parallel=1 then
        indexes.list(0):=((properties.num_macs-1)*properties.inter_rate)+indexes.phase;
      else
        index_count:=((properties.base_count-1)*properties.inter_rate)+indexes.phase;
        for i in 0 to indexes.list'HIGH loop
          indexes.list(i):=index_count;
          index_count:=index_count+(properties.base_count*properties.inter_rate);
        end loop;
      end if;
    else
      indexes.phase:=(indexes.phase+1) mod reqs.inter_rate;
      index_count:= (indexes.phase*reqs.deci_rate)+( (properties.base_count-1)*reqs.inter_rate*reqs.deci_rate);
      for i in 0 to indexes.list'HIGH loop
        indexes.list(i):=index_count;
        index_count:=index_count+(properties.base_count*reqs.inter_rate*reqs.deci_rate);
      end loop;
    end if;
  when others =>
    report "FIR MODEL: unsupported filter type" severity failure;
  end case;


  return indexes;

end;
function last_value_indexes ( reqs          : t_reqs;
                              properties    : t_syst_mac_fir_properties ) return t_last_value_indexes is
  variable indexes: t_last_value_indexes;
  variable pq_deci_interations,
           pq_deci_phase_step,
           pq_deci_phase : integer:=0;
begin
  if reqs.filter_type=c_polyphase_pq then
    if reqs.inter_rate>reqs.deci_rate then
      indexes.phase:=(-1*reqs.deci_rate) mod reqs.inter_rate;
    else

      while pq_deci_interations/=1 loop
        pq_deci_interations:=(pq_deci_interations+reqs.inter_rate) mod reqs.deci_rate;
        pq_deci_phase_step:=pq_deci_phase_step+1;
      end loop;

      for phase in 0 to reqs.deci_rate-1 loop
        if phase mod reqs.inter_rate = 0  then
          pq_deci_phase:=0;
        else
          pq_deci_phase:=(pq_deci_phase+pq_deci_phase_step) mod reqs.deci_rate;
        end if;
      end loop;
      if pq_deci_phase=0 then
        indexes.phase:=0;
      else
        indexes.phase:=reqs.inter_rate - ( (pq_deci_phase*reqs.inter_rate)/reqs.deci_rate );
      end if;
      indexes.phase:=(indexes.phase-1) mod reqs.inter_rate;
    end if;
  else
    indexes.phase:=reqs.inter_rate-1;
  end if;

  return last_value_indexes_iterate( reqs,properties,indexes);
end;
function rounding ( acc, accum_width, output_width, round_mode, acc_last_ip : integer;
                    use_approx : boolean
                  ) return integer is
  variable result       : integer := 0;
  variable midpoint     : boolean := false;
  constant half         : integer := 2**(get_max(0,(accum_width-output_width-1)));
  constant one          : integer := 2*half;
  variable last_ip      : integer := acc_last_ip;
  variable decision_acc : integer := acc;
begin
  if use_approx then
    decision_acc := acc - last_ip + (half-1);
  end if;

  case round_mode is
    when c_full_precision      => result := acc;
    when c_truncate_lsbs       => if ( acc < 0 ) then  result := (acc-one +1)/one ;
                                  else                 result := (acc       )/one ;
                                  end if;
    when c_non_symmetric_down  => if ( acc < 0 ) then  result := (acc-half  )/one ;
                                  else                 result := (acc+half-1)/one ;
                                  end if;
    when c_non_symmetric_up    => if ( acc < 0 ) then  result := (acc-half+1)/one ;
                                  else                 result := (acc+half  )/one ;
                                  end if;
    when c_symmetric_zero      => if ( decision_acc < 0 ) then
                                    if ( acc < 0 ) then  result := (acc-half+1)/one ;
                                    else                 result := (acc+half  )/one ;
                                    end if;
                                  else
                                    if ( acc < 0 ) then  result := (acc-half  )/one ;
                                    else                 result := (acc+half-1)/one ;
                                    end if;
                                  end if;
    when c_symmetric_inf       => if ( decision_acc < 0 ) then
                                    if ( acc < 0 ) then  result := (acc-half  )/one ;
                                    else                 result := (acc+half-1)/one ;
                                    end if;
                                  else
                                    if ( acc < 0 ) then  result := (acc-half+1)/one ;
                                    else                 result := (acc+half  )/one ;
                                    end if;
                                  end if;
    when c_convergent_even     => midpoint := (acc mod one) = half;
                                  if ( acc < 0 ) then  result := (acc-half+1)/one ;
                                  else                 result := (acc+half  )/one ;
                                  end if;
                                  if midpoint and (result mod 2) = 1 then
                                    result := result-1;
                                  end if;
    when c_convergent_odd      => midpoint := (acc mod one) = half;
                                  if ( acc < 0 ) then  result := (acc-half  )/one ;
                                  else                 result := (acc+half-1)/one ;
                                  end if;
                                  if midpoint and (result mod 2) = 0 then
                                    result := result+1;
                                  end if;
    when others                => report "Unsupported rounding mode specified!";
  end case;

  return result;
end;
function rounding ( acc : unsigned;
                    accum_width, result_width, round_mode : integer;
                    acc_last_ip : unsigned;
                    use_approx : boolean
                  ) return unsigned is
  variable acc_temp     : unsigned( accum_width-1 downto 0) := (others=>'0');
  variable result       : unsigned(result_width-1 downto 0) := (others=>'0');
  variable half         : unsigned( accum_width-1 downto 0) := (others=>'0');
  variable one          : unsigned( accum_width-1 downto 0) := (others=>'0');
  variable midpoint     : boolean := false;
  variable decision_acc : unsigned( accum_width-1 downto 0) := acc;
begin

  if accum_width-result_width>0 then
    half(accum_width-result_width-1):='1';
    one (accum_width-result_width)  :='1';
  end if;
  if use_approx then
    decision_acc := acc - acc_last_ip + (half-1);
  end if;
  case round_mode is
    when c_full_precision      => result   := acc;
    when c_truncate_lsbs       => result   := acc(accum_width-1 downto accum_width-result_width);
    when c_non_symmetric_down  => acc_temp := acc + half - 1;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_non_symmetric_up    => acc_temp := acc + half ;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_symmetric_zero      => acc_temp := acc + half - 1 + select_integer(0,1,decision_acc(accum_width-1)='1');
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_symmetric_inf       => acc_temp := acc + half - 1 + select_integer(0,1,decision_acc(accum_width-1)='0');
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_convergent_even     => midpoint := (acc mod one) = half;
                                  acc_temp := acc + half ;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
                                  if midpoint then
                                    result(0) := '0';
                                  end if;
    when c_convergent_odd      => midpoint := (acc mod one) = half;
                                  acc_temp := acc + half -1;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
                                  if midpoint then
                                    result(0) := '1';
                                  end if;
    when others                => report "Unsupported rounding mode specified!";
  end case;

  return result;

end;
function rounding ( acc : signed;
                    accum_width, result_width, round_mode : integer;
                    acc_last_ip : signed;
                    use_approx : boolean
                  ) return signed is
  variable acc_temp     : signed( accum_width-1 downto 0) := acc;
  variable result       : signed(result_width-1 downto 0) := (others=>'0');
  variable half         : signed( accum_width-1 downto 0) := (others=>'0');
  variable one          : signed( accum_width-1 downto 0) := (others=>'0');
  variable midpoint     : boolean := false;
  variable decision_acc : signed( accum_width-1 downto 0) := acc;
begin
  if accum_width-result_width>0 then
    half(accum_width-result_width-1):='1';
    one (accum_width-result_width)  :='1';
  end if;
  if use_approx then
    decision_acc := acc - acc_last_ip + (half-1);
  end if;
  case round_mode is
    when c_full_precision      => result   := acc_temp;
    when c_truncate_lsbs       => result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_non_symmetric_down  => acc_temp := acc + half - 1;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_non_symmetric_up    => acc_temp := acc + half ;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_symmetric_zero      => acc_temp := acc + half - 1 + select_integer(0,1,decision_acc(accum_width-1)='1');
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_symmetric_inf       => acc_temp := acc + half - 1 + select_integer(0,1,decision_acc(accum_width-1)='0');
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
    when c_convergent_even     => midpoint := (acc mod one) = half;
                                  acc_temp := acc + half ;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
                                  if midpoint then
                                    result(0) := '0';
                                  end if;
    when c_convergent_odd      => midpoint := (acc mod one) = half;
                                  acc_temp := acc + half - 1 ;
                                  result   := acc_temp(accum_width-1 downto accum_width-result_width);
                                  if midpoint then
                                    result(0) := '1';
                                  end if;
    when others                => report "Unsupported rounding mode specified!";
  end case;

  return result;

end;
function fir ( c            : t_coefficients;
               d            : t_regressor;
               taps,
               zpf          : integer;
               round        : boolean:=false;
               last_indexes : t_last_value_indexes:=(phase=>0,list=>(others=>0));
               use_indexes  : boolean:=false
             ) return integer is
  variable acc         : integer := 0;
  variable acc_last_ip : integer := 0;
  variable temp        : integer := 0;
begin
  if not use_indexes then
    for i in 0 to (taps-1) loop
      acc := acc + d(i*zpf) * c(taps-1-i);
    end loop;
  else
    for i in 0 to last_indexes.list'HIGH loop
      if last_indexes.list(i) <= c'HIGH then
        acc := acc + c(last_indexes.list(i)) * d((taps-1-last_indexes.list(i))*zpf);
      end if;
    end loop;
  end if;
  if round then
    if properties.use_approx then
      acc_last_ip := fir(c,d,taps,zpf,false,last_indexes,true);
    end if;
    temp := rounding ( acc, ACCUM_WIDTH, OUTPUT_WIDTH, ROUND_MODE, acc_last_ip, properties.use_approx );
    return temp;
  else
    return acc;
  end if;
end;
function fir_ovfl_unsigned ( c            : t_coefficients;
                             d            : t_regressor;
                             taps,
                             zpf,
                             accum_width : integer;
                             last_indexes : t_last_value_indexes:=(phase=>0,list=>(others=>0));
                             use_indexes  : boolean:=false
                           ) return unsigned is

  variable inc      : natural  := 0;
  variable acc      : natural  := 0;
  variable acc_ovfl : natural  := 0;
  variable result   : unsigned(accum_width-1 downto 0) := (others=>'0');
begin
  if not use_indexes then
    for i in 0 to (taps-1) loop
      inc := d(i*zpf) * c(taps-1-i);
      if inc>(maxint-acc) then
        acc      := acc+inc-(maxint+1);
        acc_ovfl := acc_ovfl+1;

      else
        acc := acc+inc;
      end if;

    end loop;
  else
    for i in 0 to last_indexes.list'HIGH loop
      if last_indexes.list(i) <= c'HIGH then
        inc := c(last_indexes.list(i)) * d((taps-1-last_indexes.list(i))*zpf);

        if inc>(maxint-acc) then
          acc      := acc+inc-(maxint+1);
          acc_ovfl := acc_ovfl+1;

        else
          acc := acc+inc;
        end if;
      end if;
    end loop;
  end if;
    result((max_int_width-1)-1 downto 0)           := to_unsigned(acc, (max_int_width-1));
    result(accum_width-1 downto (max_int_width-1)) := to_unsigned(acc_ovfl, accum_width-(max_int_width-1));
  return result;
end;
function fir_ovfl_signed ( c            : t_coefficients;
                           d            : t_regressor;
                           taps,
                           zpf,
                           accum_width : integer;
                           last_indexes : t_last_value_indexes:=(phase=>0,list=>(others=>0));
                           use_indexes  : boolean:=false
                         ) return signed is

  variable inc      : integer  := 0;
  variable acc      : natural := 0;
  variable acc_ovfl : integer  := 0;
  variable result   : signed(accum_width-1 downto 0) := (others=>'0');
begin

  if not use_indexes then
    for i in 0 to (taps-1) loop
      inc  := d(i*zpf) * c(taps-1-i);

      if inc>(maxint-acc) then
        acc      := acc+inc-(maxint+1);
        acc_ovfl := acc_ovfl+1;

      elsif (acc+inc)<0 then
        acc      := acc+inc+(maxint+1);
        acc_ovfl := acc_ovfl-1;

      else
        acc := acc+inc;
      end if;

    end loop;
  else
    for i in 0 to last_indexes.list'HIGH loop
      if last_indexes.list(i) <= c'HIGH then
        inc  := c(last_indexes.list(i)) * d((taps-1-last_indexes.list(i))*zpf);

        if inc>(maxint-acc) then
          acc      := acc+inc-(maxint+1);
          acc_ovfl := acc_ovfl+1;

        elsif (acc+inc)<0 then
          acc      := acc+inc+(maxint+1);
          acc_ovfl := acc_ovfl-1;

        else
          acc := acc+inc;
        end if;
      end if;
    end loop;
  end if;

    result((max_int_width-1)-1 downto 0)           := signed(to_unsigned(acc, (max_int_width-1)));
    result(accum_width-1 downto (max_int_width-1)) := to_signed(acc_ovfl, accum_width-(max_int_width-1));

  return result;
end;
function split_fir_unsigned ( c            : t_coeff_pages;
                              d            : t_regressor;
                              cpages,
                              taps,
                              zpf,
                              coef_width,
                              split_cwidth,
                              accum_width,
                              result_width : integer;
                              round : boolean := false;
                              last_indexes : t_last_value_indexes:=(phase=>0,list=>(others=>0));
                              use_indexes: boolean:=false
                            ) return unsigned is

  variable inc1         : unsigned( accum_width-1 downto 0) := (others=>'0');
  variable inc2         : unsigned( accum_width-1 downto 0) := (others=>'0');
  variable acc          : unsigned( accum_width-1 downto 0) := (others=>'0');
  variable result       : unsigned(result_width-1 downto 0) := (others=>'0');
  variable read_width   : integer := split_cwidth;
  variable remaining    : integer := coef_width;
  variable acc_last_ip  : unsigned( accum_width-1 downto 0) := (others=>'0');
begin
  for i in 0 to cpages-1 loop
    read_width := get_min(split_cwidth,remaining);
    if (accum_width>coef_width-remaining+max_int_width-(DATA_TYPE*COEF_TYPE)) then
      inc1 := fir_ovfl_unsigned ( c(i), d, taps, zpf, accum_width, last_indexes, use_indexes );
      inc2 := (others=>'0');
      inc2(accum_width-1 downto coef_width-remaining) := inc1(accum_width-1-(coef_width-remaining) downto 0) ;
    else
      inc1 := to_unsigned( fir( c(i), d, taps, zpf, false, last_indexes ,use_indexes) , accum_width );
      inc2 := (others=>'0');
      inc2(accum_width-1 downto coef_width-remaining) := inc1(accum_width-1-(coef_width-remaining) downto 0) ;
    end if;
    acc := acc + inc2;
    remaining := remaining - read_width;
  end loop;
  if round then
    if properties.use_approx then
      acc_last_ip := split_fir_unsigned(c,d,cpages,taps,zpf,coef_width,split_cwidth,accum_width,accum_width,false,last_indexes,true);
    end if;
    result := rounding ( acc, accum_width, result_width, ROUND_MODE, acc_last_ip, properties.use_approx  );
  else
    result := acc;
  end if;

  return result;

end;
function split_fir_signed ( c            : t_coeff_pages;
                            d            : t_regressor;
                            cpages,
                            taps,
                            zpf,
                            coef_width,
                            split_cwidth,
                            accum_width,
                            result_width : integer;
                            round : boolean := false;
                            last_indexes : t_last_value_indexes:=(phase=>0,list=>(others=>0));
                            use_indexes: boolean:=false
                          ) return signed is

  variable inc1         : signed( accum_width-1 downto 0) := (others=>'0');
  variable inc2         : signed( accum_width-1 downto 0) := (others=>'0');
  variable acc          : signed( accum_width-1 downto 0) := (others=>'0');
  variable result       : signed(result_width-1 downto 0) := (others=>'0');
  variable read_width   : integer := split_cwidth;
  variable remaining    : integer := coef_width;
  variable acc_last_ip  : signed( accum_width-1 downto 0) := (others=>'0');
begin
  for i in 0 to cpages-1 loop
    read_width := get_min(split_cwidth,remaining);
    if (accum_width>coef_width-remaining+max_int_width-(DATA_TYPE*COEF_TYPE)) then
      inc1 := fir_ovfl_signed ( c(i), d, taps, zpf, accum_width, last_indexes, use_indexes  );
      inc2 := (others=>'0');
      inc2(accum_width-1 downto coef_width-remaining) := inc1(accum_width-1-(coef_width-remaining) downto 0) ;
    else
      inc1 := to_signed( fir( c(i), d, taps, zpf, false, last_indexes, use_indexes ) , accum_width );
      inc2 := (others=>'0');
      inc2(accum_width-1 downto coef_width-remaining) := inc1(accum_width-1-(coef_width-remaining) downto 0) ;
    end if;
    acc := acc + inc2;
    remaining := remaining - read_width;
  end loop;
  if round then
    if properties.use_approx then
      acc_last_ip := split_fir_signed(c,d,cpages,taps,zpf,coef_width,split_cwidth,accum_width,accum_width,false,last_indexes,true);
    end if;
    result := rounding ( acc, accum_width, result_width, ROUND_MODE, acc_last_ip, properties.use_approx );
  else
    result := acc;
  end if;
  return result;

end;
type t_reload_status is
record
  reload_pntr_update   : t_reload_pointer;
  phase_count          : integer;
  interpolation_phases : integer;
  pq_int_idle          : boolean;
  pq_int_px_cnt        : integer;
end record;
function reload_status_init return t_reload_status is
  variable reload_status:t_reload_status;
begin
  reload_status.reload_pntr_update    :=(others=>0);
  reload_status.phase_count           :=0;
  reload_status.interpolation_phases  :=2;
  reload_status.pq_int_idle           :=false;
  reload_status.pq_int_px_cnt         :=((properties.clk_per_chan*DECIM_RATE)/INTERP_RATE)*reload_status.interpolation_phases*NUM_CHANNELS;
  return reload_status;
end;
function update_filter_page( reload_status_in   :t_reload_status;
                             push_regressor_rld : boolean;
                             filter_channel_rld : integer;
                             reload_pntr,
                             reload_pntr_current: t_reload_pointer;
                             coef_ld : std_logic;
                             coef_filter_sel,
                             filter_fsel_rld: integer ) return t_reload_status is
  variable reload_status:t_reload_status:=reload_status_in;
begin

  if push_regressor_rld then
    if (fir_model_reqs_v3.filter_type =  c_polyphase_pq and INTERP_RATE<DECIM_RATE) or
       fir_model_reqs_v3.filter_type =  c_polyphase_decimating then
      if filter_channel_rld=0 then
        if reload_status.phase_count=0 then
          reload_status.reload_pntr_update:=reload_pntr;
        else
          reload_status.reload_pntr_update:=reload_pntr_current;
        end if;
      end if;
      if filter_channel_rld=NUM_CHANNELS-1 then
        if reload_status.phase_count=DECIM_RATE-1 then
          reload_status.phase_count:=0;
        else
          reload_status.phase_count:=reload_status.phase_count+1;
        end if;
      end if;
    elsif  fir_model_reqs_v3.filter_type =  c_polyphase_pq and NUM_CHANNELS>1 then
      if filter_channel_rld=0 then
        if reload_status.interpolation_phases>1 or reload_status.pq_int_idle then
          reload_status.reload_pntr_update:=reload_pntr;
        elsif reload_status.interpolation_phases=1 then
          reload_status.reload_pntr_update:=reload_status.reload_pntr_update;
        end if;
        reload_status.pq_int_px_cnt:=0;
        reload_status.pq_int_idle:=false;
      end if;
      if filter_channel_rld=0 then
        reload_status.interpolation_phases:=((INTERP_RATE-reload_status.phase_count)/DECIM_RATE)
                                            +select_integer(0,1,(INTERP_RATE-reload_status.phase_count) rem DECIM_RATE>0);
        reload_status.phase_count:=(reload_status.phase_count+(reload_status.interpolation_phases*DECIM_RATE)) mod INTERP_RATE;
      end if;
    elsif  fir_model_reqs_v3.filter_type =  c_polyphase_interpolating then
      if filter_channel_rld=0 then
        reload_status.reload_pntr_update:=reload_pntr;
      end if;
    else
      reload_status.reload_pntr_update:=reload_pntr;
    end if;
  end if;
  if coef_ld='1' then
    if reload_pntr_current(coef_filter_sel) /=  reload_pntr(coef_filter_sel) and
       filter_fsel_rld/=coef_filter_sel and
       reload_status.reload_pntr_update(coef_filter_sel)=reload_pntr(coef_filter_sel)
       then
      reload_status.reload_pntr_update(coef_filter_sel) := (reload_status.reload_pntr_update(coef_filter_sel)-1) mod (reload_pointer_max+1);
    end if;
  end if;
  if fir_model_reqs_v3.filter_type =  c_polyphase_pq and INTERP_RATE>DECIM_RATE then
    if reload_status.pq_int_px_cnt/=((properties.clk_per_chan*DECIM_RATE)/INTERP_RATE)*reload_status.interpolation_phases*NUM_CHANNELS then
      reload_status.pq_int_px_cnt:=reload_status.pq_int_px_cnt+1;
    else
      reload_status.pq_int_idle:=true;
    end if;
  end if;
  return reload_status;
end;
function read_miffile_data ( filename         : string;
                             num_taps         : integer;
                             full_taps        : integer;
                             pad_offset       : integer;
                             num_filts        : integer;
                             is_signed           : boolean;
                             coef_width       : integer;
                             split_cwidth     : integer;
                             cpages           : integer
                           ) return t_coeff_array is
  variable coeffs       : t_coeff_array := (others=>(others=>(others=>0)));
  file     filepointer  : text;
  variable dataline     : line;
  variable binstring    : string(1 to coef_width);
  variable mif_status   : file_open_status;
  variable read_width   : integer := split_cwidth;
  variable remaining    : integer := coef_width;
  variable start        : integer := 1;
begin
  file_open(mif_status,filepointer,filename,read_mode);
  report "Padding offset = " & int_to_string(pad_offset);
  for i in 0 to num_filts-1 loop
    for j in 0 to num_taps-1 loop
      readline(filepointer, dataline);
      read(dataline, binstring);
      remaining := coef_width;
      for k in 0 to cpages-1 loop
        read_width := get_min(split_cwidth,remaining);
        start := remaining-read_width+1;
        coeffs(i)(k)(j+pad_offset) := bin_to_int(binstring(start to remaining),read_width,(is_signed and k=(cpages-1)));
        remaining := remaining - read_width;
      end loop;
    end loop;
  end loop;

  file_close(filepointer);

  return coeffs;

end read_miffile_data;
function split_reload_coeff ( coeff            : integer;
                              split_cwidth     : integer;
                              cpages           : integer
                           ) return t_split_coeff is
  variable coeff_temp   : t_split_coeff := (others=>0);
  variable remnant      : integer       := coeff;
begin
  for k in 0 to cpages-1 loop
    if k=(cpages-1) then
      coeff_temp(k) := remnant;
    else
      coeff_temp(k) := remnant mod 2**split_cwidth;
      remnant       := (remnant - coeff_temp(k)) / (2**split_cwidth);
    end if;
  end loop;
  return coeff_temp;

end split_reload_coeff;
function do_coeff_reorder ( reloaded : t_reload_coeffs;
                            order    : t_rld_order
                          ) return t_coefficients is
  variable index    : t_rld_index    := (others=>-1);
  variable coeffs   : t_coefficients := (others=>0);
  constant full_taps_adjusted : integer := properties.full_taps-2*inter_sym_shift;
begin
  for i in 0 to properties.reload_taps-1 loop
    index := order(i);
    if index(0)<0 or (index(1)>=0 and index(2)>=0) then
      report "<FIR MODEL>: ERROR: Reload indices incorrect at location " & integer'image(i) severity failure;
    end if;
    coeffs(index(0)) := coeffs(index(0)) + reloaded(i);
    if fir_model_reqs_v3.filter_type=c_interpolating_symmetry then
      if index(1)>=0 then
        coeffs(index(1)) := coeffs(index(1)) + reloaded(i);
      elsif index(2)>=0 then
        coeffs(index(2)) := coeffs(index(2)) - reloaded(i);
      else
        coeffs(index(0)) := coeffs(index(0)) + reloaded(i);
      end if;
    end if;
  end loop;

  if fir_model_reqs_v3.filter_type=c_interpolating_symmetry then
    for i in 0 to properties.reload_taps-1 loop
      coeffs(i) := coeffs(i)/2;
    end loop;
  end if;
  if properties.symmetry=1 then
    for i in 1 to (full_taps_adjusted)/2 loop
      if NEG_SYMMETRY=1 then
        coeffs(full_taps_adjusted-i) := -1 * coeffs(i-1);
      else
        coeffs(full_taps_adjusted-i) := coeffs(i-1);
      end if;
    end loop;
  end if;

  return coeffs;

end do_coeff_reorder;

constant coeff_sets : t_coeff_array
                    := read_miffile_data(
                                         C_ELABORATION_DIR&C_MEM_INIT_FILE,
                                         NUM_TAPS,
                                         properties.full_taps,
                                         pad_offset,
                                         NUM_FILTS,
                                         (COEF_TYPE=0),
                                         COEF_WIDTH,
                                         split_cwidth,
                                         cpages
                                        );
signal int_sclr               : std_logic := '0';
signal int_ce                 : std_logic := '0';
signal reload_sets            : t_reload_array    := (others => coeff_sets);
signal reload_pntr,
       reload_pntr_current    : t_reload_pointer  := (others=>0);
signal current_coeffs         : t_coeff_pages := (others=>(others=>0));
signal sets                   : t_sets  := (others=>0);
signal set                    : integer := 0;
signal rldpg                  : integer := 0;
signal channel_in             : integer := 0;
signal channel_out            : integer := 0;
signal ipbuff_data_out        : integer        := 0;
signal ipbuff_channel         : integer        := 0;
signal ipbuff_fsel_out        : integer        := 0;
signal ipbuff_data_out1       : integer        := 0;
signal ipbuff_channel1        : integer        := 0;
signal ipbuff_fsel_out1       : integer        := 0;
signal filter_data_in         : integer        := 0;
signal filter_channel         : integer        := 0;
signal filter_fsel            : integer        := 0;
signal channel_data           : t_buffer_array := (others=>(others=>0));
signal channel_fsel           : t_buffer_array := (others=>(others=>0));
signal channel_rldpg          : t_buffer_array := (others=>(others=>0));
signal data_slice             : t_regressor    := (others=>0);
signal push_ipbuff            : boolean := false;
signal pop_ipbuff             : boolean := false;
signal push_regressor         : boolean := false;
signal pop_regressor          : boolean := false;
signal next_data              : integer := 0;
signal next_data_fsel         : integer := 0;
signal result_int             : integer := 0;
signal result                 : std_logic_vector(OUTPUT_WIDTH-1 downto 0) := (others=>'0');
signal regout                 : std_logic_vector(OUTPUT_WIDTH-1 downto 0) := (others=>'0');
signal result_i               : std_logic_vector(  DATA_WIDTH-1 downto 0) := (others=>'0');
signal regout_i               : std_logic_vector(  DATA_WIDTH-1 downto 0) := (others=>'0');
signal ipbuff_rdy             : std_logic := '0';
signal ira_rdy                : std_logic := '0';
signal dra_rdy                : std_logic := '0';
signal rfd_int                : std_logic := '1';
signal cascade_in             : std_logic := '0';
signal rdy_dly                : t_rdy_dly := (others=>'0');
signal cascade_out            : std_logic := '0';
signal rdy_early              : std_logic := '0';
signal rdy_int                : std_logic := '0';
signal last_indexes           : t_last_value_indexes := last_value_indexes(fir_model_reqs_v3,properties);
begin
  int_sclr    <= SCLR when C_HAS_SCLR = 1 else '0';
  int_ce      <= CE   when C_HAS_CE   = 1 else '1';

  next_data      <= to_integer(signed(DIN)) when DATA_TYPE=0 else to_integer(unsigned(DIN));
  next_data_fsel <= to_integer(unsigned(FILTER_SEL));
  push_ipbuff    <= true when (ND='1' or C_HAS_ND=0) and rfd_int='1'  else false;
  pop_regressor  <= true when rdy_early='1'                           else false;



  gib2: if buff_in_depth > 0 generate
  begin

    ib2cnt: process (clk)
      variable input_buffer     : t_ipbuff_array := (others=>(others=>0));
      variable input_buffer1    : t_ipbuff_array := (others=>(others=>0));
      variable ipbuff_phase     : integer        := 0;
      variable ipbuff_wrcnt     : integer        := 0;
      variable ipbuff_rdcnt     : integer        := 0;
      variable ipbuff_rden      : boolean        := false;
      variable ipbuff_wrpntr    : integer        := 0;
      variable ipbuff_wrpntr1   : integer        := 0;
      variable ipbuff_rdpntr    : integer        := 0;
      variable ipbuff_rdpntr1   : integer        := 0;
      constant ipbuff_opthresh  : integer := properties.ipbuff_rate;
      variable ipbuff_rdcnt_limit    : integer;
      variable ipbuff_pq_phase       : integer := 0;
      variable ipbuff_pq_ops         : integer := 1;
      variable update_ipbuff_rden    : boolean;
    begin
      if (rising_edge(clk)) then
        if (int_sclr='1') then
          ipbuff_wrcnt    := 0;
          ipbuff_rdcnt    := 0;
          ipbuff_rden     := false;
          pop_ipbuff      <= false;
          ipbuff_rdy      <= '0';
          ipbuff_wrpntr   := 0;
          ipbuff_wrpntr1  := 0;
          ipbuff_rdpntr   := 0;
          ipbuff_rdpntr1  := 0;
          ipbuff_phase    := 0;
          input_buffer    := (others=>(others=>0));
          input_buffer1   := (others=>(others=>0));
          ipbuff_data_out <= 0;
          ipbuff_channel  <= 0;
          ipbuff_fsel_out <= 0;
          ipbuff_pq_phase := 0;
          ipbuff_pq_ops   := 0;
        elsif (int_ce='1') then
          if ipbuff_rdcnt=0 and ipbuff_rden then
            if fir_model_reqs_v3.filter_type = c_decimating_half_band then
              ipbuff_rdcnt_limit :=(properties.ipbuff_depth/2)*properties.ipbuff_rate-1;
            elsif not(fir_model_reqs_v3.filter_type = c_polyphase_pq and INTERP_RATE>DECIM_RATE) then
              ipbuff_rdcnt_limit :=properties.ipbuff_depth*INTERP_RATE*properties.ipbuff_rate-1;
            else
              ipbuff_pq_ops      := (INTERP_RATE-ipbuff_pq_phase)/DECIM_RATE + select_integer(0,1,(INTERP_RATE-ipbuff_pq_phase) rem DECIM_RATE > 0 );
              ipbuff_pq_phase    :=(ipbuff_pq_phase+(ipbuff_pq_ops*DECIM_RATE)) mod INTERP_RATE;
              ipbuff_rdcnt_limit :=properties.ipbuff_depth*ipbuff_pq_ops*properties.ipbuff_rate-1;
            end if;
          end if;
          update_ipbuff_rden:=ipbuff_rdcnt = ipbuff_rdcnt_limit and ipbuff_rden;
          if ipbuff_rden then
            if ipbuff_rdcnt < ipbuff_rdcnt_limit then
              ipbuff_rdcnt := ipbuff_rdcnt + 1;
            else
              ipbuff_rdcnt := 0;
              ipbuff_rden  := false;
            end if;
          end if;
          if push_ipbuff then
            ipbuff_wrcnt := ipbuff_wrcnt + 1;
          end if;
            if update_ipbuff_rden or push_ipbuff then
              if (ipbuff_wrcnt-1 = properties.ipbuff_thresh and push_ipbuff and not ipbuff_rden) or
                 (ipbuff_wrcnt > properties.ipbuff_thresh and update_ipbuff_rden) then
                ipbuff_rden  := true;
                ipbuff_wrcnt := ipbuff_wrcnt - properties.ipbuff_depth;
              end if;
            end if;
          if push_ipbuff then
            if (fir_model_reqs_v3.filter_type /= c_decimating_half_band                   ) or
               (fir_model_reqs_v3.filter_type  = c_decimating_half_band and ipbuff_phase=0) then
              input_buffer(0)(ipbuff_wrpntr)   := next_data;
              input_buffer(1)(ipbuff_wrpntr)   := channel_in;
              input_buffer(2)(ipbuff_wrpntr)   := next_data_fsel;
              ipbuff_wrpntr                    := (ipbuff_wrpntr+1) mod buff_in_depth;
            else
              input_buffer1(0)(ipbuff_wrpntr1) := next_data;
              input_buffer1(1)(ipbuff_wrpntr1) := channel_in;
              input_buffer1(2)(ipbuff_wrpntr1) := next_data_fsel;
              ipbuff_wrpntr1                   := (ipbuff_wrpntr1+1) mod buff_in_depth;
            end if;

            if channel_in=NUM_CHANNELS-1 then
              ipbuff_phase := (ipbuff_phase+1) mod DECIM_RATE;
            end if;

          end if;

          if ipbuff_rden and (ipbuff_rdcnt mod properties.ipbuff_rate)=0 then
            ipbuff_rdy <= '1';
          else
            ipbuff_rdy <= '0';
          end if;
          if  ipbuff_rden and
              ipbuff_rdcnt < (properties.ipbuff_depth*ipbuff_opthresh) and
             (ipbuff_rdcnt mod ipbuff_opthresh) = 0 then
            pop_ipbuff       <= true;
            ipbuff_data_out  <= input_buffer(0)(ipbuff_rdpntr);
            ipbuff_channel   <= input_buffer(1)(ipbuff_rdpntr);
            ipbuff_fsel_out  <= input_buffer(2)(ipbuff_rdpntr);
            ipbuff_rdpntr    := (ipbuff_rdpntr+1) mod buff_in_depth;
            if fir_model_reqs_v3.filter_type = c_decimating_half_band then
              ipbuff_data_out1 <= input_buffer1(0)(ipbuff_rdpntr1);
              ipbuff_channel1  <= input_buffer1(1)(ipbuff_rdpntr1);
              ipbuff_fsel_out1 <= input_buffer1(2)(ipbuff_rdpntr1);
              ipbuff_rdpntr1   := (ipbuff_rdpntr1+1) mod buff_in_depth;
            end if;
          else
            pop_ipbuff <= false;
          end if;

        end if;
      end if;
    end process;
  end generate;

  push_regressor <= push_ipbuff     when buff_in_depth=0 else pop_ipbuff;
  filter_data_in <= next_data       when buff_in_depth=0 else ipbuff_data_out;
  filter_channel <= channel_in      when buff_in_depth=0 else ipbuff_channel;
  filter_fsel    <= next_data_fsel  when buff_in_depth=0 else ipbuff_fsel_out;

  gira1: if buff_in_depth = 0 generate
  begin

    ira_cnt: process (clk)
      variable ira_count_en : boolean := false;
      variable ira_count    : integer := 0;
    begin
      if (rising_edge(clk)) then
        if (int_sclr='1') then
          ira_count_en  := false;
          ira_count     := 0;
          ira_rdy       <= '0';
        elsif (int_ce='1') then

          if ira_count_en then
            if ira_count < (INTERP_RATE*properties.ipbuff_rate-1) then
              ira_count := ira_count + 1;
            else
              ira_count     := 0;
              ira_count_en  := false;
            end if;
          end if;

          if push_ipbuff then

            ira_count_en := true;

          end if;

          if ira_count_en and (ira_count mod properties.ipbuff_rate)=0 then
            ira_rdy <= '1';
          else
            ira_rdy <= '0';
          end if;
        end if;
      end if;
    end process;
  end generate;

  cascade_in <= ira_rdy when buff_in_depth=0 else ipbuff_rdy;
  gndat1 : if true generate
  begin
    new_data: process (clk)
      variable pointers  : t_pointers := (others=>pointer_start);
      variable rst_shift : t_pointers := (others=>0);
      variable push_regressor_dly   : t_push_regressor_dly   := (others=>false);
      variable filter_channel_dly   : t_filter_channel_dly   := (others=>0);
      variable filter_data_in_dly   : t_filter_data_in_dly   := (others=>0);
      variable filter_fsel_dly      : t_filter_fsel_dly      := (others=>0);
      variable ipbuff_channel1_dly  : t_ipbuff_channel1_dly  := (others=>0);
      variable ipbuff_data_out1_dly : t_ipbuff_data_out1_dly := (others=>0);
      variable ipbuff_fsel_out1_dly : t_ipbuff_fsel_out1_dly := (others=>0);
      variable push_regressor_rld   : boolean:=false;
      variable filter_channel_rld   : integer:=0;
      variable filter_data_in_rld   : integer:=0;
      variable filter_fsel_rld      : integer:=0;
      variable ipbuff_channel1_rld  : integer:=0;
      variable ipbuff_data_out1_rld : integer:=0;
      variable ipbuff_fsel_out1_rld : integer:=0;
      variable reload_status : t_reload_status:=reload_status_init;
    begin
      if (rising_edge(clk)) then
        if int_sclr='1' then
          channel_out <= 0;
          for j in 0 to NUM_CHANNELS-1 loop
            for i in 0 to main_buffer_depth-rst_shift(j)-1 loop
              channel_data (j)(i) <= channel_data (j)(i+rst_shift(j));
              channel_fsel (j)(i) <= channel_fsel (j)(i+rst_shift(j));
              channel_rldpg(j)(i) <= channel_rldpg(j)(i+rst_shift(j));
            end loop;
          end loop;
          pointers  := (others=>pointer_start);
          rst_shift := (others=>0);

          push_regressor_rld := false;
          push_regressor_dly := (others=>false);

          last_indexes <= last_value_indexes(fir_model_reqs_v3,properties);

          reload_status := reload_status_init;
        elsif (int_ce='1') then
          if pop_regressor then
            for i in 0 to main_buffer_depth-DECIM_RATE-1 loop
              channel_data (channel_out)(i) <= channel_data (channel_out)(i+DECIM_RATE);
              channel_fsel (channel_out)(i) <= channel_fsel (channel_out)(i+DECIM_RATE);
              channel_rldpg(channel_out)(i) <= channel_rldpg(channel_out)(i+DECIM_RATE);
            end loop;

            pointers(channel_out)  := pointers(channel_out) - DECIM_RATE;

            for i in 0 to DECIM_RATE-1 loop
              if rst_shift(channel_out)=0 then
                rst_shift(channel_out) := INTERP_RATE-1;
              else
                rst_shift(channel_out) := rst_shift(channel_out) - 1;
              end if;
            end loop;
            if (fir_model_reqs_v3.filter_type = c_polyphase_interpolating or
                fir_model_reqs_v3.filter_type = c_interpolating_symmetry or
                fir_model_reqs_v3.filter_type = c_interpolating_half_band or
                fir_model_reqs_v3.filter_type = c_polyphase_pq) and
                properties.use_approx then
              if channel_out=NUM_CHANNELS-1 then
                last_indexes<=last_value_indexes_iterate(fir_model_reqs_v3,properties,last_indexes);
              end if;
            end if;
            if channel_out=NUM_CHANNELS-1 then
              channel_out <= 0;
            else
              channel_out <= channel_out+1;
            end if;
          end if;

          push_regressor_dly(1 to reload_dly_len-2)   := push_regressor_dly(0 to reload_dly_len-3);
          push_regressor_dly(0)                       := push_regressor;
          filter_channel_dly(1 to reload_dly_len-2)   := filter_channel_dly(0 to reload_dly_len-3);
          filter_channel_dly(0)                       := filter_channel;
          filter_data_in_dly(1 to reload_dly_len-2)   := filter_data_in_dly(0 to reload_dly_len-3);
          filter_data_in_dly(0)                       := filter_data_in;
          filter_fsel_dly(1 to reload_dly_len-2)      := filter_fsel_dly(0 to reload_dly_len-3);
          filter_fsel_dly(0)                          := filter_fsel;
          ipbuff_channel1_dly(1 to reload_dly_len-2)  := ipbuff_channel1_dly(0 to reload_dly_len-3);
          ipbuff_channel1_dly(0)                      := ipbuff_channel1;
          ipbuff_data_out1_dly(1 to reload_dly_len-2) := ipbuff_data_out1_dly(0 to reload_dly_len-3);
          ipbuff_data_out1_dly(0)                     := ipbuff_data_out1;
          ipbuff_fsel_out1_dly(1 to reload_dly_len-2) := ipbuff_fsel_out1_dly(0 to reload_dly_len-3);
          ipbuff_fsel_out1_dly(0)                     := ipbuff_fsel_out1;
          push_regressor_rld   := push_regressor_dly(reload_dly_len-3);
          filter_channel_rld   := filter_channel_dly(reload_dly_len-3);
          filter_data_in_rld   := filter_data_in_dly(reload_dly_len-3);
          filter_fsel_rld      := filter_fsel_dly(reload_dly_len-3);
          ipbuff_channel1_rld  := ipbuff_channel1_dly(reload_dly_len-3);
          ipbuff_data_out1_rld := ipbuff_data_out1_dly(reload_dly_len-3);
          ipbuff_fsel_out1_rld := ipbuff_fsel_out1_dly(reload_dly_len-3);
          reload_status:=update_filter_page( reload_status,
                                             push_regressor_rld,
                                             filter_channel_rld,
                                             reload_pntr,
                                             reload_pntr_current,
                                             COEF_LD,
                                             to_integer(unsigned(COEF_FILTER_SEL)),
                                             filter_fsel_rld );
          if push_regressor_rld then
            if not( fir_model_reqs_v3.filter_type = c_polyphase_pq and INTERP_RATE>DECIM_RATE) then
              channel_data (filter_channel_rld)(pointers(filter_channel_rld)-(DECIM_RATE-1)) <= filter_data_in_rld;
              channel_fsel (filter_channel_rld)(pointers(filter_channel_rld)-(DECIM_RATE-1)) <= filter_fsel_rld;
              channel_rldpg(filter_channel_rld)(pointers(filter_channel_rld)-(DECIM_RATE-1)) <= reload_status.reload_pntr_update(filter_fsel_rld);
            else
              channel_data (filter_channel_rld)(pointers(filter_channel_rld)) <= filter_data_in_rld;
              channel_fsel (filter_channel_rld)(pointers(filter_channel_rld)) <= filter_fsel_rld;
              channel_rldpg(filter_channel_rld)(pointers(filter_channel_rld)) <= reload_status.reload_pntr_update(filter_fsel_rld);
            end if;

            reload_pntr_current(filter_fsel_rld)<=reload_status.reload_pntr_update(filter_fsel_rld);
            for i in 1 to INTERP_RATE-1 loop
              if not( fir_model_reqs_v3.filter_type = c_polyphase_pq and INTERP_RATE>DECIM_RATE) then
                channel_data (filter_channel_rld)(pointers(filter_channel_rld)+i-(DECIM_RATE-1)) <= 0;
                channel_fsel (filter_channel_rld)(pointers(filter_channel_rld)+i-(DECIM_RATE-1)) <= filter_fsel_rld;
                channel_rldpg(filter_channel_rld)(pointers(filter_channel_rld)+i-(DECIM_RATE-1)) <= reload_status.reload_pntr_update(filter_fsel_rld);
              else
                channel_data (filter_channel_rld)(pointers(filter_channel_rld)+i) <= 0;
                channel_fsel (filter_channel_rld)(pointers(filter_channel_rld)+i) <= filter_fsel_rld;
                channel_rldpg(filter_channel_rld)(pointers(filter_channel_rld)+i) <= reload_status.reload_pntr_update(filter_fsel_rld);
              end if;
            end loop;
            pointers(filter_channel_rld) := pointers(filter_channel_rld) + INTERP_RATE;
            if fir_model_reqs_v3.filter_type = c_decimating_half_band then
              channel_data (ipbuff_channel1_rld)(pointers(ipbuff_channel1_rld)-(DECIM_RATE-1)) <= ipbuff_data_out1_rld;
              channel_fsel (ipbuff_channel1_rld)(pointers(ipbuff_channel1_rld)-(DECIM_RATE-1)) <= ipbuff_fsel_out1_rld;
              channel_rldpg(ipbuff_channel1_rld)(pointers(ipbuff_channel1_rld)-(DECIM_RATE-1)) <= reload_pntr(ipbuff_fsel_out1_rld);
              pointers(ipbuff_channel1_rld) := pointers(ipbuff_channel1_rld) + 1;
            end if;
          end if;
        end if;

      end if;
    end process;
  end generate;


  data_slice <= t_regressor(channel_data(channel_out)(0 to regressor_len-1));

  set <= channel_fsel(channel_out)(set_index);

  rldpg <= channel_rldpg(channel_out)(set_index);
  current_coeffs <= reload_sets(rldpg)(set);

  gfir1: if not split_accum and not signed_output generate
  begin

    result <= std_logic_vector(
                to_unsigned (
                  fir (
                    current_coeffs(0),
                    data_slice,
                    properties.full_taps,
                    properties.zpf,
                    true,
                    last_indexes ),
                  fir_model_reqs_v3.output_width
                )
              );

  end generate;
  gfir2: if not split_accum and signed_output generate
  begin
    result <= std_logic_vector(
                to_signed (
                  fir (
                    current_coeffs(0),
                    data_slice,
                    properties.full_taps,
                    properties.zpf,
                    true,
                    last_indexes ),
                  fir_model_reqs_v3.output_width
                )
              );

  end generate;
  gfir3: if split_accum and not signed_output generate
  begin

    result <= std_logic_vector(
                split_fir_unsigned(
                  current_coeffs,
                  data_slice,
                  cpages,
                  properties.full_taps,
                  properties.zpf,
                  coef_width,
                  split_cwidth,
                  fir_model_reqs_v3.accum_width,
                  fir_model_reqs_v3.output_width,
                  true,
                  last_indexes
                )
              );

  end generate;
  gfir4: if split_accum and signed_output generate
  begin

    result <= std_logic_vector(
                split_fir_signed(
                  current_coeffs,
                  data_slice,
                  cpages,
                  properties.full_taps,
                  properties.zpf,
                  coef_width,
                  split_cwidth,
                  fir_model_reqs_v3.accum_width,
                  fir_model_reqs_v3.output_width,
                  true,
                  last_indexes
                )
              );

  end generate;
  opreg1: process (clk)
  begin
    if (rising_edge(clk)) then
      if int_sclr='1' then
        regout   <= (others=>'0');
        CHAN_OUT <= (others=>'0');
      elsif (int_ce='1') then
        if rdy_early='1' then
          regout   <= result;
          CHAN_OUT <= std_logic_vector(to_unsigned(channel_out,CHAN_SEL_WIDTH));
        end if;
      end if;
    end if;
  end process;

  ghilb1: if fir_model_reqs_v3.filter_type/=c_hilbert_transform generate
  begin
    DOUT   <= regout when OUTPUT_REG=1 or rdy_int='1' else (others=>'X');
    DOUT_Q <= (others=>'X');
    DOUT_I <= (others=>'X');
  end generate;

  ghilb2: if fir_model_reqs_v3.filter_type=c_hilbert_transform generate
  begin

    result_i <= std_logic_vector(to_unsigned(channel_data(channel_out)(properties.full_taps/2),DATA_WIDTH))
           when DATA_TYPE=1
           else std_logic_vector(  to_signed(channel_data(channel_out)(properties.full_taps/2),DATA_WIDTH));

    opreg2: process (clk)
    begin
      if (rising_edge(clk)) then
        if int_sclr='1' then
          regout_i <= (others=>'0');
        elsif (int_ce='1') then
          if rdy_early='1' then
            regout_i <= result_i;
          end if;
        end if;
      end if;
    end process;

    DOUT   <= (others=>'X');
    DOUT_Q <= regout   when OUTPUT_REG=1 or rdy_int='1' else (others=>'X');
    DOUT_I <= regout_i when OUTPUT_REG=1 or rdy_int='1' else (others=>'X');
  end generate;


  casc_dly1: process (clk)
  begin
    if (rising_edge(clk)) then
      if int_sclr='1' then
        rdy_dly  <= (others=>'0');
      elsif (int_ce='1') then
        rdy_dly (1 to rdy_dly_len-1) <= rdy_dly (0 to rdy_dly_len-2);
        rdy_dly (0)                  <= cascade_in;
      end if;
    end if;
  end process;
  cascade_out <= rdy_dly(rdy_dly_len-1);
  dra_cnt: process (clk)
    variable dra_count       : integer := 0;
    constant dra_deci_rate   : integer := select_integer(DECIM_RATE-1,0,
                                                         (fir_model_reqs_v3.filter_type=c_polyphase_pq and INTERP_RATE>DECIM_RATE) or
                                                          fir_model_reqs_v3.filter_type=c_decimating_half_band);
    constant dra_threshold   : integer :=
                                            select_integer(dra_deci_rate, 0,
                                                           fir_model_reqs_v3.filter_type=c_decimating_half_band);
  begin
    if (rising_edge(clk)) then
      if (int_sclr='1') then
        dra_count := 0;
        dra_rdy   <= '0';
      elsif (int_ce='1') then

        if (cascade_out='1') then
          if dra_count = dra_threshold then
            dra_rdy   <= '1';
          else
            dra_rdy   <= '0';
          end if;
          if dra_count < (dra_deci_rate) then
            dra_count := dra_count + 1;
          else
            dra_count := 0;
          end if;
        else
          dra_rdy <= '0';
        end if;
      end if;
    end if;
  end process;
  ob1: if buff_out_depth=0 generate

    rdy_early <= dra_rdy;
  end generate;
  ob2: if buff_out_depth>0 generate
    ob2cnt: process (clk)
      variable opbuff_wrcnt     : integer   := 0;
      variable opbuff_rdcnt     : integer   := 0;
      variable opbuff_rden      : boolean   := false;
    begin
      if (rising_edge(clk)) then
        if (int_sclr='1') then
          opbuff_wrcnt := 0;
          opbuff_rdcnt := 0;
          opbuff_rden  := false;
          rdy_early    <= '0';
        elsif (int_ce='1') then

          if opbuff_rden then
            if opbuff_rdcnt < (buff_out_depth*properties.opbuff_rate-1) then
              opbuff_rdcnt := opbuff_rdcnt + 1;
            else
              opbuff_rdcnt := 0;
              opbuff_rden  := false;
            end if;
          end if;

          if (dra_rdy='1') then
            if opbuff_wrcnt = properties.opbuff_thresh then
              opbuff_rden  := true;
            end if;
            if opbuff_wrcnt < (buff_out_depth-1) then
              opbuff_wrcnt := opbuff_wrcnt + 1;
            else
              opbuff_wrcnt := 0;
            end if;
          end if;

          if opbuff_rden and (opbuff_rdcnt mod properties.opbuff_rate)=0 then
            rdy_early <= '1';
          else
            rdy_early <= '0';
          end if;

        end if;
      end if;
    end process;
  end generate;
  rdy_reg: process (clk)
  begin
    if (rising_edge(clk)) then
      if (int_sclr='1') then
        rdy_int <= '0';
      elsif (int_ce='1') then
        rdy_int <= rdy_early;
      end if;
    end if;
  end process;
  RDY <= rdy_int;

  chin_cnt: process (clk)
  begin
    if (rising_edge(clk)) then
      if (int_sclr='1') then
        channel_in      <= 0;
      elsif (int_ce='1') then

        if push_ipbuff then
          if channel_in=NUM_CHANNELS-1 then
            channel_in <= 0;
          else
            channel_in <= channel_in+1;
          end if;
        end if;

      end if;
    end if;
  end process;

  CHAN_IN  <= std_logic_vector(to_unsigned(channel_in, CHAN_SEL_WIDTH));
  rfd_cnt: process (clk)
    variable rfd_count    : integer := 0;
    variable rfd_cnt_en   : boolean := false;
    variable rfd_count_len: integer := properties.clk_per_chan;
  begin
    if (rising_edge(clk)) then
      if (int_sclr='1') then
        rfd_cnt_en   := false;
        rfd_count    := 0;
        rfd_int      <= '1';
        rfd_count_len:= properties.clk_per_chan;
      elsif (int_ce='1') then
        if push_ipbuff and rfd_count_len>1 then
          rfd_cnt_en   := true;
          rfd_int      <= '0';
        end if;
        if rfd_cnt_en and rfd_count < rfd_count_len then
          rfd_count    := rfd_count + 1;
        end if;
        if (rfd_cnt_en and rfd_count = rfd_count_len) or rfd_count_len = 1 then
          rfd_cnt_en := false;
          rfd_count  := 0;
          rfd_int    <= '1';
          if alter_input_rate and
             channel_in = fir_model_reqs_v3.num_channels-1 then
            rfd_count_len:= properties.clk_per_chan+1;
          else
            rfd_count_len:= properties.clk_per_chan;
          end if;
        end if;
      end if;
    end if;
  end process;
  RFD <= rfd_int;
  grld1: if COEF_RELOAD = 1 generate
  begin
    rld1: process (clk)

      variable reload_count       : integer := 0;
      variable reload_cnt_en      : boolean := false;
      variable temp_set           : integer := 0;
      variable temp_coeff         : t_reload_coeffs := (others=>0);
      variable reordered_coeffs   : t_coefficients  := (others=>0);
      variable reload_temp        : t_split_coeff   := (others=>0);
      variable reloaded_coeffs    : t_coeff_pages   := (others=>(others=>0));
      variable reload_done        : integer         := -1;
      variable reload_end         : integer         := -1;

      variable push_regressor_dly   : t_push_regressor_dly   := (others=>false);
      variable push_regressor_rld   : boolean:=false;
      variable filter_fsel_dly      : t_filter_fsel_dly      := (others=>0);
      variable filter_fsel_rld      : integer:=0;
      variable filter_channel_dly   : t_filter_channel_dly   := (others=>0);
      variable filter_channel_rld   : integer:=0;
      variable reload_status : t_reload_status:=reload_status_init;
    begin
      if (rising_edge(clk)) then

        if (int_sclr='1') then

          reload_cnt_en       := false;
          reload_count        := 0;
          temp_set            := 0;
          temp_coeff          := (others=>0);
          reordered_coeffs    := (others=>0);
          reload_temp         := (others=>0);
          reloaded_coeffs     := (others=>(others=>0));
          reload_done         := -1;
          reload_end          := -1;
          push_regressor_rld  := false;
          push_regressor_dly  := (others=>false);
          reload_status :=reload_status_init;
        elsif (int_ce='1') then
          push_regressor_dly(1 to reload_dly_len-2)   := push_regressor_dly(0 to reload_dly_len-3);
          push_regressor_dly(0)                       := push_regressor;
          push_regressor_rld   := push_regressor_dly(reload_dly_len-3);
          filter_fsel_dly(1 to reload_dly_len-2)      := filter_fsel_dly(0 to reload_dly_len-3);
          filter_fsel_dly(0)                          := filter_fsel;
          filter_fsel_rld      := filter_fsel_dly(reload_dly_len-3);
          filter_channel_dly(1 to reload_dly_len-2)   := filter_channel_dly(0 to reload_dly_len-3);
          filter_channel_dly(0)                       := filter_channel;
          filter_channel_rld   := filter_channel_dly(reload_dly_len-3);
          reload_status:=update_filter_page( reload_status,
                                             push_regressor_rld,
                                             filter_channel_rld,
                                             reload_pntr,
                                             reload_pntr_current,
                                             COEF_LD,
                                             to_integer(unsigned(COEF_FILTER_SEL)),
                                             filter_fsel_rld );
          if reload_cnt_en and COEF_WE='1' and reload_count < properties.reload_taps and COEF_LD='0' then
            if COEF_TYPE=0 or fir_model_reqs_v3.filter_type = c_interpolating_symmetry then
              temp_coeff(reload_count) := to_integer(signed(COEF_DIN));
            else
              temp_coeff(reload_count) := to_integer(unsigned(COEF_DIN));
            end if;
            reload_count    := reload_count + 1;
          end if;

          if COEF_LD='1' then
            reload_count  := 0;
            reload_cnt_en := true;
            temp_set      := to_integer(unsigned(COEF_FILTER_SEL));
            if reload_pntr_current(temp_set) /=  reload_pntr(temp_set) and
               not(reload_status.reload_pntr_update(filter_fsel_rld) = reload_pntr(temp_set) and
                filter_fsel_rld=temp_set) then
              reload_pntr(temp_set) <= (reload_pntr(temp_set)-1) mod (reload_pointer_max+1);
            end if;
          end if;
          reload_done := -1;

          if (reload_cnt_en and reload_count = properties.reload_taps) then

            reordered_coeffs := (others=>0);
            reload_temp      := (others=>0);
            reloaded_coeffs  := (others=>(others=>0));

            reload_cnt_en := false;
            reload_count  := 0;

            reload_done   := temp_set;
            reordered_coeffs := do_coeff_reorder(temp_coeff,reload_order);

            for i in 0 to properties.full_taps-1 loop
              reload_temp := split_reload_coeff( reordered_coeffs(i), split_cwidth, cpages );
              for j in 0 to cpages-1 loop
                reloaded_coeffs(j)(i) := reload_temp(j);
              end loop;
            end loop;

            reload_sets((reload_pntr(temp_set)+1) mod (reload_pointer_max+1))(temp_set) <= reloaded_coeffs;

            temp_coeff := (others=>0);
            temp_set   := 0;
          end if;

          reload_end := reload_done;
          if (reload_end >= 0) and (reload_end < NUM_FILTS) then
            reload_pntr(reload_end) <= (reload_pntr(reload_end)+1) mod (reload_pointer_max+1);
          end if;

        end if;
      end if;

    end process;
  end generate;
end behavioural;
