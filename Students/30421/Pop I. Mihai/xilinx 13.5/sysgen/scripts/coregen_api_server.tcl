#This is a server program that provides access into
#CoreGen API function. This TCL program should only 
#be launched in the context of xtclsh because of
#dependencies of CoreGen API on xtclsh.

package require cmdline
##
# This is a convenience function that sends a reply back on the
# socket that has been opened
#
proc sendResult { channelID result } {
	puts $channelID $result
	flush $channelID
}

##
# Callback for registering a socket. This function is called 
# when a socket is registered and sets up the info recieve 
# event handlers
#
proc CoregenServer_Accept { channel clientaddr clientport } {
    fileevent $channel readable [list CoregenServer_ProcessCommand $channel]
} 

##
# Callback for acting on a command to this server. Again for
# now this only handles one client. This is required since
# the DesignEnvAPI is a singleTon API and only one version
# Can exist in a tcl session
#
proc CoregenServer_ProcessCommand { channel } {
    set command ""
    if {[eof $channel] || [catch {gets $channel command}]} {
        #The client is dead so we want to exit this session
        close $channel
        exit
    } else {
        #Fall through to service request
        #puts "Received the following command : $command"
        #::Xilinx::DesignEnvAPI::Puts $command
        switch -exact $command {
            "Init" {
                ####################################
                #   PERFORM INITIALIZATION HERE    #
                ####################################
                set err [\
		    	    catch {
					    ::Xilinx::DesignEnvAPI::init
					    ::Xilinx::DesignEnvAPI::SetTempDir "."
				    } initErrMsg
			    ]
			    if {$err} {
				    sendResult $channel "ERROR:$initErrMsg"
			    } else {
				    sendResult $channel "OK"
			    }
            }
            "Disable" {
                ####################################
                #   PERORM ANY CLEANUP IF REQUIRED #
                #   Then shut down                 #
                ####################################
                sendResult $channel "OK"
                close $channel
                exit
            }
		    default {
                ####################################
                #   Evaluate all other commands as #
                #   Tcl Commands                   #
                ####################################
            	set err [\
            		catch {
                      set result [eval $command]
			        } cmdExecErr
		        ]
		        if {$err} {
			        sendResult $channel "ERROR:$cmdExecErr"
		        } else {
		            sendResult $channel $result	
                }
            }
        } 
   }
} 

##
# Parse the command line arguments and provide error messages
# if command line incorrectly formed
proc getopts { c_argc c_argv opts} {
 upvar 1 $opts opts_ref
 set parameters {  
     {p.arg   4001 "Port used to start the CoreGen API Server Program"}  
 }
 array set opts_ref [cmdline::getoptions c_argv $parameters]  
}

##
# Main sub routine. Parses command line arguments and initiates
# a tcl server
proc main { c_argc c_argv } {
    # Set program Defaults
    getopts $c_argc $c_argv opts 
    
    # Suppress All Puts
    set fid "suppress" 
    ::Xilinx::DesignEnvAPI::SetPutsChannelID $fid 
    
    # Start the server    
    socket -server CoregenServer_Accept $opts(p) 
    vwait forever
}

source DesignEnvAPI.tcl
main $argc $argv
 
 
