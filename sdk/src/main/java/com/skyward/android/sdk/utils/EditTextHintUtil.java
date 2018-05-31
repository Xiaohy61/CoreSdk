package com.skyward.android.sdk.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

/**
 *
 * @author skyward
 * @date 2017/8/12 0012
 * email:
 * 调整输入框提示字体的大小
 */

public class EditTextHintUtil {

    public static void setHint(EditText editText, int size, String text){
        // 新建一个属性对象,设置文字的大小
        SpannableString ss = new SpannableString(text);
        // 附加属性到文本
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // 设置hint
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
}
