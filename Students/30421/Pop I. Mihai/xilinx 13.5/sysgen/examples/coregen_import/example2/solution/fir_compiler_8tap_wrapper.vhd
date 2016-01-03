------------------------
-- Empty Wrapper File --
------------------------
-- The following code must appear in the VHDL architecture header:

LIBRARY std, ieee;
USE std.standard.ALL;
USE ieee.std_logic_1164.ALL;
-- Remember to modify the CLK port declaration
-- of the entity below to be lower case
entity fir_compiler_8tap_wrapper is
---- Add Port declaration for entity ----
 PORT (
    clk: IN STD_LOGIC;
    ce: IN STD_LOGIC;
    s_axis_data_tvalid : IN STD_LOGIC;
    s_axis_data_tready : OUT STD_LOGIC;
    s_axis_data_tdata : IN STD_LOGIC_VECTOR(15 DOWNTO 0);
    m_axis_data_tvalid : OUT STD_LOGIC;
    m_axis_data_tdata : OUT STD_LOGIC_VECTOR(31 DOWNTO 0));
---- End Port declaration for entity ----
end fir_compiler_8tap_wrapper;

architecture test of fir_compiler_8tap_wrapper is

-- Add Component Declaration from VHO file ----
COMPONENT fir_compiler_8tap
  PORT (
    aclk : IN STD_LOGIC;
    s_axis_data_tvalid : IN STD_LOGIC;
    s_axis_data_tready : OUT STD_LOGIC;
    s_axis_data_tdata : IN STD_LOGIC_VECTOR(15 DOWNTO 0);
    m_axis_data_tvalid : OUT STD_LOGIC;
    m_axis_data_tdata : OUT STD_LOGIC_VECTOR(31 DOWNTO 0)
  );
END COMPONENT;
---- End COMPONENT Declaration -----
begin
--m_axis_data_o_int_26_bits <= "000000" & m_axis_data_tdata(25 downto 0);
---- ADD INSTANTIATION Template ---- 
UO : fir_compiler_8tap
  PORT MAP (
    aclk => clk,
    s_axis_data_tvalid => s_axis_data_tvalid,
    s_axis_data_tready => s_axis_data_tready,
    s_axis_data_tdata => s_axis_data_tdata,
    m_axis_data_tvalid => m_axis_data_tvalid,
    m_axis_data_tdata => m_axis_data_tdata
  );
---- End INSTANTIATION Template ----
end test;

