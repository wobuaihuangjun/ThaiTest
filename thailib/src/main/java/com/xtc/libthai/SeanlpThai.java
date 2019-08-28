package com.xtc.libthai;

import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.tokenizer.ThaiCRFTokenizer;
import com.xtc.libthai.tokenizer.ThaiMatchTokenizer;

import java.util.List;

//import cn.edu.kmust.seanlp.extractor.domain.KeyTerm;
//import cn.edu.kmust.seanlp.extractor.language.ThaiExtractor;
//import cn.edu.kmust.seanlp.similarity.language.ThaiSentenceSimilarity;
//import cn.edu.kmust.seanlp.tokenizer.KCCTokenizer;
//import cn.edu.kmust.seanlp.tokenizer.ThaiDATTokenizer;


public class SeanlpThai {

    private static boolean isDebug;

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        SeanlpThai.isDebug = isDebug;
    }

    /**
     * CRF泰语音节切分
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String syllableSegment(String text) {
        return ThaiCRFTokenizer.syllableSegment(text);
    }

    private static String toString(List<Term> terms) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            line.append(terms.get(i).getWord()).append("|");
        }
        return line.toString();
    }

//    /**
//     * 泰语TCC切分
//     *
//     * @param text 原文本
//     * @return 拆分后的文本数组
//     */
//    public static List<cn.edu.kmust.seanlp.segmenter.domain.Term> segmentTCC(String text) {
//        return KCCTokenizer.segment(text);
//    }
//
//    /**
//     * 泰语DAT分词
//     *
//     * @param text 原文本
//     * @return 拆分后的文本数组
//     */
//    public static List<cn.edu.kmust.seanlp.segmenter.domain.Term> datSegment(String text) {
//        return ThaiDATTokenizer.segment(text);
//    }

    /**
     * 泰语层叠CRF分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String dCRFSegment(String text) {
        List<Term> terms = ThaiCRFTokenizer.segment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

    /**
     * 泰语CRF分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String gCRFSegment(String text) {
        List<Term> terms = ThaiCRFTokenizer.segment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

    public static String customMaxSegment(String text) {
        List<Term> terms = ThaiMatchTokenizer.customMaxSegment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

    /**
     * 泰语正向最大分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String maxSegment(String text) {
        List<Term> terms = ThaiMatchTokenizer.maxSegment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

    /**
     * 泰语正向最小分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String minSegment(String text) {
        List<Term> terms = ThaiMatchTokenizer.minSegment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

    /**
     * 泰语逆向最大分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String reMaxSegment(String text) {
        List<Term> terms = ThaiMatchTokenizer.rMaxSegment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

    /**
     * 泰语逆向最小分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String reMinSegment(String text) {
        List<Term> terms = ThaiMatchTokenizer.rMinSegment(text);
        if (isDebug()) {
            System.out.println(terms);
        }
        return toString(terms);
    }

//    /**
//     * 提取关键词
//     *
//     * @param document 文档内容
//     * @param size     希望提取几个关键词
//     * @return 一个列表
//     */
//    public static List<KeyTerm> extractKeyword(String document, int size) {
//        return ThaiExtractor.extractKeyword(document, size);
//    }
//
//    /**
//     * 自动摘要
//     *
//     * @param document 目标文档
//     * @param size     需要的关键句的个数
//     * @return 关键句列表
//     */
//    public static List<String> extractSummary(String document, int size) {
//        return ThaiExtractor.extractSummary(document, size);
//    }
//
//    /**
//     * 自动摘要
//     *
//     * @param document   目标文档
//     * @param max_length 需要摘要的长度
//     * @return 摘要文本
//     */
//    public static String getSummary(String document, int max_length) {
//        return ThaiExtractor.getSummary(document, max_length);
//    }
//
//    /**
//     * 句子相似度计算
//     *
//     * @param sent1
//     * @param sent2
//     * @return
//     */
//    public static double sentenceSimilarity(String sent1, String sent2) {
//        return ThaiSentenceSimilarity.getSimilarity(sent1, sent2);
//    }
}
