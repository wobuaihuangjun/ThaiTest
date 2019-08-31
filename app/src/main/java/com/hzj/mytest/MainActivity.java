package com.hzj.mytest;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.xtc.libthai.SeanlpThai;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity-hzjdemo：";

    static String[] text = {
            "หากคุณต้องการเปิดหรือปิดการแจ้งเตือนข้อความใหม่จาก",
            "รับรหัสยืนยันSMSล้มเหลว ไม่มีผู้ใช้งานนี้ กรุณากรอกใหม่อีกครั้ง รหัสข้อผิดพลาด",
            "กรุณากรอกค่าระดับสีเทา0หรือ619386",
            "คุณจะเสียสิทธิ์การเป็นแอดมินนาฬิกาไปหลังเปลี่ยนแอดมิน ต้องการเปลี่ยนแอดมินหรือไม่",
            "คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ",
            "กำลังจับคู่ กรุณารอสักครู่&#8230;",
            "School Numberต้องมีมากกว่า1หลัก",
            "นาฬิกาแบตเตอรี่ต่ำ เข้าสู่โหมดประหยัดพลังงาน ในโหมดนี้นาฬิกาจะไม่ได้เชื่อมต่ออินเทอร์เน็ต แต่สามารถโทรหาลูกได้เท่านั้น กรุณารีบติดต่อลูกเพื่อให้ชาร์จแบตเตอรี่นาฬิกา",
            "หลังจากปิด ผู้ติดต่อจะไม่เห็นนาฬิกาที่คุณเชื่อมต่อ เนื่องจากระบบโทรศัพท์บางรุ่นหรือการข้อจำกัดของการรักษาความปลอดภัยของซอฟต์แวร์ โทรศัพท์มือถือของคุณอาจจะไม่สามารุถรับการแจ้งเตือนใหม่ๆจาก %1$s ได้",
            "แพ็คเกจโทรที่สมัครไว้ สามารถโทรหา2&#8211;Short Number6หลักเพื่อโทรศัพท์."
    };

    TextView textView;

    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_thai);

        textView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        textView.append("\n\n" + textView.getHeight() + "," + textView.getWidth());

                        width = textView.getWidth();
                        System.out.println("width： " + width);
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String s : text) {
                            stringBuilder.append(s).append("\n\n");
                            stringBuilder.append(SeanlpThai.customMaxSegment(s)).append("\n\n");

                            String result = SeanlpThai.breakStringByCustomMaxSegment(s, width);
                            stringBuilder.append(result).append("\n\n");
                        }
                        textView.setText(stringBuilder.toString());
                    }
                });
    }

}
