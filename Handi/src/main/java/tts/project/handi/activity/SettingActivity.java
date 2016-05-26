package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.BaseActivity;
import tts.project.handi.MainActivity;
import tts.project.handi.R;
import tts.project.handi.common.Constant;
import tts.project.handi.utils.DataCleanManager;
import tts.project.handi.utils.MyAccountMoudle;
import tts.project.handi.utils.Utils;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.clean)
    RelativeLayout clean;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.exit)
    Button exit;
    @Bind(R.id.cache_size)
    TextView cacheSize;
    @Bind(R.id.img_right_btn2)
    ImageView imgRightBtn2;
    @Bind(R.id.version)
    RelativeLayout version;
    @Bind(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @Bind(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @Bind(R.id.rl_share)
    RelativeLayout rlShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        setTitle(new BarBean().setMsg("系统设置"));
        tvVersion.setText(Utils.getAppVersionName(this));
        try {
            cacheSize.setText(DataCleanManager.getTotalCacheSize(this) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_share, R.id.rl_feedback, R.id.rl_about_us, R.id.clean, R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_about_us:
                startActivity(new Intent(this, AboutActivity.class).putExtra("url", Constant.ABOUT_US).putExtra("title", "关于我们"));
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.rl_share:
                showShare();
                break;

            case R.id.clean:
                DataCleanManager.clearAllCache(this);
                try {
                    cacheSize.setText(DataCleanManager.getTotalCacheSize(this) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.exit:
                SharedPreferencesMoudle.getInstance().setJson(this
                        .getApplicationContext
                                (), "user_login", "");
                MyAccountMoudle.getInstance().setUserInfo(null);
                AccountMoudle.getInstance().setUserBean(null);
                if (TextUtils.isEmpty(SharedPreferencesMoudle.getInstance().getJson(this
                        .getApplicationContext(), "user_login"))) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("init", "1");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Logger.wtf(SharedPreferencesMoudle.getInstance().getJson(this
                            .getApplicationContext(), "user_login"));

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    private void showShare() {
        ShareSDK.initSDK(this.getApplicationContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("憨弟网络科技（上海）有限公司");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.xn--zfr10sy1jr37a.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("互联网安装服务平台\n链接工匠和商户\n为消费者提供满意的安装维修服务");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        getActivity().getResources().getDrawable(R.mipmap.logo2x);
        oks.setImageUrl("http://www.mob.com/files/apps/icon/1461656046.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://handi.tstmobile.com/aaa.html");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(this.getApplicationContext());
    }
}
