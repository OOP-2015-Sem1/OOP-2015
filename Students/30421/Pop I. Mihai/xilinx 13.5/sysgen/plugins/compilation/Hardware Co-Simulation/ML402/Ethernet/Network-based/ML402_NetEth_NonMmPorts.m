%
% Filename   : ML402_NetEth_NonMmPorts.m
%
% Description: Defines the non-memory mapped ports for the
%              Ethernet co-simulation platform.
%

function non_mm_ports = ML402_NetEth_NonMmPorts()

    non_mm_ports = {};

    % ----------------------------------------------
    % LEDs
    % ----------------------------------------------
    non_mm_ports.('leds')            = {'out', 4};

    % ----------------------------------------------
    % UART
    % ----------------------------------------------
    non_mm_ports.('uart_rxd')        = {'in',  1};
    non_mm_ports.('uart_txd')        = {'out', 1};

    % ----------------------------------------------
    % DDR
    % ----------------------------------------------
    non_mm_ports.('ddr_addr')        = {'out', 13};
    non_mm_ports.('ddr_bankaddr')    = {'out', 2};
    non_mm_ports.('ddr_casn')        = {'out', 1};
    non_mm_ports.('ddr_cke')         = {'out', 1};
    non_mm_ports.('ddr_csn')         = {'out', 1};
    non_mm_ports.('ddr_rasn')        = {'out', 1};
    non_mm_ports.('ddr_wen')         = {'out', 1};
    non_mm_ports.('ddr_clock')       = {'out', 1};
    non_mm_ports.('ddr_clockn')      = {'out', 1};
    non_mm_ports.('ddr_clock_in')    = {'in',  1};
    non_mm_ports.('ddr_dm')          = {'out', 4};

    % ----------------------------------------------
    % VGA
    % ----------------------------------------------
    non_mm_ports.('vga_r')           = {'out', 8};
    non_mm_ports.('vga_g')           = {'out', 8};
    non_mm_ports.('vga_b')           = {'out', 8};
    non_mm_ports.('vga_psave_n')     = {'out', 1};
    non_mm_ports.('vga_blank_n')     = {'out', 1};
    non_mm_ports.('vga_sync_n')      = {'out', 1};
    non_mm_ports.('vga_hsync')       = {'out', 1};
    non_mm_ports.('vga_vsync')       = {'out', 1};
    non_mm_ports.('vga_clk')         = {'out', 1};

    % ----------------------------------------------
    % DIP Switch
    % ----------------------------------------------
    non_mm_ports.('dipsw')           = {'in',  8};

    % ----------------------------------------------
    % Push Button
    % ----------------------------------------------
    non_mm_ports.('pbutton_c')       = {'in',  5};

    % ----------------------------------------------
    % Audio
    % ----------------------------------------------
    non_mm_ports.('audio_bit_clk')   = {'in',  1};
    non_mm_ports.('audio_reset_n')   = {'in',  1};
    non_mm_ports.('audio_sdata_in')  = {'in',  1};
    non_mm_ports.('audio_sdata_out') = {'out', 1};
    non_mm_ports.('audio_sync')      = {'out', 1};
