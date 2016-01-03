
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
module xlsprom_71 (addr, en, rst, ce, clr, clk, data);
   parameter core_name0= "";
   parameter latency= 1;
   parameter c_sinit_value= "0000";
   // synopsys translate_off
   parameter c_mem_init_file= "null.mif";
   // synopsys translate_on
   parameter c_has_sinit= 0;
   parameter uid= 0;
   parameter c_width= 12;
   parameter c_address_width= 4;
   parameter c_depth= 4;
   parameter c_pipe_stages= 0;
   input [c_address_width-1:0] addr;
   input en;
   input rst;
   input ce;
   input clr;
   input clk;
   output [c_width-1:0] data;
   wire [c_address_width-1:0] core_addr;
   wire [c_width-1:0] core_data_out;
   wire  core_ce, sinit;
   assign core_addr = addr;

   assign core_ce = ce & en;
   assign sinit = (rst | clr) & core_ce;
generate
[% names_already_seen = {}                                                 -%]
[% FOREACH name = core_name0                                               -%]
[%   NEXT IF (! name.defined || names_already_seen.$name == 1)             -%]
[%   names_already_seen.$name = 1                                          -%]
[%   i = loop.index                                                        -%]
[%   inst_txt = core_instance_text.$i                                      -%]
if (core_name0 == "[% name %]")
begin:comp[%2 * i + 1 %]
  [% name %] core_instance[% i %] (
                [% inst_txt %]
        );
end
[% END -%]
 if (latency > 1)
     begin:latency_test
        synth_reg # (c_width, latency-1)
          reg1 (
               .i(core_data_out),
               .ce(core_ce),
               .clr(clr),
               .clk(clk),
               .o(data));
     end

   if (latency <= 1)
     begin:latency_1
        assign data = core_data_out;
     end
endgenerate
endmodule
