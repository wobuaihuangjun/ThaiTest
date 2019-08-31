package com.xtc.libthai;

import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.tokenizer.ThaiCustomMatchTokenizer;
import com.xtc.libthai.tokenizer.ThaiMatchTokenizer;

import java.util.List;

//import cn.edu.kmust.seanlp.extractor.domain.KeyTerm;
//import cn.edu.kmust.seanlp.extractor.language.ThaiExtractor;
//import cn.edu.kmust.seanlp.similarity.language.ThaiSentenceSimilarity;
//import cn.edu.kmust.seanlp.tokenizer.KCCTokenizer;
//import cn.edu.kmust.seanlp.tokenizer.ThaiDATTokenizer;


public class SeanlpThai {

    private static String toString(List<Term> terms) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            line.append(terms.get(i).getWord()).append("|");
        }
        return line.toString();
    }


    public static String customMaxSegment(String text) {
        List<Term> terms = ThaiCustomMatchTokenizer.customMaxSegment(text);

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
        return toString(terms);
    }

    /**
     * 将文本进行换行处理
     *
     * @param text   待处理文本
     * @param length 每行最大长度
     * @return 添加了换行符之后的文本
     */
    public static String breakStringByCustomMaxSegment(String text, int length){
        List<Term> terms = ThaiCustomMatchTokenizer.customMaxSegment(text);
        System.out.println("分词: " + toString(terms));

        return breakString(terms, length);
    }

    /**
     * 将文本进行换行处理
     *
     * @param terms   待处理分词List
     * @param length 每行最大长度
     * @return 添加了换行符之后的文本
     */
    public static String breakString(List<Term> terms, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        for (Term term : terms) {
            String word = term.getWord();
            if (word.length() <= 0) {
                continue;
            }
            if (line.length() > 0) {
                if ((line + word).length() < length) {
                    // 未超过最大长度,继续追加
                    line = line + word;
                } else {
                    stringBuilder.append(line).append("\n");
                    line = word;// 最新的词另起一行
                }
            } else {
                line = word;
            }
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String result = SeanlpThai.breakStringByCustomMaxSegment("School Numberต้องมีมากกว่า1หลัก", 50);
        System.out.println(result);
    }
}
