
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
module xlcmult (a, ce, clr, clk, core_ce, core_clr, core_clk, rst, en, p);
parameter core_name0= "";
parameter a_width= 4;
parameter a_bin_pt= 2;
parameter a_arith= `xlSigned;
parameter b_width= 4;
parameter b_bin_pt= 2;
parameter b_arith= `xlSigned;
parameter p_width= 8;
parameter p_bin_pt= 2;
parameter p_arith= `xlSigned;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter rst_arith= `xlUnsigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter multsign= `xlSigned;
parameter quantization= `xlTruncate;
parameter overflow= `xlWrap;
parameter extra_registers= 0;
parameter c_a_width= 7;
parameter c_b_width= 7;
parameter c_a_type= 0;
parameter c_b_type= 0;
parameter c_type= 0;
parameter const_bin_pt= 1;
parameter c_output_width= 16;
parameter zero_const = 0;

input [a_width-1:0] a;
input  ce;
input  clr;
input  clk;
input  core_ce;
input  core_clr;
input  core_clk;
input [rst_width-1:0] rst;
input [en_width-1:0] en;
output [p_width-1:0] p;
wire [c_a_width-1:0] #0.1 tmp_a;
wire [c_output_width-1:0] tmp_p;
wire [p_width-1:0] conv_p;
wire  real_a,real_p;
wire  nd;
wire  internal_ce;
wire  internal_clr;
wire  internal_core_ce;
assign internal_ce = ce & en[0];
assign internal_core_ce = core_ce & en[0];
assign internal_clr = (clr | rst[0]) & ce;
assign nd = internal_ce;
zero_ext # (a_width,c_a_width) zero_ext_a(.inp(a),.res(tmp_a));
convert_type # (c_output_width, a_bin_pt+b_bin_pt, multsign,
                           p_width, p_bin_pt, p_arith, quantization, overflow)
convert_p(.inp(tmp_p),.res(conv_p));
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
 if ((extra_registers > 0) && (zero_const == 0))
     begin:latency_gt_0
        synth_reg # (p_width, extra_registers)
          reg1 (
               .i(conv_p),
               .ce(internal_ce),
               .clr(internal_clr),
               .clk(clk),
               .o(p)
               );
     end

   if ((extra_registers == 0) && (zero_const == 0))
     begin:latency_eq_0
        assign p = conv_p;
     end
   if (zero_const == 1)
     begin:zero_constant
        assign p = {p_width{1'b0}};
     end
endgenerate
endmodule
