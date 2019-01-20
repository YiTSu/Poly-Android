package com.yitsu.poly;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoteShowActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        editText = (EditText) findViewById(R.id.edit_note);
        button = (Button)findViewById(R.id.button_note);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                TextView textView = (TextView) layout.findViewById(R.id.nameee);
                textView.setText("李达康");
                TextView textView1 = (TextView) layout.findViewById(R.id.text);
                textView1.setText(str);
                layout.setVisibility(View.VISIBLE);
            }
        });
    }

}

