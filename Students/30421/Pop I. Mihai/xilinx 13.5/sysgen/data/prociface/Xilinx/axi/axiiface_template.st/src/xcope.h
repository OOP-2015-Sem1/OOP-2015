//////////////////////////////////////////////////////////////////////
//
// Copyright (c) 2010 Xilinx, Inc.  All rights reserved.
//
// Xilinx, Inc.  XILINX IS PROVIDING THIS DESIGN, CODE, OR INFORMATION
// "AS IS" AS  A COURTESY TO YOU.  BY PROVIDING  THIS DESIGN, CODE, OR
// INFORMATION  AS  ONE   POSSIBLE  IMPLEMENTATION  OF  THIS  FEATURE,
// APPLICATION OR  STANDARD, XILINX  IS MAKING NO  REPRESENTATION THAT
// THIS IMPLEMENTATION  IS FREE FROM  ANY CLAIMS OF  INFRINGEMENT, AND
// YOU ARE  RESPONSIBLE FOR OBTAINING  ANY RIGHTS YOU MAY  REQUIRE FOR
// YOUR  IMPLEMENTATION.   XILINX  EXPRESSLY  DISCLAIMS  ANY  WARRANTY
// WHATSOEVER  WITH RESPECT  TO  THE ADEQUACY  OF THE  IMPLEMENTATION,
// INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OR REPRESENTATIONS THAT
// THIS IMPLEMENTATION  IS FREE  FROM CLAIMS OF  INFRINGEMENT, IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
// 
//////////////////////////////////////////////////////////////////////

#ifndef __XCOPE_H__
#define __XCOPE_H__

#include "xbasic_types.h"

// Basic data types
typedef char    uint8_t;
typedef Xuint16 uint16_t;
typedef u32 uint32_t;

// Raw address and processing data types
typedef unsigned xc_raw_addr_t;
typedef unsigned xc_word_t;

// Read-only address data type
typedef xc_raw_addr_t xc_r_addr_t;
// Write-only address data type
typedef xc_raw_addr_t xc_w_addr_t;
// Read-Write address data type
typedef xc_raw_addr_t xc_addr_t;

typedef u32 xc_status_t;
// error codes between +1023 and -1024 are reserved for XCOPE use
#define XC_SUCCESS 0    // non-negative values denote ok
#define XC_FAILURE -1   // negative values denote error

typedef struct {
    int n_bits;
    xc_word_t raw_bits[0];
} xc_bits_t;

typedef struct {
    int bin_pt;
    u32 attr;
    xc_bits_t bits;
} xc_fix_t;

typedef uint16_t xc_id_t;

typedef struct {
    xc_r_addr_t dout;
    u32 n_bits;
    u32 bin_pt;
    // u32 attr;
} xc_from_reg_t;

typedef struct {
    xc_addr_t din;
    u32 n_bits;
    u32 bin_pt;
    // u32 attr;
} xc_to_reg_t;

typedef struct {
    xc_r_addr_t dout;
    xc_r_addr_t percentfull;
    xc_r_addr_t empty;
    u32 n_bits;
    u32 bin_pt;
    // u32 attr;
} xc_from_fifo_t;

typedef struct {
    xc_w_addr_t din;
    xc_r_addr_t percentfull;
    xc_r_addr_t full;
    u32 n_bits;
    u32 bin_pt;
    // u32 attr;
} xc_to_fifo_t;

typedef struct {
    xc_addr_t addr;
    // xc_r_addr_t *grant;
    // xc_w_addr_t *req;
    u32 n_bits;
    u32 bin_pt;
    // u32 attr;
} xc_shram_t;

typedef struct {
    xc_from_reg_t  *from_regs;
    xc_to_reg_t    *to_regs;
    xc_from_fifo_t *from_fifos;
    xc_to_fifo_t   *to_fifos;
    xc_shram_t     *shrams;
} xc_memmap_t;

#ifdef __XCOPE_CDMA__
/**
* Only used if PCore is connected to an AXI CDMA
*/
#include "xaxicdma.h"
#define SUBMIT_TRIES 5
#define RESET_LOOP_COUNT 5

/**
 * advance iface data structure for advanced data transfer (e.g., burst transfer with a Central DMA)
 */
struct struct_XC_CdmaIface {
    u32 version;
    // function pointers to low-level drivers
    xc_status_t (*xc_create)(struct struct_XC_CdmaIface **, void *);
    xc_status_t (*xc_release)(struct struct_XC_CdmaIface **);
    xc_status_t (*xc_open)(struct struct_XC_CdmaIface *);
    xc_status_t (*xc_close)(struct struct_XC_CdmaIface *);
    xc_status_t (*xc_read)(struct struct_XC_CdmaIface *, u32 *, uint32_t *);
    xc_status_t (*xc_write)(struct struct_XC_CdmaIface *, u32 *, const uint32_t);
    xc_status_t (*xc_get_shmem)(struct struct_XC_CdmaIface *, const char *, void **);
    xc_from_reg_t  *from_regs;
    xc_to_reg_t    *to_regs;
    xc_from_fifo_t *from_fifos;
    xc_to_fifo_t   *to_fifos;
    xc_shram_t     *shrams;
    // optional device-specific data fields
    xc_word_t data[0];

    // handle to the CDMA instance used for burst transfer
    XAxiCdma cdmaInstance;
};

typedef struct struct_XC_CdmaIface XC_CdmaIface;

/**
 * Configuration Initialization for burst transfer using Central DMA
 *
 * Use this configuration initialization method when, for example, DMAs, are used in the transfer.
 * Compared with XC_CfgInitialization, the main difference is that the memory space of the interface
 * data structure (the first argument) is allocated by the user.
 * In the simple XC_CfgInitialization case, it simply returns the address of the specific entry
 * on the configurable table.
 *
 * Two different initialization functions are provided to ensure driver performance.
 *
 * @param cdmaIface interface data structure for CDMA based burst transfer
 * @param configTableEntry entry on the configuration table
 * @param cdmaDeviceId ID of the CDMA device used for burst transfer
 *
 * @return XST_SUCCESS upon success; XST_FAILURE upon failure
 */
static inline int XC_CdmaCfgInitialize(XC_CdmaIface *cdmaIface, void *configTableEntry, u32 cdmaDeviceId)
{
	int Status = XST_SUCCESS;

	XAxiCdma_Config *CfgPtr;

	// copy interface settings from configuration table
	XC_CdmaIface *iface = (XC_CdmaIface *) configTableEntry;

	cdmaIface->version = iface->version;
	cdmaIface->xc_create = iface->xc_create;
	cdmaIface->xc_release = iface->xc_release;
	cdmaIface->xc_open = iface->xc_open;
	cdmaIface->xc_close = iface->xc_close;
	cdmaIface->xc_read = iface->xc_read;
	cdmaIface->xc_write = iface->xc_write;
	cdmaIface->xc_get_shmem = iface->xc_get_shmem;

	cdmaIface->xc_create((XC_CdmaIface **) cdmaIface, configTableEntry);

    // initialize the CDMA engine

	/* Initialize the XAxiCdma device.
	 */
	CfgPtr = XAxiCdma_LookupConfig(cdmaDeviceId);
	if (!CfgPtr) {
		xdbg_printf(XDBG_DEBUG_ERROR,
		            "Cannot find configuration entry for CDMA device Id %d\n\r",
		            cdmaDeviceId);
		Status = XST_FAILURE;
		goto XC_CdmaCfgInitialize_EXIT;
	}


	Status = XAxiCdma_CfgInitialize(&cdmaIface->cdmaInstance, CfgPtr, CfgPtr->BaseAddress);
	if (Status != XST_SUCCESS) {
		xdbg_printf(XDBG_DEBUG_ERROR,
		            "CDMA Initialization failed with %d\n\r",
		            Status);
		Status = XST_FAILURE;
		goto XC_CdmaCfgInitialize_EXIT;
	}

XC_CdmaCfgInitialize_EXIT:
    return Status;
}

/*****************************************************************************/
/*
* This function does one simple transfer in polled mode
*
* @param InstancePtr a pointer to the XAxiCdma instance
* @param Length transfer length in words
* @param Retries is how many times to retry on submission
*
* @return
* - XST_SUCCESS if transfer is successful
* - XST_FAILURE if either the transfer fails or the data has error
*
******************************************************************************/
static inline int XC_CdmaBurstRead_Impl(XAxiCdma *InstancePtr,
		                         u32 *DestPtr,
		                         u32 *SrcPtr,
		                         int Length,
		                         int Retries)
{
	int Status, Error;
	u32 IRQMask;

	// remember the current interrupt mode so that
	// it is recovered after the read transfer
	IRQMask = XAxiCdma_IntrGetEnabled(InstancePtr);

	// disable interrupts, polling mode is used
	XAxiCdma_IntrDisable(InstancePtr, XAXICDMA_XR_IRQ_ALL_MASK);

	// try to start the DMA transfer
	while (Retries) {
		Retries -= 1;

		Status = XAxiCdma_SimpleTransfer(InstancePtr,
				                         (u32)SrcPtr,
			                             (u32)DestPtr,
			                             Length * 4,  // number of bytes
			                             NULL,
			                             NULL);
		if (Status == XST_SUCCESS) {
			break;
		}
	}

	// return failure if failed to submit the transfer
	if (!Retries) {
		xdbg_printf(XDBG_DEBUG_ERROR,
		            "Failed to submit the transfer %d\n\r", Status);
		Status = XST_FAILURE;
		goto XC_CdmaBurstRead_Impl_EXIT;
	}

	// Wait until the DMA transfer is done
	while (XAxiCdma_IsBusy(InstancePtr)) {
		/* Wait */
	}

	/* report XST_FAILURE if the hardware has errors. */
	// Therefore, error conditions are not cleared by the driver.
	Error = XAxiCdma_GetError(InstancePtr);
	if (Error != XST_SUCCESS) {
		int TimeOut = RESET_LOOP_COUNT;

		xdbg_printf(XDBG_DEBUG_ERROR,
				    "Transfer has error %x\n\r", Error);

		// Need to reset the hardware to restore to the correct state
		XAxiCdma_Reset(InstancePtr);

		while (TimeOut) {
			if (XAxiCdma_ResetIsDone(InstancePtr)) {
				break;
			}
			TimeOut -= 1;
		}

		/* reset has failed, print a message to notify the user */
		if (!TimeOut) {
			xdbg_printf(XDBG_DEBUG_ERROR,
			            "Reset hardware failed with %d\n\r", Status);
		}

		Status = XST_FAILURE;
	}

XC_CdmaBurstRead_Impl_EXIT:
    /* recover the original interrupt mask */
    XAxiCdma_IntrEnable(InstancePtr, IRQMask);

	return Status;
}

/*****************************************************************************/
/**
 * Burst read transfer through Central DMA
 *
 * @param iface settings of the interface used for transfer
 * @param dstAddr destination address
 * @param srcAddr source address
 * @param length length of transfer data (in bytes)
 *
 * @return XST_SUCCESS upon success; XST_FAILURE upon failure
 *
 *****************************************************************************/
static inline int XC_CdmaBurstRead(XC_CdmaIface *cdmaIface, u32 *dstAddr, u32 *srcAddr, uint32_t length)
{
	int Status = XST_SUCCESS;
	int i;

	if (&cdmaIface->cdmaInstance) {
		// burst read using Central DMA
		Status = XC_CdmaBurstRead_Impl(&cdmaIface->cdmaInstance, // pointer to the CDMA instance
				                       dstAddr,                  // destination address
				                       srcAddr,                  // source address
			                           length,                   // burst length in bytes
			                           SUBMIT_TRIES);            // number of tries to submit jobs
		                                                         // before reporting error
	} else {
		// burst read using processor
		for (i = 0; i < length; i++) {
			dstAddr = srcAddr;
			dstAddr++; srcAddr++;
		}
	}
	return Status;
}
#endif

struct struct_xc_iface_t {
    u32 version;
    // function pointers to low-level drivers
    xc_status_t (*xc_create)(struct struct_xc_iface_t **, void *);
    xc_status_t (*xc_release)(struct struct_xc_iface_t **);
    xc_status_t (*xc_open)(struct struct_xc_iface_t *);
    xc_status_t (*xc_close)(struct struct_xc_iface_t *);
    xc_status_t (*xc_read)(struct struct_xc_iface_t *, xc_r_addr_t, u32 *);
    xc_status_t (*xc_write)(struct struct_xc_iface_t *, xc_w_addr_t, const u32);
    xc_status_t (*xc_get_shmem)(struct struct_xc_iface_t *, const char *, void **);
    xc_from_reg_t  *from_regs;
    xc_to_reg_t    *to_regs;
    xc_from_fifo_t *from_fifos;
    xc_to_fifo_t   *to_fifos;
    xc_shram_t     *shrams;
    // optional device-specific data fields
    xc_word_t data[0];
};

typedef struct struct_xc_iface_t xc_iface_t;


// Legacy XCOPE functions
// These functions are kept for back compatibility and are subject to deprecation in future releases.
static inline xc_status_t xc_create(xc_iface_t** iface, void* config_table) {
    return ((xc_iface_t *) config_table)->xc_create(iface, config_table);
}

static inline xc_status_t xc_get_shmem(xc_iface_t* iface, const char* name, void** shmem) {
    return iface->xc_get_shmem(iface, name, shmem);
}

static inline xc_status_t xc_write(xc_iface_t* iface, xc_w_addr_t w_addr, const uint32_t data) {
    return iface->xc_write(iface, w_addr, data);
}
static inline xc_status_t xc_write_float(xc_iface_t* iface, xc_w_addr_t w_addr, const float data) {
    uint32_t *fData = (uint32_t *) &data;
    return iface->xc_write(iface, w_addr, *fData);
}

static inline xc_status_t xc_read(xc_iface_t* iface, xc_r_addr_t r_addr, uint32_t* data) {
    return iface->xc_read(iface, r_addr, data);
}

static inline xc_status_t xc_read_float(xc_iface_t* iface, xc_r_addr_t r_addr, float* data) {
    return iface->xc_read(iface, r_addr, (uint32_t *) data);
}

// XCOPE helper functions
static inline xc_raw_addr_t xc_get_addr(xc_raw_addr_t addr, uint32_t offset) {
    return addr + sizeof(xc_raw_addr_t) * offset;
}

/**
  * EDK friendly APIs for accessing System Generator shared memories
  */ 

/**
 *  
 * Initialize the software driver by reading the corresponding entries in the configuration table.
 * The configuration table is automatically generated by XPS and EDK based on hardware configuration.
 * 
 */

/**
 * Initialize Iface parameter with the corresponding device entry in
 * configuration table (ConfigTableEntry). The configuration table is
 * generated automatically by XPS and SDK based on the settings of the hardware
 * platofrm. 
 *
 * @param Iface Interface object to be initialized
 * @param ConfigTableEntry Entry on the configuration table corresponding to the System Generator Pcore of interest
 * @return always return XCS_SUCCESS. No checking for invalid configuration table entry is implemented. 
 */
#define XC_CfgInitialize(Iface, ConfigTableEntry) ((xc_iface_t *) ConfigTableEntry)->xc_create(Iface, ConfigTableEntry)

/**
 * Retrieve the settings of a System Generator shared memory object by name.
 *
 * @param Iface Interface object
 * @param Name Name of the System Generator shared memory object
 * @param Shmem Data structure to hold the settings of the shared memory object
 * @return always return XCS_SUCCESS.
 */
#define XC_GetShmem(Iface, Name, Shmem) Iface->xc_get_shmem(Iface, Name, Shmem)

/**
 * Write to a specific pin on a System Generator shared memory object.
 *
 * @param Iface Interface object
 * @param WriteAddr Absolute address of the memory-mapped pin on shared memory object
 * @param Data Date to write to
 * @return always return XCS_SUCCESS.
 */
#define XC_Write(Iface, WriteAddr, Data) iface->xc_write(iface, WriteAddr, Data)

/**
 * Write to a specific pin on a System Generator shared memory object.
 *
 * @param Iface Interface object
 * @param WriteAddr Absolute address of the memory-mapped pin on shared memory object
 * @param Data Float date to write to
 * @return always return XCS_SUCCESS.
 */
#define XC_Write_Float(Iface, WriteAddr, Data) { u32* pIntData = (u32 *) &Data; iface->xc_write(iface, WriteAddr, *pIntData); } 

/**
 * Read from a specific pin on a System Generator shared memory object.
 *
 * @param Iface Interface object
 * @param ReadAddr Absolute address of the memory-mapped pin on shared memory object
 * @param Data Date to store the read data
 * @return always return XCS_SUCCESS.
 */
#define XC_Read(Iface, ReadAddr, DataAddr) Iface->xc_read(Iface, ReadAddr, DataAddr)

/**
 * Read a float data from a specific pin on a System Generator shared memory object.
 *
 * @param Iface Interface object
 * @param ReadAddr Absolute address of the memory-mapped pin on shared memory object
 * @param Data storage for the read back float data
 * @return always return XCS_SUCCESS.
 */
#define XC_Read_Float(Iface, ReadAddr, DataAddr) Iface->xc_read(Iface, ReadAddr, (u32 *) DataAddr)

// XCOPE helper functions

/**
 * Calculate the absolute address based on base address and offset. This is a
 * utility function for random access to a shared System Generator 'Shared
 * Memory' object.
 *
 * @param BaseAddr Base address
 * @param Offset Offset relative to base address
 * @return Absolute address
 */
#define XC_GetAddr(BaseAddr, Offset) BaseAddr + sizeof(xc_raw_addr_t) * Offset

#endif
