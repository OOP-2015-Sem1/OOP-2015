%%%% Function reassembles the sinked data from the alpha blending simulation for the user
%%%% display

%%%%%%%%%%%%%%%%%%%% Alpha -1 %%%%%%%%%%%%%%
red_inter1 = Red_Alpha1';
blue_inter1 = Blue_Alpha1';
green_inter1 = Green_Alpha1';

red_new = reshape(red_inter1,L,W);
blue_new = reshape(blue_inter1,L,W);
green_new = reshape(green_inter1,L,W);

ABlening_image4(:,:,1) = uint8(red_new);
ABlening_image4(:,:,2) = uint8(green_new);
ABlening_image4(:,:,3) = uint8(blue_new);
% figure;
% image(ABlening_image4);
     subplot(2,2,3);
      image(ABlening_image4), ...
        axis equal, axis square, axis off, title 'Alpha-0.3';

%%%%%%%%%%%%%%%%%%%% Alpha -2 %%%%%%%%%%%%%%
red_inter1 = Red_Alpha2';
blue_inter1 = Blue_Alpha2';
green_inter1 = Green_Alpha2';

red_new = reshape(red_inter1,L,W);
blue_new = reshape(blue_inter1,L,W);
green_new = reshape(green_inter1,L,W);

ABlening_image4(:,:,1) = uint8(red_new);
ABlening_image4(:,:,2) = uint8(green_new);
ABlening_image4(:,:,3) = uint8(blue_new);
% figure;
% image(ABlening_image4);
     subplot(2,2,4);
      image(ABlening_image4), ...
        axis equal, axis square, axis off, title 'Alpha-0.6';