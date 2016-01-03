
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
module  xlusamp  (d, src_clk, src_ce, src_clr, dest_clk, dest_ce, dest_clr, en, q);
parameter d_width= 5;
parameter d_bin_pt= 2;
parameter d_arith= `xlUnsigned;
parameter q_width= 5;
parameter q_bin_pt= 2;
parameter q_arith= `xlUnsigned;
parameter en_width = 1;
parameter en_bin_pt = 0;
parameter en_arith = `xlUnsigned;
parameter sampling_ratio = 2;
parameter latency = 1;
parameter copy_samples= 0;


input [d_width-1:0] d;
input  src_clk;
input  src_ce;
input  src_clr;
input  dest_clk;
input  dest_ce;
input  dest_clr;
input [en_width-1:0] en;
output [q_width-1:0] q;
wire [q_width-1:0] zero;
wire  mux_sel;
wire internal_ce;
wire [d_width-1:0] sampled_d;
assign internal_ce = src_ce & en[0];
generate
        if (copy_samples == 0)
        begin:copy_samples_0
                FDSE sel_gen(.Q(mux_sel),
            .D(src_ce),
                .C(src_clk),
                .S(src_clr),
                .CE(dest_ce));
                assign zero = {q_width{1'b0}};
                if (latency == 0)
                begin
                        assign q = (mux_sel) ? d : zero;
                end
                else
                begin
                        synth_reg # (d_width, latency)
                        reg1 (
                                .i(d),
                                .ce(internal_ce),
                                .clr(src_clr),
                                .clk(src_clk),
                                .o(sampled_d)
                        );
                        assign q = (mux_sel) ? sampled_d : zero;
                end
        end
        if (copy_samples == 1)
        begin:copy_samples_1
                if (latency == 0)
                begin
                        assign q = d;
                end
                else
                begin
                        assign q = sampled_d;
                        synth_reg # (d_width, latency)
                        reg2 (
                                .i(d),
                                .ce(internal_ce),
                                .clr(src_clr),
                                .clk(src_clk),
                                .o(sampled_d)
                        );
                end
        end
endgenerate
endmodule
