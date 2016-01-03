function y = syslocked_r13(sys)
% SYSLOCKED Determine whether a system is locked.
%  This function is error-protected against calls on
%  subsystems or blocks, which do not have a lock parameter.


% Copyright 1995-2002 The MathWorks, Inc.
% $Revision: 1.1 $ $Date: 2004/11/22 00:17:13 $

y = ~isempty(sys);
if y,
   y = strcmpi(get_param(bdroot(sys),'lock'),'on');
end

% [EOF] syslocked.m