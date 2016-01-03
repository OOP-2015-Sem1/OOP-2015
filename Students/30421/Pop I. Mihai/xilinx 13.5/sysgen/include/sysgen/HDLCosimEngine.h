/*
 *
 *  Module:      HDLCosimEngine.h
 *  Description: HDL software cosimulation interface class.
 *               This class extends the base cosim
 *               class defined in CosimEngine.h
 */

#ifndef HDL_COSIM_ENGINE_H
#define HDL_COSIM_ENGINE_H

#include "sysgen/CosimEngine.h"
#include "sysgen/PortDescriptor.h"
#include "sysgen/stringList.h"
#include "sysgen/MaskDB.h"

#ifdef _MSC_VER
#pragma warning (disable:4786)
// VC++ warning 4786 identifier over 255 characters truncated

#pragma warning (disable:4786)
// VC++ warning 4786 identifier over 255 characters truncated
#endif

namespace Sysgen
{
    /// \ingroup includes 
    /// \file HDLCosimEngine.h
    
    /// \ingroup published 
    /// \brief HDLCosimEngine

    class HDLCosimEngine : public Sysgen::CosimEngine {
    public:
        HDLCosimEngine(std::string srcDir,
                       std::vector<Sysgen::PortDescriptor> inPortdesc,
                       std::vector<Sysgen::PortDescriptor> outPortdesc,
                       std::string wrapperEntityName,
                       stringList files)
            : CosimEngine(srcDir,inPortdesc,outPortdesc),
              _wrapperEntityName(wrapperEntityName), _files(files),
              _step_cntr(0)
            {};
        virtual ~HDLCosimEngine() {}
        virtual void open(Sysgen::MaskDB &mask) = 0;
        virtual void close() = 0;
        virtual const Sysgen::StdLogicVector* examine(int portIndx) = 0;
        virtual void applyMask(Sysgen::MaskDB &mask) = 0;
        virtual void force(int portIndx, const Sysgen::StdLogicVector* val) = 0;
        void setClockDriverPortNames(stringList& names)
            {_clkdriver_portnames = names;};
        void setStepTimeScale(double t) {_step_size = t;}
        virtual void run(int steps) {_step_cntr += steps; }
        unsigned long getCurrentStepCount() const { return _step_cntr; }

    protected:
        std::string _wrapperEntityName;
        stringList _files;
        stringList _clkdriver_portnames;
        unsigned long _step_cntr;
        double _step_size;
    };

}


#endif /* HDL_COSIM_ENGINE_H */

