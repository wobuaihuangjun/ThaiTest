package com.xtc.libthai.tool;

import com.xtc.libthai.seanlp.SeanlpThai;
import com.xtc.libthai.seanlp.util.IOUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目之外的文件操作
 * <p>
 * Created by huangzj on 2019/09/05.
 */
public class FileOperation {

    public static final String Operation_Path = IOUtil.getCurrentFilePath() + "/OperationFile/";

    public interface ConfigPrefix {
        String BreakString = "break_string_file";
        String BreakResult = "break_result_file";

        String NewWord = "new_word_file";

        String RepeatString = "repeat_string_file";
        String RepeatResult = "repeat_result_file";
    }

    /**
     * 从程序当前目录，加载配置文件
     */
    public static void loadConfigFile() {
        String configPath = Operation_Path + "config.txt";

        List<String> configList = IOUtil.readLines(configPath);

        for (String config : configList) {
            System.out.println(config);
        }
        System.out.println("配置文件加载完成：" + configPath);
    }

    public static void breakThaiTextBatch(String inputFileName, String outputFileName) {

        String inputPath = Operation_Path + inputFileName;
        long start = System.currentTimeMillis();

        List<String> stringList = IOUtil.readLines(inputPath);

        List<String> newList = new LinkedList<>();
        for (String str : stringList) {
            String result = breakThaiText(str);
            newList.add(result);
        }

        String outputPath = Operation_Path + outputFileName;
        IOUtil.overwriteLines(outputPath, newList);

        System.out.println("泰文批量分词 完成，耗时: " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 拆分单条泰文
     */
    public static String breakThaiText(String text) {
        return SeanlpThai.customMaxSegment(text);
    }

    /**
     * 移除文件中重复的数据行
     */
    public static void removeRepeatString(String inputFileName, String outputFileName) {
        String inputPath = Operation_Path + inputFileName;
        List<String> stringList = IOUtil.readLines(inputPath);

        List<String> newList = new LinkedList<>();

        for (String str : stringList) {
            if (!newList.contains(str)) {
                newList.add(str);
            }
        }
        String outputPath = Operation_Path + outputFileName;
        IOUtil.overwriteLines(outputPath, newList);
    }

    /**
     * Android string文件转换为纯粹的txt文件。
     */
    public static void androidStringXmlToTxt(String inputFileName, String outputFileName) {
        File inputXml = new File(FileOperation.Operation_Path + inputFileName);
        SAXReader saxReader = new SAXReader();

        List<String> stringList = new ArrayList<>();

        try {
            Document document = saxReader.read(inputXml);
            Element rootElement = document.getRootElement();
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element employee = (Element) iterator.next();

                String name = employee.getQualifiedName();
                if ("string".equals(name)) {
                    String valueName = employee.attributeValue("name");
                    String value = employee.getStringValue();
                    System.out.println(name + "---" + valueName + "---" + value);
                    stringList.add(value);
                } else {
                    System.out.println("unknown type. type:" + name);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        IOUtil.saveCollectionToTxt(stringList, FileOperation.Operation_Path + outputFileName);
    }

    /**
     * 解析xml文件
     *
     * @param fileName 文件名
     */
    public static void parserXml(String fileName) {
        File inputXml = new File(FileOperation.Operation_Path + fileName);
        SAXReader saxReader = new SAXReader();

        List<XmlString> stringList = new ArrayList<>();

        List<XmlStringArray> stringArrayList = new ArrayList<>();
        try {
            Document document = saxReader.read(inputXml);
            Element rootElement = document.getRootElement();
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element employee = (Element) iterator.next();

                String name = employee.getQualifiedName();
                if ("string".equals(name)) {
                    String valueName = employee.attributeValue("name");
                    String value = employee.getStringValue();
                    System.out.println(name + "---" + valueName + "---" + value);

//                    if (!employee.isTextOnly()) {
//                        for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
//                            Element node = (Element) j.next();
//
//                            System.out.println(node.getQualifiedName());
//                            System.out.println(node.attribute("id").getValue());
//                            System.out.println(node.getStringValue());
//                        }
//                    }

                    stringList.add(new XmlString(valueName, value));
                } else if ("string-array".equals(name)) {
                    XmlStringArray xmlStringArray = new XmlStringArray();

                    String valueName = employee.attributeValue("name");
                    System.out.println(name + "---" + valueName);

                    List<String> items = new ArrayList<>();
                    for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                        Element node = (Element) j.next();
                        System.out.println(node.getName() + ":" + node.getStringValue());
                        items.add(node.getStringValue());
                    }
                    xmlStringArray.setName(valueName);
                    xmlStringArray.setItem(items);
                    stringArrayList.add(xmlStringArray);
                } else {
                    System.out.println("unknown type. type:" + name);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 保存xml文件
//        createXml("back_" + fileName, stringList, stringArrayList);
    }

    /**
     * 创建Android的string.xml文件
     *
     * @param fileName 文件名
     */
    private static void createXml(String fileName, List<XmlString> stringList, List<XmlStringArray> stringArrayList) {
        Document document = DocumentHelper.createDocument();
        //xml的编码类型
        document.setXMLEncoding("UTF-8");

        //添加root节点
        Element resources = document.addElement("resources");

        //添加string节点
        for (XmlString xmlString : stringList) {
            Element string = resources.addElement("string");
            string.addAttribute("name", xmlString.getName());
            string.setText(xmlString.getValue());
        }

        //添加string-array节点
        for (XmlStringArray stringArray : stringArrayList) {
            Element string_array = resources.addElement("string-array");
            string_array.addAttribute("name", stringArray.getName());
            List<String> item = stringArray.getItem();
            for (String text : item) {
                string_array.addElement("item").setText(text);
            }
        }

        // 格式化输出xml
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(document.getXMLEncoding());

        XMLWriter xmlWriter = null;
        try {
            Writer fileWriter = new FileWriter(FileOperation.Operation_Path + fileName);
            xmlWriter = new XMLWriter(fileWriter, format);
            xmlWriter.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlWriter != null) {
                    xmlWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
