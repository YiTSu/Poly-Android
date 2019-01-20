package com.yitsu.poly.view.splashUI.settingsUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
/**
 * Created by butterfly on 2018/12/23.
 * 设置用户活动覆盖范围的Activity
 */
public class AcsRangeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editSetRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acs_range);

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
        editSetRange = (EditText)findViewById(R.id.edit_set_range);
    }

    /*
    修改活动覆盖范围
     */
    public void setAcsRange(View view){
        if (editSetRange.getText().toString().equals("")){
            Poly.AcsRange = 50;
        }else {
            Poly.AcsRange = Integer.parseInt(editSetRange.getText().toString());
        }
        Toast.makeText(AcsRangeActivity.this,"范围修改成功：" + Poly.AcsRange,Toast.LENGTH_LONG).show();
        finish();
    }
}
