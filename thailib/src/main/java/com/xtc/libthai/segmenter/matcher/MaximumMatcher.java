package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.collection.trie.DATrie;
import com.xtc.libthai.recognition.RecogTool;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MaximumMatcher extends AbstractMatcher {
    public MaximumMatcher(DATrie<?> dict) {
        super(dict);
    }

    public List<Term> segment(String[] strs) {
        List<Term> result = new ArrayList();
        int textLen = strs.length;
        int len = wordMaxLength;

        for(int start = 0; start < textLen; len = wordMaxLength) {
            if (len > textLen - start) {
                len = textLen - start;
            }

            while(!dict.contains(StringUtil.merge(strs, start, len)) && !RecogTool.recog(StringUtil.merge(strs, start, len)) && len != 1) {
                --len;
            }

            result.add(new Term(StringUtil.merge(strs, start, len), (Nature)null));
            start += len;
        }

        return result;
    }

    public List<Term> segment(char[] sentence) {
        List<Term> result = new ArrayList();
        int textLen = sentence.length;
        int len = wordMaxLength;

        for(int start = 0; start < textLen; len = wordMaxLength) {
            if (len > textLen - start) {
                len = textLen - start;
            }

            while(!dict.contains(sentence, start, len) && !RecogTool.recog(new String(sentence, start, len)) && len != 1) {
                --len;
            }

            result.add(new Term(new String(sentence, start, len), (Nature)null));
            start += len;
        }

        return result;
    }

    public List<Term> segment(String sentence) {
        return this.segment(sentence.toCharArray());
    }
}

