set ip_name "Reed-Solomon Encoder"
set ip_version "6.1"

set block_description "$ip_name $ip_version. The encoder transforms each block of k information symbols presented serially at the input port to a block of n symbols presented serially at the output port."

set param_order \
    [list \
        [list "<tab>" "Basic" \
            [list \
                code_specification \
                number_of_channels \
                [list "<etch>" "Optional Ports" \
                    [list variable_number_of_check_symbols variable_block_length nd rdy rfd rffd rst en ] \
                ]\
            ] \
        ] \
        [list "<tab>" "Custom Specification" \
            [list symbol_width symbol_per_block data_symbols field_polynomial generator_start scaling_factor ] \
        ] \
        [list "<tab>" "Implementation" \
            [list \
                [list "<etch>" "Core Parameters" \
                    [list memory_style check_symbol_generator]\
                ] \
                fpga_area_estimation \
             ] \
        ] \
    ]

