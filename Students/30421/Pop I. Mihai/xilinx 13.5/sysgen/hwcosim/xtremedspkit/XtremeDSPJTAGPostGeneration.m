
%
% XTREMEDSPJTAGPOSTGENERATION
%
% Copyright (c) 2005, Xilinx, Inc. All rights reserved.
%
% Defines board-specific parameters (e.g. ucf file, non-memory
% mapped ports) before invoking the generic JTAG post-generation
% callback function.
%
% @param target_params Struct defining compilation parameters for
%        XtremeDSP compilation target.
%

function status = XtremeDSPJTAGPostGeneration(target_params)

    status = 0;

    % ************************************************************
    % * Define board specific parameters as additional fields on
    % * the target_params struct.
    % ************************************************************

    % Determine if V2Pro XtremeDSP Kit is selected.
    is_v2pro = strcmp('xc2vp30', target_params.part);
    is_v4 = strcmp('xc4vsx35', target_params.part);

    % Device position in the boundary scan chain (beginning at 1).
    target_params.boundary_scan_position = '4';

    % Override JTAG top-level using benone_top_jtag.
    target_params.vendor_toplevel = 'benone_top_jtag';

    % Specify additional vendor NGC components.
    target_params.vendor_netlists = {'benone_top_jtag.ngc'};

    % Constraints file to use for this compilation target.
    target_params.ucf_template = ['benone_top_jtag_' target_params.part '.ucf'];

    % Instruction register length of every scan chain device.
    if (is_v2pro)
        target_params.instruction_register_lengths = '[8 8 8 14 6]';
    elseif (is_v4)
        target_params.instruction_register_lengths = '[8 8 8 10 6]';
    else
        target_params.instruction_register_lengths = '[8 8 8 6 6]';
    end

    % Specify non-memory mapped ports for this target.
    target_params.non_memory_mapped_ports = XtremeDSPNonMmPorts;

    % Add the XtremeDSP startup mex_function to the block.
	target_params.block_params.preConfigCallbackString = [ ...
        'XtremeDSPJTAGSetup(' ...
        'fileparts(which(get_param(gcb,''''parent''''))),''''' ...
        target_params.part ''''',' ...
        'get_param(gcb,''''cable''''),' ...
        'get_param(gcb,''''speed''''));'];

    % ************************************************************
    % * Invoke JTAG post-generation callback function to run the
    % * Xilinx tools and create a run-time co-simulation token.
    % ************************************************************
    status = xlJTAGPostGeneration(target_params);
