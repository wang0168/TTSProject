package tts.project.handi.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.JsonUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 服务设置
 */
public class ServiceSettingActivity extends BaseActivity {


    private final int getData = 1001;
    private final int submitData = 1002;
    String[] serviceType;
    int[] serviceNames = new int[]{0, 0, 0, 0, 0};
    //    List<String> serviceNames = new ArrayList<>();
    @Bind(R.id.checkbox_shui)
    CheckBox checkboxShui;
    @Bind(R.id.checkbox_dian)
    CheckBox checkboxDian;
    @Bind(R.id.checkbox_ni)
    CheckBox checkboxNi;
    @Bind(R.id.checkbox_mu)
    CheckBox checkboxMu;
    @Bind(R.id.checkbox_qi)
    CheckBox checkboxQi;
    @Bind(R.id.btn_save_change)
    Button btnSaveChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_setting);
        ButterKnife.bind(this);
        mProgressDialog.show();
        BarBean barBean = new BarBean();
        barBean.setMsg("服务设置");
        setTitle(barBean);
        startRequestData(getData);
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                try {

                    serviceType = JsonUtils.getJsonFromJson(response,"mbm").split(",");
                    for (int i = 0; i < serviceType.length; i++) {
                        if (serviceType[i].contains("1")) {
                            checkboxShui.setChecked(true);
                            serviceNames[0] = 1;
                        } else if (serviceType[i].contains("2")) {
                            checkboxDian.setChecked(true);
                            serviceNames[1] = 2;
                        } else if (serviceType[i].contains("3")) {
                            checkboxNi.setChecked(true);
                            serviceNames[2] = 3;
                        } else if (serviceType[i].contains("4")) {
                            checkboxMu.setChecked(true);
                            serviceNames[3] = 4;
                        } else if (serviceType[i].contains("5")) {
                            checkboxQi.setChecked(true);
                            serviceNames[4] = 5;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case submitData:
                CustomUtils.showTipShort(this, response);
                finish();
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=SetService", params);
                break;
            case submitData:
                String serviceName = "";
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token",MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                for (int i = 0; i < serviceNames.length; i++) {
                    if (serviceNames[i] != 0) {
                        serviceName += serviceNames[i] + ",";
                    }
                }
                if (!TextUtils.isEmpty(serviceName)){
                    params.put("servicename", serviceName);
                    getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Personal&a=SetService", params);
                }
                else{
                    CustomUtils.showTipShort(this,"请选择至少一项");
                }

                break;
        }
    }


    @OnClick({R.id.checkbox_shui, R.id.checkbox_dian, R.id.checkbox_ni, R.id.checkbox_mu, R.id.checkbox_qi, R.id.btn_save_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkbox_shui:
                if (checkboxShui.isChecked()) {
                    serviceNames[0] = 1;
                } else {
                    serviceNames[0] = 0;
                }
                break;
            case R.id.checkbox_dian:
                if (checkboxDian.isChecked()) {
                    serviceNames[1] = 2;
                } else {
                    serviceNames[1] = 0;
                }
                break;
            case R.id.checkbox_ni:
                if (checkboxNi.isChecked()) {
                    serviceNames[2] = 3;
                } else {
                    serviceNames[2] = 0;
                }
                break;
            case R.id.checkbox_mu:
                if (checkboxMu.isChecked()) {
                    serviceNames[3] = 4;
                } else {
                    serviceNames[3] = 0;
                }
                break;
            case R.id.checkbox_qi:
                if (checkboxQi.isChecked()) {
                    serviceNames[4] = 5;
                } else {
                    serviceNames[4] = 0;
                }
                break;
            case R.id.btn_save_change:
                startRequestData(submitData);
                break;
        }

    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        CustomUtils.showTipShort(this,response);
    }
}
