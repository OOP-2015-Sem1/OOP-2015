
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
ROM_form.v
Joel Coburn (Xilinx Inc.) July 2003
This is the Verilog template file for the KCPSM3 assembler.
It is used to configure a Spartan-3, Virtex-II or Virtex-IIPRO block RAM to act as
a single port program ROM.
This Verilog file is not valid as input directly into a synthesis or simulation tool.
The assembler will read this template and insert the data required to complete the
definition of program ROM and write it out to a new '.v' file associated with the
name of the original '.psm' file being assembled.
This template can be modified to define alternative memory definitions such as dual port.
However, you are responsible for ensuring the template is correct as the assembler does
not perform any checking of the Verilog.
The assembler identifies all text enclosed by {} characters, and replaces these
character strings. All templates should include these {} character strings for
the assembler to work correctly.
****************************************************************************************

This template defines a block RAM configured in 1024 x 18-bit single port mode and
conneceted to act as a single port ROM.
****************************************************************************************
The next line is used to determine where the template actually starts and must exist.
{begin template}
module {name} (address, instruction, clk);
input [9:0] address;
input clk;
output [17:0] instruction;
RAMB16_S18 ram_1024_x_18(
                         .DI    (16'b0),
                         .DIP (2'b0),
                         .EN    (1'b1),
                         .WE    (1'b0),
                         .SSR   (1'b0),
                         .CLK   (clk),
                         .ADDR(address),
                         .DO    (instruction[15:0]),
                         .DOP   (instruction[17:16])
                         );
defparam ram_1024_x_18.INIT_00  = 256'h{INIT_00};
defparam ram_1024_x_18.INIT_01  = 256'h{INIT_01};
defparam ram_1024_x_18.INIT_02  = 256'h{INIT_02};
defparam ram_1024_x_18.INIT_03  = 256'h{INIT_03};
defparam ram_1024_x_18.INIT_04  = 256'h{INIT_04};
defparam ram_1024_x_18.INIT_05  = 256'h{INIT_05};
defparam ram_1024_x_18.INIT_06  = 256'h{INIT_06};
defparam ram_1024_x_18.INIT_07  = 256'h{INIT_07};
defparam ram_1024_x_18.INIT_08  = 256'h{INIT_08};
defparam ram_1024_x_18.INIT_09  = 256'h{INIT_09};
defparam ram_1024_x_18.INIT_0A  = 256'h{INIT_0A};
defparam ram_1024_x_18.INIT_0B  = 256'h{INIT_0B};
defparam ram_1024_x_18.INIT_0C  = 256'h{INIT_0C};
defparam ram_1024_x_18.INIT_0D  = 256'h{INIT_0D};
defparam ram_1024_x_18.INIT_0E  = 256'h{INIT_0E};
defparam ram_1024_x_18.INIT_0F  = 256'h{INIT_0F};
defparam ram_1024_x_18.INIT_10  = 256'h{INIT_10};
defparam ram_1024_x_18.INIT_11  = 256'h{INIT_11};
defparam ram_1024_x_18.INIT_12  = 256'h{INIT_12};
defparam ram_1024_x_18.INIT_13  = 256'h{INIT_13};
defparam ram_1024_x_18.INIT_14  = 256'h{INIT_14};
defparam ram_1024_x_18.INIT_15  = 256'h{INIT_15};
defparam ram_1024_x_18.INIT_16  = 256'h{INIT_16};
defparam ram_1024_x_18.INIT_17  = 256'h{INIT_17};
defparam ram_1024_x_18.INIT_18  = 256'h{INIT_18};
defparam ram_1024_x_18.INIT_19  = 256'h{INIT_19};
defparam ram_1024_x_18.INIT_1A  = 256'h{INIT_1A};
defparam ram_1024_x_18.INIT_1B  = 256'h{INIT_1B};
defparam ram_1024_x_18.INIT_1C  = 256'h{INIT_1C};
defparam ram_1024_x_18.INIT_1D  = 256'h{INIT_1D};
defparam ram_1024_x_18.INIT_1E  = 256'h{INIT_1E};
defparam ram_1024_x_18.INIT_1F  = 256'h{INIT_1F};
defparam ram_1024_x_18.INIT_20  = 256'h{INIT_20};
defparam ram_1024_x_18.INIT_21  = 256'h{INIT_21};
defparam ram_1024_x_18.INIT_22  = 256'h{INIT_22};
defparam ram_1024_x_18.INIT_23  = 256'h{INIT_23};
defparam ram_1024_x_18.INIT_24  = 256'h{INIT_24};
defparam ram_1024_x_18.INIT_25  = 256'h{INIT_25};
defparam ram_1024_x_18.INIT_26  = 256'h{INIT_26};
defparam ram_1024_x_18.INIT_27  = 256'h{INIT_27};
defparam ram_1024_x_18.INIT_28  = 256'h{INIT_28};
defparam ram_1024_x_18.INIT_29  = 256'h{INIT_29};
defparam ram_1024_x_18.INIT_2A  = 256'h{INIT_2A};
defparam ram_1024_x_18.INIT_2B  = 256'h{INIT_2B};
defparam ram_1024_x_18.INIT_2C  = 256'h{INIT_2C};
defparam ram_1024_x_18.INIT_2D  = 256'h{INIT_2D};
defparam ram_1024_x_18.INIT_2E  = 256'h{INIT_2E};
defparam ram_1024_x_18.INIT_2F  = 256'h{INIT_2F};
defparam ram_1024_x_18.INIT_30  = 256'h{INIT_30};
defparam ram_1024_x_18.INIT_31  = 256'h{INIT_31};
defparam ram_1024_x_18.INIT_32  = 256'h{INIT_32};
defparam ram_1024_x_18.INIT_33  = 256'h{INIT_33};
defparam ram_1024_x_18.INIT_34  = 256'h{INIT_34};
defparam ram_1024_x_18.INIT_35  = 256'h{INIT_35};
defparam ram_1024_x_18.INIT_36  = 256'h{INIT_36};
defparam ram_1024_x_18.INIT_37  = 256'h{INIT_37};
defparam ram_1024_x_18.INIT_38  = 256'h{INIT_38};
defparam ram_1024_x_18.INIT_39  = 256'h{INIT_39};
defparam ram_1024_x_18.INIT_3A  = 256'h{INIT_3A};
defparam ram_1024_x_18.INIT_3B  = 256'h{INIT_3B};
defparam ram_1024_x_18.INIT_3C  = 256'h{INIT_3C};
defparam ram_1024_x_18.INIT_3D  = 256'h{INIT_3D};
defparam ram_1024_x_18.INIT_3E  = 256'h{INIT_3E};
defparam ram_1024_x_18.INIT_3F  = 256'h{INIT_3F};
defparam ram_1024_x_18.INITP_00 = 256'h{INITP_00};
defparam ram_1024_x_18.INITP_01 = 256'h{INITP_01};
defparam ram_1024_x_18.INITP_02 = 256'h{INITP_02};
defparam ram_1024_x_18.INITP_03 = 256'h{INITP_03};
defparam ram_1024_x_18.INITP_04 = 256'h{INITP_04};
defparam ram_1024_x_18.INITP_05 = 256'h{INITP_05};
defparam ram_1024_x_18.INITP_06 = 256'h{INITP_06};
defparam ram_1024_x_18.INITP_07 = 256'h{INITP_07};
endmodule
