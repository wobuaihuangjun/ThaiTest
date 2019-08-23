package com.xtc.libthai.CRF.model;


import com.xtc.libthai.CRF.CRFModel;
import com.xtc.libthai.Config;

public class StaticThaigCRFModel {
    private static String thCRFWordSegmentModelFile;
    public static CRFModel crfThaiWordSegmentModel;

    public StaticThaigCRFModel() {
    }

    static {
        thCRFWordSegmentModelFile = Config.ModelConf.CRFModelPath + Config.language.toString() + Config.ModelConf.wordSegment;
        System.out.println("正在加载泰语CRF模型：" + thCRFWordSegmentModelFile);
        long start = System.currentTimeMillis();
        crfThaiWordSegmentModel = CRFModel.loadCRFModel(thCRFWordSegmentModelFile);
        if (crfThaiWordSegmentModel == null) {
            System.out.println("加载泰语CRF模型：" + thCRFWordSegmentModelFile + " 失败，耗时 " + (System.currentTimeMillis() - start) + " ms");
            System.exit(-1);
        } else {
            System.out.println("加载泰语CRF模型：" + thCRFWordSegmentModelFile + " 成功，耗时 " + (System.currentTimeMillis() - start) + " ms");
        }

    }
}

