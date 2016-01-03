
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
library design1_lib;
use design1_lib.conv_pkg.all;

entity top_level_testbench is 
end top_level_testbench;

architecture structural of top_level_testbench is
    component top_level 
	port (addr  : in std_logic_vector(3 downto 0);
	      data  : inout std_logic_vector(15 downto 0);
	      rd_wr : in std_logic;
	      fir_in  : in std_logic_vector(11 downto 0);
              fir_out : out std_logic_vector(24 downto 0);
	      clk1   : in std_logic;
	      clk2   : in std_logic	      
	      );
    end component;

    signal clk1 : std_logic := '1';
    signal clk2 : std_logic := '1';
    signal data : std_logic_vector(15 downto 0) := (others => 'Z');
    signal real_data, real_fir_out : real;
    signal addr : unsigned(3 downto 0) := x"0";
    signal clk_num : integer := 0;
    signal rd_wr : std_logic := '0';  	-- 0 = read, 1 = write
    signal fir_in  : std_logic_vector(11 downto 0) := "000100000000";
    signal fir_out : std_logic_vector(24 downto 0);

    
begin
    clk1_gen : process(clk1)
    begin
	clk1 <= not(clk1) after 50 ns;
	if rising_edge(clk1) then
	    addr <= addr + 1;
	end if;
    end process;

    clk2_gen : process(clk2)
    begin
	clk2 <= not(clk2) after 15 ns;
	if rising_edge(clk2) then
	    clk_num <= clk_num + 1;
	    if clk_num = 15 then
		fir_in <= (others => '0');
	    end if;    
	end if;
    end process;
    

    -- After 1700 ns write into the memory
    data <= x"E000" after 1600 ns, "ZZZZZZZZZZZZZZZZ" after 2300 ns;
    rd_wr <= '1' after 1600 ns, '0' after 2300 ns;
    
     dut: top_level 
	port map (addr => unsigned_to_std_logic_vector(addr),
	      data => data,
	      rd_wr => rd_wr,
	      fir_in => fir_in,
	      fir_out => fir_out,
	      clk1  => clk1,
	      clk2  => clk2  
	      );

    -- Real number representation of data bus.  It is a 16 bit signed value
    -- with the binary point at 12, i.e. Fix_16_12
    real_data <= to_real(data, 12, xlSigned);

    -- Real number representation of FIR filter output.  It is a 25 bit signed value
    -- with the binary point at 19, i.e. Fix_25_19
    real_fir_out <= to_real(fir_out, 19, xlSigned);
end structural;


