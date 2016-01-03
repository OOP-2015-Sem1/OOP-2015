/*
 *  Util.h
 *
 *  Copyright (c) 2005-2008, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Public, stand-alone functions for use with
 *               System Generator.
 */

#ifndef SYSGEN_UTIL_H
#define SYSGEN_UTIL_H

// Xilinx:
#include "sysgen/sg_config.h"
#include "sysgen/EnvironmentUtil.h"

// Standard Library:
#include <string>

namespace Sysgen {
    /// \ingroup public_utility
    namespace Utility {
        /// \brief Returns the greatest common denominator of the two input values.
        SG_API unsigned gcd(unsigned a, unsigned b);

        /// \brief Returns the greatest common denominator of the two input values.
        SG_API double gcd(double a, double b, double tol=1e-15);

        /// parse the fileset.txt and get the ise version number
        SG_API std::string getISEVersion(const std::string& xilinx = "");

        /// parse the version file and get the sysgen version number
        SG_API std::string getSysgenVersion();
    } // namespace Utility
} // namespace Sysgen

#endif // SYSGEN_UTIL_H
