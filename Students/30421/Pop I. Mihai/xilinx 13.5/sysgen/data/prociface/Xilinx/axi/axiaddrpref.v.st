//-----------------------------------------------------------------
// System Generator version 13.1.00 VERILOG source file.
//
// Copyright(C) 2010 by Xilinx, Inc.  All rights reserved.  This
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
// text at all times.  
// (c) Copyright 1995-2010 Xilinx, Inc.  All rights reserved.
//-----------------------------------------------------------------

module plbaddrpref(sg_s_axi_arid, s_axi_arid, sg_s_axi_awid, s_axi_awid, sg_s_axi_rid, s_axi_rid, sg_s_axi_bid, s_axi_bid);
    parameter C_BASEADDR = 32'h80000000;
    parameter C_HIGHADDR = 32'h80000FFF;
    parameter C_S_AXI_ID_WIDTH = 1;
    parameter C_S_AXI_NATIVE_ID_WIDTH = 8;

    // arid
    input  [C_S_AXI_ID_WIDTH-1:0]        sg_s_axi_arid];
    output [C_S_AXI_NATIVE_ID_WIDTH-1:0] s_axi_arid];
    // awid
    input  [C_S_AXI_ID_WIDTH-1:0]        sg_s_axi_awid];
    output [C_S_AXI_NATIVE_ID_WIDTH-1:0] s_axi_awid];
    // rid
    output [C_S_AXI_ID_WIDTH-1:0]        sg_s_axi_rid];
    input  [C_S_AXI_NATIVE_ID_WIDTH-1:0] s_axi_rid];
    // bid
    output [C_S_AXI_ID_WIDTH-1:0]        sg_s_axi_bid];
    input  [C_S_AXI_NATIVE_ID_WIDTH-1:0] s_axi_bid];

    assign s_axi_arid[C_S_AXI_ID_WIDTH-1:0] = sg_s_axi_arid;
    assign s_axi_awid[C_S_AXI_ID_WIDTH-1:0] = sg_s_axi_awid;
    
    assign sg_s_axi_rid = s_axi_rid[C_S_AXI_ID_WIDTH-1:0];
    assign sg_s_axi_bid = s_axi_bid[C_S_AXI_ID_WIDTH-1:0];

endmodule    


