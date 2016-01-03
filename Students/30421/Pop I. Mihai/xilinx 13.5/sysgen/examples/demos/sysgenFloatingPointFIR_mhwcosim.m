%Configure the hardware cosim block to be free running or in single stepped
%mode. Also, if your design has a chipscope block, make sure that you
%enable the cable sharing option. 
%xlHwcosimConfig('fir_filter_cw.hwc');


% Creates a hardware co-simulation instance from the project 'fir_filter_cw.hwc'.
h = Hwcosim('fir_filter_cw.hwc');
 
% Opens and configures the hardware co-simulation interface.
open(h);

fifo_depth = 4095; 
load sysgenFloatingPointFIRInput.mat
test_output = 0;
ncycles = floor(length(test_input)/fifo_depth);

% Creates a shared FIFO instance 'WriteFifo' for writing data to the 
% hardware. Similarly, creates another shared FIFO instance 'ReadFifo' for 
% reading data from the hardware. 
wf = Shfifo('CA');
rf = Shfifo('VA');

%Configuring the read and write fifo appropriately
set(wf, 'Signed', true)
set(wf, 'FloatingPoint', true)
set(wf, 'BinaryPoint', 24)
set(rf, 'Signed', true)
set(rf, 'FloatingPoint', true)
set(rf, 'BinaryPoint', 24)

%Pass the data(based upon the fifo_depth) into the FIR Filter. 
for i = 1: ncycles
	temp = i*fifo_depth - fifo_depth + 1;
	temp_data = test_input(temp: (temp+fifo_depth - 1));
	write(wf, fifo_depth, temp_data);  
	temp_read = read(rf, fifo_depth) ;
	test_output = [ test_output temp_read];
end
 
remainder = rem(length(test_input), fifo_depth);

%if the remainder is non-zero, pass the remaining data into the FIR Filter
if ne(remainder, 0) 
	write(wf, remainder, test_input((ncycles*fifo_depth): ((ncycles*fifo_depth) + remainder - 1)));  
	temp_read = read(rf, remainder) ;
	test_output = [ test_output temp_read];
end

%removing the 0 that was added previously from the output
test_output = test_output(2:20002);

%Plotting the input and the output
fft_input = abs(fft(test_input));
fft_output = abs(fft(test_output));
length_input = length(test_input);
fft_length = floor(length_input/2);
t = 1:fft_length; 
figure;
subplot(2,1,1);
plot(t, fft_input(1:fft_length));
title('FFT of un-filtered input');
subplot(2,1,2);
plot(t, fft_output(1:fft_length));
title('FFT of filtered output');


%release the fifos and the hardware co-sim instance.
release(rf)
release(wf) 
release(h)
