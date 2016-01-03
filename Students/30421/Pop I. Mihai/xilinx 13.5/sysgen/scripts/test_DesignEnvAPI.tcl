#
source DesignEnvAPI.tcl
#Initialize the ChannelID for Purposes of Debugging
#set fid [open "/tmp/arvindsfile.txt" w]
#::Xilinx::DesignEnvAPI::SetPutsChannelID $fid
#Initialize the API This will be used by the Server Program
# Set Work Area
::Xilinx::DesignEnvAPI::SetTempDir "."
::Xilinx::DesignEnvAPI::SetOutputDir "."
#Create a project
set designProj [::Xilinx::DesignEnvAPI::GetProject]

##### Test Sysgen Flow : FlyWeight Repository Approach ################
#This will close the project add repository and reopen the project
::Xilinx::DesignEnvAPI::AddRepository "./repositories/hdl5/"
#Create an instance of an adder imported for sysgen
set adderInstance [::Xilinx::DesignEnvAPI::GetComponentInstance "my_adder" "xlsysgen_mdl_tb" "Xilinx:HdlBlockLib:adder:1.0"]
#Add one more repository
::Xilinx::DesignEnvAPI::AddRepository "./repositories/hdl8/"
set macInstance [::Xilinx::DesignEnvAPI::GetComponentInstance "my_mac" "xlsysgen_mdl_tb" "Xilinx:HdlBlockLib:mac:1.0"]
set topLevelInfo [::Xilinx::DesignEnvAPI::GetComponentInstanceTopLevelNameAndLanguage "my_adder" "xlsysgen_mdl_tb"]
puts "TopLevelInfo : $topLevelInfo"
set params [::Xilinx::DesignEnvAPI::GetComponentInstanceParams "my_adder" "xlsysgen_mdl_tb"]
puts "params my_adder : $params"
set ports [::Xilinx::DesignEnvAPI::GetComponentInstancePorts "my_adder" "xlsysgen_mdl_tb"]
puts "ports my_adder : $ports"
::Xilinx::DesignEnvAPI::SetComponentInstanceParams "my_adder" "xlsysgen_mdl_tb" {{"input_bitwidth" "13"}}
puts "params my_adder : $params"
set params [::Xilinx::DesignEnvAPI::GetComponentInstanceParams "my_adder" "xlsysgen_mdl_tb"]
puts "params after updating  my_adder : $params"
set ports [::Xilinx::DesignEnvAPI::GetComponentInstancePorts "my_adder" "xlsysgen_mdl_tb"]
puts "ports after updating my_adder : $ports"
exit
#
#
#::Xilinx::DesignEnvAPI::AddRepository "./test_rep/hdl5/"
#set ciID_1 [::Xilinx::DesignEnvAPI::GetComponentInstance "my_mac" "xlsysgen_mdl_tb" "xilinx.com:HdlBlockLib:multiply_accumulate:1.0"]
#set topLevelInfo [::Xilinx::DesignEnvAPI::GetComponentInstanceTopLevelNameAndLanguage "my_mac" "xlsysgen_mdl_tb"]
#set params [::Xilinx::DesignEnvAPI::GetComponentInstanceParams "my_mac" "xlsysgen_mdl_tb"]
#puts "params my_mac : $params"
#set ports [::Xilinx::DesignEnvAPI::GetComponentInstancePorts "my_mac" "xlsysgen_mdl_tb"]
#puts "ports my_mac : $ports"
#
#set topLevelInfo [::Xilinx::DesignEnvAPI::GetComponentInstanceTopLevelNameAndLanguage "my_bufadder" "xlsysgen_mdl_tb"]
#set params [::Xilinx::DesignEnvAPI::GetComponentInstanceParams "my_bufadder" "xlsysgen_mdl_tb"]
#puts "params my_bufadder : $params"
#set ports [::Xilinx::DesignEnvAPI::GetComponentInstancePorts "my_bufadder" "xlsysgen_mdl_tb"]
#puts "ports my_bufadder : $ports"
#set files [::Xilinx::DesignEnvAPI::GetComponentInstanceSourceFiles "my_bufadder" "xlsysgen_mdl_tb"]
#puts "files : $files"

# Create a project
#set pid [::Xilinx::DesignEnvAPI::GetProject]
# Add an instance 
set ciID [::Xilinx::DesignEnvAPI::GetComponentInstance "my_mult" "xlsysgen_mdl_tb" "xilinx.com:ip:mult_gen:11.2"]
#Obtain the toplevel modelname
set topLevelInfo [::Xilinx::DesignEnvAPI::GetComponentInstanceTopLevelNameAndLanguage "my_mult" "xlsysgen_mdl_tb"]
#set generatorChains [list "CUSTOMIZE_AND_GENERATE_CHAIN"]
::Xilinx::DesignEnvAPI::GenerateComponentInstance "my_mult" "xlsysgen_mdl_tb" { {AND COREGEN} {OR CUSTOMIZE_CHAIN INSTANTIATION_TEMPLATES_CHAIN} }
puts "topLevelInfo : $topLevelInfo"
# Get Model Params
#set p [::Xilinx::DesignEnvAPI::GetComponentInstanceParams "my_mult" "xlsysgen_mdl_tb"]
#::Xilinx::DesignEnvAPI::Puts "Before Updating ComponentInstance Params...."
#::Xilinx::DesignEnvAPI::Puts "$p"
#::Xilinx::DesignEnvAPI::Puts "---------------------"
#set portA [list "C_A_WIDTH" "24"]
#lappend pList $portA
#::Xilinx::DesignEnvAPI::SetComponentInstanceParams "my_mult" "xlsysgen_mdl_tb" $pList
#set p [::Xilinx::DesignEnvAPI::GetComponentInstanceParams "my_mult" "xlsysgen_mdl_tb"]
#::Xilinx::DesignEnvAPI::Puts "After Updating Component Instance Params...."
#::Xilinx::DesignEnvAPI::Puts "$p"
#::Xilinx::DesignEnvAPI::Puts "--------------------"

#Update to see if the port widths changed
#set generatorChains [list "CUSTOMIZE_AND_GENERATE_CHAIN"]
#::Xilinx::DesignEnvAPI::GenerateComponentInstance "my_mult" "xlsysgen_mdl_tb" $generatorChains

#set p [::Xilinx::DesignEnvAPI::GetComponentInstancePorts "my_mult" "xlsysgen_mdl_tb"]
#::Xilinx::DesignEnvAPI::Puts "Component Instance Ports After invoking the DRC chain...."
#::Xilinx::DesignEnvAPI::Puts "$p"
#::Xilinx::DesignEnvAPI::Puts "--------------------"

#set p [::Xilinx::DesignEnvAPI::GetComponentInstanceSourceFiles "my_mult" "xlsysgen_mdl_tb"]
#::Xilinx::DesignEnvAPI::Puts "$p"
#set generatorChains [list "COREGEN" "IP_UPDATE_CHAIN"]
#::Xilinx::DesignEnvAPI::GenerateComponentInstance "my_mult" "xlsysgen_mdl_tb" $generatorChains
#close $fid
