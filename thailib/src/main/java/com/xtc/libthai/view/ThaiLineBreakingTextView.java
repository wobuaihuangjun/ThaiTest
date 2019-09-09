package com.xtc.libthai.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.xtc.libthai.seanlp.SeanlpThai;

public class ThaiLineBreakingTextView extends AppCompatTextView {

    public ThaiLineBreakingTextView(Context context) {
        super(context);
    }

    public ThaiLineBreakingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThaiLineBreakingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//
//        //        获取文字内容
//        String text = getText().toString();
//
//        String result = SeanlpThai.breakString(text, getWidth(), getPaint());
//
//        setText(result);
//
//        super.onDraw(canvas);
//    }

}
