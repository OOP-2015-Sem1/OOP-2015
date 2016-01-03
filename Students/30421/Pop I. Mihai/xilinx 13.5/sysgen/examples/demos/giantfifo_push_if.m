function [emi_in_s_axi_s2mm_tdata,emi_in_s_axi_s2mm_tstrb,emi_in_s_axi_s2mm_tvalid,emi_in_s_axi_s2mm_tlast, fifo_push_in_s_axis_s2mm_tready]  = giantfifo_push_if( ...
    emi_in_s_axis_s2mm_tready, ...
    fifo_push_in_s_axi_s2mm_tdata, ...
    fifo_push_in_s_axi_s2mm_tstrb, ...
    fifo_push_in_s_axi_s2mm_tvalid,...
    enable_packet_push,            ...
    packet_length)

persistent beatCount,beatCount = xl_state(0,{xlUnsigned,32,0});

%initialization to avoid errors
emi_in_s_axi_s2mm_tdata  = xfix({xlUnsigned,32,0},0);
emi_in_s_axi_s2mm_tstrb  = xfix({xlUnsigned,4,0},0);
emi_in_s_axi_s2mm_tvalid = xfix({xlBoolean},false);
emi_in_s_axi_s2mm_tlast  = xfix({xlBoolean},false);
fifo_push_in_s_axis_s2mm_tready = xfix({xlBoolean},0);


if(enable_packet_push == true)
    beatCount = packet_length;
end


if(beatCount ~= 0)
    fifo_push_in_s_axis_s2mm_tready = emi_in_s_axis_s2mm_tready;

    if(fifo_push_in_s_axi_s2mm_tvalid == true && fifo_push_in_s_axis_s2mm_tready == true)
        emi_in_s_axi_s2mm_tdata  = fifo_push_in_s_axi_s2mm_tdata;
        emi_in_s_axi_s2mm_tstrb  = xfix({xlUnsigned,4,0},65535);
        emi_in_s_axi_s2mm_tvalid = fifo_push_in_s_axi_s2mm_tvalid;
        
%         disp(emi_in_s_axi_s2mm_tdata);
%         disp(beatCount);

        beatCount = beatCount - 1;

        if(beatCount == 0)
            emi_in_s_axi_s2mm_tlast  = xfix({xlBoolean},true);
        else
            emi_in_s_axi_s2mm_tlast  = xfix({xlBoolean},false);
        end
    end
end
%CR#586628 -- Fixed
%Compensates for two cycles delay in data path due to two feedback loops
if(beatCount == 0)
    fifo_push_in_s_axis_s2mm_tready = false;
end
