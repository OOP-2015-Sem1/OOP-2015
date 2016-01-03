
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
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on
entity xlp2s is
    generic (dout_width  : integer := 8;
             dout_arith  : integer := xlSigned;
             dout_bin_pt : integer := 0;
             din_width   : integer := 1;
             din_arith   : integer := xlUnsigned;
             din_bin_pt  : integer := 0;
             rst_width   : integer := 1;
             rst_bin_pt  : integer := 0;
             rst_arith   : integer := xlUnsigned;
             en_width    : integer := 1;
             en_bin_pt   : integer := 0;
             en_arith    : integer := xlUnsigned;
             lsb_first   : integer := 0;
             latency     : integer := 0;
             num_outputs : integer := 0);
    port (din        : in std_logic_vector (din_width-1 downto 0);
          src_ce     : in std_logic;
          src_clr    : in std_logic;
          src_clk    : in std_logic;
          dest_ce    : in std_logic;
          dest_clr   : in std_logic;
          dest_clk   : in std_logic;
          rst        : in std_logic_vector(rst_width-1 downto 0);
          en         : in std_logic_vector(en_width-1 downto 0);
          dout       : out std_logic_vector (dout_width-1 downto 0));
end xlp2s;
architecture synth_behavioral of xlp2s is
    component FDSE
        port(q  : out STD_ULOGIC;
             d  : in  STD_ULOGIC;
             c  : in  STD_ULOGIC;
             ce : in  STD_ULOGIC;
             s  : in  STD_ULOGIC);
    end component;
    attribute syn_black_box of FDSE : component is true;
    attribute fpga_dont_touch of FDSE : component is "true";
    component synth_reg
        generic (width   : integer;
                 latency : integer);
        port (i   : in std_logic_vector(width-1 downto 0);
              ce  : in std_logic;
              clr : in std_logic;
              clk : in std_logic;
              o   : out std_logic_vector(width-1 downto 0));
    end component;
    component synth_reg_w_init
        generic (width      : integer;
                 init_index : integer;
                 latency    : integer);
        port (i   : in std_logic_vector(width-1 downto 0);
              ce  : in std_logic;
              clr : in std_logic;
              clk : in std_logic;
              o   : out std_logic_vector(width-1 downto 0));
    end component;
    type temp_array is array (0 to num_outputs-1) of std_logic_vector(dout_width-1 downto 0);
    signal i : temp_array;
    signal o : temp_array;
    signal dout_temp        : std_logic_vector(dout_width -1 downto 0);
    signal src_ce_hold      : std_logic;
    signal internal_src_ce  : std_logic;
    signal internal_dest_ce : std_logic;
    signal internal_clr     : std_logic;
begin
    internal_src_ce   <= src_ce_hold and (en(0));
    internal_dest_ce  <= dest_ce and (en(0));
    internal_clr      <= ((dest_clr or src_clr ) and dest_ce) or rst(0);
    src_ce_reg : FDSE
    port map (q  => src_ce_hold,
              d  => src_ce,
              c  => src_clk,
              ce => dest_ce,
              s  => src_clr);
    lsb_is_first: if(lsb_first = 1) generate
       fd_array: for index in num_outputs - 1 downto 0 generate
          capture : synth_reg_w_init
             generic map (width      => dout_width,
                          init_index => 0,
                          latency    => 1)
             port map (i   => i(index),
                       ce  => internal_dest_ce,
                       clr => internal_clr,
                       clk => dest_clk,
                       o   => o(index));
       end generate;
       signal_select : for idx in num_outputs -1 downto 0 generate
          signal_0: if(idx < num_outputs-1)generate
             i(idx) <=  din((idx+1)*dout_width+dout_width-1 downto (idx+1)*dout_width)
                        when internal_src_ce = '1' else o(idx+1);
          end generate;
          signal_1: if(idx = num_outputs-1)generate
             i(idx) <=  din(idx*dout_width+dout_width-1 downto idx*dout_width)
                        when internal_src_ce = '1' else o(idx);
          end generate;
      end generate;
      dout_temp <= o(0) when internal_src_ce = '0' else din(dout_width-1 downto 0);
    end generate;
    msb_is_first: if(lsb_first = 0) generate
       fd_array: for index in num_outputs - 1 downto 0 generate
          capture : synth_reg_w_init
             generic map (width      => dout_width,
                          init_index => 0,
                          latency    => 1)
             port map (i   => i(index),
                       ce  => internal_dest_ce,
                       clr => internal_clr,
                       clk => dest_clk,
                       o   => o(index));
       end generate;
       signal_select : for idx in num_outputs -1 downto 0 generate
          signal_0: if(idx < num_outputs-1)generate
             i(idx) <=  din(din_width-1-(idx+1)*dout_width downto din_width-1 - (idx+1)*dout_width-dout_width+1)
                        when internal_src_ce = '1' else o(idx+1);
          end generate;
          signal_1: if(idx = num_outputs-1)generate
             i(idx) <=  din(din_width-1-idx*dout_width downto din_width-1-idx*dout_width-dout_width+1)
                        when internal_src_ce = '1' else o(idx);
          end generate;
       end generate;
       dout_temp <= o(0) when internal_src_ce = '0'
                         else din(din_width-1 downto din_width-dout_width);
    end generate;
    latency_gt_0 : if (latency > 0) generate
      data_reg : synth_reg
          generic map (width   => dout_width,
                       latency => latency)
          port map (i   => dout_temp,
                    ce  => internal_dest_ce,
                    clr => internal_clr,
                    clk => dest_clk,
                    o   => dout);
    end generate;
    latency0 : if (latency = 0 ) generate
       dout       <= dout_temp;
    end generate;
end architecture synth_behavioral;
