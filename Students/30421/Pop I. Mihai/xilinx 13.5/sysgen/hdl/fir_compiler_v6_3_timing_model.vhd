
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
use ieee.std_logic_textio.all;
library std;
use std.textio.all;
library xilinxcorelib;
use xilinxcorelib.bip_utils_pkg_v2_0.all;
use xilinxcorelib.fir_compiler_v6_3_sim_pkg.all;

  entity fir_compiler_v6_3_timing_model is
  generic (
    C_XDEVICEFAMILY     : string  := "virtex6";
    C_ELABORATION_DIR   : string  := "./";
    C_COMPONENT_NAME    : string  := "fir_compiler_v6_3_default";
    C_COEF_FILE         : string  := "fir_compiler_v6_3_default.mif";
    C_COEF_FILE_LINES   : integer := 11;

    C_NUM_TAPS_CALCED      : integer := 21;

    C_FILTER_TYPE          : integer := 0;
    C_INTERP_RATE          : integer := 1;
    C_DECIM_RATE           : integer := 1;
    C_ZERO_PACKING_FACTOR  : integer := 1;
    C_SYMMETRY             : integer := 1;
    C_NUM_FILTS            : integer := 1;
    C_NUM_TAPS             : integer := 21;
    C_NUM_CHANNELS         : integer := 1;
    C_CHANNEL_PATTERN      : string  := "fixed";
    C_ROUND_MODE           : integer := 0;
    C_COEF_RELOAD          : integer := 0;
    C_NUM_RELOAD_SLOTS     : integer := 1;
    C_COL_MODE             : integer := 0;
    C_COL_PIPE_LEN         : integer := 4;
    C_COL_CONFIG           : string  := "1";
    C_OPTIMIZATION         : integer := 0;
    C_DATA_PATH_WIDTHS     : string  := "16";
    C_DATA_WIDTH           : integer := 16;
    C_COEF_PATH_WIDTHS     : string  := "16";
    C_COEF_WIDTH           : integer := 16;
    C_DATA_PATH_SRC        : string  := "0";
    C_COEF_PATH_SRC        : string  := "0";
    C_DATA_PATH_SIGN       : string  := "0";
    C_COEF_PATH_SIGN       : string  := "0";
    C_ACCUM_PATH_WIDTHS    : string  := "24";
    C_OUTPUT_WIDTH         : integer := 24;
    C_OUTPUT_PATH_WIDTHS   : string  := "24";
    C_ACCUM_OP_PATH_WIDTHS : string  := "24";
    C_EXT_MULT_CNFG        : string  := "none";
    C_NUM_MADDS            : integer := 1;
    C_OPT_MADDS            : string  := "none";
    C_OVERSAMPLING_RATE    : integer := 11;
    C_INPUT_RATE           : integer := 300000;
    C_OUTPUT_RATE          : integer := 300000;
    C_DATA_MEMTYPE         : integer := 0;
    C_COEF_MEMTYPE         : integer := 2;
    C_IPBUFF_MEMTYPE       : integer := 0;
    C_OPBUFF_MEMTYPE       : integer := 0;
    C_DATAPATH_MEMTYPE     : integer := 0;
    C_MEM_ARRANGEMENT      : integer := 1;
    C_DATA_MEM_PACKING     : integer := 0;
    C_COEF_MEM_PACKING     : integer := 0;
    C_FILTS_PACKED         : integer := 0;
    C_LATENCY              : integer := 18;
    C_HAS_ARESETn          : integer := 0;
    C_HAS_ACLKEN           : integer := 0;
    C_DATA_HAS_TLAST     : integer := 0;

    C_S_DATA_HAS_FIFO    : integer := 1;
    C_S_DATA_HAS_TUSER   : integer := 0;
    C_S_DATA_TDATA_WIDTH : integer := 16;
    C_S_DATA_TUSER_WIDTH : integer := 1;
    C_M_DATA_HAS_TREADY  : integer := 0;
    C_M_DATA_HAS_TUSER   : integer := 0;
    C_M_DATA_TDATA_WIDTH : integer := 24;
    C_M_DATA_TUSER_WIDTH : integer := 1;
    C_HAS_CONFIG_CHANNEL : integer := 0;
    C_CONFIG_SYNC_MODE   : integer := 0;
    C_CONFIG_PACKET_SIZE : integer := 0;
    C_CONFIG_TDATA_WIDTH : integer := 1;
    C_RELOAD_TDATA_WIDTH : integer := 1
  );
  port (
    aresetn                          : in  std_logic:='1';
    aclk                             : in  std_logic;
    aclken                           : in  std_logic:='1';
    s_axis_data_tvalid               : in std_logic:='0';
    s_axis_data_tready               : out std_logic:='1';
    s_axis_data_tlast                : in std_logic:='0';
    s_axis_data_tuser                : in std_logic_vector(C_S_DATA_TUSER_WIDTH-1 downto 0):=(others=>'0');
    s_axis_config_tvalid             : in  std_logic:='0';
    s_axis_config_tready             : out std_logic:='1';
    s_axis_config_tlast              : in  std_logic:='0';

    s_axis_reload_tvalid             : in  std_logic:='0';
    s_axis_reload_tready             : out std_logic:='1';
    s_axis_reload_tlast              : in  std_logic:='0';
    s_axis_reload_tdata              : in  std_logic_vector(C_RELOAD_TDATA_WIDTH-1 downto 0):=(others=>'0');
    m_axis_data_tvalid               : out std_logic:='0';
    m_axis_data_tready               : in std_logic:='1';
    m_axis_data_tlast                : out std_logic:='0';
    m_axis_data_tuser                : out std_logic_vector(C_M_DATA_TUSER_WIDTH-1 downto 0):=(others=>'0');

    s_axis_config_send_packet        : out std_logic:='0';
    s_axis_config_send_packet_ce     : out std_logic:='0';
    s_axis_reload_packet_valid       : out std_logic:='0';
    s_axis_reload_packet_fsel        : out integer  := 0;
    s_axis_reload_packet_data        : out std_logic_vector(C_NUM_TAPS_CALCED*C_RELOAD_TDATA_WIDTH-1 downto 0) := (others=>'0');
    s_axis_reload_packet_ce          : out std_logic:='0';
    s_axis_data_send                 : out std_logic:='0';
    s_axis_data_send_amount          : out integer  := 1;
    s_axis_data_send_ce              : out std_logic:='0';
    reset_model                      : out std_logic:='0';

    event_s_data_tlast_missing       : out std_logic := '0';
    event_s_data_tlast_unexpected    : out std_logic := '0';
    event_s_data_chanid_incorrect    : out std_logic := '0';
    event_s_config_tlast_missing     : out std_logic := '0';
    event_s_config_tlast_unexpected  : out std_logic := '0';
    event_s_reload_tlast_missing     : out std_logic := '0';
    event_s_reload_tlast_unexpected  : out std_logic := '0'
  );
end fir_compiler_v6_3_timing_model;
architecture behavioral of fir_compiler_v6_3_timing_model is
  type t_fir_properties is
  record
    actual_taps         : integer;
    reload_taps         : integer;
    full_taps           : integer;
    madd_mem_depth      : integer;
    px_time             : integer;
    op_px_time          : integer;
    ipbuff_size         : integer;
    ipbuff_thres        : integer;
    opbuff_size         : integer;
    opbuff_thres        : integer;
    cntrl_dly           : integer;
    data_dly            : integer;
    cnfg_read_dly       : integer;
    odd_symmetry        : integer;
    casc_mod            : integer;
    flip_reload_order   : boolean;
    coef_holdoff_update : integer;
  end record;


  constant ci_data_path_signs : t_int_array:=fn_str_to_int_array(C_DATA_PATH_SIGN,',');
  constant ci_coef_path_widths: t_int_array:=fn_str_to_int_array(C_COEF_PATH_WIDTHS,',');
  constant ci_coef_path_signs : t_int_array:=fn_str_to_int_array(C_COEF_PATH_SIGN,',');
  constant ci_coef_path_src   : t_int_array:=fn_str_to_int_array(C_COEF_PATH_SRC,',');
  constant ci_op_accum_widths : t_int_array:=fn_str_to_int_array(C_ACCUM_OP_PATH_WIDTHS,',');
  constant ci_output_widths   : t_int_array:=fn_str_to_int_array(C_OUTPUT_PATH_WIDTHS,',');

  constant ci_num_paths       : integer := ci_op_accum_widths'LENGTH;
  constant ci_coef_width_physical   : integer := fn_get_path_base_i(ci_coef_path_widths,ci_coef_path_src,ci_data_path_signs'LENGTH);

  constant ci_data_sign   : integer := ci_data_path_signs(ci_data_path_signs'HIGH);
  constant ci_coef_sign   : integer := ci_coef_path_signs(ci_coef_path_signs'HIGH);
  constant ci_accum_width : integer := ci_op_accum_widths(ci_op_accum_widths'HIGH);

  constant ci_fixed_chan_pat : boolean := fn_str_compare(C_CHANNEL_PATTERN,"fixed");
  constant ci_chan_pat       : t_int_array := fn_get_channel_pat(ci_fixed_chan_pat,C_NUM_CHANNELS,C_CHANNEL_PATTERN);
  constant ci_pat_len        : integer := 2**log2roundup(C_NUM_CHANNELS);
  constant ci_num_pat        : integer := ci_chan_pat'LENGTH / ci_pat_len;
  constant ci_chan_base      : t_int_array := fn_gen_has_diff_bases(ci_fixed_chan_pat,C_NUM_CHANNELS,ci_chan_pat);
  constant ci_chan_pat_step  : t_int_array := fn_gen_addr_step(ci_fixed_chan_pat,C_NUM_CHANNELS,1,ci_chan_pat,ci_chan_base,true);

  constant ci_cnfg_update_dly : integer := fn_select_integer(1,2,C_COEF_RELOAD=1 and C_NUM_FILTS > 1);
  constant ci_cnfg_lat        : integer := fn_select_integer(0,2+ci_cnfg_update_dly,C_HAS_CONFIG_CHANNEL=1);

  constant ci_col_lengths       : t_int_array:=fn_str_to_int_array(C_COL_CONFIG,',');
  constant ci_num_split_cols    : integer := ci_col_lengths'LENGTH;
  constant ci_use_sym_multi_col : boolean := C_OVERSAMPLING_RATE > 1 and C_SYMMETRY /= ci_non_symmetric and ci_num_split_cols>1 and C_COL_MODE=1;
  constant ci_cntrl_index       : t_int_array:=fn_gen_cntrl_index(
                                                 fn_gen_cnt(C_NUM_MADDS,1,0),
                                                 ci_col_lengths,
                                                 ci_use_sym_multi_col);
  constant ci_cascade_length    : integer := fn_max_val(ci_cntrl_index).val+1;

  function fn_gen_fir_properties return t_fir_properties is
    variable properties : t_fir_properties;
    variable cnfg_and_rld_lat : integer;
    variable rounding_cnfg : t_rounding_cnfg;
  begin
    properties.flip_reload_order := false;
    properties.coef_holdoff_update := 0;
    case C_FILTER_TYPE is
    when ci_single_rate | ci_transpose_single_rate | ci_interpolated =>
      properties.actual_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE;
      properties.full_taps          :=C_NUM_MADDS * C_OVERSAMPLING_RATE * fn_select_integer(1,2,C_SYMMETRY/=ci_non_symmetric)
                                      - fn_select_integer(0,(C_NUM_TAPS rem 2),C_SYMMETRY/=ci_non_symmetric);
      properties.reload_taps        :=properties.actual_taps;
      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE;
      rounding_cnfg := fn_get_rounding_cnfg(
                         C_XDEVICEFAMILY,
                         C_ROUND_MODE,
                         true,
                         C_INPUT_RATE > C_OVERSAMPLING_RATE and C_OVERSAMPLING_RATE>1);
      if rounding_cnfg.use_spare_cycle then
        properties.px_time            :=C_OVERSAMPLING_RATE+1;
      else
        properties.px_time            :=C_OVERSAMPLING_RATE;
      end if;
      properties.odd_symmetry       :=(C_NUM_TAPS rem 2);
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cntrl_dly          := ci_cnfg_lat;
      if C_OVERSAMPLING_RATE = 1 or C_S_DATA_HAS_FIFO = 0 then
        properties.data_dly           := ci_cnfg_lat;
      else
        properties.data_dly           := ci_cnfg_lat-1;
      end if;
      properties.cnfg_read_dly      := 0;
      properties.casc_mod           := properties.cntrl_dly + properties.px_time + fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1);
      if C_OVERSAMPLING_RATE = 1 and C_SYMMETRY/=ci_non_symmetric then
        properties.flip_reload_order := true;
      end if;
      if C_FILTER_TYPE = ci_transpose_single_rate then
        properties.flip_reload_order := true;
      end if;
      if ci_use_sym_multi_col then
        properties.coef_holdoff_update :=  C_NUM_MADDS +  ((ci_num_split_cols-1)*C_COL_PIPE_LEN)
                                           + C_OVERSAMPLING_RATE
                                           - ci_cascade_length
                                           + ci_cntrl_index(0);
      end if;
    when ci_halfband_decimation =>
      if divroundup(divroundup(C_NUM_TAPS,2),2) + 1 = C_OVERSAMPLING_RATE then
        properties.actual_taps        :=(C_OVERSAMPLING_RATE-1) * 2;
        cnfg_and_rld_lat := fn_cnfg_and_reload(C_NUM_FILTS,
                                               C_FILTS_PACKED,
                                               1,
                                               C_COEF_RELOAD=1,
                                               ci_fixed_chan_pat or ci_num_pat=1,
                                               false).latency;
      else
        properties.actual_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE * 2;
        cnfg_and_rld_lat := fn_cnfg_and_reload(C_NUM_FILTS,
                                               C_FILTS_PACKED,
                                               1,
                                               C_COEF_RELOAD=1,
                                               ci_fixed_chan_pat or ci_num_pat=1,
                                               true).latency;
      end if;
      if cnfg_and_rld_lat /= 0 then
        cnfg_and_rld_lat := cnfg_and_rld_lat +1;
      end if;
      properties.full_taps          :=(properties.actual_taps * 2)-1;
      properties.reload_taps        :=properties.actual_taps/2 + 1;
      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE;
      rounding_cnfg := fn_get_rounding_cnfg(
                         C_XDEVICEFAMILY,
                         C_ROUND_MODE,
                         true,
                         C_OUTPUT_RATE > C_OVERSAMPLING_RATE);
      if C_M_DATA_HAS_TREADY= 1 then
        properties.px_time            :=C_OVERSAMPLING_RATE;
        if rounding_cnfg.use_spare_cycle then
          properties.px_time := properties.px_time+1;
        end if;
      else
        properties.px_time            :=C_OUTPUT_RATE;
      end if;
      properties.odd_symmetry       :=1;
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cnfg_read_dly    := 0;
      properties.cntrl_dly         := get_max(2,ci_cnfg_lat);
      properties.data_dly          := get_max(0,ci_cnfg_lat-1);

      properties.casc_mod           := properties.cntrl_dly + properties.px_time + fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1);
      if ci_use_sym_multi_col then
        properties.coef_holdoff_update :=  C_NUM_MADDS +  ((ci_num_split_cols-1)*C_COL_PIPE_LEN)
                                           + C_OVERSAMPLING_RATE
                                           - ci_cascade_length
                                           + ci_cntrl_index(0);
      end if;
    when ci_halfband_interpolation =>
      if divroundup(divroundup(C_NUM_TAPS,2),2) + 1 = C_OVERSAMPLING_RATE then
        properties.actual_taps        :=(C_OVERSAMPLING_RATE-1) * 2;
      else
        properties.actual_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE * 2;
      end if;
      properties.full_taps          :=(properties.actual_taps * 2)-1;
      properties.reload_taps        :=properties.actual_taps/2 + 1;
      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE;
      properties.px_time            :=C_OVERSAMPLING_RATE;
      properties.odd_symmetry       :=1;
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cntrl_dly          := ci_cnfg_lat;
      properties.data_dly           := ci_cnfg_lat-1;
      properties.cnfg_read_dly      := 0;
      properties.op_px_time       := C_OUTPUT_RATE;
      properties.casc_mod         := properties.cntrl_dly + properties.px_time + C_OUTPUT_RATE + fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1);
      if ci_use_sym_multi_col then
        properties.coef_holdoff_update :=  C_NUM_MADDS +  ((ci_num_split_cols-1)*C_COL_PIPE_LEN)
                                           + C_OVERSAMPLING_RATE
                                           - ci_cascade_length
                                           + ci_cntrl_index(0);
      end if;
    when ci_polyphase_decimation | ci_transpose_decimation =>
      properties.actual_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE * C_DECIM_RATE;
      properties.full_taps          :=C_NUM_MADDS * C_OVERSAMPLING_RATE * C_DECIM_RATE * fn_select_integer(1,2,C_SYMMETRY/=ci_non_symmetric)
                                      - fn_select_integer(0,(C_NUM_TAPS rem 2),C_SYMMETRY/=ci_non_symmetric);
      properties.reload_taps        :=properties.actual_taps;
      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE * C_DECIM_RATE;
      rounding_cnfg := fn_get_rounding_cnfg(
                         C_XDEVICEFAMILY,
                         C_ROUND_MODE,
                         true,
                         C_INPUT_RATE > C_OVERSAMPLING_RATE and C_OVERSAMPLING_RATE>1);

      if C_INPUT_RATE > C_OVERSAMPLING_RATE and C_M_DATA_HAS_TREADY = 0 and C_NUM_CHANNELS>1 then
        properties.px_time            :=C_INPUT_RATE;
      elsif rounding_cnfg.use_spare_cycle then
        properties.px_time            :=C_OVERSAMPLING_RATE+1;
      else
        properties.px_time            :=C_OVERSAMPLING_RATE;
      end if;
      properties.odd_symmetry       :=(C_NUM_TAPS rem 2);
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cntrl_dly          := ci_cnfg_lat;
      if C_OVERSAMPLING_RATE = 1 then
        properties.data_dly           := ci_cnfg_lat;
      else
        properties.data_dly           := ci_cnfg_lat-1;
      end if;

      properties.cnfg_read_dly      := 0;
      properties.casc_mod           := properties.cntrl_dly +
                                       properties.px_time +
                                       fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1)+
                                       fn_select_integer(0,-1,C_M_DATA_HAS_TREADY=0 and C_NUM_CHANNELS>1)+
                                       fn_select_integer(0,-(C_INPUT_RATE-C_OVERSAMPLING_RATE),C_INPUT_RATE > C_OVERSAMPLING_RATE and C_M_DATA_HAS_TREADY = 0 and C_NUM_CHANNELS>1 );
      if C_OVERSAMPLING_RATE = 1 and C_SYMMETRY/=ci_non_symmetric then
        properties.flip_reload_order := true;
      end if;
      if C_FILTER_TYPE = ci_transpose_decimation then
        properties.flip_reload_order := true;
      end if;
      if ci_use_sym_multi_col and C_FILTER_TYPE = ci_polyphase_decimation then
        properties.coef_holdoff_update :=  C_NUM_MADDS +  ((ci_num_split_cols-1)*C_COL_PIPE_LEN)
                                           + (C_DECIM_RATE*C_OVERSAMPLING_RATE)
                                           - ci_cascade_length
                                           + ci_cntrl_index(0);
      end if;
    when ci_decimation =>
      properties.actual_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE * C_INTERP_RATE;
      properties.full_taps          :=C_NUM_MADDS * C_OVERSAMPLING_RATE * C_INTERP_RATE * fn_select_integer(1,2,C_SYMMETRY/=ci_non_symmetric)
                                      - fn_select_integer(0,(C_NUM_TAPS rem 2),C_SYMMETRY/=ci_non_symmetric);
      properties.reload_taps        :=properties.actual_taps;
      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE * C_INTERP_RATE;
      properties.px_time            :=C_OVERSAMPLING_RATE;
      rounding_cnfg := fn_get_rounding_cnfg(
                         C_XDEVICEFAMILY,
                         C_ROUND_MODE,
                         true,
                         C_OUTPUT_RATE > C_OVERSAMPLING_RATE and C_OVERSAMPLING_RATE>1);
      if rounding_cnfg.use_spare_cycle then
        properties.px_time            :=C_OVERSAMPLING_RATE+1;
      else
        properties.px_time            :=C_OVERSAMPLING_RATE;
      end if;
      properties.odd_symmetry       :=(C_NUM_TAPS rem 2);
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cntrl_dly          := ci_cnfg_lat+1;
      properties.data_dly           := ci_cnfg_lat;
      properties.cnfg_read_dly      := 1;
      if C_DATA_HAS_TLAST = ci_pass_tlast and C_CONFIG_SYNC_MODE = ci_cnfg_packet_sync and C_INTERP_RATE>1 then
        properties.cnfg_read_dly := properties.cnfg_read_dly + 1;
        properties.cntrl_dly := properties.cntrl_dly + 1;
        properties.data_dly  := properties.data_dly  + 1;
      end if;
      properties.casc_mod           := properties.cntrl_dly + properties.px_time + fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1);
    when ci_polyphase_interpolation | ci_transpose_interpolation =>
      properties.actual_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE * C_INTERP_RATE;
      properties.full_taps          :=C_NUM_MADDS * C_OVERSAMPLING_RATE * C_INTERP_RATE * fn_select_integer(1,2,C_SYMMETRY/=ci_non_symmetric)
                                      - fn_select_integer(0,(C_NUM_TAPS rem 2),C_SYMMETRY/=ci_non_symmetric);
      properties.reload_taps        :=properties.actual_taps;
      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE * C_INTERP_RATE;
      properties.odd_symmetry       :=(C_NUM_TAPS rem 2);
      if (C_OUTPUT_RATE > C_OVERSAMPLING_RATE) and
         ((C_NUM_CHANNELS=1 and ci_fixed_chan_pat and C_DECIM_RATE=1 and (C_SYMMETRY=ci_non_symmetric or (C_INTERP_RATE=2 and properties.odd_symmetry=1))) or
          (C_DECIM_RATE > 1 and C_S_DATA_HAS_FIFO = 1) ) then
        properties.px_time            :=C_OUTPUT_RATE;
      else
        properties.px_time            :=C_OVERSAMPLING_RATE;
      end if;
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cntrl_dly          := ci_cnfg_lat;
      if C_OVERSAMPLING_RATE = 1 then
        properties.data_dly           := ci_cnfg_lat;
      else
        properties.data_dly           := ci_cnfg_lat-1;
      end if;
      properties.cnfg_read_dly      := 0;
      properties.casc_mod           := properties.cntrl_dly + C_OVERSAMPLING_RATE + fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1);
      if C_OVERSAMPLING_RATE = 1 and C_SYMMETRY/=ci_non_symmetric then
        properties.flip_reload_order := true;
      end if;
      if C_FILTER_TYPE = ci_transpose_interpolation then
        properties.flip_reload_order := true;
      end if;
      if ci_use_sym_multi_col and C_FILTER_TYPE = ci_polyphase_interpolation then
        properties.coef_holdoff_update :=  C_NUM_MADDS +  ((ci_num_split_cols-1)*C_COL_PIPE_LEN)
                                           + (C_OVERSAMPLING_RATE*C_INTERP_RATE)
                                           - ci_cascade_length
                                           + ci_cntrl_index(0);
      end if;
    when ci_hilbert | ci_halfband =>
      properties.actual_taps        :=2 * C_NUM_MADDS * C_OVERSAMPLING_RATE;
      properties.full_taps          :=2 * C_NUM_MADDS * C_OVERSAMPLING_RATE * fn_select_integer(1,2,C_SYMMETRY/=ci_non_symmetric)
                                      - fn_select_integer(0,(C_NUM_TAPS rem 2),C_SYMMETRY/=ci_non_symmetric);
      properties.reload_taps        :=C_NUM_MADDS * C_OVERSAMPLING_RATE;
      if C_FILTER_TYPE = ci_halfband then
        properties.reload_taps := properties.reload_taps + 1;
      end if;

      properties.madd_mem_depth     :=C_OVERSAMPLING_RATE;
      rounding_cnfg := fn_get_rounding_cnfg(
                         C_XDEVICEFAMILY,
                         C_ROUND_MODE,
                         true,
                         C_INPUT_RATE > C_OVERSAMPLING_RATE and C_OVERSAMPLING_RATE>1);
      if rounding_cnfg.use_spare_cycle then
        properties.px_time            :=C_OVERSAMPLING_RATE+1;
      else
        properties.px_time            :=C_OVERSAMPLING_RATE;
      end if;
      properties.odd_symmetry       :=(C_NUM_TAPS rem 2);
      properties.ipbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.ipbuff_thres       :=1;
      properties.opbuff_size        :=get_max(ci_default_axi_fifo_depth,2**log2roundup(C_NUM_CHANNELS));
      properties.opbuff_thres       :=1;
      properties.cntrl_dly          := ci_cnfg_lat;
      if C_OVERSAMPLING_RATE = 1 or C_S_DATA_HAS_FIFO = 0 then
        properties.data_dly           := ci_cnfg_lat;
      else
        properties.data_dly           := ci_cnfg_lat-1;
      end if;
      properties.cnfg_read_dly      := 0;
      properties.casc_mod           := properties.cntrl_dly + properties.px_time + fn_select_integer(0,1,C_M_DATA_HAS_TREADY=1);
      if C_OVERSAMPLING_RATE = 1 and C_SYMMETRY/=ci_non_symmetric then
        properties.flip_reload_order := true;
      end if;
      if ci_use_sym_multi_col then
        properties.coef_holdoff_update :=  C_NUM_MADDS +  ((ci_num_split_cols-1)*C_COL_PIPE_LEN)
                                           + C_OVERSAMPLING_RATE
                                           - ci_cascade_length
                                           + ci_cntrl_index(0);
      end if;
    when others =>
      report "FAILURE: FIR Compiler: Invalid filter type." severity failure;
    end case;

    if (C_FILTER_TYPE = ci_transpose_single_rate or
          C_FILTER_TYPE = ci_transpose_decimation or
          C_FILTER_TYPE = ci_transpose_interpolation ) and
         fn_is_data_reset(C_HAS_ARESETn) = 1 then
        properties.coef_holdoff_update := (C_NUM_MADDS-1)*fn_mem_lat(C_XDEVICEFAMILY,C_COEF_MEMTYPE,ci_read_first,1);
      end if;

    return properties;
  end function;

  constant ci_fir_properties : t_fir_properties := fn_gen_fir_properties;



  constant ci_max_int_width : integer :=  31;
  constant ci_maxint        : integer :=    2**(ci_max_int_width-1) - 1;
  constant ci_minint        : integer := -1*2**(ci_max_int_width-1);

  constant ci_split_dwidth : integer := fn_select_integer(C_DATA_WIDTH,(C_DATA_WIDTH+1)/2,C_DATA_WIDTH+ci_data_sign>=ci_max_int_width);

  constant ci_dpages : integer:= fn_select_integer(1,2,C_DATA_WIDTH+ci_data_sign>=ci_max_int_width);

  constant ci_split_cwidth : integer := get_min( (ci_max_int_width-ci_split_dwidth-fn_select_integer(ci_data_sign,1,ci_dpages>1)) , C_COEF_WIDTH );

  constant ci_cpages : integer := (C_COEF_WIDTH+ci_split_cwidth-1)/ci_split_cwidth;

  constant ci_split_accum : boolean :=  ci_accum_width >= ci_max_int_width
                                        or (C_DATA_WIDTH+fn_select_integer(ci_data_sign,1,ci_dpages>1)+C_COEF_WIDTH) >= ci_max_int_width
                                        or (ci_cpages>1);

  constant ci_signed_output : boolean := ((ci_coef_sign=0) or (ci_data_sign=0));

  constant ci_main_buffer_depth : integer := ci_fir_properties.full_taps*C_ZERO_PACKING_FACTOR+
                                             20
                                             + fn_select_integer(0,2*C_NUM_CHANNELS,C_FILTER_TYPE = ci_halfband_interpolation)
                                             + fn_select_integer(0,C_DECIM_RATE*C_NUM_CHANNELS,C_FILTER_TYPE = ci_decimation or C_FILTER_TYPE = ci_polyphase_decimation or C_FILTER_TYPE = ci_transpose_decimation)
                                             + fn_select_integer(0,2 * C_INTERP_RATE*C_NUM_CHANNELS+ci_fir_properties.cntrl_dly+1+C_OUTPUT_RATE,C_FILTER_TYPE = ci_polyphase_interpolation or
                                                                                                                                                C_FILTER_TYPE = ci_transpose_interpolation);

  constant ci_coeff_snap_shot : boolean := C_COEF_RELOAD = 1 and
                                           ( (C_FILTER_TYPE = ci_polyphase_interpolation and not(C_NUM_CHANNELS=1 and (C_SYMMETRY=ci_non_symmetric or (C_INTERP_RATE=2 and ci_fir_properties.odd_symmetry=1)))) or
                                             (C_FILTER_TYPE = ci_polyphase_decimation and C_M_DATA_HAS_TREADY = 0 and C_NUM_CHANNELS > 1) or
                                             (C_FILTER_TYPE = ci_halfband_interpolation) );

  constant ci_data_width_concat : integer := C_DATA_WIDTH * ci_num_paths;
  constant ci_output_width_concat : integer := C_OUTPUT_WIDTH * ci_num_paths;

  constant ci_tlast_width : integer := fn_select_integer(0,1,C_DATA_HAS_TLAST/=ci_no_tlast);

  constant ci_s_tuser_width     : integer := fn_select_integer(0,C_S_DATA_TUSER_WIDTH,C_S_DATA_HAS_TUSER/=ci_no_tuser);
  constant ci_s_tdata_fields    : t_int_array(ci_num_paths-1 downto 0):=(others=>C_DATA_WIDTH);
  subtype s_s_tdata is integer range ci_data_width_concat-1 downto 0;
  subtype s_s_tuser is integer range ci_s_tuser_width+s_s_tdata'HIGH downto s_s_tdata'HIGH+1;
  subtype s_s_tlast is integer range ci_tlast_width+s_s_tuser'HIGH downto s_s_tuser'HIGH+1;
  constant ci_s_tuser_chanid_width : integer := fn_select_integer(0,log2roundup(C_NUM_CHANNELS),C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser+ci_user_tuser);
  constant ci_s_tuser_user_width   : integer := fn_select_integer(0,C_S_DATA_TUSER_WIDTH-ci_s_tuser_chanid_width,C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser+ci_user_tuser);
  subtype s_s_tuser_chanid is integer range ci_s_tuser_chanid_width-1 downto 0;
  subtype s_s_tuser_user   is integer range ci_s_tuser_user_width+s_s_tuser_chanid'HIGH downto s_s_tuser_chanid'HIGH+1;

  constant ci_m_tuser_width : integer := fn_select_integer(0,C_M_DATA_TUSER_WIDTH,C_M_DATA_HAS_TUSER/=ci_no_tuser or C_HAS_ARESETn = ci_cntrl_only_reset);
  subtype s_m_tdata is integer range      fn_select_integer(
                                            0,
                                            ci_output_width_concat,
                                            C_FILTER_TYPE /= ci_hilbert)-1 downto 0;
  subtype s_m_tdata_real is integer range fn_select_integer(
                                            0,
                                            ci_data_width_concat,
                                            C_FILTER_TYPE = ci_hilbert)+s_m_tdata'HIGH downto s_m_tdata'HIGH+1;
  subtype s_m_tdata_imag is integer range fn_select_integer(
                                            0,
                                            ci_output_width_concat,
                                            C_FILTER_TYPE = ci_hilbert)+s_m_tdata_real'HIGH downto s_m_tdata_real'HIGH+1;
  subtype s_m_tuser is integer range ci_m_tuser_width+s_m_tdata_imag'HIGH downto s_m_tdata_imag'HIGH+1;
  subtype s_m_tlast is integer range ci_tlast_width+s_m_tuser'HIGH downto s_m_tuser'HIGH+1;
  constant ci_m_tuser_data_valid_width : integer := fn_select_integer(0,1,C_HAS_ARESETn = ci_cntrl_only_reset);
  constant ci_m_tuser_chanid_width : integer := fn_select_integer(0,log2roundup(C_NUM_CHANNELS),C_M_DATA_HAS_TUSER = ci_chanid_tuser or C_M_DATA_HAS_TUSER = ci_chanid_tuser+ci_user_tuser);
  constant ci_m_tuser_user_width   : integer := fn_select_integer(0,C_M_DATA_TUSER_WIDTH-ci_m_tuser_chanid_width-ci_m_tuser_data_valid_width,C_M_DATA_HAS_TUSER = ci_user_tuser or C_M_DATA_HAS_TUSER = ci_chanid_tuser+ci_user_tuser);
  subtype s_m_tuser_data_valid is integer range ci_m_tuser_data_valid_width-1 downto 0;
  subtype s_m_tuser_chanid is integer range ci_m_tuser_chanid_width+s_m_tuser_data_valid'HIGH downto s_m_tuser_data_valid'HIGH+1;
  subtype s_m_tuser_user   is integer range ci_m_tuser_user_width+s_m_tuser_chanid'HIGH downto s_m_tuser_chanid'HIGH+1;

  constant ci_tuser_dly_width : integer := ci_s_tuser_user_width +
                                           fn_select_integer(0,1,C_DATA_HAS_TLAST = ci_pass_tlast);

  constant ci_output_hilb_widths : t_int_array := fn_interleave_widths(ci_s_tdata_fields,ci_output_widths);

  constant ci_fsel_width      : integer := fn_select_integer(
                                              fn_select_integer(
                                                0,
                                                1,
                                                ci_fixed_chan_pat or ci_num_pat=1),
                                              log2roundup(C_NUM_FILTS),
                                              C_NUM_FILTS>1);
  constant ci_fsel_8bit_width : integer := fn_select_integer(
                                             divroundup(ci_fsel_width,8)*8,
                                             1,
                                             C_HAS_CONFIG_CHANNEL=0);
  constant ci_chanpat_width : integer := fn_select_integer(0,log2roundup(ci_num_pat),not ci_fixed_chan_pat and ci_num_pat>1);

  subtype s_fsel is integer range ci_fsel_width-1 downto 0;
  subtype s_chanpat is integer range ci_chanpat_width+ci_fsel_8bit_width-1 downto ci_fsel_8bit_width;

  type t_bool_array is array (integer range <>) of boolean;
  type t_coefficients     is array (0 to   ci_fir_properties.full_taps-1) of integer;
  type t_coeffs_array     is array (integer range <>) of std_logic_vector(ci_coef_width_physical-1 downto 0);
  subtype t_coefficients_slv is t_coeffs_array(0 to   ci_fir_properties.full_taps-1);
  subtype t_reload_coeffs is t_coeffs_array(0 to ci_fir_properties.reload_taps-1);

  type t_split_coeff      is array (0 to ci_cpages-1) of integer;
  type t_coeff_pages      is array (0 to ci_cpages-1) of t_coefficients;
  type t_coeff_array      is array (0 to
                                      fn_select_integer(
                                        2**log2roundup(C_NUM_FILTS+fn_select_integer(0,C_NUM_RELOAD_SLOTS,C_COEF_RELOAD=1)),
                                        C_NUM_CHANNELS,
                                        C_FILTER_TYPE=ci_channelizer_receiver or C_FILTER_TYPE=ci_channelizer_transmitter)-1) of t_coeff_pages;
  type t_coeff_snap_shot is array (0 to
                                   fn_select_integer(
                                     0,
                                     ci_fir_properties.cntrl_dly-1+
                                     fn_select_integer(0,1,C_FILTER_TYPE = ci_polyphase_interpolation and ci_fir_properties.px_time=1),
                                     ci_coeff_snap_shot)) of t_coeff_array;

  type t_tuser_array      is array (integer range <>) of std_logic_vector(ci_tuser_dly_width-1 downto 0);
  type t_main_buffer is
  record
    fsel            : t_int_array(0 to ci_main_buffer_depth-1);
    tuser           : t_tuser_array(0 to ci_main_buffer_depth-1);
    chanpat         : t_int_array(0 to ci_main_buffer_depth-1);
    chanpat_updated : t_bool_array(0 to ci_main_buffer_depth-1);
  end record;
  type t_main_buffer_array is array (0 to ci_num_paths-1,0 to C_NUM_CHANNELS-1) of t_main_buffer;

  type t_pointers         is array (0 to           C_NUM_CHANNELS-1) of integer;


  type t_cascade is
  record
    tuser      : std_logic_vector(s_m_tuser_user);
    tlast      : std_logic;
    chan_id    : integer;
    valid      : boolean;
    data_valid : std_logic_vector(s_m_tuser_data_valid);
  end record;
  type t_cascade_dly is array (0 to C_LATENCY-ci_fir_properties.casc_mod-1) of t_cascade;

  constant ci_buffer_width : integer := get_max(
                                          s_s_tlast'HIGH,
                                          get_max(
                                            s_m_tlast'HIGH,
                                            s_chanpat'HIGH+1))+1;
  type t_buffer is array (integer range <>) of std_logic_vector(ci_buffer_width-1 downto 0);



  procedure prc_dly( ip: in boolean; variable dly : inout t_bool_array; variable op : out boolean) is
  begin
    if dly'LENGTH > 0 then
      dly(dly'HIGH downto 1) := dly(dly'HIGH-1 downto 0);
      dly(0) := ip;
      op     := dly(dly'HIGH);
    else
      op := ip;
    end if;
  end procedure prc_dly;

  procedure prc_dly( ip: in integer; variable dly : inout t_int_array; variable op : out integer) is
  begin
    if dly'LENGTH > 0 then
      dly(dly'HIGH downto 1) := dly(dly'HIGH-1 downto 0);
      dly(0) := ip;
      op     := dly(dly'HIGH);
    else
      op := ip;
    end if;
  end procedure prc_dly;

  procedure prc_dly( ip: in std_logic_vector; variable dly : inout t_buffer; variable op : out std_logic_vector) is
  begin
    if dly'LENGTH > 0 then
      dly(dly'HIGH downto 1) := dly(dly'HIGH-1 downto 0);
      dly(0)(ip'range) := ip;
      op     := dly(dly'HIGH)(op'range);
    else
      op := ip;
    end if;
  end procedure prc_dly;

  procedure prc_fifo( write, read     : in boolean;
                      data_in  : in std_logic_vector;
                      signal buff     : inout t_buffer;
                      signal ptr      : inout integer;
                      signal data_out : out std_logic_vector;
                      signal data_rdy  : inout boolean;
                      signal data_flag : inout boolean;
                      signal full     : out std_logic;
                      rdy_threshold   : integer;
                      full_threshold  : integer := -1;
                      flag_threshold  : integer := 0 ) is
    variable read_qual  : boolean;
    variable full_thres : integer := fn_select_integer(full_threshold,buff'HIGH,full_threshold=-1);
  begin
    read_qual := read and (ptr > -1 );

    if write then
       buff(buff'HIGH downto 1) <= buff(buff'HIGH-1 downto 0);
       buff(0)(data_in'HIGH downto 0) <= data_in;
    end if;

    if read then
      data_out <= buff( (ptr mod (buff'HIGH+1)) )(data_out'HIGH downto 0);
    end if;

    if write and read_qual then
      null;
    elsif write and not read_qual then
      ptr <= ptr + 1;
      data_rdy <= false;
      if ptr >= rdy_threshold-2 then
        data_rdy <= true;
      end if;
      full <= '0';
      if ptr >= full_thres-1 then
        full <= '1';
      end if;
      data_flag <= false;
      if ptr >= flag_threshold-2 then
        data_flag <= true;
      end if;
    elsif not write and read_qual then
      ptr <= ptr - 1;
      data_rdy <= false;
      if ptr > rdy_threshold-1 then
        data_rdy <= true;
      end if;
      if full_threshold = -1 then
        full <= '0';
      else
        if ptr <= full_thres-1 then
          full <= '0';
        end if;
      end if;
      data_flag <= false;
      if ptr > flag_threshold-1 then
        data_flag <= true;
      end if;
    end if;

  end procedure prc_fifo;

  procedure prc_push_main_buffer( chan               : integer;
                                 variable ptrs      : inout t_pointers;
                                 variable main_buff : inout t_main_buffer_array;
                                 data_in            : std_logic_vector;
                                 tuser_in           : std_logic_vector;
                                 fsel_in            : integer:=0;
                                 chanpat_in         : integer:=0;
                                 chanpat_upd_in     : boolean:=false;
                                 reset_all_fsel     : boolean:=false) is
  begin
    for path in 0 to ci_num_paths-1 loop

      main_buff(path,chan).tuser(1 to ci_main_buffer_depth-1) :=  main_buff(path,chan).tuser(0 to ci_main_buffer_depth-2);
      main_buff(path,chan).tuser(0)                           := tuser_in;

      if reset_all_fsel then
        main_buff(path,chan).fsel(0 to ci_main_buffer_depth-1) := (others=>fsel_in);
      else
        main_buff(path,chan).fsel(1 to ci_main_buffer_depth-1) :=  main_buff(path,chan).fsel(0 to ci_main_buffer_depth-2);
        main_buff(path,chan).fsel(0)                           := fsel_in;
      end if;

      main_buff(path,chan).chanpat(1 to ci_main_buffer_depth-1) :=  main_buff(path,chan).chanpat(0 to ci_main_buffer_depth-2);
      main_buff(path,chan).chanpat(0)                           := chanpat_in;

      main_buff(path,chan).chanpat_updated(1 to ci_main_buffer_depth-1) :=  main_buff(path,chan).chanpat_updated(0 to ci_main_buffer_depth-2);
      main_buff(path,chan).chanpat_updated(0)                           := chanpat_upd_in;
    end loop;
    ptrs(chan) := ptrs(chan) + 1;
  end procedure prc_push_main_buffer;

  procedure prc_flush_main_buffer( variable ptrs      : inout t_pointers;
                                   variable main_buff : inout t_main_buffer_array) is
  begin
  end procedure prc_flush_main_buffer;



  type t_reorder_info is
  record
    combination : integer;
    index_1     : integer;
    index_2     : integer;
  end record;

  type t_reorder_info_array  is array (integer range <>) of t_reorder_info;

  constant ci_mif_num_filts : integer := fn_select_integer(
                                            C_NUM_FILTS,
                                            C_NUM_FILTS + C_NUM_RELOAD_SLOTS,
                                            C_COEF_RELOAD = 1);

  type t_mif_contents is
  record
    reorder_info : t_reorder_info_array(0 to ci_fir_properties.reload_taps-1);
    coeffs       : t_coeffs_array(0 to (ci_mif_num_filts * ci_fir_properties.reload_taps)-1);
    num_paths    : integer;
    coeff_cnt    : integer;
  end record;

  procedure prc_read_helper(line_in,line_out: inout line) is
    variable read_char : character;
    variable ignore_white_space : boolean := true;
  begin

    ignore_white_space := true;

    readchars : loop
      read(line_in,read_char);
      if read_char /= ' ' then
        ignore_white_space := false;
        write(line_out,read_char);
      elsif not ignore_white_space then
        exit readchars;
      end if;
      if line_in'LENGTH = 0 then
        exit readchars;
      end if;
    end loop;
  end procedure;

  procedure prc_clr_line(line_in : inout line) is
    variable read_char : character;
  begin
    if line_in'LENGTH > 0 then
      loop
        read(line_in,read_char);
        if line_in'LENGTH=0 then
          exit;
        end if;
      end loop;
    end if;
  end procedure;

  impure function fn_read_mif_file return t_mif_contents is
    constant read_width   : integer := 32;
    variable coefficient  : std_logic_vector(read_width-1 downto 0);
    file     filepointer  : text;
    variable filestatus   : file_open_status;
    variable fileline     : line;
    variable linecnt,
             path,
             madd,
             filter,
             madd_i,
             coeff_i: integer :=0;
    variable mif_contents : t_mif_contents;
    variable read_line        : line;
  begin
    mif_contents.coeffs     :=(others=>(others=>'0'));
    mif_contents.num_paths  := 1;
    mif_contents.coeff_cnt  := 0;

    file_open(filestatus,filepointer,C_ELABORATION_DIR&C_COEF_FILE,read_mode);

    while not(endfile(filepointer)) and linecnt<C_COEF_FILE_LINES loop
      readline(filepointer, fileline);

      prc_read_helper(fileline,read_line);
      hread(read_line, coefficient);
      prc_clr_line(read_line);

      prc_read_helper(fileline,read_line);
      read(read_line, path);
      prc_clr_line(read_line);

      prc_read_helper(fileline,read_line);
      read(read_line, madd);
      prc_clr_line(read_line);

      prc_read_helper(fileline,read_line);
      read(read_line, filter);
      prc_clr_line(read_line);

      if madd = -1 then
        coeff_i:=(filter*ci_fir_properties.reload_taps) + ((C_NUM_MADDS)*ci_fir_properties.madd_mem_depth)+madd_i;
      else
        if ci_fir_properties.flip_reload_order then
          coeff_i:=(filter*ci_fir_properties.reload_taps) + ((madd)*ci_fir_properties.madd_mem_depth)+madd_i;
        else
          coeff_i:=(filter*ci_fir_properties.reload_taps) + ((C_NUM_MADDS-madd-1)*ci_fir_properties.madd_mem_depth)+madd_i;
        end if;
      end if;


      if path = 0 and filter = 0 then

        prc_read_helper(fileline,read_line);
        read(read_line, mif_contents.reorder_info(coeff_i).combination);
        prc_clr_line(read_line);
        prc_read_helper(fileline,read_line);
        read(read_line,  mif_contents.reorder_info(coeff_i).index_1);
        prc_clr_line(read_line);
        prc_read_helper(fileline,read_line);
        read(read_line, mif_contents.reorder_info(coeff_i).index_2);
        prc_clr_line(read_line);

        mif_contents.coeff_cnt := mif_contents.coeff_cnt + 1;
      end if;

      if path /= -1 then
        madd_i := ( madd_i + 1 ) mod ci_fir_properties.madd_mem_depth;

        if madd = -1 then
          madd_i := 0;
        end if;
      end if;
      linecnt := linecnt + 1;

      if path > 0 then
        mif_contents.num_paths  := 2;
        mif_contents.coeffs(coeff_i)(ci_coef_path_widths(0)+ci_coef_path_widths(path)-1 downto ci_coef_path_widths(0)) := coefficient(ci_coef_path_widths(path)-1 downto 0);
      elsif path = 0 then
        mif_contents.coeffs(coeff_i)(ci_coef_path_widths(0)-1 downto 0) := coefficient(ci_coef_path_widths(0)-1 downto 0);
      end if;
    end loop;
    return mif_contents;
  end fn_read_mif_file;

  constant mif_contents : t_mif_contents := fn_read_mif_file;

  function do_coeff_reorder ( reloaded : t_reload_coeffs ) return t_coeff_pages is

    variable coeffs   : t_coefficients_slv:= (others=>(others=>'0'));

    type t_coefficients_slv_paths is array (0 to mif_contents.num_paths-1) of t_coefficients_slv;
    variable coeffs_paths   : t_coefficients_slv_paths := (others=>(others=>(others=>'0')));

    constant inter_sym_shift : integer:=fn_select_integer(0,C_INTERP_RATE/2,
                                                        C_FILTER_TYPE=ci_polyphase_interpolation and C_SYMMETRY /= ci_non_symmetric
                                                        and ci_fir_properties.odd_symmetry=1
                                                        and (C_INTERP_RATE rem 2 >0));

    constant full_taps_adjusted : integer := ci_fir_properties.full_taps-2*inter_sym_shift;

    variable path_coef: signed(ci_coef_width_physical-1 downto 0);

    variable ret_coeffs : t_coeff_pages := (others=>(others=>0));

    variable upper_src : integer;
  begin

    for i in 0 to ci_fir_properties.reload_taps-1 loop

      if C_FILTER_TYPE=ci_polyphase_interpolation and C_SYMMETRY /= ci_non_symmetric and
         not (C_INTERP_RATE=2 and ci_fir_properties.odd_symmetry=1) then
        if mif_contents.reorder_info(i).index_1 > -1 then
          if mif_contents.num_paths=1 then
            coeffs(mif_contents.reorder_info(i).index_1) := std_logic_vector(signed(coeffs(mif_contents.reorder_info(i).index_1)) + signed(reloaded(i)));
            if mif_contents.reorder_info(i).combination=1 then
              coeffs(mif_contents.reorder_info(i).index_2) := std_logic_vector(signed(coeffs(mif_contents.reorder_info(i).index_2)) + signed(reloaded(i)));
            elsif mif_contents.reorder_info(i).combination=2 then
              coeffs(mif_contents.reorder_info(i).index_2) := std_logic_vector(signed(coeffs(mif_contents.reorder_info(i).index_2)) - signed(reloaded(i)));
            else
              coeffs(mif_contents.reorder_info(i).index_1) := std_logic_vector(signed(coeffs(mif_contents.reorder_info(i).index_1)) + signed(reloaded(i)));
            end if;
          else
            for path in 0 to 1 loop
              if path=0 then
                path_coef:=signed(fn_ext_bus(reloaded(i)(ci_coef_path_widths(0)-1 downto 0),ci_coef_width_physical,c_signed));
              else
                path_coef:=signed(fn_ext_bus(reloaded(i)(fn_sum_vals(ci_coef_path_widths(path downto 0))-1 downto fn_sum_vals(ci_coef_path_widths(path-1 downto 0))),ci_coef_width_physical,c_signed));
              end if;
              coeffs_paths(path)(mif_contents.reorder_info(i).index_1) := std_logic_vector(signed(coeffs_paths(path)(mif_contents.reorder_info(i).index_1)) + path_coef);
              if mif_contents.reorder_info(i).combination=1 then
                coeffs_paths(path)(mif_contents.reorder_info(i).index_2) := std_logic_vector(signed(coeffs_paths(path)(mif_contents.reorder_info(i).index_2)) + path_coef);
              elsif mif_contents.reorder_info(i).combination=2 then
                coeffs_paths(path)(mif_contents.reorder_info(i).index_2) := std_logic_vector(signed(coeffs_paths(path)(mif_contents.reorder_info(i).index_2)) - path_coef);
              else
                coeffs_paths(path)(mif_contents.reorder_info(i).index_1) := std_logic_vector(signed(coeffs_paths(path)(mif_contents.reorder_info(i).index_1)) + path_coef);
              end if;
            end loop;
          end if;
        end if;
      else
        if mif_contents.reorder_info(i).index_1 > -1 then
          coeffs(mif_contents.reorder_info(i).index_1) := std_logic_vector(unsigned(coeffs(mif_contents.reorder_info(i).index_1)) + unsigned(reloaded(i)));
        end if;
      end if;
    end loop;

    if C_FILTER_TYPE=ci_polyphase_interpolation and C_SYMMETRY /= ci_non_symmetric and
       not (C_INTERP_RATE=2 and ci_fir_properties.odd_symmetry=1) then
      if mif_contents.num_paths=1 then
        for i in 0 to ci_fir_properties.reload_taps-1 loop

          if ci_coef_sign = ci_signed then
            coeffs(i) := std_logic_vector(resize(signed(coeffs(i)(ci_coef_width_physical-1 downto 1)),ci_coef_width_physical));
          else
            coeffs(i) := std_logic_vector(resize(unsigned(coeffs(i)(ci_coef_width_physical-1 downto 1)),ci_coef_width_physical));
          end if;


        end loop;
      else

        for i in 0 to ci_fir_properties.reload_taps-1 loop
          upper_src := ci_coef_path_widths(0) - 2;


          coeffs(i)(upper_src-1 downto 0):=coeffs_paths(0)(i)(upper_src downto 1);

          coeffs(i)(C_COEF_WIDTH-1 downto upper_src):=
                                                      coeffs_paths(1)(i)(C_COEF_WIDTH-upper_src downto 1);
          if ci_coef_sign = ci_signed then
            coeffs(i)(ci_coef_width_physical-1 downto C_COEF_WIDTH) := (others=>coeffs(i)(C_COEF_WIDTH-1));
          else
            coeffs(i)(ci_coef_width_physical-1 downto C_COEF_WIDTH) := (others=>'0');
          end if;
        end loop;

      end if;
    end if;

    if C_SYMMETRY/=ci_non_symmetric then
      for i in 1 to (full_taps_adjusted)/2 loop
        if C_SYMMETRY=ci_neg_symmetric then
          coeffs(full_taps_adjusted-i) := std_logic_vector( signed(not(coeffs(i-1)))+1 );
        else
          coeffs(full_taps_adjusted-i) := coeffs(i-1);
        end if;
      end loop;
    end if;

    for tap in coeffs'range loop
      for k in 0 to ci_cpages-1 loop
        if k=ci_cpages-1 then
          if ci_coef_sign=ci_signed then
            ret_coeffs(k)(tap) := to_integer(signed(coeffs(tap)( get_min(((k+1)*ci_split_cwidth)-1,coeffs(tap)'HIGH) downto (k*ci_split_cwidth) )));
          else
            ret_coeffs(k)(tap) := to_integer(unsigned(coeffs(tap)( get_min(((k+1)*ci_split_cwidth)-1,coeffs(tap)'HIGH) downto (k*ci_split_cwidth) )));
          end if;
        else
          ret_coeffs(k)(tap) := to_integer(unsigned(coeffs(tap)( ((k+1)*ci_split_cwidth)-1 downto (k*ci_split_cwidth) )));
        end if;
      end loop;
    end loop;

    return ret_coeffs;

  end do_coeff_reorder;

  function fn_get_coefficients return t_coeff_array is
    variable reordered_coeffs : t_coeff_array := (others=>(others=>(others=>0)));
  begin
    if false then
    else
      for filt in 0 to C_NUM_FILTS-1 loop
        reordered_coeffs(filt):=do_coeff_reorder(mif_contents.coeffs( filt * ci_fir_properties.reload_taps to ((filt+1) * ci_fir_properties.reload_taps) - 1 ));
      end loop;
    end if;
    return reordered_coeffs;
  end fn_get_coefficients;

  signal coeff_sets : t_coeff_array := fn_get_coefficients;
  signal coeff_snap_shot : t_coeff_snap_shot;


  signal clk,
         ce_int,
         ce_px,
         sclr_int,
         areset     : std_logic;
  signal sclr_end   : std_logic := fn_select_sl('0','1',C_S_DATA_HAS_FIFO=0);

  signal ipbuff             : t_buffer(ci_fir_properties.ipbuff_size-1 downto 0) := (others=>(others=>'0'));
  signal ipbuff_out         : std_logic_vector(s_s_tlast'HIGH downto 0);
  signal ipbuff_ptr         : integer := -1;
  signal ipbuff_read_addr,
         ipbuff_write_addr  : integer := 0;
  signal ipbuff_ready,
         ipbuff_flag,
         ipbuff_read,
         ipbuff_page_read   : boolean   := false;
  signal ipbuff_full        : std_logic := '0';
  signal s_data_tready      : std_logic := '0';
  signal ip_write           : boolean   := false;
  signal ipbuff_in          : std_logic_vector(s_s_tlast'HIGH downto 0);

  signal s_data_tvalid : boolean;

  signal ip_px_rdy          : boolean := (C_S_DATA_HAS_FIFO=1);
  signal data_ready         : boolean;
  signal ip_px_cnt,
         ip_chan_cnt,
         ip_phase_cnt,
         op_px_cnt,
         op_chan_cnt,
         op_phase_cnt,
         output_chan_pat,
         new_samples_for_op,
         new_samples_for_next_op,
         data_vector_cnt
         : integer := 0;
  signal cascade_dly        : t_cascade_dly := (others=>(
                                                         tuser=>(others=>'0'),
                                                         tlast=>'0',
                                                         chan_id=>0,
                                                         valid=>false,
                                                         data_valid=>(others=>'0')
                                                         ));
  signal ip_px_cnt_en,
         ip_px_cnt_max,
         op_px_cnt_max,
         op_px_cnt_en,
         get_new_data,
         get_fifo_data,
         ip_px_cnt_max_actual
         : boolean := false;

  signal opbuff             : t_buffer(ci_fir_properties.opbuff_size-1 downto 0) := (others=>(others=>'0'));
  signal opbuff_out         : std_logic_vector(s_m_tlast'HIGH downto 0):=(others=>'0');
  signal opbuff_ptr         : integer := -1;
  signal opbuff_rdy,
         opbuff_flag: boolean := false;
  signal back_throttle,
         m_data_tvalid      : std_logic := '0';

  signal cnfgbuff           : t_buffer(get_max(ci_default_axi_fifo_depth,2**log2roundup(
                                                       fn_select_integer(
                                                         1,
                                                         C_NUM_CHANNELS+2,
                                                         C_CONFIG_PACKET_SIZE=ci_chan_cnfg)))-1 downto 0) := (others=>(others=>'0'));
  signal cnfgbuff_out,
         cnfgbuff_in        : std_logic_vector(s_chanpat'HIGH+1 downto 0);
  signal cnfgbuff_ptr       : integer := -1;
  signal cnfgbuff_ready,
         cnfgbuff_flag,
         cnfgbuff_read,
         cnfgbuff_write     : boolean := false;
  signal cnfgbuff_full      : std_logic := '0';
  signal s_cnfg_tready      : std_logic := '0';
  signal s_reload_tready    : std_logic := '1';
  signal updating_cnfg,
         next_cnfg,
         update_cnfg_first,
         check_tlast,
         tlast_ored,
         next_chan,
         reset_fill
                            : boolean := false;
  signal update_cnfg        : boolean := C_FILTER_TYPE=ci_polyphase_decimation or
                                         C_FILTER_TYPE=ci_transpose_decimation;
  signal packet_end         : boolean := false;
  signal update_chan
         : integer := 0;
  signal fsel               : integer := 0;
  signal get_reload_filt    : boolean := (C_NUM_FILTS > 1);
  signal update_fsel,
         update_fsel_dly,
         holdoff_update_fsel: boolean := false;
  signal rlded_filt,
         rlded_filt_slot    : t_int_array(C_NUM_RELOAD_SLOTS-1 downto 0) := (others=>0);
  signal rlded_cnt          : integer := -1;
  signal updated_fsel,
         updated_fsel_slot,
         freed_slot         : integer := 0;
  signal chanpat            : integer := 0;
  signal chanpat_updated    : boolean := false;
  signal coef_holdoff_update : integer :=0;
begin

  clk <= ACLK;
  ce_int <= ACLKEN when C_HAS_ACLKEN = 1 else '1';
  ce_px  <=  not back_throttle and ACLKEN when C_HAS_ACLKEN=1 and C_M_DATA_HAS_TREADY = 1 else
             not back_throttle when C_M_DATA_HAS_TREADY = 1 else
             ACLKEN  when C_HAS_ACLKEN=1 else '1';
  areset <= not ARESETn when fn_is_cntrl_reset(C_HAS_ARESETn) = 1 else '0';

  s_axis_data_send_ce          <= ce_px;
  s_axis_config_send_packet_ce <= ce_px;
  s_axis_reload_packet_ce      <= ce_int;
  reset_model                  <= sclr_int;

  g_sclr : if fn_is_cntrl_reset(C_HAS_ARESETn)=1 generate
    signal reset_history : std_logic_vector(2 downto 0) := (others => '0');
  begin
    i_sclr: process(CLK)
    begin
      if (rising_edge(CLK)) then
        sclr_int<= not ARESETn;
      end if;
    end process;

    g_sclr_end : if C_S_DATA_HAS_FIFO = 0 generate
    begin
      process(CLK)
      begin
        if (rising_edge(CLK)) then
          if sclr_int = '1' and ARESETn = '1' then
            sclr_end <= '1';
          elsif ce_px = '1' then
            sclr_end  <= '0';
          end if;
        end if;
      end process;
    end generate g_sclr_end;

    check_reset: process (aclk) is
    begin
      if rising_edge(aclk) then
        reset_history <= reset_history(1 downto 0) & ARESETn;
        assert not(reset_history = "010" or reset_history = "101")
          report "WARNING: aresetn must be asserted or deasserted for a minimum of 2 cycles"
          severity warning;
      end if;
    end process check_reset;
  end generate g_sclr;

  g_no_sclr : if fn_is_cntrl_reset(C_HAS_ARESETn)/=1 generate
    sclr_int<= '0';

    g_sclr_end : if C_S_DATA_HAS_FIFO = 0 generate
    begin
      process(CLK)
      begin
        if (rising_edge(CLK)) then
          sclr_end  <= '0';
        end if;
      end process;
    end generate g_sclr_end;
  end generate g_no_sclr;

  g_ipbuff : if C_S_DATA_HAS_FIFO = 1 generate
    i_ipbuff : process(clk)
      variable v_write,
               v_read,
               v_writing_2nd_page: boolean := false;
      variable v_ipbuff_in : std_logic_vector(s_s_tlast'HIGH downto 0);
      variable v_phase     : integer:=0;
      variable v_ipbuff_read_addr : integer := 0;
    begin
      if (rising_edge(clk)) then
        if (areset = '1' and ce_int = '1') or sclr_int = '1' then
          ipbuff           <= (others=>(others=>'0'));
          ipbuff_full      <= '0';
          ipbuff_ptr       <= -1;
          ipbuff_ready     <= false;
          ipbuff_out       <= (others=>'0');
          ipbuff_read_addr <= 0;
          ipbuff_write_addr<= 0;
          s_data_tready <= '0';

          v_phase          := 0;
          v_writing_2nd_page := false;
          ip_write         <= false;
        else
          v_write := (S_AXIS_DATA_TVALID='1') and (s_data_tready='1') and (ce_int='1');

          if C_S_DATA_HAS_TUSER/=ci_no_tuser then
            v_ipbuff_in(s_s_tuser) := S_AXIS_DATA_TUSER;
          end if;
          if C_DATA_HAS_TLAST/=ci_no_tlast then
            v_ipbuff_in(s_s_tlast) := (s_s_tlast'HIGH=>S_AXIS_DATA_TLAST);
          end if;

          ipbuff_in <= v_ipbuff_in;
          ip_write  <= v_write;

          prc_fifo( ip_write,
                    ip_px_rdy and ipbuff_ready and (ce_px='1'),
                    ipbuff_in,
                    ipbuff,
                    ipbuff_ptr,
                    ipbuff_out,
                    ipbuff_ready,
                    ipbuff_flag,
                    ipbuff_full,
                    ci_fir_properties.ipbuff_thres,
                    ipbuff'HIGH-2);
          if ce_int = '1' then
            s_data_tready <= not ipbuff_full;
          end if;

        end if;
      end if;
    end process i_ipbuff;
    S_AXIS_DATA_TREADY <= s_data_tready;
  end generate g_ipbuff;

  g_no_ipbuff: if C_S_DATA_HAS_FIFO = 0 generate
    s_data_tvalid       <= (S_AXIS_DATA_TVALID = '1');

    i_reg:process(clk)
    begin
      if (rising_edge(clk)) then
        if ce_px='1' then
          if S_AXIS_DATA_TVALID = '1' and ip_px_rdy then
            if C_S_DATA_HAS_TUSER/=ci_no_tuser then
              ipbuff_out(s_s_tuser) <= S_AXIS_DATA_TUSER;
            end if;
            if C_DATA_HAS_TLAST/=ci_no_tlast then
              ipbuff_out(s_s_tlast) <= (s_s_tlast'HIGH=>S_AXIS_DATA_TLAST);
            end if;
          end if;
        end if;
      end if;
    end process;

    S_AXIS_DATA_TREADY  <= fn_select_sl('0','1',ip_px_rdy);

  end generate g_no_ipbuff;

  data_ready <= ipbuff_ready when C_S_DATA_HAS_FIFO = 1 else
                s_data_tvalid;

  i_cnfgbuff: process(clk)
  begin
    if (rising_edge(clk)) then
      if (areset = '1' and ce_int = '1') or sclr_int = '1' then

        cnfgbuff_full <= '0';
        s_cnfg_tready  <= '0';

        cnfgbuff_ptr  <= -1;
        cnfgbuff_ready <= false;
        cnfgbuff_write <= false;
        cnfgbuff_in    <= (others=>'0');
        cnfgbuff_out   <= (others=>'0');
      else

        cnfgbuff_write <= (S_AXIS_CONFIG_TVALID='1') and (s_cnfg_tready = '1') and (ce_int='1');
        cnfgbuff_in(cnfgbuff_in'HIGH) <= S_AXIS_CONFIG_TLAST;
        if ce_int='1' then
          s_cnfg_tready  <= not cnfgbuff_full;
        end if;

        prc_fifo( cnfgbuff_write,
                  cnfgbuff_read and (ce_px='1'),
                  cnfgbuff_in,
                  cnfgbuff,
                  cnfgbuff_ptr,
                  cnfgbuff_out,
                  cnfgbuff_ready,
                  cnfgbuff_flag,
                  cnfgbuff_full,
                  fn_select_integer(1,C_NUM_CHANNELS,C_CONFIG_PACKET_SIZE=ci_chan_cnfg),
                  cnfgbuff'HIGH-2,
                  fn_select_integer(2,C_NUM_CHANNELS+1,C_CONFIG_PACKET_SIZE=ci_chan_cnfg));
      end if;
    end if;
  end process i_cnfgbuff;

  S_AXIS_CONFIG_TREADY <= s_cnfg_tready;

  i_cnfg_and_reload : process(clk)
    constant ci_fsel_lookup      : t_int_array(C_NUM_FILTS-1 downto 0) := fn_gen_cnt(C_NUM_FILTS,1,0);
    constant ci_fsel_pad         : t_int_array(2**log2roundup(C_NUM_FILTS)-1 downto C_NUM_FILTS) := (others=>0);
    constant ci_fsel_full        : t_int_array(2**log2roundup(C_NUM_FILTS)-1 downto 0) := fn_gen_cnt(2**log2roundup(C_NUM_FILTS),1,0);
    variable fsel_lookup         : t_int_array(2**log2roundup(C_NUM_FILTS)-1 downto 0) := fn_select_int_array(
                                                                                            fn_select_int_array(
                                                                                              ci_fsel_lookup,
                                                                                              ci_fsel_pad&ci_fsel_lookup,
                                                                                              2**log2roundup(C_NUM_FILTS) > C_NUM_FILTS),
                                                                                            ci_fsel_full,
                                                                                            C_NUM_FILTS > 1 and C_FILTS_PACKED = 0 and C_COEF_RELOAD=0);
    variable fsel_current       : t_int_array(C_NUM_CHANNELS-1 downto 0) := (others=>0);
    variable rld_slots          : t_int_array(C_NUM_RELOAD_SLOTS-1 downto 0) := fn_gen_cnt(C_NUM_RELOAD_SLOTS,1,C_NUM_FILTS);
    variable rld_slot           : integer := 0;
    variable rld_slot_i         : integer := -1;
    variable rld_filt           : integer := 0;
    variable freed_slots        : t_int_array(C_NUM_RELOAD_SLOTS-1 downto 0) := (others=>0);
    variable freed_cnt          : integer := -1;
    variable rld_cnt            : integer := 0;
    variable rld_coeff          : t_reload_coeffs := (others=>(others=>'0'));
    variable rld_complete,
             rld_complete_dly   : boolean := false;
    variable rld_invalid_fsel   : boolean := false;
    variable get_next_cnfg      : boolean := false;
    variable get_next_cnfg_dly  : t_bool_array(ci_fir_properties.cnfg_read_dly downto 0) := (others=>false);
    variable ip_chan_cnt_dly    : integer := 0;
    variable chan_cnt_dly       : t_int_array(ci_fir_properties.cnfg_read_dly downto 0) := (others=>0);
    variable tlast_sel          : boolean:=false;
    variable tlast_dly          : t_bool_array(ci_fir_properties.cnfg_read_dly downto 0) := (others=>false);
    variable after_reset        : boolean:=true;
    variable v_update_fsel      : boolean:=false;
    variable cnfgbuff_read_dly,
             next_cnfg_dly,
             update_cnfg_first_dly,
             next_chan_dly,
             reset_fill_dly   : boolean := false;
    variable update_chan_dly  : integer := 0;
    variable cnfgbuff_out_dly : std_logic_vector(s_chanpat'HIGH+1 downto 0);
    variable cnfgbuff_read_dly_pipe,
             next_cnfg_dly_pipe,
             update_cnfg_first_dly_pipe,
             next_chan_dly_pipe,
             reset_fill_dly_pipe  : t_bool_array(ci_cnfg_update_dly downto 0);
    variable update_chan_dly_pipe : t_int_array(ci_cnfg_update_dly downto 0);
    variable cnfgbuff_out_dly_pipe : t_buffer(ci_cnfg_update_dly-1 downto 0);

    variable reload_packet : t_coeff_pages;
    variable upper_i : integer;
    variable lower_i : integer;
  begin
    if (rising_edge(clk)) then
      if sclr_int='1' then
        cnfgbuff_read         <= false;
        cnfgbuff_read_dly     := false;
        updating_cnfg         <= false;
        packet_end            <= false;
        next_cnfg             <= false;
        update_chan           <= 0;
        update_chan_dly       := 0;
        get_reload_filt       <= (C_NUM_FILTS > 1);
        update_cnfg_first     <= false;
        update_cnfg_first_dly := false;
        if C_COEF_RELOAD = 0 then
          fsel                  <= 0;
          fsel_current          := (others => 0);
        end if;
        next_cnfg             <= false;
        next_cnfg_dly         := false;
        chanpat               <= 0;
        chanpat_updated       <= false;
        check_tlast           <= false;
        rld_cnt               := 0;
        rld_invalid_fsel      := false;
        next_chan             <= false;
        next_chan_dly         := false;
        after_reset           := true;
        reset_fill            <= false;
        get_next_cnfg_dly     := (others=>false);

        event_s_config_tlast_missing     <= '0';
        event_s_config_tlast_unexpected  <= '0';
        event_s_reload_tlast_missing     <= '0';
        event_s_reload_tlast_unexpected  <= '0';
        next_cnfg_dly_pipe               := (others=>false);

        s_axis_config_send_packet  <= '0';
        s_axis_reload_packet_valid <= '0';
      else
        if ce_px='1' then
          s_axis_config_send_packet <= '0';

          event_s_config_tlast_missing     <= '0';
          event_s_config_tlast_unexpected  <= '0';

          prc_dly(get_new_data,get_next_cnfg_dly,get_next_cnfg);

          if C_FILTER_TYPE = ci_decimation or C_FILTER_TYPE = ci_halfband_decimation then
            prc_dly(tlast_ored,tlast_dly(tlast_dly'HIGH-1 downto 0),tlast_sel);
            prc_dly(op_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          else
            tlast_sel := (ipbuff_out(s_s_tlast)="1");
            prc_dly(ip_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          end if;

          if C_NUM_CHANNELS > 1 then

            cnfgbuff_read <= false;
            if get_next_cnfg and update_cnfg and ( (ip_chan_cnt_dly=0 and cnfgbuff_ready and (C_CONFIG_SYNC_MODE=ci_cnfg_vector_sync or packet_end)) or
                                                   (updating_cnfg and C_CONFIG_PACKET_SIZE /= ci_single_cnfg) ) then
              cnfgbuff_read <= true;
            end if;

            update_cnfg_first <= false;
            if get_next_cnfg and update_cnfg and ip_chan_cnt_dly=0 and cnfgbuff_ready and (C_CONFIG_SYNC_MODE=ci_cnfg_vector_sync or packet_end) then
              updating_cnfg     <= true;
              update_cnfg_first <= true;
              packet_end        <= false;
            elsif (get_next_cnfg and update_cnfg and ip_chan_cnt_dly=C_NUM_CHANNELS-1) or C_CONFIG_PACKET_SIZE = ci_single_cnfg then
              updating_cnfg <= false;
            end if;

          else
            cnfgbuff_read     <= false;
            update_cnfg_first <= false;
            updating_cnfg     <= false;
            if cnfgbuff_read and get_next_cnfg and update_cnfg and cnfgbuff_ready and cnfgbuff_flag and (C_CONFIG_SYNC_MODE=ci_cnfg_vector_sync or packet_end)  then
              cnfgbuff_read     <= true;
              update_cnfg_first <= true;
              updating_cnfg     <= true;
              packet_end        <= false;
            elsif not cnfgbuff_read and get_next_cnfg and update_cnfg and cnfgbuff_ready and (C_CONFIG_SYNC_MODE=ci_cnfg_vector_sync or packet_end)  then
              cnfgbuff_read     <= true;
              update_cnfg_first <= true;
              updating_cnfg     <= true;
              packet_end        <= false;
            end if;

          end if;

          if C_NUM_CHANNELS=1 or C_CONFIG_PACKET_SIZE=ci_single_cnfg then
            if get_next_cnfg then
              if after_reset then
                reset_fill  <= true;
                after_reset := false;
              else
                reset_fill  <= false;
              end if;
            end if;
          else
            if get_next_cnfg and after_reset then
              reset_fill  <= true;
              after_reset := false;
            elsif next_chan and update_chan=C_NUM_CHANNELS-1 then
              reset_fill  <= false;
            end if;
          end if;

          if get_next_cnfg and tlast_sel then
            packet_end <= true;
          end if;

          next_chan             <= get_next_cnfg;
          next_cnfg             <= get_next_cnfg and update_cnfg;
          update_chan           <= ip_chan_cnt_dly;
          prc_dly(next_chan,next_chan_dly_pipe,next_chan_dly);
          prc_dly(next_cnfg,next_cnfg_dly_pipe,next_cnfg_dly);
          prc_dly(cnfgbuff_read,cnfgbuff_read_dly_pipe,cnfgbuff_read_dly);
          prc_dly(update_chan,update_chan_dly_pipe,update_chan_dly);
          prc_dly(update_cnfg_first,update_cnfg_first_dly_pipe,update_cnfg_first_dly);
          prc_dly(reset_fill,reset_fill_dly_pipe,reset_fill_dly);
          prc_dly(cnfgbuff_out,cnfgbuff_out_dly_pipe,cnfgbuff_out_dly);

          if next_cnfg_dly then
            if cnfgbuff_read_dly or reset_fill_dly then
              if C_CONFIG_PACKET_SIZE = ci_single_cnfg then
                if C_NUM_FILTS > 1 then
                  fsel_current := (others=> fsel_lookup(to_integer(unsigned(cnfgbuff_out_dly(s_fsel)))));
                else
                  fsel_current := (others=> fsel_lookup(0));
                end if;
              else
                if C_NUM_FILTS > 1 then
                  fsel_current(update_chan_dly) := fsel_lookup(to_integer(unsigned(cnfgbuff_out_dly(s_fsel))));
                else
                  fsel_current(update_chan_dly) := fsel_lookup(0);
                end if;
              end if;
              if reset_fill_dly and not cnfgbuff_read_dly then
                fsel_current(update_chan_dly) := fsel_lookup(0);
              end if;
            end if;


            if not ci_fixed_chan_pat then
              chanpat_updated <= false;
              if chanpat /= to_integer(unsigned(cnfgbuff_out_dly(s_chanpat))) and update_cnfg_first_dly then
                chanpat_updated <= true;
                chanpat         <= to_integer(unsigned(cnfgbuff_out_dly(s_chanpat)));
              end if;
            end if;
          end if;

          if next_cnfg_dly_pipe(1) then
            if cnfgbuff_read_dly_pipe(1) then
              if update_chan_dly_pipe(1) = C_NUM_CHANNELS - 1 and cnfgbuff_out_dly_pipe(0)(s_chanpat'HIGH+1) = '0' then
                event_s_config_tlast_missing     <= '1';
              end if;
              if update_chan_dly_pipe(1) /= C_NUM_CHANNELS - 1 and cnfgbuff_out_dly_pipe(0)(s_chanpat'HIGH+1) = '1' then
                event_s_config_tlast_unexpected     <= '1';
              end if;
            end if;
          end if;

          if next_chan_dly then
            fsel <= fsel_current(update_chan_dly);

            if update_cnfg_first_dly then
              s_axis_config_send_packet <= '1';
            end if;
          end if;

        end if;

        if ce_px ='1' then
          holdoff_update_fsel <= (updating_cnfg or reset_fill);
        end if;

        if ce_int ='1' then
          s_axis_reload_packet_valid <= '0';

          event_s_reload_tlast_missing     <= '0';
          event_s_reload_tlast_unexpected  <= '0';

          if S_AXIS_RELOAD_TVALID='1' and s_reload_tready='1' and get_reload_filt then
            rld_filt        := to_integer(unsigned(S_AXIS_RELOAD_TDATA(s_fsel)));
            get_reload_filt <= false;

            if rld_filt > C_NUM_FILTS -1 then
              rld_invalid_fsel := true;
            else
              rld_invalid_fsel := false;
            end if;
          end if;

          rld_complete := false;
          if S_AXIS_RELOAD_TVALID='1' and s_reload_tready='1' and rld_cnt = ci_fir_properties.reload_taps-1 then
            rld_complete := true;
          end if;

          if rld_complete and S_AXIS_RELOAD_TLAST = '0' then
            event_s_reload_tlast_missing <= '1';
          end if;
          if (not rld_complete) and S_AXIS_RELOAD_TLAST = '1' then
            event_s_reload_tlast_unexpected <= '1';
          end if;

          rld_complete := false;
          if S_AXIS_RELOAD_TVALID='1' and s_reload_tready='1' and not get_reload_filt and rld_cnt < ci_fir_properties.reload_taps then
            rld_coeff(rld_cnt) := S_AXIS_RELOAD_TDATA(ci_coef_width_physical-1 downto 0);
            rld_cnt            := rld_cnt + 1;

            if rld_cnt = ci_fir_properties.reload_taps then
              rld_cnt               := 0;
              get_reload_filt       <= (C_NUM_FILTS > 1);
              rld_complete := true;
            end if;

            if rld_invalid_fsel then
              rld_complete := false;
            end if;

          end if;
        end if;
      end if;

      if ce_int ='1' then
          if (update_cnfg_first and ce_px='1') and rld_complete then
            rld_slot_i := rld_slot_i - (freed_cnt+1) + 1;
          elsif not(update_cnfg_first and ce_px='1') and rld_complete then
            rld_slot_i := rld_slot_i +  1;
          elsif (update_cnfg_first and ce_px='1') and not rld_complete then
            rld_slot_i := rld_slot_i - (freed_cnt+1);
          end if;

          if update_cnfg_first and ce_px='1' then
            rld_slots(rld_slots'HIGH-(freed_cnt+1) downto 0) := rld_slots(rld_slots'HIGH downto freed_cnt+1);
            for i in 0 to freed_cnt loop
              rld_slots(rld_slots'HIGH-i) := freed_slots(i);
            end loop;
          end if;

          rld_slot := rld_slots(get_max(0,rld_slot_i));

          if rld_complete then

            reload_packet := do_coeff_reorder(rld_coeff);
            s_axis_reload_packet_valid <= '1';
            s_axis_reload_packet_fsel  <= rld_slot;
            s_axis_reload_packet_data  <= (others=>'0');
            for i in 0 to ci_fir_properties.full_taps-1 loop
              for p in 0 to ci_cpages-1 loop
                lower_i :=     p * ci_split_cwidth + C_RELOAD_TDATA_WIDTH*i;
                upper_i := (p+1) * ci_split_cwidth + C_RELOAD_TDATA_WIDTH*i - 1;
                upper_i := get_min(upper_i,s_axis_reload_packet_data'HIGH);
                if p = ci_cpages-1 and ci_coef_sign = ci_signed then
                  s_axis_reload_packet_data(upper_i  downto lower_i) <= std_logic_vector(to_signed(reload_packet(p)(i),upper_i-lower_i+1));
                else
                  s_axis_reload_packet_data(upper_i  downto lower_i) <= std_logic_vector(to_unsigned(reload_packet(p)(i),upper_i-lower_i+1));
                end if;
              end loop;
            end loop;

          end if;
      end if;

      if sclr_int = '1' then
        s_reload_tready <= '0';
      else
        if ce_int ='1' then
          if C_NUM_RELOAD_SLOTS > 1 then
            if (update_cnfg_first and ce_px='1') or (rld_slot_i < C_NUM_RELOAD_SLOTS-1) then
              s_reload_tready <= '1';
            elsif rld_complete and rld_slot_i = C_NUM_RELOAD_SLOTS-1 then
              s_reload_tready <= '0';
            end if;
          else
            if rld_complete then
              s_reload_tready <= '0';
            elsif (rld_slot_i < C_NUM_RELOAD_SLOTS-1) then
              s_reload_tready <= '1';
            end if;
          end if;
        end if;
      end if;

      if ce_int ='1' then
          if ci_fir_properties.coef_holdoff_update > 0 then
            if rld_complete and not rld_complete_dly then
              coef_holdoff_update <= ci_fir_properties.coef_holdoff_update;
            elsif coef_holdoff_update > 0 then
              coef_holdoff_update <= coef_holdoff_update - 1;
            end if;
            rld_complete_dly := rld_complete;
          end if;

          if rld_complete then
            rlded_filt(rlded_filt'HIGH downto 1) <= rlded_filt(rlded_filt'HIGH-1 downto 0);
            rlded_filt(0)                        <= rld_filt;
            rlded_filt_slot(rlded_filt'HIGH downto 1) <= rlded_filt_slot(rlded_filt'HIGH-1 downto 0);
            rlded_filt_slot(0)                        <= rld_slot;
          end if;

          if C_COEF_RELOAD=1 and C_NUM_FILTS>1 then
            v_update_fsel := (rlded_cnt /= -1) and not (holdoff_update_fsel or coef_holdoff_update>0);
          else
            v_update_fsel := (rlded_cnt /= -1) and not (updating_cnfg or reset_fill or coef_holdoff_update>0);
          end if;
          update_fsel <= v_update_fsel;
          update_fsel_dly <= update_fsel;
          updated_fsel      <= rlded_filt(get_max(0,rlded_cnt));
          updated_fsel_slot <= rlded_filt_slot(get_max(0,rlded_cnt));

          if v_update_fsel and rld_complete then
            null;
          elsif not(v_update_fsel) and rld_complete then
            rlded_cnt <= rlded_cnt + 1;
          elsif v_update_fsel and not rld_complete then
            rlded_cnt <= rlded_cnt - 1;
          end if;

          if update_fsel then
            freed_slot <= fsel_lookup(updated_fsel);
            fsel_lookup(updated_fsel) := updated_fsel_slot;
          end if;

          if update_fsel_dly and (update_cnfg_first and ce_px='1') then
            freed_cnt := 0;
          elsif update_fsel_dly and not (update_cnfg_first and ce_px='1') then
            freed_cnt := freed_cnt + 1;
          elsif not update_fsel_dly and (update_cnfg_first and ce_px='1') then
            freed_cnt := - 1;
          end if;

          if update_fsel_dly then
            freed_slots(freed_slots'HIGH downto 1) := freed_slots(freed_slots'HIGH-1 downto 0);
            freed_slots(0) := freed_slot;
          end if;

      end if;
    end if;
  end process i_cnfg_and_reload;

  S_AXIS_RELOAD_TREADY <= s_reload_tready;

  i_fir : process(clk)
    variable main_buffer  : t_main_buffer_array:= (others=>(others=>(
                                                                     tuser            => (others=>(others=>'0')),
                                                                     fsel             => (others=>0),
                                                                     chanpat          => (others=>0),
                                                                     chanpat_updated  => (others=>false) )));

    constant main_buffer_reset       : t_main_buffer_array := (others => (others => (
                                                                                     tuser            => (others=>(others=>'0')),
                                                                                     fsel             => (others=>0),
                                                                                     chanpat          => (others=>0),
                                                                                     chanpat_updated  => (others=>false))));

    variable main_buffer_ptrs : t_pointers := (others=>-1);
    variable chan_id          : integer := 0;
    variable coeff_set        : t_coeff_pages;
    variable current_chan_pat,
             current_op_chan_pat
             : integer := 0;
    variable ipbuff_tlast     : std_logic_vector(0 downto 0);
    variable ipbuff_tuser     : std_logic_vector(ci_s_tuser_width-1 downto 0);
    variable gen_op_dly,
             start_op_dly,
             push_buffer_dly    : t_bool_array(ci_fir_properties.cntrl_dly downto 0):=(others=>false);
    variable chan_cnt_dly,
             op_chan_cnt_dly,
             op_phase_cnt_dly : t_int_array(ci_fir_properties.cntrl_dly downto 0):=(others=>0);
    variable buff_out_dly       : t_buffer(ci_fir_properties.data_dly downto 0) := (others=>(others=>'0'));
    variable gen_op,
             start_op,
             push_buffer        : boolean := false;
    variable ip_chan_cnt_dly,
             push_chan_cnt,
             gen_op_chan_cnt,
             gen_op_chan_cnt_src,
             gen_op_phase_cnt : integer := 0;
    variable ipbuff_out_dly     : std_logic_vector(ipbuff_out'HIGH downto 0):= (others=>'0');
    variable v_ipbuff_read,
             v_ipbuff_page_read : boolean := false;
    variable v_tlast_ored       : std_logic := '0';
    constant ci_deci_max_ips_per_op    : integer := divroundup(C_DECIM_RATE,C_INTERP_RATE);
    constant ci_deci_min_ips_per_op    : integer := (C_DECIM_RATE/C_INTERP_RATE);
    variable v_ip_write,
             v_ip_write_dly,
             v_ip_read,
             v_op_start,
             v_get_new_data            : boolean := false;
    constant ci_deci_phase_cnt_init    : integer := (ci_deci_max_ips_per_op*C_INTERP_RATE) mod C_DECIM_RATE;
    variable v_new_samples_for_op      : integer := ci_deci_max_ips_per_op;
    constant ci_new_samples_for_next_op_init : integer:= fn_select_integer(
                                                      ci_deci_min_ips_per_op,
                                                      ci_deci_max_ips_per_op,
                                                      C_INTERP_RATE=1 or
                                                      (2*ci_deci_max_ips_per_op-1)*C_INTERP_RATE+1 <= 2*C_DECIM_RATE);
    variable v_new_samples_for_next_op : integer := ci_new_samples_for_next_op_init;
    variable buff_ptr_in,
             buff_ptr_out,
             buff_ptr_tlast,
             ptr_temp                  : integer := 0;
    constant ci_ip_store_depth         : integer :=(2*ci_deci_max_ips_per_op*C_NUM_CHANNELS) +  ci_fir_properties.cntrl_dly;
    variable ip_store                  : t_buffer(ci_ip_store_depth downto 0) := (others=>(others=>'0'));
    variable new_samples_for_op_dly,
             new_samples_for_next_op_dly : t_int_array(ci_fir_properties.cntrl_dly downto 0):=(others=>0);
    variable new_samples_for_op_out,
             new_samples_for_next_op_out : integer := 0;
    constant ci_inter_max_ops_per_ip : integer := divroundup(C_INTERP_RATE,C_DECIM_RATE);
    constant ci_inter_min_ops_per_ip : integer := (C_INTERP_RATE/C_DECIM_RATE);
    variable v_op_samples_for_ip      : integer := ci_inter_max_ops_per_ip;
    constant ci_op_samples_for_next_ip_init : integer := fn_select_integer(
                                                      ci_inter_min_ops_per_ip,
                                                      ci_inter_max_ops_per_ip,
                                                      C_DECIM_RATE=1 or
                                                      (2*ci_inter_max_ops_per_ip-1)*C_DECIM_RATE+1 <= 2*C_INTERP_RATE);
    variable v_op_samples_for_next_ip : integer := ci_op_samples_for_next_ip_init;
    constant ci_inter_phase_cnt_init    : integer := (ci_inter_max_ops_per_ip*C_DECIM_RATE) mod C_INTERP_RATE;
    variable v_op_samples_for_ip_out
             : integer := 0;
    variable v_op_px_cnt_max_sel,
             v_gen_op_src       : boolean;
    variable ip_px_rdy_set,
             ip_px_rdy_set_src,
             ip_px_rdy_clr: boolean;
    variable ops_for_ip_dly : t_int_array(ci_fir_properties.cntrl_dly downto 0):=(others=>0);
    variable ops_for_ip,
             v_op_phase_cnt_sel,
             v_op_samples_for_ip_sel : integer;
    variable v_op_samples_for_ip_dly : t_int_array(2*ci_inter_max_ops_per_ip downto 0) := (others=>0);
    variable chan_buff_i : t_int_array(C_NUM_CHANNELS-1 downto 0) := (others=>0);

    variable v_phase_cnt               : integer := fn_select_integer(
                                                      ci_deci_phase_cnt_init,
                                                      ci_inter_phase_cnt_init,
                                                      C_FILTER_TYPE = ci_polyphase_interpolation or
                                                      C_FILTER_TYPE = ci_transpose_interpolation );

    variable ip_px_rdy_dly            : t_bool_array(fn_select_integer(
                                                      2*C_OUTPUT_RATE-C_OVERSAMPLING_RATE,
                                                        fn_select_integer(
                                                          (C_OUTPUT_RATE-C_OVERSAMPLING_RATE)*C_INTERP_RATE,
                                                          0,
                                                          (C_NUM_CHANNELS=1 and C_DECIM_RATE=1 and (C_SYMMETRY=ci_non_symmetric or (C_INTERP_RATE=2 and ci_fir_properties.odd_symmetry=1))) or
                                                          (C_DECIM_RATE > 1 and C_S_DATA_HAS_FIFO = 1) ),
                                                      C_FILTER_TYPE = ci_polyphase_interpolation or
                                                      C_FILTER_TYPE = ci_transpose_interpolation )
                                                      downto 0);
    variable ip_px_cnt_modifier : boolean := false;
    variable reset_fsel         : boolean := false;

    variable evnt_if_chan_cnt : integer := 0;
  begin
    if (rising_edge(clk)) then
      if sclr_int='1' then
        ipbuff_read      <= false;
        ipbuff_page_read <= false;
        if C_S_DATA_HAS_FIFO = 1 then
          ip_px_rdy        <= true;
        else
          ip_px_rdy        <= false;
        end if;
        ip_px_cnt        <= 0;
        ip_chan_cnt      <= 0;
        ip_phase_cnt     <= 0;
        main_buffer_ptrs := (others=>-1);
        main_buffer      := main_buffer_reset;
        cascade_dly      <= (others=>(
                                      tuser=>(others=>'0'),
                                      tlast=>'0',
                                      chan_id=>0,
                                      valid=>false,
                                      data_valid=>(others=>'0')
                                      ));
        gen_op           := false;
        ip_px_cnt_en     <= false;
        current_chan_pat := 0;
        output_chan_pat  <= 0;
        if C_FILTER_TYPE = ci_polyphase_decimation or
           C_FILTER_TYPE = ci_transpose_decimation then
          update_cnfg      <= true;
        else
          update_cnfg      <= false;
        end if;
        push_buffer      := false;
        gen_op_dly       := (others=>false);
        push_buffer_dly  := (others=>false);
        ip_px_cnt_max    <= false;
        chan_cnt_dly     := (others=>0);
        buff_out_dly     := (others=>(others=>'0'));
        ip_chan_cnt_dly  := 0;
        ipbuff_out_dly   := (others=>'0');
        get_new_data     <= false;
        get_fifo_data    <= false;
        op_px_cnt_en     <= false;
        op_px_cnt        <= 0;
        op_chan_cnt      <= 0;
        op_px_cnt_max    <= false;
        buff_ptr_in      := 0;
        buff_ptr_out     := 0;
        buff_ptr_tlast   := 0;
        v_phase_cnt      := fn_select_integer(
                              ci_deci_phase_cnt_init,
                              ci_inter_phase_cnt_init,
                              C_FILTER_TYPE = ci_polyphase_interpolation or
                              C_FILTER_TYPE = ci_transpose_interpolation);
        v_new_samples_for_op := ci_deci_max_ips_per_op;
        v_new_samples_for_next_op := ci_new_samples_for_next_op_init;
        ptr_temp         := 0;
        new_samples_for_op <= 0;
        new_samples_for_next_op <= 0;
        v_op_samples_for_ip := ci_inter_max_ops_per_ip;
        v_op_samples_for_next_ip := ci_op_samples_for_next_ip_init;
        op_phase_cnt     <= 0;
        start_op_dly     := (others=>false);
        start_op         := false;
        ip_store         := (others=>(others=>'0'));
        v_ip_write_dly   := false;
        ip_px_rdy_dly    := (others=> false);
        v_op_samples_for_ip_dly := (others=>0);
        data_vector_cnt  <= 0;
        ip_px_cnt_modifier := false;
        chan_buff_i      := (others => 0);
        if C_FILTER_TYPE = ci_transpose_single_rate or
           C_FILTER_TYPE = ci_transpose_decimation or
           C_FILTER_TYPE = ci_transpose_interpolation then
          reset_fsel       := true;
        end if;

        event_s_data_tlast_missing    <= '0';
        event_s_data_tlast_unexpected <= '0';
        event_s_data_chanid_incorrect <= '0';
        evnt_if_chan_cnt              := 0;

        s_axis_data_send        <= '0';
        s_axis_data_send_amount <= 1;
      elsif ce_px='1' then

        s_axis_data_send        <= '0';
        s_axis_data_send_amount <= 1;

        case C_FILTER_TYPE is
        when ci_single_rate | ci_transpose_single_rate | ci_hilbert | ci_halfband | ci_interpolated =>
          update_cnfg <= true;
          get_new_data <= false;

          if ip_px_rdy and data_ready then
            get_new_data <= true;
            if ci_fir_properties.px_time > 1 then
              ip_px_rdy    <= false;
            end if;
            ip_px_cnt_en <= true;
          elsif ip_px_cnt = ci_fir_properties.px_time-1 then
            ip_px_cnt_en <= false;
          end if;

          if (ip_px_cnt = ci_fir_properties.px_time-2 and ip_px_cnt_en) or sclr_end = '1' then
            ip_px_rdy    <= true;
          end if;

          ip_px_cnt_max <= false;
          if ci_fir_properties.px_time > 1 then
            if ip_px_cnt = ci_fir_properties.px_time-2 and ip_px_cnt_en then
              ip_px_cnt_max <= true;
            end if;
          else
            ip_px_cnt_max <= ip_px_rdy and data_ready;
          end if;

          if ip_px_cnt_en then
            if ip_px_cnt = ci_fir_properties.px_time-1 then
              ip_px_cnt    <= 0;
              if ip_chan_cnt = C_NUM_CHANNELS-1 then
                ip_chan_cnt <= 0;
              else
                ip_chan_cnt <= ip_chan_cnt +1;
              end if;
            else
              ip_px_cnt <= ip_px_cnt + 1;
            end if;
          end if;

          prc_dly(get_new_data,push_buffer_dly,push_buffer);
          prc_dly(ip_px_cnt_max,gen_op_dly,gen_op);
          prc_dly(ip_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          prc_dly(ipbuff_out,buff_out_dly,ipbuff_out_dly);

          if get_new_data then
            if C_DATA_HAS_TLAST = ci_vector_tlast then
              if ipbuff_out(s_s_tlast) = "1" and ip_chan_cnt /= C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '1';
              elsif ipbuff_out(s_s_tlast) = "0" and ip_chan_cnt = C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '1';
                event_s_data_tlast_unexpected <= '0';
              else
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '0';
              end if;
            end if;
            if C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              event_s_data_chanid_incorrect <= '0';
              ipbuff_tuser := ipbuff_out(s_s_tuser);
              if ip_chan_cnt /= to_integer(unsigned(ipbuff_tuser(s_s_tuser_chanid))) then
                event_s_data_chanid_incorrect <= '1';
              end if;
            end if;
          end if;

          if push_buffer then

            if ip_chan_cnt_dly = 0 and chanpat_updated then
              current_chan_pat := chanpat;
            end if;

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

            prc_push_main_buffer( chan_id,
                                  main_buffer_ptrs,
                                  main_buffer,
                                  ipbuff_out_dly(s_s_tdata),
                                  fn_select_slv(
                                    ipbuff_tuser(s_s_tuser_user),
                                    ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                    C_DATA_HAS_TLAST = ci_pass_tlast),
                                  fsel,
                                  current_chan_pat,
                                  chanpat_updated,
                                  reset_fsel);
            s_axis_data_send <= '1';

            reset_fsel := false;
          end if;

          cascade_dly(1 to cascade_dly'HIGH) <= cascade_dly(0 to cascade_dly'HIGH-1);
          cascade_dly(0).valid                   <= false;

          if gen_op then
            if C_FILTER_TYPE = ci_transpose_single_rate then
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)+C_NUM_MADDS-1));
            else
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            end if;

            cascade_dly(0).valid   <= true;
            cascade_dly(0).chan_id <= chan_id;
            if C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_m_tuser_user_width-1 downto 0);
            end if;
            if C_DATA_HAS_TLAST = ci_pass_tlast then
              cascade_dly(0).tlast <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_tuser_dly_width-1);
            elsif C_DATA_HAS_TLAST = ci_vector_tlast and C_NUM_CHANNELS>1 then
              cascade_dly(0).tlast <= '0';
              if ip_chan_cnt_dly = C_NUM_CHANNELS-1 then
                cascade_dly(0).tlast <= '1';
              end if;
            end if;
            if C_HAS_ARESETn = ci_cntrl_only_reset then
              if ip_chan_cnt_dly = 0 or C_NUM_CHANNELS=1 then
                if C_FILTER_TYPE = ci_interpolated then
                  if data_vector_cnt = ci_fir_properties.full_taps * C_ZERO_PACKING_FACTOR - 1 then
                    cascade_dly(0).data_valid <= "1";
                  else
                    cascade_dly(0).data_valid <= "0";
                    data_vector_cnt <= data_vector_cnt + 1;
                  end if;
                else
                  if data_vector_cnt = ci_fir_properties.full_taps - 1 then
                    cascade_dly(0).data_valid <= "1";
                  else
                    cascade_dly(0).data_valid <= "0";
                    data_vector_cnt <= data_vector_cnt + 1;
                  end if;
                end if;
              end if;
            end if;

            if not ci_fixed_chan_pat and chan_id=0 and main_buffer(0,chan_id).chanpat_updated(main_buffer_ptrs(chan_id)) then
              prc_flush_main_buffer(main_buffer_ptrs,main_buffer);
            end if;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - 1;
          end if;
        when ci_halfband_decimation =>
          update_cnfg <= true;
          get_new_data <= false;
          get_fifo_data <= false;

          if ip_px_rdy and data_ready then
            get_fifo_data <= true;
            if C_INPUT_RATE > 1 then
              ip_px_rdy    <= false;
            end if;
            ip_px_cnt_en <= true;
          elsif ip_px_cnt = C_INPUT_RATE-1 then
            ip_px_cnt_en <= false;
          end if;

          if (ip_px_cnt = C_INPUT_RATE-2 and ip_px_cnt_en) or sclr_end = '1' then
            ip_px_rdy    <= true;
          end if;

          ip_px_cnt_max <= false;
          if C_INPUT_RATE > 1 then
            if ip_px_cnt = C_INPUT_RATE-2 and ip_px_cnt_en then
              ip_px_cnt_max <= true;
            end if;
          else
            ip_px_cnt_max <= ip_px_rdy and data_ready;
          end if;

          if ip_px_cnt_en then
            if ip_px_cnt = C_INPUT_RATE-1 then
              ip_px_cnt    <= 0;
              if ip_chan_cnt = C_NUM_CHANNELS-1 then
                ip_chan_cnt <= 0;
                if ip_phase_cnt = 1 then
                  ip_phase_cnt <= 0;
                else
                  ip_phase_cnt <= ip_phase_cnt + 1;
                end if;
              else
                ip_chan_cnt <= ip_chan_cnt +1;
              end if;
            else
              ip_px_cnt <= ip_px_cnt + 1;
            end if;
          end if;

          if get_fifo_data and
             ip_chan_cnt = C_NUM_CHANNELS-1 and
             ip_phase_cnt = 1 then
            op_px_cnt_en <= true;
          elsif op_px_cnt = ci_fir_properties.px_time-1 and
                op_chan_cnt = C_NUM_CHANNELS-1 then
            op_px_cnt_en <= false;
          end if;

          get_new_data   <= false;
          if (get_fifo_data and
              ip_chan_cnt = C_NUM_CHANNELS-1 and
              ip_phase_cnt = 1) or
             (op_px_cnt_en and op_px_cnt = ci_fir_properties.px_time-1 and op_chan_cnt < C_NUM_CHANNELS - 1)
             then
            get_new_data   <= true;
          end if;

          v_get_new_data := false;
          if (get_fifo_data and
              ip_chan_cnt = C_NUM_CHANNELS-1 and
              ip_phase_cnt = 1)
             then
            v_get_new_data := true;
          end if;

          if op_px_cnt_en then
            if op_px_cnt = ci_fir_properties.px_time-1 then
              op_px_cnt <= 0;
              if op_chan_cnt = C_NUM_CHANNELS-1 then
                op_chan_cnt <= 0;
              else
                op_chan_cnt <= op_chan_cnt + 1;
              end if;
            else
              op_px_cnt <= op_px_cnt + 1;
            end if;
          end if;

          if v_ip_write_dly then
            ip_store(buff_ptr_in)(ipbuff_out'RANGE) := ipbuff_out;
            buff_ptr_in := (buff_ptr_in + 1) mod ci_ip_store_depth;
          end if;
          v_ip_write_dly := ip_px_rdy and data_ready;

          tlast_ored <= false;
          if v_get_new_data then
            for i in 0 to 2*C_NUM_CHANNELS-1 loop
              if ip_store((buff_ptr_tlast+i) mod ci_ip_store_depth)(s_s_tlast)="1" then
                tlast_ored <= true;
              end if;
            end loop;
            buff_ptr_tlast := (buff_ptr_tlast + v_new_samples_for_op*C_NUM_CHANNELS) mod ci_ip_store_depth;
          end if;

          if (get_fifo_data and C_S_DATA_HAS_FIFO = 1) or (S_AXIS_DATA_TVALID = '1' and ip_px_rdy and C_S_DATA_HAS_FIFO=0) then
            if C_S_DATA_HAS_FIFO = 1 then
              ipbuff_tuser := ipbuff_out(s_s_tuser);
              if C_DATA_HAS_TLAST = ci_vector_tlast then
                ipbuff_tlast := ipbuff_out(s_s_tlast);
              end if;
            else
              if ci_s_tuser_width > 0 then
                ipbuff_tuser := S_AXIS_DATA_TUSER;
              end if;
              ipbuff_tlast(0) := S_AXIS_DATA_TLAST;
            end if;

            if C_DATA_HAS_TLAST = ci_vector_tlast then
              if  ipbuff_tlast = "1" and evnt_if_chan_cnt /= C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '1';
              elsif ipbuff_tlast = "0" and evnt_if_chan_cnt = C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '1';
                event_s_data_tlast_unexpected <= '0';
              else
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '0';
              end if;
            end if;
            if C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              event_s_data_chanid_incorrect <= '0';
              if evnt_if_chan_cnt /= to_integer(unsigned(ipbuff_tuser(s_s_tuser_chanid))) then
                event_s_data_chanid_incorrect <= '1';
              end if;
            end if;
            if evnt_if_chan_cnt = C_NUM_CHANNELS - 1 then
              evnt_if_chan_cnt := 0;
            else
              evnt_if_chan_cnt := evnt_if_chan_cnt + 1;
            end if;
          end if;

          prc_dly(get_new_data,push_buffer_dly,push_buffer);
          prc_dly(op_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          prc_dly(op_px_cnt = ci_fir_properties.px_time-1,gen_op_dly,gen_op);

          if push_buffer then

            if ip_chan_cnt_dly = 0 then
              s_axis_data_send        <= '1';
              s_axis_data_send_amount <= 2 * C_NUM_CHANNELS;
            end if;

            if ip_chan_cnt_dly = 0 and chanpat_updated then
              current_chan_pat := chanpat;
              chan_buff_i := (others=>0);
            end if;

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            if ci_fixed_chan_pat then
              ipbuff_out_dly := ip_store(buff_ptr_out)(ipbuff_out'RANGE);
            else
              loop
                if chan_id = ci_chan_pat(current_chan_pat*ci_pat_len + (chan_buff_i(chan_id) mod C_NUM_CHANNELS)) then
                  ipbuff_out_dly := ip_store((buff_ptr_out+chan_buff_i(chan_id)) mod ci_ip_store_depth)(ipbuff_out'RANGE);
                  chan_buff_i(chan_id) := chan_buff_i(chan_id) + 1;
                  exit;
                else
                  chan_buff_i(chan_id) := chan_buff_i(chan_id) + 1;
                end if;
              end loop;
            end if;

            ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

            prc_push_main_buffer( chan_id,
                                  main_buffer_ptrs,
                                  main_buffer,
                                  ipbuff_out_dly(s_s_tdata),
                                  fn_select_slv(
                                    ipbuff_tuser(s_s_tuser_user),
                                    ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                    C_DATA_HAS_TLAST = ci_pass_tlast),
                                  fsel,
                                  current_chan_pat,
                                  chanpat_updated);

            if ci_fixed_chan_pat then
              ipbuff_out_dly := ip_store((buff_ptr_out+C_NUM_CHANNELS) mod ci_ip_store_depth)(ipbuff_out'RANGE);
            else
              loop
                if chan_id = ci_chan_pat(current_chan_pat*ci_pat_len + (chan_buff_i(chan_id) mod C_NUM_CHANNELS)) then
                  ipbuff_out_dly := ip_store((buff_ptr_out+chan_buff_i(chan_id)) mod ci_ip_store_depth)(ipbuff_out'RANGE);
                  chan_buff_i(chan_id) := chan_buff_i(chan_id) + 1;
                  exit;
                else
                  chan_buff_i(chan_id) := chan_buff_i(chan_id) + 1;
                end if;
              end loop;
            end if;

            ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

            prc_push_main_buffer( chan_id,
                                  main_buffer_ptrs,
                                  main_buffer,
                                  ipbuff_out_dly(s_s_tdata),
                                  fn_select_slv(
                                    ipbuff_tuser(s_s_tuser_user),
                                    ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                    C_DATA_HAS_TLAST = ci_pass_tlast),
                                  fsel,
                                  current_chan_pat,
                                  chanpat_updated);

            if ci_fixed_chan_pat then
              if ip_chan_cnt_dly = C_NUM_CHANNELS-1 then
                buff_ptr_out := (buff_ptr_out + 1 + C_NUM_CHANNELS) mod ci_ip_store_depth;
              else
                buff_ptr_out := (buff_ptr_out + 1) mod ci_ip_store_depth;
              end if;
            else
              if ip_chan_cnt_dly = C_NUM_CHANNELS-1 then
                chan_buff_i := (others=>0);
                buff_ptr_out := (buff_ptr_out + 2 * C_NUM_CHANNELS) mod ci_ip_store_depth;
              end if;
            end if;

          end if;

          cascade_dly(1 to cascade_dly'HIGH) <= cascade_dly(0 to cascade_dly'HIGH-1);
          cascade_dly(0).valid                   <= false;

          if gen_op then

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));

            cascade_dly(0).valid   <= true;
            cascade_dly(0).chan_id <= chan_id;
            if C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_m_tuser_user_width-1 downto 0);
            end if;
            if C_DATA_HAS_TLAST = ci_pass_tlast then
              cascade_dly(0).tlast <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_tuser_dly_width-1);
              cascade_dly(0).tlast <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_tuser_dly_width-1) or
                                      main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id)-1)(ci_tuser_dly_width-1);
            elsif C_DATA_HAS_TLAST = ci_vector_tlast and C_NUM_CHANNELS>1 then
              cascade_dly(0).tlast <= '0';
              if ip_chan_cnt_dly = C_NUM_CHANNELS-1 then
                cascade_dly(0).tlast <= '1';
              end if;
            end if;
            if C_HAS_ARESETn = ci_cntrl_only_reset then
              if ip_chan_cnt_dly = 0 or C_NUM_CHANNELS=1 then
                if data_vector_cnt = divroundup(ci_fir_properties.full_taps,2) - 1 then
                  cascade_dly(0).data_valid <= "1";
                else
                  cascade_dly(0).data_valid <= "0";
                  data_vector_cnt <= data_vector_cnt + 1;
                end if;
              end if;
            end if;

            if not ci_fixed_chan_pat and chan_id=0 and main_buffer(0,chan_id).chanpat_updated(main_buffer_ptrs(chan_id)) then
              prc_flush_main_buffer(main_buffer_ptrs,main_buffer);
            end if;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - 1;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - 1;
          end if;

        when ci_halfband_interpolation =>

          update_cnfg <= true;
          get_new_data <= false;

          ip_px_rdy_set_src := false;
          ip_px_rdy_clr     := false;

          if ip_px_rdy and data_ready then
            get_new_data <= true;
            if ci_fir_properties.px_time > 1 then
              ip_px_rdy_clr := true;
            end if;
            ip_px_cnt_en <= true;
          elsif ip_px_cnt = ci_fir_properties.px_time-1 then
            ip_px_cnt_en <= false;
          end if;

          if ip_px_cnt = ci_fir_properties.px_time-2 and ip_px_cnt_en then
            ip_px_rdy_set_src := true;
          end if;

          ip_px_cnt_max <= false;
          if ci_fir_properties.px_time > 1 then
            if ip_px_cnt = ci_fir_properties.px_time-2 and ip_px_cnt_en then
              ip_px_cnt_max <= true;
            end if;
          else
            ip_px_cnt_max <= ip_px_rdy and data_ready;
          end if;

          if ci_coeff_snap_shot then
            coeff_snap_shot(1 to coeff_snap_shot'HIGH) <= coeff_snap_shot(0 to coeff_snap_shot'HIGH-1);
          end if;

          if ip_px_cnt_en then
            if ip_px_cnt = ci_fir_properties.px_time-1 then
              ip_px_cnt    <= 0;
              if ip_chan_cnt = C_NUM_CHANNELS-1 then
                ip_chan_cnt <= 0;
                coeff_snap_shot(0) <= coeff_sets;
              else
                ip_chan_cnt <= ip_chan_cnt +1;
              end if;
            else
              ip_px_cnt <= ip_px_cnt + 1;
            end if;
          end if;

          prc_dly(ip_px_rdy_set_src,ip_px_rdy_dly,ip_px_rdy_set);

          if ip_px_rdy_set or sclr_end = '1' then
            ip_px_rdy    <= true;
          elsif ip_px_rdy_clr then
            ip_px_rdy    <= false;
          end if;

          prc_dly(get_new_data,push_buffer_dly,push_buffer);
          prc_dly(ip_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          prc_dly(ipbuff_out,buff_out_dly,ipbuff_out_dly);
          prc_dly(ip_px_cnt = ci_fir_properties.px_time-1 and ip_chan_cnt = C_NUM_CHANNELS - 1,
                  start_op_dly,start_op);

          if get_new_data then
            if C_DATA_HAS_TLAST = ci_vector_tlast then
              if ipbuff_out(s_s_tlast) = "1" and ip_chan_cnt /= C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '1';
              elsif ipbuff_out(s_s_tlast) = "0" and ip_chan_cnt = C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '1';
                event_s_data_tlast_unexpected <= '0';
              else
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '0';
              end if;
            end if;
            if C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              event_s_data_chanid_incorrect <= '0';
              ipbuff_tuser := ipbuff_out(s_s_tuser);
              if ip_chan_cnt /= to_integer(unsigned(ipbuff_tuser(s_s_tuser_chanid))) then
                event_s_data_chanid_incorrect <= '1';
              end if;
            end if;
          end if;

          if push_buffer then

            s_axis_data_send <= '1';

            if ip_chan_cnt_dly = 0 and chanpat_updated then
              current_chan_pat := chanpat;
            end if;

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

            prc_push_main_buffer( chan_id,
                                  main_buffer_ptrs,
                                  main_buffer,
                                  ipbuff_out_dly(s_s_tdata),
                                  std_logic_vector(to_unsigned(0,ci_tuser_dly_width)),
                                  fsel,
                                  current_chan_pat,
                                  chanpat_updated);

            prc_push_main_buffer( chan_id,
                                  main_buffer_ptrs,
                                  main_buffer,
                                  std_logic_vector(to_unsigned(0,ci_data_width_concat)),
                                  fn_select_slv(
                                    ipbuff_tuser(s_s_tuser_user),
                                    ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                    C_DATA_HAS_TLAST = ci_pass_tlast),
                                  fsel,
                                  current_chan_pat,
                                  false);
          end if;

          if start_op then
            output_chan_pat <= current_chan_pat;
            op_px_cnt_en <= true;
          elsif op_px_cnt_max and op_chan_cnt = 2 * C_NUM_CHANNELS - 1 then
            op_px_cnt_en <= false;
          end if;

          op_px_cnt_max <= false;
          if ci_fir_properties.op_px_time > 1 then
            if op_px_cnt = ci_fir_properties.op_px_time-2 and op_px_cnt_en then
              op_px_cnt_max <= true;
            end if;
          else
            op_px_cnt_max <= true;
          end if;

          if op_px_cnt_en then
            if op_px_cnt = ci_fir_properties.op_px_time-1 then
              op_px_cnt    <= 0;
              if op_chan_cnt = 2 * C_NUM_CHANNELS-1 then
                op_chan_cnt <= 0;
              else
                op_chan_cnt <= op_chan_cnt +1;
              end if;
            else
              op_px_cnt <= op_px_cnt + 1;
            end if;
          end if;

          if ci_fixed_chan_pat then
            chan_id := op_chan_cnt mod C_NUM_CHANNELS;
          else
            chan_id := ci_chan_pat(output_chan_pat*ci_pat_len + (op_chan_cnt mod C_NUM_CHANNELS));
          end if;

          if ci_fir_properties.op_px_time = 1 then
            gen_op := op_px_cnt_en;
          else
            gen_op := op_px_cnt_max;
          end if;

          cascade_dly(1 to cascade_dly'HIGH) <= cascade_dly(0 to cascade_dly'HIGH-1);
          cascade_dly(0).valid               <= false;

          if gen_op then

            if ci_coeff_snap_shot then
              coeff_set := coeff_snap_shot(coeff_snap_shot'HIGH)(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            else
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            end if;

            cascade_dly(0).valid   <= true;
            cascade_dly(0).chan_id <= chan_id;
            if C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_m_tuser_user_width-1 downto 0);
            end if;
            if C_DATA_HAS_TLAST = ci_pass_tlast then
              cascade_dly(0).tlast <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_tuser_dly_width-1);
            elsif C_DATA_HAS_TLAST = ci_vector_tlast then
              cascade_dly(0).tlast <= '0';
              if (op_chan_cnt mod C_NUM_CHANNELS) = C_NUM_CHANNELS-1 then
                cascade_dly(0).tlast <= '1';
              end if;
            end if;
            if C_HAS_ARESETn = ci_cntrl_only_reset then
              if (op_chan_cnt mod C_NUM_CHANNELS) = 0 or C_NUM_CHANNELS=1 then
                if data_vector_cnt = ci_fir_properties.full_taps - 1 then
                  cascade_dly(0).data_valid <= "1";
                else
                  cascade_dly(0).data_valid <= "0";
                  data_vector_cnt <= data_vector_cnt + 1;
                end if;
              end if;
            end if;

            if not ci_fixed_chan_pat and (op_chan_cnt mod C_NUM_CHANNELS)=0 and main_buffer(0,chan_id).chanpat_updated(main_buffer_ptrs(chan_id)) then
              prc_flush_main_buffer(main_buffer_ptrs,main_buffer);
            end if;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - 1;
          end if;

        when ci_polyphase_decimation | ci_transpose_decimation =>
          get_new_data <= false;

          if ip_px_rdy and data_ready then
            get_new_data <= true;
            if ci_fir_properties.px_time > 1 then
              ip_px_rdy    <= false;
            end if;
            ip_px_cnt_en <= true;
          elsif ip_px_cnt = ci_fir_properties.px_time-1 then
            ip_px_cnt_en <= false;
          end if;

          if (ip_px_cnt = ci_fir_properties.px_time-2 and ip_px_cnt_en) or sclr_end = '1' then
            ip_px_rdy    <= true;
          end if;

          ip_px_cnt_max <= false;
          if ci_fir_properties.px_time > 1 then
            if ip_px_cnt = ci_fir_properties.px_time-2 and ip_px_cnt_en then
              ip_px_cnt_max <= true;
            end if;
          else
            ip_px_cnt_max <= ip_px_rdy and data_ready;
          end if;

          if ip_px_cnt_max and ip_chan_cnt = C_NUM_CHANNELS-1 then
            update_cnfg <= false;
            if ip_phase_cnt = C_DECIM_RATE - 1 then
              update_cnfg <= true;
            end if;
          end if;

          if ip_px_cnt_en then
            if ip_px_cnt = ci_fir_properties.px_time-1 then
              ip_px_cnt    <= 0;
              if ip_chan_cnt = C_NUM_CHANNELS-1 then
                ip_chan_cnt <= 0;
                if ip_phase_cnt = C_DECIM_RATE - 1 then
                  ip_phase_cnt <= 0;
                else
                  ip_phase_cnt <= ip_phase_cnt + 1;
                end if;
              else
                ip_chan_cnt <= ip_chan_cnt +1;
              end if;
            else
              ip_px_cnt <= ip_px_cnt + 1;
            end if;
          end if;

          if ci_coeff_snap_shot then
            coeff_snap_shot(1 to coeff_snap_shot'HIGH) <= coeff_snap_shot(0 to coeff_snap_shot'HIGH-1);
          end if;

          if C_M_DATA_HAS_TREADY = 0 and C_NUM_CHANNELS > 1 then

            ip_px_cnt_max_actual <= false;
            if C_OVERSAMPLING_RATE > 1 then
              if ip_px_cnt = C_OVERSAMPLING_RATE-2 and ip_px_cnt_en then
                ip_px_cnt_max_actual <= true;
              end if;
            else
              ip_px_cnt_max_actual <= ip_px_rdy and data_ready;
            end if;


            if ((C_OVERSAMPLING_RATE < ci_fir_properties.px_time and ip_px_cnt_max_actual) or
                (C_OVERSAMPLING_RATE = ci_fir_properties.px_time and ip_px_cnt_max)) and
               ip_phase_cnt = C_DECIM_RATE-1 then
              ptr_temp := ptr_temp + 1;
            end if;

            if (not op_px_cnt_en and ptr_temp > 0) or
               (op_px_cnt = C_OUTPUT_RATE-1 and ptr_temp > 0 and op_chan_cnt=C_NUM_CHANNELS-1) then
              coeff_snap_shot(0) <= coeff_sets;
            end if;

            if (not op_px_cnt_en and ptr_temp > 0) or
               (op_px_cnt = C_OUTPUT_RATE-1 and ptr_temp > 0) then
              op_px_cnt_en <= true;
              ptr_temp := ptr_temp - 1;
            elsif op_px_cnt = C_OUTPUT_RATE-1 then
              op_px_cnt_en <= false;
            end if;

            if op_px_cnt_en then
              if op_px_cnt = C_OUTPUT_RATE-1 then
                op_px_cnt <= 0;
                if op_chan_cnt = C_NUM_CHANNELS-1 then
                  op_chan_cnt <= 0;
                else
                  op_chan_cnt <= op_chan_cnt + 1;
                end if;
              else
                op_px_cnt <= op_px_cnt + 1;
              end if;
            end if;

          end if;

          prc_dly(get_new_data,push_buffer_dly,push_buffer);
          prc_dly(ip_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          prc_dly(ipbuff_out,buff_out_dly,ipbuff_out_dly);

          prc_dly(op_chan_cnt,op_chan_cnt_dly,gen_op_chan_cnt);

          if C_M_DATA_HAS_TREADY = 0 and C_NUM_CHANNELS > 1 then
            prc_dly(op_px_cnt=0 and op_px_cnt_en,gen_op_dly,gen_op);
          else
            prc_dly(ip_px_cnt_max and ip_phase_cnt = C_DECIM_RATE-1,gen_op_dly,gen_op);
          end if;

          if get_new_data then
            if C_DATA_HAS_TLAST = ci_vector_tlast then
              if ipbuff_out(s_s_tlast) = "1" and ip_chan_cnt /= C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '1';
              elsif ipbuff_out(s_s_tlast) = "0" and ip_chan_cnt = C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '1';
                event_s_data_tlast_unexpected <= '0';
              else
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '0';
              end if;
            end if;
            if C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              event_s_data_chanid_incorrect <= '0';
              ipbuff_tuser := ipbuff_out(s_s_tuser);
              if ip_chan_cnt /= to_integer(unsigned(ipbuff_tuser(s_s_tuser_chanid))) then
                event_s_data_chanid_incorrect <= '1';
              end if;
            end if;
          end if;

          if push_buffer then

            s_axis_data_send <= '1';

            if ip_chan_cnt_dly = 0 and chanpat_updated then
              current_chan_pat := chanpat;
            end if;

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

            prc_push_main_buffer( chan_id,
                                  main_buffer_ptrs,
                                  main_buffer,
                                  ipbuff_out_dly(s_s_tdata),
                                  fn_select_slv(
                                    ipbuff_tuser(s_s_tuser_user),
                                    ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                    C_DATA_HAS_TLAST = ci_pass_tlast),
                                  fsel,
                                  current_chan_pat,
                                  chanpat_updated,
                                  reset_fsel);

            reset_fsel := false;
          end if;

          cascade_dly(1 to cascade_dly'HIGH) <= cascade_dly(0 to cascade_dly'HIGH-1);
          cascade_dly(0).valid                   <= false;

          if gen_op then
            if C_M_DATA_HAS_TREADY = 0 and C_NUM_CHANNELS > 1 then
              chan_id := gen_op_chan_cnt;
            else
              chan_id := ip_chan_cnt_dly;
            end if;

            if C_FILTER_TYPE = ci_transpose_decimation then
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)+((C_NUM_MADDS-1)*C_DECIM_RATE)-1));
            elsif ci_coeff_snap_shot then
              coeff_set := coeff_snap_shot(coeff_snap_shot'HIGH)(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            else
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            end if;


            cascade_dly(0).valid   <= true;
            cascade_dly(0).chan_id <= chan_id;
            if C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_m_tuser_user_width-1 downto 0);
            end if;
            if C_DATA_HAS_TLAST = ci_pass_tlast then
              v_tlast_ored := '0';
              for phase in 0 to C_DECIM_RATE-1 loop
                v_tlast_ored := v_tlast_ored or main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id)-phase)(ci_tuser_dly_width-1);
              end loop;
              cascade_dly(0).tlast <= v_tlast_ored;

            elsif C_DATA_HAS_TLAST = ci_vector_tlast then
              cascade_dly(0).tlast <= '0';
              if chan_id = C_NUM_CHANNELS-1 then
                cascade_dly(0).tlast <= '1';
              end if;
            end if;
            if C_HAS_ARESETn = ci_cntrl_only_reset then
              if chan_id = 0 or C_NUM_CHANNELS=1 then
                if data_vector_cnt = ((fn_select_integer(1,2,C_SYMMETRY>0) * C_NUM_MADDS * C_OVERSAMPLING_RATE)) - 1 then
                  cascade_dly(0).data_valid <= "1";
                else
                  cascade_dly(0).data_valid <= "0";
                  data_vector_cnt <= data_vector_cnt + 1;
                end if;
              end if;
            end if;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - (C_DECIM_RATE-1);

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - 1;
          end if;

        when ci_decimation =>
          update_cnfg <= true;
          v_get_new_data := false;


          v_ip_write := ip_px_rdy and data_ready;
          v_ip_read  := op_chan_cnt=C_NUM_CHANNELS-1 and op_px_cnt=ci_fir_properties.px_time-1;

          if v_ip_write and v_ip_read then
            ip_px_cnt <= ip_px_cnt + 1 - (v_new_samples_for_op*C_NUM_CHANNELS);
            ip_px_rdy <= true;
          elsif v_ip_write and not v_ip_read then
            ip_px_cnt <= ip_px_cnt + 1;
            ip_px_rdy <= true;
            if ip_px_cnt_modifier then
              if ip_px_cnt = ((2*ci_deci_max_ips_per_op-1)*C_NUM_CHANNELS)-1 then
                ip_px_rdy <= false;
              end if;
            else
              if ip_px_cnt = (2*ci_deci_max_ips_per_op*C_NUM_CHANNELS)-1 then
                ip_px_rdy <= false;
              end if;
            end if;
          elsif not v_ip_write and v_ip_read then
            ip_px_cnt <= ip_px_cnt - (v_new_samples_for_op*C_NUM_CHANNELS);
            ip_px_rdy <= true;
          end if;

          if v_ip_read then
            if v_new_samples_for_op > v_new_samples_for_next_op then
              ip_px_cnt_modifier := true;
            elsif v_new_samples_for_op < v_new_samples_for_next_op then
              ip_px_cnt_modifier := false;
            end if;
          end if;

          if sclr_end = '1' then
            ip_px_rdy <= true;
          end if;

          v_get_new_data := false;
          if not op_px_cnt_en then
            if v_ip_write and ip_px_cnt >=(v_new_samples_for_op*C_NUM_CHANNELS)-1 then
              op_px_cnt_en <= true;
              v_get_new_data := true;
            end if;
          else
            if v_ip_read then
              if v_ip_write and ip_px_cnt >=((v_new_samples_for_op+v_new_samples_for_next_op)*C_NUM_CHANNELS)-1 then
                op_px_cnt_en <= true;
                v_get_new_data := true;
              elsif ip_px_cnt >=((v_new_samples_for_op+v_new_samples_for_next_op)*C_NUM_CHANNELS) then
                op_px_cnt_en <= true;
                v_get_new_data := true;
              else
                op_px_cnt_en <= false;
              end if;
            end if;
          end if;
          get_new_data <= v_get_new_data or (op_px_cnt = ci_fir_properties.px_time-1 and op_chan_cnt /= C_NUM_CHANNELS-1);

          if op_px_cnt_en then
            if op_px_cnt = ci_fir_properties.px_time-1 then
              op_px_cnt <= 0;
              if op_chan_cnt = C_NUM_CHANNELS-1 then
                op_chan_cnt <= 0;
              else
                op_chan_cnt <= op_chan_cnt + 1;
              end if;
            else
              op_px_cnt <= op_px_cnt + 1;
            end if;
          end if;

          if v_ip_read and C_INTERP_RATE> 1 then
            v_new_samples_for_op := v_new_samples_for_next_op;

            v_phase_cnt := (v_phase_cnt + (v_new_samples_for_next_op*C_INTERP_RATE)) mod C_DECIM_RATE;

            if v_phase_cnt+(ci_deci_min_ips_per_op*C_INTERP_RATE)>=C_DECIM_RATE then
              v_new_samples_for_next_op := ci_deci_min_ips_per_op;
            else
              v_new_samples_for_next_op := ci_deci_max_ips_per_op;
            end if;
          end if;

          if v_ip_write_dly then
            if C_DATA_HAS_TLAST = ci_vector_tlast then
              if ipbuff_out(s_s_tlast) = "1" and ip_chan_cnt /= C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '1';
              elsif ipbuff_out(s_s_tlast) = "0" and ip_chan_cnt = C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '1';
                event_s_data_tlast_unexpected <= '0';
              else
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '0';
              end if;
            end if;
            if C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              event_s_data_chanid_incorrect <= '0';
              ipbuff_tuser := ipbuff_out(s_s_tuser);
              if ip_chan_cnt /= to_integer(unsigned(ipbuff_tuser(s_s_tuser_chanid))) then
                event_s_data_chanid_incorrect <= '1';
              end if;
            end if;
            if ip_chan_cnt = C_NUM_CHANNELS-1 then
              ip_chan_cnt <= 0;
            else
              ip_chan_cnt <= ip_chan_cnt + 1;
            end if;
          end if;

          if v_ip_write_dly then
            ip_store(buff_ptr_in)(ipbuff_out'RANGE) := ipbuff_out;
            buff_ptr_in := (buff_ptr_in + 1) mod ci_ip_store_depth;
          end if;
          v_ip_write_dly := v_ip_write;

          tlast_ored <= false;
          if get_new_data and op_chan_cnt = 0 then
            for i in 0 to v_new_samples_for_op*C_NUM_CHANNELS-1 loop
              if ip_store((buff_ptr_tlast+i) mod ci_ip_store_depth)(s_s_tlast)="1" then
                tlast_ored <= true;
              end if;
            end loop;
            buff_ptr_tlast := (buff_ptr_tlast + v_new_samples_for_op*C_NUM_CHANNELS) mod ci_ip_store_depth;
          end if;

          new_samples_for_op      <= v_new_samples_for_op;
          new_samples_for_next_op <= v_new_samples_for_next_op;

          prc_dly(get_new_data and op_chan_cnt=0,push_buffer_dly,push_buffer);
          prc_dly(op_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          prc_dly(op_px_cnt = ci_fir_properties.px_time-1,gen_op_dly,gen_op);
          prc_dly(new_samples_for_op,new_samples_for_op_dly,new_samples_for_op_out);
          prc_dly(new_samples_for_next_op,new_samples_for_next_op_dly,new_samples_for_next_op_out);

          if push_buffer then

            s_axis_data_send <= '1';
            s_axis_data_send_amount <= new_samples_for_op_out*C_NUM_CHANNELS;

            for i in 0 to new_samples_for_op_out*C_NUM_CHANNELS-1 loop
              push_chan_cnt := i mod C_NUM_CHANNELS;

              ipbuff_out_dly := ip_store(buff_ptr_out)(ipbuff_out'RANGE);
              buff_ptr_out := (buff_ptr_out + 1) mod ci_ip_store_depth;

              if push_chan_cnt = 0 and chanpat_updated then
                current_chan_pat := chanpat;
              end if;

              if ci_fixed_chan_pat then
                chan_id := push_chan_cnt;
              else
                chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + push_chan_cnt);
              end if;

              ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

              prc_push_main_buffer( chan_id,
                                    main_buffer_ptrs,
                                    main_buffer,
                                    ipbuff_out_dly(s_s_tdata),
                                    fn_select_slv(
                                      ipbuff_tuser(s_s_tuser_user),
                                      ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                      C_DATA_HAS_TLAST = ci_pass_tlast),
                                    fsel,
                                    current_chan_pat,
                                    chanpat_updated and i=0);

              if C_INTERP_RATE > 1 then
                for j in 0 to C_INTERP_RATE-2 loop
                  prc_push_main_buffer( chan_id,
                                        main_buffer_ptrs,
                                        main_buffer,
                                        std_logic_vector(to_unsigned(0,ci_data_width_concat)),
                                        fn_select_slv(
                                          ipbuff_tuser(s_s_tuser_user),
                                          '0'&ipbuff_tuser(s_s_tuser_user),
                                          C_DATA_HAS_TLAST = ci_pass_tlast),
                                        fsel,
                                        current_chan_pat,
                                        false);
                end loop;
              end if;
            end loop;
          end if;

          cascade_dly(1 to cascade_dly'HIGH) <= cascade_dly(0 to cascade_dly'HIGH-1);
          cascade_dly(0).valid                   <= false;

          if gen_op then

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            cascade_dly(0).valid   <= true;
            cascade_dly(0).chan_id <= chan_id;
            ptr_temp := main_buffer_ptrs(chan_id);
            if C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser( ptr_temp )(ci_m_tuser_user_width-1 downto 0);
            end if;
            if C_INTERP_RATE > 1 then
              if (main_buffer_ptrs(chan_id) + 1) rem C_INTERP_RATE > 0 then
                ptr_temp := ((main_buffer_ptrs(chan_id)+1)/C_INTERP_RATE)*C_INTERP_RATE-1;
                cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser(ptr_temp)(ci_m_tuser_user_width-1 downto 0);
              end if;
            end if;
            if C_DATA_HAS_TLAST = ci_pass_tlast then
              v_tlast_ored := '0';
              for phase in 0 to C_DECIM_RATE-1 loop
                v_tlast_ored := v_tlast_ored or main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id)-phase)(ci_tuser_dly_width-1);
              end loop;
              cascade_dly(0).tlast <= v_tlast_ored;

            elsif C_DATA_HAS_TLAST = ci_vector_tlast then
              cascade_dly(0).tlast <= '0';
              if ip_chan_cnt_dly = C_NUM_CHANNELS-1 then
                cascade_dly(0).tlast <= '1';
              end if;
            end if;
            if C_HAS_ARESETn = ci_cntrl_only_reset then
              if ip_chan_cnt_dly = 0 or C_NUM_CHANNELS=1 then
                if data_vector_cnt = divroundup((C_NUM_MADDS * C_OVERSAMPLING_RATE),(C_DECIM_RATE/C_INTERP_RATE)) - 1 then
                  cascade_dly(0).data_valid <= "1";
                else
                  cascade_dly(0).data_valid <= "0";
                  data_vector_cnt <= data_vector_cnt + 1;
                end if;
              end if;
            end if;

            if not ci_fixed_chan_pat and chan_id=0 and main_buffer(0,chan_id).chanpat_updated(main_buffer_ptrs(chan_id)) then
              prc_flush_main_buffer(main_buffer_ptrs,main_buffer);
            end if;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - (C_DECIM_RATE-1);

            coeff_set := coeff_sets(fsel);

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - 1;
          end if;
        when ci_polyphase_interpolation | ci_transpose_interpolation =>
          update_cnfg <= true;
          get_new_data <= false;

          ip_px_rdy_set_src := false;
          ip_px_rdy_clr     := false;

          if ip_px_rdy and data_ready then
            get_new_data <= true;
            ip_px_cnt_en <= true;

            if C_DECIM_RATE=1 then
              ip_px_rdy_clr := true;
            else
              if ci_fir_properties.px_time > 1 then
                ip_px_rdy_clr := true;
              else
                if not( (v_op_samples_for_ip*ci_fir_properties.px_time = 1 and not ip_px_cnt_en) or
                        (v_op_samples_for_next_ip*ci_fir_properties.px_time = 1 and ip_px_cnt_en and ip_chan_cnt = C_NUM_CHANNELS-1) or
                        (v_op_samples_for_ip*ci_fir_properties.px_time = 1 and ip_px_cnt_en and not (ip_chan_cnt = C_NUM_CHANNELS-1)) ) then
                  ip_px_rdy_clr := true;
                end if;
              end if;
            end if;


          elsif ip_px_cnt = (v_op_samples_for_ip*ci_fir_properties.px_time)-1 then
            ip_px_cnt_en <= false;
          end if;

          if ip_px_cnt = (v_op_samples_for_ip*ci_fir_properties.px_time)-2 and ip_px_cnt_en then
            ip_px_rdy_set_src := true;
          end if;

          prc_dly(ip_px_rdy_set_src,ip_px_rdy_dly,ip_px_rdy_set);

          if ip_px_rdy_set or sclr_end='1' then
            ip_px_rdy    <= true;
          elsif ip_px_rdy_clr then
            ip_px_rdy    <= false;
          end if;

          ip_px_cnt_max <= false;
          if C_DECIM_RATE>1 and ip_px_rdy and data_ready and
                                ( (v_op_samples_for_ip*ci_fir_properties.px_time = 1 and not ip_px_cnt_en) or
                                  (v_op_samples_for_next_ip*ci_fir_properties.px_time = 1 and ip_px_cnt_en and ip_chan_cnt = C_NUM_CHANNELS-1) or
                                  (v_op_samples_for_ip*ci_fir_properties.px_time = 1 and ip_px_cnt_en and not (ip_chan_cnt = C_NUM_CHANNELS-1)) ) then
            ip_px_cnt_max <= true;
          elsif C_DECIM_RATE>1 and C_S_DATA_HAS_FIFO = 1 and C_OUTPUT_RATE > C_OVERSAMPLING_RATE then
            if ip_px_cnt / ci_fir_properties.px_time   = v_op_samples_for_ip - 1 and
               ip_px_cnt mod ci_fir_properties.px_time = C_OVERSAMPLING_RATE - 2 and
               ip_px_cnt_en then
              ip_px_cnt_max <= true;
            end if;
          else
            if ip_px_cnt = (v_op_samples_for_ip*ci_fir_properties.px_time)-2 and (v_op_samples_for_ip*ci_fir_properties.px_time) > 1  and ip_px_cnt_en then
              ip_px_cnt_max <= true;
            end if;
          end if;

          if ip_px_cnt_en then
            if ip_px_cnt = (v_op_samples_for_ip*ci_fir_properties.px_time)-1 then
              ip_px_cnt    <= 0;
              if ip_chan_cnt = C_NUM_CHANNELS-1 then
                ip_chan_cnt <= 0;
              else
                ip_chan_cnt <= ip_chan_cnt +1;
              end if;
            else
              ip_px_cnt <= ip_px_cnt + 1;
            end if;
          end if;

          if ci_coeff_snap_shot then
            coeff_snap_shot(1 to coeff_snap_shot'HIGH) <= coeff_snap_shot(0 to coeff_snap_shot'HIGH-1);
          end if;

          if C_NUM_CHANNELS=1 and (C_SYMMETRY=ci_non_symmetric or (C_INTERP_RATE=2 and ci_fir_properties.odd_symmetry=1)) then
            v_gen_op_src :=false;
            if ci_fir_properties.px_time = 1 then
              v_gen_op_src := ip_px_cnt_en;
            elsif ((ip_px_cnt+(C_OUTPUT_RATE-C_OVERSAMPLING_RATE)) mod ci_fir_properties.px_time) = ci_fir_properties.px_time - 1 and ip_px_cnt_en then
              v_gen_op_src := true;
            end if;
            gen_op_chan_cnt_src     := ip_chan_cnt;
            v_op_samples_for_ip_sel := v_op_samples_for_ip;
            v_op_phase_cnt_sel      := (ip_px_cnt / ci_fir_properties.px_time);
          else

            if C_OUTPUT_RATE = 1 then
              v_op_px_cnt_max_sel := op_px_cnt_en;
            else
              v_op_px_cnt_max_sel := op_px_cnt_max;
            end if;

            v_op_samples_for_ip_sel := v_op_samples_for_ip_out;

            if C_DECIM_RATE > 1 then

              if (ip_px_cnt_max and ip_chan_cnt = C_NUM_CHANNELS-1) then
                for i in 0 to v_op_samples_for_ip-1 loop
                  v_op_samples_for_ip_dly := v_op_samples_for_ip_dly(v_op_samples_for_ip_dly'HIGH-1 downto 0)&v_op_samples_for_ip;
                end loop;
              end if;

              if op_px_cnt_en  then
                if v_op_px_cnt_max_sel then
                  op_px_cnt <= 0;
                  if op_chan_cnt = C_NUM_CHANNELS-1 then
                    op_chan_cnt <= 0;
                    if op_phase_cnt = v_op_samples_for_ip_out -1 then
                      op_phase_cnt <= 0;
                    else
                      op_phase_cnt <= op_phase_cnt + 1;
                    end if;
                  else
                    op_chan_cnt <= op_chan_cnt + 1;
                  end if;
                else
                  op_px_cnt <= op_px_cnt + 1;
                end if;
              end if;

              if (ip_px_cnt_max and ip_chan_cnt = C_NUM_CHANNELS-1) and
                 (ptr_temp>0 and ( not op_px_cnt_en or (v_op_px_cnt_max_sel and op_chan_cnt = C_NUM_CHANNELS-1))) then
                ptr_temp := ptr_temp + v_op_samples_for_ip - 1;
              elsif (ip_px_cnt_max and ip_chan_cnt = C_NUM_CHANNELS-1) and
                    not (ptr_temp>0 and ( not op_px_cnt_en or (v_op_px_cnt_max_sel and op_chan_cnt = C_NUM_CHANNELS-1))) then
                ptr_temp := ptr_temp + v_op_samples_for_ip;
              elsif not (ip_px_cnt_max and ip_chan_cnt = C_NUM_CHANNELS-1) and
                    (ptr_temp>0 and ( not op_px_cnt_en or (v_op_px_cnt_max_sel and op_chan_cnt = C_NUM_CHANNELS-1))) then
                ptr_temp := ptr_temp - 1;
              end if;

              if (ptr_temp>0 and ( not op_px_cnt_en or (v_op_px_cnt_max_sel and op_chan_cnt = C_NUM_CHANNELS-1))) then
                op_px_cnt_en <= true;
                coeff_snap_shot(0) <= coeff_sets;
                v_op_samples_for_ip_out := v_op_samples_for_ip_dly(ptr_temp-1);
              elsif (v_op_px_cnt_max_sel and op_chan_cnt = C_NUM_CHANNELS-1) then
                op_px_cnt_en <= false;
              end if;


              op_px_cnt_max <= false;
              if op_px_cnt = C_OUTPUT_RATE-2 and op_px_cnt_en then
                op_px_cnt_max <= true;
              end if;

            else
              if ip_px_cnt_max and ip_chan_cnt = C_NUM_CHANNELS-1 then
                op_px_cnt_en <= true;
                coeff_snap_shot(0) <= coeff_sets;
                v_op_samples_for_ip_out := v_op_samples_for_ip;
              elsif v_op_px_cnt_max_sel and op_chan_cnt = C_NUM_CHANNELS-1 and op_phase_cnt = v_op_samples_for_ip_out -1 then
                op_px_cnt_en <= false;
              end if;

              if op_px_cnt_en  then
                if v_op_px_cnt_max_sel then
                  op_px_cnt <= 0;
                  if op_chan_cnt = C_NUM_CHANNELS-1 then
                    op_chan_cnt <= 0;
                    if op_phase_cnt = v_op_samples_for_ip_out -1 then
                      op_phase_cnt <= 0;
                    else
                      op_phase_cnt <= op_phase_cnt + 1;
                    end if;
                  else
                    op_chan_cnt <= op_chan_cnt + 1;
                  end if;
                else
                  op_px_cnt <= op_px_cnt + 1;
                end if;
              end if;

              op_px_cnt_max <= false;
              if op_px_cnt = C_OUTPUT_RATE-2 and op_px_cnt_en then
                op_px_cnt_max <= true;
              end if;

            end if;

            v_gen_op_src        := v_op_px_cnt_max_sel;
            gen_op_chan_cnt_src := op_chan_cnt;
            v_op_phase_cnt_sel      := op_phase_cnt;
          end if;


          if ip_px_cnt_en and  ip_px_cnt = (v_op_samples_for_ip*ci_fir_properties.px_time)-1  and ip_chan_cnt = C_NUM_CHANNELS-1 and C_DECIM_RATE>1 then
            v_op_samples_for_ip := v_op_samples_for_next_ip;

            v_phase_cnt := (v_phase_cnt + (v_op_samples_for_next_ip*C_DECIM_RATE)) mod C_INTERP_RATE;

            if v_phase_cnt+(ci_inter_min_ops_per_ip*C_DECIM_RATE)>=C_INTERP_RATE then
              v_op_samples_for_next_ip := ci_inter_min_ops_per_ip;
            else
              v_op_samples_for_next_ip := ci_inter_max_ops_per_ip;
            end if;

          end if;

          prc_dly(get_new_data,push_buffer_dly,push_buffer);
          prc_dly(v_gen_op_src,gen_op_dly,gen_op);
          prc_dly(ip_chan_cnt,chan_cnt_dly,ip_chan_cnt_dly);
          prc_dly(ipbuff_out,buff_out_dly,ipbuff_out_dly);

          prc_dly(gen_op_chan_cnt_src,op_chan_cnt_dly,gen_op_chan_cnt);
          prc_dly(v_op_samples_for_ip_sel,ops_for_ip_dly,ops_for_ip);
          prc_dly(v_op_phase_cnt_sel,op_phase_cnt_dly,gen_op_phase_cnt);

          if get_new_data then
            if C_DATA_HAS_TLAST = ci_vector_tlast then
              if ipbuff_out(s_s_tlast) = "1" and ip_chan_cnt /= C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '1';
              elsif ipbuff_out(s_s_tlast) = "0" and ip_chan_cnt = C_NUM_CHANNELS-1 then
                event_s_data_tlast_missing    <= '1';
                event_s_data_tlast_unexpected <= '0';
              else
                event_s_data_tlast_missing    <= '0';
                event_s_data_tlast_unexpected <= '0';
              end if;
            end if;
            if C_S_DATA_HAS_TUSER = ci_chanid_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              event_s_data_chanid_incorrect <= '0';
              ipbuff_tuser := ipbuff_out(s_s_tuser);
              if ip_chan_cnt /= to_integer(unsigned(ipbuff_tuser(s_s_tuser_chanid))) then
                event_s_data_chanid_incorrect <= '1';
              end if;
            end if;
          end if;

          if push_buffer then

            s_axis_data_send <= '1';

            if ip_chan_cnt_dly = 0 and chanpat_updated then
              current_chan_pat := chanpat;
            end if;

            if ip_chan_cnt_dly = C_NUM_CHANNELS-1 then
              output_chan_pat <= current_chan_pat;
            end if;

            if ci_fixed_chan_pat then
              chan_id := ip_chan_cnt_dly;
            else
              chan_id := ci_chan_pat(current_chan_pat*ci_pat_len + ip_chan_cnt_dly);
            end if;

            ipbuff_tuser := ipbuff_out_dly(s_s_tuser);

            if ci_fixed_chan_pat then

              prc_push_main_buffer( chan_id,
                                    main_buffer_ptrs,
                                    main_buffer,
                                    ipbuff_out_dly(s_s_tdata),
                                    fn_select_slv(
                                      ipbuff_tuser(s_s_tuser_user),
                                      ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                      C_DATA_HAS_TLAST = ci_pass_tlast),
                                    fsel,
                                    current_chan_pat,
                                    chanpat_updated,
                                    reset_fsel);
              reset_fsel := false;

              for i in 0 to C_INTERP_RATE-2 loop
                prc_push_main_buffer( chan_id,
                                          main_buffer_ptrs,
                                          main_buffer,
                                          std_logic_vector(to_unsigned(0,ci_data_width_concat)),
                                          fn_select_slv(
                                            ipbuff_tuser(s_s_tuser_user),
                                            ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                            C_DATA_HAS_TLAST = ci_pass_tlast),
                                          fsel,
                                          current_chan_pat,
                                          false);
              end loop;
            else
               prc_push_main_buffer( chan_id,
                                    main_buffer_ptrs,
                                    main_buffer,
                                    ipbuff_out_dly(s_s_tdata),
                                    std_logic_vector(to_unsigned(0,ci_tuser_dly_width)),
                                    fsel,
                                    current_chan_pat,
                                    chanpat_updated,
                                    reset_fsel);

              reset_fsel := false;
              for i in 0 to C_INTERP_RATE-2 loop
                prc_push_main_buffer( chan_id,
                                          main_buffer_ptrs,
                                          main_buffer,
                                          std_logic_vector(to_unsigned(0,ci_data_width_concat)),
                                          fn_select_slv(
                                            std_logic_vector(to_unsigned(0,ci_tuser_dly_width)),
                                            fn_select_slv(
                                              ipbuff_tuser(s_s_tuser_user),
                                              ipbuff_out_dly(s_s_tlast)&ipbuff_tuser(s_s_tuser_user),
                                              C_DATA_HAS_TLAST = ci_pass_tlast),
                                            i=C_INTERP_RATE-2),
                                          fsel,
                                          current_chan_pat,
                                          false);
              end loop;
            end if;
          end if;

          cascade_dly(1 to cascade_dly'HIGH) <= cascade_dly(0 to cascade_dly'HIGH-1);
          cascade_dly(0).valid                   <= false;

          if gen_op then

            if gen_op_chan_cnt = 0 and gen_op_phase_cnt = 0 then
              current_op_chan_pat := output_chan_pat;
            end if;

            if ci_fixed_chan_pat then
              chan_id := gen_op_chan_cnt;
            else
              chan_id := ci_chan_pat(current_op_chan_pat*ci_pat_len + gen_op_chan_cnt);
            end if;

            if C_FILTER_TYPE = ci_transpose_interpolation then
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)+C_NUM_MADDS-1));
            elsif ci_coeff_snap_shot then
              coeff_set := coeff_snap_shot(coeff_snap_shot'HIGH)(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            else
              coeff_set := coeff_sets(main_buffer(0,chan_id).fsel(main_buffer_ptrs(chan_id)));
            end if;

            cascade_dly(0).valid   <= true;
            cascade_dly(0).chan_id <= chan_id;
            if C_S_DATA_HAS_TUSER = ci_user_tuser or C_S_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
              if gen_op_phase_cnt = ops_for_ip-1 or not ci_fixed_chan_pat then
                cascade_dly(0).tuser <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_m_tuser_user_width-1 downto 0);
              else
                cascade_dly(0).tuser <= (others=>'0');
              end if;
            end if;
            if C_DATA_HAS_TLAST = ci_pass_tlast then
              if gen_op_phase_cnt = ops_for_ip-1 or not ci_fixed_chan_pat then
                cascade_dly(0).tlast <= main_buffer(0,chan_id).tuser(main_buffer_ptrs(chan_id))(ci_tuser_dly_width-1);
              else
                cascade_dly(0).tlast <='0';
              end if;
            elsif C_DATA_HAS_TLAST = ci_vector_tlast then
              cascade_dly(0).tlast <= '0';
              if gen_op_chan_cnt = C_NUM_CHANNELS-1 then
                cascade_dly(0).tlast <= '1';
              end if;
            end if;
            if C_HAS_ARESETn = ci_cntrl_only_reset then
              if gen_op_chan_cnt = 0 or C_NUM_CHANNELS=1 then
                if data_vector_cnt = ci_fir_properties.full_taps - 1 then
                  cascade_dly(0).data_valid <= "1";
                else
                  cascade_dly(0).data_valid <= "0";
                  data_vector_cnt <= data_vector_cnt + 1;
                end if;
              end if;
            end if;

            if not ci_fixed_chan_pat and chan_id=0 and main_buffer(0,chan_id).chanpat_updated(main_buffer_ptrs(chan_id)) then
              prc_flush_main_buffer(main_buffer_ptrs,main_buffer);
            end if;

            main_buffer_ptrs(chan_id) := main_buffer_ptrs(chan_id) - C_DECIM_RATE;
          end if;

        when others =>
          report "FIR Compiler : Invalid filter type" severity failure;
        end case;
      end if;
    end if;
  end process i_fir;

  g_opbuff : if C_M_DATA_HAS_TREADY = 1 generate
    i_opbuff : process(clk)
      variable v_opbuff_in : std_logic_vector(s_m_tlast'HIGH downto 0);
      variable v_tuser     : std_logic_vector(ci_m_tuser_width-1 downto 0) := (others=>'0');
      variable v_read      : boolean;
    begin
      if (rising_edge(clk)) then

          if C_DATA_HAS_TLAST /= ci_no_tlast then
            v_opbuff_in(s_m_tlast)    := (0=> cascade_dly(cascade_dly'HIGH).tlast);
          end if;
          if C_M_DATA_HAS_TUSER = ci_chanid_tuser or
             C_M_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
             v_tuser(s_m_tuser_chanid) := std_logic_vector(to_unsigned(cascade_dly(cascade_dly'HIGH).chan_id,ci_m_tuser_chanid_width));
          end if;
          if C_M_DATA_HAS_TUSER = ci_user_tuser or
             C_M_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser then
             v_tuser(s_m_tuser_user)   := cascade_dly(cascade_dly'HIGH).tuser;
          end if;
          if C_HAS_ARESETn = ci_cntrl_only_reset then
            v_tuser(s_m_tuser_data_valid) := cascade_dly(cascade_dly'HIGH).data_valid;
          end if;
          v_opbuff_in(s_m_tuser)    := v_tuser;

          v_read:= ((M_AXIS_DATA_TREADY='1') or m_data_tvalid='0') and (ce_int='1');

          prc_fifo( cascade_dly(cascade_dly'HIGH).valid and (ce_px='1'),
                    v_read,
                    v_opbuff_in,
                    opbuff,
                    opbuff_ptr,
                    opbuff_out,
                    opbuff_rdy,
                    opbuff_flag,
                    back_throttle,
                    ci_fir_properties.opbuff_thres );

          if v_read then
            if opbuff_rdy then
              m_data_tvalid <= '1';
            else
              m_data_tvalid <= '0';
            end if;
          end if;

          if sclr_int='1' then
            opbuff_ptr    <= -1;
            opbuff_rdy    <= false;
            m_data_tvalid <= '0';
            back_throttle <= '0';
          end if;
      end if;
    end process i_opbuff;

    M_AXIS_DATA_TVALID <= m_data_tvalid;
    g_m_tlast : if C_DATA_HAS_TLAST/=ci_no_tlast generate
      M_AXIS_DATA_TLAST       <= opbuff_out(s_m_tlast)(s_m_tlast'HIGH);
    end generate g_m_tlast;
    g_m_tuser : if C_M_DATA_HAS_TUSER/=ci_no_tuser or C_HAS_ARESETn = ci_cntrl_only_reset generate
      M_AXIS_DATA_TUSER       <= opbuff_out(s_m_tuser);
    end generate g_m_tuser;

  end generate g_opbuff;

  g_no_opbuff: if C_M_DATA_HAS_TREADY = 0 generate
    signal data        : std_logic_vector(s_m_tdata_imag'HIGH downto s_m_tdata'LOW);
  begin

    M_AXIS_DATA_TVALID <= '1' when cascade_dly(cascade_dly'HIGH).valid else '0';

    g_m_tlast : if C_DATA_HAS_TLAST/=ci_no_tlast generate
      M_AXIS_DATA_TLAST       <= cascade_dly(cascade_dly'HIGH).tlast;
    end generate g_m_tlast;

    g_tuser_chanid: if C_M_DATA_HAS_TUSER = ci_chanid_tuser or
                       C_M_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser generate
       M_AXIS_DATA_TUSER(s_m_tuser_chanid) <= std_logic_vector(to_unsigned(cascade_dly(cascade_dly'HIGH).chan_id,ci_m_tuser_chanid_width));
    end generate g_tuser_chanid;
    g_tuser_user : if C_M_DATA_HAS_TUSER = ci_user_tuser or
                   C_M_DATA_HAS_TUSER = ci_chanid_tuser + ci_user_tuser generate
       M_AXIS_DATA_TUSER(s_m_tuser_user)   <= cascade_dly(cascade_dly'HIGH).tuser;
    end generate g_tuser_user;
    g_tuser_data_valid: if C_HAS_ARESETn = ci_cntrl_only_reset generate
      M_AXIS_DATA_TUSER(s_m_tuser_data_valid) <= cascade_dly(cascade_dly'HIGH).data_valid;
    end generate g_tuser_data_valid;

  end generate g_no_opbuff;

end;
