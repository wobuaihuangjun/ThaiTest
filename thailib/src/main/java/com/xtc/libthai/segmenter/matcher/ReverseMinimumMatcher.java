package com.xtc.libthai.segmenter.matcher;

import com.xtc.libthai.collection.trie.DATrie;
import com.xtc.libthai.recognition.RecogTool;
import com.xtc.libthai.segmenter.domain.Nature;
import com.xtc.libthai.segmenter.domain.Term;
import com.xtc.libthai.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ReverseMinimumMatcher extends AbstractMatcher {
    public ReverseMinimumMatcher(DATrie<?> dict) {
        super(dict);
    }

    public List<Term> segment(String[] strs) {
        List<Term> result = new ArrayList();
        int textLen = strs.length;
        int len = wordMinLength;

        for(int start = textLen - len; start >= 0; len = wordMinLength) {
            label24: {
                do {
                    if (dict.contains(StringUtil.merge(strs, start, len)) || RecogTool.recog(StringUtil.merge(strs, start, len))) {
                        break label24;
                    }

                    ++len;
                    --start;
                } while(len <= wordMaxLength && start >= 0);

                start += len - 1;
                len = wordMinLength;
            }

            result.add(0, new Term(StringUtil.merge(strs, start, len), (Nature)null));
            --start;
        }

        return result;
    }

    public List<Term> segment(char[] sentence) {
        List<Term> result = new ArrayList();
        int textLen = sentence.length;
        int len = wordMinLength;

        for(int start = textLen - len; start >= 0; len = wordMinLength) {
            label24: {
                do {
                    if (dict.contains(sentence, start, len) || RecogTool.recog(new String(sentence, start, len))) {
                        break label24;
                    }

                    ++len;
                    --start;
                } while(len <= wordMaxLength && start >= 0);

                start += len - 1;
                len = wordMinLength;
            }

            result.add(0, new Term(new String(sentence, start, len), (Nature)null));
            --start;
        }

        return result;
    }

    public List<Term> segment(String sentence) {
        return this.segment(sentence.toCharArray());
    }
}

