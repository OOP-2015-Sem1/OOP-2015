engineDll = 'JTAGCosimEngine';
libDir = xlFindPath(engineDll, 'CosimEngine');
binDir = fileparts(libDir);
try
    xlJTAGCableUtil('syncCableParams', gcbh, 'JTAG');
end
