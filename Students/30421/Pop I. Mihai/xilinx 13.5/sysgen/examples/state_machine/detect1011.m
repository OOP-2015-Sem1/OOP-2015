function [nextState, matched] = detect1011(currentState, din)
% This is the update function for detecting a pattern of 1011
% This function represents a stateless and combination logic block.
% The output nextState should be to a register, and the output
% of the register should be fed back to the input currentState.
% Because the state register block has a feed back connection, in
% order to propagate the port prototypes automatically, the prototype
% of nextState should be determined only by constants or through
% an explicity xfix() call.

  seen_none = 0; % initial state, if input is 1, switch to seen_1
  seen_1 = 1;    % first 1 has been seen, if input is 0, switch
                 % seen_10
  seen_10 = 2;   % 10 has been detected, if input is 1, switch to
                 % seen_1011
  seen_101 = 3;  % now 101 is detected, is input is 1, 1011 is
                 % detected and the FSM switches to seen_1

  % the default value of matched is false
  matched = false;

  switch currentState
   case seen_none
    if din==1
      nextState = seen_1;
    else
      nextState = seen_10;
    end
   case seen_1 % seen first 1
    if din==1
      nextState = seen_1;
    else
      nextState = seen_10;
    end
   case seen_10 % seen 10
    if din==1
      nextState = seen_101;
    else
      % no part of sequence seen, go to seen_none
      nextState = seen_none;
    end
   case seen_101
    if din==1
      nextState = seen_1;
      matched = true;
    else
      nextState = seen_10;
      matched = false;
    end
   otherwise
    % if nextState is not assigned outside the switch statement,
    % an otherwise statment is required
    nextState = seen_none;
  end




