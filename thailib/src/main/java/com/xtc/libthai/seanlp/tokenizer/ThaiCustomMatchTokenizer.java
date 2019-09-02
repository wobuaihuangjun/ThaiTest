package com.xtc.libthai.seanlp.tokenizer;

import com.xtc.libthai.seanlp.segmenter.Segmenter;
import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.segmenter.matcher.language.ThaiCustomMatchSegmenter;

import java.util.List;

public class ThaiCustomMatchTokenizer {

    public final static Segmenter customMatchSegmenter = new ThaiCustomMatchSegmenter();

    public static List<Term> customMaxSegment(String text) {
        return customMatchSegmenter.segment(text);
    }
}
