package com.xtc.libthai.tool;

import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.SeanlpThai;
import com.xtc.libthai.seanlp.tokenizer.ThaiCustomMatchTokenizer;
import com.xtc.libthai.seanlp.util.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangzj on 2019/09/05.
 */
public class ToolThaiDomain {

    public static void main(String[] args) {

//        xmlToTxt();

//        testString();

//        removeRepeatString();

//        FileOperation.loadConfigFile();
//
//        insertNewWord();
//
//        breakThaiTextBatch();

        FileOperation.parserXml("test.xml");
//        FileOperation.createXml("xml_create.xml");
    }

    /**
     * 从程序所在文件路径，读取新词条，与当前词库进行比较，生成新增词条文件和总的词条文件
     */
    private static void insertNewWord(){
        ThaiCustomMatchTokenizer.insertNewWord(FileOperation.Operation_Path, "new_word");
    }

    /**
     * 将txt格式的字符，按行进行分词
     */
    private static void breakThaiTextBatch() {
        FileOperation.breakThaiTextBatch("break_string", "break_result");
    }

    /**
     * 移除文件中重复的数据行
     */
    private static void removeRepeatString() {
        FileOperation.removeRepeatString("repeat_string", "repeat_result");
    }

    /**
     * 将xml文件的String提取出来，保存成txt
     */
    private static void xmlToTxt() {
        IOUtil.saveCollectionToTxt(loadXmlData(), "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/thai-string.txt");
    }

    private static List<String> loadXmlData() {
        String path = "/com/xtc/strings.txt";
        List<String> wordList = new LinkedList<>();

        BufferedReader br = null;
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStream(path);
            if (inputStream == null) {
                return wordList;
            }
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String text = "";
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("<string")) {//行开始
                    text = line;
                } else {
                    text = text + line;
                }

                if (line.contains("</string>")) {//行结束
//                    System.out.println(text);
//                    System.out.println();

                    if (text.trim().length() > 0) {
                        text = text.replace("</string>", "");
                        String[] split = text.split(">");


                        if (split.length == 2) {
                            wordList.add(split[1]);
                        } else {
//                            System.out.println("split length:" + split.length);
//                            System.out.println(split[0]);
//                            System.out.println(split[1]);
                        }
                    }
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return wordList;
    }

}
