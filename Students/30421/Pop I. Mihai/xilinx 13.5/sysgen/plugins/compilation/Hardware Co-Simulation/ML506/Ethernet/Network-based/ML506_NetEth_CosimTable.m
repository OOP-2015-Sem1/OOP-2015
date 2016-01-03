%
% Filename   : ML506_NetEth_CosimTable.m
%
% Description: Defines the co-simulation information for the Ethernet
%              co-simulation platform.
%

function t = ML506_NetEth_CosimTable(varargin)

    t = struct();

    % ------------------------------------------------------------------
    % Ethernet co-simulation type.
    % ------------------------------------------------------------------
    t.ethernetCosim.type = 'networkethernet';

    % ------------------------------------------------------------------
    % Target platform.
    % ------------------------------------------------------------------
    t.ethernetCosim.target = 'ML506';

    % ------------------------------------------------------------------
    % Position of target FPGA on boundary scan chain (starts from 1).
    % ------------------------------------------------------------------
    t.ethernetCosim.bscanPos = 5;

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
