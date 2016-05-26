package tts.project.igg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TabHost;

import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.TTSRadioButton;
import tts.moudle.api.widget.TabManager;
import tts.project.igg.activity.login.LoginActivity;
import tts.project.igg.common.MyAccountMoudle;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static TabHost mTabHost;
    private TabManager mTabManager;
    private TTSRadioButton homeBtn, newsBtn, cartBtn, meBtn;
    private RadioGroup RGRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("tab0").setIndicator("tab0"), HomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("tab1"), NewsFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab2").setIndicator("tab2"), CartFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab3").setIndicator("tab3"), MeFragment.class, null);
        findAllView();
    }

    private void findAllView() {
        homeBtn = (TTSRadioButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(this);
        newsBtn = (TTSRadioButton) findViewById(R.id.newsBtn);
        newsBtn.setOnClickListener(this);
        cartBtn = (TTSRadioButton) findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(this);
        meBtn = (TTSRadioButton) findViewById(R.id.meBtn);
        meBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeBtn:
                mTabHost.setCurrentTabByTag("tab0");
                break;
            case R.id.newsBtn:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    mTabHost.setCurrentTabByTag("tab1");
                } else {
                    mTabHost.setCurrentTabByTag("tab0");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.cartBtn:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    mTabHost.setCurrentTabByTag("tab2");
                } else {
                    mTabHost.setCurrentTabByTag("tab0");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.meBtn:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    mTabHost.setCurrentTabByTag("tab3");
                } else {
                    mTabHost.setCurrentTabByTag("tab0");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }
}
