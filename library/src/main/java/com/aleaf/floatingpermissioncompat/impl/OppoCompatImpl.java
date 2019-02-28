package com.aleaf.floatingpermissioncompat.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author tory
 * @date 2018/12/6
 * @des:
 */
public class OppoCompatImpl extends BelowApi23CompatImpl {
    private static final String TAG = "OppoCompatImpl";

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public boolean apply(Context context) {
        //merge request from https://github.com/zhaozepeng/FloatWindowPermission/pull/26
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //com.coloros.safecenter/.sysfloatwindow.FloatWindowListActivity
            ComponentName comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            context.startActivity(intent);
            return true;
        } catch(Exception e){
            Log.e(TAG, "apply error", e);
            return false;
        }
    }
}
