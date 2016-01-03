
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
module  xldsp48e1  (acout, bcout, carrycascout, carryout, multsignout, overflow, underflow, patterndetect, patternbdetect, p, pcout, a, acin, alumode, b, bcin, c, carrycascin, carryin, carryinsel, cea1, cea2, cead, cealumode, ceb1, ceb2, cec, cecarryin, cectrl, ced, ceinmode, cem, cemultcarryin, cep, multsignin, op, opmode, pcin, rsta, rstcarryin, rstalumode, rstb, rstc, rstctrl, rstd, rstinmode, rstm, rstp, clk, d, inmode, en, rst, ce);
parameter integer areg= 1;
parameter a_input = "DIRECT";
parameter legacy_mode = "NONE";
parameter integer carryout_width = 4;
parameter integer breg = 1;
parameter integer acascreg = 1;
parameter integer adreg = 1;
parameter integer bcascreg = 1;
parameter integer carryinreg = 1;
parameter integer carryinselreg = 1;
parameter integer creg = 1;
parameter integer dreg = 1;
parameter integer inmodereg = 1;
parameter integer mreg = 1;
parameter integer opmodereg= 1;
parameter integer preg = 1;
parameter use_op = 0;
parameter use_c_port = 1;
parameter c_use_cea1 = 0;
parameter c_use_cea2 = 0;
parameter c_use_cead = 0;
parameter c_use_ced  = 0;
parameter c_use_ceinmode = 0;
parameter c_use_rstd = 0;
parameter c_use_rstinmode = 0;
parameter c_use_ceb1 = 0;
parameter c_use_ceb2 = 0;
parameter c_use_cemultcarryin = 0;
parameter c_use_cem = 0;
parameter c_use_cep = 0;
parameter c_use_cec = 0;
parameter c_use_cecarryin = 0;
parameter c_use_cectrl = 0;
parameter c_use_bcin = 0;
parameter c_use_a = 1;
parameter c_use_acin = 0;
parameter c_use_en = 0;
parameter c_use_rst = 0;
parameter c_use_rsta = 0;
parameter c_use_rstb = 0;
parameter c_use_rstp = 0;
parameter c_use_rstc = 0;
parameter c_use_rstcarryin = 0;
parameter c_use_rstalumode = 0;
parameter c_use_rstctrl = 0;
parameter c_use_rstm = 0;
parameter c_use_pcin = 0;
parameter c_use_b = 0;
parameter c_use_c = 0;
parameter c_use_cealumode = 0;
parameter alumodereg = 1;
parameter autoreset_pattern_detect = "NO_RESET";
parameter b_input = "DIRECT";
parameter mask = 48'h3FFFFFFFFFFF;
parameter pattern = 48'h000000000000;
parameter sel_mask = "MASK";
parameter sel_pattern = "PATTERN";
parameter use_dport = "FALSE";
parameter use_mult = "MULTIPLY";
parameter use_pattern_detect = "NO_PATDET";
parameter use_simd = "ONE48";
output [29:0] acout;
output [17:0] bcout;
output carrycascout;
output [3:0]carryout;
output multsignout;
output [47:0] p;
output patternbdetect;
output patterndetect;
output [47:0] pcout;
output underflow;
output overflow;
input [29:0] a;
input [29:0] acin;
input [3:0] alumode;
input [17:0] b;
input [17:0] bcin;
input [47:0] c;
input carrycascin;
input carryin;
input [2:0] carryinsel;
input cea1;
input cea2;
input cead;
input ceb1;
input ceb2;
input cealumode;
input cec;
input cecarryin;
input cectrl;
input ced;
input ceinmode;
input cem;
input cemultcarryin;
input cep;
input multsignin;
input [19:0] op;
input [6:0] opmode;
input [47:0] pcin;
input rsta;
input rstcarryin;
input rstalumode;
input rstb;
input rstc;
input rstctrl;
input rstd;
input rstinmode;
input rstm;
input rstp;
input clk;
input [24:0] d;
input [4:0] inmode;
input en;
input rst;
input ce;
wire internal_cea1;
wire internal_cea2;
wire internal_cead;
wire internal_ceb1;
wire internal_ceb2;
wire internal_cec;
wire internal_cep;
wire internal_cem;
wire internal_cemultcarryin;
wire internal_cecarryin;
wire internal_cectrl;
wire internal_ced;
wire internal_ceinmode;
wire internal_cealumode;
wire internal_en;
wire internal_rst;
wire internal_rsta;
wire internal_rstb;
wire internal_rstc;
wire internal_rstd;
wire internal_rstinmode;
wire internal_rstalumode;
wire internal_rstcarryin;
wire internal_rstctrl;
wire internal_rstm;
wire [47:0] internal_pcin;
wire internal_rstp;
wire [6:0] internal_opmode;
wire internal_sub;
wire internal_carryin;
wire [2:0] internal_carryin_sel;
wire [3:0] internal_alumode;
wire [29:0] internal_a;
wire [29:0] internal_acin;
wire [17:0] internal_b;
wire [17:0] internal_bcin;
wire [47:0] internal_c;
wire [3:0] internal_carryout;
wire [24:0] internal_d;
wire [4:0]  internal_inmode;
  assign internal_en = (c_use_en ? en : 1'b1);
  assign internal_cem = (ce & internal_en) & (c_use_cem ? cem : 1'b1);
  assign internal_cemultcarryin = (ce & internal_en) & (c_use_cemultcarryin ? cemultcarryin : 1'b1);
  assign internal_cep = (ce & internal_en) & (c_use_cep ? cep : 1'b1);
  assign internal_cecarryin = (ce & internal_en) & (c_use_cecarryin ? cecarryin : 1'b1);
  assign internal_cectrl = (ce & internal_en) & (c_use_cectrl ? cectrl : 1'b1);
  assign internal_ced = (ce & internal_en) & (dreg ? ced : 1'b1);
  assign internal_ceinmode = (ce & internal_en) & (c_use_ceinmode ? ceinmode :  1'b1);
  assign internal_cealumode = (ce & internal_en) & (c_use_cealumode ? cealumode : 1'b1);
  assign internal_rst = (c_use_rst ? rst : 1'b0);
  assign internal_rsta = (c_use_rsta ? rsta : 1'b0) | internal_rst;
  assign internal_rstb = (c_use_rstb ? rstb : 1'b0) | internal_rst;
  assign internal_rstcarryin = (c_use_rstcarryin ? rstcarryin : 1'b0) | internal_rst;
  assign internal_rstctrl = (c_use_rstctrl ? rstctrl : 1'b0) | internal_rst;
  assign internal_rstd = (use_dport ? rstd : 1'b0) | internal_rst;
  assign internal_rstinmode = (use_dport ? rstinmode : 1'b0) | internal_rst;
  assign internal_rstm = (c_use_rstm ? rstm : 1'b0) | internal_rst;
  assign internal_rstp = (c_use_rstp ? rstp : 1'b0) | internal_rst;
  assign internal_rstalumode = (c_use_rstalumode ? rstalumode : 1'b0) | internal_rst;
  assign internal_acin = (c_use_acin ? acin : 30'b00000000000000000000000000000);
  assign internal_bcin = (c_use_bcin ? bcin : 18'b000000000000000000);
  assign internal_a = (c_use_a ? a : 30'd0);
  assign internal_b = (c_use_b ? b : 18'd0);
  assign internal_c = (c_use_c ? c : 48'd0);
  assign internal_pcin = (c_use_pcin ? pcin : 48'd0);
  assign internal_d = (use_dport ? d : 24'd0);

generate
  if(areg == 0)
  begin: cea_0
        assign internal_cea1 = 1'b1;
        assign internal_cea2 = 1'b1;
  end
  if(areg == 1)
  begin: cea_1
        assign internal_cea1 = 1'b1;
        assign internal_cea2 = (ce & internal_en) & (c_use_cea1 ? cea1 : 1'b1);
  end

  if(areg == 2)
  begin: cea_2
       assign internal_cea1 = (ce & internal_en) & (c_use_cea1 ? cea1 : 1'b1);
       assign internal_cea2 = (ce & internal_en) & (c_use_cea2 ? cea2 : 1'b1);
  end
  if(adreg == 1)
  begin: cead_1
        assign internal_cead = (ce & internal_en) & cead;
  end
  if(adreg == 0)
  begin: cead_0
        assign internal_cead = 0;
  end
  if(breg == 0)
  begin: ceb_0
        assign internal_ceb1 = 1'b1;
        assign internal_ceb2 = 1'b1;
  end
  if(breg == 1)
  begin: ceb_1
        assign internal_ceb1 = 1'b1;
        assign internal_ceb2 = (ce & internal_en) & (c_use_ceb1 ? ceb1 : 1'b1);
  end

  if(breg == 2)
  begin: ceb_2
       assign internal_ceb1 = (ce & internal_en) & (c_use_ceb1 ? ceb1 : 1'b1);
       assign internal_ceb2 = (ce & internal_en) & (c_use_ceb2 ? ceb2 : 1'b1);
  end

  if (use_op == 0)
  begin:opmode_0
          assign internal_opmode = opmode;
          assign internal_alumode = alumode;
          assign internal_carryin = carryin;
          assign internal_carryin_sel = carryinsel;
          if(use_dport == "FALSE")
          begin:dport_0
                assign internal_inmode = 0;
          end
          if(use_dport == "TRUE")
          begin:dport_1
                assign internal_inmode = inmode;
          end
  end

  if(use_op == 1)
  begin:opmode_1
        assign internal_opmode = op [6:0];
        assign internal_alumode = op[10:7];
        assign internal_carryin = op[11];
        assign internal_carryin_sel = op[14:12];
        assign internal_inmode = op[19:15];
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
  if (carryout_width == 1)
  begin: carryoutwidth1
     assign carryout = internal_carryout[3];
  end
  if (carryout_width == 2)
  begin: carryoutwidth2
     assign carryout[1] = internal_carryout[3];
     assign carryout[0] = internal_carryout[1];
  end
  if (carryout_width == 4)
  begin: carryoutwidth4
     assign carryout = internal_carryout;
  end
endgenerate
   DSP48E1 dsp48_inst(.ACOUT(acout),
                    .BCOUT(bcout),
                    .CARRYCASCOUT(carrycascout),
                    .CARRYOUT(internal_carryout),
                    .MULTSIGNOUT(multsignout),
                    .OVERFLOW(overflow),
                    .P(p),
                    .PATTERNBDETECT(patternbdetect),
                    .PATTERNDETECT(patterndetect),
                    .PCOUT(pcout),
                    .UNDERFLOW(underflow),
                    .A(internal_a),
                    .ACIN(internal_acin),
                    .ALUMODE(internal_alumode),
                    .B(internal_b),
                    .BCIN(internal_bcin),
                    .C(internal_c),
                    .CARRYCASCIN(carrycascin),
                    .CARRYIN(internal_carryin),
                    .CARRYINSEL(internal_carryin_sel),
                    .CEA1(internal_cea1),
                    .CEA2(internal_cea2),
                        .CEAD(internal_cead),
                    .CEALUMODE(internal_cealumode),
                    .CEB1(internal_ceb1),
                    .CEB2(internal_ceb2),
                    .CEC(internal_cec),
                    .CECARRYIN(internal_cecarryin),
                    .CECTRL(internal_cectrl),
                        .CED(internal_ced),
                        .CEINMODE(internal_ceinmode),
                    .CEM(internal_cem),
                    .CEP(internal_cep),
                    .CLK(clk),
                        .D(internal_d),
                        .INMODE(internal_inmode),
                    .MULTSIGNIN(internal_multsignin),
                    .OPMODE(internal_opmode),
                    .PCIN(internal_pcin),
                    .RSTA(internal_rsta),
                    .RSTALLCARRYIN(internal_rstcarryin),
                    .RSTALUMODE(internal_rstalumode),
                    .RSTB(internal_rstb),
                    .RSTC(internal_rstc),
                    .RSTCTRL(internal_rstctrl),
                        .RSTD(internal_rstd),
                        .RSTINMODE(internal_rstinmode),
                    .RSTM(internal_rstm),
                    .RSTP(internal_rstp)
                        );
defparam
dsp48_inst.ACASCREG = acascreg,
dsp48_inst.ALUMODEREG = alumodereg,
dsp48_inst.AREG = areg,
dsp48_inst.ADREG = adreg,
dsp48_inst.AUTORESET_PATDET = autoreset_pattern_detect,
dsp48_inst.B_INPUT = b_input,
dsp48_inst.BREG = breg,
dsp48_inst.CARRYINREG = carryinreg,
dsp48_inst.CARRYINSELREG = carryinselreg,
dsp48_inst.CREG = creg,
dsp48_inst.DREG = dreg,
dsp48_inst.INMODEREG = inmodereg,
dsp48_inst.MREG = mreg,
dsp48_inst.OPMODEREG = opmodereg,
dsp48_inst.PREG = preg,
dsp48_inst.A_INPUT = a_input,
dsp48_inst.BCASCREG = bcascreg,
dsp48_inst.B_INPUT = b_input,
dsp48_inst.MASK = mask,
dsp48_inst.PATTERN = pattern,
dsp48_inst.SEL_MASK = sel_mask,
dsp48_inst.SEL_PATTERN = sel_pattern,
dsp48_inst.USE_DPORT = use_dport,
dsp48_inst.USE_MULT= use_mult,
dsp48_inst.USE_PATTERN_DETECT = use_pattern_detect,
dsp48_inst.USE_SIMD = use_simd;
endmodule
