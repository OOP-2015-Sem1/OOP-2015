/*
 *  SysgenRate.h
 *
 *  Copyright (c) 2004, 2005, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Generalizable rate object
 *
 */ 

#ifndef _SYSGENRATE_H_
#define _SYSGENRATE_H_

// Xilinx
#include "sysgen/sg_config.h"

// SL/Boost
#include <map>

namespace Sysgen {

    /// \ingroup public_config
    /// \brief Rate specifier class
    struct SG_API SysgenRate {

    private:
        static const double RATE_UNKNOWN; 
        static const double RATE_CONSTANT;
        static const double RATE_MAXIMUM;

    public:

        /// \brief RATE_UNKNOWN is a symbolic constant that represents
        /// an unknown rate.  This value can be assigned or compared
        /// to the period field in the class.
        static double rate_unknown();
        
        /// \brief RATE_CONSTANT is a symbolic constant that
        /// represents a constant rate (no sampling).  This value can
        /// be assigned or compared to the period field in the class.
        static double rate_constant();

        /// \brief RATE_MAXIMUM is a symbolic constant that represents
        /// an undivided/unscaled system clock rate.
        static double rate_maximum();
        
        /// \brief Default constructor.  Sets period to RATE_UNKNOWN and offset to 0.
        SysgenRate();
          
        /// \param in_period The period value to use in the constructed SysgenRate object.
        /// \param in_offset The offset value to use in the constructed SysgenRate object.
        /// \brief Constructs a SysgenRate object with the period and offset set to the values of the parameters.
        SysgenRate(const double& in_period, const double& in_offset = 0) :
            period(in_period),
            offset(in_offset){}

        /// \sa RATE_UNKNOWN
        /// \brief Returns true unless the period is set to the value RATE_UNKNOWN 
        bool isRateKnown() const;

        /// \sa RATE_CONSTANT
        /// \brief Returns true if the period is set to the value RATE_CONSTANT
        bool isRateConstant() const;

        /// \sa RATE_MAXIMUM
        /// \brief Returns true if the period is set to the value RATE_MAXIMUM
        bool isRateMaximum() const;

        /// \brief Does a field by field comparison of the two SysgenRates and returns true if all fields are identical.
        bool operator==(const SysgenRate& rhs) const;

        /// \brief Does a field by field comparison of the two SysgenRates and returns true if any fields are different.
        bool operator!=(const SysgenRate& rhs) const;

        /// Orders by rate, offset

        /// The less than operator returns true if the SysgenRate on the left of the operator is "less than"
        /// the one on the right of the operator.  The return value is determined by comparing the values in
        /// the fields in the order period, offset.  This operator allows SysgenRates to be stored in sorted
        /// STL containers such as maps and sets.
        /// \brief Returns true if the SysgenRate on the left is "less than" the one on the right.  Useful for storing SysgenRates in STL containers.
        bool operator<(const SysgenRate& rhs) const; 
        
        /// \brief The interval that determines how frequently the block or port operates.  A period of 1 will cause the block or port to operate during each system clock cycle.
        double period;
        /// \brief Offset is not being used in this version of System Generator.  Do not use the offset value.
        double offset;
    };


    /// The SysgenRateMap holds the mapping between a SysgenRate id and a corresponding SysgenRate object.  Each set of values for the SysgenRate fields will have exactly one entry in the SysgenRateMap and exactly one corresponding rate id.
    /// The SysgenRateMap class is built to optimize speed and memory usage for SysgenRate objects.  A design may only use a small number of data rates, but the internals of the System Generator software may contain many instances of SysgenRate objects.  The SysgenRateMap is a good solution to this problem since it allows the System Generator internals to store handles to SysgenRate objects rather than storing the entire object.  SysgenRate equality comparisons can be done via the handle rather than doing a field by field comparison on the SysgenRate object.  The SysgenRateMap will not consume much memory since there are only a small number of SysgenRates in the design.
    /// brief The SysgenRateMap allows SysgenRate objects to be associated with a rate id.  It provides methods to lookup the Rate object based on the id and to look up the id based on the rate object.
    class SG_API SysgenRateMap {    
    public:
        /// The get_rate method will return a read-only reference to the SysgenRate object that corresponds
        /// to the given rate id.  If no SysgenRate object is associated with the id, then a runtime_error
        /// exception will be thrown.
        /// \param id The key used to look up the SysgenRate in the map.
        /// \brief Returns a read-only reference to the SysgenRate object associated with the given rate id.  Throws an exception if there is no SysgenRate associated with the id.
        static const SysgenRate& get_rate(int id); 

        /// The get_id method will return an id that corresponds to the given rate SysgenRate.  If the SysgenRate
        /// object does not yet exist in the map, then it will be added to the map and the id for the new map entry
        /// will be returned.
        /// \param type The SysgenRate to look up in the map.
        /// \brief Returns the id associated with the given SysgenRate.  Adds the SysgenRate to the map if it has not yet been added.
        static int get_id(const SysgenRate& type); 

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        /// Returns typeid corresponding to type installs if no such id
        static int get_id(const double& period, const double& offset); 
        /// Install type corresponding to SysgenType
        static int install_rate(const SysgenRate& rate);
#endif
    private:
        // storage type lookup
        typedef std::map<int, SysgenRate> rate_lookup_t;
        static rate_lookup_t& rate_lookup();
        // storage id lookup
        typedef std::map<SysgenRate, int> id_lookup_t;
        static id_lookup_t& id_lookup(); 
    };


}

#endif // _SYSGENRATE_H_
