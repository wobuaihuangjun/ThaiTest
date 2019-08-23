package com.xtc.libthai.tokenizer;

import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.Segmenter;
import com.xtc.libthai.segmenter.matcher.language.ThaiMaxMatchSegmenter;
import com.xtc.libthai.segmenter.matcher.language.ThaiMinMatchSegmenter;
import com.xtc.libthai.segmenter.matcher.language.ThaiRevMaxMatchSegmenter;
import com.xtc.libthai.segmenter.matcher.language.ThaiRevMinMatchSegmenter;

import java.util.List;

public class ThaiMatchTokenizer {
    public static final Segmenter maxMatchThaiSegment = new ThaiMaxMatchSegmenter();
    public static final Segmenter minMatchThaiSegment = new ThaiMinMatchSegmenter();
    public static final Segmenter rMaxMatchThaiSegment = new ThaiRevMaxMatchSegmenter();
    public static final Segmenter rMinMatchThaiSegment = new ThaiRevMinMatchSegmenter();

    public ThaiMatchTokenizer() {
    }

    public static List<Term> maxSegment(String text) {
        return maxMatchThaiSegment.segment(text);
    }

    public static List<Term> minSegment(String text) {
        return minMatchThaiSegment.segment(text);
    }

    public static List<Term> rMaxSegment(String text) {
        return rMaxMatchThaiSegment.segment(text);
    }

    public static List<Term> rMinSegment(String text) {
        return rMinMatchThaiSegment.segment(text);
    }
}
