package com.xtc.libthai.segmenter.domain;


import com.xtc.libthai.Config;

public class Term {
    private String word;
    private Nature nature;
    private int offset;

    public Term(String word, Nature nature) {
        this.word = word;
        this.nature = nature;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Nature getNature() {
        return this.nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String toString() {
        return Config.BaseConf.speechTagging ? this.word + "/" + this.nature : this.word;
    }

    public int length() {
        return this.word.length();
    }
}
