function [ writeFrameNumber, readFrameNumber] ...
    = xlTripleBufferAddressController( writeDone, readDone , aresetn)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% XLTRIPPLEBUFFERADDRESSCONTROLLER implements the logic for determining the
% frame to write to and the frame to read from. 
% Notes on tripple buffer controller : Read Buffer is locked and when a 
% device is reading from the readBuffer nothing can be written to it. 
% Usually a display device reads fom the read buffer while a graphics card 
% is writing to the two write buffers(writeBuffer1, writeBuffer2). Writing 
% to the write buffers occurs in parallel where the device writes either
% writeBuffer1 or writeBuffer2. Say the device starts writing to
% writeBuffer1. When the frame is completed lastWriteBuffer is set to
% writeBuffer1. And the writing device moves to writeBuffer2. When reading
% device completes reading from readBuffer a swap is made between readBuffer 
% and the lastWriteBuffer and read and write continues.
%
% writeDone - Perform writeFrame swap
% readDone - perform the readFrame swap
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
persistent writeFrameNumberState, writeFrameNumberState ...
    = xl_state(0, {xlUnsigned, 2 ,0});
persistent lastWriteFrameNumberState, lastWriteFrameNumberState ...
    = xl_state(1, {xlUnsigned, 2 ,0});
persistent nextWriteFrameNumberState, nextWriteFrameNumberState ...
    = xl_state(1, {xlUnsigned, 2 ,0});
persistent readFrameNumberState, readFrameNumberState ...
    = xl_state(2, {xlUnsigned, 2 ,0});

readFrameNumber = readFrameNumberState;
writeFrameNumber = writeFrameNumberState;

if aresetn == false,
    writeFrameNumberState = xfix({xlUnsigned, 2, 0}, 0);
    lastWriteFrameNumberState = xfix({xlUnsigned, 2,0}, 1);
    nextWriteFrameNumberState = xfix({xlUnsigned, 2, 0}, 1);
    readFrameNumberState = xfix({xlUnsigned, 2, 0}, 2);
else
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Read, Write, nextWrite buffer locations
%   Goal is to read from the last full written frame.  If the read frame
%   finishes before the write, it should select the most recent full frame
%   (either in read or nextWrite buffer location).  If the write finishes
%   before the read, it should move to the nextWrite buffer location.
%   
%   Locations (0,1,2) for triple buffer
%       Read    Write   NextWrite   LastWrite   NextRead
%       0       1       2           0           0
%       0       1       2           2           2
%       0       2       1           0           0
%       0       2       1           1           1
%       1       0       2           1           1
%       1       0       2           2           2
%       1       2       0           1           1
%       1       2       0           0           0
%       2       0       1           2           2
%       2       0       1           1           1
%       2       1       0           2           2
%       2       1       0           0           0
%
%   On writeDone
%       lastWrite = write
%       write = nextWrite
%   On readDone
%       nextWrite = read iff read~=lastWrite
%       read = lastWrite
%   On both
%       swap write and read

    if (readDone && writeDone)
        lastWriteFrameNumberState = writeFrameNumberState;          % Locate the last full frame written
        readFrameNumberState = writeFrameNumberState;               % Read the last full frame written
        writeFrameNumberState = readFrameNumberState;               % Write to the trash frame
        nextWriteFrameNumberState = 3-readFrameNumberState-writeFrameNumberState;   % Next triple buffer write will be to the empty frame
    else
        if (readDone)
            if (lastWriteFrameNumberState ~= readFrameNumberState)  % There is a newer frame available, if next readDone occurs before next writeDone, reread this frame 
                nextWriteFrameNumberState = readFrameNumberState;   % Next write will be to the previously read frame
            end
            readFrameNumberState = lastWriteFrameNumberState;       % Read the last full frame written;
        end
        if (writeDone)
            lastWriteFrameNumberState = writeFrameNumberState;      % Locate the last full frame written
            writeFrameNumberState = nextWriteFrameNumberState;      % Write to the trash frame
            nextWriteFrameNumberState = lastWriteFrameNumberState;  % If next writeDone occurs before next readDone, overwrite the last frame
        end
    end
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% We are still not handling the case when both happen at the same time.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%    if readDone == true,    
%        nextWriteFrameNumberState = readFrameNumberState;
%        readFrameNumberState = lastWriteFrameNumberState;
%    end
%    if writeDone == true,
%        lastWriteFrameNumberState = writeFrameNumberState;
%        writeFrameNumberState = nextWriteFrameNumberState;
%        nextWriteFrameNumberState = lastWriteFrameNumberState;
%    end
end
