function [status, customBusInterfaceValue]= xlPCoreExportBusInterfaceMarshalling(customBusInterfaceValue, netlistdir)
% Loops through the structure returned in customBusInterfaceValue - derived
% from the xledkBusInterfaceGUI. Checks to see if the gateway names are
% valid, if not, check to see if it can match and HDL port names, otherwise
% report error, but do not stop compile! Let pcore export complete.
% Prepare additon BUS_INTERFACE and PORT attributes for MPD.

% Check to see if customBusInterfaceValue is empty and has relevant fields.
status='';
if isempty(customBusInterfaceValue)
    return;
end

if ~isfield(customBusInterfaceValue,'port')
    customBusInterfaceValue=[];
    return;
end

synopsisFile = java.io.File(netlistdir, 'synopsis');
container = com.xilinx.sysgen.netlist.Block.fromXTable(synopsisFile);
cw = container.findBlock(com.xilinx.sysgen.netlist.Attribute.IS_CLK_WRAPPER);
%block = com.xilinx.sysgen.netlist.Block('sg_plb_module_dut','sg_plb_module', container);
%targetDir = container.getAttribute('directory');

cw_ports = cw.getPorts();

% Loop through ports in customBusInterfaceValue.port and check to see if I
% can match it with top level ports. First try to match with Block Name, if
% that fails try to match with hdlname.
portDecorations.col1={};
portDecorations.col2={};
portDecorations.col3={};
for m=0:1
  for l=0:(cw_ports.size-1)
    if m==0
      simName = cw_ports.get(l).getAttribute('simulinkName');
      if ~isempty(simName)
          [dontcare,matchName] = fileparts(simName);
      else
        continue;
      end
    else
      matchName = cw_ports.get(l).getHdlName;
    end
    for n=length(customBusInterfaceValue.port.col1):-1:1
      if strcmp(customBusInterfaceValue.port.col1{n},matchName)
        portDecorations.col1{end+1} = char(cw_ports.get(l).getHdlName);
        portDecorations.col2{end+1} = customBusInterfaceValue.port.col2{n};
        portDecorations.col3{end+1} = customBusInterfaceValue.port.col3{n};
        customBusInterfaceValue.port.col1(n) = [];
        customBusInterfaceValue.port.col2(n) = [];
        customBusInterfaceValue.port.col3(n) = [];
      end
    end
  end
end

% Construct warning message if there are ports that are in the
% customBusInterface but not found in synopsis
status='';
for l=1:length(customBusInterfaceValue.port.col1)
  status = [status 'Gateway "' customBusInterfaceValue.port.col1{l} '" not found.' char(10)]; 
end

customBusInterfaceValue.port = portDecorations;
