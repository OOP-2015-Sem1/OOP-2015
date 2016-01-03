proc swapp_get_name {} {
    return "System Generator example";
}

proc swapp_get_description {} {
    return "Example C program for using System Generator software driver.";
}

proc get_os {} {
    set oslist [xget_sw_modules "type" "os"];
    set os [lindex \$oslist 0];

    if { \$os == "" } {
        error "No Operating System specified in the Board Support Package.";
    }
    
    return \$os;
}

proc check_stdout_hw {} {
    set uartlites [xget_ips "type" "uartlite"];
    if { [llength \$uartlites] == 0 } {
        # we do not have an uartlite
	    set uart16550s [xget_ips "type" "uart16550"];
	    if { [llength \$uart16550s] == 0 } {      
            # Check for MDM-Uart peripheral. The MDM would be listed as a peripheral
            # only if it has a UART interface. So no further check is required
            set mdmlist [xget_ips "type" "mdm"]
            if { [llength \$mdmlist] == 0 } {
                error "This application requires a Uart IP in the hardware."
            }
        }
    }
}

proc check_stdout_sw {} {
    set stdout [get_stdout];
    if { \$stdout == "none" } {
        error "The STDOUT parameter is not set on the OS."
    }
}

proc swapp_is_supported_hw {} {
    # check for uart peripheral
    check_stdout_hw;

    return 1;
}


proc swapp_is_supported_sw {} {
    # check for stdout being set
    check_stdout_sw;

    return 1;
}

proc generate_stdout_config { fid } {
    set stdout [get_stdout];

    # if stdout is uartlite, we don't have to generate anything
    set stdout_type [xget_ip_attribute "type" \$stdout];

    if { [regexp -nocase "uartlite" \$stdout_type] || [string match -nocase "mdm" \$stdout_type] } {
        return;
    } elseif { \$stdout_type == "uart16550" } {
        # mention that we have a 16550
            puts \$fid "#define STDOUT_IS_16550";

            # and note down its base address
        set prefix "XPAR_";
        set postfix "_BASEADDR";
        set stdout_baseaddr_macro \$prefix\$stdout\$postfix;
        set stdout_baseaddr_macro [string toupper \$stdout_baseaddr_macro];
        puts \$fid "#define STDOUT_BASEADDR \$stdout_baseaddr_macro";
    }
}

proc swapp_generate {} {
}
