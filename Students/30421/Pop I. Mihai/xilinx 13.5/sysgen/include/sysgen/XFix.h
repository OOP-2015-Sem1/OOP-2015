/*
 * $Header: /devl/xcs/repo/env/Jobs/sysgen/src/cxx/include/sysgen/XFix.h,v 1.4 2005/11/15 20:49:32 daleh Exp $
 *
 * Module:  XFix.h
 *
 *  Description:  fixed-point data type
 */
#ifndef XFIX_H
#define XFIX_H

#define XFIX_TEMP_POOL_SIZE 8
#define XFIX_TEMP_ADD_IDX_0 0
#define XFIX_TEMP_ADD_IDX_1 1
#define XFIX_TEMP_ADD_IDX_2 2
#define XFIX_TEMP_MULT_P 0
#define XFIX_TEMP_MULT_Q 1
#define XFIX_TEMP_MULT_RES 2
#define XFIX_COMP_P 6
#define XFIX_COMP_Q 7
#define XFIX_TEMP_P 0
#define XFIX_TEMP_Q 1
#define XFIX_TEMP_RES 2
#define XFIX_TEMP_TD ((XFIX_TEMP_POOL_SIZE)-1)

#define XFIX_COMP_EQ 1
#define XFIX_COMP_NE 2
#define XFIX_COMP_LT 3
#define XFIX_COMP_LE 4
#define XFIX_COMP_GT 5
#define XFIX_COMP_GE 6

#include "sysgen/sg_config.h"

// Friend Prototypes

namespace Sysgen {
    class XFix;
    SG_API XFix operator+(const XFix& p, const XFix& q);
    SG_API XFix operator-(const XFix& p, const XFix& q);
    SG_API XFix operator*(const XFix& p, const XFix& q);
    SG_API XFix operator&(const XFix& p, const XFix& q);
    SG_API XFix operator|(const XFix& p, const XFix& q);
    SG_API XFix operator^(const XFix& p, const XFix& q);
    SG_API bool operator>=(const XFix& p, const XFix& q);
    SG_API bool operator<=(const XFix& p, const XFix& q);
    SG_API bool operator>(const XFix& p,  const XFix& q);
    SG_API bool operator<(const XFix& p,  const XFix& q);
    SG_API bool operator==(const XFix& p, const XFix& q);
    SG_API bool operator!=(const XFix& p, const XFix& q);
}

#ifdef _MSC_VER
#pragma warning(disable:4786)
// warning 4786: 255 characer symbols for debug
#endif

#include <vector>

#ifdef _MSC_VER
#pragma warning(disable:4786)
// warning 4786: 255 characer symbols for debug

#pragma warning(disable:4231)
// VC++ warning #4231 is a warning about non-standard (VC++) "extern"
// preceeding template instantiation
#endif

#include <cstdlib>
#include <cmath>
#include <string>

#include "sysgen/XNum.h"


namespace Sysgen {
    /// \ingroup public_utility
    /// \brief Xilinx Proprietary Fixed Point Data Type
    /**
     * Multiple precision fixed-point arithmetic class<P>

     * Attributes such as bit width and binary point position are not
     * part of the type definition, which means numeric comparison
     * between XFix numbers of differing sizes is meaningful.<P>

     * XFix numbers also have attributes to define their quantization
     * and overflow characteristics.  Precision, overflow, and
     * quantization is controlled through the use of explicit functions
     * (e.g., constructors) and operators.  <P>

     * There are two flavors of binary operators, those that preserve
     * a number's precision: <P>

     * <code>
     * <UL>
     * <LI>XFix operator+=(const XFix&);
     * <LI>XFix operator-=(const XFix&);
     * <LI>XFix operator*=(const XFix&);
     * <LI>XFix operator/=(const XFix&);
     * <LI>XFix& operator%=(const XFix& q);
     * <LI>XFix& operator>>=(int);
     * <LI>XFix& operator<<=(int);
     * <LI>XFix operator-();
     * </UL>
     * </code><P>

     * and those that return full-precision results:<P>

     * <code>
     * <UL>
     * <LI>friend XFix operator+(const XFix&, const XFix&);
     * <LI>friend XFix operator-(const XFix&, const XFix&);
     * <LI>friend XFix operator*(const XFix&, const XFix&);
     * </UL>
     * </code><P>

     * The assignment operator involves an implicit cast of its
     * argument to the precision of the object being assigned to.

     * Declaration of a precision, quantization, and overflow
     * characteristic <B>does not</b> ensure preservation by
     * assignment.  Constructors are provided to convert
     * between different precision XFix numbers, or to obtain a
     * desired quantization or overflow characteristic.

    */
    class SG_API XFix
    {
    public:

        /**
         * int to XFix constructor
         *
         * @param  val     type int representation of value
         * @param  binpt   binary point index
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or xf_truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XFix(int val, int binpt = 0,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);

        /**
         * int to XFix constructor
         *
         * @param  val     type int representation of value
         * @param  nbits   number of bits precision
         * @param  binpt   binary point index
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or xf_truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XFix(int val, int nbits, int binpt = 0,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow   = defaultOverflow);

        /**
         * bool to XFix constructor
         *
         * @param  val     type bool representation of value
         *
         *  constructs a 'logical_type' XFix
         */
        explicit XFix(bool val);

        /**
         * Double to XFix constructor
         *
         * @param  val     type double representation of value
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XFix(double val,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow = defaultOverflow);


        /**
         * Double to XFix constructor
         *
         * @param  val     type double representation of value
         * @param  binpt   binary point index
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XFix(double val, int binpt,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow = defaultOverflow);


        /**
         * Double to XFix constructor
         *
         * @param  val     type double representation of value
         * @param  nbits   number of bits precision
         * @param  binpt   binary point index
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XFix(double val, int nbits, int binpt,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow = defaultOverflow);

        /**
         * XFix to XFix conversion constructor
         *
         * @param  val     type XFix representation of value
         * @param  nbits   number of bits precision
         * @param  binpt   binary point index
         * @param  ntype    either xlSigned or xlUnsigned
         * @param  qnt     quantize or xf_truncate
         * @param  oflow     xlSaturate or xlWrap
         */
        XFix(const XFix& val, int nbits, int binpt = 0,
             xlArithType ntype = defaultArithType,
             xlQuantization qnt = defaultQuantization,
             xlOverflow oflow = defaultOverflow);


        /**
         * XNum to XFix constructor
         *
         * @param  val     type XNum representation of value
         */
        XFix(const XNum& val);

        /**
         * char[] to XFix constructor
         *
         * @param  val     binary string representation of value for UNDERLYING XNUM
         * @param  binpt   binary point position ('.' should NOT appear in string)
         * @param  tp      arith type
         */
        XFix(const char* val, int binpt, xlArithType tp);

        /**
         * vector<int> to XFix constructor
         *
         * @param  proto_vec  vector<int> must be lenght 5
         * @ proto_vec[0]  :  number of bits precision
         * @ proto_vec[1]  :  binary point index
         * @ proto_vec[2]  :  either xlSigned or xlUnsigned
         * @ proto_vec[3]  :  xlRound, xlRoundBanker, xlIPRoundBanker or xlTruncate
         * @ proto_vec[4]  :  xlSaturate or xlWrap
         */
        XFix(std::vector<int> proto_vec);


        /**
         * Copy constructor
         *
         * @param  val     type XFix representation of value
         */
        XFix(const XFix& val);

        /**
         * Default constructor
         */
        XFix();

        /**
         * XFix destructor
         */
        ~XFix() { delete_temp_pool(); }

        /**
         * Assignment operator, with implicit cast.  The operand is
         * cast to this's precision.
         *
         * @param    val XFix value to assign to this
         * @return   reference to this
         */
        XFix& operator=(const XFix& val);
        XFix& assignDouble(double val);

        /**
         * Assignment operator from double, with implicit cast.
         * The operand double is cast to this's precision.
         *
         * @param    val value to assign to this
         * @return   reference to this
         */
        XFix& operator=(double val);

        /**
         * Assignment operator from integer, with implicit cast.
         * The operand double is cast to this's precision.
         *
         * @param    val value to assign to this
         * @return   reference to this
         */
        XFix& operator=(int val);

        /**
         * Assignment operator from bool
         * Only works for assignment to XFix's of type logical
         * ...otherwise an exception (XNum::UnimplementedSpec) is thrown
         *
         * @param    val value to assign to this
         * @return   reference to this
         */
        XFix& operator=(bool val);

        inline void saturate(int to) {num.saturate(to);}

        /**
         * return true is the previous convert or assignDouble call overflows
         */
        inline bool isOverflow() const {
            return num.getTagOverflow() != 0;
        }

        /**
         * ReParameterize and Resize and this XFix number, applying overflow
         * and quantization characteristics as required.
         *
         * Conversion process is specified as sequencing operations as:
         *  - Apply changes to quantization and overflow modes
         *  - Change width (number of bits) if increasing
         *  - Apply ArithType change if going to unsigned (may get zero underflow)
         *  - Shift Binary Point (apply quantization or overflow as needed)
         *  - Change width if decreasing (apply overflow as needed)
         *  - Apply ArithType change if going to signed (apply overflow as needed)
         *
         * @param  nb   number of bits
         * @param  bp   binary point
         * @param  nt   xlArithType
         * @param  qt   quantization characteristic
         * @param  ov   overflow characteristic
         */
        void setParam(int nb, int bp,
                      xlArithType nt,
                      xlQuantization qt,
                      xlOverflow ov);

        /**
         * set the param as the result of full precision addition
         */
        inline void setParamAdd(const XFix &p, const XFix &q) { setParam(p+q); }

        /**
         * set the param as the result of full precision subtraction
         */
        inline void setParamSub(const XFix &p, const XFix &q) { setParam(p-q); }

        /**
         * set the param as the result of full precision multiplication
         */
        inline void setParamMult(const XFix &p, const XFix &q) { setParam(p*q); }

        /**
         * set the param as the minimum container required to hold both arguments
         */
        inline void setParamAlign(const XFix &p, const XFix &q) { setParam(p&q); }

        /* move the binary point of this xfix*/
        XFix& moveBinpt(int m);

        inline void setParam(xlArithType nt,
                             xlQuantization qt,
                             xlOverflow ov) { num.setParam(nt,qt,ov); }

#include "sysgen/XNum-setParam-var.h"

#define argCHEAT1 int nb, int bp
#define argCHEAT2 nb, bp
#include "sysgen/XNum-setParam-var.h"
#undef argCHEAT1
#undef argCHEAT2


        /**
         * Resize this XFix number, applying overflow and rounding
         * characteristics as required, to match the XFix supplied
         * as a parameter
         *
         * @param q XFix prototype
         */
        inline void setParam(const XFix& q)
            {
                setParam(q.getNbits(), q.binpt, q.getArithType(),
                         q.getQuantization(),
                         q.getOverflow());
                if (q.isBool()){
                    if (!valid){
                        *this = 0;
                    }
                    makeBool();
                }
                if (q.isAlwaysValid()){
                    if (!valid){
                        valid = true;
                        determinate = false;
                    }
                    makeAlwaysValid();
                }

            }

        /**
         * Resize this XFix number, applying quantization and
         * overflow as necessary.
         *
         * @param  nb   number of bits
         * @param  bp   binary point
         */
        inline void resize(int nb, int bp)
            { setParam(nb,bp); }



        /**
         * Resize this XFix number to the sizes of the XFix
         * supplied as a parameter, applying quantization and
         * overflow as necessary.
         *
         */
        inline void resize(const XFix& q)
            { setParam(q.getNbits(),q.binpt); }

        //////////////////////////////////////////////////
        // Full-precision arithmetic operators
        //////////////////////////////////////////////////

        /**
         * Addition binary operator
         *
         * @param    p XFix left operand
         * @param    q XFix right operand
         * @return   full precision result
         */
        friend SG_API XFix operator+(const XFix& p, const XFix& q);

        /**
         * Subtraction binary operator
         *
         * @param    p XFix left operand
         * @param    q XFix right operand
         * @return   full precision result
         */
        friend SG_API XFix operator-(const XFix& p, const XFix& q);

        /**
         * Multiplication binary operator
         *
         * @param    p XFix left operand
         * @param    q XFix right operand
         * @return   full precision result
         */
        friend SG_API XFix operator*(const XFix& p, const XFix& q);


        /**
         * Additively invert this XFix
         * by taking its two's complement (if xlSigned)
         */
        inline XFix& negate() { num.negate(); dbl=-dbl; return *this; }
        XFix& negateAssign(const XFix& p);

        /**
         * Unary minus (two's complement)
         *
         * @return   full precision two's complement of this
         */
        XFix operator-() const;

        /**
         * Binary not operator
         */
        XFix operator~() const;

        /**
         * Return absolute value as a new XFix object
         *
         * @return absolute value as a new XFix object
         */
        inline XFix abs()  const
            { return (isNegative()) ? -(*this) : XFix(*this); };

        ////////////////////////////////////////////////////////////
        // Precision-preserving, side-effecting arithmetic operators
        ////////////////////////////////////////////////////////////

        /**
         * Multiply this by another XFix, preserving this's precision
         * @param    q   XFix multiplier
         * @return   reference to this after the multiplication
         */
        XFix& operator*=(const XFix& q);

        /**
         * Add a XFix to this, preserving this's precision
         *
         * @param    q   XFix addend
         * @return   reference to this after the addition
         */
        XFix& operator+=(const XFix& q);

        /**
         * Subtract a XFix from this, preserving this's precision
         *
         * @param    q   XFix subtrahend
         * @return   reference to this after the subtraction
         */
        XFix& operator-=(const XFix& q);

        /**
         * Divide this by a XFix, preserving this's precision
         * @param    q   XFix divisor
         * @return   reference to this after the division
         */
        XFix& operator/=(const XFix& q);

        /**
         * Replace this by its remainder modulo another XFix
         *
         * @param    q   XFix divisor
         * @return   reference to this after the division
         */
        XFix& operator%=(const XFix& q);

        /**
         * Right shift operator
         *
         * @param    shift number of bits to shift
         * @return   reference to this after the shift
         */
        XFix& operator>>=(int shift);

        /**
         * Left shift operator
         *
         * @param    shift number of bits to shift
         * @return   reference to this after the shift
         */
        XFix& operator<<=(int shift);

        //////////////////////////////////////////////////
        // Bitwise boolean operators
        //////////////////////////////////////////////////


        /**
         * Binary logical and operator
         * operands will first be alligned at the binary point
         * and zero or sign extended as appropriate.
         *
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   bitwise and (with p's precision and characteristic)
         */
        friend SG_API XFix operator&(const XFix& p, const XFix& q);

        /**
         * Binary logical or operator
         * operands will first be alligned at the binary point
         * and zero or sign extended as appropriate.
         *
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   bitwise or (with p's precision and characteristic)
         */
        friend SG_API XFix operator|(const XFix& p, const XFix& q);

        /**
         * Binary exclusive or operator
         * operands will first be alligned at the binary point
         * and zero or sign extended as appropriate.
         *
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   bitwise or (with p's precision and characteristic)
         */
        friend SG_API XFix operator^(const XFix& p, const XFix& q);

        /**
         * right shift operator
         * @param   shift  number of bits to shift right
         * @return  shifted XFix object (with this precision and characteristic)
         */
        XFix operator>>(int shift);

        /**
         * left shift operator
         * @param   shift  number of bits to shift right
         * @return  shifted XFix object (with this precision and characteristic)
         */
        XFix operator<<(int shift);

        //////////////////////////////////////////////////
        // Comparison operators
        //////////////////////////////////////////////////

        /**
         * Equality predicate
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   true if p == q, false otherwise
         */
        friend SG_API bool operator==(const XFix& p, const XFix& q);

        /**
         * Inequality predicate
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   true if p != q, false otherwise
         */
        friend SG_API bool operator!=(const XFix& p, const XFix& q);

        /**
         * Less than predicate
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   true if p < q, false otherwise
         */
        friend SG_API bool operator<(const XFix& p,  const XFix& q);

        /**
         * Greater than predicate
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   true if p > q, false otherwise
         */
        friend SG_API bool operator>(const XFix& p,  const XFix& q);

        /**
         * Less than or equals predicate
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   true if p <= q, false otherwise
         */
        friend SG_API bool operator<=(const XFix& p, const XFix& q);

        /**
         * Greater than or equals predicate
         * @param    p   XFix left operand
         * @param    q   XFix right operand
         * @return   true if p >= q, false otherwise
         */
        friend SG_API bool operator>=(const XFix& p, const XFix& q);

        //////////////////////////////////////////////////
        // Misc. methods
        //////////////////////////////////////////////////

        /** @return  (bit) value as a double */
        double toDouble() const;
        double quicktoDouble() const;
        double goodtoDouble() const;

        /**
         * Compute the quantization error vs. double precision value
         * @return  quantization error
         */
        double qError() const { return dbl-toDouble(); };

        /**
         * Return raw XFix bits as an Unsigned XNum (dropping the binary point)
         *
         * @return  XNum numerically equalling this<<(this->binpt)
         */
        inline XNum toRawXNum() const
            { XNum r(num); r.setParam(xlWrap,xlUnsigned); return r; }

        /**
         * Return (bit) value as std::string of arbitrary radix
         *
         * @param   radix  defaults to 2
         * @param   prec   digits past radix point to compute
         * @param   nobaseinfo whether to append radix info
         * @return  value as a std::string
         */
        std::string toString(int radix=2, int prec=-1, bool appendradixinfo = true) const;

        /**
         * Return true bit-values as Radix-2 String
         *
         * @return  value as a std::string
         */
        std::string toRawString() const;

        /**
         * Generate a String type name encoding XFixType and precision.
         * Example:  Fix_17_8
         * @return std::string type name
         */
        std::string typeName() const;

        /**
         * Generate a type string in the mcode format: { width, binpt,
         * arithtype, quantization, overflow }
         */
        std::string typeToMatlabString() const;

        /** @return the number of bits in this XFix */
        inline int  getNbits() const { return num.getNbits(); };

        /** @return the binary point position in this XFix */
        inline int  getBinpt() const { return binpt; };

        /** @return the number of bits BEFORE the binary point in this XFix */
        inline int  getNIntBits() const { return num.getNbits()-binpt; };

        /** @return the internally stored double value */
        inline double getDbl() const { return dbl; };

        /** set (override) the internally stored double value */
        inline void setDbl(double val) { dbl=val; };

        /**
         * Get the value of the i-th bit
         * @param  i  bit of interest
         * @return i-th bit of this XFix
         */
        inline int  getBit(int i) const { return num.getBit(i); };

        /**
         * Set value of i-th bit
         * @param  i    bit of interest
         * @param  val  desired value (either 0 or 1)
         */
        inline void setBit(int i, int val) { num.setBit(i, val); };

        /** @return xlArithType */
        inline xlArithType getArithType() const { return num.getArithType(); }

        /** @return true if ArithType is Signed, false if not  */
        inline bool isSigned() const { return num.isSigned(); }

        /** @return true if ArithType is Signed, false if not  */
        inline bool isUnsigned() const { return num.isUnsigned(); }

        /** @return xlQuantization characteristic */
        inline xlQuantization getQuantization() const { return num.getQuantization(); }

        /** @return xlOverflow characteristic */
        inline xlOverflow getOverflow() const { return num.getOverflow(); }

        /** @return all (setParam()able) characteristics */
        inline void getParam(int &nb, int &bp, xlArithType &t,
                             xlQuantization &q, xlOverflow &o) const
            { nb=getNbits(); bp=getBinpt(); t=getArithType();
            q=getQuantization(); o=getOverflow(); }

        inline void setArithType(xlArithType t) { num.setArithType(t); }
        inline void setQuantization(xlQuantization t) { num.setQuantization(t); }
        inline void setOverflow(xlOverflow t) { num.setOverflow(t); }

        // forces Arithmetic Type to supplied type but without changing
        // binary representation of XFix (no conversion, saturation, etc.)
        inline void forceArithType(xlArithType t) { num.forceArithType(t); }

        void makeInvalid();
        inline bool  isValid() const { return (always_valid || valid); }
        inline void makeValid()   { valid = true; pipeline_valid = true;}
        inline void setValidFlag(bool flag) { valid = flag; }

        inline bool  isPipelineValid() const { return (pipeline_valid); }
        inline void setPipelineValidFlag(bool flag) { pipeline_valid = flag; }

        /**
         * @return the address of valid
         * it is provided for HSimValue <=> XFix conversion
         * use it at your own risk
         */
//        inline int *getValidPtr() { return &valid; }

        inline bool  isDeterminate() const { return determinate; }
        inline void makeIndeterminate() { determinate = false; }
        inline void setDeterminateFlag(bool flag) { determinate = flag; }

        inline bool  isAlwaysValid() const { return always_valid; }
        inline void  makeAlwaysValid() { always_valid = true; }
        inline void setAlwaysValidFlag(bool flag) { always_valid = flag; }

        inline bool isBool() const { 
            return (logical_type && (getNbits()==1) && (binpt==0) && (getArithType()==xlUnsigned));
        }
        void makeBool();
        inline void clearBoolFlag() { logical_type = false; }

        inline bool isDblSyncd() const { return dblSyncd; }
        inline void setDblSyncd(bool state) { dblSyncd = state; }

        /** @return 1 if negative, 0 if not  */
        inline bool isNegative() const { return num.isNegative(); }

        /**
         * @return internal data representation of XNum
         * it is provided for HSimValue <=> XFix conversion
         * use at your own risk
         */
        inline data  * getRawDataPtr() { return num.getRawDataPtr(); }
        inline data const * getRawDataPtr() const { return num.getRawDataPtr(); }
        /**
         * @return this
         * a.convert(b) is equivalent to a = b;
         */
        XFix& convert(const XFix& from);

        /**
         * a.addAssign(p, q) is equivalent to a = p + q;
         * internally it does a full precision addition
         * then does a conversion.
         */
        XFix& addAssign(const XFix& p, const XFix& q);

        /**
         * a.subAssign(p, q) is equivalent to a = p - q;
         * internally it does a full precision subtraction
         * then does a conversion.
         */
        XFix& subAssign(const XFix& p, const XFix& q);

        /**
         * a.multAssign(p, q) is equivalent to a = p * q;
         * internally it does a full precision multiplication
         * then does a conversion.
         */
        XFix& multAssign(const XFix& p, const XFix& q);

        /**
         * a.bitAndAssign(p, q) is equivalent to a = p & q;
         * internally it does a full precision bitwise and
         * then does a conversion.
         */
        XFix& bitAndAssign(const XFix& p, const XFix& q);

        /**
         * a.bitOrAssign(p, q) is equivalent to a = p | q;
         * internally it does a full precision bitwise or
         * then does a conversion.
         */
        XFix& bitOrAssign(const XFix& p, const XFix& q);

        /**
         * a.bitXorAssign(p, q) is equivalent to a = p ^ q;
         * internally it does a full precision bitwise xor
         * then does a conversion.
         */
        XFix& bitXorAssign(const XFix& p, const XFix& q);

        /**
         * a.bitNotAssign(p) is equivalent to a = ~p;
         * internally it does a full precision bitwise not
         * (1's comp.) then does a conversion.
         */
        XFix& bitNotAssign(const XFix& p);

        /**
         * it does a bit wise not to itself.
         */
        inline XFix& bitNot() {num.bitNot(); dbl = toDouble(); return *this;}

        /**
         * a.bitNandAssign(p, q) is equivalent to a = ~(p & q);
         * internally it does a full precision bitwise nand
         * then does a conversion.
         */
        XFix& bitNandAssign(const XFix& p, const XFix& q);

        /**
         * a.bintNorAssign(p, q) is equivalent to a = ~(p | q);
         * internally it does a full precision bitwise nor
         * then does a conversion.
         */
        XFix& bitNorAssign(const XFix& p, const XFix& q);

        /**
         * a.bitXnorAssign(p, q) is equivalent to a = ~(p ^ q);
         * internally it does a full precision bitwise xnor
         * then does a conversion.
         */
        XFix& bitXnorAssign(const XFix& p, const XFix& q);

        /**
         * @return true if this equals to p.
         */
        bool equals(const XFix& p);

        /**
         * @return positive number if this is greater than p
         *         zero if this equals to p
         *         negative number if this is less than p
         */
        int compareTo(const XFix& p);

        /**
         * a.eqAssign(p, q) is equivalent to a = p==q;
         * it compares p and q for equality then converts
         * the bool result
         */
        inline XFix& eqAssign(const XFix& p, const XFix& q) {
            return compareAssign(p, q, XFIX_COMP_EQ);
        }

        /**
         * a.neAssign(p, q) is equivalent to a = p!=q;
         * it compares p and q for inequality then converts
         * the bool result
         */
        inline XFix& neAssign(const XFix& p, const XFix& q) {
            return compareAssign(p, q, XFIX_COMP_NE);
        }

        /**
         * a.neAssign(p, q) is equivalent to a = p!=q;
         * it compares p and q for less than then converts
         * the bool result
         */
        inline XFix& ltAssign(const XFix& p, const XFix& q) {
            return compareAssign(p, q, XFIX_COMP_LT);
        }

        /**
         * a.neAssign(p, q) is equivalent to a = p<=q;
         * it compares p and q for less than or equal then converts
         * the bool result
         */
        inline XFix& leAssign(const XFix& p, const XFix& q) {
            return compareAssign(p, q, XFIX_COMP_LE);
        }

        /**
         * a.neAssign(p, q) is equivalent to a = p>q;
         * it compares p and q for greater than then converts
         * the bool result
         */
        inline XFix& gtAssign(const XFix& p, const XFix& q) {
            return compareAssign(p, q, XFIX_COMP_GT);
        }

        /**
         * a.neAssign(p, q) is equivalent to a = p>=q;
         * it compares p and q for greater than or equal then converts
         * the bool result
         */
        inline XFix& geAssign(const XFix& p, const XFix& q) {
            return compareAssign(p, q, XFIX_COMP_GE);
        }

        /**
         * a.lshAssign(p, shift) is equivalent to a = p<<shift;
         * it does a full precision left shift (the kind shift that
         * does not loose any bit), then does a conversion.
         */
        XFix& lshAssign(const XFix& p, int shift);

        /**
         * a.rshAssign(p, shift) is equivalent to a = p>>shift;
         * it does a full precision right shift (the kind shift that
         * does not loose any bit), then does a conversion.
         */
        XFix& rshAssign(const XFix& p, int shift);

    protected:
        /**
         * It copies bits from one container to this without
         * rounding or satuation.
         * @param from    The contained that copied from
         *
         */
        inline void cast(const XFix& from) { num.cast(from.num, from.binpt, binpt);};

        /**
         * compares two xfix number according to the compCode then
         * converts the bool result to this.
         */
        XFix& compareAssign(const XFix& p, const XFix& q, int compCode);

    private:
        XNum num;         // data value
        int  binpt;       // binary point index
        double dbl;       // double value for error analysis

        bool valid;        // boolean; true if XFix value is valid (false=>NaN)
        //          corresponds to valid bit state in hardware

        bool pipeline_valid;//boolean; true if XFix is tagged as being a valid
        //          pipeline stage output.  Used by xlbalance

        bool determinate;  // boolean; true if XFix value is known (false=>NaN)
        //          corresponds to 0bXX concept of behavioral sim

        bool always_valid; // boolean; effectively, an assertion that XFix.valid
        //          will always be true

        bool logical_type; // boolean; true if XFix represents boolean control type

        bool dblSyncd;     // boolean; true if dbl value synced to XFix

        // temp_pool is introduced to improve performance. The key
        // in improving performance is to use temporary xfix containers as few
        // as possible, to resize containers or delete containers when it is
        // really necessary. In order to accomplish this, each xfix object has
        // a pool of temporary containers. An element of the pool is initialized
        // when it's used. It is deleted when the XFix object is deleted.
        XFix *temp_pool[XFIX_TEMP_POOL_SIZE];

        inline void init_temp_pool() {
            for (unsigned int i=0; i<XFIX_TEMP_POOL_SIZE; i++) {
                temp_pool[i] = NULL;
            }
        }

        inline void delete_temp_pool() {
            for (unsigned int i=0; i<XFIX_TEMP_POOL_SIZE; i++) {
                if (temp_pool[i]) {
                    delete temp_pool[i];
                    temp_pool[i] = NULL;
                }
            }
        }

        /**
         * If the required element of the pool has the required
         * prototype, reurn the element. Otherwise initialize
         * the element to the required prototype then return it.
         */
        inline XFix& get_temp(int idx,
                              int tmpnbits, int tmpbinpt,
                              xlArithType tmptype,
                              xlQuantization tmpqnt,
                              xlOverflow tmpoflow) {
            if (idx<0 || XFIX_TEMP_POOL_SIZE<=idx)
                idx = 0;
            if (temp_pool[idx] &&
                temp_pool[idx]->num.nbits != tmpnbits) {
                delete temp_pool[idx];
                temp_pool[idx] = NULL;
            }
            if (!temp_pool[idx])
                temp_pool[idx] = new XFix(0, tmpnbits, tmpbinpt,
                                          tmptype, tmpqnt, tmpoflow);
            if (!temp_pool[idx])
                throw std::bad_alloc();
            temp_pool[idx]->binpt = tmpbinpt;
            temp_pool[idx]->num.ntype = tmptype;
            temp_pool[idx]->num.qc = tmpqnt;
            temp_pool[idx]->num.of = tmpoflow;
            return *temp_pool[idx];
        }

        /**
         * if the required prototype is same as that of this, returns
         * this, otherwise use the one in the temporary pool.
         */
        inline XFix& get_temp_res(int idx,
                                  int tmpnbits, int tmpbinpt,
                                  xlArithType tmptype,
                                  xlQuantization tmpqnt,
                                  xlOverflow tmpoflow) {
            if (tmpnbits==getNbits() &&
                tmpbinpt==getBinpt() &&
                tmptype==getArithType() &&
                tmpqnt==getQuantization() &&
                tmpoflow==getOverflow())
                return *this;
            else
                return get_temp(idx, tmpnbits, tmpbinpt,
                                tmptype, tmpqnt, tmpoflow);
        }

        /**
         * if the required prototype is same as that of from, returns
         * from, otherwise use the one in the temporary pool then assign
         * from to it.
         */
        inline const XFix& get_temp_const(int idx,
                                          int tmpnbits, int tmpbinpt,
                                          xlArithType tmptype,
                                          const XFix& from) {
            if (from.getNbits()==tmpnbits &&
                from.getBinpt()==tmpbinpt &&
                from.getArithType()==tmptype) {
                return from;
            }
            XFix& tmp = get_temp(idx, tmpnbits, tmpbinpt, tmptype,
                                 xlTruncate, xlWrap);
            tmp = from;
            return tmp;
        }

        inline void setValidDeterminate(const XFix& p, const XFix& q) {
            valid = p.valid && q.valid;
            pipeline_valid =  p.pipeline_valid && q.pipeline_valid;
            determinate = p.determinate && q.determinate;
        }
        inline void setValidDeterminate(const XFix& p) {
            valid = p.valid;
            pipeline_valid =  p.pipeline_valid;
            determinate = p.determinate;
        }
    };

    SG_API std::ostream& operator<<(std::ostream& os, const XFix& x);
    SG_API XFix operator+(const XFix& p, const XFix& q);
    SG_API XFix operator-(const XFix& p, const XFix& q);
    SG_API XFix operator*(const XFix& p, const XFix& q);
    SG_API XFix operator&(const XFix& p, const XFix& q);
    SG_API XFix operator|(const XFix& p, const XFix& q);
    SG_API XFix operator^(const XFix& p, const XFix& q);
    SG_API bool operator==(const XFix& p, const XFix& q);
    SG_API bool operator!=(const XFix& p, const XFix& q);
    SG_API bool operator<(const XFix& p,  const XFix& q);
    SG_API bool operator>(const XFix& p,  const XFix& q);
    SG_API bool operator<=(const XFix& p, const XFix& q);
    SG_API bool operator>=(const XFix& p, const XFix& q);
}
//////////////////////////////////////////////////
//     End XFIX_H
//////////////////////////////////////////////////
#endif
