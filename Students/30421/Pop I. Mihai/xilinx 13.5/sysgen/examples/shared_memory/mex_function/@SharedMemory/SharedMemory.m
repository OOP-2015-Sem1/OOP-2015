%
% SharedMemory Objects provide a MATLAB interface to Shared Memory
% address spaces that are typically created through the simulation
% of a Simulink model that contains either shared memory blocks or
% hardware co-simulation blocks that utilize shared memory.
% 
% Assuming that a memory with name "Mmy" has been created
% elsewhere, it can be accessed as follows:
%
% >> M = SharedMemroy('Mmy');
% >> x = M(0)      % note that SharedMemory objects are indexed
%                    from zero
%  x =
%       1
% >> M(1) = x+1;
% >> M(1)
%  ans =
%       2
% >> release(M);   % this step is essential -- it releases our
%                    handle to the resource; the actual memory
%                    cannot be freed until released by all users.


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
%
% Xilinx is providing this design, code, or information "as is" solely
% for use in developing programs and solutions for Xilinx devices.  By
% providing this design, code, or information as one possible
% implementation of this feature, application or standard, Xilinx is
% making no representation that this implementation is free from any
% claims of infringement.  You are responsible for obtaining any rights
% you may require for your implementation.  Xilinx expressly disclaims
% any warranty whatsoever with respect to the adequacy of the
% implementation, including but not limited to warranties of
% merchantability or fitness for a particular purpose.
%
% Xilinx products are not intended for use in life support appliances,
% devices, or systems.  Use in such applications is expressly prohibited.
%
% Any modifications that are made to the source code are done at the
% user's sole risk and will be unsupported.
%
% This copyright and support notice must be retained as part of this
% text at all times.  (c) Copyright 2004 Xilinx, Inc.  All rights
% reserved.
%-------------------------------------------------------------------------


function this = SharedMemory(varargin)

  if (nargin==1 ...
      & isa(varargin{1},'char'))
    [this.name, this.depth, this.width] = xlshmem_mex('construct',varargin{1});
  else
    error ('Invalid SharedMemory constructor invocation.');
  end

  this = class(this, 'SharedMemory');

  return;




