----------------------------------------------------------
--
--  Filename    : top_level.vhd
--
--  Description : Top level VHDL for tutorial example. This
--                example shows how to instantiate a System
--                Generator design into a top-level piece
--                on VHDL code.
--
----------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;

library design1_lib;
use design1_lib.all;
library design2_lib;
use design2_lib.all;
-- synopsys translate_on


entity top_level is
  port (addr    : in std_logic_vector(3 downto 0);
	
	-- Bi-directional data port
        data    : inout std_logic_vector(15 downto 0);
	
        rd_wr   : in std_logic;

        fir_in  : in std_logic_vector(11 downto 0);
        fir_out : out std_logic_vector(24 downto 0);

	-- Two clocks
        clk1     : in std_logic;
	clk2     : in std_logic
       );
end top_level;

architecture structural of top_level is
 
  attribute syn_black_box : boolean;
  attribute box_type :  string;


  component ibufg
    port (i : in  std_logic;
          o : out std_logic);
  end component; -- end component ibufg
  attribute box_type of ibufg : component is "black_box";
  attribute syn_black_box of ibufg : component is true;


  component ibuf
    port (i : in  std_logic;
          o : out std_logic);
  end component; -- end component ibuf
  attribute box_type of ibuf : component is "black_box";
  attribute syn_black_box of ibuf : component is true;


  component obuft
    port (i : in std_logic;
          t : in std_logic;
          o : out std_logic
         );
  end component; -- end component obuft
  attribute box_type of obuft : component is "black_box";
  attribute syn_black_box of obuft : component is true;

  component spram_cw is 
  port (
    addr: in std_logic_vector(3 downto 0);

    --  Note: The ce port is not connected to registers within this design.  It
    --  is just a there so that this VHDL file could be imported as a Black Box
    --  within System Generator
    ce: in std_logic := '1';	
    clk: in std_logic;
    data_in: in std_logic_vector(15 downto 0);
    we: in std_logic;
    data_out: out std_logic_vector(15 downto 0)
  );
  end component;
  attribute box_type of spram_cw : component is "black_box";
  attribute syn_black_box of spram_cw : component is true;

  component mac_fir_cw is 
  port (
    ce: in std_logic := '1';
    clk: in std_logic;
    data_in: in std_logic_vector(11 downto 0);
    data_out: out std_logic_vector(24 downto 0)
  );
  end component;
  attribute box_type of mac_fir_cw : component is "black_box";
  attribute syn_black_box of mac_fir_cw : component is true;
				

							      
  signal data_in, data_out : std_logic_vector(15 downto 0);
  signal clk1_ibufg, clk2_ibufg : std_logic;
 
begin

  -- Global clock buffer 1
  u_ibufg1 : ibufg
    port map(i => clk1,
             o => clk1_ibufg
            );

  -- Global clock buffer 2
  u_ibufg2 : ibufg
    port map(i => clk2,
             o => clk2_ibufg
            );
  
  -- tri-state buffers for memory data ports.
  inst_obuft : for i in 0 to 15 generate
    U1 : ibuf port map (i => data(i),
                        o => data_in(i)
                       );
    U2 : obuft port map (i => data_out(i),
                         t => rd_wr,
                         o => data(i)
                        );
  end generate; 

  -- System Generator Design #1
  u_spram_cw : spram_cw
  port map (
    addr     => addr,
    ce       => '1',
    clk      => clk1_ibufg,
    data_in  => data_in,
    we       => rd_wr,
    data_out => data_out
  );


  -- System Generator Design #2
  u_mac_fir : mac_fir_cw
  port map (
    ce       => '1',
    clk      => clk2_ibufg,
    data_in  => fir_in,
    data_out => fir_out
  );



end structural;







