
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
module xlfpconvert (din, ce, clr, clk, rst, en, dout);
parameter core_name0= "";
parameter din_width= 32;
parameter din_bin_pt= 24;
parameter din_arith= `xlFloat;
parameter dout_width= 32;
parameter dout_bin_pt= 24;
parameter dout_arith= `xlFloat;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter rst_arith= `xlUnsigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter din_tdata_width= 32;
parameter dout_tdata_width= 32;
parameter extra_registers= 0;
parameter latency= 0;
parameter quantization= `xlTruncate;
parameter overflow= `xlWrap;
parameter c_latency= 0;
input [din_width-1:0] din;
input ce, clr, clk, rst, en;
output [dout_width-1:0] dout;
wire  a_tvalid_net;
wire  [din_tdata_width-1:0] a_tdata;
wire  result_tvalid_net;
wire  [dout_tdata_width-1:0] result_tdata;
wire  internal_clr;
wire  internal_ce;
wire  [dout_width-1:0] result;
assign internal_clr = (clr | rst) & ce;
assign internal_ce = ce & en;
assign a_tvalid_net = 1'b1;
assign a_tdata[din_width-1:0] = din[din_width-1:0];
assign result[dout_width-1:0] = result_tdata[dout_width-1:0];
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
generate
  if (extra_registers > 0)
  begin:latency_gt_0
    synth_reg # (dout_width, extra_registers)
      extra_reg (
        .i(result),
        .ce(internal_ce),
        .clr(internal_clr),
        .clk(clk),
        .o(dout));
  end
endgenerate
generate
  if (extra_registers == 0)
  begin:latency_eq_0
    assign dout = result;
  end
endgenerate
endmodule
