package com.xtc.libthai.seanlp.dictionary;

import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.collection.trie.CustomTrie;
import com.xtc.libthai.seanlp.util.ByteArray;
import com.xtc.libthai.seanlp.util.IOUtil;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 使用DoubleArrayTrie实现的核心词典
 */
public class CustomDictionary implements Dictionary<CustomDictionary> {

    private static final String TAG = "CustomDictionary: ";

    public CustomTrie customTrie;

    public static CustomDictionary loadCustomDictionary(String path, String[] fileName) {
//        String path = "/com/xtc/libthai/dictionary/Thai/tdict-common";

        Config.Log.logger.info("自定义词典开始加载:" + path);
        System.out.println(TAG + "自定义词典开始加载:" + path);

        return loadCustomTxtDictionary(path, fileName);
    }

    private static CustomDictionary loadCustomTxtDictionary(String path, String[] fileName) {
        CustomDictionary coreDictionary = new CustomDictionary();
        coreDictionary.customTrie = new CustomTrie();
        List<String> keyWord = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (String file : fileName) {
            String realPath = path + file + Config.FileExtensions.TXT;
            List<String> words = loadCustomTxtDictionary(realPath);
            if (words != null) {
                System.out.println(TAG + "自定义词典: " + realPath + "，读入词条：" + words.size());
                keyWord.addAll(words);
            }
        }
        System.out.println(TAG + "自定义词典读入词条" + keyWord.size() + "，耗时" + (System.currentTimeMillis() - start) + "ms");
        coreDictionary.customTrie.build(keyWord);

//        getNewWord(path, keyWord);

        return coreDictionary;
    }

    private static void getNewWord(String path, List<String> keyWord) {
        String realPath = path + "/dictionary" + Config.FileExtensions.TXT;
        List<String> words = loadCustomTxtDictionary(realPath);

        List<String> newWord = new LinkedList<>();
        for (String word : words) {
            if (!keyWord.contains(word)) {
                newWord.add(word);
            }
        }

        IOUtil.saveCollectionToTxt(newWord, "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/libthai/dictionary/Thai/tdict-new-word.txt");
    }

    /**
     * 从resources读取文件
     */
    private static List<String> loadCustomTxtDictionary(String path) {
        return IOUtil.readLines(IOUtil.getInputStream(path));
    }

    @Override
    public boolean loadDat(ByteArray var1) {
        return false;
    }
}

