#ifndef _SYSGEN_ERROR_H_
#define _SYSGEN_ERROR_H_

// Xilinx
#include "sysgen/sg_config.h"

// SL
#include <stdexcept>
#include <ostream>
#include <string>

#define SYSGEN_ERROR(error_message) ::Sysgen::Error(error_message,"",__FILE__,__LINE__)

#define SYSGEN_ERROR_DETAILED(error_summary, error_detail) ::Sysgen::Error(error_message,error_detail,__FILE__,__LINE__)

#define SYSGEN_ASSERT(cond) if(!(cond)) {throw ::Sysgen::Error("SYSGEN_ASSERT failed: "#cond, "",__FILE__,__LINE__);}

namespace Sysgen
{
/// \ingroup includes
/// \ingroup public_utility
/// \ingroup published
/// \brief Public System Generator Error/Exception class
class SG_API Error : public std::exception
{
public:
    /// Construct with summary message
    /// Include the file name and line number for debugging usage
    Error(const std::string& in_summary, 
          const std::string& in_details = "", 
          const std::string& file_name = "", 
          int line_number = -1);
    /// Copy Constructor
    Error(const Error& e);
    
    /// Virtual Destructor
    virtual ~Error() throw();
    
    /// Overrides std::exception what()
    virtual const char *what() const throw();

    /// Comparison Operator
    bool operator==(const Error& lhs) const;

    /// Return true is summary and details contain no text
    bool empty() const;
    
    std::string summary() const { return _summary; }
    std::string details() const { return _details; }

private:
    std::string _summary;
    std::string _details;

    std::string _file;
    int _line;

protected:
    // A temporary string used to hold the string returned by the
    // what() function call
    mutable std::string _whatString;
};

class SG_API FatalError : public std::bad_exception
{
public:
    /// Construct with summary message
    /// Include the file name and line number for debugging usage
    FatalError(const std::string& details = "");
    
    /// Virtual Destructor
    virtual ~FatalError() throw() {}
    
    /// Overrides std::bad_exception what()
    virtual const char *what() const throw();
private:
    std::string _details;
};

struct ConfigError {};

struct FlowError {};


// The InternalError class is being obsoleted.  Use the SYSGEN_ERROR
// macro instead
class SG_API InternalError : public Error
{
public:
    InternalError(const std::string& m, const std::string& f, int l) :
        Error(m,"",f,l) {}
    virtual ~InternalError() throw() {}
};

}

#endif // _SYSGEN_ERROR_H_
