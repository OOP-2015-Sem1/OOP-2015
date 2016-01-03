%
% Filename:    xltarget.m 
%
% Description: Defines the target compilation entry point for  
%              the Xilinx tool compilation targets.

function s = xltarget

  s = {};

  target_1.('name') = 'Standalone Bitstream';
  target_1.('target_info') = 'xltools_target';
 
  target_2.('name') = 'iMPACT';
  target_2.('target_info') = 'xltools_target';

  target_3.('name') = 'ChipScope Pro Analyzer';
  target_3.('target_info') = 'xltools_target';

  s = {target_1, target_2, target_3};   
