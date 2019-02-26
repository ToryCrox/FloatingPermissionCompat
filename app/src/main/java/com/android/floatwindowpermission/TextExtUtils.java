package com.android.floatwindowpermission;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

/**
 * @author tory
 * @date 2018/12/21
 * @des:
 */
public class TextExtUtils {
    private static final String TAG = "TextExtUtils";

    public static final char HOT_TAG = '#';
    public static final char HOT_TAG_ESCAPE = '&';

    public interface SpanFactory{
        Object createSpan(CharSequence sequence);
    }

    public static SpannableStringBuilder parseHotText(@NonNull String hotText, @ColorInt int hotTextColor){
        return parseSpannableText(hotText, sequence -> new ForegroundColorSpan(hotTextColor));
    }

    /**
     * 对以'#'渲染重点词
     * 要单独显示'#'，以'&'来转义
     * @param hotText
     * @param spanFactory
     * @return
     */
    public static SpannableStringBuilder parseSpannableText(@NonNull String hotText,@NonNull SpanFactory spanFactory) {
        final SpannableStringBuilder ssb = new SpannableStringBuilder();
        StringBuilder sb = null;
        char[] chars = hotText.toCharArray();
        char lastC = 0;
        for (char c : chars) {
            if (c != HOT_TAG || lastC == HOT_TAG_ESCAPE) {
                if (sb != null) {
                    sb.append(c);
                } else {
                    ssb.append(c);
                }
            } else {
                if (sb == null) {
                    sb = new StringBuilder();
                } else {
                    int start = ssb.length();
                    ssb.append(sb);
                    ssb.setSpan(spanFactory.createSpan(sb.toString()), start, ssb.length(),
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    sb = null;
                }
            }
            lastC = c;
        }
        if (sb != null) {
            Log.d(TAG, "parseHotText: lost sb = " + sb);
            ssb.append(sb);
        }
        return ssb;
    }
}