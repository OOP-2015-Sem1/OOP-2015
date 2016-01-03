/*
 *  SysgenDebug.h
 *
 *  Copyright (c) 2003, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description:  Provides macro definitions for debug clause exclusion.
 *                DLLs sould define an integer variable named DLLDebugLevel.
 *                DEBUG_LEVEL_MIN, DEBUG_LEVEL_MAX, are compile time swithches
 *                that can be used for optimization (defining DEBUG_LEVEL_MAX=0
 *                will cause all if(DEBUG*) clauses to be excluded from the
 *                compilation.
 *                
 *                Usage of the macro definitions should be as:
 *                
 *                if (DEBUG_HVY) {
 *                   checkStuff();
 *                }
 *                
 *                To state the obvious, the code in debug clauses should not
 *                have side affects, e.g.:
 *                
 *                if (DEBUG_STD) {
 *                   doStuff();   // shouldn't do this
 *                }
 *                
 *                Debug levels are:
 *                0 - disabled, 1 - stdandard debug, 2 - heavy debug, 
 *                3 - maximum debug (not compiled in producion code)
 *                
 *                The following macros are defined
 *                DEBUG_STD, DEBUG_HVY, DEBUG_MAX
 *                allowing code to be made active if the debug level
 *                meets or exceeds level 1, 2, or 3, respectively.
 *                
 *                ------------------------------
 *                
 *                Also provides a replacement for printf(), Sysgen::Printf(),
 *                and defines printf as a macro (to become Sysgen::Printf).
 *                Sysgen::Printf() allows all output to be redirected or turned
 *                off via a call to Sysgen::directOutput().
 */


#ifndef SYSGEN_DEBUG_H
#define SYSGEN_DEBUG_H

#ifndef DEBUG_LEVEL_MIN
#define DEBUG_LEVEL_MIN 0
#endif

#ifndef DEBUG_LEVEL_MAX
#ifdef _DEBUG
#define DEBUG_LEVEL_MAX 3
#else
#define DEBUG_LEVEL_MAX 2
#endif
#endif

#ifdef DEBUG_LEVEL
#undef DEBUG_LEVEL
#endif
#define DEBUG_LEVEL(x) \
 ((DEBUG_LEVEL_MIN>=(x)) || ((DEBUG_LEVEL_MAX>=(x)) && (DLLDebugLevel>=(x))))

#define DEBUG_ON (DEBUG_LEVEL(1))
#define DEBUG_OFF (~DEBUG_ON)

#define DEBUG_STD (DEBUG_LEVEL(1))
#define DEBUG_HVY (DEBUG_LEVEL(2))
#define DEBUG_MAX (DEBUG_LEVEL(3))

#include "sysgen/sg_config.h"

#include <mex.h>
#undef printf 

#define printf Sysgen::Printf

#if defined(OS_WINDOWS)
#ifndef WIN32_LEAN_AND_MEAN
#define WIN32_LEAN_AND_MEAN  // Exclude rarely-used stuff from Windows headers
#include <windows.h>
#undef WIN32_LEAN_AND_MEAN 
#endif
#endif

/// \ingroup public_utility
namespace Sysgen
{
    SG_API int  getDebugLevel(const char* lib);
#if defined(OS_WINDOWS)
    SG_API int  getDebugLevel(const HINSTANCE hlib);
#endif
    // of the two above methods, the one taking a char* is faster

    SG_API void setDebugLevel(const char* lib, int level);
    SG_API void setDebugLevel(int level);

    // use directOutput("stdout") to redirect output to console
    // use directOutput(fileName) to redirect output to a log file
    //                            (will be opened for appending)
    // use directOutput("/dev/null") to turn off output
    SG_API void directOutput(const char *dest);

    SG_API int Printf(const char *fmt, ...);
}

#endif // SYSGEN_DEBUG_H
