%
% Filename   : S3ADSP_DB_PPEth_NonMmPorts.m
%
% Description: Defines the non-memory mapped ports for the
%              Ethernet co-simulation platform.
%

function non_mm_ports = S3ADSP_DB_PPEth_NonMmPorts()

    non_mm_ports = {};
    non_mm_ports.('uart_rxd') = { 'in', 1 };
    non_mm_ports.('uart_txd') = { 'out', 1 };
    non_mm_ports.('dipsw') = { 'in', 8 };
    non_mm_ports.('leds') = { 'out', 8 };
    non_mm_ports.('pbutton_c') = { 'in', 5 };
