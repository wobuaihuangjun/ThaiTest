package com.xtc.libthai.seanlp;

import com.xtc.libthai.seanlp.util.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangzj on 2016/3/17.
 */
public class JavaDomain {

    static String[] text = {
            "หากคุณต้องการเปิดหรือปิดการแจ้งเตือนข้อความใหม่จาก",
            "โครงการปรับปรุงประสบการณ์การใช้งาน",
            "กรุณากรอกค่าระดับสีเทา0หรือ619386",
            "คุณจะเสียสิทธิ์การเป็นแอดมินนาฬิกาไปหลังเปลี่ยนแอดมิน ต้องการเปลี่ยนแอดมินหรือไม่",
            "คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ",
            "กรุณารอสักครู่&#8230;",
            "School Numberต้องมีมากกว่า1หลัก",
            "นาฬิกาอาจทำงานช้าเมื่อมีรายชื่อผู้ติดต่อถึง50คน",
            "กำลังจับคู่ กรุณารอสักครู่&#8230;",
            "หลังจากปิด ผู้ติดต่อจะไม่เห็นนาฬิกาที่คุณเชื่อมต่อ",
            "แพ็คเกจโทรที่สมัครไว้ สามารถโทรหา2&#8211;Short Number6หลักเพื่อโทรศัพท์."
    };
//    โครงการปรับปรุงประสบการณ์การใช้งาน
//    หากคุณต้องการเปิดหรือปิดการแจ้งเตือนข้อความใหม่จาก
//    หาก|คุณ|ต้องการ|เปิด|หรือ|ปิด|การ|แจ้งเตือน|ข้อความ|ใหม่|จาก|
//
//    โครงการปรับปรุงประสบการณ์การใช้งาน
//    โครงการ|ปรับปรุง|ประสบการณ์|การใช้งาน|
//
//    กรุณากรอกค่าระดับสีเทา0หรือ619386
//    กรุณา|กรอก|ค่า|ระดับสีเทา|0|หรือ|619386|
//
//    คุณจะเสียสิทธิ์การเป็นแอดมินนาฬิกาไปหลังเปลี่ยนแอดมิน ต้องการเปลี่ยนแอดมินหรือไม่
//    คุณ|จะ|เสีย|สิทธิ์|การ|เป็น|แอดมิน|นาฬิกา|ไป|หลัง|เปลี่ยน|แอดมิน|ต้องการ|เปลี่ยน|แอดมิน|หรือไม่|
//
//    คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ
//    คุณ|สามารถ|ดำเนินการ|Handle|ใน|การ|จัด|ลำดับ|รายชื่อ|
//
//    กรุณารอสักครู่&#8230;
//    กรุณา|รอ|สักครู่|&#|8230|;|
//
//    School Numberต้องมีมากกว่า1หลัก
//    School|Number|ต้อง|มี|มากกว่า|1|หลัก|
//
//    นาฬิกาอาจทำงานช้าเมื่อมีรายชื่อผู้ติดต่อถึง50คน
//    นาฬิกา|อาจ|ทำงาน|ช้า|เมื่อ|มี|รายชื่อ|ผู้ติดต่อ|ถึง|50|คน|
//
//    กำลังจับคู่ กรุณารอสักครู่&#8230;
//    กำลัง|จับคู่|กรุณา|รอ|สักครู่|&#|8230|;|
//
//    หลังจากปิด ผู้ติดต่อจะไม่เห็นนาฬิกาที่คุณเชื่อมต่อ
//    หลังจาก|ปิด|ผู้ติดต่อ|จะ|ไม่เห็น|นาฬิกา|ที่|คุณ|เชื่อมต่อ|
//
//    แพ็คเกจโทรที่สมัครไว้ สามารถโทรหา2&#8211;Short Number6หลักเพื่อโทรศัพท์.
//    แพ|็คเกจ|โทร|ที่|สมัคร|ไว้|สามารถ|โทร|หา|2|&#|8211|;|Short|Number|6|หลัก|เพื่อ|โทรศัพท์|.|

    public static void main(String[] args) {
//        testThai(text[0]);
//        System.out.println();

//        System.out.println("กรุณารอ");
//        System.out.println("      maxSegment: " + SeanlpThai.maxSegment("กรุณารอ"));
//        System.out.println("customMaxSegment: " + SeanlpThai.customMaxSegment("กรุณารอ"));

//        System.out.println("คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ");
//        String result = SeanlpThai.breakStringByCustomMaxSegment("คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ", 20);
//        System.out.println(result);

//        for (String s : text) {
//            maxSegmentTest(s);
//        }

//        xmlToTxt();

//        testString();

//        removeRepeatString();
    }

    private static void maxSegmentTest(String thText) {
        System.out.println(thText);

        long start = System.currentTimeMillis();
        String result = SeanlpThai.maxSegment(thText);
        System.out.println("      maxSegment: " + result + "time cost1: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        result = SeanlpThai.customMaxSegment(thText);
        System.out.println("customMaxSegment: " + result + "time cost2: " + (System.currentTimeMillis() - start));

        System.out.println();
    }

    private static void testThai(String thText) {
        //泰语分词
        System.out.println("0: " + thText);
        System.out.println();

        System.out.println("1: " + SeanlpThai.maxSegment(thText));// 词汇最大化拆分
        System.out.println("2: " + SeanlpThai.customMaxSegment(thText));// 词汇最大化拆分

        System.out.println();
    }

    private static void testString() {
        String path = "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/thai-string.txt";
        long start = System.currentTimeMillis();

        List<String> stringList = IOUtil.readLines(path);

        List<String> newList = new LinkedList<>();
        for (String str : stringList) {
            String result = SeanlpThai.customMaxSegment(str);
            newList.add(result);
        }

        System.out.println("testString time cost2: " + (System.currentTimeMillis() - start));

        String segmentPath = "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/thai-segment-string.txt";
        IOUtil.overwriteLines(segmentPath, newList);
    }

    /**
     * 移除文件中重复的数据行
     */
    private static void removeRepeatString() {
        String path = "C:/Code/GitHubProject/ThaiTest/thailib/src/main/resources/com/xtc/thai-string.txt";

        List<String> stringList = IOUtil.readLines(path);

        List<String> newList = new LinkedList<>();

        for (String str : stringList) {
            if (!newList.contains(str)) {
                newList.add(str);
            }
        }

        IOUtil.overwriteLines(path, newList);
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
