library ieee;
use ieee.std_logic_1164.all;

entity $T.pcoreName$ is
  generic
  (
--  -- System Parameter
    C_FAMILY              : string                    := "virtex6";
--  -- PLB Parameters
    C_BASEADDR            : std_logic_vector(31 downto 0) := X"FFFF_FFFF";
    C_HIGHADDR            : std_logic_vector(31 downto 0) := X"0000_0000";
    $T.bus_info.params.keys:{$hdl_generic(name=it,param=T.bus_info.params.(it))$};separator=";\n"$
  );
  port
  (
    -- 'inports'
    ARADDR : in std_logic_vector(32-1 downto 0);
    ARBURST : in std_logic_vector(2-1 downto 0);
    ARCACHE : in std_logic_vector(4-1 downto 0);
    ARID : in std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
    ARLEN : in std_logic_vector(8-1 downto 0);
    ARLOCK : in std_logic_vector(2-1 downto 0);
    ARPROT : in std_logic_vector(3-1 downto 0);
    ARSIZE : in std_logic_vector(3-1 downto 0);
    ARVALID : in std_logic;
    AWADDR : in std_logic_vector(32-1 downto 0);
    AWBURST : in std_logic_vector(2-1 downto 0);
    AWCACHE : in std_logic_vector(4-1 downto 0);
    AWID : in std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
    AWLEN : in std_logic_vector(8-1 downto 0);
    AWLOCK : in std_logic_vector(2-1 downto 0);
    AWPROT : in std_logic_vector(3-1 downto 0);
    AWSIZE : in std_logic_vector(3-1 downto 0);
    AWVALID : in std_logic;
    BREADY : in std_logic;
    RREADY : in std_logic;
    WDATA : in std_logic_vector(32-1 downto 0);
    WLAST : in std_logic;
    WSTRB : in std_logic_vector(4-1 downto 0);
    WVALID : in std_logic;
    AXI_ACLK : in std_logic;
    AXI_ARESETN : in std_logic;
    -- 'outports'
    ARREADY : out std_logic;
    AWREADY : out std_logic;
    BID : out std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
    BRESP : out std_logic_vector(2-1 downto 0);
    BVALID : out std_logic;
    RDATA : out std_logic_vector(32-1 downto 0);
    RID : out std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
    RLAST : out std_logic;
    RRESP : out std_logic_vector(2-1 downto 0);
    RVALID : out std_logic;
    WREADY : out std_logic;
    -- 'sginports'
    sgARADDR : out std_logic_vector(32-1 downto 0);
    sgARBURST : out std_logic_vector(2-1 downto 0);
    sgARCACHE : out std_logic_vector(4-1 downto 0);
    sgARID : out std_logic_vector(8-1 downto 0);
    sgARLEN : out std_logic_vector(8-1 downto 0);
    sgARLOCK : out std_logic_vector(2-1 downto 0);
    sgARPROT : out std_logic_vector(3-1 downto 0);
    sgARSIZE : out std_logic_vector(3-1 downto 0);
    sgARVALID : out std_logic;
    sgAWADDR : out std_logic_vector(32-1 downto 0);
    sgAWBURST : out std_logic_vector(2-1 downto 0);
    sgAWCACHE : out std_logic_vector(4-1 downto 0);
    sgAWID : out std_logic_vector(8-1 downto 0);
    sgAWLEN : out std_logic_vector(8-1 downto 0);
    sgAWLOCK : out std_logic_vector(2-1 downto 0);
    sgAWPROT : out std_logic_vector(3-1 downto 0);
    sgAWSIZE : out std_logic_vector(3-1 downto 0);
    sgAWVALID : out std_logic;
    sgBREADY : out std_logic;
    sgRREADY : out std_logic;
    sgWDATA : out std_logic_vector(32-1 downto 0);
    sgWLAST : out std_logic;
    sgWSTRB : out std_logic_vector(4-1 downto 0);
    sgWVALID : out std_logic;
    sgAXI_ACLK : out std_logic;
    sgAXI_ARESETN : out std_logic;
    -- 'sgoutports'
    sgARREADY : in std_logic;
    sgAWREADY : in std_logic;
    sgBID : in std_logic_vector(8-1 downto 0);
    sgBRESP : in std_logic_vector(2-1 downto 0);
    sgBVALID : in std_logic;
    sgRDATA : in std_logic_vector(32-1 downto 0);
    sgRID : in std_logic_vector(8-1 downto 0);
    sgRLAST : in std_logic;
    sgRRESP : in std_logic_vector(2-1 downto 0);
    sgRVALID : in std_logic;
    sgWREADY : in std_logic
  );
end entity $T.pcoreName$;

architecture imp of $T.pcoreName$ is

begin

    -- 'inports'
    sgARADDR <= ARADDR;
    sgARBURST <= ARBURST;
    sgARCACHE <= ARCACHE;
    sgARID(C_S_AXI_ID_WIDTH-1 downto 0) <= ARID;
    sgARLEN <= ARLEN;
    sgARLOCK <= ARLOCK;
    sgARPROT <= ARPROT;
    sgARSIZE <= ARSIZE;
    sgARVALID <= ARVALID;
    sgAWADDR <= AWADDR;
    sgAWBURST <= AWBURST;
    sgAWCACHE <= AWCACHE;
    sgAWID(C_S_AXI_ID_WIDTH-1 downto 0) <= AWID;
    sgAWLEN <= AWLEN;
    sgAWLOCK <= AWLOCK;
    sgAWPROT <= AWPROT;
    sgAWSIZE <= AWSIZE;
    sgAWVALID <= AWVALID;
    sgBREADY <= BREADY;
    sgRREADY <= RREADY;
    sgWDATA <= WDATA;
    sgWLAST <= WLAST;
    sgWSTRB <= WSTRB;
    sgWVALID <= WVALID;
    sgAXI_ACLK <= AXI_ACLK;
    sgAXI_ARESETN <= AXI_ARESETN;
    -- 'outports'
    ARREADY <= sgARREADY;
    AWREADY <= sgAWREADY;
    BID <= sgBID(C_S_AXI_ID_WIDTH-1 downto 0);
    BRESP <= sgBRESP;
    BVALID <= sgBVALID;
    RDATA <= sgRDATA;
    RID <= sgRID(C_S_AXI_ID_WIDTH-1 downto 0);
    RLAST <= sgRLAST;
    RRESP <= sgRRESP;
    RVALID <= sgRVALID;
    WREADY <= sgWREADY;

end architecture imp;
