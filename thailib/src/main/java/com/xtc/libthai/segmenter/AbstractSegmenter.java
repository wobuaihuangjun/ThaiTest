package com.xtc.libthai.segmenter;

import com.xtc.libthai.Config;
import com.xtc.libthai.Language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSegmenter {
    protected Config config = new Config();

    public AbstractSegmenter() {
    }

    protected String[] sentenceSegment(String text) {
        String regex = "[.;!?។]";
        if (Config.language.equals(Language.Thai)) {
            regex = regex + "|[ ]";
        }

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

    public AbstractSegmenter enablePartOfSpeechTagging(boolean enable) {
        Config.BaseConf.speechTagging = enable;
        return this;
    }
}
