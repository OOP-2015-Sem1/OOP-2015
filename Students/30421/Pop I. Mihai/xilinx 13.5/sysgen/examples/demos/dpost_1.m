function f = dpost_1( hw_out_r, hw_out_g, hw_out_b, tvalid, tlast,tx_rows_1,tx_cols_1)

persistent blk_r_channel;
persistent blk_g_channel;
persistent blk_b_channel;
persistent row;
persistent col;
persistent first_state;

%%%%%%%%%%%%%%%%%%
f = 1;
rows = tx_rows_1;
cols = tx_cols_1;
%%%%%%%%%%%%%%%%%%%

%Initilization

if(isempty(first_state))
  row = 1;
  col = 1;
  first_state = true;
end 


%post processing

if tvalid == 1,
    blk_r_channel(row,col,1) = hw_out_r;
    blk_g_channel(row,col,1) = hw_out_g;
    blk_b_channel(row,col,1) = hw_out_b;    
    
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
       
        figure(3); imagesc(uint8(out_image));
       % imshow(uint8(out_image));
        title(['Output image from Double Buffer Channel 1:',num2str(rows),'x',num2str(cols),' (RxC)']);       
        
     end
 end
end
