package com.daydreamer.ggiot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daydreamer.ggiot.R;
import com.daydreamer.ggiot.Util.GeneralUtil;

public class RetrievePassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneralUtil.translucentStatus(this);
        setContentView(R.layout.activity_retrieve_pass);
    }
}
