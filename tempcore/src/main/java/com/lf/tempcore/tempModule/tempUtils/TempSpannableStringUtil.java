package com.lf.tempcore.tempModule.tempUtils;

import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by Administrator on 2015/11/14.
 */
public class TempSpannableStringUtil {

    public static SpannableStringBuilder setStringColor(String str,
                                                        String rules,
                                                        final int size,
                                                        final int color) {
        final int start = str.indexOf(rules);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(str);
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                if (size != 0) {
                    ds.setTextSize(size);
                }
                if (color != 0) {
                    ds.setColor(color);
                }
                ds.setUnderlineText(false);
            }

        }, start, start + rules.length(), 0);
        return ssb;
    }
}
