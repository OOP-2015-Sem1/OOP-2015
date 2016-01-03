/*
 * $Header: /devl/xcs/repo/env/Jobs/sysgen/src/cxx/include/sysgen/xlTypes.h,v 1.1 2004/11/22 00:16:57 rosty Exp $
 *
 * Module:  xlTypes.h
 *
 *  Description:  types and defaults for xlArithType, xlQuantization
 *                and xlOverflow
 */
//------------------------------------------------------------

#ifndef xlArithTypes_DEFINED
#define xlArithTypes_DEFINED

#include <string>

// This global enumeration has been depreciated. Please
// use the SysgenType::ArithmeticType enumeration defined
// in the public header SysgenType.h instead.
/**
 * Supported arithmetic types
 * <PRE>
 *     xlUnsigned -  unsigned integer
 *     xlSigned   -  signed two's complement
 *     xlBoolean  -  boolean
 *     xlFloat    -  arbitrary precision floating point data
 *     xlDontChangeArithType is a placeholder for the setParam
 *       function and doesn't correspond to a genuine arithmetic type
 * </PRE>
 */
enum xlArithType { xlDontChangeArithType=0,
                   xlUnsigned=1,
                   xlSigned=2,
                   xlBoolean=3,
                   xlFloat=4,
                   n_xlArithType=5 };

const xlArithType defaultArithType = xlSigned;

xlArithType StringToArithType(const std::string &);
std::string ArithTypeToString(xlArithType);

#endif //xlArithType

//------------------------------------------------------------

#ifndef xlQuantization_DEFINED
#define xlQuantization_DEFINED

/**
 * Supported quantization characteristics
 * <PRE>
 *     xlTruncate - truncate results according to precision
 *     xlRound    - round toward +/- infinity
 *     xlRoundBanker - round toward even when equidistant
 *     xlIPRoundBanker - round toward even when equidistant, 
 *						 except when maximum positive value is used
 *
 *     xlDontChangeQuantization is a placeholder for the setParam
 *       function and doesn't correspond to a characteristic
 * </PRE>
 */
enum xlQuantization { xlDontChangeQuantization=0,
                      xlTruncate=1,
                      xlRound=2,
                      xlRoundBanker=3,
					  xlIPRoundBanker=4,
                      n_xlQuantization=5};

const xlQuantization defaultQuantization = xlTruncate;

xlQuantization StringToQuantization(const std::string &);
std::string QuantizationToString(xlQuantization);

#endif //xlQuantization

//------------------------------------------------------------

#ifndef xlOverflow_DEFINED
#define xlOverflow_DEFINED

/**
 * Supported overflow characteristics
 * <PRE>
 *     xlWrap          - wrap bits on overflow
 *     xlSaturate      - value to max (positive) or min (negative or zero)
 *     xlThrowOverflow - throw exception on overflow
 *
 *     xlDontChangeOverflow is a placeholder for the setParam
 *       function and doesn't correspond to a characteristic
 * </PRE>
 */
enum xlOverflow { xlDontChangeOverflow=0,
                  xlWrap=1,
                  xlSaturate=2,
                  xlThrowOverflow=3,
                  n_xlOverflow=4};


const xlOverflow defaultOverflow = xlWrap;

xlOverflow StringToOverflow(const std::string &);
std::string OverflowToString(xlOverflow);

#endif //xlOverflow
