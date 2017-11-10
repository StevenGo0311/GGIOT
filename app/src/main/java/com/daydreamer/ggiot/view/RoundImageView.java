package com.daydreamer.ggiot.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.daydreamer.ggiot.R;


/**
 * Created by StevenGo on 2017/11/9.
 * 圆形和圆角矩形
 */

public class RoundImageView extends ImageView {
    /**
     * 图片类型，圆角或者圆形
     */
    private int type;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;
    /**
     * 绘图的Paint
     */
    private Paint mPaint;
    /**
     * 圆的半径
     */
    private int mRadius;
    /**
     * 缩放的矩阵
     */
    private Matrix mMatrix;
    /**
     * 渲染图像
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;
    /**
     * 默认圆角的大小
     */
    private static final int BORDER_RADIUS_DEFAULT = 10;

    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //初始化，读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mBorderRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                BORDER_RADIUS_DEFAULT, getResources()
                        .getDisplayMetrics()));
        type = typedArray.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果是圆形，以小的边为准使宽高一致
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    /**
     * 设置bitmapshader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        //得到bitmap
        Bitmap bitmap = drawableToBitmap(drawable);
        //将bitmap设置给bitmapshader,模式为拉伸
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        //当类型是圆形的时候，拿到bitmap宽和高的最小值
        if (type == TYPE_CIRCLE) {
            int bSize = Math.max(bitmap.getWidth(), bitmap.getHeight());
            scale = mWidth * 1.0f / bSize;
        } else if (type == TYPE_ROUND) {
            scale = Math.min(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
        }
        //shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        //设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        //设置shader
        mPaint.setShader(mBitmapShader);
    }

    /**
     * 通过转化得到bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        //如果drawable是bitmapdrawable的对象，直接得到其bitmap
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }
        //否则，进行下面的操作
        //得到drawable固有的宽度和高度
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        //创建和drawable大小一样的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //如果没有drawable,直接返回
        if (getDrawable() == null) {
            return;
        }
        //设置shader
        setUpShader();
        //绘制出同的图形
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mPaint);
        } else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND) {
            //创建矩形
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    //设置圆角大小
    public void setBorderRadius(int borderRadius) {
        int pxVal = dp2px(borderRadius);
        if (this.mBorderRadius != pxVal) {
            this.mBorderRadius = pxVal;
            invalidate();
        }
    }

    //设置绘制类型
    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }

    }

    /**
     * 单位转换
     */
    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
