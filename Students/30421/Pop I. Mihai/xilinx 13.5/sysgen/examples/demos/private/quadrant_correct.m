function [cosine, sine] = quadrant_correct(cos, sin, angle_map, ...
                                            sgn_z, nbits, binpt)

  proto = {xlSigned, nbits, binpt};

  if angle_map
    if sgn_z
      cosine = sin;
      sine = -cos;
    else
      cosine =  -sin;
      sine = cos;
    end
  else
    cosine = cos;
    sine = sin;
  end

  cosine = xfix(proto, cosine);
  sine = xfix(proto, sine);

