
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

library work;
use work.all;

entity fir_compiler_v2_0_control is
generic (
    num_taps            : integer := 20;
    clock_freq          : integer := 1;
    sample_freq         : integer := 1;
    filter_type         : integer := 0;
    symmetry            : integer := 0;
    neg_symmetry        : integer := 0;
    odd_symmetry        : integer := 0;
    num_channels        : integer := 1;
    chan_sel_width      : integer := 1;
    interp_rate         : integer := 1;
    decim_rate          : integer := 1;
    device              : integer := 0;
    coef_reload         : integer := 0;
    memory_mode         : integer := 0;
    data_memtype        : integer := 0;
    coef_memtype        : integer := 0;
    col_mode            : integer := 0;
    col_1st_len         : integer := 3;
    col_wrap_len        : integer := 4;
    col_pipe_len        : integer := 3;
    output_width        : integer := 33;
    data_type           : integer := 0;
    data_width          : integer := 16;
    coef_type           : integer := 0;
    coef_width          : integer := 13
);
port (
  sclr          : in  std_logic;
  clk           : in  std_logic;
  ce            : in  std_logic;
  nd            : in  std_logic;
  rfd           : out std_logic;
  rdy           : out std_logic;
  chan_in       : out std_logic_vector(chan_sel_width-1 downto 0);
  chan_out      : out std_logic_vector(chan_sel_width-1 downto 0);
  coef_ld       : in  std_logic := '0';
  coef_we       : in  std_logic := '0';
  coef_din      : in  std_logic_vector(coef_width-1 downto 0)   := (others => '0');

  we_filter_block   : out STD_LOGIC;
  coef_current_page : out STD_LOGIC
);
end fir_compiler_v2_0_control;

architecture synth of fir_compiler_v2_0_control is


function map_int_to_device_name(device:in integer) return string is
variable device_str : string(1 to 7)  := "virtex4";
begin
    if (device = 1) then
        device_str := "virtex4";
    else
        device_str := "virtex5";
    end if;
    return device_str;
end function;

signal internal_clk : std_logic;
begin
internal_clk <= clk;
mac6: entity work.fir_compiler_v2_0_xst
    generic map(
        num_taps => num_taps,
        sample_freq => sample_freq,
        clock_freq => clock_freq,
        filter_type => filter_type,
        symmetry => symmetry,
        neg_symmetry => neg_symmetry,
        odd_symmetry => odd_symmetry,
        num_channels => num_channels,
        chan_sel_width => chan_sel_width,
        decim_rate => decim_rate,
        interp_rate => interp_rate,
        c_family => map_int_to_device_name(device),
        coef_reload => coef_reload,
        memory_mode => memory_mode,
        data_memtype => data_memtype,
        coef_memtype => coef_memtype,
        col_mode => col_mode,
        col_1st_len => col_1st_len,
        col_wrap_len => col_wrap_len,
        col_pipe_len => col_pipe_len,
        output_width => output_width,
        data_type => data_type,
        data_width => data_width,
        coef_type => coef_type,
        coef_width => coef_width
    )
    port map (
      SCLR       => sclr,
      CLK        => internal_clk,
      CE         => ce,
      ND         => nd,
      RFD        => rfd,
      RDY        => rdy,
      CHAN_IN    => chan_in,
      CHAN_OUT   => chan_out,
      COEF_LD    => coef_ld,
      COEF_WE    => coef_we,
      COEF_DIN   => coef_din,
      WE_FILTER_BLOCK => we_filter_block,
      COEF_CURRENT_PAGE => coef_current_page
    );

end synth;
