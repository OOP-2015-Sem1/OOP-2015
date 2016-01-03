package SgChipscopeDeliverFile;

require Exporter;
@ISA = qw(Exporter);
@EXPORT = qw(deliverChipscopeFile makeChipscopeNgc);

use strict;

use File::Basename;
use FindBin;
use Carp qw(croak);
use lib "$FindBin::Bin";
use Sg;
use SgSynth;
use SgXtable;
use Template;

#invokes chipscope to create the EDIF netlist
sub createChipscopeEdifs {
   my($target_directory, $tkv) = @_;
   my $ila = $target_directory.'/ila.arg';
   my $icon = $target_directory.'/icon.arg';
   open (ILA, ">$ila");
   open (ICON, ">$icon");
   print ILA $tkv->{"ila"};
   print ICON $tkv->{"icon"};
   close(ILA);
   close(ICON);
    
   #Create chipscope generator command
   my($generate) = $tkv->{"chipScopeLocation"};   
   my($ila_cmd) = "\"$generate\" ila_pro -f=\"$target_directory/ila.arg\" > chipscope_ila.log";
   my($icon_cmd) = "\"$generate\" icon_pro -f=\"$target_directory/icon.arg\" > chipscope_icon.log";
   
   # run the ChipScope core generator
   my $rval;
   $rval = &Sg::runcmd($generate, 'ila_pro', "-f=$target_directory/ila.arg", "2>&1", ">chipscope_ila.log");
   if ($rval) {
       croak "'$generate' exited with $rval, see chipscope_ila.log";
   }
   &Sg::runcmd($generate, 'icon_pro', "-f=$target_directory/icon.arg", "2>&1", ">chipscope_icon.log");
   if ($rval) {
       croak "'$generate' exited with $rval, see chipscope_icon.log";
   }
   
   copyordie(buildPath($target_directory, 'ila.edn'), buildPath($target_directory, '/sysgen/ila.edn'));
   copyordie(buildPath($target_directory, 'icon.edn'), buildPath($target_directory, 'sysgen/icon.edn'));
}

#Serialize the CDC file information
sub createCDCFile{
  my($target_directory, $tkv) = @_;
  my $cdc_file_name = $tkv->{"BlockName"};
  $cdc_file_name = $target_directory.'\\'.$cdc_file_name.'.cdc';
  $cdc_file_name =~ s/\\+/\//g;
  open (CDC, ">$cdc_file_name");
  print CDC "$tkv->{\"cdc\"}";
  close(CDC);  
}

#Use the entity, conv_pkg.vhd and the chipscope pro outputs to create the ngc list
sub makeChipscopeNgc{
  my($target_directory, $tkv) = @_;
  my $temp_directory = $target_directory.'/sysgen';

  #run synthesis to create the ngc netlist
  my($synthTool) = $tkv->{"xlSynthTool"};
  my($sysgenDir) = $tkv->{"sysgen_root"};
  my($design) =  $tkv->{"entity_name"};   
  
     
  # Results from synthesis are stored in this file.
  my($logFile) = "$design.xst_results";
  my($prjFile) = "xst_$design.prj"; 

  # Invoke perl script that creates XST project/script files  

  my($files) = [];
  push(@$files,"ila.edn");
  push(@$files,"icon.edn");
  push(@$files,"xlchipscope.vhd");
  my $clkWrapper = $design;
  my $ngc_instrs = {
    "synthesisTool" => "XST",
    "design" => $design,
    "files" => $files,
    "clkWrapper" => $clkWrapper,
    "freq" => 10,
    "hdlKind" => "vhdl",
    "omitIobs" => 1,
    "xcfFile" => undef,
    "ncfFile" =>undef,
    "exposeStdStreams" => 0,
    "omitCores" => 1,
  };   
  if (!chdir($temp_directory)) {
    croak("Could not change directory to $temp_directory");
  }
  
  if ($design eq "xlchipscope") {
    &SgXst::makeXstFiles($ngc_instrs);    
    my($xst) = &which("xst.exe");
    defined($xst) || croak("XST is not in your path");    
    &Sg::runcmd('xst', '-ifn', "xst_$design.scr", '-ofn', $logFile, "2>&1", '>xst.results');
  }

  copyordie(buildPath($temp_directory, 'xlchipscope.ngc'), buildPath($target_directory, 'xlchipscope.ngc'));
}

#
# Calls the Template::Toolkit text processor to process and deliver a file.
#
# @param $sf Name of the source file.
# @param $df Name of the destination file (undef if we should use a default).
#        Relative names are interpreted with respect to the target directory.
# @param %$templateKeyValues Keys/values that should be used by the text 
#        processor.
#
# @return Name of the file that was created if text processing results were
#         stored in an individual file; undef if results were adjoined to a
#         shared file.
#
sub callTextProcessor {
  my($sf, $df, $templateKeyValues) = @_;

  # Read source file into local array.
  open(SOURCE, "< $sf") || croak "Couldn't open $sf.\n";
  my @text = <SOURCE>;
  close(SOURCE) || croak "Trouble reading $sf.\n";
  chomp(@text);

  # Process source file text with Template::Toolkit text processor, using
  # template key/values as Template::Toolkit symbol table.
  my @newText;
  my $textString = join("\n", @text);
  my $tt = Template->new({OUTPUT => \@newText});
  $tt->process(\$textString, $templateKeyValues) || croak "$tt->error\n";

  # Write text processor results to the appropriate file handle.
  my($handle, $destFile) = &Sg::getFileHandle($sf, $df);
  my $line;
  foreach $line (@newText) {
    print $handle "$line\n";
  }

  # Close file handle and report name of delivered file if appropriate.
  if ($destFile) {
    close($handle) || croak "Trouble delivering $destFile\n";
    return $destFile;
  }

  $handle->flush;
  return undef;
}

sub wrapup {
    my $results = ["xlchipscope.ngc","ila.edn","icon.edn"];
    return $results;
}
sub deliverChipscopeFile {
 my $instrs = $_[0];   
 #Extract deliver File information
 my $target_directory =  $instrs->{"directory"}; 
 my $tkv = $instrs->{"templateKeyValues"}; 
 my $design  =  $instrs->{"entity_name"}; 
 my $df_shell = $target_directory.'\\'.$design.'.vhd';

 
 
 #Create shell for modelsim
 my $trig_port_info = $instrs->{"trig_port_info"};
 my $data_port_info = $instrs->{"data_port_info"};   
 my @text = (
	    "library IEEE;\n",
	    "use IEEE.std_logic_1164.all;\n",	    
	    "entity $design is\n",
	    "    port (\n",
	    "$trig_port_info",
	    "$data_port_info",
	    "          ce       : in std_logic;\n",
	    "          clr      : in std_logic;\n",
	    "          clk      : in std_logic);\n",
	    "end $design;\n",
	    "-- synopsys translate_off\n",
	    "architecture behavior of $design is\n",
	    "begin\n",
	    "end  behavior;\n",
	    "-- synopsys translate_on\n");

 my $toplevellanguage = $instrs->{"TopLevelLanguage"};

 if ($toplevellanguage eq "Verilog") {
	my $verilog_module_ports = $instrs->{"verilog_module_ports"};
	my $verilog_trig_port_info = $instrs->{"verilog_trig_port_info"};
	my $verilog_data_port_info = $instrs->{"verilog_trig_port_info"};    
	@text = (
	    "module $design $verilog_module_ports\n",	    
	    "$verilog_trig_port_info",
	    "$verilog_data_port_info",
	    " input         ce;\n",
	    " input         clr;\n",
	    " input         clk;\n",	    
	    "-- synopsys translate_off\n",	    	    	    
	    "-- synopsys translate_on\n",
	    "endmodule;\n"
	);
	    
  $df_shell = $target_directory.'\\'.$design.'.v';
 }

 open(SHELL_FILE,">$df_shell");
 print SHELL_FILE @text; 
 close(SHELL_FILE);

  
 my $df = $target_directory.'\\sysgen\\'.$design.'.vhd';
 $instrs->{"destFile"} = $df;
 my $sf = &Sg::makeSourceFileCanonical($instrs->{"sourceFile"});
 $instrs->{"sourceFile"} = $sf;
 $target_directory =~ s/\\+/\//g;
 #if the sysgen directory does not exist create it
 if (! -d "$target_directory/sysgen" ) {
    mkdir("$target_directory/sysgen") || croak("Could not create the sysgen directory");
 }
 &callTextProcessor($sf,$df,$instrs);

 chdir("$target_directory/sysgen") || croak("Could not change to the sysgen directory");

 &createChipscopeEdifs($target_directory, $instrs);

 &createCDCFile($target_directory, $instrs);

 &makeChipscopeNgc($target_directory, $instrs);

 return undef;

}

1;
