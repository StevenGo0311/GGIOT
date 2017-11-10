package com.daydreamer.ggiot.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by StevenGo on 2017/11/9.
 */

public class DengFontCountDownButton extends CountDownButton {
    public DengFontCountDownButton(Context context) {
        this(context,null);
    }

    public DengFontCountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DengFontCountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Dengl.ttf");
        this.setTypeface(typeface);
    }
}
