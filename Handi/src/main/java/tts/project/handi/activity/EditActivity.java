package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.cityapi.CityMoudle;
import tts.moudle.api.cityapi.ProvinceSelectPopupwindow;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.baidumapapi.moudle.BaiduMoudle;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 编辑页面
 * Created by chen on 2016/3/15.
 */
public class EditActivity extends BaseActivity {
    private EditText editText;
    private EditText editAddress;
    private TextView rightAction;
    private String oldStr;
    private String paramKey;
    private String mCurrentProviceName;
    private String mCurrentCityName;
    private String mCurrentDistrictName;
    private String mLatitude;
    private String mLongitude;
    private int postLocation = 101;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = (EditText) findViewById(R.id.edit_input);
        editAddress = (EditText) findViewById(R.id.edit_address);
        setTitle(new BarBean().setMsg("修改资料"));
        MenuItemBean menuItemBean = new MenuItemBean().setTitle("保存");
        addMenu(menuItemBean);
        oldStr = getIntent().getStringExtra("oldStr");
        paramKey = getIntent().getStringExtra("key");
        if ("address".equals(paramKey)) {
            BaiduMoudle.getInstance().startLocation(this, new BDLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation bbdLocation) {
                            mLatitude = bbdLocation.getLatitude() + "";
                            mLongitude = bbdLocation.getLongitude() + "";

//                            String locationStr = "维度：" + bbdLocation.getLatitude() + "\n"
//                                    + "经度：" + bbdLocation.getLongitude();
                        }
                    }
            );
            editAddress.setVisibility(View.VISIBLE);
            editAddress.setHint("点击选择地区");
            editText.setHint("请输入详细地址");
            editAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityMoudle.getInstance().showProvincePopupwindow(EditActivity.this, editAddress, 2, new ProvinceSelectPopupwindow.OnClickListener() {
                        @Override
                        public void doClick(String province, String city, String area) {
//                            CustomUtils.showTipShort(MainActivity.this, province + "==" + city + "===" + area);
                            mCurrentProviceName = province;
                            mCurrentCityName = city;
                            mCurrentDistrictName = area;
                            editAddress.setText(mCurrentProviceName + mCurrentCityName + mCurrentDistrictName + "");
                        }
                    });
                }
            });
            editText.setText("");

//            editText.setSelection(oldStr.length());
        } else {
            editText.setText(oldStr);
            editText.setSelection(oldStr.length());
        }


    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startRequestData(submitData);

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());

            if ("address".equals(paramKey)) {
                if (TextUtils.isEmpty(mCurrentProviceName)) {
                    CustomUtils.showTipLong(this, "请选择地区");
                    return;
                }
//                params.put("city", mCurrentProviceName);
//                params.put("county", mCurrentCityName);
//                params.put("address", mCurrentDistrictName);
                params.put("xxaddress", mCurrentProviceName+mCurrentCityName+mCurrentDistrictName+editText.getText().toString());
                params.put("B", mLatitude);
                params.put("L", mLongitude);
            } else {
                params.put(paramKey, editText.getText().toString());
            }

            getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Personal&a=PerPerfect", params);
        }
        if (index == postLocation) {

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, "修改成功");
        Intent intent = new Intent();
        if ("address".equals(paramKey)) {

            setResult(RESULT_OK, intent.putExtra("newStr", mCurrentProviceName + mCurrentCityName + mCurrentDistrictName));
        } else {
            setResult(RESULT_OK, intent.putExtra("newStr", editText.getText().toString()));
        }

        finish();
    }


}
