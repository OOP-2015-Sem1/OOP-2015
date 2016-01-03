%
% Filename:    yourboard_target.m
%
% Description: Template file that defines the supported and default
%              compilation settings on the SysGen GUI for a given
%              board.
%

  % (1) Rename the yourboard_target function.
function settings = yourboard_target
  %                 ~~~~~~~~~~~~~~~~

  % (2) Set the values of the 'family', 'part', 'speed' and
  %     'package' fields to match the FPGA device on your board.
  my_part.('family') = 'Virtex2';
  %                     ~~~~~~~
  my_part.('part') = 'xc2v1000';
  %                   ~~~~~~~~
  my_part.('speed') = '-4';
  %                    ~~
  my_part.('package') = 'ff896';
  %                      ~~~~~

  settings.('supported_parts').('allowed') = {my_part};

  % (3) Set the value of the 'sysclk_period' field to match the
  %     period of the clock on your board (in ns).
  settings.('sysclk_period').('allowed') = '37';
  %                                         ~~

  % (4) Rename the post-generation function.
  settings.('postgeneration_fcn') = 'yourboard_postgeneration';
  %                                  ~~~~~~~~~~~~~~~~~~~~~~~~

  % Testbench should not be generated for this target.
  settings.('testbench').('allowed') = 'off';

  % Specify default target directory for this target.
  settings.('directory') = './netlist';

  % List supported synthesis tools.
  settings.('synthesis_tool') = 'XST';

  % Specify pre-compile callback function.
  settings.('precompile_fcn') = 'xlJTAGPreCompile';

  % This target can be imported as a configurable subsystem.
  settings.('getimportblock_fcn') = 'xlGetHwcosimBlockName';

  % Disable the clock location constraint field.
  settings.('clock_loc').('allowed') = 'Fixed';
