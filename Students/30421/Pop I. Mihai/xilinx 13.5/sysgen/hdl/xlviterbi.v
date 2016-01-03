
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
module xlviterbi (din0, din1,din2, din3, din4,din5,din6, dout, vout, vin, rst, en,erase0,       erase1, erase2, erase3, erase4, erase5, erase6, sel, clk, ce,rfd,ber,ber_done,norm,clr);
parameter core_name0= "";
parameter c_red_latency= 0;
parameter c_serial= 0;
parameter c_radix4= 0;
parameter c_channel_count= 1;
parameter c_dual_decoder= 0;
parameter c_trellis_mode= 0;
parameter c_has_sync= 0;
parameter c_has_sync_thresh= 0;
parameter c_punc_input_rate= 2;
parameter c_has_block_valid= 0;
parameter c_constraint_length= 3;
parameter c_traceback_length= 18;
parameter c_has_best_state= 0;
parameter c_best_state_bwr= 2;
parameter c_max_rate= 2;
parameter c_output_rate0= 2;
parameter c_convolution0_code0= 7;
parameter c_convolution0_code1= 5;
parameter c_convolution0_code2= 0;
parameter c_convolution0_code3= 0;
parameter c_convolution0_code4= 0;
parameter c_convolution0_code5= 0;
parameter c_convolution0_code6= 0;
parameter c_output_rate1= 0;
parameter c_convolution1_code0= 0;
parameter c_convolution1_code1= 0;
parameter c_convolution1_code2= 0;
parameter c_convolution1_code3= 0;
parameter c_convolution1_code4= 0;
parameter c_convolution1_code5= 0;
parameter c_convolution1_code6= 0;
parameter c_soft_coding= 1;
parameter c_soft_code= 0;
parameter c_soft_width= 3;
parameter c_has_erased= 0;
parameter c_has_sclr= 0;
parameter c_has_rdy= 0;
parameter c_punctured= 0;
parameter c_has_norm= 0;
parameter c_has_ber= 0;
parameter c_compare= 0;
parameter snr= 20;
parameter bit_limit= 100;
parameter c_ber_rate= 20;
parameter c_family= 0;
input [c_soft_width-1:0] din0;
input [c_soft_width-1:0] din1;
input [c_soft_width-1:0] din2;
input [c_soft_width-1:0] din3;
input [c_soft_width-1:0] din4;
input [c_soft_width-1:0] din5;
input [c_soft_width-1:0] din6;
output dout, vout, ber_done, norm, rfd;
input vin,rst,en,erase0,erase1,erase2,erase3,erase4,erase5,erase6,sel, clk, ce, clr;
output [15:0] ber;
wire  internal_clr;
wire  internal_ce;
wire  rdy_temp;
wire [6:0] erase_temp;
// synopsys translate_off
   initial
     begin
        $display("  ");
        $display("** Error encountered during behavioral simulation. ");
        $display("** Behavioral simulation model is not available for the XILINX Viterbi Decoder v4.0");
        $display("** Use Post-Translate, Post-Map or Post-Place & Route Verilog model for testbench verification.");
        $display("  ");
        $finish;
     end
// synopsys translate_on

assign internal_clr = (clr | rst) & ce;
assign internal_ce = (ce & en) | internal_clr;
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                      -%]
generate
  if (core_name0 == "[% name %]")
    begin:comp[% i %]
    [% name %] core_instance[% i %] (
[% inst_txt %]
      );
    end
endgenerate
[% END -%]
assign erase_temp[0] = erase0;
assign erase_temp[1] = erase1;
assign erase_temp[2] = erase2;
assign erase_temp[3] = erase3;
assign erase_temp[4] = erase4;
assign erase_temp[5] = erase5;
assign erase_temp[6] = erase6;
endmodule
