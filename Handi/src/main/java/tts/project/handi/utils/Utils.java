package tts.project.handi.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;


/**
 * Created by chen on 2016/3/17 0017.
 */
public class Utils {
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
//        int versioncode;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    // 因为本类不是activity所以通过继承接口的方法获取到点击的事件
    public interface OnClickYesListener {
        abstract void onClickYes();
    }

    /**
     * 提问框的 Listener
     */
    public interface OnClickNoListener {
        abstract void onClickNo();
    }

    public static void showDialog(Context context, String message, String yes, String no, final OnClickYesListener listenerYes,
                                  final OnClickNoListener listenerNo, boolean cancelable) {
        new AlertDialog.Builder(context).setMessage(message).setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listenerYes != null) {
                    listenerYes.onClickYes();
                }
            }
        }).setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 如果取消被点击
                if (listenerNo != null) {
                    listenerNo.onClickNo();
                }
            }
        }).setCancelable(cancelable).show();

    }
}
