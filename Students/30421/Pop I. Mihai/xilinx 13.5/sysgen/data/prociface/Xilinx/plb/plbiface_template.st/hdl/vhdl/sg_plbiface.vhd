library ieee;
use ieee.std_logic_1164.all;

entity $T.pcoreName$ is
  generic
  (
--  -- System Parameter
    C_FAMILY              : string                    := "virtex5";
--  -- PLB Parameters
    C_BASEADDR            : std_logic_vector(0 to 31) := X"FFFF_FFFF";
    C_HIGHADDR            : std_logic_vector(0 to 31) := X"0000_0000";
    $T.bus_info.params.keys:{$hdl_generic(name=it,param=T.bus_info.params.(it))$};separator=";\n"$
  );
  port
  (
$if(T.isimportmode)$
    -- 'inports'
    PLB_ABus : in std_logic_vector(0 to C_SPLB_AWIDTH-1);
    PLB_PAValid : in std_logic;
    PLB_RNW : in std_logic;
    PLB_wrDBus : in std_logic_vector(0 to C_SPLB_DWIDTH-1);
    SPLB_Clk : in std_logic;
    SPLB_Rst : in std_logic;
    -- 'outports'
    Sl_addrAck : out std_logic;
    Sl_rdComp : out std_logic;
    Sl_rdDAck : out std_logic;
    Sl_rdDBus : out std_logic_vector(0 to C_SPLB_DWIDTH-1);
    Sl_wait : out std_logic;
    Sl_wrComp : out std_logic;
    Sl_wrDAck : out std_logic;
    -- 'sginports'
    sgPLB_ABus : out std_logic_vector(0 to 32-1);
    sgPLB_PAValid : out std_logic;
    sgPLB_RNW : out std_logic;
    sgPLB_wrDBus : out std_logic_vector(0 to 32-1);
    sgSPLB_Clk : out std_logic;
    sgSPLB_Rst : out std_logic;
    -- 'sgoutports'
    sgSl_addrAck : in std_logic;
    sgSl_rdComp : in std_logic;
    sgSl_rdDAck : in std_logic;
    sgSl_rdDBus : in std_logic_vector(0 to 32-1);
    sgSl_wait : in std_logic;
    sgSl_wrComp : in std_logic;
    sgSl_wrDAck : in std_logic
$else$
    -- 'inports'
    $T.bus_info.inports.keys:{$hdl_ports_attr(dir="in",name=it,width=T.bus_info.inports.(it))$};separator=";\n"$$last(T.bus_info.inports):{;}$
    $T.bus_info.clks.keys:{$hdl_ports_attr(dir="in",name=it,width=T.bus_info.clks.(it))$};separator=";\n"$$last(T.bus_info.clks):{;}$
    $T.bus_info.resets.keys:{$hdl_ports_attr(dir="in",name=it,width=T.bus_info.resets.(it))$};separator=";\n"$$last(T.bus_info.resets):{;}$
    -- 'outports'
    $T.bus_info.outports.keys:{$hdl_ports_attr(dir="out",name=it,width=T.bus_info.outports.(it))$};separator=";\n"$$last(T.bus_info.outports):{;}$
    -- 'sginports'
    $T.bus_info.inports.keys:{$hdl_ports_attr(dir="out",prefix="sg",name=it,width=T.bus_info.inports.(it))$};separator=";\n"$$last(T.bus_info.inports):{;}$
    $T.bus_info.clks.keys:{$hdl_ports_attr(dir="out",prefix="sg",name=it,width=T.bus_info.clks.(it))$};separator=";\n"$$last(T.bus_info.clks):{;}$
    $T.bus_info.resets.keys:{$hdl_ports_attr(dir="out",prefix="sg",name=it,width=T.bus_info.resets.(it))$};separator=";\n"$$last(T.bus_info.resets):{;}$
    -- 'sgoutports'
    $T.bus_info.outports.keys:{$hdl_ports_attr(dir="in",prefix="sg",name=it,width=T.bus_info.outports.(it))$};separator=";\n"$
$endif$
  );
end entity $T.pcoreName$;

architecture imp of $T.pcoreName$ is

$if(T.isimportmode)$
signal sl_rddbus_i            : std_logic_vector(0 to C_SPLB_DWIDTH-1);
$endif$

begin

$if(T.isimportmode)$
-------------------------------------------------------------------------------
-- Mux/Steer data/be's correctly for connect 32-bit slave to 128-bit plb
-------------------------------------------------------------------------------
GEN_128_TO_32_SLAVE : if C_SPLB_NATIVE_DWIDTH = 32 and C_SPLB_DWIDTH = 128 generate
begin
    -----------------------------------------------------------------------
    -- Map lower rd data to each quarter of the plb slave read bus
    -----------------------------------------------------------------------
    sl_rddbus_i(0 to 31)      <=  sgSl_rdDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
    sl_rddbus_i(32 to 63)     <=  sgSl_rdDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
    sl_rddbus_i(64 to 95)     <=  sgSl_rdDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
    sl_rddbus_i(96 to 127)    <=  sgSl_rdDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
end generate GEN_128_TO_32_SLAVE;

-------------------------------------------------------------------------------
-- Mux/Steer data/be's correctly for connect 32-bit slave to 64-bit plb
-------------------------------------------------------------------------------
GEN_64_TO_32_SLAVE : if C_SPLB_NATIVE_DWIDTH = 32 and C_SPLB_DWIDTH = 64 generate
begin
    ---------------------------------------------------------------------------        
    -- Map lower rd data to upper and lower halves of plb slave read bus
    ---------------------------------------------------------------------------        
    sl_rddbus_i(0 to 31)      <=  sgSl_rdDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
    sl_rddbus_i(32 to 63)     <=  sgSl_rdDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
end generate GEN_64_TO_32_SLAVE;

-------------------------------------------------------------------------------
-- IPIF DWidth = PLB DWidth
-- If IPIF Slave Data width is equal to the PLB Bus Data Width
-- Then BE and Read Data Bus map directly to eachother.
-------------------------------------------------------------------------------
GEN_FOR_EQUAL_SLAVE : if C_SPLB_NATIVE_DWIDTH = C_SPLB_DWIDTH generate
    sl_rddbus_i    <= sgSl_rdDBus;
end generate GEN_FOR_EQUAL_SLAVE;
$endif$

$if(T.isimportmode)$
    -- 'inports'
    sgPLB_ABus <= PLB_ABus(0 to 32-1);
    sgPLB_PAValid <= PLB_PAValid;
    sgPLB_RNW <= PLB_RNW;
    sgPLB_wrDBus <= PLB_wrDBus(0 to C_SPLB_NATIVE_DWIDTH-1);
    sgSPLB_Clk <= SPLB_Clk;
    sgSPLB_Rst <= SPLB_Rst;
    -- 'outports'
    Sl_addrAck <= sgSl_addrAck;
    Sl_rdComp <= sgSl_rdComp;
    Sl_rdDAck <= sgSl_rdDAck;
    Sl_rdDBus <= sl_rddbus_i;
    Sl_wait <= sgSl_wait;
    Sl_wrComp <= sgSl_wrComp;
    Sl_wrDAck <= sgSl_wrDAck;
$else$
    -- 'inports'
    $T.bus_info.inports.keys:{sg$it$ <= $it$};separator=";\n"$$last(T.bus_info.inports):{;}$
    $T.bus_info.clks.keys:{sg$it$ <= $it$};separator=";\n"$$last(T.bus_info.clks):{;}$
    $T.bus_info.resets.keys:{sg$it$ <= $it$};separator=";\n"$$last(T.bus_info.resets):{;}$
    -- 'outports'
    $T.bus_info.outports.keys:{$it$ <= sg$it$};separator=";\n"$$last(T.bus_info.outports):{;}$
$endif$

end architecture imp;
