
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
module xladdrsr (d, addr, ce, clr, clk, en, q);
parameter core_name0= "";
parameter addr_arith= `xlSigned;
parameter addr_bin_pt= 7;
parameter addr_width= 12;
parameter core_addr_width = 0;
parameter d_arith= `xlSigned;
parameter d_bin_pt= 7;
parameter d_width= 12;
parameter q_arith= `xlSigned;
parameter q_bin_pt= 7;
parameter q_width= `xlSigned;
parameter uid= 0;
input [d_width-1:0] d;
input [addr_width-1:0] addr;
input ce, clr, clk, en;
output [d_width-1:0] q;
wire internal_ce;
wire [d_width-1:0] #0.1 tmp_d;
wire [addr_width-1:0] #0.1 tmp_addr;
wire [core_addr_width-1:0] padded_addr;
assign internal_ce = ce & en;
assign tmp_d = d;
assign tmp_addr = addr;
assign padded_addr = {{(core_addr_width-addr_width){1'b0}}, addr};
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
