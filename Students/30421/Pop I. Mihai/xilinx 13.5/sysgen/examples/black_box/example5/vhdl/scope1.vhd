library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity scope1 is
    generic (width1 : integer := 8);
    port    (sig1   : in std_logic_vector(width1-1 downto 0)); 
end scope1;

architecture behavior of scope1 is
begin
end behavior;

