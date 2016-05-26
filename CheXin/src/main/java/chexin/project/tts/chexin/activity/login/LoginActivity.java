package chexin.project.tts.chexin.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import chexin.project.tts.chexin.AppManager;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.UserInfoBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.UserBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.CustomUtils;

/**
 * Created by sjb on 2016/2/29.
 */
public class LoginActivity extends BaseActivity {
    private final int login_ok = 1001;
    private EditText ETMobile, ETPassword;
    private boolean IsCallback;
    private String param1 = "";


    private String mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        IsCallback = getIntent().getBooleanExtra("IsCallback", false);
        param1 = getIntent().getStringExtra("param");
        mobile = getIntent().getStringExtra("mobile");
        password = getIntent().getStringExtra("password");
        findAllView();
    }


    private void findAllView() {
        setTitle(new BarBean().setMsg("登录").setIsRemoveBack(true));
        ETMobile = (EditText) findViewById(R.id.ETMobile);
        ETPassword = (EditText) findViewById(R.id.ETPassword);
        ETMobile.setText(mobile == null ? "" : mobile);
        ETPassword.setText(password == null ? "" : password);
    }


    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.regeisterBtn) {
            startActivityForResult(new Intent(this, RegisterActivity.class).putExtra("param", param1), 1000);
        } else if (i == R.id.loginBtn) {
//            if (!TextUtils.isMobileNO(ETMobile.getText().toString())) {
//                CustomUtils.showTipShort(this, "请输入正确的是手机号");
//                return;
//            }
            if (ETPassword.getText().toString().equals("")) {
                CustomUtils.showTipShort(this, "请输入密码");
                return;
            }
            startRequestData(login_ok);
        } else if (i == R.id.forgetBtn) {
            startActivityForResult(new Intent(this, ForgetPasswordActivity.class), 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    ETMobile.setText(data.getStringExtra("mobile"));
                    ETPassword.setText(data.getStringExtra("password"));
                    Intent intent = new Intent();
                    intent.putExtra("mobile", data.getStringExtra("mobile"));
                    intent.putExtra("password", data.getStringExtra("password"));
                    intent.putExtra("response", data.getStringExtra("response"));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case 2000:
                if (resultCode == RESULT_OK) {
                    ETMobile.setText(data.getStringExtra("mobile"));
                    ETPassword.setText(data.getStringExtra("password"));
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case login_ok:
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobileno", ETMobile.getText().toString());
                params.put("password", ETPassword.getText().toString());
                getDataWithPost(login_ok, Host.hostUrl + "/userfaces?login", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case login_ok:
                loginOk(response);
                break;
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
    private void loginOk(String response) {
        Logger.d(response);
        UserInfoBean userInfoBean = new Gson().fromJson(response, UserInfoBean.class);
        MyAccountMoudle.getInstance().setUserInfo(userInfoBean);
        response = response.substring(0, response.length() - 1) + ",\"login\": true}";
        UserBean userBean = new Gson().fromJson(response, UserBean.class);
        if (SharedPreferencesMoudle.getInstance().setJson(getApplicationContext(), "user_login", response)) {
            AccountMoudle.getInstance().setUserBean(userBean);
            CustomUtils.showTipShort(this, "登录成功");
            JPushInterface.setAlias(this, userInfoBean.getPushalias(), new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
//                    CustomUtils.showTipShort(LoginActivity.this,"推送回调状态码："+i);
                    if (i != 0) {
                        CustomUtils.showTipShort(LoginActivity.this, "推送服务异常");
                    }
                    //i 0为成功
                }
            });
            setResult(RESULT_OK);
            //EventBus.getDefault().post(new LoginEvent().setIsLogin(true));
            finish();
        } else {
            CustomUtils.showTipShort(this, "登录异常");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
