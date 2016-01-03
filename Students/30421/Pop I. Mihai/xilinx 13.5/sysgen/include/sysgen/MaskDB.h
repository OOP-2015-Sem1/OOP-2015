/*
 *  MaskDB.h
 *
 *  Copyright (c) 2003, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  A simple typedef of MaskDB to PTable. Class MaskDB
 *                formerly served the purpose now served by the PTable
 *                class. For new code, please include PTable.h directly.
 */

#ifndef SYSGEN_MASK_DB_H
#define SYSGEN_MASK_DB_H

#include "sysgen/PTable.h"

namespace Sysgen {
    /// \ingroup public_util
    /// \brief Deprecated Proxy for PTable Class.  Use PTable directly instead.
    typedef class PTable MaskDB;
}

#endif //SYSGEN_MASK_DB_H

