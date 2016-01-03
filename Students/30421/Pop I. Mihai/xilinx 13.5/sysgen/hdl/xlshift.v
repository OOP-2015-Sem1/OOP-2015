
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
module  xlshift  (din, ce, clr, clk, rst, en, dout);
parameter din_width= 4;
parameter din_bin_pt= 2;
parameter din_arith= `xlSigned;
parameter dout_width= 2;
parameter dout_bin_pt= 0;
parameter dout_arith= `xlSigned;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter en_width= 5;
parameter en_bin_pt= 2;
parameter en_arith= `xlUnsigned;
parameter rst_arith= `xlUnsigned;
parameter direction= 1;
parameter num_bits= 2;
parameter latency= 0;
parameter quantization= `xlTruncate;
parameter overflow= `xlWrap;
parameter shifted_din_width = din_width + num_bits;
parameter shifted_bin_pt = din_bin_pt + (1 - direction)*num_bits;
input [din_width-1:0] din;
input  ce;
input  clr;
input  clk;
input [rst_width-1:0] rst;
input [en_width-1:0] en;
output [dout_width-1:0] dout;
wire [dout_width-1:0] result;
wire  internal_clr;
wire  internal_ce;
wire [din_width + num_bits - 1:0] shifted_din;
assign internal_clr = (clr | rst[0]) & ce;
assign internal_ce = ce & en[0];
generate
 if(direction == 0)
   begin:shift_right
       extend_msb # (din_width,shifted_din_width, din_arith)
         extend_din(.inp(din),.res(shifted_din));
   end

 if( direction != 0 )
   begin:shift_left
           pad_lsb # (din_width,shifted_din_width)
            pad_din(.inp(din),.res(shifted_din));
   end
endgenerate
convert_type # (shifted_din_width,shifted_bin_pt, din_arith,
                               dout_width, dout_bin_pt, dout_arith,
                               quantization, overflow)
  convert_din(.inp(shifted_din),.res(result));

generate
 if (latency > 0)
     begin:latency_gt_0
        synth_reg # (dout_width, latency)
          reg1 (
               .i(result),
               .ce(internal_ce),
               .clr(internal_clr),
               .clk(clk),
               .o(dout));
     end

   if (latency == 0)
     begin:latency_eq_0
        assign dout = result;
     end
endgenerate
 endmodule
