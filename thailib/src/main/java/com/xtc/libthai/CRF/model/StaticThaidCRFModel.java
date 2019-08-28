package com.xtc.libthai.CRF.model;

import com.xtc.libthai.CRF.CRFModel;
import com.xtc.libthai.Config;

/**
 * 泰语静态CRF分词模型
 */
public class StaticThaidCRFModel {

    private static final String TAG = "StaticThaidCRFModel: ";

    private static String thSyllableSegmentModelFile;
    private static String thSyllableMergeModelFile;
    public static CRFModel crfThaiSyllableSegmentModel;
    public static CRFModel crfThaiSyllableMergeModel;

    public StaticThaidCRFModel() {
    }

    static {
        thSyllableSegmentModelFile = Config.ModelConf.CRFModelPath + Config.language.toString() + Config.ModelConf.syllableSegment;
        thSyllableMergeModelFile = Config.ModelConf.CRFModelPath + Config.language.toString() + Config.ModelConf.syllableMerge;
        System.out.println("正在加载泰语CRF模型：" + thSyllableSegmentModelFile + "\n" + thSyllableMergeModelFile);
        long start = System.currentTimeMillis();
        crfThaiSyllableSegmentModel = CRFModel.loadCRFModel(thSyllableSegmentModelFile);
        crfThaiSyllableMergeModel = CRFModel.loadCRFModel(thSyllableMergeModelFile);
        if (crfThaiSyllableSegmentModel != null && crfThaiSyllableMergeModel != null) {
            System.out.println(TAG + "加载泰语CRF模型：" + thSyllableSegmentModelFile + "\n" + thSyllableMergeModelFile + " 成功，耗时 " + (System.currentTimeMillis() - start) + " ms");
        } else {
            System.out.println(TAG + "加载泰语CRF模型：" + thSyllableSegmentModelFile + "\n" + thSyllableMergeModelFile + " 失败，耗时 " + (System.currentTimeMillis() - start) + " ms");
            System.exit(-1);
        }

    }
}
