package com.xtc.libthai.segmenter;

import com.xtc.libthai.segmenter.domain.Term;

import java.util.List;

/**
 * 泰语分词器接口
 *
 */
public interface ThaiSegmenter extends Segmenter {

    /**
     * 泰语音节切分接口
     * @param sentence 带切分的句子
     * @return String
     */
    String syllableSegment(String sentence);

    /**
     * 双层条件随机场泰语分词
     * @param sentence 待分词的句子
     * @return List<Term>
     */
    List<Term> dCRFWordSegment(String sentence);

    /**
     * 单层条件随机场泰语分词
     * @param sentence 待分词的句子
     * @return List<Term>
     */
    List<Term> gCRFWordSegment(String sentence);

    /**
     * 音节切分和分词同时完成
     * @param text
     * @return
     */
    List<Term> seg(String text);

    /**
     * 对句子进行分词
     * @param sentence
     * @return
     */
    public List<Term> segmentSentence(String sentence);

}
