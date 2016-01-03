
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
// synopsys translate_off
`timescale 1 ns / 10 ps
module xltbinout (clk,io);
  parameter io_width = 3;
  parameter inputFile = "inout.dat";
  parameter periodMultiplier = 1;
  parameter reqHoldTime = 1.5;

  input clk;
  inout[io_width - 1:0] io;
  reg [io_width - 1:0] tmp_o;
  integer period_count;
  integer inputFilePtr, errcheck;
  integer start_up;
  wire [io_width - 1:0] throw;
  assign #reqHoldTime io = tmp_o;
  assign throw = (tmp_o == 'bZ)? 'bZ: io;
 initial
  begin
   start_up = 1;
   period_count = 0;
   if (start_up == 1)
      begin
        inputFilePtr = $fopen(inputFile, "r");
        errcheck = $fscanf(inputFilePtr,"%b",tmp_o);
        start_up = 0;
      end
  end
 always @(posedge clk)
   begin
     period_count = period_count + 1;
     if (period_count == periodMultiplier)
     begin
        period_count = 0;
        if (errcheck == 1)
            errcheck = $fscanf(inputFilePtr,"%b",tmp_o);
    end
   end
endmodule
// synopsys translate_on
