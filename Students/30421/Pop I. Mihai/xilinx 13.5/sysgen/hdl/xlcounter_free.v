
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
module xlcounter_free (ce, clr, clk, op, up, load, din, en, rst);
parameter core_name0= "";
parameter op_width= 5;
parameter op_arith= `xlSigned;
input ce, clr, clk;
input up, load;
input [op_width-1:0] din;
input en, rst;
output [op_width-1:0] op;
parameter [(8*op_width)-1:0] oneStr = { op_width{"1"}};
wire  core_sinit;
wire  core_ce;
wire [op_width-1:0] op_net;
   assign core_ce = ce & en;
   assign core_sinit = (clr | rst) & ce;
   assign op = op_net;
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
