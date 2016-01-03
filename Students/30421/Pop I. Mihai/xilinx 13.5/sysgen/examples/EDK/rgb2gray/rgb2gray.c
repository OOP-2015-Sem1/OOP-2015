#include "xparameters.h"
#include "stdio.h"
#include "xutil.h"

// header file of System Generator Pcore
#include "rgb2gray_plbw.h"

int main (void) {
    int i;
    uint32_t gray, red, green, blue;

    print("-- Entering main() --\n\r");

    xc_iface_t *iface;
    xc_from_reg_t *fromreg_gray;
    xc_to_reg_t *toreg_red, *toreg_green, *toreg_blue;

    // initialize the software driver
    xc_create(&iface, &RGB2GRAY_PLBW_ConfigTable[0]);

    // obtain the memory locations
    xc_get_shmem(iface, "result",  (void **) &fromreg_gray);
    xc_get_shmem(iface, "red",   (void **) &toreg_red);
    xc_get_shmem(iface, "green", (void **) &toreg_green);
    xc_get_shmem(iface, "blue",  (void **) &toreg_blue);

    for (i=15; i<30; i++){	
        red = i;
        green = i + 10;
        blue = i + 20;

        // Write RGB value to peripheral
        xc_write(iface, toreg_red->din, red); 
        xc_write(iface, toreg_green->din, green); 
        xc_write(iface, toreg_blue->din, blue); 
         
        xil_printf("R = 0x%x, G = 0x%x, B = 0x%x -- ",
                red, green, blue);

        xc_read(iface, fromreg_gray->dout, &gray); 

        xil_printf("Gray = %x \n\r", gray);
    } 

    print("-- Exiting main() --\n\r");
    return 0;
}
