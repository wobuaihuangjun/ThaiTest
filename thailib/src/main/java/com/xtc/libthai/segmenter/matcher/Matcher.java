package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.segmenter.domain.Term;

import java.util.List;

public interface Matcher {
    List<Term> segment(String[] var1);

    List<Term> segment(String var1);

    List<Term> segment(char[] var1);
}
