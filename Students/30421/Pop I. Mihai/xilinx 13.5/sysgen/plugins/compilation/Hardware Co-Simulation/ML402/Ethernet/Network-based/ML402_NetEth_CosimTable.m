%
% Filename   : ML402_NetEth_CosimTable.m
%
% Description: Defines the co-simulation information for the Ethernet
%              co-simulation platform.
%

function t = ML402_NetEth_CosimTable(varargin)

    t = struct();

    % ------------------------------------------------------------------
    % Ethernet co-simulation type.
    % ------------------------------------------------------------------
    t.ethernetCosim.type = 'networkethernet';

    % ------------------------------------------------------------------
    % Target platform.
    % ------------------------------------------------------------------
    t.ethernetCosim.target = 'ML402';

    % ------------------------------------------------------------------
    % Position of target FPGA on boundary scan chain (starts from 1).
    % ------------------------------------------------------------------
    t.ethernetCosim.bscanPos = 3;

    % ------------------------------------------------------------------
    % System ACE configuration profiles.
    % ------------------------------------------------------------------
    % Profile: Default
    profile(1).name = 'Default settings';
    profile(1).bootID = 4;
    profile(1).configFiles = { ...
        '$(XILINX)/xcfp/data/xcf32p_vo48.bsd', ...
        '$(BITFILE)', ...
        '$(XILINX)/xc9500xl/data/xc95144xl_tq100.bsd', ...
    };

    profile(2).name = 'Video I/O daughter card (VIODC)';
    profile(2).bootID = int32(6);
    profile(2).configFiles = { ...
        '$(XILINX)/xcfp/data/xcf32p_vo48.bsd', ...
        '$(BITFILE)', ...
        '$(XILINX)/xc9500xl/data/xc95144xl_tq100.bsd', ...
        '$(XILINX)/xcfp/data/xcf08p.bsd', ...
        '$(XILINX)/virtex2p/data/xc2vp7.bsd', ...
    };

    t.ethernetCosim.systemACEConfig.profiles = { ...
        profile(1), ...
        profile(2), ...
    };

    % Current System ACE configuration profile.
    t.ethernetCosim.systemACEConfig.current = profile(1).name;
