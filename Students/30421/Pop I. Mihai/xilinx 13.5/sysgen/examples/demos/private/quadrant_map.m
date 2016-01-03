function [z_out, angle_map, sgn_z] = quadrant_map(z, half_pi_dbl, nbits, ...
                                              binpt)
  const_proto = {xlSigned, nbits, binpt, xlRound, xlWrap};
  proto = {xlSigned, nbits, binpt};

  half_pi = xfix(const_proto, half_pi_dbl);
  neg_half_pi = xfix(const_proto, -half_pi_dbl);

  z_gt_hp = z>half_pi;
  z_lt_nhp = z<neg_half_pi;

  angle_map = z_gt_hp | z_lt_nhp;

  if angle_map
    if z_gt_hp
      z_out = xfix(proto, z - half_pi);
    else
      z_out = xfix(proto, z + half_pi);
    end
  else
      z_out = z;
  end


  z_out = xfix(proto, z_out);
  sgn_z = z<0;
