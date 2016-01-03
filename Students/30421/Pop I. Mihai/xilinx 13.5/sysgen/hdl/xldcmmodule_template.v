//-------------------------------------------------------------
// (c) Copyright 1995 - [% T.MetaData.Year %] Xilinx, Inc. All rights reserved.
//
// This file contains confidential and proprietary information
// of Xilinx, Inc. and is protected under U.S. and 
// international copyright and other intellectual property
// laws.
//
// DISCLAIMER
// This disclaimer is not a license and does not grant any
// rights to the materials distributed herewith. Except as
// otherwise provided in a valid license issued to you by
// Xilinx, and to the maximum extent permitted by applicable
// law: (1) THESE MATERIALS ARE MADE AVAILABLE "AS IS" AND
// WITH ALL FAULTS, AND XILINX HEREBY DISCLAIMS ALL WARRANTIES
// AND CONDITIONS, EXPRESS, IMPLIED, OR STATUTORY, INCLUDING
// BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY, NON-
// INFRINGEMENT, OR FITNESS FOR ANY PARTICULAR PURPOSE; and
// (2) Xilinx shall not be liable (whether in contract or tort,
// including negligence, or under any other theory of
// liability) for any loss or damage of any kind or nature
// related to, arising under or in connection with these
// materials, including for any direct, or any indirect,
// special, incidental, or consequential loss or damage
// (including loss of data, profits, goodwill, or any type of
// loss or damage suffered as a result of any action brought
// by a third party) even if such damage or loss was
// reasonably foreseeable or Xilinx had been advised of the
// possibility of the same.
//
// CRITICAL APPLICATIONS
// Xilinx products are not designed or intended to be fail-
// safe, or for use in any application requiring fail-safe
// performance, such as life-support or safety devices or
// systems, Class III medical devices, nuclear facilities,
// applications related to the deployment of airbags, or any
// other applications that could lead to death, personal
// injury, or severe property or environmental damage
// (individually and collectively, "Critical
// Applications"). Customer assumes the sole risk and
// liability of any use of Xilinx products in Critical
// Applications, subject only to applicable laws and
// regulations governing limitations on product liability.
//
// THIS COPYRIGHT NOTICE AND DISCLAIMER MUST BE RETAINED AS
// PART OF THIS FILE AT ALL TIMES.
//-------------------------------------------------------------

module xlnegedgedet (a, clk, dout);

  input a;
  input clk;
  output dout;
  reg delayed_a;

  always @(posedge clk)
  begin
    delayed_a = a;
  end

  assign dout = delayed_a & ~a;

endmodule

//----------------------------------------------------------------------------

module xldcmlockrelease (clk, reset, locked, releasebufgce);

  parameter EXTRA_DELAY = 0;

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
  // Initialize all nets and out port connectivity
  assign reset_net = reset;
  assign locked_net = locked;
  assign locked_bar_net = ~locked;

// synopsys translate_off
  assign simulation_mux = 1'b1;
// synopsys translate_on
  always@ (simulation_mux, locked_net, release_net)
  begin
// synopsys translate_off
    if (simulation_mux == 1'b1)
      releasebufgce = locked_net;
    else
// synopsys translate_on
      releasebufgce = release_net;
  end

  // Capture the falling edge on the reset_net
  xlnegedgedet falling_edge_on_reset (
    .a(reset_net),
    .clk(clk),
    .dout(reset_neg_edge_pulse_net)
  );

  FDRE capture_falling_edge_on_reset (
    .C(clk),
    .CE(reset_neg_edge_pulse_net),
    .D(1'b1),
    .R(reset_net),
    .Q(reset_neg_edge_capture_net)
  );

  // Detect rising edge on the locked signal after
  // falling edge on reset signal. Required for
  // consistent DCM locking. Answer record : 14425
  xlnegedgedet rising_edge_detect_on_locked (
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
    if (EXTRA_DELAY == 0)
    begin : virtex4_mode
      assign release_net = release_net_temp;
    end

    if (EXTRA_DELAY == 1)
    begin : virtex5_spartan3adsp_mode
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

//----------------------------------------------------------------------------

module xlresetgenerator (clk, lock, user_reset, dcm_reset);

  parameter N_RESET_STAGES = 3;

  input clk;
  input lock;
  input user_reset;
  output dcm_reset;

  wire det_neg_edge_lock_signal;
  wire loss_of_lock_or_reset;
  reg [N_RESET_STAGES-1:0] reset_stages = {N_RESET_STAGES{1'b1}};

  xlnegedgedet neg_edge_det_inst (
    .a(lock),
    .clk(clk),
    .dout(det_neg_edge_lock_signal)
  );

  assign loss_of_lock_or_reset = det_neg_edge_lock_signal | user_reset;

  always @(posedge clk)
  begin
    if (loss_of_lock_or_reset)
      reset_stages <= {N_RESET_STAGES{1'b1}};
    else
      reset_stages <= reset_stages << 1;
  end

  assign dcm_reset = reset_stages[N_RESET_STAGES-1];

endmodule

//----------------------------------------------------------------------------

(* core_generation_info = "[% T.ModuleName %],platform_api_clocking_dcm,{Family=[% T.Family %],Device=[% T.Device %],Speed=[% T.Speed %],Package=[% T.Package %],HDLLanguage=[% T.HDLLanguage %],InputClockPeriod=[% T.InputClock.Period %],InputClockIsDifferential=[% T.InputClock.IsDifferential %],
[%- OutputClockInfo = ''                                                            -%]
[%- i = 1                                                                           -%]
[%- FOREACH p = T.OutputClocks                                                      -%]
OutputClock[% i %]Period=[% p.Period %],
[%-   i = i + 1                                                                     -%]
[%- END                                                                             -%]
}" *)
module [% T.ModuleName %] (
  reset,
[%  IF T.InputClock.IsDifferential                                                  -%]
  clkin_p,
  clkin_n,
[%  ELSE                                                                            -%]
  clkin,
[%  END                                                                             -%]
[%  FOREACH p = T.OutputClocks                                                      -%]
  [% p.Name %],
[%  END                                                                             -%]
  locked
);

  input reset;
[%  IF T.InputClock.IsDifferential                                                  -%]
  input clkin_p;
  input clkin_n;
[%  ELSE                                                                            -%]
  input clkin;
[%  END                                                                             -%]
[%  FOREACH p = T.OutputClocks                                                      -%]
  output [% p.Name %];
[%  END                                                                             -%]
  output locked;

  wire clkin_net;
  wire releasebufgce;
  wire dcm_reset;
  wire dcm_clk0;
  wire dcm_clk2x;
  wire dcm_clkdv;
  wire dcm_clkfx;
  wire dcm_clk0_buf;
  wire dcm_clk2x_buf;
  wire dcm_clkdv_buf;
  wire dcm_clkfx_buf;
  wire dcm_clkfb;
  wire dcm_locked;

[%  IF T.InputClock.IsDifferential                                                  -%]
  IBUFGDS ibufgds_clkin (
    .I(clkin_p),
    .IB(clkin_n),
    .O(clkin_net)
  );
[%  ELSE                                                                            -%]
  IBUFG ibufg_clkin (
    .I(clkin),
    .O(clkin_net)
  );
[%  END                                                                             -%]

  assign locked = dcm_locked;

  xlresetgenerator resetgenerator_inst (
    .clk(clkin_net),
    .lock(dcm_locked),
    .user_reset(reset),
    .dcm_reset(dcm_reset)
  );

  xldcmlockrelease #(
[%  IF T.Family == 'virtex4'                                                        -%]
    .EXTRA_DELAY(1)
[%  ELSE                                                                            -%]
    .EXTRA_DELAY(0)
[%  END                                                                             -%]
  )
  dcmlockrelease_inst (
    .clk(clkin_net),
    .locked(dcm_locked),
    .reset(dcm_reset),
    .releasebufgce(releasebufgce)
  );

[%  IF T.Family == 'virtex4' || T.Family == 'virtex5'                               -%]
  DCM_ADV #(
[%  IF T.ClockGenerator.DCM.ClkFx.Used                                              -%]
    .CLKFX_MULTIPLY([% T.ClockGenerator.DCM.ClkFx.Multiplier %]),
    .CLKFX_DIVIDE([% T.ClockGenerator.DCM.ClkFx.Divisor %]),
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkDv.Used                                              -%]
    .CLKDV_DIVIDE([% T.ClockGenerator.DCM.ClkDv.Divisor.replace('^(\d+)$', '$1.0') %]),
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DFSFrequencyMode.length != ''                           -%]
    .DFS_FREQUENCY_MODE("[% T.ClockGenerator.DCM.DFSFrequencyMode %]"),
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DLLFrequencyMode.length != ''                           -%]
    .DLL_FREQUENCY_MODE("[% T.ClockGenerator.DCM.DLLFrequencyMode %]"),
[%  END                                                                             -%]
    .CLKIN_PERIOD([% T.Platform.Clock.Period.replace('^(\d+)$', '$1.0') %]),
    .CLKIN_DIVIDE_BY_2("[% T.ClockGenerator.DCM.ClkInDivideBy2 ? 'TRUE' : 'FALSE' %]"),
    .CLKOUT_PHASE_SHIFT("NONE"),
    .SIM_DEVICE("[% T.Family %]"),
    .CLK_FEEDBACK("1X"),
    .PHASE_SHIFT(0)
  )
  dcm_inst (
    .RST(dcm_reset),
    .CLKIN(clkin_net),
    .CLK0(dcm_clk0),
    .CLK180(),
    .CLK270(),
    .CLK2X(dcm_clk2x),
    .CLK2X180(),
    .CLK90(),
    .CLKDV(dcm_clkdv),
    .CLKFB(dcm_clkfb),
    .CLKFX(dcm_clkfx),
    .CLKFX180(),
    .LOCKED(dcm_locked),
    .DADDR(7'b0),
    .DCLK(1'b0),
    .DEN(1'b0),
    .DI(16'b0),
    .DO(),
    .DRDY(),
    .DWE(1'b0),
    .PSCLK(1'b0),
    .PSEN(1'b0),
    .PSINCDEC(1'b0),
    .PSDONE()
  );
[%  ELSE                                                                            -%]
  DCM #(
[%  IF T.ClockGenerator.DCM.ClkFx.Used                                              -%]
    .CLKFX_MULTIPLY([% T.ClockGenerator.DCM.ClkFx.Multiplier %]),
    .CLKFX_DIVIDE([% T.ClockGenerator.DCM.ClkFx.Divisor %]),
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkDv.Used                                              -%]
    .CLKDV_DIVIDE([% T.ClockGenerator.DCM.ClkDv.Divisor.replace('^(\d+)$', '$1.0') %]),
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DFSFrequencyMode.length != ''                           -%]
    .DFS_FREQUENCY_MODE("[% T.ClockGenerator.DCM.DFSFrequencyMode %]"),
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DLLFrequencyMode.length != ''                           -%]
    .DLL_FREQUENCY_MODE("[% T.ClockGenerator.DCM.DLLFrequencyMode %]"),
[%  END                                                                             -%]
    .CLKIN_PERIOD([% T.Platform.Clock.Period.replace('^(\d+)$', '$1.0') %]),
    .CLKIN_DIVIDE_BY_2("[% T.ClockGenerator.DCM.ClkInDivideBy2 ? 'TRUE' : 'FALSE' %]"),
    .CLKOUT_PHASE_SHIFT("NONE"),
    .CLK_FEEDBACK("1X"),
    .PHASE_SHIFT(0)
  )
  dcm_inst (
    .RST(dcm_reset),
    .CLKIN(clkin_net),
    .CLK0(dcm_clk0),
    .CLK180(),
    .CLK270(),
    .CLK2X(dcm_clk2x),
    .CLK2X180(),
    .CLK90(),
    .CLKDV(dcm_clkdv),
    .CLKFB(dcm_clkfb),
    .CLKFX(dcm_clkfx),
    .CLKFX180(),
    .LOCKED(dcm_locked),
    .DSSEN(1'b0),
    .PSCLK(1'b0),
    .PSEN(1'b0),
    .PSINCDEC(1'b0),
    .PSDONE(),
    .STATUS()
  );
[%  END                                                                             -%]

[%  IF T.ClockGenerator.DCM.Clk0.Used                                               -%]
  BUFGCE bufgce_dcm_clk (
    .I(dcm_clk0),
    .CE(releasebufgce),
    .O(dcm_clk0_buf)
  );

[%    FOREACH p = T.ClockGenerator.DCM.Clk0.MappedPorts                             -%]
  assign [% p %] = dcm_clk0_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.Clk2x.Used                                              -%]
  BUFGCE bufgce_dcm_clk2x (
    .I(dcm_clk2x),
    .CE(releasebufgce),
    .O(dcm_clk2x_buf)
  );

[%    FOREACH p = T.ClockGenerator.DCM.Clk2x.MappedPorts                            -%]
  assign [% p %] = dcm_clk2x_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkDv.Used                                              -%]
  BUFGCE bufgce_dcm_clkdv (
    .I(dcm_clkdv),
    .CE(releasebufgce),
    .O(dcm_clkdv_buf)
  );

[%    FOREACH p = T.ClockGenerator.DCM.ClkDv.MappedPorts                            -%]
  assign [% p %] = dcm_clkdv_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkFx.Used                                              -%]
  BUFGCE bufgce_dcm_clkfx (
    .I(dcm_clkfx),
    .CE(releasebufgce),
    .O(dcm_clkfx_buf)
  );

[%    FOREACH p = T.ClockGenerator.DCM.ClkFx.MappedPorts                            -%]
  assign [% p %] = dcm_clkfx_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
  BUFG bufg_dcm_clkfb (
    .I(dcm_clk0),
    .O(dcm_clkfb)
  );

endmodule
