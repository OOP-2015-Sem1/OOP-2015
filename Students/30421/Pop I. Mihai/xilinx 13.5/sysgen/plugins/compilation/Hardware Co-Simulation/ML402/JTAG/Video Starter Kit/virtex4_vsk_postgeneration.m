%
% Filename:    virtex4_vsk_postgeneration.m
%
% Description: Defines board-specific parameters (e.g. ucf file, 
%              non-memory mapped ports) before invoking the 
%              generic JTAG post-generation callback function.

function st = virtex4_vsk_postgeneration(params)

  % Device position in the boundary scan chain (beginning at 1).
  params.boundary_scan_position = '3';

  % Instruction register length of every scan chain device. 
  params.instruction_register_lengths = '[8, 16, 10, 8, 16, 10]';

  % Constraints file to use for this compilation target.
  params.ucf_template = 'virtex4_vsk.ucf'; 

  % Define non-memory mapped ports.
  params.non_memory_mapped_ports = ML402_NonMmPorts;

  % Invoke the JTAG post-generation callback function to run
  % Xilinx tools and create a run-time co-simulation token.
  try
    st = xlJTAGPostGeneration(params);
  catch
    errordlg(['-- An unknown error was encountered while running ' ...
             'the JTAG hardware co-simulation flow for the ' ...
             'Virtex4 ML402 Video Starter Kit']);
    st = 1;
  end
