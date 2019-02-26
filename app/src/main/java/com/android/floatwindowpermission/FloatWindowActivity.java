/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.android.floatwindowpermission;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aleaf.floatingpermissioncompat.FloatingPermissionCompat;
import com.aleaf.floatingpermissioncompat.RomUtils;

import java.util.Arrays;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-10-17
 */

public class FloatWindowActivity extends Activity {

    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        findViewById(R.id.btn_show_or_apply).setOnClickListener(v -> {
            // 检查是否已经授权
            if (FloatingPermissionCompat.get().check(context)) {
                FloatWindowManager.get().show(context);
            } else {
                // 授权提示
                new AlertDialog.Builder(context).setTitle("悬浮窗权限未开启")
                        .setMessage("你的手机没有授权" + context.getString(R.string.app_name) + "获得悬浮窗权限，视频悬浮窗功能将无法正常使用")
                        .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 显示授权界面
                                FloatingPermissionCompat.get().apply(context);
                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });

        findViewById(R.id.btn_dismiss).setOnClickListener(v -> FloatWindowManager.get().dismiss());

        String s = "vivo手机需要到i管家->软件管理->悬浮窗管理/权限管理，打开应用悬浮窗权限\n" +
                "点击#这里跳转#i管家";
        if (RomUtils.isVivo()){
            SpannableStringBuilder ss = TextExtUtils.parseSpannableText(s, sp -> new ClickSpan());
            ((TextView)findViewById(R.id.tv_turn_ops)).setText(ss);
            ((TextView)findViewById(R.id.tv_turn_ops)).setMovementMethod(LinkMovementMethod.getInstance());
            Log.e("ClickSpan", "onCreate: " + Arrays.toString(ss.getSpans(0, ss.length(), IconMarginSpan.class)));
        }
    }


    private class ClickSpan extends ClickableSpan {

        @Override
        public void onClick(@NonNull View widget) {
            Log.d("ClickSpan", "onClick: ....");
            FloatingPermissionCompat.get().apply(widget.getContext());
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(true);
            ds.clearShadowLayer();
        }

        @Override
        public String toString() {
            return "ClickSpan1111";
        }
    }
}
