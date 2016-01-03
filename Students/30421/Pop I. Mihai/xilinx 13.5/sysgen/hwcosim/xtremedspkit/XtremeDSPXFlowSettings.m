%
% Filename:    XtremeDSPXFlowSettings.m
% 
% Description: Defines the default XFLOW options files for the  
%              XtremeDSP development kit.
%
function status = XtremeDSPXFlowSettings(varargin) 

  % Obtain block handle from function arguments.
  cfg_params = varargin{1};
  
  % Find directory containing XtremeDSP hardware co-sim files.
  [vendor_dir, unused, unused] = ... 
    fileparts(which('XtremeDSPRuntimeCosim_r4'));
  
  % Set default option file names.
  if (~isfield(cfg_params,'xflow_settings'))
    cfg_params.xflow_settings.compoptfile = ...
      fullfile(vendor_dir, 'balanced_xdsp.opt');
    cfg_params.xflow_settings.configoptfile = ...
      fullfile(vendor_dir, 'bitgen_xdsp.opt');
  end
  
  status = xlHwcosimSettings(cfg_params);
