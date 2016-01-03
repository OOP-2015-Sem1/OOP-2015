///////////////////////////////////////////////////////////////-*-C-*-
//
// Copyright (c) 2011 Xilinx, Inc.  All rights reserved.
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

#include "$T.pcoreName$.h"
#include "xparameters.h"
#include "xil_io.h"
#include "xcope.h"

inline xc_status_t $T.memmap_info.xc_create$(xc_iface_t **iface, void *config_table)
{
    // set up iface
    *iface = (xc_iface_t *) config_table;

#ifdef XC_DEBUG
    $T.pcoreName;format="toUpper"$_Config *_config_table = config_table;

    if (_config_table->xc_create == NULL) {
        print("config_table.xc_create == NULL\r\n");
        exit(1);
    }
#endif

    // does nothing
    return XC_SUCCESS;
}

inline xc_status_t $T.memmap_info.xc_release$(xc_iface_t **iface) 
{
    // does nothing
    return XC_SUCCESS;
}

inline xc_status_t $T.memmap_info.xc_open$(xc_iface_t *iface)
{
    // does nothing
    return XC_SUCCESS;
}

inline xc_status_t $T.memmap_info.xc_close$(xc_iface_t *iface)
{
    // does nothing
    return XC_SUCCESS;
}

inline xc_status_t $T.memmap_info.xc_read$(xc_iface_t *iface, xc_r_addr_t addr, uint32_t *value)
{
    *value = Xil_In32((uint32_t *) addr);
    return XC_SUCCESS;
}

inline xc_status_t $T.memmap_info.xc_write$(xc_iface_t *iface, xc_w_addr_t addr, const uint32_t value)
{
    Xil_Out32((uint32_t *) addr, value);
    return XC_SUCCESS;
}

xc_status_t $T.memmap_info.xc_get_shmem$(xc_iface_t *iface, const char *name, void **shmem)
{
    $T.pcoreName;format="toUpper"$_Config *_config_table = ($T.pcoreName;format="toUpper"$_Config *) iface;

    $[T.memmap_info.fromregs,T.memmap_info.toregs,T.memmap_info.fromfifos,T.memmap_info.tofifos,T.memmap_info.shmems]:{if (strcmp("$it.name$", name) == 0) \{
    *shmem = (void *) & _config_table->$it.name$;
\}};separator=" else "$
$if([T.memmap_info.fromregs,T.memmap_info.toregs,T.memmap_info.fromfifos,T.memmap_info.tofifos,T.memmap_info.shmems])$    else { *shmem = NULL; return XC_FAILURE; }$endif$

    return XC_SUCCESS;
}
