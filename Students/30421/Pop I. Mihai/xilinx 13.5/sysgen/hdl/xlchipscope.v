
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


module [% entity_name %] ( [% trig_port_interface %] [% data_port_interface %] ce, clr, clk);
[% trig_port_declaration %]
[% data_port_declaration %]
input ce;
input clr;
input clk;

wire [[% c_data_width %]-1:0] data;
wire [35:0] control;

[% port_def = "assign data = {data0" -%]
[% num_data_ports_minus_1 = num_data_ports - 1 -%]
[% FOREACH i = [1 .. num_data_ports_minus_1] -%]
[%   port_def = "$port_def , data$i" -%]
[% END -%]
[%   port_def = "$port_def }" -%]
[% IF (! data_is_trigger) -%]
    [% port_def %];
[% END -%]

[% ila_core_name %]  i_ila (
  .CONTROL(control),
[% num_trig_ports_minus_1 = num_trig_ports - 1 -%]
[% FOREACH i = [0 .. num_trig_ports_minus_1] -%]
        .TRIG[% i %](trig[% i %]),
[% END -%]
[% IF (! data_is_trigger) -%]
        .DATA(data),
[% END -%]
        .CLK(clk)
    );

[% icon_core_name %] i_icon_for_syn (
  .CONTROL0(control)
);

endmodule


// synopsys translate_off
module icon_bscan_bufg (DRCK_LOCAL_I, DRCK_LOCAL_O);
 input [0:0] DRCK_LOCAL_I;
 output [0:0] DRCK_LOCAL_O;
endmodule
// synopsys translate_on
