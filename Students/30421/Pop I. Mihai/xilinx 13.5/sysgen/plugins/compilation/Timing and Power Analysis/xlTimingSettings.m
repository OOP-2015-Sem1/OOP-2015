function varargout = xlTimingSettings(varargin)
% XLTOPLEVELNETLISTGUI M-file for xlTimingSettings.fig
%      XLTOPLEVELNETLISTGUI, by itself, creates a new XLTOPLEVELNETLISTGUI or raises the existing
%      singleton*.
%
%      H = XLTOPLEVELNETLISTGUI returns the handle to a new XLTOPLEVELNETLISTGUI or the handle to
%      the existing singleton*.
%
%      XLTOPLEVELNETLISTGUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in XLTOPLEVELNETLISTGUI.M with the given input arguments.
%
%      XLTOPLEVELNETLISTGUI('Property','Value',...) creates a new XLTOPLEVELNETLISTGUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before xlTimingSettings_OpeningFunction gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to xlTimingSettings_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help xlTimingSettings

% Last Modified by GUIDE v2.5 26-Apr-2009 23:04:47

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @xlTimingSettings_OpeningFcn, ...
                   'gui_OutputFcn',  @xlTimingSettings_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before xlTimingSettings is made visible.
function xlTimingSettings_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to xlTimingSettings (see VARARGIN)

% Register this compilation target with sgtoken, so that if it is forced
% to close it will also close the compilation target settings.
xlRegCompTargetsForClosereq;

% Choose defaults command line output for xlTimingSettings
handles.output = hObject;
handles.ok = 0;

% -- Grab the block handle that is passed in my the SysGen token
handles.block=varargin{1}.block;

% -- Search for a previously saved field that was created
tmp = xlgetfield(varargin{1}, 'custom_settings', struct());
impl_opt_file = xlgetfield(tmp, 'impl_opt_file', '');
trace_opt_file = xlgetfield(tmp, 'trace_opt_file', '');
use_custom_opt_files = ~isempty(impl_opt_file) || ~isempty(trace_opt_file);
power_analysis_mode = xlgetfield(tmp, 'power_analysis_mode', '');

set(handles.xflowImplOptFileEditBox, 'String', impl_opt_file);
set(handles.xflowTraceOptFileEditBox, 'String', trace_opt_file);
set(handles.xflowOptionsFileCheckBox, 'Value', use_custom_opt_files);
if isempty(xlFindMasterAbove(handles.block))
    % Enable full simulation-based power analysis only if invoked by a master SysGen token
    set(handles.powerAnalysisMenu, 'String', 'No analysis|Quick analysis|Full simulation-based analysis');
    set(handles.powerAnalysisMenu, 'Value', getPowerAnalysisMenuIndex(power_analysis_mode, true));
else
    set(handles.powerAnalysisMenu, 'String', 'No analysis|Quick analysis');
    set(handles.powerAnalysisMenu, 'Value', getPowerAnalysisMenuIndex(power_analysis_mode, false));
end

enableXFlowOptionsFilePanel(handles, use_custom_opt_files);


% Update handles structure
guidata(hObject, handles);

% Set Icons for push buttons
xlSetIcon(handles.xflowImplOptFileBrowseButton, 2);
xlSetIcon(handles.xflowTraceOptFileBrowseButton, 2);


% UIWAIT makes xlTimingSettings wait for user response (see UIRESUME)
uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = xlTimingSettings_OutputFcn(hObject, eventdata, handles)
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% return 0 for okay status, 1 for erro.
varargout{1} = 0;


% --- Executes on button press in okButton.
function okButton_Callback(hObject, eventdata, handles)
% hObject    handle to okButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

tmp = struct();

use_custom_opt_files = get(handles.xflowOptionsFileCheckBox, 'Value');
if use_custom_opt_files
    tmp.impl_opt_file = get(handles.xflowImplOptFileEditBox, 'String');
    impl_opt_file = xlGetAbsoluteFilename(tmp.impl_opt_file);
    if (exist(impl_opt_file) ~= 2)
        errordlg(['The .opt file for the FPGA implementation flow, ' impl_opt_file ', does not exist. Please enter a valid file.']);
        return;
    end

    tmp.trace_opt_file = get(handles.xflowTraceOptFileEditBox, 'String');
    trace_opt_file = xlGetAbsoluteFilename(tmp.trace_opt_file);
    if (exist(trace_opt_file) ~= 2)
        errordlg(['The .opt file for the FPGA timing analysis flow, ' trace_opt_file ', does not exist. Please enter a valid file.']);
        return;
    end
end

testbench_value = 'off';
switch get(handles.powerAnalysisMenu, 'Value')
case 2
    tmp.power_analysis_mode = 'non-simulation';
case 3
    tmp.power_analysis_mode = 'isim-simulation';
    testbench_value = 'on';
end

% Set compilation path
if (isfield(handles, 'block'))
    ws = xlparamconst_work;
    xlsetparamws(handles.block, ws, 'custom_settings', tmp);
    xlsetparamws(handles.block, ws, 'testbench', testbench_value);
    fighdl = xlfindsysgenfig(handles.block);
    xlfig_set(fighdl, 'testbench', testbench_value);
end;

handles.ok = 1;
% Update handles structure
guidata(hObject, handles);

uiresume(handles.figure1);
close(handles.figure1);


% --- Executes on button press in xflowImplOptFileBrowseButton.
function xflowImplOptFileBrowseButton_Callback(hObject, eventdata, handles)
% hObject    handle to xflowImplOptFileBrowseButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
currentFile = xlGetAbsoluteFilename(get(handles.xflowImplOptFileEditBox,'String'));
currentDir = pwd;
if (exist(currentFile) == 2)
    % Move the current specified file directory
    [path, name, ext] = fileparts(currentFile);
    cd(path);
end;

[filename, pathname, filterindex] = uigetfile( ...
       {'*.opt','XFlow options file (*.opt)'}, ...
        'Select a XFlow options file', 'par.opt');
cd(currentDir);

if (filename)
    set(handles.xflowImplOptFileEditBox,'String', [pathname filename]);
end

% --- Executes on button press in cancelButton.
function cancelButton_Callback(hObject, eventdata, handles)
% hObject    handle to cancelButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

handles.ok = 0;
% Update handles structure
guidata(hObject, handles);

uiresume(handles.figure1);
close(handles.figure1);


% --- Executes on button press in xflowTraceOptFileBrowseButton.
function xflowTraceOptFileBrowseButton_Callback(hObject, eventdata, handles)
% hObject    handle to xflowTraceOptFileBrowseButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

currentFile = xlGetAbsoluteFilename(get(handles.xflowTraceOptFileEditBox,'String'));
currentDir = pwd;
if (exist(currentFile) == 2)
    % Move the current specified file directory
    [path, name, ext] = fileparts(currentFile);
    cd(path);
end;

[filename, pathname, filterindex] = uigetfile( ...
       {'*.opt','XFlow options file (*.opt)'}, ...
        'Select a XFlow options file', 'trace.opt');
cd(currentDir);

if (filename)
    set(handles.xflowTraceOptFileEditBox,'String', [pathname filename]);
end

% --- Executes on button press in helpButton.
function helpButton_Callback(hObject, eventdata, handles)
% hObject    handle to helpButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
xlDoc('-book','sysgen','-title','Timing and Power Analysis Compilation');


% --- Executes on selection change in powerAnalysisMenu.
function powerAnalysisMenu_Callback(hObject, eventdata, handles)
% hObject    handle to powerAnalysisMenu (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = get(hObject,'String') returns powerAnalysisMenu contents as cell array
%        contents{get(hObject,'Value')} returns selected item from powerAnalysisMenu


% --- Executes during object creation, after setting all properties.
function powerAnalysisMenu_CreateFcn(hObject, eventdata, handles)
% hObject    handle to powerAnalysisMenu (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes during object creation, after setting all properties.
function xflowImplOptFileEditBox_CreateFcn(hObject, eventdata, handles)
% hObject    handle to xflowImplOptFileEditBox (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

% --- Executes during object creation, after setting all properties.
function xflowTraceOptFileEditBox_CreateFcn(hObject, eventdata, handles)
% hObject    handle to xflowTraceOptFileEditBox (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

function xflowImplOptFileEditBox_Callback(hObject, eventdata, handles)
% hObject    handle to xflowImplOptFileEditBox (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of xflowImplOptFileEditBox as text
%        str2double(get(hObject,'String')) returns contents of xflowImplOptFileEditBox as a double



function xflowTraceOptFileEditBox_Callback(hObject, eventdata, handles)
% hObject    handle to xflowTraceOptFileEditBox (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of xflowTraceOptFileEditBox as text
%        str2double(get(hObject,'String')) returns contents of xflowTraceOptFileEditBox as a double


% --- Executes during object creation, after setting all properties.
function figure1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to figure1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called


% --- Executes on button press in xflowOptionsFileCheckBox.
function xflowOptionsFileCheckBox_Callback(hObject, eventdata, handles)
% hObject    handle to xflowOptionsFileCheckBox (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of xflowOptionsFileCheckBox
enableXFlowOptionsFilePanel(handles, get(hObject, 'Value'));

function enableXFlowOptionsFilePanel(handles, enabled)
if enabled
    uistate = 'on';
else
    uistate = 'off';
end
set(handles.xflowImplOptFileText, 'Enable', uistate);
set(handles.xflowTraceOptFileText, 'Enable', uistate);
set(handles.xflowImplOptFileEditBox, 'Enable', uistate);
set(handles.xflowTraceOptFileEditBox, 'Enable', uistate);
set(handles.xflowImplOptFileBrowseButton, 'Enable', uistate);
set(handles.xflowTraceOptFileBrowseButton, 'Enable', uistate);

function index = getPowerAnalysisMenuIndex(mode, enable_isim)
index = 1;
switch lower(mode)
    case 'non-simulation'
        index = 2;
    case 'isim-simulation'
        if enable_isim
            index = 3;
        end
end
