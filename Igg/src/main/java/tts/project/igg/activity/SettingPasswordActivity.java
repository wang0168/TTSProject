package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.activity.login.InputInviteActivity;
import tts.project.igg.utils.LoginInfoSave;

public class SettingPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.confirmPassword)
    EditText confirmPassword;
    @Bind(R.id.action)
    Button action;
    private String title;
    private BarBean barBean;
    private String codeStr;
    private String mobileStr;
    private final int login_ok = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        barBean = new BarBean().setMsg(title);
        barBean.setSubTitle("返回");
        codeStr = getIntent().getStringExtra("code");
        mobileStr = getIntent().getStringExtra("mobile");
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                if (TextUtils.isEmpty(password.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
                    CustomUtils.showTipShort(this, "请再次输入新密码");
                    return;
                }
                if (!password.getText().toString().matches("\\^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$\\n")) {
                    CustomUtils.showTipShort(this, "密码不满足要求");
                    return;
                }
                if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    CustomUtils.showTipShort(this, "两次密码输入不一致，请重新输入后再次尝试");
                    return;
                } else {
                    startRequestData(submitData);
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                params.put("mobile", mobileStr);
                params.put("password", password.getText().toString());
                params.put("code", codeStr);
                getDataWithPost(submitData, Host.hostUrl + "memberInterface.api?forgetPassword", params);
                break;
            case login_ok:
                params = new ArrayMap<>();
                params.put("mobile", mobileStr);
                params.put("password", password.getText().toString());
                getDataWithPost(login_ok, Host.hostUrl + "memberInterface.api?memberLogin", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case submitData:
//                CustomUtils.showTipShort(this, response);
                startRequestData(login_ok);
//                setResult(RESULT_OK);
//                finish();
                break;
            case login_ok:
                LoginInfoSave.loginOk(this, response);
//                EventBus.getDefault().post(new LoginInfoBean().setMobile(ETMobile.getText().toString())
//                        .setPassword(ETPassword.getText().toString()).setResponse(response));
                startActivity(new Intent(this, InputInviteActivity.class));
                setResult(RESULT_OK);
                CustomUtils.showTipShort(this, response);
                finish();
                break;
        }
    }

    public static void main(String[] args) {
//        TextUtils.isMobileNO()
        System.out.println("123456".matches("\\^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$\\n"));

    }
}
