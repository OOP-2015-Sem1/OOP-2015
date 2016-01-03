
//-----------------------------------------------------------------
// System Generator version 13.4 VERILOG source file.
//
// Copyright(C) 2011 by Xilinx, Inc.  All rights reserved.  This
// text/file contains proprietary, confidential information of Xilinx,
// Inc., is distributed under license from Xilinx, Inc., and may be used,
// copied and/or disclosed only pursuant to the terms of a valid license
// agreement with Xilinx, Inc.  Xilinx hereby grants you a license to use
// this text/file solely for design, simulation, implementation and
// creation of design files limited to Xilinx devices or technologies.
// Use with non-Xilinx devices or technologies is expressly prohibited
// and immediately terminates your license unless covered by a separate
// agreement.
//
// Xilinx is providing this design, code, or information "as is" solely
// for use in developing programs and solutions for Xilinx devices.  By
// providing this design, code, or information as one possible
// implementation of this feature, application or standard, Xilinx is
// making no representation that this implementation is free from any
// claims of infringement.  You are responsible for obtaining any rights
// you may require for your implementation.  Xilinx expressly disclaims
// any warranty whatsoever with respect to the adequacy of the
// implementation, including but not limited to warranties of
// merchantability or fitness for a particular purpose.
//
// Xilinx products are not intended for use in life support appliances,
// devices, or systems.  Use in such applications is expressly prohibited.
//
// Any modifications that are made to the source code are done at the user's
// sole risk and will be unsupported.
//
// This copyright and support notice must be retained as part of this
// text at all times.  (c) Copyright 1995-2011 Xilinx, Inc.  All rights
// reserved.
//-----------------------------------------------------------------
module xlaxififogen (
        s_aclk,
        ce,
        aresetn,
        axis_underflow,
        axis_overflow,
        axis_data_count,
        axis_prog_full_thresh,
        axis_prog_empty_thresh,
        s_axis_tdata,
        s_axis_tstrb,
        s_axis_tkeep,
        s_axis_tlast,
        s_axis_tid,
        s_axis_tdest,
        s_axis_tuser,
        s_axis_tvalid,
        s_axis_tready,
        m_axis_tdata,
        m_axis_tstrb,
        m_axis_tkeep,
        m_axis_tlast,
        m_axis_tid,
        m_axis_tdest,
        m_axis_tuser,
        m_axis_tvalid,
        m_axis_tready
);
  parameter core_name0 = "";
  parameter has_aresetn = -1;
  parameter tdata_width = -1;
  parameter tdest_width = -1;
  parameter tstrb_width = -1;
  parameter tkeep_width = -1;
  parameter tid_width = -1;
  parameter tuser_width = -1;
input ce;
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = port_declarations_text.$i                                      -%]
[% inst_txt %]
[% END -%]
  wire srst;
  reg reset_gen1 = 1'b0;
  reg reset_gen_d1 = 1'b0;
  reg reset_gen_d2 = 1'b0;
  always @(posedge s_aclk)
  begin
        reset_gen1 <= 1'b1;
        reset_gen_d1 <= reset_gen1;
        reset_gen_d2 <= reset_gen_d1;
  end
  generate
  if(has_aresetn == 0)
  begin:if_block
        assign srst = reset_gen_d2;
  end
  else
  begin:else_block
    assign srst = ~((~aresetn) & ce);
  end
  endgenerate
  generate
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                      -%]
  if (core_name0 == "[% name %]")
    begin:comp[% i %]
    [% name %] core_instance[% i %] (
[% inst_txt %]
      );
  end
[% END -%]
  endgenerate
endmodule
