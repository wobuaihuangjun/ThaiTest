package com.xtc.libthai.seanlp.dictionary.language;


import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.dictionary.CoreDictionary;

/**
 * 泰语核心词典
 *
 */
public class ThaiCoreDictionary {

    private static final String TAG = "ThaiCoreDictionary: ";

    public static CoreDictionary thaiDictionary;

    static {
        long start = System.currentTimeMillis();
        String dictionaryPath = Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.coreDictionary;
        thaiDictionary = CoreDictionary.loadCoreDictionary(dictionaryPath);
        if (thaiDictionary == null) {
            System.err.printf(TAG + "核心词典%s加载失败\n", dictionaryPath);
            System.exit(-1);
        } else {
            System.out.println(TAG + dictionaryPath + "加载成功，" + thaiDictionary.dictionaryTrie.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
            Config.Log.logger.info(dictionaryPath + "加载成功，" + thaiDictionary.dictionaryTrie.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
        }

    }
}

