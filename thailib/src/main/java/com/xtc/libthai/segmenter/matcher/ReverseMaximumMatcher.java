package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.collection.trie.DATrie;
import com.xtc.libthai.recognition.RecogTool;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ReverseMaximumMatcher extends AbstractMatcher {
    public ReverseMaximumMatcher(DATrie<?> dict) {
        super(dict);
    }

    public List<Term> segment(String[] strs) {
        List<Term> result = new ArrayList();
        int textLen = strs.length;
        int len = wordMaxLength;
        int start = textLen - len;
        if (start < 0) {
            start = 0;
        }

        if (len > textLen - start) {
            len = textLen - start;
        }

        for(; start >= 0 && len > 0; start -= len) {
            while(!dict.contains(StringUtil.merge(strs, start, len)) && !RecogTool.recog(StringUtil.merge(strs, start, len)) && len != 1) {
                --len;
                ++start;
            }

            result.add(0, new Term(StringUtil.merge(strs, start, len), (Nature)null));
            len = wordMaxLength;
            if (len > start) {
                len = start;
            }
        }

        return result;
    }

    public List<Term> segment(char[] sentence) {
        List<Term> result = new ArrayList();
        int textLen = sentence.length;
        int len = wordMaxLength;
        int start = textLen - len;
        if (start < 0) {
            start = 0;
        }

        if (len > textLen - start) {
            len = textLen - start;
        }

        for(; start >= 0 && len > 0; start -= len) {
            while(!dict.contains(sentence, start, len) && !RecogTool.recog(new String(sentence, start, len)) && len != 1) {
                --len;
                ++start;
            }

            result.add(0, new Term(new String(sentence, start, len), (Nature)null));
            len = wordMaxLength;
            if (len > start) {
                len = start;
            }
        }

        return result;
    }

    public List<Term> segment(String sentence) {
        return this.segment(sentence.toCharArray());
    }
}

