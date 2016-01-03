cordic_pe_nbits_total = 20;
cordic_pe_nbits_frac = 16;
cordic_pe_nbits_int = cordic_pe_nbits_total - cordic_pe_nbits_frac;
atan_nbits = 20;
atan_nbits_frac = 15;
atan_nbits_int = atan_nbits - atan_nbits_frac;

cordic_pe_z_correct_nbits_total = atan_nbits;
cordic_pe_z_correct_nbits_frac = atan_nbits_frac;
cordic_pe_z_correct_nbits_int = cordic_pe_z_correct_nbits_total - cordic_pe_z_correct_nbits_frac;
