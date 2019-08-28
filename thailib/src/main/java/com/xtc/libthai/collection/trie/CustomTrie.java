package com.xtc.libthai.collection.trie;

import java.util.List;

public class CustomTrie implements ITrie {

    private List<String> base;
    private int size;//词条数量

    /**
     * 唯一的构建方法
     *
     * @param key     值set，必须字典序
     */
    public void build(List<String> key) {
        if (key == null)
            return;

        base = key;
        size = key.size();
    }

    public int size() {
        return size;
    }

    @Override
    public int getMaxLength() {
        return size;
    }

    @Override
    public boolean contains(char[] text, int offset, int count) {
        return false;
    }

    @Override
    public boolean contains(String text, int offset, int count) {
        return false;
    }

    @Override
    public boolean contains(String key) {
        return base != null && base.contains(key);
    }

    @Override
    public void clear() {

    }
}
