package com.xtc.libthai.tool;

import com.xtc.libthai.seanlp.tokenizer.ThaiCustomMatchTokenizer;

/**
 * Created by huangzj on 2019/09/05.
 */
public class ToolThaiDomain {

    public static void main(String[] args) {
//        removeRepeatString();

//        FileOperation.loadConfigFile();
//
//        insertNewWord();
//
//        breakThaiTextBatch();

        recordUsedWord();

//        FileOperation.parserXml("xml_parse.xml");
//        FileOperation.createXml("xml_create.xml");
    }

    /**
     * 过滤出已被匹配的词汇
     */
    private static void recordUsedWord() {
        ThaiCustomMatchTokenizer.enableSaveUsedWord(FileOperation.Operation_Path + "used_word");

        //将xml文件的String提取出来，保存成txt
        FileOperation.androidStringXmlToTxt("strings.xml", "string_to_txt.txt");
        //将txt格式的字符，按行进行分词
        FileOperation.breakThaiTextBatch("string_to_txt.txt", "strings_break_result.txt");

        ThaiCustomMatchTokenizer.disenableSaveUsedWord();
    }

    /**
     * 从程序所在文件路径，读取新词条，与当前词库进行比较，生成新增词条文件和总的词条文件
     */
    private static void insertNewWord() {
        ThaiCustomMatchTokenizer.insertNewWord(FileOperation.Operation_Path, "new_word");
    }

    /**
     * 移除文件中重复的数据行
     */
    private static void removeRepeatString() {
        FileOperation.removeRepeatString("repeat_string.txt", "repeat_result.txt");
    }

}
