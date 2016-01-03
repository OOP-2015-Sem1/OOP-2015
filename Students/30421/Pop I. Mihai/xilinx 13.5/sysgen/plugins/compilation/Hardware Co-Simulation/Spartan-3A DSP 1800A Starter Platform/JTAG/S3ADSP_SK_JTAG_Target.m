%
% Filename:    S3ADSP_SK_JTAG_Target.m
%
% Description: This file defines the supported and default compilation
%              settings for the Spartan-3A DSP 1800A Starter Platform (JTAG)
%

function settings = S3ADSP_SK_JTAG_Target

  % Specify compilation target version.
  settings.('version') = '11.2';

  % Define parts supported by the target.
  part0.('family') = 'spartan3adsp';
  part0.('part') = 'xc3sd1800a';
  part0.('speed') = '-4';
  part0.('package') = 'fg676';
  settings.('supported_parts').('allowed') = {part0};

  % Define hardware co-simulation clock settings.
  settings.('clock_settings').('source_period') = '8';
  settings.('clock_settings').('dut_period_allowed') = '[15, 20, 30]';
  settings.('clock_settings').('dut_period_default') = '20';

  % Target has a fixed, free-running clock period.
  settings.('sysclk_period').('allowed') = settings.('clock_settings').('dut_period_default');

  % Define post-generation callback function.
  settings.('postgeneration_fcn') = 'S3ADSP_SK_JTAG_PostGeneration';

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
