
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
use work.conv_pkg.all;
entity xlshift is
    generic (
        din_width    : integer := 4;
        din_bin_pt   : integer := 2;
        din_arith    : integer := xlSigned;
        dout_width   : integer := 2;
        dout_bin_pt  : integer := 0;
        dout_arith   : integer := xlSigned;
        rst_width    : integer := 1;
        rst_bin_pt   : integer := 0;
        en_width     : integer := 5;
        en_bin_pt    : integer := 2;
        en_arith     : integer := xlUnsigned;
        rst_arith    : integer := xlUnsigned;
        direction    : integer := 1;
        num_bits     : integer := 2;
        latency      : integer := 0;
        quantization : integer := xlTruncate;
        overflow     : integer := xlWrap);
    port (
        din  : in std_logic_vector (din_width-1 downto 0);
        ce   : in std_logic;
        clr  : in std_logic;
        clk  : in std_logic;
        rst  : in std_logic_vector (rst_width-1 downto 0);
        en   : in std_logic_vector (en_width-1 downto 0);
        dout : out std_logic_vector (dout_width-1 downto 0));
end xlshift;
architecture behavior of xlshift is
    component synth_reg
        generic (width       : integer;
                 latency     : integer);
        port (i           : in std_logic_vector(width-1 downto 0);
              ce      : in std_logic;
              clr     : in std_logic;
              clk     : in std_logic;
              o       : out std_logic_vector(width-1 downto 0));
    end component;
    constant shifted_din_width : integer := din_width + num_bits;
    signal result : std_logic_vector(dout_width-1 downto 0);
    signal internal_clr : std_logic;
    signal internal_ce  : std_logic;
begin
    internal_clr <= (clr or (rst(0))) and ce;
    internal_ce <= ce and (en(0));
    Convert_Process :  process (din)
      variable shifted_bin_pt : integer;
      variable shifted_din : std_logic_vector(shifted_din_width-1 downto 0);
    begin
        if (direction = 0) then
            shifted_din := extend_MSB(din, shifted_din_width, din_arith);
            shifted_bin_pt := din_bin_pt + num_bits;
        else
            shifted_din := pad_LSB(din, shifted_din_width);
            shifted_bin_pt := din_bin_pt;
        end if;
        result <= convert_type(shifted_din, shifted_din_width, shifted_bin_pt, din_arith,
                               dout_width, dout_bin_pt, dout_arith,
                               quantization, overflow);
    end process Convert_Process;
    latency_test : if (latency > 0)
    generate
        reg : synth_reg
            generic map ( width => dout_width,
                          latency => latency)
            port map (i => result,
                      ce => internal_ce,
                      clr => internal_clr,
                      clk => clk,
                      o => dout);
    end generate;
    latency0 : if (latency = 0)
    generate
        dout <= result;
    end generate latency0;
end  behavior;
