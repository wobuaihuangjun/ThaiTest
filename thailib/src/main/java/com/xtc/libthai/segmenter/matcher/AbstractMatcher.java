package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.collection.trie.DATrie;

public abstract class AbstractMatcher implements Matcher {
    protected static int wordMaxLength = 20;
    protected static int wordMinLength = 1;
    protected static DATrie<?> dict;

    public AbstractMatcher(DATrie<?> datDict) {
        dict = datDict;
    }
}
