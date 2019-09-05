package com.xtc.libthai.seanlp;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import com.xtc.libthai.seanlp.segmenter.domain.Term;
import com.xtc.libthai.seanlp.tokenizer.ThaiCustomMatchTokenizer;
import com.xtc.libthai.seanlp.tokenizer.ThaiMatchTokenizer;

import java.util.List;

public class SeanlpThai {

    private static final String TAG = "SeanlpThai: ";

    private static String toString(List<Term> terms) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            line.append(terms.get(i).getWord()).append("|");
        }
        return line.toString();
    }


    public static String customMaxSegment(String text) {
        List<Term> terms = ThaiCustomMatchTokenizer.customMaxSegment(text);

        return toString(terms);
    }

    /**
     * 泰语正向最大分词
     *
     * @param text 原文本
     * @return 拆分后的文本，以“|”拆分
     */
    public static String maxSegment(String text) {
        List<Term> terms = ThaiMatchTokenizer.maxSegment(text);
        return toString(terms);
    }

    /**
     * 将文本进行换行处理
     *
     * @param text   待处理文本
     * @param length 每行最大长度
     * @return 添加了换行符之后的文本
     */
    public static String breakString(String text, int length, Paint textPain) {
        List<Term> terms = ThaiCustomMatchTokenizer.customMaxSegment(text);
        Config.Log.logger.info("分词: " + toString(terms));

        return breakString(terms, length, textPain);
    }

    /**
     * 将文本进行换行处理
     *
     * @param textView 显示文本的TextView
     * @param text     text to be displayed
     */
    public static void setBreakText(final TextView textView, final String text) {
        textView.setText(text);// 部分控件拿到的width偏小，暂时先设置下原文本

        textView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        int length = getTextViewWidth(textView);
                        Config.Log.logger.info(TAG + "length: " + length);

                        if (length > 0) {
                            setText(textView, text, length);
                        }
                    }
                });
    }

    private static void setText(TextView textView, String text, int length) {
        List<Term> terms = ThaiCustomMatchTokenizer.customMaxSegment(text);
        Config.Log.logger.info(TAG + "分词: " + SeanlpThai.toString(terms));

        textView.setText(SeanlpThai.breakString(terms, length, textView.getPaint()));
    }

    /**
     * 获取TextView可显示文本的宽度
     */
    private static int getTextViewWidth(TextView textView) {
        int width = textView.getWidth();
        int padLeft = textView.getTotalPaddingLeft();
        int padRight = textView.getTotalPaddingRight();

        int length = width - padLeft - padRight;
        Config.Log.logger.info(TAG + "width: " + length);

        if (length <= 0) {
            Config.Log.logger.info(TAG + "getParent");

            width = getParentViewWidth(textView);
            length = width - padLeft - padRight;
        }

        return length;
    }

    /**
     * 获取父控件的宽度
     */
    private static int getParentViewWidth(View view) {
        if (view == null) {
            return 0;
        }

        ViewGroup mViewGroup = (ViewGroup) view.getParent();
        if (mViewGroup != null) {
            int width = mViewGroup.getWidth();
            int padLeft = mViewGroup.getPaddingLeft();
            int padRight = mViewGroup.getPaddingRight();

            int length = width - padLeft - padRight;
            if (length <= 0) {
                return getParentViewWidth(mViewGroup);
            } else {
                return length;
            }
        } else {
            Config.Log.logger.info(TAG + "getParent is null");
            return getScreenWidth(view.getContext());
        }
    }

    /**
     * 获取屏幕宽度
     */
    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * 将文本进行换行处理
     *
     * @param terms  待处理分词List
     * @param length 每行最大长度
     * @return 添加了换行符之后的文本
     */
    private static String breakString(List<Term> terms, int length, Paint textPain) {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        for (Term term : terms) {
            String word = term.getWord();
            if (word.length() <= 0) {
                continue;
            }
            if (line.length() > 0) {
                float stringWidth = textPain.measureText(line + word);
                if (stringWidth < length) {
                    // 未超过最大长度,继续追加
                    line = line + word;
                } else {
//                    System.out.println("另起一行: " + line);
                    stringBuilder.append(line).append("\n");
                    line = word;// 最新的词另起一行
                }
            } else {
                line = word;
            }
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }

}
