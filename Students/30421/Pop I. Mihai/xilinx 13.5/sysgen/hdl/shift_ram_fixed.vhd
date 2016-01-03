
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
use ieee.numeric_std.all;
library work;
use work.mac_fir_utils_pkg.all;
library xilinxcorelib;
use xilinxcorelib.prims_utils_v9_0.all;
library xilinxcorelib;
use xilinxcorelib.c_shift_ram_v9_0_comp.all;
use xilinxcorelib.c_reg_fd_v9_0_comp.all;
entity shift_ram_fixed is
generic (
  C_FAMILY     : string;
  C_DEPTH      : integer;
  C_WIDTH      : integer
);
port (
  CLK   : in  std_logic;
  CE    : in  std_logic;
  D     : in  std_logic_vector(C_WIDTH-1 downto 0);
  Q     : out std_logic_vector(C_WIDTH-1 downto 0)
);
end shift_ram_fixed;
architecture xilinx of shift_ram_fixed is
  signal gnd       : std_logic;
  signal open_a    : std_logic_vector(3 downto 0);
  constant default_data_blank_slv:std_logic_vector(C_WIDTH downto 1):=std_logic_vector(to_unsigned(0,C_WIDTH));
  constant default_data_blank:string:=slv_to_str(default_data_blank_slv);
begin
  gnd <= '0';

  open_a<=(others=>'0');
  no_delay : if ( C_DEPTH = 0 ) generate
    Q <= D;
  end generate no_delay;
  reg_delay : if ( C_DEPTH = 1 ) generate
    rst_reg  : c_reg_fd_v9_0
    generic map (
      C_WIDTH         => C_WIDTH,
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
      C_ENABLE_RLOCS  => 0
    )
    port map (
      D               => D,
      CLK             => CLK,
      CE              => CE,
      ACLR            => gnd,
      ASET            => gnd,
      AINIT           => gnd,
      SCLR            => gnd,
      SSET            => gnd,
      SINIT           => gnd,
      Q               => Q
    );
  end generate reg_delay;
  SRL16_delay : if ( C_DEPTH > 1 ) generate
    shift_reg : c_shift_ram_v9_0
    generic map (
      C_FAMILY             => C_FAMILY,
      C_ADDR_WIDTH         => 4,
      C_AINIT_VAL          => "",
      C_DEFAULT_DATA       => default_data_blank,
      C_DEFAULT_DATA_RADIX => 1,
      C_DEPTH              => C_DEPTH,
      C_ENABLE_RLOCS       => 0,
      C_GENERATE_MIF       => 0,
      C_HAS_ACLR           => 0,
      C_HAS_A              => 0,
      C_HAS_AINIT          => 0,
      C_HAS_ASET           => 0,
      C_HAS_SCLR           => 0,
      C_HAS_SINIT          => 0,
      C_HAS_SSET           => 0,
      C_HAS_CE             => 1,
      C_MEM_INIT_FILE      => "",
      C_MEM_INIT_RADIX     => 1,
      C_READ_MIF           => 0,
      C_REG_LAST_BIT       => 1,
      C_SHIFT_TYPE         => 0,
      C_SINIT_VAL          => "0",
      C_SYNC_PRIORITY      => 0,
      C_SYNC_ENABLE        => 0,
      C_WIDTH              => C_WIDTH,
      C_OPT_GOAL           => 0
    )
    port map (
       A                    => open_a,
      D                    => D,
      CLK                  => CLK,
      CE                   => CE,
      ACLR                 => gnd,
      ASET                 => gnd,
      AINIT                => gnd,
      SCLR                 => gnd,
      SSET                 => gnd,
      SINIT                => gnd,
      Q                    => Q
    );
  end generate SRL16_delay;
end xilinx;
