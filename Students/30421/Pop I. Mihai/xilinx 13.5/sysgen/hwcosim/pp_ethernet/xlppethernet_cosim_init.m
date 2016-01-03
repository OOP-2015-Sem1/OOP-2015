% errmsg = xlPPEthernetCosimCheckParams(gcbh);
% if (isempty(errmsg))
% xlSetMaskValidity(gcbh, true);
% else
% xlSetMaskValidity(gcbh, false, errmsg);
% return;
% end
engineDll = 'PPEthernetCosimEngine';
libDir = xlFindPath(engineDll, 'CosimEngine');
binDir = fileparts(libDir);
try
    tmp_gcbh = gcbh;
    if xlEthernetUtil('supportsysace', get_param(tmp_gcbh, 'cosim_xtable'))
        xlJTAGCableUtil('syncCableParams', tmp_gcbh, 'PPEthernetWithSystemACE');
    else
        xlJTAGCableUtil('syncCableParams', tmp_gcbh, 'PPEthernet');
    end
end
