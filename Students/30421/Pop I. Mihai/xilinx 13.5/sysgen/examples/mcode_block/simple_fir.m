function y = simple_fir(x, lat, coefs, len, c_nbits, c_binpt, o_nbits, o_binpt)
  coef_prec = {xlSigned, c_nbits, c_binpt, xlRound, xlWrap};
  out_prec = {xlSigned, o_nbits, o_binpt};
  
  coefs_xfix = xfix(coef_prec, coefs);

  persistent coef_vec, coef_vec = xl_state(coefs_xfix, coef_prec);
  persistent x_line, x_line = xl_state(zeros(1, len-1), x);
  persistent p, p = xl_state(zeros(1, lat), out_prec, lat);
  
  sum = x * coef_vec(0);

  for idx = 1:len-1
    sum = sum + x_line(idx-1) * coef_vec(idx);
    sum = xfix(out_prec, sum);
  end
  
  y = p.back;
  p.push_front_pop_back(sum);
  x_line.push_front_pop_back(x);
  
