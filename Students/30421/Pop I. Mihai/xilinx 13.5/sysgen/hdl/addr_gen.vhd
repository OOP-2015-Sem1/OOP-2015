
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
use unisim.VComponents.all;
library std;
use std.textio.all;
library xilinxcorelib;
use xilinxcorelib.prims_utils_v9_0.all;
library work;
use work.mac_fir_utils_pkg.all;
use work.mac_fir_const_pkg.all;
use work.mac_fir_comp_pkg.all;
use work.mac_fir_func_pkg.all;
entity addr_gen is
generic (
  C_FAMILY              : string  := "virtex4";
  C_ELABORATION_DIR     : string  := "";
  C_COMPONENT_NAME      : string  := "";
  d_addr_width          : integer := 14;
  d_sym_addr_width      : integer := 14;
  c_addr_width          : integer := 14;
  filter_type           : integer := 1;
  symmetrical           : integer := 1;
  num_channels          : integer := 16;
  num_filters           : integer := 1;
  num_phases            : integer := 16;
  calculation_cycles    : integer := 5;
  input_cycles          : integer := 7;
  pq_p_val              : integer :=5;
  pq_q_val              : integer :=2;
  memory_type           : integer := 1;
  coef_memory_type      : integer := 0;
  memory_packed         : integer := 1;
  coef_offset           : integer := 0;
  sym_offset            : integer := 0;
  data_width            : integer := 8;
  odd_symmetry          : integer := 0;
  no_data_mem           : integer := 0;
  last_data_earily      : integer := 0;

  ip_buffer_type        : integer := 0;
  hb_single_mac         : integer := 0;
  C_HAS_CE              : integer := 0
);
port (
  clk                     : in std_logic;
  ce                      : in std_logic;
  reset                   : in std_logic;
  new_data                : in std_logic;
  write_data              : out std_logic;
  last_data               : out std_logic;
  rfd                     : out std_logic;
  data_addr               : out std_logic_vector(d_addr_width-1 downto 0);
  data_sym_addr           : out std_logic_vector(d_sym_addr_width-1 downto 0);
  coef_addr               : out std_logic_vector(c_addr_width-1 downto 0);
  accumulate              : out std_logic;
  load_accum              : out std_logic;
  load_accum_phase0       : out std_logic;
  latch_accum             : out std_logic;
  latch_accum_pq_deci     : out std_logic;
  sym_addsub              : out std_logic;
  chan_phase_max_out      : out std_logic;
  phase_max_out           : out std_logic;
  px_start                : out std_logic;
  filter                  : in std_logic_vector(max(1,log2_ext(num_filters))-1 downto 0);
  data_in                 : in std_logic_vector(data_width-1 downto 0);
  data_out                : out std_logic_vector(data_width-1 downto 0);
  data_out_hb_bypass      : out std_logic_vector(data_width-1 downto 0);
  filt_out_hb_bypass      : out std_logic_vector(max(1,log2_ext(num_filters))-1 downto 0);
  write_data_hb_bypass    : out std_logic
);
end addr_gen;
architecture synth of addr_gen is
function gen_ROM_content( mif_file    : string;
                          coef_width  : integer;
                          num_filters : integer;
                          max_loc     : integer;
                          filtersize  : integer;
                          offset      : integer  )
                          return integer is
  variable content    : string(coef_width downto 1);
  variable loc_value  : unsigned(coef_width-1 downto 0);
  file ROMContent     : text;
  variable ROMLoc     : line;
  variable mif_status : file_open_status;
begin

  file_open(mif_status,ROMContent,mif_file,write_mode);
  for locs_loop in 1 to max_loc loop
    if (locs_loop <= num_filters) then
      loc_value := to_unsigned(((locs_loop-1)*filtersize)+offset,coef_width);
      report "*********loc_value: "&UTILS_PKG_slv_to_string(std_logic_vector(loc_value),coef_width);
      for value_loop in 1 to coef_width loop
        if ((to_unsigned(2**(value_loop-1),coef_width) and loc_value ) =
             to_unsigned(2**(value_loop-1),coef_width) ) then
          content(value_loop) := '1';
        else
          content(value_loop) := '0';
        end if;
      end loop;
    else
      content(coef_width downto 1) := (others=>'0');
    end if;

    write(ROMLoc,content);
    writeline(ROMContent,ROMLoc);

  end loop;

  file_close(ROMContent);

  return 1;
end;
constant use_dist_mem_gen : integer := 1;
constant use_blk_mem_gen  : integer := 1;
constant dram_mem_depth_thres: integer:=UTILS_PKG_select_integer(16,32,
                                                                  C_FAMILY="virtex5");
constant pq_inter : integer:=0;
constant pq_deci  : integer:=1;
constant pq_type: integer:= UTILS_PKG_select_integer(pq_inter,pq_deci,PQ_Q_VAL>PQ_P_VAL);
constant px_time: integer:= calculation_cycles-hb_single_mac;
constant num_phase: integer:=num_phases;
constant num_channel: integer:=num_channels;
constant use_data_cascade: integer:= UTILS_PKG_select_integer(0,1,
                                        px_time=1
                                        and num_channels=1
                                        and symmetrical=0
                                        and filter_type=c_single_rate
                                        and num_filters>1);
constant ip_rate: integer:=input_cycles;
constant ip_rate_width: integer:=max(1,log2_ext(ip_rate));
signal ip_rate_cnt:std_logic_vector(ip_rate_width-1 downto 0):=(others=>'0');
signal  ip_rate_cin,
        ip_rate_max,
        ip_rate_max_cout,
        rfd_int,
        rfd_int_unreg1,
        rfd_sel1
        : std_logic:='0';
constant use_ip_buff: integer:=UTILS_PKG_select_integer(0,1, (FILTER_TYPE=c_polyphase_interpolating and num_channels>1)
                                                              or FILTER_TYPE=c_decimating_half_band
                                                              or FILTER_TYPE=c_decimating
                                                              or (FILTER_TYPE=c_polyphase_pq and num_channels>1 and pq_type=pq_inter) );
constant ip_buff_cnt:integer:=UTILS_PKG_select_integer(
                                UTILS_PKG_select_integer(
                                  (2*num_channel),
                                  (4*num_channel),
                                  FILTER_TYPE=c_decimating_half_band),
                                (2*num_phases*num_channel),
                                FILTER_TYPE=c_decimating );
constant ip_buff_type:integer:= UTILS_PKG_select_integer(
                                  UTILS_PKG_select_integer(
                                    UTILS_PKG_select_integer(0,1,ip_buff_cnt>dram_mem_depth_thres),
                                    0,
                                    ip_buffer_type=1  and
                                    not(FILTER_TYPE=c_decimating_half_band and num_channels=1)),
                                  1,
                                  ip_buffer_type=2 and
                                  not(FILTER_TYPE=c_decimating_half_band and num_channels=1) );
constant use_ipbuff_sep_deci_hb: integer:=UTILS_PKG_select_integer(0,1,( px_time=2 or (px_time=4 and num_filters>1))
                                                                      and FILTER_TYPE=c_decimating_half_band);
constant use_bypass_buff: integer:=0;
constant use_delay_addr: integer:=UTILS_PKG_select_integer(0,1,(MEMORY_PACKED=0 and MEMORY_TYPE=0)
                                                              or (use_ipbuff_sep_deci_hb=0 and FILTER_TYPE=c_decimating_half_band and ip_buff_type=0 and MEMORY_TYPE=0) );
constant use_ipbuff_sep: integer:=UTILS_PKG_select_integer(0,1,((FILTER_TYPE=c_polyphase_interpolating
                                                                and ip_buff_type=1
                                                                and px_time=2)
                                                              or(FILTER_TYPE=c_polyphase_interpolating
                                                                and px_time=1 and num_channels>1)
                                                              or(FILTER_TYPE=c_polyphase_interpolating
                                                                and num_channels>1 and num_filters>1)
                                                              or(use_ipbuff_sep_deci_hb=1)
                                                              or(FILTER_TYPE=c_decimating)
                                                              )
                                                              and use_ip_buff=1 );

constant pq_inter_spec_case: integer:=UTILS_PKG_select_integer(0,1,FILTER_TYPE=c_polyphase_pq
                                                                    and pq_type=pq_inter
                                                                    and ip_buff_type=1
                                                                    and px_time=2
                                                                    and use_ip_buff=1);
constant use_ipbuff_sep_deci:integer:= UTILS_PKG_select_integer(0,1,FILTER_TYPE=c_decimating);
constant deci_delay: integer:=1+ip_buff_type;
constant ipbuff_sep_delay: integer:=UTILS_PKG_select_integer(
                                      UTILS_PKG_select_integer(
                                        UTILS_PKG_select_integer( 0,
                                                                    1,
                                                                    (num_channels>1 and px_time<=2 and num_filters=1))
                                        + UTILS_PKG_select_integer( 0,
                                                                    1,
                                                                    ip_buff_type=1)
                                        + UTILS_PKG_select_integer( 0,
                                                                    2,
                                                                    num_filters>1)    ,
                                        2 +
                                        UTILS_PKG_select_integer( 0,
                                                                  1,
                                                                  ip_buff_type=1
                                                                  ) +
                                        UTILS_PKG_select_integer( 0,
                                                                  1,
                                                                  num_filters>1 and use_ipbuff_sep_deci_hb=1) ,

                                        FILTER_TYPE=c_decimating_half_band ),
                                      deci_delay,
                                      FILTER_TYPE=c_decimating);
constant use_ip_rate: integer:=0;
constant base_rate: integer:=px_time;
constant base_width : integer:=max(1,log2_ext(base_rate));
signal base_cnt: std_logic_vector(base_width-1 downto 0):=(others=>'0');
signal  base_max_cout,
        base_max,
        base_cin,
        base_start,
        px_start_gen
        :std_logic:='0';
constant chan_phase_cnt_phase: integer:=UTILS_PKG_select_integer(
                                          UTILS_PKG_select_integer(
                                            num_phase,
                                            1,
                                            FILTER_TYPE=c_polyphase_interpolating
                                            or (FILTER_TYPE=c_polyphase_decimating
                                                and px_time=1)
                                            or FILTER_TYPE=c_decimating
                                            or FILTER_TYPE=c_interpolating_symmetry) ,
                                          pq_q_val,
                                          (FILTER_TYPE=c_polyphase_pq
                                          and pq_type=pq_deci) );
constant chan_phase_width: integer:=UTILS_PKG_select_integer(
                                      UTILS_PKG_select_integer(
                                        max(1,log2_ext(num_channel*chan_phase_cnt_phase)),
                                        max(1,log2_ext(px_time*num_channel*chan_phase_cnt_phase)),
                                        MEMORY_PACKED=1 and MEMORY_TYPE=1),
                                      max(1,log2_ext(px_time*num_channels)),
                                      FILTER_TYPE=c_decimating);
constant chan_phase_base_step:integer:=UTILS_PKG_select_integer(
                                          UTILS_PKG_select_integer(1,px_time,MEMORY_PACKED=1 and MEMORY_TYPE=1),
                                          px_time,
                                          FILTER_TYPE=c_decimating);
signal chan_phase_cnt: std_logic_vector(chan_phase_width-1 downto 0):=(others=>'0');
signal  chan_phase_max,
        chan_max,
        chan_phase_max_cout,
        chan_only_max,
        chan_only_max_cout,
        chan_phase_max_mux,
        chan_phase_max_sel
        :std_logic:='0';
signal chan_phase_first_phase:std_logic:='1';
constant data_base_width: integer:=UTILS_PKG_select_integer(max(1,log2_ext(px_time*chan_phase_cnt_phase*num_channel)),
                                                            base_width,
                                                            MEMORY_TYPE=1);
signal  data_cnt
        :std_logic_vector( data_base_width-1 downto 0):=(others=>'0');
signal  data_cnt_val2
        :std_logic_vector( data_base_width-1 downto 0):=
            std_logic_vector(
              to_unsigned(
                UTILS_PKG_select_integer(
                  0,
                  num_channels*chan_phase_cnt_phase,
                  px_time=2 and filter_type/=c_decimating),
                data_base_width));
signal  data_max,
        data_max_cout,
        data_max_mux,
        data_max_reg_ip,
        data_max_sel,
        data_max_sup
        :std_logic:='0';
constant coef_cnt_phase: integer:= UTILS_PKG_select_integer(
                                    UTILS_PKG_select_integer(
                                      UTILS_PKG_select_integer(
                                          num_phase,
                                          1,
                                          FILTER_TYPE=c_halfband_transform
                                          or FILTER_TYPE=c_interpolated_transform
                                          or FILTER_TYPE=c_hilbert_transform
                                          or FILTER_TYPE=c_decimating),
                                      pq_p_val,
                                      FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter),
                                    pq_q_val*pq_p_val,
                                    FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci);
constant coef_base_width: integer:=max(1,log2_ext((px_time+hb_single_mac)*coef_cnt_phase));
signal coef_cnt: std_logic_vector(coef_base_width-1 downto 0):=(others=>'0');
signal  base_max_dly,
        chan_phase_max_dly,
        base_cin_ext,
        coef_cnt_rst_sel
        :std_logic:='0';
constant data_sym_base_width: integer:=UTILS_PKG_select_integer(
                                            max(1,log2_ext(px_time*num_channel*chan_phase_cnt_phase)),
                                            UTILS_PKG_select_integer(
                                                                base_width,
                                                                UTILS_PKG_select_integer(base_width+1,
                                                                  max(1,log2_ext( (px_time*num_channel*num_phase)+ sym_offset)),
                                                                  MEMORY_PACKED=1),
                                                                sym_offset>0),
                                            MEMORY_TYPE=1);
constant data_sym_cnt_offset: integer:=UTILS_PKG_select_integer(0,
                                                                sym_offset,
                                                                MEMORY_PACKED=1 and MEMORY_TYPE=1);
signal  data_sym_cnt
        : std_logic_vector(data_sym_base_width-1 downto 0):=
          std_logic_vector(
              to_unsigned(
                UTILS_PKG_select_integer(
                  0,
                  UTILS_PKG_select_integer(
                    (num_channels*chan_phase_cnt_phase),
                    data_sym_cnt_offset,
                    MEMORY_TYPE=1),
                  px_time=2 and filter_type/=c_decimating),
                data_sym_base_width));
signal  data_sym_cnt_val2
        : std_logic_vector(data_sym_base_width-1 downto 0):=
          std_logic_vector(
              to_unsigned(
                UTILS_PKG_select_integer(
                  0,
                  UTILS_PKG_select_integer(
                    0,
                    data_sym_cnt_offset+1,
                    MEMORY_TYPE=1),
                  px_time=2 and filter_type/=c_decimating),
                data_sym_base_width));
signal  data_sym_max,
        data_sym_max_sel,
        data_sym_max_reg_ip,
        data_sym_max_cout,
        data_sym_step_over,
        data_sym_max_spec,
        data_sym_step_over_sel,
        data_sym_step_over_en,
        data_sym_step_over_en_reg: std_logic;
constant filt_sel_width: integer:=max(1,log2_ext(num_filters));
constant filt_addr_width: integer:=UTILS_PKG_select_integer(filt_sel_width,
                                                            max(1,log2_ext(( (px_time+hb_single_mac)*coef_cnt_phase*num_filters)+coef_offset)),
                                                            MEMORY_PACKED=1);
constant offset_mif_file          : string := C_COMPONENT_NAME
                                            & "coef_offset_rom_struct2.mif";
constant offset_mif_file_fullpath : string := C_ELABORATION_DIR
                                            & offset_mif_file;
signal  filt_src  : std_logic_vector(max(filt_sel_width,1)-1 downto 0):=(others=>'0');
signal  filt_pad  : std_logic_vector(UTILS_PKG_select_integer(3,
                                                              4,
                                                              num_filters>16) downto 0);
signal  filt_addr,
        filt_addr_latch,
        filt_addr_dly,
        filt_addr_recycle : std_logic_vector(max(filt_addr_width,1)-1 downto 0):=(others=>'0');
signal  filt_addr_latch_sel,
        write_data_inter_dly1,
        write_data_inter_dly2,
        base_sep_max_dly1,
        base_sep_max_dly2,
        base_sep_max_dly3: std_logic;
signal
        new_data_int_gen_reg : std_logic:='0';
constant coef_addr_width: integer:=UTILS_PKG_select_integer(minf(coef_offset,1)+filt_addr_width+coef_base_width,
                                                            max(1,log2_ext(coef_offset+(num_filters*(px_time+hb_single_mac)*coef_cnt_phase))),
                                                            MEMORY_PACKED=1);
signal coef_addr_int: std_logic_vector(coef_addr_width-1 downto 0):=(others=>'0');
constant data_addr_width: integer:=UTILS_PKG_select_integer(
                                          chan_phase_width+data_base_width,
                                          UTILS_PKG_select_integer(
                                                max(1,log2_ext(num_channel*num_phase*px_time)),
                                                max(1,log2_ext(num_channel*pq_q_val*px_time)),
                                                FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci),
                                          (MEMORY_PACKED=1 and MEMORY_TYPE=1) or MEMORY_TYPE=0);
signal data_addr_int: std_logic_vector(data_addr_width-1 downto 0):=(others=>'0');
constant data_sym_addr_width: integer:=UTILS_PKG_select_integer(
                                            max(1,log2_ext(num_channel*num_phase*px_time)),
                                            UTILS_PKG_select_integer(
                                                            minf(1,sym_offset)+chan_phase_width+data_base_width,
                                                            max(1,log2_ext((num_channel*num_phase*px_time)+sym_offset)),
                                                            MEMORY_PACKED=1 and MEMORY_TYPE=1),
                                            MEMORY_TYPE=1);
signal data_sym_addr_int: std_logic_vector(data_sym_addr_width-1 downto 0):=(others=>'0');
constant buffer_width: integer:=UTILS_PKG_select_integer( data_width,
                                                          data_width+filt_sel_width,
                                                          num_filters>1);
constant ip_buff_width:integer:=max(1,log2_ext(ip_buff_cnt));
signal  in_cnt,
        out_cnt,
        out_cnt_offset
        :std_logic_vector(ip_buff_width-1 downto 0):=(others=>'0');
signal  out_cnt_spec
        :std_logic_vector(ip_buff_width downto 0):=(others=>'0');
signal  in_cnt_max,
        in_cnt_page,
        in_cnt_restart,
        in_cnt_restart_mux,
        in_cnt_max_cout,
        in_cnt_max_mux,
        in_cnt_max_mux_local,
        in_cnt_max_mux_sel,
        out_cnt_max,
        out_cnt_page,
        out_cnt_restart_mux,
        out_cnt_restart,
        out_cnt_max_cout,
        out_cnt_max_mux,
        out_cnt_max_mux_sel,
        out_cnt_en,
        base_sep_max,
        base_sep_max_cout,
        base_sep_cin,
        out_phase_cnt_max,
        out_cnt_phase_switch,
        out_cnt_phase_switch_dly
        :std_logic:='0';
signal  out_phase_cnt:std_logic_vector(max(1,log2_ext(num_phases))-1 downto 0);
signal base_sep_cnt: std_logic_vector(base_width-1 downto 0):=(others=>'0');
constant addr_min  : integer := UTILS_PKG_select_integer( 4, 5,C_FAMILY="virtex5");
constant ip_buff_width_dist_mem:integer:=UTILS_PKG_select_integer( ip_buff_width,
                                                                  addr_min,
                                                                  ip_buff_cnt<=dram_mem_depth_thres and ip_buff_type=0);
signal  ip_buff_addr_in,
        ip_buff_addr_out
        : std_logic_vector(ip_buff_width_dist_mem-1 downto 0);
signal  ip_buff_data_out,
        bypass_buff_out,
        buffer_ip
        : std_logic_vector(buffer_width-1 downto 0);
signal  ip_reg
        : std_logic_vector(data_width-1 downto 0):=(others=>'0');
signal  ip_reg_filt
        : std_logic_vector(max(1,filt_sel_width)-1 downto 0);
constant bypass_chan_cnt_len: integer:=num_channel;
constant bypass_chan_cnt_width: integer:=max(1,log2_ext(bypass_chan_cnt_len));
signal  bypass_chan_cnt: std_logic_vector( bypass_chan_cnt_width-1 downto 0);
signal  bypass_chan_cnt_max_mux,
        bypass_chan_cnt_max_cout,
        bypass_chan_cnt_max_sel
        : std_logic;
signal  bypass_chan_cnt_max,
        bypass_second_phase,
        bypass_second_phase_dly,
        bypass_buff_write,
        new_data_ce: std_logic:='0';
constant phase_cnt_width: integer:= UTILS_PKG_select_integer(
                                      UTILS_PKG_select_integer(
                                        max(1,log2_ext(num_phase)),
                                        max(1,log2_ext(pq_p_val)),
                                        FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter),
                                      max(1,log2_ext(pq_q_val)),
                                      FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci);
signal  phase_cnt:std_logic_vector( phase_cnt_width-1 downto 0):=(others=>'0');
signal  phase_cnt_max,
        phase_cnt_max_cout,
        phase_cnt_max_mux,
        phase_cnt_max_sel
        : std_logic:='0';
signal  new_data_int,
        new_data_int_gen,
        new_data_int_gen_dly,
        write_data_int,
        write_data_inter,
        last_data_int,
        new_data_int_out_dly,
        first_phase
        : std_logic:='0';
signal reset_blank: std_logic:='0';
signal  new_data_int_in,
        new_data_int_out,
        reset_dly_in,
        reset_dly_out :std_logic_vector(0 downto 0);
constant write_delay:integer:=UTILS_PKG_select_integer( 0,
                                                        1,
                                                        (MEMORY_PACKED=1 and MEMORY_TYPE=1)
                                                        and not ( (FILTER_TYPE=c_polyphase_decimating
                                                                  or (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci)
                                                                  or FILTER_TYPE=c_interpolated_transform
                                                                  or (FILTER_TYPE=c_halfband_transform and MEMORY_TYPE=1)) and px_time=1) )
                             +UTILS_PKG_select_integer( 0,
                                                        deci_delay,
                                                        FILTER_TYPE=c_decimating)
                             +UTILS_PKG_select_integer( 0,
                                                        1,
                                                        FILTER_TYPE=c_decimating and ip_rate=1)
                             +use_data_cascade
                             +UTILS_PKG_select_integer(0,1,
                                                      coef_memory_type=1 and use_data_cascade=1)
                             +pq_inter_spec_case;
constant data_delay:integer:=UTILS_PKG_select_integer( 0,
                                                        1,
                                                        (MEMORY_PACKED=1 and MEMORY_TYPE=1)
                                                        and not( ( (FILTER_TYPE=c_polyphase_decimating
                                                                  or (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci)
                                                                  or FILTER_TYPE=c_interpolated_transform) and px_time=1)
                                                                or (FILTER_TYPE=c_decimating_half_band
                                                                    and use_ipbuff_sep_deci_hb=0)
                                                                or (FILTER_TYPE=c_decimating) ) )
                            +UTILS_PKG_select_integer(0,
                                                      1,
                                                      (px_time=1
                                                      and not( (FILTER_TYPE=c_polyphase_interpolating and num_channels>1) )
                                                      and not( (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_channels>1))
                                                      and not(  FILTER_TYPE=c_halfband_transform and px_time=1 and MEMORY_TYPE=1)
                                                      and not(  FILTER_TYPE=c_single_rate and px_time=1 and num_channels=1 and symmetrical=0 ) )
                                                       )
                            +UTILS_PKG_select_integer(0,
                                                      1,
                                                      (FILTER_TYPE=c_polyphase_interpolating
                                                      and num_channels>1
                                                      and num_filters>1)
                                                    or(FILTER_TYPE=c_polyphase_pq
                                                      and pq_type=pq_inter
                                                      and num_channels>1
                                                      and num_filters>1))
                            +use_data_cascade
                            +UTILS_PKG_select_integer(0,1,
                                                      coef_memory_type=1 and use_data_cascade=1)
                            +UTILS_PKG_select_integer(0,1,use_ipbuff_sep_deci_hb=1 and num_filters>1)
                            ;
constant cntrl_delay: integer:= UTILS_PKG_select_integer( 0,
                                                       1,
                                                       (MEMORY_PACKED=1))
                                +use_data_cascade
                                +UTILS_PKG_select_integer(0,1,
                                                      coef_memory_type=1 and use_data_cascade=1)
                                +pq_inter_spec_case;
signal  write_delay_in,
        write_delay_out,
        write_delay_out_no_reset,
        write_delay_hb_in,
        write_delay_hb_out
        :std_logic_vector(0 downto 0):="0";
signal  cntrl_delay_out,
        cntrl_delay_in
        :std_logic_vector(4 downto 0);
signal  write_data_delay_in,
        write_data_delay_out
        :std_logic_vector(data_width-1 downto 0);

signal  sclr_write,
        sclr_write_dly:std_logic:='0';
signal  accumulate_int,
        load_accum_int,
        load_accum_phase0_int,
        latch_accum_pq_deci_int,
        load_accum_gen,
        load_accum_gen2,
        latch_accum_int,
        new_data_int_dly,
        in_cnt_max_dly,
        in_cnt_max_src_dly,
        in_cnt_max_dly_gen,
        in_cnt_max_dly_gen_dly
        :std_logic:='0';

signal  in_cnt_max_in,
        in_cnt_max_out: std_logic_vector(0 downto 0);
signal  deci_ph_zero_end,
        deci_ph_zero_end_en,
        deci_ph_zero_end_cout,
        deci_ph_zero_end_mux,
        deci_ph_zero_end_over,
        deci_ph_last_start,
        deci_ph_2ndlast_stop,
        deci_ph_zero_start,
        deci_ph_zero_start_en,
        write_deci,
        last_deci
        :std_logic:='0';

signal  start_toggle
        :std_logic:='0';
constant addsub_toggle_init: std_logic_vector(0 downto 0):=std_logic_vector(
                                                              to_unsigned(
                                                                UTILS_PKG_select_integer(
                                                                  1,
                                                                  0,
                                                                  num_phases rem 2 > 0),
                                                                1));
signal  addsub_toggle:std_logic:=addsub_toggle_init(0);
signal phase_gt_gen: std_logic_vector(max(1,log2_ext(pq_p_val)) downto 0);
signal phase_gt_gen_cin: std_logic_vector(0 downto 0);
signal phase_end_seq,
       phase_gt_gen_reg,
       phase_gt_gen_mask,
       phase_gt_gen_mask_sel : std_logic:='0';
signal  nd_received,
        write_data_pq_inter: std_logic:='0';
signal chan_only_cnt:std_logic_vector(max(1,log2_ext(num_channels))-1 downto 0);
signal  inter_cnt,
        inter_cnt_result,
        inter_cnt_phase0:std_logic_vector(max(1,log2_ext(pq_p_val))-1 downto 0):=(others=>'0');
signal  inter_cnt_max,
        inter_cnt_max_cout,
        inter_cnt_result_max,
        inter_result,
        inter_result_cout,
        inter_result_sel,
        inter_result_gen,
        inter_phase0,
        inter_phase0_cout,
        inter_phase0_sel,
        inter_phase0_gen,
        inter_phase0_gen2,
        inter_phase0_sel2
        :std_logic:='0';
signal  inter_phase0_first_block:std_logic:='1';
signal new_data_slv : std_logic_vector(0 downto 0);
signal  int_ce,
        sclr,
        zero: std_logic;
signal  pad: std_logic_vector(max(chan_phase_width,
                              max(ip_buff_width_dist_mem,
                              max(ip_rate_width,
                              max(data_sym_addr_width,
                              max(data_addr_width, coef_addr_width)))))-1 downto 0);
begin
sclr <= reset;
pad  <= (others=>'0');
zero <= '0';
new_data_slv(0) <= new_data;
gce1 : if C_HAS_CE=0 generate
begin
  int_ce <= '1';
end     generate;
gce2 : if C_HAS_CE/=0 generate
begin
  int_ce <= CE;
end     generate;
gen_ip_rate: if (input_cycles>1) generate
  ipratecnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => ip_rate_width,
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**ip_rate_width)-( ip_rate-1 ),
    C_CONST_2_VAL => 0
  )
  port map(
    A_IN       => ip_rate_cnt,
    ADD_C0     => ip_rate_cin,
    ADD_C1     => ip_rate_max,
    ADD_C2     => '0',
    C_IN       => '0',
    D          => ip_rate_cnt,

    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );

  ipratemax: cmp_carrychain
  generic map(
    width => ip_rate_width,
    const => 1,
    value => ip_rate-2,
    c_family => C_FAMILY
  )
  port map(
    cin   => ip_rate_cin,
    A     => ip_rate_cnt,
    B     => pad(ip_rate_width-1 downto 0),
    cout  => ip_rate_max_cout,
    Res   => ip_rate_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
  iprate_cntrl:process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        ip_rate_cin<='0';
      elsif (int_ce='1') then
        if (new_data='1' and rfd_int='1') then
          ip_rate_cin<='1';
        elsif (ip_rate_max='1') then
          ip_rate_cin<='0';
        end if;
      end if;
    end if;
  end process;
  rfd_sel1<=not(not new_data and rfd_int);
  rfdgen_1: MUXCY_L
  port map(
    LO   => rfd_int_unreg1,

    CI  => ip_rate_max_cout,
    DI  => '1',
    S   => rfd_sel1
  );
  rfdintreg: FDSE
  generic map(
    INIT => '1'
  )
  port map(
    C => clk,
    CE => int_ce,
    S => sclr,
    D => rfd_int_unreg1,
    Q => rfd_int
  );
end generate gen_ip_rate;
gen_ip_rate_constant: if (input_cycles=1) generate
  process(clk)
  begin
    if(rising_edge(clk)) then
      rfd_int<='1';
    end if;
  end process;
end generate gen_ip_rate_constant;
rfd<=rfd_int;
gen_new_data_int: process(clk)
begin
  if (rising_edge(clk)) then
    if (sclr='1') then
      new_data_int_gen<='0';
      write_data_inter<='0';
      write_data_pq_inter<='0';
    elsif (int_ce='1') then

      new_data_int_gen<=new_data and rfd_int;
      if (hb_single_mac=0) then
        if not(use_delay_addr=1 or use_ipbuff_sep=1) then
          write_data_inter<=(new_data and rfd_int and in_cnt_max) or (base_max and not chan_max and chan_phase_first_phase);
        else
          write_data_inter<=(new_data_int and in_cnt_max_dly) or (base_max and not chan_max and chan_phase_first_phase);
        end if;
      else
        if not(use_delay_addr=1 or use_ipbuff_sep=1) then
          write_data_inter<=(new_data and rfd_int and in_cnt_max) or (base_max_dly and not chan_max and chan_phase_first_phase);
        else
          write_data_inter<=(new_data_int and in_cnt_max_dly) or (base_max_dly and not chan_max and chan_phase_first_phase);
        end if;
      end if;

      if not(use_delay_addr=1 or use_ipbuff_sep=1) then
        write_data_pq_inter<=(new_data and rfd_int and in_cnt_max and (chan_phase_max or not base_cin)) or (base_max and not chan_max and chan_phase_first_phase)
                              or (base_max and nd_received and chan_phase_max);
      else
        write_data_pq_inter<=(new_data_int and in_cnt_max_dly and (chan_phase_max or not base_cin)) or (base_max and not chan_max and chan_phase_first_phase)
                              or (base_max and nd_received and chan_phase_max);
      end if;
    end if;
  end if;
end process;
newdataintdly: shift_ram_fixed
generic map (
  C_FAMILY=> C_FAMILY,
  C_DEPTH => max(0,ipbuff_sep_delay-2),
  C_WIDTH => 1
)
port map (
  CLK     => clk,
  CE      => int_ce,
  D       => new_data_int_in,
  Q       => new_data_int_out
);
new_data_int_in(0)<=new_data_int_gen;
resetdly: shift_ram_fixed
generic map (
  C_FAMILY=> C_FAMILY,
  C_DEPTH => max(0,ipbuff_sep_delay-1),
  C_WIDTH => 1
)
port map (
  CLK     => clk,
  CE      => int_ce,
  D       => reset_dly_in,
  Q       => reset_dly_out
);
reset_dly_in(0)<=sclr;
gen_newdatadly:process(clk)
begin
  if (rising_edge(clk)) then
    if (sclr='1') then
      new_data_int_out_dly<='0';
    else
      if (ipbuff_sep_delay>1) then
        if (reset_blank='1') then
          new_data_int_out_dly<='0';
        else
          if (int_ce='1') then
            new_data_int_out_dly<=new_data_int_out(0);
          end if;
        end if;
      else
        if (int_ce='1') then
          new_data_int_out_dly<=new_data_int_out(0);
        end if;
      end if;
    end if;
  end if;
end process;
gen_resetblank:process(clk)
begin
  if (rising_edge(clk)) then

    if (sclr='1') then
      reset_blank<='1';
    elsif ( reset_dly_out(0)='1') then
      reset_blank<='0';
    end if;
  end if;
end process;
new_data_int<=new_data_int_out_dly when (use_ipbuff_sep=1 and ipbuff_sep_delay>1) else
              new_data_int_gen;
write_data_int<=write_deci when (FILTER_TYPE=c_decimating) else
                write_data_inter when (FILTER_TYPE=c_polyphase_interpolating and num_channels>1) else
                write_data_pq_inter when (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_channels>1) else
                px_start_gen when (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_channels=1) else
                write_data_inter when (FILTER_TYPE=c_decimating_half_band) else
                new_data_int;
gen_write_data: if true generate
begin

  datadelay: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => data_delay,
    C_WIDTH => data_width
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => write_data_delay_in,
    Q       => write_data_delay_out
  );
  writedelay: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => write_delay-UTILS_PKG_select_integer(0,1,write_delay>0),
    C_WIDTH => 1
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => write_delay_in,
    Q       => write_delay_out_no_reset
  );
  write_delay_reset: if (write_delay>0) generate
    writedelay_rst:process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr='1' or sclr_write='1') then
          write_delay_out<="0";
        elsif (int_ce='1') then
          write_delay_out<=write_delay_out_no_reset;
        end if;
      end if;
    end process;

    gen_single_dly: if (write_delay=1) generate
      sclr_write<='0';
    end generate gen_single_dly;
    gen_gt_sing_dly: if (write_delay>1) generate
      sclrwrite:process(clk)
      begin
        if (rising_edge(clk)) then
          if (sclr='1') then
            sclr_write<='1';
          elsif (int_ce='1' and sclr_write_dly='1') then
            sclr_write<='0';
          end if;
        end if;
      end process;

      sclrwritedly: shift_ram_bit
      generic map (
        C_FAMILY       => C_FAMILY,
        C_DEPTH        => write_delay-1,
        C_ENABLE_RLOCS => 0
      )
      port map (
        CLK   => CLK,
        CE    => int_ce,
        SCLR  => '0',
        D     => sclr,
        Q     => sclr_write_dly
      );

    end generate gen_gt_sing_dly;
  end generate write_delay_reset;
  write_delay_no_reset: if (write_delay=0) generate
    write_delay_out<=write_delay_out_no_reset;
  end generate write_delay_no_reset;

  writedelay_hb: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH =>UTILS_PKG_select_integer(
                    UTILS_PKG_select_integer(2,3,ip_buff_type=1),
                    1,
                    use_ipbuff_sep=1),
    C_WIDTH => 1
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => write_delay_hb_in,
    Q       => write_delay_hb_out
  );
  cntrldelay: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => cntrl_delay,
    C_WIDTH => 5
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => cntrl_delay_in,
    Q       => cntrl_delay_out
  );
  addsubtoggle: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => cntrl_delay+1
                       - UTILS_PKG_select_integer(0,1,no_data_mem=1)
                       + UTILS_PKG_select_integer(0,1,no_data_mem=0 and memory_type=1),
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => clk,
    CE    => int_ce,
    SCLR  => sclr,
    D     => addsub_toggle,
    Q     => sym_addsub
  );
  chanphasemaxdly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => cntrl_delay+1,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => clk,
    CE    => int_ce,
    SCLR  => sclr,
    D     => chan_phase_max,
    Q     => chan_phase_max_out
  );

    phasemaxdly: shift_ram_bit
    generic map (
      C_FAMILY       => C_FAMILY,
      C_DEPTH        => cntrl_delay+1,
      C_ENABLE_RLOCS => 0
    )
    port map (
      CLK   => clk,
      CE    => int_ce,
      SCLR  => sclr,
      D     => phase_cnt_max,
      Q     => phase_max_out
    );
  gen_ip_norm: if (use_ip_buff=0) generate
    write_data_delay_in<= ip_reg;
  end generate gen_ip_norm;
  gen_ip_buff: if (use_ip_buff=1) generate
    write_data_delay_in<= ip_buff_data_out(data_width-1 downto 0);
  end generate gen_ip_buff;

  cntrl_delay_in<='0'&
                  latch_accum_int&
                  load_accum_phase0_int&
                  load_accum_int&
                  accumulate_int
                  when not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) else
                  latch_accum_pq_deci_int&
                  latch_accum_int&
                  inter_phase0&
                  load_accum_int&
                  accumulate_int;
  data_out<=write_data_delay_out(data_width-1 downto 0);
  accumulate        <=cntrl_delay_out(0);
  load_accum        <=cntrl_delay_out(1);
  load_accum_phase0 <=cntrl_delay_out(2);
  latch_accum       <=cntrl_delay_out(3);
  gen_loadaccum_pqdeci: if (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) generate
    latch_accum_pq_deci<=cntrl_delay_out(4);
  end generate gen_loadaccum_pqdeci;

  write_delay_in(0)<=write_data_int;
  write_data<=write_delay_out(0);

  write_delay_hb_in(0)<=write_delay_in(0);
  write_data_hb_bypass<=write_delay_hb_out(0);
end generate gen_write_data;
base_cnt_map: if (use_ip_rate=1) generate
  base_cnt<=ext_bus(ip_rate_cnt,base_width,1);
end generate base_cnt_map;
gen_basecnt: if (use_ip_rate=0 and px_time>1) generate
  basecnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => base_width,
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**base_width)-( base_rate-1 ),
    C_CONST_2_VAL => 0
  )
  port map(
    A_IN       => base_cnt,
    ADD_C0     => base_cin,
    ADD_C1     => base_max,
    ADD_C2     => '0',
    C_IN       => '0',
    D          => base_cnt,

    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
end generate gen_basecnt;
gen_base_max_norm: if (px_time>1) generate
  basemax: cmp_carrychain
  generic map(
    width => base_width,
    const => 1,
    value => base_rate-2,
    c_family => C_FAMILY
  )
  port map(
    cin   => base_cin,
    A     => base_cnt,
    B     => pad(base_width-1 downto 0),
    cout  => base_max_cout,
    Res   => base_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
end generate gen_base_max_norm;
gen_base_max_parallel: if (px_time=1) generate
  gen_norm: if (FILTER_TYPE/=c_polyphase_interpolating and FILTER_TYPE/=c_interpolating_symmetry
                and not (FILTER_TYPE=c_polyphase_pq)) generate
    base_max_cout<='1';
    base_max<=new_data_int;
  end generate gen_norm;
  gen_inter: if (FILTER_TYPE=c_polyphase_interpolating or FILTER_TYPE=c_interpolating_symmetry
                or (FILTER_TYPE=c_polyphase_pq)) generate
    base_max_cout<=base_cin;
    base_max<=base_cin;
  end generate gen_inter;
end generate gen_base_max_parallel;
base_cntrl:process(clk)
begin
  if (rising_edge(clk)) then
    if (sclr='1') then
      base_cin<='0';
      px_start_gen<='0';

      nd_received<='0';
    elsif (int_ce='1') then
      px_start_gen<='0';
      if ( FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter) then
        if (use_ip_buff=1) then
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            if (new_data='1' and in_cnt_max='1') then
              nd_received<='1';
            elsif (px_start_gen='1') then
              nd_received<='0';
            end if;
            if (new_data='1' and in_cnt_max='1' and rfd_int='1') then
                base_cin<='1';
            elsif (chan_phase_max='1' and nd_received='0') then
              base_cin<='0';
            end if;
            if (new_data='1' and in_cnt_max='1' and rfd_int='1' and (chan_phase_max='1' or base_cin='0'))
              or (chan_phase_max='1' and nd_received='1') then
              px_start_gen<='1';
            end if;
          else
            if (new_data_int='1' and in_cnt_max_dly='1') then
              nd_received<='1';
            elsif (px_start_gen='1') then
              nd_received<='0';
            end if;
            if (new_data_int='1' and in_cnt_max_dly='1') then
                base_cin<='1';
            elsif (chan_phase_max='1' and nd_received='0') then
              base_cin<='0';
            end if;

            if (new_data_int='1' and in_cnt_max='1' and (chan_phase_max='1' or base_cin='0'))
              or (chan_phase_max='1' and nd_received='1') then
              px_start_gen<='1';
            end if;
          end if;
        else
           if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            if (new_data='1' and rfd_int='1') then
              nd_received<='1';
            elsif (px_start_gen='1') then
              nd_received<='0';
            end if;
            if (new_data='1' and rfd_int='1') then
                base_cin<='1';
            elsif (chan_phase_max='1' and nd_received='0') then
              base_cin<='0';
            end if;

            if (new_data='1' and rfd_int='1' and (chan_phase_max='1' or base_cin='0'))
              or (chan_phase_max='1' and nd_received='1') then
              px_start_gen<='1';
            end if;
          else

            if (new_data_int='1') then
              nd_received<='1';
            elsif (px_start_gen='1') then
              nd_received<='0';
            end if;
            if (new_data_int='1') then
                base_cin<='1';
            elsif (chan_phase_max='1' and nd_received='0') then
              base_cin<='0';
            end if;

            if (new_data_int='1' and (chan_phase_max='1' or base_cin='0'))
              or (chan_phase_max='1' and nd_received='1') then
              px_start_gen<='1';
            end if;
          end if;
        end if;
      elsif (FILTER_TYPE=c_polyphase_interpolating or FILTER_TYPE=c_decimating) then
        if (use_ip_buff=1) then
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            if (new_data='1' and in_cnt_max='1' and rfd_int='1') then
                base_cin<='1';
                px_start_gen<='1';
            elsif (chan_phase_max='1') then
              base_cin<='0';
            end if;
          else
            if (new_data_int='1' and in_cnt_max_dly='1') then
                base_cin<='1';
                px_start_gen<='1';
            elsif (chan_phase_max='1') then
              base_cin<='0';
            end if;
          end if;
        else
           if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            if (new_data='1' and rfd_int='1') then
                base_cin<='1';
                px_start_gen<='1';
            elsif (chan_phase_max='1') then
              base_cin<='0';
            end if;
          else

            if (new_data_int='1') then
                base_cin<='1';
                px_start_gen<='1';
            elsif (chan_phase_max='1') then
              base_cin<='0';
            end if;
          end if;
        end if;
      elsif (FILTER_TYPE=c_decimating_half_band) then
        if (use_ip_buff=1) then
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            if (hb_single_mac=0) then
              if (new_data='1' and in_cnt_max='1' and rfd_int='1') then
                base_cin<='1';
                px_start_gen<='1';
              elsif (chan_phase_max='1') then
                base_cin<='0';
              end if;
            else
              if (new_data='1' and in_cnt_max='1' and rfd_int='1') then
                base_cin<='1';
                px_start_gen<='1';
              else
                if (chan_phase_max_dly='1') then
                  base_cin<='0';
                else
                  base_cin<=(base_cin and not base_max) or (base_cin_ext and base_max_dly);
                end if;
              end if;
            end if;
          else
            if (hb_single_mac=0) then
              if (new_data_int='1' and in_cnt_max_dly='1') then
                base_cin<='1';
                px_start_gen<='1';
              elsif (chan_phase_max='1') then
                base_cin<='0';
              end if;

            else
              if (new_data_int='1' and in_cnt_max_dly='1') then
                base_cin<='1';
                px_start_gen<='1';
              else
                if (chan_phase_max_dly='1') then
                  base_cin<='0';
                else
                  base_cin<=(base_cin and not base_max) or (base_cin_ext and base_max_dly);
                end if;
              end if;
            end if;
          end if;
        else
          report "invalid configuration for deci halfband" severity failure;
        end if;

      elsif (FILTER_TYPE=c_interpolating_symmetry) then
        if (use_delay_addr=1) then
          if (new_data_int='1') then
            base_cin<='1';
            px_start_gen<='1';
          elsif (phase_cnt_max='1') then
            base_cin<='0';
          end if;
        else
          if (  new_data='1' and rfd_int='1') then
            base_cin<='1';
            px_start_gen<='1';
          elsif (phase_cnt_max='1') then
            base_cin<='0';
          end if;
        end if;

      elsif (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) then

        if (use_delay_addr=1) then
          if (new_data_int='1') then
            base_cin<='1';
            px_start_gen<='1';
          elsif (inter_cnt_max='1') then
            base_cin<='0';
          end if;
        else
          if (  new_data='1' and rfd_int='1') then
            base_cin<='1';
            px_start_gen<='1';
          elsif (inter_cnt_max='1') then
            base_cin<='0';
          end if;
        end if;
      else
        if (use_ip_buff=0 and  use_bypass_buff=0) then
          if (use_ipbuff_sep=1 or use_delay_addr=1) then
            if (new_data_int='1') then
              base_cin<='1';
              px_start_gen<='1';
            elsif (base_max='1') then
              base_cin<='0';
            end if;
          else
            if (  new_data='1' and rfd_int='1') then
              base_cin<='1';
              px_start_gen<='1';
            elsif (base_max='1') then
              base_cin<='0';
            end if;
          end if;
        else
          report "invalid configuration for default" severity failure;
        end if;
      end if;
    end if;
  end if;
end process;
px_start<=px_start_gen;
gen_chanphase_norm: if (FILTER_TYPE/=c_polyphase_interpolating)
           and not(FILTER_TYPE=c_polyphase_decimating and px_time=1)
           and not(FILTER_TYPE=c_decimating)
           and not(FILTER_TYPE=c_interpolating_symmetry)
           and not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter)
           and not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) generate

  gen_multi_ch_or_phase: if (chan_phase_cnt_phase>1
                            or num_channels>1) generate
    channelphasecnt: priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => chan_phase_width,
      C_CONST_0_VAL => chan_phase_base_step*chan_phase_cnt_phase,
      C_CONST_1_VAL => ((2**chan_phase_width)-(chan_phase_base_step*chan_phase_cnt_phase*(num_channel-1)))+chan_phase_base_step,
      C_CONST_2_VAL => (2**chan_phase_width)-((chan_phase_base_step*(chan_phase_cnt_phase)*num_channel)-chan_phase_base_step)
    )
    port map(
      A_IN       => chan_phase_cnt,

      ADD_C0     => base_max,
      ADD_C1     => chan_max,
      ADD_C2     => chan_phase_max,

      C_IN       => '0',
      D          => chan_phase_cnt,

      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );

    chanphasemax: cmp_carrychain
    generic map(
      width => chan_phase_width,
      const => 1,
      value => (chan_phase_base_step*(chan_phase_cnt_phase)*num_channel)-chan_phase_base_step,
        c_family => C_FAMILY
    )
    port map(
      cin   => base_max_cout,
      A     => chan_phase_cnt,
      B     => pad(chan_phase_width-1 downto 0),
      cout  => chan_phase_max_cout,
      Res   => chan_phase_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
  end generate gen_multi_ch_or_phase;

  gen_single_ch: if (num_channels=1 and chan_phase_cnt_phase=1) generate
    chan_phase_max_cout<=base_max_cout;
    chan_phase_max<=base_max;

  end generate gen_single_ch;
end generate gen_chanphase_norm;
gen_chanphase_inter: if (FILTER_TYPE=c_polyphase_interpolating)
           or (FILTER_TYPE=c_polyphase_decimating and px_time=1)
           or (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter) generate
  channelphasecnt: priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => chan_phase_width,
    C_CONST_0_VAL => chan_phase_base_step*chan_phase_cnt_phase,
    C_CONST_1_VAL => ((2**chan_phase_width)-(chan_phase_base_step*chan_phase_cnt_phase*(num_channel-1))),
    C_CONST_2_VAL => 0
  )
  port map(
    A_IN       => chan_phase_cnt,
    ADD_C0     => base_max,
    ADD_C1     => chan_max,
    ADD_C2     => '0',
    C_IN       => '0',
    D          => chan_phase_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
  gen_multich: if (num_channels>1) generate
    chanphasemax: cmp_carrychain
    generic map(
      width => chan_phase_width,
      const => 1,
      value => (chan_phase_base_step*(chan_phase_cnt_phase)*num_channel)-chan_phase_base_step
                -UTILS_PKG_select_integer(0,1,px_time=1),
      c_family => C_FAMILY
    )
    port map(
      cin   => base_max_cout,
      A     => chan_phase_cnt,
      B     => pad(chan_phase_width-1 downto 0),
      cout  => chan_only_max_cout,
      Res   => chan_only_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
  end generate gen_multich;
  gen_singch: if (num_channels=1) generate
    chan_only_max_cout<=base_max_cout;
    chan_only_max<=base_max;
  end generate gen_singch;
end generate gen_chanphase_inter;
gen_chanphase_decimation: if (FILTER_TYPE=c_decimating) generate

  gen_multich: if (num_channels>1) generate
    channelphasecnt: priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => chan_phase_width,
      C_CONST_0_VAL => chan_phase_base_step,
      C_CONST_1_VAL => ((2**chan_phase_width)-(chan_phase_base_step*(num_channel-1))),
      C_CONST_2_VAL => 0
    )
    port map(
      A_IN       => chan_phase_cnt,

      ADD_C0     => base_max,
      ADD_C1     => chan_phase_max,
      ADD_C2     => '0',
      C_IN       => '0',
      D          => chan_phase_cnt,

      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );

    chanphasemax: cmp_carrychain
    generic map(
      width => chan_phase_width,
      const => 1,
      value => (chan_phase_base_step*(num_channel-1)),
      c_family => C_FAMILY
    )
    port map(
      cin   => base_max_cout,
      A     => chan_phase_cnt,
      B     => pad(chan_phase_width-1 downto 0),
      cout  => chan_phase_max_cout,
      Res   => chan_phase_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
  end generate gen_multich;

  gen_singch: if (num_channels=1) generate
    chan_phase_max_cout<=base_max_cout;
    chan_phase_max<=base_max;
  end generate gen_singch;
end generate gen_chanphase_decimation;
gen_chanphase_interpolating_sym: if (FILTER_TYPE=c_interpolating_symmetry) generate
  gen_multich: if (num_channels>1) generate
    channelphasecnt: priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => chan_phase_width,
      C_CONST_0_VAL => chan_phase_base_step,
      C_CONST_1_VAL => ((2**chan_phase_width)-(chan_phase_base_step*(num_channel-1))),
      C_CONST_2_VAL => 0
    )
    port map(
      A_IN       => chan_phase_cnt,
      ADD_C0     => phase_cnt_max,
      ADD_C1     => chan_phase_max,
      ADD_C2     => '0',

      C_IN       => '0',
      D          => chan_phase_cnt,
      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );
    chanphasemax: cmp_carrychain
    generic map(
      width => chan_phase_width,
      const => 1,
      value => (chan_phase_base_step*(num_channel-1)),
      c_family => C_FAMILY
    )
    port map(
      cin   => phase_cnt_max_cout,
      A     => chan_phase_cnt,
      B     => pad(chan_phase_width-1 downto 0),
      cout  => chan_phase_max_cout,
      Res   => chan_phase_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
  end generate gen_multich;

  gen_singch: if (num_channels=1) generate
    chan_phase_max_cout<=phase_cnt_max_cout;
    chan_phase_max<=phase_cnt_max;
  end generate gen_singch;
end generate gen_chanphase_interpolating_sym;
gen_chanphase_pq_deci: if (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) generate
  intercnt: priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => max(1,log2_ext(pq_p_val)),
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**max(1,log2_ext(pq_p_val)))-(pq_p_val-1),
    C_CONST_2_VAL => 0
  )
  port map(
    A_IN       => inter_cnt,
    ADD_C0     => base_max,
    ADD_C1     => inter_cnt_max,
    ADD_C2     => '0',
    C_IN       => '0',
    D          => inter_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
  intercntmax: cmp_carrychain
  generic map(
    width => max(1,log2_ext(pq_p_val)),
    const => 1,
    value => pq_p_val-1
            -UTILS_PKG_select_integer(0,1,px_time=1),
    c_family => C_FAMILY
  )
  port map(
    cin   => base_max_cout,
    A     => inter_cnt,
    B     => pad(max(1,log2_ext(pq_p_val))-1 downto 0),
    cout  => inter_cnt_max_cout,
    Res   => inter_cnt_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
  channelphasecnt: priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => chan_phase_width,
    C_CONST_0_VAL => chan_phase_base_step*chan_phase_cnt_phase,
    C_CONST_1_VAL => ((2**chan_phase_width)-(chan_phase_base_step*chan_phase_cnt_phase*(num_channel-1)))+chan_phase_base_step,
    C_CONST_2_VAL => (2**chan_phase_width)-((chan_phase_base_step*(chan_phase_cnt_phase)*num_channel)-chan_phase_base_step)
  )
  port map(
    A_IN       => chan_phase_cnt,
    ADD_C0     => inter_cnt_max,
    ADD_C1     => chan_only_max,
    ADD_C2     => chan_phase_max,

    C_IN       => '0',
    D          => chan_phase_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
  gen_multich: if (num_channels>1) generate
    channelcnt: priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => max(1,log2_ext(num_channels)),
      C_CONST_0_VAL => 1,
      C_CONST_1_VAL => (2**max(1,log2_ext(num_channels)))-(num_channels-1),
      C_CONST_2_VAL => 0
    )
    port map(
      A_IN       => chan_only_cnt,

      ADD_C0     => inter_cnt_max,
      ADD_C1     => chan_only_max,
      ADD_C2     => '0',

      C_IN       => '0',
      D          => chan_only_cnt,
      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );
    chanphasemax: cmp_carrychain
    generic map(
      width => max(1,log2_ext(num_channels)),
      const => 1,
      value => num_channels-1,
      c_family => C_FAMILY
    )
    port map(
      cin   => inter_cnt_max_cout,
      A     => chan_only_cnt,
      B     => pad(max(1,log2_ext(num_channels))-1 downto 0),
      cout  => chan_only_max_cout,
      Res   => chan_only_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
  end generate gen_multich;

  gen_singch: if (num_channels=1) generate
    chan_only_max_cout<=inter_cnt_max_cout;
    chan_only_max<=inter_cnt_max;
  end generate gen_singch;
end generate gen_chanphase_pq_deci;
gen_chanmax: if ( chan_phase_cnt_phase>1 or
                  FILTER_TYPE=c_polyphase_decimating or
                  (FILTER_TYPE=c_decimating_half_band and hb_single_mac=1) ) generate
  chanmax: process(clk)
  begin
    if (rising_edge(clk)) then

      if (sclr='1') then
        chan_max<='0';
      elsif (int_ce='1') then
        if (num_channels>1) then
          if (FILTER_TYPE=c_decimating and ip_rate=1) then

            if (base_max='1') then
              if (unsigned(chan_phase_cnt) = to_unsigned( chan_phase_base_step*chan_phase_cnt_phase*(num_channel-2),chan_phase_width)) then
                chan_max<='1';
              else
                chan_max<='0';
              end if;
            end if;

          elsif (FILTER_TYPE=c_polyphase_interpolating and px_time=1) then
            if (unsigned(chan_phase_cnt) = to_unsigned( (num_channel-2),chan_phase_width) and base_cin='1') then
              chan_max<='1';
            else
              chan_max<='0';
            end if;
          elsif (FILTER_TYPE=c_polyphase_decimating and px_time=1) then
            if ( base_cin='1') then
              if (unsigned(chan_phase_cnt) = to_unsigned( (num_channel-2),chan_phase_width)) then
                chan_max<='1';
              else
                chan_max<='0';
              end if;
            end if;

          else

            if (px_time=1) then

              if (unsigned(chan_phase_cnt) = to_unsigned( chan_phase_base_step*chan_phase_cnt_phase*(num_channel-1),chan_phase_width)) then
                chan_max<='1';
              else
                chan_max<='0';
              end if;

            else

              if (unsigned(chan_phase_cnt) >= to_unsigned( chan_phase_base_step*chan_phase_cnt_phase*(num_channel-1),chan_phase_width)) then
                chan_max<='1';
              else
                chan_max<='0';
              end if;

            end if;

          end if;
        else
          chan_max<='1';
        end if;

      end if;
    end if;
  end process;
end generate gen_chanmax;
gen_chanmax_map: if (chan_phase_cnt_phase=1 and
                     FILTER_TYPE/=c_polyphase_decimating and
                     not(FILTER_TYPE=c_decimating_half_band and hb_single_mac=1) ) generate
  chan_max<=chan_only_max when (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) or
                               (FILTER_TYPE=c_polyphase_interpolating) or
                               (FILTER_TYPE=c_polyphase_decimating and px_time=1) or
                               (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter) else
            chan_phase_max;
end generate gen_chanmax_map;
chanphasefirstphase:process(clk)
begin
        if (rising_edge(clk)) then
                if (sclr='1') then
      chan_phase_first_phase<='1';
                elsif (int_ce='1') then

      if (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) then

        if (chan_only_max='1' and phase_cnt_max='1') then
          chan_phase_first_phase<='1';
        elsif (chan_only_max='1' and base_max='1') then
          chan_phase_first_phase<='0';
        end if;
      elsif (FILTER_TYPE=c_polyphase_decimating and px_time=1) then
        if (chan_phase_max='1' and chan_max='1' and base_max='1') then
          chan_phase_first_phase<='1';
        elsif (chan_max='1' and base_max='1') then
          chan_phase_first_phase<='0';
        end if;
      else
        if (chan_phase_max='1') then
          chan_phase_first_phase<='1';
        elsif (chan_max='1' and base_max='1') then
          chan_phase_first_phase<='0';
        end if;
      end if;
                end if;
        end if;
end process;
gen_data_base_norm: if (FILTER_TYPE/=c_decimating) generate
  gen_data_base_gt2px: if (px_time>2) generate
    gen_data_base_bram: if (MEMORY_TYPE=1) generate
      datacnt: priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => data_base_width,
        C_CONST_0_VAL => 1,
        C_CONST_1_VAL => (2**data_base_width)-px_time+1,
        C_CONST_2_VAL => 0
      )
      port map(
        A_IN       => data_cnt,

        ADD_C0     => base_cin,
        ADD_C1     => data_max,
        ADD_C2     => chan_phase_max,

        C_IN       => '0',
        D          => data_cnt,

        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

      datamax: cmp_carrychain
      generic map(
        width => data_base_width,
        const => 1,
        value => px_time-2,
        c_family => C_FAMILY
      )
      port map(
        cin   => data_max_sup,
        A     => data_cnt,
        B     => pad(data_base_width-1 downto 0),
        cout  => data_max_cout,
        Res   => open,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );

      data_max_sup<=not chan_phase_max;

      data_max_sel<=not(data_max and chan_phase_max);

      datamaxmux: MUXCY
      port map(
        O   => data_max_mux,
        CI  => data_max_cout,
        DI  => '1',
        S   => data_max_sel
      );

      datamaxreg: FDRE
      generic map(
        INIT => '0'
      )
      port map(
        C => clk,
        CE => int_ce,
        R => sclr,
        D => data_max_reg_ip,
        Q => data_max
      );

      data_max_reg_ip<=data_max_mux when base_cin='1' else data_max;

    end generate gen_data_base_bram;
    gen_data_base_srl16: if (MEMORY_TYPE=0) generate

      datacnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => data_base_width,
        C_CONST_0_VAL => num_channel*chan_phase_cnt_phase,
        C_CONST_1_VAL => (2**data_base_width)-( (px_time-1)*(num_channel*chan_phase_cnt_phase) ),
        C_CONST_2_VAL => 0
      )
      port map(
        A_IN       => data_cnt,

        ADD_C0     => base_cin,
        ADD_C1     => base_max,
        ADD_C2     => '0',
        C_IN       => '0',
        D          => data_cnt,

        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );
    end generate gen_data_base_srl16;

  end generate gen_data_base_gt2px;
  gen_data_base_px2: if (px_time=2) generate

    process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr='1') then
          data_cnt<=(others=>'0');
          data_cnt_val2<=std_logic_vector(to_unsigned(num_channels*chan_phase_cnt_phase,data_base_width));
        elsif (int_ce='1') then

          if (MEMORY_TYPE=0) then
            if (base_cin='1') then
              data_cnt<=data_cnt_val2;
              data_cnt_val2<=data_cnt;
            end if;

          else
            if (base_cin='1' and chan_phase_max='0') then
              data_cnt(0)<=not data_cnt(0);
            end if;
          end if;
        end if;
      end if;
    end process;

  end generate gen_data_base_px2;
end generate gen_data_base_norm;
gen_data_base_deci: if (FILTER_TYPE=c_decimating) generate
  datacnt: priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => data_base_width,
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**data_base_width)-px_time+1,
    C_CONST_2_VAL => -1*(num_phases-1)
  )
  port map(
    A_IN       => data_cnt,
    ADD_C0     => base_cin,
    ADD_C1     => data_max,
    ADD_C2     => chan_phase_max,
    C_IN       => '0',
    D          => data_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
  datamax: cmp_carrychain
  generic map(
    width => data_base_width,
    const => 1,
    value => px_time-2,
    c_family => C_FAMILY
  )
  port map(
    cin   => base_cin,
    A     => data_cnt,
    B     => pad(data_base_width-1 downto 0),
    cout  => open,
    Res   => data_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
end generate gen_data_base_deci;
gen_coef_norm: if (hb_single_mac=0) generate
  gen_coef_cnt: if (coef_cnt_phase>1) generate
    gen_norm: if (FILTER_TYPE/=c_interpolating_symmetry)
                and not(FILTER_TYPE=c_polyphase_pq) generate
      coefcnt: priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => coef_base_width,
        C_CONST_0_VAL => (2**coef_base_width)-(px_time),
        C_CONST_1_VAL => 0,
        C_CONST_2_VAL => (2**coef_base_width)-(px_time*coef_cnt_phase)
      )
      port map(
        A_IN       => coef_cnt,
        ADD_C0     => base_max,
        ADD_C1     => chan_max,
        ADD_C2     => chan_phase_max,
        C_IN       => base_cin,
        D          => coef_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

    end generate gen_norm;

    gen_pq_inter: if (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter) generate

      coefcnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => coef_base_width,
        C_CONST_0_VAL => (2**coef_base_width)-(px_time),
        C_CONST_1_VAL => 0,
        C_CONST_2_VAL => (2**coef_base_width)-(px_time*coef_cnt_phase)
      )
      port map(
        A_IN       => coef_cnt,
        ADD_C0     => base_max,
        ADD_C1     => chan_max,
        ADD_C2     => phase_end_seq,
        C_IN       => base_cin,
        D          => coef_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

    end generate gen_pq_inter;

    gen_pq_deci: if (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) generate

      coefcnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => coef_base_width,
        C_CONST_0_VAL => (2**coef_base_width)-(px_time*pq_p_val),
        C_CONST_1_VAL => 0,
        C_CONST_2_VAL => (2**coef_base_width)-(px_time*coef_cnt_phase)
      )
      port map(
        A_IN       => coef_cnt,
        ADD_C0     => inter_cnt_max,
        ADD_C1     => chan_only_max,
        ADD_C2     => chan_phase_max,
        C_IN       => base_cin,
        D          => coef_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );
    end generate gen_pq_deci;
    gen_intersym: if (FILTER_TYPE=c_interpolating_symmetry) generate
      coefcnt: priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => coef_base_width,
        C_CONST_0_VAL => 0,
        C_CONST_1_VAL => 0,
        C_CONST_2_VAL => (2**coef_base_width)-(px_time*coef_cnt_phase)
      )
      port map(
        A_IN       => coef_cnt,
        ADD_C0     => base_max,
        ADD_C1     => '0',
        ADD_C2     => phase_cnt_max,
        C_IN       => base_cin,
        D          => coef_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );
    end generate gen_intersym;
  end generate gen_coef_cnt;
  gen_coef_map: if (coef_cnt_phase=1) generate

    coef_cnt<=base_cnt;

  end generate gen_coef_map;
end generate gen_coef_norm;
gen_coef_hb_smac: if (hb_single_mac=1) generate
  coef_cnt_cntrl: process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        chan_phase_max_dly<='0';
        base_cin_ext<='0';
      elsif (int_ce='1') then
        chan_phase_max_dly<=chan_phase_max;
        if (use_ip_buff=1) then
          if not(use_delay_addr=1 or use_ipbuff_sep_deci_hb=1) then
            if (new_data='1' and in_cnt_max='1' and rfd_int='1') then
                base_cin_ext<='1';
            elsif (chan_phase_max_dly='1') then
              base_cin_ext<='0';
            end if;

          else

            if (new_data_int='1' and in_cnt_max_dly='1') then
                base_cin_ext<='1';
            elsif (chan_phase_max_dly='1') then
              base_cin_ext<='0';
            end if;
          end if;

        else

          if (use_delay_addr=1) then
            if (new_data_int='1') then
                base_cin_ext<='1';
            elsif (base_max_dly='1') then
              base_cin_ext<='0';
            end if;
          else
            if (new_data='1' and rfd_int='1') then
                base_cin_ext<='1';
            elsif (base_max_dly='1') then
              base_cin_ext<='0';
            end if;
          end if;
        end if;
      end if;
    end if;
  end process;
  coefcnt: priority_counter
  generic map(
    C_HAS_C_IN    => 1,
    C_WIDTH       => coef_base_width,
    C_CONST_0_VAL => (2**coef_base_width)-((px_time)+1),
    C_CONST_1_VAL => 0,
    C_CONST_2_VAL => 0
  )
  port map(
    A_IN       => coef_cnt,
    ADD_C0     => base_max_dly,
    ADD_C1     => '0',
    ADD_C2     => '0',
    C_IN       => base_cin_ext,
    D          => coef_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
end generate gen_coef_hb_smac;
gen_data_sym_base_norm: if (FILTER_TYPE/=c_decimating  and symmetrical=1) generate
  gen_data_sym_base_gt2px: if (px_time>2) generate
    gen_data_sym_base_bram: if (MEMORY_TYPE=1) generate

      datasymcnt: priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => data_sym_base_width,
        C_CONST_0_VAL => 1,
        C_CONST_1_VAL => ((2**data_sym_base_width)-px_time )+1 ,
        C_CONST_2_VAL => ((2**data_sym_base_width)-px_time )+1 ,
        C_INIT_SCLR_VAL => data_sym_cnt_offset
      )
      port map(
        A_IN       => data_sym_cnt,
        ADD_C0     => base_cin,
        ADD_C1     => data_sym_max,
        ADD_C2     => data_sym_step_over_en_reg,

        C_IN       => chan_phase_max,
        D          => data_sym_cnt,

        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

      datasymmax: cmp_carrychain_dualval
      generic map(
        width => data_sym_base_width,
        value1 => px_time-2 +data_sym_cnt_offset,
        value2 => px_time-3 +data_sym_cnt_offset,
        c_family => C_FAMILY
      )
      port map(
        cin   => '1',
        A     => data_sym_cnt,
        sel   => chan_phase_max,
        cout  => data_sym_max_cout,
        Res   => open,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );
      data_sym_max_sel<=base_cin;

      datasymmaxsel: MUXCY
      port map(
        O   => data_sym_max_spec,

        CI  => data_sym_max_cout,
        DI  => data_sym_max,
        S   => data_sym_max_sel
      );
      datasymmaxreg: FDRE
      generic map(
        INIT => '0'
      )
      port map(
        C => clk,
        CE => int_ce,
        R => sclr,
        D => data_sym_max_reg_ip,
        Q => data_sym_max
      );
      data_sym_max_reg_ip<=data_sym_max_spec when base_cin='1' else data_sym_max;
      datasymstepover: cmp_carrychain
      generic map(
        width => data_sym_base_width,
        const => 1,
        value => UTILS_PKG_select_integer(px_time-4 ,px_time+(px_time-4),px_time-4<0) +data_sym_cnt_offset,
        c_family => C_FAMILY
      )
      port map(
        cin   => '1',
        A     => data_sym_cnt,
        B     => pad(data_sym_base_width-1 downto 0),
        cout  => open,
        Res   => data_sym_step_over,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );
      datasymstepoverenable: MUXCY_L
      port map(
        LO   => data_sym_step_over_en,

        CI  => chan_phase_max_cout,
        DI  => '0',
        S   => data_sym_step_over_sel
      );
      data_sym_step_over_sel<=base_cin and data_sym_step_over;


      datasymstepoverenablereg: FDRE
      generic map(
        INIT => '0'
      )
      port map(
        C => clk,
        CE => int_ce,
        R => sclr,
        D => data_sym_step_over_en,
        Q => data_sym_step_over_en_reg
      );
    end generate gen_data_sym_base_bram;
    gen_data_sym_base_srl16: if (MEMORY_TYPE=0) generate

      datacnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => data_sym_base_width,
        C_CONST_0_VAL => -1*(num_channel*chan_phase_cnt_phase),
        C_CONST_1_VAL => ( (px_time-1)*(num_channel*chan_phase_cnt_phase) ),
        C_CONST_2_VAL => 0,
        C_INIT_SCLR_VAL => ( (px_time-1)*(num_channel*chan_phase_cnt_phase) )

      )
      port map(
        A_IN       => data_sym_cnt,
        ADD_C0     => base_cin,
        ADD_C1     => base_max,
        ADD_C2     => '0',

        C_IN       => '0',
        D          => data_sym_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

    end generate gen_data_sym_base_srl16;

  end generate gen_data_sym_base_gt2px;
  gen_data_sym_base_px2: if (px_time=2) generate
    gen_srl16: if (MEMORY_TYPE=0) generate
      gen_1bit: if (data_sym_base_width=1) generate
        data_sym_cnt<=not data_cnt;
      end generate gen_1bit;

      gen_gtbit: if (data_sym_base_width>1) generate
        process(clk)
        begin
          if (rising_edge(clk)) then
            if (sclr='1') then
              data_sym_cnt<=std_logic_vector(to_unsigned((num_channels*chan_phase_cnt_phase),data_sym_base_width));
              data_sym_cnt_val2<=(others=>'0');
            elsif (int_ce='1') then
              if (base_cin='1') then
                data_sym_cnt<=data_sym_cnt_val2;
                data_sym_cnt_val2<=data_sym_cnt;
              end if;
            end if;
          end if;
        end process;
      end generate gen_gtbit;

    end generate gen_srl16;

    gen_bram: if (MEMORY_TYPE=1) generate
      gen_no_offset: if (SYM_OFFSET=0) generate
        data_sym_cnt<=data_cnt;
      end generate gen_no_offset;

      gen_offset: if (SYM_OFFSET>0) generate

        process(clk)
        begin
          if (rising_edge(clk)) then

            if (sclr='1') then
              data_sym_cnt<=std_logic_vector(to_unsigned(SYM_OFFSET,data_sym_base_width));
              data_sym_cnt_val2<=std_logic_vector(to_unsigned(SYM_OFFSET+1,data_sym_base_width));
            elsif (int_ce='1') then
              if (base_cin='1' and chan_phase_max='0') then
                data_sym_cnt<=data_sym_cnt_val2;
                data_sym_cnt_val2<=data_sym_cnt;
              end if;

            end if;
          end if;
        end process;

      end generate gen_offset;
    end generate gen_bram;

  end generate gen_data_sym_base_px2;
end generate gen_data_sym_base_norm;
gen_data_sym_base_decimation: if (FILTER_TYPE=c_decimating  and symmetrical=1) generate
  datasymcnt: combined_enable_counter
  generic map(
    C_HAS_C_IN    => 1,
    C_WIDTH       => data_sym_base_width,
    C_CONST_0_VAL => (2**data_sym_base_width)-px_time,
    C_CONST_1_VAL => num_phases,
    C_CONST_2_VAL => (2**data_sym_base_width)-px_time,
    C_INIT_SCLR_VAL => data_sym_cnt_offset
  )
  port map(
    A_IN       => data_sym_cnt,
    ADD_C0     => data_sym_max,
    ADD_C1     => chan_phase_max,
    ADD_C2     => data_sym_step_over,
    C_IN       => base_cin,
    D          => data_sym_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
  datasymmax: cmp_carrychain
  generic map(
    width => data_sym_base_width,
    const => 1,
    value => px_time-2+data_sym_cnt_offset,
    c_family => C_FAMILY
  )
  port map(
    cin   => base_cin,
    A     => data_sym_cnt,
    B     => pad(data_sym_base_width-1 downto 0),
    cout  => open,
    Res   => data_sym_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );

  datasymstepover: cmp_carrychain
  generic map(
    width => data_sym_base_width,
    const => 1,
    value => px_time-2-num_phases+data_sym_cnt_offset,
    c_family => C_FAMILY
  )
  port map(
    cin   => base_cin,
    A     => data_sym_cnt,
    B     => pad(data_sym_base_width-1 downto 0),
    cout  => open,
    Res   => data_sym_step_over,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
end generate gen_data_sym_base_decimation;
gen_filt_sel: if (num_filters>1) generate
  gen_filt_delay: if (MEMORY_PACKED=0 or use_data_cascade=1) generate
    filt_addr<=ip_reg_filt;

  end generate gen_filt_delay;

  gen_filt_decode: if (MEMORY_PACKED=1 and use_data_cascade=0) generate
    filt_pad<=ext_bus(filt_src,UTILS_PKG_select_integer(4,5,num_filters>16),1);
    filter_offset_rom: drom
    generic map(
      C_FAMILY          => C_FAMILY,
      data_width        => filt_addr_width,
      depth             => UTILS_PKG_select_integer(  16,
                                                      32,
                                                      num_filters>16),
      addr_width        => UTILS_PKG_select_integer(  4,
                                                      5,
                                                      num_filters>16),
      c_elaboration_dir => C_ELABORATION_DIR,
      mif_file          => offset_mif_file,
      C_HAS_CE          => C_HAS_CE
    )
    port map(
      addr => filt_pad,
      data => filt_addr,
      clk  => clk,
      ce   => int_ce
    );
  end generate gen_filt_decode;
  filtaddrlatch: process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        filt_addr_latch<=(others=>'0');
        filt_addr_dly<=(others=>'0');
        new_data_int_gen_reg<='0';

        write_data_inter_dly1<='0';
        write_data_inter_dly2<='0';
        base_sep_max_dly1<='0';
        base_sep_max_dly2<='0';
        base_sep_max_dly3<='0';
      elsif (int_ce='1') then
        new_data_int_gen_reg<=new_data_int_gen;

        write_data_inter_dly1<=write_data_inter;
        write_data_inter_dly2<=write_data_inter_dly1;
        base_sep_max_dly1<=base_sep_max;
        base_sep_max_dly2<=base_sep_max_dly1;
        base_sep_max_dly3<=base_sep_max_dly2;
        if not(FILTER_TYPE=c_polyphase_interpolating and num_channels>1)
          and not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_channels>1) then
          if (filt_addr_latch_sel='1') then
            filt_addr_latch<=filt_addr;
          end if;
        else
          filt_addr_dly<=filt_addr;
        end if;
      end if;
    end if;
  end process;

  filt_addr_latch_sel<= base_sep_max_dly3 when ( FILTER_TYPE=c_decimating_half_band and
                                                 use_ipbuff_sep_deci_hb=1 and
                                                 ip_buff_type=1) else
                        base_sep_max_dly2 when ( FILTER_TYPE=c_decimating_half_band and
                                                 use_ipbuff_sep_deci_hb=1) else
                        write_data_inter_dly1 when ( FILTER_TYPE=c_decimating_half_band and
                                                     ip_buff_type=1) else
                        write_data_inter when ( FILTER_TYPE=c_decimating_half_band) else
                        new_data_int;

  gen_filt_src_norm: if (use_ip_buff=0) generate
    filt_src<=filter;

  end generate gen_filt_src_norm;

  gen_filt_src_buff: if (use_ip_buff=1) generate
    filt_src<=ip_buff_data_out(buffer_width-1 downto buffer_width-filt_sel_width);
  end generate gen_filt_src_buff;
end generate gen_filt_sel;
gen_packed: if (MEMORY_PACKED=1 and use_data_cascade=0) generate
  process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        coef_addr_int<=(others=>'0');
        data_addr_int<=(others=>'0');
        data_sym_addr_int<=std_logic_vector(to_unsigned(data_sym_cnt_offset,data_sym_addr_width));
      elsif (int_ce='1') then
        if (base_cin='1' and hb_single_mac=0) or (base_cin_ext='1' and hb_single_mac=1) then
          if (px_time>1
              or (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) ) then
            if (num_filters>1) then
              if not(FILTER_TYPE=c_polyphase_interpolating and num_channels>1)
                and not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_channels>1) then
                if (filt_addr_latch_sel='1') then
                  coef_addr_int<=std_logic_vector(unsigned(ext_bus(coef_cnt,coef_addr_width,1))+unsigned(ext_bus(filt_addr,coef_addr_width,1)));
                else
                  coef_addr_int<=std_logic_vector(unsigned(ext_bus(coef_cnt,coef_addr_width,1))+unsigned(ext_bus(filt_addr_latch,coef_addr_width,1)));
                end if;
              else
                coef_addr_int<=std_logic_vector(unsigned(ext_bus(coef_cnt,coef_addr_width,1))+unsigned(ext_bus(filt_addr,coef_addr_width,1)));
              end if;
            elsif (coef_offset>0) then
              coef_addr_int<=std_logic_vector(unsigned(ext_bus(coef_cnt,coef_addr_width,1))+to_unsigned(coef_offset,coef_addr_width));
            else
              coef_addr_int<=ext_bus(coef_cnt,coef_addr_width,1);
            end if;
          else
            if (num_filters>1) then
              if not(FILTER_TYPE=c_polyphase_interpolating and num_channels>1)
               and not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_channels>1) then
                if (new_data_int='1') then
                  coef_addr_int<=std_logic_vector(unsigned(ext_bus(phase_cnt,coef_addr_width,1))+unsigned(ext_bus(filt_addr,coef_addr_width,1)));
                else
                  coef_addr_int<=std_logic_vector(unsigned(ext_bus(phase_cnt,coef_addr_width,1))+unsigned(ext_bus(filt_addr_latch,coef_addr_width,1)));
                end if;
              else
                coef_addr_int<=std_logic_vector(unsigned(ext_bus(coef_cnt,coef_addr_width,1))+unsigned(ext_bus(filt_addr,coef_addr_width,1)));
              end if;
            elsif (coef_offset>0) then
              coef_addr_int<=std_logic_vector(unsigned(ext_bus(phase_cnt,coef_addr_width,1))+to_unsigned(coef_offset,coef_addr_width));
            else
              coef_addr_int<=ext_bus(phase_cnt,coef_addr_width,1);
            end if;
          end if;
        end if;
        if (base_cin='1') then
          if (px_time>1) then
            if (MEMORY_TYPE=1) then
              if (num_channel>1 or chan_phase_cnt_phase>1) then
                data_addr_int<=std_logic_vector(unsigned(ext_bus(data_cnt,data_addr_width,1))+unsigned(ext_bus(chan_phase_cnt,data_addr_width,1)));
              else
                data_addr_int<=ext_bus(data_cnt,data_addr_width,1);
              end if;
            else
              data_addr_int<=ext_bus(data_cnt,data_addr_width,1);
            end if;
          else
            data_addr_int<=ext_bus(chan_phase_cnt,data_addr_width,1);
          end if;

          if (px_time>1) then
            if (MEMORY_TYPE=1) then
              if (num_channel>1 or chan_phase_cnt_phase>1) then
                data_sym_addr_int<=std_logic_vector(unsigned(ext_bus(data_sym_cnt,data_sym_addr_width,1))+unsigned(ext_bus(chan_phase_cnt,data_sym_addr_width,1)));
              else
                data_sym_addr_int<=ext_bus(data_sym_cnt,data_sym_addr_width,1);
              end if;
            else
              data_sym_addr_int<=ext_bus(data_sym_cnt,data_sym_addr_width,1);
            end if;
          else
            data_sym_addr_int<=ext_bus(chan_phase_cnt,data_sym_addr_width,1);
          end if;
        end if;
      end if;
    end if;
  end process;
end generate gen_packed;
gen_non_packed: if (MEMORY_PACKED=0 or use_data_cascade=1) generate
  coef_addr_int<= ext_bus(filt_addr,c_addr_width,1) when use_data_cascade=1 else
                  '1'&filt_addr&phase_cnt when (coef_offset>1 and num_filters>1 and px_time=1) else
                  filt_addr&phase_cnt when (num_filters>1 and px_time=1) else
                  phase_cnt when px_time=1 else
                  '1'&filt_addr&coef_cnt when (coef_offset>1 and num_filters>1) else
                  filt_addr&coef_cnt when (num_filters>1) else
                  coef_cnt;
  data_addr_int<= (others=>'0') when px_time=1 else
                  data_cnt when MEMORY_TYPE=0 else
                  chan_phase_cnt&data_cnt when (num_channel>1 or chan_phase_cnt_phase>1) else
                  data_cnt;
  data_sym_addr_int<= (others=>'0') when px_time=1 else
                      data_sym_cnt when MEMORY_TYPE=0 else
                      '1'&chan_phase_cnt&data_sym_cnt when (sym_offset>1 and (num_channel>1 or chan_phase_cnt_phase>1)) else
                      chan_phase_cnt&data_sym_cnt when (num_channel>1 or chan_phase_cnt_phase>1) else
                      '1'&data_sym_cnt when (sym_offset>1) else
                      data_sym_cnt;
end generate gen_non_packed;
gen_last_data_reg: if (MEMORY_PACKED=1 and last_data_earily=0) generate
  process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        last_data_int<='0';
        first_phase<='0';
      elsif (int_ce='1') then
        if (FILTER_TYPE=c_interpolating_symmetry) then
          if (MEMORY_TYPE=1) then
            last_data_int<=base_max and not first_phase;
            if (phase_cnt_max='1') then
              first_phase<='0';
            elsif (base_max='1') then
              first_phase<='1';
            end if;
          else
            last_data_int<=phase_cnt_max;
          end if;
        else
          last_data_int<=base_max;
        end if;
      end if;
    end if;
  end process;
end generate gen_last_data_reg;
gen_last_data_map: if (MEMORY_PACKED=0 or last_data_earily=1) generate
  last_data_int<= last_deci when FILTER_TYPE=c_decimating else
                  base_max;
end generate gen_last_data_map;
gen_norm_output: if pq_inter_spec_case=0 generate
  data_addr<=ext_bus(data_addr_int,d_addr_width,1);
  data_sym_addr<=ext_bus(data_sym_addr_int,d_sym_addr_width,1);
  coef_addr<=ext_bus(coef_addr_int,c_addr_width,1);
  last_data<=last_data_int;
end generate gen_norm_output;
gen_delayed_output: if pq_inter_spec_case=1 generate
  signal data_addr_dly_out: std_logic_vector(data_addr_width-1 downto 0);
  signal coef_addr_dly_out: std_logic_vector(coef_addr_width-1 downto 0);
begin
  data_addr_dly: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => 1,
    C_WIDTH => data_addr_width
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => data_addr_int,
    Q       => data_addr_dly_out
  );
  data_addr<=ext_bus(data_addr_dly_out,d_addr_width,1);

  coef_addr_dly: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => 1,
    C_WIDTH => coef_addr_width
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => coef_addr_int,
    Q       => coef_addr_dly_out
  );
  coef_addr<=ext_bus(coef_addr_dly_out,c_addr_width,1);
  last_data_dly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => 1,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => reset,
    D     => last_data_int,
    Q     => last_data
  );
end generate gen_delayed_output;
gen_ip_buff: if use_ip_buff=1 generate
  incnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => ip_buff_width,
    C_CONST_0_VAL => 0,
    C_CONST_1_VAL => 1,
    C_CONST_2_VAL => (2**ip_buff_width)-(ip_buff_cnt-1)
  )
  port map(
    A_IN       => in_cnt,
    ADD_C0     => new_data,
    ADD_C1     => rfd_int,
    ADD_C2     => in_cnt_restart,
    C_IN       => '0',
    D          => in_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );
  incntmax: cmp_carrychain_dualval
  generic map(
    width => ip_buff_width,
    value1 => (ip_buff_cnt/2)-2,
    value2 => (ip_buff_cnt)-2,
    c_family => C_FAMILY
  )
  port map(
    cin   => '1',
    A     => in_cnt,
    sel   => in_cnt_page,
    cout  => in_cnt_max_cout,
    Res   => open,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
  incntmaxmux: MUXCY_D
  port map(
    O   => in_cnt_max_mux,
    LO  => in_cnt_max_mux_local,
    CI  => in_cnt_max_cout,
    DI  => in_cnt_max,
    S   => in_cnt_max_mux_sel
  );
  in_cnt_max_mux_sel<=new_data and rfd_int;
  intcntmaxreg: FDRE
  generic map(
    INIT => '0'
  )
  port map(
    C => clk,
    CE => int_ce,
    R => sclr,
    D => in_cnt_max_mux_local,
    Q => in_cnt_max
  );
  incntpagereg: process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        in_cnt_page<='0';
        in_cnt_restart<='0';
        in_cnt_max_src_dly<='0';
      elsif(int_ce='1') then

        in_cnt_max_src_dly<=in_cnt_max;
        if (new_data='1' and rfd_int='1' and in_cnt_max='1') then
          in_cnt_page<=not in_cnt_page;
        end if;
        in_cnt_restart<=in_cnt_restart_mux;
      end if;
    end if;
  end process;
  incntrestartmux: MUXCY_L
  port map(
    LO   => in_cnt_restart_mux,
    CI  => in_cnt_max_mux,
    DI  => '0',
    S   => in_cnt_page
  );
  incntmaxdly: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => UTILS_PKG_select_integer(1,ipbuff_sep_delay,use_ipbuff_sep=1)-1,
    C_WIDTH => 1
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => in_cnt_max_in,
    Q       => in_cnt_max_out
  );
  in_cnt_max_in(0)<=in_cnt_max;
  gen_incntmaxdly:process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        in_cnt_max_dly<='0';
      else
        if (ipbuff_sep_delay>2) then
          if (reset_blank='1') then
            in_cnt_max_dly<='0';
          else
            if (int_ce='1') then
              in_cnt_max_dly<=in_cnt_max_out(0);
            end if;
          end if;
        else
          if (int_ce='1') then
            in_cnt_max_dly<=in_cnt_max_out(0);
          end if;
        end if;
      end if;
    end if;
  end process;
  gen_norm_outcnt: if (use_ipbuff_sep=0 or use_ipbuff_sep_deci=1) generate

    gen_interpolation: if (FILTER_TYPE/=c_decimating_half_band and FILTER_TYPE/=c_decimating) generate
      outcnt: priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => ip_buff_width,
        C_CONST_0_VAL => 1,
        C_CONST_1_VAL => 0,
        C_CONST_2_VAL => (2**ip_buff_width)-(ip_buff_cnt-1)
      )
      port map(
        A_IN       => out_cnt,

        ADD_C0     => out_cnt_max_mux_sel,
        ADD_C1     => '0',
        ADD_C2     => out_cnt_max,

        C_IN       => '0',
        D          => out_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );
      outcntmax: cmp_carrychain
      generic map(
        width => ip_buff_width,
        const => 1,
        value => ip_buff_cnt-2,
        c_family => C_FAMILY
      )
      port map(
        cin   => '1',
        A     => out_cnt,
        B     => pad(ip_buff_width-1 downto 0),
        cout  => out_cnt_max_cout,
        Res   => open,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );
      outcntmaxmux: MUXCY_L
      port map(
        LO   => out_cnt_max_mux,
        CI  => out_cnt_max_cout,
        DI  => out_cnt_max,
        S   => out_cnt_max_mux_sel
      );

      out_cnt_max_mux_sel<= write_data_pq_inter when (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter) else
                            write_data_inter;
      outcntmaxreg: FDRE
      generic map(
        INIT => '0'
      )
      port map(
        C => clk,
        CE => int_ce,
        R => sclr,
        D => out_cnt_max_mux,
        Q => out_cnt_max
      );
    end generate gen_interpolation;
    gen_decihalfband: if (FILTER_TYPE=c_decimating_half_band) generate

      outcnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => ip_buff_width,
        C_CONST_0_VAL => 1,
        C_CONST_1_VAL => num_channels+1,
        C_CONST_2_VAL => (2**ip_buff_width)-(ip_buff_cnt-num_channels-1)
      )
      port map(
        A_IN       => out_cnt,
        ADD_C0     => write_data_inter,
        ADD_C1     => out_cnt_max,
        ADD_C2     => out_cnt_page,
        C_IN       => '0',
        D          => out_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

      gen_cmp: if (num_channels>1) generate
        outcntmax: cmp_carrychain_dualval
        generic map(
          width => ip_buff_width,
          value1 => (num_channels)-2 ,
          value2 => (ip_buff_cnt-num_channels)-2,
          c_family => C_FAMILY
        )
        port map(
          cin   => '1',
          A     => out_cnt,
          sel   => out_cnt_page,
          cout  => out_cnt_max_cout,
          Res   => open,
          sclr  => reset,
          ce    => int_ce,
          clk   => clk
        );
        outcntmaxmux: MUXCY_L
        port map(
          LO   => out_cnt_max_mux,

          CI  => out_cnt_max_cout,
          DI  => out_cnt_max,
          S   => out_cnt_max_mux_sel
        );

        out_cnt_max_mux_sel<=write_data_inter;

        outcntmaxreg: FDRE
        generic map(
          INIT => '0'
        )
        port map(
          C => clk,
          CE => int_ce,
          R => sclr,
          D => out_cnt_max_mux,
          Q => out_cnt_max
        );
      end generate gen_cmp;
      gen_cmp_const: if (num_channels=1) generate

        out_cnt_max<='1';
      end generate gen_cmp_const;
      process(clk)
      begin
        if (rising_edge(clk)) then
          if (sclr='1') then
            out_cnt_page<='0';
          elsif (int_ce='1') then

            if (out_cnt_max='1' and write_data_inter='1') then
              out_cnt_page<=not out_cnt_page;
            end if;
          end if;
        end if;
      end process;
    end generate gen_decihalfband;
    gen_decimating: if (FILTER_TYPE=c_decimating) generate

      outcnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => ip_buff_width,
        C_CONST_0_VAL => -1*num_channels,
        C_CONST_1_VAL => (num_channels*(num_phase-1))+1,
        C_CONST_2_VAL =>  (num_channels*(num_phase-1))-(num_channels-1)  ,
        C_INIT_SCLR_VAL =>(num_channels*(num_phase-1))
      )
      port map(
        A_IN       => out_cnt,
        ADD_C0     => write_deci,
        ADD_C1     => deci_ph_zero_end,
        ADD_C2     => out_cnt_max,
        C_IN       => '0',
        D          => out_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

      outcntmax: cmp_carrychain
      generic map(
        width => ip_buff_width,
        const => 1,
        value => (num_channels*2)-1,
        c_family => C_FAMILY
      )
      port map(
        cin   => write_deci,
        A     => out_cnt,
        B     => pad(ip_buff_width-1 downto 0),
        cout  => open,
        Res   => out_cnt_max,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );

      process(clk)
      begin
        if (rising_edge(clk)) then

          if (sclr='1') then
            out_cnt_page<='0';
          elsif (int_ce='1') then

            if (out_cnt_max='1') then
              out_cnt_page<=not out_cnt_page;
            end if;
          end if;
        end if;
      end process;
    end generate gen_decimating;
  end generate gen_norm_outcnt;

  gen_seperate_outcnt: if (use_ipbuff_sep=1 and use_ipbuff_sep_deci=0) generate
    gen_interpolation: if (filter_type/=c_decimating_half_band) generate
      gen_outcnt_norm: if (num_filters=1) generate
        outcnt: combined_priority_counter
        generic map(
          C_HAS_C_IN    => 0,
          C_WIDTH       => ip_buff_width,
          C_CONST_0_VAL => 1,
          C_CONST_1_VAL => 1,
          C_CONST_2_VAL => (2**ip_buff_width)-(ip_buff_cnt-1)
        )
        port map(
          A_IN       => out_cnt,
          ADD_C0     => base_sep_max,
          ADD_C1     => out_cnt_max,
          ADD_C2     => out_cnt_page,

          C_IN       => '0',
          D          => out_cnt,
          CE        => int_ce,
          SCLR      => reset,
          CLK       => clk
        );
      end generate gen_outcnt_norm;

      gen_outcnt_multifiltsets: if (num_filters>1) generate
        outcnt: combined_priority_counter
        generic map(
          C_HAS_C_IN    => 1,
          C_WIDTH       => ip_buff_width,
          C_CONST_0_VAL => (2**ip_buff_width)-((ip_buff_cnt/2)-1)-1,
          C_CONST_1_VAL => 0,
          C_CONST_2_VAL => (2**ip_buff_width)-(ip_buff_cnt-1)-1
        )
        port map(
          A_IN       => out_cnt,

          ADD_C0     => out_cnt_max,
          ADD_C1     => out_phase_cnt_max,
          ADD_C2     => out_cnt_page,

          C_IN       => base_sep_max,
          D          => out_cnt,

          CE        => int_ce,
          SCLR      => reset,
          CLK       => clk
        );

      end generate gen_outcnt_multifiltsets;
    end generate gen_interpolation;
    gen_decihalfband: if (filter_type=c_decimating_half_band) generate

      outcnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 1,
        C_WIDTH       => ip_buff_width,
        C_CONST_0_VAL => 1,
        C_CONST_1_VAL => num_channels+1,
        C_CONST_2_VAL => (2**ip_buff_width)-(ip_buff_cnt-num_channels-1)
      )
      port map(
        A_IN       => out_cnt,
        ADD_C0     => base_sep_max,
        ADD_C1     => out_cnt_max,
        ADD_C2     => out_cnt_page,
        C_IN       => '0',
        D          => out_cnt,
        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );
    end generate gen_decihalfband;
    outcntmax: cmp_carrychain_dualval
    generic map(
      width => ip_buff_width,
      value1 => UTILS_PKG_select_integer( (ip_buff_cnt/2)-1,
                                          num_channels-1,
                                          filter_type=c_decimating_half_band)
                                -UTILS_PKG_select_integer(0,1,px_time=1),
      value2 => UTILS_PKG_select_integer( (ip_buff_cnt)-1,
                                          ip_buff_cnt-num_channels-1,
                                          filter_type=c_decimating_half_band)
                                 -UTILS_PKG_select_integer(0,1,px_time=1),
      c_family => C_FAMILY
    )
    port map(
      cin   => base_sep_max_cout,
      A     => out_cnt,
      sel   => out_cnt_page,
      cout  => out_cnt_max_cout,
      Res   => out_cnt_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
    process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr='1') then
          base_sep_cin<='0';
          out_cnt_page<='0';
        elsif (int_ce='1') then
          if (num_filters=1 or filter_type=c_decimating_half_band) then
            if (new_data='1' and rfd_int='1' and in_cnt_max='1') then
              base_sep_cin<='1';
            elsif (out_cnt_max='1') then
              base_sep_cin<='0';
            end if;
          else
            if (new_data='1' and rfd_int='1' and in_cnt_max='1') then
              base_sep_cin<='1';
            elsif (out_phase_cnt_max='1') then
              base_sep_cin<='0';
            end if;
          end if;
          if (num_filters=1 or filter_type=c_decimating_half_band) then
            if (out_cnt_max='1') then
              out_cnt_page<=not out_cnt_page;
            end if;
          else
            if (out_phase_cnt_max='1') then
              out_cnt_page<=not out_cnt_page;
            end if;
          end if;
        end if;
      end if;
    end process;
    gen_norm_basesep: if (px_time>1) generate
      basecntsep: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => base_width,
        C_CONST_0_VAL => 1,
        C_CONST_1_VAL => (2**base_width)-( px_time-1 ),
        C_CONST_2_VAL => 0
      )
      port map(
        A_IN       => base_sep_cnt,

        ADD_C0     => base_sep_cin,
        ADD_C1     => base_sep_max,
        ADD_C2     => '0',

        C_IN       => '0',
        D          => base_sep_cnt,

        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );
      basesepmax: cmp_carrychain
      generic map(
        width => base_width,
        const => 1,
        value => px_time-2,
        c_family => C_FAMILY
      )
      port map(
        cin   => base_sep_cin,
        A     => base_sep_cnt,
        B     => pad(base_width-1 downto 0),
        cout  => base_sep_max_cout,
        Res   => base_sep_max,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );
    end generate gen_norm_basesep;

    gen_para_basesep: if (px_time=1) generate
      base_sep_max_cout<=base_sep_cin;
      base_sep_max<=base_sep_cin;
    end generate gen_para_basesep;
    gen_out_phase_cnt: if (num_filters>1 and filter_type=c_polyphase_interpolating)
                          or (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_filters>1) generate
      outphasecnt: combined_priority_counter
      generic map(
        C_HAS_C_IN    => 0,
        C_WIDTH       => max(1,log2_ext(num_phases)),
        C_CONST_0_VAL => 0,
        C_CONST_1_VAL => 1,
        C_CONST_2_VAL => (2**max(1,log2_ext(num_phases)))-(num_phases-1)
      )
      port map(
        A_IN       => out_phase_cnt,

        ADD_C0     => base_sep_max,
        ADD_C1     => out_cnt_max,
        ADD_C2     => out_phase_cnt_max,
        C_IN       => '0',
        D          => out_phase_cnt,

        CE        => int_ce,
        SCLR      => reset,
        CLK       => clk
      );

      outphasecntmax: cmp_carrychain
      generic map(
        width => max(1,log2_ext(num_phases)),
        const => 1,
        value => num_phases-1,
        c_family => C_FAMILY
      )
      port map(
        cin   => out_cnt_max_cout,
        A     => out_phase_cnt,
        B     => pad(max(1,log2_ext(num_phases))-1 downto 0),
        cout  => open,
        Res   => out_phase_cnt_max,
        sclr  => reset,
        ce    => int_ce,
        clk   => clk
      );
    end generate gen_out_phase_cnt;
    gen_out_phase_cnt_map: if not(num_filters>1 and filter_type=c_polyphase_interpolating)
                              and not(FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter and num_filters>1) generate
      out_phase_cnt_max<=out_cnt_max;
    end generate gen_out_phase_cnt_map;
  end generate gen_seperate_outcnt;

  gen_deci_halfband_phase_toggle: if (FILTER_TYPE=c_decimating_half_band) generate
    offsetadd: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => ip_buff_width,
      C_CONST_0_VAL => num_channels,
      C_CONST_1_VAL => 0,
      C_CONST_2_VAL => 0
    )
    port map(
      A_IN       => out_cnt,
      ADD_C0     => '1',
      ADD_C1     => out_cnt_phase_switch,
      ADD_C2     => '0',
      C_IN       => '0',
      D          => out_cnt_offset,
      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );
    gen_switch_no_sep: if (use_ipbuff_sep=0) generate
      out_cnt_phase_switch<= write_data_inter;
    end generate gen_switch_no_sep;
    gen_switch_sep: if (use_ipbuff_sep=1) generate
      process(clk)
      begin
        if (rising_edge(clk)) then
          if (sclr='1') then
            out_cnt_phase_switch<='0';
            out_cnt_phase_switch_dly<='0';
          elsif (int_ce='1') then
            out_cnt_phase_switch_dly<=(new_data and rfd_int and in_cnt_max) or (base_sep_max and not out_cnt_max );
            out_cnt_phase_switch<=out_cnt_phase_switch_dly;
          end if;
        end if;
      end process;
    end generate gen_switch_sep;
  end generate gen_deci_halfband_phase_toggle;

  gen_decimating_page_toggle: if (FILTER_TYPE=c_decimating) generate

    offsetadd: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => ip_buff_width,
      C_CONST_0_VAL => num_phases*num_channels,
      C_CONST_1_VAL => 0,
      C_CONST_2_VAL => 0
    )
    port map(
      A_IN       => out_cnt,
      ADD_C0     => out_cnt_page,
      ADD_C1     => '0',
      ADD_C2     => '0',
      C_IN       => '0',
      D          => out_cnt_offset,
      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );
  end generate gen_decimating_page_toggle;
  gen_buff_ip_data: if num_filters=1 generate
    buffer_ip<=data_in;

  end generate gen_buff_ip_data;

  gen_buff_ip_data_filt: if num_filters>1 generate

    buffer_ip<=filter&data_in;

  end generate gen_buff_ip_data_filt;
end generate gen_ip_buff;
gen_ip_reg: if use_ip_buff=0 generate
  process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        ip_reg<=(others=>'0');
        ip_reg_filt<=(others=>'0');
      elsif (int_ce='1') then
        if (new_data='1' and rfd_int='1') then
          ip_reg<=data_in;
          ip_reg_filt<=filter;
        else
          ip_reg<=ip_reg;
          ip_reg_filt<=ip_reg_filt;
        end if;
      end if;
    end if;
  end process;
end generate gen_ip_reg;
gen_hb_deci_bypass: if FILTER_TYPE=c_decimating_half_band generate
  gen_sing_filt: if (num_filters=1) generate
    data_out_hb_bypass<=ip_buff_data_out(data_width-1 downto 0);
  end generate gen_sing_filt;
  gen_filt: if (num_filters>1) generate

    hbdata_dly: shift_ram_fixed
    generic map (
      C_FAMILY=> C_FAMILY,
      C_DEPTH => UTILS_PKG_select_integer( 0,1,
                    use_ipbuff_sep_deci_hb=1),
      C_WIDTH => data_width
    )
    port map (
      CLK     => clk,
      CE      => int_ce,
      D       => ip_buff_data_out(data_width-1 downto 0),
      Q       => data_out_hb_bypass
    );

    hbfilt_dly: shift_ram_fixed
    generic map (
      C_FAMILY=> C_FAMILY,
      C_DEPTH => UTILS_PKG_select_integer( 0,2,
                    use_ipbuff_sep_deci_hb=1),
      C_WIDTH => filt_sel_width
    )
    port map (
      CLK     => clk,
      CE      => int_ce,
      D       => ip_buff_data_out(buffer_width-1 downto data_width),
      Q       => filt_out_hb_bypass
    );
  end generate gen_filt;
end generate gen_hb_deci_bypass;
gen_bypass_buff_filt_sel: if (num_filters>1 and (FILTER_TYPE=c_interpolating_half_band
                                                or FILTER_TYPE=c_halfband_transform)) generate
  bypass_phase_store: shift_ram_fixed
  generic map (
    C_FAMILY=> C_FAMILY,
    C_DEPTH => data_delay,
    C_WIDTH => filt_sel_width
  )
  port map (
    CLK     => clk,
    CE      => int_ce,
    D       => ip_reg_filt,
    Q       => filt_out_hb_bypass
  );
end generate gen_bypass_buff_filt_sel;
gen_cntrl_sig: process(clk)
begin
  if (rising_edge(clk)) then
    if (sclr='1') then
      accumulate_int<='0';
      load_accum_int<='0';
      load_accum_phase0_int<='0';
      load_accum_gen<='0';
      load_accum_gen2<='0';
      latch_accum_int<='0';
      new_data_int_dly<='0';
      if (num_phases rem 2 > 0) then
        addsub_toggle<='0';
      else
        addsub_toggle<='1';
      end if;
      start_toggle<='0';
      base_max_dly<='0';

      latch_accum_pq_deci_int<='0';
    elsif (int_ce='1') then
      base_max_dly<=base_max;
      new_data_int_dly<=new_data_int;
      accumulate_int<=base_cin;
      if (FILTER_TYPE=c_polyphase_pq and pq_type=pq_inter) then

        if (use_ip_buff=1) then
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            load_accum_gen<=(base_max and not chan_phase_max) or (base_max and nd_received);
            load_accum_int<=(new_data_int and in_cnt_max_dly and (base_max_dly or not accumulate_int) ) or load_accum_gen;
          else
            load_accum_gen<=(base_max and not chan_phase_max) or (base_max and nd_received);
            load_accum_gen2<=(new_data_int and in_cnt_max_dly and (base_max_dly or not accumulate_int));
            load_accum_int<=load_accum_gen2 or load_accum_gen;
          end if;
        else
            if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            load_accum_gen<=(base_max and not chan_phase_max) or (base_max and nd_received);
            load_accum_int<=(new_data_int and (base_max_dly or not accumulate_int)) or load_accum_gen;
          else
            load_accum_gen<=(base_max and not chan_phase_max) or (base_max and nd_received);
            load_accum_gen2<=new_data_int and (base_max_dly or not accumulate_int);
            load_accum_int<=load_accum_gen2 or load_accum_gen;
          end if;
        end if;
      elsif (FILTER_TYPE=c_polyphase_interpolating or FILTER_TYPE=c_interpolating_symmetry) then
        if (use_ip_buff=1) then
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            load_accum_gen<=(base_max and not chan_phase_max);
            load_accum_int<=(new_data_int and in_cnt_max_dly) or load_accum_gen;
          else
            load_accum_gen<=(base_max and not chan_phase_max);
            load_accum_gen2<=(new_data_int and in_cnt_max_dly);
            load_accum_int<=load_accum_gen2 or load_accum_gen;
          end if;
        else
            if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            load_accum_gen<=(base_max and not chan_phase_max);
            load_accum_int<=new_data_int or load_accum_gen;
          else
            load_accum_gen<=(base_max and not chan_phase_max);
            load_accum_gen2<=new_data_int;
            load_accum_int<=load_accum_gen2 or load_accum_gen;
          end if;
        end if;
      elsif (FILTER_TYPE=c_decimating_half_band) then

        if (hb_single_mac=0) then
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            load_accum_gen<=(base_max and not chan_phase_max);
            load_accum_int<=(new_data_int and in_cnt_max_dly) or load_accum_gen;
          else
            load_accum_gen<=(base_max and not chan_phase_max);
            load_accum_gen2<=(new_data_int and in_cnt_max_dly);
            load_accum_int<=load_accum_gen2 or load_accum_gen;
          end if;
        else
          if not(use_delay_addr=1 or use_ipbuff_sep=1) then
            load_accum_gen<=(base_max_dly and not chan_phase_max);
            load_accum_int<=(new_data_int and in_cnt_max_dly) or load_accum_gen;
          else
            load_accum_gen<=(base_max_dly and not chan_phase_max);
            load_accum_gen2<=(new_data_int and in_cnt_max_dly);
            load_accum_int<=load_accum_gen2 or load_accum_gen;
          end if;
        end if;
      elsif (FILTER_TYPE=c_decimating) then
        if not(use_delay_addr=1 or use_ipbuff_sep=1) then
          load_accum_gen<=(base_max and not chan_phase_max);
          load_accum_int<=(new_data_int and in_cnt_max_dly) or load_accum_gen;
        else
          load_accum_gen<=(base_max and not chan_phase_max);
          load_accum_gen2<=(new_data_int and in_cnt_max_dly);
          load_accum_int<=load_accum_gen2 or load_accum_gen;
        end if;

      elsif (FILTER_TYPE=c_polyphase_pq and pq_type=pq_deci) then
        if not(use_delay_addr=1 or use_ipbuff_sep=1) then
          load_accum_gen<=(base_max and not inter_cnt_max);
          load_accum_int<=(new_data_int) or load_accum_gen;
        else
          load_accum_gen<=(base_max and not inter_cnt_max);
          load_accum_gen2<=(new_data_int);
          load_accum_int<=load_accum_gen2 or load_accum_gen;
        end if;
      else
        if not(use_delay_addr=1 or use_ipbuff_sep=1) then
          load_accum_int<=(new_data_int);
        else
          load_accum_int<=new_data_int_dly;
        end if;
      end if;
      if (FILTER_TYPE=c_polyphase_decimating)  then
        if not(use_delay_addr=1 or use_ipbuff_sep=1) then
          load_accum_phase0_int<= (new_data_int and chan_phase_first_phase);
        else
          load_accum_phase0_int<= (new_data_int_dly and chan_phase_first_phase);
        end if;
      end if;

      latch_accum_pq_deci_int<=phase_cnt_max and inter_result;

      if (FILTER_TYPE=c_polyphase_decimating) then
        latch_accum_int<=base_max and phase_cnt_max;
      else
        latch_accum_int<=base_max;
      end if;

      if (hb_single_mac=1) then
        accumulate_int<=base_cin_ext;
        latch_accum_int<=base_max_dly;
      end if;

      if (FILTER_TYPE=c_interpolating_symmetry) then
        if (num_phases rem 2 > 0) then
          if(phase_cnt_max='1') then
            addsub_toggle<='0';
          elsif (base_max='1')then
            addsub_toggle<=not addsub_toggle;
          end if;
        else
          if(phase_cnt_max='1') then
            addsub_toggle<='1';
          elsif (base_max='1')then
            addsub_toggle<=not addsub_toggle;
          end if;
        end if;
      end if;
    end if;
  end if;
end process;
gen_phasecnt_decipoly: if (FILTER_TYPE=c_polyphase_decimating) generate
  phasecnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => max(1,log2_ext(num_phase)),
    C_CONST_0_VAL => 0,
    C_CONST_1_VAL => 1,
    C_CONST_2_VAL => (2**log2_ext(num_phase))-(num_phase-1)
  )
  port map(
    A_IN       => phase_cnt,

    ADD_C0     => base_max,
    ADD_C1     => chan_max,
    ADD_C2     => phase_cnt_max,
    C_IN       => '0',
    D          => phase_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );

  phasecntmax: cmp_carrychain
  generic map(
    width => max(1,log2_ext(num_phase)),
    const => 1,
    value => num_phase-2,
    c_family => C_FAMILY
  )
  port map(
    cin   => '1',
    A     => phase_cnt,
    B     => pad(max(1,log2_ext(num_phase))-1 downto 0),
    cout  => phase_cnt_max_cout,
    Res   => open,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );

  phasecntmaxmux: MUXCY_L
  port map(
    LO   => phase_cnt_max_mux,
    CI  => phase_cnt_max_cout,
    DI  => phase_cnt_max,
    S   => phase_cnt_max_sel
  );

  phase_cnt_max_sel<=base_max and chan_max;

  phasemaxreg: FDRE
  generic map(
    INIT => '0'
  )
  port map(
    C => clk,
    CE => int_ce,
    R => sclr,
    D => phase_cnt_max_mux,
    Q => phase_cnt_max
  );
  gen_chan_phase_max_para: if(px_time=1) generate
    chan_phase_max<=phase_cnt_max;
  end generate gen_chan_phase_max_para;
end generate gen_phasecnt_decipoly;
gen_phasecnt_inter: if (FILTER_TYPE=c_polyphase_interpolating) generate
  phasecnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => max(1,log2_ext(num_phase)),
    C_CONST_0_VAL => 0,
    C_CONST_1_VAL => 1,
    C_CONST_2_VAL => (2**log2_ext(num_phase))-(num_phase-1)
  )
  port map(
    A_IN       => phase_cnt,
    ADD_C0     => base_max,
    ADD_C1     => chan_max,
    ADD_C2     => chan_phase_max,

    C_IN       => '0',
    D          => phase_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );

  phasecntmax: cmp_carrychain
  generic map(
    width => max(1,log2_ext(num_phase)),
    const => 1,
    value => num_phase-1
            -UTILS_PKG_select_integer(0,1,px_time=1 and num_channel=1),
    c_family => C_FAMILY
  )
  port map(
    cin   => chan_only_max_cout,
    A     => phase_cnt,
    B     => pad(max(1,log2_ext(num_phase))-1 downto 0),
    cout  => chan_phase_max_cout,
    Res   => chan_phase_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
end generate gen_phasecnt_inter;
gen_phasecnt_pq: if (FILTER_TYPE=c_polyphase_pq) generate
  constant pq_phases  : integer:=UTILS_PKG_select_integer(pq_p_val,pq_q_val, pq_type=pq_deci);
  constant pq_step    : integer:=UTILS_PKG_select_integer(pq_q_val,pq_p_val, pq_type=pq_deci);
  constant numcntmax: integer:= pq_phases/pq_step + UTILS_PKG_select_integer(0,1,pq_phases rem pq_step > 0 );
  signal num_phase_cnt: std_logic_vector(max(1,log2_ext(numcntmax))-1 downto 0);
  signal num_phase_cnt_val_sel:std_logic:='0';

  signal count_len_toggle: std_logic:='0';
begin

  gen_inter: if (pq_type=pq_inter) generate

    phasecnt: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => max(1,log2_ext(pq_phases)),
      C_CONST_0_VAL => 0,
      C_CONST_1_VAL => pq_step,
      C_CONST_2_VAL => (2**log2_ext(pq_phases))-(pq_phases-pq_step)
    )
    port map(
      A_IN       => phase_cnt,

      ADD_C0     => '1',
      ADD_C1     => chan_only_max,
      ADD_C2     => chan_phase_max,

      C_IN       => '0',
      D          => phase_cnt,
      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );

    numphasecnt:chan_counter
    generic map(
      C_WIDTH=>max(1,log2_ext(numcntmax)),
      C_FLEXIPORT_FUNC=>1
    )
    port map(
      A_IN => num_phase_cnt,

      ENABLE     => chan_only_max,
      ENABLE_RST => chan_phase_max,
      FLEXIPORT  => '1',

      D => num_phase_cnt,

      CE => int_ce,
      SCLR => reset,
      CLK => clk
    );

    chanphasemax: cmp_carrychain_dualval
    generic map(
      width => max(1,log2_ext(numcntmax)),
      value1 => numcntmax-1,
      value2 => numcntmax-2,
      c_family => C_FAMILY
    )
    port map(
      cin   => chan_only_max_cout,
      A     => num_phase_cnt,
      sel   => num_phase_cnt_val_sel,
      cout  => chan_phase_max_cout,
      Res   => chan_phase_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );

    gen_num_phase_cnt_val_sel:process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset='1') then
          num_phase_cnt_val_sel<='0';
          count_len_toggle<='0';
                elsif (int_ce='1') then

                  if (phase_cnt > std_logic_vector(
                              to_unsigned(
                                ((pq_phases-1)-( ((pq_phases/pq_step)+1)*pq_step)) mod pq_phases,
                                max(1,log2_ext(pq_phases)) ))) then
            count_len_toggle<='1';
          else
            count_len_toggle<='0';
          end if;

          if (chan_phase_max='1') then
            if (count_len_toggle='1') then
              num_phase_cnt_val_sel<='1';
            else
              num_phase_cnt_val_sel<='0';
            end if;
          end if;

                end if;
        end if;

    end process;
    phaseendseq: cmp_carrychain
    generic map(
      width => max(1,log2_ext(pq_phases)),
      const => 1,
      value => (pq_phases-pq_step),
      c_family => C_FAMILY
    )
    port map(
      cin   => '1',
      A     => phase_cnt,
      B     => pad(max(1,log2_ext(pq_phases))-1 downto 0),
      cout  => open,
      Res   => phase_end_seq,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
  end generate gen_inter;
  gen_deci: if (pq_type=pq_deci) generate
    phasecnt: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => max(1,log2_ext(pq_phases)),
      C_CONST_0_VAL => 0,
      C_CONST_1_VAL => pq_step,
      C_CONST_2_VAL => (2**log2_ext(pq_phases))-(pq_phases-pq_step)
    )
    port map(
      A_IN       => phase_cnt,

      ADD_C0     => '1',
      ADD_C1     => chan_only_max,
      ADD_C2     => phase_cnt_max,
      C_IN       => '0',
      D          => phase_cnt,

      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );
    chanphasemax: cmp_carrychain
    generic map(
      width => max(1,log2_ext(pq_phases)),
      const => 1,
      value => (pq_phases-pq_step),
      c_family => "virtex4"
    )
    port map(
      cin   => chan_only_max_cout,
      A     => phase_cnt,
      B     => pad(max(1,log2_ext(pq_phases))-1 downto 0),
      cout  => chan_phase_max_cout,
      Res   => chan_phase_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );

    gen_phase_cnt_max:process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset='1') then
          phase_cnt_max<='0';
                elsif( int_ce='1') then
          if ( phase_cnt > std_logic_vector( to_unsigned( pq_phases-pq_step-1,max(1,log2_ext(pq_phases))))) then
            phase_cnt_max<='1';
          else
            phase_cnt_max<='0';
          end if;
                end if;
        end if;
    end process;

    intercntresult: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => max(1,log2_ext(pq_p_val)),
      C_CONST_0_VAL => 0,
      C_CONST_1_VAL => -1,
      C_CONST_2_VAL => pq_p_val-1,
      C_INIT_SCLR_VAL => pq_p_val-1
    )
    port map(
      A_IN       => inter_cnt_result,
      ADD_C0     => chan_only_max,
      ADD_C1     => phase_cnt_max,
      ADD_C2     => inter_cnt_result_max,
      C_IN       => '0',
      D          => inter_cnt_result,

      CE        => int_ce,
      SCLR      => reset,
      CLK       => clk
    );
    intercntphase0:process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset='1') then
          inter_cnt_phase0<=(others=>'0');
                elsif (int_ce='1') then
                  if (chan_only_max='1' and phase_cnt_max='1') then
            inter_cnt_phase0<=inter_cnt_result;
          end if;
                end if;
        end if;

    end process;
    intercntresultmax: cmp_carrychain
    generic map(
      width => max(1,log2_ext(pq_p_val)),
      const => 1,
      value => 0,
      c_family => C_FAMILY
    )
    port map(
      cin   => '1',
      A     => inter_cnt_result,
      B     => pad(max(1,log2_ext(pq_p_val))-1 downto 0),
      cout  => open,
      Res   => inter_cnt_result_max,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );
    interresult: cmp_carrychain
    generic map(
      width => max(1,log2_ext(pq_p_val)),
      const => 0,
      value => 0,
      c_family => C_FAMILY
    )
    port map(
      cin   => '1',
      A     => inter_cnt_result,
      B     => inter_cnt,
      cout  => inter_result_cout,
      Res   => open,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );

    interresultgen: MUXCY_L
    port map(
      LO   => inter_result_gen,

      CI  => inter_result_cout,
      DI  => '0',
      S   => inter_result_sel
    );

    inter_result_sel<=base_max;
    interphase0: cmp_carrychain
    generic map(
      width => max(1,log2_ext(pq_p_val)),
      const => 0,
      value => 0,
      c_family => C_FAMILY
    )
    port map(
      cin   => '1',
      A     => inter_cnt_phase0,
      B     => inter_cnt,
      cout  => inter_phase0_cout,
      Res   => open,
      sclr  => reset,
      ce    => int_ce,
      clk   => clk
    );

    gen_sing_chan: if (num_channels=1) generate
      interphase0gen: MUXCY_L
      port map(
        LO   => inter_phase0_gen,

        CI  => inter_phase0_cout,
        DI  => '0',
        S   => inter_phase0_sel
      );

      inter_phase0_sel<= (new_data_int and chan_phase_first_phase) or (base_max_dly and chan_phase_first_phase and not chan_phase_max_dly);
    end generate gen_sing_chan;

    gen_multi_chan: if (num_channels>1) generate

      interphase0gen: MUXCY_L
      port map(
        LO   => inter_phase0_gen,
        CI  => inter_phase0_gen2,
        DI  => '0',
        S   => inter_phase0_sel2
      );
      inter_phase0_sel2<= (base_max_dly and chan_phase_first_phase and not chan_phase_max_dly) or inter_phase0_first_block;

      interphase0gen2: MUXCY
      port map(
        O   => inter_phase0_gen2,
        CI  => inter_phase0_cout,
        DI  => '0',
        S   => inter_phase0_sel
      );
      inter_phase0_sel<= (new_data_int and chan_phase_first_phase) or not inter_phase0_first_block;
    end generate gen_multi_chan;
    load_latch_gen: process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset='1') then
          inter_phase0<='0';
          inter_result<='0';
          chan_phase_max_dly<='0';
          inter_phase0_first_block<='1';
                elsif (int_ce='1') then
          chan_phase_max_dly<=chan_phase_max;
          if (chan_only_max='1' and phase_cnt_max='1' and inter_cnt_result_max='1') then
            inter_phase0_first_block<='1';
          elsif (chan_only_max='1' and phase_cnt_max='1') then
            inter_phase0_first_block<='0';
          end if;
          inter_phase0<=inter_phase0_gen;
          inter_result<=inter_result_gen;
                end if;
        end if;
    end process;
  end generate gen_deci;
end generate gen_phasecnt_pq;
gen_phasecnt_inter_sym: if (FILTER_TYPE=c_interpolating_symmetry) generate

  phasecnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => max(1,log2_ext(num_phase)),
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**log2_ext(num_phase))-(num_phase-1),
    C_CONST_2_VAL => 0
  )
  port map(
    A_IN       => phase_cnt,

    ADD_C0     => base_max,
    ADD_C1     => phase_cnt_max,
    ADD_C2     => '0',

    C_IN       => '0',
    D          => phase_cnt,
    CE        => int_ce,
    SCLR      => reset,
    CLK       => clk
  );

  phasecntmax: cmp_carrychain
  generic map(
    width => max(1,log2_ext(num_phase)),
    const => 1,
    value => num_phase-1-UTILS_PKG_select_integer(0,1,px_time=1),
    c_family => C_FAMILY
  )
  port map(
    cin   => base_max_cout,
    A     => phase_cnt,
    B     => pad(max(1,log2_ext(num_phase))-1 downto 0),
    cout  => phase_cnt_max_cout,
    Res   => phase_cnt_max,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
end generate gen_phasecnt_inter_sym;
gen_decimation_write_pulses: if (FILTER_TYPE=c_decimating) generate
  deciphzeroend: cmp_carrychain
  generic map(
    width => base_width,
    const => 1,
    value => (num_phases-2)-1-ip_buff_type+UTILS_PKG_select_integer(0,(px_time),(num_phases-2)-1-ip_buff_type<0),
    c_family => C_FAMILY
  )
  port map(
    cin   => deci_ph_zero_end_en,
    A     => base_cnt,
    B     => pad(base_width-1 downto 0),
    cout  => deci_ph_zero_end_cout,
    Res   => open,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );

  deciphzeroendover: MUXCY_L
  port map(
    LO   => deci_ph_zero_end_mux,
    CI  => deci_ph_zero_end_cout,
    DI  => '1',
    S   => deci_ph_zero_end_over
  );
  deci_ph_zero_end_over<= not( new_data_int and in_cnt_max_dly) when (ip_buff_type=0 and num_phases=2) else
                          not( new_data_int_gen and in_cnt_max_src_dly) when (ip_buff_type=1 and num_phases=2) else
                          not( new_data_int and in_cnt_max_dly) when (ip_buff_type=1 and num_phases=3) else
                          '1';

  deci_ph_zero_end_en<= deci_ph_zero_start_en when ((num_phases=2 or (num_phases=3 and ip_buff_type=1) ) and num_channels>1) else
                        base_cin;
  deciphzeroendreg: FDRE
  generic map(
    INIT => '0'
  )
  port map(
    C => clk,
    CE => int_ce,
    R => sclr,
    D => deci_ph_zero_end_mux,
    Q => deci_ph_zero_end
  );
  deciphzerostart: cmp_carrychain
  generic map(
    width => base_width,
    const => 1,
    value => (px_time-1)-2-ip_buff_type,
    c_family => C_FAMILY
  )
  port map(
    cin   => deci_ph_zero_start_en,
    A     => base_cnt,
    B     => pad(base_width-1 downto 0),
    cout  => open,
    Res   => deci_ph_zero_start,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
  deci_ph_zero_start_en<=base_cin and not chan_max;
  deciphlaststart: cmp_carrychain
  generic map(
    width => base_width,
    const => 1,
    value => (px_time-1)-num_phases-1,
    c_family => C_FAMILY
  )
  port map(
    cin   => base_cin,
    A     => base_cnt,
    B     => pad(base_width-1 downto 0),
    cout  => open,
    Res   => deci_ph_last_start,
    sclr  => reset,
    ce    => int_ce,
    clk   => clk
  );
  process(clk)
  begin
    if (rising_edge(clk)) then
      if (sclr='1') then
        write_deci<='0';
        last_deci<='0';
      elsif (int_ce='1') then
        if (ip_rate>1) then
          if (num_channels=1) then
            if (new_data='1' and rfd_int='1' and in_cnt_max='1') then
              write_deci<='1';
            elsif (deci_ph_zero_end='1') then
              write_deci<='0';
            end if;
          else
            if (new_data='1' and rfd_int='1' and in_cnt_max='1') or (deci_ph_zero_start='1') then
              write_deci<='1';
            elsif (deci_ph_zero_end='1') then
              write_deci<='0';
            end if;
          end if;
        else
            if (new_data='1' and rfd_int='1' and in_cnt_max='1') then
              write_deci<='1';
            elsif (deci_ph_zero_end='1' and chan_max='1') then
              write_deci<='0';
            end if;
        end if;

        if (ip_rate>1) then
          if (deci_ph_last_start='1') then
            last_deci<='1';
          elsif (base_max='1') then
            last_deci<='0';
          end if;
        else
          last_deci<=write_deci;
        end if;
      end if;
    end if;
  end process;
end generate gen_decimation_write_pulses;
end;
