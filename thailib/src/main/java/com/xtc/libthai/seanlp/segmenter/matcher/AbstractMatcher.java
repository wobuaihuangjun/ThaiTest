package com.xtc.libthai.seanlp.segmenter.matcher;

import com.xtc.libthai.seanlp.collection.trie.DATrie;

/**
 * 词典匹配算法基类
 */
public abstract class AbstractMatcher implements Matcher {

    /**
     * 单个词汇的最大长度
     */
    protected static int wordMaxLength = 20;
    protected static int wordMinLength = 1;
    protected static DATrie<?> dict;

    public AbstractMatcher(DATrie<?> datDict) {
        dict = datDict;
    }
}
