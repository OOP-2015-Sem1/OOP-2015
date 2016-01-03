
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
module xlnegate (a, ce, clr, clk, rst, en, p);
parameter core_name0= "";
parameter a_width= 12;
parameter a_bin_pt= 7;
parameter a_arith= `xlSigned;
parameter p_width= 13;
parameter p_bin_pt= 7;
parameter p_arith= `xlSigned;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter en_width= 5;
parameter en_bin_pt= 2;
parameter en_arith= `xlUnsigned;
parameter rst_arith= `xlUnsigned;
parameter latency= 1;
parameter quantization= `xlRound;
parameter overflow= `xlSaturate;
parameter c_width= 12;
parameter c_has_s= 0;
parameter c_has_q= 1;
parameter c_enable_rlocs= 1;
parameter core= 0;
parameter zeroStr = {c_width{"0"}};
input [a_width-1:0] a;
input  ce;
input  clr;
input  clk;
input [rst_width-1:0] rst;
input [en_width-1:0] en;
output [p_width-1:0] p;
wire [c_width-1:0] full_a;
wire [c_width:0] full_s,full_q;
wire [p_width-1:0] conv_p;
wire [c_width:0] core_p;
wire  internal_clr;
wire  internal_ce;
assign internal_clr = (clr | rst[0]) & ce;
assign internal_ce  = (ce & en[0]);
extend_msb #  (a_width,c_width,a_arith) extend_a(.inp(a),.res(full_a));
convert_type # (c_width + 1, a_bin_pt, `xlSigned,
                         p_width, p_bin_pt, p_arith, quantization, overflow)
convert_p(.inp(core_p),.res(conv_p));

generate
if (!core)
        begin:synth_behavioral_inst
              assign p = conv_p;
              synth_negate # (c_width, latency)
              neg_reg1(
                    .a(full_a),
                    .ce(internal_ce),
                    .clr(internal_clr),
                    .clk(clk),
                    .p(core_p));
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
generate
 if ((latency > 1) && (core))
     begin:latency_gt_0
        assign core_p = full_q;
        synth_reg # (p_width, latency-1)
          reg1 (
               .i(conv_p),
               .ce(internal_ce),
               .clr(internal_clr),
               .clk(clk),
               .o(p));
     end

 if ((latency == 0) && (core))
     begin:latency_eq_0
        assign core_p = full_s;
        assign p = conv_p;
     end

 if ((latency == 1) && (core))
     begin:latency_eq_1
        assign core_p = full_q;
        assign p = conv_p;
     end

endgenerate
endmodule
