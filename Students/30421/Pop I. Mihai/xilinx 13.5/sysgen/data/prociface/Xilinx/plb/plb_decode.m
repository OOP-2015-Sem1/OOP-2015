function [wrDBusReg, addrAck, rdComp, wrDAck, bankAddr, RNWReg, rdDAck, rdDBus, linearAddr] = plb_bus_decode(plbRst, plbABus, plbPAValid, plbRNW, plbWrDBus, rdData, addrPref)

% constant variables (TODO: should pass from outside)
ADDRPREF_LEN = [% T.addrPref_len %];
BANKADDR_LEN = [% T.bankAddr_len %];
LINEARADDR_LEN = [% T.linearAddr_len %];
ABUS_LEN = [% T.ABus_len %];
DBUS_LEN = [% T.DBus_len %];

% declare and initialize persistent variables
% register input bus signals
persistent plbRstReg_, plbRstReg_ = xl_state(0, {xlBoolean});
persistent plbABusReg_, plbABusReg_ = xl_state(0, {xlUnsigned, ABUS_LEN, 0});
persistent plbPAValidReg_, plbPAValidReg_ = xl_state(0, {xlBoolean});
persistent plbRNWReg_, plbRNWReg_ = xl_state(0, {xlUnsigned, 1, 0});
persistent plbWrDBusReg_, plbWrDBusReg_ = xl_state(0, {xlUnsigned, DBUS_LEN, 0});

% ===== rest of the outputs =====

bankAddr   = xl_slice(plbABusReg_, 2+BANKADDR_LEN+LINEARADDR_LEN-1, 2+LINEARADDR_LEN);
linearAddr = xl_slice(plbABusReg_, 2+LINEARADDR_LEN-1, 2);
RNWReg = plbRNWReg_;
wrDBusReg = plbWrDBusReg_;

% ===== p_select =====

% register PAValid
persistent aValidReg, aValidReg = xl_state(0, {xlBoolean});
aValidReg = plbPAValidReg_;

% extract and register the address prefix
addrPref_in = xl_slice(plbABusReg_, xl_nbits(plbABusReg_)-1, xl_nbits(plbABusReg_)-ADDRPREF_LEN);
if addrPref_in == addrPref
    ps1 = true;
else 
    ps1 = false;
end 

persistent ps1Reg, ps1Reg = xl_state(0, ps1);
ps1Reg = ps1;

ps = xl_and(ps1Reg, aValidReg);

% ===== addrAck =====

% register ps
persistent psReg, psReg = xl_state(0, ps);

addrAck = xfix({xlUnsigned, 1, 0}, xl_and(xl_not(plbRstReg_), ps, xl_not(psReg)));

psReg = ps;

% ===== rdComp, rd/wr DAck =====
 
rdComp1 = xfix({xlUnsigned, 1, 0}, xl_and(addrAck, RNWReg));

NUM_rdCompDelay = 3;
persistent rdCompDelay, rdCompDelay = xl_state(zeros(1, NUM_rdCompDelay), rdComp1, NUM_rdCompDelay);
rdComp2 = rdCompDelay.back;
rdCompDelay.push_front_pop_back(rdComp1);

persistent rdCompReg, rdCompReg = xl_state(0, rdComp1);
rdComp = rdCompReg;
rdCompReg = rdComp2;

persistent rdDAckReg, rdDAckReg = xl_state(0, rdComp1);
rdDAck = rdDAckReg;
rdDAckReg = rdComp;

persistent wrDAckReg, wrDAckReg = xl_state(0, addrAck);
wrDAck = wrDAckReg;
wrDAckReg = xl_and(addrAck, xl_not(RNWReg));

% ===== rdDBus =====

rdSel = xl_or(rdComp2, rdComp);

if rdSel == 1
    rdDBus1 = rdData;
else
    rdDBus1 = 0;
end % if

persistent rdDBusReg, rdDBusReg = xl_state(0, rdDBus1);
rdDBus = rdDBusReg;
rdDBusReg = rdDBus1;

% rdDBus = xl_concat(rdDBus32, rdDBus32);
% rdDBus = rdDBus32;

% ===== update the persistent variables =====

plbRstReg_ = plbRst;
plbABusReg_ = plbABus;
plbPAValidReg_ = plbPAValid;
plbRNWReg_ = plbRNW;
plbWrDBusReg_ = xl_slice(plbWrDBus, DBUS_LEN-1, 0);
