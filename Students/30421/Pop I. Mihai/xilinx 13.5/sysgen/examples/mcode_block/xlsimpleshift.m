function [lsh3, rsh2] = xlsimpleshift(din)
% [lsh3, rsh2] = xlsimpleshift(din) does a left shift 3 bits and a
% right shift 2 bits. The shift operation is accomplished by
% multiplication and division of power of two constant.
  lsh3 = din * 8;
  rsh2 = din / 4;
