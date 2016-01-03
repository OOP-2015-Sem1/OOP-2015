%
% Filename:    ML506_JTAG_Target.m
%
% Description: This file defines the supported and default compilation
%              settings for the ML506 (JTAG)
%

function settings = ML506_JTAG_Target

  % Specify compilation target version.
  settings.('version') = '11.2';

  % Define parts supported by the target.
  part0.('family') = 'virtex5';
  part0.('part') = 'xc5vsx50t';
  part0.('speed') = '-1';
  part0.('package') = 'ff1136';
  settings.('supported_parts').('allowed') = {part0};

  % Define hardware co-simulation clock settings.
  settings.('clock_settings').('source_period') = '5';
  settings.('clock_settings').('dut_period_allowed') = '[10, 15, 20, 30]';
  settings.('clock_settings').('dut_period_default') = '10';

  % Target has a fixed, free-running clock period.
  settings.('sysclk_period').('allowed') = settings.('clock_settings').('dut_period_default');

  % Define post-generation callback function.
  settings.('postgeneration_fcn') = 'ML506_JTAG_PostGeneration';

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
