package com.daydreamer.ggiot.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.daydreamer.ggiot.R;
import com.daydreamer.ggiot.Util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动页，检测权限
 */
public class SplashActivity extends BaseActivity {
    /**
     * Log的TAG
     */
    private final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载布局文件
        setContentView(R.layout.activity_splash);
        //初始化权限
        initPermission();
    }

    /**
     * 启动登录页面并结束SplashActivity
     */
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 延时启动页面
     */
    private void delayedSkip(int millisecond) {
        Timer timer = new Timer();
        //执行的任务，启动登录页面
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startLoginActivity();
            }
        };
        //延时执行任务
        timer.schedule(timerTask, millisecond);
    }

    /**
     * 初始化权限的方法
     */
    private void initPermission() {
        //判断当前编译版本是否大于M
        //如果小于M直接延时启动
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            delayedSkip(Constant.DELAYED_MILLISECOND);
            return;
        }
        //当有未授权的权限时进行权限请求
        if (checkPermission().length != 0) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    checkPermission(),
                    Constant.PERMISSON_REQUESTCODE);
            //没有未授权的权限时延时启动登录页面
        } else {
            delayedSkip(Constant.DELAYED_MILLISECOND);
        }
    }

    /**
     * 检查未授权的权限
     */
    private String[] checkPermission() {
        //存储未授权的权限
        List<String> needPermissionsTemp = new ArrayList<>();
        //检查需要的权限是否已经授权，将未授权的权限保存到list中
        for (int i = 0; i < Constant.PERMISSIONS.length; i++) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this, Constant.PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                needPermissionsTemp.add(Constant.PERMISSIONS[i]);
            }
        }
        //将list中的权限转换成array存储
        String[] needPermissions = new String[needPermissionsTemp.size()];
        for (int i = 0; i < needPermissions.length; i++) {
            needPermissions[i] = needPermissionsTemp.get(i);
        }
        return needPermissions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean flag = true;
        switch (requestCode) {
            case Constant.PERMISSON_REQUESTCODE:
                //检查是否有未授权的权限
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                        break;
                    }
                }
                //如果没有未授权的权限，打开登录页面
                if (flag) {
                    delayedSkip(Constant.DELAYED_MILLISECOND);
                    //如果有未授权的权限，引导用户授权
                } else {
                    showAlterDialog(R.string.string_dialog_title_help, R.string.string_dialog_content_lack_permission);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 显示提示对话框
     */
    private void showAlterDialog(int titleId, int contentId) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                //设置标题
                .setTitle(titleId)
                //设置内容
                .setMessage(contentId)
                //设置确定按钮
                .setPositiveButton(R.string.string_dialog_positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //启动应用详情页
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse(Constant.PACKAGE_URL_SCHEME + getPackageName()));
                        startActivityForResult(intent, Constant.FOR_RESULT_REQUESTCODE);
                    }
                })
                //设置取消按钮
                .setNegativeButton(R.string.string_dialog_negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //显示权限不足的提示，然后启动登录页
                        showLongToast(R.string.string_toast_lack_permission);
                        delayedSkip(Constant.DELAYED_MILLISECOND);
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.FOR_RESULT_REQUESTCODE:
                //重新初始化权限
                initPermission();
                break;
            default:
                break;
        }
    }
}
