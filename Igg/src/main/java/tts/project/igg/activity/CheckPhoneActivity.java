package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.common.MyAccountMoudle;

public class CheckPhoneActivity extends BaseActivity implements View.OnClickListener {
    private final int ver_ok = 1001;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.code)
    EditText code;
    @Bind(R.id.VerBtn)
    TextView VerBtn;
    @Bind(R.id.action)
    Button action;
    private String codeStr;
    private String title;
    private int from;//0忘记密码 1，修改密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("验证手机号码").setSubTitle("返回"));
        VerBtn.setOnClickListener(this);
        action.setOnClickListener(this);
        from = getIntent().getIntExtra("from", 0);
        if (from == 1) {
            if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getMobile())) {
                mobile.setText(MyAccountMoudle.getInstance().getUserInfo().getMobile());
                mobile.setEnabled(false);
                mobile.setSelection(MyAccountMoudle.getInstance().getUserInfo().getMobile().length());
            } else {
                mobile.setEnabled(true);
            }
            title = "修改密码";
        } else {
            title = "设置新密码";
        }
    }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VerBtn:
                if (!TextUtils.isMobileNO(mobile.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入正确的是手机号");
                    return;
                }
                VerBtn.setClickable(false);
                timer.start();
                startRequestData(ver_ok);
                break;
            case R.id.action:
                if (TextUtils.isEmpty(code.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(codeStr)) {
                    CustomUtils.showTipShort(this, "请获取验证码");
                    return;
                } else {
                    if (code.getText().toString().equals(codeStr)) {
                        startActivityForResult(new Intent(this, SettingPasswordActivity.class)
                                .putExtra("code", codeStr).putExtra("title", title).putExtra("mobile", mobile.getText().toString()), 1000);
                    } else {
                        CustomUtils.showTipShort(this, "请输入正确的验证码");
                        return;
                    }
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case ver_ok:
                params = new ArrayMap<>();
                params.put("mobile", mobile.getText().toString());
                params.put("code_type", "2");
                getDataWithPost(ver_ok, Host.hostUrl + "code.api?sendCode", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case ver_ok:
                CustomUtils.showTipShort(this, "已发送到手机");
                codeStr = response;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
