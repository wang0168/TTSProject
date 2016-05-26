package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.utils.DataCleanManager;

public class SystemSettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout layoutFixPassword;
    private RelativeLayout layoutPush;
    private TextView textCacheSize;
    private RelativeLayout layoutCleanCache;
    private RelativeLayout layoutAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("系统设置").setSubTitle("返回"));

        findAllView();
        try {
            textCacheSize.setText(DataCleanManager.getTotalCacheSize(this) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findAllView() {
        layoutFixPassword = (RelativeLayout) findViewById(R.id.layout_fix_password);
        layoutPush = (RelativeLayout) findViewById(R.id.layout_push);
        layoutCleanCache = (RelativeLayout) findViewById(R.id.layout_clean_cache);
        layoutAboutUs = (RelativeLayout) findViewById(R.id.layout_about_us);
        textCacheSize = (TextView) findViewById(R.id.text_cache_size);
        layoutFixPassword.setOnClickListener(this);
        layoutPush.setOnClickListener(this);
        layoutCleanCache.setOnClickListener(this);
        layoutAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_fix_password:
                startActivity(new Intent(this, CheckPhoneActivity.class).putExtra("from", 1));
                break;
            case R.id.layout_push:
                startActivity(new Intent(this, PushSettingActivity.class));
                break;
            case R.id.layout_clean_cache:
                DataCleanManager.clearAllCache(this);
                try {
                    textCacheSize.setText(DataCleanManager.getTotalCacheSize(this) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.layout_about_us:
                startActivity(new Intent(this, AboutActivity.class).putExtra("url", "http://wl.tstmobile.com/ShoppingMall/html/others/about_our.html"));
                break;
        }
    }
}
