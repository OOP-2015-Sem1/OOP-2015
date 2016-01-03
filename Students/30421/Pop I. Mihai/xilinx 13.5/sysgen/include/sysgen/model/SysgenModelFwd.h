#ifndef _SYSGEN_MODEL_FWD_H_
#define _SYSGEN_MODEL_FWD_H_

namespace AnyTable
{
class AnyType;
}

namespace Sysgen 
{
namespace Model
{
class SubSystem;
class Block;
class Port;
class Inport;
class Outport;
class Signal;
class BuilderPool;
class Builder;
class PortBindings;
class Parameters;
class Serializer;
class Handle;
class BuilderHierarchyHelper;
}
}

namespace Sysgen 
{
class BlockDescriptor;
class PortDescriptor;
}

namespace Sysgen
{
namespace Model
{
// Typedef a builder function
typedef void (*builder_function_t)(const AnyTable::AnyType& parameters);
}
}

#endif
