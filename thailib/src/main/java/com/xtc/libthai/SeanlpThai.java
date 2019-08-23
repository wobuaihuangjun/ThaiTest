package com.xtc.libthai;

import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.tokenizer.ThaiCRFTokenizer;
import com.xtc.libthai.tokenizer.ThaiMatchTokenizer;

import java.util.List;

import cn.edu.kmust.seanlp.extractor.domain.KeyTerm;
import cn.edu.kmust.seanlp.extractor.language.ThaiExtractor;
import cn.edu.kmust.seanlp.similarity.language.ThaiSentenceSimilarity;
import cn.edu.kmust.seanlp.tokenizer.KCCTokenizer;
import cn.edu.kmust.seanlp.tokenizer.ThaiDATTokenizer;


public class SeanlpThai {

    /**
     * CRF泰语音节切分
     * @param text
     * @return
     */
    public static String syllableSegment(String text) {
        return ThaiCRFTokenizer.syllableSegment(text);
    }

    public static String maxSegmentText(String text) {
        List<Term> terms = maxSegment(text);
        String line = "";

        for (int i = 0; i< terms.size(); i++) {
            line = line + terms.get(i).getWord() + "|";
        }
        return line;
    }

    public static List<cn.edu.kmust.seanlp.segmenter.domain.Term> segmentTCC(String text) {
        return KCCTokenizer.segment(text);
    }

    public static List<cn.edu.kmust.seanlp.segmenter.domain.Term> datSegment(String text) {
        return ThaiDATTokenizer.segment(text);
    }

    public static List<Term> dCRFSegment(String text) {
        return ThaiCRFTokenizer.segment(text);
    }

    public static List<Term> gCRFSegment(String text) {
        return ThaiCRFTokenizer.segment(text);
    }

    public static List<Term> maxSegment(String text) {
        return ThaiMatchTokenizer.maxSegment(text);
    }

    public static List<Term> minSegment(String text) {
        return ThaiMatchTokenizer.minSegment(text);
    }

    public static List<Term> reMaxSegment(String text) {
        return ThaiMatchTokenizer.rMaxSegment(text);
    }

    public static List<Term> reMinSegment(String text) {
        return ThaiMatchTokenizer.rMinSegment(text);
    }

    public static List<KeyTerm> extractKeyword(String document, int size) {
        return ThaiExtractor.extractKeyword(document, size);
    }

    public static List<String> extractSummary(String document, int size) {
        return ThaiExtractor.extractSummary(document, size);
    }

    public static String getSummary(String document, int max_length) {
        return ThaiExtractor.getSummary(document, max_length);
    }

    public static double sentenceSimilarity(String sent1, String sent2) {
        return ThaiSentenceSimilarity.getSimilarity(sent1, sent2);
    }
}
