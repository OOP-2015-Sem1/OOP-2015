[%- BLOCK b_memmaplist %]
[%- FOREACH name = T.mem_names -%]
[%- ", " IF loop.first -%]
sm_[% name %][% ", " IF not loop.last %]
[%- END -%]
[% END -%]
[%- BLOCK b_re_portlist %]
[%- FOREACH mem = T.toregs -%]
[%- ", " IF loop.first -%]
sm_[% mem.name %]_din, sm_[% mem.name %]_en[% ", " IF not loop.last %]
[%- END -%]
[%- FOREACH mem = T.fromfifos -%]
[%- ", " IF loop.first -%]
sm_[% mem.name %]_re[% ", " IF not loop.last %]
[%- END -%]
[%- FOREACH mem = T.tofifos -%]
[%- ", " IF loop.first -%]
sm_[% mem.name %]_din, sm_[% mem.name %]_we[% ", " IF not loop.last %]
[%- END -%]
[%- FOREACH mem = T.shmems -%]
[%- ", " IF loop.first -%]
sm_[% mem.name %]_addr, sm_[% mem.name %]_din, sm_[% mem.name %]_we[% ", " IF not loop.last %]
[%- END -%]
[% END -%]
function [read_bank_out[%- PROCESS b_re_portlist -%]] = plb_memmap(wrDBus, bankAddr, linearAddr, RNWReg, addrAck[%- PROCESS b_memmaplist -%])


% connvert the input data to UFix_32_0 (the bus data type)
% 'From Register' blocks
[%- FOREACH mem = T.fromregs %]
% sm_[% mem.name %]_bus = xfix({xlUnsigned, 32, 0}, 0);
sm_[% mem.name %]_bus = xl_force(sm_[% mem.name %], xlUnsigned, 0);
[% END %]
% 'To Register' blocks
[% IF T.readback_toregs_size > 0 %]
[%- FOREACH mem = T.toregs %]
% sm_[% mem.name %]_dout = xfix({xlUnsigned, 32, 0}, 0);
sm_[% mem.name %]_dout = xl_force(sm_[% mem.name %], xlUnsigned, 0);
[% END %]
[% END %]
% 'From FIFO' blocks
[%- FOREACH mem = T.fromfifos %]
% dout
sm_[% mem.name %]_bus = xl_force(sm_[% mem.name %], xlUnsigned, 0);
% pfull
sm_[% mem.name %]_pfull_bus = xl_force(sm_[% mem.name %]_pfull, xlUnsigned, 0);
% empty
sm_[% mem.name %]_empty_bus = xfix({xlUnsigned, 32, 0}, sm_[% mem.name %]_empty);
[% END %]
% 'To FIFO' blocks
[%- FOREACH mem = T.tofifos %]
% pfull
sm_[% mem.name %]_pfull_bus = xl_force(sm_[% mem.name %]_pfull, xlUnsigned, 0);
% full
sm_[% mem.name %]_full_bus = xfix({xlUnsigned, 32, 0}, sm_[% mem.name %]_full);
[% END %]
% 'Shared Memory' blocks
[%- FOREACH mem = T.shmems %]
% [% mem.name %]_bus = xfix({xlUnsigned, 32, 0}, 0);
sm_[% mem.name %]_bus = xl_force(sm_[% mem.name %], xlUnsigned, 0);
[% END %]

% 'dout' ports of 'From Register' blocks
[% IF T.fromregs_size > 0 || T.readback_toregs_size > 0 %]
% registered register mux output
persistent reg_bank_out_reg; reg_bank_out_reg = xl_state(0, {xlUnsigned, 32, 0});
reg_bank_out = reg_bank_out_reg;

[% IF T.fromregs_size == 1 && T.readback_toregs_size == 0 -%]
% direct connection if there is one 'From Reg' but no 'To Reg'
reg_bank_out_reg = sm_[% T.fromregs.0.name %]_bus;
[%- ELSIF T.fromregs_size == 0 && T.readback_toregs_size == 1 -%]
% direct connection if there is no 'From Reg' but one 'To Reg'
reg_bank_out_reg = sm_[% T.toregs.0.name %]_dout;
[%- ELSE -%]
[% FOREACH mem = T.fromregs -%]
[%- "else" IF not loop.first -%]
if linearAddr == [% mem.addr %]
    reg_bank_out_reg = sm_[% mem.name %]_bus;
[% END %]
[%- "else" IF T.fromregs_size > 0 && T.readback_toregs_size > 0 -%]
[%- IF T.readback_toregs_size > 0 %]
[%- FOREACH mem = T.toregs -%]
[%- "else" IF not loop.first -%]
if linearAddr == [% mem.addr %]
    reg_bank_out_reg = sm_[% mem.name %]_dout;
[% END -%]
[% END %]
end
[% END %]
[%- END %]

% 'From FIFO' and 'To FIFO' blocks

[% IF T.fromfifos_size > 0 || T.tofifos_size > 0 -%]
% registered FIFO mux output
persistent fifo_bank_out_reg; fifo_bank_out_reg = xl_state(0, {xlUnsigned, 32, 0});
fifo_bank_out = fifo_bank_out_reg;
[%- END %]

[% FOREACH mem = T.fromfifos -%]
[%- "else" IF not loop.first -%]
if linearAddr == [% mem.addr %]
    fifo_bank_out_reg = sm_[% mem.name %]_bus;
elseif linearAddr == [% mem.pfull.addr %]
    fifo_bank_out_reg = sm_[% mem.name %]_pfull_bus;
elseif linearAddr == [% mem.empty.addr %]
    fifo_bank_out_reg = sm_[% mem.name %]_empty_bus;
[% END %]
[%- "else" IF T.fromfifos_size > 0 && T.tofifos_size > 0 -%]
[% FOREACH mem = T.tofifos -%]
[%- "else" IF not loop.first -%]
if linearAddr == [% mem.pfull.addr %]
    fifo_bank_out_reg = sm_[% mem.name %]_pfull_bus;
elseif linearAddr == [% mem.full.addr %]
    fifo_bank_out_reg = sm_[% mem.name %]_full_bus;
[% END -%]
[% "end" IF T.fromfifos_size > 0 || T.tofifos_size > 0 %]

opCode = xl_concat(addrAck, RNWReg, bankAddr, linearAddr);

% 'Shared Memory' blocks

[% IF T.shmems_size == 1 -%]
sm_[% T.shmems.first.name %]_sel = true;
[%- ELSE -%]
[% FOREACH mem = T.shmems -%]
sm_[% mem.name %]_sel_value = xl_concat(xl_slice(linearAddr, ...
                                        xl_nbits(linearAddr) - 1, ...
                                        [% mem.width %]) ...
                                    );
if sm_[% mem.name %]_sel_value == xfix({xlUnsigned, ...
                                        xl_nbits(linearAddr) - [% mem.width %], ...
                                        0}, ...
                                        [% mem.pref %]);
    sm_[% mem.name %]_sel = true;
else
    sm_[% mem.name %]_sel = false;
end
[% END %]
[%- END %]

[% FOREACH mem = T.shmems -%]
[%- IF loop.first -%]
% registered Shared Memory mux output
persistent ram_bank_out_reg; ram_bank_out_reg = xl_state(0, {xlUnsigned, 32, 0});
ram_bank_out = ram_bank_out_reg;
[% END %]
[%- "else" IF not loop.first -%]
if sm_[% mem.name %]_sel
    ram_bank_out_reg = sm_[% mem.name %]_bus;
[% "end" IF loop.last %]
[%- END %]

% 'din' ports of 'Shared Memory' blocks
[% FOREACH mem = T.shmems -%]
sm_[% mem.name %]_din = xl_force(xl_slice(wrDBus, [% mem.n_bits %] - 1, 0), ...
                                 [% IF mem.arith_type == 1 %]xlSigned[% ELSE %]xlUnsigned[% END %], ...
                                 [% mem.bin_pt %]);
[% END %]

% 'we' ports of 'Shared Memory' blocks
[% FOREACH mem = T.shmems -%]
persistent sm_[% mem.name %]_we_reg; sm_[% mem.name %]_we_reg = xl_state(false, {xlBoolean});
sm_[% mem.name %]_we = sm_[% mem.name %]_we_reg;
[% IF T.shmems_size == 1 -%]
opCode_sm_[% mem.name %] = xl_concat(addrAck, RNWReg, bankAddr);
if opCode_sm_[% mem.name %] == xfix({xlUnsigned, 4, 0}, 8);
    sm_[% mem.name %]_we_reg = true;
else
    sm_[% mem.name %]_we_reg = false;
end
[%- ELSE -%]
opCode_sm_[% mem.name %] = xl_concat(addrAck, ...
                                     RNWReg, ...
                                     bankAddr, ...
                                     xl_slice(linearAddr, ...
                                              xl_nbits(linearAddr) - 1, ...
                                              [% mem.width %]) ...
                                    );
if opCode_sm_[% mem.name %] == xl_concat(xfix({xlUnsigned, 4, 0}, 8), ...
                                         xfix({xlUnsigned, ...
                                               xl_nbits(linearAddr) - [% mem.width %], ...
                                               0}, ...
                                               [% mem.pref %]) ...
                                        );
    sm_[% mem.name %]_we_reg = true;
else
    sm_[% mem.name %]_we_reg = false;
end
[%- END %]
[% END %]

% 'addr' ports of 'Shared Memory' blocks
[% FOREACH mem = T.shmems -%]
persistent sm_[% mem.name %]_addr_reg; 
sm_[% mem.name %]_addr_reg = xl_state(0, {xlUnsigned, [% mem.width %], 0});
sm_[% mem.name %]_addr = sm_[% mem.name %]_addr_reg;
[% IF T.shmems_size == 1 -%]
sm_[% mem.name %]_addr_reg = linearAddr;
[%- ELSE -%]
if addrAck == 1
    sm_[% mem.name %]_addr_reg = xl_slice(linearAddr, [% mem.width %], 0);
else
    sm_[% mem.name %]_addr_reg = sm_[% mem.name %]_addr_reg;
end
[%- END %]
[% END %]

% 're' ports of 'From FIFO' blocks
[% FOREACH mem = T.fromfifos -%]
if opCode == xl_concat(xfix({xlUnsigned, 4, 0}, 13), ...
                       xfix({xlUnsigned, xl_nbits(linearAddr), 0}, [% mem.addr %]))
    sm_[% mem.name %]_re = true;
else
    sm_[% mem.name %]_re = false;
end
[% END %]

% 'en' ports of 'To Register' blocks
[% FOREACH mem = T.toregs -%]
if opCode == xl_concat(xfix({xlUnsigned, 4, 0}, 10), ...
                       xfix({xlUnsigned, xl_nbits(linearAddr), 0}, [% mem.addr %]))
    sm_[% mem.name %]_en = true;
else
    sm_[% mem.name %]_en = false;
end
[% END %]

% 'din' ports of 'To FIFO' blocks
[% FOREACH mem = T.tofifos -%]
sm_[% mem.name %]_din = xl_force(xl_slice(wrDBus, [% mem.n_bits %] - 1, 0), ...
                                 [% IF mem.arith_type == 1 %]xlSigned[% ELSE %]xlUnsigned[% END %], ...
                                 [% mem.bin_pt %]);
[% END %]

% 'we' ports of 'To FIFO' blocks
[% FOREACH mem = T.tofifos -%]
if opCode == xl_concat(xfix({xlUnsigned, 4, 0}, 9), ...
                       xfix({xlUnsigned, xl_nbits(linearAddr), 0}, [% mem.addr %]))
    sm_[% mem.name %]_we = true;
else
    sm_[% mem.name %]_we = false;
end
[% END %]

% 'din' ports of 'To Register' blocks
[% FOREACH mem = T.toregs -%]
sm_[% mem.name %]_din = xl_force(xl_slice(wrDBus, [% mem.n_bits %] - 1, 0), ...
                                 [% IF mem.arith_type == 1 %]xlSigned[% ELSE %]xlUnsigned[% END %], ...
                                 [% mem.bin_pt %]);
[% END %]

persistent read_bank_out_reg; read_bank_out_reg = xl_state(0, {xlUnsigned, 32, 0});
read_bank_out = read_bank_out_reg;

persistent bankAddr_reg; bankAddr_reg = xl_state(0, bankAddr);

if bankAddr_reg == 0
    % Bank 0: Shared Memories
[% IF T.shmems_size == 0 -%]
    read_bank_out_reg = 0;
[%- ELSE -%]
    read_bank_out_reg = ram_bank_out;
[%- END %]
elseif bankAddr_reg == 1
    % Bank 1: From/To FIFOs
[% IF T.fromfifos_size == 0 && T.tofifos_size == 0 -%]
    read_bank_out_reg =  0;
[%- ELSE -%]
    read_bank_out_reg = fifo_bank_out;
[%- END %]
elseif bankAddr_reg == 2
    % Bank 2: From/To Registers
[% IF T.fromregs_size == 0 && T.readback_toregs_size == 0 -%]
    read_bank_out_reg = 0;
[%- ELSE -%]
    read_bank_out_reg = reg_bank_out;
[%- END %]
elseif bankAddr_reg == 3
    % Bank 3: Configuration Registers
    read_bank_out_reg = 0;
end

bankAddr_reg = bankAddr;
