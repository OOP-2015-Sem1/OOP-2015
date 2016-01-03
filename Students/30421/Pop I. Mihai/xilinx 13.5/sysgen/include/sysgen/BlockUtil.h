/*
 *  BlockUtil.h
 *
 *  Copyright (c) 2005, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Public, stand-alone proceedures for
 *               System Generator BlockDescriptors and
 *               PortDescriptors.
 */

#ifndef SYSGEN_BLOCK_UTIL_H
#define SYSGEN_BLOCK_UTIL_H

// Xilinx inclusions:
#include "sysgen/sg_config.h"
#include "sysgen/PTable.h"
#include "anytable/AnyTableForwards.h"

// Standard library inclusions:
#include <string>
#include <map>


// Forward declarations:
namespace Sysgen {
class BlockDescriptor;
class PortDescriptor;
struct SysgenType;
}

// No block may have a latency greater than 64k
static const int MAX_LATENCY = 64 * 1024;
static const std::string DEFAULT_BLOCK_NAME = "";

namespace Sysgen {
/// \ingroup public_utility
namespace Utility {

/// \brief True System Generator is running the export flow
SG_API
bool isCoreDeployed();

/// \brief The gcdRate method returns the greatest common denominator
/// of the periods in the SysgenRate objects associated with the
/// PortDescriptor parameters.  
////\param a PortDescriptor pointer whose
/// SysgenRate period will be used in the gcd calculation \param b
/// PortDescriptor pointer whose SysgenRate period will be used in the
/// gcd calculation
SG_API
double gcdRate(const PortDescriptor *a, const PortDescriptor *b);

/// \brief The gcdRate method returns the greatest common denominator
/// of the periods in the SysgenRate objects associated with the
/// PortDescriptor parameters.
/// \param a PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation
/// \param b PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation 
/// \param c PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation 
SG_API
double gcdRate(const PortDescriptor *a, const PortDescriptor *b,
               const PortDescriptor *c);

/// \brief The gcdRate method returns the greatest common denominator
/// of the periods in the SysgenRate objects associated with the
/// PortDescriptor parameters.
/// \param a PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation
/// \param b PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation 
/// \param c PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation 
/// \param d PortDescriptor pointer whose SysgenRate period will be used in the gcd calculation 
SG_API
double gcdRate(const PortDescriptor *a, const PortDescriptor *b,
               const PortDescriptor *c, const PortDescriptor *d);

/// \brief The gcdRate method returns the greatest common
/// denominator of the periods in the SysgenRate objects
/// associated with the input ports of the BlockDescriptor.
/// \param d BlockDescriptor pointer whose input ports'
/// SysgenRate period will be used in the gcd calculation
SG_API
double gcdRate(const BlockDescriptor *d);

/// \brief The gcdRateAllPorts method returns the greatest
/// common denominator of the periods in the SysgenRate
/// objects associated with the input and output ports of the
/// BlockDescriptor.        
/// \param d BlockDescriptor pointer whose ports' SysgenRate period
/// will be used in the gcd calculation
SG_API
double gcdRateAllPorts(const BlockDescriptor *d);

/// \brief The gcdRateAllPorts method returns the greatest
/// common denominator of the periods in the SysgenRate
/// objects associated the vector of PortDescriptors
SG_API
double gcdRate(const std::vector<const PortDescriptor*>& pd);

SG_API
int lcmRate(const std::vector<const PortDescriptor*>& pd);

//nLimit > nIn >0
//find a = nIn * n, such that n is positive int, a <= nLimit and a + nIn > nLimit;
//return a
SG_API
int largestMultiplier(int nIn, int nLimit);

#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// Deprecated.  Do not use.
SG_API
std::pair<bool,double> isEqualRates(const BlockDescriptor* bd);

/// Deprecated.  Do not use.
SG_API
std::pair<bool,double> 
isEqualRates(const std::vector<const PortDescriptor*> &pd);

/// Deprecated.  Use ChkLatEnRst instead.
SG_API
std::pair<bool,std::string> checkCtrlCI(const BlockDescriptor& bd);
#endif
        
/// The makeIcnStrWithLatency method constructs a string for display on a block;
/// the string is three lines, the first line is blank and centers the second line
/// on the block.  The second line is blockText.  blockText must conform to Matlab's tex
/// capabiilties.  The third line contains z to the power of negative latency.
/// \param blockText Text that will be displayed on the block.  Conforms to Matlab's tex format.
/// \param latency The latency value to display on the block.
/// \brief Constructs the text that is displayed on the block in the schematic.
SG_API
std::string makeIcnStrWithLatency(const std::string& blockText, int latency);

/// Retrun the root directory of the Sysgen Generator Installation
SG_API
std::string getSysgenDirectoryName();
        
/// Get the hierarchical settings from the appropriate System Generator
/// tokens as seen by a particular block. The settings are combined into 
/// a single PTable. The PTable contents are deep-copied and returned by 
/// value.
/// \param bd The block descriptor whose settings will be retrieved
/// \brief Returns the settings on a BlockDescriptor.
SG_API
Sysgen::PTable getHierarchicalSettings(const BlockDescriptor& bd);
SG_API
void getHierarchicalSettings(const BlockDescriptor& bd, AnyTable::AnyType &anytable);
#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// For internal use only.
SG_API
Sysgen::PTable getHierarchicalSettings(double block_handle);
SG_API
void getHierarchicalSettings(double block_handle, AnyTable::AnyType &anytable);
#endif

/// Get the base system period for a particular block by retrieving the
/// "base_system_period_simulink" property from the block descriptor's PTable.  All 
/// rates should create events at integer multiples of this base rate. In the absence
/// of System Generator tokens (e.g., in a stand-alone call to configureInterface),
/// defaults to -1.0.
/// \param bd The block descriptor whose PTable will be searched for the base system period.
/// \brief Returns the base system period from the specified block descriptor.        
SG_API
double getBaseSystemPeriod(const BlockDescriptor& bd);

/// Get the HDL synthesis language string (e.g., vhdl or verilog) for 
/// the design.  This string corresponds to the Hardware Description
/// Language setting on the System Generator token.  In the absence
/// of System Generator tokens, defaults to vhdl.  The synthesis language string is stored
/// in the block descriptor's PTable with the key "synthesis_language".
/// \param bd The block descriptor whose PTable will be searched for the synthesis language
/// \brief Returns the synthesis language setting from the specified block descriptor.
SG_API
std::string getSynthesisLanguage(const BlockDescriptor& bd);
        
/// Get the HDL synthesis tool string (e.g., XST or Synplify) for
/// the design.  This string corresponds to the Synthesis Tool
/// setting on the System Generator token.  In the absence of
/// System Generator tokens.  The synthesis tool string is stored
/// in the block descriptor's PTable with the key "synthesis_tool".
/// \param bd The block descriptor whose PTable will be searched for the synthesis tool
/// \brief Returns the synthesis tool setting from the specified block descriptor.
SG_API
std::string getSynthesisTool(const BlockDescriptor& bd);

/// Get the Clock wrapper used (e.g., Single or Multiple) for
/// the design.  This string corresponds to the "Implementing Rates"
/// setting on the System Generator token. The synthesis tool string is
/// in the block descriptor's PTable with the key "synthesis_tool".
SG_API
std::string getClockWrapperType(const BlockDescriptor& bd);

/// Returns the extension of the block netlisting language
/// Example VHDL return .vhd and Verilog returns .v
SG_API
std::string getNetlistingLanguageExtension(const BlockDescriptor& bd);

/// Returns the location of the installed hdl files delivered by sysgen
SG_API 
std::string getHdlFilesDir();


/// Returns true if rates are implemented using multiple clocks
SG_API
bool isMultipleClockWrapper(const BlockDescriptor& bd);

/// Normalize a period (e.g a sample rate period) by the base system
/// period as seen by a block. In the absence of System Generator tokens 
/// (e.g., in a stand-alone call to configureInterface), defaults to -1.0.
/// Returned rates are rounded to integer values if they are within epsilon
/// of an integer.
/// \param bd The block descriptor used for getting the base system period.
/// \param period The period to normalize against the system period. 
/// \param epsilon Determines how close the normalized value must be to an integer in order to perform rounding.
/// \sa getBaseSystemPeriod
/// \brief Returns the value of the period normalized to the base system period.
SG_API
double normalizePeriod(const BlockDescriptor& bd, double period, double epsilon=1e-12);

/// Get the targeted implementation family for a particular block.
/// If the family is not known (e.g., in a stand-alone call to 
/// configureInterface), the empty string will be returned.
/// The coregen part family is stored in the block descriptor's PTable with the
/// key "coregen_part_family".
/// \param bd The block descriptor whose PTable will be searched for the coregen part family.
/// \brief Returns the corgen part family setting from the specified block descriptor.
SG_API 
std::string getCoregenFamilyName(const BlockDescriptor &bd, bool specialized = false);

/// Get the targeted  part/device name for a particular block.
/// If the part is not known (e.g., in a stand-alone call to 
/// configureInterface), the empty string will be returned.
/// \param bd The block descriptor whose part name is requested.
/// \brief Returns the corgen part setting for the specified block descriptor.
SG_API 
std::string getCoregenPartName(const BlockDescriptor &bd);
        
/// Get the targeted  package name for a particular block.
/// If the part is not known (e.g., in a stand-alone call to 
/// configureInterface), the empty string will be returned.
/// \param bd The block descriptor whose part name is requested.
/// \brief Returns the corgen part setting for the specified block descriptor.
SG_API 
std::string getCoregenPackageName(const BlockDescriptor &bd);
/// Get the targeted speed grade for a particular block.
/// If the speed grade is not known (e.g., in a stand-alone call to 
/// configureInterface), -1 will be returned.
/// \param bd The block descriptor whose speed grade is requested.
/// \brief Returns the corgen speed grade for the specified block descriptor.
SG_API 
std::string getCoregenSpeed(const BlockDescriptor &bd);
#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// Returns true if and only if the block lies within a subsystem
/// that is being generated (i.e. the generate button was pressed
/// on a System Generator token in a subsystem containing the block).
SG_API 
bool hasGenerateButtonEvent(const BlockDescriptor &bd);
#endif

#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// chkIfInfRate - Checks if the rates on output ports of the block is infinite.
/// Returns true, if the rates on all the output signals of a block are infinite. 
SG_API
bool checkIfInfRate(const BlockDescriptor &bd);
#endif
                
/// propagateRate - Propagates the rate on a block through a predefined set of rules.
/// Uses the rates on the list of data ports(pd), the enable port(en) and the reset port(rst).
/// \param pd The port descriptors whose rates will be considered when determining which rate to propagate.
/// \param en The port descriptor for the enable port.
/// \param rst The port descriptor for the reset port.
/// \brief Calculates the slowest rate that can be used to update all of the ports as frequently as their rates require.
SG_API
double
propagateRate(const std::vector<const PortDescriptor*> &pd, 
              PortDescriptor* en, 
              PortDescriptor* rst);

/// propagateRate - Propagates the rate on a block through a predefined set of rules.
/// Uses the rates on the data port(p0), the enable port(en) and the reset port(rst).
/// \param p0 The port descriptor whose rate will be considered when determining which rate to propagate.
/// \param en The port descriptor for the enable port.
/// \param rst The port descriptor for the reset port.
/// \brief Calculates the slowest rate that can be used to update all of the ports as frequently as their rates require.
SG_API
double
propagateRate(const PortDescriptor* p0, 
              PortDescriptor* en, 
              PortDescriptor* rst);
/// propagateRate - Propagates the rate on a block through a predefined set of rules.
/// Uses the rates on the data ports(p0,p1), the enable port(en) and the reset port(rst).
/// \param p0 A port descriptor whose rate will be considered when determining which rate to propagate.
/// \param p1 A port descriptor whose rate will be considered when determining which rate to propagate.
/// \param en The port descriptor for the enable port.
/// \param rst The port descriptor for the reset port.
/// \brief Calculates the slowest rate that can be used to update all of the ports as frequently as their rates require.
SG_API
double
propagateRate(const PortDescriptor* p0, 
              const PortDescriptor* p1, 
              PortDescriptor* en, 
              PortDescriptor* rst);

//! @name Error reporting
//@{

/// The illegalParameterization reports an error saying that the current_value of
/// block parameter param_description is illegal because of the specified reason.
/// If current_value or reason is set to an empty string, then that part of the error message will be omitted.
/// param_group only exists for backwards compatibility.  It should not be used.
/// \param aBlock The block that caused the error.
/// \param param_description The name of the parameter that is incorrectly configured.
/// \param current_value The value of the parameter that is not legal.
/// \param reason Text to explain why the parameter setting is not legal.
/// \param param_group Backwards compatibility.  Do not use.
/// \brief Constructs error text that indicates an illegal parameter setting and reports the error on the block.
SG_API
void illegalParameterization(const BlockDescriptor& aBlock,
                             const std::string &param_description,
                             const std::string &current_value,
                             const std::string &reason,
                             const std::string& param_group = std::string(""));

/// \brief This is an overloaded member function, provided for
/// convenience. It behaves like the other versions except that this
/// one accepts a double as the current value.
/// \sa illegalParameterization
SG_API
void illegalParameterization(const BlockDescriptor& aBlock,
                             const std::string &param_description,
                             double current_value,
                             const std::string &reason,
                             const std::string& param_group = std::string(""));
/// \brief This is an overloaded member function, provided for convenience. It behaves like the other versions except that this one accepts an integer as the current value.
/// \sa illegalParameterization
SG_API
void illegalParameterization(const BlockDescriptor& aBlock,
                             const std::string &param_description,
                             int current_value,
                             const std::string &reason,
                             const std::string& param_group = std::string(""));

/// The illegalInputType method reports an error saying that the the type of data
/// that is being passed to input port is illegal because of the specified reason.
/// It displays the name of the port, the type of data that is being passed to the
/// input port, and optionally displays the type of data that should be passed to
/// the port.  The method also causes the port to be highlighted in the schematic.
/// \param aBlock The block whose input port caused the error.
/// \param pd The port that is connected to an illegal input.
/// \param reason Text to explain why the input type is not legal.
/// \param expected_type Text to explain what the input type should be.
/// \sa illegalDataType
/// \brief Constructs error text that indicates an illegal signal type is connected to the input port and reports the error on the block.
SG_API
void illegalInputType(const BlockDescriptor& aBlock,
                      const PortDescriptor *pd,
                      const std::string &reason,
                      const std::string &expected_type = std::string(""));

SG_API
void illegalInputType(const BlockDescriptor& aBlock, const std::vector<PortDescriptor*>& portVector, const std::string &reason, const std::string &expectedType = std::string(""));

/// \brief The illegalDataType function behaves like the illegalInputType function except it creates an error message that can apply to either an input or an output port.
/// \sa illegalInputType
SG_API
void illegalDataType(const BlockDescriptor& aBlock,
                     const PortDescriptor *pd,
                     const std::string &reason,
                     const std::string &expected_type = std::string(""));
        
//To detect Fixed-point and Floating-point port types mix
SG_API
void illegalDataType(const BlockDescriptor& aBlock,
                     const PortDescriptor *pd1,
                     const PortDescriptor *pd2,
                     const std::string &reason,
                     const std::string &expected_type = std::string(""));

/// The illegalInputRate method reports an error saying that the the rate of data
/// that is being passed to input port is illegal because of the specified reason.
/// It displays the name of the port, the rate of the data that is being passed to the
/// input port, and optionally displays the rate of data that should be passed to
/// the port.  The method also causes the port to be highlighted in the schematic.
/// \param aBlock The block whose input port caused the error.
/// \param pd The port that is connected to an illegal input.
/// \param reason Text to explain why the input rate is not legal.
/// \param expected_type Value to explain what the input rate should be.
/// \sa illegalInputRate
/// \brief Constructs error text that indicates an illegal signal type is connected to the input port and reports the error on the block.
SG_API
void illegalInputRate(const BlockDescriptor& aBlock, const PortDescriptor& pd, 
                      const std::string &reason, 
                      const std::string &expectedRate = std::string(""));
SG_API
void illegalInputRate(const BlockDescriptor& aBlock, const std::vector<PortDescriptor*>& portVector, 
                      const std::string &reason, const std::string &expectedRate = std::string(""));

//@}

//! @name Coregen Interface Error Reporting
//@{
/// The coregenClassNotFound method reports an error stating that it was unable to find coregenClass, and optionally
/// that this occurred when trying to call coregenMethod.  Finally, if additionalInfo is also specified, the message in
/// this string is also presented to the user following the aforementioned message.
/// 
/// Does not throw an exception on failure.
SG_API
void coregenClassNotFound(BlockDescriptor& aBlock, const std::string& coregenClass, 
                          const std::string& coregenMethod = std::string(""), const std::string& additionalInfo = std::string(""));

SG_API
bool chkIfFloatOperation(const BlockDescriptor& aBlock,
                         const PortDescriptor* in1,
                         const PortDescriptor* in2 = NULL);

//@}

//! @name Parameter checking
//@{

/// The chkIfAllowedType constructs and reports an error message if the signal connected
/// to the portToChk does not have the same SysgenType as allowedType1.  The aBlockName
/// argument only exists for backwards compatability.  It should not be used.
/// \param aBlock The block whose input port is being tested.
/// \param portToChk The port whose input type will be tested.
/// \param allowedType1 The SysgenType that is a legal input to portToChk.
/// \param aBlockName Backwards compatibility.  Do not use.
/// \sa illegalDataType
/// \brief Report an error if the signal connected to portToChk does not have the allowed SysgenType
SG_API 
void chkIfAllowedType(const BlockDescriptor& aBlock,
                      const PortDescriptor* portToChk, 
                      const Sysgen::SysgenType& allowedType1,
                      const std::string& aBlockName = DEFAULT_BLOCK_NAME);

/// \sa chkIfAllowedType

/// \brief This is an overloaded member function, provided for
/// convenience. It behaves like the other versions except that this
/// one allows the SysgenType to be either of two legal SysgenTypes.
SG_API
void chkIfAllowedType(const BlockDescriptor& aBlock,
                      const PortDescriptor* portToChk, 
                      const Sysgen::SysgenType& allowedType1,
                      const Sysgen::SysgenType& allowedType2,
                      const std::string& aBlockName = DEFAULT_BLOCK_NAME);

/// \sa chkIfAllowedType

/// \brief This is an overloaded member function, provided for
/// convenience. It behaves like the other versions except that this
/// one allows the SysgenType to be any of three legal SysgenTypes.
SG_API
void chkIfAllowedType(const BlockDescriptor& aBlock,
                      const PortDescriptor* portToChk, 
                      const Sysgen::SysgenType& allowedType1,
                      const Sysgen::SysgenType& allowedType2,
                      const Sysgen::SysgenType& allowedType3,
                      const std::string& aBlockName = DEFAULT_BLOCK_NAME);

/// \sa chkIfAllowedType

/// \brief This is an overloaded member function, provided for
/// convenience. It behaves like the other versions except that this
/// one only checks that the arithmetic type field of the signal's
/// SysgenType matches the arithmetic types allowed for the port.
SG_API
void chkIfAllowedType(const BlockDescriptor& aBlock,
                      const PortDescriptor* portToChk,
                      bool allowBool, 
                      bool allowSigned, 
                      bool allowUnsigned,
                      const std::string& aBlockName = DEFAULT_BLOCK_NAME,
                      bool allowFloat = false);

SG_API
void chkIfInportXFloat(const BlockDescriptor& aBlock,
                       const PortDescriptor* portToChk);

/// The chkTypeAllBoolOrNotBool verifies that all the ports in the ports vector are
/// boolean type or that all of them have a non boolean type.  An error will be reported
/// if there is a mix of signal types.
/// \sa chkIfAllowedType
/// \param aBlock The block whose input ports are being tested.
/// \param ports The ports whose input types will be tested.
/// \brief Add an error if the ports contain a mix of boolean and non-boolean input signals.
SG_API
void chkTypeAllBoolOrNotBool(const BlockDescriptor& aBlock,
                             const std::vector<const PortDescriptor*> &ports,
                             bool allowFloat = true);

#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// The chkPrec method ensures that aPrecision is either one or
/// two.  Failing this constraint, an error is added to aBlock,
/// reporting the failure.
///
/// This method has no effect unless DEBUG is defined.
SG_API
void chkPrec(const BlockDescriptor& aBlock,
             const int aPrecision);
#endif

/// \brief The chkLatency method verifies that latency is greater than
/// or equal to zero and less than MAX_LATENCY and reports an error if
/// either condition is not met.
/// \param aBlock If the latency is illegal, the error will be reported on the given block.
/// \param latency The value of the latency parameter that will be tested.
/// \sa chkLatEnRst
SG_API
void chkLatency(const BlockDescriptor& aBlock,
                int latency);

/// \brief the chkBits method verifies that the number of bits is
/// greater than or equal to 1 and reports an error if it is not.
/// \param aBlock If the number of bits is illegal, the error will be reported on the given block.
/// \param aNumOfBits The setting for number of bits that will be tested.
/// \param aParamGroupName Backwards compatibility.  Do not use.
/// \sa chkBitsAndBinPt
SG_API
bool chkBits(const BlockDescriptor& aBlock,
             const int aNumOfBits,
             const std::string& aParamGroupName = std::string(""));

#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// chkBitsAndBinPt - Checks to see if the number of bits
/// is greater than or equal to 1, and if the binary point
/// is greater than or equal to 0.  If not, an error message
/// is added to the block.
///
/// The error message comes in three flavors; hence the three
/// variations on the method signature.  Effectively it boils
/// down to how the error header is formated (i.e. the 
/// "Illegal Parameterization: param_name" part--the variation
/// being what is used for the param_name).
///
/// Several version currently exist only for backwards compatability.
/// Only use the new version when creating new code.

/*
  for backwards compatability
*/

SG_API
void chkBitsAndBinPt(const BlockDescriptor& aBlock,
                     const int aNumOfBits,
                     const std::string& aNumOfBitsParamName,
                     const int aBinPt,
                     const std::string& aBinPtParamName);
SG_API
void chkBitsAndBinPt(const BlockDescriptor& aBlock,
                     const int aNumOfBits,
                     const std::string& aNumOfBitsParamName,
                     const int aBinPt,
                     const std::string& aBinPtParamName,
                     const std::string& aParamGroupName);
SG_API
void chkBitsAndBinPt(const BlockDescriptor& aBlock,
                     const int aNumOfBits,
                     const std::string& aNumOfBitsParamName,
                     const std::string& aNumBitsParamGroupName,
                     const int aBinPt,
                     const std::string& aBinPtParamName,
                     const std::string& aBinPtParamGroupName);
/*
  end for backwards compatability
*/
#endif

/// chkBitsAndBinPt - Use this version

/// \brief the chkBitsAndBinPt method verifies that the number of bits
/// is greater than or equal to 1, that the binary point is greater
/// than or equal to 0, and that the binary point is less than the
/// number of bits and reports an error if any of these conditions are
/// not met.
/// \param aBlock If the number of bits is illegal, the error will be reported on the given block.
/// \param aNumOfBits The setting for number of bits that will be tested.
/// \param aBinPt The setting for binary point that will be tested.
/// \param aParamGroupName Backwards compatibility.  Do not use.
/// \sa chkBits
SG_API
bool chkBitsAndBinPt(const BlockDescriptor& aBlock,
                     const int aNumOfBits,
                     const int aBinPt,
                     const std::string& aParamGroupName = std::string(""));

/// \brief the chkExponentAndFractionBits method verifies that the number
/// of exponent and fraction bits is greater than or equal to 1, and that their
/// sum is within a maximum system limit of 64 and reports an error if
/// any of these conditions are not met.
/// \param aBlock If the number of bits is illegal, the error will be reported on the given block.
/// \param aNumOfExpBits The setting for number of exponent bits that will be tested.
/// \param aNumOfExpBitsParamName The control name for better error reporting.
/// \param aNumExpBitsParamGroupName The control group name for better error reporting.
/// \param aNumOfFractBits The setting for number of fraction bits that will be tested.
/// \param aFractBitsParamName The control name for better error reporting.
/// \param aFractBitsParamGroupName The control group name for better error reporting.
/// \sa chkBits
SG_API
bool chkExponentAndFractionBits(const BlockDescriptor& aBlock,
                                 const int aNumOfExpBits,
                                 const std::string& aNumOfExpBitsParamName,
                                 const std::string& aNumExpBitsParamGroupName,
                                 const int aNumOfFractBits,
                                 const std::string& aFractBitsParamName,
                                 const std::string& aFractBitsParamGroupName);
/// The chkGreaterThan method verifies that aValueToTest is greater than aReferenceValue 
/// and reports an error if it is not.  The generated error message states that aParamterName
/// has value aValueToTest, but must be greater than aReferenceValue.  Additional reason text
/// may be provided to explanation why the value must be greater than the reference value.
/// The function will return false if the test fails and an error is generated.  Otherwise,
/// it will return true.
/// <br>There are several different versions of this method to check other inequalities such as
/// greater than or equal to, less than, less than or equal to, and range inclusive.  There are 
/// also different versions of this method for different data types for aValueToTest and 
/// aReferenceValue such as integer or double.
/// \param aBlock If the condition is not met, the error will be reported on the given block.
/// \param aValueToTest The value of the parameter that is being tested.
/// \param aReferenceValue The value that the parameter is being compared to.
/// \param aParameterName The name of the parameter that is being tested.
/// \param aParameterGroupName Backwards compatibility.  Do not use.
/// \param reason Additional text that will be included in the generated error message.
/// \sa chkGreaterThan chkGreaterThanOrEqual chkLessThan chkLessThanOrEqual chkRangeInclusive
/// \brief Verifies that the parameter's value is greater than the reference value.  Otherwise an error message is generated.
SG_API
bool chkGreaterThan(const BlockDescriptor& aBlock,
                    const int aValueToTest,
                    const int aReferenceValue,
                    const std::string& aParameterName,
                    const std::string& aParameterGroupName = std::string(""),
                    const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is greater than the
/// reference value.  Otherwise an error message is generated.  This
/// function behaves like the other inequality checking functions.
/// See the chkGreaterThan documentation for more information.  \sa
/// chkGreaterThan
SG_API
bool chkGreaterThan(const BlockDescriptor& aBlock,
                    const double aValueToTest,
                    const double aReferenceValue,
                    const std::string& aParameterName,
                    const std::string& aParameterGroupName = std::string(""),
                    const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is greater than or
/// equal to the reference value.  Otherwise an error message is
/// generated.  This function behaves like the other inequality
/// checking functions.  See the chkGreaterThan documentation for more
/// information.  \sa chkGreaterThan
SG_API
bool chkGreaterThanOrEqual(const BlockDescriptor& aBlock,
                           const int aValueToTest,
                           const int aReferenceValue,  
                           const std::string& aParameterName,
                           const std::string& aParameterGroupName = std::string(""),
                           const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is greater than or
/// equal to the reference value.  Otherwise an error message is
/// generated.  This function behaves like the other inequality
/// checking functions.  See the chkGreaterThan documentation for more
/// information.  \sa chkGreaterThan
SG_API
bool chkGreaterThanOrEqual(const BlockDescriptor& aBlock,
                           const double aValueToTest,
                           const double aReferenceValue,  
                           const std::string& aParameterName,
                           const std::string& aParameterGroupName = std::string(""),
                           const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is less than the
/// reference value.  Otherwise an error message is generated.  This
/// function behaves like the other inequality checking functions.
/// See the chkGreaterThan documentation for more information.  \sa
/// chkGreaterThan
SG_API
bool chkLessThan(const BlockDescriptor& aBlock,
                 const int aValueToTest,
                 const int aReferenceValue,  
                 const std::string& aParameterName,
                 const std::string& aParameterGroupName = std::string(""),
                 const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is less than the
/// reference value.  Otherwise an error message is generated.  This
/// function behaves like the other inequality checking functions.
/// See the chkGreaterThan documentation for more information.  \sa
/// chkGreaterThan
SG_API
bool chkLessThan(const BlockDescriptor& aBlock,
                 const double aValueToTest,
                 const double aReferenceValue,  
                 const std::string& aParameterName,
                 const std::string& aParameterGroupName = std::string(""),
                 const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is less than or equal
/// to the reference value.  Otherwise an error message is generated.
/// This function behaves like the other inequality checking
/// functions.  See the chkGreaterThan documentation for more
/// information.  \sa chkGreaterThan
SG_API
bool chkLessThanOrEqual(const BlockDescriptor& aBlock,
                        const int aValueToTest,
                        const int aReferenceValue,  
                        const std::string& aParameterName,
                        const std::string& aParameterGroupName = std::string(""),
                        const std::string& reason = std::string(""));

/// \brief Verifies that the parameter's value is less than or equal
/// to the reference value.  Otherwise an error message is generated.
/// This function behaves like the other inequality checking
/// functions.  See the chkGreaterThan documentation for more
/// information.  \sa chkGreaterThan
SG_API
bool chkLessThanOrEqual(const BlockDescriptor& aBlock,
                        const double aValueToTest,
                        const double aReferenceValue,  
                        const std::string& aParameterName,
                        const std::string& aParameterGroupName = std::string(""),
                        const std::string& reason = std::string(""));

/// The chkRangeInclusive method verifies that aValueToTest is greater than
/// or equal to aMinValue and less than or equal to aMaxValue.  The method reports an error
/// if either condition is not met.  The generated error message states that aParamterName
/// has value aValueToTest, but must be between aMinValue and aMaxValue.  Additional reason text
/// may be provided to explanation why the parameter value must be between the two values.
/// The function will return false if the test fails and an error is generated.  Otherwise,
/// it will return true.
/// \param aBlock If the condition is not met, the error will be reported on the given block.
/// \param aMinValue The minimum value that the parameter is allowed to have.
/// \param aMaxValue The maximum value that the parameter is allowed to have.
/// \param aValueToTest The value of the parameter that is being tested.
/// \param aParameterName The name of the parameter that is being tested.
/// \param reason Additional text that will be included in the generated error message.
/// \sa chkGreaterThan chkRangeInclusive
/// \brief Verifies that the parameter's value is in the given range.  Otherwise an error message is generated.
SG_API
bool chkRangeInclusive(const BlockDescriptor& aBlock,
                       const int aMinValue,
                       const int aMaxValue,
                       const int aValueToTest,
                       const std::string& aParameterName,
                       const std::string& reason = std::string(""));

/// The chkRangeInclusive method verifies that aValueToTest is greater than
/// or equal to aMinValue and less than or equal to aMaxValue.  The method reports an error
/// if either condition is not met.  The generated error message states that aParamterName
/// has value aValueToTest, but must be between aMinValue and aMaxValue.
/// If the aMinValue or aMaxValue has a special meaning (eg. it is generated by a particular
/// equation), the value description field may be used to provide more explanation of the
/// significance of those values. Additional reason text may be provided to explanation why
/// the parameter value must be between the two values.  The function will return false if
/// the test fails and an error is generated.  Otherwise, it will return true.
/// \param aBlock If the condition is not met, the error will be reported on the given block.
/// \param aMinValue The minimum value that the parameter is allowed to have.
/// \param aMaxValue The maximum value that the parameter is allowed to have.
/// \param aValueToTest The value of the parameter that is being tested.
/// \param aParameterName The name of the parameter that is being tested.
/// \param minValueDescription The explanation of the meaning of the minimum value.
/// \param maxValueDescription The explanation of the meaning of the maximum value.
/// \param reason Additional text that will be included in the generated error message.
/// \sa chkRangeInclusive
/// \brief Verifies that the parameter's value is in the given range.  Otherwise an error message is generated.
SG_API
bool chkRangeInclusive(const BlockDescriptor& aBlock,
                       const double aMinValue,
                       const double aMaxValue,
                       const double aValueToTest,
                       const std::string& aParameterName,
                       const std::string& minValueDescription = std::string(""),
                       const std::string& maxValueDescription = std::string(""),
                       const std::string& reason = std::string(""));
 
        
/// The chkLatEnRst method verifies that if the block has an enable and/or reset port,
/// then the latency is greater than zero.  It also verifies that the latency is greater
/// than or equal to zero and less than MAX_LATENCY.  An error is reported if any of these
/// conditions are not met.
/// \param aBlock If the settings are illegal, the error will be reported on the given block.
/// \param latency The value of the latency parameter that will be tested.
/// \param hasEnable The param should be set to true if the block has an enable port.
/// \param hasReset The param should be set to true if the block has a reset port.
/// \sa chkLatency
/// \brief Verifies that the latency setting is legal given the existence of the enable and reset ports.
SG_API
void chkLatEnRst(const BlockDescriptor& aBlock,
                 int latency,
                 bool hasEnable,
                 bool hasReset);

/// The checkEnTypeAndRate method verifies that the type and rate of aPort are legal.
/// It ensures that aPort has a boolean SysgenType and that the SysgenRate of aPort
/// is an integer multiple of the SysgenRate of inPort (the greatest common denominator of
/// the SysgenRates on the inports if more than one inport is spcified). An error is reported
/// if any of these conditions are not met.
/// \param aBlock If the settings are illegal, the error will be reported on the given block.
/// \param aPort The port to check.  Typically, an enable or reset port.
/// \param inPort The port whose rate will be tested against aPort's rate.
/// \brief Verifies that the type and rate of the enable or reset port are legal relative to the inPort.
SG_API
void checkEnTypeAndRate(const BlockDescriptor& aBlock, 
                        const PortDescriptor* aPort,
                        const PortDescriptor* inPort);


/// \brief Verifies that the type and rate of the enable or reset port are legal relative to the inPorts.
/// \sa checkEnTypeAndRate
SG_API
void checkEnTypeAndRate(const BlockDescriptor& aBlock, 
                        const PortDescriptor* aPort,
                        const PortDescriptor* inPort1,
                        const PortDescriptor* inPort2);

/// \brief Verifies that the type and rate of the enable or reset port are legal relative to the inPorts.
/// \sa checkEnTypeAndRate
SG_API
void checkEnTypeAndRate(const BlockDescriptor& aBlock, 
                        const PortDescriptor* aPort,
                        const PortDescriptor* inPort1,
                        const PortDescriptor* inPort2,
                        const PortDescriptor* inPort3);

/// chkIfAllowedType - Checks If the allowed type(s) match the type of
/// portToChk, if they do not, an error is added to aBlock.
/// checkEnTypeAndRate - Check the type and rate of aPort.
/// Ensures that aPort is boolean, otherwise an error is added
/// to aBlock, reporting the failure.  Ensures that the rate of aPort
/// is an integer multiple of the rate of other relevant ports on the
/// block.  Otherwise an error is added to aBlock, reporting the
/// failure.
SG_API
void checkEnTypeAndRate(const BlockDescriptor& aBlock, 
                        const PortDescriptor* aPort,
                        const std::vector<const PortDescriptor*>& portsForGCD);

/// The checkEnOrRstTypeAndRate method verifies that the type and rate of aPort is legal.
/// It ensures that aPort is a boolean SysgenType and that the SysgenRate of aPort
/// is an integer multiple of the SysgenRate of all other ports in aBlock excluding any ports called
/// reset, rst, enable, or en.  An error is reported if any of these conditions are not met.
/// \param aBlock If the settings are illegal, the error will be reported on the given block.
/// \param aPort The port to check.  An enable or reset port.
/// \ brief Verifies that the type and rate of aPort (an enable/reset port) is legal relative to aBlock's other ports.
SG_API
void checkEnRstTypeAndRate(const BlockDescriptor& aBlock, bool ignoreEn = false, bool ignoreRst = false);

#ifndef DOXYGEN_SHOULD_SKIP_THIS

/// This function is deprecated.  Use chkBitAndBinPt instead.

/// getTypeChkFixUfix - Get type and check if it is signed (Fix) or
/// unsigned (Ufix).  Also check that the number of bits, nBits is
/// greater than zero and that a validxz binary point, binPt is 
/// specified.  Failing these constraints results in an error being
/// added to aBlock, reporting the failure.  Additionally, the first
/// component of the returned tupple is returned as false, the second
/// component being the default SysgenType struct.
///
/// On sucessful checks, the first tupple component is returned true and
/// the second component contains the SysgenType struct formed by 
/// arithType, nBits, and binPt.
SG_API
std::pair<bool, Sysgen::SysgenType>
getTypeChkFixUfix(BlockDescriptor& aBlock,
                  const int arithType,
                  const int nBits,
                  const int binPt);
#endif


/// \brief Verfies that the currently selected device is in one of the supported families listed.
/// The checkDeviceFamilyIsSupported verfies that the device selected in the System Generator GUI
/// is in the list of supported device families.  If it is not, then it adds an error message to the
/// block and throws an exception.
/// \param aBlock The block that only supports the listed device families
/// \param supported_device_families A string array containing the names of all the device families that are supported by this block.  The last element in the array must be the NULL value.
/// \sa checkDeviceFamilyIsNotUnsupported
SG_API
void checkDeviceFamilyIsSupported(const BlockDescriptor& aBlock,
                                  const std::string& block_type,
                                  const char **supported_device_families,
                                  const std::string& additionalInfo = std::string(""));

/// \brief Verfies that the currently selected device is not in one of the unsupported families listed.
/// The checkDeviceFamilyIsNotUnsupported verfies that the device selected in the System Generator GUI
/// is not in the list of unsupported device families.  If it is, then it adds an error message to the
/// block and throws an exception.
/// \param aBlock The block that only supports the listed device families
/// \param unsupported_device_families A string array containing the names of all the device families that are not supported by this block.  The last element in the array must be the NULL value.
/// \sa checkDeviceFamilyIsSupported
SG_API
void checkDeviceFamilyIsNotUnsupported(const BlockDescriptor& aBlock,
                                       const std::string& block_type,
                                       const char **unsupported_device_families,
                                       const std::string& additionalInfo = std::string(""));

//@}
        
//! @name Netlisting Convenience Methods
//@{

#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// getRelPathHDLFile - get a string containing the HDL file and it's
/// relative path if it's contained in $SYSGEN/hdl from anEntityName.
/// anEntityName is the entity name contained in the HDL file and should
/// also be the file name minus extension.
SG_API
std::string getRelPathHDLFile(BlockDescriptor& aBlock,
                              const std::string& anEntityName);
#endif

/// The addPortGenerics method adds generics to the aBlock block descriptor for each port in the port descriptor vector.  It adds generics for the port's arithmetic type, width, and binary point.
/// \param aBlock The block where the generics will be added.
/// \param aPDVec The vector of port's whose SysgenTypes will be added to the block descriptor as generics.
/// \sa BlockDescriptor::addGeneric
/// \brief Adds generics to the block descriptor for each port in the port descriptor vector.
SG_API
void addPortGenerics(BlockDescriptor& aBlock,
                     const std::vector<const PortDescriptor*> aPDVec);

//addAXITdataPortWidthGenerics method adds tdata-width generic for each AXI TDATA-port in the port-descriptor vector,
//to the aBlock block-descriptor.
SG_API
void addAXITdataPortWidthGenerics(BlockDescriptor& aBlock,
               const std::vector<const PortDescriptor*> aPDVec);

/// The getTargetDirectory method obtains the current netlisting target directory.
/// \param full_path Boolean to indicate the if full_path is to be returned or whatever is being pointed to by the appropriate SysGen Token
SG_API
std::string getTargetDirectory(bool full_path);


/// \brief ?
SG_API
Sysgen::SysgenType fullPrecisionType(std::vector<const PortDescriptor*>& ports,
                                     bool containerFlag);


/// \brief Get the handle associated with the simulatio island
/// associated wth specified block handle
SG_API
double getSimulationIslandHandle(const double& block_handle);


/// \brief Verify that there are no NaNs in a double vector
SG_API
bool confirmNoNanInVector(std::vector<double>& vec, std::string& error);


SG_API
double getSimulationIslandHandle(const double& block_handle);

/// Packs use_ce_syn_keep into the model_globals
/// This attribute is used by the ClockWrapper to put a syn_keep constraint
/// on the clock enable nets.				
SG_API
void markCeNetsSynKeep();
#ifndef DOXYGEN_SHOULD_SKIP_THIS
/// addScriptFromXCO - Adds a netlisting script to aBlock given
/// aVectorOfXCOInstrs, aCoreName, and an entity name for the block's 
/// top level HDL file, anEntityName.
///
/// This method assumes the top level HDL file is contained in
/// $SYSGEN/hdl.
///
/// An override is provided if additional template key/pairs are
/// required to be set with the map, mapList.
SG_API
void addScriptFromXCO(BlockDescriptor& aBlock,
                      const std::string& aCoreName, 
                      const std::vector<std::string>& aVectorOfXCOInstrs,        
                      const std::string& anEntityName);
SG_API
void addScriptFromXCO(BlockDescriptor& aBlock,
                      const std::string& aCoreName, 
                      const std::vector<std::string>& aVectorOfXCOInstrs,
                      const std::string& anEntityName,
                      const std::map<std::string, std::string>& mapList);
SG_API
void addScriptFromXCO(BlockDescriptor& aBlock,
                      const std::string& aCoreName, 
                      const std::vector<std::string>& aVectorOfXCOInstrs,
                      const std::string& anEntityName,
                      const std::map<std::string, std::string>& mapList,
                      const std::string& fileName,
                      bool useNGCForVerilog);

SG_API
void configureLocalizedClockEnableGenerator(BlockDescriptor& bd, int factor);

SG_API
void addEntityAndFileForNetlisting(BlockDescriptor& bd, std::string ename,
                                   bool check_multi_clk = true);

SG_API
void addSgGrenerateCoresScriptFromXCO(
    BlockDescriptor& aBlock,
    const std::vector<std::string>& aVectorOfXCOInstrs);

SG_API
std::string obtainVhpcompExecutable();

SG_API
std::string obtainVlogcompExecutable();

SG_API
std::string obtainFuseExecutable(bool unwrapped = false);

SG_API
bool vhpcomp(const std::vector<std::string>& files);

SG_API
std::string computeIsimHash(const std::vector<std::string>& files, const std::map<std::string,int>& generics, AnyTable::AnyType templateInstructions = AnyTable::AnyType());

SG_API
void create_isim_project(const std::vector<std::string>& files);

SG_API
void create_isim_generics_args(const std::map<std::string, int>& generics, std::vector<std::string>& args);

SG_API
std::string fuse(const std::vector<std::string>& files, 
                 const std::map<std::string, int>& generics, 
                 const std::string& top_level_entity_name,
                 const std::string& sim_dll_name,
				 AnyTable::AnyType fileinstructions = AnyTable::AnyType());

typedef struct{
    char* parameterName; 
    void * paramValue;
} generics_t;


SG_API
void
convert_generics_struct_to_map(void* generics, int number_of_generics,std::map<std::string, int>& generics_map);


SG_API
std::string fuse(const std::vector<std::string>& files, 
            void* generics, 
            int number_of_generics,
                 const std::string& top_level_entity_name,
                 const std::string& sim_dll_name);


SG_API
bool clean_isim_work_dir(const std::string& tempdir);

SG_API
bool parse_for_isim_error(const std::string& isim_err);

SG_API
bool isUseISIM92Interface();

#endif


enum PRECISION_TYPE {FULL_PRECISION = 1, USER_DEFINED = 2};
enum OVERFLOW_TYPE {WRAP = 1, SATURATE = 2, THROWOVERFLOW = 3};
enum QUANTIZATION_TYPE {TRUNCATE = 1, ROUND = 2};

// Following two functions deal with Addsub, Multiplier etc blocks to decide
// whether extra conversion logic is required
SG_API
bool isSaturationHardwareRequired (const BlockDescriptor *bdptr, const SysgenType & full_prec);

SG_API
bool isRoundingHardwareRequired (const BlockDescriptor *bdptr, const SysgenType & full_prec);

SG_API
bool isVirtex6 (const BlockDescriptor &bd);

SG_API
bool isVirtex5 (const BlockDescriptor &bd);

SG_API
bool isVirtex4 (const BlockDescriptor &bd);

SG_API
bool isSpartan3A (const BlockDescriptor &bd);

SG_API
bool isSpartan3ADSP (const BlockDescriptor &bd);

SG_API
bool isSpartan6 (const BlockDescriptor &bd);

SG_API
bool isDeviceSupported(const BlockDescriptor& bd, const char* devicesSupported[]);

SG_API
bool isCurrentDeviceSupported (const BlockDescriptor& bd);

SG_API
bool isDerivedVirtex4 (const BlockDescriptor& bd);

SG_API
bool isDerivedVirtex5 (const BlockDescriptor& bd);

SG_API
bool isDerivedVirtex6 (const BlockDescriptor& bd);

SG_API
bool isDerivedSpartan6 (const BlockDescriptor& bd);

SG_API
bool isDerivedVirtex7 (const BlockDescriptor& bd);

SG_API
bool isDerivedKintex7 (const BlockDescriptor& bd);

SG_API
bool isDerivedArtix7 (const BlockDescriptor& bd);

SG_API
bool isDerivedZynq (const BlockDescriptor& bd);

SG_API
bool doesFPOCoreSupportDevice(const BlockDescriptor& bd);

//@}
} // namespace Utility


namespace BaseIP 
{
SG_API
bool deviceContainsXTremeDSPSlice(const BlockDescriptor& aBlock);

SG_API
bool deviceContainsDSP48E(const BlockDescriptor& aBlock);

SG_API
bool deviceContainsDSP48E1(const BlockDescriptor& aBlock);

SG_API
std::string getMultUtilsTclScriptPath(std::string ipKey);

SG_API
std::string getMultLatencyFunctionName(std::string ipKey);

SG_API
int getMultOptimumLatency(const BlockDescriptor& aBlock, std::string ipKey, const AnyTable::Sequence& script_arguments);

SG_API
void assertOptimumPipelining (const BlockDescriptor& bd, std::string ipKey, const AnyTable::Sequence& script_arguments, int latency, bool test_for_optimum_pipeline);

} // namespace BaseIP


} // namespace Sysgen

#endif // SYSGEN_BLOCK_UTIL_H
