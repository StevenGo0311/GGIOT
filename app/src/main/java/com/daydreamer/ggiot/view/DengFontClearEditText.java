package com.daydreamer.ggiot.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by StevenGo on 2017/11/9.
 */

public class DengFontClearEditText extends ClearEditText {
    public DengFontClearEditText(Context context) {
        this(context,null);
    }

    public DengFontClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public DengFontClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Dengl.ttf");
        this.setTypeface(typeface);

    }
}
