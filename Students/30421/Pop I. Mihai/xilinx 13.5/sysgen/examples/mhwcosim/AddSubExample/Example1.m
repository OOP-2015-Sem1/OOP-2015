% Specifies a hardware co-simulation project file.
prj = 'addsubexample_cw.hwc';

% Configure the co-simulation interface. Note: This needs only to be
% done once, since the configuration is stored back into the hwc file
% This will launch a configuration GUI.
xlHwcosimConfig(prj, true);
 
% Define the number of simulation cycles.
nCycles = 1000;
% Pre-allocates a buffer to hold the result.
result = zeros(1, nCycles);
 
% Creates a hardware co-simulation instance from the project
% 'mydesign.hwc'.
h = Hwcosim(prj);
 
% Opens and configures the hardware co-simulation interface.
open(h);
 
% Initializes the 'op' input port with a constant value zero.
h('op') = 0;
% Simulates the design.
for l = 1:nCycles
% Writes a random number to each of the input ports, x1 and x2.
h('x1') = rand;
h('x2') = rand;
% Reads the current value of output port y into the result buffer.
result(l) = h('y');
% Single-steps the clock of the design to advance to the next cycle.
run(h);
end
% Releases the hardware co-simulation instance.
% The hardware co-simulation interface is closed implicitly.
release(h);
