package com.xtc.libthai.tokenizer;

import com.xtc.libthai.segmenter.Segmenter;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.matcher.language.ThaiCustomMatchSegmenter;

import java.util.List;

public class ThaiCustomMatchTokenizer {

    public final static Segmenter customMatchSegmenter = new ThaiCustomMatchSegmenter();

    public static List<Term> customMaxSegment(String text) {
        return customMatchSegmenter.segment(text);
    }
}
