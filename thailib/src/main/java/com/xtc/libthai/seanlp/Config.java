package com.xtc.libthai.seanlp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 东南亚语言信息处理 <br>
 * 常用配置工具类
 */
public class Config {

    /**
     * 开发模式
     */
    public static boolean DEBUG = false;

    /**
     * 选择语言，默认为泰语
     */
    public static Language language = Language.Thai;

    /**
     * 基本配置
     */
    public static final class BaseConf {

        /**
         * 分词结果是否展示词性
         */
        public static boolean speechTagging = true;

        /**
         * 是否使用自定义词典
         */
        public static boolean useCustomDictionary = false;

        /**
         * 开启调试模式(会降低性能)
         */
        public static void enableDebug() {
            enableDebug(true);
        }

        /**
         * 开启调试模式(会降低性能)
         *
         * @param enable
         */
        public static void enableDebug(boolean enable) {
            DEBUG = enable;
            if (DEBUG) {
                Log.logger.setLevel(Level.ALL);
            } else {
                Log.logger.setLevel(Level.OFF);
            }
        }

        /**
         * 选择要分析的语言
         *
         * @param lang
         */
        public static void seletcLanguage(Language lang) {
            language = lang;
        }

    }

    /**
     * 模型配置
     */
    public static final class ModelConf {
        /**
         * CRF模型路径
         */
        public static String CRFModelPath = "/com/xtc/libthai/model/segmenter/";
        /**
         * syllable segmentation
         */
        public static String syllableSegment = "/syllable.segment.dCRF.5gram";
        /**
         * syllable merge
         */
        public static String syllableMerge = "/syllable.merge.dCRF.3gram";
        /**
         * word segmentation
         */
        public static String wordSegment = "/word.segment.gCRF.7gram";

        public static String POS = "POS.";

    }

    /**
     * 模型和其它库配置
     */
    public static final class DictConf {
        /**
         * 词典路径
         */
        public static String dictionaryPath = "/com/xtc/libthai/dictionary/";

        /**
         * 普通词典
         */
        public static String commonDictionary = "/CommonDictionary";
        /**
         * 核心词典
         */
        public static String coreDictionary = "/CoreDictionary";
        /**
         * 核心词典词性转移矩阵
         */
        public static String natureTransitionMatrix = "/NatureTransitionMatrix";
        public static String syllableDictionary = "/SyllableDictionary";
        /**
         * 停用词词典路径
         */
        public static String stopWord = "stopwords";

        /**
         * 自定义词典
         */
        public static String[] customDictionaryFile = {
                "/tdict-city",
                "/tdict-collection",
                "/tdict-common",
                "/tdict-country",
                "/tdict-custom",
                "/tdict-district",
                "/tdict-geo",
                "/tdict-history",
                "/tdict-ict",
                "/tdict-lang-ethnic",
                "/tdict-proper",
                "/tdict-science",
                "/tdict-slang",
                "/tdict-spell",
                "/tdict-std",
                "/tdict-std-compound",
                "/tdict-new-word",
                "/thai-use",

        };
    }

    /**
     * 一些预定义的静态全局变量，文件后缀
     */
    public static final class FileExtensions {
        /**
         * trie树文件后缀名
         */
        public static final String TRIE_EXT = ".trie.dat";
        /**
         * 值文件后缀名
         */
        public static final String VALUE_EXT = ".value.dat";
        /**
         * 逆转后缀名
         */
        public static final String REVERSE_EXT = ".reverse";
        /**
         * 二进制文件后缀名
         */
        public static final String BIN = ".bin";
        /**
         * zip压缩文件后缀名
         */
        public static final String ZIP = ".zip";
        /**
         * gzip压缩文件后缀名
         */
        public static final String GZ = ".gz";
        /**
         * 文本文件后缀名
         */
        public static final String TXT = ".txt";

        /**
         * 文本文件后缀名
         */
        public static final String XML = ".xml";
    }

    /**
     * logger
     */
    public static final class Log {
        public static Logger logger = Logger.getLogger("SEANLP");

        static {
            logger.setLevel(Level.SEVERE);
        }
    }


}
