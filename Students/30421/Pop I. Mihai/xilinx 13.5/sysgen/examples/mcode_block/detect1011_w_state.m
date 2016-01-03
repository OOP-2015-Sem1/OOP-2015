function matched = detect1011_w_state(din)
% This is the detect1011 function with states for detecting a
% pattern of 1011.

  seen_none = 0; % initial state, if input is 1, switch to seen_1
  seen_1 = 1;    % first 1 has been seen, if input is 0, switch
                 % seen_10
  seen_10 = 2;   % 10 has been detected, if input is 1, switch to
                 % seen_1011
  seen_101 = 3;  % now 101 is detected, is input is 1, 1011 is
                 % detected and the FSM switches to seen_1


  % the state is a 2-bit register
  persistent state, state = xl_state(seen_none, {xlUnsigned, 2, 0});

  % the default value of matched is false
  matched = false;

  switch state
   case seen_none
    if din==1
      state = seen_1;
    else
      state = seen_none;
    end
   case seen_1 % seen first 1
    if din==1
      state = seen_1;
    else
      state = seen_10;
    end
   case seen_10 % seen 10
    if din==1
      state = seen_101;
    else
      % no part of sequence seen, go to seen_none
      state = seen_none;
    end
   case seen_101
    if din==1
      state = seen_1;
      matched = true;
    else
      state = seen_10;
      matched = false;
    end
  end
