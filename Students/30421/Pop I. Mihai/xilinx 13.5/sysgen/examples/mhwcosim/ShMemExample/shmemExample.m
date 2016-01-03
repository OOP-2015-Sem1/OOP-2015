% Specifies a hardware co-simulation project file.
prj = 'shmem_cw.hwc';

% Configure the co-simulation interface. Note: This needs only to be
% done once, since the configuration is stored back into the hwc file
% This will launch a configuration GUI.
xlHwcosimConfig(prj, true);

% Creates a hardware co-simulation instance from the project.
h = Hwcosim(prj);
 
% Opens and configures the hardware co-simulation interface.
open(h);
 
% Creates a shared memory instance 'MyMem'. It connects the corresponding
% shared memory running in hardware.
m = Shmem('MyMem');
 
% Creates a shared FIFO instance 'WriteFifo' for writing data to the
% hardware. Similarly, creates another shared FIFO instance 'ReadFifo' for
% reading data from the hardware.
wf = Shfifo('WriteFifo');
rf = Shfifo('ReadFifo');
 
% Writes random numbers to memory address 0 to 49 of MyMem.
m(0:49) = rand(1, 50);
 
% Read the value at memory address 100 of MyMem.
y = m(100);
 
% Writes 10 random numbers to WriteFifo if it has 10 or more empty space.
if wf.Available >= 10
write(wf, 10, rand(1, 10));
end
 
% Reads 5 values from ReadFifo if it has 5 or more data.
if rf.Available >= 5
d = read(rf, 5);
end
 
% Releases the shared memory instances.
release(m);
release(wf);
release(rf);
 
% Releases the hardware co-simulation instance.
release(h);
