package com.itheima.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.config.KeyValues;
import com.itheima.mobilesafe.utils.StreamUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    private final int OPEN_HOME_ACTIVITY = 100;
    private final int DOWNLOAD_UPDATE_UI = 101;
    private final String NEW_APK_INFO = "apk_info";

    private long mStartTime = 0;
    private String tag = "SplashActivity";
    private int mVersionCode = 0;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPEN_HOME_ACTIVITY:
                    openHomeActivity();
                    break;
                case DOWNLOAD_UPDATE_UI:
                    JSONObject newAPKInfo;
                    try {
                        newAPKInfo = new JSONObject(msg.getData().getString(NEW_APK_INFO));
                        Log.e(tag, newAPKInfo.toString());
                        showDialogUpdate(newAPKInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    /**
     * 安装APK文件
     *
     * @param file 安装APK文件的File对象
     */
    private void installAPK(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        delayOpenHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUI();
        checkUpdate();
    }

    /**
     * 更新UI显示
     */
    @SuppressLint("SetTextI18n")
    private void initUI() {
        PackageManager pm = getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(getPackageName(), 0);
            mVersionCode = pInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView versionName = findViewById(R.id.tv_version_name);
        RelativeLayout rlRoot = findViewById(R.id.rl_root);
        if (pInfo != null) {
            versionName.setText("版本名称:" + pInfo.versionName);
        }

        //设置动画
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(2000);
        rlRoot.setAnimation(animation);
    }

    /**
     * 检查版本更新
     */
    private void checkUpdate() {
        mStartTime = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject apkInfo = getServiceAPKInfo();
                try {
                    if ((apkInfo != null ? apkInfo.getInt("version_code") : 0) > mVersionCode) {
                        //下载更新
                        //downloadUpdate(apkInfo);
                        Message message = new Message();
                        message.what = DOWNLOAD_UPDATE_UI;
                        Bundle bundle = new Bundle();
                        bundle.putString(NEW_APK_INFO, apkInfo.toString());
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    } else {
                        //进入home界面
                        delayOpenHome();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 弹出提示更新，确认 下载更新 取消 进入HomeActivity
     *
     * @param apkInfo 新版APK的信息
     * @throws JSONException
     */
    private void showDialogUpdate(final JSONObject apkInfo) throws JSONException {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        builder.setTitle("更新提示").setMessage(apkInfo.getString(KeyValues.DESCRIPTION)).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消下载
                Log.e(tag, "取消下载");
                delayOpenHome();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载更新
                try {
                    Log.e(tag, "更新下载执行");
                    downloadUpdate(apkInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).show();
    }

    /**
     * 延时慢4秒打开HomeActivity
     */
    private void delayOpenHome() {
        long timeNeed = (mStartTime + 4000) - System.currentTimeMillis();
        if (timeNeed > 0) {
            mHandler.sendEmptyMessageDelayed(OPEN_HOME_ACTIVITY, timeNeed);
        } else {
            mHandler.sendEmptyMessage(OPEN_HOME_ACTIVITY);
        }
    }

    /**
     * 打开跳转到HomeActivity
     */
    private void openHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 下载并更新
     *
     * @param apkInfo 应用的更新信息的JSON对象
     * @throws JSONException
     */
    private void downloadUpdate(JSONObject apkInfo) throws JSONException {
        HttpUtils httpUtils = new HttpUtils();
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mobilesafe.apk";
        }
        Log.e(tag, "下载更新存放：" + path);
        httpUtils.download(apkInfo.getString(KeyValues.DOWNLOAD_URL), path, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //下载成功
                Log.e(tag, "下载成功");
                File apkFile = responseInfo.result;
                installAPK(apkFile);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //下载失败
                delayOpenHome();
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
            }

            @Override
            public void onCancelled() {
                super.onCancelled();
            }
        });
    }

    /**
     * 获取服务器上的APK信息
     *
     * @return 返回获取的JSON对象，返回空获取失败
     */
    private JSONObject getServiceAPKInfo() {
        InputStream in = null;
        JSONObject apkInfo = null;

        try {
            URL url = new URL(getString(R.string.service_apk_info));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int code = connection.getResponseCode();
            if (200 == code) {
                in = connection.getInputStream();
                String apkInfoStr = StreamUtil.readStringFromIn(in);

                Log.e(tag, apkInfoStr);

                apkInfo = new JSONObject(apkInfoStr);
            }
            return apkInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
