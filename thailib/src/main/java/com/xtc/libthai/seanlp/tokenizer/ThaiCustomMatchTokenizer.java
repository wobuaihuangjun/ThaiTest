package com.xtc.libthai.seanlp.tokenizer;

import android.text.TextUtils;

import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.collection.trie.CustomTrie;
import com.xtc.libthai.seanlp.dictionary.language.ThaiCustomDictionary;
import com.xtc.libthai.seanlp.segmenter.Segmenter;
import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.segmenter.matcher.language.ThaiCustomMatchSegmenter;
import com.xtc.libthai.seanlp.util.IOUtil;

import java.util.ArrayList;
import java.util.List;

public class ThaiCustomMatchTokenizer {

    public final static Segmenter customMatchSegmenter = new ThaiCustomMatchSegmenter();

    public static List<Term> customMaxSegment(String text) {
        return customMatchSegmenter.segment(text);
    }

    /**
     * 从程序当前目录读取新词汇文件，追加新的词汇到词库
     * <p>
     * 每行一个词汇
     * <p>
     * 生成新增词条文件和总的词条文件
     */
    public static void insertNewWord(String path, String inputFileName) {
        if (inputFileName == null || inputFileName.length() <= 0) {
            return;
        }
        long start = System.currentTimeMillis();

        String inputPath = path + inputFileName + Config.FileExtensions.TXT;

        CustomTrie customTrie = ThaiCustomDictionary.customDictionary.customTrie;
        List<String> keyWord = customTrie.getWord();

        List<String> newWords = IOUtil.readLines(inputPath);

        List<String> addWords = new ArrayList<>();
        for (String word : newWords) {
            if (!keyWord.contains(word)) {
                addWords.add(word);
            }
        }

        String newWordPath = IOUtil.getCurrentFilePath() + "/OperationFile/add_word" + Config.FileExtensions.TXT;
        IOUtil.saveCollectionToTxt(addWords, newWordPath);
        System.out.println("新增词条文件生成 完成，耗时: " + (System.currentTimeMillis() - start) + "ms");

        String allWordPath = IOUtil.getCurrentFilePath() + "/OperationFile/all_word" + Config.FileExtensions.TXT;
        keyWord.addAll(addWords);
        IOUtil.saveCollectionToTxt(keyWord, allWordPath);

        System.out.println("总的词条文件生成 完成，耗时: " + (System.currentTimeMillis() - start) + "ms");
    }

}
