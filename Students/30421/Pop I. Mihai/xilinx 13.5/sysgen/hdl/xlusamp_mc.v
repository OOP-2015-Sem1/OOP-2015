
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
module  xlusamp  (d, en, src_clk, src_ce, src_clr, dest_clk, dest_ce, dest_clr, q);
parameter d_width= 5;
parameter d_bin_pt= 2;
parameter d_arith= `xlUnsigned;
parameter q_width= 5;
parameter q_bin_pt= 2;
parameter q_arith= `xlUnsigned;
parameter en_width= 1;
parameter copy_samples= 0;
parameter normalized_clock_enable_period = 2;
parameter log_2_normalized_clock_enable_period = 1;
parameter latency = 0;
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
wire [d_width-1:0] delay_adjusted_d;
wire  mux_sel;
wire internal_ce;
wire internal_mux_sel_gen;
wire internal_mux_sel_gen_shift;
generate
    assign internal_ce = src_ce & en[0];
    if (latency ==  0) begin
        assign delay_adjusted_d = d;
    end
    else begin
        synth_reg #(d_width, latency)
        delay_d (
            .i(d),
            .ce(internal_ce),
            .clr(1'b0),
            .clk(src_clk),
            .o(delay_adjusted_d)
        );
    end
endgenerate
generate
if(copy_samples == 0)
 begin:copy_samples_0
    xlclockenablegenerator # (
        .period(normalized_clock_enable_period),
        .pipeline_regs(0),
        .log_2_period(log_2_normalized_clock_enable_period)
    )
    generate_input_selector(
      .clk(dest_clk),
      .clr(1'b0),
      .ce(internal_mux_sel_gen)
    );
        assign zero = {q_width{1'b0}};
        FDSE sel_gen(
      .Q(internal_mux_sel_gen_shift),
      .D(internal_mux_sel_gen),
      .C(dest_clk),
          .S(src_clr),
      .CE(dest_ce)
    );

    assign q = internal_mux_sel_gen_shift ? delay_adjusted_d :zero;
 end
if(copy_samples==1)
begin:copy_samples_1
        assign q = delay_adjusted_d;
end
endgenerate
endmodule
