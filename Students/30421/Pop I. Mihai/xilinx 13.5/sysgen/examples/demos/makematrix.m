function [matrix] = makematrix(red, blue, green)

min_length = min(min(length(red),length(blue)),length(green));
min_x = floor(sqrt(min_length));
min_y = min_x;
red = red(1:(min_x*min_y));
blue = blue(1:(min_x*min_y));
green = green(1:(min_x*min_y));

re_red = reshape(red, min_x,min_y);
re_red = re_red/256;

re_blue = reshape(blue, min_x,min_y);
re_blue = re_blue/256;

re_green = reshape(green, min_x,min_y);
re_green = re_green/256;

ii(:,:,1) = re_red;
ii(:,:,2) = re_blue;
ii(:,:,3) = re_green;

image(ii);
