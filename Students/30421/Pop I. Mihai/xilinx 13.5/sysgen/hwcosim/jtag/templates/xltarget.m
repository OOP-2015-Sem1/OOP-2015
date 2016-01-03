%
% Filename:    xltarget.m 
%
% Description: Template file that defines the target compilation   
%              entry point for a given board.

function s = xltarget
  % (1) Set the value of the 'name' field to the name of your
  %     compilation target (as you'd like it to appear in the 
  %     SysGen block dialog box).
  s.('name') = 'Foo (JTAG)';
  %             ~~~~~~~~~~

  % (2) Set the value of the 'target_info' field to point to
  %     yourboard_target.m.
  s.('target_info') = 'yourboard_target';
  %                    ~~~~~~~~~~~~~~~~
