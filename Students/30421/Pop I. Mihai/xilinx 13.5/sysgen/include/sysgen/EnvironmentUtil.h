/*
 *  Environment.h
 *
 *  Copyright (c) 2008, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Environment information used by System Generator.
 */

#ifndef SYSGEN_ENVIRONMENT_H
#define SYSGEN_ENVIRONMENT_H

// Xilinx
#include "sysgen/sg_config.h"

// Standard Library
#include <string>

namespace Sysgen {
/// \ingroup public_utility
namespace Utility {

    /// \brief Sanitize the environment settings for Xilinx ISE.
    SG_API void sanitizeISEEnvironment();

    /// \brief Sanitize the environment settings for Xilinx EDK.
    SG_API void sanitizeEDKEnvironment();

    /// \brief Sanitize the environment settings for Xilinx Sysgen.
    SG_API void sanitizeSysgenEnvironment();

    /// \brief Returns the platform specific tag used by Xilinx tools.
    SG_API const std::string& getXilinxPlatformTag();

    /// \brief Returns the name of the directory pointed by an envrionment variable.
    SG_API std::string getInstallDir(const std::string&);

    /// \brief Returns the name of the directory where Xilinx PlanAhead is installed.
    SG_API const std::string& getPlanAheadInstallDir();

    /// \brief Returns the path where Xilinx PlanAhead exeutables are installed relative to the xilinx root
    SG_API const std::string& getRelativePlanAheadBinDir();

    /// \brief Returns the name of the bin directory where Xilinx PlanAhead exeutables are installed.
    SG_API const std::string& getPlanAheadBinDir();

    /// \brief Returns the path where Xilinx PlanAhead libraries are installed relative to the xilinx root
    SG_API const std::string& getRelativePlanAheadLibDir();

    /// \brief Returns the name of the lib directory where Xilinx PlanAhead library files are installed.
    SG_API const std::string& getPlanAheadLibDir();

    /// \brief Returns the name of the directory where Xilinx PlanAhead strategy files are saved.
    SG_API const std::string& getPlanAheadStrategyDir();

    /// \brief Returns the name of the directory where Xilinx PlanAhead user strategy files are saved.
    SG_API const std::string& getPlanAheadUserStrategyDir();

    /// \brief Returns the name of the directory where Xilinx ISE is installed.
    SG_API const std::string& getISEInstallDir();

    /// \brief Returns the path where Xilinx ISE exeutables are installed relative to the xilinx root
    SG_API const std::string& getRelativeISEBinDir();

    /// \brief Returns the name of the bin directory where Xilinx ISE exeutables are installed.
    SG_API const std::string& getISEBinDir();

    /// \brief Returns the path where Xilinx ISE libraries are installed relative to the xilinx root
    SG_API const std::string& getRelativeISELibDir();

    /// \brief Returns the name of the lib directory where Xilinx ISE library files are installed.
    SG_API const std::string& getISELibDir();

    /// \brief Returns the name of the directory where Xilinx EDK is installed.
    SG_API const std::string& getEDKInstallDir();

    /// \brief Returns the path where Xilinx EDK exeutables are installed relative to the EDK root
    SG_API const std::string& getRelativeEDKBinDir();

    /// \brief Returns the name of the bin directory where Xilinx EDK exeutables are installed.
    SG_API const std::string& getEDKBinDir();

    /// \brief Returns the path where Xilinx EDK libraries are installed relative to the EDK root
    SG_API const std::string& getRelativeEDKLibDir();

    /// \brief Returns the name of the lib directory where Xilinx EDK library files are installed.
    SG_API const std::string& getEDKLibDir();

    /// \brief Returns the name of the directory where System Generator is installed.
    SG_API const std::string& getSysgenInstallDir();

    /// \brief Returns the name of the directory where System Generator executables are installed.
    SG_API const std::string& getSysgenBinDir();

    /// \brief Returns the name of the directory where System Generator shared libraries are installed.
    SG_API const std::string& getSysgenLibDir();

    /// \brief Returns the name of the directory where System Generator scripts are installed.
    SG_API const std::string& getSysgenScriptsDir();

    /// \brief Returns the name of the directory where System Generator HDLs are installed.
    SG_API const std::string& getSysgenHDLDir();

    /// \brief Returns the name of the directory where System Generator data files are installed.
    SG_API const std::string& getSysgenDataDir();

    /// \brief Returns the name of the directory where System Generator help files are installed.
    SG_API const std::string& getSysgenHelpDir();

    /// \brief Returns the path to System Generator shared library.
    SG_API const std::string& getSysgenDllPath();

    /// \brief Returns the path to System Generator blockset library.
    SG_API const std::string& getSysgenBlocksetDllPath();

    /// \brief Returns the path for XPS executable.
    SG_API const std::string& getXpsPath();

    /// \brief Returns the path for GCC executable.
    SG_API const std::string& getGccPath();

    /// \brief Returns the path for ISE Fuse executable.
    SG_API const std::string& getFusePath();

    /// \brief Returns the path for Tcl library.
    SG_API const std::string& getTclLibraryPath();

    /// \brief Returns the system temporary directory.
    SG_API const std::string& getTempDir();

    /// \brief Returns the per-user temporary directory.
    SG_API const std::string& getUserTempDir();

    /// \brief Returns the path for ISE XilPerl exeutable.
    SG_API const std::string& getXilperlPath();

    /// \brief  Returns the root path for all the sysgen caches
    SG_API const std::string& getCacheRootPath();

    /// \brief Returns the path for the sysgen core cache
    SG_API const std::string& getSgCoreCachePath();

    /// \brief Returns the path for the repository where block parameter template file is located 
    SG_API const std::string& getBlockParamTemplateDir();

    /// \brief Returns the path for matlab installation directory
    /// Clients should not pass any inputs to this function.
    /// Usage : std::string a = getMatlabInstallDir();
    SG_API const std::string& getMatlabInstallDir(std::string matlabroot = "");
    
    /// \brief Returns the location of file in provided colon or semicolon separated path
    SG_API std::string findFileInPath(const std::string & file, const std::string & path);
    
    /// \bried Returns the location of file in PATH environment variable
    SG_API std::string findFileInPath(const std::string & file);

    /// \brief Returns the location of java.exe we use for CoreGenIFClient
    SG_API const std::string& getJavaPath(std::string matlab_version = "");

} // namespace Utility
} // namespace Sysgen

#endif // SYSGEN_ENVIRONMENT_H
