/*
 *  MCodeModelDescriptor.h
 *
 *  Copyright (c) 2006, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  Base Class for mcode blocks.
 */

#ifndef MCODEMODELDESCRIPTOR_H
#define MCODEMODELDESCRIPTOR_H

#include "sysgen/BlockDescriptor.h"

namespace Sysgen {

/// \ingroup includes
/// \ingroup descriptors
/// \ingroup public_config
/// \brief BlockDescriptor public interface

class SG_API MCodeModelDescriptor : public BlockDescriptor
{
public:
    //! @name Constructors/Desctructors
    //@{
    /// constructor
    MCodeModelDescriptor();
    
    /// Destructor
    virtual ~MCodeModelDescriptor();
    //@}
    
    //! @name Configuration API
    //@{
    /// See Sysgen::BlockDescriptor::configureSimulation
    virtual void configureSimulation();
    
    /// See Sysgen::BlockDescriptor::configureNetlistInterface
    virtual void configureNetlistInterface();

    /// See Sysgen::BlockDescriptor::configureNetlist
    virtual void configureNetlist();

protected:
    /// Push parameters to mcode
    virtual void defineMCodeModel() = 0;
    //@}

    /// Wraps the call to defineMCodeModel to ensure that it is only called once
    void defineMCodeModelWrapper();

private:    
    bool mcode_parameters_defined_;
};

}

#endif /* MCODEMODELDESCRIPTOR_H */
