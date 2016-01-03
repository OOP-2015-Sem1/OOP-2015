#ifndef SGTCLIF_H
#define SGTCLIF_H

#include "sysgen/sg_config.h"
#include "spil/shared_library.h"

#include <string>
#include <map>

#if defined(OS_WINDOWS)
#define TCL_PROC FARPROC
#else
#define TCL_PROC void *
#endif

/**
 * SgTclIF is the sysgen tcl interface class.
 * The class loads and starts a tcl interpreter libtcl84.dll that
 * is delivered by the ISE tools. This tcl interpreter is also
 * used by coregen for calling its GUI utilities.
 */

namespace Sysgen {

/**
 * The XlTclInterpreterObject is used to communicate
 * with the TCL interpreter from Sysgen
 */
typedef struct XlTclInterpreterObject {
        char *result;
        void (*freeProc) (char *blockPtr);
        int errorLine;
} XlTclInterpreterObject;

typedef struct XlTclInvocationsFunctionTable {
    const char *name;
    TCL_PROC *ptr;
} XlTclInvocationsFunctionTable;

class SG_API SgTclIF {
public:
  /**
   * constructor SgTclIF
   * No parameters required.
   */
    SgTclIF(bool keep_alive = false, bool overload_puts = true);

  /**
   * destructor SgTclIF
   * Calls cleanup if required.
   */
  virtual ~SgTclIF();
  /**
   * Calls a tcl Proc that returns a string
   * @param source         Fully qualified source file name,
   *                       e.g., c:/matlab/toolbox/xilinx/sysgen/bin/test_utils.tcl
   * @param methodName     Fully qualified method name e.g. test_utils::call_function
   * @param parameters     A string representing the parameter list.
   * @return               The result of the script call.
   * @throw                                Throws error if unsuccessful in calling the script.
   */
  virtual std::string callProc(const std::string& source,
                               const std::string& methodName,
                               const std::string& parameters);
  /**
   * Calls a tcl Proc that does not return anything.This might be because the
   * tcl proc might only setup global variables
   * @param source         Fully qualified source file name,
   *                       e.g., c:/matlab/toolbox/xilinx/sysgen/bin/test_utils.tcl
   * @param methodName     Fully qualified method name e.g. test_utils::call_function
   * @param parameters     A string representing the parameter list.
   * @param is_for_init    inidicates the script is for init, the function keeps a list of commands that are for init, if it's already invoked, the command will be skipped, this is for when keep_alive is true.
   * @throw                                Throws error if unsuccessful in calling the script.
  */
  virtual void callProcNoReturn(const std::string& source,
                                const std::string& methodName,
                                const std::string& parameters,
                                bool is_for_init = false);

  /**
   * Reads a global variable from the tcl interpreter
   * @param global variable name
   *                       e.g., SgVar
   * @return               The value of the global variable
   */
  virtual std::string getGlobal(const std::string& var);

  /**
   * Sources/Includes a file in the tcl shell
   * @param fully qualified source file name
   *                       e.g., c:/matlab/toolbox/xilinx/sysgen/bin/test_utils.tcl
   * @return
   */
  virtual void sourceFile(const std::string& source);

  /**
   * @return the sysgen tcl script directory, i.e. sysgen/scripts
   */
  static std::string getTclUtilsLocation();

  /**
   * @return the cwd, chdir to sysgen/scripts
   */
  static std::string prepareForTcl();


 /**
  * Asserts that no error occurred during
  * invocation of the Tcl Interpreter
  * @throw if assertion failed.
  */
  bool assertSuccess();

private:

#ifdef _MSC_VER
#pragma warning (disable:4251)
// warning 4251: (private) type has no dll interface
#endif
  /**
  * Sets up a Tcl Interpreter
  * Loads the tcl interpreter and sets up the gateway/interfacefunctions
  * @throw Bogus exception if anything wrong.
  */
  void setupTclInterpreter();

  /**
  * Sets up a Tcl Interpreter
  * Loads the tcl interpreter
  * @throw exception if loading was unsuccessful
  */
  void loadTclInterpreter();

  /**
  * Attaches the private gateway function pointers
  * to the tcl interpreter
  * @throw exception if unsuccessful
  */
  void attachGatewayFunctions();

  /**
  * Initializes the TCL interpreter
  * @throw exception if unsuccessful
  */
  void initializeInterpreter();

  /**
  * Shuts down the TCL interpreter
  */
  void shutDown();

  bool _verbosity;

  bool _keep_alive;

  bool _overload_puts;

  bool _tcl_started;

  // the following can be static if we don't shut down
  XlTclInterpreterObject *(*_tcl_CreateInterp) ();

  void *(*_tcl_DeleteInterp) (XlTclInterpreterObject*);

  int *(*_tcl_Eval) (XlTclInterpreterObject *, const char*);

  const char *(*_tcl_GetVar) (XlTclInterpreterObject *, const char*, int);

  SPIL::SharedLibrary _shlib;

  XlTclInterpreterObject *_interp;

  std::map<std::string, bool> _initProcPool;

};

}

#endif // SGTCLIF_H
