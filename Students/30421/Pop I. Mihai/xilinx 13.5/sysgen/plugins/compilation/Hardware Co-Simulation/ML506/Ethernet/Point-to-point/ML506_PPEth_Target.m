%
% Filename   : ML506_PPEth_Target.m
%
% Description: Defines the compilation target information for the
%              Ethernet co-simulation platform.
%

function settings = ML506_PPEth_Target

    % ------------------------------------------------------------------
    % Initialize generic target parameters.
    % ------------------------------------------------------------------
    settings = xlEthernetTarget();

    % ------------------------------------------------------------------
    % Define interface-specific information.
    % ------------------------------------------------------------------

    % Specify compilation target version.
    settings.version = '10.1';

    % FPGA parts supported by the target.
    part.family = 'virtex5';
    part.part = 'xc5vsx50t';
    part.speed = '-1';
    part.package = 'ff1136';
    settings.supported_parts.allowed = { part };

    % Define hardware co-simulation clock settings.
    settings.clock_settings.source_period = '5';
    settings.clock_settings.dut_period_allowed = '[10,15,20,30]';
    settings.clock_settings.dut_period_default = '10';

    % Target has a fixed, free-running clock period.
    settings.sysclk_period.allowed = '10';

    % Post-generation function.
    settings.postgeneration_fcn = 'ML506_PPEth_PostGeneration';
