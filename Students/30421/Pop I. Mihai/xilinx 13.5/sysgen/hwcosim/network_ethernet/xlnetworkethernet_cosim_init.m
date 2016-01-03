errmsg = xlEthernetCosimCheckParams(gcbh);
if (isempty(errmsg))
xlSetMaskValidity(gcbh, true);
else
xlSetMaskValidity(gcbh, false, errmsg);
return;
end
engineDll = 'NetworkEthernetCosimEngine';
libDir = xlFindPath(engineDll, 'CosimEngine');
binDir = fileparts(libDir);
