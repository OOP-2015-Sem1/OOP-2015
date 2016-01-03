/*
 *  PTable.h
 *
 *  Copyright (c) 2003-2006, Xilinx, Inc.  All Rights Reserved.
 *
 *  Description:  Provides a class to store and access all variables
 *                associated with block configuration.
 */

#ifndef SYSGEN_PTABLE_H
#define SYSGEN_PTABLE_H

#include "sysgen/sg_config.h"
#include "sysgen/Error.h"
#include "anytable/AnyType.h"

#if defined (_MSC_VER) && (_MSC_VER >= 1020)
// warning 4786: 255 characer symbols for debug
#pragma warning(disable:4786)
#endif

#include <iostream>
#include <vector>
#include <string>
#include "stringList.h"

class XTable; // forward declaration

#ifndef DOXYGEN_SHOULD_SKIP_THIS

#ifndef _SIMSTRUCT
#define _SIMSTRUCT
/*
 * Use incomplete type for function prototypes within SimStruct itself
 */
typedef struct SimStruct_tag SimStruct;
#endif

#if !defined(mxArray_DEFINED)
/*
 * Incomplete definition of MATLAB's mxArray
 */
struct mxArray_tag;
typedef struct mxArray_tag mxArray;
#define mxArray_DEFINED
#endif /* !defined(mxArray_DEFINED) */

#endif /* ifndef DOXYGEN_SHOULD_SKIP_THIS */

namespace Sysgen
{
    class PTableImpl; // Forward declaration

    /// \ingroup includes 
    /// \ingroup public_utility
    /// \ingroup published
    /// \brief An associative container object
    class SG_API PTable
    {
    public:

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        //! @name Construction/Destruction
        //@{    
        /// Construct from simstruct
        PTable(SimStruct *S);
        /// Construct from shared AnyTable
        PTable(AnyTable::AnyType *anytable);
//        PTable(boost::shared_ptr<XTable> xtable);
        /// Copy construct (Shallow)
        PTable(const PTable& m);
        /// Construct a ptable from a non-shared AnyTable
        PTable(const XTable& xtable);
        PTable(const AnyTable::AnyType &anytable);
        /// Construct a ptable from a serialized PTable
        PTable(const std::string &serialized_ptable, std::ostream::openmode mode=static_cast<std::ostream::openmode>(0));
        /// Default construct        
        PTable();
        /// Construct assign (Shallow)
        PTable& operator=(const PTable&);
        /// Destructor
        ~PTable();	  
        //@}
#endif /* ifndef DOXYGEN_SHOULD_SKIP_THIS */

        //! @name Iterators
        //@{   
#ifndef DOXYGEN_SHOULD_SKIP_THIS
        typedef const std::string key_type;
        typedef PTable data_type;
        typedef std::pair<key_type, data_type> value_type;

#endif /* ifndef DOXYGEN_SHOULD_SKIP_THIS */
        //@}

        void setAnyTable(AnyTable::AnyType *anytable);

        //! @name Scalar Accessors
        //@{
        /// The getBool method returns the boolean value associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a boolean type.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the boolean value associated with the key.
        bool getBool(const std::string &key) const;

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given boolean value.
        void set(const std::string &key, bool val);

        /// The getInt method returns the integer value associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not an integer type.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the integer value associated with the key.
        int getInt(const std::string &key) const;

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given integer value.
        void set(const std::string &key, int val);

        /// The getDouble method returns the double value associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a double type.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the double value associated with the key.
        double getDouble(const std::string &key) const;

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given double value.
        void set(const std::string &key, const double& val);

        /// The getString method returns the string value associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a string type.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the string value associated with the key.
        std::string getString(const std::string &key) const;

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given string value.
        void set(const std::string &key, const std::string &val);

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given
        /// string value via converting the char* to a string.
        void set(const std::string &key, const char* val);
        //@}


        //! @name Vector Accessors
        //@{    
        /// The getIntVector method returns the vector of integers associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a vector of integers.  This method will return a copy
        /// of the vector, rather than a reference to the table entry.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the vector of integers associated with the key.
        std::vector<int> getIntVector(const std::string &key) const;

        /// The pushBackIntVector method appends the specified value to the vector of integers
        /// associated with the given key.  If no value has been associated with the key, this
        /// method will create a new vector of integers and add the value to it.  This method 
        /// will throw a Sysgen::Error exception if the data associated with the key is not a
        /// vector of integers.
        /// \param key The identifier that the data is associated with
        /// \param val The data to add to the vector associated with the key
        /// \brief Adds the integer value to the vector of integers associated with the key.
        void pushBackIntVector(const std::string& key, int val);

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given vector value.
        void set(const std::string &key, const std::vector<int>& val);

        /// The getDoubleVector method returns the vector of doubles associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a vector of doubles. This method will return a copy
        /// of the vector, rather than a reference to the table entry.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the vector of doubles associated with the key.
        std::vector<double> getDoubleVector(const std::string &key) const;

        /// The pushBackDoubleVector method appends the specified value to the vector of doubles
        /// associated with the given key.  If no value has been associated with the key, this
        /// method will create a new vector of doubles and add the value to it.  This method 
        /// will throw a Sysgen::Error exception if the data associated with the key is not a
        /// vector of doubles.
        /// \param key The identifier that the data is associated with
        /// \param val The data to add to the vector associated with the key
        /// \brief Adds the double value to the vector of doubles associated with the key.
        void pushBackDoubleVector(const std::string &key, const double& val);

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given vector value.
        void set(const std::string &key, const std::vector<double>& val);

        /// The getStringVector method returns the vector of strings associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a vector of strings.  This method will return a copy
        /// of the vector, rather than a reference to the table entry.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the vector of strings associated with the key.
        stringList getStringVector(const std::string &key) const;

        /// The pushBackStringVector method appends the specified value to the vector of strings
        /// associated with the given key.  If no value has been associated with the key, this
        /// method will create a new vector of strings and add the value to it.  This method 
        /// will throw a Sysgen::Error exception if the data associated with the key is not a
        /// vector of strings.
        /// \param key The identifier that the data is associated with
        /// \param val The data to add to the vector associated with the key 
        /// \brief Adds the string value to the vector of strings associated with the key.
        void pushBackStringVector(const std::string& key, const std::string& val);

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.  The set method will throw a Sysgen::Error exception if the
        /// new value does not have the same data type as the previous value associated with the key.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given vector value.
        void set(const std::string &key, const stringList &val);

        //@}

        //! @name Structure Accessors
        //@{    
        /// The getPTable method returns the PTable associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a PTable. This method will return a copy of the
        /// PTable, rather than a reference to the PTable.
        /// \param key The identifier associated with the data to retrieved
        /// \brief Retrieves the PTable object associated with the key.
        PTable getPTable(const std::string &key) const;

        /// The set method sets the value associated with the given key to the given value.
        /// There are several overloaded versions of the set method; each one accepts a different
        /// data type as an argument.
        /// \param key The identifier that the data is associated with
        /// \param val The data to associate with the key
        /// \brief Sets the data associated with the key to the given PTable.
        void set(const std::string &key, const PTable &val);

        /// The ptableInsertInt method assigns a value to a key in a PTable that is nested in this 
        /// PTable.  PTables may be nested in other PTables via a call to the set(string, PTable)
        /// method.  The path parameter may be used to refer to any nested PTable regardless of how
        /// many levels of nesting there are.  Each element in the path is the key to indicate which
        /// PTable to look for at that level of nesting.  Elements in the path may be separated by a
        /// user configurable delimiter.  The default delimiter is "/".  The key parameter is used as
        /// the key to associate with the value in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be inserted into the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param value The data to associate with the key
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Sets the data associated with the key to the given integer value in the nested PTable referenced by the path.
        /// \sa set(const std::string &key, const PTable &val) getPTable
        void ptableInsertInt(const std::string& path,  
                             const std::string& key, 
                             int value,
                             const std::string& path_delimiter= "/"); 
        
        
        /// The ptableInsertDouble method assigns a value to a key in a PTable that is nested in this 
        /// PTable.  PTables may be nested in other PTables via a call to the set(string, PTable)
        /// method.  The path parameter may be used to refer to any nested PTable regardless of how
        /// many levels of nesting there are.  Each element in the path is the key to indicate which
        /// PTable to look for at that level of nesting.  Elements in the path may be separated by a
        /// user configurable delimiter.  The default delimiter is "/".  The key parameter is used as
        /// the key to associate with the value in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be inserted into the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param value The data to associate with the key
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Sets the data associated with the key to the given double value in the nested PTable referenced by the path.
        /// \sa set(const std::string &key, const PTable &val) getPTable
        void ptableInsertDouble(const std::string& path,  
                                const std::string& key, 
                                const double& value,
                                const std::string& path_delimiter= "/"); 
        
        
        /// The ptableInsertString method assigns a value to a key in a PTable that is nested in this 
        /// PTable.  PTables may be nested in other PTables via a call to the set(string, PTable)
        /// method.  The path parameter may be used to refer to any nested PTable regardless of how
        /// many levels of nesting there are.  Each element in the path is the key to indicate which
        /// PTable to look for at that level of nesting.  Elements in the path may be separated by a
        /// user configurable delimiter.  The default delimiter is "/".  The key parameter is used as
        /// the key to associate with the value in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be inserted into the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param value The data to associate with the key
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Sets the data associated with the key to the given string value in the nested PTable referenced by the path.
        /// \sa set(const std::string &key, const PTable &val) getPTable
        void ptableInsertString(const std::string& path,  
                                const std::string& key, 
                                const std::string& value,
                                const std::string& path_delimiter= "/"); 
        
       
        /// The ptableInsertPTable method assigns a PTable to a key in a PTable that is nested in this 
        /// PTable.  PTables may be nested in other PTables via a call to the set(string, PTable)
        /// method.  The path parameter may be used to refer to any nested PTable regardless of how
        /// many levels of nesting there are.  Each element in the path is the key to indicate which
        /// PTable to look for at that level of nesting.  Elements in the path may be separated by a
        /// user configurable delimiter.  The default delimiter is "/".  The key parameter is used as
        /// the key to associate with the value in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be inserted into the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param ptable The PTable to associate with the key
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Sets the data associated with the key to the given PTable value in the nested PTable referenced by the path.
        /// \sa set(const std::string &key, const PTable &val) getPTable
        void ptableInsertPTable(const std::string& path,  
                                const std::string& key,
                                const PTable& ptable,
                                const std::string& path_delimiter= "/");
        
        /// The ptableRetrieveInt method returns the value associated with a key in a PTable that is
        /// nested in this PTable.  PTables may be nested in other PTables via a call to the
        /// set(string, PTable) method.  The path parameter may be used to refer to any nested PTable
        /// regardless of how many levels of nesting there are.  Each element in the path is the key
        /// to indicate which PTable to look for at that level of nesting.  Elements in the path may
        /// be separated by a user configurable delimiter.  The default delimiter is "/".  The key
        /// parameter is used as the lookup key in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be retrieved from the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Retrieves the integer value associated with the key in the nested PTable referenced by the path.
        int ptableRetrieveInt(const std::string& path,  
                              const std::string& key, 
                              const std::string& path_delimiter= "/"); 
        
        /// The ptableRetrieveDouble method returns the value associated with a key in a PTable that is
        /// nested in this PTable.  PTables may be nested in other PTables via a call to the
        /// set(string, PTable) method.  The path parameter may be used to refer to any nested PTable
        /// regardless of how many levels of nesting there are.  Each element in the path is the key
        /// to indicate which PTable to look for at that level of nesting.  Elements in the path may
        /// be separated by a user configurable delimiter.  The default delimiter is "/".  The key
        /// parameter is used as the lookup key in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be retrieved from the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Retrieves the double value associated with the key in the nested PTable referenced by the path.
        double ptableRetrieveDouble(const std::string& path,  
                                    const std::string& key, 
                                    const std::string& path_delimiter= "/"); 
        
        /// The ptableRetrieveString method returns the value associated with a key in a PTable that is
        /// nested in this PTable.  PTables may be nested in other PTables via a call to the
        /// set(string, PTable) method.  The path parameter may be used to refer to any nested PTable
        /// regardless of how many levels of nesting there are.  Each element in the path is the key
        /// to indicate which PTable to look for at that level of nesting.  Elements in the path may
        /// be separated by a user configurable delimiter.  The default delimiter is "/".  The key
        /// parameter is used as the lookup key in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be retrieved from the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Retrieves the string value associated with the key in the nested PTable referenced by the path.
        std::string ptableRetrieveString(const std::string& path,  
                                         const std::string& key, 
                                         const std::string& path_delimiter= "/"); 
        
        
        /// The ptableRetrievePTable method returns the PTable associated with a key in a PTable that is
        /// nested in this PTable.  PTables may be nested in other PTables via a call to the
        /// set(string, PTable) method.  The path parameter may be used to refer to any nested PTable
        /// regardless of how many levels of nesting there are.  Each element in the path is the key
        /// to indicate which PTable to look for at that level of nesting.  Elements in the path may
        /// be separated by a user configurable delimiter.  The default delimiter is "/".  The key
        /// parameter is used as the lookup key in the PTable referred to by the path.  This method will
        /// throw a Sysgen::Error if the PTable referred to by the path can not be found.  There are
        /// several forms of this operations, one for each data type that may be retrieved from the
        /// nested PTable.
        /// \param path The list of keys used to determine which nested PTable to operate on
        /// \param key The identifier that the data is associated with
        /// \param path_delimiter The string used to seperate elements in the path
        /// \brief Retrieves the PTable object associated with the key in the nested PTable referenced by the path.
        PTable ptableRetrievePTable(const std::string& path,  
                                    const std::string& key,
                                    const std::string& path_delimiter= "/");
        
        /// Clear all entries from the PTable
        void clear();
        
        /// Return the number of top-level entries in the PTable
        std::size_t size() const;

        /// Return true if there no entries in the PTable; false otherwise
        bool empty() const;


        //@}
        
        //! @name Queries
        //@{    
        /// The hasEntry method will return true if the PTable has data associated with the 
        /// given key value.  Data can be associated with the key value via the set method. 
        /// \param key See if there is data associated with this key
        /// \brief The hasEntry method will return true if the PTable has data associated with the given key value.
        bool hasEntry(const std::string &key) const;

        /// The getVectorSize method returns the size of the vector associated with the given key.
        /// It will throw a Sysgen::ParameterLookupError exception if the key is not present 
        /// in the table.  It will throw a Sysgen::ParameterLookupError exception if the data
        /// associated with the key is not a vector and can not be converted to a vector.  If the
        /// key is associated with an integer or double scalar value, this method will return the size 1.
        /// \param key The identifier that determines which vector's size to return
        /// \brief Returns the size of the vector associated with the key.
        int getVectorSize(const std::string &key) const;

        /// The getKeys method returns a vector of strings containing all the top level keys
        /// in the PTable.  This method will not search nested PTables for keys.
        /// \brief Returns a vector of all the top level keys in the PTable.
        stringList getKeys(void) const;

        /// The findKeysCaseInsensitive method returns a vector of strings containing all
        /// the top level keys in the PTable that match the key parameter when doing a case
        /// insensitive string comparison.
        /// \param key The function will return all keys with the same name as this parameter
        /// \brief Get all top level keys that match the search key using a case insensitive comparison.
        stringList findKeysCaseInsensitive(const std::string &key) const;

        //@}

        //! @name Serialization    
        //@{

        /// \brief Serialize the table contents to an output string
        /// \param os set the output stream.
        /// \param mode determines ASCII or binary.
        /// \sa deserialize(), print()
        void serialize(std::ostream& os = std::cout, std::ostream::openmode mode=static_cast<std::ostream::openmode>(0)) const;
        
        /// \brief Load a table contents from a input stream (presumably, written by serialize).
        /// \param is set the input stream
        /// \param mode determines ASCII or binary
        /// \sa serialize()
        void deserialize(std::istream& is, std::ostream::openmode mode=static_cast<std::ostream::openmode>(0)) const;

        /// The print method generates an ASCII representation of all the contents in the
        /// PTable.  The data is generated in the given output stream object.
        /// \param os the output stream
        /// \sa serialize(), deserialize()
        /// \brief Print an ASCII representation of the ptable to the given ostream
        void print(std::ostream& os = std::cout) const;

        //@}

#ifndef DOXYGEN_SHOULD_SKIP_THIS
        //! @name Depreciated
        //@{    
        std::string getBlockName(bool hierarchial_name=true) const;
        void setBlockName(const std::string& block_name);
        std::string getModelName() const;
        int getCellLength(const std::string &key) {return 0;}
        mxArray *getCellArray(const std::string &key) {return NULL;}
        double getCellDouble(const std::string &key, int n) {return 0;}
        //@}
        
        //! @name Opaque shared pointer
        //@{    
        const AnyTable::AnyType &getAnyTable() const;
        AnyTable::AnyType &getAnyTable();

        XTable &getXTable();
        const XTable &getXTable() const;
        //@}

#endif /* ifndef DOXYGEN_SHOULD_SKIP_THIS */

        class SG_API ParameterLookupError : public Error
        {
        public:
            enum ErrorType { NONE, DOES_NOT_EXIST, WRONG_DATA_TYPE };

            ParameterLookupError(const PTable &pt, const std::string &key, const std::string &block,
                                 ErrorType et = DOES_NOT_EXIST, const std::string &reason = "");
            virtual ~ParameterLookupError() throw() {}
            
            std::string key() const { return _key; }
        private:
            std::string _key;

            std::string errorText(const PTable &pt, ErrorType et, const std::string &key,
                                  const std::string &block, const std::string &reason);
        };

    private:

        void add(const std::string &key, bool val);
        void add(const std::string &key, int val);
        void add(const std::string &key, const std::vector<int>& val);
        void add(const std::string &key, const double& val);
        void add(const std::string &key, const std::vector<double>& val);
        void add(const std::string &key, const std::string& val);
        void add(const std::string &key, const stringList& val);
        void add(const std::string &key, const PTable& val);

        template<typename T> void verifyTypeAndKey(const std::string &key) const
        {
            if(!hasEntry(key))
                throw ParameterLookupError(*this, key, getBlockName(), ParameterLookupError::DOES_NOT_EXIST);
            const AnyTable::AnyType& tbl = getAnyTable()[key];
            if(!AnyTable::isa<T>(tbl))
                throw ParameterLookupError(*this, key, getBlockName(), ParameterLookupError::WRONG_DATA_TYPE,
                                           AnyTable::bad_basetype_cast(tbl.content->gettype(), T::statictype()).what());
        }

        PTableImpl *_imp;
    };
}

#endif //SYSGEN_PTABLE_H
