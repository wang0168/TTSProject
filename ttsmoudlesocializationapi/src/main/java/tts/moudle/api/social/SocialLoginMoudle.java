package tts.moudle.api.social;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import tts.moudle.api.socialapi.SocialConstants;

/**
 * Created by sjb on 2016/2/15.
 */
public class SocialLoginMoudle {
    //QQ
    public Tencent mTencent;
    private Context context;
    public UserInfo mInfo;
    public LoginListener loginListener;

    //微信
    public IWXAPI wxApi;

    //微博
    public SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private UsersAPI mUsersAPI;

    private static class Moudle {
        protected final static SocialLoginMoudle mInstance = new SocialLoginMoudle();
    }

    public static SocialLoginMoudle getInstance() {
        return Moudle.mInstance;
    }


    /**
     * 登录调用接口
     *
     * @param context  上下文
     * @param type     登录类型 qq：QQ登录  wx：微信登录  wb：微博登录
     */
    public void login(Context context, String type) {
        this.context = context;
        if (type.equals("qq")) {
            mTencent = Tencent.createInstance(SocialConstants.getInstance().getQqKey(), context);
            mTencent.logout(context);
            mTencent.login((Activity) context, "all", baseUiListener);
        } else if (type.equals("wx")) {
            wxApi = WXAPIFactory.createWXAPI(context, SocialConstants.getInstance().getWxKey());
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            wxApi.sendReq(req);
        } else if (type.equals("wb")) {
            AuthInfo mAuthInfo = new AuthInfo(context, SocialConstants.getInstance().getWbKey(), SocialConstants.getInstance().getWbREDIRECT_URL(), SocialConstants.getInstance().getWbSCOPE());
            mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
            mSsoHandler.authorize(new AuthListener());
        }
    }

    /**
     * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #} 中调用
     * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
     * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
     * SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            // 从这里获取用户输入的 电话号码信息
            String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                mUsersAPI = new UsersAPI(context, SocialConstants.getInstance().getWbKey(), mAccessToken);
                long uid = Long.parseLong(mAccessToken.getUid());
                mUsersAPI.show(uid, mListener);
                // 显示 Token
                // updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(context, mAccessToken);
                // CustomUtils.showTipShort(WbApi.this, "授权成功");
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                Log.i("", "================" + code);
                loginListener.loginFailed("授权失败");
            }
        }

        @Override
        public void onCancel() {
            loginListener.loginFailed("放弃授权");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            loginListener.loginFailed(e.getMessage());
        }
    }

    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            try {
                if (!TextUtils.isEmpty(response)) {
                    // 调用 User#parse 将JSON串解析成User对象
                    WBUserInfo wbUserInfo = new Gson().fromJson(response, WBUserInfo.class);
                    loginListener.wbLoginSuccess(wbUserInfo);
                }
            } catch (Exception e) {
                loginListener.loginFailed(e.getMessage());
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            loginListener.loginFailed(e.getMessage());
        }
    };
    public IUiListener baseUiListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            getUserInfoInThread();
        }
    };

    public void getUserInfoInThread() {
        IUiListener listener = new IUiListener() {
            @Override
            public void onError(UiError e) {
                loginListener.loginFailed("授权失败");
            }

            @Override
            public void onComplete(final Object response) {
                // 用户信息 response
                if (loginListener != null) {
                    QQUserInfo qqUserInfo = new Gson().fromJson(response.toString(), QQUserInfo.class);
                    loginListener.qqLoginSuccess(qqUserInfo);
                }
            }

            @Override
            public void onCancel() {
                loginListener.loginFailed("取消授权");
            }
        };
        mInfo = new UserInfo(context, mTencent.getQQToken());
        mInfo.getUserInfo(listener);
    }

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {

            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {

            }
            doComplete(jsonResponse);
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError e) {

        }

        @Override
        public void onCancel() {

        }
    }

    public void setOnLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public interface LoginListener {
        /**
         * QQ登录成功回调
         *
         * @param qqUserInfo
         */
        public void qqLoginSuccess(QQUserInfo qqUserInfo);

        /**
         * 微信登录成功回调
         *
         * @param wxUserInfo
         */
        public void wxLoginSuccess(WXInfoBean wxUserInfo);

        /**
         * 微博登录成功回调
         *
         * @param wxUserInfo
         */
        public void wbLoginSuccess(WBUserInfo wxUserInfo);

        /**
         * 第三发登录失败
         *
         * @param error
         */
        public void loginFailed(String error);
    }
}
