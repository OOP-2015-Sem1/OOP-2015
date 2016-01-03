function [ arready, rvalid, rlast, rdata, rresp ] = xlScatterGatherEngineBlockTransfer_2( ...
    araddr, arlen, arsize, arburst,arprot, arcache, arvalid, ...
    rready, tx_rows, tx_cols, rx_rows, rx_cols)  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%This is The Block Descriptor Generator for the scatter gather engine to
%perform data transfer. Each block descriptor is 7 contiguous elements of
%the "mem" variable. Each transfer is of 128bit data ie 8 hsize and 16
%lines of 16Vsize. The stride lengthnis set to be 32. The first 28 elements
%(or 4 block descriptors) are used for writing cycle and the next 28
%elements(or 4 block descriptor) are used of reading cycle. During writing
%cycle 0-31 is written and during reading cycle the same values are read
%out. This tests whether the simulation model has heartbeat or not.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%size_y = 320; size_x = 240;
uint32 = {xlUnsigned, 32, 0};
STATE_RESET = 0;
STATE_STRT = 1;
STATE_ADDR_VALID_RESPONDED = 2;
INCREMENT_LINENUMBER = 4;
DELAY = 0;
WORD_SIZE = 4;%Each Pixel Data is stored in 16 Bytes
IMAGE_ROWS = tx_rows;
IMAGE_COLS = tx_cols;
READING_ROWS = IMAGE_ROWS;
READING_COLS = IMAGE_COLS;
VSIZE = READING_ROWS;
HSIZE = READING_COLS*WORD_SIZE;
START_ADDRESS = (rx_rows*rx_cols*4) +DELAY;
%Setting Up the Descriptor Memory to extract 8x8 blocks
persistent mem, mem = xl_state(...       
    [...
        32,  0,  START_ADDRESS, 0, VSIZE, HSIZE, HSIZE ...
    ],uint32);
%Always ready to accept an excitation
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
%  STATE VARAIBLE FOR STRORING The POST_RESET_STATE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
persistent post_reset_state, post_reset_state = xl_state(STATE_STRT, {xlUnsigned, 5, 0});

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  STATE VARAIBLE FOR STRORING BLOCK NUMBER - Used to define BDs
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% persistent block_number, block_number = xl_state(0, {xlUnsigned, 12, 0});

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  STATE VARAIBLE FOR STRORING SWATH NUMBER - Used to define BDs
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% persistent swath_number, swath_number = xl_state(0, {xlUnsigned, 12, 0});

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  STATE VARAIBLE FOR ADDRESS of NEXT MEMORY LOCATION - Used by Scatter
%  Engine to know when to stop gathering block descriptors
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
persistent next_block_descriptor_addr, next_block_descriptor_addr = xl_state(32, {xlUnsigned, 32, 0});

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% STATE VARIABLE FOR THE NEXT START ADDRESS FOR THE BLOCKDESCRIPTOR
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
persistent next_start_address, next_start_address = xl_state(0, {xlUnsigned, 32, 0});

arready = arready_state;
rvalid = rvalid_state;
rdata = xfix({xlUnsigned,32,0},rdata_state);
rresp = rresp_state;
rlast = rlast_state;


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
        rdata_state = 0;
        rlast_state = false;        
        mem_addr = 0;
        %May need to define a next state so that this effectively becomes
        %a funcion call
        state = post_reset_state;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    case INCREMENT_LINENUMBER,
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Resets the Raster Scan Variables - Line number is incremented
    % Block Number is set to the first block in line         
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

       
    case STATE_ADDR_VALID_RESPONDED,
        arready_state = false;
        if arlen_state == 0,
            rvalid_state = false;
            rlast_state = false;
            state = STATE_RESET;            
        else
            if rready,
                if (arlen_state ~= 0)
                    rvalid_state = true;                    
                    %if mem_addr == 2, %START_ADDR - MAC for address computation     
                    %    rdata_state = next_start_address;
                    if mem_addr == 0,
                        rdata_state = next_block_descriptor_addr;
                    else
                        rdata_state = mem(mem_addr);  
                    end;
                    mem_addr = mem_addr+1;     
                     arlen_state = arlen_state-1;
                    if arlen_state == 0,                        
                        rlast_state = true;
                    end;
                end
            end;
        end;
end;