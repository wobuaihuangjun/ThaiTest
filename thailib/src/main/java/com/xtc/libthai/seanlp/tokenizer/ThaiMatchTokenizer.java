package com.xtc.libthai.seanlp.tokenizer;

import com.xtc.libthai.seanlp.segmenter.Segmenter;
import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.segmenter.matcher.language.ThaiMaxMatchSegmenter;
import com.xtc.libthai.seanlp.segmenter.matcher.language.ThaiMinMatchSegmenter;
import com.xtc.libthai.seanlp.segmenter.matcher.language.ThaiRevMaxMatchSegmenter;
import com.xtc.libthai.seanlp.segmenter.matcher.language.ThaiRevMinMatchSegmenter;

import java.util.List;

/**
 * 泰语正逆向匹配分词器
 */
public class ThaiMatchTokenizer {

    /**
     * 预置分词器
     */
    public final static Segmenter maxMatchThaiSegment = new ThaiMaxMatchSegmenter();
    public final static Segmenter minMatchThaiSegment = new ThaiMinMatchSegmenter();
    public final static Segmenter rMaxMatchThaiSegment = new ThaiRevMaxMatchSegmenter();
    public final static Segmenter rMinMatchThaiSegment = new ThaiRevMinMatchSegmenter();

    /**
     * 正向最大分词
     */
    public static List<Term> maxSegment(String text) {
        return maxMatchThaiSegment.segment(text);
    }

    /**
     * 正向最小分词
     */
    public static List<Term> minSegment(String text) {
        return minMatchThaiSegment.segment(text);
    }

    /**
     * 逆向最大分词
     */
    public static List<Term> rMaxSegment(String text) {
        return rMaxMatchThaiSegment.segment(text);
    }

    /**
     * 逆向最小分词
     */
    public static List<Term> rMinSegment(String text) {
        return rMinMatchThaiSegment.segment(text);
    }


}
