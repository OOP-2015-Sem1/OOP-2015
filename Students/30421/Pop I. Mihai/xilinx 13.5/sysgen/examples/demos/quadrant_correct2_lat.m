function [z_out] = quadrant_correct2_lat(z_in, sgn_y, sgn_x, nbits, binpt, lat)
  const_proto = {xlSigned, nbits, binpt, xlRound, xlSaturate};
  proto = {xlSigned, nbits, binpt};

  if sgn_x
    if sgn_y
      angle = xfix(const_proto, -pi);
    else
      angle = xfix(const_proto, pi);
    end
    z = angle - z_in;
    z = xfix(proto, z);
  else
    z = z_in;
  end

  persistent z_dly;
  if lat > 0
    z_dly = xl_state(zeros(1, lat), z, lat);
    z_out = z_dly.back;
    z_dly.push_front_pop_back(z);
  else
    z_out = z;
  end


