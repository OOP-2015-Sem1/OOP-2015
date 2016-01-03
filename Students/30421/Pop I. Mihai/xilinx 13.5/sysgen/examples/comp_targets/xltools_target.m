%
% Filename:    xltools_target.m
%
% Description: This file defines the supported and default compilation
%              settings for the Xilinx tools compilation targets.
%
function settings = xltools_target

  % Testbench should not be generated for this target.
  settings.('testbench').('allowed') = 'off';

  % Set default target directory for this target.
  settings.('directory') = './netlist';

  % List supported synthesis tools.
  settings.('synthesis_tool') = 'XST';

  % Define post-generation callback function.
  settings.('postgeneration_fcn') = 'xltools_postgeneration';
