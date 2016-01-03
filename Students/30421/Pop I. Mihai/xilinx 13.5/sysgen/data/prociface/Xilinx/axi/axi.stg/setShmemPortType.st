sm_$it.name$_port = this_block.port('sm_$it.name$_$ext$');
$if(_isvec.(it.n_bits))$
sm_$it.name$_port.setType('$it.arith_type;format="toArithType"$_$it.n_bits$_$it.bin_pt$');
$else$
sm_$it.name$_port.setType('Bool');
sm_$it.name$_port.useHDLVector(false);
$endif$
