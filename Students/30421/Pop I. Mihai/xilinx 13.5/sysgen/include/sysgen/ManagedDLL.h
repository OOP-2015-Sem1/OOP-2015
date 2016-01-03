/*
 *  ManagedDLL.h
 *
 *  Copyright (c) 2004, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  System Generator interface for the management
 *                of dynamically linked libraries.
 */

#ifndef _MANAGEDDLL_H_
#define _MANAGEDDLL_H_

// System Generator Inclusions:
#include "sysgen/sg_config.h"

// Standard Library Inclusions:
#include <string>

// Forward declarations:
namespace Sysgen {
    class ManagedDLLImpl;
}

namespace boost {
    template<class T> class shared_ptr; 
}

//---------------------------------------------------------------------------

namespace Sysgen {
    /// \ingroup public_util    
    /// \brief Wrapper class for System Generator DLL's. Manages loading and unloading.
    class ManagedDLL
    {
    public:
        //! @name Constructors/Destructors
        //@{    
        /// Default
        ManagedDLL(void);
        /// Constructed from a string (the name of the DLL)
        ManagedDLL(const std::string &dll_name);
        /// Copy Constructor
        ManagedDLL(const ManagedDLL &q);
        /// Assignment Constructor
        ManagedDLL& operator=(const ManagedDLL &q);

        /// Virtual destructor. Note: the ManagedDLL class
        /// has the semantics of a shared pointer. When the
        /// last ManagedDLL reference to a DLL goes out of
        /// scope, the DLL will be unloaded.
        virtual ~ManagedDLL(void);
        //@}
        
        //! @name Class Interface
        //@{
        /// Get a pointer to a function exported by the dll.
        /// Will return null if no such function is exported.
        void* getFunctionPointer(const std::string &fname) const;
        /// Get the name of the dll.
        const std::string& getName() const;
        /// returns true if the object represents a loaded dll
        bool isValid() const;
        //@}
        
        //! @name Static Class Interface
        //@{
        /// Returns a ManagedDLL instance to the sysgen dll.
        /// Note: the sysgen dll never goes out of scope and is
        /// never unloaded.
        static const ManagedDLL& getSysgenDLL();
        //@}
        
    private:    	
        boost::shared_ptr<ManagedDLLImpl>* _impl;
    };

    bool operator==(ManagedDLL const & a, ManagedDLL const & b);
    
    bool operator<(ManagedDLL const & a, ManagedDLL const & b);
}


#endif // _MANAGEDDLL_H_
