function [ arready, rvalid, rlast, rdata, rresp ] = xlScatterGatherEngineBlockTransfer( ...
    araddr, arlen, arsize, arburst,arprot, arcache, arvalid,...
    rready, resetbd, aresetn, IMAGE_COLS, IMAGE_ROWS, BLOCK_SIZE)  
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
uint32 = {xlUnsigned, 32, 0};
STATE_RESET = 0;
STATE_STRT = 1;
STATE_ADDR_VALID_RESPONDED = 2;
%Setting Up the Descriptor Memory to extract 8x8 blocks
%64,  0,  2688, 0, 8, 16, 320, ...
%96,  0, 5376, 0, 8, 16, 320, ...
%128, 0, 8064, 0, 8, 16, 320, ...
%32,  0,  0, 0, 320*16, 1, 320, ...%Next DESC PTR, 0, START ADDRESS, 0, VSIZE, HSIZE, STRIDE, PADDING
%64,  0,  2688, 0, 8, 16, 320, ...
%96,  0, 5376, 0, 8, 16, 320, ...
WORD_SIZE = 4;%Each Pixel Data is stored in 16 Bytes
%BLOCK_SIZE = 64;%We Want to retrieve ablock size of 64 pixels
BLOCKS_PER_ROW = xfix({xlUnsigned,32,0},IMAGE_COLS/BLOCK_SIZE);
blocks_per_row = xfix({xlUnsigned,10,8},IMAGE_COLS/BLOCK_SIZE);
remainder = xl_slice(blocks_per_row,7,0);

if(remainder ~= 0)
    BLOCKS_PER_ROW = BLOCKS_PER_ROW + 1;
end

HORIZONTAL_BLOCK_ADDRESS_STRIDE = BLOCK_SIZE*WORD_SIZE;
VERTICAL_BLOCK_ADDRESS_STRIDE = BLOCK_SIZE*IMAGE_COLS*WORD_SIZE;

persistent mem, mem = xl_state(...       
    [...
        32,  0,  0, 0, BLOCK_SIZE, HORIZONTAL_BLOCK_ADDRESS_STRIDE, IMAGE_COLS*WORD_SIZE ...%BLOCK_SIZE, BLOCK_SIZE*WORD_SIZE, IMAGE_COLS*WORD_SIZE ...%Next DESC PTR, 0, START ADDRESS, 0, VSIZE, HSIZE, STRIDE, PADDING
    ],uint32);
%Always ready to accept an excitation
persistent arready_state, arready_state = xl_state(false, {xlBoolean});
persistent araddr_state, araddr_state = xl_state(0, araddr);
persistent arsize_state, arsize_state = xl_state(0, arsize);
persistent arburst_state, arburst_state = xl_state(0, arburst);
persistent arlen_state, arlen_state = xl_state(0, arlen);

persistent rvalid_state, rvalid_state = xl_state(false, {xlBoolean});
persistent rresp_state, rresp_state = xl_state(0, {xlUnsigned, 2, 0});
persistent rlast_state, rlast_state = xl_state(false, {xlBoolean});
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
persistent block_number, block_number = xl_state(0, {xlUnsigned, 12, 0});

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  STATE VARAIBLE FOR STRORING SWATH NUMBER - Used to define BDs
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
persistent swath_number, swath_number = xl_state(0, {xlUnsigned, 12, 0});

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

if aresetn == false,
    next_start_address = 0;
    next_block_descriptor_addr = 32;
    swath_number = 0;
    block_number = 0;
    state = STATE_RESET;
end;

if resetbd == true,
    next_block_descriptor_addr = 32;
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
        rdata_state = 0;
        rlast_state = false;        
        mem_addr = 0;
        %May need to define a next state so that this effectively becomes
        %a funcion call
        state = post_reset_state;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
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
        arready_state = false;
        if arlen_state == 0,
            rvalid_state = false;
            rlast_state = false;
            rdata_state = 0;
            state = STATE_RESET;
            next_start_address = next_start_address + HORIZONTAL_BLOCK_ADDRESS_STRIDE;
            block_number = block_number + 1;
            %disp('######################');
            %disp(block_number);
            %disp(BLOCKS_PER_ROW);
            if block_number == BLOCKS_PER_ROW,
                swath_number = swath_number + 1;
                next_start_address = swath_number*VERTICAL_BLOCK_ADDRESS_STRIDE;
                block_number = 0;
            end
            next_block_descriptor_addr = next_block_descriptor_addr +  32;
        else

                %disp('Ready is true');
                rvalid_state = true; 
                if (arlen_state ~= 0)                                 
                    if mem_addr == 2, % START_ADDR - MAC for address computation
                        % Compute the next start address
                        % This could be packed in a DSP48 to improve
                        % performance                          
                        rdata_state = next_start_address;
                        %disp('rrrrrrrrrrrrrrrrrr')
                        %disp(remainder);                    
						%disp('eeeeeeeeeeeeeeeeeeee');
						%disp(next_start_address);
                    elseif mem_addr == 0,
                        rdata_state = next_block_descriptor_addr;                    
                    else
                        rdata_state = mem(mem_addr);                                            
                    end;
                    if arlen_state == 1,                        
                        rlast_state =true;
                    end;
                end
            if rready == true,                
                mem_addr = mem_addr+1;                                     
                arlen_state = arlen_state-1;                
            end;
        end;
end;


rvalid = rvalid_state;
rdata = xfix({xlUnsigned,32,0},rdata_state);
rresp = rresp_state;
rlast = rlast_state;
