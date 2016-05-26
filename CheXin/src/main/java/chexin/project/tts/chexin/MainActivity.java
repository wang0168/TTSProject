package chexin.project.tts.chexin;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import chexin.project.tts.chexin.activity.login.LoginActivity;
import chexin.project.tts.chexin.bean.MainBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import de.greenrobot.event.EventBus;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.TTSRadioButton;
import tts.moudle.api.widget.TabManager;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final int init_ok = 1001;
    public static TabHost mTabHost;
    private TabManager mTabManager;
    private TTSRadioButton homeBtn, goodsBtn, carBtn, meBtn;
    private RadioGroup RGRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("tab0").setIndicator("tab0"), HomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("tab1"), GoodsFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab2").setIndicator("tab2"), CarFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab3").setIndicator("tab3"), MeGoodsFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab4").setIndicator("tab4"), MeCarFragment.class, null);
        findAllView();
    }

    public void onEventMainThread(MainBean mainBean) {
        if ("1".equals(mainBean.getType())) {
            mTabHost.setCurrentTabByTag("tab2");
            carBtn.setChecked(true);
        } else if ("2".equals(mainBean.getType())) {
            mTabHost.setCurrentTabByTag("tab1");
            goodsBtn.setChecked(true);
        }
    }

    private void findAllView() {
        homeBtn = (TTSRadioButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(this);
        goodsBtn = (TTSRadioButton) findViewById(R.id.goodsBtn);
        goodsBtn.setOnClickListener(this);
        carBtn = (TTSRadioButton) findViewById(R.id.carBtn);
        carBtn.setOnClickListener(this);
        meBtn = (TTSRadioButton) findViewById(R.id.meBtn);
        meBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeBtn:
                mTabHost.setCurrentTabByTag("tab0");
                break;
            case R.id.goodsBtn:
                mTabHost.setCurrentTabByTag("tab1");
                break;
            case R.id.carBtn:
                mTabHost.setCurrentTabByTag("tab2");
                break;
            case R.id.meBtn:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        mTabHost.setCurrentTabByTag("tab4");
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        mTabHost.setCurrentTabByTag("tab3");
                    } else {
                        CustomUtils.showTipShort(this, "账户信息有误，请联系客服");
                    }

                } else {
                    homeBtn.setChecked(true);
                    mTabHost.setCurrentTabByTag("tab0");
                    startActivityForResult(new Intent(this, LoginActivity.class), 1001);
                }

                break;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ("1".equals(intent.getStringExtra("state"))) {
            finish();
        }
        if ("1".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab0");
            homeBtn.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit(this);
        }
    }
}

