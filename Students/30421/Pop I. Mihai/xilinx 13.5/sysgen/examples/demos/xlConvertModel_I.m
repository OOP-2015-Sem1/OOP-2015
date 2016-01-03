function result = xlConvertModel_I();
%This function helps programmatically switch between Fixed-point
%and Floating-point implementation of the sysgenFloatingPointIIR_DFormII.mdl.

clc;

ret_msg = {['The ''InteractiveConversionModel'' switch helps switch between Fixed-point '];
           ['and Floating-point implementation of the IIR Filter listing different '];
           ['block-GUI configuration changes on the Matlab Konsole.'];
           ['This conversion routine works on predetermined Fixed-point and '];
           ['Floating-point precisions. For Fixed-point model, total bit-width = 32 '];
           ['and binary-point = 24 are used. For Floating-point model, Custom precision '];
           ['number with Exponent-width = 6 and Fraction-width = 9 are used.'];
           [''];
           ['To configure manually -'];
           [''];
           ['1.  Configure ''filter_data_i'' block GUI to specify desired data-type and'];
           ['    precision on the ''Basic'' tab'];
           [''];
           ['2.  Open IIR Filter Subsystem'];
           [''];
           ['3.  Configure NUM1, NUM3, DEN2 and DEN3 block GUI to specify desired'];
           ['    data-type and precision on the ''Basic'' tab'];
           [''];
           ['4.  Specify output precision on AddSub, AddSub1 and AddSub2 block-GUIs'''];
           ['   ''Output'' tab. For Floating-point model, select ''Full'' precision.'];
           ['    For Fixed-point model, select ''User defined'' precision and specify'];
           ['   ''Number of bits'' and ''Binary point'' explicitly - this is to ensure'];
           ['    correct rate and type propagation in feedback system.'];
           [''];
           ['5.  Specify ouput precision on Mult, Mult1, Mult2 and Mult3 block-GUIs'''];
           ['   ''Basic'' tab. For Floating-point model, select ''Full'' precision.'];
           ['    For Fixed-point model, select ''User defined'' precision an specify'];
           ['   ''Number of bits'' and ''Binary point'' explicitly - this is to ensure'];
           ['    correct rate and type propagation in the feedback system.'];
           [''];
           ['Press OK and check MATLAB Konsole to see the steps now.']};
ret_title = 'Interactive Conversion Model Help';
msgbox(ret_msg, ret_title, 'modal');

srcModel = 'sysgenFloatingPointIIR_DFormII';
result = false;
fltptfilter = 0;
load_system(srcModel);
mdlhandle = get_param(srcModel,'handle');

%the model consists of the IIR Filter Subsystem. Depending on the data-type currently
%selected at the SystemGenerator GatewayIn block, switch to Fixed-point or Floating-point
%happens.
GatewayInList = find_system(mdlhandle,'SearchDepth',2,'block_type','gatewayin');
arithtype = get_param(GatewayInList(1),'arith_type');
if strcmp(arithtype,'Floating-point')
    fltptfilter = 1;
    disp(sprintf('Floating-point Filter - current data-type at GatewayIn block : %s\n\n',arithtype));
    disp(sprintf('\n---------------------Proceeding to convert to Fixed-point model-------------------------\n'));

    disp(sprintf('\n------------First changing configuration of the GatewayIn block--------------\n'));

    gatewayinname = get_param(GatewayInList(1),'Name');
    disp(sprintf('\nGatewayIn block ''%s''',gatewayinname));

    set_param(GatewayInList(1),'gui_display_data_type','Fixed-point');
    input(sprintf('On ''Basic'' tab\n\tSelected ''Output Type'' radio-button to ''Fixed-point''. Press any key to continue.'));

    set_param(GatewayInList(1),'arith_type','Signed  (2''s comp)');
    input(sprintf('\tSelected ''Arithmetic Type'' to ''Signed  (2''s comp)''. Press any key to continue.'));

    set_param(GatewayInList(1),'n_bits','32');
    input(sprintf('\tSpecified ''Number of bits'' as ''32''. Press any key to continue.'));

    set_param(GatewayInList(1),'bin_pt','24');
    input(sprintf('\tSpecified ''Binary point'' as ''24''. Press any key to continue.'));
else
    disp(sprintf('Fixed-point Filter - current data-type at GatewayIn block : %s',arithtype));
    disp(sprintf('\n---------------------Proceeding to convert to Floating-point model-------------------------\n'));

    disp(sprintf('\n------------First changing configuration of the GatewayIn block--------------\n'));

    gatewayinname = get_param(GatewayInList(1),'Name');
    disp(sprintf('\nGatewayIn block ''%s''',gatewayinname));

    set_param(GatewayInList(1),'gui_display_data_type','Floating-point');
    input(sprintf('On ''Basic'' tab\n\tSelected ''Output Type'' radio-button to ''Floating-point''. Press any key to continue.'));

    set_param(GatewayInList(1),'arith_type','Floating-point');
    input(sprintf('\tSelected ''Arithmetic Type'' to ''Floating-point''. Press any key to continue.'));

    set_param(GatewayInList(1),'preci_type','Custom');
    input(sprintf('\tSelected ''Floating-point Precision'' radio-button to ''Custom''. Press any key to continue.'));

    set_param(GatewayInList(1),'exp_width','6');
    input(sprintf('\tSpecified ''Exponent width'' as ''6''. Press any key to continue.'));

    set_param(GatewayInList(1),'frac_width','9');
    input(sprintf('\tSpecified ''Fraction width'' as ''9''. Press any key to continue.'));
end;

if (fltptfilter == 1)
    updateBlocksFltptToFxdpt(mdlhandle);
else
    updateBlocksFxdptToFltpt(mdlhandle);
end;

result = true;

disp(sprintf('\n\n----------------------------Done converting the model--------------------------------\n'));
disp(sprintf('\n\n------------Now press Ctrl+D to propagate data-types in the updated model------------\n'));

return;

function result = updateBlocksFxdptToFltpt(mdlhandle);

disp(sprintf('\n-----Proceeding to configure IIR Filter Subsystem from Fixed-point to Floating-point------\n'));

disp(sprintf('\n--------------Changing configuration of the Constant blocks---------------\n'));
ConstantList = find_system(mdlhandle,'SearchDepth',2,'block_type','constant');
for i = 1:length(ConstantList),
    constantname = get_param(ConstantList(i),'Name');
    disp(sprintf('\nConstant block ''%s''',constantname));

    set_param(ConstantList(i),'gui_display_data_type','Floating-point');
    input(sprintf('On ''Basic'' tab\n\tSelected ''Output Type'' radio-button to ''Floating-point''. Press any key to continue.'));

    set_param(ConstantList(i),'arith_type','Floating-point');
    input(sprintf('\tSelected ''Arithmetic Type'' to ''Floating-point''. Press any key to continue.'));

    set_param(ConstantList(i),'preci_type','Custom');
    input(sprintf('\tSelected ''Floating-point Precision'' radio-button to ''Custom''. Press any key to continue.'));

    set_param(ConstantList(i),'exp_width','6');
    input(sprintf('\tSpecified ''Exponent width'' as ''6''. Press any key to continue.'));

    set_param(ConstantList(i),'frac_width','9');
    input(sprintf('\tSpecified ''Fraction width'' as ''9''. Press any key to continue.'));
end;

disp(sprintf('\n------------Next changing configuration of the Addsub blocks-------------\n'));
AddSubList = find_system(mdlhandle,'SearchDepth',2,'block_type','addsub');
for i = 1:length(AddSubList),
    addsubname = get_param(AddSubList(i),'Name');
    disp(sprintf('\nAddSub block ''%s''',addsubname));

    set_param(AddSubList(i),'precision','Full');
    input(sprintf('\n\tFor Floating-point Addition/Subtraction only Full precision output is allowed.\n\tOn ''Output'' tab selected ''Precision'' radio-button to ''Full''. Press any key to continue.'));
end;

disp(sprintf('\n-----------Finally changing configuration of the Mult blocks------------\n'));
disp(sprintf('\n\nFinally changing configuration of the  Mult blocks...\n'));
MultList = find_system(mdlhandle,'SearchDepth',2,'block_type','mult');
for i = 1:length(MultList),
    multname = get_param(MultList(i),'Name');
    disp(sprintf('\nMult block ''%s''',multname));

    set_param(MultList(i),'precision','Full');
    input(sprintf('\n\tFor Floating-point Multiplication only Full precision output is allowed.\n\tOn ''Basic'' tab selected ''Precision'' radio-button to ''Full''. Press any key to continue.'));
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

disp(sprintf('\n-----Proceeding to configure IIR Filter Subsystem from Floating-point to Fixed-point------\n'));

disp(sprintf('\n--------------Changing configuration of the Constant blocks---------------\n'));
ConstantList = find_system(mdlhandle,'SearchDepth',2,'block_type','constant');
for i = 1:length(ConstantList),
    constantname = get_param(ConstantList(i),'Name');
    disp(sprintf('\nConstant block ''%s''',constantname));

    set_param(ConstantList(i),'gui_display_data_type','Fixed-point');
    input(sprintf('On ''Basic'' tab\n\tSelected ''Output Type'' radio-button to ''Fixed-point''. Press any key to continue.'));

    set_param(ConstantList(i),'arith_type','Signed (2''s comp)');
    input(sprintf('\tSelected ''Arithmetic Type'' to ''Signed  (2''s comp)''. Press any key to continue.'));

    set_param(ConstantList(i),'n_bits','32');
    input(sprintf('\tSpecified ''Number of bits'' as ''32''. Press any key to continue.'));

    set_param(ConstantList(i),'bin_pt','24');
    input(sprintf('\tSpecified ''Binary point'' as ''24''. Press any key to continue.'));
end;

disp(sprintf('\n------------Next changing configuration of the Addsub blocks-------------\n'));
AddSubList = find_system(mdlhandle,'SearchDepth',2,'block_type','addsub');
for i = 1:length(AddSubList),
    addsubname = get_param(AddSubList(i),'Name');
    disp(sprintf('\nAddSub block ''%s''',addsubname));

    disp(sprintf('\n\tExplicitly specify output-precision for Rate & Type to converge in Fixed-point feedback system\n')); 
    set_param(AddSubList(i),'precision','User Defined');
    input(sprintf('On ''Output'' tab\n\tSelected ''Precision'' radio-button to ''User Defined''. Press any key to continue.'));

    set_param(AddSubList(i),'arith_type','Signed  (2''s comp)');
    input(sprintf('\tSelected ''Arithmetic type'' radio-button to ''Signed  (2''s comp)''. Press any key to continue.'));

    set_param(AddSubList(i),'n_bits','32');
    input(sprintf('\tSpecified ''Number of bits'' as ''32''. Press any key to continue.'));

    set_param(AddSubList(i),'bin_pt','24');
    input(sprintf('\tSpecified ''Binary point'' as ''24''. Press any key to continue.'));
end;

disp(sprintf('\n-----------Finally changing configuration of the Mult blocks------------\n'));
MultList = find_system(mdlhandle,'SearchDepth',2,'block_type','mult');
for i = 1:length(MultList),
    multname = get_param(MultList(i),'Name');
    disp(sprintf('\nMult block ''%s''',multname));

    disp(sprintf('\n\tExplicitly specify output-precision for Rate & Type to converge in Fixed-point feedback system\n')); 
    set_param(MultList(i),'precision','User Defined');
    input(sprintf('On ''Basic'' tab\n\tSelected ''Precision'' radio-button to ''User Defined''. Press any key to continue.'));

    set_param(MultList(i),'arith_type','Signed  (2''s comp)');
    input(sprintf('\tSelected ''Arithmetic type'' radio-button to ''Signed  (2''s comp)''. Press any key to continue.'));

    set_param(MultList(i),'n_bits','32');
    input(sprintf('\tSpecified ''Number of bits'' as ''32''. Press any key to continue.'));

    set_param(MultList(i),'bin_pt','24');
    input(sprintf('\tSpecified ''Binary point'' as ''24''. Press any key to continue.'));
end;

ah = find_system(gcs, 'FindAll', 'on', 'type', 'annotation', 'Text', 'Floating-point IIR Filter Direct-Form II Implementation');
if ~isempty(ah)
    for i = 1:length(ah)
        set_param(ah(i), 'Text', 'Fixed-point IIR Filter Direct-Form II Implementation');
    end;
end

result = true;

return;
