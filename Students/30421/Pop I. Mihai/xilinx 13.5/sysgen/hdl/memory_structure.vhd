
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
use work.mac_fir_func_pkg.all;
entity memory_structure is
generic (
  C_FAMILY          : string;
  fir_reqs          : t_reqs;
  fir_details       : t_details;
  C_ELABORATION_DIR : string;
  mif_file          : string;
  LAST_COEF_STAGE   : integer;
  C_HAS_CE          : integer
);
port (
  clk         : in  std_logic;
  ce          : in  std_logic;
  reset       : in  std_logic;
  c_addr      : in  std_logic_vector(max(log2_ext(fir_details.coef_mem_depth),1)-1 downto 0);
  d_addr      : in  std_logic_vector(max(log2_ext(fir_details.data_mem_depth),1)-1 downto 0);
  d_sym_addr  : in  std_logic_vector(max(log2_ext(fir_details.datasym_mem_depth),1)-1 downto 0);
  coef_out    : out std_logic_vector(fir_reqs.coef_width-1 downto 0);
  data_in     : in  std_logic_vector(fir_reqs.data_width-1 downto 0);
  data_out    : out std_logic_vector(fir_reqs.data_width-1 downto 0);
  we          : in  std_logic;
  datasym_in  : in  std_logic_vector(fir_reqs.data_width-1 downto 0);
  datasym_out : out std_logic_vector(fir_reqs.data_width-1 downto 0);
  we_sym      : in  std_logic;
  coef_ld         : in std_logic;
  coef_we_in      : in std_logic;
  coef_we_out     : out std_logic;
  coef_ld_en_in   : in std_logic;
  coef_ld_en_out  : out std_logic;
  coef_din        : in std_logic_vector(fir_reqs.coef_width-1 downto 0);
  coef_dout       : out std_logic_vector(fir_reqs.coef_width-1 downto 0);
  coef_reload_page_in  : in std_logic;
  coef_reload_page_out : out std_logic
);
end memory_structure;
architecture synth of memory_structure is
constant use_dist_mem_gen : integer := 1;
constant use_blk_mem_gen  : integer := 1;

constant mif_file_fullpath : string := C_ELABORATION_DIR & mif_file;
signal vcc,gnd : std_logic;
signal gnd_bus : std_logic_vector(47 downto 0);
constant d_addr_width    : integer := max(1,log2_ext(fir_details.data_mem_depth));
constant c_addr_width    : integer := max(1,log2_ext(fir_details.coef_mem_depth));
constant dsym_addr_width : integer := max(1,log2_ext(fir_details.datasym_mem_depth));
constant max_local_width : integer := max(fir_reqs.data_width,fir_reqs.coef_width);
signal data_comb_ip,
       coef_comb_out,
       data_comb_out
       : std_logic_vector(max(fir_reqs.data_width,fir_reqs.coef_width)-1 downto 0);
signal data_comb_addr
       : std_logic_vector(UTILS_PKG_select_integer(c_addr_width, dsym_addr_width,
                         (FIR_DETAILS.data_combined=1))-1 downto 0);
signal  c_addr_reload: std_logic_vector(c_addr_width-1 downto 0):=(others=>'0');
signal  c_addr_reload_page: std_logic:='1';
signal  c_addr_reload_max,
        coef_we_dly,
        coef_ld_en_out_int: std_logic:='0';

signal  reload_en_int,
        enable_we_out:std_logic:='1';
signal  coef_we_gen: std_logic:='0';
signal  coef_dout_ram,
        coef_dout_reg : std_logic_vector(fir_reqs.coef_width-1 downto 0);

constant depth_min : integer := 16;
constant addr_min  : integer := 4;
constant dist_mem_addr_width: integer:=UTILS_PKG_select_integer(c_addr_width,
                                                                addr_min,
                                                                FIR_DETAILS.coef_mem_depth<depth_min);
signal  c_addr_pad,
        c_addr_reload_pad : std_logic_vector( dist_mem_addr_width-1 downto 0);
constant data_dist_mem_addr_width: integer:=UTILS_PKG_select_integer(d_addr_width,
                                                                     addr_min,
                                                                     FIR_DETAILS.data_mem_depth<depth_min);
signal d_addr_dly     : std_logic_vector(max(log2_ext(fir_details.data_mem_depth),1)-1 downto 0);
signal d_addr_pad     : std_logic_vector( data_dist_mem_addr_width-1 downto 0);
signal d_addr_pad_dly : std_logic_vector( data_dist_mem_addr_width-1 downto 0);
constant datasym_dist_mem_addr_width: integer:=UTILS_PKG_select_integer( dsym_addr_width,
                                                                4,
                                                                FIR_DETAILS.datasym_mem_depth<16);
signal d_sym_addr_dly    : std_logic_vector(max(log2_ext(fir_details.datasym_mem_depth),1)-1 downto 0);
signal dsym_addr_pad     : std_logic_vector( datasym_dist_mem_addr_width-1 downto 0);
signal dsym_addr_pad_dly : std_logic_vector( datasym_dist_mem_addr_width-1 downto 0);
  signal int_ce        : std_logic;
  signal we_ce         : std_logic;
  signal we_sym_ce     : std_logic;
signal we_slv          : std_logic_vector(0 downto 0);
signal we_sym_slv      : std_logic_vector(0 downto 0);
signal coef_we_in_slv  : std_logic_vector(0 downto 0);
signal int_ce_slv      : std_logic_vector(0 downto 0);
signal we_ce_slv       : std_logic_vector(0 downto 0);
signal we_sym_ce_slv   : std_logic_vector(0 downto 0);
begin
vcc     <= '1';
gnd     <= '0';
gnd_bus <= (others=>'0');
we_slv(0)          <=we;
we_sym_slv(0)      <=we_sym;
coef_we_in_slv(0)  <=coef_we_in;
int_ce_slv(0)      <=int_ce;
we_ce_slv(0)       <=we_ce;
we_sym_ce_slv(0)   <=we_sym_ce;
  gce1 : if C_HAS_CE=0 generate
  begin
    int_ce <= '1';
  end   generate;
  gce2 : if C_HAS_CE/=0 generate
  begin
    int_ce <= ce;
  end   generate;
  we_ce     <= we     and int_ce;
  we_sym_ce <= we_sym and int_ce;

gen_data_bypass: if (fir_details.no_data_mem=1) generate
  data_out    <= data_in;
  datasym_out <= datasym_in;
end generate gen_data_bypass;
gen_coef_single: if (fir_details.data_coef_combined=0) generate
  gen_reload: if (FIR_REQS.coef_reload=1) generate
    gen_reload_addr:process(clk)
    begin
      if (rising_edge(clk) and int_ce='1') then
        c_addr_reload_page<=coef_reload_page_in;
      end if;
      if (rising_edge(clk) and int_ce='1') then
        if (coef_ld='1') then
          c_addr_reload(c_addr_width-2 downto 0)<=(others=>'0');
          c_addr_reload_max<='0';
          coef_we_gen<='0';
          coef_we_dly<='0';
          reload_en_int<='1';
          enable_we_out<='1';
          coef_ld_en_out_int<='0';
        else
          if (reload_en_int='1' and coef_we_in='1') then
            if (c_addr_reload_max='1') then
              c_addr_reload(c_addr_width-2 downto 0)<=(others=>'0');
            else
              c_addr_reload(c_addr_width-2 downto 0)<=std_logic_vector(
                                    unsigned(c_addr_reload(c_addr_width-2 downto 0))
                                    +1);
            end if;
          end if;
          if(FIR_DETAILS.num_taps_per_mac_coefspace>1) then
            if (coef_we_in='1') then
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
          if (FIR_DETAILS.coef_mem_type=1 and FIR_DETAILS.num_taps_per_mac_coefspace>2) then
            coef_we_gen<=coef_we_dly;
            coef_we_dly<=coef_we_in and enable_we_out;
          else
            coef_we_gen<=coef_we_in and enable_we_out;
          end if;
          if (coef_we_in='1' and c_addr_reload_max='1' and coef_ld_en_in='1') then
            enable_we_out<='0';
            coef_ld_en_out_int<='1';
          end if;
        end if;
      end if;
    end process;

    c_addr_reload(c_addr_width-1)<=c_addr_reload_page;
    coef_ld_en_out<=coef_ld_en_out_int when LAST_COEF_STAGE=0 else '1';
    coef_we_out<=coef_we_gen;
    coef_reload_page_out<=c_addr_reload(c_addr_width-1);
  end generate gen_reload;
end generate gen_coef_single;
end;
