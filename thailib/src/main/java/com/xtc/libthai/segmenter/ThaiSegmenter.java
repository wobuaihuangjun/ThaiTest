package com.xtc.libthai.segmenter;

import com.xtc.libthai.segmenter.domain.Term;

import java.util.List;

public interface ThaiSegmenter extends Segmenter {
    String syllableSegment(String var1);

    List<Term> dCRFWordSegment(String var1);

    List<Term> gCRFWordSegment(String var1);

    List<Term> seg(String var1);

    List<Term> segmentSentence(String var1);
}
