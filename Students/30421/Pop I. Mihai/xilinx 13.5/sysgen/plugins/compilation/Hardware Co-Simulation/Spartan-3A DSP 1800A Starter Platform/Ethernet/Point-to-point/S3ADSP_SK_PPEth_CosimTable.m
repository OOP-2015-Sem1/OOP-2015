%
% Filename   : S3ADSP_SK_PPEth_CosimTable.m
%
% Description: Defines the co-simulation information for the Ethernet
%              co-simulation platform.
%

function t = S3ADSP_SK_PPEth_CosimTable(varargin)

    t = struct();

    % ------------------------------------------------------------------
    % Ethernet co-simulation type.
    % ------------------------------------------------------------------
    t.ethernetCosim.type = 'ppethernet';

    % ------------------------------------------------------------------
    % Target platform.
    % ------------------------------------------------------------------
    t.ethernetCosim.target = 'S3ADSP_SK';

    % ------------------------------------------------------------------
    % Position of target FPGA on boundary scan chain (starts from 1).
    % ------------------------------------------------------------------
    t.ethernetCosim.bscanPos = 1;

    % ------------------------------------------------------------------
    % Maximum Ethernet frame size limit.
    % ------------------------------------------------------------------
    t.ethernetCosim.maxFrameSize = 2040;
