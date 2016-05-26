package chexin.project.tts.chexin.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.ConfigBean;
import chexin.project.tts.chexin.widget.selectPopupWindow.SelectPopupWindow;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by sjb on 2016/3/1.
 */
public class RegisterActivity extends TTSBaseActivity implements View.OnClickListener {
    private final int ver_ok = 1001;
    private final int regeister_ok = 1002;
    private EditText ETMobile, ETVer, ETPassword, edit_user_type;

    private Button VerBtn;
    private Button regeisterBtn;
    private Button btn_account_type;
    private TextView txtUserAgreement;//用户协议
    private CheckBox chkUserAgreement;
    private int count = 60;
    private String param = "";
    private SelectPopupWindow selectPopupWindow;
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
        param = getIntent().getStringExtra("param");
        findAllView();
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg("注册"));
        ETMobile = (EditText) findViewById(R.id.ETMobile);
        ETVer = (EditText) findViewById(R.id.ETVer);
        ETPassword = (EditText) findViewById(R.id.ETPassword);
        edit_user_type = (EditText) findViewById(R.id.edit_user_type);

        VerBtn = (Button) findViewById(R.id.VerBtn);
        regeisterBtn = (Button) findViewById(R.id.regeisterBtn);
        btn_account_type = (Button) findViewById(R.id.btn_account_type);
        btn_account_type.setOnClickListener(this);
        txtUserAgreement = (TextView) findViewById(R.id.txt_user_agreement);
        txtUserAgreement.setOnClickListener(this);
        chkUserAgreement = (CheckBox) findViewById(R.id.chk_user_agreement);
        chkUserAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    regeisterBtn.setBackgroundColor(getResources().getColor(R.color.gray_normal));
                    regeisterBtn.setClickable(false);
                } else {
                    regeisterBtn.setClickable(true);
                    regeisterBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        if (i == R.id.regeisterBtn) {
            if (!TextUtils.isMobileNO(ETMobile.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入正确的是手机号");
                return;
            }
            if (ETVer.getText().toString().equals("") || ETVer.getText().toString().length() < 4) {
                CustomUtils.showTipShort(this, "请输入验证码");
                return;
            }
            if (TextUtils.isEmpty(edit_user_type.getText().toString())) {
                CustomUtils.showTipShort(this, "请选择用户类型");
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
                params.put("mobileno", ETMobile.getText().toString());
                getDataWithPost(ver_ok, Host.hostUrl + "userfaces?booleanUser", params);
                break;
            case regeister_ok:
                params = new HashMap<>();
                params.put("mobileno", ETMobile.getText().toString());
                params.put("code", ETVer.getText().toString());
                if ("车主".equals(edit_user_type.getText().toString())) {
                    params.put("usertype", "1");
                } else if ("货主".equals(edit_user_type.getText().toString())) {
                    params.put("usertype", "2");
                }

                params.put("password", ETPassword.getText().toString());
//                if (!TextUtils.isEmpty(param)) {
//                    params.put("state", param);
//                }
                getDataWithPost(regeister_ok, Host.hostUrl + "userfaces?register", params);
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
                intent.putExtra("response", response);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_account_type:
                List<ConfigBean> temp = new ArrayList<>();
                temp.add(new ConfigBean(0, null, "车主", null));
                temp.add(new ConfigBean(0, null, "货主", null));
                selectPopupWindow = new SelectPopupWindow(this, temp, 2, "用户类型");
                selectPopupWindow.showAtLocation(btn_account_type, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other,int pos) {
                        edit_user_type.setText(other);
//                        edit_user_type.setSelection(other.length());
                    }
                });
                break;

            case R.id.txt_user_agreement:
                if (chkUserAgreement.isChecked())
                    chkUserAgreement.setChecked(false);
                else
                    chkUserAgreement.setChecked(true);
                break;
        }
    }
}
