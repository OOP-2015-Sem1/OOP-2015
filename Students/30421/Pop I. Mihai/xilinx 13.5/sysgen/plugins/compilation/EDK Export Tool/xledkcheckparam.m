function status = xledkcheckparam(params)

status.status_code = 0;
status.err_msg = '';
if ~strcmp(params.('synthesis_tool'), 'XST')
    status.status_code = 1;
    status.err_msg = 'EDK Pcore Export flow only supports XST as synthesis tool.';
end