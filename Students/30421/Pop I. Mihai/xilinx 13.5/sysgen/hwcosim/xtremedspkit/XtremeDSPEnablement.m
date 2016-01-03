function keysEnablementsStruct = XtremeDSPEnablement(blockHandle, keysValuesStruct)

% Construct return value with all controls enabled.
keys = fieldnames(keysValuesStruct);
for i = 1:length(keys)
    keysEnablementsStruct.(keys{i}) = 'on';
end

if (isfield(keysValuesStruct,'clkSrc'))
    if strcmp (keysValuesStruct.('clkSrc'), 'Single Stepped')
        keysEnablementsStruct.('prgClkFreq') = 'off';
    end
end

if (isfield(keysValuesStruct,'busType'))
    if strcmp (keysValuesStruct.('busType'), 'USB')
        keysEnablementsStruct.('clkSrc') = 'off';
    end
end
