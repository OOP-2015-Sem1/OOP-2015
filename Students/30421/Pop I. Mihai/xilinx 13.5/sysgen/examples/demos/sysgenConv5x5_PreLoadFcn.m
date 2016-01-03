
% sysgenConv5x5_imageData is a matrix which is created by reading 
% the xsg_icon_64.jpg image
[sysgenConv5x5_imageData, map] = imread('xsg_icon_64.bmp');

lineSize = size(sysgenConv5x5_imageData,1);
NPixels = size(sysgenConv5x5_imageData,1) * size(sysgenConv5x5_imageData,2);

grayScaleImage = 0;
for I = 1:lineSize,
   for J = 1:lineSize,
       pixel = double(sysgenConv5x5_imageData(I,J));
       mapValue = map(pixel + 1);
       grayPixel = mapValue * 255;
       grayScaleImage(I,J) = uint8(floor(grayPixel));
   end
end

% turn the array into a vector
grayScaleSignal = reshape(grayScaleImage,1,NPixels);

% insert a column of 'time values' in front -- the from workspace
% block expects time followed by data on every row of the input
grayScaleSignal = [ double(0:NPixels-1)'  double(grayScaleSignal)'];

