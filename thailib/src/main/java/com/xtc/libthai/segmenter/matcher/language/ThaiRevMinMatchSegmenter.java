package com.xtc.libthai.segmenter.matcher.language;

import com.xtc.libthai.Config;
import com.xtc.libthai.dictionary.language.ThaiCoreDictionary;
import com.xtc.libthai.pos.POS;
import com.xtc.libthai.pos.ThaiPOS;
import com.xtc.libthai.segmenter.AbstractThaiSegmenter;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.segmenter.matcher.Matcher;
import com.xtc.libthai.segmenter.matcher.ReverseMinimumMatcher;

import java.util.ArrayList;
import java.util.List;


/**
 * 泰语逆向最小匹配算法分词
 */
public class ThaiRevMinMatchSegmenter extends AbstractThaiSegmenter {

    private Matcher revMinMatcher = new ReverseMinimumMatcher(ThaiCoreDictionary.thaiDictionary.dictionaryTrie);
    private POS pos = new ThaiPOS();

    protected List<Term> segment(String[] sentence) {
        List<Term> ret = revMinMatcher.segment(sentence);
        if (Config.BaseConf.speechTagging) {
            ret = pos.speechTagging(ret);
        }
        return ret;
    }

    @Override
    protected List<Term> sentenceMerge(String[] sentences) {
        List<Term> terms = new ArrayList<>();
        int len = sentences.length;
        for (int i = 0; i < len; i++) {
            terms.addAll(segment(toTCC(sentences[i])));
        }
        return terms;
    }

    @Override
    public List<Term> segment(String text) {
        return sentenceMerge(sentenceSegment(text));
    }

    public static void main(String[] args) {
        Config.BaseConf.enableDebug();
        String text = "ความสัมพันธ์ในทางเศรษฐกิจกับระบบความสัมพันธ์ทางกฎหมาย";
        ThaiRevMinMatchSegmenter seg = new ThaiRevMinMatchSegmenter();
        System.out.println(seg.segment(text));
        String line = "";
        for (Term term : seg.segment(text)) {
            line += term.getWord() + "|";
        }
        System.err.println(text);
        System.out.println(line.replaceAll("\\|", ""));

    }

    @Override
    public String syllableSegment(String text) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Term> dCRFWordSegment(String text) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Term> gCRFWordSegment(String text) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Term> seg(String text) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String syllableSegment(String[] sentences) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected StringBuffer syllableSegment(char[] chars) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] sentenceTosyllables(char[] chars) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] sentenceTosyllables(String sentence) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Term> syllableMerge(String[] sentences) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Term> syllableMerge(String sentence) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Term> syllableMerging(String[] syllables) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Term> gCRFWordSegment(String[] sentences) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Term> segment(char[] chars) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Term> gCRFWordSegment(char[] chars) {
        // TODO Auto-generated method stub
        return null;
    }

}
