package com.xtc.libthai.tokenizer;

import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.CRF.ThaiCRFSegmenter;
import com.xtc.libthai.segmenter.ThaiSegmenter;

import java.util.List;

public class ThaiCRFTokenizer {

    public static final ThaiSegmenter crfThaiSegment = new ThaiCRFSegmenter();

    public ThaiCRFTokenizer() {
    }

    public static List<Term> segment(String text) {
        return crfThaiSegment.segment(text);
    }

    public static List<Term> gCRFSegment(String text) {
        return crfThaiSegment.gCRFWordSegment(text);
    }

    public static String syllableSegment(String text) {
        return crfThaiSegment.syllableSegment(text);
    }

}
