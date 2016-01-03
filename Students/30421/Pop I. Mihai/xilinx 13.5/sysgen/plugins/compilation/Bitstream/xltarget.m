%
% Filename:    xltarget.m 
%
% Description: Defines the target compilation entry point for  
%              the Xilinx tool compilation targets.

function s = xltarget

  s.('name') = 'Bitstream';
  s.('target_info') = 'xltools_target';
