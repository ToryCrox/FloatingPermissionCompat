package com.aleaf.floatingpermissioncompat.impl;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aleaf.floatingpermissioncompat.RomUtils;

/**
 * 华为悬浮窗权限兼容实现
 * <p>
 * Created by linchaolong on 2016/12/26.
 */
public class HuaweiCompatImpl extends BelowApi23CompatImpl {

    private static final String TAG = "HuaweiCompatImpl";

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public boolean apply(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            if (RomUtils.getEmuiVersion() == 3.1) {
                //emui 3.1 的适配
                context.startActivity(intent);
            } else {
                //emui 3.0 的适配
                comp = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");//悬浮窗管理页面
                intent.setComponent(comp);
                context.startActivity(intent);
            }
            return true;
        } catch (SecurityException e) {
            return apply1(context);
        } catch (ActivityNotFoundException e) {
            return apply2(context);
        } catch (Exception e) {
            Log.e(TAG, "apply", e);
            return false;
        }
    }

    private boolean apply1(Context context){
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");
            //华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
            intent.setComponent(comp);
            context.startActivity(intent);
            return true;
        } catch (Exception e){
            Log.e(TAG, "apply1", e);
            return false;
        }
    }

    private boolean apply2(Context context){
        try {
            /**
             * 手机管家版本较低 HUAWEI SC-UL10
             */
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.Android.settings",
                    "com.android.settings.permission.TabItem");//权限管理页面 android4.4
            intent.setComponent(comp);
            context.startActivity(intent);
            return true;
        } catch (Exception e){
            Log.e(TAG, "apply2", e);
            return false;
        }

    }

}
