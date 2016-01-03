cd([xilinx.environment.getpath('sysgen') '/examples/demos']);
[image_1,MAP] = imread('./lion.bmp');
figure(1);
imagesc(image_1);
title('Input image for the first buffer channel');

[image_2,MAP] = imread('./leopard.bmp');
figure(2);
imagesc(image_2);
title('Input image for the second buffer channel');
% Parameters for input images used as default settings
c_rx_rows_1 = 170;
c_rx_cols_1 = 150;
c_rx_rows_2 = 190;
c_rx_cols_2 = 140;

% Parameters for output images used as default settings
c_tx_rows_1 = 170;
c_tx_cols_1 = 150;
c_tx_rows_2 = 190;
c_tx_cols_2 = 140;
