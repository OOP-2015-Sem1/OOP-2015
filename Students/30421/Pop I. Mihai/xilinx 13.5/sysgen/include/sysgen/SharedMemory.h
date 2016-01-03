/*
 *  SharedMemory.h
 *
 *  Copyright (c) 2004, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description:  System Generator Shared Memory Interface
 */

#ifndef SYSGEN_SHARED_MEMORY_H
#define SYSGEN_SHARED_MEMORY_H

#include "sysgen/sg_config.h"
#include "sysgen/IntType.h"
#include "sysgen/StdLogicVector.h"

#include <string>

// forward class declarations:
namespace Sysgen
{
class SharedMemoryImpl;
class LockableSharedMemoryImpl;
class SharedMemoryProxyImpl;

class SG_API PTable;
class XFix;
class SharedMemory;
class SG_API BlockDescriptor;
class SG_API PortDescriptor;


enum {
	ARYTH_TYPE_SIGNED = 1,
	ARYTH_TYPE_UNSIGNED,
	ARYTH_TYPE_FLOATING_POINT
};

static const std::string ARITH_TYPE = "arith_type";
static const std::string SHARED_MEMORY_NAME = "shared_memory_name";
static const std::string INIT = "init";
static const std::string NBITS = "n_bits";
static const std::string BINPT = "bin_pt";
static const std::string PERIOD = "period";
static const std::string EXPLICIT_DATA_TYPE = "explicit_data_type";
static const std::string OWNERSHIP = "ownership";
static const std::string IS_FLOATING_BLOCK = "is_floating_block";
static const std::string DATA_IN = "data_in";
static const std::string DATA_OUT = "data_out";
static const std::string EN = "en";
static const std::string DIN = "din";
static const std::string DOUT = "dout";

static const std::string MUTEX = "mutex";
static const std::string MODE = "mode";
static const std::string DEPTH = "depth";
static const std::string WE = "we";
static const std::string REQ = "req";
static const std::string GRANT = "grant";
static const std::string RE = "re";
static const std::string ADDR = "addr";
static const std::string LATENCY = "latency";

static const std::string AXI_FIFO_DEPTH = "input_depth_axis";
static const std::string S_AXIS_TVALID = "s_axis_tvalid";
static const std::string S_AXIS_TREADY = "s_axis_tready";
static const std::string S_AXIS_TDATA = "s_axis_tdata";
static const std::string S_AXIS_TSTRB = "s_axis_tstrb";
static const std::string S_AXIS_TKEEP = "s_axis_tkeep";
static const std::string S_AXIS_TLAST = "s_axis_tlast";
static const std::string S_AXIS_TID = "s_axis_tid";
static const std::string S_AXIS_TDEST = "s_axis_tdest";
static const std::string S_AXIS_TUSER = "s_axis_tuser";

static const std::string MASK = "mask";

static const std::string INIT_VECTOR = "initVector";
static const std::string INIT_BIT_VECTOR = "init_bit_vector";

static const std::string TIME_OUT = "time_out";
static const std::string WRITE_MODE = "write_mode";

static const std::string RST = "rst";
static const std::string HAS_ASYNC_RST = "has_arst";
static const std::string FULL = "full";
static const std::string PERCENT_FULL = "percent_full";
static const std::string PERCENT_NBITS = "percent_nbits";

static const std::string MEMORY_INIT_COE = "memory_init.coe";




/*
 * whether the shared memory block uses fixed and floating point data type
 */
SG_API bool isFloatingPointDataType(const PTable& mask);

/*
 * read from shared memory object
 */
SG_API void readFromSharedMemory(XFix *dout, SharedMemory *reg);

/*
 * set port data type based on block mask
 */
SG_API void setPortDataTypeFromMask(BlockDescriptor *blockDescriptor,
		                            PortDescriptor *portDescriptor,
		                            PTable& mask);

/*
 * get data type from block mask
 */
SG_API SysgenType getDataTypeFromMask(const BlockDescriptor *blockDescriptor, const PTable &mask);

}

namespace Sysgen
{
    /// \ingroup includes
    /// \file SharedMemory.h

    /// \ingroup published
    /// \ingroup public_utility
    /// \brief SharedMemory
    class SG_API SharedMemory
    {
        friend class SharedMemoryImpl;
        friend class LockableSharedMemoryImpl;
        friend class SharedMemoryProxyImpl;
    public:
        static const int NEVER = -1;   /**< Used to parameterize methods
                                          with timeout settings such that
                                          they never timeout. */

        static const int INHERIT = -1; /**< Used inherit characteristics
                                          from an already created
                                          shared memory. */

        enum creation_tag_dispatch {
            creation_tag  /**< Used exclusively to distinguish the
                             constructor that creates the physical
                             memory from the constructor that accesses
                             an already extant physical memory*/
        };

        enum owner_type {
            base,     /**< Physical memory created as SharedMemory */
            lockable, /**< Physical memory created as LockableSharedMemory */
            proxy     /**< Physical memory created as SharedMemoryProxy */
        };

        SharedMemory(const std::string &name,
                     int nwords,
                     int word_size,
                     creation_tag_dispatch);

        SharedMemory(const std::string &name,
                     unsigned start_address = 0,
                     int nwords = INHERIT,
                     int word_size = INHERIT,
                     double timeout_sec = NEVER);

        virtual ~SharedMemory();

        std::string getName() const;
        unsigned   getNWords() const;
        unsigned   getWordSize() const;
        owner_type getOwnerType() const;

        virtual bool couldBlockOnReadOrWrite() const;

        virtual bool read(unsigned addr, StdLogicVector &value,
                          double timeout_sec = NEVER) const;

        virtual bool write(unsigned addr, const StdLogicVector &value,
                           double timeout_sec = NEVER);

        virtual bool readArray(unsigned addr, unsigned nwords,
                               StdLogicVectorVector &buffer,
                               double timeout_sec = NEVER) const;

        virtual bool writeArray(unsigned addr, unsigned nwords,
                                const StdLogicVectorVector &buffer,
                                double timeout_sec = NEVER);

    private:
        /** SharedMemory objects can not be copied.
         * The copy constructor is declared as private and not defined.
         */
        SharedMemory(const SharedMemory&);
        /** SharedMemory objects can not be copied.
         * The assignment operator is declared as private and not defined.
         */
        SharedMemory& operator= (const SharedMemory&);

    protected:
        enum protected_constructor_tag_dispatch {protected_constructor_tag};

        SharedMemory(const std::string &name,
                     int nwords,
                     int word_size,
                     protected_constructor_tag_dispatch);

        SharedMemory();

        SharedMemoryImpl *_impl;
    };

    class SG_API LockableSharedMemory : public Sysgen::SharedMemory
    {
    public:
        static const int NEVER = -1;
        static const int INHERIT = -1;

        typedef void (*callback) (LockableSharedMemory&, void*);

        LockableSharedMemory(const std::string &name,
                             int nwords,
                             int word_size,
                             creation_tag_dispatch);

        LockableSharedMemory(const std::string &name,
                             unsigned start_address = 0,
                             int nwords = INHERIT,
                             int word_size = INHERIT,
                             double timeout_sec = 15.0);

        virtual ~LockableSharedMemory();

        virtual bool couldBlockOnReadOrWrite() const {return true;};

        virtual bool acquireLock(double timeout_sec = NEVER);

        virtual bool acquireLock(callback function, void* arg,
                                 double timeout_sec = NEVER);

        virtual bool lockedByMe() const;

        virtual void releaseLock();



        virtual const StdLogicVectorVector& viewAsStdLogicVectorVector() const;
        virtual StdLogicVectorVector& viewAsStdLogicVectorVector();

        const uint32* getRawDataPtr() const;
        uint32* getRawDataPtr();

    private:
        LockableSharedMemoryImpl *_impl;
    };

    class SG_API SharedMemoryProxy : public Sysgen::SharedMemory
    {
        friend class NamedPipeWriterProxy;
        friend class NamedPipeReaderProxy;
        //friend class Sysgen::XCosimShmemManager;

    public:
        /**
         * Used to encode information passed to
         * SharedMemoryProxy::requestServicer callback functions. For details,
         * see the SharedMemoryProxy constructor documentation.
         */
        struct Request
        {
            enum Type {
                read_request,  /**< client wants to read the memory */
                write_request, /**< client wants to write to the memory */
                addr_request,  /**< client wants memory address pointer */
                peek_request   /**< client wants to peek current memory value */
                } type;
            unsigned start_address;  /**< first address to read / write */
            unsigned nwords;         /**< number of words to read / write */
        };

        static const int NEVER = -1;

        /**
         * A function pointer, of the type declared by this typedef,
         * is passed to the constructor of the SharedMemoryProxy
         * constructor. See the constructor documentation for details.
         */
        typedef void (*requestServicer) (const Request&, SharedMemoryProxy&,
                                         void *arg);

        SharedMemoryProxy(const std::string &name,
                          int nwords,
                          int word_size,
                          requestServicer rs,
                          void* rs_arg=NULL);


        ~SharedMemoryProxy();

        virtual bool couldBlockOnReadOrWrite() const {return false;};

        void service();

        virtual const StdLogicVectorVector& viewAsStdLogicVectorVector() const;

        virtual StdLogicVectorVector& viewAsStdLogicVectorVector();

        const uint32* getRawDataPtr() const;
        uint32* getRawDataPtr();
        void setWriteAddr(unsigned addr);

    private:
        SharedMemoryProxyImpl *_impl;
    };

    class SG_API NamedPipeWriter
    {
        friend class NamedPipeWriterProxy;
    public:
        static const int NEVER = -1;

        NamedPipeWriter(const std::string &name,
                        int nwords,
                        int word_size);

        NamedPipeWriter(const std::string &name,
                        int nwords,
                        int word_size,
                        double time_out);

        ~NamedPipeWriter();

        bool write(const StdLogicVector &value,
                   double timeout_sec = NEVER);

        bool writeArray(unsigned nwords,
                        const StdLogicVectorVector &buffer,
                        double timeout_sec = NEVER);

        unsigned getNWords() const;
        unsigned getWordSize() const;

        bool isFull(double timeout_sec = NEVER) const;
        unsigned numAvailable(double timeout_sec = NEVER) const;
        void clear();
        bool clearedByReader();

    private:
        /** NamedPipeWriter objects can not be copied.
         * The copy constructor is declared as private and not defined.
         */
        NamedPipeWriter(const NamedPipeWriter&);  // not defined
        /** NamedPipeWriter objects can not be copied.
         * The assignment operator is declared as private and not defined.
         */
        //NamedPipeWriter& operator= (const NamedPipeWriter&) {}  // not defined
        void operator= (const NamedPipeWriter&) {}

        SharedMemoryImpl* _impl;
    protected:
        NamedPipeWriter();
    };

    class SG_API NamedPipeWriterProxy : public Sysgen::NamedPipeWriter
    {
    public:
        static const int NEVER = -1;

        NamedPipeWriterProxy(const std::string &name,
                             int nwords,
                             int word_size,
                             SharedMemoryProxy::requestServicer rs,
                             void* rs_arg=NULL);

        ~NamedPipeWriterProxy();
        void service();

    private:
        /** NamedPipeWriterProxy objects can not be copied.
         *  The copy constructor is declared as private and not defined.
         */
        NamedPipeWriterProxy(const NamedPipeWriterProxy&);  // not defined
        /** NamedPipeWriter objects can not be copied.
         *  The assignment operator is declared as private and not defined.
         */
        NamedPipeWriterProxy& operator= (const NamedPipeWriterProxy&);  // not defined
        SharedMemoryProxy* _smp;
    };

    class SG_API NamedPipeReader
    {
        friend class NamedPipeReaderProxy;
    public:
        static const int NEVER = -1;
        static const int INHERIT = -1;

        NamedPipeReader(const std::string &name,
                        int nwords = INHERIT,
                        int word_size = INHERIT,
                        double timeout_sec = 15.0);

        NamedPipeReader(int nwords,
                        int word_size,
                        const std::string &name);

        ~NamedPipeReader();

        bool peek(StdLogicVector &value,
                  double timeout_sec = NEVER) const;

        bool read(StdLogicVector &value,
                  double timeout_sec = NEVER);

        bool readArray(unsigned nwords,
                       StdLogicVectorVector &buffer,
                       double timeout_sec = NEVER);

        unsigned getNWords() const;
        unsigned getWordSize() const;

        bool isEmpty(double timeout_sec = NEVER) const;
        unsigned numAvailable(double timeout_sec = NEVER) const;
        void clear();
        bool clearedByWriter();

    private:
        /** NamedPipeReader objects can not be copied.
         * The copy constructor is declared as private and not defined.
         */
        NamedPipeReader(const NamedPipeReader&);  // not defined
        /** NamedPipeReader objects can not be copied.
         * The assignment operator is declared as private and not defined.
         */
        NamedPipeReader& operator= (const NamedPipeReader&);  // not defined

        SharedMemoryImpl* _impl;

    protected:
        NamedPipeReader();
    };

    class SG_API NamedPipe
    {
    public:
        enum Mode { WRITE_ONLY, READ_ONLY };

        static const int NEVER = -1;

        NamedPipe(const std::string &name,
                  int nwords,
                  int word_size,
                  Mode mode);

        NamedPipe(const std::string &name,
                  int nwords,
                  int word_size,
                  double time_out);

        ~NamedPipe();

        bool write(const StdLogicVector &value,
                   double timeout_sec = NEVER);

        bool writeArray(unsigned nwords,
                        const StdLogicVectorVector &buffer,
                        double timeout_sec = NEVER);

        bool peek(StdLogicVector &value,
                  double timeout_sec = NEVER) const;

        bool read(StdLogicVector &value,
                  double timeout_sec = NEVER);

        bool readArray(unsigned nwords,
                       StdLogicVectorVector &buffer,
                       double timeout_sec = NEVER);

        unsigned getNWords() const;
        unsigned getWordSize() const;
        Mode getMode() const { return _mode; }

        bool isFull(double timeout_sec = NEVER) const;
        bool isEmpty(double timeout_sec = NEVER) const;
        unsigned numAvailable(double timeout_sec = NEVER) const;
        void clear();
        bool clearedByReader();
        bool clearedByWriter();

    private:
        /** NamedPipe objects can not be copied.
         * The copy constructor is declared as private and not defined.
         */
        NamedPipe(const NamedPipe&);  // not defined
        /** NamedPipe objects can not be copied.
         * The assignment operator is declared as private and not defined.
         */
        //NamedPipe& operator= (const NamedPipe&) {}  // not defined
        void operator= (const NamedPipe&) {}

        Mode _mode;
        SharedMemoryImpl* _impl;
    };

    class SG_API NamedPipeReaderProxy : public Sysgen::NamedPipeReader
    {
    public:
        static const int NEVER = -1;

        NamedPipeReaderProxy(const std::string &name,
                             int nwords,
                             int word_size,
                             SharedMemoryProxy::requestServicer rs,
                             void* rs_arg=NULL);

        ~NamedPipeReaderProxy();

        void service();

    private:
        /** NamedPipeReaderProxy objects can not be copied.
         * The copy constructor is declared as private and not defined.
         */
        NamedPipeReaderProxy(const NamedPipeReaderProxy&);  // not defined
        /** NamedPipeReader objects can not be copied.
         * The assignment operator is declared as private and not defined.
         */
        NamedPipeReaderProxy& operator= (const NamedPipeReaderProxy&);  // not defined
        SharedMemoryProxy* _smp;
    };

} // namespace Sysgen

#endif // SYSGEN_SHARED_MEMORY_H
