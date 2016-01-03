function cnt = counter(reset, init)
  persistent r, r = xl_state(init, {xlUnsigned, 2, 0});

  cnt = r;

  if reset
    r = init;
  else 
    r = r + 1;
  end
  
