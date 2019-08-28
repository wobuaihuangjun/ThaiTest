package com.xtc.libthai.segmenter.matcher;


import com.xtc.libthai.collection.trie.CustomTrie;

public abstract class AbstractCustomMatcher implements Matcher {

    /**
     * 单个词汇的最大长度
     */
    protected static int wordMaxLength = 20;
    protected static int wordMinLength = 1;
    protected static CustomTrie dict;

    public AbstractCustomMatcher(CustomTrie datDict) {
        dict = datDict;
    }
}
