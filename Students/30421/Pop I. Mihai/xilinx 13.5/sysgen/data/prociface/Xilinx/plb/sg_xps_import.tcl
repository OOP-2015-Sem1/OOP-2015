######################################################
set common_utils_path [file join {[% T.template_dir %]} sg_edk_common_utils.tcl]
source ${common_utils_path}

cd {[% T.EDKPrjPath %]}

variable ::sysgen::model_dir
set ::sysgen::model_dir {[% T.modelDir %]}
variable ::sysgen::codebug
set ::sysgen::codebug {[% T.codebug %]}

if [catch { set error_msg [sysgen::[% T.command %] [% T.args %]] } result] {
    set error_msg "encounter error when importing XPS project (return status: ${result})."
}

if {[string length ${error_msg}] > 0} {
    puts "ERROR:SYSGEN: ${error_msg}"
    exit 1
} else {
    puts "SUCCESS:SYSGEN"
}

exit
