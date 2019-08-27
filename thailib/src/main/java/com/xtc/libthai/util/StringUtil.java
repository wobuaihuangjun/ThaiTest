package com.xtc.libthai.util;

import com.xtc.libthai.Config;
import com.xtc.libthai.Language;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public StringUtil() {
    }

    /**
     * 将数组转换为String
     */
    public static String merge(String[] strArray, int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        } else if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        } else if (offset > strArray.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        } else {
            return toString(Arrays.copyOfRange(strArray, offset, offset + count));
        }
    }

    public static String getViWord(String[] strArray, int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        } else if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        } else if (offset > strArray.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        } else {
            return getViWord(Arrays.copyOfRange(strArray, offset, offset + count));
        }
    }

    public static String toString(String[] values) {
        int len = values.length;
        if (len == 0) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < len; ++i) {
                if (Config.language.equals(Language.Vietnamese)) {
                    sb.append(' ').append(values[i]);
                } else {
                    sb.append(values[i]);
                }
            }

            return sb.toString().trim();
        }
    }

    public static String getViWord(String[] values) {
        StringBuffer sb = new StringBuffer();
        int len = values.length;

        for(int i = 0; i < len; ++i) {
            if (i == 0) {
                sb.append(values[i]);
            } else {
                sb.append(" " + values[i]);
            }
        }

        return sb.toString();
    }

    public static String[] sentenceSegment(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        String[] substrs = pattern.split(text);
        if (substrs.length > 0) {
            for(int count = 0; count < substrs.length; ++count) {
                if (matcher.find()) {
                    substrs[count] = substrs[count] + matcher.group();
                }
            }
        }

        return substrs;
    }

    public static String[] sentenceSegment(String text) {
        String regex = "[.;!?។\r\n]";
        if (Config.language.equals(Language.Thai)) {
            regex = regex + "|[ ]";
        }

        return sentenceSegment(text, regex);
    }

    public static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String join(String delimiter, Collection<String> stringCollection) {
        StringBuilder sb = new StringBuilder(stringCollection.size() * (16 + delimiter.length()));
        Iterator var3 = stringCollection.iterator();

        while(var3.hasNext()) {
            String str = (String)var3.next();
            sb.append(str).append(delimiter);
        }

        return sb.toString();
    }
}

