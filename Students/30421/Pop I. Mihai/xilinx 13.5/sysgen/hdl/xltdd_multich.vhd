
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
use ieee.std_logic_unsigned.all;
use work.conv_pkg.all;
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on
entity xltdd_multich is
    generic (
        frame_length  : integer := 4;
        data_width    : integer := 4);
    port (
        d : in std_logic_vector (data_width-1 downto 0);
        vin : in std_logic_vector (0 downto 0) := (others => '1');
        src_clk: in std_logic;
        src_ce: in std_logic;
        src_clr: in std_logic;
        dest_clk: in std_logic;
        dest_ce: in std_logic;
        dest_clr: in std_logic;
        q0 : out std_logic_vector (data_width-1 downto 0);
        q1 : out std_logic_vector (data_width-1 downto 0);
        q2 : out std_logic_vector (data_width-1 downto 0);
        q3 : out std_logic_vector (data_width-1 downto 0);
        q4 : out std_logic_vector (data_width-1 downto 0);
        q5 : out std_logic_vector (data_width-1 downto 0);
        q6 : out std_logic_vector (data_width-1 downto 0);
        q7 : out std_logic_vector (data_width-1 downto 0);
        q8 : out std_logic_vector (data_width-1 downto 0);
        q9 : out std_logic_vector (data_width-1 downto 0);
        q10 : out std_logic_vector (data_width-1 downto 0);
        q11 : out std_logic_vector (data_width-1 downto 0);
        q12 : out std_logic_vector (data_width-1 downto 0);
        q13 : out std_logic_vector (data_width-1 downto 0);
        q14 : out std_logic_vector (data_width-1 downto 0);
        q15 : out std_logic_vector (data_width-1 downto 0);
        q16 : out std_logic_vector (data_width-1 downto 0);
        q17 : out std_logic_vector (data_width-1 downto 0);
        q18 : out std_logic_vector (data_width-1 downto 0);
        q19 : out std_logic_vector (data_width-1 downto 0);
        q20 : out std_logic_vector (data_width-1 downto 0);
        q21 : out std_logic_vector (data_width-1 downto 0);
        q22 : out std_logic_vector (data_width-1 downto 0);
        q23 : out std_logic_vector (data_width-1 downto 0);
        q24 : out std_logic_vector (data_width-1 downto 0);
        q25 : out std_logic_vector (data_width-1 downto 0);
        q26 : out std_logic_vector (data_width-1 downto 0);
        q27 : out std_logic_vector (data_width-1 downto 0);
        q28 : out std_logic_vector (data_width-1 downto 0);
        q29 : out std_logic_vector (data_width-1 downto 0);
        q30 : out std_logic_vector (data_width-1 downto 0);
        q31 : out std_logic_vector (data_width-1 downto 0);
        vout : out std_logic_vector (0 downto 0));
end xltdd_multich;
architecture behavior of xltdd_multich is
    component SRL16E
        -- synopsys translate_off
        generic (INIT : bit_vector := X"0000");
        -- synopsys translate_on
        port (D   : in std_ulogic;
              CE  : in std_ulogic;
              CLK : in std_ulogic;
              A0  : in std_ulogic;
              A1  : in std_ulogic;
              A2  : in std_ulogic;
              A3  : in std_ulogic;
              Q   : out std_ulogic);
    end component;
    attribute syn_black_box of SRL16E : component is true;
    attribute fpga_dont_touch of SRL16E : component is "true";

    component synth_reg
        generic (width   : integer;
                 latency : integer);
        port (i   : in std_logic_vector(width-1 downto 0);
              ce  : in std_logic;
              clr : in std_logic;
              clk : in std_logic;
              o   : out std_logic_vector(width-1 downto 0));
    end component;

    type temp_array is array (0 to 31) of std_logic_vector(data_width-1 downto 0);
    signal i, o, dout_temp, capture_in : temp_array;

    constant pg_addr : std_logic_vector(3 downto 0)
        := integer_to_std_logic_vector(frame_length-1, 4, xlUnsigned);
    constant cnt_zero : std_logic_vector(3 downto 0) := (others => '0');
    signal smpl_dout: std_logic_vector(data_width-1 downto 0);
    signal fifo_addr: std_logic_vector(3 downto 0) := (others => '0');
    signal dly_fifo_en,fifo_en, pg_out, src_en : std_logic;
    signal tmp_vout : std_logic_vector(0 downto 0) := (others => '0');
    signal cnt_by_one : std_logic_vector(0 downto 0);

begin
    src_en <= src_ce and vin(0);
    fifo_en <= src_en and pg_out;
    dly_fifo_en <= fifo_en after 200 ps;
    cnt_by_one(0) <= '1';
       fd_array: for index in frame_length - 1 downto 0 generate
          comp : synth_reg
             generic map (width      => data_width,
                          latency    => 1)
             port map (i   => i(index),
                       ce  => src_en,
                       clr => src_clr,
                       clk => src_clk,
                       o   => o(index));
          capture : synth_reg
             generic map (width      => data_width,
                           latency    => 1)
             port map (i   => capture_in(index),
                       ce  => dest_ce,
                       clr => dest_clr,
                       clk => dest_clk,
                       o   => dout_temp(frame_length-1-index));
          signal_0: if (index = 0) generate
                       i(index) <= d;
                       capture_in(index) <= d;
          end generate;
          signal_gt_0: if (index > 0) generate
                        i(index) <= o(index - 1);
                        capture_in(index) <= o(index - 1);
          end generate;
       end generate;
        q0 <= dout_temp(0);
        q1 <= dout_temp(1);
        q2 <= dout_temp(2);
        q3 <= dout_temp(3);
        q4 <= dout_temp(4);
        q5 <= dout_temp(5);
        q6 <= dout_temp(6);
        q7 <= dout_temp(7);
        q8 <= dout_temp(8);
        q9 <= dout_temp(9);
        q10 <= dout_temp(10);
        q11 <= dout_temp(11);
        q12 <= dout_temp(12);
        q13 <= dout_temp(13);
        q14 <= dout_temp(14);
        q15 <= dout_temp(15);
        q16 <= dout_temp(16);
        q17 <= dout_temp(17);
        q18 <= dout_temp(18);
        q19 <= dout_temp(19);
        q20 <= dout_temp(20);
        q21 <= dout_temp(21);
        q22 <= dout_temp(22);
        q23 <= dout_temp(23);
        q24 <= dout_temp(24);
        q25 <= dout_temp(25);
        q26 <= dout_temp(26);
        q27 <= dout_temp(27);
        q28 <= dout_temp(28);
        q29 <= dout_temp(29);
        q30 <= dout_temp(30);
        q31 <= dout_temp(31);

end  behavior;
