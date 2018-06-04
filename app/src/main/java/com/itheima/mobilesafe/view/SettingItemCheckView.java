package com.itheima.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.config.KeyValues;

/**
 * Created by Administrator on 2018/5/7.
 */

public class SettingItemCheckView extends RelativeLayout {
    private String mNameSpace = "http://schemas.android.com/apk/res/com.itheima.mobilesafe";

    private String mDesTitle, mDesStatusOn, mDesStatusOff;

    private TextView mTVDescription, mTVStatus;
    private CheckBox mCBCheck;

    public SettingItemCheckView(Context context) {
        this(context, null);
    }

    public SettingItemCheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI(context);
        initDate(attrs);
    }

    /**
     * 初始化UI显示
     *
     * @param context   上下文
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_setting, this);
        mTVDescription = findViewById(R.id.tv_description);
        mTVStatus = findViewById(R.id.tv_status);
        mCBCheck = findViewById(R.id.cb_check);
    }

    /**
     * 获取必须的String数据
     *
     * @param attrs 用于获取数据的attr集合
     */
    private void initDate(AttributeSet attrs) {
        mDesTitle = attrs.getAttributeValue(mNameSpace, KeyValues.DESTITLE);
        mDesStatusOn = attrs.getAttributeValue(mNameSpace, KeyValues.DESSTATUSON);
        mDesStatusOff = attrs.getAttributeValue(mNameSpace, KeyValues.DESSTATUSOFF);

        mTVDescription.setText(mDesTitle);
    }

    /**
     * 设置显示当前状态
     *
     * @param isCheck
     */
    public void setCheck(boolean isCheck) {
        if (isCheck) {
            mTVStatus.setText(mDesStatusOn);
        } else {
            mTVStatus.setText(mDesStatusOff);
        }
        mCBCheck.setChecked(isCheck);
    }

    /**
     * 返回选择状态
     *
     * @return
     */
    public boolean isCheck() {
        return mCBCheck.isChecked();
    }
}
