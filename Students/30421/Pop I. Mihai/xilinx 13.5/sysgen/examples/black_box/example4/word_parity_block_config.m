
function word_parity_block_config(this_block)

  this_block.setTopLevelLanguage('VHDL');

  this_block.setEntityName('word_parity_block');

  this_block.tagAsCombinational;

  this_block.addSimulinkInport('din');

  this_block.addSimulinkOutport('parity');

  % -----------------------------
  if (this_block.inputTypesKnown)
    parity = this_block.port('parity');
    parity.setWidth(1);
    parity.useHDLVector(false);
    this_block.addGeneric('width',this_block.port('din').width);
  end  % if(inputTypesKnown)
  % -----------------------------

  % -----------------------------
   if (this_block.inputRatesKnown)
     parity = this_block.port('parity');
     din = this_block.port('din');
     parity.setRate(din.rate);
   end  % if(inputRatesKnown)
  % -----------------------------

  this_block.addFile('word_parity_block.vhd');

return;
