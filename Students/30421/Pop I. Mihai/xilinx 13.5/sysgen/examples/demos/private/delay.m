function q = delay(d, lat)
  persistent r;

  if lat > 0
    r = xl_state(zeros(1, lat), d, lat);
    q = r.back;
    r.push_front_pop_back(d);
  else
    q = d;
  end

