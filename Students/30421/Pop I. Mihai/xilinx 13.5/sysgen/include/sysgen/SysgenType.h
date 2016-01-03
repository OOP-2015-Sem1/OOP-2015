/*
 *  SysgenType.h
 *
 *  Copyright (c) 2004, 2005, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Manages bi-directional mapping from denumerable typeid to SysgenTypeInfo 
 */ 

#ifndef _SYSGENTYPE_H_
#define _SYSGENTYPE_H_

// Xilinx
#include "sysgen/sg_config.h"

// SL/Boost
#include <string>
#include <map>

namespace Sysgen {

    /// \ingroup public_config
    /// \brief Type specifier class
    struct SG_API SysgenType {
        
        /// The ArithmeticType enumeration is used in the Sysgen Type class to represent part of the data type.
        /// The type UNKNOWN is used to indicate that no valid Arithmetic type has been assigned to the variable.
        /// The type xlBOOL is used to represent a boolean value.  Boolean values should also have width set
        /// to 1 and binary point set to 0.
        /// The type xlFIX is used to represent a fixed-point, signed value.
        /// The type xlUFIX is used to represent a fixed-point, unsigned value.
        /// The type xlFLOAT is used to represent floating-point data with arbitrary precision.
        /// N_ARITHMETICTYPE will always be the last element in the enum and may be used as an indicator of how
        /// many elements are in the enum.
        enum ArithmeticType {UNKNOWN, xlBOOL, xlFIX, xlUFIX, xlFLOAT, N_ARITHMETICTYPE};

        /// \brief Default constructor.  Sets arithmetic type to UNKNOWN and width and binary point to 0.
        SysgenType() : 
            type(SysgenType::UNKNOWN),
            width(0),
            binary_point(0){}

        /// \param in_type The arithmetic type value to use in the constructed SysgenType object. 
        /// \param in_width The width value to use in the constructed SysgenType object. 
        /// \param in_binary_point The binary point value to use in the constructed SysgenType object.
        /// \brief Constructs a SysgenType object with the arithmetic type, width, and binary point set to the values of the parameters.
        SysgenType(ArithmeticType in_type, int in_width, int in_binary_point) : 
            type(in_type),
            width(in_width),
            binary_point(in_binary_point){}

        /// Helper function to convert the ArithmeticType enum into a string representation. 
        /// It returns: UNKNOWN->"unknown" xlBOOL->"Bool" xlFIX->"Fix" xlUFIX->"UFix" xlFLOAT->"float"
        /// The method will throw an exception for any other value of ArithmeticType, including N_ARITHMETICTYPE.
        /// \param t An value from the Arithmetic type enum that will be converted to a string.
        /// \sa ArithmeticType
        /// \brief Returns a string representation of the given ArithmeticType.
        static std::string arithmeticTypeToString(ArithmeticType t);

        /// The toString method returns a string representation of the SysgenType.  It will produce output
        /// of the form: "Bool", "Fix_X_Y", "UFix_X_Y", "float", "unknown", or "illegal" where X is the width
        /// value and Y is the binary point value.  "illegal" will be returned if the isLegal() method returns
        /// false.  This method may throw an exception if the arithmetic type is set to N_ARITHMETICTYPE.
        /// The output produced by this method may be parsed by the fromString method.
        /// \sa isLegalType fromString
        /// \brief Return a string representation of the data in the SysgenType object.
        std::string toString() const;

        /// The fromString method takes a string representation of a SysgenType, parses the string to extract
        /// the type, width, and binary point information and sets the fields in the object to the parsed values.
        /// The input strings may be of the form: "Bool", "Fix_X_Y", "UFix_X_Y" where X is the width value and Y
        /// is the binary point value.  This method may throw a std::logic_error exception if there is an error
        /// parsing the string format.  The input to this method is in the same format produced by the toString()
        /// method.
        /// \param stringofied_type The string that will be parsed to extract the type information.
        /// \sa toString
        /// \brief Sets the fields in the SysgenType by parsing the string representation of the data.
        void fromString(const std::string& stringofied_type);
        // For XFloat data type, XFloat_X_Y represents total number of X bits and Y fraction bits.
        // The actual precision is Y+1 bits.

        /// The isLegalType method returns true if the data in this SysgenType object satisfies the requirements
        /// imposed by System Generator on what a legal type is. A SysgenType with ArithmeticType set to xlBOOL
        /// must have a width of one and binary point set to zero.  A SysgenType with ArithmeticType set to xlFIX
        /// or xlUFIX must have a width greater than or equal to 1 and a binary point between 0 and width, inclusive.
        /// \sa reasonIllegal
        /// \brief Returns true if the field values meet System Generator's requirements.
        bool isLegalType() const;

        /// The reasonIllegal method returns a string that explains why the SysgenType is not legal.  This
        /// method is useful for determining why isLegalType returns false.  If the type is legal, then an
        /// empty string will be returned.
        /// \sa isLegalType
        /// \brief Returns a string that explains why the SysgenType is not legal.
        std::string reasonIllegal() const;

        /// \brief Does a field by field comparison of the two SysgenTypes and returns true if all fields are identical.
        bool operator==(const SysgenType& rhs) const;

        /// \brief Does a field by field comparison of the two SysgenTypes and returns true if any fields are different.
        bool operator!=(const SysgenType& rhs) const;

        /// The less than operator returns true if the SysgenType on the left of the operator is "less than"
        /// the one on the right of the operator.  The return value is determined by comparing the values in
        /// the fields in the order type, width, binary point.  This operator allows SysgenTypes to be stored
        /// in sorted STL containers such as maps and sets.
        /// \brief Returns true if the SysgenType on the left is "less than" the one on the right.  Useful for storing SysgenTypes in STL containers.
        bool operator<(const SysgenType& rhs) const; 

        /// \brief returns true if floating point data type is held
        bool isFloat() const;

        /// \brief returns fraction width if floating point data else returns -1
		int fractionWidth() const;
        /// The type field stores the arithmetic type of the SysgenType.  The value assigned to this field should
        /// be one of the values in the ArithmeticType enum.
        /// \sa ArithmeticType
        /// \brief Contains the arithmetic type
        int type;

        /// \brief The width field stores the width of the data (also known as the "number of bits").
        int width;

        /// \brief The binary_point field stores the location of the decimal point in the value.  A binary_point of 0 indicates that the value is an integer.
        int binary_point;
    };

    /// The SysgenTypeMap holds the mapping between a SysgenType id and a corresponding SysgenType object.  Each set of values for the SysgenType fields will have exactly one entry in the SysgenTypeMap and exactly one corresponding type id.
    /// The SysgenTypeMap class is built to optimize speed and memory usage for SysgenType objects.  A design may only use a small number of data types, but the internals of the System Generator software may contain many instances of SysgenType objects.  The SysgenTypeMap is a good solution to this problem since it allows the System Generator internals to store handles to SysgenType objects rather than storing the entire object.  SysgenType equality comparisons can be done via the handle rather than doing a field by field comparison on the SysgenType object.  The SysgenTypeMap will not consume much memory since there are only a small number of SysgenTypes in the design.
    /// brief The SysgenTypeMap allows SysgenType objects to be associated with a type id.  It provides methods to lookup the Type object based on the id and to look up the id based on the type object.
    class SG_API SysgenTypeMap {    
    public:
        /// The get_type method will return a read-only reference to the SysgenType object that corresponds
        /// to the given type id.  If no SysgenType object is associated with the id, then a runtime_error
        /// exception will be thrown.
        /// \param id The key used to look up the SysgenType in the map.
        /// \brief Returns a read-only reference to the SysgenType object associated with the given type id.  Throws an exception if there is no SysgenType associated with the id.
        static const SysgenType& get_type(int id); 

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        /// Returns stringofied representation of type corresponding to id
        static std::string get_type_string(int id); 
#endif

        /// The get_id method will return an id that corresponds to the given type SysgenType.  If the SysgenType object does not yet exist in the map, then it will be added to the map and the id for the new map entry will be returned.
        /// \param type The SysgenType to look up in the map.
        /// \brief Returns the id associated with the given SysgenType.  Adds the SysgenType to the map if it has not yet been added.
        static int get_id(const SysgenType& type); 

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        /// Returns typeid corresponding to type installs if no such id
        static int get_id(const std::string& type); 
        /// Install type corresponding to SysgenType
        static int install_type(const SysgenType& type); 
        /// Install type corresponding to stringofied type representation
        static int install_type(const std::string& type);
        /// Clear all installed types
        static void clear();
#endif
    private:
        // storage type lookup
        typedef std::map<int, SysgenType> type_lookup_t;
        static type_lookup_t& type_lookup();
        // storage id lookup
        typedef std::map<SysgenType, int> id_lookup_t;
        static id_lookup_t& id_lookup(); 
    };
}

#endif // _SYSGENTYPE_H_
