% for usage, see: help SharedMemory

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


function this = subsasgn(this, index, val)

  val = round(val);
  switch index(1).type
   case '{}'
    error(['Use () for indexing into SharedMemory proxy objects.']);
   case '.'
    error(['SharedMemory proxy objects contain no fields.']);
   case '()'
    arg = index(1).subs;
     if (length(arg)==1)
       if (isa(arg{1},'double') & prod(size(arg{1})) ==1)
	 addr = arg{1};
       elseif (isa(arg{1},'char') & strcmp(arg{1},':'))
	 addr = 0:this.depth-1;
       else
	 addr = arg{1};
       end
     else
       error(['SharedMemory proxy objects must be indexed as vectors.']);
     end

     % check for legal address indexing
     %
     for i = 1:length(addr)
       if (addr(i)<0 | (addr(i) ~= floor(addr(i))))
	 error('Subscript indices must either be non-negative integers.');
       end
       if (addr(i)>=this.depth)
	 error('Index exceeds memory addresses.');
       end
     end
     
     if (length(val)==1 & length(addr) > 1)
       val = val * ones(length(addr),1);
     end

     if (length(val) ~= length(addr))
       error(['In an assignment "A(I) = B", the number of elements' ...
	      ' in "B" and "I" must be the same.']);
     end
       

     % write data
     %
     for i = 1:length(addr)
       xlshmem_mex('write',this.name,addr(i),val(i));
     end

  end

  return;
    

