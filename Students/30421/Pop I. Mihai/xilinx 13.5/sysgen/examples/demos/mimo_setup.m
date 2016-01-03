function [res]=mimo_setup(arg)

	%Only calculate model parameters once
	persistent model;
	if (isempty(model))
		%Get rand/randn states so we can restore
		rand_state=rand('state');
		randn_state=randn('state');

%- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

		%Number of transmit antennas
		model.Mt=4;

		%Number of discrete paths
		model.N=1;

		%Number of receieve antennas
		model.Mr=4;

		%Maximum doppler frequency
		model.Fdmax=500.0;

		%Channel sample rate
		model.Fs=8.192e6;

%- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

		%Set seeds so we get repeatable system
		rand('state',23454326);
		randn('state',45432623);

		%Calculate interpolation rate (256 is fixed interpolation rate of iDFT)
		model.rate=floor(model.Fs/model.Fdmax/256);

		%Actual fdmax derived from rate
		fdmax=model.Fs/model.rate/256;

		[model]=mimo_environment(model);

		%Randomised path gains
		model.gain=(randn(1,model.N)/16+1).*(2.^(-[0:model.N-1]));

		%Convert transport delays in to whole numbers of samples, and normalise against minimum
		model.delay=round(model.delay*model.Fs);
		model.delay=model.delay-min(model.delay);

		%All paths have classic Doppler, and scale for actual Fdmax
		model.spec_type=zeros(model.Mr,model.Mt,model.N)+2;
		model.spec_fd  =zeros(model.Mr,model.Mt,model.N)+(model.Fdmax/fdmax);

		%Calculate spectrum data
		model.spec_data=calc_path_data(mimo_setup('spec_type'),mimo_setup('spec_fd'));

		%Calculate the minimum input period
		model.Tmin=2*model.N*max(max(model.Mt,model.Mr)^2,model.Mt*model.Mr*ceil(64/model.rate));

		%Restore rand/randn states
		rand('state',rand_state);
		randn('state',randn_state);
	end

	%Now return whole model, or only the requested parameter
	if (nargin<1 || isempty(arg))
		res=model;
	else
		res=eval(strcat('model.',arg));
	end

end
