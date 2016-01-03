------------------------
-- Empty Wrapper File --
------------------------
-- The following code must appear in the VHDL architecture header:

LIBRARY std, ieee;
USE std.standard.ALL;
USE ieee.std_logic_1164.ALL;
-- Remember to modify the CLK port declaration
-- of the entity below to be lower case
entity mac_fir_8tap_wrapper is
---- Add Port declaration for entity ----

---- End Port declaration for entity ----
end mac_fir_8tap_wrapper;

architecture test of mac_fir_8tap_wrapper is
-- Add Component Declaration from VHO file ----

---- End COMPONENT Declaration -----
begin
---- ADD INSTANTIATION Template ---- 

---- End INSTANTIATION Template ----
end test;

