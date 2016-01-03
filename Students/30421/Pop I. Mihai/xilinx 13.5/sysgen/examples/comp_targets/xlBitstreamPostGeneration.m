%
% Post-generation function produces a configuration bitstream.
% Script execution is as follows:
%
% - Invoke point-tools to produce the bitstream
%
% @param target_params Struct defining configuration of selected
%        compilation target.
% @return status A non-zero status indicates an error.
%
function status = xlBitstreamPostGeneration(target_params)
  status = 0;

  % Obtain model's target implementation directory from target_params struct.
  target_dir = target_params.('directory');
  vendor_dir = fileparts(which(target_params.('postgeneration_fcn')));

  % Change to the target implementation directory.
  cd(target_dir);

  % Fetch the design synopsis from the description written by the netlister.
  import com.xilinx.sysgen.netlist.*;
  synopsis_file = fullfile(target_dir, 'synopsis');
  try
    synopsis = Block.fromXTable(java.io.File(synopsis_file));
  catch
    status = reportError('Could not read design synopsis ''%s''.', synopsis_file);
    return;
  end

  % Get name of user top level from the "userTopLevel" global attribute.
  h = synopsis.getAttributes;
  design = h.get(Attribute.DESIGN);
  toplevel = h.get(Attribute.CLK_WRAPPER);
  device = h.get(Attribute.DEVICE);
  synthtool = h.get(Attribute.SYNTHESIS_TOOL);

  % Synthesize the design.
  if strcmp(synthtool, 'XST')
    synth_cmd = sprintf('xst -ifn xst_%s.scr', design);
  elseif strcmp(synthtool, 'Synplify Pro')
    synth_cmd = sprintf('synplify_pro -batch synplify_%s.prj', design);
  elseif strcmp(synthtool, 'Synplify')
    synth_cmd = sprintf('synplify -batch synplify_%s.prj', design);
  else
    status = reportError('Unsupported synthesis tool ''%s''.', synthtool);
    return;
  end

  [st, res] = system(synth_cmd);
  if (st ~= 0)
    status = reportError('Failed to synthesize the design.');
    return;
  end

  % Copy the XFlow options files to the target directory.
  impl_opt_file = 'balanced_xltools.opt';
  config_opt_file = 'bitgen_xltools.opt';
  copyfile(fullfile(vendor_dir, impl_opt_file), target_dir);
  copyfile(fullfile(vendor_dir, config_opt_file), target_dir);

  % Implement the design using XFlow.
  xflow_cmd = sprintf('xflow -p %s -implement %s -config %s %s', ...
                      device, impl_opt_file, config_opt_file, toplevel);
  [st, res] = system(xflow_cmd);
  if (st ~= 0)
    status = reportError('Failed to implement the design.');
    return;
  end

function status = reportError(msg)
  err = sprintf(['An error was encountered in the configuration ' ...
                 'bitstream generation flow. %s'], msg);
  status = struct('status_code', -1, 'err_msg', err);
