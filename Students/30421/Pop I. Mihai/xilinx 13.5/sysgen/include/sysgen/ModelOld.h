#ifndef SYSGEN_MODEL_H
#define SYSGEN_MODEL_H

#include "sg_config.h"

#include "sysgen/BlockDescriptor.h"
#include "sysgen/PTable.h"

#include <boost/shared_ptr.hpp>

// STL
#include <vector>
#include <map>

namespace Sysgen {

class SG_API ModelOld
{
public:

    ModelOld(const char *model_name, 
          const double& parent_model_handle,
          const double& model_handle);
    
    ModelOld(const char *model_name = "unnamed");

    ~ModelOld();
    
    // create a block which is managed by the model
    BlockDescriptor* createBlock(const std::string &block_type, const PTable &overrides = PTable());
    
    // create a block with a name
    BlockDescriptor* createBlock(const std::string &block_type,
                                 const std::string &block_name,
                                 const PTable &overrides = PTable());
    
    // calls the std::string version
    BlockDescriptor* createBlock(const char *block_type,
                                 const char *block_name,
                                 const PTable &overrides = PTable());
    
    // create a network
    void createNet(const char *net_name);
    
    // connect a pin to a net
    void connectNet(const char *net_name,
                    const char *block_name,
                    const char *pin_name);
    
    // connect the source (src) to the destination (dst)
    // throws error if src is not an output or dst is not an input port
    void connect(PortDescriptor* src, PortDescriptor* dst, const std::string &net_name = "");
    
    void connect(const std::string &source_block,
                 const std::string &source_port,
                 const std::string &dest_block,
                 const std::string &dest_port,
                 const std::string &net_name = "");
    
    // calls the std::string version
    void connect(const char *source_block,
                 const char *source_port,
                 const char *dest_block,
                 const char *dest_port,
                 const char *net_name = "");

    // returns true if model is empty
    bool empty() const;

    // temporary hack to display the graph using dot
    void simulate();

    // After calling this function all of the referenced blocks become
    // invalid!!
    void clear();

private:

    std::string _model_name;
    double _parent_model_handle;
    double _model_handle;
    bool _verbosity;

    typedef std::map<std::string, BlockDescriptor *> BLOCK_MAP_t;
    BLOCK_MAP_t _block_map;

    // map named nets to handles
    typedef std::map<std::string, double> NET_MAP_t;
    NET_MAP_t _net_map;
};

};



#endif
