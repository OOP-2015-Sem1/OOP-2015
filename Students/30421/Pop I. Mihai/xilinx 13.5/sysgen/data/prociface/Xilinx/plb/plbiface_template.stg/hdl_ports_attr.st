$if(_isvec.(width))$
$prefix$$name$ : $dir$ std_logic_vector(0 to $width$-1)
$else$
$prefix$$name$ : $dir$ std_logic
$endif$
