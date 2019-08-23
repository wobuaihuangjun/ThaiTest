package com.xtc.libthai.dictionary.language;


import com.xtc.libthai.Config;
import com.xtc.libthai.dictionary.CoreDictionary;

public class ThaiCoreDictionary {
    public static CoreDictionary thaiDictionary;

    public ThaiCoreDictionary() {
    }

    static {
        long start = System.currentTimeMillis();
        thaiDictionary = CoreDictionary.loadCoreDictionary(Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.coreDictionary);
        if (thaiDictionary == null) {
            System.err.printf("核心词典%s加载失败\n", Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.coreDictionary);
            System.exit(-1);
        } else {
            Config.Log.logger.info(Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.coreDictionary + "加载成功，" + thaiDictionary.dictionaryTrie.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
        }

    }
}

