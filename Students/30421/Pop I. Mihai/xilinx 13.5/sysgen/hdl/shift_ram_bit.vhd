
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
use work.mac_fir_utils_pkg.all;
library xilinxcorelib;
use xilinxcorelib.c_shift_ram_v9_0_comp.all;
use xilinxcorelib.c_reg_fd_v9_0_comp.all;
entity shift_ram_bit is
generic (
  C_FAMILY       : string;
  C_DEPTH        : integer;
  C_ENABLE_RLOCS : integer
);
port (
  CLK            : in  std_logic;
  CE             : in  std_logic;
  SCLR           : in  std_logic;
  D              : in  std_logic;
  Q              : out std_logic
);
end shift_ram_bit;
architecture xilinx of shift_ram_bit is
  signal gnd          : std_logic;
  signal open_a       : std_logic_vector(3 downto 0);
  signal int_d        : std_logic_vector(0 downto 0);
  signal int_q        : std_logic_vector(0 downto 0);
  signal reg_output   : std_logic_vector(0 downto 0);
begin
  gnd <= '0';
  no_delay : if ( C_DEPTH = 0 ) generate
    Q <= D;
  end generate no_delay;
  reg_delay : if ( C_DEPTH = 1 ) generate
    int_d(0) <= D;
    rst_reg  : c_reg_fd_v9_0
    generic map (
      C_WIDTH         => 1,
      C_AINIT_VAL     => "0",
      C_SINIT_VAL     => "0",
      C_SYNC_PRIORITY => 0,
      C_SYNC_ENABLE   => 0,
      C_HAS_CE        => 1,
      C_HAS_ACLR      => 0,
      C_HAS_ASET      => 0,
      C_HAS_AINIT     => 0,
      C_HAS_SCLR      => 0,
      C_HAS_SSET      => 0,
      C_HAS_SINIT     => 0,
      C_ENABLE_RLOCS  => C_ENABLE_RLOCS
    )
    port map (
      D               => int_d,
      CLK             => CLK,
      CE              => CE,
      ACLR            => gnd,
      ASET            => gnd,
      AINIT           => gnd,
      SCLR            => gnd,
      SSET            => gnd,
      SINIT           => gnd,
      Q               => reg_output
    );
    Q <= reg_output(0);
  end generate reg_delay;
  SRL16_delay : if ( C_DEPTH > 1 ) generate
    int_d(0) <= D;
    shift_reg : c_shift_ram_v9_0
    generic map (
      C_FAMILY             => C_FAMILY,
      C_ADDR_WIDTH         => 4,
      C_AINIT_VAL          => "0",
      C_DEFAULT_DATA       => "0",
      C_DEFAULT_DATA_RADIX => 1,
      C_DEPTH              => C_DEPTH,
      C_ENABLE_RLOCS       => C_ENABLE_RLOCS,
      C_GENERATE_MIF       => 0,
      C_HAS_ACLR           => 0,
      C_HAS_A              => 0,
      C_HAS_AINIT          => 0,
      C_HAS_ASET           => 0,
      C_HAS_CE             => 1,
      C_HAS_SCLR           => 0,
      C_HAS_SINIT          => 0,
      C_HAS_SSET           => 0,
      C_MEM_INIT_FILE      => "",
      C_MEM_INIT_RADIX     => 1,
      C_READ_MIF           => 0,
      C_REG_LAST_BIT       => 1,
      C_SHIFT_TYPE         => 0,
      C_SINIT_VAL          => "0",
      C_SYNC_PRIORITY      => 0,
      C_SYNC_ENABLE        => 0,
      C_WIDTH              => 1,
      C_OPT_GOAL           => 0
    )
    port map (
      A                    => open_a,
      D                    => int_d,
      CLK                  => CLK,
      CE                   => CE,
      ACLR                 => gnd,
      ASET                 => gnd,
      AINIT                => gnd,
      SCLR                 => gnd,
      SSET                 => gnd,
      SINIT                => gnd,
      Q                    => int_q
    );
    Q <= int_q(0);
  end generate SRL16_delay;
end xilinx;
