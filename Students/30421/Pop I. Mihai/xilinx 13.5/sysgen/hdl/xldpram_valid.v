
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
module xldpram_valid (dina, addra, wea, a_ce, a_clr, a_clk, rst_a, douta, vouta, dinb, addrb, web, b_ce, b_clr, b_clk, rst_b, doutb, voutb);
parameter core_name0= "";
parameter c_width_a= 13;
parameter c_depth_a= 16;
parameter c_address_width_a= 4;
parameter c_pipe_stages_a= 0;
parameter c_write_modea= 0;
parameter c_width_b= 13;
parameter c_depth_b= 16;
parameter c_address_width_b= 4;
parameter c_write_modeb= 0;
parameter c_pipe_stages_b= 0;
parameter c_sinita_value= "0";
parameter c_sinitb_value= "0";
parameter c_mem_init_file= "null.mif";
parameter uid= 0;
parameter latency= 1;
input [c_width_a-1:0] dina;
input [c_address_width_a-1:0] addra;
input wea, a_ce, a_clr, a_clk, rst_a;
input [c_width_b-1:0] dinb;
input [c_address_width_b-1:0] addrb;
input web, b_ce, b_clr, b_clk, rst_b;
output [c_width_a-1:0] douta;
output [c_width_b-1:0] doutb;
output vouta, voutb;
wire addra_valid_vec_in, addra_valid_vec_out;
wire addrb_valid_vec_in, addrb_valid_vec_out;
wire [c_address_width_a-1:0] core_addra;
wire [c_address_width_b-1:0] core_addrb;
wire [c_address_width_b-1:0] high_addra;
wire [c_width_a-1:0] core_dina, core_douta, dly_douta;
wire [c_width_b-1:0] core_dinb, core_doutb, dly_doutb;
wire dly_addra_valid, dly_addrb_valid;
wire core_wea, core_web, mod_core_wea, mod_core_web;
wire core_a_ce, core_b_ce;
reg rw_error_a, rw_error_b;
wire rw_error_a_dly, rw_error_b_dly;
wire rw_error_a_vec_in, rw_error_a_vec_out;
wire rw_error_b_vec_in, rw_error_b_vec_out;
reg writeError;
wire sinita, sinitb, not_writeError;
   always @(a_ce or b_ce or high_addra or core_addrb or core_wea or core_web)
     begin:rw_error_detection_a
        if ((a_ce == 1'b1) & (b_ce == 1'b1) & (high_addra == core_addrb))
          begin
             if ((core_wea == 1'b1) & (core_web == 1'b1))
               begin
                  writeError = 1'b1;
               end
             else
               begin
                  writeError = 1'b0;
               end
             if ( ((core_wea == 1'b0) & (core_web == 1'b1) & !(c_write_modeb == 1))
                 |((core_wea == 1'b1) & (core_web == 1'b1) & !(c_write_modea == 2)))
               begin
                  rw_error_a = 1'b1;
               end
             else
               begin
                  if ( (core_wea == 1'b1) & (core_web == 1'b0) & (c_write_modea == 2) )
                    begin
                       rw_error_a = rw_error_a_dly;
                    end
                  else
                    begin
                       rw_error_a = 1'b0;
                    end
               end
          end
        else
          begin
             writeError = 1'b0;
             if ( (core_wea == 1'b1) & (c_write_modea == 2) )
               begin
                  rw_error_a = rw_error_a_dly;
               end
             else
               begin
                  rw_error_a = 1'b0;
               end
          end
     end
generate
   if (c_write_modea == 2)
     begin:dly_rw_error_a_case
        synth_reg_w_init # (1, 0, 1'b0, 1)
         rw_error_a_reg ( .i(rw_error_a),
                          .ce(a_ce),
                          .clr(a_clr),
                          .clk(a_clk),
                          .o(rw_error_a_dly));
     end
endgenerate
   always @(a_ce or b_ce or high_addra or core_addrb or core_wea or core_web)
     begin:rw_error_detection_b
        if ((a_ce == 1'b1) & (b_ce == 1'b1) & (high_addra == core_addrb))
          begin
             if ( ((core_web == 1'b0) & (core_wea == 1'b1) & !(c_write_modea == 1))
                 |((core_wea == 1'b1) & (core_web == 1'b1) & !(c_write_modeb == 2)))
               begin
                  rw_error_b = 1'b1;
               end
             else
               begin
                  if ( (core_web == 1'b1) & (core_wea == 1'b0) & (c_write_modeb == 2) )
                    begin
                       rw_error_b = rw_error_b_dly;
                    end
                  else
                    begin
                       rw_error_b = 1'b0;
                    end
               end
          end
        else
          begin
             if ( (core_web == 1'b1) & (c_write_modeb == 2) )
               begin
                  rw_error_b = rw_error_b_dly;
               end
             else
               begin
                  rw_error_b = 1'b0;
               end
          end
     end
generate
   if (c_write_modeb == 2)
     begin:dly_rw_error_b_case
        synth_reg_w_init # (1, 0, 1'b0, 1)
         rw_error_b_reg ( .i(rw_error_b),
                          .ce(b_ce),
                          .clr(b_clr),
                          .clk(b_clk),
                          .o(rw_error_b_dly));
     end
endgenerate
   assign core_addra = addra;
   assign high_addra = addra[c_address_width_a - 1:(c_address_width_a-c_address_width_b)];
   assign core_dina = dina;
   assign douta = dly_douta;
   assign vouta = dly_addra_valid;
   assign core_wea = wea;
   assign mod_core_wea = wea & !writeError;
   assign core_a_ce = a_ce;
   assign sinita = (rst_a | a_clr) & a_ce;
   assign addra_valid_vec_in = !rw_error_a;

synth_reg_w_init # (1, 1, 1'b1, 1)
  synth_reg_instA ( .i(addra_valid_vec_in),
                    .ce(a_ce),
                    .clr(sinita),
                    .clk(a_clk),
                    .o(dly_addra_valid));
   assign core_addrb = addrb;
   assign core_dinb = dinb;
   assign doutb = dly_doutb;
   assign voutb = dly_addrb_valid;
   assign core_web = web;
   assign mod_core_web = web & !writeError;
   assign core_b_ce = b_ce;
   assign sinitb = (rst_b | b_clr) & b_ce;
   assign addrb_valid_vec_in = !rw_error_b;

synth_reg_w_init # (1, 1, 1'b1, 1)
  synth_reg_instB ( .i(addrb_valid_vec_in),
                    .ce(b_ce),
                    .clr(sinitb),
                    .clk(b_clk),
                    .o(dly_addrb_valid));

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
      .addra(core_addra),
      .clka(a_clk),
      .addrb(core_addrb),
      .clkb(b_clk),
      .dina(core_dina),
      .wea(mod_core_wea),
      .dinb(core_dinb),
      .web(mod_core_web),
      .ena(core_a_ce),
      .enb(core_b_ce),
      .sinita(sinita),
      .sinitb(sinitb),
      .douta(core_douta),
      .doutb(core_doutb));
  end
[% END -%]
if (latency > 2)
  begin:latency_test_instA
   synth_reg # (c_width_a, latency-1-c_pipe_stages_a)
   regA(
     .i(core_douta),
     .ce(a_ce),
     .clr(a_clr),
     .clk(a_clk),
     .o(dly_douta));
  end
if (latency > 2)
  begin:latency_test_instB
   synth_reg # (c_width_b, latency-1-c_pipe_stages_b)
   regB(
     .i(core_doutb),
     .ce(b_ce),
     .clr(b_clr),
     .clk(b_clk),
     .o(dly_doutb));
   end

if (latency <= 1)
   begin:latency1
     assign dly_douta = core_douta;
     assign dly_doutb = core_doutb;
   end
endgenerate

endmodule
