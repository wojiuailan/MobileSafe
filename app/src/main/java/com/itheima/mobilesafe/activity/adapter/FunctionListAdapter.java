package com.itheima.mobilesafe.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.mobilesafe.R;

/**
 * Created by Administrator on 2018/5/7.
 */

public class FunctionListAdapter extends BaseAdapter {

    private String[] mFunctionName = {"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private int[] mIcon = {R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};

    @Override
    public int getCount() {
        return mIcon.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_function_list_adapter, null);
            viewHolder.mIVIcon = convertView.findViewById(R.id.iv_icon);
            viewHolder.mTVFunctionListName = convertView.findViewById(R.id.tv_function_list_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTVFunctionListName.setText(mFunctionName[position]);
        viewHolder.mIVIcon.setImageResource(mIcon[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView mIVIcon;
        TextView mTVFunctionListName;
    }
}
