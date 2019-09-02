package com.xtc.libthai.seanlp.segmenter.matcher;

import com.xtc.libthai.seanlp.segmenter.domain.Term;

import java.util.List;

/**
 * 词典匹配算法接口
 */
public interface Matcher {
    List<Term> segment(String[] var1);

    List<Term> segment(String var1);

    List<Term> segment(char[] var1);
}
