
The purpose of this example is not so much to be useful in its own
right as to show how to build Visual C++ based projects that access
System Generator's Shared Memory API. You can find documentation on
this API in the System Generator User Guide.

To build this project, you will need Microsoft's Developer Studio
2003, Professional. You may be able to get it to build with other
compilers, but it has only been tested against this environment.

You will also need to define an environment variable, SYSGEN, to 
point to the base of your System Generator installation, e.g.,
C:\MATLAB\toolbox\xilinx\sysgen. 

Open "peek.sln" and build the "peek" project in "Release" mode.
This should create an executable ./Release/peek.exe

The sysgen.dll must be in your path in order for this executable to
run properly.   This can be accomplished by either:
append $SYSGEN/bin/ to your PATH environment variable
or
copy $SYSGEN/bin/sysgen.dll to the ./Release/ directory

Create a System Generator design that contains a shared memory block
that initializes an address space named "bar" (or other name of your
choice) that is 16 words deep. Set the simulation time to "Inf" and 
start the model.

Go to a shell prompt (e.g., DOS), and execute ./Release/peek.exe with
the command line argument: bar/0:15

You should see something like the following (with different bit patterns):

  Microsoft Windows XP [Version 5.1.2600]
  (C) Copyright 1985-2001 Microsoft Corp.

  C:\some\dir> .\Release\peek.exe  bar/0:15
  ------------------------------------
  | bar[0000] = 0b'00100101111100111 |
  | bar[0001] = 0b'00100101111000001 |
  | bar[0002] = 0b'00100101110011011 |
  | bar[0003] = 0b'00100101101110101 |
  | bar[0004] = 0b'00100101101010000 |
  | bar[0005] = 0b'00100101100101010 |
  | bar[0006] = 0b'00100101100000100 |
  | bar[0007] = 0b'00100101011011110 |
  | bar[0008] = 0b'00100101010111000 |
  | bar[0009] = 0b'00100101010010010 |
  | bar[0010] = 0b'00100101001101100 |
  | bar[0011] = 0b'00100101001000110 |
  | bar[0012] = 0b'00100101000100000 |
  | bar[0013] = 0b'00100100111111010 |
  | bar[0014] = 0b'00100100111010100 |
  | bar[0015] = 0b'00100100110101110 |
  ------------------------------------
  | bar[0000] = 0b'00100101111100111 |
  | bar[0001] = 0b'00100101111000001 |
  | bar[0002] = 0b'00100101110011011 |
  | bar[0003] = 0b'00100101101110101 |
  | bar[0004] = 0b'00100101101010000 |
  | bar[0005] = 0b'00100101100101010 |
  | bar[0006] = 0b'00100101100000100 |
  | bar[0007] = 0b'00100101011011110 |
  | bar[0008] = 0b'00100101010111000 |
  | bar[0009] = 0b'00100101010010010 |
  | bar[0010] = 0b'00100101001101100 |
  | bar[0011] = 0b'00100101001000110 |
  | bar[0012] = 0b'00100101000100000 |
  | bar[0013] = 0b'00100100111111010 |
  | bar[0014] = 0b'00100100111010100 |
  | bar[0015] = 0b'00100100110101110 |
  ------------------------------------
  C:\some\dir>

Note that "bar" is read out twice. If you examine peek.cpp, you will
see why: it first reads out the sixteen addresses using individual
"read" method invocations, and then reads them a second time via a
single call to the "readArray" method.

