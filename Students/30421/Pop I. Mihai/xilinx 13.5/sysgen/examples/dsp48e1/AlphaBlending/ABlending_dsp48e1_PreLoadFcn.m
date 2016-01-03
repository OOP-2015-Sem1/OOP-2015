
% Alpha Blending Demo takes input from Image files
% Separate out the red green and blue components from the image.

% clear all;

h = figure;
clf;
set(h,'Name','  Alpha Blending Results');
set(h,'Position',[100 50 1000 800]);

% ABlening_image2 = imread('im.bmp');
% ABlening_image1 = imread('im1.bmp');

ABlening_image2 = imread('2.png');
ABlening_image1 = imread('1.png');
%%% Basic comparing of the image sizes 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
NPixels1 = size(ABlening_image1,1) * size(ABlening_image1,2);
s11 = size(ABlening_image1,1);
s12 = size(ABlening_image1,2);
NPixels2 = size(ABlening_image2,1) * size(ABlening_image2,2);
s21 = size(ABlening_image2,1);
s22 = size(ABlening_image2,2);

Size1 = s11*s12;
Size2 = s21*s22;
if(Size1>Size2)
    L = s11;
    W = s12;
else
    L = s21;
    W = s22;
end
if(NPixels1>NPixels2)
    ABlening_image3 = ABlening_image1;
    ABlening_image3(1:s11,1:s12,1:3) = 0;
    Rowstart = round((s11-s21)/2);
    ColumnStart = round((s12-s22)/2);
    ABlening_image3(1+Rowstart:Rowstart+s21,1+ColumnStart:ColumnStart+s22,1:3) = ABlening_image2(1:s21,1:s22,1:3);
    ABlening_image2 = ABlening_image3;
end
    

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%% Structure image1 to match input format
    redSignal = ABlening_image1(:,:,1);
    greenSignal = ABlening_image1(:,:,2);
    blueSignal = ABlening_image1(:,:,3);

     subplot(2,2,1);
      image(ABlening_image1), ...
        axis equal, axis square, axis off, title 'Input Image-1';

    NPixels = size(redSignal,1) * size(redSignal,2);
    % turn them into vectors (they were arrays):

    redSignal = reshape(redSignal,1,NPixels);
    greenSignal = reshape(greenSignal,1,NPixels);
    blueSignal = reshape(blueSignal,1,NPixels);

    % insert a column of 'time values' in front -- the from workspace
    % block expects time followed by data on every row of the input

    redSignal1 = [ double(0:NPixels-1)'  double(redSignal)'];
    greenSignal1 = [ double(0:NPixels-1)'  double(greenSignal)'];
    blueSignal1 = [ double(0:NPixels-1)'  double(blueSignal)'];

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%% Structure image2 to match input format

    redSignal = ABlening_image2(:,:,1);
    greenSignal = ABlening_image2(:,:,2);
    blueSignal = ABlening_image2(:,:,3);

    % redSignal(:,:) = 0;
    % blueSignal(:,:)  = 0;
    % greenSignal(:,:) = 0;

    subplot(2,2,2);
    image(ABlening_image2), ...
    axis equal, axis square, axis off, title 'Input Image-2';

    NPixels = size(redSignal,1) * size(redSignal,2);

    % turn them into vectors (they were arrays):

    redSignal = reshape(redSignal,1,NPixels);
    greenSignal = reshape(greenSignal,1,NPixels);
    blueSignal = reshape(blueSignal,1,NPixels);

    % insert a column of 'time values' in front -- the from workspace
    % block expects time followed by data on every row of the input
    NPixels = size(redSignal,1) * size(redSignal,2);

    redSignal2 = [ double(0:NPixels-1)'  double(redSignal)'];
    greenSignal2 = [ double(0:NPixels-1)'  double(greenSignal)'];
    blueSignal2 = [ double(0:NPixels-1)'  double(blueSignal)'];
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%