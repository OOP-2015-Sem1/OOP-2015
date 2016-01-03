
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
-- synopsys translate_off
library XilinxCoreLib;
-- synopsys translate_on
library IEEE;
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on
-- synopsys translate_off
library simprim;
use simprim.VPACKAGE.all;
-- synopsys translate_on
entity xldsp48 is
  generic (
        AREG            : integer       := 1;
        B_INPUT         : string        := "DIRECT";
            LEGACY_MODE     : string        := "NONE";
        BREG            : integer       := 1;
        CARRYINREG      : integer       := 1;
        CARRYINSELREG   : integer       := 1;
        CREG            : integer       := 1;
        MREG            : integer       := 1;
        OPMODEREG       : integer       := 1;
        PREG            : integer       := 1;
            USE_OP              : integer       := 0;
        SUBTRACTREG     : integer       := 1;
            USE_C_PORT      : integer       := 1);
 port (
        BCOUT           : out std_logic_vector(17 downto 0);
        P               : out std_logic_vector(47 downto 0);
        PCOUT           : out std_logic_vector(47 downto 0);
        A               : in  std_logic_vector(17 downto 0) := (others => '0');
        B               : in  std_logic_vector(17 downto 0) := (others => '0');
        BCIN            : in  std_logic_vector(17 downto 0) := (others => '0');
        C               : in  std_logic_vector(47 downto 0) := (others => '0');
        CARRYIN         : in  std_logic_vector(0 downto 0) := (others => '0');
        CARRYINSEL      : in  std_logic_vector(1 downto 0) := (others => '0');
        CEA             : in  std_logic_vector(0 downto 0) := (others => '1');
        CEB             : in  std_logic_vector(0 downto 0) := (others => '1');
        CEC             : in  std_logic_vector(0 downto 0) := (others => '1');
        CECARRYIN       : in  std_logic_vector(0 downto 0) := (others => '1');
        CECINSUB        : in  std_logic_vector(0 downto 0) := (others => '1');
        CECTRL          : in  std_logic_vector(0 downto 0) := (others => '1');
        CEM             : in  std_logic_vector(0 downto 0) := (others => '1');
        CEP             : in  std_logic_vector(0 downto 0) := (others => '1');
            OP                  : in  std_logic_vector(10 downto 0) := (others => '0');
        OPMODE          : in  std_logic_vector(6 downto 0) := (others => '0');
        PCIN            : in  std_logic_vector(47 downto 0) := (others => '0');
        RSTA            : in  std_logic_vector(0 downto 0) := (others => '0');
        RSTB            : in  std_logic_vector(0 downto 0) := (others => '0');
        RSTC            : in  std_logic_vector(0 downto 0) := (others => '0');
        RSTCARRYIN      : in  std_logic_vector(0 downto 0) := (others => '0');
        RSTCTRL         : in  std_logic_vector(0 downto 0) := (others => '0');
        RSTM            : in  std_logic_vector(0 downto 0) := (others => '0');
        RSTP            : in  std_logic_vector(0 downto 0) := (others => '0');
        SUBTRACT        : in  std_logic_vector(0 downto 0) := (others => '0');
        CLK             : in  std_ulogic;
            en              : in  std_logic_vector(0 downto 0) := (others => '1');
            rst             : in  std_logic_vector(0 downto 0) := (others => '0');
            ce                  : in  std_logic;
            clr                     : in  std_logic
      );
end xldsp48;
architecture behavior of xldsp48 is
   component DSP48
     generic (
      AREG            : integer;
        B_INPUT       : string;
            LEGACY_MODE   : string;
        BREG          : integer;
        CARRYINREG    : integer;
        CARRYINSELREG : integer;
        CREG          : integer;
        MREG          : integer;
        OPMODEREG     : integer;
        PREG          : integer;
        SUBTRACTREG   : integer);
     port(
        BCOUT      : out std_logic_vector(17 downto 0);
        P          : out std_logic_vector(47 downto 0);
        PCOUT      : out std_logic_vector(47 downto 0);
        A          : in  std_logic_vector(17 downto 0);
        B          : in  std_logic_vector(17 downto 0);
        BCIN       : in  std_logic_vector(17 downto 0);
        C          : in  std_logic_vector(47 downto 0);
        CARRYIN    : in  std_ulogic;
        CARRYINSEL : in  std_logic_vector(1 downto 0);
        CEA        : in  std_ulogic;
        CEB        : in  std_ulogic;
        CEC        : in  std_ulogic;
        CECARRYIN  : in  std_ulogic;
            CECINSUB   : in  std_ulogic;
        CECTRL     : in  std_ulogic;
        CEM        : in  std_ulogic;
        CEP        : in  std_ulogic;
        CLK        : in  std_ulogic;
        OPMODE     : in  std_logic_vector(6 downto 0);
        PCIN       : in  std_logic_vector(47 downto 0);
        RSTA       : in  std_ulogic;
        RSTB       : in  std_ulogic;
        RSTC       : in  std_ulogic;
        RSTCARRYIN : in  std_ulogic;
        RSTCTRL    : in  std_ulogic;
        RSTM       : in  std_ulogic;
        RSTP       : in  std_ulogic;
        SUBTRACT   : in  std_ulogic);
   end component;
  signal internal_cea: std_logic;
  signal internal_ceb: std_logic;
  signal internal_cec: std_logic;
  signal internal_cep: std_logic;
  signal internal_cem: std_logic;
  signal internal_cecarryin: std_logic;
  signal internal_cectrl: std_logic;
  signal internal_rst : std_logic;
  signal internal_rsta : std_logic;
  signal internal_rstb : std_logic;
  signal internal_rstc : std_logic;
  signal internal_rstcarryin : std_logic;
  signal internal_rstctrl : std_logic;
  signal internal_rstm : std_logic;
  signal internal_cecinsub : std_logic;
  signal internal_rstp : std_logic;
  signal internal_opmode : std_logic_vector(6 downto 0);
  signal internal_sub : std_logic;
  signal internal_carryin : std_logic;
  signal internal_carryin_sel : std_logic_vector(1 downto 0);
begin
  internal_rst <= (rst(0) or clr);
  internal_cea <= CEA(0) and ce and en(0);
  internal_ceb <= CEB(0) and ce and en(0);
  using_c_port: if (USE_C_PORT = 1)
  generate
      internal_cec <= CEC(0) and ce and en(0);
      internal_rstc <= RSTC(0) or rst(0);
  end generate;
  not_using_c_port: if (USE_C_PORT = 0)
  generate
      internal_cec <= '1';
      internal_rstc <= '1';
  end generate;
  internal_cem <= CEM(0) and ce and en(0);
  internal_cep <= CEP(0) and ce and en(0);
  internal_cecarryin <= CECARRYIN(0) and ce and en(0);
  internal_cectrl <= CECTRL(0) and ce and en(0);
  internal_cecinsub <= CECINSUB(0) and ce and en(0);

  internal_rsta <= (RSTA(0) or internal_rst) and ce;
  internal_rstb <= (RSTB(0) or internal_rst) and ce;
  internal_rstcarryin <= (RSTCARRYIN(0) or internal_rst) and ce;
  internal_rstctrl <= (RSTCTRL(0) or internal_rst) and ce;
  internal_rstm <= (RSTM(0) or internal_rst) and ce;
  internal_rstp <= (RSTP(0) or internal_rst) and ce;
  opmode_0: if(use_op = 0)
  generate
        internal_opmode <= OPMODE;
  end generate;
  opmode_1: if(use_op = 1)
  generate
        internal_opmode <= OP(6 downto 0);
  end generate;
  sub_0: if(use_op = 0)
  generate
        internal_sub <= SUBTRACT(0);
  end generate;
  sub_1: if(use_op = 1)
  generate
        internal_sub <= OP(7);
  end generate;
  carryin_0: if(use_op = 0)
  generate
        internal_carryin <= CARRYIN(0);
  end generate;
  carryin_1: if(use_op = 1)
  generate
        internal_carryin <= OP(8);
  end generate;
  carryinsel_0: if(use_op = 0)
  generate
        internal_carryin_sel <= CARRYINSEL;
  end generate;
  carryinsel_1: if(use_op = 1)
  generate
        internal_carryin_sel <= OP(10 downto 9);
  end generate;
  dsp48_inst: DSP48
  generic map(
        AREG   => AREG,
        B_INPUT => B_INPUT,
            LEGACY_MODE => LEGACY_MODE,
        BREG   => BREG,
        CARRYINREG    => CARRYINREG,
        CARRYINSELREG => CARRYINSELREG,
        CREG        => CREG,
        MREG        => MREG,
        OPMODEREG   => OPMODEREG,
        PREG        => PREG,
        SUBTRACTREG => SUBTRACTREG)
  port map(
        BCOUT => BCOUT,
        P     => P,
        PCOUT => PCOUT,
        A     => A,
        B     => B,
        BCIN  => BCIN,
        C     => C,
        CARRYIN     => internal_carryin,
        CARRYINSEL  => internal_carryin_sel,
        CEA   => internal_cea,
        CEB   => internal_ceb,
        CEC   => internal_cec,
        CECARRYIN   => internal_cecarryin,
            CECINSUB    => internal_cecinsub,
        CECTRL      => internal_cectrl,
        CEM   => internal_cem,
        CEP   => internal_cep,
        CLK   => CLK,
        OPMODE  => internal_opmode,
        PCIN  => PCIN,
        RSTA  => internal_rsta,
        RSTB  => internal_rstb,
        RSTC  => internal_rstc,
        RSTCARRYIN  => internal_rstcarryin,
        RSTCTRL     => internal_rstctrl,
        RSTM        => internal_rstm,
        RSTP        => internal_rstp,
        SUBTRACT    => internal_sub
      );
end behavior;
