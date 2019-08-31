package com.xtc.libthai.collection.trie;

import com.xtc.libthai.util.IOUtil;

import java.util.List;

public class CustomTrie implements ITrie {

    private List<String> base;
    private int size;//词条数量

    /**
     * 唯一的构建方法
     *
     * @param key 值set，必须字典序
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
        boolean result = base != null && base.contains(key);
//        if (result) {
//            updateWord(key);
//        }
        return result;
    }

    @Override
    public void clear() {

    }

    /**
     * 分词时，将使用到的词汇进行提取。下次就直接使用提取后的词汇，增加词汇利用率和匹配速度
     */
    private void updateWord(String key) {
        String path = "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/thai-use.txt";

        List<String> word = IOUtil.readLines(path);

        if (!word.contains(key)) {
            IOUtil.appendLine(path, key);
        }
    }

    /**
     * 分词时，将使用到的词汇进行提取，并设置词频。下次就直接使用提取后的词汇，增加词汇利用率和匹配速度
     */
    private void updateWordFrequency(String key) {
        String path = "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/thai-use.txt";

        List<String> word = IOUtil.readLines(path);
        String line = "";
        int i;
        for (i = 0; i < word.size(); i++) {
            String[] param = word.get(i).split("\t");
            if (param[0].equals(key) && param.length == 2) {
                line = key + "\t" + (Integer.valueOf(param[1]) + 1);
                break;
            }
        }
        if (line.length() > 0) {
            word.remove(i);
        } else {
            line = key + "\t" + 1;
        }

        word.add(line);

        IOUtil.overwriteLines(path, word);
    }


}
