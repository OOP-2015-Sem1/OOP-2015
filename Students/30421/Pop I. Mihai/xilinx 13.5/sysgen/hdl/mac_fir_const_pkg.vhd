
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
package mac_fir_const_pkg is
type text_file is file of string;
type integer_vector is array (NATURAL range <>) of integer;
type real_vector    is array (NATURAL range <>) of real;
constant c_distmem   : integer := 0;
constant c_blockmem  : integer := 1;
constant c_automatic : integer := 2;
constant c_maximum   : integer := 1;
constant mif_width            : integer := 32;
constant c_signed   : integer := 0;
constant c_unsigned : integer := 1;
constant c_new_line : string(1 to 1) := (1 => LF);
constant c_single_rate                : integer := 0;
constant c_polyphase_decimating       : integer := 1;
constant c_polyphase_interpolating    : integer := 2;
constant c_hilbert_transform          : integer := 3;
constant c_interpolated_transform     : integer := 4;
constant c_halfband_transform         : integer := 5;
constant c_decimating_half_band       : integer := 6;
constant c_interpolating_half_band    : integer := 7;
constant c_decimating                 : integer := 8;
constant c_polyphase_pq               : integer := 9;
constant c_interpolating_symmetry     : integer := 10;
type t_bram_depth is array (integer range <>)
     of integer;
constant v4_bram_depth : t_bram_depth(1 to 48)
         := ( 2**14,
              2**13,
              2**12,2**12,
              2**11,2**11,2**11,2**11,2**11,
              2**10,2**10,2**10,2**10,
              2**10,2**10,2**10,2**10,2**10,
              2**9,2**9,2**9,2**9,2**9,2**9,2**9,2**9,2**9,
              2**9,2**9,2**9,2**9,2**9,2**9,2**9,2**9,2**9,
              0,0,0,0,0,0,0,0,0,0,0,0);
type t_bram_prim is array (integer range <>)
     of string(1 to 6);
type t_bram_prim_bmg2v1 is array (integer range <>)
     of integer;
constant v4_bram_prim : t_bram_prim (1 to 36)
         := ( " 32kx1",
              "  8kx2",
              "  4kx4","  4kx4",
              "  2kx9","  2kx9","  2kx9","  2kx9","  2kx9",
              " 1kx18"," 1kx18"," 1kx18"," 1kx18",
              " 1kx18"," 1kx18"," 1kx18"," 1kx18"," 1kx18",
              "512x36","512x36","512x36","512x36","512x36","512x36","512x36","512x36","512x36",
              "512x36","512x36","512x36","512x36","512x36","512x36","512x36","512x36","512x36");
constant bmg2v1_bram_prim : t_bram_prim_bmg2v1 (1 to 36) := (
              0,
              1,
              2,2,
              3,3,3,3,3,
              4,4,4,4,4,4,4,4,4,
              5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5
              );
type t_reqs is
record
      filter_type         : integer;
      deci_rate           : integer;
      inter_rate          : integer;
      rate_type           : integer;
      num_taps            : integer;
      clk_per_samp        : integer;
      num_channels        : integer;
      num_filts           : integer;
      memory_pack         : integer;
      symmetry            : integer;
      odd_symmetry        : integer;
      neg_symmetry        : integer;
      zero_packing_factor : integer;
      coef_reload         : integer;
      data_width          : integer;
      coef_width          : integer;

      data_mem_type       : integer;
      coef_mem_type       : integer;
      data_sign           : integer;
      coef_sign           : integer;

      tap_balance         : integer;

      reg_output          : integer;

      c_has_nd            : integer;
end record;
constant cs_we_ip                     : integer := 0;
constant cs_we_reg1                   : integer := 1;
constant cs_we_reg2                   : integer := 6;
constant cs_we_reg3                   : integer := 7;
constant cs_we_reg4                   : integer := 8;
constant cs_we_reg5                   : integer := 9;
constant cs_we_sym_ip                 : integer := 2;
constant cs_we_sym_reg1               : integer := 3;
constant cs_we_sym_reg2               : integer := 4;
constant cs_we_sym_reg3               : integer := 13;
constant cs_high                      : integer := 5;
constant cs_cntrl_extra1              : integer :=10;
constant cs_cntrl_extra2              : integer :=11;
constant cs_cntrl_extra3              : integer :=12;
constant filter_state_num_ce_srcs     : integer :=14;
constant ds_store_out                 : integer := 0;
constant ds_data_in                   : integer := 1;
constant ds_buffer_out                : integer := 2;
constant ds_data_casc                 : integer := 3;
constant ds_data_sym_in               : integer := 4;
constant ds_data_sym_store_out        : integer := 5;
constant ds_data_sym_latch            : integer := 6;
constant ds_data_sym_buffer_out       : integer := 7;
constant ds_adder_out                 : integer := 8;
constant ds_adder_out_blanking        : integer := 9;
constant ds_data_dly                  : integer :=10;
constant ds_coef_dly                  : integer :=11;
constant ds_coef_store_out            : integer :=12;
constant filter_stage_num_srcs        : integer :=13;
type t_filter_stage_struct is
record
      data_store_in_src           : integer;
      data_store_we_src           : integer;
      data_sym_store_in_src       : integer;
      data_sym_store_we_src       : integer;
      adder_ip_1_src              : integer;
      adder_ip_2_src              : integer;
      adder_blank_src             : integer;
      data_buff_src               : integer;
      data_buff_ce_src            : integer;
      data_buff_laststage_src     : integer;
      data_buff_laststage_ce_src  : integer;
      data_out_src                : integer;
      data_ce_out_src             : integer;

      data_sym_buff_src           : integer;
      data_sym_buff_ce_src        : integer;
      data_sym_buff_re_src        : integer;
      data_sym_buff_depth_multi   : integer;
      data_sym_latch_src          : integer;
      data_sym_latch_ce_src       : integer;

      data_sym_out_src            : integer;
      data_sym_ce_out_src         : integer;

      data_bal_src                : integer;
      data_bal_depth              : integer;
      coef_bal_src                : integer;
      coef_bal_depth              : integer;
      multi_ip_ce_src             : integer;
      multi_data_src              : integer;
      multi_data_laststage_src    : integer;
      multi_coef_src              : integer;
      multi_coef_extra_delay      : integer;
      multi_data_extra_delay      : integer;
      multi_casc_add_en           : integer;
      input_data_sym_src          : integer;

      dout_mid_src                : integer;
end record;
type t_details is
record
      num_taps_calced             : integer;
      symmetry                    : integer;
      clk_per_chan                : integer;
      num_macs                    : integer;
      num_taps_per_mac            : integer;
      num_taps_per_mac_coefspace  : integer;
      num_taps_per_mac_dataspace  : integer;
      num_taps_per_phase          : integer;
      deci_rate                   : integer;
      inter_rate                  : integer;
      multi_write_deci_rate       : integer;
      deci_packed                 : integer;
      data_mem_depth              : integer;
      data_mem_type               : integer;
      datasym_mem_depth           : integer;
      datasym_mem_type            : integer;
      datasym_mem_offset          : integer;
      data_combined               : integer;
      coef_mem_depth              : integer;
      coef_mem_type               : integer;
      coef_mem_offset             : integer;
      data_coef_combined          : integer;
      mac_block_latency           : integer;
      data_delay                  : integer;
      coef_delay                  : integer;
      odd_symmetry                : integer;
      neg_symmetry                : integer;
      data_sym_buffer_depth       : integer;
      data_buffer_depth           : integer;

      last_stage_output_latch     : integer;
      last_stage_output_delay     : integer;

      datasym_output_latch        : integer;
      datasym_output_buffer_depth : integer;
      datasym_output_buffer_latch : integer;

      use_data_cascade            : integer;

      acum_required               : integer;
      no_data_mem                 : integer;
      parallel_datasym_mem        : integer;

      latch_last_phase_dly_mod    : integer;

      filter_stage_struct         : t_filter_stage_struct;

      data_in_latch_req           : integer;
      data_out_latch_req          : integer;

      ip_buff_mem_type            : integer;

      half_band_inter_dly         : integer;
      single_mac_modifier         : integer;
      mac_mid_tap                 : integer;

      flip_buff_para_extra_delay  : integer;
      addr_gen_calc_cyc           : integer;
      addr_gen_ip_cyc             : integer;
end record;
constant max_coef_width      : integer := 32;
constant max_num_taps        : integer := 1024;
constant max_rate            : integer := 1024;
constant max_frac_q_rate     : integer := 32;
constant max_num_taps_calced : integer := max_num_taps * max_frac_q_rate;
type t_coefficients is array (natural range <>)
     of std_logic_vector (max_coef_width-1 downto 0);

type t_multi_coefficients is array (natural range <>)
     of t_coefficients (max_num_taps_calced-1 downto 0);
end mac_fir_const_pkg;
package body mac_fir_const_pkg is
end mac_fir_const_pkg;
