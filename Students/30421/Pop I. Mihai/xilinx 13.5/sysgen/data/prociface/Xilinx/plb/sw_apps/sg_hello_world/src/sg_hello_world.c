/*
 * Copyright (c) 2010 Xilinx, Inc.  All rights reserved.
 *
 * Xilinx, Inc.
 * XILINX IS PROVIDING THIS DESIGN, CODE, OR INFORMATION "AS IS" AS A
 * COURTESY TO YOU.  BY PROVIDING THIS DESIGN, CODE, OR INFORMATION AS
 * ONE POSSIBLE   IMPLEMENTATION OF THIS FEATURE, APPLICATION OR
 * STANDARD, XILINX IS MAKING NO REPRESENTATION THAT THIS IMPLEMENTATION
 * IS FREE FROM ANY CLAIMS OF INFRINGEMENT, AND YOU ARE RESPONSIBLE
 * FOR OBTAINING ANY RIGHTS YOU MAY REQUIRE FOR YOUR IMPLEMENTATION.
 * XILINX EXPRESSLY DISCLAIMS ANY WARRANTY WHATSOEVER WITH RESPECT TO
 * THE ADEQUACY OF THE IMPLEMENTATION, INCLUDING BUT NOT LIMITED TO
 * ANY WARRANTIES OR REPRESENTATIONS THAT THIS IMPLEMENTATION IS FREE
 * FROM CLAIMS OF INFRINGEMENT, IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */

/*
 * helloworld.c: simple test application
 */

#include <stdio.h>
#include <sg_$T.bus_type$iface.h>

// set to an integer number to turn on debugging information on console 
#define SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG 0

/*
 * write data to a "To Register" block
 * 
 * @ param shram_name Name of the "To Register" block
 * @ param value data to write to
 * 
 * @ return status Status of the write operation
 */
xc_status_t writeToRegister(char *toregister_name, u32 value) {
    xc_iface_t *iface;
    xc_to_reg_t *toreg;
    xc_status_t status;

    // initialize software driver, assuming device ID is 0
    XC_CfgInitialize(&iface, &SG_$T.bus_type;format="toUpper"$IFACE_ConfigTable[0]);
    if (iface == NULL) {
        print("unable to access config table"); print("\r\n");
    }

    // obtain shared memory settings
    XC_GetShmem(iface, toregister_name, (void **) &toreg);
    if (toreg == NULL) {
        print("unable to access shared memory"); print("\r\n");
    }
#if SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG > 0
    print(toregister_name);
    xil_printf(" :0x%x, 0x%x\r\n", toreg->din, value);
#endif
    // write value to the "din" port of the "To Register" block
    status = XC_Write(iface, toreg->din, (const unsigned) value); 
    return status;
}

/*
 * read data from a "From Register" block
 * 
 * @ param shram_name Name of the "From Register" block
 * @ param value Location for storing the read-back data
 * 
 * @ return status Status of the read operation
 */
xc_status_t readFromRegister(char *fromregister_name, u32 *value) {
    xc_iface_t *iface;
    xc_from_reg_t *fromreg;
    xc_status_t status;

    // initialize software driver, assuming device ID is 0
    XC_CfgInitialize(&iface, &SG_$T.bus_type;format="toUpper"$IFACE_ConfigTable[0]);
    if (iface == NULL) {
        print("unable to access config table"); print("\r\n");
    }
    // obtain shared memory settings
    XC_GetShmem(iface, fromregister_name, (void **) &fromreg);
    if (fromreg == NULL) {
        print("unable to access shared memory"); print("\r\n");
    }

    // write value to the "dout" port of the "From Register" block
    status = XC_Read(iface, fromreg->dout, value); 
#if SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG > 0
    print(fromregister_name); 
    xil_printf(" : %x, %x\r\n", fromreg->dout, *value);
#endif
    return status;
}

/*
 * write data to a "To FIFO" block
 * 
 * @ param shram_name Name of the "To FIFO" block
 * @ param value data to write to
 * 
 * @ return status Status of the write operation
 */
xc_status_t writeToFIFO(char *tofifo_name, u32 value) {
    xc_iface_t *iface;
    xc_to_fifo_t *tofifo;
    xc_status_t status;

    // initialize software driver, assuming device ID is 0
    XC_CfgInitialize(&iface, &SG_$T.bus_type;format="toUpper"$IFACE_ConfigTable[0]);
    if (iface == NULL) {
        print("unable to access config table"); print("\r\n");
    }

    // obtain shared memory settings
    XC_GetShmem(iface, tofifo_name, (void **) &tofifo);
    if (tofifo == NULL) {
        print("unable to access shared memory"); print("\r\n");
    }
    
    // check the "full" port of shared memory "tofifo"
    do {
        XC_Read(iface, tofifo->full, &value);
    } while (value == 1);
    // write value to the "din" port of the "To FIFO" block
    status = XC_Write(iface, tofifo->din, (const unsigned) value); 
        
#if SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG > 0
    print(tofifo_name);
    xil_printf(" :0x%x, 0x%x\r\n", tofifo->din, value);
#endif
    return status;
}

/*
 * read data from a "From FIFO" block
 * 
 * @ param shram_name Name of the "From FIFO" block
 * @ param value Location for storing the read-back data
 * 
 * @ return status Status of the read operation
 */
xc_status_t readFromFIFO(char *fromfifo_name, u32 *value) {
    xc_iface_t *iface;
    xc_from_fifo_t *fromfifo;
    xc_status_t status;

    // initialize the software driver, assuming device ID is 0
    XC_CfgInitialize(&iface, &SG_$T.bus_type;format="toUpper"$IFACE_ConfigTable[0]);
    if (iface == NULL) {
        print("unable to access config table"); print("\r\n");
    }
    // obtain shared memory settings
    XC_GetShmem(iface, fromfifo_name, (void **) &fromfifo);
    if (fromfifo == NULL) {
        print("unable to access shared memory"); print("\r\n");
    }

    // wait till there is data in the "From FIFO" block 
    do {
        XC_Read(iface, fromfifo->empty, value);
    } while (value == 0);
    // write value to the "dout" port of the "From FIFO" block
    status = XC_Read(iface, fromfifo->dout, value); 
#if SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG > 0
    print(fromfifo_name);
    xil_printf(" : %x, %x\r\n", fromfifo->dout, *value);
#endif
    return status;
}

/*
 * write data to a "Shared Memory" block
 * 
 * @ param shram_name Name of the "Shared Memory" block
 * @ param offset Location offset to read data from
 * @ param value data to write to
 * 
 * @ return status Status of the write operation
 */
xc_status_t writeSharedMemory(char *shram_name, u32 offset, u32 value) {
    xc_iface_t *iface;
    xc_shram_t *shram;
    xc_status_t status;

    // initialize software driver, assuming device ID is 0
    XC_CfgInitialize(&iface, &SG_$T.bus_type;format="toUpper"$IFACE_ConfigTable[0]);
    if (iface == NULL) {
        print("unable to access config table"); print("\r\n");
    }
    
    // obtain shared memory settings
    XC_GetShmem(iface, shram_name, (void **) &shram);
    // write value to the shared memory
    status = XC_Write(iface, XC_GetAddr(shram->addr, offset), (const unsigned) value); 
	
#if SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG > 0
    print(shram_name);
    xil_printf(" :0x%x, 0x%x\r\n", shram->addr, value);
#endif
    return status;
}

/*
 * read data from a "Shared Memory" block
 * 
 * @ param shram_name Name of the "Shared Memory" block
 * @ param offset Location offset to read data from
 * @ param value Location for storing the read-back data
 * 
 * @ return status Status of the read operation
 */
xc_status_t readSharedMemory(char *shram_name, u32 offset, u32 *value) {
    xc_iface_t *iface;
    xc_shram_t *shram;
    xc_status_t status;

    // initialize software driver, assuming device ID is 0
    XC_CfgInitialize(&iface, &SG_$T.bus_type;format="toUpper"$IFACE_ConfigTable[0]);
    if (iface == NULL) {
        print("unable to access config table"); print("\r\n");
    }
    
    // obtain shared memory settings
    XC_GetShmem(iface, shram_name, (void **) &shram);
    // read value from the shared memory
    status = XC_Read(iface, XC_GetAddr(shram->addr, offset), value); 
	
#if SG_$T.bus_type;format="toUpper"$IFACE_TEST_APP_DEBUG > 0
    print(shram_name);
    xil_printf(" :0x%x, 0x%x\r\n", shram->addr, *value);
#endif
    return status;
}

int main()
{
    print("Hello World\n\r");
    
    // use the above utility functions to access the shared memory blocks 
    // in the source System Generator design
    
    // check the API document of the System Generator Pcore for information
    // of the available shared memory blocks
    
    return 0;
}

