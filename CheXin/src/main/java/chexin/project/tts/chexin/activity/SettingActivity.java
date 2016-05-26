package chexin.project.tts.chexin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.MainActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.utils.DataCleanManager;
import chexin.project.tts.chexin.adapter.PublicStyleAdapter;
import chexin.project.tts.chexin.bean.PublicMenuBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class SettingActivity extends BaseActivity implements TTSBaseAdapterRecyclerView.OnItemClickListener {
    private RecyclerViewAutoRefreshUpgraded settingList;
    private PublicStyleAdapter publicStyleAdapter;
    private List<PublicMenuBean> publicMenuBeans;
    private boolean ischeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        setTitle(new BarBean().setMsg("系统设置"));
        findAllView();
        adapterSetting();
    }

    private void findAllView() {
        settingList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.settingList);
    }

    private void adapterSetting() {
        publicMenuBeans = new ArrayList<>();
        publicMenuBeans.add(new PublicMenuBean().setId("1").setName("使用帮助").setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("2").setName("系统消息").setIconRightId(R.drawable.push_button));
        try {
            publicMenuBeans.add(new PublicMenuBean().setId("3").setName("清空缓存").setIsMargin(true).setIsLine(true).setOther(DataCleanManager.getTotalCacheSize(this) + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        publicMenuBeans.add(new PublicMenuBean().setId("4").setName("声音设置"));
        publicMenuBeans.add(new PublicMenuBean().setId("5").setName("联系我们").setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("6").setName("关于我们"));
        publicMenuBeans.add(new PublicMenuBean().setId("7").setName("协议条款").setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("8").setName("分享车新"));
        publicMenuBeans.add(new PublicMenuBean().setId("9").setName("意见反馈").setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("10").setName("检查更新"));
        publicMenuBeans.add(new PublicMenuBean().setId("11").setName("退出登录").setIsMargin(true).setIsLine(true));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        settingList.setLayoutManager(layoutManager);
        publicStyleAdapter = new PublicStyleAdapter(this, publicMenuBeans);
        settingList.setAdapter(publicStyleAdapter);
        publicStyleAdapter.setOnItemClickListener(this);
        publicStyleAdapter.setCheckButtonClickListener(new PublicStyleAdapter.OnCheckButtonClickListener() {
            @Override
            public void onChecked(View v, boolean isChecked, int position) {
                ischeck = isChecked;
            }
        });
    }

    @Override
    public void onClick(View itemView, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, HelperActivity.class));
                break;
            case 1:
                if (ischeck) {
                    startActivity(new Intent(this, SystemInfoActivity.class));
                }
                break;
            case 2:
                try {
                    DataCleanManager.clearAllCache(this);
                    publicMenuBeans.get(position).setOther(DataCleanManager.getTotalCacheSize(this));
                    settingList.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                startActivity(new Intent(this, SoundSettingActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ContactUsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, AboutActivity.class).putExtra("title", "关于我们").putExtra("url", Host.hostUrl + "AboutOur.html"));
                break;
            case 6:
                startActivity(new Intent(this, AboutActivity.class).putExtra("title", "协议条款").putExtra("url", Host.hostUrl + "Protocol.html"));
                break;
            case 7:
                break;
            case 8:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case 9:
                break;
            case 10:
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
    public void onLongClick(View itemView, int position) {

    }
}
