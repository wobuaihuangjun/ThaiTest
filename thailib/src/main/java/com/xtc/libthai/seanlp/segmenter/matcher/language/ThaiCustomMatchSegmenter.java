package com.xtc.libthai.seanlp.segmenter.matcher.language;

import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.dictionary.language.ThaiCustomDictionary;
import com.xtc.libthai.seanlp.segmenter.AbstractThaiSegmenter;
import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.segmenter.matcher.Matcher;
import com.xtc.libthai.seanlp.segmenter.matcher.ThaiCustomMatcher;

import java.util.ArrayList;
import java.util.List;

public class ThaiCustomMatchSegmenter extends AbstractThaiSegmenter {

    private Matcher maxMatcher;

    public ThaiCustomMatchSegmenter() {
        this.maxMatcher = new ThaiCustomMatcher(ThaiCustomDictionary.customDictionary.customTrie);
        enablePartOfSpeechTagging(false);//关闭词性
    }

    @Override
    protected List<Term> sentenceMerge(String[] sentences) {
        List<Term> terms = new ArrayList<>();
        int len = sentences.length;
        long start = System.currentTimeMillis();
        for(int i = 0; i < len; ++i) {
            String string = sentences[i];
//            System.out.println("11: " + string + "，time cost1: " + (System.currentTimeMillis() - start));

            String[] d = toTCC(string);
//            System.out.println("12: " +  Arrays.toString(d)+ "，time cost1: " + (System.currentTimeMillis() - start));

            List<Term> a = segment(d);
//            System.out.println("13: " + a+ "，time cost1: " + (System.currentTimeMillis() - start));

            terms.addAll(a);
        }

        return terms;
    }

    @Override
    protected List<Term> segment(String[] strs) {
        return maxMatcher.segment(strs);
    }

    @Override
    protected List<Term> segment(char[] chars) {
        return maxMatcher.segment(chars);
    }

    @Override
    public List<Term> segment(String text) {
        return this.sentenceMerge(this.sentenceSegment(text));
    }

    public static void main(String[] args) {
        Config.BaseConf.enableDebug();

        String text = "คุณจะเสียสิทธิ์การเป็นแอดมินนาฬิกาไปหลังเปลี่ยนแอดมิน ต้องการเปลี่ยนแอดมินหรือไม่";
        ThaiCustomMatchSegmenter seg = new ThaiCustomMatchSegmenter();
        List<Term> terms = seg.segment(text);
//        System.out.println("1: " + terms);
        String line = "";

        for (int i = 0; i< terms.size(); i++) {
            line = line + terms.get(i).getWord() + "|";
        }
//        System.out.println("2: " + text);
//        System.out.println("3: " + line);
    }

    @Override
    protected String syllableSegment(String[] var1) {
        return null;
    }

    @Override
    protected StringBuffer syllableSegment(char[] var1) {
        return null;
    }

    @Override
    protected String[] sentenceTosyllables(char[] var1) {
        return new String[0];
    }

    @Override
    protected String[] sentenceTosyllables(String var1) {
        return new String[0];
    }

    @Override
    protected List<Term> syllableMerge(String[] var1) {
        return null;
    }

    @Override
    protected List<Term> syllableMerge(String var1) {
        return null;
    }

    @Override
    protected List<Term> syllableMerging(String[] var1) {
        return null;
    }

    @Override
    protected List<Term> gCRFWordSegment(String[] var1) {
        return null;
    }

    @Override
    protected List<Term> gCRFWordSegment(char[] var1) {
        return null;
    }

    @Override
    public String syllableSegment(String var1) {
        return null;
    }

    @Override
    public List<Term> dCRFWordSegment(String var1) {
        return null;
    }

    @Override
    public List<Term> gCRFWordSegment(String var1) {
        return null;
    }

    @Override
    public List<Term> seg(String var1) {
        return null;
    }
}
