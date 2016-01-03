%Construct Input Image
h1 = figure(1);
set(h1,'Name','Original Image');
input_image_resized = imresize(input_image, 4);
imshow(input_image_resized);

%Construct Output Image
output_image(:,:,1) = reshape(R_out,im_size,im_size);
output_image(:,:,2) = reshape(G_out,im_size,im_size);
output_image(:,:,3) = reshape(B_out,im_size,im_size);
output_image_resized = imresize(output_image, 4);
h2 = figure(2);
set(h2,'Name','Blue Green Filtered Image');
imshow(uint8(output_image_resized));


