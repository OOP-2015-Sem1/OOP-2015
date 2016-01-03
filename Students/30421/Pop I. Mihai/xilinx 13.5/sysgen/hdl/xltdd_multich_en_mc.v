
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
module  cas_srls  (d,
                   cnt_index,
                   clk,
                   src_ce,
                   dest_ce,
                   q);
   parameter    index= 0;
   parameter    data_width= 4;
   input [data_width-1:0] d;
   input [4:0] cnt_index;
   input clk, src_ce, dest_ce;
   output [data_width-1:0] q;
   wire [3:0] fifo_addr;
   reg  [3:0] fifo_addr_new;
   wire fifo_en;
   reg cnt_en;
   genvar i;

   parameter [4:0] const_index = index;
   initial
     begin
        fifo_addr_new = 4'b0000;
        cnt_en = 1'b0;
     end

   assign fifo_en = cnt_en & src_ce;

   always @(cnt_index)
     begin:const_comp
        if (cnt_index == const_index)
          cnt_en = 1'b1;
        else
          cnt_en = 1'b0;
     end
   always @(dest_ce or fifo_en or fifo_addr)
     begin:addr_counter
        if (dest_ce == 1'b1)
          begin
             if (fifo_en == 1'b1)
               fifo_addr_new = fifo_addr;
             else
               fifo_addr_new = fifo_addr - 1'b1;
          end
        else
          begin
             if (fifo_en == 1'b1)
               fifo_addr_new = fifo_addr + 1'b1;
             else
               fifo_addr_new = fifo_addr;
          end
     end
   synth_reg_w_init # (4, 2, 4'b1111, 1)
    addr_reg ( .i(fifo_addr_new),
               .ce(1'b1),
               .clr(1'b0),
               .clk(clk),
               .o(fifo_addr));
generate
   for (i=0; i < data_width; i=i+1)
     begin:u1
        SRL16E u1 (.CLK(clk),
                   .D(d[i]),
                   .Q(q[i]),
                   .CE(fifo_en),
                   .A0(fifo_addr[0]),
                   .A1(fifo_addr[1]),
                   .A2(fifo_addr[2]),
                   .A3(fifo_addr[3]));
     end
endgenerate
endmodule
module xltdd_multich_en (d,
                        vin,
                        core_clk,
                        core_ce,
                        clr,
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
parameter  normalized_clock_enable_period_src = 2;
parameter  log_2_normalized_clock_enable_period_src = 1;
parameter  normalized_clock_enable_period_dest = 2;
parameter  log_2_normalized_clock_enable_period_dest = 1;
input [data_width-1:0] d;
input vin, core_clk, core_ce, clr;
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
   reg  [4:0] cnt_index;
   wire latch_en, src_en, tmp_vin, dest_en, latch_vout, latch_vout_in;
   reg  tmp_vout;
   wire  cnt_by_one;
   wire  local_dest_ce;
   wire  local_src_ce;
   genvar index;
   parameter [4:0] in_samples = frame_length-1;
   parameter [4:0] in_samples_1 = frame_length-2;
   assign tmp_vin = (hasValid == 0) ? 1'b1 : vin;
   assign src_en = local_src_ce & tmp_vin;
   assign dest_en = local_dest_ce & latch_vout;
   assign latch_vout_in = src_en & tmp_vout;
   assign latch_en = (src_en & tmp_vout) | local_dest_ce;
   initial
   begin
         cnt_index = 5'b00000;
         tmp_vout = 1'b0;
   end
   xlclockenablegenerator # (
     .period(normalized_clock_enable_period_src),
     .pipeline_regs(0),
     .log_2_period(log_2_normalized_clock_enable_period_src)
   )
   local_src_clock_enable_generator(
     .clk(core_clk),
     .clr(1'b0),
     .ce(local_src_ce)
   );

   xlclockenablegenerator # (
     .period(normalized_clock_enable_period_dest),
     .pipeline_regs(0),
     .log_2_period(log_2_normalized_clock_enable_period_dest)
   )
   local_dest_clock_enable_generator(
     .clk(core_clk),
     .clr(1'b0),
     .ce(local_dest_ce)
    );
   always @(posedge core_clk)
     begin : index_counter
         if (src_en == 1'b1)
         begin
           if (cnt_index == in_samples_1)
           begin
             tmp_vout = 1'b1;
                 cnt_index = cnt_index + 1'b1;
           end
           else
           begin
                 tmp_vout = 1'b0;
                 if (cnt_index == in_samples)
                   cnt_index = 5'b00000;
                 else
                   cnt_index = cnt_index + 1'b1;
             end
           end
     end
   synth_reg # (1, 1)
     latch_vout_reg (.i(latch_vout_in),
                     .ce(latch_en),
                     .clr(clr),
                     .clk(core_clk),
                     .o(latch_vout));
   synth_reg # (1, 1)
     vout_reg (.i(dest_en),
               .ce(local_dest_ce),
               .clr(clr),
               .clk(core_clk),
               .o(vout));
generate
   for (index=0; index < frame_length; index = index+1)
     begin:fd_array
        cas_srls # (index, data_width)
          comp (.d(d),
                .cnt_index(cnt_index),
                .clk(core_clk),
                .src_ce(src_en),
                .dest_ce(dest_en),
                .q(o[index]));

        synth_reg # (data_width, 1)
          capture (.i(o[index]),
                   .ce(dest_en),
                   .clr(clr),
                   .clk(core_clk),
                   .o(dout_temp[index]));
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
