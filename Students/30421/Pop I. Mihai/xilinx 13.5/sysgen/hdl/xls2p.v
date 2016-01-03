
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
module  xls2p  (din, src_ce, src_clr, src_clk, dest_ce, dest_clr, dest_clk, rst, en, dout);
parameter signed [31:0] dout_width = 8;
parameter signed [31:0] dout_arith = `xlSigned;
parameter signed [31:0] dout_bin_pt = 0;
parameter signed [31:0] din_width = 1;
parameter signed [31:0] din_arith = `xlUnsigned;
parameter signed [31:0] din_bin_pt = 0;
parameter signed [31:0] rst_width = 1;
parameter signed [31:0] rst_bin_pt = 0;
parameter signed [31:0] rst_arith = `xlUnsigned;
parameter signed [31:0] en_width = 1;
parameter signed [31:0] en_bin_pt = 0;
parameter signed [31:0] en_arith = `xlUnsigned;
parameter signed [31:0] lsb_first = 0;
parameter signed [31:0] latency = 0;
parameter signed [31:0] num_inputs = 0;
input [din_width-1:0] din;
input src_ce, src_clr, src_clk;
input dest_ce, dest_clr, dest_clk;
input rst, en;
output [dout_width-1:0] dout;
   wire [din_width-1:0] capture_in [0:num_inputs-1];
   wire [din_width-1:0] i [0:num_inputs-1];
   wire [din_width-1:0] o [0:num_inputs-1];
   wire del_dest_ce;
   wire [dout_width-1:0] dout_temp, dout_hold;
   wire internal_src_ce;
   wire internal_dest_ce;
   wire internal_clr;
   genvar index;

   assign internal_src_ce = src_ce & en;
   assign internal_dest_ce = dest_ce & en;
   assign internal_clr = ((dest_clr | src_clr ) & dest_ce) | rst;
   generate
      for(index=0; index<num_inputs; index=index+1)
        begin:fd_array
           synth_reg # (din_width,1)
             comp ( .i(i[index]),
                    .ce(internal_src_ce),
                    .clr(internal_clr),
                    .clk(src_clk),
                    .o(o[index]));
           synth_reg_w_init # (din_width, 0, 1'b0, 1)
                  capture ( .i(capture_in[index]),
                            .ce(internal_dest_ce),
                            .clr(internal_clr),
                            .clk(dest_clk),
                            .o(dout_hold[dout_width-1-index*din_width:dout_width-index*din_width-din_width]));
           if (index == 0)
             begin:signal_01
                assign i[index] = din;
                assign capture_in[index] = din;
             end
           else
             begin:signal_gt_0
                assign i[index] = o[index-1];
                assign capture_in[index] = o[index-1];
             end
        end
   endgenerate
   generate
      if (lsb_first==1)
        begin:lsb_is_first
           assign dout_temp = dout_hold;
       end
     else
       begin:msb_is_first
           s2p_bit_reverse # (din_width, dout_width, num_inputs) reverse_input(.din(dout_hold), .dout(dout_temp));
       end
   endgenerate
   generate
      if (latency > 1)
        begin:latency_gt_0
           synth_reg # (dout_width, latency-1)
             data_reg (.i(dout_temp),
                       .ce(internal_dest_ce),
                       .clr(internal_clr),
                       .clk(dest_clk),
                       .o(dout));
        end
      if (latency <= 1)
        begin:latency0
           assign dout = dout_temp;
        end
   endgenerate

endmodule
module s2p_bit_reverse (din, dout);
   parameter signed [31:0] din_width = 2;
   parameter signed [31:0] dout_width = 8;
   parameter signed [31:0] num_inputs = 4;
   input [dout_width-1:0] din;
   output [dout_width-1:0] dout;
   genvar index;
   generate
      for (index=0; index<num_inputs; index=index+1)
        begin:u0
          assign dout[(num_inputs-index)*din_width-1:(num_inputs-index-1)*din_width] = din[index*din_width+din_width-1:index*din_width];
       end
 endgenerate
endmodule
