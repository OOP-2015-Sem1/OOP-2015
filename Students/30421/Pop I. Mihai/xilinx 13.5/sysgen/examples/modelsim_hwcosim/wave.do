onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -color {Medium Spring Green} -format Analog-Step -height 200 -offset 100.0 /lowpass_filter_testbench/sample_in/o
add wave -noupdate -color Cyan -format Analog-Step -height 200 -offset 1500000.0 -scale 0.0001 /lowpass_filter_testbench/sample_out/i
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {0 ps} 0}
WaveRestoreZoom {0 ps} {2823211370 ps}
configure wave -namecolwidth 150
configure wave -valuecolwidth 108
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
