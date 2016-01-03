
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
use work.mac_fir_const_pkg.all;
use work.mac_fir_comp_pkg.all;
use work.mac_fir_utils_pkg.all;
library xilinxcorelib;
use xilinxcorelib.prims_utils_v9_0.all;
entity filter_block is
generic (
  C_FAMILY            : string;
  C_ELABORATION_DIR   : string := "";
  C_COMPONENT_NAME    : string := "";
  FIR_REQS            : t_reqs;
  FIR_DETAILS         : t_details;
  COL_MODE            : integer := 0;
  COL_1ST_LEN         : integer := 3;
  COL_WRAP_LEN        : integer := 4;
  COL_PIPE_LEN        : integer := 3;
  C_HAS_CE            : integer := 0
);
port (
  CLK                 : in  std_logic;
  CE                  : in  std_logic;
  RESET               : in  std_logic;
  WE                  : in  std_logic;
  WE_SYM              : in  std_logic;
  DIN                 : in  std_logic_vector(FIR_REQS.data_width-1 downto 0);
  DATA_ADDR_IN        : in std_logic_vector(UTILS_PKG_bits_needed_to_represent
                           (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
  DATA_SYM_ADDR_IN    : in std_logic_vector(UTILS_PKG_bits_needed_to_represent
                           (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
  COEF_ADDR_IN        : in std_logic_vector(UTILS_PKG_bits_needed_to_represent
                           (FIR_DETAILS.coef_mem_depth-1)-1 downto 0);
  WE_OUT              : out std_logic;
  WE_SYM_OUT          : out std_logic;
  PC_OUT              : out std_logic_vector(47 downto 0);
  P_OUT_LAST_MAC      : out std_logic_vector(47 downto 0);
  DATA_OUT_CENTRE_TAP : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
  SYM_ADDSUB_IN       : in std_logic;
  SYM_ADDSUB_OUT      : out std_logic;
  PHASE_END           : in std_logic;
  PHASE_START         : in std_logic;
  DOUT_SMAC           : out std_logic_vector(24 downto 0);
  COUT_SMAC           : out std_logic_vector(24 downto 0);
  COEF_LD           : in std_logic;
  COEF_WE           : in std_logic;
  COEF_DIN          : in std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  COEF_RELOAD_PAGE_IN  : in std_logic;
  COEF_DIN_MID      : out std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  COEF_WE_MID       : out std_logic;
  COEF_LD_COMPLETE  : out std_logic

);
end filter_block;
architecture synth of filter_block is
  constant d_addr_width : integer :=
           UTILS_PKG_bits_needed_to_represent(FIR_DETAILS.data_mem_depth-1);
  constant c_addr_width : integer :=
           UTILS_PKG_bits_needed_to_represent(FIR_DETAILS.coef_mem_depth-1);
  constant col_run_length : integer := COL_WRAP_LEN + COL_PIPE_LEN;
  constant num_wrap_pipes : integer := UTILS_PKG_select_integer(
                            1+(FIR_DETAILS.num_macs-COL_1ST_LEN-1)/COL_WRAP_LEN,
                            0,
                            FIR_DETAILS.num_macs <= COL_1ST_LEN);
  constant num_stages     : integer := FIR_DETAILS.num_macs
                                     + COL_MODE*num_wrap_pipes*COL_PIPE_LEN;
  constant num_outputs    : integer := num_stages-1;
  subtype  num_internal_stages  is natural range 0 to num_stages+3;
  subtype  num_stages_outputs   is natural range 0 to num_outputs+1
                                                      +UTILS_PKG_select_integer(
                                                        0,
                                                        1,
                                                        (FIR_REQS.coef_reload=1
                                                        and FIR_DETAILS.num_taps_per_mac_coefspace=1));
  constant MULTIPLY_OPCODE          : std_logic_vector(6 downto 0):="0000101";
  constant MULTIPLY_ADD_PCIN_OPCODE : std_logic_vector(6 downto 0):="0010101";
  constant MULTIPLY_ADD_CIN_OPCODE  : std_logic_vector(6 downto 0):="0110101";

  constant coef_reload_connect_step : integer:=UTILS_PKG_select_integer(
                                                0,
                                                1,
                                                (FIR_DETAILS.num_taps_per_mac_coefspace=1
                                                and FIR_REQS.coef_reload=1));
  type     we_array         is array (num_internal_stages)
                            of std_logic;
  type     data_array       is array (num_stages_outputs )
                            of std_logic_vector(FIR_REQS.data_width-1 downto 0);
  type     coef_array       is array (num_stages_outputs )
                            of std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  type     data_bc_array    is array (num_stages_outputs )
                            of std_logic_vector(29 downto 0);
  type     data_smac_array  is array (num_stages_outputs )
                            of std_logic_vector(24 downto 0);
  type     data_sym_array   is array (num_internal_stages)
                            of std_logic_vector(FIR_REQS.data_width-1 downto 0);
  type     pc_array         is array (num_internal_stages)
                            of std_logic_vector(47 downto 0);
  type     data_addr_array  is array (num_stages_outputs)
                            of std_logic_vector(d_addr_width-1 downto 0);
  type     data_sym_addr_array is array (num_stages_outputs)
                            of std_logic_vector(max(1,log2_ext
                            (FIR_DETAILS.datasym_mem_depth))-1 downto 0);
  type     coef_addr_array is array (num_stages_outputs)
                            of std_logic_vector(c_addr_width-1 downto 0);
  signal   filter_stage_we            : we_array;
  signal   filter_stage_we_ce         : we_array;
  signal   filter_stage_data          : data_array;
  signal   filter_stage_data_bc       : data_bc_array;
  signal   filter_stage_data_bc_piped : data_bc_array;
  signal   filter_stage_dout_mid      : data_array;
  signal   filter_stage_we_sym        : we_array;
  signal   filter_stage_data_sym      : data_sym_array;
  signal   filter_stage_p             : pc_array;
  signal   filter_stage_pc            : pc_array;
  signal   filter_stage_carry         : pc_array;
  signal   filter_stage_data_addr     : data_addr_array;
  signal   filter_stage_data_sym_addr : data_sym_addr_array;
  signal   filter_stage_coef_addr     : coef_addr_array;
  signal   filter_stage_data_smac     : data_smac_array;
  signal   filter_stage_coef_smac     : data_smac_array;

  signal   filter_stage_cntrl_extra1,
           filter_stage_cntrl_extra2,
           filter_stage_cntrl_extra3,
           filter_stage_first_phase  : we_array;
  signal   filter_stage_mid_tap       : std_logic_vector(fir_reqs.data_width-1 downto 0);
  signal  filter_stage_coef_we,
          filter_stage_coef_reload_page,
          filter_stage_coef_ld_en: we_array;
  signal  filter_stage_coef_data: coef_array;
  signal  coef_we_strm_toggle,
          coef_we_mid_pulse,
          c_addr_reload_max,
          coef_ld_en,
          coef_ld_en_strm2,
          coef_ld_complete_int
          : std_logic := '0';
  signal  coef_we_en,
          coef_we_en_strm2
          : std_logic := '1';

  signal  coef_we_gen,
          coef_we_gen_strm2:std_logic:='0';
  signal  coef_data_reg:std_logic_vector(FIR_REQS.coef_width-1 downto 0);
  signal  c_addr_reload: std_logic_vector(c_addr_width-2 downto 0):=(others=>'0');
  signal coef_we_mid_int:std_logic:='0';


  function col_connect(   mode: string;
                          current_stage:integer;
                          num_stages:integer;
                          fnc_COL_MODE:integer;
                          fnc_COL_1ST_LEN:integer;
                          fnc_COL_WRAP_LEN:integer;
                          fnc_COL_PIPE_LEN:integer;
                          num_strms: integer) return integer is
    variable  current_col,
              stage_pos,
              column_pos: integer:=1;
    variable  prev_col_dir,
              col_dir,
              match,
              mid_step,
              last,
              stages_sofar:integer:=0;
    variable  mac_src: integer:=-1;
  begin
    if (mode="connect_we"
        or mode="connect_en"
        or mode="last")
        and (num_strms=1) then
      if (fnc_COL_MODE=1) then

        while match=0 and stage_pos<=num_stages loop

          if (stage_pos=current_stage
              or current_stage=0) then
            if (current_stage=0) then
              stage_pos:=0;
            end if;
            match:=1;
            if (mode="last") then
              last:=0;
              if (stage_pos>fnc_COL_1ST_LEN and fnc_COL_MODE=1) then
                if (col_dir=0) then
                  if (stage_pos=num_stages) then
                    last:=1;
                  else
                    last:=0;
                  end if;
                else
                  stages_sofar:=(fnc_COL_1ST_LEN+fnc_COL_PIPE_LEN)
                       +(max(0,current_col-2)*(fnc_COL_WRAP_LEN+fnc_COL_PIPE_LEN));
                  if (num_stages-stages_sofar<=fnc_COL_WRAP_LEN and column_pos=1) then
                    last:=1;
                  else
                    last:=0;
                  end if;
                end if;
              else
                if (stage_pos= num_stages) then
                  last:=1;
                end if;
              end if;
            end if;
          end if;

          if (stage_pos<=fnc_COL_1ST_LEN) then
            if (stage_pos=fnc_COL_1ST_LEN) then
              if (mode="connect_en") then
                mac_src:=minf(num_stages,stage_pos+fnc_COL_WRAP_LEN+fnc_COL_PIPE_LEN);
              else
                mac_src:=stage_pos-1;
              end if;
              stage_pos:=stage_pos+fnc_COL_PIPE_LEN+1;
              current_col:=current_col+1;
              prev_col_dir:=0;
              col_dir:=1;
            else
              if (mode="connect_en") then
                mac_src:=stage_pos+1;
              else
                mac_src:=stage_pos-1;
              end if;
              stage_pos:=stage_pos+1;
            end if;
          else
            if (column_pos=fnc_COL_WRAP_LEN) then
              if (col_dir=1 and mode="connect_we") then
                mac_src:=stage_pos-fnc_COL_WRAP_LEN-fnc_COL_PIPE_LEN;
              elsif (col_dir=0 and mode="connect_en") then
                mac_src:=minf(num_stages,stage_pos+fnc_COL_WRAP_LEN+fnc_COL_PIPE_LEN);
              else
                if (mode="connect_en") then
                  if (col_dir=1) then
                    mac_src:=stage_pos-1;
                  else
                    mac_src:=stage_pos+1;
                  end if;
                else
                  if (col_dir=1) then
                    mac_src:=stage_pos+1;
                  else
                    mac_src:=stage_pos-1;
                  end if;
                end if;
              end if;
              current_col:=current_col+1;
              column_pos:=1;
              stage_pos:=stage_pos+fnc_COL_PIPE_LEN+1;
              if (col_dir=1) then
                col_dir:=0;
              else
                col_dir:=1;
              end if;
            elsif (column_pos=1) then
              if (col_dir=0 and mode="connect_we") then
                mac_src:=stage_pos-fnc_COL_WRAP_LEN-fnc_COL_PIPE_LEN;
              elsif (col_dir=1 and mode="connect_en") then
                mac_src:=minf(num_stages+1,stage_pos+fnc_COL_WRAP_LEN+fnc_COL_PIPE_LEN);
              else
                if (mode="connect_en") then
                  if (col_dir=1) then
                    mac_src:=stage_pos-1;
                  else
                    mac_src:=stage_pos+1;
                  end if;
                else
                  if (col_dir=1) then
                    if (stage_pos+1<=num_stages) then
                      mac_src:=stage_pos+1;
                    else
                      mac_src:=stage_pos-column_pos-fnc_COL_PIPE_LEN;
                    end if;
                  else
                    mac_src:=stage_pos-1;
                  end if;
                end if;
              end if;
              stage_pos:=stage_pos+1;
              column_pos:=column_pos+1;
            else
              if (mode="connect_en") then
                if (col_dir=1) then
                  mac_src:=stage_pos-1;
                else
                  mac_src:=stage_pos+1;
                end if;
              else
                if (col_dir=1) then
                  if (stage_pos+1<=num_stages) then
                    mac_src:=stage_pos+1;
                  else
                    mac_src:=stage_pos-column_pos-fnc_COL_PIPE_LEN;
                  end if;
                else
                  mac_src:=stage_pos-1;
                end if;
              end if;
              stage_pos:=stage_pos+1;
              column_pos:=column_pos+1;
            end if;
          end if;
        end loop;

      else
        match:=1;
        if not(mode="last") then
          if (mode="connect_en") then
            mac_src:=current_stage+1;
          else
            mac_src:=current_stage-1;
          end if;
        else
          last:=0;
          if (current_stage=num_stages) then
            last:=1;
          end if;
        end if;
      end if;
      if (mode="connect_en"
          or mode="connect_we") then
        if (match=1) then
          return minf(num_stages,mac_src);
        else
          report "COEF RELOAD ERROR: stage not matched" severity error;
          return 0;
        end if;
      else
        return last;
      end if;
    elsif (num_strms=2) then
        report "2 STREAM CALCS" severity note;
        if (mode="connect_we") then
          mid_step:=col_connect("connect_we",current_stage,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
          return col_connect("connect_we",mid_step,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
        elsif (mode="connect_en") then
          mid_step:=col_connect("connect_en",current_stage,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
          return col_connect("connect_en",mid_step,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
        elsif (mode="last") then
          mid_step:=col_connect("last",current_stage,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
          if (mid_step=1) then
            return 1;
          else
            mid_step:=col_connect("connect_en",current_stage,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
            return col_connect("last",mid_step,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
          end if;
        elsif (mode="strm2_bottom_ld") then
          return col_connect("connect_en",1,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
        elsif (mode="strm2_bottom_we") then
          mid_step:=col_connect("connect_en",1,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,1);
          return col_connect("connect_we",mid_step,num_stages,fnc_COL_MODE,fnc_COL_1ST_LEN,fnc_COL_WRAP_LEN,fnc_COL_PIPE_LEN,2);
        end if;
    else
      return 0;
    end if;
  end;
constant col_connect_tmp : integer := col_connect("strm2_bottom_we",1,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,2)+1;
  signal   gnd     : std_logic;
  signal   vcc     : std_logic;
  signal   gnd_bus : std_logic_vector(47 downto 0);
  signal data_out_last,
         data_out_last_latch
         : std_logic_vector(FIR_REQS.data_width-1 downto 0);
  signal int_ce    : std_logic;
  signal we_ce     : std_logic;
  signal  we_sel,
          we_sym_sel,
          cntrl_ex1_sel,
          cntrl_ex2_sel,
          cntrl_ex3_sel,
          cntrl_ex2_sel_src,
          cntrl_ex2_sel_src_dly,
          we_sym_dly,
          we_sym_dly2,
          we_sym_2ndlast_dly,
          we_dly,
          phase_end_dly,
          phase_start_dly,
          we_shift,
          we_shift_dly :std_logic:='0';

  signal  data_addr_dly,
          data_addr_sel : std_logic_vector(UTILS_PKG_bits_needed_to_represent
                                            (FIR_DETAILS.data_mem_depth-1)-1 downto 0):=(others=>'0');
  signal  coef_addr_dly,
          coef_addr_sel : std_logic_vector(UTILS_PKG_bits_needed_to_represent
                                            (FIR_DETAILS.coef_mem_depth-1)-1 downto 0):=(others=>'0');
  signal  data_sym_addr_dly,
          data_sym_addr_sel : std_logic_vector(UTILS_PKG_bits_needed_to_represent
                                            (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0):=(others=>'0');
  signal  data_in_dly,
          data_in_sel,
          shuffle_src   : std_logic_vector(FIR_REQS.data_width-1 downto 0);
  constant first_phase_odd_sym_init_val: std_logic_vector(0 downto 0):=
                                            std_logic_vector(
                                              to_unsigned(
                                                UTILS_PKG_select_integer(
                                                  0,1,
                                                  FIR_DETAILS.odd_symmetry=1
                                                  and FIR_DETAILS.deci_rate=2
                                                  and FIR_DETAILS.num_taps_per_mac<=3
                                                  and FIR_REQS.num_channels=1),1));
  signal  first_phase_odd_sym: std_logic:=first_phase_odd_sym_init_val(0);
  signal  reset_dly: std_logic;

  signal  flip_buffer_out: std_logic_vector(FIR_REQS.data_width-1 downto 0);
  signal  flip_buffer_out_valid,
          basedly_we_ce:std_logic;
begin
  gnd     <= '0';
  vcc     <= '1';
  gnd_bus <= X"000000000000";
  gce1 : if C_HAS_CE=0 generate
  begin
    int_ce <= '1';
  end   generate;
  gce2 : if C_HAS_CE/=0 generate
  begin
    int_ce <= ce;
  end   generate;
  we_ce <= we and int_ce;

  cntrl_ex1_sel<= SYM_ADDSUB_IN when (FIR_REQS.filter_type=c_interpolating_symmetry) else
                  we_sym;

  filter_stage_cntrl_extra1(0)      <= cntrl_ex1_sel;
  mac_stages: for i in 1 to num_stages generate
    constant num_pipes_sofar : integer := UTILS_PKG_select_integer(
                                          1+(i-COL_1ST_LEN-1)/col_run_length,
                                          0, i<=COL_1ST_LEN or COL_MODE=0);
    constant stage_num_nopipe : integer := i-num_pipes_sofar*COL_PIPE_LEN;
    constant num_strms:integer:=UTILS_PKG_select_integer(1,2,(FIR_DETAILS.num_taps_per_mac_coefspace=1
                                                              and FIR_REQS.coef_reload=1));
  begin

    g_stage : if COL_MODE = 0
              or (i-1) mod col_run_length <  COL_1ST_LEN
              or (i-1) mod col_run_length >= COL_1ST_LEN+COL_PIPE_LEN
              generate
    begin
      mac: filter_stage
      generic map (
        C_FAMILY          => C_FAMILY,
        FIR_REQS          => FIR_REQS,
        FIR_DETAILS       => FIR_DETAILS,
        COL_TOP           => UTILS_PKG_select_integer(0,1, COL_MODE=1 and
                            (i mod col_run_length = COL_1ST_LEN) ),
        COL_BOTTOM        => UTILS_PKG_select_integer(0,1, COL_MODE=1 and
                             ((i-COL_1ST_LEN-COL_PIPE_LEN) mod col_run_length = 1) ),
        LAST_STAGE        => UTILS_PKG_select_integer(0,1,i=num_stages),
        STAGE_NUM         => stage_num_nopipe,
        C_ELABORATION_DIR => C_ELABORATION_DIR,
        MIF_FILE          => C_COMPONENT_NAME
                           & "COEFF_auto"
                           & int_to_str(stage_num_nopipe-1)
                           & ".mif",
        LAST_COEF_STAGE   => col_connect("last",i,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,num_strms),
        C_HAS_CE          => C_HAS_CE
      )
      port map (
        clk               => clk,
        ce                => int_ce,
        reset             => reset,
        we                => filter_stage_we(i-1),
        we_sym            => filter_stage_we_sym(i-1),
        din               => filter_stage_data(i-1),
        din_sym           => filter_stage_data_sym(i+
                             FIR_DETAILS.filter_stage_struct.input_data_sym_src),
        data_addr_in      => filter_stage_data_addr(i-1),
        data_sym_addr_in  => filter_stage_data_sym_addr(i-1),
        coef_addr_in      => filter_stage_coef_addr(i-1),
        pc_in             => filter_stage_pc(i-1),
        carry_in          => filter_stage_carry(i-1),
        din_casc          => filter_stage_data_bc(i-1),
        we_out            => filter_stage_we(i),
        we_out_sym        => filter_stage_we_sym(i),
        dout              => filter_stage_data(i),
        dout_sym          => filter_stage_data_sym(i),
        dout_mid          => filter_stage_dout_mid(i),
        p_out             => filter_stage_p(i),
        pc_out            => filter_stage_pc(i),
        data_addr_out     => filter_stage_data_addr(i),
        data_sym_addr_out => filter_stage_data_sym_addr(i),
        coef_addr_out     => filter_stage_coef_addr(i),
        dout_casc         => filter_stage_data_bc(i),
        dout_smac         => filter_stage_data_smac(i),
        cout_smac         => filter_stage_coef_smac(i),
        coef_ld         => COEF_LD,
        coef_we_in      => filter_stage_coef_we(col_connect("connect_we",i,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,num_strms)
                                                +num_strms-1),
        coef_we_out     => filter_stage_coef_we(i+num_strms-1),
        coef_ld_en_in   => filter_stage_coef_ld_en(col_connect("connect_en",i,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,num_strms)
                                                   +num_strms-1),
        coef_ld_en_out  => filter_stage_coef_ld_en(i+num_strms-1),
        coef_din        => filter_stage_coef_data(col_connect("connect_we",i,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,num_strms)
                                                  +num_strms-1),
        coef_dout       => filter_stage_coef_data(i+num_strms-1),
        coef_reload_page_in  => filter_stage_coef_reload_page(col_connect("connect_we",i,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,num_strms)
                                                +num_strms-1),
        coef_reload_page_out => filter_stage_coef_reload_page(i+num_strms-1),

        cntrl_extra1_in   => filter_stage_cntrl_extra1(i-1),
        cntrl_extra2_in   => filter_stage_cntrl_extra2(i-1),
        cntrl_extra3_in   => filter_stage_cntrl_extra3(i-1),
        cntrl_extra1_out  => filter_stage_cntrl_extra1(i),
        cntrl_extra2_out  => filter_stage_cntrl_extra2(i),
        cntrl_extra3_out  => filter_stage_cntrl_extra3(i),
        first_phase       => filter_stage_first_phase(i)
      );

      gen_mid_tap: if ( stage_num_nopipe=FIR_DETAILS.mac_mid_tap) generate
        filter_stage_mid_tap<=filter_stage_dout_mid(i);
        filter_stage_first_phase(i)<=first_phase_odd_sym;
      end generate;
    end generate g_stage;

   end generate mac_stages;

  SYM_ADDSUB_OUT<=filter_stage_cntrl_extra1(num_stages-1);
  gen_coef_reload: if (FIR_REQS.coef_reload=1) generate

    gen_cntrl: process(clk)
      begin
        if (rising_edge(CLK) and int_ce='1') then
          if (coef_ld='1') then
            coef_data_reg<=(others=>'0');
            c_addr_reload<=(others=>'0');
            coef_we_en<='1';
            coef_we_gen<='0';
            coef_we_en_strm2<='1';
            coef_we_gen_strm2<='0';
            coef_we_strm_toggle<='0';
            coef_we_mid_pulse<='0';

            coef_we_mid_int<='0';
            c_addr_reload_max<='0';

            coef_ld_complete_int<='0';

          else
            coef_data_reg<=COEF_DIN;
            if (COEF_WE='1') then
              if (c_addr_reload_max='1') then
                c_addr_reload<=(others=>'0');
              else
                c_addr_reload<=std_logic_vector(
                                      unsigned(c_addr_reload)
                                      +1);
              end if;
            end if;
            if(FIR_DETAILS.num_taps_per_mac_coefspace>1) then
              if (COEF_WE='1') then
                if (c_addr_reload(c_addr_width-2 downto 0)
                    =std_logic_vector(
                      to_unsigned((FIR_DETAILS.num_taps_per_mac_coefspace)-2,
                                  c_addr_width-1)))then
                  c_addr_reload_max<='1';
                else
                  c_addr_reload_max<='0';
                end if;
              end if;
            else
                c_addr_reload_max<='1';
           end if;
            if (FIR_DETAILS.num_taps_per_mac_coefspace>1) then
              coef_we_gen<=COEF_WE and coef_we_en;
              if (  COEF_WE='1'
                    and c_addr_reload_max='1'
                    and coef_ld_en='1') then
                coef_we_en<='0';
              end if;
            else
              if (COEF_WE='1') then
                coef_we_strm_toggle<=not coef_we_strm_toggle;
              end if;
              if (FIR_DETAILS.num_macs rem 2 >0) then
                coef_we_gen<=COEF_WE and coef_we_en and not coef_we_strm_toggle;
                coef_we_gen_strm2<=COEF_WE and coef_we_en_strm2 and coef_we_strm_toggle;

                if (  COEF_WE='1'
                      and coef_we_strm_toggle='0'
                      and coef_ld_en='1') then

                  coef_we_en<='0';
                end if;

                if (  COEF_WE='1'
                      and coef_we_strm_toggle='1'
                      and coef_ld_en_strm2='1') then

                  coef_we_en_strm2<='0';
                end if;
              else
                coef_we_gen<=COEF_WE and coef_we_en and coef_we_strm_toggle;
                coef_we_gen_strm2<=COEF_WE and coef_we_en_strm2 and not coef_we_strm_toggle;

                if (  COEF_WE='1'
                      and coef_we_strm_toggle='1'
                      and coef_ld_en='1') then

                  coef_we_en<='0';
                end if;

                if (  COEF_WE='1'
                      and coef_we_strm_toggle='0'
                      and coef_ld_en_strm2='1') then

                  coef_we_en_strm2<='0';
                end if;
              end if;
            end if;
            if ( (FIR_REQS.filter_type = c_halfband_transform
                or  FIR_REQS.filter_type = c_decimating_half_band
                or  FIR_REQS.filter_type = c_interpolating_half_band)
                and not (FIR_DETAILS.num_macs=1 and FIR_DETAILS.single_mac_modifier=0) ) then
              coef_we_mid_pulse<=coef_we_mid_pulse or (COEF_WE and not coef_we_en);
              coef_we_mid_int<=COEF_WE and not coef_we_en and not coef_we_mid_pulse;
            end if;
            coef_ld_complete_int<=not coef_we_en;
          end if;
        end if;
      end process;
      COEF_WE_MID<=coef_we_mid_int;
      gen_map:if (FIR_DETAILS.num_taps_per_mac_coefspace>1) generate
        filter_stage_coef_data(0)<=coef_data_reg;
        filter_stage_coef_we(0)<=coef_we_gen;
        coef_ld_en<=filter_stage_coef_ld_en(1);
        filter_stage_coef_reload_page(0)<=COEF_RELOAD_PAGE_IN;
      end generate  gen_map;
      gen_map_1tpm: if (FIR_DETAILS.num_taps_per_mac_coefspace=1) generate
        filter_stage_coef_data(0)<=coef_data_reg;
        filter_stage_coef_we(0)<=coef_we_gen;
        coef_ld_en<=filter_stage_coef_ld_en(2);
        filter_stage_coef_reload_page(0)<=COEF_RELOAD_PAGE_IN;
        gen_strm2_connect: if (FIR_DETAILS.num_macs>1) generate
          coef_ld_en_strm2<=filter_stage_coef_ld_en(col_connect("strm2_bottom_ld",1,num_stages,COL_MODE,COL_1ST_LEN,COL_WRAP_LEN,COL_PIPE_LEN,2)+1);
          filter_stage_coef_data(col_connect_tmp)<=coef_data_reg;
          filter_stage_coef_we(col_connect_tmp)<=coef_we_gen_strm2;
          filter_stage_coef_reload_page(col_connect_tmp)<=COEF_RELOAD_PAGE_IN;
        end generate gen_strm2_connect;
      end generate gen_map_1tpm;
      COEF_DIN_MID<=coef_data_reg;
      COEF_LD_COMPLETE<=coef_we_mid_pulse
                    when ( (FIR_REQS.filter_type = c_halfband_transform
                      or  FIR_REQS.filter_type = c_decimating_half_band
                      or  FIR_REQS.filter_type = c_interpolating_half_band)
                      and not (FIR_DETAILS.num_macs=1 and FIR_DETAILS.single_mac_modifier=0) )
                    else coef_ld_complete_int;
  end generate gen_coef_reload;
end synth;
