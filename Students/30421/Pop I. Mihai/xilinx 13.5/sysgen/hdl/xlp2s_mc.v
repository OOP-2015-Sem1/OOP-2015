
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
module  xlp2s  (din, src_ce, src_clr, src_clk, dest_ce, dest_clr, dest_clk, rst, en, dout);
parameter dout_width= 8;
parameter dout_arith= `xlSigned;
parameter dout_bin_pt= 0;
parameter din_width= 1;
parameter din_arith= `xlUnsigned;
parameter din_bin_pt= 0;
parameter rst_width= 1;
parameter rst_bin_pt= 0;
parameter rst_arith= `xlUnsigned;
parameter en_width= 1;
parameter en_bin_pt= 0;
parameter en_arith= `xlUnsigned;
parameter lsb_first= 0;
parameter latency= 0;
parameter normalized_clock_enable_period = 2;
parameter log_2_normalized_clock_enable_period = 1;
parameter num_outputs= 0;
input [din_width-1:0] din;
input src_ce, src_clr, src_clk;
input dest_ce, dest_clr, dest_clk;
input rst;
input en;
output [dout_width-1:0] dout;
   wire [dout_width-1:0] i [0:num_outputs-1];
   wire [din_width-1:0] din_temp;
   wire [dout_width-1:0] o [0:num_outputs-1];
   wire [dout_width-1:0] dout_temp;
   wire src_ce_hold;
   wire internal_src_ce;
   wire internal_dest_ce;
   wire internal_mux_sel;
   wire internal_mux_sel_gen;
   wire internal_mux_sel_gen_shift;
   wire internal_clr;
   genvar index, idx, idx1;

   assign internal_mux_sel = internal_mux_sel_gen_shift & en;
   assign internal_src_ce = src_ce_hold & en;
   assign internal_dest_ce = dest_ce & en;
   assign internal_clr = (dest_clr | src_clr | rst) & dest_ce;
   assign dout_temp = internal_mux_sel ? din_temp[dout_width-1:0]: o[1];
   xlclockenablegenerator #(
     .period(normalized_clock_enable_period),
     .pipeline_regs(0),
     .log_2_period(log_2_normalized_clock_enable_period)
   )
   generate_input_selector(
     .clk(dest_clk),
     .clr(1'b0),
     .ce(internal_mux_sel_gen)
   );
   FDSE src_ce_reg(.Q(internal_mux_sel_gen_shift),
                   .D(internal_mux_sel_gen),
                   .C(dest_clk),
                   .CE(dest_ce),
                   .S(src_clr)
   );
   generate
   if (lsb_first==1)
     begin:lsb_is_first
           assign din_temp = din;
     end
   else
     begin:msb_is_first
           p2s_bit_reverse # (din_width, dout_width, num_outputs) reverse_input(.din(din), .dout(din_temp));
     end
  endgenerate

   generate
      for(index=0; index<num_outputs; index=index+1)
        begin:fd_array
           synth_reg_w_init # (dout_width, 0, 1'b0, 1)
             capture ( .i(i[index]),
                       .ce(internal_dest_ce),
                       .clr(internal_clr),
                       .clk(dest_clk),
                       .o(o[index]));
        end
   endgenerate
   generate
      for (idx=0; idx<num_outputs; idx=idx+1)
        begin:signal_select
           if (idx < num_outputs-1)
             begin:signal_0
                assign i[idx] = internal_mux_sel ? din_temp[idx*dout_width+dout_width-1:idx*dout_width] : o[idx+1];
             end
           if (idx == num_outputs-1)
             begin:signal_1
                assign i[idx] = internal_mux_sel ? din_temp[idx*dout_width+dout_width-1:idx*dout_width] : o[idx];
             end
        end
   endgenerate
   generate
      if (latency > 0)
        begin:latency_gt_0
           synth_reg # (dout_width, latency)
             data_reg (.i(dout_temp),
                       .ce(internal_dest_ce),
                       .clr(internal_clr),
                       .clk(dest_clk),
                       .o(dout));
        end
      if (latency == 0)
        begin:latency0
           assign dout = dout_temp;
        end
   endgenerate
endmodule
module p2s_bit_reverse (din, dout);
   parameter din_width = 8;
   parameter dout_width = 2;
   parameter num_outputs = 4;
   input [din_width-1:0] din;
   output [din_width-1:0] dout;
   genvar index;
   generate
      for (index=0; index<num_outputs; index=index+1)
        begin:u0
          assign dout[(num_outputs-index)*dout_width-1:(num_outputs-index-1)*dout_width] = din[index*dout_width+dout_width-1:index*dout_width];
       end
 endgenerate
endmodule
