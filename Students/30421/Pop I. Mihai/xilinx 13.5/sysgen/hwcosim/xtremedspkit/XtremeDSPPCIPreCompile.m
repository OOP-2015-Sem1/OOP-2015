%
% Pre-compilation function for the XtremeDSP Kit hardware co-simulation.
%
% @param target_params Struct defining configuration of selected
%        compilation target.
% @return status A non-zero status indicates an error.
%
function status = XtremeDSPPCIPreCompile(target_params)
    message = sprintf('%s is going to be deprecated in future releases of System Generator. Please use other hardware co-simulation targets instead.', target_params.compilation);
    fid = xlfeaturedescriptormanager('register', ...
                                     'Hardware Co-simulation Targets', ...
                                     message, ...
                                     { 'Block Instance', 'Target Deprecated'});

    xlfeaturedescriptormanager('set_content', ...
                               fid, ...
                               0, ...
                               { target_params.block, target_params.compilation });

    status = 0;
