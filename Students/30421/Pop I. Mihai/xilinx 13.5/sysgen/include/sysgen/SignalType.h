#ifndef _SIGNALTYPE_H_
#define _SIGNALTYPE_H_

#include "sysgen/sg_config.h"
#include <string>

namespace Sysgen {

/// \ingroup public_config
/// \brief Type specifier class
class SG_API SignalType {
    
private:
    // The NUM_BITS_TO_REPRESENT_WIDTH represents the number of bits to use in this class to store the value of
    // the _width and the _binary_point data members.  Therefore, if NUM_BITS_TO_REPRESENT_WIDTH is set to 12,
    // then the value of width or binary_point that can be stored in this class is 0-4095.
    static const int NUM_BITS_TO_REPRESENT_WIDTH=12;

public:

    /// The ArithmeticType enumeration is used in the SignalType class to represent part of the data type.
    /// The type UNKNOWN is used to indicate that no valid Arithmetic type has been assigned to the variable.
    /// The type xlBOOL is used to represent a boolean value.  Boolean values should also have width set
    /// to 1 and binary point set to 0.
    /// The type xlFIX is used to represent a fixed-point, signed value.
    /// The type xlUFIX is used to represent a fixed-point, unsigned value.
    /// The type xlFLOAT is used to represent a floating-point value in arbitrary precision format
    /// N_ARITHMETICTYPE will always be the last element in the enum and may be used as an indicator of how
    /// many elements are in the enum.
    enum ArithmeticType {UNKNOWN, xlBOOL, xlFIX, xlUFIX, xlFLOAT, N_ARITHMETICTYPE};

    /// \brief Default constructor.  Sets arithmetic type to UNKNOWN and width and binary point to 0.
    SignalType() : _atype(UNKNOWN), _width(0), _binary_point(0) {}

    /*! \brief Constructs a SignalType object with the arithmetic type, width, and binary point
     *  set to the values of the parameters.
     *
     *  \param in_type The arithmetic type value to use in the constructed SignalType object. 
     *  \param in_width The width value to use in the constructed SignalType object. 
     *  \param in_binary_point The binary point value to use in the constructed SignalType object.
     */
    SignalType(ArithmeticType in_type, int in_width, int in_binary_point) : 
        _atype(in_type), _width(in_width), _binary_point(in_binary_point) {
        _verifyInputRange(in_width, "width");
        _verifyInputRange(in_binary_point, "binary point");
    }

    /// \brief Accessor method to retrieve the arithmetic type field.
    ArithmeticType getArithmeticType(void) const { return (ArithmeticType)_atype; }

    /// \brief Accessor method to retrieve the width field.
    unsigned int getWidth(void) const { return _width; }

    /// \brief Accessor method to retrieve the binary_point field.
    unsigned int getBinaryPoint(void) const { return _binary_point; }

    /// \brief Mutator method to modify the value of the arithmetic type field.
    void setArithmeticType(ArithmeticType atype) { _atype = atype; }

    /// \brief Mutator method to modify the value of the width field.
    void setWidth(int width) { _verifyInputRange(width, "width"); _width = width; }

    /// \brief Mutator method to modify the value of the binary point field.
    void setBinaryPoint(int binpt) { _verifyInputRange(binpt, "binary point"); _binary_point = binpt; }

    /// Helper function to convert the ArithmeticType enum into a string representation. 
    /// It returns: UNKNOWN->"unknown" xlBOOL->"Bool" xlFIX->"Fix" xlUFIX->"UFix"
    /// The method will throw an exception for any other value of ArithmeticType, including N_ARITHMETICTYPE.
    /// \param t A value from the Arithmetic type enum that will be converted to a string.
    /// \sa ArithmeticType
    /// \brief Returns a string representation of the given ArithmeticType.
    static std::string arithmeticTypeToString(ArithmeticType t);

    /// The toString method returns a string representation of the SignalType.  It will produce output
    /// of the form: "Bool", "Fix_X_Y", "UFix_X_Y", "unknown", or "illegal" where X is the width
    /// value and Y is the binary point value.  "illegal" will be returned if the isLegal() method returns
    /// false.  This method may throw an exception if the arithmetic type is set to N_ARITHMETICTYPE.
    /// The output produced by this method may be parsed by the fromString method.
    /// \sa isLegalType fromString
    /// \brief Return a string representation of the data in the SignalType object.
    std::string toString() const;

    /// The fromString method takes a string representation of a SignalType, parses the string to extract
    /// the type, width, and binary point information and sets the fields in the object to the parsed values.
    /// The input strings may be of the form: "Bool", "Fix_X_Y", "UFix_X_Y" where X is the width value and Y
    /// is the binary point value.  This method may throw a std::logic_error exception if there is an error
    /// parsing the string format.  The input to this method is in the same format produced by the toString()
    /// method.
    /// \param stringofied_type The string that will be parsed to extract the type information.
    /// \sa toString
    /// \brief Sets the fields in the SignalType by parsing the string representation of the data.
    void fromString(const std::string& stringofied_type);

    /// The isLegalType method returns true if the data in this SignalType object satisfies the requirements
    /// imposed by System Generator on what a legal type is. A SignalType with ArithmeticType set to xlBOOL
    /// must have a width of one and binary point set to zero.  A SignalType with ArithmeticType set to xlFIX
    /// or xlUFIX must have a width greater than or equal to 1 and a binary point between 0 and width, inclusive.
    /// \sa reasonIllegal
    /// \brief Returns true if the field values meet System Generator's requirements.
    bool isLegalType() const;

    /// The reasonIllegal method returns a string that explains why the SignalType is not legal.  This
    /// method is useful for determining why isLegalType returns false.  If the type is legal, then an
    /// empty string will be returned.
    /// \sa isLegalType
    /// \brief Returns a string that explains why the SignalType is not legal.
    std::string reasonIllegal() const;

    /// \brief Does a field by field comparison of the two SignalTypes and returns true if all fields are identical.
    bool operator==(const SignalType& rhs) const;

    /// \brief Does a field by field comparison of the two SignalTypes and returns true if any fields are different.
    bool operator!=(const SignalType& rhs) const;

    /*! \brief Returns true if the SignalType on the left is "less than" the one on the right.
     *  Useful for storing SignalTypes in STL containers.
     *
     *  The less than operator returns true if the SignalType on the left of the operator is "less than"
     *  the one on the right of the operator.  The return value is determined by comparing the values in
     *  the fields in the order type, width, binary point.  This operator allows SignalTypes to be stored
     *  in sorted STL containers such as maps and sets.
     */
    bool operator<(const SignalType& rhs) const; 

private:
    /*! \brief Verifies that the value is within the legal range for the given field and throws
     *  an exception if it is not.
     */
    void _verifyInputRange(int val, const char *fieldname);

    /// The atype field stores the arithmetic type of the SignalType.  The value assigned to this field should
    /// be one of the values in the ArithmeticType enum.
    /// \sa ArithmeticType
    /// \brief Contains the arithmetic type
    unsigned int _atype:3;

    /// \brief The width field stores the width of the data (also known as the "number of bits").
    unsigned int _width:NUM_BITS_TO_REPRESENT_WIDTH;

    /*! \brief The binary_point field stores the location of the decimal point in the value.
     *  A binary_point of 0 indicates that the value is an integer.
     */
    unsigned int _binary_point:NUM_BITS_TO_REPRESENT_WIDTH;
};
}
#endif
