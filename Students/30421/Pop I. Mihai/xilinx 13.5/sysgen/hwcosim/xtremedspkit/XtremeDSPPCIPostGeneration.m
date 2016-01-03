%
% XTREMEDSPPCIPOSTGENERATION
%
% Copyright (c) 2005, Xilinx, Inc. All rights reserved.
%
% Hardware co-simulation post-generation function for the
% XtremeDSP Development Kit.  The function performs the
% following tasks:
%
% - It invokes the hardware co-simulation wrapper builder.
% - It runs the Xilinx point-tools necessary to produce a bitstream.
% - It creates a new run-time co-simulation library and block.
%
% @param target_params Struct defining compilation parameters for
%        XtremeDSP compilation target.
%

function status = XtremeDSPPCIPostGeneration(target_params)

    %import com.xilinx.sysgen.netlister.*;

    status = 0;

    % Obtain model target directory.
    target_dir = target_params.directory;

    CONST_ERRORMSG = ['An internal error was encountered while compiling <br>' ...
                      'the design for XtremeDSP hardware co-simulation.'];
    CONST_LOGFILE = [target_dir filesep 'xtremedsp_error.log'];
    
    % Find the XtremeDSP Kit install directory.
    [vendor_dir,unused, unused] = fileparts(which(target_params.cosim_library));
    if (isempty(vendor_dir))
        status = xlHwcosimError(CONST_LOGFILE, CONST_ERRORMSG, ...
            'XtremeDSP hardware co-simulation files could not be located');
        return;
    end

    % Specify vendor attributes.
    config_xtable = xlMatlabToJavaXTable(XtremeDSPConfigAttributes(target_params));
    %config_xtable.put('skipCompilation', 1);

    % Fetch design attributes from the synopsis file.
    block_info = xlGetHwcosimInfo(target_dir, config_xtable);

    cwd = pwd;
    try
        cd(target_dir);
        % Invoke HwcosimWrapperBuilder to construct an appropriate hardware
        % co-simulation wrapper for the Sysgen top-level design.
        java_cmd = ['com.xilinx.sysgen.netlister.HwcosimWrapperBuilder.buildHwcosimFiles' ...
            '(target_dir, config_xtable, vendor_dir);'];
        [unused, e_msg] = xlJavaExceptionCatch(java_cmd, ...
            'com.xilinx.sysgen.netlist.NetlistException');
        cd(cwd);
        if (e_msg)
            status = xlHwcosimError(CONST_LOGFILE, e_msg, e_msg);
            return;
        end
    catch
        cd(cwd);
        status = xlHwcosimError(CONST_LOGFILE, CONST_ERRORMSG, lasterr);
        return;
    end

    % Specify vendor specific block parameters.
    target_params.block_params.bitFileName = [strrep(target_dir,'/',filesep) filesep ...
                               block_info.topLevelName '.bit'];
    target_params.block_params.prgClkFreq = 1000/block_info.systemClockPeriod;

    % Add additional parameters for ModelSim hardware in-the-loop.
    target_params.mti_params = target_params.block_params;
    target_params.mti_params.binDir = vendor_dir;
    target_params.mti_params.dllName = 'XtremeDSPCosimEngine';
    target_params.mti_params.clkSrc = 1;
    target_params.mti_params.busType = 1;
    target_params.mti_params.cardNumber = 1;

    % Compile design into a bitstream.
    xlStartHwcosimWaitBox(target_dir, target_params, config_xtable);
