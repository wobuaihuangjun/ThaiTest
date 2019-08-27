package com.xtc.libthai;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    public static boolean DEBUG = false;
    public static Language language;

    public Config() {
    }

    static {
        language = Language.Thai;
    }

    public static final class Log {
        public static Logger logger = Logger.getLogger("SEANLP");

        public Log() {
        }

        static {
            logger.setLevel(Level.SEVERE);
        }
    }

    public static final class FileExtensions {
        public static final String TRIE_EXT = ".trie.dat";
        public static final String VALUE_EXT = ".value.dat";
        public static final String REVERSE_EXT = ".reverse";
        public static final String BIN = ".bin";
        public static final String ZIP = ".zip";
        public static final String GZ = ".gz";
        public static final String TXT = ".txt";

        public FileExtensions() {
        }
    }

    public static final class DictConf {
        public static String dictionaryPath = "/com/xtc/libthai/dictionary/";

        public static String commonDictionary = "/CommonDictionary";
        public static String coreDictionary = "/CoreDictionary";
        public static String natureTransitionMatrix = "/NatureTransitionMatrix";
        public static String syllableDictionary = "/SyllableDictionary";
        public static String stopWord = "stopwords";

        public DictConf() {
        }
    }

    public static final class ModelConf {
        public static String CRFModelPath = "/cn/edu/kmust/seanlp/model/segmenter/";
        public static String syllableSegment = "/syllable.segment.dCRF.5gram";
        public static String syllableMerge = "/syllable.merge.dCRF.3gram";
        public static String wordSegment = "/word.segment.gCRF.7gram";
        public static String viWordSegment = "/word.segment.CRF.3gram";
        public static String khWordSegment = "/word.segment.CRF.5gram";
        public static String POS = "POS.";

        public ModelConf() {
        }
    }

    public static final class BaseConf {
        public static boolean speechTagging = true;
        public static boolean useCustomDictionary = false;

        public BaseConf() {
        }

        public static void enableDebug() {
            enableDebug(true);
        }

        public static void enableDebug(boolean enable) {
            Config.DEBUG = enable;
            if (Config.DEBUG) {
                Config.Log.logger.setLevel(Level.ALL);
            } else {
                Config.Log.logger.setLevel(Level.OFF);
            }

        }

        public static void seletcLanguage(Language lang) {
            Config.language = lang;
        }
    }
}
