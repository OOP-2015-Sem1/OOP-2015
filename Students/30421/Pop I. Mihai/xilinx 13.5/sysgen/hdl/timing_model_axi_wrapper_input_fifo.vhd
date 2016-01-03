
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
entity timing_model_axi_wrapper_input_fifo is
  generic (C_FIFO_WIDTH      : integer := 0;
           C_THROTTLE_SCHEME : integer := 0;
           C_HAS_SKID_BUFFER : integer := 0
           );
  port (   aclk       : in std_logic := '1';
           aresetn    : in std_logic := '1';
           fifo_reset : in std_logic := '1';
           aclken     : in std_logic := '1';
           tvalid     : in  std_logic;
           tready     : out std_logic := '0';
           data_in    : in  std_logic_vector(C_FIFO_WIDTH-1 downto 0);
           empty       : out std_logic;
           almost_empty: out std_logic;
           read_fifo   : in  std_logic;
           data_avail  : out std_logic := '0';
           data_out    : out std_logic_vector(C_FIFO_WIDTH-1 downto 0)
           );
end timing_model_axi_wrapper_input_fifo;
architecture synth of timing_model_axi_wrapper_input_fifo is
begin
  gen_real_time : if C_THROTTLE_SCHEME = 0 generate
    signal tready_int : std_logic := '0';
    signal full       : std_logic := '0';
  begin
    p_real_time_data_in : process (aclk)
      variable v_read  : std_logic := '0';
      variable v_write : std_logic := '0';
    begin
      if rising_edge(aclk) then
        if aresetn = '0' then
          full       <= '0';
          data_avail <= '0';
        else
          v_write := '0';
          v_read  := '0';
          if aclken = '1' and tvalid = '1' and tready_int = '1' then
            v_write := '1';
          end if;
          if read_fifo = '1' then
            v_read := '1';
          end if;
          if v_write = '1' then
            full       <= '1';
            data_avail <= '1';
          elsif v_write = '0' and v_read = '1' then
            full       <= '0';
            data_avail <= '0';
          end if;
          if v_write = '1' then
            data_out <= data_in;
          end if;
        end if;
      end if;
    end process p_real_time_data_in;
    tready_int <= (not full) or read_fifo;
    tready     <= tready_int;
    empty        <= not full;
    almost_empty <= read_fifo  and not (aclken and tvalid and tready_int);
  end generate gen_real_time;
  gen_non_real_time : if C_THROTTLE_SCHEME /= 0 generate
    signal data_in_fifo_read : std_logic := '0';
    signal data_valid_fifo : std_logic := '0';
    signal data_avail_fifo : std_logic := '0';
    signal data_out_fifo : std_logic_vector(C_FIFO_WIDTH-1 downto 0);
    signal fifo_empty        : std_logic;
    signal fifo_almost_empty : std_logic;
  begin
    gen_has_skid_buffer: if C_HAS_SKID_BUFFER = 1 generate
      signal data_out_1 : std_logic_vector(C_FIFO_WIDTH-1 downto 0);
      signal data_out_2 : std_logic_vector(C_FIFO_WIDTH-1 downto 0);
      signal data_out_reg_full_1 : std_logic := '0';
      signal data_out_reg_full_2 : std_logic := '0';
    begin
      p_reg_data_out : process (aclk)
        variable v_stage_1_w : std_logic := '0';
        variable v_stage_2_w : std_logic := '0';
        variable v_stage_1_r : std_logic := '0';
        variable v_stage_2_r : std_logic := '0';
      begin
        if rising_edge(aclk) then
          if fifo_reset = '1' then
            data_out_1          <= (others => '0');
            data_out_2          <= (others => '0');
            data_out_reg_full_1 <= '0';
            data_out_reg_full_2 <= '0';
          else
            v_stage_2_r := '0';
            v_stage_2_w := '0';
            v_stage_1_w := '0';
            v_stage_1_r := '0';
            if read_fifo = '1' and data_out_reg_full_2 = '1' then
              v_stage_2_r := '1';
            end if;
            if (data_out_reg_full_2 = '0' or v_stage_2_r = '1') and (data_out_reg_full_1 = '1' or data_valid_fifo = '1') then
              v_stage_2_w := '1';
            end if;
            if data_valid_fifo = '1' and data_out_reg_full_1 = '0' and data_out_reg_full_2 = '1' and v_stage_2_r = '0' then
              v_stage_1_w := '1';
            end if;
            if data_out_reg_full_1 = '1' and (data_out_reg_full_2 = '0' or (data_out_reg_full_2 = '1' and v_stage_2_r = '1')) then
              v_stage_1_r := '1';
            end if;
            if v_stage_2_w = '1' then
              if data_out_reg_full_1 = '1' then
                data_out_2 <= data_out_1;
              else
                data_out_2 <= data_out_fifo;
              end if;
            end if;
            if v_stage_1_w = '1' then
              data_out_1 <= data_out_fifo;
            end if;
            if v_stage_2_w = '1' then
              data_out_reg_full_2 <= '1';
            elsif v_stage_2_r = '1' then
              data_out_reg_full_2 <= '0';
            end if;
            if v_stage_1_w = '1' then
              data_out_reg_full_1 <= '1';
            elsif v_stage_1_r = '1' then
              data_out_reg_full_1 <= '0';
            end if;
          end if;
        end if;
      end process p_reg_data_out;
      almost_empty <= '1' when
                      (data_avail_fifo = '0' and data_out_reg_full_1 = '0' and data_out_reg_full_2 = '1')
                      or
                      (data_valid_fifo = '0' and data_out_reg_full_1 = '0' and data_out_reg_full_2 = '1' and read_fifo = '1')
                      else '0';
      data_avail <= data_out_reg_full_2;
      empty      <= not data_out_reg_full_2;
      data_out <= data_out_2;
      data_in_fifo_read <= '1' when data_out_reg_full_2 = '0' or read_fifo = '1' else '0';
    end generate gen_has_skid_buffer;
    gen_no_skid_buffer: if C_HAS_SKID_BUFFER = 0 generate
      data_avail        <= data_avail_fifo;
      data_out          <= data_out_fifo;
      data_in_fifo_read <= read_fifo;
      empty             <= fifo_empty;
      almost_empty      <= fifo_almost_empty;
    end generate gen_no_skid_buffer;
    data_in_fifo_pt1 :  glb_ifx_slave_v1_0
      generic map (
        WIDTH          => C_FIFO_WIDTH,
        DEPTH          => 16,
        HAS_UVPROT     => true,
        AEMPTY_THRESH0 => 15,
        AEMPTY_THRESH1 => 15)
      port map (
        aclk   => aclk,
        aclken => aclken,
        areset => fifo_reset,
        ifx_valid  => tvalid,
        ifx_ready  => tready,
        ifx_data   => data_in,
        rd_enable  => data_in_fifo_read,
        rd_avail   => data_avail_fifo,
        rd_valid   => data_valid_fifo,
        rd_data    => data_out_fifo,
        full       => open,
        empty      => fifo_empty,
        aempty     => fifo_almost_empty,
        not_full   => open,
        not_empty  => open,
        not_aempty => open,
        add        => open);
  end generate gen_non_real_time;
end synth;
