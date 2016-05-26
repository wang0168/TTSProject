package tts.moudle.takecashapi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import moudle.project.tts.ttsmoudletakecashapi.R;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BankBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.SystemUtils;

/**
 * Created by sjb on 2016/2/22.
 */
public class TakeCashActivity extends TTSBaseActivity {
    private final int take_cash = 1001;
    private String title;
    BankBean bankBean;

    private TextView bankName;
    private TextView name;
    private TextView endNum;
    private EditText take_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_cash_activity);
        getIntentData();
        findAllView();
        //SystemUtils.hideKeyboard(this);
    }

    private void getIntentData() {
        title = getIntent().getStringExtra("title");
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg(title == null ? "提现" : title).setSubTitle("返回"));
        bankName = (TextView) findViewById(R.id.bankName);
        name = (TextView) findViewById(R.id.name);
        endNum = (TextView) findViewById(R.id.endNum);
        take_price = (EditText) findViewById(R.id.take_price);
    }

    private void fullData() {
        if (bankBean != null) {
            bankName.setText(bankBean.getBank());
            name.setText(bankBean.getName());
            endNum.setText("尾号是" + bankBean.getCard().substring(bankBean.getCard().length() - 4, bankBean.getCard().length()));
        }
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

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case take_cash:
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserBean().getUser_token());
                params.put("bank_id", bankBean.getBank_id());
                params.put("value", take_price.getText().toString());
                getDataWithPost(take_cash, Host.hostUrl + "api.php?m=Api&c=bank&a=getdraw", params);
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
        }
    }
}
