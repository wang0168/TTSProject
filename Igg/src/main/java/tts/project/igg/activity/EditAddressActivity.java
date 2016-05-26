package tts.project.igg.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.cityapi.CityMoudle;
import tts.moudle.api.cityapi.ProvinceSelectPopupwindow;
import tts.moudle.api.utils.CustomUtils;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.bean.AddressBean;
import tts.project.igg.common.MyAccountMoudle;

public class EditAddressActivity extends BaseActivity implements OnClickListener {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.edit_phone)
    EditText editPhone;

    @Bind(R.id.edit_area)
    EditText editArea;
    @Bind(R.id.layout_area)
    RelativeLayout layoutArea;

    @Bind(R.id.edit_address)
    EditText editAddress;

    @Bind(R.id.edit_zip_code)
    EditText edit_zip_code;
    @Bind(R.id.action)
    Button action;
    private AddressBean addressBean;
    private int from;//0新增 1，编辑
    private String provinceStr;
    private String cityStr;
    private String countryStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);
        from = getIntent().getIntExtra("from", 0);
        action.setOnClickListener(this);
        layoutArea.setOnClickListener(this);
        if (from == 0) {
            setTitle(new BarBean().setMsg("新增收货地址"));

        } else {
            addressBean = (AddressBean) getIntent().getSerializableExtra("data");
            setTitle(new BarBean().setMsg("编辑收货地址"));
            bindData();
        }


    }

    private void bindData() {
        editName.setText(addressBean.getName());
        editPhone.setText(addressBean.getMobile());
        editArea.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCountry());
        editAddress.setText(addressBean.getDetailed_address());
        edit_zip_code.setText(addressBean.getZip_code());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                startRequestData(submitData);
                break;
            case R.id.layout_area:
                CityMoudle.getInstance().showProvincePopupwindow(this, v, 2, new ProvinceSelectPopupwindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area) {
                        editArea.setText(provinceStr + cityStr + countryStr);
                        provinceStr = province;
                        cityStr = city;
                        countryStr = area;
                    }
                });
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
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token() + "");
                params.put("mobile", editPhone.getText().toString());
                params.put("name", editName.getText().toString());
                params.put("detailed_address", editAddress.getText().toString());
                params.put("province", provinceStr);
                params.put("city", cityStr);
                params.put("country", countryStr);
                params.put("zip_code", edit_zip_code.getText().toString());
                if (from == 1) {
                    params.put("address_id", addressBean.getAddress_id() + "");
                }
                getDataWithPost(submitData, Host.hostUrl + "addressInterface.api?addAddress", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case submitData:
                CustomUtils.showTipShort(this, "保存成功");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
