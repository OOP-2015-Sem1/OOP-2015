/*
 *  ShmemCosimEngine.h
 *
 *  Copyright (c) 2003-2008, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Base class that defines the methods necessary to
 *               communicate with an underlying co-simulation
 *               platform that utilizes shared memory objects.
 *               This class should be extended to produce a vendor-
 *               specific co-simulation engine.
 */

#ifndef SHMEM_COSIM_ENGINE_H
#define SHMEM_COSIM_ENGINE_H

// Xilinx
#include "sysgen/sg_config.h"
#include "CosimEngine.h"

// Forward declarations:
namespace Sysgen {
    class BlockDescriptor;
    class StdLogicVector;
    class StdLogicVectorVector;
}

namespace Sysgen {

    /// \ingroup includes
    /// \ingroup published
    /// \ingroup public_cosim
    /// \brief ShmemCosimEngine
    class SG_API ShmemCosimEngine : public CosimEngine {

    public:

        ShmemCosimEngine(const BlockDescriptor& bd);
        virtual ~ShmemCosimEngine() {}

        /// Opens and initializes the co-simulation platform.  This method is
        /// called prior to the start of simulation.
        virtual void open() = 0;

        /// Closes the co-simulation platform.
        virtual void close() = 0;

        /// Examines the current state of the co-simulation platform's output
        /// port specified by the portIndx parameter.
        virtual void read(uint32 portIndx, StdLogicVector& val) = 0;

        /// Forces the current state of the co-simulation platform's input
        /// port specified by the portIndx parameter.
        virtual void write(uint32 portIndx, const StdLogicVector& val) = 0;

        /// Steps the co-simulation platform by the specified number of iterations.
        virtual void run(uint32 steps) = 0;

        /// Reads an array of data from a shared memory bank.
        virtual void readArray(uint32 bank, uint32 addr, uint32 nwords,
                               Sysgen::StdLogicVectorVector& buffer) = 0;

        /// Writes an array of data to a shared memory bank.
        virtual void writeArray(uint32 bank, uint32 addr, uint32 nwords,
                                const Sysgen::StdLogicVectorVector& buffer) = 0;

    private:

        // ShmemCosimEngine's are prevented from being copied.
        // The class copy constructors and assignment operators are declared
        // private and never defined.
        ShmemCosimEngine(const ShmemCosimEngine&);  // not defined
        ShmemCosimEngine& operator= (const ShmemCosimEngine&);  // not defined
    };
}

#endif  //SHMEM_COSIM_ENGINE_H
