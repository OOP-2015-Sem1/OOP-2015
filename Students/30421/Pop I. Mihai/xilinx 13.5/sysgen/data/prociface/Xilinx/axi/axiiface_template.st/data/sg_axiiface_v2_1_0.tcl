##############################################################################
##
## ***************************************************************************
## **                                                                       **
## ** Copyright (c) 1995-2010 Xilinx, Inc.  All rights reserved.            **
## **                                                                       **
## ** You may copy and modify these files for your own internal use solely  **
## ** with Xilinx programmable logic devices and Xilinx EDK system or       **
## ** create IP modules solely for Xilinx programmable logic devices and    **
## ** Xilinx EDK system. No rights are granted to distribute any files      **
## ** unless they are distributed in Xilinx programmable logic devices.     **
## **                                                                       **
## ***************************************************************************
##
##############################################################################

proc generate {drv_handle} {
    puts "Generating Macros for $T.pcoreName$ driver access ... "

    # initialize
    lappend config_table
    lappend addr_config_table
    lappend xparam_config_table

    # hardware version
    lappend config_table "C_XC_VERSION"
    # Low-level function names
    lappend config_table "C_XC_CREATE" "C_XC_RELEASE" "C_XC_OPEN" "C_XC_CLOSE" "C_XC_READ" "C_XC_WRITE" "C_XC_GET_SHMEM"
    # Optional parameters
    # (empty)

    # Memory map information
$T.memmap_info.fromregs:{    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_N_BITS"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_BIN_PT"
    # sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_ATTR"};separator="\n"$
$T.memmap_info.toregs:{    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_N_BITS"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_BIN_PT"
    # sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_ATTR"};separator="\n"$
$T.memmap_info.fromfifos:{    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$"
    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$_PERCENTFULL"
    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$_EMPTY"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_N_BITS"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_BIN_PT"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_DEPTH"
    # sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_ATTR"};separator="\n"$
$T.memmap_info.tofifos:{    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$"
    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$_PERCENTFULL"
    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$_FULL"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_N_BITS"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_BIN_PT"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_DEPTH"
    # sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_ATTR"};separator="\n"$
$T.memmap_info.shmems:{    sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$"
    # sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$_GRANT"
    # sg_lappend config_table addr_config_table "C_MEMMAP_$it.name;format="toUpper"$_REQ"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_N_BITS"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_BIN_PT"
    sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_DEPTH"
    # sg_lappend config_table xparam_config_table "C_MEMMAP_$it.name;format="toUpper"$_ATTR"};separator="\n"$

    # XPS parameters
    sg_lappend config_table xparam_config_table "DEVICE_ID" "C_BASEADDR"

    # generate xparameters.h
    eval xdefine_include_file \$drv_handle "xparameters.h" "$T.pcoreName;format="toUpper"$" "NUM_INSTANCES" \${xparam_config_table}
    eval sg_xdefine_include_file \$drv_handle "xparameters.h" "$T.pcoreName;format="toUpper"$" \${addr_config_table}
    # generate $T.pcoreName$_g.c
    eval xdefine_config_file \$drv_handle "$T.pcoreName$_g.c" "$T.pcoreName;format="toUpper"$" \${config_table}
}

proc sg_xdefine_include_file {drv_handle file_name drv_string args} {
    # Open include file
    set file_handle [xopen_include_file \$file_name]

    # Get all peripherals connected to this driver
    set periphs [xget_periphs \$drv_handle] 

    # Print all parameters for all peripherals
    set device_id 0
    foreach periph \${periphs} {
        # base_addr of the peripheral
        set base_addr [xget_param_value \${periph} "C_BASEADDR"]

        puts \${file_handle} ""
        puts \${file_handle} "/* Definitions (address parameters) for peripheral [string toupper [xget_hw_name \$periph]] */"
        foreach arg \${args} {
            set value [xget_param_value \${periph} \${arg}]
            if {[llength \${value}] == 0} {
                set value 0
            }
            set value [expr \${base_addr} + \${value}]
            set value_str [xformat_address_string \${value}]
            puts \${file_handle} "#define [xget_name \${periph} \${arg}] \${value_str}"
        }

        puts \$file_handle "/* software driver settings for peripheral [string toupper [xget_hw_name \$periph]] */"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_VERSION   $T.memmap_info.xc_version$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_CREATE    $T.memmap_info.xc_create$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_RELEASE   $T.memmap_info.xc_release$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_OPEN      $T.memmap_info.xc_open$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_CLOSE     $T.memmap_info.xc_close$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_READ      $T.memmap_info.xc_read$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_WRITE     $T.memmap_info.xc_write$"
        puts \$file_handle "#define XPAR_[string toupper [xget_hw_name \${periph}]]_XC_GET_SHMEM $T.memmap_info.xc_get_shmem$"

        puts \$file_handle ""
    }		
    puts \$file_handle "\n/******************************************************************/\n"
    close \$file_handle
}

proc sg_lappend {required_config_table {extra_config_table ""} args} {
    upvar \${required_config_table} config_table_1
    if {[string length \${extra_config_table}] != 0} {
        upvar \${extra_config_table} config_table_2
    }

    foreach value \${args} {
        eval [list lappend config_table_1 \${value}]
        if {[string length \${extra_config_table}] != 0} {
            lappend config_table_2 \${value}
        }
    }
}
