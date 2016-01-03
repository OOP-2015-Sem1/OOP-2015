%
% Filename   : ML402_NetEth_Target.m
%
% Description: Defines the compilation target information for the
%              Ethernet co-simulation platform.
%

function settings = ML402_NetEth_Target

    % ------------------------------------------------------------------
    % Initialize generic target parameters.
    % ------------------------------------------------------------------
    settings = xlEthernetTarget();

    % ------------------------------------------------------------------
    % Define interface-specific information.
    % ------------------------------------------------------------------

    % Specify compilation target version.
    settings.version = '9.2';

    % FPGA parts supported by the target.
    part1.family = 'Virtex4';
    part1.part = 'xc4vsx35';
    part1.speed = '-10';
    part1.package = 'ff668';

    settings.supported_parts.allowed = {part1};

    % Define hardware co-simulation clock settings.
    settings.clock_settings.source_period = '10';
    settings.clock_settings.dut_period_allowed = '[10, 15, 20, 30]';
    settings.clock_settings.dut_period_default = '10';

    % Target has a fixed, free-running clock period.
    settings.sysclk_period.allowed = ...
        settings.clock_settings.dut_period_default;

    % Post-generation function.
    settings.postgeneration_fcn = 'ML402_NetEth_PostGeneration';
