/*
 *  BlockDescriptor.h
 *
 *  Copyright (c) 2004, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  System Generator block descriptor interface.
 */

#ifndef _BLOCKDESCRIPTOR_H_
#define _BLOCKDESCRIPTOR_H_

//Xilinx
#include "sysgen/sg_config.h"
#include "sysgen/PTable.h"
#include "sysgen/PortDescriptor.h"
#include "sysgen/SysgenConstants.h"
#include "sysgen/BlockFactory.h"
#include "anytable/AnyTableForwards.h"

// SL/Boost
#include <string>
#include <vector>
#include <map>
#include <limits>

// Forward declarations:
namespace Sysgen {
class BlockDescriptorImpl;
class FPOCore;
namespace Graph 
{
class Block;
}
#ifndef DOXYGEN_SHOULD_SKIP_THIS
class BitVector;
#endif
}

//---------------------------------------------------------------------------

#ifndef EXPORT_SYSGEN_FACTORY
#  define EXPORT_SYSGEN_FACTORY(factory_name,class_name) \
    extern "C" { SG_DLL_EXPORT \
      void* (factory_name)() {return (void*)(new (class_name));} \
    } \
namespace { \
    bool isRegistered = Sysgen::BlockFactory::instance().registerBuilder<class_name>(#factory_name); \
}
#endif // EXPORT_SYSGEN_FACTORY

#ifndef EXPORT_SYSGEN_MODEL
#  define EXPORT_SYSGEN_MODEL(model_name,class_name) \
    extern "C" { SG_DLL_EXPORT \
        void* (model_name)(Sysgen::BlockDescriptor& bd) { return (void*)(new class_name(bd)); } \
    }
#endif // EXPORT_SYSGEN_MODEL

//---------------------------------------------------------------------------

namespace Sysgen {

/// \ingroup includes
/// \ingroup descriptors
/// \ingroup public_config
/// \brief BlockDescriptor public interface
class SG_API BlockDescriptor
{
public:

    // Forward
    class PortIterator;
    // Forward
    class ConstPortIterator;

    //! simulation behavior enum.
    /*! Enumeration representing the model used to implement the blocks simulation behavior */
    enum SimulationType {
        SIMULATIONTYPE_DEFAULT = 0, //!< Default simulation type.
        PASSTHRU, //!< Xilinx internal.
        MCODE, //!< Xilinx internal.
        COSIM, //!< Xilinx internal.
        ISIM, //!< Xilinx internal.
        LEGACY, //!< Xilinx internal.
        EXTERNAL_DOUBLE, //!< Public C/CXX simulation model; signal types are double.
        EXTERNAL_STD_LOGIC_VECTOR, //!< Public C/CXX simulation model; signal types StdLogicVector.
        EXTERNAL_XFIX, //!< Public C/CXX simulation model; signal types Xilinx Fix Point.
        PROXY, //!< Xilinx BlackBlox.
        COMPOSITE, //!< Xilinix internal
        NULLSIM, //!< Invalid.                
        N_SIMULATIONTYPE,
        MULTI_SIGNAL_REP_SIM, //!< Xilinx internal
        EXTERNAL_XFLOAT,
        PROXYCOSIM //!< Xilinx internal.
    };

    /// Enumeration representing the SIMULINK icon shape
    enum IconShape { ICONSHAPE_DEFAULT, RECTANGLE, BUFFER, MUX, ROUND, PENTAGON_RIGHT, PENTAGON_LEFT, N_ICONSHAPE};

    //! @name Constructors/Desctructors
    //@{
    /// Default constructor
    BlockDescriptor() : _impl(NULL) {};


#ifndef DOXYGEN_SHOULD_SKIP_THIS
    BlockDescriptor(BlockDescriptorImpl* impl);
#endif

    /// Destructor
    virtual ~BlockDescriptor();
    //@}
protected:
    //! @name Configuration API
    //@{
    /// <strong>Block Interface Configuration.</strong>
    /// 
    /// This method is called exactly once per BlockDescriptor,
    /// and before any of the other configuration methods.  The
    /// minimal requirements of this method is that it specify the
    /// number and names of the ports on the blocks.  This is
    /// accomplished via the BlockDescriptor::addInport and
    /// BlockDescriptor::addOutport methods This method may also
    /// be used to set additional invariants or persistent state
    /// (member variables) on the block.
    ///
    /// \brief Initial Block Configuration.
    virtual void configureInterface() {};

    /// <strong> Map Input Rates and Types onto Output Rate and Types </strong>
    ///
    /// This method is called during the rate and type propagation
    /// phase of the model compilation.  If the block is contained
    /// in a cycle this method will be potentially be called
    /// multiple times therefore it must be re-entrant.  The block
    /// is free to (and in fact encouraged to) proffer output
    /// rates/types based upon incomplete inputs.  Since is
    /// possible that the block will be presented with inputs that
    /// map to dissallowed outputs during the iterative phase of a
    /// cyclic rate/type resolution the block should defer the
    /// application of rate and type constraints until the
    /// BlockDescriptor::configurePostRateAndType phase.
    ///
    /// \brief Map Input Rates and Types onto Output Rate and Types 
    virtual void configureRateAndType() {};

    /// <strong> Pre-Simulation Configuration </strong>
    /// 
    /// This method is called once per BlockDescriptor prior to
    /// the begining of simulation.  Typically one sets selects
    /// the simulation used for behavioral simulation of the block
    /// during this phase via the BlockDescriptor::setSimulationType call.
    /// The simulation APIs available to Sysgen block developers are 
    /// described in further detail in \ref simulation.
    /// 
    /// \brief Pre-simulation configuration
    virtual void configureSimulation() {};

    /// <strong> Apply Block Specific Rate and Type Constraints </strong>
    ///
    /// This method is called once per BlockDescriptor upon completion of 
    /// the rate and type propagation algorithm.  This method is invoked
    /// regardless of the successfull completion the rate and type propagation
    /// solver.  At this juncture the block can apply rate and type constraints and
    /// report errors if these constraints are violated.
    ///
    /// \brief Post Rate and Type
    virtual void configurePostRateAndType() const {};

    /// <strong> Pre-netlist Configuration </strong>
    ///
    /// This method is envoked during a global pass over the
    /// entire design, prior to the invocation of the incremental
    /// netlister. It is used to configure those aspects of HDL
    /// code generation required prior to the execution of the
    /// incremental netlister.  Currently this includes
    /// configuring the block's clock requirements and identifying
    /// those blocks that are to be ignored during netlisting via
    /// the BlockDescriptor::setHardwareForceTrim, or protected
    /// from trimming via a call to BlockDescriptor::setHardwareNoTrim
    ///
    /// \brief Pre-netlist configuration
    virtual void configureNetlistInterface() {};

    /// <strong> Set HDL Code Generation Instructions and Options </strong><
    ///
    /// This method is used to set the HDL code generation instructions via
    /// the Netlisting interface methods of this API.  The method is invokes
    /// when the incremental netlister visits this block.  Upon completion
    /// the instructions specified in the config function are cleared.
    ///
    /// \brief HDL Code generation configuration.
    virtual void configureNetlist() {};

    //@}

public:

    virtual const Sysgen::FPOCore* getFPOCore() { return NULL; }
    //! @name Block Name & Properties
    //@{
    /// Get the block name.
    /// \param heirarchical if true returns the full "path" to the block
    /// \return the block name, optionally perpended with the path\n
    /// the the block
    std::string getName(bool heirarchical = true) const;
    /// True returns block type
    std::string getType() const;
    /// Text to be displayed in center of block icon.
    /// \param icon_text text to display
    void setIconText(const std::string& icon_text);

    /// If ports are grouped using the addInportAsGroups or addOutportAsGroups
    /// call, the name of the ports may or may not get compacted based on the 
    /// status of the CompatingPortNames flag. If isCompactingPortNames, preceeding
    /// m_axis_ and s_axis_ is removed from the block icon text.
    bool isCompactingPortNames() const;
    void setCompactingPortNames(bool value);

    /// Get the icon text
    const std::string&  getIconText(void) const;

    /// Shape of icon
    /// \see IconShape
    void setIconShape(IconShape shape);
    /// Background color for icon
    /// \param color set the background color for the block
    void setIconBackgroundColor(const std::string& color);
    /// Background watermark color for icon
    /// \param color set the watermark color for the block
    void setIconWatermarkColor(const std::string& color);
	/// Block annotation
	void setIconAnnotation(const std::string& annotate);
    /// Get Handle (Sysgen Object Manager)
    double getHandle() const;
    /// Get immutable parameter table object.
    const PTable& getPTable() const;
    /// Get mutable parameter table object.
    PTable& getPTable();
    /// Get immutable parameter table object.
    const AnyTable::AnyType& getAnyTable() const;
    /// Get mutable parameter table object.
    AnyTable::AnyType& getAnyTable();
    //@}


    //! @name Simulation
    //@{
    /// Set the simulation.
    /// \param type the type of the simulation to use for this block
    /// \see SimulationType
    void setSimulationType(SimulationType type);
    /// Get the current simulation type
    /// \return the current simulation type
    /// \see SimulationType
    SimulationType getSimulationType() const;
    /// Get the current simulation type as a string
    /// \return the current simulation type as a string
    /// \see SimulationType
    std::string getSimulationTypeString() const;
    /// Is Simulation NoTrim tag
    /// \return true if the simulation no trim tag applies.
    bool isSimulationNoTrim() const;
    ///  Set SimulationNoTrim  tag
    void setSimulationNoTrim(bool value);
    //@}


    //! @name Ports
    //@{
    /// Create an new inport on this block.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addInport(const std::string& pname);

    /// Create an new inport on this block with preset icon text.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \param icon_text text to display on the icon
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addInport(const std::string& pname, const std::string& icon_text);

    /// Create an new inport on this block.
    /// \param channel_group integer (>0) that describes which channel a port belongs to.
    /// ports that are not add with this function are automatically placed in channel_group=1.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addInportAsGroup(const int channel_group, const std::string& pname);

    /// Create an new inport on this block with preset icon text.
    /// \param channel_group integer (>0) that describes which channel a port belongs to.
    /// ports that are not add with this function are automatically placed in channel_group=1.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \param icon_text text to display on the icon
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addInportAsGroup(const int channel_group, const std::string& pname, const std::string& icon_text);


    /// Create an new inport on this block.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addOutport(const std::string& pname);

    /// Create an new outport on this block with preset icon text.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \param icon_text text to display on the icon
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addOutport(const std::string& pname, const std::string& icon_text);

    /// Create an new outport on this block.
    /// \param channel_group integer (>0) that describes which channel a port belongs to.
    /// ports that are not add with this function are automatically placed in channel_group=1.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addOutportAsGroup(const int channel_group, const std::string& pname);

    /// Create an new outport on this block with preset icon text.
    /// \param channel_group integer (>0) that describes which channel a port belongs to.
    /// ports that are not add with this function are automatically placed in channel_group=1.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \param icon_text text to display on the icon
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addOutportAsGroup(const int channel_group, const std::string& pname, const std::string& icon_text);




    /// Create an new inoutport on this block.
    /// \param pname name for the port; an error occurs if pname is empty
    /// \return a pointer to the newly created port if successful
    PortDescriptor* addInoutport(const std::string& pname);

    /// Get port by name
    /// \param name name of port to find
    /// \return a pointer to the port or NULL
    PortDescriptor* getPort(const std::string& name);

    /// Get port by name (const version)
    /// \param name name of port to find
    /// \return a const pointer to the port or NULL
    const PortDescriptor* getPort(const std::string& name) const;

    ///  Get port by index
    /// \param index port index number
    /// \throw Error exception raised for index-out-of-bounds
    /// \return pointer to port
    PortDescriptor* getPort(int index);

    ///  Get port by index (const version)
    /// \param index port index number
    /// \throw Error exception raised for index-out-of-bounds
    /// \return const pointer to port
    const PortDescriptor* getPort(int index) const;

    /// Get input port by index
    /// \param index port index number
    /// \return pointer to the input port or NULL if no corresponding \e input  port is found
    PortDescriptor* getInport(int index);

    /// Get input port by index (const version)
    /// \param index port index number
    /// \return const pointer to the input port or NULL if no corresponding \e input port is found
    const PortDescriptor* getInport(int index) const;

    /// Get output port by index
    /// \param index port index number
    /// \return pointer to the output port or NULL if no corresponding \e output  port is found
    PortDescriptor* getOutport(int index);
    
    /// Get output port by index (const version)
    /// \param index port index number
    /// \return const pointer to the output port or NULL if no corresponding \e output  port is found
    const PortDescriptor* getOutport(int index) const;
    
    /// Get inoutput port by index
    /// \param index port index number
    /// \return pointer to the inoutput port or NULL if no corresponding \e inoutput  port is found
    PortDescriptor* getInoutport(int index);
    
    /// Get inoutput port by index (const version)
    /// \param index port index number
    /// \return const pointer to the inoutput port or NULL if no corresponding \e inoutput port is found
    const PortDescriptor* getInoutport(int index) const;
    
    /// Get a pair of iterators for the input ports
    ///
    /// This method returns a pair of iterators which bound the
    /// set of input ports.
    ///
    /// Typical usage:
    /// \code
    /// // ...
    /// std::pair<PortIterator, PortIterator> iters = getInports();
    /// PortIterator begin = iters.first();
    /// PortIterator end = iters.second();
    ///
    /// for ( ; begin != end; ++begin) {
    ///   // do something with *begin
    /// }
    /// // ...  \endcode \return a pair of iterators, where the
    /// first of the pair is the beginning of the input ports and
    /// the second is the end.
    std::pair<PortIterator, PortIterator> getInports();

    /// Get a pair of iterators for the input ports (const version)
    /// \see getInports() \return a pair of const iterators, where the
    /// first of the pair is the beginning of the input ports and the
    /// second is the end.
    std::pair<ConstPortIterator, ConstPortIterator> getInports() const;

    /// Get a pair of iterators for the output ports \see getInports()
    /// \return a pair of iterators, where the first of the pair is
    /// the beginning of the output ports and the second is the end.
    std::pair<PortIterator, PortIterator> getOutports();

    /// Get a pair of iterators for the output ports (const version)
    /// \see getInports() \return a pair of const iterators, where the
    /// first of the pair is the beginning of the output ports and the
    /// second is the end.
    std::pair<ConstPortIterator, ConstPortIterator> getOutports() const;

    /// Get a pair of iterators for all ports \see getInports()
    /// \return a pair of iterators, where the first of the pair is
    /// the beginning of the ports and the second is the end.
    std::pair<PortIterator, PortIterator> getPorts();

    /// Get a pair of iterators for all ports (const version) \see
    /// getInports() \return a pair of const iterators, where the
    /// first of the pair is the beginning of the ports and the second
    /// is the end.
    std::pair<ConstPortIterator, ConstPortIterator> getPorts() const;

    /// Get the count of input ports
    /// \return count of input ports
    int getNumInports() const;

    /// Get the count of output ports
    /// \return count of output ports
    int getNumOutports() const;
    //@}

    //! @name Rates & Types
    //@{
    ///  Input types are known
    /// \return true if the input types are known; false otherwise
    bool inputTypesKnown() const;

    ///  Input rates are known
    /// \return true if the input rates are known; false otherwise
    bool inputRatesKnown() const;

    ///  Output rates are known
    /// \return true if the output rates are known; false otherwise
    bool outputRatesKnown() const;
    
    ///  Output types are known
    /// \return true if the output types are known; false otherwise
    bool outputTypesKnown() const;
    //@}

    /// @name Gateway Tags
    //@{
    /// Set gateway in tag
    /// \param value true if the block is a gateway in; false otherwise
    void setGatewayIn(bool value);
    /// Test for input gateway
    /// \return true if the block is a gateway in; false otherwise
    bool isGatewayIn() const;
    ///  Set gateway out tag
    /// \param value true if the block is a gateway out; false otherwise
    void setGatewayOut(bool value);
    /// Test for output gateway
    /// \return true if the output is a gateway; false otherwise
    bool isGatewayOut() const;
    ///  Set gateway inout tag
    /// \param value true if the block is a gateway inout; false otherwise
    void setGatewayInout(bool value);
    /// Test for inout gateway
    /// \return true if the block is a gateway inout; false otherwise
    bool isGatewayInout() const;
    //@}

    //! @name Clock & Clock Enable
    //@{
    /// Add clock and clock enable pair
    /// \param clkPname clock port name
    /// \param cePname clock enable port name
    /// \param rate clock period
    /// \param tied_to_gnd clock port is tied to ground
    /// \param domain named clock domain
    /// \param multicycle_path_constrained specifies that all sequential elements connected to such a ce will be multicycle_path_constarined
    void addClkCEPair(const std::string& clkPname,
                      const std::string& cePname,
                      const double& rate,
                      bool tied_to_gnd,
                      const std::string& domain,
                      bool multicycle_path_constrained);  

    /// Add clock and clock enable pair
    /// \param clkPname clock port name
    /// \param cePname clock enable port name
    /// \param rate clock period
    /// \param tied_to_gnd clock port is tied to ground
    /// \param domain named clock domain
    void addClkCEPair(const std::string& clkPname,
                      const std::string& cePname,
                      const double& rate,
                      bool tied_to_gnd,
                      const std::string& domain);
    
    /// Add clock and clock enable pair
    /// \param clkPname clock port name
    /// \param cePname clock enable port name
    /// \param rate clock period
    /// \param tied_to_gnd clock port is tied to ground
    /// \param domain named clock domain
    void addClkCEPair(const std::string& clkPname,
                      const std::string& cePname,
                      const double& rate,
                      bool tied_to_gnd);
    
    /// Add clock and clock enable pair
    /// \param clkPname clock port name
    /// \param cePname clock enable port name
    /// \param rate clock period
    void addClkCEPair(const std::string& clkPname,
                      const std::string& cePname,
                      const double& rate);
    
    ///  Add clock port
    /// \param clkPname clock port name
    /// \param rate clock period
    /// \param domain named clock domain
    void addClk(const std::string& clkPname, 
                const double& rate,
                const std::string& domain = std::string(),
                bool ce_port = true);

    /// Specify an internal rate for inclusion into system
    /// rate GCD design rule checks, and, optionally, into
    /// the scheduling of the block for simulation.
    void addInternalRate(const double& rate, bool schedule=true);
    //@}

    //! @name Latency
    //@{
    /// setMinLatency from input port to output port pair.
    ///
    /// \param pname1 any valid port name
    /// \param pname2 any valid port name
    /// \param latency minimum latency between ports (see notes)
    ///
    /// Meaningful latencies are:
    ///  - 0, combinational dependence,
    ///  - positive integers,
    ///  - and +Inf (no dependence, unconditionally).
    ///
    /// pname1 and pname2 must name one input port and one output
    /// between them.  Otherwise, an error is raised via addError().
    ///
    /// \brief Minimum latency between named ports
    void setMinLatency(const std::string& pname1,
                       const std::string& pname2, const double& latency);

    /// Convenience method for setting all minimum latencies associated with the named port.
    ///
    /// \param pname name of the port.  If pname refers to an
    ///   inport, then all its associated outports will be
    ///   affected, and visa versa.
    /// \param latency minimum latency
    ///
    /// \see setMinLatency(const std::string& pname1, const std::string& pname2, double latency)
    /// \brief Set Minimum latency between named port and all associated ports
    void setMinLatency(const std::string& pname, const double& latency);

    /// Convenience method for setting minimum latencies for all inports.
    ///
    /// Equivalent to calling setMinLatency(pname, latency) for all inports.
    ///
    /// \param latency minimum latency
    ///
    /// \see setMinLatency(const std::string& pname, double latency)
    /// \brief Minimum latency for all input ports
    void setMinLatency(const double& latency);
    //@}

    //! @name HDL Generation & HDL Simulation
    //@{

    /// Is netlisting for simulation
    /// \sa setGenerateForSimulation(bool)
    /// \return true if the netlisting is being generated for simulation (false for hardware)
    bool isGenerateForSimulation() const;

    /// Set netlist for simulation
    /// \sa setGenerateForSimulation()
    /// \param val true if the netlisting is being generated for simulation (false for hardware)
    void setGenerateForSimulation(bool val);

    /// Set the scrubbed HDL name
    /// \param name scrubbed HDL name
    void setHDLName(const std::string& name);

    /// Scrubbed HDL name
    /// \return the scrubbed HDL name
    std::string getHDLName() const;

    ///  Set the top level language
    /// \param language HDL language type; must be in ["vhdl", "verilog", "ngc", "edif"]
    void setTopLevelLanguage(const std::string& language);

    /// Set the top level language symbolically
    /// \param language HDL language type
    void setTopLevelLanguage(Sysgen::Language language);

    /// Get top level language
    /// \return the current HDL language type (in Sysgen::Language)
    int getTopLevelLanguage() const;

    ///  Get top level language as a string
    /// \return a string representing the current top-level language
    std::string getTopLevelLanguageString() const;

    ///  Suggest entity name.
    ///  Will be changed in the case of a collision
    void suggestEntityName(const std::string& name);

    ///  Demand entity name.
    ///  Will \e not be changed in the case of a collision
    void demandEntityName(const std::string& name);

    /// Add configuration parameters the block.
    /// \param identifier name for the parameter
    /// \param value initial value for the parameter
    void addGeneric(const std::string& identifier, bool value);

    /// Add configuration parameters the block.
    /// \param identifier name for the parameter
    /// \param value initial value for the parameter
    void addGeneric(const std::string& identifier, int value);

    /// Add configuration parameters the block.
    /// \param identifier name for the parameter
    /// \param value initial value for the parameter
    void addGeneric(const std::string& identifier, const double& value);

    /// Add configuration parameters the block.
    /// \param identifier name for the parameter
    /// \param value initial value for the parameter
    void addGeneric(const std::string& identifier, const std::string& value);

#ifndef DOXYGEN_SHOULD_SKIP_THIS
    /// Add configuration parameters the block.
    /// \param identifier name for the parameter
    /// \param bitvector initial value for the parameter
    void addGeneric(const std::string& identifier, const BitVector& bitvector);
#endif

    /// HDL Generic by type
    /// \param identifier name for the parameter
    /// \param type parameter type; must be one of ["boolean", "integer", "real", "string"]
    /// \param value initial stringification of value
    void addGeneric(const std::string& identifier, const std::string& type, const std::string& value);

    /// Deliver file during netlisting
    /// \param fn filename
    void addFile(const std::string& fn);
    
	/// Deliver a src file as a consolidated destination file. During netlisting
	/// contents of the source file will be appended to a the destination file
	/// \param sfn source file name
	/// \param dfn source file name
	void addFile(const std::string& sfn, const std::string& dfn);
	/// File
		void addFile(const std::string& sfn, const std::string& dfn, const std::string& lib_name);
	
	/// Do not consolidate the delivered files to the <design>.vhd by default
	void setDoNotConsolidate();
	
	/// Returns consolidate flag
	bool getConsolidateFlag() const;

	/// Sets the default HDL library to compile all files delivered by this block
	/// \param default block library name
	void setHdlLibrary(const std::string& lib_name);
	
	///Get Hdl Library
	const std::string& getHdlLibrary() const;
    
	/// Add delivery script & arguments
    /// \param script perl script to run during netlisting
    /// \param includeDir path to add to perl invocation
    /// \param args perl hash table of arguments
    void addNetlistingScript(const std::string& script, const std::string& includeDir, const PTable& args);
#ifndef DOXYGEN_SHOULD_SKIP_THIS
    ///  HDL simulator options
    void setHDLSimulatorOptions(const std::string& simulator, const PTable& options);

    ///  HDL simulator options
    const PTable& getHDLSimulatorOptions(const std::string& simulator);
#endif

    /// Is floating block tag
    /// \return true if the block is a floater
    bool isFloaterBlock() const;

    ///  Set floating block tag
    /// \param value true if the block is to be a floater; false otherwise
    void setFloaterBlock(bool value);

    /// Is Hardware No Trim tag
    /// \return true if the hardware no trim tag is set; false otherwise
    bool isHardwareNoTrim() const;

    ///  Set Hardware No Trim tag
    /// Protect the block from trimming during HDL generation
    /// \param value true if the hardware no trim tag should be set
    void setHardwareNoTrim(bool value);

    /// Is Hardware Force Trim tag
    /// \see setHardwareNoTrim()
    /// \return true if the hardware force trim tag is set
    bool isHardwareForceTrim() const;

    ///  Set Hardware Force Trim tag
    /// \param value true if the hardware force trim tag should be set
    void setHardwareForceTrim(bool value);

#ifndef DOXYGEN_SHOULD_SKIP_THIS
    ///  Set update state rate
    ///  \param rate how often updates happen
    void setUpdateStateRate(const double& rate);
#endif

    /// Set an attribute
    void setAttribute(const std::string&, const AnyTable::AnyType&);
    void setAttribute(const std::string&, const PTable&);

    /// Clear all attributes
    void clearAttributes();

    /// Set block to combinational
    void tagAsCombinational(void); 

    /// Check if block is combinational
    /// \return true if the block is combinational; false otherwise
    bool isCombinational(void) const;
    //@}

    //! @name Error Generation
    //@{
    /// Register an error message with the ErrorManager.  
    void addError(const std::string& summary, const std::string& details="") const;

    /// Register an warning message with the WarningManager.  
    void addWarning(const std::string& summary, const std::string& details="") const;

    ///  Append Sysgen::Error
    void addError(const Sysgen::Error& error) const;

    /// Register an error message with the ErrorManager and
    /// immediately return from the current configuration method
    void addErrorAndThrow(const std::string& summary, const std::string& details="") const;

    /// Immediately return from the current configuraton method.
    void throwConfigError() const;

    /// Register a fatal error with the ErrorManager. This method will
    /// generate a standard error message augemented by the details
    /// specified.  The standard error message will suggest that the user
    /// restart the MATLAB session.  This method also results in immediate
    /// temination of the Sysgen flow, that is it potentially modifies the
    /// error aggregation policy set by the infrastructure.
    void fatalError(const std::string& details="") const;

    ///  Returns true if error vector is non-empty
    bool hasError() const;
    //@}

    //! @name Diagnostics
    //@{
    /// Write BlockDescriptor state to standard output stream (std::cout)
    void print() const;

    /// Immutable reference to vector of Sysgen::Errors
    const std::vector<Sysgen::Error>& getErrors() const;

    /// Mutable reference to vector of Sysgen::Errors
    std::vector<Sysgen::Error>& getErrors();

    /// Returns an error message string containing the text of all of
    /// the errors that have been added to the block
    std::string getErrorString() const;
    //@}

    /// ConstPortIterator: models SL forward iterator
    class SG_API ConstPortIterator {
    public:

        //! @name SL forward iterator
        //@{
        typedef PortDescriptor value_type;
        typedef std::ptrdiff_t difference_type;
        typedef std::forward_iterator_tag iterator_category;
        typedef const value_type* const_pointer;
        typedef const value_type& const_reference;
        //@}
        ConstPortIterator() : _bd(0) {}
        ConstPortIterator(const BlockDescriptor* bd,
                          const std::vector<int> indices = std::vector<int>(),
                          bool init_to_last=false) :
            _bd(bd),
            _indices(indices)
        {
            if(init_to_last) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
        }

        // Copy Constructor
        ConstPortIterator(const ConstPortIterator& rhs) :
            _bd(rhs._bd),
            _indices(rhs._indices){
            if(rhs._private_iter == rhs._indices.end()) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
        }

        // Copy/Convert from non-const PortIterator
        ConstPortIterator(const PortIterator& rhs) :
            _bd(rhs._bd),
            _indices(rhs._indices){
            if(rhs._private_iter == rhs._indices.end()) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
        }

        // Assignment operator
        ConstPortIterator& operator=(const ConstPortIterator& rhs) {
            _bd = rhs._bd;
            _indices = rhs._indices;
            if(rhs._private_iter == rhs._indices.end()) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
            return *this;
        }


        // Conversion assignment 
        ConstPortIterator& operator=(const PortIterator& rhs) {
            _bd = rhs._bd;
            _indices = rhs._indices;
            if(rhs._private_iter == rhs._indices.end()) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
            return *this;
        }

        bool
        operator==(const ConstPortIterator& rhs) {
            bool same = (_bd == rhs._bd) &&
                (_indices == rhs._indices) &&
                (_private_iter - _indices.begin()) == (rhs._private_iter - rhs._indices.begin());
            return same;
        }

        bool
        operator!=(const ConstPortIterator& rhs) {
            return !operator==(rhs);
        }

        /// Returns reference
        const_reference operator*() const;
        /// Returns pointer
        const_pointer operator->() const;
        /// Returns pointer
        const_pointer get() const;
        // Increment
        void operator++();
        // Prefix increment
        void operator++(int);

    private:

        // private members
        const BlockDescriptor* _bd;
        std::vector<int> _indices;
        std::vector<int>::iterator _private_iter;
    }; // End of ConstPortIterator

    /// PortIterator: models SL forward iterator
    class SG_API PortIterator {
    public:

        //! @name SL forward iterator
        //@{
        typedef PortDescriptor value_type;
        typedef std::ptrdiff_t difference_type;
        typedef std::forward_iterator_tag iterator_category;
        typedef value_type* pointer;
        typedef value_type& reference;
        //@}

        PortIterator(BlockDescriptor* bd,
                     const std::vector<int> indices = std::vector<int>(),
                     bool init_to_last=false) :
            _bd(bd),
            _indices(indices)
        {
            if(init_to_last) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
        }

        PortIterator() : _bd(0) {}

        // Copy Constructor
        PortIterator(const PortIterator& rhs) :
            _bd(rhs._bd),
            _indices(rhs._indices){
            if(rhs._private_iter == rhs._indices.end()) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
        }

        // Assignment operator
        PortIterator& operator=(const PortIterator& rhs) {
            _bd = rhs._bd;
            _indices = rhs._indices;
            if(rhs._private_iter == rhs._indices.end()) {
                _private_iter = _indices.end();
            } else {
                _private_iter = _indices.begin();
            }
            return *this;
        }

        bool
        operator==(const PortIterator& rhs) {
            bool same = (_bd == rhs._bd) &&
                (_indices == rhs._indices) &&
                (_private_iter - _indices.begin()) == (rhs._private_iter - rhs._indices.begin());
            return same;
        }

        bool
        operator!=(const PortIterator& rhs) {
            return !operator==(rhs);
        }

        /// Returns reference
        reference operator*();
        /// Returns pointer
        pointer operator->();
        /// Returns pointer
        pointer get();
        // Increment
        void operator++();
        // Prefix increment
        void operator++(int);

    private:
        
        friend class Sysgen::Graph::Block;
        friend class ConstPortIterator;

        // private members
        BlockDescriptor* _bd;
        std::vector<int> _indices;
        std::vector<int>::iterator _private_iter;
    }; // End of PortIterator

public:

#ifndef DOXYGEN_SHOULD_SKIP_THIS
    void setImplPtr(BlockDescriptorImpl *impl) { _impl = impl; };
    // Dispatch layer for derived class configuration methods
    BlockDescriptorImpl* getImplPtr() { return (_impl); };
    void dispatchConfigureInterface();
    void dispatchConfigureRateAndType();            
    void dispatchConfigurePostRateAndType() const;            
    void dispatchConfigureSimulation();
    void dispatchConfigureNetlistInterface();            
    void dispatchConfigureNetlist();            
    // dispatch for non-const config methods
    typedef void (BlockDescriptor::*config_function_ptr)(void);
    void dispatch(config_function_ptr func, const std::string& phase);
    // dispatch for const config methods
    typedef void (BlockDescriptor::*const_config_function_ptr)(void) const;
    void dispatch(const_config_function_ptr func, const std::string& phase);
#endif

private:    

    // exception 
    struct APIError{};    

    // BlockDescriptor objects can not be copied.
    // The copy constructor is declared as private and not defined.
    BlockDescriptor(const BlockDescriptor& q){;}
    
    // BlockDescriptor objects can not be copied.
    // The copy constructor is declared as private and not defined.
    void operator=(const Sysgen::BlockDescriptor& q){;}

    BlockDescriptorImpl* _impl;
};

} // namespace Sysgen


#endif // _BLOCKDESCRIPTOR_H_
