function varargout = xfer_function1(action)
% Xfer Fucntion Mask dynamic dialog function for Xfer_Function analysis block

% Copyright 1995-2001 The MathWorks, Inc.
% $Date: 2004/11/22 00:17:13 $ $Revision: 1.1 $
% RAB tweaked from dspblkshorttimefft2. 
blk = gcb;
if nargin==0, action = 'dynamic'; end

switch action
case 'dynamic'
   % Execute dynamic dialogs
   
   % Determine "Inherit FFT length" checkbox setting
   inhFftStr = get_param(blk,'inheritFFT');
   
   % Determine window popup setting
   win = lower(get_param(blk,'wintype'));
   
   % Cache original dialog mask enables
   ena_orig = get_param(blk,'maskenables');
   ena = ena_orig;

   % Determine if FFT length edit box is visible
   iFFTedit = 6; fftEditBoxEnabled = strcmp(inhFftStr, 'off');
   
   % Determine whether Stopband, Beta, and Window Sampling are visible:
   iRipple = 2; isCheby  = strcmp(win,'chebyshev');
   iBeta   = 3; isKaiser = strcmp(win,'kaiser');
   iWSamp  = 4; isGenCos = any(strcmp(win,{'hamming','hann','hanning','blackman'}));
   
   % Map true/false to off/on strings, and place into visibilities array:
   enaopt = {'off','on'};
   ena([iFFTedit iRipple iBeta iWSamp]) = enaopt([fftEditBoxEnabled isCheby isKaiser isGenCos]+1);
   if ~isequal(ena,ena_orig),
      % Only update if a change was really made:
      set_param(blk,'maskenables',ena);
  end

  
case 'init'
    % Make model changes here, in response to mask changes
    
    % Determine "Inherit FFT length" checkbox setting
    inhFftStr = get_param(blk,'inheritFFT');
    
    % Compare 'inherit' checkbox of this block
    % to the setting of the underlying mag-fft block
    %
    % If not the same, push the change through:
    magfftblk       = [blk '/aspec_cspec'];       % RAB
    magfftCheckbox  = get_param(magfftblk,'fftLenInherit');
    changePending   = ~strcmp(inhFftStr, magfftCheckbox);
    
    if changePending,
        % Update the Mag FFT block underneath the top level
        set_param(magfftblk, 'fftLenInherit', inhFftStr);
    end
    
    
case 'icon'
   d = 0.1; xe=4; x=-xe:d:xe;
   y = ones(size(x)); i=find(x); y(i)=sin(pi*x(i))./(pi*x(i));
   y = abs(y).^(0.75);

   varargout = {x,y,xe};
   
   % Update underlying blocks:
   wintype = get_param(blk,'wintype');
   winsamp = get_param(blk,'winsamp');
   set_param([blk '/Window'],'winsamp',winsamp,'wintype',wintype);
end

% [EOF] 
