package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.collection.trie.DATrie;
import com.xtc.libthai.recognition.RecogTool;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MinimumMatcher extends AbstractMatcher {
    public MinimumMatcher(DATrie<?> dict) {
        super(dict);
    }

    public List<Term> segment(String[] strs) {
        List<Term> result = new ArrayList();
        int textLen = strs.length;
        int len = wordMinLength;

        for(int start = 0; start < textLen; len = wordMinLength) {
            while(!dict.contains(StringUtil.merge(strs, start, len)) && !RecogTool.recog(StringUtil.merge(strs, start, len))) {
                if (len == wordMaxLength || len == textLen - start) {
                    len = wordMinLength;
                    break;
                }

                ++len;
            }

            result.add(new Term(StringUtil.merge(strs, start, len), (Nature)null));
            start += len;
        }

        return result;
    }

    public List<Term> segment(char[] sentence) {
        List<Term> result = new ArrayList();
        int textLen = sentence.length;
        int len = wordMinLength;

        for(int start = 0; start < textLen; len = wordMinLength) {
            while(!dict.contains(sentence, start, len) && !RecogTool.recog(new String(sentence, start, len))) {
                if (len == wordMaxLength || len == textLen - start) {
                    len = wordMinLength;
                    break;
                }

                ++len;
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
