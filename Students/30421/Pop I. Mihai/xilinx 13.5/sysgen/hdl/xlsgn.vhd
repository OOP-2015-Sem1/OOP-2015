
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
use IEEE.numeric_std.all;
use work.conv_pkg.all;
entity synth_sgn is
    generic (d_width   : integer;
             d_arith   : integer;
             q_width  : integer;
             latency     : integer);
    port (d     : in std_logic_vector(d_width-1 downto 0);
          ce      : in std_logic;
          clr     : in std_logic;
          clk     : in std_logic;
          q    : out std_logic_vector(q_width-1 downto 0));
end synth_sgn;
architecture behavior of synth_sgn is
    component synth_reg
        generic (width       : integer;
                 latency     : integer);
        port (i           : in std_logic_vector(width-1 downto 0);
              ce      : in std_logic;
              clr     : in std_logic;
              clk     : in std_logic;
              o       : out std_logic_vector(width-1 downto 0));
    end component;
    function sgn(inp, one, neg_one : std_logic_vector; result_width : integer)
        return std_logic_vector
    is
        constant width : integer := inp'length;
        variable vec : std_logic_vector(width-1 downto 0);
        variable result : std_logic_vector(result_width-1 downto 0);
        -- synopsys translate_off
        variable U : std_logic_vector(result_width-1 downto 0);
        -- synopsys translate_on
    begin
        vec := inp;
        -- synopsys translate_off
        U := (others => 'U');
        if (is_XorU(vec)) then
            return U;
        end if;
         -- synopsys translate_on
        if std_logic_vector_to_signed(vec) >= 0 then
            result := one;
        else
            result := neg_one;
        end if;
        return result;
    end;
    signal one, neg_one : std_logic_vector(q_width-1 downto 0);
    signal result : std_logic_vector(q_width-1 downto 0);
begin
    one(q_width-1 downto 1) <= (others => '0');
    one(0) <= '1';
    neg_one <= (others => '1');
    SGN_Process :  process (d)
    begin
        if (d_arith = xlUnsigned) then
            result <= one;
        else
            result <= sgn(d, one, neg_one, q_width);
        end if;
    end process SGN_Process;
    latency_test : if (latency > 0) generate
        reg : synth_reg
            generic map ( width => q_width,
                          latency => latency)
            port map (i => result,
                      ce => ce,
                      clr => clr,
                      clk => clk,
                      o => q);
    end generate;
    latency0 : if (latency = 0) generate
        q <= result;
    end generate latency0;
end behavior;
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use work.conv_pkg.all;
entity xlsgn is
    generic (
        d_width      : integer := 16;
        d_bin_pt     : integer := 4;
        d_arith      : integer := xlSigned;
        q_width      : integer := 16;
        q_bin_pt     : integer := 0;
        q_arith      : integer := xlSigned;
        rst_width    : integer := 1;
        rst_bin_pt   : integer := 0;
        en_width     : integer := 5;
        en_bin_pt    : integer := 2;
        en_arith     : integer := xlUnsigned;
        rst_arith    : integer := xlUnsigned;
        latency      : integer := 2);
    port (
        d : in std_logic_vector (d_width-1 downto 0);
        ce  : in std_logic;
        clr : in std_logic;
        clk : in std_logic;
        rst  : in std_logic_vector (rst_width-1 downto 0);
        en   : in std_logic_vector (en_width-1 downto 0);
        q : out std_logic_vector (q_width-1 downto 0));
end xlsgn;
architecture behavior of xlsgn is
    component synth_sgn
        generic (d_width   : integer;
                 d_arith   : integer;
                 q_width  : integer;
                 latency     : integer);
        port (d     : in std_logic_vector(d_width-1 downto 0);
              ce      : in std_logic;
              clr     : in std_logic;
              clk     : in std_logic;
              q    : out std_logic_vector(q_width-1 downto 0));
    end component;
    signal internal_clr : std_logic;
    signal internal_ce  : std_logic;
begin
   internal_clr <= (clr or (rst(0))) and ce;
   internal_ce <= ce and (en(0));
    synth_sgn_inst : synth_sgn
        generic map(
            d_width => d_width,
            d_arith => d_arith,
            q_width => q_width,
            latency => latency)
        port map (
            d => d,
            ce => internal_ce,
            clr => internal_clr,
            clk => clk,
            q => q);
end  behavior;
