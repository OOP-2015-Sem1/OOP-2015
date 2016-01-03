function mfcm_mac_syn_config(this_block)

	%Simulink name of the mfcm_mac block containing this black box
	simulink_block=this_block.blockName;
	simulink_block=simulink_block(1:end-length('/mfcm_mac/mfcm_mac_syn/mac'));

	this_block.setTopLevelLanguage('VHDL');
	this_block.setEntityName('mfcm_mac_syn');

	this_block.addSimulinkInport('a');
	this_block.addSimulinkInport('b');
	this_block.addSimulinkInport('op_acc');
	this_block.addSimulinkInport('op_sub');

	this_block.addSimulinkOutport('x');

	if (this_block.inputTypesKnown)
		%Need to get at block workspace variables (these are actual evaluated values - not the same as dialog box strings)
		ws_values=get_param(simulink_block,'MaskWSVariables');

		if (this_block.port('a').isSigned~=true);
			this_block.setError('Input data type for port "a" must be signed.');
		end

		this_block.addGeneric('A_WIDTH',this_block.port('a').width);

		if (this_block.port('b').isSigned~=true);
			this_block.setError('Input data type for port "b" must be signed.');
		end

		this_block.addGeneric('B_WIDTH',this_block.port('b').width);

		if (this_block.port('op_acc').width~=1);
			this_block.setError('Input data type for port "op_acc" must have width=1.');
		end

		this_block.port('op_acc').useHDLVector(false);

		if (this_block.port('op_sub').width~=1);
			this_block.setError('Input data type for port "op_sub" must have width=1.');
		end

		this_block.port('op_sub').useHDLVector(false);

		ACC_GROWTH=ws_values(find(strcmp({ws_values.Name},'ACC_GROWTH'))).Value;
		X_WIDTH=this_block.port('a').width+this_block.port('b').width+ACC_GROWTH;
		X_BINPT=this_block.port('a').binpt+this_block.port('b').binpt;
		this_block.addGeneric('X_WIDTH',X_WIDTH);

		ACC_DEPTH=ws_values(find(strcmp({ws_values.Name},'ACC_DEPTH'))).Value;
		this_block.addGeneric('X_DEPTH',ACC_DEPTH);

		this_block.port('x').makeSigned;
		this_block.port('x').setWidth(X_WIDTH);
		this_block.port('x').setBinpt(X_BINPT);

		%Check family for multiplier type
		dev=this_block.getDeviceFamilyName();
		MULT_TYPE=-1;

		%Virtex-II derivatives need MULT18X18S based multiplier (latency three)
		if (~isempty(regexpi(dev,'^Virtex2$' ))) MULT_TYPE=0; end
		if (~isempty(regexpi(dev,'^Virtex2P$'))) MULT_TYPE=0; end
		if (~isempty(regexpi(dev,'^Spartan3$'))) MULT_TYPE=0; end

		%Spartan-3E derivatives need MULT18X18SIO based multiplier (latency two)
		if (~isempty(regexpi(dev,'^Spartan3E$'))) MULT_TYPE=1; end

		%Virtex-4 derivatives need DSP48 based multiplier (latency two)
		if (~isempty(regexpi(dev,'^Virtex4$'))) MULT_TYPE=2; end
		if (~isempty(regexpi(dev,'^Virtex5$'))) MULT_TYPE=2; end

		%Check for unrecognised parts
		if (MULT_TYPE<0)
			this_block.setError([dev ' - Unsupported device family.']);
		else
			this_block.addGeneric('MULT_TYPE',MULT_TYPE);
		end
	end

	if (this_block.inputRatesKnown)
		setup_as_single_rate(this_block,'clk','ce');
	end

	this_block.addFile(xilinx.environment.getpath('sysgen', 'hdl', 'mfcm_mac_syn.vhd'));
	return;

function setup_as_single_rate(block,clkname,cename)
	inputRates=block.inputRates;
	uniqueInputRates=unique(inputRates);
	if (length(uniqueInputRates)==1 & uniqueInputRates(1)==Inf)
		block.setError('The inputs to this block cannot all be constant.');
		return;
	end
	if (uniqueInputRates(end)==Inf)
		hasConstantInput=true;
		uniqueInputRates=uniqueInputRates(1:end-1);
	end
	if (length(uniqueInputRates)~=1)
		block.setError('The inputs to this block must run at a single rate.');
		return;
	end
	theInputRate=uniqueInputRates(1);
	for i=1:block.numSimulinkOutports
		block.outport(i).setRate(theInputRate);
	end
	block.addClkCEPair(clkname,cename,theInputRate);
	return;
