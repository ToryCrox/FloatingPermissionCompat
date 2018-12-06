package com.aleaf.floatingpermissioncompat.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * @author tory
 * @date 2018/12/6
 * @des:
 */
public class OppoCompatImpl extends BelowApi23CompatImpl {


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
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
