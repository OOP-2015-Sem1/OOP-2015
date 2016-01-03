%
% Filename:    xlNGCTarget.m
%
% Description: This file defines the supported and default compilation
%              settings for the System Generator NGC export target. 
%
function settings = xlNGCTarget 

  % Set default target directory for this target.
  settings.('directory') = './ngc_netlist';

  % Define post-generation callback function. 
  settings.('postgeneration_fcn') = 'xlNGCPostGeneration';

  % Define a settings GUI for this target.
  settings.('settings_fcn') = 'xlngcsettings';
