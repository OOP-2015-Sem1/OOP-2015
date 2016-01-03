%
% Filename:    S3ADSP_SK_JTAG_PostGeneration.m
%
% Description: Defines board-specific parameters (e.g. ucf file, 
%              non-memory mapped ports) before invoking the 
%              generic JTAG post-generation callback function.

function st = S3ADSP_SK_JTAG_PostGeneration(params)

  % Device position in the boundary scan chain (beginning at 1).
  params.('boundary_scan_position') = '1';

  % Instruction register length of every scan chain device. 
  params.('instruction_register_lengths') = '[6]';

  % Constraints file to use for this compilation target.
  params.('ucf_template') = 'S3ADSP_SK_JTAG.ucf'; 

  % Invoke the JTAG post-generation callback function to run
  % Xilinx tools and create a run-time co-simulation token.
  try
    st = xlJTAGPostGeneration(params);
  catch
    errordlg(['-- An unknown error was encountered while running ' ...
             'the JTAG hardware co-simulation flow for the ' ...
             'Spartan-3A DSP 1800A Starter Platform']);
    st = 1;
  end
