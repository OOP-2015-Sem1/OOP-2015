function [ awvalid, awaddr, wvalid, wdata, bready, arvalid, araddr, rready, done, read_value ] = xlAXILiteVDMACommandDriver( ...
    addr, ...
    value, ...
    xc_write, ...
    xc_read, ...
    awready, ...
    wready, ...
    bresp, ...
    bvalid, ...
    arready, ...
    rvalid, ...
    rdata, ...
    rresp, ...
    aresetn ...
    )
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Implements a state machine to interface with the AXI Lite Interface.
    % Used for Writing and reading the control registers of the AXI VDMA.
    % This is NOT a duplex interface in that either we can read or write to
    % the VDMA registers and cannot perform both simultaneously. This is a
    % helper block that transforms the AXI Lite Control inteface of the
    % VDMA Interface block into a simpler read/write interface.
    % The xc_write, xc_read, value, addr, ready are considered to be the 
    % public interface. The other signals are for the AXI Lite Interface 
    % on the device.
    %Inputs :
    %   xc_write : A signal to indicate that a control register is written to
    %   addr : The Memory Mapped address of the control register on the
    %            device
    %   value : Data for the control register to be written to
    %   xc_read : A signal to indicate that a control register is read from
    %Outputs :
    %   read_value : Data read from a control register
    %   done :A single pulse generated when the transaction is completed
    %       done pulse is generated to indicate the data has been written to
    %       the control register and also to indicate a valid value is
    %       available on the read_value. This pulse is only one cycle wide.    
    %
    % NOTE : The other ports not described are standard AXI Lite
    % Interface signals and must be connected to their counterpart on the
    % VDMA Interface block.
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    %Enumerating One Hot State Machines States
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Wait for User write_en/xc_read
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    STATE_WAIT_FOR_USER_COMMAND = 0;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Addres Valid Sent to Device and Waiting for Reponse
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    
    SEND_WRITE_ADDRESS_TO_DEVICE = 1;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Wait for Write address acknowledgement from the device
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%        
    WAIT_FOR_WRITE_ADDRESS_READY_FROM_DEVICE = 2;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Wait for write data ready
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%        
    WAIT_FOR_WRITE_DATA_READY_FROM_DEVICE = 3;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    %  Wait for write resp valid
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%        
    WAIT_FOR_WRITE_RESP_VALID_FROM_DEVICE = 4;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Address Valid Sent to Device and Waiting for Reponse
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    SEND_READ_ADDRESS_TO_DEVICE = 5;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Addres Valid Sent to Device and Waiting for Reponse
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    WAIT_FOR_READ_ADDRESS_READY_FROM_DEVICE = 6;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Read data obtained from the device
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    
    WAIT_FOR_READ_DATA_VALID_FROM_DEVICE = 7;
    
    STATE_RESET = 8;
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % The Address of the Control Register that is to be read from or
    % written to
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    persistent addr_state, addr_state = xl_state(0, {xlUnsigned, 32, 0});
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % The state variable for for the AXILite VDMA Command Generator
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    
    persistent state, state = ...
        xl_state(STATE_RESET, {xlUnsigned, 32, 0});
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % State variable for value
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    
    persistent value_state, value_state = ...
        xl_state(0, {xlUnsigned, 32, 0});
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % State Variables for AXI Signals
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    persistent awvalid_state, awvalid_state = xl_state(false, {xlBoolean, 1, 0});
    persistent awaddr_state, awaddr_state = xl_state(0, {xlUnsigned, 32, 0});    
    persistent wvalid_state, wvalid_state = xl_state(false, {xlBoolean, 1, 0});
    persistent wdata_state, wdata_state = xl_state(0, {xlUnsigned, 32, 0});    
    persistent rdata_state, rdata_state = xl_state(0, {xlUnsigned, 32, 0});    
    persistent bready_state, bready_state = xl_state(0, {xlBoolean, 1, 0});    
    persistent arvalid_state, arvalid_state = xl_state(false, {xlBoolean, 1, 0});
    persistent araddr_state, araddr_state = xl_state(0, {xlUnsigned, 32, 0});    
    persistent rready_state, rready_state = xl_state(false, {xlBoolean, 1, 0});
    
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % State Variable to indicate a transaction is complete
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    persistent done_state, done_state = ...
        xl_state(false, {xlBoolean,1,0});
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Assign all AXI Signals
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    awvalid = awvalid_state;
    awaddr = awaddr_state;
    wdata = wdata_state;
    wvalid = wvalid_state; 
    bready = bready_state;
    done = done_state;
    
    arvalid = arvalid_state;
    araddr = araddr_state;
    rready = rready_state;
    
    read_value = rdata_state;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Do Reset
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    if aresetn == false,
        state = STATE_RESET;        
        rdata_state = 0;
    end;
    switch state
        case STATE_RESET,            
            done_state = false;
            awvalid_state = false;
            wvalid_state = false;
%             bready_state = false;
            state = STATE_WAIT_FOR_USER_COMMAND;
%           disp('Reset');

        case STATE_WAIT_FOR_USER_COMMAND, 
            if xc_write == true,
                addr_state = addr;
                value_state = value;
                state = SEND_WRITE_ADDRESS_TO_DEVICE; 
%               disp('write');
            elseif xc_read == true,
                    addr_state = addr;
                    value_state = value;
                    state = SEND_READ_ADDRESS_TO_DEVICE;     
%                   disp('read');
            end
        case SEND_WRITE_ADDRESS_TO_DEVICE,
            awvalid_state = true;
            awaddr_state = addr_state;
            state = WAIT_FOR_WRITE_ADDRESS_READY_FROM_DEVICE;
%           disp('write addr');
        case WAIT_FOR_WRITE_ADDRESS_READY_FROM_DEVICE,
            if awready == true,
                awvalid_state = false;
                bready_state = false;
                wvalid_state = true;
                wdata_state = value_state;
                state = WAIT_FOR_WRITE_DATA_READY_FROM_DEVICE;
%               disp('write addr ready');
            end;
        case WAIT_FOR_WRITE_DATA_READY_FROM_DEVICE,
            if wready == true,
                wvalid_state = false;
                state = WAIT_FOR_WRITE_RESP_VALID_FROM_DEVICE;
%               disp('write data ready');
            end;
        case WAIT_FOR_WRITE_RESP_VALID_FROM_DEVICE,
            if bvalid == true,
                bready_state = true;
                done_state = true;
                state = STATE_RESET;
%               disp('Write rsp');
            end;
%                 if (bresp ~= 0)
%                     disp(bresp);
%                     error('Write response is invalid. Please check the control register information');
%                 end;
%                 bready_state = true;
%                 done_state = true;
%                 state = STATE_RESET;                
%             end;
        case SEND_READ_ADDRESS_TO_DEVICE,            
            arvalid_state = true;
            araddr_state = addr_state;
            state = WAIT_FOR_READ_ADDRESS_READY_FROM_DEVICE;
%           disp('Read Addr');
        case WAIT_FOR_READ_ADDRESS_READY_FROM_DEVICE,
            if arready == true,                
                arvalid_state = false;
                rready_state = true;
                state = WAIT_FOR_READ_DATA_VALID_FROM_DEVICE;
%               disp('Read addr ready');
            end;
        case WAIT_FOR_READ_DATA_VALID_FROM_DEVICE,             
            if rvalid == true,
                rready_state = false;
                rdata_state = rdata;
                done_state = true;
                state = STATE_RESET;
%               disp('Read Data ready');
%                 if rresp ~= 0,
%                     error('Read response is invalid. Please check the control register information');
%                 end;
            end;                    
        otherwise,
            error('Illegal State Encountered. Cannot recover');
    end;                
    
    
