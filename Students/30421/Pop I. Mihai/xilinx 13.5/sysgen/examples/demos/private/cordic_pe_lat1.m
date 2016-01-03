function [x_out, y_out, z_out] = cordic_pe_lat1(x, y, z, shbits, epsilon, ...
                                                nbits, binpt, lat)

  round_proto = {xlSigned, nbits, binpt, xlRound, xlWrap};
  epsilon = xfix(round_proto, epsilon);

  proto = {xlSigned, nbits, binpt};

  persistent xp; xp = xl_state(0, proto);
  persistent yp; yp = xl_state(0, proto);
  persistent zp; zp = xl_state(0, proto);

  x_out = xp;
  y_out = yp;
  z_out = zp;

  x = xfix(round_proto, x);
  y = xfix(round_proto, y);

  shift_x = xfix(proto, xl_rsh(x, shbits));
  shift_y = xfix(proto, xl_rsh(y, shbits));

  if xl_slice(z, nbits-1, nbits-1)==1
    x_internal = x + shift_y;
    y_internal = y - shift_x;
    z_internal = z + epsilon;
  else
    x_internal = x - shift_y
    y_internal = y + shift_x
    z_internal = z - epsilon;
  end

  xp = x_internal;
  yp = y_internal;
  zp = z_internal;



