package com.daydreamer.ggiot.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.daydreamer.ggiot.R;
import com.daydreamer.ggiot.Util.Constant;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by StevenGo on 2017/11/8.
 * 自定义按钮，加入倒计时功能
 */

public class CountDownButton extends Button {
    /**
     * 倒计时时长，以毫秒为单位
     */
    private int countDownMillisecond;
    /**
     * 临时时长，以秒为单位
     */
    private int tempCountDownsecond;
    /**
     * 正常情况下按钮显示的文字
     */
    private String textNomal;
    /**
     * 倒计时的时候显示的文字
     */
    private String textCountDowning;
    /**
     * 定时任务
     */
    private TimerTask timerTask;
    /**
     * 更新ui
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.COUNT_DOWN_START:
                    //按钮设置为不可点击
                    setEnabled(false);
                    //设置文字为剩余时间+固定内容
                    setText((tempCountDownsecond + 1) + textCountDowning);
                    break;
                case Constant.COUNT_DOWN_FINISH:
                    //按钮设置为可点击
                    setEnabled(true);
                    //设置文字为正常情况下的文字
                    setText(textNomal);
                    break;
            }
        }
    };

    private static final String TAG = CountDownButton.class.getSimpleName();

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        //设置正常情况下显示的文字
        textNomal = getText().toString().trim();
        //设置倒计时的固定文字
        textCountDowning = getResources().getString(R.string.string_countDownButton_disable);
        //设置倒计时默认时间，60s
        countDownMillisecond = Constant.COUNT_DOWN_MILLISECOND;
        //为按钮添加单击监听
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownStartAndFinish();
            }
        });
    }

    /**
     * 控制开始和结束倒计时
     */
    private void countDownStartAndFinish() {
        //定时器
        Timer timer = new Timer();
        //设置临时时长
        tempCountDownsecond = countDownMillisecond / 1000;
        //定时任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //判断剩余时间是否大于0
                if (tempCountDownsecond > 0) {
                    handler.sendEmptyMessage(Constant.COUNT_DOWN_START);
                    tempCountDownsecond--;
                } else {
                    handler.sendEmptyMessage(Constant.COUNT_DOWN_FINISH);
                    //停止计时
                    countDownStop();
                }
            }
        };
        //定时任务，从0开始，每1s执行一次
        timer.schedule(timerTask, Constant.COUNT_DOWN_SECOND_START, Constant.COUNT_DOWN_SECOND_STEP);
    }

    /**
     * 停止定时任务
     */
    private void countDownStop() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        countDownStop();
        super.onDetachedFromWindow();
    }

    public int getCountDownMillisecond() {
        return countDownMillisecond;
    }

    public void setCountDownMillisecond(int countDownMillisecond) {
        this.countDownMillisecond = countDownMillisecond;
    }

    public String getTextCountDowning() {
        return textCountDowning;
    }

    public void setTextCountDowning(String textCountDowning) {
        this.textCountDowning = textCountDowning;
    }
}
