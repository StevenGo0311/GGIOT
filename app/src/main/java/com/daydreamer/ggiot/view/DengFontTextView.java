package com.daydreamer.ggiot.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by StevenGo on 2017/11/9.
 */

public class DengFontTextView extends TextView {
    public DengFontTextView(Context context) {
        this(context,null);
    }

    public DengFontTextView(Context context,AttributeSet attrs) {
        this(context, attrs,android.R.attr.textViewStyle);
    }

    public DengFontTextView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Dengl.ttf");
        this.setTypeface(typeface);
    }
}