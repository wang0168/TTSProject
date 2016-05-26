package chexin.project.tts.chexin.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;


import chexin.project.tts.chexin.R;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by sjb on 2016/3/1.
 */
public class ForgetPasswordActivity extends TTSBaseActivity {
    private final int ver_ok = 1001;
    private final int regeister_ok = 1002;
    private EditText ETMobile, ETVer, ETPassword;

    private Button VerBtn;
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
        setContentView(R.layout.regeister_activity);
        findAllView();
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg("找回密码"));
        ETMobile = (EditText) findViewById(R.id.ETMobile);
        ETVer = (EditText) findViewById(R.id.ETVer);
        ETPassword = (EditText) findViewById(R.id.ETPassword);

        VerBtn = (Button) findViewById(R.id.VerBtn);
    }

    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.regeisterBtn) {
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
                params = new HashMap<String, String>();
                params.put("mobileno", ETMobile.getText().toString());
                getDataWithPost(ver_ok, Host.hostUrl + "userfaces?getUpdateCode", params);
                break;
            case regeister_ok:
                params = new HashMap<String, String>();
                params.put("mobileno", ETMobile.getText().toString());
                params.put("code", ETVer.getText().toString());
                params.put("password", ETPassword.getText().toString());
                getDataWithPost(regeister_ok, Host.hostUrl + "userfaces?updatePwd", params);
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
                Intent intent = new Intent();
                intent.putExtra("mobile", ETMobile.getText().toString());
                intent.putExtra("password", ETPassword.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
