-- If you see error messages concerning missing libraries for
-- XilinxCoreLib, unisims, or simprims, you may not have set
-- up your ModelSim environment correctly.  See the Xilinx
-- Support Website for instructions telling how to compile
-- these libraries.

vlib work

vcom -93 -nowarn 1 lowpass_filter_pkgs.vhd
-- Add the VHDL co-simulation component here.  For example:
-- vcom -93 -nowarn 1 ./netlist/lp_mac_fir_mti_hwcosim.vhd 
vcom -93 -nowarn 1 lowpass_filter_testbench.vhd

