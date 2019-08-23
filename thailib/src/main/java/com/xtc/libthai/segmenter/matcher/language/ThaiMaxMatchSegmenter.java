package com.xtc.libthai.segmenter.matcher.language;

import com.xtc.libthai.Config;
import com.xtc.libthai.dictionary.language.ThaiCoreDictionary;
import com.xtc.libthai.pos.POS;
import com.xtc.libthai.pos.ThaiPOS;
import com.xtc.libthai.segmenter.AbstractThaiSegmenter;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.matcher.Matcher;
import com.xtc.libthai.segmenter.matcher.MaximumMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThaiMaxMatchSegmenter extends AbstractThaiSegmenter {
    private Matcher maxMatcher;
    private POS pos;

    public ThaiMaxMatchSegmenter() {
        this.maxMatcher = new MaximumMatcher(ThaiCoreDictionary.thaiDictionary.dictionaryTrie);
        this.pos = new ThaiPOS();
    }

    protected List<Term> segment(String[] strs) {
        List<Term> ret = this.maxMatcher.segment(strs);
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

    protected List<Term> segment(char[] chars) {
        List<Term> ret = this.maxMatcher.segment(chars);
        if (Config.BaseConf.speechTagging) {
            ret = this.pos.speechTagging(ret);
        }

        return ret;
    }

    public List<Term> segment(String text) {
        return this.sentenceMerge(this.sentenceSegment(text));
    }

    public static void main(String[] args) {
        Config.BaseConf.enableDebug();
        String text = "ความสัมพันธ์ในทางเศรษฐกิจกับระบบความสัมพันธ์ทางกฎหมาย";
        ThaiMaxMatchSegmenter seg = new ThaiMaxMatchSegmenter();
        List<Term> terms = seg.segment(text);
        System.out.println("1: " + terms);
        String line = "";

        for (int i = 0; i< terms.size(); i++) {
            line = line + terms.get(i).getWord() + "|";
        }
        System.err.println("2: " + text);
        System.out.println("3: " + line);
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

    protected List<Term> gCRFWordSegment(char[] chars) {
        return null;
    }
}

