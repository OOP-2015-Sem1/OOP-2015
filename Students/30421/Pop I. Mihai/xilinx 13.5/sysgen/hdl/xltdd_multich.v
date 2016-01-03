
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
module  xltdd_multich  (d,
                        vin,
                        src_clk,
                        src_ce,
                        src_clr,
                        dest_clk,
                        dest_ce,
                        dest_clr,
                        q0,
                        q1,
                        q2,
                        q3,
                        q4,
                        q5,
                        q6,
                        q7,
                        q8,
                        q9,
                        q10,
                        q11,
                        q12,
                        q13,
                        q14,
                        q15,
                        q16,
                        q17,
                        q18,
                        q19,
                        q20,
                        q21,
                        q22,
                        q23,
                        q24,
                        q25,
                        q26,
                        q27,
                        q28,
                        q29,
                        q30,
                        q31,
                        vout);
parameter  frame_length= 4;
parameter  data_width= 4;
parameter  hasValid = 0;
input [data_width-1:0] d;
input vin, src_clk, src_ce, src_clr, dest_clk, dest_ce, dest_clr;
output [data_width-1:0] q0;
output [data_width-1:0] q1;
output [data_width-1:0] q2;
output [data_width-1:0] q3;
output [data_width-1:0] q4;
output [data_width-1:0] q5;
output [data_width-1:0] q6;
output [data_width-1:0] q7;
output [data_width-1:0] q8;
output [data_width-1:0] q9;
output [data_width-1:0] q10;
output [data_width-1:0] q11;
output [data_width-1:0] q12;
output [data_width-1:0] q13;
output [data_width-1:0] q14;
output [data_width-1:0] q15;
output [data_width-1:0] q16;
output [data_width-1:0] q17;
output [data_width-1:0] q18;
output [data_width-1:0] q19;
output [data_width-1:0] q20;
output [data_width-1:0] q21;
output [data_width-1:0] q22;
output [data_width-1:0] q23;
output [data_width-1:0] q24;
output [data_width-1:0] q25;
output [data_width-1:0] q26;
output [data_width-1:0] q27;
output [data_width-1:0] q28;
output [data_width-1:0] q29;
output [data_width-1:0] q30;
output [data_width-1:0] q31;
output vout;
   wire [data_width-1:0] i [0:31];
   wire [data_width-1:0] o [0:31];
   wire [data_width-1:0] dout_temp [0:31];
   wire [data_width-1:0] capture_in [0:31];
   wire [data_width-1:0] smpl_dout;
   wire [3:0] fifo_addr;
   wire dly_fifo_en, fifo_en, pg_out, src_en, tmp_vin;
   wire  tmp_vout;
   wire  cnt_by_one;
   genvar index;
   assign tmp_vin = (hasValid == 0) ? 1'b1 : vin;
   assign src_en = src_ce & tmp_vin;
generate
   for (index=0; index < frame_length; index = index+1)
     begin:fd_array
        synth_reg # (data_width, 1)
             comp (.i(i[index]),
                   .ce(src_en),
                   .clr(src_clr),
                   .clk(src_clk),
                   .o(o[index]));
        synth_reg # (data_width, 1)
             capture (.i(capture_in[index]),
                      .ce(dest_ce),
                      .clr(dest_clr),
                      .clk(dest_clk),
                      .o(dout_temp[frame_length-1-index]));
        if (index == 0)
          begin:signal_0
             assign i[index] = d;
             assign capture_in[index] = d;
          end
        else
          begin:signal_gt_0
             assign i[index] = o[index-1];
             assign capture_in[index] = o[index-1];
          end
     end
endgenerate

    assign q0 = dout_temp[0];
    assign q1 = dout_temp[1];
    assign q2 = dout_temp[2];
    assign q3 = dout_temp[3];
    assign q4 = dout_temp[4];
    assign q5 = dout_temp[5];
    assign q6 = dout_temp[6];
    assign q7 = dout_temp[7];
    assign q8 = dout_temp[8];
    assign q9 = dout_temp[9];
    assign q10 = dout_temp[10];
    assign q11 = dout_temp[11];
    assign q12 = dout_temp[12];
    assign q13 = dout_temp[13];
    assign q14 = dout_temp[14];
    assign q15 = dout_temp[15];
    assign q16 = dout_temp[16];
    assign q17 = dout_temp[17];
    assign q18 = dout_temp[18];
    assign q19 = dout_temp[19];
    assign q20 = dout_temp[20];
    assign q21 = dout_temp[21];
    assign q22 = dout_temp[22];
    assign q23 = dout_temp[23];
    assign q24 = dout_temp[24];
    assign q25 = dout_temp[25];
    assign q26 = dout_temp[26];
    assign q27 = dout_temp[27];
    assign q28 = dout_temp[28];
    assign q29 = dout_temp[29];
    assign q30 = dout_temp[30];
    assign q31 = dout_temp[31];
endmodule
