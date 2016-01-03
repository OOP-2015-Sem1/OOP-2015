%
% Description: Runs Xilinx tools after a configuration bitstream 
%              is generated for a given model.
%
function st = xltools_postgeneration(params)
  
 
  % Invoke bitstream post-generation callback function to run
  % the Xilinx tools and create a run-time co-simulation token.
  try
    st = xlBitstreamPostGeneration(params); 
  catch
    errordlg(['An unkown error was encountered while running ' ...
              'the Top Level EDIF/NGC compilation flow.']);
    st = 1;  
  end;  
