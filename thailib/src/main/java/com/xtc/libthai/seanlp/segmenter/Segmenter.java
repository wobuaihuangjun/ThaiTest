package com.xtc.libthai.seanlp.segmenter;


import com.xtc.libthai.seanlp.segmenter.domain.Term;

import java.util.List;

/**
 * 分词器接口</br>
 * 所有分词器的接口
 */
public interface Segmenter {

    /**
     * 分词接口
     *
     * @param sentence 带分词的句子
     */
    List<Term> segment(String sentence);
}
