
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
use work.conv_pkg.all;
entity [% entity_name %] is
   generic (
[% port_names = port_names.replace('\s*,\s*\]\s*$', '') -%]
[% port_names = port_names.replace('^\s*\[\s*', '') -%]
[% port_names = port_names.replace("'", '') -%]
[% port_names = port_names.split(',') -%]
[% FOREACH port_name = port_names -%]
            [% port_name %]_width  : integer := 12;
            [% port_name %]_bin_pt : integer := 7;
            [% port_name %]_arith  : integer := xlSigned;
[% END -%]
            full_width  : integer := 12;
            full_bin_pt : integer := 7;
            full_arith  : integer := xlSigned;
            dout_width  : integer := 12;
            dout_bin_pt : integer := 7;
            dout_arith  : integer := xlSigned;
            en_width    : integer := 1;
            en_bin_pt   : integer := 0;
            en_arith    : integer := xlUnsigned;
            latency     : integer := 1
   );
   port (
[% FOREACH port_name = port_names -%]
         [% port_name %]        : in std_logic_vector ([% port_name %]_width-1 downto 0);
[% END -%]
         ce         : in std_logic;
         clr        : in std_logic;
         clk        : in std_logic;
         en         : in std_logic_vector(en_width-1 downto 0);
         dout       : out std_logic_vector (dout_width-1 downto 0));
end [% entity_name %];
architecture behavior of [% entity_name %] is
   component synth_reg
      generic (width   : integer;
               latency : integer);
      port (i   : in std_logic_vector(width-1 downto 0);
            ce  : in std_logic;
            clr : in std_logic;
            clk : in std_logic;
            o   : out std_logic_vector(width-1 downto 0));
   end component;
   signal full_dout_unreg : std_logic_vector (full_width-1 downto 0);
   signal full_dout_reg   : std_logic_vector (full_width-1 downto 0);
   signal user_dout       : std_logic_vector (dout_width-1 downto 0);
   signal internal_ce     : std_logic;
[% num_inputs_minus_1 = num_inputs - 1 -%]
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
   signal full_din[% i %] : std_logic_vector (full_width-1 downto 0);
[% END -%]
   constant quantization : integer := xlTruncate;
   constant overflow     : integer := xlWrap;
begin
   internal_ce  <= ce and (en(0));
[% FOREACH name = port_names -%]
   full_din[% loop.index %] <= align_input([% name %],
                                 [% name %]_width,
                                 full_bin_pt - [% name %]_bin_pt,
                                 [% name %]_arith,
                                 full_width);
[% END -%]
   full_dout_unreg <= [% vhdl_expr %];
   latency_gt_0 : if (latency > 0) generate
      reg_inst : synth_reg
         generic map (width   => full_width,
                      latency => 1)
         port map (i   => full_dout_unreg,
                   ce  => internal_ce,
                   clr => clr,
                   clk => clk,
                   o   => full_dout_reg);
   end generate;
   latency_eq_0 : if (latency = 0) generate
      full_dout_reg <= full_dout_unreg;
   end generate;
   user_dout <= convert_type(full_dout_reg, full_width, full_bin_pt, full_arith,
                             dout_width, dout_bin_pt, dout_arith, quantization,
                             overflow);
   extra_latency : if (latency > 1) generate
      latency_pipe : synth_reg
         generic map (width   => dout_width,
                      latency => latency-1)
         port map (i   => user_dout,
                   ce  => internal_ce,
                   clr => clr,
                   clk => clk,
                   o   => dout);
   end generate;
   no_extra_latency : if (latency = 0 or latency = 1) generate
      dout <= user_dout;
   end generate;
end architecture behavior;
