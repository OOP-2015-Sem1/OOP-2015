if [catch {eval $tclcmd} errorInfo] {
    puts stderr ${errorInfo}
    exit 1
}
exit
