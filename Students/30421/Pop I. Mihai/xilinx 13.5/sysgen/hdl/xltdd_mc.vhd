
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
entity xltdd is
    generic (
        frame_length  : integer := 4;
        srl_addr      : integer := 4;
        srl_addr1     : integer := 4;
        fp_bv         : bit_vector  := "0000";
        fp_string     : string  := "0000";
        fp_bv1        : bit_vector  := "0000";
        fp_string1    : string  := "0000";
        data_width    : integer := 4;
    normalized_clock_enable_period    : integer := 2;
    log_2_normalized_clock_enable_period    : integer := 1);
    port (
    d : in std_logic_vector (data_width-1 downto 0);
        vin : in std_logic_vector (0 downto 0) := (others => '1');
        src_clk: in std_logic;
        src_ce: in std_logic;
        src_clr: in std_logic;
        dest_clk: in std_logic;
        dest_ce: in std_logic;
        dest_clr: in std_logic;
    q : out std_logic_vector (data_width-1 downto 0);
        vout : out std_logic_vector (0 downto 0));
end xltdd;
architecture behavior of xltdd is
    attribute INIT : string;
    component synth_reg_w_init
        generic (width       : integer;
                 init_index  : integer;
                 latency     : integer);
        port (i           : in std_logic_vector(width-1 downto 0);
              ce      : in std_logic;
              clr     : in std_logic;
              clk     : in std_logic;
              o       : out std_logic_vector(width-1 downto 0));
    end component;

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
    attribute INIT of pg_u1 : label is fp_string;
    attribute INIT of pg_u2 : label is fp_string1;
    constant pg_addr : std_logic_vector(3 downto 0)
        := integer_to_std_logic_vector(srl_addr-1, 4, xlUnsigned);
    constant pg_addr1 : std_logic_vector(3 downto 0)
        := integer_to_std_logic_vector(srl_addr1-1, 4, xlUnsigned);
    constant cnt_zero : std_logic_vector(3 downto 0) := (others => '0');
    signal smpl_dout: std_logic_vector(data_width-1 downto 0);
    signal fifo_addr: std_logic_vector(3 downto 0) := (others => '0');
    signal dly_fifo_en,fifo_en, src_en : std_logic;
    signal pg_in, pg_in1, pg_out, pg_out1 : std_logic;
    signal tmp_vout : std_logic_vector(0 downto 0) := (others => '0');
    signal cnt_by_one : std_logic_vector(0 downto 0);
    signal local_dest_ce : std_logic;

begin
    local_dest_ce_generator : entity work.xlclockenablegenerator
    generic map(
        period => normalized_clock_enable_period,
        pipeline_regs => 0,
        log_2_period => log_2_normalized_clock_enable_period
    )
    port map(
        clk => src_clk,
        clr => '0',
        ce => local_dest_ce
    );
    src_en <= src_ce and vin(0);
    fifo_en <= src_en and pg_out;
    dly_fifo_en <= fifo_en after 200 ps;
    cnt_by_one(0) <= '1';
    addr_counter : process (src_clk)
    begin
        if rising_edge(src_clk) then
            if (local_dest_ce = '1') then
                if (fifo_en = '1') then
                    fifo_addr <= fifo_addr;
                                        tmp_vout(0) <= '1';
                else
                                        if (fifo_addr = cnt_zero) then
                                                fifo_addr <= fifo_addr;
                                                tmp_vout(0) <= '0';
                                        else
                                                fifo_addr <= fifo_addr - 1;
                                                tmp_vout(0) <= '1';
                                        end if;
                end if;
                        else
                                if (fifo_en = '1') then
                                        fifo_addr <= fifo_addr + 1;
                                end if;
            end if;
        end if;
    end process;

   data_srl16_rows : for i in 0 to (data_width-1) generate
       u1 : srl16e
-- synopsys translate_off
       generic map(INIT => x"0000")
-- synopsys translate_on
       port map(clk => src_clk,
                d   => d(i),
                q   => smpl_dout(i),
                ce  => fifo_en,
                a0  => fifo_addr(0),
                a1  => fifo_addr(1),
                a2  => fifo_addr(2),
                a3  => fifo_addr(3));
   end generate;
    concat_addr_32 : if (frame_length > 16)
    generate
        pg_in <= pg_out1;
        pg_in1 <= pg_out;
    end generate;
    concat_addr_16 : if (frame_length <= 16)
    generate
        pg_in <= pg_out;
        pg_in1 <= pg_out1;
    end generate;

    pg_u1 : srl16e
-- synopsys translate_off
       generic map(INIT => fp_bv)
-- synopsys translate_on
       port map(clk => src_clk,
                d   => pg_in,
                q   => pg_out,
                ce  => src_en,
                a0  => pg_addr(0),
                a1  => pg_addr(1),
                a2  => pg_addr(2),
                a3  => pg_addr(3));
    pg_u2 : srl16e
-- synopsys translate_off
       generic map(INIT => fp_bv1)
-- synopsys translate_on
       port map(clk => src_clk,
                d   => pg_in1,
                q   => pg_out1,
                ce  => src_en,
                a0  => pg_addr1(0),
                a1  => pg_addr1(1),
                a2  => pg_addr1(2),
                a3  => pg_addr1(3));
   output_reg : synth_reg_w_init
       generic map ( width => data_width,
                     init_index => 0,
                     latency => 1)
       port map (i => smpl_dout,
                 ce => dest_ce,
                 clr => '0',
                 clk => dest_clk,
                 o => q);
   vout_reg : synth_reg_w_init
       generic map ( width => 1,
                     init_index => 0,
                     latency => 1)
       port map (i => tmp_vout,
                 ce => dest_ce,
                 clr => '0',
                 clk => dest_clk,
                 o => vout);
end  behavior;
