package tts.project.handi;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.TabManager;
import tts.moudle.loginapi.activity.LoginActivity;
import tts.project.handi.activity.fragment.HomeFragment;
import tts.project.handi.activity.fragment.OrdersFragment;
import tts.project.handi.activity.fragment.PersonalCenterFragment;
import tts.project.handi.utils.MyAccountMoudle;

public class MainActivity extends BaseActivity {
    //    HomeFragment homeFragment;
//    PersonalCenterFragment personalCenterFragment;
//    OrdersFragment ordersFragments;
//    FrameLayout mainContent;
    @Bind(R.id.rb_home_bottombar_home)
    RadioButton rbHomeBottombarHome;
    @Bind(R.id.rb_home_bottombar_order)
    RadioButton rbHomeBottombarOrder;
    @Bind(R.id.rb_home_bottombar_my)
    RadioButton rbHomeBottombarMy;
    @Bind(R.id.home_bottom_bar)
    RadioGroup homeBottomBar;
    boolean isLogin;


    public static TabHost mTabHost;
    private TabManager mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isLogin = AccountMoudle.getInstance().getUserBean().isLogin();
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("tab0").setIndicator("tab0"), HomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("tab1"), OrdersFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab2").setIndicator("tab2"), PersonalCenterFragment.class, null);

    }


    @OnClick({R.id.rb_home_bottombar_home, R.id.rb_home_bottombar_order, R.id.rb_home_bottombar_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home_bottombar_home:
                mTabHost.setCurrentTabByTag("tab0");
                break;
            case R.id.rb_home_bottombar_order:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    mTabHost.setCurrentTabByTag("tab1");
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("IsCallback", true);
                    intent.putExtra("param", "2");
                    startActivityForResult(intent, 3001);
                    mTabHost.setCurrentTabByTag("tab0");
                    rbHomeBottombarHome.setChecked(true);
                }

                break;
            case R.id.rb_home_bottombar_my:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    mTabHost.setCurrentTabByTag("tab2");
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("IsCallback", true);
                    intent.putExtra("param", "2");
                    startActivityForResult(intent, 3001);
                    mTabHost.setCurrentTabByTag("tab0");
                    rbHomeBottombarHome.setChecked(true);
                }

        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ("1".equals(intent.getStringExtra("IsOut"))) {
//            finish();
        }
        if ("1".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab0");
            homeBottomBar.check(R.id.rb_home_bottombar_home);
        }
        if ("2".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab1");
            homeBottomBar.check(R.id.rb_home_bottombar_home);
        }
        if ("3".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab2");
            homeBottomBar.check(R.id.rb_home_bottombar_home);
            rbHomeBottombarMy.setChecked(true);
        }
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
