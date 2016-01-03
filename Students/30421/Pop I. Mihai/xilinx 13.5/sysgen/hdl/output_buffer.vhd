
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
entity output_buffer is
generic (
  data_width    : integer;
  fir_reqs      :t_reqs;
  fir_details   :t_details;
  C_HAS_CE      : integer;
  c_family      : string;
  C_ELABORATION_DIR : string
);
port (
  CLK   : in  std_logic;
  CE    : in  std_logic;
  CLR   : in  std_logic;
  WE   : in  std_logic;
  D     : in  std_logic_vector(data_width-1 downto 0);
  Q     : out std_logic_vector(data_width-1 downto 0);
  VOUT  : out std_logic;

  LAST_PHASE: in std_logic;
  LAST_CHAN_PHASE: in std_logic
);
end output_buffer;
architecture synth of output_buffer is
constant use_dist_mem_gen : integer := 1;
constant use_blk_mem_gen  : integer := 1;
constant depth_min : integer := 16;
constant addr_min  : integer := 4;
constant depth_thresh : integer := UTILS_PKG_select_integer(16,32,C_FAMILY="virtex5");
constant memory_depth:integer:=UTILS_PKG_select_integer(
                                  UTILS_PKG_select_integer(
                                    UTILS_PKG_select_integer(
                                      max(16,2**log2_ext(2*2**log2_ext(2*fir_reqs.num_channels))),
                                      max(16,2**log2_ext(2*2**log2_ext(fir_reqs.num_channels))),
                                      (FIR_REQS.filter_type=c_polyphase_decimating) ),
                                    max(16,2**log2_ext(2*fir_reqs.num_channels*fir_details.inter_rate)),
                                    (FIR_REQS.filter_type=c_interpolating_symmetry) ),
                                  max(16,2**log2_ext(2*2**log2_ext(fir_reqs.num_channels*fir_details.inter_rate))),
                                  (FIR_REQS.filter_type=c_polyphase_pq and FIR_DETAILS.inter_rate<FIR_DETAILS.deci_rate) );
constant addr_width:integer:=max(addr_min,max(1,log2_ext(memory_depth)));
constant counter_width: integer:=UTILS_PKG_select_integer(addr_width-1,
                                                          addr_width,
                                                          FIR_REQS.filter_type=c_interpolating_symmetry);
constant odd_rate: integer:=UTILS_PKG_select_integer(0,1,
                              FIR_DETAILS.inter_rate rem 2 > 0 );
constant output_rate_count: integer:= UTILS_PKG_select_integer(
                                        UTILS_PKG_select_integer(
                                          UTILS_PKG_select_integer(
                                            ((fir_reqs.clk_per_samp/fir_reqs.num_channels)/2),
                                            ((fir_reqs.clk_per_samp/fir_reqs.num_channels)*FIR_DETAILS.deci_rate),
                                            (FIR_REQS.filter_type=c_polyphase_decimating) ),
                                          ((fir_reqs.clk_per_samp/fir_reqs.num_channels)/fir_details.inter_rate),
                                          (FIR_REQS.filter_type=c_interpolating_symmetry) ),
                                        (((fir_reqs.clk_per_samp/fir_reqs.num_channels)/fir_details.inter_rate)*fir_details.deci_rate),
                                        (FIR_REQS.filter_type=c_polyphase_pq and FIR_DETAILS.inter_rate<FIR_DETAILS.deci_rate) );
constant output_rate_width:integer:=max(1,log2_ext(output_rate_count));
constant addr_out_count:integer:= UTILS_PKG_select_integer(
                                    UTILS_PKG_select_integer(
                                      2*fir_reqs.num_channels,
                                      fir_reqs.num_channels,
                                      (FIR_REQS.filter_type=c_polyphase_decimating)),
                                    fir_reqs.num_channels*fir_details.inter_rate,
                                    (FIR_REQS.filter_type=c_polyphase_pq and FIR_DETAILS.inter_rate<FIR_DETAILS.deci_rate) );
signal  addr_out_cnt,
        addr_in_cnt_step:std_logic_vector(counter_width-1 downto 0);


signal  addr_in_cnt:std_logic_vector(counter_width-1 downto 0):=
                                std_logic_vector(to_unsigned(
                                    UTILS_PKG_select_integer(
                                      0,
                                      UTILS_PKG_select_integer(fir_details.inter_rate/2,
                                                               fir_details.inter_rate-1,
                                                               odd_rate=0),
                                      FIR_REQS.filter_type=c_interpolating_symmetry)
                                    ,counter_width));
signal addr_in,
       addr_out:std_logic_vector(addr_width-1 downto 0);
signal addr_in_max,
       addr_in_inc_2nd,
       addr_in_page,
       addr_in_max_sel,
       addr_in_max_gen,
       addr_in_max_cout,
       addr_out_max,
       addr_out_page,
       addr_out_cin,
       output_en,
       output_en_dly,
       addr_out_max_sel,
       addr_out_max_cout,
       addr_out_max_gen,
       vout_gen,
       vout_gen_dly1,
       vout_gen_dly2,
       last_chan,
       last_chan_cout,
       last_chan_sel,
       last_chan_gen
       :std_logic:='0';
signal  output_rate:std_logic_vector(output_rate_width-1 downto 0);
signal  output_rate_max:std_logic;
signal  phase_2ndlast,
        phase_2ndlast_cout,
        phase_2ndlast_gen,
        phase_2ndlast_latch,
        phase_2ndlast_norm,
        phase_2ndlast_dlyout,
        last_phase_dly,
        we_dly,
        we_sel,
        we_dmem_ce,
        chan_step_toggle,
        clr_dly,
        clr_latch,
        clr_ext
        :std_logic:='0';
signal  first_phase: std_logic:='1';
constant add_sub_init:std_logic_vector(0 downto 0):=std_logic_vector(to_unsigned(
                                                                          UTILS_PKG_select_integer(
                                                                            1,
                                                                            0,
                                                                            odd_rate=1)
                                                                          ,1));
signal  add_sub:std_logic:=add_sub_init(0);
signal  addr_in_cntrl_code,
        addr_out_cntrl_code:std_logic_vector(2 downto 0):="000";
signal  d_sel,
        d_dly
        :std_logic_vector(data_width-1 downto 0):=(others=>'0');

signal  chan_cnt:std_logic_vector(max(1,log2_ext(fir_reqs.num_channels))-1 downto 0);
signal  chan_max,
        chan_max_sel,
        chan_max_cout,
        chan_max_gen:std_logic:='0';
signal pad:std_logic_vector(max(output_rate_width,addr_width)-1 downto 0);
signal int_ce : std_logic;
signal we_sel_slv : std_logic_vector(0 downto 0);
begin
we_sel_slv(0) <= we_sel;
gce1 : if C_HAS_CE=0 generate
begin
  int_ce <= '1';
end     generate;
gce2 : if C_HAS_CE/=0 generate
begin
  int_ce <= ce;
end     generate;
gen_input_interpolating_hb: if (FIR_REQS.filter_type=c_interpolating_half_band) generate
  cntrl:process(clk)
  begin
        if (rising_edge(clk)) then
                if (clr='1') then
        addr_in_inc_2nd<='0';
        addr_in_page<='0';
                elsif (int_ce='1') then
        addr_in_inc_2nd<=WE and not addr_in_inc_2nd;
        if (addr_in_max='1') then
          addr_in_page<=not addr_in_page;
        end if;
                end if;
        end if;
  end process;
  addrincnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => counter_width,
    C_CONST_0_VAL => FIR_REQS.num_channels,
    C_CONST_1_VAL => -1*(FIR_REQS.num_channels-1),
    C_CONST_2_VAL => (2**counter_width)-((2*FIR_REQS.num_channels)-1)

  )
  port map(
    A_IN       => addr_in_cnt,

    ADD_C0     => WE,
    ADD_C1     => addr_in_inc_2nd,
    ADD_C2     => addr_in_max,
    C_IN       => '0',
    D          => addr_in_cnt,

    CE        => int_ce,
    SCLR      => clr,
    CLK       => clk
  );
  addrinmax: cmp_carrychain
  generic map(
    width => counter_width,
    const => 1,
    value => (FIR_REQS.num_channels)-1,
    c_family => c_family
  )
  port map(
    cin   => WE,
    A     => addr_in_cnt,
    B     => pad(counter_width-1 downto 0),
    cout  => open,
    Res   => addr_in_max,
    sclr  => clr,
    ce    => int_ce,
    clk   => clk
  );
  addr_in<=addr_in_page&addr_in_cnt;
end generate gen_input_interpolating_hb;
gen_input_decimation_polyphase: if (FIR_REQS.filter_type=c_polyphase_decimating)
                                    or (FIR_REQS.filter_type=c_polyphase_pq and FIR_DETAILS.inter_rate<FIR_DETAILS.deci_rate) generate

  addrincnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => counter_width,
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**counter_width)-(addr_out_count-1),
    C_CONST_2_VAL => 0

  )
  port map(
    A_IN       => addr_in_cnt,
    ADD_C0     => WE,
    ADD_C1     => addr_in_max,
    ADD_C2     => '0',
    C_IN       => '0',
    D          => addr_in_cnt,

    CE        => int_ce,
    SCLR      => clr,
    CLK       => clk
  );

  addrinmax: cmp_carrychain
  generic map(
    width => counter_width,
    const => 1,
    value => addr_out_count-2,
    c_family => c_family
  )
  port map(
    cin   => WE,
    A     => addr_in_cnt,
    B     => pad(counter_width-1 downto 0),
    cout  => addr_in_max_cout,
    Res   => open,
    sclr  => clr,
    ce    => int_ce,
    clk   => clk
  );

  addrinmaxgen: MUXCY_L
  port map(
    LO   => addr_in_max_gen,
    CI  => addr_in_max_cout,
    DI  => addr_in_max,
    S   => addr_in_max_sel
  );

  addr_in_max_sel<=WE;
  cntrl:process(clk)
  begin
        if (rising_edge(clk)) then
                if (clr='1') then
        addr_in_page<='0';
        addr_in_max<='0';
                elsif (int_ce='1') then
        if (addr_in_max='1' and WE='1') then
          addr_in_page<=not addr_in_page;
        end if;
        addr_in_max<=addr_in_max_gen;
                end if;
        end if;
  end process;
  addr_in<=addr_in_page&addr_in_cnt;
end generate gen_input_decimation_polyphase;
gen_input_interpolating_symmetry: if (FIR_REQS.filter_type=c_interpolating_symmetry) generate
constant delay_method_size: integer:=UTILS_PKG_select_integer (
                                    ((fir_details.inter_rate-3)/32)
                                      +UTILS_PKG_select_integer(0,1,(fir_details.inter_rate-3) rem 32>0)
                                    +((fir_details.inter_rate-4)/32)
                                      +UTILS_PKG_select_integer(0,1,(fir_details.inter_rate-4) rem 32>0)
                                    + 3,
                                    fir_details.inter_rate-2,
                                    fir_details.inter_rate-2<=1);
constant counter_method_size_val1: integer:=(log2_ext(fir_details.inter_rate)/2)
                                      + UTILS_PKG_select_integer(0,1,log2_ext(fir_details.inter_rate) rem 2 >0);
constant counter_method_size: integer:=counter_method_size_val1
                                      +UTILS_PKG_select_integer( counter_method_size_val1/4 + minf(1,counter_method_size_val1 rem 4),
                                                                 counter_method_size_val1/6 + minf(1,counter_method_size_val1 rem 6),
                                                                 c_family="virtex5") +1;
begin
  gen_delay_method: if (delay_method_size<=counter_method_size) generate
    signal delay_shift: std_logic;
  begin
    delay_shift<=int_ce and WE;
    gen_delay: if (fir_details.inter_rate-2>1) generate
      gen_2ndlastphase: shift_ram_bit
      generic map (
        C_FAMILY       => C_FAMILY,
        C_DEPTH         => fir_details.inter_rate-2 -1,
        C_ENABLE_RLOCS  => 0
      )
      port map (
        CLK     => CLK,
        CE      => delay_shift,
        SCLR    => clr,
        D       => first_phase,
        Q       => phase_2ndlast_dlyout
      );
      clrdelay: shift_ram_bit
      generic map (
        C_FAMILY        => C_FAMILY,
        C_DEPTH         => fir_details.inter_rate-2 -2,
        C_ENABLE_RLOCS  => 0
      )
      port map (
        CLK     => CLK,
        CE      => delay_shift,
        SCLR    => clr,
        D       => clr_latch,
        Q       => clr_dly
      );

      process(clk)
      begin
        if (rising_edge(clk)) then

          if (clr='1' or clr_ext='1') then
            phase_2ndlast<='0';
                elsif(int_ce='1') then
                  if (delay_shift='1') then
              phase_2ndlast<=phase_2ndlast_dlyout;
            end if;
                end if;
          if (clr='1') then
            clr_ext<='1';
          elsif (clr_dly='1') then
            clr_ext<='0';
          end if;
          if (clr='1') then
            clr_latch<='1';
          elsif (delay_shift='1') then
            clr_latch<='0';
          end if;
        end if;
      end process;
    end generate gen_delay;

    gen_reg: if ( fir_details.inter_rate-2=1) generate

      process(clk)
      begin
        if (rising_edge(clk)) then
                if (clr='1') then
            phase_2ndlast<='0';
                elsif (int_ce='1') then
                  if (delay_shift='1') then
              phase_2ndlast<=first_phase;
            end if;
                end if;
        end if;

      end process;
    end generate gen_reg;
    gen_map: if (fir_details.inter_rate-2=0) generate
      phase_2ndlast<=first_phase;
    end generate gen_map;

  end generate gen_delay_method;
  gen_counter_method: if (delay_method_size>counter_method_size) generate
    constant phase_width:integer:=max(1,log2_ext(fir_details.inter_rate));
    signal phase_cnt:std_logic_vector(phase_width-1 downto 0);
  begin
    phasecnt: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => phase_width,
      C_CONST_0_VAL => 1,
      C_CONST_1_VAL => (2**phase_width)-(fir_details.inter_rate-1),
      C_CONST_2_VAL => 0

    )
    port map(
      A_IN       => phase_cnt,

      ADD_C0     => WE,
      ADD_C1     => LAST_PHASE,
      ADD_C2     => '0',

      C_IN       => '0',
      D          => phase_cnt,
      CE        => int_ce,
      SCLR      => clr,
      CLK       => clk
    );

    phasecnt2ndlast: cmp_carrychain
    generic map(
      width => phase_width,
      const => 1,
      value => fir_details.inter_rate-2
               -UTILS_PKG_select_integer(0,1,fir_details.no_data_mem=1),
      c_family => c_family
    )
    port map(
      cin   => '1',
      A     => phase_cnt,
      B     => pad(phase_width-1 downto 0),
      cout  => phase_2ndlast_cout,
      Res   => phase_2ndlast_norm,
      sclr  => clr,
      ce    => int_ce,
      clk   => clk
    );

    gen_latch: if (fir_details.no_data_mem=1) generate

      phasecnt2ndlastgen: MUXCY_L
      port map(
        LO   => phase_2ndlast_gen,
        CI  => phase_2ndlast_cout,
        DI  => phase_2ndlast_latch,
        S   => WE
      );

      process(clk)
      begin
        if (rising_edge(clk)) then
                if (clr='1') then
            phase_2ndlast_latch<='0';
                elsif (int_ce='1') then
                  phase_2ndlast_latch<=phase_2ndlast_gen;
                end if;
        end if;

      end process;
    end generate gen_latch;

    phase_2ndlast<=phase_2ndlast_norm when fir_details.no_data_mem=0 else
                   phase_2ndlast_latch;
  end generate gen_counter_method;
  gen_odd_step: if (odd_rate=1) generate
    addrinstep: code_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => counter_width,

      C_INIT_SCLR_VAL => fir_details.inter_rate/2,

      C_VAL_0 => 0,
      C_VAL_1 => -1,
      C_VAL_2 => fir_details.inter_rate/2,
      C_VAL_3 => fir_details.inter_rate-1,
      C_VAL_4 => -1*
                (fir_details.inter_rate-1
                 +(-1*(fir_details.inter_rate-3))
                 + (fir_details.inter_rate-1)
                 - fir_details.inter_rate/2),
      C_VAL_5 => -1*( (2*fir_reqs.num_channels*fir_details.inter_rate)-1
                      -(fir_details.inter_rate-(fir_details.inter_rate/2))
                      -(fir_details.inter_rate/2) )
                  -
                      (fir_details.inter_rate-1
                      +(-1*(fir_details.inter_rate-3))),
      C_VAL_6 =>  -1*(-1*( (2*fir_reqs.num_channels*fir_details.inter_rate)-1
                      -(fir_details.inter_rate-(fir_details.inter_rate/2))
                      -(fir_details.inter_rate/2) )
                  -   (fir_details.inter_rate-1
                      +(-1*(fir_details.inter_rate-3))))
                   - 1*( (fir_details.inter_rate-1
                          +(-1*(fir_details.inter_rate-3)))
                        - (fir_details.inter_rate/2) ),
      C_VAL_7 => 0
    )
    port map(
      A_IN       => addr_in_cnt_step,

      CODE      =>  addr_in_cntrl_code,

      C_IN       => '0',
      D          => addr_in_cnt_step,

      CE        => int_ce,
      SCLR      => clr,
      CLK       => clk
    );

  end generate gen_odd_step;

  gen_even_step: if (odd_rate=0) generate

  constant val_2: integer:= ((fir_details.inter_rate*2)-1)
                            -((fir_details.inter_rate/2)-1)
                            -1;

  constant val_4: integer:= -1* (
                                ((fir_details.inter_rate/2)-1)+fir_details.inter_rate*((2*fir_reqs.num_channels)-1)
                               - (fir_details.inter_rate-1) )
                               - 1;

  begin

    addrinstep: code_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => counter_width,

      C_INIT_SCLR_VAL => fir_details.inter_rate-1,

      C_VAL_0 => 0,
      C_VAL_1 => -1,

      C_VAL_2 =>  val_2,
      C_VAL_3 => (-1*val_2)
                +(fir_details.inter_rate-2),

      C_VAL_4 =>  val_4,
      C_VAL_5 => (-1*val_4)
                +(fir_details.inter_rate-2),
      C_VAL_6 => 0,
      C_VAL_7 => 0

    )
    port map(
      A_IN       => addr_in_cnt_step,
      CODE      =>  addr_in_cntrl_code,

      C_IN       => '0',
      D          => addr_in_cnt_step,

      CE        => int_ce,
      SCLR      => clr,
      CLK       => clk
    );
  end generate gen_even_step;
  gen_cntrl:process(clk)
  begin
        if (rising_edge(clk)) then
                if (clr='1') then
        first_phase<='1';
        addr_in_cntrl_code<="000";
        addr_in_page<='0';

        if (odd_rate=1) then
          add_sub<='0';
        else
          add_sub<='1';
        end if;
        addr_in_cnt<=std_logic_vector(to_unsigned(
                                      UTILS_PKG_select_integer(fir_details.inter_rate/2,
                                                               fir_details.inter_rate-1,
                                                               odd_rate=0)
                                      ,counter_width));
        addr_in_max<='0';
        we_dly<='0';
        d_dly<=(others=>'0');
                elsif (int_ce='1') then
        d_dly<=d;
        if (LAST_PHASE='1') then
          first_phase<='1';
        elsif (WE='1') then
          first_phase<='0';
        end if;

        we_dly<=we;
        last_phase_dly<=LAST_PHASE;
        if (odd_rate=1) then
          if (WE='1') then

            if (LAST_CHAN_PHASE='1' and addr_in_page='1') then
              addr_in_cntrl_code<="110";
            elsif (last_chan='1' and phase_2ndlast='1' and addr_in_page='1') then
              addr_in_cntrl_code<="101";
            elsif (LAST_PHASE='1') then
              addr_in_cntrl_code<="100";
            elsif (phase_2ndlast='1') then
              addr_in_cntrl_code<="011";
            elsif (first_phase='1') then
              addr_in_cntrl_code<="010";
            else
              addr_in_cntrl_code<="001";
            end if;
          else
            addr_in_cntrl_code<="000";
          end if;
        else
            if (WE='1') then

            if (LAST_CHAN_PHASE='1' and addr_in_page='1') then
              addr_in_cntrl_code<="101";
            elsif (last_chan='1' and phase_2ndlast='1' and addr_in_page='1') then
              addr_in_cntrl_code<="100";
            elsif (LAST_PHASE='1') then
              addr_in_cntrl_code<="011";
            elsif (phase_2ndlast='1') then
              addr_in_cntrl_code<="010";
            else
              addr_in_cntrl_code<="001";
            end if;
          else
            addr_in_cntrl_code<="000";
          end if;
        end if;

        if (LAST_CHAN_PHASE='1') then
          addr_in_page<=not addr_in_page;
        end if;


        if (odd_rate=1) then
          if (last_phase_dly='1') then
            add_sub<='0';
          elsif (we_dly='1') then
            add_sub<=not add_sub;
          end if;
        else
          if (last_phase_dly='1') then
            add_sub<='1';
          elsif (we_dly='1') then
            add_sub<=not add_sub;
          end if;
        end if;

        if (we_dly='1') then
          if (add_sub='1') then
            addr_in_cnt<=std_logic_vector(unsigned(addr_in_cnt)-unsigned(addr_in_cnt_step));
          else
            addr_in_cnt<=std_logic_vector(unsigned(addr_in_cnt)+unsigned(addr_in_cnt_step));
          end if;
        else
          addr_in_cnt<=addr_in_cnt;
        end if;

        addr_in_max<=LAST_CHAN_PHASE;
                end if;
        end if;
  end process;
  addr_in<=addr_in_cnt;

  gen_last_chan: if (fir_reqs.num_channels>1) generate
  constant last_chan_mod: integer:=UTILS_PKG_select_integer(0,
                                                            1,
                                                            fir_details.no_data_mem=1 and fir_details.inter_rate=2);
  begin
    lastchan: cmp_carrychain
    generic map(
      width => counter_width,
      const => 1,
      value => UTILS_PKG_select_integer(
                      ((fir_details.inter_rate/2)-1)+fir_details.inter_rate*((2*fir_reqs.num_channels)-2),
                      ((fir_details.inter_rate/2)-1)+fir_details.inter_rate*((2*fir_reqs.num_channels)-2),
                      odd_rate=0)
              + last_chan_mod,
      c_family => c_family
    )
    port map(
      cin   => '1',
      A     => addr_in_cnt,
      B     => pad(counter_width-1 downto 0),
      cout  => last_chan_cout,
      Res   => open,
      sclr  => clr,
      ce    => int_ce,
      clk   => clk
    );

    lastchangen: MUXCY_L
    port map(
      LO   => last_chan_gen,
      CI  => last_chan_cout,
      DI  => last_chan,
      S   => last_chan_sel
    );

    last_chan_sel<= LAST_PHASE when (fir_details.no_data_mem=1 and fir_details.inter_rate=2) else
                    last_phase_dly;

    process(clk)
    begin
        if (rising_edge(clk)) then
                if (clr='1') then
          last_chan<='0';
                elsif (int_ce='1') then
          last_chan<=last_chan_gen;
                end if;
        end if;
    end process;
  end generate gen_last_chan;
  gen_last_chan_const: if (fir_reqs.num_channels=1) generate
    last_chan<='1';
  end generate gen_last_chan_const;
end generate gen_input_interpolating_symmetry;
gen_norm_output: if not(FIR_REQS.filter_type=c_interpolating_symmetry) generate
  addroutcnt: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => counter_width,
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**counter_width)-(addr_out_count-1),
    C_CONST_2_VAL => 0

  )
  port map(
    A_IN       => addr_out_cnt,
    ADD_C0     => output_rate_max,
    ADD_C1     => addr_out_max,
    ADD_C2     => '0',
    C_IN       => '0',
    D          => addr_out_cnt,
    CE        => int_ce,
    SCLR      => clr,
    CLK       => clk
  );
  addroutmax: cmp_carrychain
  generic map(
    width => counter_width,
    const => 1,
    value => (addr_out_count-2),
    c_family => c_family
  )
  port map(
    cin   => output_en,
    A     => addr_out_cnt,
    B     => pad(counter_width-1 downto 0),
    cout  => addr_out_max_cout,
    Res   => open,
    sclr  => clr,
    ce    => int_ce,
    clk   => clk
  );

  addroutmaxgen: MUXCY_L
  port map(
    LO   => addr_out_max_gen,
    CI  => addr_out_max_cout,
    DI  => addr_out_max,
    S   => addr_out_max_sel
  );

  addr_out_max_sel<=output_rate_max;
  cntrl:process(clk)
  begin
        if (rising_edge(clk)) then
                if (clr='1') then
        output_en<='0';
        addr_out_page<='0';
        addr_out_max<='0';
        vout_gen<='0';
        vout_gen_dly1<='0';
        vout_gen_dly2<='0';
                elsif (int_ce='1') then
        if (WE='1' and addr_in_max='1') then
          output_en<='1';
        elsif (output_rate_max='1' and addr_out_max='1') then
          output_en<='0';
        end if;
        if (output_rate_max='1' and addr_out_max='1') then
          addr_out_page<=not addr_out_page;
        end if;
        addr_out_max<=addr_out_max_gen;
        vout_gen<=(WE and addr_in_max) or (output_rate_max and not addr_out_max);
        vout_gen_dly1<=vout_gen;
        vout_gen_dly2<=vout_gen_dly1;
                end if;
        end if;
  end process;

  addr_out<=addr_out_page&addr_out_cnt;
  VOUT<=vout_gen_dly1 when (memory_depth<=depth_thresh and fir_reqs.data_mem_type=0)
                      or (fir_reqs.data_mem_type=1) else
        vout_gen_dly2;
end generate;
gen_intersym_output: if (FIR_REQS.filter_type=c_interpolating_symmetry) generate
constant parallel_mod: integer:=UTILS_PKG_select_integer( 0,
                                                          UTILS_PKG_select_integer(
                                                            1,
                                                            UTILS_PKG_select_integer(
                                                              -1*fir_details.inter_rate,
                                                               fir_details.inter_rate-1,
                                                               fir_reqs.num_channels=2),
                                                            fir_reqs.num_channels>1),
                                                          output_rate_count=1);
begin

  cntrl:process(clk)
  begin
        if (rising_edge(clk)) then
                if (clr='1') then
        addr_out_cntrl_code<="000";
        addr_out_max<='0';
        addr_out_page<='0';
        output_en<='0';
        vout_gen<='0';
        output_en_dly<='0';
                elsif (int_ce='1') then

                  if (output_rate_max='1') then
                    if (addr_out_max='1' and addr_out_page='1') then
                      addr_out_cntrl_code<="100";
                    elsif (addr_out_max='1') then
                      addr_out_cntrl_code<="011";
                    elsif (chan_max='1') then
                      addr_out_cntrl_code<="010";
                    else
                      addr_out_cntrl_code<="001";
                    end if;
                  else
                    addr_out_cntrl_code<="000";
                  end if;


                  if (output_rate_count=1 and fir_details.inter_rate=2 and fir_reqs.num_channels=1) then
          addr_out_max<=output_en and not addr_out_max;
                  else
          addr_out_max<=addr_out_max_gen;
        end if;

                  if (addr_in_max='1' and we_dly='1') then
                    output_en<='1';
                  elsif (output_rate_max='1' and addr_out_max='1') then
          output_en<='0';
        end if;

        vout_gen<=(we_dly and addr_in_max) or (output_rate_max and not addr_out_max);
        if (output_rate_max='1' and addr_out_max='1') then
          addr_out_page<=not addr_out_page;
        end if;

        output_en_dly<=output_en;
                end if;
        end if;
  end process;
  gen_addrout_norm: if not(output_rate_count=1 and fir_details.inter_rate=2 and fir_reqs.num_channels=1) generate
    addroutmax: cmp_carrychain_dualval
    generic map(
      width => counter_width,
      value1 => UTILS_PKG_select_integer(
                     fir_details.inter_rate-2-parallel_mod,
                     fir_details.inter_rate-1+((fir_reqs.num_channels-2)*fir_details.inter_rate)+parallel_mod,
                     fir_reqs.num_channels>1 ),
      value2 => (fir_details.inter_rate*fir_reqs.num_channels)
                +UTILS_PKG_select_integer(
                     fir_details.inter_rate-2-parallel_mod,
                     fir_details.inter_rate-1+((fir_reqs.num_channels-2)*fir_details.inter_rate)+parallel_mod,
                     fir_reqs.num_channels>1 ),
      c_family => c_family
    )
    port map(
      cin   => addr_out_cin,
      A     => addr_out_cnt,
      sel   => addr_out_page,
      cout  => addr_out_max_cout,
      Res   => open,
      sclr  => clr,
      ce    => int_ce,
      clk   => clk
    );

    addr_out_cin<=output_en_dly when (fir_details.inter_rate=3 and output_rate_count=1) else
                  output_en;

    addroutmaxgen: MUXCY_L
    port map(
      LO   => addr_out_max_gen,

      CI  => addr_out_max_cout,
      DI  => addr_out_max,
      S   => addr_out_max_sel
    );

    addr_out_max_sel<=output_rate_max;
  end generate gen_addrout_norm;

  addroutcnt: code_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => counter_width,
    C_INIT_SCLR_VAL => 0,
    C_VAL_0 => 0,
    C_VAL_1 => UTILS_PKG_select_integer(1,fir_details.inter_rate,fir_reqs.num_channels>1),
    C_VAL_2 => -1* (UTILS_PKG_select_integer(1,fir_details.inter_rate*(fir_reqs.num_channels-1),fir_reqs.num_channels>1)-1),
    C_VAL_3 => 1,
    C_VAL_4 => (2**counter_width)-( (2*fir_details.inter_rate*fir_reqs.num_channels)-1),
    C_VAL_5 => 0,
    C_VAL_6 => 0,
    C_VAL_7 => 0
  )
  port map(
    A_IN       => addr_out_cnt,
    CODE      =>  addr_out_cntrl_code,
    C_IN       => '0',
    D          => addr_out_cnt,
    CE        => int_ce,
    SCLR      => clr,
    CLK       => clk
  );
  genvout: shift_ram_bit
  generic map (
    C_FAMILY        => C_FAMILY,
    C_DEPTH         => UTILS_PKG_select_integer( 3,
                                                 2,
                                                 (memory_depth<=depth_thresh and fir_reqs.data_mem_type=0)
                                                 or (fir_reqs.data_mem_type=1)),
    C_ENABLE_RLOCS  => 0
  )
  port map (
    CLK     => CLK,
    CE      => int_ce,
    SCLR    => clr,
    D       => vout_gen,
    Q       => VOUT
  );

  addr_out<=addr_out_cnt;

  gen_chan_cnt: if (fir_reqs.num_channels>1) generate

    chancnt: combined_priority_counter
    generic map(
      C_HAS_C_IN    => 0,
      C_WIDTH       => max(1,log2_ext(fir_reqs.num_channels)),
      C_CONST_0_VAL => 1,
      C_CONST_1_VAL => (2**max(1,log2_ext(fir_reqs.num_channels)))-(fir_reqs.num_channels-1),
      C_CONST_2_VAL => 0

    )
    port map(
      A_IN       => chan_cnt,

      ADD_C0     => output_rate_max,
      ADD_C1     => chan_max,
      ADD_C2     => '0',

      C_IN       => '0',
      D          => chan_cnt,

      CE        => int_ce,
      SCLR      => clr,
      CLK       => clk
    );

    chanmax: cmp_carrychain
    generic map(
      width => max(1,log2_ext(fir_reqs.num_channels)),
      const => 1,
      value => fir_reqs.num_channels-2,
      c_family => c_family
    )
    port map(
      cin   => '1',
      A     => chan_cnt,
      B     => pad(max(1,log2_ext(fir_reqs.num_channels))-1 downto 0),
      cout  => chan_max_cout,
      Res   => open,
      sclr  => clr,
      ce    => int_ce,
      clk   => clk
    );

    chanmaxgen: MUXCY_L
    port map(
      LO   => chan_max_gen,

      CI  => chan_max_cout,
      DI  => chan_max,
      S   => chan_max_sel
    );

    chan_max_sel<=output_rate_max;

    process(clk)
    begin
        if (rising_edge(clk)) then
                if (clr='1') then
          chan_max<='0';
                elsif (int_ce='1') then
          chan_max<=chan_max_gen;
                end if;
        end if;

    end process;

  end generate gen_chan_cnt;

  gen_chan_const: if (fir_reqs.num_channels=1) generate
    chan_max<='0';
  end generate gen_chan_const;
end generate gen_intersym_output;
gen_oprate_norm: if  output_rate_count>1 generate

  outputrate: combined_priority_counter
  generic map(
    C_HAS_C_IN    => 0,
    C_WIDTH       => output_rate_width,
    C_CONST_0_VAL => 1,
    C_CONST_1_VAL => (2**output_rate_width)-(output_rate_count-1),
    C_CONST_2_VAL => 0

  )
  port map(
    A_IN       => output_rate,

    ADD_C0     => output_en,
    ADD_C1     => output_rate_max,
    ADD_C2     => '0',

    C_IN       => '0',
    D          => output_rate,

    CE        => int_ce,
    SCLR      => clr,
    CLK       => clk
  );

  outputratemax: cmp_carrychain
  generic map(
    width => output_rate_width,
    const => 1,
    value => output_rate_count-2,
    c_family => c_family
  )
  port map(
    cin   => output_en,
    A     => output_rate,
    B     => pad(output_rate_width-1 downto 0),
    cout  => open,
    Res   => output_rate_max,
    sclr  => clr,
    ce    => int_ce,
    clk   => clk
  );
end generate gen_oprate_norm;
gen_oprate_para: if output_rate_count=1 generate

  output_rate_max<=output_en;
end generate gen_oprate_para;
pad<=(others=>'0');
we_sel<=we_dly when (FIR_REQS.filter_type=c_interpolating_symmetry) else
        we;

d_sel<=
        d;
end;
