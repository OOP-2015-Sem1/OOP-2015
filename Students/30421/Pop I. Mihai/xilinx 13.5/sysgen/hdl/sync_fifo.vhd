
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
use IEEE.std_logic_unsigned.all;
use work.conv_pkg.all;
entity raminfr is
  generic (width : integer := 8);
  port (clk  : in std_logic;
        we   : in std_logic;
        a    : in std_logic_vector(3 downto 0);
        dpra : in std_logic_vector(3 downto 0);
        di   : in std_logic_vector(width-1 downto 0);
        spo  : out std_logic_vector(width-1 downto 0);
        dpo  : out std_logic_vector(width-1 downto 0));
end raminfr;
architecture syn of raminfr is
  type ram_type is array (15 downto 0)
    of std_logic_vector(width-1 downto 0);
  signal RAM : ram_type;
begin
  process(clk)
  begin
    if (clk'event and clk = '1') then
      if (we = '1') then
        RAM(conv_integer(a)) <= di;
      end if;
    end if;
  end process;
  spo <= RAM(conv_integer(a));
  dpo <= RAM(conv_integer(dpra));
end syn;
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use work.conv_pkg.all;
entity sync_fifo is
  generic (din_width   : integer := 8;
           din_bin_pt  : integer := 2;
           din_arith   : integer := xlSigned;
           dout_width  : integer := 18;
           dout_bin_pt : integer := 8;
           dout_arith  : integer := xlSigned;
           depth       : integer := 3);
  port (din      : in std_logic_vector (din_width-1 downto 0);
        write_ce : in std_logic;
        read_ce  : in std_logic;
        clr      : in std_logic;
        clk      : in std_logic;
        dout     : out std_logic_vector (dout_width-1 downto 0));
end sync_fifo;
architecture behavior of sync_fifo is
  constant depth_minus_1 : unsigned(2 downto 0) := std_logic_vector_to_unsigned(
    integer_to_std_logic_vector (depth-1,3,xlUnsigned));

  component raminfr is
    generic (width : integer := 8);
    port (clk  : in std_logic;
          we   : in std_logic;
          a    : in std_logic_vector(3 downto 0);
          dpra : in std_logic_vector(3 downto 0);
          di   : in std_logic_vector(width-1 downto 0);
          spo  : out std_logic_vector(width-1 downto 0);
          dpo  : out std_logic_vector(width-1 downto 0));
  end component;

  signal not_empty : std_logic := '0';
  signal write_pos : unsigned(2 downto 0) := "000";
  signal read_pos  : unsigned(2 downto 0) := "000";
  signal ram_data  : std_logic_vector(dout_width-1 downto 0) := (others => '0');
  signal write_addr : std_logic_vector(3 downto 0) := (others => '0');
  signal read_addr  : std_logic_vector(3 downto 0) := (others => '0');
begin
  write_addr(2 downto 0) <= unsigned_to_std_logic_vector(write_pos);
  write_addr(3) <= '0';
  read_addr(2 downto 0) <= unsigned_to_std_logic_vector(read_pos);
  read_addr(3) <= '0';

  distributed_ram : raminfr
    generic map (width => dout_width)
    port map (clk  => clk,
              we   => write_ce,
              a    => write_addr,
              dpra => read_addr,
              di   => din,
              spo  => open,
              dpo  => ram_data);
  process (clr, clk)
  begin
    if (rising_edge(clk)) then
      if (clr = '1') then
        write_pos <= (others => '0');
        read_pos <= (others => '0');
        not_empty <= '0';
        dout <= (others => '0');
      else
        if (write_ce = '1') then
          not_empty <= '1';
          if write_pos = depth_minus_1 then
            write_pos <= "000";
          else
            write_pos <= write_pos + 1;
          end if;
        end if;
        if read_ce = '1' then
          if not_empty = '1' then
            if read_pos = depth_minus_1 then
              read_pos <= "000";
            else
              read_pos <= read_pos + 1;
            end if;
          end if;
          dout <= ram_data;
        end if;
      end if;
    end if;
  end process;
end behavior;
