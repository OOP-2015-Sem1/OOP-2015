
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
module xlsincos (cos, sin, theta, rst, en, ce, clr, clk);
parameter core_name0= "";
parameter theta_width= 3;
parameter theta_bin_pt= 0;
parameter theta_arith= `xlSigned;
parameter sin_width= 4;
parameter sin_bin_pt= 0;
parameter sin_arith= `xlSigned;
parameter cos_width= 4;
parameter cos_bin_pt= 0;
parameter cos_arith= `xlSigned;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter en_width= 5;
parameter en_bin_pt= 2;
parameter en_arith= `xlUnsigned;
parameter rst_arith= `xlUnsigned;
parameter c_pipe_stages= 0;
parameter c_latency= 0;
parameter c_negative_sine= 0;
parameter c_negative_cosine= 0;
parameter c_width= 3;
parameter c_memtype= 0;
parameter c_mode= 1;
parameter c_output_width= 1;
parameter extra_registers= 0;
parameter c_has_ce= 1;
parameter c_has_clk= 1;
parameter c_reg_output= 1;
parameter c_enable_rlocs= 1;
parameter c_symmetric= 0;
parameter c_has_sclr= 1;
output [cos_width-1:0] cos;
output [sin_width-1:0] sin;
input [theta_width-1:0] theta;
input [rst_width-1:0] rst;
input en, ce, clr, clk;
wire [sin_width-1:0] core_sin;
wire [cos_width-1:0] core_cos;
wire [sin_width-1:0] tmp_sin;
wire [cos_width-1:0] tmp_cos;
wire  rfd,rdy;
wire  internal_clr;
wire  internal_ce;
assign internal_ce = ce & en;
assign internal_clr = (clr | rst) & en;
 convert_type # (sin_width, sin_bin_pt, sin_arith,
                   sin_width, sin_bin_pt, sin_arith, `xlTruncate, `xlSaturate)
     conv_udp (.inp(tmp_sin), .res(sin));
 convert_type # (cos_width, cos_bin_pt, cos_arith,
                   cos_width, cos_bin_pt, cos_arith, `xlTruncate, `xlSaturate)
     conv_udp_cos (.inp(tmp_cos), .res(cos));
generate
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                      -%]
if (core_name0 == "[% name %]")
begin:comp[% i %]
[% name %] core_instance[% i %] (
[% inst_txt %]
);
end
[% END -%]
 if (extra_registers > 0 && c_mode != 1)
     begin:latency_gt_0_sin
        synth_reg # (sin_width, extra_registers)
          reg1 (
               .i(core_sin),
               .ce(internal_ce),
               .clr(internal_clr),
               .clk(clk),
               .o(tmp_sin));
     end

 if (extra_registers > 0 && c_mode != 0)
     begin:latency_gt_0_cos
        synth_reg # (cos_width, extra_registers)
          reg1 (
               .i(core_cos),
               .ce(internal_ce),
               .clr(internal_clr),
               .clk(clk),
               .o(tmp_cos));
     end
 if ( extra_registers == 0)
     begin:latency_eq_0
        assign tmp_sin = core_sin;
        assign tmp_cos = core_cos;
     end
 endgenerate
 endmodule
