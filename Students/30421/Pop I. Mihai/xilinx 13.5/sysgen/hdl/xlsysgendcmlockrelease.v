
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
module xlsysgendcmlockrelease(clk, reset, locked, releasebufgce);
parameter extra_delay = 0;
input clk;
input reset;
input locked;
output reg releasebufgce;
wire reset_net;
wire reset_neg_edge_pulse_net;
wire reset_neg_edge_capture_net;
wire locked_net;
wire locked_bar_net;
wire locked_pos_edge_pulse_net;
wire locked_pos_edge_capture_net;
wire release_net;
wire release_net_temp;
wire simulation_mux;
  assign reset_net = reset;
  assign locked_net = locked;
  assign locked_bar_net = ~locked;
  // synopsys translate_off
  assign simulation_mux = 1'b1;
  // synopsys translate_on
  always@ (simulation_mux, locked_net, release_net)
  begin
  //synopsys translate_off
    if (simulation_mux == 1'b1)
      releasebufgce = locked_net;
    else
  //synopsys translate_on
      releasebufgce = release_net;
  end
  xlnegedgedet falling_edge_on_reset(
    .a(reset_net),
    .clk(clk),
    .dout(reset_neg_edge_pulse_net)
  );
  FDRE capture_falling_edge_on_reset(
    .C(clk),
    .CE(reset_neg_edge_pulse_net),
    .D(1'b1),
    .R(reset_net),
    .Q(reset_neg_edge_capture_net)
  );
  xlnegedgedet rising_edge_detect_on_locked(
    .a(locked_bar_net),
    .clk(clk),
    .dout(locked_pos_edge_pulse_net)
  );
  FDRE capture_rising_edge_on_locked (
    .C(clk),
    .CE(locked_pos_edge_capture_net),
    .D(1'b1),
    .R(reset_net),
    .Q(release_net_temp)
  );
  assign locked_pos_edge_capture_net = reset_neg_edge_capture_net & locked_pos_edge_pulse_net;
generate
if (extra_delay == 0)
begin:virtex4_mode
  assign release_net = release_net_temp;
end
if (extra_delay == 1)
begin:virtex5_spartan3adsp_mode
  FDRE extra_delay_reg (
    .C(clk),
    .CE(1'b1),
    .D(release_net_temp),
    .R(reset_net),
    .Q(release_net)
  );
end
endgenerate

endmodule
