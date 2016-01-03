
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
-- synopsys translate_off
library XilinxCoreLib;
-- synopsys translate_on
use IEEE.std_logic_1164.all;
use work.conv_pkg.all;

entity [% entity_name %] is
  generic (
      [% generic_map %]
    );
    port (
        [% port_map %]
     );
end [% entity_name %];

architecture behavior of [% entity_name %] is
    signal core_ce, core_rst, reg_rst, core_done, tmp_done : std_logic;
    signal core_ovflo, core_edone : std_logic_vector(0 downto 0);
    signal core_blkexp : std_logic_vector(4 downto 0);
    [% temp_signals %]


    component synth_reg_w_init
        generic (width       : integer;
                 init_index  : integer;
                 latency     : integer);
        port (i           : in std_logic_vector(width-1 downto 0);
              ce      : in std_logic;
              clr     : in std_logic;
              clk     : in std_logic;
              o       : out std_logic_vector(width-1 downto 0));
    end component;

    component [% core_name %]
     port (
       [% core_component_def %]
       );
    end component;

    attribute syn_black_box of [% core_name %] : component is true;
    attribute fpga_dont_touch of [% core_name %] : component is "true";
    attribute box_type of [% core_name %] :  component  is "black_box";

begin

    core_rst <=  rst(0) and ce;
    reg_rst <= (rst(0) or nfft_we(0)) and ce;
    core_ce <= (ce and en(0)) or core_rst;

      core_instance : [% core_name %]
       port map (
           [% core_instance_text %]
       );

  tmp_done <= core_ce and core_edone(0) after 200 ps;
  latch_ovflo : if (c_arch /= 3) and (c_has_scaling = 1) and (c_has_bfp = 0) generate
        ovflo_reg : synth_reg_w_init
        generic map ( width => 1,
                      init_index => 0,
                      latency => 1)
        port map (i => core_ovflo,
                  ce => tmp_done,
                  clr => reg_rst,
                  clk => clk,
                  o => ovflo);
  end generate;

  latch_blkexp : if (c_arch /= 3) and (c_has_scaling = 1) and (c_has_bfp = 1) generate
        blkexp_reg : synth_reg_w_init
        generic map ( width => 5,
                      init_index => 0,
                      latency => 1)
        port map (i => core_blkexp,
                  ce => tmp_done,
                  clr => reg_rst,
                  clk => clk,
                  o => blk_exp);
  end generate;

  pass_ovflo_blkexp  : if (c_arch = 3)  generate
    ovflo <= core_ovflo;
   end generate;

  done_reg : synth_reg_w_init
    generic map ( width => 1,
                  init_index => 0,
                  latency => 1)
    port map (i => core_edone,
              ce => core_ce,
              clr => reg_rst,
              clk => clk,
              o => done);
   edone <= core_edone;
end  behavior;






















