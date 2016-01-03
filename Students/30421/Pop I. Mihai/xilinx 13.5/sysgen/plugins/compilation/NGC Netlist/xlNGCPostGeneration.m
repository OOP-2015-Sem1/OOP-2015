%
% XLNGCPOSTGENERATION
%
% Copyright (c) 2005, Xilinx, Inc. All rights reserved.
%
% Post-generation function for compiling a System Generator
% design into a standalone NGC file.
%
% @param target_params Struct defining configuration of selected
%        compilation target.
% @return status A non-zero status indicates an error.
%
function status = xlNGCPostGeneration(target_params)

    status.status_code = 0;
    status.err_msg = '';

    % Find the model's target directory.
    target_dir = target_params.directory;

    % Set defaults.
    include_clockwrapper = true;
    include_cf = true;
    omit_iobs = true;

    % Extract NGC configuration parameters from GUI.
    has_ngc_config = isfield(target_params, 'ngc_config');
    if (has_ngc_config)
        include_clockwrapper = target_params.ngc_config.include_clockwrapper;
        if (~include_clockwrapper)
            include_cf = 0;
        else
            include_cf = target_params.ngc_config.include_cf;
        end
    end

    programmatic_mode = isfield(target_params, 'programmaticmode') && target_params.programmaticmode;
    originalwb = target_params.wb;

    % Optionally insert IOBs.
    if (isfield(target_params, 'insertIOBs') && target_params.insertIOBs)
        omit_iobs = false;
    end

    % Create a new wait-box object.
    wb = com.xilinx.sysgen.guitools.xlNgcCompileWaitBox(target_dir, omit_iobs, include_clockwrapper, ...
        include_cf, programmatic_mode, originalwb);

    % Set the M-code callback function.
    if (~programmatic_mode)
      el_id = xlEventListenerManager('add',wb,'ActionPerformed',@xlNGCCompileCallback);
    else
      el_id=[];
    end

    xlSetSgFigEnable(target_params.block, 'off');
    ud = get(wb, 'UserData');
    ud.target_params = target_params;
    ud.eventListener_id=el_id;
    set(wb, 'UserData', ud);
    wb.start();
    xWb = wb.getWaitBox();
    if ~isempty(xWb)
        wbf = xWb.getFrame();
        if ~isempty(wbf) 
            wbf.toFront();
        end
    end

    if (programmatic_mode)
        wb.join();
        wb.stopGUI();
    	if ~isempty(xWb)
        	wb_errmsg = char(xWb.getErrStr());
        	if ~isempty(wb_errmsg)
            		status.err_msg = ['An error occurred during NGC netlisting. ' wb_errmsg];
            		status.status_code = 1;
            		errordlg(wb_errmsg, 'NGC Netlisting Error');
        	end
        end
    end
