package tts.project.igg.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import tts.moudle.api.bean.UserBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.project.igg.bean.UserInfoBean;
import tts.project.igg.common.MyAccountMoudle;

/**
 * Created by lenove on 2016/5/9.
 */
public class LoginInfoSave {
    public static boolean loginOk(Context context, String response) {
        Logger.d(response);
        UserInfoBean userInfoBean = new Gson().fromJson(response, UserInfoBean.class);
        MyAccountMoudle.getInstance().setUserInfo(userInfoBean);
        response = response.substring(0, response.length() - 1) + ",\"login\": true}";
        UserBean userBean = new Gson().fromJson(response, UserBean.class);
        if (SharedPreferencesMoudle.getInstance().setJson(context.getApplicationContext(), "user_login", response)) {
            AccountMoudle.getInstance().setUserBean(userBean);
//            CustomUtils.showTipShort(context, "登录成功");
            return true;
        } else {
            return false;
        }
    }
}
