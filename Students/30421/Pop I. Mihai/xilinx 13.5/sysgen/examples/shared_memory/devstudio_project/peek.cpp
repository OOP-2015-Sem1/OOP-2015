//-------------------------------------------------------------------------
// System Generator source file.
//
// Copyright(C) 2004 by Xilinx, Inc.  All rights reserved.  This
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
// Any modifications that are made to the source code are done at the
// user's sole risk and will be unsupported.
//
// This copyright and support notice must be retained as part of this
// text at all times.  (c) Copyright 2004 Xilinx, Inc.  All rights
// reserved.
//-------------------------------------------------------------------------


#ifdef _DEBUG
#error The preprocessor directive "_DEBUG" must not be defined when linking against sysgen.dll. _DEBUG selects C-runtime heap management routines that are incompatible with the ones used in sysgen.dll. Make sure that _DEBUG is not explicitly set under C++/Preprocessor and that "Multi-threaded DLL (/MD)" is selected under C++/Code Generation/Runtime Library.
#endif

#include <cstdlib>
#include <string>
#include <iostream>
#include <cmath>

#define SG_STATIC
#pragma warning(disable:4251)
#include "sysgen/SharedMemory.h"

using std::cout;
using std::cerr;
using std::cin;
using std::endl;
using std::string;

void usage(string program_name)
{
    cout << "usage: " << endl;
    cout << "       " << program_name << "  mem_name/address" << endl;
    cout << "       " << program_name << "  mem_name/addr_start:addr_end" << endl;
    cout << " e.g.  " << program_name << "  bar/0:3" << endl;
}


int main(int argc, char **argv) 
{
    if (argc!=2) {
        usage(argv[0]);
        return 0;
    }
    
    int i, i0, i1;

    string addr_arg(argv[1]);

    size_t slash_loc = addr_arg.find_last_of('/');
    string mem_name = addr_arg.substr(0,slash_loc);
    addr_arg = addr_arg.substr(slash_loc+1);
    size_t colon_loc = addr_arg.find_first_of(':');
    string i0_string = addr_arg.substr(0,colon_loc); 
    string i1_string = addr_arg.substr(colon_loc+1); 
    
    int nscan;
    
    nscan = sscanf(i0_string.c_str(),"%d",&i0);
    if (nscan != 1) {
        usage(argv[0]);
        return 0;
    }
    if (i0<0) i0 = 0;

    nscan = sscanf(i1_string.c_str(),"%d",&i1);
    if (nscan != 1) {
        usage(argv[0]);
        return 0;
    }
    if (i1<0) i1 = 0;
    
    if (i0<=i1) {

        try {
            Sysgen::SharedMemory M(mem_name);
//          Equivalent to:
//           Sysgen::SharedMemory M(mem_name,
//                                  0,
//                                  Sysgen::SharedMemory::INHERIT,
//                                  Sysgen::SharedMemory::INHERIT,
//                                  0.0
//                                 );
            
            char format[64];

            unsigned depth = M.getNWords();

            int addr_width = 
                (depth==0) ? 1 : 
                ((int) floor(log10((double)(depth))) + 1);

            sprintf(format,"| %%s[%%0%dd] = 0b'%%s |\n", addr_width);
            
            Sysgen::StdLogicVector slv(0.0, M.getWordSize(), 0, false);

            char temp[8192];
            sprintf(temp,format, mem_name.c_str(), i1, 
                    slv.toRawString().c_str());

            size_t line_length = strlen(temp) - 1;

            std::string divider(line_length-2, '-');

            cout << " " << divider << endl;

            for (i=i0;i<=i1;i++) {
                M.read(i,slv);

                fprintf(stdout,format, mem_name.c_str(), i,
                        slv.toRawString().c_str());
            }

            cout << " " << divider << endl;

            Sysgen::StdLogicVectorVector slvv(i1-i0+1, slv);

            M.readArray(i0, i1-i0+1, slvv);

            for (i=i0;i<=i1;i++) {
                fprintf(stdout,format, mem_name.c_str(), i,
                        slvv[i-i0].toRawString().c_str());
            }

            cout << " " << divider << endl;
        }
        catch (Sysgen::Error &e) {
            cerr << "Error: " << e.what() << endl;
            return 1;
        }
        catch (std::exception &e) {
            cerr << "Error: " << e.what() << endl;
            return 1;
        }
    }
    
    return 0;
}
