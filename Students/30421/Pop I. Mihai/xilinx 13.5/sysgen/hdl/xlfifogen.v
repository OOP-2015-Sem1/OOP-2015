
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
module xlfifogen (din, we, we_ce, re, re_ce, rst, en, ce, clk,
  empty, full, percent_full, dcount, ae, af, dout);
  parameter core_name0 = "";
  parameter data_width = -1;
  parameter data_count_width = -1;
  parameter percent_full_width = -1;
  parameter has_ae = 0;
  parameter has_af = 0;
  input [data_width-1:0] din;
  input ce, clk, en, re, re_ce, rst, we, we_ce;
  output empty, full;
  output reg [percent_full_width-1:0] percent_full;
  output [data_count_width-1:0] dcount;
  output ae, af;
  output [data_width-1:0] dout;
  wire rd_en, wr_en, srst, core_ae, core_af;
  assign rd_en = re & en & re_ce;
  assign wr_en = we & en & we_ce;
  assign srst = rst & ce;
  assign ae = (has_ae) ? core_ae : 0;
  assign af = (has_af) ? core_af : 0;
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
  endgenerate

  always @ (full or dcount)
  begin:modify_count
    percent_full = (full == 1) ?
      {percent_full_width {1'b1}} :
      dcount[(data_count_width-1):(data_count_width-percent_full_width)];
  end
endmodule
