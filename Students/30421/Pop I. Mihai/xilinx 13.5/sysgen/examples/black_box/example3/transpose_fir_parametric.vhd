library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_SIGNED.ALL;

--  Uncomment the following lines to use the declarations that are
--  provided for instantiating Xilinx primitive components.
--library UNISIM;
--use UNISIM.VComponents.all;

entity transpose_fir is
    generic (
    	     input_bitwidth : integer;
    	     output_bitwidth : integer
	);

    Port ( din : in std_logic_vector(input_bitwidth-1 downto 0);
           clk : in std_logic;
           ce : in std_logic;
	   rst : in std_logic;
           dout : out std_logic_vector(output_bitwidth-1 downto 0));
end transpose_fir;

architecture Behavioral of transpose_fir is

component mac
	generic (
	coef_value : integer := 0;
	input_bitwidth : integer := 0;
	output_bitwidth : integer := 0
	);
	
	port(
	din : in std_logic_vector(input_bitwidth-1 downto 0);
	cin : in std_logic_vector(output_bitwidth-1 downto 0);
	clk : in std_logic;
	ce : in std_logic;
	rst : in std_logic;
	dout : out std_logic_vector(output_bitwidth-1 downto 0));
end component;



-- Number of Coefficients
constant N : integer := 23;	                   
-- Coefficient Values
type coef_array is array (0 to N-1) of integer;	  
constant coefficient : coef_array := (-38, -74, -109, -109, -37, 140, 435, 827, 1262, 1663, 1945, 2047, 1945, 1663, 1262, 827, 435, 140, -37, -109, -109, -74, -38);

signal cin_temp : std_logic_vector(output_bitwidth*N downto 0) := CONV_STD_LOGIC_VECTOR (0, output_bitwidth*N+1);  

signal cin_gnd : std_logic_vector(output_bitwidth-1 downto 0);

begin

   cin_gnd <= CONV_STD_LOGIC_VECTOR (0, output_bitwidth);
   
   
   G0: for I in 0 to N-1 generate
      	
      	G_first: if I = 0 generate
		
		M0: MAC 
			generic map (
		        coef_value => coefficient(I),
		        input_bitwidth => input_bitwidth,
		        output_bitwidth => output_bitwidth
		        
			)
			port map (
			din => din, 
			cin => cin_gnd,
			clk => clk,
			ce => ce,
			rst => rst,
			dout => cin_temp(output_bitwidth-1 downto 0));
			end generate;
	
	GX: if (I >= 1 and I < N-1) generate
		M1: MAC 
			generic map (
			coef_value => coefficient(I),
			input_bitwidth => input_bitwidth,
		        output_bitwidth => output_bitwidth
			)
			port map (
			din => din, 
			cin => cin_temp(I*(output_bitwidth-1)+(I-1) downto ((I-1)*output_bitwidth)),
			clk => clk,
			ce => ce,
			rst => rst,
			dout => cin_temp((I+1)*(output_bitwidth-1)+I downto I*output_bitwidth));
			end generate;
	
	
	G_last: if I = N-1 generate
		M2: MAC 
			generic map(
			coef_value => coefficient(I),
			input_bitwidth => input_bitwidth,
		        output_bitwidth => output_bitwidth
			)			
			port map (
			din => din, 
			cin => cin_temp(I*(output_bitwidth-1)+(I-1) downto ((I-1)*output_bitwidth)),
			clk => clk,
		        ce => ce,
		        rst => rst,
		        dout => dout);
			end generate;
   end generate;

end Behavioral;
