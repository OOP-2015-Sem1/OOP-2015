
function lowpass_filter_config(this_block)

  this_block.setTopLevelLanguage('VHDL');
  this_block.setEntityName('lowpass_filter');

  this_block.addSimulinkInport('sample_in');
  this_block.addSimulinkOutport('sample_out');

  sample_out_port = this_block.port('sample_out');
  sample_out_port.setType('UFix_25_0');

  % -----------------------------
  if (this_block.inputTypesKnown)
    % do input type checking, dynamic output type and generic setup in this code block.

    if (this_block.port('sample_in').width ~= 8);
      this_block.setError('Input data type for port "sample_in" must have width=8.');
    end

  end  % if(inputTypesKnown)
  % -----------------------------

  % -----------------------------
   if (this_block.inputRatesKnown)
     setup_as_single_rate(this_block,'clk','ce')
   end  % if(inputRatesKnown)
  % -----------------------------


  % Filter is contained in standalone NGC file.
  this_block.addFile('lowpass_filter.ngc');

return;


% ------------------------------------------------------------

function setup_as_single_rate(block,clkname,cename)
  inputRates = block.inputRates;
  uniqueInputRates = unique(inputRates);
  if (length(uniqueInputRates)==1 & uniqueInputRates(1)==Inf)
    block.setError('The inputs to this block cannot all be constant.');
    return;
  end
  if (uniqueInputRates(end) == Inf)
     hasConstantInput = true;
     uniqueInputRates = uniqueInputRates(1:end-1);
  end
  if (length(uniqueInputRates) ~= 1)
    block.setError('The inputs to this block must run at a single rate.');
    return;
  end
  theInputRate = uniqueInputRates(1);
  for i = 1:block.numSimulinkOutports
     block.outport(i).setRate(theInputRate);
  end
  block.addClkCEPair(clkname,cename,theInputRate);
  return;

% ------------------------------------------------------------

