function [ arready, rvalid, rlast, rdata, rresp ] = xlScatterGatherEngineFrameTransfer( ...
    frame,...
    araddr, arlen, arsize, arburst,arprot, arcache, arvalid,...
    rready, aresetn, IMAGE_COLS, IMAGE_ROWS, WORD_SIZE)  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% XLSCATTERGATHERENGINEFRAMETRAMSFER creates commands for transfer of one
% frame of data. The commands are transferred to the VDMA through AXI read
% channel interface. Each command is a block descriptor and a template
% descriptor is held by the "mem" persistent variable. The sideband signal
% frame is used to detrmine which fram to transfer in creating the command.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
uint32 = {xlUnsigned, 32, 0};
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Defining States
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
STATE_RESET = 0;
STATE_STRT = 1;
STATE_ADDR_VALID_RESPONDED = 2;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Frame Constants
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
BYTES_PER_FRAME = IMAGE_COLS*IMAGE_ROWS*WORD_SIZE;
BYTES_PER_ROW = IMAGE_COLS*WORD_SIZE;

persistent mem, mem = xl_state(...       
    [...
        32,  0,  0, 0, IMAGE_ROWS, IMAGE_COLS*WORD_SIZE, IMAGE_COLS*WORD_SIZE ...%Next DESC PTR, 0, START ADDRESS, 0, VSIZE, HSIZE, STRIDE, PADDING
    ],uint32);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% AXI Read Channel Interface States
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
persistent arready_state, arready_state = xl_state(false, {xlBoolean, 1, 0});
persistent araddr_state, araddr_state = xl_state(0, araddr);
persistent arsize_state, arsize_state = xl_state(0, arsize);
persistent arburst_state, arburst_state = xl_state(0, arburst);
persistent arlen_state, arlen_state = xl_state(0, arlen);

persistent rvalid_state, rvalid_state = xl_state(false, {xlBoolean, 1, 0});
persistent rresp_state, rresp_state = xl_state(0, {xlUnsigned, 2, 0});
persistent rlast_state, rlast_state = xl_state(false, {xlBoolean, 1, 0});
persistent rdata_state, rdata_state = xl_state(0, {xlUnsigned, 32, 0});


persistent mem_addr, mem_addr = xl_state(0, {xlUnsigned, 10, 0});

persistent state, state = xl_state(0, {xlUnsigned, 5, 0});

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% AXI READ CHANNEL SIGNALS
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
arready = arready_state;
rlast_state = false;
rdata_state = 0;
        
if aresetn == false,    
    state = STATE_RESET;
end;

switch state,
    case STATE_RESET,
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Reset State : All Perisitent Variables are Reinitialized       
        araddr_state = 0;
        arsize_state = 0;
        arburst_state = 0;
        arlen_state = 0;
        arready_state = false;        
        rvalid_state = false;
        rresp_state = 0;  
        rlast_state = false;        
        mem_addr = 0;
        %May need to define a next state so that this effectively becomes
        %a funcion call
        state = STATE_STRT;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    case STATE_STRT,
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Checks if the scatter gather engine has sent a request for a block
    % descriptor    
        if arvalid == true,
            araddr_state = araddr;
            arsize_state = arsize;
            arburst_state = arburst;        
            arlen_state = arlen+1;
            arready_state = true;
            state = STATE_ADDR_VALID_RESPONDED;
        end
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    case STATE_ADDR_VALID_RESPONDED,
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Send the data beats     
        arready_state = false;
        if arlen_state == 0,
            rvalid_state = false;
            state = STATE_RESET;            
        else
            rvalid_state = true;                    
            if mem_addr == 2, % START_ADDR
                rdata_state = BYTES_PER_FRAME*frame;
            else
                rdata_state = mem(mem_addr);                                            
            end;
            if arlen_state == 1,                        
                rlast_state = true;
            end;                
            if rready == true,
                mem_addr = mem_addr+1;                                     
                arlen_state = arlen_state-1;
            end;
        end;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
end;

rvalid = rvalid_state;
rdata = xfix({xlUnsigned,32,0},rdata_state);
rresp = rresp_state;
rlast = rlast_state;