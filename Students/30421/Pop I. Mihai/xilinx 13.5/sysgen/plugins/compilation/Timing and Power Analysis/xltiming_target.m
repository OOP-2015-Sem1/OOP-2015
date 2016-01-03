function settings = xltiming_target

  % Define version of this target.
  settings.('version') = '11.2';

  % The testbench option is only controlled by the settings function.
  settings.('testbench').('allowed') = 'off';

  % Set default target directory for this target.
  settings.('directory') = './timing';

  % List supported synthesis tools.
  settings.('synthesis_tool') = 'XST';

  % Define post-generation callback function.
  settings.('postgeneration_fcn') = 'xlTimingPostGeneration';

  % Define settings callback function.
  settings.('settings_fcn') = 'xlTimingSettings';
