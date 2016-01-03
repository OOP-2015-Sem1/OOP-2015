function q = xl_accum(b, rst, load, en, nbits, ov, op, feed_back_down_scale)
% q = xl_accum(b, rst, nbits, ov, op, feed_back_down_scale) is
% equivalent to our Accumulator block.

  binpt = xl_binpt(b);
  init = 0;

  precision = {xlSigned, nbits, binpt, xlTruncate, ov};

  persistent s, s = xl_state(init, precision);

  q = s;

  if rst
    if load
      % reset from the input port
      s = b;
    else
      % reset from zero
      s = init;
    end
  else
    if ~en
    else
      % if enabled, update the state
      if op==0
        s = s/feed_back_down_scale + b;
      else
        s = s/feed_back_down_scale - b;
      end
    end
  end

