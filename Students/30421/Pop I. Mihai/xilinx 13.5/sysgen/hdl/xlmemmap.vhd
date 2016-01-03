
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
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
entity xlreqgrantmanager is
    port (hw_req   : in std_logic;
          sw_req   : out std_logic;
          hw_grant : out std_logic;
          sw_grant : in std_logic;
          clk      : in std_logic);
end xlreqgrantmanager;
architecture structural of xlreqgrantmanager is
signal hw_req_int : std_logic;
signal hr_reg1 : std_logic;
signal hr_reg1_en : std_logic;
signal hr_reg2 : std_logic;
signal sw_req_int : std_logic;
signal sw_grant_int : std_logic;
begin
  sw_grant_int <= sw_grant;
  sw_req <= sw_req_int;
  hr_reg1_en <= not(hr_reg2);
  process (clk, hw_req)
  begin
    if (hw_req = '0') then
      hr_reg1 <= '0';
    elsif (clk'event and clk='1') then
      if (hr_reg1_en = '1') then
        hr_reg1 <= '1';
      end if;
    end if;
  end process;
  process (clk)
  begin
    if (clk'event and clk='1') then
      hr_reg2 <= hr_reg1;
    end if;
  end process;
  hw_req_int <= hw_req and hr_reg1;
  process (clk, hw_req_int)
  begin
    if (hw_req_int = '0') then
      sw_req_int <= '0';
    elsif ( rising_edge(clk) ) then
      if (sw_grant_int = '0') then
        sw_req_int <= '1';
      end if;
    end if;
  end process;
  process (clk, hw_req_int)
  begin
    if ( hw_req_int = '0') then
      hw_grant <= '0';
    elsif ( rising_edge(clk) ) then
      if (sw_req_int = '1' and sw_grant_int = '1') then
        hw_grant <= '1';
      end if;
    end if;
  end process;
end architecture structural;
library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use work.conv_pkg.all;
entity xlmemmap is
    generic (
### Generics added by wrapper builder
    );
    port (
### Ports added by wrapper builder
    );
end xlmemmap;
architecture behavioral of xlmemmap is
    function zero_pad(inp : std_logic_vector; new_width : INTEGER)
        return std_logic_vector
    is
        constant old_width : integer := inp'length;
        variable vec : std_logic_vector(old_width-1 downto 0);
        variable result : std_logic_vector(new_width-1 downto 0);
    begin
        vec := inp;
        if new_width >= old_width then
            result(old_width-1 downto 0) := vec;
            if new_width-1 >= old_width then
                for i in new_width-1 downto old_width loop
                    result(i) := '0';
                end loop;
            end if;
        else
            result(new_width-1 downto 0) := vec(new_width-1 downto 0);
        end if;
        return result;
    end;
    function zero_pad(inp : std_logic; new_width : INTEGER)
        return std_logic_vector
    is
        variable result : std_logic_vector(new_width-1 downto 0);
    begin
        result(0) := inp;
        for i in new_width-1 downto 1 loop
            result(i) := '0';
        end loop;
        return result;
    end;
    component xlreqgrantmanager
      port (hw_req   : in std_logic;
            sw_req   : out std_logic;
            hw_grant : out std_logic;
            sw_grant : in std_logic;
            clk      : in std_logic);
    end component;
    signal addr_int     : std_logic_vector(23 downto 0);
    signal bank_sel_int : std_logic_vector(7 downto 0);
    signal mm_data_out  : std_logic_vector(31 downto 0);
### Shared memory request and grant signals added by wrapper builder
begin
    addr_int     <= addr(23 downto 0);
    bank_sel_int <= bank_sel(7 downto 0);
    process ( pci_clk )
    begin
        if ( pci_clk'event and pci_clk = '1' ) then
            if ( we = '1' and bank_sel_int = x"00" ) then
                case addr_int is
### Memory map to in port connections added by wrapper builder
                    when others => NULL;
                end case;
            else
                case addr_int is
### Out port to memory map connections added by wrapper builder
                    when others => NULL;
                end case;
            end if;
        end if;
    end process;
### Output signal multiplexing logic added by wrapper builder
### Shared memory write enable logic added by wrapper builder
### Shared memory xlreqgrantmanager components added by wrapper builder
end
behavioral;
