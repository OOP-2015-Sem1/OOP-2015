%
% Filename:    xltarget.m
%
% Description: Defines the target compilation entry points

function s = xltarget
  s = {
     struct('name', 'KC705 (JTAG)', ...
            'target_info', 'xlHwcosimTarget(''kc705-jtag'')', ...
            'title', 'JTAG'), ...
  };
