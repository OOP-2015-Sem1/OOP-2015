# Implement the service
# This example just writes the info back to the client...
proc doService {sock cmd} {
    # puts $sock "echosrv:$l"
    # puts "\[$cmd\]"
    if { [catch { set result [eval $cmd]} errMsg] } {
        puts $sock "$errMsg"
    } else {
        puts $sock "$result"
    }
}

# Handles the input from the client and  client shutdown
proc  svcHandler {sock} {
    set l [gets $sock]    ;# get the client packet
    if {[eof $sock]} {    ;# client gone or finished
        close $sock        ;# release the servers client channel
    } else {
        doService $sock $l
    }
}

# Accept-Connection handler for Server. 
# called When client makes a connection to the server
# Its passed the channel we're to communicate with the client on, 
# The address of the client and the port we're using
#
# Setup a handler for (incoming) communication on 
# the client channel - send connection Reply and log connection
proc accept {sock addr port} {
    
    # if {[badConnect $addr]} {
    #     close $sock
    #     return
    # }

    # Setup handler for future communication on client socket
    fileevent $sock readable [list svcHandler $sock]

    # Read client input in lines, disable blocking I/O
    fconfigure $sock -buffering line -blocking 0

    # Send Acceptance string to client
    # puts $sock "$addr:$port, You are connected to the echo server."
    # puts $sock "It is now [exec date]"

    # log the connection
    # puts "Accepted connection from $addr at [exec date]"
}

proc cd_and_source {dir f} {
    set cwd [pwd]
    cd $dir
    source $f
    # xilinx::dsptool::init
    cd $cwd
}

proc find_and_start_server {svcPort} {
    set found 0
    while {!$found} {
        if { [catch { socket -server accept $svcPort} errMsg] } {
            incr svcPort
        } else {
            set found 1
        }
    }

    return $svcPort
}

if {$argc == 2} {
    set svcPort [lindex $argv 1]
    set opt1 [lindex $argv 0]
    if {$opt1 == "-mp"} {
        set useMasterPort 1
    } else {
        set useMasterPort 0
    }
} else {
    set svcPort 4002
    set useMasterPort 0
}

set script_dir [file dirname $argv0]
cd_and_source $script_dir "DSPAPI.tcl"

# Create a server socket on port $svcPort. 
# Call proc accept when a client attempts a connection.
if {!$useMasterPort} {
    socket -server accept $svcPort
} else {
    set masterPortSocket [socket "localhost" $svcPort]
    fconfigure $masterPortSocket -buffering line
    fconfigure $masterPortSocket -blocking off

    set svcPort [find_and_start_server $svcPort]

    puts $masterPortSocket $svcPort
    close $masterPortSocket
}

puts "Server started at port $svcPort"
vwait events    ;# handle events till variable events is set
