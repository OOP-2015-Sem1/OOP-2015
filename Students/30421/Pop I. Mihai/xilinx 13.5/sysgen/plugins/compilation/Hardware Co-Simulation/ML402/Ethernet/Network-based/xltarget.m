%
% Filename   : xltarget.m
%
% Description: Defines the target compilation entry point for the
%              Ethernet co-simulation interface.
%

function s = xltarget
  s.('name') = 'ML402 (Network-based Ethernet)';
  s.('target_info') = 'ML402_NetEth_Target';
