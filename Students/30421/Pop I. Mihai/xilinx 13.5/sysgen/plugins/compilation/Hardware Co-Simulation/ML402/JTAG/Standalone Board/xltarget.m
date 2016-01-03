%
% Filename:    xltarget.m 
%
% Description: Defines the target compilation entry point for the
%              Virtex4 ML402

function s = xltarget
  s.('name') = 'Virtex4 ML402 (JTAG)';
  s.('target_info') = 'virtex4_ml402_target';
  s.('libgen_info') = 'virtex4_ml402_libgen';
  s.('sbd_xml') = 'virtex4_ml402.xml';
