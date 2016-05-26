package tts.project.igg.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.activity.CheckPhoneActivity;
import tts.project.igg.utils.LoginInfoSave;

/**
 * Created by sjb on 2016/2/29.
 */
public class LoginActivity extends BaseActivity {
    private final int login_ok = 1001;
    private EditText ETMobile, ETPassword;
    private boolean IsCallback;
    private String mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IsCallback = getIntent().getBooleanExtra("IsCallback", false);
        mobile = getIntent().getStringExtra("mobile");
        password = getIntent().getStringExtra("password");
        findAllView();
    }


    private void findAllView() {
//        setTitle(new BarBean().setMsg("登录").setIsRemoveBack(true));
        ETMobile = (EditText) findViewById(R.id.username);
        ETPassword = (EditText) findViewById(R.id.password);
        ETMobile.setText(mobile == null ? "" : mobile);
        ETPassword.setText(password == null ? "" : password);
    }


    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.registered) {
            startActivityForResult(new Intent(this, RegisterActivity.class), 1000);
        } else if (i == R.id.action_login) {
//            if (!TextUtils.isMobileNO(ETMobile.getText().toString())) {
//                CustomUtils.showTipShort(this, "请输入正确的是手机号");
//                return;
//            }
            if (ETPassword.getText().toString().equals("")) {
                CustomUtils.showTipShort(this, "请输入密码");
                return;
            }
            startRequestData(login_ok);
        } else if (i == R.id.forget) {
            startActivityForResult(new Intent(this, CheckPhoneActivity.class).putExtra("from", 0), 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
//                    ETMobile.setText(data.getStringExtra("mobile"));
//                    ETPassword.setText(data.getStringExtra("password"));
//                    Intent intent = new Intent();
//                    intent.putExtra("mobile", data.getStringExtra("mobile"));
//                    intent.putExtra("password", data.getStringExtra("password"));
//                    intent.putExtra("response", data.getStringExtra("response"));
//                    setResult(RESULT_OK, intent);
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
                params.put("mobile", ETMobile.getText().toString());
                params.put("password", ETPassword.getText().toString());
                getDataWithPost(login_ok, Host.hostUrl + "memberInterface.api?memberLogin", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case login_ok:
                boolean b = LoginInfoSave.loginOk(this, response);
                if (b) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    CustomUtils.showTipShort(this, "登录异常");
                }
                break;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private long exitTime = 0;
//
//    public void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
//            AppManager.getAppManager().AppExit(this);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
