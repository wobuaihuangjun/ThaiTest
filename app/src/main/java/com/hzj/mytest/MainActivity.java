package com.hzj.mytest;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.tlb._interface.ThaiLineBreaker;
import com.tlb.engine.ImmutableContainmentSearcher;
import com.tlb.engine.ImmutableInverseTrie;
import com.tlb.engine.ThaiLineBreakerImpl;
import com.tlb.util.ThaiUtil;
import com.tlb.util.Utils;
import com.xtc.libthai.seanlp.Config;
import com.xtc.libthai.seanlp.SeanlpThai;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MainActivity extends Activity {

    static String[] text = {
            "หากคุณต้องการเปิดหรือปิดการแจ้งเตือนข้อความใหม่จาก",
            "รับรหัสยืนยันSMSล้มเหลว ไม่มีผู้ใช้งานนี้ กรุณากรอกใหม่อีกครั้ง รหัสข้อผิดพลาด",
//            "กรุณากรอกค่าระดับสีเทา0หรือ619386",
            "คุณจะเสียสิทธิ์การเป็นแอดมินนาฬิกาไปหลังเปลี่ยนแอดมิน ต้องการเปลี่ยนแอดมินหรือไม่",
//            "คุณสามารถดำเนินการHandleในการจัดลับดับรายชื่อ",
//            "กำลังจับคู่ กรุณารอสักครู่&#8230;",
//            "School Numberต้องมีมากกว่า1หลัก",
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

        Config.BaseConf.enableDebug();

        textView = findViewById(R.id.text_thai);


        TextView textViewSetThai = findViewById(R.id.textview_thai_set_text1);
        Button buttonSetThai1 = findViewById(R.id.button_thai_set_text1);

        Button buttonSetThai2 = findViewById(R.id.button_thai_set_text2);

        TextView textViewSetThai2 = findViewById(R.id.textview_thai_set_text2);
        TextView textViewSetThai3 = findViewById(R.id.textview_thai_set_text3);

        textView.setText(SeanlpThai.customMaxSegment(text[0]));

        SeanlpThai.setBreakText(textViewSetThai, text[0]);

        SeanlpThai.setBreakText(textViewSetThai2, text[0]);

        SeanlpThai.setBreakText(textViewSetThai3, text[0]);

        SeanlpThai.setBreakText(buttonSetThai1, text[0]);
        SeanlpThai.setBreakText(buttonSetThai2, text[0]);//此种布局拿到的width会偏小，提前set一下原文才能正常。

//        setText();

//        textView.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        textView.append("\n\n" + textView.getHeight() + "," + textView.getWidth());
//
//                        width = textView.getWidth();
//                        System.out.println("width： " + width);
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (String s : text) {
//                            stringBuilder.append(s).append("\n\n");
//                            stringBuilder.append(SeanlpThai.customMaxSegment(s)).append("\n\n");
//
//                            String result = SeanlpThai.breakStringByCustomMaxSegment(s, width);
//                            stringBuilder.append(result).append("\n\n");
//                        }
//                        textView.setText(stringBuilder.toString());
//                    }
//                });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        handler.postDelayed(runnable, 500);
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            width = textView.getWidth();
            System.out.println("width： " + width);
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : text) {
                stringBuilder.append(s).append("\n\n");
                stringBuilder.append(SeanlpThai.customMaxSegment(s)).append("\n\n");

                String result = SeanlpThai.breakString(s, width, textView.getPaint());
                stringBuilder.append(result).append("\n\n");
            }
            textView.setText(stringBuilder.toString());
        }
    };

    private void setText() {
        width = textView.getWidth();
        System.out.println("width： " + width);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : text) {
            stringBuilder.append(s).append("\n\n");

//            CharSequence result = doBreakText(s, getInnerWidth(textView), textView.getPaint());
//            stringBuilder.append(result).append("\n\n");
        }

        textView.setText(stringBuilder.toString());
    }

    private static final char NBSP = '\u00a0';

    private static CharSequence doBreakText(CharSequence originalString,
                                            int innerWidth, Paint textPainter) {
        if (innerWidth <= 0)
            return originalString;
        if (innerWidth < 100)
            System.err.println("inner width of less than 100 is highly not recommended");
        String nbspString = originalString.toString().replace(' ', NBSP);
        List<String> lines = Utils.longStringToList(nbspString);
        StringBuilder ans = new StringBuilder();
        for (String s : lines)
            ans.append(breakLine(s, innerWidth, textPainter)).append('\n');
        return Utils.trimTrailingWhiteSpace(ans);
    }

    private static final int RESOURCE_TRIE_CACHE = R.raw.trie_cache,
            RESOURCE_WHOLE_WORD_CACHE = R.raw.whole_word_cache;

    private static class Holder {
        private static volatile ThaiLineBreaker breaker;

        private synchronized static void init(Resources res) {
            ThaiLineBreaker temp = breaker;
            if (temp == null) {// second lock
                try {
                    // create ans1
                    InputStream is = res.openRawResource(RESOURCE_TRIE_CACHE);
                    ImmutableInverseTrie ans1 = ImmutableInverseTrie
                            .deserialize(is);
                    // create ans2
                    is = res.openRawResource(RESOURCE_WHOLE_WORD_CACHE);
                    ImmutableContainmentSearcher ans2 = ImmutableContainmentSearcher
                            .deserialize(is);
                    breaker = new ThaiLineBreakerImpl(ans1, ans2);
                } catch (IOException e) {
                    e.printStackTrace();
                    breaker = null;
                }
            }
        }
    }

    private static CharSequence breakLine(String oneLine, int innerWidth, Paint textPainter) {
        ThaiLineBreaker breaker = Holder.breaker;
        StringBuilder ans = new StringBuilder(oneLine);
        int pos = 0, count = 0;
        /*
         * FIXME count < oneLine.length() to prevent infinite loop in jb??
         */
        while (pos < oneLine.length() && count < oneLine.length()) {
            // TODO trim leading space
            pos = offsetLeadingSpace(oneLine, pos);
            int maxText = textPainter.breakText(oneLine, pos, oneLine.length(),
                    true, innerWidth, null);
            // FIXME troublesome jellybean and correct this function when
            // android fix this problem
            if (Build.VERSION.SDK_INT >= 16)
                maxText = resolveCorrectPositionForJB(oneLine, pos,
                        oneLine.length(), maxText);
            int breakAt = breaker.breakLine(oneLine, pos + maxText);
            count += addAdditionalSpacing(pos + maxText, breakAt, oneLine, ans,
                    pos, count);
            pos = breakAt;
            ans.insert(pos + count, '\n');
            count++;
        }
        return ans;
    }

    private static int offsetLeadingSpace(String line, int offset) {
        while (offset < line.length()) {
            char c = line.charAt(offset);
            if (Character.isWhitespace(c))
                offset++;
            else
                break;
        }
        return offset;
    }

    /**
     * SaraUm which is one character is misunderstood by native android to be 2
     * chars so we need to convert it back. Damned u Android!!
     */
    private static int resolveCorrectPositionForJB(String s, int start,
                                                   int end, int pos) {
        TreeSet<Integer> saraUmPos = new TreeSet<Integer>();
        for (int i = start; i < end; i++)
            if (s.charAt(i) == 'ำ')
                saraUmPos.add(i - start);
        return pos - saraUmPos.headSet(pos).size();
    }

    /**
     * Make all spaces in this line become double space if not already is. We do
     * this only when the right side has too many space to spare from line
     * breaking algorithm.
     */
    private static int addAdditionalSpacing(int attempt, int actualBreak,
                                            String s, StringBuilder ans, int offset, int offset2) {
        final int countSpace = ThaiUtil.countNonZeroWidth(s, actualBreak,
                attempt);
        ArrayList<Integer> addingPlaces = new ArrayList<Integer>();
        boolean isPreviousOneSpace = false;
        for (int i = offset + offset2 + 1; i < actualBreak + offset2 - 2; i++) {
            if (!ThaiUtil.isWhiteSpace(ans.charAt(i))) {
                isPreviousOneSpace = false;
                continue;
            }
            if (isPreviousOneSpace) {
                if (addingPlaces.get(addingPlaces.size() - 1) == i - 1)
                    addingPlaces.remove(addingPlaces.size() - 1);
                continue;
            }
            addingPlaces.add(i);
            isPreviousOneSpace = true;
        }
        if (countSpace < addingPlaces.size())
            return 0;
        for (int i = 0; i < addingPlaces.size(); i++)
            ans.insert(i + addingPlaces.get(i), NBSP);
        return addingPlaces.size();
    }

    private int getInnerWidth(TextView textView) {
        int ans = textView.getWidth() - textView.getTotalPaddingLeft() - textView.getTotalPaddingRight()
                - 5;
        // FIXME troublesome jellybean and correct this function when android
        // fix this problem
        return Build.VERSION.SDK_INT >= 16 ? ans - 5 : ans;
    }
}
