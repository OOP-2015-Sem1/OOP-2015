
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
entity xliobuf is
  generic (
    width : integer := 8
  );
  port (
    i  : in std_logic_vector(width-1 downto 0);
    o  : out std_logic_vector(width-1 downto 0);
    t  : in std_logic_vector(width-1 downto 0);
    io : inout std_logic_vector(width-1 downto 0)
  );
end xliobuf;
architecture structural of xliobuf is
  component IOBUF
    port (
      I  : in STD_ULOGIC;
      T  : in STD_ULOGIC;
      O  : out STD_ULOGIC;
      IO : inout STD_ULOGIC
    );
  end component;
begin
  iobuf_array : for j in 0 to width-1 generate
    u1 : IOBUF port map(I  => i(j),
                        O  => o(j),
                        T  => t(j),
                        IO => io(j));
  end generate;
end architecture structural;
library IEEE;
use IEEE.std_logic_1164.all;
entity xlibuf is
  generic (
    width : integer := 8
  );
  port (
    i  : in std_logic_vector(width-1 downto 0);
    o  : out std_logic_vector(width-1 downto 0)
  );
end xlibuf;
architecture structural of xlibuf is
  component IBUF
    port (
      I  : in STD_ULOGIC;
      O  : out STD_ULOGIC
    );
  end component;
begin
  ibuf_array : for j in 0 to width-1 generate
    u1 : IBUF port map(I  => i(j),
                       O  => o(j));
  end generate;
end architecture structural;
library IEEE;
use IEEE.std_logic_1164.all;
entity xlobuf is
  generic (
    width : integer := 8
  );
  port (
    i  : in std_logic_vector(width-1 downto 0);
    o  : out std_logic_vector(width-1 downto 0)
  );
end xlobuf;
architecture structural of xlobuf is
  component OBUF
    port (
      I  : in STD_ULOGIC;
      O  : out STD_ULOGIC
    );
  end component;
begin
  obuf_array : for j in 0 to width-1 generate
    u1 : OBUF port map(I  => i(j),
                       O  => o(j));
  end generate;
end architecture structural;
library IEEE;
use IEEE.std_logic_1164.all;
entity xlibufds is
  generic (
    width : integer := 8
  );
  port (
    i  : in std_logic_vector(width-1 downto 0);
        ib : in std_logic_vector(width-1 downto 0);
    o  : out std_logic_vector(width-1 downto 0)
  );
end xlibufds;
architecture structural of xlibufds is
  component IBUFDS
    port (
      I  : in STD_ULOGIC;
          IB : in STD_ULOGIC;
      O  : out STD_ULOGIC
    );
  end component;
begin
  ibufds_array : for j in 0 to width-1 generate
    u1 : IBUFDS port map(I  => i(j),
                             IB => ib(j),
                         O  => o(j));
  end generate;
end architecture structural;
library IEEE;
use IEEE.std_logic_1164.all;
entity xlobufds is
  generic (
    width : integer := 8
  );
  port (
    i  : in std_logic_vector(width-1 downto 0);
        o  : out std_logic_vector(width-1 downto 0);
    ob : out std_logic_vector(width-1 downto 0)
  );
end xlobufds;
architecture structural of xlobufds is
  component OBUFDS
    port (
      I  : in STD_ULOGIC;
      O  : out STD_ULOGIC;
      OB : out STD_ULOGIC
        );
  end component;
begin
  ibufds_array : for j in 0 to width-1 generate
    u1 : OBUFDS port map(I  => i(j),
                             O  => o(j),
                         OB => ob(j));
  end generate;
end architecture structural;
