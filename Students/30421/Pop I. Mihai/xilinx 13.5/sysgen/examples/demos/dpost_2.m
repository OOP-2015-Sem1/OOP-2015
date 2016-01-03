 function f = dpost_2(hw_out_r_2, hw_out_g_2, hw_out_b_2, tvalid, tlast,tx_rows_2,tx_cols_2)
 
persistent blk_r_channel;
persistent blk_g_channel;
persistent blk_b_channel;
persistent row;
persistent col;
persistent first_state;

%%%%%%%%%%%%%%%%%%%%
f = 1;
rows = tx_rows_2;
cols = tx_cols_2;
%%%%%%%%%%%%%%%%%%%


%Initialization
if(isempty(first_state))
  row = 1;
  col = 1;
  first_state = true;
end 


%post processing

if tvalid == 1,
    blk_r_channel(row,col,1) = hw_out_r_2;
    blk_g_channel(row,col,1) = hw_out_g_2;
    blk_b_channel(row,col,1) = hw_out_b_2;    
    
        if (col==cols)
              col=1;
              row=row+1;
             % disp(row);
         else
              col=col+1;
             % disp(col);
         end;
 end;
 if(row == rows) 
     if(col == cols)
        hw_image = blk_r_channel; 
        hw_image(:, :, 2) = blk_g_channel;
        hw_image(:, :, 3) = blk_b_channel;
 
        %out_image = hw_image(WV_real:rows, WH:cols,:);
        out_image = hw_image(1:rows, 1:cols,:);
        
        figure(4); imagesc(uint8(out_image));
        %imshow(uint8(out_image));
        title(['Output image from Double Buffer Channel 2:',num2str(rows),'x',num2str(cols),' (RxC)']);       

     end
 end
end
