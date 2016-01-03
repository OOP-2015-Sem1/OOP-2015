onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic /top_level_testbench/clk1
add wave -noupdate -format Literal -radix unsigned /top_level_testbench/addr
add wave -noupdate -format Logic /top_level_testbench/rd_wr
add wave -noupdate -format Literal /top_level_testbench/dut/data_in
add wave -noupdate -format Literal /top_level_testbench/dut/data_out
add wave -noupdate -format Literal /top_level_testbench/data
add wave -noupdate -format Logic /top_level_testbench/clk2
add wave -noupdate -format Literal /top_level_testbench/fir_in
add wave -noupdate -format Literal -radix binary /top_level_testbench/fir_out
add wave -noupdate -divider {Design #1 Output}
add wave -noupdate -format Analog-Step -radix decimal -scale 5.0 /top_level_testbench/real_data
add wave -noupdate -divider {Design #2 Output}
add wave -noupdate -format Analog-Step -radix decimal -scale 30.0 /top_level_testbench/real_fir_out
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {39521022 ps} 0}
WaveRestoreZoom {5814063 ps} {10220313 ps}
configure wave -namecolwidth 212
configure wave -valuecolwidth 92
configure wave -justifyvalue left
configure wave -signalnamewidth 0
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
