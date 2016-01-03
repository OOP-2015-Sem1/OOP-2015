#ifndef BLOCKFACTORY_H
#define BLOCKFACTORY_H

#include "sg_config.h"
#include <string>
#include <map>

namespace Sysgen {

class BlockDescriptor;

class SG_API BlockFactory
{
public:
    typedef BlockDescriptor *(*BUILDER_FUNCTION_t)();
    typedef std::map<std::string, BUILDER_FUNCTION_t> BUILDERS_t;

    static BlockFactory & instance();

    BlockDescriptor *create(const std::string &name);

    bool addBuilder(const std::string &name, BUILDER_FUNCTION_t builder_function);

    template<class T>
    static BlockDescriptor *createObject() { 
        return new T(); 
    }

    template<class T>
    bool registerBuilder(const std::string &name) 
    {
        BUILDER_FUNCTION_t bdf = &createObject<T>;
        return addBuilder(name, bdf);
    }

    void clear();

    std::size_t size() const { return builder_map.size(); }
    BUILDERS_t::const_iterator begin() const { return builder_map.begin(); }
    BUILDERS_t::const_iterator end() const { return builder_map.end(); }

private:

    BlockFactory() 
    {
    }
    
    ~BlockFactory()
    {
    }

    BUILDERS_t builder_map;
};

}

#endif
