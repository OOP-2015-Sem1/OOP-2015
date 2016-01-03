
% A Matlab function that invokes "mex" with the appropriate
% arguments to build the mex function (dll) that is used by this
% example. To successfully build, you will need to have Microsoft's
% Visual C++ installed. The example has only been tested with the
% Visual C++ compiler that is supplied with Microsoft's Developer
% Studio, Professional, 2003.

%-------------------------------------------------------------------------
% System Generator source file.
%
% Copyright(C) 2004 by Xilinx, Inc.  All rights reserved.  This
% text/file contains proprietary, confidential information of Xilinx,
% Inc., is distributed under license from Xilinx, Inc., and may be used,
% copied and/or disclosed only pursuant to the terms of a valid license
% agreement with Xilinx, Inc.  Xilinx hereby grants you a license to use
% this text/file solely for design, simulation, implementation and
% creation of design files limited to Xilinx devices or technologies.
% Use with non-Xilinx devices or technologies is expressly prohibited
% and immediately terminates your license unless covered by a separate
% agreement.
%-------------------------------------------------------------------------

function build_example()

  src = 'xlshmem_mex.cpp';

  sysgen_root = [fileparts(fileparts(which('xlmeta')))];
  sysgen_include_dir = [sysgen_root filesep 'include'];
  sysgen_lib_dir = [sysgen_root filesep 'lib'];

  dash_I = ['-I' sysgen_include_dir];

  comp_flags = 'COMPFLAGS#"$COMPFLAGS -GX" -DWIN32 -DSG_STATIC';

  link_flags = ['LINKFLAGS#"$LINKFLAGS '];
  link_flags = [link_flags sprintf('-LIBPATH:%s ', sysgen_lib_dir)];
  link_flags = [link_flags '-DELAYLOAD:sysgen.dll '];
  link_flags = [link_flags 'sysgen.lib DelayImp.lib'];
  link_flags = [link_flags '"'];

  mex_command = ['mex '  comp_flags ' ' dash_I ' ' link_flags ' ' src];

  fprintf('\n Calling mex as:\n   %s\n\n',mex_command);
  eval(mex_command);
