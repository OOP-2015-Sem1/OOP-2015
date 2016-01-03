
/*
 *  CosimEngine.h
 *
 *  Copyright (c) 2003-2005 Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Base class that defines the methods to communicate 
 *               with a co-simulation platform.  This class should
 *               be extended to produce a vendor-specific 
 *               co-simulation engine.
 */

#ifndef SYSGEN_COSIM_ENGINE_H
#define SYSGEN_COSIM_ENGINE_H

#include "sysgen/sg_config.h"
#include "sysgen/IntType.h"

// Forward declarations:
namespace Sysgen {
    class BlockDescriptor;
    class StdLogicVector;
}

namespace Sysgen {

    /// \ingroup includes
    /// \ingroup public_cosim
    /// \ingroup published
    /// \brief CosimEngine
    class SG_API CosimEngine {

    public:
       
        //! @name Constructors/Destructors
        //@{
        /// Construct with a BlockDescriptor.
        CosimEngine(const BlockDescriptor& bd);
        /// Virtual destructor.
        virtual ~CosimEngine() {}
        //@}

        //! @name Class methods
        //@{
        /// Opens and initializes the co-simulation platform.  This method is 
        /// called prior to the start of simulation.
        virtual void open() = 0;
        /// Closes the co-simulation platform.  This method is called after 
        /// simulation has completed, or if an error was encountered.
        virtual void close() = 0;
        /// Reads from an output port on the co-simulation platform.  
        virtual void read(uint32 portIndx, StdLogicVector& val) = 0;
        /// Writes to an input port on the co-simulation platform.
        virtual void write(uint32 portIndx, const StdLogicVector& val) = 0;
        /// Runs the co-simulation platform for some number of steps. 
        virtual void run(uint32 steps) = 0;
        //@}
	
    private:

        // CosimEngine's are prevented from being copied.
        // The class copy constructors and assignment operators are declared
        // private and never defined.
        CosimEngine(const CosimEngine&);  // not defined
        CosimEngine& operator= (const CosimEngine&);  // not defined
    };
}

#endif  //SYSGEN_COSIM_ENGINE_H
