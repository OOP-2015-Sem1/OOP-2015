function f = xlFrameDisplay(frameIndex, data_real, data_imag, frameSize)
f = 1;
persistent mri_samples;
if nargin == 0,
    mri_samples = [];
    return;
end;
if isempty(mri_samples),
    mri_samples = zeros(frameSize, 1);
end;
mri_samples(frameIndex+1) = complex(data_real, data_imag);
if (frameIndex == frameSize-1),
    figure(1);
    imshow(abs(reshape(mri_samples,256,256)),[]);
    mri_samples = [];
end;
