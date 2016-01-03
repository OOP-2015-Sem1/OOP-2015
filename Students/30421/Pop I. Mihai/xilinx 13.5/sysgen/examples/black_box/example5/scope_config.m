
function scope_config(this_block)

  simulink_block = this_block.blockName;

  nports = eval(get_param(simulink_block,'nports'));

  this_block.setTopLevelLanguage('VHDL');

  entityName = sprintf('scope%d',nports);

  this_block.setEntityName(entityName);
  this_block.addFile(['vhdl/' entityName '.vhd']);
  this_block.tagAsCombinational;

  for i=1:nports
    this_block.addSimulinkInport(sprintf('sig%d',i));
  end

  % -----------------------------
  if (this_block.inputTypesKnown)
    for i=1:nports
      width = this_block.inport(i).width;
      this_block.addGeneric(sprintf('width%d',i),width);
    end
  end  % if(inputTypesKnown)
  % -----------------------------
   
return;
