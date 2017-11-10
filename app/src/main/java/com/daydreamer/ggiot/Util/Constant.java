package com.daydreamer.ggiot.Util;

/**
 * Created by StevenGo on 2017/11/8.
 * 常量表
 */

public interface Constant {
    /**
     * 跳转等待的时间
     */
    public static final int DELAYED_MILLISECOND = 2 * 1000;
    /**
     * 权限请求码
     */
    public static final int PERMISSON_REQUESTCODE = 0x001;
    /**
     * startActivityForResult请求码
     */
    public static final int FOR_RESULT_REQUESTCODE = 0x002;
    /**
     * url scheme
     */
    public static final String PACKAGE_URL_SCHEME = "package:";

    /**
     * 应用程序所需的权限
     */
    public static final String[] PERMISSIONS = {
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
    };
    public static final int COUNT_DOWN_MILLISECOND=60*1000;
    public static final int COUNT_DOWN_SECOND_STEP=1000;
    public static final int COUNT_DOWN_SECOND_START=0;

    public static final int COUNT_DOWN_START=0;
    public static final int COUNT_DOWN_FINISH=1;

}
