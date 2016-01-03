function x = testdisp(a, b)
  persistent dly, dly = xl_state(zeros(1, 8), a);
  persistent rom, rom = xl_state([3, 2, 1, 0], a);
  
  disp('Hello World!');
  disp(['num2str(dly) is ', num2str(dly)]);
  disp('disp(dly) is ');
  disp(dly);
  disp('disp(rom) is ');
  disp(rom);
  a2 = dly.back;
  dly.push_front_pop_back(a);
  
  x = a + b;
  
  disp(['a = ', num2str(a), ', ', ...
        'b = ', num2str(b), ', ', ...
        'x = ', num2str(x)]);

  disp(num2str(true));

  disp('disp(10) is');
  disp(10);
  disp('disp(-10) is');
  disp(-10);

  disp('disp(a) is ');
  disp(a);

  disp('disp(a == b)');
  disp(a==b);
