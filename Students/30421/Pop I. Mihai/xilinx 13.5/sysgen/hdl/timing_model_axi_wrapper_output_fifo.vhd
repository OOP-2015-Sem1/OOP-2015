
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
library xilinxcorelib;
use xilinxcorelib.axi_utils_pkg_v1_0.all;
use xilinxcorelib.axi_utils_v1_0_comps.all;
entity timing_model_axi_wrapper_output_fifo is
  generic (C_FIFO_WIDTH      : integer := 0;
           C_THROTTLE_SCHEME : integer := 0
           );
  port (   aclk       : in std_logic := '1';
           aresetn    : in std_logic := '1';
           fifo_reset : in std_logic := '1';
           aclken     : in std_logic := '1';
           data_in    : in  std_logic_vector(C_FIFO_WIDTH-1 downto 0);
           fifo_write : in  std_logic;
           full       : out std_logic := '0';
           almost_full: out std_logic := '0';
           tvalid     : out  std_logic := '0';
           tready     : in std_logic;
           data_out   : out std_logic_vector(C_FIFO_WIDTH-1 downto 0)
                                                   := (others => '0')
           );
end timing_model_axi_wrapper_output_fifo;
architecture synth of timing_model_axi_wrapper_output_fifo is
begin
  gen_real_time : if C_THROTTLE_SCHEME = 0 generate
    signal tvalid_int : std_logic := '0';
    signal full_int   : std_logic := '0';
  begin
    p_real_time : process (aclk)
      variable v_read  : std_logic := '0';
      variable v_write : std_logic := '0';
    begin
      if rising_edge(aclk) then
        if aresetn = '0' then
          full_int <= '0';
          data_out <= (others => '0');
        else
          v_write := '0';
          v_read  := '0';
          if aclken = '1' and tvalid_int = '1' and tready = '1' then
            v_read := '1';
          end if;
          if fifo_write = '1' then
            v_write := '1';
          end if;
          if v_write = '1' then
            full_int <= '1';
          elsif v_write = '0' and v_read = '1' then
            full_int <= '0';
          end if;
          if (v_write = '1' and full_int = '0') or (v_write = '1' and v_read = '1') then
            data_out <= data_in;
          end if;
        end if;
      end if;
    end process p_real_time;
    tvalid_int <= full_int;
    tvalid     <= tvalid_int;
    full        <= full_int and not (aclken and tvalid_int and tready) ;
    almost_full <= '0';
  end generate gen_real_time;
  gen_non_real_time : if C_THROTTLE_SCHEME /= 0 generate
    fifo :  glb_ifx_master_v1_0
      generic map (
        WIDTH         => C_FIFO_WIDTH,
        DEPTH         => 16,
        AFULL_THRESH1 => 14,
        AFULL_THRESH0 => 14)
      port map (
        aclk   => aclk,
        aclken => aclken,
        areset => fifo_reset,
        wr_enable => fifo_write,
        wr_data   => data_in,
        ifx_valid => tvalid,
        ifx_ready => tready,
        ifx_data  => data_out,
        full      => full,
        afull     => almost_full,
        not_full  => open,
        not_afull => open,
        add       => open);
  end generate gen_non_real_time;
end synth;
