
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
library UNISIM;
use UNISIM.vcomponents.all;
entity mfcm_mac_syn is
        generic
        (
                MULT_TYPE:      integer:=2;
                A_WIDTH:        integer:=18;
                B_WIDTH:        integer:=18;
                X_WIDTH:        integer:=48;
                X_DEPTH:        integer:=1
        );
        port
        (
                clk:            in  std_logic;
                ce:                             in      std_logic;
                a:                              in      std_logic_vector(A_WIDTH-1 downto 0);
                b:                              in      std_logic_vector(B_WIDTH-1 downto 0);
                op_acc:         in  std_logic;
                op_sub:         in  std_logic;
                x:                              out     std_logic_vector(X_WIDTH-1 downto 0)
        );
end entity;
architecture rtl of mfcm_mac_syn is
        constant P_WIDTH: integer:=A_WIDTH+B_WIDTH;
        subtype XWord is signed(X_WIDTH-1 downto 0);
        type XPipe is array(X_DEPTH downto 0) of XWord;
        signal xx: XPipe:=(others=>XWord'(others=>'0'));
        component DSP48
                generic
                (
                        AREG : integer := 1;
                        B_INPUT : string := "DIRECT";
                        BREG : integer := 1;
                        CARRYINREG : integer := 1;
                        CARRYINSELREG : integer := 1;
                        CREG : integer := 1;
                        LEGACY_MODE : string := "MULT18X18S";
                        MREG : integer := 1;
                        OPMODEREG : integer := 1;
                        PREG : integer := 1;
                        SUBTRACTREG : integer := 1
                );
                port
                (
                        BCOUT : out std_logic_vector(17 downto 0);
                        P : out std_logic_vector(47 downto 0);
                        PCOUT : out std_logic_vector(47 downto 0);
                        A : in std_logic_vector(17 downto 0);
                        B : in std_logic_vector(17 downto 0);
                        BCIN : in std_logic_vector(17 downto 0);
                        C : in std_logic_vector(47 downto 0);
                        CARRYIN : in std_ulogic;
                        CARRYINSEL : in std_logic_vector(1 downto 0);
                        CEA : in std_ulogic;
                        CEB : in std_ulogic;
                        CEC : in std_ulogic;
                        CECARRYIN : in std_ulogic;
                        CECINSUB : in std_ulogic;
                        CECTRL : in std_ulogic;
                        CEM : in std_ulogic;
                        CEP : in std_ulogic;
                        CLK : in std_ulogic;
                        OPMODE : in std_logic_vector(6 downto 0);
                        PCIN : in std_logic_vector(47 downto 0);
                        RSTA : in std_ulogic;
                        RSTB : in std_ulogic;
                        RSTC : in std_ulogic;
                        RSTCARRYIN : in std_ulogic;
                        RSTCTRL : in std_ulogic;
                        RSTM : in std_ulogic;
                        RSTP : in std_ulogic;
                        SUBTRACT : in std_ulogic
                );
        end component;
        constant DSP48_MUL : std_logic_vector(6 downto 0):="000" & "01" & "01";
        constant DSP48_MACP: std_logic_vector(6 downto 0):="010" & "01" & "01";
        constant DSP48_MACC: std_logic_vector(6 downto 0):="011" & "01" & "01";
begin
        assert MULT_TYPE>=0 and MULT_TYPE<=2
                report "Unrecognised MULT_TYPE value"
                severity failure;
        assert X_DEPTH>=1 and X_DEPTH<=2
                report "X_DEPTH must be 1 or 2"
                severity failure;
        MT0: if MULT_TYPE=0 generate
                signal a0:      signed(A_WIDTH-1 downto 0):=(others=>'0');
                signal b0:      signed(B_WIDTH-1 downto 0):=(others=>'0');
                signal op_acc0: std_logic:='0';
                signal op_sub0: std_logic:='0';
                signal p1:      signed(P_WIDTH-1 downto 0):=(others=>'0');
                signal op_acc1: std_logic:='0';
                signal op_sub1: std_logic:='0';
                signal p2:      signed(P_WIDTH-1 downto 0):=(others=>'0');
                signal op_acc2: std_logic:='0';
                signal op_sub2: std_logic:='0';
        begin
                x<=std_logic_vector(xx(0));
                regProc: process (clk)
                        variable c: signed(X_WIDTH-1 downto 0);
                begin
                        if rising_edge(clk) then
                                if (ce='1') then
                                        a0     <=signed(a);
                                        b0     <=signed(b);
                                        op_acc0<=op_acc;
                                        op_sub0<=op_sub;
                                        p1     <=a0*b0;
                                        op_acc1<=op_acc0;
                                        op_sub1<=op_sub0;
                                        p2     <=p1;
                                        op_acc2<=op_acc1;
                                        op_sub2<=op_sub1;
                                        if (op_acc2='1') then
                                                c:=xx(X_DEPTH-1);
                                        else
                                                c:=(others=>'0');
                                        end if;
                                        if (op_sub2='1') then
                                                xx<=xx(X_DEPTH-1 downto 0) & (c-p2);
                                        else
                                                xx<=xx(X_DEPTH-1 downto 0) & (c+p2);
                                        end if;
                                end if;
                        end if;
                end process;
        end generate;
        MT1: if MULT_TYPE=1 or (MULT_TYPE=2 and (A_WIDTH>18 or B_WIDTH>18 or X_WIDTH>48)) generate
                signal a0:      signed(A_WIDTH-1 downto 0):=(others=>'0');
                signal b0:      signed(B_WIDTH-1 downto 0):=(others=>'0');
                signal op_acc0: std_logic:='0';
                signal op_sub0: std_logic:='0';
                signal a1:      signed(A_WIDTH-1 downto 0):=(others=>'0');
                signal b1:      signed(B_WIDTH-1 downto 0):=(others=>'0');
                signal op_acc1: std_logic:='0';
                signal op_sub1: std_logic:='0';
                signal p2:      signed(P_WIDTH-1 downto 0):=(others=>'0');
                signal op_acc2: std_logic:='0';
                signal op_sub2: std_logic:='0';
        begin
                x<=std_logic_vector(xx(0));
                regProc: process (clk)
                        variable c: signed(X_WIDTH-1 downto 0);
                begin
                        if rising_edge(clk) then
                                if (ce='1') then
                                        a0     <=signed(a);
                                        b0     <=signed(b);
                                        op_acc0<=op_acc;
                                        op_sub0<=op_sub;
                                        a1     <=a0;
                                        b1     <=b0;
                                        op_acc1<=op_acc0;
                                        op_sub1<=op_sub0;
                                        p2     <=a1*b1;
                                        op_acc2<=op_acc1;
                                        op_sub2<=op_sub1;
                                        if (op_acc2='1') then
                                                c:=xx(X_DEPTH-1);
                                        else
                                                c:=(others=>'0');
                                        end if;
                                        if (op_sub2='1') then
                                                xx<=xx(X_DEPTH-1 downto 0) & (c-p2);
                                        else
                                                xx<=xx(X_DEPTH-1 downto 0) & (c+p2);
                                        end if;
                                end if;
                        end if;
                end process;
        end generate;
        MT2_1: if (MULT_TYPE=2 and A_WIDTH<=18 and B_WIDTH<=18 and X_WIDTH<=48 and X_DEPTH=1) generate
                signal AA: std_logic_vector(17 downto 0);
                signal BB: std_logic_vector(17 downto 0);
                signal CC: std_logic_vector(47 downto 0);
                signal PP: std_logic_vector(47 downto 0);
                signal OPMODE:   std_logic_vector(6 downto 0);
                signal SUBTRACT: std_logic;
                signal BCIN:  std_logic_vector(17 downto 0);
                signal BCOUT: std_logic_vector(17 downto 0);
                signal PCIN:  std_logic_vector(47 downto 0);
                signal PCOUT: std_logic_vector(47 downto 0);
                signal op_acc0: std_logic:='0';
                signal op_sub0: std_logic:='0';
                signal op_acc1: std_logic:='0';
                signal op_sub1: std_logic:='0';
        begin
                AA<=std_logic_vector(resize(signed(a),18));
                BB<=std_logic_vector(resize(signed(b),18));
                CC<=(others=>'0');
                x <=std_logic_vector(resize(signed(PP),X_WIDTH));
                reg_proc: process (clk)
                begin
                        if rising_edge(clk) then
                                if (ce='1') then
                                        op_acc0<=op_acc;
                                        op_sub0<=op_sub;
                                        op_acc1<=op_acc0;
                                        op_sub1<=op_sub0;
                                end if;
                        end if;
                end process;
                OPMODE  <=DSP48_MACP when op_acc1='1' else DSP48_MUL;
                SUBTRACT<=                op_sub1;
                i0: DSP48
                        generic map (
                                AREG=>2, BREG=>2, B_INPUT => "DIRECT",
                                CARRYINREG=>1, CARRYINSELREG=>1,
                                CREG=>1, LEGACY_MODE=>"MULT18X18S",
                                MREG=>1, OPMODEREG=>1, PREG=>1, SUBTRACTREG=>1)
                        port map (
                                A=>AA, B=>BB, C=>CC, P=>PP,
                                CARRYIN=>'0', CARRYINSEL=>"00",
                                CLK=>clk, CEA=>ce, CEB=>ce, CEC=>ce, CECARRYIN=>ce, CECINSUB=>ce, CECTRL=>ce, CEM=>ce, CEP=>ce,
                                OPMODE=>OPMODE, SUBTRACT=>SUBTRACT,
                                BCIN=>BCIN, PCIN=>PCIN, BCOUT=>BCOUT, PCOUT=>PCOUT,
                                RSTA=>'0', RSTB=>'0', RSTC=>'0', RSTCARRYIN=>'0', RSTCTRL=>'0', RSTM=>'0', RSTP=>'0');
        end generate;
        MT2_2: if (MULT_TYPE=2 and A_WIDTH<=18 and B_WIDTH<=18 and X_WIDTH<=48 and X_DEPTH=2) generate
                signal AA: std_logic_vector(17 downto 0);
                signal BB: std_logic_vector(17 downto 0);
                signal CC: std_logic_vector(47 downto 0);
                signal PP: std_logic_vector(47 downto 0);
                signal OPMODE:   std_logic_vector(6 downto 0);
                signal SUBTRACT: std_logic;
                signal BCIN:  std_logic_vector(17 downto 0);
                signal BCOUT: std_logic_vector(17 downto 0);
                signal PCIN:  std_logic_vector(47 downto 0);
                signal PCOUT: std_logic_vector(47 downto 0);
                signal op_acc0: std_logic:='0';
                signal op_sub0: std_logic:='0';
                signal op_acc1: std_logic:='0';
                signal op_sub1: std_logic:='0';
        begin
                AA<=std_logic_vector(resize(signed(a),18));
                BB<=std_logic_vector(resize(signed(b),18));
                CC<=PP;
                x <=std_logic_vector(resize(signed(PP),X_WIDTH));
                reg_proc: process (clk)
                begin
                        if rising_edge(clk) then
                                if (ce='1') then
                                        op_acc0<=op_acc;
                                        op_sub0<=op_sub;
                                        op_acc1<=op_acc0;
                                        op_sub1<=op_sub0;
                                end if;
                        end if;
                end process;
                OPMODE  <=DSP48_MACC when op_acc1='1' else DSP48_MUL;
                SUBTRACT<=                op_sub1;
                i0: DSP48
                        generic map (
                                AREG=>2, BREG=>2, B_INPUT => "DIRECT",
                                CARRYINREG=>1, CARRYINSELREG=>1,
                                CREG=>1, LEGACY_MODE=>"MULT18X18S",
                                MREG=>1, OPMODEREG=>1, PREG=>1, SUBTRACTREG=>1)
                        port map (
                                A=>AA, B=>BB, C=>CC, P=>PP,
                                CARRYIN=>'0', CARRYINSEL=>"00",
                                CLK=>clk, CEA=>ce, CEB=>ce, CEC=>ce, CECARRYIN=>ce, CECINSUB=>ce, CECTRL=>ce, CEM=>ce, CEP=>ce,
                                OPMODE=>OPMODE, SUBTRACT=>SUBTRACT,
                                BCIN=>BCIN, PCIN=>PCIN, BCOUT=>BCOUT, PCOUT=>PCOUT,
                                RSTA=>'0', RSTB=>'0', RSTC=>'0', RSTCARRYIN=>'0', RSTCTRL=>'0', RSTM=>'0', RSTP=>'0');
        end generate;
end architecture;
