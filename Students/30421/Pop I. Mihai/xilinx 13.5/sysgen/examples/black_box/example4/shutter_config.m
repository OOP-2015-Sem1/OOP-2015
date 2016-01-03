
function shutter_config(this_block)

  this_block.setTopLevelLanguage('Verilog');

  this_block.setEntityName('shutter');

  this_block.tagAsCombinational;

  this_block.addSimulinkInport('din');
  this_block.addSimulinkInport('latch');

  this_block.addSimulinkOutport('dout');

  if (this_block.inputRatesKnown | this_block.inputTypesKnown)
      din = this_block.inport(1);
      dout = this_block.outport(1);
  end
  
  % -----------------------------
  if (this_block.inputTypesKnown)
    if (~strcmp(this_block.port('latch').type, 'Bool'))
      this_block.setError ...
	  ('The latch input must be driven with a Bool.')
    end

    this_block.port('latch').useHDLVector(false);

    dwidth = din.width;

    dout.setType(din.type);

    this_block.addGeneric('din_width',dwidth);
    this_block.addGeneric('dout_width',dwidth);

  end  % if(inputTypesKnown)
  % -----------------------------


  % -----------------------------
   if (this_block.inputRatesKnown)
     inputRates = this_block.inputRates; 
     uniqueInputRates = unique(inputRates); 
     if (length(uniqueInputRates)==1 & uniqueInputRates(1)==Inf) 
       this_block.setError ...
	   ('The inputs to this block cannot all be constant.'); 
       return; 
     end 
     if (uniqueInputRates(end) == Inf) 
       hasConstantInput = true; 
       uniqueInputRates = uniqueInputRates(1:end-1); 
     end 
     if (length(uniqueInputRates) ~= 1) 
       this_block.setError ...
	   ('The inputs to this block must run at a single rate.'); 
       return; 
     end 
     theInputRate = uniqueInputRates(1); 
     this_block.addClkCEPair('clk','ce',theInputRate); 

     dout.setRate(1); 

   end  % if(inputRatesKnown)
  % -----------------------------

  this_block.addFile('shutter.v');
