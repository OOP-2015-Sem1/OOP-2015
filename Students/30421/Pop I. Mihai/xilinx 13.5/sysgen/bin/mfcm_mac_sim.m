%Simulation model of synthesiable MAC - why must this be so painful....
%
%  a,b          Multiplier inputs
%  acc          Accumulation control
%  sub          Subtraction control
%  res          Result
%
%  ACC_GROWTH   Accumulator bit growth (relative to a*b)
%  ACC_DEPTH    Accumulator pipeline depth (>0)
%
function [res] = mfcm_mac_sim(a,b,acc,sub,ACC_GROWTH,ACC_DEPTH)

	p=a*b;

	persistent res_pipe0, res_pipe0=xl_state(zeros(1,ACC_DEPTH),{xl_arith(p), xl_nbits(p)+ACC_GROWTH, xl_binpt(p)},ACC_DEPTH);
	persistent res_pipe1, res_pipe1=xl_state(zeros(1,4        ),{xl_arith(p), xl_nbits(p)+ACC_GROWTH, xl_binpt(p)},4        );

	if (acc)
		if (sub)
			res0=xfix({xl_arith(p), xl_nbits(p)+ACC_GROWTH, xl_binpt(p)},res_pipe0.back-p);
		else
			res0=xfix({xl_arith(p), xl_nbits(p)+ACC_GROWTH, xl_binpt(p)},res_pipe0.back+p);
		end
	else
		if (sub)
			res0=xfix({xl_arith(p), xl_nbits(p)+ACC_GROWTH, xl_binpt(p)},0-p);
		else
			res0=xfix({xl_arith(p), xl_nbits(p)+ACC_GROWTH, xl_binpt(p)},0+p);
		end
	end

	res=res_pipe1.back;

	res_pipe0.push_front_pop_back(res0);
	res_pipe1.push_front_pop_back(res0);
