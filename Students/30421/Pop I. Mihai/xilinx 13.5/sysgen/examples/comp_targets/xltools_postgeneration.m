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
    errordlg(['-- An unkown error was encountered while running ' ...
             'the Xilinx Tools compilation flow.']);
    st = 1;  
  end;  
  
  current_dir = pwd;
  cd(params.directory);

  % Invoke the appropriate Xilinx tool depending on the selected
  % compilation target.
  if (strcmp(params.compilation, 'iMPACT')) 
    dos('impact');
  end; 

  if (strcmp(params.compilation, 'ChipScope Pro Analyzer')) 
    xlCallChipScopeAnalyzer;
  end; 

  cd(current_dir);
