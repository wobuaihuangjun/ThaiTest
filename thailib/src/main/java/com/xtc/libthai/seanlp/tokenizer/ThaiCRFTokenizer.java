package com.xtc.libthai.seanlp.tokenizer;

import com.xtc.libthai.seanlp.segmenter.CRF.ThaiCRFSegmenter;
import com.xtc.libthai.seanlp.segmenter.ThaiSegmenter;
import com.xtc.libthai.seanlp.segmenter.domain.Term;

import java.util.List;

/**
 * 泰语CRF分词器
 */
public class ThaiCRFTokenizer {

    /**
     * 预置分词器
     */
    public final static ThaiSegmenter crfThaiSegment = new ThaiCRFSegmenter();

    /**
     * 层叠CRF分词
     */
    public static List<Term> segment(String text) {
        return crfThaiSegment.segment(text);
    }

    /**
     * 单层CRF分词
     */
    public static List<Term> gCRFSegment(String text) {
        return crfThaiSegment.gCRFWordSegment(text);
    }

    /**
     * CRF音节切分
     */
    public static String syllableSegment(String text) {
        return crfThaiSegment.syllableSegment(text);
    }

}
