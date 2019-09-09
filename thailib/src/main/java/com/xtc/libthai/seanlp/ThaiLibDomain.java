package com.xtc.libthai.seanlp;

import com.xtc.libthai.seanlp.util.IOUtil;

/**
 * Created by huangzj on 2019/8/17.
 */
public class ThaiLibDomain {

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
        System.out.println("customMaxSegment: " + SeanlpThai.customMaxSegment("ดำเนินการตรวจสอบอินเทอร์เน็ตจะช่วยให้เราแก้ไขปัญหาของคุณได้เร็วขึ้น"));

//        System.out.println("คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ");
//        String result = SeanlpThai.breakStringByCustomMaxSegment("คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ", 20);
//        System.out.println(result);

//        for (String s : text) {
//            maxSegmentTest(s);
//        }

//        System.out.println(IOUtil.getCurrentFilePath());//user.dir指定了当前的路径
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

}
