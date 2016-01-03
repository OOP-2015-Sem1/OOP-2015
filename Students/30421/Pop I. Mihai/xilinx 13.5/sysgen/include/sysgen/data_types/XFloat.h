/**
 *  Module:  XFloat.h
 *  Description:  floating-point data type
 *  Author:  Jayesh Siddhiwala
 *
 **/

#ifndef _XFLOAT_H
#define _XFLOAT_H


#include <cstdlib>
#include <cmath>
#include <string>
#include <vector>

#include "sysgen/sg_config.h"
#include "sysgen/IntType.h"
#include "sysgen/xlTypes.h"


namespace Sysgen {

    static const uint32 FLOAT_SIZE           = 32;
    static const uint32 FLOAT_EXP_WIDTH      = 8;
    static const uint32 FLOAT_FRACTION_WIDTH = 24;
    static const uint32 FLOAT_EXP_MAX        = 127;
    static const int    FLOAT_EXP_MIN        = -126;
    static const uint32 FLOAT_EXP_BIAS       = 127;
    static const uint32 FLOAT_EXP_NaN        = 257;

    static const uint32 DBL_SIZE           = 64;
    static const uint32 DBL_EXP_WIDTH      = 11;
    static const uint32 DBL_FRACTION_WIDTH = 53;
    static const uint32 DBL_EXP_MAX        = 1023;
    static const int    DBL_EXP_MIN        = -1022;
    static const uint32 DBL_EXP_BIAS       = 1023;
    static const uint32 DBL_EXP_NaN        = 2047;

    static const uint64 FLOAT_SIGN_BIT_MASK = 0x80000000;
    static const uint64 FLOAT_POS_SIGN_BIT_MASK = 0x7FFFFFFF;
    static const uint64 FLOAT_EXPONENT_BITS_MASK = 0x7F800000;
    static const uint64 FLOAT_FRACTION_BITS_MASK = 0x007FFFFF;

    static const uint64 DBL_SIGN_BIT_MASK = 0x8000000000000000ULL;
    static const uint64 DBL_POS_SIGN_BIT_MASK = 0x7FFFFFFFFFFFFFFFULL;
    static const uint64 DBL_EXPONENT_BITS_MASK = 0x7FF0000000000000ULL;
    static const uint64 DBL_FRACTION_BITS_MASK = 0x000FFFFFFFFFFFFFULL;


    union uint32_float_union_t {
        uint32 i32;
        float f;
    };

    union uint64_double_union_t {
        uint64 i64;
        double d;
    };

    /// \ingroup public_utility
    /// \brief Xilinx Proprietary Floating-point Data Type
    /**
     *
     * Arbitrary precision floating-point data type class
     *
     **/
    class SG_API XFloat
    {
    public:

        /**
         * XFloat
         *
         * Default constructor
         * A double precision will be implemented by default
         *
         */
        XFloat();

        /**
         * XFloat (const XFloat &)
         *
         * Copy constructor
         *
         */
        XFloat(const XFloat &);

        /**
         * XFloat::XFloat(int, int, int, xlQuantization, xlOverflow)
         *
         * constructor with INT as data type for input value
         *
         * @param  val     type int representation of value
         * @param  bitWidth total number of bits
         * @param  binPt   binary point index
         * @param  qnt     quantize or truncate (defaultQuantization is xlTruncate)
         * @param  oflow   xlSaturate or xlWrap (defaultOverflow is xlWrap)
         *
         */
        XFloat(int val,
               int bitWidth = DBL_SIZE,
               int binPt = DBL_FRACTION_WIDTH,
               xlQuantization qnt = defaultQuantization,
               xlOverflow oflow   = defaultOverflow);

        /**
         * XFloat::XFloat(double, int, int, xlQuantization, xlOverflow)
         *
         * constructor with double as data type for input value
         *
         * @param  val     type double representation of value
         * @param  bitWidth total number of bits
         * @param  binPt   binary point index
         * @param  qnt     quantize or truncate
         * @param  oflow     xlSaturate or xlWrap
         *
         */
        XFloat(double val,
               int bitWidth = DBL_SIZE,
               int binPt = DBL_FRACTION_WIDTH,
               xlQuantization qnt = defaultQuantization,
               xlOverflow oflow = defaultOverflow);

        /**
         * ~XFloat
         *
         * default destructor
         *
         */
        ~XFloat();

        /**
         * std::string toRawString (int) const
         *
         * @param radix Supports radix 2 and 16. the default radix is 2.
         *
         * returns equivalent XFloat value as a string of bits
         * The possible bit values are 0, 1 and X.
         *
         */
        std::string toRawString(int radix = 2) const;

        /**
         * toFloat
         *
         * returns equivalent single precision value
         *
         */
        float toFloat() const;

        /**
         * toDouble
         *
         * returns equivalent double precision value
         *
         */
        double toDouble() const;
        
        /**
         * std::string toString (int) const
         *
         * convert XFloat to a string for nice display
         *
         * @param radix radix to display
         * @return string to display
         */
        std::string toString( int radix = 10 ) const;

        /**
         * copyToBitVector (uint64**, uint32, uint32)
         *
         * The expWidth and fracWidth arguments help decide floating-point format.
         * Fills up input V with bit representation of floating point number.
         * The unused MSBs are set to 0.
         *
         * The application is responsible to allocate and delete
         * the memory associated with the pointer
         *
         */
        void copyToBitVector (uint64** V, uint32 expWidth, uint32 fracWidth) const;

        /**
         * const uint64* toBitVector
         *
         * fills up an uint64 with bit representation of floating point number
         * using internal parameters.
         * unset MSBs are set to 0.
         *
         */
        const uint64* toBitVector();

        /**
         * bitVectorToXFloat (const uint64*)
         *
         * Performs the opposite of toBitVector
         *
         */
        void bitVectorToXFloat(const uint64* input_v);

        /**
         * std::string typeName () const
         *
         * generates a type name.  Example:  XFloat_8_24
         * where XFloat exponent bit width is 8 and the precision is 24 bits
         *
         */
        std::string typeName() const
        {
            char buf[32];
            sprintf(buf, "XFloat_%d_%d", _expWidth, _fractionWidth);
            return std::string(buf);
        }

        /**
         * operator=(float val)
         *
         * copies float value into equivalent XFloat object
         * This function throws exception if called for custom precision
         *
         */
        XFloat& operator=(float val);

        /**
         * operator=(double val)
         *
         * copies double into equivalent XFloat object
         *
         */
        XFloat& operator=(double val);

        /**
         * operator=(const XFloat& inp_xflt)
         *
         * copies value if inp_xflt to this XFloat object
         *
         */
        XFloat& operator=(const XFloat& inp_xflt);

        /**
         * operator=(const char* inp_str)
         *
         * extracts 0, 1 bit values from inp_str char string
         * and assigns appropriate values to exponent, fraction etc.
         * object members
         *
         * Assumtion: The total bit width (the sum of the exponent width
         * and the fraction bit width) of this object must match with the
         * length of char string
         *
         */
        XFloat& operator=(const char* inp_str);

        /**
         * operator==(const XFloat& rhs)
         *
         * comparison operator
         * returns true or false
         *
         */
        bool operator==(const XFloat& rhs) const;

        /**
         * set_mpfr_str(int, const char*, int)
         *
         * takes signed exponent value as integer,
         * fraction bits in the form of const char string and
         * boolean sign for positive/negative number.
         * The default radix base is 2.
         *
         * returns true of operation is successful, otherwise returns false.
         * for radix other than 2 it gives error and returns false.
         *
         */
        bool set_mpfr_str (int exponent, const char* fraction_str, int base = 2);

        /**
         * std::string get_mpfr_str (int) const
         *
         * The default radix base is 2.
         *
         * returns char string in MPFR format as described below
         * <sign_of_the_number><fraction_digits><Exponent_prefix><Exponent_sign><Exponent_value>
         *
         * sign_of_the_number is optional.
         * The exponent section is optional too.
         * Exponent_prefix = e | E | p | P | @
         * sign_of_number and Exponent_sign are either + or -
         * The Exponent_value must be in decimal.
         * The each fraction digit must be smaller than  base value.
         * For example base 10, each digit of fraction digit must be between 0 and 9.
         *
         * For radix other than 2 it gives error and returns NULL pointer.
         *
         */
        std::string get_mpfr_str (int base = 2) const;

        // methods to check status
        bool isNegative () const { return ((_signBit) ? true : false); }

        bool isZero () const { return _isZero; }

        bool isNaN () const { return _NaN; }

        bool isInfinity () const { return _infinity; }

        bool isSubNormalNumber () const { return _subNormalNum; }

        /**
         * isDeterminate
         *
         * returns true if floating-point value is determinable
         *
         */
        bool isDeterminate () const { return _determinate; }

        /**
         * makeDeterminate
         *
         * sets _determinate to true
         *
         */
        void makeDeterminate () { _determinate = true; }

        /**
         * makeInDeterminate
         *
         * sets _determinate to false
         *
         */
        void makeIndeterminate () { _determinate = false; }

        bool isValid () const { return _valid; }

        void makeValid () { _valid = true; }

        void makeInvalid () { _valid = false; }

        bool isSinglePrecision () const { return _singlePrecision; }

        bool isDoublePrecision () const { return _doublePrecision; }

        // methods to get values for XFloat components
        int64 getExponent () const { return _exponent; }

        uint64* getFractionBits () const { return _fractionBits; }

        uint32 getWidth () const { return _bitWidth; }

        uint32 getExponentBitWidth () const { return _expWidth; }

        uint32 getFractionBitWidth () const { return _fractionWidth; }

        uint32 getNum64BitWords () const { return _num64BitWords; }

    private:
        uint32 _bitWidth; // total number of bits, input parameter
        uint32 _fractionWidth; // width of fraction/mantissa that includes the first hidden bit
        uint32 _expWidth; // width of exponent value
        uint32 _num64BitWords; // number of 64-bit words
        uint32 _num64BitFractionWords; // number of 64-bit words to store fraction bits

        uint32 _signBit;
        uint32 _bias;
        uint32 _expMax;
        int    _expMin;
        int64  _exponent;
        int64  _biasedExponent;
        uint32 _invalidExponent;

        uint64* _v;
        uint64* _fractionBits; // storage for fraction bits that
                               // does not include the first hidden bit value, 1

        uint32_float_union_t _flt; // single precision value of xfloat data
        uint64_double_union_t _dbl; // double precision value of xfloat data

        bool _isZero;
        bool _valid;
        bool _determinate;
        bool _NaN;
        bool _infinity;
        bool _subNormalNum;

        // commenting out _overFlow and _underFlow flags until
        // needed to check the status
        // bool _overFlow;
        // bool _underFlow;

        bool _doublePrecision;
        bool _singlePrecision;

        void resetStatus () {
            _isZero = false;
            _signBit = 0;
            _NaN = false;
            _infinity = false;
            _subNormalNum = false;

            // commenting out _overFlow and _underFlow flags until
            // needed to check the status
            // _overFlow = false;
            // _underFlow = false;
        }

        //
        // Private function members
        //
        /**
         * setNaNorInfBits
         *
         * sets _exponent, _biasedExponent and _fractionBits for NaN and infinity
         *
         */
        void setNaNorInfBits ();

        /**
         * checkParamWidth
         *
         * checks DRC for _bitWidth, _expWidth and _fractionWidth
         * in case of failure throws runtime exception
         *
         */
        void checkParamWidth () const;

        /**
         * bool isInputZero (double)
         *
         * returns true if the input double is 0.0 or -0.0
         * otherwise returns false
         *
         */
        bool isInputZero (double val);
        
        /**
         * int isInputNegative (double)
         *
         * returns 1 if the input double is negative value incl -0.0
         * otherwise returns 0
         *
         */
        int isInputNegative (double val);

        /**
         * checkAndUpdateStatusFlags
         *
         * checks for NaN, Infinity, Sub-normal values and
         * sets appropriate member variables
         *
         */
        void checkAndUpdateStatusFlags ();

#if 0
        /**
         * checkForOverflowUnderflow
         *
         * checks for over-flow and under-flow conditions
         * sets appropriate value for _biasedExponent
         *
         */
        void checkAndUpdateForOverflowUnderflow ()
#endif

        /**
         * createAndInitialize64BitVector (uint32, uint64**);
         *
         * if input arrPtr is NULL then allocates memory for numWords.
         * also initializes all bytes to 0
         *
         */
        void createAndInitialize64BitVector (uint32 num64BitWords, uint64** arrPtr);

        /**
         * calcXFloatElements (double)
         *
         * calculates values for sign, exponent and fraction bits
         *
         */
        void calcXfloatElements (double val);


        /**
         * calcExponentAndFractionBits (double)
         *
         * calculates values for _exponent and _fractionBits array
         *
         */
        void calcExponentAndFractionBits (double val);


        /**
         * setV
         *
         * copies bits from _signBit, _biasedExponent and _fractionBits to _v
         *
         */
        void setV ();

        /**
         * extractExponent (const uint64*)
         *
         * extracts exponent bits and calculates values
         * for _biasedExponent and _exponent data members
         *
         */
        void extractExponent (const uint64* input_v);

        /**
         * copySignAndExponentBits
         *
         * this function copies the bits to _v
         * It also shifts _v to make sure the sign and exponent bits
         * are at correct position.
         *
         */
        void copySignAndExponentBits ();

        /**
         * copyBits(const uint64*, uint32, uint64**, uint32) const
         *
         * copy min(src_width, dest_width) number of bits from
         * src bit array to dest bit array
         *
         */
        void copyBits(const uint64* src_arr, uint32 src_width, uint64** dest_arr, uint32 dest_width) const;

        /**
         * getCharIndex (int)
         *
         * @param count  counter for the number of 4 bits sets to shift right
         *
         * returns integer value that the LSB 4 bits represent
         *
         */
        int getCharIndex (int count) const;


        /**
         * getBit (int)
         *
         * returns bit value (0/1) from _v vector array for the given index
         *
         */
        int getBit(int index) const;

        /**
         * clear
         *
         * deletes memory for _v and _fractionBits
         * Also assigns the data members to NULL
         *
         */
        void clear();

    };

} // namespace Sysgen

#endif // _XFLOAT_H
