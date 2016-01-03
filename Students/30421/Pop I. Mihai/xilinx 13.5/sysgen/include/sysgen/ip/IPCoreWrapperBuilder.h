#ifndef SYSGEN_IPCOREWRAPPERBUILDER_H
#define SYSGEN_IPCOREWRAPPERBUILDER_H

#include "sysgen/ip/IPCore.h"
#include "sysgen/BlockDescriptor.h"
#include "anytable/AnyType.h"

//STL inclusions
#include <iostream>

namespace Sysgen{
namespace IP{

class IPCoreWrapperBuilder {
public:
	IPCoreWrapperBuilder(IPCore& ip, BlockDescriptor& bd, const std::string& core_name, const AnyTable::AnyType& updatedPortInfo);
    IPCoreWrapperBuilder(IPCore& ip, BlockDescriptor& bd, const std::string& core_name);
    IPCoreWrapperBuilder(IPCore& ip, BlockDescriptor& bd);   
    
#if 0
    // Renaud
    // Fixing Coverity issues. Removing unsed contructor which do not initialize all members
    IPCoreWrapperBuilder(std::string& filename);
    IPCoreWrapperBuilder();
    IPCoreWrapperBuilder(int lang);
#endif
    
    //A constructor to obtain it from a file

    //Accessors
    int getLanguage() const;
    
    std::string getWrapperName() const;

    std::string getWrapperName(const std::string& text) const;

    //Modifiers
    void setLanguage(int lang);
    
    void netlist(BlockDescriptor& bd);

    void netlistVHDL(BlockDescriptor& bd);

    void netlistVerilog(BlockDescriptor& bd);

    //Returns true if the wrapper usues the special port called "rst"
    bool isUsingRst();

    //Returns true if the wrapper uses the special port called "en"
    bool isUsingEn();

    //Language independent code generators
    std::string outputSynchronizer(std::string synchronizedNet, std::string inputNet, std::string synchronizerNet, std::string netType, int inputWidth, double synchronizationRate);

	std::string outputAligner(std::string alignedNet, std::string inputNet, int sysgenType, int outputWidth, int inputWidth);

    std::string outputFixedPtDivisionAligner(int q_e_idx, int q_s_idx);

    std::string outputMergedDividerAligner(int op_width, int inp_width);

    std::string outputDoubleSynchronizer(std::string synchronizedNet, std::string inputNet, std::string netType, int inputWidth, double synchronizationRate);

    std::string outputDataDoubleSynchronizer(std::string synchronizedNet, std::string inputNet, std::string netType, int inputWidth, double synchronizationRate);

    std::string transformOutputPort( std::string wrapper_port_name, const AnyTable::AnyType& port, bool needDoubleSynchronizer);

    std::string transformAXISubFieldOutputPort( std::string wrapper_port_name, const AnyTable::AnyType& subField, bool needDoubleSynchronizer );

    void transformMergedDividerOutputPort( std::string tdataPortName, std::string wrapper_port_name, int currMsb );

    //implement a port visitor
    bool isQualifierPort(std::string port_name);
    
    //Debug utilities
    void serialize(std::ostream& o);

    //Utility functions
    //Generate string for HDL net Ands
    static std::string netAnd(int lang, std::string netName1, std::string netName2);
    
    //Generate string for HDL net Assigns
    static std::string netAssign(int lang, std::string netLHS, std::string netRHS);

    //Generate string for HDL net slices
    static std::string netSlice(int lang, std::string netName, int Msb, int Lsb);

    //Generate string for HDL net 'not'
    static std::string netInverse(int lang, std::string netName);
 
    //Generate string for HDL net Ors
    static std::string netOr(int lang, std::string netName1, std::string netName2);

    //Generate string for parenthesizing an HDL net-operation statement
    static std::string parenthesizeNetOp(std::string netOpStmt);
 
    //Obtain the string for port declaration also initializes to 0 if possible
    static std::string getHdlPortDeclaration(int lang, std::string port_name, std::string port_type, std::string direction, int port_width);
    
    //Obtain the string for Net declaration
    static std::string getHdlNetDeclaration(int lang, std::string net_name, std::string net_type, int net_width);

	void setRunPortsAtSystemRate(bool f);

	bool getRunPortsAtSystemRate() const;

    bool getIsSimulationMode() const;

    void setIsSimulationMode(bool isSimulationMode);
private :
    //Private Utilities
    void buildWrapperConnectivity();

    void buildWrapperConnectivityForAXItdataPort(const AnyTable::Dictionary& tdataInfo,
                            const std::string& port_name, const std::string& port_type,
                            const std::string& port_direction, const int port_width,
                            std::set<double>& setOfRates, bool needDoubleSynchronizer);

    void initNetsForAXItdataSubFieldInfo(const AnyTable::AnyType& subFieldInfo,
                                         const std::string& tdataNetName,
                                         const std::string& port_direction,
                                         int& currMsb, std::set<double>& setOfRates,
                                         bool needDoubleSynchronizer);
    //Modifier
    void initializeMetaConnectionObject();

	void updateMetaConnectionObjectUsingPortInfo(const AnyTable::AnyType& portInfo);

	void updateMetaConnectionObjectUsingBlockDescriptor(BlockDescriptor& bd);
    
	//Language specific code generators
    std::string get_vhdl_entity();
    std::string get_vhdl_component();
    std::string get_core_instance();
    std::string get_vhdl_arch();
    std::string get_verilog_module();

    //Sysgen Conventions
    //Returns the name of the reset signal to be used after it
    //has been anded with the appropriate clock enable signal
    std::string getSyncRstNetName(double period);
    
    //Returns the name of the reset signal to be used after it
    //has been anded with the appropriate clock enable signal    
    std::string getSyncEnNetName(double period);

    //Returns the name of the broadcasted synchronizer arbitrated based
    //on the presence of enable signal
    std::string getSyncNetName(double period);

    //Returns the Rst signal arbitrated based on the broadcasted signal
    //or returns the constant tied to 0 based on availability of reset signal
    std::string getRstNetName(double period);
    
	std::string getCEName(double period, bool ce_logic=false);

    //Constructor utility
	void IPCoreWrapperBuilderImpl(IPCore& ip, BlockDescriptor& bd, const std::string& core_name, AnyTable::AnyType& portTranslationMapDict, const AnyTable::AnyType& updatePortInfo);

    AnyTable::AnyType _metaConnectionObject;
    
	int _lang;

	bool _runPortsAtSystemRate;

    bool _isSimulationMode;
};


}
}
#endif
