#ifndef _COREGENIMPORTBLOCK_H
#define _COREGENIMPORTBLOCK_H

#include "sysgen/sg_config.h"
#include "sysgen/BlockDescriptor.h"
#include "sysgen/ip/IPCore.h"
#include "anytable/AnyType.h"

#include <map>
#include <vector>

namespace Sysgen {
    class SG_API CoregenImportBlock: public BlockDescriptor
    {
    public:

        /// constructor
        /**
         * special parameters in the mask
         *
         * ip_name
         * ip_version
         * ipcore_verbose
         * ipcore_use_cache
         * ipcore_store_updated_params
         */
        CoregenImportBlock();
        
        /// destructor
        virtual ~CoregenImportBlock() {};

        virtual void configureInterface();
        virtual void configureRateAndType();
        virtual void configurePostRateAndType() const;
        virtual void configureNetlistInterface();
        virtual void configureNetlist();
        virtual void configureSimulation();
        
        //If Sysgen Wrapper is available
        virtual bool isWrapperAvailable() const;
        virtual void configureNetlistWithWrapper(std::string wrapper_prefix);
        
        static std::string addCoreFromXCOInstr(BlockDescriptor& block, const Sysgen::XCOInstr& xcoInstr);
        void getExternalSimExecuteFunction(const std::string& functionName, AnyTable::AnyType& result) const;

    protected:
        virtual void setIPCoreParams(Sysgen::IP::IPCore& ipcore);
        virtual void setPortConfig(AnyTable::AnyType& portConfig);

        /**
         * we pass a struct in PTable to guide the mcode sim configuration:
         * mcode_config => {
         *   param_match => {
         *     param1 => value1,
         *     param2 => value2,
         *   },
         *   mcode_model => "String", The mcode model name
         *   param_map => {
         *     core_param1 => mcode_param1, empty meaning using the same name
         *                                  if the param a string and is a choice
         *                                  we auto convert the string to enum int starting at 0
         *     core_param2 => {
         *       mcode_param => "mcode_param_name",
         *       string_to_enum => {
         *         [string1, string2]
         *       }, 
         *     },
         *   inport_default => {
         *     name1 => value1,
         *     name2 => value2,
         *   }
         * }
         * 
         * the mcode config can be a struct or a sequence of struct
         * the param_match is used to switch to different mcode model.
         */
        virtual void setMCodeModelParamsFromParamMap(const AnyTable::AnyType& param_map);

        virtual void setMCodeModelParamsWithNameMatch(const AnyTable::AnyType& mcode_model_argsin);
        virtual void setMCodeModelParamsWithPortDefault(const AnyTable::AnyType& inport_default);

        virtual void setMCodeModelShowPortList(const AnyTable::AnyType& config);

        virtual void setMCodeModelParams(const AnyTable::AnyType& config);
        virtual void configureMCodeModel(const AnyTable::AnyType& config);

        /// let's temporarily put this utility here
        static int matchStringInSequence(const std::string& str, const AnyTable::AnyType& seq);

        virtual std::string getSimulationType() const;

        bool isVerbose() const;

        void updateAXITDataInfoMaskParam();

        void updateAXIFIRCoefFileNameGeneric(AnyTable::AnyType& generics);

        ///
        /// Method to determine if the block has a combinational
        /// path or not.
        /// 
        /// @param portInfo param to obtain latency information
        /// 
        /// @return return true if the block has a combinational path.
        ///
        bool has_combinational(const AnyTable::AnyType& portInfo) const;
    private:
        void setMCodeModelName(const AnyTable::AnyType& config);
        bool _dsptool_ready;
        Sysgen::IP::IPCore ipcore;
        AnyTable::AnyType port_config;
		AnyTable::AnyType updatedPortInfo;
        std::map<std::string, std::string> embellishStandardMapping();
        

    };
}
#endif // COREGENIMPORTBLOCK_H
