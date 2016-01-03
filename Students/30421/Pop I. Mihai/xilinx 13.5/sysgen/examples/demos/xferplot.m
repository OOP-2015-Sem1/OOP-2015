function  [sys, x0, str, ts] = xferplot(t,x,u,varargin)
%XFERPLOT Frame- and vector-display block for the DSP Blockset.
%  The DSP Blockset provides several scopes useful for visualizing
%  frame- or vector-based signals in a Simulink model.
%
%  The following blocks in the "DSP Sinks" library utilize this
%  scope display:
%    - Vector Scope
%    - FFT Frame Scope
%    - Buffered FFT Frame Scope
%
%  These blocks accept 2-D signals, where an MxN input matrix is
%  interpreted as N channels of data, each presenting M consecutive
%  time samples of data.  A 1-D signal is interpreted as a single
%  channel of data.
%
%  Most menu options are duplicated in a context menu, accessible
%  by right-clicking within the scope display.  Depending upon the
%  scope block, and the number of data channels entering the block,
%  different options are provided.
%
%  See also DSPLIB.

%  This is an M-file S-Function used by the DSP Blockset that
%  implements a frame- or vector-based scope display.

% Copyright 1995-2001 The MathWorks, Inc.
% $Revision: 1.1 $  $Date: 2004/11/22 00:17:13 $
% RAB, tweaked for transfer function 


% Syntax:
%
%   xferplot(t,x,u,flag,params);
%
% where params is a structure containing all block dialog parameters.
%
% What's in the Figure userdata:
% ------------------------------
% Main scope figure handles:
%   fig_data.block        block name
%   fig_data.hfig         handle to figure
%   fig_data.hcspec       handle to non-displayed line for color translation
%
%   fig_data.main.haxis         handle to axes
%   fig_data.main.hline         Nx1 vector of line handles
%   fig_data.main.hstem         scalar stem line handle
%   fig_data.main.hgrid         vector of handles to axis grid lines
%   fig_data.main.axiszoom.on   P/V cell-array pairs to turn on
%   fig_data.main.axiszoom.off  and off full-axis zoom
%
% Handles to menu items:
%   - appearing only in figure menu:
%       fig_data.menu.recpos      record position
%       fig_data.menu.axislegend  (checkable)
%       fig_data.menu.framenumber (checkable)
%       fig_data.menu.axisgrid    (checkable)
%       fig_data.menu.memory      (checkable)
%       fig_data.menu.refresh
%
%   - appearing in both figure and context menu:
%       fig_data.menu.top         top-level Axes and Lines in Figure
%       fig_data.menu.context     context menu
%       fig_data.menu.linestyle    2xN, [fig;context] x [one per display line]
%       fig_data.menu.linemarker   2xN        (children are individual submenu options)
%       fig_data.menu.linecolor    2xN
%       fig_data.menu.linedisable  2x1
%       fig_data.menu.axiszoom     2x1, [fig;context] (checkable)
%       fig_data.menu.autoscale
%
%
% What's in the Block userdata:
% -----------------------------
%   block_data.firstcall    flag for first call to function
%   block_data.autoscaling  indicates autoscale computation in progress
%   block_data.hfig         handle to figure
%   block_data.hcspec       handle to non-displayed line for color translation
%   block_data.haxis        handle to axes
%   block_data.hline        Nx1 vector of line handles
%   block_data.hstem        scalar line handle
%   block_data.hgrid        handles to axis grid lines
%   block_data.hgridtext    vector of handles
%   block_data.hlegend      handle to legend itself
%   block_data.hframenum    handle to frame number text indicator
%   block_data.params       structure of cached block dialog parameters
%   block_data.Ts           updated sample time for block
%   block_data.inputDims    dimension vector of input to scope
%   block_data.NChans       Number of frames (columns) in input matrix
%
%
%
% Parameters structure fields:
% ----------------------------
% .ScopeParams
% .Domain: 1=Time, 2=Frequency, 3=User Defined
% .XLabel:
%     Time, Frequency: ignored
%     User: displayed
% .XUnits:
%     User, Time: ignored
%     Freq: 1=Hz, 2=rad/s
% .InheritXIncr: checkbox
% .XIncr: increment of x-axis samples, used for x-axis display
%     Time: ignored (assumes frame-based)
%     User, Freq: seconds per sample
% .XRange:
%     User, Time: ignored
%     Freq: 1=[0,Fn] , 2=[-Fn,Fn], 3=[0, Fs]
%                (Fn=Nyquist rate, Fs=Sample rate)
% .YLabel:
% .YUnits:
%      User, Time: ignored
%      Freq: 1=Magnitude, 2=dB
%
% .HorizSpan: Horizontal time span (number of frames)
%             Only displayed for Time and User-defined
%
% Optionally displayed in dialog:
% .AxisParams: indicates whether the Axis Settings are
%              currently displayed in block dialog.
% .YMin: Minimum y-limit
% .YMax: Maximum y-limit
% .FigPos: figure position
%
% .AxisGrid:   Current setting, on or off
% .AxisZoom:    similar
% .FrameNumber: similar
% .AxisLegend:  similar
% .Memory: checkbox
%
% .LineParams:
% .LineDisables:
% .LineColors: pipe-delimited string of colors, one per channel
% .LineStyles: similar
% .LineMarkers: similar
%
% .WindowParams: indicates whether the Scope Settings are
%                currently displayed in block dialog.
% .OpenScopeAtSimStart: checkbox
% .OpenScopeImmediately: checkbox

switch(varargin{1})
case 3,
	sys = [];   % mdlOutput - unused
case 2,
	sys = mdlUpdate(t,x,u,varargin{:});
case 0,
	[sys,x0,str,ts] = mdlInitializeSizes;
case 9,
	sys = mdlTerminate;
otherwise
	% GUI callback:
	feval(varargin{:});
	sys = [];   % None of these options change the state:
end


% -----------------------------------------------------------
function [sys,x0,str,ts] = mdlInitializeSizes

sfcn   = gcb;  % s-fcn
blk    = get_param(sfcn,'parent');
parent = get_param(blk, 'parent');

sizes = simsizes;
sizes.NumInputs      = -1;
sizes.NumOutputs     =  0;
sizes.NumSampleTimes =  1;
sys = simsizes(sizes);
x0  = [];           % states
str = '';           % state ordering strings
ts  = [-1 1];       % inherited sample time, fixed in minor steps

%%% if ~syslocked(parent),
	% No need to execute in a library
	%  (we also want to keep the block UserData empty so that
	%   DialogApply will not run)
%%%	resetToFirstCall(blk);
%%% end


% ------------------------------------------------------------
function sys = mdlUpdate(t,x,u,flag,params)

% Faster implementation of: blk=gcb;
cs   = get_param(0,'CurrentSystem');
cb   = get_param(cs,'CurrentBlock');
sfcn = [cs '/' cb];
blk  = get_param(sfcn,'parent');
sys  = [];
block_data = get_param(blk, 'UserData');
if block_data.firstcall,
	first_update(blk, sfcn, u, params);
else
	update_lines(block_data, u);
end


% ---------------------------------------------------------------
function update_lines(block_data, u)
% UPDATE_LINES Update the lines in the scope display

% u: one frame of data
% Does not alter block_data

% If the user closed the figure window while the simulation
% was running, then hfig has been reset to empty.
%
% Allow the simulation to continue, do not put up a new figure
% window, and do not generate an error if window closes early.
%
if isempty(block_data.hfig), return; end

% Call update_lines function:
feval(block_data.UpdateLinesFcn, block_data, u);

% Update frame number display:
% Always update - it is invisible unless feature is enabled
%
% UpdateFramenum(block_data);
d = get(block_data.hframenum(2),'userdata');
set(block_data.hframenum(2), 'userdata', d+1, ...
	'string', sprintf('%d',d+1));

% Check if autoscaling is in progress:
if ~isempty(block_data.autoscaling),
	Autoscale([],[], block_data, u);  % next frame of data
end


% ---------------------------------------------------------------
function fcn = select_UpdateLinesFcn(block_data)

freqDomain = (block_data.params.Domain==2);
oneFrame   = (block_data.params.HorizSpan==1);
oneChan    = (block_data.NChans==1);

% Use the frame counter to determine where we are:
% Counter starts at 0, since it is incremented after
% update_lines occurs.
nframes = block_data.params.HorizSpan;
d = get(block_data.hframenum(2),'userdata');
useInit = (d < nframes);

if freqDomain,
    % Frequency domain:
	if oneChan,
		fcn = @update_lines_freq_oneframe_onechan;
	else
		fcn = @update_lines_freq_oneframe_multichan;
	end
else
	% Time domain:
	if oneFrame,
		if oneChan,
			fcn = @update_lines_time_oneframe_onechan;
		else
			fcn = @update_lines_time_oneframe_multichan;
		end
	else
		if oneChan,
            if useInit,
                fcn = @init_lines_time_multiframe_onechan;
            else
                fcn = @update_lines_time_multiframe_onechan;
            end
		else
            if useInit,
                fcn = @init_lines_time_multiframe_multichan;
            else
                fcn = @update_lines_time_multiframe_multichan;
            end
		end
	end
end


% ---------------------------------------------------------------
function update_lines_freq_oneframe_onechan(block_data, u)

% Single channel
% One frame per horizontal span

[nrows,nchans] = size(u);

% Frequency domain conversions
% Convert to dB if required:
if(block_data.params.YUnits==2),
	u = lin2dB(u);
end

% Rotate data if display range is [-Fn, Fn]:
if (block_data.params.XRange==2),
	% rotate each channel of data:
	p = nrows./2;  % all FFT's are a power of 2 here
	u = u([p+1:nrows 1:p],:);
end

set(block_data.hline, 'YData', u);

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	ystem = get(block_data.hstem,'ydata');
	ystem((0:nrows-1)*3 + 2) = u;
	set(block_data.hstem,'ydata',ystem);
end


% ---------------------------------------------------------------
function update_lines_freq_oneframe_multichan(block_data, u)

% One frame per horizontal span
% Multiple channels (matrix input)

[nrows,nchans] = size(u);

% Frequency domain conversions
% Convert to dB if required:
if(block_data.params.YUnits==2),
	u = lin2dB(u);
end

% Rotate data if display range is [-Fn, Fn]:
if (block_data.params.XRange==2),
	% rotate each channel of data:
	p = nrows./2;  % all FFT's are a power of 2 here
	u = u([p+1:nrows 1:p],:);
end

hline = block_data.hline;

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	ystem = get(block_data.hstem,'ydata');
	kup = (0:length(u)-1)*3 + 2;  % index to top of stems
	kdn = kup-1;                  % index to bottom of stems
	ystem(kup) = 0;
	ystem(kdn) = 0;
	markerpipestr = block_data.params.LineMarkers;
	
	for i = 1:nchans, % block_data.NChans
		val = u(:,i)';
		set(hline(i), 'YData', val);
		
		if strcmp(get_pipestr(markerpipestr, i,1), 'stem'),
			ystem(kup) = max(ystem(kup), val);
			ystem(kdn) = min(ystem(kdn), val);
		end
	end
	set(block_data.hstem,'ydata',ystem);
	
else
	for i = 1:nchans, % block_data.NChans
		set(hline(i), 'YData', u(:,i));
	end
end


% ---------------------------------------------------------------
function update_lines_time_oneframe_onechan(block_data, u)

% One frame per horizontal span
% Single channel

set(block_data.hline, 'YData', u);

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	[nrows,nchans] = size(u);
	ystem = get(block_data.hstem,'ydata');
	ystem((0:nrows-1)*3 + 2) = u;
	set(block_data.hstem,'ydata',ystem);
end


% ---------------------------------------------------------------
function update_lines_time_oneframe_multichan(block_data, u)

% One frame per horizontal span
% Multiple channels (matrix input):

[nrows,nchans] = size(u);
hline = block_data.hline;

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	ystem = get(block_data.hstem,'ydata');
	kup = (0:length(u)-1)*3 + 2;  % index to top of stems
	kdn = kup-1;                  % index to bottom of stems
	ystem(kup) = 0;
	ystem(kdn) = 0;
	markerpipestr = block_data.params.LineMarkers;
	
	for i = 1:nchans, % block_data.NChans
		val = u(:,i)';
		set(hline(i), 'YData', val);
		
		if strcmp(get_pipestr(markerpipestr, i,1), 'stem'),
			ystem(kup) = max(ystem(kup), val);
			ystem(kdn) = min(ystem(kdn), val);
		end
	end
	set(block_data.hstem,'ydata',ystem);
else
	for i = 1:nchans, % block_data.NChans
		set(hline(i), 'YData', u(:,i));
	end
end


% ---------------------------------------------------------------
function init_lines_time_multiframe_onechan(block_data, u)

% differs from update_lines_time_multiframe_onechan in that
% this begins rendering data from the LEFT side of the display.
% Used at the start (or re-start) of a simulation, and
% is only used for nframes number of input frames.  After that,
% the non-init version is installed and used thereafter.

% Multiple frames per horiz span
% Single channel

[nrows,nchans] = size(u);
nframes = block_data.params.HorizSpan;

% Use the frame counter to determine where we are:
% Counter starts at 0, since it is incremented after
% update_lines occurs.
d = get(block_data.hframenum(2),'userdata');
y = get(block_data.hline,'YData')';
y(d*nrows+1 : (d+1)*nrows, :) = u;
set(block_data.hline, 'YData', y);

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	ystem = get(block_data.hstem,'ydata');
	ystem( 3*nrows*d + (0:length(u)-1)*3 + 2  ) = u;
	set(block_data.hstem,'ydata',ystem);
end

if (d >= nframes-1),
    % install non-init update fcn:
    % Cache the appropriate line-update function:
    block_data.UpdateLinesFcn = @update_lines_time_multiframe_onechan;
    ud=get(block_data.hfig,'userdata');
    set_param(ud.block,'userdata',block_data);
end


% ---------------------------------------------------------------
function update_lines_time_multiframe_onechan(block_data, u)

% Multiple frames per horiz span
% Single channel

[nrows,nchans] = size(u);
nframes = block_data.params.HorizSpan;

y = get(block_data.hline,'YData')';
y(1 : nrows*(nframes-1)) = y(nrows+1:nrows*nframes);
y(nrows*(nframes-1)+1 : end) = u;
set(block_data.hline, 'YData', y);

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	ystem = get(block_data.hstem,'ydata');
	ystem(1 : 3*nrows*(nframes-1)) = ystem(3*nrows+1:3*nrows*nframes);        
	ystem( 3*nrows*(nframes-1) + (0:length(u)-1)*3 + 2  ) = u;
	set(block_data.hstem,'ydata',ystem);
end


% ---------------------------------------------------------------
function init_lines_time_multiframe_multichan(block_data, u)

% Multiple frames per horiz span
% Multiple channels, multiple frames (matrix input)

[nrows,nchans] = size(u);
nframes = block_data.params.HorizSpan;
hline   = block_data.hline;

% Use the frame counter to determine where we are:
% Counter starts at 0, since it is incremented after
% update_lines occurs.
d = get(block_data.hframenum(2),'userdata');
if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	
	% ystem contains "triplets" of data
	% [y1top y1bottom NaN y2top y2bottom NaN ...]
	ystem = get(block_data.hstem,'ydata');
	
	% k = indices of (d+1)th frame
	k = nrows*d+1 : nrows*(d+1);
	k = (k-1)*3+2;
	kdn = k-1;
	ystem(k) = 0;
	ystem(kdn) = 0;
	markerpipestr = block_data.params.LineMarkers;
	
	for i = 1:nchans,
		y = get(hline(i),'YData');
		val = u(:,i)';
        y(d*nrows+1 : (d+1)*nrows) = val;
		set(hline(i), 'YData', y);
		
		if strcmp(get_pipestr(markerpipestr, i,1), 'stem'),
			ystem(k)   = max(ystem(k), val);
			ystem(kdn) = min(ystem(kdn), val);
		end
	end
	set(block_data.hstem,'ydata',ystem);
	
else
	for i = 1:nchans,
		y = get(hline(i),'YData')';
		y(d*nrows+1 : (d+1)*nrows, :) = u(:,i);
		set(hline(i), 'YData', y);
	end
end

if (d >= nframes-1),
    % install non-init update fcn:
    % Cache the appropriate line-update function:
    block_data.UpdateLinesFcn = @update_lines_time_multiframe_multichan;
    ud=get(block_data.hfig,'userdata');
    set_param(ud.block,'userdata',block_data);
end


% ---------------------------------------------------------------
function update_lines_time_multiframe_multichan(block_data, u)

% Multiple frames per horiz span
% Multiple channels, multiple frames (matrix input)

[nrows,nchans] = size(u);
nframes = block_data.params.HorizSpan;
hline   = block_data.hline;

if strcmp(get(block_data.hstem,'vis'),'on'),
	% using stem plots:
	
	% ystem contains "triplets" of data
	% [y1top y1bottom NaN y2top y2bottom NaN ...]
	ystem = get(block_data.hstem,'ydata');
	
	% copy stem values from last (span-1) frames to first (span-1) frames
	ystem(1 : 3*nrows*(nframes-1)) = ystem(3*nrows+1:end);
	
	% k = indices of last frame
	k = nrows*(nframes-1)+1 : nrows*nframes;
	k = (k-1)*3+2;
	kdn = k-1;
	ystem(k) = 0;
	ystem(kdn) = 0;
	markerpipestr = block_data.params.LineMarkers;
	
	for i = 1:nchans,
		y = get(hline(i),'YData');
		y(1 : nrows*(nframes-1)) = y(nrows+1:end);
		val = u(:,i)';
		y(nrows*(nframes-1)+1 : end) = val;
		set(hline(i), 'YData', y);
		
		if strcmp(get_pipestr(markerpipestr, i,1), 'stem'),
			ystem(k)   = max(ystem(k), val);
			ystem(kdn) = min(ystem(kdn), val);
		end
	end
	set(block_data.hstem,'ydata',ystem);
	
else
	for i = 1:nchans,
		y = get(hline(i),'YData');
		y(1 : nrows*(nframes-1)) = y(nrows+1:end);
		y(nrows*(nframes-1)+1 : end) = u(:,i);
		set(hline(i), 'YData', y);
	end
end


% ---------------------------------------------------------------
function OpenScope(blk)
% Open the scope in response to the 'OpenScopeImmediately' dialog
% checkbox being pressed.

% If scope is already open, bring it forward and return
%
block_data = get_param(blk,'UserData');
if ~isfield(block_data,'hfig'), return; end

if ~isempty(block_data.hfig), 
	% Scope exists
	% Two options:
	% 1 - Simply bring existing scope forward and return:
	% figure(block_data.hfig);
	% 2 - do nothing
	return; 
end

% Need to open/re-open scope:
status    =  get_param(bdroot(blk),'simulationstatus');
isRunning = ~strcmp(status,'stopped');
if isRunning,
	openScopeWhileRunning(blk);
end


% ---------------------------------------------------------------
function openScopeWhileRunning(blk)

% Respond to user request to open the scope
% (eg, user pressed button in block dialog)
%
% We must open GUI; thus, we ignore "OpenScopeAtSimStart" setting in dialog
% (pass flag=1 to force GUI to open)
resetToFirstCall(blk,1);

% nothing else to do
% scope will re-open at next time step


% ---------------------------------------------------------------
function resetToFirstCall(blk, mustOpen)
% Sets up block and GUI prior to opening the scope
%
% Resets hfig handle if not present
% Sets firstcall flag to either open the gui on the next
%   time step, or keep it closed, according to the block
%   parameter "OpenScopeAtSimStart".
%
% If mustOpen is passed in, the decision to open a GUI
% will come from mustOpen instead of the dialog param.

block_data = get_param(blk,'UserData');

% Determine if scope should open at next time step:
if nargin>1,
	block_data.firstcall = mustOpen;
else
	% NOTE: This is called before params are set into block_data
	block_data.firstcall = isOn(get_param(blk,'OpenScopeAtSimStart'));
	% block_data.firstcall = strcmp(block_data.params.OpenScopeAtSimStart,'on');
end

% Setup empty figure handle if handle not recorded:
if ~isfield(block_data,'hfig'),
	block_data.hfig = [];
end
set_param(blk,'UserData',block_data);


% ---------------------------------------------------------------
function startLineEraseMode(blk)
% Set channel lines to proper erase mode at start of simulation.

% The lines are set to 'normal' mode when a simulation terminates;
% when lines redraw over themselves in "xor" mode, dots are left at
% peaks without lines connecting to them.  This can be visually misleading.

block_data = get_param(blk,'UserData');

if isOn(block_data.params.Memory),
	emode='none';    % Memory mode
else
	emode='xor';
end
set([block_data.hline(:) ; block_data.hstem(:)], 'EraseMode',emode);
set(block_data.hframenum, 'EraseMode','xor');


% ---------------------------------------------------------------
function terminateLineEraseMode(blk)
% Set channel line erase mode at simulation termination

block_data = get_param(blk,'UserData');

% Skip if HG window is closed:
if isempty(block_data.hfig),
	return;
end

if isOn(block_data.params.Memory),
	emode='none';    % Memory mode
else
	emode='normal';
end
set([block_data.hline(:) ; block_data.hstem(:)], 'EraseMode',emode);
set(block_data.hframenum, 'EraseMode','normal');


% ---------------------------------------------------------------
function first_update(blk, sfcn_blk, u, params)
% FIRST_UPDATE Called the first time the update function is executed
%   in a simulation run.  Creates a new scope GUI if it does not exist,
%   or restarts an existing scope GUI.

% blk: masked subsystem block
% sfcn_blk: name of s-function block under the masked subsystem
% u: one frame of data
% Updates block_data

block_data = get_param(blk,'UserData');

% Get dimensions of input port of scope block:
% Particularly useful for the Spectrum Scope, where 
% the internal buffering and FFT blocks may change
% the signal width without our knowledge:
%
inBlk = [blk '/In1'];
dimsVect = get_param(inBlk,'CompiledPortDimensions');
block_data.inputDims  = dimsVect.Outport(2:end);

% Record input frame time to the input port of the subsystem
% (and not the S-fcn itself!)
%
% NOTE: This is the per-sample time, NOT the per-frame time
%
ts = get_param(inBlk,'CompiledSampleTime');
block_data.Ts = ts(1) ./ block_data.inputDims(1); % ignore sample offset time

% Check sample time:
% - disallow continuous signals
% - allow fixed- and variable-step discrete
if ts(1)==0,
	error('Continuous-time inputs are not supported.');
end

% Check input data complexity and type:
if ~isreal(u) | ~isa(u,'double'),
	error('Inputs must be real, double-precision values.');
end

% Construct new scope figure window, or bring up old one:
if isfield(block_data,'hfig'),
	hfig = block_data.hfig; % scope already exists
else
	hfig = [];              % scope was never run before
end

if isfield(block_data,'NChans') & (block_data.NChans ~= size(u,2)),
	% Close the figure if the # of channels has changed
	% The GUI may need to be reconfigured significantly for
	% a change in channel count.
	CloseFigure([],[],blk);
	hfig=[];
end

[block_data.samples_per_frame, block_data.NChans] = size(u);
set_param(blk, 'UserData', block_data);

% Establish a valid scope GUI:
if ~isempty(hfig),
	% Found existing scope figure window:
	
	% Prepare to re-start with existing scope window:
	fig_data = restart_scope(blk, params);
	
	% If things did not go well during restart, say the axis
	% was somehow deleted from the existing scope figure, etc.,
	% then hfig is left empty, and a new scope must be created.
	% Get hfig, then check if it is empty later:
	hfig = fig_data.hfig;
end

if isempty(hfig),
	% Initialize new figure window:
	% Create the scope GUI
	fig_data = create_scope(blk, params, block_data.NChans);
end

% Get line handle:
hline = fig_data.main.hline;
hstem = fig_data.main.hstem;
hgrid = fig_data.main.hgrid;

% Retain the name of the figure window for use when the
% block's name changes. Name is retained in S-fcn block's
% user-data:
block_data.firstcall   = 0;   % reset "new simulation" flag
block_data.hfig      = fig_data.hfig;
block_data.params    = params;

block_data.hcspec    = fig_data.hcspec;
block_data.haxis     = fig_data.main.haxis;
block_data.hline     = hline;
block_data.hstem     = hstem;
block_data.hgrid     = hgrid;
block_data.hframenum = fig_data.main.hframenum;
block_data.autoscaling = []; % turn off any autoscaling, if in progress

if ~isfield(block_data,'hgridtext'),
	block_data.hgridtext = [];   % only exists in block_data, not fig_data
end
if ~isfield(block_data,'hlegend'),
	block_data.hlegend   = [];   % ditto
end

% Cache the appropriate line-update function:
block_data.UpdateLinesFcn = select_UpdateLinesFcn(block_data);

% Set block's user data:
set_param(blk, 'UserData', block_data);


% The following block callbacks are assumed to be set
% in the library block:
%
%   CopyFcn		      "sdspfscope2([],[],[],'BlockCopy');"
%   DeleteFcn		  "sdspfscope2([],[],[],'BlockDelete');"
%   NameChangeFcn     "sdspfscope2([],[],[],'NameChange');"

SetMenuChecks(blk);  % update state of menu items
setup_axes(blk);  % send one frame of data for axis setup
update_lines(block_data, u);  % one frame of data


% ---------------------------------------------------------------
function h = getDisableMenuHandles(blk, lineNum)

block_data = get_param(blk,'UserData');
hfig = block_data.hfig;
fig_data = get(hfig,'UserData');

h = fig_data.menu.linedisable(:,lineNum);


% ---------------------------------------------------------------
function h = getMarkerMenuHandlesFromMarker(blk, lineNum, marker)

% If marker is empty, we won't find any match
% Just return a quick empty:
if isempty(marker),
	h=[]; return;
end

block_data = get_param(blk,'UserData');
hfig = block_data.hfig;
fig_data = get(hfig,'UserData');

% Get handles to just one of the options menu line style items.
% The context (and other line #) menus simply contain redundant info.
% 
hmenus  = fig_data.menu.linemarker;        % [options;context] x [line1 line2 ...]
hmarkers = get(hmenus(:,lineNum),'child');  % marker menu items for lineNum menu, options/context
hmarkers = cat(2,hmarkers{:});               % matrix of handles: markers x [options context]
menuMarkers = get(hmarkers(:,1),'UserData'); % cell array of marker strings just for options menu

h = [];  % in case no match is found
for i=1:size(hmarkers,1),
	if isequal(marker, menuMarkers{i}),
		% Found a matching marker entry
		% Return both Options and Context menu handles for
		%   corresponding style entry for line number lineNum
		h = hmarkers(i,:);
		return;
	end
end


% ---------------------------------------------------------------
function h = getStyleMenuHandlesFromStyle(blk, lineNum, style)

% If style is empty, we won't find any match
% Just return a quick empty:
if isempty(style),
	h=[]; return;
end

block_data = get_param(blk,'UserData');
hfig = block_data.hfig;
fig_data = get(hfig,'UserData');

% Get handles to just one of the options menu line style items.
% The context (and other line #) menus simply contain redundant info.
% 
hmenus  = fig_data.menu.linestyle;         % [options;context] x [line1 line2 ...]
hstyles = get(hmenus(:,lineNum),'child');  % style menu items for lineNum menu, options/context
hstyles = cat(2,hstyles{:});               % matrix of handles: styles x [options context]
menuStyles = get(hstyles(:,1),'UserData'); % cell array of style strings just for options menu

h = [];  % in case no match is found
for i=1:size(hstyles,1),
	if isequal(style, menuStyles{i}),
		% Found a matching style entry
		% Return both Options and Context menu handles for
		%   corresponding style entry for line number lineNum
		h = hstyles(i,:);
		return;
	end
end


% ---------------------------------------------------------------
function h = getColorMenuHandlesFromRGB(blk, lineNum, rgb)
% Maps an RGB color spec to a color menu index.
% The userdata fields of color menu objects contain RGB specs.
% Returns an empty handle vector if no match is found.

% If RGB is empty, we won't find any match
% Just return a quick empty:
if isempty(rgb),
	h=[]; return;
end

block_data = get_param(blk,'UserData');
hfig = block_data.hfig;
fig_data = get(hfig,'UserData');

% Get handles to just one of the options menu line color items.
% The context (and other line #) menus simply contain redundant info.
% 
hmenus  = fig_data.menu.linecolor;    % [options;context] x [line1 line2 ...]
hclrs   = get(hmenus(:,lineNum),'child'); % color menu items for lineNum menu, options/context
hclrs   = cat(2,hclrs{:});            % matrix of handles: colors x [options context]
menuRGB = get(hclrs(:,1),'UserData'); % cell array of RGB vectors just for options menu

h = [];  % in case no match is found
for i=1:size(hclrs,1),
	if isequal(rgb, menuRGB{i}),
		% Found a matching RGB entry
		% Return both Options and Context menu handles for
		%   corresponding color entry for line number lineNum
		h = hclrs(i,:);
		return;
	end
end

% ---------------------------------------------------------------
function rgb = mapCSpecToRGB(blk, user_cspec)
% Maps a user-defined color spec (CSpec) to an RGB triple
% An empty string maps to black (so unspecified lines are simply black)
% User-define color spec can be 'r' or 'red' or [1 0 0], etc.

% If user-spec is an empty, it is mapped to black
if isempty(user_cspec),
	rgb=[0 0 0];  % black
	return;
end

% If user-spec is an RGB triple encoded as a string,
% convert to numeric and return:
rgb = str2num(user_cspec);
if ~isempty(rgb),
	return;
end

% User spec is not an RGB triple.
% As a favor to the user, remove any apostrophes from the spec.
% The user might have accidentally entered:
%   'c'|'y'  (for example)
% instead of
%    c|y     (which is what is required since this is a 'literal' edit box)
%
% If any apostrophes were detected, remove them:
i = find(user_cspec == '''');
if ~isempty(i),
	user_cspec(i)=''; % remove apostrophes
	%warning('Channel color specs are literal strings - do not use apostrophes.');
end


% If user-defined color spec is invalid, return an empty
block_data = get_param(blk,'UserData');
hcspec = block_data.hcspec;
try
	set(hcspec,'color',user_cspec);
	rgb = get(hcspec,'color');
catch
	warning('Invalid line color specified.');
	rgb = zeros(0,3);  % empty RGB spec
end


% ---------------------------------------------------------------
function [rgb,h] = getDialogLineColor(blk,lineNum)
% Determine RGB vector corresponding to user-specified color.
%  - If user-specified color is empty, black is substituted.
%  - If user-specified color is not found, RGB is set to empty.
%
% Optionally returns vector of 2 handles, H, for corresponding
%   line color menu items in the Options and Context menus.

pipestr = get_param(blk,'LineColors');        % get all user-specified color specs
user_cspec = get_pipestr(pipestr,lineNum,1);  % cspec for line lineNum
rgb = mapCSpecToRGB(blk, user_cspec);         % find RGB representation - empty if no match
if nargout>1,
	h = getColorMenuHandlesFromRGB(blk, lineNum, rgb); % get handles - may be empty
end


% ---------------------------------------------------------------
function [style,h] = getDialogLineStyle(blk,lineNum)
% Determine style string corresponding to user-specified color.
%  - If user-specified style is empty, solid is substituted.
%  - If user-specified style is not found, style is set to empty.
%
% Optionally returns vector of 2 handles, H, for corresponding
%   line style menu items in the Options and Context menus.

pipestr = get_param(blk,'LineStyles');   % get all user-specified style specs
style = get_pipestr(pipestr,lineNum,1);  % style for line lineNum

% Map from user-specified style to actual style string:
if isempty(style),
	style='-';
end

h = getStyleMenuHandlesFromStyle(blk, lineNum, style); % get handles - may be empty


% ---------------------------------------------------------------
function y = anyStemMarkers(blk)
% Determine if any lines have a Stem marker selected

block_data = get_param(blk,'UserData');
nchans = block_data.NChans;
pipestr = get_param(blk,'LineMarkers');   % get all user-specified marker specs
y = 0;  % assume no stem markers selected
for lineNum=1:nchans,
	marker = get_pipestr(pipestr, lineNum,1);
	y = strcmp(marker,'stem');
	if y, return; end
end


% ---------------------------------------------------------------
function [marker,h] = getDialogLineMarker(blk,lineNum)
% Determine RGB vector corresponding to user-specified color.
%  - If user-specified marker is empty, 'none' is substituted.
%  - If user-specified marker is not found, marker is set to empty.
%
% Optionally returns vector of 2 handles, H, for corresponding
%   line marker menu items in the Options and Context menus.

pipestr = get_param(blk,'LineMarkers');   % get all user-specified marker specs
marker = get_pipestr(pipestr,lineNum,1);  % marker for line lineNum

% Map from user-specified marker to actual style string:
if isempty(marker),
	marker='None';
end

h = getMarkerMenuHandlesFromMarker(blk, lineNum, marker); % get handles - may be empty


% ---------------------------------------------------------------
function [disable,h] = getDialogLineDisable(blk,lineNum)
% Determine channel disable setting
%
% Optionally returns vector of 2 handles, H, for corresponding
%   line marker menu items in the Options and Context menus.

pipestr = get_param(blk,'LineDisables');   % get all user-specified disable specs
disable = get_pipestr(pipestr,lineNum,1);  % disable for line lineNum

% Map from user-specified disable to actual disable string:
if isempty(disable),
	disable='on';
end

h = getDisableMenuHandles(blk, lineNum);


% ---------------------------------------------------------------
function SetMenuChecks(blk)
% Called only from first_update to preset menu checks

% blk: masked subsystem block

block_data = get_param(blk,'UserData');
fig_data   = get(block_data.hfig,'UserData');

% Update AxisGrid menu check:
%
opt = get_param(blk,'AxisGrid');
set(fig_data.menu.axisgrid, 'Checked',opt);

% Update AxisZoom menu check:
%
opt = get_param(blk,'AxisZoom');
set(fig_data.menu.axiszoom, 'Checked',opt);

% Update Frame Number menu check:
%
opt = get_param(blk,'FrameNumber');
set(fig_data.menu.framenumber, 'Checked',opt);


% Update Legend menu check:
%
opt = get_param(blk,'AxisLegend');
set(fig_data.menu.axislegend, 'Checked',opt);

% Update Memory menu check:
%
opt = get_param(blk,'Memory');
set(fig_data.menu.memory, 'Checked',opt);


% Update line color menu checks:
%

% Reset all checks for this line in both the options and
%   context menus for line styles/colors/markers:
h=[fig_data.menu.linecolor fig_data.menu.linestyle ...
		fig_data.menu.linemarker];
hc=get(h,'child');
hc=cat(1,hc{:});
set(hc,'check','off');

% Turn on appropriate menu checks:
for i = 1 : block_data.NChans,
	% If item corresponds to a valid index, turn on check-marks
	%   for that item in both the options and context menus:
	% Handle will be empty if menu does not contain user-specified choice
	
	% Update line disable menu checks:
	[status, h] = getDialogLineDisable(blk, i);
	set(h,'check',status);
	
	% Update line colors menu checks:
	[rgb, h] = getDialogLineColor(blk, i);
	set(h,'check','on');
	
	% Update line styles menu checks:
	[style, h] = getDialogLineStyle(blk, i);
	set(h,'check','on');
	
	% Update line markers menu checks:
	[marker, h] = getDialogLineMarker(blk, i);
	set(h,'check','on');
end


% ---------------------------------------------------------------
function RevertDialogParams(blk)
% Reset all current parameters in block dialog
% These are all the "eval-mode edit box" fields:

block_data = get_param(blk, 'UserData');
names  = get_param(blk,'masknames');
styles = get_param(blk,'maskstyles');
% values = get_param(blk,'maskvalues');
pv={};
r = get_param(blk,'maskvariables');
for i=1:length(styles),
	[t,r]=strtok(r,'@&');
	if (r(1)=='@') & strcmp(styles{i},'edit'), % eval-mode edit box
		% Cannot use MaskValues field from the block, since we need
		% to access the "old" parameter values and the mask only
		% holds the "new" (and apparently erroneous) ones:
		val = mat2str(getfield(block_data.params,names{i}));
		pv = [pv {names{i},val}];
	end
end
set_param(blk,pv{:});


% ---------------------------------------------------------------
function msg = CheckParams(blk, params)

msg = '';

% Check Domain:
% -------------
x = params.Domain;
if (x~=1) & (x~=2) & (x~=3),
	msg = 'Domain must be 1 (Time), 2 (Freq), or 3 (User).';
	return
end

% Check XLabel:
% -------------
if ~ischar(params.XLabel),
	msg = 'X-axis label must be a string.';
	return
end

% Check XUnits:
% -------------
x = params.XUnits;
if (x~=1) & (x~=2),
	msg = 'X-axis units must be 1 (Hz) or 2 (rad/s).';
	return
end

% Check XIncr:
% -------------
if ~isOn(params.InheritXIncr),
	x = params.XIncr;
	Nx = GetNumberOfElements(x);
	if ~isa(x,'double') | issparse(x) | ~isreal(x) | ...
			(Nx ~= 1) | (x <= 0),
		msg = 'X-axis increment must be a real, double-precision scalar > 0.';
		return
	end
end

% Check XRange:
% -------------
x = params.XRange;
if (x~=1) & (x~=2) & (x~=3),
	msg = 'X-axis range must be 1 [0,Fn], 2 [-Fn,Fn], or 3 [0,Fs].';
	return
end

% Check YLabel:
% -------------
if ~ischar(params.YLabel),
	msg = 'Y-axis label must be a string.';
	return
end

% Check YUnits:
% -------------
x = params.YUnits;
if (x~=1) & (x~=2),
	msg = 'YUnits must be 1 (Hz) or 2 (rad/s).';
	return
end

% Check horizontal span:
% ----------------------
x = params.HorizSpan;
Nx = GetNumberOfElements(x);
if ~isa(x,'double') | issparse(x) | ~isreal(x) | ...
		(Nx ~= 1) | (x ~= floor(x)) | (x <= 0),
	msg = 'Horizontal span must be a real, integer-valued scalar > 0.';
	return
end


% Check YMin:
% -----------
x = params.YMin;
Nx = GetNumberOfElements(x);
if ~isa(x,'double') | issparse(x) | ~isreal(x) | ...
		(Nx ~= 1),
	msg = 'Y-minimum must be a real-valued scalar.';
	return
end
ymin = x;

% Check YMax:
% -----------
x = params.YMax;
Nx = GetNumberOfElements(x);
if ~isa(x,'double') | issparse(x) | ~isreal(x) | ...
		(Nx ~= 1),
	msg = 'Y-maximum must be a real-valued scalar.';
	return
end
if x<=ymin,
	msg = 'Maximum Y-axis limit must be greater than Minimum Y-axis limit.';
	return
end

% Check FigPos:
% -------------
x = params.FigPos;
if ~isa(x,'double') | issparse(x) | ~isreal(x) | ...
		size(x,1)~= 1 | size(x,2)~=4,
	msg = 'Figure position must be a real-valued 1x4 vector.';
	return
end

% Check LineColors/Styles/Markers:
% ----------------
x = params.LineColors;
if ~ischar(params.LineColors) | ...
		~ischar(params.LineStyles) | ...
		~ischar(params.LineMarkers) | ...
		~ischar(params.LineDisables),
	msg = 'Line Colors, Styles, Markers, and Disables must be strings.';
	return
end

% Check AxisGrid:
% Check AxisZoom:
% Check FrameNumber:
% Check AxisLegend:
% Check Memory:
% Check AxisParams:
% Check LineParams:
% -----------------
% (skip checkboxes)


% ---------------------------------------------------------------
function y = relevant_params_changed(params1, params2)

% Remove the "dialog tab" fields, eg, fields that can change
% without affecting any user options:
%
nop={'ScopeProperties','DisplayProperties','AxisProperties','LineProperties'};

params1 = rmfield(params1,nop);
params2 = rmfield(params2,nop);
y = ~isequal(params1,params2);


% ---------------------------------------------------------------
function DialogApply(params,block_name)

% Called from MaskInitialization command via:
%   sdspfscope2([],[],[],'DialogApply',params);

% Updates block_data

% In case of call from an internal method (not a callback)
% For example, SetAndApply calls us directly, and gcb might
% not be correct...
if nargin<2,
	block_name = gcb;
end
block_data = get_param(block_name, 'UserData');

if isempty(block_data),
	return;  % System has not run yet
end

% Check dialog parameters:
% ------------------------
msg = CheckParams(block_name, params);
if ~isempty(msg),
	% Invalid parameters
	errordlg(msg,'Frame Scope Dialog Error','modal');
	RevertDialogParams(block_name);
	return
end

% On start-up, no params field exists
% Set params, and skip any further processing
if ~isfield(block_data,'params'),
	block_data.params = params;
	set_param(block_name, 'UserData', block_data);
	return;
end

% Check for a run-time change in the scaling or units:
if relevant_params_changed(params, block_data.params),
    
    if isempty(block_data.hfig),
        % Scope window is not open
        
        % Just record new param info:
        block_data.params = params;
        set_param(block_name, 'UserData', block_data);
        return;
    end
    
    % Scope window is open and a change has been made
    
    % Update menu checks:
    SetMenuChecks(block_name);
    
    % Handle figure position changes here:
    % Only update if the current block dialog FigPos differs from
    % the cached block_data FigPos.  The figure itself might be at
    % a different position, which we should not change UNLESS the
    % user actually made an explicit change in the mask (or, hit
    % the RecordFigPos menu item, which simply changes the mask).
    if ~isequal(block_data.params.FigPos, params.FigPos),
        set(block_data.hfig,'Position',params.FigPos);
    end
    
    % Could check for a change in YUnits (Amplitude <-> dB)
    % Start autoscale if changed
    % start_autoscale = ~isequal(block_data.params.YUnits, params.YUnits);
    
    % OBJECTIVES:
    %
    % 1 - formulate a "u" vector that emulates that which Simulink
    %     would have passed in on a standard "mdlUpdate" call.
    % 2 - update the display if the number of horiz frames has changed
    % 3 - call setup_axes
    
    % Get currently displayed line data, before any
    % new dialog param changes are instated.
    %
    % xdata = get(block_data.hline(1),'xdata');
    u_disp = get(block_data.hline, 'ydata');
    
    if iscell(u_disp),
        % If we have a cell, then length(block_data.hline) > 1
        % This was caused by a multi-channel display
        %
        ndepc  = length(u_disp{1}); % (# display elements per chan) = (samples/frame)*(nframes)
        nchans = length(u_disp);    % number of channels
        u_disp = [u_disp{:}];       % make it one long vector
        u_disp = reshape(u_disp,ndepc,nchans);
    else
        % Reshape in case of multiple channels (matrix input)
        % Also, corrects for HG giving us a 1xN when it should be Nx1 per channel
        nchans = 1;
        if (block_data.NChans ~= nchans),
            error('Number of channels is incorrect.');
        end
        ndepc  = length(u_disp(:));
        u_disp = reshape(u_disp, ndepc, block_data.NChans);
    end
    
    % Determine samples per frame
    % NOTE: could be multiple channels of data,
    %  and multiple horizontal frames per channel.
    %  Disregard param if freq domain
    orig_horizspan = block_data.params.HorizSpan;
    orig_samples_per_frame = block_data.samples_per_frame;
    
    % Formulate "emulated input, u" from u_disp
    %
    if (orig_horizspan == 1),
        % Only one frame in display span
        % The input, u, to mdlUpdate is u_disp itself
        u = u_disp;
    else
        % More than one frame in horiz span
        % The input, u, from Simulink is only one frame
        % Hence, we must keep only one frame from u_disp
        % We choose to keep the most recent:
        u = u_disp(end - orig_samples_per_frame+1:end, :);
    end
    
    % Update displayed data
    %
    % xxx must also update stem lines
    %
    if params.HorizSpan ~= orig_horizspan,
        % Number of displayed frames has changed
        
        if params.HorizSpan > orig_horizspan,
            % new horiz span exceeds span in current display
            % we need to "manufacture" additional input data
            ydata = u_disp;
            padframes = params.HorizSpan - orig_horizspan;
            prepad = NaN * ones(padframes*orig_samples_per_frame, nchans);
            ydata = [prepad; ydata];
            
        else % if params.HorizSpan < orig_horizspan,
            % new horiz span is less than span in current display
            %
            % retain only the most recent frames for the display
            f1 = orig_horizspan - (params.HorizSpan-1); % first frame
            f2 = orig_horizspan;                        % last frame
            s1 = (f1-1)*orig_samples_per_frame+1;       % 1st sample in 1st frame
            s2 = f2*orig_samples_per_frame;             % last sample of last frame
            ydata = u_disp(s1:s2,:);
        end
        
        % We can make up any xdata, as long as it has the right length,
        % since setup_axes will correct the values:
        xdata = (1:size(ydata,1))';
        for i=1:nchans,
            set(block_data.hline(i),'xdata',xdata, 'ydata',ydata(:,i));
        end
    end
    
    % Must "undo" any preprocessing performed on data
    % to emulate Simulink's input data to block
    %
    % Use "old" params to determine how to undo processing.
    if (block_data.params.Domain==2),
        % Convert from dB back to linear, if required:
        if block_data.params.YUnits == 2,
			u = dB2lin(u);
        end
        % Unrotate data if display range is [-Fn, Fn]:
        if block_data.params.XRange == 2,
            % unrotate each channel of data:
            p = orig_samples_per_frame/2;  % all FFT's are a power of 2 here
            u = u([p+1:orig_samples_per_frame 1:p],:);
        end
    end
    
    % Record new param info:
    block_data.params = params;
    
    % Cache the appropriate line-update function
    %
    % If HorizSpan, NChans, or Domain changed, this will need updating:
    block_data.UpdateLinesFcn = select_UpdateLinesFcn(block_data);

    set_param(block_name, 'UserData', block_data);

    % Adjust the GUI axes, line styles, etc,
    setup_axes(block_name);
end


% ---------------------------------------------------------------
function setup_axes(block_name)
% Setup scope x- and y-axes

% Does not alter block_data
% u = input data, one frame of data

block_data = get_param(block_name,'UserData');
hfig    = block_data.hfig;
hax     = block_data.haxis;
hline   = block_data.hline;
hstem   = block_data.hstem;
nframes = block_data.params.HorizSpan;
nchans  = block_data.NChans;
samples_per_channel = block_data.samples_per_frame;
% [samples_per_channel, nchans] = size(u);

% Clear memory (persistence):
% ---------------------------
FigRefresh([],[],block_data.hfig);

% Assign line colors and styles:
% ------------------------------
stem_rgb = 'k';  % in case no lines use stem plots
for i=1:length(hline),
	rgb        = getDialogLineColor(block_name, i);
	style      = getDialogLineStyle(block_name, i);
	marker     = getDialogLineMarker(block_name, i);
	disable    = getDialogLineDisable(block_name, i);
	markerface = 'auto'; % rgb;
	
	% There is only one set of stem lines, so we need to deduce
	% which line color/style to set it to.  We could use multiple
	% stem lines, but that seems like it would use significantly
	% more time/memory without much improvement.
	if strcmp(marker,'stem'),
		% Set stem line style
		% If style is 'none', use solid:
		if strcmp(lower(style),'none'),
			style='-';
		end
		set(hstem,'linestyle',style);
		stem_rgb = rgb;  % use the "last" stem color
		
		% Reset some properties for stem markers:
		style='none';
		marker='o';
	end
	
	set(hline(i), ...
		'Color', rgb, ...
		'LineStyle', style, ...
		'Visible', disable, ...
		'Marker', marker, ...
		'MarkerFaceColor',markerface);
end

% Setup vertical stem lines:
if anyStemMarkers(block_name), 
	stemVis='on';
else
	stemVis='off';
end
set(hstem, ...
	'color',     stem_rgb, ...
	'visible',   stemVis, ...
	'marker',    'none');

% Determine x-axis limits:
% ------------------------
switch block_data.params.Domain
case 1
	% Time-domain:
	
	if block_data.Ts<0,
		% Triggered:
		ts = 1;
		xLabel = 'Trigger events (samples)';
	else
		ts = block_data.Ts;
		xLabel = 'Time (s)';
	end
	xData  = (0:samples_per_channel*nframes-1)' * ts;
	
	if (samples_per_channel==1) & (nframes==1),
		xLimits = [-ts ts];  % prevent problems
	else
		xLimits = [-ts xData(end)+ts];
	end
	
case 2
	% Frequency domain:
	
	% Disregard # horiz frames (nframes) for freq domain
	% sample time can be inherited, but is usually overridden by user
	if isOn(block_data.params.InheritXIncr),
		% Inherited sample rate:
		if block_data.Ts==-1,
			Fs = samples_per_channel;  % unusual, but we'll allow it
			
		else
			% This is either the Spectrum Scope, or the Vector Scope
			% set to the Freq domain.
			%
			% We may have a buffer of data here, due to:
			%  - an inherited frame, or
			%  - explicit buffering parameters
			%
			% NOTE: .Ts is the per-frame time, not per-sample time
			
			% Determine if explicit buffered is being used:
			%   - this must be a spectrum scope block, and
			%   - the buffered checkbox (UseBuffer) is set
			if strcmp(get_param(block_name,'MaskType'), 'Spectrum Scope'),
				isExplicitBuffer = isOn(block_data.params.UseBuffer);
			else
				isExplicitBuffer=0;
			end
			if isExplicitBuffer,
				buffer_size    = block_data.params.BufferSize;
				buffer_overlap = block_data.params.Overlap;
			else
				buffer_size = samples_per_channel;  % assume frame input
				buffer_overlap = 0;                 % assume no overlap
			end
			
			Fs = 1 ./ block_data.Ts;  % sample rate
		end
	else
		% User-defined sample time for frequency domain:
		Fs = 1 ./ block_data.params.XIncr;  % sample rate
	end
	
	Fn = Fs/2;  % Nyquist rate
	
	xData = (0 : samples_per_channel-1)' .* Fs ./ samples_per_channel;
	
	switch block_data.params.XRange
	case 1,
		xLimits = [0 Fn];
	case 2,
		xLimits = [-Fn Fn];
		xData = (0:samples_per_channel-1)' .* Fs ./ samples_per_channel - Fn;
	otherwise,
		xLimits = [0 Fs];
	end
	
	if block_data.params.XUnits == 1,
		xLabel  = 'Frequency (Hz)';
	else
		xLabel  = 'Frequency (rad/s)';
		xLimits = 2*pi * xLimits;
		xData   = 2*pi * xData;
	end
	
otherwise
	% User-defined
	
	% Determine sample increment if inherited:
	if isOn(block_data.params.InheritXIncr),
		if block_data.Ts==-1,  % triggered system?
			incr = 1; % unusual, but we'll allow it
		else
			incr = block_data.Ts;
		end
	else
		incr = block_data.params.XIncr;
	end
	
	xLabel = block_data.params.XLabel;
	if ~isstr(xLabel), xLabel = 'X-Axis'; end
	xData  = (0:samples_per_channel*nframes-1)' * incr;
	
	if (samples_per_channel==1) & (nframes==1),
		xLimits = [-incr incr];  % prevent problems
	else
		xLimits = [-incr xData(end)+incr];
	end
	
end

% Adjust x-axes for engineering units:
% ------------------------------------
% Allow scalar
if xLimits(2)==0,
	xLimits=[0 1];
elseif (xLimits(1) > xLimits(2)),
	warning(sprintf(['Reversing the order of X-axis limits entered in scope block: %s\n', ...
			'Limits must appear in ascending order.'], block_name));
	xLimits(1:2)=xLimits([2 1]);
end
set(hax,'xLim',xLimits);  % preliminary gridding of limits

% Don't adjust the user-defined domain:
if (block_data.params.Domain ~= 3),
	xlim = get(hax,'xlim');
	
	if block_data.params.Domain==1,
		% engunits will use us/ms, and s/mins/hrs where applicable
		[xunits_val,xunits_exp,xunits_prefix] = engunits(max(abs(xlim)),'latex','time');
	else
		[xunits_val,xunits_exp,xunits_prefix] = engunits(max(abs(xlim)),'latex','freq');
	end
	xData = xData .* xunits_exp;
	set(hax, 'xlim', xlim .* xunits_exp);
end

% Setup X-axis label:
% -------------------
% Don't modify user-defined domain:
if block_data.params.Domain == 2,
	% Freq - insert units only  'Freq (Hz)' => 'Freq (kHz)'
	i = find(xLabel=='(');
	s = [xLabel(1:i) xunits_prefix xLabel(i+1:end)];
	xLabel = s;
elseif block_data.params.Domain==1,
	% Time - remove everything between parens  'Horiz (s)' => 'Horiz (days)'
	i = find(xLabel=='('); j = find(xLabel==')');
	s = [xLabel(1:i) xunits_prefix xLabel(j:end)];
	xLabel = s;
end
hxLabel = get(hax, 'XLabel');
set(hxLabel, 'String', xLabel);


% Setup Y-axis label and limits:
% ------------------------------
yLabel = block_data.params.YLabel;

if ~isstr(yLabel), yLabel='Y-Axis'; end
hyLabel = get(hax,'YLabel');
set(hyLabel, 'String', yLabel);

set(hax, 'ylimmode','manual', ...
	'ylim',[block_data.params.YMin block_data.params.YMax]);

% Setup line data:
% ----------------
% Don't draw anything, so use NaN's for Y-data.
% Can use vectorized set since all x/y data are identical.
%
% NOTE: update_lines() does NOT alter/update the x-data,
%       so it needs to be set up once here, correctly.
%       The y-data is not significant ... just needs to be
%       sized appropriately:

xLen=length(xData);
uNaN = NaN;
for i=1:length(hline),
	yData = get(hline(i),'YData')';
	% Manufacture the right yData if default is present:
	if length(yData) ~= xLen,
		yData = uNaN(ones(xLen,1));
	end
	set(hline(i),'XData',xData, 'YData',yData);
end


% Setup "stem" line data:
% -----------------------
% Stems are implemented as a SECOND line
% The usual data plotting occurs, but with a circle ('o')
%   substituted for the marker.
% In addition, a second line is set up for the vertical
%   stems themselves.
%
% We only need to set up ONE stem line.
% The vertical extent will reach to the "highest" point
%   at each sample time, effectively providing a stem for
%   *all* data channels at one time.
%
% Vertical stem data:
%
%    [x1 x1 x1   x2 x2 x2   x3 x3 x3  ....]
%    [0  y1 NaN  0  y2 NaN  0  y3 NaN ...]

xstem = [xData';xData';xData'];
xstem = xstem(:)'; % triplicate each value
ymin = 0;  % stems originate from y=0, not block_data.params.YMin
ystem = [ymin;0;NaN];  % assume y values are 0 for now
ystem = ystem(:,ones(size(xData)));
ystem = ystem(:)';
set(hstem, 'xdata', xstem, 'ydata', ystem);

% Perform AxisZoom:
% -----------------
%
% Put axis into correct zoom state:
fig_data = get(hfig,'UserData');
if strcmp(block_data.params.AxisZoom,'off'),
	% Turn off AxisZoom:
	% - turn on menus
	set(fig_data.menu.top,'vis','on');
	% - reset axis position
	set(hax, fig_data.main.axiszoom.off{:});
	
else
	% Turn on AxisZoom:
	% - turn off top-level menus
	set(fig_data.menu.top,'vis','off');
	set(hax, fig_data.main.axiszoom.on{:});
end


% Turn on scalar warning message, if appropriate:
% ------------------------------
isScalarPlot = (samples_per_channel==1) & (nframes==1);
if isScalarPlot,
	if block_data.params.Domain==2,
		tstr='[Plotting single points - consider using a vector input]';
	else
		tstr='[Plotting single points - consider increasing the display span]';
	end
else
	tstr='';
end
set(get(hax,'Title'), 'String', tstr, 'color','b');

% Update Frame Number display:
% ----------------------------
%
if isOn(block_data.params.FrameNumber),
	% Move frame # text depending on axis zoom (on or off)
	% The frame handle vector contains:
	%   .hframenum(1) = "Frame:"
	%   .hframenum(2) = "###"
	% NOTE: Position of frame readout is updated in figresize
	% 
	if isOn(block_data.params.AxisZoom),
		% Axis zoom on - x-axis is not visible
		ltgrayt = ones(1,3)*.6;  % slightly darker for grid labels
		set(block_data.hframenum, ...
			'verticalalignment','bottom', ...
			'color',ltgrayt, 'vis','on');
	else
		% Axis zoom off - x-axis is visible
		set(block_data.hframenum, ...
			'verticalalignment','cap', ...
			'color','k', 'vis','on');
	end
else
	set(block_data.hframenum,'vis','off');
end

% Update line erase mode (Persistence):
startLineEraseMode(block_name);

UpdateLegend(block_name);

% Manually call the resize fcn:
FigResize([],[],hfig);


% ---------------------------------------------------------------
function UpdateLegend(blk)

block_data = get_param(blk,'UserData');
hlegend    = block_data.hlegend;
useLegend  = isOn(block_data.params.AxisLegend);

if ishandle(hlegend),
	delete(hlegend);
end
hlegend = [];

if useLegend,
	hlines = block_data.hline;
	
	% Get signal names:
	names = getInputSignalNames(blk);
	
	% Prevent failures in legend:
	prop = 'ShowHiddenHandles';
	old_state = get(0,prop);
	set(0,prop,'on');
	axes(block_data.haxis);
	hlegend = legend(hlines, names{:});
	% hlegend = legend(block_data.haxis, hlines, str{:});
	set(0,prop,old_state);
end

% Store changes to legend handle:
block_data.hlegend = hlegend;
set_param(blk,'UserData',block_data);


% ---------------------------------------------------------------
function UpdateFrameNumPos(blk)

block_data = get_param(blk,'UserData');

if isOn(block_data.params.FrameNumber),
	if isOn(block_data.params.AxisZoom),
		% Axis zoom on - x-axis is not visible
		%
		% Position "Frame: #" text above the x-axis tick numbers
		% and to the right of the y-axis tick numbers
		%
		hgridtxt = block_data.hgridtext;
		grid_ext = get(hgridtxt(1),'extent');
		ypos     = grid_ext(2)+grid_ext(4);
		grid_ext = get(hgridtxt(end),'extent');
		xpos     = grid_ext(1)+grid_ext(3);
		
		set(block_data.hframenum(1), ...
			'units','data', 'pos',[xpos ypos]);
		ex=get(block_data.hframenum(1),'extent');
		set(block_data.hframenum(2), ...
			'units','data', 'pos',[ex(1)+ex(3) ypos]);
	else
		% Axis zoom off - x-axis is visible
		%
		% Position "Frame: #" at the same y-level as the x-axis label
		% and flush with the start of the x-axis.
		hax = block_data.haxis;
		hxtitle = get(hax,'xlabel');
		set(hxtitle','units','data');
		xtpos = get(hxtitle,'pos');
		xlim = get(hax,'xlim');
		set(block_data.hframenum(1), 'units','data', ...
			'pos',[xlim(1) xtpos(2)]);
		ex=get(block_data.hframenum(1),'extent');
		frpos = [ex(1)+ex(3) xtpos(2)];
		set(block_data.hframenum(2), 'units','data', ...
			'pos',frpos);
		
		% If xlabel overlaps frame label, turn off frame label:
		xtitle_ex = get(hxtitle,'extent');
		fr_ex = get(block_data.hframenum(2),'extent');
		overlap = xtitle_ex(1) < (fr_ex(1)+fr_ex(3));
		if overlap, vis='off'; else vis='on'; end
		set(block_data.hframenum,'vis',vis);
	end
end


% ---------------------------------------------------------------
function FigResize(hcb,eventStruct,hfig)
% Callback from window resize function

if nargin<3, hfig = gcbf; end
fig_data = get(hfig,'UserData');
if isempty(fig_data), return; end

blk = fig_data.block;
block_data = get_param(blk,'UserData');

% Resize the axis when not in "compact display" (axis zoom) mode:
if ~isOn(block_data.params.AxisZoom),
	fig_pos = get(hfig,'pos');
	ax_pos = [60 40 max([1 1], fig_pos([3 4])-[80 60])];
	set(block_data.haxis, 'pos',ax_pos);
end

UpdateGrid(blk);  % Do this prior to repositioning frame # text
UpdateFrameNumPos(blk);

% Update legend, if it is on:
if isOn(block_data.params.AxisLegend),
	legend('ResizeLegend', block_data.haxis);
end


% ---------------------------------------------------------------
function fig_data = create_scope(block_name, params, nchans)
% CREATE_SCOPE Create new scope GUI

% Initialize empty settings:
fig_data.main  = [];  % until we move things here
fig_data.menu  = [];

hfig = figure('numbertitle', 'off', ...
	'name',              block_name, ...
	'menubar',           'none', ...
	'position',          params.FigPos, ...
	'nextplot',          'add', ...
	'integerhandle',     'off', ...
	'doublebuffer',      'off', ...
	'PaperPositionMode', 'auto', ...
	'ResizeFcn',         @FigResize, ...
	'DeleteFcn',         @FigDelete, ...
	'HandleVisibility', 'callback');

hax = axes('Parent',hfig, ...
	'DrawMode','fast', ...
	'Box','on', 'ticklength',[0 0], ...
	'Position', [0.1300 0.1450 0.7750 0.8000]);

% Set up line for each channel:
for i = 1:nchans,
	hline(i) = line('parent',hax, ...
		'xdata',NaN, 'ydata',NaN);
end
hstem = line('parent',hax, ...
	'xdata',NaN, 'ydata',NaN);
hgrid = line('parent',hax, ...
	'xdata',NaN, 'ydata',NaN, ...
	'erasemode','xor', 'color',[.8 .8 .8]);

% Create non-displaying line to use for color translations
hcspec = line('parent',hax, ...
	'xdata',nan,'ydata',nan, ...
	'vis','off');

% Create Frame Number text
hframenum(1) = text(0,0,'','parent',hax);
hframenum(2) = text(0,0,'','parent',hax);
set(hframenum(1),'string','Frame: ','erase','xor','horiz','left');
set(hframenum(2),'string','-','erase','xor','userdata',0,'horiz','left');

% Create a context menu:
mContext = uicontextmenu('parent',hfig);

% Establish settings for all structure fields:
fig_data.block  = block_name;
fig_data.hfig   = hfig;
fig_data.hcspec = hcspec;

% Store major settings:
fig_data.main.haxis   = hax;
fig_data.main.hline   = hline;
fig_data.main.hstem   = hstem;
fig_data.main.hgrid   = hgrid;
fig_data.main.hframenum = hframenum;
fig_data.menu.context = mContext;

% Set the non-compact display axis to pixels,
% so resizing does not affect it
set(hax,'units','pixels')

% Store settings for axis zoom:
% Cell-array contains {params, values},
% where params itself is a cell-array of Units and Position
% and values is a cell-array of corresponding values.
p = {'Units','Position'};
fig_data.main.axiszoom.off = {p, get(hax, p)};
fig_data.main.axiszoom.on  = {p, {'Normalized',[0 0 1 1]}};


% ---------------------------------------------------------
% Figure menus:
% ---------------------------------------------------------
%
% Define FILE menu:
%
pcwin = strcmp(computer,'PCWIN');
if pcwin,
	labels = {'&File', ...
			'&Close', ...
			'&Export...', ...
			'Pa&ge Setup...', ...
			'Print Set&up...', ...
			'Print Pre&view...', ...
			'&Print...'};
else
	labels = {'File', ...
			'Close', ...
			'Export...', ...
			'Page Setup...', ...
			'Print Setup...', ...
			'Print Preview...', ...
			'Print...'};
end
%
mFile = uimenu(hfig,'Label',labels{1});
%
% submenu items:
uimenu(mFile, 'label',labels{2}, ...
	'accel','W', ...
	'callback',{@CloseFigure, block_name});
%uimenu(mFile, 'label',labels{3}, ...
%    'separator','on', ...
%   'callback','filemenufcn(gcbf,''FileExport'')');
uimenu(mFile, 'label',labels{4}, ...
	'separator','on', ...
	'callback','pagesetupdlg(gcbf)');
uimenu(mFile, 'label',labels{5}, ...
	'callback','printdlg(''-setup'')');
uimenu(mFile, 'label',labels{6}, ...
	'callback','printpreview(gcbf)');
uimenu(mFile, 'label',labels{7}, ...
	'accel','P', ...
	'callback','printdlg(gcbf)');

%
% Define AXES menu labels:
%
if pcwin,
	labels = {'&Axes', 'Per&sistence', '&Refresh', ...
			'&Autoscale', 'Show &grid', 'Compact &display', ...
			'&Frame #', 'Channel &legend', 'Save &position'};
else
	labels = {'Axes', 'Persistence', 'Refresh', ...
			'Autoscale', 'Show grid', 'Compact display', ...
			'Frame #', 'Channel legend', 'Save position'};
end
%
% Create figure AXIS menu
%
mAxes = uimenu(hfig, 'Label', labels{1});  % top-level Axes menu in figure
%
% submenu items:
fig_data.menu.memory = uimenu(mAxes, 'label',labels{2}, ...
	'callback',@Memory);
fig_data.menu.refresh = uimenu(mAxes, 'label',labels{3}, ...
	'callback',@FigRefresh);
fig_data.menu.autoscale = uimenu(mAxes, 'label',labels{4}, ...
	'separator','on', 'callback', @Autoscale);
% - Create Axis Grid item
fig_data.menu.axisgrid = uimenu(mAxes, 'Label', labels{5}, ...
	'Callback', @AxisGrid);
% - Create Axis Zoom item
fig_data.menu.axiszoom = uimenu(mAxes, ...
	'Label', labels{6}, ...
	'Callback', @AxisZoom);
% - Create Axis Frame Number item
fig_data.menu.framenumber = uimenu(mAxes, 'Label', labels{7}, ...
	'Callback', @FrameNumber);
% - Create Axis Legend item
fig_data.menu.axislegend = uimenu(mAxes, 'Label', labels{8}, ...
	'Callback', @AxisLegend);
% - Create Record Position item
fig_data.menu.recpos = uimenu(mAxes, 'label',labels{9}, ...
	'callback', @SaveFigPos, ...
	'separator','on');

% Define OPTIONS menu labels:
%
if pcwin,
	% Use "&" for accelerator characters on the PC:
	labels = {'&Channels', '&Style', '&Marker', '&Color'};
else
	labels = {'Channels', 'Style', 'Marker', 'Color'};
end
%
% Create menus as if there were only ONE line in display:
if nchans >= 1,
	mLines = uimenu(hfig, 'label',labels{1});  % top-level Lines menu in figure
	
	lsmenu = uimenu(mLines, 'label',labels{2});
	lmmenu = uimenu(mLines, 'label',labels{3});
	lcmenu = uimenu(mLines, 'label',labels{4});
	
	fig_data.menu.linestyle  = lsmenu;
	fig_data.menu.linemarker = lmmenu;
	fig_data.menu.linecolor  = lcmenu;
	
	% Line styles submenu:
	uimenu(lsmenu,'label',' None', 'userdata','None', ...
		'callback',@LineStyle);
	uimenu(lsmenu,'label',' -', 'userdata','-', ...
		'callback',@LineStyle);
	uimenu(lsmenu,'label',' --', 'userdata','--', ...
		'callback',@LineStyle);
	uimenu(lsmenu,'label',' :', 'userdata',':', ...
		'callback',@LineStyle);
	uimenu(lsmenu,'label',' -.', 'userdata','-.', ...
		'callback',@LineStyle);
	
	% Line markers submenu:
	uimenu(lmmenu,'label','None','userdata','None', ...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','+','userdata','+',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','o','userdata','o',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','*','userdata','*',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','.','userdata','.',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','x','userdata','x',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','Square','userdata','Square',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','Diamond','userdata','diamond',...
		'callback',@LineMarker);
	uimenu(lmmenu,'label','Stem','userdata','stem',...
		'callback',@LineMarker);
	
	% Line colors submenu:
	% UserData holds valid RGB triples for each entry
	uimenu(lcmenu,'label','Cyan','userdata',[0 1 1],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','Magenta','userdata',[1 0 1],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','Yellow','userdata',[1 1 0],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','Black','userdata',[0 0 0],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','Red','userdata',[1 0 0],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','Green','userdata',[0 1 0],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','Blue','userdata',[0 0 1],...
		'callback',@LineColor);
	uimenu(lcmenu,'label','White','userdata',[1 1 1 ],...
		'callback',@LineColor);
end

%
% Define WINDOW menu
%
if pcwin,
	labels = {'&Window'};
else
	labels = {'Window'};
end
mWindow = uimenu(hfig,'Label',labels{1}, ...
	'tag','winmenu', ...
	'callback', winmenu('callback'));

% Help menu:
mHelp = uimenu(hfig,'Label','&Help');

% Help->Help Topics:
uimenu('parent',mHelp, ...
	'label','&Help Topics', ...
	'callback', @HelpTopicsCB);

% Help->What's This? submenu:
%uimenu('parent',mHelp, ...
%   'label','&What''s This?', ...
%   'callback', @HelpWhatsThisCB);


% Store all top-level menu items in one vector
fig_data.menu.top = [mFile mAxes mLines mWindow mHelp];


% Recreate the figure and context menus if there are 2 or more lines
%
% One line menu item for each channel.
% Need to position the menu items according to i
if nchans==1,
	% Single line display:
	
	% Just to allow things to be easy, define a "visible" menu item,
	% but make them invisible (doesn't seem to make a lot of sense
	% for one channel):
	fig_data.menu.linedisable(1,i) = uimenu(mLines,'label','Visible',...
		'callback',@LineDisable,'position',1, ...
		'visible','off');
	fig_data.menu.linedisable(2,i) = uimenu(mContext,'label','Visible',...
		'callback',@LineDisable, ...
		'visible','off');
	
	% Populate the context menu with Style/Color/Marker menus:
	fig_data.menu.linestyle(2,1)  = copyobj(lsmenu, mContext);
	fig_data.menu.linemarker(2,1) = copyobj(lmmenu, mContext);
	fig_data.menu.linecolor(2,1)  = copyobj(lcmenu, mContext);
	
else
	% Multiple line display:
	fig_data.menu.linestyle  = [];
	fig_data.menu.linemarker = [];
	fig_data.menu.linecolor  = [];
	for i = 1:nchans,
		% Create new "Ch #" submenus in Options and Context menus:
		s = ['CH ' num2str(i)];
		lineo(i) = uimenu(mLines, 'Label', s, 'Position', i, 'UserData', i);
		linec(i) = uimenu(mContext, 'Label', s, 'Position', i, 'UserData', i);
		
		% Add "disable" option to each channel menu
		fig_data.menu.linedisable(1,i) = uimenu(lineo(i),'label','Visible',...
			'callback',@LineDisable);
		fig_data.menu.linedisable(2,i) = uimenu(linec(i),'label','Visible',...
			'callback',@LineDisable);
		
		% Copy each line options submenu under the new "Line #"
		% submenus in both the Options and Context menus:
		% - styles
		fig_data.menu.linestyle(:,i) = ...
			[copyobj(lsmenu, lineo(i)); copyobj(lsmenu, linec(i))];
		set(fig_data.menu.linestyle(:,i),'separator','on');
		% - markers
		fig_data.menu.linemarker(:,i) = ...
			[copyobj(lmmenu, lineo(i)); copyobj(lmmenu, linec(i))];
		% - colors
		fig_data.menu.linecolor(:,i) = ...
			[copyobj(lcmenu, lineo(i)); copyobj(lcmenu, linec(i))];
	end
	
	% Get rid of original "one display line" submenus from Options menu:
	delete([lsmenu lmmenu lcmenu]);  % Options submenus
end

% Copy menu items in common to both single- and multi-line context menus:
%
% Copy autoscale menu to context menu:
%
cAutoscale = copyobj(fig_data.menu.autoscale, mContext);
%set(cAutoscale,'separator','on');  % Turn on separator just above item
%
% Copy AxisGrid menu, storing both menu handles:
cAxisGrid = copyobj(fig_data.menu.axisgrid, mContext);
fig_data.menu.axisgrid = [fig_data.menu.axisgrid cAxisGrid];
% Copy AxisZoom menu, storing both menu handles:
cAxisZoom = copyobj(fig_data.menu.axiszoom, mContext);
fig_data.menu.axiszoom = [fig_data.menu.axiszoom cAxisZoom];
% Copy Frame #, storing both menu handles:
cFrameNumber = copyobj(fig_data.menu.framenumber, mContext);
fig_data.menu.framenumber = [fig_data.menu.framenumber cFrameNumber];
% Copy Legend menu, storing both menu handles:
cAxisLegend = copyobj(fig_data.menu.axislegend, mContext);
fig_data.menu.axislegend = [fig_data.menu.axislegend cAxisLegend];

%
% Copy save position menu:
cSavePos = copyobj(fig_data.menu.recpos, mContext);

% ---------------------------------------------------------
% End of figure menus
% ---------------------------------------------------------

% Record figure data:
set(hfig, 'UserData', fig_data);

% Assign context menu to the axis, lines, and grid:
set([fig_data.main.haxis fig_data.main.hgrid ...
		fig_data.main.hline fig_data.main.hstem], ...
	'UIContextMenu', mContext);


% --------------------------------------------------------------
function HelpTopicsCB(hco, eventStruct)
% HelpTopicsCB Get reference-page help
%
helpwin(mfilename);
return


% ---------------------------------------------------------------
function fig_data = restart_scope(block_name, params)
% RESTART_SCOPE Restart with existing scope window

% We want to confirm to a reasonable probability that
% the existing scope window is valid and can be restarted.

% The caller already verified that hfig is non-empty
block_data = get_param(block_name,'UserData');
hfig = block_data.hfig;

% We don't know if the handle points to a valid window:
if isempty(hfig) | ~ishandle(hfig),
	block_data.hfig = [];  % reset it back
	set_param(block_name,'UserData',block_data);
	fig_data = [];
	return;
end

% Something could fail during restart if the figure data was
% altered between runs ... for example, by command-line interaction.
% If errors occur, abandon the restart attempt:
try,
	fig_data = get(hfig,'UserData');
	hax = fig_data.main.haxis;
	
	% In case memory (persistence) was on:
	FigRefresh([],[],hfig);
	
	% Replace existing lines:
	delete(fig_data.main.hline);
	
	% Data lines:
	for idx = 1:block_data.NChans,
		hline(idx) = line('parent',hax, ...
			'xdata', NaN, ...
			'ydata', NaN, ...
			'linestyle', '-', ...
			'marker',    'none', ...
			'markerfacecolor', 'k', ...
			'color',     'k');
	end
	% NOTE: No need to delete stem lines
	
	% Reset frame number:
	set(fig_data.main.hframenum(2),'userdata',0,'string','0');
	
	% Update data structures:
	fig_data.main.hline = hline;
	
	%block_data.hgrid    = hgrid;
	block_data.hline    = hline;
	
	% Reassign context menu to the lines and grid:
	set(hline, 'UIContextMenu', fig_data.menu.context);
	% set([hline hgrid], 'UIContextMenu', mContext);
	
	figure(hfig); % bring window forward
	
catch
	% Something failed - reset hfig to indicate error during restart:
	fig_data.hfig=[];
	block_data.hfig=[];
end

% Update data structures:
set(hfig, 'UserData',fig_data);
set_param(block_name, 'UserData',block_data);


% ---------------------------------------------------------------
function NameChange
% In response to the name change, we must do the following:
%
% (1) find the old figure window, only if the block had a GUI 
%     associated with it.
%     NOTE: Current block is parent of the S-function block
block_name = gcb;
block_data = get_param(block_name, 'UserData');

% System might never have been run since loading.
% Therefore, block_data might be empty:
if ~isempty(block_data),
	%isstruct(block_data),
	% (2) change name of figure window (cosmetic)
	hfig = block_data.hfig;
	set(hfig,'name',block_name);
	
	% (3) update figure's userdata so that the new blockname
	%     can be used if the figure gets deleted
	fig_data = get(hfig,'UserData');
	fig_data.block = block_name;
	set(hfig,'UserData',fig_data);
end



% ---------------------------------------------------------------
function CloseFigure(hco, eventStruct, blk)
% Manual (programmatic) closing of the figure window

block_data = get_param(blk,'UserData');
hfig       = block_data.hfig;
fig_data   = get(hfig,'UserData');

% Reset the block's figure handle:
block_data.hfig = [];
set_param(blk, 'UserData',block_data);

% Delete the window:
set(hfig,'DeleteFcn','');  % prevent recursion
delete(hfig);


% ---------------------------------------------------------------
function FigDelete(hcb,eventStruct)
% Callback from figure window
% Called when the figure is closed or deleted

hfig = gcbf;
fig_data = get(hfig,'UserData');
if hfig ~= fig_data.hfig,
	error('Figure handle consistency error in FigDelete.');
end

% Close the figure window
CloseFigure([],[],fig_data.block);


% ---------------------------------------------------------------
function BlockDelete
% Block is being deleted from the model

% clear out figure's close function
% delete figure manually
blk = gcbh;
block_data = get_param(blk,'UserData');
if isstruct(block_data),
	set(block_data.hfig, 'DeleteFcn','');
	delete(block_data.hfig);
	block_data.hfig = [];
	set_param(blk,'UserData',block_data);
end


% ---------------------------------------------------------------
function BlockCopy
% Block is being copied from the model

% clear out stored figure handle
blk = gcbh;
block_data = get_param(blk,'UserData');
if isstruct(block_data),
	block_data.hfig = [];
	set_param(blk,'UserData',block_data);
end


% ---------------------------------------------------------------
function SaveFigPos(hcb,eventStruct)
% Record the current position of the figure into the block's mask

% Get the block's name:
hfig = gcbf;
fig_data = get(hfig,'UserData');
if hfig ~= fig_data.hfig,
	error('Figure handle consistency error in SaveFigPos.');
end

% Record the figure position, as a string, into the appropriate mask dialog:
FigPos = get(hfig,'Position');             % Get the fig position in pixels
blk = fig_data.block;
FigPosStr = mat2str(FigPos);
set_param(blk, 'FigPos', FigPosStr); % Record new position

% No need for SetAndApply here
% xxx
% Manually apply change if block dialog is open:
%SetAndApply(blk,'FigPos',FigPosStr);


% ---------------------------------------------------------------
function LineColor(hcb,eventStruct)
% Change line color for one line due to a menu-item selection

hco=gcbo; hfig=gcbf;

fig_data = get(hfig, 'UserData');
blk      = fig_data.block;
hmenus   = fig_data.menu.linecolor;  % [options;context] x [line1 line2 ...]

% Given color-menu handle into Options or Context menu,
%  find "other" menu handle, and return both in a vector.
for lineNum=1:size(hmenus,2),  % loop over columns = Line1,Line2,...
	% rows are [options;context] menus
	h=get(hmenus(:,lineNum),'child');
	h=cat(2,h{:});
	[i,j]=find(h==hco); % i=row#=which color
	if ~isempty(i),
		hi=h(i,:);  % get menu for option and context menus
		break;
	end
end
set(h,'check','off');
set(hi,'check','on');

% Update block dialog setting, so param is recorded in model
% This will indirectly update the param structure, via the
% mask dialog callbacks.
str = mat2str(get(hco,'userdata'));  % convert RGB triple to string
pipestr = set_pipestr( get_param(blk,'LineColors'), lineNum, str);

% Manually apply change if block dialog is open:
SetAndApply(blk,'LineColors',pipestr);


% ---------------------------------------------------------------
function SetAndApply(blk,varargin)

if strcmp(varargin{end},'eval'),
	evalmode=1;
	varargin=varargin(1:end-1);
else
	evalmode=0;
end

% Set value into mask param:
set_param(blk, varargin{:});

% Determine if dialog is open:
dialog_open = 1; % xxx determine if block dialog is open

if dialog_open,
	% Manually apply changes, since dynamic dialog behavior
	% does not allow the change to apply when dialog is open:
	block_data = get_param(blk,'UserData');
	params = block_data.params;
	for i=1:length(varargin)/2,
		v = varargin(2*i-1 : 2*i);
		if evalmode,
			% evaluation value in p/v pair:
			v{2} = str2double(v{2});
		end
		params = setfield(params,v{:});
	end
	DialogApply(params, blk);
end


% ---------------------------------------------------------------
function LineStyle(hcb,eventStruct)
% Change line style for one line due to a menu-item selection

hco=gcbo; hfig=gcbf;

fig_data = get(hfig, 'UserData');
blk      = fig_data.block;
hmenus   = fig_data.menu.linestyle;  % [options context] x [line1 line2 ...]

for lineNum=1:size(hmenus,2),  % loop over columns = Line1,Line2,...
	% rows are [options;context] menus
	h=get(hmenus(:,lineNum),'child');
	h=cat(2,h{:});
	[i,j]=find(h==hco); % i=row#=which style
	if ~isempty(i),
		hi=h(i,:);  % get menu for option and context menus
		break;
	end
end
set(h,'check','off');
set(hi,'check','on');

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
str = get(hco,'userdata');
pipestr = set_pipestr( get_param(blk,'LineStyles'), lineNum, str);
SetAndApply(blk, 'LineStyles', pipestr);


% ---------------------------------------------------------------
function LineMarker(hcb,eventStruct)
% Change line marker for one line due to a menu-item selection

hco=gcbo; hfig=gcbf;

fig_data = get(hfig, 'UserData');
blk      = fig_data.block;
hmenus   = fig_data.menu.linemarker;  % [options context] x [line1 line2 ...]

for lineNum=1:size(hmenus,2),  % loop over columns = Line1,Line2,...
	% rows are [options;context] menus
	h=get(hmenus(:,lineNum),'child');
	h=cat(2,h{:});
	[i,j]=find(h==hco); % i=row#=which marker
	if ~isempty(i),
		hi=h(i,:);  % get menu for option and context menus
		break;
	end
end
set(h,'check','off');
set(hi,'check','on');

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
str = get(hco,'userdata');
pipestr = set_pipestr( get_param(blk,'LineMarkers'), lineNum, str);
SetAndApply(blk, 'LineMarkers', pipestr);


% ---------------------------------------------------------------
function LineDisable(hcb,eventStruct)
% Change disable state for selected line due to a menu-item selectino

hco=gcbo; hfig=gcbf;

fig_data = get(hfig, 'UserData');
blk      = fig_data.block;
hmenus   = fig_data.menu.linedisable;  % [options context] x [line1 line2 ...]

for lineNum=1:size(hmenus,2),  % loop over columns = Line1,Line2,...
	% rows are [options;context] menus
	h=hmenus(:,lineNum);
	i=find(h==hco);
	if ~isempty(i),
		hi=h;  % get menu for option and context menus
		break;
	end
end

if isOn(get(hi(1),'checked')),
	opt='off';
else
	opt='on';
end
set(hi,'check',opt);

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
pipestr = set_pipestr( get_param(blk,'LineDisables'), lineNum, opt);
SetAndApply(blk, 'LineDisables', pipestr);


% ---------------------------------------------------------------
function AxisZoom(hcb,eventStruct,hfig,opt)
% Toggle display of compact display (zoomed-in axes)
%
% opt is a string option and may be one of the following:
%     'toggle', 'on', 'off'
% If not passed, default is 'toggle'.
%
% hfig is the figure handle
% if missing, it is set to gcbf

if nargin<4, opt='toggle'; end
if nargin<3, hfig=gcbf; end

fig_data = get(hfig, 'UserData');
blk      = fig_data.block;
haxzoom  = fig_data.menu.axiszoom;

if strcmp(opt,'toggle'),
	% toggle current setting:
	if isOn(get(haxzoom,'Checked')),
		opt='off';
	else
		opt='on';
	end
end

% Update menu check:
set(haxzoom,'Checked',opt);

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
SetAndApply(blk, 'AxisZoom', opt);


% ---------------------------------------------------------------
function AxisGrid(hcb,eventStruct,hfig,opt)
% Toggle setting of axis grid
%
% opt is a string option and may be one of the following:
%     'toggle', 'on', 'off'
% If not passed, default is 'toggle'.
%
% hfig is the figure handle
% if missing, it is set to gcbf

if nargin<4, opt='toggle'; end
if nargin<3, hfig=gcbf; end

fig_data  = get(hfig, 'UserData');
blk       = fig_data.block;
hopt      = fig_data.menu.axisgrid;

if strcmp(opt,'toggle'),
	% toggle current setting:
	if strcmp(get(hopt,'Checked'),'on'),
		opt='off';
	else
		opt='on';
	end
end

% Update menu check:
set(hopt,'Checked',opt);

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
SetAndApply(blk, 'AxisGrid', opt);


% ---------------------------------------------------------------
function FrameNumber(hcb,eventStruct,hfig,opt)
% Toggle setting of frame number display
%
% opt is a string option and may be one of the following:
%     'toggle', 'on', 'off'
% If not passed, default is 'toggle'.
%
% hfig is the figure handle
% if missing, it is set to gcbf

if nargin<4, opt='toggle'; end
if nargin<3, hfig=gcbf; end

fig_data  = get(hfig, 'UserData');
blk       = fig_data.block;
hfnum     = fig_data.menu.framenumber;

if strcmp(opt,'toggle'),
	% toggle current setting:
	if strcmp(get(hfnum,'Checked'),'on'),
		opt='off';
	else
		opt='on';
	end
end

% Update menu check:
set(hfnum,'Checked',opt);

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
SetAndApply(blk, 'FrameNumber', opt);


% ---------------------------------------------------------------
function AxisLegend(hcb,eventStruct,hfig,opt)
% Toggle setting of axis legend
%
% opt is a string option and may be one of the following:
%     'toggle', 'on', 'off'
% If not passed, default is 'toggle'.
%
% hfig is the figure handle
% if missing, it is set to gcbf

if nargin<4, opt='toggle'; end
if nargin<3, hfig=gcbf; end

fig_data  = get(hfig, 'UserData');
blk       = fig_data.block;
haxlegend = fig_data.menu.axislegend;

if strcmp(opt,'toggle'),
	% toggle current setting:
	if strcmp(get(haxlegend,'Checked'),'on'),
		opt='off';
	else
		opt='on';
	end
end

% Update menu check:
set(haxlegend,'Checked',opt);

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
SetAndApply(blk, 'AxisLegend', opt);


% ---------------------------------------------------------------
function Memory(hcb,eventStruct,hfig,opt)
% Toggle persistence
%
% opt is a string option and may be one of the following:
%     'toggle', 'on', 'off'
% If not passed, default is 'toggle'.
%
% hfig is the figure handle
% if missing, it is set to gcbf

if nargin<4, opt='toggle'; end
if nargin<3, hfig=gcbf; end

fig_data = get(hfig, 'UserData');
blk      = fig_data.block;
h        = fig_data.menu.memory;

if strcmp(opt,'toggle'),
	% toggle current setting:
	if strcmp(get(h,'Checked'),'on'),
		opt='off';
	else
		opt='on';
	end
end

% Update menu check:
set(h,'Checked',opt);

% Update block dialog setting, so param is recorded in model:
% This will indirectly update the param structure, via the
% mask dialog callbacks.
SetAndApply(blk, 'Memory', opt);

% Update line erase mode:
startLineEraseMode(blk);


% ---------------------------------------------------------------
function Autoscale(hcb,eventStruct, block_data, u)
% AUTOSCALE Compute min/max y-limits for several input frames

if nargin<4,
	% Begin autoscale iterations
	
	if nargin==3,
		hfig = block_data;  % 1st arg is hfig
	else
		hfig = gcbf;
	end
	
	fig_data = get(hfig,'UserData');
	blk = fig_data.block;
	block_data = get_param(blk,'UserData');
	
	% If an autoscale operation is currently in progress,
	% cancel it and stop:
	if ~isempty(block_data.autoscaling),
		CancelAutoscale(blk);
		return;
	end
	
	% If simulation stopped, do a one-shot autoscale and leave:
	v = get_sysparam(blk,'simulationstatus');
	if ~strcmp(v,'running'),
		% Simulation is stopped or paused - perform simple one-shot autoscaling:
		oneshot_autoscale(hfig);
		return;
	end
	
	% Begin countdown
	% This is the number of sequential frames which will be examined
	% in order to determine the min/max y-limits
	%
	count=10;
	
	% Preset min and max
	ymin=+inf;
	ymax=-inf;
	
	% Put up an autoscale indicator:
	str = ['Autoscale: ' mat2str(count)];
	htext = text('units','norm','pos',[0.5 0.5], ...
		'EraseMode','xor', ...
		'horiz','center', 'string',str);
	
	block_data.autoscaling = [count ymin ymax htext];
	set_param(blk, 'UserData', block_data);
	
else
	% 2 input arguments
	
	% Continue processing next frame of inputs
	% to determine autoscale limits
	
	count = block_data.autoscaling(1);
	ymin  = block_data.autoscaling(2);
	ymax  = block_data.autoscaling(3);
	htext = block_data.autoscaling(4);
	
	if count>0,
		% Continue tracking min and max:
		
		fig_data = get(block_data.hfig, 'UserData');
		blk = fig_data.block;
		
		if (block_data.params.Domain == 2) & (block_data.params.YUnits==2),
			u = lin2dB(u); % Frequency domain, dB
		end
			
		count=count-1;
		ymin=min(ymin,min(u(:)));
		ymax=max(ymax,max(u(:)));
		
		% Update user feedback:
		set(htext,'string',['Autoscale: ' mat2str(count)]);
		
		block_data.autoscaling = [count ymin ymax htext];
		set_param(blk, 'UserData', block_data);
		
	else
		% Finished computing autoscale limits
		
		% Remove autoscale indicator:
		delete(htext);
		htext=[];  % reset so that terminate call deletes an empty handle
		
		% Turn off autoscale flag
		fig_data = get(block_data.hfig, 'UserData');
		blk = fig_data.block;
		
		block_data = get_param(blk,'UserData');
		block_data.autoscaling = [];
		set_param(blk, 'UserData', block_data);
		
		% Adjust ymin and ymax to give a bit of margin:
		ymin=ymin*1.05;
		ymax=ymax*1.05;
		
		% Protect against horizontal lines:
		if (ymax==ymin),
			ymin=floor(ymin-.5);
			ymax=ceil(ymax+.5);
		end
		
		% Indirectly set these via the DialogApply callback:
		SetAndApply(blk, 'YMin',mat2str(ymin), ...
			'YMax',mat2str(ymax), 'eval');
	end
end


% ---------------------------------------------------------------
function CancelAutoscale(blk)

% Cancel any pending autoscale operation

block_data = get_param(blk,'UserData');

% No autoscale operation in progress:
if ~isfield(block_data,'autoscaling') | isempty(block_data.autoscaling),
	return;
end

htext = block_data.autoscaling(4);
delete(htext);
block_data.autoscaling=[];
set_param(blk,'UserData', block_data);


% ---------------------------------------------------------------
function oneshot_autoscale(hfig)
% ONESHOT_AUTOSCALE Used when simulation is stopped
%   Cannot use multi-frame autoscale, since the simulation is no longer
%   running.  Instead, we compute a one-time ymin/ymax computation, and
%   apply it to the static scope result.

fig_data = get(hfig, 'UserData');
blk = fig_data.block;

% Get data for each line, and find min/max:
hline = fig_data.main.hline;
ymin=inf; ymax=-inf;
for i=1:length(hline),
	y = get(hline(i),'ydata');
	ymin = min(ymin, min(y)) * 1.05;
	ymax = max(ymax, max(y)) * 1.05;
end

% Protect against horizontal lines:
if (ymax==ymin),
	ymin=floor(ymin-.5);
	ymax=ceil(ymax+.5);
end

% Indirectly set these via the DialogApply callback:
SetAndApply(blk, 'YMin',mat2str(ymin), ...
	'YMax',mat2str(ymax), 'eval');


% ---------------------------------------------------------------
function sys = mdlTerminate
% TERMINATE Clean up any remaining items

sfcn = gcb;
blk  = get_param(sfcn,'parent');
block_data = get_param(blk,'UserData');

% Cancel any pending autoscale operation:
CancelAutoscale(blk);

% Redraw all lines in "normal" mode; when lines redraw over themselves
% in "xor" mode, dots are left at peaks without lines connecting to them.
% This can be visually misleading.
terminateLineEraseMode(blk);

sys = [];  % No states to return


% ---------------------------------------------------------------
function FigRefresh(hcb,eventStruct,hfig)
% Refresh display while memory turned on

if nargin<3, hfig=gcbf; end
if ~isempty(hfig),
	refresh(hfig);
end


% ------------------------------------------------------------
function UpdateGrid(blk)
% UpdateGrid Draw scope grid for frame scope.

block_data = get_param(blk,'UserData');
hax        = block_data.haxis;
hgrid      = block_data.hgrid;   
hgridtext  = block_data.hgridtext;

% Determine if compact display (axis zoom) mode is on:
fig_data = get(block_data.hfig, 'UserData');
haxzoom  = fig_data.menu.axiszoom;
isZoom   = strcmp(get(haxzoom,'Checked'),'on');

ltgray  = ones(1,3)*.8;  % light gray for axis lines
ltgrayt = ones(1,3)*.6;  % slightly darker for grid labels

xtick = get(hax,'xtick'); xn=length(xtick);
ytick = get(hax,'ytick'); yn=length(ytick);
xlim  = get(hax,'xlim');
ylim  = get(hax,'ylim');

% Render the tick label values only in compact display mode:
use_xlabels = isZoom;
use_ylabels = isZoom;
% Select grid options:
isFreq = (block_data.params.Domain==2);
% Don't use centerline ticks in the Frequency domain plots:
centerline_ticks = ~isFreq;

% Major axis lines
% ----------------

% - vert lines
y=[ylim NaN]; y=repmat(y,1,xn);
xnan = NaN; xnan=xnan(ones(xn,1));
x=[xtick' xtick' xnan]'; x=x(:)';
x1=x; y1=y;

% - horiz lines
x=[xlim NaN]; x=repmat(x,1,yn);
ynan=NaN; ynan=ynan(ones(yn,1));
y=[ytick' ytick' ynan]'; y=y(:)';
x2=x; y2=y;

if centerline_ticks,
	% Create centerline tick marks:
	
	xmajor = min(diff(xtick)); xminor=xmajor/5;
	ymajor = min(diff(ytick)); yminor=ymajor/5;
	
	% Compute axis tick lengths
	% - Normalize tick lengths according to the data aspect ratio
	%   and the ratio of window length to height
	% - Must get out of normalized mode, since we need "absolute"
	%   units to compare in x- and y-dimensions.
	oldUnits=get(hax,'units');
	set(hax,'unit','pix'); p=get(hax,'pos');
	set(hax,'unit',oldUnits);
	
	dar = get(hax,'dataaspectratio');
	ar = dar(2)/dar(1) * p(3)/p(4);
	tylen = yminor/2;
	txlen = tylen/ar;
	
	% - x-axis ticks
	if rem(yn,2)==1,
		% odd # of y-ticks
		ymiddle=ytick((yn+1)/2);
	else
		% even # of y-ticks
		ymiddle=ytick(yn/2);
	end
	
	% NOTE: xlim has nothing to do with where the actual grid
	%     lines fall ... we need to find first minor grid position
	di = floor(abs(xlim(1) - xtick(1)) / xminor);
	xstart = xtick(1) - di * xminor;
	xend = xlim(2);  % fine to do this for xend
	x = xstart : xminor : xend;
	
	% remove grid line positions from x - they won't show up
	x = setdiff(round(x*1e5), round(xtick*1e5)) * 1e-5;
	nx=length(x);
	y=[ymiddle-tylen ymiddle+tylen NaN]; y=repmat(y,1,nx);
	x=[x' x' NaN*ones(nx,1)]'; x=x(:)';
	xc1=x; yc1=y;
	
	% - y-axis ticks
	if rem(xn,2)==1,
		% odd # of x-ticks
		xmiddle=xtick((xn+1)/2);
	else
		% even # of x-ticks
		xmiddle=xtick(xn/2+1);
	end
	
	% NOTE: xlim has nothing to do with where the actual grid
	%     lines fall ... we need to find first minor grid position
	di = floor(abs(ylim(1) - ytick(1)) / yminor);
	ystart = ytick(1) - di * yminor;
	yend = ylim(2);  % fine to do this for xend
	y = ystart : yminor : yend;
	
	% remove grid line positions from y - they won't show up
	y = setdiff(round(y*1e5), round(ytick*1e5)) * 1e-5;
	ny=length(y);
	x=[xmiddle-txlen xmiddle+txlen NaN]; x=repmat(x,1,ny);
	y=[y' y' NaN*ones(ny,1)]'; y=y(:)';
	yc2=y; xc2=x;
	
else
	% No centerline ticks:
	xc1=[]; yc1=[];
	xc2=[]; yc2=[];
	
end

% Always get rid of any existing grid text labels first:
if ishandle(hgridtext),
	delete(hgridtext);
end
hgridtext = [];

% Plot grid:
% ----------
x = [xc1 xc2 x1 x2];
y = [yc1 yc2 y1 y2];
if isempty(hgrid),
	hgrid = line('parent',hax, 'xdata',x, 'ydata',y, 'color',ltgray);
else
	set(hgrid,'xdata',x, 'ydata',y);
end

% Update labels:
% --------------
if use_xlabels | use_ylabels,
	
	xt=get(hax,'xtick');
	yt=get(hax,'ytick');
	if length(xt)==1,
		dx=xt;
	else
		dx=xt(2)-xt(1);
	end
	if length(yt)==1,
		dy=yt;
	else
		dy=yt(2)-yt(1);
	end
	xlim=get(hax,'xlim'); xmin=xlim(1); xmax=xlim(2);
	ylim=get(hax,'ylim'); ymin=ylim(1); ymax=ylim(2);
	
	if use_ylabels,
		% Add new y-axis labels INSIDE grid
		
		% Remove first and last ticks if they are at start/end of ylimits
		ytt=yt;
		if isApproxEqual(ymin, ytt(1)),
			ytt(1)=[];
		end
		if isApproxEqual(ymax, ytt(end)),
			ytt(end)=[];
		end
		
		% Determine vertical text label placements
		ytxt = ytt;  % add a little bit so we can see it over the tick line?
		% start 10 percent of the way between the first x-ticks
		xtxt = xmin + dx*0.05;
		xtxt = xtxt(ones(size(ytxt)));
		
		% Determine vertical text strings:
		str={};
		for i=1:length(ytt),
			str{i}=sprintf('%+g',ytt(i));
		end
		hytext = text(xtxt,ytxt,str, ...
			'vert','base', ...
			'color',ltgrayt, 'parent',hax);
	else
		hytext=[];
	end
	
	if use_xlabels,
		% Add new x-axis labels INSIDE grid
		
		% Remove first and last ticks if they are at start/end of ylimits
		xtt=xt;
		if isApproxEqual(xmin,xtt(1)),
			xtt(1)=[];
		end
		if isApproxEqual(xmax,xtt(end)),
			xtt(end)=[];
		end
		
		xtxt = xtt;
		ytxt = ymin + dy*0.05;
		ytxt = ytxt(ones(size(xtxt)));
		str={};
		for i=1:length(xtt),
			str{i}=sprintf('%g',xtt(i));  % don't use + sign
		end
		hxtext = text(xtxt,ytxt,str, ...
			'color',ltgrayt,'horiz','center','vert','bottom',...
			'parent',hax);
	else
		hxtext = [];
	end
	
	% Store all text handles:
	hgridtext = [hxtext;hytext];
end

% Check AxisGrid setting, and make invisible if necessary:
%
set([hgrid;hgridtext],'vis', get_param(blk,'AxisGrid'));

% Reassign context menu to the grid:
fig_data = get(block_data.hfig,'UserData');
set([hgrid;hgridtext], 'UIContextMenu', fig_data.menu.context);

% Always store the grid text handle vector (even if empty):
block_data.hgrid     = hgrid;
block_data.hgridtext = hgridtext;
set_param(blk,'UserData',block_data);

% Set parent figure color to ltgray:
hfig=get(hax,'parent');
set(hfig,'color',ltgray);


% ------------------------------------------------------------
function names = getInputSignalNames(blk)
% getInputSignalNames
% Return cell-array of strings, one string per input.

% Scope only accepts on input port.
% A single channel is an input vector (1-D or 2-D)
% Multiple channels is a matrix input (2-D)
%
% If single channel,
%   - get name of the input signal
%
% If multiple channels,
%   If input is named,
%       - get name of input signal as base name
%         if non-empty, concatenate with "CH #"
%   If input is not named,
%     If driven by a Mux or a Matrix Concat block,
%         - get name of each signal driving mux
%
% Assign a default name "CH #" to each channel with a blank name.

block_data = get_param(blk,'UserData');
nchans = block_data.NChans;

if nchans==1,
	% Only one input port - 1-D or 2-D vector input
	%
	% Only one name will appear, if specified:
	names = get_param(blk,'InputSignalNames');
	
else
	% Multiple channels - matrix input
	
	% Populate return array with empty strings:
	str = {''};
	names = str(ones(1,nchans));
	
	% Get name of input matrix:
	baseName = get_param(blk,'InputSignalNames');
	
	% If it's empty, that's that - we live with default names
	if ~isempty(baseName{1}),
		% Input is named - concatenate channel # to base name
		if ~isempty(baseName),
			for i=1:length(names),
				names{i} = [baseName{1} ' - CH ' num2str(i)];
			end
		end
	else
		% Input matrix is not named
		%
		% If input is a mux or a matrix concatenation block,
		% get the names of its inputs:
		pc    = get_param(blk,'portconnectivity');
		isMC  = strcmp(get_param(pc.SrcBlock,'masktype'), 'Matrix Concatenation');
		isMux = strcmp(get_param(pc.SrcBlock,'blocktype'),'Mux');
		if isMC | isMux,
			% Driven by a matrix concat block or a mux - get names of input ports:
			driver_blk = pc.SrcBlock;
			names = get_param(driver_blk,'InputSignalNames');
			
			% Verify that we have nchans number of signal names:
			num=length(names);
			if num < nchans,
				% Could have been a mux of muxes, etc.
				% Don't follow this path any longer.
				str={''};
				names = str(ones(1,nchans));
			elseif num > nchans,
				% Too many inputs to mux/cat block - just truncate names array:
				names = names(1:nchans);
			end
		end
	end
end

% Assign a default name to any unnamed channel:
% Default is 'CH #'
%
for i=1:length(names),
	if isempty(names{i}),
		names{i}=['CH ' num2str(i)];
	end
end

% ------------------------------------------------------------
function z = isApproxEqual(x,y)

% different if difference in the two numbers
% is more than 1 percent of the maximum of
% the two:
tol = max(abs(x),abs(y)) * 1e-2;
z = (abs(x-y) < tol);


% ---------------------------------------------------------------
function nEle = GetNumberOfElements(x)
nEle = prod(size(x));


% ---------------------------------------------------------------
function n = numDecimalDigits(x)
n = ceil(log10(1+floor(abs(x))));


% ---------------------------------------------------------------
function y = isOn(x)
y = strcmp(x,'on');


% ---------------------------------------------------------------
function y = lin2dB(x)
% Fast way to do "y = 10*log10(x)"
% We would like an input x>0 always, but we can't guarantee that.
% So we protect ourselves with the following:
%  - taking the absolute value of the input
%  - adding an eps-offset to combat zero-valued inputs
y = 10./log(10) .* log(abs(x)+eps);
		

% ---------------------------------------------------------------
function y = dB2lin(x)
y = 10.^(x./10) - eps;
        

% ------------------------------------------------------------
% [EOF] xferplot.m