package com.itheima.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.config.KeyValues;
import com.itheima.mobilesafe.utils.SPUtil;
import com.itheima.mobilesafe.view.SettingItemCheckView;

public class SettingActivity extends AppCompatActivity {
    private SettingItemCheckView mSettingItemUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI();
        initDate();
        setOnClick();
    }

    private void initDate() {
        boolean isCheck = SPUtil.getBoolean(this, KeyValues.AUTO_UPDATE, false);
        mSettingItemUpdate.setCheck(isCheck);
    }

    /**
     * 为所有Item设置点击事件
     */
    private void setOnClick() {
        mSettingItemUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = mSettingItemUpdate.isCheck();
                SPUtil.putBoolean(SettingActivity.this, KeyValues.AUTO_UPDATE, !isCheck);
                mSettingItemUpdate.setCheck(!isCheck);
            }
        });
    }

    private void initUI() {
        mSettingItemUpdate = findViewById(R.id.setting_item_update);
    }
}
