
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
module  xltdd  (d,
                vin,
                src_clk,
                src_ce,
                src_clr,
                dest_clk,
                dest_ce,
                dest_clr,
                q,
                vout);
   parameter  frame_length= 4;
   parameter  srl_addr= 4;
   parameter  srl_addr1= 4;
   parameter  [15:0] fp_bv= 16'b0000000000000000;
   parameter  fp_string= "0000";
   parameter  [15:0] fp_bv1= 16'b0000000000000000;
   parameter  fp_string1= "0000";
   parameter  data_width= 4;
   parameter  hasValid = 0;
   parameter  normalized_clock_enable_period = 2;
   parameter  log_2_normalized_clock_enable_period = 1;
   input [data_width-1:0] d;
   input vin;
   input src_clk, src_ce, src_clr;
   input dest_clk, dest_ce, dest_clr;
   output [data_width-1:0] q;
   output vout;
   parameter mod_srl_addr1 = (srl_addr1 > 0) ? srl_addr1 : 1;
   parameter [srl_addr-1:0] mod_fp_bv = fp_bv;
   parameter [mod_srl_addr1-1:0] mod_fp_bv1 = fp_bv1;
   wire [srl_addr-1:0] pg_u1_in, pg_u1_out;
   wire [mod_srl_addr1-1:0] pg_u2_in, pg_u2_out;
   wire [data_width-1:0] smpl_dout;
   reg [3:0] fifo_addr;
   wire [3:0] dly_fifo_addr;
   wire dly_fifo_en,fifo_en,src_en;
   wire pg_in,pg_in1,pg_out,pg_out1;
   reg tmp_vout;
   wire tmp_vin;
   wire local_dest_ce;
   parameter [3:0] cnt_zero = 4'b0000;

   genvar i;
   initial
     begin
        fifo_addr = 4'b0000;
        tmp_vout = 1'b0;
     end

   assign tmp_vin = (hasValid == 0) ? 1'b1 : vin;
   assign src_en = src_ce & tmp_vin;
   assign pg_out = pg_u1_out[srl_addr-1];
   assign pg_out1 = pg_u2_out[mod_srl_addr1-1];
   assign pg_in = (frame_length > 16) ? pg_out1 : pg_out;
   assign pg_in1 = (frame_length > 16) ? pg_out : pg_out1;
   assign fifo_en = src_en & pg_out;
   assign #0.2 dly_fifo_en = fifo_en;
   assign #0.2 dly_fifo_addr = fifo_addr;
   xlclockenablegenerator # (
     .period(normalized_clock_enable_period),
     .log_2_period(log_2_normalized_clock_enable_period),
     .pipeline_regs(0)
   )
   local_dest_ce_generator(
     .clk(src_clk),
     .clr(1'b0),
     .ce(local_dest_ce)
   );

   always @(posedge src_clk)
     begin : addr_counter
        if (local_dest_ce == 1'b1)
          begin
             if (fifo_en == 1'b1)
               begin
                  fifo_addr = fifo_addr;
                  tmp_vout = 1'b1;
               end
             else
               begin
                  if (fifo_addr == cnt_zero)
                    begin
                       fifo_addr = fifo_addr;
                       tmp_vout = 1'b0;
                    end
                  else
                    begin
                       fifo_addr = fifo_addr-1'b1;
                       tmp_vout = 1'b1;
                    end
               end
          end
        else if (fifo_en == 1'b1)
          begin
             fifo_addr = fifo_addr + 1'b1;
          end
     end
generate
   for (i=0; i < data_width; i=i+1)
     begin:u1
        SRL16E u1 (.CLK(src_clk),
                   .D(d[i]),
                   .Q(smpl_dout[i]),
                   .CE(fifo_en),
                   .A0(dly_fifo_addr[0]),
                   .A1(dly_fifo_addr[1]),
                   .A2(dly_fifo_addr[2]),
                   .A3(dly_fifo_addr[3]));
     end
endgenerate
generate
   for (i=0; i < srl_addr; i=i+1)
     begin:pg_u1
        if (i == 0)
          begin:u1
             assign pg_u1_in[i] = pg_in;
          end
        else
          begin:u2
             assign pg_u1_in[i] = pg_u1_out[i-1];
          end
// synopsys translate_off
             defparam pg_u1.width = 1;
             defparam pg_u1.init_index = 2;
             defparam pg_u1.init_value = mod_fp_bv[i];
             defparam pg_u1.latency = 1;
// synopsys translate_on
        synth_reg_w_init # (1, 2, mod_fp_bv[i], 1)
          pg_u1 ( .i(pg_u1_in[i]),
                  .ce(src_en),
                  .clr(1'b0),
                  .clk(src_clk),
                  .o(pg_u1_out[i]));
     end
endgenerate
generate
for (i=0; i < srl_addr1; i=i+1)
  begin:pg_u2
     if (i == 0)
       begin:u1
          assign pg_u2_in[i] = pg_in1;
       end
     else
       begin:u2
          assign pg_u2_in[i] = pg_u2_out[i-1];
       end
// synopsys translate_off
             defparam pg_u2.width = 1;
             defparam pg_u2.init_index = 2;
             defparam pg_u2.init_value = mod_fp_bv1[i];
             defparam pg_u2.latency = 1;
// synopsys translate_on
     synth_reg_w_init # (1, 2, mod_fp_bv1[i], 1)
       pg_u2 ( .i(pg_u2_in[i]),
               .ce(src_en),
               .clr(1'b0),
               .clk(src_clk),
               .o(pg_u2_out[i]));
  end
endgenerate

   synth_reg_w_init # (data_width, 0, 1'b0, 1)
    output_reg ( .i(smpl_dout),
                 .ce(dest_ce),
                 .clr(1'b0),
                 .clk(dest_clk),
                 .o(q));
   synth_reg_w_init # (1, 0, 1'b0, 1)
    vout_reg ( .i(tmp_vout),
               .ce(dest_ce),
               .clr(1'b0),
               .clk(dest_clk),
               .o(vout));

endmodule
