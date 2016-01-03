---------------------------------------------------------------
-- (c) Copyright 1995 - [% T.MetaData.Year %] Xilinx, Inc. All rights reserved.
--
-- This file contains confidential and proprietary information
-- of Xilinx, Inc. and is protected under U.S. and 
-- international copyright and other intellectual property
-- laws.
--
-- DISCLAIMER
-- This disclaimer is not a license and does not grant any
-- rights to the materials distributed herewith. Except as
-- otherwise provided in a valid license issued to you by
-- Xilinx, and to the maximum extent permitted by applicable
-- law: (1) THESE MATERIALS ARE MADE AVAILABLE "AS IS" AND
-- WITH ALL FAULTS, AND XILINX HEREBY DISCLAIMS ALL WARRANTIES
-- AND CONDITIONS, EXPRESS, IMPLIED, OR STATUTORY, INCLUDING
-- BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY, NON-
-- INFRINGEMENT, OR FITNESS FOR ANY PARTICULAR PURPOSE; and
-- (2) Xilinx shall not be liable (whether in contract or tort,
-- including negligence, or under any other theory of
-- liability) for any loss or damage of any kind or nature
-- related to, arising under or in connection with these
-- materials, including for any direct, or any indirect,
-- special, incidental, or consequential loss or damage
-- (including loss of data, profits, goodwill, or any type of
-- loss or damage suffered as a result of any action brought
-- by a third party) even if such damage or loss was
-- reasonably foreseeable or Xilinx had been advised of the
-- possibility of the same.
--
-- CRITICAL APPLICATIONS
-- Xilinx products are not designed or intended to be fail-
-- safe, or for use in any application requiring fail-safe
-- performance, such as life-support or safety devices or
-- systems, Class III medical devices, nuclear facilities,
-- applications related to the deployment of airbags, or any
-- other applications that could lead to death, personal
-- injury, or severe property or environmental damage
-- (individually and collectively, "Critical
-- Applications"). Customer assumes the sole risk and
-- liability of any use of Xilinx products in Critical
-- Applications, subject only to applicable laws and
-- regulations governing limitations on product liability.
--
-- THIS COPYRIGHT NOTICE AND DISCLAIMER MUST BE RETAINED AS
-- PART OF THIS FILE AT ALL TIMES.
---------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity xlnegedgedet is
  port (
    a : in std_logic;
    clk : in std_logic;
    dout : out std_logic
  );
end xlnegedgedet;

architecture behavior of xlnegedgedet is
  signal delayed_a : std_logic := '0';
begin
  delay_input_signal : process(clk)
  begin
    if clk'event and clk = '1' then
      delayed_a <= a;
    end if;
  end process;
  dout <= delayed_a and (not a);
end behavior;

------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library unisim;
use unisim.vcomponents.all;

entity xldcmlockrelease is
  generic (
    EXTRA_DELAY : integer := 0
  );
  port (
    clk : in std_logic;
    reset : in std_logic;
    locked : in std_logic;
    releasebufgce : out std_logic
  );
end xldcmlockrelease;

architecture structural of xldcmlockrelease is
  signal reset_net : std_logic;
  signal reset_neg_edge_pulse_net : std_logic;
  signal reset_neg_edge_capture_net : std_logic;

  signal locked_net : std_logic;
  signal locked_bar_net : std_logic;
  signal locked_pos_edge_pulse_net : std_logic;
  signal locked_pos_edge_capture_net : std_logic;

  signal release_net : std_logic;
  signal release_net_temp : std_logic;

  signal simulation_mux : std_logic := '0';
begin
  reset_net <= reset;
  locked_net <= locked;
  locked_bar_net <= not locked;

-- synopsys translate_off
  simulation_mux <= '1';
-- synopsys translate_on

  process (simulation_mux, locked_net, release_net)
  begin
    if simulation_mux = '1' then
      releasebufgce <= locked_net;
    else
      releasebufgce <= release_net;
    end if;
  end process;

  falling_edge_detect_on_reset : entity work.xlnegedgedet
    port map (
      a => reset_net,
      clk => clk,
      dout => reset_neg_edge_pulse_net
    );

  capture_falling_edge_on_reset : FDRE
    port map (
      C => clk,
      CE => reset_neg_edge_pulse_net,
      D => '1',
      R => reset_net,
      Q => reset_neg_edge_capture_net
    );

  rising_edge_detect_on_locked : entity work.xlnegedgedet
    port map (
      a => locked_bar_net,
      clk => clk,
      dout => locked_pos_edge_pulse_net
    );

  capture_rising_edge_on_locked : FDRE
    port map (
      C => clk,
      CE => locked_pos_edge_capture_net,
      D => '1',
      R => reset_net,
      Q => release_net_temp
    );

  locked_pos_edge_capture_net <= reset_neg_edge_capture_net and locked_pos_edge_pulse_net;

  virtex4_mode : if (EXTRA_DELAY = 0) generate
    release_net <= release_net_temp;
  end generate virtex4_mode;

  virtex5_spartan3adsp_mode : if (EXTRA_DELAY /= 0) generate
    delay_v5_s3adsp : FDRE
      port map (
        C => clk,
        CE => '1',
        D => release_net_temp,
        R => reset_net,
        Q => release_net
      );
  end generate virtex5_spartan3adsp_mode;

end structural;

------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
-- synopsys translate_off
library unisim;
use unisim.vcomponents.all;
-- synopsys translate_on

entity xlresetgenerator is
  generic (
    N_RESET_STAGES : integer := 3
  );
  port (
    clk : in std_logic;
    lock : in std_logic;
    user_reset : in std_logic;
    dcm_reset : out std_logic
  );
end xlresetgenerator;

architecture rtl of xlresetgenerator is
  signal det_neg_edge_lock_signal : std_logic;
  signal loss_of_lock_or_reset : std_logic;
  signal reset_stages : std_logic_vector(N_RESET_STAGES-1 downto 0) := (others => '1');
begin
  neg_edge_det_inst : entity work.xlnegedgedet
    port map (
      a => lock,
      clk => clk,
      dout => det_neg_edge_lock_signal
    );

  loss_of_lock_or_reset <= det_neg_edge_lock_signal or user_reset;

  gen_reset_stages : process (clk)
  begin
    if clk'event and clk = '1' then
      if loss_of_lock_or_reset = '1' then
        reset_stages <= (others => '1');
      else
        reset_stages <= reset_stages(N_RESET_STAGES-2 downto 0) & '0';
      end if;
    end if;
  end process gen_reset_stages;

  dcm_reset <= reset_stages(N_RESET_STAGES-1);
end rtl;

------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library unisim;
use unisim.vcomponents.all;

entity [% T.ModuleName %] is
  port (
    reset : in std_logic;
[%  IF T.InputClock.IsDifferential                                                  -%]
    clkin_p : in std_logic;
    clkin_n : in std_logic;
[%  ELSE                                                                            -%]
    clkin : in std_logic;
[%  END                                                                             -%]
[%  FOREACH p = T.OutputClocks                                                      -%]
    [% p.Name %] : out std_logic;
[%  END                                                                             -%]
    locked : out std_logic
  );
end [% T.ModuleName %];

architecture rtl of [% T.ModuleName %] is
  attribute core_generation_info: string;
  attribute core_generation_info of rtl : architecture is "[% T.ModuleName %],platform_api_clocking_dcm,{Family=[% T.Family %],Device=[% T.Device %],Speed=[% T.Speed %],Package=[% T.Package %],HDLLanguage=[% T.HDLLanguage %],InputClockPeriod=[% T.InputClock.Period %],InputClockIsDifferential=[% T.InputClock.IsDifferential %],
[%- OutputClockInfo = ''                                                            -%]
[%- i = 1                                                                           -%]
[%- FOREACH p = T.OutputClocks                                                      -%]
OutputClock[% i %]Period=[% p.Period %],
[%-   i = i + 1                                                                     -%]
[%- END                                                                             -%]
}";

  signal clkin_net : std_logic;
  signal releasebufgce : std_logic;
  signal dcm_reset : std_logic;
  signal dcm_clk0 : std_logic;
  signal dcm_clk2x : std_logic;
  signal dcm_clkdv : std_logic;
  signal dcm_clkfx : std_logic;
  signal dcm_clk0_buf : std_logic;
  signal dcm_clk2x_buf : std_logic;
  signal dcm_clkdv_buf : std_logic;
  signal dcm_clkfx_buf : std_logic;
  signal dcm_clkfb : std_logic;
  signal dcm_locked : std_logic;
begin
[%  IF T.InputClock.IsDifferential                                                  -%]
  ibufgds_clkin : IBUFGDS
    port map (
      I => clkin_p,
      IB => clkin_n,
      O => clkin_net
    );
[%  ELSE                                                                            -%]
  ibufg_clkin : IBUFG
    port map (
      I => clkin,
      O => clkin_net
    );
[%  END                                                                             -%]

  locked <= dcm_locked;

  resetgenerator_inst : entity work.xlresetgenerator
    port map (
      clk => clkin_net,
      lock => dcm_locked,
      user_reset => reset,
      dcm_reset => dcm_reset
    );

  dcmlockrelease_inst : entity work.xldcmlockrelease
    generic map (
[%  IF T.Family == 'virtex4'                                                        -%]
      EXTRA_DELAY => 1
[%  ELSE                                                                            -%]
      EXTRA_DELAY => 0
[%  END                                                                             -%]
    )
    port map (
      clk => clkin_net,
      locked => dcm_locked,
      reset => dcm_reset,
      releasebufgce => releasebufgce
    );

[%  IF T.Family == 'virtex4' || T.Family == 'virtex5'                               -%]
  dcm_inst : DCM_ADV
    generic map (
[%  IF T.ClockGenerator.DCM.ClkFx.Used                                              -%]
      CLKFX_MULTIPLY => [% T.ClockGenerator.DCM.ClkFx.Multiplier %],
      CLKFX_DIVIDE => [% T.ClockGenerator.DCM.ClkFx.Divisor %],
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkDv.Used                                              -%]
      CLKDV_DIVIDE => [% T.ClockGenerator.DCM.ClkDv.Divisor.replace('^(\d+)$', '$1.0') %],
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DFSFrequencyMode.length != ''                           -%]
      DFS_FREQUENCY_MODE => "[% T.ClockGenerator.DCM.DFSFrequencyMode %]",
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DLLFrequencyMode.length != ''                           -%]
      DLL_FREQUENCY_MODE => "[% T.ClockGenerator.DCM.DLLFrequencyMode %]",
[%  END                                                                             -%]
      CLKIN_PERIOD => [% T.InputClock.Period.replace('^(\d+)$', '$1.0') %],
      CLKIN_DIVIDE_BY_2 => [% T.ClockGenerator.DCM.ClkInDivideBy2 ? 'TRUE' : 'FALSE' %],
      CLKOUT_PHASE_SHIFT => "NONE",
      SIM_DEVICE => "[% T.Family %]",
      CLK_FEEDBACK => "1X",
      PHASE_SHIFT => 0
    )
    port map (
      RST => dcm_reset,
      CLKIN => clkin_net,
      CLK0 => dcm_clk0,
      CLK180 => open,
      CLK270 => open,
      CLK2X => dcm_clk2x,
      CLK2X180 => open,
      CLK90 => open,
      CLKDV => dcm_clkdv,
      CLKFB => dcm_clkfb,
      CLKFX => dcm_clkfx,
      CLKFX180 => open,
      LOCKED => dcm_locked,
      DADDR => (others => '0'),
      DCLK => '0',
      DEN => '0',
      DI => (others => '0'),
      DO => open,
      DRDY => open,
      DWE => '0',
      PSCLK => '0',
      PSEN => '0',
      PSINCDEC => '0',
      PSDONE => open
    );
[%  ELSE                                                                            -%]
  dcm_inst : DCM
    generic map (
[%  IF T.ClockGenerator.DCM.ClkFx.Used                                              -%]
      CLKFX_MULTIPLY => [% T.ClockGenerator.DCM.ClkFx.Multiplier %],
      CLKFX_DIVIDE => [% T.ClockGenerator.DCM.ClkFx.Divisor %],
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkDv.Used                                              -%]
      CLKDV_DIVIDE => [% T.ClockGenerator.DCM.ClkDv.Divisor.replace('^(\d+)$', '$1.0') %],
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DFSFrequencyMode.length != ''                           -%]
      DFS_FREQUENCY_MODE => "[% T.ClockGenerator.DCM.DFSFrequencyMode %]",
[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.DLLFrequencyMode.length != ''                           -%]
      DLL_FREQUENCY_MODE => "[% T.ClockGenerator.DCM.DLLFrequencyMode %]",
[%  END                                                                             -%]
      CLKIN_PERIOD => [% T.InputClock.Period.replace('^(\d+)$', '$1.0') %],
      CLKIN_DIVIDE_BY_2 => [% T.ClockGenerator.DCM.ClkInDivideBy2 ? 'TRUE' : 'FALSE' %],
      CLKOUT_PHASE_SHIFT => "NONE",
      CLK_FEEDBACK => "1X",
      PHASE_SHIFT => 0
    )
    port map (
      RST => dcm_reset,
      CLKIN => clkin_net,
      CLK0 => dcm_clk0,
      CLK180 => open,
      CLK270 => open,
      CLK2X => dcm_clk2x,
      CLK2X180 => open,
      CLK90 => open,
      CLKDV => dcm_clkdv,
      CLKFB => dcm_clkfb,
      CLKFX => dcm_clkfx,
      CLKFX180 => open,
      LOCKED => dcm_locked,
      DSSEN => '0',
      PSCLK => '0',
      PSEN => '0',
      PSINCDEC => '0',
      PSDONE => open,
      STATUS => open
    );
[%  END                                                                             -%]

[%  IF T.ClockGenerator.DCM.Clk0.Used                                               -%]
  bufgce_dcm_clk0 : BUFGCE
    port map (
      I => dcm_clk0,
      CE => releasebufgce,
      O => dcm_clk0_buf
    );

[%    FOREACH p = T.ClockGenerator.DCM.Clk0.MappedPorts                             -%]
  [% p %] <= dcm_clk0_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.Clk2x.Used                                              -%]
  bufgce_dcm_clk2x : BUFGCE
    port map (
      I => dcm_clk2x,
      CE => releasebufgce,
      O => dcm_clk2x_buf
    );

[%    FOREACH p = T.ClockGenerator.DCM.Clk2x.MappedPorts                            -%]
  [% p %] <= dcm_clk2x_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkDv.Used                                              -%]
  bufgce_dcm_clkdv : BUFGCE
    port map (
      I => dcm_clkdv,
      CE => releasebufgce,
      O => dcm_clkdv_buf
    );

[%    FOREACH p = T.ClockGenerator.DCM.ClkDv.MappedPorts                            -%]
  [% p %] <= dcm_clkdv_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
[%  IF T.ClockGenerator.DCM.ClkFx.Used                                              -%]
  bufgce_dcm_clkfx : BUFGCE
    port map (
      I => dcm_clkfx,
      CE => releasebufgce,
      O => dcm_clkfx_buf
    );

[%    FOREACH p = T.ClockGenerator.DCM.ClkFx.MappedPorts                            -%]
  [% p %] <= dcm_clkfx_buf;
[%    END                                                                           -%]

[%  END                                                                             -%]
  bufg_dcm_clkfb : BUFG
    port map (
      I => dcm_clk0,
      O => dcm_clkfb
    );

end rtl;
