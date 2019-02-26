package com.aleaf.floatingpermissioncompat.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.aleaf.floatingpermissioncompat.FloatingPermissionCompat;

/**
 * @author tory
 * @date 2019/2/26
 * @des:
 */
public class VivoCompatImpl implements FloatingPermissionCompat.CompatImpl{

    private FloatingPermissionCompat.CompatImpl mCommonCompat;

    public VivoCompatImpl(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mCommonCompat = new BelowApi23CompatImpl() {
                @Override
                public boolean isSupported() {
                    return VivoCompatImpl.this.isSupported();
                }

                @Override
                public boolean apply(Context context) {
                    return VivoCompatImpl.this.apply(context);
                }
            };
        } else {
            mCommonCompat = new Api23CompatImpl();
        }
    }

    @Override
    public boolean check(Context context) {
        return mCommonCompat.check(context);
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public boolean apply(Context context) {
        boolean success = false;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            success = mCommonCompat.apply(context);
        }
        if (!success){
            try {
                Intent intent = new Intent("com.iqoo.secure");
                intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
                // com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity
                // com.iqoo.secure.ui.phoneoptimize.FloatWindowManager
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                success = true;
            } catch (Exception e){
                success = false;
            }
        }
        return success;
    }
}
