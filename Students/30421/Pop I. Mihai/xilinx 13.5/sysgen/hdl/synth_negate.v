
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
module  synth_negate  (a, ce, clr, clk, p);
parameter c_a_width= 8;
parameter latency= 5;
input [c_a_width-1:0] a;
input ce;
input clr;
input clk;
output [c_a_width:0] p;
wire signed [c_a_width:0] signed_a;
wire signed [c_a_width:0] result;
assign result = -a;
 generate
 if (latency > 0)
     begin:latency_gt_0
        synth_reg # (c_a_width + 1, latency)
          reg1 (
               .i(result),
               .ce(ce),
               .clr(clr),
               .clk(clk),
               .o(p));
     end

   if ( latency == 0 )
     begin:latency_eq_0
        assign p = result;
     end
 endgenerate
 endmodule
