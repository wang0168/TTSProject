package tts.moudle.addressapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import moudle.project.tts.tts_moudle_address_api.R;
import tts.moudle.addressapi.bean.AddressBean;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by shanghang on 2016/1/19.
 */
public class AddressNewActivity extends TTSBaseActivity {

    private EditText ETName, ETMobile, ETProvince, ETAddress;
    private final int add_ok = 1001;

    private AddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_adress);
        addressBean = (AddressBean) getIntent().getSerializableExtra("addressBean");
        setTitle("新建地址");
        findAllView();
        if (addressBean != null) {
            fullData();
        }
    }

    private void fullData() {
        ETName.setText(addressBean.getName());
        ETMobile.setText(addressBean.getMobile());
        ETProvince.setText(addressBean.getCounty());
        ETAddress.setText(addressBean.getAddress());
    }

    private void findAllView() {
        ETName = (EditText) findViewById(R.id.ETName);
        ETMobile = (EditText) findViewById(R.id.ETMobile);
        ETProvince = (EditText) findViewById(R.id.ETProvince);
        ETAddress = (EditText) findViewById(R.id.ETAddress);
    }

    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.addBtn) {
            if (ETName.getText().toString().equals("")) {
                CustomUtils.showTipLong(this, "请填写姓名");
                return;
            }
            if (ETMobile.getText().toString().equals("")) {
                CustomUtils.showTipLong(this, "请填写手机号");
                return;
            }
            if (!TextUtils.isMobileNO(ETMobile.getText().toString())) {
                CustomUtils.showTipLong(this, "请填写正的手机号");
                return;
            }
            if (ETProvince.getText().toString().equals("")) {
                CustomUtils.showTipLong(this, "请填写省市区");
                return;
            }
            if (ETAddress.getText().toString().equals("")) {
                CustomUtils.showTipLong(this, "请填写详细地址");
                return;
            }
            startRequestData(add_ok);
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case add_ok:
                showTipMsg("添加中......");
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", ETName.getText().toString());
                params.put("address", ETAddress.getText().toString());
                params.put("county", ETProvince.getText().toString());
                params.put("mobile", ETMobile.getText().toString());
                params.put("is_default", "on");
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                if (addressBean != null) {
                    params.put("id", addressBean.getId());
                }
                getDataWithPost(add_ok, Host.hostUrl + "/Api.php?m=api&c=memeber&a=addaddress", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case add_ok:
                if (addressBean != null) {
                    addressBean.setName(ETName.getText().toString());
                    addressBean.setAddress(ETAddress.getText().toString());
                    addressBean.setCounty(ETProvince.getText().toString());
                    addressBean.setMobile(ETMobile.getText().toString());
                    addressBean.setIs_default("on");
                    addressBean.setMember_id(AccountMoudle.getInstance().getUserBean().getMember_id());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addressBean", addressBean);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_OK);
                }
                finish();
                break;
        }
    }
}
