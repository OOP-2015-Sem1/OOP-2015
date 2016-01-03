
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
use work.sim_pkg.all;
use work.model_pkg.all;

entity fir_compiler_v3_0_model is
generic (
  C_FAMILY            : string  := "virtex4";
  C_XDEVICEFAMILY     : string  := "virtex4";
  C_ELABORATION_DIR   : string  := "./";
  C_COMPONENT_NAME    : string  := "fir";
  COE_FILE            : string  := "coeff.coe";
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
  OUTPUT_WIDTH        : integer := 48;
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
end entity fir_compiler_v3_0_model;

architecture behavioural of fir_compiler_v3_0_model is

constant clocks_per_sample : integer := integer(clock_freq/sample_freq)/num_channels;
constant clks_per_samp_int : integer := clocks_per_sample*NUM_CHANNELS;

constant fir_model_reqs_v3 : t_reqs := (
        family              => family_val(select_string(
                                            C_FAMILY,
                                            "spartan3adsp",
                                            C_XDEVICEFAMILY="spartan3adsp")),
        filter_type         => FILTER_TYPE,
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
        output_width        => OUTPUT_WIDTH,
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
constant properties : t_syst_mac_fir_properties := fir_v3_prop(fir_model_reqs_v3);


constant max_int_width : integer :=  31;
constant maxint        : integer :=    2**(max_int_width-1) - 1;
constant minint        : integer := -1*2**(max_int_width-1);

constant buffer_fudge : integer := properties.full_taps;
constant buffer_depth : integer := properties.zpf*(properties.full_taps+properties.cascade_dly+buffer_fudge);

constant sym_padding     : integer := ((properties.full_taps-fir_model_reqs_v3.num_taps)/2);
constant inter_sym_shift : integer:=select_integer(0,fir_model_reqs_v3.inter_rate/2,
                                                  fir_model_reqs_v3.filter_type=c_polyphase_interpolating and
                                                  properties.symmetry=1 and properties.odd_symmetry=1 and
                                                  (fir_model_reqs_v3.inter_rate rem 2 >0));

constant pad_offset      : integer := select_integer(0,sym_padding-inter_sym_shift,properties.symmetry=1);

constant buff_in_depth  : integer := properties.ipbuff_depth;

constant rdy_dly_len    : integer := select_integer(
                                     properties.cascade_dly-3,
                                     properties.cascade_dly-4,
                                     properties.opbuff_depth>0);


constant buff_out_depth : integer := (properties.opbuff_depth);

constant regressor_len  : integer := properties.zpf*properties.full_taps;

constant pointer_start : integer := (properties.full_taps-1)*properties.zpf;

constant split_accum : boolean := OUTPUT_WIDTH > max_int_width;


constant split_cwidth : integer := get_min( (max_int_width-DATA_WIDTH) , COEF_WIDTH );

constant cpages : integer := (COEF_WIDTH+split_cwidth-1)/split_cwidth;

constant signed_output : boolean := ((COEF_TYPE=0) or (DATA_TYPE=0));

constant effective_cwidth : integer := select_integer(COEF_WIDTH, COEF_WIDTH-1,
                                                      FILTER_TYPE=c_polyphase_interpolating and
                                                      (SYMMETRY=1 or NEG_SYMMETRY=1));

constant set_index : integer := (regressor_len-1)-((regressor_len-1) mod DECIM_RATE);


type t_coefficients is array (0 to properties.full_taps-1) of integer;
type t_coeff_pages  is array (0 to               cpages-1) of t_coefficients;
type t_coeff_array  is array (0 to            NUM_FILTS-1) of t_coeff_pages;

type t_regressor      is array (0 to        regressor_len-1) of integer;

type t_buffer       is array (0 to         buffer_depth-1) of integer;
type t_buffer_array is array (0 to         NUM_CHANNELS-1) of t_buffer;

type t_pointers     is array (0 to         NUM_CHANNELS-1) of integer;

type t_rdy_dly      is array (0 to          rdy_dly_len-1) of std_logic;




function fir ( c            : t_coefficients;
               d            : t_regressor;
               taps,
               zpf          : integer
             ) return integer is

  variable acc : integer := 0;

begin

  for i in 0 to (taps-1) loop

    acc := acc + d(i*zpf) * c(taps-1-i);


  end loop;

  return acc;

end;


function fir_ovfl_unsigned ( c            : t_coefficients;
                             d            : t_regressor;
                             taps,
                             zpf,
                             result_width : integer
                           ) return unsigned is

  variable inc      : natural  := 0;
  variable acc      : natural  := 0;
  variable acc_ovfl : natural  := 0;
  variable result   : unsigned(result_width-1 downto 0) := (others=>'0');

begin


  for i in 0 to (taps-1) loop

    inc := d(i*zpf) * c(taps-1-i);

    if inc>(maxint-acc) then
      acc      := acc+inc-(maxint+1);
      acc_ovfl := acc_ovfl+1;

    else
      acc := acc+inc;
    end if;

  end loop;

    result((max_int_width-1)-1 downto 0)            := to_unsigned(acc, (max_int_width-1));
    result(result_width-1 downto (max_int_width-1)) := to_unsigned(acc_ovfl, result_width-(max_int_width-1));

  return result;


end;


function fir_ovfl_signed ( c            : t_coefficients;
                           d            : t_regressor;
                           taps,
                           zpf,
                           result_width : integer
                         ) return signed is

  variable inc      : integer  := 0;
  variable acc      : natural := 0;
  variable acc_ovfl : integer  := 0;
  variable result   : signed(result_width-1 downto 0) := (others=>'0');

begin


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


    result((max_int_width-1)-1 downto 0)           := signed(to_unsigned(acc, (max_int_width-1)));
    result(result_width-1 downto (max_int_width-1)) := to_signed(acc_ovfl, result_width-(max_int_width-1));

  return result;

end;


function split_fir_unsigned ( c            : t_coeff_pages;
                              d            : t_regressor;
                              cpages,
                              taps,
                              zpf,
                              coef_width,
                              split_cwidth,
                              result_width : integer
                            ) return unsigned is

  variable inc          : unsigned(result_width-1 downto 0) := (others=>'0');
  variable acc          : unsigned(result_width-1 downto 0) := (others=>'0');
  variable read_width   : integer := split_cwidth;
  variable remaining    : integer := coef_width;

begin

  for i in 0 to cpages-1 loop
    read_width := get_min(split_cwidth,remaining);
    if (result_width>coef_width-remaining+max_int_width-(DATA_TYPE*COEF_TYPE)) then
      inc := shift_left( fir_ovfl_unsigned ( c(i), d, taps, zpf, result_width  ) , coef_width-remaining ) ;
    else
      inc := shift_left( to_unsigned( fir( c(i), d, taps, zpf ) , result_width ) , coef_width-remaining ) ;
    end if;
    acc := acc + inc;
    remaining := remaining - read_width;
  end loop;

  return acc;

end;


function split_fir_signed ( c            : t_coeff_pages;
                            d            : t_regressor;
                            cpages,
                            taps,
                            zpf,
                            coef_width,
                            split_cwidth,
                            result_width : integer
                          ) return signed is

  variable inc1         : signed(result_width-1 downto 0) := (others=>'0');
  variable inc2         : signed(result_width-1 downto 0) := (others=>'0');
  variable acc          : signed(result_width-1 downto 0) := (others=>'0');
  variable read_width   : integer := split_cwidth;
  variable remaining    : integer := coef_width;

begin

  for i in 0 to cpages-1 loop
    read_width := get_min(split_cwidth,remaining);
    if (result_width>coef_width-remaining+max_int_width-(DATA_TYPE*COEF_TYPE)) then
      inc1 := fir_ovfl_signed ( c(i), d, taps, zpf, result_width  );
      inc2 := (others=>'0');
      inc2(result_width-1 downto coef_width-remaining) := inc1(result_width-1-(coef_width-remaining) downto 0) ;
    else
      inc1 := to_signed( fir( c(i), d, taps, zpf ) , result_width );
      inc2 := (others=>'0');
      inc2(result_width-1 downto coef_width-remaining) := inc1(result_width-1-(coef_width-remaining) downto 0) ;
    end if;
    acc := acc + inc2;
    remaining := remaining - read_width;
  end loop;

  return acc;

end;




function read_miffile_data ( filename         : string;
                             num_taps         : integer;
                             full_taps        : integer;
                             pad_offset       : integer;
                             num_filts        : integer;
                             signed           : boolean;
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
        coeffs(i)(k)(j+pad_offset) := bin_to_int(binstring(start to remaining),read_width,(signed and k=(cpages-1)));
        remaining := remaining - read_width;
      end loop;
    end loop;
  end loop;

  file_close(filepointer);

  return coeffs;

end read_miffile_data;






signal coeff_sets             : t_coeff_array
                              := read_miffile_data(
                                                   C_MEM_INIT_FILE,
                                                   NUM_TAPS,
                                                   properties.full_taps,
                                                   pad_offset,
                                                   NUM_FILTS,
                                                   COEF_TYPE=0,
                                                   COEF_WIDTH,
                                                   split_cwidth,
                                                   cpages
                                                  );

signal int_sclr               : std_logic := '0';
signal int_ce                 : std_logic := '0';

signal set                    : integer := 0;
signal channel_in             : integer := 0;
signal channel_out            : integer := 0;

signal channel_data           : t_buffer_array := (others=>(others=>0));
signal channel_fsel           : t_buffer_array := (others=>(others=>0));
signal data_slice             : t_regressor    := (others=>0);

signal push_regressor         : boolean := false;
signal pop_regressor          : boolean := false;
signal next_data              : integer := 0;
signal next_data_fsel         : integer := 0;
signal result                 : std_logic_vector(OUTPUT_WIDTH-1 downto 0) := (others=>'0');
signal regout                 : std_logic_vector(OUTPUT_WIDTH-1 downto 0) := (others=>'0');
signal result_i               : std_logic_vector(  DATA_WIDTH-1 downto 0) := (others=>'0');
signal regout_i               : std_logic_vector(  DATA_WIDTH-1 downto 0) := (others=>'0');

signal int_out                : std_logic := '0';
signal dec_out                : std_logic := '0';

signal rfd_int                : std_logic := '1';

signal cascade_in             : std_logic := '0';
signal cascade_alt            : std_logic := '0';
signal rdy_dly                : t_rdy_dly := (others=>'0');
signal cascade_out            : std_logic := '0';
signal rdy_early              : std_logic := '0';
signal rdy_int                : std_logic := '0';

begin

  int_sclr    <= SCLR when C_HAS_SCLR = 1 else '0';
  int_ce      <= CE   when C_HAS_CE   = 1 else '1';

  next_data      <= to_integer(signed(DIN)) when DATA_TYPE=0 else to_integer(unsigned(DIN));
  next_data_fsel <= to_integer(unsigned(FILTER_SEL));
  push_regressor <= true when (ND='1' or C_HAS_ND=0) and rfd_int='1'  else false;
  pop_regressor  <= true when rdy_early='1'                           else false;


  new_data: process (clk)
    variable pointers  : t_pointers := (others=>pointer_start);
    variable rst_shift : t_pointers := (others=>0);
  begin

    if (rising_edge(clk)) then
      if int_sclr='1' then
        channel_in  <= 0;
        channel_out <= 0;
        for j in 0 to NUM_CHANNELS-1 loop
          for i in 0 to buffer_depth-rst_shift(j)-1 loop
            channel_data(j)(i) <= channel_data(j)(i+rst_shift(j));
            channel_fsel(j)(i) <= channel_fsel(j)(i+rst_shift(j));
          end loop;
        end loop;
        pointers  := (others=>pointer_start);
        rst_shift := (others=>0);
      elsif (int_ce='1') then
        if pop_regressor then

          for i in 0 to buffer_depth-DECIM_RATE-1 loop
            channel_data(channel_out)(i) <= channel_data(channel_out)(i+DECIM_RATE);
            channel_fsel(channel_out)(i) <= channel_fsel(channel_out)(i+DECIM_RATE);
          end loop;

          pointers(channel_out)  := pointers(channel_out) - DECIM_RATE;

          for i in 0 to DECIM_RATE-1 loop
            if rst_shift(channel_out)=0 then
              rst_shift(channel_out) := INTERP_RATE-1;
            else
              rst_shift(channel_out) := rst_shift(channel_out) - 1;
            end if;
          end loop;

          if channel_out=NUM_CHANNELS-1 then
            channel_out <= 0;
          else
            channel_out <= channel_out+1;
          end if;

        end if;
        if push_regressor then

          channel_data(channel_in)(pointers(channel_in)-DECIM_RATE+1) <= next_data;
          channel_fsel(channel_in)(pointers(channel_in)-DECIM_RATE+1) <= next_data_fsel;

          for i in 1 to INTERP_RATE-1 loop
            channel_data(channel_in)(pointers(channel_in)+i) <= 0;
            channel_fsel(channel_in)(pointers(channel_in)+i) <= next_data_fsel;
          end loop;

          pointers(channel_in)  := pointers(channel_in)  + INTERP_RATE;

          if channel_in=NUM_CHANNELS-1 then
            channel_in <= 0;
          else
            channel_in <= channel_in+1;
          end if;

        end if;
      end if;

    end if;

  end process;



  data_slice <= t_regressor(channel_data(channel_out)(0 to regressor_len-1));
  set        <= channel_fsel(channel_out)(set_index);

  gfir1: if not split_accum and not signed_output generate
  begin

    result <= std_logic_vector(
                to_unsigned (
                  fir (
                    coeff_sets(set)(0),
                    data_slice,
                    properties.full_taps,
                    properties.zpf ),
                  OUTPUT_WIDTH
                )
              );

  end generate;

  gfir2: if not split_accum and signed_output generate
  begin

    result <= std_logic_vector(
                to_signed (
                  fir (
                    coeff_sets(set)(0),
                    data_slice,
                    properties.full_taps,
                    properties.zpf ),
                  OUTPUT_WIDTH
                )
              );

  end generate;

  gfir3: if split_accum and not signed_output generate
  begin

    result <= std_logic_vector(
                split_fir_unsigned(
                  coeff_sets(set),
                  data_slice,
                  cpages,
                  properties.full_taps,
                  properties.zpf,
                  coef_width,
                  split_cwidth,
                  OUTPUT_WIDTH
                )
              );

  end generate;

  gfir4: if split_accum and signed_output generate
  begin

    result <= std_logic_vector(
                split_fir_signed(
                  coeff_sets(set),
                  data_slice,
                  cpages,
                  properties.full_taps,
                  properties.zpf,
                  coef_width,
                  split_cwidth,
                  OUTPUT_WIDTH
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

  ghilb1: if FILTER_TYPE/=c_hilbert_transform generate
  begin
    DOUT   <= regout when OUTPUT_REG=1 or rdy_int='1' else (others=>'X');
    DOUT_Q <= (others=>'X');
    DOUT_I <= (others=>'X');
  end generate;

  ghilb2: if FILTER_TYPE=c_hilbert_transform generate
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






    ib2cnt: process (clk)
      variable ipbuff_wrcnt     : integer   := 0;
      variable ipbuff_rdcnt     : integer   := 0;
      variable ipbuff_rden      : boolean   := false;
    begin
      if (rising_edge(clk)) then
        if (int_sclr='1') then
          ipbuff_wrcnt := 0;
          ipbuff_rdcnt := 0;
          ipbuff_rden  := false;
          cascade_alt    <= '0';
        elsif (int_ce='1') then

          if ipbuff_rden then
            if ipbuff_rdcnt < (buff_in_depth*INTERP_RATE*properties.ipbuff_rate-1) then
              ipbuff_rdcnt := ipbuff_rdcnt + 1;
            else
              ipbuff_rdcnt := 0;
              ipbuff_rden  := false;
            end if;
          end if;

          if (push_regressor) then
            if ipbuff_wrcnt = properties.ipbuff_thresh then
              ipbuff_rden  := true;
            end if;
            if ipbuff_wrcnt < (buff_in_depth-1) then
              ipbuff_wrcnt := ipbuff_wrcnt + 1;
            else
              ipbuff_wrcnt := 0;
            end if;
          end if;

          if ipbuff_rden and (ipbuff_rdcnt mod properties.ipbuff_rate)=0 then
            cascade_alt <= '1';
          else
            cascade_alt <= '0';
          end if;

        end if;
      end if;
    end process;


  cascade_in <= cascade_alt;


  casc_dly1: process (clk)
  begin
    if (rising_edge(clk)) then
      if int_sclr='1' then
        rdy_dly <= (others=>'0');
      elsif (int_ce='1') then
        rdy_dly(1 to rdy_dly_len-1) <= rdy_dly(0 to rdy_dly_len-2);
        rdy_dly(0)                  <= cascade_in;
      end if;
    end if;
  end process;

  cascade_out <= rdy_dly(rdy_dly_len-1);


  dec_cnt: process (clk)
    variable dec_count       : integer := 0;
    constant dec_threshold   : integer := select_integer(DECIM_RATE-1, 0,
                                                         FILTER_TYPE=c_decimating_half_band);
  begin
    if (rising_edge(clk)) then
      if (int_sclr='1') then
        dec_count := 0;
        dec_out   <= '0';
      elsif (int_ce='1') then
        if (cascade_out='1') then
          if dec_count = dec_threshold then
            dec_out   <= '1';
          else
            dec_out   <= '0';
          end if;
          if dec_count < (DECIM_RATE-1) then
            dec_count := dec_count + 1;
          else
            dec_count := 0;
          end if;
        else
          dec_out <= '0';
        end if;
      end if;
    end if;
  end process;

  ob1: if buff_out_depth=0 generate

    rdy_early <= dec_out;

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

          if (dec_out='1') then
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


  CHAN_IN  <= std_logic_vector(to_unsigned(channel_in, CHAN_SEL_WIDTH));


  rfd_cnt: process (clk)
    variable rfd_count    : integer := 0;
    variable rfd_cnt_en   : boolean := false;
  begin
    if (rising_edge(clk)) then
      if (int_sclr='1') then
        rfd_cnt_en   := false;
        rfd_count    := 0;
        rfd_int      <= '1';
      elsif (int_ce='1') then
        if push_regressor and properties.clk_per_chan>1 then
          rfd_cnt_en   := true;
          rfd_int      <= '0';
        end if;
        if rfd_cnt_en and rfd_count < properties.clk_per_chan then
          rfd_count    := rfd_count + 1;
        end if;
        if (rfd_cnt_en and rfd_count = properties.clk_per_chan) or properties.clk_per_chan = 1 then
          rfd_cnt_en := false;
          rfd_count  := 0;
          rfd_int    <= '1';
        end if;
      end if;
    end if;
  end process;

  RFD <= rfd_int;


end behavioural;
