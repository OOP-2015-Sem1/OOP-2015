%
% Filename   : xltarget.m
%
% Description: Defines the target compilation entry point for the
%              Ethernet co-simulation interface.
%

function s = xltarget
  s.('name') = 'ML402 (Point-to-point Ethernet)';
  s.('target_info') = 'ML402_PPEth_Target';
