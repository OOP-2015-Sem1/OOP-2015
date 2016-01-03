%
% Filename   : xltarget.m
%
% Description: Defines the target compilation entry point for the
%              Ethernet co-simulation interface.
%

function s = xltarget
  s.('name') = 'ML506 (Point-to-point Ethernet)';
  s.('target_info') = 'ML506_PPEth_Target';
