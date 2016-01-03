function [z1, z2, z3, z4] = xlSimpleArith(a, b)
%xlSimpleArith demonstrated some simple arithmetic operations
% supported by the MCode block. The function uses xfix call
% to create Xilinx fixed point numbers.
%
% A floating point constant must use a xfix call
% to specify type, width, and binary point position.
% By default, the xfix call uses xlTruncate and xlWrap
% for quantization and overflow mode.

  % const1 is UFix_8_3
  const1 = xfix({xlUnsigned, 8, 3}, 1.53);
  % const2 is Fix_10_4
  const2 = xfix({xlSigned, 10, 4, xlRound, xlWrap}, 5.687);

  z1 = a + const1;
  z2 = -b - const2;

  z3 = z1 - z2;
  z3 = xfix({xlSigned, 12, 8, xlTruncate, xlSaturate}, z3);

  % z4 is true if both inputs are positive
  z4 = a>const1 & b>1;
