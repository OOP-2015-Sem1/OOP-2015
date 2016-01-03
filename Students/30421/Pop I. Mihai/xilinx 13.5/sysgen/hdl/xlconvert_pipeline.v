
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
module convert_pipeline (inp, en, clk, ce, clr, res);
   parameter signed [31:0]  old_width = 4;
   parameter signed [31:0]  old_bin_pt = 2;
   parameter signed [31:0]  old_arith = `xlSigned;
   parameter signed [31:0]  new_width = 4;
   parameter signed [31:0]  new_bin_pt = 1;
   parameter signed [31:0]  new_arith = `xlSigned;
   parameter signed [31:0]  en_width = 1;
   parameter signed [31:0]  en_bin_pt = 0;
   parameter signed [31:0]  en_arith = `xlSigned;
   parameter signed [31:0]  quantization = `xlTruncate;
   parameter signed [31:0]  overflow = `xlWrap;
   parameter signed [31:0]  latency = 0;
   input [old_width - 1 : 0] inp;
   input [en_width - 1 : 0] en;
   input ce, clk, clr;
   output [new_width - 1 : 0] res;

   parameter signed [31:0]  fp_width = old_width + 2;
   parameter signed [31:0]  fp_bin_pt = old_bin_pt;
   parameter signed [31:0]  fp_arith = old_arith;
   parameter signed [31:0]  q_width = fp_width + new_bin_pt - old_bin_pt;
   parameter signed [31:0]  q_bin_pt = new_bin_pt;
   parameter signed [31:0]  q_arith = old_arith;
   wire [fp_width-1:0] full_precision_result_in, full_precision_result_out;
   wire [new_width-1:0] result;
   wire [q_width-1:0] quantized_result_in, quantized_result_out;
   wire internal_ce;
   assign internal_ce = ce & en[0];
   cast # (old_width, old_bin_pt, fp_width, fp_bin_pt, fp_arith)
     fp_cast (.inp(inp), .res(full_precision_result_in));
   generate
      if (latency > 2)
        begin:latency_fpr
           synth_reg # (fp_width, 1)
             reg_fpr (.i(full_precision_result_in),
                      .ce(internal_ce),
                      .clr(clr),
                      .clk(clk),
                      .o(full_precision_result_out));
        end
      else
        begin:no_latency_fpr
           assign full_precision_result_out = full_precision_result_in;
        end
   endgenerate

   generate
      if (quantization == `xlRound)
        begin:ct_u0
           round_towards_inf # (fp_width, fp_bin_pt, fp_arith, q_width, q_bin_pt, q_arith)
             quant_rtf (.inp(full_precision_result_out), .res(quantized_result_in));
        end
   endgenerate

   generate
      if (quantization == `xlRoundBanker)
        begin:ct_u1
           round_towards_even # (fp_width, fp_bin_pt, fp_arith, q_width, q_bin_pt, q_arith)
             quant_rte (.inp(full_precision_result_out), .res(quantized_result_in));
        end
   endgenerate

   generate
      if (quantization == `xlTruncate)
        begin:ct_u2
           trunc # (fp_width, fp_bin_pt, fp_arith, q_width, q_bin_pt, q_arith)
             quant_tr (.inp(full_precision_result_out), .res(quantized_result_in));
            end
   endgenerate
   generate
      if (latency > 1)
        begin:latency_qr
           synth_reg # (q_width, 1)
             reg_qr (.i(quantized_result_in),
                     .ce(internal_ce),
                     .clr(clr),
                     .clk(clk),
                     .o(quantized_result_out));
        end
      else
        begin:no_latency_qr
           assign quantized_result_out = quantized_result_in;
        end
   endgenerate

   generate
      if (overflow == `xlSaturate)
        begin:ct_u3
           saturation_arith # (q_width, q_bin_pt, q_arith, new_width, new_bin_pt, new_arith)
            ovflo_sat (.inp(quantized_result_out), .res(result));
        end
   endgenerate

   generate
      if (overflow == `xlWrap)
        begin:ct_u4
           wrap_arith # (q_width, q_bin_pt, q_arith, new_width, new_bin_pt, new_arith)
             ovflo_wrap (.inp(quantized_result_out), .res(result));
        end
   endgenerate
   generate
      if (latency > 3)
        begin:latency_qt_3
           synth_reg # (new_width, latency-2)
             reg_out (.i(result),
                      .ce(internal_ce),
                      .clr(clr),
                      .clk(clk),
                      .o(res));
        end
      else if ((latency <4) && (latency > 0))
        begin:no_latency_lt_t
           synth_reg # (new_width, 1)
             reg_out (.i(result),
                      .ce(internal_ce),
                      .clr(clr),
                      .clk(clk),
                      .o(res));
        end
      else
        begin:latency0
           assign res = result;
        end
   endgenerate

endmodule
module xlconvert_pipeline(din, en, clk, ce, clr, dout);
   parameter din_width= 16;
   parameter din_bin_pt= 4;
   parameter din_arith= `xlUnsigned;
   parameter dout_width= 8;
   parameter dout_bin_pt= 2;
   parameter dout_arith= `xlUnsigned;
   parameter en_width = 1;
   parameter en_bin_pt = 0;
   parameter en_arith = `xlUnsigned;
   parameter bool_conversion = 0;
   parameter latency = 0;
   parameter quantization= `xlTruncate;
   parameter overflow= `xlWrap;
   input [din_width-1:0] din;
   input [en_width-1:0] en;
   input clk, ce, clr;
   output [dout_width-1:0] dout;
   wire [dout_width-1:0] result;
   convert_pipeline #(din_width,
                      din_bin_pt,
                      din_arith,
                              dout_width,
                      dout_bin_pt,
                      dout_arith,
                              en_width,
                      en_bin_pt,
                      en_arith,
                      quantization,
                      overflow,
                      latency)
        conv_udp (.inp(din),
                  .en(en),
                  .clk(clk),
                  .ce(ce),
                  .clr(clr),
                  .res(dout));
endmodule
