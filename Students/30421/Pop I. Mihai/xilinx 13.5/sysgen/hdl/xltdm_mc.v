
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
module  xltdm  ( src_clk,
                 src_ce,
                 src_clr,
                 dest_clk,
                 dest_ce,
                 dest_clr,
                 d0,
                 d1,
                 d2,
                 d3,
                 d4,
                 d5,
                 d6,
                 d7,
                 d8,
                 d9,
                 d10,
                 d11,
                 d12,
                 d13,
                 d14,
                 d15,
                 d16,
                 d17,
                 d18,
                 d19,
                 d20,
                 d21,
                 d22,
                 d23,
                 d24,
                 d25,
                 d26,
                 d27,
                 d28,
                 d29,
                 d30,
                 d31,
                 vin,
                 q,
                 vout);
parameter num_inputs= 2;
parameter data_width= 4;
parameter hasValid = 0;
parameter normalized_clock_enable_period = 2;
parameter log_2_normalized_clock_enable_period = 1;
input src_clk, src_ce, src_clr;
input dest_clk, dest_ce, dest_clr;
input [data_width-1:0] d0;
input [data_width-1:0] d1;
input [data_width-1:0] d2;
input [data_width-1:0] d3;
input [data_width-1:0] d4;
input [data_width-1:0] d5;
input [data_width-1:0] d6;
input [data_width-1:0] d7;
input [data_width-1:0] d8;
input [data_width-1:0] d9;
input [data_width-1:0] d10;
input [data_width-1:0] d11;
input [data_width-1:0] d12;
input [data_width-1:0] d13;
input [data_width-1:0] d14;
input [data_width-1:0] d15;
input [data_width-1:0] d16;
input [data_width-1:0] d17;
input [data_width-1:0] d18;
input [data_width-1:0] d19;
input [data_width-1:0] d20;
input [data_width-1:0] d21;
input [data_width-1:0] d22;
input [data_width-1:0] d23;
input [data_width-1:0] d24;
input [data_width-1:0] d25;
input [data_width-1:0] d26;
input [data_width-1:0] d27;
input [data_width-1:0] d28;
input [data_width-1:0] d29;
input [data_width-1:0] d30;
input [data_width-1:0] d31;
input vin;
output [data_width-1:0] q;
output  vout;
wire  [data_width-1:0] din_temp [0:31];
wire  local_src_ce, local_src_en, dest_en, tmp_vin;
reg [5:0]  indx_cntr;
initial
begin
  indx_cntr = 6'b000000;
end
assign tmp_vin = (hasValid == 0) ? 1'b1 : vin;
assign dest_en = dest_ce & tmp_vin;
assign local_src_en = local_src_ce & tmp_vin;
assign vout = vin;
assign q = din_temp[indx_cntr];
xlclockenablegenerator # (
  .period(normalized_clock_enable_period),
  .pipeline_regs(0),
  .log_2_period(log_2_normalized_clock_enable_period)
)
generate_local_src_ce (
  .clk(dest_clk),
  .clr(1'b0),
  .ce(local_src_ce)
);
always @(posedge dest_clk)
begin : index_counter
  if (local_src_en == 1'b1)
  begin:u1
    indx_cntr = 0;
  end
  else
  begin : u2
    if (dest_en == 1'b1)
    begin:u3
      indx_cntr = indx_cntr + 1'b1 ;
    end
  end
end
assign din_temp[0] = d0;
    assign din_temp[1] = d1;
    assign din_temp[2] = d2;
    assign din_temp[3] = d3;
    assign din_temp[4] = d4;
    assign din_temp[5] = d5;
    assign din_temp[6] = d6;
    assign din_temp[7] = d7;
    assign din_temp[8] = d8;
    assign din_temp[9] = d9;
    assign din_temp[10] = d10;
    assign din_temp[11] = d11;
    assign din_temp[12] = d12;
    assign din_temp[13] = d13;
    assign din_temp[14] = d14;
    assign din_temp[15] = d15;
    assign din_temp[16] = d16;
    assign din_temp[17] = d17;
    assign din_temp[18] = d18;
    assign din_temp[19] = d19;
    assign din_temp[20] = d20;
    assign din_temp[21] = d21;
    assign din_temp[22] = d22;
    assign din_temp[23] = d23;
    assign din_temp[24] = d24;
    assign din_temp[25] = d25;
    assign din_temp[26] = d26;
    assign din_temp[27] = d27;
    assign din_temp[28] = d28;
    assign din_temp[29] = d29;
    assign din_temp[30] = d30;
    assign din_temp[31] = d31;
endmodule
