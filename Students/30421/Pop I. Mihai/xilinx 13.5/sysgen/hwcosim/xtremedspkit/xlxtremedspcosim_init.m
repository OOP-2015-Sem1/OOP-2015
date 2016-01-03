% assumes the cosim DLL resides in the same
% directory as the library containing the
% cosim block
libDir = which('XtremeDSPRuntimeCosim_r4');
binDir = fileparts(libDir);
% cosim library name goes here
engineDll = 'XtremeDSPCosimEngine.dll';
