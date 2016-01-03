function [z_out, angle_map, sgn_z] = ...
    quadrant_map_lat(z, nbits, binpt, lat)

  const_proto = {xlSigned, nbits, binpt, xlRound, xlWrap};
  proto = {xlSigned, nbits, binpt};

  half_pi = xfix(const_proto, pi / 2);
  neg_half_pi = xfix(const_proto, -pi / 2);

  z_gt_hp = z>half_pi;
  z_lt_nhp = z<neg_half_pi;

  angle_map_i = z_gt_hp | z_lt_nhp;

  z_nbits = xl_nbits(z);
  sgn_z_i = xl_slice(z, z_nbits-1, z_nbits-1) == 1;

  if angle_map_i
    if sgn_z_i
      z_out_i = z + half_pi;
    else
      z_out_i = z - half_pi;
    end
    z_out_i = xfix(proto, z_out_i);
  else
    z_out_i = z;
  end


  z_out_i = xfix(proto, z_out_i);


  persistent z_dly;
  persistent am_dly;
  persistent sz_dly;

  if lat > 0
    % z_out delay line
    z_dly = xl_state(zeros(1, lat), z_out_i, lat);
    z_out = z_dly.back;
    z_dly.push_front_pop_back(z_out_i);

    % angle_map delay line
    am_dly = xl_state(zeros(1, lat), angle_map_i, lat);
    angle_map = am_dly.back;
    am_dly.push_front_pop_back(angle_map_i);

    sz_dly = xl_state(zeros(1, lat), sgn_z_i, lat);
    sgn_z = sz_dly.back;
    sz_dly.push_front_pop_back(sgn_z_i);
  else
    z_out = z_out_i;
    angle_map = angle_map_i;
    sgn_z = sgn_z_i;
  end


