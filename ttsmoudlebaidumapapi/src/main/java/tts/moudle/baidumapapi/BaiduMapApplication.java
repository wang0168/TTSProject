package tts.moudle.baidumapapi;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by sjb on 2016/3/4.
 */
public class BaiduMapApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
