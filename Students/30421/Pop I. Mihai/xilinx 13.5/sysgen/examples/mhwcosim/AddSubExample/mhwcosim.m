function addsubexample_cw_hwcosim_test
  try
    % Define the number of hardware cycles for the simulation.
    ncycles = 10;
    origDir = pwd;
    cd('netlist');
    % Load input and output test reference data.
    testdata_x1 = getfield(load('addsubexample_cw_x1_hwcosim_test.dat', '-mat'), 'values');
    testdata_x2 = getfield(load('addsubexample_cw_x2_hwcosim_test.dat', '-mat'), 'values');
    testdata_y = getfield(load('addsubexample_cw_y_hwcosim_test.dat', '-mat'), 'values');

    % Pre-allocate memory for test results.
    result_y = zeros(size(testdata_y));
    insp_1 = 1;
    outsp_1 = 1;

    % Define hardware co-simulation project file.
    project = 'addsubexample_cw.hwc';

    % Create a hardware co-simulation instance.
    h = Hwcosim(project);

    % Open the co-simulation interface and configure the hardware.
    try
      open(h);
    catch
      % If an error occurs, launch the configuration GUI for the user
      % to change interface settings, and then retry the process again.
      release(h);
      xlHwcosimConfig(project, true);
      drawnow;
      h = Hwcosim(project);
      open(h);
    end

    % Simulate for the specified number of cycles.
    for i = 0:(ncycles-1)

      % Write data to input ports based their sample period.
      h('x1') = testdata_x1(insp_1);
      h('x2') = testdata_x2(insp_1);
      insp_1 = insp_1 + 1;

      % Read data from output ports based their sample period.
      result_y(outsp_1) = h('y');
      outsp_1 = outsp_1 + 1;

      % Advance the hardware clock for one cycle.
      run(h);

    end

    % Release the hardware co-simulation instance.
    release(h);

    result_y = result_y(1:(outsp_1-1));

    % Check simulation result for each output port.
    logfile = 'addsubexample_cw_hwcosim_test.results';
    logfd = fopen(logfile, 'w');
    sim_ok = true;
    sim_ok = sim_ok & check_result(logfd, 'y', testdata_y, result_y, ncycles);
    fclose(logfd);
    if ~sim_ok
      error('Found errors in the simulation results. Please refer to ''%s'' for details.', logfile);
    end
    cd(origDir);
  catch
    err = lasterr;
    try release(h); end
    error('Error running hardware co-simulation testbench. %s', err);
  end

%-----------------------------------------------------------------------------

function ok = check_result(fd, portname, expected, actual, ncycles)
  ok = false;

  fprintf(fd, ['\n' repmat('=', 1, 95), '\n']);
  fprintf(fd, 'Output: %s\n\n', portname);

  % Check for simulation mismatches.
  nvals = min(numel(expected), numel(actual));
  expected = reshape(expected(1:nvals), 1, nvals);
  actual = reshape(actual(1:nvals), 1, nvals);
  mismatches = find(expected ~= actual);
  num_mismatches = numel(mismatches);
  if num_mismatches > 0
    fprintf(fd, 'Number of simulation mismatches = %d\n', num_mismatches);
    fprintf(fd, '\n');
    fprintf(fd, 'Simulation mismatches:\n');
    fprintf(fd, '----------------------\n');
    fprintf(fd, '%10s %40s %40s\n', 'Cycle', 'Expected values', 'Actual values');
    fprintf(fd, '%10d %40.16f %40.16f\n', ...
            [mismatches-1; expected(mismatches); actual(mismatches)]);
    return;
  end

  ok = true;
  fprintf(fd, 'Simulation OK\n');
  disp(sprintf('Simulation OK\n'));
  disp('----------------------------------------------------------------');
  disp(sprintf('%5s %25s %25s', 'Cycle', 'Expected values', 'Actual values'));
  disp('----------------------------------------------------------------');
  for l = 1:ncycles
   disp(sprintf('%3d %30.16f %25.16f\n', ...
            [l-1; expected(l); actual(l)]));
  end        
   disp('---------------------------------------------------------------'); 