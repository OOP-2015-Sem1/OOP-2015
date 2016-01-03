% This function calculates the Next State and Output matrices for the combination
% lock example given a given binary string sequence.
%
% example: calcLockMatrices('0110111')
%          results:
%            next_state_matrix = [1 0; 1 2; 1 3; 4 0; 1 5; 1 6; 4 7; 1 0]
%            output_matrix = [0 0; 0 0; 0 0; 0 0; 0 0; 0 0; 0 0; 1 0]

function [next_state_matrix, output_matrix] = calcLockMatrices(seq)

seq_len = length(seq);
next_state_matrix = zeros(seq_len+1,2);
output_matrix = zeros(seq_len+1,2);

% Varaible used to keep track of the sequence seen
seq_seen = '';
seq_seen_vec = {''};

% Create next State matrix by going through all the states
for i = 1:seq_len

   % Set the next state for the case when we get the bit that we're looking for.
   bit = str2num(seq(i));
   next_state_matrix(i,bit+1) = i;

   %  Set the next state for the case when we get a bit that we're NOT looking for
   bit = not(bit);
   seq_got = strcat(seq_seen, num2str(bit));

   % Search beckwards through the previous sequences seen for a part of string
   %  that we actually got
   for j = i-1:-1:1
      search_for = seq_got(i-j+1:i);
                if (strcmp(seq_seen_vec{j}, search_for) == 1)
         next_state_matrix(i,bit+1) = j;
         break;
      end;
   end;

   % Save the sequence seen in seq_seen_vec
   seq_seen = strcat(seq_seen, seq(i));
   seq_seen_vec{i} = seq_seen;

end;

% Set last state of next state matrix to the same at the first state
next_state_matrix(seq_len+1,1) = next_state_matrix(1,1);
next_state_matrix(seq_len+1,2) = next_state_matrix(1,2);


% Set Output matrix
%  if input is opposite the last bit in the sequence set the output to be 1
if seq(seq_len) == '1'
     output_matrix(seq_len+1,1) = 1;
   output_matrix(seq_len+1,2) = 0;
else
   output_matrix(seq_len+1,1) = 0;
   output_matrix(seq_len+1,2) = 1;
end;
