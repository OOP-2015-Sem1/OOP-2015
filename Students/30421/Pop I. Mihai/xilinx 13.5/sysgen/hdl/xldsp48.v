
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
module  xldsp48  (bcout, p, pcout, a, b, bcin, c, carryin, carryinsel, cea, ceb, cec, cecarryin, cecinsub, cectrl, cem, cep, op , opmode, pcin, rsta, rstb, rstc, rstcarryin, rstctrl, rstm, rstp, subtract, clk, en, rst, ce, clr);
parameter areg= 1;
parameter b_input = "DIRECT";
parameter legacy_mode = "NONE";
parameter breg = 1;
parameter carryinreg = 1;
parameter carryinselreg = 1;
parameter creg = 1;
parameter mreg = 1;
parameter opmodereg= 1;
parameter preg = 1;
parameter use_op = 0;
parameter subtractreg = 1;
parameter use_c_port = 1;
parameter c_use_cea = 0;
parameter c_use_ceb = 0;
parameter c_use_ce_mult = 0;
parameter c_use_cep = 0;
parameter c_use_cec = 0;
parameter c_use_ce_carry_in = 0;
parameter c_use_ce_ctrl = 0;
parameter c_use_cecinsub = 0;
parameter c_use_bcin = 0;
parameter c_use_en = 0;
parameter c_use_rst = 0;
parameter c_use_rsta = 0;
parameter c_use_rstb = 0;
parameter c_use_rstp = 0;
parameter c_use_rstc = 0;
parameter c_use_rstcarryin = 0;
parameter c_use_rstctrl = 0;
parameter c_use_rstm = 0;
parameter c_use_pcin = 0;
parameter c_use_b = 0;
parameter c_use_c = 0;
output [17:0] bcout;
output [47:0] p;
output [47:0] pcout;
input [17:0] a;
input [17:0] b;
input [17:0] bcin;
input [47:0] c;
input carryin;
input [1:0] carryinsel;
input  cea;
input  ceb;
input  cec;
input  cecarryin;
input  cecinsub;
input  cectrl;
input  cem;
input  cep;
input [10:0] op;
input [6:0] opmode;
input [47:0] pcin;
input  rsta;
input  rstb;
input  rstc;
input  rstcarryin;
input  rstctrl;
input  rstm;
input  rstp;
input  subtract;
input clk;
input en;
input rst;
input ce;
input clr;
wire  internal_cea;
wire  internal_ceb;
wire  internal_cec;
wire  internal_cep;
wire  internal_cem;
wire  internal_cecarryin;
wire  internal_cectrl;
wire  internal_en;
wire  internal_rst;
wire  internal_rsta;
wire  internal_rstb;
wire  internal_rstc;
wire  internal_rstcarryin;
wire  internal_rstctrl;
wire  internal_rstm;
wire  [47:0] internal_pcin;
wire  internal_cecinsub;
wire  internal_rstp;
wire [6:0] internal_opmode;
wire  internal_sub;
wire  internal_carryin;
wire [1:0] internal_carryin_sel;
wire [17:0] internal_b;
wire [17:0] internal_bcin;
wire [47:0] internal_c;
  assign internal_en = (c_use_en ? en : 1'b1);
  assign internal_cea = (ce & internal_en) & (c_use_cea ? cea : 1'b1);
  assign internal_ceb = (ce & internal_en) & (c_use_ceb ? ceb : 1'b1);
  assign internal_cem = (ce & internal_en) & (c_use_ce_mult ? cem : 1'b1);
  assign internal_cep = (ce & internal_en) & (c_use_cep ? cep : 1'b1);
  assign internal_cecarryin = (ce & internal_en) & (c_use_ce_carry_in ? cecarryin : 1'b1);
  assign internal_cectrl = (ce & internal_en) & (c_use_ce_ctrl ? cectrl : 1'b1);
  assign internal_cecinsub = (ce & internal_en) & (c_use_ce_ctrl ? cecinsub : 1'b1);
  assign internal_rst = (c_use_rst ? rst : 1'b0) | clr;
  assign internal_rsta = ((c_use_rsta ? rsta : 1'b0) | internal_rst) & ce;
  assign internal_rstb = ((c_use_rstb ? rstb : 1'b0) | internal_rst) & ce;
  assign internal_rstcarryin = ((c_use_rstcarryin ? rstcarryin : 1'b0) | internal_rst) & ce;
  assign internal_rstctrl = ((c_use_rstctrl ? rstctrl : 1'b0) | internal_rst) & ce;
  assign internal_rstm = ((c_use_rstm ? rstm : 1'b0) | internal_rst) & ce;
  assign internal_rstp = ((c_use_rstp ? rstp : 1'b0) | internal_rst) & ce;
  assign internal_bcin = (c_use_bcin ? bcin : 18'b000000000000000000);
  assign internal_b = (c_use_b ? b : 18'b000000000000000000);
  assign internal_c = (c_use_c ? c : 48'd0);
  assign internal_pcin = (c_use_pcin ? pcin : 48'd0);

generate
  if (use_op == 0)
  begin:opmode_0
        assign internal_opmode = opmode;
        assign internal_sub = subtract;
        assign internal_carryin = carryin;
        assign internal_carryin_sel = carryinsel;
  end

  if(use_op == 1)
  begin:opmode_1
        assign internal_opmode = op [6:0];
        assign internal_sub = op[7];
        assign internal_carryin = op[8];
        assign internal_carryin_sel = op[10:9];
  end
  if (use_c_port == 1)
  begin:using_cport
    assign internal_cec = (ce & internal_en) & (c_use_cec ? cec : 1'b1);
    assign internal_rstc = (c_use_rstc ? rstc : 1'b0) | internal_rst;
  end

  if (use_c_port == 0)
  begin:not_using_cport
    assign internal_cec = 1'b1;
    assign internal_rstc = 1'b1;
  end
endgenerate
   DSP48 dsp48_inst(.BCOUT(bcout),
                    .P(p),
                    .PCOUT(pcout),
                    .A(a),
                    .B(internal_b),
                    .BCIN(internal_bcin),
                    .C(internal_c),
                    .CARRYIN(internal_carryin),
                    .CARRYINSEL(internal_carryin_sel),
                    .CEA(internal_cea),
                    .CEB(internal_ceb),
                    .CEC(internal_cec),
                    .CECARRYIN(internal_cecarryin),
                    .CECINSUB(internal_cecinsub),
                    .CECTRL(internal_cectrl),
                    .CEM(internal_cem),
                    .CEP(internal_cep),
                    .CLK(clk),
                    .OPMODE(internal_opmode),
                    .PCIN(internal_pcin),
                    .RSTA(internal_rsta),
                    .RSTB(internal_rstb),
                    .RSTC(internal_rstc),
                    .RSTCARRYIN(internal_rstcarryin),
                    .RSTCTRL(internal_rstctrl),
                    .RSTM(internal_rstm),
                    .RSTP(internal_rstp),
                    .SUBTRACT(internal_sub));
defparam
dsp48_inst.AREG = areg,
dsp48_inst.B_INPUT = b_input,
dsp48_inst.LEGACY_MODE = legacy_mode,
dsp48_inst.BREG =breg,
dsp48_inst.CARRYINREG = carryinreg,
dsp48_inst.CARRYINSELREG = carryinselreg,
dsp48_inst.CREG = creg,
dsp48_inst.MREG = mreg,
dsp48_inst.OPMODEREG = opmodereg,
dsp48_inst.PREG = preg,
dsp48_inst.SUBTRACTREG = subtractreg;
endmodule
