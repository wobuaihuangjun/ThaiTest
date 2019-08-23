package com.xtc.libthai.segmenter.matcher.language;

import com.xtc.libthai.Config;
import com.xtc.libthai.dictionary.language.ThaiCoreDictionary;
import com.xtc.libthai.pos.POS;
import com.xtc.libthai.pos.ThaiPOS;
import com.xtc.libthai.segmenter.AbstractThaiSegmenter;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.matcher.Matcher;
import com.xtc.libthai.segmenter.matcher.ReverseMaximumMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThaiRevMaxMatchSegmenter extends AbstractThaiSegmenter {
    private Matcher revMaxMatcher;
    private POS pos;

    public ThaiRevMaxMatchSegmenter() {
        this.revMaxMatcher = new ReverseMaximumMatcher(ThaiCoreDictionary.thaiDictionary.dictionaryTrie);
        this.pos = new ThaiPOS();
    }

    protected List<Term> segment(String[] sentence) {
        List<Term> ret = this.revMaxMatcher.segment(sentence);
        if (Config.BaseConf.speechTagging) {
            ret = this.pos.speechTagging(ret);
        }

        return ret;
    }

    protected List<Term> sentenceMerge(String[] sentences) {
        List<Term> terms = new ArrayList();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            terms.addAll(this.segment(this.toTCC(sentences[i])));
        }

        return terms;
    }

    public List<Term> segment(String text) {
        return this.sentenceMerge(this.sentenceSegment(text));
    }

    public static void main(String[] args) {
        Config.BaseConf.enableDebug();
        String text = "ความสัมพันธ์ในทางเศรษฐกิจกับระบบความสัมพันธ์ทางกฎหมาย";
        ThaiRevMaxMatchSegmenter seg = new ThaiRevMaxMatchSegmenter();
        System.out.println(seg.segment(text));
        String line = "";

        Term term;
        for(Iterator var4 = seg.segment(text).iterator(); var4.hasNext(); line = line + term.getWord() + "|") {
            term = (Term)var4.next();
        }

        System.err.println(text);
        System.out.println(line);
    }

    public String syllableSegment(String text) {
        return null;
    }

    public List<Term> dCRFWordSegment(String text) {
        return null;
    }

    public List<Term> gCRFWordSegment(String text) {
        return null;
    }

    public List<Term> seg(String text) {
        return null;
    }

    protected String syllableSegment(String[] sentences) {
        return null;
    }

    protected StringBuffer syllableSegment(char[] chars) {
        return null;
    }

    protected String[] sentenceTosyllables(char[] chars) {
        return null;
    }

    protected String[] sentenceTosyllables(String sentence) {
        return null;
    }

    protected List<Term> syllableMerge(String[] sentences) {
        return null;
    }

    protected List<Term> syllableMerge(String sentence) {
        return null;
    }

    protected List<Term> syllableMerging(String[] syllables) {
        return null;
    }

    protected List<Term> gCRFWordSegment(String[] sentences) {
        return null;
    }

    protected List<Term> segment(char[] chars) {
        return null;
    }

    protected List<Term> gCRFWordSegment(char[] chars) {
        return null;
    }
}
