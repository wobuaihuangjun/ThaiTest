package com.xtc.libthai.segmenter.domain;

import com.xtc.libthai.collection.trie.DATrie;
import com.xtc.libthai.dictionary.CoreDictionary;

import java.util.Map.Entry;

public class Vertex {
    public String word;
    public String realWord;
    public CoreDictionary.Attribute attribute;
    public int wordID;
    public int index;
    public static Vertex B;
    public Vertex from;

    public Vertex(String word, String realWord, CoreDictionary.Attribute attribute) {
        this(word, realWord, attribute, -1);
    }

    public Vertex(String word, String realWord, CoreDictionary.Attribute attribute, int wordID) {
        if (attribute == null) {
            attribute = new CoreDictionary.Attribute(Nature.NCMN, 1);
        }

        this.wordID = wordID;
        this.attribute = attribute;

        assert realWord.length() > 0 : "构造空白节点会导致死循环！";

        this.word = word;
        this.realWord = realWord;
    }

    public Vertex(String realWord, CoreDictionary.Attribute attribute) {
        this((String)null, realWord, attribute);
    }

    public Vertex(String realWord, CoreDictionary.Attribute attribute, int wordID) {
        this((String)null, realWord, attribute, wordID);
    }

    public Vertex(Entry<String, CoreDictionary.Attribute> entry) {
        this((String)entry.getKey(), (CoreDictionary.Attribute)entry.getValue());
    }

    public Vertex(DATrie<CoreDictionary.Attribute> dictionaryTrie, String realWord) {
        this((String)null, realWord, CoreDictionary.get(dictionaryTrie, realWord));
    }

    public Vertex(char realWord, CoreDictionary.Attribute attribute) {
        this(String.valueOf(realWord), attribute);
    }

    public String getRealWord() {
        return this.realWord;
    }

    public CoreDictionary.Attribute getAttribute() {
        return this.attribute;
    }

    public boolean confirmNature(Nature nature) {
        if (this.attribute.nature.length == 1 && this.attribute.nature[0] == nature) {
            return true;
        } else {
            boolean result = true;
            int frequency = this.attribute.getNatureFrequency(nature);
            if (frequency == 0) {
                frequency = 1000;
                result = false;
            }

            this.attribute = new CoreDictionary.Attribute(nature, frequency);
            return result;
        }
    }

    public Nature getNature() {
        return this.attribute.nature.length == 1 ? this.attribute.nature[0] : null;
    }

    public Nature guessNature() {
        return this.attribute.nature[0];
    }

    public boolean hasNature(Nature nature) {
        return this.attribute.getNatureFrequency(nature) > 0;
    }

    public Vertex copy() {
        return new Vertex(this.word, this.realWord, this.attribute);
    }

    public Vertex setWord(String word) {
        this.word = word;
        return this;
    }

    public Vertex setRealWord(String realWord) {
        this.realWord = realWord;
        return this;
    }

    public static Vertex newPunctuationInstance(String realWord) {
        return new Vertex(realWord, new CoreDictionary.Attribute(Nature.PUNC, 1000));
    }

    public String toString() {
        return this.realWord;
    }
}
