library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity counter is
   generic (
      COUNT_BIT_WIDTH            : integer := 4; -- number of counter bits, max count is 2^COUNT_BIT_WIDTH-1
      COUNT_INITIAL              : integer := 0;
      COUNT_MAX                  : integer := 15
   );
   port (
      -- clocks and reset
      clk                        : in std_logic;
      ce                         : in std_logic;
      reset                      : in std_logic;
      
      -- control signals
      enable                     : in std_logic;
      
      -- count value
      count                      : out std_logic_vector(COUNT_BIT_WIDTH-1 downto 0)
   );
end counter;

architecture rtl of counter is
signal count_int              : std_logic_vector(COUNT_BIT_WIDTH-1 downto 0);
begin
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- Perform error checking
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-- initial count value
ASSERT (COUNT_INITIAL <= COUNT_MAX)
   REPORT "Initial count cannot exceed the maximum count."
   SEVERITY ERROR;
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-- Implement counter
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

process (clk) begin
   if rising_edge (clk) then
      if (reset = '1') then
         count_int <= CONV_STD_LOGIC_VECTOR(COUNT_INITIAL, COUNT_BIT_WIDTH);
      else
         if (enable = '1') then
            count_int <= count_int + '1';
            
            if (count_int = COUNT_MAX) then
               count_int <= CONV_STD_LOGIC_VECTOR(COUNT_INITIAL, COUNT_BIT_WIDTH);
            end if;
         end if;
      end if;
   end if;
end process;

count <= count_int;
--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

end rtl;
