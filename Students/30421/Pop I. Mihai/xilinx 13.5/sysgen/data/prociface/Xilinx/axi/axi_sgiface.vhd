library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity axi_sgiface is
    generic (
        -- AXI specific.
        -- TODO: need to figure out a way to pass these generics from outside
        C_S_AXI_SUPPORT_BURST   : integer := 0;
        -- TODO: fix the internal ID width to 8
        C_S_AXI_ID_WIDTH        : integer := 8;
        C_S_AXI_DATA_WIDTH      : integer := 32;
        C_S_AXI_ADDR_WIDTH      : integer := 32;
        C_S_AXI_TOTAL_ADDR_LEN  : integer := $T.memmap_info.totalAddr_len$;
        C_S_AXI_LINEAR_ADDR_LEN : integer := $T.memmap_info.linearAddr_len$;
        C_S_AXI_BANK_ADDR_LEN   : integer := $T.memmap_info.bankAddr_len$;
        C_S_AXI_AWLEN_WIDTH     : integer := 8;
        C_S_AXI_ARLEN_WIDTH     : integer := 8
    );
    port (
        -- General.
        AXI_AClk      : in  std_logic;
        AXI_AResetN    : in  std_logic;
        -- not used
        AXI_Ce        : in  std_logic;
  
        -- AXI Port.
        S_AXI_AWADDR  : in  std_logic_vector(C_S_AXI_ADDR_WIDTH-1 downto 0);
        S_AXI_AWID    : in  std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
        S_AXI_AWLEN   : in  std_logic_vector(C_S_AXI_AWLEN_WIDTH-1 downto 0);
        S_AXI_AWSIZE  : in  std_logic_vector(2 downto 0);
        S_AXI_AWBURST : in  std_logic_vector(1 downto 0);
        S_AXI_AWLOCK  : in  std_logic_vector(1 downto 0);
        S_AXI_AWCACHE : in  std_logic_vector(3 downto 0);
        S_AXI_AWPROT  : in  std_logic_vector(2 downto 0);
        S_AXI_AWVALID : in  std_logic;
        S_AXI_AWREADY : out std_logic;
        
        S_AXI_WLAST   : in  std_logic;
        S_AXI_WDATA   : in  std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
        S_AXI_WSTRB   : in  std_logic_vector((C_S_AXI_DATA_WIDTH/8)-1 downto 0);
        S_AXI_WVALID  : in  std_logic;
        S_AXI_WREADY  : out std_logic;
        
        S_AXI_BRESP   : out std_logic_vector(1 downto 0);
        S_AXI_BID     : out std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
        S_AXI_BVALID  : out std_logic;
        S_AXI_BREADY  : in  std_logic;
        
        S_AXI_ARADDR  : in  std_logic_vector(C_S_AXI_ADDR_WIDTH-1 downto 0);
        S_AXI_ARID    : in  std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
        S_AXI_ARLEN   : in  std_logic_vector(C_S_AXI_ARLEN_WIDTH-1 downto 0);
        S_AXI_ARSIZE  : in  std_logic_vector(2 downto 0);
        S_AXI_ARBURST : in  std_logic_vector(1 downto 0);
        S_AXI_ARLOCK  : in  std_logic_vector(1 downto 0);
        S_AXI_ARCACHE : in  std_logic_vector(3 downto 0);
        S_AXI_ARPROT  : in  std_logic_vector(2 downto 0);
        S_AXI_ARVALID : in  std_logic;
        S_AXI_ARREADY : out std_logic;
        
        -- 'From Register'
        $T.memmap_info.fromregs:{-- '$it.name$'
sm_$it.name$_dout : in std_logic_vector($it.n_bits$-1 downto 0);};separator="\n"$
        -- 'To Register'
        $T.memmap_info.toregs:{-- '$it.name$'
sm_$it.name$_dout : in std_logic_vector($it.n_bits$-1 downto 0);
sm_$it.name$_din  : out std_logic_vector($it.n_bits$-1 downto 0);
sm_$it.name$_en   : out std_logic;};separator="\n"$
        -- 'From FIFO'
        $T.memmap_info.fromfifos:{-- '$it.name$'
sm_$it.name$_dout  : in  std_logic_vector($it.n_bits$-1 downto 0);
sm_$it.name$_pfull : in  std_logic_vector($it.percent_nbits$-1 downto 0);
sm_$it.name$_empty : in  std_logic;
sm_$it.name$_re    : out std_logic;};separator="\n"$
        -- 'To FIFO'
        $T.memmap_info.tofifos:{-- '$it.name$'
sm_$it.name$_din   : out std_logic_vector($it.n_bits$-1 downto 0);
sm_$it.name$_we    : out std_logic;
sm_$it.name$_pfull : in  std_logic_vector($it.percent_nbits$-1 downto 0);
sm_$it.name$_full  : in  std_logic;};separator="\n"$
        -- 'Shared Memory'
        $T.memmap_info.shmems:{-- '$it.name$'
sm_$it.name$_dout  : in  std_logic_vector($it.n_bits$-1 downto 0);
sm_$it.name$_addr  : out std_logic_vector($it.width$-1 downto 0);
sm_$it.name$_din   : out std_logic_vector($it.n_bits$-1 downto 0);
sm_$it.name$_we    : out std_logic;};separator="\n"$
$if(T.memmap_info.shmems)$        shram_en : out std_logic;$endif$
$if(T.memmap_debug)$        debug : out std_logic_vector(31 downto 0);$endif$

        S_AXI_RLAST   : out std_logic;
        S_AXI_RID     : out std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
        S_AXI_RDATA   : out std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
        S_AXI_RRESP   : out std_logic_vector(1 downto 0);
        S_AXI_RVALID  : out std_logic;
        S_AXI_RREADY  : in  std_logic
    );
end entity axi_sgiface;

architecture IMP of axi_sgiface is

-- Internal signals for write channel.
signal S_AXI_BVALID_i       : std_logic;
signal S_AXI_BID_i          : std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);
signal S_AXI_WREADY_i       : std_logic;
  
-- Internal signals for read channels.
signal S_AXI_ARLEN_i        : std_logic_vector(C_S_AXI_ARLEN_WIDTH-1 downto 0);
signal S_AXI_RLAST_i        : std_logic;
signal S_AXI_RREADY_i       : std_logic;
signal S_AXI_RVALID_i       : std_logic;
signal S_AXI_RDATA_i        : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal S_AXI_RID_i          : std_logic_vector(C_S_AXI_ID_WIDTH-1 downto 0);

-- for read channel
signal read_bank_addr_i     : std_logic_vector(C_S_AXI_BANK_ADDR_LEN-1 downto 0);
signal read_linear_addr_i   : std_logic_vector(C_S_AXI_LINEAR_ADDR_LEN-1 downto 0);
-- for write channel
signal write_bank_addr_i    : std_logic_vector(C_S_AXI_BANK_ADDR_LEN-1 downto 0);
signal write_linear_addr_i  : std_logic_vector(C_S_AXI_LINEAR_ADDR_LEN-1 downto 0);

signal reg_bank_out_i       : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal fifo_bank_out_i      : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal shmem_bank_out_i     : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
    
-- 'From Register'
$T.memmap_info.fromregs:{-- '$it.name$'
signal sm_$it.name$_dout_i  : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);};separator="\n"$
-- 'To Register'
$T.memmap_info.toregs:{-- '$it.name$'
signal sm_$it.name$_din_i   : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal sm_$it.name$_en_i    : std_logic;
signal sm_$it.name$_dout_i  : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);};separator="\n"$
-- 'From FIFO'
$T.memmap_info.fromfifos:{-- '$it.name$'
signal sm_$it.name$_dout_i  : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal sm_$it.name$_pfull_i : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal sm_$it.name$_empty_i : std_logic;
signal sm_$it.name$_re_i    : std_logic;};separator="\n"$
-- 'To FIFO'
$T.memmap_info.tofifos:{-- '$it.name$'
signal sm_$it.name$_din_i   : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal sm_$it.name$_pfull_i : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal sm_$it.name$_full_i  : std_logic;
signal sm_$it.name$_we_i    : std_logic;};separator="\n"$
-- 'Shared Memory'
$T.memmap_info.shmems:{-- '$it.name$'
signal sm_$it.name$_addr_i  : std_logic_vector(C_S_AXI_LINEAR_ADDR_LEN-1 downto 0);
signal sm_$it.name$_din_i   : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);
signal sm_$it.name$_we_i    : std_logic;
signal sm_$it.name$_dout_i  : std_logic_vector(C_S_AXI_DATA_WIDTH-1 downto 0);};separator="\n"$

type t_read_state is (IDLE, READ_PREP, READ_DATA);
signal read_state : t_read_state;

type t_write_state is (IDLE, WRITE_DATA, WRITE_RESPONSE);
signal write_state : t_write_state;

type t_memmap_state is (READ, WRITE);
signal memmap_state : t_memmap_state;

constant C_READ_PREP_DELAY : std_logic_vector(1 downto 0) := "11";

signal read_prep_counter : std_logic_vector(1 downto 0);
signal read_addr_counter : std_logic_vector(C_S_AXI_ARLEN_WIDTH-1 downto 0);
signal read_data_counter : std_logic_vector(C_S_AXI_ARLEN_WIDTH-1 downto 0);

-- enable of shared BRAMs
signal s_shram_en : std_logic;

signal write_addr_valid : std_logic;
signal write_ready : std_logic;

-- 're' of From/To FIFOs
signal s_fifo_re : std_logic;
-- 'we' of To FIFOs
signal s_fifo_we : std_logic;

begin

-- enable for 'Shared Memory' blocks
$if(T.memmap_info.shmems)$shram_en <= s_shram_en;$endif$
$T.memmap_info.shmems:{-- $it.name$ din
sm_$it.name$_din_i <= S_AXI_WDATA;};separator="\n"$

-- conversion to match with the data bus width
-- 'From Register'
$T.memmap_info.fromregs:{-- '$it.name$'
gen_sm_$it.name$_dout_i: if ($it.n_bits$ < C_S_AXI_DATA_WIDTH) generate
    sm_$it.name$_dout_i(C_S_AXI_DATA_WIDTH-1 downto $it.n_bits$) <= (others => '0');
end generate gen_sm_$it.name$_dout_i;
sm_$it.name$_dout_i($it.n_bits$-1 downto 0) <= sm_$it.name$_dout;};separator="\n"$
-- 'To Register'
$T.memmap_info.toregs:{-- '$it.name$'
sm_$it.name$_din     <= sm_$it.name$_din_i($it.n_bits$-1 downto 0);
sm_$it.name$_en      <= sm_$it.name$_en_i;
gen_sm_$it.name$_dout_i: if ($it.n_bits$ < C_S_AXI_DATA_WIDTH) generate
    sm_$it.name$_dout_i(C_S_AXI_DATA_WIDTH-1 downto $it.n_bits$) <= (others => '0');
end generate gen_sm_$it.name$_dout_i;
sm_$it.name$_dout_i($it.n_bits$-1 downto 0) <= sm_$it.name$_dout;};separator="\n"$
-- 'From FIFO'
$T.memmap_info.fromfifos:{-- '$it.name$'
gen_sm_$it.name$_dout_i: if ($it.n_bits$ < C_S_AXI_DATA_WIDTH) generate
    sm_$it.name$_dout_i(C_S_AXI_DATA_WIDTH-1 downto $it.n_bits$) <= (others => '0');
end generate gen_sm_$it.name$_dout_i;
sm_$it.name$_dout_i($it.n_bits$-1 downto 0) <= sm_$it.name$_dout;
gen_sm_$it.name$_pfull_i: if ($it.percent_nbits$ < C_S_AXI_DATA_WIDTH) generate
    sm_$it.name$_pfull_i(C_S_AXI_DATA_WIDTH-1 downto $it.percent_nbits$) <= (others => '0');
end generate gen_sm_$it.name$_pfull_i;
sm_$it.name$_pfull_i($it.percent_nbits$-1 downto 0) <= sm_$it.name$_pfull;
sm_$it.name$_empty_i <= sm_$it.name$_empty;
sm_$it.name$_re      <= sm_$it.name$_re_i;};separator="\n"$
-- 'To FIFO'
$T.memmap_info.tofifos:{-- '$it.name$'
sm_$it.name$_din    <= sm_$it.name$_din_i($it.n_bits$-1 downto 0);
gen_sm_$it.name$_pfull_i: if ($it.percent_nbits$ < C_S_AXI_DATA_WIDTH) generate
    sm_$it.name$_pfull_i(C_S_AXI_DATA_WIDTH-1 downto $it.percent_nbits$) <= (others => '0');
end generate gen_sm_$it.name$_pfull_i;
sm_$it.name$_pfull_i($it.percent_nbits$-1 downto 0) <= sm_$it.name$_pfull;
sm_$it.name$_full_i <= sm_$it.name$_full;
sm_$it.name$_we     <= sm_$it.name$_we_i;};separator="\n"$
-- 'Shared Memory'
$T.memmap_info.shmems:{-- '$it.name$'
sm_$it.name$_addr <= sm_$it.name$_addr_i($it.width$-1 downto 0);
sm_$it.name$_din  <= sm_$it.name$_din_i($it.n_bits$-1 downto 0);
gen_sm_$it.name$_dout_i: if ($it.n_bits$ < C_S_AXI_DATA_WIDTH) generate
    sm_$it.name$_dout_i(C_S_AXI_DATA_WIDTH-1 downto $it.n_bits$) <= (others => '0');
end generate gen_sm_$it.name$_dout_i;
sm_$it.name$_dout_i($it.n_bits$-1 downto 0) <= sm_$it.name$_dout;
sm_$it.name$_we <= sm_$it.name$_we_i;};separator="\n"$

ReadWriteSelect: process(memmap_state) is begin
    if (memmap_state = READ) then
        $T.memmap_info.shmems:{-- '$it.name$'
sm_$it.name$_addr_i <= read_linear_addr_i;}$
    else
        $T.memmap_info.shmems:{-- '$it.name$'
sm_$it.name$_addr_i <= write_linear_addr_i;}$
    end if;
end process ReadWriteSelect;

-----------------------------------------------------------------------------
-- address for 'Shared Memory'
-----------------------------------------------------------------------------
SharedMemory_Addr_ResetN : process(AXI_AClk) is begin
    if (AXI_AClk'event and AXI_AClk = '1') then
        if (AXI_AResetN = '0') then
            memmap_state <= READ;
        else
            if (S_AXI_AWVALID = '1') then
                -- write operation
                memmap_state <= WRITE;
            elsif (S_AXI_ARVALID = '1') then
                -- read operation
                memmap_state <= READ;
            end if;
        end if;
    end if;
end process SharedMemory_Addr_ResetN;

-----------------------------------------------------------------------------
-- WRITE Command Control
-----------------------------------------------------------------------------
S_AXI_BID     <= S_AXI_BID_i;
S_AXI_BVALID  <= S_AXI_BVALID_i;
S_AXI_WREADY  <= S_AXI_WREADY_i;
-- No error checking
S_AXI_BRESP  <= (others=>'0');

PROC_AWREADY_ACK: process(read_state, write_state, S_AXI_ARVALID, S_AXI_AWVALID) is begin
    if (write_state = IDLE and S_AXI_AWVALID = '1' and read_state = IDLE) then
        S_AXI_AWREADY <= S_AXI_AWVALID;
    else
        S_AXI_AWREADY <= '0';
    end if;
end process PROC_AWREADY_ACK;

Cmd_Decode_Write: process(AXI_AClk) is begin
    if (AXI_AClk'event and AXI_AClk = '1') then
        if (AXI_AResetN = '0') then
            write_addr_valid    <= '0';
            write_ready         <= '0';
            s_fifo_we           <= '0';
            S_AXI_BVALID_i      <= '0';
            S_AXI_BID_i         <= (others => '0');
            write_bank_addr_i   <= (others => '0');
            write_linear_addr_i <= (others => '0');
        else
            if (write_state = IDLE) then
                if (S_AXI_AWVALID = '1' and read_state = IDLE) then
                    -- reflect awid
                    S_AXI_BID_i <= S_AXI_AWID;

                    -- latch bank and linear addresses
                    write_bank_addr_i   <= S_AXI_AWADDR(C_S_AXI_TOTAL_ADDR_LEN-1 downto C_S_AXI_LINEAR_ADDR_LEN+2);
                    write_linear_addr_i <= S_AXI_AWADDR(C_S_AXI_LINEAR_ADDR_LEN+1 downto 2);
                    write_addr_valid <= '1';
                    s_fifo_we <= '1';

                    -- write state transition
                    write_state <= WRITE_DATA;
                end if;
            elsif (write_state = WRITE_DATA) then
                write_ready <= '1';
                s_fifo_we <= '0';
                write_addr_valid <= S_AXI_WVALID;
                
                if (S_AXI_WVALID = '1' and write_ready = '1') then
                    write_linear_addr_i <= Std_Logic_Vector(unsigned(write_linear_addr_i) + 1);
                end if;

                if (S_AXI_WLAST = '1' and write_ready = '1') then
                    -- start responding through B channel upon the last write data sample
                    S_AXI_BVALID_i <= '1';
                    -- write data is over
                    write_addr_valid <= '0';
                    write_ready <= '0';
                    -- write state transition
                    write_state <= WRITE_RESPONSE;
                end if;
            elsif (write_state = WRITE_RESPONSE) then

                if (S_AXI_BREADY = '1') then
                    -- write respond is over
                    S_AXI_BVALID_i <= '0';
                    S_AXI_BID_i <= (others => '0');

                    -- write state transition
                    write_state <= IDLE;
                end if;
            end if;
        end if;
    end if;
end process Cmd_Decode_Write;

Write_Linear_Addr_Decode : process(AXI_AClk) is 

begin
    if (AXI_AClk'event and AXI_AClk = '1') then
        if (AXI_AResetN = '0') then
            -- 'To Register'
            $T.memmap_info.toregs:{-- $it.name$ din/en
sm_$it.name$_din_i <= (others => '0');
sm_$it.name$_en_i <= '0';};separator="\n"$
            -- 'To FIFO'
            $T.memmap_info.tofifos:{-- $it.name$ din/en
sm_$it.name$_din_i <= (others => '0');
sm_$it.name$_we_i <= '0';};separator="\n"$
            -- 'Shared Memory'
            $T.memmap_info.shmems:{-- $it.name$ we
sm_$it.name$_we_i <= '0';};separator="\n"$
        else
            -- default assignments
            $T.memmap_info.shmems:{-- $it.name$ we
sm_$it.name$_we_i <= '0';};separator="\n"$

$if(T.memmap_info.toregs)$
            -- 'To Register'
            if (unsigned(write_bank_addr_i) = 2) then
                $T.memmap_info.toregs:{if (unsigned(write_linear_addr_i) = $it.addr$) then
    -- $it.name$ din/en
    sm_$it.name$_din_i <= S_AXI_WDATA;
    sm_$it.name$_en_i  <= write_addr_valid;};separator="\nels"$
                $if(T.memmap_info.toregs)$                end if;$endif$
            end if;
$endif$        
$if(T.memmap_info.tofifos)$
            -- 'To FIFO'
            if (unsigned(write_bank_addr_i) = 1) then
                $T.memmap_info.tofifos:{if (unsigned(write_linear_addr_i) = $it.addr$) then
    -- $it.name$ din/we
    sm_$it.name$_din_i <= S_AXI_WDATA;
    sm_$it.name$_we_i  <= s_fifo_we and (not sm_$it.name$_full_i);};separator="\nels"$
                $if(T.memmap_info.tofifos)$                end if;$endif$
            end if;
$endif$        
$if(T.memmap_info.shmems)$
            -- 'Shared Memory'
            if unsigned(write_bank_addr_i) = 0 then
                $T.memmap_info.shmems:{if (unsigned(write_linear_addr_i(C_S_AXI_LINEAR_ADDR_LEN-1 downto $it.width$)) = $it.pref$) then
    -- $it.name$ we
    sm_$it.name$_we_i  <= write_addr_valid;};separator="\nels"$
                $if(T.memmap_info.shmems)$                end if;$endif$
            end if;
$endif$        
        end if;
    end if;
end process Write_Linear_Addr_Decode;
 
-----------------------------------------------------------------------------
-- READ Control
-----------------------------------------------------------------------------

S_AXI_RDATA  <= S_AXI_RDATA_i;
S_AXI_RVALID  <= S_AXI_RVALID_i;
S_AXI_RLAST   <= S_AXI_RLAST_i;
S_AXI_RID     <= S_AXI_RID_i;
-- TODO: no error checking
S_AXI_RRESP <= (others=>'0');

PROC_ARREADY_ACK: process(read_state, S_AXI_ARVALID, write_state, S_AXI_AWVALID) is begin
    -- Note: WRITE has higher priority than READ
    if (read_state = IDLE and S_AXI_ARVALID = '1' and write_state = IDLE and S_AXI_AWVALID /= '1') then
        S_AXI_ARREADY <= S_AXI_ARVALID;
    else
        S_AXI_ARREADY <= '0';
    end if;
end process PROC_ARREADY_ACK;

S_AXI_WREADY_i <= write_ready;

Process_Sideband: process(write_state, read_state) is begin
    if (read_state = READ_PREP) then
        s_shram_en <= '1';
    elsif (read_state = READ_DATA) then
        s_shram_en <= S_AXI_RREADY;
    elsif (write_state = WRITE_DATA) then
        s_shram_en <= S_AXI_WVALID;
    else
        s_shram_en <= '0';
    end if;
end process Process_Sideband;

Cmd_Decode_Read: process(AXI_AClk) is begin
    if (AXI_AClk'event and AXI_AClk = '1') then
        if (AXI_AResetN = '0') then
            S_AXI_RVALID_i <= '0';
            read_bank_addr_i    <= (others => '0');
            read_linear_addr_i  <= (others => '0');
            S_AXI_ARLEN_i       <= (others => '0');
            S_AXI_RLAST_i       <= '0';
            S_AXI_RID_i         <= (others => '0');
            read_state          <= IDLE;
            read_prep_counter   <= (others => '0');
            read_addr_counter   <= (others => '0');
            read_data_counter   <= (others => '0');
        else
            -- default assignments
            s_fifo_re <= '0';

            if (read_state = IDLE) then
                -- Note WRITE has higher priority than READ
                if (S_AXI_ARVALID = '1' and write_state = IDLE and S_AXI_AWVALID /= '1') then
                    -- extract bank and linear addresses
                    read_bank_addr_i    <= S_AXI_ARADDR(C_S_AXI_TOTAL_ADDR_LEN-1 downto C_S_AXI_LINEAR_ADDR_LEN+2);
                    read_linear_addr_i  <= S_AXI_ARADDR(C_S_AXI_LINEAR_ADDR_LEN+1 downto 2);
                    s_fifo_re <= '1';

                    -- reflect arid
                    S_AXI_RID_i <= S_AXI_ARID;

                    -- load read liner address and data counter
                    read_addr_counter <= S_AXI_ARLEN;
                    read_data_counter <= S_AXI_ARLEN;

                    -- load read preparation counter
                    read_prep_counter <= C_READ_PREP_DELAY;
                    -- read state transition
                    read_state <= READ_PREP;
                end if;
            elsif (read_state = READ_PREP) then
                if (unsigned(read_prep_counter) = 0) then
                    if (unsigned(read_data_counter) = 0) then
                        -- tag the last data generated by the slave
                        S_AXI_RLAST_i <= '1';
                    end if;
                    -- valid data appears
                    S_AXI_RVALID_i <= '1';
                    -- read state transition
                    read_state <= READ_DATA;
                else
                    -- decrease read preparation counter
                    read_prep_counter <= Std_Logic_Vector(unsigned(read_prep_counter) - 1);
                end if;

                if (unsigned(read_prep_counter) /= 3 and unsigned(read_addr_counter) /= 0) then
                    -- decrease address counter
                    read_addr_counter <= Std_Logic_Vector(unsigned(read_addr_counter) - 1);
                    -- increase linear address (no band crossing)
                    read_linear_addr_i <= Std_Logic_Vector(unsigned(read_linear_addr_i) + 1);
                end if;
            elsif (read_state = READ_DATA) then
                if (S_AXI_RREADY = '1') then
                    if (unsigned(read_data_counter) = 1) then
                        -- tag the last data generated by the slave
                        S_AXI_RLAST_i <= '1';
                    end if;

                    if (unsigned(read_data_counter) = 0) then
                        -- arid
                        S_AXI_RID_i <= (others => '0');
                        -- rlast
                        S_AXI_RLAST_i <= '0';
                        -- no more valid data
                        S_AXI_RVALID_i <= '0';
                        -- read state transition
                        read_state <= IDLE;
                    else
                        -- decrease read preparation counter
                        read_data_counter <= Std_Logic_Vector(unsigned(read_data_counter) - 1);

                        if (unsigned(read_addr_counter) /= 0) then
                            -- decrease address counter
                            read_addr_counter <= Std_Logic_Vector(unsigned(read_addr_counter) - 1);
                            -- increase linear address (no band crossing)
                            read_linear_addr_i <= Std_Logic_Vector(unsigned(read_linear_addr_i) + 1);
                        end if;
                    end if;
                end if;
            end if;

        end if;
    end if;
end process Cmd_Decode_Read;

Read_Linear_Addr_Decode : process(AXI_AClk) is begin
    if (AXI_AClk'event and AXI_AClk = '1') then
        if (AXI_AResetN = '0') then
            reg_bank_out_i   <= (others => '0');
            fifo_bank_out_i  <= (others => '0');
            shmem_bank_out_i <= (others => '0');
            S_AXI_RDATA_i    <= (others => '0');
        else
            if (unsigned(read_bank_addr_i) = 2) then
                -- 'From Register'
                $T.memmap_info.fromregs:{if (unsigned(read_linear_addr_i) = $it.addr$) then
    -- '$it.name$' dout
    reg_bank_out_i <= sm_$it.name$_dout_i;};separator="\nels"$
$if(T.memmap_info.fromregs)$                end if;$endif$
                $if(_isvec.(T.memmap_info.reg_readback))$
                -- 'To Register' (with register readback)
                $T.memmap_info.toregs:{if (unsigned(read_linear_addr_i) = $it.addr$) then
    -- '$it.name$' dout
    reg_bank_out_i <= sm_$it.name$_dout_i;};separator="\nels"$
$if(T.memmap_info.toregs)$                end if;$endif$
                $endif$

                S_AXI_RDATA_i <= reg_bank_out_i;
            elsif (unsigned(read_bank_addr_i) = 1) then
                -- 'From FIFO'
                $T.memmap_info.fromfifos:{if (unsigned(read_linear_addr_i) = $it.addr$) then
    -- '$it.name$' re
    sm_$it.name$_re_i <= s_fifo_re;
end if;
if (unsigned(read_linear_addr_i) = $it.addr$) then
    -- '$it.name$' dout
    fifo_bank_out_i <= sm_$it.name$_dout_i;
elsif (unsigned(read_linear_addr_i) = $it.pfull.addr$) then
    -- '$it.name$' pfull
    fifo_bank_out_i <= sm_$it.name$_pfull_i;
elsif (unsigned(read_linear_addr_i) = $it.empty.addr$) then
    -- '$it.name$' empty
    fifo_bank_out_i <= (0 => sm_$it.name$_empty_i, others => '0');};separator="\nels"$
$if(T.memmap_info.fromfifos)$                end if;$endif$
                -- 'To FIFO'
                $T.memmap_info.tofifos:{if (unsigned(read_linear_addr_i) = $it.pfull.addr$) then
    -- '$it.name$' pfull
    fifo_bank_out_i <= sm_$it.name$_pfull_i;
elsif (unsigned(read_linear_addr_i) = $it.full.addr$) then
    -- '$it.name$' full
    fifo_bank_out_i <= (0 => sm_$it.name$_full_i, others => '0');};separator="\nels"$
$if(T.memmap_info.tofifos)$                end if;$endif$

                S_AXI_RDATA_i <= fifo_bank_out_i;
            elsif (unsigned(read_bank_addr_i) = 0 and s_shram_en = '1') then
                -- 'Shared Memory'
                $T.memmap_info.shmems:{if (unsigned(read_linear_addr_i(C_S_AXI_LINEAR_ADDR_LEN-1 downto $it.width$)) = $it.pref$) then
    -- '$it.name$' dout
    shmem_bank_out_i <= sm_$it.name$_dout_i;};separator="\nels"$
$if(T.memmap_info.shmems)$                end if;$endif$

                S_AXI_RDATA_i <= shmem_bank_out_i;
            end if;
        end if;
    end if;
end process Read_Linear_Addr_Decode;

end architecture IMP;
