
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
library IEEE;
use IEEE.std_logic_1164.all;
entity xlv6fft_control is
  generic (
    family               : integer := 0;
    xdevicefamily        : integer := 0;
    c_channels           : integer := 1;
    c_nfft_max           : integer := 6;
    c_arch                   : integer := 1;
    c_has_nfft           : integer := 1;
    c_input_width        : integer := 12;
    c_twiddle_width      : integer := 12;
    c_output_width       : integer := 12;
    c_has_ce             : integer := 1;
    c_has_sclr           : integer := 1;
    c_has_ovflo          : integer := 0;
    c_has_scaling        : integer := 0;
    c_has_bfp            : integer := 0;
    c_has_rounding       : integer := 0;
    c_data_mem_type      : integer := 1;
    c_twiddle_mem_type   : integer := 1;
    c_bram_stages        : integer := 0;
    c_reorder_mem_type   : integer := 1;
    c_has_natural_output : integer := 0;
    c_has_cyclic_prefix  : integer := 0;
    c_optimize_goal      : integer := 0;
    c_use_hybrid_ram     : integer := 0;
    c_fast_cmpy          : integer := 0;
    c_optimize           : integer := 0;
    scale_sch_width      : integer := 6;
    c_use_flt_pt         : integer := 0);
    port (
        en           : in std_logic := '1';
        rst          : in std_logic := '0';
        clk          : in std_logic := '0';
        ce           : in std_logic := '1';
        start        : in std_logic := '0';
        unload       : in std_logic := '0';
        nfft         : in std_logic_vector (4 downto 0) := (others => '0');
        nfft_we      : in std_logic := '0';
        fwd_inv      : in std_logic := '0';
        fwd_inv_we   : in std_logic := '0';
        scale_sch    : in std_logic_vector (scale_sch_width-1 downto 0) := (others => '0');
        scale_sch_we : in std_logic := '0';
        cp_len       : in std_logic_vector(c_nfft_max-1 downto 0) := (others => '0');
        cp_len_we    : in std_logic := '0';
        xn_index     : out std_logic_vector(c_nfft_max-1 downto 0);
        xk_index     : out std_logic_vector(c_nfft_max-1 downto 0);
        rfd          : out std_logic;
        busy         : out std_logic;
        vout         : out std_logic;
        edone        : out std_logic;
        done         : out std_logic;
        cpv          : OUT STD_LOGIC;
        rfs          : OUT STD_LOGIC;
        scale_sch_used : out std_logic_vector (scale_sch_width-1 downto 0);
        fwd_inv_used   : out std_logic);
end xlv6fft_control;
architecture behavior of xlv6fft_control is
  signal core_ce : std_logic;
function xfamily (c_family:integer) return string is
begin
  if c_family = 0 then
        return "virtex2p";
  elsif c_family = 1 then
        return "virtex4";
  elsif c_family = 2 then
       return "virtex5";
  else
       return "spartan3";
  end if;
end function xfamily;
function devicefamily (c_xdevicefamily:integer; c_family:string) return string is
begin
  if c_xdevicefamily = 0 then
        return c_family;
  elsif c_xdevicefamily = 1 then
        return "spartan3e";
  elsif c_xdevicefamily = 2 then
       return "spartan3a";
  else
       return "spartan3adsp";
  end if;
end function devicefamily;
constant c_family : string := xfamily(family);
constant c_xdevicefamily : string := devicefamily(xdevicefamily, c_family);
begin
    core_ce <= ce and en;
    fft_sim : entity work.timing_model
          generic map (
            c_family => c_family,
            c_xdevicefamily => c_xdevicefamily,
            c_channels => c_channels,
            c_nfft_max => c_nfft_max,
            c_arch => c_arch,
            c_has_nfft => c_has_nfft,
            c_input_width => c_input_width,
            c_twiddle_width => c_twiddle_width,
            c_output_width => c_output_width,
            c_has_ce => c_has_ce,
            c_has_sclr => c_has_sclr,
            c_has_ovflo => c_has_ovflo,
            c_has_scaling => c_has_scaling,
            c_has_bfp => c_has_bfp,
            c_has_rounding => c_has_rounding,
            c_data_mem_type => c_data_mem_type,
            c_twiddle_mem_type => c_twiddle_mem_type,
            c_bram_stages => c_bram_stages,
            c_has_natural_output => c_has_natural_output,
            c_has_cyclic_prefix => c_has_cyclic_prefix,
                c_fast_cmpy => c_fast_cmpy,
                c_use_hybrid_ram => c_use_hybrid_ram,
                c_optimize_goal => c_optimize_goal,
                c_reorder_mem_type => c_reorder_mem_type,
            c_optimize => c_optimize)
       port map (
           clk => clk,
           ce => core_ce,
           sclr => rst,
           start => start,
           unload => unload,
           nfft => nfft,
           nfft_we => nfft_we,
           fwd_inv => fwd_inv,
           fwd_inv_we => fwd_inv_we,
           scale_sch => scale_sch,
           scale_sch_we => scale_sch_we,
           cp_len => cp_len,
           cp_len_we => cp_len_we,
           xn_index => xn_index,
           xk_index => xk_index,
           rfd => rfd,
           busy => busy,
           dv => vout,
           edone => edone,
           done => done,
           cpv => cpv,
           rfs => rfs,
           scale_sch_used => scale_sch_used,
           fwd_inv_used => fwd_inv_used);
end  behavior;
