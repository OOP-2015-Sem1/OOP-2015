/*
 * sg_config.h
 *
 * $Header: /devl/xcs/repo/env/Jobs/sysgen/src/cxx/include/sysgen/sg_config.h,v 1.1 2004/11/22 00:16:56 rosty Exp $
 *
 * Handle global build configuration options common to all C++ source files.
 *
 */

#ifndef included_sg_config_h
#define included_sg_config_h

#if defined(OS_WINDOWS)
#   if defined(SG_STATIC)
#       define SG_API
#       define SG_EXP_IMP
#   elif defined(SG_EXPORT)
#       define SG_API       __declspec(dllexport)
#       define SG_EXP_IMP
#   else
#       define SG_API       __declspec(dllimport)
#       define SG_EXP_IMP   extern
#   endif
#   define SG_DLL_IMPORT    __declspec(dllimport)
#   define SG_DLL_EXPORT    __declspec(dllexport)
#elif defined(GCC_HASCLASSVISIBILITY)
#   if defined(SG_STATIC)
#       define SG_API
#       define SG_EXP_IMP
#   elif defined(SG_EXPORT)
#       define SG_API       __attribute__ ((visibility("default")))
#       define SG_EXP_IMP
#   else
#       define SG_API
#       define SG_EXP_IMP   extern
#   endif
#   define SG_DLL_IMPORT
#   define SG_DLL_EXPORT    __attribute__ ((visibility("default")))
#else
#   if defined(SG_STATIC)
#       define SG_API
#       define SG_EXP_IMP
#   elif defined(SG_EXPORT)
#       define SG_API
#       define SG_EXP_IMP
#   else
#       define SG_API
#       define SG_EXP_IMP   extern
#   endif
#   define SG_DLL_IMPORT
#   define SG_DLL_EXPORT
#endif

#endif // included_sg_config_h
