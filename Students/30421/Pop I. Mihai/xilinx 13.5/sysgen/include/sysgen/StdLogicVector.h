/*
 *  StdLogicVector.h
 *
 *  Copyright (c) 2003-2011, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description:  System Generator "Standard Logic Vector" Data Type
 */

#ifndef SYSGEN_STD_LOGIC_VECTOR_H
#define SYSGEN_STD_LOGIC_VECTOR_H

#define Sysgen_StdLogicVector_maxWidth    (1<<12)

#define Sysgen_StdLogicVector_widthMask   (Sysgen_StdLogicVector_maxWidth - 1)
#define Sysgen_StdLogicVector_signedMask  (Sysgen_StdLogicVector_maxWidth)
#define Sysgen_StdLogicVector_XMask       (Sysgen_StdLogicVector_maxWidth << 1)
#define Sysgen_StdLogicVector_FPMask      (Sysgen_StdLogicVector_maxWidth << 2)

#include "sysgen/sg_config.h"
#include "sysgen/IntType.h"

#include <string>

namespace Sysgen {
    class XFix;
    class XFloat;
    class StdLogicVector;
    struct SysgenType;

    SG_API void convert_stdlogic_vector_to_xfloat(const StdLogicVector& slv, XFloat& xflt);
    SG_API void convert_xfloat_to_stdlogic_vector(XFloat& xflt, StdLogicVector& slv);
}

namespace Sysgen
{

// This function expects nbits to be greater than zero.
inline unsigned num_words(unsigned nbits, unsigned wordsize) {
    return ((nbits - 1) / wordsize) + 1;
}

}

namespace Sysgen
{
    /// \ingroup includes
    /// \ingroup published
    /// \ingroup public_utility
    /// \brief Vector of bits with conversion functionality
    class SG_API StdLogicVector
    {
        friend class StdLogicVectorVector;
    public:
        /// Default constructor
        StdLogicVector();

        /// Construct from an XFix
        StdLogicVector(const XFix* x);

        /// Construct from a double
        StdLogicVector(double x, int nbits=32, int binpt=0, bool isSigned=true, bool isFloatingPoint=false);

        /// Directly construct from uint32 array
        StdLogicVector(uint32 *p);

        /// Construct from a double
        StdLogicVector(double x, Sysgen::SysgenType type);

        /// Copy Constructor
        StdLogicVector(const StdLogicVector &slv);

        /// Destructor
        ~StdLogicVector();

        /// Copy assignment
        StdLogicVector& operator=(const StdLogicVector& slv);

        /// Assignment from an XFix
        StdLogicVector& operator=(const XFix* x);

        // Comparison with a StdLogicVector (true if the same; false otherwise)
        bool equals(const StdLogicVector& slv);

        /// Assignment from a double
        void assignFromDouble(double x, int nbits=32, int binpt=0, bool isSigned=true, bool isFloatingPoint=false);

        /// Assignment from a string of bits, e.g., "10001010"
        void assignFromString(const char* s);

        /// Assignment to an XFix
        void assignToXFix(XFix *x) const;

        /// Predicate to test sign
        /// \return true if signed
        bool isSigned() const { return ( (*v & Sysgen_StdLogicVector_signedMask) != 0 ) ; }

        /// Predicate to test the state of the XMask
        bool isX() const { return ( (*v & Sysgen_StdLogicVector_XMask) != 0 ) ; }

        /// Predicate to test the state of the FPMask
        bool isFloatingPoint() const { return ( (*v & Sysgen_StdLogicVector_FPMask) != 0 ) ; }

        /// Get the ith bit
        bool getBit(int i) const;

        /// Get the number of bits
        unsigned getWidth() const { return *v & Sysgen_StdLogicVector_widthMask; }

        /// Get the number of bits
        static unsigned getWidth(uint32 *rawdata) { return *rawdata & Sysgen_StdLogicVector_widthMask; }

        /// Get the number of 32 bit words (including the heading flag info)
        unsigned getNumUInt32Words() const;

        /// Get the number of 32 bit words (including the heading flag info)
        static unsigned getNumUInt32Words(uint32* rawdata) {
            return num_words(getWidth(rawdata), 32) + 1;
        }

        /// Get a raw pointer to bit vector
        uint32* getRawDataPtr() const { return v; }

        /// Convert to a double
        double toDouble(int binpt=0) const;

        /// Convert to a string
        std::string toRawString() const;

    private:
        enum memory_reuse_tag_dispatch {memory_reuse_tag};
        StdLogicVector(uint32 *p, memory_reuse_tag_dispatch);
        void resize(unsigned nwords);

        uint32 *v;
        bool reusing_memory;
    };
}


namespace Sysgen
{
    class SharedMemoryImpl; // forward declaration

    /// \ingroup includes
    /// \ingroup published
    /// \ingroup public_utility
    /// \brief Vector of StdLogicVector's
    class SG_API StdLogicVectorVector
    {
        friend class SharedMemoryImpl;
    public:
        // Constructor: create nwords
        StdLogicVectorVector(unsigned nwords, const StdLogicVector& proto);

        /// Copy Constructor
        StdLogicVectorVector(const StdLogicVectorVector &q);

        /// Destructor
        ~StdLogicVectorVector();

        /// Assignment operator
        StdLogicVectorVector& operator=(const StdLogicVectorVector& q);

        /// Get a StdLogicVector reference (const version)
        /// \param idx index into vector
        /// \return const reference to StdLogicVector object
        const StdLogicVector& operator[](unsigned idx) const;

        /// Get a StdLogicVector reference
        /// \param idx index into vector
        /// \return reference to StdLogicVector object
        StdLogicVector& operator[](unsigned idx);

        /// Get the number of words in the vector
        unsigned length() const { return nwords; };

        /// Get a raw pointer to bit vector (const version)
        const uint32* getRawDataPtr() const {return v;};

        /// Get a raw pointer to bit vector
        uint32* getRawDataPtr() {return v;};

    private:
        enum memory_reuse_tag_dispatch {memory_reuse_tag};

        StdLogicVectorVector(uint32 *p,
                             memory_reuse_tag_dispatch,
                             unsigned nwords);

        StdLogicVectorVector(StdLogicVectorVector&,
                             memory_reuse_tag_dispatch,
                             int start_addr=0, int len=-1);

        void allocate_ep_buffer();
        void free_ep_buffer();

        StdLogicVector** ep;
        uint32 *v;
        unsigned nwords;
        unsigned nUint32wordsPerSLV;
        bool reusing_memory;
    };
}
#endif // SYSGEN_STD_LOGIC_VECTOR_H
