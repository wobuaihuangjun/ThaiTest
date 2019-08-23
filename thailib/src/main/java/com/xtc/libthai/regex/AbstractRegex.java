package com.xtc.libthai.regex;

public abstract class AbstractRegex {
    protected static final String DIGITS = "[0-9]+";
    protected static final String ENGLISH = "[a-zA-Z]+";
    protected static final String PUNCTUATION = "[\\pP‘’“”]+";
    protected static final String NEWLINES = "[\r\n]+";
    protected static final String WS = "[\\s]+";
    protected static final String DONT_KNOW = ".";
    protected static final String orex = "|";
    protected static final String digit = "^[0-9]+$";
    protected static final String integer = "^(\\-|\\+)?[1-9]\\d+$";
    protected static final String fraction = "^(\\-|\\+)?\\d+(\\.|/|．|·|／)\\d+$";
    protected static final String digitThai = "^[๐-๙]+$";
    protected static final String digitKhmer = "^[០-៩៰-៹]+$";
    protected static final String digitLao = "^[໐-໙]+$";
    protected static final String digitMyanmar = "^[၀-၉႐-႙]+$";
    protected static final String number = "^[0-9]+$|^(\\-|\\+)?[1-9]\\d+$|^(\\-|\\+)?\\d+(\\.|/|．|·|／)\\d+$|^[๐-๙]+$|^[໐-໙]+$|^[០-៩៰-៹]+$|^[၀-၉႐-႙]+$";
    protected static final String english = "^[a-zA-Z]+$";

    public AbstractRegex() {
    }
}

