%cd([xilinx.environment.getpath('sysgen') '/examples/demos']);
%-------------------------------------------------------------------------------------
%%Change in .mdl file
%[im_from_gateway,MAP] = imread('macaw_192x108.bmp');
%h = figure(1);
%set(h,'name','Input Image');

%imshow(uint8(im_from_gateway));
%[c_image_rows, c_image_cols] = size(im_from_gateway(:,:,1));
%%for smaller images recommended values of swathe and block size are 16 and 64.

%c_swathe_size =16;
%c_block_size = 64;%Change BLOCK_SIZE in xlDisplayTiles.m
%c_num_fstores = 16;
%-------------------------------------------------------------------------------------
%Change in .mdl file
[im_from_gateway,MAP] = imread('macaw_384x216.bmp');
h = figure(1);
set(h,'name','Input Image');

imshow(uint8(im_from_gateway));
[c_image_rows, c_image_cols] = size(im_from_gateway(:,:,1));
%for smaller images recommended values of swathe and block size are 16 and 64.

c_swathe_size =16;
c_block_size = 64;%Change BLOCK_SIZE in xlDisplayTiles.m
c_num_fstores = 16;
%-------------------------------------------------------------------------------------
%%Change in .mdl file
%[im_from_gateway,MAP] = imread('macaw_576x324.bmp');
%h = figure(1);
%set(h,'name','Input Image');

%imshow(uint8(im_from_gateway));
%[c_image_rows, c_image_cols] = size(im_from_gateway(:,:,1));
%%for smaller images recommended values of swathe and block size are 16 and 64.

%c_swathe_size =64;
%c_block_size = 256;%Change BLOCK_SIZE in xlDisplayTiles.m
%c_num_fstores = 16;
%-------------------------------------------------------------------------------------
%[im_from_gateway,MAP] = imread('macaw_960x540.bmp');
%h = figure(1);
%set(h,'name','Input Image');

%imshow(uint8(im_from_gateway));
%[c_image_rows, c_image_cols] = size(im_from_gateway(:,:,1));
%for smaller images recommended values of swathe and block size are 16 and 64.

%c_swathe_size =256;
%c_block_size = 256;
%c_num_fstores = 16;
%-------------------------------------------------------------------------------------
%[im_from_gateway,MAP] = imread('macaw_1920x1080.bmp');
%h = figure(1);
%set(h,'name','Input Image');

%imshow(uint8(im_from_gateway));
%[c_image_rows, c_image_cols] = size(im_from_gateway(:,:,1));
%%for smaller images recommended values of swathe and block size are 16 and 64.

%c_swathe_size =256;
%c_block_size = 256;
%c_num_fstores = 16;
