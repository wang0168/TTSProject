package tts.project.handi.utils;

import tts.project.handi.bean.UserInfo;

/**
 * Created by sjb on 2016/1/21.
 */
public class MyAccountMoudle {
    private UserInfo userInfo;

    private static class Moudle {
        protected final static MyAccountMoudle mInstance = new MyAccountMoudle();
    }

    public static MyAccountMoudle getInstance() {
        return Moudle.mInstance;
    }

    public UserInfo getUserInfo() {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
