$memmap_info.fromregs:{% 'From Register' block: '$it.name$'
this_block.addSimulinkInport('sm_$it.name$_dout');
$setShmemPortType(ext="dout")$};separator="\n"$

$memmap_info.toregs:{% 'To Register' block: '$it.name$'
% 'din' port
this_block.addSimulinkOutport('sm_$it.name$_din');
$setShmemPortType(ext="din")$
% 'en' port
this_block.addSimulinkOutport('sm_$it.name$_en');
sm_$it.name$_en_port = this_block.port('sm_$it.name$_en');
% TODO: this may cause issues when SysGen token sets to rates other than 1
sm_$it.name$_en_port.setRate(1);
sm_$it.name$_en_port.setType('Bool');
sm_$it.name$_en_port.useHDLVector(false);
% 'dout' port
this_block.addSimulinkInport('sm_$it.name$_dout');
$setShmemPortType(ext="dout")$};separator="\n"$

$memmap_info.fromfifos:{% 'From FIFO' block: '$it.name$'
% 'dout' port
this_block.addSimulinkInport('sm_$it.name$_dout');
$setShmemPortType(ext="dout")$
% 'pfull' port
port_name = 'sm_$it.name$_pfull';
this_block.addSimulinkInport(port_name);
sm_$it.name$_pfull_port = this_block.port(port_name);
sm_$it.name$_pfull_port.setRate(1);
sm_$it.name$_pfull_port.setType('UFix_$it.percent_nbits$_$it.percent_nbits$');
% 'empty' port
port_name = 'sm_$it.name$_empty';
this_block.addSimulinkInport(port_name);
sm_$it.name$_empty_port = this_block.port(port_name);
sm_$it.name$_empty_port.setRate(1);
sm_$it.name$_empty_port.setType('Bool');
sm_$it.name$_empty_port.useHDLVector(false);
% 're' port
port_name = 'sm_$it.name$_re';
this_block.addSimulinkOutport(port_name);
sm_$it.name$_re_port = this_block.port(port_name);
sm_$it.name$_re_port.setRate(1);
sm_$it.name$_re_port.setType('Bool');
sm_$it.name$_re_port.useHDLVector(false);
};separator="\n"$

$memmap_info.tofifos:{% 'To FIFO' block: '$it.name$'
% 'din' port
this_block.addSimulinkOutport('sm_$it.name$_din');
$setShmemPortType(ext="din")$
% 'pfull' port
port_name = 'sm_$it.name$_pfull';
this_block.addSimulinkInport(port_name);
sm_$it.name$_pfull_port = this_block.port(port_name);
sm_$it.name$_pfull_port.setRate(1);
sm_$it.name$_pfull_port.setType('UFix_$it.percent_nbits$_$it.percent_nbits$');
% 'full' port
port_name = 'sm_$it.name$_full';
this_block.addSimulinkInport(port_name);
sm_$it.name$_full_port = this_block.port(port_name);
sm_$it.name$_full_port.setRate(1);
sm_$it.name$_full_port.setType('Bool');
sm_$it.name$_full_port.useHDLVector(false);
% 'we' port
port_name = 'sm_$it.name$_we';
this_block.addSimulinkOutport(port_name);
sm_$it.name$_we_port = this_block.port(port_name);
sm_$it.name$_we_port.setRate(1);
sm_$it.name$_we_port.setType('Bool');
sm_$it.name$_we_port.useHDLVector(false);
};separator="\n"$

$memmap_info.shmems:{% 'Shared Memory' block: '$it.name$'
% 'addr' port
port_name = 'sm_$it.name$_addr';
this_block.addSimulinkOutport(port_name);
sm_$it.name$_addr_port = this_block.port(port_name);
sm_$it.name$_addr_port.setRate(1);
sm_$it.name$_addr_port.setType('UFix_$it.width$_0');
% 'din' port
this_block.addSimulinkOutport('sm_$it.name$_din');
$setShmemPortType(ext="din")$
% 'dout' port
this_block.addSimulinkInport('sm_$it.name$_dout');
$setShmemPortType(ext="dout")$
% 'we' port
port_name = 'sm_$it.name$_we';
this_block.addSimulinkOutport(port_name);
sm_$it.name$_we_port = this_block.port(port_name);
sm_$it.name$_we_port.setRate(1);
sm_$it.name$_we_port.setType('Bool');
sm_$it.name$_we_port.useHDLVector(false);
};separator="\n"$
