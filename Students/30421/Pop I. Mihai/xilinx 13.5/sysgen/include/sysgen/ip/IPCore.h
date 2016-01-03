/*
 * IPCore.h
 *
 * Copyright (c) 2006, Xilinx, Inc.  All Rights Reserved.
 */

#ifndef SYSGEN_IP_IPCORE_H
#define SYSGEN_IP_IPCORE_H

#include "sysgen/sg_config.h"
#include "anytable/AnyTable.h"
#include "sysgen/XCOInstr.h"
#include "sysgen/util/Cacher.h"

#include "spil/filetools.h"
#include "spil/time.h"
#include "spil/proxy.h"
#include "spil/shared_memory_channel.h"

#include <set>
#include <string>
#include <sstream>
#include <iostream>
#include <time.h>
#include <boost/foreach.hpp>

namespace Sysgen {
class XCOInstr;
}

namespace Sysgen {
namespace IP {

    /**
     * the IPCore class communicates with coregen
     */

    typedef SPIL::ProxyClient<SPIL::Proxy<SPIL::SharedMemoryChannel> >  TclClient_t;

class SG_API IPCore {
public:
    /// default constructor
    IPCore();

    /// constructor using core name and core version
    IPCore(const std::string& name, const std::string& version);

    /// constructor using ip key
    IPCore(const std::string& ipKey);

    /// destructor
    ~IPCore();

    /// set the ip name and version
    void setIP(const std::string& name, const std::string& version);

    /// set the family
    void setFPGAPart(const std::string& family,
                     const std::string& device,
                     const std::string& speed,
                     const std::string& package);

    /// set the family
    void setFamily(const std::string& family);

    /// set the device
    void setDevice(const std::string& device);

    /// set the package
    void setPackage(const std::string& package);

    /// set the speed
    void setSpeed(const std::string& speed);

    AnyTable::AnyType constructFamilyArg() const;

    /// get the family
    const std::string& getFamily() const;

    /// get the device
    const std::string& getDevice() const;

    /// set the package
    const std::string& getPackage() const;

    /// set the speed
    const std::string& getSpeed() const;

    /// return the ip key
    const std::string& getIPKey() const;

    /// return the ip name
    const std::string& getName() const;

    /// return the ip version
    const std::string& getVersion() const;

    /// check whether the core is installed or not
    /// irrespective of which family
    bool isInstalled() const;

    /// check whether it's available for a particular family
    bool isAvailable(const std::string& family) const;

    /// return true is it's pay core
    bool isPayCore() const;

    /// return the license type
    /// it can be Full for non pay core, or Invalid, Design_Linking, Hardware_Evaluation, or Bought for pay core
    std::string getLicenseType() const;

    /// returns a sequence of families that available for the core
    const AnyTable::Sequence& availableFamilies() const;

    AnyTable::AnyType& getParameterProperties();

    /// return a sequence of installed ip updates
    AnyTable::Sequence installedIPUpdates() const;

    int verbosity() const;

    void setVerbosity(int v);

    void setTclVerbosity(int v);

    /// checks three dlls to see whether they are available
    /// lib
    bool apiReady();

    AnyTable::AnyType& getParams();

    const AnyTable::Sequence& getParamList() const;

    //Execute Function
    void executeFunction(const std::string& functionName, AnyTable::AnyType& result) const;

    AnyTable::AnyType& getPortInfo(bool query_coregen = true);

    const AnyTable::AnyType& getPortInfoRef() const;

    const AnyTable::AnyType& findClk() const;

    const AnyTable::AnyType& findCE() const;

    const AnyTable::AnyType& findCETemp() const;

    bool isCE(const AnyTable::AnyType& port) const;
    /// @param inportInfo is a sequence of {name, arith, nbits,
    ///  binpt, latency, rate}. It must be in that order, but elements
    /// can be optional
    /// @param retKind could be ports or params
    /// @return the same portinfo format as the getPortInfo.
    /// We'll improve it later.
    void updatePortInfo(const AnyTable::AnyType& inportInfo,
                        const std::string& retKind,
                        AnyTable::AnyType& result) const;

    void setXcoInstr(Sysgen::XCOInstr& xco, const std::string& family, bool simOnly, const std::string& sim_language, bool structural_sim = false, bool need_device_setting = false);

    // return empty if no error, otherwise, error message
    // std::string validateParams() const;

    std::string validateParams(const AnyTable::AnyType& params) const;

    const AnyTable::AnyType& findPortInfo(const std::string& portname) const;

    // return true if the core has behavioral model in that language
    // the language can be VHDL, Verilog, C, MATLAB
    bool hasBehavioralModel(const std::string& language);

    std::string componentPortListString(int leadingSpaces);


    void getUpdatedParams(AnyTable::AnyType& updatedParams);

    std::string instancePortMapString(int leadingSpaces = 4, const AnyTable::AnyType& override = AnyTable::AnyType(), const std::string& hdl = "vhdl");

    /// utility function to convert a double array to coe file
    std::string tocoefile(const AnyTable::AnyType& coe_array);

    /// doubleVectorToBinString
    static std::string doubleVectorToBinString(const AnyTable::AnyType& coe_array);

    /// utility function to call tcl command
    /// return the error message
    /// the actual return string is in ret
    std::string callTclCommand(const std::string& command, const AnyTable::Sequence& args, std::string& ret, bool usecache = false, int verbosity = 0) const;

    // Initializes the TCL console by sourcing some TCL scripts
    void initializeDSPAPI() const;

    /// utility to call tcl command through Tcl client server
    /// the result is uncached
    /// return the error message
    /// the actual return value is in ret
    std::string callTclCommandTclServer(const std::string& command, const std::string& args_str, std::string& ret, int verbosity = 0) const;

    /// same as the previous method execpt the result value of the tcl is in anytable
    std::string callTclCommand(const std::string& command, const AnyTable::Sequence& args, AnyTable::AnyType& ret, bool usecache = false, int verbosity = 0) const;

    /// convert a sequence to tcl string
    static void convert_sequence_to_tclstring(std::string& str, const AnyTable::Sequence& seq, bool use_list_cmd = true);

    /// get the use cache tag
    bool getUsecache() const;

    /// set the use cache tag
    void setUsecache(bool tag);

    bool getSuppressWaitbox() const
    {
        return _suppressWaitbox;
    }

    const std::string& getWaitboxMessage() const
    {
        return _waitboxMessage;
    }

    void setSuppressWaitbox(bool suppressWaitbox)
    {
        _suppressWaitbox = suppressWaitbox;
    }

    void setWaitboxMessage(std::string waitboxMessage)
    {
        _waitboxMessage = waitboxMessage;
    }


    /// utility function
    static const AnyTable::AnyType& findClockPort(const AnyTable::AnyType& port_list);
    ///
    /// Adds to the list of side files that is maintained
    /// by this object. This list is used to create the
    /// hash of a cachable instruction. Also only file that
    /// exist on disk are maintained.
    /// \param fileName to add to the list of element
    void addToSideFiles(const std::string& fileName)
    {
       SPIL::FileTools ft;
       if ( ft.exists(fileName) )
         _sideFiles.insert(fileName);
       return;
    }
    ///
    /// Create a string identifier that captures
    /// the statistics associated with a file so that
    /// it becomes cacheable. In computing the hascode
    /// of any command this is passed as well
    ///
    /// \return string id of the file set
    std::string getSideFilesID( ) const
    {
        std::stringstream s;
        SPIL::FileTools ft;
        BOOST_FOREACH(std::string fileName, _sideFiles) {
            SPIL::FileTools::stat_t fileStats;
            if (ft.getstatus(fileName, fileStats)) {
                time_t seconds;
                seconds = time (NULL);
                s<<fileName<<":NoFileStats:"<<seconds<<":";
            }
            else{
                s<<fileName<<":Size:"<<fileStats.st_size<<":TimeStamp:"<<SPIL::Time::ctime(fileStats.st_mtime)<<":";
            }
        }
        return s.str();
    }

    static Sysgen::Utility::Cacher& getCache(bool refresh = false);

    static void stopClient();

 protected:
    AnyTable::AnyType params;

    std::string getCoreSelectString(const std::string& family);

    AnyTable::AnyType parameterProperties;

    // return true on success
    bool coeFileToString(const std::string& coeFile, std::string& filecontent);

 private:
    /// ip key
    std::string _ipKey;

    /// ip name
    std::string _name;

    /// ip version
    std::string _version;

    /// family
    std::string _family;

    /// device
    std::string _device;

    /// package
    std::string _package;

    /// speed
    std::string _speed;

    int _verbosity;

    /// utility to find ip key
    std::string findIPKey(const std::string& name, const std::string& version, int verbosity = 0) const;

    /// get ISE and Sysgen Ver string for cacher checksum
    const static std::string& get_ISE_and_SGVer_str();


    /// get the sysgen/scripts directory
    static std::string get_tcl_utils_location();

    /// cd into the scripts directory and returns the cwd
    static std::string prepare_for_tcl();


    /// convert anytable to tcl string
    static void convert_anytable_to_tclstring(std::string& str, const AnyTable::AnyType& any);


    /// convert string to tclstring, double quote it and escape special chars
    static void convert_string_to_tclstring(std::string& tclstr,
                                            const std::string& str,
                                            bool quote = true);

    /// map the ip key to ip name and ip version, returns in arg name and versiong
    void findNameVersion(const std::string& ipKey, std::string& name, std::string& version) const;

    static TclClient_t& getTclClient(bool restart = false, bool stop=false);

    AnyTable::AnyType _port_info;

    void getPortInfo(AnyTable::AnyType& portInfo);

    // void params2list(AnyTable::Sequence& seq);
    void params2list(const AnyTable::AnyType& params, AnyTable::Sequence& seq) const;

    bool _usecache;

    bool _suppressWaitbox;

    std::string _waitboxMessage;

    std::set<std::string> _sideFiles;
};


} // namespace IP
} // namespace Sysgen

#endif // ifndef SYSGEN_IP_CORE_H
