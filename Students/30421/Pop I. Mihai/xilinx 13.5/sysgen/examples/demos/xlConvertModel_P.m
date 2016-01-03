function result = xlConvertModel_P();

srcModel = 'sysgenFloatingPointIIR_DFormII';
result = false;
fltptfilter = 0;
load_system(srcModel);
mdlhandle = get_param(srcModel,'handle');
GatewayInList = find_system(mdlhandle,'SearchDepth',2,'block_type','gatewayin');
for i = 1:length(GatewayInList),
    arithtype = get_param(GatewayInList(i),'arith_type');
    if strcmp(arithtype,'Floating-point')
        fltptfilter = 1;
        set_param(GatewayInList(i),'gui_display_data_type','Fixed-point',...
                                   'arith_type','Signed  (2''s comp)',...
                                   'n_bits','32',...
                                   'bin_pt','24');
    else
        set_param(GatewayInList(i),'gui_display_data_type','Floating-point',...
                                   'arith_type','Floating-point',...
                                   'preci_type','Custom',...
                                   'exp_width','6',...
                                   'frac_width','9');
    end;
end;

if (fltptfilter == 1)
    updateBlocksFltptToFxdpt(mdlhandle);
else
    updateBlocksFxdptToFltpt(mdlhandle);
end;

msgbox('Now press Ctrl+D to propagate data-types in the updated model','Conversion complete!!!','modal');

result = true;

return;

function result = updateBlocksFxdptToFltpt(mdlhandle);

ConstantList = find_system(mdlhandle,'SearchDepth',2,'block_type','constant');
for i = 1:length(ConstantList),
    set_param(ConstantList(i),'gui_display_data_type','Floating-point',...
                              'arith_type','Floating-point',...
                              'preci_type','Custom',...
                              'exp_width','6',...
                              'frac_width','9');
end;

AddSubList = find_system(mdlhandle,'SearchDepth',2,'block_type','addsub');
for i = 1:length(AddSubList),
    set_param(AddSubList(i),'precision','Full');
end;

MultList = find_system(mdlhandle,'SearchDepth',2,'block_type','mult');
for i = 1:length(MultList),
    set_param(MultList(i),'precision','Full');
end;

ah = find_system(gcs, 'FindAll', 'on', 'type', 'annotation', 'Text', 'Fixed-point IIR Filter Direct-Form II Implementation');
if ~isempty(ah)
    for i = 1:length(ah)
        set_param(ah(i), 'Text', 'Floating-point IIR Filter Direct-Form II Implementation');
    end;
end

result = true;

return;

function result = updateBlocksFltptToFxdpt(mdlhandle);

ConstantList = find_system(mdlhandle,'SearchDepth',2,'block_type','constant');
for i = 1:length(ConstantList),
    set_param(ConstantList(i),'gui_display_data_type','Fixed-point',...
                              'arith_type','Signed (2''s comp)',...
                              'n_bits','32',...
                              'bin_pt','24');
end;

AddSubList = find_system(mdlhandle,'SearchDepth',2,'block_type','addsub');
for i = 1:length(AddSubList),
    set_param(AddSubList(i),'precision','User Defined',...
                            'arith_type','Signed  (2''s comp)',...
                            'n_bits','32',...
                            'bin_pt','24');
end;

MultList = find_system(mdlhandle,'SearchDepth',2,'block_type','mult');
for i = 1:length(MultList),
    set_param(MultList(i),'precision','User Defined',...
                          'arith_type','Signed  (2''s comp)',...
                          'n_bits','32',...
                          'bin_pt','24');
end;

ah = find_system(gcs, 'FindAll', 'on', 'type', 'annotation', 'Text', 'Floating-point IIR Filter Direct-Form II Implementation');
if ~isempty(ah)
    for i = 1:length(ah)
        set_param(ah(i), 'Text', 'Fixed-point IIR Filter Direct-Form II Implementation');
    end;
end

result = true;

return;
