%
% XTREMEDSPTARGET
%
% Copyright (c) 2005, Xilinx, Inc. All rights reserved.
%
% Defines Sysgen GUI settings for the Xilinx XtremeDSP
% development kit.
%
% @param target_type Tells the compilation target type.
%

function settings = XtremeDSPTarget(target_type)

    settings.version = '12.1';

    part.family = 'Virtex4';
    part.part = 'xc4vsx35';
    part.speed = '-10';
    part.package = 'ff668';

    settings.supported_parts.allowed = { part };

    % Configure the Sysgen GUI with a default target directory.
    settings.directory = './netlist';

    % Use XST as default synthesis tool default.
    settings.synthesis_tool = 'XST';

    % Use a default clock period of 25 ns.
    settings.sysclk_period = '25';

    % Define the post-generation function for this target.
    if (strcmp(target_type,'jtag'))
        settings.precompile_fcn = 'XtremeDSPJTAGPreCompile';
        settings.postgeneration_fcn = 'XtremeDSPJTAGPostGeneration';
        settings.settings_fcn = 'xlJTAGXFlowSettings';
    else
        settings.precompile_fcn = 'XtremeDSPPCIPreCompile';
        settings.postgeneration_fcn = 'XtremeDSPPCIPostGeneration';
        settings.cosim_library = 'XtremeDSPRuntimeCosim_r4';
        settings.settings_fcn = 'XtremeDSPXFlowSettings';
    end

    % This target can be imported as a configurable subsystem.
    settings.getimportblock_fcn = 'xlGetHwcosimBlockName';

    % Disable the clock location constraint field.
    settings.clock_loc.allowed = 'Fixed';
