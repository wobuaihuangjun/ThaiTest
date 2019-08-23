package com.xtc.libthai.segmenter;


import com.xtc.libthai.Config;
import com.xtc.libthai.Language;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.CC.TCC;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractThaiSegmenter extends AbstractSegmenter implements ThaiSegmenter {
    public AbstractThaiSegmenter() {
        Config.BaseConf.seletcLanguage(Language.Thai);
    }

    protected String[] toTCC(String sentence) {
        return TCC.toTCC(sentence);
    }

    protected abstract String syllableSegment(String[] var1);

    protected abstract StringBuffer syllableSegment(char[] var1);

    protected abstract String[] sentenceTosyllables(char[] var1);

    protected abstract String[] sentenceTosyllables(String var1);

    protected abstract List<Term> syllableMerge(String[] var1);

    protected abstract List<Term> syllableMerge(String var1);

    protected abstract List<Term> syllableMerging(String[] var1);

    protected abstract List<Term> gCRFWordSegment(String[] var1);

    protected abstract List<Term> segment(String[] var1);

    protected abstract List<Term> segment(char[] var1);

    protected abstract List<Term> gCRFWordSegment(char[] var1);

    public List<Term> segmentSentence(String sentence) {
        return this.segment(sentence.toCharArray());
    }

    protected List<Term> sentenceMerge(String[] sentences) {
        List<Term> termList = new LinkedList();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            termList.addAll(this.segmentSentence(sentences[i]));
        }

        return termList;
    }

    public List<Term> segment(String text) {
        return this.sentenceMerge(this.sentenceSegment(text));
    }
}
