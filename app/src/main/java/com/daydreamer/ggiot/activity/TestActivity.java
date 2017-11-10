package com.daydreamer.ggiot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daydreamer.ggiot.R;
import com.daydreamer.ggiot.view.ClearEditText;
import com.daydreamer.ggiot.view.CountDownButton;

public class TestActivity extends AppCompatActivity {
    CountDownButton countDownButton;
    ClearEditText clearEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }
}
