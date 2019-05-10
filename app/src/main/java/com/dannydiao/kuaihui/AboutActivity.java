package com.dannydiao.kuaihui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

public class AboutActivity extends AppCompatActivity {


    private static final String MARKDOWN =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView textView = findViewById(R.id.markdown);
        RichText.fromMarkdown()
    }
}
