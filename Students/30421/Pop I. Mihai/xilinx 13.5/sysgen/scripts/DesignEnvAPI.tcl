##
# This File defines a thin Wrapper layer to the CoreGen 2.0 API
# Functions contained in this file have been optimized for 
# performance when using this layer in the context of a design 
# environment
#
# Created By : Arvind Sundararajan
# Created On : 10/22/2009
#
namespace eval ::Xilinx::DesignEnvAPI {
# Persistent Session Variables. The existence of these variables
# makes this a Singleton API and only one copy per session of 
# Process must exist
	variable apiData
	set apiData(_apiReady) 0
	set apiData(_iRepositoryManager) 0
	set apiData(_iProjectManager) 0
	set apiData(_iStringContainer) 0
	set apiData(_iGenerationManager) 0
	set apiData(_iTGI) 0
	set apiData(_iTGIHelper) 0
	set apiData(_iIPEngine) 0
    	# A hack for setting up the standardIO used for puts	 
    set apiData(_stdout) stdout
    variable cacheData
    set cacheData(_repository) {} 

	#Project Management Related Variables
	variable tempWorkDir
	variable outputDir 
	variable projectCache
	variable ciCache

##
# This function initializes the CoreGen 2.0 API to be used
# by the Design Environment. The interaction is modelled 
# after a Pseudo TGI
#	@param api_version a string specifying the API version to be used
#	@throws Throws a tcl error if unable to initialize
#
proc init { {api_version "20"} } {
	loadLibraries
	initManagers
	cacheRepositoryManager
}

#
# This function initializes the session persistent Managers/Engines. 
# These managers include the Repository Manager, IP Engine, 
# Project Manager, TGI Manager. All of this is code copied 
# from scripts provided by the CoreGen team. If it fails 
# please contact CoreGen team. 
#	@throws Throws a tcl error if unable to initialize
#					any of the managers or the Engines
#
proc initManagers { } {
    variable apiData
	set err [\
    	catch {
			set CStringVectorLibName      "libTcltask_Helpers"
   			set CStringVector             "{28710473-d8d4-48c6-b908-d5855e4b6a00}"

   			set CRepositoryManagerLibName "libSimC_CRepositoryManager"
   				set CRepositoryManager        "{bb780dde-678b-49b5-8dfb-3881188ae60b}"

   			set CProjectManagerLibName    "libSimC_CProjectManager"
   			set CProjectManager           "{f3be11d8-e22f-4f59-8ce9-f358bfb6c9c5}"

   			set CGenerationManagerLibName "libSimC_CGenerationManager"
   			set CGenerationManager        "{bce5a2ea-ce82-4158-8b25-7facac70e585}"

   			set CIPEngineLibName          "libSimC_CIPEngine"
   			set CIPEngine                 "{b0240c75-c285-4752-98b1-1038e49822ce}"

   			Puts "Loading CiT components..."
   			Xilinx::CitP::FactoryLoad $CStringVectorLibName
   			Xilinx::CitP::FactoryLoad $CIPEngineLibName
   			Puts "Done"

   			Puts "Creating IPEngine CiT component..."
   			set cStringVector [Xilinx::CitP::CreateComponent $CStringVector]
   			if { $cStringVector eq "0" } {
    			Puts ""
      			error "ERROR: Failed to create StringVector component"
   			}
   			set cIPEngine [Xilinx::CitP::CreateComponent $CIPEngine]
   			if { $cIPEngine eq "0" } {
       			Puts ""
       			error "ERROR: Failed to create IPEngine component"
			}
			Puts "Done"
			Puts "Getting IPEngine CiT component interfaces..."
			set apiData(_iStringContainer)   [$cStringVector GetInterface $::xilinx::UtilI::IStringContainerID]
			if { $apiData(_iStringContainer) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get StringContainer CiT component interface"
			}	
			set apiData(_iRepositoryManager) [$cIPEngine GetInterface $::xilinx::SimI::IRepositoryManagerID]
			if { $apiData(_iRepositoryManager) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get RepositoryManager CiT component interface"
			}
			set apiData(_iProjectManager)    [$cIPEngine GetInterface $::xilinx::SimI::IProjectManagerID]
			if { $apiData(_iProjectManager) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get ProjectManager CiT component interface"
			}
			set apiData(_iTGI)               [$cIPEngine GetInterface $::xilinx::SimI::ITGIID]
			if { $apiData(_iTGI) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get TGI CiT component interface"
			}
			set apiData(_iTGIHelper)         [$cIPEngine GetInterface $::xilinx::SimI::ITGIHelperID]
			if { $apiData(_iTGIHelper) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get TGIHelper CiT component interface"
			}
			set apiData(_iGenerationManager) [$cIPEngine GetInterface $::xilinx::SimI::IGenerationManagerID]
			if { $apiData(_iGenerationManager) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get GenerationManager CiT component interface"
			}
			set apiData(_iIPEngine)          [$cIPEngine GetInterface $::xilinx::SimI::IIPEngineID]
			if { $apiData(_iIPEngine) eq "0" } {
   				Puts ""
   				error "ERROR: Failed to get IPEngine CiT component interface"
			}
			Puts "Done"
		} initErrMsg
	]
    if {$err == 1} {
		error "ERROR : Could not initialize the CoreGen API : $initErrMsg"
	} else {
		set apiData(_apiReady) 1
	}
}

# This function loads a few different shared libraries to 
# extend TCL to CoreGen 20 API functionality. This list of DLLS 
# must be loaded for the API to be accessible from TCL 
#	@throws Throws a tcl error if unable to initialize
#					any of the managers or the Engines
# 
proc loadLibraries { } { 
    set apiLibs [list "libCitI_CoreStub" \
		"libUtilI_UtilStub" \
		"libSimI_IRepositoryManagerStub" \
		"libSimI_IProjectManagerStub" \
		"libSimI_IGenerationManagerStub" \
		"libSimI_IIPEngineStub"]
	set err [\
    	catch {
			foreach apiLib $apiLibs {
    			load $apiLib[info sharedlibextension]
			}	
		} loadErrMsg
	]
    if {$err == 1} {
		error "ERROR : Could not initialize the CoreGen API : $loadErrMsg"
	}
}
# ----------------- REPOSITORY MANAGER FUNCTIONS -----------------
# CoreGen team has stated that all Xilinx IP will be visible to 
# the RepositoryManager. The Iterator interface to check for 
# existence of a particular API is very expensive. Hence it
# is converted to a tcl list allowing for binary search which
# reduces the the search time by n/logn 
# ----------------------------------------------------------------

#
# This function initializes the local cache to allow faster
# search times for existence of a VLNV key for a component	variable projectCache
#
#	@throws an error if the repository manager has not been properly initialized
proc cacheRepositoryManager { } {
	variable apiData
	variable cacheData
	Puts "Caching Repository Data ..."
	set err [\
		catch {
    			set allVLNVs [$apiData(_iRepositoryManager) ListComponentVLNVs]
			    iForeach vlnv allVLNVs {
       				lappend vlnvList $vlnv
			    }
                if {[info exists vlnvList]} {
    			    set vlnvList [lsort -dictionary $vlnvList]
                    Puts "$vlnvList"
			        set cacheData(_repository) $vlnvList
                }
		} repMngrErr
	]
	if { $err } {
		error "Error occurred while caching Repository information : $repMngrErr"
	}
	Puts "Done"
}

##
# This function returns true if the VLNV exists in the repository
#
#	@param vlnv an ipxact compliant component identifier
#
#	@param return "1" if vlnv exists in the repostory else "0" 
#
proc IsComponentAvailable { vlnv } {
	variable cacheData
    Puts "Cache $cacheData(_repository)" 
	set found [lsearch -sorted -dictionary -exact $cacheData(_repository) $vlnv]
	if { $found == -1 } {
		return 0
	} else {
		return 1
	}
}

##
# This function adds a repository to the repository manager
#
#	@param uri location of CoreGen 2.0 API compliant repository 
#
#   @param return "" if successful
proc AddRepository { {uri} } {
	variable apiData
    CacheProjectsToDisk 
	#InvalidateProjectCache
	set err [$apiData(_iRepositoryManager) AddRepository $uri]
    if {$err != 1 } {
       error "Error occurred during addition of $uri to the repository" 
    }
    cacheRepositoryManager
	RefreshAllRepositories
    ReloadProjectsFromDisk
	return "OK"
}
	 
proc RefreshAllRepositories { } {
	variable apiData
	set succ [$apiData(_iRepositoryManager) RefreshAllRepositories]
	if {$succ} {
		cacheRepositoryManager	
	} else {
		error "Error occurred during refreshing the repositories"
	}
	return
}

#
# This function sets channel id used in all the puts functions in this file
# This is required because xtclsh does not seem to do a good job of displaying
# puts output to the parent stdout
#
#	@param channelID to override stdout output
# 
proc SetPutsChannelID { {channelID stdout} } {
	variable apiData
	set apiData(_stdout) $channelID
}

proc Puts { displayText } {
	variable apiData
	if { $apiData(_stdout) != "suppress" } {
	    puts $apiData(_stdout) $displayText
	    flush $apiData(_stdout)
	}
}

## ----------------- PROJECT MANAGER FUNCTIONS --------------------
# The project manager managers projects. Each system Model could 
# possibly have a separate project or the same project could be 
# shared across different sysgen models. The current inclination is
# to use only a single project as the CoreGen team has indicated in 
# its preliminary discussions that only one project will be 
# supported.
## ----------------------------------------------------------------

##
# This sets the path to the temporary working directory needed where the 
# project file will be saved. Using a variable like this makes a project
# single ton. However, we would like to use multiple projects one 
# reflecting each model that is opened. Most likely this value should be 
# similar to c:/temp/sysgentmp-<username> on windows machine as set by
# sysgen environment manager
#
#	@param uri for a temporary directory location
#
#   @param return returns true if successful
proc SetTempDir { {tdir "c:/temp"} } {
	variable tempWorkDir
	set tempWorkDir $tdir
}	

##
# This sets the output directory for the current generation
#
#   @param  odir the directory where the out products of generators should be produced
#   
#   @return "OK" if successful
proc SetOutputDir { {odir "."} } {
    variable outputDir
    set outputDir $odir
    return "OK"
}
##
# This function returns a project ID that can be used for the given name supplied. 
# The name supplied should be derived from the Design name to be used by the design
# environment. This function is different from the Project Manager API function in 
# that projects that have been already opened are cached and do not require reopening.
# The caching of projects is managed through this API
#
#	@param designName project name or design handle
#	@param deviceFamily xilinx device family "virtex5" etc.
#	@param device to be used eg. "xc5vlx20t" etc.
#	@param package to be used eg. "ff323" etc.
#	@param speedGrade to be used eg. "-2" etc.
#
#	@param return ProjectID if successful "" if unsuccessful 
#
proc GetProject { {designName "xlsysgen_mdl_tb"} {deviceFamily "virtex5"} {device "xc5vlx20t"} {package "ff323"} {speedGrade "-2"} } {
	variable projectCache
	set vlnv [GenerateDesignVLNV $designName]
	set a [info exists projectCache($vlnv)]
	if { $a } {
		return $projectCache($vlnv)
	} else {
		return [NewProject $designName $vlnv $deviceFamily $device $package $speedGrade]
	}
}

##
# This function updates the project part 
#
#	@param designName project name or design handle
#	@param deviceFamily xilinx device family "virtex5" etc.
#	@param device to be used eg. "xc5vlx20t" etc.
#	@param package to be used eg. "ff323" etc.
#	@param speedGrade to be used eg. "-2" etc.
#
#	@param return ProjectID that can be used to add components 
# Currently this function silently returns if the project is not found in cache. Maybe 
# we need to through an error. Reconsider this during Code Cleanup.
proc UpdateProject { {designName "xlsysgen_mdl_tb"} {deviceFamily "virtex5"} {device "xc5vlx20t"} {package "ff323"} {speedGrade "-2"} } {
	set vlnv [GenerateDesignVLNV $designName]
	set a [info exists projectCache($vlnv)]
	if { $a } {
		$apiData(_iTGIHelper) setDesignPart $projID "$device$speedeGrade$package"
	}
	return
}

#
# This function creates a new project and returns the project ID. This function should 
# not be used directly as GetPOroject is the preferred methodology. 
#
#	@param SysGen design Name
#	@param Device Family to use eg. "virtex5" etc.
#	@param device to be used eg. "xc5vlx20t" etc.
#	@param package to be used eg. "ff323" etc.
#	@param speedGrade to be used eg. "-2" etc.
#
#	@param return ProjectID that can be used to add components 
#
proc NewProject { designName vlnv deviceFamily device package speedGrade } {
	variable tempWorkDir
	variable apiData
	variable projectCache
    variable outputDir
	set tdir $tempWorkDir
    set odir $outputDir
	if { $vlnv == "" } {
		set vlnv [GenerateDesignVLNV $designName]
	}
	set designConfigurationFileName ${odir}/${designName}.cgd
	Puts "Creating a new project $vlnv..."
	if { [file isfile $designConfigurationFileName] } {
		set err [\
				catch {
					file delete -force $designConfigurationFileName	
				} errMsg
		]
		if {$err} {
			error "Failed to create a new project : $errMsg" 
		}
	}
	set projID [$apiData(_iProjectManager) NewProject $designConfigurationFileName $vlnv]
	$apiData(_iTGIHelper) setDesignPart $projID "$device$speedGrade$package"
    #
    # NONE of the following iTGIHelper works and this is important to be able to specify
    # atleast the workdir and tempdir
    #
	#$apiData(_iTGIHelper) setDesignWorkingDirectory $projID $tdir
	#$apiData(_iTGIHelper) setOutputDirectory $projID $odir
	Puts "Done."
	Puts "Caching the new project $vlnv..."
	set projectCache($vlnv) $projID
	Puts "Done."
	return $projID
}

#proc SaveAndCloseProject { designName } {
#    variable projectCache
#    variable apiData
#        $apiData(_iProjectManager) SaveAsProject $projectCache($vlnv) "$tempWorkDir/xlsysgen_mdl_tb.cgd"
#        $apiData(_iProjectManager) CloseProject $projectCache($vlnv)
#}
#
# The CoreGen API currently cannot handle updating an Opened Project with New Information
# This entais that we need to close all projects before we add the repository and then ReOpen
# This has been made part of repository manager function that is exposed by designEnv to
# abstract the user from such an implementation detail. This will have a drastic effiect
# on performance especially from point of view of packaging and availability of data
# Seriously consider caching related issues in the context of this defect.
#
proc InvalidateProjectCache { } {
	variable projectCache
	variable apiData
	foreach vlnv [array names projectCache] {
        #$apiData(_iProjectManager) SaveAsProject $projectCache($vlnv) "./xlsysgen_mdl_tb.xml"
        $apiData(_iProjectManager) CloseProject $projectCache($vlnv)
		unset projectCache($vlnv)
	}
}

proc CacheProjectsToDisk { } {
	variable projectCache
	variable apiData
    variable tempWorkDir
	foreach vlnv [array names projectCache] {
        $apiData(_iProjectManager) SaveAsProject $projectCache($vlnv) "$tempWorkDir/xlsysgen_mdl_tb.cgd"
        $apiData(_iProjectManager) CloseProject $projectCache($vlnv)
    }
}

proc ReloadProjectsFromDisk { } {
	variable projectCache
	variable apiData
    variable tempWorkDir
    variable ciCache
	foreach vlnv [array names projectCache] {
        set pid [$apiData(_iProjectManager) OpenProject "$tempWorkDir/xlsysgen_mdl_tb.cgd"]
        #   Reset the project cache
        set projectCache($vlnv) $pid
        #   Reset the Component Cache
        set ciIDs [$apiData(_iTGI) getDesignComponentInstanceIDs $pid]
        iForeach ciID ciIDs {
            set cID [$apiData(_iTGI) getComponentInstanceComponentID $ciID]
            set componentInstanceName [$apiData(_iTGI) getName $ciID]
            set ciCache($pid,$componentInstanceName) $ciID
        }
	}
}
##
# In IP-XACT the design configuration file has to identified by a VLNV.
# To adapt a SysGen Model File to create a VLNV we need to generate a 
# VLNV key for the design configuration based on SysGen model name. Since
# Matlab doesnot allow opening two models with the same name, this 
# should help us. 
# TODO : Attempt generating unique VLNV including path. Maybe create checksum
#
#	@param name project name or sysgen design Name
#
#	@return The VLNV identifier for a model
#
proc GenerateDesignVLNV { {name "xlsysgen_mdl_tb"} } {
	set vlnv "xilinx.com:sysgen:$name:1.0"
	return $vlnv
}

# ----------------- Component Management Functions -------------
# This is really the meat of all the manipulations through TGI like 
# api. At this level each component instance should be accessible 
# through a string id provided by the Design Environment. 
# ----------------------------------------------------------------

##
# Returns a pre-existing component instance ID or creates a new one if unavailable
#
#	@param componentInstanceName name/handle of the component instance to be obtained
#	@param designName name/handle of the project or design 
#	@param vlnv ipxact compliant component identifier
#
#	@return component instance id
#
proc GetComponentInstance { {componentInstanceName "my_mult"} {designName "xlsysgen_mdl_tb"} {vlnv "xilinx.com:ip:mult_gen:11.0"} } {
	variable apiData
	variable ciCache
	set pid [GetProject $designName]
	if { [info exists ciCache($pid,$componentInstanceName)] } {
		return $ciCache($pid,$componentInstanceName)	
	}
	if { [IsComponentAvailable $vlnv] } {
		Puts "Adding component instance $componentInstanceName..."	
		set cid [$apiData(_iTGI) addComponentInstance $pid $vlnv $componentInstanceName $componentInstanceName "CoreGen IP"]
        if {$cid == ""} {
            error "Failed to create a component instance for : $vlnv"
        }
		Puts "Done." 
		Puts "Caching component instance $componentInstanceName..."
		set ciCache($pid,$componentInstanceName) $cid
		Puts "Done."
		return $cid	
	}
	error "Failed to create a component instance for : $vlnv. Probably the specified component type is not available in the repository."
	return ""
}

##
# This function returns the parameters associated with a ComponentInstance. 
# This is required to obtain the parameterIDs to get and set values. It 
# appears that component has modelParameters which is a list of 
# modelParameter, however a componentInstance can also return parameterIDs
# through the getParameterIDs TGI call. It is unclear from the document
# if a componentInstance can actually accomodate this call, but it appears 
# to be the only way to access these parameters. We try and cache the 
# componentInstance based designName and some componentInstance handle.
# Somewhat similar to flyweight design pattern.
#
#	@param comonentInstanceName is a handle to a componentInstance
#	@param designName           is a handle to a design 
#
#	@return a list of list, each list is a description of the parameter 
#							{paramID paramName paramType paramValue}
# TODO:Caching of parameters names and types at this level may be required to speed things up
#
proc GetComponentInstanceParams { componentInstanceName designName } {
	variable apiData
	set ciID [GetComponentInstance $componentInstanceName $designName]
	if { $ciID == "" } {
		 error "Could not locate component instance."
	}
	set pIDs [$apiData(_iTGI) getParameterIDs $ciID ]
	iForeach paramID pIDs {
		set paramName [$apiData(_iTGI) getName $paramID]
		# I am not sure this is legal. Need to bring this up to the coregen team
		set paramType [GetHdlParamType [$apiData(_iTGI) getModelParameterDataType $paramID]]
		set paramValue [$apiData(_iTGI) getValue $paramID]
		lappend paramList [list "'$paramID'" "'$paramName'" "'$paramType'" "'$paramValue'"]
		lappend pNames $paramName
		 
	}
	# TODO : This is a hack because the CoreGen API exposes both paramIDs and 
	# Model IDS and we need to figure out which is which and not cause unnecessary
	# duplication. This is going to provoke some strong reaction unless it is 
	# resolved. What we do here iw we are using name of the parameter to indicate
	# that they are the same. This is really not a good idea as the name is not 
	# a unique identifier making us depart
	set pModelIDs [$apiData(_iTGI) getComponentModelParameterIDs $ciID "all"]
	iForeach pModelID pModelIDs {
		set pModelName [$apiData(_iTGI) getName $pModelID]
		set found -1 
		if {[info exists pNames]} {
		        set found [lsearch -sorted -dictionary -exact pNames $pModelName]
		}
		if { $found == -1 } {
			set pModelType [GetHdlParamType [$apiData(_iTGI) getModelParameterDataType $pModelID]]
			set pModelValue [$apiData(_iTGI) getValue $pModelID]
			lappend paramList [list "'$pModelID'" "'$pModelName'" "'$pModelType'" "'$pModelValue'"]
		}
	}
    if {[info exists paramList]} {
    	return $paramList
    }
    #Case of Zero parameterization
    set paramList [list]
    return $paramList
}

#
# This function converts the IP-XACT param type to the right HDL param type.
#
#   @param IP-XACT param type
#   @return HDL param type like integer, real and string
#
proc GetHdlParamType { paramType } {
    switch -exact $paramType {
        "long" { return "integer" }
        "double" { return "real" }
        default { return $paramType }
    }
}
##
# This function returns the ports associated with a ComponentInstance. This
# can only be done through the component ID and not the componentInstanceID.
# It is not yet clear how the port enablements will be obtained but the 
# assumption is that it must be possible to inspect some vendor extension 
# to obtain this information.
#
#	@param comonentInstanceName is a handle to a componentInstance
#	@param designName           is a handle to a design 
#
#	@ return a list of list, each list is a description of a port 
#							{portID portName portDirection portRange abstractPortType hdlPortType}
#
proc GetComponentInstancePorts { componentInstanceName designName } {
	variable apiData
	set ciID [GetComponentInstance $componentInstanceName $designName]
    #set a [$apiData(_iTGI) getVendorExtensions $ciID]
    #puts "extensions $a"
	if { $ciID == "" } {
		error "Could not locate component instance."
	}
	#set cID [$apiData(_iTGI) getComponentInstanceComponentID $ciID]
    #set vendorText [$apiData(_iTGI) getVendorExtensions $cID]
    #puts "Vendor Extensions Text : $vendorText"
    set pIDs [$apiData(_iTGI) getComponentPortIDs $ciID]	
	iForeach portID pIDs {
		set portName [$apiData(_iTGI) getName $portID]
		set portDirection [$apiData(_iTGI) getPortDirection $portID]
		set portRange [$apiData(_iTGI) getPortRange $portID]
        $portRange GetIterator myIter 
		iForeach portRangeElem portRange {
			lappend portRangeList $portRangeElem 
		}
        set hdlPortType "std_logic_vector"
        if {![info exists portRangeList]} {
            set hdlPortType "std_logic" 
            set portRangeList [list 0 0]
        }
        #set b "<spirit:vendorExtensions>\n<xilinx:portInfo>\n<xilinx:sampleRate>\n<xilinx:value spirit:resolve=\"generated\">1</xilinx:value>\n<xilinx:validation>true</xilinx:validation>\n<xilinx:portNameRef>CLK</xilinx:portNameRef>\n</xilinx:sampleRate>\n</xilinx:portInfo>\n</spirit:vendorExtensions>\n"
        #$apiData(_iTGI) setVendorExtensions $portID $b
        #set a [$apiData(_iTGI) getVendorExtensions $portID]
        #puts "extensions $a"
		# Since we haven't yet figured out CLK/CE information default everthing to data port
		# Other values can be Clock, ClockEnable, Reset etc. For now everything is Data. Also
		# We find that the wire type information is not yet available so using this approach
		set left [lindex $portRangeList 0]
		set right [lindex $portRangeList 1]
		set width [expr $left-$right]
		#Some more Hacks to figure out the type of port
		#Also some more information about the bus type etc.
		#Needs to be incorporated but we omit it for now
		set abstractPortType "Data"
		if { [string equal -nocase $portName "clk"] } {
			set abstractPortType "Clock"
		} elseif { [string equal -nocase $portName "ce"] } {
			set abstractPortType "ClockEnable"
		} elseif { [string equal -nocase $portName "sclr"] } {
			set abstractPortType "Reset"
		}
		lappend pList [list "'$portID'" "'$portName'" "'$portDirection'" $portRangeList "'$abstractPortType'" "'$hdlPortType'"]
		#set typeIDs [$apiData(_iTGI) getPortTypeDefIDs $portID]
		#iForeach typeID typeIDs {
		#	set name [$apiData(_iTGI) getTypeDefTypeName
		#}
		#Zero out the range
		unset portRangeList 
	}
	return $pList
} 

##
# This function updates the componentInstance with a new set of paramValues 
# paramValues is a name-value pair encoded as a list of lists. Each list in
# the list of lists is of the form { paramName paramValue} hence the use of
# lindex $param 0 and lindex $param 1 
#
#	@param	componentInstanceName a handle to the componentInstance
#	@param designName a handle to the design containing componentInstance
#	@param paramValues is a list of lists where each list contains paramName and paramValue pairs
#
proc SetComponentInstanceParams { componentInstanceName designName paramValues } {
	variable apiData
	set ciID [GetComponentInstance $componentInstanceName $designName]
	if { $ciID == "" } {
		error "Could not locate component instance."
	}
	# Set all Param IDS
	set pIDs [$apiData(_iTGI) getParameterIDs $ciID ]
	$pIDs GetIterator pID_iter
	while { [$pID_iter IsEnd] != 1 } {
		set paramID [$pID_iter CurrentItem]
		set paramName [$apiData(_iTGI) getName $paramID]
		set pmap($paramName) $paramID
		$pID_iter Next
	}
	foreach { param } $paramValues {
		if [info exists pmap([lindex $param 0])] {
			$apiData(_iTGI)	setValue $pmap([lindex $param 0]) [lindex $param 1]
		}
		#else silently skip updating
		#Maybe we should throw an error 
		#do not know yet
	}
	# Set all Model ParamIDs also
	set pIDs [$apiData(_iTGI) getComponentModelParameterIDs $ciID "userdefined"]
	$pIDs GetIterator pID_iter
	while { [$pID_iter IsEnd] != 1 } {
		set paramID [$pID_iter CurrentItem]
		set paramName [$apiData(_iTGI) getName $paramID]
		set mpmap($paramName) $paramID
		$pID_iter Next
	}
	foreach { param } $paramValues {
		if [info exists mpmap([lindex $param 0])] {
			$apiData(_iTGI)	setValue $mpmap([lindex $param 0]) [lindex $param 1]
		}
		#else silently skip updating
		#Maybe we should throw an error 
		#do not know yet
	}
}

##
# This function returns the source files associated with a component. The
# the source files are returned as a list and only correspond to the synthesis
# view of the IP Component Instance.  
#
#	@param componentInstanceName a handle to the componentInstance
#	@param designName a handle to the design containing componentInstance
#   
#   @return fileList is a tcl stringofied list of lists of the following form
#           { {'fileName1' {'fileType1.0' 'fileType1.1' 'sourceFile'} 'logicalName1'} {'fileName2'  {'fileType2.0' 'fileType2.1' 'sourceFile'} 'logicalName2'} ... }
#	TODO : This definitely needs Caching File sets are based on the component itself not the instance
proc GetComponentInstanceSourceFiles { componentInstanceName designName } {
	variable apiData
	set ciID [GetComponentInstance $componentInstanceName $designName]
	set cID [$apiData(_iTGI) getComponentInstanceComponentID $ciID]
	#Obtain all view IDs How to ensure that the designConfiguration is only using a particular view
	#need to find that out
	set viewIDs	[$apiData(_iTGI) getComponentViewIDs $cID 0]
	iForeach viewID viewIDs {
        set envIds [$apiData(_iTGI) getViewEnvIdentifiers $viewID]
        set synthesis_view 0
        iForeach envId envIds {
            if {$envId == "VHDL:*Synthesis:"} {
                set synthesis_view 1 
            }
            if {$envId == ":ise.xilinx.com:synthesis.rtl"} {
                set synthesis_view 1 
            }
        }
        if { $synthesis_view == 1 } {
			set fileSetIDs [$apiData(_iTGI) getViewFileSetIDs $viewID]
			iForeach fileSetID fileSetIDs {
				set fileIDs [$apiData(_iTGI) getFileSetFileIDs $fileSetID]
				iForeach fileID fileIDs {
					set srcFile [string map {"\\" "/"} [$apiData(_iTGI) getFileName $fileID 1]]
                    lappend srcFileElem "'$srcFile'"
                    set fileTypes [$apiData(_iTGI) getFileType $fileID]
                    iForeach fileType fileTypes {
                        set fileType [GetSysGenFileType $fileType]
                        lappend srcFileTypes "'$fileType'"
                    }
                    lappend srcFileTypes "'sourceFile'"
                    lappend srcFileElem $srcFileTypes
                    set logicalName [$apiData(_iTGI) getFileLogicalName $fileID]
                    if {[lsearch -exact $srcFileTypes "'vhdl'"] != -1 || [lsearch -exact $srcFileTypes "'verilog'"] != -1} {
                        if {$logicalName == ""} {
                            set logicalName "work"
                        }   
                    }
                    lappend srcFileElem "'$logicalName'"
					lappend srcFiles $srcFileElem 
                    unset srcFileTypes 
                    unset srcFileElem 
				}
			}	
			if {[info exists srcFiles]} {
				return $srcFiles
			}
			set srcFiles [list]
			return $srcFiles 
		} 
	} 
}

#
# Translates fileType in the case of vhdlSource and verilogSource
#
#   @param fileType
#   @return fileType for SysGen
#
proc GetSysGenFileType { fileType } {
    switch -exact $fileType {
        "vhdlSource" { return "vhdl" }
        "verilogSource" { return "verilog" }
        default { return $fileType }
    }
}
##
# Attempt Generation. Provide a component instance and design to 
# generate a particular configuration.
#
#   @param componentInstanceName handle to the componentInstance
#   @param designName hadle toe the design
#   @param generatorChains list of lists with first element of each list is an operator
#           eg. {  {AND CHAIN1 CHAIN2} {OR CHAIN3 CHAIN4} }
proc GenerateComponentInstance { componentInstanceName designName generatorChains } {
    variable apiData
    Puts "Generating component instance $componentInstanceName..."
    set ciID [GetComponentInstance $componentInstanceName $designName]
    set cID [$apiData(_iTGI) getComponentInstanceComponentID $ciID]
    set generatorChainIDs   [$apiData(_iTGIHelper) getGeneratorChainIDs  $cID]
    Puts "are we here : $generatorChains"
    $apiData(_iStringContainer) Clear
    foreach { generatorChainList } $generatorChains {
        $apiData(_iStringContainer) Clear
        set fxn [lindex $generatorChainList 0]
        set l [llength $generatorChainList]
        for {set x 1} {$x<$l} {incr x} {
            Puts "are we here : [lindex $generatorChainList $x]"
            set generatorChainName [lindex $generatorChainList $x] 
  	        $apiData(_iStringContainer) Add generatorChainName 
        }
   	    set generatorChainIDs   [$apiData(_iGenerationManager) FilterChainIDsByGroup $generatorChainIDs $apiData(_iStringContainer) $fxn]
    }    
	#foreach { generatorChainName } $generatorChains {
  	#    $apiData(_iStringContainer) Add generatorChainName 
	#}
   	#set generatorChainIDs   [$apiData(_iGenerationManager) FilterChainIDsByGroup $generatorChainIDs $apiData(_iStringContainer) "AND"]
	set result [$apiData(_iGenerationManager) Generate $ciID $generatorChainIDs]
	Puts "Done"
    return "OK"
}

##
#  Here we are making an assumption that the viewName "xilinx_vhdlsynthesis" is
#  actually the view we use to perform synthesis. We use the toplevel name from 
#  that view if it exists
#   @param componentInstanceName
#   @param designName
#
#   @return top level model name
#
proc GetComponentInstanceTopLevelNameAndLanguage { componentInstanceName designName } {
	variable apiData
	set ciID [GetComponentInstance $componentInstanceName $designName]
	set cID [$apiData(_iTGI) getComponentInstanceComponentID $ciID]
	#Obtain all view IDs How to ensure that the designConfiguration is only using a particular view
	#need to find that out
    set topLevelName [regsub "_v\\d+_\\d+$" [$apiData(_iTGI) getName  $cID] ""]
    set topLevelLanguage "VHDL" 
	set viewIDs	[$apiData(_iTGI) getComponentViewIDs $cID 0]
	iForeach viewID viewIDs {
		#Bad Hardcoding Here. Need to discuss with Brian about what needs
		if { [$apiData(_iTGI) getName $viewID] == "xilinx_vhdlsynthesis" } {
            set itopLevelName [string trim [$apiData(_iTGI) getViewModelName $viewID]]
            if {$itopLevelName != "" } {
                set topLevelName $itopLevelName
            }
            set itopLevelLanguage [string trim [$apiData(_iTGI) getViewLanguage $viewID]]
            if {$itopLevelLanguage != "" } {
                set topLevelLanguage $itopLevelLanguage
            }      
        }
    }
    lappend synViewInfo "'$topLevelName'"
    lappend synViewInfo "'$topLevelLanguage'"
    return $synViewInfo 
}

#proc GetBusInterfacePortMap { componetInstanceName designName } {
#	variable apiData
#	set ciID [GetComponentInstance $componentInstanceName $designName]
#	set cID [$apiData(_iTGI) getComponentInstanceComponentID $ciID]
#    set busInterfaceIDs [$apiData(_iTGI) getComponentBusInterfaceIDs $ciD]
#    iForeach busInterfaceID busInterfaceIDs {
#        Puts "$busInterfaceID"
#    }
#}
#
# This is a replacement for the more verbose while iterator construct
# This is a helper function that allows iterating over each elemnt of
# a container through the iterator interface exposed by the CoreGen 2.0
# API similar to <b>foreach</b> built in function provided by tcl
#
#	Usage :
#	iForeach elem elemContainer {
#		Puts "$elem"
#	}
#
proc iForeach {elem elem_container body } {
	set elem_iter_init_cmd "\$$elem_container GetIterator ${elem}_iter"
	uplevel 1 $elem_iter_init_cmd
	set term_cmd "\$${elem}_iter IsEnd"
	while {[uplevel 1 $term_cmd] !=1 } {
		uplevel 1 "set $elem \[\$${elem}_iter CurrentItem\]"
		#uplevel 1 "puts \$$elem"
		uplevel 1 $body
		set next_cmd "\$${elem}_iter Next"
		uplevel 1 $next_cmd
	}	
}

}
