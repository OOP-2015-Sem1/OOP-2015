%
% Filename:    XtremeDSPNonMmPorts.m
%
% Description: Defines the non-memory mapped ports for the
%              for the Xilinx XtremeDSP development kit.
%
function non_mm_ports = XtremeDSPNonMmPorts()
  non_mm_ports = {};
 
  % Define non memory-mapped ports.
  non_mm_ports.('adc1_d') = {'in',14};
  non_mm_ports.('adc2_d') = {'in',14};
  non_mm_ports.('dac1_d') = {'out',14};
  non_mm_ports.('dac2_d') = {'out',14};
  non_mm_ports.('dac1_reset') = {'out',1};
  non_mm_ports.('dac2_reset') = {'out',1};
  non_mm_ports.('dac1_mod0') = {'out',1};
  non_mm_ports.('dac1_mod1') = {'out',1};
  non_mm_ports.('dac2_mod0') = {'out',1};
  non_mm_ports.('dac2_mod1') = {'out',1};
  non_mm_ports.('dac1_div0') = {'out',1};
  non_mm_ports.('dac1_div1') = {'out',1};
  non_mm_ports.('dac2_div0') = {'out',1};
  non_mm_ports.('dac2_div1') = {'out',1};
  non_mm_ports.('led1_red') = {'out',1};
  non_mm_ports.('led1_green') = {'out',1};
  non_mm_ports.('led2_red') = {'out',1};
  non_mm_ports.('led2_green') = {'out',1};
  non_mm_ports.('zbt_addr') = {'out',19};
  non_mm_ports.('zbt_data') = {'out',32};
  non_mm_ports.('zbt_ce1') = {'out',1};
  non_mm_ports.('zbt_ce0') = {'out',1};
  non_mm_ports.('zbt_cen') = {'out',1};
  non_mm_ports.('zbt_adv_ld') = {'out',1};
  non_mm_ports.('zbt_oe') = {'out',1};
  non_mm_ports.('zbt_rd_wr') = {'out',1};
  non_mm_ports.('zbt_data_tri') = {'out', 1};
  non_mm_ports.('zbt_data_read') = {'in',32};
