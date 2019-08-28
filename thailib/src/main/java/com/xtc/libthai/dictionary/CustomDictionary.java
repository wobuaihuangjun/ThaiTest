package com.xtc.libthai.dictionary;

import com.xtc.libthai.Config;
import com.xtc.libthai.collection.trie.CustomTrie;
import com.xtc.libthai.util.ByteArray;
import com.xtc.libthai.util.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
        return coreDictionary;
    }

    private static List<String> loadCustomTxtDictionary(String path) {
        List<String> wordList = new ArrayList<>();
        BufferedReader br = null;
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStream(path);
            if (inputStream == null) {
                return wordList;
            }
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
//                System.out.println(TAG + "loadCustomTxtDictionary, line:" + line);
                wordList.add(line);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wordList;
    }

    @Override
    public boolean loadDat(ByteArray var1) {
        return false;
    }
}

