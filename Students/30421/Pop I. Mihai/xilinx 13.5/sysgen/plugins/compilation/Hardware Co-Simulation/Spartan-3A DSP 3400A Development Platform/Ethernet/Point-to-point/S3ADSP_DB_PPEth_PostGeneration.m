%
% Filename   : S3ADSP_DB_PPEth_PostGeneration.m
%
% Description: Defines the post-generation function for the
%              Ethernet co-simulation compilation.
%

function status = S3ADSP_DB_PPEth_PostGeneration(target_params)

    % ------------------------------------------------------------------
    % Define interface-specific information.
    % ------------------------------------------------------------------

    % Type of the co-simulation interface.
    target_params.iface.type = 'ppethernet';

    % Description of the co-simulation interface.
    target_params.iface.description = 'Point-to-point Ethernet';

    % Runtime co-simulation block.
    target_params.iface.cosim_library = 'PPEthernetRuntimeCosim_r4';

    % Co-simulation engine DLL.
    target_params.iface.engine_dll = 'PPEthernetCosimEngine';

    % List of files required during the compilation.
    target_params.iface.netlist_files = { ...
    };

    % XFlow options file for implementation tools (ngdbuild, map, par).
    target_params.iface.impl_options_file = 'eth_cosim_impl.opt';

    % XFlow options file for bitgen.
    target_params.iface.config_options_file = 'eth_cosim_bitgen.opt';

    % Non-memory mapped ports.
    target_params.non_memory_mapped_ports = S3ADSP_DB_PPEth_NonMmPorts;

    % Co-simulation information.
    target_params.cosim_params = S3ADSP_DB_PPEth_CosimTable;

    % ------------------------------------------------------------------
    % Invoke generic post generation function.
    % ------------------------------------------------------------------
    status = xlEthernetPostGeneration(target_params);
