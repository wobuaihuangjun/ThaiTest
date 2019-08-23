package com.xtc.libthai.segmenter.CC;


import com.xtc.libthai.regex.AbstractRegex;
import com.xtc.libthai.segmenter.Segmenter;

public abstract class AbstractCC extends AbstractRegex implements Segmenter {
    protected static final String question_mark = "?";
    protected static final String plus = "+";
    protected static final String left_parenthesis = "(";
    protected static final String right_parenthesis = ")";

    public AbstractCC() {
    }

    public abstract String token(String var1);
}

