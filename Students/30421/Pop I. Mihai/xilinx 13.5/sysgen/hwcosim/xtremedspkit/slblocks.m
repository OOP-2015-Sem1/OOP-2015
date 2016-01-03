function blkStruct = slblocks
%SLBLOCKS Defines the Simulink library block representation
%   for the XtremeDSP kit blocks.

blkStruct.Name    = ['XtremeDSP Kit'];
blkStruct.OpenFcn = 'XtremeDSPKit_r4';
blkStruct.MaskInitialization = '';

blkStruct.MaskDisplay = ['disp(''Xilinx XtremeDSP Kit'')'];

% Define the library list for the Simulink Library browser.
% Return the name of the library model and the name for it
%
Browser(1).Library = 'XtremeDSPKit_r4';
Browser(1).Name    = 'Xilinx XtremeDSP Kit';

blkStruct.Browser = Browser;

% End of slblocks.m
