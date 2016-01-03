#################################################################
# Copied from coregen/tcl/ipgui-init.tcl
# This is a work-around to support
# mult_gen_v11_0_utils::mult_gen_v11_0_calc_fully_pipelined_latency

# DerivateFamily
#   Is a derivate family ?
# Arguments:
#   sub family super family
#  as string 
# Returns:
#   bool
#
#################################################################

# Need to add to this and tidy is up but the idea is we 
# return true if super derivatefamily of sub.
#
proc IsDerivedFrom { sub super } {

    # make $sub and $super case-insensitive by lowercasing
    set sub [string tolower $sub]
    set super [string tolower $super]

    set GENERIC       "generic"
    set X4K           "x4k"
    set X4KE          "x4ke"
    set X4KL          "x4kl"
    set X4KEX         "x4kex"
    set X4KXL         "x4kxl"
    set X4KXV         "x4kxv"
    set X4KXLA        "x4kxla"
    set SPARTAN       "spartan"
    set SPARTANXL     "spartanxl"
    set SPARTAN2      "spartan2"
    set SPARTAN2E     "spartan2e"
    set VIRTEX        "virtex"
    set VIRTEXE       "virtexe"
    set VIRTEX2       "virtex2"
    set VIRTEX2P      "virtex2p"
    set SPARTAN3      "spartan3"
    set SPARTAN3E     "spartan3e"
    set VIRTEX4       "virtex4"
    set QRVIRTEX      "qrvirtex"
    set QRVIRTEX2     "qrvirtex2"
    set QVIRTEX       "qvirtex"
    set QVIRTEX2      "qvirtex2"
    set QVIRTEXE      "qvirtexe"
    set VIRTEX5       "virtex5"
    set VIRTEX6       "virtex6"
    set VIRTEX6L      "virtex6L"
    set ASPARTAN3     "aspartan3"
    set ASPARTAN3E    "aspartan3e"
    set SPARTAN3A     "spartan3a"
    set AVIRTEX4      "avirtex4"
    set SPARTAN3ADSP  "spartan3adsp"
    set ASPARTAN3A    "aspartan3a"
    set ASPARTAN3ADSP "aspartan3adsp"
    set SPARTAN6      "spartan6"
    set QVIRTEX4      "qvirtex4"
    set QRVIRTEX4     "qrvirtex4"

    # Derived rules: read right-to-left!
    lappend derivedRules [list $GENERIC $X4K $SPARTAN $SPARTANXL]
    lappend derivedRules [list $GENERIC $X4K $X4KE $X4KL]
    lappend derivedRules [list $GENERIC $X4K $X4KEX $X4KXL $X4KXV $X4KXLA]
    lappend derivedRules [list $GENERIC $VIRTEX $SPARTAN2 $SPARTAN2E]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEXE]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX2 $SPARTAN3 $SPARTAN3E $ASPARTAN3E]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX2 $SPARTAN3 $SPARTAN3A $ASPARTAN3A $SPARTAN3ADSP $ASPARTAN3ADSP]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX2 $SPARTAN3 $ASPARTAN3]
    lappend derivedRules [list $GENERIC $VIRTEX $SPARTAN6] 
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX2 $VIRTEX2P]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX4 $AVIRTEX4 $QVIRTEX4 $QRVIRTEX4]
    lappend derivedRules [list $GENERIC $VIRTEX $QRVIRTEX]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX2 $QRVIRTEX2]
    lappend derivedRules [list $GENERIC $VIRTEX $QVIRTEX]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX2 $QVIRTEX2]
    lappend derivedRules [list $GENERIC $VIRTEX $QVIRTEXE]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX5]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX6]
    lappend derivedRules [list $GENERIC $VIRTEX $VIRTEX6L]


    foreach rule $derivedRules {

      set subIndex [lsearch $rule $sub] ;# find index of $sub in list

      if { $subIndex >= 0 } { ;# if $sub exists

         set superIndex [lsearch $rule $super] ;# find index of $super in list

         if { $superIndex >= 0 } { ;# if $super exists

            if {$subIndex >= $superIndex} {
               ;# $sub is derived from $super
               return true
            } else {
            	return false
            }
         } 

      } ;# end if
    } ;# end for
  
    return false
}
