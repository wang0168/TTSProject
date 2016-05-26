package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.LineListActivity;
import chexin.project.tts.chexin.activity.goodsowner.MyGoodsSourceActivity;
import chexin.project.tts.chexin.activity.login.LoginActivity;
import chexin.project.tts.chexin.bean.RouteBean;
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
public class RunningRouteAddActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.edit_from)
    EditText editFrom;
    @Bind(R.id.btn_from)
    Button btnFrom;
    @Bind(R.id.edit_to)
    EditText editTo;
    @Bind(R.id.btn_to)
    Button btnTo;
    @Bind(R.id.btn_add_route)
    Button btnAddRoute;
    private SelectPopupWindow selectPopupWindow;
    private String FromProvince = "";
    private String FromCity = "";
    private String FromCountry = "";
    private String ToProvince = "";
    private String ToCity = "";
    private String ToCountry = "";
    private RouteBean routeBean;
    private String routeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_route_add_activity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("新增路线"));
        routeBean = (RouteBean) getIntent().getSerializableExtra("data");
        findAllView();
        if (routeBean != null) {
            btnAddRoute.setText("保存修改");
            setTitle(new BarBean().setMsg("修改路线"));
            routeId = routeBean.getRoute_id() + "";
            editFrom.setText(routeBean.getFromProvince() + routeBean.getFromCity() + routeBean.getFromCountry() + "");
            editTo.setText(routeBean.getToProvince() + routeBean.getToCity() + routeBean.getToCountry() + "");
        }
    }

    private void findAllView() {
        btnFrom.setOnClickListener(this);
        btnTo.setOnClickListener(this);
        btnAddRoute.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_from:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "始发地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other,int pos) {
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
            case R.id.btn_add_route:
                startRequestData(submitData);
                break;
        }
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
            if (!TextUtils.isEmpty(routeId)) {
                params.put("route_id", routeId);
            }
            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            params.put("FromProvince", FromProvince);
            params.put("FromCity", FromCity);
            params.put("FromCountry", FromCountry);
            params.put("ToProvince", ToProvince);
            params.put("ToCity", ToCity);
            params.put("ToCountry", ToCountry);
            getDataWithPost(submitData, Host.hostUrl + "/route.api?addRoute", params);

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == submitData) {
            CustomUtils.showTipShort(this, "添加成功");
            setResult(RESULT_OK);
            finish();
        }

    }
}
