/*
 *  ISimUtil.h
 *
 *  Copyright (c) 2005, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Public, stand-alone proceedures for
 *               System Generator MCode BlockDescriptors and
 *               PortDescriptors.
 */

#ifndef SYSGEN_ISIM_UTIL_H
#define SYSGEN_ISIM_UTIL_H

// Xilinx inclusions:
#include "sysgen/sg_config.h"

// Forward declarations:
namespace Sysgen {
    class BlockDescriptor;
}


namespace Sysgen {

    namespace Utility {

        /// \ingroup public_util
        namespace ISim {
            SG_API
            void createLogFile(BlockDescriptor &bd, bool val);
        }
    }
}

#endif // SYSGEN_ISIM_UTIL_H
