%
% XTREMEDSPPCICONFIGATTRIBUTES
%
% Copyright (c) 2005, Xilinx, Inc. All rights reserved.
%
% Defines vendor configuration attributes for the XtremeDSP 
% Development kit.
%

function config_attributes = XtremeDSPConfigAttributes(target_params)
 
    % ---------------------------------------------------------------
    % Find the directory where the XtremeDSP Kit files are installed.
    % ---------------------------------------------------------------
    [vendor_dir, unused, unused] = fileparts(which('XtremeDSPRuntimeCosim_r4'));
    
    % ---------------------------------------------------------------
    % Specify the project and company name for this target.
    % ---------------------------------------------------------------
    config_attributes.company_name = 'Nallatech';
    config_attributes.project_name = 'xdspkit';

    % ---------------------------------------------------------------
    % Define top-level constraints file. 
    % ---------------------------------------------------------------
    config_attributes.ucf_name = 'benone_top_pci.ucf';
    
    % ---------------------------------------------------------------
    % Define constraints file template from which ucf is derived.
    % ---------------------------------------------------------------
    config_attributes.ucf_template = ['benone_top_pci_' target_params.part '.ucf'];

    % ---------------------------------------------------------------
    % Define non memory-mapped ports.
    % ---------------------------------------------------------------
    config_attributes.non_memory_mapped_ports = XtremeDSPNonMmPorts;

    % ---------------------------------------------------------------
    % Define the co-simulation library.
    % ---------------------------------------------------------------
    config_attributes.cosim_library = 'XtremeDSPRuntimeCosim_r4';

    % ---------------------------------------------------------------
    % Define vendor netlist files.
    % ---------------------------------------------------------------
    config_attributes.netlistFiles = { ...
       [vendor_dir filesep 'benone_top_pci.ngc'], ...
       [vendor_dir filesep 'rm16x32d.ngc'] ...
    };

    % ---------------------------------------------------------------
    % Define netlist top-level file.
    % ---------------------------------------------------------------
    config_attributes.netlistTopLevel = 'benone_top_pci';

    % ---------------------------------------------------------------
    % Define XFlow options files.
    % ---------------------------------------------------------------
    if (isfield(target_params, 'xflow_settings'))
        config_attributes.implOptionsFile = target_params.xflow_settings.compoptfile; 
	config_attributes.configOptionsFile = target_params.xflow_settings.configoptfile; 
    else
        config_attributes.implOptionsFile = [vendor_dir filesep 'balanced_xdsp.opt'];
	config_attributes.configOptionsFile = [vendor_dir filesep 'bitgen_xdsp.opt'];
    end
