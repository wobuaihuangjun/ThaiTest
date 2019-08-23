package com.xtc.libthai.segmenter;


import com.xtc.libthai.segmenter.domain.Term;

import java.util.List;

public interface Segmenter {
    List<Term> segment(String var1);
}
