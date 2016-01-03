function [z_out, angle_map, sgn_z] = ...
    quadrant_map_lat2(z, nbits, binpt, lat)

  const_proto = {xlSigned, nbits, binpt, xlRound, xlWrap};
  proto = {xlSigned, nbits, binpt};

  half_pi = xfix(const_proto, pi / 2);
  neg_half_pi = xfix(const_proto, -pi / 2);


  persistent z_gt_hp_reg, z_gt_hp_reg = xl_state(0, {xlBoolean});
  persistent z_lt_nhp_reg, z_lt_nhp_reg = xl_state(0, {xlBoolean});
  persistent zin_reg, zin_reg = xl_state(0, proto);
  persistent addsub_reg, addsub_reg = xl_state(0, proto);
  persistent am_reg, am_reg = xl_state(0, {xlBoolean});

  persistent zout_reg; zout_reg = xl_state(0, proto);
  persistent sz_dly; sz_dly = xl_state(zeros(1, 2), {xlBoolean});


  z_out = zout_reg;
  angle_map = am_reg;
  sgn_z = sz_dly.back;

  am_i = z_gt_hp_reg | z_lt_nhp_reg;
  am_reg = am_i;
  if am_i
    zout_reg = addsub_reg;
  else
    zout_reg = zin_reg;
  end

  zin_reg = z;

  z_nbits = xl_nbits(z);
  sgn_z_i = xl_slice(z, z_nbits-1, z_nbits-1) == 1;

  if sgn_z_i
      addsub_reg = z + half_pi;
  else
      addsub_reg = z - half_pi;
  end


  sz_dly.push_front_pop_back(sgn_z_i);

  z_gt_hp_reg = z > half_pi;
  z_lt_nhp_reg = z < neg_half_pi;
  
