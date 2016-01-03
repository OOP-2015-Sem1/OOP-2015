/*
 * $Header: /devl/xcs/repo/env/Jobs/sysgen/src/cxx/include/sysgen/XNum-setParam-var.h,v 1.1 2005/08/28 23:18:19 skelly Exp $
 *
 * Module:  XNum-setParam-var.h
 *
 *  Description:
 */

#define z_FUN  setParam
#define z_FTYP void

#define z_TYP1 xlArithType
#define z_TYP2 xlQuantization
#define z_TYP3 xlOverflow
#define z_VAR1 nt
#define z_VAR2 qt
#define z_VAR3 ov
#define z_xxx1 xlDontChangeArithType
#define z_xxx2 xlDontChangeQuantization
#define z_xxx3 xlDontChangeOverflow

// internal def'ns (don't edit):

#define z_DEC1 z_TYP1 z_VAR1
#define z_DEC2 z_TYP2 z_VAR2
#define z_DEC3 z_TYP3 z_VAR3

#ifndef argCHEAT1
#define _z_comma_
#define argCHEAT1
#else
#define _z_comma_ ,
#define z_PASSED_ARGS
#define z_CREATE_EMPTY
#endif

#ifndef argCHEAT2
#define argCHEAT2
#else
#endif

// 3Param Versions:
 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC1, z_DEC3, z_DEC2 )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC2, z_DEC1, z_DEC3 )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC2, z_DEC3, z_DEC1  )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC3, z_DEC1, z_DEC2 )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC3, z_DEC2, z_DEC1  )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_VAR3); }

// 2Param Versions:

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC1, z_DEC2 )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_xxx3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC2, z_DEC1  )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_VAR2, z_xxx3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC1, z_DEC3 )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_xxx2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC3, z_DEC1  )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_xxx2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC2, z_DEC3 )
    { z_FUN(argCHEAT2 _z_comma_ z_xxx1, z_VAR2, z_VAR3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC3, z_DEC2  )
    { z_FUN(argCHEAT2 _z_comma_ z_xxx1, z_VAR2, z_VAR3); }


// 1Param Versions:

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC1 )
    { z_FUN(argCHEAT2 _z_comma_ z_VAR1, z_xxx2, z_xxx3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC2 )
    { z_FUN(argCHEAT2 _z_comma_ z_xxx1, z_VAR2, z_xxx3); }

 inline z_FTYP z_FUN(argCHEAT1 _z_comma_ z_DEC3 )
    { z_FUN(argCHEAT2 _z_comma_ z_xxx1, z_xxx2, z_VAR3); }


#ifdef z_CREATE_EMPTY
 inline z_FTYP z_FUN(argCHEAT1)
    { z_FUN(argCHEAT2 _z_comma_ z_xxx1, z_xxx2, z_xxx3); }
#endif


// CLEANUP:

#ifndef z_PASSED_ARGS
#undef argCHEAT1
#undef argCHEAT2
#endif

#undef z_PASSED_ARGS
#undef z_CREATE_EMPTY
#undef _z_comma_

#undef z_FUN
#undef z_FTYP

#undef z_TYP1
#undef z_TYP2
#undef z_TYP3
#undef z_VAR1
#undef z_VAR2
#undef z_VAR3
#undef z_xxx1
#undef z_xxx2
#undef z_xxx3

#undef z_DEC1
#undef z_DEC2
#undef z_DEC3
