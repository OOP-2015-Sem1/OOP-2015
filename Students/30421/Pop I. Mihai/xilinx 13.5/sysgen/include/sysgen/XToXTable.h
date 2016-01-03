/*
 *  xtoxtable.h
 *
 *  Xilinx Secret
 *  Copyright (c) 2004, Xilinx, Inc. All rights reserved.
 *
 *  Description: Conversion of matlab structures to xtables.
 */

#ifndef SYSGEN_XTOXTABLE_H
#define SYSGEN_XTOXTABLE_H

// Xilinx Inclusions:
#include "sysgen/util/XTable.h"
#include "sysgen/util/XManagedTable.h"

// Boost Inclusions:
#include <boost/shared_ptr.hpp>

// MATLAB Inclusions:
#include<mex.h>


/**Class Name : XToXtable
   This class performs the conversion of Matlab Structures to Global XTables that can be accessed using handles.The matlab structure can contain fields of the following type:
   - Matlab Structure
   - Matlab Cell Arrays
   - Matlab String/Character
   - Matlab Double
   - Matlab Logical
   - Matlab int8/uint8/uint16/int16
   Currently Matlab arrays of doubles/logicals/int8/int16/uint8/uint16 are not supported
*/
namespace Sysgen
{
    class SG_API XToXTable {
    public:
        static void strToXTable(const mxArray* in_char, XTable &);
        static void doubleToXTable(const mxArray* in_double, XTable &);
        static void singleToXTable(const mxArray* in_single, XTable &);
        static void logicalToXTable(const mxArray* in_logical, XTable &, bool localint4logical=true);
        static void intToXTable(const mxArray* in_int, XTable &);
        static void int8ToXTable(const mxArray* in_int, XTable &);
        static void structToXTable(const mxArray* in_struct, XTable &);		
        static void cellToXTable(const mxArray* in_cell, XTable &);
        
        static XTable mxArrayToXTable(mxArray *);

        static boost::shared_ptr<Sysgen::XManagedTable> ToXTableMap(const mxArray* struct_array, double handle);
        static double matlabDataStructureToXTable(const mxArray* data);
        static double fromSerializedXTable(const mxArray* matlabStr);
        static bool clear(double handle);

        // by default, we use int for matlab logical for the purpose of not breaking netlister.
        // the int4logical is set to true by default.
        // so far, only blockprep toggles this switch.
        static bool int4logical;
    };
}
		
#endif
