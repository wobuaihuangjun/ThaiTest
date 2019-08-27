package com.xtc.libthai.dictionary.language;

import com.xtc.libthai.Config;
import com.xtc.libthai.dictionary.NatureTransitionMatrix;
import com.xtc.libthai.segmenter.domain.Nature;

public class ThaiNatureTransMatrix {

    private static final String TAG = "ThaiNatureTransMatrix: ";

    public static NatureTransitionMatrix<Nature> thaiTransMatrix = new NatureTransitionMatrix(Nature.class);

    public ThaiNatureTransMatrix() {
    }

    static {
        long start = System.currentTimeMillis();
        String dictionaryPath = Config.DictConf.dictionaryPath + Config.language.toString() + Config.DictConf.natureTransitionMatrix;
        if (!thaiTransMatrix.load(dictionaryPath)) {
            System.err.println("加载核心词典词性转移矩阵" + dictionaryPath + "失败");
            System.exit(-1);
        } else {
            System.out.println(TAG + "加载核心词典词性转移矩阵" + dictionaryPath + "成功，耗时：" + (System.currentTimeMillis() - start) + " ms");
            Config.Log.logger.info("加载核心词典词性转移矩阵" + dictionaryPath + "成功，耗时：" + (System.currentTimeMillis() - start) + " ms");
        }

    }
}

