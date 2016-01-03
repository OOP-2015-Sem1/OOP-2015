#include "xparameters.h"
#include "stdio.h"
#include "sg_axiiface.h"
#include "xutil.h"
#include "time.h"

int main (void) {
	uint32_t i;
    uint32_t dout_empty, value;
    // impulse response
    uint32_t din_value[16]  = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    // for storing output data
    uint32_t dout_value[16] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    xc_iface_t *iface;
    xc_to_fifo_t *tofifo_din;
    xc_from_fifo_t *fromfifo_dout;


    // print("Press the Return key to continue ...\r\n");
    // getchar();

    // initialize the software driver, assuming the Pcore device ID is 0
	xc_create(&iface, &SG_AXIIFACE_ConfigTable[0]);

	// obtain the address for "din" and "dout" FIFOs
    xc_get_shmem(iface, "din", (void **) &tofifo_din);
    if (tofifo_din == NULL) {
        print("FIFO 'din' not found\r\n");
    }
    xc_get_shmem(iface, "dout", (void **) &fromfifo_dout);
    if (fromfifo_dout == NULL) {
        print("FIFO 'dout' not found\r\n");
    }

    for (i = 0; i < 16; i++) {
    	// equivalent software implementation
    	// value = 0;
        // for (j = 0; j < 16; j++) {
    	//	  if (i - j > 0) {
    	//	      value += din_value[i - j] * h[j];
    	// 	  }
    	// }
    	// dout_value[i] = value;

		// write impulse to 'din' FIFO
		xc_write(iface, tofifo_din->din, din_value[i]);
		//  wait until 'dout' FIFO has data
		do {
			xc_read(iface, fromfifo_dout->empty, &dout_empty);
		} while (dout_empty == 1);
		// read data from 'dout' FIFO
		xc_read(iface, fromfifo_dout->dout, &value);
		dout_value[i] = value;

		xil_printf("dout[%d]: %d\r\n", i, dout_value[i]);
    }

    print("Done\r\n");

    return 0;
}
