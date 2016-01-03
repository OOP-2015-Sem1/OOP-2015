%
% Filename:    xltools_target.m
%
% Description: This file defines the supported and default compilation
%              settings for the Xilinx tools compilation targets.
%
function settings = xltools_target

  % Define version of this target.
  settings.('version') = '11.2';

  % The testbench option is only controlled by the settings function.
  settings.('testbench').('allowed') = 'off';

  % Set default target directory for this target.
  settings.('directory') = './netlist';

  % List supported synthesis tools.
  settings.('synthesis_tool') = 'XST';

  % Define post-generation callback function.
  settings.('postgeneration_fcn') = 'xlBitstreamPostGeneration';

  % Define settings callback function.
  settings.('settings_fcn') = 'xlTopLevelNetlistGUI';
