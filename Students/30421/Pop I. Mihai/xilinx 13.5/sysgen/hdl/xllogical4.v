
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
module  xllogical4  (din0, din1, din2, din3, dout, en, ce, clr, clk);
parameter din0_width= 12;
parameter din0_bin_pt= 7;
parameter din0_arith= `xlSigned;
parameter din1_width= 12;
parameter din1_bin_pt= 7;
parameter din1_arith= `xlSigned;
parameter din2_width= 12;
parameter din2_bin_pt= 7;
parameter din2_arith= `xlSigned;
parameter din3_width= 12;
parameter din3_bin_pt= 7;
parameter din3_arith= `xlSigned;
parameter dout_width= 12;
parameter dout_bin_pt= 7;
parameter dout_arith= `xlSigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter full_width= 12;
parameter full_arith= `xlUnsigned;
parameter full_bin_pt= 0;
parameter num_inputs= 2;
parameter latency= 1;
parameter gate_type= 0;
input [din0_width-1:0] din0;
input [din1_width-1:0] din1;
input [din2_width-1:0] din2;
input [din3_width-1:0] din3;
output [dout_width-1:0] dout;
input [en_width-1:0] en;
input  ce;
input  clr;
input  clk;
wire [full_width-1:0] full_din0;
wire [full_width-1:0] full_din1;
wire [full_width-1:0] full_din2;
wire [full_width-1:0] full_din3;
wire [full_width-1:0] full_dout_unreg;
wire [full_width-1:0] full_dout_reg;
wire [dout_width-1:0] user_dout;
wire  block_ce;
assign block_ce = ce & en[0];
assign dout = user_dout;
align_input # (din0_width, full_bin_pt - din0_bin_pt,
                            din0_arith, full_width)
align_inp0(.inp(din0),.res(full_din0));
align_input # (din1_width, full_bin_pt - din1_bin_pt,
                             din1_arith, full_width)
align_inp1(.inp(din1),.res(full_din1));
align_input # (din2_width, full_bin_pt - din2_bin_pt,
                             din2_arith, full_width)
align_inp2(.inp(din2),.res(full_din2));
align_input # (din3_width, full_bin_pt - din3_bin_pt,
                             din3_arith, full_width)
align_inp3(.inp(din3),.res(full_din3));
generate
if (gate_type == 0)
    begin:AND_gate
      assign full_dout_unreg = full_din0 & full_din1 & full_din2 & full_din3;
   end

if (gate_type == 1)
    begin:NAND_gate
      assign full_dout_unreg = ~(full_din0 & full_din1 & full_din2 & full_din3);
   end
if (gate_type == 2)
   begin:OR_gate
      assign full_dout_unreg = full_din0 | full_din1 | full_din2 | full_din3;
   end
if (gate_type == 3)
   begin:NOR_gate
      assign full_dout_unreg = ~(full_din0 | full_din1 | full_din2 | full_din3);
   end
if (gate_type == 4)
   begin:XOR_gate
      assign full_dout_unreg = (full_din0 ^ full_din1 ^ full_din2 ^ full_din3);
   end
if (gate_type == 5)
   begin:XNOR_gate
      assign full_dout_unreg = ~(full_din0 ^ full_din1 ^ full_din2 ^ full_din3);
   end
 if (latency > 0)
     begin:latency_gt_0
        synth_reg # (full_width, latency)
          reg1 (
               .i(full_dout_unreg),
               .ce(block_ce),
               .clr(clr),
               .clk(clk),
               .o(full_dout_reg)
                );
     end

   if (latency == 0)
     begin:latency_eq_0
        assign full_dout_reg = full_dout_unreg;
     end
 endgenerate
convert_type # (full_width, full_bin_pt, full_arith,
                             dout_width, dout_bin_pt, dout_arith, `xlTruncate,
                             `xlWrap)
conv_udp(.inp(full_dout_reg),.res(user_dout));
 endmodule
