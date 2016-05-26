package chexin.project.tts.chexin.activity.goodsowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.login.LoginActivity;
import chexin.project.tts.chexin.bean.ConfigBean;
import chexin.project.tts.chexin.bean.EffectiveBean;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import chexin.project.tts.chexin.widget.selectPopupWindow.SelectPopupWindow;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by sjb on 2016/3/28.
 */
public class ReleaseGoodsInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.edit_goods_type)
    EditText editGoodsType;
    @Bind(R.id.btn_goods_type)
    Button btnGoodsType;
    @Bind(R.id.edit_goods_name)
    EditText editGoodsName;
    @Bind(R.id.tv_goods_unit)
    EditText tvGoodsUnit;
    @Bind(R.id.btn_goods_unit)
    Button btnGoodsUnit;
    @Bind(R.id.edit_car_long)
    EditText editCarLong;
    @Bind(R.id.edit_car_width)
    EditText editCarWidth;
    @Bind(R.id.tv_system_number)
    TextView tvSystemNumber;
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.edit_address)
    EditText editAddress;
    @Bind(R.id.edit_remark)
    EditText editRemark;
    @Bind(R.id.edit_validity)
    EditText editValidity;
    @Bind(R.id.btn_validity)
    Button btnValidity;
    @Bind(R.id.btn_action)
    Button btnAction;
    private Button btnFrom;
    private Button btnTo;
    private EditText editFrom;
    private EditText editTo;
    private SelectPopupWindow selectPopupWindow;
    private String FromProvince = "";
    private String FromCity = "";
    private String FromCountry = "";
    private String ToProvince = "";
    private String ToCity = "";
    private String ToCountry = "";
    private List<EffectiveBean> goodEffectiveBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_goods_info_activity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("发布货源"));
        findAllView();
        goodEffectiveBeans = ConfigMoudle.getInstance().getGoodEffectiveBeans();
    }

    private void findAllView() {
        btnFrom = (Button) findViewById(R.id.btn_from);
        btnFrom.setOnClickListener(this);
        btnTo = (Button) findViewById(R.id.btn_to);
        btnTo.setOnClickListener(this);
        editFrom = (EditText) findViewById(R.id.edit_from);
        editTo = (EditText) findViewById(R.id.edit_to);
        btnGoodsType.setOnClickListener(this);
        btnGoodsUnit.setOnClickListener(this);
        btnValidity.setOnClickListener(this);
        btnAction.setOnClickListener(this);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                startActivityForResult(new Intent(this, LoginActivity.class).putExtra("IsCallback", true), 3001);
                return;
            }
            if (TextUtils.isEmpty(FromProvince)) {
                CustomUtils.showTipShort(this, "请选择始发地");
                return;
            }
            if (TextUtils.isEmpty(ToProvince)) {
                CustomUtils.showTipShort(this, "请选择目的地");
                return;
            }
            if (TextUtils.isEmpty(editGoodsType.getText().toString())) {
                CustomUtils.showTipShort(this, "请选择货物类型");
                return;
            }
            if (TextUtils.isEmpty(editGoodsName.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入货物名称");
                return;
            }
            if (TextUtils.isEmpty(tvGoodsUnit.getText().toString())) {
                CustomUtils.showTipShort(this, "请选择体积重量");
                return;
            }
            if (TextUtils.isEmpty(editCarLong.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入需求车长");
                return;
            }
            if (TextUtils.isEmpty(editCarWidth.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入需求车宽");
                return;
            }
            if (TextUtils.isEmpty(editPhone.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入联系电话");
                return;
            }
            if (TextUtils.isEmpty(editAddress.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入详细地址");
                return;
            }
            if (TextUtils.isEmpty(editValidity.getText().toString())) {
                CustomUtils.showTipShort(this, "请选择有效期");
                return;
            }
            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            params.put("FromProvince", FromProvince);
            params.put("FromCity", FromCity);
            params.put("FromCountry", FromCountry);
            params.put("ToProvince", ToProvince);
            params.put("ToCity", ToCity);
            params.put("ToCountry", ToCountry);
            params.put("SKUType", editGoodsType.getText().toString());
            params.put("SKUWeight", tvGoodsUnit.getText().toString());
            params.put("SKULong", editCarLong.getText().toString());
            params.put("SKUWidth", editCarWidth.getText().toString());
            params.put("SKUName", editGoodsName.getText().toString());
            params.put("MobileNo", editPhone.getText().toString());
            params.put("Address", editAddress.getText().toString());
            params.put("Note", editRemark.getText().toString() + "");
            params.put("ExpiryDate", editValidity.getText().toString());
            getDataWithPost(submitData, Host.hostUrl + "/goods.api?addGoods", params);
            btnAction.setClickable(false);
            btnAction.setBackgroundResource(R.color.gray_normal);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == submitData) {
            CustomUtils.showTipShort(this, "发布成功");
            startActivity(new Intent(this, MyGoodsSourceActivity.class));
            finish();
        }

    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        btnAction.setClickable(true);
        btnAction.setBackgroundResource(R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_from:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "始发地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        FromProvince = province;
                        FromCity = city;
                        FromCountry = area;
                        String strTemp = province + city + area;
                        editFrom.setText(strTemp);
                        editFrom.setSelection(strTemp.length());
                    }
                });
                break;
            case R.id.btn_to:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "目的地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        ToProvince = province;
                        ToCity = city;
                        ToCountry = area;
                        String strTemp = province + city + area;
                        editTo.setText(strTemp);
                        editTo.setSelection(strTemp.length());
                    }
                });
                break;
            case R.id.btn_goods_type:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getGoodsTypeBeans(), 2, "货物类型");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        editGoodsType.setText(other);
                        editGoodsType.setSelection(other.length());
                    }
                });
                break;
            case R.id.btn_goods_unit:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getGoodsUnitBeans(), 2, "体积重量");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        tvGoodsUnit.setText(other);
                        tvGoodsUnit.setSelection(other.length());
                    }
                });
                break;
            case R.id.btn_validity:
                List<ConfigBean> temp = new ArrayList<>();
                for (int i = 0; i < goodEffectiveBeans.size(); i++) {
                    temp.add(new ConfigBean(0, null, goodEffectiveBeans.get(i).getEffective_time(), null));
                }
                selectPopupWindow = new SelectPopupWindow(this, temp, 2, "有效期");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        editValidity.setText(other);
                        editValidity.setSelection(other.length());
                    }
                });
                break;
            case R.id.btn_action:
                startRequestData(submitData);
                break;
        }
    }
}
