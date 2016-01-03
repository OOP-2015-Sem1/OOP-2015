
LIBRARY std, ieee;
USE std.standard.ALL;
USE ieee.std_logic_1164.ALL;

entity macfir_blackbox is
   port (
      input_clk  : in std_logic;
      input_ce   : in std_logic;
      output_clk : in std_logic;
      output_ce  : in std_logic;
      reset :  in std_logic;
      din   :  in std_logic_vector(15 downto 0);
      dout  : out std_logic_vector(38 downto 0)   
);
end macfir_blackbox;

architecture  bb_arch of macfir_blackbox is 

component macfir_core
	port (
	clk: in std_logic;
	reset: in std_logic;
	nd: in std_logic;
	rdy: out std_logic;
	rfd: out std_logic;
	din: in std_logic_vector(15 downto 0);
	dout: out std_logic_vector(38 downto 0));
end component;


-- XST black box declaration
attribute box_type : string;
attribute box_type of macfir_core: component is "black_box";

signal core_dout : std_logic_vector (38 downto 0);

begin
u1 : macfir_core
  port map (
    clk => input_clk,
    reset => reset,
    nd => input_ce,
    din => din,
    dout => core_dout
  );

-- A caputure register is used to allign the output samples to
-- the appropriate System Generator multi-cycle data word boundary
-- by using output_ce.

REG_Process :  process (output_clk, output_ce)
  variable dout_register     : std_logic_vector(38 downto 0)
    := "000000000000000000000000000000000000000";
  begin
    if (output_ce='1' and rising_edge(output_clk)) then
      dout_register := core_dout;
    end if;

    dout <= dout_register
-- synopsys translate_off
            after 1 ns
-- synopsys translate_on
            ;

  end process REG_Process;
end bb_arch;
