
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
`timescale 1 ns / 10 ps
module xlclockenablegenerator (clk, clr, ce);

   parameter signed [31:0] period  = 2;
   parameter signed [31:0] log_2_period = 1;
   parameter signed [31:0] pipeline_regs = 5;

   input clk;
   input clr;
   output ce;
   parameter signed [31:0] max_pipeline_regs = 8;
   parameter signed [31:0] num_pipeline_regs = (max_pipeline_regs > pipeline_regs)? pipeline_regs : max_pipeline_regs;
   parameter signed [31:0] factor = num_pipeline_regs/period;
   parameter signed [31:0] rem_pipeline_regs =  num_pipeline_regs - (period * factor) + 1;
   parameter [log_2_period-1:0] trunc_period = ~period + 1;
   parameter signed [31:0] period_floor = (period>2)? period : 2;
   parameter signed [31:0] power_of_2_counter = (trunc_period == period) ? 1 : 0;
   parameter signed [31:0] cnt_width = (power_of_2_counter & (log_2_period>1)) ? (log_2_period - 1) : log_2_period;
   parameter [cnt_width-1:0] clk_for_ce_pulse_minus1 = period_floor-2;
   parameter [cnt_width-1:0] clk_for_ce_pulse_minus2 = (period-3>0)? period-3 : 0;
   parameter [cnt_width-1:0] clk_for_ce_pulse_minus_regs = ((period-rem_pipeline_regs)>0)? (period-rem_pipeline_regs) : 0;
   reg [cnt_width-1:0] clk_num;
   reg temp_ce_vec;
   wire [num_pipeline_regs:0] ce_vec;
   wire internal_ce;
   reg cnt_clr;
   wire cnt_clr_dly;
   genvar index;
initial
   begin
      clk_num = 'b0;
   end
   always @(posedge clk)
     begin : cntr_gen
      if ((cnt_clr_dly == 1'b1) || (clr == 1'b1))
        begin:u1
           clk_num = {cnt_width{1'b0}};
        end
      else
        begin:u2
           clk_num = clk_num + 1 ;
        end
    end
   generate
      if (power_of_2_counter == 1)
        begin:clr_gen_p2
           always @(clr)
             begin:u1
                cnt_clr = clr;
             end
       end
   endgenerate
   generate
      if (power_of_2_counter == 0)
        begin:clr_gen
           always @(clk_num or clr)
             begin:u1
                if ( (clk_num == clk_for_ce_pulse_minus1) | (clr == 1'b1) )
                  begin:u2
                     cnt_clr = 1'b1 ;
                  end
                else
                  begin:u3
                     cnt_clr = 1'b0 ;
                  end
             end
       end
   endgenerate
   synth_reg_w_init #(1, 0, 'b0000, 1)
     clr_reg(.i(cnt_clr),
             .ce(1'b1),
             .clr(clr),
             .clk(clk),
             .o(cnt_clr_dly));

   generate
      if (period > 1)
        begin:pipelined_ce
           always @(clk_num)
             begin:np_ce_gen
                if (clk_num == clk_for_ce_pulse_minus_regs)
                  begin
                     temp_ce_vec = 1'b1 ;
                  end
                else
                  begin
                     temp_ce_vec = 1'b0 ;
                  end
             end

           for(index=0; index<num_pipeline_regs; index=index+1)
             begin:ce_pipeline
                synth_reg_w_init #(1, ((((index+1)%period)>0)?0:1), 1'b0, 1)
                  ce_reg(.i(ce_vec[index+1]),
                         .ce(1'b1),
                         .clr(clr),
                         .clk(clk),
                   .o(ce_vec[index]));
             end
          assign ce_vec[num_pipeline_regs] = temp_ce_vec;
          assign internal_ce = ce_vec[0];
      end
   endgenerate
   generate
      if (period > 1)
        begin:period_greater_than_1
          assign ce = internal_ce;
        end
   endgenerate

   generate
     if (period == 1)
       begin:period_1
         assign ce = 1'b1;
       end
   endgenerate
endmodule
