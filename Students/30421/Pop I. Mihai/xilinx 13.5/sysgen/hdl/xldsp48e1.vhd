
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
entity generatePowerEfficientEnable_dsp48e1 is
  generic (
    use_reg : integer := 0
  );
  port (
    cereg : in std_logic;
    ce : in std_logic;
    en : in std_logic;
    internal_cereg : out std_logic
  );
end  generatePowerEfficientEnable_dsp48e1;
architecture structural of generatePowerEfficientEnable_dsp48e1 is
begin
using_reg : if (use_reg = 1)
generate
  internal_cereg <= cereg and ce and en;
end generate;
not_using_reg : if (use_reg /= 1)
generate
  internal_cereg <= '0';
end generate;
end structural;
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
entity xldsp48e1 is
  generic (
        carryout_width  : integer               := 1;
        alumodereg      : integer               := 1;
        areg            : integer               := 1;
        use_c_port      : integer              := 1;
        use_op          : integer               := 0;
        autoreset_pattern_detect                : string               :=       "NO_RESET";
        a_input         : string                := "DIRECT";
            acascreg        : integer           := 0;
            adreg           : integer           := 1;
        bcascreg        : integer               := 1;
        breg            : integer               := 1;
        b_input         : string                := "DIRECT";
        carryinreg      : integer               := 1;
        carryinselreg   : integer               := 1;
        creg            : integer               := 1;
        dreg            : integer               := 1;
        inmodereg            : integer               := 1;
        mask            : bit_vector            := X"3FFFFFFFFFFF";
        mreg            : integer               := 1;
        opmodereg       : integer               := 1;
        pattern         : bit_vector            := X"000000000000";
        preg            : integer               := 1;
        sel_mask        : string                := "MASK";
        sel_pattern     : string                := "PATTERN";
                use_dport       : boolean               := FALSE;
        use_mult        : string                := "MULTIPLY";
        use_pattern_detect      : string        := "NO_PATDET";
        use_simd        : string                := "ONE48"
        );
 port (
        acout              : out std_logic_vector(29 downto 0);
        bcout              : out std_logic_vector(17 downto 0);
        carrycascout       : out std_logic_vector(0 downto 0);
        carryout           : out std_logic_vector(carryout_width-1 downto 0);
        multsignout        : out std_logic_vector(0 downto 0);
        overflow           : out std_logic_vector(0 downto 0);
        p                  : out std_logic_vector(47 downto 0);
        patternbdetect     : out std_logic_vector(0 downto 0);
        patterndetect      : out std_logic_vector(0 downto 0);
        pcout              : out std_logic_vector(47 downto 0);
        underflow          : out std_logic_vector(0 downto 0);
        a                  : in  std_logic_vector(29 downto 0) := (others => '0');
        acin               : in  std_logic_vector(29 downto 0) := (others => '0');
        alumode            : in  std_logic_vector(3 downto 0) := (others => '0');
        b                  : in  std_logic_vector(17 downto 0) := (others => '0');
        bcin               : in  std_logic_vector(17 downto 0) := (others => '0');
        c                  : in  std_logic_vector(47 downto 0) := (others => '0');
        carrycascin        : in  std_logic_vector(0 downto 0) := (others => '0');
        carryin            : in  std_logic_vector(0 downto 0) := (others => '0');
        carryinsel         : in  std_logic_vector(2 downto 0) := (others => '0');
        cea1               : in  std_logic_vector(0 downto 0) := (others => '1');
        cea2               : in  std_logic_vector(0 downto 0) := (others => '1');
        cead               : in  std_logic_vector(0 downto 0) := (others => '1');
        cealumode          : in  std_logic_vector(0 downto 0) := (others => '1');
        ceb1               : in  std_logic_vector(0 downto 0) := (others => '1');
        ceb2               : in  std_logic_vector(0 downto 0) := (others => '1');
        cec                : in  std_logic_vector(0 downto 0) := (others => '1');
        cecarryin          : in  std_logic_vector(0 downto 0) := (others => '1');
        cectrl             : in  std_logic_vector(0 downto 0) := (others => '1');
        ced                : in  std_logic_vector(0 downto 0) := (others => '1');
        ceinmode             : in  std_logic_vector(0 downto 0) := (others => '1');
        cem                : in  std_logic_vector(0 downto 0) := (others => '1');
        cemultcarryin      : in  std_logic_vector(0 downto 0) := (others => '1');
        cep                : in  std_logic_vector(0 downto 0) := (others => '1');
        multsignin         : in  std_logic_vector(0 downto 0) := (others => '0');
        opmode             : in  std_logic_vector(6 downto 0) := (others => '0');
        pcin               : in  std_logic_vector(47 downto 0) := (others => '0');
        rsta               : in  std_logic_vector(0 downto 0) := (others => '0');
        rstcarryin      : in  std_logic_vector(0 downto 0) := (others => '0');
        rstalumode         : in  std_logic_vector(0 downto 0) := (others => '0');
        rstb               : in  std_logic_vector(0 downto 0) := (others => '0');
        rstc               : in  std_logic_vector(0 downto 0) := (others => '0');
        rstctrl            : in  std_logic_vector(0 downto 0) := (others => '0');
        rstd               : in  std_logic_vector(0 downto 0) := (others => '0');
        rstinmode            : in  std_logic_vector(0 downto 0) := (others => '0');
        rstm               : in  std_logic_vector(0 downto 0) := (others => '0');
        rstp               : in  std_logic_vector(0 downto 0) := (others => '0');
        op                 : in  std_logic_vector(19 downto 0) := (others => '0');
        clk                : in  std_ulogic;
        d                  : in  std_logic_vector(24 downto 0) := (others => '0');
        inmode             : in  std_logic_vector(4 downto 0) := (others => '0');
        en                 : in  std_logic_vector(0 downto 0) := (others => '1');
        rst                : in  std_logic_vector(0 downto 0) := (others => '0');
        ce                 : in  std_logic
      );
end xldsp48e1;
architecture behavior of xldsp48e1 is
component DSP48E1
 generic(
        ACASCREG        : integer;
        ADREG           : integer;
        ALUMODEREG      : integer;
        AREG            : integer;
        AUTORESET_PATDET                : string;
        A_INPUT         : string;
        BCASCREG        : integer;
        BREG            : integer;
        B_INPUT         : string;
        CARRYINREG      : integer;
        CARRYINSELREG   : integer;
        CREG            : integer;
        DREG            : integer               := 1;
        INMODEREG       : integer               := 1;
        MASK            : bit_vector;
        MREG            : integer;
        OPMODEREG       : integer;
        PATTERN         : bit_vector;
        PREG            : integer;
        SEL_MASK        : string;
        SEL_PATTERN     : string;
        USE_DPORT       : boolean               := FALSE;
        USE_MULT        : string        ;
        USE_PATTERN_DETECT      : string;
        USE_SIMD        : string
        );
  port(
        ACOUT                   : out std_logic_vector(29 downto 0);
        BCOUT                   : out std_logic_vector(17 downto 0);
        CARRYCASCOUT            : out std_ulogic;
        CARRYOUT                : out std_logic_vector(3 downto 0);
        MULTSIGNOUT             : out std_ulogic;
        OVERFLOW                : out std_ulogic;
        P                       : out std_logic_vector(47 downto 0);
        PATTERNBDETECT          : out std_ulogic;
        PATTERNDETECT           : out std_ulogic;
        PCOUT                   : out std_logic_vector(47 downto 0);
        UNDERFLOW               : out std_ulogic;
        A                       : in  std_logic_vector(29 downto 0);
        ACIN                    : in  std_logic_vector(29 downto 0);
        ALUMODE                 : in  std_logic_vector(3 downto 0);
        B                       : in  std_logic_vector(17 downto 0);
        BCIN                    : in  std_logic_vector(17 downto 0);
        C                       : in  std_logic_vector(47 downto 0);
        CARRYCASCIN             : in  std_ulogic;
        CARRYIN                 : in  std_ulogic;
        CARRYINSEL              : in  std_logic_vector(2 downto 0);
        CEA1                    : in  std_ulogic;
        CEA2                    : in  std_ulogic;
        CEAD                    : in  std_ulogic;
        CEALUMODE               : in  std_ulogic;
        CEB1                    : in  std_ulogic;
        CEB2                    : in  std_ulogic;
        CEC                     : in  std_ulogic;
        CECARRYIN               : in  std_ulogic;
        CECTRL                  : in  std_ulogic;
        CED                     : in  std_ulogic;
        CEINMODE                : in  std_ulogic;
        CEM                     : in  std_ulogic;
        CEP                     : in  std_ulogic;
        CLK                     : in  std_ulogic;
        D                       : in  std_logic_vector(24 downto 0);
        INMODE                  : in  std_logic_vector(4 downto 0);
        MULTSIGNIN              : in std_ulogic;
        OPMODE                  : in  std_logic_vector(6 downto 0);
        PCIN                    : in  std_logic_vector(47 downto 0);
        RSTA                    : in  std_ulogic;
        RSTALLCARRYIN           : in  std_ulogic;
        RSTALUMODE              : in  std_ulogic;
        RSTB                    : in  std_ulogic;
        RSTC                    : in  std_ulogic;
        RSTCTRL                 : in  std_ulogic;
        RSTD                    : in  std_ulogic;
        RSTINMODE               : in  std_ulogic;
        RSTM                    : in  std_ulogic;
        RSTP                    : in  std_ulogic
      );
   end component;
  signal internal_cea1: std_logic;
  signal internal_cea2: std_logic;
  signal internal_cead: std_logic;
  signal internal_ceb1: std_logic;
  signal internal_ceb2: std_logic;
  signal internal_cec: std_logic;
  signal internal_cep: std_logic;
  signal internal_cem: std_logic;
  signal internal_cecarryin: std_logic;
  signal internal_cectrl: std_logic;
  signal internal_ced: std_logic;
  signal internal_ceinmode: std_logic;
  signal internal_rsta : std_logic;
  signal internal_rstb : std_logic;
  signal internal_rstc : std_logic;
  signal internal_rstalumode : std_logic;
  signal internal_rstcarryin : std_logic;
  signal internal_rstctrl : std_logic;
  signal internal_rstd : std_logic;
  signal internal_rstinmode : std_logic;
  signal internal_rstm : std_logic;
  signal internal_cecinsub : std_logic;
  signal internal_rstp : std_logic;
  signal internal_opmode : std_logic_vector(6 downto 0);
  signal internal_alumode : std_logic_vector(3 downto 0);
  signal internal_cealumode : std_logic;
  signal internal_carryin : std_logic;
  signal internal_cemultcarryin : std_logic;
  signal internal_inmode : std_logic_vector(4 downto 0);
  signal internal_carryinsel : std_logic_vector(2 downto 0);
  signal internal_carryout : std_logic_vector(3 downto 0);
begin
  using_c_port: if (use_c_port = 1)
  generate
      generate_power_efficient_creg_enable : entity work.generatePowerEfficientEnable_dsp48e1
        generic map(
          use_reg => creg
        )
        port map(
          cereg => cec(0),
          ce => ce,
          en => en(0),
          internal_cereg => internal_cec
        );
      internal_rstc <= (rstc(0) or rst(0)) and ce;
  end generate;
  not_using_c_port: if (use_c_port = 0)
  generate
      internal_cec <= '0';
      internal_rstc <= '1';
  end generate;

  generate_power_efficient_mreg_enable : entity work.generatePowerEfficientEnable_dsp48e1
    generic map(
      use_reg => mreg
    )
    port map(
      cereg => cem(0),
      ce => ce,
      en => en(0),
      internal_cereg => internal_cem
    );
  generate_power_efficient_preg_enable : entity work.generatePowerEfficientEnable_dsp48e1
    generic map(
      use_reg => preg
    )
    port map(
      cereg => cep(0),
      ce => ce,
      en => en(0),
      internal_cereg => internal_cep
    );
  internal_cecarryin <= cecarryin(0) and ce and en(0);
  internal_cectrl <= cectrl(0) and ce and en(0);
  internal_ced <= ced(0) and ce and en(0);
  internal_ceinmode <= ceinmode(0) and ce and en(0);
  internal_cealumode <= cealumode(0) and ce and en(0);

  internal_rsta <= (rsta(0) or rst(0)) and ce;
  internal_rstb <= (rstb(0) or rst(0)) and ce;
  internal_rstcarryin <= (rstcarryin(0) or rst(0)) and ce;
  internal_rstctrl <= (rstctrl(0) or rst(0)) and ce;
  internal_rstd <= (rstd(0) or rst(0)) and ce;
  internal_rstinmode <= (rstinmode(0) or rst(0)) and ce;
  internal_rstalumode <= (rstalumode(0) or rst(0)) and ce;
  internal_rstm <= (rstm(0) or rst(0)) and ce;
  internal_rstp <= (rstp(0) or rst(0)) and ce;

  internal_cemultcarryin <= cemultcarryin(0) and ce and en(0);
  ceacontrol_1: if(areg = 1)
  generate
    internal_cea1 <= '0';
    internal_cea2 <= cea1(0) and ce and en(0);
  end generate;
  ceacontrol_2: if(areg = 2)
  generate
    internal_cea1 <= cea1(0) and ce and en(0);
    internal_cea2 <= cea2(0) and ce and en(0);
  end generate;
  ceacontrol_0: if(areg = 0)
  generate
    internal_cea1 <= '0';
    internal_cea2 <= '0';
  end generate;
  ceadcontrol_1: if(adreg = 1)
  generate
    internal_cead <= cead(0) and ce and en(0);
  end generate;
  ceadcontrol_0: if(adreg = 0)
  generate
    internal_cead <= '0';
  end generate;
  cebcontrol_1: if(breg = 1)
  generate
    internal_ceb1 <= '0';
    internal_ceb2 <= ceb1(0) and ce and en(0);
  end generate;
  cebcontrol_2: if(breg = 2)
  generate
    internal_ceb1 <= ceb1(0) and ce and en(0);
    internal_ceb2 <= ceb2(0) and ce and en(0);
  end generate;
  cebcontrol_0: if(breg = 0)
  generate
    internal_ceb1 <= '0';
    internal_ceb2 <= '0';
  end generate;
  opmode_0: if(use_op = 0)
  generate
        internal_opmode <= opmode;
  end generate;
  opmode_1: if(use_op = 1)
  generate
        internal_opmode <= op(6 downto 0);
  end generate;
  sub_0: if(use_op = 0)
  generate
        internal_alumode <= alumode;
  end generate;
  sub_1: if(use_op = 1)
  generate
        internal_alumode <= op(10 downto 7);
  end generate;
  carryin_0: if(use_op = 0)
  generate
        internal_carryin <= carryin(0);
  end generate;
  carryin_1: if(use_op = 1)
  generate
        internal_carryin <= op(11);
  end generate;
  carryinsel_0: if(use_op = 0)
  generate
        internal_carryinsel <= carryinsel;
  end generate;
  carryinsel_1: if(use_op = 1)
  generate
        internal_carryinsel <= op(14 downto 12);
  end generate;
  inmode_0: if(use_op  = 0)
  generate
        internal_inmode <= inmode;
  end generate;
  inmode_1: if(use_op  = 1)
  generate
        internal_inmode <= op(19 downto 15);
  end generate;
  dsp48e1_inst: DSP48E1
  generic map(
        ACASCREG        => acascreg,
                ADREG       => adreg,
        ALUMODEREG      => alumodereg,
        AREG            => areg,
        AUTORESET_PATDET                => autoreset_pattern_detect,
        A_INPUT         => a_input,
        BCASCREG        => bcascreg,
        BREG            => breg,
        B_INPUT         => b_input,
        CARRYINREG      => carryinreg,
        CARRYINSELREG   => carryinselreg,
        CREG            => creg,
        DREG            => dreg,
        INMODEREG            => inmodereg,
        MASK            => mask,
        MREG            => mreg,
        OPMODEREG       => opmodereg,
        PATTERN         => pattern,
        PREG            => preg,
        SEL_MASK        => sel_mask,
        SEL_PATTERN     => sel_pattern,
                USE_DPORT       => use_dport,
        USE_MULT                => use_mult,
        USE_PATTERN_DETECT      => use_pattern_detect,
        USE_SIMD                => use_simd
        )
  port map(
        ACOUT           => acout,
        BCOUT           => bcout,
        CARRYCASCOUT    => carrycascout(0),
        CARRYOUT        => internal_carryout,
        MULTSIGNOUT     => multsignout(0),
        OVERFLOW        => overflow(0),
        P               => p,
        PATTERNBDETECT  => patternbdetect(0),
        PATTERNDETECT   => patterndetect(0),
        PCOUT           => pcout,
        UNDERFLOW       => underflow(0),
        A               => a,
        ACIN            => acin,
        ALUMODE         => internal_alumode,
        B               => b,
        BCIN            => bcin,
        C               => c,
        CARRYCASCIN     => carrycascin(0),
        CARRYIN         => internal_carryin,
        CARRYINSEL      => internal_carryinsel,
        CEA1            => internal_cea1,
        CEA2            => internal_cea2,
                CEAD            => internal_cead,
        CEALUMODE       => internal_cealumode,
        CEB1            => internal_ceb1,
        CEB2            => internal_ceb2,
        CEC             => internal_cec,
        CECARRYIN       => internal_cecarryin,
        CECTRL          => internal_cectrl,
                CED             => internal_ced,
                CEINMODE        => internal_ceinmode,
        CEM             => internal_cem,
        CEP             => internal_cep,
        CLK             => clk,
                D               => d,
                INMODE          => internal_inmode,
        MULTSIGNIN      => multsignin(0),
        OPMODE          => internal_opmode,
        PCIN            => pcin,
        RSTA            => internal_rsta,
        RSTALLCARRYIN   => internal_rstcarryin,
        RSTALUMODE      => internal_rstalumode,
        RSTB            => internal_rstb,
        RSTC            => internal_rstc,
        RSTCTRL         => internal_rstctrl,
        RSTD            => internal_rstd,
        RSTINMODE       => internal_rstinmode,
        RSTM            => internal_rstm,
        RSTP            => internal_rstp
      );
  one48_mode : if (  use_simd = "ONE48") generate
      carryout(0) <= internal_carryout(3);
  end generate;
  two24_mode : if ( use_simd = "TWO24" ) generate
        carryout(1) <= internal_carryout(3);
        carryout(0) <= internal_carryout(1);
  end generate;
  four12_mode : if ( use_simd = "FOUR12" ) generate
        carryout <= internal_carryout;
  end generate;
end behavior;
