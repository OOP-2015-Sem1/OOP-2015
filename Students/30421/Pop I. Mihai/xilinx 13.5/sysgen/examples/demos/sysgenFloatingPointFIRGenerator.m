function sysgenFloatingPointFIRGenerator(filter_coefficients)


%xBlock;
n_bits = length(filter_coefficients);

if isequal(n_bits, 0)
    error('n_bits cannot be 0')
end

%Define the input and output ports
[data_in] = xInport('data_in');
[enable] = xInport('enable');
[data_out] = xOutport('data_out');

%Generate the first tap
if isequal(n_bits, 1)
    xBlock('lib/Coefficient1', struct('coeff', filter_coefficients(1)), {data_in, enable}, {data_out});
    return
end

inter{1} = xSignal(['tap_' num2str(1) '_out']);
xBlock('sysgenFloatingPointFIR_Lib/Coefficient1', struct('coeff', filter_coefficients(1)), {data_in, enable}, inter(1));

%Generate the remaining taps
for i=2:n_bits
    inter{i} = xSignal(['tap_' num2str(i) '_out']);
    xBlock('sysgenFloatingPointFIR_Lib/1_tap_filter', struct('filter_coefficient', filter_coefficients(i)), {data_in, inter{i-1}, enable}, inter(i));
end

%Assign the internal signal to the output
data_out.bind(inter{i});
