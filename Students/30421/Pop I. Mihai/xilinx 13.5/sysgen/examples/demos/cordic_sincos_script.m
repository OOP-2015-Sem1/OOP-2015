function cordic_sincos_script(pe_stages, pe_nbits, pe_binpt)
 


    z = xInport('z');

    cos_port = xOutport('cos');
    sin_port = xOutport('sin');
    
    map_z = xSignal;
    angle_map = xSignal;
    sgn_z = xSignal;
    
    xBlock(@quandrant_map, ...
           {pe_nbits, pe_binpt}, ...
           {z}, {map_z, angle_map, sgn_z});
           
    xi = const_blk(pe_nbits, pe_binpt, 1/1.646760);
    yi = const_blk(pe_nbits, pe_binpt, 0);
    
    pe_xo = xSignal;
    pe_yo = xSignal;
    
    xBlock(@fine_angle_pe, ...
           {pe_stages, pe_nbits, pe_binpt}, ...
           {xi, yi, map_z}, ...
           {pe_xo, pe_yo});

    angle_map_dly = dly_blk(angle_map, pe_stages + 1);
    sgn_z_dly = dly_blk(sgn_z, pe_stages + 2);
    
    xBlock(@quandrant_correct, ...
           {pe_nbits, pe_binpt}, ...
           {pe_xo, pe_yo, angle_map_dly, sgn_z_dly}, ...
           {cos_port, sin_port});
    
    
function quandrant_correct(pe_nbits, pe_binpt)
% there is a bug that ports are added only when connecting
    c = xInport('c');
    s = xInport('s');
    angle_map = xInport('angle_map');
    sgn_z = xInport('sgn_z');

    cosine = xOutport('cosine');
    sine = xOutport('sine');

    neg_s = cordic_negate(s, pe_nbits, pe_binpt);
    mux1 = mux_blk(0, sgn_z, neg_s, s);

    neg_c = cordic_negate(c, pe_nbits, pe_binpt);
    mux2 = mux_blk(0, sgn_z, c, neg_c);

    cosine.assign(mux_blk(1, angle_map, c, mux1));
    sine.assign(mux_blk(1, angle_map, s, mux2));

function s = cordic_negate(d, nbits, binpt)
    s = xSignal;
    xBlock('Negate', ...
           struct('precision', 'User Defined', ...
                  'arith_type','Signed  (2''s comp)', ...
                  'n_bits', nbits, ...
                  'bin_pt', binpt), ...
           {d}, {s});

function quandrant_map(pe_nbits, pe_binpt)
    z = xInport('z');

    zo = xOutport('zo');
    angle_map = xOutport('angle_map');
    sgn_z = xOutport('sgn_z');
    
    half_pi = const_blk(pe_nbits, pe_binpt, pi / 2);
    neg_half_pi = const_blk(pe_nbits, pe_binpt, -pi / 2);
    
    z_gt_half_pi = rel_blk(z, half_pi, '>', 1);
    z_lt_neg_half_pi = rel_blk(z, neg_half_pi, '<', 1);
    
    sel = logi_blk(z_gt_half_pi, z_lt_neg_half_pi, 'OR');
    
    z_dly_1 = dly_blk(z, 1);
    
    is_neg_z = sgn_blk(z);
    is_pos_z = not_blk(is_neg_z);

    addsub_sig = pe_addsub(pe_nbits, pe_binpt, 1, z, half_pi, is_pos_z);

    zo.assign(mux_blk(1, sel, z_dly_1, addsub_sig));

    angle_map.assign(logi_blk(z_gt_half_pi, z_lt_neg_half_pi, 'OR'));
    sgn_z.assign(sgn_blk(z));
    
function mux_to_port(varargin)
    n_inputs = nargin - 2;
    xBlock('Mux', ...
           struct('inputs', n_inputs, ...
                  'latency', 1), ...
           varargin(1:nargin-1), ...
           varargin(nargin));
    
function q = mux_blk(latency, varargin)
    q = xSignal;
    n_inputs = nargin - 2;
    xBlock('Mux', ...
           struct('inputs', n_inputs, 'latency', latency), ...
           varargin(:), ...
           {q});
    
function q = dly_blk(d, latency)
    q = xSignal;
    xBlock('Delay', ...
           struct('latency', latency), ...
           {d}, {q});
    
function s = logi_blk(a, b, op)
    s = xSignal;
    xBlock('Logical', ...
           struct('logical_function', op, ...
                  'inputs', 2), ...
           {a, b}, {s});

function logi_to_port(a, b, op, s)
    xBlock('Logical', ...
           struct('logical_function', op, ...
                  'inputs', 2), ...
           {a, b}, {s});

    
function s = rel_blk(a, b, op, latency)
    s = xSignal;
    xBlock('Relational', ...
           struct('mode', ['a', op, 'b'], ...
                  'latency', latency), ...
           {a, b}, {s});

function fine_angle_pe(stages, pe_nbits, pe_binpt)
    xi = xInport;
    yi = xInport;
    zi = xInport;

    xo = xOutport;
    yo = xOutport;

    if stages <= 0
        error(['stages must be positive']);
    end

    % todo, when assign is ready, fix the following code

    for i = 1:stages
        xoi = xSignal;
        yoi = xSignal;
        zoi = xSignal;

        xBlock(@cordic_pe, ...
               {i-1, pe_nbits, pe_binpt, 'atan', 1}, ...
               {xi, yi, zi}, ...
               {xoi, yoi, zoi});
        xi = xoi;
        yi = yoi;
        zi = zoi;
    end

    xo.assign(xi);
    yo.assign(yi);

    xBlock('Terminator', {}, {zi}, {});

    
function cordic_pe(ii, pe_nbits, pe_binpt, epsilon_k_fcn, pipeline)
    switch epsilon_k_fcn
     case 'atan'
      epsilon_k = atan(1/2^ii);
     case 'atanh'
      epsilon_k = atanh(1/2^ii);
     otherwise
      error('epsilon_k_fcn must be either atan or atanh');
    end
    
    epsilon_const = const_blk(pe_nbits, pe_binpt, epsilon_k);
    
    xi = xInport('xi');
    
    yi = xInport('yi');
    zi = xInport('zi');
      
    xo = xOutport('xo');
    yo = xOutport('yo');
    zo = xOutport('zo');

    sh_xi = right_shift(ii, pe_nbits, pe_binpt, xi);
    sh_yi = right_shift(ii, pe_nbits, pe_binpt, yi);

    is_neg_z = sgn_blk(zi);
    is_pos_z = not_blk(is_neg_z);

    xo.assign(pe_addsub(pe_nbits, pe_binpt, pipeline, ...
                        xi, sh_yi, is_pos_z));
    yo.assign(pe_addsub(pe_nbits, pe_binpt, pipeline, ...
                        yi, sh_xi, is_neg_z));
    zo.assign(pe_addsub(pe_nbits, pe_binpt, pipeline, ...
                        zi, epsilon_const, is_pos_z));
    
function s = pe_addsub(nbits, binpt, pipeline, a, b, sub)
    s = xSignal;
    xBlock('AddSub', ...
           struct('mode', 'Addition or Subtraction', ...
                  'latency', pipeline, ...
                  'precision', 'User Defined', ...
                  'arith_type', 'Signed  (2''s comp)', ...
                  'n_bits', nbits, ...
                  'bin_pt', binpt, ...
                  'quantization', 'Truncate', ...
                  'overflow', 'Wrap'), ...
           {a, b, sub}, ...
           {s});
    
function q = right_shift(shbits, nbits, binpt, d)
    q = xSignal;
    
    xBlock('Shift', ...
           struct('shift_dir', 'Right', ...
                  'shift_bits', shbits, ...
                  'precision', 'User Defined', ...
                  'arith_type', 'Signed  (2''s comp)', ...
                  'n_bits', nbits, ...
                  'bin_pt', binpt), ...
           {d}, ...
           {q});

function c = const_blk(nbits, binpt, value)
    c = xSignal;
    
    xBlock('Constant', ...
           struct('arith_type', 'Signed (2''s comp)', ...
                  'n_bits', nbits, ...
                  'bin_pt', binpt, ...
                  'const', value), ...
           {}, ...
           {c});
    
function s = sgn_blk(d)
    s = xSignal;
    xBlock('Slice', ...
           struct('boolean_output', 'on', ...
                  'mode', 'Upper Bit Location + Width', ...
                  'bit1', 0, ...
                  'base1', 'MSB of Input'), ...
           {d}, ...
           {s});

function sgn_to_port(d, s)
    xBlock('Slice', ...
           struct('boolean_output', 'on', ...
                  'mode', 'Upper Bit Location + Width', ...
                  'bit1', 0, ...
                  'base1', 'MSB of Input'), ...
           {d}, ...
           {s});

function q = not_blk(d)
    q = xSignal;
    
    xBlock('Inverter', ...
           struct('latency', 0), ...
           {d}, ...
           {q});
                  
    
                  
