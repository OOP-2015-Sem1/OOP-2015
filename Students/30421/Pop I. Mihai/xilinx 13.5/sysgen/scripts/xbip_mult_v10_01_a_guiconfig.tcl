set ip_name "Multiplier"
set ip_version "10.01.a"
set dsptool_ready true

set block_description "$ip_name $ip_version. Multiplier BaseIP."

set param_order \
    [list \
        [list "<tab>" "Basic" \
            [list \
                multtype \
                constvalue \
                ccmimp \
                multiplier_construction \
                optgoal \
                use_custom_output_width \
                outputwidthhigh \
                outputwidthlow \
                userounding \
                roundpoint \
                pipestages \
                [list "<etch>" "Optional Ports" \
                    [list clockenable syncclear sclrcepriority SCLR_Overrides_CE ] \
                ]\
                 zerodetect \
            ] \
        ] \
        [list "<tab>" "Implementation" \
            [list \
                fpga_area_estimation \
             ] \
        ] \
    ]

