function [x_out, y_out, z_out] = cordic_pe(x, y, z, divby, epsilon, nbits, binpt)

  round_proto = {xlSigned, nbits, binpt, xlRound, xlWrap};
  epsilon = xfix(round_proto, epsilon);

  proto = {xlSigned, nbits, binpt};

  x = xfix(round_proto, x);
  y = xfix(round_proto, y);

  if xl_slice(z, nbits-1, nbits-1)==1
    x_out = x + xfix(proto, y / divby);
    y_out = y - xfix(proto, x / divby);
    z_out = z + epsilon;
  else
    x_out = x - xfix(proto, y / divby);
    y_out = y + xfix(proto, x / divby);
    z_out = z - epsilon;
  end

  x_out = xfix(proto, x_out);
  y_out = xfix(proto, y_out);
  z_out = xfix(proto, z_out);



