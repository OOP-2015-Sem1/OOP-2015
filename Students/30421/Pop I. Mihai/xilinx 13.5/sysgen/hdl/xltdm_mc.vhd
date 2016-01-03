
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
entity xltdm is
    generic (
        num_inputs    : integer := 2;
        data_width    : integer := 4;
        hasValid      : integer := 0;
    normalized_clock_enable_period  : integer := 2;
    log_2_normalized_clock_enable_period  : integer := 1);
    port (
        src_clk: in std_logic;
        src_ce: in std_logic;
        dest_clk: in std_logic;
        dest_ce: in std_logic;
    d0 : in std_logic_vector (data_width-1 downto 0):= (others => '0');
        d1 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d2 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d3 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d4 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d5 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d6 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d7 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d8 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d9 : in std_logic_vector (data_width-1 downto 0) := (others => '0');

    d10 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d11 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d12 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d13 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d14 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d15 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d16 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d17 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d18 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d19 : in std_logic_vector (data_width-1 downto 0) := (others => '0');

    d20 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d21 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d22 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d23 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d24 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d25 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d26 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d27 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d28 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d29 : in std_logic_vector (data_width-1 downto 0) := (others => '0');

    d30 : in std_logic_vector (data_width-1 downto 0) := (others => '0');
        d31 : in std_logic_vector (data_width-1 downto 0) := (others => '0');

    vin : in std_logic_vector (0 downto 0) := (others => '1');
        q : out std_logic_vector (data_width-1 downto 0);
        vout : out std_logic_vector (0 downto 0));
end xltdm;
architecture behavior of xltdm is
    type temp_array is array (0 to 31) of std_logic_vector(data_width-1 downto 0);
    signal din_temp : temp_array;

    signal src_en, dest_en, local_src_ce, local_src_en : std_logic;
    signal indx_cntr : integer := 0;

begin
    dest_en <= dest_ce and vin(0);
    local_src_en <= local_src_ce and vin(0);
    vout <= vin;
    generate_local_src_ce : entity work.xlclockenablegenerator
      generic map (
      period => normalized_clock_enable_period,
      pipeline_regs => 0,
      log_2_period => log_2_normalized_clock_enable_period
    )
    port map (
      clk => dest_clk,
      clr => '0',
      ce => local_src_ce
    );

    q <= din_temp(indx_cntr);
    index_counter : process(dest_clk)
    begin
        if rising_edge(dest_clk) then
                    if (local_src_en = '1') then
                indx_cntr <= 0;
            else
                if (dest_en = '1') then
                    indx_cntr <= indx_cntr + 1;
                end if;
            end if;
        end if;
    end process;

    din_temp(0) <= d0;
    din_temp(1) <= d1;
    din_temp(2) <= d2;
    din_temp(3) <= d3;
    din_temp(4) <= d4;
    din_temp(5) <= d5;
    din_temp(6) <= d6;
    din_temp(7) <= d7;
    din_temp(8) <= d8;
    din_temp(9) <= d9;
    din_temp(10) <= d10;
    din_temp(11) <= d11;
    din_temp(12) <= d12;
    din_temp(13) <= d13;
    din_temp(14) <= d14;
    din_temp(15) <= d15;
    din_temp(16) <= d16;
    din_temp(17) <= d17;
    din_temp(18) <= d18;
    din_temp(19) <= d19;
    din_temp(20) <= d20;
    din_temp(21) <= d21;
    din_temp(22) <= d22;
    din_temp(23) <= d23;
    din_temp(24) <= d24;
    din_temp(25) <= d25;
    din_temp(26) <= d26;
    din_temp(27) <= d27;
    din_temp(28) <= d28;
    din_temp(29) <= d29;
    din_temp(30) <= d30;
    din_temp(31) <= d31;

end  behavior;
