%
% Filename:    virtex4_vsk_target.m
%
% Description: This file defines the supported and default compilation
%              settings for the Virtex4 Video Starter Kit.
%

function settings = virtex4_vsk_target

  % Specify compilation target version.
  settings.('version') = '9.2';

  % Define parts supported by the target.
  part0.('family') = 'virtex4';
  part0.('part') = 'xc4vsx35';
  part0.('speed') = '-10';
  part0.('package') = 'ff668';
  settings.('supported_parts').('allowed') = {part0};

  % Define hardware co-simulation clock settings.
  settings.('clock_settings').('source_period') = '10';
  settings.('clock_settings').('dut_period_allowed') = '[10, 15, 20, 30]';
  settings.('clock_settings').('dut_period_default') = '10';

  % Target has a fixed, free-running clock period.
  settings.('sysclk_period').('allowed') = settings.('clock_settings').('dut_period_default');

  % Define post-generation callback function.
  settings.('postgeneration_fcn') = 'virtex4_vsk_postgeneration';

  % Set default target directory for this target.
  settings.('directory') = './netlist';

  % List supported synthesis tools.
  settings.('synthesis_tool') = 'XST';

  % Define pre-compile callback function.
  settings.('precompile_fcn') = 'xlJTAGPreCompile';

  % Define post-generation callback function.
  settings.('getimportblock_fcn') = 'xlGetHwcosimBlockName';

  % Disable the clock location constraint field.
  settings.('clock_loc').('allowed') = 'Fixed';

  % Define a hardware co-simulation settings GUI.
  settings.('settings_fcn') = 'xlJTAGXFlowSettings';
