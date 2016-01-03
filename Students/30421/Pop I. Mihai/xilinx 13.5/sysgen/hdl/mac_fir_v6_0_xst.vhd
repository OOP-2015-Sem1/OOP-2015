
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


library work;
use work.mac_fir_utils_pkg.all;
use work.mac_fir_const_pkg.all;
use work.mac_fir_comp_pkg.all;
use work.mac_fir_func_pkg.all;


entity mac_fir_v6_0_xst is
generic (
  C_FAMILY            : string;
  C_ELABORATION_DIR   : string  := "./";
  C_COMPONENT_NAME    : string  := "fir";
  C_MEM_INIT_FILE     : string  := "COEFF";
  FILTER_TYPE         : integer := 0;
  INTERP_RATE         : integer := 1;
  DECIM_RATE          : integer := 1;
  RATE_CHANGE_TYPE    : integer := 0;
  ZERO_PACKING_FACTOR : integer := 0;
  NUM_CHANNELS        : integer := 1;
  CHAN_SEL_WIDTH      : integer := 4;
  NUM_TAPS            : integer := 16;
  CLK_PER_SAMP        : integer := 1;
  DATA_WIDTH          : integer := 16;
  COEF_WIDTH          : integer := 16;
  OUTPUT_WIDTH        : integer := 48;
  OUTPUT_REG          : integer := 1;
  SYMMETRY            : integer := 0;
  ODD_SYMMETRY        : integer := 0;
  NEG_SYMMETRY        : integer := 0;
  COEF_RELOAD         : integer := 1;
  NUM_FILTS           : integer := 1;
  FILTER_SEL_WIDTH    : integer := 4;
  C_HAS_SCLR          : integer := 1;
  C_HAS_CE            : integer := 0;
  C_HAS_ND            : integer := 1;
  C_HAS_RFD           : integer := 1;
  C_HAS_RDY           : integer := 1;
  DATA_MEMTYPE        : integer := 0;
  COEF_MEMTYPE        : integer := 0;
  MEMORY_PACK         : integer := 0;
  COL_MODE            : integer := 0;
  COL_1ST_LEN         : integer := 3;
  COL_WRAP_LEN        : integer := 4;
  COL_PIPE_LEN        : integer := 3;
  DATA_SIGN           : integer := 0;
  COEF_SIGN           : integer := 0;
  C_TAP_BALANCE       : integer := 0
);
port (
  CLK           : in  std_logic;
  CE            : in  std_logic;
  RESET         : in  std_logic;
  VIN           : in  std_logic;
  DIN           : in  std_logic_vector(DATA_WIDTH-1 downto 0);
  FILTER_SEL    : in  std_logic_vector(FILTER_SEL_WIDTH-1 downto 0);

  WE_FILTER_BLOCK   : out STD_LOGIC;
  COEF_CURRENT_PAGE : out STD_LOGIC;

  RFD           : out std_logic;
  VOUT          : out std_logic;
  DOUT          : out std_logic_vector(OUTPUT_WIDTH-1 downto 0);
  CHAN_OUT      : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0);
  CHAN_IN       : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0);

  COEF_LD       : in std_logic;
  COEF_WE       : in std_logic;
  COEF_DIN      : in std_logic_vector(COEF_WIDTH-1 downto 0);

  DOUT_I        : out std_logic_vector(DATA_WIDTH-1 downto 0);
  DOUT_Q        : out std_logic_vector(OUTPUT_WIDTH-1 downto 0)
);
end mac_fir_v6_0_xst;


architecture synth of mac_fir_v6_0_xst is


  constant fir_reqs : t_reqs:= (
           filter_type          =>

                                      UTILS_PKG_select_integer(
                                        FILTER_TYPE,
                                        c_interpolating_symmetry,
                                        FILTER_TYPE=c_polyphase_interpolating
                                        and SYMMETRY=1
                                        and not( (INTERP_RATE rem 2=0) and (NUM_TAPS rem 2=1) ) ),


           deci_rate            => DECIM_RATE,
           inter_rate           => INTERP_RATE,
           rate_type            => RATE_CHANGE_TYPE,
           num_taps             => NUM_TAPS,
           clk_per_samp         => CLK_PER_SAMP,
           num_channels         => NUM_CHANNELS,
           num_filts            => NUM_FILTS,
           memory_pack          => 1,
           symmetry             => SYMMETRY,
           odd_symmetry         => ODD_SYMMETRY,
           neg_symmetry         => NEG_SYMMETRY,
           zero_packing_factor  => ZERO_PACKING_FACTOR,
           data_width           => DATA_WIDTH,
           coef_width           => COEF_WIDTH,
           coef_reload          => COEF_RELOAD,
           data_mem_type        => DATA_MEMTYPE,
           coef_mem_type        => COEF_MEMTYPE,
           data_sign            => DATA_SIGN,
           coef_sign            => COEF_SIGN,
           tap_balance          => 1,
           reg_output           => OUTPUT_REG,
           c_has_nd             => C_HAS_ND
           );

  constant fir_details : t_details := mac_fir_generic_analysis(fir_reqs, C_FAMILY);




  constant use_dist_mem_gen : integer := 1;
  constant depth_min : integer := 16;
  constant addr_min  : integer := 4;

  constant col_run_length      : integer := COL_WRAP_LEN + COL_PIPE_LEN;
  constant num_wrap_pipes      : integer := UTILS_PKG_select_integer(
                                 1+(FIR_DETAILS.num_macs-COL_1ST_LEN-1)/COL_WRAP_LEN,
                                 0,
                                 FIR_DETAILS.num_macs <= COL_1ST_LEN);
  constant num_pipe_stages     : integer := COL_MODE*num_wrap_pipes*COL_PIPE_LEN;
  constant num_stages          : integer := FIR_DETAILS.num_macs + num_pipe_stages;

  constant has_output_hold     : integer := 1;
  constant result_output_width : integer := OUTPUT_WIDTH
                                            +UTILS_PKG_select_integer(0,1,fir_reqs.filter_type=c_interpolating_symmetry);

  constant dsp48e_use_mode : string := "MULT_S";

  constant load_acc                : std_logic_vector(6 downto 0) := "0010000";
  constant accumulate              : std_logic_vector(6 downto 0) := "0010010";
  constant load_acc_plus_c         : std_logic_vector(6 downto 0) := "0011100";
  constant halt_acc                : std_logic_vector(6 downto 0) := "0000010";
  constant load_acc_plus_a_times_b : std_logic_vector(6 downto 0) := "0010101";
  constant a_times_b               : std_logic_vector(6 downto 0) := "0000101";
  constant c_plus_pcin             : std_logic_vector(6 downto 0) := "0011100";
  constant a_times_b_plus_p        : std_logic_vector(6 downto 0) := "0100101";
  constant a_times_b_plus_c        : std_logic_vector(6 downto 0) := "0110101";
  constant a_concat_b_plus_p       : std_logic_vector(6 downto 0) := "0100011";
  constant p_plus_p                : std_logic_vector(6 downto 0) := "0100010";
  constant c_plus_c                : std_logic_vector(6 downto 0) := "0111100";

  constant data_depth : integer := fir_details.data_mem_depth;
  constant coef_depth : integer := fir_details.coef_mem_depth;

  constant d_addr_width : integer := max(1,UTILS_PKG_bits_needed_to_represent(data_depth-1));
  constant c_addr_width : integer := max(1,UTILS_PKG_bits_needed_to_represent(coef_depth-1));

  constant result_store_depth: integer:=UTILS_PKG_select_integer(
                                            fir_reqs.num_channels,
                                            fir_reqs.num_channels*fir_details.inter_rate,
                                            fir_reqs.filter_type=c_polyphase_pq and fir_reqs.deci_rate>fir_reqs.inter_rate);




  signal
         gnd,
         vcc,
         reset_clked,
         we_filter_block_int,
         we_out_filter_block,
         we_sym_filter_block,
         we_sym_out_filter_block,
         load_accum,
         accum_load_gen,
         accum_deci_phase0,
         accum_deci_phase0_gen,
         latch_lastphase_dly,
         load_accum_dly,
         latch_lastphase,
         latch_lastphase_gen,
         latch_accum_pq_deci_gen,
         latch_accum_pq_deci,
         output_reg_enab,
         output_chan_inc_gen,
         output_chan_clr_gen,
         output_chan_inc,
         output_chan_clr,
         accumulate_enable,
         accumulate_enable_delayed,
         output_chan_inc_latch,
         output_chan_clr_latch,
         input_chan_cnt_max,
         input_chan_cnt_max_cout,
         input_chan_cnt_max_sel,
         input_chan_cnt_max_gen,
         output_chan_cnt_max,
         output_chan_cnt_en,
         output_chan_cnt_max_cout,
         output_chan_cnt_max_gen,
         new_data_qual,
         vout_dly,
         vout_gen,
         rfd_internal,
         rfd_enab,
         rfd_ipbuff,
         inc_ip_buff,
         addr_gen_vin,
         ip_buff_vin,
         ip_buff_vin_dly,
         dsp48_c_en,
         load_acum_single_dly,
         latch_acum_inter_halfband,
         latch_lastphase_single_dly,
         data_bypass_we,
         latch_acum_inter_halfband_dly,
         op_rate_inter_halfband,
         op_rate_inter_halfband_dly,
         centre_tap_latch,
         we_sym_out_filter_block_dly,
         data_smux_sel,
         nd_internal,
         sclr_internal

         : std_logic:='0';


  signal
         din_filter_block,
         din_filter_block_2,
         din_filter_block_ip,
         latch_data_ip,
         latch_data_vin,
         din_filter_block_ip_latch,
         centre_tap_data,
         centre_tap_data_dly,
         centre_tap_data_chan_dly,
         centre_tap_data_halfband_deci,
         data_bypass_block_ip
         : std_logic_vector(fir_reqs.data_width-1 downto 0);

  signal
         halfband_centre_coef
         : std_logic_vector(fir_reqs.coef_width-1 downto 0);

  signal data_smac,
         data_smac_mux,
         coef_smac
         : std_logic_vector(24 downto 0);

  signal dsp48_data_in,
         dsp48_coef_in
         : std_logic_vector(29 downto 0);

  signal dsp48_a_in : std_logic_vector(29 downto 0);
  signal dsp48_b_in : std_logic_vector(17 downto 0);

  signal
         gnd_bus,
         accum_output,
         pc_out_filter_block,
         p_out_last_mac,
         dsp48_c_in

         : std_logic_vector(47 downto 0);

  signal output_reg_din,
         op_buff_out,
         op_buff_latch
         : std_logic_vector(OUTPUT_WIDTH-1 downto 0);

  signal deci_phase_results: std_logic_vector(UTILS_PKG_select_integer(
                                                OUTPUT_WIDTH,
                                                48,
                                                ( (fir_reqs.filter_type = c_polyphase_decimating
                                                or (fir_reqs.filter_type = c_polyphase_pq and fir_reqs.deci_rate>fir_reqs.inter_rate))
                                                and result_store_depth = 2))
                                              -1 downto 0):=(others=>'0');
  signal
         output_chan_cnt,
         output_chan_cnt_latch,
         input_chan_cnt
         : std_logic_vector(CHAN_SEL_WIDTH-1 downto 0)
         := (others=>'0');

  signal  filter_sel_int,
          filter_sel_ip,
          filter_sel_data_phase_bypass,
          filt_bypass_block_ip
          :std_logic_vector(FILTER_SEL_WIDTH-1 downto 0);

  signal coef_reload_data_mid: std_logic_vector(fir_reqs.coef_width-1 downto 0);
  signal  coef_reload_we_mid,
          coef_reload_complete,
          coef_reload_complete_dly,
          coef_current_page_int,
          coef_reload_finished,
          coef_next_page: std_logic:='0';
  signal  mid_tap_filter_page: std_logic_vector(0 downto 0):="0";
  signal  coef_reload_page: std_logic_vector(0 downto 0):="1";
  signal  coef_current_reload_page: std_logic:='1';


  signal accum_opcode              : std_logic_vector(6 downto 0):=(others=>'0');

  signal data_address_filter_block : std_logic_vector(d_addr_width-1 downto 0);

  signal data_sym_address_filter_block
         : std_logic_vector(max(1,log2_ext(fir_details.datasym_mem_depth))-1 downto 0);

  signal coef_address_filter_block,
         coef_address_filter_block_addr_gen : std_logic_vector(c_addr_width-1 downto 0);

  signal  ip_buff_in,
          ip_buff_out,
          data_bypass_in,
          data_bypass_out
          :std_logic_vector(FIR_REQS.data_width
                            +UTILS_PKG_select_integer(0,
                                                      FILTER_SEL_WIDTH,
                                                      FIR_REQS.NUM_FILTS>1)
                            -1 downto 0);

  signal  reset_clked_dly,
          reset_pipe :std_logic:='0';

  signal  coef_reload_page_pad,
          mid_tap_filter_page_pad:std_logic_vector(3 downto 0);

  signal  px_start: std_logic;


  signal  sym_addsub_in,
          sym_addsub_out,
          sym_addsub_dly,
          sym_addsub_firstphase,
          sym_addsub_firstphase_dly,
          sym_addsub_dly2,
          sym_addsub_dly3,
          sym_addsub_dly4,
          sym_addsub_dly5,
          inter_sym_acum_latch,
          inter_sym_acum_latch_dly,
          chan_phase_max,
          chan_phase_max_dly,
          chan_phase_max_dly_gen,
          phase_max,
          phase_max_dly,
          phase_max_dly2,
          latch_lastphase_reg,
          dsp48_a_ce,
          dsp48_b_ce,
          dsp48_sub,
          accum_sub_intersym,
          partial_add,
          reset_intsym
          :std_logic:='0';

  signal dsp48e_sub : std_logic_vector(3 downto 0);

  signal
          output_inter_sym
          : std_logic_vector(result_output_width-1 downto 0):=(others=>'0');

  signal  partial_store,
          partial_dly,
          partial_sel
          : std_logic_vector( max(1,(result_output_width-35))-1 downto 0):=(others=>'0');

  signal  a_b_concat_ip : std_logic_vector(47 downto 0);


  constant output_sign:integer:=UTILS_PKG_select_integer( 1,
                                                          0,
                                                          DATA_SIGN=0
                                                          or COEF_SIGN=0 );
  signal intersymadder_bypass:std_logic;

  signal int_ce                : std_logic := '0';
  signal new_data_qual_ce      : std_logic := '0';
  signal load_accum_dly_ce    : std_logic := '0';
  signal centre_latch_ce       : std_logic := '0';
        signal centre_tap_latch_ce   : std_logic := '0';
        signal dsp48_c_en_ce         : std_logic := '0';
        signal dsp48_c_ce            : std_logic := '0';


  function get_we_filt_blk_dly (
                              data_mem_type,
                              use_data_cascade,
                              clk_per_chan,
                              no_data_mem
                              : integer
                             )
                             return integer is

    variable temp_we_filt_blk_dly : integer := 0;

  begin


    if ( (data_mem_type=0 or no_data_mem=1) and use_data_cascade=0 ) then
      return 1;
    else
      return 0;
    end if;

  end;

  function get_coef_page_dly (
                              num_channels,
                              clk_per_chan,
                              use_data_cascade,
                              data_mem_type,
                              coef_mem_type,
                              filter_type,
                              inter_rate
                              : integer
                             )
                             return integer is

    variable delay:integer;
  begin

    if use_data_cascade=1 then
      if (coef_mem_type=1) then
         delay:=2;
      else
        delay:=1;
      end if;

    elsif num_channels>1 and clk_per_chan=1 then
      delay:=0;

    else
      delay:=0;

    end if;

    if clk_per_chan/inter_rate=1 then
      delay:=delay+1;
    end if;

    return delay;

  end;

  signal   sym_addsub_pipe : std_logic_vector (fir_details.num_macs-1 downto 0) := (others=>'0');

  constant we_filt_blk_dly : integer := get_we_filt_blk_dly ( fir_details.data_mem_type,
                                                              fir_details.use_data_cascade,
                                                              fir_details.clk_per_chan,
                                                              fir_details.no_data_mem );

  constant coef_page_dly   : integer := get_coef_page_dly ( fir_reqs.num_channels,
                                                            fir_details.clk_per_chan,
                                                            fir_details.use_data_cascade,
                                                            fir_details.data_mem_type,
                                                            fir_details.coef_mem_type,
                                                            fir_reqs.filter_type,
                                                            fir_details.inter_rate );

  signal we_filt_blk_pipe : std_logic_vector(we_filt_blk_dly-1 downto 0) := (others=>'0');
  signal coef_page_pipe   : std_logic_vector(  coef_page_dly-1 downto 0) := (others=>'0');



begin





  gsgwefb1 : if (we_filt_blk_dly > 1) generate
  begin

    sysgen_wefb_d1: process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr_internal='1') then
          we_filt_blk_pipe <= (others=>'0');
        elsif (int_ce='1') then
          we_filt_blk_pipe(we_filt_blk_dly-1 downto 1) <= we_filt_blk_pipe(we_filt_blk_dly-2 downto 0);
          we_filt_blk_pipe(0) <= we_filter_block_int;
        end if;
      end if;
    end process;

    WE_FILTER_BLOCK <= we_filt_blk_pipe(we_filt_blk_dly-1);

  end generate;

  gsgwefb2 : if (we_filt_blk_dly = 1) generate
  begin

    sysgen_wefb_d1: process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr_internal='1') then
          WE_FILTER_BLOCK <= '0';
        elsif (int_ce='1') then
          WE_FILTER_BLOCK <= we_filter_block_int;
        end if;
      end if;
    end process;

  end generate;

  gsgwefb3 : if (we_filt_blk_dly = 0) generate
  begin
    WE_FILTER_BLOCK <= we_filter_block_int;
  end generate;

  gsgccp1 : if (coef_page_dly > 1) generate
  begin

    sysgen_ccp_d1: process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr_internal='1') then
          coef_page_pipe <= (others=>'0');
        elsif (int_ce='1') then
          coef_page_pipe(coef_page_dly-1 downto 1) <= coef_page_pipe(coef_page_dly-2 downto 0);
          coef_page_pipe(0) <= coef_current_page_int;
        end if;
      end if;
    end process;

    COEF_CURRENT_PAGE <= coef_page_pipe(coef_page_dly-1);

  end generate;

  gsgccp2 : if (coef_page_dly = 1) generate
  begin

    sysgen_ccp_d1: process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr_internal='1') then
          COEF_CURRENT_PAGE <= '0';
        elsif (int_ce='1') then
          COEF_CURRENT_PAGE <= coef_current_page_int;
        end if;
      end if;
    end process;

  end generate;

  gsgccp3 : if (coef_page_dly = 0) generate
  begin
    COEF_CURRENT_PAGE <= coef_current_page_int;
  end generate;


  gnd     <= '0';
  vcc     <= '1';
  gnd_bus <= (others=>'0');

  gce1 : if C_HAS_CE=0 generate
  begin
    int_ce <= '1';
  end   generate;
  gce2 : if C_HAS_CE/=0 generate
  begin
    int_ce <= CE;
  end   generate;

  gen_reset_reg:process(clk)
  begin
    if (rising_edge(CLK) and int_ce='1') then
            reset_clked<=sclr_internal;
    end if;
  end process;

  sclr_internal<=RESET when C_HAS_SCLR=1 else
                 '0';

  nd_internal<= VIN when C_HAS_ND=1 else
                rfd_internal;


  rfd<= rfd_internal;



  address_generator: addr_gen
  generic map(
    C_FAMILY              => C_FAMILY,
    C_ELABORATION_DIR     => C_ELABORATION_DIR,
    C_COMPONENT_NAME      => C_COMPONENT_NAME,
    d_addr_width          => d_addr_width,
    d_sym_addr_width      => max(1,log2_ext(fir_details.datasym_mem_depth)),
    c_addr_width          => c_addr_width
                             -UTILS_PKG_select_integer(
                                      0,
                                      1,
                                      (FIR_REQS.coef_reload=1 and c_addr_width>1)),
    filter_type           => fir_reqs.filter_type,
    symmetrical           => fir_details.symmetry,
    num_channels          => fir_reqs.num_channels,
    num_filters           => fir_reqs.num_filts,
    num_phases            => UTILS_PKG_select_integer(
                              UTILS_PKG_select_integer(1,
                                                      fir_details.inter_rate,
                                                      fir_reqs.filter_type=c_polyphase_interpolating
                                                      or fir_reqs.filter_type=c_interpolating_symmetry),
                                                    fir_details.deci_rate,
                                                    fir_reqs.filter_type=c_polyphase_decimating
                                                    or fir_reqs.filter_type=c_halfband_transform
                                                    or fir_reqs.filter_type=c_hilbert_transform
                                                    or fir_reqs.filter_type=c_interpolated_transform
                                                    or fir_reqs.filter_type=c_decimating),

    calculation_cycles    => UTILS_PKG_select_integer(
                                  fir_details.num_taps_per_mac/fir_details.inter_rate,
                                  fir_details.num_taps_per_mac,
                                  fir_reqs.filter_type=c_polyphase_pq and fir_details.inter_rate>fir_details.deci_rate),

    input_cycles          =>  fir_details.addr_gen_ip_cyc,


    memory_type           => UTILS_PKG_select_integer(
                                  minf(1,fir_details.data_mem_type),
                                  0,
                                  FIR_DETAILS.no_data_mem=1),

    coef_memory_type      => fir_details.coef_mem_type,

    pq_p_val              => fir_details.inter_rate,
    pq_q_val              => fir_details.deci_rate,

    memory_packed         => 1,

    coef_offset           => fir_details.coef_mem_offset,
    sym_offset            => fir_details.datasym_mem_offset,
    data_width            => fir_reqs.data_width,
    odd_symmetry          => fir_details.odd_symmetry,
    no_data_mem           => fir_details.no_data_mem,
    hb_single_mac         => UTILS_PKG_select_integer(0,(1/FIR_DETAILS.num_macs),FIR_DETAILS.single_mac_modifier=0 and (fir_reqs.filter_type=c_decimating_half_band
                                                                                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                                                                                    or fir_reqs.filter_type=c_halfband_transform)),

    last_data_earily      => UTILS_PKG_select_integer(
                                      0,
                                      1,
                                      FIR_REQS.filter_type=c_decimating
                                      or (FIR_REQS.filter_type=c_polyphase_decimating
                                          and FIR_DETAILS.symmetry=1
                                          and FIR_DETAILS.num_macs=1)),

    ip_buffer_type        => FIR_REQS.data_mem_type,

    C_HAS_CE              => C_HAS_CE
  )
  port map(
    clk                   => clk,
    ce                    => int_ce,
    reset                 => sclr_internal,
    new_data              => nd_internal,
    write_data            => we_filter_block_int,
    last_data             => we_sym_filter_block,

    rfd                   => rfd_internal,
    filter                => FILTER_SEL,
    data_addr             => data_address_filter_block,
    data_sym_addr         => data_sym_address_filter_block,
    coef_addr             => coef_address_filter_block_addr_gen
                             (c_addr_width-UTILS_PKG_select_integer(
                                      1,
                                      2,
                                      (FIR_REQS.coef_reload=1 and c_addr_width>1)) downto 0),

    accumulate            => accumulate_enable,
    load_accum            => accum_load_gen,
    load_accum_phase0     => accum_deci_phase0_gen,
    latch_accum           => latch_lastphase_gen,
    latch_accum_pq_deci   => latch_accum_pq_deci_gen,
    sym_addsub            => sym_addsub_in,
    chan_phase_max_out    => chan_phase_max,
    phase_max_out         => phase_max,
    px_start              => px_start,

    data_in               => DIN,
    data_out              => din_filter_block_ip,

    data_out_hb_bypass    => data_bypass_block_ip,
    filt_out_hb_bypass    => filt_bypass_block_ip,
    write_data_hb_bypass  => data_bypass_we
  );

  output_chan_inc_gen<=latch_lastphase_gen;

  gen_reload_filter_switch: if (FIR_REQS.coef_reload=1) generate

    process(clk)
    begin
      if (rising_edge(clk)) then
        if (sclr_internal='1') then
          coef_reload_complete_dly<='0';
          coef_reload_finished<='0';
        elsif (int_ce='1') then
          coef_reload_complete_dly<=coef_reload_complete;

          if (FIR_DETAILS.no_data_mem=1) then

            if (coef_reload_complete='1' and coef_reload_complete_dly='0') then
              coef_current_page_int<=not coef_current_page_int;
              coef_current_reload_page<=not coef_current_reload_page;
            end if;

          else
            if (COEF_LD='1'or (px_start='1' and coef_reload_finished='1') ) then
              coef_reload_finished<='0';
            elsif (coef_reload_complete='1' and coef_reload_complete_dly='0') then
              coef_reload_finished<='1';
            end if;

            if (px_start='1' and (coef_reload_finished='1' and COEF_LD='0') ) then
              coef_current_page_int<=not coef_current_page_int;
              coef_current_reload_page<=not coef_current_reload_page;
            end if;

          end if;

        end if;
      end if;
    end process;

    coef_address_filter_block(c_addr_width-1)<=coef_current_page_int;

    gen_connect_address: if (c_addr_width>1) generate

      coef_address_filter_block(c_addr_width-2 downto 0)
          <=coef_address_filter_block_addr_gen(c_addr_width-2 downto 0);

    end generate gen_connect_address;

  end generate gen_reload_filter_switch;

  gen_connect_address_no_reload: if (FIR_REQS.coef_reload=0) generate
    coef_address_filter_block<=coef_address_filter_block_addr_gen;
  end generate gen_connect_address_no_reload;

  filter_engine: filter_block
  generic map (
    C_FAMILY            => C_FAMILY,
    C_ELABORATION_DIR   => C_ELABORATION_DIR,
    C_COMPONENT_NAME    => C_COMPONENT_NAME,
    FIR_REQS            => FIR_REQS,
    FIR_DETAILS         => FIR_DETAILS,
    COL_MODE            => COL_MODE,
    COL_1ST_LEN         => COL_1ST_LEN,
    COL_WRAP_LEN        => COL_WRAP_LEN,
    COL_PIPE_LEN        => COL_PIPE_LEN,
    C_HAS_CE             => C_HAS_CE
  )
  port map (
    CLK                 => CLK,
    CE                  => int_ce,
    RESET               => sclr_internal,
    WE                  => we_filter_block_int,
    DIN                 => din_filter_block_ip,
    DATA_ADDR_IN        => data_address_filter_block,
    COEF_ADDR_IN        => coef_address_filter_block,
    WE_OUT              => we_out_filter_block,
    WE_SYM              => we_sym_filter_block,
    WE_SYM_OUT          => we_sym_out_filter_block,
    PC_OUT              => pc_out_filter_block,
    DATA_SYM_ADDR_IN    => data_sym_address_filter_block,

    DATA_OUT_CENTRE_TAP => centre_tap_data,
    P_OUT_LAST_MAC      => p_out_last_mac,

    SYM_ADDSUB_IN       => sym_addsub_in,
    SYM_ADDSUB_OUT      => sym_addsub_out,
    PHASE_END           => latch_lastphase_gen,
    PHASE_START         => accum_deci_phase0_gen,

    DOUT_SMAC           => data_smac,
    COUT_SMAC           => coef_smac,

    COEF_LD             => COEF_LD,
    COEF_WE             => COEF_WE,
    COEF_DIN            => COEF_DIN,
    COEF_RELOAD_PAGE_IN => coef_current_reload_page,

    COEF_DIN_MID        => coef_reload_data_mid,
    COEF_WE_MID         => coef_reload_we_mid,

    COEF_LD_COMPLETE    => coef_reload_complete
  );






  load_accum_gen_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    - 1 - 1
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                    and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    +0,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => accum_load_gen,
    Q     => load_accum
  );

  load_accum_phase0_gen_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    - 1 - 1
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    +0,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => accum_deci_phase0_gen,
    Q     => accum_deci_phase0
  );

  accum_enable_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    - 1 - 1
                    + UTILS_PKG_select_integer
                        ( fir_details.half_band_inter_dly,
                          0,
                          (FIR_REQS.filter_type=c_interpolating_half_band
                          and FIR_DETAILS.num_macs=1
                          and FIR_DETAILS.single_mac_modifier=0))
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    +0,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => accumulate_enable,
    Q     => accumulate_enable_delayed
  );

  latch_accum_gen_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    - 1
                    - fir_details.half_band_inter_dly
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    +1
                    - UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_interpolating_symmetry),
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => latch_lastphase_gen,
    Q     => latch_lastphase
  );

  latch_accum_pq_deci_gen_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    - 1
                    - fir_details.half_band_inter_dly
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    +1
                    - UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_interpolating_symmetry)
                    + fir_details.acum_required
                    -1,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => latch_accum_pq_deci_gen,
    Q     => latch_accum_pq_deci
  );

  output_chan_inc_gen_dly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    + fir_details.acum_required
                    - 1
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    +1,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => output_chan_inc_gen,
    Q     => output_chan_inc
  );

  output_chan_clr_gen_dly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    + fir_details.acum_required
                    + fir_details.latch_last_phase_dly_mod
                    - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                   and FIR_DETAILS.single_mac_modifier=0
                                                   and (fir_reqs.filter_type=c_decimating_half_band
                                                        or fir_reqs.filter_type=c_interpolating_half_band
                                                        or fir_reqs.filter_type=c_halfband_transform) )
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    ,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => output_chan_clr_gen,
    Q     => output_chan_clr
  );

  gen_acum_dly_single:process(clk)
  begin
    if (rising_edge(clk)) then

      if (sclr_internal='1') then
        load_acum_single_dly       <='0';
        latch_lastphase_single_dly <='0';
      elsif (int_ce='1') then
        load_acum_single_dly       <= load_accum;
        latch_lastphase_single_dly <= latch_lastphase;
      end if;
    end if;
  end process;

  gen_symaddsubdly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.mac_block_latency-1
                      + UTILS_PKG_select_integer(0,1,fir_details.no_data_mem=1)
                      - UTILS_PKG_select_integer(0,1,fir_details.no_data_mem=0 and (fir_details.data_mem_type=1
                                                                                  or fir_details.data_mem_type=2)),
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => sym_addsub_out,
    Q     => sym_addsub_dly
  );

  gen_chanphasemax_dly_gen: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    +1
                    +fir_details.acum_required
                    -1,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => chan_phase_max,
    Q     => chan_phase_max_dly_gen
  );

  gen_chanphasemax_dly:process(clk)
  begin
        if (rising_edge(clk)) then
                if (reset_pipe='1') then
        chan_phase_max_dly<='0';
                elsif (int_ce='1') then
                  chan_phase_max_dly<=chan_phase_max_dly_gen;
                end if;
        end if;
  end process;

  gen_phasemax_dly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                  -1,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => phase_max,
    Q     => phase_max_dly
  );

  gen_phasemax_dly2: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.acum_required+2,
    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => phase_max_dly,
    Q     => phase_max_dly2
  );

  gen_multi_mac: if (FIR_DETAILS.num_macs /=1 or FIR_DETAILS.single_mac_modifier=1) generate








  gen_opcode_inter_halfband:
  if fir_reqs.filter_type = c_interpolating_half_band generate

    opcode_mux: process(clk)
    begin
      if (rising_edge(clk) and int_ce='1') then


        if ( latch_lastphase ='1' or latch_lastphase_single_dly='1' ) then
           latch_acum_inter_halfband <= '1';
        else
           latch_acum_inter_halfband <= '0';
        end if;

      end if;
    end process opcode_mux;

  end generate gen_opcode_inter_halfband;

  gen_opcode_sym_inter: if (fir_reqs.filter_type =c_interpolating_symmetry) generate
    constant odd_rate:integer:=UTILS_PKG_select_integer(0,1,
                                 FIR_DETAILS.inter_rate rem 2 > 0 );
  begin

    gen_inter_sym_parallel_dly: if (fir_details.no_data_mem=1) generate
      resetintsym: shift_ram_bit
      generic map (
        C_FAMILY       => C_FAMILY,
        C_DEPTH        => fir_details.num_macs
                        + fir_details.mac_block_latency
                        + num_pipe_stages
                        - 1
                        - fir_details.half_band_inter_dly
                        - UTILS_PKG_select_integer(0,1,fir_details.num_macs=1
                                                       and FIR_DETAILS.single_mac_modifier=0
                                                       and (fir_reqs.filter_type=c_decimating_half_band
                                                            or fir_reqs.filter_type=c_interpolating_half_band
                                                            or fir_reqs.filter_type=c_halfband_transform) )
                        + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                        + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                         and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                        + FIR_DETAILS.flip_buff_para_extra_delay
                        +1
                        - UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_interpolating_symmetry)
                        +1,
        C_ENABLE_RLOCS => 0
      )
      port map (
        CLK   => CLK,
        CE    => int_ce,
        SCLR  => '0',
        D     => sclr_internal,
        Q     => reset_intsym
      );

    end generate gen_inter_sym_parallel_dly;

    gen_inter_sym_disable: if (fir_details.no_data_mem=0) generate
      reset_intsym<='0';
    end generate gen_inter_sym_disable;

    opcode_mux: process(clk)
    begin
      if (rising_edge(clk)) then

        if (reset_intsym='1') then

          sym_addsub_firstphase<='0';
          accum_sub_intersym<='0';
          accum_opcode <=(others=>'0');
          sym_addsub_dly2<='0';
          sym_addsub_dly3<='0';
          sym_addsub_dly4<='0';
          sym_addsub_dly5<='0';

        elsif (int_ce='1') then

          accum_sub_intersym<='0';
          sym_addsub_dly2<=sym_addsub_dly;
          sym_addsub_dly3<=sym_addsub_dly2;
          sym_addsub_dly4<=sym_addsub_dly3;
          sym_addsub_dly5<=sym_addsub_dly4;


          if (odd_rate=1) then
            if (phase_max_dly='1') then
              sym_addsub_firstphase<='0';
            elsif (latch_lastphase='1') then
              sym_addsub_firstphase<='1';
            end if;
          else
            sym_addsub_firstphase<='1';
          end if;


          if (fir_details.no_data_mem=0) then

            if (odd_rate=1) then
              if (latch_lastphase='1' and sym_addsub_firstphase='0' and sym_addsub_dly2='0') then
                accum_opcode <= p_plus_p;
              elsif (latch_lastphase='1' and sym_addsub_firstphase='1' and sym_addsub_dly2='0') then
                accum_opcode <= a_concat_b_plus_p;
              elsif (load_acum_single_dly = '1' and sym_addsub_dly3='0') then
                accum_opcode <= c_plus_pcin;
              elsif (load_accum = '1'  and sym_addsub_dly='0') then
                accum_opcode <= accumulate;
                accum_sub_intersym<='1';
              elsif (accumulate_enable_delayed='1') then
                accum_opcode <= accumulate;
              else
                accum_opcode <= halt_acc;
              end if;
            else
              if (latch_lastphase='1' and sym_addsub_dly2='0') then
                accum_opcode <= a_concat_b_plus_p;
              elsif (load_acum_single_dly = '1' and sym_addsub_dly='1') then
                accum_opcode <= c_plus_pcin;
              elsif (load_accum = '1'  and sym_addsub_dly='0') then
                accum_opcode <= accumulate;
                accum_sub_intersym<='1';
              elsif (accumulate_enable_delayed='1') then
                accum_opcode <= accumulate;
              else
                accum_opcode <= halt_acc;
              end if;
            end if;

          else
            if (odd_rate=1) then

              if (sym_addsub_dly2='0' and sym_addsub_firstphase='0') then
                accum_opcode <= c_plus_c;
              elsif (sym_addsub_dly2='1') then
                accum_opcode <= c_plus_pcin;
                accum_sub_intersym<='1';
              else
                accum_opcode <= a_concat_b_plus_p;
              end if;
            else

              if (sym_addsub_dly='0') then
                accum_opcode <= c_plus_pcin;
                accum_sub_intersym<='1';
              else
                accum_opcode <= a_concat_b_plus_p;
              end if;

            end if;

          end if;

        end if;

      end if;
    end process opcode_mux;

  end generate gen_opcode_sym_inter;

  end generate gen_multi_mac;
  gen_opcode_single_mac:
  if (FIR_DETAILS.num_macs=1 and FIR_DETAILS.single_mac_modifier=0) generate

    opcode_mux:process(clk)
    begin

      if (rising_edge(clk) and int_ce='1') then

        if (FIR_REQS.filter_type=c_interpolating_half_band) then

          if ( latch_lastphase ='1' or latch_lastphase_single_dly='1' ) then
             latch_acum_inter_halfband <= '1';
          else
             latch_acum_inter_halfband <= '0';
          end if;

        end if;

      end if;

    end process;
  end generate gen_opcode_single_mac;



  dsp48_data_in <=
                ext_bus(data_smac_mux,30,FIR_REQS.data_sign)
                when (FIR_DETAILS.num_macs=1 and FIR_DETAILS.single_mac_modifier=0
                      and (FIR_REQS.filter_type=c_halfband_transform
                           or FIR_REQS.filter_type=c_interpolating_half_band
                           or FIR_REQS.filter_type=c_decimating_half_band)) else
                ext_bus(data_smac,30,FIR_REQS.data_sign)
                when (FIR_DETAILS.num_macs=1 and FIR_DETAILS.single_mac_modifier=0) else
                ext_bus(centre_tap_data_dly,30,FIR_REQS.data_sign)
                when ( fir_reqs.filter_type = c_halfband_transform
                  or   fir_reqs.filter_type = c_interpolating_half_band ) else
                ext_bus(centre_tap_data_halfband_deci,30,FIR_REQS.data_sign)
                when fir_reqs.filter_type = c_decimating_half_band else
                (others=>'0');

  dsp48_coef_in <=
                ext_bus(coef_smac,30,FIR_REQS.coef_sign)
                when (FIR_DETAILS.num_macs=1 and FIR_DETAILS.single_mac_modifier=0) else
                ext_bus(halfband_centre_coef,30,FIR_REQS.coef_sign);

  dsp48_a_in <= a_b_concat_ip(47 downto 18)
                when (fir_reqs.filter_type=c_interpolating_symmetry) and (C_FAMILY = "virtex5") else
                ext_bus(a_b_concat_ip(35 downto 18),30,0)
                when (fir_reqs.filter_type=c_interpolating_symmetry) and (C_FAMILY = "virtex4") else
                dsp48_coef_in
                when (FIR_REQS.coef_width+FIR_REQS.coef_sign > 18) else
                dsp48_data_in;

  dsp48_b_in <= a_b_concat_ip(17 downto 0)
                when (fir_reqs.filter_type=c_interpolating_symmetry) else
                dsp48_data_in(17 downto 0)
                when (FIR_REQS.coef_width+FIR_REQS.coef_sign > 18) else
                dsp48_coef_in(17 downto 0);

  dsp48_c_in <= p_out_last_mac
                when fir_reqs.filter_type = c_interpolating_half_band
                    or fir_reqs.filter_type = c_interpolating_symmetry else
                ext_bus(deci_phase_results,48,UTILS_PKG_select_integer(1,0,(FIR_REQS.data_sign=0 or FIR_REQS.coef_sign=0)));


  dsp48_c_ce <= int_ce and load_accum_dly
                    when (fir_reqs.filter_type = c_polyphase_decimating
                      or (fir_reqs.filter_type = c_polyphase_pq and fir_reqs.deci_rate>fir_reqs.inter_rate)) else
                int_ce
                    when fir_reqs.filter_type = c_interpolating_half_band
                      or fir_reqs.filter_type = c_interpolating_symmetry else
                '1';

  dsp48_a_ce<=int_ce and sym_addsub_dly3 when (fir_reqs.filter_type=c_interpolating_symmetry and fir_details.no_data_mem=1) else
              int_ce and latch_lastphase_dly when (fir_reqs.filter_type=c_interpolating_symmetry) else
              int_ce;

  dsp48_b_ce<=int_ce and sym_addsub_dly3 when (fir_reqs.filter_type=c_interpolating_symmetry and fir_details.no_data_mem=1) else
              int_ce and latch_lastphase_dly when (fir_reqs.filter_type=c_interpolating_symmetry) else
              int_ce;

  dsp48_sub  <= accum_sub_intersym
                when (fir_reqs.filter_type=c_interpolating_symmetry) else
                '0';
  dsp48e_sub <= "00" & accum_sub_intersym & accum_sub_intersym
                when (fir_reqs.filter_type=c_interpolating_symmetry) else
                "0000";






  load_accum_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH         => fir_details.acum_required +1,
    C_ENABLE_RLOCS  => 0
  )
  port map (
    CLK     => CLK,
    CE      => int_ce,
    SCLR    => sclr_internal,
    D       => load_accum,
    Q       => load_accum_dly
  );

  latch_lastphase_delay: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH         => fir_details.acum_required
                      +UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_interpolating_symmetry),
    C_ENABLE_RLOCS  => 0
  )
  port map (
    CLK     => CLK,
    CE      => int_ce,
    SCLR    => sclr_internal,
    D       => latch_lastphase,
    Q       => latch_lastphase_dly
  );

  gen_latch_inter_halfband:process(clk)
    begin
      if (rising_edge(clk)) then
        if (reset_pipe='1') then
          latch_acum_inter_halfband_dly <= '0';
        elsif (int_ce='1') then
          latch_acum_inter_halfband_dly <= latch_acum_inter_halfband;
        end if;
      end if;

    end process;

  gen_cascb_vout_dly:process(clk)
  begin
    if (rising_edge(clk)) then

      if (reset_pipe='1') then
        vout_gen <= '0';
      elsif (int_ce='1') then

        vout_gen<=latch_lastphase_dly;

      end if;
    end if;
  end process;


  gen_inter_sym: if (fir_reqs.filter_type=c_interpolating_symmetry) generate
    constant odd_rate:integer:=UTILS_PKG_select_integer(0,1,
                                 FIR_DETAILS.inter_rate rem 2 > 0 );
  begin

    intersymgencntrl:process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset_pipe='1') then

          inter_sym_acum_latch<='0';
          inter_sym_acum_latch_dly<='0';

          sym_addsub_firstphase_dly<='0';
                elsif (int_ce='1') then

          sym_addsub_firstphase_dly<=sym_addsub_firstphase;

          if (fir_details.no_data_mem=0) then
            inter_sym_acum_latch<=(latch_lastphase_reg and not sym_addsub_firstphase_dly)
                                  or (latch_lastphase and sym_addsub_firstphase and not sym_addsub_dly2)
                                  or (latch_lastphase_reg and sym_addsub_firstphase_dly and not sym_addsub_dly3);
          else
            inter_sym_acum_latch<=latch_lastphase_reg;
          end if;

          inter_sym_acum_latch_dly<=inter_sym_acum_latch;

                end if;
        end if;

    end process;

    latchlastphasereg:process(clk)
    begin
        if (rising_edge(clk) and int_ce='1') then
        latch_lastphase_reg<=latch_lastphase;
        end if;
    end process;


    gen_intersym_add: if ( (OUTPUT_WIDTH+1) >35) and (C_FAMILY /= "virtex5") generate
      process(clk)
      begin
        if (rising_edge(clk)) then
                if (reset_pipe='1') then
            output_inter_sym(34 downto 0)<=(others=>'0');
            partial_store<=(others=>'0');
            partial_dly<=(others=>'0');
            partial_add<='0';
                elsif (int_ce='1') then

            if (fir_details.no_data_mem=0) then

              if (latch_lastphase_dly='1') then
                partial_store<=accum_output(OUTPUT_WIDTH-1 downto 34);
              end if;

              output_inter_sym(34 downto 0)<=accum_output(34 downto 0);

              partial_dly<=partial_store;

              partial_add<=inter_sym_acum_latch_dly and latch_lastphase_dly;


            else
              if (sym_addsub_dly3='1') then
                partial_store<=p_out_last_mac(OUTPUT_WIDTH-1 downto 34);
              end if;

              output_inter_sym(34 downto 0)<=accum_output(34 downto 0);

              partial_dly<=partial_store;

              if (odd_rate=1) then
                partial_add<=sym_addsub_dly5;
              else
                partial_add<=sym_addsub_dly3;
              end if;

            end if;

                end if;
        end if;

      end process;


      partial_sel<=partial_dly when fir_details.no_data_mem=1 else partial_store;

      intersymadder_bypass <= not partial_add;

    end generate gen_intersym_add;

    gen_intersym_dly: if ( (OUTPUT_WIDTH+1) <=35) or (C_FAMILY = "virtex5") generate
      process(clk)
      begin
        if (rising_edge(clk)) then
                if (reset_pipe='1') then
            output_inter_sym<=(others=>'0');
                elsif (int_ce='1') then
                  output_inter_sym<=accum_output(OUTPUT_WIDTH downto 0);
                end if;
        end if;

      end process;

    end generate gen_intersym_dly;

    gen_norm_ab_ip: if ((OUTPUT_WIDTH+1)<=35) or (C_FAMILY = "virtex5") generate

      gen_norm_v4: if (fir_details.no_data_mem=0) and (C_FAMILY="virtex4") generate
        a_b_concat_ip<=ext_bus(accum_output(34 downto 0),47,0)&"0";
      end generate gen_norm_v4;

      gen_norm_v5: if (fir_details.no_data_mem=0) and (C_FAMILY="virtex5") generate
        a_b_concat_ip<=ext_bus(accum_output(46 downto 0),47,0)&"0";
      end generate gen_norm_v5;

      gen_para_v4: if (fir_details.no_data_mem=1) and (C_FAMILY="virtex4") generate
        a_b_concat_ip<=ext_bus(p_out_last_mac(34 downto 0),47,0)&"0";
      end generate gen_para_v4;

      gen_para_v5: if (fir_details.no_data_mem=1) and (C_FAMILY="virtex5") generate
        a_b_concat_ip<=ext_bus(p_out_last_mac(46 downto 0),47,0)&"0";
      end generate gen_para_v5;

    end generate gen_norm_ab_ip;

    gen_split_ab_ip: if ((OUTPUT_WIDTH+1)>35) and (C_FAMILY /= "virtex5") generate

      gen_norm: if (fir_details.no_data_mem=0) generate
        a_b_concat_ip<=ext_bus(accum_output(33 downto 0),47,1)&"0";
      end generate gen_norm;

      gen_para: if (fir_details.no_data_mem=1) generate
        a_b_concat_ip<=ext_bus(p_out_last_mac(33 downto 0),47,1)&"0";
      end generate  gen_para;

    end generate gen_split_ab_ip;

  end generate gen_inter_sym;


  output_reg_enab <= latch_accum_pq_deci
                when (fir_reqs.filter_type=c_polyphase_pq and fir_reqs.deci_rate>fir_reqs.inter_rate)
                else inter_sym_acum_latch_dly
                when fir_reqs.filter_type = c_interpolating_symmetry
                else latch_acum_inter_halfband_dly
                when fir_reqs.filter_type = c_interpolating_half_band
                else latch_lastphase_dly when fir_details.use_data_cascade=0
                else vout_gen;



  process (output_inter_sym, accum_output, pc_out_filter_block)
begin
if fir_reqs.filter_type=c_interpolating_symmetry then
output_reg_din <= output_inter_sym(minf(result_output_width-1,OUTPUT_WIDTH) downto 1);
else
if fir_details.acum_required = 1 then
output_reg_din <= accum_output(OUTPUT_WIDTH-1 downto 0);
else
output_reg_din <= pc_out_filter_block(result_output_width-1 downto 0);
end if;
end if;
end process;




  gen_reset_dly: shift_ram_bit
  generic map (
    C_FAMILY       => C_FAMILY,
    C_DEPTH        => fir_details.num_macs
                    + fir_details.mac_block_latency
                    + num_pipe_stages
                    + fir_details.acum_required
                    + 1
                    + UTILS_PKG_select_integer(0,1,FIR_REQS.filter_type=c_decimating and FIR_DETAILS.odd_symmetry=1)
                    + UTILS_PKG_select_integer(0,
                                               2,
                                               FIR_REQS.filter_type
                                               =c_decimating_half_band)

                    + UTILS_PKG_select_integer(0,2,(FIR_REQS.filter_type=c_decimating or FIR_REQS.filter_type=c_polyphase_decimating)
                                                     and FIR_DETAILS.clk_per_chan=1 and fir_details.symmetry=1)
                    + FIR_DETAILS.flip_buff_para_extra_delay
                    + UTILS_PKG_select_integer(0,1, FIR_DETAILS.use_data_cascade=1 and FIR_DETAILS.coef_mem_type=1)
                    ,

    C_ENABLE_RLOCS => 0
  )
  port map (
    CLK   => CLK,
    CE    => int_ce,
    SCLR  => sclr_internal,
    D     => reset_clked,
    Q     => reset_clked_dly
  );

  gen_reset_pipe:process(clk)
  begin
    if (rising_edge(clk) and int_ce='1') then
      if (C_HAS_SCLR=1) then
        if (sclr_internal='1') then
          reset_pipe<='1';
        elsif (reset_clked_dly='1') then
          reset_pipe<='0';
        end if;
      else
        reset_pipe<='0';
      end if;
    end if;
  end process;


  output_hold_reg_gen:
  if fir_details.data_out_latch_req = 1 and
     fir_reqs.filter_type /= c_interpolating_half_band and
     not (fir_reqs.filter_type = c_polyphase_decimating
          and fir_reqs.num_channels>1)
     and not( fir_reqs.filter_type = c_interpolating_symmetry)
     and not(fir_reqs.filter_type = c_polyphase_pq and fir_reqs.inter_rate<fir_reqs.deci_rate) generate

    output_hold_reg: process(CLK)
    begin
      if (rising_edge(clk)) then
        if ( reset_pipe = '1' ) then
          DOUT <= (others => '0');
        elsif (int_ce='1') then
          if ( output_reg_enab = '1' ) then
            DOUT <= output_reg_din;
          end if;
        end if;
      end if;
    end process output_hold_reg;

    gen_hilbert_op: if (FIR_REQS.filter_type=c_hilbert_transform) generate
      process(CLK)
      begin
        if (rising_edge(clk)) then

          if (reset_pipe='1') then
            DOUT_I<=(others=>'0');
            DOUT_Q<=(others=>'0');
          elsif (int_ce='1') then
            if ( output_reg_enab = '1' ) then
              DOUT_Q<=output_reg_din;
              DOUT_I<=centre_tap_data_dly;
            end if;
          end if;
        end if;
      end process;
    end generate gen_hilbert_op;

    vout_reg: process(CLK)
    begin
      if (rising_edge(clk)) then
        if ( reset_pipe = '1' ) then
          VOUT <= '0';
        elsif (int_ce='1') then
          if (sclr_internal='1') then
            VOUT<='0';
          else
            VOUT <= output_reg_enab;
          end if;

        end if;
      end if;
    end process vout_reg;

  end generate output_hold_reg_gen;

  no_output_hold_reg_gen:
  if fir_details.data_out_latch_req = 0 and
     fir_reqs.filter_type /= c_interpolating_half_band and
     not ( fir_reqs.filter_type = c_polyphase_decimating
          and fir_reqs.num_channels>1)
     and not(fir_reqs.filter_type = c_polyphase_pq and fir_reqs.inter_rate<fir_reqs.deci_rate) generate

    VOUT <= output_reg_enab;
    DOUT <= output_reg_din;

    gen_hilbert_op: if (FIR_REQS.filter_type=c_hilbert_transform) generate
      DOUT_Q<=output_reg_din;
      DOUT_I<=centre_tap_data_dly;
    end generate gen_hilbert_op;

  end generate no_output_hold_reg_gen;

  gen_output_buffer:
  if    fir_reqs.filter_type = c_interpolating_half_band
     or ( fir_reqs.filter_type = c_polyphase_decimating
        and fir_reqs.num_channels>1)
     or fir_reqs.filter_type = c_interpolating_symmetry
     or (fir_reqs.filter_type = c_polyphase_pq and fir_reqs.inter_rate<fir_reqs.deci_rate)  generate


    op_buff: output_buffer
    generic map(
      C_FAMILY     => C_FAMILY,
      C_ELABORATION_DIR => C_ELABORATION_DIR,
      data_width   => OUTPUT_WIDTH,
      fir_reqs     => fir_reqs,
      fir_details  => fir_details,
      C_HAS_CE     => C_HAS_CE
    )
    port map(
      CLK   => clk,
      CE    => int_ce,
      CLR   =>reset_pipe,

      WE   => output_reg_enab,
      D    => output_reg_din,

      Q    => op_buff_out,
      VOUT => op_rate_inter_halfband,

      LAST_PHASE => phase_max_dly2,
      LAST_CHAN_PHASE => chan_phase_max_dly
    );

    latch_data:process(clk)
    begin
      if (rising_edge(clk)) then
        if (reset_pipe='1') then
          op_buff_latch              <= (others=>'0');
          op_rate_inter_halfband_dly <= '0';
        elsif (int_ce='1') then
          if (sclr_internal='1') then
            op_buff_latch              <= (others=>'0');
            op_rate_inter_halfband_dly <= '0';
          else
            if (op_rate_inter_halfband='1') then
              op_buff_latch <= op_buff_out;
            end if;
            op_rate_inter_halfband_dly <= op_rate_inter_halfband;
          end if;
        end if;
      end if;
    end process;

      DOUT <= op_buff_latch
         when fir_details.data_out_latch_req = 1
         else op_buff_out;
      VOUT <= op_rate_inter_halfband_dly
         when fir_details.data_out_latch_req = 1
         else op_rate_inter_halfband;

  end generate gen_output_buffer;


  gen_channel_counter: if (fir_reqs.num_channels>1) generate


    output_chan_cnt_en<=op_rate_inter_halfband when (fir_reqs.filter_type = c_interpolating_half_band
                                                    or fir_reqs.filter_type = c_polyphase_decimating
                                                    or fir_reqs.filter_type = c_interpolating_symmetry
                                                    or (fir_reqs.filter_type = c_polyphase_pq and fir_reqs.inter_rate<fir_reqs.deci_rate)) else
                        output_chan_inc        when (fir_reqs.filter_type = c_polyphase_interpolating) else
                        output_reg_enab;

    chanout:chan_counter
    generic map(
      C_WIDTH=>CHAN_SEL_WIDTH,
      C_FLEXIPORT_FUNC=>0
    )
    port map(
      A_IN => output_chan_cnt,

      ENABLE     => output_chan_cnt_en,
      ENABLE_RST => output_chan_cnt_max,
      FLEXIPORT  => sclr_internal,

      D => output_chan_cnt,

      CE => int_ce,
      SCLR => reset_pipe,
      CLK => clk
    );

    chanoutmax: cmp_carrychain
    generic map(
      width => CHAN_SEL_WIDTH,
      const => 1,
      value => FIR_REQS.num_channels-2,
      c_family => C_FAMILY
    )
    port map(
      cin   => output_chan_cnt_en,
      A     => output_chan_cnt,
      B     => gnd_bus(CHAN_SEL_WIDTH-1 downto 0),
      cout  => output_chan_cnt_max_cout,
      Res   => open,
      sclr  => reset_pipe,
      ce    => int_ce,
      clk   => clk
    );

    chanoutmaxlatch: MUXCY_L
    port map(
      LO   => output_chan_cnt_max_gen,

      CI  => output_chan_cnt_max_cout,
      DI  => output_chan_cnt_max,
      S   => output_chan_cnt_en
    );

    chanoutmaxreg:process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset_pipe='1') then
          output_chan_cnt_max<='0';
                elsif (int_ce='1') then
                  output_chan_cnt_max<=output_chan_cnt_max_gen;
                end if;
        end if;
    end process;

    chanoutlatch:process(clk)
    begin
        if (rising_edge(clk)) then
                if (reset_pipe='1') then
          output_chan_cnt_latch<=(others=>'0');
                elsif (int_ce='1') then
                    if (sclr_internal='1') then
                      output_chan_cnt_latch<=(others=>'0');
                    elsif ( output_chan_cnt_en='1') then
                      output_chan_cnt_latch<=output_chan_cnt;
                    end if;
                end if;
        end if;
    end process;

    CHAN_OUT<=output_chan_cnt_latch when fir_details.data_out_latch_req=1 else
              output_chan_cnt;


    chanin:chan_counter
    generic map(
      C_WIDTH=>CHAN_SEL_WIDTH,
      C_FLEXIPORT_FUNC=>1
    )
    port map(
      A_IN => input_chan_cnt,

      ENABLE     => nd_internal,
      ENABLE_RST => input_chan_cnt_max,
      FLEXIPORT  => rfd_internal,

      D => input_chan_cnt,

      CE => int_ce,
      SCLR => sclr_internal,
      CLK => clk
    );

    chaninmax: cmp_carrychain
    generic map(
      width => CHAN_SEL_WIDTH,
      const => 1,
      value => FIR_REQS.num_channels-2,
      c_family => C_FAMILY
    )
    port map(
      cin   => input_chan_cnt_max_sel,
      A     => input_chan_cnt,
      B     => gnd_bus(CHAN_SEL_WIDTH-1 downto 0),
      cout  => input_chan_cnt_max_cout,
      Res   => open,
      sclr  => sclr_internal,
      ce    => int_ce,
      clk   => clk
    );

    chaninmaxlatch: MUXCY_L
    port map(
      LO   => input_chan_cnt_max_gen,

      CI  => input_chan_cnt_max_cout,
      DI  => input_chan_cnt_max,
      S   => input_chan_cnt_max_sel
    );

    input_chan_cnt_max_sel<=nd_internal and rfd_internal;

    chaninmaxreg:process(clk)
    begin
        if (rising_edge(clk)) then
                if (sclr_internal='1') then
          input_chan_cnt_max<='0';
                elsif (int_ce='1') then
                  input_chan_cnt_max<=input_chan_cnt_max_gen;
                end if;
        end if;
    end process;

  CHAN_IN <= input_chan_cnt;

 end generate gen_channel_counter;





  gen_we_sym_out_filter_block_dly:process(clk)
  begin
    if (rising_edge(CLK)) then
      if (reset_pipe='1') then
        we_sym_out_filter_block_dly<='0';
      elsif (int_ce='1') then
        we_sym_out_filter_block_dly<=we_sym_out_filter_block;
      end if;
    end if;
  end process;

  centre_tap_latch<=we_sym_out_filter_block when (FIR_DETAILS.data_mem_type=0 or FIR_DETAILS.no_data_mem=1)
                    else we_sym_out_filter_block_dly;

  centre_tap_latch_ce <= centre_tap_latch and int_ce;








end synth;


