package com.xtc.libthai.collection.trie;


public interface ITrie {
    int getMaxLength();

    boolean contains(char[] var1, int var2, int var3);

    boolean contains(String var1, int var2, int var3);

    boolean contains(String var1);

    void clear();
}
