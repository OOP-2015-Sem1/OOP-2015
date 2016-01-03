function str = get_pipestr_r13(pipestr, pos, extendFlag)
% GET_PIPESTR(PIPESTR, POS) returns the POS'th string from pipe-delimited
%   string PIPESTR.  If POS exceeds the number of strings in the PIPESTR,
%   an error is returned.
%
% GET_PIPESTR(PIPESTR, POS, 1) returns an empty string if POS exceeds the
%   number of strings in PIPESTR.

% Copyright 1995-2002 The MathWorks, Inc.
% $Revision: 1.1 $  $Date: 2004/11/22 00:17:12 $

error(nargchk(2,3,nargin));
if nargin<3, extendFlag=0; end

if ~isstr(pipestr),
   error('Input must be a string.');
end
if pos<1,
   error('Invalid index specified.');
end

c = cellpipe_r13(pipestr);

if pos > length(c),
   if ~extendFlag,
      error('Index exceeds number of entries in pipe-delimited string.');
   end
   str='';
else
   str=c{pos};
end
