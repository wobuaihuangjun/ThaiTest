package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.collection.trie.DATrie;

public abstract class AbstractMatcher implements Matcher {

    /**
     * 单个词汇的最大长度
     */
    protected static int wordMaxLength = 30;
    protected static int wordMinLength = 1;
    protected static DATrie<?> dict;

    public AbstractMatcher(DATrie<?> datDict) {
        dict = datDict;
    }
}
