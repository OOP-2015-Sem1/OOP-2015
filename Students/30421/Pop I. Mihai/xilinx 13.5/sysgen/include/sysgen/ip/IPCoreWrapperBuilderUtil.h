#ifndef SYSGEN_IPCOREWRAPPERBUILDERUTIL_H
#define SYSGEN_IPCOREWRAPPERBUILDERUTIL_H

//ITLib inclusions
#include "anytable/AnyType.h"
#include "sysgen/sg_config.h"

//STD inclusions
#include <string>

using namespace std;
using namespace AnyTable;


namespace Sysgen{
namespace IP{
namespace IPCoreWrapperBuilderUtil{
    
    //Returns a hardware entity description that can be used
    //to synchronize data signals eg. AXI CIC Compiler's m_axis_data_tdata    
    //
    //@param dict contains the parameters for the synchronizer
    //@param useReset does use reset for synchronizer
    //@param lang indicates VHDL or Verilog, 1 = VHDL
    //
    //@return Returns a stringofied represetation of the entity
    std::string getDataDoubleSynchronizer(AnyType& dict, bool useReset = false, int lang = 1);

    //Returns a hardware entity description that can be used
    //to synchronize such qualifier signals as RDY    
    //
    //@param dict contains the parameters of the synchronizer
    //@param useReset does use reset for synchronizer
    //@param lang indicates VHDL or Verilog, 1 = VHDL
    //
    //@return Returns a stringofied represetation of the entity
    SG_API std::string getQualifierSynchronizer(AnyType& dict, bool useReset = false, int lang = 1);
    
    //Returns a hardware entity description that can be used
    //to synchronize data signals eg. FIR Compilre's dout    
    //
    //@param dict contains the parameters for the synchronizer
    //@param useReset does use reset for synchronizer
    //@param lang indicates VHDL or Verilog, 1 = VHDL
    //
    //@return Returns a stringofied represetation of the entity
    SG_API std::string getDataSynchronizer(AnyType& dict, bool useReset = false, int lang = 1);

    //Returns a hardware entity description that can be used
    //to synchronize data signals eg. FIR Compilre's dout    
    //
    //@param dict contains the parameters for the synchronizer
    //@param useReset does use reset for synchronizer
    //@param lang indicates VHDL or Verilog, 1 = VHDL
    //
    //@return Returns a stringofied represetation of the entity
	std::string getDataAligner(AnyType& dict, int lang);

    std::string getDividerResultAligner(AnyType& dict, int lang);

    std::string getMergedDividerOutAligner(AnyType& dict, int lang);

    //Right now, this function is written for firv6_3_CModel class only: it supports VHDL only now.
    //Please update if need to be used for verilog as well, without breaking any previous functionality
    //of this code.
    SG_API std::string getClockDriverWrapper(AnyType& dict, int lang = 1);

    //Performs search and replace of a sysgen-meta-ized text. 
    //dict is a key value pair of of search and replace similar
    //to embellishedStandardsMapping.
    string searchAndReplace(const string& text, AnyType& dict);

    //Performs search and replace of a sysgen-meta-ized text. 
    //Follows the perl text replacement method for now.
    string searchAndReplace(const string& text, const string& searchTag, const string& replaceTag);

    //Creates a unique hashcode used for entity name.     
    string getUniqueCode(const string& str);

    string purifyName(const string& text);
}
}
}

#endif // SYSGEN_IPCOREWRAPPERBUILDERUTIL_H
