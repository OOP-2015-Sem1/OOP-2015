$memmap_info.toregs:{'sm_$it.name$_din' => $it.n_bits$,
'sm_$it.name$_en' => 0,};separator="\n"$
$memmap_info.fromfifos:{'sm_$it.name$_re' => 0,};separator="\n"$
$memmap_info.tofifos:{'sm_$it.name$_din' => $it.n_bits$,
'sm_$it.name$_we' => 0,};separator="\n"$
$memmap_info.shmems:{'sm_$it.name$_addr' => $it.width$,
'sm_$it.name$_din' => $it.n_bits$,
'sm_$it.name$_we' => 0,
'sm_$it.name$_en' => 0,};separator="\n"$
$if(memmap_info.shmems)$'shram_en' => 0, $endif$
