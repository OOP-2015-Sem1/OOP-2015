%
% Filename:    virtex4_ml402_postgeneration.m
%
% Description: Defines board-specific parameters (e.g. ucf file, 
%              non-memory mapped ports) before invoking the 
%              generic JTAG post-generation callback function.

function st = virtex4_ml402_postgeneration(params)

  % Device position in the boundary scan chain (beginning at 1).
  params.boundary_scan_position = '3';

  % Instruction register length of every scan chain device. 
  params.instruction_register_lengths = '[8, 16, 10, 8]';

  % Constraints file to use for this compilation target.
  params.ucf_template = 'virtex4_ml402.ucf'; 

  % Define non-memory mapped ports.
  params.non_memory_mapped_ports = ML402_NonMmPorts;

  % You may use your own top-level netlist file by uncommenting the 
  % following line and setting the 'vendor_toplevel' field accordingly.
  % params.('vendor_toplevel') = 'virtex4_ml402_toplevel';

  % If you use your own top-level, you must tell SysGen what netlist 
  % files are required.  Set the 'vendor_netlists' field to a cell 
  % array listing the required file names. 
  % params.('vendor_netlists') = {'virtex4_ml402_toplevel.ngc','virtex4_ml402.edf'};

  % Invoke the JTAG post-generation callback function to run
  % Xilinx tools and create a run-time co-simulation token.
  try
    st = xlJTAGPostGeneration(params);
  catch
    errordlg(['-- An unknown error was encountered while running ' ...
             'the JTAG hardware co-simulation flow for the ' ...
             'Virtex4 ML402']);
    st = 1;
  end
