
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
package timing_model_comp is
  component timing_model
    generic (
      C_XDEVICEFAMILY             : string;
      C_S_AXIS_CONFIG_TDATA_WIDTH : integer;
      C_S_AXIS_DATA_TDATA_WIDTH   : integer;
      C_M_AXIS_DATA_TDATA_WIDTH   : integer;
      C_M_AXIS_DATA_TUSER_WIDTH   : integer;
      C_M_AXIS_STATUS_TDATA_WIDTH : integer;
      C_THROTTLE_SCHEME           : integer;
      C_CHANNELS                  : integer;
      C_NFFT_MAX                  : integer;
      C_ARCH                      : integer;
      C_HAS_NFFT                  : integer;
      C_USE_FLT_PT                : integer;
      C_INPUT_WIDTH               : integer;
      C_TWIDDLE_WIDTH             : integer;
      C_OUTPUT_WIDTH              : integer;
      C_HAS_SCALING               : integer;
      C_HAS_BFP                   : integer;
      C_HAS_ROUNDING              : integer;
      C_HAS_ACLKEN                : integer;
      C_HAS_ARESETN               : integer;
      C_HAS_OVFLO                 : integer;
      C_HAS_NATURAL_OUTPUT        : integer;
      C_HAS_CYCLIC_PREFIX         : integer;
      C_HAS_XK_INDEX              : integer;
      C_DATA_MEM_TYPE             : integer;
      C_TWIDDLE_MEM_TYPE          : integer;
      C_BRAM_STAGES               : integer;
      C_REORDER_MEM_TYPE          : integer;
      C_USE_HYBRID_RAM            : integer;
      C_OPTIMIZE_GOAL             : integer;
      C_CMPY_TYPE                 : integer;
      C_BFLY_TYPE                 : integer);
    port (
      aclk                        : in  std_logic := '1';
      aclken                      : in  std_logic := '1';
      aresetn                     : in  std_logic := '1';
      s_axis_config_tdata         : in  std_logic_vector (C_S_AXIS_CONFIG_TDATA_WIDTH-1 downto 0);
      s_axis_config_tvalid        : in  std_logic;
      s_axis_config_tready        : out std_logic;
      s_axis_data_tdata           : in  std_logic_vector (C_S_AXIS_DATA_TDATA_WIDTH-1 downto 0);
      s_axis_data_tvalid          : in  std_logic;
      s_axis_data_tlast           : in  std_logic;
      s_axis_data_tready          : out std_logic;
      m_axis_data_tuser           : out std_logic_vector (C_M_AXIS_DATA_TUSER_WIDTH-1 downto 0);
      m_axis_data_tvalid          : out std_logic;
      m_axis_data_tlast           : out std_logic;
      m_axis_data_tready          : in  std_logic;
      m_axis_status_tvalid        : out std_logic;
      m_axis_status_tready        : in  std_logic := '1';
      event_frame_started         : out std_logic;
      event_tlast_unexpected      : out std_logic;
      event_tlast_missing         : out std_logic;
      event_status_channel_halt   : out std_logic;
      event_data_in_channel_halt  : out std_logic;
      event_data_out_channel_halt : out std_logic;
      FWD_INV_USED                : out std_logic;
      FWD_INV0_USED               : out std_logic;
      FWD_INV1_USED               : out std_logic;
      FWD_INV2_USED               : out std_logic;
      FWD_INV3_USED               : out std_logic;
      FWD_INV4_USED               : out std_logic;
      FWD_INV5_USED               : out std_logic;
      FWD_INV6_USED               : out std_logic;
      FWD_INV7_USED               : out std_logic;
      FWD_INV8_USED               : out std_logic;
      FWD_INV9_USED               : out std_logic;
      FWD_INV10_USED              : out std_logic;
      FWD_INV11_USED              : out std_logic;
      SCALE_SCH_USED              : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH0_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH1_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH2_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH3_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH4_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH5_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH6_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH7_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH8_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH9_USED             : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH10_USED            : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0);
      SCALE_SCH11_USED            : out std_logic_vector((C_NFFT_MAX*2)-((C_NFFT_MAX/2*2)*(C_ARCH mod 2))-1 downto 0));
  end component;
end timing_model_comp;
