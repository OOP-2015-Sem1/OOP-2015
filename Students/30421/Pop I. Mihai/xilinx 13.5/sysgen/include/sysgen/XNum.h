/*
 * $Header: /devl/xcs/repo/env/Jobs/sysgen/src/cxx/include/sysgen/XNum.h,v 1.6 2005/11/15 20:49:32 daleh Exp $
 *
 * Module:  XNum.h
 *
 *  Description:  natural number user-defined type
 */
#ifndef XNUM_H
#define XNUM_H

#include "sysgen/sg_config.h"
#include "sysgen/IntType.h"
#include "sysgen/xlTypes.h"

#include <cassert>
#include <cstdlib>
#include <cmath>
#include <string>

#ifdef XNUM_DEBUG
#define xn_size 4
#endif

/** <em>Internal use only.</em>
 *  Alias the underlying word data type.
 */
typedef Sysgen::uint32 data;

#define XNUM_OVERFLOW 1

namespace Sysgen {
    class XNum;
    class XFix;
}

/// \ingroup public_utility
/// \brief XNum related exception class
/**
 * Simple class to declare and define XNum-related exceptions.<P>

 * Objects of this class have public data members for retrieving
 * a char* string and a class-specific value in which the thrower
 * has economically indicated the nature of the exceptional
 * circumstances.
 */
class SG_API XNumException
{
public:
    class IllegalIntArg {
    public:
        const char* arg;
        int val;
        IllegalIntArg() : arg(0), val(0) {}
        IllegalIntArg(const char* a) : arg(a), val(0) {}
        IllegalIntArg(const char* a, int v) : arg(a), val(v) {}
    };

    class IllegalStrArg {
    public:
        const char* arg;
        const char* val;
        IllegalStrArg() : arg(0), val(0) {}
        IllegalStrArg(const char* a) : arg(a), val(0) {}
        IllegalStrArg(const char* a, const char* v) : arg(a), val(v) {}
    };

    class UnimplementedSpec {
    public:
        const char* arg;
        UnimplementedSpec() : arg(0) {}
        UnimplementedSpec(const char* a) : arg(a) {}
    };
    class tooManyBits {
    public:
        const char* arg;
        tooManyBits() : arg(0) {}
    };
    class Overflow {
    public:
        const char* arg;
        int val;
        Overflow() : arg(0), val(0) {}
        Overflow(const char* a) : arg(a), val(0) {}
        Overflow(const char* a, int v) : arg(a), val(v) {}
    };
};
namespace Sysgen {
    /* */
    int XNumcmp(const XNum& p, const XNum& q);

    /* predicate comparison operators */
    SG_API bool operator==(const XNum& p, const XNum& q);
    SG_API bool operator!=(const XNum& p, const XNum& q);
    SG_API bool operator>(const XNum& p,  const XNum& q);
    SG_API bool operator<(const XNum& p,  const XNum& q);
    SG_API bool operator<=(const XNum& p, const XNum& q);
    SG_API bool operator>=(const XNum& p, const XNum& q);

    SG_API XNum operator^(const XNum& p, const XNum& q);
    SG_API XNum operator|(const XNum& p, const XNum& q);
    SG_API XNum operator&(const XNum& p, const XNum& q);
    SG_API XNum operator%(const XNum& p, const XNum& q);
    SG_API XNum operator/(const XNum& p, const XNum& q);
    SG_API XNum operator*(const XNum& p, const XNum& q);
    SG_API XNum operator-(const XNum& p, const XNum& q);
    SG_API XNum operator+(const XNum& p, const XNum& q);
}


namespace Sysgen {
    /// \ingroup public_utility
    /// \brief Xilinx Proprietary Fixed Point Data Type
    /**
     * Multiple precision signed integer arithmetic class.

     * An XNum is characterized by its \e precision, or in
     * number of bits used to represent the number.  Precision is not
     * part of the type definition, so numeric comparison
     * between XNums of differing sizes is meaningful.<P>

     * There are two flavors of binary operators: those that preserve
     * a number's precision: <P>

     * <code>
     * <UL>
     * <LI>XNum operator+=(const XNum&);
     * <LI>XNum operator-=(const XNum&);
     * <LI>XNum operator*=(const XNum&);
     * <LI>XNum operator/=(const XNum&);
     * <LI>XNum& operator%=(const XNum& q);
     * <LI>XNum& operator>>=(int);
     * <LI>XNum& operator<<=(int);
     * <LI>XNum operator-();
     * </UL>
     * </code><P>

     * and those that return full-precision results:<P>

     * <code>
     * <UL>
     * <LI>friend XNum operator+(const XNum&, const XNum&);
     * <LI>friend XNum operator-(const XNum&, const XNum&);
     * <LI>friend XNum operator*(const XNum&, const XNum&);
     * <LI>friend XNum operator/(const XNum&, const XNum&);
     * </UL>
     * </code><P>

     * The assignment operator involves an implicit cast of its
     * argument to the precision of the object being assigned to.
     */
    class SG_API XNum
    {
        friend class Sysgen::XFix;

    public:

        /**
         * int to XNum constructor
         *
         * @param  val     type double representation of value
         * @param  nbits   number of bits precision
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or xf_truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XNum(int val, int nbits,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);
        XNum(int val,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);

        /**
         * Double to XNum constructor
         *
         * @param  nbits   number of bits precision
         * @param  val     type double representation of value
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or xf_truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XNum(double val, int nbits,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);
        XNum(double val,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);

        /**
         * XNum to XNum conversion constructor
         *
         * @param  nbits   number of bits precision
         * @param  val     type XNum representation of value
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or xf_truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XNum(const XNum& val, int nbits,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);

        /**
         * char[] to XFix constructor
         *
         * @param  val     binary string representation of value
         * @param  nbits   number of bits precision
         * @param  ntype   arith type
         */
        XNum(const char* val, int nbits, xlArithType ntype);

        /**
         * Copy constructor
         *
         * @param  val     type XNum representation of value
         */
        XNum(const XNum& val);

        /**
         * XNum default constructor
         */
        XNum();

        /**
         * XNum destructor
         */
#ifdef XNUM_DEBUG
        ~XNum() {}
#else
        ~XNum() { delete[] v; }
#endif  //XNUM_DEBUG


        /**
         * ReParameterize and Resize and this XNum, applying overflow
         * characteristics as required.
         *
         * Conversion process is specified as sequencing operations as:
         *  - Apply changes to quantization and overflow modes
         *  - Change width (number of bits) if increasing
         *  - Apply ArithType change if going to unsigned (may get zero underflow)
         *  - Change width if decreasing (apply overflow as needed)
         *  - Apply ArithType change if going to signed (apply overflow as needed)
         *
         * @param  nb   number of bits
         * @param  nt   xlArithType
         * @param  qt   quantization characteristic
         * @param  ov   overflow characteristic
         */
        void setParam(int nb,
                      xlArithType nt,
                      xlQuantization qt,
                      xlOverflow ov);

        /**
         * Change overflow and rounding and Sign type characteristics
         * of this XNum as requested.
         *
         * Changes to quantization and overflow modes are applied first.
         *
         * @param  nt   xlArithType
         * @param  qt   quantization characteristic
         * @param  ov   overflow characteristic
         */
        void setParam(xlArithType nt,
                      xlQuantization qt,
                      xlOverflow ov);

#include "sysgen/XNum-setParam-var.h"

#define argCHEAT1 int nb
#define argCHEAT2 nb
#include "sysgen/XNum-setParam-var.h"
#undef argCHEAT1
#undef argCHEAT2

        /**
         * ReParameterize and Resize this XNum, applying overflow
         * characteristics as required, to match the XFix supplied
         * as a parameter.
         *
         * @param  q XNum prototype
         */
        inline void setParam(const XNum& q)
            { setParam(q.nbits,q.ntype,q.qc,q.of); }

        /**
         * Resize this XNum, applying overflow as necessary.
         *
         * @param  nb   number of bits
         */
        inline void resize(int nb)
            { setParam(nb); }

        /**
         * Resize this XNum to the sizes of the XNum
         * supplied as a parameter, applying overflow as necessary.
         *
         */
        inline void resize(const XNum& q)
            { resize(q.nbits); }

        //////////////////////////////////////////////////
        // Full-precision arithmetic operators
        //////////////////////////////////////////////////

        /**
         * Assignment operator, with implicit cast.  The operand is
         * cast to this's precision.
         * @param    val XNum value to assign to this
         * @return   reference to this
         */
        XNum& operator=(const XNum& val);

        XNum& operator=(double x);
        XNum& operator=(int x);

        /**
         * Addition binary operator
         * @param    p XNum left operand
         * @param    q XNum right operand
         * @return   full precision result
         */
        friend SG_API XNum operator+(const XNum& p, const XNum& q);

        /**
         * Subtraction binary operator
         * @param    p XNum left operand
         * @param    q XNum right operand
         * @return   full precision result
         */
        friend SG_API XNum operator-(const XNum& p, const XNum& q);

        /**
         * Multiplication binary operator
         * @param    p XNum left operand
         * @param    q XNum right operand
         * @return   full precision result
         */
        friend SG_API XNum operator*(const XNum& p, const XNum& q);

        /**
         * Division binary operator
         * @param    p XNum left operand
         * @param    q XNum right operand
         * @return   full precision result
         */
        friend SG_API XNum operator/(const XNum& p, const XNum& q);

        /**
         * Remainder
         * @param    p XNum left operand
         * @param    q XNum right operand
         * @return   full precision result
         */
        friend SG_API XNum operator%(const XNum& p, const XNum& q);

        /**
         * Additively invert this XNum
         * by taking its two's complement (if xlSigned)
         */
        XNum& negate();

        /**
         * unary minus (additive negation) operator
         * @return  two's complement of this XNum
         */
        XNum operator-() const;


        /**
         * Logically invert this XNum
         * by taking its binary ("unary") complement (if xlSigned
         */
        XNum& negateLogical();


        /**
         * XNum unary complement (logical negation) operator
         * @return   logical complement
         */
        XNum operator~() const;

        ////////////////////////////////////////////////////////////
        // Precision-preserving, side-effecting arithmetic operators
        ////////////////////////////////////////////////////////////

        /**
         * Multiply this by another XNum, preserving this's precision
         * @param    q   XNum multiplier
         * @return   reference to this after the multiplication
         */
        XNum& operator*=(const XNum& q);

        /**
         * Add a XNum to this, preserving this's precision
         * @param    q   XNum addend
         * @return   reference to this after the addition
         */
        XNum& operator+=(const XNum& q);

        /**
         * Subtract a XNum from this, preserving this's precision
         * @param    q   XNum subtrahend
         * @return   reference to this after the subtraction
         */
        XNum& operator-=(const XNum& q);

        /**
         * Divide this by a XNum, preserving this's precision
         * @param    q   XNum divisor
         * @return   reference to this after the division
         */
        XNum& operator/=(const XNum& q);


        /**
         * Replace this by its remainder modulo another XNum
         * @param    q   XNum divisor
         * @return   reference to this after the division
         */
        XNum& operator%=(const XNum& q);

        /**
         * Right shift operator
         * @param   shift number of bits to shift
         * @return  reference to this after the shift
         */
        XNum& operator>>=(int shift);

        /**
         * Left shift operator
         * @param   shift number of bits to shift
         * @return   reference to this after the shift
         */
        XNum& operator<<=(int shift);

        //////////////////////////////////////////////////
        // Bitwise boolean operators
        //////////////////////////////////////////////////

        /**
         * increment one
         */
        XNum& incr();

        /**
         * decrement one
         */
        XNum& decr();

        /**
         * @return 1 if there is a need to increment for rounding, otherwise 0.
         */
        int incr4round(const XNum& from, int old_binpt, int new_binpt);
        int incr4round_banker(const XNum& from, int old_binpt, int new_binpt);

        /**
         * Binary logical and operator
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   bitwise and (with p's precision and characteristic)
         */
        friend SG_API XNum operator&(const XNum& p, const XNum& q);

        /**
         * Binary logical or operator
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   bitwise or (with p's precision and characteristic)
         */
        friend SG_API XNum operator|(const XNum& p, const XNum& q);

        /**
         * Binary exclusive or operator
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   bitwise or (with p's precision and characteristic)
         */
        friend SG_API XNum operator^(const XNum& p, const XNum& q);

        /**
         * Right shift
         * @param    shift   number of bits to shift
         * @return   XNum value after shift
         */
        XNum operator>>(int shift) const;

        /**
         * Left shift
         * @param    shift   number of bits to shift
         * @return   XNum value after shift
         */
        XNum operator<<(int shift) const;

        //////////////////////////////////////////////////
        // Comparison operators
        //////////////////////////////////////////////////

        /**
         * Equality predicate
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   true if p == q, false otherwise
         */
        friend SG_API bool operator==(const XNum& p, const XNum& q);

        /**
         * Inequality predicate
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   true if p != q, false otherwise
         */
        friend SG_API bool operator!=(const XNum& p, const XNum& q);

        /**
         * Less than predicate
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   true if p < q, false otherwise
         */
        friend SG_API bool operator<(const XNum& p,  const XNum& q);

        /**
         * Greater than predicate
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   true if p > q, false otherwise
         */
        friend SG_API bool operator>(const XNum& p,  const XNum& q);

        /**
         * Less than or equals predicate
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   true if p <= q, false otherwise
         */
        friend SG_API bool operator<=(const XNum& p, const XNum& q);

        /**
         * Greater than or equals predicate
         * @param    p   XNum left operand
         * @param    q   XNum right operand
         * @return   true if p >= q, false otherwise
         */
        friend SG_API bool operator>=(const XNum& p, const XNum& q);

        //////////////////////////////////////////////////
        // Misc. methods
        //////////////////////////////////////////////////

        /** @return  (bit) value as a double */
        double toDouble() const;

        /**
         * Return true bit-values as Radix-2 String
         *
         * @return  value as a std::string
         */
        std::string toRawString() const;

        /**
         * Generate a std::string type name encoding xlArithType and precision.
         * Example:  UNum_17
         * @return std::string type name
         */
        std::string typeName();

        /** @return the number of bits in this XNum */
        inline int  getNbits() const { return nbits; }

        /**
         * Get the value of the i-th bit
         * @param  index  bit of interest
         * @return index bit of this XNum
         */
        int  getBit(int index) const;

        /**
         * Set value of i-th bit
         * @param  index bit of interest
         * @param  val   desired value (either 0 or 1)
         */
        void setBit(int index, int val);

        /** @return xlArithType of this XNum  */
        inline xlArithType getArithType() const { return ntype; }

        /** @return xlQuantization characteristic */
        inline xlQuantization getQuantization() const { return qc; }

        /** @return xlOverflow characteristic */
        inline xlOverflow getOverflow() const { return of; }

        /** @return 1 if signed, 0 if unsigned  */
        inline bool isSigned() const { return ntype == xlSigned; }

        /** @return 0 if signed, 1 if unsigned  */
        inline bool isUnsigned() const { return ntype == xlUnsigned; }

        /** @return 1 if negative, 0 if not  */
        inline bool isNegative() const
            { return (ntype == xlUnsigned || nbits==0) ? false : (getBit(nbits-1) ? true : false); }

        inline void setArithType(xlArithType t) { setParam(t); }
        inline void setQuantization(xlQuantization t) { setParam(t); }
        inline void setOverflow(xlOverflow t) { setParam(t); }

        // forces Arithmetic Type to supplied type but without changing
        // binary representation of XNum (no conversion, saturation, etc.)
        inline void forceArithType(xlArithType t) { ntype = t; }

        /** @return index of most sig. bit */
        int getMsBitIndx() const;

        // it is provided for HSimValue <=> XFix conversion
        // use it for your own risk
        inline data * getRawDataPtr() { return v; }
        inline data const * getRawDataPtr() const { return v; }

    protected:
        //XNum operator*(XNum*);


    private:
        /**
         * It copies bits from one container to this without
         * rounding or satuation. Since XNum has a binpt,
         * the source binpt and the target binpt must also be passed
         * to the methos.
         * @param from        The contained that copied from
         * @param binpt_from  The binpt of the source container
         * @param binpt_to    The binpt of the target container
         */
        void cast(const XNum& from, int binpt_from, int binpt_to);

        /**
         * It performs an addition for p and q, and stores the result
         * into this.
         * The add is used for full precision xfix addition
         * it assumes this, p, and q all have the same width and type
         */
        XNum& add(const XNum& p, const XNum& q);

        /**
         * It performs a subtraction for p and q, and stores the result
         * into this.
         * the sub is used for full precision xfix addition
         * it assumes this, p, and q all have the same width and type
         */
        XNum& sub(const XNum& p, const XNum& q);

        /**
         * It performs an unsigned muliplication for p and q, and stores the result
         * into this.
         * unsigned_mult performs a full precision unsigned muliplication
         * it assumes this.nbits = p.nbits + q.nbits.
         */
        XNum& unsigned_mult(const XNum& p, const XNum& q);

        /**
         * It performs a bit wise and for p and q, and stores the result
         * into this.
         * the bitAnd is used by full precision xfix bit wise and
         * it assumes this, p, and q all have the same width and type
         */
        XNum& bitAnd(const XNum& p, const XNum& q);

        /**
         * It performs a bit wise or for p and q, and stores the result
         * into this.
         * The bitOr is used for by precision XFix bit wise or.
         * It assumes this, p, and q all have the same width and type
         */
        XNum& bitOr(const XNum& p, const XNum& q);

        /**
         * It performs a bit wise xor for p and q, and stores the result
         * into this.
         * The bitXor is used for full precision xfix bit wise and
         * it assumes this, p, and q all have the same width and type
         */
        XNum& bitXor(const XNum& p, const XNum& q);

        /**
         * It performs a bit wise not for p, and stores the result
         * into this.
         * The bitNot is used for full precision xfix bit wise and
         * it assumes this and p both have the same width and type
         */
        XNum& bitNot(const XNum& p);

        /**
         * It performs an unsigned comparison for this and p.
         * It returns a positive number if this is greater than p,
         * zero if this equals to p, a negative number if this is less
         * than p. It assumed p and this have the same width.
         */
        int unsigned_comp (const XNum& p) const;

        inline XNum& bitNot() {return negateLogical();}

        inline void round_toward_inf(const XNum &from,
                                     int binpt_from, int binpt_to) {
            if (incr4round(from, binpt_from, binpt_to))
                incr();
        }

        inline void round_banker(const XNum &from,
                                 int binpt_from, int binpt_to) {
            if (incr4round_banker(from, binpt_from, binpt_to))
                incr();
        }

        /**
         * clear the overflow bit of tag
         */
        inline void clearTagOverflow() { tag &= ~XNUM_OVERFLOW;}

        /**
         * set the overflow bit of tag
         */
        inline void setTagOverflow() { tag |= XNUM_OVERFLOW;}

        /**
         * get the overflow bit of tag
         */
        inline int getTagOverflow() const { return tag & XNUM_OVERFLOW; }

        void saturate_arith(const XNum& from, int binpt_from, int binpt_to);
                            
        /** @return -1 if p < q, 0 if p==q, 1 if p>q */
        friend int XNumcmp(const XNum& p, const XNum& q);

        /**
         * @param nb  number of bits
         * @return number of words required to represent nb bits
         */
        int bits2words(int) const;

        /**
         * @param nb  number of bits
         * @return number bits in the most significant word of the words
         * required to represent nb bits
         */
        int bits2msw_sz(int) const;


        /**
         * Allocate and clear a data array having specified size
         */
        data* newDataArray(int sz);

        /**
         * initialize the data array.
         * @param  initval  initialization value
         * @param  len      length of initialization array
         */
        void initData(data* initval, int len);


        /**
         * Saturate this XNum
         *
         * @param  to  1: saturate to most pos value
         *            -1: saturate to most neg value
         *             0: saturate to zero
         */
        XNum& saturate(int to);

        /** @return index of most sig. word */
        int getMswIndx() const;


        /**
         * radix conversion utility.  Divides this XNum by a value
         * and returns the remainder.
         * @param  q   divisor
         * @return remainder of this mod q
         */
        XNum remDivEq(XNum);

        /**
         * Assign all bits to the left of position i
         * to have the value <code>getBit(i)</code>.
         * @param   i  starting bit position
         * @return  <code>getBit(i)</code> or -1 if invalid position
         */
        inline int extendLeft(int pos) {
            if (pos < 0 || pos >= nbits)
                return -1;
            int bit = getBit(pos);
            while (++pos < nbits)
                setBit(pos, bit);
            return bit;
        }

        //////////////////////////////////////////////////
        //
        //  Layout of each data word
        //
        //      +---------+---------+
        //      |  XXXXX  |base_bits|
        //      +---------+---------+
        //
        //
        //  Layout of the data array (Little Endian)
        //
        // |<--------------- nwrds ------->|
        // |                               |
        // |    |<---------- nbits ------->|
        // +========+====     ====+========+
        // |    1   |    o o o    |        |   <--- data array
        // +========+====     ====+========+
        //
        //////////////////////////////////////////////////
        int nbits;   // number of bits precision
        xlArithType  ntype;
        xlQuantization  qc; // quantization characteristic
        xlOverflow      of; // overflow characteristic
        int nwrds;   // number of words in data array
        int msw_sz;  // number of bits in v[nwrds-1]
        data* v;     // the data array
        int tag;     // so far only records overflow in saturate_arith
#ifdef XNUM_DEBUG
        data v_[xn_size];
#endif

    private:
        /** Number of data bits per memory word */
        static const int _base_bits;

        /** Mask for a word's msb */
        static const data _base_msb_mask;

        /** Mask for a word's data bits */
        static const data _base_wordbits;

        /** Mask for a word's guard bits */
        static const data _base_guardbits;
        /** Mask for a word's 1st guard bit */
        static const data _base_1st_guardbit;

        static const data _ov_mask;
        static const data _ov_mask_clr;
        /**
         * Table of masks for most * significant words
         * (index = nbits/base_bits)
         */
        static const data _wordbit_mask[];

        /** Table of word masks for most
         * significant bit (index = nbits % base_bits)
         */
        static const data _msb_mask[];

        /** Table of masks for guard bits
         * (index = nbits % base_bits)
         */
        static const data _guardbit_mask[];

    public:
        /** Number of data bits per memory word */
        static const int &base_bits();

        /** Mask for a word's msb */
        static const data &base_msb_mask();

        /** Mask for a word's data bits */
        static const data &base_wordbits();

        /** Mask for a word's guard bits */
        static const data &base_guardbits();

        /** Mask for a word's 1st guard bit */
        static const data &base_1st_guardbit();

        static const data &ov_mask();

        static const data &ov_mask_clr();

        /**
         * Table of masks for most * significant words
         * (index = nbits/base_bits)
         */
        static const data *wordbit_mask();

        /** Table of word masks for most
         * significant bit (index = nbits % base_bits)
         */
        static const data *msb_mask();

        /** Table of masks for guard bits
         * (index = nbits % base_bits)
         */
        static const data *guardbit_mask();

        inline void set_ov_flag() { v[nwrds-1] |= ov_mask(); }
        inline void clr_ov_flag() { v[nwrds-1] &= ov_mask_clr(); }
    };

    /**
     * \param val "the int"
     * \param typ the methods used for arithmetic
     * \return number bits required to represent the int argument
     * \see xlArithType
     */
    SG_API int bitsInInt(int val, xlArithType typ);
}
//////////////////////////////////////////////////
//     End XNUM_H
//////////////////////////////////////////////////
#endif
