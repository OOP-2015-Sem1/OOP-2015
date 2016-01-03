%
% Filename:    new_comp_target.m
%
% Description: Launches Sysgen Board Description GUI
%
function settings = new_comp_target
  
  settings.('selectedcallback_fcn') = 'xlSBDBuilder;';

  % Define a hardware co-simulation settings GUI.
  settings.('ignore_target') = '1';
