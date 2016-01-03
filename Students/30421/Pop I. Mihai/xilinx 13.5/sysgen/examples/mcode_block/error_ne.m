function eq = error_ne(a, b, report, mod)
  persistent cnt, cnt = xl_state(0, {xlUnsigned, 16, 0});
  switch mod
   case 1
    eq = a==b;
   case 2
    eq = isnan(a) || isnan(b) || a == b;
   case 3
    eq = ~isnan(a) && ~isnan(b) && a == b;
   otherwise
    eq = false;
    error(['wrong value of mode ', num2str(mod)]);
  end

  if report
    if ~eq
      error(['two inputs are not equal at time ', num2str(cnt)]);
    end
  end

  cnt = cnt + 1;

