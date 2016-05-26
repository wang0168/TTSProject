package tts.project.handi;

import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;

import cn.sharesdk.framework.ShareSDK;
import tts.moudle.api.TTSBaseApplication;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.project.handi.bean.UserInfo;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * Created by chen on 2016/2/22.
 */
public class BaseApplication extends TTSBaseApplication {

    private static BaseApplication baseApplication = new BaseApplication();

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    public BaseApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHost("http://handi.tstmobile.com/");
        Log.i("", "==========================1111111111111111");
        initUser();
        initMyUser(getApplicationContext());
        SDKInitializer.initialize(this);
        ShareSDK.initSDK(this);

    }

    /**
     * 初始化用户信息
     */
    public void initMyUser(Context context) {
        Log.i("","==========================2222222222222222222");
        String json = SharedPreferencesMoudle.getInstance().getJson(context, "user_login");
        Log.i("","ddd==============="+json);
        UserInfo userInfo = new Gson().fromJson(json, UserInfo.class);
        MyAccountMoudle.getInstance().setUserInfo(userInfo);
    }


}
