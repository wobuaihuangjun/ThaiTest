package com.xtc.libthai.seanlp;

import com.xtc.libthai.seanlp.util.IOUtil;

import java.util.List;

public class FileOperation {







    /**
     * 加载配置文件
     */
    public static void loadConfigFile() {
        String rootPath = IOUtil.getCurrentFilePath();

        String configPath = rootPath + "/config.txt";
        System.out.println(configPath);
        List<String> result = IOUtil.readLines(configPath);

        for (String s : result) {
            System.out.println(s);
        }

    }




}
