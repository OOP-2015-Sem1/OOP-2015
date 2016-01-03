function [z_out, z_gt_hp, z_lt_nhp, z_m_hp, z_p_hp, sgn_z] = ...
    quadrant_map1(z, half_pi_dbl, nbits, binpt)
% in order to imporve the performace, the quadrant_map is divided
% into two parts with delays block in between. This is the first
% part of the two.
%
% half_pi_dbl, nbits, and binpt are constant arguments set from the
% block mask.

  const_proto = {xlSigned, 16, 13, xlRound, xlWrap};
  proto = {xlSigned, 16, 13};

  half_pi = xfix(const_proto, half_pi_dbl);
  neg_half_pi = xfix(const_proto, -half_pi_dbl);

  z_gt_hp = z>half_pi;
  z_lt_nhp = z<neg_half_pi;

  z_m_hp = xfix(proto, z - half_pi);
  z_p_hp = xfix(proto, z + half_pi);

  sgn_z = z<0;

  z_out = z;
