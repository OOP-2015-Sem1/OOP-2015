
function XtremeDSPCallback()
% Callback routine for XtremeDSP cosimulation
% block and XtremeDSP compilation block.
blk = gcb;

var = get_param(blk,'MaskNames');
vis = get_param(blk,'maskVisibilities');
ens = get_param(blk,'maskEnables');

loc = find(strcmp(var,'clkSrc'));
if (~isempty(loc))
   clk_src = get_param(blk,'clkSrc');
   clk_freq_loc = find(strcmp(var,'prgClkFreq'));
   bus_type_loc = find(strcmp(var,'busType'));
   if strcmp(clk_src, 'Free Running')
      vis(clk_freq_loc) = {'on'};
      ens(bus_type_loc) = {'on'};
   else
      vis(clk_freq_loc) = {'off'};
      ens(bus_type_loc) = {'off'};
      set_param(blk, 'busType','PCI');
   end
end

loc = find(strcmp(var,'part'));
if (~isempty(loc))
   part = get_param(blk,'part');
   if strcmp(part, 'xc2v6000')
      set_param(blk, 'package', 'ff1152'); 
   else
      set_param(blk, 'package', 'fg676');
   end
end

loc = find(strcmp(var,'prtDirs'));
if (~isempty(loc))
   blk_period_loc = find(strcmp(var,'blkPeriod'));
   prt_signal_type_loc = find(strcmp(var,'prtSignalType'));
   
   prt_dirs = get_param(blk, 'prtDirs');
   
   if (isempty(findstr('0',prt_dirs)))
      vis(blk_period_loc) = {'on'};
      vis(prt_signal_type_loc) = {'on'};
   else
      vis(blk_period_loc) = {'off'};
      vis(prt_signal_type_loc) = {'off'};
   end
end

set_param(blk,'maskEnables',ens);
set_param(blk,'maskVisibilities',vis);
