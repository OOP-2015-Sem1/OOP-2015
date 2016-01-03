
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
library xilinxcorelib;
use work.timing_model_pkg.all;
use work.timing_model_xfft_v8_0_axi_pkg.all;
use xilinxcorelib.axi_utils_pkg_v1_0.all;
use xilinxcorelib.axi_utils_v1_0_comps.all;
entity timing_model_axi_wrapper is
  generic (
    C_CHANNELS                  : integer := 1;
    C_NFFT_MAX                  : integer := 8;
    C_ARCH                      : integer := 2;
    C_HAS_NFFT                  : integer := 1;
    C_USE_FLT_PT                : integer := 0;
    C_INPUT_WIDTH               : integer := 12;
    C_OUTPUT_WIDTH              : integer := 12;
    C_HAS_SCALING               : integer := 1;
    C_HAS_BFP                   : integer := 0;
    C_HAS_ROUNDING              : integer := 0;
    C_HAS_ACLKEN                : integer := 0;
    C_HAS_ARESETN               : integer := 0;
    C_HAS_OVFLO                 : integer := 0;
    C_HAS_NATURAL_OUTPUT        : integer := 1;
    C_HAS_CYCLIC_PREFIX         : integer := 1;
    C_HAS_XK_INDEX              : integer := 0;
    C_THROTTLE_SCHEME           : integer := 0;
    C_S_AXIS_CONFIG_TDATA_WIDTH : integer := 40;
    C_S_AXIS_DATA_TDATA_WIDTH   : integer := 32;
    C_M_AXIS_DATA_TDATA_WIDTH   : integer := 32;
    C_M_AXIS_DATA_TUSER_WIDTH   : integer := 1;
    C_M_AXIS_STATUS_TDATA_WIDTH : integer := 1
    );
  port (
    aclk    : in std_logic := '1';
    aresetn : in std_logic := '1';
    aclken  : in std_logic := '1';
    s_axis_config_tdata  : in  std_logic_vector (C_S_AXIS_CONFIG_TDATA_WIDTH-1 downto 0) := (others => '0');
    s_axis_config_tvalid : in  std_logic                                                 := '0';
    s_axis_config_tready : out std_logic                                                 := '0';
    s_axis_data_tdata    : in  std_logic_vector (C_S_AXIS_DATA_TDATA_WIDTH-1 downto 0) := (others => '0');
    s_axis_data_tvalid   : in  std_logic                                               := '0';
    s_axis_data_tready   : out std_logic                                               := '0';
    s_axis_data_tlast    : in  std_logic                                               := '0';
    m_axis_data_tdata    : out std_logic_vector (C_M_AXIS_DATA_TDATA_WIDTH-1 downto 0) := (others => '0');
    m_axis_data_tuser    : out std_logic_vector (C_M_AXIS_DATA_TUSER_WIDTH-1 downto 0) := (others => '0');
    m_axis_data_tvalid   : out std_logic                                               := '0';
    m_axis_data_tready   : in  std_logic                                               := '0';
    m_axis_data_tlast    : out std_logic                                               := '0';
    m_axis_status_tdata  : out std_logic_vector(C_M_AXIS_STATUS_TDATA_WIDTH-1 downto 0);
    m_axis_status_tvalid : out std_logic;
    m_axis_status_tready : in  std_logic;
    event_frame_started        : out std_logic := '0';
    event_tlast_unexpected     : out std_logic := '0';
    event_tlast_missing        : out std_logic := '0';
    event_fft_overflow         : out std_logic := '0';
    event_status_channel_halt  : out std_logic := '0';
    event_data_in_channel_halt : out std_logic := '0';
    event_data_out_channel_halt: out std_logic := '0';
    NFFT           : out std_logic_vector(4 downto 0)                                                  := (others => '0');
    NFFT_WE        : out std_logic                                                                     := '0';
    START          : out std_logic                                                                     := '0';
    UNLOAD         : out std_logic                                                                     := '0';
    CP_LEN         : out std_logic_vector(C_NFFT_MAX-1 downto 0)                                       := (others => '0');
    CP_LEN_WE      : out std_logic                                                                     := '0';
    XN_RE          : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN_IM          : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    FWD_INV        : out std_logic                                                                     := '0';
    FWD_INV_WE     : out std_logic                                                                     := '0';
    SCALE_SCH      : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH_WE   : out std_logic                                                                     := '0';
    XN0_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN0_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN1_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN1_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN2_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN2_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN3_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN3_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN4_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN4_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN5_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN5_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN6_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN6_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN7_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN7_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN8_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN8_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN9_RE         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN9_IM         : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN10_RE        : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN10_IM        : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN11_RE        : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    XN11_IM        : out std_logic_vector(C_INPUT_WIDTH-1 downto 0)                                    := (others => '0');
    FWD_INV0       : out std_logic                                                                     := '1';
    FWD_INV0_WE    : out std_logic                                                                     := '0';
    FWD_INV1       : out std_logic                                                                     := '1';
    FWD_INV1_WE    : out std_logic                                                                     := '0';
    FWD_INV2       : out std_logic                                                                     := '1';
    FWD_INV2_WE    : out std_logic                                                                     := '0';
    FWD_INV3       : out std_logic                                                                     := '1';
    FWD_INV3_WE    : out std_logic                                                                     := '0';
    FWD_INV4       : out std_logic                                                                     := '1';
    FWD_INV4_WE    : out std_logic                                                                     := '0';
    FWD_INV5       : out std_logic                                                                     := '1';
    FWD_INV5_WE    : out std_logic                                                                     := '0';
    FWD_INV6       : out std_logic                                                                     := '1';
    FWD_INV6_WE    : out std_logic                                                                     := '0';
    FWD_INV7       : out std_logic                                                                     := '1';
    FWD_INV7_WE    : out std_logic                                                                     := '0';
    FWD_INV8       : out std_logic                                                                     := '1';
    FWD_INV8_WE    : out std_logic                                                                     := '0';
    FWD_INV9       : out std_logic                                                                     := '1';
    FWD_INV9_WE    : out std_logic                                                                     := '0';
    FWD_INV10      : out std_logic                                                                     := '1';
    FWD_INV10_WE   : out std_logic                                                                     := '0';
    FWD_INV11      : out std_logic                                                                     := '1';
    FWD_INV11_WE   : out std_logic                                                                     := '0';
    SCALE_SCH0     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH0_WE  : out std_logic                                                                     := '0';
    SCALE_SCH1     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH1_WE  : out std_logic                                                                     := '0';
    SCALE_SCH2     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH2_WE  : out std_logic                                                                     := '0';
    SCALE_SCH3     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH3_WE  : out std_logic                                                                     := '0';
    SCALE_SCH4     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH4_WE  : out std_logic                                                                     := '0';
    SCALE_SCH5     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH5_WE  : out std_logic                                                                     := '0';
    SCALE_SCH6     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH6_WE  : out std_logic                                                                     := '0';
    SCALE_SCH7     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH7_WE  : out std_logic                                                                     := '0';
    SCALE_SCH8     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH8_WE  : out std_logic                                                                     := '0';
    SCALE_SCH9     : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH9_WE  : out std_logic                                                                     := '0';
    SCALE_SCH10    : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH10_WE : out std_logic                                                                     := '0';
    SCALE_SCH11    : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0) := (others => '0');
    SCALE_SCH11_WE : out std_logic                                                                     := '0';
    sclr           : out std_logic                                                                     := '0';
    ce             : out std_logic;
    xk_re_array   : in T_XK_RE_CHANNEL_ARRAY;
    xk_im_array   : in T_XK_RE_CHANNEL_ARRAY;
    blk_exp_array : in T_BLK_EXP_CHANNEL_ARRAY;
    ovflo_array   : in T_OVFLO_CHANNEL_ARRAY;
    RFD       : in std_logic;
    XN_INDEX  : in std_logic_vector(C_NFFT_MAX-1 downto 0);
    BUSY      : in std_logic;
    EDONE     : in std_logic;
    DONE      : in std_logic;
    DV        : in std_logic;
    XK_INDEX  : in std_logic_vector(C_NFFT_MAX-1 downto 0);
    CPV       : in std_logic;
    RFS       : in std_logic;
    XK_RE     : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK_IM     : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    BLK_EXP   : in std_logic_vector(4 downto 0);
    OVFLO     : in std_logic;
    XK0_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK0_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK1_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK1_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK2_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK2_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK3_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK3_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK4_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK4_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK5_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK5_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK6_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK6_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK7_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK7_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK8_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK8_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK9_RE    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK9_IM    : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK10_RE   : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK10_IM   : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK11_RE   : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    XK11_IM   : in std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
    BLK_EXP0  : in std_logic_vector(4 downto 0);
    BLK_EXP1  : in std_logic_vector(4 downto 0);
    BLK_EXP2  : in std_logic_vector(4 downto 0);
    BLK_EXP3  : in std_logic_vector(4 downto 0);
    BLK_EXP4  : in std_logic_vector(4 downto 0);
    BLK_EXP5  : in std_logic_vector(4 downto 0);
    BLK_EXP6  : in std_logic_vector(4 downto 0);
    BLK_EXP7  : in std_logic_vector(4 downto 0);
    BLK_EXP8  : in std_logic_vector(4 downto 0);
    BLK_EXP9  : in std_logic_vector(4 downto 0);
    BLK_EXP10 : in std_logic_vector(4 downto 0);
    BLK_EXP11 : in std_logic_vector(4 downto 0);
    OVFLO0    : in std_logic;
    OVFLO1    : in std_logic;
    OVFLO2    : in std_logic;
    OVFLO3    : in std_logic;
    OVFLO4    : in std_logic;
    OVFLO5    : in std_logic;
    OVFLO6    : in std_logic;
    OVFLO7    : in std_logic;
    OVFLO8    : in std_logic;
    OVFLO9    : in std_logic;
    OVFLO10   : in std_logic;
    OVFLO11   : in std_logic
    );
end timing_model_axi_wrapper;
architecture synth of timing_model_axi_wrapper is
  function expand_nfft(constant nfft          : in std_logic_vector(NFFT_MAX_WIDTH - 1 downto 0);
                        constant P_NFFT_MAX   : in integer;
                        constant P_SUB_OFFSET : in integer
                        )
    return std_logic_vector is
    variable ret : std_logic_vector(P_NFFT_MAX-1 downto 0) := (others => '0');
  begin
    case nfft is
      when "00011" => ret := std_logic_vector(to_unsigned(8 - P_SUB_OFFSET, P_NFFT_MAX));
      when "00100" => ret := std_logic_vector(to_unsigned(16 - P_SUB_OFFSET, P_NFFT_MAX));
      when "00101" => ret := std_logic_vector(to_unsigned(32 - P_SUB_OFFSET, P_NFFT_MAX));
      when "00110" => ret := std_logic_vector(to_unsigned(64 - P_SUB_OFFSET, P_NFFT_MAX));
      when "00111" => ret := std_logic_vector(to_unsigned(128 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01000" => ret := std_logic_vector(to_unsigned(256 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01001" => ret := std_logic_vector(to_unsigned(512 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01010" => ret := std_logic_vector(to_unsigned(1024 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01011" => ret := std_logic_vector(to_unsigned(2048 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01100" => ret := std_logic_vector(to_unsigned(4096 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01101" => ret := std_logic_vector(to_unsigned(8192 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01110" => ret := std_logic_vector(to_unsigned(16384 - P_SUB_OFFSET, P_NFFT_MAX));
      when "01111" => ret := std_logic_vector(to_unsigned(32768 - P_SUB_OFFSET, P_NFFT_MAX));
      when "10000" => ret := std_logic_vector(to_unsigned(65536 - P_SUB_OFFSET, P_NFFT_MAX));
      when others  => ret := (others => '0');
    end case;
    return ret;
  end expand_nfft;
  constant C_S_AXIS_CONFIG_FIFO_WIDTH_NO_PADDING : integer :=
    calculate_config_width_no_padding(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING, C_NFFT_MAX, C_HAS_BFP, C_ARCH);
  constant C_S_AXIS_DATA_FIFO_WIDTH_NO_PADDING : integer :=
    calculate_data_in_width_no_padding(C_INPUT_WIDTH, C_CHANNELS);
  constant C_M_AXIS_DATA_FIFO_WIDTH_NO_PADDING : integer :=
   calculate_data_out_width_no_padding(C_OUTPUT_WIDTH => C_OUTPUT_WIDTH,
                                       C_CHANNELS     => C_CHANNELS,
                                       C_NFFT_MAX     => C_NFFT_MAX,
                                       C_HAS_OVFLO    => C_HAS_OVFLO,
                                       C_HAS_SCALING  => C_HAS_SCALING,
                                       C_HAS_BFP      => C_HAS_BFP,
                                       C_HAS_XK_INDEX => C_HAS_XK_INDEX,
                                       C_USE_FLT_PT   => C_USE_FLT_PT);
  constant C_M_AXIS_STATUS_FIFO_WIDTH_NO_PADDING : integer :=
    calculate_status_channel_width_no_padding(C_CHANNELS     => C_CHANNELS,
                                               C_HAS_OVFLO   => C_HAS_OVFLO,
                                               C_HAS_SCALING => C_HAS_SCALING,
                                               C_HAS_BFP     => C_HAS_BFP,
                                               C_USE_FLT_PT  => C_USE_FLT_PT);
  constant C_STREAMING_MODE    : integer := is_streaming(C_ARCH);
  signal config_fifo_in   : std_logic_vector(C_S_AXIS_CONFIG_FIFO_WIDTH_NO_PADDING-1 downto 0);
  signal config_fifo_out  : std_logic_vector(C_S_AXIS_CONFIG_FIFO_WIDTH_NO_PADDING-1 downto 0);
  signal config_fifo_read : std_logic := '0';
  signal new_config       : std_logic;
  signal new_config_unregistered : std_logic;
  signal last_config      : std_logic_vector(C_S_AXIS_CONFIG_FIFO_WIDTH_NO_PADDING-1 downto 0) :=
                                             get_config_default_value(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING,
                                                                      C_NFFT_MAX, C_HAS_BFP, C_ARCH);
  signal use_last_config  : std_logic := '0';
  signal config_fifo_out_nfft        : std_logic_vector(NFFT_MAX_WIDTH - 1 downto 0);
  signal config_fifo_out_cp_len      : std_logic_vector(c_nfft_max-1 downto 0);
  signal config_fifo_out_fwd_inv0    : std_logic;
  signal config_fifo_out_fwd_inv1    : std_logic;
  signal config_fifo_out_fwd_inv2    : std_logic;
  signal config_fifo_out_fwd_inv3    : std_logic;
  signal config_fifo_out_fwd_inv4    : std_logic;
  signal config_fifo_out_fwd_inv5    : std_logic;
  signal config_fifo_out_fwd_inv6    : std_logic;
  signal config_fifo_out_fwd_inv7    : std_logic;
  signal config_fifo_out_fwd_inv8    : std_logic;
  signal config_fifo_out_fwd_inv9    : std_logic;
  signal config_fifo_out_fwd_inv10   : std_logic;
  signal config_fifo_out_fwd_inv11   : std_logic;
  signal config_fifo_out_scale_sch0  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch1  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch2  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch3  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch4  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch5  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch6  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch7  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch8  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch9  : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch10 : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal config_fifo_out_scale_sch11 : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal fwd_inv_we_int   : std_logic;
  signal fwd_inv_we_prev  : std_logic;
  signal data_in_fifo_in  : std_logic_vector(C_S_AXIS_DATA_FIFO_WIDTH_NO_PADDING-1 downto 0);
  signal data_in_fifo_out : std_logic_vector(C_S_AXIS_DATA_FIFO_WIDTH_NO_PADDING-1 downto 0);
  signal data_in_tlast    : std_logic;
  signal new_data         : std_logic := '0';
  signal new_data_read    : std_logic;
  signal data_in_fifo_almost_empty: std_logic;
  signal data_in_fifo_empty: std_logic;
  signal data_out_fifo_write      : std_logic;
  signal data_out_fifo_write_data : std_logic_vector(C_M_AXIS_DATA_FIFO_WIDTH_NO_PADDING -1 downto 0);
  signal data_out_fifo_read_data  : std_logic_vector(C_M_AXIS_DATA_FIFO_WIDTH_NO_PADDING -1 downto 0);
  signal data_out_fifo_full       : std_logic;
  signal data_out_tlast           : std_logic;
  signal data_out_fifo_almost_full: std_logic;
  signal m_axis_data_tuser_int    : std_logic_vector (C_M_AXIS_DATA_TUSER_WIDTH-1 downto 0) := (others => '0');
  signal m_axis_data_tvalid_int   :  std_logic                                              := '0';
  signal m_axis_data_tready_int   :  std_logic;
  signal status_fifo_write_desired  : std_logic := '0';
  signal status_fifo_write_decision : std_logic := '0';
  signal status_fifo_full        : std_logic;
  signal status_fifo_almost_full : std_logic;
  signal m_axis_status_tready_int: std_logic;
  constant ST_IDLE                         : integer := 0;
  constant ST_START                        : integer := 1;
  constant ST_LOAD                         : integer := 2;
  constant ST_PROCESS                      : integer := 3;
  constant ST_UNLOAD                       : integer := 4;
  constant ST_UNLOADING                    : integer := 5;
  constant ST_FINISH_FLUSH                 : integer := 6;
  constant ST_FINISH_FLUSH_FP_MODE         : integer := 7;
  constant ST_FINISH_FLUSH_RESET           : integer := 8;
  constant ST_FINISH_FLUSH_RESTORE_CONFIG  : integer := 9;
  constant ST_CONFIG_STALL                 : integer := 10;
  constant ST_CONFIG_NFFT                  : integer := 11;
  signal current_state : std_logic_vector(11 downto 0) := "000000000001";
  signal next_state    : std_logic_vector(11 downto 0) := "000000000001";
  signal flushing       : std_logic := '0';
  signal flushing_set   : std_logic := '0';
  signal flushing_clear : std_logic := '0';
  signal cpv_int : std_logic;
  signal nfft_expandedm1 : std_logic_vector(C_NFFT_MAX-1 downto 0) := expand_nfft(std_logic_vector(to_unsigned(C_NFFT_MAX, NFFT_MAX_WIDTH)), C_NFFT_MAX, 1);
  signal load_output_symbol_counter : std_logic := '0';
  signal dv_resolved_prev           : std_logic := '0';
  signal symbols_out_remaining      : std_logic_vector(C_NFFT_MAX-1 downto 0) := (others => '0');
  signal reading_last_symbol        : std_logic;
  constant FRAMES_IN_PROGRESS_WIDTH : integer := 4;
  signal frames_in_progress    : std_logic_vector(FRAMES_IN_PROGRESS_WIDTH-1 downto 0) := (others => '0');
  signal no_frames_in_progress : std_logic := '0';
  signal data_in_channel_halt              : std_logic := '0';
  signal data_out_channel_halt             : std_logic := '0';
  signal status_channel_halt               : std_logic := '0';
  signal data_in_channel_halt_predicted    : std_logic := '0';
  signal data_out_channel_halt_predicted   : std_logic := '0';
  signal status_channel_halt_predicted     : std_logic := '0';
  signal event_status_channel_halt_int     : std_logic := '0';
  signal event_data_in_channel_halt_int    : std_logic := '0';
  signal event_data_out_channel_halt_int   : std_logic := '0';
  signal ce_int                            : std_logic;
  signal ce_predicted                      : std_logic;
  signal nfft_changing                  : std_logic                                     := '0';
  signal current_nfft                   : std_logic_vector(NFFT_MAX_WIDTH - 1 downto 0) := std_logic_vector(TO_UNSIGNED(C_NFFT_MAX, NFFT_MAX_WIDTH));
  signal next_nfft                      : std_logic_vector(NFFT_MAX_WIDTH - 1 downto 0) := std_logic_vector(TO_UNSIGNED(C_NFFT_MAX, NFFT_MAX_WIDTH));
  signal new_config_with_nfft_change    : std_logic                                     := '0';
  signal new_config_without_nfft_change : std_logic                                     := '0';
  signal nfft_we_int                    : std_logic                                     := '0';
  signal sclr_int                       : std_logic                                     := '0';
  signal fifo_reset                     : std_logic                                     := '0';
  signal reset                          : std_logic                                     := '0';
  signal event_reset                    : std_logic                                     := '0';
  signal reset_pipe                     : std_logic_vector(1 downto 0)  := (others => '0');
  signal frame_started                  : std_logic                     := '0';
begin
  p_generate_reset: process (aclk)
  begin
    if rising_edge(aclk) then
      if aresetn = '0' then
        reset_pipe <= (others => '1');
      else
        reset_pipe <= reset_pipe(reset_pipe'high - 1 downto 0) & '0';
      end if;
    end if;
  end process p_generate_reset;
  reset       <= reset_pipe(reset_pipe'high);
  event_reset <= reset_pipe(reset_pipe'high);
  fifo_reset  <= reset_pipe(reset_pipe'high);
  p_sclr : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        sclr_int <= '1';
      elsif ce_int = '1' then
        if next_state(ST_FINISH_FLUSH_RESET) = '1' then
          sclr_int <= '1';
        else
          sclr_int <= '0';
        end if;
      end if;
    end if;
  end process p_sclr;
  sclr                  <= sclr_int;
  gen_check_aresetn: if C_HAS_ARESETN = 1 generate
    p_check_reset_length: process (aclk)
      variable v_low_count: integer := 0;
      variable v_reset_been_low : boolean := false;
    begin
      if rising_edge(aclk) then
        if aresetn = '0' then
          v_low_count := v_low_count + 1;
          v_reset_been_low := true;
        else
          if v_reset_been_low = true and v_low_count < 2 then
            report "XFFT v8.0: ERROR: ARESETN must be asserted low for a mimimum of 2 clock cycles" severity failure;
          end if;
          v_low_count:= 0;
          v_reset_been_low := false;
        end if;
      end if;
    end process p_check_reset_length;
  end generate;
  gen_m_axis_data_tuser: if C_HAS_XK_INDEX                                                                                                                /= 0 or
                            has_ovflo  (C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT) /= 0 or
                            has_blk_exp(                            C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP                              ) /= 0 generate
    m_axis_data_tuser  <= m_axis_data_tuser_int;
  end generate gen_m_axis_data_tuser;
  m_axis_data_tvalid <= m_axis_data_tvalid_int;
  reading_last_symbol <= '1' when xn_index = nfft_expandedm1 else '0';
  data_in_channel_halt_predicted <= '1' when ((data_in_fifo_almost_empty = '1' and reading_last_symbol = '0') or data_in_fifo_empty = '1')
                                    and rfd  = '1' and flushing = '0' else '0';
  data_out_channel_halt_predicted <= '1' when
                                     (dv = '1' or edone = '1') and
                                     ((data_out_fifo_almost_full = '1' and ((symbols_out_remaining /= std_logic_vector(to_unsigned(1, C_NFFT_MAX))) or edone = '1'))
                                      or data_out_fifo_full = '1')
                                     else '0';
  status_channel_halt_predicted   <= '1' when status_fifo_full = '1' and status_fifo_write_desired = '1' else '0';
  data_in_channel_halt  <= '1' when new_data           = '0' and rfd                       = '1' and flushing = '0' else '0';
  data_out_channel_halt <= '1' when data_out_fifo_full = '1' and dv                        = '1'                    else '0';
  status_channel_halt   <= '1' when status_fifo_full   = '1' and status_fifo_write_desired = '1'                    else '0';
  gen_ce_real_time : if C_THROTTLE_SCHEME = 0 generate
    ce_int <= aclken;
  end generate gen_ce_real_time;
  gen_ce_non_real_time : if C_THROTTLE_SCHEME /= 0 generate
    process (aclk)
    begin
      if rising_edge(aclk) then
        if aresetn = '0' then
          ce_predicted <= '0';
        else
          if aclken                         = '0' or
            data_in_channel_halt_predicted  = '1' or
            data_out_channel_halt_predicted = '1' or
            status_channel_halt_predicted   = '1'
          then
            ce_predicted <= '0';
          else
            ce_predicted <= '1';
          end if;
       end if;
      end if;
    end process;
    ce_int <= ce_predicted;
  end generate gen_ce_non_real_time;
  ce <= ce_int;
  gen_has_cp : if C_HAS_CYCLIC_PREFIX = 1 and C_HAS_NATURAL_OUTPUT = 1 generate
    cpv_int <= cpv;
  end generate gen_has_cp;
  gen_no_cp : if C_HAS_CYCLIC_PREFIX = 0 or C_HAS_NATURAL_OUTPUT = 0 generate
    cpv_int <= '0';
  end generate gen_no_cp;
  config_fifo_in <= remove_config_data_padding(s_axis_config_tdata,
                                               C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS,
                                               C_HAS_SCALING, C_NFFT_MAX, C_HAS_BFP, C_ARCH);
  data_in_fifo_in <= convert_axi_to_data_in_vector(tdata         => s_axis_data_tdata,
                                                   tlast         => s_axis_data_tlast,
                                                   C_INPUT_WIDTH => C_INPUT_WIDTH,
                                                   C_CHANNELS    => C_CHANNELS);
  config_channel_fifo: entity work.timing_model_axi_wrapper_input_fifo
    generic map (
      C_FIFO_WIDTH      => C_S_AXIS_CONFIG_FIFO_WIDTH_NO_PADDING,
      C_THROTTLE_SCHEME => C_THROTTLE_SCHEME,
      C_HAS_SKID_BUFFER => 0
      )
    port map (
      aclk         => aclk,
      aresetn      => aresetn,
      fifo_reset   => fifo_reset,
      aclken       => aclken,
      tvalid       => s_axis_config_tvalid,
      tready       => s_axis_config_tready,
      data_in      => config_fifo_in,
      empty        => open,
      almost_empty => open,
      read_fifo    => config_fifo_read,
      data_avail   => new_config_unregistered,
      data_out     => config_fifo_out);
  gen_store_last_config: if C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 0 generate
    p_store_last_config: process (aclk)
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          last_config <= get_config_default_value(C_HAS_NFFT, C_HAS_CYCLIC_PREFIX, C_HAS_NATURAL_OUTPUT, C_CHANNELS, C_HAS_SCALING,
                                                  C_NFFT_MAX, C_HAS_BFP, C_ARCH);
        else
          if config_fifo_read = '1' then
            last_config <= config_fifo_out;
          end if;
        end if;
      end if;
    end process p_store_last_config;
    p_use_last_config : process (aclk)
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          use_last_config <= '0';
        else
          if ce_int = '1' then
            if next_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '1' then
               use_last_config <= '1';
            else
              use_last_config <= '0';
            end if;
          end if;
        end if;
      end if;
    end process p_use_last_config;
  end generate gen_store_last_config;
  p_split_config_fifo_output : process (config_fifo_out, last_config, use_last_config)
    variable config_source  : std_logic_vector(C_S_AXIS_CONFIG_FIFO_WIDTH_NO_PADDING-1 downto 0);
  begin
    if use_last_config = '1' then
      config_source := last_config;
    else
      config_source := config_fifo_out;
    end if;
    split_config_tdata (
                        tdata                => config_source,
                        nfft                 => config_fifo_out_nfft,
                        cp_len               => config_fifo_out_cp_len,
                        fwd_inv0             => config_fifo_out_fwd_inv0,
                        fwd_inv1             => config_fifo_out_fwd_inv1,
                        fwd_inv2             => config_fifo_out_fwd_inv2,
                        fwd_inv3             => config_fifo_out_fwd_inv3,
                        fwd_inv4             => config_fifo_out_fwd_inv4,
                        fwd_inv5             => config_fifo_out_fwd_inv5,
                        fwd_inv6             => config_fifo_out_fwd_inv6,
                        fwd_inv7             => config_fifo_out_fwd_inv7,
                        fwd_inv8             => config_fifo_out_fwd_inv8,
                        fwd_inv9             => config_fifo_out_fwd_inv9,
                        fwd_inv10            => config_fifo_out_fwd_inv10,
                        fwd_inv11            => config_fifo_out_fwd_inv11,
                        scale_sch0           => config_fifo_out_scale_sch0,
                        scale_sch1           => config_fifo_out_scale_sch1,
                        scale_sch2           => config_fifo_out_scale_sch2,
                        scale_sch3           => config_fifo_out_scale_sch3,
                        scale_sch4           => config_fifo_out_scale_sch4,
                        scale_sch5           => config_fifo_out_scale_sch5,
                        scale_sch6           => config_fifo_out_scale_sch6,
                        scale_sch7           => config_fifo_out_scale_sch7,
                        scale_sch8           => config_fifo_out_scale_sch8,
                        scale_sch9           => config_fifo_out_scale_sch9,
                        scale_sch10          => config_fifo_out_scale_sch10,
                        scale_sch11          => config_fifo_out_scale_sch11,
                        C_HAS_NFFT           => C_HAS_NFFT,
                        C_HAS_CYCLIC_PREFIX  => C_HAS_CYCLIC_PREFIX,
                        C_HAS_NATURAL_OUTPUT => C_HAS_NATURAL_OUTPUT,
                        C_CHANNELS           => C_CHANNELS,
                        C_HAS_SCALING        => C_HAS_SCALING,
                        C_NFFT_MAX           => C_NFFT_MAX,
                        C_HAS_BFP            => C_HAS_BFP,
                        C_ARCH               => C_ARCH,
                        C_CONTAINS_PADDING   => 0
                        );
  end process p_split_config_fifo_output;
  data_in_channel_fifo: entity work.timing_model_axi_wrapper_input_fifo
    generic map (
      C_FIFO_WIDTH      => C_S_AXIS_DATA_FIFO_WIDTH_NO_PADDING,
      C_THROTTLE_SCHEME => C_THROTTLE_SCHEME,
      C_HAS_SKID_BUFFER => 1
      )
    port map (
      aclk        => aclk,
      aresetn    => aresetn,
      fifo_reset => fifo_reset,
      aclken     => aclken,
      tvalid     => s_axis_data_tvalid,
      tready     => s_axis_data_tready,
      empty        => data_in_fifo_empty,
      almost_empty => data_in_fifo_almost_empty,
      data_in    => data_in_fifo_in,
      read_fifo  => new_data_read,
      data_avail => new_data,
      data_out   => data_in_fifo_out);
  new_data_read <= '1' when rfd = '1' and ce_int = '1' and flushing = '0' and reset = '0' else '0';
  p_data_to_fft : process (data_in_fifo_out, rfd)
    function vector_bitwise_and(v: std_logic_vector;
                                b: std_logic) return std_logic_vector is
      variable ret: std_logic_vector(v'range) := (others => '0');
    begin
      for i in v'range loop
        ret(i) := v(i) and b;
      end loop;
      return(ret);
    end function vector_bitwise_and;
     variable v_XN_RE_int          : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN_IM_int          : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN0_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN0_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN1_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN1_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN2_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN2_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN3_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN3_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN4_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN4_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN5_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN5_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN6_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN6_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN7_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN7_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN8_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN8_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN9_RE_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN9_IM_int         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN10_RE_int        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN10_IM_int        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN11_RE_int        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
     variable v_XN11_IM_int        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  begin
    convert_data_in_vector_to_fft_in (fifo_vector        => data_in_fifo_out,
                                        tlast              => data_in_tlast,
                                        XN_RE              => v_XN_RE_int,
                                        XN_IM              => v_XN_IM_int,
                                        XN0_RE             => v_XN0_RE_int,
                                        XN0_IM             => v_XN0_IM_int,
                                        XN1_RE             => v_XN1_RE_int,
                                        XN1_IM             => v_XN1_IM_int,
                                        XN2_RE             => v_XN2_RE_int,
                                        XN2_IM             => v_XN2_IM_int,
                                        XN3_RE             => v_XN3_RE_int,
                                        XN3_IM             => v_XN3_IM_int,
                                        XN4_RE             => v_XN4_RE_int,
                                        XN4_IM             => v_XN4_IM_int,
                                        XN5_RE             => v_XN5_RE_int,
                                        XN5_IM             => v_XN5_IM_int,
                                        XN6_RE             => v_XN6_RE_int,
                                        XN6_IM             => v_XN6_IM_int,
                                        XN7_RE             => v_XN7_RE_int,
                                        XN7_IM             => v_XN7_IM_int,
                                        XN8_RE             => v_XN8_RE_int,
                                        XN8_IM             => v_XN8_IM_int,
                                        XN9_RE             => v_XN9_RE_int,
                                        XN9_IM             => v_XN9_IM_int,
                                        XN10_RE            => v_XN10_RE_int,
                                        XN10_IM            => v_XN10_IM_int,
                                        XN11_RE            => v_XN11_RE_int,
                                        XN11_IM            => v_XN11_IM_int,
                                        C_INPUT_WIDTH      => C_INPUT_WIDTH,
                                        C_CHANNELS         => C_CHANNELS,
                                        C_CONTAINS_PADDING => 0
                                        );
      if is_rdx2_lite(C_ARCH) = 1 and
         (
            (has_ovflo  (C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT) /= 0)  or
            (has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0)
          )
      then
        XN_RE              <= vector_bitwise_and(v => v_XN_RE_int, b => rfd);
        XN_IM              <= vector_bitwise_and(v => v_XN_IM_int, b => rfd);
        XN0_RE             <= vector_bitwise_and(v => v_XN0_RE_int, b => rfd);
        XN0_IM             <= vector_bitwise_and(v => v_XN0_IM_int, b => rfd);
        XN1_RE             <= vector_bitwise_and(v => v_XN1_RE_int, b => rfd);
        XN1_IM             <= vector_bitwise_and(v => v_XN1_IM_int, b => rfd);
        XN2_RE             <= vector_bitwise_and(v => v_XN2_RE_int, b => rfd);
        XN2_IM             <= vector_bitwise_and(v => v_XN2_IM_int, b => rfd);
        XN3_RE             <= vector_bitwise_and(v => v_XN3_RE_int, b => rfd);
        XN3_IM             <= vector_bitwise_and(v => v_XN3_IM_int, b => rfd);
        XN4_RE             <= vector_bitwise_and(v => v_XN4_RE_int, b => rfd);
        XN4_IM             <= vector_bitwise_and(v => v_XN4_IM_int, b => rfd);
        XN5_RE             <= vector_bitwise_and(v => v_XN5_RE_int, b => rfd);
        XN5_IM             <= vector_bitwise_and(v => v_XN5_IM_int, b => rfd);
        XN6_RE             <= vector_bitwise_and(v => v_XN6_RE_int, b => rfd);
        XN6_IM             <= vector_bitwise_and(v => v_XN6_IM_int, b => rfd);
        XN7_RE             <= vector_bitwise_and(v => v_XN7_RE_int, b => rfd);
        XN7_IM             <= vector_bitwise_and(v => v_XN7_IM_int, b => rfd);
        XN8_RE             <= vector_bitwise_and(v => v_XN8_RE_int, b => rfd);
        XN8_IM             <= vector_bitwise_and(v => v_XN8_IM_int, b => rfd);
        XN9_RE             <= vector_bitwise_and(v => v_XN9_RE_int, b => rfd);
        XN9_IM             <= vector_bitwise_and(v => v_XN9_IM_int, b => rfd);
        XN10_RE            <= vector_bitwise_and(v => v_XN10_RE_int, b => rfd);
        XN10_IM            <= vector_bitwise_and(v => v_XN10_IM_int, b => rfd);
        XN11_RE            <= vector_bitwise_and(v => v_XN11_RE_int, b => rfd);
        XN11_IM            <= vector_bitwise_and(v => v_XN11_IM_int, b => rfd);
      else
        XN_RE              <= v_XN_RE_int ;
        XN_IM              <= v_XN_IM_int ;
        XN0_RE             <= v_XN0_RE_int;
        XN0_IM             <= v_XN0_IM_int;
        XN1_RE             <= v_XN1_RE_int;
        XN1_IM             <= v_XN1_IM_int;
        XN2_RE             <= v_XN2_RE_int;
        XN2_IM             <= v_XN2_IM_int;
        XN3_RE             <= v_XN3_RE_int;
        XN3_IM             <= v_XN3_IM_int;
        XN4_RE             <= v_XN4_RE_int;
        XN4_IM             <= v_XN4_IM_int;
        XN5_RE             <= v_XN5_RE_int;
        XN5_IM             <= v_XN5_IM_int;
        XN6_RE             <= v_XN6_RE_int;
        XN6_IM             <= v_XN6_IM_int;
        XN7_RE             <= v_XN7_RE_int;
        XN7_IM             <= v_XN7_IM_int;
        XN8_RE             <= v_XN8_RE_int;
        XN8_IM             <= v_XN8_IM_int;
        XN9_RE             <= v_XN9_RE_int;
        XN9_IM             <= v_XN9_IM_int;
        XN10_RE            <= v_XN10_RE_int;
        XN10_IM            <= v_XN10_IM_int;
        XN11_RE            <= v_XN11_RE_int;
        XN11_IM            <= v_XN11_IM_int;
      end if;
  end process p_data_to_fft;
  p_current_nfft : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        current_nfft <= std_logic_vector(TO_UNSIGNED(C_NFFT_MAX, NFFT_MAX_WIDTH));
      elsif ce_int = '1' then
        if C_HAS_NFFT = 1 and next_state(ST_CONFIG_NFFT) = '1' then
          current_nfft <= next_nfft;
        end if;
      end if;
    end if;
  end process p_current_nfft;
  next_nfft                      <= config_fifo_out_nfft;
  nfft_changing                  <= '1' when C_HAS_NFFT = 1 and current_nfft /= next_nfft else '0';
  process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        new_config                     <= '0';
        new_config_with_nfft_change    <= '0';
        new_config_without_nfft_change <= '0';
      elsif ce_int = '1' then
        new_config <= new_config_unregistered;
        if current_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '0' then
          if new_config_unregistered = '1' and nfft_changing = '1' then
            new_config_with_nfft_change    <= '1';
          else
            new_config_with_nfft_change    <= '0';
          end if;
        end if;
        if current_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '0' then
          if new_config_unregistered = '1' and nfft_changing = '0' then
            new_config_without_nfft_change    <= '1';
          else
            new_config_without_nfft_change    <= '0';
          end if;
        end if;
      end if;
    end if;
  end process;
  p_state_logic : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        current_state <= (others => '0');
        current_state(ST_IDLE) <= '1';
      elsif ce_int = '1' then
        current_state <= next_state;
      end if;
    end if;
  end process p_state_logic;
  next_state(ST_IDLE)    <= '1' when
                            (current_state(ST_IDLE) = '1' and new_data = '0' and new_config_with_nfft_change = '0') or
                            (current_state(ST_LOAD) = '1' and reading_last_symbol = '1' and (data_in_fifo_almost_empty = '1' or
                                                              new_config_with_nfft_change = '1') and C_STREAMING_MODE = 1  ) or
                           (current_state(ST_UNLOADING) = '1' and dv       = '0'                           ) or
                           (current_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '1')
                           else '0';
  next_state(ST_START) <=  '1' when
                           (current_state(ST_START) = '1' and rfd = '0') or
                           (current_state(ST_IDLE) = '1' and new_data = '1' and new_config_with_nfft_change = '0') or
                           (current_state(ST_LOAD) = '1' and reading_last_symbol = '1' and (data_in_fifo_almost_empty = '0'
                                                         and new_config_with_nfft_change = '0') and C_STREAMING_MODE = 1  ) or
                           (current_state(ST_PROCESS) = '1' and edone = '1' and C_HAS_NATURAL_OUTPUT = 0) or
                           (current_state(ST_CONFIG_NFFT) = '1' and new_data = '1')
                           else '0';
  next_state(ST_LOAD)                         <= '1' when
                                                 (current_state(ST_LOAD) = '1' and reading_last_symbol = '0') or
                                                 (current_state(ST_START) = '1' and rfd = '1')
                                                 else '0';
  next_state(ST_PROCESS)                       <= '1' when
                                                  (current_state(ST_PROCESS) = '1' and edone = '0') or
                                                  (current_state(ST_LOAD)    = '1' and reading_last_symbol = '1' and C_STREAMING_MODE = 0 and flushing = '0')
                                                  else '0';
  next_state(ST_UNLOAD)                        <= '1' when
                                                  (current_state(ST_UNLOAD) = '1' and dv = '0') or
                                                  (current_state(ST_PROCESS) = '1' and edone = '1' and C_HAS_NATURAL_OUTPUT = 1 )
                                                  else '0';
  next_state(ST_UNLOADING)                    <= '1' when
                                                 (current_state(ST_UNLOAD   ) = '1' and dv = '1') or
                                                 (current_state(ST_UNLOADING) = '1' and dv = '1')
                                                 else '0';
  next_state(ST_CONFIG_STALL                 ) <= '1' when
                                                  (current_state(ST_IDLE) = '1' and  C_STREAMING_MODE = 1 and new_config_with_nfft_change = '1') or
                                                  (current_state(ST_CONFIG_STALL) = '1' and no_frames_in_progress = '0')
                                                  else '0';
  next_state(ST_CONFIG_NFFT                  ) <= '1' when
                                                  (current_state(ST_CONFIG_STALL) = '1' and no_frames_in_progress = '1') or
                                                  (current_state(ST_IDLE) = '1' and  C_STREAMING_MODE /= 1 and new_config_with_nfft_change = '1') or
                                                  (current_state(ST_CONFIG_NFFT) = '1' and new_data = '0')
                                                  else '0';
  next_state(ST_FINISH_FLUSH                 ) <= '1' when
                                                  (current_state(ST_LOAD) = '1' and reading_last_symbol = '1' and C_STREAMING_MODE = 0
                                                               and C_HAS_NATURAL_OUTPUT = 0 and flushing = '1' and C_USE_FLT_PT = 0) or
                                                  (current_state(ST_FINISH_FLUSH_FP_MODE) = '1' and dv = '1') or
                                                  (current_state(ST_FINISH_FLUSH) = '1' and dv = '1')
                                                  else '0';
  next_state(ST_FINISH_FLUSH_FP_MODE         ) <= '1' when
                                                  (current_state(ST_LOAD) = '1' and reading_last_symbol = '1' and C_STREAMING_MODE = 0
                                                   and C_HAS_NATURAL_OUTPUT = 0 and flushing = '1' and C_USE_FLT_PT = 1) or
                                                  (current_state(ST_FINISH_FLUSH_FP_MODE) = '1' and dv = '0' )
                                                  else '0';
  next_state(ST_FINISH_FLUSH_RESET           ) <= '1' when
                                                  (current_state(ST_FINISH_FLUSH) = '1' and dv = '0')
                                                  else '0';
  next_state(ST_FINISH_FLUSH_RESTORE_CONFIG  ) <= '1' when
                                                  (current_state(ST_FINISH_FLUSH_RESET) = '1')
                                                  else '0';
  blk_check_fsm: block
    signal reset_d1         : std_logic := '0';
  begin
    p_check_fsm: process (aclk)
      variable number_of_1s : integer;
    begin
      if rising_edge(aclk) then
        reset_d1 <= reset;
        if reset_d1 = '1' then
          assert (current_state(ST_IDLE) = '1')
            report "AXI_WRAPPER is in reset but FSM is not in ST_IDLE"
            severity failure;
        end if;
        number_of_1s := 0;
        for i in current_state'range loop
          if current_state(i) = '1' then
            number_of_1s := number_of_1s + 1;
          end if;
        end loop;
        if number_of_1s = 0 then
          report "AXI_WRAPPER FSM is not one hot - no bits are set"
            severity failure;
        end if;
        if number_of_1s > 1 then
          report "AXI_WRAPPER FSM is not one hot - multiple bits are set"
            severity failure;
        end if;
        if current_state(ST_PROCESS) = '1' then
          assert C_STREAMING_MODE = 0
            report "AXI_WRAPPER is in ST_PROCESS but it's not in Burst IO Mode"
            severity failure;
        end if;
        if current_state(ST_UNLOAD) = '1' then
          assert C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 1
            report "AXI_WRAPPER is in ST_UNLOAD but it's not in Burst IO with Natural Outputs mode"
            severity failure;
        end if;
        if current_state(ST_UNLOADING) = '1' then
          assert C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 1
            report "AXI_WRAPPER is in ST_UNLOADING but it's not in Burst IO with Natural Outputs mode"
            severity failure;
        end if;
        if current_state(ST_FINISH_FLUSH_FP_MODE) = '1' then
          assert C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 0 and C_USE_FLT_PT = 1
            report "AXI_WRAPPER is in ST_FINISH_FLUSH_FP_MODE but it's not in Floating point Burst IO with Bit Reversed Outputs mode"
            severity failure;
        end if;
        if current_state(ST_FINISH_FLUSH) = '1' then
          assert C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 0
            report "AXI_WRAPPER is in ST_FINISH_FLUSH but it's not in Burst IO with Bit Reversed Outputs mode"
            severity failure;
        end if;
        if current_state(ST_FINISH_FLUSH_RESET) = '1' then
          assert C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 0
            report "AXI_WRAPPER is in ST_FINISH_FLUSH_RESET but it's not in Burst IO with Bit Reversed Outputs mode"
            severity failure;
        end if;
        if current_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '1' then
         assert C_STREAMING_MODE = 0 and C_HAS_NATURAL_OUTPUT = 0
           report "AXI_WRAPPER is in ST_FINISH_FLUSH_RESTORE_CONFIG but it's not in Burst IO with Bit Reversed Outputs mode"
           severity failure;
        end if;
        if current_state(ST_CONFIG_STALL) = '1' then
         assert C_STREAMING_MODE = 1
           report "AXI_WRAPPER is in ST_CONFIG_STALL but it's not in Streaming Mode"
           severity failure;
        end if;
      end if;
    end process p_check_fsm;
  end block blk_check_fsm;
  flushing_set   <= '1' when current_state(ST_PROCESS) = '1' and next_state(ST_START) = '1' and (new_data = '0' or new_config_with_nfft_change = '1') else '0';
  flushing_clear <= '1' when next_state(ST_FINISH_FLUSH) = '1' else '0';
  p_flushing : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        flushing <= '0';
      else
        if ce_int = '1' then
          if flushing_set = '1' then
            flushing <= '1';
          elsif flushing_clear = '1' then
            flushing <= '0';
          end if;
        end if;
      end if;
    end if;
  end process p_flushing;
  p_apply_configuration : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
         cp_len_we        <= '0';
         fwd_inv_we_int   <= '0';
         scale_sch_we     <= '0';
         fwd_inv0_we      <= '0';
         fwd_inv1_we      <= '0';
         fwd_inv2_we      <= '0';
         fwd_inv3_we      <= '0';
         fwd_inv4_we      <= '0';
         fwd_inv5_we      <= '0';
         fwd_inv6_we      <= '0';
         fwd_inv7_we      <= '0';
         fwd_inv8_we      <= '0';
         fwd_inv9_we      <= '0';
         fwd_inv10_we     <= '0';
         fwd_inv11_we     <= '0';
         scale_sch0_we    <= '0';
         scale_sch1_we    <= '0';
         scale_sch2_we    <= '0';
         scale_sch3_we    <= '0';
         scale_sch4_we    <= '0';
         scale_sch5_we    <= '0';
         scale_sch6_we    <= '0';
         scale_sch7_we    <= '0';
         scale_sch8_we    <= '0';
         scale_sch9_we    <= '0';
         scale_sch10_we   <= '0';
         scale_sch11_we   <= '0';
      else
        if ce_int = '1' then
          if (current_state(ST_START) = '1' and next_state(ST_LOAD) = '1' and new_config = '1' and flushing = '0') or
             (next_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '1') then
            cp_len_we      <= '1';
            scale_sch_we   <= '1';
            fwd_inv_we_int <= '1';
            scale_sch0_we  <= '1';
            scale_sch1_we  <= '1';
            scale_sch2_we  <= '1';
            scale_sch3_we  <= '1';
            scale_sch4_we  <= '1';
            scale_sch5_we  <= '1';
            scale_sch6_we  <= '1';
            scale_sch7_we  <= '1';
            scale_sch8_we  <= '1';
            scale_sch9_we  <= '1';
            scale_sch10_we <= '1';
            scale_sch11_we <= '1';
            fwd_inv0_we    <= '1';
            fwd_inv1_we    <= '1';
            fwd_inv2_we    <= '1';
            fwd_inv3_we    <= '1';
            fwd_inv4_we    <= '1';
            fwd_inv5_we    <= '1';
            fwd_inv6_we    <= '1';
            fwd_inv7_we    <= '1';
            fwd_inv8_we    <= '1';
            fwd_inv9_we    <= '1';
            fwd_inv10_we   <= '1';
            fwd_inv11_we   <= '1';
          else
            cp_len_we      <= '0';
            fwd_inv_we_int <= '0';
            scale_sch_we   <= '0';
            fwd_inv0_we    <= '0';
            fwd_inv1_we    <= '0';
            fwd_inv2_we    <= '0';
            fwd_inv3_we    <= '0';
            fwd_inv4_we    <= '0';
            fwd_inv5_we    <= '0';
            fwd_inv6_we    <= '0';
            fwd_inv7_we    <= '0';
            fwd_inv8_we    <= '0';
            fwd_inv9_we    <= '0';
            fwd_inv10_we   <= '0';
            fwd_inv11_we   <= '0';
            scale_sch0_we  <= '0';
            scale_sch1_we  <= '0';
            scale_sch2_we  <= '0';
            scale_sch3_we  <= '0';
            scale_sch4_we  <= '0';
            scale_sch5_we  <= '0';
            scale_sch6_we  <= '0';
            scale_sch7_we  <= '0';
            scale_sch8_we  <= '0';
            scale_sch9_we  <= '0';
            scale_sch10_we <= '0';
            scale_sch11_we <= '0';
          end if;
        end if;
      end if;
    end if;
  end process p_apply_configuration;
  fwd_inv_we <= fwd_inv_we_int;
  p_config_fifo_read: process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        fwd_inv_we_prev <= '0';
      else
        fwd_inv_we_prev <= fwd_inv_we_int and (not use_last_config);
        if fwd_inv_we_prev = '1' and fwd_inv_we_int = '0' then
          config_fifo_read <= '1';
        else
          config_fifo_read <= '0';
        end if;
      end if;
    end if;
  end process p_config_fifo_read;
  cp_len      <= config_fifo_out_cp_len;
  scale_sch   <= config_fifo_out_scale_sch0;
  fwd_inv     <= config_fifo_out_fwd_inv0;
  scale_sch0  <= config_fifo_out_scale_sch0;
  scale_sch1  <= config_fifo_out_scale_sch1;
  scale_sch2  <= config_fifo_out_scale_sch2;
  scale_sch3  <= config_fifo_out_scale_sch3;
  scale_sch4  <= config_fifo_out_scale_sch4;
  scale_sch5  <= config_fifo_out_scale_sch5;
  scale_sch6  <= config_fifo_out_scale_sch6;
  scale_sch7  <= config_fifo_out_scale_sch7;
  scale_sch8  <= config_fifo_out_scale_sch8;
  scale_sch9  <= config_fifo_out_scale_sch9;
  scale_sch10 <= config_fifo_out_scale_sch10;
  scale_sch11 <= config_fifo_out_scale_sch11;
  fwd_inv0    <= config_fifo_out_fwd_inv0;
  fwd_inv1    <= config_fifo_out_fwd_inv1;
  fwd_inv2    <= config_fifo_out_fwd_inv2;
  fwd_inv3    <= config_fifo_out_fwd_inv3;
  fwd_inv4    <= config_fifo_out_fwd_inv4;
  fwd_inv5    <= config_fifo_out_fwd_inv5;
  fwd_inv6    <= config_fifo_out_fwd_inv6;
  fwd_inv7    <= config_fifo_out_fwd_inv7;
  fwd_inv8    <= config_fifo_out_fwd_inv8;
  fwd_inv9    <= config_fifo_out_fwd_inv9;
  fwd_inv10   <= config_fifo_out_fwd_inv10;
  fwd_inv11   <= config_fifo_out_fwd_inv11;
  start <= next_state(ST_START);
  unload <= '1';
  gen_has_nfft : if C_HAS_NFFT = 1 generate
    constant NFFT_MIN : std_logic_vector(NFFT_MAX_WIDTH - 1 downto 0) :=
      std_logic_vector(to_unsigned(get_nfft_min(C_ARCH, C_HAS_NFFT, C_NFFT_MAX), NFFT_MAX_WIDTH));
  begin
    p_apply_nfft: process (aclk)
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          nfft_we_int <= '0';
          nfft_we     <= '0';
        elsif ce_int = '1' then
          if (current_state(ST_CONFIG_NFFT) = '0' and next_state(ST_CONFIG_NFFT) = '1') or
            (next_state(ST_FINISH_FLUSH_RESTORE_CONFIG) = '1') then
            nfft_we     <= '1';
            nfft_we_int <= '1';
          else
            nfft_we     <= '0';
            nfft_we_int <= '0';
          end if;
        end if;
      end if;
    end process p_apply_nfft;
    nfft    <= next_nfft;
    p_expand_nfft : process (aclk)
      variable resolved_nfft : std_logic_vector(NFFT_MAX_WIDTH - 1 downto 0);
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          nfft_expandedm1 <= expand_nfft(std_logic_vector(to_unsigned(C_NFFT_MAX, NFFT_MAX_WIDTH)), C_NFFT_MAX, 1);
        elsif ce_int = '1' and nfft_we_int = '1' then
          if next_nfft < NFFT_MIN then
            resolved_nfft := NFFT_MIN;
          elsif next_nfft > std_logic_vector(to_unsigned(C_NFFT_MAX, NFFT_MAX_WIDTH)) then
            resolved_nfft := std_logic_vector(to_unsigned(C_NFFT_MAX, NFFT_MAX_WIDTH));
          else
            resolved_nfft := next_nfft;
          end if;
          nfft_expandedm1 <= expand_nfft(resolved_nfft, C_NFFT_MAX, 1);
        end if;
      end if;
    end process p_expand_nfft;
  end generate gen_has_nfft;
  gen_no_nfft : if C_HAS_NFFT = 0 generate
  begin
    nfft_we <= '0';
    nfft    <= (others => '0');
    nfft_expandedm1 <= expand_nfft(std_logic_vector(to_unsigned(C_NFFT_MAX, NFFT_MAX_WIDTH)), C_NFFT_MAX, 1);
  end generate gen_no_nfft;
  p_edge_detect_dv : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        dv_resolved_prev <= '0';
      elsif ce_int = '1' then
        dv_resolved_prev <= dv and (not cpv_int);
      end if;
    end if;
  end process p_edge_detect_dv;
  load_output_symbol_counter <= '1' when ((dv and (not cpv_int)) = '1' and dv_resolved_prev = '0') or
                                (symbols_out_remaining = std_logic_vector(to_unsigned(0, C_NFFT_MAX)))
                                else '0';
  p_output_symbol_counter : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        symbols_out_remaining <= (others => '0');
      elsif ce_int = '1' then
        if load_output_symbol_counter = '1' then
          symbols_out_remaining <= nfft_expandedm1;
        elsif data_out_fifo_write = '1' and cpv_int = '0' then
          symbols_out_remaining <= std_logic_vector(unsigned(symbols_out_remaining) - "01");
        end if;
      end if;
    end if;
  end process p_output_symbol_counter;
  data_out_tlast <= '1' when dv = '1' and (symbols_out_remaining = std_logic_vector(to_unsigned(1, C_NFFT_MAX))) else '0';
  p_frames_in_progress_counter : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' or nfft_we_int = '1' or sclr_int = '1' then
        frames_in_progress <= (others => '0');
      elsif ce_int = '1' then
        if frame_started = '1' and data_out_tlast = '0' then
          assert (frames_in_progress /= std_logic_vector(to_unsigned(15, FRAMES_IN_PROGRESS_WIDTH))) report "Frame In Progress Counter about to wrap" severity failure;
          frames_in_progress <= std_logic_vector(unsigned(frames_in_progress) + "0001");
        elsif frame_started = '0' and data_out_tlast = '1' then
          frames_in_progress <= std_logic_vector(unsigned(frames_in_progress) - "0001");
        end if;
      end if;
    end if;
  end process p_frames_in_progress_counter;
  p_no_frames_in_progress : process (aclk)
  begin
    if rising_edge(aclk) then
      if reset = '1' then
        no_frames_in_progress <= '0';
      elsif ce_int = '1' then
        if frames_in_progress = std_logic_vector(to_unsigned(0, FRAMES_IN_PROGRESS_WIDTH)) and frame_started = '0' then
           no_frames_in_progress <= '1';
        else
           no_frames_in_progress <= '0';
        end if;
      end if;
    end if;
  end process p_no_frames_in_progress;
  frame_started <= '1' when current_state(ST_START) = '1' and next_state(ST_LOAD) = '1' else '0';
  blk_event_frame_started: block
    type   ced_state_t is (ST_NO_EVENT, ST_WAIT_FOR_DEST);
    signal ced_current_state: ced_state_t := ST_NO_EVENT;
    signal event_frame_started_int  : std_logic := '0';
  begin
     event_frame_started_int  <= '1' when
                                   frame_started = '1' and flushing = '0' and ce_int = '1'
                                   else '0';
    process (aclk)
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          ced_current_state <= ST_NO_EVENT;
          event_frame_started <= '0';
        else
          case ced_current_state is
            when ST_NO_EVENT =>
              event_frame_started <= event_frame_started_int;
              if event_frame_started_int = '1'  then
                ced_current_state <= ST_WAIT_FOR_DEST;
              end if;
            when ST_WAIT_FOR_DEST =>
              if aclken = '1' then
                event_frame_started <= event_frame_started_int;
                if event_frame_started_int = '0' then
                  ced_current_state <= ST_NO_EVENT;
                end if;
              end if;
            when others =>
          end case;
        end if;
      end if;
    end process;
  end block blk_event_frame_started;
  blk_event_tlast_missing: block
    type   ced_state_t is (ST_NO_EVENT, ST_WAIT_FOR_DEST);
    signal ced_current_state: ced_state_t := ST_NO_EVENT;
    signal event_tlast_missing_int  : std_logic := '0';
  begin
    event_tlast_missing_int  <= '1' when
                                   data_in_tlast = '0' and reading_last_symbol = '1' and flushing = '0' and ce_int = '1'
                                   else '0';
    process (aclk)
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          ced_current_state <= ST_NO_EVENT;
          event_tlast_missing <= '0';
        else
          case ced_current_state is
            when ST_NO_EVENT =>
              event_tlast_missing <= event_tlast_missing_int;
              if event_tlast_missing_int = '1'  then
                ced_current_state <= ST_WAIT_FOR_DEST;
              end if;
            when ST_WAIT_FOR_DEST =>
              if aclken = '1' then
                event_tlast_missing <= event_tlast_missing_int;
                if event_tlast_missing_int = '0' then
                  ced_current_state <= ST_NO_EVENT;
                end if;
              end if;
            when others =>
          end case;
        end if;
      end if;
    end process;
  end block blk_event_tlast_missing;
  blk_event_tlast_unexpected: block
    type   ced_state_t is (ST_NO_EVENT, ST_WAIT_FOR_DEST);
    signal ced_current_state          : ced_state_t := ST_NO_EVENT;
    signal event_tlast_unexpected_int : std_logic := '0';
    signal events_to_transfer     : std_logic_vector(1 downto 0) := (others => '0');
    signal events_to_transfer_nxt : std_logic_vector(1 downto 0) := (others => '0');
    signal new_event  : std_logic := '0';
    signal event_done : std_logic := '0';
  begin
    event_tlast_unexpected_int  <= '1' when
                                   data_in_tlast = '1' and reading_last_symbol = '0' and flushing = '0' and ce_int = '1' and rfd = '1'
                                   else '0';
    new_event  <= event_tlast_unexpected_int;
    event_done <= '1' when events_to_transfer/= std_logic_vector(to_unsigned(0, 2)) and aclken = '1' else '0';
    events_to_transfer_nxt <= std_logic_vector(unsigned(events_to_transfer) + "01") when new_event = '1' and event_done = '0' else
                              std_logic_vector(unsigned(events_to_transfer) - "01") when new_event = '0' and event_done = '1' else
                              events_to_transfer;
    process (aclk)
    begin
      if rising_edge(aclk) then
        if reset = '1' then
          events_to_transfer <= (others => '0');
          event_tlast_unexpected <= '0';
        else
          events_to_transfer <= events_to_transfer_nxt;
          if events_to_transfer_nxt = std_logic_vector(to_unsigned(0, 2)) then
            event_tlast_unexpected <= '0';
          else
            event_tlast_unexpected <= '1';
          end if;
        end if;
      end if;
    end process;
  end block blk_event_tlast_unexpected;
  gen_event_fft_overflow : if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT) /= 0 generate
    p_event_fft_overflow : process (aclk)
      variable v_ovflo_resolved : std_logic := '0';
    begin
      if rising_edge(aclk) then
        if event_reset = '1' then
          event_fft_overflow <= '0';
        elsif aclken = '1' then
          if m_axis_data_tvalid_int = '1' and m_axis_data_tready_int = '1' then
            v_ovflo_resolved := '0';
            for chan in 0 to C_CHANNELS-1 loop
              v_ovflo_resolved := v_ovflo_resolved or m_axis_data_tuser_int(chan + get_start_of_data_channel_ovflo(C_NFFT_MAX     => C_NFFT_MAX,
                                                                                                                   C_HAS_XK_INDEX => C_HAS_XK_INDEX));
            end loop;
            event_fft_overflow <= v_ovflo_resolved;
          else
            event_fft_overflow <= '0';
          end if;
        end if;
      end if;
    end process p_event_fft_overflow;
  end generate gen_event_fft_overflow;
  p_event_data_in_channel_halt : process (aclk)
  begin
    if rising_edge(aclk) then
      if event_reset = '1' then
        event_data_in_channel_halt_int  <= '0';
      elsif aclken = '1' then
        event_data_in_channel_halt_int  <= data_in_channel_halt ;
      end if;
    end if;
  end process p_event_data_in_channel_halt;
  event_data_in_channel_halt  <= event_data_in_channel_halt_int;
  gen_nrt_events : if C_THROTTLE_SCHEME = 1 generate
    p_events : process (aclk)
    begin
      if rising_edge(aclk) then
        if event_reset = '1' then
          event_data_out_channel_halt_int <= '0';
          event_status_channel_halt_int   <= '0';
        elsif aclken = '1' then
          event_data_out_channel_halt_int <= data_out_channel_halt;
          event_status_channel_halt_int   <= status_channel_halt  ;
        end if;
      end if;
    end process p_events;
    event_status_channel_halt   <= event_status_channel_halt_int;
    event_data_out_channel_halt <= event_data_out_channel_halt_int;
  end generate gen_nrt_events;
  p_check_event_star_halt : process (aclk)
    variable v_prev_event_reset           : std_logic := '0';
    variable v_prev_data_in_channel_halt  : std_logic := '0';
    variable v_prev_data_out_channel_halt : std_logic := '0';
    variable v_prev_status_channel_halt   : std_logic := '0';
  begin
    if rising_edge(aclk) then
      if v_prev_event_reset = '1' then
        if event_data_in_channel_halt_int  /= '0' then
          report "xfft_v8.0::axi_wrapper.vhd: event_data_in_channel_halt wasn't cleared on reset" severity failure;
        end if;
        if C_THROTTLE_SCHEME = 1  then
          if event_data_out_channel_halt_int /= '0' then
            report "xfft_v8.0::axi_wrapper.vhd: event_data_out_channel_halt wasn't cleared on reset" severity failure;
          end if;
          if event_status_channel_halt_int   /= '0' then
            report "xfft_v8.0::axi_wrapper.vhd: event_status_channel_halt wasn't cleared on reset" severity failure;
          end if;
        end if;
      else
        if event_data_in_channel_halt_int  /= v_prev_data_in_channel_halt then
          report "xfft_v8.0::axi_wrapper.vhd: event_data_in_channel_halt isn't as expected" severity failure;
        end if;
        if C_THROTTLE_SCHEME = 1  then
          if event_data_out_channel_halt_int  /= v_prev_data_out_channel_halt then
            report "xfft_v8.0::axi_wrapper.vhd: event_data_out_channel_halt isn't as expected" severity failure;
          end if;
          if event_status_channel_halt_int  /= v_prev_status_channel_halt then
            report "xfft_v8.0::axi_wrapper.vhd: event_status_channel_halt isn't as expected" severity failure;
          end if;
        end if;
      end if;
      v_prev_event_reset := event_reset;
      if aclken = '1' then
        v_prev_data_in_channel_halt   := rfd and (not new_data) and (not flushing);
        if C_THROTTLE_SCHEME = 1  then
          v_prev_data_out_channel_halt  := dv and  data_out_fifo_full;
          v_prev_status_channel_halt    := status_fifo_write_desired and status_fifo_full;
        end if;
      end if;
    end if;
  end process p_check_event_star_halt;
  gen_rt_status_tready : if C_THROTTLE_SCHEME = 0 generate
    m_axis_status_tready_int <= '1';
  end generate gen_rt_status_tready;
  gen_nrt_status_tready : if C_THROTTLE_SCHEME = 1 generate
    m_axis_status_tready_int <= m_axis_status_tready;
  end generate gen_nrt_status_tready;
  gen_status_channel : if has_ovflo  (C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT) /= 0 or
                          has_blk_exp(                            C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP                              ) /= 0 generate
    signal status_fifo_write_data  : std_logic_vector(C_M_AXIS_STATUS_FIFO_WIDTH_NO_PADDING -1 downto 0);
    signal status_fifo_read_data   : std_logic_vector(C_M_AXIS_STATUS_FIFO_WIDTH_NO_PADDING -1 downto 0);
  begin
    status_fifo: entity work.timing_model_axi_wrapper_output_fifo
      generic map (
        C_FIFO_WIDTH      => C_M_AXIS_STATUS_FIFO_WIDTH_NO_PADDING,
        C_THROTTLE_SCHEME => C_THROTTLE_SCHEME
        )
      port map (
        aclk        => aclk,
        aresetn    => aresetn,
        fifo_reset => fifo_reset,
        aclken     => aclken,
        data_in    => status_fifo_write_data,
        fifo_write => status_fifo_write_decision,
        full       => status_fifo_full,
        almost_full=> status_fifo_almost_full,
        tvalid     => m_axis_status_tvalid,
        tready     => m_axis_status_tready_int,
        data_out   => status_fifo_read_data
        );
    status_fifo_write_decision <= '1' when status_fifo_write_desired = '1' and status_fifo_full = '0' and ce_int = '1' else '0';
    gen_status_ovflo : if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT) /= 0 generate
      type   we_state_t is (ST_WAIT_FOR_FRAME_END, ST_WAIT_FOR_FIFO_WRITE);
      signal we_current_state: we_state_t := ST_WAIT_FOR_FRAME_END;
    begin
      p_gen_fifo_we: process (aclk)
      begin
        if rising_edge(aclk) then
          if reset = '1' then
            we_current_state <= ST_WAIT_FOR_FRAME_END;
            status_fifo_write_desired <= '0';
          else
            status_fifo_write_desired <= '0';
            case we_current_state is
              when ST_WAIT_FOR_FRAME_END =>
                if data_out_tlast = '1' then
                  we_current_state <= ST_WAIT_FOR_FIFO_WRITE;
                  status_fifo_write_desired <= '1';
                  status_fifo_write_data <= convert_fft_out_to_status_vector(blk_exp       => blk_exp_array,
                                                                 ovflo         => ovflo_array,
                                                                 C_CHANNELS    => C_CHANNELS,
                                                                 C_HAS_OVFLO   => C_HAS_OVFLO,
                                                                 C_HAS_SCALING => C_HAS_SCALING,
                                                                 C_HAS_BFP     => C_HAS_BFP,
                                                                 C_USE_FLT_PT  => C_USE_FLT_PT);
                end if;
              when ST_WAIT_FOR_FIFO_WRITE =>
                status_fifo_write_desired <= '1';
                if status_fifo_write_decision = '1' then
                  status_fifo_write_desired <= '0';
                  we_current_state <=ST_WAIT_FOR_FRAME_END;
                end if;
              when others =>
            end case;
          end if;
        end if;
      end process p_gen_fifo_we;
    end generate gen_status_ovflo;
    gen_status_blk_exp : if has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) /= 0 generate
      type   we_state_t is (ST_WAIT_FOR_FRAME_START, ST_WAIT_FOR_FIFO_WRITE, ST_WAIT_FOR_FRAME_END);
      signal we_current_state: we_state_t := ST_WAIT_FOR_FRAME_START;
      begin
      p_gen_fifo_we: process (aclk)
      begin
        if rising_edge(aclk) then
          if reset = '1' then
            we_current_state <= ST_WAIT_FOR_FRAME_START;
            status_fifo_write_desired <= '0';
          else
            status_fifo_write_desired <= '0';
            case we_current_state is
              when ST_WAIT_FOR_FRAME_START =>
                if dv = '1' or cpv = '1' then
                  we_current_state <= ST_WAIT_FOR_FIFO_WRITE;
                  status_fifo_write_desired <= '0';
                end if;
              when ST_WAIT_FOR_FIFO_WRITE =>
                status_fifo_write_desired <= '1';
                if status_fifo_write_decision = '1'then
                  status_fifo_write_desired <= '0';
                  we_current_state <=ST_WAIT_FOR_FRAME_END;
                end if;
              when ST_WAIT_FOR_FRAME_END =>
                if data_out_tlast = '1' and ce_int = '1' then
                  we_current_state <=ST_WAIT_FOR_FRAME_START;
                end if;
              when others =>
            end case;
          end if;
        end if;
      end process p_gen_fifo_we;
      p_pack_status_fifo_data : process (blk_exp_array, ovflo_array)
      begin
        status_fifo_write_data <= convert_fft_out_to_status_vector(blk_exp       => blk_exp_array,
                                                                   ovflo         => ovflo_array,
                                                                   C_CHANNELS    => C_CHANNELS,
                                                                   C_HAS_OVFLO   => C_HAS_OVFLO,
                                                                   C_HAS_SCALING => C_HAS_SCALING,
                                                                   C_HAS_BFP     => C_HAS_BFP,
                                                                   C_USE_FLT_PT  => C_USE_FLT_PT);
      end process p_pack_status_fifo_data;
    end generate gen_status_blk_exp;
    p_unpack_status_fifo_data : process(status_fifo_read_data)
    begin
      convert_status_vector_to_axi ( fifo_vector    => status_fifo_read_data,
                                     tdata          => m_axis_status_tdata,
                                     C_CHANNELS     => C_CHANNELS,
                                     C_HAS_OVFLO    => C_HAS_OVFLO,
                                     C_HAS_SCALING  => C_HAS_SCALING,
                                     C_HAS_BFP      => C_HAS_BFP,
                                     C_USE_FLT_PT   => C_USE_FLT_PT);
    end process p_unpack_status_fifo_data;
  end generate gen_status_channel;
  gen_no_status : if has_ovflo(C_HAS_OVFLO => C_HAS_OVFLO, C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP, C_USE_FLT_PT => C_USE_FLT_PT) = 0
                 and has_blk_exp(C_HAS_SCALING => C_HAS_SCALING, C_HAS_BFP => C_HAS_BFP) = 0  generate
    status_fifo_write_desired <= '0';
  end generate gen_no_status;
  gen_rt_data_out_tready : if C_THROTTLE_SCHEME = 0 generate
    m_axis_data_tready_int <= '1';
  end generate gen_rt_data_out_tready;
  gen_nrt_data_out_tready : if C_THROTTLE_SCHEME = 1 generate
    m_axis_data_tready_int <= m_axis_data_tready;
  end generate gen_nrt_data_out_tready;
  data_out_channel: entity work.timing_model_axi_wrapper_output_fifo
    generic map (
      C_FIFO_WIDTH      => C_M_AXIS_DATA_FIFO_WIDTH_NO_PADDING,
      C_THROTTLE_SCHEME => C_THROTTLE_SCHEME
      )
    port map (
      aclk        => aclk,
      aresetn    => aresetn,
      fifo_reset => fifo_reset,
      aclken     => aclken,
      data_in    => data_out_fifo_write_data,
      fifo_write => data_out_fifo_write,
      full       => data_out_fifo_full,
      almost_full=> data_out_fifo_almost_full,
      tvalid     => m_axis_data_tvalid_int,
      tready     => m_axis_data_tready_int,
      data_out   => data_out_fifo_read_data
      );
  data_out_fifo_write <= '1' when dv = '1' and ce_int = '1' and reset = '0' else '0';
  p_pack_data_out_fifo_data : process (data_out_tlast, xk_re_array, xk_im_array, xk_index, blk_exp_array, ovflo_array )
  begin
    data_out_fifo_write_data <= convert_fft_out_to_data_out_vector(
      tlast          => data_out_tlast,
      xk_re          => xk_re_array,
      xk_im          => xk_im_array,
      xk_index       => xk_index,
      blk_exp        => blk_exp_array,
      ovflo          => ovflo_array,
      C_OUTPUT_WIDTH => C_OUTPUT_WIDTH,
      C_CHANNELS     => C_CHANNELS,
      C_NFFT_MAX     => C_NFFT_MAX,
      C_HAS_OVFLO    => C_HAS_OVFLO,
      C_HAS_SCALING  => C_HAS_SCALING,
      C_HAS_BFP      => C_HAS_BFP,
      C_HAS_XK_INDEX => C_HAS_XK_INDEX,
      C_USE_FLT_PT   => C_USE_FLT_PT);
  end process p_pack_data_out_fifo_data;
  p_unpack_data_out_fifo_data : process(data_out_fifo_read_data)
  begin
    convert_data_out_vector_to_axi (fifo_vector    => data_out_fifo_read_data,
                                    tlast          => m_axis_data_tlast,
                                    tdata          => m_axis_data_tdata,
                                    tuser          => m_axis_data_tuser_int,
                                    C_OUTPUT_WIDTH => C_OUTPUT_WIDTH,
                                    C_CHANNELS     => C_CHANNELS,
                                    C_NFFT_MAX     => C_NFFT_MAX,
                                    C_HAS_OVFLO    => C_HAS_OVFLO,
                                    C_HAS_SCALING  => C_HAS_SCALING,
                                    C_HAS_BFP      => C_HAS_BFP,
                                    C_HAS_XK_INDEX => C_HAS_XK_INDEX,
                                    C_USE_FLT_PT   => C_USE_FLT_PT);
  end process p_unpack_data_out_fifo_data;
end architecture synth;
