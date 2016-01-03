puts "vector adder"

namespace eval AccelTest {
    namespace eval Design {

	set __baseName vector_add

	proc GetScriptFile {} {
	    return "$AccelTest::Design::__baseName\_script.m"
	}
	
	proc GetDesignFunction {} {
	    return "for n.$AccelTest::Design::__baseName"
	}
	
	proc GetProjectFile {} {
	    return "$AccelTest::Design::__baseName.acc"
	}
	
	proc GetMFile {} {
	    return "$AccelTest::Design::__baseName.m"
	}
	
	proc GetDesignRoot {} {
	    return "T:/TestCases/SystemGenerator/blocks/acceldsp_vector_adder"
	}
    }
}
