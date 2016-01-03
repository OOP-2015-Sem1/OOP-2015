
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
module  xlsgn (d, ce, clr, clk, en, rst, q);
parameter d_width = 4;
parameter d_arith = `xlSigned;
parameter d_bin_pt = 0;
parameter q_width = 2;
parameter q_arith = `xlUnsigned;
parameter q_bin_pt = 0;
parameter rst_width = 1;
parameter rst_arith = `xlUnsigned;
parameter rst_bin_pt = 0;
parameter en_width = 1;
parameter en_arith = `xlUnsigned;
parameter en_bin_pt = 0;
parameter latency = 0;
input [d_width-1:0] d;
input  ce;
input  clr;
input  clk;
input  rst;
input  en;
output [q_width-1:0] q;
wire [q_width-1:0] one,neg_one;
reg [q_width-1:0] result;
wire mux_sel;
wire d_is_XorZ;
wire internal_clr,internal_ce;
assign internal_clr = (clr | rst) & ce;
assign internal_ce = ce & en;
generate
assign d_is_XorZ = |d;
if(d_arith == `xlUnsigned)
        begin:UNSIGNED_INPUT
                always@(d)
                 if((d_is_XorZ == 1'bz)||(d_is_XorZ == 1'bx))
                         result = 2'bxx;
                 else
                 result = 2'b01;
        end
else
   begin:SIGNED_INPUT
        assign mux_sel = d[d_width-1];
        always@(d)
         if((d_is_XorZ == 1'bz)||(d_is_XorZ == 1'bx))
                result = 2'bxx;
         else
                result = mux_sel ? 2'b11:2'b01;
   end
endgenerate
generate
  if (latency > 0)
        begin:latency_gt_0
        synth_reg # (q_width, latency)
            reg1(.i(result),
                  .ce(internal_ce),
                  .clr(internal_clr),
                  .clk(clk),
                  .o(q));
          end
  if (latency == 0)
        begin:latency_eq_0
                assign   q = result;
        end
endgenerate
endmodule
