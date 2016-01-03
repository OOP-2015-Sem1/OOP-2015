/*
 *  SysgenConstant.h
 *
 *  Copyright (c) 2003-2008, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  Sysgen namespace symbolic constants
 *
 */

#ifndef _SYSGENCONSTANTS_H_
#define _SYSGENCONSTANTS_H_

#include <string>

namespace Sysgen
{

/// Port direction enum
enum Direction { DIRECTION_UNDEFINED, INPUT, OUTPUT, N_DIRECTION};

/// HDL language-type enumeration.
enum Language { UNDEFINED_LANGUAGE, VERILOG, VHDL, NGC, EDIF, N_LANGUAGE};

/// Netlist runner implementing the standard flow for the net list post processing
const static std::string STANDARD_NETLIST_RUNNER = "java:com.xilinx.sysgen.netlister.DefaultWrapupNetlister";

/// Netlister runner for doing the black box net listing post processing. This points to a perl script
const static std::string BLACKBOX_NETLIST_RUNNER = "perl:SgPass2Netlist::finishBlackBoxNetlisting";

/// Netlister runner for doing the isim net listing  post processing. This points to a perl script
const static std::string ISIM_NETLIST_RUNNER = "perl:SgPass2Netlist::finishISIMNetlisting";

/// Maximum bit width of fixed point data type
const int MAX_DATA_BIT_WIDTH = 4000;
}

#endif //_SYSGENCONSTANTS_H_
