#ifndef DEFAULT_PTABLE_DICTIONARY_H
#define DEFAULT_PTABLE_DICTIONARY_H

// Sysgen
#include "sg_config.h"
#include "sysgen/PTable.h"

// STL
#include <string>
#include <map>

namespace Sysgen {

class SG_API DefaultPTableDictionary
{
public:
    static DefaultPTableDictionary & instance();

    const PTable &find(const std::string &name);

    bool addDefaultPTable(const std::string &name, const PTable &default_ptable);

    void clear();

    std::size_t size() const { return ptable_map.size(); }

private:
    DefaultPTableDictionary() 
    {
    }
    
    ~DefaultPTableDictionary()
    {
    }

    typedef std::map<std::string, PTable> default_ptables_t;
    default_ptables_t ptable_map;
};

}

#endif
