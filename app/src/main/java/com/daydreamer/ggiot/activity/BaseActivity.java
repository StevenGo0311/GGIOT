package com.daydreamer.ggiot.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.daydreamer.ggiot.R;

public class BaseActivity extends AppCompatActivity {

    protected void showLongToast(int textId){
        String text=getString(textId);
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
    protected void showShortToast(int textId){
        String text=getString(textId);
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
