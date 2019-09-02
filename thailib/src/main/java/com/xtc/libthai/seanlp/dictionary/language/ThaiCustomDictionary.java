package com.xtc.libthai.seanlp.dictionary.language;


import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.dictionary.CustomDictionary;

public class ThaiCustomDictionary {

    private static final String TAG = "ThaiCustomDictionary: ";

    public static CustomDictionary customDictionary;

    static {
        long start = System.currentTimeMillis();
        String dictionaryPath = Config.DictConf.dictionaryPath + Config.language.toString();
        customDictionary = CustomDictionary.loadCustomDictionary(dictionaryPath, Config.DictConf.customDictionaryFile);
        if (customDictionary == null || customDictionary.customTrie == null) {
            System.err.printf(TAG + "自定义词典%s加载失败\n", dictionaryPath);
            System.exit(-1);
        } else {
            System.out.println(TAG + dictionaryPath + "加载成功，" + customDictionary.customTrie.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
            Config.Log.logger.info(dictionaryPath + "加载成功，" + customDictionary.customTrie.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
        }

    }
}

