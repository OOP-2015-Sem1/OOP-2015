
//-------------------------------------------------------------------------
// System Generator source file.
//
// Copyright(C) 2004 by Xilinx, Inc.  All rights reserved.  This
// text/file contains proprietary, confidential information of Xilinx,
// Inc., is distributed under license from Xilinx, Inc., and may be used,
// copied and/or disclosed only pursuant to the terms of a valid license
// agreement with Xilinx, Inc.  Xilinx hereby grants you a license to use
// this text/file solely for design, simulation, implementation and
// creation of design files limited to Xilinx devices or technologies.
// Use with non-Xilinx devices or technologies is expressly prohibited
// and immediately terminates your license unless covered by a separate
// agreement.
//
// Xilinx is providing this design, code, or information "as is" solely
// for use in developing programs and solutions for Xilinx devices.  By
// providing this design, code, or information as one possible
// implementation of this feature, application or standard, Xilinx is
// making no representation that this implementation is free from any
// claims of infringement.  You are responsible for obtaining any rights
// you may require for your implementation.  Xilinx expressly disclaims
// any warranty whatsoever with respect to the adequacy of the
// implementation, including but not limited to warranties of
// merchantability or fitness for a particular purpose.
//
// Xilinx products are not intended for use in life support appliances,
// devices, or systems.  Use in such applications is expressly prohibited.
//
// Any modifications that are made to the source code are done at the
// user's sole risk and will be unsupported.
//
// This copyright and support notice must be retained as part of this
// text at all times.  (c) Copyright 2004 Xilinx, Inc.  All rights
// reserved.
//-------------------------------------------------------------------------

#include <cstdlib>
#include <string>
#include <cmath>
#include <map>
#include "mex.h"

#pragma warning(disable:4251)
#include "sysgen/SharedMemory.h"

using std::string;

// utility function, ala mxIsChar, etc.
// mxIsInt returns true if the supplied mxArray pointer contains a
// scalar integer
//
static bool mxIsInt(const mxArray* a)
{
    bool is_int = false;
    
    if (mxIsNumeric(a)) {
        if (mxGetNumberOfDimensions(a) <= 2) {
            if (mxGetN(a) == 1 || mxGetM(a) == 1) {
                double d = mxGetScalar(a);
                if (d==floor(d)) {
                    is_int = true;
                }
            }
        }
    }
    return is_int;
}


// Class that handles commands dispatched from the mex function
//
class SharedMemoryCmd 
{
  
public:
  
  static void construct(int nlhs,mxArray *plhs[],int nrhs,const mxArray *prhs[])
  {
    // Sanity check the input arguments
    //
    if((nrhs!=2) || !mxIsChar(prhs[1])){ 
        mexErrMsgTxt("Illegal invocation of constructor.");
        return;
    }
    char *mem_name = mxArrayToString(prhs[1]);
    
    // Check that this memory is not already in the map
    //
    if(shmem_map().find(mem_name) != shmem_map().end()) mexErrMsgTxt("Memory proxy with this name already exists");
    
    // Insert create and insert shared memory proxy
    //
    std::pair<shmem_map_t::iterator, bool> ipair;
    ipair = shmem_map().insert(
        std::make_pair(mem_name, 
                       new Sysgen::SharedMemory(mem_name,
                                                0,                             
                                                Sysgen::SharedMemory::INHERIT, 
                                                Sysgen::SharedMemory::INHERIT, 
                                                0.0))); 
    // Retrieve the size and word size from the memory
    //
    Sysgen::SharedMemory* memory = (ipair.first)->second;
    int nwords = memory->getNWords();
    int word_size = memory->getWordSize();

    // Return handle, memory size, and word length
    //
    if(nlhs>=1) plhs[0] = mxCreateString(mem_name);      
    if(nlhs>=2) plhs[1] = mxCreateDoubleScalar(nwords);      
    if(nlhs>=3) plhs[2] = mxCreateDoubleScalar(word_size);          
  }
  
  static void destroy(int nlhs,mxArray *plhs[],int nrhs,const mxArray *prhs[])
  {
    // Sanity check the input arguments
    //
    if((nrhs!=2) || !mxIsChar(prhs[1])){ 
        mexErrMsgTxt("Illegal invocation of destructor");
        return;
    }
    char *mem_name = mxArrayToString(prhs[1]);

    // Delete shared memory of given name and remove from the map
    //
    shmem_map_t::iterator iter = shmem_map().find(mem_name);
    if( iter == shmem_map().end() ) {
      mexErrMsgTxt((string("Attempt to delete non existant shared memory ") 
                    + string(mem_name)).c_str());
    } else {
      delete iter->second;
      shmem_map().erase(mem_name);
    }
  }
  
  static void read(int nlhs,mxArray *plhs[],int nrhs,const mxArray *prhs[])
  {
      // Sanity check the input arguments
      //
      if((nrhs!=3) || !mxIsChar(prhs[1]) || !mxIsInt(prhs[2])){ 
          mexErrMsgTxt("Illegal invocation of read method");
          return;
      }

      // Input arguments are ("read", name, address)
      //
      char *mem_name = mxArrayToString(prhs[1]);
      unsigned addr = (unsigned) mxGetScalar(prhs[2]);

      double val;

      shmem_map_t::iterator iter = shmem_map().find(mem_name);
      if( iter == shmem_map().end() ) {
          mexErrMsgTxt((string("Attempt to read non-existant shared memory ") 
                        + string(mem_name)).c_str());
      } else {
          Sysgen::SharedMemory *M = iter->second;
          Sysgen::StdLogicVector slv(0.0, M->getWordSize(), 0, false);
          M->read(addr, slv, 5.0);
          val = slv.toDouble();
      }

      if(nlhs>=1) plhs[0] = mxCreateDoubleScalar(val);      
  }
  
  static void write(int nlhs,mxArray *plhs[],int nrhs,const mxArray *prhs[])
  {
      // Sanity check the input arguments
      //
      if((nrhs!=4) || !mxIsChar(prhs[1]) || !mxIsInt(prhs[2]) 
         || !mxIsInt(prhs[3])){ 
          mexErrMsgTxt("Illegal invocation of write method");
          return;
      }

      // Input arguments are ("write", name, address, value)
      //
      char *mem_name = mxArrayToString(prhs[1]);
      unsigned addr = (unsigned) mxGetScalar(prhs[2]);
      double val = mxGetScalar(prhs[3]);

      shmem_map_t::iterator iter = shmem_map().find(mem_name);
      if( iter == shmem_map().end() ) {
          mexErrMsgTxt((string("Attempt to write non-existant shared memory ") 
                        + string(mem_name)).c_str());
      } else {
          Sysgen::SharedMemory *M = iter->second;
          int nbits = M->getWordSize();
          Sysgen::StdLogicVector slv(0.0, nbits, 0, val<0);
          slv.assignFromDouble(val, nbits, 0, val<0);
          M->write(addr, slv, 5.0);
      }
  }
  
private:  
  
  typedef std::map<string, Sysgen::SharedMemory*> shmem_map_t;

  // Intantiate upon first use semantics for the lookup map
  //
  static shmem_map_t& shmem_map(){
    static shmem_map_t lcl;
    return lcl;
  };

 };

void mexFunction(int nlhs,mxArray *plhs[],int nrhs,const mxArray *prhs[])
{
    static char msg_buffer[8192];

    if ((nrhs == 0) || !mxIsChar(prhs[0])) {
        mexErrMsgTxt("Illegal invocation of xlshmem_mex");
        return;
    }
    
    char *command = mxArrayToString(prhs[0]);

    try {
        if (command) {
            if (!strcmp(command,"construct")) {
                SharedMemoryCmd::construct(nlhs,plhs, nrhs,prhs);
            }
            else if (!strcmp(command,"destroy")) {
                SharedMemoryCmd::destroy(nlhs,plhs, nrhs,prhs);
            }
            else if (!strcmp(command,"read")) {
                SharedMemoryCmd::read(nlhs,plhs, nrhs,prhs);
            }
            else if (!strcmp(command,"write")) {
                SharedMemoryCmd::write(nlhs,plhs, nrhs,prhs);
            }
            else {
                mexErrMsgTxt("Illegal invocation of xlshmem_mex");
                return;
            }
        }
        else {
            mexErrMsgTxt("Illegal invocation of xlshmem_mex");
            return;
        }
    }
    catch (std::exception& e) {
        strcpy(msg_buffer,e.what());
        mexErrMsgTxt(msg_buffer);
    }
    catch (...) {
        mexErrMsgTxt("Caught unknown exception");
    }
    return;
}

