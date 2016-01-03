function [emi_m_axis_mm2s_tready,fifo_pop_m_axis_mm2s_tdata,fifo_pop_m_axis_mm2s_tstrb,fifo_pop_m_axis_mm2s_tvalid,fifo_pop_m_axis_mm2s_tlast]  = giantfifo_pop_if( ...
emi_m_axis_mm2s_tdata,  ...
emi_m_axis_mm2s_tstrb,  ...
emi_m_axis_mm2s_tvalid, ...
emi_m_axis_mm2s_tlast,  ...
fifo_pop_m_axis_mm2s_tready,...
enable_packet_pop,      ...
packet_length)

persistent beatCount,beatCount = xl_state(0,{xlUnsigned,32,0});

%initialization to avoid errors
emi_m_axis_mm2s_tready        = xfix({xlBoolean},false);
fifo_pop_m_axis_mm2s_tdata    = xfix({xlUnsigned,32,0},0);
fifo_pop_m_axis_mm2s_tstrb    = xfix({xlUnsigned,4,0},0);
fifo_pop_m_axis_mm2s_tvalid   = xfix({xlBoolean},false);
fifo_pop_m_axis_mm2s_tlast    = xfix({xlBoolean},false);

if(enable_packet_pop == true)
    %initialize beat counter
    beatCount = packet_length;
end

if(beatCount ~= 0)
    emi_m_axis_mm2s_tready        = fifo_pop_m_axis_mm2s_tready;
    fifo_pop_m_axis_mm2s_tvalid   = emi_m_axis_mm2s_tvalid;    
    if(fifo_pop_m_axis_mm2s_tready == true && emi_m_axis_mm2s_tvalid == true)
        fifo_pop_m_axis_mm2s_tdata    = emi_m_axis_mm2s_tdata;
        fifo_pop_m_axis_mm2s_tstrb    = emi_m_axis_mm2s_tstrb;
        %TBD Needs fix CR#586627
        %if(emi_m_axis_mm2s_tdata ~= 0)
            
        %end
        fifo_pop_m_axis_mm2s_tlast    = emi_m_axis_mm2s_tlast;
        beatCount = beatCount - 1;
    end 
end
