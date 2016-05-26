package tts.project.igg.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.igg.R;
import tts.project.igg.utils.LoginInfoSave;

/**
 * Created by sjb on 2016/3/1.
 */
public class RegisterActivity extends TTSBaseActivity implements View.OnClickListener {
    private final int ver_ok = 1001;
    private final int regeister_ok = 1002;
    private final int login_ok = 1003;
    private EditText ETMobile, ETVer, ETPassword, confirmPassword;

    private TextView VerBtn;
    private TextView tv_user_agreement;
    private Button regeisterBtn;

    private CheckBox chkUserAgreement;
    private int count = 60;

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        public void onTick(long millisUntilFinished) {
            count--;
            VerBtn.setText(count + "秒后重新发送");
        }

        public void onFinish() {
            count = 60;
            VerBtn.setClickable(true);
            VerBtn.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeister);
        findAllView();
    }

    private void findAllView() {

        ETMobile = (EditText) findViewById(R.id.ETMobile);
        ETVer = (EditText) findViewById(R.id.ETVer);
        ETPassword = (EditText) findViewById(R.id.ETPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        VerBtn = (TextView) findViewById(R.id.VerBtn);
        tv_user_agreement = (TextView) findViewById(R.id.tv_user_agreement);
        regeisterBtn = (Button) findViewById(R.id.action);

        chkUserAgreement = (CheckBox) findViewById(R.id.chk_user_agreement);
        tv_user_agreement.setOnClickListener(this);
        chkUserAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    regeisterBtn.setBackgroundColor(getResources().getColor(R.color.gray_normal));
                    regeisterBtn.setClickable(false);
                } else {
                    regeisterBtn.setClickable(true);
                    regeisterBtn.setBackgroundColor(getResources().getColor(R.color.RGB230_235_241));
                }
            }
        });
//        tvUserAgreement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                CustomUtils.showTipShort(RegisterActivity.this, "前往用户协议页面");
//                Intent intent = new Intent(RegisterActivity.this, AboutActivity.class);
//                intent.putExtra("url", Host.hostUrl + "/wap/yhxy.html");
//                intent.putExtra("title", "用户协议");
//                startActivity(intent);
//            }
//        });
    }

    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.action) {
            if (!TextUtils.isMobileNO(ETMobile.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入正确的是手机号");
                return;
            }
            if (ETVer.getText().toString().equals("") || ETVer.getText().toString().length() < 4) {
                CustomUtils.showTipShort(this, "请输入验证码");
                return;
            }

            if (ETPassword.getText().toString().equals("")) {
                CustomUtils.showTipShort(this, "请输入密码");
                return;
            }
            if (!chkUserAgreement.isChecked()) {
                CustomUtils.showTipShort(this, "请勾选用户协议");
                return;
            }
            if (!ETPassword.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$\n")) {
                CustomUtils.showTipShort(this, "密码不满足要求");
                return;
            }
//            if (!ETPassword.getText().toString().equals(ETAgainPassword.getText().toString())) {
//                CustomUtils.showTipShort(this, "2次密码不匹配");
//                return;
//            }
            startRequestData(regeister_ok);

        } else if (i == R.id.VerBtn) {
            if (!TextUtils.isMobileNO(ETMobile.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入正确的是手机号");
                return;
            }
            VerBtn.setClickable(false);
            timer.start();
            startRequestData(ver_ok);
        }
    }


    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case ver_ok:
                params = new HashMap<>();
                params.put("mobile", ETMobile.getText().toString());
                params.put("code_type", "1");
                getDataWithPost(ver_ok, Host.hostUrl + "code.api?sendCode", params);
                break;
            case regeister_ok:
                params = new HashMap<>();
                params.put("mobile", ETMobile.getText().toString());
                params.put("code", ETVer.getText().toString());
                params.put("password", ETPassword.getText().toString());

                getDataWithPost(regeister_ok, Host.hostUrl + "memberInterface.api?memberRegister", params);
                break;
            case login_ok:
                params = new ArrayMap<>();
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
            case ver_ok:
                CustomUtils.showTipShort(this, "已发送到手机");
                break;
            case regeister_ok:
//                Intent intent = new Intent();
//                intent.putExtra("mobile", ETMobile.getText().toString());
//                intent.putExtra("password", ETPassword.getText().toString());
//                intent.putExtra("response", response);
                startRequestData(login_ok);

                break;
            case login_ok:
                LoginInfoSave.loginOk(this, response);
//                EventBus.getDefault().post(new LoginInfoBean().setMobile(ETMobile.getText().toString())
//                        .setPassword(ETPassword.getText().toString()).setResponse(response));
                startActivity(new Intent(this, InputInviteActivity.class));
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_account_type:
//                List<ConfigBean> temp = new ArrayList<>();
//                temp.add(new ConfigBean(0, null, "车主", null));
//                temp.add(new ConfigBean(0, null, "货主", null));
//                selectPopupWindow = new SelectPopupWindow(this, temp, 2, "用户类型");
//                selectPopupWindow.showAtLocation(btn_account_type, Gravity.CENTER, 0, 0);
//                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
//                    @Override
//                    public void doClick(String province, String city, String area, String other, int pos) {
//                        edit_user_type.setText(other);
////                        edit_user_type.setSelection(other.length());
//                    }
//                });
//                break;

            case R.id.tv_user_agreement:
                startActivity(new Intent(this, AboutActivity.class).putExtra("title", "用户协议").putExtra("url", "http://wl.tstmobile.com/ShoppingMall/html/others/protocols.html"));
                break;
        }
    }
}
