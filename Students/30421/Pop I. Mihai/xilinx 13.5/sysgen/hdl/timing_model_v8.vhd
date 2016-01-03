
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
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;
library xilinxcorelib;
use xilinxcorelib.bip_utils_pkg_v2_0.all;
library work;
use work.timing_model_pkg.all;
use work.timing_model_xfft_v8_0_axi_pkg.all;
entity timing_model is
  generic (
      COMPONENT_NAME              : string  := "fftv8_timing_model";
      C_XDEVICEFAMILY             : string  := "no_family";
      C_S_AXIS_CONFIG_TDATA_WIDTH : integer := 32;
      C_S_AXIS_DATA_TDATA_WIDTH   : integer := 32;
      C_M_AXIS_DATA_TDATA_WIDTH   : integer := 32;
      C_M_AXIS_DATA_TUSER_WIDTH   : integer := 32;
      C_M_AXIS_STATUS_TDATA_WIDTH : integer := 32;
      C_THROTTLE_SCHEME           : integer := 0;
      C_CHANNELS                  : integer := 1;
      C_NFFT_MAX                  : integer := 10;
      C_ARCH                      : integer := 1;
      C_HAS_NFFT                  : integer := 0;
      C_USE_FLT_PT                : integer := 0;
      C_INPUT_WIDTH               : integer := 16;
      C_TWIDDLE_WIDTH             : integer := 16;
      C_OUTPUT_WIDTH              : integer := 16;
      C_HAS_SCALING               : integer := 1;
      C_HAS_BFP                   : integer := 0;
      C_HAS_ROUNDING              : integer := 0;
      C_HAS_ACLKEN                : integer := 0;
      C_HAS_ARESETN               : integer := 1;
      C_HAS_OVFLO                 : integer := 0;
      C_HAS_NATURAL_OUTPUT        : integer := 0;
      C_HAS_CYCLIC_PREFIX         : integer := 0;
      C_HAS_XK_INDEX              : integer := 1;
      C_DATA_MEM_TYPE             : integer := 1;
      C_TWIDDLE_MEM_TYPE          : integer := 1;
      C_BRAM_STAGES               : integer := 0;
      C_REORDER_MEM_TYPE          : integer := 1;
      C_USE_HYBRID_RAM            : integer := 0;
      C_OPTIMIZE_GOAL             : integer := 0;
      C_CMPY_TYPE                 : integer := 0;
      C_BFLY_TYPE                 : integer := 0
    );
  port (
      aclk       : in std_logic                               := '1';
      aclken     : in std_logic                               := '1';
      aresetn    : in std_logic                               := '1';
      s_axis_config_tdata  : in std_logic_vector (C_S_AXIS_CONFIG_TDATA_WIDTH-1 downto 0)              ;
      s_axis_config_tvalid : in std_logic                                                              ;
      s_axis_config_tready : out std_logic                                                             ;
      s_axis_data_tdata  : in std_logic_vector (C_S_AXIS_DATA_TDATA_WIDTH-1 downto 0)                  ;
      s_axis_data_tvalid : in std_logic                                                                ;
      s_axis_data_tlast  : in std_logic                                                                ;
      s_axis_data_tready : out std_logic                                                               ;
      m_axis_data_tuser  : out std_logic_vector (C_M_AXIS_DATA_TUSER_WIDTH-1 downto 0);
      m_axis_data_tvalid : out std_logic                                              ;
      m_axis_data_tlast  : out std_logic                                              ;
      m_axis_data_tready : in  std_logic                                              ;
      m_axis_status_tvalid : out std_logic;
      m_axis_status_tready : in  std_logic := '1';
      event_frame_started        : out std_logic;
      event_tlast_unexpected     : out std_logic;
      event_tlast_missing        : out std_logic;
      event_status_channel_halt  : out std_logic;
      event_data_in_channel_halt : out std_logic;
      event_data_out_channel_halt: out std_logic;
      XN_INDEX : out std_logic_vector(C_NFFT_MAX-1 downto 0);
      XK_INDEX : out std_logic_vector(C_NFFT_MAX-1 downto 0);
      FWD_INV_USED   : out std_logic;
      FWD_INV0_USED  : out std_logic;
      FWD_INV1_USED  : out std_logic;
      FWD_INV2_USED  : out std_logic;
      FWD_INV3_USED  : out std_logic;
      FWD_INV4_USED  : out std_logic;
      FWD_INV5_USED  : out std_logic;
      FWD_INV6_USED  : out std_logic;
      FWD_INV7_USED  : out std_logic;
      FWD_INV8_USED  : out std_logic;
      FWD_INV9_USED  : out std_logic;
      FWD_INV10_USED : out std_logic;
      FWD_INV11_USED : out std_logic;

      SCALE_SCH_USED   : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH0_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH1_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH2_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH3_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH4_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH5_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH6_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH7_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH8_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH9_USED  : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH10_USED : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH11_USED : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0)
      );
end timing_model;
architecture behavioral of timing_model is

  function is_streaming  ( constant C_ARCH : integer
                         ) return integer is
  begin
    if C_ARCH = 3 then
      return 1;
    else
      return 0;
    end if;
  end is_streaming;
  function get_ce_generic (constant C_THROTTLE_SCHEME : integer;
                           constant C_HAS_ACLKEN      : integer)
    return integer is
  begin
    if C_THROTTLE_SCHEME = 0 then
      return C_HAS_ACLKEN;
    else
      return 1;
    end if;
  end get_ce_generic;
  function get_sclr_generic (constant C_ARCH               : integer;
                             constant C_HAS_NATURAL_OUTPUT : integer;
                             constant C_HAS_ARESETN        : integer
                             )
    return integer is
  begin
    if is_streaming(C_ARCH) = 0 and C_HAS_NATURAL_OUTPUT = 0 then
      return 1;
    else
      return C_HAS_ARESETN;
    end if;
  end get_sclr_generic;

  constant R_XDEVICEFAMILY      : string  := C_XDEVICEFAMILY;
  constant R_CHANNELS           : integer := C_CHANNELS;
  constant R_NFFT_MAX           : integer := C_NFFT_MAX;
  constant R_ARCH               : integer := C_ARCH;
  constant R_HAS_NFFT           : integer := C_HAS_NFFT;
  constant R_INPUT_WIDTH        : integer := C_INPUT_WIDTH;
  constant R_TWIDDLE_WIDTH      : integer := C_TWIDDLE_WIDTH;
  constant R_OUTPUT_WIDTH       : integer := C_OUTPUT_WIDTH;
  constant R_HAS_SCALING        : integer := C_HAS_SCALING;
  constant R_HAS_BFP            : integer := C_HAS_BFP;
  constant R_HAS_ROUNDING       : integer := C_HAS_ROUNDING;
  constant R_HAS_ACLKEN         : integer := C_HAS_ACLKEN;
  constant R_HAS_ARESETN        : integer := C_HAS_ARESETN;
  constant R_HAS_OVFLO          : integer := C_HAS_OVFLO;
  constant R_HAS_NATURAL_OUTPUT : integer := C_HAS_NATURAL_OUTPUT;
  constant R_HAS_CYCLIC_PREFIX  : integer := C_HAS_CYCLIC_PREFIX;
  constant R_DATA_MEM_TYPE      : integer := C_DATA_MEM_TYPE;
  constant R_TWIDDLE_MEM_TYPE   : integer := C_TWIDDLE_MEM_TYPE;
  constant R_BRAM_STAGES        : integer := C_BRAM_STAGES;
  constant R_REORDER_MEM_TYPE   : integer := C_REORDER_MEM_TYPE;
  constant R_USE_HYBRID_RAM     : integer := C_USE_HYBRID_RAM;
  constant R_OPTIMIZE_GOAL      : integer := 1;
  constant R_CMPY_TYPE          : integer := C_CMPY_TYPE;
  constant R_BFLY_TYPE          : integer := C_BFLY_TYPE;
  constant R_XN_DATA_OFFSET     : integer := 0;
  signal ce_w2c             : std_logic;
  signal sclr_w2c           : std_logic;
  signal NFFT_w2c           : std_logic_vector(4 downto 0);
  signal NFFT_WE_w2c        : std_logic;
  signal START_w2c          : std_logic;
  signal UNLOAD_w2c         : std_logic;
  signal CP_LEN_w2c         : std_logic_vector(C_NFFT_MAX-1 downto 0);
  signal CP_LEN_WE_w2c      : std_logic;
  signal XN_RE_w2c          : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN_IM_w2c          : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal FWD_INV_w2c        : std_logic;
  signal FWD_INV_WE_w2c     : std_logic;
  signal SCALE_SCH_w2c      : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH_WE_w2c   : std_logic;
  signal XN0_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN0_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN1_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN1_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN2_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN2_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN3_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN3_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN4_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN4_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN5_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN5_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN6_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN6_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN7_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN7_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN8_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN8_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN9_RE_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN9_IM_w2c         : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN10_RE_w2c        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN10_IM_w2c        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN11_RE_w2c        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal XN11_IM_w2c        : std_logic_vector(C_INPUT_WIDTH-1 downto 0);
  signal FWD_INV0_w2c       : std_logic;
  signal FWD_INV0_WE_w2c    : std_logic;
  signal FWD_INV1_w2c       : std_logic;
  signal FWD_INV1_WE_w2c    : std_logic;
  signal FWD_INV2_w2c       : std_logic;
  signal FWD_INV2_WE_w2c    : std_logic;
  signal FWD_INV3_w2c       : std_logic;
  signal FWD_INV3_WE_w2c    : std_logic;
  signal FWD_INV4_w2c       : std_logic;
  signal FWD_INV4_WE_w2c    : std_logic;
  signal FWD_INV5_w2c       : std_logic;
  signal FWD_INV5_WE_w2c    : std_logic;
  signal FWD_INV6_w2c       : std_logic;
  signal FWD_INV6_WE_w2c    : std_logic;
  signal FWD_INV7_w2c       : std_logic;
  signal FWD_INV7_WE_w2c    : std_logic;
  signal FWD_INV8_w2c       : std_logic;
  signal FWD_INV8_WE_w2c    : std_logic;
  signal FWD_INV9_w2c       : std_logic;
  signal FWD_INV9_WE_w2c    : std_logic;
  signal FWD_INV10_w2c      : std_logic;
  signal FWD_INV10_WE_w2c   : std_logic;
  signal FWD_INV11_w2c      : std_logic;
  signal FWD_INV11_WE_w2c   : std_logic;
  signal SCALE_SCH0_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH0_WE_w2c  : std_logic;
  signal SCALE_SCH1_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH1_WE_w2c  : std_logic;
  signal SCALE_SCH2_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH2_WE_w2c  : std_logic;
  signal SCALE_SCH3_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH3_WE_w2c  : std_logic;
  signal SCALE_SCH4_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH4_WE_w2c  : std_logic;
  signal SCALE_SCH5_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH5_WE_w2c  : std_logic;
  signal SCALE_SCH6_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH6_WE_w2c  : std_logic;
  signal SCALE_SCH7_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH7_WE_w2c  : std_logic;
  signal SCALE_SCH8_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH8_WE_w2c  : std_logic;
  signal SCALE_SCH9_w2c     : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH9_WE_w2c  : std_logic;
  signal SCALE_SCH10_w2c    : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH10_WE_w2c : std_logic;
  signal SCALE_SCH11_w2c    : std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
  signal SCALE_SCH11_WE_w2c : std_logic;
  signal RFD_c2w       : std_logic;
  signal XN_INDEX_c2w  : std_logic_vector(C_NFFT_MAX-1 downto 0);
  signal BUSY_c2w      : std_logic;
  signal EDONE_c2w     : std_logic;
  signal DONE_c2w      : std_logic;
  signal DV_c2w        : std_logic;
  signal XK_INDEX_c2w  : std_logic_vector(C_NFFT_MAX-1 downto 0);
  signal CPV_c2w       : std_logic;
  signal RFS_c2w       : std_logic;
  signal XK_RE_c2w     : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK_IM_c2w     : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal BLK_EXP_c2w   : std_logic_vector(4 downto 0);
  signal OVFLO_c2w     : std_logic;
  signal XK0_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK0_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK1_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK1_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK2_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK2_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK3_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK3_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK4_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK4_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK5_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK5_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK6_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK6_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK7_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK7_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK8_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK8_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK9_RE_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK9_IM_c2w    : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK10_RE_c2w   : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK10_IM_c2w   : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK11_RE_c2w   : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal XK11_IM_c2w   : std_logic_vector(C_OUTPUT_WIDTH-1 downto 0);
  signal BLK_EXP0_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP1_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP2_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP3_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP4_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP5_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP6_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP7_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP8_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP9_c2w  : std_logic_vector(4 downto 0);
  signal BLK_EXP10_c2w : std_logic_vector(4 downto 0);
  signal BLK_EXP11_c2w : std_logic_vector(4 downto 0);
  signal OVFLO0_c2w    : std_logic;
  signal OVFLO1_c2w    : std_logic;
  signal OVFLO2_c2w    : std_logic;
  signal OVFLO3_c2w    : std_logic;
  signal OVFLO4_c2w    : std_logic;
  signal OVFLO5_c2w    : std_logic;
  signal OVFLO6_c2w    : std_logic;
  signal OVFLO7_c2w    : std_logic;
  signal OVFLO8_c2w    : std_logic;
  signal OVFLO9_c2w    : std_logic;
  signal OVFLO10_c2w   : std_logic;
  signal OVFLO11_c2w   : std_logic;
  signal aclken_int    : std_logic;
  signal aresetn_int   : std_logic;
  signal xk_re_array   : T_XK_RE_CHANNEL_ARRAY;
  signal xk_im_array   : T_XK_RE_CHANNEL_ARRAY;
  signal blk_exp_array : T_BLK_EXP_CHANNEL_ARRAY;
  signal ovflo_array   : T_OVFLO_CHANNEL_ARRAY;


begin
  has_aresetn : if C_HAS_ARESETN = 1 generate
    aresetn_int <= aresetn;
  end generate has_aresetn;
  no_aresetn : if C_HAS_ARESETN = 0 generate
   aresetn_int <= '1';
  end generate no_aresetn;
  has_aclken : if C_HAS_ACLKEN = 1 generate
    aclken_int <= aclken;
  end generate has_aclken;

  no_aclken : if C_HAS_ACLKEN = 0 generate
   aclken_int <= '1';
  end generate no_aclken;
  p_convert_fft_out_to_arrays : process (blk_exp_c2w, blk_exp0_c2w, blk_exp1_c2w, blk_exp10_c2w, blk_exp11_c2w, blk_exp2_c2w, blk_exp3_c2w, blk_exp4_c2w,
                                         blk_exp5_c2w, blk_exp6_c2w,blk_exp7_c2w, blk_exp8_c2w, blk_exp9_c2w,
                                         ovflo_c2w, ovflo0_c2w, ovflo1_c2w, ovflo10_c2w, ovflo11_c2w, ovflo2_c2w, ovflo3_c2w, ovflo4_c2w,
                                         ovflo5_c2w, ovflo6_c2w, ovflo7_c2w, ovflo8_c2w, ovflo9_c2w,
                                         xk0_im_c2w, xk0_re_c2w, xk10_im_c2w, xk10_re_c2w, xk11_im_c2w, xk11_re_c2w, xk1_im_c2w, xk1_re_c2w, xk2_im_c2w,
                                         xk2_re_c2w, xk3_im_c2w, xk3_re_c2w,
                                         xk4_im_c2w, xk4_re_c2w, xk5_im_c2w, xk5_re_c2w, xk6_im_c2w, xk6_re_c2w, xk7_im_c2w, xk7_re_c2w, xk8_im_c2w,
                                         xk8_re_c2w, xk9_im_c2w, xk9_re_c2w, xk_im_c2w, xk_re_c2w)
  begin
    if C_CHANNELS = 1 then
      xk_re_array (0)(C_OUTPUT_WIDTH-1 downto 0) <= xk_re_c2w;
      xk_im_array (0)(C_OUTPUT_WIDTH-1 downto 0) <= xk_im_c2w;
      blk_exp_array(0)                           <= blk_exp_c2w;
      ovflo_array (0)                            <= ovflo_c2w;
    else
      xk_re_array (0)(C_OUTPUT_WIDTH-1 downto 0) <= xk0_re_c2w;
      xk_im_array (0)(C_OUTPUT_WIDTH-1 downto 0) <= xk0_im_c2w;
      blk_exp_array(0)                           <= blk_exp0_c2w;
      ovflo_array (0)                            <= ovflo0_c2w;
      if(C_CHANNELS > 1) then
        xk_re_array (1)(C_OUTPUT_WIDTH-1 downto 0) <= xk1_re_c2w;
        xk_im_array (1)(C_OUTPUT_WIDTH-1 downto 0) <= xk1_im_c2w;
        blk_exp_array(1)                           <= blk_exp1_c2w;
        ovflo_array (1)                            <= ovflo1_c2w;
      end if;
      if(C_CHANNELS > 2) then
        xk_re_array (2)(C_OUTPUT_WIDTH-1 downto 0) <= xk2_re_c2w;
        xk_im_array (2)(C_OUTPUT_WIDTH-1 downto 0) <= xk2_im_c2w;
        blk_exp_array(2)                           <= blk_exp2_c2w;
        ovflo_array (2)                            <= ovflo2_c2w;
      end if;
      if(C_CHANNELS > 3) then
        xk_re_array (3)(C_OUTPUT_WIDTH-1 downto 0) <= xk3_re_c2w;
        xk_im_array (3)(C_OUTPUT_WIDTH-1 downto 0) <= xk3_im_c2w;
        blk_exp_array(3)                           <= blk_exp3_c2w;
        ovflo_array (3)                            <= ovflo3_c2w;
      end if;
      if(C_CHANNELS > 4) then
        xk_re_array (4)(C_OUTPUT_WIDTH-1 downto 0) <= xk4_re_c2w;
        xk_im_array (4)(C_OUTPUT_WIDTH-1 downto 0) <= xk4_im_c2w;
        blk_exp_array(4)                           <= blk_exp4_c2w;
        ovflo_array (4)                            <= ovflo4_c2w;
      end if;
      if(C_CHANNELS > 5) then
        xk_re_array (5)(C_OUTPUT_WIDTH-1 downto 0) <= xk5_re_c2w;
        xk_im_array (5)(C_OUTPUT_WIDTH-1 downto 0) <= xk5_im_c2w;
        blk_exp_array(5)                           <= blk_exp5_c2w;
        ovflo_array (5)                            <= ovflo5_c2w;
      end if;
      if(C_CHANNELS > 6) then
        xk_re_array (6)(C_OUTPUT_WIDTH-1 downto 0) <= xk6_re_c2w;
        xk_im_array (6)(C_OUTPUT_WIDTH-1 downto 0) <= xk6_im_c2w;
        blk_exp_array(6)                           <= blk_exp6_c2w;
        ovflo_array (6)                            <= ovflo6_c2w;
      end if;
      if(C_CHANNELS > 7) then
        xk_re_array (7)(C_OUTPUT_WIDTH-1 downto 0) <= xk7_re_c2w;
        xk_im_array (7)(C_OUTPUT_WIDTH-1 downto 0) <= xk7_im_c2w;
        blk_exp_array(7)                           <= blk_exp7_c2w;
        ovflo_array (7)                            <= ovflo7_c2w;
      end if;
      if(C_CHANNELS > 8) then
        xk_re_array (8)(C_OUTPUT_WIDTH-1 downto 0) <= xk8_re_c2w;
        xk_im_array (8)(C_OUTPUT_WIDTH-1 downto 0) <= xk8_im_c2w;
        blk_exp_array(8)                           <= blk_exp8_c2w;
        ovflo_array (8)                            <= ovflo8_c2w;
      end if;
      if(C_CHANNELS > 9) then
        xk_re_array (9)(C_OUTPUT_WIDTH-1 downto 0) <= xk9_re_c2w;
        xk_im_array (9)(C_OUTPUT_WIDTH-1 downto 0) <= xk9_im_c2w;
        blk_exp_array(9)                           <= blk_exp9_c2w;
        ovflo_array (9)                            <= ovflo9_c2w;
      end if;
      if(C_CHANNELS > 10) then
        xk_re_array (10)(C_OUTPUT_WIDTH-1 downto 0) <= xk10_re_c2w;
        xk_im_array (10)(C_OUTPUT_WIDTH-1 downto 0) <= xk10_im_c2w;
        blk_exp_array(10)                           <= blk_exp10_c2w;
        ovflo_array (10)                            <= ovflo10_c2w;
      end if;
      if(C_CHANNELS > 11) then
        xk_re_array (11)(C_OUTPUT_WIDTH-1 downto 0) <= xk11_re_c2w;
        xk_im_array (11)(C_OUTPUT_WIDTH-1 downto 0) <= xk11_im_c2w;
        blk_exp_array(11)                           <= blk_exp11_c2w;
        ovflo_array (11)                            <= ovflo11_c2w;
      end if;
    end if;
  end process p_convert_fft_out_to_arrays;
  axi_wrapper : entity work.timing_model_axi_wrapper
    generic map (
      C_CHANNELS                  => R_CHANNELS,
      C_NFFT_MAX                  => R_NFFT_MAX,
      C_ARCH                      => R_ARCH,
      C_HAS_NFFT                  => R_HAS_NFFT,
      C_USE_FLT_PT                => C_USE_FLT_PT,
      C_INPUT_WIDTH               => R_INPUT_WIDTH,
      C_OUTPUT_WIDTH              => R_OUTPUT_WIDTH,
      C_HAS_SCALING               => R_HAS_SCALING,
      C_HAS_BFP                   => R_HAS_BFP,
      C_HAS_ROUNDING              => R_HAS_ROUNDING,
      C_HAS_ACLKEN                => R_HAS_ACLKEN,
      C_HAS_ARESETN               => R_HAS_ARESETN,
      C_HAS_OVFLO                 => R_HAS_OVFLO,
      C_HAS_NATURAL_OUTPUT        => R_HAS_NATURAL_OUTPUT,
      C_HAS_CYCLIC_PREFIX         => R_HAS_CYCLIC_PREFIX,
      C_HAS_XK_INDEX              => C_HAS_XK_INDEX,
      C_THROTTLE_SCHEME           => C_THROTTLE_SCHEME,
      C_S_AXIS_CONFIG_TDATA_WIDTH => C_S_AXIS_CONFIG_TDATA_WIDTH,
      C_S_AXIS_DATA_TDATA_WIDTH   => C_S_AXIS_DATA_TDATA_WIDTH,
      C_M_AXIS_DATA_TDATA_WIDTH   => C_M_AXIS_DATA_TDATA_WIDTH,
      C_M_AXIS_DATA_TUSER_WIDTH   => C_M_AXIS_DATA_TUSER_WIDTH,
      C_M_AXIS_STATUS_TDATA_WIDTH => C_M_AXIS_STATUS_TDATA_WIDTH
      )
    port map (
      aclk                       => aclk,
      aresetn                    => aresetn_int,
      aclken                     => aclken_int,
      s_axis_config_tdata        => s_axis_config_tdata,
      s_axis_config_tvalid       => s_axis_config_tvalid,
      s_axis_config_tready       => s_axis_config_tready,

      s_axis_data_tdata          => s_axis_data_tdata,
      s_axis_data_tvalid         => s_axis_data_tvalid,
      s_axis_data_tready         => s_axis_data_tready,
      s_axis_data_tlast          => s_axis_data_tlast,

      m_axis_data_tdata          => open,
      m_axis_data_tuser          => m_axis_data_tuser,
      m_axis_data_tvalid         => m_axis_data_tvalid,
      m_axis_data_tlast          => m_axis_data_tlast,
      m_axis_data_tready         => m_axis_data_tready,

      m_axis_status_tvalid       => m_axis_status_tvalid,
      m_axis_status_tready       => m_axis_status_tready,
      m_axis_status_tdata        => open,
      event_frame_started        => event_frame_started,
      event_tlast_unexpected     => event_tlast_unexpected,
      event_tlast_missing        => event_tlast_missing,
      event_fft_overflow         => open,
      event_status_channel_halt  => event_status_channel_halt,
      event_data_in_channel_halt => event_data_in_channel_halt,
      event_data_out_channel_halt=> event_data_out_channel_halt,

      XN_INDEX  => XN_INDEX_c2w,
      XK_INDEX  => XK_INDEX_c2w,

      NFFT           => NFFT_w2c,
      NFFT_WE        => NFFT_WE_w2c,
      START          => START_w2c,
      UNLOAD         => UNLOAD_w2c,
      CP_LEN         => CP_LEN_w2c,
      CP_LEN_WE      => CP_LEN_WE_w2c,
      XN_RE          => XN_RE_w2c,
      XN_IM          => XN_IM_w2c,
      FWD_INV        => FWD_INV_w2c,
      FWD_INV_WE     => FWD_INV_WE_w2c,
      SCALE_SCH      => SCALE_SCH_w2c,
      SCALE_SCH_WE   => SCALE_SCH_WE_w2c,
      XN0_RE         => XN0_RE_w2c,
      XN0_IM         => XN0_IM_w2c,
      XN1_RE         => XN1_RE_w2c,
      XN1_IM         => XN1_IM_w2c,
      XN2_RE         => XN2_RE_w2c,
      XN2_IM         => XN2_IM_w2c,
      XN3_RE         => XN3_RE_w2c,
      XN3_IM         => XN3_IM_w2c,
      XN4_RE         => XN4_RE_w2c,
      XN4_IM         => XN4_IM_w2c,
      XN5_RE         => XN5_RE_w2c,
      XN5_IM         => XN5_IM_w2c,
      XN6_RE         => XN6_RE_w2c,
      XN6_IM         => XN6_IM_w2c,
      XN7_RE         => XN7_RE_w2c,
      XN7_IM         => XN7_IM_w2c,
      XN8_RE         => XN8_RE_w2c,
      XN8_IM         => XN8_IM_w2c,
      XN9_RE         => XN9_RE_w2c,
      XN9_IM         => XN9_IM_w2c,
      XN10_RE        => XN10_RE_w2c,
      XN10_IM        => XN10_IM_w2c,
      XN11_RE        => XN11_RE_w2c,
      XN11_IM        => XN11_IM_w2c,
      FWD_INV0       => FWD_INV0_w2c,
      FWD_INV0_WE    => FWD_INV0_WE_w2c,
      FWD_INV1       => FWD_INV1_w2c,
      FWD_INV1_WE    => FWD_INV1_WE_w2c,
      FWD_INV2       => FWD_INV2_w2c,
      FWD_INV2_WE    => FWD_INV2_WE_w2c,
      FWD_INV3       => FWD_INV3_w2c,
      FWD_INV3_WE    => FWD_INV3_WE_w2c,
      FWD_INV4       => FWD_INV4_w2c,
      FWD_INV4_WE    => FWD_INV4_WE_w2c,
      FWD_INV5       => FWD_INV5_w2c,
      FWD_INV5_WE    => FWD_INV5_WE_w2c,
      FWD_INV6       => FWD_INV6_w2c,
      FWD_INV6_WE    => FWD_INV6_WE_w2c,
      FWD_INV7       => FWD_INV7_w2c,
      FWD_INV7_WE    => FWD_INV7_WE_w2c,
      FWD_INV8       => FWD_INV8_w2c,
      FWD_INV8_WE    => FWD_INV8_WE_w2c,
      FWD_INV9       => FWD_INV9_w2c,
      FWD_INV9_WE    => FWD_INV9_WE_w2c,
      FWD_INV10      => FWD_INV10_w2c,
      FWD_INV10_WE   => FWD_INV10_WE_w2c,
      FWD_INV11      => FWD_INV11_w2c,
      FWD_INV11_WE   => FWD_INV11_WE_w2c,
      SCALE_SCH0     => SCALE_SCH0_w2c,
      SCALE_SCH0_WE  => SCALE_SCH0_WE_w2c,
      SCALE_SCH1     => SCALE_SCH1_w2c,
      SCALE_SCH1_WE  => SCALE_SCH1_WE_w2c,
      SCALE_SCH2     => SCALE_SCH2_w2c,
      SCALE_SCH2_WE  => SCALE_SCH2_WE_w2c,
      SCALE_SCH3     => SCALE_SCH3_w2c,
      SCALE_SCH3_WE  => SCALE_SCH3_WE_w2c,
      SCALE_SCH4     => SCALE_SCH4_w2c,
      SCALE_SCH4_WE  => SCALE_SCH4_WE_w2c,
      SCALE_SCH5     => SCALE_SCH5_w2c,
      SCALE_SCH5_WE  => SCALE_SCH5_WE_w2c,
      SCALE_SCH6     => SCALE_SCH6_w2c,
      SCALE_SCH6_WE  => SCALE_SCH6_WE_w2c,
      SCALE_SCH7     => SCALE_SCH7_w2c,
      SCALE_SCH7_WE  => SCALE_SCH7_WE_w2c,
      SCALE_SCH8     => SCALE_SCH8_w2c,
      SCALE_SCH8_WE  => SCALE_SCH8_WE_w2c,
      SCALE_SCH9     => SCALE_SCH9_w2c,
      SCALE_SCH9_WE  => SCALE_SCH9_WE_w2c,
      SCALE_SCH10    => SCALE_SCH10_w2c,
      SCALE_SCH10_WE => SCALE_SCH10_WE_w2c,
      SCALE_SCH11    => SCALE_SCH11_w2c,
      SCALE_SCH11_WE => SCALE_SCH11_WE_w2c,
      sclr           => sclr_w2c,
      ce             => ce_w2c,
      RFD       => RFD_c2w,
      BUSY      => BUSY_c2w,
      EDONE     => EDONE_c2w,
      DONE      => DONE_c2w,
      DV        => DV_c2w,
      CPV       => CPV_c2w,
      RFS       => RFS_c2w,
      XK_RE     => XK_RE_c2w,
      XK_IM     => XK_IM_c2w,
      BLK_EXP   => BLK_EXP_c2w,
      OVFLO     => OVFLO_c2w,
      XK0_RE    => XK0_RE_c2w,
      XK0_IM    => XK0_IM_c2w,
      XK1_RE    => XK1_RE_c2w,
      XK1_IM    => XK1_IM_c2w,
      XK2_RE    => XK2_RE_c2w,
      XK2_IM    => XK2_IM_c2w,
      XK3_RE    => XK3_RE_c2w,
      XK3_IM    => XK3_IM_c2w,
      XK4_RE    => XK4_RE_c2w,
      XK4_IM    => XK4_IM_c2w,
      XK5_RE    => XK5_RE_c2w,
      XK5_IM    => XK5_IM_c2w,
      XK6_RE    => XK6_RE_c2w,
      XK6_IM    => XK6_IM_c2w,
      XK7_RE    => XK7_RE_c2w,
      XK7_IM    => XK7_IM_c2w,
      XK8_RE    => XK8_RE_c2w,
      XK8_IM    => XK8_IM_c2w,
      XK9_RE    => XK9_RE_c2w,
      XK9_IM    => XK9_IM_c2w,
      XK10_RE   => XK10_RE_c2w,
      XK10_IM   => XK10_IM_c2w,
      XK11_RE   => XK11_RE_c2w,
      XK11_IM   => XK11_IM_c2w,
      BLK_EXP0  => BLK_EXP0_c2w,
      BLK_EXP1  => BLK_EXP1_c2w,
      BLK_EXP2  => BLK_EXP2_c2w,
      BLK_EXP3  => BLK_EXP3_c2w,
      BLK_EXP4  => BLK_EXP4_c2w,
      BLK_EXP5  => BLK_EXP5_c2w,
      BLK_EXP6  => BLK_EXP6_c2w,
      BLK_EXP7  => BLK_EXP7_c2w,
      BLK_EXP8  => BLK_EXP8_c2w,
      BLK_EXP9  => BLK_EXP9_c2w,
      BLK_EXP10 => BLK_EXP10_c2w,
      BLK_EXP11 => BLK_EXP11_c2w,
      OVFLO0    => OVFLO0_c2w,
      OVFLO1    => OVFLO1_c2w,
      OVFLO2    => OVFLO2_c2w,
      OVFLO3    => OVFLO3_c2w,
      OVFLO4    => OVFLO4_c2w,
      OVFLO5    => OVFLO5_c2w,
      OVFLO6    => OVFLO6_c2w,
      OVFLO7    => OVFLO7_c2w,
      OVFLO8    => OVFLO8_c2w,
      OVFLO9    => OVFLO9_c2w,
      OVFLO10   => OVFLO10_c2w,
      OVFLO11   => OVFLO11_c2w,
      xk_re_array    => xk_re_array,
      xk_im_array    => xk_im_array,
      blk_exp_array  => blk_exp_array,
      ovflo_array    => ovflo_array
      );

  XN_INDEX   <= XN_INDEX_c2w;
  XK_INDEX   <= XK_INDEX_c2w;



  xfft_inst : entity work.timing_model_core
    generic map (
      C_XDEVICEFAMILY      => C_XDEVICEFAMILY,
      C_CHANNELS           => C_CHANNELS,
      C_NFFT_MAX           => C_NFFT_MAX,
      C_ARCH               => C_ARCH,
      C_HAS_NFFT           => C_HAS_NFFT,
      C_USE_FLT_PT         => C_USE_FLT_PT,
      C_INPUT_WIDTH        => C_INPUT_WIDTH,
      C_TWIDDLE_WIDTH      => C_TWIDDLE_WIDTH,
      C_OUTPUT_WIDTH       => C_OUTPUT_WIDTH,
      C_HAS_SCALING        => C_HAS_SCALING,
      C_HAS_BFP            => C_HAS_BFP,
      C_HAS_ROUNDING       => C_HAS_ROUNDING,
      C_HAS_CE             => get_ce_generic (C_THROTTLE_SCHEME => C_THROTTLE_SCHEME, C_HAS_ACLKEN => C_HAS_ACLKEN),
      C_HAS_SCLR           => get_sclr_generic(C_ARCH => C_ARCH, C_HAS_NATURAL_OUTPUT => C_HAS_NATURAL_OUTPUT, C_HAS_ARESETN => C_HAS_ARESETN),
      C_HAS_OVFLO          => C_HAS_OVFLO,
      C_HAS_NATURAL_OUTPUT => C_HAS_NATURAL_OUTPUT,
      C_HAS_CYCLIC_PREFIX  => C_HAS_CYCLIC_PREFIX,
      C_DATA_MEM_TYPE      => C_DATA_MEM_TYPE,
      C_TWIDDLE_MEM_TYPE   => C_TWIDDLE_MEM_TYPE,
      C_BRAM_STAGES        => C_BRAM_STAGES,
      C_REORDER_MEM_TYPE   => C_REORDER_MEM_TYPE,
      C_USE_HYBRID_RAM     => C_USE_HYBRID_RAM,
      C_OPTIMIZE_GOAL      => C_OPTIMIZE_GOAL,
      C_CMPY_TYPE          => C_CMPY_TYPE,
      C_BFLY_TYPE          => C_BFLY_TYPE
      )
    port map (
      CLK            => ACLK,
      CE             => CE_w2c,
      SCLR           => SCLR_w2c,
      NFFT           => NFFT_w2c,
      NFFT_WE        => NFFT_WE_w2c,
      START          => START_w2c,
      UNLOAD         => UNLOAD_w2c,
      CP_LEN         => CP_LEN_w2c,
      CP_LEN_WE      => CP_LEN_WE_w2c,
      FWD_INV        => FWD_INV_w2c,
      FWD_INV_WE     => FWD_INV_WE_w2c,
      SCALE_SCH      => SCALE_SCH_w2c,
      SCALE_SCH_WE   => SCALE_SCH_WE_w2c,
      FWD_INV0       => FWD_INV0_w2c,
      FWD_INV0_WE    => FWD_INV0_WE_w2c,
      FWD_INV1       => FWD_INV1_w2c,
      FWD_INV1_WE    => FWD_INV1_WE_w2c,
      FWD_INV2       => FWD_INV2_w2c,
      FWD_INV2_WE    => FWD_INV2_WE_w2c,
      FWD_INV3       => FWD_INV3_w2c,
      FWD_INV3_WE    => FWD_INV3_WE_w2c,
      FWD_INV4       => FWD_INV4_w2c,
      FWD_INV4_WE    => FWD_INV4_WE_w2c,
      FWD_INV5       => FWD_INV5_w2c,
      FWD_INV5_WE    => FWD_INV5_WE_w2c,
      FWD_INV6       => FWD_INV6_w2c,
      FWD_INV6_WE    => FWD_INV6_WE_w2c,
      FWD_INV7       => FWD_INV7_w2c,
      FWD_INV7_WE    => FWD_INV7_WE_w2c,
      FWD_INV8       => FWD_INV8_w2c,
      FWD_INV8_WE    => FWD_INV8_WE_w2c,
      FWD_INV9       => FWD_INV9_w2c,
      FWD_INV9_WE    => FWD_INV9_WE_w2c,
      FWD_INV10      => FWD_INV10_w2c,
      FWD_INV10_WE   => FWD_INV10_WE_w2c,
      FWD_INV11      => FWD_INV11_w2c,
      FWD_INV11_WE   => FWD_INV11_WE_w2c,
      SCALE_SCH0     => SCALE_SCH0_w2c,
      SCALE_SCH0_WE  => SCALE_SCH0_WE_w2c,
      SCALE_SCH1     => SCALE_SCH1_w2c,
      SCALE_SCH1_WE  => SCALE_SCH1_WE_w2c,
      SCALE_SCH2     => SCALE_SCH2_w2c,
      SCALE_SCH2_WE  => SCALE_SCH2_WE_w2c,
      SCALE_SCH3     => SCALE_SCH3_w2c,
      SCALE_SCH3_WE  => SCALE_SCH3_WE_w2c,
      SCALE_SCH4     => SCALE_SCH4_w2c,
      SCALE_SCH4_WE  => SCALE_SCH4_WE_w2c,
      SCALE_SCH5     => SCALE_SCH5_w2c,
      SCALE_SCH5_WE  => SCALE_SCH5_WE_w2c,
      SCALE_SCH6     => SCALE_SCH6_w2c,
      SCALE_SCH6_WE  => SCALE_SCH6_WE_w2c,
      SCALE_SCH7     => SCALE_SCH7_w2c,
      SCALE_SCH7_WE  => SCALE_SCH7_WE_w2c,
      SCALE_SCH8     => SCALE_SCH8_w2c,
      SCALE_SCH8_WE  => SCALE_SCH8_WE_w2c,
      SCALE_SCH9     => SCALE_SCH9_w2c,
      SCALE_SCH9_WE  => SCALE_SCH9_WE_w2c,
      SCALE_SCH10    => SCALE_SCH10_w2c,
      SCALE_SCH10_WE => SCALE_SCH10_WE_w2c,
      SCALE_SCH11    => SCALE_SCH11_w2c,
      SCALE_SCH11_WE => SCALE_SCH11_WE_w2c,
      XN_INDEX  => XN_INDEX_c2w,
      XK_INDEX  => XK_INDEX_c2w,
      RFD      => RFD_c2w,
      BUSY     => BUSY_c2w,
      EDONE    => EDONE_c2w,
      DONE     => DONE_c2w,
      DV       => DV_c2w,
      CPV      => CPV_c2w,
      RFS      => RFS_c2w,
      FWD_INV_USED     => FWD_INV_USED     ,
      FWD_INV0_USED  => FWD_INV0_USED  ,
      FWD_INV1_USED  => FWD_INV1_USED  ,
      FWD_INV2_USED  => FWD_INV2_USED  ,
      FWD_INV3_USED  => FWD_INV3_USED  ,
      FWD_INV4_USED  => FWD_INV4_USED  ,
      FWD_INV5_USED  => FWD_INV5_USED  ,
      FWD_INV6_USED  => FWD_INV6_USED  ,
      FWD_INV7_USED  => FWD_INV7_USED  ,
      FWD_INV8_USED  => FWD_INV8_USED  ,
      FWD_INV9_USED  => FWD_INV9_USED  ,
      FWD_INV10_USED => FWD_INV10_USED ,
      FWD_INV11_USED => FWD_INV11_USED ,
      SCALE_SCH_USED   => SCALE_SCH_USED   ,
      SCALE_SCH0_USED  => SCALE_SCH0_USED  ,
      SCALE_SCH1_USED  => SCALE_SCH1_USED  ,
      SCALE_SCH2_USED  => SCALE_SCH2_USED  ,
      SCALE_SCH3_USED  => SCALE_SCH3_USED  ,
      SCALE_SCH4_USED  => SCALE_SCH4_USED  ,
      SCALE_SCH5_USED  => SCALE_SCH5_USED  ,
      SCALE_SCH6_USED  => SCALE_SCH6_USED  ,
      SCALE_SCH7_USED  => SCALE_SCH7_USED  ,
      SCALE_SCH8_USED  => SCALE_SCH8_USED  ,
      SCALE_SCH9_USED  => SCALE_SCH9_USED  ,
      SCALE_SCH10_USED => SCALE_SCH10_USED ,
      SCALE_SCH11_USED => SCALE_SCH11_USED
      );
end behavioral;
