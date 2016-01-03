
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
module xlfpsqroot (a, ce, clr, clk, a_tvalid, a_tlast, a_tuser, rst, en, result_tready, a_tready, result_tvalid, result_tlast, invalid_op, op);
parameter core_name0= "";
parameter a_width= 32;
parameter a_bin_pt= 24;
parameter a_arith= `xlFloat;
parameter op_width= 32;
parameter op_bin_pt= 24;
parameter op_arith= `xlFloat;
parameter a_tuser_width= 1;
parameter result_tuser_width= 1;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter rst_arith= `xlUnsigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter a_tdata_width= 32;
parameter op_tdata_width= 32;
parameter extra_registers= 0;
parameter latency= 0;
parameter quantization= `xlTruncate;
parameter overflow= `xlWrap;
parameter c_latency= 0;
parameter c_blocking= 0;
parameter c_invalid_op= 0;
parameter c_invalid_op_idx= 0;
input [a_width-1:0] a;
input ce, clr, clk;
input a_tvalid, a_tlast;
input [a_tuser_width-1:0] a_tuser;
input rst, en;
output result_tready;
output a_tready;
output result_tvalid, result_tlast;
output invalid_op;
output [op_width-1:0] op;
wire  a_tvalid_net, a_tready_net, a_tlast_net;
wire  [a_tdata_width-1:0] a_tdata;
wire  result_tvalid_net, result_tready_net, result_tlast_net;
wire  [op_tdata_width-1:0] result_tdata;
wire  internal_clr;
wire  internal_ce;
wire  [result_tuser_width-1:0] result_tuser;
wire  [op_width-1:0] result;
assign internal_clr = (clr | rst) & ce;
assign internal_ce = ce & en;
generate
  if (c_blocking > 0)
  begin:axi_handshake_on
    assign a_tvalid_net = a_tvalid;
    assign result_tvalid = result_tvalid_net;
  end
endgenerate
generate
  if (c_blocking == 0)
  begin:axi_handshake_off
    assign a_tvalid_net = 1'b1;
  end
endgenerate
assign a_tlast_net = a_tlast;
assign result_tready_net = result_tready;
assign a_tready = a_tready_net;
assign result_tlast = result_tlast_net;
assign a_tdata[a_width-1:0] = a[a_width-1:0];
assign result[op_width-1:0] = result_tdata[op_width-1:0];
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
    synth_reg # (op_width, extra_registers)
      extra_reg (
        .i(result),
        .ce(internal_ce),
        .clr(internal_clr),
        .clk(clk),
        .o(op));
  end
endgenerate
generate
  if (extra_registers == 0)
  begin:latency_eq_0
    assign op = result;
  end
endgenerate
generate
  if (c_invalid_op == 1)
  begin:tie_invalid_op
    assign invalid_op = result_tuser[c_invalid_op_idx];
  end
endgenerate
endmodule
