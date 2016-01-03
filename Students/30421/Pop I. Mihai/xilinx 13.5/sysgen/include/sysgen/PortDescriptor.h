/*
 *  PortDescriptor.h
 *
 *  Copyright (c) 2004, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  System Generator port descriptor interface.
 */

#ifndef _PORTDESCRIPTOR_H_
#define _PORTDESCRIPTOR_H_

// Xilinx
#include "sysgen/sg_config.h"
#include "sysgen/SysgenType.h"
#include "sysgen/SysgenRate.h"
#include "sysgen/SysgenConstants.h"

// SL
#include <string>
#include <cassert>

// Forward declarations:
namespace Sysgen {
    class PortDescriptorImpl;
    class PTable;
}

namespace Sysgen {

    /// \ingroup includes
    /// \ingroup descriptors
    /// \ingroup public_config
    /// \brief PortDescriptor public interface
    class SG_API PortDescriptor
    {
    public:

        /// The Direction enum describes whether data comes into the block via the port (INPUT) or
        /// out of the block via the port (OUTPUT).  The direction may be undefined until a
        /// direction has been specified by calling the setDirection method.  N_DIRECTION will
        /// always be the last element in the enum and may be used as an indicator of how many
        /// elements are in the enum.  DIRECTION_UNDEFINED and N_DIRECTION are not valid directions
        /// and should never be used as an argument to the setDirection function.
        /// \brief Enumerated type that represents the direction that data flows through port.
        enum Direction { DIRECTION_UNDEFINED, INPUT, OUTPUT, INOUT, N_DIRECTION};

        /// The HDLType enum is used to determine how to represent the port data type in HDL.
        /// STD_LOGIC and STD_LOGIC_VEC correspond to the HDL types of the same name.
        /// STD_GENERIC corresponds to a STD_LOGIC_VECTOR with width parameterized by a generic.
        /// USER_DEFINED causes the HDL data type to be set to the data type specified by the 
        /// argument to setHDLTypeString.  N_HDLTYPE will always be the last element in the enum
        /// and may be used as an indicator of how many elements are in the enum.  HDLTYPE_DEFAULT
        /// and N_HDLTYPE are not valid HDL types and should never be used as an argument to the
        /// setHDLType function.
        /// \brief Enumerated type that determines how the port data type in the HDL representation of the design.
        enum HDLType   { HDLTYPE_DEFAULT, STD_LOGIC, STD_LOGIC_VEC, STD_GENERIC, USER_DEFINED, N_HDLTYPE};

        /// The HDLTie enum is used to indicate that a port should always be connected to one of 
        /// GND, VCC, HIGH_IMPEDENCE, PIN, or CE.  Ports connected to GND will always receive a data value
        /// of 0 in HDL.  Ports connected to VCC will always receive a data value of 1 in HDL.
        /// Ports connected to HIGH_IMPEDENCE will have a data value of 'Z' in HDL.  
        /// Ports tied to PIN will be floated to the top of the design and appear as an
        /// external ping.  Ports connected to CE will be connected to
        /// the CE net of like period. N_HDLTIE will
        /// always be the last element in the enum and may be used as an indicator of how many elements
        /// are in the enum.  N_HDLTIE should never be used as an argument to setHDLTie.
        /// \brief Enumerated type that allows ports to be connected to specific nets in the HDL representation of the design.
        enum HDLTie {GND, VCC, HIGH_IMPEDENCE, PIN, CE, N_HDLTIE};

        //! @name Constructors/Desctructors
        //@{
        /// 
#ifndef DOXYGEN_SHOULD_SKIP_THIS
        /// Construct with shared pointer to impl
        PortDescriptor(PortDescriptorImpl* port_desc_impl);
        /// Destructor
        virtual ~PortDescriptor();
#endif
        //@}

        //! @name Name & Icon Label
        //@{
        /// The setName method sets the name property on the port to the value of the name
        /// parameter.  The name is used internally to uniquely identify the port within the block
        /// (for use with function calls like BlockDescriptor::getPort).  The name will also be 
        /// used to identify the port in error messages and in generated HDL files.
        /// If no icon name has been set with the setIcon call, then this name will also be used
        /// as the port name on the block icon.
        /// \param name The name to assign to the port.
        /// \brief Sets the port name.
        void setName(const std::string& name);

        /// The getName method returns the name that was set by the last call to setName.
        /// \brief Gets the port name.
        const std::string& getName(void) const;

        /// The setIcon method sets the port label that is displayed on the simulink block icon.
        /// \param icon The name to display on the block icon.
        /// \brief Set the port name that appears on the block icon in the diagram.
        void setIcon(const std::string& icon);

        /// The getIcon method reads the port label that is displayed on the simulink block icon.
        /// \brief Get the port name that appears on the block icon in the diagram.
        const std::string& getIcon(void) const;

        /// The getHandle method returns a globally unique identifier for the port.  Every port
        /// on every block and every block will all have unique handle IDs.
        /// \brief Gets a globally unique identifier that is used internally to identify the port.
        double getHandle() const;

        //@}

        //! @name Polarity
        //@{

        /// The setDirection method is used to set the direction property on the port to
        /// indicate that the port is either an INPUT or an OUTPUT port.
        /// \param direction The direction to assign to the port.
        /// \brief Sets the direction of the port to either INPUT or OUTPUT.
        void setDirection(Direction direction);

        /// The setDirection method is used to set the direction property on the port to
        /// indicate that the port is either an INPUT or an OUTPUT port.  The direction string
        /// must be set to the string "in" or "out".
        /// \param direction The direction to assign to the port.
        /// \brief Sets the direction of the port to either "in" or "out".
        void setDirection(const std::string& direction);

        /// The getDirection method will return the Direction set by the last call to setDirection.
        /// \brief Gets the direction of the port.
        Direction getDirection(void) const;

        /// The getDirectionString method will return a string that represents the value of the
        /// direction property.  The possible return values are "in", "out", or "undefined".
        /// \brief Gets the direction of the port as a string.
        const std::string& getDirectionString(void) const;

        /// The isInport method will return true if the port direction is set to INPUT.
        /// \brief Returns true if the port direction is set to INPUT.
        bool isInport(void) const;

        /// The isOutport method will return true if the port direction is set to OUTPUT.
        /// \brief Returns true if the port direction is set to OUTPUT.
        bool isOutport(void) const;

        /// The isInoutport method will return true if the port direction is set to INOUT.
        /// \brief Returns true if the port direction is set to INOUT.
        bool isInoutport(void) const;

        //@}

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        //! @name Index
        //@{
        ///   Port index 0 to (N-1)
        int getIndex(void) const;
        //@}
#endif

        //! @name Type
        //@{

        /// The setType method assigns a SysgenType to the port to indicate the number of bits,
        /// binary point, and type (signed/unsigned/bool), of the data that passes through the
        /// port.  See the SysgenType documentation for more information on constructing and
        /// using SysgenType objects.
        /// \param type The SysgenType object to assign to the port.
        /// \brief Sets the port's data type to the given SysgenType.
        void setType(const SysgenType& type);

        /// The setType method assigns a SysgenType to the port to indicate the number of bits,
        /// binary point, and type (signed/unsigned/bool), of the data that passes through the
        /// port.  The type_id is used to indicate which SysgenType object in the SysgenTypeMap
        /// to use as the data type on the port.  See the SysgenType and SysgenTypeMap documentation 
        /// for more information on using SysgenType ids.
        /// \param type_id The SysgenTypeMap key associated with the type value to assign to the port.
        /// \brief Sets the port's data type to the SysgenType corresponding to the given type_id.
        void setType(int type_id);

        /// The setType method assigns a SysgenType to the port to indicate the number of bits, 
        /// binary point, and type (signed/unsigned/bool), of the data that passes through the
        /// port.  The SysgenType object will be constructed from the string representation of
        /// the type using the SysgenType::fromString method.  See the SysgenType documentation
        /// for more information on how SysgenType objects are constructed from strings.
        /// \param typeName The string representation of the SysgenType to assign to the port.
        /// \brief Sets the port's data type to the SysgenType that is constructed by parsing the typeName string.
        void setType(const std::string& typeName);

        /// The getType method returns a read only reference to the SysgenType object that was set
        /// by the last call to setType.
        /// \brief Gets the port's data type.
        const SysgenType& getType() const;

#ifndef DOXYGEN_SHOULD_SKIP_THIS
/*
        /// The getType method returns a mutable reference to the SysgenType object.  This method
        /// allows convenience methods on the SysgenType object to be used to modify the PortDescriptor's
        /// type property.
        /// \brief Gets a reference to the port's data type to allow easy modification of the SysgenType's data.

        // AB: This function would be nice to have but it is difficult to implement currently due to the fact that the port descriptor only stored an index into the SysgenTypeMap and we are not able to modify entries in the SysgenTypeMap.
        SysgenType& getType();
*/
#endif

        /// The getTypeID method returns the SysgenTypeMap id of the SysgenType object that was set
        /// by the last call to setType.
        /// \brief Gets the type id that corresponds to the port's data type.
        int getTypeID() const;

        /// The getTypeString method returns a string that represents the SysgenType object that was 
        /// set by the last call to setType.  The string is constructed by the SysgenType::toString
        /// method.
        /// \brief Gets the port's data type as a string.
        std::string getTypeString(void) const;

        //@}

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        // AB: These convenience methods are shortcuts for editing fields in the SysgenType object
        // associated with the PortDescriptor.  We hope to remove this interface in 8.2, so you should
        // stop using it.  To modify the SysgenType or get data from the SysgenType, use the setType or
        // getType method instead.

        //! @name Type Convenience Methods
        //@{
        /// The setArithmeticType method sets the type field on the SysgenType property that
        /// represents the port's data type.
        /// \brief Sets the arithmetic type field of the port's data type.
        void setArithmeticType(SysgenType::ArithmeticType type);  // Deprecated.  Use setType instead.
        /// The getArithmeticType method returns the type field on the SysgenType property that
        /// represents the port's data type.
        /// \brief Returns the arithmetic type field of the port's data type.
        SysgenType::ArithmeticType getArithmeticType(void) const; // Deprecated.  Use getType instead.
#ifndef DOXYGEN_SHOULD_SKIP_THIS
        /// Gets the arithmetic type that has been converted to a HDL language specific string
        /// that can be conveniently passed to a call to BlockDescriptor::addGeneric
        std::string getArithmeticTypeString(void) const; // Deprecated.  Use getType instead.
#endif
        /// The setWidth method sets the width field (also known as the "number of bits") on 
        /// the SysgenType property that represents the port's data type.
        /// \brief Sets the width field of the port's data type.
        void setWidth(int w); // Deprecated.  Use setType instead.
        /// The getWidth method returns the width field (also known as the "number of bits") on
        /// the SysgenType property that represents the port's data type.
        /// \brief Returns the width field of the port's data type.
        int getWidth(void) const; // Deprecated.  Use getType instead.
        /// The setBinpt method sets the binary point field on the SysgenType property that
        /// represents the port's data type.
        /// \brief Sets the binary_point field of the port's data type.
        void setBinpt(int bp); // Deprecated.  Use setType instead.
        /// The getBinpt method returns the binary point field on the SysgenType property that
        /// represents the port's data type.
        /// \brief Returns the binary_point field of the port's data type.
        int getBinpt(void) const; // Deprecated.  Use getType instead.
        ///  Make Type=xlBOOL, width=1, binpt=0
        void makeBool(void); // Deprecated.  Use setType instead.
        ///  Is type:Type=xlBOOL, width=1, binpt=0
        bool isBool(void) const; // Deprecated.  Use getType instead.
        ///  Make type SIGNED, width binpt are unchanged
        void makeSigned(void); // Deprecated.  Use setType instead.
        ///  Is type SIGNED
        bool isSigned(void) const; // Deprecated.  Use getType instead.
        ///  Make type SIGNED, width binpt are unchanged
        void makeUnsigned(void); // Deprecated.  Use setType instead.
        ///  Is type UNSIGNED
        bool isUnsigned(void) const; // Deprecated.  Use getType instead.
        ///  Make type XFloat
        void makeXFloat(void);
        ///  Is type XFloat
        bool isXFloat(void) const; // Deprecated.  Use getType instead.
        ///  Make type UNKNOWN
        void makeTypeUnknown(); // Deprecated.  Use setType instead.
        /// Known type
        bool isTypeKnown(void) const; // Deprecated.  Use getType instead.
        //@}
#endif

        //! @name Rate
        //@{
        /// The setRate method assigns a SysgenRate to the port to indicate the period and offset
        /// that determines when data passes through the port.  See the SysgenRate documentation 
        /// for more information on constructing and using SysgenRate objects.
        /// \param rate The SysgenRate object to assign to the port.
        /// \brief Sets the port's data rate to the given SysgenRate.
        void setRate(const SysgenRate& rate);

        /// The getRate method returns a read only reference to the SysgenRate object that was set
        /// by the last call to setRate.
        /// \brief Gets the port's data rate.
        const SysgenRate& getRate() const;

        /// The setRate method assigns a SysgenRate to the port to indicate the period and offset
        /// that determines when data passes through the port. The rate_id is used to indicate which
        /// SysgenRate object in the SysgenRateMap to use as the data rate on the port.  See the
        /// SysgenRate and SysgenRateMap documentation for more information on using SysgenRate ids.
        /// \param rate_id The SysgenRateMap key associated with the rate value to assign to the port.
        /// \brief Sets the port's data rate to the SysgenRate corresponding to the given rate_id.
        void setRate(const int rate_id);

        /// The getRateID method returns the SysgenRateMap id of the SysgenRate object that was set
        /// by the last call to setRate.
        /// \brief Gets the rate id that corresponds to the port's data rate.
        int getRateID() const;
        //@}


#ifndef DOXYGEN_SHOULD_SKIP_THIS
        // AB: These convenience methods are shortcuts for editing fields in the SysgenRate object
        // associated with the PortDescriptor.  We hope to remove this interface in 8.2, so you should
        // stop using it.  To modify the SysgenRate or get data from the SysgenRate, use the setRate or
        // getRate method instead.

        //! @name Rate Convenience Methods
        //@{
        /// Sets SysgenRate.period
        void setPeriod(const double& period); // Deprecated.  Use setRate instead.
        /// Get SysgenRate.period
        const double& getPeriod() const; // Deprecated.  Use getRate instead.
        /// Sets SysgenRate.period
        void setOffset(const double& period); // Deprecated.  Use setRate instead.
        /// Get SysgenRate.period
        const double& getOffset() const; // Deprecated.  Use getRate instead.
        /// Set period to constant
        void makeRateConstant(void); // Deprecated.  Use setRate instead.
        /// Is period constant
        bool isRateConstant(void) const; // Deprecated.  Use getRate instead.
        /// Set period to unknown
        void makeRateUnknown(void); // Deprecated.  Use setRate instead.
        /// Period known
        bool isRateKnown(void) const; // Deprecated.  Use getRate instead.
        //@}
#endif

        //! @name Netlister & Simulation
        //@{
        /// The setHDLName method is used to set the name that will be used to identify
        /// the port in the generated HDL netlist.  The name will appear in the HDL exactly
        /// as it appears in the name parameter, so the name must be a legal HDL name.
        /// \param name The name that will be used for the port in the generated HDL netlist.
        /// \brief Sets the name that will be used for the port in the generated HDL netlist.
        void setHDLName(const std::string& name);

        /// The getHDLName method will return the HDLName set by the last call to setHDLName.
        /// The return value will contain the name exactly as it will appear in the HDL, so
        /// the name should be a legal HDL name.
        /// \brief Gets the name of the port that will appear in the generated HDL netlist.
        const std::string& getHDLName() const;

#ifndef DOXYGEN_SHOULD_SKIP_THIS        
        /// \brief Sets the name of the file where gateway data will be generated when netlisting with the generate testbench option set.
        void setGatewayFileName(const std::string& value);
        /// \brief Gets the name of the file where gateway data will be generated when netlisting with the generate testbench option set.
        std::string getGatewayFileName() const;
#endif

        /// The makeSimulationOnly method instructs System Generator to ignore this port
        /// (and blocks that do not do anything other than drive this port) during generation
        /// of HDL netlists.  Ports that are set to simulation only will not appear in generated 
        /// HDL netlists.
        /// \brief Causes this port to not appear in generated HDL netlists but to continue to be used in Simulink simulations.
        void makeSimulationOnly(void);

        /// The isSimulationOnly method will return true if the port is going to be ignored during
        /// generation of HDL netlists.  The method will return true if makeSimulationOnly has been
        /// called on the port.
        /// \brief Returns true if makeSimulationOnly has been called on the port.
        bool isSimulationOnly(void) const;

        /// The makeHDLOnly method instructs System Generator to ignore this port (and blocks that
        /// do not do anything other than drive this port) when simulating the design in the Simulink
        /// environment.  Ports that are set to HDL only will appear in generated HDL netlists but
        /// will not affect simulation results in the Simulink environment.
        /// \brief Causes this port to not be used in Simulink simulations but still appear in generated HDL netlists.
        void makeHDLOnly(void);

        /// The isHDLOnly method will return true if the port is going to be ignored during
        /// simulation in the Simulink environment.  The method will return true if makeHDLOnly has been
        /// called on the port.
        /// \brief Returns true if makeHDLOnly has been called on the port.
        bool isHDLOnly(void) const;

        /// When setAllowUndriven is invoked with the value true, it will prevent a DRC error from
        /// being generated when there is no net connected to the input port.  By default, a DRC
        /// error will be generated for all unconnected input ports on System Generator blocks.
        /// This method has no effect on output ports since they are not required to be connected to a net.
        /// \param value Set to true to allow undriven nets, false to report errors on undriven nets.
        /// \brief Determines if a DRC error should be generated if the input port is not connected to a net.
        void setAllowUndriven(bool value);

        /// The isAllowUndriven method will return true if the port does not report a DRC errors when this
        /// input port is not connected to a net.  This method is meaningless for output ports since they
        /// are never required to be connected to a net.  This method will return the value set by the last
        /// call to setAllowUndriven on this port.
        /// \brief Returns true if it is legal for the input port to not be connected to a net.
        bool isAllowUndriven(void) const;

        /// When setAllowExternallyLoaded is invoked with the value true, the port is ignored by the
        /// DRC to check if the port is directly connected to a non Xilinx block without going through
        /// a gateway out. This method has no effect on input ports.
        /// \param value Set to true to allow undriven nets, false to report errors on undriven nets.
        /// \brief Determines if this port is ignored by the DRC to check if the port is directly connected to a non Xilinx block without going through a gateway out.
        void setAllowExternallyLoaded(bool value);

        /// The isAllowExternallyLoaded method will return true if the port is ignored by the DRC to check
        /// if the port is directly connected to a non Xilinx block without going through a gateway out. 
        /// This method is meaningless for input ports. This method will return the value set by the last
        /// call to setAllowExternallyLoaded on this port.
        /// \brief Returns true if it is legal for the output port to be directly connected to a non Xilinx block without going through a gateway out.
        bool isAllowExternallyLoaded(void) const;

        /// The setHDLType method is used to set the HDL type property on the port which determines
        /// how to represent the port data type in generated HDL netlists.  See the HDLType enum for
        /// information on the different types of data that may be used.
        /// \param type The HDLType enum value that will be assigned to the port.
        /// \brief Sets the HDL data type that will be used for the port in generated HDL netlist.
        void setHDLType(HDLType type);

        /// The setHDLType method is used to set the HDL type string property on the port which determines
        /// what string is used to represent the port data type in generated HDL netlists.  This method
        /// will set the HDLType to USER_DEFINED.  The port's data type in the HDL file will be an exact
        /// copy of the contents of the hdl_type parameter.
        /// \param hdl_type The string that will appear in generated HDL code to represent the port's type.
        /// \brief Sets the user defined HDL data type that will be used for the port in generated HDL netlist.  The hdl_type string will appear in the HDL file as the port's data type.
        void setHDLTypeString(const std::string& hdl_type);

        /// The getHDLType method will return the HDLType set by the last call to setHDLType.
        /// \brief Gets the HDL data type of the port.
        int getHDLType(void) const;

        /// The getHDLTypeString method returns the port's data type exactly as it will appear in a
        /// generated netlist in the specified synthesis_language format.  See Sysgen::Language for
        /// more information on legal values for supported synthesis languages.
        /// \param synthesis_language The Sysgen::Language enum value that determines how to format the port's data type.
        /// \brief Gets a string that contains the port's data type exactly as it will appear in a generated netlist in the specified language.
        std::string getHDLTypeString(const Sysgen::Language synthesis_language) const;

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        /// Port is externally connected.
        bool isExternallyConnected(void) const;
#endif

        /// The setHDLTie method sets the value of the Port's HDLTie property.  The value of the
        /// HDLTie property is used during netlist generation to connect the port to one of the
        /// HDLTie types (GND, VCC, HIGH_IMPEDENCE) in the generated HDL netlist.  Ports are
        /// initialized with HDLTie set to GND by default.  The HDL tie property will only affect
        /// netlisting if the port is set to be HDL only.  See makeHDLOnly and isHDLOnly.
        /// \param value The HDLTie enum value to assign to the port.
        /// \brief Sets which net the port will be tied to in the generated HDL netlist if isHDLOnly is true.
        void setHDLTie(HDLTie value);

        /// The getHDLTie method return the value of the Port's HDLTie property that was set by the
        /// last call to setHDLTie.  Ports are initialized with HDLTie set to GND by default.  The
        /// HDL tie property will only affect netlisting if the port is set to be HDL only.  See
        /// makeHDLOnly and isHDLOnly.
        /// \brief Returns the enum that determines which net the port will be tied to in the generated HDL netlist if isHDLOnly is true.
        HDLTie getHDLTie() const;

        /// The setAttribute method is used for giving additional instructions to the netlister
        /// that affect the way the port appears in the generated HDL netlist.  The legal
        /// attributes that may be passed to the netlister are described in the netlister documentation.
        /// \param key The name of the attribute to set.
        /// \param value The value to assign to the attribute.
        /// \brief Sets attributes that are used by the netlister to change the way that the port is generated in the HDL netlist.
        void setAttribute(const std::string& key, const PTable& value);

        /// Inidicate an error condition for the port on the user interface (irreversible)
        void indicateError() const;

        //@}

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        // Internal Use Only
        PortDescriptorImpl* getImplPtr() { return (_impl); };
        // Internal Use Only
        const PortDescriptorImpl* getImplPtr() const { return (_impl); };
#endif

    private:
        /// PortDescriptor objects can not be copied.
        /// The copy constructor is declared as private and not defined.
        PortDescriptor(const PortDescriptor* q){assert(0);}
        ///  PortDescriptor objects can not be copied.
        /// The copy constructor is declared as private and not defined.
        void operator=(const Sysgen::PortDescriptor* q){assert(0);}
        PortDescriptorImpl* _impl;

        // Explicitly dissallow the setRate argument from being converted from double to int.
        void setRate(const double bogus) { assert(0); }
    };
}
#endif // _PORTDESCRIPTOR_H_
