package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class XMLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_xmlactivity);
        TextView tv_xml = (TextView) findViewById(R.id.tv_xml);
        tv_xml.setText(extras.getString("xml"));
    }
}