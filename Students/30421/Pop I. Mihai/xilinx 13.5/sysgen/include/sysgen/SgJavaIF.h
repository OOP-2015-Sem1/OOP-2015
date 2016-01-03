#ifndef SGJAVAIF_H
#define SGJAVAIF_H

#include "sysgen/sg_config.h"
#include "spil/process.h"
#include "spil/shared_library.h"

#include <string>
#include <jni.h>

/**
 * SgJavaIF is the Sysgen Java interface class.
 * The class starts a JVM delivered by Xilinx.
 * The SgJavaIF class makes sure only one JVM is started and
 * and it has a way to find the already created JVM.
 */

namespace Sysgen {

struct XlJavaInvocationFunctions {
  jint (JNICALL *CreateJavaVM)(JavaVM **pvm, void **env, void *args);
  jint (JNICALL *GetCreatedJavaVMs) (JavaVM ** vmBuf, jsize buflen, jsize *nVMS);
};

class SG_API SgJavaIF {
public:
  static SgJavaIF& getInstance();

  /**
   * Calls a public static method which returns an int number.
   * @param className      Fully qualified java class name,
   *                       e.g., com.xilinx.sysgen.corefunc.xlmult_j
   * @param methodName     The name of the method
   * @param parameters     A string representing the parameter list.
   *                       The parameter list is very close to the java syntax
   *                       e.g., (1, "str", 3L, 3.0, 4.3D, 0x010, true)
   *                       or   (3, {1, 2, 3}, false, {1L, 2L, 3L})
   * @param coreTriple     A string that identifies the core.  The format
   *                       of the string is: \<Core name\>|\<Vendor\>|\<version\>
   * @param dbg            For internal use only.  Do not use.
   * @return               The result of the static call
   * @throw                Bogus exception if anything wrong in JNI or in java.
   */
  virtual jlong callStaticLong(const std::string& className,
                               const std::string& methodName,
                               const std::string& parameters,
                               const std::string& coreTriple,
                               int dbg=0);

    /// Calls a public static method which returns an int number.
    /// \sa callStaticLong
  virtual int callStaticInt(const std::string& className,
                            const std::string& methodName,
                            const std::string& parameters,
                            const std::string& coreTriple,
                            int dbg=0);

    /// Calls a public static method which returns a string.
    /// \sa callStaticLong
  virtual std::string callStaticString(const std::string& className,
                                       const std::string& methodName,
                                       const std::string& parameters,
                                       const std::string& coreTriple,
                                       int dbg=0);

  virtual JavaVM *getJVM();
  virtual JNIEnv *getEnv();

private:

  /**
   * constructor SgJavaIF
   * Usually no parameters are required for this constructor.
   * As a matter of fact, passing any parameter is not recommended.
   * @param classname     The name for the CoreGen Java interface class
   * @param sysgen        The root of Sysgen
   * @param xilinx        The root of Xilinx software
   */
  SgJavaIF(std::string classname = "",
           std::string sysgen = "",
           std::string xilinx = "");

  // Make SgJavaIF non-copyable
  SgJavaIF(const SgJavaIF&);
  const SgJavaIF& operator=(const SgJavaIF&);

  virtual ~SgJavaIF();

  void shutdown();

#ifdef _MSC_VER
#pragma warning (disable:4251)
// warning 4251: (private) type has no dll interface
#endif

  JavaVM *jvm;
  JNIEnv *env;

  jobject coregenMasterObj;
  jobject invokeStaticMethod;
  jobject invokeStaticStringMethod;
  jobject shutdownMethod;

  jclass objectClass;

  jclass methodClass;
  jmethodID method_invoke;

  jclass integerClass;
  jclass longClass;
  jmethodID integer_intValue;
  jmethodID long_longValue;

  bool setupdone;

  SPIL::SharedLibrary jvmlib;
  SPIL::Process jvmproc;

  /**
   * finds two JNI invocation functions.
   * The two functions are: JNI_GetCreatedJavaVMs and JNI_CreateJavaVM
   * This method is machine dependent. So far it only works for windows.
   * The method loads a jvm.dll delivered by Xilinx software, then invokes
   * MS GetProcAddress to locate these functions.
   * @throw Bogus exception if anything wrong.
   */
  virtual void findInvFcns(Sysgen::XlJavaInvocationFunctions *ifn);

  /**
   * Sets up a Java VM.
   * It first invokes JNI_GetCreatedJavaVMs. If any JVM is created, invokes
   * jvm->AttachCurrentThread to the JVM.
   * If no JVM is created, invokes JNI_CreateJavaVM.
   * @param ifn        Pointer to invocation function structure
   * @param sysgen     The root of sysgen. If it is empty, setupJVM will find
   *                   the sysgen root. It is recommended to pass an empty
   *                   string.
   * @param xilinx     The root of xilinx software. If it is empty, the
   *                   setupJVM will find the appropriate xilinx root. It is
   *                   recommended to pass an empty string.
   * @throw Bogus exception if anything wrong.
   */
  virtual void setupJVM(Sysgen::XlJavaInvocationFunctions *ifn,
                        std::string sysgen,
                        std::string xilinx);

  /**
   * Creates a coregen java interface object through JNI.
   * It looks for a coregen interface class, invokes the Constructor(String).
   * @param classname   The name for the coregen interface class. If it's
   *                    empty, "com/xilinx/sysgen/java/util/CoreGenIF" is
   *                    assumed. It is recommended to pass an empty string.
   * @param xilinx      The name for the xilinx root. It it's empty,
   *                    setupCoreGenIF will find an appropriate xilinx root.
   *                    It is recommended to pass an empty string.
   * @throw Bogus exception if anything wrong.
   */
  virtual void setupCoreGenIFMaster(std::string aysgenjar,
                                    std::string javaexe,
                                    std::string xilinx,
                                    std::string myxilinx,
                                    std::string classpath);

  /**
   * Throws a Bogus exception is there is any java exception.
   * @throw Bogus exception if there is any java exception.
   */
  virtual void checkJavaException();

  virtual void startupServer(std::string javaexe,
                             std::string xilinx,
                             std::string myxilinx,
                             std::string classpath,
                             int masterPort);
}; // class SgJavaIF

} // namespace Sysgen

#endif // SGJAVAIF_H
