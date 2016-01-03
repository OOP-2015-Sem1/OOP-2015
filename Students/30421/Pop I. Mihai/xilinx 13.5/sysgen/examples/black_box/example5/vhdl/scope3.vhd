library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity scope3 is
    generic (width1 : integer := 8;
             width2 : integer := 8;
             width3 : integer := 8);
    port (sig1 : in std_logic_vector(width1-1 downto 0); 
          sig2 : in std_logic_vector(width2-1 downto 0); 
          sig3 : in std_logic_vector(width3-1 downto 0)); 
end scope3;

architecture behavior of scope3 is
begin
end behavior;

