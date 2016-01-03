
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
module xlfir_compiler ( din, dout, chan_in, chan_out, coef_din, coef_ld, coef_we, en, rst, vin, vout, rfd, src_ce, src_clk, dest_ce, dest_clk, core_ce, core_clk);
parameter core_name0 = "";
parameter din_width = 16;
parameter dout_width = 16;
parameter dout_type = 0;
parameter core_dout_width = 16;
parameter chan_in_width = 1;
parameter chan_out_width = 1;
parameter coef_din_width = 1;
parameter use_core_chan_in = 0;
input [din_width-1:0] din;
output [dout_width-1:0] dout;
output [chan_in_width-1:0] chan_in;
output [chan_out_width-1:0] chan_out;
input [coef_din_width-1:0] coef_din;
input coef_ld, coef_we, en, rst, vin, src_ce, src_clk, dest_ce, dest_clk, core_ce, core_clk;
output vout,rfd;

wire core_rfd;
reg din_rfd;
wire inter_rfd;
wire internal_nd;
wire [core_dout_width-1:0] core_dout;
wire [chan_in_width-1:0] core_chan_in;
reg [core_dout_width-1:0] dout_reg;
reg vout_reg;
wire [chan_out_width-1:0] core_chan_out;
reg [chan_out_width-1:0] chan_out_reg;
wire internal_core_ce;
wire internal_src_ce;
wire internal_dest_ce;
wire [0:0] latched_core_rdy_vec;
wire predicated_core_rdy;
wire [0:0] predicated_core_rdy_vec;
wire [0:0] ored_latched_core_rdy_vec;
wire [0:0] vout_vec;
wire core_rdy;
wire [core_dout_width-1:0] adjusted_rate_core_dout;
wire internal_rst_src;
wire internal_rst_core;
wire internal_rst_dest;
assign internal_nd = src_ce & vin;
assign internal_core_ce = core_ce & en;
assign internal_src_ce = src_ce & en;
assign internal_dest_ce = dest_ce & en;
assign internal_rst_src = src_ce & rst;
assign internal_rst_core = core_ce & rst;
assign internal_rst_dest = dest_ce & rst;
assign rfd = core_rfd;
extend_msb # ( .old_width(core_dout_width), .new_arith(dout_type), .new_width(dout_width))
extend_msb_process(.inp(adjusted_rate_core_dout),.res(dout));
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
assign predicated_core_rdy = (!internal_dest_ce) & (core_rdy | latched_core_rdy_vec[0]);
assign predicated_core_rdy_vec[0] = predicated_core_rdy;
synth_reg_w_init # (1)
    latch_rdy_for_vout(
        .i(predicated_core_rdy_vec),
        .ce(internal_core_ce),
        .clr(internal_rst_core),
        .clk(core_clk),
        .o(latched_core_rdy_vec)
    );
assign ored_latched_core_rdy_vec[0] = latched_core_rdy_vec[0] | core_rdy;
synth_reg_w_init # (core_dout_width)
    latch_data_out(
        .i(core_dout),
        .ce(internal_dest_ce),
        .clr(internal_rst_dest),
        .clk(core_clk),
        .o(adjusted_rate_core_dout)
    );
synth_reg_w_init # (chan_out_width)
    latch_chan_out(
        .i(core_chan_out),
        .ce(internal_dest_ce),
        .clr(internal_rst_dest),
        .clk(core_clk),
        .o(chan_out)
    );
synth_reg_w_init # (1)
    latch_vout(
        .i(ored_latched_core_rdy_vec),
        .ce(internal_dest_ce),
        .clr(internal_rst_dest),
        .clk(core_clk),
        .o(vout_vec)
    );
generate
if (use_core_chan_in != 1)
  begin:USE_CORE_CHAN_IN_LATCHED
    synth_reg_w_init # (chan_in_width)
      latch_core_chan_in(
        .i(core_chan_in),
        .ce(internal_src_ce),
        .clr(internal_rst_src),
        .clk(src_clk),
        .o(chan_in)
      );
  end
else
  begin:USE_CORE_CHAN_IN_DIRECT
    assign chan_in = core_chan_in;
  end
endgenerate
assign vout = vout_vec;
endmodule
