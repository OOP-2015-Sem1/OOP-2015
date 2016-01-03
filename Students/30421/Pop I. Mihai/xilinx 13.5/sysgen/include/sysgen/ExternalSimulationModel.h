/*
 *  ExternalSimulationModel.h
 *
 *  Copyright (c) 2003, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  System Generator external simulation model
 *                interface.
 */
#ifndef __EXTERNAL_SIMULATION_MODEL__
#define __EXTERNAL_SIMULATION_MODEL__
// Xilinx inclusions:
#include "sysgen/sg_config.h"
#include "sysgen/IntType.h"

// Standard Library inclusions:
#include <string>
#include <deque>

// Forward declarations:
namespace Sysgen { 
    class XFix;
    class StdLogicVector;
    class BlockDescriptor;
    class PortDescriptor;
    class PTable;
}

namespace Sysgen {

    /// \ingroup public_simulation
    /// \brief External Simulation Model API
    template<typename T>	
    class ExternalSimulationModel {
    public:
        // type definitions
        typedef T value_type;

        ExternalSimulationModel(BlockDescriptor& bd);
        virtual ~ExternalSimulationModel();

        const BlockDescriptor& getBlockDescriptor() const;

        BlockDescriptor& getBlockDescriptor();
        
        const PTable& getPTable() const;

        const T* getInputPtr(const PortDescriptor* pd) const;
        const T* getInputPtr(const std::string& pname) const;
        const Sysgen::XFix* getXFixInputPtr(const std::string& pname) const;

        T* getOutputPtr(const PortDescriptor* pd) const;
        T* getOutputPtr(const std::string& pname) const;
        Sysgen::XFix* getXFixOutputPtr(const std::string& pname) const;
        
        const Sysgen::uint32* getEventSignalPtr(const PortDescriptor* pd) const;
        const Sysgen::uint32* getEventSignalPtr(const std::string& pname) const;

        virtual void updateState() = 0;
        virtual void updateOutputs() const = 0;

    private:
        BlockDescriptor &bd;
    };
}
#endif
/*
#ifndef SG_EXPORT

SG_EXP_IMP template class SG_API 
Sysgen::ExternalSimulationModel<double>;

SG_EXP_IMP template class SG_API 
Sysgen::ExternalSimulationModel<Sysgen::XFix>;

SG_EXP_IMP template class SG_API 
Sysgen::ExternalSimulationModel<Sysgen::StdLogicVector>;

#endif
*/
