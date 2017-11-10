package com.daydreamer.ggiot.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by StevenGo on 2017/11/9.
 */

public class DengFontEditText extends EditText {
    public DengFontEditText(Context context) {
        this(context,null);
    }

    public DengFontEditText(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public DengFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Dengl.ttf");
        this.setTypeface(typeface);
    }

}
