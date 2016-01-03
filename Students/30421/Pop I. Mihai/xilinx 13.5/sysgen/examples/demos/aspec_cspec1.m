function iconstr = aspec_cspec1(action)
%  Mask dynamic dialog function for
%  FFT auto and cross power spectrum estimation block

% Copyright 1995-2001 The MathWorks, Inc.
% $Date: 2004/11/22 00:17:12 $ $Revision: 1.1 $
% RAB, tweaked from dspblkmagfft2

blk = gcb;
if nargin<1, action='dynamic'; end

% Determine "Inherit FFT length" checkbox setting
inhFftStr = get_param(blk,'fftLenInherit');


switch action
case 'init'
    % Make model changes here, in response to mask changes
    % Compare 'inherit' checkbox of this block
    % to the setting of the underlying zero-pad block
    %
    % If not the same, push the change through:
    zpadblk       = [blk '/Zero Pad_a'];
    zpadPopup     = get_param(zpadblk,'zpadAlong');
    changePending = ~(     (strcmp(inhFftStr,'on')  & strcmp(zpadPopup,'None'))    ...
                         | (strcmp(inhFftStr,'off') & strcmp(zpadPopup,'Columns')) ...
                     );
    if changePending,
        % Update the Zero Pad block underneath the top level
        if strcmp(inhFftStr, 'on'), str='None'; else str='Columns'; end
        set_param(zpadblk, 'zpadAlong', str);
        zpadblk       = [blk '/Zero Pad_b'];    % rab
        set_param(zpadblk, 'zpadAlong', str);   % rab
    end

case 'dynamic'
    % Execute dynamic dialogs
    
    % Determine if FFT length edit box is visible
    iFFTedit = 3; fftEditBoxEnabled = strcmp(inhFftStr, 'off');

    % Cache original dialog mask enables
    ena_orig = get_param(blk,'maskenables');
    ena = ena_orig;
    enaopt = {'off','on'};
    ena([iFFTedit]) = enaopt([fftEditBoxEnabled]+1);
    
    % Map true/false to off/on strings, and place into visibilities array:
    if ~isequal(ena,ena_orig),
        % Only update if a change was really made:
        set_param(blk,'maskenables',ena);
    end

case 'update'
  % not needed (RAB)
      
end


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function present = exist_block(sys, name)
    present = ~isempty(find_system(sys,'searchdepth',1,...
        'followlinks','on','lookundermasks','on','name',name));

% [EOF] 