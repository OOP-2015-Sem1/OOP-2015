%Assert that width and period of input signal are within range
%  din             Input signal
%  fire            Assertion failure fire output (currently irrelavent as assertion failure always stops simulation)
%  MIN_WIDTH       Minimum width (0 for none)
%  MAX_WIDTH       Maximum width (0 for none)
%  MIN_PERIOD      Minimum period (0 for none)
%  MAX_PERIOD      Maximum period (0 for none)
%  MAX_CNT         Maximum value for counter
%
function [fire] = mfcm_assert_width_period(din,MIN_WIDTH,MAX_WIDTH,MIN_PERIOD,MAX_PERIOD,MAX_CNT)

	persistent din0, din0=xl_state(0,din);
	persistent run0, run0=xl_state(0,{xlBoolean});
	persistent cnt0, cnt0=xl_state(0,MAX_CNT);

	fire=false;
	%Falling edge of din
	if (~din && din0)
		if (run0 && MIN_WIDTH>0 && cnt0<MIN_WIDTH-1)
			error('ASSERTION FAILURE:Minimum width violated');
			fire=true;
		end
		if (run0 && MAX_WIDTH>0 && cnt0>MAX_WIDTH-1)
			error('ASSERTION FAILURE:Maximum width violated');
			fire=true;
		end
	end

	%Rising edge of din
	if (din && ~din0)
		if (run0 && MIN_PERIOD>0 && cnt0<MIN_PERIOD-1)
			error('ASSERTION FAILURE:Minimum period violated');
			fire=true;
		end
		if (run0 && MAX_PERIOD>0 && cnt0>MAX_PERIOD-1)
			error('ASSERTION FAILURE:Maximum period violated');
			fire=true;
		end

		run0=true;
		cnt0=0;
	else
		if (cnt0<MAX_CNT)
			cnt0=cnt0+1;
		end
	end

	din0=din;
