function blkStruct = slblocks
%SLBLOCKS Defines the Simulink library block representation
%   for the Xilinx Blockset.

% Copyright (c) 1998 Xilinx Inc. All Rights Reserved.

blkStruct.Name    = ['Xilinx' sprintf('\n') 'Blockset'];
blkStruct.OpenFcn = 'xbs_r4';
blkStruct.MaskInitialization = '';

blkStruct.MaskDisplay = ['disp(''Xilinx\nBlockset'')'];

% Define the library list for the Simulink Library browser.
% Return the name of the library model and the name for it
%
Browser(1).Library = 'xbs_r4';
Browser(1).Name    = 'Xilinx Blockset';

Browser(2).Library = 'xrbs_r4';
Browser(2).Name    = 'Xilinx Reference Blockset';

blkStruct.Browser = Browser;

% End of slblocks.m
