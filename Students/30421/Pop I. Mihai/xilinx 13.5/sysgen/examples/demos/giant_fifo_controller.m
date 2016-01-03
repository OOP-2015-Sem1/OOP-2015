function [emi_m_axi_lite_awvalid,emi_m_axi_lite_awaddr,emi_m_axi_lite_wvalid,emi_m_axi_lite_wdata,emi_m_axi_lite_bready,emi_m_axi_lite_arvalid,emi_m_axi_lite_araddr,emi_m_axi_lite_rready,emi_sg_s_axi_arready,emi_sg_s_axi_rvalid,emi_sg_s_axi_rlast,emi_sg_s_axi_rdata,emi_sg_s_axi_rresp,enable_packet_push,enable_packet_pop,fifo_full,fifo_empty] = giant_fifo_controller( ...
    emi_m_axi_lite_awready, ...
    emi_m_axi_lite_wready,  ...
    emi_m_axi_lite_bresp,   ...
    emi_m_axi_lite_bvalid,  ...
    emi_m_axi_lite_arready, ...
    emi_m_axi_lite_rvalid,  ...
    emi_m_axi_lite_rdata,   ...
    emi_m_axi_lite_rresp,   ...
    emi_sg_s_axi_araddr,    ...
    emi_sg_s_axi_arlen,     ...
    emi_sg_s_axi_arsize,    ...
    emi_sg_s_axi_arburst,   ...
    emi_sg_s_axi_arprot,    ...
    emi_sg_s_axi_arcache,   ...
    emi_sg_s_axi_arvalid,   ...
    emi_sg_s_axi_rready,    ...
    packet_length,          ...
    s2mm_trf_complete,      ...
    mm2s_trf_complete,      ...
    aresetn) 
%initiate
persistent push_pointer,push_pointer         = xl_state(0,{xlUnsigned,30,0});
persistent pop_pointer, pop_pointer          = xl_state(0,{xlUnsigned,30,0});
persistent pushSgBeatCount,pushSgBeatCount   = xl_state(0,{xlUnsigned,3,0});
persistent popSgBeatCount,popSgBeatCount     = xl_state(0,{xlUnsigned,3,0});

persistent memSize,memSize                   = xl_state(6777216,{xlUnsigned,32,0});

persistent pushSgBlockDescriptorWords,pushSgBlockDescriptorWords=xl_state([0, 0, 0, 0, 1, 64, 0],{xlUnsigned,16,0});
persistent popSgBlockDescriptorWords,popSgBlockDescriptorWords=xl_state([0, 0, 0, 0, 1, 64, 0],{xlUnsigned,16,0});
persistent fifo_full_status,fifo_full_status = xl_state(false,{xlBoolean});
persistent fifo_empty_status,fifo_empty_status = xl_state(true,{xlBoolean});

%S2MM block descriptor data
persistent PUSH_CURPTR_REGADDR,PUSH_CURPTR_REGADDR    = xl_state(0,{xlUnsigned,32,0});
persistent PUSH_DMACR_REGADDR,PUSH_DMACR_REGADDR      = xl_state(0,{xlUnsigned,32,0});
persistent PUSH_TAILPTR_REGADDR,PUSH_TAILPTR_REGADDR  = xl_state(0,{xlUnsigned,32,0});
persistent PUSH_CURPTR_REGDATA,PUSH_CURPTR_REGDATA    = xl_state(0,{xlUnsigned,32,0});
persistent PUSH_TAILPTR_REGDATA,PUSH_TAILPTR_REGDATA  = xl_state(0,{xlUnsigned,32,0});
persistent PUSH_DMACR_REGDATA,PUSH_DMACR_REGDATA      = xl_state(0,{xlUnsigned,32,0});

%MM2S block descriptor data
persistent POP_CURPTR_REGADDR,POP_CURPTR_REGADDR      = xl_state(0,{xlUnsigned,32,0});
persistent POP_DMACR_REGADDR,POP_DMACR_REGADDR        = xl_state(0,{xlUnsigned,32,0});
persistent POP_TAILPTR_REGADDR,POP_TAILPTR_REGADDR    = xl_state(0,{xlUnsigned,32,0});
persistent POP_CURPTR_REGDATA,POP_CURPTR_REGDATA      = xl_state(0,{xlUnsigned,32,0});
persistent POP_TAILPTR_REGDATA,POP_TAILPTR_REGDATA    = xl_state(0,{xlUnsigned,32,0});
persistent POP_DMACR_REGDATA,POP_DMACR_REGDATA        = xl_state(0,{xlUnsigned,32,0});

emi_m_axi_lite_awvalid = xfix({xlBoolean},false);
emi_m_axi_lite_awaddr  = xfix({xlUnsigned,32,0},0);
emi_m_axi_lite_wvalid  = xfix({xlBoolean},false);
emi_m_axi_lite_wdata   = xfix({xlUnsigned,32,0},0);
emi_m_axi_lite_bready  = xfix({xlBoolean},false);
emi_m_axi_lite_arvalid = xfix({xlBoolean},false);
emi_m_axi_lite_araddr  = xfix({xlUnsigned,32,0},0);
emi_m_axi_lite_rready  = xfix({xlBoolean},false);

emi_sg_s_axi_arready = xfix({xlBoolean},false);
emi_sg_s_axi_rvalid  = xfix({xlBoolean},false);
emi_sg_s_axi_rlast   = xfix({xlBoolean},false);
emi_sg_s_axi_rdata   = xfix({xlUnsigned,32,0},0);
emi_sg_s_axi_rresp   = xfix({xlUnsigned,2,0},0);

enable_packet_push     = xfix({xlBoolean},false);
enable_packet_pop      = xfix({xlBoolean},false);


%States for AXI-Lite register interface transfer
persistent rtf_sv,rtf_sv         = xl_state(0,{xlUnsigned,32,0});
persistent rtf_arbiter,rtf_arbiter         = xl_state(0,{xlUnsigned,32,0});
RTF_ARB_S2MM=0;
RTF_ARB_MM2S=1;

persistent s2mm_rtf_waiting_for_trf, s2mm_rtf_waiting_for_trf = xl_state(false,{xlBoolean});
persistent mm2s_rtf_waiting_for_trf, mm2s_rtf_waiting_for_trf = xl_state(false,{xlBoolean});

RTF_RESET = 99;

RTF_INIT=0;
S2MM_REG_IFACE_READY_FOR_TRF=1;
MM2S_REG_IFACE_READY_FOR_TRF=2;

RTF_S2MM_SEND_CURPTR_WADDR=3;
RTF_S2MM_SEND_CURPTR_WDATA=4;
RTF_S2MM_SEND_CURPTR_BRSP =10;
RTF_S2MM_SEND_TAILPTR_WADDR=5;
RTF_S2MM_SEND_TAILPTR_WDATA=6;
RTF_S2MM_SEND_TAILPTR_BRSP=11;
RTF_S2MM_SEND_DMACR_WADDR=7;
RTF_S2MM_SEND_DMACR_WDATA=8;
RTF_S2MM_SEND_DMACR_BRSP=12;

RTF_S2MM_SEND_DMACR_WADDR_1=13;
RTF_S2MM_SEND_DMACR_WDATA_1=14;
RTF_S2MM_SEND_DMACR_BRSP_1=15;

% RTF_S2MM_SEND_RESET_DMACR_WADDR=13;
% RTF_S2MM_SEND_RESET_DMACR_WDATA=14;
% RTF_S2MM_SEND_RESET_DMACR_BRSP=15;

RTF_S2MM_SEND_CURPTR_RADDR = 40;
RTF_S2MM_READ_CURPTR_RDATA = 41;
RTF_S2MM_SEND_TAILPTR_RADDR = 42;
RTF_S2MM_READ_TAILPTR_RDATA = 43;
RTF_S2MM_SEND_DMACR_RADDR = 44;
RTF_S2MM_READ_DMACR_RDATA = 45;

RTF_S2MM_SEND_DMASR_RADDR = 46;
RTF_S2MM_READ_DMASR_RDATA = 47;


RTF_MM2S_SEND_CURPTR_WADDR=20;
RTF_MM2S_SEND_CURPTR_WDATA=21;
RTF_MM2S_SEND_CURPTR_BRSP=30;
RTF_MM2S_SEND_TAILPTR_WADDR=22;
RTF_MM2S_SEND_TAILPTR_WDATA=23;
RTF_MM2S_SEND_TAILPTR_BRSP=31;
RTF_MM2S_SEND_DMACR_WADDR=24;
RTF_MM2S_SEND_DMACR_WDATA=25;
RTF_MM2S_SEND_DMACR_BRSP=32;

RTF_MM2S_SEND_CURPTR_RADDR = 50;
RTF_MM2S_READ_CURPTR_RDATA = 51;
RTF_MM2S_SEND_TAILPTR_RADDR = 52;
RTF_MM2S_READ_TAILPTR_RDATA = 53;
RTF_MM2S_SEND_DMACR_RADDR = 54;
RTF_MM2S_READ_DMACR_RDATA = 55;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%S2MM state machine
 
%if it is first beat in push packet, initialize values for axi-lite
%transfer 
S2MM_TRF_WAITING_FOR_TRF          = 0;
S2MM_TRF_INIT                     = 1;
S2MM_TRF_UPDATE_FIFO_FULL_STATUS  = 2;
S2MM_TRF_RESET_STATE              = 3;

MM2S_TRF_WAITING_FOR_TRF          = 0;
MM2S_TRF_INIT                     = 1;
MM2S_TRF_UPDATE_FIFO_FULL_STATUS  = 2;
M2SS_TRF_RESET_STATE              = 3;
   
persistent s2mm_trf,s2mm_trf=xl_state(S2MM_TRF_UPDATE_FIFO_FULL_STATUS,{xlUnsigned,32,0});
persistent mm2s_trf,mm2s_trf=xl_state(MM2S_TRF_UPDATE_FIFO_FULL_STATUS,{xlUnsigned,32,0});

if(aresetn == false)
    s2mm_trf = S2MM_TRF_RESET_STATE;
    mm2s_trf = M2SS_TRF_RESET_STATE;
    rtf_sv   = RTF_RESET;
end
if(aresetn == true && rtf_sv == RTF_RESET)
    s2mm_trf = S2MM_TRF_UPDATE_FIFO_FULL_STATUS;
    mm2s_trf = MM2S_TRF_UPDATE_FIFO_FULL_STATUS;
    rtf_sv   = RTF_INIT;
end

if(s2mm_trf == S2MM_TRF_WAITING_FOR_TRF)
    if(s2mm_trf_complete == true )
         push_pointer = push_pointer + xl_lsh(packet_length,2);
         if(push_pointer == memSize)
             push_pointer = 0;
         end
        s2mm_trf = S2MM_TRF_INIT;
    end
end
if(mm2s_trf == MM2S_TRF_WAITING_FOR_TRF)
    if(mm2s_trf_complete == true )
        pop_pointer = pop_pointer + xl_lsh(packet_length,2);       
        if(pop_pointer == memSize)
            pop_pointer = 0;
        end

        mm2s_trf = MM2S_TRF_INIT;
    end
end

if(s2mm_trf == S2MM_TRF_INIT)
    if(push_pointer ~= pop_pointer)
        fifo_empty_status             = xfix({xlBoolean},false);
    end
    s2mm_trf = S2MM_TRF_UPDATE_FIFO_FULL_STATUS;    
end


if(mm2s_trf == MM2S_TRF_INIT)
    %mm2s can make fifo_full_status = false and fifo_empty_status=true
    if((     ((push_pointer > pop_pointer) && (push_pointer - pop_pointer == 64)) ...
            ||  ((push_pointer < pop_pointer) && (pop_pointer - push_pointer == 64)) ) == false)
        fifo_full_status             = xfix({xlBoolean},false);
    end     
    
    if(push_pointer == pop_pointer)
        fifo_empty_status             = xfix({xlBoolean},true);
    end
    mm2s_trf = MM2S_TRF_UPDATE_FIFO_FULL_STATUS;    
end

if(s2mm_trf == S2MM_TRF_UPDATE_FIFO_FULL_STATUS)
    if(fifo_full_status == false)
        PUSH_CURPTR_REGADDR    = xfix({xlUnsigned,32,0},56);%CURPTR
        PUSH_DMACR_REGADDR     = xfix({xlUnsigned,32,0},48);%DMACR
        PUSH_TAILPTR_REGADDR   = xfix({xlUnsigned,32,0},64);%TAILPTR
        PUSH_CURPTR_REGDATA    = xfix({xlUnsigned,32,0},128);
        PUSH_DMACR_REGDATA     = xfix({xlUnsigned,32,0},65555);
        PUSH_TAILPTR_REGDATA   = xfix({xlUnsigned,32,0},128);
        s2mm_rtf_waiting_for_trf = xfix({xlBoolean},true);
        s2mm_trf = S2MM_TRF_WAITING_FOR_TRF;
    end
end

if(mm2s_trf == MM2S_TRF_UPDATE_FIFO_FULL_STATUS)
    if(fifo_empty_status == false)
        POP_CURPTR_REGADDR     = xfix({xlUnsigned,32,0},8);%CURPTR
        POP_DMACR_REGADDR      = xfix({xlUnsigned,32,0},0);%DMACR
        POP_TAILPTR_REGADDR    = xfix({xlUnsigned,32,0},16);%TAILPTR
        POP_CURPTR_REGDATA     = xfix({xlUnsigned,32,0},256);
        POP_DMACR_REGDATA      = xfix({xlUnsigned,32,0},65555);
        POP_TAILPTR_REGDATA    = xfix({xlUnsigned,32,0},256);
        mm2s_rtf_waiting_for_trf = xfix({xlBoolean},true);
        mm2s_trf = MM2S_TRF_WAITING_FOR_TRF;
    end
end

if(rtf_sv == RTF_INIT)
    if(s2mm_rtf_waiting_for_trf == false && rtf_arbiter == RTF_ARB_MM2S)
        rtf_arbiter = RTF_ARB_S2MM;
    end   
    if(mm2s_rtf_waiting_for_trf == false && rtf_arbiter == RTF_ARB_S2MM)
        rtf_arbiter = RTF_ARB_MM2S;
    end
    
    
    if(s2mm_rtf_waiting_for_trf == true && rtf_arbiter == RTF_ARB_MM2S)
        rtf_sv = S2MM_REG_IFACE_READY_FOR_TRF;
        s2mm_rtf_waiting_for_trf = false;
    end
    
    if(mm2s_rtf_waiting_for_trf == true && rtf_arbiter == RTF_ARB_S2MM)
        rtf_sv = MM2S_REG_IFACE_READY_FOR_TRF;
        mm2s_rtf_waiting_for_trf = false;
    end    
end

%AXI-Lite RTF state machine

switch rtf_sv,
    case S2MM_REG_IFACE_READY_FOR_TRF,
         rtf_sv = RTF_S2MM_SEND_CURPTR_WADDR;

    case RTF_S2MM_SEND_CURPTR_WADDR,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = PUSH_CURPTR_REGADDR;
        if(emi_m_axi_lite_awready == true)    
            rtf_sv = RTF_S2MM_SEND_CURPTR_WDATA;
        end 
        
    case RTF_S2MM_SEND_CURPTR_WDATA,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = PUSH_CURPTR_REGDATA;
        if(emi_m_axi_lite_wready == true)
            rtf_sv = RTF_S2MM_SEND_CURPTR_BRSP;
        end
        
    case RTF_S2MM_SEND_CURPTR_BRSP,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);          
            rtf_sv = RTF_S2MM_SEND_DMACR_WADDR;
        end
    
    case RTF_S2MM_SEND_DMACR_WADDR,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = PUSH_DMACR_REGADDR;
        if(emi_m_axi_lite_awready == true)
            rtf_sv = RTF_S2MM_SEND_DMACR_WDATA;
        end
    
    case RTF_S2MM_SEND_DMACR_WDATA,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = PUSH_DMACR_REGDATA;
        if(emi_m_axi_lite_wready == true)
            rtf_sv = RTF_S2MM_SEND_DMACR_BRSP;
        end

    case RTF_S2MM_SEND_DMACR_BRSP,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);
            rtf_sv = RTF_S2MM_SEND_DMACR_RADDR;
        end

    case RTF_S2MM_SEND_DMACR_RADDR,
        emi_m_axi_lite_arvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_araddr  = PUSH_DMACR_REGADDR;
        if(emi_m_axi_lite_arready == true)
            rtf_sv = RTF_S2MM_READ_DMACR_RDATA;  
        end
        
    case RTF_S2MM_READ_DMACR_RDATA,
        if(emi_m_axi_lite_rvalid == true)
             emi_m_axi_lite_rready = xfix({xlBoolean},true);
            if(xl_and(emi_m_axi_lite_rdata,1) == 1)
                 rtf_sv = RTF_S2MM_SEND_DMASR_RADDR;
            else
                rtf_sv = RTF_S2MM_SEND_DMACR_WADDR;
            end
        end  

    case RTF_S2MM_SEND_DMASR_RADDR,
        emi_m_axi_lite_arvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_araddr  = 52;%PUSH_DMASR_REGADDR;
        if(emi_m_axi_lite_arready == true)
            rtf_sv = RTF_S2MM_READ_DMASR_RDATA;
        end

    case RTF_S2MM_READ_DMASR_RDATA,
        if(emi_m_axi_lite_rvalid == true)
             emi_m_axi_lite_rready = xfix({xlBoolean},true);
            if((xl_and(emi_m_axi_lite_rdata,1) == 0) && (xl_and(emi_m_axi_lite_rdata,2) == 0) )
               rtf_sv = RTF_S2MM_SEND_DMACR_WADDR_1; 
            else
                rtf_sv = RTF_S2MM_SEND_DMACR_WADDR;
            end
        end  
    
    case RTF_S2MM_SEND_DMACR_WADDR_1,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = PUSH_DMACR_REGADDR;
        if(emi_m_axi_lite_awready == true)
            rtf_sv = RTF_S2MM_SEND_DMACR_WDATA_1;
        end

    case RTF_S2MM_SEND_DMACR_WDATA_1,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = PUSH_DMACR_REGDATA;
        if(emi_m_axi_lite_wready == true)
            rtf_sv = RTF_S2MM_SEND_DMACR_BRSP_1;
        end

    case RTF_S2MM_SEND_DMACR_BRSP_1,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);         
            rtf_sv = RTF_S2MM_SEND_TAILPTR_WADDR;
        end
    
    case RTF_S2MM_SEND_TAILPTR_WADDR,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = PUSH_TAILPTR_REGADDR;
        if(emi_m_axi_lite_awready == true)
            rtf_sv = RTF_S2MM_SEND_TAILPTR_WDATA;
        end
    
    case RTF_S2MM_SEND_TAILPTR_WDATA,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = PUSH_TAILPTR_REGDATA;
        if(emi_m_axi_lite_wready == true)
             rtf_sv = RTF_S2MM_SEND_TAILPTR_BRSP;
        end
        
    case RTF_S2MM_SEND_TAILPTR_BRSP,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);        
            rtf_arbiter=RTF_ARB_S2MM;
            rtf_sv = RTF_INIT;
        end

    case MM2S_REG_IFACE_READY_FOR_TRF,
        rtf_sv = RTF_MM2S_SEND_CURPTR_WADDR;

    case RTF_MM2S_SEND_CURPTR_WADDR,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = POP_CURPTR_REGADDR;
        if(emi_m_axi_lite_awready == true)
            rtf_sv = RTF_MM2S_SEND_CURPTR_WDATA;
        end

    case RTF_MM2S_SEND_CURPTR_WDATA,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = POP_CURPTR_REGDATA;   
        if(emi_m_axi_lite_wready == true)
            rtf_sv = RTF_MM2S_SEND_CURPTR_BRSP;
        end

    case RTF_MM2S_SEND_CURPTR_BRSP,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);  
            rtf_sv = RTF_MM2S_SEND_DMACR_WADDR;
        end
        
    case RTF_MM2S_SEND_DMACR_WADDR,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = POP_DMACR_REGADDR;
        if(emi_m_axi_lite_awready == true)
            rtf_sv = RTF_MM2S_SEND_DMACR_WDATA;
        end
    case RTF_MM2S_SEND_DMACR_WDATA,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = POP_DMACR_REGDATA;   
        if(emi_m_axi_lite_wready == true)
            rtf_sv = RTF_MM2S_SEND_DMACR_BRSP;
        end
    case RTF_MM2S_SEND_DMACR_BRSP,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);        
            rtf_sv = RTF_MM2S_SEND_TAILPTR_WADDR;
        end
        
    case RTF_MM2S_SEND_TAILPTR_WADDR,
        emi_m_axi_lite_awvalid = xfix({xlBoolean},true);
        emi_m_axi_lite_awaddr  = POP_TAILPTR_REGADDR;    
        if(emi_m_axi_lite_awready == true)
            rtf_sv = RTF_MM2S_SEND_TAILPTR_WDATA;
        end
        
    case RTF_MM2S_SEND_TAILPTR_WDATA,
        emi_m_axi_lite_wvalid  = xfix({xlBoolean},true);
        emi_m_axi_lite_wdata   = POP_TAILPTR_REGDATA;
        if(emi_m_axi_lite_wready == true)
            rtf_sv = RTF_MM2S_SEND_TAILPTR_BRSP;
        end
        
    case RTF_MM2S_SEND_TAILPTR_BRSP,
        if(emi_m_axi_lite_bvalid == true && emi_m_axi_lite_bresp == 0)
            emi_m_axi_lite_bready  = xfix({xlBoolean},true);       
            rtf_arbiter=RTF_ARB_MM2S;
            rtf_sv = RTF_INIT;
        end
end;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%State machine for scatter gather engine.
persistent sg_sv,sg_sv         = xl_state(0,{xlUnsigned,32,0});
%Scatter Gather Engine states

SG_WAIT_FOR_READ_ADDR=0;
SG_SEND_S2MM_READ_DATA=1;
SG_SEND_MM2S_READ_DATA=2;
SG_RESET_STATE=3;

emi_sg_s_axi_arready   = xfix({xlBoolean},true);
emi_sg_s_axi_rvalid    = xfix({xlBoolean},false);
emi_sg_s_axi_rlast     = xfix({xlBoolean},false);
emi_sg_s_axi_rdata     = xfix({xlUnsigned,32,0},0);
emi_sg_s_axi_rresp     = xfix({xlUnsigned,2,0},0);
 
if(aresetn == false)
   sg_sv = SG_RESET_STATE;
end
if(aresetn == true && sg_sv == SG_RESET_STATE)
    sg_sv = SG_WAIT_FOR_READ_ADDR;
end

 if sg_sv == SG_WAIT_FOR_READ_ADDR,
        if(emi_sg_s_axi_arvalid == true)
            if(emi_sg_s_axi_araddr == PUSH_CURPTR_REGDATA)
                sg_sv = SG_SEND_S2MM_READ_DATA;
                pushSgBeatCount = 7;
            end
            if(emi_sg_s_axi_araddr == POP_CURPTR_REGDATA)
                sg_sv = SG_SEND_MM2S_READ_DATA; 
                popSgBeatCount = 7;                
            end              
        end
 end
 
 if sg_sv == SG_SEND_S2MM_READ_DATA,
        emi_sg_s_axi_rvalid = true;
        emi_sg_s_axi_rresp  = 0;%OKAY
        if(emi_sg_s_axi_rready == true)
           %count the beats
           pushSgBeatCount = pushSgBeatCount - 1;
           emi_sg_s_axi_rvalid = true;
           dataIndex = 6 - pushSgBeatCount;
           if(dataIndex ~= 2)
                emi_sg_s_axi_rdata  = pushSgBlockDescriptorWords(dataIndex);
           else
                emi_sg_s_axi_rdata  = push_pointer;
            end
            emi_sg_s_axi_rresp  = 0;
            if(pushSgBeatCount == 0)
                emi_sg_s_axi_rlast  = true;
                enable_packet_push  = xfix({xlBoolean},true);
                sg_sv = SG_WAIT_FOR_READ_ADDR;
            end
        end      
 end
 if sg_sv == SG_SEND_MM2S_READ_DATA,        
        if(emi_sg_s_axi_rready == true)
            %count the beats
            popSgBeatCount = popSgBeatCount - 1;
            emi_sg_s_axi_rvalid = true;
            dataIndex = 6 - popSgBeatCount;
            if(dataIndex ~= 2)
                emi_sg_s_axi_rdata  = popSgBlockDescriptorWords(dataIndex);
            else
                emi_sg_s_axi_rdata  = pop_pointer;
            end
            emi_sg_s_axi_rresp  = 0;
            if(popSgBeatCount == 0)
                emi_sg_s_axi_rlast  = true;
                enable_packet_pop  = xfix({xlBoolean},true);
                sg_sv = SG_WAIT_FOR_READ_ADDR;             
            end
        end
end


% %--------------------------------------------------------------------------

fifo_empty             = fifo_empty_status;
fifo_full              = fifo_full_status;

end 

