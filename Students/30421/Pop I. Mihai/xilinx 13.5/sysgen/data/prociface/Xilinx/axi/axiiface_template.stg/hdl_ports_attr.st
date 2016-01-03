$if(_isvec.(width))$
$prefix$$name$ : $dir$ std_logic_vector($width$-1 downto 0)
$else$
$prefix$$name$ : $dir$ std_logic
$endif$
