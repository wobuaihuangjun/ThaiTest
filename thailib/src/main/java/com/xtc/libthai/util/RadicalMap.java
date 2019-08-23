package com.xtc.libthai.util;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RadicalMap {
    private static HashMap<Character, Character> charsToRads = new HashMap();
    private static HashMap<Character, Set<Character>> radsToChars = new HashMap();

    public RadicalMap() {
    }

    public static char getThaiRadical(char ch) {
        Character character = (Character)charsToRads.get(ch);
        return character != null ? character : 'o';
    }

    public static String getThaiCharType(char ch) {
        Character character = (Character)charsToRads.get(ch);
        return character != null ? character.toString() : "o";
    }

    public static Set<Character> getThaiChars(char ch) {
        return (Set)radsToChars.get(ch);
    }

    public static String getThaiSyllableType(String str) {
        String type;
        if (str.matches("[0-9]+")) {
            type = "d";
        } else if (str.matches("[a-zA-Z]+")) {
            type = "e";
        } else if (str.matches("[ก-๛]+")) {
            type = "t";
        } else if (str.matches("[\\pP‘’“”]+")) {
            type = "p";
        } else {
            type = "v";
        }

        return type;
    }

    public static String getThaiWordType(String word) {
        String type;
        if (word.matches("[0-9]+")) {
            type = "d";
        } else if (word.matches("[a-zA-Z]+")) {
            type = "e";
        } else if (word.matches("[ก-๛]+")) {
            type = "t";
        } else if (word.matches("[\\pP‘’“”]+")) {
            type = "p";
        } else {
            type = "v";
        }

        return type;
    }

    public static String getVietnameseType(String viWord) {
        String temp = "";
        if (viWord.matches("^[0-9]+$")) {
            temp = "d";
        } else if (viWord.matches("^[A-Za-z]+$")) {
            temp = "e";
        } else if (viWord.matches("^[À-ỹ]+$")) {
            temp = "t";
        } else if (viWord.matches("[\\pP]+")) {
            temp = "p";
        } else {
            temp = "v";
        }

        return temp;
    }

    static {
        String[] radLists = new String[]{"eabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "cกขฃคฆงจชซญฎฏฐฑฒณดตถทธนบปพฟภมยรลวศษสฬอ", "nฅฉผฝฌหฮฤฦ", "vะัิีึืฺุูาำๅ", "wเแโใไ", "t่้๊๋", "sฯๆ๎์ํ๏๚๛", "d0123456789๐๑๒๓๔๕๖๗๘๙", "q.,?!;:`~-_=+'\"\\/()[]{}<>@#$%^&*", "p "};

        for(int i = 0; i < radLists.length; ++i) {
            Set<Character> chars = new HashSet();
            char rad = radLists[i].charAt(0);
            int j = 1;

            for(int rLeng = radLists[i].length(); j < rLeng; ++j) {
                char ch = radLists[i].charAt(j);
                charsToRads.put(ch, rad);
                chars.add(ch);
            }

            radsToChars.put(rad, chars);
        }

    }
}
