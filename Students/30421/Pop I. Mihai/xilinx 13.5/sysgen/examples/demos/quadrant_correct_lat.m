function [cosine, sine] = quadrant_correct_lat(cos, sin, angle_map, ...
                                               sgn_z, nbits, binpt, ...
                                               lat)

  proto = {xlSigned, nbits, binpt};

  if angle_map
    if sgn_z
      cosine_i = sin;
      sine_i = -cos;
    else
      cosine_i =  -sin;
      sine_i = cos;
    end
  else
    cosine_i = cos;
    sine_i = sin;
  end

  cosine_i = xfix(proto, cosine_i);
  sine_i = xfix(proto, sine_i);

  persistent c_dly;
  persistent s_dly;

  if lat > 0
    c_dly = xl_state(zeros(1, lat), cosine_i, lat);
    s_dly = xl_state(zeros(1, lat), sine_i, lat);

    cosine = c_dly.back;
    c_dly.push_front_pop_back(cosine_i);

    sine = s_dly.back;
    s_dly.push_front_pop_back(sine_i);
  else
    cosine = cosine_i;
    sine = sine_i;
  end

