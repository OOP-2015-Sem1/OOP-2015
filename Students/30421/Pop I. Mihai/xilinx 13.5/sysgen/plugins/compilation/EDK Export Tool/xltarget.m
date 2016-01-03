%
% Filename:    xltarget.m 
%
% Description: Defines the target compilation entry point for  
%              the MicroBlaze Multimedia Demonstration Board.

function s = xltarget
  s.('name') = 'Export as a pcore to EDK';
  s.('target_info') = 'xledkexporttarget';
