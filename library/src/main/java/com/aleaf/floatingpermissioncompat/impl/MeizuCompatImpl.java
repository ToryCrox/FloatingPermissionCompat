package com.aleaf.floatingpermissioncompat.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 魅族悬浮窗权限兼容实现
 * <p>
 * Created by linchaolong on 2016/12/26.
 */
public class MeizuCompatImpl extends BelowApi23CompatImpl {
    private static final String TAG = "MeizuCompatImpl";

    @Override
    public boolean isSupported() {
        return true;
    }

    /**
     * 去魅族权限申请页面
     */
    @Override
    public boolean apply(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
            intent.putExtra("packageName", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e){
            Log.e(TAG, "apply error", e);
            return false;
        }
    }
}
