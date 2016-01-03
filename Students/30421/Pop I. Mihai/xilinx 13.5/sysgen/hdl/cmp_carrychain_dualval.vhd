
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
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
use ieee.numeric_std.all;
library unisim;
use unisim.VComponents.all;
library work;
use work.mac_fir_utils_pkg.all;
use work.mac_fir_const_pkg.all;
use work.mac_fir_comp_pkg.all;
entity cmp_carrychain_dualval is
generic(
        width: integer:=5;
        value1: integer:=6;
        value2: integer:=5;
        c_family: string:="virtex4"
);
port(
     cin: in std_logic;
     A:in std_logic_vector(width-1 downto 0);
     sel: in std_logic;
     Res:out std_logic;
     cout: out std_logic;
     ce: in std_logic;
     sclr: in std_logic;
     clk: in std_logic
);
end cmp_carrychain_dualval;
architecture arch1 of cmp_carrychain_dualval is
constant LUT_width: integer:=UTILS_PKG_select_integer(4,6,c_family="virtex5");
constant bits_per_compare: integer:=LUT_width-1;
constant num_compares: integer:=(width/bits_per_compare)+UTILS_PKG_select_integer(0,1,width rem bits_per_compare >0);
constant const_slv1: std_logic_vector(width-1 downto 0):=std_logic_vector(to_signed(value1,width));
constant const_slv2: std_logic_vector(width-1 downto 0):=std_logic_vector(to_signed(value2,width));
signal  lut_ops,
        muxcy_ops : std_logic_vector(num_compares downto 0);
signal  local_op:std_logic;
begin
gen_loop: for i in 1 to ((num_compares)*bits_per_compare) generate
  gen_compare: if ( i rem (bits_per_compare) = 0) generate
    LUT:process(a,muxcy_ops,lut_ops,sel)
    begin
        if ( (A(minf(i-1,width-1) downto i-(bits_per_compare))=const_slv1(minf(i-1,width-1) downto i-(bits_per_compare)) and sel='0')
            or (A(minf(i-1,width-1) downto i-(bits_per_compare))=const_slv2(minf(i-1,width-1) downto i-(bits_per_compare))and sel='1')) then
          lut_ops( (i/(bits_per_compare))-1)<='1';
        else
          lut_ops( (i/(bits_per_compare))-1)<='0';
        end if;
    end process;
    n: if (i /= (num_compares*bits_per_compare) ) generate
      MUX: MUXCY
      port map(
        O   => muxcy_ops(i/(bits_per_compare)),

        CI  =>muxcy_ops( (i/(bits_per_compare))-1),
        DI  =>'0',
        S   => lut_ops( (i/(bits_per_compare))-1)
      );
    end generate n;

    l: if ( i = (num_compares*bits_per_compare) ) generate

      MUX_L: MUXCY_D
      port map(
        O   => muxcy_ops(i/(bits_per_compare)),
        LO  => local_op,

        CI  =>muxcy_ops( (i/(bits_per_compare))-1),
        DI  =>'0',
        S   => lut_ops( (i/(bits_per_compare))-1)
      );
    end generate l;

  end generate gen_compare;
end generate gen_loop;
muxcy_ops(0)<=cin;
cout<=muxcy_ops(num_compares);
i_reg: FDRE
generic map(
  INIT => '0'
)
port map(
  C => clk,
  CE => ce,
  R => sclr,
  D => local_op,
  Q => Res
);
end;
