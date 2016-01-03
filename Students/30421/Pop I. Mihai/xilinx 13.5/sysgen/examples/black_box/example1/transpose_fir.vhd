library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_SIGNED.ALL;

--  Uncomment the following lines to use the declarations that are
--  provided for instantiating Xilinx primitive components.
--library UNISIM;
--use UNISIM.VComponents.all;

entity transpose_fir is
  port (
    din: in std_logic_vector(11 downto 0);
    clk: in std_logic;
    ce: in std_logic;
    rst: in std_logic;
    dout: out std_logic_vector(25 downto 0)
  );
end transpose_fir;

architecture behavioral of transpose_fir is
  component mac
    generic (
      coef_value: integer := 0
    );
    port (
      din: in std_logic_vector(11 downto 0);
      cin: in std_logic_vector(25 downto 0);
      clk: in std_logic;
      ce: in std_logic;
      rst: in std_logic;
      dout: out std_logic_vector(25 downto 0)
    );
  end component;

  constant n: integer := 23; -- Number of Coefficients
  type coef_array is array (0 to n - 1) of integer; -- Coefficient Values
  constant coefficient: coef_array := (
     -38, -74, -109, -109, -37, 140, 435, 827, 1262, 1663, 1945, 2047,
     1945, 1663, 1262, 827, 435, 140, -37, -109, -109, -74, -38
  );

  signal cin_temp : std_logic_vector(26 * n downto 0) :=
    conv_std_logic_vector(0, 26 * n + 1);
begin
  G0: for i in 0 to n - 1 generate

  G_first: if i = 0 generate
    M0: MAC
      generic map (
        coef_value => coefficient(i)
      )
      port map (
        din => din,
        cin => "00000000000000000000000000",
        clk => clk,
        ce => ce,
        rst => rst,
        dout => cin_temp(25 downto 0)
      );
  end generate;

  GX: if (i >= 1 and i < n - 1) generate
    M1: MAC
      generic map (
        coef_value => coefficient(i)
      )
      port map (
        din => din,
        cin => cin_temp(i * 25 + (i - 1) downto ((i - 1) * 26)),
        clk => clk,
        ce => ce,
        rst => rst,
        dout => cin_temp((i + 1) * 25 + i downto i * 26)
      );
  end generate;

  G_last: if i = n - 1 generate
    M2: MAC
      generic map (
        coef_value => coefficient(i)
      )
      port map (
        din => din,
        cin => cin_temp(i * 25 + (i - 1) downto ((i - 1) * 26)),
        clk => clk,
        ce => ce,
        rst => rst,
        dout => dout
      );
    end generate;
  end generate;
end behavioral;
