
-------------------------------------------------------------------
-- System Generator version 13.4 VHDL source file.
--
-- Copyright(C) 2011 by Xilinx, Inc.  All rights reserved.  This
-- text/file contains proprietary, confidential information of Xilinx,
-- Inc., is distributed under license from Xilinx, Inc., and may be used,
-- copied and/or disclosed only pursuant to the terms of a valid license
-- agreement with Xilinx, Inc.  Xilinx hereby grants you a license to use
-- this text/file solely for design, simulation, implementation and
-- creation of design files limited to Xilinx devices or technologies.
-- Use with non-Xilinx devices or technologies is expressly prohibited
-- and immediately terminates your license unless covered by a separate
-- agreement.
--
-- Xilinx is providing this design, code, or information "as is" solely
-- for use in developing programs and solutions for Xilinx devices.  By
-- providing this design, code, or information as one possible
-- implementation of this feature, application or standard, Xilinx is
-- making no representation that this implementation is free from any
-- claims of infringement.  You are responsible for obtaining any rights
-- you may require for your implementation.  Xilinx expressly disclaims
-- any warranty whatsoever with respect to the adequacy of the
-- implementation, including but not limited to warranties of
-- merchantability or fitness for a particular purpose.
--
-- Xilinx products are not intended for use in life support appliances,
-- devices, or systems.  Use in such applications is expressly prohibited.
--
-- Any modifications that are made to the source code are done at the user's
-- sole risk and will be unsupported.
--
-- This copyright and support notice must be retained as part of this
-- text at all times.  (c) Copyright 1995-2011 Xilinx, Inc.  All rights
-- reserved.
-------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
library work;
use work.mac_fir_const_pkg.all;
use work.mac_fir_utils_pkg.all;
package mac_fir_comp_pkg is
  component mac_fir_v6_0_xst
  generic (
    C_FAMILY            : string;
    C_ELABORATION_DIR   : string  := "";
    C_COMPONENT_NAME    : string  := "";
    C_MEM_INIT_FILE     : string  := "COEFF";
    FILTER_TYPE         : integer := 0;
    INTERP_RATE         : integer := 1;
    DECIM_RATE          : integer := 1;
    RATE_CHANGE_TYPE    : integer := 0;
    ZERO_PACKING_FACTOR : integer := 0;
    NUM_CHANNELS        : integer := 1;
    CHAN_SEL_WIDTH      : integer := 4;
    NUM_TAPS            : integer := 16;
    CLK_PER_SAMP        : integer := 4;
    DATA_WIDTH          : integer := 16;
    COEF_WIDTH          : integer := 16;
    OUTPUT_WIDTH        : integer := 48;
    OUTPUT_REG          : integer := 1;
    SYMMETRY            : integer := 0;
    ODD_SYMMETRY        : integer := 0;
    NEG_SYMMETRY        : integer := 0;
    COEF_RELOAD         : integer := 0;
    NUM_FILTS           : integer := 1;
    FILTER_SEL_WIDTH    : integer := 4;
    C_HAS_SCLR          : integer := 1;
    C_HAS_CE            : integer := 0;
    C_HAS_ND            : integer := 1;
    C_HAS_RFD           : integer := 1;
    C_HAS_RDY           : integer := 1;
    DATA_MEMTYPE        : integer := 0;
    COEF_MEMTYPE        : integer := 0;
    MEMORY_PACK         : integer := 0;
    COL_MODE            : integer := 0;
    COL_1ST_LEN         : integer := 3;
    COL_WRAP_LEN        : integer := 4;
    COL_PIPE_LEN        : integer := 3;
    DATA_SIGN           : integer := 0;
    COEF_SIGN           : integer := 0;
    C_TAP_BALANCE       : integer := 0
  );
  port (
    RESET         : in  std_logic;
    CLK           : in  std_logic;
    CE            : in  std_logic;
    VIN           : in  std_logic;
    DIN           : in  std_logic_vector(DATA_WIDTH-1 downto 0);
    FILTER_SEL    : in  std_logic_vector(FILTER_SEL_WIDTH-1 downto 0);
    WE_FILTER_BLOCK   : out STD_LOGIC;
    COEF_CURRENT_PAGE : out STD_LOGIC;

    RFD           : out std_logic;
    VOUT          : out std_logic;
    DOUT          : out std_logic_vector(OUTPUT_WIDTH-1 downto 0);
    CHAN_OUT      : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0);
    CHAN_IN       : out std_logic_vector(CHAN_SEL_WIDTH-1 downto 0);

    DOUT_I        : out std_logic_vector(DATA_WIDTH-1 downto 0);
    DOUT_Q        : out std_logic_vector(OUTPUT_WIDTH-1 downto 0);
    COEF_LD           : in std_logic;
    COEF_WE           : in std_logic;
    COEF_DIN          : in std_logic_vector(COEF_WIDTH-1 downto 0)
  );
  end component;
  component filter_block
  generic (
    C_FAMILY            : string;
    C_ELABORATION_DIR   : string := "";
    C_COMPONENT_NAME    : string := "";
    FIR_REQS            : t_reqs;
    FIR_DETAILS         : t_details;
    COL_MODE            : integer := 0;
    COL_1ST_LEN         : integer := 3;
    COL_WRAP_LEN        : integer := 4;
    COL_PIPE_LEN        : integer := 3;
    C_HAS_CE            : integer := 0
  );
  port (
    CLK                 : in  std_logic;
    CE                  : in  std_logic;
    RESET               : in  std_logic;
    WE                  : in  std_logic;
    WE_SYM              : in  std_logic;
    DIN                 : in  std_logic_vector(FIR_REQS.data_width-1 downto 0);
    DATA_ADDR_IN        : in std_logic_vector(UTILS_PKG_bits_needed_to_represent
                             (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
    DATA_SYM_ADDR_IN    : in std_logic_vector(UTILS_PKG_bits_needed_to_represent
                             (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
    COEF_ADDR_IN        : in std_logic_vector(UTILS_PKG_bits_needed_to_represent
                             (fir_details.coef_mem_depth-1)-1 downto 0);
    WE_OUT              : out std_logic;
    WE_SYM_OUT          : out std_logic;
    PC_OUT              : out std_logic_vector(47 downto 0);
    P_OUT_LAST_MAC      : out std_logic_vector(47 downto 0);
    DATA_OUT_CENTRE_TAP : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
    SYM_ADDSUB_IN       : in std_logic;
    SYM_ADDSUB_OUT      : out std_logic;
    PHASE_END           : in std_logic;
    PHASE_START         : in std_logic;
    DOUT_SMAC           : out std_logic_vector(24 downto 0);
    COUT_SMAC           : out std_logic_vector(24 downto 0);
    COEF_LD           : in std_logic;
    COEF_WE           : in std_logic;
    COEF_DIN          : in std_logic_vector(FIR_REQS.coef_width-1 downto 0);
    COEF_RELOAD_PAGE_IN  : in std_logic;

    COEF_DIN_MID      : out std_logic_vector(FIR_REQS.coef_width-1 downto 0);
    COEF_WE_MID       : out std_logic;

    COEF_LD_COMPLETE  : out std_logic
  );
  end component;

  component filter_stage
  generic (
    C_FAMILY          : string;
    FIR_REQS          : t_reqs;
    FIR_DETAILS       : t_details;
    COL_TOP           : integer;
    COL_BOTTOM        : integer;
    LAST_STAGE        : integer;
    STAGE_NUM         : integer;
    C_ELABORATION_DIR : string;
    MIF_FILE          : string;
    LAST_COEF_STAGE   : integer;
    C_HAS_CE          : integer
  );
  port (
    CLK               : in  std_logic;
    CE                : in  std_logic;
    RESET             : in  std_logic;
    WE                : in  std_logic;
    WE_SYM            : in  std_logic;
    DIN               : in  std_logic_vector(fir_reqs.data_width-1 downto 0);
    DIN_SYM           : in  std_logic_vector(fir_reqs.data_width-1 downto 0);
    DATA_ADDR_IN      : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
    DATA_SYM_ADDR_IN  : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
    COEF_ADDR_IN      : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.coef_mem_depth-1)-1 downto 0);
    PC_IN             : in  std_logic_vector(47 downto 0);
    CARRY_IN          : in  std_logic_vector(47 downto 0);
    DIN_CASC          : in  std_logic_vector(29 downto 0);
    WE_OUT            : out std_logic;
    WE_OUT_SYM        : out std_logic;
    DOUT              : out std_logic_vector(fir_reqs.data_width-1 downto 0);
    DOUT_SYM          : out std_logic_vector(fir_reqs.data_width-1 downto 0);
    DOUT_MID          : out std_logic_vector(fir_reqs.data_width-1 downto 0);
    PC_OUT            : out std_logic_vector(47 downto 0);
    P_OUT             : out std_logic_vector(47 downto 0);
    DATA_ADDR_OUT     : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
    DATA_SYM_ADDR_OUT : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
    COEF_ADDR_OUT     : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.coef_mem_depth-1)-1 downto 0);
    DOUT_CASC         : out std_logic_vector(29 downto 0);
    DOUT_SMAC         : out std_logic_vector(24 downto 0);
    COUT_SMAC         : out std_logic_vector(24 downto 0);

    COEF_LD           : in std_logic;
    COEF_WE_IN        : in std_logic;
    COEF_WE_OUT       : out std_logic;
    COEF_LD_EN_IN     : in std_logic;
    COEF_LD_EN_OUT    : out std_logic;
    COEF_DIN          : in std_logic_vector(FIR_REQS.coef_width-1 downto 0);
    COEF_DOUT         : out std_logic_vector(FIR_REQS.coef_width-1 downto 0);
    COEF_RELOAD_PAGE_IN  : in std_logic;
    COEF_RELOAD_PAGE_OUT : out std_logic;

    CNTRL_EXTRA1_IN   : in std_logic;
    CNTRL_EXTRA2_IN   : in std_logic;
    CNTRL_EXTRA3_IN   : in std_logic;
    CNTRL_EXTRA1_OUT  : out std_logic;
    CNTRL_EXTRA2_OUT  : out std_logic;
    CNTRL_EXTRA3_OUT  : out std_logic;

    FIRST_PHASE       : in std_logic
  );
  end component;

  component filter_stage_pipe
  generic (
    FIR_REQS          : t_reqs;
    FIR_DETAILS       : t_details;
    LAST_DELAY        : integer := 0;
    C_HAS_CE          : integer := 0
  );
  port (
    CLK               : in  std_logic;
    CE                : in  std_logic;
    RESET             : in  std_logic;
    WE                : in  std_logic;
    WE_SYM            : in  std_logic;
    DIN               : in  std_logic_vector(FIR_REQS.data_width-1 downto 0);
    DIN_SYM           : in  std_logic_vector(FIR_REQS.data_width-1 downto 0);
    DATA_ADDR_IN      : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
    DATA_SYM_ADDR_IN  : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
    COEF_ADDR_IN      : in  std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.coef_mem_depth-1)-1 downto 0);
    PC_IN             : in  std_logic_vector(47 downto 0);

    WE_OUT            : out std_logic;
    WE_OUT_SYM        : out std_logic;
    DOUT              : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
    DOUT_SYM          : out std_logic_vector(FIR_REQS.data_width-1 downto 0);
    PC_OUT            : out std_logic_vector(47 downto 0);
    DATA_ADDR_OUT     : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.data_mem_depth-1)-1 downto 0);
    DATA_SYM_ADDR_OUT : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.datasym_mem_depth-1)-1 downto 0);
    COEF_ADDR_OUT     : out std_logic_vector(UTILS_PKG_bits_needed_to_represent
                            (FIR_DETAILS.coef_mem_depth-1)-1 downto 0)
  );
  end component;
  component data_phase_store
  generic (
    C_FAMILY        : string;
    data_width      : integer;
    fir_reqs        : t_reqs;
    fir_details     : t_details;
    filter_sel_store  : integer;
    C_HAS_CE        : integer
  );
  port (
    clk                 : in  std_logic;
    ce                  : in  std_logic;
    reset               : in  std_logic;
    we                  : in  std_logic;
    din                 : in  std_logic_vector(data_width-1 downto 0);
    re                  : in  std_logic;
    we_out              : out std_logic;
    data_out_centre_tap : out std_logic_vector(data_width-1 downto 0)
  );
  end component;
  component output_buffer
  generic (
    data_width  : integer;
    fir_reqs    : t_reqs;
    fir_details : t_details;
    C_HAS_CE    : integer;
    c_family    : string;
    C_ELABORATION_DIR : string
  );
  port (
    CLK  : in  std_logic;
    CE   : in  std_logic;
    CLR  : in  std_logic;
    WE   : in  std_logic;
    D    : in  std_logic_vector(data_width-1 downto 0);

    Q    : out std_logic_vector(data_width-1 downto 0);
    VOUT : out std_logic;

    LAST_PHASE: in std_logic;
    LAST_CHAN_PHASE: in std_logic
  );
  end component;
  component memory_structure
  generic (
    C_FAMILY          : string;
    fir_reqs          : t_reqs;
    fir_details       : t_details;
    C_ELABORATION_DIR : string;
    mif_file          : string;
    LAST_COEF_STAGE   : integer;
    C_HAS_CE          : integer
  );
  port (
    clk         : in  std_logic;
    ce          : in  std_logic;
    reset       : in  std_logic;
    c_addr      : in  std_logic_vector(max(log2_ext(fir_details.coef_mem_depth),1)-1 downto 0);
    d_addr      : in  std_logic_vector(max(log2_ext(fir_details.data_mem_depth),1)-1 downto 0);
    d_sym_addr  : in  std_logic_vector(max(log2_ext(fir_details.datasym_mem_depth),1)-1 downto 0);
    coef_out    : out std_logic_vector(fir_reqs.coef_width-1 downto 0);

    data_in     : in  std_logic_vector(fir_reqs.data_width-1 downto 0);
    data_out    : out std_logic_vector(fir_reqs.data_width-1 downto 0);
    we          : in  std_logic;

    datasym_in  : in  std_logic_vector(fir_reqs.data_width-1 downto 0);
    datasym_out : out std_logic_vector(fir_reqs.data_width-1 downto 0);
    we_sym      : in  std_logic;
    coef_ld         : in std_logic;
    coef_we_in      : in std_logic;
    coef_we_out     : out std_logic;
    coef_ld_en_in   : in std_logic;
    coef_ld_en_out  : out std_logic;
    coef_din        : in std_logic_vector(fir_reqs.coef_width-1 downto 0);
    coef_dout       : out std_logic_vector(fir_reqs.coef_width-1 downto 0);
    coef_reload_page_in  : in std_logic;
    coef_reload_page_out : out std_logic

  );
  end component;
  component shift_ram
  generic (
    C_FAMILY       : string;
    C_DEPTH        : integer;
    C_WIDTH        : integer;
    C_HAS_CE       : integer;
    C_SHIFT_TYPE   : integer := 0;
    C_ENABLE_RLOCS : integer
  );
  port (
    CLK   : in  std_logic;
    WE    : in  std_logic;
    CE    : in  std_logic;
    SCLR  : in  std_logic;
    ADDR  : in  std_logic_vector
                (UTILS_PKG_bits_needed_to_represent(C_DEPTH-1)-1 downto 0);
    D     : in  std_logic_vector(C_WIDTH-1 downto 0);
    Q     : out std_logic_vector(C_WIDTH-1 downto 0)
  );
  end component;
  component shift_ram_fixed
  generic (
    C_FAMILY     : string;
    C_DEPTH      : integer;
    C_WIDTH      : integer
  );
  port (
    CLK   : in  std_logic;
    CE    : in  std_logic;
    D     : in  std_logic_vector(C_WIDTH-1 downto 0);
    Q     : out std_logic_vector(C_WIDTH-1 downto 0)
  );
  end component;
  component shift_ram_bit
  generic (
    C_FAMILY       : string;
    C_DEPTH        : integer;
    C_ENABLE_RLOCS : integer
  );
  port (
    CLK   : in  std_logic;
    CE    : in  std_logic;
    SCLR  : in  std_logic;
    D     : in  std_logic;
    Q     : out std_logic
  );
  end component;
  component addr_gen
  generic (
    C_FAMILY              : string;
    C_ELABORATION_DIR     : string  := "";
    C_COMPONENT_NAME      : string  := "";
    d_addr_width          : integer := 14;
    d_sym_addr_width      : integer := 14;
    c_addr_width          : integer := 14;

    filter_type           : integer := 0;
    symmetrical           : integer := 1;
    num_channels          : integer := 2;
    num_filters           : integer := 1;
    num_phases            : integer := 1;
    calculation_cycles    : integer := 5;
    input_cycles          : integer := 7;
    pq_p_val              : integer :=5;
    pq_q_val              : integer :=2;

    memory_type           : integer := 1;
    coef_memory_type      : integer := 0;
    memory_packed         : integer := 1;
    coef_offset           : integer := 0;
    sym_offset            : integer := 0;
    data_width            : integer := 8;
    odd_symmetry          : integer := 0;
    no_data_mem           : integer := 0;
    last_data_earily      : integer := 0;

    ip_buffer_type        : integer := 0;

    hb_single_mac         : integer := 0;
    C_HAS_CE              : integer := 0
  );
  port (
    clk                     : in std_logic;
    ce                      : in std_logic;
    reset                   : in std_logic;
    new_data                : in std_logic;

    write_data              : out std_logic;
    last_data               : out std_logic;
    rfd                     : out std_logic;
    data_addr               : out std_logic_vector(d_addr_width-1 downto 0);
    data_sym_addr           : out std_logic_vector(d_sym_addr_width-1 downto 0);
    coef_addr               : out std_logic_vector(c_addr_width-1 downto 0);

    accumulate              : out std_logic;
    load_accum              : out std_logic;
    load_accum_phase0       : out std_logic;
    latch_accum             : out std_logic;
    latch_accum_pq_deci     : out std_logic;
    sym_addsub              : out std_logic;
    chan_phase_max_out      : out std_logic;
    phase_max_out           : out std_logic;
    px_start                : out std_logic;

    filter                  : in std_logic_vector(max(1,log2_ext(num_filters))-1 downto 0);
    data_in                 : in std_logic_vector(data_width-1 downto 0);
    data_out                : out std_logic_vector(data_width-1 downto 0);
    data_out_hb_bypass      : out std_logic_vector(data_width-1 downto 0);
    filt_out_hb_bypass      : out std_logic_vector(max(1,log2_ext(num_filters))-1 downto 0);
    write_data_hb_bypass    : out std_logic
  );
  end component;
  component drom
  generic(
    C_FAMILY          : string;
    data_width        : integer;
    depth             : integer;
    addr_width        : integer;
    c_elaboration_dir : string;
    mif_file          : string;
    C_HAS_CE          : integer
  );
  port(
    addr  : in  std_logic_vector(addr_width-1  downto 0);
    data  : out std_logic_vector(data_width-1 downto 0);

    clk   : std_logic;
    ce    : std_logic
  );
  end component;

  component cmp_carrychain
  generic(
          width: integer;
          const: integer;
          value: integer;
          c_family: string
  );
  port(
       cin: in std_logic;
       A:in std_logic_vector(width-1 downto 0);
       B:in std_logic_vector(width-1 downto 0);
       Res:out std_logic;
       cout: out std_logic;
       ce: in std_logic;
       sclr: in std_logic;
       clk: in std_logic
  );
  end component;

  component cmp_carrychain_dualval
  generic(
          width: integer;
          value1: integer;
          value2: integer;
          c_family: string
  );
  port(
       cin: in std_logic;
       A:in std_logic_vector(width-1 downto 0);
       sel : in std_logic;
       Res:out std_logic;
       cout: out std_logic;

       ce: in std_logic;
       sclr: in std_logic;
       clk: in std_logic
  );
  end component;

  component priority_counter
    generic (
          C_HAS_C_IN   : integer := 0;
          C_WIDTH      : integer := 8;
          C_CONST_0_VAL    : integer := 1;
          C_CONST_1_VAL    : integer := 2;
          C_CONST_2_VAL    : integer := 3;
          C_INIT_SCLR_VAL  : integer :=0
            );
    port (
          A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);

          ADD_C0     : in std_logic:='0';
          ADD_C1     : in std_logic:='0';
          ADD_C2     : in std_logic:='0';

          C_IN       : in  std_logic := '0';
          D          : out std_logic_vector(C_WIDTH-1 downto 0);

          CE        : in  std_logic := '1';
          SCLR      : in  std_logic := '0';
          CLK       : in  std_logic := '0'
         );
  end component;

  component two_priority_counter
    generic (
          C_HAS_C_IN   : integer := 0;
          C_WIDTH      : integer := 8;
          C_CONST_0_VAL    : integer := 1;
          C_CONST_1_VAL    : integer := 2;
          C_CONST_2_VAL    : integer := 3;
          C_INIT_SCLR_VAL  : integer :=0
            );
    port (
          A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
          ADD_C0     : in std_logic:='0';
          ADD_C1     : in std_logic:='0';
          ADD_C2     : in std_logic:='0';
          C_IN       : in  std_logic := '0';
          D          : out std_logic_vector(C_WIDTH-1 downto 0);

          CE        : in  std_logic := '1';
          SCLR      : in  std_logic := '0';
          CLK       : in  std_logic := '0'
         );
  end component;

  component combined_priority_counter
    generic (
          C_HAS_C_IN   : integer := 0;
          C_WIDTH      : integer := 8;
          C_CONST_0_VAL    : integer := 1;
          C_CONST_1_VAL    : integer := 2;
          C_CONST_2_VAL    : integer := 3;
          C_INIT_SCLR_VAL  : integer :=0
            );
    port (
          A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);

          ADD_C0     : in std_logic:='0';
          ADD_C1     : in std_logic:='0';
          ADD_C2     : in std_logic:='0';

          C_IN       : in  std_logic := '0';
          D          : out std_logic_vector(C_WIDTH-1 downto 0);

          CE        : in  std_logic := '1';
          SCLR      : in  std_logic := '0';
          CLK       : in  std_logic := '0'
         );
  end component;

  component combined_enable_counter
  generic (
        C_HAS_C_IN   : integer := 0;
        C_WIDTH      : integer := 8;
        C_CONST_0_VAL    : integer := 1;
        C_CONST_1_VAL    : integer := 2;
        C_CONST_2_VAL    : integer := 3;
        C_INIT_SCLR_VAL  : integer :=0
          );
  port (
        A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
        ADD_C0     : in std_logic:='0';
        ADD_C1     : in std_logic:='0';
        ADD_C2     : in std_logic:='0';
        C_IN       : in  std_logic := '0';
        D          : out std_logic_vector(C_WIDTH-1 downto 0);
        CE        : in  std_logic := '1';
        SCLR      : in  std_logic := '0';
        CLK       : in  std_logic := '0'
       );
  end component;

  component code_counter
  generic (
        C_HAS_C_IN   : integer := 0;
        C_WIDTH      : integer := 8;
        C_INIT_SCLR_VAL  : integer :=0;
        C_VAL_0          : integer := 1;
        C_VAL_1          : integer := 1;
        C_VAL_2          : integer := 1;
        C_VAL_3          : integer := 1;
        C_VAL_4          : integer := 1;
        C_VAL_5          : integer := 1;
        C_VAL_6          : integer := 1;
        C_VAL_7          : integer := 1
          );
  port (
        A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
        CODE       : in std_logic_vector(2 downto 0);
        C_IN       : in  std_logic := '0';
        D          : out std_logic_vector(C_WIDTH-1 downto 0);
        CE        : in  std_logic := '1';
        SCLR      : in  std_logic := '0';
        CLK       : in  std_logic := '0'
       );
  end component;

  component block_shift_buffer
  generic (
    C_FAMILY     : string;
    C_DEPTH      : integer;
    C_WIDTH      : integer;
    C_RATE       : integer;
    C_FLIP_ORDER : integer
  );
  port (
    CLK   : in  std_logic;
    CE    : in  std_logic;
    WE    : in  std_logic;
    SCLR  : in  std_logic;
    RE    : in  std_logic;
    D     : in  std_logic_vector(C_WIDTH-1 downto 0);
    Q     : out std_logic_vector(C_WIDTH-1 downto 0)
  );
  end component;
  component block_shift_flip
  generic (
    C_DEPTH      : integer:=6;
    C_WIDTH      : integer:=8;
    C_HAS_CE     : integer:=0;
    C_FAMILY      : string:="virtex4";
    C_ELABORATION_DIR : string := "./";
    C_PARALLEL    : integer:=0;
    C_MEM_TYPE    : integer:=0
  );
  port (
    CLK   : in  std_logic;
    CE    : in  std_logic;
    WE    : in  std_logic;
    SCLR  : in  std_logic;
    D     : in  std_logic_vector(C_WIDTH-1 downto 0);
    Q     : out std_logic_vector(C_WIDTH-1 downto 0);
    VOUT  : out std_logic;
    FIRST_PHASE: out std_logic
  );
  end component;
  component phase_shuffle
  generic (
    C_FAMILY     : string;
    C_ELABORATION_DIR : string;
    C_WIDTH      : integer;
    C_RATE       : integer;
    C_HAS_CE     : integer;
    C_HAS_EARILY_PHASE: integer;
    C_EARILY_PHASE  : integer;
    C_HAS_EARILY_READ : integer;
    C_MEM_TYPE        : integer:=0;
    FIR_REQS     : t_reqs;
    FIR_DETAILS  : t_details
  );
  port (
    CLK   : in  std_logic;
    CE    : in  std_logic;
    WE_LAST    : in  std_logic;
    WE_2NDLAST : in  std_logic;
    SCLR  : in  std_logic;
    D     : in  std_logic_vector(C_WIDTH-1 downto 0);
    Q     : out std_logic_vector(C_WIDTH-1 downto 0);
    FIRST_PHASE_GEN: out std_logic
  );
  end component;

  component phase_shuffle_parallel
  generic (
    C_WIDTH      : integer;
    C_RATE       : integer;
    C_HAS_CE     : integer;
    C_MEM_TYPE        : integer:=0;
    FIR_REQS     : t_reqs;
    FIR_DETAILS  : t_details;
    C_FAMILY     : string;
    C_ELABORATION_DIR : string
  );
  port (
    CLK   : in  std_logic;
    CE    : in  std_logic;
    WE    : in  std_logic;
    SCLR  : in  std_logic;
    D     : in  std_logic_vector(C_WIDTH-1 downto 0);
    Q     : out std_logic_vector(C_WIDTH-1 downto 0)

  );
  end component;
  component counter
  generic(
    c_family      : string := "virtex4";
    width         : integer;
    updown        : integer;
    hasload       : integer;
    clr           : integer;
    set           : integer;
    clr_ce        : integer;
    ld_ce         : integer;
    countby       : integer;
    sinit         : integer;
    C_HAS_CE      : integer
  );
  port(
    clk           :  in std_logic;
    ce            :  in std_logic;
    incdec        :  in std_logic;
    sclr          :  in std_logic;
    load          :  in std_logic;
    loadvalue     :  in std_logic_vector(width-1 downto 0);
    countervalue  : out std_logic_vector(width-1 downto 0)
  );
  end component;
  component chan_counter
  generic (
        C_WIDTH      : integer := 8;
        C_FLEXIPORT_FUNC  : integer := 0
          );
  port (
        A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
        ENABLE     : in std_logic:='0';
        ENABLE_RST : in std_logic:='0';
        FLEXIPORT  : in std_logic:='0';
        D          : out std_logic_vector(C_WIDTH-1 downto 0);
        CE        : in  std_logic := '1';
        SCLR      : in  std_logic := '0';
        CLK       : in  std_logic := '0'
       );
  end component;
  component sym_adder
  generic (
        C_WIDTH           : integer := 8
        );
  port (
        A_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
        B_IN       : in  std_logic_vector(C_WIDTH-1 downto 0);
        SUB        : in std_logic:='0';
        BYPASS     : in std_logic:='0';
        D          : out std_logic_vector(C_WIDTH-1 downto 0);
        CE        : in  std_logic := '1';
        SCLR      : in  std_logic := '0';
        CLK       : in  std_logic := '0'
       );
  end component;

end mac_fir_comp_pkg;
package body mac_fir_comp_pkg is
end mac_fir_comp_pkg;
