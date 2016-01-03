%
% Filename   : S3ADSP_DB_NetEth_Target.m
%
% Description: Defines the compilation target information for the
%              Ethernet co-simulation platform.
%

function settings = S3ADSP_DB_NetEth_Target

    % ------------------------------------------------------------------
    % Initialize generic target parameters.
    % ------------------------------------------------------------------
    settings = xlEthernetTarget();

    % ------------------------------------------------------------------
    % Define interface-specific information.
    % ------------------------------------------------------------------

    % Specify compilation target version.
    settings.version = '10.1';

    % FPGA part supported by the target.
    part.family = 'spartan3adsp';
    part.part = 'xc3sd3400a';
    part.speed = '-4';
    part.package = 'fg676';
    settings.supported_parts.allowed = { part };

    % Define hardware co-simulation clock settings.
    settings.clock_settings.source_period = '8';
    settings.clock_settings.dut_period_allowed = '[15,20,30]';
    settings.clock_settings.dut_period_default = '20';

    % Target has a fixed, free-running clock period.
    settings.sysclk_period.allowed = '20';

    % Post-generation function.
    settings.postgeneration_fcn = 'S3ADSP_DB_NetEth_PostGeneration';
