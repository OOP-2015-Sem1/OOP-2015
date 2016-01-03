function paramin=xledkupdatefn(paramin)
% get previous version

version = xlgetfield(paramin, 'version', '');

latestVersion = '9.2.01.1';
switch version
  case ''
    if isfield(paramin,'xledksettingsdata')
        paramin.xledksettingsdata.isDevelopment = 0;
        paramin.version = latestVersion;
        paramin.xledksettingsdata.useCustomBusInterface = 0;
        paramin.xledksettingsdata.customBusInterfaceValue = [];
    end
  case '9.2.01'
     if isfield(paramin,'xledksettingsdata')
        paramin.version = latestVersion;
        paramin.xledksettingsdata.useCustomBusInterface = 0;
        paramin.xledksettingsdata.customBusInterfaceValue = [];
    end
  otherwise
    % do nothing for the current version
end

