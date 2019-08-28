package com.xtc.libthai.dictionary.language;

import com.xtc.libthai.Config;
import com.xtc.libthai.dictionary.NatureTransitionMatrix;
import com.xtc.libthai.segmenter.domain.Nature;

/**
 * 泰语词性转移矩阵
 *
 * @author Zhao Shiyu
 *
 */
public class ThaiNatureTransMatrix {

    public static NatureTransitionMatrix<Nature> thaiTransMatrix;

    static {
        thaiTransMatrix = new NatureTransitionMatrix<>(Nature.class);
        long start = System.currentTimeMillis();
        if (!thaiTransMatrix.load(Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.natureTransitionMatrix)) {
            System.err.println("加载核心词典词性转移矩阵" + Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.natureTransitionMatrix + "失败");
            System.exit(-1);
        } else {
            Config.Log.logger.info("加载核心词典词性转移矩阵" + Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.natureTransitionMatrix + "成功，耗时：" + (System.currentTimeMillis() - start) + " ms");
        }
    }

}

