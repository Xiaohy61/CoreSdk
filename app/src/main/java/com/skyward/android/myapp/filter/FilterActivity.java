package com.skyward.android.myapp.filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.skyward.android.myapp.R;
import com.skyward.android.sdk.utils.EditTextUtil;

/**
 * @author skyward
 */
public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        EditText edittext = findViewById(R.id.edittext);
        ImageView clear = findViewById(R.id.clear);
        EditTextUtil.filterCharacter(edittext,20);
        EditTextUtil.clearEditText(edittext,clear);
    }
}
