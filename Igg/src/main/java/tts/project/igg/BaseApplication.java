package tts.project.igg;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import tts.moudle.api.TTSBaseApplication;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.project.igg.bean.UserInfoBean;
import tts.project.igg.common.MyAccountMoudle;

/**
 * Created by lenove on 2016/4/29.
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
        initHost("http://wl.tstmobile.com/ShoppingMall/");
        initUser();
//        initQQ("wxbf654da821c08af5");
//        initWb("3112442065");
//        initWx("wxbf654da821c08af5", "");
        initMyUser(getApplicationContext());
//        JPushInterface.init(this);

    }

    /**
     * 初始化用户信息
     */
    public void initMyUser(Context context) {
        Log.i("", "==========================2222222222222222222");
        String json = SharedPreferencesMoudle.getInstance().getJson(context, "user_login");
        Log.i("", "ddd===============" + json);
        UserInfoBean userInfo = new Gson().fromJson(json, UserInfoBean.class);
        MyAccountMoudle.getInstance().setUserInfo(userInfo);
    }

}
