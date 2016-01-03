function status = xlTimingPostGeneration(target_params)

    status = 0;

    % Find the model's target directory.
    target_dir = target_params.directory;

    base_dir = xilinx.environment.getpath('sysgen', '', 'plugins/compilation/Timing and Power Analysis');
    custom_settings = xlgetfield(target_params, 'custom_settings', struct());

    % Determine XFlow implementation options file.
    impl_opt_file = fullfile(base_dir, 'par.opt');
    impl_opt_file = xlgetfield(custom_settings, 'impl_opt_file', impl_opt_file);
    impl_opt_file = xlGetAbsoluteFilename(impl_opt_file);

    % Determine XFlow timing analysis options file.
    trace_opt_file = fullfile(base_dir, 'trace.opt');
    trace_opt_file = xlgetfield(custom_settings, 'trace_opt_file', trace_opt_file);
    trace_opt_file = xlGetAbsoluteFilename(trace_opt_file);

    % Determine power analysis mode.
    power_analysis_mode = xlgetfield(custom_settings, 'power_analysis_mode', '');
    % Simulation-based power analysis cannot be supported if testbench generation is disabled.
    if strcmp(power_analysis_mode, 'isim-simulation') && strcmp(target_params.testbench, 'off')
        power_analysis_mode = '';
    end

    programmatic_mode = false;
    originalwb = target_params.wb;
    if (isfield(target_params, 'programmaticmode'))
        programmatic_mode = true;
    end

    % Create a new wait-box object.
    wb = com.xilinx.sysgen.guitools.xlTimingWaitBox(target_dir, ...
        impl_opt_file, trace_opt_file, power_analysis_mode, ...
        programmatic_mode, originalwb);

    el_id = xlEventListenerManager('add',wb,'ActionPerformed',@xlTimingCallback);
    xlSetSgFigEnable(target_params.block, 'off');
    ud = get(wb,'UserData');
    ud.target_params = target_params;
    ud.eventListener_id=el_id;
    set(wb,'UserData',ud);
    wb.start();

    if (programmatic_mode)
        wb.join;
    end
