package com.itheima.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.activity.adapter.FunctionListAdapter;

public class HomeActivity extends AppCompatActivity {
    private GridView mGVFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        initDate();
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        FunctionListAdapter functionListAdapter = new FunctionListAdapter();
        mGVFunction.setAdapter(functionListAdapter);
    }

    /**
     * 初始化UI显示
     */
    private void initUI() {
        mGVFunction = findViewById(R.id.gv_function);
    }
}
