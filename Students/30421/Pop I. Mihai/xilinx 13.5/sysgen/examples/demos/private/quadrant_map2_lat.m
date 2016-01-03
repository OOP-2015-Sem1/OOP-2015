function [x_out, y_out, sgn_y, sgn_x] = quadrant_map2_lat(x_in, y_in, nbits, binpt, lat)
  proto = {xlSigned, nbits, binpt};

  sgn_x_i = x_in < 0;
  sgn_y_i = y_in < 0;

  if sgn_x_i
    x = -x_in;
  else
    x = x_in;
  end

  x = xfix(proto, x);

  y = y_in;


  persistent x_dly;
  persistent y_dly;
  persistent sx_dly;
  persistent sy_dly;

  if lat > 0
    x_dly = xl_state(zeros(1, lat), x, lat);
    x_out = x_dly.back;
    x_dly.push_front_pop_back(x);

    y_dly = xl_state(zeros(1, lat), y, lat);
    y_out = y_dly.back;
    y_dly.push_front_pop_back(y);

    sx_dly = xl_state(zeros(1, lat), sgn_x_i, lat);
    sgn_x = sx_dly.back;
    sx_dly.push_front_pop_back(sgn_x_i);

    sy_dly = xl_state(zeros(1, lat), sgn_y_i, lat);
    sgn_y = sy_dly.back;
    sy_dly.push_front_pop_back(sgn_y_i);
  else
    x_out = x;
    y_out = y;
    sgn_x = sgn_x_i;
    sgn_y = sgn_y_i;
  end


