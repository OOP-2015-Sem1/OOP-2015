%
%  $RCSfile: xlNGCCompileCallback.m,v $
%
%  Xilinx Secret
%  Copyright (c) 2005, Xilinx, Inc. All rights reserved.
%
%  Last modified on: $Date: 2005/03/14 03:48:49 $  (UTC)
%  Last modified by: $Author: jballagh $
%
%  Handles compilation cleanup for NGC compilation target.
%
%  @param src Handle to the source object that invoked this function.
%  @param data Data associated with the source object.
%

function xlNGCCompileCallback(src, data)

    %import com.xilinx.sysgen.guitools.xlNgcCompileWaitBox;

    % Define action constants.
    COMPLETED_ACTION = char(com.xilinx.sysgen.guitools.xlNgcCompileWaitBox.COMPLETED_ACTION);

    % Find the wait-box object that invoked this callback.
    wbo = get(data, 'Source');

    % Extract user data from the wait-box object.
    ud = get(wbo, 'UserData');

    % Extract the design compilation parameters.
    target_params = ud.target_params;

    % Re-enable System Generator GUI controls.
    xlSetSgFigEnable(target_params.block, 'on');

    % Update message text depending on status value.
    if (~strcmp(char(data.getActionCommand), COMPLETED_ACTION))
        return;
    end

    % Stop the GUI and display successfull completion message.
    wbo.stopGUI();
    wbo.setText('Compilation finished successfully.');
    xlEventListenerManager('delete',ud.eventListener_id);
