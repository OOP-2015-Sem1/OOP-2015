%
% Filename:    yourboard_postgeneration.m
%
% Description: Template file that defines board-specific parameters 
%              (e.g. ucf file, non-memory mapped ports) before 
%              invoking the generic JTAG post-generation function.
%

  % (1) Rename the yourboard_postgeneration function.
function st = yourboard_postgeneration(params)
  %           ~~~~~~~~~~~~~~~~~~~~~~~~        
  
  % (2) Set the value of the 'boundary_scan_position' field to the 
  %     position of your FPGA in the Boundary-Scan chain (beginning at 1).
  params.('boundary_scan_position') = '2';  
  %                                    ~
                          
  % (3) Set the value of the 'instruction_register_lengths' field to the 
  %     vector of instruction register lengths in the Boundary-Scan chain.
  params.('instruction_register_lengths') = '[8, 6]'; 
  %                                          ~~~~~~

  % (4) Set the value of the 'ucf_template' field to match the name of
  %     your board's UCF file.
  params.('ucf_template') = 'yourboard.ucf'; 
  %                          ~~~~~~~~~~~~~
  
  % Specify board-specific I/O ports as follows (ensure the ports are
  % also declared in your board's UCF file):
  % non_mm_ports.('adc1_d') = {'in', 14};
  % non_mm_ports.('dac1_d') = {'out', 14};
  % ...
  % params.('non_memory_mapped_ports') = non_mm_ports;
  
  % You may use your own top-level netlist file by uncommenting the 
  % following line and setting the 'vendor_toplevel' field accordingly.
  % params.('vendor_toplevel') = 'yourboard_toplevel';

  % If you use your own top-level, you must tell SysGen what netlist 
  % files are required.  Set the 'vendor_netlists' field to a cell 
  % array listing the required file names. 
  % params.('vendor_netlists') = {'yourboard_toplevel.ngc','foo.edf'};

  % Invoke the JTAG post-generation callback function to run
  % Xilinx tools and create a run-time co-simulation token.
  try
    st = xlJTAGPostGeneration(params); 
  catch
    errordlg(['-- An unkown error was encountered while running ' ...
             'the JTAG hardware co-simulation flow.']);
    st = 1;  
  end   
