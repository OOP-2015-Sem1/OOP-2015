-------------------------------------------------------------------------------
-- Copyright (c) 2009 Xilinx, Inc.
-- All Rights Reserved
-------------------------------------------------------------------------------
--   ____  ____
--  /   /\/   /
-- /___/  \  /    Vendor     : Xilinx
-- \   \   \/     Version    : 1.0
--  \   \         Application: Xilinx System Generator
--  /   /         Filename   : encrypted_hdl_import_wrapper.vhd
-- /___/   /\     
-- \   \  /  \
--  \___\/\___\
--
-- Design Name: Wrapper to import an encrypted VHDL file 
-------------------------------------------------------------------------------
library IEEE;
use IEEE.std_logic_1164.all;
entity encrypted_hdl_import_wrapper is
  PORT (
    clk : in STD_LOGIC;
    ce : in STD_LOGIC;
    active_video_in : in STD_LOGIC;
    hblank_in : in STD_LOGIC;
    sclr : in STD_LOGIC;
    vblank_in : in STD_LOGIC;
    video_data_in : in STD_LOGIC_VECTOR(23 DOWNTO 0);
    video_data_out : out STD_LOGIC_VECTOR(23 DOWNTO 0);
    active_video_out : out STD_LOGIC;
    hblank_out : out STD_LOGIC;
    vblank_out : out STD_LOGIC);
end encrypted_hdl_import_wrapper;
architecture structural of encrypted_hdl_import_wrapper is
    component encrypted_hdl_import
      PORT (
        clk : in STD_LOGIC;
        ce : in STD_LOGIC;
        active_video_in : in STD_LOGIC;
        hblank_in : in STD_LOGIC;
        sclr : in STD_LOGIC;
        vblank_in : in STD_LOGIC;
        video_data_in : in STD_LOGIC_VECTOR(23 DOWNTO 0);
        video_data_out : out STD_LOGIC_VECTOR(23 DOWNTO 0);
        active_video_out : out STD_LOGIC;
        hblank_out : out STD_LOGIC;
        vblank_out : out STD_LOGIC);
    end component;
begin
    your_instance_name : encrypted_hdl_import
      port map (
        clk => clk,
        ce => ce,
        active_video_in => active_video_in,
        hblank_in => hblank_in,
        sclr => sclr,
        vblank_in => vblank_in,
        video_data_in => video_data_in,
        video_data_out => video_data_out,
        active_video_out => active_video_out,
        hblank_out => hblank_out,
        vblank_out => vblank_out);
end structural;