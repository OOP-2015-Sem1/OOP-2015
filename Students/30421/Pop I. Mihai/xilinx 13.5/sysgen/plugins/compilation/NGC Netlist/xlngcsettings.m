function varargout = xlngcsettings(varargin)
% XLNGCSETTINGS M-file for xlngcsettings.fig
%      XLNGCSETTINGS, by itself, creates a new XLNGCSETTINGS or raises the existing
%      singleton*.
%
%      H = XLNGCSETTINGS returns the handle to a new XLNGCSETTINGS or the handle to
%      the existing singleton*.
%
%      XLNGCSETTINGS('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in XLNGCSETTINGS.M with the given input arguments.
%
%      XLNGCSETTINGS('Property','Value',...) creates a new XLNGCSETTINGS or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before xlngcsettings_OpeningFunction gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to xlngcsettings_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help_button xlngcsettings

% Last Modified by GUIDE v2.5 11-Mar-2005 17:37:51

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @xlngcsettings_OpeningFcn, ...
                   'gui_OutputFcn',  @xlngcsettings_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
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


% --- Executes just before xlngcsettings is made visible.
function xlngcsettings_OpeningFcn(hObject, eventdata, handles, varargin)
    
    % Register this compilation target with sgtoken, so that if it is forced
    % to close it will also close the compilation target settings.
    xlRegCompTargetsForClosereq;

    % Choose defaults command line output for xlngcsettings
    handles.output = hObject;
    handles.ok = 0;

    if (isempty(varargin))
        return;
    end

    if (isfield(varargin{1}, 'ngc_config'))
        % -- Extract saved parameters from block.
        ngc_config = varargin{1}.ngc_config;
    else
        % -- Set config defaults.
        ngc_config.include_clockwrapper = 1;
        ngc_config.include_cf = 1;
        xlsetparamws(varargin{1}.block, 'work', 'ngc_config', ngc_config);
    end

    % -- Configure with settings.
    set(handles.include_clockwrapper_checkbox, 'Value', ...
        ngc_config.include_clockwrapper);

    if (~ngc_config.include_clockwrapper)
        % -- Disable include constraints file checkbox.
        set(handles.include_cf_checkbox, 'Value', 0);
        set(handles.include_cf_checkbox, 'Enable', 'off');
    else
        set(handles.include_cf_checkbox, 'Value', ...
            ngc_config.include_cf);
        set(handles.include_cf_checkbox, 'Enable', 'on');
    end

    % -- Add entries to handles.
    handles.block = varargin{1}.block;
    handles.include_cf_cached = ngc_config.include_cf;

    % Update handles structure.
    guidata(hObject, handles);

    % UIWAIT makes xlngcsettings wait for user response (see UIRESUME).
    uiwait(handles.figure1);

% --- Outputs from this function are returned to the command line.
function varargout = xlngcsettings_OutputFcn(hObject, eventdata, handles)
    % return 0 for okay status, 1 for erro.
    varargout{1} = 0;

% --- Executes on button press in ok_button.
function ok_button_Callback(hObject, eventdata, handles)

    % Get current saved parameters.
    ngc_config = xlgetparamws(handles.block, 'work', 'ngc_config');

    % Get current GUI settings.
    ngc_config.include_clockwrapper = ...
        get(handles.include_clockwrapper_checkbox, 'Value');
    if (ngc_config.include_clockwrapper)
        ngc_config.include_cf = ...
            get(handles.include_cf_checkbox, 'Value');
    end

    % Save new configuration.
    xlsetparamws(handles.block, 'work', 'ngc_config', ngc_config);

    handles.ok = 1;
    % Update handles structure
    guidata(hObject, handles);

    uiresume(handles.figure1);
    close(handles.figure1);

% --- Executes on button press in cancel_button.
function cancel_button_Callback(hObject, eventdata, handles)
    handles.ok = 0;
    % Update handles structure
    guidata(hObject, handles);
    uiresume(handles.figure1);
    close(handles.figure1);

% --- Executes on button press in import_top_level_include_cf_checkbox.
function include_clockwrapper_checkbox_Callback(hObject, eventdata, handles)

    include_clockwrapper = ...
        get(handles.include_clockwrapper_checkbox, 'Value');
    if (~include_clockwrapper)
        % -- Cache constraints file checkbox value.
        handles.include_cf_cached = ...
            get(handles.include_cf_checkbox, 'Value');
        % -- Update handles structure.
        guidata(hObject, handles);
        % -- Disable include constraints file checkbox.
        set(handles.include_cf_checkbox, 'Value', 0);
        set(handles.include_cf_checkbox, 'Enable', 'off');
    else
        % -- Enable constraints file checkbox with cached setting.
        set(handles.include_cf_checkbox, 'Value', ...
            handles.include_cf_cached);
        set(handles.include_cf_checkbox, 'Enable', 'on');
    end


% --- Executes on button press in help_button.
function help_button_Callback(hObject, eventdata, handles)
    xlDoc('-book','sysgen','-title','NGC Netlist Compilation');


% --- Executes on button press in include_cf_checkbox.
function include_cf_checkbox_Callback(hObject, eventdata, handles)
% hObject    handle to include_cf_checkbox (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: get(hObject,'Value') returns toggle state of include_cf_checkbox


