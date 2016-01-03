
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
module  xlrelational  (a, b, dout, ce, clr, clk, en);
parameter a_width= 25;
parameter a_bin_pt= 19;
parameter a_arith= `xlSigned;
parameter b_width= 2;
parameter b_bin_pt= 0;
parameter b_arith= `xlSigned;
parameter dout_width= 1;
parameter dout_bin_pt= 0;
parameter dout_arith= `xlUnsigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter full_width= 12;
parameter full_arith= `xlUnsigned;
parameter full_bin_pt= 0;
parameter latency= 1;
parameter operator_type= 1;
input [a_width-1:0] a;
input [b_width-1:0] b;
output [dout_width-1:0] dout;
input ce;
input clr;
input clk;
input [en_width-1:0] en;
wire [full_width-1:0] full_a;
wire [full_width-1:0] full_b;
wire [dout_width-1:0] dout_unreg;
wire [dout_width-1:0] dout_reg;
wire  block_ce;
wire signed [full_width-1:0] signed_full_a;
wire signed [full_width-1:0] signed_full_b;
assign block_ce = ce & en[0];
assign dout = dout_reg;
 align_input # (a_width, full_bin_pt - a_bin_pt, a_arith, full_width)
    align_a(.inp(a),.res(full_a));

 align_input # (b_width, full_bin_pt - b_bin_pt, b_arith, full_width)
    align_b(.inp(b),.res(full_b));
generate
if(full_arith == `xlSigned)
begin:SIGNED_com
assign signed_full_a = full_a;
assign signed_full_b = full_b;
        if(operator_type == 0)
        begin:EQUALITY
                assign dout_unreg = (signed_full_a == signed_full_b);
        end
        if(operator_type == 1)
        begin:INEQUALITY
                assign dout_unreg = (signed_full_a != signed_full_b);
        end
        if(operator_type == 2)
        begin:LESSTHAN
                assign dout_unreg = (signed_full_a < signed_full_b);
        end
        if(operator_type == 3)
        begin:GREATERTHAN
                assign dout_unreg = (signed_full_a > signed_full_b);
        end
        if(operator_type == 4)
        begin:LESSTHAN_EQUAL
                assign dout_unreg = (signed_full_a <= signed_full_b);
        end
        if(operator_type == 5)
        begin:GREATERTHAN_EQUAL
                assign dout_unreg = (signed_full_a >= signed_full_b);
        end
end
if(full_arith == `xlUnsigned)
begin:UNSIGNED_com
        if(operator_type == 0)
        begin:UEQUALITY
                assign dout_unreg = (full_a == full_b);
        end
        if(operator_type == 1)
        begin:UINEQUALITY
                assign dout_unreg = (full_a != full_b);
        end
        if(operator_type == 2)
        begin:ULESSTHAN
                assign dout_unreg = (full_a < full_b);
        end
        if(operator_type == 3)
        begin:UGREATERTHAN
                assign dout_unreg = (full_a > full_b);
        end
        if(operator_type == 4)
        begin:ULESSTHAN_EQUAL
                assign dout_unreg = (full_a <= full_b);
        end
        if(operator_type == 5)
        begin:UGREATERTHAN_EQUAL
                assign dout_unreg = (full_a >= full_b);
        end
end
 if (latency > 0)
     begin:latency_gt_0
        synth_reg # (dout_width, latency)
          reg1 (
               .i(dout_unreg),
               .ce(block_ce),
               .clr(clr),
               .clk(clk),
               .o(dout_reg));
     end

   if (latency == 0)
     begin:latency_eq_0
        assign dout_reg = dout_unreg;
     end
 endgenerate
 endmodule
