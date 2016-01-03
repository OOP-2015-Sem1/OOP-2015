set ip_name "Reed-Solomon Decoder"
set ip_version "6.1"

set block_description "$ip_name $ip_version. Decodes systematically encoded RS codes. The decoder supports making received data symbols as erasure."

set param_order \
    [list \
        [list "<tab>" "Basic" \
            [list \
                code_specification \
                number_of_channels \
                clocks_per_symbol \
                [list "<etch>" "Optional Ports" \
                    [list erase original_delayed_data variable_block_length rst en ] \
                ]\
            ] \
        ] \
        [list "<tab>" "Custom Specification" \
            [list symbol_width symbols_per_block data_symbols field_polynomial generator_start scaling_factor ] \
        ] \
        [list "<tab>" "Implementation" \
            [list \
                [list "<etch>" "Core Parameters" \
                    [list memory_style optimization self_recovering]\
                ] \
                fpga_area_estimation \
             ] \
        ] \
    ]

