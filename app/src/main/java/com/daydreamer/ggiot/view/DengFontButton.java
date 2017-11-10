package com.daydreamer.ggiot.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by StevenGo on 2017/11/9.
 */

public class DengFontButton extends Button {
    public DengFontButton(Context context) {
        this(context,null);
    }

    public DengFontButton(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.buttonStyle);
    }

    public DengFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Dengl.ttf");
        this.setTypeface(typeface);
    }
}
