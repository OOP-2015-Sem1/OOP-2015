%
% Filename   : xltarget.m
%
% Description: Defines the target compilation entry point for the
%              Ethernet co-simulation interface.
%

function s = xltarget
  s.('name') = 'Spartan-3A DSP 1800A Starter Platform (Point-to-point Ethernet)';
  s.('target_info') = 'S3ADSP_SK_PPEth_Target';
