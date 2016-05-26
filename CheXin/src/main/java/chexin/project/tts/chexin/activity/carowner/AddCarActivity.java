package chexin.project.tts.chexin.activity.carowner;


import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarDetailBean;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import chexin.project.tts.chexin.widget.selectPopupWindow.SelectPopupWindow;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * 添加/修改 车辆信息
 */
public class AddCarActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.edit_car_code)
    EditText editCarCode;
    @Bind(R.id.edit_car_type)
    EditText editCarType;
    @Bind(R.id.btn_car_type)
    Button btnCarType;
    @Bind(R.id.edit_car_long)
    EditText editCarLong;
    @Bind(R.id.edit_car_width)
    EditText editCarWidth;
    @Bind(R.id.edit_car_weight)
    EditText editCarWeight;
    @Bind(R.id.btn_unit)
    Button btnUnit;
    @Bind(R.id.btn_action)
    Button btnAction;
    private CarDetailBean mData;
    private SelectPopupWindow selectPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("车辆信息"));
        mData = (CarDetailBean) getIntent().getSerializableExtra("data");
        if (mData != null) {
            bindData();
        }
        btnAction.setOnClickListener(this);
        btnCarType.setOnClickListener(this);
        btnUnit.setOnClickListener(this);
    }

    private void bindData() {
        editCarCode.setText(mData.getCarCode());
//        editCarCode.setSelection();
        editCarType.setText(mData.getCarType());
        editCarLong.setText(mData.getCarLength());
        editCarWidth.setText(mData.getCarWidth());
        editCarWeight.setText(mData.getCarWeight());
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                if (TextUtils.isEmpty(editCarCode.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入车牌号");
                    return;
                }
                if (TextUtils.isEmpty(editCarType.getText().toString())) {
                    CustomUtils.showTipShort(this, "请选择车辆类型");
                    return;
                }
                if (TextUtils.isEmpty(editCarLong.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入车长");
                    return;
                }
                if (TextUtils.isEmpty(editCarWidth.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入车宽");
                    return;
                }
                if (TextUtils.isEmpty(editCarWeight.getText().toString())) {
                    CustomUtils.showTipShort(this, "请选择车量载重");
                    return;
                }
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                if (mData != null) {
                    if (!TextUtils.isEmpty(mData.getVehicle_id() + "")) {
                        params.put("vehicle_id", mData.getVehicle_id() + "");
                    }
                }
                params.put("CarType", editCarType.getText().toString());
                params.put("CarWeight", editCarWeight.getText().toString());
                params.put("CarLength", editCarLong.getText().toString());
                params.put("CarWidth", editCarWidth.getText().toString());
                params.put("CarCode", editCarCode.getText().toString());
                getDataWithPost(submitData, Host.hostUrl + "car.api?addVehicle", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case submitData:
                Logger.d(response);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
                startRequestData(submitData);
                break;
            case R.id.btn_car_type:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getCarTypeBeans(), 2, "请选择车辆类型");
                selectPopupWindow.showAtLocation(btnAction, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        editCarType.setText(other);
                        editCarType.setSelection(other.length());
                    }
                });
                break;
            case R.id.btn_unit:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getGoodsUnitBeans(), 2, "请选择车辆载重");
                selectPopupWindow.showAtLocation(btnAction, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        editCarWeight.setText(other);
                        editCarWeight.setSelection(other.length());
                    }
                });
                break;
        }
    }
}
