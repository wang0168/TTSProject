package tts.moudle.api.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Request;
import tts.moudle.api.socialapi.SocialConstants;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private String data;
    private TokenBean tokenBean;
    private static WXInfoBean infoBean;// 用户的信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, SocialConstants.getInstance().getWxKey(), false);
        api.registerApp(SocialConstants.getInstance().getWxKey());// 必须
        api.handleIntent(getIntent(), this);// 必须
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.getType()) {// 区分是登录 还是分享
            case ConstantsAPI.COMMAND_SENDAUTH:// 是登录回调
                SendAuthLogin((SendAuth.Resp) baseResp);
                break;
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:// 分享回调
                SendMessageToWXShare((SendMessageToWX.Resp) baseResp);
                break;
            default:

                break;
        }

    }

    /**
     * 处理登录成功后的数据
     *
     * @param resp
     */
    private void SendAuthLogin(SendAuth.Resp resp) {
        Intent mIntent;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // resp.fromBundle(arg0);
                String state = resp.state;
                final String token = resp.code;
                OkHttpUtils
                        .get()
                        .url("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                                + SocialConstants.getInstance().getWxKey() + "&secret="
                                + SocialConstants.getInstance().getWxSercent() + "&code=" + token
                                + "&grant_type=authorization_code").tag(this)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                tokenBean = new Gson().fromJson(response, TokenBean.class);
                                getUserInfor();
                            }
                        });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                SocialLoginMoudle.getInstance().loginListener.loginFailed("取消登录");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                SocialLoginMoudle.getInstance().loginListener.loginFailed("拒绝访问");
                finish();
                break;
            default:
                SocialLoginMoudle.getInstance().loginListener.loginFailed("发送返回");
                finish();
                break;
        }
    }

    private void getUserInfor() {
        /**
         * 获得用户信息
         */
        OkHttpUtils
                .get()
                .url("https://api.weixin.qq.com/sns/userinfo?access_token="
                        + tokenBean.getAccess_token() + "&openid=" + tokenBean.getOpenid()).tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        WXInfoBean wxInfoBean = new Gson().fromJson(response, WXInfoBean.class);
                        SocialLoginMoudle.getInstance().loginListener.wxLoginSuccess(wxInfoBean);
                        finish();
                    }
                });
    }

    /**
     * 分享成功后的处理
     *
     * @param resp
     */
    private void SendMessageToWXShare(SendMessageToWX.Resp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (SocialShareMoudle.getInstance().shareListener != null) {
                    SocialShareMoudle.getInstance().shareListener.shareFailed("取消分享");
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (SocialShareMoudle.getInstance().shareListener != null) {
                    SocialShareMoudle.getInstance().shareListener.shareFailed("拒绝访问");
                }
                break;
            default:
                if (SocialShareMoudle.getInstance().shareListener != null) {
                    SocialShareMoudle.getInstance().shareListener.shareFailed("发送返回");
                }
                break;
        }
        finish();
    }

    private void goToGetMsg() {
        /*
         * Intent intent = new Intent(this, GetFromWXActivity.class);
		 * intent.putExtras(getIntent()); startActivity(intent); finish();
		 */
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);

		/*
         * Intent intent = new Intent(this, ShowFromWXActivity.class);
		 * intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
		 * intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
		 * intent.putExtra(Constants.ShowMsgActivity.BAThumbData,
		 * wxMsg.thumbData); startActivity(intent); finish();
		 */
    }
}
