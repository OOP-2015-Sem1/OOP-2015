%cd( fullfile(xilinx.environment.getpath('sysgen'),'examples','demos') );
load videoFrame;
%Setting up System parameters
c_image_rows = 120;
c_image_cols = 160;
c_vsync = 1;
c_hsync = 16;
c_word_size = 4;%In Bytes
c_num_fstores = 1;
pixel_period = 1;