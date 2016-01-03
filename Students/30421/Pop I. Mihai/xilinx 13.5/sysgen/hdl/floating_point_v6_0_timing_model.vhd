
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
use ieee.numeric_std.all;
use ieee.math_real.all;
library xilinxcorelib;
use xilinxcorelib.bip_utils_pkg_v2_0.all;
use xilinxcorelib.axi_utils_pkg_v1_1.all;
use xilinxcorelib.axi_utils_v1_1_comps.all;
use xilinxcorelib.floating_point_v6_0_consts.all;
use xilinxcorelib.floating_point_pkg_v6_0.all;
  entity floating_point_v6_0_timing_model is
  generic (
    C_XDEVICEFAMILY         : string  := "no_family";
    C_HAS_ADD               : integer := 1;
    C_HAS_SUBTRACT          : integer := 0;
    C_HAS_MULTIPLY          : integer := 0;
    C_HAS_DIVIDE            : integer := 0;
    C_HAS_SQRT              : integer := 0;
    C_HAS_COMPARE           : integer := 0;
    C_HAS_FIX_TO_FLT        : integer := 0;
    C_HAS_FLT_TO_FIX        : integer := 0;
    C_HAS_FLT_TO_FLT        : integer := 0;
    C_HAS_RECIP             : integer := 0;
    C_HAS_RECIP_SQRT        : integer := 0;
    C_A_WIDTH               : integer := 32;
    C_A_FRACTION_WIDTH      : integer := 24;
    C_B_WIDTH               : integer := 32;
    C_B_FRACTION_WIDTH      : integer := 24;
    C_RESULT_WIDTH          : integer := 32;
    C_RESULT_FRACTION_WIDTH : integer := 24;
    C_COMPARE_OPERATION     : integer := 1;
    C_LATENCY               : integer := 1000;
    C_OPTIMIZATION          : integer := 1;
    C_MULT_USAGE            : integer := 2;
    C_RATE                  : integer := 1;
    C_HAS_UNDERFLOW         : integer := 0;
    C_HAS_OVERFLOW          : integer := 0;
    C_HAS_INVALID_OP        : integer := 0;
    C_HAS_DIVIDE_BY_ZERO    : integer := 0;
    C_HAS_ACLKEN            : integer := 0;
    C_HAS_ARESETN           : integer := 0;
    C_THROTTLE_SCHEME       : integer := 1;
    C_HAS_A_TUSER           : integer := 0;
    C_HAS_A_TLAST           : integer := 0;
    C_HAS_B                 : integer := 1;
    C_HAS_B_TUSER           : integer := 0;
    C_HAS_B_TLAST           : integer := 0;
    C_HAS_OPERATION         : integer := 0;
    C_HAS_OPERATION_TUSER   : integer := 0;
    C_HAS_OPERATION_TLAST   : integer := 0;
    C_HAS_RESULT_TUSER      : integer := 0;
    C_HAS_RESULT_TLAST      : integer := 0;
    C_TLAST_RESOLUTION      : integer := 1;
    C_A_TDATA_WIDTH         : integer := 32;
    C_A_TUSER_WIDTH         : integer := 1;
    C_B_TDATA_WIDTH         : integer := 32;
    C_B_TUSER_WIDTH         : integer := 1;
    C_OPERATION_TDATA_WIDTH : integer := 8;
    C_OPERATION_TUSER_WIDTH : integer := 1;
    C_RESULT_TDATA_WIDTH    : integer := 32;
    C_RESULT_TUSER_WIDTH    : integer := 1
  );
  port (
    aclk                    : in  std_logic                                            := '0';
    aclken                  : in  std_logic                                            := '1';
    aresetn                 : in  std_logic                                            := '1';
    s_axis_a_tvalid         : in  std_logic                                            := '0';
    s_axis_a_tready         : out std_logic                                            := '0';
    s_axis_a_tdata          : in  std_logic_vector(C_A_TDATA_WIDTH-1 downto 0)         := (others => '0');
    s_axis_a_tuser          : in  std_logic_vector(C_A_TUSER_WIDTH-1 downto 0)         := (others => '0');
    s_axis_a_tlast          : in  std_logic                                            := '0';
    s_axis_b_tvalid         : in  std_logic                                            := '0';
    s_axis_b_tready         : out std_logic                                            := '0';
    s_axis_b_tdata          : in  std_logic_vector(C_B_TDATA_WIDTH-1 downto 0)         := (others => '0');
    s_axis_b_tuser          : in  std_logic_vector(C_B_TUSER_WIDTH-1 downto 0)         := (others => '0');
    s_axis_b_tlast          : in  std_logic                                            := '0';
    s_axis_operation_tvalid : in  std_logic                                            := '0';
    s_axis_operation_tready : out std_logic                                            := '0';
    s_axis_operation_tdata  : in  std_logic_vector(C_OPERATION_TDATA_WIDTH-1 downto 0) := (others => '0');
    s_axis_operation_tuser  : in  std_logic_vector(C_OPERATION_TUSER_WIDTH-1 downto 0) := (others => '0');
    s_axis_operation_tlast  : in  std_logic                                            := '0';
    m_axis_result_tvalid    : out std_logic                                            := '0';
    m_axis_result_tready    : in  std_logic                                            := '0';
    m_axis_result_tlast     : out std_logic                                            := '0'
    );
end entity floating_point_v6_0_timing_model;
architecture behavioral of floating_point_v6_0_timing_model is
  constant FP_GENERICS : generics_type := floating_point_v6_0_check_generics(
    C_XDEVICEFAMILY         => C_XDEVICEFAMILY,
    C_HAS_ADD               => C_HAS_ADD,
    C_HAS_SUBTRACT          => C_HAS_SUBTRACT,
    C_HAS_MULTIPLY          => C_HAS_MULTIPLY,
    C_HAS_DIVIDE            => C_HAS_DIVIDE,
    C_HAS_SQRT              => C_HAS_SQRT,
    C_HAS_COMPARE           => C_HAS_COMPARE,
    C_HAS_FIX_TO_FLT        => C_HAS_FIX_TO_FLT,
    C_HAS_FLT_TO_FIX        => C_HAS_FLT_TO_FIX,
    C_HAS_FLT_TO_FLT        => C_HAS_FLT_TO_FLT,
    C_HAS_RECIP             => C_HAS_RECIP,
    C_HAS_RECIP_SQRT        => C_HAS_RECIP_SQRT,
    C_A_WIDTH               => C_A_WIDTH,
    C_A_FRACTION_WIDTH      => C_A_FRACTION_WIDTH,
    C_B_WIDTH               => C_B_WIDTH,
    C_B_FRACTION_WIDTH      => C_B_FRACTION_WIDTH,
    C_RESULT_WIDTH          => C_RESULT_WIDTH,
    C_RESULT_FRACTION_WIDTH => C_RESULT_FRACTION_WIDTH,
    C_COMPARE_OPERATION     => C_COMPARE_OPERATION,
    C_LATENCY               => C_LATENCY,
    C_OPTIMIZATION          => C_OPTIMIZATION,
    C_MULT_USAGE            => C_MULT_USAGE,
    C_RATE                  => C_RATE,
    C_HAS_UNDERFLOW         => C_HAS_UNDERFLOW,
    C_HAS_OVERFLOW          => C_HAS_OVERFLOW,
    C_HAS_INVALID_OP        => C_HAS_INVALID_OP,
    C_HAS_DIVIDE_BY_ZERO    => C_HAS_DIVIDE_BY_ZERO,
    C_HAS_ACLKEN            => C_HAS_ACLKEN,
    C_HAS_ARESETN           => C_HAS_ARESETN,
    C_THROTTLE_SCHEME       => C_THROTTLE_SCHEME,
    C_HAS_A_TUSER           => C_HAS_A_TUSER,
    C_HAS_A_TLAST           => C_HAS_A_TLAST,
    C_HAS_B                 => C_HAS_B,
    C_HAS_B_TUSER           => C_HAS_B_TUSER,
    C_HAS_B_TLAST           => C_HAS_B_TLAST,
    C_HAS_OPERATION         => C_HAS_OPERATION,
    C_HAS_OPERATION_TUSER   => C_HAS_OPERATION_TUSER,
    C_HAS_OPERATION_TLAST   => C_HAS_OPERATION_TLAST,
    C_HAS_RESULT_TUSER      => C_HAS_RESULT_TUSER,
    C_HAS_RESULT_TLAST      => C_HAS_RESULT_TLAST,
    C_TLAST_RESOLUTION      => C_TLAST_RESOLUTION,
    C_A_TDATA_WIDTH         => C_A_TDATA_WIDTH,
    C_A_TUSER_WIDTH         => C_A_TUSER_WIDTH,
    C_B_TDATA_WIDTH         => C_B_TDATA_WIDTH,
    C_B_TUSER_WIDTH         => C_B_TUSER_WIDTH,
    C_OPERATION_TDATA_WIDTH => C_OPERATION_TDATA_WIDTH,
    C_OPERATION_TUSER_WIDTH => C_OPERATION_TUSER_WIDTH,
    C_RESULT_TDATA_WIDTH    => C_RESULT_TDATA_WIDTH,
    C_RESULT_TUSER_WIDTH    => C_RESULT_TUSER_WIDTH);
  constant OP_CODE_SLV : std_logic_vector(FLT_PT_OP_CODE_SLICE) := flt_pt_get_op_code(
    C_HAS_ADD          => C_HAS_ADD,
    C_HAS_SUBTRACT     => C_HAS_SUBTRACT,
    C_HAS_MULTIPLY     => C_HAS_MULTIPLY,
    C_HAS_DIVIDE       => C_HAS_DIVIDE,
    C_HAS_SQRT         => C_HAS_SQRT,
    C_HAS_COMPARE      => C_HAS_COMPARE,
    C_HAS_FIX_TO_FLT   => C_HAS_FIX_TO_FLT,
    C_HAS_FLT_TO_FIX   => C_HAS_FLT_TO_FIX,
    C_HAS_FLT_TO_FLT   => C_HAS_FLT_TO_FLT,
    C_HAS_RECIP        => C_HAS_RECIP,
    C_HAS_RECIP_SQRT   => C_HAS_RECIP_SQRT);
  constant CORE_LATENCY : integer := flt_pt_delay(
    family                => C_XDEVICEFAMILY,
    op_code               => OP_CODE_SLV,
    a_width               => C_A_WIDTH,
    a_fraction_width      => C_A_FRACTION_WIDTH,
    b_width               => C_B_WIDTH,
    b_fraction_width      => C_B_FRACTION_WIDTH,
    result_width          => C_RESULT_WIDTH,
    result_fraction_width => C_RESULT_FRACTION_WIDTH,
    optimization          => C_OPTIMIZATION,
    mult_usage            => C_MULT_USAGE,
    rate                  => C_RATE,
    throttle_scheme       => C_THROTTLE_SCHEME,
    has_add               => C_HAS_ADD,
    has_subtract          => C_HAS_SUBTRACT,
    has_multiply          => C_HAS_MULTIPLY,
    has_divide            => C_HAS_DIVIDE,
    has_sqrt              => C_HAS_SQRT,
    has_compare           => C_HAS_COMPARE,
    has_flt_to_fix        => C_HAS_FLT_TO_FIX,
    has_fix_to_flt        => C_HAS_FIX_TO_FLT,
    has_flt_to_flt        => C_HAS_FLT_TO_FLT,
    has_recip             => C_HAS_RECIP,
    has_recip_sqrt        => C_HAS_RECIP_SQRT,
    required              => C_LATENCY);
  function get_axi_slave_ctrl_latency(c_throttle_scheme : integer) return integer is
    constant AXI_SYNC_DELAY : integer := 1;
    variable result : integer := 0;
  begin
    if c_throttle_scheme /= CI_AND_TVALID_THROTTLE then
      result := result + AXI_SYNC_DELAY;
    end if;
    return result;
  end function get_axi_slave_ctrl_latency;
  constant AXI_SLAVE_CTRL_LATENCY : integer := get_axi_slave_ctrl_latency(C_THROTTLE_SCHEME);
  constant AXI_MASTER_CTRL_LATENCY : integer := 2*boolean'pos(FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE);
  constant DELAY : integer := CORE_LATENCY - AXI_SLAVE_CTRL_LATENCY - AXI_MASTER_CTRL_LATENCY;
  signal aclk_i                    : std_logic := '0';
  signal aclken_i                  : std_logic := '1';
  signal aresetn_i                 : std_logic := '1';
  signal s_axis_a_tvalid_i         : std_logic := '0';
  signal s_axis_a_tready_i         : std_logic := '0';
  signal s_axis_a_tdata_i          : std_logic_vector(FP_GENERICS.c_a_tdata_width-1 downto 0) := (others => '0');
  signal s_axis_a_tuser_i          : std_logic_vector(FP_GENERICS.c_a_tuser_width-1 downto 0) := (others => '0');
  signal s_axis_a_tlast_i          : std_logic := '0';
  signal s_axis_b_tvalid_i         : std_logic := '0';
  signal s_axis_b_tready_i         : std_logic := '0';
  signal s_axis_b_tdata_i          : std_logic_vector(FP_GENERICS.c_b_tdata_width-1 downto 0) := (others => '0');
  signal s_axis_b_tuser_i          : std_logic_vector(FP_GENERICS.c_b_tuser_width-1 downto 0) := (others => '0');
  signal s_axis_b_tlast_i          : std_logic := '0';
  signal s_axis_operation_tvalid_i : std_logic := '0';
  signal s_axis_operation_tready_i : std_logic := '0';
  signal s_axis_operation_tdata_i  : std_logic_vector(FP_GENERICS.c_operation_tdata_width-1 downto 0) :=(others => '0');
  signal s_axis_operation_tuser_i  : std_logic_vector(FP_GENERICS.c_operation_tuser_width-1 downto 0) :=(others => '0');
  signal s_axis_operation_tlast_i  : std_logic := '0';
  signal m_axis_result_tvalid_i    : std_logic := '0';
  signal m_axis_result_tready_i    : std_logic := '0';
  signal m_axis_result_tdata_i     : std_logic_vector(FP_GENERICS.c_result_tdata_width-1 downto 0) := (others => '0');
  signal m_axis_result_tuser_i     : std_logic_vector(FP_GENERICS.c_result_tuser_width-1 downto 0) := (others => '0');
  signal m_axis_result_tlast_i     : std_logic := '0';
  signal a_tdata_in          : std_logic_vector(FP_GENERICS.c_a_tdata_width-1 downto 0) := (others => '0');
  signal a_tuser_in          : std_logic_vector(FP_GENERICS.c_a_tuser_width-1 downto 0) := (others => '0');
  signal a_tlast_in          : std_logic := '0';
  signal b_tdata_in          : std_logic_vector(FP_GENERICS.c_b_tdata_width-1 downto 0) := (others => '0');
  signal b_tuser_in          : std_logic_vector(FP_GENERICS.c_b_tuser_width-1 downto 0) := (others => '0');
  signal b_tlast_in          : std_logic := '0';
  signal operation_tdata_in  : std_logic_vector(FP_GENERICS.c_operation_tdata_width-1 downto 0) := (others => '0');
  signal operation_tuser_in  : std_logic_vector(FP_GENERICS.c_operation_tuser_width-1 downto 0) := (others => '0');
  signal operation_tlast_in  : std_logic := '0';
  signal tvalid_in    : std_logic := '0';
  signal backpressure : std_logic := '0';
  signal start_op     : std_logic := '1';
  signal sclr         : std_logic := '0';
  signal result_tvalid_async  : std_logic                                                     := '0';
  signal result_tdata_async   : std_logic_vector(FP_GENERICS.c_result_tdata_width-1 downto 0) := (others => '0');
  signal result_tuser_async   : std_logic_vector(FP_GENERICS.c_result_tuser_width-1 downto 0) := (others => '0');
  signal result_tlast_async   : std_logic                                                     := '0';
  constant TLAST_WIDTH              : integer                                        := 1;
  constant OUTPUT_FIFO_WIDTH        : integer                                        := m_axis_result_tdata_i'length+m_axis_result_tuser_i'length+TLAST_WIDTH;
  signal fifo_wr_en                 : std_logic                                      := '0';
  signal fifo_wr_data, fifo_rd_data : std_logic_vector(OUTPUT_FIFO_WIDTH-1 downto 0) := (others => '0');
  signal m_axis_result_tdata_i_temp  : std_logic_vector(m_axis_result_tdata_i'range) := (others => '0');
  signal m_axis_result_tuser_i_temp  : std_logic_vector(m_axis_result_tuser_i'range) := (others => '0');
  signal m_axis_result_tlast_i_temp  : std_logic                                     := '0';
  signal m_axis_result_tvalid_i_temp : std_logic                                     := '0';
  signal start_op_i : std_logic := '0';
begin
  aclk_i    <= aclk;
  aclken_i  <= aclken  when FP_GENERICS.c_has_aclken = 1  else '1';
  aresetn_i <= aresetn when FP_GENERICS.c_has_aresetn = 1 else '1';
  s_axis_a_tvalid_i <= s_axis_a_tvalid;
  s_axis_a_tdata_i  <= s_axis_a_tdata;
  s_axis_a_tuser_i  <= s_axis_a_tuser when FP_GENERICS.c_has_a_tuser = 1 else (others => '0');
  s_axis_a_tlast_i  <= s_axis_a_tlast when FP_GENERICS.c_has_a_tlast = 1 else '0';
  s_axis_b_tvalid_i <= s_axis_b_tvalid when FP_GENERICS.c_has_b = 1       else '1';
  s_axis_b_tdata_i  <= s_axis_b_tdata  when FP_GENERICS.c_has_b = 1       else (others => '0');
  s_axis_b_tuser_i  <= s_axis_b_tuser  when FP_GENERICS.c_has_b_tuser = 1 else (others => '0');
  s_axis_b_tlast_i  <= s_axis_b_tlast  when FP_GENERICS.c_has_b_tlast = 1 else '0';
  s_axis_operation_tvalid_i <= s_axis_operation_tvalid when FP_GENERICS.c_has_operation = 1       else '1';
  s_axis_operation_tdata_i  <= s_axis_operation_tdata  when FP_GENERICS.c_has_operation = 1       else (others => '0');
  s_axis_operation_tuser_i  <= s_axis_operation_tuser  when FP_GENERICS.c_has_operation_tuser = 1 else (others => '0');
  s_axis_operation_tlast_i  <= s_axis_operation_tlast  when FP_GENERICS.c_has_operation_tlast = 1 else '0';
  m_axis_result_tready_i <= m_axis_result_tready when (FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE or
                                                       FP_GENERICS.c_throttle_scheme = CI_CE_THROTTLE) else '1';
    reg_sclr : process (aclk)
    begin
      if rising_edge(aclk) then
        sclr <= not aresetn_i;
      end if;
    end process reg_sclr;
  AND_TVALIDS_CTRL : if FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE generate
    signal rate_count_atv : integer := 0;
  begin
    a_tdata_in         <= s_axis_a_tdata_i;
    a_tuser_in         <= s_axis_a_tuser_i;
    a_tlast_in         <= s_axis_a_tlast_i;
    b_tdata_in         <= s_axis_b_tdata_i;
    b_tuser_in         <= s_axis_b_tuser_i;
    b_tlast_in         <= s_axis_b_tlast_i;
    operation_tdata_in <= s_axis_operation_tdata_i;
    operation_tuser_in <= s_axis_operation_tuser_i;
    operation_tlast_in <= s_axis_operation_tlast_i;
    tvalid_in <= s_axis_a_tvalid_i and s_axis_b_tvalid_i and s_axis_operation_tvalid_i;
    backpressure <= '0';
    LATENCY_GT_ZERO : if C_LATENCY > 0 generate
      rate_counter : process (aclk)
      begin
        if rising_edge(aclk) then
          if sclr = '1' then
            if FP_GENERICS.c_rate > 1 then
              start_op <= '0';
            end if;
            rate_count_atv            <= 0;
            s_axis_a_tready_i         <= '0';
            s_axis_b_tready_i         <= '0';
            s_axis_operation_tready_i <= '0';
          elsif aclken_i = '1' then
            if rate_count_atv = 0 then
              s_axis_a_tready_i         <= '1';
              s_axis_b_tready_i         <= '1';
              s_axis_operation_tready_i <= '1';
            else
              s_axis_a_tready_i         <= '0';
              s_axis_b_tready_i         <= '0';
              s_axis_operation_tready_i <= '0';
            end if;
            if rate_count_atv = 0 then
              rate_count_atv <= FP_GENERICS.c_rate - 1;
            else
              rate_count_atv <= rate_count_atv - 1;
            end if;
            if rate_count_atv = 0 then
              start_op <= '1';
            else
              start_op <= '0';
            end if;
          end if;
        end if;
      end process rate_counter;
    end generate LATENCY_GT_ZERO;
    LATENCY_ZERO : if C_LATENCY = 0 generate
      s_axis_a_tready_i         <= '1';
      s_axis_b_tready_i         <= '1';
      s_axis_operation_tready_i <= '1';
      start_op                  <= '1';
    end generate LATENCY_ZERO;
  end generate AND_TVALIDS_CTRL;
  AXI_CTRL : if FP_GENERICS.c_throttle_scheme /= CI_AND_TVALID_THROTTLE generate
    constant HAS_Z_TREADY : boolean := not(FP_GENERICS.c_throttle_scheme = CI_GEN_THROTTLE and FP_GENERICS.c_rate = 1);
    signal rate_count_axi : integer   := 0;
    signal core_tready    : std_logic := '0';
    signal tvalid_sync           : std_logic;
    signal a_tdata_sync          : std_logic_vector(c_a_tdata_width-1 downto 0);
    signal a_tuser_sync          : std_logic_vector(c_a_tuser_width-1 downto 0);
    signal a_tlast_sync          : std_logic;
    signal b_tdata_sync          : std_logic_vector(c_b_tdata_width-1 downto 0);
    signal b_tuser_sync          : std_logic_vector(c_b_tuser_width-1 downto 0);
    signal b_tlast_sync          : std_logic;
    signal operation_tdata_sync  : std_logic_vector(c_operation_tdata_width-1 downto 0);
    signal operation_tuser_sync  : std_logic_vector(c_operation_tuser_width-1 downto 0);
    signal operation_tlast_sync  : std_logic;
  begin
    i_sync : axi_slave_3to1_v1_1
      generic map (
        C_A_TDATA_WIDTH => C_A_TDATA_WIDTH,
        C_HAS_A_TUSER   => C_HAS_A_TUSER = 1,
        C_A_TUSER_WIDTH => C_A_TUSER_WIDTH,
        C_HAS_A_TLAST   => C_HAS_A_TLAST = 1,
        C_B_TDATA_WIDTH => C_B_TDATA_WIDTH,
        C_HAS_B_TUSER   => C_HAS_B_TUSER = 1,
        C_B_TUSER_WIDTH => C_B_TUSER_WIDTH,
        C_HAS_B_TLAST   => C_HAS_B_TLAST = 1,
        C_C_TDATA_WIDTH => C_OPERATION_TDATA_WIDTH,
        C_HAS_C_TUSER   => C_HAS_OPERATION_TUSER = 1,
        C_C_TUSER_WIDTH => C_OPERATION_TUSER_WIDTH,
        C_HAS_C_TLAST   => C_HAS_OPERATION_TLAST = 1,
        C_HAS_Z_TREADY  => HAS_Z_TREADY
        )
      port map (
        aclk,
        aclken_i,
        sclr,
        s_axis_a_tready_i,
        s_axis_a_tvalid_i,
        s_axis_a_tdata,
        s_axis_a_tuser,
        s_axis_a_tlast,
        s_axis_b_tready_i,
        s_axis_b_tvalid_i,
        s_axis_b_tdata,
        s_axis_b_tuser,
        s_axis_b_tlast,
        s_axis_operation_tready_i,
        s_axis_operation_tvalid_i,
        s_axis_operation_tdata,
        s_axis_operation_tuser,
        s_axis_operation_tlast,
        core_tready,
        tvalid_sync,
        a_tdata_sync,
        a_tuser_sync,
        a_tlast_sync,
        b_tdata_sync,
        b_tuser_sync,
        b_tlast_sync,
        operation_tdata_sync,
        operation_tuser_sync,
        operation_tlast_sync
        );
    tvalid_in   <= tvalid_sync;
    a_tdata_in  <= a_tdata_sync(FP_GENERICS.c_a_tdata_width-1 downto 0);
    a_tuser_in  <= a_tuser_sync(FP_GENERICS.c_a_tuser_width-1 downto 0) when FP_GENERICS.c_has_a_tuser = 1 else (others => '0');
    a_tlast_in  <= a_tlast_sync when FP_GENERICS.c_has_a_tlast = 1 else '0';
    b_tdata_in  <= b_tdata_sync(FP_GENERICS.c_b_tdata_width-1 downto 0);
    b_tuser_in  <= b_tuser_sync(FP_GENERICS.c_b_tuser_width-1 downto 0) when FP_GENERICS.c_has_b_tuser = 1 else (others => '0');
    b_tlast_in  <= b_tlast_sync when FP_GENERICS.c_has_b_tlast = 1 else '0';
    operation_tdata_in  <= operation_tdata_sync(FP_GENERICS.c_operation_tdata_width-1 downto 0);
    operation_tuser_in  <= operation_tuser_sync(FP_GENERICS.c_operation_tuser_width-1 downto 0) when FP_GENERICS.c_has_operation_tuser = 1 else (others => '0');
    operation_tlast_in  <= operation_tlast_sync when FP_GENERICS.c_has_operation_tlast = 1 else '0';
    rate_counter : process (aclk)
    begin
      if rising_edge(aclk) then
        if sclr = '1' then
          rate_count_axi <= 0;
        elsif aclken_i = '1' and (backpressure = '0' or
                                  (FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE and rate_count_axi /= 0)) then
          if rate_count_axi = 0 then
            if start_op = '1' then
              rate_count_axi <= FP_GENERICS.c_rate - 1;
            end if;
          else
            rate_count_axi <= rate_count_axi - 1;
          end if;
        end if;
      end if;
    end process rate_counter;
    backpressure <= '0' when sclr = '1' else m_axis_result_tvalid_i and not m_axis_result_tready_i;
    core_tready <= not backpressure when rate_count_axi = 0 else '0';
    start_op <= core_tready and tvalid_in;
  end generate AXI_CTRL;
  calc_results : process(backpressure, start_op, tvalid_in,
                         a_tdata_in, b_tdata_in, operation_tdata_in,
                         a_tuser_in, b_tuser_in, operation_tuser_in,
                         a_tlast_in, b_tlast_in, operation_tlast_in)
    variable a         : std_logic_vector(FP_GENERICS.c_a_width-1 downto 0)  := (others => '0');
    variable b         : std_logic_vector(FP_GENERICS.c_b_width-1 downto 0)  := (others => '0');
    variable operation : std_logic_vector(FLT_PT_OPERATION_WIDTH-1 downto 0) := (others => '0');
    variable result_async         : std_logic_vector(FP_GENERICS.c_result_width-1 downto 0) := (others => '0');
    variable invalid_op_async     : std_logic                                               := '0';
    variable overflow_async       : std_logic                                               := '0';
    variable underflow_async      : std_logic                                               := '0';
    variable divide_by_zero_async : std_logic                                               := '0';
    variable tuser_pos : integer := 0;
  begin
    if backpressure = '0' then
      result_tvalid_async <= start_op and tvalid_in;
    end if;
  end process calc_results;
  DELAY_0 : if DELAY = 0 generate
    m_axis_result_tvalid_i_temp <= result_tvalid_async;
    m_axis_result_tdata_i_temp  <= result_tdata_async;
    m_axis_result_tuser_i_temp  <= result_tuser_async;
    m_axis_result_tlast_i_temp  <= result_tlast_async;
  end generate DELAY_0;
  DELAY_N : if DELAY /= 0 generate
    model : process(aclk)
      variable tvalid_delay   : std_logic_vector(1 to DELAY) := (others => '0');
      variable tlast_delay    : std_logic_vector(1 to DELAY) := (others => '0');
      variable start_op_delay : std_logic_vector(1 to DELAY) := (others => '0');
    begin
      if rising_edge(aclk) then
        if sclr = '1' then
          tvalid_delay                := (others => '0');
          m_axis_result_tvalid_i_temp <= '0';
          start_op_delay              := (others => '0');
          start_op_i <= '0';
        elsif aclken_i = '1' then
          start_op_delay := start_op & start_op_delay(1 to DELAY - 1);
          start_op_i <= start_op_delay(DELAY);
          if backpressure = '0' or (FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE) then
            tvalid_delay                := result_tvalid_async & tvalid_delay(1 to DELAY - 1);
            m_axis_result_tvalid_i_temp <= tvalid_delay(DELAY);
          end if;
        end if;
      end if;
    end process model;
    fifo_wr_en <= start_op_i and aclken_i;
  end generate DELAY_N;
  NO_OUTPUT_FIFO : if FP_GENERICS.c_throttle_scheme /= CI_RFD_THROTTLE generate
    m_axis_result_tdata_i  <= m_axis_result_tdata_i_temp;
    m_axis_result_tuser_i  <= m_axis_result_tuser_i_temp;
    m_axis_result_tlast_i  <= m_axis_result_tlast_i_temp;
    m_axis_result_tvalid_i <= m_axis_result_tvalid_i_temp;
  end generate NO_OUTPUT_FIFO;
  HAS_OUTPUT_FIFO : if FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE generate
    constant MIN_DEPTH : integer := 16;
    constant COMBINER_LATENCY : integer := 1;
    constant OUTPUT_FIFO_DEPTH : integer := flt_pt_max(2**log2roundup(DELAY+COMBINER_LATENCY), MIN_DEPTH);
  begin
    output_fifo : glb_ifx_master_v1_1
      generic map(
        WIDTH         => OUTPUT_FIFO_WIDTH,
        DEPTH         => OUTPUT_FIFO_DEPTH,
        AFULL_THRESH1 => 1,
        AFULL_THRESH0 => 1
        )
      port map(
        aclk      => aclk,
        aclken    => aclken_i,
        areset    => sclr,
        wr_enable => fifo_wr_en,
        wr_data   => fifo_wr_data,
        ifx_valid => m_axis_result_tvalid_i,
        ifx_ready => m_axis_result_tready,
        ifx_data  => fifo_rd_data,
        full      => open,
        afull     => open,
        not_full  => open,
        not_afull => open,
        add       => open
        );
  end generate HAS_OUTPUT_FIFO;
  s_axis_a_tready <= '1'
                     when (FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE and
                           FP_GENERICS.c_rate > 1 and
                           FP_GENERICS.c_latency = 0)
                     else s_axis_a_tready_i
                     when (FP_GENERICS.c_throttle_scheme = CI_CE_THROTTLE or
                           FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE or
                           FP_GENERICS.c_throttle_scheme = CI_GEN_THROTTLE or
                           (FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE and
                            FP_GENERICS.c_rate > 1))
                     else 'X';
  s_axis_b_tready <= '1'
                     when (FP_GENERICS.c_has_b = 1 and
                           (FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE and
                            FP_GENERICS.c_rate > 1 and
                            FP_GENERICS.c_latency = 0))
                     else s_axis_b_tready_i
                     when (FP_GENERICS.c_has_b = 1 and
                           (FP_GENERICS.c_throttle_scheme = CI_CE_THROTTLE or
                            FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE or
                            FP_GENERICS.c_throttle_scheme = CI_GEN_THROTTLE or
                            (FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE and
                             FP_GENERICS.c_rate > 1)))
                     else 'X';
  s_axis_operation_tready <= '1'
                             when (FP_GENERICS.c_has_operation = 1 and
                                   (FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE and
                                    FP_GENERICS.c_rate > 1 and
                                    FP_GENERICS.c_latency = 0))
                             else s_axis_operation_tready_i
                             when (FP_GENERICS.c_has_operation = 1 and
                                   (FP_GENERICS.c_throttle_scheme = CI_CE_THROTTLE or
                                    FP_GENERICS.c_throttle_scheme = CI_RFD_THROTTLE or
                                    FP_GENERICS.c_throttle_scheme = CI_GEN_THROTTLE or
                                    (FP_GENERICS.c_throttle_scheme = CI_AND_TVALID_THROTTLE and
                                     FP_GENERICS.c_rate > 1)))
                             else 'X';
  m_axis_result_tvalid <= m_axis_result_tvalid_i;
end architecture behavioral;
