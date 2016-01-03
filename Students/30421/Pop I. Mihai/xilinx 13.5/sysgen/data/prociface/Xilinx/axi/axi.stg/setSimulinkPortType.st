$key$_port = this_block.port('$key$');
$if(_isvec.(port.(key)))$
$key$_port.setType('UFix_$port.(key)$_0');
$else$
$key$_port.setType('Bool');
$key$_port.useHDLVector(false);
$endif$
