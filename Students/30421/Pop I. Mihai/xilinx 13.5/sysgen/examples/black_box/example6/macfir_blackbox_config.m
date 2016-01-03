
function macfir_blackbox_config(this_block)

  this_block.setTopLevelLanguage('VHDL');

  this_block.setEntityName('macfir_blackbox');

  this_block.addFile('macfir_core.edn');
  this_block.addFile('COEF_BUFFER.mif');
  this_block.addFile('macfir_core.mif');
  this_block.addFile('macfir_core.vhd');
  this_block.addFile('macfir_blackbox.vhd');

  % this_block.tagAsCombinational;  -- the MAC FIR has no combinational path


  this_block.addSimulinkInport('din');
  this_block.addSimulinkInport('reset');
  
  this_block.addSimulinkOutport('dout');


  dout_port = this_block.port('dout');
  dout_port.setType('Fix_39_32');


  % -----------------------------
  if (this_block.inputTypesKnown)
    if (this_block.port('reset').width ~= 1);
      this_block.setError('Input data type for port "reset" must have width=1.');
    end

    this_block.port('reset').useHDLVector(false);

    if (this_block.port('din').width ~= 16);
      this_block.setError('Input data type for port "din" must have width=16.');
    end
  end  % if(inputTypesKnown)
  % -----------------------------


  % -----------------------------
   if (this_block.inputRatesKnown)

       din_port = this_block.port('din');
       rst_port = this_block.port('reset');
       inputRate = din_port.rate;

       if (inputRate == Inf)
	 this_block.setError(['The "din" port of the MAC FIR cannot' ...
		    ' be driven with a constant.']);
       end
       if (rst_port.rate~=inputRate & rst_port.rate~=Inf) 
	 this_block.setError(['The "reset" port of the MAC FIR must either' ...
 		    ' be driven at the "din" input rate or with a constant.']);
       end
       
       this_block.addClkCEPair('input_clk','input_ce',inputRate); 
       
       outputRate = 8*inputRate;
       dout_port.setRate(outputRate); 
       this_block.addClkCEPair('output_clk','output_ce',outputRate); 

   end  % if(inputRatesKnown)
  % -----------------------------


return;

