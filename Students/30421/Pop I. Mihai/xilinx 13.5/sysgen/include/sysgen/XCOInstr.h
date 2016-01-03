/*
 *  XCOInstr.h
 *
 *  Copyright (c) 2004-2007, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:    A class to construct, store, and manipulate
 *                  XCO instructions.
 */

#ifndef _XCOINSTR_H_
#define _XCOINSTR_H_

// Xilinx
#include "sysgen/sg_config.h"
#include "sysgen/SysgenType.h"

// SL
#include <string>
#include <vector>

#include <anytable/AnyTable.h>

namespace Sysgen {    
    class BlockDescriptor;   // Fwd. Decl.

    /// \ingroup includes
    /// \ingroup public_utility
    /// \brief Class for creating, storing, and manipulating XCO Instructions.    
    class SG_API XCOInstr
    {
    private:
        static const std::string _DEFAULT_RDX_PARAM_NAME;
        static const std::string _DEFAULT_VEC_PARAM_NAME;

    public:
        static const std::string &default_rdx_param_name();
        static const std::string &default_vec_param_name();

        //! @name Constructors/Desctructors
        //@{
        /// Constructor
        XCOInstr() {};
        /// Desctructor
        ~XCOInstr() {};
        //@}

        //! @name XCO Instruction Creation Methods
        //@{
        /// addComment - adds a Comment for XCO instruction; the instruction is
        /// formed by prepending "# " to aCommentLine.
        /// The comment string is useful in creating hash code based on the
        /// license of a pay core
        void addComment(const std::string& aCommentLine);

        /// addSelect - adds a SELECT XCO instruction; the instruction is
        /// formed by prepending "SELECT " to aSelectLine.
        void addSelect(const std::string& aSelectLine);

        /// addCSet - adds a CSET XCO instruction; the instruction is formed
        /// by prepending "CSET " to the string formed by concatenating 
        /// aProperty, " = ", and aPropertySetting.
        ///
        /// Overloaded methods exist below to allow construction from a 
        /// string and a string, a string and an int, a string and a bool, 
        /// and a string and a double.  Additionally, overloaded methods 
        /// allow for const char* to be passed in instead of std::string.
        void addCSet(const std::string& aProperty, const char* aPropertySetting);
        void addCSet(const std::string& aProperty, const std::string& aPropertySetting);
        void addCSet(const std::string& aProperty, int aPropertySetting);
        void addCSet(const std::string& aProperty, double aPropertySetting);
        void addCSet(const std::string& aProperty, bool aPropertySetting);
        void addCSet(const char* aProperty, const char* aPropertySetting);
        void addCSet(const char* aProperty, int aPropertySetting);
        void addCSet(const char* aProperty, double aPropertySetting);
        void addCSet(const char* aProperty, bool aPropertySetting);

        /// addCSetHex - adds a CSET XCO instruction; aPropertySetting is 
        /// quantized according to SysgenType with Rounding and Saturation and
        /// converted to a value in aRadix number system, which is then 
        /// assigned to aProperty using a CSET XCO instruction.
        ///
        /// Overloaded methods allow the passing of an integer or double value
        /// to aPropertySetting.
        ///
        /// For radix 2 numbers, the writeAllBits parameter can be set to true
        /// so that the string representation is all container bits (i.e. 2 -> 0010
        /// instead of 10). 
        void addCSetRdx(const std::string& aProperty, int aPropertySetting, const SysgenType& aType, int aRadix, bool writeAllBits = false);
        void addCSetRdx(const std::string& aProperty, double aPropertySetting, const SysgenType& aType, int aRadix, bool writeAllBits = false);

        /// addCSetRdxVec - adds a CSET XCO instruction; aPropertySetting is
        /// quantized according to SysgenType with Rounding and Saturation from a 
        /// vector of doubles or integers and then converted into a vector of
        /// aRadix values, eventually ending up as a comma delimited string of
        /// numbers.
        void addCSetRdxVec(const std::string& aProperty, const std::vector<int>& aPropertySetting, const SysgenType& aType, int aRadix,
                bool writeAllBits = false, const std::string& aRadixPropertyName = default_rdx_param_name(),
                const std::string& aVectorPropertyName = default_vec_param_name());
        void addCSetRdxVec(const std::string& aProperty, const std::vector<double>& aPropertySetting, const SysgenType& aType, int aRadix,
                bool writeAllBits = false, const std::string& aRadixPropertyName = default_rdx_param_name(), 
                const std::string& aVectorPropertyName = default_vec_param_name());
        void addCSetRdxVecDepth(const std::string& aProperty, const std::vector<int>& aPropertySetting, const SysgenType& aType, int aRadix,
                unsigned depth = 0, bool writeAllBits = false, const std::string& aRadixPropertyName = default_rdx_param_name(),
                const std::string& aVectorPropertyName = default_vec_param_name());
        void addCSetRdxVecDepth(const std::string& aProperty, const std::vector<double>& aPropertySetting, const SysgenType& aType, int aRadix,
                unsigned depth = 0, bool writeAllBits = false, const std::string& aRadixPropertyName = default_rdx_param_name(), 
                const std::string& aVectorPropertyName = default_vec_param_name());

        /// addSet - adds a SET XCO instruction; the instruction is formed
        /// by prepending "SET " to the string formed by concatenating 
        /// aProperty, " = ", and aPropertySetting.
        ///
        /// Overloaded methods exist below to allow construction from a 
        /// string and a string, a string and an int, a string and a bool, 
        /// and a string and a double.
        void addSet(const std::string& aProperty, const char* aPropertySetting);
        void addSet(const std::string& aProperty, const std::string& aPropertySetting);
        void addSet(const std::string& aProperty, int aPropertySetting);
        void addSet(const std::string& aProperty, double aPropertySetting);
        void addSet(const std::string& aProperty, bool aPropertySetting);
        
        /// getCoreNameAndXCOs - returns a pair containing a unique core name 
        /// and a vector of strings containing the XCO instructions.
        std::pair<std::string, const std::vector<std::string> > getCoreNameAndXCOs(
                BlockDescriptor& block,
                const std::string& core_abbreviation = std::string(""), 
                const std::string& core_version = std::string(""), 
                const std::string& cg_family_name = std::string(""),
                bool skipHash = false) const;
        const std::vector<std::string> getXCOs() const;

	/// Implementing interface to IPCore
	AnyTable::AnyType & getXCOsAnyTable ();

	void setIPName (const std::string ip_name);

	void setIPVersion (std::string ip_version);

	const std::string& getIPName () const;

	const std::string& getIPVersion () const;

    static void clearBlockCoreMap();

    static void printBlockCoreMap(std::string& fileName);


        //@}

    private:
        void addMemoryInitInstruction(const std::string& aProperty, const std::string& aPropertySetting, int aRadix,
            const std::string& aRadixPropertyName = default_rdx_param_name(),
            const std::string& aVectorPropertyName = default_vec_param_name());
        std::string convertNumberToQuantizedRdxStr(int aNum, const SysgenType& aType, int aRadix, bool writeAllBits = false);
        std::string convertNumberToQuantizedRdxStr(double aNum, const SysgenType& aType, int aRadix, bool writeAllBits = false);
        std::string convertVectorToQuantizedRdxStr(const std::vector<int>& anIntVector, const SysgenType& aType, int aRadix,
                bool writeAllBits = false, unsigned depth = 0);
        std::string convertVectorToQuantizedRdxStr(const std::vector<double>& aDblVector, const SysgenType& aType, int aRadix,
                bool writeAllBits = false, unsigned depth = 0);

        void truncateCoreName (const BlockDescriptor &bd, const std::string & Sel_Instr, std::string & core_name) const;

        void truncateBlockTypeStr (std::string & block_type) const;

        std::string _select_instr;
        std::vector<std::string> _xco_instrs;
        std::vector<std::vector<std::string> > _xco_memory_instrs;

	// Added for IPCore interface
	AnyTable::AnyType _xco_instr_anytable;
	std::string _ip_name;
	std::string _ip_version;

    static AnyTable::AnyType _blockCoreMap;

    };
}
#endif // _XCOINSTR_H_
