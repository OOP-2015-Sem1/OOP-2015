function status = xledkprecompile(input_args)
% input_args contains the parameters passed in by sysgen.

err_msg = '';
status = struct('status_code', 0, 'err_msg', '');

try
    check_export_edk_project(input_args);
    
    mdl_handle = get_param(input_args.mdl_root, 'Handle');
    procblock_handle = find_procblock(mdl_handle);

    % 'proc_block_info'
    xlProcBlockCallbacks('set_mdl_global_export', procblock_handle);

    % 'proc_block_settings'
    set_mdl_global_export_settings(input_args);
catch err_msg
    % 'err_msg' should be a string that describes the error, not a struct
    if ~ischar(err_msg)
        err_msg = err_msg.message;
    end
    status = struct('status_code', 8, 'err_msg', err_msg);
end

% ========================================
function set_mdl_global_export_settings(input_args)

% export settings data
export_settings = xlgetfield(input_args, 'xledksettingsdata', struct());

% export directory 
export_dir = xlgetfield(export_settings, 'exportdirpath', '');
% for Windows and Unix compatibility
export_dir = strrep(export_dir, '\', '/');
% hw version
major_version = xlgetfield(export_settings, 'major', 1);
minor_version = xlgetfield(export_settings, 'minor', 0);
hw_compatibility = xlgetfield(export_settings, 'hw_compatibility', 'a');
version = ['v' num2str(major_version) '_' num2str(minor_version, '%02.0f') '_' hw_compatibility];

% development 
is_development = xlgetfield(export_settings, 'isDevelopment', false);

% custom bus interface
use_custom_bus_iface = xlgetfield(export_settings, 'useCustomBusInterface', false);

if use_custom_bus_iface && isfield(export_settings, 'customBusInterfaceValue')
    custom_bus_iface = export_settings.customBusInterfaceValue;
else
    custom_bus_iface = struct();
end

% check EDK preferences
custom_bus_iface_prefs = xlProcBlockUtils('get_edk_prefs', 'custom_bus_iface');
if isempty(custom_bus_iface_prefs)
    % obtain from EDK settings GUI
    custom_bus_iface = custom_bus_iface_marshaling(custom_bus_iface);
else
    % obtain from preferences
    custom_bus_iface = custom_bus_iface_prefs;
end

proc_block_settings = struct('export_dir', export_dir, ...
                             'version', version, ...
                             'is_development', is_development, ...
                             'custom_bus_iface', custom_bus_iface);

xlsetenv(xlmdlh, xlmdlh, 'EDK', 'proc_block_export_settings', proc_block_settings);

% ========================================
function custom_bus_iface = custom_bus_iface_marshaling(custom_bus_iface)

% buses
orig_bus_iface = xlgetfield(custom_bus_iface, 'bi', {});

bus_names = xlgetfield(orig_bus_iface, 'col1', {});
bus_stds = xlgetfield(orig_bus_iface, 'col2', {});
bus_types = xlgetfield(orig_bus_iface, 'col3', {});

bus_iface = {};
for i = 1:length(bus_names)
    bus_iface{end+1} = {bus_names{i}, bus_stds{i}, bus_types{i}};
end
    
% ports on buses
orig_ports = xlgetfield(custom_bus_iface, 'port', {});

gw_names = xlgetfield(orig_ports, 'col1', {});
port_names = xlgetfield(orig_ports, 'col2', {});
bus_names = xlgetfield(orig_ports, 'col3', {});

ports = {};
for i = 1:length(gw_names)
    ports{i} = {gw_names{i}, port_names{i}, '', bus_names{i}};
end

custom_bus_iface = struct();
custom_bus_iface.bus_iface = bus_iface;
custom_bus_iface.ports = ports;

% ========================================
function procblock_handle = find_procblock(mdl_handle)

%% Check EDK Processor block
procblocks = find_system(xlmdlh(mdl_handle), 'LookUnderMasks', 'all', 'block_type', 'edkprocessor');
if ~isempty(procblocks) && ~iscell(procblocks)
    procblocks = { procblocks; }; 
end
num_procblock = length(procblocks);
 
if num_procblock > 1
    error(['Multiple EDK Processor blocks were found. ' ...
           'When exporting a pcore, only one EDK processor is allowed.']);
elseif num_procblock == 0
    error('No EDK Processor block is found in the design');
elseif num_procblock == 1
    procblock_handle = procblocks{1};
    mode = get_param(procblock_handle, 'mode');

    if ~strcmp(mode, 'EDK pcore generation')
        error('EDK Processor block was not set to ''EDK pcore generation'' mode.');
    end
end

% ========================================
function check_export_edk_project(input_args)

% -- See user has given a user setting
if ~isfield(input_args, 'xledksettingsdata'); return; end
xledksettingsdata = input_args.xledksettingsdata;

selectiontag = xlgetfield(xledksettingsdata, 'selectiontag', '');
exportdir = xlgetfield(xledksettingsdata, 'exportdir', '');

if strcmpi(selectiontag, 'export') && ~exist(exportdir, 'file')
    error(['The EDK project (''' exportdir ''') to export to cannot be found.' char(10) ...
           'Check the settings of the compilation target.']);
end


