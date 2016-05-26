package tts.moudle.takecashapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import moudle.project.tts.ttsmoudletakecashapi.R;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BankBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.BankMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by sjb on 2016/2/23.
 */
public class BankAddActivity extends TTSBaseActivity {
    private EditText name;
    private EditText bankCode;
    private TextView bankName;
    private EditText mobile;
    private EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_add_activity);
        findAllView();
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg("添加银行卡").setSubTitle("返回"));
        name = (EditText) findViewById(R.id.name);
        bankCode = (EditText) findViewById(R.id.bankCode);
        bankCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.checkBankCard(s.toString())) {
                    bankName.setText(BankMoudle.getInstance().getBankName(BankAddActivity.this, s.toString()));
                } else {
                    bankName.setText("");
                }
            }
        });
        bankName = (TextView) findViewById(R.id.bankName);
        mobile = (EditText) findViewById(R.id.mobile);
        code = (EditText) findViewById(R.id.code);
    }

    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.okBtn) {
            addBank();
        }
    }

    private final int add_bank = 1001;

    private void addBank() {
        if (name.getText().toString().equals("")) {
            CustomUtils.showTipShort(this, "请填写持卡人");
            return;
        }
        if (!TextUtils.checkBankCard(bankCode.getText().toString())) {
            CustomUtils.showTipShort(this, "请填写正确的银行卡号");
            return;
        }
        if (bankName.getText().toString().equals("")) {
            CustomUtils.showTipShort(this, "请填写银行名称");
            return;
        }
        if (!TextUtils.isMobileNO(mobile.getText().toString())) {
            CustomUtils.showTipShort(this, "请填写手机号");
            return;
        }
        if (code.getText().toString().equals("")) {
            CustomUtils.showTipShort(this, "请填写验证码");
            return;
        }

        startRequestData(add_bank);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case add_bank:
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserBean().getUser_token());
                params.put("name", name.getText().toString());
                params.put("bank", bankName.getText().toString());
                params.put("phone", mobile.getText().toString());
                params.put("card", bankCode.getText().toString());
                getDataWithPost(add_bank, Host.hostUrl + "api.php?m=Api&c=bank&a=setbank", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case add_bank:
                BankBean bankBean = new BankBean();
                bankBean.setBank_id(response);
                bankBean.setMember_id(AccountMoudle.getInstance().getUserBean().getMember_id());
                bankBean.setCard(bankCode.getText().toString());
                bankBean.setBank(bankName.getText().toString());
                bankBean.setName(name.getText().toString());
                bankBean.setPhone(mobile.getText().toString());

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bankBean", bankBean);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
