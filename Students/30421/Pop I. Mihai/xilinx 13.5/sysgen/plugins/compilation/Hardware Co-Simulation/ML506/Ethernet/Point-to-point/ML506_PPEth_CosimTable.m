%
% Filename   : ML506_PPEth_CosimTable.m
%
% Description: Defines the co-simulation information for the Ethernet
%              co-simulation platform.
%

function t = ML506_PPEth_CosimTable(varargin)

    t = struct();

    % ------------------------------------------------------------------
    % Ethernet co-simulation type.
    % ------------------------------------------------------------------
    t.ethernetCosim.type = 'ppethernet';

    % ------------------------------------------------------------------
    % Target platform.
    % ------------------------------------------------------------------
    t.ethernetCosim.target = 'ML506';

    % ------------------------------------------------------------------
    % Position of target FPGA on boundary scan chain (starts from 1).
    % ------------------------------------------------------------------
    t.ethernetCosim.bscanPos = 5;

    % ------------------------------------------------------------------
    % Maximum Ethernet frame size limit.
    % ------------------------------------------------------------------
    t.ethernetCosim.maxFrameSize = 8184;

    % ------------------------------------------------------------------
    % Block RAM size.
    % ------------------------------------------------------------------
    t.ethernetCosim.blockRAMSize = 4096;

    % ------------------------------------------------------------------
    % System ACE configuration profiles.
    % ------------------------------------------------------------------
    profile(1).name = 'Default settings';
    profile(1).bootID = 4;
    profile(1).configFiles = { ...
        '$(BITFILE)', ...
    };

    t.ethernetCosim.systemACEConfig.profiles = { ...
        profile(1), ...
    };

    % Current System ACE configuration profile.
    t.ethernetCosim.systemACEConfig.current = profile(1).name;
