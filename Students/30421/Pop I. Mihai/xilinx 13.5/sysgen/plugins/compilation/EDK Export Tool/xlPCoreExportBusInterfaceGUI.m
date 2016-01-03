function [ varargout ] = xlPCoreExportBusInterfaceGUI( varargin )
if nargin && isstr(varargin{1})  
  thecmd = (varargin{1});
  varargin(1)='';
else
  % if first argument is a number, assume it is a call back, so
  % first arg = hdl, 2nd arg = event,
  if (nargin>2) && isstr(varargin{3})
    thecmd = (varargin{3});
  end
  hdl=varargin{1};
  event=varargin{2};
  varargin(1)=[];
  varargin(1)=[];
  varargin(1)=[];
end
theCallback = str2func(thecmd);
  
% evaluate the function handle
if nargout
    [varargout{1:nargout}] = feval(theCallback, varargin{:});
else
  if strcmp(thecmd,'validateEvent')
    feval(theCallback, varargin{:}, event);
  else
    feval(theCallback, varargin{:});
  end
end


% -------------------------------------------------------------------------
%{
function setDirData(tablehdl,t)
% The dir colum is column 4 (counting from 0 in Java)
dircol = 4;
if ~isfield(t,'direction')
  return;
end
for l = 0:length(t.direction)-1
  if ~isempty(deblank(t.direction{l+1}))
    tablehdl.getTableModel.setValueAt(java.lang.String(t.direction{l+1}),l,dircol);
  else
    tablehdl.getTableModel.setValueAt('',l,dircol);
  end
end
%}


%% ------------------------------------------------------------------------
%% UI action Callbacks
%% ------------------------------------------------------------------------
function myCloseRequest(hdl)
d = guidata(hdl);
if isfield(d,'attemptedToCloseOnce')
  % something gone wrong, exit
  closereq;
  return;
else
  d.attemptedToCloseOnce=1;
  guidata(hdl,d);
end

uiresume(hdl);

% -------------------------------------------------------------------------
function add_callback(hdl)
hdl.getTableModel.addRow({'','','','',''});

% -------------------------------------------------------------------------
function del_callback(hdl)
row=hdl.getTable.getSelectedRow;
if (row<0)
  return;
end
hdl.getTableModel.removeRow(row);

% -------------------------------------------------------------------------
function cancel_callback(hdl)
set(hdl,'UserData',1);
close(hdl);

% -------------------------------------------------------------------------
function ok_callback(hdl)
% validate all parameters 

d = guiData(hdl);

% Ensure that editing of cell is completed
if ((d.bitbl.getTable.isEditing) || (d.ptbl.getTable.isEditing))
  updateStatus(d.status,'Please complete editing of a table cell before continuing.');
  return;
end

% For some reason t.getNumRows and t.getTable.getRowCount don't return the
% same numbers... not sure why.
for l=0:1
  if l==0
    t=d.bitbl;
  else
    t=d.ptbl;
  end
  for row= 0:(t.getTable.getRowCount-1)
    for col= 0:(t.getTable.getColumnCount-1)
      err=validateOneCell(hdl,l,row,col);
      if err
        return;
      end
    end
  end
end

set(hdl,'UserData',0);
close(hdl);


%% ------------------------------------------------------------------------
%% UI Create, resize and modification
%% ------------------------------------------------------------------------
%% ---------------------------------------------------------------
function t=create(t,pmx,pmy)
% Store table in an array of struct. Index 1 of each array correspond to
% row 1 of a table. And each field in the struct represents a column.
% e.g. 
% t.gatewaynm = {'myport','port1','port2'};
% t.bifacenm  = {'bus1','bus2','bus3'};
% t.busnm     = {'mybus','mybus','mybus2'};

if (nargin<1)
t.port.col1 = {'myport','port1','port2'};
t.port.col2 = {'bus1','bus2','bus3'};
t.port.col3 = {'mybus','mybus','mybus2'};
t.bi.col1   = {'mybus23','mybuds2'};
t.bi.col2   = {'mybusstd','mybusstd2'};
t.bi.col3   = {'Slave','Master'};
else
  if isempty(t)
    t = returnEmptyStruct;
  end
end

% Set WindowStyle to normal to enable line by line debug

% position figure
if (nargin>2)
  % default w=360, h=260
  pos = [pmx-180 pmy-100 360 260];
else
  pos = [200 200 360 260];
end

% make tableDlg
dlg = figure(...
  'Name','Bus Interface',...
  'NumberTitle','off', ...
  'Color', get(0,'defaultUicontrolBackgroundColor'), ...
  'Position', pos, ...
  'DockControls', 'off',...
  'WindowStyle', 'normal',...
  'MenuBar','none',...
  'Toolbar','none'...
  );

movegui(dlg,'onscreen');

% -- Create the two tables --
[gdata.bitbl, gdata.ptbl]=makeTable(dlg,t);

set(dlg,'CloseRequestFcn',{@xlPCoreExportBusInterfaceGUI,'myCloseRequest',dlg});
set(dlg,'ResizeFcn', {@xlPCoreExportBusInterfaceGUI,'resizeDlg',dlg});

%Set Label units to Character so it will scale with character size. 
labelstr = 'Bus Interface ';
gdata.bilabel = uicontrol(dlg,'Style','text',...
  'String',labelstr,...
  'Units','characters',...
  'TooltipString','BUS_INTERFACE BUS=Bus Name, BUS_STD=Bus Standard, BUS_TYPE=Bus Type ',...
  'Position',[1,1,length(labelstr)+20,1],...
  'HorizontalAlignment','left'...
  );
set(gdata.bilabel,'Units','pixels');
lpos = get(gdata.bilabel,'position');
labelWd(lpos(3));
labelHt(lpos(4));
gdata.plabel = uicontrol(dlg,'Style','text',...
  'String','Port-Bus Mapping',...
  'TooltipString','PORT Gateway Name = Bus Interface Name, BUS=Bus Name',...
  'Position',[1,1,labelWd, labelHt],'HorizontalAlignment','left');

gdata.biaddbut = uicontrol(dlg,...
  'Style','pushbutton',...
  'Callback', {@xlPCoreExportBusInterfaceGUI,'add_callback',gdata.bitbl},...
  'Position',[1 1 smbutSz smbutSz]);
addIcon(gdata.biaddbut,'add');
gdata.bidelbut = uicontrol(dlg,...
  'Style','pushbutton',...
  'Callback', {@xlPCoreExportBusInterfaceGUI,'del_callback',gdata.bitbl},...
  'Position',[25 1 smbutSz smbutSz]);
addIcon(gdata.bidelbut,'delete');

gdata.paddbut = uicontrol(dlg,...
  'Style','pushbutton',...
  'Callback', {@xlPCoreExportBusInterfaceGUI,'add_callback',gdata.ptbl},...
  'Position',[1 1 smbutSz smbutSz]);
addIcon(gdata.paddbut,'add');
gdata.pdelbut = uicontrol(dlg,...
  'Style','pushbutton',...
  'Callback', {@xlPCoreExportBusInterfaceGUI,'del_callback',gdata.ptbl},...
  'Position',[25 1 smbutSz smbutSz]);
addIcon(gdata.pdelbut,'delete');

gdata.status = uicontrol(dlg,...
  'Units','characters','Position',[1 1 10 2],...
  'Style','text','ForegroundColor',[0.9 0 0]);
set(gdata.status,'Units','pixels');

gdata.ok = uicontrol(dlg,...
  'String','Ok',...
  'Style','pushbutton',...
  'Callback', {@xlPCoreExportBusInterfaceGUI,'ok_callback',dlg});
gdata.cancel = uicontrol(dlg,...
  'String','Cancel',...
  'Style','pushbutton',...
  'Callback', {@xlPCoreExportBusInterfaceGUI,'cancel_callback',dlg});

% Save handle data for later.
guidata(dlg,gdata);
drawnow;
resizeDlg(dlg);
updatePortTableBusMenu(gdata.bitbl,gdata.ptbl);
setPortTableBus(gdata.ptbl,t.port.col3,gdata.status);
initBusIfaceTable(gdata.bitbl,t.bi.col3);

% If UserData is 1, we assume abort. Ok_call back sets this to 0.
set(dlg,'UserData',1);

uiwait(dlg);
try
  abort=get(dlg,'UserData');
  t=[];
  if abort
    t=[];
  else
    t.bi=tableData2Struct(gdata.bitbl);
    t.port=tableData2Struct(gdata.ptbl);
  end
catch
  t = [];
end

delete(dlg);

% -------------------------------------------------------------------------
function resizeDlg(dlg)
data  = guidata(dlg);
p = get(dlg,'Position');
dlgWd = p(3);
dlgHt = p(4);

% Status is the text area where errs are reported
p = get(data.status,'Position');
statusHt = p(4);

% Bot panel includes status and buttons.
botPanel = statusHt + smbutSz + 2*borderSz;

% table sizes - top table 1/3: bot table 2/3
tWd = dlgWd - 2*borderSz - smbutSz - borderSz;
tHt = round((dlgHt - 3*borderSz - 2*labelHt - botPanel)/3);
tX  = borderSz+2;

smbutX  = borderSz+2+tWd+borderSz;
addbutY = dlgHt-smbutSz-borderSz-labelHt;
delbutY = addbutY-smbutSz-borderSz;

x = borderSz+2;

% Bus interface label and table
curY = dlgHt-borderSz-labelHt;
set(data.bilabel,'Position',[x,curY,labelWd,labelHt]);
curY = curY-tHt;
set(data.bitbl,'Position',[x,curY,tWd,tHt]);

set(data.biaddbut,'Position',[smbutX, addbutY, smbutSz, smbutSz]);
set(data.bidelbut,'Position',[smbutX, delbutY, smbutSz, smbutSz]);

% Port mapping label and table
curY = curY-borderSz-labelHt;
set(data.plabel,'Position',[x,curY,labelWd,labelHt]);
addbutY=curY - smbutSz;
delbutY=addbutY-smbutSz-borderSz;
curY = curY-2*tHt;
set(data.ptbl,'Position',[x,curY,tWd,2*tHt]);

set(data.paddbut,'Position',[smbutX, addbutY, smbutSz, smbutSz]);
set(data.pdelbut,'Position',[smbutX, delbutY, smbutSz, smbutSz]);

% status text

curY=curY-borderSz-statusHt;
statusWd = dlgWd - 2*borderSz;
set(data.status,'Position',[tX,curY,statusWd,statusHt]);

butWd = 65;
butHt = smbutSz;
set(data.ok,'Position',[borderSz,borderSz,borderSz+butWd, borderSz+butHt]);
set(data.cancel,'Position',[borderSz*3+butWd,borderSz,borderSz*4+butWd, borderSz+butHt]);

% -------------------------------------------------------------------------
function [bitbl, ptbl]=makeTable(parent,t)
biheaders= {'Bus Name','Bus Standard', 'Bus Type'};
pheaders = {'Gateway Name','Bus Interface Name','Bus Name'};

pPos=get(parent,'Position');
pWd = pPos(3);
pHt = pPos(4);
tWd = pWd-(2*borderSz);
tHt = 80;

bitbl=uitable(parent, ...
  'Position',[borderSz,pHt-tHt-borderSz,tWd,tHt],...
  'Data',struct2datacell(0,t.bi),...
  'columnNames',biheaders ...
  );
bitbl.getTable.setAutoResizeMode(1);

ptbl =uitable(parent, ...
  'Position',[borderSz,pHt-2*tHt-2*borderSz,tWd,tHt],...
  'Data',struct2datacell(1,t.port),...
  'columnNames',pheaders ...
  ); 
ptbl.getTable.setAutoResizeMode(1);

set(bitbl,'DataChangedCallback',{@xlPCoreExportBusInterfaceGUI,'validateEvent',parent,0});
set(ptbl,'DataChangedCallback',{@xlPCoreExportBusInterfaceGUI,'validateEvent',parent,1});

% -------------------------------------------------------------------------
function updateStatus(status,txt)
set(status,'String',txt);

% -------------------------------------------------------------------------
function addItemToPullDownMenu(col,item)
col.getCellEditor.getComponent.addItem(item);
col.getCellRenderer.addItem(item);

% -------------------------------------------------------------------------
function removeItemFromPullDownMenu(col,item)
col.getCellEditor.getComponent.removeItem(item);
col.getCellRenderer.remove(item);

% -------------------------------------------------------------------------
function removeAllItemFromPullDownMenu(col)
try
col.getCellEditor.getComponent.removeAllItems;
col.getCellRenderer.removeAllItems;
end

% -------------------------------------------------------------------------
function updatePortTableBusMenu(bitbl,ptbl)
data = tableData2Struct(bitbl);
col=ptbl.getTable.getColumnModel.getColumn(2);
removeAllItemFromPullDownMenu(col);
for l=1:length(data.col1)
  addItemToPullDownMenu(col,data.col1{l});
end
busIndex(data.col1,'store');

% -------------------------------------------------------------------------
function setPortTableBus(ptbl,original_bus_selection,status)
data = ptbl.getData;
topPopup=java.lang.String(busIndex(0,'GetFirstBusFromBITBL'));
err=[];
for l=1:length(original_bus_selection)
  try
    if ~isempty(original_bus_selection{l})
      if ((busIndex(original_bus_selection{l})==-1) && (~isempty(deblank(data(l,1)))))
        err=[err ...
          'Bus Name "' original_bus_selection{l} '" not defined. '...
          'Reselect the Bus Name for port ' num2str(l) char(10)];
        ptbl.getTable.setValueAt(topPopup,l-1,2);
      else
        ptbl.getTable.setValueAt(java.lang.String(original_bus_selection{l}),l-1,2);
      end
    end
  catch
  end
end
% Draw now required to sequentialize the callbacks for table updates,
% otherwise errors may not be displayed in status.
drawnow;

if ~isempty(err)
   updateStatus(status,err);
end

% -------------------------------------------------------------------------
function initBusIfaceTable(bitbl,orig_data)
for l=1:length(orig_data)
  bitbl.getTable.setValueAt(java.lang.String(orig_data{l}),l-1,2);
end




%% ------------------------------------------------------------------------
%% DRC checks
%% ------------------------------------------------------------------------
function validateEvent(hdl,tbl,event)
row = event.getEvent.getFirstRow;
col = event.getEvent.getColumn;
if (col < 0)
  % A whole row was deleted, if it was a BUS_INTERFACE row, some options
  % in the port mapping may be changed.
  validatePortTableBusCol(hdl);
  return; 
end
err=validateOneCell(hdl,tbl,row,col);
if ((err==0) && (tbl==0) && (col==0))
  % The Bus Name in bitbl was changed, need to check to see if
  % the Bus Name options in ptbl was invalidated.
  validatePortTableBusCol(hdl);
end

% -------------------------------------------------------------------------
function validatePortTableBusCol(hdl)
  gdata=guidata(hdl);
  t=tableData2Struct(gdata.ptbl);
  updatePortTableBusMenu(gdata.bitbl,gdata.ptbl);
  setPortTableBus(gdata.ptbl,t.col3,gdata.status);

% -------------------------------------------------------------------------
function err=validateOneCell(hdl,tbl,row,col)
try
  gd=guiData(hdl);
  if (tbl==0)
    d = gd.bitbl;
    e = 'Bus Interface';
  else
    d = gd.ptbl;
    e = 'Bus-Port Mapping';
  end
catch
  % during closeing of the GUI, if a table cell is still inediting mode, I
  % set it to stopEditing, and continue. At this point, the gui may be
  % closed, before the callback for validating that OnceCell is completed.
  err=0;return;
end
txt=d.getTable.getValueAt(row,col);
err = 0;
switch col
  case 0
    if (tbl==1)
      err = checkValidText(txt,'\W'); % white spaces allowed for gateways
    else
      err = checkValidText(txt);
    end
  case 1
    err = checkValidText(txt);
  case 2
    err = isempty(txt);
end
if (err > 0)
  updateStatus(gd.status,['In ' e ' "' txt '" is not valid. Row:' num2str(row+1) ' Col:' num2str(col+1) ' Pos:' num2str(err)]);
else
  updateStatus(gd.status,'');
end




%% ------------------------------------------------------------------------
%% Utility functions
%% ------------------------------------------------------------------------

function t = returnEmptyStruct
t.port.col1 = {' '};
t.port.col2 = {' '};
t.port.col3  = {' '};
t.bi.col1   = {' '};
t.bi.col2   = {' '};
t.bi.col3   = {' '};

% -------------------------------------------------------------------------
    
function err=checkValidNum(txt)
if isempty(txt)
  err=1;
  return;
end
err=0;
p=regexp(txt,'[^0-9]');
if ~isempty(p)
  err=p(1);
  return;
end

% -------------------------------------------------------------------------
function err=checkValidText(txt,extra)
if isempty(txt)
  err=1;
  return;
end
if (nargin>1)
  match = ['[^a-zA-Z0-9_' extra ']'];
else
  match = '[^a-zA-Z0-9_]';
end

err=0;
p=regexp(txt,match);
if ~isempty(p)
  err=p(1);
  return;
end

p=regexp(txt(1),'[0-9]');
if ~isempty(p)
  err=p(1);
  return;
end

% -------------------------------------------------------------------------
function addIcon(but, style)
switch style
  case 'delete'
    loc = fullfile(matlabroot,'toolbox','Simulink','Simulink','delete.gif');
    str = '-';
  case 'add'
    loc = fullfile(matlabroot,'toolbox','Simulink','Simulink','add.gif');
    str = '+';
  otherwise
    return;
end

if ~exist(loc,'file')
  % can't find icons, so just use text
  set(but,'String',str);
  return;
end

[idx,map] = imread(loc);

% assume 1,1 is transparent
bg=get(0,'defaultUicontrolBackgroundColor');
bgidx = idx(1,1);
map(bgidx+1,:)=bg;
rgb = ind2rgb(idx,map);
set(but,'CData',rgb);

% -------------------------------------------------------------------------
function c = struct2datacell(mode,t)
% Mode: 0=bus interface, 1=port interface
bustype = {'INITIATOR','MASTER','MASTER_SLAVE','MONITOR','SLAVE','TARGET'};
defaultbus = {''};

if (isempty(t) || ~isfield(t,'col1'))
  c = {};
  return;
end
colNames = fieldnames(t);
numCols=length(fieldnames(t));
numRows=length(t.col1);
c = cell(numRows,numCols);

for col=1:numCols
  for row=1:numRows
    data = t.(colNames{col})(row);
    data = data{1};
    if ((mode==0) && (col==numCols))
      c{row,col} = bustype;
    else
      if ((mode==1) && (col==numCols))
        c{row,col} = defaultbus;
      else
        c{row,col} = data;
      end
    end
  end
end

% -------------------------------------------------------------------------
function t=tableData2Struct(thdl)
d = cell(thdl.getData);
if ~isempty(d)
  if iscell(d(:,1))
    t.col1 = cell(d(:,1))';
    t.col2 = cell(d(:,2))';
    t.col3 = cell(d(:,3))';
  else
    t.col1 = {d(:,1)};
    t.col2 = {d(:,2)};
    t.col3 = {d(:,3)};
  end
else
  % Never return an empty table. Else when the structure is read back in
  % with the "create" function it will break the code
  t.col1 = {' '};
  t.col2 = {' '};
  t.col3 = {' '};
end


% -------------------------------------------------------------------------
% num pixels on the border of the dlg
function e=borderSz
e=3;

function e = smbutSz
e=24;

function w = labelWd(s)
persistent a;
if (nargin>0)
  a=s;
end
w=a;

function h = labelHt(s)
persistent a;
if (nargin>0)
  a=s;
end
h=a;

% -------------------------------------------------------------------------
function idx=busIndex(s,store)
persistent theindex;
if nargin>1
  if isnumeric(s)
    if ~isempty(theindex)
      idx=theindex{1};
    else
      idx='';
    end
  else
    theindex = s;
    idx=0;
  end
  return;
end
m = strmatch(s,theindex,'exact');
if isempty(m)
  idx = -1;
else
  idx = m-1;
end

