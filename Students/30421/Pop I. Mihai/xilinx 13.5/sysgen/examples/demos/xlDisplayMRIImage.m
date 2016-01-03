function f = xlDisplayMRIImage( real_channel, imag_channel, tvalid, tready, tlast, x_size, y_size)

persistent blk_real_channel;
persistent blk_imag_channel;
persistent tlastcount;
persistent colcount;
persistent rowcount;
persistent reconstructedimage;

if nargin == 0,
    blk_real_channel = [];
    blk_imag_channel = [];    
    tlastcount = 0;   
    reconstructedimage = [];
    colcount = 1;
    rowcount = 1;
    return;
end;


f = [1];
IMAGE_COLS = x_size;
IMAGE_ROWS = y_size;
BLOCK_SIZE = 1;
BLOCKS_PER_ROW = IMAGE_COLS/BLOCK_SIZE;

if ((tvalid == 1) && (tready == 1)),
    blk_real_channel(end+1) = real_channel;
    blk_imag_channel(end+1) = imag_channel;   
    if tlast==1,
        mri_block_real = blk_real_channel;
        mri_block_imag = blk_imag_channel;
        mri_block = complex(mri_block_real, mri_block_imag);            
        if rowcount <= y_size,
            disp(['row count output of fft: ' num2str(rowcount)]);
            rowcount = rowcount+1;
        else
            disp(['col count output of fft: ' num2str(colcount)]);
            if (isempty(reconstructedimage))
                reconstructedimage = mri_block.';
                %set_param(bdroot(gcb),'SimulationCommand','Stop');
            else    
                reconstructedimage(:,colcount) = mri_block.';                            
            end;
            colcount = colcount + 1;
            if colcount == x_size+1,
                figure(2);
                h = imshow(flipud(fftshift(abs(reconstructedimage))),[]);
                set(get(get(h,'parent'),'parent'),'name','Output Spatial Data');
                set_param(bdroot(gcb),'SimulationCommand','Stop');
            end;
        end;                
        blk_real_channel = [];
        blk_imag_channel = [];                       
        %end;
    end;
end;

