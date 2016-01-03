function [z_out, angle_map] = ...
  quadrant_map2(z, z_gt_hp, z_lt_nhp, z_m_hp, z_p_hp, ...
                half_pi_dbl, nbits, binpt)
% in order to imporve the performace, the quadrant_map is divided
% into two parts with delays block in between. This is the second
% part of the two.
%
% half_pi_dbl, nbits, and binpt are constant arguments set from the
% block mask.


  proto = {xlSigned, nbits, binpt};

  angle_map = z_gt_hp | z_lt_nhp;

  if angle_map
    if z_gt_hp
      z_out = z_m_hp;
    else
      z_out = z_p_hp;
    end
  else
      z_out = z;
  end

  z_out = xfix(proto, z_out);
