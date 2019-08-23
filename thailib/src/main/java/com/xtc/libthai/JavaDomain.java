package com.xtc.libthai;


import cn.edu.kmust.seanlp.SEANLP;

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


    public static void main(String[] args) {
//        String thText = "กรุณากรอกค่าระดับสีเทา0หรือ619386";
//        testThai(thText);
//        System.out.println();
//        System.out.println();


//        for (String value : text) {
//            syllableSegmentTest(value);
//        }

        for (String s : text) {
            maxSegmentTest(s);
        }

    }

    private static void maxSegmentTest(String thText) {
        System.out.println(thText);
//        System.out.println(SeanlpThai.maxSegment(thText));
        System.out.println(SeanlpThai.maxSegmentText(thText));
        System.out.println();
    }

    private static void syllableSegmentTest(String thText) {
        System.out.println(thText);
        System.out.println(SeanlpThai.syllableSegment(thText));
        System.out.println();
    }

    private static void testThai(String thText) {
        //泰语分词
        System.out.println("0: " + thText);
        System.out.println("1: " + SeanlpThai.segmentTCC(thText));


        System.out.println("2: " + SeanlpThai.syllableSegment(thText));
        System.out.println("3: " + SeanlpThai.datSegment(thText));
        System.out.println("4: " + SeanlpThai.dCRFSegment(thText));
        System.out.println("5: " + SeanlpThai.gCRFSegment(thText));

        System.out.println("6: " + SeanlpThai.maxSegment(thText));// 词汇最大化拆分
        System.out.println("7: " + SeanlpThai.minSegment(thText));// 拆分太细
        System.out.println("8: " + SeanlpThai.reMaxSegment(thText));// 词汇最大化拆分 同maxSegment结果一致
        System.out.println("9: " + SeanlpThai.reMinSegment(thText));
    }

}
