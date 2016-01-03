%
% Filename:    XtremeDSPWrapperSettings.m
%
% Description: Defines hardware co-simulation wrapper builder
%              settings (e.g. ucf, file, non-memory mapped 
%              ports) for the Xilinx XtremeDSP development kit.
%
function wrapper_settings = XtremeDSPWrapperSettings(params)
 
  % Specify the project and company name for this target. 
  wrapper_settings.('company_name') = 'Nallatech';
  wrapper_settings.('project_name') = 'xdspkit';

  % Define a constraints file name that matches the name of 
  % the target's top-level component.
  wrapper_settings.('ucf_name') = 'benone_top_pci.ucf';

  % Define constraints file template from which ucf is derived.
  wrapper_settings.('ucf_template') = ...
    ['benone_top_pci_' params.part '.ucf'];

  % Define non memory-mapped ports.
  wrapper_settings.('non_memory_mapped_ports') = XtremeDSPNonMmPorts;
