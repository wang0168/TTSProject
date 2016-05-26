package tts.moudle.api.social;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import moudle.project.tts.ttsmoudlesocializationapi.R;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.socialapi.SocialConstants;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/2/15.
 */
public class SocialShareMoudle {
    public ShareListener shareListener;
    private Context context;
    //qq
    private int shareType;
    private int mExtarFlag = 0x00;
    public static Tencent mTencent;

    //微信
    public IWXAPI wxApi;
    private final int THUMB_SIZE = 150;


    //微博
    private static class Moudle {
        protected final static SocialShareMoudle mInstance = new SocialShareMoudle();
    }

    public static SocialShareMoudle getInstance() {
        return Moudle.mInstance;
    }

    public SharePopupwindow popupWindow;


    public void showShareTxt(final Context context, View view, final String title) {
        showShare(context, view, 2, "", title, "", "", null);
    }

    public void showShareImg(final Context context, View view, final Bitmap bitmap) {
        showShare(context, view, 3, "", "", "", "", bitmap);
    }

    public void showShareUrl(final Context context, View view, final String url, final String title, final String summary, final String imgUrl, final Bitmap bitmap) {
        showShare(context, view, 1, url, title, summary, imgUrl, bitmap);
    }

    /**
     * @param context
     * @param view
     * @param type    1。网址链接  2文本 3图片
     * @param url
     * @param title
     * @param summary
     * @param imgUrl
     */
    public void showShare(final Context context, View view, final int type, final String url, final String title, final String summary, final String imgUrl, final Bitmap bitmap) {
        View popupView = View.inflate(context, R.layout.share_popupwindow, null);
        popupWindow = new SharePopupwindow(context, popupView);
        RecyclerViewAutoRefreshUpgraded list = (RecyclerViewAutoRefreshUpgraded) popupView.findViewById(R.id.list);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 3);
        list.setLayoutManager(linearLayoutManager);
        final List<ImgTxtBean> imgTxtBeans = new ArrayList<>();
        imgTxtBeans.add(new ImgTxtBean().setId("1").setName("QQ分享").setIconId(R.drawable.share_qq));
        imgTxtBeans.add(new ImgTxtBean().setId("2").setName("微信好友").setIconId(R.drawable.share_weixin));
        imgTxtBeans.add(new ImgTxtBean().setId("3").setName("微信朋友圈").setIconId(R.drawable.share_pengyou));
        imgTxtBeans.add(new ImgTxtBean().setId("4").setName("微博").setIconId(R.drawable.share_wb));
        ImgTxtAdapter imgTxtAdapter=new ImgTxtAdapter(context, imgTxtBeans);
        list.setAdapter(imgTxtAdapter);
        imgTxtAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {

            }

            @Override
            public void onLongClick(View itemView, int position) {
                switch (imgTxtBeans.get(position).getId()) {
                    case "1":
                        if (type == 1) {
                            shareToQQ(context, QQShare.SHARE_TO_QQ_TYPE_DEFAULT, url, title, summary, imgUrl, "整案科技", url);
                        } else if (type == 2) {
                            shareToQQ(context, QQShare.SHARE_TO_QQ_TYPE_DEFAULT, url, title, summary, imgUrl, "整案科技", url);
                        } else if (type == 3) {
                            shareToQQ(context, QQShare.SHARE_TO_QQ_TYPE_IMAGE, url, title, summary, imgUrl, "整案科技", url);
                        } else {
                            shareToQQ(context, QQShare.SHARE_TO_QQ_TYPE_DEFAULT, url, title, summary, imgUrl, "整案科技", url);
                        }
                        break;
                    case "2":
                        if (type == 1) {
                            shareUrlToWx(context, 0, url, title, summary, bitmap);
                        } else if (type == 2) {
                            shareTxtToWx(context, 0, title);
                        } else if (type == 3) {
                            shareImgToWx(context, 0, bitmap);
                        } else {
                            shareUrlToWx(context, 0, url, title, summary, bitmap);
                        }
                        break;
                    case "3":
                        if (type == 1) {
                            shareUrlToWx(context, 1, url, title, summary, bitmap);
                        } else if (type == 2) {
                            shareTxtToWx(context, 1, title);
                        } else if (type == 3) {
                            shareImgToWx(context, 1, bitmap);
                        } else {
                            shareUrlToWx(context, 1, url, title, summary, bitmap);
                        }
                        break;
                    case "4":
                        if (type == 1) {
                            shareToWB(context, 3 + "", title, summary, bitmap, url);
                        } else if (type == 2) {
                            shareToWB(context, 1 + "", title);
                        } else if (type == 3) {
                            shareToWB(context, 2 + "", bitmap);
                        } else {
                            shareToWB(context, 3 + "", title, summary, bitmap, url);
                        }
                        break;
                }
            }
        });

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        // 这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置PopupWindow的弹出和消失效果
        // popupWindow.setAnimationStyle(R.style.popupAnimation);
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
    }

    public IWeiboShareAPI mWeiboShareAPI;

    public void shareToWB(Context context, String WBShareType, String title) {
        shareToWB(context, WBShareType, title, "", null, "");
    }

    public void shareToWB(Context context, String WBShareType, Bitmap bitmap) {
        shareToWB(context, WBShareType, "", "", bitmap, "");
    }

    /**
     * @param context
     * @param WBShareType 1 文本 2图片 3网址 4音乐 5视频 6声音
     * @param title       标题
     * @param desc        描述
     * @param bitmap      图片
     * @param url         网址
     */
    public void shareToWB(final Context context, String WBShareType, String title, String desc, Bitmap bitmap, String url) {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, SocialConstants.getInstance().getWbKey());
        mWeiboShareAPI.registerApp(); // 将应用注册到微博客户端

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();// 初始化微博的分享消息
        if (WBShareType.equals("1")) {
            weiboMessage.textObject = getTextObj(title);
        } else if (WBShareType.equals("2")) {
            weiboMessage.imageObject = getImageObj(bitmap);
        } else if (WBShareType.equals("3")) {
            weiboMessage.mediaObject = getWebpageObj(title, desc, bitmap, url);
        } else if (WBShareType.equals("4")) {
            weiboMessage.mediaObject = getMusicObj(title, desc, bitmap, url);
        } else if (WBShareType.equals("5")) {
            weiboMessage.mediaObject = getVideoObj(title, desc, bitmap, url);
        } else if (WBShareType.equals("6")) {
            weiboMessage.mediaObject = getVoiceObj(title, desc, bitmap, url);
        }

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        // 发送请求消息到微博，唤起微博分享界面
        AuthInfo authInfo = new AuthInfo(context, SocialConstants.getInstance().getWbKey(), SocialConstants.getInstance().getWbREDIRECT_URL(), SocialConstants.getInstance().getWbSCOPE());
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        mWeiboShareAPI.sendRequest((Activity) context, request, authInfo, token, new WeiboAuthListener() {
            @Override
            public void onWeiboException(WeiboException arg0) {
                SocialShareMoudle.getInstance().shareListener.shareFailed(arg0.getMessage());
            }

            @Override
            public void onComplete(Bundle bundle) {
                // TODO Auto-generated method stub
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(context, newToken);
                SocialShareMoudle.getInstance().shareListener.wbShareSuccess("分享");
                // Toast.makeText(getApplicationContext(), "onAuthorizeComplete
                // token = " + newToken.getToken(), 0).show();
            }

            @Override
            public void onCancel() {
                SocialShareMoudle.getInstance().shareListener.shareFailed("取消分享");
            }
        });
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String title) {
        TextObject textObject = new TextObject();
        textObject.text = title;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     * <p/>
     * 图片不能大于32kb
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        // 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(String title, String desc, Bitmap bitmap, String url) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = desc;

        // 设置 Bitmap 类型的图片到视频对象里 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj(String title, String desc, Bitmap bitmap, String url) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = title;
        musicObject.description = desc;
        // 设置 Bitmap 类型的图片到视频对象里 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(bitmap);
        musicObject.actionUrl = url;
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = "Music 默认文案";
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj(String title, String desc, Bitmap bitmap, String url) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = title;
        videoObject.description = desc;

        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
            System.out.println("kkkkkkk    size  " + os.toByteArray().length);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("Weibo.BaseMediaObject", "put thumb failed");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = url;
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 默认文案";
        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj(String title, String desc, Bitmap bitmap, String url) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = title;
        voiceObject.description = desc;
        voiceObject.setThumbImage(bitmap);
        voiceObject.actionUrl = url;
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = "Voice 默认文案";
        return voiceObject;
    }

    /**
     * 分享网址到url
     *
     * @param flag        0分享到好友 1分享到朋友圈
     * @param url         网址
     * @param title       标题
     * @param description 内容描述
     * @param thumb       图片展示
     */
    public void shareUrlToWx(Context context, int flag, String url, String title, String description, Bitmap thumb) {
        wxApi = WXAPIFactory.createWXAPI(context, SocialConstants.getInstance().getWxKey());

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = thumb == null ? null : Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }

    /**
     * 分享图片到微信
     *
     * @param flag 0分享到好友 1分享到朋友圈
     * @param bmp
     */
    public void shareImgToWx(Context context, int flag, Bitmap bmp) {
        wxApi = WXAPIFactory.createWXAPI(context, SocialConstants.getInstance().getWxKey());

        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true); // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }

    /**
     * 分享文本到微信
     *
     * @param flag 0分享到好友 1分享到朋友圈
     */
    public void shareTxtToWx(Context context, int flag, String text) {
        wxApi = WXAPIFactory.createWXAPI(context, SocialConstants.getInstance().getWxKey());

        if (text == null || text.length() == 0) {
            return;
        }

        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        wxApi.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 分享到QQ
     *
     * @param context   上下文
     * @param shareType 分享类型
     * @param url       网址
     * @param title     标题
     * @param summary   描述
     * @param imgUrl    图片地址
     * @param appName   app名字
     * @param audioUrl  音频地址
     */
    public void shareToQQ(Context context, int shareType, String url, String title, String summary, String imgUrl, String appName, String audioUrl) {
        this.context = context;
        this.shareType = shareType;
        mTencent = Tencent.createInstance(SocialConstants.getInstance().getQqKey(), context);
        startShareToQQ(title, url, summary, imgUrl, appName, audioUrl);
    }

    private void startShareToQQ(String title, String url, String summary, String imgUrl, String appName, String audioUrl) {
        final Bundle params = new Bundle();
        if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        }

        if (shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrl);
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        }
        params.putString(
                shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE ? QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL
                        : QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);
        if (shareType == QQShare.SHARE_TO_QQ_TYPE_AUDIO) {
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, audioUrl);
        }
        doShareToQQ(params);
    }

    private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ((Activity) context, params,
                            qqShareListener);
                }
            }
        });
    }

    public IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            if (shareListener != null) {
                shareListener.shareFailed("取消分享");
            }
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Log.i("", "===============" + response);
            //成功返回    response={"ret":0}
            if (shareListener != null) {
                shareListener.qqShareSuccess("分享成功");
            }
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            if (shareListener != null) {
                shareListener.shareFailed(e.errorMessage);
            }
        }
    };

   /* public IWeiboHandler.Response wbListener = new IWeiboHandler.Response() {
        @Override
        public void onResponse(BaseResponse baseResp) {
            Log.i("", "=====================vssdvsvdsvcds");
            switch (baseResp.errCode) {
                case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_OK:
                    Log.i("", "=====================sddsdsvd");
                    shareListener.wbShareSuccess("分享成功");
                    break;
                case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_CANCEL:
                    Log.i("", "=====================svdsvdsv");
                    shareListener.shareFailed("取消分享");
                    break;
                case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_FAIL:
                    Log.i("", "=====================sdvdsvds");
                    shareListener.shareFailed("分享失败");
                    break;
            }
        }
    };
*/

    public void setOnShareListener(ShareListener listener) {
        this.shareListener = listener;
    }

    public interface ShareListener {
        /**
         * qq分享成功
         *
         * @param message
         */
        public void qqShareSuccess(String message);

        /**
         * 微信分享成功
         *
         * @param message
         */
        public void wxShareSuccess(String message);

        /**
         * 微博分享成功
         *
         * @param message
         */
        public void wbShareSuccess(String message);

        /**
         * 第三方分享失败
         *
         * @param error
         */
        public void shareFailed(String error);
    }
}
