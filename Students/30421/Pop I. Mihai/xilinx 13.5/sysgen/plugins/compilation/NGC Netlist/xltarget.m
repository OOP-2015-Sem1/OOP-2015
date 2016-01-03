%
% Filename:    xltarget.m 
%
% Description: Defines the target compilation entry point for  
%              the System Generator NGC export target.

function s = xltarget
  s.('name') = 'NGC Netlist';
  s.('target_info') = 'xlNGCTarget';
