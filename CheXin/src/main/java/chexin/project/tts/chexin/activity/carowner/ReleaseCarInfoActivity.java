package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.login.LoginActivity;
import chexin.project.tts.chexin.bean.CarDetailBean;
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
 * Created by sjb on 2016/3/25.
 */
public class ReleaseCarInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.edit_from)
    EditText editFrom;
    @Bind(R.id.btn_from)
    Button btnFrom;
    @Bind(R.id.edit_to)
    EditText editTo;
    @Bind(R.id.btn_to)
    Button btnTo;
    @Bind(R.id.edit_car_number)
    EditText editCarNumber;
    @Bind(R.id.edit_car_type)
    EditText editCarType;
    @Bind(R.id.btn_car_code)
    Button btnCarCode;

    @Bind(R.id.edit_phone_number)
    EditText editPhoneNumber;
    @Bind(R.id.edit_note)
    EditText editNote;
    @Bind(R.id.edit_validity)
    EditText editValidity;
    @Bind(R.id.btn_validity)
    Button btnValidity;

    @Bind(R.id.btn_action)
    Button btnAction;
    @Bind(R.id.edit_car_long)
    EditText editCarLong;
    @Bind(R.id.edit_car_width)
    EditText editCarWidth;
    @Bind(R.id.tv_car_weight)
    EditText tvCarWeight;
    private SelectPopupWindow selectPopupWindow;
    private String FromProvince = "";
    private String FromCity = "";
    private String FromCountry = "";
    private String ToProvince = "";
    private String ToCity = "";
    private String ToCountry = "";
    private List<CarDetailBean> carDetailBeans;
    private List<EffectiveBean> carEffectiveBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_car_info_activity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("发布车源"));
        btnValidity.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnTo.setOnClickListener(this);
        btnCarCode.setOnClickListener(this);
        btnAction.setOnClickListener(this);
        carDetailBeans = ConfigMoudle.getInstance().getCarDetailBeans();
        carEffectiveBeans = ConfigMoudle.getInstance().getCarEffectiveBeans();
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

            if (TextUtils.isEmpty(editCarNumber.getText().toString())) {
                CustomUtils.showTipShort(this, "请选择车牌号码");
                return;
            }
            if (TextUtils.isEmpty(editPhoneNumber.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入联系电话");
                return;
            }
            if (TextUtils.isEmpty(editNote.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入备注");
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
            params.put("CarType", editCarType.getText().toString());
            params.put("CarNo", editCarNumber.getText().toString());
            params.put("CarLength", editCarLong.getText().toString());
            params.put("CarWidth", editCarWidth.getText().toString());
            params.put("CarWeight", tvCarWeight.getText().toString());
            params.put("MobileNo", editPhoneNumber.getText().toString());
            params.put("Note", editNote.getText().toString() + "");
            params.put("ExpiryDate", editValidity.getText().toString());
            params.put("vehicle_id", editCarNumber.getTag() + "");
            getDataWithPost(submitData, Host.hostUrl + "/car.api?releaseCar", params);
            btnAction.setClickable(false);
            btnAction.setBackgroundResource(R.color.gray_normal);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == submitData) {
            CustomUtils.showTipShort(this, "发布成功");
            startActivity(new Intent(this, MyCarSourceActivity.class));
            finish();
        }

    }


    @Override
    public void onClick(View v) {
        List<ConfigBean> temp;
        switch (v.getId()) {
            case R.id.btn_action:
                startRequestData(submitData);
                break;
            case R.id.btn_validity:
                temp = new ArrayList<>();
                for (int i = 0; i < carEffectiveBeans.size(); i++) {
                    temp.add(new ConfigBean(0, null, carEffectiveBeans.get(i).getEffective_time(), null));
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
            case R.id.btn_car_code:
                temp = new ArrayList<>();
                if (carDetailBeans.size() == 0) {
                    CustomUtils.showTipShort(this, "请先添加车辆");
                    return;
                }
                for (int i = 0; i < carDetailBeans.size(); i++) {
                    temp.add(new ConfigBean(0, null, carDetailBeans.get(i).getCarCode(), null));
                }
                selectPopupWindow = new SelectPopupWindow(this, temp, 2, "车牌号码");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        editCarLong.setText(carDetailBeans.get(pos).getCarLength());
                        editCarType.setText(carDetailBeans.get(pos).getCarType());
                        editCarWidth.setText(carDetailBeans.get(pos).getCarWidth());
                        tvCarWeight.setText(carDetailBeans.get(pos).getCarWeight());
                        editCarNumber.setText(other);
                        editCarNumber.setTag(carDetailBeans.get(pos).getVehicle_id());
                        editCarNumber.setSelection(other.length());
                    }
                });
                break;
        }
    }
}
