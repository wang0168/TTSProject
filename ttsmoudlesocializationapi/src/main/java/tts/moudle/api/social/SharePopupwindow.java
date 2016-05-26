package tts.moudle.api.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Request;
import tts.moudle.api.bean.UpdateBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.SystemUtils;
import tts.moudle.api.widget.ProgressBarUpdate;

/**
 * Created by sjb on 2016/3/26.
 */
public class SharePopupwindow extends PopupWindow {
    private Context context;
    View view = null;

    public SharePopupwindow(Context context, View view) {
        super(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        this.view = view;
        this.context = context;
        onCreate();
    }

    private void onCreate() {
        findAllView();
    }

    private void findAllView() {

    }
}

