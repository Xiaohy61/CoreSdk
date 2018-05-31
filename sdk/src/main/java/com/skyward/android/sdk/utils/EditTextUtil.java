package com.skyward.android.sdk.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * EditTextUtil工具类
 *
 * @author skyward
 * @date 2016/6/28
 */
public class EditTextUtil {
    /**
     * @param editText  要监听的输入框
     * @param imageView 显示清除输入内容的图标
     */
    public static void clearEditText(@NonNull final EditText editText,@NonNull final ImageView imageView) {


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = String.valueOf(editable);
                if (str.length() > 0) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }


    /**
     * 过滤填写特殊字符 只允许输入中文英文数字
     * @param editText editText
     * @param maxLength 允许最大输入长度
     */
    public static void filterCharacter(@NonNull final EditText editText,@NonNull final int maxLength) {
        //设置允许输入的字符长度


        editText.addTextChangedListener(new TextWatcher() {
            private int cou = 0;
            int selectionEnd = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                cou = before + count;
                String editable = editText.getText().toString();
                //过滤特殊字符
                String str = stringFilter(editable);
                if (!editable.equals(str)) {
                    editText.setText(str);
                }
                editText.setSelection(editText.length());
                cou = editText.length();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cou > maxLength) {
                    selectionEnd = editText.getSelectionEnd();
                    s.delete(maxLength, selectionEnd);
                }
            }
        });
    }


    /**
     * 只允许输入中文英文数字的正则
     *
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5.@]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 统计文本输入框的字符个数
     * @param editText editText
     */
    public static int calculateCounts(EditText editText) {
        final int[] count = {0};
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                count[0] = s.length();

            }
        });
        return count[0];
    }


}
