package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;


import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BankBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.takecashapi.activity.BankListActivity;
import tts.project.handi.R;

/**
 * Created by sjb on 2016/2/22.
 */
public class TakeCashActivity extends TTSBaseActivity {
    private final int take_cash = 1001;
    private final int get_code = 1002;
    private String title;
    BankBean bankBean;

    private TextView bankName;
    private TextView name;
    private TextView endNum;
    private TextView price;
    private EditText take_price;
    private EditText check_code;
    private Button getCode;
    private double monkey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_cash_activity);
        startRequestData(101);
        getIntentData();
        findAllView();
        //SystemUtils.hideKeyboard(this);
    }

    private void getIntentData() {
        monkey = getIntent().getDoubleExtra("monkey", 3000.00);
        title = getIntent().getStringExtra("title");
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg(title == null ? "提现" : title).setSubTitle("返回"));

        bankName = (TextView) findViewById(R.id.bankName);
        name = (TextView) findViewById(R.id.name);
        endNum = (TextView) findViewById(R.id.endNum);
        take_price = (EditText) findViewById(R.id.take_price);
        price = (TextView) findViewById(R.id.price);
        check_code = (EditText) findViewById(R.id.check_code);
        getCode = (Button) findViewById(R.id.btn_get_code);
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode.setClickable(false);
                timer.start();
                startRequestData(get_code);
            }
        });
    }

    private void fullData() {
        if (bankBean != null) {
            bankName.setText(bankBean.getBank());
            name.setText(bankBean.getName());
            endNum.setText("尾号是" + bankBean.getCard().substring(bankBean.getCard().length() - 4, bankBean.getCard().length()));
        }
        Logger.d(monkey + "");
        price.setText((monkey > 3000.00 ? 3000.00 : monkey) + "");
    }

    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.RlALlBank) {
            Intent intent = new Intent(this, BankListActivity.class);
            intent.putExtra("isClick", true);//不传此参数 则列表不可点击
            startActivityForResult(intent, 1000);
        } else if (i == R.id.okBtn) {
            TakeCash();
        }
    }

    private void TakeCash() {
        if (bankBean == null) {
            CustomUtils.showTipLong(this, "请先选择银行卡");
            return;
        } else if (Float.valueOf(take_price.getText().toString()) <= 0) {
            CustomUtils.showTipLong(this, "请先填写转出金额");
            return;
        } else if (Float.valueOf(take_price.getText().toString()) > monkey) {
            CustomUtils.showTipLong(this, "转出金额不能大于可转出金额");
            return;
        } else if (TextUtils.isEmpty(check_code.getText().toString())) {
            CustomUtils.showTipLong(this, "请填写验证码");
            return;
        }
        startRequestData(take_cash);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        bankBean = (BankBean) data.getSerializableExtra("bankBean");
                        fullData();
                    }
                }
                break;
        }
    }

    private int count = 60;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        public void onTick(long millisUntilFinished) {
            count--;
            getCode.setText(count + "秒后重新发送");
        }

        public void onFinish() {
            count = 60;
            getCode.setClickable(true);
            getCode.setText("获取验证码");
        }
    };

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case take_cash:
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserBean().getUser_token());
                params.put("bank_id", bankBean.getBank_id());
                params.put("value", take_price.getText().toString());
                params.put("verify", check_code.getText().toString());
                getDataWithPost(take_cash, Host.hostUrl + "api.php?m=Api&c=bank&a=getdraw", params);
                break;
            case 101:
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserBean().getUser_token());
                getDataWithPost(101, Host.hostUrl + "api.php?m=Api&c=bank&a=gdefbank", params);
                break;
            case get_code:
                params = new HashMap<>();
                params.put("mobile", AccountMoudle.getInstance().getUserBean().getMobile());
                params.put("type", "3");
                getDataWithPost(get_code, Host.hostUrl + "/api.php?m=Api&c=public&a=sendSMS", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case take_cash:
                CustomUtils.showTipShort(this, "申请成功");
                setResult(RESULT_OK);
                finish();
                break;
            case 101:
                bankBean = JSON.parseObject(response, BankBean.class);
                fullData();
                break;
            case get_code:
                CustomUtils.showTipShort(this, "已发送到手机");
                break;
        }
    }
}
