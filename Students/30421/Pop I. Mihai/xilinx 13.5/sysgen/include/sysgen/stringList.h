/*
 * 
 *
 * Module:  stringList.h
 *
 *  Description:  proxy to std::vector<std::string>
 */

#ifndef STRING_LIST_H
#define STRING_LIST_H

#include "sysgen/sg_config.h"

#if defined (_MSC_VER) && (_MSC_VER >= 1020)
#pragma warning(disable:4231)
// VC++ warning #4231 is a warning about non-standard (VC++) "extern"
// preceeding template instantiation

#pragma warning (disable:4786)
// warning 4786: 255 character symbols for debug
#endif

#include <vector>
#include <string>
#include <iostream>

// this class has to use a vector as VC++ is unable to export any other
// STL container types from a DLL (see Microsoft Q169958: Exporting
// STL Components)

class SG_API stringList 
{
public:

    //! @name type defines to be usable with STL functions
    //@{
    typedef std::vector<std::string>::value_type value_type;
    typedef std::vector<std::string>::pointer pointer;
    typedef std::vector<std::string>::const_pointer const_pointer;
    typedef std::vector<std::string>::reference reference;
    typedef std::vector<std::string>::const_reference const_reference;
    typedef std::vector<std::string>::size_type size_type;
    typedef std::vector<std::string>::difference_type difference_type;
    typedef std::vector<std::string>::iterator iterator;
    typedef std::vector<std::string>::const_iterator const_iterator;
    typedef std::vector<std::string>::reverse_iterator reverse_iterator;
    typedef std::vector<std::string>::const_reverse_iterator const_reverse_iterator;
    //@}

    /// Default constructor
    stringList();
  
    /// Construct a vector of n copies of str
    /// \param n number of copies
    /// \param str string to copy
    stringList(size_type n, const std::string& str = std::string());
  
    /// Copy constructor
    stringList(const stringList& in) : _strings(in._strings) {;}

    /// Copy assignment
    stringList& operator=(const stringList& rhs) { 
	_strings = rhs._strings;
	return *this;
    }
    
    /// Destructor
    virtual ~stringList() {}

    /// Get forward iterator pointing to the beginning of the vector
    iterator begin(){ return _strings.begin(); }

    const_iterator begin() const { return _strings.begin(); }

    /// Get forward iterator pointing to the end of the vector
    iterator end(){ return _strings.end(); }

    /// Get const forward iterator pointing to the end of the vector
    const_iterator end() const { return _strings.end(); }

    /// Get reverse iterator pointing to the beginning of the reversed vector
    reverse_iterator rbegin(){ return _strings.rbegin(); }

    /// Get const reverse iterator pointing to the beginning of the reversed vector
    const_reverse_iterator rbegin() const { return _strings.rbegin(); }

    /// Get iterator pointing to the end of the reversed vector
    reverse_iterator rend(){ return _strings.rend(); }

    /// Get const iterator pointing to the end of the reversed vector
    const_reverse_iterator rend() const { return _strings.rend(); }

    /// Return the number of strings in the vector
    size_type size() const { return _strings.size(); }

    /// Return the maximum vector size
    size_type max_size() const { return _strings.max_size(); }

    /// Return how many slots are reserved
    size_type capacity() const { return _strings.capacity(); }

    /// Return true if the vector contains no strings (size() == 0)
    bool empty() const { return _strings.empty(); }

    /// Return reference to the nth string
    reference operator[](size_type n) { return _strings[n]; }

    /// Return const reference to the nth string
    const_reference operator[](size_type n) const { return _strings[n]; }

    /// swap the contents with another stringList
    void swap(stringList& in) { _strings.swap(in._strings); }

    /// allocate space for more string references
    void reserve(size_type n) { _strings.reserve(n); }

    /// return a reference to the first string in the vector
    reference front() { return _strings.front(); }

    /// return a const reference to the first string in the vector
    const_reference front() const { return _strings.front(); }

    /// return a reference to the last string in the vector
    reference back() { return _strings.back(); }

    /// return a const reference to the last string in the vector
    const_reference back() const { return _strings.back(); }

    /// append a new string to the end of the vector (grows the vector by one)
    void push_back(const std::string& in) { _strings.push_back(in); }

    /// remove and return the last string in the vector (shrink the vector by one)
    void pop_back() { _strings.pop_back(); }

    /// insert a string just before pos
    iterator insert(iterator pos, const std::string& in) { return _strings.insert(pos, in); }

    /// erase the string from the vector
    iterator erase(iterator pos) { return _strings.erase(pos); }

    /// erase a range from the vector
    iterator erase(iterator first, iterator last) { return _strings.erase(first, last); }

    /// empty the vector
    void clear() { _strings.clear(); }

    /// grow or shrink the vector (depending how n compares to size()).
    /// 
    /// If the vector grows, copies of in are appended to the end of
    /// the vector
    void resize(size_type n, const std::string& in = std::string()) { _strings.resize(n,in); }

    /// alias to push_back()
    virtual void add(std::string S) { push_back(S); }
  
    /// concatenate another vector of strings to this one
    stringList& operator+=(const stringList &q);

private:

    std::vector<std::string> _strings;

};

/// print the vector of strings to os
/// \param os output stream to write the vector
SG_API std::ostream& operator<<(std::ostream& os, const stringList& v);

#endif //STRING_LIST_H
