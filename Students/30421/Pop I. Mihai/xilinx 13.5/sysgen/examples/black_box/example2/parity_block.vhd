library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity parity_block is
  port (
    din: in std_logic_vector(7 downto 0);
    parity: out std_logic;
    ce: in std_logic;
    clk: in std_logic
  );
end parity_block;

architecture behavior of parity_block is
begin
  PARITY_Process: process (din, clk, ce)
    variable cur_parity: std_logic := '0';
    variable running_parity: std_logic := '0';
  begin
    if (ce='1' and rising_edge(clk)) then
      cur_parity := '0';
      cur_parity := cur_parity XOR din(0);
      cur_parity := cur_parity XOR din(1);
      cur_parity := cur_parity XOR din(2);
      cur_parity := cur_parity XOR din(3);
      cur_parity := cur_parity XOR din(4);
      cur_parity := cur_parity XOR din(5);
      cur_parity := cur_parity XOR din(6);
      cur_parity := cur_parity XOR din(7);
      running_parity := running_parity XOR cur_parity;
    end if;

    parity <= running_parity after 1 ns;
  end process PARITY_Process;
end behavior;
