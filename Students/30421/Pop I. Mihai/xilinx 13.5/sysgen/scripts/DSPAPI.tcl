set xilinxPath $::env(XILINX)
lappend ::auto_path [file join $xilinxPath "coregen"]
set dir [file join $xilinxPath "coregen"]
source [file join $xilinxPath "coregen" "pkgIndex.tcl"]
package require xilinxIPEngine 1.0

#This is a server program that provides access into
#CoreGen API function. This TCL program should only 
#be launched in the context of xtclsh because of
#dependencies of CoreGen API on xtclsh.
set thisScriptDir [file dirname [info script]]

source [file join $thisScriptDir "AnyTableString.tcl"]

package provide dspapi 1.0
package require AnyTableString

namespace eval ::xilinx::dsptool {
    namespace export init setFamilies isAvailableFamily listAvailableFamilies apiReady
    namespace export getPortList_AT

    namespace import ::xilinx::anytablestring::*

    # Persistent Session Variables : only one copy per session exists
    variable ipmgr_if
    variable ipmgr_ready 0
    variable DEBUG 0
	variable _families 
    variable _deviceLUT
    variable verbosity 0
    variable designID

    ##
    #concat_path
    #Description : Modifies path by adding element either to beginning or end of path
    #Modifies the PATH env variable
    #
    #
    #   @param the new path element to be added to ::env(PATH)
    #   @param "front" of "back", location of the newelement in path 
    #
    proc concat_path { newpathelement { location front } } {
        # If empty element is given for path, there is nothing to do...
        if { [ string equal -nocase $newpathelement "" ] } { return }

        # Assume UNIX style separator until confirmation of Windows OS type
        variable separator ":"
        if { [::xilinx::dsptool::isWindows] } {
            set separator ";"
        }

        # Get the current path and split into a list of items
        set current_path [ split $::env(PATH) $separator ]

        # Take appropriate action based on the selection of location for
        # new path element.
        switch -- $location {
            back {
                set current_path [ linsert $current_path end $newpathelement ]
            }
            front {
                set current_path [ linsert $current_path 0 $newpathelement ]
            }
            default {
                ::__concat_path $newpathelement
                return
            }
        }

        # Rejoin the path together now that the new element has been placed.
        set current_path [ join $current_path $separator ]
        # Update the global env variable.
        set ::env(PATH) $current_path
    }

    ##
    # This function modifies the LD_LIBRARY_PATH env variable by adding
    # new path-element either at the beginning or end.
    #
    #   @param the new path element to be added to ::env(LD_LIBRARY_PATH)
    #
    proc concat_ld_library_path { newpathelement { location front } } {
        # If empty element is given for path, there is nothing to do...
        if { [ string equal -nocase $newpathelement "" ] } { return }

        # Assume UNIX style separator until confirmation of Windows OS type
        variable separator ":"
        if { [::xilinx::dsptool::isWindows] } {
            set separator ";"
        }

        # Get the current path and split into a list of items
        set current_path [ split $::env(LD_LIBRARY_PATH) $separator ]

        # Take appropriate action based on the selection of location for
        # new path element.
        switch -- $location {
            back {
                set current_path [ linsert $current_path end $newpathelement ]
            }
            front {
                set current_path [ linsert $current_path 0 $newpathelement ]
            }
            default {
                ::__concat_path $newpathelement
                return
            }
        }

        # Rejoin the path together now that the new element has been placed.
        set current_path [ join $current_path $separator ]
        # Update the global env variable.
        set ::env(LD_LIBRARY_PATH) $current_path
    }
}

##
#  The init command to load the libary and create ipmgr_if instance
#  It intializes the CoreGen API and must be called only once per session
#  before the API can be used.
#
#   @param version of the api eg. 10,11,12
proc ::xilinx::dsptool::init { {api_version 10} } {
    variable _families
	setFamilies [getAllFamiliesDefault]
    loadLibs $api_version
}

##
# This function returns whether loading
# library, initialization of ipMgr, ipmodelcache
# etc. have been done or not
#
#   @return true if api has been initialized and is ready
proc ::xilinx::dsptool::apiReady {} {
    variable ipmgr_ready

    return $ipmgr_ready
}

##
# This function loads a few different shared libraries to 
# extend TCL to CoreGen API functionality. These DLLS 
# must be loaded for the API to be accessible from TCL
# It also initializes session-persistent manager and
# clears out ipModel-cache
#
#   @param version number for the api
proc ::xilinx::dsptool::loadLibs { api_version } {
    variable ipmgr_if
    variable ipmgr_ready

    variable StringContainer
    variable StringContainerI

    variable ipbuilder_c
    variable ipbuilder

    variable coregen_api_version

    variable ipmodel_obj_hashmap
    variable ipmodel_obj_cache
    variable ipmodel_cache_ipkey
    variable ipmodel_cache_fam
    variable ipmodel_cache_dev
    variable ipmodel_cache_spd
    variable ipmodel_cache_pkg
    variable ipmodel_cache_params
    variable ipmodel_cache_use_cache
    variable ipmodel_cache_params
    variable ipmodel_cache_create_time

    variable available_families_cache

    variable verbosity
    variable safeParameterPassing
    variable passParameterDifferences
    variable use_ip_object_cache
    
    variable DEBUG

    variable designID

    set verbosity 0
    set safeParameterPassing 0
    set passParameterDifferences 1
    set use_ip_object_cache 1

    set ::env(XIL_SYSGEN_NOGRAPH) 1

    setVerbosity 0
        
    # if you want your coregen ip tcl to print some debugging message
    # uncomment the following command
    # setVerbosity 1

    # Initialise the repository manager  
    ::xilinx::RepositoryManager::RefreshAllRepositories

    set coregen_api_version $api_version

    load libCitI_CoreStub[info sharedlibextension]
    load libSimI_IIPMgrStub[info sharedlibextension]
    load libUtilI_UtilStub[info sharedlibextension]
    if { $DEBUG } {
    	load [getSysgenInstallDir]/[getSharedLibrarySubdir]/[getSharedLibraryPrefix]Xlgetcoregencontrolgroupingtcl[info sharedlibextension]
    }
    
    namespace eval ::xilinx::IpMgrI {set INameID "{8f5df37c-9472-4732-bda4-a89ea3007fe2}"}
    namespace eval ::xilinx::IpI {set INameID "{c4b35fd2-3f5c-44e3-9390-f50f38117d97}"}
    
    Xilinx::CitP::FactoryLoad "libSimC_IPMgr"
    set ipmgr_id "{b74632ef-0743-4398-991c-425462b0961b}"
    set ipmgr [Xilinx::CitP::CreateComponent $ipmgr_id]
    set ipmgr_if [$ipmgr GetInterface $::xilinx::IpMgrI::INameID]
    
    Xilinx::CitP::FactoryLoad "libTcltask_Helpers"
    set StringContainer [Xilinx::CitP::CreateComponent "{28710473-d8d4-48c6-b908-d5855e4b6a00}"]
    set StringContainerI [$StringContainer GetInterface "{4a051b34-f0f4-4b80-bc1e-05e8974ac7bc}"]
    
    if { [expr $coregen_api_version >= 10] } {
        set ipbuilder_c [Xilinx::CitP::CreateComponent "{ae5b09da-4a5d-49c5-b3b2-418b42d490b4}"]
        set ipbuilder   [$ipbuilder_c GetInterface "{a2e38663-a6f3-490f-bd29-e0df9a98064d}"]

        set cgProjectPath [::xilinx::dsptool::getTempDirectory]
        set designID [::xilinx::ProjectManager::NewProject $cgProjectPath "xilinx.com:projects:coregen:2.0"]
    }


    clearIpModelCache


    set ipmgr_ready 1

}

##
# This function is to set verbosity mode for APIs : if
# debugging messages are to be printed, set verbosity to 1
#
#   @param flag to set verbosity 1 or 0
proc ::xilinx::dsptool::setVerbosity {v} {
    variable verbosity

    if {$verbosity} {
        puts "setting verbosity to $v"
    }

    set verbosity $v

    # we may consider set CG_API_DEBUG only verbosity is larger than 1
    if {$verbosity > 1} {
        set ::env(XIL_CG_API_DEBUG) 1
    } elseif { [info exists ::env(XIL_CG_API_DEBUG) ] } {
        unset ::env(XIL_CG_API_DEBUG)
    }

    if {$verbosity} {
        puts "verbosity is set to $verbosity"
    }

}

##
# This function clears out ipmodel_obj_cache,
# ipkey,dev, package, params etc.
#
proc ::xilinx::dsptool::clearIpModelCache {} {
    variable verbosity

    variable ipmodel_obj_cache
    variable ipmodel_obj_hashmap
    variable ipmodel_cache_ipkey
    variable ipmodel_cache_fam
    variable ipmodel_cache_dev
    variable ipmodel_cache_spd
    variable ipmodel_cache_pkg
    variable ipmodel_cache_params
    variable ipmodel_cache_use_cache
    variable ipmodel_cache_params
    variable ipmodel_cache_create_time
    variable available_families_cache

    set ipmodel_obj_cache ""
    array set ipmodel_obj_hashmap {}
    set ipmodel_cache_ipkey ""
    set ipmodel_cache_fam ""
    set ipmodel_cache_dev ""
    set ipmodel_cache_spd ""
    set ipmodel_cache_pkg ""
    set ipmodel_cache_use_cache false
    set ipmodel_cache_create_time [clock seconds]
    set ipmodel_cache_params [list]

    if {$verbosity} {
        puts "ipmodel_cache cleared"
    }
}

#
#Set families using platform API
#
#	@param families parameter 
#		to set _familes for this instance of DSPAPI
#
proc ::xilinx::dsptool::setFamilies { families } {
	variable _families
    variable _deviceLUT
	set _families $families
    foreach {i} $_families {
    	set _deviceLUT([lindex $i 0]) $i	
	}
}

##
# this is a utility that gets all the default families, devices, packages, speeds
# the problem is that we must set family device speed and package correctly
#
#   @return list of lists where each element list is of the form {family device speed package}
proc ::xilinx::dsptool::getAllFamilies {} {
    variable _families
    # we have to setup the 
    #set families { \
    #                   {spartan2 xc2s15 -6 cs114}  \
    #                   {spartan2e xc2s50e -7 ft256}  \
    #                   {spartan3 xc3s50 -5 pq208}  \
    #                   {spartan3e xc3s100e -5 vq100}  \
    #                   {spartan3a xc3s50a -5 tq144}  \
    #                   {spartan3adsp xc2s50e -4 cs484}  \
    #                   {spartan6 xc6slx16 -2 csg324}  \ 
    #                   {virtex xcv50 -6 bg256}  \
    #                   {virtexe xcv50e -6 cs144}  \
    #                   {virtex2 xc2v40 -6 cs144}  \
    #                   {virtex2p xc2vp2 -7 fg256}  \
    #                   {virtex4 xc4vfx12 -12 sf363}  \
    #                   {virtex5 xc5vlx30 -3 ff324}  \
    #                   {virtex6 xc6vlx75t -3 ff484}  \
    #                   {virtex6l xc6vlx75tl -1l ff484}  \
    #                   {qvirtex4 xq4vlx25 -10 ff668}  \
    #                   {qrvirtex4 xqr4vsx55 -10 cf1140}  \
    #                   {qvirtex5 xq5vsx240t -1 ef1738}  \
    #               }

    return $_families
}

##
# this is a utility that gets all the default families, devices, packages, speeds
# the problem is that we must set family device speed and package correctly
#
#   @return list of lists where each element list is of the form {family device speed package}
proc ::xilinx::dsptool::getAllFamiliesDefault {} {
    # we have to setup the 
    set families { \
                       {spartan2 xc2s15 -6 cs114}  \
                       {spartan2e xc2s50e -7 ft256}  \
                       {spartan3 xc3s50 -5 pq208}  \
                       {spartan3e xc3s100e -5 vq100}  \
                       {spartan3a xc3s50a -5 tq144}  \
                       {spartan3adsp xc2s50e -4 cs484}  \
                       {spartan6 xc6slx16 -2 csg324}  \
                       {spartan6l xc6slx9l -1l csg225}  \
                       {aspartan6 xa6slx4 -2 csg225}  \
                       {virtex xcv50 -6 bg256}  \
                       {virtexe xcv50e -6 cs144}  \
                       {virtex2 xc2v40 -6 cs144}  \
                       {virtex2p xc2vp2 -7 fg256}  \
                       {virtex4 xc4vfx12 -12 sf363}  \
                       {virtex5 xc5vlx30 -3 ff324}  \
                       {virtex6 xc6vlx75t -3 ff484}  \
                       {virtex6l xc6vlx75tl -1l ff484}  \
                       {virtex7 xc7v585t -2 ffg1761}  \
                       {virtex7l xc7v585tl -2l ffg1761}  \
                       {kintex7 xc7k70t -2 fbg676}  \
                       {kintex7l xc7k70tl -2l fbg676}  \
                       {artix7 xc7a50t -2 csg324}  \
                       {qvirtex4 xq4vlx25 -10 ff668}  \
                       {qrvirtex4 xqr4vsx55 -10 cf1140}  \
                       {qvirtex5 xq5vsx240t -1 ff1738}  \
                       {qvirtex6 xq6vsx315t -1 ffg1156}  \
                       {qvirtex6l xq6vsx315tl -1l ffg1156}  \
                       {qspartan6 xq6slx75 -2 fg484}  \
                       {qspartan6l xq6slx75l -1l fg484}  \
                       {zynq xc7z020 -2 clg484}  \
                   }
	return $families
}

##
# This is a lookup table function - returns familyname,
# device, speed, package for a particular family
#
#   @return list of the type {family device speed package}
proc ::xilinx::dsptool::getDevSetting {family} {
    variable _deviceLUT
    #set deviceLUT(spartan2) {spartan2 xc2s15 -6 cs114}
    #set deviceLUT(spartan2e) {spartan2e xc2s50e -7 ft256}
    #set deviceLUT(spartan3) {spartan3 xc3s50 -5 pq208} 
    #set deviceLUT(spartan3e) {spartan3e xc3s100e -5 vq100} 
    #set deviceLUT(spartan3a) {spartan3a xc3s50a -5 tq144} 
    #set deviceLUT(spartan3adsp) {spartan3adsp xc2s50e -4 cs484} 
    #set deviceLUT(spartan6) {spartan6 xc6slx16 -2 csg324} 
    #set deviceLUT(virtex) {virtex xcv50 -6 bg256} 
    #set deviceLUT(virtexe) {virtexe xcv50e -6 cs144} 
    #set deviceLUT(virtex2) {virtex2 xc2v40 -6 cs144} 
    #set deviceLUT(virtex2p) {virtex2p xc2vp2 -7 fg256} 
    #set deviceLUT(virtex4) {virtex4 xc4vfx12 -12 sf363} 
    #set deviceLUT(virtex5) {virtex5 xc5vlx30 -3 ff324}
    #set deviceLUT(virtex6) {virtex6 xc6vlx75t -3 ff484}
    #set deviceLUT(virtex6l) {virtex6l xc6vlx75tl -1l ff484}
    #set deviceLUT(qvirtex4) {qvirtex4 xq4vlx25 -10 ff668}
    #set deviceLUT(qrvirtex4) {qrvirtex4 xqr4vsx55 -10 cf1140} 
    #set deviceLUT(qvirtex5) {qvirtex5 xq5vsx240t -1 ef1738} 

    return $_deviceLUT($family)
}

# returns 1 if the ipkey is available for a family
##
# Checks if an ipkey exists for the specified family 
#
#   @param ipkey eg. "fir_compiler_v5_0"
#   @param Xilinx device family eg. "Virtex4"
#   @return 1 if ipkey exists for the family, 0 otherwise
proc ::xilinx::dsptool::isAvailableFamily {ipKey2test family} {
    set ipKey $ipKey2test
    if {[llength $ipKey2test] != 1} {
        set coreName [lindex $ipKey2test 0]
        set coreVer [lindex $ipKey2test 1]
        set ipKey [findIPKey $coreName $coreVer]
    }
    set componentID [::xilinx::RepositoryManager::GetComponentID $ipKey]

    #################################################################
    #   OLD CODE
    #################################################################
    #variable ipmgr_if

    #if {[llength $ipKey2test] == 1} {
        #set searchByKey 1
    #} else {
        #set coreName [lindex $ipKey2test 0]
        #set coreVer [lindex $ipKey2test 1]
        #set searchByKey 0
    #}

    if {[llength $family] == 1} {
        set devsetting [getDevSetting [string tolower $family]]
        set fam [lindex $devsetting 0]
        set dev [lindex $devsetting 1]
        set spd [lindex $devsetting 2]
        set pkg [lindex $devsetting 3]
    } else {
        set fam [string tolower [lindex $family 0]]
        set dev [lindex $family 1]
        set spd [lindex $family 2]
        set pkg [lindex $family 3]
    }

    set fam [string tolower $fam]
    #################################################################

    set supported 0
    foreach supportedFamily [::xilinx::TGIHelper::getComponentFamilies $componentID] {
        if {$supportedFamily == $fam} { 
            set supported 1
            break;
        }
    }
    if {$supported == 0} { return 0 }

    set componentPart $dev$spd$pkg
    set status [::xilinx::TGIHelper::getComponentPartStatus $componentID $componentPart]
    #if { $status == "" || $status == "Removed" || $status == "Superseded" || $status == "Discontinued" } { set supported 0 }
    if { $status == "" || $status == "Removed" } { set supported 0 }

    return $supported

    #################################################################
    #   OLD CODE
    #################################################################
    #$ipmgr_if setProjectProperty devicefamily $fam
    #$ipmgr_if setProjectProperty device $dev
    #$ipmgr_if setProjectProperty speedgrade $spd
    #$ipmgr_if setProjectProperty package $pkg

    #set ipKeyList [$ipmgr_if listIPKeys]
    #$ipKeyList GetIterator ipKeyItr


    #$ipKeyItr First

    #while {[$ipKeyItr IsEnd] != 1} {
        #set ipKey [$ipKeyItr CurrentItem]

        #if {$searchByKey ==1} {
            ## search by key
            #if {$ipKey == $ipKey2test} {
                #return 1
            #}
        #} else {
            ## search by name and version
            #set ipName [$ipmgr_if getIPProperty $ipKey "Name"]
            #set ipVersion [$ipmgr_if getIPProperty $ipKey "Version"]
            #if { $ipName == $coreName && $ipVersion == $coreVer } {
                #return 1
            #}
        #}
        #$ipKeyItr Next
    #}

    #return 0
    #################################################################
}

## 
# Checks if an ipkey exists for the specified family
#
#   @param ipkey eg. "fir_compiler_v5_0"
#   @param Xilinx device family eg. "Virtex4"
#
#   @return Boolean Anytable, true if ipkey exists
proc ::xilinx::dsptool::isAvailableFamily_AT { ipKey2Test family } {
	set ret [isAvailableFamily $ipKey2Test $family]
	set ret [BoolToAnyBool $ret]
	return $ret
}

proc ::xilinx::dsptool::getOldFormatIPKey { ipKey } {
    set vlnvList [split $ipKey ":"]

    set componentName [ lindex $vlnvList 2 ]
    set componentVersion [ lindex $vlnvList 3 ]

    set componentVersion [ string map { . _ } $componentVersion ]

    set oldIpKey [ list $componentName $componentVersion ]
    set oldIpKey [ join $oldIpKey "_v" ]
    return $oldIpKey
}

proc ::xilinx::dsptool::isIPXACTIpCoregenCompatible { componentID } {
    foreach viewID [::xilinx::TGI::getComponentViewIDs $componentID false] {
        foreach envIdentifier [::xilinx::TGI::getViewEnvIdentifiers $viewID] {
            if { $envIdentifier eq ":ise.xilinx.com:coregen.model" } { return true }
        }
    }

    return false
}

##
# find IP Key using IP name and IP version
#
#   @param Name of the IP
#   @param Version of the IP
#
#   @return IPKey, a unique string identifier for the IP used by IP Manager
proc ::xilinx::dsptool::findIPKey {name version} {
    foreach vlnv [::xilinx::RepositoryManager::ListComponentVLNVs] {
        set componentID [::xilinx::RepositoryManager::GetComponentID $vlnv]
        if { $componentID eq "" } { continue }

        set ipType [::xilinx::TGIHelper::getComponentIpType $componentID]
        if { $ipType != "COREGEN" && $ipType != "SPIRIT" } { continue }
        if { $ipType == "SPIRIT" && [isIPXACTIpCoregenCompatible $componentID] == false } { continue }

        set vlnvList  [split $vlnv ":"]
        set ipVersion [lindex $vlnvList 3]

        set ipDisplayName [::xilinx::TGI::getDisplayName $componentID]

        if {$ipDisplayName == $name && $ipVersion == $version} {
            return $vlnv
        }
    }

    #################################################################
    #   OLD CODE
    #################################################################
    #variable ipmgr_if

    #set ipKeyList [$ipmgr_if listAllIPKeys]
    #$ipKeyList GetIterator ipKeyItr

    ## ALL IP irrespective of device settings (some IP is platform specific! e.g. MIG)
    #$ipKeyItr First
    #while {[$ipKeyItr IsEnd] != 1} {
	#set ipKey [$ipKeyItr CurrentItem]
	#set ipName [$ipmgr_if getIPProperty $ipKey "Name"]
	#set ipVersion [$ipmgr_if getIPProperty $ipKey "Version"]

        #if {$ipName == $name && $ipVersion == $version} {
            #return $ipKey
        #}
   
	#$ipKeyItr Next
    #}
    #################################################################

    return ""
}

##
# find IP Key using IP Name and IP version and return an anytable for the same
#   @param Name of the IP
#   @param Version of the IP
#
#   @return an AnyTable represenation of IPKey
proc ::xilinx::dsptool::findIPKey_AT {name version} {
    set ipKey [findIPKey $name $version]
    return [String2AnyString $ipKey]
}

##
# returns a list containing all IPKeys (comprised of
# IPname, IPversion and IPKey)
#
#   @return a list containing all IPKeys
proc ::xilinx::dsptool::listIPs {} {
    set ipList [list]
    foreach vlnv [::xilinx::RepositoryManager::ListComponentVLNVs] {
        set componentID [::xilinx::RepositoryManager::GetComponentID $vlnv]
        if { $componentID eq "" } { continue }

        set ipType [::xilinx::TGIHelper::getComponentIpType $componentID]
        if { $ipType != "COREGEN" && $ipType != "SPIRIT" } { continue }
        if { $ipType == "SPIRIT" && [isIPXACTIpCoregenCompatible $componentID] == false } { continue }

        set oneIP [list]
        set vlnvList  [split $vlnv ":"]
        set ipVersion [lindex $vlnvList 3]

        set ipDisplayName [::xilinx::TGI::getDisplayName $componentID]
        lappend oneIP $ipDisplayName
        lappend oneIP $ipVersion
        lappend oneIP $vlnv

        lappend ipList $oneIP
    }
    #################################################################
    #   OLD CODE
    #################################################################
    #variable ipmgr_if

    #set ipList [list]

    #set ipKeyList [$ipmgr_if listAllIPKeys]
    #$ipKeyList GetIterator ipKeyItr

    ## ALL IP irrespective of device settings (some IP is platform specific! e.g. MIG)
    #$ipKeyItr First
    #while {[$ipKeyItr IsEnd] != 1} {
        #set oneIP [list]

	#set ipKey [$ipKeyItr CurrentItem]
	#set ipName [$ipmgr_if getIPProperty $ipKey "Name"]
	#set ipVersion [$ipmgr_if getIPProperty $ipKey "Version"]

        #lappend oneIP $ipName
        #lappend oneIP $ipVersion
        #lappend oneIP $ipKey

        #lappend ipList $oneIP

	#$ipKeyItr Next
    #}
    #################################################################

    return $ipList
}

# find IP Key using IP name and IP version
#comment this out
proc ::xilinx::dsptool::findIPKey_AT {name version} {
    set ip_key [findIPKey $name $version]
    set ip_key_at [String2AnyString $ip_key]
    return $ip_key_at
}

##
# find IP Key using IP name and IP version
#   
#   @return a list of the form {name version}
proc ::xilinx::dsptool::findNameVersion {ipKey} {
    set vlnvList  [split $ipKey ":"]
    set ipVersion [lindex $vlnvList 3]

    set componentID [::xilinx::RepositoryManager::GetComponentID $ipKey]
    set ipDisplayName [::xilinx::TGI::getDisplayName $componentID]

    set ret [list]

    lappend ret $ipDisplayName
    lappend ret $ipVersion

    #################################################################
    #   OLD CODE
    #################################################################
    #variable ipmgr_if

    #set ipKeyList [$ipmgr_if listAllIPKeys]
    #$ipKeyList GetIterator ipKeyItr

    #set ret [list]

    ## ALL IP irrespective of device settings (some IP is platform specific! e.g. MIG)
    #$ipKeyItr First
    #while {[$ipKeyItr IsEnd] != 1} {
	#if {$ipKey == [$ipKeyItr CurrentItem]} {
            #set ipName [$ipmgr_if getIPProperty $ipKey "Name"]
            #set ipVersion [$ipmgr_if getIPProperty $ipKey "Version"]
            #lappend ret $ipName
            #lappend ret $ipVersion

            #return $ret
        #}

	#$ipKeyItr Next
    #}
    #################################################################

    return $ret
}

##
# find IP Key using IP name and IP version returns in an AnyTable Sequence
#
#   @return an AnyTable Sequence [ name, version ]
proc ::xilinx::dsptool::findNameVersion_AT {ipKey} {
    set nameVersion [findNameVersion $ipKey]
    set ret [StringList2AnyString $nameVersion]
}

#########################################################################
#                 COMMENTING OUTDATED CODE
#########################################################################
##
# return a list of ip updates
#
#   @return list containing all the ip updates {ipupdate1 ipupdate2 ipupdate3}
#proc ::xilinx::dsptool::listInstalledIPUpdates {} {
    #variable ipmgr_if
    
    #set ipUpdatesInstalled [$ipmgr_if listInstalledIPUpdates]
    #$ipUpdatesInstalled GetIterator ipUpdatesInstalledItr
    
    #$ipUpdatesInstalledItr First

    #set ipupdates [list]

    #while {[$ipUpdatesInstalledItr IsEnd] != 1} {
        #lappend ipupdates [string trim [$ipUpdatesInstalledItr CurrentItem]]
        #$ipUpdatesInstalledItr Next
    #}

    #return $ipupdates
#}

##
# returns a list of ip updates in a serialized anytable format
#
#   @return a AnyTable Sequence of IP updates eg. [ ipupdate1, ipupdate2 ]
#proc ::xilinx::dsptool::listInstalledIPUpdates_AT {} {
    #set ipUpdates [listInstalledIPUpdates]
    
    #set seq [StringList2AnyString $ipUpdates]

    #return $seq
#}
#########################################################################

##
# return a list of availble family for that core
#
#   @param ipkey
#
#   @return a list of families supported by the ipkey eg. {virtex4 virtex6 spartan2}
proc ::xilinx::dsptool::listAvailableFamilies {ipKey} {
    #variable ipmgr_if
    variable available_families_cache

    if { [ info exists available_families_cache($ipKey) ] } {
        set supported $available_families_cache($ipKey)
        return $supported
    }

    set families [getAllFamilies]

    set supported [list]
    
    foreach family $families {
        set fam [lindex $family 0]

        if {[isAvailableFamily $ipKey $fam]} {
            lappend supported $fam
        }
    }

    set available_families_cache($ipKey) $supported
    return $supported
}

##
# return a list of availble family for that core in a serialized AnyTable
#
#   @param ipkey
#
#   @return a list of families supported by the ipkey as an AnyTable Sequence eg. [ virtex4, spartan3 ]
proc ::xilinx::dsptool::listAvailableFamilies_AT {ipKey} {
    set families [listAvailableFamilies $ipKey]
    set seq [StringList2AnyString $families]

    return $seq
}

##
# compares two param-lists : in case of mismatch in length/
# any param-name/for diff exceeding max-limit 4, then returns
# 0; else returns a list, containing the difference.
#
#   @param list of lists representing the ip params name and value of the form {{param11 value11} {param12 value12} }
#   @param list of lists representing the ip params name and value of the form {{param21 value21} {param22 value22} }
#
#   @return 0 if excessive diffeence in params else a list of different params
proc ::xilinx::dsptool::findOnlyDiffParam {params1 params2} {
    set len1 [llength $params1]
    set len2 [llength $params2]

    if { $len1 != $len2 } {
        return 0
    }

    set param_diff [list]

    for { set i 0} { $i < $len1 } { incr i } {
        set param1 [lindex $params1 $i]
        set param2 [lindex $params2 $i]
        set name1 [lindex $param1 0]
        set name2 [lindex $param2 0]

        if { $name1 != $name2 } {
            #puts "name1: $name1, name2: $name2"
            return 0
        } elseif { $param1 != $param2 } {
            if { [llength $param_diff] >= 4 } {
                #puts "param diff exceeds limit: $param_diff"
                return 0
            } else {
                lappend param_diff $param2
            }
        }
    }

    return $param_diff
}

proc ::xilinx::dsptool::buildIPHashKey {ipKey fam dev spd pkg} {
    return [concat $ipKey $fam $dev $spd $pkg]
}

##
# creates an instance of an IPcore, suitable for parameterization,
# using ipKey, for a family (if mentioned) and reuses a previously
# created ipobj if contactr_cache is true
#
#   @param ipkey eg. "fir_compiler_v5_0"
#   @param device family eg. "virtex5"
#   @param flag to reuse a pre-created ipobj
#
#   @return ip object
proc ::xilinx::dsptool::createIP {ipKey {family ""} {contact_cache true}} {
    
    variable verbosity
    variable designID

    if {$verbosity} {
        puts "createIP contact_cache passed: $contact_cache"
    }

    set enter_clicks [clock clicks -milliseconds]

    variable ipmgr_if
    variable ipbuilder
    variable coregen_api_version

    variable ipmodel_obj_hashmap
    variable ipmodel_obj_cache
    variable ipmodel_cache_ipkey
    variable ipmodel_cache_fam
    variable ipmodel_cache_dev
    variable ipmodel_cache_spd
    variable ipmodel_cache_pkg
    variable ipmodel_cache_use_cache
    variable ipmodel_cache_create_time
    variable use_ip_object_cache

    if {$family == ""} {
        set availList [listAvailableFamilies $ipKey];
        set numOfAvail [llength $availList]
        if { $numOfAvail > 0} {
            ## last family is coming as zynq - hence using end-1 - should we use 0-th element instead???
            #set family [lindex $availList end-1]
            set family [lindex $availList end] 
            # set family virtex4
        } else {
            set family virtex4
        }
        # puts "Using family: $family"
    }

    if {[llength $family] == 1} {
        set devsetting [getDevSetting [string tolower $family]]
        set fam [lindex $devsetting 0]
        set dev [lindex $devsetting 1]
        set spd [lindex $devsetting 2]
        set pkg [lindex $devsetting 3]
    } else {
        set fam [string tolower [lindex $family 0]]
        set dev [lindex $family 1]
        set spd [lindex $family 2]
        set pkg [lindex $family 3]
    }

    set current_seconds [clock seconds]

    # if found in cache, use cached ipobj
#    if { $contact_cache && $ipmodel_obj_cache != "" && $ipmodel_cache_ipkey == $ipKey && $ipmodel_cache_fam == $fam && $ipmodel_cache_dev == $dev && $ipmodel_cache_spd == $spd && $ipmodel_cache_pkg == $pkg && [expr $current_seconds-$ipmodel_cache_create_time] <= 300 && [expr $current_seconds-$ipmodel_cache_create_time] > 0} {
#        if {$verbosity} {
#            puts "Use cached ipmodel"
#        }
#        set ip $ipmodel_obj_cache
#        set ipmodel_cache_use_cache true
#    } else {
#        if { [expr $coregen_api_version >= 10] } {
#            set ippath [$ipmgr_if getModelPath $ipKey]
#            $ipbuilder setProjectProperty devicefamily $fam
#            $ipbuilder setProjectProperty device $dev
#            $ipbuilder setProjectProperty speedgrade $spd
#            $ipbuilder setProjectProperty package $pkg
#            
#            set ip [$ipbuilder createIP $ippath]
#        } else {
#            
#            $ipmgr_if setProjectProperty devicefamily $fam
#            $ipmgr_if setProjectProperty device $dev
#            $ipmgr_if setProjectProperty speedgrade $spd
#            $ipmgr_if setProjectProperty package $pkg
#            
#            set ip [$ipmgr_if createIP $ipKey]
#        }
#        # if $contact_cache is set, then cache data for this ipobj
#        if {$contact_cache} {
#            set ipmodel_obj_cache $ip
#            set ipmodel_cache_ipkey $ipKey
#            set ipmodel_cache_fam $fam 
#            set ipmodel_cache_dev $dev
#            set ipmodel_cache_spd $spd
#            set ipmodel_cache_pkg $pkg
#            set ipmodel_cache_use_cache false
#            set ipmodel_cache_create_time [clock seconds]
#
#            # puts "set ipmodel cache"
#        }
#    }

    set ipHashKey [buildIPHashKey $ipKey $fam $dev $spd $pkg]
#    puts "# IP Hash: $ipHashKey"
    if { [info exists ipmodel_obj_hashmap($ipHashKey)] && $use_ip_object_cache } {
#        puts "# Found existing IP"
    } else {
        set componentID [::xilinx::RepositoryManager::GetComponentID $ipKey]
        set ippath [::xilinx::TGIHelper::getFilePaths $componentID ":ise.xilinx.com:coregen.model" true]
        if { [::xilinx::dsptool::isWindows] } {
            set ippath [ string trim $ippath "{}" ]
        }

        #set ippath [$ipmgr_if getModelPath $ipKey]
        $ipbuilder setProjectProperty devicefamily $fam
        $ipbuilder setProjectProperty device $dev
        $ipbuilder setProjectProperty speedgrade $spd
        $ipbuilder setProjectProperty package $pkg
        
        set ip [$ipbuilder createIP $ippath]
        set ipmodel_obj_hashmap($ipHashKey) $ip
        # puts "# $ipKey created"
    }

    set ip $ipmodel_obj_hashmap($ipHashKey)
    set finish_clicks [clock clicks -milliseconds]

    if {$verbosity} {
        puts "Elapsed time in createIP: [expr $finish_clicks-$enter_clicks] ms"
    }

    return $ip;
}

##
# sets values for a set of XCO parameters, for an IpObject in one batch as opposed to 
# in order (refer to setParameterValuesInOrder)
#   @param an ipobject
#   @param ip parameters specified as a list { {param1 value1} {param2 value2} }
#
#   @return an empty string if successful, else an error message
proc ::xilinx::dsptool::setParameterValues {ipobj paramlist} {
    upvar $ipobj ip
    variable StringContainerI

    $StringContainerI Clear
    foreach pair $paramlist {
        # old way of setting parameters one by one
        # set error [$ip setParameterProperty [lindex $pair 0] "value" [lindex $pair 1]]
        # if { $error != "" } {
            # return $error
        # }

        set var "[lindex $pair 0] [lindex $pair 1]"
        $StringContainerI Add var
    }
    # $StringContainerI Dump

    if { [catch {set err_msg [$ip setParameterValues $StringContainerI] } err2 ] } {
        if { $err2 != "" && [string tolower $err2] != "ok" } {
            return $err2
        }
    }

    if { $err_msg != "" && [string tolower $err_msg] != "ok" } {
        return $err_msg
    } else {
        return ""
    }
    
}

##
# for enabled parameters of an IpObj, sets corresponding param-values,
# as provided in "paramlist"; for other params, sets coregen value.
# in case of invald param-value, errors out. Different from setParameterValues
# in that ipobject is repeatedly updated with one parameter at a time
# with the parameter precedence specified by order or parameters in paramList
#   @param an ipobject
#   @param ip parameters specified as a list { {param1 value1} {param2 value2} }
#
#   @return an empty string if successful, else an error message
proc ::xilinx::dsptool::setParameterValuesInOrder {ipobj paramlist {forceSetParam false}} {
    upvar $ipobj ip
    variable verbosity
    variable passParameterDifferences

    set enter_click [clock clicks -milliseconds]

    foreach pair $paramlist {
        set paramName [lindex $pair 0]
        set paramValue [lindex $pair 1]

        # puts "Setting $paramName to $paramValue"

        set currentValue [$ip getParameterProperty $paramName "value"]
        set paramEnabled [$ip getParameterProperty $paramName "enabled"]
        set paramType [$ip getParameterProperty $paramName "type"]
        
        set error ""
        set setParam 1
        if  { $forceSetParam == false && $passParameterDifferences } {
            set setParam [expr {$currentValue != $paramValue}]
        }
            
        if { $paramEnabled } {
            if {$paramType == "BOOL"} {
                if {$paramValue} {
                    set paramValue true
                } else {
                    set paramValue false
                }
            }
            if { $setParam } {
                set error [$ip setParameterProperty $paramName "value" $paramValue]
            } 
        } else {
            if { $setParam } {
                #set error [$ip setParameterProperty $paramName "value" $currentValue]
            }
        }

        if {$error != "" && [string tolower $error] != "ok" } {
            $ip setParameterProperty $paramName "value" $currentValue
            # puts "Got error $error"
            return $error
        } 
    }

    set finish_click [clock clicks -milliseconds]
    if {$verbosity} {
        puts "Elapsed time in setParameterValuesInOrder: [expr $finish_click - $enter_click] ms"
    }

    return ""
}

##
# Test function to iterate over all the ports - Consider deleting
#
#   @param IP Object
#
proc ::xilinx::dsptool::getPortInfo {ipobj} {
    upvar $ipobj ip

    set portList [$ip listPorts]
    $portList GetIterator portNameItr

    $portNameItr First
    set index 0
    while {[$portNameItr IsEnd]!=1} {
        set portName [$portNameItr CurrentItem]
        if { ![$ip getPortProperty $portName "Enabled"] } {
            $portNameItr Next
            continue
        }
	set portDirection [string tolower [$ip getPortProperty $portName "direction" ]]
	set portType [string tolower [$ip getPortProperty $portName "type" ]]
	if {$portType == "bus"} {
            set left [$ip getPortProperty $portName "leftindex" ]
            set right [$ip getPortProperty $portName "rightindex" ]
            set portName [string tolower $portName]
            # puts "$portName $portDirection $portType [expr $left-$right+1]"
	} else {
            set portName [string tolower $portName]
            # puts "$portName $portDirection $portType"
	}
        $portNameItr Next
    }
}

##
# attempts to set values to parameters(enabled) for a particular
# ip - in case of any invalid param-value, returns error, else
# returns OK
#
#   @param ipkey
#   @param params as list of lists { {param value} {param value} }
#
#   @return error message string else returns "OK"
proc ::xilinx::dsptool::validateParams {ipKey params {family ""}} {
    set ip [createIP $ipKey $family]

    variable safeParameterPassing
    if { $safeParameterPassing } {
        set error [setParameterValues ip $params]
    } else {
        set error [setParameterValuesInOrder ip $params]
    }

    if { $error != ""} {
        return $error
    }
    return "OK"
}

##
# finds param names for an ip and returns in an Anytable Sequence
#
#   @param ipKey
#
#   @return AnyTable Sequence of paramnames associated with the ip 
#
proc ::xilinx::dsptool::getParamList_AT {ipKey} {
    set ip [createIP $ipKey ""]
    set paramList [$ip listParameters]
    $paramList GetIterator paramIter
    $paramIter First
    
    
    SeqBegin paramListSeq

    while { [$paramIter IsEnd] != 1 } {
	set paramName [$paramIter CurrentItem]
        set paramName [string tolower $paramName]

        SeqAppendStr paramListSeq $paramName
        
        $paramIter Next
    }

    SeqEnd paramListSeq

    return $paramListSeq
}

##
# finds generic names for an ip and returns in an Anytable Sequence
#
#   @param ipkey
#  
#   @return AnyTable Sequence of HDL generics associated with the IP
proc ::xilinx::dsptool::getGenericList_AT {ipKey} {
    set ip [createIP $ipKey ""]
    set paramList [$ip listGenerics]
    $paramList GetIterator paramIter
    $paramIter First
    
    
    SeqBegin paramListSeq

    while { [$paramIter IsEnd] != 1 } {
	set paramName [$paramIter CurrentItem]
        set paramName [string tolower $paramName]

        SeqAppendStr paramListSeq $paramName
        
        $paramIter Next
    }

    SeqEnd paramListSeq

    return $paramListSeq
}

##
# gets non-parameter info of a metaparam(typically displayed in Coregen IP GUIs
# using text labels) for an IP and returns in form of a table
#
#   @param IP Object
#   @param name of the meta parameter
#
#   @return AnyTable Dictionary of the MetaParameter properties { "value"=>value, type=>"STRING", enabled=>"false" }
proc ::xilinx::dsptool::getMetaParamProperties_AT {ipobj paramName} {
    upvar $ipobj ip
    set value [$ip getMetaParameterProperty $paramName "value"]
    DictBegin oneParam
    set currentValue [$ip getMetaParameterProperty $paramName "value"]
    DictPut oneParam "value" $currentValue
    DictPut oneParam "type" "STRING"
    DictPut oneParam "enabled" "false"
    DictEnd oneParam
    return $oneParam
}

##
# constructs a table with all the param and metaparam data, with current
# value, for an IP. This is to faicilitate callback, when param-values
# have been changed
#
#   @param IP object
#   @param a tcl array with paramName as key - auxilary information added to parameter property as "forced"
#   @param optional parameter for also including metaParams in the returned AnyTable
#
#   @return AnyTable Dictionary with key set to paramnames and value is another Dictionary specifying the param properties
#       eg. {
#                data_width => {
#                                   value => 36, 
#                                   type => "INT", 
#                                   enabled => 1,
#                               },
#           }
proc ::xilinx::dsptool::constructParam4CallbackAT {ipobj forcedTableVar {metaParams ""} } {
    upvar $ipobj ip
    upvar $forcedTableVar forcedTable
    #upvar $metaParamsVar metaParams

    set paramList [$ip listParameters]
    $paramList GetIterator paramIter
    $paramIter First
    
    DictBegin paramSet

    while { ! [$paramIter IsEnd] } {
	set paramName [$paramIter CurrentItem]

        DictBegin oneParam

        set currentValue [$ip getParameterProperty $paramName "value"]
        set paramType [$ip getParameterProperty $paramName "type"]
        set paramEnabled [$ip getParameterProperty $paramName "enabled"]

        DictPutStr oneParam "type" $paramType
        DictPut oneParam "enabled" $paramEnabled
        if {[info exists forcedTable($paramName)]} {
            DictPut oneParam "forced" true
        }

        if {$paramType == "INT" } {
            DictPut oneParam "value" $currentValue

        } elseif {$paramType == "LIST" } {
            DictPutStr oneParam "value" $currentValue
        } elseif {$paramType == "STRING" || $paramType == "BIN" } {
            DictPutStr oneParam "value" $currentValue
        } elseif {$paramType == "DOUBLE" } {
            DictPut oneParam "value" $currentValue
        } elseif {$paramType == "BOOL" } {
            DictPut oneParam "value" [string tolower $currentValue]
        } elseif {$paramType == "FILE" } {
            DictPutStr oneParam "value" $currentValue
            DictPut oneParam "evaluate" false
        }

        DictEnd oneParam

        DictPut paramSet [string tolower $paramName] $oneParam

        $paramIter Next
    }
    if { $metaParams != "" } {
        foreach {i} $metaParams {            
            DictBegin oneParam
            set currentValue [$ip getMetaParameterProperty $i "value"]
            DictPut oneParam "value" [String2AnyString $currentValue]
            DictPutStr oneParam "type" "STRING"
            DictPut oneParam "enabled" false
            DictEnd oneParam
            DictPut paramSet $i $oneParam
        }
    }
    
    DictEnd paramSet

    return $paramSet
}

# gets license key for an ip
proc ::xilinx::dsptool::getIPLicense {ip_key} {
    #variable ipmgr_if
    #set core_state [$ipmgr_if getIPProperty $ip_key "check_license"]

    #return $core_state
    set componentID [::xilinx::RepositoryManager::GetComponentID $ip_key]
    set core_state [::xilinx::TGIHelper::getComponentLicenseKeys $componentID]
    return $core_state
}

# returns true, if license is available for an ip
proc ::xilinx::dsptool::isPayCore {ip_key} {
    set license [getIPLicense $ip_key]
    if { $license != "" } {
        return true
    } else {
        return false
    }
}

# same as above
proc ::xilinx::dsptool::isPayCore_AT { ip_key } {
    return [isPayCore $ip_key]
}

# returns whether status for license is bought/invalid etc.
proc ::xilinx::dsptool::getLicenseStatus { license } {
    variable ipmgr_if
    set lic_status [$ipmgr_if getLicenseStatus $license]
    return $lic_status
}

# returns license type(Full/Bought etc.)
proc ::xilinx::dsptool::getLicenseType { ip_key } {
    set license [getIPLicense $ip_key]
    if { $license == "" } {
        return "Full"
    } else {
        set lic_status [getLicenseStatus $license]
        return $lic_status
    }
}

# returns license type(Full/Bought etc.) in an anytable
proc ::xilinx::dsptool::getLicenseType_AT { ip_key } {
    set license_type [getLicenseType $ip_key]
    return [String2AnyString $license_type]
}

# returns true if the ipcore has VHDL behavioral model
proc ::xilinx::dsptool::hasVHDLBehavioralModel {ip_key} {
    variable ipmgr_if
    set oldIPKey [xilinx::dsptool::getOldFormatIPKey $ip_key]
    # For few IPs, old-style IPKeys come appended with "_xst"
    if { $oldIPKey == "cmpy_v3_1" || $oldIPKey == "cic_compiler_v2_0" || $oldIPKey == "dds_compiler_v4_0" || $oldIPKey == "convolution_v7_0" } {
        set appendstr "_xst"
        set oldIPKey $oldIPKey$appendstr
    }
    set beh [$ipmgr_if getIPProperty $oldIPKey "VHDLBehavioralModel"]

    return $beh
}

# returns true if the ipcore has Verilog behavioral model
proc ::xilinx::dsptool::hasVerilogBehavioralModel {ip_key} {
    variable ipmgr_if
    set oldIPKey [xilinx::dsptool::getOldFormatIPKey $ip_key]
    # For few IPs, old-style IPKeys come appended with "_xst"
    if { $oldIPKey == "cmpy_v3_1" || $oldIPKey == "cic_compiler_v2_0" || $oldIPKey == "dds_compiler_v4_0" || $oldIPKey == "convolution_v7_0" } {
        set appendstr "_xst"
        set oldIPKey $oldIPKey$appendstr
    }
    set beh [$ipmgr_if getIPProperty $oldIPKey "VerilogBehavioralModel"]

    return $beh
}

# returns true if the ipcore has VHDL behavioral model
proc ::xilinx::dsptool::hasVHDLBehavioralModel_AT {ip_key} {
    set beh [hasVHDLBehavioralModel $ip_key]
    set beh_at [BoolToAnyBool $beh]
    return $beh_at
}

# returns true if the ipcore has Verilog behavioral model
proc ::xilinx::dsptool::hasVerilogBehavioralModel_AT {ip_key} {
    set beh [hasVerilogBehavioralModel $ip_key]
    set beh_at [BoolToAnyBool $beh]
    return $beh_at
}

#########################################################################
#                 COMMENTING OUTDATED CODE
#########################################################################
# returns the core state for an ip(whether it is Available or not etc.)
#proc ::xilinx::dsptool::getCoreState {ip_key} {
    #variable ipmgr_if
    #set core_state [$ipmgr_if getCoreState $ip_key]

    #return $core_state
#}
#########################################################################

#
# get info about parameters(in case justValue option is set, only
# value is used) for an ip and returns that in form of a table
#
proc ::xilinx::dsptool::constructParamAT {ipobj {justValue false}} {
    upvar $ipobj ip

    set paramList [$ip listParameters]
    $paramList GetIterator paramIter
    $paramIter First
    
    DictBegin paramSet

    while { ![$paramIter IsEnd] } {
	set paramName [$paramIter CurrentItem]
        set currentValue [$ip getParameterProperty $paramName "value"]

        if {$justValue} {
            set oneParam ""
        } else {
            DictBegin oneParam
        }

        set defaultValue [$ip getParameterProperty $paramName "defaultvalue"]
        set paramType [$ip getParameterProperty $paramName "type"]
        set paramDescription [$ip getParameterProperty $paramName "description"]
        set paramTooltip [$ip getParameterProperty $paramName "tooltip"]
        set paramEnabled [$ip getParameterProperty $paramName "enabled"]

        DictPutStr oneParam "type" $paramType
        DictPutStr oneParam "tooltip" $paramTooltip
        DictPutStr oneParam "description" $paramDescription
        DictPut oneParam "enabled" $paramEnabled

        if {$paramType == "INT" } {
            if {$justValue} {
                set oneParam $currentValue
            } else {
                set min [$ip getParameterProperty $paramName "minimumvalue"] 
                set max [$ip getParameterProperty $paramName "maximumvalue"] 
                DictPut oneParam "default" $defaultValue
                DictPut oneParam "value" $currentValue
                DictPut oneParam "max" $max
                DictPut oneParam "min" $min
            }
        } elseif {$paramType == "LIST" } {
            if {$justValue} {
                set oneParam [String2AnyString $currentValue]
            } else {
                set items [$ip getParameterProperty $paramName "list"] 
                DictPut oneParam "items" [StringList2AnyString $items]
                DictPutStr oneParam "default" $defaultValue
                DictPutStr oneParam "value" $currentValue
            }
        } elseif {$paramType == "STRING" } {
            if {$justValue} {
                set oneParam [String2AnyString $currentValue]
            } else {
                DictPutStr oneParam "default" $defaultValue
                DictPutStr oneParam "value" $currentValue
            }
        } elseif {$paramType == "DOUBLE" } {
            if {$justValue} {
                set oneParam $currentValue
            } else {
                DictPut oneParam "default" $defaultValue
                DictPut oneParam "value" $currentValue
            }
        } elseif {$paramType == "BOOL" } {
            if {$justValue} {
                set oneParam [string tolower $currentValue]
            } else {
                DictPut oneParam "default" [string tolower $defaultValue]
                DictPut oneParam "value" [string tolower $currentValue]
            }
        } elseif {$paramType == "FILE" } {
            if {$justValue} {
                set oneParam [String2AnyString $currentValue]
            } else {
                DictPutStr oneParam "default" $defaultValue
                DictPutStr oneParam "value" $currentValue
                DictPut oneParam "evaluate" false
            }
        }

        if {$justValue} {
        } else {
            DictEnd oneParam
        }

        DictPut paramSet [string tolower $paramName] $oneParam

        $paramIter Next
    }

    DictEnd paramSet

    return $paramSet
}

# returns name-value pair list for parameters of an ip
proc ::xilinx::dsptool::getParamValues {ipobj} {
    upvar $ipobj ip

    set paramList [$ip listParameters]
    $paramList GetIterator paramIter
    $paramIter First
    
    set paramValueList [list]

    while { [$paramIter IsEnd] != 1 } {
	set paramName [$paramIter CurrentItem]

        set currentValue [$ip getParameterProperty $paramName "value"]

        set nameValuePair [list [string tolower $paramName] $currentValue]

        lappend paramValueList $nameValuePair

        $paramIter Next
    }


    return $paramValueList
}

##
# If a set of XCO params has been passed, first tries to set
# param-properties, which might affect the top-level interface
# of the core and might change the generics.
# Then queries Coregen to get list of names for the generics
# of the core and returns name-value info for generics in form of a table
#
proc ::xilinx::dsptool::getGenerics_AT { ipobj {params {}} } {    

    upvar $ipobj ip
    set error ""
    if {[llength $params] == 1} {
        set one_pair [lindex $params 0]
        set error [$ip setParameterProperty [lindex $one_pair 0] "value" [lindex $one_pair 1]]
    } elseif {[llength $params] > 1} {
        set error [setParameterValues ip $params]
    }

    if { $error != ""} {
        return [String2AnyString $error]
    }
            
    set genericList [$ip listGenerics]
    $genericList GetIterator genericIter
    $genericIter First
    set genericValueList [list]
    DictBegin genericSet
    while { [$genericIter IsEnd] != 1 } {      
        set genericName [$genericIter CurrentItem]                       
        set currentValue [$ip getGenericProperty $genericName "value"]

        DictBegin oneParam
        DictPutStr oneParam "value" $currentValue
        DictEnd oneParam                

        DictPut genericSet [string tolower $genericName] $oneParam

        $genericIter Next
    }    
    DictEnd genericSet    

    return $genericSet
}

#
# if param-list supplied, then first tries to set param-properties
# for that ip - in case of any error at this stage, returns.
# For the ipObj, executes a Coregen API for an ip and returns the
# result - if that the function has not been exported, then errors out
#
proc ::xilinx::dsptool::getFunctionResult_AT { ipKey functionName {params {}} {family ""} } {    
    
	set ip [createIP $ipKey $family]
	set error ""
    if {[llength $params] == 1} {
        set one_pair [lindex $params 0]
        set error [$ip setParameterProperty [lindex $one_pair 0] "value" [lindex $one_pair 1]]
    } elseif {[llength $params] > 1} {
        set error [setParameterValues ip $params]
    }
        
    if { $error != "" && [string tolower $error] != "ok" } {
        return [String2AnyString $error]
    }
    
    if { [$ip supportsFunction $functionName ] } {
	    set functionResult [$ip executeFunction $functionName]
	    return [String2AnyString $functionResult]
    }

	set error "ERROR : could not execute $functioName. Please verify this function has been exported."
	return [String2AnyString $error]
}


#proc ::xilinx::dsptool::getGenericValues_AT { ipKey } {
#    set ip [createIP $ipKey ""]

#    set genericValueList [getGenericValues ip]
#}



##
# sets properties for a set of XCO parameters supplied and 
# returns description(type, value, defaultvalue etc.) about
# all the parameters of the ip in form of a table
#
proc ::xilinx::dsptool::getParamDes_AT {ipKey {params {}} } {
    set ip [createIP $ipKey ""]

    if {[llength $params] == 1} {
        set one_pair [lindex $params 0]
        set error [$ip setParameterProperty [lindex $one_pair 0] "value" [lindex $one_pair 1]]
    } elseif {[llength $params] > 1} {
        set error [setParameterValues ip $params]
    }
    
    set paramSet [constructParamAT ip]

    return $paramSet
}

##
# sets a group of XCO parameters for an ip and updates the value
# of a particular param, as specified through "updated_param";
# finally returns the parameter-description for the ip in form of a table
#
proc ::xilinx::dsptool::updateOneParam_AT {ipKey params updated_param} {
    set ip [createIP $ipKey ""]

    set error [setParameterValues ip $params]
    
    if {$error == ""} {
        # puts "Old params are OK"
    }
    set error [$ip setParameterProperty [lindex $updated_param 0] "value" [lindex $updated_param 1]]
    if {$error == ""} {
        # puts "new param is OK"
    }

    set paramSet [constructParamAT ip]

    return $paramSet
}

##
# for an ipKey, creates the ipObj and sets param-values, as passed
# through "params" - returns param and metaparam info in form of a
# table, for callback
#
proc ::xilinx::dsptool::setParamsInOrderGetParams_AT {ipKey params {family ""} {contact_cache true} {metaParams ""}} {

    variable verbosity

    if {$verbosity} {
        puts "setParamsInOrderGetParams_AT contact_cache passed: $contact_cache"
    }

    variable ipmodel_cache_params
    variable ipmodel_cache_use_cache
    variable safeParameterPassing
    variable passParameterDifferences

    set ip [createIP $ipKey $family $contact_cache]

    set enter_click [clock clicks -milliseconds]

    set params2 $params

    # if ipobj from ipmodel_cache is to be reused, then get
    # diff between cached param and params, passed and update
    # params only where it differs
    if { false && $contact_cache && $ipmodel_cache_use_cache } {
        set param_diff [findOnlyDiffParam $ipmodel_cache_params $params]
        if { $param_diff != 0 && [llength $param_diff] <= 10 } {
            set params2 $param_diff
            if {$verbosity} {
                puts "Reusing params: $params2"
            }
        } else {
            # puts $ipmodel_cache_params
            # puts $params
        }
    }

    set after_hash_click [clock clicks -milliseconds]

    set forcedTable() false

    if { $safeParameterPassing } {
        set error [setParameterValues ip $params]
        if { $error != "" && [string tolower $error] != "ok"} {
            return [String2AnyString $error]
        }
    } else { 
        foreach pair $params2 {
            set paramName [lindex $pair 0]
            set paramValue [lindex $pair 1]

            set currentValue [$ip getParameterProperty $paramName "value"]
            set paramEnabled [$ip getParameterProperty $paramName "enabled"]
            set paramType [$ip getParameterProperty $paramName "type"]

            set setParam 1
            if  { $passParameterDifferences } {
                set setParam [expr {$currentValue != $paramValue}]
            }

            if { $paramEnabled } {
                if {$paramType == "BOOL"} {
                    if {$paramValue} {
                        set paramValue true
                    } else {
                        set paramValue false
                    }
                }
                # puts "setting $paramName $paramValue"
                # in case of any error while setting paramvalue, update that this
                # parameter's value has been forced
                if { $setParam } {
                    set error [$ip setParameterProperty $paramName "value" $paramValue]

                    if {$error != "" && [string tolower $error] != "ok"} {
                        set forcedTable($paramName) true
                        # puts "got error $error, reverting $paramName to $currentValue"
                        # Make Sure to revert it back
                        set error [$ip setParameterProperty $paramName "value" $currentValue]
                        #if {$error != "" && [string tolower $error] != "ok"} {
                        #    # puts "error on reverting: $error"
                        #}
                    } else {
                        # set valueAfterSet [$ip getParameterProperty $paramName "value"]
                        # puts "value after set is $valueAfterSet, paramValue is $paramValue"
                        # if {$valueAfterSet != $paramValue} {
                        # set forcedTable($paramName) true
                        # puts "reverting $paramName to $currentValue"
                        # set error [$ip setParameterProperty $paramName "value" $currentValue]
                        #} 
                    }
                }
            } else {
                if { $setParam } {
                    #set error [$ip setParameterProperty $paramName "value" $currentValue]
                }
            }
        }
    }

    set after_setparam_click [clock clicks -milliseconds]
    if {$contact_cache} {
        set ipmodel_cache_params $params
    }

    set paramSet [constructParam4CallbackAT ip forcedTable $metaParams]

    set after_con_at_click [clock clicks -milliseconds]

    if {$verbosity} {
        puts "setParamsInOrderGetParams_AT: hashing clicks: [expr $after_hash_click-$enter_click], set param clicks: [expr $after_setparam_click-$after_hash_click], con_at clicks: [expr $after_con_at_click-$after_setparam_click]"
        puts "Elapsed time in setParamsInOrderGetParams_AT: [expr $after_con_at_click - $enter_click] ms"
    }

    return $paramSet
}

#
# Checks if the ip represented by ipKey supports AXI Streaming
# Currently we assume only Complex Multiplier v4.0 supports it.
# TODO: extend it to use the published API from coregen. Gather
# some kind of port property
#
#   @param ipKey - a unique string identifier for each class of IP
#
#   @return 1 if isAXICore else return 0
proc ::xilinx::dsptool::isAXIStreamingSupported { ipKey } {
    set isAXICore 0
    set componentID [::xilinx::RepositoryManager::GetComponentID $ipKey]
    set value [::xilinx::TGIHelper::getComponentAxiInterfaces $componentID]
    if { ( $value == "AXI4-Stream" || $value == "AXI_STREAMING" ) } {
        set isAXICore 1
    }
    return $isAXICore
    #variable ipmgr_if
    #set isAXICore 0
    #set val [$ipmgr_if getIPProperty $ipKey "interfaces"]
    #if { ( $val == "AXI_STREAMING" ) } {
        #set isAXICore 1
    #}
    #return $isAXICore    
}

proc tdata_info {} {
    # for now, interpret 'binarypoint' as 'tdata_info'
    # after Coregen's support, use 'tdata_info' or whatever other
    # attribute name they provide
    return "subfields"
}

proc tdataInfoExists { ip portName } {
    upvar $ip ipObj
    set tdataInfo [$ipObj getPortProperty $portName [tdata_info]]
    if { $tdataInfo != "" } {
        if  { [ catch { llength $tdataInfo } ] } {
            return false
        } else {
            if { [llength $tdataInfo] <= 1 } {
                return false
            } else {
                return true
            }
        }
    } else {
        return false
    }
}

##
# if paramlist passed, first tries to set param-values for that ipobj;
# in case of any error, returns error-string - the paramvalues might
# impact port-data (for e.g. width etc.). 
# Then based on name of ports for that core, builds port-data records (name,
# type, direction, width, latency etc.) for that ipobj, and returns in an 
# anytable sequence
#
proc ::xilinx::dsptool::getPortList_AT {ipKey params {family ""}} {

    variable safeParameterPassing

    # find out if the ip supports AXI
    set isAXICore [isAXIStreamingSupported $ipKey]

    # todo need try catch
    set ip [createIP $ipKey $family]

    if { $params == "" || $safeParameterPassing } {
        set error [setParameterValues ip $params]
    } else {
        set error [setParameterValuesInOrder ip $params]
        # set error [setParameterValues ip $params]
    }
    if { $error != ""} {
        return [String2AnyString $error]
    }
    
    #Get latency Data - This latency data is wrt to a block 
    #context. ie either 1 which means there is some latency from
    #all inout ports to all output ports
    #0 implying there is atleat one combinational path
    set has_combinational 0 
    if { [$ip supportsFunction has_combinational ] } {
	    set has_combinational [$ip executeFunction has_combinational]
    }
    set block_latency [expr 1-$has_combinational]
    
    set portList [$ip listPorts]
    $portList GetIterator portNameItr

    SeqBegin portListSeq
    $portNameItr First
    set index 0
    while {[$portNameItr IsEnd]!=1} {
        set portName [$portNameItr CurrentItem]
        if { ![$ip getPortProperty $portName "Enabled"] } {
            $portNameItr Next
            continue
        }
	    set portDirection [$ip getPortProperty $portName "direction" ]
	    set portType [$ip getPortProperty $portName "type" ]
	    if {$portType == "BUS"} {
            set left [$ip getPortProperty $portName "leftindex" ]
            set right [$ip getPortProperty $portName "rightindex" ]
            set portWidth [expr $left-$right+1]
	    } else {
            set portWidth ""
	    }

        if {[string tolower $portName] == "clk"} {
            set kind "clk"
        } elseif {[string tolower $portName] == "aclk"} {
            set kind "clk"
        } elseif {[string tolower $portName] == "ce"} {
            set kind "ce"
        } elseif {[string tolower $portName] == "aclken"} {
            set kind "ce"
        } else {
            set kind ""
        }
     
        if { $isAXICore == 1 } {
            if { [tdataInfoExists ip $portName] } {
                set tdataInfo [$ip getPortProperty $portName [tdata_info]]
                set onePortInfo [buildOnePortAT $index $portName $portDirection $portType $portWidth $kind "" "" $block_latency "" $tdataInfo]
            } else {
                set onePortInfo [buildOnePortAT $index $portName $portDirection $portType $portWidth $kind "" "" $block_latency]
            }
        } else {	
            set onePortInfo [buildOnePortAT $index $portName $portDirection $portType $portWidth $kind "" "" $block_latency]
        }
        SeqAppend portListSeq $onePortInfo

        incr index

        $portNameItr Next
    }
    SeqEnd portListSeq

    return $portListSeq
}

##
# given an ip object, constructs port data-record (for
# ports that are enabled) based on info from the core
# and returns port-info in an anytable sequence
#
proc ::xilinx::dsptool::constructPortListAT {ipobj {isAXICore 0}} {
    upvar $ipobj ip

    set portList [$ip listPorts]
    $portList GetIterator portNameItr

    SeqBegin portListSeq
    $portNameItr First
    set index 0
    while {[$portNameItr IsEnd]!=1} {
        set portName [$portNameItr CurrentItem]
        if { ![$ip getPortProperty $portName "Enabled"] } {
            $portNameItr Next
            continue
        }
	set portDirection [$ip getPortProperty $portName "direction" ]
	set portType [$ip getPortProperty $portName "type" ]
	if {$portType == "BUS"} {
            set left [$ip getPortProperty $portName "leftindex" ]
            set right [$ip getPortProperty $portName "rightindex" ]
            set portWidth [expr $left-$right+1]
	} else {
            set portWidth ""
	}

        # set sig_is [string tolower [$ip getPortProperty $portName "SIGIS"]]
        # set kind $sig_is
        
#         set isClock [$ip getPortProperty $portName "isClock"]
#         set isReset [$ip getPortProperty $portName "isReset"]
          set isClockEnable [$ip getPortProperty $portName "isclockenable"]

          set isReset [$ip getPortProperty $portName "isreset"]

#         if {$isClock != "" && $isClock} {
#             set kind "clk"
#         } elseif {$isReset} {
#             set kind "rst"
#         } elseif {$isClockEnable} {
#             set kind "ce"
#         }

        if {[string tolower $portName] == "clk"} {
            set kind "clk"
        } elseif {[string tolower $portName] == "aclk"} {
            set kind "clk"
        } elseif {[string tolower $portName] == "ce"} {
            set kind "ce"
        } elseif {[string tolower $portName] == "aclken"} {
            set kind "ce"
        } elseif {$isClockEnable == 1.0 || $isClockEnable == true} {
             set kind "ce"
        } elseif {$isReset == 1.0 || $isReset == true} {
             set kind "rst"
        } else {
            set kind ""
        }
        
        set portArith [$ip getPortProperty $portName "arithmetictype"]
        set portBinpt [$ip getPortProperty $portName "binarypoint"]
        set portRate [$ip getPortProperty $portName "rate"]
        set portLatency [$ip getPortProperty $portName "latency"]

        #for now interpret binary point as tdata_info
        if { $isAXICore == 1 } {
            if { [tdataInfoExists ip $portName] } {
                set tdataInfo [$ip getPortProperty $portName [tdata_info]]
                set onePortInfo [buildOnePortAT $index $portName $portDirection $portType $portWidth $kind $portArith $portBinpt $portLatency $portRate $tdataInfo]
            } else {
                set onePortInfo [buildOnePortAT $index $portName $portDirection $portType $portWidth $kind $portArith $portBinpt $portLatency $portRate]
            }
        } else {
            set onePortInfo [buildOnePortAT $index $portName $portDirection $portType $portWidth $kind $portArith $portBinpt $portLatency $portRate]
        }
        SeqAppend portListSeq $onePortInfo

        incr index

        $portNameItr Next
    }
    SeqEnd portListSeq

    return $portListSeq
}

# updates properties for a set of ports for an ipObj
proc ::xilinx::dsptool::updatePort {ipobj ports {isAXICore 0}} {
    upvar $ipobj ip

    variable verbosity

    set enter_clicks [clock clicks -milliseconds]

    # puts $ports
    foreach port $ports {
        # puts $port
        set name "";
        set arith ""
        set nbits 0
        set binpt 0
        set latency -1
        set rate -1
        set tdataInfo ""	

        if { [llength $port] >= 1} {
            # for AXI cores, use smaller-case for name, as per convention in Coregen
            if { $isAXICore } {
                set name [lindex $port 0]
            } else {
                set name [string toupper [lindex $port 0]]
            }
        }
        if { [llength $port] >= 2} {
            set arith [lindex $port 1]
        }
        if { [llength $port] >= 3} {
            set nbits [lindex $port 2]
        }
        if { [llength $port] >= 4} {
            set binpt [lindex $port 3]
        }

        if { [llength $port] >= 5} {
            set latency [lindex $port 4]
        }

        if { [llength $port] >= 6} {
            set rate [lindex $port 5]
        }

        if { [llength $port] >= 7} {
            #TODO : anindit uncomment it later and delete the line after that
            set tdataInfo [lindex $port 6]
            #set binpt [lindex $port 6]
        }

        # puts "$name $arith $nbits $binpt"

        if { $arith != "" } {
            # puts "setting portProperty $name arithmetictype $arith"
            set status [$ip setPortProperty $name "arithmetictype" $arith]
            # puts "set arithmetictype status $status"
        }
        
        set status [$ip setPortProperty $name "leftindex" [expr $nbits-1]]
        # puts "set leftindex status $status"
        set status [$ip setPortProperty $name "rightindex" 0]
        # puts "set rightindex status $status"
        set status [$ip setPortProperty $name "binarypoint" $binpt]
        # puts "set binarypoint status $status"
        set status [$ip setPortProperty $name "latency" $latency]
        # puts "set latency status $latency"
        set status [$ip setPortProperty $name "rate" $rate]
        # puts "set rate status $rate"
        #TODO : anindit uncomment it later
        set status [$ip setPortProperty $name [tdata_info] $tdataInfo]
    }

    set finish_clicks [clock clicks -milliseconds]

    if {$verbosity} {
        puts "Elapsed time in updatePort: [expr $finish_clicks-$enter_clicks] ms"
    }    
}

##
# if a set of param is passed, first updates properties for those
# params, for the ipObj(created using the ipKey). If the core
# does not have any uUPP function, errors out, else updates properties
# for the set of ports, that has been supplied.
# Based on "ret_kind", returns port-info/param-info/both etc.
#
proc ::xilinx::dsptool::updatePort_AT {ipKey family params ports ret_kind } {
    variable verbosity
    variable safeParameterPassing

    # find out if the ip supports AXI
    set isAXICore [isAXIStreamingSupported $ipKey]

    # todo need try catch
    set ip [createIP $ipKey $family]

    if { [llength $params] == 0 || $safeParameterPassing } {
        set error [setParameterValues ip $params]
    } else {
        set forceSetParam false
        if { $ipKey == "xilinx.com:ip:fir_compiler:6.3" && $ret_kind == "generics" } {
            set forceSetParam true
        }
        set error [setParameterValuesInOrder ip $params $forceSetParam]
    }

    if { $error != ""} {
        return [String2AnyString $error]
    }

    set hasUUPP [$ip supportsUpdateUsingPortProperties]
    if {!$hasUUPP} {
        set error "The block does not have DSP Tool function updateUsingPortProperties"
        return [String2AnyString $error]
    }

    updatePort ip $ports $isAXICore

    set before_updateUsingPortProperties [clock clicks -milliseconds]
    set update_status  [$ip updateUsingPortProperties]
    set after_updateUsingPortProperties [clock clicks -milliseconds]

    if {$verbosity} {
        puts "Elapsed time in updateUsingPortProperties: [expr $after_updateUsingPortProperties-$before_updateUsingPortProperties] ms"
    }    

    if { $update_status != "" && $update_status != true && [string tolower $update_status] != "ok" } {
        set ret [::xilinx::dsptool::removeFromCache $ipKey $family]
        if { $ret != "OK" } {
            set update_status "$ret : $update_status"
        }
        set error_at [String2AnyString $update_status]
        return $error_at
    }

    if { [llength $ret_kind] <= 1 } {
        if {$ret_kind == "ports"} {
            set portListSeq [constructPortListAT ip $isAXICore]
            return $portListSeq
        } elseif {$ret_kind == "params"} {
            set paramDes [constructParamAT ip true]
            return $paramDes
        } else {
            set generics [getGenerics_AT ip]
            return $generics
        }
    } else {
        set retList [list]
        foreach kind $ret_kind {
            if {$kind == "ports"} {
                set portListSeq [constructPortListAT ip $isAXICore]
                lappend retList $portListSeq
            } elseif {$kind == "params"} {
                set paramDes [constructParamAT ip true]
                lappend retList $paramDes
            } else {
                set generics [getGenerics_AT ip]
                lappend retList $generics            
            }
        }
        return $retList
    }
}

# creates one port data record, based on the various properties of the port, supplied
proc ::xilinx::dsptool::buildOnePortAT { index name direction type width {kind ""} {arith ""} {binpt ""} {latency ""} {rate ""} {tdataInfo ""}} {
    DictBegin port
    DictPut port "index" $index
    DictPutStr port "name" [string tolower $name]
    DictPutStr port "direction" [string tolower $direction]
    DictPutStr port "type" [string tolower $type]
    if {$width != ""} {
        DictPut port "width" $width
    }

    if {$kind != ""} {
        DictPutStr port "kind" [string tolower $kind]
    }

    if {$arith != ""} {
        DictPutStr port "arith" $arith
    }

    if {$binpt != ""} {
        if { $tdataInfo != "" } {
            DictPut port "binpt" 0 
        } else {
            DictPut port "binpt" $binpt
        }
    }

    if {$latency != ""} {
        DictPut port "latency" $latency
    }

    if {$rate != ""} {
        DictPut port "period" $rate
    }
    
    # for axi tdata_port, put tdata_info
    if { $tdataInfo != "" } {
        tdataInfoListToAnyTable port tdataInfo
    }

    DictEnd port

    return $port
}

##
# Packs the tdataInfo in the form of tcl list of lists into an AnyTable
# dictionary so that it can be processed across language boundaries.
# We mimic pass by reference here and parameters passed here are simply
# names of the variables in a procedure stack frame one level above. We
# are doing this for performance, but passing by value is okay too.
#
#   @param name of the variable in which we want to return the dictionary,
#          corresponds to the dictionary-data for an AXI tdata-port
#   @param tdataInfo in form of list of lists, that is to be packed
#
proc tdataInfoListToAnyTable { tdataInfoDictName tdataInfoName } {
    upvar $tdataInfoName tdataInfo
    upvar $tdataInfoDictName axiPort 
    set tDataInfoLen [llength $tdataInfo]
        if { [isTdataInfoValid $tdataInfo] == 1 } {
            ::xilinx::anytablestring::DictBegin tdataRec
            set axiClassName [lindex $tdataInfo 0]
            set dataClassName [lindex $tdataInfo 1]

            ::xilinx::anytablestring::DictPutStr tdataRec "axi_class_name"  [string tolower $axiClassName]
            ::xilinx::anytablestring::DictPutStr tdataRec "data_class_name" [string tolower $dataClassName]

            if {$tDataInfoLen >= 3 } {
                ::xilinx::anytablestring::DictPut tdataRec "interfaceWidth" [lindex $tdataInfo 2]
            }

            if {$tDataInfoLen >= 4 } {
                set subFieldInfoList [lindex $tdataInfo 3]
                set totalsubfieldwidth 0
                set subFieldDataRec [::xilinx::dsptool::getTdataSubFieldInfoAT $subFieldInfoList totalsubfieldwidth]
                ::xilinx::anytablestring::DictPut tdataRec "subFieldInfoList" $subFieldDataRec
                ::xilinx::anytablestring::DictPut tdataRec "interfaceActualWidth" $totalsubfieldwidth
            }

            ::xilinx::anytablestring::DictEnd tdataRec

            ::xilinx::anytablestring::DictPut axiPort "tdata_info" $tdataRec
        }

}

proc isTdataInfoValid { tdataInfo } {
    if { $tdataInfo != "" } {
        if { [string is double $tdataInfo] } {
            return 0
        }
        return 1
    }
    return 0
}
##
# creates Anytable Dictionary for subFieldInfoList - recursive procedure,
# for any subfield in the list, may in turn contain subfields
#     @param  subFieldInfoList  list of subfields, actually it may be list
#                               of lists, as the each subFieldInfo, can contain
#                               list of info about subfields of its own
#
#     @returns  AnyTable Dictionary corresponding to various
#               subfield info present in the list
# TODO: An example fo how the tdataInfo list of lists is laid out
# would be very instructive. That information is hardcoded in the function
# below but it would be good to get some visual on how it is laid out
proc ::xilinx::dsptool::getTdataSubFieldInfoAT { subFieldInfoList width } {
    upvar $width totalsubfieldwidth
    SeqBegin subFieldList
    foreach subFieldInfo $subFieldInfoList {

        DictBegin subFieldDataRec

        set subFieldName [lindex $subFieldInfo 0]
        set subFieldType [lindex $subFieldInfo 1]
        set subFieldWidth [lindex $subFieldInfo 2]
        set subFieldActualWidth [lindex $subFieldInfo 3]
        set arithmeticType [lindex $subFieldInfo 4]
        set binaryPoint [lindex $subFieldInfo 5]

        set subFieldName [string tolower $subFieldName]
        DictPutStr subFieldDataRec "subFieldName" $subFieldName
        DictPutStr subFieldDataRec "subFieldType" [string tolower $subFieldType]
        DictPut subFieldDataRec "subFieldWidth" $subFieldWidth
        DictPut subFieldDataRec "subFieldActualWidth" $subFieldActualWidth
        DictPutStr subFieldDataRec "arithmeticType" [string tolower $arithmeticType]
        DictPut subFieldDataRec "binaryPoint" $binaryPoint

        set totalsubfieldwidth [expr $totalsubfieldwidth + $subFieldActualWidth]

        if { [llength $subFieldInfo] >= 7 } {
            set period [lindex $subFieldInfo 6]
            DictPut subFieldDataRec "period" $period
        }

        if { [llength $subFieldInfo] >= 8 } {
            set addnlSubFieldData [lindex $subFieldInfo 7]
            set totalwidth 0
            set addnlSubFieldDataRec [getTdataSubFieldInfoAT $addnlSubFieldData totalwidth]
            set totalsubfieldwidth [expr $totalsubfieldwidth + $totalwidth]
            DictPut subFieldDataRec "subFieldInfoList" $addnlSubFieldDataRec
        }

        DictEnd subFieldDataRec

        SeqAppend subFieldList $subFieldDataRec

    }

    SeqEnd subFieldList

    return $subFieldList
}

##
# utility proc - consider deleting
# creates a tcl list (of lists) for subFieldInfoList - recursive procedure,
# for any subfield in the list, may in turn contain subfields
#     @param  subFieldInfoList  list of subfields, actually it may be list
#                               of lists, as the each subFieldInfo, can contain
#                               list of info about subfields of its own
#
#     @returns  tcl list (of lists) corresponding to various subfield info
proc ::xilinx::dsptool::buildAXItdataSubFieldInfo { subFieldInfoList } {
    set subFieldList [list]
    foreach subFieldInfo $subFieldInfoList {

        set subFieldData [list]

        set subFieldName [lindex $subFieldInfo 0]
        set subFieldType [lindex $subFieldInfo 1]
        set subFieldWidth [lindex $subFieldInfo 2]
        set subFieldActualWidth [lindex $subFieldInfo 3]
        set arithmeticType [lindex $subFieldInfo 4]
        set binaryPoint [lindex $subFieldInfo 5]

        lappend subFieldData [string tolower $subFieldName]
        lappend subFieldData [string tolower $subFieldType]
        lappend subFieldData $subFieldWidth
        lappend subFieldData $subFieldActualWidth
        lappend subFieldData [string tolower $arithmeticType]
        lappend subFieldData $binaryPoint

        if { [llength $subFieldInfo] >= 7 } {
            set period [lindex $subFieldInfo 6]
            lappend subFieldData $period
        }

        if { [llength $subFieldInfo] >= 8 } {
            set addnlSubFieldData [lindex $subFieldInfo 7]
            set addnlSubFieldDataRec [buildAXItdataSubFieldInfo $addnlSubFieldData]
            lappend subFieldData $addnlSubFieldDataRec
        }

        lappend subFieldList $subFieldData

    }

    return $subFieldList
}

##
# utility proc - consider deleting
# creates one tcl list for tdata_info, based on the various arguments supplied
#
#    @param   axi_class_name     one of {Streaming, MemoryMapped, None}
#    @param   data_class_name    one of {Real, Complex, SingleFloat, DoubleFloat, Vector, Parallel, Composite}
#    @param   interface_width    total width of TDATA signal along with padding, if unknown, can be Null
#    @param   subfield_info_list from MSB to LSB of the interface information(in list of lists) of the TDATA subfields
#
#    @returns  tcl list for tdata_info
proc ::xilinx::dsptool::buildOneAXItdataInfo { axi_class_name data_class_name interface_width subFieldInfoList } {
    set tdataInfo [list]
    lappend tdataInfo [string tolower $axi_class_name]
    lappend tdataInfo [string tolower $data_class_name]

    if {$interface_width != ""} {
        lappend tdataInfo $interface_width
    }

    if {$subFieldInfoList != ""} {
        if { [llength $subFieldInfoList] > 0 } {
            set subFieldDataRec [buildAXItdataSubFieldInfo $subFieldInfoList]
            lappend tdataInfo $subFieldDataRec
        }
    }

    return $tdataInfo
}

##
# utility proc - consider deleting
# creates one tcl list(of lists) for tdata_subfield_info, based on the various arguments supplied
#
#    @param   subFieldName          name of the subfield
#    @param   subFieldType          one of "Atomic" or "Composite"
#    @param   subFieldWidth         bit positions occupied by the subfield {MSBBit LSBBit}
#    @param   subFieldActualWidth   bit positions occupied by nonpadded data
#    @param   arithmeticType        one of "bool", "unsigned" or "signed"
#    @param   binaryPoint           sysgen binary point location
#    @param   period                subField period information
#    @param   subfield_info_list    from MSB to LSB of the interface information(in list of lists) of the subfields; if
#                                   the parent subfield is of type "Atomic", it won't have at the end any additional subfield info
#
#    @returns tcl list(of lists) representing the subfieldInfo
proc ::xilinx::dsptool::buildOneAXItdataSubFieldInfo { subFieldName subFieldType subFieldWidth subFieldActualWidth arithmeticType binaryPoint { period "" } { subFieldInfoList "" } } {
    set subFieldInfo [list]
    lappend subFieldInfo  [string tolower $subFieldName]
    lappend subFieldInfo [string tolower $subFieldType]
    lappend subFieldInfo  $subFieldWidth
    lappend subFieldInfo $subFieldActualWidth
    lappend subFieldInfo [string tolower $arithmeticType]
    lappend subFieldInfo $binaryPoint

    if {$period != ""} {
        lappend subFieldInfo $period
    }

    if {$subFieldInfoList != ""} {
        if { [llength $subFieldInfoList] > 0 } {
            set subFieldDataRec [buildAXItdataSubFieldInfo $subFieldInfoList]
            lappend subFieldInfo $subFieldDataRec
        }
    }

    return $subFieldInfo
}

proc ::xilinx::dsptool::indent_space {n} {
    set n [expr $n*2]
    set c " "
    set ret ""
    for {set i 0} {$i < $n} {incr i} {
        append ret $c
    }
    return $ret

}

proc ::xilinx::dsptool::xml_editbox { name type label default_value evaluate {indent 0} } {
    if {$type == ""} {
        set xml_ctype ""
    } else {
        set xml_ctype "ctype=\"$type\""
    }
    set xml_text "[indent_space $indent]<editbox name=\"$name\" default=\"$default_value\" top_label=\"false\" evaluate=\"$evaluate\" label=\"$label\" $xml_ctype/>\n"

    return $xml_text
}

proc ::xilinx::dsptool::xml_listbox { name label items default_value {indent 0} {style auto}} {
    if {$style == "auto"} {
        if { [llength $items] > 3 } {
            set style listbox
        } else {
            set style radiogroup
        }
    }

    if { $style != "listbox" && $style != "radiogroup" } {
        error "invalid style passed for $name, supported values are auto listbox radiogroup"
    }
    set xml_start "[indent_space $indent]<$style name=\"$name\" default=\"$default_value\" top_label=\"false\" evaluate=\"false\" label=\"$label\" ctype=\"String\" allow_advanced=\"false\">\n"

    set items_str ""
    incr indent
    foreach item $items {
        set item_str "[indent_space $indent]<item value=\"$item\"/>\n"
        set items_str "$items_str$item_str"
    }
    incr indent -1

    set xml_end "[indent_space $indent]</$style>\n"

    set xml_str "$xml_start$items_str$xml_end"

    return $xml_str

    return $xml_text
}

# create a xml text for GUI checkbox
proc ::xilinx::dsptool::xml_checkbox { name label default_value {indent 0}} {
    if {$default_value} {
        set sysgen_default "on"
    } else {
        set sysgen_default "off"
    }

    set xml_text "[indent_space $indent]<checkbox name=\"$name\" default=\"$sysgen_default\" label=\"$label\" ctype=\"Bool\" allow_advanced=\"false\"/>\n"

    return $xml_text
}

# paramOrder is a list, each element is another list of tab name and parameters
# e.g. [list [list "Basic" [list "p1" "p2"]]]
proc ::xilinx::dsptool::toXmlCoregenTab {ipKey {paramOrder {}} {indent 0}} {
    set ip [createIP $ipKey ""]
    set paramList [$ip listParameters]
    $paramList GetIterator paramIter
    $paramIter First
    
    
    set xml_text_dict() ""    
    set group_map() ""
    set tab_map() ""
    set coregen_param_order [list]  
    incr indent
    while { [$paramIter IsEnd] != 1 } {
        set paramName [string tolower [$paramIter CurrentItem] ]

        if {$paramName != "component_name"} {

            lappend coregen_param_order $paramName

            set defaultValue [$ip getParameterProperty $paramName "defaultvalue"]
            set paramType [$ip getParameterProperty $paramName "type"]

            set paramInfo [getParamInfo $ipKey $paramName $paramType]
            set paramTab [lindex $paramInfo 0]
            set paramLabel [lindex $paramInfo 1]
            set paramGroup [lindex $paramInfo 2]
            set paramRank  [lindex $paramInfo 3]
            
            #Set up helper associative arrays
            set tab_map($paramName) $paramTab
            set group_map($paramName) $paramGroup



            #Collect the visualization/Representation information
            #Collect Tab data
            if { [array exists param_map_tab] == 0 || [lsearch -exact [array names param_map_tab] $paramTab] == -1 } {
               set param_map_tab($paramTab) [list]                            
            }            
            set paramList $param_map_tab($paramTab)
            lappend paramList $paramName
            set param_map_tab($paramTab) $paramList

            #Collect Rank data
            if { [array exists param_map_rank] == 0 || [lsearch -exact [array names param_map_rank] $paramRank] == -1 } {
               set param_map_rank($paramRank) [list]
            }
            set rankedParamList $param_map_rank($paramRank)
            lappend rankedParamList $paramName
            set param_map_rank($paramRank) $rankedParamList
            
            #Collect Group Data
            if { [array exists param_map_group] == 0 || [lsearch -exact [array names param_map_group] $paramGroup] == -1 } {
               set param_map_group($paramGroup) [list]
            }
            set groupedParamList $param_map_group($paramGroup)
            lappend groupedParamList $paramName
            set param_map_group($paramGroup) $groupedParamList


            if { $paramLabel == "" } {
                set paramDescription [$ip getParameterProperty $paramName "description"]
            } else {
                set paramDescription $paramLabel
            }
            set paramTooltip [$ip getParameterProperty $paramName "tooltip"]
            
            set paramType "[string toupper [string index $paramType 0]][string tolower [string range $paramType 1 end]]"
            
            if { $paramDescription != "" } {
                set paramLabel $paramDescription
            } else {
                if { [string length $paramName] <= 5 && $paramType == "Bool" && [regexp _ $paramName] == 0 } {
                    set paramLabel [string toupper $paramName]
                } else {
                    set name_w_space [regsub -all _ $paramName " "]
                    set paramLabel "[string toupper [string index $name_w_space 0]][string range $name_w_space 1 end]"
                }

                if { $paramTooltip != "" } {
                    append paramLabel " ($paramTooltip)"
                }
            }

            regsub -all "\\\"" $paramLabel "'" paramLabel
            
            if {$paramType == "Int" || $paramType == "Double" || $paramType == "String" || $paramType == "Bin" || $paramType == "File" } {
                set evaluate true
                if {$paramType == "String" || $paramType == "Bin" || $paramType == "File" } {
                    if {$paramType == "File"} {
                        set evaluate false
                    }

                    set paramType "String"
                    if { $defaultValue != "" && $evaluate } {
                        set defaultValue "'$defaultValue'"
                    }
                }
                set xml_one [xilinx::dsptool::xml_editbox $paramName $paramType $paramLabel $defaultValue $evaluate $indent]
            } elseif {$paramType == "List" } {
                set items [$ip getParameterProperty $paramName "list"] 
                set xml_one [xilinx::dsptool::xml_listbox $paramName $paramLabel $items $defaultValue $indent]
            } elseif {$paramType == "Bool" } {
                set xml_one [xilinx::dsptool::xml_checkbox $paramName $paramLabel $defaultValue $indent]
            } else {
                set xml_one ""
            } 
            
            set xml_text_dict($paramName) $xml_one
        }

        $paramIter Next
    }

    if { [llength $paramOrder] > 0 } {
        set paramName "en"
        set xml_en [xilinx::dsptool::xml_checkbox $paramName "EN" false $indent]
        set xml_text_dict($paramName) $xml_en
        lappend coregen_param_order $paramName
        
        set paramName "rst"
        set xml_rst [xilinx::dsptool::xml_checkbox $paramName "RST" false $indent]
        set xml_text_dict($paramName) $xml_rst
        lappend coregen_param_order $paramName
    } 

    set paramName "fpga_area_estimation"
    set xml_use_fpga_area [xilinx::dsptool::xml_checkbox "xl_use_area" "Define FPGA area for resource estimation" false [expr $indent+1]]
    set xml_fpga_area [xilinx::dsptool::xml_editbox "xl_area" "" "FPGA area \[slices, FFs, BRAMs, LUTs, IOBs, emb. mults, TBUFs\]" "\[0,0,0,0,0,0,0\]" true [expr $indent+1]]
    set xml_fpga_area_estimation [xilinx::dsptool::xml_etch "FPGA Area Estimation" "$xml_use_fpga_area$xml_fpga_area" $indent]
    set xml_text_dict($paramName) $xml_fpga_area_estimation

    set implementationOrder [list]

    if { [llength $paramOrder] > 0 } {
        lappend coregen_param_order $paramName
    } else {
        lappend implementationOrder $paramName
    }

    set hasPortConfig false
    if { $hasPortConfig } {
        # let's disable first
        # some how the label won't show up
        set paramName "port_config"
        set xml_port_config [xilinx::dsptool::xml_editbox "port_config" "Struct" "Port Configuration" "struct()" true $indent]
        set xml_text_dict($paramName) $xml_port_config
        lappend coregen_param_order $paramName
    }

    incr indent -1
    
    if { [llength $paramOrder] == 0 } {
        set paramOrder {}
        foreach tab [array names param_map_tab] {
                lappend paramOrder [list "<tab>" "$tab" $param_map_tab($tab)]
        }             
        lappend paramOrder [list "<tab>" "Implementation" $implementationOrder]
    }

    #Compress the list
    set grouped_param_order {}
    set group_param_list_map() ""
    foreach rank [lsort -dictionary [array names param_map_rank]] {
        set paramList $param_map_rank($rank)
        foreach paramName $paramList {   
            set group $group_map($paramName)
            set tab $tab_map($paramName)

            if { $group == "" } {#Top level param
                lappend grouped_param_order $paramName
            } else {
                set groupKey "<::group::>${group}"
                if { [lsearch -exact [array names group_param_list_map] $groupKey] == -1 } {
                    lappend grouped_param_order $groupKey
                    set tab_map($groupKey) $tab
                    set group_param_list_map($groupKey) [list]
                }
                set gParamList $group_param_list_map($groupKey)
                lappend gParamList $paramName        
                set group_param_list_map($groupKey) $gParamList
            }            
        }
    }                 
    
    set newParamOrder {}
    set visitedTabs() ""
    set gParamList {}
    set prevTab ""
    set groupKey "<::group::>Input Options"
    foreach paramName $grouped_param_order {
        set tab $tab_map($paramName)
        if { $prevTab != "" && $prevTab != $tab } {
            lappend newParamOrder [list "<tab>" "$prevTab" $gParamList]
            set gParamList {}            
        }
        #if a grouped param
        if { [lsearch -exact [array names group_param_list_map] $paramName] != -1 } {
            lappend gParamList [list "<etch>" [string map {<::group::> ""} $paramName] $group_param_list_map($paramName)]
        } else {
            lappend gParamList $paramName
        }           
        set prevTab $tab
    }
    lappend newParamOrder [list "<tab>" "$tab" $gParamList]
    set paramOrder $newParamOrder
    #error "$newParamOrder"

    set xml_text ""

    foreach tab $paramOrder {
        set tabLabel [lindex $tab 1]
        set params [lindex $tab 2]

        # puts "tab: $tabLabel, params: $params"

        regsub -all "\\s" $tabLabel "_" tabName
        set underscore_tab "_tab"
        set tabName "$tabName$underscore_tab"
        
        set tab_begin [toXmlTabBegin $tabName $tabLabel $indent]

        set tab_content ""
        foreach paramName $params {
            # todo: need to change this to a decendent recursive calls
            # but by now, let's just have a patch
            if { [llength $paramName] > 1 } {
                set local_tag [lindex $paramName 0]
                if { $local_tag == "<etch>" } {
                    set etch_label [lindex $paramName 1]
                    set params_in_etch [lindex $paramName 2]
                    set etch_content ""
                    foreach paramName2 $params_in_etch {
                        if { [info exists xml_text_dict($paramName2)] } {
                            set xml_one $xml_text_dict($paramName2)
                            if {$xml_one != ""} {
                                set xml_one [insert_indent $xml_one 1]
                                append etch_content "$xml_one"
                            }
                        }
                    }
                    set etch_xml [xilinx::dsptool::xml_etch $etch_label $etch_content [expr $indent+1]]
                    append tab_content $etch_xml

                } else {
                    error "Unrecognized tag $local_tag"
                }
            } elseif { [info exists xml_text_dict($paramName)] } {
                set xml_one $xml_text_dict($paramName)
                if {$xml_one != ""} {
                    append tab_content $xml_one
                }
            }
        }
        set tab_end [toXmlTabEnd $indent]

        append xml_text "$tab_begin$tab_content$tab_end"
    }

    return $xml_text
}

proc ::xilinx::dsptool::insert_indent {str indent} {
    set lines [split $str "\n"]
    set lines2 ""
    foreach line $lines {
        if { $line != "" } {
            set line "[indent_space $indent]$line\n"
            append lines2 $line
        }
    }
    return $lines2
}

proc ::xilinx::dsptool::xml_etch {label content indent} {
    set etchbox_begin "[indent_space $indent]<etch label=\"$label\">\n"
    set etchbox_end "[indent_space $indent]</etch>\n"

    set etchbox_text "$etchbox_begin$content$etchbox_end"

    return $etchbox_text
}

proc ::xilinx::dsptool::blockxml_copyright {{indent 0}} {
    set crtext [list]
    lappend crtext "Copyright (c) 2005, Xilinx, Inc.  All Rights Reserved.          "
    lappend crtext "Reproduction or reuse, in any form, without the explicit written"
    lappend crtext "consent of Xilinx, Inc., is strictly prohibited.                "


    set text ""

    foreach linetext $crtext {
        set xml_one "[indent_space $indent]<!--   *  $linetext -->\n"
        append text $xml_one
    }
    return $text
}

proc ::xilinx::dsptool::toXmlSimulinkLibs {libraries indent} {
    set xml_lib_begin "[indent_space $indent]<libraries>\n"
    set xml_libs ""
    incr indent
    foreach lib $libraries {
        set xml_one_lib "[indent_space $indent]<library name=\"$lib\"/>\n"
        append xml_libs $xml_one_lib
    }
    incr indent -1
    set xml_lib_end "[indent_space $indent]</libraries>\n"

    
    set xml_libraries ""

    append xml_libraries $xml_lib_begin
    append xml_libraries $xml_libs
    append xml_libraries $xml_lib_end

    return $xml_libraries
}

proc ::xilinx::dsptool::toXmlTabEnd {indent} {
    set tab_end "[indent_space $indent]</tab>\n"
    return $tab_end
}

proc ::xilinx::dsptool::toXmlTabBegin {tabName tabLabel indent} {
    set tab_begin "[indent_space $indent]<tab name=\"$tabName\" label=\"$tabLabel\">\n"
    return $tab_begin
}

proc ::xilinx::dsptool::toBlockXml {ipname ipversion {dsptool_ready false} {block_description ""} {param_order {}} {icon_width ""} {icon_height ""} {icon_wmark_color ""} {icon_bg_color ""} {dll_name ""} {dll_entry_point ""} {handlers_enablement ""} {handlers_action ""} {libraries {}} {xmlfile ""}} {

    set indent 0

    set xml_sysgenblock ""

    append xml_sysgenblock [blockxml_copyright $indent]

    set ipkey [findIPKey $ipname $ipversion]
    if {$ipkey == ""} {
        error "Could not find $ipname $ipversion"
        return $xml_sysgenblock
    }

    if { $block_description == ""} {
        set block_description "$ipname $ipversion"
    }

    if {$icon_width == "" || $icon_width == 0} {
        set icon_width 95
    }

    if {$icon_height == "" || $icon_height == 0} {
        set icon_height 142
    }

    if {$icon_wmark_color == ""} {
        set icon_wmark_color "white"
    }

    if {$icon_bg_color == ""} {
        set ip_license [getIPLicense $ipkey]
        if { $ip_license == ""} {
            set icon_bg_color "blue"
        } else {
            set icon_bg_color "green"
        }
    }

    if {[llength $libraries] == 0} {
        set libraries [list "xbsIndex"]
    }

    # TODO: for now think we can go ahead with this - but feel free to change this, if needed in future.
    # Putting block_type in old Coregen style : i.e. not considering VLNV as the ipKey here.
    set oldIPKey [xilinx::dsptool::getOldFormatIPKey $ipkey]
    set xml_sysgenblock_begin "[indent_space $indent]<sysgenblock simulinkname=\"$ipname $ipversion \" block_type=\"$oldIPKey\">\n"

    incr indent

    set xml_blockhead [toXmlBlockHead $ipkey $icon_width $icon_height $icon_wmark_color $icon_bg_color $handlers_enablement $handlers_action $dll_name $dll_entry_point $libraries $indent]

    set xml_blockgui [toXmlBlockgui $ipname $ipversion $dsptool_ready $block_description $ipkey $param_order $indent]

    incr indent -1
    set xml_sysgenblock_end "[indent_space $indent]</sysgenblock>\n"

    append xml_sysgenblock $xml_sysgenblock_begin
    append xml_sysgenblock $xml_blockhead
    append xml_sysgenblock $xml_blockgui
    append xml_sysgenblock $xml_sysgenblock_end

    if {$xmlfile == ""} {
        # by default we write the xml file
        # TODO: for now think we can go ahead with this - but feel free to change this, if needed in future
        # generating xmlfile name in old Coregen style : i.e. not considering VLNV as the ipKey here
        set xmlfile "$oldIPKey"
        #set xmlfile "$ipkey"
        append xmlfile "_gui.xml"
    }

    if {$xmlfile != "" && $xmlfile != "-"} {
        set xmlfid [open $xmlfile w]
        puts $xmlfid $xml_sysgenblock
        close $xmlfid
        puts "wrote $xmlfile"
    } elseif {$xmlfile == "-"} {
        puts $xml_sysgenblock
    }

    return $xml_sysgenblock
}

proc ::xilinx::dsptool::toXmlBlockHead {ipkey icon_width icon_height icon_wmark_color icon_bg_color handlers_enablement handlers_action dll_name dll_entry_point libraries indent} {
    set xml_icon "[indent_space $indent]<icon width=\"$icon_width\" height=\"$icon_height\" wmark_color=\"$icon_wmark_color\" bg_color=\"$icon_bg_color\"/>\n"

    if { $dll_name == "" } {
        set dll_name "sysgen"
    }

    if { $dll_entry_point == "" } {
        set dll_entry_point "coregenimport_config"
    }

    # we used to use sysgen:ipkey_config as the entry point

    set xml_dll "[indent_space $indent]<dll name=\"$dll_name\" entry_point=\"$dll_entry_point\"/>\n"

    set handlers [list]
    if {$handlers_enablement != ""} {
        lappend  handlers "enablement=\"$handlers_enablement\""
    }
    if {$handlers_action != ""} {
        lappend  handlers "action=\"$handlers_action\""
    }
    
    set handlers_xml ""
    if {[llength $handlers] > 0} {
        set handlers_body [join $handlers " "]
        append handlers_xml "[indent_space $indent]<handlers $handlers_body/>\n"
    }
    set xml_libraries [toXmlSimulinkLibs $libraries $indent]

    set xml_head "$xml_icon$xml_dll$handlers_xml$xml_libraries"

    return $xml_head
}

proc ::xilinx::dsptool::toXmlBlockgui {ipname ipversion dsptool_ready block_description ipkey param_order indent} {
    set xml_blockgui_begin "[indent_space $indent]<blockgui label=\"Xilinx $ipname $ipversion\">\n"

    # puts "in toXmlBlockgui, block_description: $block_description"

    incr indent
    set xml_info "[indent_space $indent]<editbox name=\"infoedit\" default=\"$block_description\" read_only=\"true\" evaluate=\"false\" multi_line=\"true\"/>\n"

    set xml_tabpane [toXmlTabpane $ipkey $param_order $indent]
    set xml_hidden_vars [toXmlHiddenVars $ipname $ipversion $dsptool_ready $indent]

    incr indent -1
    set xml_blockgui_end "[indent_space $indent]</blockgui>\n"

    set xml_blockgui ""

    append xml_blockgui $xml_blockgui_begin
    append xml_blockgui $xml_info
    append xml_blockgui $xml_tabpane
    append xml_blockgui $xml_hidden_vars
    append xml_blockgui $xml_blockgui_end

    return $xml_blockgui

}
#
#Creates a block gui based on user options and the IP requested from IP-repository
#
#@param
#@param
#@param
proc ::xilinx::dsptool::toBlockXml_v2 {ipname ipversion {dsptool_ready false} {block_description ""} {param_order {}} {icon_width ""} {icon_height ""} {icon_wmark_color ""} {icon_bg_color ""} {dll_name ""} {dll_entry_point ""} {handlers_enablement ""} {handlers_action ""} {libraries {}} {xmlfile ""}} { 
    # Obtain the IPKey based on name and version
    set ipKey [findIPKey $ipname $ipversion]
    if {$ipKey == ""} {
        error "Could not find $ipname $ipversion"
        return $xml_sysgenblock
    }
    set ip [createIP $ipKey ""]
    
    set oldIPKey [xilinx::dsptool::getOldFormatIPKey $ipKey]
    #Create a list of template parameters
    set paramList [$ip listParameters]
    $paramList GetIterator paramIter
    $paramIter First
    DictBegin guiTemplateCommands
    DictPutStr guiTemplateCommands "SIMULINK_NAME" "$ipname $ipversion "
    DictPutStr guiTemplateCommands "IP_NAME" "$ipname"
    DictPutStr guiTemplateCommands "IP_VERSION" "$ipversion"
    # TODO: for now think we can go ahead with this - but feel free to change this, if needed in future.
    # Putting block_type in old Coregen style : i.e. not considering VLNV as the ipKey here.
    DictPutStr guiTemplateCommands "BLOCK_TYPE" "$oldIPKey"
    #DictPutStr guiTemplateCommands "BLOCK_TYPE" "$ipKey"
    DictPutStr guiTemplateCommands "ENABLEMENT_FUNCTION" "$handlers_enablement"
    if { [llength $libraries] > 0 } {
        set librariesValue ""
        foreach library $libraries {
             set librariesValue "$librariesValue<library name=\"$library\"/>\n"                        
        }
        DictPutStr guiTemplateCommands "LIBRARY_NAME" $librariesValue
    } else {
        DictPutStr guiTemplateCommands "LIBRARY_NAME" "$ipKey_Lib"
    }
    while { [$paramIter IsEnd] != 1 } {
        set paramName [string tolower [$paramIter CurrentItem] ]        
        set paramType [$ip getParameterProperty $paramName "type"]
        set paramInfo [getParamInfo $ipKey $paramName $paramType]
        set paramTab [lindex $paramInfo 0]
        set paramLabel [lindex $paramInfo 1]
        set paramGroup [lindex $paramInfo 2]
        set paramRank  [lindex $paramInfo 3]
        set paramNameTemplate [string toupper [$paramIter CurrentItem] ]
        set sysgenParamType [getSysgenParamType $paramType]
        set paramDefaultValue [getSysgenDefaultValue [$ip getParameterProperty $paramName "defaultvalue"] $paramType]
        DictPutStr guiTemplateCommands "LABEL_$paramNameTemplate" $paramLabel
        DictPutStr guiTemplateCommands "TYPE_$paramNameTemplate" $sysgenParamType
        DictPutStr guiTemplateCommands "DEFAULT_$paramNameTemplate" $paramDefaultValue
        if { $paramType == "LIST" } {            
            set itemsValue ""
            set items [$ip getParameterProperty $paramName "list"]
            foreach item $items {
                set itemsValue "$itemsValue<item value=\"$item\"/>\n"
            }
            DictPutStr guiTemplateCommands "ITEMS_$paramNameTemplate" $itemsValue
        }
        $paramIter Next
    }    
    DictEnd guiTemplateCommands

    if {$xmlfile == ""} {
        # TODO: for now think we can go ahead with this - but feel free to change this, if needed in future
        # generating xmlfile name in old Coregen style : i.e. not considering VLNV as the ipKey here
        set xmlfile "$oldIPKey"
        #set xmlfile "$ipKey"
        append xmlfile "_gui"
    }    
    set cg2sg "[getSysgenInstallDir]/bin/lin64/cg2sg"
    set ::env(XCOPE) $::env(XILINX_DSP)/sysgen
    exec $cg2sg [getSideInfo $ipKey] $xmlfile $guiTemplateCommands
    puts "wrote $xmlfile.xml"
    return $guiTemplateCommands
}

proc ::xilinx::dsptool::toXmlHiddenVars {ipname ipversion dsptool_ready indent} {
    #Standard IP Import Specific Parameters
    set comment_ip_import_vars "[indent_space $indent]<!-- IP Import Specific Parameters -->\n"    
    set hidden_ipname [toXmlOneHiddenVar ip_name $ipname false String $indent]
    set hidden_ipversion [toXmlOneHiddenVar ip_version $ipversion false String $indent]
    set hidden_dsptool_ready [toXmlOneHiddenVar dsptool_ready $dsptool_ready true Bool $indent]
    set hidden_ipcore_usecache [toXmlOneHiddenVar "ipcore_usecache" true true Bool $indent]
    set hidden_ipcore_useipmodelcache [toXmlOneHiddenVar "ipcore_useipmodelcache" true true Bool $indent]
    
    #Non-Standard Wrapper Parameters
    set comment_ip_import_wrapper_vars "[indent_space $indent]<!-- IP Import Wrapper Specific Parameters -->\n"
    set hidden_ipcore_wrapper_available [toXmlOneHiddenVar "wrapper_available" true true Bool $indent]
    set hidden_ipcore_port_translation_map [toXmlOneHiddenVar "port_translation_map" "{ 'ce' => 'en', 'sclr' => 'rst' }" false String $indent]    
    
    return "$comment_ip_import_vars$hidden_ipname$hidden_ipversion$hidden_dsptool_ready$hidden_ipcore_usecache$hidden_ipcore_useipmodelcache$comment_ip_import_wrapper_vars$hidden_ipcore_wrapper_available$hidden_ipcore_port_translation_map"
}

proc ::xilinx::dsptool::toXmlOneHiddenVar {name default_value evaluate ctype indent } {
    set xml_one_hidden "[indent_space $indent]<hiddenvar name=\"$name\" default=\"$default_value\" evaluate=\"$evaluate\" ctype=\"$ctype\"/>\n"
    return $xml_one_hidden
}

proc ::xilinx::dsptool::toXmlTabpane {ipkey param_order indent} {
    set xml_tabpane ""

    set xml_tabpane_begin "[indent_space $indent]<tabpane>\n"

    incr indent
    set xml_coregen_tab [toXmlCoregenTab $ipkey $param_order $indent]
    incr indent -1

    set xml_tabpane_end "[indent_space $indent]</tabpane>\n"
    
    append xml_tabpane $xml_tabpane_begin
    append xml_tabpane $xml_coregen_tab
    append xml_tabpane $xml_tabpane_end

    return $xml_tabpane
}

proc ::xilinx::dsptool::toBlockXmlWrapper { args } {
    variable DEBUG
    set handlers_enablement "xlipmagiccallback"
    set dsptool_ready false

    # set handlers_action "xlipmagicaction"
    if { [llength $args] == 1 } {
        set config_file [lindex $args 0]
        source $config_file
    } elseif { [llength $args] == 2 } {
        set ip_name [lindex $args 0]
        set ip_version [lindex $args 1]
    } elseif { [llength $args] == 3 } {
        set ip_name [lindex $args 0]
        set ip_version [lindex $args 1]
        set libraries [list [lindex $args 2]]
    } elseif { [llength $args] == 4 } {
        set ip_name [lindex $args 0]
        set ip_version [lindex $args 1]
        set libraries [list [lindex $args 2]]
        set handlers_enablement [list [lindex $args 3]]
    }

    if {! [info exists ip_name] } {
        error "ip_name must be set in the config file $config_file"
    }
    if {! [info exists ip_version] } {
        error "ip_version must be set in the config file $config_file"
    }

    set other_params [list "block_description" "param_order" "icon_width" "icon_height" "icon_wmark_color" "icon_bg_color" "dll_name" "dll_entry_point" "handlers_enablement" "handlers_action" "libraries" "xmlfile"]

    foreach other_param $other_params {
        if {! [info exists $other_param] } {
            set $other_param ""
        }
    }
    if {$DEBUG == 1} {
		toBlockXml_v2 $ip_name $ip_version $dsptool_ready $block_description $param_order $icon_width $icon_height $icon_wmark_color $icon_bg_color $dll_name $dll_entry_point $handlers_enablement $handlers_action $libraries $xmlfile
	} else {
    	set xml_text [toBlockXml $ip_name $ip_version $dsptool_ready $block_description $param_order $icon_width $icon_height $icon_wmark_color $icon_bg_color $dll_name $dll_entry_point $handlers_enablement $handlers_action $libraries $xmlfile]
	}
}

# returns the path for the coregen ip gui's .xml file, if any,
# else returns null
proc ::xilinx::dsptool::findSysgenXml_AT { ipKey } {
    set xml_file [findSysgenXml $ipKey]

    set xml_file_at [String2AnyString $xml_file]

    return $xml_file_at
}

# returns the path for the coregen ip gui's .xml file, if any,
# else returns null
proc ::xilinx::dsptool::findSysgenXml { ipKey } {
    #variable ipmgr_if

    #set ippath [$ipmgr_if getModelPath $ipKey]
    set componentID [::xilinx::RepositoryManager::GetComponentID $ipKey]
    set ippath [::xilinx::TGIHelper::getFilePaths $componentID ":ise.xilinx.com:coregen.model" true]

    set ippath_parent [file dirname $ippath]

    set oldIPKey [xilinx::dsptool::getOldFormatIPKey $ipKey]
    # TODO: for now think we can go ahead with this - but feel free to change this, if needed in future.
    # Generating xmlfile name in old Coregen style : i.e. not considering VLNV as the ipKey here. For
    # third-party IP-support, if this needs to be changed (because, a third-party IP may have same name
    # and version as an existing Xilinx IP) in future, then please visit xladdipblock.m as well.
    set xml_name $oldIPKey
    #set xml_name $ipKey
    append xml_name "_gui.xml"

    set xml_file [file join $ippath_parent $xml_name]

    if { [file isfile $xml_file] } {
        return $xml_file
    } else {
        return ""
    }
}

# returns the path for the coregen ip gui's .tcl file
proc ::xilinx::dsptool::getIPPath { ipKey } {
    #variable ipmgr_if
    #set ippath [$ipmgr_if getModelPath $ipKey]
    set componentID [::xilinx::RepositoryManager::GetComponentID $ipKey]
    set ippath [::xilinx::TGIHelper::getFilePaths $componentID ":ise.xilinx.com:coregen.model" true]
    return $ippath
}

# returns the path for the coregen ip gui's .ui file
proc ::xilinx::dsptool::getSideInfo { ipKey } {
    set ippath [getIPPath $ipKey]
    set oldIPKey [xilinx::dsptool::getOldFormatIPKey $ipKey]
    set sideFile [file join [file dirname $ippath] ${oldIPKey}.ui]
    #set sideFile [file join [file dirname $ippath] ${ipKey}.ui]
    return $sideFile
}

# returns whether coregen ip gui's .ui file exists or not
proc ::xilinx::dsptool::hasSideInfo { ipKey } {
    return [file exists [getSideInfo $ipKey]]
}

proc ::xilinx::dsptool::getParamInfo { ipKey paramName paramType} {
	variable DEBUG
    set paramInfo [list "Coregen Parameters" "" "" "100000"]
    if {[hasSideInfo $ipKey] && $DEBUG } {
        set paramInfo [getcontrolgrouping $paramName [getSideInfo $ipKey] $paramType]        
    }
    if { [lindex $paramInfo 0]  == "" } {
        return [list "Coregen Parameters" "" "" "100000"]
    }
    return $paramInfo
}

# returns tcl platform (unix/windows etc.)
proc ::xilinx::dsptool::getPlatform { } {
    global tcl_platform
    return $tcl_platform(platform)
}

# returns true if platform is unix/linux, else false
proc ::xilinx::dsptool::isLinux { } {
    global tcl_platform
    if { [lindex $tcl_platform(os) 0] == "Linux"} {
        return true
    }
    return false
}

# returns true if platform is windows, else false
proc ::xilinx::dsptool::isWindows { } {
    global tcl_platform
    if { $tcl_platform(platform) == "windows" } {
        return true
    }
    return false
}

# for windows platform, returns bin/nt
# else returns lib/lin64
proc ::xilinx::dsptool::getSharedLibrarySubdir { } {
    set mplatform [getPlatform]
    if { $mplatform == "windows" } {
        return "bin/nt"
    }
    return "lib/lin64"
}

# for windows platform returns null, else lib
proc ::xilinx::dsptool::getSharedLibraryPrefix { } {
    set mplatform [getPlatform]
    if { $mplatform == "windows" } {
        return ""
    }
    return "lib"
}

# returns the path where sysgen has been installed
proc ::xilinx::dsptool::getSysgenInstallDir { } {
	#XILINX_DSP
	set XILINX_DSP ""
    catch {
        set XILINX_DSP $::env(XILINX_DSP)
    }
	set sysgenInstallDir [file join $XILINX_DSP sysgen]
	if { $sysgenInstallDir != "" } {
	    return $sysgenInstallDir
	}
	    
    #XILINX
    catch {
		set XILINX $::env(XILINX)
		set XILINX_DSP $XILINX
	}
	set sysgenInstallDir [file join $XILINX_DSP sysgen]
	if { $sysgenInstallDir != "" } {
	    return $sysgenInstallDir
	}
	
}

#
#Description : Returns the xilinx's platform tag as nt, lin32 or lin64

#
proc ::xilinx::dsptool::getPlatformTag { } {
    global tcl_platform
    switch -glob -- [lindex $tcl_platform(os) 0] {
       Lin* {
           switch -glob -- $tcl_platform(machine) {
               x86_64 {
                   return "lin64"
               }
               default {
                   return "lin"
               }
           }
       }
        default {
       switch -glob -- $tcl_platform(machine) {
           amd64 {
	           return "nt64"
           }
           default {
	           return "nt"
           }
       }
    }
}
}

# returns Sysgen param type : for e.g. if coregen paramtype is INT, it will return Int
proc ::xilinx::dsptool::getSysgenParamType { paramType } {
    set paramType "[string toupper [string index $paramType 0]][string tolower [string range $paramType 1 end]]"            
    if {$paramType == "String" || $paramType == "Bin" || $paramType == "File" } {
        set paramType "String"
    }
    return $paramType
}

# returns absolute path for the sysgen's executable/dll file
proc ::xilinx::dsptool::getPath { product {type ""} {file ""} } {
    set platformTag [::xilinx::dsptool::getPlatformTag]
    if { $product == "sysgen" } {
        set sysgenInstallDir [::xilinx::dsptool::getSysgenInstallDir]
        set dir $sysgenInstallDir
        switch $type {
            exe { 
                set dir [file join $sysgenInstallDir "bin" $platformTag] 
            }
            dll {
                    if { [::xilinx::dsptool::isWindows] } {
                        set dir [file join $sysgenInstallDir "bin" $platformTag]
                    } else {
                        set dir [file join $sysgenInstallDir "lib" $platformTag]
                    }
            }
        }
    }

    if { $file == "" } {
        return $dir
    } else {
        return [file join $dir $file]
    }
} 

#
# returns sysgen default value for the parameter
# for BOOL type params, returns on or off (instead of true or false)
# for STRING,BIN,FILE type params, returns default value with ''
# for others, returns coregen default values
#
proc ::xilinx::dsptool::getSysgenDefaultValue { defaultValue paramType } {
    if { $paramType == "BOOL" } {        
        set mdefaultValue "off"
        if { $defaultValue == true } {
            set mdefaultValue "on"
        } 
        set defaultValue $mdefaultValue
    } elseif { $paramType == "STRING" || $paramType == "BIN" || $paramType == "FILE" } {
        set defaultValue "'$defaultValue'"
    }
    return $defaultValue
}

#
#sysgen_environment
#Description : Sets up enevironment variables as required by the tools
#
proc ::xilinx::dsptool::sysgen_ipexport_environment { } {

    if { [info exists ::env(XIL_INTERNAL_SYSGEN_IPEXPORT)] } {
        # This is called many times in Coregen and ends up causing a crash
        # Only need to set the environment once.
        return
    }

    set sysgeninstalldir [::xilinx::dsptool::getSysgenInstallDir]
    set ::env(SYSGENBUILD) [file dirname $sysgeninstalldir]
    concat_path [::xilinx::dsptool::getPath "sysgen" "exe"]
    set mcr [file join [::xilinx::dsptool::getPath "sysgen"] "3rdparty" [::xilinx::dsptool::getPlatformTag] "mcr"]

    # CR 535910
    # Set the TEMP environment variable if not set
    ::xilinx::dsptool::setTempDirectory

    set ::env(MCR_CACHE_ROOT) $::env(TEMP)
    set ::env(XCOPE) [::xilinx::dsptool::getPath "sysgen"]
    
    # Used by mex bootstrap code
    set ::env(XIL_INTERNAL_SYSGEN_IPEXPORT) "1"

    switch [::xilinx::dsptool::getPlatformTag] {
        lin64 {
            concat_ld_library_path [file join $mcr "v78" "bin" "glnxa64"]
            concat_ld_library_path [file join $mcr "v78" "runtime" "glnxa64"]
            concat_ld_library_path [file join $mcr "v78" "sys" "os" "glnxa64"]

            concat_ld_library_path [file join $::env(XILINX) "java6/lin64/jre/lib/amd64/server"]

            set ::env(XAPPLRESDIR) [file join $mcr "v78" "X11" "app-defaults"]

            # Prevents a crash in the MCR
            array unset ::env MATLAB
            array unset ::env MCR_CACHE_ROOT
        }
        lin {
            concat_ld_library_path [file join $mcr "v78" "bin" "glnx86"]
            concat_ld_library_path [file join $mcr "v78" "runtime" "glnx86"]
            concat_ld_library_path [file join $mcr "v78" "sys" "os" "glnx86"]

            concat_ld_library_path [file join $::env(XILINX) "java/lin/jre/lib/i386/client"]

            set ::env(XAPPLRESDIR) [file join $mcr "v78" "X11" "app-defaults"]

            # Prevents a crash in the MCR
            array unset ::env MATLAB
            array unset ::env MCR_CACHE_ROOT
        }
        nt {
            concat_path [file join $mcr "bin" "win32"]
            concat_path [file join $mcr "runtime" "win32"]
        }
        default {
        }
    }
}

#
#getTempDirectory
#Description : return path to a temporary directory
#
proc ::xilinx::dsptool::getTempDirectory { } {
    set tempDir ""
    catch {
        set tempDir $::env(TEMP)
    }
    if { [string equal $tempDir ""] } {
        catch {
            set tempDir $::env(TMP)
        }
    }
    if { [string equal $tempDir ""] } {
        set $tempDir "./temp"
        catch {
            if { [::xilinx::dsptool::isWindows] } {
                set tempDir "c:/temp"
            } else {
                set tempDir "/tmp"
            }
        }
    }
    set tempDir [string map { "\\" "/" } $tempDir]
    return $tempDir
}

#setTempDirectory
#Description : Set the TEMP directory
# 
proc ::xilinx::dsptool::setTempDirectory { } {
    set tempDir ""
    catch {
        set tempDir $::env(TEMP)
    }
    if { [string equal $tempDir ""] } {
        set tempDir [::xilinx::dsptool::getTempDirectory]
    }
    set ::env(TEMP) $tempDir;
    #puts "Setting TEMP to $tempDir"
    return
}

##
# Description : recaches the corrupted object. Ocassionally an irrecoverable
# error occurs that results in the cached object not working. This method 
# recreates the object.
#
#   @param ipKey the coregen unique id of an IP
#   @param family FPGA platform to be targetted could be a 4-tuple of 
#       a string
proc ::xilinx::dsptool::removeFromCache { ipKey family } {
    variable ipmodel_obj_hashmap
    if {[llength $family] == 1} {
        set devsetting [getDevSetting [string tolower $family]]
        set fam [lindex $devsetting 0]
        set dev [lindex $devsetting 1]
        set spd [lindex $devsetting 2]
        set pkg [lindex $devsetting 3]
    } else {
        set fam [string tolower [lindex $family 0]]
        set dev [lindex $family 1]
        set spd [lindex $family 2]
        set pkg [lindex $family 3]
    }
    set ipHashKey [::xilinx::dsptool::buildIPHashKey $ipKey $fam $dev $spd $pkg]
    unset ipmodel_obj_hashmap($ipHashKey)
    if { [info exists ipmodel_obj_hashmap($ipHashKey)] } {
        return "ERROR: Did not delete hash object"
    }
    return "OK"
}
