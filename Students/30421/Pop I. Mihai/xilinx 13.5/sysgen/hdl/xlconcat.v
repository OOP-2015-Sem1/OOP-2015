
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
[% num_inputs_minus_1 = num_inputs - 1 -%]
module  [% entity_name %]  (
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
        din[% i %],
[% END -%]
 dout, ce, clr, clk);
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
parameter din[% i %]_width = 3;
parameter din[% i %]_bin_pt= 1;
parameter din[% i %]_arith = `xlUnsigned;
[% END -%]
parameter dout_width= 7;
parameter dout_bin_pt= 0;
parameter dout_arith= `xlUnsigned;
[% FOREACH i = [0 .. num_inputs_minus_1] -%]
input [din[% i %]_width-1:0] din[% i %];
[% END -%]
output [dout_width-1:0] dout;
input  ce;
input  clr;
input  clk;
[% port_def = " assign dout = {din0" -%]
[% FOREACH i = [1 .. num_inputs_minus_1] -%]
[%   port_def = "$port_def , din$i" -%]
[% END -%]
[%   port_def = "$port_def }" -%]
[% port_def %];
endmodule
