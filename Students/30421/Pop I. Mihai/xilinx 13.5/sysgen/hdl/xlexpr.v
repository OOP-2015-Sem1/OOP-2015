
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
[% port_names = port_names.replace('\s*,\s*\]\s*$', '') -%]
[% port_names = port_names.replace('^\s*\[\s*', '') -%]
[% port_names = port_names.replace("'", '') -%]
[% port_names = port_names.split(',') -%]
module  [% entity_name %]  ([% FOREACH port_name = port_names -%]
                                [% port_name %],
                            [% END -%]
ce, clr, clk, en, dout);
[% FOREACH port_name = port_names -%]
        parameter [% port_name %]_width= 12;
        parameter [% port_name %]_bin_pt= 7;
        parameter [% port_name %]_arith= `xlSigned;
[% END -%]
parameter full_width= 12;
parameter full_bin_pt= 7;
parameter full_arith= `xlSigned;
parameter dout_width= 12;
parameter dout_bin_pt= 7;
parameter dout_arith= `xlSigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter latency= 1;
[% FOREACH port_name = port_names -%]
input [[% port_name %]_width-1:0] [% port_name %];
[% END -%]
input  ce;
input  clr;
input  clk;
input [en_width-1:0] en;
output [dout_width-1:0] dout;
wire [full_width-1:0] full_dout_unreg;
wire [full_width-1:0] full_dout_reg;
wire [dout_width-1:0] user_dout;
wire  internal_ce;
[% num_inputs_minus_1 = num_inputs - 1 -%]
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
wire [full_width-1:0] full_din[% i %];
[% END -%]
assign internal_ce = ce & en[0];
[% FOREACH name = port_names -%]
align_input # ([% name %]_width,
                full_bin_pt - [% name %]_bin_pt,
                [% name %]_arith,
                full_width)
align_[% name %](.inp([% name %]),.res(full_din[% loop.index %]));
[%END -%]
assign full_dout_unreg = [% vhdl_expr %];
 generate
 if (latency > 0)
     begin:latency_gt_0
        synth_reg # (full_width,1)
          reg1 (
               .i(full_dout_unreg),
               .ce(internal_ce),
               .clr(clr),
               .clk(clk),
               .o(full_dout_reg)
               );
     end

   if (latency == 0)
     begin:latency_eq_0
        assign full_dout_reg = full_dout_unreg;
     end
   if(latency>1)
      begin:latency_gt_1
        synth_reg # (dout_width,latency-1)
          reg2 (
               .i(user_dout),
               .ce(internal_ce),
               .clr(clr),
               .clk(clk),
               .o(dout)
               );
      end
   if ((latency == 0)||(latency == 1))
     begin:latency_also_eq_0
        assign dout = user_dout;
     end
 endgenerate
convert_type # (full_width, full_bin_pt, full_arith,
                             dout_width, dout_bin_pt, dout_arith, `xlTruncate,
                             `xlWrap)
convert_userdout(.inp(full_dout_reg),.res(user_dout));
 endmodule
