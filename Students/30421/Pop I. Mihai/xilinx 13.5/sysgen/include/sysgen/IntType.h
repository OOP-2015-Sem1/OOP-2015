#ifndef _SYSGEN_INT_TYPE_H_
#define _SYSGEN_INT_TYPE_H_

#if defined(OS_WINDOWS)
namespace Sysgen
{
    /// Unsigned 8-bit integer.
    typedef unsigned __int8    uint8;
    /// Signed 8-bit integer.
    typedef signed __int8      int8;
    /// Unsigned 16-bit integer.
    typedef unsigned __int16   uint16;
    /// Signed 16-bit integer.
    typedef signed __int16     int16;
    /// Unsigned 32-bit integer.
    typedef unsigned __int32   uint32;
    /// Signed 32-bit integer.
    typedef signed __int32     int32;
    /// Unsigned 64-bit integer.
    typedef unsigned __int64   uint64;
    /// Signed 64-bit integer.
    typedef signed __int64     int64;
}
#else
#include <stdint.h>
namespace Sysgen
{
    /// Unsigned 8-bit integer.
    typedef ::uint8_t          uint8;
    /// Signed 8-bit integer.
    typedef ::int8_t           int8;
    /// Unsigned 16-bit integer.
    typedef ::uint16_t         uint16;
    /// Signed 16-bit integer.
    typedef ::int16_t          int16;
    /// Unsigned 32-bit integer.
    typedef ::uint32_t         uint32;
    /// Signed 32-bit integer.
    typedef ::int32_t          int32;
    /// Unsigned 64-bit integer.
    typedef ::uint64_t         uint64;
    /// Signed 64-bit integer.
    typedef ::int64_t          int64;
}
#endif

#endif // _SYSGEN_INT_TYPE_H_
