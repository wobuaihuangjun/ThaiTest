package com.xtc.libthai.seanlp.segmenter.matcher.language;

import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.dictionary.language.ThaiCoreDictionary;
import com.xtc.libthai.seanlp.pos.POS;
import com.xtc.libthai.seanlp.pos.ThaiPOS;
import com.xtc.libthai.seanlp.segmenter.AbstractThaiSegmenter;
import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.segmenter.matcher.Matcher;
import com.xtc.libthai.seanlp.segmenter.matcher.MaximumMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 泰语正向最大匹配算法分词
 *
 */
public class ThaiMaxMatchSegmenter extends AbstractThaiSegmenter {
    private Matcher maxMatcher;
    private POS pos;

    public ThaiMaxMatchSegmenter() {
        this.maxMatcher = new MaximumMatcher(ThaiCoreDictionary.thaiDictionary.dictionaryTrie);
        this.pos = new ThaiPOS();
    }

    @Override
    protected List<Term> sentenceMerge(String[] sentences) {
        List<Term> terms = new ArrayList<>();
        int len = sentences.length;

        for(int i = 0; i < len; ++i) {
            String string = sentences[i];
//            System.out.println("11: " + string);

            String[] d = toTCC(string);
//            System.out.println("12: " +  Arrays.toString(d));

            List<Term> a = segment(d);
//            System.out.println("13: " + a);

            terms.addAll(a);
        }

        return terms;
    }

    @Override
    protected List<Term> segment(String[] strs) {
        List<Term> ret = maxMatcher.segment(strs);
//        System.out.println("14: " + ret);
        if (Config.BaseConf.speechTagging) {
            ret = pos.speechTagging(ret);
        }

        return ret;
    }

    @Override
    protected List<Term> segment(char[] chars) {
        List<Term> ret = maxMatcher.segment(chars);
        if (Config.BaseConf.speechTagging) {
            ret = pos.speechTagging(ret);
        }
        return ret;
    }

    @Override
    public List<Term> segment(String text) {
        return sentenceMerge(sentenceSegment(text));
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

