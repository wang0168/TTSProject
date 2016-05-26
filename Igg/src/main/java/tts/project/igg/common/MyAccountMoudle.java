package tts.project.igg.common;


import tts.project.igg.bean.UserInfoBean;

/**
 * Created by sjb on 2016/1/21.
 */
public class MyAccountMoudle {
    private UserInfoBean userInfo;


    private static class Moudle {
        protected final static MyAccountMoudle mInstance = new MyAccountMoudle();
    }

    public static MyAccountMoudle getInstance() {
        return Moudle.mInstance;
    }

    public UserInfoBean getUserInfo() {
        if (userInfo == null) {
            userInfo = new UserInfoBean();
        }
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }


}
