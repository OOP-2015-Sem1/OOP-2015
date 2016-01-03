% Configure the co-simulation interface. Note: This needs only to be
% done once, since the configuration is stored back into the hwc file
% This will launch a configuration GUI.
xlHwcosimConfig('addsubexample_cw.hwc', true);
 
% Define the number of simulation cycles.
nCycles = 1000;
 
% Creates a hardware co-simulation instance from the project
% 'mydesign.hwc'.
h = Hwcosim('addsubexample_cw.hwc');
 
% Opens and configures the hardware co-simulation interface.
open(h);
 
% Initializes the 'op' input port with a constant value zero.
write(h, 'op', 0);
 
% Initializes an execution definition that covers the input ports,
% x1 and x2, and the output ports y. It returns an execution
% identifier for use in subsequent exec instructions.
execId = initExec(h, {'x1', 'x2'}, {'y'});
 
% Simulate the design using the exec instruction.
% The input data are given as a 2-D matrix. Each row of the matrix
% gives the simulation data of an input port for all the cycles.
% For example, row i column j stores the data for the i-th port at
% (j-1)th cycle.
result = exec(h, execId, nCycles, rand(2, nCycles));
 
% Releases the hardware co-simulation instance.
% The hardware co-simulation interface is closed implicitly.
release(h);
