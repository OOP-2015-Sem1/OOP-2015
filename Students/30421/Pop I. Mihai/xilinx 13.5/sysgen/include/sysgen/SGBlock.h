/*
 *  SGBlock.h
 *
 *  Copyright (c) 2006, Xilinx, Inc.  All Rights Reserved.
 *
 */
#ifndef SGBLOCK_H
#define SGBLOCK_H

#include "sysgen/BlockDescriptor.h"
#include "sysgen/XCOInstr.h"
#include "anytable/AnyTable.h"

namespace Sysgen {
    /// \ingroup includes
    /// \ingroup descriptors
    /// \ingroup public_config
    /// \brief A class for completely describing a Sysgen block, appearance, ports, simulation, and hardware 
    ///        realization.  For example implementations, look at the Delay, Inverter, FIFO, or RS Encoder blocks.
    ///        C-based simulation is not yet supported.
    class SG_API SGBlock : public BlockDescriptor
    {
        public:
            /// The CORE_LICENSE enumeration describes the types of licenses that a Xilinx Coregen Pay-Core can have.
            /// Licences include BEHAVIORAL_SIM_CORE_LICENSE, which only permits behavioral simulation of a core,
            /// HARDWARE_EVAL_CORE_LICENSE, which provides a realizable netlist of the core that is limited in
            /// performance, and finally PAID_CORE_LICENSE, which provides the full functionality of the core to the
            /// user.  INVALID_CORE_LICENSE is the default setting for uninitialized enumerations of this type.
            /// \brief CORE_LICENSE is an enumeration of the varieties of licenses possible for a Xilinx Coregen
            ///        Pay-Core.
            enum CORE_LICENSE {INVALID_CORE_LICENSE = -1, BEHAVIORAL_SIM_CORE_LICENSE = 0, HARDWARE_EVAL_CORE_LICENSE = 1, PAID_CORE_LICENSE = 2};
            

            /// @name Constructor
            //@{
            /// The SGBlock constructor constructs an SGBlock.
            /// \param enable_debug_override_variable a boolean argument that is set to false by default.
            ///        If this argument is set to true, the block will feature a variable called _xlcconfig
            ///        that is only modifiable in debug builds.
            /// \see _xlcconfig
            /// \brief The SGBlock constructs an SGBlock, with an optional debug mode variable called _xlcconfig.
            SGBlock(bool enable_debug_override_variable = false);
            //@}
            

            #ifndef DOXYGEN_SHOULD_SKIP_THIS
                virtual ~SGBlock();
            #endif /* DOXYGEN_SHOULD_SKIP_THIS */
            

            //! @name Sysgen Block Interface Methods.
            //@{
            /// The getBlockParameters method is used to get the value of parameters
            /// that appear in a Sysgen block's Simulink GUI (or block mask).  These parameters
            /// correspond to the parameters defined in the block's GUI xml file.
            /// \param userSettings is a data structure containing the parameters
            ///        defined in the block GUI, these can be obtained with calls
            ///        like int myparam = userSettings.getInt("myparam");
            /// \brief getBlockParameters is used to explicitly get the parameters of a Sysgen Block from the
            ///        userSettings data structure.
            virtual void getBlockParameters(const PTable& userSettings) = 0;

            /// The checkBlockParameters method is provided for checking the parameters
            /// that were obtained from the Sysgen block's Simulink GUI in the method
            /// getBlockParameters.
            /// \see getBlockParameters
            /// \brief Check the parameters obtained from a Sysgen Block.
            virtual void checkBlockParameters() = 0;

            /// \brief The setInputPorts method is provided for adding input ports to the block.
            virtual void setInputPorts() = 0;

            /// \brief The setOutputPorts method is provided for adding output ports to the block.
            virtual void setOutputPorts() = 0;

            /// \brief The setPortToPortLatencies method is provided for specifying the latencies
            ///        between input ports and output ports.
            virtual void setPortToPortLatencies() = 0;

            /// \brief The setSimulationType method is used to specify the method for simulation.
            /// \see simulateUsingMCode
            /// \see simulateUsingISIM
            /// \see simulateUsingCPP
            virtual void setSimulationType() = 0;

            /// \brief The setBlockIconAppearance can be used to specify the text in a Sysgen block's
            ///        icon.
            virtual void setBlockIconAppearance() {};

            /// \brief The processBlockParameters method can be used to perform miscellaneous operations
            ///        and setup with block parameters.  It is the last method called before periods and types
            ///        are propagated.
            virtual void processBlockParameters() {};
            //@}


            //! @name Sysgen Block Type and Period Propagation Behavior Methods.
            //@{
            /// \brief Defines how the type(s) of a Sysgen Block's input ports are propagated to its output ports.`
            virtual void propagatePortTypes() = 0;

            /// \brief Defines how the period(s) of a Sysgen Block's input ports are propagated to its output ports.
            virtual void propagatePortPeriods() = 0;
            
            /// \brief Checks the sanity of the type and period of the blocks input and/or output ports.
            virtual void checkPortTypesAndPeriods() const = 0;
            //@}


            //! @name Sysgen Block MCode Interface Methods
            //@{
            /// The parameterizeMCodeModel method is used to parameterize an M-Code model specified in a call to
            /// simulateUsingMCode.  The parameters are the arguments to the M-Code model.  They can be 
            /// parameterized by using the M-Code model convenience methods.
            /// \brief parameterizeMCodeModel is used for parameterizing the M-Code model specified in a call to
            ///        simulateUsingMCode.
            /// \see simulateUsingMCode
            /// \see setMCodeModelParam
            /// \see setMCodeModelPortPeriod
            /// \see tieMCodeModelInputLow
            /// \see tieMCodeModelInputHigh
            /// \see hideMCodeModelInport
            /// \see hideMCodeModelOutport
            virtual void parameterizeMCodeModel() {};
            //@}


            //! @name Sysgen Block Hardware Interface Methods
            //@{
            /// \brief checkHDLModelSupportsBlockParameters is used to ensure that an HDL model specified
            ///        in the setupHDL method with a call to useSysgenHDLEntity or useSysgenHDLEntityFromFile
            ///        is capable of supporting parameters obtained in the method getBlockParameters.
            /// \see setupHDL
            /// \see useSysgenHDLEntity
            /// \see useSysgenHDLEntityFromFile
            virtual void checkHDLModelSupportsBlockParameters() {};

            /// \brief checkCoreSupportsBlockParameters is used to ensure that a core specified in 
            ///        parameterizeCore is capable of supporting parameters obtained in the method
            ///        getBlockParameters.
            /// \see parameterizeCore
            virtual void checkCoreSupportsBlockParameters() {};

            /// \brief setRequiredClockSignals is used to specify the clock and clock enable signals
            ///        required by the Sysgen Block and the period of these signals.
            virtual void setRequiredClockSignals();

            /// \brief parameterizeCore is provided for parameterization of any core(s) upon which
            ///        the block is relying upon for its underlying hardware implementation.
            /// \param coreInstructions is a data structure that is used to hold Xilinx
            ///        Coregen Core Instructions that parameterize a core.
            /// \see checkCoreSupportsBlockParameters
            virtual void parameterizeCore(XCOInstr& coreInstructions) {};

            /// \brief setupHDL is a method that specifies how the HDL (Hardware Description Language)
            /// netlist will be realized from the block's parameters, including which HDL files or cores
            /// will be used in realizing the block.
            virtual void setupHDL();
            //@)
            
            
            //! @name Conveniences for Adding Ports
            //@{
            PortDescriptor* addBooleanInport(const std::string& name, const std::string& label = USE_NAME);
            PortDescriptor* addBooleanOutport(const std::string& name, const std::string& label = USE_NAME);
            
            PortDescriptor* addEnableInport(const std::string& name, bool exposePort = true, bool scalar = true, const std::string& label = USE_NAME);
            PortDescriptor* addResetInport(const std::string& name, bool exposePort = true, bool scalar = true, const std::string& label = USE_NAME);
            PortDescriptor* addAXIResetInport(const std::string& name, bool exposePort = true, bool scalar = true, const std::string& label = USE_NAME);

            PortDescriptor* addStaticTypeInport(const std::string& name, bool isSigned, int bits, int fractionalBits, const std::string& label = USE_NAME);
            PortDescriptor* addStaticTypeInport(const std::string& name, const SysgenType& sgt, const std::string& label = USE_NAME);
            PortDescriptor* addStaticTypeOutport(const std::string& name, bool isSigned, int bits, int fractionalBits, const std::string& label = USE_NAME);
            PortDescriptor* addStaticTypeOutport(const std::string& name, const SysgenType& sgt, const std::string& label = USE_NAME);
            
            PortDescriptor* addUnlabledInport(const std::string& name);
            PortDescriptor* addUnlabledOutport(const std::string& name);
            
            PortDescriptor* addInport(const std::string& name, const std::string& label = USE_NAME);
            PortDescriptor* addOutport(const std::string& name, const std::string& label = USE_NAME);

            PortDescriptor* addBooleanInportAsGroup(const int channel_group, const std::string& name, const std::string& label = USE_NAME);
            PortDescriptor* addBooleanOutportAsGroup(const int channel_group, const std::string& name, const std::string& label = USE_NAME);

            PortDescriptor* addInportAsGroup(const int channel_group, const std::string& name, const std::string& label = USE_NAME);
            PortDescriptor* addOutportAsGroup(const int channel_group, const std::string& name, const std::string& label = USE_NAME);
            //@}
            

            //! @name Conveniences for Getting Port Properties
            //@{
            SysgenType::ArithmeticType getPortType(const std::string& name) const;
            size_t getPortWidth(const std::string& name) const;
            size_t getPortFractionalBits(const std::string& name) const;
            double getPortPeriod(const std::string& name) const;
            //@}
            
            
            //!@name Conveniences for Setting Port Properties
            //@{
            void setPortArithProperties(const std::string& name, SysgenType::ArithmeticType type, size_t width, size_t fractionalBits);
            void setPortType(const std::string& name, SysgenType::ArithmeticType type);
            void setPortWidth(const std::string& name, size_t width);
            void setPortFractionalBits(const std::string& name, size_t fractionalBits);
            void setPortPeriod(const std::string& name, double period);
            //@}


            //!@name Conveniences for Propagating Port Properties
            //@{
            /// \brief propagateTypeFromPortToPort propagates the arithmetic properties (type, width, fractionalBits) of
            ///        input port port1Name to output port port2Name.
            /// \param port1Name an input port.
            /// \param port2Name an output port.
            void propagateTypeFromPortToPort(const std::string& port1Name, const std::string& port2Name);
            
            /// \brief propagatePeriodFromPortToPort propagates the period of input port port1Name to 
            ///        output port port2Name.
            /// \param port1Name an input port.
            /// \param port2Name an output port.
            void propagatePeriodFromPortToPort(const std::string& port1Name, const std::string& port2Name);
            //@}


            //! @name Conveniences for Error Checking
            //@{
            void errorIfPortXFloat(const std::string& name) const;
            void errorIfPortXFloat(const PortDescriptor* p) const;
            void errorIfPortNotSigned(const std::string& name) const;
            void errorIfPortNotSigned(const PortDescriptor* p) const;
            void errorIfPortNotUnsigned(const std::string& name) const;
            void errorIfPortNotUnsigned(const PortDescriptor* p) const;
            void errorIfPortBoolean(const std::string& name, bool allowFloat = true) const;
            void errorIfPortBoolean(const PortDescriptor* p, bool allowFloat = true) const;
            void errorIfInputPortsUnsampled(bool hasExplicitPeriod = false, const double& explicitPeriod = 0.0) const;
            void errorIfInputPortSetUnsampled(const std::vector<const PortDescriptor*>& inputsToExamine, bool hasExplicitPeriod = false, const double& explicitPeriod = 0.0) const;
            //@}
            

            //! @name Conveniences for Setting the Type of Simulation Used
            //@{
            void simulateUsingMCode(const std::string& modelName, bool simulationOnly = false);
            void simulateUsingISIM(bool useVHDLForTopLevel = true);
            void simulateUsingCPP();
            //@}


            //! @name Conveniences for Parameterizing MCode Models
            //@{
            void setMCodeModelParam(const std::string& parameter, bool value);
            void setMCodeModelParam(const std::string& parameter, int value);
            void setMCodeModelPortPeriod(const std::string& mcodePortName, const double& period);
            void tieMCodeModelInputLow(const std::string& mcodePortName, bool groundThisPort = true);
            void tieMCodeModelInputHigh(const std::string& mcodePortName, bool pullUpThisPort = true);
            void hideMCodeModelInport(const std::string& mcodePortName, bool inputTerminationValue);
            void hideMCodeModelInport(const std::string& mcodePortName, int inputTerminationValue);
            void hideMCodeModelOutport(const std::string& mcodePortName, bool hideThisPort = true);
            //@}
            

            //! @name Conveniences for Setting up a Block's HDL
            //@{
            void useSysgenHDLEntity(const std::string& hdlEntityName);
            void useSysgenHDLEntityFromFile(const std::string& hdlEntityName, const std::string& fileName);
            void setHDLParameter(const std::string& param, bool setting);
            void setHDLParameter(const std::string& param, size_t setting);
            void setHDLParameter(const std::string& param, int setting);
            void setHDLParameter(const std::string& param, const std::string& setting);
            CORE_LICENSE getCoreLicenseType(const std::string& coreXCDFilePath);
            std::string getCoreLicenseTypeString(const std::string& coreXCDFilePath);
            std::string addCoreInstance(const std::string& hdlEntity, 
                const std::map<std::string, std::string>& customMappings,
                const std::string& coreAbbreviation = "",
                const std::string& coreVersion = "",
                const std::string& coreDevice = "");
            std::string addCoreInstance(const std::string& hdlEntity, 
                const std::map<std::string, std::string>& customMappings,
                const std::string& fileName,
                bool implementVerilogWithNGC = false,
                bool includeCoreNameInHDLEntity = false,
                bool includeHashInHDLEntity = false,
                const std::string& coreAbbreviation = "",
                const std::string& coreVersion = "",
                const std::string& coreDevice = "");
            //@}
            
            
            //! @name Miscellaneous. Conveniences
            //@{
            const std::string getDevice();
            /// \brief setInPortsToValidateControlInportPeriods is used to specify a list of ports that will be 
            ///        used to validate the period of any input ports added with addEnableInport, addResetInport, 
            ///        or addControlInport.  This method should be called from checkPortTypesAndPeriods.
            /// \param ports is a vector of input port pointers.
            /// \see checkPortTypesAndPeriods
            void setInPortsToValidateControlInportPeriods(const std::vector<const PortDescriptor*>& ports) const;
            //@}

        protected:
            virtual void setupMCodeModel();

            bool _mcode_model_setup;

            bool _enable_debug_override_variable;
            std::string _xlcconfig;

        private:
            static const std::string USE_NAME;
            static const SysgenType BOOLSGT;

            static const std::string SGBLOCK_USAGE_ERR;
            static const std::string SGBLOCK_DEFINE_SRC_METHOD_MSG;
            static const std::string SGBLOCK_DEFINE_SH_METHOD_MSG;
            static const char* SGBLOCK_BAD_GETDEVICE_CALL_MSG;
            static const std::string SGBLOCK_BAD_PORTNAME_ERROR;
            static const std::string SGBLOCK_BAD_PORTORIENTATION_ERROR;

            static const std::string COREGENIF_ERR;
            static const std::string COREGENIF_CLASS_NOT_FOUND_MSG;
            static const std::string COREGENIF_CLASS;
            static const std::string COREGENIF_REVENUE_METHOD;

            #ifndef DOXYGEN_SHOULD_SKIP_THIS
                virtual void configureInterface();
                virtual void configureRateAndType();
                virtual void configurePostRateAndType() const;
                virtual void configureSimulation();
                virtual void configureNetlistInterface();
                virtual void configureNetlist();
            #endif /* DOXYGEN_SHOULD_SKIP_THIS */           

            void appendStaticInport(const PortDescriptor* p, const SysgenType& sgt);

            PortDescriptor* addBooleanPort(const std::string& name, const std::string& label, bool input);
            PortDescriptor* addBooleanPortAsGroup(const int channel_group, const std::string& name, const std::string& label, bool input);
            PortDescriptor* addControlInport(const std::string& name, const std::string& label, bool exposePort, bool scalar, bool gnd);
            PortDescriptor* addStaticTypePort(const std::string& name, const std::string& label, bool isSigned, int bits, int fractionalBits, bool input);
            PortDescriptor* addStaticTypePort(const std::string& name, const std::string& label, const SysgenType& sgt, bool input);
            PortDescriptor* addStaticTypePortAsGroup(const int channel_group, const std::string& name, const std::string& label, const SysgenType& sgt, bool input);



            bool errorPortDoesNotExist(const std::string& portName, const std::string& callingMethod = "") const;
            bool errorPortArgumentIsNotInport(const std::string& name, const std::string& method, const std::string& argumentPosition = "") const;
            bool errorPortArgumentIsNotOutport(const std::string& name, const std::string& method, const std::string& argumentPosition = "") const;

            bool _mcode_used_for_simulation_only, _configuring_interface;

            std::string _mcode_model_name;

            std::vector<const PortDescriptor*> _all_inports;
            std::vector<std::pair<const PortDescriptor*, int> > _static_type_inports;
            std::vector<const PortDescriptor*> _control_inports;
            mutable std::vector<const PortDescriptor*> _inports_to_validate_control_inport_period;
            
            AnyTable::AnyType _unexposed_control_inport_data;

            std::vector<const PortDescriptor*> _all_outports;
            std::vector<const PortDescriptor*> _dynamic_outports;

            XCOInstr _core_instructions;
    };
}
#endif /* SGBLOCK_H */
