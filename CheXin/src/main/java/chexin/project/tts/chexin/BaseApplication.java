package chexin.project.tts.chexin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import chexin.project.tts.chexin.bean.UserInfoBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import cn.jpush.android.api.JPushInterface;
import tts.moudle.api.TTSBaseApplication;
import tts.moudle.api.moudle.SharedPreferencesMoudle;

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
        initHost("http://wl.tstmobile.com/ssm/");
        initUser();
        initQQ("wxbf654da821c08af5");
        initWb("3112442065");
        initWx("wxbf654da821c08af5", "");
        initMyUser(getApplicationContext());
        JPushInterface.init(this);

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
