
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
module xlconvencoder (din, dout0, dout1, dout2, dout3, dout4, dout5, dout6, vout, vin, clk, ce, clr, en, rst);
parameter core_name0 = "";
parameter din_arith = `xlUnsigned;
parameter din_bin_pt = 0;
parameter din_width= 1;
parameter dout0_arith= `xlUnsigned;
parameter dout0_bin_pt= 0;
parameter dout0_width= 1;
parameter dout1_arith= `xlUnsigned;
parameter dout1_bin_pt= 0;
parameter dout1_width= 1;
parameter dout2_arith= `xlUnsigned;
parameter dout2_bin_pt= 0;
parameter dout2_width= 1;
parameter dout3_arith= `xlUnsigned;
parameter dout3_bin_pt= 0;
parameter dout3_width= 1;
parameter dout4_arith= `xlUnsigned;
parameter dout4_bin_pt= 0;
parameter dout4_width= 1;
parameter dout5_arith= `xlUnsigned;
parameter dout5_bin_pt= 0;
parameter dout5_width= 1;
parameter dout6_arith= `xlUnsigned;
parameter dout6_bin_pt= 0;
parameter dout6_width= 1;
parameter en_arith= `xlUnsigned;
parameter en_bin_pt= 0;
parameter en_width= 1;
parameter rst_arith= `xlUnsigned;
parameter rst_bin_pt= 0;
parameter rst_width= 1;
parameter vin_arith= `xlUnsigned;
parameter vin_bin_pt= 0;
parameter vin_width= 1;
parameter vout_arith= `xlUnsigned;
parameter vout_bin_pt= 0;
parameter vout_width= 1;
parameter c_constraint_length= 3;
parameter c_output_rate= 2;
parameter c_convolution_code6= 5;
parameter c_convolution_code5= 5;
parameter c_convolution_code4= 5;
parameter c_convolution_code3= 5;
parameter c_convolution_code2= 0;
parameter c_convolution_code1= 5;
parameter c_convolution_code0= 7;
parameter c_enable_rlocs = 1;
input din, vin, clk, ce, clr, en, rst;
output dout0, dout1, dout2, dout3, dout4, dout5, dout6, vout;
wire  internal_clr;
wire  internal_ce;
wire  rdy_temp;
wire [6:0] dout;
// synopsys translate_off
   initial
     begin
        $display("  ");
        $display("** Error encountered during behavioral simulation. ");
        $display("** Behavioral simulation model is not available for the XILINX Convolution Encoder v4.0");
        $display("** Use Post-Translate, Post-Map or Post-Place & Route Verilog model for testbench verification.");
        $display("  ");
        $finish;
     end
// synopsys translate_on
assign internal_ce = (ce & en) | internal_clr;
assign internal_clr = (clr | rst) & ce;
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                       -%]
generate
  if (core_name0 == "[% name %]")
    begin:comp[% i %]
    [% name %] core_instance[% i %] (
[% inst_txt %]
      );
 end
endgenerate
[% END -%]
assign dout0 = dout[0];
assign dout1 = dout[1];
assign dout2 = dout[2];
assign dout3 = dout[3];
assign dout4 = dout[4];
assign dout5 = dout[5];
assign dout6 = dout[6];
endmodule
