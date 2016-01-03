## NOTE:  customer.do file
##
vlib design1_lib
vcom -explicit  -93 -work design1_lib "../hdl_netlist1/spram.vhd"
vcom -explicit  -93 -work design1_lib "../hdl_netlist1/spram_cw.vhd"
vlib design2_lib
vcom -explicit  -93 -work design2_lib "../hdl_netlist2/mac_fir.vhd"
vcom -explicit  -93 -work design2_lib "../hdl_netlist2/mac_fir_cw.vhd"
vlib work
vcom -explicit  -93 top_level.vhd
vcom -explicit  -93 top_level_testbench.vhd
foreach i [glob ../hdl_netlist1/*.mif] {
	file copy -force $i .
}
foreach i [glob ../hdl_netlist2/*.mif] {
	file copy -force $i .
}
vsim -t 1ps   -lib work top_level_testbench
do wave.do
run 10000ns

