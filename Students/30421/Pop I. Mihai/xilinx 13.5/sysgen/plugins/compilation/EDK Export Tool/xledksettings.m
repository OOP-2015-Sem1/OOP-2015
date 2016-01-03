function varargout = xledksettings(varargin)
% XLEDKSETTINGS M-file for xledksettings.fig
%      XLEDKSETTINGS, by itself, creates a new XLEDKSETTINGS or raises the existing
%      singleton*.
%
%      H = XLEDKSETTINGS returns the handle to a new XLEDKSETTINGS or the handle to
%      the existing singleton*.
%
%      XLEDKSETTINGS('Property','Value',...) creates a new XLEDKSETTINGS using the
%      given property value pairs. Unrecognized properties are passed via
%      varargin to xledksettings_OpeningFcn.  This calling syntax produces a
%      warning when there is an existing singleton*.
%
%      XLEDKSETTINGS('CALLBACK') and XLEDKSETTINGS('CALLBACK',hObject,...) call the
%      local function named CALLBACK in XLEDKSETTINGS.M with the given input
%      arguments.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Last Modified by GUIDE v2.5 19-Sep-2007 14:52:47

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @xledksettings_OpeningFcn, ...
                   'gui_OutputFcn',  @xledksettings_OutputFcn, ...
                   'gui_LayoutFcn',  [], ...
                   'gui_Callback',   []);
if nargin & isstr(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before xledksettings is made visible.
function xledksettings_OpeningFcn(hObject, eventdata, handles, varargin)

% Register this compilation target with sgtoken, so that if it is forced
% to close it will also close the compilation target settings.
xlRegCompTargetsForClosereq;

handles.block=varargin{1}.block;
handles.targetdir=varargin{1}.directory;
handles.exportdirpath='';
if (isfield(varargin{1},'xledksettingsdata'))
  edkdata=varargin{1}.xledksettingsdata;
  set(handles.export,'Value',edkdata.export);
  set(handles.exportdir,'String',edkdata.exportdir);
  switch (edkdata.selectiontag)
    case 'target_directory'
      set(handles.target_directory,'Value',1);
    case 'export'
      set(handles.export,'Value',1);
      set(handles.browse,'Enable','on');
      set(handles.exportdir,'Enable','on');
    otherwise
      set(handles.target_directory,'Value',1);
  end
  
  set(handles.major, 'String', num2str(edkdata.major));
  set(handles.minor, 'String', num2str(edkdata.minor, '%02.0f'));
  set(handles.hw_compatibility, 'String', edkdata.hw_compatibility);
  set(handles.maj_slider, 'Value', edkdata.maj_slider);
  set(handles.minor_slider, 'Value', edkdata.minor_slider);
  set(handles.hw_compatibility_slider, 'Value', edkdata.hw_compatibility_slider);
  
  if isfield(edkdata,'isDevelopment')
    set(handles.isDevelopment,'Value',edkdata.isDevelopment);
  else
    set(handles.isDevelopment,'Value',0);
  end

  if isfield(edkdata,'useCustomBusInterface')
    set(handles.useCustomBusInterface,'Value',edkdata.useCustomBusInterface);
    if edkdata.useCustomBusInterface
      set(handles.launchBusInterfaceGUI,'Enable','on');
    else
      set(handles.launchBusInterfaceGUI,'Enable','off');
    end
    handles.customBusInterfaceValue = edkdata.customBusInterfaceValue;
  else
    set(handles.useCustomBusInterface,'Value',0);
    set(handles.launchBusInterfaceGUI,'Enable','off');
    handles.customBusInterfaceValue = [];
  end
else
  handles.customBusInterfaceValue = [];
end

% Update handles structure
guidata(hObject, handles);
% -- draw icons
xlSetIcon(handles.browse,2);
% UIWAIT makes xledksettings wait for user response (see UIRESUME)
uiwait(hObject);

% --- Outputs from this function are returned to the command line.
function varargout = xledksettings_OutputFcn(hObject, eventdata, handles)
delete(handles.figure1);
varargout{1}=0;

% --- Executes when user attempts to close figure1.
function figure1_CloseRequestFcn(hObject, eventdata, handles)
 uiresume(handles.figure1);

% --- Executes on 'EDK project location' is changed
function exportdir_Callback(hObject, eventdata, handles)


% --- Executes on button press in ok.
function ok_Callback(hObject, eventdata, handles)

edkdata.export= get(handles.export,'Value');
edkdata.exportdir=get(handles.exportdir,'String');
selectionobj = get(handles.radiopanel,'SelectedObject');
edkdata.selectiontag = get(selectionobj,'tag');
switch (edkdata.selectiontag)
  case 'export'
      % use the parent directory of the selected XMP file
      edkdata.exportdirpath = fileparts(get(handles.exportdir, 'String'));
  otherwise
      % otherwise, put an empty string so that the netlister would export to
      % the netlist directory
      edkdata.exportdirpath = '';
end

edkdata.major = str2num(get(handles.major,'String'));
edkdata.minor = str2num(get(handles.minor,'String'));
edkdata.hw_compatibility = get(handles.hw_compatibility,'String');
edkdata.maj_slider = get(handles.maj_slider,'Value');
edkdata.minor_slider = get(handles.minor_slider,'Value');
edkdata.hw_compatibility_slider = get(handles.hw_compatibility_slider,'Value');
edkdata.isDevelopment = get(handles.isDevelopment,'Value');
edkdata.useCustomBusInterface = get(handles.useCustomBusInterface,'Value');
edkdata.customBusInterfaceValue = handles.customBusInterfaceValue;

% Set compilation path
xlsetparam(handles.block,'xledksettingsdata',edkdata);
xlsetparamws(handles.block,'work','xledksettingsdata',edkdata);
close(handles.figure1);


% --- Executes on button press in Cancel.
function Cancel_Callback(hObject, eventdata, handles)
close(handles.figure1);
 

% --- Executes during object creation, after setting all properties.
function exportdir_CreateFcn(hObject, eventdata, handles)
if ispc
    set(hObject,'BackgroundColor','white');
else
    set(hObject,'BackgroundColor',get(0,'defaultUicontrolBackgroundColor'));
end


% --- Executes on button press in browse.
% --- If exportdir  is an empty string, start from the sysgen netlist dir
function browse_Callback(hObject, eventdata, handles)
currentFile = xlGetAbsoluteFilename(get(handles.exportdir,'String'));
currentDir = pwd;
if (exist(currentFile) == 2)
    % Move the current specified file directory
    [path, name, ext] = fileparts(currentFile);   
else
    % Default to mdl file directory
    path = xlGetAbsolutePath('.',gcs);
end;
cd(path);
[filename, pathname] = uigetfile('*.xmp', 'Pick the Xilinx Platform Studio project to export to ...');
if ~(isequal(filename,0) | isequal(pathname,0))
    set(handles.exportdir,'String',[pathname filename]);
    handles.exportdirpath=pathname;
end
cd(currentDir);
guidata(hObject, handles);


% --- Executes on button press in help.
function help_Callback(hObject, eventdata, handles)
% hObject    handle to help (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

xlDoc('-book','sysgen','-title','EDK Export Tool');


% --------------------------------------------------------------------
function radiopanel_SelectionChangeFcn(hObject, eventdata, handles)
% hObject    handle to radiopanel (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

enable = get(handles.export,'Value');

if (enable)
    set(handles.browse,'Enable','on');
    set(handles.exportdir,'Enable','on');
else
    set(handles.browse,'Enable','off');
    set(handles.exportdir,'Enable','off');
end



function major_Callback(hObject, eventdata, handles)
% hObject    handle to major (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of major as text
%        str2double(get(hObject,'String')) returns contents of major as a double


% --- Executes during object creation, after setting all properties.
function major_CreateFcn(hObject, eventdata, handles)
% hObject    handle to major (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes during object creation, after setting all properties.
function minor_CreateFcn(hObject, eventdata, handles)
% hObject    handle to minor (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

% --- Executes during object creation, after setting all properties.
function hw_compatibility_CreateFcn(hObject, eventdata, handles)
% hObject    handle to hw_compatibility (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on slider movement.
function maj_slider_Callback(hObject, eventdata, handles)
% hObject    handle to maj_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider

set(handles.major,'String',num2str(ceil(get(hObject,'Value'))));

% --- Executes during object creation, after setting all properties.
function maj_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to maj_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end

% --- Executes on slider movement.
function minor_slider_Callback(hObject, eventdata, handles)
% hObject    handle to minor_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider
val = floor(get(hObject,'Value'));
set(handles.minor, 'String', num2str(val, '%02.0f'));

% --- Executes during object creation, after setting all properties.
function minor_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to minor_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end


% --- Executes on slider movement.
function hw_compatibility_slider_Callback(hObject, eventdata, handles)
% hObject    handle to hw_compatibility_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider

val = floor(get(hObject,'Value'));
set(handles.hw_compatibility,'String',char(val));


% --- Executes during object creation, after setting all properties.
function hw_compatibility_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to hw_compatibility_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end

% --- Executes on button press in useCustomBusInterface.
function useCustomBusInterface_Callback(hObject, eventdata, handles)
% hObject    handle to useCustomBusInterface (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
curState = get(hObject,'Value');
if (curState==1)
  set(handles.launchBusInterfaceGUI,'Enable','on');
else
  set(handles.launchBusInterfaceGUI,'Enable','off');
end
 
% --- Executes on button press in launchBusInterfaceGUI.
function launchBusInterfaceGUI_Callback(hObject, eventdata, handles)
% hObject    handle to launchBusInterfaceGUI (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
parentPos=get(handles.figure1,'Position');
parentMidx = parentPos(1) + round(parentPos(3)/2);
parentMidy = parentPos(2) + round(parentPos(4)/2);

p = xlPCoreExportBusInterfaceGUI('create',handles.customBusInterfaceValue,...
  parentMidx, parentMidy);
if ~isempty(p)
  handles.customBusInterfaceValue=p;
  guidata(handles.figure1, handles);
end


